package com.android.wm.shell.startingsurface;

import android.R;
import android.content.Context;
import android.content.pm.ActivityInfo;
import android.hardware.display.DisplayManager;
import com.android.systemui.statusbar.phone.CentralSurfacesImpl$$ExternalSyntheticLambda23;
import com.android.wm.shell.common.ShellExecutor;
import com.android.wm.shell.startingsurface.StartingSurfaceDrawer;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes3.dex */
public abstract class AbsSplashWindowCreator {
    public final Context mContext;
    public final DisplayManager mDisplayManager;
    public final ShellExecutor mSplashScreenExecutor;
    public final SplashscreenContentDrawer mSplashscreenContentDrawer;
    public final StartingSurfaceDrawer.StartingWindowRecordManager mStartingWindowRecordManager;
    public CentralSurfacesImpl$$ExternalSyntheticLambda23 mSysuiProxy;

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
