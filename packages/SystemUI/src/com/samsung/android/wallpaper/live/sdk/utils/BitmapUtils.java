package com.samsung.android.wallpaper.live.sdk.utils;

import android.graphics.Bitmap;
import android.graphics.Matrix;
import android.graphics.Rect;
import android.os.ParcelFileDescriptor;
import android.os.SystemClock;
import java.io.IOException;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
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

    public static ParcelFileDescriptor encodeBitmapToPipe(final Bitmap bitmap, final Bitmap.CompressFormat compressFormat, final Runnable runnable) {
        final long elapsedRealtime = SystemClock.elapsedRealtime();
        try {
            final ParcelFileDescriptor[] createPipe = ParcelFileDescriptor.createPipe();
            final boolean[] zArr = {false};
            new Thread(new Runnable() { // from class: com.samsung.android.wallpaper.live.sdk.utils.BitmapUtils$$ExternalSyntheticLambda0
                /* JADX WARN: Multi-variable type inference failed */
                /* JADX WARN: Removed duplicated region for block: B:15:0x0055 A[EXC_TOP_SPLITTER, SYNTHETIC] */
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
                    To view partially-correct code enable 'Show inconsistent code' option in preferences
                */
                public final void run() {
                    /*
                        r10 = this;
                        android.os.ParcelFileDescriptor[] r0 = r1
                        android.graphics.Bitmap r1 = r2
                        android.graphics.Bitmap$CompressFormat r2 = r3
                        boolean[] r3 = r4
                        java.lang.Runnable r4 = r5
                        long r5 = r6
                        r10 = 1
                        r7 = 0
                        r0 = r0[r10]     // Catch: java.lang.Exception -> L3f
                        android.os.ParcelFileDescriptor$AutoCloseOutputStream r8 = new android.os.ParcelFileDescriptor$AutoCloseOutputStream     // Catch: java.lang.Throwable -> L32
                        r8.<init>(r0)     // Catch: java.lang.Throwable -> L32
                        r9 = 100
                        boolean r1 = r1.compress(r2, r9, r8)     // Catch: java.lang.Throwable -> L28
                        r8.close()     // Catch: java.lang.Throwable -> L26
                        if (r0 == 0) goto L54
                        r0.close()     // Catch: java.lang.Exception -> L24
                        goto L54
                    L24:
                        r0 = move-exception
                        goto L41
                    L26:
                        r2 = move-exception
                        goto L34
                    L28:
                        r1 = move-exception
                        r8.close()     // Catch: java.lang.Throwable -> L2d
                        goto L31
                    L2d:
                        r2 = move-exception
                        r1.addSuppressed(r2)     // Catch: java.lang.Throwable -> L32
                    L31:
                        throw r1     // Catch: java.lang.Throwable -> L32
                    L32:
                        r2 = move-exception
                        r1 = r7
                    L34:
                        if (r0 == 0) goto L3e
                        r0.close()     // Catch: java.lang.Throwable -> L3a
                        goto L3e
                    L3a:
                        r0 = move-exception
                        r2.addSuppressed(r0)     // Catch: java.lang.Exception -> L24
                    L3e:
                        throw r2     // Catch: java.lang.Exception -> L24
                    L3f:
                        r0 = move-exception
                        r1 = r7
                    L41:
                        java.lang.String r2 = "BitmapUtils"
                        java.lang.StringBuilder r8 = new java.lang.StringBuilder
                        java.lang.String r9 = "encodeBitmapToPipe: encoder: e="
                        r8.<init>(r9)
                        r8.append(r0)
                        java.lang.String r0 = r8.toString()
                        com.samsung.android.wallpaper.live.sdk.utils.SdkLog.e(r2, r0)
                    L54:
                        monitor-enter(r3)
                        r3[r7] = r10     // Catch: java.lang.Throwable -> L8d
                        r3.notify()     // Catch: java.lang.Throwable -> L8d
                        monitor-exit(r3)     // Catch: java.lang.Throwable -> L8d
                        if (r4 == 0) goto L60
                        r4.run()
                    L60:
                        java.lang.String r10 = "BitmapUtils"
                        java.lang.StringBuilder r0 = new java.lang.StringBuilder
                        java.lang.String r2 = "encodeBitmapToPipe: encoder: result="
                        r0.<init>(r2)
                        if (r1 == 0) goto L6e
                        java.lang.String r1 = "success"
                        goto L70
                    L6e:
                        java.lang.String r1 = "fail"
                    L70:
                        r0.append(r1)
                        java.lang.String r1 = ", "
                        r0.append(r1)
                        long r1 = android.os.SystemClock.elapsedRealtime()
                        long r1 = r1 - r5
                        r0.append(r1)
                        java.lang.String r1 = "ms"
                        r0.append(r1)
                        java.lang.String r0 = r0.toString()
                        com.samsung.android.wallpaper.live.sdk.utils.SdkLog.i(r10, r0)
                        return
                    L8d:
                        r10 = move-exception
                        monitor-exit(r3)     // Catch: java.lang.Throwable -> L8d
                        throw r10
                    */
                    throw new UnsupportedOperationException("Method not decompiled: com.samsung.android.wallpaper.live.sdk.utils.BitmapUtils$$ExternalSyntheticLambda0.run():void");
                }
            }).start();
            new Thread(new Runnable() { // from class: com.samsung.android.wallpaper.live.sdk.utils.BitmapUtils$$ExternalSyntheticLambda1
                @Override // java.lang.Runnable
                public final void run() {
                    boolean[] zArr2 = zArr;
                    ParcelFileDescriptor[] parcelFileDescriptorArr = createPipe;
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
            return createPipe[0];
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
        float min = Math.min(1.0f, Math.min(i / centerCropRect.width(), i2 / centerCropRect.height()));
        Bitmap cropRotateResizeBitmap = cropRotateResizeBitmap(bitmap, centerCropRect, i3, min, z);
        SdkLog.d("BitmapUtils", "getSizeLimitedCenterCropBitmap: , src=" + bitmap.getWidth() + "x" + bitmap.getHeight() + " " + rect + ", adjusted=" + rect2 + ", center=" + centerCropRect + ", scale=" + min + ", out=" + cropRotateResizeBitmap.getWidth() + "x" + cropRotateResizeBitmap.getHeight());
        return cropRotateResizeBitmap;
    }
}
