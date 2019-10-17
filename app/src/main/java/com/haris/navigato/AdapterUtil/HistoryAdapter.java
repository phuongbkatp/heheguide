package com.haris.navigato.AdapterUtil;

import android.content.Context;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.haris.navigato.ConstantUtil.Constant;
import com.haris.navigato.ObjectUtil.ErrorObject;
import com.haris.navigato.ObjectUtil.HistoryQueryObject;
import com.haris.navigato.R;
import com.haris.navigato.TextviewUtil.UbuntuMediumTextview;
import com.haris.navigato.TextviewUtil.UbuntuRegularTextview;

import java.util.ArrayList;

/**
 * Created by hp on 6/1/2018.
 */

public abstract class HistoryAdapter extends RecyclerView.Adapter {
    private Context context;
    private ArrayList<Object> historyArrays = new ArrayList<>();
    private ArrayList<Object> history = new ArrayList<>();
    private int NO_INTERNET_VIEW = 1, AVAILABLE_DATA_VIEW = 2;


    public HistoryAdapter(Context context, ArrayList<Object> historyArrays) {
        this.context = context;
        this.historyArrays = historyArrays;
    }

    @Override
    public int getItemViewType(int position) {
        if (historyArrays.get(position) instanceof ErrorObject)
            return NO_INTERNET_VIEW;
        else if (historyArrays.get(position) instanceof HistoryQueryObject)
            return AVAILABLE_DATA_VIEW;

        return NO_INTERNET_VIEW;

    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        RecyclerView.ViewHolder viewHolder = null;

        if (viewType == NO_INTERNET_VIEW) {

            View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.history_empty_state, parent, false);
            viewHolder = new EmptyHolder(view);

        } else if (viewType == AVAILABLE_DATA_VIEW) {

            View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.history_item_layout, parent, false);
            viewHolder = new HistoryHolder(view);

        }


        return viewHolder;
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {


        if (holder instanceof HistoryHolder) {

            final HistoryHolder emptyHolder = (HistoryHolder) holder;
            HistoryQueryObject historyQueryObject = (HistoryQueryObject) historyArrays.get(position);

            emptyHolder.labelFrom.setText(Constant.MenuText.FROM_LABEL);
            emptyHolder.labelTo.setText(Constant.MenuText.TO_LABEL);
            emptyHolder.labelDistance.setText(Constant.MenuText.DISTANCE_LABEL);
            emptyHolder.labelTime.setText(Constant.MenuText.TIME_LABEL);
            emptyHolder.labelPrice.setText(Constant.MenuText.PETROL_LABEL);
            emptyHolder.txtRoute.setText(Constant.MenuText.ROUTE_LABEL);

            emptyHolder.txtRoute.setText(historyQueryObject.getRouteName());
            emptyHolder.txtConveyance.setText(historyQueryObject.getConveyanceName());
            emptyHolder.txtFrom.setText(historyQueryObject.getSourceName());
            emptyHolder.txtTo.setText(historyQueryObject.getDestinationName());
            emptyHolder.txtDistance.setText(historyQueryObject.getDistance());
            emptyHolder.txtTime.setText(historyQueryObject.getDuration());
            emptyHolder.txtPrice.setText(historyQueryObject.getPetrol());

            emptyHolder.cardHistory.setTag(position);
            emptyHolder.cardHistory.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    int pos = (int) emptyHolder.cardHistory.getTag();
                    onItemSelected(pos);
                }
            });


        } else if (holder instanceof EmptyHolder) {

            EmptyHolder emptyHolder = (EmptyHolder) holder;
            ErrorObject errorObject = (ErrorObject) historyArrays.get(position);

            emptyHolder.txtErrorTitle.setText(errorObject.getTitle());
            emptyHolder.txtErrorTagline.setText(errorObject.getTagline());
            emptyHolder.imageEmpty.setImageResource(errorObject.getErrorImage());

        }


    }

    @Override
    public int getItemCount() {
        return historyArrays.size();
    }


    public abstract void onItemSelected(int position);


    protected class HistoryHolder extends RecyclerView.ViewHolder {
        private CardView cardHistory;
        private UbuntuMediumTextview txtRoute;
        private UbuntuMediumTextview txtConveyance;
        private UbuntuRegularTextview labelFrom;
        private UbuntuMediumTextview txtFrom;
        private UbuntuRegularTextview labelTo;
        private UbuntuMediumTextview txtTo;
        private UbuntuMediumTextview txtDistance;
        private UbuntuRegularTextview labelDistance;
        private UbuntuMediumTextview txtTime;
        private UbuntuRegularTextview labelTime;
        private UbuntuMediumTextview txtPrice;
        private UbuntuRegularTextview labelPrice;

        public HistoryHolder(View view) {
            super(view);

            cardHistory = (CardView) view.findViewById(R.id.card_history);
            txtRoute = (UbuntuMediumTextview) view.findViewById(R.id.txt_route);
            txtConveyance = (UbuntuMediumTextview) view.findViewById(R.id.txt_conveyance);
            labelFrom = (UbuntuRegularTextview) view.findViewById(R.id.label_from);
            txtFrom = (UbuntuMediumTextview) view.findViewById(R.id.txt_from);
            labelTo = (UbuntuRegularTextview) view.findViewById(R.id.label_to);
            txtTo = (UbuntuMediumTextview) view.findViewById(R.id.txt_to);
            txtDistance = (UbuntuMediumTextview) view.findViewById(R.id.txt_distance);
            labelDistance = (UbuntuRegularTextview) view.findViewById(R.id.label_distance);
            txtTime = (UbuntuMediumTextview) view.findViewById(R.id.txt_time);
            labelTime = (UbuntuRegularTextview) view.findViewById(R.id.label_time);
            txtPrice = (UbuntuMediumTextview) view.findViewById(R.id.txt_price);
            labelPrice = (UbuntuRegularTextview) view.findViewById(R.id.label_price);

        }
    }

    protected class EmptyHolder extends RecyclerView.ViewHolder {
        private LinearLayout layoutNotesState;
        private ImageView imageEmpty;
        private TextView txtErrorTitle;
        private UbuntuRegularTextview txtErrorTagline;

        public EmptyHolder(View view) {
            super(view);
            layoutNotesState = (LinearLayout) view.findViewById(R.id.layout_notes_state);
            imageEmpty = (ImageView) view.findViewById(R.id.image_empty);
            txtErrorTitle = (TextView) view.findViewById(R.id.txt_error_title);
            txtErrorTagline = (UbuntuRegularTextview) view.findViewById(R.id.txt_error_tagline);
        }
    }


}
