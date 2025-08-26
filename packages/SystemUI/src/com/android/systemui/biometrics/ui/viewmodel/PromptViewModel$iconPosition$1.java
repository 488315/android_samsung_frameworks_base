package com.android.systemui.biometrics.ui.viewmodel;

import android.graphics.Rect;
import com.android.systemui.biometrics.shared.model.BiometricModalities;
import kotlin.NoWhenBranchMatchedException;
import kotlin.ResultKt;
import kotlin.Unit;
import kotlin.coroutines.Continuation;
import kotlin.coroutines.intrinsics.CoroutineSingletons;
import kotlin.coroutines.jvm.internal.SuspendLambda;
import kotlin.jvm.functions.Function5;

/* loaded from: classes.dex */
final class PromptViewModel$iconPosition$1 extends SuspendLambda implements Function5 {
    /* synthetic */ Object L$0;
    /* synthetic */ Object L$1;
    /* synthetic */ Object L$2;
    /* synthetic */ Object L$3;
    int label;
    final /* synthetic */ PromptViewModel this$0;

    public abstract /* synthetic */ class WhenMappings {
        public static final /* synthetic */ int[] $EnumSwitchMapping$0;

        static {
            int[] iArr = new int[PromptPosition.values().length];
            try {
                iArr[PromptPosition.Bottom.ordinal()] = 1;
            } catch (NoSuchFieldError unused) {
            }
            try {
                iArr[PromptPosition.Right.ordinal()] = 2;
            } catch (NoSuchFieldError unused2) {
            }
            try {
                iArr[PromptPosition.Left.ordinal()] = 3;
            } catch (NoSuchFieldError unused3) {
            }
            try {
                iArr[PromptPosition.Top.ordinal()] = 4;
            } catch (NoSuchFieldError unused4) {
            }
            $EnumSwitchMapping$0 = iArr;
        }
    }

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    public PromptViewModel$iconPosition$1(PromptViewModel promptViewModel, Continuation continuation) {
        super(5, continuation);
        this.this$0 = promptViewModel;
    }

    @Override // kotlin.jvm.functions.Function5
    public final Object invoke(Object obj, Object obj2, Object obj3, Object obj4, Object obj5) {
        PromptViewModel$iconPosition$1 promptViewModel$iconPosition$1 = new PromptViewModel$iconPosition$1(this.this$0, (Continuation) obj5);
        promptViewModel$iconPosition$1.L$0 = (Rect) obj;
        promptViewModel$iconPosition$1.L$1 = (PromptSize) obj2;
        promptViewModel$iconPosition$1.L$2 = (PromptPosition) obj3;
        promptViewModel$iconPosition$1.L$3 = (BiometricModalities) obj4;
        return promptViewModel$iconPosition$1.invokeSuspend(Unit.INSTANCE);
    }

    @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
    public final Object invokeSuspend(Object obj) {
        CoroutineSingletons coroutineSingletons = CoroutineSingletons.COROUTINE_SUSPENDED;
        if (this.label != 0) {
            throw new IllegalStateException("call to 'resume' before 'invoke' with coroutine");
        }
        ResultKt.throwOnFailure(obj);
        Rect rect = (Rect) this.L$0;
        PromptSize promptSize = (PromptSize) this.L$1;
        PromptPosition promptPosition = (PromptPosition) this.L$2;
        BiometricModalities biometricModalities = (BiometricModalities) this.L$3;
        int i = WhenMappings.$EnumSwitchMapping$0[promptPosition.ordinal()];
        if (i == 1) {
            return PromptSizeKt.isSmall(promptSize) ? new Rect(0, 0, 0, this.this$0.portraitSmallBottomPadding) : (PromptSizeKt.isMedium(promptSize) && biometricModalities.getHasUdfps()) ? new Rect(0, 0, 0, rect.bottom) : PromptSizeKt.isMedium(promptSize) ? new Rect(0, 0, 0, this.this$0.portraitMediumBottomPadding) : new Rect(0, 0, 0, this.this$0.portraitLargeScreenBottomPadding);
        }
        if (i == 2) {
            if (PromptSizeKt.isSmall(promptSize) || biometricModalities.getHasFaceOnly()) {
                PromptViewModel promptViewModel = this.this$0;
                return new Rect(0, 0, promptViewModel.landscapeSmallHorizontalPadding, promptViewModel.landscapeSmallBottomPadding);
            }
            if (PromptSizeKt.isMedium(promptSize) && biometricModalities.getHasUdfps()) {
                return new Rect(0, 0, rect.right, rect.bottom);
            }
            PromptViewModel promptViewModel2 = this.this$0;
            return new Rect(0, 0, promptViewModel2.landscapeMediumHorizontalPadding, promptViewModel2.landscapeMediumBottomPadding);
        }
        if (i != 3) {
            if (i == 4) {
                return PromptSizeKt.isSmall(promptSize) ? new Rect(0, 0, 0, this.this$0.portraitSmallBottomPadding) : (PromptSizeKt.isMedium(promptSize) && biometricModalities.getHasUdfps()) ? new Rect(0, 0, 0, rect.bottom) : new Rect(0, 0, 0, this.this$0.portraitMediumBottomPadding);
            }
            throw new NoWhenBranchMatchedException();
        }
        if (PromptSizeKt.isSmall(promptSize) || biometricModalities.getHasFaceOnly()) {
            PromptViewModel promptViewModel3 = this.this$0;
            return new Rect(promptViewModel3.landscapeSmallHorizontalPadding, 0, 0, promptViewModel3.landscapeSmallBottomPadding);
        }
        if (PromptSizeKt.isMedium(promptSize) && biometricModalities.getHasUdfps()) {
            return new Rect(rect.left, 0, 0, rect.bottom);
        }
        PromptViewModel promptViewModel4 = this.this$0;
        return new Rect(promptViewModel4.landscapeMediumHorizontalPadding, 0, 0, promptViewModel4.landscapeMediumBottomPadding);
    }
}
