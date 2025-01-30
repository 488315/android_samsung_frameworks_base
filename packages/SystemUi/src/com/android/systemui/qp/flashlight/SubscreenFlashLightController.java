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

/* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
/* loaded from: classes2.dex */
public final class SubscreenFlashLightController implements SubscreenQSControllerContract$Presenter {
    public static Context mContext;
    public static SubscreenFlashLightController sInstance;
    public final C20201 mDisplayListener;
    public SubscreenQSControllerContract$FlashLightView mFlashLightPresentationView;
    public final Handler mUiHandler = new Handler(Looper.getMainLooper());
    public final WakefulnessLifecycle mWakefulnessLifeCycle;
    public final C20212 mWakefulnessObserver;

    /* JADX WARN: Multi-variable type inference failed */
    /* JADX WARN: Type inference failed for: r0v1, types: [com.android.systemui.qp.flashlight.SubscreenFlashLightController$1, java.lang.Object] */
    /* JADX WARN: Type inference failed for: r1v1, types: [com.android.systemui.qp.flashlight.SubscreenFlashLightController$2] */
    private SubscreenFlashLightController(Context context) {
        ?? r0 = new DisplayLifecycle.Observer() { // from class: com.android.systemui.qp.flashlight.SubscreenFlashLightController.1
            @Override // com.android.systemui.keyguard.DisplayLifecycle.Observer
            public final void onFolderStateChanged(boolean z) {
                SubscreenFlashLightController subscreenFlashLightController = SubscreenFlashLightController.this;
                if (!z) {
                    if (((FlashlightControllerImpl) ((FlashlightController) Dependency.get(FlashlightController.class))).isEnabled()) {
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
        this.mDisplayListener = r0;
        this.mWakefulnessObserver = new WakefulnessLifecycle.Observer() { // from class: com.android.systemui.qp.flashlight.SubscreenFlashLightController.2
            public final void onPowerKeyPressed() {
                if (((FlashlightControllerImpl) ((FlashlightController) Dependency.get(FlashlightController.class))).isEnabled()) {
                    ((FlashlightControllerImpl) ((FlashlightController) Dependency.get(FlashlightController.class))).setFlashlight(false);
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
                ListPopupWindow$$ExternalSyntheticOutline0.m10m("onStartedGoingToSleep ", i, "SubscreenFlashLightController");
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
                ListPopupWindow$$ExternalSyntheticOutline0.m10m("onStartedWakingUp ", i, "SubscreenFlashLightController");
                if (i != 1) {
                    return;
                }
                onPowerKeyPressed();
            }
        };
        mContext = context;
        DisplayLifecycle displayLifecycle = (DisplayLifecycle) Dependency.get(DisplayLifecycle.class);
        if (displayLifecycle != 0) {
            displayLifecycle.addObserver(r0);
        }
        this.mWakefulnessLifeCycle = (WakefulnessLifecycle) Dependency.get(WakefulnessLifecycle.class);
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
            ((SubscreenUtil) Dependency.get(SubscreenUtil.class)).startActivity(mContext, "com.android.systemui.qp.flashlight.SubroomFlashLightSettingsActivity");
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
