package com.android.systemui.statusbar.notification.stack.ui.viewmodel;

import androidx.appcompat.app.AppCompatDelegateImpl$$ExternalSyntheticOutline0;
import androidx.compose.animation.core.KeyframesSpec$KeyframeEntity$$ExternalSyntheticOutline0;
import com.android.compose.animation.scene.SceneKey;
import com.android.systemui.Flags;
import com.android.systemui.common.shared.model.NotificationContainerBounds;
import com.android.systemui.dump.DumpManager;
import com.android.systemui.flags.RefactorFlagUtils;
import com.android.systemui.keyguard.domain.interactor.KeyguardInteractor;
import com.android.systemui.keyguard.domain.interactor.KeyguardTransitionInteractor;
import com.android.systemui.keyguard.shared.model.Edge;
import com.android.systemui.keyguard.shared.model.KeyguardState;
import com.android.systemui.keyguard.ui.viewmodel.AlternateBouncerToGoneTransitionViewModel;
import com.android.systemui.keyguard.ui.viewmodel.AodBurnInViewModel;
import com.android.systemui.keyguard.ui.viewmodel.AodToGoneTransitionViewModel;
import com.android.systemui.keyguard.ui.viewmodel.AodToLockscreenTransitionViewModel;
import com.android.systemui.keyguard.ui.viewmodel.AodToOccludedTransitionViewModel;
import com.android.systemui.keyguard.ui.viewmodel.DozingToLockscreenTransitionViewModel;
import com.android.systemui.keyguard.ui.viewmodel.DozingToOccludedTransitionViewModel;
import com.android.systemui.keyguard.ui.viewmodel.DreamingToLockscreenTransitionViewModel;
import com.android.systemui.keyguard.ui.viewmodel.GlanceableHubToLockscreenTransitionViewModel;
import com.android.systemui.keyguard.ui.viewmodel.GoneToAodTransitionViewModel;
import com.android.systemui.keyguard.ui.viewmodel.GoneToDozingTransitionViewModel;
import com.android.systemui.keyguard.ui.viewmodel.GoneToDreamingTransitionViewModel;
import com.android.systemui.keyguard.ui.viewmodel.GoneToLockscreenTransitionViewModel;
import com.android.systemui.keyguard.ui.viewmodel.LockscreenToDreamingTransitionViewModel;
import com.android.systemui.keyguard.ui.viewmodel.LockscreenToGlanceableHubTransitionViewModel;
import com.android.systemui.keyguard.ui.viewmodel.LockscreenToGoneTransitionViewModel;
import com.android.systemui.keyguard.ui.viewmodel.LockscreenToOccludedTransitionViewModel;
import com.android.systemui.keyguard.ui.viewmodel.LockscreenToPrimaryBouncerTransitionViewModel;
import com.android.systemui.keyguard.ui.viewmodel.OccludedToAodTransitionViewModel;
import com.android.systemui.keyguard.ui.viewmodel.OccludedToGoneTransitionViewModel;
import com.android.systemui.keyguard.ui.viewmodel.OccludedToLockscreenTransitionViewModel;
import com.android.systemui.keyguard.ui.viewmodel.PrimaryBouncerToGoneTransitionViewModel;
import com.android.systemui.keyguard.ui.viewmodel.PrimaryBouncerToLockscreenTransitionViewModel;
import com.android.systemui.scene.shared.flag.SceneContainerFlag;
import com.android.systemui.scene.shared.model.Scenes;
import com.android.systemui.shade.domain.interactor.ShadeInteractor;
import com.android.systemui.shade.domain.interactor.ShadeInteractorImpl;
import com.android.systemui.statusbar.notification.stack.domain.interactor.NotificationStackAppearanceInteractor;
import com.android.systemui.statusbar.notification.stack.domain.interactor.SharedNotificationContainerInteractor;
import com.android.systemui.unfold.domain.interactor.UnfoldTransitionInteractor;
import com.android.systemui.util.kotlin.BooleanFlowOperators;
import com.android.systemui.util.kotlin.FlowDumperImpl;
import com.android.systemui.util.kotlin.Utils;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.Set;
import kotlin.Lazy;
import kotlin.LazyKt__LazyJVMKt;
import kotlin.ResultKt;
import kotlin.Triple;
import kotlin.Unit;
import kotlin.collections.CollectionsKt__IterablesKt;
import kotlin.collections.SetsKt__SetsKt;
import kotlin.coroutines.Continuation;
import kotlin.coroutines.intrinsics.CoroutineSingletons;
import kotlin.coroutines.jvm.internal.ContinuationImpl;
import kotlin.coroutines.jvm.internal.SuspendLambda;
import kotlin.jvm.functions.Function0;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.functions.Function2;
import kotlin.jvm.functions.Function5;
import kotlinx.coroutines.CoroutineScope;
import kotlinx.coroutines.flow.EmptyFlow;
import kotlinx.coroutines.flow.Flow;
import kotlinx.coroutines.flow.FlowCollector;
import kotlinx.coroutines.flow.FlowKt;
import kotlinx.coroutines.flow.FlowKt__EmittersKt$onStart$$inlined$unsafeFlow$1;
import kotlinx.coroutines.flow.FlowKt__ZipKt$combine$$inlined$combineUnsafe$FlowKt__ZipKt$2;
import kotlinx.coroutines.flow.FlowKt__ZipKt$combine$$inlined$unsafeFlow$1;
import kotlinx.coroutines.flow.FlowKt__ZipKt$combineTransform$$inlined$combineTransformUnsafe$FlowKt__ZipKt$4;
import kotlinx.coroutines.flow.MutableSharedFlow;
import kotlinx.coroutines.flow.ReadonlySharedFlow;
import kotlinx.coroutines.flow.ReadonlyStateFlow;
import kotlinx.coroutines.flow.SafeFlow;
import kotlinx.coroutines.flow.SharingStarted;
import kotlinx.coroutines.flow.StartedEagerly;
import kotlinx.coroutines.flow.StateFlow;

