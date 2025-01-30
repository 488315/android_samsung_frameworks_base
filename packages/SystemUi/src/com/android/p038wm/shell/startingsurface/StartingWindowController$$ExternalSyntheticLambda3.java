package com.android.p038wm.shell.startingsurface;

import android.app.ActivityTaskManager;
import android.os.Bundle;
import android.os.RemoteCallback;
import android.window.SplashScreenView;
import android.window.StartingWindowRemovalInfo;
import com.android.p038wm.shell.common.HandlerExecutor;
import com.android.p038wm.shell.protolog.ShellProtoLogCache;
import com.android.p038wm.shell.protolog.ShellProtoLogGroup;
import com.android.p038wm.shell.protolog.ShellProtoLogImpl;
import com.android.p038wm.shell.startingsurface.SplashscreenWindowCreator;
import com.android.p038wm.shell.startingsurface.StartingSurfaceDrawer;

/* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
/* loaded from: classes2.dex */
public final /* synthetic */ class StartingWindowController$$ExternalSyntheticLambda3 implements Runnable {
    public final /* synthetic */ int $r8$classId;
    public final /* synthetic */ StartingWindowController f$0;
    public final /* synthetic */ int f$1;

    public /* synthetic */ StartingWindowController$$ExternalSyntheticLambda3(StartingWindowController startingWindowController, int i, int i2) {
        this.$r8$classId = i2;
        this.f$0 = startingWindowController;
        this.f$1 = i;
    }

    @Override // java.lang.Runnable
    public final void run() {
        SplashScreenView.SplashScreenViewParcelable splashScreenViewParcelable;
        switch (this.$r8$classId) {
            case 0:
                this.f$0.mStartingSurfaceDrawer.mSplashscreenWindowCreator.onAppSplashScreenViewRemoved(this.f$1, true);
                break;
            case 1:
                StartingWindowController startingWindowController = this.f$0;
                int i = this.f$1;
                StartingSurfaceDrawer startingSurfaceDrawer = startingWindowController.mStartingSurfaceDrawer;
                StartingSurfaceDrawer.StartingWindowRecordManager startingWindowRecordManager = startingSurfaceDrawer.mWindowRecords;
                StartingSurfaceDrawer.StartingWindowRecord startingWindowRecord = (StartingSurfaceDrawer.StartingWindowRecord) startingWindowRecordManager.mStartingWindowRecords.get(i);
                StartingSurfaceDrawer.SnapshotRecord snapshotRecord = startingWindowRecord instanceof StartingSurfaceDrawer.SnapshotRecord ? (StartingSurfaceDrawer.SnapshotRecord) startingWindowRecord : null;
                if (snapshotRecord != null && snapshotRecord.hasImeSurface()) {
                    StartingWindowRemovalInfo startingWindowRemovalInfo = startingWindowRecordManager.mTmpRemovalInfo;
                    startingWindowRemovalInfo.taskId = i;
                    startingWindowRecordManager.removeWindow(startingWindowRemovalInfo, true);
                }
                StartingSurfaceDrawer.StartingWindowRecordManager startingWindowRecordManager2 = startingSurfaceDrawer.mWindowlessRecords;
                StartingSurfaceDrawer.StartingWindowRecord startingWindowRecord2 = (StartingSurfaceDrawer.StartingWindowRecord) startingWindowRecordManager2.mStartingWindowRecords.get(i);
                StartingSurfaceDrawer.SnapshotRecord snapshotRecord2 = startingWindowRecord2 instanceof StartingSurfaceDrawer.SnapshotRecord ? (StartingSurfaceDrawer.SnapshotRecord) startingWindowRecord2 : null;
                if (snapshotRecord2 != null && snapshotRecord2.hasImeSurface()) {
                    StartingWindowRemovalInfo startingWindowRemovalInfo2 = startingWindowRecordManager2.mTmpRemovalInfo;
                    startingWindowRemovalInfo2.taskId = i;
                    startingWindowRecordManager2.removeWindow(startingWindowRemovalInfo2, true);
                    break;
                }
                break;
            default:
                StartingWindowController startingWindowController2 = this.f$0;
                final int i2 = this.f$1;
                final SplashscreenWindowCreator splashscreenWindowCreator = startingWindowController2.mStartingSurfaceDrawer.mSplashscreenWindowCreator;
                StartingSurfaceDrawer.StartingWindowRecord startingWindowRecord3 = (StartingSurfaceDrawer.StartingWindowRecord) splashscreenWindowCreator.mStartingWindowRecordManager.mStartingWindowRecords.get(i2);
                SplashscreenWindowCreator.SplashWindowRecord splashWindowRecord = startingWindowRecord3 instanceof SplashscreenWindowCreator.SplashWindowRecord ? (SplashscreenWindowCreator.SplashWindowRecord) startingWindowRecord3 : null;
                SplashScreenView splashScreenView = splashWindowRecord != null ? splashWindowRecord.mSplashView : null;
                if (splashScreenView == null || !splashScreenView.isCopyable()) {
                    splashScreenViewParcelable = null;
                } else {
                    splashScreenViewParcelable = new SplashScreenView.SplashScreenViewParcelable(splashScreenView);
                    splashScreenViewParcelable.setClientCallback(new RemoteCallback(new RemoteCallback.OnResultListener() { // from class: com.android.wm.shell.startingsurface.SplashscreenWindowCreator$$ExternalSyntheticLambda3
                        public final void onResult(Bundle bundle) {
                            final SplashscreenWindowCreator splashscreenWindowCreator2 = SplashscreenWindowCreator.this;
                            final int i3 = i2;
                            splashscreenWindowCreator2.getClass();
                            ((HandlerExecutor) splashscreenWindowCreator2.mSplashScreenExecutor).execute(new Runnable() { // from class: com.android.wm.shell.startingsurface.SplashscreenWindowCreator$$ExternalSyntheticLambda4
                                @Override // java.lang.Runnable
                                public final void run() {
                                    SplashscreenWindowCreator.this.onAppSplashScreenViewRemoved(i3, false);
                                }
                            });
                        }
                    }));
                    splashScreenView.onCopied();
                    splashscreenWindowCreator.mAnimatedSplashScreenSurfaceHosts.append(i2, splashScreenView.getSurfaceHost());
                }
                if (ShellProtoLogCache.WM_SHELL_STARTING_WINDOW_enabled) {
                    ShellProtoLogImpl.m232v(ShellProtoLogGroup.WM_SHELL_STARTING_WINDOW, -1584013466, 13, null, Long.valueOf(i2), Boolean.valueOf(splashScreenViewParcelable != null));
                }
                ActivityTaskManager.getInstance().onSplashScreenViewCopyFinished(i2, splashScreenViewParcelable);
                break;
        }
    }
}
