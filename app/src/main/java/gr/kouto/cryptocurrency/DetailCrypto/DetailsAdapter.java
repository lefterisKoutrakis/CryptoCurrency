package gr.kouto.cryptocurrency.DetailCrypto;

import android.app.Activity;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import gr.kouto.cryptocurrency.Object.CryptoCurrency;
import gr.kouto.cryptocurrency.Object.ListedCryptoDetais;
import gr.kouto.cryptocurrency.R;

public class DetailsAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder>{

    private Activity activity;
    private ListedCryptoDetais listedCryptoDetais;

    DetailsAdapter(Activity activity, ListedCryptoDetais listedCryptoDetais){
        this.activity = activity;
        this.listedCryptoDetais = listedCryptoDetais;
    }

    protected class MyViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        TextView symbolName, rate, fullName;
        ImageView imageView;
        MyViewHolder(View view){
            super(view);
            symbolName = view.findViewById(R.id.cryptoTitle);
            rate = view.findViewById(R.id.cryptoRates);
            imageView = view.findViewById(R.id.cryptoLogo);
            fullName = view.findViewById(R.id.cryptoFullName);
            imageView.setVisibility(View.GONE);
            fullName.setVisibility(View.GONE);
            view.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            Intent intent = new Intent(activity, CryptoDetailActivity.class);
            intent.putExtra("crypto", (CryptoCurrency)listedCryptoDetais.get(getAdapterPosition()));
            activity.startActivity(intent);
        }
    }

    protected class MyViewDateHolder extends RecyclerView.ViewHolder{
        TextView date;
        MyViewDateHolder(View view){
            super(view);
            date = view.findViewById(R.id.dateTxt);
        }
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        if(i == 1) {
            View itemView = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.list_row, viewGroup, false);
            return new MyViewHolder(itemView);
        }
        else {
            View itemView = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.detail_row, viewGroup, false);
            return new MyViewDateHolder(itemView);
        }
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder viewHolder, int i) {
        if(viewHolder instanceof MyViewHolder) {
            CryptoCurrency cryptoCurrency = (CryptoCurrency) listedCryptoDetais.get(viewHolder.getAdapterPosition());
            ((MyViewHolder) viewHolder).symbolName.setText(cryptoCurrency.getSymbol());
            ((MyViewHolder) viewHolder).rate.setText(cryptoCurrency.getRate());
        }
        else{
            ((MyViewDateHolder)viewHolder).date.setText((String)listedCryptoDetais.get(viewHolder.getAdapterPosition()));
        }
    }

    @Override
    public int getItemViewType(int position) {
        if(listedCryptoDetais.get(position) instanceof CryptoCurrency)
            return 1;
        else
            return 2;
    }

    @Override
    public int getItemCount() {
        return listedCryptoDetais.size();
    }

}