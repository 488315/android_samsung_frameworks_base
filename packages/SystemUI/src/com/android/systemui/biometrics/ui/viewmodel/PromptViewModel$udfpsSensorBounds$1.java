package com.android.systemui.biometrics.ui.viewmodel;

import android.graphics.Rect;
import android.util.RotationUtils;
import com.android.systemui.biometrics.shared.model.DisplayRotation;
import com.android.systemui.biometrics.shared.model.UdfpsOverlayParams;
import kotlin.ResultKt;
import kotlin.Unit;
import kotlin.coroutines.Continuation;
import kotlin.coroutines.intrinsics.CoroutineSingletons;
import kotlin.coroutines.jvm.internal.SuspendLambda;
import kotlin.jvm.functions.Function3;

/* compiled from: qb/89523975 427a50d40ec74a85ca352b86f77450b1c52ece5389e11158752b0d641a3a5098 */
final class PromptViewModel$udfpsSensorBounds$1 extends SuspendLambda implements Function3 {
    /* synthetic */ Object L$0;
    /* synthetic */ Object L$1;
    int label;

    public PromptViewModel$udfpsSensorBounds$1(Continuation continuation) {
        super(3, continuation);
    }

    @Override // kotlin.jvm.functions.Function3
    public final Object invoke(Object obj, Object obj2, Object obj3) {
        PromptViewModel$udfpsSensorBounds$1 promptViewModel$udfpsSensorBounds$1 = new PromptViewModel$udfpsSensorBounds$1((Continuation) obj3);
        promptViewModel$udfpsSensorBounds$1.L$0 = (UdfpsOverlayParams) obj;
        promptViewModel$udfpsSensorBounds$1.L$1 = (DisplayRotation) obj2;
        return promptViewModel$udfpsSensorBounds$1.invokeSuspend(Unit.INSTANCE);
    }

    @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
    public final Object invokeSuspend(Object obj) {
        CoroutineSingletons coroutineSingletons = CoroutineSingletons.COROUTINE_SUSPENDED;
        if (this.label != 0) {
            throw new IllegalStateException("call to 'resume' before 'invoke' with coroutine");
        }
        ResultKt.throwOnFailure(obj);
        UdfpsOverlayParams udfpsOverlayParams = (UdfpsOverlayParams) this.L$0;
        DisplayRotation displayRotation = (DisplayRotation) this.L$1;
        Rect rect = new Rect(udfpsOverlayParams.sensorBounds);
        RotationUtils.rotateBounds(rect, udfpsOverlayParams.naturalDisplayWidth, udfpsOverlayParams.naturalDisplayHeight, displayRotation.ordinal());
        return new Rect(rect.left, rect.top, udfpsOverlayParams.logicalDisplayWidth - rect.right, udfpsOverlayParams.logicalDisplayHeight - rect.bottom);
    }
}
