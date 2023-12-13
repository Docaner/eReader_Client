package com.example.ereader;

import com.google.gson.annotations.SerializedName;

public class Users {
        @SerializedName("id")
        private String Id;

        @SerializedName("status")
        private String Response;

        public String getId() {
                return Id;
        }

        public String getResponse() {
                return Response;
        }


        {/*@SerializedName("name")
        private String Name;

        @SerializedName("author")
        private String Author;

        @SerializedName("description")
        private String Description;

        @SerializedName("rating")
        private String Rating;

        @SerializedName("image")
        private String Image;
        public String getId() {
        return Id;
}

        public String getResponse() {
        return Response;
}

        public String getName() {
                return Name;
        }

        public String getAuthor() {
                return Author;
        }

        public String getDescription() {
                return Description;
        }

        public String getRating() {
                return Rating;
        }

        public String getImage() {
                return Image;
        }*/}
}
