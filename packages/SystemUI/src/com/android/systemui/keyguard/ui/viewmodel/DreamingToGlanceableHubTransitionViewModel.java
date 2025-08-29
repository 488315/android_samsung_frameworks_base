package com.android.systemui.keyguard.ui.viewmodel;

import com.android.compose.animation.scene.SceneKey;
import com.android.systemui.R;
import com.android.systemui.common.ui.domain.interactor.ConfigurationInteractor;
import com.android.systemui.common.ui.domain.interactor.ConfigurationInteractorImpl;
import com.android.systemui.dagger.DaggerReferenceGlobalRootComponent;
import com.android.systemui.keyguard.dagger.GlanceableHubBlurComponent;
import com.android.systemui.keyguard.shared.model.Edge;
import com.android.systemui.keyguard.shared.model.KeyguardState;
import com.android.systemui.keyguard.ui.KeyguardTransitionAnimationFlow;
import com.android.systemui.keyguard.ui.KeyguardTransitionAnimationFlow$FlowBuilder$sharedFlow74qcysc$$inlined$mapNotNull$1;
import com.android.systemui.keyguard.ui.transitions.DeviceEntryIconTransition;
import com.android.systemui.keyguard.ui.transitions.GlanceableHubTransition;
import com.android.systemui.scene.shared.model.Scenes;
import kotlin.ResultKt;
import kotlin.Unit;
import kotlin.coroutines.Continuation;
import kotlin.coroutines.intrinsics.CoroutineSingletons;
import kotlin.coroutines.jvm.internal.ContinuationImpl;
import kotlin.jvm.functions.Function0;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.time.Duration;
import kotlin.time.DurationKt;
import kotlin.time.DurationUnit;
import kotlinx.coroutines.flow.Flow;
import kotlinx.coroutines.flow.FlowCollector;
import kotlinx.coroutines.flow.FlowKt;
import kotlinx.coroutines.flow.internal.ChannelFlowTransformLatest;

/* loaded from: classes2.dex */
public final class DreamingToGlanceableHubTransitionViewModel implements DeviceEntryIconTransition, GlanceableHubTransition {
    public static final /* synthetic */ int $r8$clinit = 0;
    public static final long TO_GLANCEABLE_HUB_DURATION;
    public final KeyguardTransitionAnimationFlow$FlowBuilder$sharedFlow74qcysc$$inlined$mapNotNull$1 deviceEntryParentViewAlpha;
    public final KeyguardTransitionAnimationFlow$FlowBuilder$sharedFlow74qcysc$$inlined$mapNotNull$1 dreamAlpha;
    public final KeyguardTransitionAnimationFlow$FlowBuilder$sharedFlow74qcysc$$inlined$mapNotNull$1 dreamOverlayAlpha;
    public final ChannelFlowTransformLatest dreamOverlayTranslationX;
    public final DreamingToGlanceableHubTransitionViewModel$special$$inlined$map$1 showUmo;
    public final KeyguardTransitionAnimationFlow.FlowBuilder transitionAnimation;
    public final KeyguardTransitionAnimationFlow$FlowBuilder$sharedFlow74qcysc$$inlined$mapNotNull$1 windowBlurRadius;

    public final class Companion {
        public /* synthetic */ Companion(DefaultConstructorMarker defaultConstructorMarker) {
            this();
        }

        private Companion() {
        }
    }

    static {
        new Companion(null);
        Duration.Companion companion = Duration.Companion;
        TO_GLANCEABLE_HUB_DURATION = DurationKt.toDuration(1, DurationUnit.SECONDS);
    }

