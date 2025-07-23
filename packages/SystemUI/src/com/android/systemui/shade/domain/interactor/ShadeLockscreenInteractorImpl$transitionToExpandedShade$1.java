package com.android.systemui.shade.domain.interactor;

import kotlin.ResultKt;
import kotlin.Unit;
import kotlin.coroutines.Continuation;
import kotlin.coroutines.intrinsics.CoroutineSingletons;
import kotlin.coroutines.jvm.internal.SuspendLambda;
import kotlin.jvm.functions.Function2;
import kotlinx.coroutines.CoroutineScope;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes3.dex */
final class ShadeLockscreenInteractorImpl$transitionToExpandedShade$1 extends SuspendLambda implements Function2 {
    final /* synthetic */ long $delay;
    int label;
    final /* synthetic */ ShadeLockscreenInteractorImpl this$0;

    /* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
    /* renamed from: com.android.systemui.shade.domain.interactor.ShadeLockscreenInteractorImpl$transitionToExpandedShade$1$1, reason: invalid class name */
    final class AnonymousClass1 extends SuspendLambda implements Function2 {
        int label;
        final /* synthetic */ ShadeLockscreenInteractorImpl this$0;

        /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
        public AnonymousClass1(ShadeLockscreenInteractorImpl shadeLockscreenInteractorImpl, Continuation continuation) {
            super(2, continuation);
            this.this$0 = shadeLockscreenInteractorImpl;
        }

        @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
        public final Continuation create(Object obj, Continuation continuation) {
            return new AnonymousClass1(this.this$0, continuation);
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
            ((ShadeInteractorImpl) this.this$0.shadeInteractor).expandNotificationsShade("ShadeLockscreenInteractorImpl.transitionToExpandedShade");
            return Unit.INSTANCE;
        }
    }

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    public ShadeLockscreenInteractorImpl$transitionToExpandedShade$1(long j, ShadeLockscreenInteractorImpl shadeLockscreenInteractorImpl, Continuation continuation) {
        super(2, continuation);
        this.$delay = j;
        this.this$0 = shadeLockscreenInteractorImpl;
    }

    @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
    public final Continuation create(Object obj, Continuation continuation) {
        return new ShadeLockscreenInteractorImpl$transitionToExpandedShade$1(this.$delay, this.this$0, continuation);
    }

    @Override // kotlin.jvm.functions.Function2
    public final Object invoke(Object obj, Object obj2) {
        return ((ShadeLockscreenInteractorImpl$transitionToExpandedShade$1) create((CoroutineScope) obj, (Continuation) obj2)).invokeSuspend(Unit.INSTANCE);
    }

    /* JADX WARN: Code restructure failed: missing block: B:13:0x003a, code lost:
    
        if (kotlinx.coroutines.BuildersKt.withContext(r1, r3, r6) == r0) goto L15;
     */
    /* JADX WARN: Code restructure failed: missing block: B:14:0x003c, code lost:
    
        return r0;
     */
    /* JADX WARN: Code restructure failed: missing block: B:16:0x0027, code lost:
    
        if (kotlinx.coroutines.DelayKt.delay(r4, r6) == r0) goto L15;
     */
    @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public final java.lang.Object invokeSuspend(java.lang.Object r7) {
        /*
            r6 = this;
            kotlin.coroutines.intrinsics.CoroutineSingletons r0 = kotlin.coroutines.intrinsics.CoroutineSingletons.COROUTINE_SUSPENDED
            int r1 = r6.label
            r2 = 2
            r3 = 1
            if (r1 == 0) goto L1c
            if (r1 == r3) goto L18
            if (r1 != r2) goto L10
            kotlin.ResultKt.throwOnFailure(r7)
            goto L3d
        L10:
            java.lang.IllegalStateException r6 = new java.lang.IllegalStateException
            java.lang.String r7 = "call to 'resume' before 'invoke' with coroutine"
            r6.<init>(r7)
            throw r6
        L18:
            kotlin.ResultKt.throwOnFailure(r7)
            goto L2a
        L1c:
            kotlin.ResultKt.throwOnFailure(r7)
            long r4 = r6.$delay
            r6.label = r3
            java.lang.Object r7 = kotlinx.coroutines.DelayKt.delay(r4, r6)
            if (r7 != r0) goto L2a
            goto L3c
        L2a:
            com.android.systemui.shade.domain.interactor.ShadeLockscreenInteractorImpl r7 = r6.this$0
            kotlinx.coroutines.CoroutineDispatcher r1 = r7.mainDispatcher
            com.android.systemui.shade.domain.interactor.ShadeLockscreenInteractorImpl$transitionToExpandedShade$1$1 r3 = new com.android.systemui.shade.domain.interactor.ShadeLockscreenInteractorImpl$transitionToExpandedShade$1$1
            r4 = 0
            r3.<init>(r7, r4)
            r6.label = r2
            java.lang.Object r6 = kotlinx.coroutines.BuildersKt.withContext(r1, r3, r6)
            if (r6 != r0) goto L3d
        L3c:
            return r0
        L3d:
            kotlin.Unit r6 = kotlin.Unit.INSTANCE
            return r6
        */
        throw new UnsupportedOperationException("Method not decompiled: com.android.systemui.shade.domain.interactor.ShadeLockscreenInteractorImpl$transitionToExpandedShade$1.invokeSuspend(java.lang.Object):java.lang.Object");
    }
}
