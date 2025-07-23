package com.android.wm.shell.startingsurface;

import android.content.res.Configuration;
import com.android.internal.protolog.ProtoLogImpl_1771455215;
import com.android.wm.shell.protolog.ShellProtoLogGroup;
import com.android.wm.shell.startingsurface.SplashscreenContentDrawer;
import com.android.wm.shell.startingsurface.StartingWindowController;
import com.android.wm.shell.sysui.ConfigurationChangeListener;
import com.android.wm.shell.sysui.ShellController;
import java.util.function.Supplier;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes3.dex */
public final /* synthetic */ class StartingWindowController$$ExternalSyntheticLambda5 implements Runnable {
    public final /* synthetic */ int $r8$classId;
    public final /* synthetic */ StartingWindowController f$0;

    public /* synthetic */ StartingWindowController$$ExternalSyntheticLambda5(StartingWindowController startingWindowController, int i) {
        this.$r8$classId = i;
        this.f$0 = startingWindowController;
    }

    @Override // java.lang.Runnable
    public final void run() {
        int i = this.$r8$classId;
        final StartingWindowController startingWindowController = this.f$0;
        switch (i) {
            case 0:
                StartingSurfaceDrawer startingSurfaceDrawer = startingWindowController.mStartingSurfaceDrawer;
                startingSurfaceDrawer.getClass();
                if (ProtoLogImpl_1771455215.Cache.WM_SHELL_STARTING_WINDOW_enabled[1]) {
                    ProtoLogImpl_1771455215.v(ShellProtoLogGroup.WM_SHELL_STARTING_WINDOW, 5962205083984802852L, 0, null);
                }
                startingSurfaceDrawer.mWindowRecords.clearAllWindows();
                startingSurfaceDrawer.mWindowlessRecords.clearAllWindows();
                synchronized (startingWindowController.mTaskBackgroundColors) {
                    startingWindowController.mTaskBackgroundColors.clear();
                }
                return;
            default:
                startingWindowController.mShellTaskOrganizer.mStartingWindow = startingWindowController;
                Supplier supplier = new Supplier() { // from class: com.android.wm.shell.startingsurface.StartingWindowController$$ExternalSyntheticLambda9
                    @Override // java.util.function.Supplier
                    public final Object get() {
                        StartingWindowController startingWindowController2 = StartingWindowController.this;
                        startingWindowController2.getClass();
                        return new StartingWindowController.IStartingWindowImpl(startingWindowController2);
                    }
                };
                ShellController shellController = startingWindowController.mShellController;
                shellController.addExternalInterface("com.android.wm.shell.startingsurface.IStartingWindow", supplier, startingWindowController);
                shellController.addConfigurationChangeListener(new ConfigurationChangeListener() { // from class: com.android.wm.shell.startingsurface.StartingWindowController.1
                    public AnonymousClass1() {
                    }

                    @Override // com.android.wm.shell.sysui.ConfigurationChangeListener
                    public final void onConfigurationChanged(Configuration configuration) {
                        SplashscreenContentDrawer splashscreenContentDrawer = StartingWindowController.this.mStartingSurfaceDrawer.mSplashscreenContentDrawer;
                        splashscreenContentDrawer.getClass();
                        boolean isNightModeActive = configuration.isNightModeActive();
                        if (SplashscreenContentDrawer.mIsNightMode != isNightModeActive) {
                            int i2 = SplashscreenContentDrawer.SettingObserver.$r8$clinit;
                            splashscreenContentDrawer.mSettingObserver.updateSettings(true);
                        }
                        SplashscreenContentDrawer.mIsNightMode = isNightModeActive;
                    }
                });
                return;
        }
    }
}
