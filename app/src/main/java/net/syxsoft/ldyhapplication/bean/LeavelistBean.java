package net.syxsoft.ldyhapplication.bean;

import java.util.List;

/**
 * Created by 谷永庆 on 2018/3/28.
 */

public class LeavelistBean {

    /**
     * RequestCode : 200
     * ErrorMessage : null
     * SuccessInfo : {"rows":[{"Id":"362eb15f-bb07-4f47-8035-ae6b1078bbfc","type":"病假","start":"2018-03-05","end":"2018-03-08","addtime":"2018-03-05","longtime":1.2,"reason":"份饭","Status":"通过","pname":"审核人：gc"},{"Id":"f779745c-365d-48b9-b7cd-6a6c23ae6fc2","type":"病假","start":"2018-03-05","end":"2018-03-06","addtime":"2018-03-05","longtime":0,"reason":"测试","Status":"未审批","pname":"审核人：gc"}],"total":2}
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
         * rows : [{"Id":"362eb15f-bb07-4f47-8035-ae6b1078bbfc","type":"病假","start":"2018-03-05","end":"2018-03-08","addtime":"2018-03-05","longtime":1.2,"reason":"份饭","Status":"通过","pname":"审核人：gc"},{"Id":"f779745c-365d-48b9-b7cd-6a6c23ae6fc2","type":"病假","start":"2018-03-05","end":"2018-03-06","addtime":"2018-03-05","longtime":0,"reason":"测试","Status":"未审批","pname":"审核人：gc"}]
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
             * Id : 362eb15f-bb07-4f47-8035-ae6b1078bbfc
             * type : 病假
             * start : 2018-03-05
             * end : 2018-03-08
             * addtime : 2018-03-05
             * longtime : 1.2
             * reason : 份饭
             * Status : 通过
             * pname : 审核人：gc
             */

            private String Id;
            private String type;
            private String start;
            private String end;
            private String addtime;
            private double longtime;
            private String reason;
            private String Status;
            private String pname;

            public String getId() {
                return Id;
            }

            public void setId(String Id) {
                this.Id = Id;
            }

            public String getType() {
                return type;
            }

            public void setType(String type) {
                this.type = type;
            }

            public String getStart() {
                return start;
            }

            public void setStart(String start) {
                this.start = start;
            }

            public String getEnd() {
                return end;
            }

            public void setEnd(String end) {
                this.end = end;
            }

            public String getAddtime() {
                return addtime;
            }

            public void setAddtime(String addtime) {
                this.addtime = addtime;
            }

            public double getLongtime() {
                return longtime;
            }

            public void setLongtime(double longtime) {
                this.longtime = longtime;
            }

            public String getReason() {
                return reason;
            }

            public void setReason(String reason) {
                this.reason = reason;
            }

            public String getStatus() {
                return Status;
            }

            public void setStatus(String Status) {
                this.Status = Status;
            }

            public String getPname() {
                return pname;
            }

            public void setPname(String pname) {
                this.pname = pname;
            }
        }
    }
}
