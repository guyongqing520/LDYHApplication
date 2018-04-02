package net.syxsoft.ldyhapplication.Adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import net.syxsoft.ldyhapplication.R;
import net.syxsoft.ldyhapplication.bean.KaoqDayworkBean;


/**
 * Created by gyq on 2018/2/28.
 */

public class CalendarDayWorkdailyAdapter extends RecyclerView.Adapter<CalendarDayWorkdailyAdapter.ViewHolder> {

    private Context context;
    private KaoqDayworkBean.SuccessInfoBean successInfoBean;


    static class ViewHolder extends RecyclerView.ViewHolder {
        View view;
        TextView type;
        TextView time;
        TextView address;


        public ViewHolder(View view) {
            super(view);
            this.view = view;
            this.type = view.findViewById(R.id.type);
            this.time = view.findViewById(R.id.time);
            this.address = view.findViewById(R.id.address);
        }
    }

    public CalendarDayWorkdailyAdapter(Context context, KaoqDayworkBean.SuccessInfoBean successInfoBeans) {
        this.context = context;
        this.successInfoBean = successInfoBeans;
    }

    @Override
    public ViewHolder onCreateViewHolder(final ViewGroup parent, int viewType) {

        if (context == null) {
            context = parent.getContext();
        }

        View view = LayoutInflater.from(context).inflate(R.layout.date_daywork_item, parent, false);
        return new ViewHolder(view);
    }


    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {

        if (successInfoBean != null && successInfoBean.getRows().size() > 0) {
            holder.type.setText(successInfoBean.getRows().get(position).getContent());
            holder.address.setText(successInfoBean.getRows().get(position).getAddress());
            holder.time.setText(successInfoBean.getRows().get(position).getTime());
        }
    }

    @Override
    public int getItemCount() {
        return successInfoBean.getRows().size();
    }
}
