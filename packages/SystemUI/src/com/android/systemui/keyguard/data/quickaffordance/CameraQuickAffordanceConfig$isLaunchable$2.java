package com.android.systemui.keyguard.data.quickaffordance;

import com.android.systemui.settings.UserTrackerImpl;
import kotlin.ResultKt;
import kotlin.Unit;
import kotlin.coroutines.Continuation;
import kotlin.coroutines.intrinsics.CoroutineSingletons;
import kotlin.coroutines.jvm.internal.SuspendLambda;
import kotlin.jvm.functions.Function2;
import kotlinx.coroutines.CoroutineScope;

/* compiled from: qb/89523975 427a50d40ec74a85ca352b86f77450b1c52ece5389e11158752b0d641a3a5098 */
/* loaded from: classes2.dex */
final class CameraQuickAffordanceConfig$isLaunchable$2 extends SuspendLambda implements Function2 {
    int label;
    final /* synthetic */ CameraQuickAffordanceConfig this$0;

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    public CameraQuickAffordanceConfig$isLaunchable$2(CameraQuickAffordanceConfig cameraQuickAffordanceConfig, Continuation continuation) {
        super(2, continuation);
        this.this$0 = cameraQuickAffordanceConfig;
    }

    @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
    public final Continuation create(Object obj, Continuation continuation) {
        return new CameraQuickAffordanceConfig$isLaunchable$2(this.this$0, continuation);
    }

    @Override // kotlin.jvm.functions.Function2
    public final Object invoke(Object obj, Object obj2) {
        return ((CameraQuickAffordanceConfig$isLaunchable$2) create((CoroutineScope) obj, (Continuation) obj2)).invokeSuspend(Unit.INSTANCE);
    }

    @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
    public final Object invokeSuspend(Object obj) {
        boolean z;
        CoroutineSingletons coroutineSingletons = CoroutineSingletons.COROUTINE_SUSPENDED;
        if (this.label != 0) {
            throw new IllegalStateException("call to 'resume' before 'invoke' with coroutine");
        }
        ResultKt.throwOnFailure(obj);
        CameraQuickAffordanceConfig cameraQuickAffordanceConfig = this.this$0;
        if (!cameraQuickAffordanceConfig.devicePolicyManager.getCameraDisabled(null, ((UserTrackerImpl) cameraQuickAffordanceConfig.userTracker).getUserId())) {
            CameraQuickAffordanceConfig cameraQuickAffordanceConfig2 = this.this$0;
            if ((cameraQuickAffordanceConfig2.devicePolicyManager.getKeyguardDisabledFeatures(null, ((UserTrackerImpl) cameraQuickAffordanceConfig2.userTracker).getUserId()) & 2) == 0) {
                z = true;
                return Boolean.valueOf(z);
            }
        }
        z = false;
        return Boolean.valueOf(z);
    }
}
