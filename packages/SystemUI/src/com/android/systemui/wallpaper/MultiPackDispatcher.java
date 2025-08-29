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
import android.support.v4.media.MediaBrowserCompat$MediaBrowserImplBase$$ExternalSyntheticOutline0;
import android.text.TextUtils;
import android.util.Log;
import androidx.compose.ui.platform.AndroidCompositionLocals_androidKt$$ExternalSyntheticOutline0;
import com.android.keyguard.KeyguardUCMViewController$StateMachine$$ExternalSyntheticOutline0;
import com.android.settingslib.volume.MediaSessions$H$$ExternalSyntheticOutline0;
import com.android.systemui.pluginlock.PluginLockUtils;
import com.android.systemui.wallpaper.MultiPackDispatcher;
import com.android.systemui.wallpaper.PluginWallpaperController;
import com.android.systemui.wallpaper.log.WallpaperLogger;
import com.android.systemui.wallpaper.log.WallpaperLoggerImpl;
import com.samsung.systemui.splugins.pluginlock.PluginLock;
import java.io.File;
import java.io.IOException;

/* loaded from: classes3.dex */
public class MultiPackDispatcher {
    public static int mRetryCount;
    public static int mRetryCountSub;
    public final Context mContext;
    public MyHandler mHandler;
    public final WallpaperLogger mLoggerWrapper;
    public PluginWallpaperController.AnonymousClass1 mOnApplyMultipackListener = null;
    public final PluginLockUtils mPluginLockUtils;
    public final int mSelectedUserId;

    public class MyHandler extends Handler {
        public MyHandler(Looper looper) {
            super(looper);
        }

