package com.android.systemui.keyguard.domain.interactor;

import com.android.compose.animation.scene.ContentKey;
import com.android.compose.animation.scene.SceneKey;
import com.android.systemui.deviceentry.domain.interactor.DeviceEntryInteractor;
import com.android.systemui.keyguard.data.repository.KeyguardTransitionRepository;
import com.android.systemui.keyguard.data.repository.KeyguardTransitionRepositoryImpl;
import com.android.systemui.keyguard.domain.interactor.WindowManagerLockscreenVisibilityInteractor;
import com.android.systemui.keyguard.shared.model.Edge;
import com.android.systemui.keyguard.shared.model.KeyguardState;
import com.android.systemui.keyguard.shared.model.TransitionInfo;
import com.android.systemui.keyguard.shared.model.TransitionState;
import com.android.systemui.keyguard.shared.model.TransitionStep;
import com.android.systemui.scene.domain.interactor.SceneInteractor;
import com.android.systemui.scene.shared.model.Overlays;
import com.android.systemui.scene.shared.model.Scenes;
import com.android.systemui.statusbar.notification.domain.interactor.NotificationLaunchAnimationInteractor;
import com.android.systemui.util.kotlin.Quad;
import com.android.systemui.util.kotlin.Utils;
import com.android.systemui.util.kotlin.WithPrev;
import com.android.systemui.utils.coroutines.flow.LatestConflatedKt;
import java.util.Collections;
import java.util.Set;
import kotlin.Lazy;
import kotlin.LazyKt__LazyJVMKt;
import kotlin.ResultKt;
import kotlin.Unit;
import kotlin.collections.ArraysKt___ArraysKt;
import kotlin.coroutines.Continuation;
import kotlin.coroutines.intrinsics.CoroutineSingletons;
import kotlin.coroutines.jvm.internal.ContinuationImpl;
import kotlin.jvm.functions.Function0;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlinx.coroutines.flow.Flow;
import kotlinx.coroutines.flow.FlowCollector;
import kotlinx.coroutines.flow.FlowKt;
import kotlinx.coroutines.flow.FlowKt__ZipKt$combine$$inlined$combineUnsafe$FlowKt__ZipKt$2;
import kotlinx.coroutines.flow.FlowKt__ZipKt$combine$$inlined$unsafeFlow$1;
import kotlinx.coroutines.flow.MutableSharedFlow;
import kotlinx.coroutines.flow.StateFlow;

/* loaded from: classes2.dex */
public final class WindowManagerLockscreenVisibilityInteractor {
    public final Flow aodVisibility;
    public final FlowKt__ZipKt$combine$$inlined$unsafeFlow$1 defaultSurfaceBehindVisibility;
    public final Lazy isDeviceEnteredDirectly$delegate;
    public final Lazy isDeviceNotEnteredDirectly$delegate;
    public final Flow lockscreenVisibility;
    public final WindowManagerLockscreenVisibilityInteractor$special$$inlined$map$1 lockscreenVisibilityLegacy;
    public final Flow lockscreenVisibilityWithScenes;
    public final Flow surfaceBehindVisibility;
    public final Flow transitionSpecificSurfaceBehindVisibility;
    public final Flow usingKeyguardGoingAwayAnimation;
    public static final Companion Companion = new Companion(null);
    public static final Set keyguardContent = ArraysKt___ArraysKt.toSet(new ContentKey[]{Scenes.Lockscreen, Overlays.Bouncer, Scenes.Communal, Scenes.Dream});
    public static final Set nonKeyguardContent = Collections.singleton(Scenes.Gone);
    public static final Set keyguardAgnosticContent = ArraysKt___ArraysKt.toSet(new ContentKey[]{Scenes.Shade, Scenes.QuickSettings, Overlays.NotificationsShade, Overlays.QuickSettingsShade});

    public final class Companion {
        public /* synthetic */ Companion(DefaultConstructorMarker defaultConstructorMarker) {
            this();
        }

        private Companion() {
        }
    }

    public abstract /* synthetic */ class WhenMappings {
        public static final /* synthetic */ int[] $EnumSwitchMapping$0;

        static {
            int[] iArr = new int[KeyguardState.values().length];
            try {
                iArr[KeyguardState.LOCKSCREEN.ordinal()] = 1;
            } catch (NoSuchFieldError unused) {
            }
            try {
                iArr[KeyguardState.PRIMARY_BOUNCER.ordinal()] = 2;
            } catch (NoSuchFieldError unused2) {
            }
            try {
                iArr[KeyguardState.ALTERNATE_BOUNCER.ordinal()] = 3;
            } catch (NoSuchFieldError unused3) {
            }
            try {
                iArr[KeyguardState.OCCLUDED.ordinal()] = 4;
            } catch (NoSuchFieldError unused4) {
            }
            $EnumSwitchMapping$0 = iArr;
        }
    }

