package com.android.systemui.wallpaper;

import android.app.WallpaperManager;
import android.content.Context;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.support.v4.media.MediaBrowserCompat$MediaBrowserImplBase$$ExternalSyntheticOutline0;
import android.util.Log;
import androidx.compose.ui.platform.AndroidCompositionLocals_androidKt$$ExternalSyntheticOutline0;
import com.android.keyguard.KeyguardUCMViewController$StateMachine$$ExternalSyntheticOutline0;
import com.android.systemui.pluginlock.PluginLockUtils;
import com.android.systemui.wallpaper.PluginWallpaperController;
import com.android.systemui.wallpaper.log.WallpaperLogger;
import com.android.systemui.wallpaper.log.WallpaperLoggerImpl;
import com.samsung.systemui.splugins.pluginlock.PluginLock;

public final class MultiPackDispatcher {
    public static int mRetryCount;
    public static int mRetryCountSub;
    public final Context mContext;
    public MyHandler mHandler;
    public final WallpaperLogger mLoggerWrapper;
    public PluginWallpaperController.AnonymousClass1 mOnApplyMultipackListener = null;
    public final PluginLockUtils mPluginLockUtils;
    public final int mSelectedUserId;

    public final class MyHandler extends Handler {
        public MyHandler(Looper looper) {
            super(looper);
        }

        @Override // android.os.Handler
        /*
            Code decompiled incorrectly, please refer to instructions dump.
            To view partially-correct code enable 'Show inconsistent code' option in preferences
        */
        public final void handleMessage(android.os.Message r20) {
            /*
                Method dump skipped, instructions count: 373
                To view this dump change 'Code comments level' option to 'DEBUG'
            */
            throw new UnsupportedOperationException("Method not decompiled: com.android.systemui.wallpaper.MultiPackDispatcher.MyHandler.handleMessage(android.os.Message):void");
        }
    }

    /* renamed from: -$$Nest$mrequestImageWallpaper, reason: not valid java name */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public static void m2372$$Nest$mrequestImageWallpaper(com.android.systemui.wallpaper.MultiPackDispatcher r9, java.lang.String r10) {
        /*
            com.android.systemui.wallpaper.log.WallpaperLogger r0 = r9.mLoggerWrapper
            com.android.systemui.wallpaper.log.WallpaperLoggerImpl r0 = (com.android.systemui.wallpaper.log.WallpaperLoggerImpl) r0
            java.lang.String r1 = "MultiPackDispatcher"
            java.lang.String r2 = "requestImageWallpaper for subuser."
            r0.log(r1, r2)
            android.content.Context r0 = r9.mContext
            android.app.WallpaperManager r2 = android.app.WallpaperManager.getInstance(r0)
            java.io.File r0 = new java.io.File
            r0.<init>(r10)
            boolean r3 = r0.exists()
            r4 = 0
            if (r3 == 0) goto L47
            java.io.File[] r0 = r0.listFiles()
            if (r0 == 0) goto L40
            int r3 = r0.length
            if (r3 > 0) goto L28
            goto L40
        L28:
            int r3 = r0.length
            r5 = 0
        L2a:
            if (r5 >= r3) goto L47
            r6 = r0[r5]
            if (r6 == 0) goto L3d
            java.lang.String r7 = r6.getName()
            java.lang.String r8 = "1"
            boolean r7 = r7.contains(r8)
            if (r7 == 0) goto L3d
            goto L48
        L3d:
            int r5 = r5 + 1
            goto L2a
        L40:
            java.lang.String r10 = "getFirstImage list is empty."
            android.util.Log.e(r1, r10)
        L45:
            r3 = r4
            goto L7e
        L47:
            r6 = r4
        L48:
            if (r6 != 0) goto L50
            java.lang.String r10 = "getFirstImage firstFile is null"
            android.util.Log.d(r1, r10)
            goto L45
        L50:
            java.lang.String r0 = r6.getPath()
            java.lang.String r3 = "getFirstImage path = "
            java.lang.String r5 = ", firstFilePath"
            com.android.systemui.bixby2.controller.MWBixbyController$$ExternalSyntheticOutline0.m(r3, r10, r5, r0, r1)
            if (r10 == 0) goto L78
            java.io.File r10 = new java.io.File     // Catch: java.lang.Exception -> L74
            r10.<init>(r0)     // Catch: java.lang.Exception -> L74
            boolean r3 = r10.exists()     // Catch: java.lang.Exception -> L74
            if (r3 == 0) goto L78
            boolean r10 = r10.canRead()     // Catch: java.lang.Exception -> L74
            if (r10 == 0) goto L78
            android.graphics.Bitmap r10 = android.graphics.BitmapFactory.decodeFile(r0)     // Catch: java.lang.Exception -> L74
            r3 = r10
            goto L7e
        L74:
            r10 = move-exception
            r10.printStackTrace()
        L78:
            java.lang.String r10 = "getFirstImage return null"
            android.util.Log.e(r1, r10)
            goto L45
        L7e:
            if (r3 != 0) goto L87
            java.lang.String r9 = "requestImageWallpaper bitmap is null"
            android.util.Log.e(r1, r9)
            goto L9a
        L87:
            java.lang.String r10 = "requestImageWallpaper setBitmap"
            android.util.Log.d(r1, r10)     // Catch: java.io.IOException -> L96
            int r7 = r9.mSelectedUserId     // Catch: java.io.IOException -> L96
            r5 = 0
            r6 = 2
            r4 = 0
            r2.setBitmap(r3, r4, r5, r6, r7)     // Catch: java.io.IOException -> L96
            goto L9a
        L96:
            r9 = move-exception
            r9.printStackTrace()
        L9a:
            return
        */
        throw new UnsupportedOperationException("Method not decompiled: com.android.systemui.wallpaper.MultiPackDispatcher.m2372$$Nest$mrequestImageWallpaper(com.android.systemui.wallpaper.MultiPackDispatcher, java.lang.String):void");
    }

