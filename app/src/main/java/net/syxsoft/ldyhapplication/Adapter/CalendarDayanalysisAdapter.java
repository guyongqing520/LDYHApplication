package net.syxsoft.ldyhapplication.Adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import net.syxsoft.ldyhapplication.R;
import net.syxsoft.ldyhapplication.bean.KaoqDayanalysisBean;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by gyq on 2018/2/28.
 */

public class CalendarDayanalysisAdapter extends RecyclerView.Adapter<CalendarDayanalysisAdapter.ViewHolder> {

    private Context context;
    private List<KaoqDayanalysisBean.SuccessInfoBean> successInfoBeans;


    static class ViewHolder extends RecyclerView.ViewHolder {
        View view;
        TextView name;
        TextView attribute;
        TextView address;


        public ViewHolder(View view) {
            super(view);
            this.view = view;
            this.name = view.findViewById(R.id.qdsj);
            this.attribute = view.findViewById(R.id.state_text);
            this.address = view.findViewById(R.id.adress_text);
        }
    }

    public CalendarDayanalysisAdapter(Context context, List<KaoqDayanalysisBean.SuccessInfoBean> successInfoBeans) {
        this.context = context;
        this.successInfoBeans = successInfoBeans;
    }

    @Override
    public ViewHolder onCreateViewHolder(final ViewGroup parent, int viewType) {

        if (context == null) {
            context = parent.getContext();
        }

        View view = LayoutInflater.from(context).inflate(R.layout.date_dayanalysis_item, parent, false);
        return new ViewHolder(view);
    }


    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {

        holder.name.setText(successInfoBeans.get(position).getName());
        holder.address.setText(successInfoBeans.get(position).getAddress());
        holder.attribute.setText(successInfoBeans.get(position).getAttribute());

        //设置灰色
        //int r = ContextCompat.getColor(context, R.color.colorbgzcc);//灰色
        //holder.view.setBackgroundResource(R.drawable.view_circle_ring_daka_today_qj);

    }

    @Override
    public int getItemCount() {
        return successInfoBeans.size();
    }
}
