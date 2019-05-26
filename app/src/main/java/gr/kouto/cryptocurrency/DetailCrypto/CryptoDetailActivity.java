package gr.kouto.cryptocurrency.DetailCrypto;

import android.app.Dialog;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.RecyclerView;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;

import gr.kouto.cryptocurrency.DividerItemDecoration;
import gr.kouto.cryptocurrency.NetData.CrypoCurrencyParse;
import gr.kouto.cryptocurrency.Object.CryptoCurrency;
import gr.kouto.cryptocurrency.Object.ListedCryptoDetais;
import gr.kouto.cryptocurrency.R;
import gr.kouto.cryptocurrency.WrapContentLinearLayoutManager;

public class CryptoDetailActivity extends AppCompatActivity {

    Toolbar toolbar;
    RecyclerView recyclerView;
    DetailsAdapter mAdapter;
    ListedCryptoDetais cryptoDetais;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.detail_layout);
        CryptoCurrency cryptoCurrency = getIntent().getParcelableExtra("crypto");
        Date date = Calendar.getInstance().getTime();
        cryptoDetais = new ListedCryptoDetais();
        toolbar = findViewById(R.id.toolbar);
        recyclerView = findViewById(R.id.recycler);

        toolbar.setNavigationIcon(getResources().getDrawable(R.drawable.ic_arrow_back_black_24dp));
        toolbar.setNavigationOnClickListener(v -> CryptoDetailActivity.this.finish());

        recyclerView.setHasFixedSize(false);
        RecyclerView.LayoutManager mLayoutManager = new WrapContentLinearLayoutManager(this, 1, false);
        recyclerView.setLayoutManager(mLayoutManager);
        RecyclerView.ItemDecoration dividerItemDecoration = new DividerItemDecoration(10, "VERTICAL");
        recyclerView.addItemDecoration(dividerItemDecoration);
        recyclerView.setItemAnimator(new DefaultItemAnimator());

        Dialog dialog = new Dialog(this);
        dialog.setCancelable(false);
        View view = View.inflate(this,R.layout.dialog_loading,null);
        ((TextView)view.findViewById(R.id.dialogTxt)).setText("Downloading History...");
        dialog.setContentView(view);
        dialog.setTitle("Downloading History");
        dialog.show();
        new Thread(new Runnable() {
            @Override
            public void run() {
                for(int i = 0; i <= 30; i++) {
                    Date newDate = removeDays(date,i);
                    SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
                    CrypoCurrencyParse parse = new CrypoCurrencyParse();
                    cryptoDetais =  parse.getCryptoDetails(CryptoDetailActivity.this,cryptoDetais,
                            format.format(newDate), cryptoCurrency.getSymbol());
                }
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        mAdapter = new DetailsAdapter(CryptoDetailActivity.this, cryptoDetais );
                        recyclerView.setAdapter(mAdapter);
                        dialog.dismiss();
                    }
                });
            }
        }).start();
    }

    public static Date removeDays(Date date, int days) {
        GregorianCalendar cal = new GregorianCalendar();
        cal.setTime(date);
        cal.add(Calendar.DATE, -days);

        return cal.getTime();
    }
}
