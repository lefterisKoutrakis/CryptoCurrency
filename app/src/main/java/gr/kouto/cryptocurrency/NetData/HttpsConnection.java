package gr.kouto.cryptocurrency.NetData;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

public class HttpsConnection {

    public static String HttpsRequest(String urll, int timeoutSocket) throws Exception
    {
        try {

            URL url = new URL(urll);
            HttpURLConnection urlConnection = (HttpURLConnection) url.openConnection();
            urlConnection.setRequestMethod("GET");

            urlConnection.setConnectTimeout(timeoutSocket);

            InputStream in = new BufferedInputStream(urlConnection.getInputStream());
            BufferedReader r = new BufferedReader(new InputStreamReader(in,"UTF-8"));
            StringBuilder total = new StringBuilder();
            String line;
            while ((line = r.readLine()) != null) {
                if (!(total.length() == 0 && line.trim().equals("")))
                    total.append(line);
            }
            in.close();
            r.close();
            return total.toString();
        }
        catch (Exception ex){
            ex.printStackTrace();
        }
        return null;
    }
}
