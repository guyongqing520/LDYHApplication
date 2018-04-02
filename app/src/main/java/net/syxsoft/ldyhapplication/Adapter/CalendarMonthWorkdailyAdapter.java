package net.syxsoft.ldyhapplication.Adapter;

import android.content.Context;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import net.syxsoft.ldyhapplication.R;
import net.syxsoft.ldyhapplication.bean.KaoqDayanalysisBean;
import net.syxsoft.ldyhapplication.bean.KaoqDayworkBean;
import net.syxsoft.ldyhapplication.bean.KaoqMonthanalysisBean;
import net.syxsoft.ldyhapplication.bean.KaoqMonthworkBean;
import net.syxsoft.ldyhapplication.callback.LoadCallBack;
import net.syxsoft.ldyhapplication.utils.DateUtils;
import net.syxsoft.ldyhapplication.utils.LunarDateUtils;
import net.syxsoft.ldyhapplication.utils.MyAlert;
import net.syxsoft.ldyhapplication.utils.MyToast;
import net.syxsoft.ldyhapplication.utils.OkHttpManager;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.Date;
import java.util.List;

import okhttp3.Call;
import okhttp3.Response;

/**
 * Created by gyq on 2018/2/28.
 */

public class CalendarMonthWorkdailyAdapter extends RecyclerView.Adapter<CalendarMonthWorkdailyAdapter.ViewHolder> {

    private List<Integer> odays = new ArrayList<>();
    private List<Integer> days = new ArrayList<>();
    private Calendar toadyCalendar = Calendar.getInstance();
    private KaoqMonthworkBean kaoqMonthworkBean;
    private RecyclerView recylerviewdateworkView;
    private CalendarDayWorkdailyAdapter calendarDayaworkAdapter;
    private Context context;
    private int year;
    private int month;
    private int today;
    private String userId;
    private CalendarMonthWorkdailyAdapter.ViewHolder lastholder;
    private int lastholderBg = 0;


    static class ViewHolder extends RecyclerView.ViewHolder {
        View view;
        TextView name_yl;
        TextView name_nl;
        Calendar date;
        int taskNum = 0;

        public ViewHolder(View view) {
            super(view);
            this.view = view;
            this.name_yl = view.findViewById(R.id.date_yl);
            this.name_nl = view.findViewById(R.id.date_nl);
        }
    }

    public CalendarMonthWorkdailyAdapter(Context context, int[][] days, int year, int month, int today,
                                         KaoqMonthworkBean kaoqMonthworkBean,
                                         RecyclerView recylerviewdatedayworkView, String userId) {
        this.context = context;
        this.today = today;
        this.odays.clear();
        this.days.clear();
        this.recylerviewdateworkView = recylerviewdatedayworkView;
        this.userId = userId;


        //将二维数组转化为一维数组，方便使用

        for (int i = 0; i < days.length; i++) {
            for (int j = 0; j < days[i].length; j++) {
                this.odays.add(days[i][j]);
            }
        }

        if (Collections.max(this.odays.subList(35, 41)) < 15) {
            this.days = this.odays.subList(0, 35);
        } else {
            this.days = this.odays;
        }

        this.year = year;
        this.month = month;
        this.kaoqMonthworkBean = kaoqMonthworkBean;
    }

