package com.android.systemui.keyguard.data.quickaffordance;

import com.android.systemui.R;
import com.android.systemui.common.shared.model.ContentDescription;
import com.android.systemui.common.shared.model.Icon;
import com.android.systemui.keyguard.data.quickaffordance.KeyguardQuickAffordanceConfig;
import kotlin.ResultKt;
import kotlin.Unit;
import kotlin.coroutines.Continuation;
import kotlin.coroutines.intrinsics.CoroutineSingletons;
import kotlin.coroutines.jvm.internal.SuspendLambda;
import kotlin.jvm.functions.Function2;
import kotlinx.coroutines.BuildersKt;
import kotlinx.coroutines.flow.FlowCollector;

final class CameraQuickAffordanceConfig$lockScreenState$1 extends SuspendLambda implements Function2 {
    private /* synthetic */ Object L$0;
    int label;
    final /* synthetic */ CameraQuickAffordanceConfig this$0;

    public CameraQuickAffordanceConfig$lockScreenState$1(CameraQuickAffordanceConfig cameraQuickAffordanceConfig, Continuation continuation) {
        super(2, continuation);
        this.this$0 = cameraQuickAffordanceConfig;
    }

    @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
    public final Continuation create(Object obj, Continuation continuation) {
        CameraQuickAffordanceConfig$lockScreenState$1 cameraQuickAffordanceConfig$lockScreenState$1 = new CameraQuickAffordanceConfig$lockScreenState$1(this.this$0, continuation);
        cameraQuickAffordanceConfig$lockScreenState$1.L$0 = obj;
        return cameraQuickAffordanceConfig$lockScreenState$1;
    }

    @Override // kotlin.jvm.functions.Function2
    public final Object invoke(Object obj, Object obj2) {
        return ((CameraQuickAffordanceConfig$lockScreenState$1) create((FlowCollector) obj, (Continuation) obj2)).invokeSuspend(Unit.INSTANCE);
    }

    @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
    public final Object invokeSuspend(Object obj) {
        FlowCollector flowCollector;
        CoroutineSingletons coroutineSingletons = CoroutineSingletons.COROUTINE_SUSPENDED;
        int i = this.label;
        if (i == 0) {
            ResultKt.throwOnFailure(obj);
            flowCollector = (FlowCollector) this.L$0;
            CameraQuickAffordanceConfig cameraQuickAffordanceConfig = this.this$0;
            this.L$0 = flowCollector;
            this.label = 1;
            int i2 = CameraQuickAffordanceConfig.$r8$clinit;
            obj = cameraQuickAffordanceConfig.packageManager.hasSystemFeature("android.hardware.camera.any") ? BuildersKt.withContext(cameraQuickAffordanceConfig.backgroundDispatcher, new CameraQuickAffordanceConfig$isLaunchable$2(cameraQuickAffordanceConfig, null), this) : Boolean.FALSE;
            if (obj == coroutineSingletons) {
                return coroutineSingletons;
            }
        } else {
            if (i != 1) {
                if (i != 2) {
                    throw new IllegalStateException("call to 'resume' before 'invoke' with coroutine");
                }
                ResultKt.throwOnFailure(obj);
                return Unit.INSTANCE;
            }
            flowCollector = (FlowCollector) this.L$0;
            ResultKt.throwOnFailure(obj);
        }
        Object visible = ((Boolean) obj).booleanValue() ? new KeyguardQuickAffordanceConfig.LockScreenState.Visible(new Icon.Resource(R.drawable.ic_camera, new ContentDescription.Resource(R.string.accessibility_camera_button)), null, 2, null) : KeyguardQuickAffordanceConfig.LockScreenState.Hidden.INSTANCE;
        this.L$0 = null;
        this.label = 2;
        if (flowCollector.emit(visible, this) == coroutineSingletons) {
            return coroutineSingletons;
        }
        return Unit.INSTANCE;
    }
}
