package com.android.wm.shell.startingsurface;

import android.app.ActivityTaskManager;
import android.os.Bundle;
import android.os.RemoteCallback;
import android.window.SplashScreenView;
import android.window.StartingWindowRemovalInfo;
import com.android.internal.protolog.ProtoLogImpl_1771455215;
import com.android.wm.shell.protolog.ShellProtoLogGroup;
import com.android.wm.shell.startingsurface.SplashscreenWindowCreator;
import com.android.wm.shell.startingsurface.StartingSurfaceDrawer;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes3.dex */
public final /* synthetic */ class StartingWindowController$$ExternalSyntheticLambda0 implements Runnable {
    public final /* synthetic */ int $r8$classId;
    public final /* synthetic */ StartingWindowController f$0;
    public final /* synthetic */ int f$1;

    public /* synthetic */ StartingWindowController$$ExternalSyntheticLambda0(StartingWindowController startingWindowController, int i, int i2) {
        this.$r8$classId = i2;
        this.f$0 = startingWindowController;
        this.f$1 = i;
    }

    @Override // java.lang.Runnable
    public final void run() {
        switch (this.$r8$classId) {
            case 0:
                StartingWindowController startingWindowController = this.f$0;
                final int i = this.f$1;
                final SplashscreenWindowCreator splashscreenWindowCreator = startingWindowController.mStartingSurfaceDrawer.mSplashscreenWindowCreator;
                StartingSurfaceDrawer.StartingWindowRecord startingWindowRecord = (StartingSurfaceDrawer.StartingWindowRecord) splashscreenWindowCreator.mStartingWindowRecordManager.mStartingWindowRecords.get(i);
                SplashScreenView.SplashScreenViewParcelable splashScreenViewParcelable = null;
                SplashscreenWindowCreator.SplashWindowRecord splashWindowRecord = startingWindowRecord instanceof SplashscreenWindowCreator.SplashWindowRecord ? (SplashscreenWindowCreator.SplashWindowRecord) startingWindowRecord : null;
                SplashScreenView splashScreenView = splashWindowRecord != null ? splashWindowRecord.mSplashView : null;
                if (splashScreenView != null && splashScreenView.isCopyable()) {
                    splashScreenViewParcelable = new SplashScreenView.SplashScreenViewParcelable(splashScreenView);
                    splashScreenViewParcelable.setClientCallback(new RemoteCallback(new RemoteCallback.OnResultListener() { // from class: com.android.wm.shell.startingsurface.SplashscreenWindowCreator$$ExternalSyntheticLambda4
                        public final void onResult(Bundle bundle) {
                            final SplashscreenWindowCreator splashscreenWindowCreator2 = SplashscreenWindowCreator.this;
                            final int i2 = i;
                            splashscreenWindowCreator2.getClass();
                            splashscreenWindowCreator2.mSplashScreenExecutor.execute(new Runnable() { // from class: com.android.wm.shell.startingsurface.SplashscreenWindowCreator$$ExternalSyntheticLambda5
                                @Override // java.lang.Runnable
                                public final void run() {
                                    SplashscreenWindowCreator.this.onAppSplashScreenViewRemoved(i2, false);
                                }
                            });
                        }
                    }));
                    splashScreenView.onCopied();
                    splashscreenWindowCreator.mAnimatedSplashScreenSurfaceHosts.append(i, splashScreenView.getSurfaceHost());
                }
                if (ProtoLogImpl_1771455215.Cache.WM_SHELL_STARTING_WINDOW_enabled[1]) {
                    ProtoLogImpl_1771455215.v(ShellProtoLogGroup.WM_SHELL_STARTING_WINDOW, 5357618960991468999L, 13, Long.valueOf(i), Boolean.valueOf(splashScreenViewParcelable != null));
                }
                ActivityTaskManager.getInstance().onSplashScreenViewCopyFinished(i, splashScreenViewParcelable);
                break;
            case 1:
                StartingWindowController startingWindowController2 = this.f$0;
                int i2 = this.f$1;
                StartingSurfaceDrawer startingSurfaceDrawer = startingWindowController2.mStartingSurfaceDrawer;
                StartingSurfaceDrawer.StartingWindowRecordManager startingWindowRecordManager = startingSurfaceDrawer.mWindowRecords;
                StartingSurfaceDrawer.StartingWindowRecord startingWindowRecord2 = (StartingSurfaceDrawer.StartingWindowRecord) startingWindowRecordManager.mStartingWindowRecords.get(i2);
                StartingSurfaceDrawer.SnapshotRecord snapshotRecord = startingWindowRecord2 instanceof StartingSurfaceDrawer.SnapshotRecord ? (StartingSurfaceDrawer.SnapshotRecord) startingWindowRecord2 : null;
                if (snapshotRecord != null && snapshotRecord.hasImeSurface()) {
                    StartingWindowRemovalInfo startingWindowRemovalInfo = startingWindowRecordManager.mTmpRemovalInfo;
                    startingWindowRemovalInfo.taskId = i2;
                    startingWindowRecordManager.removeWindow(startingWindowRemovalInfo, true);
                }
                StartingSurfaceDrawer.StartingWindowRecordManager startingWindowRecordManager2 = startingSurfaceDrawer.mWindowlessRecords;
                StartingSurfaceDrawer.StartingWindowRecord startingWindowRecord3 = (StartingSurfaceDrawer.StartingWindowRecord) startingWindowRecordManager2.mStartingWindowRecords.get(i2);
                StartingSurfaceDrawer.SnapshotRecord snapshotRecord2 = startingWindowRecord3 instanceof StartingSurfaceDrawer.SnapshotRecord ? (StartingSurfaceDrawer.SnapshotRecord) startingWindowRecord3 : null;
                if (snapshotRecord2 != null && snapshotRecord2.hasImeSurface()) {
                    StartingWindowRemovalInfo startingWindowRemovalInfo2 = startingWindowRecordManager2.mTmpRemovalInfo;
                    startingWindowRemovalInfo2.taskId = i2;
                    startingWindowRecordManager2.removeWindow(startingWindowRemovalInfo2, true);
                    break;
                }
                break;
            default:
                this.f$0.mStartingSurfaceDrawer.mSplashscreenWindowCreator.onAppSplashScreenViewRemoved(this.f$1, true);
                break;
        }
    }
}
