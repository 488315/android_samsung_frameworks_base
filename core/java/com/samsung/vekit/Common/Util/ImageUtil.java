package com.samsung.vekit.Common.Util;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.media.ExifInterface;
import android.util.Log;
import com.samsung.vekit.Common.Object.ImageInfo;

/* loaded from: classes6.dex */
public class ImageUtil {
    private static final String TAG = "ImageUtil";
    private static int height;
    private static int orientation;
    private static int sampleSize;
    private static int width;

    public static int getWidth() {
        return width;
    }

    public static int getHeight() {
        return height;
    }

    public static int getOrientation() {
        return orientation;
    }

    public static int getSampleSize() {
        return sampleSize;
    }

    public static ImageInfo parseImage(String filePath) {
        Log.m96e(TAG, "filepath : " + filePath);
        ExifInterface exif = getExif(filePath);
        if (exif != null) {
            width = Integer.parseInt(getAttribute(exif, ExifInterface.TAG_IMAGE_WIDTH));
            height = Integer.parseInt(getAttribute(exif, ExifInterface.TAG_IMAGE_LENGTH));
            orientation = Integer.parseInt(getAttribute(exif, ExifInterface.TAG_ORIENTATION));
        }
        if (width == 0 || height == 0) {
            BitmapFactory.Options options = new BitmapFactory.Options();
            options.inJustDecodeBounds = true;
            BitmapFactory.decodeFile(filePath, options);
            width = options.outWidth;
            height = options.outHeight;
            orientation = 0;
        }
        ImageInfo info = new ImageInfo(width, height, orientation);
        Log.m96e(TAG, "width : " + width + ", height  : " + height + ", orientation :  " + orientation);
        return info;
    }

    /* JADX WARN: Can't fix incorrect switch cases order, some code will duplicate */
    public static Bitmap decodeImage(String filePath, int targetWidth, int targetHeight) {
        boolean isNewBitmapNeeded;
        Log.m96e(TAG, "filePath : " + filePath + ", targetWidth : " + targetWidth + ", targetHeight :" + targetHeight);
        Bitmap bitmap = decodeImageBySkia(filePath, targetWidth, targetHeight);
        if (bitmap == null) {
            Log.m96e(TAG, "can't decode image file");
            return null;
        }
        Bitmap finalBitmap = bitmap;
        Matrix matrix = new Matrix();
        switch (orientation) {
            case 0:
            case 1:
                isNewBitmapNeeded = false;
                break;
            case 2:
                matrix.setScale(-1.0f, 1.0f);
                isNewBitmapNeeded = true;
                break;
            case 3:
                matrix.postRotate(180.0f);
                isNewBitmapNeeded = true;
                break;
            case 4:
                matrix.setScale(1.0f, -1.0f);
                isNewBitmapNeeded = true;
                break;
            case 5:
                matrix.setScale(1.0f, -1.0f);
                matrix.postRotate(90.0f);
                isNewBitmapNeeded = true;
                break;
            case 6:
                matrix.postRotate(90.0f);
                isNewBitmapNeeded = true;
                break;
            case 7:
                matrix.setScale(1.0f, -1.0f);
                matrix.postRotate(270.0f);
                isNewBitmapNeeded = true;
                break;
            case 8:
                matrix.postRotate(270.0f);
                isNewBitmapNeeded = true;
                break;
            default:
                isNewBitmapNeeded = true;
                break;
        }
        if (isNewBitmapNeeded) {
            finalBitmap = Bitmap.createBitmap(bitmap, 0, 0, bitmap.getWidth(), bitmap.getHeight(), matrix, true);
            bitmap.recycle();
        }
        if (finalBitmap == null) {
            return convert(bitmap, bitmap.getConfig());
        }
        return convert(finalBitmap, finalBitmap.getConfig());
    }

    private static Bitmap convert(Bitmap bitmap, Bitmap.Config config) {
        Log.m96e(TAG, ": " + bitmap);
        Bitmap convertedBitmap = Bitmap.createBitmap(bitmap.getWidth(), bitmap.getHeight(), config);
        Canvas canvas = new Canvas(convertedBitmap);
        Paint paint = new Paint();
        paint.setColor(-16777216);
        canvas.drawBitmap(bitmap, 0.0f, 0.0f, paint);
        return convertedBitmap;
    }

    private static ExifInterface getExif(String path) {
        try {
            return new ExifInterface(path);
        } catch (Exception | OutOfMemoryError | StackOverflowError e) {
            Log.m97e(TAG, "getExif failed " + path, e);
            return null;
        }
    }

    private static String getAttribute(ExifInterface exif, String key) {
        if (exif != null) {
            return exif.getAttribute(key);
        }
        return null;
    }

    private static Bitmap decodeImageBySkia(String filepath, int targetWidth, int targetHeight) {
        BitmapFactory.Options options = new BitmapFactory.Options();
        options.inJustDecodeBounds = true;
        BitmapFactory.decodeFile(filepath, options);
        int calculateInSampleSize = calculateInSampleSize(options.outWidth, options.outHeight, targetWidth, targetHeight);
        options.inSampleSize = calculateInSampleSize;
        sampleSize = calculateInSampleSize;
        Log.m96e(TAG, "decodeImageBySkia: inSampleSize = " + options.inSampleSize);
        options.inJustDecodeBounds = false;
        Bitmap bitmap = BitmapFactory.decodeFile(filepath, options);
        return bitmap;
    }

    public static int calculateInSampleSize(int width2, int height2, int targetWidth, int targetHeight) {
        int sampleSize2 = 1;
        int tempWidth = width2;
        int tempHeight = height2;
        if (tempWidth <= targetWidth && tempHeight <= targetHeight) {
            return 1;
        }
        while (true) {
            if (tempWidth > targetWidth || tempHeight > targetHeight) {
                sampleSize2++;
                tempWidth = width2 / sampleSize2;
                tempHeight = height2 / sampleSize2;
            } else {
                Log.m94d(TAG, "sampleSize : " + sampleSize2 + " tempWidth : " + tempWidth + " tempHeight : " + tempHeight);
                return sampleSize2;
            }
        }
    }
}
