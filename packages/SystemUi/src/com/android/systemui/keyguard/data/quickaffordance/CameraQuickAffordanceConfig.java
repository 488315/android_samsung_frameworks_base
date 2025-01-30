package com.android.systemui.keyguard.data.quickaffordance;

import android.app.admin.DevicePolicyManager;
import android.content.Context;
import android.content.pm.PackageManager;
import com.android.systemui.R;
import com.android.systemui.animation.Expandable;
import com.android.systemui.camera.CameraGestureHelper;
import com.android.systemui.common.shared.model.ContentDescription;
import com.android.systemui.common.shared.model.Icon;
import com.android.systemui.keyguard.data.quickaffordance.KeyguardQuickAffordanceConfig;
import com.android.systemui.settings.UserTracker;
import com.samsung.android.nexus.video.VideoPlayer;
import dagger.Lazy;
import kotlin.ResultKt;
import kotlin.coroutines.Continuation;
import kotlin.coroutines.intrinsics.CoroutineSingletons;
import kotlinx.coroutines.BuildersKt;
import kotlinx.coroutines.CoroutineDispatcher;
import kotlinx.coroutines.flow.Flow;
import kotlinx.coroutines.flow.FlowKt__BuildersKt$flowOf$$inlined$unsafeFlow$2;

/* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
/* loaded from: classes.dex */
public final class CameraQuickAffordanceConfig implements KeyguardQuickAffordanceConfig {
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
        return new FlowKt__BuildersKt$flowOf$$inlined$unsafeFlow$2(new KeyguardQuickAffordanceConfig.LockScreenState.Visible(new Icon.Resource(R.drawable.ic_camera, new ContentDescription.Resource(R.string.accessibility_camera_button)), null, 2, null));
    }

    @Override // com.android.systemui.keyguard.data.quickaffordance.KeyguardQuickAffordanceConfig
    public final int getPickerIconResourceId() {
        return R.drawable.ic_camera;
    }

    /* JADX WARN: Removed duplicated region for block: B:18:0x0066  */
    /* JADX WARN: Removed duplicated region for block: B:21:0x0073  */
    /* JADX WARN: Removed duplicated region for block: B:23:0x003b  */
    /* JADX WARN: Removed duplicated region for block: B:8:0x0023  */
    @Override // com.android.systemui.keyguard.data.quickaffordance.KeyguardQuickAffordanceConfig
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    public final Object getPickerScreenState(Continuation continuation) {
        CameraQuickAffordanceConfig$getPickerScreenState$1 cameraQuickAffordanceConfig$getPickerScreenState$1;
        Object obj;
        int i;
        if (continuation instanceof CameraQuickAffordanceConfig$getPickerScreenState$1) {
            cameraQuickAffordanceConfig$getPickerScreenState$1 = (CameraQuickAffordanceConfig$getPickerScreenState$1) continuation;
            int i2 = cameraQuickAffordanceConfig$getPickerScreenState$1.label;
            if ((i2 & VideoPlayer.MEDIA_ERROR_SYSTEM) != 0) {
                cameraQuickAffordanceConfig$getPickerScreenState$1.label = i2 - VideoPlayer.MEDIA_ERROR_SYSTEM;
                obj = cameraQuickAffordanceConfig$getPickerScreenState$1.result;
                CoroutineSingletons coroutineSingletons = CoroutineSingletons.COROUTINE_SUSPENDED;
                i = cameraQuickAffordanceConfig$getPickerScreenState$1.label;
                if (i != 0) {
                    ResultKt.throwOnFailure(obj);
                    cameraQuickAffordanceConfig$getPickerScreenState$1.L$0 = this;
                    cameraQuickAffordanceConfig$getPickerScreenState$1.label = 1;
                    obj = this.packageManager.hasSystemFeature("android.hardware.camera.any") ? BuildersKt.withContext(this.backgroundDispatcher, new CameraQuickAffordanceConfig$isLaunchable$2(this, null), cameraQuickAffordanceConfig$getPickerScreenState$1) : Boolean.FALSE;
                    if (obj == coroutineSingletons) {
                        return coroutineSingletons;
                    }
                } else {
                    if (i != 1) {
                        if (i == 2) {
                            ResultKt.throwOnFailure(obj);
                        }
                        throw new IllegalStateException("call to 'resume' before 'invoke' with coroutine");
                    }
                    ResultKt.throwOnFailure(obj);
                }
                if (((Boolean) obj).booleanValue()) {
                    return KeyguardQuickAffordanceConfig.PickerScreenState.UnavailableOnDevice.INSTANCE;
                }
                cameraQuickAffordanceConfig$getPickerScreenState$1.L$0 = null;
                cameraQuickAffordanceConfig$getPickerScreenState$1.label = 2;
                obj = new KeyguardQuickAffordanceConfig.PickerScreenState.Default(null, 1, null);
                return obj == coroutineSingletons ? coroutineSingletons : obj;
            }
        }
        cameraQuickAffordanceConfig$getPickerScreenState$1 = new CameraQuickAffordanceConfig$getPickerScreenState$1(this, continuation);
        obj = cameraQuickAffordanceConfig$getPickerScreenState$1.result;
        CoroutineSingletons coroutineSingletons2 = CoroutineSingletons.COROUTINE_SUSPENDED;
        i = cameraQuickAffordanceConfig$getPickerScreenState$1.label;
        if (i != 0) {
        }
        if (((Boolean) obj).booleanValue()) {
        }
    }

    @Override // com.android.systemui.keyguard.data.quickaffordance.KeyguardQuickAffordanceConfig
    public final KeyguardQuickAffordanceConfig.OnTriggeredResult onTriggered(Expandable expandable) {
        CameraGestureHelper cameraGestureHelper = (CameraGestureHelper) this.cameraGestureHelper.get();
        cameraGestureHelper.launchCamera(3, cameraGestureHelper.getStartCameraIntent());
        return KeyguardQuickAffordanceConfig.OnTriggeredResult.Handled.INSTANCE;
    }

    @Override // com.android.systemui.keyguard.data.quickaffordance.KeyguardQuickAffordanceConfig
    public final String pickerName() {
        return this.context.getString(R.string.accessibility_camera_button);
    }
}
