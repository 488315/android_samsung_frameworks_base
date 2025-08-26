package com.android.systemui.keyguard.domain.interactor;

import com.android.compose.animation.scene.ObservableTransitionState;
import com.android.compose.animation.scene.SceneKey;
import com.android.systemui.scene.domain.interactor.SceneInteractor;
import com.android.systemui.utils.coroutines.flow.LatestConflatedKt;
import dagger.Lazy;
import kotlin.NoWhenBranchMatchedException;
import kotlin.Pair;
import kotlin.ResultKt;
import kotlin.Unit;
import kotlin.coroutines.Continuation;
import kotlin.coroutines.intrinsics.CoroutineSingletons;
import kotlin.coroutines.jvm.internal.ContinuationImpl;
import kotlin.coroutines.jvm.internal.SuspendLambda;
import kotlin.jvm.functions.Function2;
import kotlin.jvm.functions.Function3;
import kotlin.jvm.internal.AdaptedFunctionReference;
import kotlinx.coroutines.flow.Flow;
import kotlinx.coroutines.flow.FlowCollector;
import kotlinx.coroutines.flow.FlowKt__BuildersKt$flowOf$$inlined$unsafeFlow$2;
import kotlinx.coroutines.flow.FlowKt__ZipKt$combine$$inlined$unsafeFlow$1;

/* loaded from: classes2.dex */
final class WindowManagerLockscreenVisibilityInteractor$lockscreenVisibilityWithScenes$1 extends SuspendLambda implements Function2 {
    final /* synthetic */ Lazy $sceneInteractor;
    final /* synthetic */ KeyguardWakeDirectlyToGoneInteractor $wakeToGoneInteractor;
    /* synthetic */ boolean Z$0;
    int label;
    final /* synthetic */ WindowManagerLockscreenVisibilityInteractor this$0;

    /* renamed from: com.android.systemui.keyguard.domain.interactor.WindowManagerLockscreenVisibilityInteractor$lockscreenVisibilityWithScenes$1$1, reason: invalid class name */
    final class AnonymousClass1 extends SuspendLambda implements Function2 {
        /* synthetic */ Object L$0;
        int label;
        final /* synthetic */ WindowManagerLockscreenVisibilityInteractor this$0;

        /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
        public AnonymousClass1(WindowManagerLockscreenVisibilityInteractor windowManagerLockscreenVisibilityInteractor, Continuation continuation) {
            super(2, continuation);
            this.this$0 = windowManagerLockscreenVisibilityInteractor;
        }

        @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
        public final Continuation create(Object obj, Continuation continuation) {
            AnonymousClass1 anonymousClass1 = new AnonymousClass1(this.this$0, continuation);
            anonymousClass1.L$0 = obj;
            return anonymousClass1;
        }

        @Override // kotlin.jvm.functions.Function2
        public final Object invoke(Object obj, Object obj2) {
            return ((AnonymousClass1) create((ObservableTransitionState) obj, (Continuation) obj2)).invokeSuspend(Unit.INSTANCE);
        }

