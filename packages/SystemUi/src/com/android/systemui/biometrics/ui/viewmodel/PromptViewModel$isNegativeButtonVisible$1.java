package com.android.systemui.biometrics.ui.viewmodel;

import kotlin.ResultKt;
import kotlin.Unit;
import kotlin.coroutines.Continuation;
import kotlin.coroutines.intrinsics.CoroutineSingletons;
import kotlin.coroutines.jvm.internal.DebugMetadata;
import kotlin.coroutines.jvm.internal.SuspendLambda;
import kotlin.jvm.functions.Function4;

/* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
@DebugMetadata(m276c = "com.android.systemui.biometrics.ui.viewmodel.PromptViewModel$isNegativeButtonVisible$1", m277f = "PromptViewModel.kt", m278l = {}, m279m = "invokeSuspend")
/* loaded from: classes.dex */
public final class PromptViewModel$isNegativeButtonVisible$1 extends SuspendLambda implements Function4 {
    /* synthetic */ Object L$0;
    /* synthetic */ Object L$1;
    /* synthetic */ boolean Z$0;
    int label;

    public PromptViewModel$isNegativeButtonVisible$1(Continuation<? super PromptViewModel$isNegativeButtonVisible$1> continuation) {
        super(4, continuation);
    }

    @Override // kotlin.jvm.functions.Function4
    public final Object invoke(Object obj, Object obj2, Object obj3, Object obj4) {
        boolean booleanValue = ((Boolean) obj3).booleanValue();
        PromptViewModel$isNegativeButtonVisible$1 promptViewModel$isNegativeButtonVisible$1 = new PromptViewModel$isNegativeButtonVisible$1((Continuation) obj4);
        promptViewModel$isNegativeButtonVisible$1.L$0 = (PromptSize) obj;
        promptViewModel$isNegativeButtonVisible$1.L$1 = (PromptAuthState) obj2;
        promptViewModel$isNegativeButtonVisible$1.Z$0 = booleanValue;
        return promptViewModel$isNegativeButtonVisible$1.invokeSuspend(Unit.INSTANCE);
    }

    /* JADX WARN: Code restructure failed: missing block: B:7:0x001f, code lost:
    
        if (r1 == false) goto L11;
     */
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
        PromptAuthState promptAuthState = (PromptAuthState) this.L$1;
        boolean z2 = this.Z$0;
        if (PromptSizeKt.isNotSmall(promptSize)) {
            boolean z3 = promptAuthState.isAuthenticated;
            z = true;
            if (!z3) {
            }
        }
        z = false;
        return Boolean.valueOf(z);
    }
}
