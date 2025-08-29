package com.android.systemui.keyguard.ui.viewmodel;

import com.android.compose.modifiers.AnimatedBackgroundKt$$ExternalSyntheticLambda0;
import com.android.systemui.R;
import com.android.systemui.common.ui.domain.interactor.ConfigurationInteractor;
import com.android.systemui.common.ui.domain.interactor.ConfigurationInteractorImpl;
import com.android.systemui.deviceentry.domain.interactor.DeviceEntryUdfpsInteractor;
import com.android.systemui.keyguard.domain.interactor.FromOccludedTransitionInteractor;
import com.android.systemui.keyguard.domain.interactor.KeyguardInteractor;
import com.android.systemui.keyguard.domain.interactor.KeyguardInteractor$$ExternalSyntheticOutline0;
import com.android.systemui.keyguard.domain.interactor.KeyguardTransitionInteractor;
import com.android.systemui.keyguard.shared.model.Edge;
import com.android.systemui.keyguard.shared.model.KeyguardState;
import com.android.systemui.keyguard.ui.KeyguardTransitionAnimationFlow;
import com.android.systemui.keyguard.ui.KeyguardTransitionAnimationFlow$FlowBuilder$sharedFlow74qcysc$$inlined$mapNotNull$1;
import com.android.systemui.keyguard.ui.transitions.DeviceEntryIconTransition;
import com.android.systemui.util.kotlin.WithPrev;
import com.samsung.android.knox.custom.IKnoxCustomManager;
import kotlin.ResultKt;
import kotlin.Unit;
import kotlin.coroutines.Continuation;
import kotlin.coroutines.intrinsics.CoroutineSingletons;
import kotlin.coroutines.jvm.internal.ContinuationImpl;
import kotlin.time.Duration;
import kotlin.time.DurationKt;
import kotlin.time.DurationUnit;
import kotlinx.coroutines.flow.Flow;
import kotlinx.coroutines.flow.FlowCollector;
import kotlinx.coroutines.flow.FlowKt;
import kotlinx.coroutines.flow.internal.ChannelFlowTransformLatest;
import kotlinx.coroutines.flow.internal.ChannelLimitedFlowMerge;

/* loaded from: classes2.dex */
public final class OccludedToLockscreenTransitionViewModel implements DeviceEntryIconTransition {
    public final KeyguardTransitionAnimationFlow$FlowBuilder$sharedFlow74qcysc$$inlined$mapNotNull$1 deviceEntryBackgroundViewAlpha;
    public final KeyguardTransitionAnimationFlow$FlowBuilder$sharedFlow74qcysc$$inlined$mapNotNull$1 deviceEntryParentViewAlpha;
    public final ChannelLimitedFlowMerge lockscreenAlpha;
    public final ChannelFlowTransformLatest lockscreenTranslationY;
    public final KeyguardTransitionAnimationFlow$FlowBuilder$sharedFlow74qcysc$$inlined$mapNotNull$1 shortcutsAlpha;
    public final KeyguardTransitionAnimationFlow.FlowBuilder transitionAnimation;

