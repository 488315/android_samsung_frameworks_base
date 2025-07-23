package com.android.systemui.keyguard.ui.viewmodel;

import android.util.MathUtils;
import com.android.compose.animation.scene.SceneKey;
import com.android.compose.modifiers.AnimatedBackgroundKt$$ExternalSyntheticLambda0;
import com.android.systemui.communal.domain.interactor.CommunalInteractor;
import com.android.systemui.deviceentry.domain.interactor.DeviceEntryInteractor;
import com.android.systemui.dump.DumpManager;
import com.android.systemui.keyguard.domain.interactor.FromAlternateBouncerTransitionInteractor;
import com.android.systemui.keyguard.domain.interactor.KeyguardInteractor;
import com.android.systemui.keyguard.domain.interactor.KeyguardTransitionInteractor;
import com.android.systemui.keyguard.domain.interactor.PulseExpansionInteractor;
import com.android.systemui.keyguard.shared.model.Edge;
import com.android.systemui.keyguard.shared.model.KeyguardState;
import com.android.systemui.keyguard.ui.KeyguardTransitionAnimationFlow;
import com.android.systemui.keyguard.ui.KeyguardTransitionAnimationFlow$FlowBuilder$sharedFlow74qcysc$$inlined$mapNotNull$1;
import com.android.systemui.scene.shared.model.Overlays;
import com.android.systemui.scene.shared.model.Scenes;
import com.android.systemui.shade.domain.interactor.ShadeInteractor;
import com.android.systemui.shade.domain.interactor.ShadeInteractorImpl;
import com.android.systemui.shade.ui.viewmodel.NotificationShadeWindowModel;
import com.android.systemui.statusbar.notification.domain.interactor.NotificationsKeyguardInteractor;
import com.android.systemui.statusbar.notification.icon.ui.viewmodel.NotificationIconContainerAlwaysOnDisplayViewModel;
import com.android.systemui.statusbar.notification.promoted.domain.interactor.AODPromotedNotificationInteractor;
import com.android.systemui.statusbar.phone.DozeParameters;
import com.android.systemui.statusbar.phone.ScreenOffAnimationController;
import com.android.systemui.util.kotlin.BooleanFlowOperators;
import com.android.systemui.util.kotlin.FlowDumperImpl;
import com.android.systemui.util.ui.AnimatedValue;
import com.android.systemui.util.ui.AnimatedValueKt;
import com.android.systemui.wallpapers.domain.interactor.WallpaperFocalAreaInteractor;
import com.samsung.android.knox.custom.IKnoxCustomManager;
import kotlin.ResultKt;
import kotlin.Unit;
import kotlin.coroutines.Continuation;
import kotlin.coroutines.intrinsics.CoroutineSingletons;
import kotlin.coroutines.jvm.internal.ContinuationImpl;
import kotlin.coroutines.jvm.internal.SuspendLambda;
import kotlin.jvm.functions.Function0;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.functions.Function3;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.Ref$FloatRef;
import kotlin.time.Duration;
import kotlin.time.DurationKt;
import kotlin.time.DurationUnit;
import kotlinx.coroutines.CoroutineScope;
import kotlinx.coroutines.flow.EmptyFlow;
import kotlinx.coroutines.flow.Flow;
import kotlinx.coroutines.flow.FlowCollector;
import kotlinx.coroutines.flow.FlowKt;
import kotlinx.coroutines.flow.FlowKt__EmittersKt$onStart$$inlined$unsafeFlow$1;
import kotlinx.coroutines.flow.FlowKt__ZipKt$combine$$inlined$combineUnsafe$FlowKt__ZipKt$1;
import kotlinx.coroutines.flow.FlowKt__ZipKt$combine$$inlined$unsafeFlow$1;
import kotlinx.coroutines.flow.MutableSharedFlow;
import kotlinx.coroutines.flow.ReadonlyStateFlow;
import kotlinx.coroutines.flow.SafeFlow;
import kotlinx.coroutines.flow.SharingStarted;
import kotlinx.coroutines.flow.StartedWhileSubscribed;
import kotlinx.coroutines.flow.StateFlow;
import kotlinx.coroutines.flow.StateFlowImpl;
import kotlinx.coroutines.flow.internal.ChannelFlowTransformLatest;
import kotlinx.coroutines.flow.internal.CombineKt;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes2.dex */
public final class KeyguardRootViewModel extends FlowDumperImpl {
    public final Flow alphaOnShadeExpansion;
    public final AlternateBouncerToAodTransitionViewModel alternateBouncerToAodTransitionViewModel;
    public final AlternateBouncerToGoneTransitionViewModel alternateBouncerToGoneTransitionViewModel;
    public final AlternateBouncerToLockscreenTransitionViewModel alternateBouncerToLockscreenTransitionViewModel;
    public final AlternateBouncerToOccludedTransitionViewModel alternateBouncerToOccludedTransitionViewModel;
    public final AlternateBouncerToPrimaryBouncerTransitionViewModel alternateBouncerToPrimaryBouncerTransitionViewModel;
    public final AodBurnInViewModel aodBurnInViewModel;
    public final AodToGlanceableHubTransitionViewModel aodToGlanceableHubTransitionViewModel;
    public final AodToGoneTransitionViewModel aodToGoneTransitionViewModel;
    public final AodToLockscreenTransitionViewModel aodToLockscreenTransitionViewModel;
    public final AodToOccludedTransitionViewModel aodToOccludedTransitionViewModel;
    public final AodToPrimaryBouncerTransitionViewModel aodToPrimaryBouncerTransitionViewModel;
    public final Flow burnInLayerVisibility;
    public final DeviceEntryInteractor deviceEntryInteractor;
    public final DozeParameters dozeParameters;
    public final DozingToDreamingTransitionViewModel dozingToDreamingTransitionViewModel;
    public final DozingToGoneTransitionViewModel dozingToGoneTransitionViewModel;
    public final DozingToLockscreenTransitionViewModel dozingToLockscreenTransitionViewModel;
    public final DozingToOccludedTransitionViewModel dozingToOccludedTransitionViewModel;
    public final DozingToPrimaryBouncerTransitionViewModel dozingToPrimaryBouncerTransitionViewModel;
    public final DreamingToAodTransitionViewModel dreamingToAodTransitionViewModel;
    public final DreamingToGoneTransitionViewModel dreamingToGoneTransitionViewModel;
    public final DreamingToLockscreenTransitionViewModel dreamingToLockscreenTransitionViewModel;
    public final GlanceableHubToAodTransitionViewModel glanceableHubToAodTransitionViewModel;
    public final GlanceableHubToLockscreenTransitionViewModel glanceableHubToLockscreenTransitionViewModel;
    public final GoneToAodTransitionViewModel goneToAodTransitionViewModel;
    public final GoneToDozingTransitionViewModel goneToDozingTransitionViewModel;
    public final GoneToDreamingTransitionViewModel goneToDreamingTransitionViewModel;
    public final GoneToGlanceableHubTransitionViewModel goneToGlanceableHubTransitionViewModel;
    public final GoneToLockscreenTransitionViewModel goneToLockscreenTransitionViewModel;
    public final Flow hideKeyguard;
    public final StateFlow isAodPromotedNotifVisible;
    public final StateFlow isNotifIconContainerVisible;
    public final KeyguardInteractor keyguardInteractor;
    public final Flow lastRootViewTapPosition;
    public final LockscreenToAodTransitionViewModel lockscreenToAodTransitionViewModel;
    public final LockscreenToDozingTransitionViewModel lockscreenToDozingTransitionViewModel;
    public final LockscreenToDreamingTransitionViewModel lockscreenToDreamingTransitionViewModel;
    public final LockscreenToGlanceableHubTransitionViewModel lockscreenToGlanceableHubTransitionViewModel;
    public final LockscreenToGoneTransitionViewModel lockscreenToGoneTransitionViewModel;
    public final LockscreenToOccludedTransitionViewModel lockscreenToOccludedTransitionViewModel;
    public final LockscreenToPrimaryBouncerTransitionViewModel lockscreenToPrimaryBouncerTransitionViewModel;
    public final NotificationsKeyguardInteractor notificationsKeyguardInteractor;
    public final OccludedToAlternateBouncerTransitionViewModel occludedToAlternateBouncerTransitionViewModel;
    public final OccludedToAodTransitionViewModel occludedToAodTransitionViewModel;
    public final OccludedToDozingTransitionViewModel occludedToDozingTransitionViewModel;
    public final OccludedToLockscreenTransitionViewModel occludedToLockscreenTransitionViewModel;
    public final OccludedToPrimaryBouncerTransitionViewModel occludedToPrimaryBouncerTransitionViewModel;
    public final OffToLockscreenTransitionViewModel offToLockscreenTransitionViewModel;
    public final PrimaryBouncerToAodTransitionViewModel primaryBouncerToAodTransitionViewModel;
    public final PrimaryBouncerToGoneTransitionViewModel primaryBouncerToGoneTransitionViewModel;
    public final PrimaryBouncerToLockscreenTransitionViewModel primaryBouncerToLockscreenTransitionViewModel;
    public final PulseExpansionInteractor pulseExpansionInteractor;
    public final Flow scale;
    public final Flow scaleFromZoomOut;
    public final Flow topClippingBounds;
    public final Flow translationX;
    public final Flow translationY;

