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
import com.samsung.android.nexus.video.VideoPlayer;
import kotlin.Lazy;
import kotlin.LazyKt__LazyJVMKt;
import kotlin.ResultKt;
import kotlin.coroutines.Continuation;
import kotlin.coroutines.intrinsics.CoroutineSingletons;
import kotlin.jvm.functions.Function0;
import kotlinx.coroutines.BuildersKt;
import kotlinx.coroutines.CoroutineDispatcher;
import kotlinx.coroutines.flow.Flow;
import kotlinx.coroutines.flow.SafeFlow;

/* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
/* loaded from: classes.dex */
public final class VideoCameraQuickAffordanceConfig implements KeyguardQuickAffordanceConfig {
    public final ActivityIntentHelper activityIntentHelper;
    public final CoroutineDispatcher backgroundDispatcher;
    public final CameraIntentsWrapper cameraIntents;
    public final Context context;
    public final DevicePolicyManager devicePolicyManager;
    public final Lazy intent$delegate = LazyKt__LazyJVMKt.lazy(new Function0() { // from class: com.android.systemui.keyguard.data.quickaffordance.VideoCameraQuickAffordanceConfig$intent$2
        {
            super(0);
        }

        @Override // kotlin.jvm.functions.Function0
        public final Object invoke() {
            VideoCameraQuickAffordanceConfig.this.cameraIntents.getClass();
            CameraIntents.Companion.getClass();
            Intent intent = new Intent(CameraIntents.VIDEO_CAMERA_INTENT_ACTION);
            intent.putExtra("com.android.systemui.camera_launch_source", 3);
            return intent;
        }
    });
    public final UserTracker userTracker;

    public VideoCameraQuickAffordanceConfig(Context context, CameraIntentsWrapper cameraIntentsWrapper, ActivityIntentHelper activityIntentHelper, UserTracker userTracker, DevicePolicyManager devicePolicyManager, CoroutineDispatcher coroutineDispatcher) {
        this.context = context;
        this.cameraIntents = cameraIntentsWrapper;
        this.activityIntentHelper = activityIntentHelper;
        this.userTracker = userTracker;
        this.devicePolicyManager = devicePolicyManager;
        this.backgroundDispatcher = coroutineDispatcher;
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
    /* JADX WARN: Removed duplicated region for block: B:21:0x005e  */
    /* JADX WARN: Removed duplicated region for block: B:23:0x003a  */
    /* JADX WARN: Removed duplicated region for block: B:8:0x0022  */
    @Override // com.android.systemui.keyguard.data.quickaffordance.KeyguardQuickAffordanceConfig
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    public final Object getPickerScreenState(Continuation continuation) {
        VideoCameraQuickAffordanceConfig$getPickerScreenState$1 videoCameraQuickAffordanceConfig$getPickerScreenState$1;
        Object obj;
        int i;
        if (continuation instanceof VideoCameraQuickAffordanceConfig$getPickerScreenState$1) {
            videoCameraQuickAffordanceConfig$getPickerScreenState$1 = (VideoCameraQuickAffordanceConfig$getPickerScreenState$1) continuation;
            int i2 = videoCameraQuickAffordanceConfig$getPickerScreenState$1.label;
            if ((i2 & VideoPlayer.MEDIA_ERROR_SYSTEM) != 0) {
                videoCameraQuickAffordanceConfig$getPickerScreenState$1.label = i2 - VideoPlayer.MEDIA_ERROR_SYSTEM;
                obj = videoCameraQuickAffordanceConfig$getPickerScreenState$1.result;
                Object obj2 = CoroutineSingletons.COROUTINE_SUSPENDED;
                i = videoCameraQuickAffordanceConfig$getPickerScreenState$1.label;
                if (i != 0) {
                    ResultKt.throwOnFailure(obj);
                    videoCameraQuickAffordanceConfig$getPickerScreenState$1.L$0 = this;
                    videoCameraQuickAffordanceConfig$getPickerScreenState$1.label = 1;
                    obj = isLaunchable(videoCameraQuickAffordanceConfig$getPickerScreenState$1);
                    if (obj == obj2) {
                        return obj2;
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
                videoCameraQuickAffordanceConfig$getPickerScreenState$1.L$0 = null;
                videoCameraQuickAffordanceConfig$getPickerScreenState$1.label = 2;
                obj = new KeyguardQuickAffordanceConfig.PickerScreenState.Default(null, 1, null);
                return obj == obj2 ? obj2 : obj;
            }
        }
        videoCameraQuickAffordanceConfig$getPickerScreenState$1 = new VideoCameraQuickAffordanceConfig$getPickerScreenState$1(this, continuation);
        obj = videoCameraQuickAffordanceConfig$getPickerScreenState$1.result;
        Object obj22 = CoroutineSingletons.COROUTINE_SUSPENDED;
        i = videoCameraQuickAffordanceConfig$getPickerScreenState$1.label;
        if (i != 0) {
        }
        if (((Boolean) obj).booleanValue()) {
        }
    }

    public final Object isLaunchable(Continuation continuation) {
        if (this.activityIntentHelper.getTargetActivityInfo((Intent) this.intent$delegate.getValue(), true, ((UserTrackerImpl) this.userTracker).getUserId()) == null) {
            return Boolean.FALSE;
        }
        return BuildersKt.withContext(this.backgroundDispatcher, new VideoCameraQuickAffordanceConfig$isLaunchable$2(this, null), continuation);
    }

    @Override // com.android.systemui.keyguard.data.quickaffordance.KeyguardQuickAffordanceConfig
    public final KeyguardQuickAffordanceConfig.OnTriggeredResult onTriggered(Expandable expandable) {
        return new KeyguardQuickAffordanceConfig.OnTriggeredResult.StartActivity((Intent) this.intent$delegate.getValue(), false);
    }

    @Override // com.android.systemui.keyguard.data.quickaffordance.KeyguardQuickAffordanceConfig
    public final String pickerName() {
        return this.context.getString(R.string.video_camera);
    }
}
