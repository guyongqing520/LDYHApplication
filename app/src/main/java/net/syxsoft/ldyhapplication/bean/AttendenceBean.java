package net.syxsoft.ldyhapplication.bean;

/**
 * Created by 谷永庆 on 2018/3/17.
 */

public class AttendenceBean {


    /**
     * RequestCode : 200
     * ErrorMessage : null
     * SuccessInfo : {"timeAreaId":"677eeff2-b72f-47b5-ac86-f6ac80c175ad","timeTitle":"第二段时间","isAtt":true,"resultMsg":"【签退】<br/>11:30--19:30","addressCenter":"遵义市"}
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
         * timeAreaId : 677eeff2-b72f-47b5-ac86-f6ac80c175ad
         * timeTitle : 第二段时间
         * isAtt : true
         * resultMsg : 【签退】<br/>11:30--19:30
         * addressCenter : 遵义市
         */

        private String timeAreaId;
        private String timeTitle;
        private boolean isAtt;
        private String resultMsg;
        private String addressCenter;

        public String getTimeAreaId() {
            return timeAreaId;
        }

        public void setTimeAreaId(String timeAreaId) {
            this.timeAreaId = timeAreaId;
        }

        public String getTimeTitle() {
            return timeTitle;
        }

        public void setTimeTitle(String timeTitle) {
            this.timeTitle = timeTitle;
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

        public String getAddressCenter() {
            return addressCenter;
        }

        public void setAddressCenter(String addressCenter) {
            this.addressCenter = addressCenter;
        }
    }
}
