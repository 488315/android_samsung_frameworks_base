package com.android.wm.shell.naturalswitching;

import android.media.AudioManager;
import android.os.IBinder;
import android.util.Log;
import android.view.HapticFeedbackConstants;
import android.view.SurfaceControl;
import android.window.TaskAppearedInfo;
import com.samsung.android.multiwindow.MultiWindowManager;
import com.samsung.android.rune.CoreRune;
import com.sec.ims.presence.ServiceTuple;
import java.util.ArrayList;
import java.util.List;
import java.util.function.Consumer;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes3.dex */
public final /* synthetic */ class NaturalSwitchingLayout$$ExternalSyntheticLambda0 implements Runnable {
    public final /* synthetic */ int $r8$classId;
    public final /* synthetic */ NaturalSwitchingLayout f$0;

    public /* synthetic */ NaturalSwitchingLayout$$ExternalSyntheticLambda0(NaturalSwitchingLayout naturalSwitchingLayout, int i) {
        this.$r8$classId = i;
        this.f$0 = naturalSwitchingLayout;
    }

    @Override // java.lang.Runnable
    public final void run() {
        DragTargetView dragTargetView;
        int i = this.$r8$classId;
        final NaturalSwitchingLayout naturalSwitchingLayout = this.f$0;
        switch (i) {
            case 0:
                if (!naturalSwitchingLayout.mHideRequested) {
                    naturalSwitchingLayout.mReadyToStart = true;
                    NonDragTargetView nonDragTargetView = naturalSwitchingLayout.mNonDragTargetView;
                    nonDragTargetView.getClass();
                    Log.d("NonDragTargetView", "showBackground");
                    nonDragTargetView.mMainView.setVisibility(0);
                    nonDragTargetView.mDimView.setVisibility(nonDragTargetView.mNaturalSwitchingMode != 1 ? 8 : 0);
                    if (CoreRune.MW_NATURAL_SWITCHING_PIP && naturalSwitchingLayout.mIsPipNaturalSwitching) {
                        DragTargetView dragTargetView2 = naturalSwitchingLayout.mDragTargetView;
                        dragTargetView2.getClass();
                        dragTargetView2.performHapticFeedback(HapticFeedbackConstants.semGetVibrationIndex(108));
                        AudioManager audioManager = (AudioManager) dragTargetView2.getContext().getSystemService(ServiceTuple.MEDIA_CAP_AUDIO);
                        if (audioManager == null) {
                            Log.w("DragTargetView", "performSoundEffect: Couldn't get audio manager");
                        } else {
                            audioManager.playSoundEffect(106);
                        }
                        dragTargetView2.mNonDragTargetView.startTransition(true);
                    } else {
                        naturalSwitchingLayout.mDragTargetView.startSpringAnimation(true);
                    }
                    naturalSwitchingLayout.mHandler.post(new NaturalSwitchingLayout$$ExternalSyntheticLambda0(naturalSwitchingLayout, 1));
                    break;
                } else {
                    Log.w("NaturalSwitchingLayout", "onPreDraw: failed, reason=hide_requested");
                    break;
                }
                break;
            default:
                if (!naturalSwitchingLayout.mNaturalSwitchingStartReported) {
                    if (!naturalSwitchingLayout.mHideRequested) {
                        int i2 = naturalSwitchingLayout.mNaturalSwitchingMode;
                        if (i2 != 0) {
                            naturalSwitchingLayout.mNaturalSwitchingStartReported = true;
                            if (i2 == 1 && !naturalSwitchingLayout.mTaskVisibility.isTaskVisible(1)) {
                                naturalSwitchingLayout.mSplitScreenController.setDividerVisibilityFromNS(false);
                            }
                            boolean z = CoreRune.MW_NATURAL_SWITCHING_PIP;
                            IBinder iBinder = null;
                            if (z && naturalSwitchingLayout.mIsPipNaturalSwitching && (dragTargetView = naturalSwitchingLayout.mDragTargetView) != null) {
                                iBinder = dragTargetView.getWindowToken();
                            }
                            Log.d("NaturalSwitchingLayout", "startNaturalSwitchingIfNeeded: " + naturalSwitchingLayout);
                            MultiWindowManager.getInstance().startNaturalSwitching(naturalSwitchingLayout.mBinder, iBinder);
                            if (!z || !naturalSwitchingLayout.mIsPipNaturalSwitching || naturalSwitchingLayout.mNaturalSwitchingMode != 2) {
                                naturalSwitchingLayout.mHideTasks.clear();
                                List visibleTaskAppearedInfos = naturalSwitchingLayout.mShellTaskOrganizer.getVisibleTaskAppearedInfos();
                                final SurfaceControl.Transaction transaction = new SurfaceControl.Transaction();
                                ((ArrayList) visibleTaskAppearedInfos).forEach(new Consumer() { // from class: com.android.wm.shell.naturalswitching.NaturalSwitchingLayout$$ExternalSyntheticLambda6
                                    @Override // java.util.function.Consumer
                                    public final void accept(Object obj) {
                                        NaturalSwitchingLayout naturalSwitchingLayout2 = NaturalSwitchingLayout.this;
                                        SurfaceControl.Transaction transaction2 = transaction;
                                        TaskAppearedInfo taskAppearedInfo = (TaskAppearedInfo) obj;
                                        boolean z2 = NaturalSwitchingLayout.DEBUG_DEV;
                                        naturalSwitchingLayout2.getClass();
                                        boolean z3 = taskAppearedInfo.getTaskInfo().taskId == naturalSwitchingLayout2.mTaskInfo.taskId;
                                        if (naturalSwitchingLayout2.mNaturalSwitchingMode != 2 || z3) {
                                            if (!(CoreRune.MW_NATURAL_SWITCHING_PIP && naturalSwitchingLayout2.mIsPipNaturalSwitching && z3) && taskAppearedInfo.getTaskInfo().displayId == 0) {
                                                transaction2.setAlpha(taskAppearedInfo.getLeash(), 0.0f);
                                                naturalSwitchingLayout2.mHideTasks.add(taskAppearedInfo);
                                            }
                                        }
                                    }
                                });
                                if (!naturalSwitchingLayout.mHideTasks.isEmpty()) {
                                    transaction.apply();
                                    break;
                                }
                            }
                        }
                    } else {
                        Log.w("NaturalSwitchingLayout", "startNaturalSwitchingIfNeeded: failed, reason=hide_requested");
                        break;
                    }
                } else {
                    Log.w("NaturalSwitchingLayout", "startNaturalSwitchingIfNeeded: failed, already started!");
                    break;
                }
                break;
        }
    }
}
