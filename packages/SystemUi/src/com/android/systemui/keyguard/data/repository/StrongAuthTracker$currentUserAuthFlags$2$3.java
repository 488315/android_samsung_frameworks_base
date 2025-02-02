package com.android.systemui.keyguard.data.repository;

import com.android.systemui.keyguard.shared.model.AuthenticationFlags;
import kotlin.ResultKt;
import kotlin.Unit;
import kotlin.coroutines.Continuation;
import kotlin.coroutines.intrinsics.CoroutineSingletons;
import kotlin.coroutines.jvm.internal.DebugMetadata;
import kotlin.coroutines.jvm.internal.SuspendLambda;
import kotlin.jvm.functions.Function2;
import kotlinx.coroutines.flow.FlowCollector;

/* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
@DebugMetadata(m276c = "com.android.systemui.keyguard.data.repository.StrongAuthTracker$currentUserAuthFlags$2$3", m277f = "BiometricSettingsRepository.kt", m278l = {346}, m279m = "invokeSuspend")
/* loaded from: classes.dex */
public final class StrongAuthTracker$currentUserAuthFlags$2$3 extends SuspendLambda implements Function2 {
    final /* synthetic */ int $userId;
    private /* synthetic */ Object L$0;
    int label;
    final /* synthetic */ StrongAuthTracker this$0;

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    public StrongAuthTracker$currentUserAuthFlags$2$3(int i, StrongAuthTracker strongAuthTracker, Continuation<? super StrongAuthTracker$currentUserAuthFlags$2$3> continuation) {
        super(2, continuation);
        this.$userId = i;
        this.this$0 = strongAuthTracker;
    }

    @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
    public final Continuation create(Object obj, Continuation continuation) {
        StrongAuthTracker$currentUserAuthFlags$2$3 strongAuthTracker$currentUserAuthFlags$2$3 = new StrongAuthTracker$currentUserAuthFlags$2$3(this.$userId, this.this$0, continuation);
        strongAuthTracker$currentUserAuthFlags$2$3.L$0 = obj;
        return strongAuthTracker$currentUserAuthFlags$2$3;
    }

    @Override // kotlin.jvm.functions.Function2
    public final Object invoke(Object obj, Object obj2) {
        return ((StrongAuthTracker$currentUserAuthFlags$2$3) create((FlowCollector) obj, (Continuation) obj2)).invokeSuspend(Unit.INSTANCE);
    }

    @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
    public final Object invokeSuspend(Object obj) {
        CoroutineSingletons coroutineSingletons = CoroutineSingletons.COROUTINE_SUSPENDED;
        int i = this.label;
        if (i == 0) {
            ResultKt.throwOnFailure(obj);
            FlowCollector flowCollector = (FlowCollector) this.L$0;
            int i2 = this.$userId;
            AuthenticationFlags authenticationFlags = new AuthenticationFlags(i2, this.this$0.getStrongAuthForUser(i2));
            this.label = 1;
            if (flowCollector.emit(authenticationFlags, this) == coroutineSingletons) {
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
