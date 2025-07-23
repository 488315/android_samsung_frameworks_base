package com.android.systemui.statusbar.data.repository;

import com.android.systemui.display.data.repository.DisplayRepositoryImpl;
import com.android.systemui.util.kotlin.FlowKt;
import java.util.Set;
import kotlin.ResultKt;
import kotlin.Unit;
import kotlin.collections.SetsKt___SetsKt;
import kotlin.coroutines.Continuation;
import kotlin.coroutines.intrinsics.CoroutineSingletons;
import kotlin.coroutines.jvm.internal.SuspendLambda;
import kotlin.jvm.functions.Function2;
import kotlin.jvm.functions.Function3;
import kotlinx.coroutines.CoroutineScope;
import kotlinx.coroutines.flow.Flow;
import kotlinx.coroutines.flow.FlowCollector;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes3.dex */
final class StatusBarPerDisplayStoreImpl$start$1 extends SuspendLambda implements Function2 {
    int label;
    final /* synthetic */ StatusBarPerDisplayStoreImpl this$0;

    /* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
    /* renamed from: com.android.systemui.statusbar.data.repository.StatusBarPerDisplayStoreImpl$start$1$1, reason: invalid class name */
    final class AnonymousClass1 extends SuspendLambda implements Function3 {
        /* synthetic */ Object L$0;
        /* synthetic */ Object L$1;
        int label;

        public AnonymousClass1(Continuation continuation) {
            super(3, continuation);
        }

        @Override // kotlin.jvm.functions.Function3
        public final Object invoke(Object obj, Object obj2, Object obj3) {
            AnonymousClass1 anonymousClass1 = new AnonymousClass1((Continuation) obj3);
            anonymousClass1.L$0 = (Set) obj;
            anonymousClass1.L$1 = (Set) obj2;
            return anonymousClass1.invokeSuspend(Unit.INSTANCE);
        }

        @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
        public final Object invokeSuspend(Object obj) {
            CoroutineSingletons coroutineSingletons = CoroutineSingletons.COROUTINE_SUSPENDED;
            if (this.label != 0) {
                throw new IllegalStateException("call to 'resume' before 'invoke' with coroutine");
            }
            ResultKt.throwOnFailure(obj);
            return SetsKt___SetsKt.minus((Set) this.L$0, (Iterable) this.L$1);
        }
    }

    /* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
    /* renamed from: com.android.systemui.statusbar.data.repository.StatusBarPerDisplayStoreImpl$start$1$2, reason: invalid class name */
    public final class AnonymousClass2 implements FlowCollector {
        public final /* synthetic */ StatusBarPerDisplayStoreImpl this$0;

        public AnonymousClass2(StatusBarPerDisplayStoreImpl statusBarPerDisplayStoreImpl) {
            this.this$0 = statusBarPerDisplayStoreImpl;
        }

