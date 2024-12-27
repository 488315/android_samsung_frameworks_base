package com.samsung.android.wallpaper.legibilitycolors.utils.image;

import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Rect;
import android.media.MediaMetrics;
import android.view.View;
import com.samsung.android.wallpaper.legibilitycolors.utils.IUXColorUtils;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.IntBuffer;

/* loaded from: classes6.dex */
public class BitmapHelper {
    static Bitmap.CompressFormat mCompressFormat = Bitmap.CompressFormat.PNG;
    static int mCompressQuality = 100;
    static String TAG = "BitmapHelper";

    public static Bitmap.CompressFormat getCompressedFormat() {
        return mCompressFormat;
    }

    public static void setCompressedFormat(Bitmap.CompressFormat format) {
        mCompressFormat = format;
    }

    public static int getCompressedQuality() {
        return mCompressQuality;
    }

    public static void setCompressedQuality(int quality) {
        mCompressQuality = quality;
    }

    public static boolean saveBitmapAsFile(Bitmap bitmap, String dirPath, String fileName) {
        File file = new File(dirPath);
        if (!file.exists()) {
            boolean success = file.mkdir();
            if (!success) {
                return false;
            }
        }
        return saveBitmapAsFile(bitmap, dirPath + File.separator + fileName);
    }

