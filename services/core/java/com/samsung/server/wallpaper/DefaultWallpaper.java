package com.samsung.server.wallpaper;

import android.app.SemWallpaperResourcesInfo;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.os.UserHandle;
import android.provider.Settings;
import com.android.server.wallpaper.WallpaperManagerService;
import com.samsung.android.wallpaper.Rune;
import com.samsung.android.wallpaper.utils.WhichChecker;

/* loaded from: classes2.dex */
public class DefaultWallpaper {
    public final WallpaperManagerService.SemCallback mCallback;
    public final Context mContext;
    public final Handler mHandler = new Handler(Looper.getMainLooper()) { // from class: com.samsung.server.wallpaper.DefaultWallpaper.1
        @Override // android.os.Handler
        public void handleMessage(Message message) {
            String str;
            String str2;
            switch (message.what) {
                case 1004:
                    int i = message.arg2;
                    if (WhichChecker.isDex(i)) {
                        str = "dex_system_wallpaper_transparency";
                    } else {
                        str = WhichChecker.isSubDisplay(i) ? "sub_display_system_wallpaper_transparency" : "android.wallpaper.settings_systemui_transparency";
                    }
                    DefaultWallpaper.this.setSettingsSystemUiTransparency(message.arg1, str);
                    break;
                case 1005:
                    int i2 = message.arg2;
                    if (Rune.SUPPORT_SUB_DISPLAY_MODE && (i2 & 60) == 16) {
                        Settings.System.putIntForUser(DefaultWallpaper.this.mContext.getContentResolver(), "lockscreen_wallpaper_sub", message.arg1, -2);
                        break;
                    } else {
                        Settings.System.putIntForUser(DefaultWallpaper.this.mContext.getContentResolver(), "lockscreen_wallpaper", message.arg1, -2);
                        break;
                    }
                case 1006:
                    int i3 = message.arg2;
                    if (WhichChecker.isDex(i3)) {
                        str2 = "dex_lockscreen_wallpaper_transparency";
                    } else {
                        str2 = WhichChecker.isSubDisplay(i3) ? "sub_display_lockscreen_wallpaper_transparency" : "lockscreen_wallpaper_transparent";
                    }
                    DefaultWallpaper.this.setSettingsSystemUiTransparency(message.arg1, str2);
                    break;
                case 1007:
                    Intent intent = new Intent();
                    intent.setAction("com.sec.android.intent.action.WALLPAPER_CHANGED");
                    intent.setFlags(16777216);
                    DefaultWallpaper.this.mContext.sendBroadcastAsUser(intent, new UserHandle(DefaultWallpaper.this.mService.getCurrentUserId()));
                    Log.m129d("DefaultWallpaper", "send wallpaperChangedIntent");
                    break;
                case 1008:
                    Log.m129d("DefaultWallpaper", "msg MSG_UPDATE_DEFAULT_WALLPAPER");
                    DefaultWallpaper.this.mCallback.rebindDefaultWallpaperIfNeeded();
                    break;
            }
        }
    };
    public SemWallpaperResourcesInfo mSemWallpaperResourcesInfo;
    public final SemWallpaperManagerService mService;

    public DefaultWallpaper(Context context, WallpaperManagerService.SemCallback semCallback, SemWallpaperManagerService semWallpaperManagerService, SemWallpaperResourcesInfo semWallpaperResourcesInfo) {
        Log.m129d("DefaultWallpaper", "DefaultWallpaper");
        this.mContext = context;
        this.mCallback = semCallback;
        this.mService = semWallpaperManagerService;
        this.mSemWallpaperResourcesInfo = semWallpaperResourcesInfo;
    }

    public void setSWPTypePreload(int i, int i2) {
        Message obtainMessage = this.mHandler.obtainMessage(1004);
        obtainMessage.arg1 = i;
        obtainMessage.arg2 = i2;
        this.mHandler.sendMessage(obtainMessage);
    }

    public void setKWPTypeLiveWallpaper(int i) {
        int i2 = 0;
        if (!Rune.SUPPORT_COVER_DISPLAY_WATCHFACE && this.mService.mSubDisplayMode.getLidState() == 0) {
            i2 = 16;
        }
        setKWPTypeLiveWallpaper(i, i2);
    }

    public void setKWPTypeLiveWallpaper(int i, int i2) {
        Message obtainMessage = this.mHandler.obtainMessage(1005);
        obtainMessage.arg1 = i;
        obtainMessage.arg2 = i2;
        this.mHandler.sendMessage(obtainMessage);
    }

    public void setKWPTypePreload(int i, int i2) {
        Message obtainMessage = this.mHandler.obtainMessage(1006);
        obtainMessage.arg1 = i;
        obtainMessage.arg2 = i2;
        this.mHandler.sendMessage(obtainMessage);
    }

    public void updateTransparencySettingIfNeed(String str, int i, boolean z) {
        boolean z2 = true;
        if (!WhichChecker.isDex(i) ? "com.samsung.android.themecenter".equals(str) || "service.odtcfactory.sec.com.odtcfactoryservice".equals(str) || "com.samsung.android.app.dressroom".equals(str) : "com.sec.android.app.desktoplauncher".equals(str)) {
            z2 = false;
        }
        if (z2) {
            if (WhichChecker.containsSystem(i)) {
                setSWPTypePreload(z ? 1 : 0, i);
            }
            if (WhichChecker.containsLock(i)) {
                setKWPTypePreload(z ? 1 : 0, i);
            }
        }
    }

    public final void setSettingsSystemUiTransparency(int i, String str) {
        Log.m129d("DefaultWallpaper", "setSettingsSystemUiTransparency : " + i + " name=" + str);
        Settings.System.putIntForUser(this.mContext.getContentResolver(), str, i, -2);
    }

    public void sendWallpaperChangeIntent() {
        this.mHandler.sendMessage(this.mHandler.obtainMessage(1007));
        Log.m129d("DefaultWallpaper", "send ChangedIntent complete");
    }

    public void updateDefaultWallpaper() {
        this.mHandler.sendEmptyMessage(1008);
    }

    public Bitmap getDefaultWallpaperBitmap(int i) {
        String defaultImageFileName = this.mSemWallpaperResourcesInfo.getDefaultImageFileName(i);
        if (defaultImageFileName != null && !defaultImageFileName.isEmpty()) {
            String substring = defaultImageFileName.substring(0, defaultImageFileName.lastIndexOf(46));
            try {
                Resources resources = this.mContext.createPackageContext("com.samsung.android.wallpaper.res", 0).getResources();
                int identifier = resources.getIdentifier(substring, "drawable", "com.samsung.android.wallpaper.res");
                Log.m129d("DefaultWallpaper", "getDefaultWallpaperBitmap: resid = " + identifier + ", name = " + substring);
                if (identifier > 0) {
                    return BitmapFactory.decodeResource(resources, identifier);
                }
                return null;
            } catch (PackageManager.NameNotFoundException e) {
                Log.m134w("DefaultWallpaper", "Fail to load default wallpaper : " + substring);
                e.printStackTrace();
                return null;
            }
        }
        Log.m134w("DefaultWallpaper", "getDefaultWallpaperBitmap: Fail to get default file name for which[" + i + "].");
        return null;
    }
}
