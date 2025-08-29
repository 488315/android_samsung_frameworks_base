package com.android.systemui.statusbar.notification.stack.ui.viewmodel;

import android.content.Context;
import android.util.MathUtils;
import androidx.compose.animation.FlingCalculator$FlingInfo$$ExternalSyntheticOutline0;
import com.android.compose.animation.scene.OverlayKey;
import com.android.compose.animation.scene.SceneKey;
import com.android.compose.modifiers.AnimatedBackgroundKt$$ExternalSyntheticLambda0;
import com.android.systemui.bouncer.domain.interactor.BouncerInteractor;
import com.android.systemui.common.shared.model.NotificationContainerBounds;
import com.android.systemui.common.ui.domain.interactor.ConfigurationInteractor;
import com.android.systemui.communal.domain.interactor.CommunalSceneInteractor;
import com.android.systemui.dump.DumpManager;
import com.android.systemui.flags.RefactorFlagUtils;
import com.android.systemui.keyguard.domain.interactor.KeyguardInteractor;
import com.android.systemui.keyguard.domain.interactor.KeyguardTransitionInteractor;
import com.android.systemui.keyguard.shared.model.BurnInModel;
import com.android.systemui.keyguard.shared.model.Edge;
import com.android.systemui.keyguard.shared.model.KeyguardState;
import com.android.systemui.keyguard.shared.model.StatusBarState;
import com.android.systemui.keyguard.ui.KeyguardTransitionAnimationFlow;
import com.android.systemui.keyguard.ui.KeyguardTransitionAnimationFlow$FlowBuilder$sharedFlow74qcysc$$inlined$mapNotNull$1;
import com.android.systemui.keyguard.ui.transitions.PrimaryBouncerTransition;
import com.android.systemui.keyguard.ui.viewmodel.AlternateBouncerToGoneTransitionViewModel;
import com.android.systemui.keyguard.ui.viewmodel.AlternateBouncerToPrimaryBouncerTransitionViewModel;
import com.android.systemui.keyguard.ui.viewmodel.AodBurnInViewModel;
import com.android.systemui.keyguard.ui.viewmodel.AodToGlanceableHubTransitionViewModel;
import com.android.systemui.keyguard.ui.viewmodel.AodToGoneTransitionViewModel;
import com.android.systemui.keyguard.ui.viewmodel.AodToGoneTransitionViewModel$$ExternalSyntheticLambda0;
import com.android.systemui.keyguard.ui.viewmodel.AodToGoneTransitionViewModel$$ExternalSyntheticLambda1;
import com.android.systemui.keyguard.ui.viewmodel.AodToLockscreenTransitionViewModel;
import com.android.systemui.keyguard.ui.viewmodel.AodToOccludedTransitionViewModel;
import com.android.systemui.keyguard.ui.viewmodel.AodToPrimaryBouncerTransitionViewModel;
import com.android.systemui.keyguard.ui.viewmodel.DozingToGlanceableHubTransitionViewModel;
import com.android.systemui.keyguard.ui.viewmodel.DozingToLockscreenTransitionViewModel;
import com.android.systemui.keyguard.ui.viewmodel.DozingToOccludedTransitionViewModel;
import com.android.systemui.keyguard.ui.viewmodel.DozingToPrimaryBouncerTransitionViewModel;
import com.android.systemui.keyguard.ui.viewmodel.DreamingToLockscreenTransitionViewModel;
import com.android.systemui.keyguard.ui.viewmodel.GlanceableHubToAodTransitionViewModel;
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
import com.android.systemui.keyguard.ui.viewmodel.OccludedToGoneTransitionViewModel$$ExternalSyntheticLambda2;
import com.android.systemui.keyguard.ui.viewmodel.OccludedToLockscreenTransitionViewModel;
import com.android.systemui.keyguard.ui.viewmodel.OffToLockscreenTransitionViewModel;
import com.android.systemui.keyguard.ui.viewmodel.PrimaryBouncerToGoneTransitionViewModel;
import com.android.systemui.keyguard.ui.viewmodel.PrimaryBouncerToLockscreenTransitionViewModel;
import com.android.systemui.keyguard.ui.viewmodel.ViewStateAccessor;
import com.android.systemui.scene.shared.flag.SceneContainerFlag;
import com.android.systemui.scene.shared.model.Overlays;
import com.android.systemui.scene.shared.model.Scenes;
import com.android.systemui.shade.domain.interactor.ShadeInteractor;
import com.android.systemui.shade.domain.interactor.ShadeInteractorImpl;
import com.android.systemui.shade.domain.interactor.ShadeModeInteractor;
import com.android.systemui.statusbar.StatusBarStateControllerImpl;
import com.android.systemui.statusbar.notification.stack.domain.interactor.NotificationStackAppearanceInteractor;
import com.android.systemui.statusbar.notification.stack.domain.interactor.SharedNotificationContainerInteractor;
import com.android.systemui.statusbar.notification.stack.ui.viewbinder.SharedNotificationContainerBinder$$ExternalSyntheticLambda0;
import com.android.systemui.statusbar.notification.stack.ui.viewmodel.SharedNotificationContainerViewModel;
import com.android.systemui.unfold.domain.interactor.UnfoldTransitionInteractor;
import com.android.systemui.util.SecQsUiDisplayModeInteractor;
import com.android.systemui.util.kotlin.BooleanFlowOperators;
import com.android.systemui.util.kotlin.FlowDumperImpl;
import com.android.systemui.utils.coroutines.flow.FlowConflatedKt;
import com.samsung.android.knox.custom.IKnoxCustomManager;
import defpackage.ReorderTile$$ExternalSyntheticOutline0;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.Set;
import kotlin.Lazy;
import kotlin.LazyKt__LazyJVMKt;
import kotlin.ResultKt;
import kotlin.Unit;
import kotlin.collections.CollectionsKt__IterablesKt;
import kotlin.coroutines.Continuation;
import kotlin.coroutines.intrinsics.CoroutineSingletons;
import kotlin.coroutines.jvm.internal.ContinuationImpl;
import kotlin.jvm.functions.Function0;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.Intrinsics;
import kotlin.jvm.internal.Ref$BooleanRef;
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
import kotlinx.coroutines.flow.StartedEagerly;
import kotlinx.coroutines.flow.StateFlow;
import kotlinx.coroutines.flow.internal.ChannelLimitedFlowMerge;

/* loaded from: classes3.dex */
public final class SharedNotificationContainerViewModel extends FlowDumperImpl {
    public final Flow alphaForShadeAndQsExpansion;
    public final AlternateBouncerToGoneTransitionViewModel alternateBouncerToGoneTransitionViewModel;
    public final AlternateBouncerToPrimaryBouncerTransitionViewModel alternateBouncerToPrimaryBouncerTransitionViewModel;
    public final AodToGlanceableHubTransitionViewModel aodToGlanceableHubTransitionViewModel;
    public final AodToGoneTransitionViewModel aodToGoneTransitionViewModel;
    public final AodToLockscreenTransitionViewModel aodToLockscreenTransitionViewModel;
    public final AodToOccludedTransitionViewModel aodToOccludedTransitionViewModel;
    public final AodToPrimaryBouncerTransitionViewModel aodToPrimaryBouncerTransitionViewModel;
    public final Flow availableHeight;
    public final Flow blurRadius;
    public final BouncerInteractor bouncerInteractor;
    public final Lazy bounds$delegate;
    public final CommunalSceneInteractor communalSceneInteractor;
    public final Flow configurationBasedDimensions;
    public final Context context;
    public final DozingToLockscreenTransitionViewModel dozingToLockscreenTransitionViewModel;
    public final DozingToOccludedTransitionViewModel dozingToOccludedTransitionViewModel;
    public final DozingToPrimaryBouncerTransitionViewModel dozingToPrimaryBouncerTransitionViewModel;
    public final DreamingToLockscreenTransitionViewModel dreamingToLockscreenTransitionViewModel;
    public final Flow glanceableHubAlpha;
    public final GlanceableHubToAodTransitionViewModel glanceableHubToAodTransitionViewModel;
    public final GlanceableHubToLockscreenTransitionViewModel glanceableHubToLockscreenTransitionViewModel;
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
    public final KeyguardInteractor keyguardInteractor;
    public final KeyguardTransitionInteractor keyguardTransitionInteractor;
    public final dagger.Lazy largeScreenHeaderHelperLazy;
    public final LockscreenToDreamingTransitionViewModel lockscreenToDreamingTransitionViewModel;
    public final LockscreenToGlanceableHubTransitionViewModel lockscreenToGlanceableHubTransitionViewModel;
    public final LockscreenToGoneTransitionViewModel lockscreenToGoneTransitionViewModel;
    public final LockscreenToOccludedTransitionViewModel lockscreenToOccludedTransitionViewModel;
    public final LockscreenToPrimaryBouncerTransitionViewModel lockscreenToPrimaryBouncerTransitionViewModel;
    public final OccludedToAodTransitionViewModel occludedToAodTransitionViewModel;
    public final OccludedToGoneTransitionViewModel occludedToGoneTransitionViewModel;
    public final OccludedToLockscreenTransitionViewModel occludedToLockscreenTransitionViewModel;
    public final OffToLockscreenTransitionViewModel offToLockscreenTransitionViewModel;
    public final Flow paddingTopDimen;
    public final ReadonlyStateFlow panelAlpha;
    public final PrimaryBouncerToGoneTransitionViewModel primaryBouncerToGoneTransitionViewModel;
    public final PrimaryBouncerToLockscreenTransitionViewModel primaryBouncerToLockscreenTransitionViewModel;
    public final Set primaryBouncerTransitions;
    public final StateFlow shadeCollapseFadeIn;
    public final ShadeInteractor shadeInteractor;
    public final Flow translationX;
    public final Flow translationY;

