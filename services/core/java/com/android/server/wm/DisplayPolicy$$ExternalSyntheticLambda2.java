package com.android.server.wm;

import android.os.RemoteException;
import android.view.ViewRootImpl;
import com.android.internal.statusbar.IStatusBar;
import com.android.server.LocalServices;
import com.android.server.statusbar.StatusBarManagerInternal;
import com.android.server.statusbar.StatusBarManagerService;
import com.android.server.wallpaper.WallpaperManagerService;

/* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
/* loaded from: classes2.dex */
public final /* synthetic */ class DisplayPolicy$$ExternalSyntheticLambda2 implements Runnable {
    public final /* synthetic */ int $r8$classId;
    public final /* synthetic */ DisplayPolicy f$0;

    public /* synthetic */ DisplayPolicy$$ExternalSyntheticLambda2(DisplayPolicy displayPolicy, int i) {
        this.$r8$classId = i;
        this.f$0 = displayPolicy;
    }

    @Override // java.lang.Runnable
    public final void run() {
        IStatusBar iStatusBar;
        int i = this.$r8$classId;
        DisplayPolicy displayPolicy = this.f$0;
        switch (i) {
            case 0:
                synchronized (displayPolicy.mLock) {
                    try {
                        displayPolicy.onConfigurationChanged();
                        if (!ViewRootImpl.CLIENT_TRANSIENT) {
                            displayPolicy.mSystemGestures.onConfigurationChanged();
                        }
                        displayPolicy.mDisplayContent.updateSystemGestureExclusion();
                    } catch (Throwable th) {
                        throw th;
                    }
                }
                return;
            case 1:
                int i2 = displayPolicy.mDisplayContent.mDisplayId;
                StatusBarManagerInternal statusBarManagerInternal = displayPolicy.getStatusBarManagerInternal();
                if (statusBarManagerInternal != null && (iStatusBar = StatusBarManagerService.this.mBar) != null) {
                    try {
                        iStatusBar.onDisplayReady(i2);
                    } catch (RemoteException unused) {
                    }
                }
                WallpaperManagerService.LocalService localService = (WallpaperManagerService.LocalService) LocalServices.getService(WallpaperManagerService.LocalService.class);
                if (localService != null) {
                    localService.onDisplayReady(i2);
                    return;
                }
                return;
            case 2:
                displayPolicy.onConfigurationChanged();
                if (ViewRootImpl.CLIENT_TRANSIENT) {
                    return;
                }
                displayPolicy.mSystemGestures.onConfigurationChanged();
                return;
            default:
                displayPolicy.updateForceShowNavBarSettings();
                return;
        }
    }
}
