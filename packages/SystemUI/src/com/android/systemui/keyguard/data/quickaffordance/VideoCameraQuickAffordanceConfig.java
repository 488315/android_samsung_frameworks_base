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
import kotlin.ResultKt;
import kotlin.coroutines.Continuation;
import kotlin.coroutines.intrinsics.CoroutineSingletons;
import kotlin.coroutines.jvm.internal.ContinuationImpl;
import kotlinx.coroutines.BuildersKt;
import kotlinx.coroutines.CoroutineDispatcher;
import kotlinx.coroutines.flow.Flow;
import kotlinx.coroutines.flow.SafeFlow;

/* loaded from: classes2.dex */
public final class VideoCameraQuickAffordanceConfig implements KeyguardQuickAffordanceConfig {
    public static final /* synthetic */ int $r8$clinit = 0;
    public final ActivityIntentHelper activityIntentHelper;
    public final CoroutineDispatcher backgroundDispatcher;
    public final CameraIntentsWrapper cameraIntents;
    public final Context context;
    public final DevicePolicyManager devicePolicyManager;
    public final UserTracker userTracker;

    /* renamed from: com.android.systemui.keyguard.data.quickaffordance.VideoCameraQuickAffordanceConfig$getPickerScreenState$1, reason: invalid class name */
    final class AnonymousClass1 extends ContinuationImpl {
        Object L$0;
        int label;
        /* synthetic */ Object result;

        public AnonymousClass1(Continuation continuation) {
            super(continuation);
        }

        @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
        public final Object invokeSuspend(Object obj) {
            this.result = obj;
            this.label |= Integer.MIN_VALUE;
            return VideoCameraQuickAffordanceConfig.this.getPickerScreenState(this);
        }
    }

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

    /* JADX WARN: Removed duplicated region for block: B:7:0x0013  */
    @Override // com.android.systemui.keyguard.data.quickaffordance.KeyguardQuickAffordanceConfig
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    public final Object getPickerScreenState(Continuation continuation) {
        AnonymousClass1 anonymousClass1;
        if (continuation instanceof AnonymousClass1) {
            anonymousClass1 = (AnonymousClass1) continuation;
            int i = anonymousClass1.label;
            if ((i & Integer.MIN_VALUE) != 0) {
                anonymousClass1.label = i - Integer.MIN_VALUE;
            } else {
                anonymousClass1 = new AnonymousClass1(continuation);
            }
        }
        Object objIsLaunchable$1 = anonymousClass1.result;
        CoroutineSingletons coroutineSingletons = CoroutineSingletons.COROUTINE_SUSPENDED;
        int i2 = anonymousClass1.label;
        if (i2 == 0) {
            ResultKt.throwOnFailure(objIsLaunchable$1);
            anonymousClass1.L$0 = this;
            anonymousClass1.label = 1;
            objIsLaunchable$1 = isLaunchable$1(anonymousClass1);
            if (objIsLaunchable$1 != coroutineSingletons) {
            }
        }
        if (i2 != 1) {
            if (i2 != 2) {
                throw new IllegalStateException("call to 'resume' before 'invoke' with coroutine");
            }
            ResultKt.throwOnFailure(objIsLaunchable$1);
            return objIsLaunchable$1;
        }
        this = (VideoCameraQuickAffordanceConfig) anonymousClass1.L$0;
        ResultKt.throwOnFailure(objIsLaunchable$1);
        if (!((Boolean) objIsLaunchable$1).booleanValue()) {
            return KeyguardQuickAffordanceConfig.PickerScreenState.UnavailableOnDevice.INSTANCE;
        }
        anonymousClass1.L$0 = null;
        anonymousClass1.label = 2;
        Object pickerScreenState = super.getPickerScreenState(anonymousClass1);
        return pickerScreenState == coroutineSingletons ? coroutineSingletons : pickerScreenState;
    }

    public final Object isLaunchable$1(ContinuationImpl continuationImpl) {
        if (this.activityIntentHelper.getTargetActivityInfo(getIntent(), true, ((UserTrackerImpl) this.userTracker).getUserId()) == null) {
            return Boolean.FALSE;
        }
        return BuildersKt.withContext(this.backgroundDispatcher, new VideoCameraQuickAffordanceConfig$isLaunchable$2(this, null), continuationImpl);
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
