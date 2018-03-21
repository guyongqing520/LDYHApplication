package net.syxsoft.ldyhapplication.bean;

/**
 * Created by 谷永庆 on 2018/3/17.
 */

public class LoginBean {

    /**
     * RequestCode : 200
     * ErrorMessage : null
     * SuccessInfo : {"personId":"7bdc959c-c774-440a-a236-8064c54f4cbd","centerAddress":"遵义市"}
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
         * personId : 7bdc959c-c774-440a-a236-8064c54f4cbd
         * centerAddress : 遵义市
         */

        private String personId;
        private String centerAddress;

        public String getPersonId() {
            return personId;
        }

        public void setPersonId(String personId) {
            this.personId = personId;
        }

        public String getCenterAddress() {
            return centerAddress;
        }

        public void setCenterAddress(String centerAddress) {
            this.centerAddress = centerAddress;
        }
    }
}
