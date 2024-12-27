package com.android.systemui.keyguard.data.quickaffordance;

import android.app.admin.DevicePolicyManager;
import android.content.Context;
import android.content.pm.PackageManager;
import com.android.systemui.R;
import com.android.systemui.animation.Expandable;
import com.android.systemui.camera.CameraGestureHelper;
import com.android.systemui.keyguard.data.quickaffordance.KeyguardQuickAffordanceConfig;
import com.android.systemui.settings.UserTracker;
import dagger.Lazy;
import kotlinx.coroutines.CoroutineDispatcher;
import kotlinx.coroutines.flow.Flow;
import kotlinx.coroutines.flow.SafeFlow;

/* compiled from: qb/89523975 427a50d40ec74a85ca352b86f77450b1c52ece5389e11158752b0d641a3a5098 */
public final class CameraQuickAffordanceConfig implements KeyguardQuickAffordanceConfig {
    public static final /* synthetic */ int $r8$clinit = 0;
    public final CoroutineDispatcher backgroundDispatcher;
    public final Lazy cameraGestureHelper;
    public final Context context;
    public final DevicePolicyManager devicePolicyManager;
    public final PackageManager packageManager;
    public final UserTracker userTracker;

    public CameraQuickAffordanceConfig(Context context, PackageManager packageManager, Lazy lazy, UserTracker userTracker, DevicePolicyManager devicePolicyManager, CoroutineDispatcher coroutineDispatcher) {
        this.context = context;
        this.packageManager = packageManager;
        this.cameraGestureHelper = lazy;
        this.userTracker = userTracker;
        this.devicePolicyManager = devicePolicyManager;
        this.backgroundDispatcher = coroutineDispatcher;
    }

    @Override // com.android.systemui.keyguard.data.quickaffordance.KeyguardQuickAffordanceConfig
    public final String getKey() {
        return "camera";
    }

    @Override // com.android.systemui.keyguard.data.quickaffordance.KeyguardQuickAffordanceConfig
    public final Flow getLockScreenState() {
        return new SafeFlow(new CameraQuickAffordanceConfig$lockScreenState$1(this, null));
    }

    @Override // com.android.systemui.keyguard.data.quickaffordance.KeyguardQuickAffordanceConfig
    public final int getPickerIconResourceId() {
        return R.drawable.ic_camera;
    }

    /* JADX WARN: Removed duplicated region for block: B:18:0x0065  */
    /* JADX WARN: Removed duplicated region for block: B:21:0x0071  */
    /* JADX WARN: Removed duplicated region for block: B:23:0x003b  */
    /* JADX WARN: Removed duplicated region for block: B:8:0x0023  */
    @Override // com.android.systemui.keyguard.data.quickaffordance.KeyguardQuickAffordanceConfig
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public final java.lang.Object getPickerScreenState(kotlin.coroutines.Continuation r7) {
        /*
            r6 = this;
            boolean r0 = r7 instanceof com.android.systemui.keyguard.data.quickaffordance.CameraQuickAffordanceConfig$getPickerScreenState$1
            if (r0 == 0) goto L13
            r0 = r7
            com.android.systemui.keyguard.data.quickaffordance.CameraQuickAffordanceConfig$getPickerScreenState$1 r0 = (com.android.systemui.keyguard.data.quickaffordance.CameraQuickAffordanceConfig$getPickerScreenState$1) r0
            int r1 = r0.label
            r2 = -2147483648(0xffffffff80000000, float:-0.0)
            r3 = r1 & r2
            if (r3 == 0) goto L13
            int r1 = r1 - r2
            r0.label = r1
            goto L18
        L13:
            com.android.systemui.keyguard.data.quickaffordance.CameraQuickAffordanceConfig$getPickerScreenState$1 r0 = new com.android.systemui.keyguard.data.quickaffordance.CameraQuickAffordanceConfig$getPickerScreenState$1
            r0.<init>(r6, r7)
        L18:
            java.lang.Object r7 = r0.result
            kotlin.coroutines.intrinsics.CoroutineSingletons r1 = kotlin.coroutines.intrinsics.CoroutineSingletons.COROUTINE_SUSPENDED
            int r2 = r0.label
            r3 = 0
            r4 = 2
            r5 = 1
            if (r2 == 0) goto L3b
            if (r2 == r5) goto L33
            if (r2 != r4) goto L2b
            kotlin.ResultKt.throwOnFailure(r7)
            goto L70
        L2b:
            java.lang.IllegalStateException r6 = new java.lang.IllegalStateException
            java.lang.String r7 = "call to 'resume' before 'invoke' with coroutine"
            r6.<init>(r7)
            throw r6
        L33:
            java.lang.Object r6 = r0.L$0
            com.android.systemui.keyguard.data.quickaffordance.CameraQuickAffordanceConfig r6 = (com.android.systemui.keyguard.data.quickaffordance.CameraQuickAffordanceConfig) r6
            kotlin.ResultKt.throwOnFailure(r7)
            goto L5d
        L3b:
            kotlin.ResultKt.throwOnFailure(r7)
            r0.L$0 = r6
            r0.label = r5
            android.content.pm.PackageManager r7 = r6.packageManager
            java.lang.String r2 = "android.hardware.camera.any"
            boolean r7 = r7.hasSystemFeature(r2)
            if (r7 == 0) goto L58
            com.android.systemui.keyguard.data.quickaffordance.CameraQuickAffordanceConfig$isLaunchable$2 r7 = new com.android.systemui.keyguard.data.quickaffordance.CameraQuickAffordanceConfig$isLaunchable$2
            r7.<init>(r6, r3)
            kotlinx.coroutines.CoroutineDispatcher r2 = r6.backgroundDispatcher
            java.lang.Object r7 = kotlinx.coroutines.BuildersKt.withContext(r2, r7, r0)
            goto L5a
        L58:
            java.lang.Boolean r7 = java.lang.Boolean.FALSE
        L5a:
            if (r7 != r1) goto L5d
            return r1
        L5d:
            java.lang.Boolean r7 = (java.lang.Boolean) r7
            boolean r7 = r7.booleanValue()
            if (r7 == 0) goto L71
            r0.L$0 = r3
            r0.label = r4
            java.lang.Object r7 = super.getPickerScreenState(r0)
            if (r7 != r1) goto L70
            return r1
        L70:
            return r7
        L71:
            com.android.systemui.keyguard.data.quickaffordance.KeyguardQuickAffordanceConfig$PickerScreenState$UnavailableOnDevice r6 = com.android.systemui.keyguard.data.quickaffordance.KeyguardQuickAffordanceConfig.PickerScreenState.UnavailableOnDevice.INSTANCE
            return r6
        */
        throw new UnsupportedOperationException("Method not decompiled: com.android.systemui.keyguard.data.quickaffordance.CameraQuickAffordanceConfig.getPickerScreenState(kotlin.coroutines.Continuation):java.lang.Object");
    }

    @Override // com.android.systemui.keyguard.data.quickaffordance.KeyguardQuickAffordanceConfig
    public final KeyguardQuickAffordanceConfig.OnTriggeredResult onTriggered(Expandable expandable) {
        ((CameraGestureHelper) this.cameraGestureHelper.get()).launchCamera(3);
        return KeyguardQuickAffordanceConfig.OnTriggeredResult.Handled.INSTANCE;
    }

    @Override // com.android.systemui.keyguard.data.quickaffordance.KeyguardQuickAffordanceConfig
    public final String pickerName() {
        return this.context.getString(R.string.accessibility_camera_button);
    }
}
