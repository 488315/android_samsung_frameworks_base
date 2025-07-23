package com.android.systemui.qp.flashlight;

import android.content.Context;
import android.os.Handler;
import android.os.Looper;
import android.util.Log;
import androidx.appcompat.widget.ListPopupWindow$$ExternalSyntheticOutline0;
import com.android.systemui.Dependency;
import com.android.systemui.keyguard.DisplayLifecycle;
import com.android.systemui.keyguard.WakefulnessLifecycle;
import com.android.systemui.qp.SubscreenQSControllerContract$Presenter;
import com.android.systemui.qp.util.SubscreenUtil;
import com.android.systemui.statusbar.policy.FlashlightController;
import com.android.systemui.statusbar.policy.FlashlightControllerImpl;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes2.dex */
public class SubscreenFlashLightController implements SubscreenQSControllerContract$Presenter {
    public static Context mContext;
    public static SubscreenFlashLightController sInstance;
    public final AnonymousClass1 mDisplayListener;
    public SubroomFlashLightSettingsActivity mFlashLightPresentationView;
    public final Handler mUiHandler = new Handler(Looper.getMainLooper());
    public final WakefulnessLifecycle mWakefulnessLifeCycle;
    public final AnonymousClass2 mWakefulnessObserver;

    /* JADX WARN: Multi-variable type inference failed */
    /* JADX WARN: Type inference failed for: r0v1, types: [com.android.systemui.qp.flashlight.SubscreenFlashLightController$1, java.lang.Object] */
    /* JADX WARN: Type inference failed for: r1v1, types: [com.android.systemui.qp.flashlight.SubscreenFlashLightController$2] */
    private SubscreenFlashLightController(Context context) {
        ?? r0 = new DisplayLifecycle.Observer() { // from class: com.android.systemui.qp.flashlight.SubscreenFlashLightController.1
            @Override // com.android.systemui.keyguard.DisplayLifecycle.Observer
            public final void onFolderStateChanged(boolean z) {
                SubscreenFlashLightController subscreenFlashLightController = SubscreenFlashLightController.this;
                if (!z) {
                    if (((FlashlightControllerImpl) ((FlashlightController) Dependency.sDependency.getDependencyInner(FlashlightController.class))).isEnabled()) {
                        subscreenFlashLightController.startFlashActivity();
                    }
                    WakefulnessLifecycle wakefulnessLifecycle = subscreenFlashLightController.mWakefulnessLifeCycle;
                    if (wakefulnessLifecycle != null) {
                        wakefulnessLifecycle.addObserver(subscreenFlashLightController.mWakefulnessObserver);
                        return;
                    }
                    return;
                }
                SubroomFlashLightSettingsActivity subroomFlashLightSettingsActivity = subscreenFlashLightController.mFlashLightPresentationView;
                if (subroomFlashLightSettingsActivity != null) {
                    subroomFlashLightSettingsActivity.finishFlashLightActivity();
                }
                WakefulnessLifecycle wakefulnessLifecycle2 = subscreenFlashLightController.mWakefulnessLifeCycle;
                if (wakefulnessLifecycle2 != null) {
                    wakefulnessLifecycle2.removeObserver(subscreenFlashLightController.mWakefulnessObserver);
                }
            }
        };
        this.mDisplayListener = r0;
        this.mWakefulnessObserver = new WakefulnessLifecycle.Observer() { // from class: com.android.systemui.qp.flashlight.SubscreenFlashLightController.2
            @Override // com.android.systemui.keyguard.WakefulnessLifecycle.Observer
            public final void onStartedGoingToSleep() {
                SubscreenFlashLightController subscreenFlashLightController = SubscreenFlashLightController.this;
                WakefulnessLifecycle wakefulnessLifecycle = subscreenFlashLightController.mWakefulnessLifeCycle;
                if (wakefulnessLifecycle == null) {
                    return;
                }
                int i = wakefulnessLifecycle.mLastSleepReason;
                ListPopupWindow$$ExternalSyntheticOutline0.m(i, "onStartedGoingToSleep ", "SubscreenFlashLightController");
                if (i != 4) {
                    return;
                }
                subscreenFlashLightController.onPowerKeyPressed();
            }

            @Override // com.android.systemui.keyguard.WakefulnessLifecycle.Observer
            public final void onStartedWakingUp() {
                SubscreenFlashLightController subscreenFlashLightController = SubscreenFlashLightController.this;
                WakefulnessLifecycle wakefulnessLifecycle = subscreenFlashLightController.mWakefulnessLifeCycle;
                if (wakefulnessLifecycle == null) {
                    return;
                }
                int i = wakefulnessLifecycle.mLastWakeReason;
                ListPopupWindow$$ExternalSyntheticOutline0.m(i, "onStartedWakingUp ", "SubscreenFlashLightController");
                if (i != 1) {
                    return;
                }
                subscreenFlashLightController.onPowerKeyPressed();
            }
        };
        mContext = context;
        DisplayLifecycle displayLifecycle = (DisplayLifecycle) Dependency.sDependency.getDependencyInner(DisplayLifecycle.class);
        if (displayLifecycle != 0) {
            displayLifecycle.addObserver(r0);
        }
        this.mWakefulnessLifeCycle = (WakefulnessLifecycle) Dependency.sDependency.getDependencyInner(WakefulnessLifecycle.class);
    }

    public static SubscreenFlashLightController getInstance(Context context) {
        if (sInstance == null) {
            mContext = context;
            sInstance = new SubscreenFlashLightController(context);
        }
        return sInstance;
    }

    public final void finishFlashLightActivity() {
        SubroomFlashLightSettingsActivity subroomFlashLightSettingsActivity = this.mFlashLightPresentationView;
        if (subroomFlashLightSettingsActivity == null || subroomFlashLightSettingsActivity.getActivityState() == 0) {
            return;
        }
        this.mFlashLightPresentationView.finishFlashLightActivity();
    }

    public final void onPowerKeyPressed() {
        if (((FlashlightControllerImpl) ((FlashlightController) Dependency.sDependency.getDependencyInner(FlashlightController.class))).isEnabled()) {
            ((FlashlightControllerImpl) ((FlashlightController) Dependency.sDependency.getDependencyInner(FlashlightController.class))).setFlashlight(false);
        }
        finishFlashLightActivity();
    }

    public final void startFlashActivity() {
        SubroomFlashLightSettingsActivity subroomFlashLightSettingsActivity = this.mFlashLightPresentationView;
        if (subroomFlashLightSettingsActivity == null || subroomFlashLightSettingsActivity.getActivityState() != 1) {
            Log.d("SubscreenFlashLightController", "FlashLight presentation Activity starting");
            ((SubscreenUtil) Dependency.sDependency.getDependencyInner(SubscreenUtil.class)).startActivity(mContext, "com.android.systemui.qp.flashlight.SubroomFlashLightSettingsActivity");
        } else {
            Log.d("SubscreenFlashLightController", "FlashLight presentation Activity already in stack or in top: " + this.mFlashLightPresentationView.getActivityState());
        }
    }

    @Override // com.android.systemui.qp.SubscreenQSControllerContract$Presenter
    public final void registerReceiver(boolean z) {
    }

    @Override // com.android.systemui.qp.SubscreenQSControllerContract$Presenter
    public final void unRegisterReceiver(boolean z) {
    }
}
