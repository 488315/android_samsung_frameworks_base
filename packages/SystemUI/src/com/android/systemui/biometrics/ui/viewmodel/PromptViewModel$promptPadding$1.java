package com.android.systemui.biometrics.ui.viewmodel;

import android.graphics.Insets;
import android.graphics.Rect;
import com.android.systemui.biometrics.Utils;
import com.android.systemui.biometrics.shared.model.DisplayRotation;
import kotlin.ResultKt;
import kotlin.Unit;
import kotlin.coroutines.Continuation;
import kotlin.coroutines.intrinsics.CoroutineSingletons;
import kotlin.coroutines.jvm.internal.SuspendLambda;
import kotlin.jvm.functions.Function3;

final class PromptViewModel$promptPadding$1 extends SuspendLambda implements Function3 {
    /* synthetic */ Object L$0;
    /* synthetic */ Object L$1;
    int label;
    final /* synthetic */ PromptViewModel this$0;

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    public PromptViewModel$promptPadding$1(PromptViewModel promptViewModel, Continuation continuation) {
        super(3, continuation);
        this.this$0 = promptViewModel;
    }

    @Override // kotlin.jvm.functions.Function3
    public final Object invoke(Object obj, Object obj2, Object obj3) {
        PromptViewModel$promptPadding$1 promptViewModel$promptPadding$1 = new PromptViewModel$promptPadding$1(this.this$0, (Continuation) obj3);
        promptViewModel$promptPadding$1.L$0 = (PromptSize) obj;
        promptViewModel$promptPadding$1.L$1 = (DisplayRotation) obj2;
        return promptViewModel$promptPadding$1.invokeSuspend(Unit.INSTANCE);
    }

    @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
    public final Object invokeSuspend(Object obj) {
        CoroutineSingletons coroutineSingletons = CoroutineSingletons.COROUTINE_SUSPENDED;
        if (this.label != 0) {
            throw new IllegalStateException("call to 'resume' before 'invoke' with coroutine");
        }
        ResultKt.throwOnFailure(obj);
        PromptSize promptSize = (PromptSize) this.L$0;
        DisplayRotation displayRotation = (DisplayRotation) this.L$1;
        if (promptSize == PromptSize.LARGE) {
            return new Rect(0, 0, 0, 0);
        }
        Insets navbarInsets = Utils.getNavbarInsets(this.this$0.context);
        return displayRotation == DisplayRotation.ROTATION_90 ? new Rect(0, 0, navbarInsets.right, 0) : displayRotation == DisplayRotation.ROTATION_270 ? new Rect(navbarInsets.left, 0, 0, 0) : new Rect(0, 0, 0, navbarInsets.bottom);
    }
}
