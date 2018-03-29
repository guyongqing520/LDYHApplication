package net.syxsoft.ldyhapplication.Adapter;

import android.content.Context;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import net.syxsoft.ldyhapplication.R;
import net.syxsoft.ldyhapplication.bean.AttendenceListBean;
import net.syxsoft.ldyhapplication.bean.LeavelistBean;
import net.syxsoft.ldyhapplication.utils.DateUtils;

import java.util.Date;
import java.util.List;

/**
 * Created by gyq on 2018/2/28.
 */

public class AttendenceListmyqijiaAdapter extends RecyclerView.Adapter<AttendenceListmyqijiaAdapter.ViewHolder> {

    private Context context;
    private List<LeavelistBean.SuccessInfoBean.RowsBean> rows;


    static class ViewHolder extends RecyclerView.ViewHolder {
        View view;
        TextView name;
        TextView starttime;
        TextView endtime;
        TextView state;


        public ViewHolder(View view) {
            super(view);
            this.view = view;
            this.name = view.findViewById(R.id.type);
            this.starttime = view.findViewById(R.id.time1);
            this.endtime = view.findViewById(R.id.time2);
            this.state = view.findViewById(R.id.state);

        }
    }

    public AttendenceListmyqijiaAdapter(Context context, List<LeavelistBean.SuccessInfoBean.RowsBean> rows) {
        this.context = context;
        this.rows = rows;
    }

    @Override
    public ViewHolder onCreateViewHolder(final ViewGroup parent, int viewType) {

        if (context == null) {
            context = parent.getContext();
        }

        View view = LayoutInflater.from(context).inflate(R.layout.myqinjia_item_layout, parent, false);
        return new ViewHolder(view);
    }


    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {

        try {

            holder.name.setText(rows.get(position).getType());
            holder.starttime.setText("开始时间：" + rows.get(position).getStart());
            holder.endtime.setText("结束时间：" + rows.get(position).getStart());

            holder.state.setText(rows.get(position).getStatustext());

            int status = rows.get(position).getStatus();

            switch (status) {
                case 0:
                    holder.state.setTextColor(ContextCompat.getColor(context, R.color.colorbgpink));
                    break;
                case 1:
                    holder.state.setTextColor(ContextCompat.getColor(context, R.color.colorbgpink1));
                    break;
                case 2:
                    holder.state.setTextColor(ContextCompat.getColor(context, R.color.colorbgpink2));
                    break;
            }


        } catch (Exception ex) {
        }
    }

    @Override
    public int getItemCount() {
        return rows.size();
    }
}
