package com.example.taskmanagerproject.utils;

import android.app.Activity;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Point;

public class PictureUtils {

    public static Bitmap getScaledBitmap(String path, int desWidth, int desHeight) {
        BitmapFactory.Options options = new BitmapFactory.Options();
        options.inJustDecodeBounds = true;
        BitmapFactory.decodeFile(path, options);

        int srcWidth = options.outWidth;
        int srcHeight = options.outHeight;

        int inSampleSize = Math.min(srcWidth / desWidth, srcHeight / desHeight);

        options = new BitmapFactory.Options();
        options.inSampleSize = inSampleSize;
        return BitmapFactory.decodeFile(path, options);
    }

    public static Bitmap getScaledBitmap(String path, Activity activity) {
        Point point = new Point();
        activity.getWindowManager().getDefaultDisplay().getSize(point);
        return getScaledBitmap(path, point.x, point.y);
    }
//
//    public static Bitmap getScaledBitmap(String filePath, int dstWidth, int dstHeight) {
//        BitmapFactory.Options options = new BitmapFactory.Options();
//        options.inJustDecodeBounds = true;
//
//        BitmapFactory.decodeFile(filePath, options);
//
//        int srcWidth = options.outWidth;
//        int srcHeight = options.outHeight;
//
//        int scaleFactor = Math.max(1, Math.min(srcWidth/dstWidth, srcHeight/dstHeight));
//
//        options.inJustDecodeBounds = false;
//        options.inSampleSize = scaleFactor;
//
//        Bitmap bitmap = BitmapFactory.decodeFile(filePath, options);
//        return bitmap;
//    }
//
//    public static Bitmap getScaledBitmap(String filePath, Activity activity) {
//        Point size = new Point();
//        activity.getWindowManager().getDefaultDisplay().getSize(size);
//        return getScaledBitmap(filePath, size.x, size.y);
//    }
}
