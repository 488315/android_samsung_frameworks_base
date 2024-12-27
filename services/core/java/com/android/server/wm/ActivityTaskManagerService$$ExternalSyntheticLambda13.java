package com.android.server.wm;

import android.os.Bundle;
import android.os.RemoteCallback;
import android.os.Trace;
import android.util.Log;
import android.util.Slog;
import com.android.internal.util.FrameworkStatsLog;
import com.android.server.BatteryService$$ExternalSyntheticOutline0;
import com.android.server.HeimdAllFsService$$ExternalSyntheticOutline0;
import com.android.server.policy.PhoneWindowManagerExt;
import com.android.server.policy.PhoneWindowManagerExt$$ExternalSyntheticLambda1;
import com.android.server.wm.ActivityRecord;
import com.android.server.wm.KeyguardController;
import com.samsung.android.rune.CoreRune;
import com.samsung.android.rune.InputRune;
import java.util.ArrayList;
import java.util.Optional;
import java.util.function.Consumer;

/* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
/* loaded from: classes2.dex */
public final /* synthetic */ class ActivityTaskManagerService$$ExternalSyntheticLambda13 implements Consumer {
    public final /* synthetic */ int $r8$classId;
    public final /* synthetic */ ActivityTaskManagerService f$0;
    public final /* synthetic */ int f$1;

    public /* synthetic */ ActivityTaskManagerService$$ExternalSyntheticLambda13(ActivityTaskManagerService activityTaskManagerService, int i, int i2) {
        this.$r8$classId = i2;
        this.f$0 = activityTaskManagerService;
        this.f$1 = i;
    }

    @Override // java.util.function.Consumer
    public final void accept(Object obj) {
        switch (this.$r8$classId) {
            case 0:
                ActivityTaskManagerService activityTaskManagerService = this.f$0;
                int i = this.f$1;
                KeyguardController keyguardController = activityTaskManagerService.mKeyguardController;
                int i2 = ((DisplayContent) obj).mDisplayId;
                KeyguardController.KeyguardDisplayState displayState = keyguardController.getDisplayState(i2);
                if (!displayState.mKeyguardShowing || displayState.mKeyguardGoingAway) {
                    StringBuilder m = BatteryService$$ExternalSyntheticOutline0.m(i2, "keyguardGoingAway returned, d=", ", flags=0x");
                    BatteryService$$ExternalSyntheticOutline0.m(i, m, ", mKeyguardShowing=");
                    m.append(displayState.mKeyguardShowing);
                    m.append(", mKeyguardGoingAway=");
                    HeimdAllFsService$$ExternalSyntheticOutline0.m("ActivityTaskManager", m, displayState.mKeyguardGoingAway);
                    return;
                }
                Trace.traceBegin(32L, "keyguardGoingAway");
                Slog.d("ActivityTaskManager", "keyguardGoingAway d" + i2 + ", fl=0x" + Integer.toHexString(i));
                boolean z = (i & 256) != 0;
                boolean z2 = CoreRune.MW_MULTI_SPLIT_FOLDING_POLICY;
                ActivityTaskManagerService activityTaskManagerService2 = keyguardController.mService;
                if (z2) {
                    activityTaskManagerService2.mMultiWindowFoldController.getClass();
                }
                if (i2 == 0) {
                    displayState.mWakeAndUnlock = z;
                }
                boolean z3 = InputRune.PWM_FINGERPRINT_SIDE_TOUCH;
                if (z3 && z) {
                    WindowManagerServiceExt windowManagerServiceExt = keyguardController.mWindowManager.mExt;
                    boolean z4 = z3 && z;
                    PhoneWindowManagerExt phoneWindowManagerExt = windowManagerServiceExt.mPolicyExt;
                    phoneWindowManagerExt.getClass();
                    Slog.d("PhoneWindowManagerExt", "UnlockFP triggered. isWakeAndUnlock=" + z4);
                    if (z4) {
                        PhoneWindowManagerExt.PolicyExtHandler policyExtHandler = phoneWindowManagerExt.mHandler;
                        PhoneWindowManagerExt$$ExternalSyntheticLambda1 phoneWindowManagerExt$$ExternalSyntheticLambda1 = phoneWindowManagerExt.mWakeAndUnlockRunning;
                        policyExtHandler.removeCallbacks(phoneWindowManagerExt$$ExternalSyntheticLambda1);
                        phoneWindowManagerExt.mWakeAndUnlockTriggered = true;
                        phoneWindowManagerExt.mHandler.postDelayed(phoneWindowManagerExt$$ExternalSyntheticLambda1, 1000L);
                    }
                    if (CoreRune.FW_FOLD_SA_LOGGING) {
                        phoneWindowManagerExt.sendFoldSaLoggingCanceledIfNeeded();
                    }
                }
                activityTaskManagerService2.deferWindowLayout();
                displayState.mKeyguardGoingAway = true;
                try {
                    EventLogTags.writeWmSetKeyguardShown(i2, displayState.mKeyguardShowing ? 1 : 0, displayState.mAodShowing ? 1 : 0, 1, displayState.mOccluded ? 1 : 0, "keyguardGoingAway");
                    int i3 = (i & 1) != 0 ? FrameworkStatsLog.HDMI_CEC_MESSAGE_REPORTED__USER_CONTROL_PRESSED_COMMAND__UP : 256;
                    if ((i & 2) != 0) {
                        i3 |= 2;
                    }
                    if ((i & 4) != 0) {
                        i3 |= 4;
                    }
                    if ((i & 8) != 0) {
                        i3 |= 8;
                    }
                    if ((i & 16) != 0) {
                        i3 |= 512;
                    }
                    DisplayContent displayContent = keyguardController.mRootWindowContainer.mDefaultDisplay;
                    displayContent.prepareAppTransition(7, i3);
                    displayContent.mAtmService.mWindowOrganizerController.mTransitionController.requestTransitionIfNeeded(4, i3, null, displayContent);
                    keyguardController.updateKeyguardSleepToken();
                    keyguardController.mRootWindowContainer.resumeFocusedTasksTopActivities();
                    keyguardController.mRootWindowContainer.ensureActivitiesVisible();
                    RootWindowContainer rootWindowContainer = keyguardController.mRootWindowContainer;
                    rootWindowContainer.getClass();
                    rootWindowContainer.forAllActivities(new RootWindowContainer$$ExternalSyntheticLambda2(rootWindowContainer, new ArrayList(), 1));
                    if (CoreRune.FW_APPLOCK) {
                        Task rootTask = keyguardController.mRootWindowContainer.mDefaultDisplay.getRootTask(1, 1);
                        if (rootTask != null) {
                            ActivityRecord topActivity = rootTask.getTopActivity(false, true);
                            if (topActivity != null) {
                                if (keyguardController.mTaskSupervisor.mTopResumedActivity != topActivity) {
                                    if (!topActivity.isState(ActivityRecord.State.RESUMED)) {
                                        if (topActivity.nowVisible) {
                                        }
                                    }
                                    Log.d("ActivityTaskManager", "Applock Activity record " + topActivity);
                                    activityTaskManagerService2.mExt.startAppLockActivity(topActivity);
                                }
                            }
                        }
                    }
                    keyguardController.mWindowManager.executeAppTransition();
                    activityTaskManagerService2.continueWindowLayout();
                    Trace.traceEnd(32L);
                    return;
                } catch (Throwable th) {
                    activityTaskManagerService2.continueWindowLayout();
                    Trace.traceEnd(32L);
                    throw th;
                }
            default:
                ActivityTaskManagerService activityTaskManagerService3 = this.f$0;
                int i4 = this.f$1;
                RemoteCallback remoteCallback = (RemoteCallback) obj;
                activityTaskManagerService3.getClass();
                try {
                    Bundle bundle = new Bundle();
                    bundle.putInt("TASK_ID", i4);
                    remoteCallback.sendResult(bundle);
                    return;
                } catch (Exception e) {
                    Slog.e("ActivityTaskManager", "notifyDedicatedState : ", e);
                    activityTaskManagerService3.mCb4Task = Optional.empty();
                    return;
                }
        }
    }
}
