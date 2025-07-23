package com.android.systemui.qs.composefragment.viewmodel;

import android.content.res.Resources;
import android.graphics.Rect;
import android.util.IndentingPrintWriter;
import androidx.compose.runtime.MutableState;
import androidx.compose.runtime.SnapshotMutableStateImpl;
import androidx.compose.runtime.SnapshotStateKt;
import androidx.compose.runtime.State;
import androidx.lifecycle.LifecycleCoroutineScope;
import com.android.app.tracing.coroutines.CoroutineTracingKt;
import com.android.internal.logging.UiEventLogger;
import com.android.systemui.Dumpable;
import com.android.systemui.R;
import com.android.systemui.classifier.domain.interactor.FalsingInteractor;
import com.android.systemui.common.ui.domain.interactor.ConfigurationInteractor;
import com.android.systemui.common.ui.domain.interactor.ConfigurationInteractorImpl;
import com.android.systemui.deviceentry.domain.interactor.DeviceEntryInteractor;
import com.android.systemui.keyguard.domain.interactor.KeyguardTransitionInteractor;
import com.android.systemui.keyguard.shared.model.Edge;
import com.android.systemui.keyguard.shared.model.KeyguardState;
import com.android.systemui.lifecycle.ExclusiveActivatable;
import com.android.systemui.lifecycle.Hydrator;
import com.android.systemui.log.table.TableLogBuffer;
import com.android.systemui.media.controls.ui.view.MediaHost;
import com.android.systemui.qs.FooterActionsController;
import com.android.systemui.qs.footer.ui.viewmodel.FooterActionsViewModel;
import com.android.systemui.qs.panels.domain.interactor.TileSquishinessInteractor;
import com.android.systemui.qs.panels.ui.viewmodel.InFirstPageViewModel;
import com.android.systemui.qs.panels.ui.viewmodel.MediaInRowInLandscapeViewModel;
import com.android.systemui.qs.panels.ui.viewmodel.QuickQuickSettingsViewModel;
import com.android.systemui.qs.ui.viewmodel.QuickSettingsContainerViewModel;
import com.android.systemui.scene.shared.model.Overlays;
import com.android.systemui.shade.LargeScreenHeaderHelper;
import com.android.systemui.shade.PanelTransitionStateChangeEvent;
import com.android.systemui.shade.PanelTransitionStateListener;
import com.android.systemui.shade.SecPanelSplitHelper;
import com.android.systemui.shade.domain.interactor.ShadeInteractor;
import com.android.systemui.shade.domain.interactor.ShadeInteractorImpl;
import com.android.systemui.shade.transition.LargeScreenShadeInterpolator;
import com.android.systemui.statusbar.StatusBarState;
import com.android.systemui.statusbar.SysuiStatusBarStateController;
import com.android.systemui.statusbar.disableflags.domain.interactor.DisableFlagsInteractor;
import com.android.systemui.statusbar.disableflags.shared.model.DisableFlagsModel;
import com.android.systemui.util.DumpUtilsKt;
import com.android.systemui.util.kotlin.FlowKt$emitOnStart$1;
import com.android.systemui.utils.coroutines.flow.FlowConflatedKt;
import java.io.PrintWriter;
import kotlin.ResultKt;
import kotlin.Unit;
import kotlin.coroutines.Continuation;
import kotlin.coroutines.intrinsics.CoroutineSingletons;
import kotlin.coroutines.intrinsics.IntrinsicsKt__IntrinsicsJvmKt;
import kotlin.coroutines.jvm.internal.ContinuationImpl;
import kotlin.coroutines.jvm.internal.SuspendLambda;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.functions.Function2;
import kotlinx.coroutines.CancellableContinuationImpl;
import kotlinx.coroutines.CoroutineScope;
import kotlinx.coroutines.flow.Flow;
import kotlinx.coroutines.flow.FlowCollector;
import kotlinx.coroutines.flow.FlowKt;
import kotlinx.coroutines.flow.FlowKt__BuildersKt$flowOf$$inlined$unsafeFlow$2;
import kotlinx.coroutines.flow.FlowKt__EmittersKt$onStart$$inlined$unsafeFlow$1;
import kotlinx.coroutines.flow.ReadonlyStateFlow;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes2.dex */
public final class QSFragmentComposeViewModel extends ExclusiveActivatable implements Dumpable {
    public final State alphaProgress$delegate;
    public final State anyShadeExpanded$delegate;
    public Runnable collapseExpandAccessibilityAction;
    public final State collapsedLandscapeMedia$delegate;
    public final QuickSettingsContainerViewModel containerViewModel;
    public final State expansionState$delegate;
    public final FalsingInteractor falsingInteractor;
    public final FooterActionsController footerActionsController;
    public final FooterActionsViewModel footerActionsViewModel;
    public final State forceQs$delegate;
    public final State headerTranslation$delegate;
    public final MutableState heightOverride$delegate;
    public final Hydrator hydrator;
    public final InFirstPageViewModel inFirstPageViewModel;
    public final State isBypassEnabled$delegate;
    public final State isEditing$delegate;
    public final State isInBouncerTransit$delegate;
    public final MutableState isInSplitShade$delegate;
    public final State isNotTransitioning$delegate;
    public final State isQsEnabled$delegate;
    public final MutableState isQsExpanded$delegate;
    public final State isQsFullyCollapsed$delegate;
    public final State isQsFullyExpanded$delegate;
    public final MutableState isQsVisible$delegate;
    public final MutableState isSmallScreen$delegate;
    public final MutableState isStackScrollerOverscrolling$delegate;
    public final MutableState isTransitioningToFullShade$delegate;
    public final LargeScreenHeaderHelper largeScreenHeaderHelper;
    public final LargeScreenShadeInterpolator largeScreenShadeInterpolator;
    public final MutableState lockscreenToShadeProgress$delegate;
    public final State mediaSquishiness$delegate;
    public final MutableState overScrollAmount$delegate;
    public final MutableState panelExpansionFraction$delegate;
    public final MutableState panelState$delegate;
    public final MutableState proposedTranslation$delegate;
    public final State qqsBottomPadding$delegate;
    public final State qqsHeaderHeight$delegate;
    public final MutableState qqsHeight$delegate;
    public final MediaHost qqsMediaHost;
    public final MediaInRowInLandscapeViewModel qqsMediaInRowViewModel;
    public final State qqsMediaVisible$delegate;
    public final MutableState qsExpansion$delegate;
    public final MediaHost qsMediaHost;
    public final MediaInRowInLandscapeViewModel qsMediaInRowViewModel;
    public final State qsMediaTranslationY$delegate;
    public final State qsMediaVisible$delegate;
    public final MutableState qsScrollHeight$delegate;
    public final State qsScrollTranslationY$delegate;
    public final QuickQuickSettingsViewModel quickQuickSettingsViewModel;
    public final Resources resources;
    public final SecPanelSplitHelper secPanelSplitHelper;
    public final State shouldApplySquishinessToMedia$delegate;
    public final MutableState shouldUpdateSquishinessOnMedia$delegate;
    public final State showCollapsedOnKeyguard$delegate;
    public final MutableState squishinessFraction$delegate;
    public final TileSquishinessInteractor squishinessInteractor;
    public final State statusBarState$delegate;
    public final SysuiStatusBarStateController sysuiStatusBarStateController;
    public final UiEventLogger uiEventLogger;
    public final boolean usingMedia;
    public final State viewAlpha$delegate;
    public final State viewTranslationY$delegate;

