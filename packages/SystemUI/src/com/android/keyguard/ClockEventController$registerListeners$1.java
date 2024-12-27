package com.android.keyguard;

import androidx.lifecycle.Lifecycle;
import androidx.lifecycle.LifecycleOwner;
import androidx.lifecycle.RepeatOnLifecycleKt;
import com.android.systemui.Flags;
import kotlin.ResultKt;
import kotlin.Unit;
import kotlin.coroutines.Continuation;
import kotlin.coroutines.intrinsics.CoroutineSingletons;
import kotlin.coroutines.jvm.internal.SuspendLambda;
import kotlin.jvm.functions.Function2;
import kotlin.jvm.functions.Function3;
import kotlinx.coroutines.CoroutineScope;

/* compiled from: qb/89523975 427a50d40ec74a85ca352b86f77450b1c52ece5389e11158752b0d641a3a5098 */
/* loaded from: classes.dex */
final class ClockEventController$registerListeners$1 extends SuspendLambda implements Function3 {
    private /* synthetic */ Object L$0;
    int label;
    final /* synthetic */ ClockEventController this$0;

    /* compiled from: qb/89523975 427a50d40ec74a85ca352b86f77450b1c52ece5389e11158752b0d641a3a5098 */
    /* renamed from: com.android.keyguard.ClockEventController$registerListeners$1$1, reason: invalid class name */
    final class AnonymousClass1 extends SuspendLambda implements Function2 {
        private /* synthetic */ Object L$0;
        int label;
        final /* synthetic */ ClockEventController this$0;

        /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
        public AnonymousClass1(ClockEventController clockEventController, Continuation continuation) {
            super(2, continuation);
            this.this$0 = clockEventController;
        }

        @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
        public final Continuation create(Object obj, Continuation continuation) {
            AnonymousClass1 anonymousClass1 = new AnonymousClass1(this.this$0, continuation);
            anonymousClass1.L$0 = obj;
            return anonymousClass1;
        }

        @Override // kotlin.jvm.functions.Function2
        public final Object invoke(Object obj, Object obj2) {
            return ((AnonymousClass1) create((CoroutineScope) obj, (Continuation) obj2)).invokeSuspend(Unit.INSTANCE);
        }

        @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
        public final Object invokeSuspend(Object obj) {
            CoroutineSingletons coroutineSingletons = CoroutineSingletons.COROUTINE_SUSPENDED;
            if (this.label != 0) {
                throw new IllegalStateException("call to 'resume' before 'invoke' with coroutine");
            }
            ResultKt.throwOnFailure(obj);
            CoroutineScope coroutineScope = (CoroutineScope) this.L$0;
            this.this$0.listenForDozing$frameworks__base__packages__SystemUI__android_common__SystemUI_core(coroutineScope);
            Flags.migrateClocksToBlueprint();
            this.this$0.listenForDozeAmount$frameworks__base__packages__SystemUI__android_common__SystemUI_core(coroutineScope);
            return Unit.INSTANCE;
        }
    }

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    public ClockEventController$registerListeners$1(ClockEventController clockEventController, Continuation continuation) {
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
            Lifecycle.State state = Lifecycle.State.CREATED;
            AnonymousClass1 anonymousClass1 = new AnonymousClass1(this.this$0, null);
            this.label = 1;
            if (RepeatOnLifecycleKt.repeatOnLifecycle(lifecycleOwner, state, anonymousClass1, this) == coroutineSingletons) {
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
