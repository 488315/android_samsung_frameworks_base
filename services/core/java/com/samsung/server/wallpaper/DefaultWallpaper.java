package com.samsung.server.wallpaper;

import android.app.SemWallpaperResourcesInfo;
import android.content.Context;
import android.content.Intent;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.os.UserHandle;
import android.provider.Settings;

import com.android.server.DeviceIdleController$$ExternalSyntheticOutline0;
import com.android.server.wallpaper.WallpaperData;
import com.android.server.wallpaper.WallpaperManagerService;

import com.samsung.android.wallpaper.utils.WhichChecker;

public final class DefaultWallpaper {
    public final WallpaperManagerService.SemCallback mCallback;
    public final Context mContext;
    public final AnonymousClass1 mHandler = new Handler(Looper.getMainLooper()) { // from class:
                // com.samsung.server.wallpaper.DefaultWallpaper.1
                @Override // android.os.Handler
                public final void handleMessage(Message message) {
                    switch (message.what) {
                        case 1004:
                            int i = message.arg2;
                            String str = "android.wallpaper.settings_systemui_transparency";
                            if (WhichChecker.isDex(i)) {
                                str = "dex_system_wallpaper_transparency";
                            } else if (WhichChecker.isSubDisplay(i)) {
                                str = "sub_display_system_wallpaper_transparency";
                            }
                            DefaultWallpaper.m1236$$Nest$msetSettingsSystemUiTransparency(
                                    DefaultWallpaper.this, message.arg1, str);
                            return;
                        case 1005:
                        default:
                            return;
                        case 1006:
                            int i2 = message.arg2;
                            String str2 = "lockscreen_wallpaper_transparent";
                            if (WhichChecker.isDex(i2)) {
                                str2 = "dex_lockscreen_wallpaper_transparency";
                            } else if (WhichChecker.isSubDisplay(i2)) {
                                str2 = "sub_display_lockscreen_wallpaper_transparency";
                            }
                            DefaultWallpaper.m1236$$Nest$msetSettingsSystemUiTransparency(
                                    DefaultWallpaper.this, message.arg1, str2);
                            return;
                        case 1007:
                            Intent intent = new Intent();
                            intent.setAction("com.sec.android.intent.action.WALLPAPER_CHANGED");
                            intent.setFlags(16777216);
                            DefaultWallpaper.this.mContext.sendBroadcastAsUser(
                                    intent,
                                    new UserHandle(DefaultWallpaper.this.mService.mCurrentUserId));
                            Log.d("DefaultWallpaper", "send wallpaperChangedIntent");
                            return;
                        case 1008:
                            Log.d("DefaultWallpaper", "msg MSG_UPDATE_DEFAULT_WALLPAPER");
                            WallpaperManagerService.SemCallback semCallback =
                                    DefaultWallpaper.this.mCallback;
                            if (WallpaperManagerService.this.mSemWallpaperResourcesInfo
                                    .isSupportCMF()) {
                                String deviceColor =
                                        WallpaperManagerService.this.mSemService.mCMFWallpaper
                                                .getDeviceColor();
                                DeviceIdleController$$ExternalSyntheticOutline0.m(
                                        "rebindDefaultWallpaperIfNeeded: Change system wallpaper by"
                                            + " color(",
                                        deviceColor,
                                        ")",
                                        "WallpaperManagerService");
                                WallpaperData wallpaperData =
                                        WallpaperManagerService.this.mWallpaperMap.get(
                                                UserHandle.getCallingUserId(), 0);
                                if (wallpaperData != null) {
                                    synchronized (WallpaperManagerService.this.mLock) {
                                        WallpaperManagerService.this.bindWallpaperComponentLocked(
                                                null, true, false, wallpaperData, null, null);
                                    }
                                    WallpaperManagerService.this.mSemService.mLegibilityColor
                                            .extractColor(
                                                    wallpaperData.mSemWallpaperData.mWhich, false);
                                }
                                DeviceIdleController$$ExternalSyntheticOutline0.m(
                                        "rebindDefaultWallpaperIfNeeded: Change lock wallpaper by"
                                            + " color(",
                                        deviceColor,
                                        ")",
                                        "WallpaperManagerService");
                                WallpaperManagerService.this.mSemService.semClearWallpaperLocked(
                                        6, UserHandle.getCallingUserId(), "android_CMF");
                                return;
                            }
                            return;
                    }
                }
            };
    public final SemWallpaperResourcesInfo mSemWallpaperResourcesInfo;
    public final SemWallpaperManagerService mService;

    /* renamed from: -$$Nest$msetSettingsSystemUiTransparency, reason: not valid java name */
    public static void m1236$$Nest$msetSettingsSystemUiTransparency(
            DefaultWallpaper defaultWallpaper, int i, String str) {
        defaultWallpaper.getClass();
        Log.d("DefaultWallpaper", "setSettingsSystemUiTransparency : " + i + " name=" + str);
        Settings.System.putIntForUser(defaultWallpaper.mContext.getContentResolver(), str, i, -2);
    }

    public DefaultWallpaper(
            Context context,
            WallpaperManagerService.SemCallback semCallback,
            SemWallpaperManagerService semWallpaperManagerService,
            SemWallpaperResourcesInfo semWallpaperResourcesInfo) {
        Log.d("DefaultWallpaper", "DefaultWallpaper");
        this.mContext = context;
        this.mCallback = semCallback;
        this.mService = semWallpaperManagerService;
        this.mSemWallpaperResourcesInfo = semWallpaperResourcesInfo;
    }

    public final String getDefaultWallpaperUri(int i) {
        String str =
                "android.resource://com.samsung.android.wallpaper.res/drawable/"
                        + this.mSemWallpaperResourcesInfo.getDefaultImageFileName(i);
        Log.d("DefaultWallpaper", "getDefaultWallpaperUri: defaultWallpaper = " + str);
        return str;
    }

    public final void updateTransparencySettingIfNeed(int i, String str, boolean z) {
        if (WhichChecker.isDex(i)) {
            if ("com.sec.android.app.desktoplauncher".equals(str)) {
                return;
            }
        } else if ("com.samsung.android.themecenter".equals(str)
                || "service.odtcfactory.sec.com.odtcfactoryservice".equals(str)
                || "com.samsung.android.app.dressroom".equals(str)) {
            return;
        }
        boolean isSystem = WhichChecker.isSystem(i);
        AnonymousClass1 anonymousClass1 = this.mHandler;
        if (isSystem) {
            Message obtainMessage = anonymousClass1.obtainMessage(1004);
            obtainMessage.arg1 = z ? 1 : 0;
            obtainMessage.arg2 = i;
            anonymousClass1.sendMessage(obtainMessage);
        }
        if (WhichChecker.isLock(i)) {
            Message obtainMessage2 = anonymousClass1.obtainMessage(1006);
            obtainMessage2.arg1 = z ? 1 : 0;
            obtainMessage2.arg2 = i;
            anonymousClass1.sendMessage(obtainMessage2);
        }
    }
}
