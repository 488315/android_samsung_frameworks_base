package com.android.systemui.biometrics.ui.viewmodel;

import com.android.systemui.biometrics.shared.model.DisplayRotation;
import com.android.systemui.biometrics.shared.model.FingerprintSensorType;
import kotlin.ResultKt;
import kotlin.Unit;
import kotlin.coroutines.Continuation;
import kotlin.coroutines.intrinsics.CoroutineSingletons;
import kotlin.coroutines.jvm.internal.SuspendLambda;
import kotlin.jvm.functions.Function3;

/* compiled from: qb/89523975 427a50d40ec74a85ca352b86f77450b1c52ece5389e11158752b0d641a3a5098 */
final class PromptIconViewModel$shouldFlipIconView$1$1 extends SuspendLambda implements Function3 {
    /* synthetic */ Object L$0;
    /* synthetic */ Object L$1;
    int label;

    /* compiled from: qb/89523975 427a50d40ec74a85ca352b86f77450b1c52ece5389e11158752b0d641a3a5098 */
    public abstract /* synthetic */ class WhenMappings {
        public static final /* synthetic */ int[] $EnumSwitchMapping$0;

        static {
            int[] iArr = new int[FingerprintSensorType.values().length];
            try {
                iArr[FingerprintSensorType.POWER_BUTTON.ordinal()] = 1;
            } catch (NoSuchFieldError unused) {
            }
            $EnumSwitchMapping$0 = iArr;
        }
    }

    public PromptIconViewModel$shouldFlipIconView$1$1(Continuation continuation) {
        super(3, continuation);
    }

    @Override // kotlin.jvm.functions.Function3
    public final Object invoke(Object obj, Object obj2, Object obj3) {
        PromptIconViewModel$shouldFlipIconView$1$1 promptIconViewModel$shouldFlipIconView$1$1 = new PromptIconViewModel$shouldFlipIconView$1$1((Continuation) obj3);
        promptIconViewModel$shouldFlipIconView$1$1.L$0 = (FingerprintSensorType) obj;
        promptIconViewModel$shouldFlipIconView$1$1.L$1 = (DisplayRotation) obj2;
        return promptIconViewModel$shouldFlipIconView$1$1.invokeSuspend(Unit.INSTANCE);
    }

    @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
    public final Object invokeSuspend(Object obj) {
        CoroutineSingletons coroutineSingletons = CoroutineSingletons.COROUTINE_SUSPENDED;
        if (this.label != 0) {
            throw new IllegalStateException("call to 'resume' before 'invoke' with coroutine");
        }
        ResultKt.throwOnFailure(obj);
        FingerprintSensorType fingerprintSensorType = (FingerprintSensorType) this.L$0;
        DisplayRotation displayRotation = (DisplayRotation) this.L$1;
        boolean z = false;
        if (WhenMappings.$EnumSwitchMapping$0[fingerprintSensorType.ordinal()] == 1 && displayRotation == DisplayRotation.ROTATION_180) {
            z = true;
        }
        return Boolean.valueOf(z);
    }
}
