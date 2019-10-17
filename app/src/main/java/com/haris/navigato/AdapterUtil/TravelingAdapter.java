package com.haris.navigato.AdapterUtil;

import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.haris.navigato.ObjectUtil.SpinnerItem;
import com.haris.navigato.R;
import com.haris.navigato.TextviewUtil.UbuntuMediumTextview;

import java.util.ArrayList;

/**
 * Created by hp on 5/28/2018.
 */

public class TravelingAdapter extends BaseAdapter {

    private ArrayList<SpinnerItem> objects = new ArrayList<>();
    private Context context;
    private LayoutInflater layoutInflater;

    public TravelingAdapter(Context context, ArrayList<SpinnerItem> objects) {
        this.context = context;
        this.objects = objects;
        this.layoutInflater = LayoutInflater.from(context);
    }

    @Override
    public int getCount() {
        return objects.size();
    }

    @Override
    public Object getItem(int position) {
        return getItemId(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder viewHolder = null;

        if (convertView == null) {
            convertView = layoutInflater.inflate(R.layout.traveling_spinner_item_layout, null);
            viewHolder = new ViewHolder(convertView);
            convertView.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) convertView.getTag();
        }

        if (objects.get(position).getPicture() != 0)
            viewHolder.imgObjective.setImageResource(objects.get(position).getPicture());

        viewHolder.txtObjective.setText(objects.get(position).getTitle());

        return convertView;
    }

    @Override
    public View getDropDownView(int position, View convertView, ViewGroup parent) {
        View view = super.getDropDownView(position, convertView, parent);

        TextView title = (TextView) view.findViewById(R.id.txt_objective);
        ImageView img = (ImageView) view.findViewById(R.id.img_objective);

        if (position == 0) {
            img.setPadding(2, 2, 2, 2);
            title.setTextColor(Color.GRAY);
            img.setColorFilter(Color.GRAY);

            //img.setVisibility(View.GONE);
        } else {
            //img.setVisibility(View.VISIBLE);
            img.setColorFilter(null);
            title.setTextColor(context.getResources().getColor(R.color.lightBlue));
        }


        return view;
    }

    protected class ViewHolder {
        private ImageView imgObjective;
        private UbuntuMediumTextview txtObjective;

        public ViewHolder(View view) {
            imgObjective = (ImageView) view.findViewById(R.id.img_objective);
            txtObjective = (UbuntuMediumTextview) view.findViewById(R.id.txt_objective);
        }
    }

}
