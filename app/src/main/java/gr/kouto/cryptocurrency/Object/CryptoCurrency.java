package gr.kouto.cryptocurrency.Object;

import android.os.Parcel;
import android.os.Parcelable;

public class CryptoCurrency implements Parcelable {

    String symbol;
    String name;
    String fullName;
    String rate;
    String icon;

    public CryptoCurrency(){}

    protected CryptoCurrency(Parcel in) {
        symbol = in.readString();
        name = in.readString();
        fullName = in.readString();
        rate = in.readString();
        icon = in.readString();
    }

    public static final Creator<CryptoCurrency> CREATOR = new Creator<CryptoCurrency>() {
        @Override
        public CryptoCurrency createFromParcel(Parcel in) {
            return new CryptoCurrency(in);
        }

        @Override
        public CryptoCurrency[] newArray(int size) {
            return new CryptoCurrency[size];
        }
    };

    public void setSymbol(String value)     { symbol = value; }
    public void setName(String value)       { name = value; }
    public void setFullName(String  value)  { fullName = value; }
    public void setRate(String value)       { rate = value; }
    public void setIcon(String value)       { icon = value; }

    public String getSymbol()   { return symbol; }
    public String getName()     { return name; }
    public String getFullName() { return fullName; }
    public String getRate()     { return rate; }
    public String getIcon()     { return icon; }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(symbol);
        dest.writeString(name);
        dest.writeString(fullName);
        dest.writeString(rate);
        dest.writeString(icon);
    }
}
