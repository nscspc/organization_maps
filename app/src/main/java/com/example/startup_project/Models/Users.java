package com.example.startup_project.Models;

    public class Users {
        private String userName,email,password;
        private String organizationPhoto,profilePhoto,UserID;//name("coverPhoto") should be same as given in the database.

//        public Users(String userName, String email, String password) {
//            this.userName = userName;
//            this.email = email;
//            this.password = password;
//        }

        public String getUserID() {
            return UserID;
        }

        public void setUserID(String userID) {
            UserID = userID;
        }

        public String getOrganizationPhoto() {
            return organizationPhoto;
        }

        public void setOrganizationPhoto(String organizationPhoto) {
            this.organizationPhoto = organizationPhoto;
        }

        public Users() {
        }

        public String getProfilePhoto() {
            return profilePhoto;
        }

        public void setProfilePhoto(String profilePhoto) {
            this.profilePhoto = profilePhoto;
        }

        public Users(String userName, String email, String password) {
            this.userName = userName;
            this.email = email;
            this.password = password;
        }

        public String getUserName() {
            return userName;
        }

        public void setUserName(String userName) {
            this.userName = userName;
        }

        public String getEmail() {
            return email;
        }

        public void setEmail(String email) {
            this.email = email;
        }

        public String getPassword() {
            return password;
        }

        public void setPassword(String password) {
            this.password = password;
        }

    }
