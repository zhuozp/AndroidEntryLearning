package com.example.zhipengzhuo.entrytask.model;

import java.util.List;

public class UserInfo {
    private Info info;

    public static class Info {
        private String content;
        private List<GridView> gridview;
        private int id;
        private int level;
        private String name;

        public static class GridView {
            private String title;
            private String url;

            public String getTitle() {
                return title;
            }

            public void setTitle(String title) {
                this.title = title;
            }

            public String getUrl() {
                return url;
            }

            public void setUrl(String url) {
                this.url = url;
            }
        }

        public String getContent() {
            return content;
        }

        public void setContent(String content) {
            this.content = content;
        }

        public List<GridView> getGridview() {
            return gridview;
        }

        public void setGridview(List<GridView> gridview) {
            this.gridview = gridview;
        }

        public int getId() {
            return id;
        }

        public void setId(int id) {
            this.id = id;
        }

        public int getLevel() {
            return level;
        }

        public void setLevel(int level) {
            this.level = level;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }
    }

    public Info getInfo() {
        return info;
    }

    public void setInfo(Info info) {
        this.info = info;
    }
}
