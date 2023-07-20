package com.test.app.LoadMaps.view.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.test.app.LoadMaps.data.dataSets.CountriesApi;
import com.test.app.R;

import java.util.ArrayList;

public class CountiresListAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    private ArrayList<CountriesApi> mCountryList;
    private Context context;
    public CountiresListAdapter(ArrayList<CountriesApi> countryList,Context context) {
        this.mCountryList = countryList;
        this.context = context;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        if (viewType == 2) {
            return new CountiresListAdapter.CountryViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.country_details, parent, false));
        } else {
            return new CountiresListAdapter.EmptyViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.empty_view, parent, false));
        }
    }

    @Override
    public void onBindViewHolder(final RecyclerView.ViewHolder holder, final int position) {

        if (holder.getItemViewType() == 2) {
            CountiresListAdapter.CountryViewHolder countryViewHolder = (CountryViewHolder) holder;
            countryViewHolder.tv_common_name.setText(mCountryList.get(position).getName().getCommon());
            countryViewHolder.tv_official_name.setText(mCountryList.get(position).getName().getOfficial());
            Glide.with(context).load(mCountryList.get(position).getFlags().getPng_url()).into(countryViewHolder.iv_icon);
        } else{
            CountiresListAdapter.EmptyViewHolder emptyViewHolder = (CountiresListAdapter.EmptyViewHolder) holder;
            emptyViewHolder.tv_Empty.setText("No Data Found!!");
        }

    }

    @Override
    public int getItemViewType(int position) {
        if (mCountryList != null) {
            if (mCountryList.size() > 0) {
                return 2;
            } else {
                return 1;
            }
        } else {
            return 1;
        }
    }

    @Override
    public int getItemCount() {
        if (mCountryList != null) {
            if (mCountryList.size() > 0) {
                return mCountryList.size();
            } else {
                return 1;
            }
        } else {
            return 1;
        }
    }

    public class CountryViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        TextView tv_common_name,tv_official_name;
        ImageView iv_icon;
        CardView layout_country_detail_holder;

        public CountryViewHolder(View itemView) {
            super(itemView);

            tv_common_name = (TextView) itemView.findViewById(R.id.tv_common_name);
            tv_official_name = (TextView) itemView.findViewById(R.id.tv_official_name);
            iv_icon = itemView.findViewById(R.id.iv_icon);
            layout_country_detail_holder = itemView.findViewById(R.id.layout_country_detail_holder);
            layout_country_detail_holder.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            if(v.getId()==R.id.layout_country_detail_holder){
                //mPresenter.redirectCountryDetails(mCountryList.get(getAdapterPosition()).getName().getCommon());
            }
        }
    }

    public class EmptyViewHolder extends RecyclerView.ViewHolder {
        private TextView tv_Empty;

        EmptyViewHolder(View itemView) {
            super(itemView);
            tv_Empty = itemView.findViewById(R.id.tv_empty);
        }
    }
}
