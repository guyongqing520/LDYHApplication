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
import net.syxsoft.ldyhapplication.bean.KaoqMonthanalysisBean;
import net.syxsoft.ldyhapplication.callback.LoadCallBack;
import net.syxsoft.ldyhapplication.utils.DateUtils;
import net.syxsoft.ldyhapplication.utils.LunarDateUtils;
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

public class CalendarMonthanalysisAdapter extends RecyclerView.Adapter<CalendarMonthanalysisAdapter.ViewHolder> {

    private List<Integer> odays = new ArrayList<>();
    private List<Integer> days = new ArrayList<>();
    private Calendar toadyCalendar = Calendar.getInstance();
    private KaoqMonthanalysisBean kaoqMonthanalysisBean;
    private RecyclerView recylerviewdatedayanalysisView;
    private CalendarDayanalysisAdapter calendarDayanalysisAdapter;
    private Context context;
    private int year;
    private int month;
    private int today;
    private String userId;
    private int lastModthCount = 0;
    private ViewHolder lastholder;
    private int lastholderBg = 0;
    private TextView selectDate;


    static class ViewHolder extends RecyclerView.ViewHolder {
        View view;
        TextView name_yl;
        TextView name_nl;

        public ViewHolder(View view) {
            super(view);
            this.view = view;
            this.name_yl = view.findViewById(R.id.date_yl);
            this.name_nl = view.findViewById(R.id.date_nl);
        }
    }

    public CalendarMonthanalysisAdapter(Context context, int[][] days, int year, int month, int today,
                                        KaoqMonthanalysisBean kaoqMonthanalysisBean,
                                        RecyclerView recylerviewdatedayanalysisView, String userId, TextView selectDate) {
        this.context = context;
        this.today = today;
        this.odays.clear();
        this.days.clear();
        this.recylerviewdatedayanalysisView = recylerviewdatedayanalysisView;
        this.userId = userId;
        this.selectDate = selectDate;

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
        this.kaoqMonthanalysisBean = kaoqMonthanalysisBean;
    }