    /* JADX WARN: Type inference failed for: r14v11, types: [com.android.systemui.keyguard.domain.interactor.WindowManagerLockscreenVisibilityInteractor$special$$inlined$map$1, kotlinx.coroutines.flow.Flow] */
    public WindowManagerLockscreenVisibilityInteractor(final KeyguardInteractor keyguardInteractor, final KeyguardTransitionRepository keyguardTransitionRepository, KeyguardTransitionInteractor keyguardTransitionInteractor, KeyguardSurfaceBehindInteractor keyguardSurfaceBehindInteractor, FromLockscreenTransitionInteractor fromLockscreenTransitionInteractor, FromPrimaryBouncerTransitionInteractor fromPrimaryBouncerTransitionInteractor, FromAlternateBouncerTransitionInteractor fromAlternateBouncerTransitionInteractor, NotificationLaunchAnimationInteractor notificationLaunchAnimationInteractor, dagger.Lazy lazy, final dagger.Lazy lazy2, KeyguardWakeDirectlyToGoneInteractor keyguardWakeDirectlyToGoneInteractor) {
        SceneKey sceneKey = Scenes.Gone;
        KeyguardState keyguardState = KeyguardState.GONE;
        this.defaultSurfaceBehindVisibility = new FlowKt__ZipKt$combine$$inlined$unsafeFlow$1(keyguardTransitionInteractor.isFinishedIn(keyguardState), keyguardWakeDirectlyToGoneInteractor.canWakeDirectlyToGone, new WindowManagerLockscreenVisibilityInteractor$defaultSurfaceBehindVisibility$1(null));
        this.transitionSpecificSurfaceBehindVisibility = FlowKt.distinctUntilChanged(FlowKt.transformLatest(keyguardTransitionInteractor.startedKeyguardTransitionStep, new WindowManagerLockscreenVisibilityInteractor$special$$inlined$flatMapLatest$1(null, fromLockscreenTransitionInteractor, fromPrimaryBouncerTransitionInteractor, fromAlternateBouncerTransitionInteractor)));
        final int i = 0;
        this.isDeviceEnteredDirectly$delegate = LazyKt__LazyJVMKt.lazy(new Function0() { // from class: com.android.systemui.keyguard.domain.interactor.WindowManagerLockscreenVisibilityInteractor$$ExternalSyntheticLambda0
            @Override // kotlin.jvm.functions.Function0
            public final Object invoke() {
                Object obj = lazy2;
                switch (i) {
                    case 0:
                        WindowManagerLockscreenVisibilityInteractor.Companion companion = WindowManagerLockscreenVisibilityInteractor.Companion;
                        return ((DeviceEntryInteractor) ((dagger.Lazy) obj).get()).isDeviceEnteredDirectly;
                    default:
                        WindowManagerLockscreenVisibilityInteractor.Companion companion2 = WindowManagerLockscreenVisibilityInteractor.Companion;
                        final StateFlow stateFlow = (StateFlow) ((WindowManagerLockscreenVisibilityInteractor) obj).isDeviceEnteredDirectly$delegate.getValue();
                        return new Flow() { // from class: com.android.systemui.keyguard.domain.interactor.WindowManagerLockscreenVisibilityInteractor$isDeviceNotEnteredDirectly_delegate$lambda$3$$inlined$map$1

                            /* renamed from: com.android.systemui.keyguard.domain.interactor.WindowManagerLockscreenVisibilityInteractor$isDeviceNotEnteredDirectly_delegate$lambda$3$$inlined$map$1$2, reason: invalid class name */
                            public final class AnonymousClass2 implements FlowCollector {
                                public final /* synthetic */ FlowCollector $this_unsafeFlow;

                                /* renamed from: com.android.systemui.keyguard.domain.interactor.WindowManagerLockscreenVisibilityInteractor$isDeviceNotEnteredDirectly_delegate$lambda$3$$inlined$map$1$2$1, reason: invalid class name */
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
                                        Boolean boolValueOf = Boolean.valueOf(!((Boolean) obj).booleanValue());
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
                                Object objCollect = stateFlow.collect(new AnonymousClass2(flowCollector), continuation);
                                return objCollect == CoroutineSingletons.COROUTINE_SUSPENDED ? objCollect : Unit.INSTANCE;
                            }
                        };
                }
            }
        });
        final int i2 = 1;
        this.isDeviceNotEnteredDirectly$delegate = LazyKt__LazyJVMKt.lazy(new Function0() { // from class: com.android.systemui.keyguard.domain.interactor.WindowManagerLockscreenVisibilityInteractor$$ExternalSyntheticLambda0
            @Override // kotlin.jvm.functions.Function0
            public final Object invoke() {
                Object obj = this;
                switch (i2) {
                    case 0:
                        WindowManagerLockscreenVisibilityInteractor.Companion companion = WindowManagerLockscreenVisibilityInteractor.Companion;
                        return ((DeviceEntryInteractor) ((dagger.Lazy) obj).get()).isDeviceEnteredDirectly;
                    default:
                        WindowManagerLockscreenVisibilityInteractor.Companion companion2 = WindowManagerLockscreenVisibilityInteractor.Companion;
                        final Flow stateFlow = (StateFlow) ((WindowManagerLockscreenVisibilityInteractor) obj).isDeviceEnteredDirectly$delegate.getValue();
                        return new Flow() { // from class: com.android.systemui.keyguard.domain.interactor.WindowManagerLockscreenVisibilityInteractor$isDeviceNotEnteredDirectly_delegate$lambda$3$$inlined$map$1

                            /* renamed from: com.android.systemui.keyguard.domain.interactor.WindowManagerLockscreenVisibilityInteractor$isDeviceNotEnteredDirectly_delegate$lambda$3$$inlined$map$1$2, reason: invalid class name */
                            public final class AnonymousClass2 implements FlowCollector {
                                public final /* synthetic */ FlowCollector $this_unsafeFlow;

                                /* renamed from: com.android.systemui.keyguard.domain.interactor.WindowManagerLockscreenVisibilityInteractor$isDeviceNotEnteredDirectly_delegate$lambda$3$$inlined$map$1$2$1, reason: invalid class name */
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
                                        Boolean boolValueOf = Boolean.valueOf(!((Boolean) obj).booleanValue());
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
                                Object objCollect = stateFlow.collect(new AnonymousClass2(flowCollector), continuation);
                                return objCollect == CoroutineSingletons.COROUTINE_SUSPENDED ? objCollect : Unit.INSTANCE;
                            }
                        };
                }
            }
        });
        Flow flowDistinctUntilChanged = FlowKt.distinctUntilChanged(FlowKt.transformLatest(keyguardTransitionInteractor.isInTransition, new WindowManagerLockscreenVisibilityInteractor$special$$inlined$flatMapLatest$2(null, this)));
        this.surfaceBehindVisibility = flowDistinctUntilChanged;
        Edge.Companion companion = Edge.Companion;
        this.usingKeyguardGoingAwayAnimation = FlowKt.distinctUntilChanged(FlowKt.combine(keyguardTransitionInteractor.isInTransition(Edge.Companion.create$default(companion, sceneKey), Edge.Companion.create$default(companion, null, keyguardState, 1)), FlowKt.distinctUntilChanged(keyguardTransitionInteractor.isFinishedIn$1(keyguardState)), keyguardSurfaceBehindInteractor.isAnimatingSurface, notificationLaunchAnimationInteractor.repository.isLaunchAnimationRunning, new WindowManagerLockscreenVisibilityInteractor$usingKeyguardGoingAwayAnimation$2(null)));
        this.lockscreenVisibilityWithScenes = LatestConflatedKt.flatMapLatestConflated(((SceneInteractor) lazy.get()).isVisible, new WindowManagerLockscreenVisibilityInteractor$lockscreenVisibilityWithScenes$1(lazy, keyguardWakeDirectlyToGoneInteractor, this, null));
        WindowManagerLockscreenVisibilityInteractor$lockscreenVisibilityLegacy$1 windowManagerLockscreenVisibilityInteractor$lockscreenVisibilityLegacy$1 = new WindowManagerLockscreenVisibilityInteractor$lockscreenVisibilityLegacy$1(Utils.Companion);
        final FlowKt__ZipKt$combine$$inlined$combineUnsafe$FlowKt__ZipKt$2 flowKt__ZipKt$combine$$inlined$combineUnsafe$FlowKt__ZipKt$2Combine = FlowKt.combine(keyguardTransitionInteractor.currentKeyguardState, keyguardTransitionInteractor.startedStepWithPrecedingStep, keyguardWakeDirectlyToGoneInteractor.canWakeDirectlyToGone, flowDistinctUntilChanged, windowManagerLockscreenVisibilityInteractor$lockscreenVisibilityLegacy$1);
        ?? r14 = new Flow() { // from class: com.android.systemui.keyguard.domain.interactor.WindowManagerLockscreenVisibilityInteractor$special$$inlined$map$1

            /* renamed from: com.android.systemui.keyguard.domain.interactor.WindowManagerLockscreenVisibilityInteractor$special$$inlined$map$1$2, reason: invalid class name */
            public final class AnonymousClass2 implements FlowCollector {
                public final /* synthetic */ KeyguardInteractor $keyguardInteractor$inlined;
                public final /* synthetic */ FlowCollector $this_unsafeFlow;
                public final /* synthetic */ KeyguardTransitionRepository $transitionRepository$inlined;

                /* renamed from: com.android.systemui.keyguard.domain.interactor.WindowManagerLockscreenVisibilityInteractor$special$$inlined$map$1$2$1, reason: invalid class name */
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

                public AnonymousClass2(FlowCollector flowCollector, KeyguardTransitionRepository keyguardTransitionRepository, dagger.Lazy lazy, KeyguardInteractor keyguardInteractor) {
                    this.$this_unsafeFlow = flowCollector;
                    this.$transitionRepository$inlined = keyguardTransitionRepository;
                    this.$keyguardInteractor$inlined = keyguardInteractor;
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
                        FlowCollector flowCollector = this.$this_unsafeFlow;
                        Quad quad = (Quad) obj;
                        KeyguardState keyguardState = (KeyguardState) quad.component1();
                        WithPrev withPrev = (WithPrev) quad.component2();
                        boolean zBooleanValue = ((Boolean) quad.component3()).booleanValue();
                        boolean zBooleanValue2 = ((Boolean) quad.component4()).booleanValue();
                        TransitionStep transitionStep = (TransitionStep) withPrev.getPreviousValue();
                        KeyguardState keyguardState2 = ((TransitionStep) withPrev.getNewValue()).to;
                        KeyguardState keyguardState3 = KeyguardState.GONE;
                        boolean z = false;
                        boolean z2 = keyguardState2 == keyguardState3 && transitionStep.transitionState == TransitionState.CANCELED && transitionStep.from == keyguardState3;
                        TransitionInfo transitionInfo = ((KeyguardTransitionRepositoryImpl) this.$transitionRepository$inlined).currentTransitionInfo;
                        KeyguardState.Companion companion = KeyguardState.Companion;
                        KeyguardState keyguardState4 = transitionInfo.from;
                        companion.getClass();
                        boolean z3 = KeyguardState.Companion.deviceIsAsleepInState(keyguardState4) && transitionInfo.to == keyguardState3;
                        if (!z2 && !z3 && !zBooleanValue && ((keyguardState != KeyguardState.DREAMING || !((Boolean) this.$keyguardInteractor$inlined.isKeyguardDismissible.getValue()).booleanValue()) && ((((TransitionStep) withPrev.getNewValue()).from != KeyguardState.OCCLUDED || ((TransitionStep) withPrev.getNewValue()).to != keyguardState3) && (!zBooleanValue2 || keyguardState != keyguardState3)))) {
                            z = true;
                        }
                        Boolean boolValueOf = Boolean.valueOf(z);
                        anonymousClass1.label = 1;
                        if (flowCollector.emit(boolValueOf, anonymousClass1) == coroutineSingletons) {
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
                Object objCollect = flowKt__ZipKt$combine$$inlined$combineUnsafe$FlowKt__ZipKt$2Combine.collect(new AnonymousClass2(flowCollector, keyguardTransitionRepository, lazy2, keyguardInteractor), continuation);
                return objCollect == CoroutineSingletons.COROUTINE_SUSPENDED ? objCollect : Unit.INSTANCE;
            }
        };
        this.lockscreenVisibilityLegacy = r14;
        this.lockscreenVisibility = FlowKt.distinctUntilChanged(r14);
        final MutableSharedFlow transitionValueFlow = keyguardTransitionInteractor.getTransitionValueFlow(KeyguardState.AOD);
        this.aodVisibility = FlowKt.distinctUntilChanged(new Flow() { // from class: com.android.systemui.keyguard.domain.interactor.WindowManagerLockscreenVisibilityInteractor$special$$inlined$map$2

            /* renamed from: com.android.systemui.keyguard.domain.interactor.WindowManagerLockscreenVisibilityInteractor$special$$inlined$map$2$2, reason: invalid class name */
            public final class AnonymousClass2 implements FlowCollector {
                public final /* synthetic */ FlowCollector $this_unsafeFlow;

                /* renamed from: com.android.systemui.keyguard.domain.interactor.WindowManagerLockscreenVisibilityInteractor$special$$inlined$map$2$2$1, reason: invalid class name */
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
                        Boolean boolValueOf = Boolean.valueOf(((Number) obj).floatValue() == 1.0f);
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
                Object objCollect = transitionValueFlow.collect(new AnonymousClass2(flowCollector), continuation);
                return objCollect == CoroutineSingletons.COROUTINE_SUSPENDED ? objCollect : Unit.INSTANCE;
            }
        });
    }
}
