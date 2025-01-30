package com.android.systemui.keyguard.domain.interactor;

import android.animation.ValueAnimator;
import com.android.app.animation.Interpolators;
import com.android.systemui.keyguard.data.repository.KeyguardTransitionRepository;
import com.android.systemui.keyguard.data.repository.KeyguardTransitionRepositoryImpl;
import com.android.systemui.keyguard.shared.model.KeyguardState;
import com.android.systemui.keyguard.shared.model.TransitionInfo;
import com.android.systemui.keyguard.shared.model.TransitionStep;
import com.android.systemui.util.kotlin.FlowKt;
import com.android.systemui.util.kotlin.Utils;
import kotlin.Pair;
import kotlin.ResultKt;
import kotlin.Triple;
import kotlin.Unit;
import kotlin.coroutines.Continuation;
import kotlin.coroutines.intrinsics.CoroutineSingletons;
import kotlin.coroutines.jvm.internal.DebugMetadata;
import kotlin.coroutines.jvm.internal.SuspendLambda;
import kotlin.jvm.functions.Function2;
import kotlin.jvm.functions.Function3;
import kotlin.jvm.internal.AdaptedFunctionReference;
import kotlin.time.Duration;
import kotlinx.coroutines.CoroutineScope;
import kotlinx.coroutines.DelayKt;
import kotlinx.coroutines.flow.FlowCollector;
import kotlinx.coroutines.flow.FlowKt__TransformKt$onEach$$inlined$unsafeTransform$1;
import kotlinx.coroutines.flow.FlowKt__ZipKt$combine$$inlined$unsafeFlow$1;
import kotlinx.coroutines.flow.SafeFlow;

/* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
@DebugMetadata(m276c = "com.android.systemui.keyguard.domain.interactor.FromDreamingTransitionInteractor$listenForDreamingToOccluded$1", m277f = "FromDreamingTransitionInteractor.kt", m278l = {84}, m279m = "invokeSuspend")
/* loaded from: classes.dex */
final class FromDreamingTransitionInteractor$listenForDreamingToOccluded$1 extends SuspendLambda implements Function2 {
    int label;
    final /* synthetic */ FromDreamingTransitionInteractor this$0;

    /* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
    @DebugMetadata(m276c = "com.android.systemui.keyguard.domain.interactor.FromDreamingTransitionInteractor$listenForDreamingToOccluded$1$1", m277f = "FromDreamingTransitionInteractor.kt", m278l = {75}, m279m = "invokeSuspend")
    /* renamed from: com.android.systemui.keyguard.domain.interactor.FromDreamingTransitionInteractor$listenForDreamingToOccluded$1$1 */
    final class C16621 extends SuspendLambda implements Function2 {
        int label;

        public C16621(Continuation<? super C16621> continuation) {
            super(2, continuation);
        }

        @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
        public final Continuation create(Object obj, Continuation continuation) {
            return new C16621(continuation);
        }

        @Override // kotlin.jvm.functions.Function2
        public final Object invoke(Object obj, Object obj2) {
            ((Boolean) obj).booleanValue();
            return new C16621((Continuation) obj2).invokeSuspend(Unit.INSTANCE);
        }

