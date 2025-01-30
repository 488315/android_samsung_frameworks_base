package com.android.systemui.statusbar.pipeline.mobile.domain.interactor;

import kotlin.ResultKt;
import kotlin.Unit;
import kotlin.coroutines.Continuation;
import kotlin.coroutines.intrinsics.CoroutineSingletons;
import kotlin.coroutines.jvm.internal.DebugMetadata;
import kotlin.coroutines.jvm.internal.SuspendLambda;
import kotlin.jvm.functions.Function4;

/* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
@DebugMetadata(m276c = "com.android.systemui.statusbar.pipeline.mobile.domain.interactor.MobileIconInteractorImpl$showExclamationMark$1", m277f = "MobileIconInteractor.kt", m278l = {}, m279m = "invokeSuspend")
/* loaded from: classes2.dex */
public final class MobileIconInteractorImpl$showExclamationMark$1 extends SuspendLambda implements Function4 {
    /* synthetic */ boolean Z$0;
    /* synthetic */ boolean Z$1;
    /* synthetic */ boolean Z$2;
    int label;

    public MobileIconInteractorImpl$showExclamationMark$1(Continuation<? super MobileIconInteractorImpl$showExclamationMark$1> continuation) {
        super(4, continuation);
    }

    @Override // kotlin.jvm.functions.Function4
    public final Object invoke(Object obj, Object obj2, Object obj3, Object obj4) {
        boolean booleanValue = ((Boolean) obj).booleanValue();
        boolean booleanValue2 = ((Boolean) obj2).booleanValue();
        boolean booleanValue3 = ((Boolean) obj3).booleanValue();
        MobileIconInteractorImpl$showExclamationMark$1 mobileIconInteractorImpl$showExclamationMark$1 = new MobileIconInteractorImpl$showExclamationMark$1((Continuation) obj4);
        mobileIconInteractorImpl$showExclamationMark$1.Z$0 = booleanValue;
        mobileIconInteractorImpl$showExclamationMark$1.Z$1 = booleanValue2;
        mobileIconInteractorImpl$showExclamationMark$1.Z$2 = booleanValue3;
        return mobileIconInteractorImpl$showExclamationMark$1.invokeSuspend(Unit.INSTANCE);
    }

    @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
    public final Object invokeSuspend(Object obj) {
        CoroutineSingletons coroutineSingletons = CoroutineSingletons.COROUTINE_SUSPENDED;
        if (this.label != 0) {
            throw new IllegalStateException("call to 'resume' before 'invoke' with coroutine");
        }
        ResultKt.throwOnFailure(obj);
        return Boolean.valueOf((this.Z$0 && !this.Z$1 && this.Z$2) ? false : true);
    }
}
