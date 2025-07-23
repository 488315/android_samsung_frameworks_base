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

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes2.dex */
final class WindowManagerLockscreenVisibilityInteractor$lockscreenVisibilityWithScenes$1 extends SuspendLambda implements Function2 {
    final /* synthetic */ Lazy $sceneInteractor;
    final /* synthetic */ KeyguardWakeDirectlyToGoneInteractor $wakeToGoneInteractor;
    /* synthetic */ boolean Z$0;
    int label;
    final /* synthetic */ WindowManagerLockscreenVisibilityInteractor this$0;

    /* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
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

    /* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
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

            /* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
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

                /* JADX WARN: Removed duplicated region for block: B:15:0x002f  */
                /* JADX WARN: Removed duplicated region for block: B:8:0x0021  */
                @Override // kotlinx.coroutines.flow.FlowCollector
                /*
                    Code decompiled incorrectly, please refer to instructions dump.
                    To view partially-correct code enable 'Show inconsistent code' option in preferences
                */
                public final java.lang.Object emit(java.lang.Object r5, kotlin.coroutines.Continuation r6) {
                    /*
                        r4 = this;
                        boolean r0 = r6 instanceof com.android.systemui.keyguard.domain.interactor.WindowManagerLockscreenVisibilityInteractor$lockscreenVisibilityWithScenes$1$invokeSuspend$$inlined$map$1.AnonymousClass2.AnonymousClass1
                        if (r0 == 0) goto L13
                        r0 = r6
                        com.android.systemui.keyguard.domain.interactor.WindowManagerLockscreenVisibilityInteractor$lockscreenVisibilityWithScenes$1$invokeSuspend$$inlined$map$1$2$1 r0 = (com.android.systemui.keyguard.domain.interactor.WindowManagerLockscreenVisibilityInteractor$lockscreenVisibilityWithScenes$1$invokeSuspend$$inlined$map$1.AnonymousClass2.AnonymousClass1) r0
                        int r1 = r0.label
                        r2 = -2147483648(0xffffffff80000000, float:-0.0)
                        r3 = r1 & r2
                        if (r3 == 0) goto L13
                        int r1 = r1 - r2
                        r0.label = r1
                        goto L18
                    L13:
                        com.android.systemui.keyguard.domain.interactor.WindowManagerLockscreenVisibilityInteractor$lockscreenVisibilityWithScenes$1$invokeSuspend$$inlined$map$1$2$1 r0 = new com.android.systemui.keyguard.domain.interactor.WindowManagerLockscreenVisibilityInteractor$lockscreenVisibilityWithScenes$1$invokeSuspend$$inlined$map$1$2$1
                        r0.<init>(r6)
                    L18:
                        java.lang.Object r6 = r0.result
                        kotlin.coroutines.intrinsics.CoroutineSingletons r1 = kotlin.coroutines.intrinsics.CoroutineSingletons.COROUTINE_SUSPENDED
                        int r2 = r0.label
                        r3 = 1
                        if (r2 == 0) goto L2f
                        if (r2 != r3) goto L27
                        kotlin.ResultKt.throwOnFailure(r6)
                        goto L5e
                    L27:
                        java.lang.IllegalStateException r4 = new java.lang.IllegalStateException
                        java.lang.String r5 = "call to 'resume' before 'invoke' with coroutine"
                        r4.<init>(r5)
                        throw r4
                    L2f:
                        kotlin.ResultKt.throwOnFailure(r6)
                        kotlin.Pair r5 = (kotlin.Pair) r5
                        java.lang.Object r6 = r5.component1()
                        java.lang.Boolean r6 = (java.lang.Boolean) r6
                        boolean r6 = r6.booleanValue()
                        java.lang.Object r5 = r5.component2()
                        java.lang.Boolean r5 = (java.lang.Boolean) r5
                        boolean r5 = r5.booleanValue()
                        if (r6 == 0) goto L4e
                        if (r5 != 0) goto L4e
                        r5 = r3
                        goto L4f
                    L4e:
                        r5 = 0
                    L4f:
                        java.lang.Boolean r5 = java.lang.Boolean.valueOf(r5)
                        r0.label = r3
                        kotlinx.coroutines.flow.FlowCollector r4 = r4.$this_unsafeFlow
                        java.lang.Object r4 = r4.emit(r5, r0)
                        if (r4 != r1) goto L5e
                        return r1
                    L5e:
                        kotlin.Unit r4 = kotlin.Unit.INSTANCE
                        return r4
                    */
                    throw new UnsupportedOperationException("Method not decompiled: com.android.systemui.keyguard.domain.interactor.WindowManagerLockscreenVisibilityInteractor$lockscreenVisibilityWithScenes$1$invokeSuspend$$inlined$map$1.AnonymousClass2.emit(java.lang.Object, kotlin.coroutines.Continuation):java.lang.Object");
                }
            }

            @Override // kotlinx.coroutines.flow.Flow
            public final Object collect(FlowCollector flowCollector, Continuation continuation) {
                Object collect = Flow.this.collect(new AnonymousClass2(flowCollector), continuation);
                return collect == CoroutineSingletons.COROUTINE_SUSPENDED ? collect : Unit.INSTANCE;
            }
        };
    }
}
