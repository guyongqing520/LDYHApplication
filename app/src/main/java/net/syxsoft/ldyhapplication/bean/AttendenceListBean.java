package net.syxsoft.ldyhapplication.bean;

import java.util.List;

/**
 * Created by 谷永庆 on 2018/3/17.
 */

public class AttendenceListBean {


    /**
     * RequestCode : 200
     * ErrorMessage : null
     * SuccessInfo : {"rows":[{"name":"第二段时间","time":"2018-03-20T11:44:00","type":"Web","address":"192.168.31.108","remark":null},{"name":"上午打卡","time":"2018-03-20T09:57:00","type":"Web","address":"192.168.31.108","remark":null}],"total":2}
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
         * rows : [{"name":"第二段时间","time":"2018-03-20T11:44:00","type":"Web","address":"192.168.31.108","remark":null},{"name":"上午打卡","time":"2018-03-20T09:57:00","type":"Web","address":"192.168.31.108","remark":null}]
         * total : 2
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
             * name : 第二段时间
             * time : 2018-03-20T11:44:00
             * type : Web
             * address : 192.168.31.108
             * remark : null
             */

            private String name;
            private String time;
            private String type;
            private String address;
            private Object remark;

            public String getName() {
                return name;
            }

            public void setName(String name) {
                this.name = name;
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

            public String getAddress() {
                return address;
            }

            public void setAddress(String address) {
                this.address = address;
            }

            public Object getRemark() {
                return remark;
            }

            public void setRemark(Object remark) {
                this.remark = remark;
            }
        }
    }
}

