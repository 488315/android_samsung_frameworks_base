package com.android.systemui.qp.flashlight;

import android.content.Context;
import android.os.Handler;
import android.os.Looper;
import android.util.Log;
import androidx.appcompat.widget.ListPopupWindow$$ExternalSyntheticOutline0;
import com.android.systemui.Dependency;
import com.android.systemui.keyguard.DisplayLifecycle;
import com.android.systemui.keyguard.WakefulnessLifecycle;
import com.android.systemui.qp.SubscreenQSControllerContract$FlashLightView;
import com.android.systemui.qp.SubscreenQSControllerContract$Presenter;
import com.android.systemui.qp.util.SubscreenUtil;
import com.android.systemui.statusbar.policy.FlashlightController;
import com.android.systemui.statusbar.policy.FlashlightControllerImpl;

public final class SubscreenFlashLightController implements SubscreenQSControllerContract$Presenter {
    public static Context mContext;
    public static SubscreenFlashLightController sInstance;
    public final AnonymousClass1 mDisplayListener;
    public SubscreenQSControllerContract$FlashLightView mFlashLightPresentationView;
    public final Handler mUiHandler = new Handler(Looper.getMainLooper());
    public final WakefulnessLifecycle mWakefulnessLifeCycle;
    public final AnonymousClass2 mWakefulnessObserver;

    private SubscreenFlashLightController(Context context) {
        DisplayLifecycle.Observer observer = new DisplayLifecycle.Observer() { // from class: com.android.systemui.qp.flashlight.SubscreenFlashLightController.1
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
                SubscreenQSControllerContract$FlashLightView subscreenQSControllerContract$FlashLightView = subscreenFlashLightController.mFlashLightPresentationView;
                if (subscreenQSControllerContract$FlashLightView != null) {
                    ((SubroomFlashLightSettingsActivity) subscreenQSControllerContract$FlashLightView).finishFlashLightActivity();
                }
                WakefulnessLifecycle wakefulnessLifecycle2 = subscreenFlashLightController.mWakefulnessLifeCycle;
                if (wakefulnessLifecycle2 != null) {
                    wakefulnessLifecycle2.removeObserver(subscreenFlashLightController.mWakefulnessObserver);
                }
            }
        };
        this.mWakefulnessObserver = new WakefulnessLifecycle.Observer() { // from class: com.android.systemui.qp.flashlight.SubscreenFlashLightController.2
            public final void onPowerKeyPressed() {
                if (((FlashlightControllerImpl) ((FlashlightController) Dependency.sDependency.getDependencyInner(FlashlightController.class))).isEnabled()) {
                    ((FlashlightControllerImpl) ((FlashlightController) Dependency.sDependency.getDependencyInner(FlashlightController.class))).setFlashlight(false);
                }
                SubscreenFlashLightController.this.finishFlashLightActivity();
            }

            @Override // com.android.systemui.keyguard.WakefulnessLifecycle.Observer
            public final void onStartedGoingToSleep() {
                WakefulnessLifecycle wakefulnessLifecycle = SubscreenFlashLightController.this.mWakefulnessLifeCycle;
                if (wakefulnessLifecycle == null) {
                    return;
                }
                int i = wakefulnessLifecycle.mLastSleepReason;
                ListPopupWindow$$ExternalSyntheticOutline0.m(i, "onStartedGoingToSleep ", "SubscreenFlashLightController");
                if (i != 4) {
                    return;
                }
                onPowerKeyPressed();
            }

            @Override // com.android.systemui.keyguard.WakefulnessLifecycle.Observer
            public final void onStartedWakingUp() {
                WakefulnessLifecycle wakefulnessLifecycle = SubscreenFlashLightController.this.mWakefulnessLifeCycle;
                if (wakefulnessLifecycle == null) {
                    return;
                }
                int i = wakefulnessLifecycle.mLastWakeReason;
                ListPopupWindow$$ExternalSyntheticOutline0.m(i, "onStartedWakingUp ", "SubscreenFlashLightController");
                if (i != 1) {
                    return;
                }
                onPowerKeyPressed();
            }
        };
        mContext = context;
        DisplayLifecycle displayLifecycle = (DisplayLifecycle) Dependency.sDependency.getDependencyInner(DisplayLifecycle.class);
        if (displayLifecycle != null) {
            displayLifecycle.addObserver(observer);
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
        SubscreenQSControllerContract$FlashLightView subscreenQSControllerContract$FlashLightView = this.mFlashLightPresentationView;
        if (subscreenQSControllerContract$FlashLightView == null || ((SubroomFlashLightSettingsActivity) subscreenQSControllerContract$FlashLightView).getActivityState() == 0) {
            return;
        }
        ((SubroomFlashLightSettingsActivity) this.mFlashLightPresentationView).finishFlashLightActivity();
    }

    public final void startFlashActivity() {
        SubscreenQSControllerContract$FlashLightView subscreenQSControllerContract$FlashLightView = this.mFlashLightPresentationView;
        if (subscreenQSControllerContract$FlashLightView == null || ((SubroomFlashLightSettingsActivity) subscreenQSControllerContract$FlashLightView).getActivityState() != 1) {
            Log.d("SubscreenFlashLightController", "FlashLight presentation Activity starting");
            ((SubscreenUtil) Dependency.sDependency.getDependencyInner(SubscreenUtil.class)).startActivity(mContext, "com.android.systemui.qp.flashlight.SubroomFlashLightSettingsActivity");
        } else {
            Log.d("SubscreenFlashLightController", "FlashLight presentation Activity already in stack or in top: " + ((SubroomFlashLightSettingsActivity) this.mFlashLightPresentationView).getActivityState());
        }
    }

    @Override // com.android.systemui.qp.SubscreenQSControllerContract$Presenter
    public final void registerReceiver(boolean z) {
    }

    @Override // com.android.systemui.qp.SubscreenQSControllerContract$Presenter
    public final void unRegisterReceiver(boolean z) {
    }
}
