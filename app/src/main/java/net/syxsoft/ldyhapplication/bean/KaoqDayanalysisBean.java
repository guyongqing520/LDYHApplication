package net.syxsoft.ldyhapplication.bean;

import java.util.List;

/**
 * Created by 谷永庆 on 2018/3/21.
 */

public class KaoqDayanalysisBean {

    /**
     * RequestCode : 200
     * ErrorMessage : null
     * SuccessInfo : [{"name":"上午打卡09:57","attribute":"Web","address":"192.168.31.108"},{"name":"第二段时间11:44","attribute":"Web","address":"192.168.31.108"}]
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
         * name : 上午打卡09:57
         * attribute : Web
         * address : 192.168.31.108
         */

        private String name;
        private String attribute;
        private String address;

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public String getAttribute() {
            return attribute;
        }

        public void setAttribute(String attribute) {
            this.attribute = attribute;
        }

        public String getAddress() {
            return address;
        }

        public void setAddress(String address) {
            this.address = address;
        }
    }
}