        @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
        public final Object invokeSuspend(Object obj) {
            CoroutineSingletons coroutineSingletons = CoroutineSingletons.COROUTINE_SUSPENDED;
            if (this.label != 0) {
                throw new IllegalStateException("call to 'resume' before 'invoke' with coroutine");
            }
            ResultKt.throwOnFailure(obj);
            ObservableTransitionState observableTransitionState = (ObservableTransitionState) this.L$0;
            if (observableTransitionState instanceof ObservableTransitionState.Idle) {
                ObservableTransitionState.Idle idle = (ObservableTransitionState.Idle) observableTransitionState;
                SceneKey sceneKey = idle.currentScene;
                WindowManagerLockscreenVisibilityInteractor.Companion.getClass();
                if (WindowManagerLockscreenVisibilityInteractor.keyguardContent.contains(sceneKey)) {
                    return new FlowKt__BuildersKt$flowOf$$inlined$unsafeFlow$2(Boolean.TRUE);
                }
                if (WindowManagerLockscreenVisibilityInteractor.nonKeyguardContent.contains(sceneKey)) {
                    return new FlowKt__BuildersKt$flowOf$$inlined$unsafeFlow$2(Boolean.FALSE);
                }
                if (WindowManagerLockscreenVisibilityInteractor.keyguardAgnosticContent.contains(sceneKey)) {
                    return (Flow) this.this$0.isDeviceNotEnteredDirectly$delegate.getValue();
                }
                throw new IllegalStateException("Unknown scene: " + idle.currentScene);
            }
            if (!(observableTransitionState instanceof ObservableTransitionState.Transition)) {
                throw new NoWhenBranchMatchedException();
            }
            WindowManagerLockscreenVisibilityInteractor.Companion.getClass();
            if (ObservableTransitionState.isTransitioningSets$default(observableTransitionState, WindowManagerLockscreenVisibilityInteractor.keyguardContent)) {
                return new FlowKt__BuildersKt$flowOf$$inlined$unsafeFlow$2(Boolean.TRUE);
            }
            if (ObservableTransitionState.isTransitioningSets$default(observableTransitionState, WindowManagerLockscreenVisibilityInteractor.nonKeyguardContent)) {
                return new FlowKt__BuildersKt$flowOf$$inlined$unsafeFlow$2(Boolean.FALSE);
            }
            if (ObservableTransitionState.isTransitioningSets$default(observableTransitionState, WindowManagerLockscreenVisibilityInteractor.keyguardAgnosticContent)) {
                return (Flow) this.this$0.isDeviceNotEnteredDirectly$delegate.getValue();
            }
            throw new IllegalStateException("Unknown content: " + ((ObservableTransitionState.Transition) observableTransitionState).fromContent);
        }
    }

