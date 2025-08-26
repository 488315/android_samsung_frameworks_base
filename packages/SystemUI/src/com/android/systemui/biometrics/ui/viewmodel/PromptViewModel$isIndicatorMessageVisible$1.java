package com.android.systemui.biometrics.ui.viewmodel;

import com.android.systemui.biometrics.ui.viewmodel.PromptMessage;
import kotlin.ResultKt;
import kotlin.Unit;
import kotlin.coroutines.Continuation;
import kotlin.coroutines.intrinsics.CoroutineSingletons;
import kotlin.coroutines.jvm.internal.SuspendLambda;
import kotlin.jvm.functions.Function4;
import kotlin.text.StringsKt__StringsKt;

/* loaded from: classes.dex */
final class PromptViewModel$isIndicatorMessageVisible$1 extends SuspendLambda implements Function4 {
    /* synthetic */ Object L$0;
    /* synthetic */ Object L$1;
    int label;

    public PromptViewModel$isIndicatorMessageVisible$1(Continuation continuation) {
        super(4, continuation);
    }

    @Override // kotlin.jvm.functions.Function4
    public final Object invoke(Object obj, Object obj2, Object obj3, Object obj4) {
        PromptViewModel$isIndicatorMessageVisible$1 promptViewModel$isIndicatorMessageVisible$1 = new PromptViewModel$isIndicatorMessageVisible$1((Continuation) obj4);
        promptViewModel$isIndicatorMessageVisible$1.L$0 = (PromptSize) obj;
        promptViewModel$isIndicatorMessageVisible$1.L$1 = (PromptMessage) obj3;
        return promptViewModel$isIndicatorMessageVisible$1.invokeSuspend(Unit.INSTANCE);
    }

    /* JADX WARN: Removed duplicated region for block: B:16:0x0036  */
    @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    public final Object invokeSuspend(Object obj) {
        boolean z;
        CoroutineSingletons coroutineSingletons = CoroutineSingletons.COROUTINE_SUSPENDED;
        if (this.label != 0) {
            throw new IllegalStateException("call to 'resume' before 'invoke' with coroutine");
        }
        ResultKt.throwOnFailure(obj);
        PromptSize promptSize = (PromptSize) this.L$0;
        PromptMessage promptMessage = (PromptMessage) this.L$1;
        if (PromptSizeKt.isMedium(promptSize)) {
            promptMessage.getClass();
            z = !StringsKt__StringsKt.isBlank(promptMessage instanceof PromptMessage.Error ? ((PromptMessage.Error) promptMessage).errorMessage : promptMessage instanceof PromptMessage.Help ? ((PromptMessage.Help) promptMessage).helpMessage : "");
        }
        return Boolean.valueOf(z);
    }
}