    public final class ConfigurationBasedDimensions {
        public final HorizontalPosition horizontalPosition;
        public final int marginBottom;
        public final int marginEnd;
        public final int marginStart;
        public final int marginTop;
        public final int panelWidth;
        public final float transitionX;
        public final SecQsUiDisplayModeInteractor.UiDisplayMode uiDisplayMode;

        public ConfigurationBasedDimensions(HorizontalPosition horizontalPosition, int i, int i2, int i3, int i4, int i5, float f, SecQsUiDisplayModeInteractor.UiDisplayMode uiDisplayMode) {
            this.horizontalPosition = horizontalPosition;
            this.marginStart = i;
            this.marginTop = i2;
            this.marginEnd = i3;
            this.marginBottom = i4;
            this.panelWidth = i5;
            this.transitionX = f;
            this.uiDisplayMode = uiDisplayMode;
        }

        public final boolean equals(Object obj) {
            if (this == obj) {
                return true;
            }
            if (!(obj instanceof ConfigurationBasedDimensions)) {
                return false;
            }
            ConfigurationBasedDimensions configurationBasedDimensions = (ConfigurationBasedDimensions) obj;
            return Intrinsics.areEqual(this.horizontalPosition, configurationBasedDimensions.horizontalPosition) && this.marginStart == configurationBasedDimensions.marginStart && this.marginTop == configurationBasedDimensions.marginTop && this.marginEnd == configurationBasedDimensions.marginEnd && this.marginBottom == configurationBasedDimensions.marginBottom && this.panelWidth == configurationBasedDimensions.panelWidth && Float.compare(this.transitionX, configurationBasedDimensions.transitionX) == 0 && this.uiDisplayMode == configurationBasedDimensions.uiDisplayMode;
        }

        public final int hashCode() {
            return this.uiDisplayMode.hashCode() + FlingCalculator$FlingInfo$$ExternalSyntheticOutline0.m(this.transitionX, ReorderTile$$ExternalSyntheticOutline0.m(this.panelWidth, ReorderTile$$ExternalSyntheticOutline0.m(this.marginBottom, ReorderTile$$ExternalSyntheticOutline0.m(this.marginEnd, ReorderTile$$ExternalSyntheticOutline0.m(this.marginTop, ReorderTile$$ExternalSyntheticOutline0.m(this.marginStart, this.horizontalPosition.hashCode() * 31, 31), 31), 31), 31), 31), 31);
        }

        public final String toString() {
            return "ConfigurationBasedDimensions(horizontalPosition=" + this.horizontalPosition + ", marginStart=" + this.marginStart + ", marginTop=" + this.marginTop + ", marginEnd=" + this.marginEnd + ", marginBottom=" + this.marginBottom + ", panelWidth=" + this.panelWidth + ", transitionX=" + this.transitionX + ", uiDisplayMode=" + this.uiDisplayMode + ")";
        }
    }

    public interface HorizontalPosition {

        public final class EdgeToEdge implements HorizontalPosition {
            public static final EdgeToEdge INSTANCE = new EdgeToEdge();

            private EdgeToEdge() {
            }

            public final boolean equals(Object obj) {
                return this == obj || (obj instanceof EdgeToEdge);
            }

            public final int hashCode() {
                return 174599778;
            }

            public final String toString() {
                return "EdgeToEdge";
            }
        }

        public final class MiddleToEdge implements HorizontalPosition {
            public final float ratio;

            public MiddleToEdge() {
                this(0.0f, 1, null);
            }

            public final boolean equals(Object obj) {
                if (this == obj) {
                    return true;
                }
                return (obj instanceof MiddleToEdge) && Float.compare(this.ratio, ((MiddleToEdge) obj).ratio) == 0;
            }

            public final int hashCode() {
                return Float.hashCode(this.ratio);
            }

            public final String toString() {
                return "MiddleToEdge(ratio=" + this.ratio + ")";
            }

            public MiddleToEdge(float f) {
                this.ratio = f;
            }

            public /* synthetic */ MiddleToEdge(float f, int i, DefaultConstructorMarker defaultConstructorMarker) {
                this((i & 1) != 0 ? 0.5f : f);
            }
        }
    }

    public final class LockscreenDisplayConfig {
        public final boolean isOnLockscreen;
        public final int maxNotifications;

        public LockscreenDisplayConfig(boolean z, int i) {
            this.isOnLockscreen = z;
            this.maxNotifications = i;
        }

        public final boolean equals(Object obj) {
            if (this == obj) {
                return true;
            }
            if (!(obj instanceof LockscreenDisplayConfig)) {
                return false;
            }
            LockscreenDisplayConfig lockscreenDisplayConfig = (LockscreenDisplayConfig) obj;
            return this.isOnLockscreen == lockscreenDisplayConfig.isOnLockscreen && this.maxNotifications == lockscreenDisplayConfig.maxNotifications;
        }

        public final int hashCode() {
            return Integer.hashCode(this.maxNotifications) + (Boolean.hashCode(this.isOnLockscreen) * 31);
        }

        public final String toString() {
            return "LockscreenDisplayConfig(isOnLockscreen=" + this.isOnLockscreen + ", maxNotifications=" + this.maxNotifications + ")";
        }
    }

