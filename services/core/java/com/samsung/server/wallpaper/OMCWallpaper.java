package com.samsung.server.wallpaper;

import android.app.WallpaperManager;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.provider.Settings;
import android.text.TextUtils;

import com.android.server.wallpaper.WallpaperManagerService;

import com.samsung.android.wallpaper.Rune;

/* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
public final class OMCWallpaper {
    public final WallpaperManagerService.SemCallback mCallback;
    public final Context mContext;
    public final AnonymousClass1 mHandler =
            new Handler(
                    Looper
                            .getMainLooper()) { // from class:
                                                // com.samsung.server.wallpaper.OMCWallpaper.1
                @Override // android.os.Handler
                public final void handleMessage(Message message) {
                    if (message.what != 1) {
                        return;
                    }
                    WallpaperManagerService wallpaperManagerService = WallpaperManagerService.this;
                    WallpaperManagerService.m1042$$Nest$mhandleOMCWallpaperUpdatedLocked(
                            wallpaperManagerService, 4);
                    if (!Rune.SUPPORT_SUB_DISPLAY_MODE || Rune.SUPPORT_COVER_DISPLAY_WATCHFACE) {
                        return;
                    }
                    WallpaperManagerService.m1042$$Nest$mhandleOMCWallpaperUpdatedLocked(
                            wallpaperManagerService, 16);
                }
            };
    public final SemWallpaperManagerService mService;

    /* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
    public final class OMCWallpaperUpdatedReceiver extends BroadcastReceiver {
        public OMCWallpaperUpdatedReceiver() {}

        @Override // android.content.BroadcastReceiver
        public final void onReceive(Context context, Intent intent) {
            String action = intent.getAction();
            OMCWallpaper oMCWallpaper = OMCWallpaper.this;
            oMCWallpaper.getClass();
            Log.d("OMCWallpaper", "OMCWallpaperUpdatedReceiver : onReceive:" + action);
            if ("com.samsung.intent.action.RSCUPDATE_START".equalsIgnoreCase(action)) {
                AnonymousClass1 anonymousClass1 = oMCWallpaper.mHandler;
                if (anonymousClass1.hasMessages(1)) {
                    anonymousClass1.removeMessages(1);
                }
                anonymousClass1.sendEmptyMessage(1);
            }
        }
    }

    /* JADX WARN: Type inference failed for: r0v0, types: [com.samsung.server.wallpaper.OMCWallpaper$1] */
    public OMCWallpaper(
            Context context,
            WallpaperManagerService.SemCallback semCallback,
            SemWallpaperManagerService semWallpaperManagerService) {
        Log.d("OMCWallpaper", "OMCWallpaper");
        this.mContext = context;
        this.mCallback = semCallback;
        this.mService = semWallpaperManagerService;
    }

    public final void saveTSSActivationSettings(String str) {
        if (WallpaperManager.isDefaultOperatorWallpaper(
                        this.mContext, 2, this.mService.mCMFWallpaper.getDeviceColor())
                && !TextUtils.isEmpty(str)
                && str.equals("true")) {
            Settings.System.putInt(this.mContext.getContentResolver(), "tss_activated", 1);
        }
    }
}