    /* JADX WARN: Unsupported multi-entry loop pattern (BACK_EDGE: B:12:0x0034 -> B:6:0x0044). Please report as a decompilation issue!!! */
    public static boolean saveBitmapAsFile(Bitmap bitmap, String fileName) {
        boolean bResult = false;
        FileOutputStream out = null;
        Bitmap.CompressFormat compressFormat = mCompressFormat;
        int compressQuality = mCompressQuality;
        try {
            try {
                try {
                    String fullPath = fileName + MediaMetrics.SEPARATOR + compressFormat.toString();
                    out = new FileOutputStream(fullPath);
                    bitmap.compress(compressFormat, compressQuality, out);
                    out.close();
                    bResult = true;
                    out.close();
                } catch (Throwable th) {
                    if (out != null) {
                        try {
                            out.close();
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    }
                    throw th;
                }
            } catch (Exception e2) {
                e2.printStackTrace();
                if (out != null) {
                    out.close();
                }
            }
        } catch (IOException e3) {
            e3.printStackTrace();
        }
        return bResult;
    }

    public static Bitmap createCroppedImageKeepingRatio(Bitmap srcBitmap, float dstWidthRatio) {
        int srcWidth = srcBitmap.getWidth();
        int srcHeight = srcBitmap.getHeight();
        float srcWidthRatio = srcWidth / srcHeight;
        if (dstWidthRatio > srcWidthRatio) {
            int croppedHeight = (int) (srcHeight * (srcWidthRatio / dstWidthRatio));
            Bitmap croppedBitmap = Bitmap.createBitmap(srcBitmap, 0, (srcHeight - croppedHeight) / 2, srcWidth, croppedHeight);
            return croppedBitmap;
        }
        if (dstWidthRatio < srcWidthRatio) {
            int croppedWidth = (int) (srcWidth * (dstWidthRatio / srcWidthRatio));
            Bitmap croppedBitmap2 = Bitmap.createBitmap(srcBitmap, (srcWidth - croppedWidth) / 2, 0, croppedWidth, srcHeight);
            return croppedBitmap2;
        }
        Bitmap croppedBitmap3 = Bitmap.createBitmap(srcBitmap);
        return croppedBitmap3;
    }

    public static Rect getBitmapRectForCenterCrop(Bitmap srcBitmap, float widthRatio) {
        float srcBitmapWidth = srcBitmap.getWidth();
        float srcBitmapHeight = srcBitmap.getHeight();
        float srcWidthRatio = srcBitmapWidth / srcBitmapHeight;
        if (srcWidthRatio > widthRatio) {
            float scaledWidth = srcBitmapHeight * widthRatio;
            Rect rectResult = new Rect((int) ((srcBitmapWidth * 0.5f) - (scaledWidth * 0.5f)), (int) ((srcBitmapHeight * 0.5f) - (srcBitmapHeight * 0.5f)), (int) ((srcBitmapWidth * 0.5f) + (scaledWidth * 0.5f)), (int) ((srcBitmapHeight * 0.5f) + (0.5f * srcBitmapHeight)));
            return rectResult;
        }
        if (srcWidthRatio < widthRatio) {
            float scaledHeight = srcBitmapWidth / widthRatio;
            Rect rectResult2 = new Rect((int) ((srcBitmapWidth * 0.5f) - (srcBitmapWidth * 0.5f)), (int) ((srcBitmapHeight * 0.5f) - (scaledHeight * 0.5f)), (int) ((srcBitmapWidth * 0.5f) + (srcBitmapWidth * 0.5f)), (int) ((srcBitmapHeight * 0.5f) + (0.5f * scaledHeight)));
            return rectResult2;
        }
        Rect rectResult3 = new Rect(0, 0, (int) srcBitmapWidth, (int) srcBitmapHeight);
        return rectResult3;
    }

    public static Bitmap getBitmapFromView(View view) {
        view.setPressed(false);
        view.invalidate();
        Bitmap buffer = Bitmap.createBitmap(view.getWidth(), view.getHeight(), Bitmap.Config.ARGB_8888);
        Canvas canvas = new Canvas(buffer);
        view.draw(canvas);
        return buffer;
    }

    public static int[] getImageSizeFromFile(String imagePath) {
        BitmapFactory.Options options = new BitmapFactory.Options();
        options.inJustDecodeBounds = true;
        BitmapFactory.decodeFile(imagePath, options);
        int imageHeight = options.outHeight;
        int imageWidth = options.outWidth;
        return new int[]{imageWidth, imageHeight};
    }

    public static int[] getImageSizeFromResource(Resources resources, int id) {
        BitmapFactory.Options options = new BitmapFactory.Options();
        options.inJustDecodeBounds = true;
        BitmapFactory.decodeResource(resources, id, options);
        int imageHeight = options.outHeight;
        int imageWidth = options.outWidth;
        return new int[]{imageWidth, imageHeight};
    }

    public static int fineScaleOptionValueBySquareRootSize(int imageWidth, int imageHeight, int sizeTobe) {
        int imageAreaSize = imageWidth * imageHeight;
        return (int) Math.max(Math.sqrt(imageAreaSize) / sizeTobe, 1.0d);
    }

    public static float fineScaleValueBySquareRootSize(int imageWidth, int imageHeight, int sizeTobe) {
        int imageAreaSize = imageWidth * imageHeight;
        return (float) (sizeTobe / Math.sqrt(imageAreaSize));
    }

    public static int getAverageColorFromBitmap(Bitmap bitmap) {
        int[] pixels = new int[bitmap.getWidth() * bitmap.getHeight()];
        bitmap.getPixels(pixels, 0, bitmap.getWidth(), 0, 0, bitmap.getWidth(), bitmap.getHeight());
        return getAverageColor(pixels);
    }

    public static float[] getAverageHSVFromBitmap(Bitmap bitmap) {
        int[] pixels = new int[bitmap.getWidth() * bitmap.getHeight()];
        bitmap.getPixels(pixels, 0, bitmap.getWidth(), 0, 0, bitmap.getWidth(), bitmap.getHeight());
        return getAverageHSV(pixels);
    }

    public static int[] getBoarderPixels(Bitmap bitmap, int borderWidth) {
        int[] pixels = new int[bitmap.getWidth() * bitmap.getHeight()];
        bitmap.getPixels(pixels, 0, bitmap.getWidth(), 0, 0, bitmap.getWidth(), bitmap.getHeight());
        return getBoarderPixels(pixels, bitmap.getWidth(), borderWidth);
    }

    public static int[] getBoarderPixels(int[] pixels, int width, int borderWidth) {
        if (pixels == null || pixels.length <= 0) {
            return null;
        }
        int height = pixels.length / width;
        if (width < borderWidth) {
            borderWidth = width;
        }
        if (height < borderWidth) {
            borderWidth = height;
        }
        int borderPixelsNum = pixels.length - ((width - (borderWidth + borderWidth)) * (height - (borderWidth + borderWidth)));
        int[] boarderPixels = new int[borderPixelsNum];
        int i = 0;
        int size = borderWidth;
        for (int h = 0; h < size; h++) {
            int w = 0;
            while (w < width) {
                boarderPixels[i] = pixels[(h * width) + w];
                w++;
                i++;
            }
        }
        int size2 = height - borderWidth;
        for (int h2 = borderWidth; h2 < size2; h2++) {
            int w2 = 0;
            while (w2 < borderWidth) {
                boarderPixels[i] = pixels[(h2 * width) + w2];
                w2++;
                i++;
            }
        }
        for (int h3 = borderWidth; h3 < height - borderWidth; h3++) {
            int w3 = width - borderWidth;
            while (w3 < width) {
                boarderPixels[i] = pixels[(h3 * width) + w3];
                w3++;
                i++;
            }
        }
        for (int h4 = height - borderWidth; h4 < height; h4++) {
            int w4 = 0;
            while (w4 < width) {
                boarderPixels[i] = pixels[(h4 * width) + w4];
                w4++;
                i++;
            }
        }
        return boarderPixels;
    }

    public static int[] getBoarderPixels(IntBuffer intbuffer, int width, int borderWidth) {
        if (intbuffer == null || intbuffer.capacity() <= 0) {
            return null;
        }
        int bufferLength = intbuffer.capacity();
        int height = bufferLength / width;
        if (width < borderWidth) {
            borderWidth = width;
        }
        if (height < borderWidth) {
            borderWidth = height;
        }
        int borderPixelsNum = bufferLength - ((width - (borderWidth + borderWidth)) * (height - (borderWidth + borderWidth)));
        int[] boarderPixels = new int[borderPixelsNum];
        int i = 0;
        int size1 = borderWidth;
        for (int h = 0; h < size1; h++) {
            int w = 0;
            while (w < width) {
                boarderPixels[i] = intbuffer.get((h * width) + w);
                w++;
                i++;
            }
        }
        int size12 = height - borderWidth;
        int size2 = borderWidth;
        for (int h2 = borderWidth; h2 < size12; h2++) {
            int w2 = 0;
            while (w2 < size2) {
                boarderPixels[i] = intbuffer.get((h2 * width) + w2);
                w2++;
                i++;
            }
        }
        int size13 = height - borderWidth;
        for (int h3 = borderWidth; h3 < size13; h3++) {
            int w3 = width - borderWidth;
            while (w3 < width) {
                boarderPixels[i] = intbuffer.get((h3 * width) + w3);
                w3++;
                i++;
            }
        }
        for (int h4 = height - borderWidth; h4 < height; h4++) {
            int w4 = 0;
            while (w4 < width) {
                boarderPixels[i] = intbuffer.get((h4 * width) + w4);
                w4++;
                i++;
            }
        }
        return boarderPixels;
    }

    public static int getAverageColor(int[] pixels) {
        return IUXColorUtils.getAverageColor(pixels);
    }

    public static float[] getAverageHSV(int[] pixels) {
        return IUXColorUtils.getAverageHSV(pixels);
    }
}
