package com.samsung.android.wallpaper.live.sdk.utils;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Rect;
import android.hardware.display.DisplayManager;
import android.os.Handler;
import android.os.HandlerThread;
import android.os.SystemClock;
import android.util.Size;
import android.view.Display;
import android.view.PixelCopy;
import android.view.Surface;
import android.view.SurfaceHolder;
import androidx.compose.runtime.snapshots.SnapshotStateObserver$$ExternalSyntheticOutline0;
import com.android.systemui.util.DelayableMarqueeTextView;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes4.dex */
public class DisplayUtils {
    public static int convertDisplayRotationToAngle(int i) {
        if (i == 1) {
            return 90;
        }
        if (i != 2) {
            return i != 3 ? 0 : 270;
        }
        return 180;
    }

    public static Bitmap copySurfaceToBitmapSync(final SurfaceHolder surfaceHolder, final Rect rect, final Size size) {
        if (surfaceHolder == null) {
            SdkLog.e("DisplayUtils", "copySurfaceToBitmapSync : surface holder is null");
            return null;
        }
        final Rect surfaceFrame = surfaceHolder.getSurfaceFrame();
        if (surfaceFrame == null || surfaceFrame.isEmpty()) {
            SdkLog.e("DisplayUtils", "copySurfaceToBitmapSync : frame is empty");
            return null;
        }
        long elapsedRealtime = SystemClock.elapsedRealtime();
        final Object obj = new Object();
        HandlerThread handlerThread = new HandlerThread("wallpaperScreenshot");
        handlerThread.start();
        final Handler handler = new Handler(handlerThread.getLooper());
        final Bitmap[] bitmapArr = {null};
        final boolean[] zArr = {false};
        synchronized (obj) {
            handler.post(new Runnable() { // from class: com.samsung.android.wallpaper.live.sdk.utils.DisplayUtils$$ExternalSyntheticLambda0
                @Override // java.lang.Runnable
                public final void run() {
                    int i;
                    int i2;
                    Size size2 = size;
                    Rect rect2 = surfaceFrame;
                    SurfaceHolder surfaceHolder2 = surfaceHolder;
                    final Bitmap[] bitmapArr2 = bitmapArr;
                    Rect rect3 = rect;
                    final boolean[] zArr2 = zArr;
                    final Object obj2 = obj;
                    Handler handler2 = handler;
                    try {
                        if (size2 != null) {
                            i = size2.getWidth();
                            i2 = size2.getHeight();
                        } else {
                            int width = rect2.width();
                            int height = rect2.height();
                            i = width;
                            i2 = height;
                        }
                        Surface surface = surfaceHolder2.getSurface();
                        SdkLog.i("DisplayUtils", "copySurfaceToBitmapSync: width = " + i + ", height = " + i2 + ", validSurface=" + surface.isValid());
                        Bitmap createBitmap = Bitmap.createBitmap(i, i2, Bitmap.Config.ARGB_8888);
                        bitmapArr2[0] = createBitmap;
                        PixelCopy.request(surface, rect3, createBitmap, new PixelCopy.OnPixelCopyFinishedListener() { // from class: com.samsung.android.wallpaper.live.sdk.utils.DisplayUtils$$ExternalSyntheticLambda1
                            @Override // android.view.PixelCopy.OnPixelCopyFinishedListener
                            public final void onPixelCopyFinished(int i3) {
                                Bitmap[] bitmapArr3 = bitmapArr2;
                                boolean[] zArr3 = zArr2;
                                Object obj3 = obj2;
                                if (i3 != 0) {
                                    SdkLog.i("DisplayUtils", "copySurfaceToBitmapSync : copy failed. code=" + i3);
                                    bitmapArr3[0].recycle();
                                    bitmapArr3[0] = null;
                                }
                                zArr3[0] = true;
                                synchronized (obj3) {
                                    obj3.notify();
                                }
                            }
                        }, handler2);
                    } catch (Exception unused) {
                        synchronized (obj2) {
                            obj2.notify();
                        }
                    }
                }
            });
            try {
                obj.wait(DelayableMarqueeTextView.DEFAULT_MARQUEE_DELAY);
            } catch (InterruptedException unused) {
            }
        }
        handlerThread.quitSafely();
        Bitmap bitmap = zArr[0] ? bitmapArr[0] : null;
        boolean z = bitmap != null;
        StringBuilder m = SnapshotStateObserver$$ExternalSyntheticOutline0.m("copySurfaceToBitmapSync : elapsed=", SystemClock.elapsedRealtime() - elapsedRealtime, ", surface=(");
        m.append(surfaceFrame.width());
        m.append("x");
        m.append(surfaceFrame.height());
        m.append("), src=");
        m.append(rect);
        m.append(", outBmp=");
        m.append(BitmapUtils.getBitmapSizeString(bitmap));
        m.append(", pxCpyFin=");
        m.append(zArr[0]);
        m.append(", isSuccess=");
        m.append(z);
        SdkLog.i("DisplayUtils", m.toString());
        return bitmap;
    }

