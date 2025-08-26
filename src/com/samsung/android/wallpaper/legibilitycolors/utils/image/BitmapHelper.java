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

    public static void setCompressedFormat(Bitmap.CompressFormat compressFormat) {
        mCompressFormat = compressFormat;
    }

    public static int getCompressedQuality() {
        return mCompressQuality;
    }

    public static void setCompressedQuality(int i) {
        mCompressQuality = i;
    }

    public static boolean saveBitmapAsFile(Bitmap bitmap, String str, String str2) {
        File file = new File(str);
        if (!file.exists() && !file.mkdir()) {
            return false;
        }
        return saveBitmapAsFile(bitmap, str + File.separator + str2);
    }

    public static boolean saveBitmapAsFile(Bitmap bitmap, String str) throws Throwable {
        FileOutputStream fileOutputStream;
        Bitmap.CompressFormat compressFormat = mCompressFormat;
        int i = mCompressQuality;
        FileOutputStream fileOutputStream2 = null;
        try {
            try {
                fileOutputStream = new FileOutputStream(str + MediaMetrics.SEPARATOR + compressFormat.toString());
            } catch (Exception e) {
                e = e;
            }
        } catch (Throwable th) {
            th = th;
        }
        try {
            bitmap.compress(compressFormat, i, fileOutputStream);
            fileOutputStream.close();
            try {
                fileOutputStream.close();
            } catch (IOException e2) {
                e2.printStackTrace();
            }
            return true;
        } catch (Exception e3) {
            e = e3;
            fileOutputStream2 = fileOutputStream;
            e.printStackTrace();
            if (fileOutputStream2 != null) {
                try {
                    fileOutputStream2.close();
                } catch (IOException e4) {
                    e4.printStackTrace();
                }
            }
            return false;
        } catch (Throwable th2) {
            th = th2;
            fileOutputStream2 = fileOutputStream;
            if (fileOutputStream2 != null) {
                try {
                    fileOutputStream2.close();
                } catch (IOException e5) {
                    e5.printStackTrace();
                }
            }
            throw th;
        }
    }

    public static Bitmap createCroppedImageKeepingRatio(Bitmap bitmap, float f) {
        int width = bitmap.getWidth();
        int height = bitmap.getHeight();
        float f2 = width;
        float f3 = height;
        float f4 = f2 / f3;
        if (f > f4) {
            int i = (int) (f3 * (f4 / f));
            return Bitmap.createBitmap(bitmap, 0, (height - i) / 2, width, i);
        }
        if (f < f4) {
            int i2 = (int) (f2 * (f / f4));
            return Bitmap.createBitmap(bitmap, (width - i2) / 2, 0, i2, height);
        }
        return Bitmap.createBitmap(bitmap);
    }

    public static Rect getBitmapRectForCenterCrop(Bitmap bitmap, float f) {
        float width = bitmap.getWidth();
        float height = bitmap.getHeight();
        float f2 = width / height;
        if (f2 > f) {
            float f3 = width * 0.5f;
            float f4 = f * height * 0.5f;
            float f5 = height * 0.5f;
            return new Rect((int) (f3 - f4), (int) (f5 - f5), (int) (f3 + f4), (int) (f5 + f5));
        }
        if (f2 < f) {
            float f6 = width / f;
            float f7 = width * 0.5f;
            float f8 = height * 0.5f;
            float f9 = f6 * 0.5f;
            return new Rect((int) (f7 - f7), (int) (f8 - f9), (int) (f7 + f7), (int) (f8 + f9));
        }
        return new Rect(0, 0, (int) width, (int) height);
    }

    public static Bitmap getBitmapFromView(View view) {
        view.setPressed(false);
        view.invalidate();
        Bitmap bitmapCreateBitmap = Bitmap.createBitmap(view.getWidth(), view.getHeight(), Bitmap.Config.ARGB_8888);
        view.draw(new Canvas(bitmapCreateBitmap));
        return bitmapCreateBitmap;
    }

    public static int[] getImageSizeFromFile(String str) throws Throwable {
        BitmapFactory.Options options = new BitmapFactory.Options();
        options.inJustDecodeBounds = true;
        BitmapFactory.decodeFile(str, options);
        return new int[]{options.outWidth, options.outHeight};
    }

    public static int[] getImageSizeFromResource(Resources resources, int i) throws Throwable {
        BitmapFactory.Options options = new BitmapFactory.Options();
        options.inJustDecodeBounds = true;
        BitmapFactory.decodeResource(resources, i, options);
        return new int[]{options.outWidth, options.outHeight};
    }

    public static int fineScaleOptionValueBySquareRootSize(int i, int i2, int i3) {
        return (int) Math.max(Math.sqrt(i * i2) / i3, 1.0d);
    }

    public static float fineScaleValueBySquareRootSize(int i, int i2, int i3) {
        return (float) (i3 / Math.sqrt(i * i2));
    }

    public static int getAverageColorFromBitmap(Bitmap bitmap) {
        int[] iArr = new int[bitmap.getWidth() * bitmap.getHeight()];
        bitmap.getPixels(iArr, 0, bitmap.getWidth(), 0, 0, bitmap.getWidth(), bitmap.getHeight());
        return getAverageColor(iArr);
    }

    public static float[] getAverageHSVFromBitmap(Bitmap bitmap) {
        int[] iArr = new int[bitmap.getWidth() * bitmap.getHeight()];
        bitmap.getPixels(iArr, 0, bitmap.getWidth(), 0, 0, bitmap.getWidth(), bitmap.getHeight());
        return getAverageHSV(iArr);
    }

    public static int[] getBoarderPixels(Bitmap bitmap, int i) {
        int[] iArr = new int[bitmap.getWidth() * bitmap.getHeight()];
        bitmap.getPixels(iArr, 0, bitmap.getWidth(), 0, 0, bitmap.getWidth(), bitmap.getHeight());
        return getBoarderPixels(iArr, bitmap.getWidth(), i);
    }

    public static int[] getBoarderPixels(int[] iArr, int i, int i2) {
        if (iArr == null || iArr.length <= 0) {
            return null;
        }
        int length = iArr.length / i;
        if (i < i2) {
            i2 = i;
        }
        if (length < i2) {
            i2 = length;
        }
        int i3 = i2 + i2;
        int[] iArr2 = new int[iArr.length - ((i - i3) * (length - i3))];
        int i4 = 0;
        for (int i5 = 0; i5 < i2; i5++) {
            int i6 = 0;
            while (i6 < i) {
                iArr2[i4] = iArr[(i5 * i) + i6];
                i6++;
                i4++;
            }
        }
        int i7 = length - i2;
        for (int i8 = i2; i8 < i7; i8++) {
            int i9 = 0;
            while (i9 < i2) {
                iArr2[i4] = iArr[(i8 * i) + i9];
                i9++;
                i4++;
            }
        }
        for (int i10 = i2; i10 < i7; i10++) {
            int i11 = i - i2;
            while (i11 < i) {
                iArr2[i4] = iArr[(i10 * i) + i11];
                i11++;
                i4++;
            }
        }
        while (i7 < length) {
            int i12 = 0;
            while (i12 < i) {
                iArr2[i4] = iArr[(i7 * i) + i12];
                i12++;
                i4++;
            }
            i7++;
        }
        return iArr2;
    }

    public static int[] getBoarderPixels(IntBuffer intBuffer, int i, int i2) {
        if (intBuffer == null || intBuffer.capacity() <= 0) {
            return null;
        }
        int iCapacity = intBuffer.capacity();
        int i3 = iCapacity / i;
        if (i < i2) {
            i2 = i;
        }
        if (i3 < i2) {
            i2 = i3;
        }
        int i4 = i2 + i2;
        int[] iArr = new int[iCapacity - ((i - i4) * (i3 - i4))];
        int i5 = 0;
        for (int i6 = 0; i6 < i2; i6++) {
            int i7 = 0;
            while (i7 < i) {
                iArr[i5] = intBuffer.get((i6 * i) + i7);
                i7++;
                i5++;
            }
        }
        int i8 = i3 - i2;
        for (int i9 = i2; i9 < i8; i9++) {
            int i10 = 0;
            while (i10 < i2) {
                iArr[i5] = intBuffer.get((i9 * i) + i10);
                i10++;
                i5++;
            }
        }
        for (int i11 = i2; i11 < i8; i11++) {
            int i12 = i - i2;
            while (i12 < i) {
                iArr[i5] = intBuffer.get((i11 * i) + i12);
                i12++;
                i5++;
            }
        }
        while (i8 < i3) {
            int i13 = 0;
            while (i13 < i) {
                iArr[i5] = intBuffer.get((i8 * i) + i13);
                i13++;
                i5++;
            }
            i8++;
        }
        return iArr;
    }

    public static int getAverageColor(int[] iArr) {
        return IUXColorUtils.getAverageColor(iArr);
    }

    public static float[] getAverageHSV(int[] iArr) {
        return IUXColorUtils.getAverageHSV(iArr);
    }
}
