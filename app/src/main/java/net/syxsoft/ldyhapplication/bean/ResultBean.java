package net.syxsoft.ldyhapplication.bean;

/**
 * Created by 谷永庆 on 2018/3/17.
 */

public class ResultBean {


    /**
     * RequestCode : 200
     * ErrorMessage : null
     * SuccessInfo : 操作成功
     */

    private int RequestCode;
    private Object ErrorMessage;
    private String SuccessInfo;

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

    public String getSuccessInfo() {
        return SuccessInfo;
    }

    public void setSuccessInfo(String SuccessInfo) {
        this.SuccessInfo = SuccessInfo;
    }
}