    /* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
    /* renamed from: com.android.systemui.qs.composefragment.viewmodel.QSFragmentComposeViewModel$1, reason: invalid class name */
    final class AnonymousClass1 extends SuspendLambda implements Function2 {
        Object L$0;
        int label;

        public AnonymousClass1(Continuation continuation) {
            super(2, continuation);
        }

        @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
        public final Continuation create(Object obj, Continuation continuation) {
            return QSFragmentComposeViewModel.this.new AnonymousClass1(continuation);
        }

        @Override // kotlin.jvm.functions.Function2
        public final Object invoke(Object obj, Object obj2) {
            return ((AnonymousClass1) create((CoroutineScope) obj, (Continuation) obj2)).invokeSuspend(Unit.INSTANCE);
        }

        @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
        public final Object invokeSuspend(Object obj) {
            CoroutineSingletons coroutineSingletons = CoroutineSingletons.COROUTINE_SUSPENDED;
            int i = this.label;
            if (i == 0) {
                ResultKt.throwOnFailure(obj);
                final QSFragmentComposeViewModel qSFragmentComposeViewModel = QSFragmentComposeViewModel.this;
                this.L$0 = qSFragmentComposeViewModel;
                this.label = 1;
                CancellableContinuationImpl cancellableContinuationImpl = new CancellableContinuationImpl(IntrinsicsKt__IntrinsicsJvmKt.intercepted(this), 1);
                cancellableContinuationImpl.initCancellability();
                final PanelTransitionStateListener panelTransitionStateListener = new PanelTransitionStateListener() { // from class: com.android.systemui.qs.composefragment.viewmodel.QSFragmentComposeViewModel$1$1$listener$1
                    @Override // com.android.systemui.shade.PanelTransitionStateListener
                    public final void onPanelTransitionStateChanged(PanelTransitionStateChangeEvent panelTransitionStateChangeEvent) {
                        ((SnapshotMutableStateImpl) QSFragmentComposeViewModel.this.panelState$delegate).setValue(Integer.valueOf(panelTransitionStateChangeEvent.state));
                    }
                };
                qSFragmentComposeViewModel.secPanelSplitHelper.addListener(panelTransitionStateListener);
                cancellableContinuationImpl.invokeOnCancellation(new Function1() { // from class: com.android.systemui.qs.composefragment.viewmodel.QSFragmentComposeViewModel$1$1$1
                    @Override // kotlin.jvm.functions.Function1
                    /* renamed from: invoke */
                    public final Object mo779invoke(Object obj2) {
                        QSFragmentComposeViewModel.this.secPanelSplitHelper.removeListener(panelTransitionStateListener);
                        return Unit.INSTANCE;
                    }
                });
                if (cancellableContinuationImpl.getResult() == coroutineSingletons) {
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

    /* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
    public interface Factory {
        QSFragmentComposeViewModel create(LifecycleCoroutineScope lifecycleCoroutineScope);
    }

    /* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
    public final class QSExpansionState {
        public final float progress;

        public QSExpansionState(float f) {
            this.progress = f;
        }

        public final boolean equals(Object obj) {
            if (this == obj) {
                return true;
            }
            return (obj instanceof QSExpansionState) && Float.compare(this.progress, ((QSExpansionState) obj).progress) == 0;
        }

        public final int hashCode() {
            return Float.hashCode(this.progress);
        }

        public final String toString() {
            return "QSExpansionState(progress=" + this.progress + ")";
        }
    }

    public QSFragmentComposeViewModel(QuickSettingsContainerViewModel.Factory factory, Resources resources, QuickQuickSettingsViewModel.Factory factory2, FooterActionsViewModel.Factory factory3, FooterActionsController footerActionsController, SysuiStatusBarStateController sysuiStatusBarStateController, DeviceEntryInteractor deviceEntryInteractor, DisableFlagsInteractor disableFlagsInteractor, KeyguardTransitionInteractor keyguardTransitionInteractor, LargeScreenShadeInterpolator largeScreenShadeInterpolator, ShadeInteractor shadeInteractor, ConfigurationInteractor configurationInteractor, LargeScreenHeaderHelper largeScreenHeaderHelper, TileSquishinessInteractor tileSquishinessInteractor, FalsingInteractor falsingInteractor, InFirstPageViewModel inFirstPageViewModel, TableLogBuffer tableLogBuffer, MediaInRowInLandscapeViewModel.Factory factory4, MediaHost mediaHost, MediaHost mediaHost2, boolean z, UiEventLogger uiEventLogger, LifecycleCoroutineScope lifecycleCoroutineScope, SecPanelSplitHelper secPanelSplitHelper) {
        Flow flowKt__BuildersKt$flowOf$$inlined$unsafeFlow$2;
        Flow flowKt__BuildersKt$flowOf$$inlined$unsafeFlow$22;
        this.resources = resources;
        this.footerActionsController = footerActionsController;
        this.sysuiStatusBarStateController = sysuiStatusBarStateController;
        this.largeScreenShadeInterpolator = largeScreenShadeInterpolator;
        this.largeScreenHeaderHelper = largeScreenHeaderHelper;
        this.squishinessInteractor = tileSquishinessInteractor;
        this.falsingInteractor = falsingInteractor;
        this.inFirstPageViewModel = inFirstPageViewModel;
        this.qqsMediaHost = mediaHost;
        this.qsMediaHost = mediaHost2;
        this.usingMedia = z;
        this.uiEventLogger = uiEventLogger;
        this.secPanelSplitHelper = secPanelSplitHelper;
        QuickSettingsContainerViewModel create = factory.create(true, null);
        this.containerViewModel = create;
        this.quickQuickSettingsViewModel = factory2.create();
        this.qqsMediaInRowViewModel = factory4.create(1);
        this.qsMediaInRowViewModel = factory4.create(0);
        Hydrator hydrator = new Hydrator("QSFragmentComposeViewModel.hydrator", tableLogBuffer);
        this.hydrator = hydrator;
        FooterActionsViewModel create2 = factory3.create(lifecycleCoroutineScope);
        CoroutineTracingKt.launchTraced$default(lifecycleCoroutineScope, null, null, new QSFragmentComposeViewModel$footerActionsViewModel$1$1(this, null), 7);
        this.footerActionsViewModel = create2;
        Boolean bool = Boolean.FALSE;
        this.isQsExpanded$delegate = SnapshotStateKt.mutableStateOf$default(bool);
        this.isQsVisible$delegate = SnapshotStateKt.mutableStateOf$default(bool);
        this.panelState$delegate = SnapshotStateKt.mutableStateOf$default(0);
        CoroutineTracingKt.launchTraced$default(lifecycleCoroutineScope, null, null, new AnonymousClass1(null), 7);
        this.qsExpansion$delegate = SnapshotStateKt.mutableStateOf$default(Float.valueOf(-1.0f));
        this.isQsFullyCollapsed$delegate = SnapshotStateKt.derivedStateOf(new QSFragmentComposeViewModel$$ExternalSyntheticLambda0(this, 8));
        this.panelExpansionFraction$delegate = SnapshotStateKt.mutableStateOf$default(Float.valueOf(0.0f));
        this.squishinessFraction$delegate = SnapshotStateKt.mutableStateOf$default(Float.valueOf(1.0f));
        ConfigurationInteractorImpl configurationInteractorImpl = (ConfigurationInteractorImpl) configurationInteractor;
        final FlowKt__EmittersKt$onStart$$inlined$unsafeFlow$1 flowKt__EmittersKt$onStart$$inlined$unsafeFlow$1 = configurationInteractorImpl.onAnyConfigurationChange;
        this.qqsHeaderHeight$delegate = hydrator.hydratedStateOf("qqsHeaderHeight", 0, new Flow() { // from class: com.android.systemui.qs.composefragment.viewmodel.QSFragmentComposeViewModel$special$$inlined$map$1

            /* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
            /* renamed from: com.android.systemui.qs.composefragment.viewmodel.QSFragmentComposeViewModel$special$$inlined$map$1$2, reason: invalid class name */
            public final class AnonymousClass2 implements FlowCollector {
                public final /* synthetic */ FlowCollector $this_unsafeFlow;
                public final /* synthetic */ QSFragmentComposeViewModel this$0;

                /* renamed from: com.android.systemui.qs.composefragment.viewmodel.QSFragmentComposeViewModel$special$$inlined$map$1$2$1, reason: invalid class name */
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

                public AnonymousClass2(FlowCollector flowCollector, QSFragmentComposeViewModel qSFragmentComposeViewModel) {
                    this.$this_unsafeFlow = flowCollector;
                    this.this$0 = qSFragmentComposeViewModel;
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
                        boolean r0 = r6 instanceof com.android.systemui.qs.composefragment.viewmodel.QSFragmentComposeViewModel$special$$inlined$map$1.AnonymousClass2.AnonymousClass1
                        if (r0 == 0) goto L13
                        r0 = r6
                        com.android.systemui.qs.composefragment.viewmodel.QSFragmentComposeViewModel$special$$inlined$map$1$2$1 r0 = (com.android.systemui.qs.composefragment.viewmodel.QSFragmentComposeViewModel$special$$inlined$map$1.AnonymousClass2.AnonymousClass1) r0
                        int r1 = r0.label
                        r2 = -2147483648(0xffffffff80000000, float:-0.0)
                        r3 = r1 & r2
                        if (r3 == 0) goto L13
                        int r1 = r1 - r2
                        r0.label = r1
                        goto L18
                    L13:
                        com.android.systemui.qs.composefragment.viewmodel.QSFragmentComposeViewModel$special$$inlined$map$1$2$1 r0 = new com.android.systemui.qs.composefragment.viewmodel.QSFragmentComposeViewModel$special$$inlined$map$1$2$1
                        r0.<init>(r6)
                    L18:
                        java.lang.Object r6 = r0.result
                        kotlin.coroutines.intrinsics.CoroutineSingletons r1 = kotlin.coroutines.intrinsics.CoroutineSingletons.COROUTINE_SUSPENDED
                        int r2 = r0.label
                        r3 = 1
                        if (r2 == 0) goto L2f
                        if (r2 != r3) goto L27
                        kotlin.ResultKt.throwOnFailure(r6)
                        goto L56
                    L27:
                        java.lang.IllegalStateException r4 = new java.lang.IllegalStateException
                        java.lang.String r5 = "call to 'resume' before 'invoke' with coroutine"
                        r4.<init>(r5)
                        throw r4
                    L2f:
                        kotlin.ResultKt.throwOnFailure(r6)
                        kotlin.Unit r5 = (kotlin.Unit) r5
                        com.android.systemui.qs.composefragment.viewmodel.QSFragmentComposeViewModel r5 = r4.this$0
                        android.content.res.Resources r6 = r5.resources
                        boolean r6 = com.android.systemui.util.LargeScreenUtils.shouldUseLargeScreenShadeHeader(r6)
                        if (r6 == 0) goto L40
                        r5 = 0
                        goto L46
                    L40:
                        com.android.systemui.shade.LargeScreenHeaderHelper r5 = r5.largeScreenHeaderHelper
                        int r5 = r5.getLargeScreenHeaderHeight()
                    L46:
                        java.lang.Integer r6 = new java.lang.Integer
                        r6.<init>(r5)
                        r0.label = r3
                        kotlinx.coroutines.flow.FlowCollector r4 = r4.$this_unsafeFlow
                        java.lang.Object r4 = r4.emit(r6, r0)
                        if (r4 != r1) goto L56
                        return r1
                    L56:
                        kotlin.Unit r4 = kotlin.Unit.INSTANCE
                        return r4
                    */
                    throw new UnsupportedOperationException("Method not decompiled: com.android.systemui.qs.composefragment.viewmodel.QSFragmentComposeViewModel$special$$inlined$map$1.AnonymousClass2.emit(java.lang.Object, kotlin.coroutines.Continuation):java.lang.Object");
                }
            }

            @Override // kotlinx.coroutines.flow.Flow
            public final Object collect(FlowCollector flowCollector, Continuation continuation) {
                Object collect = Flow.this.collect(new AnonymousClass2(flowCollector, this), continuation);
                return collect == CoroutineSingletons.COROUTINE_SUSPENDED ? collect : Unit.INSTANCE;
            }
        });
        this.qqsBottomPadding$delegate = hydrator.hydratedStateOf("qqsBottomPadding", Integer.valueOf(resources.getDimensionPixelSize(R.dimen.qqs_layout_padding_bottom)), configurationInteractorImpl.dimensionPixelSize(R.dimen.qqs_layout_padding_bottom));
        this.qqsHeight$delegate = SnapshotStateKt.mutableStateOf$default(1);
        this.qsScrollHeight$delegate = SnapshotStateKt.mutableStateOf$default(0);
        this.isStackScrollerOverscrolling$delegate = SnapshotStateKt.mutableStateOf$default(bool);
        this.proposedTranslation$delegate = SnapshotStateKt.mutableStateOf$default(Float.valueOf(0.0f));
        Boolean valueOf = Boolean.valueOf(((DisableFlagsModel) disableFlagsInteractor.disableFlags.$$delegate_0.getValue()).isQuickSettingsEnabled());
        final ReadonlyStateFlow readonlyStateFlow = disableFlagsInteractor.disableFlags;
        this.isQsEnabled$delegate = hydrator.hydratedStateOf("isQsEnabled", valueOf, new Flow() { // from class: com.android.systemui.qs.composefragment.viewmodel.QSFragmentComposeViewModel$special$$inlined$map$2

            /* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
            /* renamed from: com.android.systemui.qs.composefragment.viewmodel.QSFragmentComposeViewModel$special$$inlined$map$2$2, reason: invalid class name */
            public final class AnonymousClass2 implements FlowCollector {
                public final /* synthetic */ FlowCollector $this_unsafeFlow;

                /* renamed from: com.android.systemui.qs.composefragment.viewmodel.QSFragmentComposeViewModel$special$$inlined$map$2$2$1, reason: invalid class name */
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
                        boolean r0 = r6 instanceof com.android.systemui.qs.composefragment.viewmodel.QSFragmentComposeViewModel$special$$inlined$map$2.AnonymousClass2.AnonymousClass1
                        if (r0 == 0) goto L13
                        r0 = r6
                        com.android.systemui.qs.composefragment.viewmodel.QSFragmentComposeViewModel$special$$inlined$map$2$2$1 r0 = (com.android.systemui.qs.composefragment.viewmodel.QSFragmentComposeViewModel$special$$inlined$map$2.AnonymousClass2.AnonymousClass1) r0
                        int r1 = r0.label
                        r2 = -2147483648(0xffffffff80000000, float:-0.0)
                        r3 = r1 & r2
                        if (r3 == 0) goto L13
                        int r1 = r1 - r2
                        r0.label = r1
                        goto L18
                    L13:
                        com.android.systemui.qs.composefragment.viewmodel.QSFragmentComposeViewModel$special$$inlined$map$2$2$1 r0 = new com.android.systemui.qs.composefragment.viewmodel.QSFragmentComposeViewModel$special$$inlined$map$2$2$1
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
                        com.android.systemui.statusbar.disableflags.shared.model.DisableFlagsModel r5 = (com.android.systemui.statusbar.disableflags.shared.model.DisableFlagsModel) r5
                        boolean r5 = r5.isQuickSettingsEnabled()
                        java.lang.Boolean r5 = java.lang.Boolean.valueOf(r5)
                        r0.label = r3
                        kotlinx.coroutines.flow.FlowCollector r4 = r4.$this_unsafeFlow
                        java.lang.Object r4 = r4.emit(r5, r0)
                        if (r4 != r1) goto L47
                        return r1
                    L47:
                        kotlin.Unit r4 = kotlin.Unit.INSTANCE
                        return r4
                    */
                    throw new UnsupportedOperationException("Method not decompiled: com.android.systemui.qs.composefragment.viewmodel.QSFragmentComposeViewModel$special$$inlined$map$2.AnonymousClass2.emit(java.lang.Object, kotlin.coroutines.Continuation):java.lang.Object");
                }
            }

            @Override // kotlinx.coroutines.flow.Flow
            public final Object collect(FlowCollector flowCollector, Continuation continuation) {
                Object collect = Flow.this.collect(new AnonymousClass2(flowCollector), continuation);
                return collect == CoroutineSingletons.COROUTINE_SUSPENDED ? collect : Unit.INSTANCE;
            }
        });
        this.isInSplitShade$delegate = SnapshotStateKt.mutableStateOf$default(bool);
        this.isTransitioningToFullShade$delegate = SnapshotStateKt.mutableStateOf$default(bool);
        this.lockscreenToShadeProgress$delegate = SnapshotStateKt.mutableStateOf$default(Float.valueOf(0.0f));
        this.isSmallScreen$delegate = SnapshotStateKt.mutableStateOf$default(bool);
        this.heightOverride$delegate = SnapshotStateKt.mutableStateOf$default(-1);
        this.expansionState$delegate = SnapshotStateKt.derivedStateOf(new QSFragmentComposeViewModel$$ExternalSyntheticLambda0(this, 13));
        this.isQsFullyExpanded$delegate = SnapshotStateKt.derivedStateOf(new QSFragmentComposeViewModel$$ExternalSyntheticLambda0(this, 14));
        this.overScrollAmount$delegate = SnapshotStateKt.mutableStateOf$default(0);
        this.viewTranslationY$delegate = SnapshotStateKt.derivedStateOf(new QSFragmentComposeViewModel$$ExternalSyntheticLambda0(this, 15));
        this.qsScrollTranslationY$delegate = SnapshotStateKt.derivedStateOf(new QSFragmentComposeViewModel$$ExternalSyntheticLambda0(this, 16));
        this.viewAlpha$delegate = SnapshotStateKt.derivedStateOf(new QSFragmentComposeViewModel$$ExternalSyntheticLambda0(this, 1));
        Boolean valueOf2 = Boolean.valueOf(z);
        if (z) {
            flowKt__BuildersKt$flowOf$$inlined$unsafeFlow$2 = new FlowKt__EmittersKt$onStart$$inlined$unsafeFlow$1(new QSFragmentComposeViewModelKt$mediaHostVisible$2(mediaHost, null), FlowKt.callbackFlow(new QSFragmentComposeViewModelKt$mediaHostVisible$1(mediaHost, null)));
        } else {
            flowKt__BuildersKt$flowOf$$inlined$unsafeFlow$2 = new FlowKt__BuildersKt$flowOf$$inlined$unsafeFlow$2(bool);
        }
        this.qqsMediaVisible$delegate = hydrator.hydratedStateOf("qqsMediaVisible", valueOf2, flowKt__BuildersKt$flowOf$$inlined$unsafeFlow$2);
        Boolean valueOf3 = Boolean.valueOf(z);
        if (z) {
            flowKt__BuildersKt$flowOf$$inlined$unsafeFlow$22 = new FlowKt__EmittersKt$onStart$$inlined$unsafeFlow$1(new QSFragmentComposeViewModelKt$mediaHostVisible$2(mediaHost2, null), FlowKt.callbackFlow(new QSFragmentComposeViewModelKt$mediaHostVisible$1(mediaHost2, null)));
        } else {
            flowKt__BuildersKt$flowOf$$inlined$unsafeFlow$22 = new FlowKt__BuildersKt$flowOf$$inlined$unsafeFlow$2(bool);
        }
        this.qsMediaVisible$delegate = hydrator.hydratedStateOf("qsMediaVisible", valueOf3, flowKt__BuildersKt$flowOf$$inlined$unsafeFlow$22);
        this.shouldUpdateSquishinessOnMedia$delegate = SnapshotStateKt.mutableStateOf$default(bool);
        this.qsMediaTranslationY$delegate = SnapshotStateKt.derivedStateOf(new QSFragmentComposeViewModel$$ExternalSyntheticLambda0(this, 2));
        this.isEditing$delegate = hydrator.hydratedStateOf(create.editModeViewModel.isEditing, "isEditing");
        this.isNotTransitioning$delegate = SnapshotStateKt.derivedStateOf(new QSFragmentComposeViewModel$$ExternalSyntheticLambda0(this, 3));
        Boolean valueOf4 = Boolean.valueOf(resources.getBoolean(R.bool.config_quickSettingsMediaLandscapeCollapsed));
        final FlowKt__EmittersKt$onStart$$inlined$unsafeFlow$1 flowKt__EmittersKt$onStart$$inlined$unsafeFlow$12 = new FlowKt__EmittersKt$onStart$$inlined$unsafeFlow$1(new FlowKt$emitOnStart$1(null), configurationInteractorImpl.onAnyConfigurationChange);
        this.collapsedLandscapeMedia$delegate = hydrator.hydratedStateOf("collapsedLandscapeMedia", valueOf4, new Flow() { // from class: com.android.systemui.qs.composefragment.viewmodel.QSFragmentComposeViewModel$special$$inlined$map$3

            /* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
            /* renamed from: com.android.systemui.qs.composefragment.viewmodel.QSFragmentComposeViewModel$special$$inlined$map$3$2, reason: invalid class name */
            public final class AnonymousClass2 implements FlowCollector {
                public final /* synthetic */ FlowCollector $this_unsafeFlow;
                public final /* synthetic */ QSFragmentComposeViewModel this$0;

                /* renamed from: com.android.systemui.qs.composefragment.viewmodel.QSFragmentComposeViewModel$special$$inlined$map$3$2$1, reason: invalid class name */
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

                public AnonymousClass2(FlowCollector flowCollector, QSFragmentComposeViewModel qSFragmentComposeViewModel) {
                    this.$this_unsafeFlow = flowCollector;
                    this.this$0 = qSFragmentComposeViewModel;
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
                        boolean r0 = r6 instanceof com.android.systemui.qs.composefragment.viewmodel.QSFragmentComposeViewModel$special$$inlined$map$3.AnonymousClass2.AnonymousClass1
                        if (r0 == 0) goto L13
                        r0 = r6
                        com.android.systemui.qs.composefragment.viewmodel.QSFragmentComposeViewModel$special$$inlined$map$3$2$1 r0 = (com.android.systemui.qs.composefragment.viewmodel.QSFragmentComposeViewModel$special$$inlined$map$3.AnonymousClass2.AnonymousClass1) r0
                        int r1 = r0.label
                        r2 = -2147483648(0xffffffff80000000, float:-0.0)
                        r3 = r1 & r2
                        if (r3 == 0) goto L13
                        int r1 = r1 - r2
                        r0.label = r1
                        goto L18
                    L13:
                        com.android.systemui.qs.composefragment.viewmodel.QSFragmentComposeViewModel$special$$inlined$map$3$2$1 r0 = new com.android.systemui.qs.composefragment.viewmodel.QSFragmentComposeViewModel$special$$inlined$map$3$2$1
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
                        kotlin.Unit r5 = (kotlin.Unit) r5
                        com.android.systemui.qs.composefragment.viewmodel.QSFragmentComposeViewModel r5 = r4.this$0
                        android.content.res.Resources r5 = r5.resources
                        r6 = 2131034168(0x7f050038, float:1.7678846E38)
                        boolean r5 = r5.getBoolean(r6)
                        java.lang.Boolean r5 = java.lang.Boolean.valueOf(r5)
                        r0.label = r3
                        kotlinx.coroutines.flow.FlowCollector r4 = r4.$this_unsafeFlow
                        java.lang.Object r4 = r4.emit(r5, r0)
                        if (r4 != r1) goto L4e
                        return r1
                    L4e:
                        kotlin.Unit r4 = kotlin.Unit.INSTANCE
                        return r4
                    */
                    throw new UnsupportedOperationException("Method not decompiled: com.android.systemui.qs.composefragment.viewmodel.QSFragmentComposeViewModel$special$$inlined$map$3.AnonymousClass2.emit(java.lang.Object, kotlin.coroutines.Continuation):java.lang.Object");
                }
            }

            @Override // kotlinx.coroutines.flow.Flow
            public final Object collect(FlowCollector flowCollector, Continuation continuation) {
                Object collect = Flow.this.collect(new AnonymousClass2(flowCollector, this), continuation);
                return collect == CoroutineSingletons.COROUTINE_SUSPENDED ? collect : Unit.INSTANCE;
            }
        });
        this.shouldApplySquishinessToMedia$delegate = SnapshotStateKt.derivedStateOf(new QSFragmentComposeViewModel$$ExternalSyntheticLambda0(this, 4));
        this.mediaSquishiness$delegate = SnapshotStateKt.derivedStateOf(new QSFragmentComposeViewModel$$ExternalSyntheticLambda0(this, 5));
        SnapshotStateKt.mutableStateOf$default(new Rect());
        SnapshotStateKt.mutableStateOf$default(bool);
        this.statusBarState$delegate = hydrator.hydratedStateOf("statusBarState", Integer.valueOf(sysuiStatusBarStateController.getState()), new FlowKt__EmittersKt$onStart$$inlined$unsafeFlow$1(new QSFragmentComposeViewModel$statusBarState$3(this, null), FlowConflatedKt.conflatedCallbackFlow(new QSFragmentComposeViewModel$statusBarState$2(this, null))));
        SnapshotStateKt.mutableStateOf$default(0);
        this.isBypassEnabled$delegate = hydrator.hydratedStateOf(deviceEntryInteractor.isBypassEnabled, "isBypassEnabled");
        this.showCollapsedOnKeyguard$delegate = SnapshotStateKt.derivedStateOf(new QSFragmentComposeViewModel$$ExternalSyntheticLambda0(this, 9));
        this.forceQs$delegate = SnapshotStateKt.derivedStateOf(new QSFragmentComposeViewModel$$ExternalSyntheticLambda0(this, 10));
        this.headerTranslation$delegate = SnapshotStateKt.derivedStateOf(new QSFragmentComposeViewModel$$ExternalSyntheticLambda0(this, 11));
        this.alphaProgress$delegate = SnapshotStateKt.derivedStateOf(new QSFragmentComposeViewModel$$ExternalSyntheticLambda0(this, 12));
        Edge.Companion companion = Edge.Companion;
        this.isInBouncerTransit$delegate = hydrator.hydratedStateOf("isInBouncerTransit", bool, keyguardTransitionInteractor.isInTransition(Edge.Companion.create$default(companion, Overlays.Bouncer), Edge.Companion.create$default(companion, null, KeyguardState.PRIMARY_BOUNCER, 1)));
        this.anyShadeExpanded$delegate = hydrator.hydratedStateOf(((ShadeInteractorImpl) shadeInteractor).baseShadeInteractor.isAnyExpanded(), "anyShadeExpanded");
    }

