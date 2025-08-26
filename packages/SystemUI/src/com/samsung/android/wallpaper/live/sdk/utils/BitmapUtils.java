package com.samsung.android.wallpaper.live.sdk.utils;

import android.graphics.Bitmap;
import android.graphics.Matrix;
import android.graphics.Rect;
import android.os.ParcelFileDescriptor;
import android.os.SystemClock;
import com.android.systemui.bixby2.actionresult.ActionResults;
import java.io.IOException;

/* loaded from: classes4.dex */
public class BitmapUtils {
    public static Bitmap cropRotateResizeBitmap(Bitmap bitmap, Rect rect, int i, float f, boolean z) {
        if (f <= 0.0f || bitmap == null || bitmap.isRecycled()) {
            SdkLog.e("BitmapUtils", "cropRotateResizeBitmap: incorrect parameter. bmp=" + bitmap + ", scale=" + f);
            return null;
        }
        Rect rect2 = new Rect(0, 0, bitmap.getWidth(), bitmap.getHeight());
        if (rect == null) {
            rect = rect2;
        }
        if (f == 1.0f && i == 0 && rect.equals(rect2)) {
            return z ? bitmap : bitmap.copy(bitmap.getConfig(), bitmap.isMutable());
        }
        Matrix matrix = new Matrix();
        matrix.postRotate(i);
        matrix.postScale(f, f);
        return Bitmap.createBitmap(bitmap, rect.left, rect.top, rect.width(), rect.height(), matrix, true);
    }

    public static ParcelFileDescriptor encodeBitmapToPipe(final Bitmap bitmap, final Bitmap.CompressFormat compressFormat, final Runnable runnable) throws IOException {
        final long jElapsedRealtime = SystemClock.elapsedRealtime();
        try {
            final ParcelFileDescriptor[] parcelFileDescriptorArrCreatePipe = ParcelFileDescriptor.createPipe();
            final boolean[] zArr = {false};
            new Thread(new Runnable() { // from class: com.samsung.android.wallpaper.live.sdk.utils.BitmapUtils$$ExternalSyntheticLambda0
                /* JADX WARN: Multi-variable type inference failed */
                /* JADX WARN: Removed duplicated region for block: B:57:0x0055 A[EXC_TOP_SPLITTER, SYNTHETIC] */
                /* JADX WARN: Type inference failed for: r1v0, types: [android.graphics.Bitmap] */
                /* JADX WARN: Type inference failed for: r1v1 */
                /* JADX WARN: Type inference failed for: r1v12 */
                /* JADX WARN: Type inference failed for: r1v13 */
                /* JADX WARN: Type inference failed for: r1v15, types: [boolean] */
                /* JADX WARN: Type inference failed for: r1v2 */
                /* JADX WARN: Type inference failed for: r1v3 */
                @Override // java.lang.Runnable
                /*
                    Code decompiled incorrectly, please refer to instructions dump.
                */
                public final void run() throws Throwable {
                    boolean z;
                    ParcelFileDescriptor[] parcelFileDescriptorArr = parcelFileDescriptorArrCreatePipe;
                    ?? Compress = bitmap;
                    Bitmap.CompressFormat compressFormat2 = compressFormat;
                    boolean[] zArr2 = zArr;
                    Runnable runnable2 = runnable;
                    long j = jElapsedRealtime;
                    try {
                        try {
                            ParcelFileDescriptor parcelFileDescriptor = parcelFileDescriptorArr[1];
                            try {
                                ParcelFileDescriptor.AutoCloseOutputStream autoCloseOutputStream = new ParcelFileDescriptor.AutoCloseOutputStream(parcelFileDescriptor);
                                try {
                                    Compress = Compress.compress(compressFormat2, 100, autoCloseOutputStream);
                                    try {
                                        autoCloseOutputStream.close();
                                        z = Compress;
                                        if (parcelFileDescriptor != null) {
                                            parcelFileDescriptor.close();
                                            z = Compress;
                                        }
                                    } catch (Throwable th) {
                                        th = th;
                                        if (parcelFileDescriptor != null) {
                                            try {
                                                parcelFileDescriptor.close();
                                            } catch (Throwable th2) {
                                                th.addSuppressed(th2);
                                            }
                                        }
                                        throw th;
                                    }
                                } finally {
                                }
                            } catch (Throwable th3) {
                                th = th3;
                                Compress = 0;
                            }
                        } catch (Exception e) {
                            e = e;
                            Compress = 0;
                            SdkLog.e("BitmapUtils", "encodeBitmapToPipe: encoder: e=" + e);
                            z = Compress;
                            synchronized (zArr2) {
                            }
                        }
                    } catch (Exception e2) {
                        e = e2;
                        SdkLog.e("BitmapUtils", "encodeBitmapToPipe: encoder: e=" + e);
                        z = Compress;
                        synchronized (zArr2) {
                        }
                    }
                    synchronized (zArr2) {
                        zArr2[0] = true;
                        zArr2.notify();
                    }
                    if (runnable2 != null) {
                        runnable2.run();
                    }
                    StringBuilder sb = new StringBuilder("encodeBitmapToPipe: encoder: result=");
                    sb.append(z ? "success" : ActionResults.RESULT_FAIL);
                    sb.append(", ");
                    sb.append(SystemClock.elapsedRealtime() - j);
                    sb.append("ms");
                    SdkLog.i("BitmapUtils", sb.toString());
                }
            }).start();
            new Thread(new Runnable() { // from class: com.samsung.android.wallpaper.live.sdk.utils.BitmapUtils$$ExternalSyntheticLambda1
                @Override // java.lang.Runnable
                public final void run() throws IOException {
                    boolean[] zArr2 = zArr;
                    ParcelFileDescriptor[] parcelFileDescriptorArr = parcelFileDescriptorArrCreatePipe;
                    synchronized (zArr2) {
                        if (zArr2[0]) {
                            return;
                        }
                        try {
                            zArr2.wait(10000L);
                        } catch (InterruptedException unused) {
                        }
                        if (zArr2[0]) {
                            return;
                        }
                        SdkLog.i("BitmapUtils", "encodeBitmapToPipe: observer: timeout. closing read pfd forcefully");
                        try {
                            parcelFileDescriptorArr[0].close();
                        } catch (IOException e) {
                            SdkLog.e("BitmapUtils", "encodeBitmapToPipe: observer: e=" + e);
                        }
                    }
                }
            }).start();
            return parcelFileDescriptorArrCreatePipe[0];
        } catch (Exception e) {
            SdkLog.e("BitmapUtils", "encodeBitmapToPipe: e=" + e);
            runnable.run();
            return null;
        }
    }

