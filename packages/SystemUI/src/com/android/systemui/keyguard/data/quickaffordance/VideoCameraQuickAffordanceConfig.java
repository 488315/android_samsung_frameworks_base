package com.android.systemui.keyguard.data.quickaffordance;

import android.app.admin.DevicePolicyManager;
import android.content.Context;
import android.content.Intent;
import com.android.systemui.ActivityIntentHelper;
import com.android.systemui.R;
import com.android.systemui.animation.Expandable;
import com.android.systemui.camera.CameraIntents;
import com.android.systemui.camera.CameraIntentsWrapper;
import com.android.systemui.keyguard.data.quickaffordance.KeyguardQuickAffordanceConfig;
import com.android.systemui.settings.UserTracker;
import com.android.systemui.settings.UserTrackerImpl;
import kotlin.coroutines.Continuation;
import kotlinx.coroutines.BuildersKt;
import kotlinx.coroutines.CoroutineDispatcher;
import kotlinx.coroutines.flow.Flow;
import kotlinx.coroutines.flow.SafeFlow;

/* compiled from: qb/89523975 427a50d40ec74a85ca352b86f77450b1c52ece5389e11158752b0d641a3a5098 */
public final class VideoCameraQuickAffordanceConfig implements KeyguardQuickAffordanceConfig {
    public static final /* synthetic */ int $r8$clinit = 0;
    public final ActivityIntentHelper activityIntentHelper;
    public final CoroutineDispatcher backgroundDispatcher;
    public final CameraIntentsWrapper cameraIntents;
    public final Context context;
    public final DevicePolicyManager devicePolicyManager;
    public final UserTracker userTracker;

    public VideoCameraQuickAffordanceConfig(Context context, CameraIntentsWrapper cameraIntentsWrapper, ActivityIntentHelper activityIntentHelper, UserTracker userTracker, DevicePolicyManager devicePolicyManager, CoroutineDispatcher coroutineDispatcher) {
        this.context = context;
        this.cameraIntents = cameraIntentsWrapper;
        this.activityIntentHelper = activityIntentHelper;
        this.userTracker = userTracker;
        this.devicePolicyManager = devicePolicyManager;
        this.backgroundDispatcher = coroutineDispatcher;
    }

    public final Intent getIntent() {
        ((UserTrackerImpl) this.userTracker).getUserId();
        this.cameraIntents.getClass();
        CameraIntents.Companion.getClass();
        Intent intent = new Intent(CameraIntents.VIDEO_CAMERA_INTENT_ACTION);
        intent.putExtra("com.android.systemui.camera_launch_source", 3);
        return intent;
    }

    @Override // com.android.systemui.keyguard.data.quickaffordance.KeyguardQuickAffordanceConfig
    public final String getKey() {
        return "video_camera";
    }

    @Override // com.android.systemui.keyguard.data.quickaffordance.KeyguardQuickAffordanceConfig
    public final Flow getLockScreenState() {
        return new SafeFlow(new VideoCameraQuickAffordanceConfig$lockScreenState$1(this, null));
    }

    @Override // com.android.systemui.keyguard.data.quickaffordance.KeyguardQuickAffordanceConfig
    public final int getPickerIconResourceId() {
        return R.drawable.ic_videocam;
    }

