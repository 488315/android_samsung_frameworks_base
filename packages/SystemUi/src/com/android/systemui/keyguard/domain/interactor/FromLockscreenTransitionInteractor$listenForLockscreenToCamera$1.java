package com.android.systemui.keyguard.domain.interactor;

import com.android.keyguard.KeyguardUpdateMonitor;
import com.android.systemui.Dependency;
import com.android.systemui.LsRune;
import com.android.systemui.keyguard.data.repository.KeyguardTransitionRepository;
import com.android.systemui.keyguard.data.repository.KeyguardTransitionRepositoryImpl;
import com.android.systemui.keyguard.shared.model.CameraLaunchSourceModel;
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
@DebugMetadata(m276c = "com.android.systemui.keyguard.domain.interactor.FromLockscreenTransitionInteractor$listenForLockscreenToCamera$1", m277f = "FromLockscreenTransitionInteractor.kt", m278l = {270}, m279m = "invokeSuspend")
/* loaded from: classes.dex */
final class FromLockscreenTransitionInteractor$listenForLockscreenToCamera$1 extends SuspendLambda implements Function2 {
    int label;
    final /* synthetic */ FromLockscreenTransitionInteractor this$0;

    /* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
    /* renamed from: com.android.systemui.keyguard.domain.interactor.FromLockscreenTransitionInteractor$listenForLockscreenToCamera$1$2 */
    final /* synthetic */ class C16752 extends AdaptedFunctionReference implements Function3 {
        public static final C16752 INSTANCE = new C16752();

        public C16752() {
            super(3, Pair.class, "<init>", "<init>(Ljava/lang/Object;Ljava/lang/Object;)V", 4);
        }

        @Override // kotlin.jvm.functions.Function3
        public final Object invoke(Object obj, Object obj2, Object obj3) {
            return new Pair((CameraLaunchSourceModel) obj, (TransitionStep) obj2);
        }
    }

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    public FromLockscreenTransitionInteractor$listenForLockscreenToCamera$1(FromLockscreenTransitionInteractor fromLockscreenTransitionInteractor, Continuation<? super FromLockscreenTransitionInteractor$listenForLockscreenToCamera$1> continuation) {
        super(2, continuation);
        this.this$0 = fromLockscreenTransitionInteractor;
    }

    @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
    public final Continuation create(Object obj, Continuation continuation) {
        return new FromLockscreenTransitionInteractor$listenForLockscreenToCamera$1(this.this$0, continuation);
    }

    @Override // kotlin.jvm.functions.Function2
    public final Object invoke(Object obj, Object obj2) {
        return ((FromLockscreenTransitionInteractor$listenForLockscreenToCamera$1) create((CoroutineScope) obj, (Continuation) obj2)).invokeSuspend(Unit.INSTANCE);
    }

    @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
    public final Object invokeSuspend(Object obj) {
        CoroutineSingletons coroutineSingletons = CoroutineSingletons.COROUTINE_SUSPENDED;
        int i = this.label;
        if (i == 0) {
            ResultKt.throwOnFailure(obj);
            FromLockscreenTransitionInteractor fromLockscreenTransitionInteractor = this.this$0;
            SafeFlow sample = FlowKt.sample(fromLockscreenTransitionInteractor.keyguardInteractor.onCameraLaunchDetected, fromLockscreenTransitionInteractor.keyguardTransitionInteractor.startedKeyguardTransitionStep, C16752.INSTANCE);
            final FromLockscreenTransitionInteractor fromLockscreenTransitionInteractor2 = this.this$0;
            FlowCollector flowCollector = new FlowCollector() { // from class: com.android.systemui.keyguard.domain.interactor.FromLockscreenTransitionInteractor$listenForLockscreenToCamera$1.3
                @Override // kotlinx.coroutines.flow.FlowCollector
                public final Object emit(Object obj2, Continuation continuation) {
                    TransitionStep transitionStep = (TransitionStep) ((Pair) obj2).component2();
                    if (LsRune.KEYGUARD_FBE && !((KeyguardUpdateMonitor) Dependency.get(KeyguardUpdateMonitor.class)).isUserUnlocked()) {
                        return Unit.INSTANCE;
                    }
                    KeyguardState keyguardState = transitionStep.f304to;
                    KeyguardState keyguardState2 = KeyguardState.LOCKSCREEN;
                    if (keyguardState == keyguardState2 || keyguardState == KeyguardState.DREAMING || keyguardState == KeyguardState.DOZING || keyguardState == KeyguardState.AOD || keyguardState == KeyguardState.OFF) {
                        FromLockscreenTransitionInteractor fromLockscreenTransitionInteractor3 = FromLockscreenTransitionInteractor.this;
                        KeyguardTransitionRepository keyguardTransitionRepository = fromLockscreenTransitionInteractor3.keyguardTransitionRepository;
                        KeyguardState keyguardState3 = KeyguardState.OCCLUDED;
                        FromLockscreenTransitionInteractor.Companion.getClass();
                        ((KeyguardTransitionRepositoryImpl) keyguardTransitionRepository).startTransition(new TransitionInfo(fromLockscreenTransitionInteractor3.name, keyguardState2, keyguardState3, fromLockscreenTransitionInteractor3.m1578getAnimatorLRDsOJo(FromLockscreenTransitionInteractor.TO_OCCLUDED_DURATION)), false);
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
