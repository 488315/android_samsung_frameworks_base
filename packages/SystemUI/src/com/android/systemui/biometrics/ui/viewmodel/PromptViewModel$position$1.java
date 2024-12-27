package com.android.systemui.biometrics.ui.viewmodel;

import com.android.systemui.biometrics.shared.model.BiometricModalities;
import com.android.systemui.biometrics.shared.model.DisplayRotation;
import com.android.systemui.biometrics.shared.model.PromptKind;
import kotlin.ResultKt;
import kotlin.Unit;
import kotlin.coroutines.Continuation;
import kotlin.coroutines.intrinsics.CoroutineSingletons;
import kotlin.coroutines.jvm.internal.SuspendLambda;
import kotlin.jvm.functions.Function6;

final class PromptViewModel$position$1 extends SuspendLambda implements Function6 {
    /* synthetic */ Object L$0;
    /* synthetic */ Object L$1;
    /* synthetic */ Object L$2;
    /* synthetic */ boolean Z$0;
    /* synthetic */ boolean Z$1;
    int label;

    public PromptViewModel$position$1(Continuation continuation) {
        super(6, continuation);
    }

    @Override // kotlin.jvm.functions.Function6
    public final Object invoke(Object obj, Object obj2, Object obj3, Object obj4, Object obj5, Object obj6) {
        boolean booleanValue = ((Boolean) obj).booleanValue();
        boolean booleanValue2 = ((Boolean) obj3).booleanValue();
        PromptViewModel$position$1 promptViewModel$position$1 = new PromptViewModel$position$1((Continuation) obj6);
        promptViewModel$position$1.Z$0 = booleanValue;
        promptViewModel$position$1.L$0 = (PromptKind) obj2;
        promptViewModel$position$1.Z$1 = booleanValue2;
        promptViewModel$position$1.L$1 = (DisplayRotation) obj4;
        promptViewModel$position$1.L$2 = (BiometricModalities) obj5;
        return promptViewModel$position$1.invokeSuspend(Unit.INSTANCE);
    }

    @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
    public final Object invokeSuspend(Object obj) {
        CoroutineSingletons coroutineSingletons = CoroutineSingletons.COROUTINE_SUSPENDED;
        if (this.label != 0) {
            throw new IllegalStateException("call to 'resume' before 'invoke' with coroutine");
        }
        ResultKt.throwOnFailure(obj);
        boolean z = this.Z$0;
        PromptKind promptKind = (PromptKind) this.L$0;
        boolean z2 = this.Z$1;
        DisplayRotation displayRotation = (DisplayRotation) this.L$1;
        return (z || z2 || promptKind.isOnePaneNoSensorLandscapeBiometric()) ? PromptPosition.Bottom : displayRotation == DisplayRotation.ROTATION_90 ? PromptPosition.Right : displayRotation == DisplayRotation.ROTATION_270 ? PromptPosition.Left : (displayRotation == DisplayRotation.ROTATION_180 && ((BiometricModalities) this.L$2).getHasUdfps()) ? PromptPosition.Top : PromptPosition.Bottom;
    }
}
