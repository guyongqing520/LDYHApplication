package net.syxsoft.ldyhapplication.bean;

/**
 * Created by 谷永庆 on 2018/3/17.
 */

public class LoginBean {

    /**
     * RequestCode : 200
     * ErrorMessage :
     * SuccessInfo : 7bdc959c-c774-440a-a236-8064c54f4cbd
     */

    private int RequestCode;
    private String ErrorMessage;
    private String SuccessInfo;

    public int getRequestCode() {
        return RequestCode;
    }

    public void setRequestCode(int RequestCode) {
        this.RequestCode = RequestCode;
    }

    public String getErrorMessage() {
        return ErrorMessage;
    }

    public void setErrorMessage(String ErrorMessage) {
        this.ErrorMessage = ErrorMessage;
    }

    public String getSuccessInfo() {
        return SuccessInfo;
    }

    public void setSuccessInfo(String SuccessInfo) {
        this.SuccessInfo = SuccessInfo;
    }
}