    /* JADX WARN: Removed duplicated region for block: B:26:0x00ec A[RETURN] */
    /* JADX WARN: Removed duplicated region for block: B:27:0x00eb A[RETURN] */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public static int getDisplayIdByWhich(int r9, android.content.Context r10) {
        /*
            r0 = 1
            r1 = r9 & 60
            java.lang.String r2 = "SEC_FLOATING_FEATURE_FRAMEWORK_SUPPORT_FOLDABLE_TYPE_FOLD"
            r3 = 4
            r4 = 0
            if (r1 == r3) goto Ldb
            r3 = 8
            java.lang.String r5 = "getDisplayIdByWhich : e="
            java.lang.String r6 = "display"
            r7 = -1
            java.lang.String r8 = "DisplayUtils"
            if (r1 == r3) goto L7a
            r3 = 16
            if (r1 == r3) goto L69
            r2 = 32
            if (r1 == r2) goto L2e
            java.lang.StringBuilder r10 = new java.lang.StringBuilder
            java.lang.String r0 = "getDisplayIdByWhich : unsupported mode. mode="
            r10.<init>(r0)
            r10.append(r9)
            java.lang.String r9 = r10.toString()
            com.samsung.android.wallpaper.live.sdk.utils.SdkLog.e(r8, r9)
            return r7
        L2e:
            java.lang.String r9 = "com.samsung.android.hardware.display.category.VIEW_COVER_DISPLAY"
            java.lang.Object r10 = r10.getSystemService(r6)
            android.hardware.display.DisplayManager r10 = (android.hardware.display.DisplayManager) r10
            java.lang.Class<android.hardware.display.DisplayManager> r1 = android.hardware.display.DisplayManager.class
            java.lang.String r2 = "getDisplays"
            java.lang.Class[] r0 = new java.lang.Class[r0]     // Catch: java.lang.Exception -> L58
            java.lang.Class<java.lang.String> r3 = java.lang.String.class
            r0[r4] = r3     // Catch: java.lang.Exception -> L58
            java.lang.reflect.Method r0 = r1.getMethod(r2, r0)     // Catch: java.lang.Exception -> L58
            java.lang.Object[] r9 = new java.lang.Object[]{r9}     // Catch: java.lang.Exception -> L58
            java.lang.Object r9 = r0.invoke(r10, r9)     // Catch: java.lang.Exception -> L58
            android.view.Display[] r9 = (android.view.Display[]) r9     // Catch: java.lang.Exception -> L58
            int r10 = r9.length     // Catch: java.lang.Exception -> L58
            if (r10 <= 0) goto L68
            r9 = r9[r4]     // Catch: java.lang.Exception -> L58
            int r9 = r9.getDisplayId()     // Catch: java.lang.Exception -> L58
            return r9
        L58:
            r9 = move-exception
            java.lang.StringBuilder r10 = new java.lang.StringBuilder
            r10.<init>(r5)
            r10.append(r9)
            java.lang.String r9 = r10.toString()
            com.samsung.android.wallpaper.live.sdk.utils.SdkLog.e(r8, r9)
        L68:
            return r7
        L69:
            com.samsung.android.feature.SemFloatingFeature r9 = com.samsung.android.feature.SemFloatingFeature.getInstance()
            boolean r9 = r9.getBoolean(r2)
            if (r9 == 0) goto Leb
            boolean r9 = com.samsung.android.wallpaper.live.sdk.utils.SdkFoldUtils.isFolded(r10)
            if (r9 == 0) goto Leb
            goto Lec
        L7a:
            java.lang.Object r9 = r10.getSystemService(r6)
            android.hardware.display.DisplayManager r9 = (android.hardware.display.DisplayManager) r9
            android.view.Display[] r10 = r9.getDisplays()
            if (r10 == 0) goto Ld5
            java.lang.Class<android.hardware.display.DisplayManager> r1 = android.hardware.display.DisplayManager.class
            java.lang.String r2 = "isExternalDesktopDisplay"
            java.lang.Class[] r3 = new java.lang.Class[r0]     // Catch: java.lang.Exception -> Lc2
            java.lang.Class<android.view.Display> r6 = android.view.Display.class
            r3[r4] = r6     // Catch: java.lang.Exception -> Lc2
            java.lang.reflect.Method r1 = r1.getMethod(r2, r3)     // Catch: java.lang.Exception -> Lc2
            int r2 = r10.length     // Catch: java.lang.Exception -> Lc2
        L95:
            if (r4 >= r2) goto Ld5
            r3 = r10[r4]     // Catch: java.lang.Exception -> Lc2
            java.lang.Object[] r6 = new java.lang.Object[]{r3}     // Catch: java.lang.Exception -> Lc2
            java.lang.Object r6 = r1.invoke(r9, r6)     // Catch: java.lang.Exception -> Lc2
            java.lang.Boolean r6 = (java.lang.Boolean) r6     // Catch: java.lang.Exception -> Lc2
            boolean r6 = r6.booleanValue()     // Catch: java.lang.Exception -> Lc2
            if (r6 == 0) goto Lc4
            int r9 = r3.getDisplayId()     // Catch: java.lang.Exception -> Lc2
            java.lang.StringBuilder r10 = new java.lang.StringBuilder     // Catch: java.lang.Exception -> Lc2
            r10.<init>()     // Catch: java.lang.Exception -> Lc2
            java.lang.String r0 = "getDisplayIdByWhich : desktop detected : id = "
            r10.append(r0)     // Catch: java.lang.Exception -> Lc2
            r10.append(r9)     // Catch: java.lang.Exception -> Lc2
            java.lang.String r10 = r10.toString()     // Catch: java.lang.Exception -> Lc2
            com.samsung.android.wallpaper.live.sdk.utils.SdkLog.d(r8, r10)     // Catch: java.lang.Exception -> Lc2
            return r9
        Lc2:
            r9 = move-exception
            goto Lc6
        Lc4:
            int r4 = r4 + r0
            goto L95
        Lc6:
            java.lang.StringBuilder r10 = new java.lang.StringBuilder
            r10.<init>(r5)
            r10.append(r9)
            java.lang.String r9 = r10.toString()
            com.samsung.android.wallpaper.live.sdk.utils.SdkLog.e(r8, r9)
        Ld5:
            java.lang.String r9 = "getDisplayIdByWhich : failed to detect the desktop display id"
            com.samsung.android.wallpaper.live.sdk.utils.SdkLog.w(r8, r9)
            return r7
        Ldb:
            com.samsung.android.feature.SemFloatingFeature r9 = com.samsung.android.feature.SemFloatingFeature.getInstance()
            boolean r9 = r9.getBoolean(r2)
            if (r9 == 0) goto Lec
            boolean r9 = com.samsung.android.wallpaper.live.sdk.utils.SdkFoldUtils.isFolded(r10)
            if (r9 == 0) goto Lec
        Leb:
            return r0
        Lec:
            return r4
        */
        throw new UnsupportedOperationException("Method not decompiled: com.samsung.android.wallpaper.live.sdk.utils.DisplayUtils.getDisplayIdByWhich(int, android.content.Context):int");
    }

