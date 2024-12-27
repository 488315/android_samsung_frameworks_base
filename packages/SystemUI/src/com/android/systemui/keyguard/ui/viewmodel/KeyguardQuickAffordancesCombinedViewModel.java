package com.android.systemui.keyguard.ui.viewmodel;

import com.android.systemui.keyguard.domain.interactor.KeyguardInteractor;
import com.android.systemui.keyguard.domain.interactor.KeyguardQuickAffordanceInteractor;
import com.android.systemui.keyguard.domain.interactor.KeyguardTransitionInteractor;
import com.android.systemui.keyguard.shared.quickaffordance.KeyguardQuickAffordancePosition;
import com.android.systemui.shade.domain.interactor.ShadeInteractor;
import com.android.systemui.shade.domain.interactor.ShadeInteractorImpl;
import kotlin.Unit;
import kotlin.coroutines.Continuation;
import kotlin.coroutines.intrinsics.CoroutineSingletons;
import kotlin.coroutines.jvm.internal.ContinuationImpl;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlinx.coroutines.flow.Flow;
import kotlinx.coroutines.flow.FlowCollector;
import kotlinx.coroutines.flow.FlowKt;
import kotlinx.coroutines.flow.FlowKt__ZipKt$combine$$inlined$unsafeFlow$1;
import kotlinx.coroutines.flow.ReadonlySharedFlow;
import kotlinx.coroutines.flow.StateFlowImpl;
import kotlinx.coroutines.flow.StateFlowKt;
import kotlinx.coroutines.flow.internal.ChannelFlowTransformLatest;
import kotlinx.coroutines.flow.internal.ChannelLimitedFlowMerge;

public final class KeyguardQuickAffordancesCombinedViewModel {
    public static final /* synthetic */ int $r8$clinit = 0;
    public final Flow areQuickAffordancesFullyOpaque;
    public final ChannelFlowTransformLatest endButton;
    public final ChannelLimitedFlowMerge fadeInAlpha;
    public final ChannelLimitedFlowMerge fadeOutAlpha;
    public final KeyguardInteractor keyguardInteractor;
    public final StateFlowImpl previewMode;
    public final KeyguardQuickAffordanceInteractor quickAffordanceInteractor;
    public final StateFlowImpl selectedPreviewSlotId;
    public final FlowKt__ZipKt$combine$$inlined$unsafeFlow$1 shadeExpansionAlpha;
    public final KeyguardQuickAffordancesCombinedViewModel$special$$inlined$map$1 showingLockscreen;
    public final ChannelFlowTransformLatest startButton;
    public final ChannelLimitedFlowMerge transitionAlpha;

    public final class Companion {
        private Companion() {
        }

        public /* synthetic */ Companion(DefaultConstructorMarker defaultConstructorMarker) {
            this();
        }

        public static /* synthetic */ void getAFFORDANCE_FULLY_OPAQUE_ALPHA_THRESHOLD$annotations() {
        }
    }

    public final class PreviewMode {
        public final boolean isInPreviewMode;
        public final boolean shouldHighlightSelectedAffordance;

