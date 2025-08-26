package com.android.systemui.keyguard.ui.viewmodel;

import com.android.app.tracing.FlowTracing;
import com.android.systemui.keyguard.domain.interactor.KeyguardInteractor;
import com.android.systemui.keyguard.domain.interactor.KeyguardQuickAffordanceInteractor;
import com.android.systemui.keyguard.domain.interactor.KeyguardTransitionInteractor;
import com.android.systemui.keyguard.shared.model.KeyguardState;
import com.android.systemui.keyguard.shared.quickaffordance.KeyguardQuickAffordancePosition;
import com.android.systemui.shade.domain.interactor.ShadeInteractor;
import com.android.systemui.shade.domain.interactor.ShadeInteractorImpl;
import kotlin.ResultKt;
import kotlin.Unit;
import kotlin.collections.MapsKt__MapsKt;
import kotlin.coroutines.Continuation;
import kotlin.coroutines.intrinsics.CoroutineSingletons;
import kotlin.coroutines.jvm.internal.ContinuationImpl;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlinx.coroutines.CoroutineScope;
import kotlinx.coroutines.flow.Flow;
import kotlinx.coroutines.flow.FlowCollector;
import kotlinx.coroutines.flow.FlowKt;
import kotlinx.coroutines.flow.FlowKt__TransformKt$onEach$$inlined$unsafeTransform$1;
import kotlinx.coroutines.flow.FlowKt__ZipKt$combine$$inlined$unsafeFlow$1;
import kotlinx.coroutines.flow.ReadonlyStateFlow;
import kotlinx.coroutines.flow.SharingStarted;
import kotlinx.coroutines.flow.StateFlowImpl;
import kotlinx.coroutines.flow.StateFlowKt;
import kotlinx.coroutines.flow.internal.ChannelLimitedFlowMerge;

/* loaded from: classes2.dex */
public final class KeyguardQuickAffordancesCombinedViewModel {
    public static final /* synthetic */ int $r8$clinit = 0;
    public final Flow areQuickAffordancesFullyOpaque;
    public final FlowKt__TransformKt$onEach$$inlined$unsafeTransform$1 endButton;
    public final ChannelLimitedFlowMerge fadeInAlpha;
    public final ChannelLimitedFlowMerge fadeOutAlpha;
    public final KeyguardInteractor keyguardInteractor;
    public final StateFlowImpl previewAffordances;
    public final StateFlowImpl previewMode;
    public final KeyguardQuickAffordanceInteractor quickAffordanceInteractor;
    public final StateFlowImpl selectedPreviewSlotId;
    public final FlowKt__ZipKt$combine$$inlined$unsafeFlow$1 shadeExpansionAlpha;
    public final FlowKt__TransformKt$onEach$$inlined$unsafeTransform$1 startButton;
    public final ReadonlyStateFlow transitionAlpha;

    public final class Companion {
        public /* synthetic */ Companion(DefaultConstructorMarker defaultConstructorMarker) {
            this();
        }

        private Companion() {
        }

        public static /* synthetic */ void getAFFORDANCE_FULLY_OPAQUE_ALPHA_THRESHOLD$annotations() {
        }
    }

    public final class PreviewMode {
        public final boolean isInPreviewMode;
        public final boolean shouldHighlightSelectedAffordance;

        /* JADX WARN: Illegal instructions before constructor call */
        public PreviewMode() {
            boolean z = false;
            this(z, z, 3, null);
        }

        public final boolean equals(Object obj) {
            if (this == obj) {
                return true;
            }
            if (!(obj instanceof PreviewMode)) {
                return false;
            }
            PreviewMode previewMode = (PreviewMode) obj;
            return this.isInPreviewMode == previewMode.isInPreviewMode && this.shouldHighlightSelectedAffordance == previewMode.shouldHighlightSelectedAffordance;
        }

        public final int hashCode() {
            return Boolean.hashCode(this.shouldHighlightSelectedAffordance) + (Boolean.hashCode(this.isInPreviewMode) * 31);
        }