    /* JADX WARN: Type inference failed for: r5v5, types: [com.android.systemui.keyguard.ui.viewmodel.DreamingToGlanceableHubTransitionViewModel$special$$inlined$map$1] */
    public DreamingToGlanceableHubTransitionViewModel(KeyguardTransitionAnimationFlow keyguardTransitionAnimationFlow, ConfigurationInteractor configurationInteractor, GlanceableHubBlurComponent.Factory factory) {
        final int i = 0;
        final int i2 = 1;
        Edge.Companion companion = Edge.Companion;
        KeyguardState keyguardState = KeyguardState.DREAMING;
        SceneKey sceneKey = Scenes.Communal;
        companion.getClass();
        Edge.StateToContent stateToContent = new Edge.StateToContent(keyguardState, sceneKey);
        long j = TO_GLANCEABLE_HUB_DURATION;
        KeyguardTransitionAnimationFlow.FlowBuilder flowBuilderM = AlternateBouncerToGoneTransitionViewModel$$ExternalSyntheticOutline0.m(keyguardState, KeyguardState.GLANCEABLE_HUB, keyguardTransitionAnimationFlow.m2613setupVtjQ1oo(j, stateToContent));
        this.transitionAnimation = flowBuilderM;
        this.dreamOverlayTranslationX = FlowKt.transformLatest(((ConfigurationInteractorImpl) configurationInteractor).directionalDimensionPixelSize(R.dimen.dreaming_to_hub_transition_dream_overlay_translation_x), new DreamingToGlanceableHubTransitionViewModel$special$$inlined$flatMapLatest$1(null, this));
        this.dreamAlpha = flowBuilderM.immediatelyTransitionTo(1.0f);
        Duration.Companion companion2 = Duration.Companion;
        DurationUnit durationUnit = DurationUnit.MILLISECONDS;
        this.dreamOverlayAlpha = KeyguardTransitionAnimationFlow.FlowBuilder.m2614sharedFlow74qcysc$default(flowBuilderM, DurationKt.toDuration(167, durationUnit), new Function1() { // from class: com.android.systemui.keyguard.ui.viewmodel.DreamingToGlanceableHubTransitionViewModel$$ExternalSyntheticLambda0
            @Override // kotlin.jvm.functions.Function1
            /* renamed from: invoke */
            public final Object mo781invoke(Object obj) {
                Float f = (Float) obj;
                switch (i) {
                    case 0:
                        float fFloatValue = f.floatValue();
                        int i3 = DreamingToGlanceableHubTransitionViewModel.$r8$clinit;
                        return Float.valueOf(1.0f - fFloatValue);
                    default:
                        f.floatValue();
                        int i4 = DreamingToGlanceableHubTransitionViewModel.$r8$clinit;
                        return f;
                }
            }
        }, 0L, null, null, null, null, "DREAMING->GLANCEABLE_HUB: dreamOverlayAlpha", 124);
        final KeyguardTransitionAnimationFlow$FlowBuilder$sharedFlow74qcysc$$inlined$mapNotNull$1 keyguardTransitionAnimationFlow$FlowBuilder$sharedFlow74qcysc$$inlined$mapNotNull$1M2614sharedFlow74qcysc$default = KeyguardTransitionAnimationFlow.FlowBuilder.m2614sharedFlow74qcysc$default(flowBuilderM, j, new Function1() { // from class: com.android.systemui.keyguard.ui.viewmodel.DreamingToGlanceableHubTransitionViewModel$$ExternalSyntheticLambda0
            @Override // kotlin.jvm.functions.Function1
            /* renamed from: invoke */
            public final Object mo781invoke(Object obj) {
                Float f = (Float) obj;
                switch (i2) {
                    case 0:
                        float fFloatValue = f.floatValue();
                        int i3 = DreamingToGlanceableHubTransitionViewModel.$r8$clinit;
                        return Float.valueOf(1.0f - fFloatValue);
                    default:
                        f.floatValue();
                        int i4 = DreamingToGlanceableHubTransitionViewModel.$r8$clinit;
                        return f;
                }
            }
        }, 0L, null, new Function0() { // from class: com.android.systemui.keyguard.ui.viewmodel.DreamingToGlanceableHubTransitionViewModel$$ExternalSyntheticLambda2
            @Override // kotlin.jvm.functions.Function0
            public final Object invoke() {
                switch (i) {
                    case 0:
                        int i3 = DreamingToGlanceableHubTransitionViewModel.$r8$clinit;
                        return Float.valueOf(0.0f);
                    default:
                        int i4 = DreamingToGlanceableHubTransitionViewModel.$r8$clinit;
                        return Float.valueOf(1.0f);
                }
            }
        }, new Function0() { // from class: com.android.systemui.keyguard.ui.viewmodel.DreamingToGlanceableHubTransitionViewModel$$ExternalSyntheticLambda2
            @Override // kotlin.jvm.functions.Function0
            public final Object invoke() {
                switch (i2) {
                    case 0:
                        int i3 = DreamingToGlanceableHubTransitionViewModel.$r8$clinit;
                        return Float.valueOf(0.0f);
                    default:
                        int i4 = DreamingToGlanceableHubTransitionViewModel.$r8$clinit;
                        return Float.valueOf(1.0f);
                }
            }
        }, null, null, 204);
        this.showUmo = new Flow() { // from class: com.android.systemui.keyguard.ui.viewmodel.DreamingToGlanceableHubTransitionViewModel$special$$inlined$map$1

            /* renamed from: com.android.systemui.keyguard.ui.viewmodel.DreamingToGlanceableHubTransitionViewModel$special$$inlined$map$1$2, reason: invalid class name */
            public final class AnonymousClass2 implements FlowCollector {
                public final /* synthetic */ FlowCollector $this_unsafeFlow;

                /* renamed from: com.android.systemui.keyguard.ui.viewmodel.DreamingToGlanceableHubTransitionViewModel$special$$inlined$map$1$2$1, reason: invalid class name */
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
                        Boolean boolValueOf = Boolean.valueOf(!(((Number) obj).floatValue() == 0.0f));
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
                Object objCollect = keyguardTransitionAnimationFlow$FlowBuilder$sharedFlow74qcysc$$inlined$mapNotNull$1M2614sharedFlow74qcysc$default.collect(new AnonymousClass2(flowCollector), continuation);
                return objCollect == CoroutineSingletons.COROUTINE_SUSPENDED ? objCollect : Unit.INSTANCE;
            }
        };
        this.deviceEntryParentViewAlpha = KeyguardTransitionAnimationFlow.FlowBuilder.m2614sharedFlow74qcysc$default(flowBuilderM, DurationKt.toDuration(167, durationUnit), new Function1() { // from class: com.android.systemui.keyguard.ui.viewmodel.DreamingToGlanceableHubTransitionViewModel$$ExternalSyntheticLambda0
            @Override // kotlin.jvm.functions.Function1
            /* renamed from: invoke */
            public final Object mo781invoke(Object obj) {
                Float f = (Float) obj;
                switch (i2) {
                    case 0:
                        float fFloatValue = f.floatValue();
                        int i3 = DreamingToGlanceableHubTransitionViewModel.$r8$clinit;
                        return Float.valueOf(1.0f - fFloatValue);
                    default:
                        f.floatValue();
                        int i4 = DreamingToGlanceableHubTransitionViewModel.$r8$clinit;
                        return f;
                }
            }
        }, DurationKt.toDuration(167, durationUnit), null, new Function0() { // from class: com.android.systemui.keyguard.ui.viewmodel.DreamingToGlanceableHubTransitionViewModel$$ExternalSyntheticLambda2
            @Override // kotlin.jvm.functions.Function0
            public final Object invoke() {
                switch (i2) {
                    case 0:
                        int i3 = DreamingToGlanceableHubTransitionViewModel.$r8$clinit;
                        return Float.valueOf(0.0f);
                    default:
                        int i4 = DreamingToGlanceableHubTransitionViewModel.$r8$clinit;
                        return Float.valueOf(1.0f);
                }
            }
        }, new Function0() { // from class: com.android.systemui.keyguard.ui.viewmodel.DreamingToGlanceableHubTransitionViewModel$$ExternalSyntheticLambda2
            @Override // kotlin.jvm.functions.Function0
            public final Object invoke() {
                switch (i2) {
                    case 0:
                        int i3 = DreamingToGlanceableHubTransitionViewModel.$r8$clinit;
                        return Float.valueOf(0.0f);
                    default:
                        int i4 = DreamingToGlanceableHubTransitionViewModel.$r8$clinit;
                        return Float.valueOf(1.0f);
                }
            }
        }, null, null, 200);
        this.windowBlurRadius = ((DaggerReferenceGlobalRootComponent.GlanceableHubBlurComponentImpl) factory.create(flowBuilderM)).getBlurProvider().enterBlurRadius;
    }

    @Override // com.android.systemui.keyguard.ui.transitions.DeviceEntryIconTransition
    public final Flow getDeviceEntryParentViewAlpha() {
        return this.deviceEntryParentViewAlpha;
    }
}
