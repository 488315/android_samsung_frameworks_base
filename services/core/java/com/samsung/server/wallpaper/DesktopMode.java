package com.samsung.server.wallpaper;

import android.content.Context;
import android.os.Environment;
import android.os.Handler;
import android.os.Looper;

import com.android.server.LocalServices;
import com.android.server.wallpaper.WallpaperData;
import com.android.server.wallpaper.WallpaperManagerService;

import com.samsung.android.desktopmode.DesktopModeManagerInternal;
import com.samsung.android.desktopmode.SemDesktopModeManager;
import com.samsung.android.desktopmode.SemDesktopModeState;
import com.samsung.android.wallpaper.Rune;

import java.io.File;

public final class DesktopMode {
    public final WallpaperManagerService.SemCallback mCallback;
    public final Context mContext;
    public final SemWallpaperManagerService mService;
    public final Handler mHandler = new Handler(Looper.getMainLooper());
    public final Object mDesktopModeLock = new Object();
    public boolean mIsDesktopMode = false;
    public boolean mWallpaperBindingFallbackExecuted = false;
    public int mDesktopMode = 0;

    public DesktopMode(
            Context context,
            WallpaperManagerService.SemCallback semCallback,
            SemWallpaperManagerService semWallpaperManagerService) {
        Log.d("DesktopMode", "DesktopMode");
        this.mContext = context;
        this.mCallback = semCallback;
        this.mService = semWallpaperManagerService;
        ((SemDesktopModeManager) context.getSystemService("desktopmode"))
                .registerListener(
                        new SemDesktopModeManager.DesktopModeListener() { // from class:
                            // com.samsung.server.wallpaper.DesktopMode.1
                            public final void onDesktopModeStateChanged(
                                    SemDesktopModeState semDesktopModeState) {
                                WallpaperData wallpaperData;
                                Log.i(
                                        "DesktopMode",
                                        "Wallpaper onDesktopModeChanged : " + semDesktopModeState);
                                boolean z = Rune.DESKTOP_STANDALONE_MODE_WALLPAPER;
                                if (!z && semDesktopModeState.getDisplayType() == 101) {
                                    boolean z2 = semDesktopModeState.getEnabled() == 3;
                                    boolean z3 = semDesktopModeState.getEnabled() == 1;
                                    synchronized (DesktopMode.this.mDesktopModeLock) {
                                        try {
                                            if (z2) {
                                                DesktopMode.this.mDesktopMode = 101;
                                            } else if (z3) {
                                                DesktopMode.this.mDesktopMode = 0;
                                            }
                                        } finally {
                                        }
                                    }
                                }
                                if (semDesktopModeState.getEnabled() == 4
                                        && semDesktopModeState.getState() == 50) {
                                    DesktopMode.this.mIsDesktopMode = true;
                                } else if (semDesktopModeState.getEnabled() != 2
                                        || semDesktopModeState.getState() != 50) {
                                    return;
                                } else {
                                    DesktopMode.this.mIsDesktopMode = false;
                                }
                                if (!z && semDesktopModeState.getDisplayType() == 101) {
                                    Log.i(
                                            "DesktopMode",
                                            "Do not switch dex wallpaper if standalone mode : "
                                                    + semDesktopModeState);
                                    return;
                                }
                                DesktopMode desktopMode = DesktopMode.this;
                                WallpaperManagerService.SemCallback semCallback2 =
                                        desktopMode.mCallback;
                                boolean z4 = desktopMode.mIsDesktopMode;
                                boolean isDesktopDualMode =
                                        WallpaperManagerService.this.mSemService.mDesktopMode
                                                .isDesktopDualMode();
                                synchronized (WallpaperManagerService.this.mLock) {
                                    if (!z4) {
                                        try {
                                            Log.addLogString(
                                                    "WallpaperManagerService",
                                                    "DesktopMode disabled");
                                            WallpaperManagerService wallpaperManagerService =
                                                    WallpaperManagerService.this;
                                            WallpaperManagerService.this.detachWallpaperLocked(
                                                    wallpaperManagerService.getWallpaperSafeLocked(
                                                            wallpaperManagerService.mCurrentUserId,
                                                            9));
                                        } finally {
                                        }
                                    }
                                }
                                DesktopMode desktopMode2 =
                                        WallpaperManagerService.this.mSemService.mDesktopMode;
                                desktopMode2.getClass();
                                Log.d(
                                        "DesktopMode",
                                        "onRefreshWallpaperByUiMode() isDesktopMode = " + z4);
                                synchronized (desktopMode2.mDesktopModeLock) {
                                    try {
                                        desktopMode2.mIsDesktopMode = z4;
                                        if (z4) {
                                            SemDesktopModeState desktopModeState =
                                                    ((DesktopModeManagerInternal)
                                                                    LocalServices.getService(
                                                                            DesktopModeManagerInternal
                                                                                    .class))
                                                            .getDesktopModeState();
                                            if (desktopModeState != null) {
                                                desktopMode2.mDesktopMode =
                                                        desktopModeState.getDisplayType();
                                            }
                                        } else if (desktopMode2.isDesktopDualMode()) {
                                            desktopMode2.mDesktopMode = 0;
                                            Log.d(
                                                    "DesktopMode",
                                                    "No need to refresh phone wallpaper when Dual"
                                                        + " dex is disabled");
                                        } else {
                                            desktopMode2.mDesktopMode = 0;
                                        }
                                        Log.d(
                                                "DesktopMode",
                                                "mDesktopMode = " + desktopMode2.mDesktopMode);
                                        if (desktopMode2.isDesktopMode()
                                                && desktopMode2.isDesktopDualMode()) {
                                            Log.d(
                                                    "DesktopMode",
                                                    "No need to refresh phone wallpaper when Dual"
                                                        + " dex is enabled");
                                        } else {
                                            WallpaperManagerService.SemCallback semCallback3 =
                                                    desktopMode2.mCallback;
                                            int i = desktopMode2.mService.mCurrentUserId;
                                            boolean z5 = desktopMode2.mIsDesktopMode;
                                            semCallback3.getClass();
                                            int i2 = z5 ? 9 : 5;
                                            synchronized (WallpaperManagerService.this.mLock) {
                                                try {
                                                    wallpaperData =
                                                            WallpaperManagerService.this
                                                                    .mWallpaperMap.get(i, i2);
                                                    if (wallpaperData == null) {
                                                        Log.d(
                                                                "WallpaperManagerService",
                                                                "no current wallpaper -- first"
                                                                    + " switching DeX?");
                                                        wallpaperData =
                                                                WallpaperManagerService.this
                                                                        .getWallpaperSafeLocked(
                                                                                i, i2);
                                                    }
                                                    WallpaperManagerService.this.mSemService
                                                            .mDesktopMode.getClass();
                                                    boolean exists =
                                                            new File(
                                                                            Environment
                                                                                    .getUserSystemDirectory(
                                                                                            i),
                                                                            "wallpaper_desktop_orig")
                                                                    .exists();
                                                    Log.d(
                                                            "DesktopMode",
                                                            "isDesktopWallpaperFileExist "
                                                                    + exists);
                                                    if (exists || !z5) {
                                                        WallpaperManagerService.this
                                                                .switchWallpaper(
                                                                        wallpaperData, null);
                                                        semCallback3.notifySemWallpaperColors(i2);
                                                    }
                                                } finally {
                                                }
                                            }
                                            WallpaperManagerService.this.notifyLockWallpaperChanged(
                                                    wallpaperData.mSemWallpaperData.mWpType,
                                                    i2,
                                                    null);
                                        }
                                    } finally {
                                    }
                                }
                                if ((!isDesktopDualMode || z4)
                                        && !(WallpaperManagerService.this.mSemService.mDesktopMode
                                                        .isDesktopDualMode()
                                                && z4)) {
                                    WallpaperManagerService.this.mSemService
                                            .handleWallpaperBindingTimeout(true);
                                } else {
                                    Log.i(
                                            "WallpaperManagerService",
                                            "Wallpaper ignore wallpaper refresh in default display"
                                                + " when desktop dual mode is enabled/disabled");
                                }
                            }
                        });
    }

    public final boolean isDesktopDualMode() {
        boolean z;
        synchronized (this.mDesktopModeLock) {
            z = this.mDesktopMode == 102;
        }
        return z;
    }

    public final boolean isDesktopMode() {
        boolean z;
        synchronized (this.mDesktopModeLock) {
            z = this.mIsDesktopMode;
        }
        return z;
    }

    public final boolean isDesktopModeEnabled(int i) {
        boolean z;
        synchronized (this.mDesktopModeLock) {
            try {
                z = this.mIsDesktopMode && (this.mDesktopMode == 101 || (i & 8) == 8);
            } finally {
            }
        }
        return z;
    }

    public final boolean isDesktopSingleMode() {
        boolean z;
        synchronized (this.mDesktopModeLock) {
            z = this.mDesktopMode == 101;
        }
        return z;
    }
}
