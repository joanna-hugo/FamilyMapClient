package felsted.joanna.fmc;
import felsted.joanna.fmc.model.*;

import com.google.gson.Gson;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.URL;

public class ServerProxy {
    //TODO interface to all public functions of the server

    /*

    https://students.cs.byu.edu/~cs240ta/winter2019/rodham_files/?path=20-android-web-async-task%2Fcode%2FAsyncWebAccess%2Fapp%2Fsrc%2Fmain%2Fjava%2Fedu%2Fbyu%2Fcs240%2Fasyncwebaccess/
    public class HttpClient {

    public String getUrl(URL url) {
        try {
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            connection.setRequestMethod("GET");
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
                String responseBodyData = baos.toString();
                return responseBodyData;
            }
        }
        catch (Exception e) {
            Log.e("HttpClient", e.getMessage(), e);
        }

        return null;
     }
}
     */
    public loginResponse login(loginRequest request){
        try {
            // connect to server
            URL url = new URL("connection://10.0.2.2:8080/user/" + "login");
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            connection.setRequestMethod("POST");
//            connection.setDoOutput(true);
            connection.addRequestProperty("Accept", "application/json");
            connection.connect();

            // send request data
            Gson gson = new Gson();
            String reqData = gson.toJson(request);
            OutputStream reqBody = connection.getOutputStream();
            writeString(reqData, reqBody);
            reqBody.close();
            if (connection.getResponseCode() == HttpURLConnection.HTTP_OK) {
                System.out.println("Route successfully claimed.");
            } else {
                System.out.println("ERROR: " + connection.getResponseMessage());
            }

            // get response data
            if (connection.getResponseCode() == HttpURLConnection.HTTP_OK) {
                InputStream respBody = connection.getInputStream();
                String respData = readString(respBody);
                System.out.println(respData);
                return respData;
            } else {
                System.out.println("ERROR: " + connection.getResponseMessage());
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

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

}
