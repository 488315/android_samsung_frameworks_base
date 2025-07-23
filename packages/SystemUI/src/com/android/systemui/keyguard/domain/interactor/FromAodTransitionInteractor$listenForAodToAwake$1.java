package com.android.systemui.keyguard.domain.interactor;

import com.android.systemui.util.kotlin.Utils;
import kotlin.ResultKt;
import kotlin.Unit;
import kotlin.coroutines.Continuation;
import kotlin.coroutines.intrinsics.CoroutineSingletons;
import kotlin.coroutines.jvm.internal.SuspendLambda;
import kotlin.jvm.functions.Function2;
import kotlinx.coroutines.CoroutineScope;
import kotlinx.coroutines.flow.Flow;
import kotlinx.coroutines.flow.FlowCollector;
import kotlinx.coroutines.flow.FlowKt;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes2.dex */
final class FromAodTransitionInteractor$listenForAodToAwake$1 extends SuspendLambda implements Function2 {
    int label;
    final /* synthetic */ FromAodTransitionInteractor this$0;

    /* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
    /* renamed from: com.android.systemui.keyguard.domain.interactor.FromAodTransitionInteractor$listenForAodToAwake$1$2, reason: invalid class name */
    public final class AnonymousClass2 implements FlowCollector {
        public final /* synthetic */ FromAodTransitionInteractor this$0;

        public AnonymousClass2(FromAodTransitionInteractor fromAodTransitionInteractor) {
            this.this$0 = fromAodTransitionInteractor;
        }

        /* JADX WARN: Code restructure failed: missing block: B:31:0x0075, code lost:
        
            if (r13 == false) goto L27;
         */
        /* JADX WARN: Code restructure failed: missing block: B:42:0x018a, code lost:
        
            if (com.android.systemui.keyguard.domain.interactor.TransitionInteractor.startTransitionTo$default(r9.this$0, r2, null, r4, "canWakeDirectlyToGone = true", r6, 2) == r0) goto L73;
         */
        /* JADX WARN: Code restructure failed: missing block: B:50:0x01ba, code lost:
        
            if (com.android.systemui.keyguard.domain.interactor.TransitionInteractor.startTransitionTo$default(r9.this$0, r2, null, r4, "listen for aod to awake", r6, 2) == r0) goto L73;
         */
        /* JADX WARN: Code restructure failed: missing block: B:57:0x01db, code lost:
        
            if (com.android.systemui.keyguard.domain.interactor.TransitionInteractor.startTransitionTo$default(r9.this$0, r2, null, null, "waking up and isOccluded=true", r6, 6) == r0) goto L73;
         */
        /* JADX WARN: Removed duplicated region for block: B:35:0x014a  */
        /* JADX WARN: Removed duplicated region for block: B:41:0x0171  */
        /* JADX WARN: Removed duplicated region for block: B:44:0x0190  */
        /* JADX WARN: Removed duplicated region for block: B:65:0x0111  */
        /* JADX WARN: Removed duplicated region for block: B:71:0x0097  */
        /* JADX WARN: Removed duplicated region for block: B:9:0x0027  */
        @Override // kotlinx.coroutines.flow.FlowCollector
        /*
            Code decompiled incorrectly, please refer to instructions dump.
            To view partially-correct code enable 'Show inconsistent code' option in preferences
        */
        public final java.lang.Object emit(kotlin.Triple r14, kotlin.coroutines.Continuation r15) {
            /*
                Method dump skipped, instructions count: 484
                To view this dump change 'Code comments level' option to 'DEBUG'
            */
            throw new UnsupportedOperationException("Method not decompiled: com.android.systemui.keyguard.domain.interactor.FromAodTransitionInteractor$listenForAodToAwake$1.AnonymousClass2.emit(kotlin.Triple, kotlin.coroutines.Continuation):java.lang.Object");
        }
    }

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    public FromAodTransitionInteractor$listenForAodToAwake$1(FromAodTransitionInteractor fromAodTransitionInteractor, Continuation continuation) {
        super(2, continuation);
        this.this$0 = fromAodTransitionInteractor;
    }

    @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
    public final Continuation create(Object obj, Continuation continuation) {
        return new FromAodTransitionInteractor$listenForAodToAwake$1(this.this$0, continuation);
    }

    @Override // kotlin.jvm.functions.Function2
    public final Object invoke(Object obj, Object obj2) {
        return ((FromAodTransitionInteractor$listenForAodToAwake$1) create((CoroutineScope) obj, (Continuation) obj2)).invokeSuspend(Unit.INSTANCE);
    }

    @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
    public final Object invokeSuspend(Object obj) {
        CoroutineSingletons coroutineSingletons = CoroutineSingletons.COROUTINE_SUSPENDED;
        int i = this.label;
        if (i == 0) {
            ResultKt.throwOnFailure(obj);
            Utils.Companion companion = Utils.Companion;
            FromAodTransitionInteractor fromAodTransitionInteractor = this.this$0;
            TransitionInteractor$filterRelevantKeyguardStateAnd$$inlined$filter$1 transitionInteractor$filterRelevantKeyguardStateAnd$$inlined$filter$1 = new TransitionInteractor$filterRelevantKeyguardStateAnd$$inlined$filter$1(FlowKt.debounce(fromAodTransitionInteractor.powerInteractor.detailedWakefulness, 50L), fromAodTransitionInteractor, new FromAodTransitionInteractor$listenForAodToAwake$1$$ExternalSyntheticLambda0());
            FromAodTransitionInteractor fromAodTransitionInteractor2 = this.this$0;
            Flow sample = companion.sample(transitionInteractor$filterRelevantKeyguardStateAnd$$inlined$filter$1, fromAodTransitionInteractor2.transitionInteractor.startedKeyguardTransitionStep, fromAodTransitionInteractor2.wakeToGoneInteractor.canWakeDirectlyToGone);
            AnonymousClass2 anonymousClass2 = new AnonymousClass2(this.this$0);
            this.label = 1;
            if (sample.collect(anonymousClass2, this) == coroutineSingletons) {
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
