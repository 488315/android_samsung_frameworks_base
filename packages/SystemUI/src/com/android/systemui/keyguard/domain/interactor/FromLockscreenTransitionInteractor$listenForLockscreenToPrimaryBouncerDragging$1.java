package com.android.systemui.keyguard.domain.interactor;

import com.android.systemui.shade.data.repository.ShadeRepositoryImpl;
import java.util.UUID;
import kotlin.KotlinNothingValueException;
import kotlin.ResultKt;
import kotlin.Unit;
import kotlin.coroutines.Continuation;
import kotlin.coroutines.intrinsics.CoroutineSingletons;
import kotlin.coroutines.jvm.internal.SuspendLambda;
import kotlin.jvm.functions.Function2;
import kotlin.jvm.internal.Ref$ObjectRef;
import kotlinx.coroutines.CoroutineScope;
import kotlinx.coroutines.flow.FlowCollector;
import kotlinx.coroutines.flow.ReadonlyStateFlow;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes2.dex */
final class FromLockscreenTransitionInteractor$listenForLockscreenToPrimaryBouncerDragging$1 extends SuspendLambda implements Function2 {
    final /* synthetic */ Ref$ObjectRef<UUID> $transitionId;
    int label;
    final /* synthetic */ FromLockscreenTransitionInteractor this$0;

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    public FromLockscreenTransitionInteractor$listenForLockscreenToPrimaryBouncerDragging$1(FromLockscreenTransitionInteractor fromLockscreenTransitionInteractor, Ref$ObjectRef<UUID> ref$ObjectRef, Continuation continuation) {
        super(2, continuation);
        this.this$0 = fromLockscreenTransitionInteractor;
        this.$transitionId = ref$ObjectRef;
    }

    @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
    public final Continuation create(Object obj, Continuation continuation) {
        return new FromLockscreenTransitionInteractor$listenForLockscreenToPrimaryBouncerDragging$1(this.this$0, this.$transitionId, continuation);
    }

    @Override // kotlin.jvm.functions.Function2
    public final Object invoke(Object obj, Object obj2) {
        return ((FromLockscreenTransitionInteractor$listenForLockscreenToPrimaryBouncerDragging$1) create((CoroutineScope) obj, (Continuation) obj2)).invokeSuspend(Unit.INSTANCE);
    }

    @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
    public final Object invokeSuspend(Object obj) {
        CoroutineSingletons coroutineSingletons = CoroutineSingletons.COROUTINE_SUSPENDED;
        int i = this.label;
        if (i == 0) {
            ResultKt.throwOnFailure(obj);
            FromLockscreenTransitionInteractor fromLockscreenTransitionInteractor = this.this$0;
            ReadonlyStateFlow readonlyStateFlow = ((ShadeRepositoryImpl) fromLockscreenTransitionInteractor.shadeRepository).legacyShadeExpansion;
            AnonymousClass1 anonymousClass1 = new AnonymousClass1(fromLockscreenTransitionInteractor, this.$transitionId);
            this.label = 1;
            if (readonlyStateFlow.$$delegate_0.collect(anonymousClass1, this) == coroutineSingletons) {
                return coroutineSingletons;
            }
        } else {
            if (i != 1) {
                throw new IllegalStateException("call to 'resume' before 'invoke' with coroutine");
            }
            ResultKt.throwOnFailure(obj);
        }
        throw new KotlinNothingValueException();
    }

    /* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
    /* renamed from: com.android.systemui.keyguard.domain.interactor.FromLockscreenTransitionInteractor$listenForLockscreenToPrimaryBouncerDragging$1$1, reason: invalid class name */
    public final class AnonymousClass1 implements FlowCollector {
        public final /* synthetic */ Ref$ObjectRef $transitionId;
        public final /* synthetic */ FromLockscreenTransitionInteractor this$0;

        public AnonymousClass1(FromLockscreenTransitionInteractor fromLockscreenTransitionInteractor, Ref$ObjectRef<UUID> ref$ObjectRef) {
            this.this$0 = fromLockscreenTransitionInteractor;
            this.$transitionId = ref$ObjectRef;
        }

        /* JADX WARN: Code restructure failed: missing block: B:34:0x0123, code lost:
        
            if (((com.android.systemui.keyguard.data.repository.KeyguardTransitionRepositoryImpl) r4).startTransition(r10, r9) == r3) goto L66;
         */
        /* JADX WARN: Multi-variable type inference failed */
        /* JADX WARN: Removed duplicated region for block: B:29:0x00e7  */
        /* JADX WARN: Removed duplicated region for block: B:38:0x0058  */
        /* JADX WARN: Removed duplicated region for block: B:9:0x002b  */
        /*
            Code decompiled incorrectly, please refer to instructions dump.
            To view partially-correct code enable 'Show inconsistent code' option in preferences
        */
        public final java.lang.Object emit(float r17, kotlin.coroutines.Continuation r18) {
            /*
                Method dump skipped, instructions count: 364
                To view this dump change 'Code comments level' option to 'DEBUG'
            */
            throw new UnsupportedOperationException("Method not decompiled: com.android.systemui.keyguard.domain.interactor.FromLockscreenTransitionInteractor$listenForLockscreenToPrimaryBouncerDragging$1.AnonymousClass1.emit(float, kotlin.coroutines.Continuation):java.lang.Object");
        }

        @Override // kotlinx.coroutines.flow.FlowCollector
        public final /* bridge */ /* synthetic */ Object emit(Object obj, Continuation continuation) {
            return emit(((Number) obj).floatValue(), continuation);
        }
    }
}
