package com.android.systemui.keyguard.domain.interactor;

import android.animation.ValueAnimator;
import com.android.app.animation.Interpolators;
import com.android.app.tracing.coroutines.CoroutineTracingKt;
import com.android.compose.animation.scene.SceneKey;
import com.android.systemui.communal.domain.interactor.CommunalSceneInteractor;
import com.android.systemui.communal.domain.interactor.CommunalSettingsInteractor;
import com.android.systemui.keyguard.data.repository.KeyguardTransitionRepository;
import com.android.systemui.keyguard.shared.model.Edge;
import com.android.systemui.keyguard.shared.model.KeyguardState;
import com.android.systemui.power.domain.interactor.PowerInteractor;
import com.android.systemui.power.shared.model.WakeSleepReason;
import com.android.systemui.power.shared.model.WakefulnessModel;
import com.android.systemui.scene.shared.model.Scenes;
import com.android.systemui.shade.data.repository.ShadeRepository;
import kotlin.ResultKt;
import kotlin.Unit;
import kotlin.collections.ArraysKt___ArraysKt;
import kotlin.coroutines.Continuation;
import kotlin.coroutines.intrinsics.CoroutineSingletons;
import kotlin.coroutines.jvm.internal.ContinuationImpl;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.Ref$ObjectRef;
import kotlin.time.Duration;
import kotlin.time.DurationKt;
import kotlin.time.DurationUnit;
import kotlinx.coroutines.CoroutineDispatcher;
import kotlinx.coroutines.CoroutineScope;
import kotlinx.coroutines.flow.Flow;
import kotlinx.coroutines.flow.FlowCollector;
import kotlinx.coroutines.flow.FlowKt;
import kotlinx.coroutines.flow.FlowKt__EmittersKt$onStart$$inlined$unsafeFlow$1;

