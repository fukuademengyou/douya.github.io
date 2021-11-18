package com.example.bean;

import java.io.Serializable;
import java.util.List;

public class CommentBean implements Serializable {

    private String message;
    private boolean success;
    private List<DataDTO> data;

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public boolean isSuccess() {
        return success;
    }

    public void setSuccess(boolean success) {
        this.success = success;
    }

    public List<DataDTO> getData() {
        return data;
    }

    public void setData(List<DataDTO> data) {
        this.data = data;
    }

    public static class DataDTO {
        private int id;
        private int userId;
        private String userName;
        private String userPicUrl;
        private int grade;
        private String content;
        private int favorPeople;

        public int getId() {
            return id;
        }

        public void setId(int id) {
            this.id = id;
        }

        public int getUserId() {
            return userId;
        }

        public void setUserId(int userId) {
            this.userId = userId;
        }

        public String getUserName() {
            return userName;
        }

        public void setUserName(String userName) {
            this.userName = userName;
        }

        public String getUserPicUrl() {
            return userPicUrl;
        }

        public void setUserPicUrl(String userPicUrl) {
            this.userPicUrl = userPicUrl;
        }

        public int getGrade() {
            return grade;
        }

        public void setGrade(int grade) {
            this.grade = grade;
        }

        public String getContent() {
            return content;
        }

        public void setContent(String content) {
            this.content = content;
        }

        public int getFavorPeople() {
            return favorPeople;
        }

        public void setFavorPeople(int favorPeople) {
            this.favorPeople = favorPeople;
        }
    }
}
