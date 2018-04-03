package net.syxsoft.ldyhapplication.Adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import net.syxsoft.ldyhapplication.R;
import net.syxsoft.ldyhapplication.bean.MainPanel;
import net.syxsoft.ldyhapplication.ui.AppBase.AppActivity;
import net.syxsoft.ldyhapplication.ui.complaint.NavigationListFragment;
import net.syxsoft.ldyhapplication.ui.superhandle.ListFragment;

import java.util.List;

/**
 * Created by gyq on 2018/2/28.
 */

public class MainAdapter extends RecyclerView.Adapter<MainAdapter.ViewHolder> {
    private Context mContext;

    private List<MainPanel> mainPanelList;

    static class  ViewHolder extends  RecyclerView.ViewHolder{
        View imageView;
        ImageView gnImage;
        TextView gnName;

        public ViewHolder(View view){
            super(view);
            imageView=view;
            gnImage=view.findViewById(R.id.gnImage);
            gnName =view.findViewById(R.id.gnName);
        }

    }

    public MainAdapter(List<MainPanel> mainPanelList){
        this.mainPanelList = mainPanelList;
    }

    @Override
    public ViewHolder onCreateViewHolder(final ViewGroup parent, int viewType) {

        if (mContext == null) {
            mContext = parent.getContext();
        }

        View view = LayoutInflater.from(mContext).inflate(R.layout.ngmian_item_layout, parent, false);
        final ViewHolder holder = new ViewHolder(view);

        holder.imageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                int ps = holder.getAdapterPosition();
                MainPanel mainPanel = mainPanelList.get(ps);

                String mainPanelText = mainPanel.getName();

                if (mainPanelText != null && mainPanelText.equals("考勤管理")) {
                    AppActivity appActivity=  (AppActivity)parent.getContext();
                    appActivity.pushFragment(new net.syxsoft.ldyhapplication.ui.attendance.NavigationListFragment());
                }
                else if (mainPanelText != null && mainPanelText.equals("日志上报")) {
                    AppActivity appActivity=  (AppActivity)parent.getContext();
                    appActivity.pushFragment(new net.syxsoft.ldyhapplication.ui.logReport.NavigationListFragment());
                }
                else if (mainPanelText != null && mainPanelText.equals("任务管理")) {
                    AppActivity appActivity=  (AppActivity)parent.getContext();
                    appActivity.pushFragment(new net.syxsoft.ldyhapplication.ui.mytask.NavigationListFragment());
                }
                else if (mainPanelText != null && mainPanelText.equals("督查督办")) {
                    AppActivity appActivity=  (AppActivity)parent.getContext();
                    appActivity.pushFragment(new ListFragment());
                }
                else if (mainPanelText != null && mainPanelText.equals("投诉举报")) {
                    AppActivity appActivity=  (AppActivity)parent.getContext();
                    appActivity.pushFragment(new NavigationListFragment());
                }
            }
        });

        return holder;

    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        MainPanel mainPanel = mainPanelList.get(position);
        holder.gnName.setText(mainPanel.getName());
        //holder.gnImage.setImageResource(mainPanel.getImageId());
        holder.gnImage.setImageResource(R.mipmap.kqgl01);
    }

    @Override
    public int getItemCount() {
        return mainPanelList.size();
    }
}