    @Override // com.android.systemui.Dumpable
    public final void dump(PrintWriter printWriter, String[] strArr) {
        IndentingPrintWriter asIndenting = DumpUtilsKt.asIndenting(printWriter);
        asIndenting.append("Quick Settings state").println(":");
        asIndenting.increaseIndent();
        try {
            Boolean bool = (Boolean) ((SnapshotMutableStateImpl) this.isQsExpanded$delegate).getValue();
            bool.booleanValue();
            DumpUtilsKt.println(asIndenting, "isQSExpanded", bool);
            Boolean bool2 = (Boolean) ((SnapshotMutableStateImpl) this.isQsVisible$delegate).getValue();
            bool2.booleanValue();
            DumpUtilsKt.println(asIndenting, "isQSVisible", bool2);
            Boolean bool3 = (Boolean) ((SnapshotMutableStateImpl) this.anyShadeExpanded$delegate).getValue();
            bool3.booleanValue();
            DumpUtilsKt.println(asIndenting, "anyShadeExpanded", bool3);
            DumpUtilsKt.println(asIndenting, "isQSVisibleAndAnyShadeExpanded", Boolean.valueOf(isQsVisibleAndAnyShadeExpanded()));
            Boolean bool4 = (Boolean) ((SnapshotMutableStateImpl) this.isQsEnabled$delegate).getValue();
            bool4.booleanValue();
            DumpUtilsKt.println(asIndenting, "isQSEnabled", bool4);
            DumpUtilsKt.println(asIndenting, "isCustomizing", this.containerViewModel.editModeViewModel.isEditing.$$delegate_0.getValue());
            DumpUtilsKt.println(asIndenting, "inFirstPage", Boolean.valueOf(this.inFirstPageViewModel.inFirstPage));
            asIndenting.decreaseIndent();
            asIndenting.append("Expansion state").println(":");
            asIndenting.increaseIndent();
            try {
                DumpUtilsKt.println(asIndenting, "qsExpansion", Float.valueOf(getQsExpansion()));
                DumpUtilsKt.println(asIndenting, "panelExpansionFraction", Float.valueOf(((Number) ((SnapshotMutableStateImpl) this.panelExpansionFraction$delegate).getValue()).floatValue()));
                DumpUtilsKt.println(asIndenting, "squishinessFraction", Float.valueOf(((Number) ((SnapshotMutableStateImpl) this.squishinessFraction$delegate).getValue()).floatValue()));
                DumpUtilsKt.println(asIndenting, "proposedTranslation", Float.valueOf(((Number) ((SnapshotMutableStateImpl) this.proposedTranslation$delegate).getValue()).floatValue()));
                DumpUtilsKt.println(asIndenting, "expansionState", (QSExpansionState) this.expansionState$delegate.getValue());
                Boolean bool5 = (Boolean) this.forceQs$delegate.getValue();
                bool5.booleanValue();
                DumpUtilsKt.println(asIndenting, "forceQS", bool5);
                asIndenting.append("Derived values").println(":");
                asIndenting.increaseIndent();
                DumpUtilsKt.println(asIndenting, "headerTranslation", Float.valueOf(((Number) this.headerTranslation$delegate.getValue()).floatValue()));
                DumpUtilsKt.println(asIndenting, "translationScaleY", Float.valueOf(getTranslationScaleY()));
                DumpUtilsKt.println(asIndenting, "viewTranslationY", Float.valueOf(((Number) this.viewTranslationY$delegate.getValue()).floatValue()));
                DumpUtilsKt.println(asIndenting, "qsScrollTranslationY", Float.valueOf(((Number) this.qsScrollTranslationY$delegate.getValue()).floatValue()));
                DumpUtilsKt.println(asIndenting, "viewAlpha", Float.valueOf(((Number) this.viewAlpha$delegate.getValue()).floatValue()));
                asIndenting.decreaseIndent();
                asIndenting.decreaseIndent();
                asIndenting.append("Shade state").println(":");
                asIndenting.increaseIndent();
                try {
                    Boolean bool6 = (Boolean) ((SnapshotMutableStateImpl) this.isStackScrollerOverscrolling$delegate).getValue();
                    bool6.booleanValue();
                    DumpUtilsKt.println(asIndenting, "stackOverscrolling", bool6);
                    DumpUtilsKt.println(asIndenting, "overscrollAmount", Integer.valueOf(((Number) ((SnapshotMutableStateImpl) this.overScrollAmount$delegate).getValue()).intValue()));
                    DumpUtilsKt.println(asIndenting, "statusBarState", StatusBarState.toString(getStatusBarState()));
                    DumpUtilsKt.println(asIndenting, "isKeyguardState", Boolean.valueOf(isKeyguardState$1()));
                    Boolean bool7 = (Boolean) ((SnapshotMutableStateImpl) this.isSmallScreen$delegate).getValue();
                    bool7.booleanValue();
                    DumpUtilsKt.println(asIndenting, "isSmallScreen", bool7);
                    DumpUtilsKt.println(asIndenting, "heightOverride", ((Number) ((SnapshotMutableStateImpl) this.heightOverride$delegate).getValue()).intValue() + "px");
                    DumpUtilsKt.println(asIndenting, "qqsHeaderHeight", ((Number) ((SnapshotMutableStateImpl) this.qqsHeaderHeight$delegate).getValue()).intValue() + "px");
                    DumpUtilsKt.println(asIndenting, "qqsBottomPadding", ((Number) ((SnapshotMutableStateImpl) this.qqsBottomPadding$delegate).getValue()).intValue() + "px");
                    DumpUtilsKt.println(asIndenting, "isSplitShade", Boolean.valueOf(isInSplitShade()));
                    Boolean bool8 = (Boolean) this.showCollapsedOnKeyguard$delegate.getValue();
                    bool8.booleanValue();
                    DumpUtilsKt.println(asIndenting, "showCollapsedOnKeyguard", bool8);
                    DumpUtilsKt.println(asIndenting, "qqsHeight", ((Number) ((SnapshotMutableStateImpl) this.qqsHeight$delegate).getValue()).intValue() + "px");
                    DumpUtilsKt.println(asIndenting, "qsScrollHeight", ((Number) ((SnapshotMutableStateImpl) this.qsScrollHeight$delegate).getValue()).intValue() + "px");
                    asIndenting.decreaseIndent();
                    asIndenting.append("Media").println(":");
                    asIndenting.increaseIndent();
                    try {
                        DumpUtilsKt.println(asIndenting, "qqsMediaVisible", Boolean.valueOf(getQqsMediaVisible()));
                        DumpUtilsKt.println(asIndenting, "qqsMediaInRow", Boolean.valueOf(this.qqsMediaInRowViewModel.getShouldMediaShowInRow()));
                        Boolean bool9 = (Boolean) ((SnapshotMutableStateImpl) this.qsMediaVisible$delegate).getValue();
                        bool9.booleanValue();
                        DumpUtilsKt.println(asIndenting, "qsMediaVisible", bool9);
                        DumpUtilsKt.println(asIndenting, "qsMediaInRow", Boolean.valueOf(this.qsMediaInRowViewModel.getShouldMediaShowInRow()));
                        Boolean bool10 = (Boolean) ((SnapshotMutableStateImpl) this.collapsedLandscapeMedia$delegate).getValue();
                        bool10.booleanValue();
                        DumpUtilsKt.println(asIndenting, "collapsedLandscapeMedia", bool10);
                        DumpUtilsKt.println(asIndenting, "qqsMediaExpansion", Float.valueOf(getQqsMediaExpansion()));
                        Boolean bool11 = (Boolean) ((SnapshotMutableStateImpl) this.shouldUpdateSquishinessOnMedia$delegate).getValue();
                        bool11.booleanValue();
                        DumpUtilsKt.println(asIndenting, "shouldUpdateSquishinessOnMedia", bool11);
                        DumpUtilsKt.println(asIndenting, "mediaSquishiness", Float.valueOf(((Number) this.mediaSquishiness$delegate.getValue()).floatValue()));
                        DumpUtilsKt.println(asIndenting, "qsMediaTranslationY", Float.valueOf(((Number) this.qsMediaTranslationY$delegate.getValue()).floatValue()));
                    } finally {
                    }
                } finally {
                }
            } catch (Throwable th) {
                throw th;
            } finally {
            }
        } finally {
        }
    }

