package com.android.systemui.user.domain.interactor;

import android.content.pm.UserInfo;
import kotlin.ResultKt;
import kotlin.Unit;
import kotlin.coroutines.Continuation;
import kotlin.coroutines.intrinsics.CoroutineSingletons;
import kotlin.coroutines.jvm.internal.DebugMetadata;
import kotlin.coroutines.jvm.internal.SuspendLambda;
import kotlin.jvm.functions.Function2;
import kotlinx.coroutines.CoroutineScope;

/* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
@DebugMetadata(m276c = "com.android.systemui.user.domain.interactor.GuestUserInteractor$remove$marked$1", m277f = "GuestUserInteractor.kt", m278l = {}, m279m = "invokeSuspend")
/* loaded from: classes2.dex */
final class GuestUserInteractor$remove$marked$1 extends SuspendLambda implements Function2 {
    final /* synthetic */ UserInfo $currentUser;
    int label;
    final /* synthetic */ GuestUserInteractor this$0;

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    public GuestUserInteractor$remove$marked$1(GuestUserInteractor guestUserInteractor, UserInfo userInfo, Continuation<? super GuestUserInteractor$remove$marked$1> continuation) {
        super(2, continuation);
        this.this$0 = guestUserInteractor;
        this.$currentUser = userInfo;
    }

    @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
    public final Continuation create(Object obj, Continuation continuation) {
        return new GuestUserInteractor$remove$marked$1(this.this$0, this.$currentUser, continuation);
    }

    @Override // kotlin.jvm.functions.Function2
    public final Object invoke(Object obj, Object obj2) {
        return ((GuestUserInteractor$remove$marked$1) create((CoroutineScope) obj, (Continuation) obj2)).invokeSuspend(Unit.INSTANCE);
    }

    @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
    public final Object invokeSuspend(Object obj) {
        CoroutineSingletons coroutineSingletons = CoroutineSingletons.COROUTINE_SUSPENDED;
        if (this.label != 0) {
            throw new IllegalStateException("call to 'resume' before 'invoke' with coroutine");
        }
        ResultKt.throwOnFailure(obj);
        return Boolean.valueOf(this.this$0.manager.markGuestForDeletion(this.$currentUser.id));
    }
}
