package net.syxsoft.ldyhapplication.Adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import net.syxsoft.ldyhapplication.R;
import net.syxsoft.ldyhapplication.bean.AttendenceListBean;
import net.syxsoft.ldyhapplication.utils.DateUtils;


import java.util.Date;
import java.util.List;

/**
 * Created by gyq on 2018/2/28.
 */

public class AttendenceListAdapter extends RecyclerView.Adapter<AttendenceListAdapter.ViewHolder> {

    private Context context;
    private List<AttendenceListBean.SuccessInfoBean.RowsBean> rows;


    static class ViewHolder extends RecyclerView.ViewHolder {
        View view;
        TextView name;
        TextView address;


        public ViewHolder(View view) {
            super(view);
            this.view = view;
            this.name = view.findViewById(R.id.name);
            this.address = view.findViewById(R.id.address);
        }
    }

    public AttendenceListAdapter(Context context, List<AttendenceListBean.SuccessInfoBean.RowsBean> rows) {
        this.context = context;
        this.rows = rows;
    }

    @Override
    public ViewHolder onCreateViewHolder(final ViewGroup parent, int viewType) {

        if (context == null) {
            context = parent.getContext();
        }

        View view = LayoutInflater.from(context).inflate(R.layout.attendence_item_layout, parent, false);
        return new ViewHolder(view);
    }


    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {

        try {
            Date date = DateUtils.getDateGST(rows.get(position).getTime(), "yyyy-MM-dd'T'HH:mm");
            holder.name.setText(DateUtils.getSimpleDateFormat(date, "yyyy年MM月dd日 HH:mm"));
            holder.address.setText("签到地点：" + rows.get(position).getAddress());
        } catch (Exception ex) {
        }
    }

    @Override
    public int getItemCount() {
        return rows.size();
    }
}