    /* renamed from: com.android.systemui.keyguard.domain.interactor.WindowManagerLockscreenVisibilityInteractor$lockscreenVisibilityWithScenes$1$4, reason: invalid class name */
    final /* synthetic */ class AnonymousClass4 extends AdaptedFunctionReference implements Function3 {
        public static final AnonymousClass4 INSTANCE = new AnonymousClass4();

        public AnonymousClass4() {
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
    public WindowManagerLockscreenVisibilityInteractor$lockscreenVisibilityWithScenes$1(Lazy lazy, KeyguardWakeDirectlyToGoneInteractor keyguardWakeDirectlyToGoneInteractor, WindowManagerLockscreenVisibilityInteractor windowManagerLockscreenVisibilityInteractor, Continuation continuation) {
        super(2, continuation);
        this.$sceneInteractor = lazy;
        this.$wakeToGoneInteractor = keyguardWakeDirectlyToGoneInteractor;
        this.this$0 = windowManagerLockscreenVisibilityInteractor;
    }

    @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
    public final Continuation create(Object obj, Continuation continuation) {
        WindowManagerLockscreenVisibilityInteractor$lockscreenVisibilityWithScenes$1 windowManagerLockscreenVisibilityInteractor$lockscreenVisibilityWithScenes$1 = new WindowManagerLockscreenVisibilityInteractor$lockscreenVisibilityWithScenes$1(this.$sceneInteractor, this.$wakeToGoneInteractor, this.this$0, continuation);
        windowManagerLockscreenVisibilityInteractor$lockscreenVisibilityWithScenes$1.Z$0 = ((Boolean) obj).booleanValue();
        return windowManagerLockscreenVisibilityInteractor$lockscreenVisibilityWithScenes$1;
    }

    @Override // kotlin.jvm.functions.Function2
    public final Object invoke(Object obj, Object obj2) {
        Boolean bool = (Boolean) obj;
        bool.booleanValue();
        return ((WindowManagerLockscreenVisibilityInteractor$lockscreenVisibilityWithScenes$1) create(bool, (Continuation) obj2)).invokeSuspend(Unit.INSTANCE);
    }

    @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
    public final Object invokeSuspend(Object obj) {
        CoroutineSingletons coroutineSingletons = CoroutineSingletons.COROUTINE_SUSPENDED;
        if (this.label != 0) {
            throw new IllegalStateException("call to 'resume' before 'invoke' with coroutine");
        }
        ResultKt.throwOnFailure(obj);
        if (!this.Z$0) {
            return new FlowKt__BuildersKt$flowOf$$inlined$unsafeFlow$2(Boolean.FALSE);
        }
        final FlowKt__ZipKt$combine$$inlined$unsafeFlow$1 flowKt__ZipKt$combine$$inlined$unsafeFlow$1 = new FlowKt__ZipKt$combine$$inlined$unsafeFlow$1(LatestConflatedKt.flatMapLatestConflated(((SceneInteractor) this.$sceneInteractor.get()).transitionState, new AnonymousClass1(this.this$0, null)), this.$wakeToGoneInteractor.canWakeDirectlyToGone, AnonymousClass4.INSTANCE);
        return new Flow() { // from class: com.android.systemui.keyguard.domain.interactor.WindowManagerLockscreenVisibilityInteractor$lockscreenVisibilityWithScenes$1$invokeSuspend$$inlined$map$1

            /* renamed from: com.android.systemui.keyguard.domain.interactor.WindowManagerLockscreenVisibilityInteractor$lockscreenVisibilityWithScenes$1$invokeSuspend$$inlined$map$1$2, reason: invalid class name */
            public final class AnonymousClass2 implements FlowCollector {
                public final /* synthetic */ FlowCollector $this_unsafeFlow;

                /* renamed from: com.android.systemui.keyguard.domain.interactor.WindowManagerLockscreenVisibilityInteractor$lockscreenVisibilityWithScenes$1$invokeSuspend$$inlined$map$1$2$1, reason: invalid class name */
                public final class AnonymousClass1 extends ContinuationImpl {
                    Object L$0;
                    int label;
                    /* synthetic */ Object result;

                    public AnonymousClass1(Continuation continuation) {
                        super(continuation);
                    }

                    @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
                    public final Object invokeSuspend(Object obj) {
                        this.result = obj;
                        this.label |= Integer.MIN_VALUE;
                        return AnonymousClass2.this.emit(null, this);
                    }
                }

                public AnonymousClass2(FlowCollector flowCollector) {
                    this.$this_unsafeFlow = flowCollector;
                }

                /* JADX WARN: Removed duplicated region for block: B:7:0x0013  */
                @Override // kotlinx.coroutines.flow.FlowCollector
                /*
                    Code decompiled incorrectly, please refer to instructions dump.
                */
                public final Object emit(Object obj, Continuation continuation) {
                    AnonymousClass1 anonymousClass1;
                    if (continuation instanceof AnonymousClass1) {
                        anonymousClass1 = (AnonymousClass1) continuation;
                        int i = anonymousClass1.label;
                        if ((i & Integer.MIN_VALUE) != 0) {
                            anonymousClass1.label = i - Integer.MIN_VALUE;
                        } else {
                            anonymousClass1 = new AnonymousClass1(continuation);
                        }
                    }
                    Object obj2 = anonymousClass1.result;
                    CoroutineSingletons coroutineSingletons = CoroutineSingletons.COROUTINE_SUSPENDED;
                    int i2 = anonymousClass1.label;
                    if (i2 == 0) {
                        ResultKt.throwOnFailure(obj2);
                        Pair pair = (Pair) obj;
                        Boolean boolValueOf = Boolean.valueOf(((Boolean) pair.component1()).booleanValue() && !((Boolean) pair.component2()).booleanValue());
                        anonymousClass1.label = 1;
                        if (this.$this_unsafeFlow.emit(boolValueOf, anonymousClass1) == coroutineSingletons) {
                            return coroutineSingletons;
                        }
                    } else {
                        if (i2 != 1) {
                            throw new IllegalStateException("call to 'resume' before 'invoke' with coroutine");
                        }
                        ResultKt.throwOnFailure(obj2);
                    }
                    return Unit.INSTANCE;
                }
            }

            @Override // kotlinx.coroutines.flow.Flow
            public final Object collect(FlowCollector flowCollector, Continuation continuation) {
                Object objCollect = flowKt__ZipKt$combine$$inlined$unsafeFlow$1.collect(new AnonymousClass2(flowCollector), continuation);
                return objCollect == CoroutineSingletons.COROUTINE_SUSPENDED ? objCollect : Unit.INSTANCE;
            }
        };
    }
}
