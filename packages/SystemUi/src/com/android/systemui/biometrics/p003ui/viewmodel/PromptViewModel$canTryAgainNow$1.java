package com.android.systemui.biometrics.p003ui.viewmodel;

import kotlin.ResultKt;
import kotlin.Unit;
import kotlin.coroutines.Continuation;
import kotlin.coroutines.intrinsics.CoroutineSingletons;
import kotlin.coroutines.jvm.internal.DebugMetadata;
import kotlin.coroutines.jvm.internal.SuspendLambda;
import kotlin.jvm.functions.Function5;

/* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
@DebugMetadata(m276c = "com.android.systemui.biometrics.ui.viewmodel.PromptViewModel$canTryAgainNow$1", m277f = "PromptViewModel.kt", m278l = {}, m279m = "invokeSuspend")
/* loaded from: classes.dex */
public final class PromptViewModel$canTryAgainNow$1 extends SuspendLambda implements Function5 {
    /* synthetic */ Object L$0;
    /* synthetic */ Object L$1;
    /* synthetic */ boolean Z$0;
    /* synthetic */ boolean Z$1;
    int label;

    public PromptViewModel$canTryAgainNow$1(Continuation<? super PromptViewModel$canTryAgainNow$1> continuation) {
        super(5, continuation);
    }

    @Override // kotlin.jvm.functions.Function5
    public final Object invoke(Object obj, Object obj2, Object obj3, Object obj4, Object obj5) {
        boolean booleanValue = ((Boolean) obj).booleanValue();
        boolean booleanValue2 = ((Boolean) obj4).booleanValue();
        PromptViewModel$canTryAgainNow$1 promptViewModel$canTryAgainNow$1 = new PromptViewModel$canTryAgainNow$1((Continuation) obj5);
        promptViewModel$canTryAgainNow$1.Z$0 = booleanValue;
        promptViewModel$canTryAgainNow$1.L$0 = (PromptSize) obj2;
        promptViewModel$canTryAgainNow$1.L$1 = (PromptAuthState) obj3;
        promptViewModel$canTryAgainNow$1.Z$1 = booleanValue2;
        return promptViewModel$canTryAgainNow$1.invokeSuspend(Unit.INSTANCE);
    }

    /* JADX WARN: Code restructure failed: missing block: B:9:0x0023, code lost:
    
        if ((!r1.isAuthenticated) != false) goto L13;
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
        boolean z = this.Z$0;
        PromptSize promptSize = (PromptSize) this.L$0;
        PromptAuthState promptAuthState = (PromptAuthState) this.L$1;
        boolean z2 = z && PromptSizeKt.isNotSmall(promptSize) && this.Z$1;
        return Boolean.valueOf(z2);
    }
}