    @Override
    public CalendarMonthWorkdailyAdapter.ViewHolder onCreateViewHolder(final ViewGroup parent, int viewType) {

        if (context == null) {
            context = parent.getContext();
        }

        View view = LayoutInflater.from(context).inflate(R.layout.date_item, parent, false);
        final CalendarMonthWorkdailyAdapter.ViewHolder holder = new CalendarMonthWorkdailyAdapter.ViewHolder(view);

        holder.view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                int position = holder.getAdapterPosition();

                if (position >= 0 && position < 7 && days.get(position) > 20) {
                    //上月
                } else if (position > 20 && days.get(position) < 15) {
                    //下月
                } else {
                    //本月
                    if (kaoqMonthworkBean != null && kaoqMonthworkBean.getSuccessInfo() != null && kaoqMonthworkBean.getSuccessInfo().size() > 0) {

                        if (holder.taskNum > 0) {
                            if (holder.date != null) {
                                String dateStr = DateUtils.getSimpleDateFormat(holder.date.getTime(), "yyyy-MM-dd");

                                for (KaoqMonthworkBean.SuccessInfoBean item : kaoqMonthworkBean.getSuccessInfo()) {
                                    if (item.getTime() != null && item.getTime().trim().equals(dateStr)) {
                                        if (lastholder != null && lastholderBg != 0) {
                                            lastholder.view.setBackgroundResource(lastholderBg);
                                        }
                                        lastholder = holder;
                                        holder.view.setBackgroundResource(R.drawable.view_circle_ring_daka_today_zc);
                                        lastholderBg = R.drawable.view_circle_zcqd;

                                        initDaywork(dateStr);
                                        return;
                                    }
                                }

                            }
                        }

                        MyToast.getInstance().show("没有任何考勤信息", context);

                    }
                }
            }
        });
        return holder;
    }

    //拉取列表
    public void initDaywork(String dateStr) {

        //提交信息
        String key="\"\"";
        String url = getRootApiUrl() + "/api/workdaily/list/" + userId + "/" + dateStr + "/" + dateStr + "/" + key + "/1/10";
        OkHttpManager.getInstance().getRequest(url, new LoadCallBack<KaoqDayworkBean>(context) {

            @Override
            public void onSuccess(Call call, Response response, KaoqDayworkBean kaoqDayworkBean) {

                if (kaoqDayworkBean.getRequestCode() == 200) {
                    KaoqDayworkBean.SuccessInfoBean successInfoBean = kaoqDayworkBean.getSuccessInfo();
                    if (kaoqDayworkBean != null && kaoqDayworkBean.getSuccessInfo() != null && kaoqDayworkBean.getSuccessInfo().getRows() != null && kaoqDayworkBean.getSuccessInfo().getRows().size() > 0) {
                        calendarDayaworkAdapter = new CalendarDayWorkdailyAdapter(context, successInfoBean);
                        recylerviewdateworkView.setAdapter(calendarDayaworkAdapter);
                        calendarDayaworkAdapter.notifyDataSetChanged();
                    }
                }
            }

        });
    }

    @Override
    public void onBindViewHolder(CalendarMonthWorkdailyAdapter.ViewHolder holder, int position) {

        holder.name_yl.setText(String.valueOf(days.get(position)));

        Calendar calendar = Calendar.getInstance();

        //将上个月的和下个月的设置为灰色
        int r = ContextCompat.getColor(context, R.color.colorbgzcc);//灰色
        int r1 = ContextCompat.getColor(context, R.color.colorbgnom);//蓝色


        if (position >= 0 && position < 7 && days.get(position) > 20) {

            calendar.set(year, month - 1, days.get(position));
            LunarDateUtils lunarDateUtils = new LunarDateUtils(calendar);
            String currentDate = lunarDateUtils.toString();
            int startIndex = currentDate.length() - 2;
            //获取农历的日
            String showDay = currentDate.substring(startIndex);

            holder.name_yl.setTextColor(r);
            holder.name_nl.setTextColor(r);
            holder.name_nl.setText(showDay);


        } else if (position > 20 && days.get(position) < 15) {

            calendar.set(year, month + 1, days.get(position));
            LunarDateUtils lunarDateUtils = new LunarDateUtils(calendar);
            String currentDate = lunarDateUtils.toString();
            int startIndex = currentDate.length() - 2;
            //获取农历的日
            String showDay = currentDate.substring(startIndex);

            holder.name_yl.setTextColor(r);
            holder.name_nl.setTextColor(r);
            holder.name_nl.setText(showDay);

        } else {
            calendar.set(year, month, days.get(position));
            LunarDateUtils lunarDateUtils = new LunarDateUtils(calendar);
            String currentDate = lunarDateUtils.toString();
            int startIndex = currentDate.length() - 2;
            //获取农历的日
            String showDay = currentDate.substring(startIndex);
            holder.name_nl.setText(showDay);


            if (position == 0 || position == 7 || position == 14 || position == 21 || position == 28 ||
                    position == 6 || position == 13 || position == 20 || position == 27 || position == 34) {
                holder.name_yl.setTextColor(r1);
            }

            if (kaoqMonthworkBean != null) {
                List<KaoqMonthworkBean.SuccessInfoBean> successInfoBeans = kaoqMonthworkBean.getSuccessInfo();

                if (successInfoBeans.size() > 0) {
                    for (KaoqMonthworkBean.SuccessInfoBean successInfoBean : successInfoBeans) {

                        Calendar calendar1 = Calendar.getInstance();
                        calendar1.set(year, month - 1, days.get(position));
                        String date = DateUtils.getSimpleDateFormat(calendar1.getTime(), null);

                        if (successInfoBean.getTime().trim().equals(date.trim())) {

                            if (successInfoBean.getNum() > 0) {//有任务
                                if (days.get(position) == today && year == toadyCalendar.get(Calendar.YEAR) && month == toadyCalendar.get(Calendar.MONTH) + 1) {
                                    holder.view.setBackgroundResource(R.drawable.view_circle_ring_daka_today_zc);
                                    lastholder = holder;
                                } else {
                                    holder.view.setBackgroundResource(R.drawable.view_circle_zcqd);
                                }
                                lastholderBg = R.drawable.view_circle_fqq;
                                holder.taskNum = successInfoBean.getNum();
                                holder.date = calendar1;
                            }

                            return;
                        }
                    }

                }
            }
            if (days.get(position) == today && year == toadyCalendar.get(Calendar.YEAR) && month == toadyCalendar.get(Calendar.MONTH) + 1){
                holder.view.setBackgroundResource(R.drawable.view_circle_ring_daka_today);
                lastholder = holder;
            }
            else{
                    holder.view.setBackgroundResource(R.drawable.view_circle_mxrz);
                }

        }
    }

    @Override
    public int getItemCount() {
        return days.size();
    }

    protected String getRootApiUrl() {
        return "http://ldyh.webapi.syxsoft.net:8801";
    }

}
