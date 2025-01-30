package com.android.p038wm.shell.naturalswitching;

import android.media.AudioManager;
import android.os.IBinder;
import android.util.Log;
import android.view.HapticFeedbackConstants;
import android.view.SurfaceControl;
import android.window.TaskAppearedInfo;
import com.android.p038wm.shell.common.DismissButtonManager;
import com.samsung.android.multiwindow.MultiWindowManager;
import com.samsung.android.rune.CoreRune;
import com.sec.ims.presence.ServiceTuple;
import java.util.ArrayList;
import java.util.List;
import java.util.function.Consumer;

/* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
/* loaded from: classes2.dex */
public final /* synthetic */ class NaturalSwitchingLayout$$ExternalSyntheticLambda0 implements Runnable {
    public final /* synthetic */ int $r8$classId;
    public final /* synthetic */ Object f$0;

    public /* synthetic */ NaturalSwitchingLayout$$ExternalSyntheticLambda0(Object obj, int i) {
        this.$r8$classId = i;
        this.f$0 = obj;
    }

    @Override // java.lang.Runnable
    public final void run() {
        DragTargetView dragTargetView;
        int i = 2;
        switch (this.$r8$classId) {
            case 0:
                NaturalSwitchingLayout naturalSwitchingLayout = (NaturalSwitchingLayout) this.f$0;
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
                    naturalSwitchingLayout.mHandler.post(new NaturalSwitchingLayout$$ExternalSyntheticLambda0(naturalSwitchingLayout, i));
                    break;
                } else {
                    Log.w("NaturalSwitchingLayout", "onPreDraw: failed, reason=hide_requested");
                    break;
                }
            case 1:
                ((NaturalSwitchingLayout) this.f$0).hide(false);
                break;
            case 2:
                final NaturalSwitchingLayout naturalSwitchingLayout2 = (NaturalSwitchingLayout) this.f$0;
                if (!naturalSwitchingLayout2.mNaturalSwitchingStartReported) {
                    if (!naturalSwitchingLayout2.mHideRequested) {
                        int i2 = naturalSwitchingLayout2.mNaturalSwitchingMode;
                        if (i2 != 0) {
                            naturalSwitchingLayout2.mNaturalSwitchingStartReported = true;
                            if (i2 == 1 && !naturalSwitchingLayout2.mTaskVisibility.isTaskVisible(1)) {
                                naturalSwitchingLayout2.mSplitScreenController.setDividerVisibilityFromNS(false);
                            }
                            IBinder iBinder = null;
                            if (CoreRune.MW_NATURAL_SWITCHING_PIP && naturalSwitchingLayout2.mIsPipNaturalSwitching && (dragTargetView = naturalSwitchingLayout2.mDragTargetView) != null) {
                                iBinder = dragTargetView.getWindowToken();
                            }
                            Log.d("NaturalSwitchingLayout", "startNaturalSwitchingIfNeeded: " + naturalSwitchingLayout2);
                            MultiWindowManager.getInstance().startNaturalSwitching(naturalSwitchingLayout2.mBinder, iBinder);
                            if (!CoreRune.MW_NATURAL_SWITCHING_PIP || !naturalSwitchingLayout2.mIsPipNaturalSwitching || naturalSwitchingLayout2.mNaturalSwitchingMode != 2) {
                                ArrayList arrayList = naturalSwitchingLayout2.mHideTasks;
                                arrayList.clear();
                                List visibleTaskAppearedInfos = naturalSwitchingLayout2.mShellTaskOrganizer.getVisibleTaskAppearedInfos();
                                final SurfaceControl.Transaction transaction = new SurfaceControl.Transaction();
                                ((ArrayList) visibleTaskAppearedInfos).forEach(new Consumer() { // from class: com.android.wm.shell.naturalswitching.NaturalSwitchingLayout$$ExternalSyntheticLambda4
                                    @Override // java.util.function.Consumer
                                    public final void accept(Object obj) {
                                        NaturalSwitchingLayout naturalSwitchingLayout3 = NaturalSwitchingLayout.this;
                                        SurfaceControl.Transaction transaction2 = transaction;
                                        TaskAppearedInfo taskAppearedInfo = (TaskAppearedInfo) obj;
                                        naturalSwitchingLayout3.getClass();
                                        boolean z = taskAppearedInfo.getTaskInfo().taskId == naturalSwitchingLayout3.mTaskInfo.taskId;
                                        if (naturalSwitchingLayout3.mNaturalSwitchingMode != 2 || z) {
                                            if (!(CoreRune.MW_NATURAL_SWITCHING_PIP && naturalSwitchingLayout3.mIsPipNaturalSwitching && z) && taskAppearedInfo.getTaskInfo().displayId == 0) {
                                                transaction2.setAlpha(taskAppearedInfo.getLeash(), 0.0f);
                                                naturalSwitchingLayout3.mHideTasks.add(taskAppearedInfo);
                                            }
                                        }
                                    }
                                });
                                if (!arrayList.isEmpty()) {
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
            default:
                ((DismissButtonManager) this.f$0).cleanUpDismissTarget();
                break;
        }
    }
}
