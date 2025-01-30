package com.android.p038wm.shell.startingsurface;

import android.R;
import android.content.Context;
import android.content.pm.ActivityInfo;
import android.hardware.display.DisplayManager;
import com.android.p038wm.shell.common.ShellExecutor;
import com.android.p038wm.shell.startingsurface.StartingSurfaceDrawer;
import com.android.systemui.statusbar.phone.CentralSurfacesImpl$$ExternalSyntheticLambda0;

/* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
/* loaded from: classes2.dex */
public abstract class AbsSplashWindowCreator {
    public final Context mContext;
    public final DisplayManager mDisplayManager;
    public final ShellExecutor mSplashScreenExecutor;
    public final SplashscreenContentDrawer mSplashscreenContentDrawer;
    public final StartingSurfaceDrawer.StartingWindowRecordManager mStartingWindowRecordManager;
    public CentralSurfacesImpl$$ExternalSyntheticLambda0 mSysuiProxy;

    public AbsSplashWindowCreator(SplashscreenContentDrawer splashscreenContentDrawer, Context context, ShellExecutor shellExecutor, DisplayManager displayManager, StartingSurfaceDrawer.StartingWindowRecordManager startingWindowRecordManager) {
        this.mSplashscreenContentDrawer = splashscreenContentDrawer;
        this.mContext = context;
        this.mSplashScreenExecutor = shellExecutor;
        this.mDisplayManager = displayManager;
        this.mStartingWindowRecordManager = startingWindowRecordManager;
    }

    public static int getSplashScreenTheme(int i, ActivityInfo activityInfo) {
        return i != 0 ? i : activityInfo.getThemeResource() != 0 ? activityInfo.getThemeResource() : R.style.Theme.DeviceDefault.DayNight;
    }
}
