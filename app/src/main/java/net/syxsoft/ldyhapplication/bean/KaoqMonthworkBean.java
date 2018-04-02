package net.syxsoft.ldyhapplication.bean;

import java.util.List;

/**
 * Created by 谷永庆 on 2018/3/21.
 */

public class KaoqMonthworkBean {


    /**
     * RequestCode : 200
     * ErrorMessage : null
     * SuccessInfo : [{"time":"2018-03-29","num":12},{"time":"2018-03-06","num":1},{"time":"2018-03-05","num":1}]
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
         * time : 2018-03-29
         * num : 12
         */

        private String time;
        private int num;

        public String getTime() {
            return time;
        }

        public void setTime(String time) {
            this.time = time;
        }

        public int getNum() {
            return num;
        }

        public void setNum(int num) {
            this.num = num;
        }
    }
}
