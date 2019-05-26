package gr.kouto.cryptocurrency;

import android.app.Dialog;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SearchView;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.MenuItemCompat;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import java.lang.ref.WeakReference;
import android.text.format.DateFormat;

import java.util.Calendar;

import gr.kouto.cryptocurrency.NetData.CrypoCurrencyParse;
import gr.kouto.cryptocurrency.Object.CryptoCurrency;
import gr.kouto.cryptocurrency.Object.ListedCryptoCurrency;


public class MainActivity extends AppCompatActivity implements SwipeRefreshLayout.OnRefreshListener
                        , SearchView.OnQueryTextListener {
    public static long date = 0;
    ListAdapter mAdapter;
    Toolbar toolbar;
    RecyclerView recyclerView;
    AsyncInitializeData async;
    ListedCryptoCurrency listedCryptoCurrency;
    SwipeRefreshLayout refreshLayout;
    TextView dateLbl;
    ProgressBar progressBar;
    MenuItem searchItem;
    SearchView searchView;
    ListedCryptoCurrency filterList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        listedCryptoCurrency = new ListedCryptoCurrency();

        progressBar = findViewById(R.id.progressBar);
        toolbar = findViewById(R.id.toolbar);
        toolbar.setTitle(getString(R.string.app_name));
        toolbar.inflateMenu(R.menu.menu_for_search);

        initializeSearch();

        refreshLayout = findViewById(R.id.refresh);
        refreshLayout.setOnRefreshListener(this);
        dateLbl = findViewById(R.id.date);

        async = new AsyncInitializeData(this);
        async.execute();

        recyclerView = findViewById(R.id.recycler_view);
        recyclerView.setHasFixedSize(false);
        RecyclerView.LayoutManager mLayoutManager = new WrapContentLinearLayoutManager(this, 1, false);
        recyclerView.setLayoutManager(mLayoutManager);
        RecyclerView.ItemDecoration dividerItemDecoration = new DividerItemDecoration(10, "VERTICAL");
        recyclerView.addItemDecoration(dividerItemDecoration);
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        mAdapter = new ListAdapter(this, listedCryptoCurrency);
        recyclerView.setAdapter(mAdapter);

    }

    private void initializeSearch() {
        searchItem = toolbar.getMenu().findItem(R.id.search);
        searchView =(SearchView) MenuItemCompat.getActionView(searchItem);
        searchView.setOnQueryTextListener(this);
    }

    @Override
    public void onRefresh() {
        refreshLayout.setRefreshing(false);
        async = new AsyncInitializeData(this);
        async.execute();
    }

    @Override
    public boolean onQueryTextSubmit(String query) {
        filterList = new ListedCryptoCurrency();
        if (!query.isEmpty()) {
            for (CryptoCurrency cr : listedCryptoCurrency) {
                if (cr.getFullName().toUpperCase().contains(query.toUpperCase())
                        || cr.getSymbol().contains(query.toUpperCase()))
                    filterList.add(cr);
            }
            mAdapter.setFilter(filterList);
        }

        return true;
    }

    @Override
    public boolean onQueryTextChange(String newText) {
        if(newText.isEmpty())
            mAdapter.setFilter(listedCryptoCurrency);
        return false;
    }

    private static class AsyncInitializeData extends AsyncTask<Void,Void,ListedCryptoCurrency>{

        private WeakReference<MainActivity> mainActivity;
        private Dialog dialog;

        public AsyncInitializeData(MainActivity activity){
            this.mainActivity = new WeakReference<>(activity);
            this.dialog = new Dialog(this.mainActivity.get());
            this.dialog.setCancelable(false);
            View view = View.inflate(this.mainActivity.get(),R.layout.dialog_loading,null);
            this.dialog.setContentView(view);
            this.dialog.setTitle("LOADING");
        }

        @Override
        protected void onPreExecute() {

            super.onPreExecute();
            dialog.show();
        }

        @Override
        protected ListedCryptoCurrency doInBackground(Void... voids) {
            CrypoCurrencyParse crypoCurrencyParse = new CrypoCurrencyParse();
            ListedCryptoCurrency cryptoCurrencies = crypoCurrencyParse.list(mainActivity.get());
            cryptoCurrencies = crypoCurrencyParse.live(mainActivity.get(), cryptoCurrencies);
            return cryptoCurrencies;
        }


        @Override
        protected void onPostExecute(ListedCryptoCurrency listedCryptoCurrency) {
            super.onPostExecute(listedCryptoCurrency);
            dialog.dismiss();

            Calendar cal = Calendar.getInstance();
            cal.setTimeInMillis(date * 1000);
            String date = DateFormat.format("dd-MM-yyyy hh:mm:ss", cal).toString();

            this.mainActivity.get().dateLbl.setText(date);
            this.mainActivity.get().listedCryptoCurrency = listedCryptoCurrency;
            mainActivity.get().mAdapter.setFilter(listedCryptoCurrency);
        }

    }
}
