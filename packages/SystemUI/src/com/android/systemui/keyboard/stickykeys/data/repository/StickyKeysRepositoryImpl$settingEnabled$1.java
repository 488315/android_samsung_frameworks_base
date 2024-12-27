package com.android.systemui.keyboard.stickykeys.data.repository;

import kotlin.ResultKt;
import kotlin.Unit;
import kotlin.coroutines.Continuation;
import kotlin.coroutines.intrinsics.CoroutineSingletons;
import kotlin.coroutines.jvm.internal.SuspendLambda;
import kotlin.jvm.functions.Function2;

final class StickyKeysRepositoryImpl$settingEnabled$1 extends SuspendLambda implements Function2 {
    /* synthetic */ boolean Z$0;
    int label;
    final /* synthetic */ StickyKeysRepositoryImpl this$0;

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    public StickyKeysRepositoryImpl$settingEnabled$1(StickyKeysRepositoryImpl stickyKeysRepositoryImpl, Continuation continuation) {
        super(2, continuation);
        this.this$0 = stickyKeysRepositoryImpl;
    }

    @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
    public final Continuation create(Object obj, Continuation continuation) {
        StickyKeysRepositoryImpl$settingEnabled$1 stickyKeysRepositoryImpl$settingEnabled$1 = new StickyKeysRepositoryImpl$settingEnabled$1(this.this$0, continuation);
        stickyKeysRepositoryImpl$settingEnabled$1.Z$0 = ((Boolean) obj).booleanValue();
        return stickyKeysRepositoryImpl$settingEnabled$1;
    }

    @Override // kotlin.jvm.functions.Function2
    public final Object invoke(Object obj, Object obj2) {
        Boolean bool = (Boolean) obj;
        bool.booleanValue();
        return ((StickyKeysRepositoryImpl$settingEnabled$1) create(bool, (Continuation) obj2)).invokeSuspend(Unit.INSTANCE);
    }

    @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
    public final Object invokeSuspend(Object obj) {
        CoroutineSingletons coroutineSingletons = CoroutineSingletons.COROUTINE_SUSPENDED;
        if (this.label != 0) {
            throw new IllegalStateException("call to 'resume' before 'invoke' with coroutine");
        }
        ResultKt.throwOnFailure(obj);
        this.this$0.stickyKeysLogger.logNewSettingValue(this.Z$0);
        return Unit.INSTANCE;
    }
}
