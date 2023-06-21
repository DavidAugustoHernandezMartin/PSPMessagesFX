package augusto.hernandez.messagesfx.utils;

import java.io.*;
import java.net.URL;
import java.net.HttpURLConnection;
import java.util.StringJoiner;
import java.util.zip.GZIPInputStream;

public class ServiceUtils {
    private static String token = null;
    private static String username = null;
    private  static  String image = null;

    public static String getToken() {
        return token;
    }

    public static String getUsername() {
        return username;
    }

    public static String getImage() {
        return image;
    }
    public static String server = "http://localhost:3000";
    public static void setToken(String token) {
        ServiceUtils.token = token;
    }

    public static void setUsername(String username){ServiceUtils.username = username;}
    public static void setImage(String image){ServiceUtils.image = image;}

    public static void removeToken() {
        ServiceUtils.token = null;
    }
    public static void removeUsername(){
        ServiceUtils.username = null;
    }
    public static void removeImage(){
        ServiceUtils.image =null;
    }
    public static String getCharset(String contentType) {
        for (String param : contentType.replace(" ", "").split(";")) {
            if (param.startsWith("charset=")) {
                return param.split("=", 2)[1];
            }
        }
        return null; // Probably binary content
    }

    public static String getResponse(String url, String data, String method) {
        BufferedReader bufInput = null;
        StringJoiner result = new StringJoiner("\n");
        try {
            URL urlConn = new URL(url);
            HttpURLConnection conn = (HttpURLConnection) urlConn.openConnection();
            conn.setReadTimeout(20000 /*milliseconds*/);
            conn.setConnectTimeout(15000 /* milliseconds */);
            conn.setRequestMethod(method);
            conn.setRequestProperty("Host", "localhost");
            conn.setRequestProperty("Connection", "keep-alive");
            conn.setRequestProperty("Accept", "application/json");
            conn.setRequestProperty("Origin", "http://localhost");
            conn.setRequestProperty("Accept-Encoding", "gzip,deflate,sdch");
            conn.setRequestProperty("Accept-Language", "es-ES,es;q=0.8");
            conn.setRequestProperty("Accept-Charset", "UTF-8");
            conn.setRequestProperty("User-Agent", "Java");
            // If set, send the authentication token
            if (token != null) {
                conn.setRequestProperty("Authorization", "Bearer " + token);
            }
            if (data != null) {
                conn.setRequestProperty("Content-Type", "application/json; charset=UTF-8");
                conn.setRequestProperty("Content-Length", Integer.toString(data.length()));
                conn.setDoOutput(true);
                //Send request
                DataOutputStream wr = new DataOutputStream(conn.getOutputStream());
                wr.write(data.getBytes());
                wr.flush();

            }
            String charset = getCharset(conn.getHeaderField("Content-Type"));
            if (charset != null) {
                InputStream input = conn.getInputStream();
                if ("gzip".equals(conn.getContentEncoding())) {
                    input = new GZIPInputStream(input);
                }
                bufInput = new BufferedReader(new InputStreamReader(input));
                String line;
                while ((line = bufInput.readLine()) != null) {
                    result.add(line);
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (bufInput != null) {
                try {
                    bufInput.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
        return result.toString();
    }
}
