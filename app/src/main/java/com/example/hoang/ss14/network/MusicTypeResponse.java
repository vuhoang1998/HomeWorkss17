package com.example.hoang.ss14.network;

import java.util.List;

/**
 * Created by hoang on 4/15/2018.
 */

public class MusicTypeResponse {
    public List<MusicTypesJSON> subgenres;

    public class MusicTypesJSON {
        public String id;
        public String translation_key;

        @Override
        public String toString() {
            return "MusicTypesJSON{" +
                    "id='" + id + '\'' +
                    ", translation_key='" + translation_key + '\'' +
                    '}';
        }
    }
}
