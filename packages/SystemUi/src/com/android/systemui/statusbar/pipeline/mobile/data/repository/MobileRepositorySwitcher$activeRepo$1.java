package com.android.systemui.statusbar.pipeline.mobile.data.repository;

import kotlin.ResultKt;
import kotlin.Unit;
import kotlin.coroutines.Continuation;
import kotlin.coroutines.intrinsics.CoroutineSingletons;
import kotlin.coroutines.jvm.internal.DebugMetadata;
import kotlin.coroutines.jvm.internal.SuspendLambda;
import kotlin.jvm.functions.Function2;

/* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
@DebugMetadata(m276c = "com.android.systemui.statusbar.pipeline.mobile.data.repository.MobileRepositorySwitcher$activeRepo$1", m277f = "MobileRepositorySwitcher.kt", m278l = {}, m279m = "invokeSuspend")
/* loaded from: classes2.dex */
public final class MobileRepositorySwitcher$activeRepo$1 extends SuspendLambda implements Function2 {
    /* synthetic */ boolean Z$0;
    int label;
    final /* synthetic */ MobileRepositorySwitcher this$0;

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    public MobileRepositorySwitcher$activeRepo$1(MobileRepositorySwitcher mobileRepositorySwitcher, Continuation<? super MobileRepositorySwitcher$activeRepo$1> continuation) {
        super(2, continuation);
        this.this$0 = mobileRepositorySwitcher;
    }

    @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
    public final Continuation create(Object obj, Continuation continuation) {
        MobileRepositorySwitcher$activeRepo$1 mobileRepositorySwitcher$activeRepo$1 = new MobileRepositorySwitcher$activeRepo$1(this.this$0, continuation);
        mobileRepositorySwitcher$activeRepo$1.Z$0 = ((Boolean) obj).booleanValue();
        return mobileRepositorySwitcher$activeRepo$1;
    }

    @Override // kotlin.jvm.functions.Function2
    public final Object invoke(Object obj, Object obj2) {
        return ((MobileRepositorySwitcher$activeRepo$1) create(Boolean.valueOf(((Boolean) obj).booleanValue()), (Continuation) obj2)).invokeSuspend(Unit.INSTANCE);
    }

    @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
    public final Object invokeSuspend(Object obj) {
        CoroutineSingletons coroutineSingletons = CoroutineSingletons.COROUTINE_SUSPENDED;
        if (this.label != 0) {
            throw new IllegalStateException("call to 'resume' before 'invoke' with coroutine");
        }
        ResultKt.throwOnFailure(obj);
        return this.Z$0 ? this.this$0.demoMobileConnectionsRepository : this.this$0.realRepository;
    }
}
