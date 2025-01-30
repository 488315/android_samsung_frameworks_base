package com.android.systemui.keyguard.domain.interactor;

import com.android.systemui.keyguard.data.repository.KeyguardTransitionRepositoryImpl;
import com.android.systemui.keyguard.shared.model.KeyguardState;
import com.android.systemui.keyguard.shared.model.TransitionInfo;
import com.android.systemui.keyguard.shared.model.TransitionStep;
import com.android.systemui.util.kotlin.FlowKt;
import kotlin.Pair;
import kotlin.ResultKt;
import kotlin.Unit;
import kotlin.coroutines.Continuation;
import kotlin.coroutines.intrinsics.CoroutineSingletons;
import kotlin.coroutines.jvm.internal.DebugMetadata;
import kotlin.coroutines.jvm.internal.SuspendLambda;
import kotlin.jvm.functions.Function2;
import kotlin.jvm.functions.Function3;
import kotlin.jvm.internal.AdaptedFunctionReference;
import kotlinx.coroutines.CoroutineScope;
import kotlinx.coroutines.flow.FlowCollector;
import kotlinx.coroutines.flow.SafeFlow;

/* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
@DebugMetadata(m276c = "com.android.systemui.keyguard.domain.interactor.FromAlternateBouncerTransitionInteractor$listenForAlternateBouncerToPrimaryBouncer$1", m277f = "FromAlternateBouncerTransitionInteractor.kt", m278l = {131}, m279m = "invokeSuspend")
/* renamed from: com.android.systemui.keyguard.domain.interactor.FromAlternateBouncerTransitionInteractor$listenForAlternateBouncerToPrimaryBouncer$1 */
/* loaded from: classes.dex */
final class C1650xd02f0f46 extends SuspendLambda implements Function2 {
    int label;
    final /* synthetic */ FromAlternateBouncerTransitionInteractor this$0;

    /* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
    /* renamed from: com.android.systemui.keyguard.domain.interactor.FromAlternateBouncerTransitionInteractor$listenForAlternateBouncerToPrimaryBouncer$1$2, reason: invalid class name */
    final /* synthetic */ class AnonymousClass2 extends AdaptedFunctionReference implements Function3 {
        public static final AnonymousClass2 INSTANCE = new AnonymousClass2();

        public AnonymousClass2() {
            super(3, Pair.class, "<init>", "<init>(Ljava/lang/Object;Ljava/lang/Object;)V", 4);
        }

        @Override // kotlin.jvm.functions.Function3
        public final Object invoke(Object obj, Object obj2, Object obj3) {
            return new Pair(Boolean.valueOf(((Boolean) obj).booleanValue()), (TransitionStep) obj2);
        }
    }

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    public C1650xd02f0f46(FromAlternateBouncerTransitionInteractor fromAlternateBouncerTransitionInteractor, Continuation<? super C1650xd02f0f46> continuation) {
        super(2, continuation);
        this.this$0 = fromAlternateBouncerTransitionInteractor;
    }

    @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
    public final Continuation create(Object obj, Continuation continuation) {
        return new C1650xd02f0f46(this.this$0, continuation);
    }

    @Override // kotlin.jvm.functions.Function2
    public final Object invoke(Object obj, Object obj2) {
        return ((C1650xd02f0f46) create((CoroutineScope) obj, (Continuation) obj2)).invokeSuspend(Unit.INSTANCE);
    }

    @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
    public final Object invokeSuspend(Object obj) {
        CoroutineSingletons coroutineSingletons = CoroutineSingletons.COROUTINE_SUSPENDED;
        int i = this.label;
        if (i == 0) {
            ResultKt.throwOnFailure(obj);
            FromAlternateBouncerTransitionInteractor fromAlternateBouncerTransitionInteractor = this.this$0;
            SafeFlow sample = FlowKt.sample(fromAlternateBouncerTransitionInteractor.keyguardInteractor.primaryBouncerShowing, fromAlternateBouncerTransitionInteractor.keyguardTransitionInteractor.startedKeyguardTransitionStep, AnonymousClass2.INSTANCE);
            final FromAlternateBouncerTransitionInteractor fromAlternateBouncerTransitionInteractor2 = this.this$0;
            FlowCollector flowCollector = new FlowCollector() { // from class: com.android.systemui.keyguard.domain.interactor.FromAlternateBouncerTransitionInteractor$listenForAlternateBouncerToPrimaryBouncer$1.3
                @Override // kotlinx.coroutines.flow.FlowCollector
                public final Object emit(Object obj2, Continuation continuation) {
                    Pair pair = (Pair) obj2;
                    boolean booleanValue = ((Boolean) pair.component1()).booleanValue();
                    TransitionStep transitionStep = (TransitionStep) pair.component2();
                    if (booleanValue) {
                        KeyguardState keyguardState = transitionStep.f304to;
                        KeyguardState keyguardState2 = KeyguardState.ALTERNATE_BOUNCER;
                        if (keyguardState == keyguardState2) {
                            FromAlternateBouncerTransitionInteractor fromAlternateBouncerTransitionInteractor3 = FromAlternateBouncerTransitionInteractor.this;
                            ((KeyguardTransitionRepositoryImpl) fromAlternateBouncerTransitionInteractor3.keyguardTransitionRepository).startTransition(new TransitionInfo(fromAlternateBouncerTransitionInteractor3.name, keyguardState2, KeyguardState.PRIMARY_BOUNCER, FromAlternateBouncerTransitionInteractor.access$getAnimator(fromAlternateBouncerTransitionInteractor3)), false);
                        }
                    }
                    return Unit.INSTANCE;
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
