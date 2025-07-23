package com.android.systemui.keyguard.domain.interactor;

import com.android.systemui.keyguard.shared.model.KeyguardState;
import com.android.systemui.keyguard.shared.model.TransitionInfo;
import com.android.systemui.util.kotlin.FlowKt;
import java.util.Set;
import kotlin.Pair;
import kotlin.ResultKt;
import kotlin.Unit;
import kotlin.coroutines.Continuation;
import kotlin.coroutines.intrinsics.CoroutineSingletons;
import kotlin.coroutines.jvm.internal.SuspendLambda;
import kotlin.jvm.functions.Function2;
import kotlin.jvm.functions.Function3;
import kotlin.jvm.internal.AdaptedFunctionReference;
import kotlinx.coroutines.CoroutineScope;
import kotlinx.coroutines.flow.Flow;
import kotlinx.coroutines.flow.FlowCollector;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes2.dex */
final class FromLockscreenTransitionInteractor$listenForLockscreenToDreaming$1 extends SuspendLambda implements Function2 {
    final /* synthetic */ Set<KeyguardState> $invalidFromStates;
    int label;
    final /* synthetic */ FromLockscreenTransitionInteractor this$0;

    /* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
    /* renamed from: com.android.systemui.keyguard.domain.interactor.FromLockscreenTransitionInteractor$listenForLockscreenToDreaming$1$3, reason: invalid class name */
    final /* synthetic */ class AnonymousClass3 extends AdaptedFunctionReference implements Function3 {
        public static final AnonymousClass3 INSTANCE = new AnonymousClass3();

        public AnonymousClass3() {
            super(3, Pair.class, "<init>", "<init>(Ljava/lang/Object;Ljava/lang/Object;)V", 4);
        }

        @Override // kotlin.jvm.functions.Function3
        public final Object invoke(Object obj, Object obj2, Object obj3) {
            Boolean bool = (Boolean) obj;
            bool.booleanValue();
            Boolean bool2 = (Boolean) obj2;
            bool2.booleanValue();
            return new Pair(bool, bool2);
        }
    }

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    /* JADX WARN: Multi-variable type inference failed */
    public FromLockscreenTransitionInteractor$listenForLockscreenToDreaming$1(FromLockscreenTransitionInteractor fromLockscreenTransitionInteractor, Set<? extends KeyguardState> set, Continuation continuation) {
        super(2, continuation);
        this.this$0 = fromLockscreenTransitionInteractor;
        this.$invalidFromStates = set;
    }

    @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
    public final Continuation create(Object obj, Continuation continuation) {
        return new FromLockscreenTransitionInteractor$listenForLockscreenToDreaming$1(this.this$0, this.$invalidFromStates, continuation);
    }

    @Override // kotlin.jvm.functions.Function2
    public final Object invoke(Object obj, Object obj2) {
        return ((FromLockscreenTransitionInteractor$listenForLockscreenToDreaming$1) create((CoroutineScope) obj, (Continuation) obj2)).invokeSuspend(Unit.INSTANCE);
    }

    @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
    public final Object invokeSuspend(Object obj) {
        CoroutineSingletons coroutineSingletons = CoroutineSingletons.COROUTINE_SUSPENDED;
        int i = this.label;
        if (i == 0) {
            ResultKt.throwOnFailure(obj);
            FromLockscreenTransitionInteractor fromLockscreenTransitionInteractor = this.this$0;
            Flow sample = FlowKt.sample(new TransitionInteractor$filterRelevantKeyguardState$$inlined$filter$1(fromLockscreenTransitionInteractor.keyguardInteractor.isAbleToDream, fromLockscreenTransitionInteractor), this.this$0.transitionInteractor.isFinishedIn$1(KeyguardState.LOCKSCREEN), AnonymousClass3.INSTANCE);
            final FromLockscreenTransitionInteractor fromLockscreenTransitionInteractor2 = this.this$0;
            final Set<KeyguardState> set = this.$invalidFromStates;
            FlowCollector flowCollector = new FlowCollector() { // from class: com.android.systemui.keyguard.domain.interactor.FromLockscreenTransitionInteractor$listenForLockscreenToDreaming$1.4
                @Override // kotlinx.coroutines.flow.FlowCollector
                public final Object emit(Object obj2, Continuation continuation) {
                    Pair pair = (Pair) obj2;
                    boolean booleanValue = ((Boolean) pair.component1()).booleanValue();
                    boolean booleanValue2 = ((Boolean) pair.component2()).booleanValue();
                    TransitionInfo currentTransitionInfoInternal$frameworks__base__packages__SystemUI__android_common__SystemUI_core = FromLockscreenTransitionInteractor.this.internalTransitionInteractor.currentTransitionInfoInternal$frameworks__base__packages__SystemUI__android_common__SystemUI_core();
                    boolean z = currentTransitionInfoInternal$frameworks__base__packages__SystemUI__android_common__SystemUI_core.to == KeyguardState.LOCKSCREEN && !set.contains(currentTransitionInfoInternal$frameworks__base__packages__SystemUI__android_common__SystemUI_core.from);
                    if (!booleanValue || (!booleanValue2 && !z)) {
                        return Unit.INSTANCE;
                    }
                    Object startTransitionTo$default = TransitionInteractor.startTransitionTo$default(FromLockscreenTransitionInteractor.this, KeyguardState.DREAMING, null, null, null, continuation, 14);
                    return startTransitionTo$default == CoroutineSingletons.COROUTINE_SUSPENDED ? startTransitionTo$default : Unit.INSTANCE;
                }
            };
            this.label = 1;
            if (sample.collect(flowCollector, this) == coroutineSingletons) {
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
