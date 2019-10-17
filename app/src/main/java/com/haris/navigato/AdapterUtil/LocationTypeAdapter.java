package com.haris.navigato.AdapterUtil;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import com.haris.navigato.ConstantUtil.Constant;
import com.haris.navigato.ObjectUtil.LocationTypeObject;
import com.haris.navigato.R;
import com.haris.navigato.TextviewUtil.UbuntuRegularTextview;

import java.util.ArrayList;

/**
 * Created by hp on 5/24/2018.
 */

public abstract class LocationTypeAdapter extends RecyclerView.Adapter {
    private Context context;
    private ArrayList<Object> locationArrays = new ArrayList<>();
    private int NO_INTERNET_VIEW = 1, PLACES_VIEW = 2;


    public LocationTypeAdapter(Context context, ArrayList<Object> locationArrays) {
        this.context = context;
        this.locationArrays = locationArrays;
    }

    @Override
    public int getItemViewType(int position) {

        if (locationArrays.get(position) instanceof LocationTypeObject)
            return PLACES_VIEW;

        return PLACES_VIEW;

    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        RecyclerView.ViewHolder viewHolder = null;

        if (viewType == PLACES_VIEW) {

            View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.places_row_item_layout, parent, false);
            viewHolder = new LocationHolder(view);

        }


        return viewHolder;
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {


        if (holder instanceof LocationHolder) {

            final LocationHolder locationHolder = (LocationHolder) holder;
            LocationTypeObject locationTypeObject = (LocationTypeObject) locationArrays.get(position);

            locationHolder.locationTypeName.setText(locationTypeObject.getPlaceName());
            locationHolder.layoutLocation.setTag(position);
            locationHolder.layoutLocation.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    int pos = (int) locationHolder.layoutLocation.getTag();
                    onLocationSelection(pos);
                }
            });


        } else if (holder instanceof EmptyHolder) {

            EmptyHolder emptyHolder = (EmptyHolder) holder;


        }


    }

    @Override
    public int getItemCount() {
        return locationArrays.size();
    }


    public abstract void onLocationSelection(int position);


    protected class LocationHolder extends RecyclerView.ViewHolder {
        private LinearLayout layoutLocation;
        private UbuntuRegularTextview locationTypeName;

        public LocationHolder(View view) {
            super(view);
            layoutLocation = (LinearLayout) view.findViewById(R.id.layout_location);
            locationTypeName = (UbuntuRegularTextview) view.findViewById(R.id.location_type_name);
        }
    }

    protected class EmptyHolder extends RecyclerView.ViewHolder {


        public EmptyHolder(View view) {
            super(view);

        }
    }


}