        /* JADX WARN: Illegal instructions before constructor call */
        /*
            Code decompiled incorrectly, please refer to instructions dump.
            To view partially-correct code enable 'Show inconsistent code' option in preferences
        */
        public PreviewMode() {
            /*
                r3 = this;
                r0 = 3
                r1 = 0
                r2 = 0
                r3.<init>(r2, r2, r0, r1)
                return
            */
            throw new UnsupportedOperationException("Method not decompiled: com.android.systemui.keyguard.ui.viewmodel.KeyguardQuickAffordancesCombinedViewModel.PreviewMode.<init>():void");
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

    public KeyguardQuickAffordancesCombinedViewModel(KeyguardQuickAffordanceInteractor keyguardQuickAffordanceInteractor, KeyguardInteractor keyguardInteractor, ShadeInteractor shadeInteractor, AodToLockscreenTransitionViewModel aodToLockscreenTransitionViewModel, DozingToLockscreenTransitionViewModel dozingToLockscreenTransitionViewModel, DreamingHostedToLockscreenTransitionViewModel dreamingHostedToLockscreenTransitionViewModel, DreamingToLockscreenTransitionViewModel dreamingToLockscreenTransitionViewModel, GoneToLockscreenTransitionViewModel goneToLockscreenTransitionViewModel, OccludedToLockscreenTransitionViewModel occludedToLockscreenTransitionViewModel, OffToLockscreenTransitionViewModel offToLockscreenTransitionViewModel, PrimaryBouncerToLockscreenTransitionViewModel primaryBouncerToLockscreenTransitionViewModel, GlanceableHubToLockscreenTransitionViewModel glanceableHubToLockscreenTransitionViewModel, LockscreenToAodTransitionViewModel lockscreenToAodTransitionViewModel, LockscreenToDozingTransitionViewModel lockscreenToDozingTransitionViewModel, LockscreenToDreamingHostedTransitionViewModel lockscreenToDreamingHostedTransitionViewModel, LockscreenToDreamingTransitionViewModel lockscreenToDreamingTransitionViewModel, LockscreenToGoneTransitionViewModel lockscreenToGoneTransitionViewModel, LockscreenToOccludedTransitionViewModel lockscreenToOccludedTransitionViewModel, LockscreenToPrimaryBouncerTransitionViewModel lockscreenToPrimaryBouncerTransitionViewModel, LockscreenToGlanceableHubTransitionViewModel lockscreenToGlanceableHubTransitionViewModel, KeyguardTransitionInteractor keyguardTransitionInteractor) {
        this.quickAffordanceInteractor = keyguardQuickAffordanceInteractor;
        this.keyguardInteractor = keyguardInteractor;
        boolean z = false;
        StateFlowImpl MutableStateFlow = StateFlowKt.MutableStateFlow(new PreviewMode(z, z, 3, null));
        final ReadonlySharedFlow readonlySharedFlow = keyguardTransitionInteractor.finishedKeyguardState;
        FlowKt__ZipKt$combine$$inlined$unsafeFlow$1 flowKt__ZipKt$combine$$inlined$unsafeFlow$1 = new FlowKt__ZipKt$combine$$inlined$unsafeFlow$1(new Flow() { // from class: com.android.systemui.keyguard.ui.viewmodel.KeyguardQuickAffordancesCombinedViewModel$special$$inlined$map$1

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
                        boolean r0 = r6 instanceof com.android.systemui.keyguard.ui.viewmodel.KeyguardQuickAffordancesCombinedViewModel$special$$inlined$map$1.AnonymousClass2.AnonymousClass1
                        if (r0 == 0) goto L13
                        r0 = r6
                        com.android.systemui.keyguard.ui.viewmodel.KeyguardQuickAffordancesCombinedViewModel$special$$inlined$map$1$2$1 r0 = (com.android.systemui.keyguard.ui.viewmodel.KeyguardQuickAffordancesCombinedViewModel$special$$inlined$map$1.AnonymousClass2.AnonymousClass1) r0
                        int r1 = r0.label
                        r2 = -2147483648(0xffffffff80000000, float:-0.0)
                        r3 = r1 & r2
                        if (r3 == 0) goto L13
                        int r1 = r1 - r2
                        r0.label = r1
                        goto L18
                    L13:
                        com.android.systemui.keyguard.ui.viewmodel.KeyguardQuickAffordancesCombinedViewModel$special$$inlined$map$1$2$1 r0 = new com.android.systemui.keyguard.ui.viewmodel.KeyguardQuickAffordancesCombinedViewModel$special$$inlined$map$1$2$1
                        r0.<init>(r6)
                    L18:
                        java.lang.Object r6 = r0.result
                        kotlin.coroutines.intrinsics.CoroutineSingletons r1 = kotlin.coroutines.intrinsics.CoroutineSingletons.COROUTINE_SUSPENDED
                        int r2 = r0.label
                        r3 = 1
                        if (r2 == 0) goto L2f
                        if (r2 != r3) goto L27
                        kotlin.ResultKt.throwOnFailure(r6)
                        goto L4a
                    L27:
                        java.lang.IllegalStateException r4 = new java.lang.IllegalStateException
                        java.lang.String r5 = "call to 'resume' before 'invoke' with coroutine"
                        r4.<init>(r5)
                        throw r4
                    L2f:
                        kotlin.ResultKt.throwOnFailure(r6)
                        com.android.systemui.keyguard.shared.model.KeyguardState r5 = (com.android.systemui.keyguard.shared.model.KeyguardState) r5
                        com.android.systemui.keyguard.shared.model.KeyguardState r6 = com.android.systemui.keyguard.shared.model.KeyguardState.LOCKSCREEN
                        if (r5 != r6) goto L3a
                        r5 = r3
                        goto L3b
                    L3a:
                        r5 = 0
                    L3b:
                        java.lang.Boolean r5 = java.lang.Boolean.valueOf(r5)
                        r0.label = r3
                        kotlinx.coroutines.flow.FlowCollector r4 = r4.$this_unsafeFlow
                        java.lang.Object r4 = r4.emit(r5, r0)
                        if (r4 != r1) goto L4a
                        return r1
                    L4a:
                        kotlin.Unit r4 = kotlin.Unit.INSTANCE
                        return r4
                    */
                    throw new UnsupportedOperationException("Method not decompiled: com.android.systemui.keyguard.ui.viewmodel.KeyguardQuickAffordancesCombinedViewModel$special$$inlined$map$1.AnonymousClass2.emit(java.lang.Object, kotlin.coroutines.Continuation):java.lang.Object");
                }
            }

            @Override // kotlinx.coroutines.flow.Flow
            public final Object collect(FlowCollector flowCollector, Continuation continuation) {
                Object collect = Flow.this.collect(new AnonymousClass2(flowCollector), continuation);
                return collect == CoroutineSingletons.COROUTINE_SUSPENDED ? collect : Unit.INSTANCE;
            }
        }, ((ShadeInteractorImpl) shadeInteractor).baseShadeInteractor.getAnyExpansion(), new KeyguardQuickAffordancesCombinedViewModel$shadeExpansionAlpha$1(null));
        this.selectedPreviewSlotId = StateFlowKt.MutableStateFlow("bottom_start");
        final ChannelLimitedFlowMerge merge = FlowKt.merge(FlowKt.merge(aodToLockscreenTransitionViewModel.shortcutsAlpha, dozingToLockscreenTransitionViewModel.shortcutsAlpha, dreamingHostedToLockscreenTransitionViewModel.shortcutsAlpha, dreamingToLockscreenTransitionViewModel.shortcutsAlpha, goneToLockscreenTransitionViewModel.shortcutsAlpha, occludedToLockscreenTransitionViewModel.shortcutsAlpha, offToLockscreenTransitionViewModel.shortcutsAlpha, primaryBouncerToLockscreenTransitionViewModel.shortcutsAlpha, glanceableHubToLockscreenTransitionViewModel.shortcutsAlpha), FlowKt.merge(lockscreenToAodTransitionViewModel.shortcutsAlpha, lockscreenToDozingTransitionViewModel.shortcutsAlpha, lockscreenToDreamingHostedTransitionViewModel.shortcutsAlpha, lockscreenToDreamingTransitionViewModel.shortcutsAlpha, lockscreenToGoneTransitionViewModel.shortcutsAlpha, lockscreenToOccludedTransitionViewModel.shortcutsAlpha, lockscreenToPrimaryBouncerTransitionViewModel.shortcutsAlpha, lockscreenToGlanceableHubTransitionViewModel.shortcutsAlpha, flowKt__ZipKt$combine$$inlined$unsafeFlow$1));
        this.areQuickAffordancesFullyOpaque = FlowKt.distinctUntilChanged(new Flow() { // from class: com.android.systemui.keyguard.ui.viewmodel.KeyguardQuickAffordancesCombinedViewModel$special$$inlined$map$2

            /* renamed from: com.android.systemui.keyguard.ui.viewmodel.KeyguardQuickAffordancesCombinedViewModel$special$$inlined$map$2$2, reason: invalid class name */
            public final class AnonymousClass2 implements FlowCollector {
                public final /* synthetic */ FlowCollector $this_unsafeFlow;

                /* renamed from: com.android.systemui.keyguard.ui.viewmodel.KeyguardQuickAffordancesCombinedViewModel$special$$inlined$map$2$2$1, reason: invalid class name */
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
                        boolean r0 = r6 instanceof com.android.systemui.keyguard.ui.viewmodel.KeyguardQuickAffordancesCombinedViewModel$special$$inlined$map$2.AnonymousClass2.AnonymousClass1
                        if (r0 == 0) goto L13
                        r0 = r6
                        com.android.systemui.keyguard.ui.viewmodel.KeyguardQuickAffordancesCombinedViewModel$special$$inlined$map$2$2$1 r0 = (com.android.systemui.keyguard.ui.viewmodel.KeyguardQuickAffordancesCombinedViewModel$special$$inlined$map$2.AnonymousClass2.AnonymousClass1) r0
                        int r1 = r0.label
                        r2 = -2147483648(0xffffffff80000000, float:-0.0)
                        r3 = r1 & r2
                        if (r3 == 0) goto L13
                        int r1 = r1 - r2
                        r0.label = r1
                        goto L18
                    L13:
                        com.android.systemui.keyguard.ui.viewmodel.KeyguardQuickAffordancesCombinedViewModel$special$$inlined$map$2$2$1 r0 = new com.android.systemui.keyguard.ui.viewmodel.KeyguardQuickAffordancesCombinedViewModel$special$$inlined$map$2$2$1
                        r0.<init>(r6)
                    L18:
                        java.lang.Object r6 = r0.result
                        kotlin.coroutines.intrinsics.CoroutineSingletons r1 = kotlin.coroutines.intrinsics.CoroutineSingletons.COROUTINE_SUSPENDED
                        int r2 = r0.label
                        r3 = 1
                        if (r2 == 0) goto L2f
                        if (r2 != r3) goto L27
                        kotlin.ResultKt.throwOnFailure(r6)
                        goto L51
                    L27:
                        java.lang.IllegalStateException r4 = new java.lang.IllegalStateException
                        java.lang.String r5 = "call to 'resume' before 'invoke' with coroutine"
                        r4.<init>(r5)
                        throw r4
                    L2f:
                        kotlin.ResultKt.throwOnFailure(r6)
                        java.lang.Number r5 = (java.lang.Number) r5
                        float r5 = r5.floatValue()
                        r6 = 1064514355(0x3f733333, float:0.95)
                        int r5 = (r5 > r6 ? 1 : (r5 == r6 ? 0 : -1))
                        if (r5 < 0) goto L41
                        r5 = r3
                        goto L42
                    L41:
                        r5 = 0
                    L42:
                        java.lang.Boolean r5 = java.lang.Boolean.valueOf(r5)
                        r0.label = r3
                        kotlinx.coroutines.flow.FlowCollector r4 = r4.$this_unsafeFlow
                        java.lang.Object r4 = r4.emit(r5, r0)
                        if (r4 != r1) goto L51
                        return r1
                    L51:
                        kotlin.Unit r4 = kotlin.Unit.INSTANCE
                        return r4
                    */
                    throw new UnsupportedOperationException("Method not decompiled: com.android.systemui.keyguard.ui.viewmodel.KeyguardQuickAffordancesCombinedViewModel$special$$inlined$map$2.AnonymousClass2.emit(java.lang.Object, kotlin.coroutines.Continuation):java.lang.Object");
                }
            }

            @Override // kotlinx.coroutines.flow.Flow
            public final Object collect(FlowCollector flowCollector, Continuation continuation) {
                Object collect = Flow.this.collect(new AnonymousClass2(flowCollector), continuation);
                return collect == CoroutineSingletons.COROUTINE_SUSPENDED ? collect : Unit.INSTANCE;
            }
        });
        FlowKt.transformLatest(MutableStateFlow, new KeyguardQuickAffordancesCombinedViewModel$button$$inlined$flatMapLatest$1(null, this, KeyguardQuickAffordancePosition.BOTTOM_START));
        FlowKt.transformLatest(MutableStateFlow, new KeyguardQuickAffordancesCombinedViewModel$button$$inlined$flatMapLatest$1(null, this, KeyguardQuickAffordancePosition.BOTTOM_END));
    }
}
