package felsted.joanna.fmc;

import org.json.JSONException;
import org.json.JSONObject;

import com.google.gson.*;

import java.io.BufferedReader;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.URL;

import felsted.joanna.fmc.model.event;
import felsted.joanna.fmc.model.eventListResponse;
import felsted.joanna.fmc.model.loginRequest;
import felsted.joanna.fmc.model.loginResponse;
import felsted.joanna.fmc.model.personListResponse;
import felsted.joanna.fmc.model.registerRequest;

public class ServerProxy {
    private String authToken; // TODO will this stay after I call login/register

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

    // TODO abstract as much as possible into helper functions


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

    public loginResponse login(loginRequest rqst) throws IOException{
        //https://stackoverflow.com/questions/21404252/post-request-send-json-data-java-httpurlconnection
        URL url = new URL ("http://10.0.2.2:8080/user/login");
        HttpURLConnection connection = (HttpURLConnection) url.openConnection();

        try{
            connection.setDoInput(true); //TODO generalize this after testing
            connection.setDoOutput(true);
            connection.setRequestMethod("POST");

            JSONObject req =  new JSONObject();
            req.put("userName", rqst.getUsername());
            req.put("password", rqst.getPassword());

            OutputStreamWriter wr = new OutputStreamWriter(connection.getOutputStream());
            wr.write(req.toString());
            wr.flush();

            StringBuilder sb = new StringBuilder();
            int HttpResult = connection.getResponseCode();
            System.out.println("GET Response Code :: " + HttpResult);
            if (HttpResult == HttpURLConnection.HTTP_OK) {
                BufferedReader br = new BufferedReader(
                        new InputStreamReader(connection.getInputStream(), "utf-8"));
                String line = null;
                while ((line = br.readLine()) != null) {
                    sb.append(line + "\n");
                }
                br.close();
                System.out.println("" + sb.toString());
//                return sb.toString();

                Gson gson = new Gson();
                loginResponse rsp = gson.fromJson(sb.toString(), loginResponse.class);
                authToken=rsp.getAuthToken();
                return rsp;
            }else{
                throw new IOException("server not responding"); //TODO handle this error better, for end user convenience
            }
        } catch (JSONException je) {
            System.out.println("ruh ro");
        }finally{
            connection.disconnect();
        }
        return null; //this may cause problems, but the if/else statement should catch everything
    }

    public loginResponse register(registerRequest rqst) throws IOException{
        //https://stackoverflow.com/questions/21404252/post-request-send-json-data-java-httpurlconnection
        URL url = new URL ("http://10.0.2.2:8080/user/register");
        HttpURLConnection connection = (HttpURLConnection) url.openConnection();

        try{
            connection.setDoInput(true); //TODO generalize this after testing
            connection.setDoOutput(true);
            connection.setRequestMethod("POST");

            JSONObject req =  new JSONObject(); //TODO can we use GSON to make the json object?
            req.put("userName", rqst.getUsername());
            req.put("password", rqst.getPassword());
            req.put("email", rqst.getEmail());
            req.put("firstName", rqst.getFirstName());
            req.put("lastName", rqst.getLastName());
            req.put("gender", rqst.getGender());


            OutputStreamWriter wr = new OutputStreamWriter(connection.getOutputStream());
            wr.write(req.toString());
            wr.flush();

            StringBuilder sb = new StringBuilder();
            int HttpResult = connection.getResponseCode();
            if (HttpResult == HttpURLConnection.HTTP_OK) { //TODO why is the server breaking right now??
                BufferedReader br = new BufferedReader(
                        new InputStreamReader(connection.getInputStream(), "utf-8"));
                String line = null;
                while ((line = br.readLine()) != null) {
                    sb.append(line + "\n");
                }
                br.close();
                System.out.println("" + sb.toString());
//                return sb.toString();

                Gson gson = new Gson();
                loginResponse rsp = gson.fromJson(sb.toString(), loginResponse.class);
                authToken = rsp.getAuthToken();
                return rsp;
            }else{
                //TODO make this a more meaningful error, maybe use the same procedure as above but make an error message
                throw new IOException("server not responding"); //TODO handle this error better, for end user convenience
            }
        } catch (JSONException je) {
            System.out.println("ruh ro");
        }finally{
            connection.disconnect();
        }
        return null; //this may cause problems, but the if/else statement should catch everything
    }

    public personListResponse getPersons(String token) throws IOException{

        URL url = new URL ("http://10.0.2.2:8080/person"); //TODO generalize this to user host/port provided in screen
        HttpURLConnection connection = (HttpURLConnection) url.openConnection();

        try {
            connection.setRequestMethod("GET");
            connection.setRequestProperty("Authorization", token);
            connection.connect();

            if (connection.getResponseCode() == HttpURLConnection.HTTP_OK) {
                // Get response body input stream
                InputStream responseBody = connection.getInputStream();

                // Read response body bytes
                ByteArrayOutputStream baos = new ByteArrayOutputStream();
                byte[] buffer = new byte[1024];
                int length = 0;
                while ((length = responseBody.read(buffer)) != -1) {
                    baos.write(buffer, 0, length);
                }

                // Convert response body bytes to a string
                Gson gson = new Gson();
                personListResponse rsp = gson.fromJson(baos.toString(), personListResponse.class);
                return rsp;
            }
        } catch (Exception e) {
            System.out.println("ruh ro");
        }catch(Throwable e){
            System.out.println("ERROR: " + e.getMessage());
        }finally{
            connection.disconnect();
        }
        return new personListResponse();
    }

    public eventListResponse getEvents(String token) throws IOException{ //TODO flesh this out

        URL url = new URL ("http://10.0.2.2:8080/event");
        HttpURLConnection connection = (HttpURLConnection) url.openConnection();

        try {
            connection.setRequestMethod("GET");
            connection.setRequestProperty("Authorization", token);
            connection.connect();

            if (connection.getResponseCode() == HttpURLConnection.HTTP_OK) {
                // Get response body input stream
                InputStream responseBody = connection.getInputStream();

                // Read response body bytes
                ByteArrayOutputStream baos = new ByteArrayOutputStream();
                byte[] buffer = new byte[1024];
                int length = 0;
                while ((length = responseBody.read(buffer)) != -1) {
                    baos.write(buffer, 0, length);
                }

                // Convert response body bytes to a string
                Gson gson = new Gson();
                eventListResponse rsp = gson.fromJson(baos.toString(), eventListResponse.class);
                return rsp;
            }
        } catch (Exception e) {
            System.out.println("ruh ro");
        }catch(Throwable e){
            System.out.println("ERROR: " + e.getMessage());
        }finally{
            connection.disconnect();
        }
        return new eventListResponse();
    }

}
