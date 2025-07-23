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
final class FromDozingTransitionInteractor$listenForDozingToAny$1 extends SuspendLambda implements Function2 {
    int label;
    final /* synthetic */ FromDozingTransitionInteractor this$0;

    /* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
    /* renamed from: com.android.systemui.keyguard.domain.interactor.FromDozingTransitionInteractor$listenForDozingToAny$1$2, reason: invalid class name */
    public final class AnonymousClass2 implements FlowCollector {
        public final /* synthetic */ FromDozingTransitionInteractor this$0;

        public AnonymousClass2(FromDozingTransitionInteractor fromDozingTransitionInteractor) {
            this.this$0 = fromDozingTransitionInteractor;
        }

        /* JADX WARN: Code restructure failed: missing block: B:31:0x00e3, code lost:
        
            if (com.android.systemui.keyguard.domain.interactor.TransitionInteractor.startTransitionTo$default(r1, r2, null, null, "lockscreen not enabled", r6, 6) == r0) goto L64;
         */
        /* JADX WARN: Code restructure failed: missing block: B:38:0x010f, code lost:
        
            if (com.android.systemui.keyguard.domain.interactor.TransitionInteractor.startTransitionTo$default(r13.this$0, r2, null, null, null, r6, 14) == r0) goto L64;
         */
        /* JADX WARN: Code restructure failed: missing block: B:41:0x012c, code lost:
        
            if (com.android.systemui.keyguard.domain.interactor.TransitionInteractor.startTransitionTo$default(r13.this$0, r2, null, null, null, r6, 14) == r0) goto L64;
         */
        /* JADX WARN: Code restructure failed: missing block: B:49:0x0166, code lost:
        
            if (com.android.systemui.keyguard.domain.interactor.TransitionInteractor.startTransitionTo$default(r13.this$0, r2, null, null, null, r6, 14) == r0) goto L64;
         */
        /* JADX WARN: Code restructure failed: missing block: B:55:0x0189, code lost:
        
            if (com.android.systemui.keyguard.domain.interactor.TransitionInteractor.startTransitionTo$default(r1, r2, null, null, r5, r6, 6) == r0) goto L64;
         */
        /* JADX WARN: Removed duplicated region for block: B:12:0x002b  */
        /* JADX WARN: Removed duplicated region for block: B:15:0x0030  */
        /* JADX WARN: Removed duplicated region for block: B:18:0x0035  */
        /* JADX WARN: Removed duplicated region for block: B:21:0x003a  */
        /* JADX WARN: Removed duplicated region for block: B:24:0x003f  */
        /* JADX WARN: Removed duplicated region for block: B:27:0x0044  */
        /* JADX WARN: Removed duplicated region for block: B:30:0x00cf  */
        /* JADX WARN: Removed duplicated region for block: B:33:0x00ea  */
        /* JADX WARN: Removed duplicated region for block: B:57:0x005a  */
        /* JADX WARN: Removed duplicated region for block: B:9:0x0023  */
        @Override // kotlinx.coroutines.flow.FlowCollector
        /*
            Code decompiled incorrectly, please refer to instructions dump.
            To view partially-correct code enable 'Show inconsistent code' option in preferences
        */
        public final java.lang.Object emit(kotlin.Triple r14, kotlin.coroutines.Continuation r15) {
            /*
                Method dump skipped, instructions count: 418
                To view this dump change 'Code comments level' option to 'DEBUG'
            */
            throw new UnsupportedOperationException("Method not decompiled: com.android.systemui.keyguard.domain.interactor.FromDozingTransitionInteractor$listenForDozingToAny$1.AnonymousClass2.emit(kotlin.Triple, kotlin.coroutines.Continuation):java.lang.Object");
        }
    }

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    public FromDozingTransitionInteractor$listenForDozingToAny$1(FromDozingTransitionInteractor fromDozingTransitionInteractor, Continuation continuation) {
        super(2, continuation);
        this.this$0 = fromDozingTransitionInteractor;
    }

    @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
    public final Continuation create(Object obj, Continuation continuation) {
        return new FromDozingTransitionInteractor$listenForDozingToAny$1(this.this$0, continuation);
    }

    @Override // kotlin.jvm.functions.Function2
    public final Object invoke(Object obj, Object obj2) {
        return ((FromDozingTransitionInteractor$listenForDozingToAny$1) create((CoroutineScope) obj, (Continuation) obj2)).invokeSuspend(Unit.INSTANCE);
    }

    @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
    public final Object invokeSuspend(Object obj) {
        CoroutineSingletons coroutineSingletons = CoroutineSingletons.COROUTINE_SUSPENDED;
        int i = this.label;
        if (i == 0) {
            ResultKt.throwOnFailure(obj);
            Utils.Companion companion = Utils.Companion;
            FromDozingTransitionInteractor fromDozingTransitionInteractor = this.this$0;
            Flow sample = companion.sample(new TransitionInteractor$filterRelevantKeyguardStateAnd$$inlined$filter$1(FlowKt.debounce(fromDozingTransitionInteractor.powerInteractor.detailedWakefulness, 50L), fromDozingTransitionInteractor, new FromDozingTransitionInteractor$listenForDozingToAny$1$$ExternalSyntheticLambda0()), this.this$0.communalInteractor.isCommunalAvailable(), this.this$0.communalSettingsInteractor.autoOpenEnabled);
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
