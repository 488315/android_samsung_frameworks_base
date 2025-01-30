package com.android.systemui.wallpaper.view;

import android.app.WallpaperManager;
import android.graphics.drawable.BitmapDrawable;
import android.util.Log;
import com.android.systemui.wallpaper.WallpaperUtils;
import com.android.systemui.wallpaper.log.WallpaperLogger;
import com.android.systemui.wallpaper.log.WallpaperLoggerImpl;
import com.android.systemui.wallpaper.view.KeyguardLiveWallpaper;
import com.android.systemui.wallpaper.view.KeyguardLiveWallpaper.WallpaperConnection;

/* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
/* loaded from: classes2.dex */
public final /* synthetic */ class KeyguardLiveWallpaper$$ExternalSyntheticLambda0 implements Runnable {
    public final /* synthetic */ int $r8$classId;
    public final /* synthetic */ KeyguardLiveWallpaper f$0;

    public /* synthetic */ KeyguardLiveWallpaper$$ExternalSyntheticLambda0(KeyguardLiveWallpaper keyguardLiveWallpaper, int i) {
        this.$r8$classId = i;
        this.f$0 = keyguardLiveWallpaper;
    }

    @Override // java.lang.Runnable
    public final void run() {
        boolean z = true;
        switch (this.$r8$classId) {
            case 0:
                KeyguardLiveWallpaper keyguardLiveWallpaper = this.f$0;
                keyguardLiveWallpaper.getClass();
                Log.i("KeyguardLiveWallpaper", "attachService");
                KeyguardLiveWallpaper.WallpaperConnection wallpaperConnection = keyguardLiveWallpaper.new WallpaperConnection(keyguardLiveWallpaper.mWallpaperIntent, new KeyguardLiveWallpaper$$ExternalSyntheticLambda2(keyguardLiveWallpaper));
                keyguardLiveWallpaper.mWallpaperConnection = wallpaperConnection;
                synchronized (wallpaperConnection) {
                    try {
                        try {
                        } catch (Exception e) {
                            WallpaperLogger wallpaperLogger = KeyguardLiveWallpaper.this.mLogger;
                            ((WallpaperLoggerImpl) wallpaperLogger).log(wallpaperConnection.TAG, "connect: failed to bind wallpaper. e= " + e);
                        }
                        if (KeyguardLiveWallpaper.this.mContext.bindService(wallpaperConnection.mIntent, wallpaperConnection, 1)) {
                            ((WallpaperLoggerImpl) KeyguardLiveWallpaper.this.mLogger).log(wallpaperConnection.TAG, "connect: bindService invoked successfully");
                            wallpaperConnection.setConnectionState(KeyguardLiveWallpaper.WallpaperConnection.ConnectionState.CONNECTING);
                        } else {
                            ((WallpaperLoggerImpl) KeyguardLiveWallpaper.this.mLogger).log(wallpaperConnection.TAG, "connect: failed");
                            z = false;
                        }
                    } finally {
                    }
                }
                if (z) {
                    return;
                }
                keyguardLiveWallpaper.mWallpaperConnection = null;
                return;
            case 1:
                KeyguardLiveWallpaper keyguardLiveWallpaper2 = this.f$0;
                KeyguardLiveWallpaper.WallpaperConnection wallpaperConnection2 = keyguardLiveWallpaper2.mWallpaperConnection;
                if (wallpaperConnection2 != null) {
                    synchronized (wallpaperConnection2) {
                        WallpaperLogger wallpaperLogger2 = KeyguardLiveWallpaper.this.mLogger;
                        ((WallpaperLoggerImpl) wallpaperLogger2).log(wallpaperConnection2.TAG, "requestRelease: connState=" + wallpaperConnection2.mConnectionState);
                        wallpaperConnection2.mIsReleaseRequested = true;
                        if (wallpaperConnection2.mConnectionState == KeyguardLiveWallpaper.WallpaperConnection.ConnectionState.CONNECTED) {
                            wallpaperConnection2.release();
                        }
                    }
                    keyguardLiveWallpaper2.mWallpaperConnection = null;
                    return;
                }
                return;
            default:
                KeyguardLiveWallpaper keyguardLiveWallpaper3 = this.f$0;
                keyguardLiveWallpaper3.mRunnableUpdateThumbnail = null;
                try {
                    keyguardLiveWallpaper3.mThumbnail = (BitmapDrawable) WallpaperManager.getInstance(keyguardLiveWallpaper3.mContext).semGetDrawable(WallpaperUtils.sCurrentWhich);
                    ((WallpaperLoggerImpl) keyguardLiveWallpaper3.mLogger).log("KeyguardLiveWallpaper", "updateThumbnail: " + keyguardLiveWallpaper3.mThumbnail);
                    return;
                } catch (ClassCastException e2) {
                    e2.printStackTrace();
                    return;
                }
        }
    }
}
