package com.android.systemui.keyguard.ui.viewmodel;

import android.util.MathUtils;
import com.android.compose.animation.scene.SceneKey;
import com.android.systemui.Flags;
import com.android.systemui.communal.domain.interactor.CommunalInteractor;
import com.android.systemui.deviceentry.domain.interactor.DeviceEntryInteractor;
import com.android.systemui.keyguard.domain.interactor.KeyguardInteractor;
import com.android.systemui.keyguard.domain.interactor.KeyguardTransitionInteractor;
import com.android.systemui.keyguard.shared.model.BurnInModel;
import com.android.systemui.keyguard.shared.model.Edge;
import com.android.systemui.keyguard.shared.model.KeyguardState;
import com.android.systemui.keyguard.ui.KeyguardTransitionAnimationFlow;
import com.android.systemui.keyguard.ui.KeyguardTransitionAnimationFlow$FlowBuilder$sharedFlow74qcysc$$inlined$mapNotNull$1;
import com.android.systemui.scene.shared.model.Scenes;
import com.android.systemui.shade.domain.interactor.ShadeInteractor;
import com.android.systemui.shade.domain.interactor.ShadeInteractorImpl;
import com.android.systemui.statusbar.notification.domain.interactor.NotificationsKeyguardInteractor;
import com.android.systemui.statusbar.phone.DozeParameters;
import com.android.systemui.statusbar.phone.ScreenOffAnimationController;
import com.android.systemui.util.kotlin.BooleanFlowOperators;
import com.android.systemui.util.ui.AnimatedValue;
import com.android.systemui.util.ui.AnimatedValueKt;
import com.samsung.android.knox.custom.IKnoxCustomManager;
import kotlin.Unit;
import kotlin.coroutines.Continuation;
import kotlin.coroutines.intrinsics.CoroutineSingletons;
import kotlin.coroutines.jvm.internal.ContinuationImpl;
import kotlin.jvm.functions.Function0;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.Ref$FloatRef;
import kotlin.time.Duration;
import kotlin.time.DurationKt;
import kotlin.time.DurationUnit;
import kotlinx.coroutines.CoroutineScope;
import kotlinx.coroutines.Job;
import kotlinx.coroutines.flow.Flow;
import kotlinx.coroutines.flow.FlowCollector;
import kotlinx.coroutines.flow.FlowKt;
import kotlinx.coroutines.flow.FlowKt__EmittersKt$onStart$$inlined$unsafeFlow$1;
import kotlinx.coroutines.flow.FlowKt__ZipKt$combine$$inlined$unsafeFlow$1;
import kotlinx.coroutines.flow.FlowKt__ZipKt$combineTransform$$inlined$combineTransformUnsafe$FlowKt__ZipKt$5;
import kotlinx.coroutines.flow.MutableSharedFlow;
import kotlinx.coroutines.flow.ReadonlySharedFlow;
import kotlinx.coroutines.flow.ReadonlyStateFlow;
import kotlinx.coroutines.flow.SafeFlow;
import kotlinx.coroutines.flow.SharingStarted;
import kotlinx.coroutines.flow.StateFlowImpl;
import kotlinx.coroutines.flow.StateFlowKt;
import kotlinx.coroutines.flow.internal.ChannelLimitedFlowMerge;

public final class KeyguardRootViewModel {
    public final StateFlowImpl _burnInModel;
    public final Flow alphaOnShadeExpansion;
    public final AlternateBouncerToGoneTransitionViewModel alternateBouncerToGoneTransitionViewModel;
    public final AodBurnInViewModel aodBurnInViewModel;
    public final AodToGoneTransitionViewModel aodToGoneTransitionViewModel;
    public final AodToLockscreenTransitionViewModel aodToLockscreenTransitionViewModel;
    public final AodToOccludedTransitionViewModel aodToOccludedTransitionViewModel;
    public final CoroutineScope applicationScope;
    public Job burnInJob;
    public final SafeFlow burnInLayerAlpha;
    public final KeyguardRootViewModel$special$$inlined$map$1 burnInLayerVisibility;
    public final ReadonlyStateFlow burnInModel;
    public final DozeParameters dozeParameters;
    public final DozingToGoneTransitionViewModel dozingToGoneTransitionViewModel;
    public final DozingToLockscreenTransitionViewModel dozingToLockscreenTransitionViewModel;
    public final DozingToOccludedTransitionViewModel dozingToOccludedTransitionViewModel;
    public final DreamingToGoneTransitionViewModel dreamingToGoneTransitionViewModel;
    public final DreamingToLockscreenTransitionViewModel dreamingToLockscreenTransitionViewModel;
    public final GlanceableHubToLockscreenTransitionViewModel glanceableHubToLockscreenTransitionViewModel;
    public final Flow goneToAodTransition;
    public final GoneToAodTransitionViewModel goneToAodTransitionViewModel;
    public final GoneToDozingTransitionViewModel goneToDozingTransitionViewModel;
    public final GoneToDreamingTransitionViewModel goneToDreamingTransitionViewModel;
    public final GoneToLockscreenTransitionViewModel goneToLockscreenTransitionViewModel;
    public final Flow hideKeyguard;
    public final ReadonlyStateFlow isNotifIconContainerVisible;
    public final KeyguardInteractor keyguardInteractor;
    public final ReadonlyStateFlow lastRootViewTapPosition;
    public final LockscreenToAodTransitionViewModel lockscreenToAodTransitionViewModel;
    public final LockscreenToDozingTransitionViewModel lockscreenToDozingTransitionViewModel;
    public final LockscreenToDreamingTransitionViewModel lockscreenToDreamingTransitionViewModel;
    public final LockscreenToGlanceableHubTransitionViewModel lockscreenToGlanceableHubTransitionViewModel;
    public final LockscreenToGoneTransitionViewModel lockscreenToGoneTransitionViewModel;
    public final LockscreenToOccludedTransitionViewModel lockscreenToOccludedTransitionViewModel;
    public final LockscreenToPrimaryBouncerTransitionViewModel lockscreenToPrimaryBouncerTransitionViewModel;
    public final OccludedToAodTransitionViewModel occludedToAodTransitionViewModel;
    public final OccludedToDozingTransitionViewModel occludedToDozingTransitionViewModel;
    public final OccludedToLockscreenTransitionViewModel occludedToLockscreenTransitionViewModel;
    public final PrimaryBouncerToAodTransitionViewModel primaryBouncerToAodTransitionViewModel;
    public final PrimaryBouncerToGoneTransitionViewModel primaryBouncerToGoneTransitionViewModel;
    public final PrimaryBouncerToLockscreenTransitionViewModel primaryBouncerToLockscreenTransitionViewModel;
    public final KeyguardRootViewModel$special$$inlined$map$8 scale;
    public final ScreenOffAnimationController screenOffAnimationController;
    public final Flow topClippingBounds;
    public final ChannelLimitedFlowMerge translationX;
    public final KeyguardRootViewModel$special$$inlined$map$6 translationY;

    public final class Companion {
        private Companion() {
        }

        public /* synthetic */ Companion(DefaultConstructorMarker defaultConstructorMarker) {
            this();
        }
    }

    static {
        new Companion(null);
    }

