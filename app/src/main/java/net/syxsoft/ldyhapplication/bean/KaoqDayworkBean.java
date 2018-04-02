package net.syxsoft.ldyhapplication.bean;

import java.util.List;

/**
 * Created by 谷永庆 on 2018/3/21.
 */

public class KaoqDayworkBean {

    /**
     * RequestCode : 200
     * ErrorMessage : null
     * SuccessInfo : {"rows":[{"addTime":"2018-03-05 10:58","content":"测试","address":"办公室","id":"8d0fbac8-6f5a-429a-a36e-8ff66f47c2f9","time":"2018-03-05","type":"办公室业务"}],"total":1}
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
         * rows : [{"addTime":"2018-03-05 10:58","content":"测试","address":"办公室","id":"8d0fbac8-6f5a-429a-a36e-8ff66f47c2f9","time":"2018-03-05","type":"办公室业务"}]
         * total : 1
         */

        private int total;
        private List<RowsBean> rows;

        public int getTotal() {
            return total;
        }

        public void setTotal(int total) {
            this.total = total;
        }

        public List<RowsBean> getRows() {
            return rows;
        }

        public void setRows(List<RowsBean> rows) {
            this.rows = rows;
        }

        public static class RowsBean {
            /**
             * addTime : 2018-03-05 10:58
             * content : 测试
             * address : 办公室
             * id : 8d0fbac8-6f5a-429a-a36e-8ff66f47c2f9
             * time : 2018-03-05
             * type : 办公室业务
             */

            private String addTime;
            private String content;
            private String address;
            private String id;
            private String time;
            private String type;

            public String getAddTime() {
                return addTime;
            }

            public void setAddTime(String addTime) {
                this.addTime = addTime;
            }

            public String getContent() {
                return content;
            }

            public void setContent(String content) {
                this.content = content;
            }

            public String getAddress() {
                return address;
            }

            public void setAddress(String address) {
                this.address = address;
            }

            public String getId() {
                return id;
            }

            public void setId(String id) {
                this.id = id;
            }

            public String getTime() {
                return time;
            }

            public void setTime(String time) {
                this.time = time;
            }

            public String getType() {
                return type;
            }

            public void setType(String type) {
                this.type = type;
            }
        }
    }
}
