package net.syxsoft.ldyhapplication.bean;

import java.util.List;

/**
 * Created by 谷永庆 on 2018/3/26.
 */

public class SyskeyvalueBean {

    /**
     * RequestCode : 200
     * ErrorMessage : null
     * SuccessInfo : [{"text":"办公室业务","value":"0"},{"text":"会议","value":"1"},{"text":"调研","value":"2"},{"text":"培训","value":"3"},{"text":"出差","value":"4"},{"text":"考察","value":"5"}]
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
         * text : 办公室业务
         * value : 0
         */

        private String text;
        private String value;

        public String getText() {
            return text;
        }

        public void setText(String text) {
            this.text = text;
        }

        public String getValue() {
            return value;
        }

        public void setValue(String value) {
            this.value = value;
        }
    }
}
