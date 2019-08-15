package felsted.joanna.fmc;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.URL;

import felsted.joanna.fmc.model.loginRequest;
import felsted.joanna.fmc.model.loginResponse;


import android.net.Uri;
import android.util.Log;

//import com.google.gson.Gson; //TODO undo this later, I'm not working on this rn

public class ServerProxy {
    //used for search or fetch photo items.

    /*
        NOTE
        When testing your app, you will normally run both your client and your server on the
same machine, using an Android emulator for your client. In this case, your emulator will
function as a separate machine and as a result, using a hostname of “localhost” or an ip
address of 127.0.0.1 from your Android login fragment will refer to the emulator--not the
host machine that will also contain your running Family Map Server. To access a server
running on the same machine as an Android emulator, use the ip address 10.0.2.2
instead of localhost or 127.0.0.1.
     */

    public static final String TAG = "FlickrFetcher";

    private static final String ENDPOINT = "https://api.flickr.com/services/rest/";
    private static final String METHOD_GET_RECENT = "flickr.photos.getRecent";
    private static final String METHOD_SEARCH = "flickr.photos.search";
    private static final String METHOD_GET_PHOTOS = "flickr.people.getPhotos";
    private static final String PARAM_EXTRAS = "extras";
    private static final String PARAM_TEXT = "text";
    private static final String PAGE = "page";
    private static final String EXTRA_SMALL_URL = "url_s";
    private static final String XML_PHOTO = "photo";
    public static final String API_KEY = "77bd723be1c9570c9df9cb84109eebbe";

    // write a String to an OutputStream
    private void writeString(String str, OutputStream os) throws IOException {
        OutputStreamWriter sw = new OutputStreamWriter(os);
        sw.write(str);
        sw.flush();
    }

    // read a String from an InputStream
    private static String readString(InputStream is) throws IOException {
        StringBuilder sb = new StringBuilder();
        InputStreamReader sr = new InputStreamReader(is);
        char[] buf = new char[1024];
        int len;
        while ((len = sr.read(buf)) > 0) {
            sb.append(buf, 0, len);
        }
        return sb.toString();
    }

    public byte[] getUrlBytes(String urlSpec) throws IOException {
        URL url = new URL (urlSpec);
        HttpURLConnection connection = (HttpURLConnection) url.openConnection();

        try{
            ByteArrayOutputStream out = new ByteArrayOutputStream();
            InputStream in = connection.getInputStream(); //or getOutputStream() for POST calls

            if(connection.getResponseCode() != HttpURLConnection.HTTP_OK){
                throw new IOException(connection.getResponseMessage() + ": with " + urlSpec);
            }

            int bytesRead = 0;
            byte[] buffer = new byte[1024];
            while((bytesRead = in.read(buffer)) >0) {
                out.write(buffer, 0, bytesRead);
            }
            out.close();
            return out.toByteArray();
        } finally{
            connection.disconnect();
        }
    }

    public String getUrlString(String urlSpec) throws IOException {
        return new String(getUrlBytes(urlSpec));
    }

    public String login(String urlSpec) throws IOException{
        //https://stackoverflow.com/questions/21404252/post-request-send-json-data-java-httpurlconnection
        URL url = new URL (urlSpec);
        HttpURLConnection connection = (HttpURLConnection) url.openConnection();

        try{ //TODO this works(vaguely) with the flickr link, why not mine?
            connection.setDoInput(true); //TODO generalize this after testing
            connection.setDoOutput(true);
            connection.setRequestMethod("POST");

            JSONObject req =  new JSONObject();
            req.put("userName", "forest_gump"); //TODO GENERALIZE THIS AFTER TESTING
            req.put("password", "1forest1");

            OutputStreamWriter wr = new OutputStreamWriter(connection.getOutputStream());
            wr.write(req.toString());
            wr.flush();

            StringBuilder sb = new StringBuilder();
            int HttpResult = connection.getResponseCode();
            if (HttpResult == HttpURLConnection.HTTP_OK) {
                BufferedReader br = new BufferedReader(
                        new InputStreamReader(connection.getInputStream(), "utf-8"));
                String line = null;
                while ((line = br.readLine()) != null) {
                    sb.append(line + "\n");
                }
                br.close();
                System.out.println("" + sb.toString());
                return sb.toString();
            }
            return "";
        } catch (JSONException je) {
            System.out.println("ruh ro");
        }finally{
            connection.disconnect();
        }
        return "";
    }


    public void loginGen( loginRequest rqst) throws IOException{
        final URL loginURL = new URL ("localhost:8080");
        HttpURLConnection con = (HttpURLConnection) loginURL.openConnection();

        //TODO make a helper function for this setup
        con.setRequestMethod("POST");
        con.setRequestProperty("Authorization", "[B@2acd548b");
        con.setDoOutput(true);
        con.setDoInput(true);

        //TODO make a helper function for this POST
        OutputStreamWriter wr = new OutputStreamWriter(con.getOutputStream());
        wr.write(rqst.toString());
        wr.flush();

    }

    public String getPerson(String urlSpec) throws IOException{
        //https://stackoverflow.com/questions/21404252/post-request-send-json-data-java-httpurlconnection
        URL url = new URL (urlSpec);
        HttpURLConnection connection = (HttpURLConnection) url.openConnection();

        try{
            connection.setDoInput(true);
            connection.setDoOutput(true);
            connection.setRequestMethod("GET");

            JSONObject req =  new JSONObject();
            req.put("userName", "forest_gump"); //GENERALIZE THIS AFTER TESTING
            req.put("password", "1forest1");

            OutputStreamWriter wr = new OutputStreamWriter(connection.getOutputStream());
            wr.write(req.toString());
            wr.flush();

            StringBuilder sb = new StringBuilder();
            int HttpResult = connection.getResponseCode();
            if (HttpResult == HttpURLConnection.HTTP_OK) {
                BufferedReader br = new BufferedReader(
                        new InputStreamReader(connection.getInputStream(), "utf-8"));
                String line = null;
                while ((line = br.readLine()) != null) {
                    sb.append(line + "\n");
                }
                br.close();
                System.out.println("" + sb.toString());
                return sb.toString();
            }
            return "";
        } catch (JSONException je) {
            System.out.println("ruh ro");
        }finally{
            connection.disconnect();
        }
        return "";
    }
}
