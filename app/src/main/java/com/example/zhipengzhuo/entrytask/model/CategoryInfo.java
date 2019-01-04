package com.example.zhipengzhuo.entrytask.model;

import java.util.List;


// for test
@Deprecated
public class CategoryInfo {
    private int code;
    private String message;
    private DataBean data;

    public static class DataBean {
        private List<RealCategoryInfo> list;

        public static class RealCategoryInfo {
            private String fcover_img_url_pc;
            private String ftitle;

            public String getFcover_img_url_pc() {
                return fcover_img_url_pc;
            }

            public void setFcover_img_url_pc(String fcover_img_url_pc) {
                this.fcover_img_url_pc = fcover_img_url_pc;
            }

            public String getFtitle() {
                return ftitle;
            }

            public void setFtitle(String ftitle) {
                this.ftitle = ftitle;
            }
        }

        public List<RealCategoryInfo> getList() {
            return list;
        }

        public void setList(List<RealCategoryInfo> list) {
            this.list = list;
        }
    }

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public DataBean getData() {
        return data;
    }

    public void setData(DataBean data) {
        this.data = data;
    }
}
