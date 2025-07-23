package com.android.systemui.screenshot;

import android.graphics.Bitmap;
import android.os.Bundle;
import android.os.SystemClock;
import android.util.Log;
import android.window.WindowContext;
import com.android.systemui.screenshot.sep.BixbyShareController;
import com.android.systemui.screenshot.sep.ScreenCaptureHelper;
import com.android.systemui.screenshot.sep.ScreenshotUtils;
import com.samsung.android.app.smartcapture.screenshot.lib.IScreenshotService;
import com.samsung.android.app.smartcapture.screenshot.lib.RemoteScreenshotInterface;
import java.io.File;
import java.io.FileOutputStream;
import kotlin.Unit;
import kotlin.io.CloseableKt;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes2.dex */
public final class ScreenshotController$startCaptureAppRemoteServiceConnection$1 {
    public final /* synthetic */ long $connectionStartTime;
    public final /* synthetic */ ScreenshotController this$0;

    public ScreenshotController$startCaptureAppRemoteServiceConnection$1(long j, ScreenshotController screenshotController) {
        this.$connectionStartTime = j;
        this.this$0 = screenshotController;
    }

    public final void onConnectionResult(boolean z) {
        String str = ScreenshotController.TAG;
        Log.d(str, "onConnectionResult : success = " + z + " / elapsed = " + (System.currentTimeMillis() - this.$connectionStartTime));
        ScreenshotController screenshotController = this.this$0;
        if (screenshotController.isSavingFailed) {
            RemoteScreenshotInterface remoteScreenshotInterface = screenshotController.screenshotInterface;
            remoteScreenshotInterface.getClass();
            remoteScreenshotInterface.disconnect();
            this.this$0.screenshotInterface = null;
            Log.e(str, "Disconnect capture app remote screenshot service because saving image failed");
        } else if (z) {
            Bundle bundle = new Bundle();
            ScreenCaptureHelper screenCaptureHelper = this.this$0.screenCaptureHelper;
            screenCaptureHelper.getClass();
            bundle.putInt("originId", screenCaptureHelper.screenCaptureOrigin);
            ScreenCaptureHelper screenCaptureHelper2 = this.this$0.screenCaptureHelper;
            screenCaptureHelper2.getClass();
            bundle.putInt("captureMode", screenCaptureHelper2.screenCaptureType);
            ScreenCaptureHelper screenCaptureHelper3 = this.this$0.screenCaptureHelper;
            screenCaptureHelper3.getClass();
            bundle.putInt("captureDisplay", screenCaptureHelper3.capturedDisplayId);
            ScreenCaptureHelper screenCaptureHelper4 = this.this$0.screenCaptureHelper;
            screenCaptureHelper4.getClass();
            bundle.putInt("rotation", screenCaptureHelper4.displayRotation);
            ScreenCaptureHelper screenCaptureHelper5 = this.this$0.screenCaptureHelper;
            screenCaptureHelper5.getClass();
            bundle.putInt("safeInsetLeft", screenCaptureHelper5.safeInsetLeft);
            ScreenCaptureHelper screenCaptureHelper6 = this.this$0.screenCaptureHelper;
            screenCaptureHelper6.getClass();
            bundle.putInt("safeInsetTop", screenCaptureHelper6.safeInsetTop);
            ScreenCaptureHelper screenCaptureHelper7 = this.this$0.screenCaptureHelper;
            screenCaptureHelper7.getClass();
            bundle.putInt("safeInsetRight", screenCaptureHelper7.safeInsetRight);
            ScreenCaptureHelper screenCaptureHelper8 = this.this$0.screenCaptureHelper;
            screenCaptureHelper8.getClass();
            bundle.putInt("safeInsetBottom", screenCaptureHelper8.safeInsetBottom);
            String str2 = this.this$0.thumbnailImageFilePath;
            if (str2 == null) {
                str2 = null;
            }
            File file = new File(str2);
            File parentFile = file.getParentFile();
            if (parentFile != null) {
                parentFile.mkdirs();
            }
            FileOutputStream fileOutputStream = new FileOutputStream(file);
            ScreenshotController screenshotController2 = this.this$0;
            try {
                SystemClock.elapsedRealtime();
                Bitmap bitmap = screenshotController2.screenBitmap;
                bitmap.getClass();
                screenshotController2.screenBitmap.getClass();
                int width = (int) (r5.getWidth() * 0.25f);
                screenshotController2.screenBitmap.getClass();
                if (Bitmap.createScaledBitmap(bitmap, width, (int) (r7.getHeight() * 0.25f), true).compress(Bitmap.CompressFormat.JPEG, 50, fileOutputStream)) {
                    String str3 = screenshotController2.thumbnailImageFilePath;
                    if (str3 == null) {
                        str3 = null;
                    }
                    bundle.putString("thumbnailFilePath", str3);
                }
                Unit unit = Unit.INSTANCE;
                fileOutputStream.close();
                ScreenCaptureHelper screenCaptureHelper9 = this.this$0.screenCaptureHelper;
                screenCaptureHelper9.getClass();
                bundle.putBoolean("isSubDisplayCapture", ScreenshotUtils.isSubDisplayCapture(screenCaptureHelper9.capturedDisplayId));
                ScreenshotController screenshotController3 = this.this$0;
                WindowContext windowContext = screenshotController3.context;
                ScreenCaptureHelper screenCaptureHelper10 = screenshotController3.screenCaptureHelper;
                screenCaptureHelper10.getClass();
                if (screenCaptureHelper10.screenCaptureType == 1) {
                    ScreenCaptureHelper screenCaptureHelper11 = this.this$0.screenCaptureHelper;
                    screenCaptureHelper11.getClass();
                    bundle.putBoolean("statusBarVisible", screenCaptureHelper11.isStatusBarVisible);
                    ScreenCaptureHelper screenCaptureHelper12 = this.this$0.screenCaptureHelper;
                    screenCaptureHelper12.getClass();
                    bundle.putBoolean("navigationBarVisible", screenCaptureHelper12.isNavigationBarVisible);
                    ScreenCaptureHelper screenCaptureHelper13 = this.this$0.screenCaptureHelper;
                    screenCaptureHelper13.getClass();
                    bundle.putInt("statusBarHeight", screenCaptureHelper13.statusBarHeight);
                    ScreenCaptureHelper screenCaptureHelper14 = this.this$0.screenCaptureHelper;
                    screenCaptureHelper14.getClass();
                    bundle.putInt("navigationBarHeight", screenCaptureHelper14.navigationBarHeight);
                }
                BixbyShareController bixbyShareController = this.this$0.bixbyShareController;
                if (bixbyShareController != null && bixbyShareController.isBixbyCaptureShared) {
                    bundle.putBoolean("isSmartCaptureVisible", false);
                }
                RemoteScreenshotInterface remoteScreenshotInterface2 = this.this$0.screenshotInterface;
                remoteScreenshotInterface2.getClass();
                ScreenshotController screenshotController4 = this.this$0;
                long j = screenshotController4.screenshotTransactionId;
                String str4 = screenshotController4.imageFilePath;
                String str5 = str4 != null ? str4 : null;
                Log.d("[ScrCap]_RemoteScreenshotInterface", "notifyGlobalScreenshotStarted");
                IScreenshotService iScreenshotService = remoteScreenshotInterface2.mService;
                if (iScreenshotService != null) {
                    try {
                        ((IScreenshotService.Stub.Proxy) iScreenshotService).onGlobalScreenshotStarted(j, str5, bundle);
                    } catch (Exception e) {
                        Log.e("[ScrCap]_RemoteScreenshotInterface", "notifyGlobalScreenshotStarted : e=" + e);
                        Log.e("[ScrCap]_RemoteScreenshotInterface", e.toString());
                    }
                } else {
                    Log.e("[ScrCap]_RemoteScreenshotInterface", "notifyGlobalScreenshotStarted : No service connection");
                }
            } catch (Throwable th) {
                try {
                    throw th;
                } catch (Throwable th2) {
                    CloseableKt.closeFinally(fileOutputStream, th);
                    throw th2;
                }
            }
        } else {
            Log.e(str, "Failed to connect to capture app remote screenshot service");
            this.this$0.screenshotInterface = null;
        }
        ScreenshotController screenshotController5 = this.this$0;
        if (screenshotController5.screenshotInterface != null) {
            synchronized (screenshotController5.remoteServiceConnectionLock) {
                screenshotController5.isRemoteScreenshotConnectionListenerInvoked = true;
                screenshotController5.remoteServiceConnectionLock.notify();
                Unit unit2 = Unit.INSTANCE;
            }
        }
    }
}