    @Override
    public ViewHolder onCreateViewHolder(final ViewGroup parent, int viewType) {

        if (context == null) {
            context = parent.getContext();
        }

        View view = LayoutInflater.from(context).inflate(R.layout.date_item, parent, false);
        final ViewHolder holder = new ViewHolder(view);

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
                    if (kaoqMonthanalysisBean != null) {
                        List<KaoqMonthanalysisBean.SuccessInfoBean> successInfoBeans = kaoqMonthanalysisBean.getSuccessInfo();

                        if (successInfoBeans.size() > 0) {

                            int count = position - lastModthCount;
                            if (count < 0 || count > successInfoBeans.size())
                                return;

                            KaoqMonthanalysisBean.SuccessInfoBean successInfoBean = successInfoBeans.get(count);
                            if (successInfoBean != null) {

                                //处理背景色

                                if (successInfoBean.getStatus() == 3) {//缺勤

                                    if (lastholder != null && lastholderBg != 0) {
                                        lastholder.view.setBackgroundResource(lastholderBg);
                                    }
                                    lastholder = holder;
                                    holder.view.setBackgroundResource(R.drawable.view_circle_ring_daka_today_fqq);
                                    lastholderBg = R.drawable.view_circle_fqq;

                                } else if (successInfoBean.getStatus() == 0) {//请假
                                    if (lastholder != null && lastholderBg != 0) {
                                        lastholder.view.setBackgroundResource(lastholderBg);
                                    }
                                    lastholder = holder;
                                    holder.view.setBackgroundResource(R.drawable.view_circle_ring_daka_today_qj);
                                    lastholderBg = R.drawable.view_circle_qj;

                                } else if (successInfoBean.getStatus() == 2) {//外勤
                                    if (lastholder != null && lastholderBg != 0) {
                                        lastholder.view.setBackgroundResource(lastholderBg);
                                    }
                                    lastholder = holder;
                                    holder.view.setBackgroundResource(R.drawable.view_circle_ring_daka_today_wq);
                                    lastholderBg = R.drawable.view_circle_wqqd;

                                } else if (successInfoBean.getStatus() == 1) {//正常签到
                                    if (lastholder != null && lastholderBg != 0) {
                                        lastholder.view.setBackgroundResource(lastholderBg);
                                    }
                                    lastholder = holder;
                                    holder.view.setBackgroundResource(R.drawable.view_circle_ring_daka_today_zc);
                                    lastholderBg = R.drawable.view_circle_zcqd;
                                }


                                if (successInfoBean.getStatus() == 1 || successInfoBean.getStatus() == 2) {

                                    if (successInfoBean.getDate() != null && successInfoBean.getDate().length() > 0) {
                                        selectDate.setText(getSelectDateTitle(successInfoBean.getDate()));
                                        initDayanalysis(successInfoBean.getDate());
                                    }
                                } else {
                                    MyToast.getInstance().show("没有任何考勤信息",context);
                                }
                            }
                        }
                    }
                }
            }
        });
        return holder;
    }

    //拉取个人考勤情况
    public void initDayanalysis(String dateStr) {

        //提交信息
        OkHttpManager.getInstance().getRequest(getRootApiUrl() + "/api/attendence/dayanalysis/" + userId + "/" + dateStr,
                new LoadCallBack<KaoqDayanalysisBean>(context) {

                    @Override
                    public void onSuccess(Call call, Response response, KaoqDayanalysisBean kaoqDayanalysisBean) {

                        if (kaoqDayanalysisBean.getRequestCode() == 200) {
                            List<KaoqDayanalysisBean.SuccessInfoBean> successInfoBeans = kaoqDayanalysisBean.getSuccessInfo();
                            if (successInfoBeans != null && successInfoBeans.size() > 0) {
                                calendarDayanalysisAdapter = new CalendarDayanalysisAdapter(context, successInfoBeans);
                                recylerviewdatedayanalysisView.setAdapter(calendarDayanalysisAdapter);
                                calendarDayanalysisAdapter.notifyDataSetChanged();
                            }
                        }
                    }
                });
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {

        holder.name_yl.setText(String.valueOf(days.get(position)));

        Calendar calendar = Calendar.getInstance();

        //将上个月的和下个月的设置为灰色
        int r = ContextCompat.getColor(context, R.color.colorbgzcc);//灰色
        int r1 = ContextCompat.getColor(context, R.color.colorbgnom);//蓝色


        if (position >= 0 && position < 7 && days.get(position) > 20) {

            lastModthCount++;
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

            if (kaoqMonthanalysisBean != null) {
                List<KaoqMonthanalysisBean.SuccessInfoBean> successInfoBeans = kaoqMonthanalysisBean.getSuccessInfo();

                if (successInfoBeans.size() > 0) {
                    for (KaoqMonthanalysisBean.SuccessInfoBean successInfoBean : successInfoBeans) {

                        Calendar calendar1 = Calendar.getInstance();
                        calendar1.set(year, month - 1, days.get(position));
                        String date = DateUtils.getSimpleDateFormat(calendar1.getTime(), null);

                        if (successInfoBean.getDate().trim().equals(date.trim())) {

                            if (successInfoBean.getStatus() == 3) {//缺勤
                                if (days.get(position) == today && year == toadyCalendar.get(Calendar.YEAR) && month == toadyCalendar.get(Calendar.MONTH) + 1) {
                                    holder.view.setBackgroundResource(R.drawable.view_circle_ring_daka_today_fqq);
                                    lastholder = holder;
                                } else {
                                    holder.view.setBackgroundResource(R.drawable.view_circle_fqq);
                                }
                                lastholderBg = R.drawable.view_circle_fqq;
                            } else if (successInfoBean.getStatus() == 0) {//请假
                                if (days.get(position) == today && year == toadyCalendar.get(Calendar.YEAR) && month == toadyCalendar.get(Calendar.MONTH) + 1) {
                                    holder.view.setBackgroundResource(R.drawable.view_circle_ring_daka_today_qj);
                                    lastholder = holder;
                                } else {
                                    holder.view.setBackgroundResource(R.drawable.view_circle_qj);
                                }
                                lastholderBg = R.drawable.view_circle_qj;
                            } else if (successInfoBean.getStatus() == 2) {//外勤
                                if (days.get(position) == today && year == toadyCalendar.get(Calendar.YEAR) && month == toadyCalendar.get(Calendar.MONTH) + 1) {
                                    holder.view.setBackgroundResource(R.drawable.view_circle_ring_daka_today_wq);
                                    lastholder = holder;
                                } else {
                                    holder.view.setBackgroundResource(R.drawable.view_circle_wqqd);
                                }
                                lastholderBg = R.drawable.view_circle_wqqd;
                            } else if (successInfoBean.getStatus() == 1) {//正常签到
                                if (days.get(position) == today && year == toadyCalendar.get(Calendar.YEAR) && month == toadyCalendar.get(Calendar.MONTH) + 1) {
                                    holder.view.setBackgroundResource(R.drawable.view_circle_ring_daka_today_zc);
                                    lastholder = holder;
                                } else {
                                    holder.view.setBackgroundResource(R.drawable.view_circle_zcqd);
                                }
                                lastholderBg = R.drawable.view_circle_zcqd;
                            } else if (successInfoBean.getStatus() == -1) {//非工作日又是今天
                                if (days.get(position) == today && year == toadyCalendar.get(Calendar.YEAR) && month == toadyCalendar.get(Calendar.MONTH) + 1) {
                                    holder.view.setBackgroundResource(R.drawable.view_circle_ring_daka_today);
                                    lastholder = holder;
                                }
                            }
                            break;
                        }
                    }
                }
            }

        }
    }

    @Override
    public int getItemCount() {
        return days.size();
    }

    public String getRootApiUrl() {
        return "http://ldyh.webapi.syxsoft.net:8801";
    }

    private String getSelectDateTitle(String dateStr) {

        Date date = DateUtils.strToDate(dateStr, null);
        String weekStr = DateUtils.dateToWeek(dateStr);
        dateStr = DateUtils.getSimpleDateFormat(date, "yyyy年MM月dd日");

        return dateStr + "（" + weekStr + "）";
    }
}