        /* JADX WARN: Multi-variable type inference failed */
        /* JADX WARN: Removed duplicated region for block: B:67:0x016e  */
        /* JADX WARN: Removed duplicated region for block: B:83:? A[RETURN, SYNTHETIC] */
        /* JADX WARN: Type inference failed for: r11v1 */
        /* JADX WARN: Type inference failed for: r11v2, types: [boolean, int] */
        /* JADX WARN: Type inference failed for: r11v3 */
        @Override // android.os.Handler
        /*
            Code decompiled incorrectly, please refer to instructions dump.
        */
        public final void handleMessage(Message message) {
            int i;
            ?? r11;
            int i2;
            final int i3;
            boolean z;
            String string;
            int i4;
            Bundle data = message.getData();
            if (message.what != 0 || data == null) {
                return;
            }
            MultiPackDispatcher multiPackDispatcher = MultiPackDispatcher.this;
            if (multiPackDispatcher.mSelectedUserId != 0) {
                try {
                    MultiPackDispatcher.m3225$$Nest$mrequestImageWallpaper(multiPackDispatcher, data.getString("wallpaper_path"));
                    return;
                } catch (Exception e) {
                    e.printStackTrace();
                    return;
                }
            }
            WallpaperLogger wallpaperLogger = multiPackDispatcher.mLoggerWrapper;
            Uri uri = (Uri) data.getParcelable("uri");
            if (uri == null) {
                Log.d("MultiPackDispatcher", "request2DLS: uri is null.");
                i3 = 4;
                i = 1;
            } else {
                if (data.getInt(PluginLock.KEY_SCREEN, 0) == 1) {
                    r11 = 1;
                    i = 1;
                } else {
                    i = 1;
                    r11 = 0;
                }
                boolean booleanQueryParameter = uri.getBooleanQueryParameter("isMigration", false);
                boolean booleanQueryParameter2 = uri.getBooleanQueryParameter("isCustom", false);
                String string2 = data.getString("wallpaper_path");
                Log.i("MultiPackDispatcher", "request2DLS path= " + string2 + ", isSubDisplay = " + ((boolean) r11));
                long jClearCallingIdentity = Binder.clearCallingIdentity();
                try {
                    try {
                        Bundle bundle = new Bundle();
                        String str = booleanQueryParameter2 ? "USER.PACK." : "MULTI.PACK.";
                        bundle.putString("name", r11 != 0 ? str.concat("02") : str.concat("01"));
                        bundle.putString("wallpaper_path", string2);
                        bundle.putInt(PluginLock.KEY_SCREEN, r11);
                        bundle.putInt("isMigration", booleanQueryParameter ? 1 : 0);
                        Bundle bundleRequestMultiPack = multiPackDispatcher.mPluginLockUtils.requestMultiPack(bundle);
                        if (bundleRequestMultiPack != null) {
                            i2 = 0;
                            try {
                                z = bundleRequestMultiPack.getBoolean("result", false);
                                string = bundleRequestMultiPack.getString("reason");
                            } catch (Exception e2) {
                                e = e2;
                                Log.e("MultiPackDispatcher", "request2DLS: error = " + e.toString());
                                Binder.restoreCallingIdentity(jClearCallingIdentity);
                                ((WallpaperLoggerImpl) wallpaperLogger).log("MultiPackDispatcher", "request2DLS success.");
                                i3 = i2;
                                final int i5 = data.getInt(PluginLock.KEY_SCREEN);
                                if (i3 != 0) {
                                }
                                if (multiPackDispatcher.mOnApplyMultipackListener == null) {
                                }
                            }
                        } else {
                            i2 = 0;
                            z = false;
                            string = "";
                        }
                    } catch (Exception e3) {
                        e = e3;
                        i2 = 0;
                    }
                    if (z) {
                        Binder.restoreCallingIdentity(jClearCallingIdentity);
                        ((WallpaperLoggerImpl) wallpaperLogger).log("MultiPackDispatcher", "request2DLS success.");
                        i3 = i2;
                    } else {
                        if (!TextUtils.isEmpty(string)) {
                            ((WallpaperLoggerImpl) wallpaperLogger).log("MultiPackDispatcher", "request2DLS fail." + string);
                        }
                        Binder.restoreCallingIdentity(jClearCallingIdentity);
                        i3 = 3;
                    }
                } catch (Throwable th) {
                    Binder.restoreCallingIdentity(jClearCallingIdentity);
                    throw th;
                }
            }
            final int i52 = data.getInt(PluginLock.KEY_SCREEN);
            if (i3 != 0 || i3 == 2) {
                if (multiPackDispatcher.mOnApplyMultipackListener == null) {
                    multiPackDispatcher.mHandler.postDelayed(new Runnable(i3, i52) { // from class: com.android.systemui.wallpaper.MultiPackDispatcher$MyHandler$$ExternalSyntheticLambda0
                        public final /* synthetic */ int f$1;

                        @Override // java.lang.Runnable
                        public final void run() {
                            MultiPackDispatcher.MyHandler myHandler = this.f$0;
                            int i6 = this.f$1;
                            MultiPackDispatcher.this.mOnApplyMultipackListener.getClass();
                            PluginWallpaperController.AnonymousClass1.onMultipackApplied(i6);
                        }
                    }, 500L);
                    return;
                }
                return;
            }
            if (i3 != 3) {
                Log.e("MultiPackDispatcher", "handleMessage: NOT A CASE!");
                return;
            }
            if (i52 == 0) {
                i4 = MultiPackDispatcher.mRetryCount + 1;
                MultiPackDispatcher.mRetryCount = i4;
            } else {
                i4 = MultiPackDispatcher.mRetryCountSub + 1;
                MultiPackDispatcher.mRetryCountSub = i4;
            }
            if (i4 >= 20) {
                if (multiPackDispatcher.mOnApplyMultipackListener != null) {
                    PluginWallpaperController.AnonymousClass1.onMultipackApplied(i);
                }
            } else {
                Message message2 = new Message();
                Bundle bundle2 = new Bundle(data);
                message2.what = message.what;
                message2.setData(bundle2);
                sendMessageDelayed(message2, 700L);
            }
        }
    }

