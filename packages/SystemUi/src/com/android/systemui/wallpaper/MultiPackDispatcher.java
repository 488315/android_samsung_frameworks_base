package com.android.systemui.wallpaper;

import android.app.WallpaperManager;
import android.content.Context;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Binder;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.support.v4.media.AbstractC0000x2c234b15;
import android.text.TextUtils;
import android.util.Log;
import androidx.constraintlayout.motion.widget.KeyAttributes$$ExternalSyntheticOutline0;
import com.android.keyguard.AbstractC0790xf8f53ce8;
import com.android.keyguard.KeyguardUpdateMonitor;
import com.android.systemui.keyguard.CustomizationProvider$$ExternalSyntheticOutline0;
import com.android.systemui.pluginlock.PluginLockUtils;
import com.android.systemui.wallpaper.KeyguardWallpaperController;
import com.android.systemui.wallpaper.MultiPackDispatcher;
import com.android.systemui.wallpaper.log.WallpaperLogger;
import com.android.systemui.wallpaper.log.WallpaperLoggerImpl;
import com.samsung.systemui.splugins.pluginlock.PluginLock;
import java.io.File;
import java.io.IOException;
import java.util.regex.Pattern;

/* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
/* loaded from: classes2.dex */
public final class MultiPackDispatcher {
    public static int mRetryCount;
    public static int mRetryCountSub;
    public final Context mContext;
    public MyHandler mHandler;
    public Uri mLastUri;
    public final WallpaperLogger mLoggerWrapper;
    public KeyguardWallpaperController.C36689 mOnApplyMultipackListener = null;
    public final PluginLockUtils mPluginLockUtils;

