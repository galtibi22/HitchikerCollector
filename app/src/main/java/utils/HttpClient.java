package utils;

import java.io.BufferedInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;

public class HttpClient {

    public String get(String url1)  {
        String response="";
       try {
           URL url = new URL(url1);
           HttpURLConnection urlConnection = (HttpURLConnection) url.openConnection();
           InputStream in = new BufferedInputStream(urlConnection.getInputStream());
           response = readStream(in);
           urlConnection.disconnect();

       }catch(Exception e){
           e.printStackTrace();
       }
        return response;
    }


    private String readStream(InputStream is) {
        try {
            ByteArrayOutputStream bo = new ByteArrayOutputStream();
            int i = is.read();
            while(i != -1) {
                bo.write(i);
                i = is.read();
            }
            return bo.toString();
        } catch (IOException e) {
            return "";
        }
    }
}
