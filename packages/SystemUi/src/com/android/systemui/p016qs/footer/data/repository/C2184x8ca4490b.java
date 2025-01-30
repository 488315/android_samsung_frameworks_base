package com.android.systemui.p016qs.footer.data.repository;

import kotlin.ResultKt;
import kotlin.Unit;
import kotlin.coroutines.Continuation;
import kotlin.coroutines.intrinsics.CoroutineSingletons;
import kotlin.coroutines.jvm.internal.DebugMetadata;
import kotlin.coroutines.jvm.internal.SuspendLambda;
import kotlin.jvm.functions.Function2;
import kotlinx.coroutines.CoroutineScope;
import kotlinx.coroutines.channels.ProducerScope;

/* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
@DebugMetadata(m276c = "com.android.systemui.qs.footer.data.repository.UserSwitcherRepositoryImpl$isEnabled$1$observer$1$handleValueChanged$1", m277f = "UserSwitcherRepository.kt", m278l = {85}, m279m = "invokeSuspend")
/* renamed from: com.android.systemui.qs.footer.data.repository.UserSwitcherRepositoryImpl$isEnabled$1$observer$1$handleValueChanged$1 */
/* loaded from: classes2.dex */
final class C2184x8ca4490b extends SuspendLambda implements Function2 {
    final /* synthetic */ ProducerScope $$this$conflatedCallbackFlow;
    int label;
    final /* synthetic */ UserSwitcherRepositoryImpl this$0;

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    public C2184x8ca4490b(ProducerScope producerScope, UserSwitcherRepositoryImpl userSwitcherRepositoryImpl, Continuation<? super C2184x8ca4490b> continuation) {
        super(2, continuation);
        this.$$this$conflatedCallbackFlow = producerScope;
        this.this$0 = userSwitcherRepositoryImpl;
    }

    @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
    public final Continuation create(Object obj, Continuation continuation) {
        return new C2184x8ca4490b(this.$$this$conflatedCallbackFlow, this.this$0, continuation);
    }

    @Override // kotlin.jvm.functions.Function2
    public final Object invoke(Object obj, Object obj2) {
        return ((C2184x8ca4490b) create((CoroutineScope) obj, (Continuation) obj2)).invokeSuspend(Unit.INSTANCE);
    }

    @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
    public final Object invokeSuspend(Object obj) {
        CoroutineSingletons coroutineSingletons = CoroutineSingletons.COROUTINE_SUSPENDED;
        int i = this.label;
        if (i == 0) {
            ResultKt.throwOnFailure(obj);
            ProducerScope producerScope = this.$$this$conflatedCallbackFlow;
            UserSwitcherRepositoryImpl userSwitcherRepositoryImpl = this.this$0;
            this.label = 1;
            if (UserSwitcherRepositoryImpl$isEnabled$1.invokeSuspend$updateState(producerScope, userSwitcherRepositoryImpl, this) == coroutineSingletons) {
                return coroutineSingletons;
            }
        } else {
            if (i != 1) {
                throw new IllegalStateException("call to 'resume' before 'invoke' with coroutine");
            }
            ResultKt.throwOnFailure(obj);
        }
        return Unit.INSTANCE;
    }
}
