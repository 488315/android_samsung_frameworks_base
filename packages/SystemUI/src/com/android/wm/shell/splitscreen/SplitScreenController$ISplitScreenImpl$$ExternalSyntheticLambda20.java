package com.android.wm.shell.splitscreen;

import android.R;
import android.app.ActivityManager;
import android.app.PendingIntent;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.UserHandle;
import android.os.Vibrator;
import android.util.Slog;
import android.view.HapticFeedbackConstants;
import android.widget.Toast;
import android.window.WindowContainerTransaction;
import com.android.wm.shell.fullscreen.FullscreenTaskListener;
import com.android.wm.shell.splitscreen.SplitScreenController;
import com.samsung.android.core.CoreSaLogger;
import com.samsung.android.multiwindow.MultiWindowUtils;
import com.samsung.android.rune.CoreRune;
import java.util.function.Consumer;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes3.dex */
public final /* synthetic */ class SplitScreenController$ISplitScreenImpl$$ExternalSyntheticLambda20 implements Consumer {
    public final /* synthetic */ int $r8$classId;
    public final /* synthetic */ Object f$0;
    public final /* synthetic */ int f$1;
    public final /* synthetic */ int f$2;
    public final /* synthetic */ int f$3;

    public /* synthetic */ SplitScreenController$ISplitScreenImpl$$ExternalSyntheticLambda20(int i, Object obj, int i2, int i3, int i4) {
        this.$r8$classId = i4;
        this.f$0 = obj;
        this.f$1 = i;
        this.f$2 = i2;
        this.f$3 = i3;
    }

    @Override // java.util.function.Consumer
    public final void accept(Object obj) {
        SplitScreenController splitScreenController;
        switch (this.$r8$classId) {
            case 0:
                boolean[] zArr = (boolean[]) this.f$0;
                final int i = this.f$1;
                final int i2 = this.f$2;
                final int i3 = this.f$3;
                int i4 = SplitScreenController.ISplitScreenImpl.$r8$clinit;
                final StageCoordinator stageCoordinator = ((SplitScreenController) obj).mStageCoordinator;
                final boolean[] zArr2 = {false};
                stageCoordinator.mRecentTasks.ifPresent(new Consumer() { // from class: com.android.wm.shell.splitscreen.StageCoordinator$$ExternalSyntheticLambda24
                    /* JADX WARN: Code restructure failed: missing block: B:32:0x00c5, code lost:
                    
                        r15 = com.android.wm.shell.splitscreen.AppPairShortcutController.supportAppPairShortCut(r15, r12);
                     */
                    @Override // java.util.function.Consumer
                    /*
                        Code decompiled incorrectly, please refer to instructions dump.
                        To view partially-correct code enable 'Show inconsistent code' option in preferences
                    */
                    public final void accept(java.lang.Object r15) {
                        /*
                            Method dump skipped, instructions count: 348
                            To view this dump change 'Code comments level' option to 'DEBUG'
                        */
                        throw new UnsupportedOperationException("Method not decompiled: com.android.wm.shell.splitscreen.StageCoordinator$$ExternalSyntheticLambda24.accept(java.lang.Object):void");
                    }
                });
                zArr[0] = zArr2[0];
                break;
            default:
                SplitScreenController.SplitTwoFingerGestureStarter splitTwoFingerGestureStarter = (SplitScreenController.SplitTwoFingerGestureStarter) this.f$0;
                int i5 = this.f$1;
                int i6 = this.f$2;
                int i7 = this.f$3;
                ActivityManager.RunningTaskInfo runningTaskInfo = (ActivityManager.RunningTaskInfo) obj;
                String str = splitTwoFingerGestureStarter.TAG;
                boolean z = splitTwoFingerGestureStarter.DEBUG;
                if (z) {
                    Slog.d(str, "top task: " + runningTaskInfo);
                }
                int activityType = runningTaskInfo.getActivityType();
                boolean z2 = runningTaskInfo.originallySupportedMultiWindow;
                SplitScreenController splitScreenController2 = SplitScreenController.this;
                if (!z2 || activityType != 1) {
                    if (activityType == 1) {
                        FullscreenTaskListener fullscreenTaskListener = splitScreenController2.mFullscreenTaskListener;
                        if (fullscreenTaskListener != null) {
                            fullscreenTaskListener.animForAffordance(runningTaskInfo.taskId, i5 != 1 ? i5 != 3 ? i5 != 4 ? 0 : 2 : 8 : 4);
                            if (splitTwoFingerGestureStarter.mVibrator == null) {
                                splitTwoFingerGestureStarter.mVibrator = (Vibrator) splitScreenController2.mContext.getSystemService(Vibrator.class);
                            }
                            splitTwoFingerGestureStarter.mVibrator.performHapticFeedback(HapticFeedbackConstants.semGetVibrationIndex(127), "Split#performHapticFeedback", 0, 0);
                        }
                        Context context = splitScreenController2.mContext;
                        Toast.makeText(context, context.getString(R.string.indeterminate_progress_39), 0).show();
                    }
                    if (z) {
                        Slog.d(str, "top task doesn't fit in split. info=" + runningTaskInfo + " type=" + runningTaskInfo.getActivityType());
                        break;
                    }
                } else {
                    ComponentName componentName = runningTaskInfo.baseActivity;
                    if (componentName == null) {
                        componentName = runningTaskInfo.baseIntent.getComponent();
                    }
                    Intent edgeAllAppsActivityIntent = MultiWindowUtils.getEdgeAllAppsActivityIntent(componentName, runningTaskInfo.userId, runningTaskInfo.taskId);
                    if (z) {
                        Slog.d(str, "enterSplitIfPossible: start intent=" + edgeAllAppsActivityIntent);
                    }
                    if (splitScreenController2.mStageCoordinator.mMainStage.mIsActive) {
                        PendingIntent activityAsUser = PendingIntent.getActivityAsUser(splitScreenController2.mContext, 0, edgeAllAppsActivityIntent, 1107296256, null, UserHandle.CURRENT);
                        WindowContainerTransaction windowContainerTransaction = new WindowContainerTransaction();
                        windowContainerTransaction.sendPendingIntent(activityAsUser, edgeAllAppsActivityIntent, splitScreenController2.mStageCoordinator.resolveStartStage(-1, i6, new Bundle(), windowContainerTransaction, i7));
                        splitScreenController2.mTaskOrganizer.applyTransaction(windowContainerTransaction);
                        splitScreenController = splitScreenController2;
                    } else {
                        splitScreenController2.startIntent(edgeAllAppsActivityIntent, null, i6, i7, null);
                        splitScreenController = splitScreenController2;
                    }
                    if (splitTwoFingerGestureStarter.mVibrator == null) {
                        splitTwoFingerGestureStarter.mVibrator = (Vibrator) splitScreenController.mContext.getSystemService(Vibrator.class);
                    }
                    splitTwoFingerGestureStarter.mVibrator.performHapticFeedback(HapticFeedbackConstants.semGetVibrationIndex(127), "Split#performHapticFeedback", 0, 0);
                    if (CoreRune.MW_SPLIT_FULL_TO_SPLIT_BY_GESTURE_SA_LOGGING) {
                        CoreSaLogger.logForAdvanced("1000", "From Gesture");
                        break;
                    }
                }
                break;
        }
    }
}
