package net.syxsoft.ldyhapplication.bean;

import java.util.List;

/**
 * Created by 谷永庆 on 2018/3/27.
 */

public class DirectleaderBean {

    /**
     * RequestCode : 200
     * ErrorMessage : null
     * SuccessInfo : [{"personid":"c7ae4a51-5099-4ab9-a8ad-15ba102becba","name":"宝宝8013","depname":"遵义市"}]
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
         * personid : c7ae4a51-5099-4ab9-a8ad-15ba102becba
         * name : 宝宝8013
         * depname : 遵义市
         */

        private String personid;
        private String name;
        private String depname;

        public String getPersonid() {
            return personid;
        }

        public void setPersonid(String personid) {
            this.personid = personid;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public String getDepname() {
            return depname;
        }

        public void setDepname(String depname) {
            this.depname = depname;
        }
    }
}
