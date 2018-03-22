package net.syxsoft.ldyhapplication.bean;

/**
 * Created by Administrator on 2018/3/22.
 */

public class RzsbRztbBean {

    /**
     * RequestCode : 200
     * ErrorMessage :
     * SuccessInfo : 操作成功
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