    public SharedNotificationContainerViewModel(SharedNotificationContainerInteractor sharedNotificationContainerInteractor, DumpManager dumpManager, CoroutineScope coroutineScope, Context context, ConfigurationInteractor configurationInteractor, KeyguardInteractor keyguardInteractor, KeyguardTransitionInteractor keyguardTransitionInteractor, ShadeInteractor shadeInteractor, BouncerInteractor bouncerInteractor, ShadeModeInteractor shadeModeInteractor, NotificationStackAppearanceInteractor notificationStackAppearanceInteractor, AlternateBouncerToGoneTransitionViewModel alternateBouncerToGoneTransitionViewModel, AlternateBouncerToPrimaryBouncerTransitionViewModel alternateBouncerToPrimaryBouncerTransitionViewModel, AodToGoneTransitionViewModel aodToGoneTransitionViewModel, AodToLockscreenTransitionViewModel aodToLockscreenTransitionViewModel, AodToOccludedTransitionViewModel aodToOccludedTransitionViewModel, AodToGlanceableHubTransitionViewModel aodToGlanceableHubTransitionViewModel, AodToPrimaryBouncerTransitionViewModel aodToPrimaryBouncerTransitionViewModel, DozingToGlanceableHubTransitionViewModel dozingToGlanceableHubTransitionViewModel, DozingToLockscreenTransitionViewModel dozingToLockscreenTransitionViewModel, DozingToOccludedTransitionViewModel dozingToOccludedTransitionViewModel, DozingToPrimaryBouncerTransitionViewModel dozingToPrimaryBouncerTransitionViewModel, DreamingToLockscreenTransitionViewModel dreamingToLockscreenTransitionViewModel, GlanceableHubToLockscreenTransitionViewModel glanceableHubToLockscreenTransitionViewModel, GlanceableHubToAodTransitionViewModel glanceableHubToAodTransitionViewModel, GoneToAodTransitionViewModel goneToAodTransitionViewModel, GoneToDozingTransitionViewModel goneToDozingTransitionViewModel, GoneToDreamingTransitionViewModel goneToDreamingTransitionViewModel, GoneToLockscreenTransitionViewModel goneToLockscreenTransitionViewModel, LockscreenToDreamingTransitionViewModel lockscreenToDreamingTransitionViewModel, LockscreenToGlanceableHubTransitionViewModel lockscreenToGlanceableHubTransitionViewModel, LockscreenToGoneTransitionViewModel lockscreenToGoneTransitionViewModel, LockscreenToPrimaryBouncerTransitionViewModel lockscreenToPrimaryBouncerTransitionViewModel, LockscreenToOccludedTransitionViewModel lockscreenToOccludedTransitionViewModel, OccludedToAodTransitionViewModel occludedToAodTransitionViewModel, OccludedToGoneTransitionViewModel occludedToGoneTransitionViewModel, OccludedToLockscreenTransitionViewModel occludedToLockscreenTransitionViewModel, OffToLockscreenTransitionViewModel offToLockscreenTransitionViewModel, PrimaryBouncerToGoneTransitionViewModel primaryBouncerToGoneTransitionViewModel, PrimaryBouncerToLockscreenTransitionViewModel primaryBouncerToLockscreenTransitionViewModel, Set<PrimaryBouncerTransition> set, AodBurnInViewModel aodBurnInViewModel, CommunalSceneInteractor communalSceneInteractor, dagger.Lazy lazy, dagger.Lazy lazy2, UnfoldTransitionInteractor unfoldTransitionInteractor) {
        super(dumpManager, null, 2, null);
        this.interactor = sharedNotificationContainerInteractor;
        this.context = context;
        this.keyguardInteractor = keyguardInteractor;
        this.keyguardTransitionInteractor = keyguardTransitionInteractor;
        this.shadeInteractor = shadeInteractor;
        this.bouncerInteractor = bouncerInteractor;
        this.alternateBouncerToGoneTransitionViewModel = alternateBouncerToGoneTransitionViewModel;
        this.alternateBouncerToPrimaryBouncerTransitionViewModel = alternateBouncerToPrimaryBouncerTransitionViewModel;
        this.aodToGoneTransitionViewModel = aodToGoneTransitionViewModel;
        this.aodToLockscreenTransitionViewModel = aodToLockscreenTransitionViewModel;
        this.aodToOccludedTransitionViewModel = aodToOccludedTransitionViewModel;
        this.aodToGlanceableHubTransitionViewModel = aodToGlanceableHubTransitionViewModel;
        this.aodToPrimaryBouncerTransitionViewModel = aodToPrimaryBouncerTransitionViewModel;
        this.dozingToLockscreenTransitionViewModel = dozingToLockscreenTransitionViewModel;
        this.dozingToOccludedTransitionViewModel = dozingToOccludedTransitionViewModel;
        this.dozingToPrimaryBouncerTransitionViewModel = dozingToPrimaryBouncerTransitionViewModel;
        this.dreamingToLockscreenTransitionViewModel = dreamingToLockscreenTransitionViewModel;
        this.glanceableHubToLockscreenTransitionViewModel = glanceableHubToLockscreenTransitionViewModel;
        this.glanceableHubToAodTransitionViewModel = glanceableHubToAodTransitionViewModel;
        this.goneToAodTransitionViewModel = goneToAodTransitionViewModel;
        this.goneToDozingTransitionViewModel = goneToDozingTransitionViewModel;
        this.goneToDreamingTransitionViewModel = goneToDreamingTransitionViewModel;
        this.goneToLockscreenTransitionViewModel = goneToLockscreenTransitionViewModel;
        this.lockscreenToDreamingTransitionViewModel = lockscreenToDreamingTransitionViewModel;
        this.lockscreenToGlanceableHubTransitionViewModel = lockscreenToGlanceableHubTransitionViewModel;
        this.lockscreenToGoneTransitionViewModel = lockscreenToGoneTransitionViewModel;
        this.lockscreenToPrimaryBouncerTransitionViewModel = lockscreenToPrimaryBouncerTransitionViewModel;
        this.lockscreenToOccludedTransitionViewModel = lockscreenToOccludedTransitionViewModel;
        this.occludedToAodTransitionViewModel = occludedToAodTransitionViewModel;
        this.occludedToGoneTransitionViewModel = occludedToGoneTransitionViewModel;
        this.occludedToLockscreenTransitionViewModel = occludedToLockscreenTransitionViewModel;
        this.offToLockscreenTransitionViewModel = offToLockscreenTransitionViewModel;
        this.primaryBouncerToGoneTransitionViewModel = primaryBouncerToGoneTransitionViewModel;
        this.primaryBouncerToLockscreenTransitionViewModel = primaryBouncerToLockscreenTransitionViewModel;
        this.primaryBouncerTransitions = set;
        this.communalSceneInteractor = communalSceneInteractor;
        this.largeScreenHeaderHelperLazy = lazy2;
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
                        Boolean boolValueOf = Boolean.valueOf(((Number) obj).floatValue() > 0.0f);
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
                Object objCollect = shadeExpansion.collect(new AnonymousClass2(flowCollector), continuation);
                return objCollect == CoroutineSingletons.COROUTINE_SUSPENDED ? objCollect : Unit.INSTANCE;
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
                        Boolean boolValueOf = Boolean.valueOf(((Number) obj).floatValue() > 0.0f);
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
                Object objCollect = qsExpansion.collect(new AnonymousClass2(flowCollector), continuation);
                return objCollect == CoroutineSingletons.COROUTINE_SUSPENDED ? objCollect : Unit.INSTANCE;
            }
        }, new SharedNotificationContainerViewModel$isAnyExpanded$3(null));
        SharingStarted.Companion companion = SharingStarted.Companion;
        companion.getClass();
        StartedEagerly startedEagerly = SharingStarted.Companion.Eagerly;
        Boolean bool = Boolean.FALSE;
        ReadonlyStateFlow readonlyStateFlowStateIn = FlowKt.stateIn(flowKt__ZipKt$combine$$inlined$unsafeFlow$1, coroutineScope, startedEagerly, bool);
        this.isAnyExpanded = readonlyStateFlowStateIn;
        final ReadonlyStateFlow readonlyStateFlow = keyguardInteractor.statusBarState;
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
                        Boolean boolValueOf = Boolean.valueOf(((StatusBarState) obj) == StatusBarState.SHADE_LOCKED);
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
                Object objCollect = readonlyStateFlow.collect(new AnonymousClass2(flowCollector), continuation);
                return objCollect == CoroutineSingletons.COROUTINE_SUSPENDED ? objCollect : Unit.INSTANCE;
            }
        }, readonlyStateFlowStateIn, new SharedNotificationContainerViewModel$isShadeLocked$2(null)), coroutineScope, startedEagerly, bool), "isShadeLocked");
        sharedNotificationContainerInteractor.getClass();
        RefactorFlagUtils refactorFlagUtils = RefactorFlagUtils.INSTANCE;
        int i = SceneContainerFlag.$r8$clinit;
        final Flow flow2 = sharedNotificationContainerInteractor.configurationBasedDimensions;
        this.paddingTopDimen = dumpWhileCollecting(FlowKt.distinctUntilChanged(new Flow() { // from class: com.android.systemui.statusbar.notification.stack.ui.viewmodel.SharedNotificationContainerViewModel$special$$inlined$map$5

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
                        SharedNotificationContainerInteractor.ConfigurationBasedDimensions configurationBasedDimensions = (SharedNotificationContainerInteractor.ConfigurationBasedDimensions) obj;
                        Integer num = new Integer(configurationBasedDimensions.useLargeScreenHeader ? configurationBasedDimensions.marginTopLargeScreen : configurationBasedDimensions.marginTop);
                        anonymousClass1.label = 1;
                        if (this.$this_unsafeFlow.emit(num, anonymousClass1) == coroutineSingletons) {
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
                Object objCollect = flow2.collect(new AnonymousClass2(flowCollector), continuation);
                return objCollect == CoroutineSingletons.COROUTINE_SUSPENDED ? objCollect : Unit.INSTANCE;
            }
        }), "paddingTopDimen");
        this.configurationBasedDimensions = dumpWhileCollecting(FlowKt.distinctUntilChanged(new Flow() { // from class: com.android.systemui.statusbar.notification.stack.ui.viewmodel.SharedNotificationContainerViewModel$special$$inlined$map$6

            /* renamed from: com.android.systemui.statusbar.notification.stack.ui.viewmodel.SharedNotificationContainerViewModel$special$$inlined$map$6$2, reason: invalid class name */
            public final class AnonymousClass2 implements FlowCollector {
                public final /* synthetic */ FlowCollector $this_unsafeFlow;

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
                    int i3 = 1;
                    if (i2 == 0) {
                        ResultKt.throwOnFailure(obj2);
                        SharedNotificationContainerInteractor.ConfigurationBasedDimensions configurationBasedDimensions = (SharedNotificationContainerInteractor.ConfigurationBasedDimensions) obj;
                        SharedNotificationContainerViewModel.ConfigurationBasedDimensions configurationBasedDimensions2 = new SharedNotificationContainerViewModel.ConfigurationBasedDimensions(configurationBasedDimensions.useSplitShade ? new SharedNotificationContainerViewModel.HorizontalPosition.MiddleToEdge(0.0f, i3, null) : SharedNotificationContainerViewModel.HorizontalPosition.EdgeToEdge.INSTANCE, configurationBasedDimensions.useSplitShade ? 0 : configurationBasedDimensions.marginHorizontal, configurationBasedDimensions.useLargeScreenHeader ? configurationBasedDimensions.marginTopLargeScreen : configurationBasedDimensions.marginTop, configurationBasedDimensions.marginHorizontal, configurationBasedDimensions.marginBottom, configurationBasedDimensions.panelWidth, configurationBasedDimensions.transitionX, configurationBasedDimensions.uiDisplayMode);
                        anonymousClass1.label = 1;
                        if (this.$this_unsafeFlow.emit(configurationBasedDimensions2, anonymousClass1) == coroutineSingletons) {
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
                Object objCollect = flow2.collect(new AnonymousClass2(flowCollector), continuation);
                return objCollect == CoroutineSingletons.COROUTINE_SUSPENDED ? objCollect : Unit.INSTANCE;
            }
        }), "configurationBasedDimensions " + new SimpleDateFormat("yyyy-MM-dd HH:mm:ss.SSS").format(Long.valueOf(System.currentTimeMillis())));
        BooleanFlowOperators booleanFlowOperators = BooleanFlowOperators.INSTANCE;
        final MutableSharedFlow transitionValueFlow = keyguardTransitionInteractor.getTransitionValueFlow(KeyguardState.ALTERNATE_BOUNCER);
        Flow flow3 = new Flow() { // from class: com.android.systemui.statusbar.notification.stack.ui.viewmodel.SharedNotificationContainerViewModel$special$$inlined$map$7

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
                        Boolean boolValueOf = Boolean.valueOf(((Number) obj).floatValue() > 0.0f);
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
        };
        OverlayKey overlayKey = Overlays.Bouncer;
        final MutableSharedFlow transitionValueFlow2 = keyguardTransitionInteractor.getTransitionValueFlow(KeyguardState.PRIMARY_BOUNCER);
        Flow flowAnyOf = booleanFlowOperators.anyOf(flow3, new Flow() { // from class: com.android.systemui.statusbar.notification.stack.ui.viewmodel.SharedNotificationContainerViewModel$special$$inlined$map$8

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
                        Boolean boolValueOf = Boolean.valueOf(((Number) obj).floatValue() > 0.0f);
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
                Object objCollect = transitionValueFlow2.collect(new AnonymousClass2(flowCollector), continuation);
                return objCollect == CoroutineSingletons.COROUTINE_SUSPENDED ? objCollect : Unit.INSTANCE;
            }
        });
        final MutableSharedFlow transitionValueFlow3 = keyguardTransitionInteractor.getTransitionValueFlow(KeyguardState.AOD);
        Flow flow4 = new Flow() { // from class: com.android.systemui.statusbar.notification.stack.ui.viewmodel.SharedNotificationContainerViewModel$special$$inlined$map$12

            /* renamed from: com.android.systemui.statusbar.notification.stack.ui.viewmodel.SharedNotificationContainerViewModel$special$$inlined$map$12$2, reason: invalid class name */
            public final class AnonymousClass2 implements FlowCollector {
                public final /* synthetic */ FlowCollector $this_unsafeFlow;

                /* renamed from: com.android.systemui.statusbar.notification.stack.ui.viewmodel.SharedNotificationContainerViewModel$special$$inlined$map$12$2$1, reason: invalid class name */
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
                        Boolean boolValueOf = Boolean.valueOf(((Number) obj).floatValue() > 0.0f);
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
                Object objCollect = transitionValueFlow3.collect(new AnonymousClass2(flowCollector), continuation);
                return objCollect == CoroutineSingletons.COROUTINE_SUSPENDED ? objCollect : Unit.INSTANCE;
            }
        };
        final MutableSharedFlow transitionValueFlow4 = keyguardTransitionInteractor.getTransitionValueFlow(KeyguardState.DOZING);
        Flow flow5 = new Flow() { // from class: com.android.systemui.statusbar.notification.stack.ui.viewmodel.SharedNotificationContainerViewModel$special$$inlined$map$13

            /* renamed from: com.android.systemui.statusbar.notification.stack.ui.viewmodel.SharedNotificationContainerViewModel$special$$inlined$map$13$2, reason: invalid class name */
            public final class AnonymousClass2 implements FlowCollector {
                public final /* synthetic */ FlowCollector $this_unsafeFlow;

                /* renamed from: com.android.systemui.statusbar.notification.stack.ui.viewmodel.SharedNotificationContainerViewModel$special$$inlined$map$13$2$1, reason: invalid class name */
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
                        Boolean boolValueOf = Boolean.valueOf(((Number) obj).floatValue() > 0.0f);
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
                Object objCollect = transitionValueFlow4.collect(new AnonymousClass2(flowCollector), continuation);
                return objCollect == CoroutineSingletons.COROUTINE_SUSPENDED ? objCollect : Unit.INSTANCE;
            }
        };
        final MutableSharedFlow transitionValueFlow5 = keyguardTransitionInteractor.getTransitionValueFlow(KeyguardState.LOCKSCREEN);
        StateFlow stateFlowDumpValue = dumpValue(FlowKt.stateIn(booleanFlowOperators.anyOf(flow4, flow5, new Flow() { // from class: com.android.systemui.statusbar.notification.stack.ui.viewmodel.SharedNotificationContainerViewModel$special$$inlined$map$14

            /* renamed from: com.android.systemui.statusbar.notification.stack.ui.viewmodel.SharedNotificationContainerViewModel$special$$inlined$map$14$2, reason: invalid class name */
            public final class AnonymousClass2 implements FlowCollector {
                public final /* synthetic */ FlowCollector $this_unsafeFlow;

                /* renamed from: com.android.systemui.statusbar.notification.stack.ui.viewmodel.SharedNotificationContainerViewModel$special$$inlined$map$14$2$1, reason: invalid class name */
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
                        Boolean boolValueOf = Boolean.valueOf(((Number) obj).floatValue() > 0.0f);
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
                Object objCollect = transitionValueFlow5.collect(new AnonymousClass2(flowCollector), continuation);
                return objCollect == CoroutineSingletons.COROUTINE_SUSPENDED ? objCollect : Unit.INSTANCE;
            }
        }, flowAnyOf), coroutineScope, startedEagerly, bool), "isOnLockscreen");
        this.isOnLockscreen = stateFlowDumpValue;
        this.isOnLockscreenWithoutShade = dumpValue(FlowKt.stateIn(new FlowKt__ZipKt$combine$$inlined$unsafeFlow$1(stateFlowDumpValue, readonlyStateFlowStateIn, new SharedNotificationContainerViewModel$isOnLockscreenWithoutShade$1(null)), coroutineScope, startedEagerly, bool), "isOnLockscreenWithoutShade");
        final Flow flowConflatedCallbackFlow = FlowConflatedKt.conflatedCallbackFlow(new SharedNotificationContainerViewModel$aboutToTransitionToHub$1(this, null));
        Flow flow6 = new Flow() { // from class: com.android.systemui.statusbar.notification.stack.ui.viewmodel.SharedNotificationContainerViewModel$special$$inlined$map$15

            /* renamed from: com.android.systemui.statusbar.notification.stack.ui.viewmodel.SharedNotificationContainerViewModel$special$$inlined$map$15$2, reason: invalid class name */
            public final class AnonymousClass2 implements FlowCollector {
                public final /* synthetic */ FlowCollector $this_unsafeFlow;

                /* renamed from: com.android.systemui.statusbar.notification.stack.ui.viewmodel.SharedNotificationContainerViewModel$special$$inlined$map$15$2$1, reason: invalid class name */
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
                Object objCollect = flowConflatedCallbackFlow.collect(new AnonymousClass2(flowCollector), continuation);
                return objCollect == CoroutineSingletons.COROUTINE_SUSPENDED ? objCollect : Unit.INSTANCE;
            }
        };
        SceneKey sceneKey = Scenes.Communal;
        KeyguardState keyguardState = KeyguardState.GLANCEABLE_HUB;
        Flow flowIsFinishedIn = keyguardTransitionInteractor.isFinishedIn(keyguardState);
        Edge.Companion companion2 = Edge.Companion;
        this.isOnGlanceableHubWithoutShade = dumpValue(FlowKt.stateIn(new FlowKt__ZipKt$combine$$inlined$unsafeFlow$1(dumpWhileCollecting(FlowKt.distinctUntilChanged(FlowKt.merge(flow6, booleanFlowOperators.anyOf(flowIsFinishedIn, keyguardTransitionInteractor.isInTransition(Edge.Companion.create$default(companion2, sceneKey), Edge.Companion.create$default(companion2, null, keyguardState, 1)), keyguardTransitionInteractor.isInTransition(new Edge.ContentToState(sceneKey, null), Edge.Companion.create$default(companion2, keyguardState, null, 2))))), "isOnGlanceableHub"), readonlyStateFlowStateIn, new SharedNotificationContainerViewModel$isOnGlanceableHubWithoutShade$1(null)), coroutineScope, startedEagerly, bool), "isOnGlanceableHubWithoutShade");
        this.isDreamingWithoutShade = dumpValue(FlowKt.stateIn(new FlowKt__ZipKt$combine$$inlined$unsafeFlow$1(keyguardTransitionInteractor.isFinishedIn$1(KeyguardState.DREAMING), readonlyStateFlowStateIn, new SharedNotificationContainerViewModel$isDreamingWithoutShade$1(null)), coroutineScope, startedEagerly, bool), "isDreamingWithoutShade");
        this.shadeCollapseFadeIn = dumpValue(FlowKt.stateIn(new SafeFlow(new SharedNotificationContainerViewModel$shadeCollapseFadeIn$1(this, null)), coroutineScope, SharingStarted.Companion.WhileSubscribed$default(companion, 3), bool), "shadeCollapseFadeIn");
        this.bounds$delegate = LazyKt__LazyJVMKt.lazy(new SharedNotificationContainerViewModel$$ExternalSyntheticLambda0(this, coroutineScope, 0));
        this.alphaForShadeAndQsExpansion = dumpWhileCollecting(new FlowKt__EmittersKt$onStart$$inlined$unsafeFlow$1(new SharedNotificationContainerViewModel$alphaForShadeAndQsExpansion$3(null), FlowKt.transformLatest(flow2, new SharedNotificationContainerViewModel$special$$inlined$flatMapLatest$2(null, this))), "alphaForShadeAndQsExpansion");
        this.panelAlpha = keyguardInteractor.panelAlpha;
        Set<PrimaryBouncerTransition> set2 = set;
        ArrayList arrayList = new ArrayList(CollectionsKt__IterablesKt.collectionSizeOrDefault(set2, 10));
        Iterator<T> it = set2.iterator();
        while (it.hasNext()) {
            arrayList.add(((PrimaryBouncerTransition) it.next()).getNotificationBlurRadius());
        }
        this.blurRadius = dumpWhileCollecting(FlowKt.merge(arrayList), "blurRadius");
        this.glanceableHubAlpha = dumpWhileCollecting(FlowKt.distinctUntilChanged(FlowKt.combineTransform(this.isOnGlanceableHubWithoutShade, this.isOnLockscreen, this.isDreamingWithoutShade, new FlowKt__EmittersKt$onStart$$inlined$unsafeFlow$1(new SharedNotificationContainerViewModel$glanceableHubAlpha$1(null), FlowKt.merge(this.lockscreenToGlanceableHubTransitionViewModel.notificationAlpha, this.glanceableHubToLockscreenTransitionViewModel.notificationAlpha, dozingToGlanceableHubTransitionViewModel.notificationAlpha)), new SharedNotificationContainerViewModel$glanceableHubAlpha$2(null))), "glanceableHubAlpha");
        final ReadonlyStateFlow readonlyStateFlow2 = aodBurnInViewModel.movement;
        this.translationY = dumpWhileCollecting(FlowKt.combine(new FlowKt__EmittersKt$onStart$$inlined$unsafeFlow$1(new SharedNotificationContainerViewModel$translationY$2(null), new Flow() { // from class: com.android.systemui.statusbar.notification.stack.ui.viewmodel.SharedNotificationContainerViewModel$special$$inlined$map$16

            /* renamed from: com.android.systemui.statusbar.notification.stack.ui.viewmodel.SharedNotificationContainerViewModel$special$$inlined$map$16$2, reason: invalid class name */
            public final class AnonymousClass2 implements FlowCollector {
                public final /* synthetic */ FlowCollector $this_unsafeFlow;

                /* renamed from: com.android.systemui.statusbar.notification.stack.ui.viewmodel.SharedNotificationContainerViewModel$special$$inlined$map$16$2$1, reason: invalid class name */
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
                        Float f = new Float(((BurnInModel) obj).translationY);
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
                Object objCollect = readonlyStateFlow2.collect(new AnonymousClass2(flowCollector), continuation);
                return objCollect == CoroutineSingletons.COROUTINE_SUSPENDED ? objCollect : Unit.INSTANCE;
            }
        }), this.isOnLockscreenWithoutShade, FlowKt.merge(this.keyguardInteractor.keyguardTranslationY, this.occludedToLockscreenTransitionViewModel.lockscreenTranslationY), new SharedNotificationContainerViewModel$translationY$3(null)), "translationY");
        this.translationX = dumpWhileCollecting(FlowKt.merge(this.lockscreenToGlanceableHubTransitionViewModel.notificationTranslationX, this.glanceableHubToLockscreenTransitionViewModel.notificationTranslationX, EmptyFlow.INSTANCE), "translationX");
        final StateFlow stateFlow = (StateFlow) this.bounds$delegate.getValue();
        this.availableHeight = dumpWhileCollecting(FlowKt.distinctUntilChanged(new Flow() { // from class: com.android.systemui.statusbar.notification.stack.ui.viewmodel.SharedNotificationContainerViewModel$special$$inlined$map$18

            /* renamed from: com.android.systemui.statusbar.notification.stack.ui.viewmodel.SharedNotificationContainerViewModel$special$$inlined$map$18$2, reason: invalid class name */
            public final class AnonymousClass2 implements FlowCollector {
                public final /* synthetic */ FlowCollector $this_unsafeFlow;

                /* renamed from: com.android.systemui.statusbar.notification.stack.ui.viewmodel.SharedNotificationContainerViewModel$special$$inlined$map$18$2$1, reason: invalid class name */
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
                        NotificationContainerBounds notificationContainerBounds = (NotificationContainerBounds) obj;
                        Float f = new Float(notificationContainerBounds.bottom - notificationContainerBounds.top);
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
                Object objCollect = stateFlow.collect(new AnonymousClass2(flowCollector), continuation);
                return objCollect == CoroutineSingletons.COROUTINE_SUSPENDED ? objCollect : Unit.INSTANCE;
            }
        }), "availableHeight");
    }

    public final Flow getLockscreenDisplayConfig(SharedNotificationContainerBinder$$ExternalSyntheticLambda0 sharedNotificationContainerBinder$$ExternalSyntheticLambda0) {
        FlowKt__ZipKt$combine$$inlined$combineUnsafe$FlowKt__ZipKt$1 flowKt__ZipKt$combine$$inlined$combineUnsafe$FlowKt__ZipKt$1Combine = FlowKt.combine(this.isOnLockscreen, this.keyguardInteractor.statusBarState, new FlowKt__EmittersKt$onStart$$inlined$unsafeFlow$1(new SharedNotificationContainerViewModel$getLockscreenDisplayConfig$showUnlimitedNotificationsAndIsOnLockScreen$1(null), FlowKt.merge(this.primaryBouncerToGoneTransitionViewModel.showAllNotifications, this.alternateBouncerToGoneTransitionViewModel.showAllNotifications)), new SharedNotificationContainerViewModel$getLockscreenDisplayConfig$showUnlimitedNotificationsAndIsOnLockScreen$2(null));
        ReadonlyStateFlow readonlyStateFlow = ((ShadeInteractorImpl) this.shadeInteractor).isUserInteracting;
        SharedNotificationContainerInteractor sharedNotificationContainerInteractor = this.interactor;
        Flow flow = sharedNotificationContainerInteractor.notificationStackChanged;
        return dumpWhileCollecting(FlowKt.distinctUntilChanged(new SafeFlow(new SharedNotificationContainerViewModel$getLockscreenDisplayConfig$$inlined$combineTransform$1(new Flow[]{this.isOnLockscreenWithoutShade, flowKt__ZipKt$combine$$inlined$combineUnsafe$FlowKt__ZipKt$1Combine, readonlyStateFlow, this.availableHeight, flow, sharedNotificationContainerInteractor.useExtraShelfSpace}, null, sharedNotificationContainerBinder$$ExternalSyntheticLambda0))), "maxNotifications");
    }

    public final Flow keyguardAlpha(final ViewStateAccessor viewStateAccessor, CoroutineScope coroutineScope) {
        KeyguardState keyguardState = KeyguardState.OCCLUDED;
        KeyguardTransitionInteractor keyguardTransitionInteractor = this.keyguardTransitionInteractor;
        final MutableSharedFlow transitionValueFlow = keyguardTransitionInteractor.getTransitionValueFlow(keyguardState);
        Flow flow = new Flow() { // from class: com.android.systemui.statusbar.notification.stack.ui.viewmodel.SharedNotificationContainerViewModel$keyguardAlpha$$inlined$map$1

            /* renamed from: com.android.systemui.statusbar.notification.stack.ui.viewmodel.SharedNotificationContainerViewModel$keyguardAlpha$$inlined$map$1$2, reason: invalid class name */
            public final class AnonymousClass2 implements FlowCollector {
                public final /* synthetic */ FlowCollector $this_unsafeFlow;

                /* renamed from: com.android.systemui.statusbar.notification.stack.ui.viewmodel.SharedNotificationContainerViewModel$keyguardAlpha$$inlined$map$1$2$1, reason: invalid class name */
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
        };
        BooleanFlowOperators booleanFlowOperators = BooleanFlowOperators.INSTANCE;
        SceneKey sceneKey = Scenes.Communal;
        final MutableSharedFlow transitionValueFlow2 = keyguardTransitionInteractor.getTransitionValueFlow(KeyguardState.GONE);
        Flow flowAnyOf = booleanFlowOperators.anyOf(flow, new Flow() { // from class: com.android.systemui.statusbar.notification.stack.ui.viewmodel.SharedNotificationContainerViewModel$keyguardAlpha$$inlined$map$2

            /* renamed from: com.android.systemui.statusbar.notification.stack.ui.viewmodel.SharedNotificationContainerViewModel$keyguardAlpha$$inlined$map$2$2, reason: invalid class name */
            public final class AnonymousClass2 implements FlowCollector {
                public final /* synthetic */ FlowCollector $this_unsafeFlow;

                /* renamed from: com.android.systemui.statusbar.notification.stack.ui.viewmodel.SharedNotificationContainerViewModel$keyguardAlpha$$inlined$map$2$2$1, reason: invalid class name */
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
                Object objCollect = transitionValueFlow2.collect(new AnonymousClass2(flowCollector), continuation);
                return objCollect == CoroutineSingletons.COROUTINE_SUSPENDED ? objCollect : Unit.INSTANCE;
            }
        });
        KeyguardInteractor keyguardInteractor = this.keyguardInteractor;
        FlowKt__ZipKt$combine$$inlined$unsafeFlow$1 flowKt__ZipKt$combine$$inlined$unsafeFlow$1 = new FlowKt__ZipKt$combine$$inlined$unsafeFlow$1(flowAnyOf, keyguardInteractor.statusBarState, new SharedNotificationContainerViewModel$keyguardAlpha$isKeyguardNotVisible$1(null));
        Flow flowDumpWhileCollecting = dumpWhileCollecting(keyguardInteractor.dismissAlpha, "keyguardInteractor.dismissAlpha");
        final PrimaryBouncerToGoneTransitionViewModel primaryBouncerToGoneTransitionViewModel = this.primaryBouncerToGoneTransitionViewModel;
        primaryBouncerToGoneTransitionViewModel.getClass();
        final Ref$FloatRef ref$FloatRef = new Ref$FloatRef();
        ref$FloatRef.element = 1.0f;
        Duration.Companion companion = Duration.Companion;
        DurationUnit durationUnit = DurationUnit.MILLISECONDS;
        KeyguardTransitionAnimationFlow$FlowBuilder$sharedFlow74qcysc$$inlined$mapNotNull$1 keyguardTransitionAnimationFlow$FlowBuilder$sharedFlow74qcysc$$inlined$mapNotNull$1M2614sharedFlow74qcysc$default = KeyguardTransitionAnimationFlow.FlowBuilder.m2614sharedFlow74qcysc$default(primaryBouncerToGoneTransitionViewModel.transitionAnimation, DurationKt.toDuration(200, durationUnit), new Function1() { // from class: com.android.systemui.keyguard.ui.viewmodel.PrimaryBouncerToGoneTransitionViewModel$$ExternalSyntheticLambda6
            @Override // kotlin.jvm.functions.Function1
            /* renamed from: invoke */
            public final Object mo781invoke(Object obj) {
                float fFloatValue = ((Float) obj).floatValue();
                PrimaryBouncerToGoneTransitionViewModel primaryBouncerToGoneTransitionViewModel2 = primaryBouncerToGoneTransitionViewModel;
                return (primaryBouncerToGoneTransitionViewModel2.willRunDismissFromKeyguard || primaryBouncerToGoneTransitionViewModel2.leaveShadeOpen) ? Float.valueOf(1.0f) : Float.valueOf(MathUtils.lerp(ref$FloatRef.element, 0.0f, fFloatValue));
            }
        }, 0L, new Function0() { // from class: com.android.systemui.keyguard.ui.viewmodel.PrimaryBouncerToGoneTransitionViewModel$$ExternalSyntheticLambda7
            @Override // kotlin.jvm.functions.Function0
            public final Object invoke() {
                ref$FloatRef.element = ((Number) viewStateAccessor.alpha.invoke()).floatValue();
                PrimaryBouncerToGoneTransitionViewModel primaryBouncerToGoneTransitionViewModel2 = primaryBouncerToGoneTransitionViewModel;
                primaryBouncerToGoneTransitionViewModel2.leaveShadeOpen = ((StatusBarStateControllerImpl) primaryBouncerToGoneTransitionViewModel2.statusBarStateController).mLeaveOpenOnKeyguardHide;
                primaryBouncerToGoneTransitionViewModel2.willRunDismissFromKeyguard = primaryBouncerToGoneTransitionViewModel2.primaryBouncerInteractor.willRunDismissFromKeyguard();
                return Unit.INSTANCE;
            }
        }, null, new AnimatedBackgroundKt$$ExternalSyntheticLambda0(), null, null, IKnoxCustomManager.Stub.TRANSACTION_getWifiState);
        final AlternateBouncerToGoneTransitionViewModel alternateBouncerToGoneTransitionViewModel = this.alternateBouncerToGoneTransitionViewModel;
        alternateBouncerToGoneTransitionViewModel.getClass();
        final Ref$FloatRef ref$FloatRef2 = new Ref$FloatRef();
        ref$FloatRef2.element = 1.0f;
        final Ref$BooleanRef ref$BooleanRef = new Ref$BooleanRef();
        Flow flowDumpWhileCollecting2 = dumpWhileCollecting(com.android.systemui.util.kotlin.FlowKt.sample(FlowKt.merge(keyguardTransitionAnimationFlow$FlowBuilder$sharedFlow74qcysc$$inlined$mapNotNull$1M2614sharedFlow74qcysc$default, KeyguardTransitionAnimationFlow.FlowBuilder.m2614sharedFlow74qcysc$default(alternateBouncerToGoneTransitionViewModel.transitionAnimation, DurationKt.toDuration(200, durationUnit), new Function1() { // from class: com.android.systemui.keyguard.ui.viewmodel.AlternateBouncerToGoneTransitionViewModel$$ExternalSyntheticLambda1
            @Override // kotlin.jvm.functions.Function1
            /* renamed from: invoke */
            public final Object mo781invoke(Object obj) {
                return ref$BooleanRef.element ? Float.valueOf(1.0f) : Float.valueOf(MathUtils.lerp(ref$FloatRef2.element, 0.0f, ((Float) obj).floatValue()));
            }
        }, 0L, new Function0() { // from class: com.android.systemui.keyguard.ui.viewmodel.AlternateBouncerToGoneTransitionViewModel$$ExternalSyntheticLambda2
            @Override // kotlin.jvm.functions.Function0
            public final Object invoke() {
                ref$BooleanRef.element = ((StatusBarStateControllerImpl) alternateBouncerToGoneTransitionViewModel.statusBarStateController).mLeaveOpenOnKeyguardHide;
                ref$FloatRef2.element = ((Number) viewStateAccessor.alpha.invoke()).floatValue();
                return Unit.INSTANCE;
            }
        }, null, new AnimatedBackgroundKt$$ExternalSyntheticLambda0(), null, null, IKnoxCustomManager.Stub.TRANSACTION_getWifiState)), this.communalSceneInteractor.isCommunalVisible, new SharedNotificationContainerViewModel$bouncerToGoneNotificationAlpha$1(null)), "bouncerToGoneNotificationAlpha");
        AodToGoneTransitionViewModel aodToGoneTransitionViewModel = this.aodToGoneTransitionViewModel;
        aodToGoneTransitionViewModel.getClass();
        Ref$FloatRef ref$FloatRef3 = new Ref$FloatRef();
        ref$FloatRef3.element = 1.0f;
        KeyguardTransitionAnimationFlow$FlowBuilder$sharedFlow74qcysc$$inlined$mapNotNull$1 keyguardTransitionAnimationFlow$FlowBuilder$sharedFlow74qcysc$$inlined$mapNotNull$1M2614sharedFlow74qcysc$default2 = KeyguardTransitionAnimationFlow.FlowBuilder.m2614sharedFlow74qcysc$default(aodToGoneTransitionViewModel.transitionAnimation, DurationKt.toDuration(200, durationUnit), new AodToGoneTransitionViewModel$$ExternalSyntheticLambda0(ref$FloatRef3, 0), 0L, new AodToGoneTransitionViewModel$$ExternalSyntheticLambda1(ref$FloatRef3, viewStateAccessor, 0), null, new AnimatedBackgroundKt$$ExternalSyntheticLambda0(), null, null, IKnoxCustomManager.Stub.TRANSACTION_getWifiState);
        KeyguardTransitionAnimationFlow$FlowBuilder$sharedFlow74qcysc$$inlined$mapNotNull$1 keyguardTransitionAnimationFlow$FlowBuilder$sharedFlow74qcysc$$inlined$mapNotNull$1 = this.aodToLockscreenTransitionViewModel.notificationAlpha;
        Flow flowLockscreenAlpha = this.aodToOccludedTransitionViewModel.lockscreenAlpha(viewStateAccessor);
        Flow flowLockscreenAlpha2 = this.aodToGlanceableHubTransitionViewModel.lockscreenAlpha(viewStateAccessor);
        EmptyFlow emptyFlow = this.aodToPrimaryBouncerTransitionViewModel.notificationAlpha;
        KeyguardTransitionAnimationFlow$FlowBuilder$sharedFlow74qcysc$$inlined$mapNotNull$1 keyguardTransitionAnimationFlow$FlowBuilder$sharedFlow74qcysc$$inlined$mapNotNull$12 = this.dozingToLockscreenTransitionViewModel.lockscreenAlpha;
        Flow flowLockscreenAlpha3 = this.dozingToOccludedTransitionViewModel.lockscreenAlpha(viewStateAccessor);
        EmptyFlow emptyFlow2 = this.dozingToPrimaryBouncerTransitionViewModel.notificationAlpha;
        KeyguardTransitionAnimationFlow$FlowBuilder$sharedFlow74qcysc$$inlined$mapNotNull$1 keyguardTransitionAnimationFlow$FlowBuilder$sharedFlow74qcysc$$inlined$mapNotNull$13 = this.dreamingToLockscreenTransitionViewModel.lockscreenAlpha;
        KeyguardTransitionAnimationFlow$FlowBuilder$sharedFlow74qcysc$$inlined$mapNotNull$1 keyguardTransitionAnimationFlow$FlowBuilder$sharedFlow74qcysc$$inlined$mapNotNull$14 = this.goneToAodTransitionViewModel.notificationAlpha;
        KeyguardTransitionAnimationFlow$FlowBuilder$sharedFlow74qcysc$$inlined$mapNotNull$1 keyguardTransitionAnimationFlow$FlowBuilder$sharedFlow74qcysc$$inlined$mapNotNull$15 = this.goneToDreamingTransitionViewModel.lockscreenAlpha;
        KeyguardTransitionAnimationFlow$FlowBuilder$sharedFlow74qcysc$$inlined$mapNotNull$1 keyguardTransitionAnimationFlow$FlowBuilder$sharedFlow74qcysc$$inlined$mapNotNull$16 = this.goneToDozingTransitionViewModel.notificationAlpha;
        KeyguardTransitionAnimationFlow$FlowBuilder$sharedFlow74qcysc$$inlined$mapNotNull$1 keyguardTransitionAnimationFlow$FlowBuilder$sharedFlow74qcysc$$inlined$mapNotNull$17 = this.goneToLockscreenTransitionViewModel.lockscreenAlpha;
        KeyguardTransitionAnimationFlow$FlowBuilder$sharedFlow74qcysc$$inlined$mapNotNull$1 keyguardTransitionAnimationFlow$FlowBuilder$sharedFlow74qcysc$$inlined$mapNotNull$18 = this.lockscreenToDreamingTransitionViewModel.lockscreenAlpha;
        final LockscreenToGoneTransitionViewModel lockscreenToGoneTransitionViewModel = this.lockscreenToGoneTransitionViewModel;
        lockscreenToGoneTransitionViewModel.getClass();
        final Ref$FloatRef ref$FloatRef4 = new Ref$FloatRef();
        ref$FloatRef4.element = 1.0f;
        final Ref$BooleanRef ref$BooleanRef2 = new Ref$BooleanRef();
        KeyguardTransitionAnimationFlow$FlowBuilder$sharedFlow74qcysc$$inlined$mapNotNull$1 keyguardTransitionAnimationFlow$FlowBuilder$sharedFlow74qcysc$$inlined$mapNotNull$1M2614sharedFlow74qcysc$default3 = KeyguardTransitionAnimationFlow.FlowBuilder.m2614sharedFlow74qcysc$default(lockscreenToGoneTransitionViewModel.transitionAnimation, DurationKt.toDuration(80, durationUnit), new Function1() { // from class: com.android.systemui.keyguard.ui.viewmodel.LockscreenToGoneTransitionViewModel$$ExternalSyntheticLambda1
            @Override // kotlin.jvm.functions.Function1
            /* renamed from: invoke */
            public final Object mo781invoke(Object obj) {
                return ref$BooleanRef2.element ? Float.valueOf(1.0f) : Float.valueOf(MathUtils.lerp(ref$FloatRef4.element, 0.0f, ((Float) obj).floatValue()));
            }
        }, 0L, new Function0() { // from class: com.android.systemui.keyguard.ui.viewmodel.LockscreenToGoneTransitionViewModel$$ExternalSyntheticLambda2
            @Override // kotlin.jvm.functions.Function0
            public final Object invoke() {
                ref$BooleanRef2.element = ((StatusBarStateControllerImpl) lockscreenToGoneTransitionViewModel.statusBarStateController).mLeaveOpenOnKeyguardHide;
                ref$FloatRef4.element = ((Number) viewStateAccessor.alpha.invoke()).floatValue();
                return Unit.INSTANCE;
            }
        }, null, null, null, null, 196);
        ChannelLimitedFlowMerge channelLimitedFlowMerge = this.lockscreenToOccludedTransitionViewModel.lockscreenAlpha;
        KeyguardTransitionAnimationFlow$FlowBuilder$sharedFlow74qcysc$$inlined$mapNotNull$1 keyguardTransitionAnimationFlow$FlowBuilder$sharedFlow74qcysc$$inlined$mapNotNull$19 = this.lockscreenToPrimaryBouncerTransitionViewModel.notificationAlpha;
        KeyguardTransitionAnimationFlow$FlowBuilder$sharedFlow74qcysc$$inlined$mapNotNull$1 keyguardTransitionAnimationFlow$FlowBuilder$sharedFlow74qcysc$$inlined$mapNotNull$110 = this.alternateBouncerToPrimaryBouncerTransitionViewModel.notificationAlpha;
        KeyguardTransitionAnimationFlow$FlowBuilder$sharedFlow74qcysc$$inlined$mapNotNull$1 keyguardTransitionAnimationFlow$FlowBuilder$sharedFlow74qcysc$$inlined$mapNotNull$111 = this.occludedToAodTransitionViewModel.lockscreenAlpha;
        OccludedToGoneTransitionViewModel occludedToGoneTransitionViewModel = this.occludedToGoneTransitionViewModel;
        occludedToGoneTransitionViewModel.getClass();
        final Ref$FloatRef ref$FloatRef5 = new Ref$FloatRef();
        Function1 function1 = new Function1() { // from class: com.android.systemui.keyguard.ui.viewmodel.OccludedToGoneTransitionViewModel$$ExternalSyntheticLambda0
            @Override // kotlin.jvm.functions.Function1
            /* renamed from: invoke */
            public final Object mo781invoke(Object obj) {
                ((Float) obj).floatValue();
                int i = OccludedToGoneTransitionViewModel.$r8$clinit;
                return Float.valueOf(ref$FloatRef5.element);
            }
        };
        Function0 function0 = new Function0() { // from class: com.android.systemui.keyguard.ui.viewmodel.OccludedToGoneTransitionViewModel$$ExternalSyntheticLambda1
            @Override // kotlin.jvm.functions.Function0
            public final Object invoke() {
                int i = OccludedToGoneTransitionViewModel.$r8$clinit;
                ref$FloatRef5.element = ((Number) viewStateAccessor.alpha.invoke()).floatValue();
                return Unit.INSTANCE;
            }
        };
        OccludedToGoneTransitionViewModel$$ExternalSyntheticLambda2 occludedToGoneTransitionViewModel$$ExternalSyntheticLambda2 = new OccludedToGoneTransitionViewModel$$ExternalSyntheticLambda2();
        ChannelLimitedFlowMerge channelLimitedFlowMergeMerge = FlowKt.merge(FlowKt.merge(flowDumpWhileCollecting, flowDumpWhileCollecting2, keyguardTransitionAnimationFlow$FlowBuilder$sharedFlow74qcysc$$inlined$mapNotNull$1M2614sharedFlow74qcysc$default2, keyguardTransitionAnimationFlow$FlowBuilder$sharedFlow74qcysc$$inlined$mapNotNull$1, flowLockscreenAlpha, flowLockscreenAlpha2, emptyFlow, keyguardTransitionAnimationFlow$FlowBuilder$sharedFlow74qcysc$$inlined$mapNotNull$12, flowLockscreenAlpha3, emptyFlow2, keyguardTransitionAnimationFlow$FlowBuilder$sharedFlow74qcysc$$inlined$mapNotNull$13, keyguardTransitionAnimationFlow$FlowBuilder$sharedFlow74qcysc$$inlined$mapNotNull$14, keyguardTransitionAnimationFlow$FlowBuilder$sharedFlow74qcysc$$inlined$mapNotNull$15, keyguardTransitionAnimationFlow$FlowBuilder$sharedFlow74qcysc$$inlined$mapNotNull$16, keyguardTransitionAnimationFlow$FlowBuilder$sharedFlow74qcysc$$inlined$mapNotNull$17, keyguardTransitionAnimationFlow$FlowBuilder$sharedFlow74qcysc$$inlined$mapNotNull$18, keyguardTransitionAnimationFlow$FlowBuilder$sharedFlow74qcysc$$inlined$mapNotNull$1M2614sharedFlow74qcysc$default3, channelLimitedFlowMerge, keyguardTransitionAnimationFlow$FlowBuilder$sharedFlow74qcysc$$inlined$mapNotNull$19, keyguardTransitionAnimationFlow$FlowBuilder$sharedFlow74qcysc$$inlined$mapNotNull$110, keyguardTransitionAnimationFlow$FlowBuilder$sharedFlow74qcysc$$inlined$mapNotNull$111, KeyguardTransitionAnimationFlow.FlowBuilder.m2614sharedFlow74qcysc$default(occludedToGoneTransitionViewModel.transitionAnimation, OccludedToGoneTransitionViewModel.DEFAULT_DURATION, function1, 0L, function0, null, occludedToGoneTransitionViewModel$$ExternalSyntheticLambda2, null, null, IKnoxCustomManager.Stub.TRANSACTION_getWifiState), this.occludedToLockscreenTransitionViewModel.lockscreenAlpha, this.offToLockscreenTransitionViewModel.lockscreenAlpha, this.primaryBouncerToLockscreenTransitionViewModel.lockscreenAlpha(viewStateAccessor), this.glanceableHubToLockscreenTransitionViewModel.keyguardAlpha, this.glanceableHubToAodTransitionViewModel.lockscreenAlpha, this.lockscreenToGlanceableHubTransitionViewModel.keyguardAlpha), this.alphaForShadeAndQsExpansion);
        SharingStarted.Companion.getClass();
        return dumpWhileCollecting(FlowKt.distinctUntilChanged(FlowKt.transformLatest(flowKt__ZipKt$combine$$inlined$unsafeFlow$1, new SharedNotificationContainerViewModel$keyguardAlpha$$inlined$flatMapLatest$1(null, this, dumpValue(FlowKt.stateIn(channelLimitedFlowMergeMerge, coroutineScope, SharingStarted.Companion.Eagerly, Float.valueOf(1.0f)), "alphaForTransitionsAndShade")))), "keyguardAlpha");
    }

    public static /* synthetic */ void getPaddingTopDimen$annotations() {
    }
}