    /* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
    public final class Companion {
        public /* synthetic */ Companion(DefaultConstructorMarker defaultConstructorMarker) {
            this();
        }

        private Companion() {
        }
    }

    static {
        new Companion(null);
    }

    public KeyguardRootViewModel(CoroutineScope coroutineScope, DeviceEntryInteractor deviceEntryInteractor, DozeParameters dozeParameters, KeyguardInteractor keyguardInteractor, CommunalInteractor communalInteractor, KeyguardTransitionInteractor keyguardTransitionInteractor, NotificationsKeyguardInteractor notificationsKeyguardInteractor, PulseExpansionInteractor pulseExpansionInteractor, NotificationShadeWindowModel notificationShadeWindowModel, AODPromotedNotificationInteractor aODPromotedNotificationInteractor, NotificationIconContainerAlwaysOnDisplayViewModel notificationIconContainerAlwaysOnDisplayViewModel, AlternateBouncerToAodTransitionViewModel alternateBouncerToAodTransitionViewModel, AlternateBouncerToGoneTransitionViewModel alternateBouncerToGoneTransitionViewModel, AlternateBouncerToLockscreenTransitionViewModel alternateBouncerToLockscreenTransitionViewModel, AlternateBouncerToOccludedTransitionViewModel alternateBouncerToOccludedTransitionViewModel, AlternateBouncerToPrimaryBouncerTransitionViewModel alternateBouncerToPrimaryBouncerTransitionViewModel, AodToGoneTransitionViewModel aodToGoneTransitionViewModel, AodToLockscreenTransitionViewModel aodToLockscreenTransitionViewModel, AodToOccludedTransitionViewModel aodToOccludedTransitionViewModel, AodToPrimaryBouncerTransitionViewModel aodToPrimaryBouncerTransitionViewModel, AodToGlanceableHubTransitionViewModel aodToGlanceableHubTransitionViewModel, DozingToDreamingTransitionViewModel dozingToDreamingTransitionViewModel, DozingToGoneTransitionViewModel dozingToGoneTransitionViewModel, DozingToLockscreenTransitionViewModel dozingToLockscreenTransitionViewModel, DozingToOccludedTransitionViewModel dozingToOccludedTransitionViewModel, DozingToPrimaryBouncerTransitionViewModel dozingToPrimaryBouncerTransitionViewModel, DreamingToAodTransitionViewModel dreamingToAodTransitionViewModel, DreamingToGoneTransitionViewModel dreamingToGoneTransitionViewModel, DreamingToLockscreenTransitionViewModel dreamingToLockscreenTransitionViewModel, GlanceableHubToLockscreenTransitionViewModel glanceableHubToLockscreenTransitionViewModel, GlanceableHubToAodTransitionViewModel glanceableHubToAodTransitionViewModel, GoneToAodTransitionViewModel goneToAodTransitionViewModel, GoneToDozingTransitionViewModel goneToDozingTransitionViewModel, GoneToDreamingTransitionViewModel goneToDreamingTransitionViewModel, GoneToLockscreenTransitionViewModel goneToLockscreenTransitionViewModel, GoneToGlanceableHubTransitionViewModel goneToGlanceableHubTransitionViewModel, LockscreenToAodTransitionViewModel lockscreenToAodTransitionViewModel, LockscreenToDozingTransitionViewModel lockscreenToDozingTransitionViewModel, LockscreenToDreamingTransitionViewModel lockscreenToDreamingTransitionViewModel, LockscreenToGlanceableHubTransitionViewModel lockscreenToGlanceableHubTransitionViewModel, LockscreenToGoneTransitionViewModel lockscreenToGoneTransitionViewModel, LockscreenToOccludedTransitionViewModel lockscreenToOccludedTransitionViewModel, LockscreenToPrimaryBouncerTransitionViewModel lockscreenToPrimaryBouncerTransitionViewModel, OccludedToAlternateBouncerTransitionViewModel occludedToAlternateBouncerTransitionViewModel, OccludedToAodTransitionViewModel occludedToAodTransitionViewModel, OccludedToDozingTransitionViewModel occludedToDozingTransitionViewModel, OccludedToLockscreenTransitionViewModel occludedToLockscreenTransitionViewModel, OccludedToPrimaryBouncerTransitionViewModel occludedToPrimaryBouncerTransitionViewModel, OffToLockscreenTransitionViewModel offToLockscreenTransitionViewModel, PrimaryBouncerToAodTransitionViewModel primaryBouncerToAodTransitionViewModel, PrimaryBouncerToGoneTransitionViewModel primaryBouncerToGoneTransitionViewModel, PrimaryBouncerToLockscreenTransitionViewModel primaryBouncerToLockscreenTransitionViewModel, ScreenOffAnimationController screenOffAnimationController, AodBurnInViewModel aodBurnInViewModel, ShadeInteractor shadeInteractor, WallpaperFocalAreaInteractor wallpaperFocalAreaInteractor, DumpManager dumpManager) {
        super(dumpManager, null, 2, 0 == true ? 1 : 0);
        this.deviceEntryInteractor = deviceEntryInteractor;
        this.dozeParameters = dozeParameters;
        this.keyguardInteractor = keyguardInteractor;
        this.notificationsKeyguardInteractor = notificationsKeyguardInteractor;
        this.pulseExpansionInteractor = pulseExpansionInteractor;
        this.alternateBouncerToAodTransitionViewModel = alternateBouncerToAodTransitionViewModel;
        this.alternateBouncerToGoneTransitionViewModel = alternateBouncerToGoneTransitionViewModel;
        this.alternateBouncerToLockscreenTransitionViewModel = alternateBouncerToLockscreenTransitionViewModel;
        this.alternateBouncerToOccludedTransitionViewModel = alternateBouncerToOccludedTransitionViewModel;
        this.alternateBouncerToPrimaryBouncerTransitionViewModel = alternateBouncerToPrimaryBouncerTransitionViewModel;
        this.aodToGoneTransitionViewModel = aodToGoneTransitionViewModel;
        this.aodToLockscreenTransitionViewModel = aodToLockscreenTransitionViewModel;
        this.aodToOccludedTransitionViewModel = aodToOccludedTransitionViewModel;
        this.aodToPrimaryBouncerTransitionViewModel = aodToPrimaryBouncerTransitionViewModel;
        this.aodToGlanceableHubTransitionViewModel = aodToGlanceableHubTransitionViewModel;
        this.dozingToDreamingTransitionViewModel = dozingToDreamingTransitionViewModel;
        this.dozingToGoneTransitionViewModel = dozingToGoneTransitionViewModel;
        this.dozingToLockscreenTransitionViewModel = dozingToLockscreenTransitionViewModel;
        this.dozingToOccludedTransitionViewModel = dozingToOccludedTransitionViewModel;
        this.dozingToPrimaryBouncerTransitionViewModel = dozingToPrimaryBouncerTransitionViewModel;
        this.dreamingToAodTransitionViewModel = dreamingToAodTransitionViewModel;
        this.dreamingToGoneTransitionViewModel = dreamingToGoneTransitionViewModel;
        this.dreamingToLockscreenTransitionViewModel = dreamingToLockscreenTransitionViewModel;
        this.glanceableHubToLockscreenTransitionViewModel = glanceableHubToLockscreenTransitionViewModel;
        this.glanceableHubToAodTransitionViewModel = glanceableHubToAodTransitionViewModel;
        this.goneToAodTransitionViewModel = goneToAodTransitionViewModel;
        this.goneToDozingTransitionViewModel = goneToDozingTransitionViewModel;
        this.goneToDreamingTransitionViewModel = goneToDreamingTransitionViewModel;
        this.goneToLockscreenTransitionViewModel = goneToLockscreenTransitionViewModel;
        this.goneToGlanceableHubTransitionViewModel = goneToGlanceableHubTransitionViewModel;
        this.lockscreenToAodTransitionViewModel = lockscreenToAodTransitionViewModel;
        this.lockscreenToDozingTransitionViewModel = lockscreenToDozingTransitionViewModel;
        this.lockscreenToDreamingTransitionViewModel = lockscreenToDreamingTransitionViewModel;
        this.lockscreenToGlanceableHubTransitionViewModel = lockscreenToGlanceableHubTransitionViewModel;
        this.lockscreenToGoneTransitionViewModel = lockscreenToGoneTransitionViewModel;
        this.lockscreenToOccludedTransitionViewModel = lockscreenToOccludedTransitionViewModel;
        this.lockscreenToPrimaryBouncerTransitionViewModel = lockscreenToPrimaryBouncerTransitionViewModel;
        this.occludedToAlternateBouncerTransitionViewModel = occludedToAlternateBouncerTransitionViewModel;
        this.occludedToAodTransitionViewModel = occludedToAodTransitionViewModel;
        this.occludedToDozingTransitionViewModel = occludedToDozingTransitionViewModel;
        this.occludedToLockscreenTransitionViewModel = occludedToLockscreenTransitionViewModel;
        this.occludedToPrimaryBouncerTransitionViewModel = occludedToPrimaryBouncerTransitionViewModel;
        this.offToLockscreenTransitionViewModel = offToLockscreenTransitionViewModel;
        this.primaryBouncerToAodTransitionViewModel = primaryBouncerToAodTransitionViewModel;
        this.primaryBouncerToGoneTransitionViewModel = primaryBouncerToGoneTransitionViewModel;
        this.primaryBouncerToLockscreenTransitionViewModel = primaryBouncerToLockscreenTransitionViewModel;
        this.aodBurnInViewModel = aodBurnInViewModel;
        final ReadonlyStateFlow readonlyStateFlow = keyguardTransitionInteractor.startedKeyguardTransitionStep;
        final Flow flow = new Flow() { // from class: com.android.systemui.keyguard.ui.viewmodel.KeyguardRootViewModel$special$$inlined$filter$1

            /* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
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
                        goto L4a
                    L27:
                        java.lang.IllegalStateException r4 = new java.lang.IllegalStateException
                        java.lang.String r5 = "call to 'resume' before 'invoke' with coroutine"
                        r4.<init>(r5)
                        throw r4
                    L2f:
                        kotlin.ResultKt.throwOnFailure(r6)
                        r6 = r5
                        com.android.systemui.keyguard.shared.model.TransitionStep r6 = (com.android.systemui.keyguard.shared.model.TransitionStep) r6
                        com.android.systemui.keyguard.shared.model.KeyguardState r6 = r6.to
                        com.android.systemui.keyguard.shared.model.KeyguardState r2 = com.android.systemui.keyguard.shared.model.KeyguardState.AOD
                        if (r6 == r2) goto L3f
                        com.android.systemui.keyguard.shared.model.KeyguardState r2 = com.android.systemui.keyguard.shared.model.KeyguardState.LOCKSCREEN
                        if (r6 != r2) goto L4a
                    L3f:
                        r0.label = r3
                        kotlinx.coroutines.flow.FlowCollector r4 = r4.$this_unsafeFlow
                        java.lang.Object r4 = r4.emit(r5, r0)
                        if (r4 != r1) goto L4a
                        return r1
                    L4a:
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
        this.burnInLayerVisibility = dumpWhileCollecting(new Flow() { // from class: com.android.systemui.keyguard.ui.viewmodel.KeyguardRootViewModel$special$$inlined$map$1

            /* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
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
                        com.android.systemui.keyguard.shared.model.TransitionStep r5 = (com.android.systemui.keyguard.shared.model.TransitionStep) r5
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
        }, "burnInLayerVisibility");
        Edge.Companion companion = Edge.Companion;
        SceneKey sceneKey = Scenes.Gone;
        KeyguardState keyguardState = KeyguardState.AOD;
        companion.getClass();
        new Edge.ContentToState(sceneKey, keyguardState);
        KeyguardState keyguardState2 = KeyguardState.GONE;
        final Flow dumpWhileCollecting = dumpWhileCollecting(keyguardTransitionInteractor.transition(new Edge.StateToState(keyguardState2, keyguardState)), "goneToAodTransition");
        Flow distinctUntilChanged = FlowKt.distinctUntilChanged(new FlowKt__EmittersKt$onStart$$inlined$unsafeFlow$1(new KeyguardRootViewModel$goneToAodTransitionRunning$2(null), new Flow() { // from class: com.android.systemui.keyguard.ui.viewmodel.KeyguardRootViewModel$special$$inlined$map$2

            /* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
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
        final MutableSharedFlow transitionValueFlow = keyguardTransitionInteractor.getTransitionValueFlow(keyguardState3);
        Flow distinctUntilChanged2 = FlowKt.distinctUntilChanged(new FlowKt__ZipKt$combine$$inlined$unsafeFlow$1(new Flow() { // from class: com.android.systemui.keyguard.ui.viewmodel.KeyguardRootViewModel$special$$inlined$map$3

            /* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
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
        }, keyguardTransitionInteractor.isInTransition(Edge.Companion.create$default(companion, null, keyguardState3, 1), null), new KeyguardRootViewModel$isOnOrGoingToLockscreen$2(null)));
        ShadeInteractorImpl shadeInteractorImpl = (ShadeInteractorImpl) shadeInteractor;
        this.alphaOnShadeExpansion = FlowKt.distinctUntilChanged(FlowKt.combineTransform(keyguardTransitionInteractor.isInTransition(new Edge.ContentToState(Overlays.Bouncer, keyguardState3), new Edge.StateToState(KeyguardState.PRIMARY_BOUNCER, keyguardState3)), distinctUntilChanged2, shadeInteractorImpl.baseShadeInteractor.getQsExpansion(), shadeInteractorImpl.baseShadeInteractor.getShadeExpansion(), new KeyguardRootViewModel$alphaOnShadeExpansion$1(null)));
        BooleanFlowOperators booleanFlowOperators = BooleanFlowOperators.INSTANCE;
        Flow flow2 = notificationShadeWindowModel.isKeyguardOccluded;
        ReadonlyStateFlow readonlyStateFlow2 = communalInteractor.isIdleOnCommunal;
        final MutableSharedFlow transitionValueFlow2 = keyguardTransitionInteractor.getTransitionValueFlow(KeyguardState.OFF);
        FlowKt__EmittersKt$onStart$$inlined$unsafeFlow$1 flowKt__EmittersKt$onStart$$inlined$unsafeFlow$1 = new FlowKt__EmittersKt$onStart$$inlined$unsafeFlow$1(new KeyguardRootViewModel$hideKeyguard$2(null), new Flow() { // from class: com.android.systemui.keyguard.ui.viewmodel.KeyguardRootViewModel$special$$inlined$map$4

            /* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
            /* renamed from: com.android.systemui.keyguard.ui.viewmodel.KeyguardRootViewModel$special$$inlined$map$4$2, reason: invalid class name */
            public final class AnonymousClass2 implements FlowCollector {
                public final /* synthetic */ FlowCollector $this_unsafeFlow;
                public final /* synthetic */ KeyguardRootViewModel this$0;

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

                public AnonymousClass2(FlowCollector flowCollector, KeyguardRootViewModel keyguardRootViewModel) {
                    this.$this_unsafeFlow = flowCollector;
                    this.this$0 = keyguardRootViewModel;
                }

                /* JADX WARN: Removed duplicated region for block: B:15:0x002f  */
                /* JADX WARN: Removed duplicated region for block: B:8:0x0021  */
                @Override // kotlinx.coroutines.flow.FlowCollector
                /*
                    Code decompiled incorrectly, please refer to instructions dump.
                    To view partially-correct code enable 'Show inconsistent code' option in preferences
                */
                public final java.lang.Object emit(java.lang.Object r9, kotlin.coroutines.Continuation r10) {
                    /*
                        r8 = this;
                        boolean r0 = r10 instanceof com.android.systemui.keyguard.ui.viewmodel.KeyguardRootViewModel$special$$inlined$map$4.AnonymousClass2.AnonymousClass1
                        if (r0 == 0) goto L13
                        r0 = r10
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
                        r0.<init>(r10)
                    L18:
                        java.lang.Object r10 = r0.result
                        kotlin.coroutines.intrinsics.CoroutineSingletons r1 = kotlin.coroutines.intrinsics.CoroutineSingletons.COROUTINE_SUSPENDED
                        int r2 = r0.label
                        r3 = 1
                        if (r2 == 0) goto L2f
                        if (r2 != r3) goto L27
                        kotlin.ResultKt.throwOnFailure(r10)
                        goto L59
                    L27:
                        java.lang.IllegalStateException r8 = new java.lang.IllegalStateException
                        java.lang.String r9 = "call to 'resume' before 'invoke' with coroutine"
                        r8.<init>(r9)
                        throw r8
                    L2f:
                        kotlin.ResultKt.throwOnFailure(r10)
                        java.lang.Number r9 = (java.lang.Number) r9
                        float r9 = r9.floatValue()
                        double r9 = (double) r9
                        r2 = 1065353216(0x3f800000, float:1.0)
                        double r4 = (double) r2
                        com.android.systemui.keyguard.ui.viewmodel.KeyguardRootViewModel r2 = r8.this$0
                        com.android.systemui.keyguard.ui.viewmodel.OffToLockscreenTransitionViewModel r2 = r2.offToLockscreenTransitionViewModel
                        double r6 = r2.alphaStartAt
                        double r4 = r4 - r6
                        int r9 = (r9 > r4 ? 1 : (r9 == r4 ? 0 : -1))
                        if (r9 <= 0) goto L49
                        r9 = r3
                        goto L4a
                    L49:
                        r9 = 0
                    L4a:
                        java.lang.Boolean r9 = java.lang.Boolean.valueOf(r9)
                        r0.label = r3
                        kotlinx.coroutines.flow.FlowCollector r8 = r8.$this_unsafeFlow
                        java.lang.Object r8 = r8.emit(r9, r0)
                        if (r8 != r1) goto L59
                        return r1
                    L59:
                        kotlin.Unit r8 = kotlin.Unit.INSTANCE
                        return r8
                    */
                    throw new UnsupportedOperationException("Method not decompiled: com.android.systemui.keyguard.ui.viewmodel.KeyguardRootViewModel$special$$inlined$map$4.AnonymousClass2.emit(java.lang.Object, kotlin.coroutines.Continuation):java.lang.Object");
                }
            }

            @Override // kotlinx.coroutines.flow.Flow
            public final Object collect(FlowCollector flowCollector, Continuation continuation) {
                Object collect = Flow.this.collect(new AnonymousClass2(flowCollector, this), continuation);
                return collect == CoroutineSingletons.COROUTINE_SUSPENDED ? collect : Unit.INSTANCE;
            }
        });
        final MutableSharedFlow transitionValueFlow3 = keyguardTransitionInteractor.getTransitionValueFlow(keyguardState2);
        this.hideKeyguard = booleanFlowOperators.anyOf(flow2, readonlyStateFlow2, flowKt__EmittersKt$onStart$$inlined$unsafeFlow$1, new FlowKt__EmittersKt$onStart$$inlined$unsafeFlow$1(new KeyguardRootViewModel$hideKeyguard$4(null), new Flow() { // from class: com.android.systemui.keyguard.ui.viewmodel.KeyguardRootViewModel$special$$inlined$map$5

            /* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
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
        }));
        this.lastRootViewTapPosition = dumpWhileCollecting(keyguardInteractor.lastRootViewTapPosition, "lastRootViewTapPosition");
        this.topClippingBounds = dumpWhileCollecting((Flow) keyguardInteractor.topClippingBounds$delegate.getValue(), "topClippingBounds");
        final StateFlowImpl stateFlowImpl = keyguardInteractor.zoomOut;
        this.scaleFromZoomOut = dumpWhileCollecting(new Flow() { // from class: com.android.systemui.keyguard.ui.viewmodel.KeyguardRootViewModel$special$$inlined$map$6

            /* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
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
                        goto L4e
                    L27:
                        java.lang.IllegalStateException r4 = new java.lang.IllegalStateException
                        java.lang.String r5 = "call to 'resume' before 'invoke' with coroutine"
                        r4.<init>(r5)
                        throw r4
                    L2f:
                        kotlin.ResultKt.throwOnFailure(r6)
                        java.lang.Number r5 = (java.lang.Number) r5
                        float r5 = r5.floatValue()
                        float r6 = (float) r3
                        r2 = 1028443341(0x3d4ccccd, float:0.05)
                        float r5 = r5 * r2
                        float r6 = r6 - r5
                        java.lang.Float r5 = new java.lang.Float
                        r5.<init>(r6)
                        r0.label = r3
                        kotlinx.coroutines.flow.FlowCollector r4 = r4.$this_unsafeFlow
                        java.lang.Object r4 = r4.emit(r5, r0)
                        if (r4 != r1) goto L4e
                        return r1
                    L4e:
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
        }, "scaleFromZoomOut");
        final ReadonlyStateFlow readonlyStateFlow3 = aodBurnInViewModel.movement;
        this.translationY = dumpWhileCollecting(new Flow() { // from class: com.android.systemui.keyguard.ui.viewmodel.KeyguardRootViewModel$special$$inlined$map$7

            /* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
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
                        boolean r0 = r6 instanceof com.android.systemui.keyguard.ui.viewmodel.KeyguardRootViewModel$special$$inlined$map$7.AnonymousClass2.AnonymousClass1
                        if (r0 == 0) goto L13
                        r0 = r6
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
                    throw new UnsupportedOperationException("Method not decompiled: com.android.systemui.keyguard.ui.viewmodel.KeyguardRootViewModel$special$$inlined$map$7.AnonymousClass2.emit(java.lang.Object, kotlin.coroutines.Continuation):java.lang.Object");
                }
            }

            @Override // kotlinx.coroutines.flow.Flow
            public final Object collect(FlowCollector flowCollector, Continuation continuation) {
                Object collect = Flow.this.collect(new AnonymousClass2(flowCollector), continuation);
                return collect == CoroutineSingletons.COROUTINE_SUSPENDED ? collect : Unit.INSTANCE;
            }
        }, "translationY");
        final ReadonlyStateFlow readonlyStateFlow4 = aodBurnInViewModel.movement;
        this.translationX = dumpWhileCollecting(FlowKt.merge(new Flow() { // from class: com.android.systemui.keyguard.ui.viewmodel.KeyguardRootViewModel$special$$inlined$map$8

            /* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
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

                /* JADX WARN: Removed duplicated region for block: B:15:0x002f  */
                /* JADX WARN: Removed duplicated region for block: B:8:0x0021  */
                @Override // kotlinx.coroutines.flow.FlowCollector
                /*
                    Code decompiled incorrectly, please refer to instructions dump.
                    To view partially-correct code enable 'Show inconsistent code' option in preferences
                */
                public final java.lang.Object emit(java.lang.Object r12, kotlin.coroutines.Continuation r13) {
                    /*
                        r11 = this;
                        boolean r0 = r13 instanceof com.android.systemui.keyguard.ui.viewmodel.KeyguardRootViewModel$special$$inlined$map$8.AnonymousClass2.AnonymousClass1
                        if (r0 == 0) goto L13
                        r0 = r13
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
                        r0.<init>(r13)
                    L18:
                        java.lang.Object r13 = r0.result
                        kotlin.coroutines.intrinsics.CoroutineSingletons r1 = kotlin.coroutines.intrinsics.CoroutineSingletons.COROUTINE_SUSPENDED
                        int r2 = r0.label
                        r3 = 1
                        if (r2 == 0) goto L2f
                        if (r2 != r3) goto L27
                        kotlin.ResultKt.throwOnFailure(r13)
                        goto L52
                    L27:
                        java.lang.IllegalStateException r11 = new java.lang.IllegalStateException
                        java.lang.String r12 = "call to 'resume' before 'invoke' with coroutine"
                        r11.<init>(r12)
                        throw r11
                    L2f:
                        kotlin.ResultKt.throwOnFailure(r13)
                        com.android.systemui.keyguard.shared.model.BurnInModel r12 = (com.android.systemui.keyguard.shared.model.BurnInModel) r12
                        com.android.systemui.keyguard.ui.StateToValue r4 = new com.android.systemui.keyguard.ui.StateToValue
                        com.android.systemui.keyguard.shared.model.KeyguardState r6 = com.android.systemui.keyguard.shared.model.KeyguardState.AOD
                        int r12 = r12.translationX
                        float r12 = (float) r12
                        java.lang.Float r8 = new java.lang.Float
                        r8.<init>(r12)
                        r9 = 5
                        r10 = 0
                        r5 = 0
                        r7 = 0
                        r4.<init>(r5, r6, r7, r8, r9, r10)
                        r0.label = r3
                        kotlinx.coroutines.flow.FlowCollector r11 = r11.$this_unsafeFlow
                        java.lang.Object r11 = r11.emit(r4, r0)
                        if (r11 != r1) goto L52
                        return r1
                    L52:
                        kotlin.Unit r11 = kotlin.Unit.INSTANCE
                        return r11
                    */
                    throw new UnsupportedOperationException("Method not decompiled: com.android.systemui.keyguard.ui.viewmodel.KeyguardRootViewModel$special$$inlined$map$8.AnonymousClass2.emit(java.lang.Object, kotlin.coroutines.Continuation):java.lang.Object");
                }
            }

            @Override // kotlinx.coroutines.flow.Flow
            public final Object collect(FlowCollector flowCollector, Continuation continuation) {
                Object collect = Flow.this.collect(new AnonymousClass2(flowCollector), continuation);
                return collect == CoroutineSingletons.COROUTINE_SUSPENDED ? collect : Unit.INSTANCE;
            }
        }, lockscreenToGlanceableHubTransitionViewModel.keyguardTranslationX, glanceableHubToLockscreenTransitionViewModel.keyguardTranslationX), "translationX");
        this.scale = dumpWhileCollecting(new Flow() { // from class: com.android.systemui.keyguard.ui.viewmodel.KeyguardRootViewModel$special$$inlined$map$9

            /* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
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
                    throw new UnsupportedOperationException("Method not decompiled: com.android.systemui.keyguard.ui.viewmodel.KeyguardRootViewModel$special$$inlined$map$9.AnonymousClass2.emit(java.lang.Object, kotlin.coroutines.Continuation):java.lang.Object");
                }
            }

            @Override // kotlinx.coroutines.flow.Flow
            public final Object collect(FlowCollector flowCollector, Continuation continuation) {
                Object collect = Flow.this.collect(new AnonymousClass2(flowCollector), continuation);
                return collect == CoroutineSingletons.COROUTINE_SUSPENDED ? collect : Unit.INSTANCE;
            }
        }, "scale");
        Flow animatedValueFlow = AnimatedValueKt.toAnimatedValueFlow(com.android.systemui.util.kotlin.FlowKt.sample(com.android.systemui.util.kotlin.FlowKt.pairwise(notificationsKeyguardInteractor.areNotificationsFullyHidden, null), deviceEntryInteractor.isBypassEnabled, new KeyguardRootViewModel$areNotifsFullyHiddenAnimated$1(this, null)));
        final Flow pairwise = com.android.systemui.util.kotlin.FlowKt.pairwise(pulseExpansionInteractor.isPulseExpanding, null);
        FlowKt__ZipKt$combine$$inlined$combineUnsafe$FlowKt__ZipKt$1 combine = FlowKt.combine(animatedValueFlow, AnimatedValueKt.toAnimatedValueFlow(new Flow() { // from class: com.android.systemui.keyguard.ui.viewmodel.KeyguardRootViewModel$isPulseExpandingAnimated$$inlined$map$1

            /* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
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
        }), aODPromotedNotificationInteractor.isPresent, new KeyguardRootViewModel$isAodPromotedNotifVisible$1(null));
        SharingStarted.Companion companion2 = SharingStarted.Companion;
        StartedWhileSubscribed WhileSubscribed$default = SharingStarted.Companion.WhileSubscribed$default(companion2, 3);
        Boolean bool = Boolean.FALSE;
        this.isAodPromotedNotifVisible = dumpValue(FlowKt.stateIn(combine, coroutineScope, WhileSubscribed$default, new AnimatedValue.NotAnimating(bool)), "isAodPromotedNotifVisible");
        final MutableSharedFlow transitionValueFlow4 = keyguardTransitionInteractor.getTransitionValueFlow(keyguardState3);
        FlowKt__EmittersKt$onStart$$inlined$unsafeFlow$1 flowKt__EmittersKt$onStart$$inlined$unsafeFlow$12 = new FlowKt__EmittersKt$onStart$$inlined$unsafeFlow$1(new KeyguardRootViewModel$isNotifIconContainerVisible$2(null), new Flow() { // from class: com.android.systemui.keyguard.ui.viewmodel.KeyguardRootViewModel$special$$inlined$map$10

            /* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
            /* renamed from: com.android.systemui.keyguard.ui.viewmodel.KeyguardRootViewModel$special$$inlined$map$10$2, reason: invalid class name */
            public final class AnonymousClass2 implements FlowCollector {
                public final /* synthetic */ FlowCollector $this_unsafeFlow;

                /* renamed from: com.android.systemui.keyguard.ui.viewmodel.KeyguardRootViewModel$special$$inlined$map$10$2$1, reason: invalid class name */
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
                        boolean r0 = r6 instanceof com.android.systemui.keyguard.ui.viewmodel.KeyguardRootViewModel$special$$inlined$map$10.AnonymousClass2.AnonymousClass1
                        if (r0 == 0) goto L13
                        r0 = r6
                        com.android.systemui.keyguard.ui.viewmodel.KeyguardRootViewModel$special$$inlined$map$10$2$1 r0 = (com.android.systemui.keyguard.ui.viewmodel.KeyguardRootViewModel$special$$inlined$map$10.AnonymousClass2.AnonymousClass1) r0
                        int r1 = r0.label
                        r2 = -2147483648(0xffffffff80000000, float:-0.0)
                        r3 = r1 & r2
                        if (r3 == 0) goto L13
                        int r1 = r1 - r2
                        r0.label = r1
                        goto L18
                    L13:
                        com.android.systemui.keyguard.ui.viewmodel.KeyguardRootViewModel$special$$inlined$map$10$2$1 r0 = new com.android.systemui.keyguard.ui.viewmodel.KeyguardRootViewModel$special$$inlined$map$10$2$1
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
                        java.lang.Number r5 = (java.lang.Number) r5
                        float r5 = r5.floatValue()
                        r6 = 0
                        int r5 = (r5 > r6 ? 1 : (r5 == r6 ? 0 : -1))
                        if (r5 <= 0) goto L3f
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
                    throw new UnsupportedOperationException("Method not decompiled: com.android.systemui.keyguard.ui.viewmodel.KeyguardRootViewModel$special$$inlined$map$10.AnonymousClass2.emit(java.lang.Object, kotlin.coroutines.Continuation):java.lang.Object");
                }
            }

            @Override // kotlinx.coroutines.flow.Flow
            public final Object collect(FlowCollector flowCollector, Continuation continuation) {
                Object collect = Flow.this.collect(new AnonymousClass2(flowCollector), continuation);
                return collect == CoroutineSingletons.COROUTINE_SUSPENDED ? collect : Unit.INSTANCE;
            }
        });
        Flow isFinishedIn = keyguardTransitionInteractor.isFinishedIn(keyguardState2);
        ReadonlyStateFlow readonlyStateFlow5 = deviceEntryInteractor.isBypassEnabled;
        Flow animatedValueFlow2 = AnimatedValueKt.toAnimatedValueFlow(com.android.systemui.util.kotlin.FlowKt.sample(com.android.systemui.util.kotlin.FlowKt.pairwise(notificationsKeyguardInteractor.areNotificationsFullyHidden, null), deviceEntryInteractor.isBypassEnabled, new KeyguardRootViewModel$areNotifsFullyHiddenAnimated$1(this, null)));
        final Flow pairwise2 = com.android.systemui.util.kotlin.FlowKt.pairwise(pulseExpansionInteractor.isPulseExpanding, null);
        Flow animatedValueFlow3 = AnimatedValueKt.toAnimatedValueFlow(new Flow() { // from class: com.android.systemui.keyguard.ui.viewmodel.KeyguardRootViewModel$isPulseExpandingAnimated$$inlined$map$1

            /* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
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
                public final Object emit(Object obj, Continuation continuation) {
                    /*
                        this = this;
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
        });
        final Flow flow3 = notificationIconContainerAlwaysOnDisplayViewModel.icons;
        final Flow[] flowArr = {distinctUntilChanged, flowKt__EmittersKt$onStart$$inlined$unsafeFlow$12, isFinishedIn, readonlyStateFlow5, animatedValueFlow2, animatedValueFlow3, new Flow() { // from class: com.android.systemui.keyguard.ui.viewmodel.KeyguardRootViewModel$special$$inlined$map$11

            /* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
            /* renamed from: com.android.systemui.keyguard.ui.viewmodel.KeyguardRootViewModel$special$$inlined$map$11$2, reason: invalid class name */
            public final class AnonymousClass2 implements FlowCollector {
                public final /* synthetic */ FlowCollector $this_unsafeFlow;

                /* renamed from: com.android.systemui.keyguard.ui.viewmodel.KeyguardRootViewModel$special$$inlined$map$11$2$1, reason: invalid class name */
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
                        boolean r0 = r6 instanceof com.android.systemui.keyguard.ui.viewmodel.KeyguardRootViewModel$special$$inlined$map$11.AnonymousClass2.AnonymousClass1
                        if (r0 == 0) goto L13
                        r0 = r6
                        com.android.systemui.keyguard.ui.viewmodel.KeyguardRootViewModel$special$$inlined$map$11$2$1 r0 = (com.android.systemui.keyguard.ui.viewmodel.KeyguardRootViewModel$special$$inlined$map$11.AnonymousClass2.AnonymousClass1) r0
                        int r1 = r0.label
                        r2 = -2147483648(0xffffffff80000000, float:-0.0)
                        r3 = r1 & r2
                        if (r3 == 0) goto L13
                        int r1 = r1 - r2
                        r0.label = r1
                        goto L18
                    L13:
                        com.android.systemui.keyguard.ui.viewmodel.KeyguardRootViewModel$special$$inlined$map$11$2$1 r0 = new com.android.systemui.keyguard.ui.viewmodel.KeyguardRootViewModel$special$$inlined$map$11$2$1
                        r0.<init>(r6)
                    L18:
                        java.lang.Object r6 = r0.result
                        kotlin.coroutines.intrinsics.CoroutineSingletons r1 = kotlin.coroutines.intrinsics.CoroutineSingletons.COROUTINE_SUSPENDED
                        int r2 = r0.label
                        r3 = 1
                        if (r2 == 0) goto L2f
                        if (r2 != r3) goto L27
                        kotlin.ResultKt.throwOnFailure(r6)
                        goto L4c
                    L27:
                        java.lang.IllegalStateException r4 = new java.lang.IllegalStateException
                        java.lang.String r5 = "call to 'resume' before 'invoke' with coroutine"
                        r4.<init>(r5)
                        throw r4
                    L2f:
                        kotlin.ResultKt.throwOnFailure(r6)
                        com.android.systemui.statusbar.notification.icon.ui.viewmodel.NotificationIconsViewData r5 = (com.android.systemui.statusbar.notification.icon.ui.viewmodel.NotificationIconsViewData) r5
                        java.util.List r5 = r5.visibleIcons
                        java.util.Collection r5 = (java.util.Collection) r5
                        boolean r5 = r5.isEmpty()
                        r5 = r5 ^ r3
                        java.lang.Boolean r5 = java.lang.Boolean.valueOf(r5)
                        r0.label = r3
                        kotlinx.coroutines.flow.FlowCollector r4 = r4.$this_unsafeFlow
                        java.lang.Object r4 = r4.emit(r5, r0)
                        if (r4 != r1) goto L4c
                        return r1
                    L4c:
                        kotlin.Unit r4 = kotlin.Unit.INSTANCE
                        return r4
                    */
                    throw new UnsupportedOperationException("Method not decompiled: com.android.systemui.keyguard.ui.viewmodel.KeyguardRootViewModel$special$$inlined$map$11.AnonymousClass2.emit(java.lang.Object, kotlin.coroutines.Continuation):java.lang.Object");
                }
            }

            @Override // kotlinx.coroutines.flow.Flow
            public final Object collect(FlowCollector flowCollector, Continuation continuation) {
                Object collect = Flow.this.collect(new AnonymousClass2(flowCollector), continuation);
                return collect == CoroutineSingletons.COROUTINE_SUSPENDED ? collect : Unit.INSTANCE;
            }
        }};
        this.isNotifIconContainerVisible = dumpValue(FlowKt.stateIn(new Flow() { // from class: com.android.systemui.keyguard.ui.viewmodel.KeyguardRootViewModel$special$$inlined$combine$1

            /* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
            /* renamed from: com.android.systemui.keyguard.ui.viewmodel.KeyguardRootViewModel$special$$inlined$combine$1$3, reason: invalid class name */
            public final class AnonymousClass3 extends SuspendLambda implements Function3 {
                private /* synthetic */ Object L$0;
                /* synthetic */ Object L$1;
                int label;
                final /* synthetic */ KeyguardRootViewModel this$0;

                /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
                public AnonymousClass3(Continuation continuation, KeyguardRootViewModel keyguardRootViewModel) {
                    super(3, continuation);
                    this.this$0 = keyguardRootViewModel;
                }

                @Override // kotlin.jvm.functions.Function3
                public final Object invoke(Object obj, Object obj2, Object obj3) {
                    AnonymousClass3 anonymousClass3 = new AnonymousClass3((Continuation) obj3, this.this$0);
                    anonymousClass3.L$0 = (FlowCollector) obj;
                    anonymousClass3.L$1 = (Object[]) obj2;
                    return anonymousClass3.invokeSuspend(Unit.INSTANCE);
                }

                @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
                public final Object invokeSuspend(Object obj) {
                    CoroutineSingletons coroutineSingletons = CoroutineSingletons.COROUTINE_SUSPENDED;
                    int i = this.label;
                    if (i == 0) {
                        ResultKt.throwOnFailure(obj);
                        FlowCollector flowCollector = (FlowCollector) this.L$0;
                        Object[] objArr = (Object[]) this.L$1;
                        ((Boolean) objArr[0]).getClass();
                        ((Boolean) objArr[1]).getClass();
                        ((Boolean) objArr[2]).getClass();
                        ((Boolean) objArr[3]).getClass();
                        ((Boolean) objArr[6]).getClass();
                        AnimatedValue.NotAnimating notAnimating = new AnimatedValue.NotAnimating(Boolean.FALSE);
                        this.label = 1;
                        if (flowCollector.emit(notAnimating, this) == coroutineSingletons) {
                            return coroutineSingletons;
                        }
                    } else {
                        if (i != 1) {
                            throw new IllegalStateException("call to 'resume' before 'invoke' with coroutine");
                        }
                        ResultKt.throwOnFailure(obj);
                    }
                    return Unit.INSTANCE;
                }
            }

            @Override // kotlinx.coroutines.flow.Flow
            public final Object collect(FlowCollector flowCollector, Continuation continuation) {
                final Flow[] flowArr2 = flowArr;
                Object combineInternal = CombineKt.combineInternal(flowArr2, new Function0() { // from class: com.android.systemui.keyguard.ui.viewmodel.KeyguardRootViewModel$special$$inlined$combine$1.2
                    @Override // kotlin.jvm.functions.Function0
                    public final Object invoke() {
                        return new Object[flowArr2.length];
                    }
                }, new AnonymousClass3(null, this), flowCollector, continuation);
                return combineInternal == CoroutineSingletons.COROUTINE_SUSPENDED ? combineInternal : Unit.INSTANCE;
            }
        }, coroutineScope, SharingStarted.Companion.WhileSubscribed$default(companion2, 3), new AnimatedValue.NotAnimating(bool)), "isNotifIconContainerVisible");
    }

    public final Flow alpha(final ViewStateAccessor viewStateAccessor) {
        int i = 1;
        Flow flow = this.keyguardInteractor.dismissAlpha;
        AlternateBouncerToAodTransitionViewModel alternateBouncerToAodTransitionViewModel = this.alternateBouncerToAodTransitionViewModel;
        alternateBouncerToAodTransitionViewModel.getClass();
        final Ref$FloatRef ref$FloatRef = new Ref$FloatRef();
        ref$FloatRef.element = 1.0f;
        FromAlternateBouncerTransitionInteractor.Companion.getClass();
        KeyguardTransitionAnimationFlow$FlowBuilder$sharedFlow74qcysc$$inlined$mapNotNull$1 m2599sharedFlow74qcysc$default = KeyguardTransitionAnimationFlow.FlowBuilder.m2599sharedFlow74qcysc$default(alternateBouncerToAodTransitionViewModel.transitionAnimation, FromAlternateBouncerTransitionInteractor.TO_AOD_DURATION, new Function1() { // from class: com.android.systemui.keyguard.ui.viewmodel.AlternateBouncerToAodTransitionViewModel$$ExternalSyntheticLambda2
            @Override // kotlin.jvm.functions.Function1
            /* renamed from: invoke */
            public final Object mo779invoke(Object obj) {
                return Float.valueOf(MathUtils.lerp(Ref$FloatRef.this.element, 1.0f, ((Float) obj).floatValue()));
            }
        }, 0L, new Function0() { // from class: com.android.systemui.keyguard.ui.viewmodel.AlternateBouncerToAodTransitionViewModel$$ExternalSyntheticLambda3
            @Override // kotlin.jvm.functions.Function0
            public final Object invoke() {
                Ref$FloatRef.this.element = ((Number) viewStateAccessor.alpha.invoke()).floatValue();
                return Unit.INSTANCE;
            }
        }, null, null, null, null, IKnoxCustomManager.Stub.TRANSACTION_getHardKeyIntentMode);
        AlternateBouncerToGoneTransitionViewModel alternateBouncerToGoneTransitionViewModel = this.alternateBouncerToGoneTransitionViewModel;
        alternateBouncerToGoneTransitionViewModel.getClass();
        final Ref$FloatRef ref$FloatRef2 = new Ref$FloatRef();
        ref$FloatRef2.element = 1.0f;
        Duration.Companion companion = Duration.Companion;
        DurationUnit durationUnit = DurationUnit.MILLISECONDS;
        KeyguardTransitionAnimationFlow$FlowBuilder$sharedFlow74qcysc$$inlined$mapNotNull$1 m2599sharedFlow74qcysc$default2 = KeyguardTransitionAnimationFlow.FlowBuilder.m2599sharedFlow74qcysc$default(alternateBouncerToGoneTransitionViewModel.transitionAnimation, DurationKt.toDuration(200, durationUnit), new Function1() { // from class: com.android.systemui.keyguard.ui.viewmodel.AlternateBouncerToGoneTransitionViewModel$$ExternalSyntheticLambda3
            @Override // kotlin.jvm.functions.Function1
            /* renamed from: invoke */
            public final Object mo779invoke(Object obj) {
                return Float.valueOf(MathUtils.lerp(Ref$FloatRef.this.element, 0.0f, ((Float) obj).floatValue()));
            }
        }, 0L, new Function0() { // from class: com.android.systemui.keyguard.ui.viewmodel.AlternateBouncerToGoneTransitionViewModel$$ExternalSyntheticLambda4
            @Override // kotlin.jvm.functions.Function0
            public final Object invoke() {
                Ref$FloatRef.this.element = ((Number) viewStateAccessor.alpha.invoke()).floatValue();
                return Unit.INSTANCE;
            }
        }, new Function0() { // from class: com.android.systemui.keyguard.ui.viewmodel.AlternateBouncerToGoneTransitionViewModel$$ExternalSyntheticLambda5
            @Override // kotlin.jvm.functions.Function0
            public final Object invoke() {
                return Float.valueOf(Ref$FloatRef.this.element);
            }
        }, new AlternateBouncerToAodTransitionViewModel$$ExternalSyntheticLambda1(), null, null, 196);
        AlternateBouncerToLockscreenTransitionViewModel alternateBouncerToLockscreenTransitionViewModel = this.alternateBouncerToLockscreenTransitionViewModel;
        alternateBouncerToLockscreenTransitionViewModel.getClass();
        final Ref$FloatRef ref$FloatRef3 = new Ref$FloatRef();
        ref$FloatRef3.element = 1.0f;
        KeyguardTransitionAnimationFlow$FlowBuilder$sharedFlow74qcysc$$inlined$mapNotNull$1 m2599sharedFlow74qcysc$default3 = KeyguardTransitionAnimationFlow.FlowBuilder.m2599sharedFlow74qcysc$default(alternateBouncerToLockscreenTransitionViewModel.transitionAnimation, DurationKt.toDuration(IKnoxCustomManager.Stub.TRANSACTION_addDexURLShortcutExtend, durationUnit), new Function1() { // from class: com.android.systemui.keyguard.ui.viewmodel.AlternateBouncerToLockscreenTransitionViewModel$$ExternalSyntheticLambda0
            @Override // kotlin.jvm.functions.Function1
            /* renamed from: invoke */
            public final Object mo779invoke(Object obj) {
                return Float.valueOf(MathUtils.lerp(Ref$FloatRef.this.element, 1.0f, ((Float) obj).floatValue()));
            }
        }, 0L, new Function0() { // from class: com.android.systemui.keyguard.ui.viewmodel.AlternateBouncerToLockscreenTransitionViewModel$$ExternalSyntheticLambda1
            @Override // kotlin.jvm.functions.Function0
            public final Object invoke() {
                Ref$FloatRef.this.element = ((Number) viewStateAccessor.alpha.invoke()).floatValue();
                return Unit.INSTANCE;
            }
        }, null, null, null, null, IKnoxCustomManager.Stub.TRANSACTION_getHardKeyIntentMode);
        EmptyFlow emptyFlow = this.alternateBouncerToPrimaryBouncerTransitionViewModel.lockscreenAlpha;
        KeyguardTransitionAnimationFlow$FlowBuilder$sharedFlow74qcysc$$inlined$mapNotNull$1 keyguardTransitionAnimationFlow$FlowBuilder$sharedFlow74qcysc$$inlined$mapNotNull$1 = this.alternateBouncerToOccludedTransitionViewModel.lockscreenAlpha;
        AodToGoneTransitionViewModel aodToGoneTransitionViewModel = this.aodToGoneTransitionViewModel;
        aodToGoneTransitionViewModel.getClass();
        Ref$FloatRef ref$FloatRef4 = new Ref$FloatRef();
        ref$FloatRef4.element = 1.0f;
        KeyguardTransitionAnimationFlow$FlowBuilder$sharedFlow74qcysc$$inlined$mapNotNull$1 m2599sharedFlow74qcysc$default4 = KeyguardTransitionAnimationFlow.FlowBuilder.m2599sharedFlow74qcysc$default(aodToGoneTransitionViewModel.transitionAnimation, DurationKt.toDuration(200, durationUnit), new AodToGoneTransitionViewModel$$ExternalSyntheticLambda0(ref$FloatRef4, 1), 0L, new AodToGoneTransitionViewModel$$ExternalSyntheticLambda1(ref$FloatRef4, viewStateAccessor, i), null, new AlternateBouncerToAodTransitionViewModel$$ExternalSyntheticLambda1(), null, null, IKnoxCustomManager.Stub.TRANSACTION_getWifiState);
        AodToLockscreenTransitionViewModel aodToLockscreenTransitionViewModel = this.aodToLockscreenTransitionViewModel;
        aodToLockscreenTransitionViewModel.getClass();
        Ref$FloatRef ref$FloatRef5 = new Ref$FloatRef();
        ref$FloatRef5.element = 1.0f;
        KeyguardTransitionAnimationFlow$FlowBuilder$sharedFlow74qcysc$$inlined$mapNotNull$1 m2599sharedFlow74qcysc$default5 = KeyguardTransitionAnimationFlow.FlowBuilder.m2599sharedFlow74qcysc$default(aodToLockscreenTransitionViewModel.transitionAnimation, DurationKt.toDuration(500, durationUnit), new AodToLockscreenTransitionViewModel$$ExternalSyntheticLambda0(ref$FloatRef5, 2), 0L, new AodToLockscreenTransitionViewModel$$ExternalSyntheticLambda5(i, ref$FloatRef5, viewStateAccessor), null, null, null, null, IKnoxCustomManager.Stub.TRANSACTION_getHardKeyIntentMode);
        Flow lockscreenAlpha = this.aodToOccludedTransitionViewModel.lockscreenAlpha(viewStateAccessor);
        EmptyFlow emptyFlow2 = this.aodToPrimaryBouncerTransitionViewModel.lockscreenAlpha;
        Flow lockscreenAlpha2 = this.aodToGlanceableHubTransitionViewModel.lockscreenAlpha(viewStateAccessor);
        KeyguardTransitionAnimationFlow$FlowBuilder$sharedFlow74qcysc$$inlined$mapNotNull$1 keyguardTransitionAnimationFlow$FlowBuilder$sharedFlow74qcysc$$inlined$mapNotNull$12 = this.dozingToDreamingTransitionViewModel.lockscreenAlpha;
        DozingToGoneTransitionViewModel dozingToGoneTransitionViewModel = this.dozingToGoneTransitionViewModel;
        dozingToGoneTransitionViewModel.getClass();
        KeyguardTransitionAnimationFlow$FlowBuilder$sharedFlow74qcysc$$inlined$mapNotNull$1 m2599sharedFlow74qcysc$default6 = KeyguardTransitionAnimationFlow.FlowBuilder.m2599sharedFlow74qcysc$default(dozingToGoneTransitionViewModel.transitionAnimation, DurationKt.toDuration(200, durationUnit), new DozingToGoneTransitionViewModel$$ExternalSyntheticLambda0(), 0L, new DozingToGoneTransitionViewModel$$ExternalSyntheticLambda1(), null, null, null, null, IKnoxCustomManager.Stub.TRANSACTION_getHardKeyIntentMode);
        KeyguardTransitionAnimationFlow$FlowBuilder$sharedFlow74qcysc$$inlined$mapNotNull$1 keyguardTransitionAnimationFlow$FlowBuilder$sharedFlow74qcysc$$inlined$mapNotNull$13 = this.dozingToLockscreenTransitionViewModel.lockscreenAlpha;
        Flow lockscreenAlpha3 = this.dozingToOccludedTransitionViewModel.lockscreenAlpha(viewStateAccessor);
        EmptyFlow emptyFlow3 = this.dozingToPrimaryBouncerTransitionViewModel.lockscreenAlpha;
        KeyguardTransitionAnimationFlow$FlowBuilder$sharedFlow74qcysc$$inlined$mapNotNull$1 keyguardTransitionAnimationFlow$FlowBuilder$sharedFlow74qcysc$$inlined$mapNotNull$14 = this.dreamingToAodTransitionViewModel.lockscreenAlpha;
        KeyguardTransitionAnimationFlow$FlowBuilder$sharedFlow74qcysc$$inlined$mapNotNull$1 keyguardTransitionAnimationFlow$FlowBuilder$sharedFlow74qcysc$$inlined$mapNotNull$15 = this.dreamingToGoneTransitionViewModel.lockscreenAlpha;
        KeyguardTransitionAnimationFlow$FlowBuilder$sharedFlow74qcysc$$inlined$mapNotNull$1 keyguardTransitionAnimationFlow$FlowBuilder$sharedFlow74qcysc$$inlined$mapNotNull$16 = this.dreamingToLockscreenTransitionViewModel.lockscreenAlpha;
        ChannelFlowTransformLatest channelFlowTransformLatest = this.glanceableHubToLockscreenTransitionViewModel.keyguardAlpha;
        KeyguardTransitionAnimationFlow$FlowBuilder$sharedFlow74qcysc$$inlined$mapNotNull$1 keyguardTransitionAnimationFlow$FlowBuilder$sharedFlow74qcysc$$inlined$mapNotNull$17 = this.glanceableHubToAodTransitionViewModel.lockscreenAlpha;
        KeyguardTransitionAnimationFlow$FlowBuilder$sharedFlow74qcysc$$inlined$mapNotNull$1 keyguardTransitionAnimationFlow$FlowBuilder$sharedFlow74qcysc$$inlined$mapNotNull$18 = this.goneToAodTransitionViewModel.enterFromTopAnimationAlpha;
        KeyguardTransitionAnimationFlow$FlowBuilder$sharedFlow74qcysc$$inlined$mapNotNull$1 keyguardTransitionAnimationFlow$FlowBuilder$sharedFlow74qcysc$$inlined$mapNotNull$19 = this.goneToDozingTransitionViewModel.lockscreenAlpha;
        KeyguardTransitionAnimationFlow$FlowBuilder$sharedFlow74qcysc$$inlined$mapNotNull$1 keyguardTransitionAnimationFlow$FlowBuilder$sharedFlow74qcysc$$inlined$mapNotNull$110 = this.goneToDreamingTransitionViewModel.lockscreenAlpha;
        KeyguardTransitionAnimationFlow$FlowBuilder$sharedFlow74qcysc$$inlined$mapNotNull$1 keyguardTransitionAnimationFlow$FlowBuilder$sharedFlow74qcysc$$inlined$mapNotNull$111 = this.goneToLockscreenTransitionViewModel.lockscreenAlpha;
        LockscreenToAodTransitionViewModel lockscreenToAodTransitionViewModel = this.lockscreenToAodTransitionViewModel;
        lockscreenToAodTransitionViewModel.getClass();
        final Ref$FloatRef ref$FloatRef6 = new Ref$FloatRef();
        ref$FloatRef6.element = 1.0f;
        SafeFlow safeFlow = new SafeFlow(new LockscreenToAodTransitionViewModel$lockscreenAlpha$$inlined$transform$1(com.android.systemui.util.kotlin.FlowKt.sample(KeyguardTransitionAnimationFlow.FlowBuilder.m2599sharedFlow74qcysc$default(lockscreenToAodTransitionViewModel.transitionAnimation, DurationKt.toDuration(500, durationUnit), new Function1() { // from class: com.android.systemui.keyguard.ui.viewmodel.LockscreenToAodTransitionViewModel$$ExternalSyntheticLambda3
            @Override // kotlin.jvm.functions.Function1
            /* renamed from: invoke */
            public final Object mo779invoke(Object obj) {
                return Float.valueOf(MathUtils.lerp(Ref$FloatRef.this.element, 1.0f, ((Float) obj).floatValue()));
            }
        }, 0L, new Function0() { // from class: com.android.systemui.keyguard.ui.viewmodel.LockscreenToAodTransitionViewModel$$ExternalSyntheticLambda4
            @Override // kotlin.jvm.functions.Function0
            public final Object invoke() {
                Ref$FloatRef.this.element = ((Number) viewStateAccessor.alpha.invoke()).floatValue();
                return Unit.INSTANCE;
            }
        }, null, null, null, null, IKnoxCustomManager.Stub.TRANSACTION_getHardKeyIntentMode), lockscreenToAodTransitionViewModel.powerInteractor.detailedWakefulness, LockscreenToAodTransitionViewModel$lockscreenAlpha$5.INSTANCE), null));
        KeyguardTransitionAnimationFlow$FlowBuilder$sharedFlow74qcysc$$inlined$mapNotNull$1 keyguardTransitionAnimationFlow$FlowBuilder$sharedFlow74qcysc$$inlined$mapNotNull$112 = this.lockscreenToDozingTransitionViewModel.lockscreenAlpha;
        KeyguardTransitionAnimationFlow$FlowBuilder$sharedFlow74qcysc$$inlined$mapNotNull$1 keyguardTransitionAnimationFlow$FlowBuilder$sharedFlow74qcysc$$inlined$mapNotNull$113 = this.lockscreenToDreamingTransitionViewModel.lockscreenAlpha;
        KeyguardTransitionAnimationFlow$FlowBuilder$sharedFlow74qcysc$$inlined$mapNotNull$1 keyguardTransitionAnimationFlow$FlowBuilder$sharedFlow74qcysc$$inlined$mapNotNull$114 = this.lockscreenToGlanceableHubTransitionViewModel.keyguardAlpha;
        LockscreenToGoneTransitionViewModel lockscreenToGoneTransitionViewModel = this.lockscreenToGoneTransitionViewModel;
        lockscreenToGoneTransitionViewModel.getClass();
        final Ref$FloatRef ref$FloatRef7 = new Ref$FloatRef();
        ref$FloatRef7.element = 1.0f;
        return dumpWhileCollecting(FlowKt.distinctUntilChanged(new FlowKt__ZipKt$combine$$inlined$unsafeFlow$1(this.hideKeyguard, new FlowKt__EmittersKt$onStart$$inlined$unsafeFlow$1(new KeyguardRootViewModel$alpha$1(null), FlowKt.merge(this.alphaOnShadeExpansion, flow, m2599sharedFlow74qcysc$default, m2599sharedFlow74qcysc$default2, m2599sharedFlow74qcysc$default3, emptyFlow, keyguardTransitionAnimationFlow$FlowBuilder$sharedFlow74qcysc$$inlined$mapNotNull$1, m2599sharedFlow74qcysc$default4, m2599sharedFlow74qcysc$default5, lockscreenAlpha, emptyFlow2, lockscreenAlpha2, keyguardTransitionAnimationFlow$FlowBuilder$sharedFlow74qcysc$$inlined$mapNotNull$12, m2599sharedFlow74qcysc$default6, keyguardTransitionAnimationFlow$FlowBuilder$sharedFlow74qcysc$$inlined$mapNotNull$13, lockscreenAlpha3, emptyFlow3, keyguardTransitionAnimationFlow$FlowBuilder$sharedFlow74qcysc$$inlined$mapNotNull$14, keyguardTransitionAnimationFlow$FlowBuilder$sharedFlow74qcysc$$inlined$mapNotNull$15, keyguardTransitionAnimationFlow$FlowBuilder$sharedFlow74qcysc$$inlined$mapNotNull$16, channelFlowTransformLatest, keyguardTransitionAnimationFlow$FlowBuilder$sharedFlow74qcysc$$inlined$mapNotNull$17, keyguardTransitionAnimationFlow$FlowBuilder$sharedFlow74qcysc$$inlined$mapNotNull$18, keyguardTransitionAnimationFlow$FlowBuilder$sharedFlow74qcysc$$inlined$mapNotNull$19, keyguardTransitionAnimationFlow$FlowBuilder$sharedFlow74qcysc$$inlined$mapNotNull$110, keyguardTransitionAnimationFlow$FlowBuilder$sharedFlow74qcysc$$inlined$mapNotNull$111, safeFlow, lockscreenToAodTransitionViewModel.lockscreenAlphaOnFold, keyguardTransitionAnimationFlow$FlowBuilder$sharedFlow74qcysc$$inlined$mapNotNull$112, keyguardTransitionAnimationFlow$FlowBuilder$sharedFlow74qcysc$$inlined$mapNotNull$113, keyguardTransitionAnimationFlow$FlowBuilder$sharedFlow74qcysc$$inlined$mapNotNull$114, KeyguardTransitionAnimationFlow.FlowBuilder.m2599sharedFlow74qcysc$default(lockscreenToGoneTransitionViewModel.transitionAnimation, DurationKt.toDuration(200, durationUnit), new Function1() { // from class: com.android.systemui.keyguard.ui.viewmodel.LockscreenToGoneTransitionViewModel$$ExternalSyntheticLambda3
            @Override // kotlin.jvm.functions.Function1
            /* renamed from: invoke */
            public final Object mo779invoke(Object obj) {
                return Float.valueOf(MathUtils.lerp(Ref$FloatRef.this.element, 0.0f, ((Float) obj).floatValue()));
            }
        }, 0L, new Function0() { // from class: com.android.systemui.keyguard.ui.viewmodel.LockscreenToGoneTransitionViewModel$$ExternalSyntheticLambda4
            @Override // kotlin.jvm.functions.Function0
            public final Object invoke() {
                Ref$FloatRef.this.element = ((Number) viewStateAccessor.alpha.invoke()).floatValue();
                return Unit.INSTANCE;
            }
        }, new AnimatedBackgroundKt$$ExternalSyntheticLambda0(), new AlternateBouncerToAodTransitionViewModel$$ExternalSyntheticLambda1(), null, null, 196), this.lockscreenToOccludedTransitionViewModel.lockscreenAlpha, this.lockscreenToPrimaryBouncerTransitionViewModel.lockscreenAlpha, this.occludedToAlternateBouncerTransitionViewModel.lockscreenAlpha, this.occludedToAodTransitionViewModel.lockscreenAlpha, this.occludedToDozingTransitionViewModel.lockscreenAlpha, this.occludedToLockscreenTransitionViewModel.lockscreenAlpha, this.occludedToPrimaryBouncerTransitionViewModel.lockscreenAlpha, this.offToLockscreenTransitionViewModel.lockscreenAlpha, this.primaryBouncerToAodTransitionViewModel.lockscreenAlpha, this.primaryBouncerToGoneTransitionViewModel.lockscreenAlpha, this.primaryBouncerToLockscreenTransitionViewModel.lockscreenAlpha(viewStateAccessor), this.goneToGlanceableHubTransitionViewModel.keyguardAlpha)), new KeyguardRootViewModel$alpha$2(null))), "alpha");
    }
}
