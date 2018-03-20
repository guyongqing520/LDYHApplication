package net.syxsoft.ldyhapplication.bean;

/**
 * Created by 谷永庆 on 2018/3/17.
 */

public class PersoninfoBean {

    /**
     * RequestCode : 200
     * ErrorMessage : null
     * SuccessInfo : {"PersonInfo":{"personId":"7bdc959c-c774-440a-a236-8064c54f4cbd","sex":"1","name":"gc","email":null,"cardId":"420624199302021215","pBirthday":"1111.11","homeTown":null,"headImg":null,"pInPartlyTime":null,"pJiGuan":"zhonguo","pMinZu":"白族","Political":"民盟盟员","pStartWork":null,"QQ":null,"Tel":"110","WeChat":null,"remark":"干部人员"},"depName":"遵义市"}
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
         * PersonInfo : {"personId":"7bdc959c-c774-440a-a236-8064c54f4cbd","sex":"1","name":"gc","email":null,"cardId":"420624199302021215","pBirthday":"1111.11","homeTown":null,"headImg":null,"pInPartlyTime":null,"pJiGuan":"zhonguo","pMinZu":"白族","Political":"民盟盟员","pStartWork":null,"QQ":null,"Tel":"110","WeChat":null,"remark":"干部人员"}
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
             * sex : 1
             * name : gc
             * email : null
             * cardId : 420624199302021215
             * pBirthday : 1111.11
             * homeTown : null
             * headImg : null
             * pInPartlyTime : null
             * pJiGuan : zhonguo
             * pMinZu : 白族
             * Political : 民盟盟员
             * pStartWork : null
             * QQ : null
             * Tel : 110
             * WeChat : null
             * remark : 干部人员
             */

            private String personId;
            private String sex;
            private String name;
            private Object email;
            private String cardId;
            private String pBirthday;
            private Object homeTown;
            private Object headImg;
            private Object pInPartlyTime;
            private String pJiGuan;
            private String pMinZu;
            private String Political;
            private Object pStartWork;
            private Object QQ;
            private String Tel;
            private Object WeChat;
            private String remark;

            public String getPersonId() {
                return personId;
            }

            public void setPersonId(String personId) {
                this.personId = personId;
            }

            public String getSex() {
                return sex;
            }

            public void setSex(String sex) {
                this.sex = sex;
            }

            public String getName() {
                return name;
            }

            public void setName(String name) {
                this.name = name;
            }

            public Object getEmail() {
                return email;
            }

            public void setEmail(Object email) {
                this.email = email;
            }

            public String getCardId() {
                return cardId;
            }

            public void setCardId(String cardId) {
                this.cardId = cardId;
            }

            public String getPBirthday() {
                return pBirthday;
            }

            public void setPBirthday(String pBirthday) {
                this.pBirthday = pBirthday;
            }

            public Object getHomeTown() {
                return homeTown;
            }

            public void setHomeTown(Object homeTown) {
                this.homeTown = homeTown;
            }

            public Object getHeadImg() {
                return headImg;
            }

            public void setHeadImg(Object headImg) {
                this.headImg = headImg;
            }

            public Object getPInPartlyTime() {
                return pInPartlyTime;
            }

            public void setPInPartlyTime(Object pInPartlyTime) {
                this.pInPartlyTime = pInPartlyTime;
            }

            public String getPJiGuan() {
                return pJiGuan;
            }

            public void setPJiGuan(String pJiGuan) {
                this.pJiGuan = pJiGuan;
            }

            public String getPMinZu() {
                return pMinZu;
            }

            public void setPMinZu(String pMinZu) {
                this.pMinZu = pMinZu;
            }

            public String getPolitical() {
                return Political;
            }

            public void setPolitical(String Political) {
                this.Political = Political;
            }

            public Object getPStartWork() {
                return pStartWork;
            }

            public void setPStartWork(Object pStartWork) {
                this.pStartWork = pStartWork;
            }

            public Object getQQ() {
                return QQ;
            }

            public void setQQ(Object QQ) {
                this.QQ = QQ;
            }

            public String getTel() {
                return Tel;
            }

            public void setTel(String Tel) {
                this.Tel = Tel;
            }

            public Object getWeChat() {
                return WeChat;
            }

            public void setWeChat(Object WeChat) {
                this.WeChat = WeChat;
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