    public KeyguardRootViewModel(CoroutineScope coroutineScope, DeviceEntryInteractor deviceEntryInteractor, DozeParameters dozeParameters, KeyguardInteractor keyguardInteractor, CommunalInteractor communalInteractor, KeyguardTransitionInteractor keyguardTransitionInteractor, NotificationsKeyguardInteractor notificationsKeyguardInteractor, AlternateBouncerToGoneTransitionViewModel alternateBouncerToGoneTransitionViewModel, AodToGoneTransitionViewModel aodToGoneTransitionViewModel, AodToLockscreenTransitionViewModel aodToLockscreenTransitionViewModel, AodToOccludedTransitionViewModel aodToOccludedTransitionViewModel, DozingToGoneTransitionViewModel dozingToGoneTransitionViewModel, DozingToLockscreenTransitionViewModel dozingToLockscreenTransitionViewModel, DozingToOccludedTransitionViewModel dozingToOccludedTransitionViewModel, DreamingToLockscreenTransitionViewModel dreamingToLockscreenTransitionViewModel, DreamingToGoneTransitionViewModel dreamingToGoneTransitionViewModel, GlanceableHubToLockscreenTransitionViewModel glanceableHubToLockscreenTransitionViewModel, GoneToAodTransitionViewModel goneToAodTransitionViewModel, GoneToDozingTransitionViewModel goneToDozingTransitionViewModel, GoneToDreamingTransitionViewModel goneToDreamingTransitionViewModel, GoneToLockscreenTransitionViewModel goneToLockscreenTransitionViewModel, LockscreenToAodTransitionViewModel lockscreenToAodTransitionViewModel, LockscreenToDozingTransitionViewModel lockscreenToDozingTransitionViewModel, LockscreenToDreamingTransitionViewModel lockscreenToDreamingTransitionViewModel, LockscreenToGlanceableHubTransitionViewModel lockscreenToGlanceableHubTransitionViewModel, LockscreenToGoneTransitionViewModel lockscreenToGoneTransitionViewModel, LockscreenToOccludedTransitionViewModel lockscreenToOccludedTransitionViewModel, LockscreenToPrimaryBouncerTransitionViewModel lockscreenToPrimaryBouncerTransitionViewModel, OccludedToAodTransitionViewModel occludedToAodTransitionViewModel, OccludedToDozingTransitionViewModel occludedToDozingTransitionViewModel, OccludedToLockscreenTransitionViewModel occludedToLockscreenTransitionViewModel, PrimaryBouncerToAodTransitionViewModel primaryBouncerToAodTransitionViewModel, PrimaryBouncerToGoneTransitionViewModel primaryBouncerToGoneTransitionViewModel, PrimaryBouncerToLockscreenTransitionViewModel primaryBouncerToLockscreenTransitionViewModel, ScreenOffAnimationController screenOffAnimationController, AodBurnInViewModel aodBurnInViewModel, AodAlphaViewModel aodAlphaViewModel, ShadeInteractor shadeInteractor) {
        this.applicationScope = coroutineScope;
        this.dozeParameters = dozeParameters;
        this.keyguardInteractor = keyguardInteractor;
        this.alternateBouncerToGoneTransitionViewModel = alternateBouncerToGoneTransitionViewModel;
        this.aodToGoneTransitionViewModel = aodToGoneTransitionViewModel;
        this.aodToLockscreenTransitionViewModel = aodToLockscreenTransitionViewModel;
        this.aodToOccludedTransitionViewModel = aodToOccludedTransitionViewModel;
        this.dozingToGoneTransitionViewModel = dozingToGoneTransitionViewModel;
        this.dozingToLockscreenTransitionViewModel = dozingToLockscreenTransitionViewModel;
        this.dozingToOccludedTransitionViewModel = dozingToOccludedTransitionViewModel;
        this.dreamingToLockscreenTransitionViewModel = dreamingToLockscreenTransitionViewModel;
        this.dreamingToGoneTransitionViewModel = dreamingToGoneTransitionViewModel;
        this.glanceableHubToLockscreenTransitionViewModel = glanceableHubToLockscreenTransitionViewModel;
        this.goneToAodTransitionViewModel = goneToAodTransitionViewModel;
        this.goneToDozingTransitionViewModel = goneToDozingTransitionViewModel;
        this.goneToDreamingTransitionViewModel = goneToDreamingTransitionViewModel;
        this.goneToLockscreenTransitionViewModel = goneToLockscreenTransitionViewModel;
        this.lockscreenToAodTransitionViewModel = lockscreenToAodTransitionViewModel;
        this.lockscreenToDozingTransitionViewModel = lockscreenToDozingTransitionViewModel;
        this.lockscreenToDreamingTransitionViewModel = lockscreenToDreamingTransitionViewModel;
        this.lockscreenToGlanceableHubTransitionViewModel = lockscreenToGlanceableHubTransitionViewModel;
        this.lockscreenToGoneTransitionViewModel = lockscreenToGoneTransitionViewModel;
        this.lockscreenToOccludedTransitionViewModel = lockscreenToOccludedTransitionViewModel;
        this.lockscreenToPrimaryBouncerTransitionViewModel = lockscreenToPrimaryBouncerTransitionViewModel;
        this.occludedToAodTransitionViewModel = occludedToAodTransitionViewModel;
        this.occludedToDozingTransitionViewModel = occludedToDozingTransitionViewModel;
        this.occludedToLockscreenTransitionViewModel = occludedToLockscreenTransitionViewModel;
        this.primaryBouncerToAodTransitionViewModel = primaryBouncerToAodTransitionViewModel;
        this.primaryBouncerToGoneTransitionViewModel = primaryBouncerToGoneTransitionViewModel;
        this.primaryBouncerToLockscreenTransitionViewModel = primaryBouncerToLockscreenTransitionViewModel;
        this.screenOffAnimationController = screenOffAnimationController;
        this.aodBurnInViewModel = aodBurnInViewModel;
        StateFlowImpl MutableStateFlow = StateFlowKt.MutableStateFlow(new BurnInModel(0, 0, 0.0f, false, 15, null));
        this._burnInModel = MutableStateFlow;
        final ReadonlyStateFlow asStateFlow = FlowKt.asStateFlow(MutableStateFlow);
        this.burnInModel = asStateFlow;
        final ReadonlySharedFlow readonlySharedFlow = keyguardTransitionInteractor.startedKeyguardState;
        final Flow flow = new Flow() { // from class: com.android.systemui.keyguard.ui.viewmodel.KeyguardRootViewModel$special$$inlined$filter$1

            /* renamed from: com.android.systemui.keyguard.ui.viewmodel.KeyguardRootViewModel$special$$inlined$filter$1$2, reason: invalid class name */
            public final class AnonymousClass2 implements FlowCollector {
                public final /* synthetic */ FlowCollector $this_unsafeFlow;

                /* renamed from: com.android.systemui.keyguard.ui.viewmodel.KeyguardRootViewModel$special$$inlined$filter$1$2$1, reason: invalid class name */
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

                public AnonymousClass2(FlowCollector flowCollector) {
                    this.$this_unsafeFlow = flowCollector;
                }

                @Override // kotlinx.coroutines.flow.FlowCollector
                /*
                    Code decompiled incorrectly, please refer to instructions dump.
                    To view partially-correct code enable 'Show inconsistent code' option in preferences
                */
                public final java.lang.Object emit(java.lang.Object r5, kotlin.coroutines.Continuation r6) {
                    /*
                        r4 = this;
                        boolean r0 = r6 instanceof com.android.systemui.keyguard.ui.viewmodel.KeyguardRootViewModel$special$$inlined$filter$1.AnonymousClass2.AnonymousClass1
                        if (r0 == 0) goto L13
                        r0 = r6
                        com.android.systemui.keyguard.ui.viewmodel.KeyguardRootViewModel$special$$inlined$filter$1$2$1 r0 = (com.android.systemui.keyguard.ui.viewmodel.KeyguardRootViewModel$special$$inlined$filter$1.AnonymousClass2.AnonymousClass1) r0
                        int r1 = r0.label
                        r2 = -2147483648(0xffffffff80000000, float:-0.0)
                        r3 = r1 & r2
                        if (r3 == 0) goto L13
                        int r1 = r1 - r2
                        r0.label = r1
                        goto L18
                    L13:
                        com.android.systemui.keyguard.ui.viewmodel.KeyguardRootViewModel$special$$inlined$filter$1$2$1 r0 = new com.android.systemui.keyguard.ui.viewmodel.KeyguardRootViewModel$special$$inlined$filter$1$2$1
                        r0.<init>(r6)
                    L18:
                        java.lang.Object r6 = r0.result
                        kotlin.coroutines.intrinsics.CoroutineSingletons r1 = kotlin.coroutines.intrinsics.CoroutineSingletons.COROUTINE_SUSPENDED
                        int r2 = r0.label
                        r3 = 1
                        if (r2 == 0) goto L2f
                        if (r2 != r3) goto L27
                        kotlin.ResultKt.throwOnFailure(r6)
                        goto L48
                    L27:
                        java.lang.IllegalStateException r4 = new java.lang.IllegalStateException
                        java.lang.String r5 = "call to 'resume' before 'invoke' with coroutine"
                        r4.<init>(r5)
                        throw r4
                    L2f:
                        kotlin.ResultKt.throwOnFailure(r6)
                        r6 = r5
                        com.android.systemui.keyguard.shared.model.KeyguardState r6 = (com.android.systemui.keyguard.shared.model.KeyguardState) r6
                        com.android.systemui.keyguard.shared.model.KeyguardState r2 = com.android.systemui.keyguard.shared.model.KeyguardState.AOD
                        if (r6 == r2) goto L3d
                        com.android.systemui.keyguard.shared.model.KeyguardState r2 = com.android.systemui.keyguard.shared.model.KeyguardState.LOCKSCREEN
                        if (r6 != r2) goto L48
                    L3d:
                        r0.label = r3
                        kotlinx.coroutines.flow.FlowCollector r4 = r4.$this_unsafeFlow
                        java.lang.Object r4 = r4.emit(r5, r0)
                        if (r4 != r1) goto L48
                        return r1
                    L48:
                        kotlin.Unit r4 = kotlin.Unit.INSTANCE
                        return r4
                    */
                    throw new UnsupportedOperationException("Method not decompiled: com.android.systemui.keyguard.ui.viewmodel.KeyguardRootViewModel$special$$inlined$filter$1.AnonymousClass2.emit(java.lang.Object, kotlin.coroutines.Continuation):java.lang.Object");
                }
            }

            @Override // kotlinx.coroutines.flow.Flow
            public final Object collect(FlowCollector flowCollector, Continuation continuation) {
                Object collect = Flow.this.collect(new AnonymousClass2(flowCollector), continuation);
                return collect == CoroutineSingletons.COROUTINE_SUSPENDED ? collect : Unit.INSTANCE;
            }
        };
        new Flow() { // from class: com.android.systemui.keyguard.ui.viewmodel.KeyguardRootViewModel$special$$inlined$map$1

            /* renamed from: com.android.systemui.keyguard.ui.viewmodel.KeyguardRootViewModel$special$$inlined$map$1$2, reason: invalid class name */
            public final class AnonymousClass2 implements FlowCollector {
                public final /* synthetic */ FlowCollector $this_unsafeFlow;

                /* renamed from: com.android.systemui.keyguard.ui.viewmodel.KeyguardRootViewModel$special$$inlined$map$1$2$1, reason: invalid class name */
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

                @Override // kotlinx.coroutines.flow.FlowCollector
                /*
                    Code decompiled incorrectly, please refer to instructions dump.
                    To view partially-correct code enable 'Show inconsistent code' option in preferences
                */
                public final java.lang.Object emit(java.lang.Object r5, kotlin.coroutines.Continuation r6) {
                    /*
                        r4 = this;
                        boolean r0 = r6 instanceof com.android.systemui.keyguard.ui.viewmodel.KeyguardRootViewModel$special$$inlined$map$1.AnonymousClass2.AnonymousClass1
                        if (r0 == 0) goto L13
                        r0 = r6
                        com.android.systemui.keyguard.ui.viewmodel.KeyguardRootViewModel$special$$inlined$map$1$2$1 r0 = (com.android.systemui.keyguard.ui.viewmodel.KeyguardRootViewModel$special$$inlined$map$1.AnonymousClass2.AnonymousClass1) r0
                        int r1 = r0.label
                        r2 = -2147483648(0xffffffff80000000, float:-0.0)
                        r3 = r1 & r2
                        if (r3 == 0) goto L13
                        int r1 = r1 - r2
                        r0.label = r1
                        goto L18
                    L13:
                        com.android.systemui.keyguard.ui.viewmodel.KeyguardRootViewModel$special$$inlined$map$1$2$1 r0 = new com.android.systemui.keyguard.ui.viewmodel.KeyguardRootViewModel$special$$inlined$map$1$2$1
                        r0.<init>(r6)
                    L18:
                        java.lang.Object r6 = r0.result
                        kotlin.coroutines.intrinsics.CoroutineSingletons r1 = kotlin.coroutines.intrinsics.CoroutineSingletons.COROUTINE_SUSPENDED
                        int r2 = r0.label
                        r3 = 1
                        if (r2 == 0) goto L2f
                        if (r2 != r3) goto L27
                        kotlin.ResultKt.throwOnFailure(r6)
                        goto L45
                    L27:
                        java.lang.IllegalStateException r4 = new java.lang.IllegalStateException
                        java.lang.String r5 = "call to 'resume' before 'invoke' with coroutine"
                        r4.<init>(r5)
                        throw r4
                    L2f:
                        kotlin.ResultKt.throwOnFailure(r6)
                        com.android.systemui.keyguard.shared.model.KeyguardState r5 = (com.android.systemui.keyguard.shared.model.KeyguardState) r5
                        java.lang.Integer r5 = new java.lang.Integer
                        r6 = 0
                        r5.<init>(r6)
                        r0.label = r3
                        kotlinx.coroutines.flow.FlowCollector r4 = r4.$this_unsafeFlow
                        java.lang.Object r4 = r4.emit(r5, r0)
                        if (r4 != r1) goto L45
                        return r1
                    L45:
                        kotlin.Unit r4 = kotlin.Unit.INSTANCE
                        return r4
                    */
                    throw new UnsupportedOperationException("Method not decompiled: com.android.systemui.keyguard.ui.viewmodel.KeyguardRootViewModel$special$$inlined$map$1.AnonymousClass2.emit(java.lang.Object, kotlin.coroutines.Continuation):java.lang.Object");
                }
            }

            @Override // kotlinx.coroutines.flow.Flow
            public final Object collect(FlowCollector flowCollector, Continuation continuation) {
                Object collect = Flow.this.collect(new AnonymousClass2(flowCollector), continuation);
                return collect == CoroutineSingletons.COROUTINE_SUSPENDED ? collect : Unit.INSTANCE;
            }
        };
        Edge.Companion companion = Edge.Companion;
        SceneKey sceneKey = Scenes.Gone;
        KeyguardState keyguardState = KeyguardState.AOD;
        companion.getClass();
        new Edge.SceneToState(sceneKey, keyguardState);
        KeyguardState keyguardState2 = KeyguardState.GONE;
        final Flow transition = keyguardTransitionInteractor.transition(new Edge.StateToState(keyguardState2, keyguardState));
        Flow distinctUntilChanged = FlowKt.distinctUntilChanged(new FlowKt__EmittersKt$onStart$$inlined$unsafeFlow$1(new KeyguardRootViewModel$goneToAodTransitionRunning$2(null), new Flow() { // from class: com.android.systemui.keyguard.ui.viewmodel.KeyguardRootViewModel$special$$inlined$map$2

            /* renamed from: com.android.systemui.keyguard.ui.viewmodel.KeyguardRootViewModel$special$$inlined$map$2$2, reason: invalid class name */
            public final class AnonymousClass2 implements FlowCollector {
                public final /* synthetic */ FlowCollector $this_unsafeFlow;

                /* renamed from: com.android.systemui.keyguard.ui.viewmodel.KeyguardRootViewModel$special$$inlined$map$2$2$1, reason: invalid class name */
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

                @Override // kotlinx.coroutines.flow.FlowCollector
                /*
                    Code decompiled incorrectly, please refer to instructions dump.
                    To view partially-correct code enable 'Show inconsistent code' option in preferences
                */
                public final java.lang.Object emit(java.lang.Object r5, kotlin.coroutines.Continuation r6) {
                    /*
                        r4 = this;
                        boolean r0 = r6 instanceof com.android.systemui.keyguard.ui.viewmodel.KeyguardRootViewModel$special$$inlined$map$2.AnonymousClass2.AnonymousClass1
                        if (r0 == 0) goto L13
                        r0 = r6
                        com.android.systemui.keyguard.ui.viewmodel.KeyguardRootViewModel$special$$inlined$map$2$2$1 r0 = (com.android.systemui.keyguard.ui.viewmodel.KeyguardRootViewModel$special$$inlined$map$2.AnonymousClass2.AnonymousClass1) r0
                        int r1 = r0.label
                        r2 = -2147483648(0xffffffff80000000, float:-0.0)
                        r3 = r1 & r2
                        if (r3 == 0) goto L13
                        int r1 = r1 - r2
                        r0.label = r1
                        goto L18
                    L13:
                        com.android.systemui.keyguard.ui.viewmodel.KeyguardRootViewModel$special$$inlined$map$2$2$1 r0 = new com.android.systemui.keyguard.ui.viewmodel.KeyguardRootViewModel$special$$inlined$map$2$2$1
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
                        com.android.systemui.keyguard.shared.model.TransitionStep r5 = (com.android.systemui.keyguard.shared.model.TransitionStep) r5
                        com.android.systemui.keyguard.shared.model.TransitionState r5 = r5.transitionState
                        com.android.systemui.keyguard.shared.model.TransitionState r6 = com.android.systemui.keyguard.shared.model.TransitionState.STARTED
                        if (r5 == r6) goto L41
                        com.android.systemui.keyguard.shared.model.TransitionState r6 = com.android.systemui.keyguard.shared.model.TransitionState.RUNNING
                        if (r5 != r6) goto L3f
                        goto L41
                    L3f:
                        r5 = 0
                        goto L42
                    L41:
                        r5 = r3
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
                    throw new UnsupportedOperationException("Method not decompiled: com.android.systemui.keyguard.ui.viewmodel.KeyguardRootViewModel$special$$inlined$map$2.AnonymousClass2.emit(java.lang.Object, kotlin.coroutines.Continuation):java.lang.Object");
                }
            }

            @Override // kotlinx.coroutines.flow.Flow
            public final Object collect(FlowCollector flowCollector, Continuation continuation) {
                Object collect = Flow.this.collect(new AnonymousClass2(flowCollector), continuation);
                return collect == CoroutineSingletons.COROUTINE_SUSPENDED ? collect : Unit.INSTANCE;
            }
        }));
        KeyguardState keyguardState3 = KeyguardState.LOCKSCREEN;
        Flow distinctUntilChanged2 = FlowKt.distinctUntilChanged(new FlowKt__ZipKt$combine$$inlined$unsafeFlow$1(new FlowKt__EmittersKt$onStart$$inlined$unsafeFlow$1(new KeyguardRootViewModel$isOnLockscreen$1(null), keyguardTransitionInteractor.isFinishedInState(keyguardState3)), BooleanFlowOperators.INSTANCE.anyOf(keyguardTransitionInteractor.isInTransition(Edge.Companion.create$default(companion, null, keyguardState3, 1), null), keyguardTransitionInteractor.isInTransition(Edge.Companion.create$default(companion, keyguardState3, null, 2), null)), new KeyguardRootViewModel$isOnLockscreen$2(null)));
        Flow isInTransition = keyguardTransitionInteractor.isInTransition(new Edge.StateToScene(keyguardState3, sceneKey), new Edge.StateToState(keyguardState3, keyguardState2));
        KeyguardState keyguardState4 = KeyguardState.PRIMARY_BOUNCER;
        ShadeInteractorImpl shadeInteractorImpl = (ShadeInteractorImpl) shadeInteractor;
        this.alphaOnShadeExpansion = FlowKt.distinctUntilChanged(new SafeFlow(new FlowKt__ZipKt$combineTransform$$inlined$combineTransformUnsafe$FlowKt__ZipKt$5(new Flow[]{isInTransition, keyguardTransitionInteractor.isInTransition(new Edge.StateToScene(keyguardState4, Scenes.Lockscreen), new Edge.StateToState(keyguardState4, keyguardState3)), distinctUntilChanged2, shadeInteractorImpl.baseShadeInteractor.getQsExpansion(), shadeInteractorImpl.baseShadeInteractor.getShadeExpansion()}, null, new KeyguardRootViewModel$alphaOnShadeExpansion$1(null))));
        ReadonlyStateFlow readonlyStateFlow = communalInteractor.isIdleOnCommunal;
        Flags.sceneContainer();
        final MutableSharedFlow transitionValue = keyguardTransitionInteractor.transitionValue(keyguardState2);
        FlowKt__EmittersKt$onStart$$inlined$unsafeFlow$1 flowKt__EmittersKt$onStart$$inlined$unsafeFlow$1 = new FlowKt__EmittersKt$onStart$$inlined$unsafeFlow$1(new KeyguardRootViewModel$hideKeyguard$2(null), new Flow() { // from class: com.android.systemui.keyguard.ui.viewmodel.KeyguardRootViewModel$special$$inlined$map$3

            /* renamed from: com.android.systemui.keyguard.ui.viewmodel.KeyguardRootViewModel$special$$inlined$map$3$2, reason: invalid class name */
            public final class AnonymousClass2 implements FlowCollector {
                public final /* synthetic */ FlowCollector $this_unsafeFlow;

                /* renamed from: com.android.systemui.keyguard.ui.viewmodel.KeyguardRootViewModel$special$$inlined$map$3$2$1, reason: invalid class name */
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

                @Override // kotlinx.coroutines.flow.FlowCollector
                /*
                    Code decompiled incorrectly, please refer to instructions dump.
                    To view partially-correct code enable 'Show inconsistent code' option in preferences
                */
                public final java.lang.Object emit(java.lang.Object r5, kotlin.coroutines.Continuation r6) {
                    /*
                        r4 = this;
                        boolean r0 = r6 instanceof com.android.systemui.keyguard.ui.viewmodel.KeyguardRootViewModel$special$$inlined$map$3.AnonymousClass2.AnonymousClass1
                        if (r0 == 0) goto L13
                        r0 = r6
                        com.android.systemui.keyguard.ui.viewmodel.KeyguardRootViewModel$special$$inlined$map$3$2$1 r0 = (com.android.systemui.keyguard.ui.viewmodel.KeyguardRootViewModel$special$$inlined$map$3.AnonymousClass2.AnonymousClass1) r0
                        int r1 = r0.label
                        r2 = -2147483648(0xffffffff80000000, float:-0.0)
                        r3 = r1 & r2
                        if (r3 == 0) goto L13
                        int r1 = r1 - r2
                        r0.label = r1
                        goto L18
                    L13:
                        com.android.systemui.keyguard.ui.viewmodel.KeyguardRootViewModel$special$$inlined$map$3$2$1 r0 = new com.android.systemui.keyguard.ui.viewmodel.KeyguardRootViewModel$special$$inlined$map$3$2$1
                        r0.<init>(r6)
                    L18:
                        java.lang.Object r6 = r0.result
                        kotlin.coroutines.intrinsics.CoroutineSingletons r1 = kotlin.coroutines.intrinsics.CoroutineSingletons.COROUTINE_SUSPENDED
                        int r2 = r0.label
                        r3 = 1
                        if (r2 == 0) goto L2f
                        if (r2 != r3) goto L27
                        kotlin.ResultKt.throwOnFailure(r6)
                        goto L50
                    L27:
                        java.lang.IllegalStateException r4 = new java.lang.IllegalStateException
                        java.lang.String r5 = "call to 'resume' before 'invoke' with coroutine"
                        r4.<init>(r5)
                        throw r4
                    L2f:
                        kotlin.ResultKt.throwOnFailure(r6)
                        java.lang.Number r5 = (java.lang.Number) r5
                        float r5 = r5.floatValue()
                        r6 = 1065353216(0x3f800000, float:1.0)
                        int r5 = (r5 > r6 ? 1 : (r5 == r6 ? 0 : -1))
                        if (r5 != 0) goto L40
                        r5 = r3
                        goto L41
                    L40:
                        r5 = 0
                    L41:
                        java.lang.Boolean r5 = java.lang.Boolean.valueOf(r5)
                        r0.label = r3
                        kotlinx.coroutines.flow.FlowCollector r4 = r4.$this_unsafeFlow
                        java.lang.Object r4 = r4.emit(r5, r0)
                        if (r4 != r1) goto L50
                        return r1
                    L50:
                        kotlin.Unit r4 = kotlin.Unit.INSTANCE
                        return r4
                    */
                    throw new UnsupportedOperationException("Method not decompiled: com.android.systemui.keyguard.ui.viewmodel.KeyguardRootViewModel$special$$inlined$map$3.AnonymousClass2.emit(java.lang.Object, kotlin.coroutines.Continuation):java.lang.Object");
                }
            }

            @Override // kotlinx.coroutines.flow.Flow
            public final Object collect(FlowCollector flowCollector, Continuation continuation) {
                Object collect = Flow.this.collect(new AnonymousClass2(flowCollector), continuation);
                return collect == CoroutineSingletons.COROUTINE_SUSPENDED ? collect : Unit.INSTANCE;
            }
        });
        final MutableSharedFlow transitionValue2 = keyguardTransitionInteractor.transitionValue(KeyguardState.OCCLUDED);
        FlowKt__EmittersKt$onStart$$inlined$unsafeFlow$1 flowKt__EmittersKt$onStart$$inlined$unsafeFlow$12 = new FlowKt__EmittersKt$onStart$$inlined$unsafeFlow$1(new KeyguardRootViewModel$hideKeyguard$4(null), new Flow() { // from class: com.android.systemui.keyguard.ui.viewmodel.KeyguardRootViewModel$special$$inlined$map$4

            /* renamed from: com.android.systemui.keyguard.ui.viewmodel.KeyguardRootViewModel$special$$inlined$map$4$2, reason: invalid class name */
            public final class AnonymousClass2 implements FlowCollector {
                public final /* synthetic */ FlowCollector $this_unsafeFlow;

                /* renamed from: com.android.systemui.keyguard.ui.viewmodel.KeyguardRootViewModel$special$$inlined$map$4$2$1, reason: invalid class name */
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

                @Override // kotlinx.coroutines.flow.FlowCollector
                /*
                    Code decompiled incorrectly, please refer to instructions dump.
                    To view partially-correct code enable 'Show inconsistent code' option in preferences
                */
                public final java.lang.Object emit(java.lang.Object r5, kotlin.coroutines.Continuation r6) {
                    /*
                        r4 = this;
                        boolean r0 = r6 instanceof com.android.systemui.keyguard.ui.viewmodel.KeyguardRootViewModel$special$$inlined$map$4.AnonymousClass2.AnonymousClass1
                        if (r0 == 0) goto L13
                        r0 = r6
                        com.android.systemui.keyguard.ui.viewmodel.KeyguardRootViewModel$special$$inlined$map$4$2$1 r0 = (com.android.systemui.keyguard.ui.viewmodel.KeyguardRootViewModel$special$$inlined$map$4.AnonymousClass2.AnonymousClass1) r0
                        int r1 = r0.label
                        r2 = -2147483648(0xffffffff80000000, float:-0.0)
                        r3 = r1 & r2
                        if (r3 == 0) goto L13
                        int r1 = r1 - r2
                        r0.label = r1
                        goto L18
                    L13:
                        com.android.systemui.keyguard.ui.viewmodel.KeyguardRootViewModel$special$$inlined$map$4$2$1 r0 = new com.android.systemui.keyguard.ui.viewmodel.KeyguardRootViewModel$special$$inlined$map$4$2$1
                        r0.<init>(r6)
                    L18:
                        java.lang.Object r6 = r0.result
                        kotlin.coroutines.intrinsics.CoroutineSingletons r1 = kotlin.coroutines.intrinsics.CoroutineSingletons.COROUTINE_SUSPENDED
                        int r2 = r0.label
                        r3 = 1
                        if (r2 == 0) goto L2f
                        if (r2 != r3) goto L27
                        kotlin.ResultKt.throwOnFailure(r6)
                        goto L50
                    L27:
                        java.lang.IllegalStateException r4 = new java.lang.IllegalStateException
                        java.lang.String r5 = "call to 'resume' before 'invoke' with coroutine"
                        r4.<init>(r5)
                        throw r4
                    L2f:
                        kotlin.ResultKt.throwOnFailure(r6)
                        java.lang.Number r5 = (java.lang.Number) r5
                        float r5 = r5.floatValue()
                        r6 = 1065353216(0x3f800000, float:1.0)
                        int r5 = (r5 > r6 ? 1 : (r5 == r6 ? 0 : -1))
                        if (r5 != 0) goto L40
                        r5 = r3
                        goto L41
                    L40:
                        r5 = 0
                    L41:
                        java.lang.Boolean r5 = java.lang.Boolean.valueOf(r5)
                        r0.label = r3
                        kotlinx.coroutines.flow.FlowCollector r4 = r4.$this_unsafeFlow
                        java.lang.Object r4 = r4.emit(r5, r0)
                        if (r4 != r1) goto L50
                        return r1
                    L50:
                        kotlin.Unit r4 = kotlin.Unit.INSTANCE
                        return r4
                    */
                    throw new UnsupportedOperationException("Method not decompiled: com.android.systemui.keyguard.ui.viewmodel.KeyguardRootViewModel$special$$inlined$map$4.AnonymousClass2.emit(java.lang.Object, kotlin.coroutines.Continuation):java.lang.Object");
                }
            }

            @Override // kotlinx.coroutines.flow.Flow
            public final Object collect(FlowCollector flowCollector, Continuation continuation) {
                Object collect = Flow.this.collect(new AnonymousClass2(flowCollector), continuation);
                return collect == CoroutineSingletons.COROUTINE_SUSPENDED ? collect : Unit.INSTANCE;
            }
        });
        final MutableSharedFlow transitionValue3 = keyguardTransitionInteractor.transitionValue(KeyguardState.DREAMING);
        this.hideKeyguard = FlowKt.distinctUntilChanged(FlowKt.combine(readonlyStateFlow, flowKt__EmittersKt$onStart$$inlined$unsafeFlow$1, flowKt__EmittersKt$onStart$$inlined$unsafeFlow$12, new FlowKt__EmittersKt$onStart$$inlined$unsafeFlow$1(new KeyguardRootViewModel$hideKeyguard$6(null), new Flow() { // from class: com.android.systemui.keyguard.ui.viewmodel.KeyguardRootViewModel$special$$inlined$map$5

            /* renamed from: com.android.systemui.keyguard.ui.viewmodel.KeyguardRootViewModel$special$$inlined$map$5$2, reason: invalid class name */
            public final class AnonymousClass2 implements FlowCollector {
                public final /* synthetic */ FlowCollector $this_unsafeFlow;

                /* renamed from: com.android.systemui.keyguard.ui.viewmodel.KeyguardRootViewModel$special$$inlined$map$5$2$1, reason: invalid class name */
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

                @Override // kotlinx.coroutines.flow.FlowCollector
                /*
                    Code decompiled incorrectly, please refer to instructions dump.
                    To view partially-correct code enable 'Show inconsistent code' option in preferences
                */
                public final java.lang.Object emit(java.lang.Object r5, kotlin.coroutines.Continuation r6) {
                    /*
                        r4 = this;
                        boolean r0 = r6 instanceof com.android.systemui.keyguard.ui.viewmodel.KeyguardRootViewModel$special$$inlined$map$5.AnonymousClass2.AnonymousClass1
                        if (r0 == 0) goto L13
                        r0 = r6
                        com.android.systemui.keyguard.ui.viewmodel.KeyguardRootViewModel$special$$inlined$map$5$2$1 r0 = (com.android.systemui.keyguard.ui.viewmodel.KeyguardRootViewModel$special$$inlined$map$5.AnonymousClass2.AnonymousClass1) r0
                        int r1 = r0.label
                        r2 = -2147483648(0xffffffff80000000, float:-0.0)
                        r3 = r1 & r2
                        if (r3 == 0) goto L13
                        int r1 = r1 - r2
                        r0.label = r1
                        goto L18
                    L13:
                        com.android.systemui.keyguard.ui.viewmodel.KeyguardRootViewModel$special$$inlined$map$5$2$1 r0 = new com.android.systemui.keyguard.ui.viewmodel.KeyguardRootViewModel$special$$inlined$map$5$2$1
                        r0.<init>(r6)
                    L18:
                        java.lang.Object r6 = r0.result
                        kotlin.coroutines.intrinsics.CoroutineSingletons r1 = kotlin.coroutines.intrinsics.CoroutineSingletons.COROUTINE_SUSPENDED
                        int r2 = r0.label
                        r3 = 1
                        if (r2 == 0) goto L2f
                        if (r2 != r3) goto L27
                        kotlin.ResultKt.throwOnFailure(r6)
                        goto L50
                    L27:
                        java.lang.IllegalStateException r4 = new java.lang.IllegalStateException
                        java.lang.String r5 = "call to 'resume' before 'invoke' with coroutine"
                        r4.<init>(r5)
                        throw r4
                    L2f:
                        kotlin.ResultKt.throwOnFailure(r6)
                        java.lang.Number r5 = (java.lang.Number) r5
                        float r5 = r5.floatValue()
                        r6 = 1065353216(0x3f800000, float:1.0)
                        int r5 = (r5 > r6 ? 1 : (r5 == r6 ? 0 : -1))
                        if (r5 != 0) goto L40
                        r5 = r3
                        goto L41
                    L40:
                        r5 = 0
                    L41:
                        java.lang.Boolean r5 = java.lang.Boolean.valueOf(r5)
                        r0.label = r3
                        kotlinx.coroutines.flow.FlowCollector r4 = r4.$this_unsafeFlow
                        java.lang.Object r4 = r4.emit(r5, r0)
                        if (r4 != r1) goto L50
                        return r1
                    L50:
                        kotlin.Unit r4 = kotlin.Unit.INSTANCE
                        return r4
                    */
                    throw new UnsupportedOperationException("Method not decompiled: com.android.systemui.keyguard.ui.viewmodel.KeyguardRootViewModel$special$$inlined$map$5.AnonymousClass2.emit(java.lang.Object, kotlin.coroutines.Continuation):java.lang.Object");
                }
            }

            @Override // kotlinx.coroutines.flow.Flow
            public final Object collect(FlowCollector flowCollector, Continuation continuation) {
                Object collect = Flow.this.collect(new AnonymousClass2(flowCollector), continuation);
                return collect == CoroutineSingletons.COROUTINE_SUSPENDED ? collect : Unit.INSTANCE;
            }
        }), new KeyguardRootViewModel$hideKeyguard$7(null)));
        ReadonlyStateFlow readonlyStateFlow2 = keyguardInteractor.lastRootViewTapPosition;
        SafeFlow safeFlow = aodAlphaViewModel.alpha;
        new Flow() { // from class: com.android.systemui.keyguard.ui.viewmodel.KeyguardRootViewModel$special$$inlined$map$6

            /* renamed from: com.android.systemui.keyguard.ui.viewmodel.KeyguardRootViewModel$special$$inlined$map$6$2, reason: invalid class name */
            public final class AnonymousClass2 implements FlowCollector {
                public final /* synthetic */ FlowCollector $this_unsafeFlow;

                /* renamed from: com.android.systemui.keyguard.ui.viewmodel.KeyguardRootViewModel$special$$inlined$map$6$2$1, reason: invalid class name */
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

                @Override // kotlinx.coroutines.flow.FlowCollector
                /*
                    Code decompiled incorrectly, please refer to instructions dump.
                    To view partially-correct code enable 'Show inconsistent code' option in preferences
                */
                public final java.lang.Object emit(java.lang.Object r5, kotlin.coroutines.Continuation r6) {
                    /*
                        r4 = this;
                        boolean r0 = r6 instanceof com.android.systemui.keyguard.ui.viewmodel.KeyguardRootViewModel$special$$inlined$map$6.AnonymousClass2.AnonymousClass1
                        if (r0 == 0) goto L13
                        r0 = r6
                        com.android.systemui.keyguard.ui.viewmodel.KeyguardRootViewModel$special$$inlined$map$6$2$1 r0 = (com.android.systemui.keyguard.ui.viewmodel.KeyguardRootViewModel$special$$inlined$map$6.AnonymousClass2.AnonymousClass1) r0
                        int r1 = r0.label
                        r2 = -2147483648(0xffffffff80000000, float:-0.0)
                        r3 = r1 & r2
                        if (r3 == 0) goto L13
                        int r1 = r1 - r2
                        r0.label = r1
                        goto L18
                    L13:
                        com.android.systemui.keyguard.ui.viewmodel.KeyguardRootViewModel$special$$inlined$map$6$2$1 r0 = new com.android.systemui.keyguard.ui.viewmodel.KeyguardRootViewModel$special$$inlined$map$6$2$1
                        r0.<init>(r6)
                    L18:
                        java.lang.Object r6 = r0.result
                        kotlin.coroutines.intrinsics.CoroutineSingletons r1 = kotlin.coroutines.intrinsics.CoroutineSingletons.COROUTINE_SUSPENDED
                        int r2 = r0.label
                        r3 = 1
                        if (r2 == 0) goto L2f
                        if (r2 != r3) goto L27
                        kotlin.ResultKt.throwOnFailure(r6)
                        goto L47
                    L27:
                        java.lang.IllegalStateException r4 = new java.lang.IllegalStateException
                        java.lang.String r5 = "call to 'resume' before 'invoke' with coroutine"
                        r4.<init>(r5)
                        throw r4
                    L2f:
                        kotlin.ResultKt.throwOnFailure(r6)
                        com.android.systemui.keyguard.shared.model.BurnInModel r5 = (com.android.systemui.keyguard.shared.model.BurnInModel) r5
                        int r5 = r5.translationY
                        float r5 = (float) r5
                        java.lang.Float r6 = new java.lang.Float
                        r6.<init>(r5)
                        r0.label = r3
                        kotlinx.coroutines.flow.FlowCollector r4 = r4.$this_unsafeFlow
                        java.lang.Object r4 = r4.emit(r6, r0)
                        if (r4 != r1) goto L47
                        return r1
                    L47:
                        kotlin.Unit r4 = kotlin.Unit.INSTANCE
                        return r4
                    */
                    throw new UnsupportedOperationException("Method not decompiled: com.android.systemui.keyguard.ui.viewmodel.KeyguardRootViewModel$special$$inlined$map$6.AnonymousClass2.emit(java.lang.Object, kotlin.coroutines.Continuation):java.lang.Object");
                }
            }

            @Override // kotlinx.coroutines.flow.Flow
            public final Object collect(FlowCollector flowCollector, Continuation continuation) {
                Object collect = Flow.this.collect(new AnonymousClass2(flowCollector), continuation);
                return collect == CoroutineSingletons.COROUTINE_SUSPENDED ? collect : Unit.INSTANCE;
            }
        };
        FlowKt.merge(new Flow() { // from class: com.android.systemui.keyguard.ui.viewmodel.KeyguardRootViewModel$special$$inlined$map$7

            /* renamed from: com.android.systemui.keyguard.ui.viewmodel.KeyguardRootViewModel$special$$inlined$map$7$2, reason: invalid class name */
            public final class AnonymousClass2 implements FlowCollector {
                public final /* synthetic */ FlowCollector $this_unsafeFlow;

                /* renamed from: com.android.systemui.keyguard.ui.viewmodel.KeyguardRootViewModel$special$$inlined$map$7$2$1, reason: invalid class name */
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

                @Override // kotlinx.coroutines.flow.FlowCollector
                /*
                    Code decompiled incorrectly, please refer to instructions dump.
                    To view partially-correct code enable 'Show inconsistent code' option in preferences
                */
                public final java.lang.Object emit(java.lang.Object r12, kotlin.coroutines.Continuation r13) {
                    /*
                        r11 = this;
                        boolean r0 = r13 instanceof com.android.systemui.keyguard.ui.viewmodel.KeyguardRootViewModel$special$$inlined$map$7.AnonymousClass2.AnonymousClass1
                        if (r0 == 0) goto L13
                        r0 = r13
                        com.android.systemui.keyguard.ui.viewmodel.KeyguardRootViewModel$special$$inlined$map$7$2$1 r0 = (com.android.systemui.keyguard.ui.viewmodel.KeyguardRootViewModel$special$$inlined$map$7.AnonymousClass2.AnonymousClass1) r0
                        int r1 = r0.label
                        r2 = -2147483648(0xffffffff80000000, float:-0.0)
                        r3 = r1 & r2
                        if (r3 == 0) goto L13
                        int r1 = r1 - r2
                        r0.label = r1
                        goto L18
                    L13:
                        com.android.systemui.keyguard.ui.viewmodel.KeyguardRootViewModel$special$$inlined$map$7$2$1 r0 = new com.android.systemui.keyguard.ui.viewmodel.KeyguardRootViewModel$special$$inlined$map$7$2$1
                        r0.<init>(r13)
                    L18:
                        java.lang.Object r13 = r0.result
                        kotlin.coroutines.intrinsics.CoroutineSingletons r1 = kotlin.coroutines.intrinsics.CoroutineSingletons.COROUTINE_SUSPENDED
                        int r2 = r0.label
                        r3 = 1
                        if (r2 == 0) goto L2f
                        if (r2 != r3) goto L27
                        kotlin.ResultKt.throwOnFailure(r13)
                        goto L53
                    L27:
                        java.lang.IllegalStateException r11 = new java.lang.IllegalStateException
                        java.lang.String r12 = "call to 'resume' before 'invoke' with coroutine"
                        r11.<init>(r12)
                        throw r11
                    L2f:
                        kotlin.ResultKt.throwOnFailure(r13)
                        com.android.systemui.keyguard.shared.model.BurnInModel r12 = (com.android.systemui.keyguard.shared.model.BurnInModel) r12
                        com.android.systemui.keyguard.ui.StateToValue r13 = new com.android.systemui.keyguard.ui.StateToValue
                        com.android.systemui.keyguard.shared.model.KeyguardState r6 = com.android.systemui.keyguard.shared.model.KeyguardState.AOD
                        int r12 = r12.translationX
                        float r12 = (float) r12
                        java.lang.Float r8 = new java.lang.Float
                        r8.<init>(r12)
                        r9 = 5
                        r10 = 0
                        r5 = 0
                        r7 = 0
                        r4 = r13
                        r4.<init>(r5, r6, r7, r8, r9, r10)
                        r0.label = r3
                        kotlinx.coroutines.flow.FlowCollector r11 = r11.$this_unsafeFlow
                        java.lang.Object r11 = r11.emit(r13, r0)
                        if (r11 != r1) goto L53
                        return r1
                    L53:
                        kotlin.Unit r11 = kotlin.Unit.INSTANCE
                        return r11
                    */
                    throw new UnsupportedOperationException("Method not decompiled: com.android.systemui.keyguard.ui.viewmodel.KeyguardRootViewModel$special$$inlined$map$7.AnonymousClass2.emit(java.lang.Object, kotlin.coroutines.Continuation):java.lang.Object");
                }
            }

            @Override // kotlinx.coroutines.flow.Flow
            public final Object collect(FlowCollector flowCollector, Continuation continuation) {
                Object collect = Flow.this.collect(new AnonymousClass2(flowCollector), continuation);
                return collect == CoroutineSingletons.COROUTINE_SUSPENDED ? collect : Unit.INSTANCE;
            }
        }, lockscreenToGlanceableHubTransitionViewModel.keyguardTranslationX, glanceableHubToLockscreenTransitionViewModel.keyguardTranslationX);
        new Flow() { // from class: com.android.systemui.keyguard.ui.viewmodel.KeyguardRootViewModel$special$$inlined$map$8

            /* renamed from: com.android.systemui.keyguard.ui.viewmodel.KeyguardRootViewModel$special$$inlined$map$8$2, reason: invalid class name */
            public final class AnonymousClass2 implements FlowCollector {
                public final /* synthetic */ FlowCollector $this_unsafeFlow;

                /* renamed from: com.android.systemui.keyguard.ui.viewmodel.KeyguardRootViewModel$special$$inlined$map$8$2$1, reason: invalid class name */
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

                @Override // kotlinx.coroutines.flow.FlowCollector
                /*
                    Code decompiled incorrectly, please refer to instructions dump.
                    To view partially-correct code enable 'Show inconsistent code' option in preferences
                */
                public final java.lang.Object emit(java.lang.Object r5, kotlin.coroutines.Continuation r6) {
                    /*
                        r4 = this;
                        boolean r0 = r6 instanceof com.android.systemui.keyguard.ui.viewmodel.KeyguardRootViewModel$special$$inlined$map$8.AnonymousClass2.AnonymousClass1
                        if (r0 == 0) goto L13
                        r0 = r6
                        com.android.systemui.keyguard.ui.viewmodel.KeyguardRootViewModel$special$$inlined$map$8$2$1 r0 = (com.android.systemui.keyguard.ui.viewmodel.KeyguardRootViewModel$special$$inlined$map$8.AnonymousClass2.AnonymousClass1) r0
                        int r1 = r0.label
                        r2 = -2147483648(0xffffffff80000000, float:-0.0)
                        r3 = r1 & r2
                        if (r3 == 0) goto L13
                        int r1 = r1 - r2
                        r0.label = r1
                        goto L18
                    L13:
                        com.android.systemui.keyguard.ui.viewmodel.KeyguardRootViewModel$special$$inlined$map$8$2$1 r0 = new com.android.systemui.keyguard.ui.viewmodel.KeyguardRootViewModel$special$$inlined$map$8$2$1
                        r0.<init>(r6)
                    L18:
                        java.lang.Object r6 = r0.result
                        kotlin.coroutines.intrinsics.CoroutineSingletons r1 = kotlin.coroutines.intrinsics.CoroutineSingletons.COROUTINE_SUSPENDED
                        int r2 = r0.label
                        r3 = 1
                        if (r2 == 0) goto L2f
                        if (r2 != r3) goto L27
                        kotlin.ResultKt.throwOnFailure(r6)
                        goto L48
                    L27:
                        java.lang.IllegalStateException r4 = new java.lang.IllegalStateException
                        java.lang.String r5 = "call to 'resume' before 'invoke' with coroutine"
                        r4.<init>(r5)
                        throw r4
                    L2f:
                        kotlin.ResultKt.throwOnFailure(r6)
                        com.android.systemui.keyguard.shared.model.BurnInModel r5 = (com.android.systemui.keyguard.shared.model.BurnInModel) r5
                        com.android.systemui.keyguard.ui.viewmodel.BurnInScaleViewModel r6 = new com.android.systemui.keyguard.ui.viewmodel.BurnInScaleViewModel
                        float r2 = r5.scale
                        boolean r5 = r5.scaleClockOnly
                        r6.<init>(r2, r5)
                        r0.label = r3
                        kotlinx.coroutines.flow.FlowCollector r4 = r4.$this_unsafeFlow
                        java.lang.Object r4 = r4.emit(r6, r0)
                        if (r4 != r1) goto L48
                        return r1
                    L48:
                        kotlin.Unit r4 = kotlin.Unit.INSTANCE
                        return r4
                    */
                    throw new UnsupportedOperationException("Method not decompiled: com.android.systemui.keyguard.ui.viewmodel.KeyguardRootViewModel$special$$inlined$map$8.AnonymousClass2.emit(java.lang.Object, kotlin.coroutines.Continuation):java.lang.Object");
                }
            }

            @Override // kotlinx.coroutines.flow.Flow
            public final Object collect(FlowCollector flowCollector, Continuation continuation) {
                Object collect = Flow.this.collect(new AnonymousClass2(flowCollector), continuation);
                return collect == CoroutineSingletons.COROUTINE_SUSPENDED ? collect : Unit.INSTANCE;
            }
        };
        final ReadonlySharedFlow readonlySharedFlow2 = keyguardTransitionInteractor.finishedKeyguardState;
        Flow flow2 = new Flow() { // from class: com.android.systemui.keyguard.ui.viewmodel.KeyguardRootViewModel$special$$inlined$map$9

            /* renamed from: com.android.systemui.keyguard.ui.viewmodel.KeyguardRootViewModel$special$$inlined$map$9$2, reason: invalid class name */
            public final class AnonymousClass2 implements FlowCollector {
                public final /* synthetic */ FlowCollector $this_unsafeFlow;

                /* renamed from: com.android.systemui.keyguard.ui.viewmodel.KeyguardRootViewModel$special$$inlined$map$9$2$1, reason: invalid class name */
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

                @Override // kotlinx.coroutines.flow.FlowCollector
                /*
                    Code decompiled incorrectly, please refer to instructions dump.
                    To view partially-correct code enable 'Show inconsistent code' option in preferences
                */
                public final java.lang.Object emit(java.lang.Object r5, kotlin.coroutines.Continuation r6) {
                    /*
                        r4 = this;
                        boolean r0 = r6 instanceof com.android.systemui.keyguard.ui.viewmodel.KeyguardRootViewModel$special$$inlined$map$9.AnonymousClass2.AnonymousClass1
                        if (r0 == 0) goto L13
                        r0 = r6
                        com.android.systemui.keyguard.ui.viewmodel.KeyguardRootViewModel$special$$inlined$map$9$2$1 r0 = (com.android.systemui.keyguard.ui.viewmodel.KeyguardRootViewModel$special$$inlined$map$9.AnonymousClass2.AnonymousClass1) r0
                        int r1 = r0.label
                        r2 = -2147483648(0xffffffff80000000, float:-0.0)
                        r3 = r1 & r2
                        if (r3 == 0) goto L13
                        int r1 = r1 - r2
                        r0.label = r1
                        goto L18
                    L13:
                        com.android.systemui.keyguard.ui.viewmodel.KeyguardRootViewModel$special$$inlined$map$9$2$1 r0 = new com.android.systemui.keyguard.ui.viewmodel.KeyguardRootViewModel$special$$inlined$map$9$2$1
                        r0.<init>(r6)
                    L18:
                        java.lang.Object r6 = r0.result
                        kotlin.coroutines.intrinsics.CoroutineSingletons r1 = kotlin.coroutines.intrinsics.CoroutineSingletons.COROUTINE_SUSPENDED
                        int r2 = r0.label
                        r3 = 1
                        if (r2 == 0) goto L2f
                        if (r2 != r3) goto L27
                        kotlin.ResultKt.throwOnFailure(r6)
                        goto L4f
                    L27:
                        java.lang.IllegalStateException r4 = new java.lang.IllegalStateException
                        java.lang.String r5 = "call to 'resume' before 'invoke' with coroutine"
                        r4.<init>(r5)
                        throw r4
                    L2f:
                        kotlin.ResultKt.throwOnFailure(r6)
                        com.android.systemui.keyguard.shared.model.KeyguardState r5 = (com.android.systemui.keyguard.shared.model.KeyguardState) r5
                        com.android.systemui.keyguard.shared.model.KeyguardState$Companion r6 = com.android.systemui.keyguard.shared.model.KeyguardState.Companion
                        r6.getClass()
                        com.android.systemui.keyguard.shared.model.KeyguardState r6 = com.android.systemui.keyguard.shared.model.KeyguardState.GONE
                        if (r5 == r6) goto L3f
                        r5 = r3
                        goto L40
                    L3f:
                        r5 = 0
                    L40:
                        java.lang.Boolean r5 = java.lang.Boolean.valueOf(r5)
                        r0.label = r3
                        kotlinx.coroutines.flow.FlowCollector r4 = r4.$this_unsafeFlow
                        java.lang.Object r4 = r4.emit(r5, r0)
                        if (r4 != r1) goto L4f
                        return r1
                    L4f:
                        kotlin.Unit r4 = kotlin.Unit.INSTANCE
                        return r4
                    */
                    throw new UnsupportedOperationException("Method not decompiled: com.android.systemui.keyguard.ui.viewmodel.KeyguardRootViewModel$special$$inlined$map$9.AnonymousClass2.emit(java.lang.Object, kotlin.coroutines.Continuation):java.lang.Object");
                }
            }

            @Override // kotlinx.coroutines.flow.Flow
            public final Object collect(FlowCollector flowCollector, Continuation continuation) {
                Object collect = Flow.this.collect(new AnonymousClass2(flowCollector), continuation);
                return collect == CoroutineSingletons.COROUTINE_SUSPENDED ? collect : Unit.INSTANCE;
            }
        };
        ReadonlyStateFlow readonlyStateFlow3 = deviceEntryInteractor.isBypassEnabled;
        Flow animatedValueFlow = AnimatedValueKt.toAnimatedValueFlow(com.android.systemui.util.kotlin.FlowKt.sample(com.android.systemui.util.kotlin.FlowKt.pairwise(notificationsKeyguardInteractor.areNotificationsFullyHidden, null), readonlyStateFlow3, new KeyguardRootViewModel$areNotifsFullyHiddenAnimated$1(this, null)));
        final Flow pairwise = com.android.systemui.util.kotlin.FlowKt.pairwise(notificationsKeyguardInteractor.isPulseExpanding, null);
        FlowKt.stateIn(FlowKt.combine(distinctUntilChanged, flow2, readonlyStateFlow3, animatedValueFlow, AnimatedValueKt.toAnimatedValueFlow(new Flow() { // from class: com.android.systemui.keyguard.ui.viewmodel.KeyguardRootViewModel$isPulseExpandingAnimated$$inlined$map$1

            /* renamed from: com.android.systemui.keyguard.ui.viewmodel.KeyguardRootViewModel$isPulseExpandingAnimated$$inlined$map$1$2, reason: invalid class name */
            public final class AnonymousClass2 implements FlowCollector {
                public final /* synthetic */ FlowCollector $this_unsafeFlow;

                /* renamed from: com.android.systemui.keyguard.ui.viewmodel.KeyguardRootViewModel$isPulseExpandingAnimated$$inlined$map$1$2$1, reason: invalid class name */
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

                @Override // kotlinx.coroutines.flow.FlowCollector
                /*
                    Code decompiled incorrectly, please refer to instructions dump.
                    To view partially-correct code enable 'Show inconsistent code' option in preferences
                */
                public final java.lang.Object emit(java.lang.Object r5, kotlin.coroutines.Continuation r6) {
                    /*
                        r4 = this;
                        boolean r0 = r6 instanceof com.android.systemui.keyguard.ui.viewmodel.KeyguardRootViewModel$isPulseExpandingAnimated$$inlined$map$1.AnonymousClass2.AnonymousClass1
                        if (r0 == 0) goto L13
                        r0 = r6
                        com.android.systemui.keyguard.ui.viewmodel.KeyguardRootViewModel$isPulseExpandingAnimated$$inlined$map$1$2$1 r0 = (com.android.systemui.keyguard.ui.viewmodel.KeyguardRootViewModel$isPulseExpandingAnimated$$inlined$map$1.AnonymousClass2.AnonymousClass1) r0
                        int r1 = r0.label
                        r2 = -2147483648(0xffffffff80000000, float:-0.0)
                        r3 = r1 & r2
                        if (r3 == 0) goto L13
                        int r1 = r1 - r2
                        r0.label = r1
                        goto L18
                    L13:
                        com.android.systemui.keyguard.ui.viewmodel.KeyguardRootViewModel$isPulseExpandingAnimated$$inlined$map$1$2$1 r0 = new com.android.systemui.keyguard.ui.viewmodel.KeyguardRootViewModel$isPulseExpandingAnimated$$inlined$map$1$2$1
                        r0.<init>(r6)
                    L18:
                        java.lang.Object r6 = r0.result
                        kotlin.coroutines.intrinsics.CoroutineSingletons r1 = kotlin.coroutines.intrinsics.CoroutineSingletons.COROUTINE_SUSPENDED
                        int r2 = r0.label
                        r3 = 1
                        if (r2 == 0) goto L2f
                        if (r2 != r3) goto L27
                        kotlin.ResultKt.throwOnFailure(r6)
                        goto L5d
                    L27:
                        java.lang.IllegalStateException r4 = new java.lang.IllegalStateException
                        java.lang.String r5 = "call to 'resume' before 'invoke' with coroutine"
                        r4.<init>(r5)
                        throw r4
                    L2f:
                        kotlin.ResultKt.throwOnFailure(r6)
                        com.android.systemui.util.kotlin.WithPrev r5 = (com.android.systemui.util.kotlin.WithPrev) r5
                        java.lang.Object r6 = r5.component1()
                        java.lang.Boolean r6 = (java.lang.Boolean) r6
                        java.lang.Object r5 = r5.component2()
                        java.lang.Boolean r5 = (java.lang.Boolean) r5
                        boolean r5 = r5.booleanValue()
                        com.android.systemui.util.ui.AnimatableEvent r2 = new com.android.systemui.util.ui.AnimatableEvent
                        java.lang.Boolean r5 = java.lang.Boolean.valueOf(r5)
                        if (r6 == 0) goto L4e
                        r6 = r3
                        goto L4f
                    L4e:
                        r6 = 0
                    L4f:
                        r2.<init>(r5, r6)
                        r0.label = r3
                        kotlinx.coroutines.flow.FlowCollector r4 = r4.$this_unsafeFlow
                        java.lang.Object r4 = r4.emit(r2, r0)
                        if (r4 != r1) goto L5d
                        return r1
                    L5d:
                        kotlin.Unit r4 = kotlin.Unit.INSTANCE
                        return r4
                    */
                    throw new UnsupportedOperationException("Method not decompiled: com.android.systemui.keyguard.ui.viewmodel.KeyguardRootViewModel$isPulseExpandingAnimated$$inlined$map$1.AnonymousClass2.emit(java.lang.Object, kotlin.coroutines.Continuation):java.lang.Object");
                }
            }

            @Override // kotlinx.coroutines.flow.Flow
            public final Object collect(FlowCollector flowCollector, Continuation continuation) {
                Object collect = Flow.this.collect(new AnonymousClass2(flowCollector), continuation);
                return collect == CoroutineSingletons.COROUTINE_SUSPENDED ? collect : Unit.INSTANCE;
            }
        }), new KeyguardRootViewModel$isNotifIconContainerVisible$2(this, null)), coroutineScope, SharingStarted.Companion.WhileSubscribed$default(SharingStarted.Companion, 3), new AnimatedValue.NotAnimating(Boolean.FALSE));
    }

    public final Flow alpha(final ViewStateAccessor viewStateAccessor) {
        Flow flow = this.keyguardInteractor.dismissAlpha;
        AlternateBouncerToGoneTransitionViewModel alternateBouncerToGoneTransitionViewModel = this.alternateBouncerToGoneTransitionViewModel;
        alternateBouncerToGoneTransitionViewModel.getClass();
        final Ref$FloatRef ref$FloatRef = new Ref$FloatRef();
        ref$FloatRef.element = 1.0f;
        Duration.Companion companion = Duration.Companion;
        DurationUnit durationUnit = DurationUnit.MILLISECONDS;
        KeyguardTransitionAnimationFlow$FlowBuilder$sharedFlow74qcysc$$inlined$mapNotNull$1 m1962sharedFlow74qcysc$default = KeyguardTransitionAnimationFlow.FlowBuilder.m1962sharedFlow74qcysc$default(alternateBouncerToGoneTransitionViewModel.transitionAnimation, DurationKt.toDuration(200, durationUnit), new Function1() { // from class: com.android.systemui.keyguard.ui.viewmodel.AlternateBouncerToGoneTransitionViewModel$lockscreenAlpha$1
            {
                super(1);
            }

            @Override // kotlin.jvm.functions.Function1
            public final Object invoke(Object obj) {
                return Float.valueOf(MathUtils.lerp(Ref$FloatRef.this.element, 0.0f, ((Number) obj).floatValue()));
            }
        }, 0L, new Function0() { // from class: com.android.systemui.keyguard.ui.viewmodel.AlternateBouncerToGoneTransitionViewModel$lockscreenAlpha$2
            {
                super(0);
            }

            @Override // kotlin.jvm.functions.Function0
            public final Object invoke() {
                Ref$FloatRef.this.element = ((Number) viewStateAccessor.alpha.invoke()).floatValue();
                return Unit.INSTANCE;
            }
        }, new Function0() { // from class: com.android.systemui.keyguard.ui.viewmodel.AlternateBouncerToGoneTransitionViewModel$lockscreenAlpha$3
            {
                super(0);
            }

            @Override // kotlin.jvm.functions.Function0
            public final Object invoke() {
                return Float.valueOf(Ref$FloatRef.this.element);
            }
        }, new Function0() { // from class: com.android.systemui.keyguard.ui.viewmodel.AlternateBouncerToGoneTransitionViewModel$lockscreenAlpha$4
            @Override // kotlin.jvm.functions.Function0
            public final /* bridge */ /* synthetic */ Object invoke() {
                return Float.valueOf(0.0f);
            }
        }, null, null, 196);
        AodToGoneTransitionViewModel aodToGoneTransitionViewModel = this.aodToGoneTransitionViewModel;
        aodToGoneTransitionViewModel.getClass();
        final Ref$FloatRef ref$FloatRef2 = new Ref$FloatRef();
        ref$FloatRef2.element = 1.0f;
        KeyguardTransitionAnimationFlow$FlowBuilder$sharedFlow74qcysc$$inlined$mapNotNull$1 m1962sharedFlow74qcysc$default2 = KeyguardTransitionAnimationFlow.FlowBuilder.m1962sharedFlow74qcysc$default(aodToGoneTransitionViewModel.transitionAnimation, DurationKt.toDuration(200, durationUnit), new Function1() { // from class: com.android.systemui.keyguard.ui.viewmodel.AodToGoneTransitionViewModel$lockscreenAlpha$1
            {
                super(1);
            }

            @Override // kotlin.jvm.functions.Function1
            public final Object invoke(Object obj) {
                return Float.valueOf(MathUtils.lerp(Ref$FloatRef.this.element, 0.0f, ((Number) obj).floatValue()));
            }
        }, 0L, new Function0() { // from class: com.android.systemui.keyguard.ui.viewmodel.AodToGoneTransitionViewModel$lockscreenAlpha$2
            {
                super(0);
            }

            @Override // kotlin.jvm.functions.Function0
            public final Object invoke() {
                Ref$FloatRef.this.element = ((Number) viewStateAccessor.alpha.invoke()).floatValue();
                return Unit.INSTANCE;
            }
        }, null, new Function0() { // from class: com.android.systemui.keyguard.ui.viewmodel.AodToGoneTransitionViewModel$lockscreenAlpha$3
            @Override // kotlin.jvm.functions.Function0
            public final /* bridge */ /* synthetic */ Object invoke() {
                return Float.valueOf(0.0f);
            }
        }, null, null, IKnoxCustomManager.Stub.TRANSACTION_getWifiState);
        AodToLockscreenTransitionViewModel aodToLockscreenTransitionViewModel = this.aodToLockscreenTransitionViewModel;
        aodToLockscreenTransitionViewModel.getClass();
        final Ref$FloatRef ref$FloatRef3 = new Ref$FloatRef();
        ref$FloatRef3.element = 1.0f;
        Duration.Companion companion2 = Duration.Companion;
        KeyguardTransitionAnimationFlow$FlowBuilder$sharedFlow74qcysc$$inlined$mapNotNull$1 m1962sharedFlow74qcysc$default3 = KeyguardTransitionAnimationFlow.FlowBuilder.m1962sharedFlow74qcysc$default(aodToLockscreenTransitionViewModel.transitionAnimation, DurationKt.toDuration(500, DurationUnit.MILLISECONDS), new Function1() { // from class: com.android.systemui.keyguard.ui.viewmodel.AodToLockscreenTransitionViewModel$lockscreenAlpha$1
            {
                super(1);
            }

            @Override // kotlin.jvm.functions.Function1
            public final Object invoke(Object obj) {
                return Float.valueOf(MathUtils.lerp(Ref$FloatRef.this.element, 1.0f, ((Number) obj).floatValue()));
            }
        }, 0L, new Function0() { // from class: com.android.systemui.keyguard.ui.viewmodel.AodToLockscreenTransitionViewModel$lockscreenAlpha$2
            {
                super(0);
            }

            @Override // kotlin.jvm.functions.Function0
            public final Object invoke() {
                Ref$FloatRef.this.element = ((Number) viewStateAccessor.alpha.invoke()).floatValue();
                return Unit.INSTANCE;
            }
        }, null, null, null, null, IKnoxCustomManager.Stub.TRANSACTION_getHardKeyIntentMode);
        AodToOccludedTransitionViewModel aodToOccludedTransitionViewModel = this.aodToOccludedTransitionViewModel;
        aodToOccludedTransitionViewModel.getClass();
        final Ref$FloatRef ref$FloatRef4 = new Ref$FloatRef();
        Duration.Companion companion3 = Duration.Companion;
        DurationUnit durationUnit2 = DurationUnit.MILLISECONDS;
        long duration = DurationKt.toDuration(IKnoxCustomManager.Stub.TRANSACTION_addDexURLShortcutExtend, durationUnit2);
        long duration2 = DurationKt.toDuration(100, durationUnit2);
        KeyguardTransitionAnimationFlow$FlowBuilder$sharedFlow74qcysc$$inlined$mapNotNull$1 m1962sharedFlow74qcysc$default4 = KeyguardTransitionAnimationFlow.FlowBuilder.m1962sharedFlow74qcysc$default(aodToOccludedTransitionViewModel.transitionAnimation, duration, new Function1() { // from class: com.android.systemui.keyguard.ui.viewmodel.AodToOccludedTransitionViewModel$lockscreenAlpha$1
            {
                super(1);
            }

            @Override // kotlin.jvm.functions.Function1
            public final Object invoke(Object obj) {
                return Float.valueOf(MathUtils.lerp(Ref$FloatRef.this.element, 0.0f, ((Number) obj).floatValue()));
            }
        }, duration2, new Function0() { // from class: com.android.systemui.keyguard.ui.viewmodel.AodToOccludedTransitionViewModel$lockscreenAlpha$2
            {
                super(0);
            }

            @Override // kotlin.jvm.functions.Function0
            public final Object invoke() {
                Ref$FloatRef.this.element = ((Number) viewStateAccessor.alpha.invoke()).floatValue();
                return Unit.INSTANCE;
            }
        }, new Function0() { // from class: com.android.systemui.keyguard.ui.viewmodel.AodToOccludedTransitionViewModel$lockscreenAlpha$3
            @Override // kotlin.jvm.functions.Function0
            public final /* bridge */ /* synthetic */ Object invoke() {
                return Float.valueOf(0.0f);
            }
        }, null, null, null, IKnoxCustomManager.Stub.TRANSACTION_setUsbConnectionType);
        DozingToGoneTransitionViewModel dozingToGoneTransitionViewModel = this.dozingToGoneTransitionViewModel;
        dozingToGoneTransitionViewModel.getClass();
        KeyguardTransitionAnimationFlow$FlowBuilder$sharedFlow74qcysc$$inlined$mapNotNull$1 m1962sharedFlow74qcysc$default5 = KeyguardTransitionAnimationFlow.FlowBuilder.m1962sharedFlow74qcysc$default(dozingToGoneTransitionViewModel.transitionAnimation, DurationKt.toDuration(200, durationUnit), new Function1() { // from class: com.android.systemui.keyguard.ui.viewmodel.DozingToGoneTransitionViewModel$lockscreenAlpha$1
            @Override // kotlin.jvm.functions.Function1
            public final /* bridge */ /* synthetic */ Object invoke(Object obj) {
                ((Number) obj).floatValue();
                return Float.valueOf(0.0f);
            }
        }, 0L, new Function0() { // from class: com.android.systemui.keyguard.ui.viewmodel.DozingToGoneTransitionViewModel$lockscreenAlpha$2
            @Override // kotlin.jvm.functions.Function0
            public final /* bridge */ /* synthetic */ Object invoke() {
                return Unit.INSTANCE;
            }
        }, null, null, null, null, IKnoxCustomManager.Stub.TRANSACTION_getHardKeyIntentMode);
        KeyguardTransitionAnimationFlow$FlowBuilder$sharedFlow74qcysc$$inlined$mapNotNull$1 keyguardTransitionAnimationFlow$FlowBuilder$sharedFlow74qcysc$$inlined$mapNotNull$1 = this.dozingToLockscreenTransitionViewModel.lockscreenAlpha;
        DozingToOccludedTransitionViewModel dozingToOccludedTransitionViewModel = this.dozingToOccludedTransitionViewModel;
        dozingToOccludedTransitionViewModel.getClass();
        final Ref$FloatRef ref$FloatRef5 = new Ref$FloatRef();
        Duration.Companion companion4 = Duration.Companion;
        DurationUnit durationUnit3 = DurationUnit.MILLISECONDS;
        long duration3 = DurationKt.toDuration(IKnoxCustomManager.Stub.TRANSACTION_addDexURLShortcutExtend, durationUnit3);
        long duration4 = DurationKt.toDuration(100, durationUnit3);
        KeyguardTransitionAnimationFlow$FlowBuilder$sharedFlow74qcysc$$inlined$mapNotNull$1 m1962sharedFlow74qcysc$default6 = KeyguardTransitionAnimationFlow.FlowBuilder.m1962sharedFlow74qcysc$default(dozingToOccludedTransitionViewModel.transitionAnimation, duration3, new Function1() { // from class: com.android.systemui.keyguard.ui.viewmodel.DozingToOccludedTransitionViewModel$lockscreenAlpha$1
            {
                super(1);
            }

            @Override // kotlin.jvm.functions.Function1
            public final Object invoke(Object obj) {
                return Float.valueOf(MathUtils.lerp(Ref$FloatRef.this.element, 0.0f, ((Number) obj).floatValue()));
            }
        }, duration4, new Function0() { // from class: com.android.systemui.keyguard.ui.viewmodel.DozingToOccludedTransitionViewModel$lockscreenAlpha$2
            {
                super(0);
            }

            @Override // kotlin.jvm.functions.Function0
            public final Object invoke() {
                Ref$FloatRef.this.element = ((Number) viewStateAccessor.alpha.invoke()).floatValue();
                return Unit.INSTANCE;
            }
        }, new Function0() { // from class: com.android.systemui.keyguard.ui.viewmodel.DozingToOccludedTransitionViewModel$lockscreenAlpha$3
            @Override // kotlin.jvm.functions.Function0
            public final /* bridge */ /* synthetic */ Object invoke() {
                return Float.valueOf(0.0f);
            }
        }, null, null, null, IKnoxCustomManager.Stub.TRANSACTION_setUsbConnectionType);
        KeyguardTransitionAnimationFlow$FlowBuilder$sharedFlow74qcysc$$inlined$mapNotNull$1 keyguardTransitionAnimationFlow$FlowBuilder$sharedFlow74qcysc$$inlined$mapNotNull$12 = this.dreamingToGoneTransitionViewModel.lockscreenAlpha;
        KeyguardTransitionAnimationFlow$FlowBuilder$sharedFlow74qcysc$$inlined$mapNotNull$1 keyguardTransitionAnimationFlow$FlowBuilder$sharedFlow74qcysc$$inlined$mapNotNull$13 = this.dreamingToLockscreenTransitionViewModel.lockscreenAlpha;
        KeyguardTransitionAnimationFlow$FlowBuilder$sharedFlow74qcysc$$inlined$mapNotNull$1 keyguardTransitionAnimationFlow$FlowBuilder$sharedFlow74qcysc$$inlined$mapNotNull$14 = this.glanceableHubToLockscreenTransitionViewModel.keyguardAlpha;
        KeyguardTransitionAnimationFlow$FlowBuilder$sharedFlow74qcysc$$inlined$mapNotNull$1 keyguardTransitionAnimationFlow$FlowBuilder$sharedFlow74qcysc$$inlined$mapNotNull$15 = this.goneToAodTransitionViewModel.enterFromTopAnimationAlpha;
        KeyguardTransitionAnimationFlow$FlowBuilder$sharedFlow74qcysc$$inlined$mapNotNull$1 keyguardTransitionAnimationFlow$FlowBuilder$sharedFlow74qcysc$$inlined$mapNotNull$16 = this.goneToDozingTransitionViewModel.lockscreenAlpha;
        KeyguardTransitionAnimationFlow$FlowBuilder$sharedFlow74qcysc$$inlined$mapNotNull$1 keyguardTransitionAnimationFlow$FlowBuilder$sharedFlow74qcysc$$inlined$mapNotNull$17 = this.goneToDreamingTransitionViewModel.lockscreenAlpha;
        KeyguardTransitionAnimationFlow$FlowBuilder$sharedFlow74qcysc$$inlined$mapNotNull$1 keyguardTransitionAnimationFlow$FlowBuilder$sharedFlow74qcysc$$inlined$mapNotNull$18 = this.goneToLockscreenTransitionViewModel.lockscreenAlpha;
        LockscreenToAodTransitionViewModel lockscreenToAodTransitionViewModel = this.lockscreenToAodTransitionViewModel;
        lockscreenToAodTransitionViewModel.getClass();
        final Ref$FloatRef ref$FloatRef6 = new Ref$FloatRef();
        ref$FloatRef6.element = 1.0f;
        KeyguardTransitionAnimationFlow$FlowBuilder$sharedFlow74qcysc$$inlined$mapNotNull$1 m1962sharedFlow74qcysc$default7 = KeyguardTransitionAnimationFlow.FlowBuilder.m1962sharedFlow74qcysc$default(lockscreenToAodTransitionViewModel.transitionAnimation, DurationKt.toDuration(500, durationUnit), new Function1() { // from class: com.android.systemui.keyguard.ui.viewmodel.LockscreenToAodTransitionViewModel$lockscreenAlpha$1
            {
                super(1);
            }

            @Override // kotlin.jvm.functions.Function1
            public final Object invoke(Object obj) {
                return Float.valueOf(MathUtils.lerp(Ref$FloatRef.this.element, 1.0f, ((Number) obj).floatValue()));
            }
        }, 0L, new Function0() { // from class: com.android.systemui.keyguard.ui.viewmodel.LockscreenToAodTransitionViewModel$lockscreenAlpha$2
            {
                super(0);
            }

            @Override // kotlin.jvm.functions.Function0
            public final Object invoke() {
                Ref$FloatRef.this.element = ((Number) viewStateAccessor.alpha.invoke()).floatValue();
                return Unit.INSTANCE;
            }
        }, null, null, null, null, IKnoxCustomManager.Stub.TRANSACTION_getHardKeyIntentMode);
        KeyguardTransitionAnimationFlow$FlowBuilder$sharedFlow74qcysc$$inlined$mapNotNull$1 keyguardTransitionAnimationFlow$FlowBuilder$sharedFlow74qcysc$$inlined$mapNotNull$19 = this.lockscreenToDozingTransitionViewModel.lockscreenAlpha;
        KeyguardTransitionAnimationFlow$FlowBuilder$sharedFlow74qcysc$$inlined$mapNotNull$1 keyguardTransitionAnimationFlow$FlowBuilder$sharedFlow74qcysc$$inlined$mapNotNull$110 = this.lockscreenToDreamingTransitionViewModel.lockscreenAlpha;
        KeyguardTransitionAnimationFlow$FlowBuilder$sharedFlow74qcysc$$inlined$mapNotNull$1 keyguardTransitionAnimationFlow$FlowBuilder$sharedFlow74qcysc$$inlined$mapNotNull$111 = this.lockscreenToGlanceableHubTransitionViewModel.keyguardAlpha;
        LockscreenToGoneTransitionViewModel lockscreenToGoneTransitionViewModel = this.lockscreenToGoneTransitionViewModel;
        lockscreenToGoneTransitionViewModel.getClass();
        final Ref$FloatRef ref$FloatRef7 = new Ref$FloatRef();
        ref$FloatRef7.element = 1.0f;
        return FlowKt.distinctUntilChanged(new FlowKt__ZipKt$combine$$inlined$unsafeFlow$1(this.hideKeyguard, new FlowKt__EmittersKt$onStart$$inlined$unsafeFlow$1(new KeyguardRootViewModel$alpha$1(null), FlowKt.merge(this.alphaOnShadeExpansion, flow, m1962sharedFlow74qcysc$default, m1962sharedFlow74qcysc$default2, m1962sharedFlow74qcysc$default3, m1962sharedFlow74qcysc$default4, m1962sharedFlow74qcysc$default5, keyguardTransitionAnimationFlow$FlowBuilder$sharedFlow74qcysc$$inlined$mapNotNull$1, m1962sharedFlow74qcysc$default6, keyguardTransitionAnimationFlow$FlowBuilder$sharedFlow74qcysc$$inlined$mapNotNull$12, keyguardTransitionAnimationFlow$FlowBuilder$sharedFlow74qcysc$$inlined$mapNotNull$13, keyguardTransitionAnimationFlow$FlowBuilder$sharedFlow74qcysc$$inlined$mapNotNull$14, keyguardTransitionAnimationFlow$FlowBuilder$sharedFlow74qcysc$$inlined$mapNotNull$15, keyguardTransitionAnimationFlow$FlowBuilder$sharedFlow74qcysc$$inlined$mapNotNull$16, keyguardTransitionAnimationFlow$FlowBuilder$sharedFlow74qcysc$$inlined$mapNotNull$17, keyguardTransitionAnimationFlow$FlowBuilder$sharedFlow74qcysc$$inlined$mapNotNull$18, m1962sharedFlow74qcysc$default7, keyguardTransitionAnimationFlow$FlowBuilder$sharedFlow74qcysc$$inlined$mapNotNull$19, keyguardTransitionAnimationFlow$FlowBuilder$sharedFlow74qcysc$$inlined$mapNotNull$110, keyguardTransitionAnimationFlow$FlowBuilder$sharedFlow74qcysc$$inlined$mapNotNull$111, KeyguardTransitionAnimationFlow.FlowBuilder.m1962sharedFlow74qcysc$default(lockscreenToGoneTransitionViewModel.transitionAnimation, DurationKt.toDuration(200, durationUnit), new Function1() { // from class: com.android.systemui.keyguard.ui.viewmodel.LockscreenToGoneTransitionViewModel$lockscreenAlpha$1
            {
                super(1);
            }

            @Override // kotlin.jvm.functions.Function1
            public final Object invoke(Object obj) {
                return Float.valueOf(MathUtils.lerp(Ref$FloatRef.this.element, 0.0f, ((Number) obj).floatValue()));
            }
        }, 0L, new Function0() { // from class: com.android.systemui.keyguard.ui.viewmodel.LockscreenToGoneTransitionViewModel$lockscreenAlpha$2
            {
                super(0);
            }

            @Override // kotlin.jvm.functions.Function0
            public final Object invoke() {
                Ref$FloatRef.this.element = ((Number) viewStateAccessor.alpha.invoke()).floatValue();
                return Unit.INSTANCE;
            }
        }, new Function0() { // from class: com.android.systemui.keyguard.ui.viewmodel.LockscreenToGoneTransitionViewModel$lockscreenAlpha$3
            @Override // kotlin.jvm.functions.Function0
            public final /* bridge */ /* synthetic */ Object invoke() {
                return Float.valueOf(1.0f);
            }
        }, new Function0() { // from class: com.android.systemui.keyguard.ui.viewmodel.LockscreenToGoneTransitionViewModel$lockscreenAlpha$4
            @Override // kotlin.jvm.functions.Function0
            public final /* bridge */ /* synthetic */ Object invoke() {
                return Float.valueOf(0.0f);
            }
        }, null, null, 196), this.lockscreenToOccludedTransitionViewModel.lockscreenAlpha, this.lockscreenToPrimaryBouncerTransitionViewModel.lockscreenAlpha, this.occludedToAodTransitionViewModel.lockscreenAlpha, this.occludedToDozingTransitionViewModel.lockscreenAlpha, this.occludedToLockscreenTransitionViewModel.lockscreenAlpha, this.primaryBouncerToAodTransitionViewModel.lockscreenAlpha, this.primaryBouncerToGoneTransitionViewModel.lockscreenAlpha, this.primaryBouncerToLockscreenTransitionViewModel.lockscreenAlpha)), new KeyguardRootViewModel$alpha$2(null)));
    }
}
