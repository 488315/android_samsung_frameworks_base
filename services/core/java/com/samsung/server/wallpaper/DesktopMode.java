package com.samsung.server.wallpaper;

import android.content.Context;
import android.content.Intent;
import android.os.Environment;
import android.os.Handler;
import android.os.Looper;
import android.os.UserHandle;
import com.android.server.LocalServices;
import com.android.server.wallpaper.WallpaperManagerService;
import com.samsung.android.desktopmode.DesktopModeManagerInternal;
import com.samsung.android.desktopmode.SemDesktopModeManager;
import com.samsung.android.desktopmode.SemDesktopModeState;
import com.samsung.android.wallpaper.Rune;
import java.io.File;

/* loaded from: classes2.dex */
public class DesktopMode {
    public final WallpaperManagerService.SemCallback mCallback;
    public final Context mContext;
    public SemDesktopModeManager mDesktopModeManager;
    public final SemWallpaperManagerService mService;
    public final Handler mHandler = new Handler(Looper.getMainLooper());
    public final Object mDesktopModeLock = new Object();
    public boolean mIsDesktopMode = false;
    public boolean mWallpaperBindingFallbackExecuted = false;
    public int mDesktopMode = 0;
    public int mWallpaperBindingFallbackCount = 0;

    public DesktopMode(Context context, WallpaperManagerService.SemCallback semCallback, SemWallpaperManagerService semWallpaperManagerService) {
        Log.m129d("DesktopMode", "DesktopMode");
        this.mContext = context;
        this.mCallback = semCallback;
        this.mService = semWallpaperManagerService;
        initDesktopMode();
    }

    public void increaseWallpaperBindingFallbackCount() {
        this.mWallpaperBindingFallbackCount++;
    }

    public void setWallpaperBindingFallbackExecuted(boolean z) {
        this.mWallpaperBindingFallbackExecuted = z;
    }

    public boolean getWallpaperBindingFallbackExecuted() {
        return this.mWallpaperBindingFallbackExecuted;
    }

    public final void initDesktopMode() {
        SemDesktopModeManager semDesktopModeManager = (SemDesktopModeManager) this.mContext.getSystemService("desktopmode");
        this.mDesktopModeManager = semDesktopModeManager;
        semDesktopModeManager.registerListener(new SemDesktopModeManager.DesktopModeListener() { // from class: com.samsung.server.wallpaper.DesktopMode.1
            public void onDesktopModeStateChanged(SemDesktopModeState semDesktopModeState) {
                Log.m132i("DesktopMode", "Wallpaper onDesktopModeChanged : " + semDesktopModeState);
                if (!Rune.DESKTOP_STANDALONE_MODE_WALLPAPER && semDesktopModeState.getDisplayType() == 101) {
                    boolean z = semDesktopModeState.getEnabled() == 3;
                    boolean z2 = semDesktopModeState.getEnabled() == 1;
                    synchronized (DesktopMode.this.mDesktopModeLock) {
                        try {
                            if (z) {
                                DesktopMode.this.mDesktopMode = 101;
                            } else if (z2) {
                                DesktopMode.this.mDesktopMode = 0;
                            }
                        } finally {
                        }
                    }
                }
                if (semDesktopModeState.getEnabled() == 4 && semDesktopModeState.getState() == 50) {
                    DesktopMode.this.mIsDesktopMode = true;
                } else if (semDesktopModeState.getEnabled() != 2 || semDesktopModeState.getState() != 50) {
                    return;
                } else {
                    DesktopMode.this.mIsDesktopMode = false;
                }
                if (!Rune.DESKTOP_STANDALONE_MODE_WALLPAPER && semDesktopModeState.getDisplayType() == 101) {
                    Log.m132i("DesktopMode", "Do not switch dex wallpaper if standalone mode : " + semDesktopModeState);
                    return;
                }
                DesktopMode.this.mCallback.updateDesktopModeState(DesktopMode.this.mIsDesktopMode);
            }
        });
    }

    public boolean isDesktopMode() {
        boolean z;
        synchronized (this.mDesktopModeLock) {
            z = this.mIsDesktopMode;
        }
        return z;
    }

    public int getDesktopMode() {
        int i;
        synchronized (this.mDesktopModeLock) {
            i = this.mDesktopMode;
        }
        return i;
    }

    public boolean isDesktopModeEnabled(int i) {
        boolean z;
        synchronized (this.mDesktopModeLock) {
            z = this.mIsDesktopMode && (this.mDesktopMode == 101 || (i & 8) == 8);
        }
        return z;
    }

    public boolean isDesktopSingleMode() {
        boolean z;
        synchronized (this.mDesktopModeLock) {
            z = this.mDesktopMode == 101;
        }
        return z;
    }

    public boolean isDesktopDualMode() {
        boolean z;
        synchronized (this.mDesktopModeLock) {
            z = this.mDesktopMode == 102;
        }
        return z;
    }

    public void onRefreshWallpaperByUiMode(boolean z) {
        Log.m129d("DesktopMode", "onRefreshWallpaperByUiMode() isDesktopMode = " + z);
        synchronized (this.mDesktopModeLock) {
            this.mIsDesktopMode = z;
            if (z) {
                SemDesktopModeState desktopModeState = ((DesktopModeManagerInternal) LocalServices.getService(DesktopModeManagerInternal.class)).getDesktopModeState();
                if (desktopModeState != null) {
                    this.mDesktopMode = desktopModeState.getDisplayType();
                }
            } else {
                if (isDesktopDualMode()) {
                    this.mDesktopMode = 0;
                    Log.m129d("DesktopMode", "No need to refresh phone wallpaper when Dual dex is disabled");
                    return;
                }
                this.mDesktopMode = 0;
            }
            Log.m129d("DesktopMode", "mDesktopMode = " + this.mDesktopMode);
            if (isDesktopMode() && isDesktopDualMode()) {
                Log.m129d("DesktopMode", "No need to refresh phone wallpaper when Dual dex is enabled");
            } else {
                this.mCallback.switchDexWallpaper(this.mService.getCurrentUserId(), this.mIsDesktopMode);
            }
        }
    }

    public void sendWallpaperEngineShownIntent(final int i) {
        this.mHandler.postDelayed(new Runnable() { // from class: com.samsung.server.wallpaper.DesktopMode.2
            @Override // java.lang.Runnable
            public void run() {
                Intent intent = new Intent("com.samsung.android.intent.action.WALLPAPER_ENGINE_SHOWN");
                intent.putExtra("which", i);
                DesktopMode.this.mContext.sendBroadcastAsUser(intent, new UserHandle(DesktopMode.this.mService.getCurrentUserId()));
            }
        }, 1500L);
    }

    public boolean isDesktopWallpaperFileExist(int i) {
        boolean exists = new File(getWallpaperDir(i), "wallpaper_desktop_orig").exists();
        Log.m129d("DesktopMode", "isDesktopWallpaperFileExist " + exists);
        return exists;
    }

    public final File getWallpaperDir(int i) {
        return Environment.getUserSystemDirectory(i);
    }
}
