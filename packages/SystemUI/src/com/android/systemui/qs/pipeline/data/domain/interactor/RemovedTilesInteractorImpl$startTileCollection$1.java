package com.android.systemui.qs.pipeline.data.domain.interactor;

import com.android.systemui.user.data.repository.UserRepositoryImpl;
import com.android.systemui.user.data.repository.UserRepositoryImpl$special$$inlined$map$2;
import kotlin.ResultKt;
import kotlin.Unit;
import kotlin.coroutines.Continuation;
import kotlin.coroutines.intrinsics.CoroutineSingletons;
import kotlin.coroutines.jvm.internal.SuspendLambda;
import kotlin.jvm.functions.Function2;
import kotlinx.coroutines.CoroutineScope;
import kotlinx.coroutines.flow.FlowCollector;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes2.dex */
final class RemovedTilesInteractorImpl$startTileCollection$1 extends SuspendLambda implements Function2 {
    int label;
    final /* synthetic */ RemovedTilesInteractorImpl this$0;

    /* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
    /* renamed from: com.android.systemui.qs.pipeline.data.domain.interactor.RemovedTilesInteractorImpl$startTileCollection$1$1, reason: invalid class name */
    public final class AnonymousClass1 implements FlowCollector {
        public final /* synthetic */ RemovedTilesInteractorImpl this$0;

        public AnonymousClass1(RemovedTilesInteractorImpl removedTilesInteractorImpl) {
            this.this$0 = removedTilesInteractorImpl;
        }

        /* JADX WARN: Removed duplicated region for block: B:16:0x0033  */
        /* JADX WARN: Removed duplicated region for block: B:8:0x0021  */
        @Override // kotlinx.coroutines.flow.FlowCollector
        /*
            Code decompiled incorrectly, please refer to instructions dump.
            To view partially-correct code enable 'Show inconsistent code' option in preferences
        */
        public final java.lang.Object emit(android.content.pm.UserInfo r6, kotlin.coroutines.Continuation r7) {
            /*
                r5 = this;
                boolean r0 = r7 instanceof com.android.systemui.qs.pipeline.data.domain.interactor.RemovedTilesInteractorImpl$startTileCollection$1$1$emit$1
                if (r0 == 0) goto L13
                r0 = r7
                com.android.systemui.qs.pipeline.data.domain.interactor.RemovedTilesInteractorImpl$startTileCollection$1$1$emit$1 r0 = (com.android.systemui.qs.pipeline.data.domain.interactor.RemovedTilesInteractorImpl$startTileCollection$1$1$emit$1) r0
                int r1 = r0.label
                r2 = -2147483648(0xffffffff80000000, float:-0.0)
                r3 = r1 & r2
                if (r3 == 0) goto L13
                int r1 = r1 - r2
                r0.label = r1
                goto L18
            L13:
                com.android.systemui.qs.pipeline.data.domain.interactor.RemovedTilesInteractorImpl$startTileCollection$1$1$emit$1 r0 = new com.android.systemui.qs.pipeline.data.domain.interactor.RemovedTilesInteractorImpl$startTileCollection$1$1$emit$1
                r0.<init>(r5, r7)
            L18:
                java.lang.Object r7 = r0.result
                kotlin.coroutines.intrinsics.CoroutineSingletons r1 = kotlin.coroutines.intrinsics.CoroutineSingletons.COROUTINE_SUSPENDED
                int r2 = r0.label
                r3 = 1
                if (r2 == 0) goto L33
                if (r2 != r3) goto L2b
                java.lang.Object r5 = r0.L$0
                kotlinx.coroutines.flow.MutableStateFlow r5 = (kotlinx.coroutines.flow.MutableStateFlow) r5
                kotlin.ResultKt.throwOnFailure(r7)
                goto L54
            L2b:
                java.lang.IllegalStateException r5 = new java.lang.IllegalStateException
                java.lang.String r6 = "call to 'resume' before 'invoke' with coroutine"
                r5.<init>(r6)
                throw r5
            L33:
                kotlin.ResultKt.throwOnFailure(r7)
                com.android.systemui.qs.pipeline.data.domain.interactor.RemovedTilesInteractorImpl r5 = r5.this$0
                int r7 = r5.userId
                int r6 = r6.id
                if (r7 == r6) goto L6d
                r5.userId = r6
                kotlinx.coroutines.flow.StateFlowImpl r7 = r5._removedTiles
                r0.L$0 = r7
                r0.label = r3
                com.android.systemui.qs.pipeline.data.repository.RemovedTileSpecRepository r5 = r5.removedTileSpecRepository
                com.android.systemui.qs.pipeline.data.repository.RemovedTileSpecRepositoryImpl r5 = (com.android.systemui.qs.pipeline.data.repository.RemovedTileSpecRepositoryImpl) r5
                java.lang.Object r5 = r5.loadTilesFromSettings(r6, r0)
                if (r5 != r1) goto L51
                return r1
            L51:
                r4 = r7
                r7 = r5
                r5 = r4
            L54:
                r6 = r7
                java.util.List r6 = (java.util.List) r6
                java.lang.StringBuilder r0 = new java.lang.StringBuilder
                java.lang.String r1 = "loadRemovedTiles  "
                r0.<init>(r1)
                r0.append(r6)
                java.lang.String r6 = r0.toString()
                java.lang.String r0 = "RemovedTilesInteractor"
                android.util.Log.d(r0, r6)
                r5.setValue(r7)
            L6d:
                kotlin.Unit r5 = kotlin.Unit.INSTANCE
                return r5
            */
            throw new UnsupportedOperationException("Method not decompiled: com.android.systemui.qs.pipeline.data.domain.interactor.RemovedTilesInteractorImpl$startTileCollection$1.AnonymousClass1.emit(android.content.pm.UserInfo, kotlin.coroutines.Continuation):java.lang.Object");
        }
    }

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    public RemovedTilesInteractorImpl$startTileCollection$1(RemovedTilesInteractorImpl removedTilesInteractorImpl, Continuation continuation) {
        super(2, continuation);
        this.this$0 = removedTilesInteractorImpl;
    }

    @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
    public final Continuation create(Object obj, Continuation continuation) {
        return new RemovedTilesInteractorImpl$startTileCollection$1(this.this$0, continuation);
    }

    @Override // kotlin.jvm.functions.Function2
    public final Object invoke(Object obj, Object obj2) {
        return ((RemovedTilesInteractorImpl$startTileCollection$1) create((CoroutineScope) obj, (Continuation) obj2)).invokeSuspend(Unit.INSTANCE);
    }

    @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
    public final Object invokeSuspend(Object obj) {
        CoroutineSingletons coroutineSingletons = CoroutineSingletons.COROUTINE_SUSPENDED;
        int i = this.label;
        if (i == 0) {
            ResultKt.throwOnFailure(obj);
            RemovedTilesInteractorImpl removedTilesInteractorImpl = this.this$0;
            UserRepositoryImpl$special$$inlined$map$2 userRepositoryImpl$special$$inlined$map$2 = ((UserRepositoryImpl) removedTilesInteractorImpl.userRepository).selectedUserInfo;
            AnonymousClass1 anonymousClass1 = new AnonymousClass1(removedTilesInteractorImpl);
            this.label = 1;
            if (userRepositoryImpl$special$$inlined$map$2.collect(anonymousClass1, this) == coroutineSingletons) {
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