    public final float getQqsMediaExpansion() {
        return (this.qqsMediaInRowViewModel.getShouldMediaShowInRow() && ((Boolean) ((SnapshotMutableStateImpl) this.collapsedLandscapeMedia$delegate).getValue()).booleanValue()) ? 0.0f : 1.0f;
    }

    public final boolean getQqsMediaVisible() {
        return ((Boolean) ((SnapshotMutableStateImpl) this.qqsMediaVisible$delegate).getValue()).booleanValue();
    }

    public final float getQsExpansion() {
        return ((Number) ((SnapshotMutableStateImpl) this.qsExpansion$delegate).getValue()).floatValue();
    }

    public final int getStatusBarState() {
        return ((Number) ((SnapshotMutableStateImpl) this.statusBarState$delegate).getValue()).intValue();
    }

    public final float getTranslationScaleY() {
        return (getQsExpansion() - 1) * (isInSplitShade() ? 1.0f : 0.1f);
    }

    public final boolean isInSplitShade() {
        return ((Boolean) ((SnapshotMutableStateImpl) this.isInSplitShade$delegate).getValue()).booleanValue();
    }

    public final boolean isKeyguardState$1() {
        return getStatusBarState() == 1;
    }

    public final boolean isQsVisibleAndAnyShadeExpanded() {
        boolean booleanValue = ((Boolean) ((SnapshotMutableStateImpl) this.anyShadeExpanded$delegate).getValue()).booleanValue();
        MutableState mutableState = this.panelState$delegate;
        return (booleanValue && ((Boolean) ((SnapshotMutableStateImpl) this.isQsVisible$delegate).getValue()).booleanValue() && ((Number) ((SnapshotMutableStateImpl) mutableState).getValue()).intValue() == 0) || ((Number) ((SnapshotMutableStateImpl) mutableState).getValue()).intValue() == 3;
    }