    public MultiPackDispatcher(Context context, WallpaperLogger wallpaperLogger, PluginLockUtils pluginLockUtils, int i) {
        this.mContext = context;
        this.mLoggerWrapper = wallpaperLogger;
        this.mPluginLockUtils = pluginLockUtils;
        this.mSelectedUserId = i;
    }

    public static boolean enableDlsIfDisabled(Context context) {
        try {
            PackageManager packageManager = context.getPackageManager();
            int applicationEnabledSetting = packageManager.getApplicationEnabledSetting("com.samsung.android.dynamiclock");
            if (applicationEnabledSetting == 2) {
                Log.d("MultiPackDispatcher", "enableDlsIfDisabled: state = " + applicationEnabledSetting);
                packageManager.setApplicationEnabledSetting("com.samsung.android.dynamiclock", 1, 0);
                if (packageManager.getApplicationEnabledSetting("com.samsung.android.dynamiclock") == 2) {
                    Log.e("MultiPackDispatcher", "enableDlsIfDisabled: Failed to enable dls.");
                    return false;
                }
            }
            return true;
        } catch (Exception e) {
            KeyguardUCMViewController$StateMachine$$ExternalSyntheticOutline0.m(e, new StringBuilder("enableDlsIfDisabled: "), "MultiPackDispatcher");
            return false;
        }
    }

    public final boolean startMultipack(int i) {
        WallpaperManager wallpaperManager = WallpaperManager.getInstance(this.mContext);
        String m = MediaBrowserCompat$MediaBrowserImplBase$$ExternalSyntheticOutline0.m(i, "startMultipack: which =");
        WallpaperLoggerImpl wallpaperLoggerImpl = (WallpaperLoggerImpl) this.mLoggerWrapper;
        wallpaperLoggerImpl.log("MultiPackDispatcher", m);
        if (!enableDlsIfDisabled(this.mContext)) {
            Log.e("MultiPackDispatcher", "startMultipack: Cannot start multipack. DLS is diabled.");
            return false;
        }
        Uri semGetUri = wallpaperManager.semGetUri(i);
        if (semGetUri == null) {
            wallpaperLoggerImpl.log("MultiPackDispatcher", "startMultipack: uri is null., uid = " + this.mContext.getUserId());
            return false;
        }
        if (this.mHandler == null) {
            this.mHandler = new MyHandler(Looper.myLooper());
        }
        String m2 = AndroidCompositionLocals_androidKt$$ExternalSyntheticOutline0.m("/data/overlays/homewallpaper/", semGetUri.getHost() + semGetUri.getPath());
        wallpaperLoggerImpl.log("MultiPackDispatcher", "startMultipack: uri = " + semGetUri + ", fullPath = " + m2 + ", which = " + i);
        int i2 = i & 48;
        if (i2 != 0) {
            mRetryCountSub = 0;
        } else {
            mRetryCount = 0;
        }
        Message message = new Message();
        Bundle bundle = new Bundle();
        message.what = 0;
        bundle.putInt(PluginLock.KEY_SCREEN, i2 != 0 ? 1 : 0);
        bundle.putString("wallpaper_path", m2);
        bundle.putParcelable("uri", semGetUri);
        message.setData(bundle);
        this.mHandler.sendMessageDelayed(message, 100L);
        return true;
    }
}