        @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
        public final Object invokeSuspend(Object obj) {
            CoroutineSingletons coroutineSingletons = CoroutineSingletons.COROUTINE_SUSPENDED;
            int i = this.label;
            if (i == 0) {
                ResultKt.throwOnFailure(obj);
                this.label = 1;
                if (DelayKt.delay(50L, this) == coroutineSingletons) {
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

    /* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
    /* renamed from: com.android.systemui.keyguard.domain.interactor.FromDreamingTransitionInteractor$listenForDreamingToOccluded$1$3 */
    final /* synthetic */ class C16633 extends AdaptedFunctionReference implements Function3 {
        public static final C16633 INSTANCE = new C16633();

        public C16633() {
            super(3, Pair.class, "<init>", "<init>(Ljava/lang/Object;Ljava/lang/Object;)V", 4);
        }

        @Override // kotlin.jvm.functions.Function3
        public final Object invoke(Object obj, Object obj2, Object obj3) {
            return new Pair(Boolean.valueOf(((Boolean) obj).booleanValue()), (TransitionStep) obj2);
        }
    }

    /* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
    /* renamed from: com.android.systemui.keyguard.domain.interactor.FromDreamingTransitionInteractor$listenForDreamingToOccluded$1$4 */
    final /* synthetic */ class C16644 extends AdaptedFunctionReference implements Function3 {
        public C16644(Object obj) {
            super(3, obj, Utils.Companion.class, "toTriple", "toTriple(Ljava/lang/Object;Lkotlin/Pair;)Lkotlin/Triple;", 4);
        }

        @Override // kotlin.jvm.functions.Function3
        public final Object invoke(Object obj, Object obj2, Object obj3) {
            boolean booleanValue = ((Boolean) obj).booleanValue();
            Utils.Companion companion = (Utils.Companion) this.receiver;
            Boolean valueOf = Boolean.valueOf(booleanValue);
            companion.getClass();
            return Utils.Companion.toTriple(valueOf, (Pair) obj2);
        }
    }

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    public FromDreamingTransitionInteractor$listenForDreamingToOccluded$1(FromDreamingTransitionInteractor fromDreamingTransitionInteractor, Continuation<? super FromDreamingTransitionInteractor$listenForDreamingToOccluded$1> continuation) {
        super(2, continuation);
        this.this$0 = fromDreamingTransitionInteractor;
    }

    @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
    public final Continuation create(Object obj, Continuation continuation) {
        return new FromDreamingTransitionInteractor$listenForDreamingToOccluded$1(this.this$0, continuation);
    }

    @Override // kotlin.jvm.functions.Function2
    public final Object invoke(Object obj, Object obj2) {
        return ((FromDreamingTransitionInteractor$listenForDreamingToOccluded$1) create((CoroutineScope) obj, (Continuation) obj2)).invokeSuspend(Unit.INSTANCE);
    }

    @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
    public final Object invokeSuspend(Object obj) {
        CoroutineSingletons coroutineSingletons = CoroutineSingletons.COROUTINE_SUSPENDED;
        int i = this.label;
        if (i == 0) {
            ResultKt.throwOnFailure(obj);
            FlowKt__TransformKt$onEach$$inlined$unsafeTransform$1 flowKt__TransformKt$onEach$$inlined$unsafeTransform$1 = new FlowKt__TransformKt$onEach$$inlined$unsafeTransform$1(this.this$0.keyguardInteractor.isDreaming, new C16621(null));
            FromDreamingTransitionInteractor fromDreamingTransitionInteractor = this.this$0;
            SafeFlow sample = FlowKt.sample(flowKt__TransformKt$onEach$$inlined$unsafeTransform$1, new FlowKt__ZipKt$combine$$inlined$unsafeFlow$1(fromDreamingTransitionInteractor.keyguardInteractor.isKeyguardOccluded, fromDreamingTransitionInteractor.keyguardTransitionInteractor.startedKeyguardTransitionStep, C16633.INSTANCE), new C16644(Utils.Companion));
            final FromDreamingTransitionInteractor fromDreamingTransitionInteractor2 = this.this$0;
            FlowCollector flowCollector = new FlowCollector() { // from class: com.android.systemui.keyguard.domain.interactor.FromDreamingTransitionInteractor$listenForDreamingToOccluded$1.5
                @Override // kotlinx.coroutines.flow.FlowCollector
                public final Object emit(Object obj2, Continuation continuation) {
                    KeyguardState keyguardState;
                    Triple triple = (Triple) obj2;
                    boolean booleanValue = ((Boolean) triple.component1()).booleanValue();
                    boolean booleanValue2 = ((Boolean) triple.component2()).booleanValue();
                    TransitionStep transitionStep = (TransitionStep) triple.component3();
                    if (booleanValue2 && !booleanValue && ((keyguardState = transitionStep.f304to) == KeyguardState.DREAMING || keyguardState == KeyguardState.LOCKSCREEN)) {
                        FromDreamingTransitionInteractor fromDreamingTransitionInteractor3 = FromDreamingTransitionInteractor.this;
                        KeyguardTransitionRepository keyguardTransitionRepository = fromDreamingTransitionInteractor3.keyguardTransitionRepository;
                        KeyguardState keyguardState2 = KeyguardState.OCCLUDED;
                        ValueAnimator valueAnimator = new ValueAnimator();
                        valueAnimator.setInterpolator(Interpolators.LINEAR);
                        valueAnimator.setDuration(Duration.m2863getInWholeMillisecondsimpl(FromDreamingTransitionInteractor.DEFAULT_DURATION));
                        ((KeyguardTransitionRepositoryImpl) keyguardTransitionRepository).startTransition(new TransitionInfo(fromDreamingTransitionInteractor3.name, keyguardState, keyguardState2, valueAnimator), false);
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
