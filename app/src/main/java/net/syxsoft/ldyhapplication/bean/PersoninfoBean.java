package net.syxsoft.ldyhapplication.bean;

/**
 * Created by 谷永庆 on 2018/3/17.
 */

public class PersoninfoBean {


    /**
     * RequestCode : 200
     * ErrorMessage : null
     * SuccessInfo : {"PersonInfo":{"personId":"7bdc959c-c774-440a-a236-8064c54f4cbd","name":"gc","sex":"男","position":null,"political":"民盟盟员","Annualleave":0,"pInPartlyTime":null,"Tel":"110","QQ":null,"WeChat":null,"email":null,"headImg":null,"remark":"干部人员"},"depName":"遵义市"}
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
         * PersonInfo : {"personId":"7bdc959c-c774-440a-a236-8064c54f4cbd","name":"gc","sex":"男","position":null,"political":"民盟盟员","Annualleave":0,"pInPartlyTime":null,"Tel":"110","QQ":null,"WeChat":null,"email":null,"headImg":null,"remark":"干部人员"}
         * depName : 遵义市
         */

        private PersonInfoBean PersonInfo;
        private String depName;

        public PersonInfoBean getPersonInfo() {
            return PersonInfo;
        }

        public void setPersonInfo(PersonInfoBean PersonInfo) {
            this.PersonInfo = PersonInfo;
        }

        public String getDepName() {
            return depName;
        }

        public void setDepName(String depName) {
            this.depName = depName;
        }

        public static class PersonInfoBean {
            /**
             * personId : 7bdc959c-c774-440a-a236-8064c54f4cbd
             * name : gc
             * sex : 男
             * position : null
             * political : 民盟盟员
             * Annualleave : 0.0
             * pInPartlyTime : null
             * Tel : 110
             * QQ : null
             * WeChat : null
             * email : null
             * headImg : null
             * remark : 干部人员
             */

            private String personId;
            private String name;
            private String sex;
            private Object position;
            private String political;
            private double Annualleave;
            private Object pInPartlyTime;
            private String Tel;
            private Object QQ;
            private Object WeChat;
            private Object email;
            private Object headImg;
            private String remark;

            public String getPersonId() {
                return personId;
            }

            public void setPersonId(String personId) {
                this.personId = personId;
            }

            public String getName() {
                return name;
            }

            public void setName(String name) {
                this.name = name;
            }

            public String getSex() {
                return sex;
            }

            public void setSex(String sex) {
                this.sex = sex;
            }

            public Object getPosition() {
                return position;
            }

            public void setPosition(Object position) {
                this.position = position;
            }

            public String getPolitical() {
                return political;
            }

            public void setPolitical(String political) {
                this.political = political;
            }

            public double getAnnualleave() {
                return Annualleave;
            }

            public void setAnnualleave(double Annualleave) {
                this.Annualleave = Annualleave;
            }

            public Object getPInPartlyTime() {
                return pInPartlyTime;
            }

            public void setPInPartlyTime(Object pInPartlyTime) {
                this.pInPartlyTime = pInPartlyTime;
            }

            public String getTel() {
                return Tel;
            }

            public void setTel(String Tel) {
                this.Tel = Tel;
            }

            public Object getQQ() {
                return QQ;
            }

            public void setQQ(Object QQ) {
                this.QQ = QQ;
            }

            public Object getWeChat() {
                return WeChat;
            }

            public void setWeChat(Object WeChat) {
                this.WeChat = WeChat;
            }

            public Object getEmail() {
                return email;
            }

            public void setEmail(Object email) {
                this.email = email;
            }

            public Object getHeadImg() {
                return headImg;
            }

            public void setHeadImg(Object headImg) {
                this.headImg = headImg;
            }

            public String getRemark() {
                return remark;
            }

            public void setRemark(String remark) {
                this.remark = remark;
            }
        }
    }
}