    public static Object getDisplayInfo(int i, Context context) {
        Class<?> cls;
        Display display = ((DisplayManager) context.getSystemService("display")).getDisplay(i);
        if (display == null) {
            return null;
        }
        try {
            try {
                cls = Class.forName("android.view.DisplayInfo");
            } catch (ClassNotFoundException | LinkageError e) {
                SdkLog.e("SdkReflectUtils", "Cannot load class: android.view.DisplayInfo : " + e.getMessage());
                cls = null;
            }
            Object newInstance = cls.newInstance();
            SdkReflectUtils.invoke(display, display.getClass().getMethod("getDisplayInfo", cls), newInstance);
            return newInstance;
        } catch (Exception e2) {
            SdkLog.e("DisplayUtils", "getDisplayInfoByWhich: e=" + e2);
            return null;
        }
    }

    public static int getDisplayRotation(int i, Context context) {
        int displayIdByWhich = getDisplayIdByWhich(i, context);
        if (displayIdByWhich != -1) {
            try {
                Object displayInfo = getDisplayInfo(displayIdByWhich, context);
                if (displayInfo != null) {
                    return ((Integer) SdkReflectUtils.getFieldValue(displayInfo.getClass().getField("rotation"), displayInfo)).intValue();
                }
            } catch (Exception e) {
                SdkLog.e("DisplayUtils", "getDisplayRotation: e=" + e);
                return 0;
            }
        }
        return 0;
    }

