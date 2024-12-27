package com.android.systemui.biometrics.ui.viewmodel;

import com.android.systemui.biometrics.shared.model.FingerprintSensorType;
import kotlin.ResultKt;
import kotlin.Unit;
import kotlin.coroutines.Continuation;
import kotlin.coroutines.intrinsics.CoroutineSingletons;
import kotlin.coroutines.jvm.internal.SuspendLambda;
import kotlin.jvm.functions.Function5;

final class PromptIconViewModel$shouldAnimateIconOverlay$1$1 extends SuspendLambda implements Function5 {
    /* synthetic */ Object L$0;
    /* synthetic */ Object L$1;
    /* synthetic */ boolean Z$0;
    /* synthetic */ boolean Z$1;
    int label;
    final /* synthetic */ PromptIconViewModel this$0;

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

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    public PromptIconViewModel$shouldAnimateIconOverlay$1$1(PromptIconViewModel promptIconViewModel, Continuation continuation) {
        super(5, continuation);
        this.this$0 = promptIconViewModel;
    }

    @Override // kotlin.jvm.functions.Function5
    public final Object invoke(Object obj, Object obj2, Object obj3, Object obj4, Object obj5) {
        boolean booleanValue = ((Boolean) obj3).booleanValue();
        boolean booleanValue2 = ((Boolean) obj4).booleanValue();
        PromptIconViewModel$shouldAnimateIconOverlay$1$1 promptIconViewModel$shouldAnimateIconOverlay$1$1 = new PromptIconViewModel$shouldAnimateIconOverlay$1$1(this.this$0, (Continuation) obj5);
        promptIconViewModel$shouldAnimateIconOverlay$1$1.L$0 = (FingerprintSensorType) obj;
        promptIconViewModel$shouldAnimateIconOverlay$1$1.L$1 = (PromptAuthState) obj2;
        promptIconViewModel$shouldAnimateIconOverlay$1$1.Z$0 = booleanValue;
        promptIconViewModel$shouldAnimateIconOverlay$1$1.Z$1 = booleanValue2;
        return promptIconViewModel$shouldAnimateIconOverlay$1$1.invokeSuspend(Unit.INSTANCE);
    }

    @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
    public final Object invokeSuspend(Object obj) {
        CoroutineSingletons coroutineSingletons = CoroutineSingletons.COROUTINE_SUSPENDED;
        if (this.label != 0) {
            throw new IllegalStateException("call to 'resume' before 'invoke' with coroutine");
        }
        ResultKt.throwOnFailure(obj);
        FingerprintSensorType fingerprintSensorType = (FingerprintSensorType) this.L$0;
        PromptAuthState promptAuthState = (PromptAuthState) this.L$1;
        boolean z = this.Z$0;
        boolean z2 = this.Z$1;
        boolean z3 = false;
        if (WhenMappings.$EnumSwitchMapping$0[fingerprintSensorType.ordinal()] == 1) {
            PromptIconViewModel promptIconViewModel = this.this$0;
            boolean z4 = promptAuthState.isAuthenticated;
            promptIconViewModel.getClass();
            if ((z && ((Boolean) promptIconViewModel._previousIconOverlayWasError.getValue()).booleanValue()) || z4 || z2) {
                z3 = true;
            }
        }
        return Boolean.valueOf(z3);
    }
}
