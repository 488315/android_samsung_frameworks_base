package com.android.systemui.biometrics.ui.viewmodel;

import com.android.systemui.biometrics.shared.model.FingerprintSensorType;
import kotlin.ResultKt;
import kotlin.Unit;
import kotlin.coroutines.Continuation;
import kotlin.coroutines.intrinsics.CoroutineSingletons;
import kotlin.coroutines.jvm.internal.SuspendLambda;
import kotlin.jvm.functions.Function6;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes.dex */
final class PromptIconViewModel$shouldAnimateIconView$1$3 extends SuspendLambda implements Function6 {
    /* synthetic */ Object L$0;
    /* synthetic */ Object L$1;
    /* synthetic */ boolean Z$0;
    /* synthetic */ boolean Z$1;
    /* synthetic */ boolean Z$2;
    int label;
    final /* synthetic */ PromptIconViewModel this$0;

    /* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
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
    public PromptIconViewModel$shouldAnimateIconView$1$3(PromptIconViewModel promptIconViewModel, Continuation continuation) {
        super(6, continuation);
        this.this$0 = promptIconViewModel;
    }

    @Override // kotlin.jvm.functions.Function6
    public final Object invoke(Object obj, Object obj2, Object obj3, Object obj4, Object obj5, Object obj6) {
        boolean booleanValue = ((Boolean) obj3).booleanValue();
        boolean booleanValue2 = ((Boolean) obj4).booleanValue();
        boolean booleanValue3 = ((Boolean) obj5).booleanValue();
        PromptIconViewModel$shouldAnimateIconView$1$3 promptIconViewModel$shouldAnimateIconView$1$3 = new PromptIconViewModel$shouldAnimateIconView$1$3(this.this$0, (Continuation) obj6);
        promptIconViewModel$shouldAnimateIconView$1$3.L$0 = (FingerprintSensorType) obj;
        promptIconViewModel$shouldAnimateIconView$1$3.L$1 = (PromptAuthState) obj2;
        promptIconViewModel$shouldAnimateIconView$1$3.Z$0 = booleanValue;
        promptIconViewModel$shouldAnimateIconView$1$3.Z$1 = booleanValue2;
        promptIconViewModel$shouldAnimateIconView$1$3.Z$2 = booleanValue3;
        return promptIconViewModel$shouldAnimateIconView$1$3.invokeSuspend(Unit.INSTANCE);
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
        boolean z3 = this.Z$2;
        boolean z4 = true;
        if (WhenMappings.$EnumSwitchMapping$0[fingerprintSensorType.ordinal()] != 1) {
            PromptIconViewModel promptIconViewModel = this.this$0;
            boolean z5 = promptAuthState.isAuthenticated;
            promptIconViewModel.getClass();
            if ((!z || !((Boolean) promptIconViewModel._previousIconWasError.getValue()).booleanValue()) && !z2 && !z5 && !z3) {
                z4 = false;
            }
        }
        return Boolean.valueOf(z4);
    }
}