    /* JADX WARN: Removed duplicated region for block: B:5:0x0052  */
    /* JADX WARN: Removed duplicated region for block: B:8:0x0064  */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public static android.graphics.Point getDisplaySize(android.content.Context r6, int r7, int r8) {
        /*
            int r0 = getDisplayIdByWhich(r7, r6)
            r1 = -1
            java.lang.String r2 = "DisplayUtils"
            r3 = 0
            if (r0 != r1) goto Lc
        La:
            r1 = r3
            goto L50
        Lc:
            java.lang.Object r0 = getDisplayInfo(r0, r6)     // Catch: java.lang.Exception -> L3d
            if (r0 != 0) goto L13
            goto La
        L13:
            java.lang.Class r1 = r0.getClass()     // Catch: java.lang.Exception -> L3d
            java.lang.String r4 = "logicalWidth"
            java.lang.reflect.Field r4 = r1.getField(r4)     // Catch: java.lang.Exception -> L3d
            java.lang.String r5 = "logicalHeight"
            java.lang.reflect.Field r1 = r1.getField(r5)     // Catch: java.lang.Exception -> L3d
            java.lang.Object r4 = com.samsung.android.wallpaper.live.sdk.utils.SdkReflectUtils.getFieldValue(r4, r0)     // Catch: java.lang.Exception -> L3d
            java.lang.Integer r4 = (java.lang.Integer) r4     // Catch: java.lang.Exception -> L3d
            int r4 = r4.intValue()     // Catch: java.lang.Exception -> L3d
            java.lang.Object r0 = com.samsung.android.wallpaper.live.sdk.utils.SdkReflectUtils.getFieldValue(r1, r0)     // Catch: java.lang.Exception -> L3d
            java.lang.Integer r0 = (java.lang.Integer) r0     // Catch: java.lang.Exception -> L3d
            int r0 = r0.intValue()     // Catch: java.lang.Exception -> L3d
            android.graphics.Point r1 = new android.graphics.Point     // Catch: java.lang.Exception -> L3d
            r1.<init>(r4, r0)     // Catch: java.lang.Exception -> L3d
            goto L50
        L3d:
            r0 = move-exception
            java.lang.StringBuilder r1 = new java.lang.StringBuilder
            java.lang.String r4 = "getDisplaySize: e="
            r1.<init>(r4)
            r1.append(r0)
            java.lang.String r0 = r1.toString()
            com.samsung.android.wallpaper.live.sdk.utils.SdkLog.e(r2, r0)
            goto La
        L50:
            if (r1 != 0) goto L64
            java.lang.StringBuilder r6 = new java.lang.StringBuilder
            java.lang.String r8 = "getDisplaySize: failed to get display size. which="
            r6.<init>(r8)
            r6.append(r7)
            java.lang.String r6 = r6.toString()
            com.samsung.android.wallpaper.live.sdk.utils.SdkLog.e(r2, r6)
            return r3
        L64:
            int r6 = getDisplayRotation(r7, r6)
            r7 = 0
            r0 = 3
            r2 = 1
            if (r6 == r2) goto L72
            if (r6 != r0) goto L70
            goto L72
        L70:
            r6 = r7
            goto L73
        L72:
            r6 = r2
        L73:
            if (r8 == r2) goto L77
            if (r8 != r0) goto L78
        L77:
            r7 = r2
        L78:
            if (r6 != r7) goto L7b
            return r1
        L7b:
            android.graphics.Point r6 = new android.graphics.Point
            int r7 = r1.y
            int r8 = r1.x
            r6.<init>(r7, r8)
            return r6
        */
        throw new UnsupportedOperationException("Method not decompiled: com.samsung.android.wallpaper.live.sdk.utils.DisplayUtils.getDisplaySize(android.content.Context, int, int):android.graphics.Point");
    }
}
