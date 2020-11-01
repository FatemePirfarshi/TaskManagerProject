package com.example.taskmanagerproject.controller.database;

import androidx.room.TypeConverter;

import java.util.Date;
import java.util.UUID;

public class Converters {

    @TypeConverter
    public static Date longToDate(long timestamp) {
        return new Date(timestamp);
    }

    @TypeConverter
    public static Long dateToLong(Date date) {
        return date.getTime();
    }

    @TypeConverter
    public static UUID stringToUUID(String value) {
        return UUID.fromString(value);
    }

    @TypeConverter
    public static String UUIDToString(UUID uuid) {
        return uuid.toString();
    }

}