        /* JADX WARN: Removed duplicated region for block: B:13:0x004b  */
        /* JADX WARN: Removed duplicated region for block: B:29:0x0037  */
        /* JADX WARN: Removed duplicated region for block: B:8:0x0021  */
        @Override // kotlinx.coroutines.flow.FlowCollector
        /*
            Code decompiled incorrectly, please refer to instructions dump.
            To view partially-correct code enable 'Show inconsistent code' option in preferences
        */
        public final java.lang.Object emit(java.util.Set r7, kotlin.coroutines.Continuation r8) {
            /*
                r6 = this;
                boolean r0 = r8 instanceof com.android.systemui.statusbar.data.repository.StatusBarPerDisplayStoreImpl$start$1$2$emit$1
                if (r0 == 0) goto L13
                r0 = r8
                com.android.systemui.statusbar.data.repository.StatusBarPerDisplayStoreImpl$start$1$2$emit$1 r0 = (com.android.systemui.statusbar.data.repository.StatusBarPerDisplayStoreImpl$start$1$2$emit$1) r0
                int r1 = r0.label
                r2 = -2147483648(0xffffffff80000000, float:-0.0)
                r3 = r1 & r2
                if (r3 == 0) goto L13
                int r1 = r1 - r2
                r0.label = r1
                goto L18
            L13:
                com.android.systemui.statusbar.data.repository.StatusBarPerDisplayStoreImpl$start$1$2$emit$1 r0 = new com.android.systemui.statusbar.data.repository.StatusBarPerDisplayStoreImpl$start$1$2$emit$1
                r0.<init>(r6, r8)
            L18:
                java.lang.Object r8 = r0.result
                kotlin.coroutines.intrinsics.CoroutineSingletons r1 = kotlin.coroutines.intrinsics.CoroutineSingletons.COROUTINE_SUSPENDED
                int r2 = r0.label
                r3 = 1
                if (r2 == 0) goto L37
                if (r2 != r3) goto L2f
                java.lang.Object r6 = r0.L$1
                java.util.Iterator r6 = (java.util.Iterator) r6
                java.lang.Object r7 = r0.L$0
                com.android.systemui.statusbar.data.repository.StatusBarPerDisplayStoreImpl r7 = (com.android.systemui.statusbar.data.repository.StatusBarPerDisplayStoreImpl) r7
                kotlin.ResultKt.throwOnFailure(r8)
                goto L45
            L2f:
                java.lang.IllegalStateException r6 = new java.lang.IllegalStateException
                java.lang.String r7 = "call to 'resume' before 'invoke' with coroutine"
                r6.<init>(r7)
                throw r6
            L37:
                kotlin.ResultKt.throwOnFailure(r8)
                java.lang.Iterable r7 = (java.lang.Iterable) r7
                java.util.Iterator r7 = r7.iterator()
                com.android.systemui.statusbar.data.repository.StatusBarPerDisplayStoreImpl r6 = r6.this$0
                r5 = r7
                r7 = r6
                r6 = r5
            L45:
                boolean r8 = r6.hasNext()
                if (r8 == 0) goto L71
                java.lang.Object r8 = r6.next()
                java.lang.Number r8 = (java.lang.Number) r8
                int r8 = r8.intValue()
                int r2 = com.android.systemui.statusbar.data.repository.StatusBarPerDisplayStoreImpl.$r8$clinit
                java.util.concurrent.ConcurrentHashMap r2 = r7.perDisplayInstances
                java.lang.Integer r4 = new java.lang.Integer
                r4.<init>(r8)
                java.lang.Object r8 = r2.remove(r4)
                if (r8 == 0) goto L45
                r0.L$0 = r7
                r0.L$1 = r6
                r0.label = r3
                java.lang.Object r8 = r7.onDisplayRemovalAction(r8)
                if (r8 != r1) goto L45
                return r1
            L71:
                kotlin.Unit r6 = kotlin.Unit.INSTANCE
                return r6
            */
            throw new UnsupportedOperationException("Method not decompiled: com.android.systemui.statusbar.data.repository.StatusBarPerDisplayStoreImpl$start$1.AnonymousClass2.emit(java.util.Set, kotlin.coroutines.Continuation):java.lang.Object");
        }
    }

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    public StatusBarPerDisplayStoreImpl$start$1(StatusBarPerDisplayStoreImpl statusBarPerDisplayStoreImpl, Continuation continuation) {
        super(2, continuation);
        this.this$0 = statusBarPerDisplayStoreImpl;
    }

    @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
    public final Continuation create(Object obj, Continuation continuation) {
        return new StatusBarPerDisplayStoreImpl$start$1(this.this$0, continuation);
    }

    @Override // kotlin.jvm.functions.Function2
    public final Object invoke(Object obj, Object obj2) {
        return ((StatusBarPerDisplayStoreImpl$start$1) create((CoroutineScope) obj, (Continuation) obj2)).invokeSuspend(Unit.INSTANCE);
    }

    @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
    public final Object invokeSuspend(Object obj) {
        CoroutineSingletons coroutineSingletons = CoroutineSingletons.COROUTINE_SUSPENDED;
        int i = this.label;
        if (i == 0) {
            ResultKt.throwOnFailure(obj);
            Flow pairwiseBy = FlowKt.pairwiseBy(((DisplayRepositoryImpl) this.this$0.displayRepository).displaysWithDecorationsRepositoryImpl.getDisplayIdsWithSystemDecorations(), new AnonymousClass1(null));
            AnonymousClass2 anonymousClass2 = new AnonymousClass2(this.this$0);
            this.label = 1;
            if (pairwiseBy.collect(anonymousClass2, this) == coroutineSingletons) {
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
