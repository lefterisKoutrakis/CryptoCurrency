package gr.kouto.cryptocurrency;

import android.app.Activity;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;


import gr.kouto.cryptocurrency.DetailCrypto.CryptoDetailActivity;
import gr.kouto.cryptocurrency.Object.CryptoCurrency;
import gr.kouto.cryptocurrency.Object.ListedCryptoCurrency;

public class ListAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder>{

    private Activity activity;
    private ListedCryptoCurrency listedCryptoCurrency;

    ListAdapter(Activity activity, ListedCryptoCurrency listedCryptoCurrency){
        this.activity = activity;
        this.listedCryptoCurrency = listedCryptoCurrency;
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
            view.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            Intent intent = new Intent(activity, CryptoDetailActivity.class);
            intent.putExtra("crypto", listedCryptoCurrency.get(getAdapterPosition()));
            activity.startActivity(intent);
        }
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View itemView = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.list_row,viewGroup,false);
        return new MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder viewHolder, int i) {
        CryptoCurrency cryptoCurrency = listedCryptoCurrency.get(viewHolder.getAdapterPosition());
        ((MyViewHolder)viewHolder).symbolName.setText(cryptoCurrency.getSymbol());
        ((MyViewHolder)viewHolder).fullName.setText(cryptoCurrency.getFullName());
        ((MyViewHolder)viewHolder).rate.setText(cryptoCurrency.getRate());

        GlideApp.with(activity)
                .load(cryptoCurrency.getIcon())
                .centerCrop()
                .dontAnimate()
                .into(((MyViewHolder)viewHolder).imageView);
    }

    @Override
    public int getItemViewType(int position) {
        return super.getItemViewType(position);
    }

    @Override
    public int getItemCount() {
        return listedCryptoCurrency.size();
    }

    public void setFilter(ListedCryptoCurrency filter){

        this.listedCryptoCurrency = filter;
        notifyDataSetChanged();
    }

    public void clear(){
        listedCryptoCurrency.clear();
    }
}
