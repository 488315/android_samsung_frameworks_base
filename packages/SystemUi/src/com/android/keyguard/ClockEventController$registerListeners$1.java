package com.android.keyguard;

import androidx.lifecycle.Lifecycle;
import androidx.lifecycle.LifecycleOwner;
import androidx.lifecycle.RepeatOnLifecycleKt;
import com.android.systemui.flags.FeatureFlags;
import com.android.systemui.flags.Flags;
import com.samsung.android.knox.custom.IKnoxCustomManager;
import kotlin.ResultKt;
import kotlin.Unit;
import kotlin.coroutines.Continuation;
import kotlin.coroutines.intrinsics.CoroutineSingletons;
import kotlin.coroutines.jvm.internal.DebugMetadata;
import kotlin.coroutines.jvm.internal.SuspendLambda;
import kotlin.jvm.functions.Function2;
import kotlin.jvm.functions.Function3;
import kotlinx.coroutines.CoroutineScope;

/* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
@DebugMetadata(m276c = "com.android.keyguard.ClockEventController$registerListeners$1", m277f = "ClockEventController.kt", m278l = {IKnoxCustomManager.Stub.TRANSACTION_getApplicationRestrictionsInternal}, m279m = "invokeSuspend")
/* loaded from: classes.dex */
final class ClockEventController$registerListeners$1 extends SuspendLambda implements Function3 {
    private /* synthetic */ Object L$0;
    int label;
    final /* synthetic */ ClockEventController this$0;

    /* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
    @DebugMetadata(m276c = "com.android.keyguard.ClockEventController$registerListeners$1$1", m277f = "ClockEventController.kt", m278l = {}, m279m = "invokeSuspend")
    /* renamed from: com.android.keyguard.ClockEventController$registerListeners$1$1 */
    final class C06461 extends SuspendLambda implements Function2 {
        private /* synthetic */ Object L$0;
        int label;
        final /* synthetic */ ClockEventController this$0;

        /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
        public C06461(ClockEventController clockEventController, Continuation<? super C06461> continuation) {
            super(2, continuation);
            this.this$0 = clockEventController;
        }

        @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
        public final Continuation create(Object obj, Continuation continuation) {
            C06461 c06461 = new C06461(this.this$0, continuation);
            c06461.L$0 = obj;
            return c06461;
        }

        @Override // kotlin.jvm.functions.Function2
        public final Object invoke(Object obj, Object obj2) {
            return ((C06461) create((CoroutineScope) obj, (Continuation) obj2)).invokeSuspend(Unit.INSTANCE);
        }

        @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
        public final Object invokeSuspend(Object obj) {
            CoroutineSingletons coroutineSingletons = CoroutineSingletons.COROUTINE_SUSPENDED;
            if (this.label != 0) {
                throw new IllegalStateException("call to 'resume' before 'invoke' with coroutine");
            }
            ResultKt.throwOnFailure(obj);
            CoroutineScope coroutineScope = (CoroutineScope) this.L$0;
            this.this$0.m57xde5cc31b(coroutineScope);
            FeatureFlags featureFlags = this.this$0.featureFlags;
            Flags flags = Flags.INSTANCE;
            featureFlags.getClass();
            this.this$0.m55xe30ca956(coroutineScope);
            return Unit.INSTANCE;
        }
    }

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    public ClockEventController$registerListeners$1(ClockEventController clockEventController, Continuation<? super ClockEventController$registerListeners$1> continuation) {
        super(3, continuation);
        this.this$0 = clockEventController;
    }

    @Override // kotlin.jvm.functions.Function3
    public final Object invoke(Object obj, Object obj2, Object obj3) {
        ClockEventController$registerListeners$1 clockEventController$registerListeners$1 = new ClockEventController$registerListeners$1(this.this$0, (Continuation) obj3);
        clockEventController$registerListeners$1.L$0 = (LifecycleOwner) obj;
        return clockEventController$registerListeners$1.invokeSuspend(Unit.INSTANCE);
    }

    @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
    public final Object invokeSuspend(Object obj) {
        CoroutineSingletons coroutineSingletons = CoroutineSingletons.COROUTINE_SUSPENDED;
        int i = this.label;
        if (i == 0) {
            ResultKt.throwOnFailure(obj);
            LifecycleOwner lifecycleOwner = (LifecycleOwner) this.L$0;
            Lifecycle.State state = Lifecycle.State.STARTED;
            C06461 c06461 = new C06461(this.this$0, null);
            this.label = 1;
            if (RepeatOnLifecycleKt.repeatOnLifecycle(lifecycleOwner, state, c06461, this) == coroutineSingletons) {
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
