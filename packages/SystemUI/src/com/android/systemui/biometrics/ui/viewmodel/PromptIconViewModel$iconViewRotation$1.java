package com.android.systemui.biometrics.ui.viewmodel;

import com.android.systemui.biometrics.shared.model.DisplayRotation;
import kotlin.NoWhenBranchMatchedException;
import kotlin.ResultKt;
import kotlin.Unit;
import kotlin.coroutines.Continuation;
import kotlin.coroutines.intrinsics.CoroutineSingletons;
import kotlin.coroutines.jvm.internal.SuspendLambda;
import kotlin.jvm.functions.Function3;

/* loaded from: classes.dex */
final class PromptIconViewModel$iconViewRotation$1 extends SuspendLambda implements Function3 {
    /* synthetic */ int I$0;
    /* synthetic */ Object L$0;
    int label;
    final /* synthetic */ PromptIconViewModel this$0;

    public abstract /* synthetic */ class WhenMappings {
        public static final /* synthetic */ int[] $EnumSwitchMapping$0;

        static {
            int[] iArr = new int[DisplayRotation.values().length];
            try {
                iArr[DisplayRotation.ROTATION_0.ordinal()] = 1;
            } catch (NoSuchFieldError unused) {
            }
            try {
                iArr[DisplayRotation.ROTATION_90.ordinal()] = 2;
            } catch (NoSuchFieldError unused2) {
            }
            try {
                iArr[DisplayRotation.ROTATION_180.ordinal()] = 3;
            } catch (NoSuchFieldError unused3) {
            }
            try {
                iArr[DisplayRotation.ROTATION_270.ordinal()] = 4;
            } catch (NoSuchFieldError unused4) {
            }
            $EnumSwitchMapping$0 = iArr;
        }
    }

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    public PromptIconViewModel$iconViewRotation$1(PromptIconViewModel promptIconViewModel, Continuation continuation) {
        super(3, continuation);
        this.this$0 = promptIconViewModel;
    }

    @Override // kotlin.jvm.functions.Function3
    public final Object invoke(Object obj, Object obj2, Object obj3) {
        int iIntValue = ((Number) obj).intValue();
        PromptIconViewModel$iconViewRotation$1 promptIconViewModel$iconViewRotation$1 = new PromptIconViewModel$iconViewRotation$1(this.this$0, (Continuation) obj3);
        promptIconViewModel$iconViewRotation$1.I$0 = iIntValue;
        promptIconViewModel$iconViewRotation$1.L$0 = (DisplayRotation) obj2;
        return promptIconViewModel$iconViewRotation$1.invokeSuspend(Unit.INSTANCE);
    }

    @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
    public final Object invokeSuspend(Object obj) {
        int i;
        CoroutineSingletons coroutineSingletons = CoroutineSingletons.COROUTINE_SUSPENDED;
        if (this.label != 0) {
            throw new IllegalStateException("call to 'resume' before 'invoke' with coroutine");
        }
        ResultKt.throwOnFailure(obj);
        int i2 = this.I$0;
        DisplayRotation displayRotation = (DisplayRotation) this.L$0;
        boolean zContains = this.this$0.assetsReusedAcrossRotations.contains(Integer.valueOf(i2));
        float f = 0.0f;
        if (zContains && (i = WhenMappings.$EnumSwitchMapping$0[displayRotation.ordinal()]) != 1) {
            if (i == 2) {
                f = 270.0f;
            } else if (i == 3) {
                f = 180.0f;
            } else {
                if (i != 4) {
                    throw new NoWhenBranchMatchedException();
                }
                f = 90.0f;
            }
        }
        return new Float(f);
    }
}