    /* JADX WARN: Removed duplicated region for block: B:21:0x004a  */
    /* JADX WARN: Removed duplicated region for block: B:22:0x0050  */
    /* JADX WARN: Removed duplicated region for block: B:32:0x0078  */
    /* renamed from: -$$Nest$mrequestImageWallpaper, reason: not valid java name */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    public static void m3225$$Nest$mrequestImageWallpaper(MultiPackDispatcher multiPackDispatcher, String str) {
        File file;
        File file2;
        ((WallpaperLoggerImpl) multiPackDispatcher.mLoggerWrapper).log("MultiPackDispatcher", "requestImageWallpaper for subuser.");
        WallpaperManager wallpaperManager = WallpaperManager.getInstance(multiPackDispatcher.mContext);
        File file3 = new File(str);
        Bitmap bitmapDecodeFile = null;
        if (file3.exists()) {
            File[] fileArrListFiles = file3.listFiles();
            if (fileArrListFiles == null || fileArrListFiles.length <= 0) {
                Log.e("MultiPackDispatcher", "getFirstImage list is empty.");
            } else {
                int length = fileArrListFiles.length;
                for (int i = 0; i < length; i++) {
                    file = fileArrListFiles[i];
                    if (file != null && file.getName().contains("1")) {
                        break;
                    }
                }
                file = null;
                if (file != null) {
                }
            }
        } else {
            file = null;
            if (file != null) {
                Log.d("MultiPackDispatcher", "getFirstImage firstFile is null");
            } else {
                String path = file.getPath();
                MediaSessions$H$$ExternalSyntheticOutline0.m("getFirstImage path = ", str, ", firstFilePath", path, "MultiPackDispatcher");
                if (str != null) {
                    try {
                        file2 = new File(path);
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                    if (file2.exists() && file2.canRead()) {
                        bitmapDecodeFile = BitmapFactory.decodeFile(path);
                    } else {
                        Log.e("MultiPackDispatcher", "getFirstImage return null");
                    }
                }
            }
        }
        Bitmap bitmap = bitmapDecodeFile;
        if (bitmap == null) {
            Log.e("MultiPackDispatcher", "requestImageWallpaper bitmap is null");
            return;
        }
        try {
            Log.d("MultiPackDispatcher", "requestImageWallpaper setBitmap");
            wallpaperManager.setBitmap(bitmap, null, false, 2, multiPackDispatcher.mSelectedUserId);
        } catch (IOException e2) {
            e2.printStackTrace();
        }
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
        String strM = MediaBrowserCompat$MediaBrowserImplBase$$ExternalSyntheticOutline0.m(i, "startMultipack: which =");
        WallpaperLoggerImpl wallpaperLoggerImpl = (WallpaperLoggerImpl) this.mLoggerWrapper;
        wallpaperLoggerImpl.log("MultiPackDispatcher", strM);
        if (!enableDlsIfDisabled(this.mContext)) {
            Log.e("MultiPackDispatcher", "startMultipack: Cannot start multipack. DLS is diabled.");
            return false;
        }
        Uri uriSemGetUri = wallpaperManager.semGetUri(i);
        if (uriSemGetUri == null) {
            wallpaperLoggerImpl.log("MultiPackDispatcher", "startMultipack: uri is null., uid = " + this.mContext.getUserId());
            return false;
        }
        if (this.mHandler == null) {
            this.mHandler = new MyHandler(Looper.myLooper());
        }
        String strM2 = AndroidCompositionLocals_androidKt$$ExternalSyntheticOutline0.m("/data/overlays/homewallpaper/", uriSemGetUri.getHost() + uriSemGetUri.getPath());
        wallpaperLoggerImpl.log("MultiPackDispatcher", "startMultipack: uri = " + uriSemGetUri + ", fullPath = " + strM2 + ", which = " + i);
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
        bundle.putString("wallpaper_path", strM2);
        bundle.putParcelable("uri", uriSemGetUri);
        message.setData(bundle);
        this.mHandler.sendMessageDelayed(message, 100L);
        return true;
    }
}