    /* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
    public final class MyHandler extends Handler {
        public MyHandler(Looper looper) {
            super(looper);
        }

        /* JADX WARN: Removed duplicated region for block: B:35:0x0179  */
        /* JADX WARN: Removed duplicated region for block: B:37:? A[RETURN, SYNTHETIC] */
        @Override // android.os.Handler
        /*
            Code decompiled incorrectly, please refer to instructions dump.
        */
        public final void handleMessage(Message message) {
            final int i;
            boolean z;
            MultiPackDispatcher multiPackDispatcher;
            int i2;
            Bundle data = message.getData();
            if (message.what != 0 || data == null) {
                return;
            }
            MultiPackDispatcher.this.getClass();
            if (KeyguardUpdateMonitor.getCurrentUser() != 0) {
                try {
                    MultiPackDispatcher.m2733$$Nest$mrequestImageWallpaper(MultiPackDispatcher.this, data.getString("wallpaper_path"));
                    return;
                } catch (Exception e) {
                    e.printStackTrace();
                    return;
                }
            }
            MultiPackDispatcher multiPackDispatcher2 = MultiPackDispatcher.this;
            WallpaperLogger wallpaperLogger = multiPackDispatcher2.mLoggerWrapper;
            Uri uri = (Uri) data.getParcelable("uri");
            if (uri == null) {
                Log.d("MultiPackDispatcher", "request2DLS: uri is null.");
                i = 4;
            } else {
                boolean z2 = data.getInt(PluginLock.KEY_SCREEN, 0) == 1;
                boolean booleanQueryParameter = uri.getBooleanQueryParameter("isMigration", false);
                boolean booleanQueryParameter2 = uri.getBooleanQueryParameter("isCustom", false);
                String string = data.getString("wallpaper_path");
                Log.i("MultiPackDispatcher", "request2DLS path= " + string + ", isSubDisplay = " + z2);
                long clearCallingIdentity = Binder.clearCallingIdentity();
                try {
                    try {
                        String str = "";
                        Bundle bundle = new Bundle();
                        String str2 = booleanQueryParameter2 ? "USER.PACK." : "MULTI.PACK.";
                        bundle.putString("name", z2 ? str2.concat("02") : str2.concat("01"));
                        bundle.putString("wallpaper_path", string);
                        bundle.putInt(PluginLock.KEY_SCREEN, z2 ? 1 : 0);
                        bundle.putInt("isMigration", booleanQueryParameter ? 1 : 0);
                        Bundle callProvider = multiPackDispatcher2.mPluginLockUtils.callProvider("user_pack", bundle);
                        if (callProvider != null) {
                            i = 0;
                            try {
                                z = callProvider.getBoolean("result", false);
                                str = callProvider.getString("reason");
                            } catch (Exception e2) {
                                e = e2;
                                Log.e("MultiPackDispatcher", "request2DLS: error = " + e.toString());
                                Binder.restoreCallingIdentity(clearCallingIdentity);
                                ((WallpaperLoggerImpl) wallpaperLogger).log("MultiPackDispatcher", "request2DLS success.");
                                final int i3 = data.getInt(PluginLock.KEY_SCREEN);
                                if (i != 0) {
                                }
                                multiPackDispatcher = MultiPackDispatcher.this;
                                if (multiPackDispatcher.mOnApplyMultipackListener == null) {
                                }
                            }
                        } else {
                            i = 0;
                            z = false;
                        }
                        if (!z) {
                            if (!TextUtils.isEmpty(str)) {
                                ((WallpaperLoggerImpl) wallpaperLogger).log("MultiPackDispatcher", "request2DLS fail." + str);
                            }
                            Binder.restoreCallingIdentity(clearCallingIdentity);
                            i = 3;
                        }
                    } catch (Throwable th) {
                        Binder.restoreCallingIdentity(clearCallingIdentity);
                        throw th;
                    }
                } catch (Exception e3) {
                    e = e3;
                    i = 0;
                }
                Binder.restoreCallingIdentity(clearCallingIdentity);
                ((WallpaperLoggerImpl) wallpaperLogger).log("MultiPackDispatcher", "request2DLS success.");
            }
            final int i32 = data.getInt(PluginLock.KEY_SCREEN);
            if (i != 0 || i == 2) {
                multiPackDispatcher = MultiPackDispatcher.this;
                if (multiPackDispatcher.mOnApplyMultipackListener == null) {
                    multiPackDispatcher.mHandler.postDelayed(new Runnable() { // from class: com.android.systemui.wallpaper.MultiPackDispatcher$MyHandler$$ExternalSyntheticLambda0
                        @Override // java.lang.Runnable
                        public final void run() {
                            MultiPackDispatcher.MyHandler myHandler = MultiPackDispatcher.MyHandler.this;
                            MultiPackDispatcher.this.mOnApplyMultipackListener.onMultipackApplied(i, i32);
                        }
                    }, 500L);
                    return;
                }
                return;
            }
            if (i != 3) {
                Log.e("MultiPackDispatcher", "handleMessage: NOT A CASE!");
                return;
            }
            if (i32 == 0) {
                i2 = MultiPackDispatcher.mRetryCount + 1;
                MultiPackDispatcher.mRetryCount = i2;
            } else {
                i2 = MultiPackDispatcher.mRetryCountSub + 1;
                MultiPackDispatcher.mRetryCountSub = i2;
            }
            if (i2 >= 20) {
                KeyguardWallpaperController.C36689 c36689 = MultiPackDispatcher.this.mOnApplyMultipackListener;
                if (c36689 != null) {
                    c36689.onMultipackApplied(1, i32);
                    return;
                }
                return;
            }
            Message message2 = new Message();
            Bundle bundle2 = new Bundle(data);
            message2.what = message.what;
            message2.setData(bundle2);
            sendMessageDelayed(message2, 700L);
        }
    }

    /* JADX WARN: Removed duplicated region for block: B:17:0x007e  */
    /* JADX WARN: Removed duplicated region for block: B:20:0x0085 A[EXC_TOP_SPLITTER, SYNTHETIC] */
    /* renamed from: -$$Nest$mrequestImageWallpaper, reason: not valid java name */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    public static void m2733$$Nest$mrequestImageWallpaper(MultiPackDispatcher multiPackDispatcher, String str) {
        File file;
        ((WallpaperLoggerImpl) multiPackDispatcher.mLoggerWrapper).log("MultiPackDispatcher", "requestImageWallpaper for subuser.");
        WallpaperManager wallpaperManager = WallpaperManager.getInstance(multiPackDispatcher.mContext);
        File file2 = new File(str);
        Bitmap bitmap = null;
        if (file2.exists()) {
            File[] listFiles = file2.listFiles();
            if (listFiles == null || listFiles.length <= 0) {
                Log.e("MultiPackDispatcher", "getFirstImage list is empty.");
                if (bitmap != null) {
                    Log.e("MultiPackDispatcher", "requestImageWallpaper bitmap is null");
                    return;
                }
                try {
                    Log.d("MultiPackDispatcher", "requestImageWallpaper setBitmap");
                    wallpaperManager.setBitmap(bitmap, null, false, 2, KeyguardUpdateMonitor.getCurrentUser());
                    return;
                } catch (IOException e) {
                    e.printStackTrace();
                    return;
                }
            }
            int length = listFiles.length;
            for (int i = 0; i < length; i++) {
                file = listFiles[i];
                if (file != null && file.getName().contains("1")) {
                    break;
                }
            }
        }
        file = null;
        if (file == null) {
            Log.d("MultiPackDispatcher", "getFirstImage firstFile is null");
        } else {
            String path = file.getPath();
            CustomizationProvider$$ExternalSyntheticOutline0.m135m("getFirstImage path = ", str, ", firstFilePath", path, "MultiPackDispatcher");
            if (str != null) {
                try {
                    File file3 = new File(path);
                    if (file3.exists() && file3.canRead()) {
                        bitmap = BitmapFactory.decodeFile(path);
                    }
                } catch (Exception e2) {
                    e2.printStackTrace();
                }
            }
            Log.e("MultiPackDispatcher", "getFirstImage return null");
        }
        if (bitmap != null) {
        }
    }

    public MultiPackDispatcher(Context context, WallpaperLogger wallpaperLogger, PluginLockUtils pluginLockUtils) {
        this.mContext = context;
        this.mLoggerWrapper = wallpaperLogger;
        this.mPluginLockUtils = pluginLockUtils;
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
            AbstractC0790xf8f53ce8.m80m(e, new StringBuilder("enableDlsIfDisabled: "), "MultiPackDispatcher");
            return false;
        }
    }

    public static int getContentType(int i, String str) {
        boolean z = (i & 48) != 0;
        return Pattern.matches("^\\S+.(?i)(gif)$", str) ? z ? 22 : 12 : Pattern.matches("^\\S+.(?i)(jpg|jpeg|png)$", str) ? z ? 21 : 11 : z ? 23 : 13;
    }

    public final boolean startMultipack(int i) {
        Context context = this.mContext;
        WallpaperManager wallpaperManager = WallpaperManager.getInstance(context);
        String m0m = AbstractC0000x2c234b15.m0m("startMultipack: which =", i);
        WallpaperLoggerImpl wallpaperLoggerImpl = (WallpaperLoggerImpl) this.mLoggerWrapper;
        wallpaperLoggerImpl.log("MultiPackDispatcher", m0m);
        if (!enableDlsIfDisabled(context)) {
            Log.e("MultiPackDispatcher", "startMultipack: Cannot start multipack. DLS is diabled.");
            return false;
        }
        Uri semGetUri = wallpaperManager.semGetUri(i);
        if (semGetUri == null) {
            wallpaperLoggerImpl.log("MultiPackDispatcher", "startMultipack: uri is null., uid = " + context.getUserId());
            return false;
        }
        if (this.mHandler == null) {
            this.mHandler = new MyHandler(Looper.myLooper());
        }
        this.mLastUri = semGetUri;
        String m21m = KeyAttributes$$ExternalSyntheticOutline0.m21m("/data/overlays/homewallpaper/", semGetUri.getHost() + semGetUri.getPath());
        wallpaperLoggerImpl.log("MultiPackDispatcher", "startMultipack: uri = " + semGetUri + ", fullPath = " + m21m + ", which = " + i);
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
        bundle.putString("wallpaper_path", m21m);
        bundle.putParcelable("uri", semGetUri);
        message.setData(bundle);
        this.mHandler.sendMessageDelayed(message, 100L);
        return true;
    }
}