    /* JADX WARN: Removed duplicated region for block: B:15:0x002f  */
    /* JADX WARN: Removed duplicated region for block: B:8:0x0021  */
    @Override // com.android.systemui.lifecycle.ExclusiveActivatable
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public final java.lang.Object onActivated(kotlin.coroutines.Continuation r5) {
        /*
            r4 = this;
            boolean r0 = r5 instanceof com.android.systemui.qs.composefragment.viewmodel.QSFragmentComposeViewModel$onActivated$1
            if (r0 == 0) goto L13
            r0 = r5
            com.android.systemui.qs.composefragment.viewmodel.QSFragmentComposeViewModel$onActivated$1 r0 = (com.android.systemui.qs.composefragment.viewmodel.QSFragmentComposeViewModel$onActivated$1) r0
            int r1 = r0.label
            r2 = -2147483648(0xffffffff80000000, float:-0.0)
            r3 = r1 & r2
            if (r3 == 0) goto L13
            int r1 = r1 - r2
            r0.label = r1
            goto L18
        L13:
            com.android.systemui.qs.composefragment.viewmodel.QSFragmentComposeViewModel$onActivated$1 r0 = new com.android.systemui.qs.composefragment.viewmodel.QSFragmentComposeViewModel$onActivated$1
            r0.<init>(r4, r5)
        L18:
            java.lang.Object r5 = r0.result
            kotlin.coroutines.intrinsics.CoroutineSingletons r1 = kotlin.coroutines.intrinsics.CoroutineSingletons.COROUTINE_SUSPENDED
            int r2 = r0.label
            r3 = 1
            if (r2 == 0) goto L2f
            if (r2 == r3) goto L2b
            java.lang.IllegalStateException r4 = new java.lang.IllegalStateException
            java.lang.String r5 = "call to 'resume' before 'invoke' with coroutine"
            r4.<init>(r5)
            throw r4
        L2b:
            kotlin.ResultKt.throwOnFailure(r5)
            goto L5e
        L2f:
            kotlin.ResultKt.throwOnFailure(r5)
            float r5 = r4.getQqsMediaExpansion()
            com.android.systemui.media.controls.ui.view.MediaHost r2 = r4.qqsMediaHost
            r2.setExpansion(r5)
            r2.setShowsOnlyActiveMedia(r3)
            r2.init(r3)
            r5 = 1065353216(0x3f800000, float:1.0)
            com.android.systemui.media.controls.ui.view.MediaHost r2 = r4.qsMediaHost
            r2.setExpansion(r5)
            r5 = 0
            r2.setShowsOnlyActiveMedia(r5)
            r2.init(r5)
            com.android.systemui.qs.composefragment.viewmodel.QSFragmentComposeViewModel$onActivated$2 r5 = new com.android.systemui.qs.composefragment.viewmodel.QSFragmentComposeViewModel$onActivated$2
            r2 = 0
            r5.<init>(r4, r2)
            r0.label = r3
            java.lang.Object r4 = kotlinx.coroutines.CoroutineScopeKt.coroutineScope(r5, r0)
            if (r4 != r1) goto L5e
            return r1
        L5e:
            kotlin.KotlinNothingValueException r4 = new kotlin.KotlinNothingValueException
            r4.<init>()
            throw r4
        */
        throw new UnsupportedOperationException("Method not decompiled: com.android.systemui.qs.composefragment.viewmodel.QSFragmentComposeViewModel.onActivated(kotlin.coroutines.Continuation):java.lang.Object");
    }
}