    /* JADX WARN: Removed duplicated region for block: B:18:0x0050  */
    /* JADX WARN: Removed duplicated region for block: B:21:0x005d  */
    /* JADX WARN: Removed duplicated region for block: B:23:0x003a  */
    /* JADX WARN: Removed duplicated region for block: B:8:0x0022  */
    @Override // com.android.systemui.keyguard.data.quickaffordance.KeyguardQuickAffordanceConfig
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public final java.lang.Object getPickerScreenState(kotlin.coroutines.Continuation r6) {
        /*
            r5 = this;
            boolean r0 = r6 instanceof com.android.systemui.keyguard.data.quickaffordance.VideoCameraQuickAffordanceConfig$getPickerScreenState$1
            if (r0 == 0) goto L13
            r0 = r6
            com.android.systemui.keyguard.data.quickaffordance.VideoCameraQuickAffordanceConfig$getPickerScreenState$1 r0 = (com.android.systemui.keyguard.data.quickaffordance.VideoCameraQuickAffordanceConfig$getPickerScreenState$1) r0
            int r1 = r0.label
            r2 = -2147483648(0xffffffff80000000, float:-0.0)
            r3 = r1 & r2
            if (r3 == 0) goto L13
            int r1 = r1 - r2
            r0.label = r1
            goto L18
        L13:
            com.android.systemui.keyguard.data.quickaffordance.VideoCameraQuickAffordanceConfig$getPickerScreenState$1 r0 = new com.android.systemui.keyguard.data.quickaffordance.VideoCameraQuickAffordanceConfig$getPickerScreenState$1
            r0.<init>(r5, r6)
        L18:
            java.lang.Object r6 = r0.result
            kotlin.coroutines.intrinsics.CoroutineSingletons r1 = kotlin.coroutines.intrinsics.CoroutineSingletons.COROUTINE_SUSPENDED
            int r2 = r0.label
            r3 = 2
            r4 = 1
            if (r2 == 0) goto L3a
            if (r2 == r4) goto L32
            if (r2 != r3) goto L2a
            kotlin.ResultKt.throwOnFailure(r6)
            goto L5c
        L2a:
            java.lang.IllegalStateException r5 = new java.lang.IllegalStateException
            java.lang.String r6 = "call to 'resume' before 'invoke' with coroutine"
            r5.<init>(r6)
            throw r5
        L32:
            java.lang.Object r5 = r0.L$0
            com.android.systemui.keyguard.data.quickaffordance.VideoCameraQuickAffordanceConfig r5 = (com.android.systemui.keyguard.data.quickaffordance.VideoCameraQuickAffordanceConfig) r5
            kotlin.ResultKt.throwOnFailure(r6)
            goto L48
        L3a:
            kotlin.ResultKt.throwOnFailure(r6)
            r0.L$0 = r5
            r0.label = r4
            java.lang.Object r6 = r5.isLaunchable$1(r0)
            if (r6 != r1) goto L48
            return r1
        L48:
            java.lang.Boolean r6 = (java.lang.Boolean) r6
            boolean r6 = r6.booleanValue()
            if (r6 == 0) goto L5d
            r6 = 0
            r0.L$0 = r6
            r0.label = r3
            java.lang.Object r6 = super.getPickerScreenState(r0)
            if (r6 != r1) goto L5c
            return r1
        L5c:
            return r6
        L5d:
            com.android.systemui.keyguard.data.quickaffordance.KeyguardQuickAffordanceConfig$PickerScreenState$UnavailableOnDevice r5 = com.android.systemui.keyguard.data.quickaffordance.KeyguardQuickAffordanceConfig.PickerScreenState.UnavailableOnDevice.INSTANCE
            return r5
        */
        throw new UnsupportedOperationException("Method not decompiled: com.android.systemui.keyguard.data.quickaffordance.VideoCameraQuickAffordanceConfig.getPickerScreenState(kotlin.coroutines.Continuation):java.lang.Object");
    }

    public final Object isLaunchable$1(Continuation continuation) {
        if (this.activityIntentHelper.getTargetActivityInfo(getIntent(), true, ((UserTrackerImpl) this.userTracker).getUserId()) == null) {
            return Boolean.FALSE;
        }
        return BuildersKt.withContext(this.backgroundDispatcher, new VideoCameraQuickAffordanceConfig$isLaunchable$2(this, null), continuation);
    }

    @Override // com.android.systemui.keyguard.data.quickaffordance.KeyguardQuickAffordanceConfig
    public final KeyguardQuickAffordanceConfig.OnTriggeredResult onTriggered(Expandable expandable) {
        return new KeyguardQuickAffordanceConfig.OnTriggeredResult.StartActivity(getIntent(), false);
    }

    @Override // com.android.systemui.keyguard.data.quickaffordance.KeyguardQuickAffordanceConfig
    public final String pickerName() {
        return this.context.getString(R.string.video_camera);
    }
}
