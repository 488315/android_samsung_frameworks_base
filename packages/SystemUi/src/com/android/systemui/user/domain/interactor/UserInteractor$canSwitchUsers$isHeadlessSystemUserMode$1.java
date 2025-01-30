package com.android.systemui.user.domain.interactor;

import android.os.UserManager;
import kotlin.ResultKt;
import kotlin.Unit;
import kotlin.coroutines.Continuation;
import kotlin.coroutines.intrinsics.CoroutineSingletons;
import kotlin.coroutines.jvm.internal.DebugMetadata;
import kotlin.coroutines.jvm.internal.SuspendLambda;
import kotlin.jvm.functions.Function2;
import kotlinx.coroutines.CoroutineScope;

/* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
@DebugMetadata(m276c = "com.android.systemui.user.domain.interactor.UserInteractor$canSwitchUsers$isHeadlessSystemUserMode$1", m277f = "UserInteractor.kt", m278l = {}, m279m = "invokeSuspend")
/* loaded from: classes2.dex */
final class UserInteractor$canSwitchUsers$isHeadlessSystemUserMode$1 extends SuspendLambda implements Function2 {
    int label;
    final /* synthetic */ UserInteractor this$0;

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    public UserInteractor$canSwitchUsers$isHeadlessSystemUserMode$1(UserInteractor userInteractor, Continuation<? super UserInteractor$canSwitchUsers$isHeadlessSystemUserMode$1> continuation) {
        super(2, continuation);
        this.this$0 = userInteractor;
    }

    @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
    public final Continuation create(Object obj, Continuation continuation) {
        return new UserInteractor$canSwitchUsers$isHeadlessSystemUserMode$1(this.this$0, continuation);
    }

    @Override // kotlin.jvm.functions.Function2
    public final Object invoke(Object obj, Object obj2) {
        return ((UserInteractor$canSwitchUsers$isHeadlessSystemUserMode$1) create((CoroutineScope) obj, (Continuation) obj2)).invokeSuspend(Unit.INSTANCE);
    }

    @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
    public final Object invokeSuspend(Object obj) {
        CoroutineSingletons coroutineSingletons = CoroutineSingletons.COROUTINE_SUSPENDED;
        if (this.label != 0) {
            throw new IllegalStateException("call to 'resume' before 'invoke' with coroutine");
        }
        ResultKt.throwOnFailure(obj);
        ((HeadlessSystemUserModeImpl) this.this$0.headlessSystemUserMode).getClass();
        return Boolean.valueOf(UserManager.isHeadlessSystemUserMode());
    }
}
