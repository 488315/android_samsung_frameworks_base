package com.android.systemui.keyguard.domain.interactor;

import android.animation.ValueAnimator;
import com.android.app.tracing.coroutines.CoroutineTracingKt;
import com.android.keyguard.KeyguardSecurityModel;
import com.android.systemui.communal.domain.interactor.CommunalSceneInteractor;
import com.android.systemui.communal.domain.interactor.CommunalSettingsInteractor;
import com.android.systemui.keyguard.data.repository.KeyguardTransitionRepository;
import com.android.systemui.keyguard.shared.model.Edge;
import com.android.systemui.keyguard.shared.model.KeyguardState;
import com.android.systemui.keyguard.shared.model.TransitionStep;
import com.android.systemui.power.domain.interactor.PowerInteractor;
import com.android.systemui.user.domain.interactor.SelectedUserInteractor;
import com.android.wm.shell.shared.animation.Interpolators;
import com.samsung.android.knox.custom.IKnoxCustomManager;
import kotlin.ResultKt;
import kotlin.Unit;
import kotlin.coroutines.Continuation;
import kotlin.coroutines.intrinsics.CoroutineSingletons;
import kotlin.coroutines.jvm.internal.ContinuationImpl;
import kotlin.jvm.internal.DefaultConstructorMarker;
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
public final class FromPrimaryBouncerTransitionInteractor extends TransitionInteractor {
    public static final Companion Companion = new Companion(null);
    public static final long DEFAULT_DURATION;
    public static final long TO_AOD_DURATION;
    public static final long TO_DOZING_DURATION;
    public static final long TO_DREAMING_DURATION;
    public static final long TO_GLANCEABLE_HUB_DURATION;
    public static final long TO_GONE_DURATION;
    public static final long TO_GONE_SHORT_DURATION;
    public static final float TO_GONE_SURFACE_BEHIND_VISIBLE_THRESHOLD;
    public static final long TO_LOCKSCREEN_DURATION;
    public static final long TO_OCCLUDED_DURATION;
    public final CommunalSceneInteractor communalSceneInteractor;
    public final CommunalSettingsInteractor communalSettingsInteractor;
    public final InternalKeyguardTransitionInteractor internalTransitionInteractor;
    public final KeyguardSecurityModel keyguardSecurityModel;
    public final CoroutineScope scope;
    public final SelectedUserInteractor selectedUserInteractor;
    public final Flow surfaceBehindVisibility;
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
                iArr[KeyguardState.AOD.ordinal()] = 1;
            } catch (NoSuchFieldError unused) {
            }
            try {
                iArr[KeyguardState.DOZING.ordinal()] = 2;
            } catch (NoSuchFieldError unused2) {
            }
            try {
                iArr[KeyguardState.GONE.ordinal()] = 3;
            } catch (NoSuchFieldError unused3) {
            }
            try {
                iArr[KeyguardState.LOCKSCREEN.ordinal()] = 4;
            } catch (NoSuchFieldError unused4) {
            }
            try {
                iArr[KeyguardState.GLANCEABLE_HUB.ordinal()] = 5;
            } catch (NoSuchFieldError unused5) {
            }
            try {
                iArr[KeyguardState.OCCLUDED.ordinal()] = 6;
            } catch (NoSuchFieldError unused6) {
            }
            $EnumSwitchMapping$0 = iArr;
        }
    }

    static {
        Duration.Companion companion = Duration.Companion;
        DurationUnit durationUnit = DurationUnit.MILLISECONDS;
        long duration = DurationKt.toDuration(300, durationUnit);
        DEFAULT_DURATION = duration;
        TO_AOD_DURATION = duration;
        TO_DOZING_DURATION = duration;
        TO_GONE_DURATION = DurationKt.toDuration(IKnoxCustomManager.Stub.TRANSACTION_addDexURLShortcutExtend, durationUnit);
        TO_GONE_SHORT_DURATION = DurationKt.toDuration(200, durationUnit);
        TO_LOCKSCREEN_DURATION = DurationKt.toDuration(450, durationUnit);
        TO_OCCLUDED_DURATION = DurationKt.toDuration(550, durationUnit);
        TO_GLANCEABLE_HUB_DURATION = duration;
        TO_GONE_SURFACE_BEHIND_VISIBLE_THRESHOLD = 0.1f;
        TO_DREAMING_DURATION = duration;
    }

    /* JADX WARN: Illegal instructions before constructor call */
    public FromPrimaryBouncerTransitionInteractor(KeyguardTransitionRepository keyguardTransitionRepository, InternalKeyguardTransitionInteractor internalKeyguardTransitionInteractor, KeyguardTransitionInteractor keyguardTransitionInteractor, CoroutineScope coroutineScope, CoroutineDispatcher coroutineDispatcher, CoroutineDispatcher coroutineDispatcher2, KeyguardInteractor keyguardInteractor, CommunalSceneInteractor communalSceneInteractor, CommunalSettingsInteractor communalSettingsInteractor, KeyguardSecurityModel keyguardSecurityModel, SelectedUserInteractor selectedUserInteractor, PowerInteractor powerInteractor, KeyguardOcclusionInteractor keyguardOcclusionInteractor) {
        KeyguardState keyguardState = KeyguardState.PRIMARY_BOUNCER;
        super(keyguardState, keyguardTransitionInteractor, coroutineDispatcher2, coroutineDispatcher, powerInteractor, keyguardOcclusionInteractor, keyguardInteractor, null);
        this.transitionRepository = keyguardTransitionRepository;
        this.internalTransitionInteractor = internalKeyguardTransitionInteractor;
        this.scope = coroutineScope;
        this.communalSceneInteractor = communalSceneInteractor;
        this.communalSettingsInteractor = communalSettingsInteractor;
        this.keyguardSecurityModel = keyguardSecurityModel;
        this.selectedUserInteractor = selectedUserInteractor;
        Edge.Companion companion = Edge.Companion;
        companion.getClass();
        Edge.Companion companion2 = Edge.Companion;
        KeyguardState keyguardState2 = KeyguardState.GONE;
        companion.getClass();
        Edge.StateToState stateToState = new Edge.StateToState(keyguardState, keyguardState2);
        keyguardTransitionInteractor.getClass();
        final Flow flowTransition = keyguardTransitionInteractor.transition(stateToState);
        this.surfaceBehindVisibility = FlowKt.distinctUntilChanged(new FlowKt__EmittersKt$onStart$$inlined$unsafeFlow$1(new FromPrimaryBouncerTransitionInteractor$surfaceBehindVisibility$2(null), new Flow() { // from class: com.android.systemui.keyguard.domain.interactor.FromPrimaryBouncerTransitionInteractor$special$$inlined$map$1

            /* renamed from: com.android.systemui.keyguard.domain.interactor.FromPrimaryBouncerTransitionInteractor$special$$inlined$map$1$2, reason: invalid class name */
            public final class AnonymousClass2 implements FlowCollector {
                public final /* synthetic */ FlowCollector $this_unsafeFlow;

                /* renamed from: com.android.systemui.keyguard.domain.interactor.FromPrimaryBouncerTransitionInteractor$special$$inlined$map$1$2$1, reason: invalid class name */
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
                        Boolean boolValueOf = Boolean.valueOf(((TransitionStep) obj).value > FromPrimaryBouncerTransitionInteractor.TO_GONE_SURFACE_BEHIND_VISIBLE_THRESHOLD);
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
                Object objCollect = flowTransition.collect(new AnonymousClass2(flowCollector), continuation);
                return objCollect == CoroutineSingletons.COROUTINE_SUSPENDED ? objCollect : Unit.INSTANCE;
            }
        }));
    }

    @Override // com.android.systemui.keyguard.domain.interactor.TransitionInteractor
    public final ValueAnimator getDefaultAnimatorForTransitionsToState(KeyguardState keyguardState) {
        long j;
        ValueAnimator valueAnimator = new ValueAnimator();
        valueAnimator.setInterpolator(Interpolators.LINEAR);
        switch (WhenMappings.$EnumSwitchMapping$0[keyguardState.ordinal()]) {
            case 1:
                j = TO_AOD_DURATION;
                break;
            case 2:
                j = TO_DOZING_DURATION;
                break;
            case 3:
                j = TO_GONE_DURATION;
                break;
            case 4:
                j = TO_LOCKSCREEN_DURATION;
                break;
            case 5:
                j = TO_GLANCEABLE_HUB_DURATION;
                break;
            case 6:
                j = TO_OCCLUDED_DURATION;
                break;
            default:
                j = DEFAULT_DURATION;
                break;
        }
        valueAnimator.setDuration(Duration.m3456getInWholeMillisecondsimpl(j));
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
        FromPrimaryBouncerTransitionInteractor$listenForPrimaryBouncerToGone$1 fromPrimaryBouncerTransitionInteractor$listenForPrimaryBouncerToGone$1 = new FromPrimaryBouncerTransitionInteractor$listenForPrimaryBouncerToGone$1(this, null);
        CoroutineScope coroutineScope = this.scope;
        CoroutineTracingKt.launchTraced$default(coroutineScope, null, null, fromPrimaryBouncerTransitionInteractor$listenForPrimaryBouncerToGone$1, 7);
        CoroutineTracingKt.launchTraced$default(coroutineScope, null, null, new FromPrimaryBouncerTransitionInteractor$listenForPrimaryBouncerToAsleep$1(this, null), 7);
        CoroutineTracingKt.launchTraced$default(coroutineScope, null, null, new FromPrimaryBouncerTransitionInteractor$listenForPrimaryBouncerNotShowing$2(this, null), 7);
        listenForTransitionToCamera(coroutineScope, this.keyguardInteractor);
    }
}