public final class SharedNotificationContainerViewModel extends FlowDumperImpl {
    public final Flow alphaForShadeAndQsExpansion;
    public final AlternateBouncerToGoneTransitionViewModel alternateBouncerToGoneTransitionViewModel;
    public final AodBurnInViewModel aodBurnInViewModel;
    public final AodToGoneTransitionViewModel aodToGoneTransitionViewModel;
    public final AodToLockscreenTransitionViewModel aodToLockscreenTransitionViewModel;
    public final AodToOccludedTransitionViewModel aodToOccludedTransitionViewModel;
    public final Flow availableHeight;
    public final Lazy bounds$delegate;
    public final Flow configurationBasedDimensions;
    public final DozingToLockscreenTransitionViewModel dozingToLockscreenTransitionViewModel;
    public final DozingToOccludedTransitionViewModel dozingToOccludedTransitionViewModel;
    public final DreamingToLockscreenTransitionViewModel dreamingToLockscreenTransitionViewModel;
    public final Flow glanceableHubAlpha;
    public final GoneToAodTransitionViewModel goneToAodTransitionViewModel;
    public final GoneToDozingTransitionViewModel goneToDozingTransitionViewModel;
    public final GoneToDreamingTransitionViewModel goneToDreamingTransitionViewModel;
    public final GoneToLockscreenTransitionViewModel goneToLockscreenTransitionViewModel;
    public final SharedNotificationContainerInteractor interactor;
    public final ReadonlyStateFlow isAnyExpanded;
    public final StateFlow isDreamingWithoutShade;
    public final StateFlow isOnGlanceableHubWithoutShade;
    public final StateFlow isOnLockscreen;
    public final StateFlow isOnLockscreenWithoutShade;
    public final Flow isShadeLocked;
    public final Flow isTransitioningToHiddenKeyguard;
    public final KeyguardInteractor keyguardInteractor;
    public final KeyguardTransitionInteractor keyguardTransitionInteractor;
    public final LockscreenToDreamingTransitionViewModel lockscreenToDreamingTransitionViewModel;
    public final LockscreenToGoneTransitionViewModel lockscreenToGoneTransitionViewModel;
    public final LockscreenToOccludedTransitionViewModel lockscreenToOccludedTransitionViewModel;
    public final LockscreenToPrimaryBouncerTransitionViewModel lockscreenToPrimaryBouncerTransitionViewModel;
    public final OccludedToAodTransitionViewModel occludedToAodTransitionViewModel;
    public final OccludedToGoneTransitionViewModel occludedToGoneTransitionViewModel;
    public final OccludedToLockscreenTransitionViewModel occludedToLockscreenTransitionViewModel;
    public final Flow paddingTopDimen;
    public final PrimaryBouncerToGoneTransitionViewModel primaryBouncerToGoneTransitionViewModel;
    public final PrimaryBouncerToLockscreenTransitionViewModel primaryBouncerToLockscreenTransitionViewModel;
    public final StateFlow shadeCollapseFadeIn;
    public final ShadeInteractor shadeInteractor;
    public final Set statesForConstrainedNotifications;
    public final Set statesForHiddenKeyguard;
    public final Flow translationX;

    public final class ConfigurationBasedDimensions {
        public final int marginBottom;
        public final int marginEnd;
        public final int marginStart;
        public final int marginTop;
        public final boolean useSplitShade;

        public ConfigurationBasedDimensions(int i, int i2, int i3, int i4, boolean z) {
            this.marginStart = i;
            this.marginTop = i2;
            this.marginEnd = i3;
            this.marginBottom = i4;
            this.useSplitShade = z;
        }

        public final boolean equals(Object obj) {
            if (this == obj) {
                return true;
            }
            if (!(obj instanceof ConfigurationBasedDimensions)) {
                return false;
            }
            ConfigurationBasedDimensions configurationBasedDimensions = (ConfigurationBasedDimensions) obj;
            return this.marginStart == configurationBasedDimensions.marginStart && this.marginTop == configurationBasedDimensions.marginTop && this.marginEnd == configurationBasedDimensions.marginEnd && this.marginBottom == configurationBasedDimensions.marginBottom && this.useSplitShade == configurationBasedDimensions.useSplitShade;
        }

        public final int hashCode() {
            return Boolean.hashCode(this.useSplitShade) + KeyframesSpec$KeyframeEntity$$ExternalSyntheticOutline0.m(this.marginBottom, KeyframesSpec$KeyframeEntity$$ExternalSyntheticOutline0.m(this.marginEnd, KeyframesSpec$KeyframeEntity$$ExternalSyntheticOutline0.m(this.marginTop, Integer.hashCode(this.marginStart) * 31, 31), 31), 31);
        }

        public final String toString() {
            StringBuilder sb = new StringBuilder("ConfigurationBasedDimensions(marginStart=");
            sb.append(this.marginStart);
            sb.append(", marginTop=");
            sb.append(this.marginTop);
            sb.append(", marginEnd=");
            sb.append(this.marginEnd);
            sb.append(", marginBottom=");
            sb.append(this.marginBottom);
            sb.append(", useSplitShade=");
            return AppCompatDelegateImpl$$ExternalSyntheticOutline0.m(sb, this.useSplitShade, ")");
        }
    }

