package com.samsung.android.wallpaper.live.sdk.utils;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Point;
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
import com.samsung.android.feature.SemFloatingFeature;
import java.lang.reflect.Method;

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
        long jElapsedRealtime = SystemClock.elapsedRealtime();
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
                    int width;
                    int height;
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
                            width = size2.getWidth();
                            height = size2.getHeight();
                        } else {
                            int iWidth = rect2.width();
                            int iHeight = rect2.height();
                            width = iWidth;
                            height = iHeight;
                        }
                        Surface surface = surfaceHolder2.getSurface();
                        SdkLog.i("DisplayUtils", "copySurfaceToBitmapSync: width = " + width + ", height = " + height + ", validSurface=" + surface.isValid());
                        Bitmap bitmapCreateBitmap = Bitmap.createBitmap(width, height, Bitmap.Config.ARGB_8888);
                        bitmapArr2[0] = bitmapCreateBitmap;
                        PixelCopy.request(surface, rect3, bitmapCreateBitmap, new PixelCopy.OnPixelCopyFinishedListener() { // from class: com.samsung.android.wallpaper.live.sdk.utils.DisplayUtils$$ExternalSyntheticLambda1
                            @Override // android.view.PixelCopy.OnPixelCopyFinishedListener
                            public final void onPixelCopyFinished(int i) {
                                Bitmap[] bitmapArr3 = bitmapArr2;
                                boolean[] zArr3 = zArr2;
                                Object obj3 = obj2;
                                if (i != 0) {
                                    SdkLog.i("DisplayUtils", "copySurfaceToBitmapSync : copy failed. code=" + i);
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
        StringBuilder sbM = SnapshotStateObserver$$ExternalSyntheticOutline0.m("copySurfaceToBitmapSync : elapsed=", SystemClock.elapsedRealtime() - jElapsedRealtime, ", surface=(");
        sbM.append(surfaceFrame.width());
        sbM.append("x");
        sbM.append(surfaceFrame.height());
        sbM.append("), src=");
        sbM.append(rect);
        sbM.append(", outBmp=");
        sbM.append(BitmapUtils.getBitmapSizeString(bitmap));
        sbM.append(", pxCpyFin=");
        sbM.append(zArr[0]);
        sbM.append(", isSuccess=");
        sbM.append(z);
        SdkLog.i("DisplayUtils", sbM.toString());
        return bitmap;
    }

    /* JADX WARN: Removed duplicated region for block: B:43:0x00eb A[RETURN] */
    /* JADX WARN: Removed duplicated region for block: B:44:0x00ec A[RETURN] */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    public static int getDisplayIdByWhich(int i, Context context) throws NoSuchMethodException, SecurityException {
        int i2 = i & 60;
        if (i2 != 4) {
            if (i2 != 8) {
                if (i2 == 16) {
                    return (SemFloatingFeature.getInstance().getBoolean("SEC_FLOATING_FEATURE_FRAMEWORK_SUPPORT_FOLDABLE_TYPE_FOLD") && SdkFoldUtils.isFolded(context)) ? 0 : 1;
                }
                if (i2 != 32) {
                    SdkLog.e("DisplayUtils", "getDisplayIdByWhich : unsupported mode. mode=" + i);
                    return -1;
                }
                try {
                    Display[] displayArr = (Display[]) DisplayManager.class.getMethod("getDisplays", String.class).invoke((DisplayManager) context.getSystemService("display"), "com.samsung.android.hardware.display.category.VIEW_COVER_DISPLAY");
                    if (displayArr.length > 0) {
                        return displayArr[0].getDisplayId();
                    }
                } catch (Exception e) {
                    SdkLog.e("DisplayUtils", "getDisplayIdByWhich : e=" + e);
                }
                return -1;
            }
            DisplayManager displayManager = (DisplayManager) context.getSystemService("display");
            Display[] displays = displayManager.getDisplays();
            if (displays != null) {
                try {
                    Method method = DisplayManager.class.getMethod("isExternalDesktopDisplay", Display.class);
                    for (Display display : displays) {
                        if (((Boolean) method.invoke(displayManager, display)).booleanValue()) {
                            int displayId = display.getDisplayId();
                            SdkLog.d("DisplayUtils", "getDisplayIdByWhich : desktop detected : id = " + displayId);
                            return displayId;
                        }
                    }
                } catch (Exception e2) {
                    SdkLog.e("DisplayUtils", "getDisplayIdByWhich : e=" + e2);
                }
            }
            SdkLog.w("DisplayUtils", "getDisplayIdByWhich : failed to detect the desktop display id");
            return -1;
        }
        if (!SemFloatingFeature.getInstance().getBoolean("SEC_FLOATING_FEATURE_FRAMEWORK_SUPPORT_FOLDABLE_TYPE_FOLD") || !SdkFoldUtils.isFolded(context)) {
        }
    }

    public static Object getDisplayInfo(int i, Context context) throws IllegalAccessException, InstantiationException, ClassNotFoundException {
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
            Object objNewInstance = cls.newInstance();
            SdkReflectUtils.invoke(display, display.getClass().getMethod("getDisplayInfo", cls), objNewInstance);
            return objNewInstance;
        } catch (Exception e2) {
            SdkLog.e("DisplayUtils", "getDisplayInfoByWhich: e=" + e2);
            return null;
        }
    }

    public static int getDisplayRotation(int i, Context context) throws NoSuchMethodException, SecurityException {
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

    public static Point getDisplaySize(Context context, int i, int i2) throws NoSuchMethodException, SecurityException {
        Object displayInfo;
        Point point;
        int displayIdByWhich = getDisplayIdByWhich(i, context);
        if (displayIdByWhich == -1) {
            point = null;
        } else {
            try {
                displayInfo = getDisplayInfo(displayIdByWhich, context);
            } catch (Exception e) {
                SdkLog.e("DisplayUtils", "getDisplaySize: e=" + e);
            }
            if (displayInfo == null) {
                point = null;
            } else {
                Class<?> cls = displayInfo.getClass();
                point = new Point(((Integer) SdkReflectUtils.getFieldValue(cls.getField("logicalWidth"), displayInfo)).intValue(), ((Integer) SdkReflectUtils.getFieldValue(cls.getField("logicalHeight"), displayInfo)).intValue());
            }
        }
        if (point != null) {
            int displayRotation = getDisplayRotation(i, context);
            return (displayRotation == 1 || displayRotation == 3) == (i2 == 1 || i2 == 3) ? point : new Point(point.y, point.x);
        }
        SdkLog.e("DisplayUtils", "getDisplaySize: failed to get display size. which=" + i);
        return null;
    }
}