        public final String toString() {
            return "PreviewMode(isInPreviewMode=" + this.isInPreviewMode + ", shouldHighlightSelectedAffordance=" + this.shouldHighlightSelectedAffordance + ")";
        }

        public PreviewMode(boolean z, boolean z2) {
            this.isInPreviewMode = z;
            this.shouldHighlightSelectedAffordance = z2;
        }

        public /* synthetic */ PreviewMode(boolean z, boolean z2, int i, DefaultConstructorMarker defaultConstructorMarker) {
            this((i & 1) != 0 ? false : z, (i & 2) != 0 ? false : z2);
        }
    }

    static {
        new Companion(null);
    }

    public KeyguardQuickAffordancesCombinedViewModel(CoroutineScope coroutineScope, KeyguardQuickAffordanceInteractor keyguardQuickAffordanceInteractor, KeyguardInteractor keyguardInteractor, ShadeInteractor shadeInteractor, AodToLockscreenTransitionViewModel aodToLockscreenTransitionViewModel, DozingToLockscreenTransitionViewModel dozingToLockscreenTransitionViewModel, DreamingToLockscreenTransitionViewModel dreamingToLockscreenTransitionViewModel, GoneToLockscreenTransitionViewModel goneToLockscreenTransitionViewModel, OccludedToLockscreenTransitionViewModel occludedToLockscreenTransitionViewModel, OffToLockscreenTransitionViewModel offToLockscreenTransitionViewModel, PrimaryBouncerToLockscreenTransitionViewModel primaryBouncerToLockscreenTransitionViewModel, GlanceableHubToLockscreenTransitionViewModel glanceableHubToLockscreenTransitionViewModel, LockscreenToAodTransitionViewModel lockscreenToAodTransitionViewModel, LockscreenToDozingTransitionViewModel lockscreenToDozingTransitionViewModel, LockscreenToDreamingTransitionViewModel lockscreenToDreamingTransitionViewModel, LockscreenToGoneTransitionViewModel lockscreenToGoneTransitionViewModel, LockscreenToOccludedTransitionViewModel lockscreenToOccludedTransitionViewModel, LockscreenToPrimaryBouncerTransitionViewModel lockscreenToPrimaryBouncerTransitionViewModel, LockscreenToGlanceableHubTransitionViewModel lockscreenToGlanceableHubTransitionViewModel, KeyguardTransitionInteractor keyguardTransitionInteractor) {
        this.quickAffordanceInteractor = keyguardQuickAffordanceInteractor;
        this.keyguardInteractor = keyguardInteractor;
        boolean z = false;
        this.previewMode = StateFlowKt.MutableStateFlow(new PreviewMode(z, z, 3, null));
        FlowKt__ZipKt$combine$$inlined$unsafeFlow$1 flowKt__ZipKt$combine$$inlined$unsafeFlow$1 = new FlowKt__ZipKt$combine$$inlined$unsafeFlow$1(keyguardTransitionInteractor.isFinishedIn$1(KeyguardState.LOCKSCREEN), ((ShadeInteractorImpl) shadeInteractor).baseShadeInteractor.getAnyExpansion(), new KeyguardQuickAffordancesCombinedViewModel$shadeExpansionAlpha$1(null));
        this.shadeExpansionAlpha = flowKt__ZipKt$combine$$inlined$unsafeFlow$1;
        this.selectedPreviewSlotId = StateFlowKt.MutableStateFlow("bottom_start");
        ChannelLimitedFlowMerge channelLimitedFlowMergeMerge = FlowKt.merge(aodToLockscreenTransitionViewModel.shortcutsAlpha, dozingToLockscreenTransitionViewModel.shortcutsAlpha, dreamingToLockscreenTransitionViewModel.shortcutsAlpha, goneToLockscreenTransitionViewModel.shortcutsAlpha, occludedToLockscreenTransitionViewModel.shortcutsAlpha, offToLockscreenTransitionViewModel.shortcutsAlpha, primaryBouncerToLockscreenTransitionViewModel.shortcutsAlpha, glanceableHubToLockscreenTransitionViewModel.shortcutsAlpha);
        this.fadeInAlpha = channelLimitedFlowMergeMerge;
        ChannelLimitedFlowMerge channelLimitedFlowMergeMerge2 = FlowKt.merge(lockscreenToAodTransitionViewModel.shortcutsAlpha, lockscreenToDozingTransitionViewModel.shortcutsAlpha, lockscreenToDreamingTransitionViewModel.shortcutsAlpha, lockscreenToGoneTransitionViewModel.shortcutsAlpha, lockscreenToOccludedTransitionViewModel.shortcutsAlpha, lockscreenToPrimaryBouncerTransitionViewModel.shortcutsAlpha, lockscreenToGlanceableHubTransitionViewModel.shortcutsAlpha, flowKt__ZipKt$combine$$inlined$unsafeFlow$1);
        this.fadeOutAlpha = channelLimitedFlowMergeMerge2;
        final ReadonlyStateFlow readonlyStateFlowStateIn = FlowKt.stateIn(FlowKt.merge(channelLimitedFlowMergeMerge, channelLimitedFlowMergeMerge2), coroutineScope, SharingStarted.Companion.WhileSubscribed$default(SharingStarted.Companion, 3), Float.valueOf(0.0f));
        this.transitionAlpha = readonlyStateFlowStateIn;
        this.areQuickAffordancesFullyOpaque = FlowKt.distinctUntilChanged(new Flow() { // from class: com.android.systemui.keyguard.ui.viewmodel.KeyguardQuickAffordancesCombinedViewModel$special$$inlined$map$1

            /* renamed from: com.android.systemui.keyguard.ui.viewmodel.KeyguardQuickAffordancesCombinedViewModel$special$$inlined$map$1$2, reason: invalid class name */
            public final class AnonymousClass2 implements FlowCollector {
                public final /* synthetic */ FlowCollector $this_unsafeFlow;

                /* renamed from: com.android.systemui.keyguard.ui.viewmodel.KeyguardQuickAffordancesCombinedViewModel$special$$inlined$map$1$2$1, reason: invalid class name */
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
                        Boolean boolValueOf = Boolean.valueOf(((Number) obj).floatValue() >= 0.95f);
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
                Object objCollect = readonlyStateFlowStateIn.collect(new AnonymousClass2(flowCollector), continuation);
                return objCollect == CoroutineSingletons.COROUTINE_SUSPENDED ? objCollect : Unit.INSTANCE;
            }
        });
        this.previewAffordances = StateFlowKt.MutableStateFlow(MapsKt__MapsKt.emptyMap());
        this.startButton = button(KeyguardQuickAffordancePosition.BOTTOM_START, null);
        this.endButton = button(KeyguardQuickAffordancePosition.BOTTOM_END, null);
    }

    public final FlowKt__TransformKt$onEach$$inlined$unsafeTransform$1 button(KeyguardQuickAffordancePosition keyguardQuickAffordancePosition, String str) {
        return FlowTracing.traceEmissionCount$default(FlowTracing.INSTANCE, FlowKt.transformLatest(this.previewMode, new KeyguardQuickAffordancesCombinedViewModel$button$$inlined$flatMapLatest$1(null, this, keyguardQuickAffordancePosition, str)), new KeyguardQuickAffordancesCombinedViewModel$$ExternalSyntheticLambda0(keyguardQuickAffordancePosition));
    }

    public final void enablePreviewMode(String str, boolean z) {
        PreviewMode previewMode = new PreviewMode(true, z);
        if (str == null) {
            str = "bottom_start";
        }
        this.selectedPreviewSlotId.updateState(null, str);
        this.previewMode.updateState(null, previewMode);
    }
}
