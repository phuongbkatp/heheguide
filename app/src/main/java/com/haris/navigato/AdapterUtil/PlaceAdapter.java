package com.haris.navigato.AdapterUtil;

import android.content.Context;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;

import com.haris.navigato.ObjectUtil.PlaceData;
import com.haris.navigato.R;
import com.haris.navigato.TextviewUtil.LemonMilkTextview;
import com.haris.navigato.TextviewUtil.UbuntuRegularTextview;

import java.util.ArrayList;

/**
 * Created by hp on 5/21/2018.
 */

public abstract class PlaceAdapter extends RecyclerView.Adapter {
    private Context context;
    private ArrayList<Object> placeArrays = new ArrayList<>();
    private ArrayList<Object> place = new ArrayList<>();
    private ArrayList<Object> history = new ArrayList<>();
    private int NO_INTERNET_VIEW = 1, AVAILABLE_DATA_VIEW = 2, ADVANCE_VIEW = 3;
    private boolean isAdvance;


    public PlaceAdapter(Context context, ArrayList<Object> placeArrays) {
        this.context = context;
        this.placeArrays = placeArrays;
    }

    public PlaceAdapter(Context context, ArrayList<Object> placeArrays, boolean isAdvance) {
        this.context = context;
        this.placeArrays = placeArrays;
        this.isAdvance = isAdvance;
    }

    @Override
    public int getItemViewType(int position) {
        if (isAdvance)
            return ADVANCE_VIEW;
        else if (placeArrays.get(position) instanceof PlaceHolder)
            return AVAILABLE_DATA_VIEW;
        else

            return AVAILABLE_DATA_VIEW;

    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        RecyclerView.ViewHolder viewHolder = null;


        if (viewType == AVAILABLE_DATA_VIEW) {

            View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.place_item_layout, parent, false);
            viewHolder = new PlaceHolder(view);

        } else if (viewType == ADVANCE_VIEW) {

            View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.advance_item_layout, parent, false);
            viewHolder = new AdvanceHolder(view);

        }


        return viewHolder;
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, final int position) {


        if (holder instanceof PlaceHolder) {

            final PlaceHolder placeHolder = (PlaceHolder) holder;
            PlaceData placeData = (PlaceData) placeArrays.get(position);

            placeHolder.txtPlaceName.setText(placeData.getPlaceName());
            placeHolder.imagePlace.setImageResource(placeData.getPlaceIcon());
            placeHolder.layoutPlace.setTag(position);
            placeHolder.layoutPlace.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    onFeaturePlaceSelect((Integer) placeHolder.layoutPlace.getTag());
                }
            });


        } else if (holder instanceof AdvanceHolder) {

            final AdvanceHolder advanceHolder = (AdvanceHolder) holder;
            PlaceData placeData = (PlaceData) placeArrays.get(position);

            advanceHolder.txtActivty.setText(placeData.getPlaceName());
            advanceHolder.imageActivityIcon.setImageResource(placeData.getPlaceIcon());
            advanceHolder.cardAdvance.setTag(position);

            advanceHolder.cardAdvance.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    int pos = (int) advanceHolder.cardAdvance.getTag();
                    onFeaturePlaceSelect(pos);
                }
            });

        }


    }

    @Override
    public int getItemCount() {
        return placeArrays.size();
    }

    public abstract void onFeaturePlaceSelect(int featurePlacePosition);

    protected class PlaceHolder extends RecyclerView.ViewHolder {
        private LemonMilkTextview txtPlaceName;
        private ImageView imagePlace;
        private RelativeLayout layoutPlace;

        public PlaceHolder(View view) {
            super(view);
            txtPlaceName = (LemonMilkTextview) view.findViewById(R.id.txt_place_name);
            imagePlace = (ImageView) view.findViewById(R.id.image_place);
            layoutPlace = (RelativeLayout) view.findViewById(R.id.layout_place);
        }
    }

    protected class AdvanceHolder extends RecyclerView.ViewHolder {
        private CardView cardAdvance;
        private ImageView imageActivityIcon;
        private UbuntuRegularTextview txtActivty;

        public AdvanceHolder(View view) {
            super(view);
            cardAdvance = (CardView) view.findViewById(R.id.card_advance);
            imageActivityIcon = (ImageView) view.findViewById(R.id.image_activity_icon);
            txtActivty = (UbuntuRegularTextview) view.findViewById(R.id.txt_activty);
        }
    }

    protected class EmptyHolder extends RecyclerView.ViewHolder {


        public EmptyHolder(View view) {
            super(view);

        }
    }


}
