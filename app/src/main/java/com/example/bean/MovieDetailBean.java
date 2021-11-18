package com.example.bean;

import java.io.Serializable;
import java.util.List;

public class MovieDetailBean implements Serializable {

    private String message;
    private boolean success;
    private DataDTO data;

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

    public DataDTO getData() {
        return data;
    }

    public void setData(DataDTO data) {
        this.data = data;
    }

    public static class DataDTO {
        private int id;
        private String name;
        private String picUrl;
        private double grade;
        private String introduction;
        private List<Integer> gradeInfo;
        private List<ActorListDTO> actorList;
        private List<String> stagePhotoUrl;
        private String showPlace;
        private String lable;
        private String showDate;
        private String lastTime;

        public int getId() {
            return id;
        }

        public void setId(int id) {
            this.id = id;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public String getPicUrl() {
            return picUrl;
        }

        public void setPicUrl(String picUrl) {
            this.picUrl = picUrl;
        }

        public double getGrade() {
            return grade;
        }

        public void setGrade(double grade) {
            this.grade = grade;
        }

        public String getIntroduction() {
            return introduction;
        }

        public void setIntroduction(String introduction) {
            this.introduction = introduction;
        }

        public List<Integer> getGradeInfo() {
            return gradeInfo;
        }

        public void setGradeInfo(List<Integer> gradeInfo) {
            this.gradeInfo = gradeInfo;
        }

        public List<ActorListDTO> getActorList() {
            return actorList;
        }

        public void setActorList(List<ActorListDTO> actorList) {
            this.actorList = actorList;
        }

        public List<String> getStagePhotoUrl() {
            return stagePhotoUrl;
        }

        public void setStagePhotoUrl(List<String> stagePhotoUrl) {
            this.stagePhotoUrl = stagePhotoUrl;
        }

        public String getShowPlace() {
            return showPlace;
        }

        public void setShowPlace(String showPlace) {
            this.showPlace = showPlace;
        }

        public String getLable() {
            return lable;
        }

        public void setLable(String lable) {
            this.lable = lable;
        }

        public String getShowDate() {
            return showDate;
        }

        public void setShowDate(String showDate) {
            this.showDate = showDate;
        }

        public String getLastTime() {
            return lastTime;
        }

        public void setLastTime(String lastTime) {
            this.lastTime = lastTime;
        }

        public static class ActorListDTO {
            private int id;
            private String nameC;
            private String nameE;
            private String picUrl;
            private String introduction;

            public int getId() {
                return id;
            }

            public void setId(int id) {
                this.id = id;
            }

            public String getNameC() {
                return nameC;
            }

            public void setNameC(String nameC) {
                this.nameC = nameC;
            }

            public String getNameE() {
                return nameE;
            }

            public void setNameE(String nameE) {
                this.nameE = nameE;
            }

            public String getPicUrl() {
                return picUrl;
            }

            public void setPicUrl(String picUrl) {
                this.picUrl = picUrl;
            }

            public String getIntroduction() {
                return introduction;
            }

            public void setIntroduction(String introduction) {
                this.introduction = introduction;
            }
        }
    }
}