    public SharedNotificationContainerViewModel(SharedNotificationContainerInteractor sharedNotificationContainerInteractor, DumpManager dumpManager, final CoroutineScope coroutineScope, KeyguardInteractor keyguardInteractor, KeyguardTransitionInteractor keyguardTransitionInteractor, ShadeInteractor shadeInteractor, NotificationStackAppearanceInteractor notificationStackAppearanceInteractor, AlternateBouncerToGoneTransitionViewModel alternateBouncerToGoneTransitionViewModel, AodToGoneTransitionViewModel aodToGoneTransitionViewModel, AodToLockscreenTransitionViewModel aodToLockscreenTransitionViewModel, AodToOccludedTransitionViewModel aodToOccludedTransitionViewModel, DozingToLockscreenTransitionViewModel dozingToLockscreenTransitionViewModel, DozingToOccludedTransitionViewModel dozingToOccludedTransitionViewModel, DreamingToLockscreenTransitionViewModel dreamingToLockscreenTransitionViewModel, GlanceableHubToLockscreenTransitionViewModel glanceableHubToLockscreenTransitionViewModel, GoneToAodTransitionViewModel goneToAodTransitionViewModel, GoneToDozingTransitionViewModel goneToDozingTransitionViewModel, GoneToDreamingTransitionViewModel goneToDreamingTransitionViewModel, GoneToLockscreenTransitionViewModel goneToLockscreenTransitionViewModel, LockscreenToDreamingTransitionViewModel lockscreenToDreamingTransitionViewModel, LockscreenToGlanceableHubTransitionViewModel lockscreenToGlanceableHubTransitionViewModel, LockscreenToGoneTransitionViewModel lockscreenToGoneTransitionViewModel, LockscreenToPrimaryBouncerTransitionViewModel lockscreenToPrimaryBouncerTransitionViewModel, LockscreenToOccludedTransitionViewModel lockscreenToOccludedTransitionViewModel, OccludedToAodTransitionViewModel occludedToAodTransitionViewModel, OccludedToGoneTransitionViewModel occludedToGoneTransitionViewModel, OccludedToLockscreenTransitionViewModel occludedToLockscreenTransitionViewModel, PrimaryBouncerToGoneTransitionViewModel primaryBouncerToGoneTransitionViewModel, PrimaryBouncerToLockscreenTransitionViewModel primaryBouncerToLockscreenTransitionViewModel, AodBurnInViewModel aodBurnInViewModel, UnfoldTransitionInteractor unfoldTransitionInteractor) {
        super(dumpManager, null, 2, null);
        this.interactor = sharedNotificationContainerInteractor;
        this.keyguardInteractor = keyguardInteractor;
        this.keyguardTransitionInteractor = keyguardTransitionInteractor;
        this.shadeInteractor = shadeInteractor;
        KeyguardState keyguardState = KeyguardState.AOD;
        KeyguardState keyguardState2 = KeyguardState.LOCKSCREEN;
        this.statesForConstrainedNotifications = SetsKt__SetsKt.setOf(keyguardState, keyguardState2, KeyguardState.DOZING, KeyguardState.ALTERNATE_BOUNCER, KeyguardState.PRIMARY_BOUNCER);
        this.statesForHiddenKeyguard = SetsKt__SetsKt.setOf(KeyguardState.GONE, KeyguardState.OCCLUDED);
        ShadeInteractorImpl shadeInteractorImpl = (ShadeInteractorImpl) shadeInteractor;
        final StateFlow shadeExpansion = shadeInteractorImpl.baseShadeInteractor.getShadeExpansion();
        Flow flow = new Flow() { // from class: com.android.systemui.statusbar.notification.stack.ui.viewmodel.SharedNotificationContainerViewModel$special$$inlined$map$1

            /* renamed from: com.android.systemui.statusbar.notification.stack.ui.viewmodel.SharedNotificationContainerViewModel$special$$inlined$map$1$2, reason: invalid class name */
            public final class AnonymousClass2 implements FlowCollector {
                public final /* synthetic */ FlowCollector $this_unsafeFlow;

                /* renamed from: com.android.systemui.statusbar.notification.stack.ui.viewmodel.SharedNotificationContainerViewModel$special$$inlined$map$1$2$1, reason: invalid class name */
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
                        boolean r0 = r6 instanceof com.android.systemui.statusbar.notification.stack.ui.viewmodel.SharedNotificationContainerViewModel$special$$inlined$map$1.AnonymousClass2.AnonymousClass1
                        if (r0 == 0) goto L13
                        r0 = r6
                        com.android.systemui.statusbar.notification.stack.ui.viewmodel.SharedNotificationContainerViewModel$special$$inlined$map$1$2$1 r0 = (com.android.systemui.statusbar.notification.stack.ui.viewmodel.SharedNotificationContainerViewModel$special$$inlined$map$1.AnonymousClass2.AnonymousClass1) r0
                        int r1 = r0.label
                        r2 = -2147483648(0xffffffff80000000, float:-0.0)
                        r3 = r1 & r2
                        if (r3 == 0) goto L13
                        int r1 = r1 - r2
                        r0.label = r1
                        goto L18
                    L13:
                        com.android.systemui.statusbar.notification.stack.ui.viewmodel.SharedNotificationContainerViewModel$special$$inlined$map$1$2$1 r0 = new com.android.systemui.statusbar.notification.stack.ui.viewmodel.SharedNotificationContainerViewModel$special$$inlined$map$1$2$1
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
                    throw new UnsupportedOperationException("Method not decompiled: com.android.systemui.statusbar.notification.stack.ui.viewmodel.SharedNotificationContainerViewModel$special$$inlined$map$1.AnonymousClass2.emit(java.lang.Object, kotlin.coroutines.Continuation):java.lang.Object");
                }
            }

            @Override // kotlinx.coroutines.flow.Flow
            public final Object collect(FlowCollector flowCollector, Continuation continuation) {
                Object collect = Flow.this.collect(new AnonymousClass2(flowCollector), continuation);
                return collect == CoroutineSingletons.COROUTINE_SUSPENDED ? collect : Unit.INSTANCE;
            }
        };
        final StateFlow qsExpansion = shadeInteractorImpl.baseShadeInteractor.getQsExpansion();
        FlowKt__ZipKt$combine$$inlined$unsafeFlow$1 flowKt__ZipKt$combine$$inlined$unsafeFlow$1 = new FlowKt__ZipKt$combine$$inlined$unsafeFlow$1(flow, new Flow() { // from class: com.android.systemui.statusbar.notification.stack.ui.viewmodel.SharedNotificationContainerViewModel$special$$inlined$map$2

            /* renamed from: com.android.systemui.statusbar.notification.stack.ui.viewmodel.SharedNotificationContainerViewModel$special$$inlined$map$2$2, reason: invalid class name */
            public final class AnonymousClass2 implements FlowCollector {
                public final /* synthetic */ FlowCollector $this_unsafeFlow;

                /* renamed from: com.android.systemui.statusbar.notification.stack.ui.viewmodel.SharedNotificationContainerViewModel$special$$inlined$map$2$2$1, reason: invalid class name */
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
                        boolean r0 = r6 instanceof com.android.systemui.statusbar.notification.stack.ui.viewmodel.SharedNotificationContainerViewModel$special$$inlined$map$2.AnonymousClass2.AnonymousClass1
                        if (r0 == 0) goto L13
                        r0 = r6
                        com.android.systemui.statusbar.notification.stack.ui.viewmodel.SharedNotificationContainerViewModel$special$$inlined$map$2$2$1 r0 = (com.android.systemui.statusbar.notification.stack.ui.viewmodel.SharedNotificationContainerViewModel$special$$inlined$map$2.AnonymousClass2.AnonymousClass1) r0
                        int r1 = r0.label
                        r2 = -2147483648(0xffffffff80000000, float:-0.0)
                        r3 = r1 & r2
                        if (r3 == 0) goto L13
                        int r1 = r1 - r2
                        r0.label = r1
                        goto L18
                    L13:
                        com.android.systemui.statusbar.notification.stack.ui.viewmodel.SharedNotificationContainerViewModel$special$$inlined$map$2$2$1 r0 = new com.android.systemui.statusbar.notification.stack.ui.viewmodel.SharedNotificationContainerViewModel$special$$inlined$map$2$2$1
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
                    throw new UnsupportedOperationException("Method not decompiled: com.android.systemui.statusbar.notification.stack.ui.viewmodel.SharedNotificationContainerViewModel$special$$inlined$map$2.AnonymousClass2.emit(java.lang.Object, kotlin.coroutines.Continuation):java.lang.Object");
                }
            }

            @Override // kotlinx.coroutines.flow.Flow
            public final Object collect(FlowCollector flowCollector, Continuation continuation) {
                Object collect = Flow.this.collect(new AnonymousClass2(flowCollector), continuation);
                return collect == CoroutineSingletons.COROUTINE_SUSPENDED ? collect : Unit.INSTANCE;
            }
        }, new SharedNotificationContainerViewModel$isAnyExpanded$3(null));
        SharingStarted.Companion companion = SharingStarted.Companion;
        companion.getClass();
        StartedEagerly startedEagerly = SharingStarted.Companion.Eagerly;
        Boolean bool = Boolean.FALSE;
        ReadonlyStateFlow stateIn = FlowKt.stateIn(flowKt__ZipKt$combine$$inlined$unsafeFlow$1, coroutineScope, startedEagerly, bool);
        final StateFlow stateFlow = keyguardInteractor.statusBarState;
        this.isShadeLocked = dumpWhileCollecting(FlowKt.stateIn(new FlowKt__ZipKt$combine$$inlined$unsafeFlow$1(new Flow() { // from class: com.android.systemui.statusbar.notification.stack.ui.viewmodel.SharedNotificationContainerViewModel$special$$inlined$map$3

            /* renamed from: com.android.systemui.statusbar.notification.stack.ui.viewmodel.SharedNotificationContainerViewModel$special$$inlined$map$3$2, reason: invalid class name */
            public final class AnonymousClass2 implements FlowCollector {
                public final /* synthetic */ FlowCollector $this_unsafeFlow;

                /* renamed from: com.android.systemui.statusbar.notification.stack.ui.viewmodel.SharedNotificationContainerViewModel$special$$inlined$map$3$2$1, reason: invalid class name */
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
                        boolean r0 = r6 instanceof com.android.systemui.statusbar.notification.stack.ui.viewmodel.SharedNotificationContainerViewModel$special$$inlined$map$3.AnonymousClass2.AnonymousClass1
                        if (r0 == 0) goto L13
                        r0 = r6
                        com.android.systemui.statusbar.notification.stack.ui.viewmodel.SharedNotificationContainerViewModel$special$$inlined$map$3$2$1 r0 = (com.android.systemui.statusbar.notification.stack.ui.viewmodel.SharedNotificationContainerViewModel$special$$inlined$map$3.AnonymousClass2.AnonymousClass1) r0
                        int r1 = r0.label
                        r2 = -2147483648(0xffffffff80000000, float:-0.0)
                        r3 = r1 & r2
                        if (r3 == 0) goto L13
                        int r1 = r1 - r2
                        r0.label = r1
                        goto L18
                    L13:
                        com.android.systemui.statusbar.notification.stack.ui.viewmodel.SharedNotificationContainerViewModel$special$$inlined$map$3$2$1 r0 = new com.android.systemui.statusbar.notification.stack.ui.viewmodel.SharedNotificationContainerViewModel$special$$inlined$map$3$2$1
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
                        com.android.systemui.keyguard.shared.model.StatusBarState r5 = (com.android.systemui.keyguard.shared.model.StatusBarState) r5
                        com.android.systemui.keyguard.shared.model.StatusBarState r6 = com.android.systemui.keyguard.shared.model.StatusBarState.SHADE_LOCKED
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
                    throw new UnsupportedOperationException("Method not decompiled: com.android.systemui.statusbar.notification.stack.ui.viewmodel.SharedNotificationContainerViewModel$special$$inlined$map$3.AnonymousClass2.emit(java.lang.Object, kotlin.coroutines.Continuation):java.lang.Object");
                }
            }

            @Override // kotlinx.coroutines.flow.Flow
            public final Object collect(FlowCollector flowCollector, Continuation continuation) {
                Object collect = Flow.this.collect(new AnonymousClass2(flowCollector), continuation);
                return collect == CoroutineSingletons.COROUTINE_SUSPENDED ? collect : Unit.INSTANCE;
            }
        }, stateIn, new SharedNotificationContainerViewModel$isShadeLocked$2(null)), coroutineScope, startedEagerly, bool), "isShadeLocked");
        final Flow flow2 = sharedNotificationContainerInteractor.configurationBasedDimensions;
        this.paddingTopDimen = dumpWhileCollecting(FlowKt.distinctUntilChanged(new Flow() { // from class: com.android.systemui.statusbar.notification.stack.ui.viewmodel.SharedNotificationContainerViewModel$special$$inlined$map$4

            /* renamed from: com.android.systemui.statusbar.notification.stack.ui.viewmodel.SharedNotificationContainerViewModel$special$$inlined$map$4$2, reason: invalid class name */
            public final class AnonymousClass2 implements FlowCollector {
                public final /* synthetic */ FlowCollector $this_unsafeFlow;

                /* renamed from: com.android.systemui.statusbar.notification.stack.ui.viewmodel.SharedNotificationContainerViewModel$special$$inlined$map$4$2$1, reason: invalid class name */
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
                        boolean r0 = r6 instanceof com.android.systemui.statusbar.notification.stack.ui.viewmodel.SharedNotificationContainerViewModel$special$$inlined$map$4.AnonymousClass2.AnonymousClass1
                        if (r0 == 0) goto L13
                        r0 = r6
                        com.android.systemui.statusbar.notification.stack.ui.viewmodel.SharedNotificationContainerViewModel$special$$inlined$map$4$2$1 r0 = (com.android.systemui.statusbar.notification.stack.ui.viewmodel.SharedNotificationContainerViewModel$special$$inlined$map$4.AnonymousClass2.AnonymousClass1) r0
                        int r1 = r0.label
                        r2 = -2147483648(0xffffffff80000000, float:-0.0)
                        r3 = r1 & r2
                        if (r3 == 0) goto L13
                        int r1 = r1 - r2
                        r0.label = r1
                        goto L18
                    L13:
                        com.android.systemui.statusbar.notification.stack.ui.viewmodel.SharedNotificationContainerViewModel$special$$inlined$map$4$2$1 r0 = new com.android.systemui.statusbar.notification.stack.ui.viewmodel.SharedNotificationContainerViewModel$special$$inlined$map$4$2$1
                        r0.<init>(r6)
                    L18:
                        java.lang.Object r6 = r0.result
                        kotlin.coroutines.intrinsics.CoroutineSingletons r1 = kotlin.coroutines.intrinsics.CoroutineSingletons.COROUTINE_SUSPENDED
                        int r2 = r0.label
                        r3 = 1
                        if (r2 == 0) goto L2f
                        if (r2 != r3) goto L27
                        kotlin.ResultKt.throwOnFailure(r6)
                        goto L53
                    L27:
                        java.lang.IllegalStateException r4 = new java.lang.IllegalStateException
                        java.lang.String r5 = "call to 'resume' before 'invoke' with coroutine"
                        r4.<init>(r5)
                        throw r4
                    L2f:
                        kotlin.ResultKt.throwOnFailure(r6)
                        com.android.systemui.statusbar.notification.stack.domain.interactor.SharedNotificationContainerInteractor$ConfigurationBasedDimensions r5 = (com.android.systemui.statusbar.notification.stack.domain.interactor.SharedNotificationContainerInteractor.ConfigurationBasedDimensions) r5
                        boolean r6 = r5.useSplitShade
                        if (r6 != 0) goto L3a
                        r5 = 0
                        goto L43
                    L3a:
                        boolean r6 = r5.useLargeScreenHeader
                        if (r6 == 0) goto L41
                        int r5 = r5.marginTopLargeScreen
                        goto L43
                    L41:
                        int r5 = r5.marginTop
                    L43:
                        java.lang.Integer r6 = new java.lang.Integer
                        r6.<init>(r5)
                        r0.label = r3
                        kotlinx.coroutines.flow.FlowCollector r4 = r4.$this_unsafeFlow
                        java.lang.Object r4 = r4.emit(r6, r0)
                        if (r4 != r1) goto L53
                        return r1
                    L53:
                        kotlin.Unit r4 = kotlin.Unit.INSTANCE
                        return r4
                    */
                    throw new UnsupportedOperationException("Method not decompiled: com.android.systemui.statusbar.notification.stack.ui.viewmodel.SharedNotificationContainerViewModel$special$$inlined$map$4.AnonymousClass2.emit(java.lang.Object, kotlin.coroutines.Continuation):java.lang.Object");
                }
            }

            @Override // kotlinx.coroutines.flow.Flow
            public final Object collect(FlowCollector flowCollector, Continuation continuation) {
                Object collect = Flow.this.collect(new AnonymousClass2(flowCollector), continuation);
                return collect == CoroutineSingletons.COROUTINE_SUSPENDED ? collect : Unit.INSTANCE;
            }
        }), "paddingTopDimen");
        dumpWhileCollecting(FlowKt.distinctUntilChanged(new Flow() { // from class: com.android.systemui.statusbar.notification.stack.ui.viewmodel.SharedNotificationContainerViewModel$special$$inlined$map$5

            /* renamed from: com.android.systemui.statusbar.notification.stack.ui.viewmodel.SharedNotificationContainerViewModel$special$$inlined$map$5$2, reason: invalid class name */
            public final class AnonymousClass2 implements FlowCollector {
                public final /* synthetic */ FlowCollector $this_unsafeFlow;

                /* renamed from: com.android.systemui.statusbar.notification.stack.ui.viewmodel.SharedNotificationContainerViewModel$special$$inlined$map$5$2$1, reason: invalid class name */
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
                public final java.lang.Object emit(java.lang.Object r11, kotlin.coroutines.Continuation r12) {
                    /*
                        r10 = this;
                        boolean r0 = r12 instanceof com.android.systemui.statusbar.notification.stack.ui.viewmodel.SharedNotificationContainerViewModel$special$$inlined$map$5.AnonymousClass2.AnonymousClass1
                        if (r0 == 0) goto L13
                        r0 = r12
                        com.android.systemui.statusbar.notification.stack.ui.viewmodel.SharedNotificationContainerViewModel$special$$inlined$map$5$2$1 r0 = (com.android.systemui.statusbar.notification.stack.ui.viewmodel.SharedNotificationContainerViewModel$special$$inlined$map$5.AnonymousClass2.AnonymousClass1) r0
                        int r1 = r0.label
                        r2 = -2147483648(0xffffffff80000000, float:-0.0)
                        r3 = r1 & r2
                        if (r3 == 0) goto L13
                        int r1 = r1 - r2
                        r0.label = r1
                        goto L18
                    L13:
                        com.android.systemui.statusbar.notification.stack.ui.viewmodel.SharedNotificationContainerViewModel$special$$inlined$map$5$2$1 r0 = new com.android.systemui.statusbar.notification.stack.ui.viewmodel.SharedNotificationContainerViewModel$special$$inlined$map$5$2$1
                        r0.<init>(r12)
                    L18:
                        java.lang.Object r12 = r0.result
                        kotlin.coroutines.intrinsics.CoroutineSingletons r1 = kotlin.coroutines.intrinsics.CoroutineSingletons.COROUTINE_SUSPENDED
                        int r2 = r0.label
                        r3 = 1
                        if (r2 == 0) goto L2f
                        if (r2 != r3) goto L27
                        kotlin.ResultKt.throwOnFailure(r12)
                        goto L61
                    L27:
                        java.lang.IllegalStateException r10 = new java.lang.IllegalStateException
                        java.lang.String r11 = "call to 'resume' before 'invoke' with coroutine"
                        r10.<init>(r11)
                        throw r10
                    L2f:
                        kotlin.ResultKt.throwOnFailure(r12)
                        com.android.systemui.statusbar.notification.stack.domain.interactor.SharedNotificationContainerInteractor$ConfigurationBasedDimensions r11 = (com.android.systemui.statusbar.notification.stack.domain.interactor.SharedNotificationContainerInteractor.ConfigurationBasedDimensions) r11
                        com.android.systemui.Flags.sceneContainer()
                        boolean r12 = r11.useLargeScreenHeader
                        if (r12 == 0) goto L3f
                        int r12 = r11.marginTopLargeScreen
                    L3d:
                        r6 = r12
                        goto L42
                    L3f:
                        int r12 = r11.marginTop
                        goto L3d
                    L42:
                        boolean r9 = r11.useSplitShade
                        if (r9 == 0) goto L49
                        r12 = 0
                    L47:
                        r5 = r12
                        goto L4c
                    L49:
                        int r12 = r11.marginHorizontal
                        goto L47
                    L4c:
                        com.android.systemui.statusbar.notification.stack.ui.viewmodel.SharedNotificationContainerViewModel$ConfigurationBasedDimensions r12 = new com.android.systemui.statusbar.notification.stack.ui.viewmodel.SharedNotificationContainerViewModel$ConfigurationBasedDimensions
                        int r7 = r11.marginHorizontal
                        int r8 = r11.marginBottom
                        r4 = r12
                        r4.<init>(r5, r6, r7, r8, r9)
                        r0.label = r3
                        kotlinx.coroutines.flow.FlowCollector r10 = r10.$this_unsafeFlow
                        java.lang.Object r10 = r10.emit(r12, r0)
                        if (r10 != r1) goto L61
                        return r1
                    L61:
                        kotlin.Unit r10 = kotlin.Unit.INSTANCE
                        return r10
                    */
                    throw new UnsupportedOperationException("Method not decompiled: com.android.systemui.statusbar.notification.stack.ui.viewmodel.SharedNotificationContainerViewModel$special$$inlined$map$5.AnonymousClass2.emit(java.lang.Object, kotlin.coroutines.Continuation):java.lang.Object");
                }
            }

            @Override // kotlinx.coroutines.flow.Flow
            public final Object collect(FlowCollector flowCollector, Continuation continuation) {
                Object collect = Flow.this.collect(new AnonymousClass2(flowCollector), continuation);
                return collect == CoroutineSingletons.COROUTINE_SUSPENDED ? collect : Unit.INSTANCE;
            }
        }), "configurationBasedDimensions");
        final ReadonlySharedFlow readonlySharedFlow = keyguardTransitionInteractor.finishedKeyguardState;
        Flow flow3 = new Flow() { // from class: com.android.systemui.statusbar.notification.stack.ui.viewmodel.SharedNotificationContainerViewModel$special$$inlined$map$6

            /* renamed from: com.android.systemui.statusbar.notification.stack.ui.viewmodel.SharedNotificationContainerViewModel$special$$inlined$map$6$2, reason: invalid class name */
            public final class AnonymousClass2 implements FlowCollector {
                public final /* synthetic */ FlowCollector $this_unsafeFlow;
                public final /* synthetic */ SharedNotificationContainerViewModel this$0;

                /* renamed from: com.android.systemui.statusbar.notification.stack.ui.viewmodel.SharedNotificationContainerViewModel$special$$inlined$map$6$2$1, reason: invalid class name */
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

                public AnonymousClass2(FlowCollector flowCollector, SharedNotificationContainerViewModel sharedNotificationContainerViewModel) {
                    this.$this_unsafeFlow = flowCollector;
                    this.this$0 = sharedNotificationContainerViewModel;
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
                        boolean r0 = r6 instanceof com.android.systemui.statusbar.notification.stack.ui.viewmodel.SharedNotificationContainerViewModel$special$$inlined$map$6.AnonymousClass2.AnonymousClass1
                        if (r0 == 0) goto L13
                        r0 = r6
                        com.android.systemui.statusbar.notification.stack.ui.viewmodel.SharedNotificationContainerViewModel$special$$inlined$map$6$2$1 r0 = (com.android.systemui.statusbar.notification.stack.ui.viewmodel.SharedNotificationContainerViewModel$special$$inlined$map$6.AnonymousClass2.AnonymousClass1) r0
                        int r1 = r0.label
                        r2 = -2147483648(0xffffffff80000000, float:-0.0)
                        r3 = r1 & r2
                        if (r3 == 0) goto L13
                        int r1 = r1 - r2
                        r0.label = r1
                        goto L18
                    L13:
                        com.android.systemui.statusbar.notification.stack.ui.viewmodel.SharedNotificationContainerViewModel$special$$inlined$map$6$2$1 r0 = new com.android.systemui.statusbar.notification.stack.ui.viewmodel.SharedNotificationContainerViewModel$special$$inlined$map$6$2$1
                        r0.<init>(r6)
                    L18:
                        java.lang.Object r6 = r0.result
                        kotlin.coroutines.intrinsics.CoroutineSingletons r1 = kotlin.coroutines.intrinsics.CoroutineSingletons.COROUTINE_SUSPENDED
                        int r2 = r0.label
                        r3 = 1
                        if (r2 == 0) goto L2f
                        if (r2 != r3) goto L27
                        kotlin.ResultKt.throwOnFailure(r6)
                        goto L4b
                    L27:
                        java.lang.IllegalStateException r4 = new java.lang.IllegalStateException
                        java.lang.String r5 = "call to 'resume' before 'invoke' with coroutine"
                        r4.<init>(r5)
                        throw r4
                    L2f:
                        kotlin.ResultKt.throwOnFailure(r6)
                        com.android.systemui.keyguard.shared.model.KeyguardState r5 = (com.android.systemui.keyguard.shared.model.KeyguardState) r5
                        com.android.systemui.statusbar.notification.stack.ui.viewmodel.SharedNotificationContainerViewModel r6 = r4.this$0
                        java.util.Set r6 = r6.statesForConstrainedNotifications
                        boolean r5 = r6.contains(r5)
                        java.lang.Boolean r5 = java.lang.Boolean.valueOf(r5)
                        r0.label = r3
                        kotlinx.coroutines.flow.FlowCollector r4 = r4.$this_unsafeFlow
                        java.lang.Object r4 = r4.emit(r5, r0)
                        if (r4 != r1) goto L4b
                        return r1
                    L4b:
                        kotlin.Unit r4 = kotlin.Unit.INSTANCE
                        return r4
                    */
                    throw new UnsupportedOperationException("Method not decompiled: com.android.systemui.statusbar.notification.stack.ui.viewmodel.SharedNotificationContainerViewModel$special$$inlined$map$6.AnonymousClass2.emit(java.lang.Object, kotlin.coroutines.Continuation):java.lang.Object");
                }
            }

            @Override // kotlinx.coroutines.flow.Flow
            public final Object collect(FlowCollector flowCollector, Continuation continuation) {
                Object collect = Flow.this.collect(new AnonymousClass2(flowCollector, this), continuation);
                return collect == CoroutineSingletons.COROUTINE_SUSPENDED ? collect : Unit.INSTANCE;
            }
        };
        final MutableSharedFlow transitionValue = keyguardTransitionInteractor.transitionValue(keyguardState2);
        StateFlow dumpValue = dumpValue(FlowKt.stateIn(new FlowKt__ZipKt$combine$$inlined$unsafeFlow$1(flow3, new Flow() { // from class: com.android.systemui.statusbar.notification.stack.ui.viewmodel.SharedNotificationContainerViewModel$special$$inlined$map$7

            /* renamed from: com.android.systemui.statusbar.notification.stack.ui.viewmodel.SharedNotificationContainerViewModel$special$$inlined$map$7$2, reason: invalid class name */
            public final class AnonymousClass2 implements FlowCollector {
                public final /* synthetic */ FlowCollector $this_unsafeFlow;

                /* renamed from: com.android.systemui.statusbar.notification.stack.ui.viewmodel.SharedNotificationContainerViewModel$special$$inlined$map$7$2$1, reason: invalid class name */
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
                        boolean r0 = r6 instanceof com.android.systemui.statusbar.notification.stack.ui.viewmodel.SharedNotificationContainerViewModel$special$$inlined$map$7.AnonymousClass2.AnonymousClass1
                        if (r0 == 0) goto L13
                        r0 = r6
                        com.android.systemui.statusbar.notification.stack.ui.viewmodel.SharedNotificationContainerViewModel$special$$inlined$map$7$2$1 r0 = (com.android.systemui.statusbar.notification.stack.ui.viewmodel.SharedNotificationContainerViewModel$special$$inlined$map$7.AnonymousClass2.AnonymousClass1) r0
                        int r1 = r0.label
                        r2 = -2147483648(0xffffffff80000000, float:-0.0)
                        r3 = r1 & r2
                        if (r3 == 0) goto L13
                        int r1 = r1 - r2
                        r0.label = r1
                        goto L18
                    L13:
                        com.android.systemui.statusbar.notification.stack.ui.viewmodel.SharedNotificationContainerViewModel$special$$inlined$map$7$2$1 r0 = new com.android.systemui.statusbar.notification.stack.ui.viewmodel.SharedNotificationContainerViewModel$special$$inlined$map$7$2$1
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
                    throw new UnsupportedOperationException("Method not decompiled: com.android.systemui.statusbar.notification.stack.ui.viewmodel.SharedNotificationContainerViewModel$special$$inlined$map$7.AnonymousClass2.emit(java.lang.Object, kotlin.coroutines.Continuation):java.lang.Object");
                }
            }

            @Override // kotlinx.coroutines.flow.Flow
            public final Object collect(FlowCollector flowCollector, Continuation continuation) {
                Object collect = Flow.this.collect(new AnonymousClass2(flowCollector), continuation);
                return collect == CoroutineSingletons.COROUTINE_SUSPENDED ? collect : Unit.INSTANCE;
            }
        }, new SharedNotificationContainerViewModel$isOnLockscreen$3(null)), coroutineScope, startedEagerly, bool), "isOnLockscreen");
        this.isOnLockscreen = dumpValue;
        this.isOnLockscreenWithoutShade = dumpValue(FlowKt.stateIn(new FlowKt__ZipKt$combine$$inlined$unsafeFlow$1(dumpValue, stateIn, new SharedNotificationContainerViewModel$isOnLockscreenWithoutShade$1(null)), coroutineScope, startedEagerly, bool), "isOnLockscreenWithoutShade");
        Flow flow4 = new Flow() { // from class: com.android.systemui.statusbar.notification.stack.ui.viewmodel.SharedNotificationContainerViewModel$special$$inlined$map$8

            /* renamed from: com.android.systemui.statusbar.notification.stack.ui.viewmodel.SharedNotificationContainerViewModel$special$$inlined$map$8$2, reason: invalid class name */
            public final class AnonymousClass2 implements FlowCollector {
                public final /* synthetic */ FlowCollector $this_unsafeFlow;

                /* renamed from: com.android.systemui.statusbar.notification.stack.ui.viewmodel.SharedNotificationContainerViewModel$special$$inlined$map$8$2$1, reason: invalid class name */
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
                        boolean r0 = r6 instanceof com.android.systemui.statusbar.notification.stack.ui.viewmodel.SharedNotificationContainerViewModel$special$$inlined$map$8.AnonymousClass2.AnonymousClass1
                        if (r0 == 0) goto L13
                        r0 = r6
                        com.android.systemui.statusbar.notification.stack.ui.viewmodel.SharedNotificationContainerViewModel$special$$inlined$map$8$2$1 r0 = (com.android.systemui.statusbar.notification.stack.ui.viewmodel.SharedNotificationContainerViewModel$special$$inlined$map$8.AnonymousClass2.AnonymousClass1) r0
                        int r1 = r0.label
                        r2 = -2147483648(0xffffffff80000000, float:-0.0)
                        r3 = r1 & r2
                        if (r3 == 0) goto L13
                        int r1 = r1 - r2
                        r0.label = r1
                        goto L18
                    L13:
                        com.android.systemui.statusbar.notification.stack.ui.viewmodel.SharedNotificationContainerViewModel$special$$inlined$map$8$2$1 r0 = new com.android.systemui.statusbar.notification.stack.ui.viewmodel.SharedNotificationContainerViewModel$special$$inlined$map$8$2$1
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
                        com.android.systemui.keyguard.shared.model.KeyguardState r6 = com.android.systemui.keyguard.shared.model.KeyguardState.GLANCEABLE_HUB
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
                    throw new UnsupportedOperationException("Method not decompiled: com.android.systemui.statusbar.notification.stack.ui.viewmodel.SharedNotificationContainerViewModel$special$$inlined$map$8.AnonymousClass2.emit(java.lang.Object, kotlin.coroutines.Continuation):java.lang.Object");
                }
            }

            @Override // kotlinx.coroutines.flow.Flow
            public final Object collect(FlowCollector flowCollector, Continuation continuation) {
                Object collect = Flow.this.collect(new AnonymousClass2(flowCollector), continuation);
                return collect == CoroutineSingletons.COROUTINE_SUSPENDED ? collect : Unit.INSTANCE;
            }
        };
        BooleanFlowOperators booleanFlowOperators = BooleanFlowOperators.INSTANCE;
        Edge.Companion companion2 = Edge.Companion;
        SceneKey sceneKey = Scenes.Communal;
        Edge.StateToScene create$default = Edge.Companion.create$default(companion2, sceneKey);
        KeyguardState keyguardState3 = KeyguardState.GLANCEABLE_HUB;
        StateFlow dumpValue2 = dumpValue(FlowKt.stateIn(new FlowKt__ZipKt$combine$$inlined$unsafeFlow$1(dumpWhileCollecting(FlowKt.distinctUntilChanged(new FlowKt__ZipKt$combine$$inlined$unsafeFlow$1(flow4, booleanFlowOperators.anyOf(keyguardTransitionInteractor.isInTransition(create$default, Edge.Companion.create$default(companion2, null, keyguardState3, 1)), keyguardTransitionInteractor.isInTransition(new Edge.SceneToState(sceneKey, null), Edge.Companion.create$default(companion2, keyguardState3, null, 2))), new SharedNotificationContainerViewModel$isOnGlanceableHub$2(null))), "isOnGlanceableHub"), stateIn, new SharedNotificationContainerViewModel$isOnGlanceableHubWithoutShade$1(null)), coroutineScope, startedEagerly, bool), "isOnGlanceableHubWithoutShade");
        StateFlow dumpValue3 = dumpValue(FlowKt.stateIn(new FlowKt__ZipKt$combine$$inlined$unsafeFlow$1(keyguardTransitionInteractor.isFinishedInState(KeyguardState.DREAMING), stateIn, new SharedNotificationContainerViewModel$isDreamingWithoutShade$1(null)), coroutineScope, startedEagerly, bool), "isDreamingWithoutShade");
        dumpValue(FlowKt.stateIn(new SafeFlow(new SharedNotificationContainerViewModel$shadeCollapseFadeIn$1(this, null)), coroutineScope, SharingStarted.Companion.WhileSubscribed$default(companion, 3), bool), "shadeCollapseFadeIn");
        Lazy lazy = LazyKt__LazyJVMKt.lazy(new Function0() { // from class: com.android.systemui.statusbar.notification.stack.ui.viewmodel.SharedNotificationContainerViewModel$bounds$2

            /* renamed from: com.android.systemui.statusbar.notification.stack.ui.viewmodel.SharedNotificationContainerViewModel$bounds$2$1, reason: invalid class name */
            final class AnonymousClass1 extends SuspendLambda implements Function2 {
                private /* synthetic */ Object L$0;
                int label;

                public AnonymousClass1(Continuation continuation) {
                    super(2, continuation);
                }

                @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
                public final Continuation create(Object obj, Continuation continuation) {
                    AnonymousClass1 anonymousClass1 = new AnonymousClass1(continuation);
                    anonymousClass1.L$0 = obj;
                    return anonymousClass1;
                }

                @Override // kotlin.jvm.functions.Function2
                public final Object invoke(Object obj, Object obj2) {
                    return ((AnonymousClass1) create((FlowCollector) obj, (Continuation) obj2)).invokeSuspend(Unit.INSTANCE);
                }

                @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
                public final Object invokeSuspend(Object obj) {
                    CoroutineSingletons coroutineSingletons = CoroutineSingletons.COROUTINE_SUSPENDED;
                    int i = this.label;
                    if (i == 0) {
                        ResultKt.throwOnFailure(obj);
                        FlowCollector flowCollector = (FlowCollector) this.L$0;
                        Triple triple = new Triple(new Float(0.0f), Boolean.FALSE, new Float(0.0f));
                        this.label = 1;
                        if (flowCollector.emit(triple, this) == coroutineSingletons) {
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

            /* renamed from: com.android.systemui.statusbar.notification.stack.ui.viewmodel.SharedNotificationContainerViewModel$bounds$2$2, reason: invalid class name */
            final class AnonymousClass2 extends SuspendLambda implements Function5 {
                /* synthetic */ int I$0;
                /* synthetic */ Object L$0;
                /* synthetic */ Object L$1;
                /* synthetic */ boolean Z$0;
                int label;

                public AnonymousClass2(Continuation continuation) {
                    super(5, continuation);
                }

                @Override // kotlin.jvm.functions.Function5
                public final Object invoke(Object obj, Object obj2, Object obj3, Object obj4, Object obj5) {
                    boolean booleanValue = ((Boolean) obj).booleanValue();
                    int intValue = ((Number) obj3).intValue();
                    AnonymousClass2 anonymousClass2 = new AnonymousClass2((Continuation) obj5);
                    anonymousClass2.Z$0 = booleanValue;
                    anonymousClass2.L$0 = (NotificationContainerBounds) obj2;
                    anonymousClass2.I$0 = intValue;
                    anonymousClass2.L$1 = (Triple) obj4;
                    return anonymousClass2.invokeSuspend(Unit.INSTANCE);
                }

                @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
                public final Object invokeSuspend(Object obj) {
                    CoroutineSingletons coroutineSingletons = CoroutineSingletons.COROUTINE_SUSPENDED;
                    if (this.label != 0) {
                        throw new IllegalStateException("call to 'resume' before 'invoke' with coroutine");
                    }
                    ResultKt.throwOnFailure(obj);
                    boolean z = this.Z$0;
                    NotificationContainerBounds notificationContainerBounds = (NotificationContainerBounds) this.L$0;
                    int i = this.I$0;
                    Triple triple = (Triple) this.L$1;
                    float floatValue = ((Number) triple.component1()).floatValue();
                    boolean booleanValue = ((Boolean) triple.component2()).booleanValue();
                    float floatValue2 = ((Number) triple.component3()).floatValue();
                    boolean z2 = false;
                    if (z) {
                        return NotificationContainerBounds.copy$default(notificationContainerBounds, notificationContainerBounds.top - i, false, 6);
                    }
                    if (floatValue2 == 0.0f && !booleanValue) {
                        z2 = true;
                    }
                    return NotificationContainerBounds.copy$default(notificationContainerBounds, floatValue, z2, 2);
                }
            }

            /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
            {
                super(0);
            }

            @Override // kotlin.jvm.functions.Function0
            public final Object invoke() {
                RefactorFlagUtils refactorFlagUtils = RefactorFlagUtils.INSTANCE;
                int i = SceneContainerFlag.$r8$clinit;
                Flags.sceneContainer();
                SharedNotificationContainerViewModel sharedNotificationContainerViewModel = SharedNotificationContainerViewModel.this;
                StateFlow stateFlow2 = sharedNotificationContainerViewModel.isOnLockscreenWithoutShade;
                StateFlow stateFlow3 = (StateFlow) sharedNotificationContainerViewModel.keyguardInteractor.notificationContainerBounds$delegate.getValue();
                SharedNotificationContainerViewModel sharedNotificationContainerViewModel2 = SharedNotificationContainerViewModel.this;
                FlowKt__ZipKt$combine$$inlined$combineUnsafe$FlowKt__ZipKt$2 combine = FlowKt.combine(stateFlow2, stateFlow3, sharedNotificationContainerViewModel2.paddingTopDimen, new FlowKt__EmittersKt$onStart$$inlined$unsafeFlow$1(new AnonymousClass1(null), Utils.Companion.sample(sharedNotificationContainerViewModel2.interactor.topPosition, sharedNotificationContainerViewModel2.keyguardTransitionInteractor.isInTransitionToAnyState, ((ShadeInteractorImpl) sharedNotificationContainerViewModel2.shadeInteractor).baseShadeInteractor.getQsExpansion())), new AnonymousClass2(null));
                CoroutineScope coroutineScope2 = coroutineScope;
                SharingStarted.Companion.getClass();
                return sharedNotificationContainerViewModel.dumpValue(FlowKt.stateIn(combine, coroutineScope2, SharingStarted.Companion.Lazily, new NotificationContainerBounds(0.0f, 0.0f, false, 7, null)), "bounds");
            }
        });
        this.bounds$delegate = lazy;
        dumpWhileCollecting(new FlowKt__EmittersKt$onStart$$inlined$unsafeFlow$1(new SharedNotificationContainerViewModel$alphaForShadeAndQsExpansion$2(null), FlowKt.transformLatest(flow2, new SharedNotificationContainerViewModel$special$$inlined$flatMapLatest$1(null, this))), "alphaForShadeAndQsExpansion");
        dumpWhileCollecting(new SafeFlow(new SharedNotificationContainerViewModel$isTransitioningToHiddenKeyguard$1(this, null)), "isTransitioningToHiddenKeyguard");
        dumpWhileCollecting(FlowKt.distinctUntilChanged(new SafeFlow(new FlowKt__ZipKt$combineTransform$$inlined$combineTransformUnsafe$FlowKt__ZipKt$4(new Flow[]{dumpValue2, dumpValue, dumpValue3, new FlowKt__EmittersKt$onStart$$inlined$unsafeFlow$1(new SharedNotificationContainerViewModel$glanceableHubAlpha$1(null), FlowKt.merge(lockscreenToGlanceableHubTransitionViewModel.notificationAlpha, glanceableHubToLockscreenTransitionViewModel.notificationAlpha))}, null, new SharedNotificationContainerViewModel$glanceableHubAlpha$2(null)))), "glanceableHubAlpha");
        Flags.sceneContainer();
        dumpWhileCollecting(FlowKt.merge(lockscreenToGlanceableHubTransitionViewModel.notificationTranslationX, glanceableHubToLockscreenTransitionViewModel.notificationTranslationX, EmptyFlow.INSTANCE), "translationX");
        Flags.sceneContainer();
        final StateFlow stateFlow2 = (StateFlow) lazy.getValue();
        dumpWhileCollecting(FlowKt.distinctUntilChanged(new Flow() { // from class: com.android.systemui.statusbar.notification.stack.ui.viewmodel.SharedNotificationContainerViewModel$special$$inlined$map$10

            /* renamed from: com.android.systemui.statusbar.notification.stack.ui.viewmodel.SharedNotificationContainerViewModel$special$$inlined$map$10$2, reason: invalid class name */
            public final class AnonymousClass2 implements FlowCollector {
                public final /* synthetic */ FlowCollector $this_unsafeFlow;

                /* renamed from: com.android.systemui.statusbar.notification.stack.ui.viewmodel.SharedNotificationContainerViewModel$special$$inlined$map$10$2$1, reason: invalid class name */
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
                        boolean r0 = r6 instanceof com.android.systemui.statusbar.notification.stack.ui.viewmodel.SharedNotificationContainerViewModel$special$$inlined$map$10.AnonymousClass2.AnonymousClass1
                        if (r0 == 0) goto L13
                        r0 = r6
                        com.android.systemui.statusbar.notification.stack.ui.viewmodel.SharedNotificationContainerViewModel$special$$inlined$map$10$2$1 r0 = (com.android.systemui.statusbar.notification.stack.ui.viewmodel.SharedNotificationContainerViewModel$special$$inlined$map$10.AnonymousClass2.AnonymousClass1) r0
                        int r1 = r0.label
                        r2 = -2147483648(0xffffffff80000000, float:-0.0)
                        r3 = r1 & r2
                        if (r3 == 0) goto L13
                        int r1 = r1 - r2
                        r0.label = r1
                        goto L18
                    L13:
                        com.android.systemui.statusbar.notification.stack.ui.viewmodel.SharedNotificationContainerViewModel$special$$inlined$map$10$2$1 r0 = new com.android.systemui.statusbar.notification.stack.ui.viewmodel.SharedNotificationContainerViewModel$special$$inlined$map$10$2$1
                        r0.<init>(r6)
                    L18:
                        java.lang.Object r6 = r0.result
                        kotlin.coroutines.intrinsics.CoroutineSingletons r1 = kotlin.coroutines.intrinsics.CoroutineSingletons.COROUTINE_SUSPENDED
                        int r2 = r0.label
                        r3 = 1
                        if (r2 == 0) goto L2f
                        if (r2 != r3) goto L27
                        kotlin.ResultKt.throwOnFailure(r6)
                        goto L49
                    L27:
                        java.lang.IllegalStateException r4 = new java.lang.IllegalStateException
                        java.lang.String r5 = "call to 'resume' before 'invoke' with coroutine"
                        r4.<init>(r5)
                        throw r4
                    L2f:
                        kotlin.ResultKt.throwOnFailure(r6)
                        com.android.systemui.common.shared.model.NotificationContainerBounds r5 = (com.android.systemui.common.shared.model.NotificationContainerBounds) r5
                        float r6 = r5.bottom
                        float r5 = r5.top
                        float r6 = r6 - r5
                        java.lang.Float r5 = new java.lang.Float
                        r5.<init>(r6)
                        r0.label = r3
                        kotlinx.coroutines.flow.FlowCollector r4 = r4.$this_unsafeFlow
                        java.lang.Object r4 = r4.emit(r5, r0)
                        if (r4 != r1) goto L49
                        return r1
                    L49:
                        kotlin.Unit r4 = kotlin.Unit.INSTANCE
                        return r4
                    */
                    throw new UnsupportedOperationException("Method not decompiled: com.android.systemui.statusbar.notification.stack.ui.viewmodel.SharedNotificationContainerViewModel$special$$inlined$map$10.AnonymousClass2.emit(java.lang.Object, kotlin.coroutines.Continuation):java.lang.Object");
                }
            }

            @Override // kotlinx.coroutines.flow.Flow
            public final Object collect(FlowCollector flowCollector, Continuation continuation) {
                Object collect = Flow.this.collect(new AnonymousClass2(flowCollector), continuation);
                return collect == CoroutineSingletons.COROUTINE_SUSPENDED ? collect : Unit.INSTANCE;
            }
        }), "availableHeight");
    }

    public static final Flow[] access$toFlowArray(SharedNotificationContainerViewModel sharedNotificationContainerViewModel, Set set, Function1 function1) {
        sharedNotificationContainerViewModel.getClass();
        Set set2 = set;
        ArrayList arrayList = new ArrayList(CollectionsKt__IterablesKt.collectionSizeOrDefault(set2, 10));
        Iterator it = set2.iterator();
        while (it.hasNext()) {
            arrayList.add((Flow) function1.invoke((KeyguardState) it.next()));
        }
        return (Flow[]) arrayList.toArray(new Flow[0]);
    }

    public static /* synthetic */ void getPaddingTopDimen$annotations() {
    }
}