    public OccludedToLockscreenTransitionViewModel(DeviceEntryUdfpsInteractor deviceEntryUdfpsInteractor, ConfigurationInteractor configurationInteractor, KeyguardTransitionAnimationFlow keyguardTransitionAnimationFlow, KeyguardInteractor keyguardInteractor, final KeyguardTransitionInteractor keyguardTransitionInteractor) {
        FromOccludedTransitionInteractor.Companion.getClass();
        KeyguardTransitionAnimationFlow.FlowBuilder flowBuilderM2613setupVtjQ1oo = keyguardTransitionAnimationFlow.m2613setupVtjQ1oo(FromOccludedTransitionInteractor.TO_LOCKSCREEN_DURATION, KeyguardInteractor$$ExternalSyntheticOutline0.m(Edge.Companion, KeyguardState.OCCLUDED, KeyguardState.LOCKSCREEN));
        this.transitionAnimation = flowBuilderM2613setupVtjQ1oo;
        this.lockscreenTranslationY = FlowKt.transformLatest(((ConfigurationInteractorImpl) configurationInteractor).dimensionPixelSize(R.dimen.occluded_to_lockscreen_transition_lockscreen_translation_y), new OccludedToLockscreenTransitionViewModel$special$$inlined$flatMapLatest$1(null, this));
        Duration.Companion companion = Duration.Companion;
        DurationUnit durationUnit = DurationUnit.MILLISECONDS;
        this.shortcutsAlpha = KeyguardTransitionAnimationFlow.FlowBuilder.m2614sharedFlow74qcysc$default(flowBuilderM2613setupVtjQ1oo, DurationKt.toDuration(IKnoxCustomManager.Stub.TRANSACTION_addDexURLShortcutExtend, durationUnit), new AodToLockscreenTransitionViewModel$$ExternalSyntheticLambda6(), 0L, null, new AlternateBouncerToAodTransitionViewModel$$ExternalSyntheticLambda1(), null, null, null, IKnoxCustomManager.Stub.TRANSACTION_getAppsButtonState);
        KeyguardTransitionAnimationFlow$FlowBuilder$sharedFlow74qcysc$$inlined$mapNotNull$1 keyguardTransitionAnimationFlow$FlowBuilder$sharedFlow74qcysc$$inlined$mapNotNull$1M2614sharedFlow74qcysc$default = KeyguardTransitionAnimationFlow.FlowBuilder.m2614sharedFlow74qcysc$default(flowBuilderM2613setupVtjQ1oo, DurationKt.toDuration(IKnoxCustomManager.Stub.TRANSACTION_addDexURLShortcutExtend, durationUnit), new AodToLockscreenTransitionViewModel$$ExternalSyntheticLambda6(), DurationKt.toDuration(IKnoxCustomManager.Stub.TRANSACTION_removeWidget, durationUnit), null, null, null, null, "OCCLUDED->LOCKSCREEN: lockscreenAlpha", 120);
        final Flow flowPairwise = com.android.systemui.util.kotlin.FlowKt.pairwise(keyguardInteractor.isKeyguardOccluded);
        final Flow flow = new Flow() { // from class: com.android.systemui.keyguard.ui.viewmodel.OccludedToLockscreenTransitionViewModel$special$$inlined$filter$1

            /* renamed from: com.android.systemui.keyguard.ui.viewmodel.OccludedToLockscreenTransitionViewModel$special$$inlined$filter$1$2, reason: invalid class name */
            public final class AnonymousClass2 implements FlowCollector {
                public final /* synthetic */ KeyguardTransitionInteractor $keyguardTransitionInteractor$inlined;
                public final /* synthetic */ FlowCollector $this_unsafeFlow;

                /* renamed from: com.android.systemui.keyguard.ui.viewmodel.OccludedToLockscreenTransitionViewModel$special$$inlined$filter$1$2$1, reason: invalid class name */
                public final class AnonymousClass1 extends ContinuationImpl {
                    Object L$0;
                    Object L$1;
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

                public AnonymousClass2(FlowCollector flowCollector, KeyguardTransitionInteractor keyguardTransitionInteractor) {
                    this.$this_unsafeFlow = flowCollector;
                    this.$keyguardTransitionInteractor$inlined = keyguardTransitionInteractor;
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
                        WithPrev withPrev = (WithPrev) obj;
                        boolean zBooleanValue = ((Boolean) withPrev.component1()).booleanValue();
                        boolean zBooleanValue2 = ((Boolean) withPrev.component2()).booleanValue();
                        if (zBooleanValue && !zBooleanValue2 && this.$keyguardTransitionInteractor$inlined.getCurrentState() == KeyguardState.OCCLUDED) {
                            anonymousClass1.label = 1;
                            if (this.$this_unsafeFlow.emit(obj, anonymousClass1) == coroutineSingletons) {
                                return coroutineSingletons;
                            }
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
                Object objCollect = flowPairwise.collect(new AnonymousClass2(flowCollector, keyguardTransitionInteractor), continuation);
                return objCollect == CoroutineSingletons.COROUTINE_SUSPENDED ? objCollect : Unit.INSTANCE;
            }
        };
        this.lockscreenAlpha = FlowKt.merge(keyguardTransitionAnimationFlow$FlowBuilder$sharedFlow74qcysc$$inlined$mapNotNull$1M2614sharedFlow74qcysc$default, new Flow() { // from class: com.android.systemui.keyguard.ui.viewmodel.OccludedToLockscreenTransitionViewModel$special$$inlined$map$1

            /* renamed from: com.android.systemui.keyguard.ui.viewmodel.OccludedToLockscreenTransitionViewModel$special$$inlined$map$1$2, reason: invalid class name */
            public final class AnonymousClass2 implements FlowCollector {
                public final /* synthetic */ FlowCollector $this_unsafeFlow;

                /* renamed from: com.android.systemui.keyguard.ui.viewmodel.OccludedToLockscreenTransitionViewModel$special$$inlined$map$1$2$1, reason: invalid class name */
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
                        Float f = new Float(0.0f);
                        anonymousClass1.label = 1;
                        if (this.$this_unsafeFlow.emit(f, anonymousClass1) == coroutineSingletons) {
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
                Object objCollect = flow.collect(new AnonymousClass2(flowCollector), continuation);
                return objCollect == CoroutineSingletons.COROUTINE_SUSPENDED ? objCollect : Unit.INSTANCE;
            }
        });
        this.deviceEntryBackgroundViewAlpha = flowBuilderM2613setupVtjQ1oo.immediatelyTransitionTo(1.0f);
        this.deviceEntryParentViewAlpha = KeyguardTransitionAnimationFlow.FlowBuilder.m2614sharedFlow74qcysc$default(flowBuilderM2613setupVtjQ1oo, DurationKt.toDuration(IKnoxCustomManager.Stub.TRANSACTION_addDexURLShortcutExtend, durationUnit), new AodToLockscreenTransitionViewModel$$ExternalSyntheticLambda6(), DurationKt.toDuration(IKnoxCustomManager.Stub.TRANSACTION_removeWidget, durationUnit), null, new AnimatedBackgroundKt$$ExternalSyntheticLambda0(), null, null, null, IKnoxCustomManager.Stub.TRANSACTION_addWidget);
    }

    @Override // com.android.systemui.keyguard.ui.transitions.DeviceEntryIconTransition
    public final Flow getDeviceEntryParentViewAlpha() {
        return this.deviceEntryParentViewAlpha;
    }
}