/* loaded from: classes2.dex */
public final class FromLockscreenTransitionInteractor extends TransitionInteractor {
    public static final Companion Companion = new Companion(null);
    public static final long DEFAULT_DURATION;
    public static final long TO_AOD_DURATION;
    public static final long TO_AOD_FOLD_DURATION;
    public static final long TO_DOZING_DURATION;
    public static final long TO_DREAMING_DURATION;
    public static final long TO_GLANCEABLE_HUB_DURATION;
    public static final long TO_GONE_DURATION;
    public static final long TO_OCCLUDED_DURATION;
    public static final long TO_PRIMARY_BOUNCER_DURATION;
    public final CoroutineScope applicationScope;
    public final CommunalSceneInteractor communalSceneInteractor;
    public final CommunalSettingsInteractor communalSettingsInteractor;
    public final InternalKeyguardTransitionInteractor internalTransitionInteractor;
    public final CoroutineScope scope;
    public final ShadeRepository shadeRepository;
    public final Flow surfaceBehindVisibility;
    public final SwipeToDismissInteractor swipeToDismissInteractor;
    public final KeyguardTransitionRepository transitionRepository;

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
                iArr[KeyguardState.DREAMING.ordinal()] = 1;
            } catch (NoSuchFieldError unused) {
            }
            try {
                iArr[KeyguardState.OCCLUDED.ordinal()] = 2;
            } catch (NoSuchFieldError unused2) {
            }
            try {
                iArr[KeyguardState.AOD.ordinal()] = 3;
            } catch (NoSuchFieldError unused3) {
            }
            try {
                iArr[KeyguardState.DOZING.ordinal()] = 4;
            } catch (NoSuchFieldError unused4) {
            }
            try {
                iArr[KeyguardState.GLANCEABLE_HUB.ordinal()] = 5;
            } catch (NoSuchFieldError unused5) {
            }
            $EnumSwitchMapping$0 = iArr;
        }
    }

    static {
        Duration.Companion companion = Duration.Companion;
        DurationUnit durationUnit = DurationUnit.MILLISECONDS;
        long duration = DurationKt.toDuration(400, durationUnit);
        DEFAULT_DURATION = duration;
        TO_DOZING_DURATION = DurationKt.toDuration(500, durationUnit);
        TO_DREAMING_DURATION = DurationKt.toDuration(933, durationUnit);
        TO_OCCLUDED_DURATION = DurationKt.toDuration(550, durationUnit);
        TO_AOD_DURATION = DurationKt.toDuration(500, durationUnit);
        TO_AOD_FOLD_DURATION = DurationKt.toDuration(1100, durationUnit);
        TO_PRIMARY_BOUNCER_DURATION = duration;
        TO_GONE_DURATION = DurationKt.toDuration(633, durationUnit);
        TO_GLANCEABLE_HUB_DURATION = DurationKt.toDuration(1, DurationUnit.SECONDS);
    }

    /* JADX WARN: Illegal instructions before constructor call */
    public FromLockscreenTransitionInteractor(KeyguardTransitionRepository keyguardTransitionRepository, InternalKeyguardTransitionInteractor internalKeyguardTransitionInteractor, KeyguardTransitionInteractor keyguardTransitionInteractor, CoroutineScope coroutineScope, CoroutineScope coroutineScope2, CoroutineDispatcher coroutineDispatcher, CoroutineDispatcher coroutineDispatcher2, KeyguardInteractor keyguardInteractor, ShadeRepository shadeRepository, PowerInteractor powerInteractor, CommunalSettingsInteractor communalSettingsInteractor, CommunalSceneInteractor communalSceneInteractor, SwipeToDismissInteractor swipeToDismissInteractor, KeyguardOcclusionInteractor keyguardOcclusionInteractor) {
        KeyguardState keyguardState = KeyguardState.LOCKSCREEN;
        super(keyguardState, keyguardTransitionInteractor, coroutineDispatcher2, coroutineDispatcher, powerInteractor, keyguardOcclusionInteractor, keyguardInteractor, null);
        this.transitionRepository = keyguardTransitionRepository;
        this.internalTransitionInteractor = internalKeyguardTransitionInteractor;
        this.scope = coroutineScope;
        this.applicationScope = coroutineScope2;
        this.shadeRepository = shadeRepository;
        this.communalSettingsInteractor = communalSettingsInteractor;
        this.communalSceneInteractor = communalSceneInteractor;
        this.swipeToDismissInteractor = swipeToDismissInteractor;
        Edge.Companion companion = Edge.Companion;
        SceneKey sceneKey = Scenes.Gone;
        companion.getClass();
        new Edge.StateToContent(keyguardState, sceneKey);
        KeyguardState keyguardState2 = KeyguardState.GONE;
        companion.getClass();
        Edge.StateToState stateToState = new Edge.StateToState(keyguardState, keyguardState2);
        keyguardTransitionInteractor.getClass();
        final Flow flowTransition = keyguardTransitionInteractor.transition(stateToState);
        this.surfaceBehindVisibility = FlowKt.distinctUntilChanged(new FlowKt__EmittersKt$onStart$$inlined$unsafeFlow$1(new FromLockscreenTransitionInteractor$surfaceBehindVisibility$2(null), new Flow() { // from class: com.android.systemui.keyguard.domain.interactor.FromLockscreenTransitionInteractor$special$$inlined$map$1

            /* renamed from: com.android.systemui.keyguard.domain.interactor.FromLockscreenTransitionInteractor$special$$inlined$map$1$2, reason: invalid class name */
            public final class AnonymousClass2 implements FlowCollector {
                public final /* synthetic */ FlowCollector $this_unsafeFlow;

                /* renamed from: com.android.systemui.keyguard.domain.interactor.FromLockscreenTransitionInteractor$special$$inlined$map$1$2$1, reason: invalid class name */
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
                        Boolean bool = Boolean.TRUE;
                        anonymousClass1.label = 1;
                        if (this.$this_unsafeFlow.emit(bool, anonymousClass1) == coroutineSingletons) {
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
                Object objCollect = flowTransition.collect(new AnonymousClass2(flowCollector), continuation);
                return objCollect == CoroutineSingletons.COROUTINE_SUSPENDED ? objCollect : Unit.INSTANCE;
            }
        }));
    }

    @Override // com.android.systemui.keyguard.domain.interactor.TransitionInteractor
    public final ValueAnimator getDefaultAnimatorForTransitionsToState(KeyguardState keyguardState) {
        long jM3461plusLRDsOJo;
        ValueAnimator valueAnimator = new ValueAnimator();
        valueAnimator.setInterpolator(Interpolators.LINEAR);
        int i = WhenMappings.$EnumSwitchMapping$0[keyguardState.ordinal()];
        if (i != 1) {
            jM3461plusLRDsOJo = i != 2 ? i != 3 ? i != 4 ? i != 5 ? DEFAULT_DURATION : TO_GLANCEABLE_HUB_DURATION : TO_DOZING_DURATION : ((WakefulnessModel) this.powerInteractor.detailedWakefulness.$$delegate_0.getValue()).lastSleepReason == WakeSleepReason.FOLD ? TO_AOD_FOLD_DURATION : TO_AOD_DURATION : TO_OCCLUDED_DURATION;
        } else {
            Duration.Companion companion = Duration.Companion;
            jM3461plusLRDsOJo = Duration.m3461plusLRDsOJo(TO_DREAMING_DURATION, DurationKt.toDuration(100, DurationUnit.MILLISECONDS));
        }
        valueAnimator.setDuration(Duration.m3457getInWholeMillisecondsimpl(jM3461plusLRDsOJo));
        return valueAnimator;
    }

    @Override // com.android.systemui.keyguard.domain.interactor.TransitionInteractor
    public final InternalKeyguardTransitionInteractor getInternalTransitionInteractor() {
        return this.internalTransitionInteractor;
    }

    @Override // com.android.systemui.keyguard.domain.interactor.TransitionInteractor
    public final KeyguardTransitionRepository getTransitionRepository() {
        return this.transitionRepository;
    }

    @Override // com.android.systemui.keyguard.domain.interactor.TransitionInteractor
    public final void start() {
        FromLockscreenTransitionInteractor$listenForLockscreenToGone$1 fromLockscreenTransitionInteractor$listenForLockscreenToGone$1 = new FromLockscreenTransitionInteractor$listenForLockscreenToGone$1(this, null);
        CoroutineScope coroutineScope = this.scope;
        CoroutineTracingKt.launchTraced$default(coroutineScope, null, null, fromLockscreenTransitionInteractor$listenForLockscreenToGone$1, 6);
        CoroutineTracingKt.launchTraced$default(coroutineScope, null, null, new FromLockscreenTransitionInteractor$listenForLockscreenToOccludedOrDreaming$2(this, null), 6);
        CoroutineTracingKt.launchTraced$default(coroutineScope, null, null, new FromLockscreenTransitionInteractor$listenForLockscreenToAodOrDozing$1(this, null), 6);
        CoroutineTracingKt.launchTraced$default(coroutineScope, null, null, new FromLockscreenTransitionInteractor$listenForLockscreenToPrimaryBouncer$1(this, null), 6);
        CoroutineTracingKt.launchTraced$default(coroutineScope, null, null, new FromLockscreenTransitionInteractor$listenForLockscreenToDreaming$1(this, ArraysKt___ArraysKt.toSet(new KeyguardState[]{KeyguardState.AOD, KeyguardState.DOZING}), null), 6);
        Ref$ObjectRef ref$ObjectRef = new Ref$ObjectRef();
        CoroutineTracingKt.launchTraced$default(this.applicationScope, null, null, new FromLockscreenTransitionInteractor$listenForLockscreenToPrimaryBouncerDragging$1(this, ref$ObjectRef, null), 6);
        CoroutineTracingKt.launchTraced$default(coroutineScope, null, null, new FromLockscreenTransitionInteractor$listenForLockscreenToPrimaryBouncerDragging$2(this, ref$ObjectRef, null), 7);
        CoroutineTracingKt.launchTraced$default(coroutineScope, null, null, new FromLockscreenTransitionInteractor$listenForLockscreenToAlternateBouncer$1(this, null), 6);
        listenForTransitionToCamera(coroutineScope, this.keyguardInteractor);
        this.communalSettingsInteractor.isV2FlagEnabled();
    }
}