    public static String getBitmapSizeString(Bitmap bitmap) {
        if (bitmap == null) {
            return "null";
        }
        return bitmap.getWidth() + "x" + bitmap.getHeight();
    }

    public static Bitmap getSizeLimitedCenterCropBitmap(Bitmap bitmap, Rect rect, int i, int i2, int i3, boolean z) {
        if (bitmap == null) {
            SdkLog.e("BitmapUtils", "getSizeLimitedCenterCropBitmap: srcBitmap is null");
            return null;
        }
        Rect rect2 = new Rect(0, 0, bitmap.getWidth(), bitmap.getHeight());
        if (rect != null && !rect.isEmpty()) {
            Rect rect3 = new Rect(rect);
            rect3.intersect(rect2);
            rect2 = rect3;
        }
        int i4 = rect2.left;
        int i5 = rect2.top;
        Rect centerCropRect = GraphicsUtils.getCenterCropRect(rect2.right - i4, rect2.bottom - i5, i, i2);
        centerCropRect.offset(i4, i5);
        float fMin = Math.min(1.0f, Math.min(i / centerCropRect.width(), i2 / centerCropRect.height()));
        Bitmap bitmapCropRotateResizeBitmap = cropRotateResizeBitmap(bitmap, centerCropRect, i3, fMin, z);
        SdkLog.d("BitmapUtils", "getSizeLimitedCenterCropBitmap: , src=" + bitmap.getWidth() + "x" + bitmap.getHeight() + " " + rect + ", adjusted=" + rect2 + ", center=" + centerCropRect + ", scale=" + fMin + ", out=" + bitmapCropRotateResizeBitmap.getWidth() + "x" + bitmapCropRotateResizeBitmap.getHeight());
        return bitmapCropRotateResizeBitmap;
    }
}
