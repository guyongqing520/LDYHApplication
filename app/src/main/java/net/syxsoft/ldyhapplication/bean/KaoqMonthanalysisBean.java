package net.syxsoft.ldyhapplication.bean;

import java.util.List;

/**
 * Created by 谷永庆 on 2018/3/21.
 */

public class KaoqMonthanalysisBean {

    /**
     * RequestCode : 200
     * ErrorMessage : null
     * SuccessInfo : [{"status":3,"date":"2018-03-01","remark":"缺勤"},{"status":3,"date":"2018-03-02","remark":"缺勤"},{"status":3,"date":"2018-03-03","remark":"缺勤"},{"status":-1,"remark":"非工作日","date":"2018-03-04"},{"status":-1,"remark":"非工作日","date":"2018-03-05"},{"status":0,"remark":"请假","date":"2018-03-06"},{"status":0,"remark":"请假","date":"2018-03-07"},{"status":0,"remark":"请假","date":"2018-03-08"},{"status":3,"date":"2018-03-09","remark":"缺勤"},{"status":3,"date":"2018-03-10","remark":"缺勤"},{"status":-1,"remark":"非工作日","date":"2018-03-11"},{"status":3,"date":"2018-03-12","remark":"缺勤"},{"status":3,"date":"2018-03-13","remark":"缺勤"},{"status":3,"date":"2018-03-14","remark":"缺勤"},{"status":3,"date":"2018-03-15","remark":"缺勤"},{"status":3,"date":"2018-03-16","remark":"缺勤"},{"status":3,"date":"2018-03-17","remark":"缺勤"},{"status":-1,"remark":"非工作日","date":"2018-03-18"},{"status":-1,"remark":"非工作日","date":"2018-03-19"},{"status":2,"date":"2018-03-20","remark":"外勤"},{"status":3,"date":"2018-03-21","remark":"缺勤"},{"status":-2,"date":"2018-03-22","remark":"未到此考勤日"},{"status":-2,"date":"2018-03-23","remark":"未到此考勤日"},{"status":-2,"date":"2018-03-24","remark":"未到此考勤日"},{"status":-1,"remark":"非工作日","date":"2018-03-25"},{"status":-1,"remark":"非工作日","date":"2018-03-26"},{"status":-2,"date":"2018-03-27","remark":"未到此考勤日"},{"status":-2,"date":"2018-03-28","remark":"未到此考勤日"},{"status":-2,"date":"2018-03-29","remark":"未到此考勤日"},{"status":-2,"date":"2018-03-30","remark":"未到此考勤日"},{"status":-2,"date":"2018-03-31","remark":"未到此考勤日"}]
     */

    private int RequestCode;
    private Object ErrorMessage;
    private List<SuccessInfoBean> SuccessInfo;

    public int getRequestCode() {
        return RequestCode;
    }

    public void setRequestCode(int RequestCode) {
        this.RequestCode = RequestCode;
    }

    public Object getErrorMessage() {
        return ErrorMessage;
    }

    public void setErrorMessage(Object ErrorMessage) {
        this.ErrorMessage = ErrorMessage;
    }

    public List<SuccessInfoBean> getSuccessInfo() {
        return SuccessInfo;
    }

    public void setSuccessInfo(List<SuccessInfoBean> SuccessInfo) {
        this.SuccessInfo = SuccessInfo;
    }

    public static class SuccessInfoBean {
        /**
         * status : 3
         * date : 2018-03-01
         * remark : 缺勤
         */

        private int status;
        private String date;
        private String remark;

        public int getStatus() {
            return status;
        }

        public void setStatus(int status) {
            this.status = status;
        }

        public String getDate() {
            return date;
        }

        public void setDate(String date) {
            this.date = date;
        }

        public String getRemark() {
            return remark;
        }

        public void setRemark(String remark) {
            this.remark = remark;
        }
    }
}
