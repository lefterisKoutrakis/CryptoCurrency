package gr.kouto.cryptocurrency.NetData;

import android.app.Activity;

import org.json.JSONObject;

import java.util.Iterator;

import gr.kouto.cryptocurrency.MainActivity;
import gr.kouto.cryptocurrency.Object.CryptoCurrency;
import gr.kouto.cryptocurrency.Object.ListedCryptoCurrency;
import gr.kouto.cryptocurrency.Object.ListedCryptoDetais;
import gr.kouto.cryptocurrency.R;

public class CrypoCurrencyParse {

    private CryptoCurrency cryptoCurrency;
    private ListedCryptoCurrency listedCryptoCurrency;
    private ListedCryptoDetais listedCryptoDetais;

    public ListedCryptoCurrency list(Activity activity){
        listedCryptoCurrency = new ListedCryptoCurrency();
        String url = activity.getResources().getString(R.string.url_list)
                + "?"
                + activity.getResources().getString(R.string.access_key)
                + activity.getResources().getString(R.string.api_key);
        try {
            String readData = HttpsConnection.HttpsRequest(url,15000);
            System.out.println(readData);
            parseListNetData(readData);
        }catch (Exception ex){
            ex.printStackTrace();
        }
        return listedCryptoCurrency;
    }

    public ListedCryptoCurrency live(Activity activity, ListedCryptoCurrency listedCryptoCurrency){

        String url = activity.getResources().getString(R.string.url_live)
                + "?"
                + activity.getResources().getString(R.string.access_key)
                + activity.getResources().getString(R.string.api_key);

        try {
            String readData = HttpsConnection.HttpsRequest(url,15000);
            System.out.println(readData);
            parseLiveNetData(readData, listedCryptoCurrency);
        }catch (Exception ex){
            ex.printStackTrace();
        }
        return listedCryptoCurrency;
    }

    public ListedCryptoDetais getCryptoDetails(Activity activity,ListedCryptoDetais cryptoDetais, String...params){
        cryptoCurrency = new CryptoCurrency();
        listedCryptoDetais = cryptoDetais;
        String url = activity.getResources().getString(R.string.url)
                + params[0]
                + "?"
                + activity.getResources().getString(R.string.access_key)
                + activity.getResources().getString(R.string.api_key)
                +"&"
                + activity.getResources().getString(R.string.url_symbol_param)
                + params[1];

        try {
            String readData = HttpsConnection.HttpsRequest(url,15000);
            System.out.println(readData);
            parseTimeFrameNetData(readData);
        }catch (Exception ex){
            ex.printStackTrace();
        }
        return listedCryptoDetais;

    }

    private void parseListNetData(String readData) throws Exception{
        JSONObject obj = new JSONObject(readData);
        String getCrypto = obj.getString("crypto");
        obj = new JSONObject(getCrypto);
        Iterator<?> keys = obj.keys();
        while (keys.hasNext()){
            String key = (String)keys.next();
            if(obj.get(key) instanceof JSONObject){
                CryptoCurrency cryptoCurrency = new CryptoCurrency();
                cryptoCurrency.setSymbol(key);
                cryptoCurrency.setFullName(((JSONObject) obj.get(key)).getString("name_full"));
                cryptoCurrency.setName(((JSONObject) obj.get(key)).getString("name"));
                cryptoCurrency.setIcon(((JSONObject) obj.get(key)).getString("icon_url" +
                        ""));
                listedCryptoCurrency.add(cryptoCurrency);
            }

        }
    }

    private void parseLiveNetData(String readData, ListedCryptoCurrency listedCryptoCurrency) throws Exception{
        JSONObject obj = new JSONObject(readData);
        MainActivity.date = Long.parseLong(obj.getString("timestamp"));
        String getRates = obj.getString("rates");
        obj = new JSONObject(getRates);
        Iterator<?> keys = obj.keys();

        while (keys.hasNext()){
            String key = (String)keys.next();
            for(CryptoCurrency cr : listedCryptoCurrency){
                if(cr.getSymbol().equals(key))
                    cr.setRate(String.format("%f",Double.parseDouble(obj.getString(key))));
            }
        }
    }

    private void parseTimeFrameNetData(String readData) throws Exception{
        JSONObject obj = new JSONObject(readData);
        String getCrypto = obj.getString("rates");
        listedCryptoDetais.add(obj.getString("date"));
        obj = new JSONObject(getCrypto);
        Iterator<?> keys = obj.keys();
        while (keys.hasNext()){
            String key = (String)keys.next();
            CryptoCurrency cryptoCurrency = new CryptoCurrency();
            cryptoCurrency.setSymbol(key);
            cryptoCurrency.setRate(String.format("%f",Double.parseDouble(obj.getString(key))));
            listedCryptoDetais.add(cryptoCurrency);
        }
    }
}
