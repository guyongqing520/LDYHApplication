package net.syxsoft.ldyhapplication.bean;

/**
 * Created by 谷永庆 on 2018/3/17.
 */

public class AttendenceBean {

    /**
     * RequestCode : 200
     * ErrorMessage : null
     * SuccessInfo : {"timeAreaId":"","isAtt":false,"resultMsg":"非考勤时间"}
     */

    private int RequestCode;
    private Object ErrorMessage;
    private SuccessInfoBean SuccessInfo;

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

    public SuccessInfoBean getSuccessInfo() {
        return SuccessInfo;
    }

    public void setSuccessInfo(SuccessInfoBean SuccessInfo) {
        this.SuccessInfo = SuccessInfo;
    }

    public static class SuccessInfoBean {
        /**
         * timeAreaId :
         * isAtt : false
         * resultMsg : 非考勤时间
         */

        private String timeAreaId;
        private boolean isAtt;
        private String resultMsg;

        public String getTimeAreaId() {
            return timeAreaId;
        }

        public void setTimeAreaId(String timeAreaId) {
            this.timeAreaId = timeAreaId;
        }

        public boolean isIsAtt() {
            return isAtt;
        }

        public void setIsAtt(boolean isAtt) {
            this.isAtt = isAtt;
        }

        public String getResultMsg() {
            return resultMsg;
        }

        public void setResultMsg(String resultMsg) {
            this.resultMsg = resultMsg;
        }
    }
}
