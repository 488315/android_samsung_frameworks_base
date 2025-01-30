package com.android.systemui.biometrics.p003ui.viewmodel;

import kotlin.ResultKt;
import kotlin.Unit;
import kotlin.coroutines.Continuation;
import kotlin.coroutines.intrinsics.CoroutineSingletons;
import kotlin.coroutines.jvm.internal.DebugMetadata;
import kotlin.coroutines.jvm.internal.SuspendLambda;
import kotlin.jvm.functions.Function3;

/* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
@DebugMetadata(m276c = "com.android.systemui.biometrics.ui.viewmodel.PromptViewModel$isIndicatorMessageVisible$1", m277f = "PromptViewModel.kt", m278l = {}, m279m = "invokeSuspend")
/* loaded from: classes.dex */
public final class PromptViewModel$isIndicatorMessageVisible$1 extends SuspendLambda implements Function3 {
    /* synthetic */ Object L$0;
    /* synthetic */ Object L$1;
    int label;

    public PromptViewModel$isIndicatorMessageVisible$1(Continuation<? super PromptViewModel$isIndicatorMessageVisible$1> continuation) {
        super(3, continuation);
    }

    @Override // kotlin.jvm.functions.Function3
    public final Object invoke(Object obj, Object obj2, Object obj3) {
        PromptViewModel$isIndicatorMessageVisible$1 promptViewModel$isIndicatorMessageVisible$1 = new PromptViewModel$isIndicatorMessageVisible$1((Continuation) obj3);
        promptViewModel$isIndicatorMessageVisible$1.L$0 = (PromptSize) obj;
        promptViewModel$isIndicatorMessageVisible$1.L$1 = (PromptMessage) obj2;
        return promptViewModel$isIndicatorMessageVisible$1.invokeSuspend(Unit.INSTANCE);
    }

    /* JADX WARN: Code restructure failed: missing block: B:6:0x0021, code lost:
    
        if ((!kotlin.text.StringsKt__StringsJVMKt.isBlank(r1.getMessage())) != false) goto L10;
     */
    @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    public final Object invokeSuspend(Object obj) {
        CoroutineSingletons coroutineSingletons = CoroutineSingletons.COROUTINE_SUSPENDED;
        if (this.label != 0) {
            throw new IllegalStateException("call to 'resume' before 'invoke' with coroutine");
        }
        ResultKt.throwOnFailure(obj);
        PromptSize promptSize = (PromptSize) this.L$0;
        PromptMessage promptMessage = (PromptMessage) this.L$1;
        boolean z = PromptSizeKt.isNotSmall(promptSize);
        return Boolean.valueOf(z);
    }
}
