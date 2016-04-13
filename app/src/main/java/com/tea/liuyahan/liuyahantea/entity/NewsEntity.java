package com.tea.liuyahan.liuyahantea.entity;

public class NewsEntity {
    private DataEntity data;

    public DataEntity getData() {
        return data;
    }

    public void setData(DataEntity data) {
        this.data = data;
    }

    public class DataEntity {
        private String id;
        private String title;
        private String source;
        private String wap_content;
        private String create_time;
        private String author;
        private String weiboUrl;

        public String getId() {
            return id;
        }

        public void setId(String id) {
            this.id = id;
        }

        public String getTitle() {
            return title;
        }

        public void setTitle(String title) {
            this.title = title;
        }

        public String getSource() {
            return source;
        }

        public void setSource(String source) {
            this.source = source;
        }

        public String getWap_content() {
            return wap_content;
        }

        public void setWap_content(String wap_content) {
            this.wap_content = wap_content;
        }

        public String getCreate_time() {
            return create_time;
        }

        public void setCreate_time(String create_time) {
            this.create_time = create_time;
        }

        public String getAuthor() {
            return author;
        }

        public void setAuthor(String author) {
            this.author = author;
        }

        public String getWeiboUrl() {
            return weiboUrl;
        }

        public void setWeiboUrl(String weiboUrl) {
            this.weiboUrl = weiboUrl;
        }
    }

}
