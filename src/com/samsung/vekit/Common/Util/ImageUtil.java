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

    public static ImageInfo parseImage(String str) throws Throwable {
        Log.e(TAG, "filepath : " + str);
        ExifInterface exif = getExif(str);
        if (exif != null) {
            width = Integer.parseInt(getAttribute(exif, ExifInterface.TAG_IMAGE_WIDTH));
            height = Integer.parseInt(getAttribute(exif, ExifInterface.TAG_IMAGE_LENGTH));
            orientation = Integer.parseInt(getAttribute(exif, ExifInterface.TAG_ORIENTATION));
        }
        if (width == 0 || height == 0) {
            BitmapFactory.Options options = new BitmapFactory.Options();
            options.inJustDecodeBounds = true;
            BitmapFactory.decodeFile(str, options);
            width = options.outWidth;
            height = options.outHeight;
            orientation = 0;
        }
        ImageInfo imageInfo = new ImageInfo(width, height, orientation);
        Log.e(TAG, "width : " + width + ", height  : " + height + ", orientation :  " + orientation);
        return imageInfo;
    }

    /* JADX WARN: Can't fix incorrect switch cases order, some code will duplicate */
    public static Bitmap decodeImage(String str, int i, int i2) throws Throwable {
        Bitmap bitmapCreateBitmap;
        Log.e(TAG, "filePath : " + str + ", targetWidth : " + i + ", targetHeight :" + i2);
        Bitmap bitmapDecodeImageBySkia = decodeImageBySkia(str, i, i2);
        if (bitmapDecodeImageBySkia == null) {
            Log.e(TAG, "can't decode image file");
            return null;
        }
        Matrix matrix = new Matrix();
        switch (orientation) {
            case 0:
            case 1:
                bitmapCreateBitmap = bitmapDecodeImageBySkia;
                break;
            case 2:
                matrix.setScale(-1.0f, 1.0f);
                bitmapCreateBitmap = Bitmap.createBitmap(bitmapDecodeImageBySkia, 0, 0, bitmapDecodeImageBySkia.getWidth(), bitmapDecodeImageBySkia.getHeight(), matrix, true);
                bitmapDecodeImageBySkia.recycle();
                break;
            case 3:
                matrix.postRotate(180.0f);
                bitmapCreateBitmap = Bitmap.createBitmap(bitmapDecodeImageBySkia, 0, 0, bitmapDecodeImageBySkia.getWidth(), bitmapDecodeImageBySkia.getHeight(), matrix, true);
                bitmapDecodeImageBySkia.recycle();
                break;
            case 4:
                matrix.setScale(1.0f, -1.0f);
                bitmapCreateBitmap = Bitmap.createBitmap(bitmapDecodeImageBySkia, 0, 0, bitmapDecodeImageBySkia.getWidth(), bitmapDecodeImageBySkia.getHeight(), matrix, true);
                bitmapDecodeImageBySkia.recycle();
                break;
            case 5:
                matrix.setScale(1.0f, -1.0f);
                matrix.postRotate(90.0f);
                bitmapCreateBitmap = Bitmap.createBitmap(bitmapDecodeImageBySkia, 0, 0, bitmapDecodeImageBySkia.getWidth(), bitmapDecodeImageBySkia.getHeight(), matrix, true);
                bitmapDecodeImageBySkia.recycle();
                break;
            case 6:
                matrix.postRotate(90.0f);
                bitmapCreateBitmap = Bitmap.createBitmap(bitmapDecodeImageBySkia, 0, 0, bitmapDecodeImageBySkia.getWidth(), bitmapDecodeImageBySkia.getHeight(), matrix, true);
                bitmapDecodeImageBySkia.recycle();
                break;
            case 7:
                matrix.setScale(1.0f, -1.0f);
                matrix.postRotate(270.0f);
                bitmapCreateBitmap = Bitmap.createBitmap(bitmapDecodeImageBySkia, 0, 0, bitmapDecodeImageBySkia.getWidth(), bitmapDecodeImageBySkia.getHeight(), matrix, true);
                bitmapDecodeImageBySkia.recycle();
                break;
            case 8:
                matrix.postRotate(270.0f);
                bitmapCreateBitmap = Bitmap.createBitmap(bitmapDecodeImageBySkia, 0, 0, bitmapDecodeImageBySkia.getWidth(), bitmapDecodeImageBySkia.getHeight(), matrix, true);
                bitmapDecodeImageBySkia.recycle();
                break;
            default:
                bitmapCreateBitmap = Bitmap.createBitmap(bitmapDecodeImageBySkia, 0, 0, bitmapDecodeImageBySkia.getWidth(), bitmapDecodeImageBySkia.getHeight(), matrix, true);
                bitmapDecodeImageBySkia.recycle();
                break;
        }
        if (bitmapCreateBitmap == null) {
            return convert(bitmapDecodeImageBySkia, bitmapDecodeImageBySkia.getConfig());
        }
        return convert(bitmapCreateBitmap, bitmapCreateBitmap.getConfig());
    }

    private static Bitmap convert(Bitmap bitmap, Bitmap.Config config) {
        Log.e(TAG, ": " + bitmap);
        Bitmap bitmapCreateBitmap = Bitmap.createBitmap(bitmap.getWidth(), bitmap.getHeight(), config);
        Canvas canvas = new Canvas(bitmapCreateBitmap);
        Paint paint = new Paint();
        paint.setColor(-16777216);
        canvas.drawBitmap(bitmap, 0.0f, 0.0f, paint);
        return bitmapCreateBitmap;
    }

    private static ExifInterface getExif(String str) {
        try {
            return new ExifInterface(str);
        } catch (Exception | OutOfMemoryError | StackOverflowError e) {
            Log.e(TAG, "getExif failed " + str, e);
            return null;
        }
    }

    private static String getAttribute(ExifInterface exifInterface, String str) {
        if (exifInterface != null) {
            return exifInterface.getAttribute(str);
        }
        return null;
    }

    private static Bitmap decodeImageBySkia(String str, int i, int i2) throws Throwable {
        BitmapFactory.Options options = new BitmapFactory.Options();
        options.inJustDecodeBounds = true;
        BitmapFactory.decodeFile(str, options);
        int iCalculateInSampleSize = calculateInSampleSize(options.outWidth, options.outHeight, i, i2);
        options.inSampleSize = iCalculateInSampleSize;
        sampleSize = iCalculateInSampleSize;
        Log.e(TAG, "decodeImageBySkia: inSampleSize = " + options.inSampleSize);
        options.inJustDecodeBounds = false;
        return BitmapFactory.decodeFile(str, options);
    }

    public static int calculateInSampleSize(int i, int i2, int i3, int i4) {
        int i5 = 1;
        if (i <= i3 && i2 <= i4) {
            return 1;
        }
        int i6 = i;
        int i7 = i2;
        while (true) {
            if (i6 > i3 || i7 > i4) {
                i5++;
                i6 = i / i5;
                i7 = i2 / i5;
            } else {
                Log.d(TAG, "sampleSize : " + i5 + " tempWidth : " + i6 + " tempHeight : " + i7);
                return i5;
            }
        }
    }
}
