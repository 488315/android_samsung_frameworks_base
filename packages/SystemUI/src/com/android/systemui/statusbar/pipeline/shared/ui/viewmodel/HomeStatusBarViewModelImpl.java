package com.android.systemui.statusbar.pipeline.shared.ui.viewmodel;

import android.graphics.Rect;
import androidx.compose.runtime.State;
import com.android.app.tracing.FlowTracing;
import com.android.app.tracing.TraceStateLogger;
import com.android.app.tracing.TrackGroupUtils;
import com.android.systemui.flags.RefactorFlagUtils;
import com.android.systemui.keyguard.domain.interactor.KeyguardInteractor;
import com.android.systemui.keyguard.domain.interactor.KeyguardInteractor$$ExternalSyntheticOutline0;
import com.android.systemui.keyguard.domain.interactor.KeyguardTransitionInteractor;
import com.android.systemui.keyguard.shared.model.Edge;
import com.android.systemui.keyguard.shared.model.KeyguardState;
import com.android.systemui.lifecycle.ExclusiveActivatable;
import com.android.systemui.lifecycle.Hydrator;
import com.android.systemui.log.table.DiffableKt;
import com.android.systemui.log.table.TableLogBuffer;
import com.android.systemui.log.table.TableLogBufferFactory;
import com.android.systemui.plugins.subscreen.SubRoom;
import com.android.systemui.scene.domain.interactor.SceneContainerOcclusionInteractor;
import com.android.systemui.scene.domain.interactor.SceneInteractor;
import com.android.systemui.shade.domain.interactor.ShadeDisplaysInteractor;
import com.android.systemui.shade.domain.interactor.ShadeInteractor;
import com.android.systemui.shade.domain.interactor.ShadeInteractorImpl;
import com.android.systemui.shade.shared.flag.ShadeWindowGoesAround;
import com.android.systemui.statusbar.chips.sharetoapp.ui.viewmodel.ShareToAppChipViewModel;
import com.android.systemui.statusbar.chips.ui.model.MultipleOngoingActivityChipsModel;
import com.android.systemui.statusbar.chips.ui.viewmodel.OngoingActivityChipsViewModel;
import com.android.systemui.statusbar.chips.uievents.StatusBarChipsUiEventLogger;
import com.android.systemui.statusbar.data.repository.StatusBarModePerDisplayRepository;
import com.android.systemui.statusbar.data.repository.StatusBarModePerDisplayRepositoryImpl;
import com.android.systemui.statusbar.events.domain.interactor.SystemStatusEventAnimationInteractor;
import com.android.systemui.statusbar.events.shared.model.SystemEventAnimationState;
import com.android.systemui.statusbar.featurepods.popups.ui.viewmodel.StatusBarPopupChipsViewModel;
import com.android.systemui.statusbar.layout.ui.viewmodel.StatusBarContentInsetsViewModel;
import com.android.systemui.statusbar.layout.ui.viewmodel.StatusBarContentInsetsViewModelStore;
import com.android.systemui.statusbar.notification.domain.interactor.ActiveNotificationsInteractor;
import com.android.systemui.statusbar.notification.domain.interactor.HeadsUpNotificationInteractor;
import com.android.systemui.statusbar.notification.domain.interactor.HeadsUpNotificationInteractor$special$$inlined$map$2;
import com.android.systemui.statusbar.notification.shared.NotificationsLiveDataStoreRefactor;
import com.android.systemui.statusbar.phone.data.repository.DarkIconRepositoryImpl;
import com.android.systemui.statusbar.phone.domain.interactor.DarkIconInteractor;
import com.android.systemui.statusbar.phone.domain.interactor.LightsOutInteractor;
import com.android.systemui.statusbar.pipeline.battery.ui.viewmodel.BatteryViewModel;
import com.android.systemui.statusbar.pipeline.shared.domain.interactor.HomeStatusBarIconBlockListInteractor;
import com.android.systemui.statusbar.pipeline.shared.domain.interactor.HomeStatusBarInteractor;
import com.android.systemui.statusbar.pipeline.shared.domain.interactor.HomeStatusBarInteractor$special$$inlined$map$1;
import com.android.systemui.statusbar.pipeline.shared.ui.model.ChipsVisibilityModel;
import com.android.systemui.statusbar.pipeline.shared.ui.model.SystemInfoCombinedVisibilityModel;
import com.android.systemui.statusbar.pipeline.shared.ui.model.VisibilityModel;
import com.android.systemui.statusbar.pipeline.shared.ui.viewmodel.HomeStatusBarViewModelImpl;
import com.samsung.systemui.splugins.lockstar.PluginLockStar;
import javax.inject.Provider;
import kotlin.Lazy;
import kotlin.LazyKt__LazyJVMKt;
import kotlin.Unit;
import kotlin.coroutines.Continuation;
import kotlin.coroutines.intrinsics.CoroutineSingletons;
import kotlin.coroutines.jvm.internal.ContinuationImpl;
import kotlin.jvm.functions.Function0;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlinx.coroutines.CoroutineDispatcher;
import kotlinx.coroutines.CoroutineScope;
import kotlinx.coroutines.flow.Flow;
import kotlinx.coroutines.flow.FlowCollector;
import kotlinx.coroutines.flow.FlowKt;
import kotlinx.coroutines.flow.FlowKt__BuildersKt$flowOf$$inlined$unsafeFlow$2;
import kotlinx.coroutines.flow.FlowKt__TransformKt$onEach$$inlined$unsafeTransform$1;
import kotlinx.coroutines.flow.FlowKt__ZipKt$combine$$inlined$combineUnsafe$FlowKt__ZipKt$1;
import kotlinx.coroutines.flow.FlowKt__ZipKt$combine$$inlined$unsafeFlow$1;
import kotlinx.coroutines.flow.ReadonlyStateFlow;
import kotlinx.coroutines.flow.SharingStarted;
import kotlinx.coroutines.flow.StartedWhileSubscribed;
import kotlinx.coroutines.flow.StateFlow;
import kotlinx.coroutines.flow.StateFlowImpl;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes3.dex */
public final class HomeStatusBarViewModelImpl extends ExclusiveActivatable implements HomeStatusBarViewModel {
    public static final Companion Companion = new Companion(null);
    public final Flow areNotificationsLightsOut;
    public final State areaDark$delegate;
    public final BatteryViewModel.Factory batteryViewModelFactory;
    public final FlowKt__ZipKt$combine$$inlined$combineUnsafe$FlowKt__ZipKt$1 canShowOngoingActivityChips;
    public final FlowKt__TransformKt$onEach$$inlined$unsafeTransform$1 chipsVisibilityModel;
    public final HomeStatusBarViewModelImpl$special$$inlined$map$9 hasOngoingActivityChips;
    public final HomeStatusBarViewModelImpl$special$$inlined$map$6 hideStartSideContentForHeadsUp;
    public final Hydrator hydrator;
    public final Flow iconBlockList;
    public final FlowKt__ZipKt$combine$$inlined$unsafeFlow$1 isAnyChipVisible;
    public final Flow isClockVisible;
    public final FlowKt__ZipKt$combine$$inlined$unsafeFlow$1 isHomeScreenStatusBarAllowedLegacy;
    public final ReadonlyStateFlow isHomeStatusBarAllowed;
    public final FlowKt__ZipKt$combine$$inlined$unsafeFlow$1 isHomeStatusBarAllowedCompat;
    public final Flow isNotificationIconContainerVisible;
    public final FlowKt__ZipKt$combine$$inlined$unsafeFlow$1 isShadeVisibleOnThisDisplay;
    public final FlowKt__ZipKt$combine$$inlined$unsafeFlow$1 isSystemInfoVisible;
    public final ReadonlyStateFlow isTransitioningFromLockscreenToOccluded;
    public final ReadonlyStateFlow mediaProjectionStopDialogDueToCallEndedState;
    public final State ongoingActivityChips$delegate;
    public final ReadonlyStateFlow ongoingActivityChipsLegacy;
    public final ReadonlyStateFlow primaryOngoingActivityChip;
    public final Flow shouldShowOperatorNameView;
    public final Lazy statusBarPopupChips$delegate;
    public final ReadonlyStateFlow systemInfoCombinedVis;
    public final Flow transitionFromLockscreenToDreamStartedEvent;
    public final StatusBarChipsUiEventLogger uiEventLogger;

    /* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
    public final class Companion {
        public /* synthetic */ Companion(DefaultConstructorMarker defaultConstructorMarker) {
            this();
        }

        private Companion() {
        }
    }

    /* JADX WARN: Type inference failed for: r2v9, types: [com.android.systemui.statusbar.pipeline.shared.ui.viewmodel.HomeStatusBarViewModelImpl$special$$inlined$map$9, kotlinx.coroutines.flow.Flow] */
    /* JADX WARN: Type inference failed for: r7v6, types: [com.android.systemui.statusbar.pipeline.shared.ui.viewmodel.HomeStatusBarViewModelImpl$special$$inlined$map$6, kotlinx.coroutines.flow.Flow] */
    public HomeStatusBarViewModelImpl(final int i, BatteryViewModel.Factory factory, TableLogBufferFactory tableLogBufferFactory, HomeStatusBarInteractor homeStatusBarInteractor, HomeStatusBarIconBlockListInteractor homeStatusBarIconBlockListInteractor, LightsOutInteractor lightsOutInteractor, ActiveNotificationsInteractor activeNotificationsInteractor, DarkIconInteractor darkIconInteractor, HeadsUpNotificationInteractor headsUpNotificationInteractor, KeyguardTransitionInteractor keyguardTransitionInteractor, KeyguardInteractor keyguardInteractor, StatusBarOperatorNameViewModel statusBarOperatorNameViewModel, SceneInteractor sceneInteractor, SceneContainerOcclusionInteractor sceneContainerOcclusionInteractor, ShadeInteractor shadeInteractor, ShareToAppChipViewModel shareToAppChipViewModel, OngoingActivityChipsViewModel ongoingActivityChipsViewModel, final StatusBarPopupChipsViewModel.Factory factory2, SystemStatusEventAnimationInteractor systemStatusEventAnimationInteractor, StatusBarContentInsetsViewModelStore statusBarContentInsetsViewModelStore, CoroutineScope coroutineScope, CoroutineDispatcher coroutineDispatcher, Provider provider, StatusBarChipsUiEventLogger statusBarChipsUiEventLogger) {
        ReadonlyStateFlow readonlyStateFlow;
        Flow flowKt__BuildersKt$flowOf$$inlined$unsafeFlow$2;
        final ReadonlyStateFlow readonlyStateFlow2;
        this.batteryViewModelFactory = factory;
        this.uiEventLogger = statusBarChipsUiEventLogger;
        Hydrator hydrator = new Hydrator("HomeStatusBarViewModel.hydrator", null, 2, null);
        this.hydrator = hydrator;
        Companion.getClass();
        TableLogBuffer orCreate = tableLogBufferFactory.getOrCreate(200, "HomeStatusBarViewModel[" + i + "]");
        this.statusBarPopupChips$delegate = LazyKt__LazyJVMKt.lazy(new Function0() { // from class: com.android.systemui.statusbar.pipeline.shared.ui.viewmodel.HomeStatusBarViewModelImpl$$ExternalSyntheticLambda0
            @Override // kotlin.jvm.functions.Function0
            public final Object invoke() {
                HomeStatusBarViewModelImpl.Companion companion = HomeStatusBarViewModelImpl.Companion;
                return StatusBarPopupChipsViewModel.Factory.this.create();
            }
        });
        Edge.Companion companion = Edge.Companion;
        KeyguardState keyguardState = KeyguardState.LOCKSCREEN;
        Edge.StateToState m = KeyguardInteractor$$ExternalSyntheticOutline0.m(companion, keyguardState, KeyguardState.OCCLUDED);
        String str = KeyguardTransitionInteractor.TAG;
        Flow logDiffsForTable = DiffableKt.logDiffsForTable(FlowKt.distinctUntilChanged(keyguardTransitionInteractor.isInTransition(m, null)), orCreate, "", "Lock->Occluded", false);
        SharingStarted.Companion companion2 = SharingStarted.Companion;
        StartedWhileSubscribed WhileSubscribed$default = SharingStarted.Companion.WhileSubscribed$default(companion2, 3);
        Boolean bool = Boolean.FALSE;
        this.isTransitioningFromLockscreenToOccluded = FlowKt.stateIn(logDiffsForTable, coroutineScope, WhileSubscribed$default, bool);
        final Flow transition = keyguardTransitionInteractor.transition(new Edge.StateToState(keyguardState, KeyguardState.DREAMING));
        final Flow flow = new Flow() { // from class: com.android.systemui.statusbar.pipeline.shared.ui.viewmodel.HomeStatusBarViewModelImpl$special$$inlined$filter$1

            /* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
            /* renamed from: com.android.systemui.statusbar.pipeline.shared.ui.viewmodel.HomeStatusBarViewModelImpl$special$$inlined$filter$1$2, reason: invalid class name */
            public final class AnonymousClass2 implements FlowCollector {
                public final /* synthetic */ FlowCollector $this_unsafeFlow;

                /* renamed from: com.android.systemui.statusbar.pipeline.shared.ui.viewmodel.HomeStatusBarViewModelImpl$special$$inlined$filter$1$2$1, reason: invalid class name */
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
                        boolean r0 = r6 instanceof com.android.systemui.statusbar.pipeline.shared.ui.viewmodel.HomeStatusBarViewModelImpl$special$$inlined$filter$1.AnonymousClass2.AnonymousClass1
                        if (r0 == 0) goto L13
                        r0 = r6
                        com.android.systemui.statusbar.pipeline.shared.ui.viewmodel.HomeStatusBarViewModelImpl$special$$inlined$filter$1$2$1 r0 = (com.android.systemui.statusbar.pipeline.shared.ui.viewmodel.HomeStatusBarViewModelImpl$special$$inlined$filter$1.AnonymousClass2.AnonymousClass1) r0
                        int r1 = r0.label
                        r2 = -2147483648(0xffffffff80000000, float:-0.0)
                        r3 = r1 & r2
                        if (r3 == 0) goto L13
                        int r1 = r1 - r2
                        r0.label = r1
                        goto L18
                    L13:
                        com.android.systemui.statusbar.pipeline.shared.ui.viewmodel.HomeStatusBarViewModelImpl$special$$inlined$filter$1$2$1 r0 = new com.android.systemui.statusbar.pipeline.shared.ui.viewmodel.HomeStatusBarViewModelImpl$special$$inlined$filter$1$2$1
                        r0.<init>(r6)
                    L18:
                        java.lang.Object r6 = r0.result
                        kotlin.coroutines.intrinsics.CoroutineSingletons r1 = kotlin.coroutines.intrinsics.CoroutineSingletons.COROUTINE_SUSPENDED
                        int r2 = r0.label
                        r3 = 1
                        if (r2 == 0) goto L2f
                        if (r2 != r3) goto L27
                        kotlin.ResultKt.throwOnFailure(r6)
                        goto L46
                    L27:
                        java.lang.IllegalStateException r4 = new java.lang.IllegalStateException
                        java.lang.String r5 = "call to 'resume' before 'invoke' with coroutine"
                        r4.<init>(r5)
                        throw r4
                    L2f:
                        kotlin.ResultKt.throwOnFailure(r6)
                        r6 = r5
                        com.android.systemui.keyguard.shared.model.TransitionStep r6 = (com.android.systemui.keyguard.shared.model.TransitionStep) r6
                        com.android.systemui.keyguard.shared.model.TransitionState r6 = r6.transitionState
                        com.android.systemui.keyguard.shared.model.TransitionState r2 = com.android.systemui.keyguard.shared.model.TransitionState.STARTED
                        if (r6 != r2) goto L46
                        r0.label = r3
                        kotlinx.coroutines.flow.FlowCollector r4 = r4.$this_unsafeFlow
                        java.lang.Object r4 = r4.emit(r5, r0)
                        if (r4 != r1) goto L46
                        return r1
                    L46:
                        kotlin.Unit r4 = kotlin.Unit.INSTANCE
                        return r4
                    */
                    throw new UnsupportedOperationException("Method not decompiled: com.android.systemui.statusbar.pipeline.shared.ui.viewmodel.HomeStatusBarViewModelImpl$special$$inlined$filter$1.AnonymousClass2.emit(java.lang.Object, kotlin.coroutines.Continuation):java.lang.Object");
                }
            }

            @Override // kotlinx.coroutines.flow.Flow
            public final Object collect(FlowCollector flowCollector, Continuation continuation) {
                Object collect = Flow.this.collect(new AnonymousClass2(flowCollector), continuation);
                return collect == CoroutineSingletons.COROUTINE_SUSPENDED ? collect : Unit.INSTANCE;
            }
        };
        this.transitionFromLockscreenToDreamStartedEvent = FlowKt.flowOn(new Flow() { // from class: com.android.systemui.statusbar.pipeline.shared.ui.viewmodel.HomeStatusBarViewModelImpl$special$$inlined$map$1

            /* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
            /* renamed from: com.android.systemui.statusbar.pipeline.shared.ui.viewmodel.HomeStatusBarViewModelImpl$special$$inlined$map$1$2, reason: invalid class name */
            public final class AnonymousClass2 implements FlowCollector {
                public final /* synthetic */ FlowCollector $this_unsafeFlow;

                /* renamed from: com.android.systemui.statusbar.pipeline.shared.ui.viewmodel.HomeStatusBarViewModelImpl$special$$inlined$map$1$2$1, reason: invalid class name */
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
                        boolean r0 = r6 instanceof com.android.systemui.statusbar.pipeline.shared.ui.viewmodel.HomeStatusBarViewModelImpl$special$$inlined$map$1.AnonymousClass2.AnonymousClass1
                        if (r0 == 0) goto L13
                        r0 = r6
                        com.android.systemui.statusbar.pipeline.shared.ui.viewmodel.HomeStatusBarViewModelImpl$special$$inlined$map$1$2$1 r0 = (com.android.systemui.statusbar.pipeline.shared.ui.viewmodel.HomeStatusBarViewModelImpl$special$$inlined$map$1.AnonymousClass2.AnonymousClass1) r0
                        int r1 = r0.label
                        r2 = -2147483648(0xffffffff80000000, float:-0.0)
                        r3 = r1 & r2
                        if (r3 == 0) goto L13
                        int r1 = r1 - r2
                        r0.label = r1
                        goto L18
                    L13:
                        com.android.systemui.statusbar.pipeline.shared.ui.viewmodel.HomeStatusBarViewModelImpl$special$$inlined$map$1$2$1 r0 = new com.android.systemui.statusbar.pipeline.shared.ui.viewmodel.HomeStatusBarViewModelImpl$special$$inlined$map$1$2$1
                        r0.<init>(r6)
                    L18:
                        java.lang.Object r6 = r0.result
                        kotlin.coroutines.intrinsics.CoroutineSingletons r1 = kotlin.coroutines.intrinsics.CoroutineSingletons.COROUTINE_SUSPENDED
                        int r2 = r0.label
                        r3 = 1
                        if (r2 == 0) goto L2f
                        if (r2 != r3) goto L27
                        kotlin.ResultKt.throwOnFailure(r6)
                        goto L41
                    L27:
                        java.lang.IllegalStateException r4 = new java.lang.IllegalStateException
                        java.lang.String r5 = "call to 'resume' before 'invoke' with coroutine"
                        r4.<init>(r5)
                        throw r4
                    L2f:
                        kotlin.ResultKt.throwOnFailure(r6)
                        com.android.systemui.keyguard.shared.model.TransitionStep r5 = (com.android.systemui.keyguard.shared.model.TransitionStep) r5
                        kotlin.Unit r5 = kotlin.Unit.INSTANCE
                        r0.label = r3
                        kotlinx.coroutines.flow.FlowCollector r4 = r4.$this_unsafeFlow
                        java.lang.Object r4 = r4.emit(r5, r0)
                        if (r4 != r1) goto L41
                        return r1
                    L41:
                        kotlin.Unit r4 = kotlin.Unit.INSTANCE
                        return r4
                    */
                    throw new UnsupportedOperationException("Method not decompiled: com.android.systemui.statusbar.pipeline.shared.ui.viewmodel.HomeStatusBarViewModelImpl$special$$inlined$map$1.AnonymousClass2.emit(java.lang.Object, kotlin.coroutines.Continuation):java.lang.Object");
                }
            }

            @Override // kotlinx.coroutines.flow.Flow
            public final Object collect(FlowCollector flowCollector, Continuation continuation) {
                Object collect = Flow.this.collect(new AnonymousClass2(flowCollector), continuation);
                return collect == CoroutineSingletons.COROUTINE_SUSPENDED ? collect : Unit.INSTANCE;
            }
        }, coroutineDispatcher);
        this.mediaProjectionStopDialogDueToCallEndedState = shareToAppChipViewModel.stopDialogToShow;
        ReadonlyStateFlow readonlyStateFlow3 = ongoingActivityChipsViewModel.primaryChip;
        this.primaryOngoingActivityChip = readonlyStateFlow3;
        this.ongoingActivityChipsLegacy = ongoingActivityChipsViewModel.chipsLegacy;
        final StateFlow anyExpansion = ((ShadeInteractorImpl) shadeInteractor).baseShadeInteractor.getAnyExpansion();
        Flow distinctUntilChanged = FlowKt.distinctUntilChanged(new Flow() { // from class: com.android.systemui.statusbar.pipeline.shared.ui.viewmodel.HomeStatusBarViewModelImpl$special$$inlined$map$2

            /* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
            /* renamed from: com.android.systemui.statusbar.pipeline.shared.ui.viewmodel.HomeStatusBarViewModelImpl$special$$inlined$map$2$2, reason: invalid class name */
            public final class AnonymousClass2 implements FlowCollector {
                public final /* synthetic */ FlowCollector $this_unsafeFlow;

                /* renamed from: com.android.systemui.statusbar.pipeline.shared.ui.viewmodel.HomeStatusBarViewModelImpl$special$$inlined$map$2$2$1, reason: invalid class name */
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
                public final java.lang.Object emit(java.lang.Object r7, kotlin.coroutines.Continuation r8) {
                    /*
                        r6 = this;
                        boolean r0 = r8 instanceof com.android.systemui.statusbar.pipeline.shared.ui.viewmodel.HomeStatusBarViewModelImpl$special$$inlined$map$2.AnonymousClass2.AnonymousClass1
                        if (r0 == 0) goto L13
                        r0 = r8
                        com.android.systemui.statusbar.pipeline.shared.ui.viewmodel.HomeStatusBarViewModelImpl$special$$inlined$map$2$2$1 r0 = (com.android.systemui.statusbar.pipeline.shared.ui.viewmodel.HomeStatusBarViewModelImpl$special$$inlined$map$2.AnonymousClass2.AnonymousClass1) r0
                        int r1 = r0.label
                        r2 = -2147483648(0xffffffff80000000, float:-0.0)
                        r3 = r1 & r2
                        if (r3 == 0) goto L13
                        int r1 = r1 - r2
                        r0.label = r1
                        goto L18
                    L13:
                        com.android.systemui.statusbar.pipeline.shared.ui.viewmodel.HomeStatusBarViewModelImpl$special$$inlined$map$2$2$1 r0 = new com.android.systemui.statusbar.pipeline.shared.ui.viewmodel.HomeStatusBarViewModelImpl$special$$inlined$map$2$2$1
                        r0.<init>(r8)
                    L18:
                        java.lang.Object r8 = r0.result
                        kotlin.coroutines.intrinsics.CoroutineSingletons r1 = kotlin.coroutines.intrinsics.CoroutineSingletons.COROUTINE_SUSPENDED
                        int r2 = r0.label
                        r3 = 1
                        if (r2 == 0) goto L2f
                        if (r2 != r3) goto L27
                        kotlin.ResultKt.throwOnFailure(r8)
                        goto L54
                    L27:
                        java.lang.IllegalStateException r6 = new java.lang.IllegalStateException
                        java.lang.String r7 = "call to 'resume' before 'invoke' with coroutine"
                        r6.<init>(r7)
                        throw r6
                    L2f:
                        kotlin.ResultKt.throwOnFailure(r8)
                        java.lang.Number r7 = (java.lang.Number) r7
                        float r7 = r7.floatValue()
                        double r7 = (double) r7
                        r4 = 4596373779694328218(0x3fc999999999999a, double:0.2)
                        int r7 = (r7 > r4 ? 1 : (r7 == r4 ? 0 : -1))
                        if (r7 < 0) goto L44
                        r7 = r3
                        goto L45
                    L44:
                        r7 = 0
                    L45:
                        java.lang.Boolean r7 = java.lang.Boolean.valueOf(r7)
                        r0.label = r3
                        kotlinx.coroutines.flow.FlowCollector r6 = r6.$this_unsafeFlow
                        java.lang.Object r6 = r6.emit(r7, r0)
                        if (r6 != r1) goto L54
                        return r1
                    L54:
                        kotlin.Unit r6 = kotlin.Unit.INSTANCE
                        return r6
                    */
                    throw new UnsupportedOperationException("Method not decompiled: com.android.systemui.statusbar.pipeline.shared.ui.viewmodel.HomeStatusBarViewModelImpl$special$$inlined$map$2.AnonymousClass2.emit(java.lang.Object, kotlin.coroutines.Continuation):java.lang.Object");
                }
            }

            @Override // kotlinx.coroutines.flow.Flow
            public final Object collect(FlowCollector flowCollector, Continuation continuation) {
                Object collect = Flow.this.collect(new AnonymousClass2(flowCollector), continuation);
                return collect == CoroutineSingletons.COROUTINE_SUSPENDED ? collect : Unit.INSTANCE;
            }
        });
        ShadeWindowGoesAround.INSTANCE.getClass();
        if (ShadeWindowGoesAround.FLAG.isTrue()) {
            final StateFlowImpl stateFlowImpl = ((ShadeDisplaysInteractor) provider.get()).displayId;
            readonlyStateFlow = readonlyStateFlow3;
            flowKt__BuildersKt$flowOf$$inlined$unsafeFlow$2 = new Flow() { // from class: com.android.systemui.statusbar.pipeline.shared.ui.viewmodel.HomeStatusBarViewModelImpl$special$$inlined$map$3

                /* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
                /* renamed from: com.android.systemui.statusbar.pipeline.shared.ui.viewmodel.HomeStatusBarViewModelImpl$special$$inlined$map$3$2, reason: invalid class name */
                public final class AnonymousClass2 implements FlowCollector {
                    public final /* synthetic */ int $thisDisplayId$inlined;
                    public final /* synthetic */ FlowCollector $this_unsafeFlow;

                    /* renamed from: com.android.systemui.statusbar.pipeline.shared.ui.viewmodel.HomeStatusBarViewModelImpl$special$$inlined$map$3$2$1, reason: invalid class name */
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

                    public AnonymousClass2(FlowCollector flowCollector, int i) {
                        this.$this_unsafeFlow = flowCollector;
                        this.$thisDisplayId$inlined = i;
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
                            boolean r0 = r6 instanceof com.android.systemui.statusbar.pipeline.shared.ui.viewmodel.HomeStatusBarViewModelImpl$special$$inlined$map$3.AnonymousClass2.AnonymousClass1
                            if (r0 == 0) goto L13
                            r0 = r6
                            com.android.systemui.statusbar.pipeline.shared.ui.viewmodel.HomeStatusBarViewModelImpl$special$$inlined$map$3$2$1 r0 = (com.android.systemui.statusbar.pipeline.shared.ui.viewmodel.HomeStatusBarViewModelImpl$special$$inlined$map$3.AnonymousClass2.AnonymousClass1) r0
                            int r1 = r0.label
                            r2 = -2147483648(0xffffffff80000000, float:-0.0)
                            r3 = r1 & r2
                            if (r3 == 0) goto L13
                            int r1 = r1 - r2
                            r0.label = r1
                            goto L18
                        L13:
                            com.android.systemui.statusbar.pipeline.shared.ui.viewmodel.HomeStatusBarViewModelImpl$special$$inlined$map$3$2$1 r0 = new com.android.systemui.statusbar.pipeline.shared.ui.viewmodel.HomeStatusBarViewModelImpl$special$$inlined$map$3$2$1
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
                            int r5 = r5.intValue()
                            int r6 = r4.$thisDisplayId$inlined
                            if (r6 != r5) goto L3e
                            r5 = r3
                            goto L3f
                        L3e:
                            r5 = 0
                        L3f:
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
                        throw new UnsupportedOperationException("Method not decompiled: com.android.systemui.statusbar.pipeline.shared.ui.viewmodel.HomeStatusBarViewModelImpl$special$$inlined$map$3.AnonymousClass2.emit(java.lang.Object, kotlin.coroutines.Continuation):java.lang.Object");
                    }
                }

                @Override // kotlinx.coroutines.flow.Flow
                public final Object collect(FlowCollector flowCollector, Continuation continuation) {
                    Object collect = Flow.this.collect(new AnonymousClass2(flowCollector, i), continuation);
                    return collect == CoroutineSingletons.COROUTINE_SUSPENDED ? collect : Unit.INSTANCE;
                }
            };
        } else {
            readonlyStateFlow = readonlyStateFlow3;
            flowKt__BuildersKt$flowOf$$inlined$unsafeFlow$2 = new FlowKt__BuildersKt$flowOf$$inlined$unsafeFlow$2(Boolean.valueOf(i == 0));
        }
        FlowKt__ZipKt$combine$$inlined$unsafeFlow$1 flowKt__ZipKt$combine$$inlined$unsafeFlow$1 = new FlowKt__ZipKt$combine$$inlined$unsafeFlow$1(flowKt__BuildersKt$flowOf$$inlined$unsafeFlow$2, distinctUntilChanged, new HomeStatusBarViewModelImpl$isShadeVisibleOnThisDisplay$1(null));
        this.isShadeVisibleOnThisDisplay = flowKt__ZipKt$combine$$inlined$unsafeFlow$1;
        DiffableKt.logDiffsForTable(FlowKt.distinctUntilChanged(FlowKt.combine(sceneInteractor.currentScene, flowKt__ZipKt$combine$$inlined$unsafeFlow$1, sceneContainerOcclusionInteractor.invisibleDueToOcclusion, new HomeStatusBarViewModelImpl$isHomeStatusBarAllowedByScene$1(null))), orCreate, "", "allowedByScene", false);
        RefactorFlagUtils refactorFlagUtils = RefactorFlagUtils.INSTANCE;
        int i2 = NotificationsLiveDataStoreRefactor.$r8$clinit;
        Flow flow2 = activeNotificationsInteractor.areAnyNotificationsPresent;
        StatusBarModePerDisplayRepository statusBarModePerDisplayRepository = (StatusBarModePerDisplayRepository) lightsOutInteractor.repository.forDisplay(i);
        Flow flow3 = (statusBarModePerDisplayRepository == null || (readonlyStateFlow2 = ((StatusBarModePerDisplayRepositoryImpl) statusBarModePerDisplayRepository).statusBarMode) == null) ? null : new Flow() { // from class: com.android.systemui.statusbar.phone.domain.interactor.LightsOutInteractor$isLowProfile$$inlined$map$1

            /* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
            /* renamed from: com.android.systemui.statusbar.phone.domain.interactor.LightsOutInteractor$isLowProfile$$inlined$map$1$2, reason: invalid class name */
            public final class AnonymousClass2 implements FlowCollector {
                public final /* synthetic */ FlowCollector $this_unsafeFlow;

                /* renamed from: com.android.systemui.statusbar.phone.domain.interactor.LightsOutInteractor$isLowProfile$$inlined$map$1$2$1, reason: invalid class name */
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
                        boolean r0 = r6 instanceof com.android.systemui.statusbar.phone.domain.interactor.LightsOutInteractor$isLowProfile$$inlined$map$1.AnonymousClass2.AnonymousClass1
                        if (r0 == 0) goto L13
                        r0 = r6
                        com.android.systemui.statusbar.phone.domain.interactor.LightsOutInteractor$isLowProfile$$inlined$map$1$2$1 r0 = (com.android.systemui.statusbar.phone.domain.interactor.LightsOutInteractor$isLowProfile$$inlined$map$1.AnonymousClass2.AnonymousClass1) r0
                        int r1 = r0.label
                        r2 = -2147483648(0xffffffff80000000, float:-0.0)
                        r3 = r1 & r2
                        if (r3 == 0) goto L13
                        int r1 = r1 - r2
                        r0.label = r1
                        goto L18
                    L13:
                        com.android.systemui.statusbar.phone.domain.interactor.LightsOutInteractor$isLowProfile$$inlined$map$1$2$1 r0 = new com.android.systemui.statusbar.phone.domain.interactor.LightsOutInteractor$isLowProfile$$inlined$map$1$2$1
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
                        com.android.systemui.statusbar.data.model.StatusBarMode r5 = (com.android.systemui.statusbar.data.model.StatusBarMode) r5
                        int[] r6 = com.android.systemui.statusbar.phone.domain.interactor.LightsOutInteractor.WhenMappings.$EnumSwitchMapping$0
                        int r5 = r5.ordinal()
                        r5 = r6[r5]
                        if (r5 == r3) goto L43
                        r6 = 2
                        if (r5 == r6) goto L43
                        r5 = 0
                        goto L44
                    L43:
                        r5 = r3
                    L44:
                        java.lang.Boolean r5 = java.lang.Boolean.valueOf(r5)
                        r0.label = r3
                        kotlinx.coroutines.flow.FlowCollector r4 = r4.$this_unsafeFlow
                        java.lang.Object r4 = r4.emit(r5, r0)
                        if (r4 != r1) goto L53
                        return r1
                    L53:
                        kotlin.Unit r4 = kotlin.Unit.INSTANCE
                        return r4
                    */
                    throw new UnsupportedOperationException("Method not decompiled: com.android.systemui.statusbar.phone.domain.interactor.LightsOutInteractor$isLowProfile$$inlined$map$1.AnonymousClass2.emit(java.lang.Object, kotlin.coroutines.Continuation):java.lang.Object");
                }
            }

            @Override // kotlinx.coroutines.flow.Flow
            public final Object collect(FlowCollector flowCollector, Continuation continuation) {
                Object collect = Flow.this.collect(new AnonymousClass2(flowCollector), continuation);
                return collect == CoroutineSingletons.COROUTINE_SUSPENDED ? collect : Unit.INSTANCE;
            }
        };
        this.areNotificationsLightsOut = FlowKt.flowOn(DiffableKt.logDiffsForTable(FlowKt.distinctUntilChanged(new FlowKt__ZipKt$combine$$inlined$unsafeFlow$1(flow2, flow3 == null ? new FlowKt__BuildersKt$flowOf$$inlined$unsafeFlow$2(bool) : flow3, new HomeStatusBarViewModelImpl$areNotificationsLightsOut$1(null))), orCreate, "", "notifLightsOut", false), coroutineDispatcher);
        final StateFlow darkState = ((DarkIconRepositoryImpl) darkIconInteractor.repository).darkState(i);
        final Flow flow4 = new Flow() { // from class: com.android.systemui.statusbar.phone.domain.interactor.DarkIconInteractor$darkState$$inlined$map$1

            /* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
            /* renamed from: com.android.systemui.statusbar.phone.domain.interactor.DarkIconInteractor$darkState$$inlined$map$1$2, reason: invalid class name */
            public final class AnonymousClass2 implements FlowCollector {
                public final /* synthetic */ FlowCollector $this_unsafeFlow;

                /* renamed from: com.android.systemui.statusbar.phone.domain.interactor.DarkIconInteractor$darkState$$inlined$map$1$2$1, reason: invalid class name */
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
                public final java.lang.Object emit(java.lang.Object r6, kotlin.coroutines.Continuation r7) {
                    /*
                        r5 = this;
                        boolean r0 = r7 instanceof com.android.systemui.statusbar.phone.domain.interactor.DarkIconInteractor$darkState$$inlined$map$1.AnonymousClass2.AnonymousClass1
                        if (r0 == 0) goto L13
                        r0 = r7
                        com.android.systemui.statusbar.phone.domain.interactor.DarkIconInteractor$darkState$$inlined$map$1$2$1 r0 = (com.android.systemui.statusbar.phone.domain.interactor.DarkIconInteractor$darkState$$inlined$map$1.AnonymousClass2.AnonymousClass1) r0
                        int r1 = r0.label
                        r2 = -2147483648(0xffffffff80000000, float:-0.0)
                        r3 = r1 & r2
                        if (r3 == 0) goto L13
                        int r1 = r1 - r2
                        r0.label = r1
                        goto L18
                    L13:
                        com.android.systemui.statusbar.phone.domain.interactor.DarkIconInteractor$darkState$$inlined$map$1$2$1 r0 = new com.android.systemui.statusbar.phone.domain.interactor.DarkIconInteractor$darkState$$inlined$map$1$2$1
                        r0.<init>(r7)
                    L18:
                        java.lang.Object r7 = r0.result
                        kotlin.coroutines.intrinsics.CoroutineSingletons r1 = kotlin.coroutines.intrinsics.CoroutineSingletons.COROUTINE_SUSPENDED
                        int r2 = r0.label
                        r3 = 1
                        if (r2 == 0) goto L2f
                        if (r2 != r3) goto L27
                        kotlin.ResultKt.throwOnFailure(r7)
                        goto L4a
                    L27:
                        java.lang.IllegalStateException r5 = new java.lang.IllegalStateException
                        java.lang.String r6 = "call to 'resume' before 'invoke' with coroutine"
                        r5.<init>(r6)
                        throw r5
                    L2f:
                        kotlin.ResultKt.throwOnFailure(r7)
                        com.android.systemui.statusbar.phone.SysuiDarkIconDispatcher$DarkChange r6 = (com.android.systemui.statusbar.phone.SysuiDarkIconDispatcher$DarkChange) r6
                        com.android.systemui.statusbar.phone.domain.model.DarkState r7 = new com.android.systemui.statusbar.phone.domain.model.DarkState
                        java.util.Collection r2 = r6.areas
                        int r4 = r6.tint
                        float r6 = r6.darkIntensity
                        r7.<init>(r2, r4, r6)
                        r0.label = r3
                        kotlinx.coroutines.flow.FlowCollector r5 = r5.$this_unsafeFlow
                        java.lang.Object r5 = r5.emit(r7, r0)
                        if (r5 != r1) goto L4a
                        return r1
                    L4a:
                        kotlin.Unit r5 = kotlin.Unit.INSTANCE
                        return r5
                    */
                    throw new UnsupportedOperationException("Method not decompiled: com.android.systemui.statusbar.phone.domain.interactor.DarkIconInteractor$darkState$$inlined$map$1.AnonymousClass2.emit(java.lang.Object, kotlin.coroutines.Continuation):java.lang.Object");
                }
            }

            @Override // kotlinx.coroutines.flow.Flow
            public final Object collect(FlowCollector flowCollector, Continuation continuation) {
                Object collect = Flow.this.collect(new AnonymousClass2(flowCollector), continuation);
                return collect == CoroutineSingletons.COROUTINE_SUSPENDED ? collect : Unit.INSTANCE;
            }
        };
        FlowKt.flowOn(FlowKt.distinctUntilChanged(FlowKt.buffer$default(new Flow() { // from class: com.android.systemui.statusbar.pipeline.shared.ui.viewmodel.HomeStatusBarViewModelImpl$special$$inlined$map$5

            /* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
            /* renamed from: com.android.systemui.statusbar.pipeline.shared.ui.viewmodel.HomeStatusBarViewModelImpl$special$$inlined$map$5$2, reason: invalid class name */
            public final class AnonymousClass2 implements FlowCollector {
                public final /* synthetic */ FlowCollector $this_unsafeFlow;

                /* renamed from: com.android.systemui.statusbar.pipeline.shared.ui.viewmodel.HomeStatusBarViewModelImpl$special$$inlined$map$5$2$1, reason: invalid class name */
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
                        boolean r0 = r6 instanceof com.android.systemui.statusbar.pipeline.shared.ui.viewmodel.HomeStatusBarViewModelImpl$special$$inlined$map$5.AnonymousClass2.AnonymousClass1
                        if (r0 == 0) goto L13
                        r0 = r6
                        com.android.systemui.statusbar.pipeline.shared.ui.viewmodel.HomeStatusBarViewModelImpl$special$$inlined$map$5$2$1 r0 = (com.android.systemui.statusbar.pipeline.shared.ui.viewmodel.HomeStatusBarViewModelImpl$special$$inlined$map$5.AnonymousClass2.AnonymousClass1) r0
                        int r1 = r0.label
                        r2 = -2147483648(0xffffffff80000000, float:-0.0)
                        r3 = r1 & r2
                        if (r3 == 0) goto L13
                        int r1 = r1 - r2
                        r0.label = r1
                        goto L18
                    L13:
                        com.android.systemui.statusbar.pipeline.shared.ui.viewmodel.HomeStatusBarViewModelImpl$special$$inlined$map$5$2$1 r0 = new com.android.systemui.statusbar.pipeline.shared.ui.viewmodel.HomeStatusBarViewModelImpl$special$$inlined$map$5$2$1
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
                        com.android.systemui.statusbar.phone.domain.model.DarkState r5 = (com.android.systemui.statusbar.phone.domain.model.DarkState) r5
                        java.util.Collection r6 = r5.areas
                        com.android.systemui.statusbar.pipeline.shared.ui.viewmodel.HomeStatusBarViewModelImpl$areaTint$1$1 r2 = new com.android.systemui.statusbar.pipeline.shared.ui.viewmodel.HomeStatusBarViewModelImpl$areaTint$1$1
                        int r5 = r5.tint
                        r2.<init>(r6, r5)
                        r0.label = r3
                        kotlinx.coroutines.flow.FlowCollector r4 = r4.$this_unsafeFlow
                        java.lang.Object r4 = r4.emit(r2, r0)
                        if (r4 != r1) goto L48
                        return r1
                    L48:
                        kotlin.Unit r4 = kotlin.Unit.INSTANCE
                        return r4
                    */
                    throw new UnsupportedOperationException("Method not decompiled: com.android.systemui.statusbar.pipeline.shared.ui.viewmodel.HomeStatusBarViewModelImpl$special$$inlined$map$5.AnonymousClass2.emit(java.lang.Object, kotlin.coroutines.Continuation):java.lang.Object");
                }
            }

            @Override // kotlinx.coroutines.flow.Flow
            public final Object collect(FlowCollector flowCollector, Continuation continuation) {
                Object collect = Flow.this.collect(new AnonymousClass2(flowCollector), continuation);
                return collect == CoroutineSingletons.COROUTINE_SUSPENDED ? collect : Unit.INSTANCE;
            }
        }, -1, 2)), coroutineDispatcher);
        HomeStatusBarViewModelImpl$areaDark$2 homeStatusBarViewModelImpl$areaDark$2 = new Object() { // from class: com.android.systemui.statusbar.pipeline.shared.ui.viewmodel.HomeStatusBarViewModelImpl$areaDark$2
        };
        final StateFlow darkState2 = ((DarkIconRepositoryImpl) darkIconInteractor.repository).darkState(i);
        DarkIconInteractor.Companion.getClass();
        final Flow distinctUntilChanged2 = FlowKt.distinctUntilChanged(new Flow() { // from class: com.android.systemui.statusbar.phone.domain.interactor.DarkIconInteractor$Companion$toIsAreaDark$$inlined$map$1

            /* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
            /* renamed from: com.android.systemui.statusbar.phone.domain.interactor.DarkIconInteractor$Companion$toIsAreaDark$$inlined$map$1$2, reason: invalid class name */
            public final class AnonymousClass2 implements FlowCollector {
                public final /* synthetic */ FlowCollector $this_unsafeFlow;

                /* renamed from: com.android.systemui.statusbar.phone.domain.interactor.DarkIconInteractor$Companion$toIsAreaDark$$inlined$map$1$2$1, reason: invalid class name */
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
                public final java.lang.Object emit(java.lang.Object r6, kotlin.coroutines.Continuation r7) {
                    /*
                        r5 = this;
                        boolean r0 = r7 instanceof com.android.systemui.statusbar.phone.domain.interactor.DarkIconInteractor$Companion$toIsAreaDark$$inlined$map$1.AnonymousClass2.AnonymousClass1
                        if (r0 == 0) goto L13
                        r0 = r7
                        com.android.systemui.statusbar.phone.domain.interactor.DarkIconInteractor$Companion$toIsAreaDark$$inlined$map$1$2$1 r0 = (com.android.systemui.statusbar.phone.domain.interactor.DarkIconInteractor$Companion$toIsAreaDark$$inlined$map$1.AnonymousClass2.AnonymousClass1) r0
                        int r1 = r0.label
                        r2 = -2147483648(0xffffffff80000000, float:-0.0)
                        r3 = r1 & r2
                        if (r3 == 0) goto L13
                        int r1 = r1 - r2
                        r0.label = r1
                        goto L18
                    L13:
                        com.android.systemui.statusbar.phone.domain.interactor.DarkIconInteractor$Companion$toIsAreaDark$$inlined$map$1$2$1 r0 = new com.android.systemui.statusbar.phone.domain.interactor.DarkIconInteractor$Companion$toIsAreaDark$$inlined$map$1$2$1
                        r0.<init>(r7)
                    L18:
                        java.lang.Object r7 = r0.result
                        kotlin.coroutines.intrinsics.CoroutineSingletons r1 = kotlin.coroutines.intrinsics.CoroutineSingletons.COROUTINE_SUSPENDED
                        int r2 = r0.label
                        r3 = 1
                        if (r2 == 0) goto L2f
                        if (r2 != r3) goto L27
                        kotlin.ResultKt.throwOnFailure(r7)
                        goto L51
                    L27:
                        java.lang.IllegalStateException r5 = new java.lang.IllegalStateException
                        java.lang.String r6 = "call to 'resume' before 'invoke' with coroutine"
                        r5.<init>(r6)
                        throw r5
                    L2f:
                        kotlin.ResultKt.throwOnFailure(r7)
                        com.android.systemui.statusbar.phone.SysuiDarkIconDispatcher$DarkChange r6 = (com.android.systemui.statusbar.phone.SysuiDarkIconDispatcher$DarkChange) r6
                        com.android.systemui.statusbar.phone.domain.interactor.DarkStateWithoutIntensity r7 = new com.android.systemui.statusbar.phone.domain.interactor.DarkStateWithoutIntensity
                        java.util.Collection r2 = r6.areas
                        float r6 = r6.darkIntensity
                        r4 = 1056964608(0x3f000000, float:0.5)
                        int r6 = (r6 > r4 ? 1 : (r6 == r4 ? 0 : -1))
                        if (r6 >= 0) goto L42
                        r6 = r3
                        goto L43
                    L42:
                        r6 = 0
                    L43:
                        r7.<init>(r2, r6)
                        r0.label = r3
                        kotlinx.coroutines.flow.FlowCollector r5 = r5.$this_unsafeFlow
                        java.lang.Object r5 = r5.emit(r7, r0)
                        if (r5 != r1) goto L51
                        return r1
                    L51:
                        kotlin.Unit r5 = kotlin.Unit.INSTANCE
                        return r5
                    */
                    throw new UnsupportedOperationException("Method not decompiled: com.android.systemui.statusbar.phone.domain.interactor.DarkIconInteractor$Companion$toIsAreaDark$$inlined$map$1.AnonymousClass2.emit(java.lang.Object, kotlin.coroutines.Continuation):java.lang.Object");
                }
            }

            @Override // kotlinx.coroutines.flow.Flow
            public final Object collect(FlowCollector flowCollector, Continuation continuation) {
                Object collect = Flow.this.collect(new AnonymousClass2(flowCollector), continuation);
                return collect == CoroutineSingletons.COROUTINE_SUSPENDED ? collect : Unit.INSTANCE;
            }
        });
        this.areaDark$delegate = hydrator.hydratedStateOf("areaDark", homeStatusBarViewModelImpl$areaDark$2, FlowKt.distinctUntilChanged(FlowKt.buffer$default(new Flow() { // from class: com.android.systemui.statusbar.phone.domain.interactor.DarkIconInteractor$Companion$toIsAreaDark$$inlined$map$2

            /* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
            /* renamed from: com.android.systemui.statusbar.phone.domain.interactor.DarkIconInteractor$Companion$toIsAreaDark$$inlined$map$2$2, reason: invalid class name */
            public final class AnonymousClass2 implements FlowCollector {
                public final /* synthetic */ FlowCollector $this_unsafeFlow;

                /* renamed from: com.android.systemui.statusbar.phone.domain.interactor.DarkIconInteractor$Companion$toIsAreaDark$$inlined$map$2$2$1, reason: invalid class name */
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
                        boolean r0 = r6 instanceof com.android.systemui.statusbar.phone.domain.interactor.DarkIconInteractor$Companion$toIsAreaDark$$inlined$map$2.AnonymousClass2.AnonymousClass1
                        if (r0 == 0) goto L13
                        r0 = r6
                        com.android.systemui.statusbar.phone.domain.interactor.DarkIconInteractor$Companion$toIsAreaDark$$inlined$map$2$2$1 r0 = (com.android.systemui.statusbar.phone.domain.interactor.DarkIconInteractor$Companion$toIsAreaDark$$inlined$map$2.AnonymousClass2.AnonymousClass1) r0
                        int r1 = r0.label
                        r2 = -2147483648(0xffffffff80000000, float:-0.0)
                        r3 = r1 & r2
                        if (r3 == 0) goto L13
                        int r1 = r1 - r2
                        r0.label = r1
                        goto L18
                    L13:
                        com.android.systemui.statusbar.phone.domain.interactor.DarkIconInteractor$Companion$toIsAreaDark$$inlined$map$2$2$1 r0 = new com.android.systemui.statusbar.phone.domain.interactor.DarkIconInteractor$Companion$toIsAreaDark$$inlined$map$2$2$1
                        r0.<init>(r6)
                    L18:
                        java.lang.Object r6 = r0.result
                        kotlin.coroutines.intrinsics.CoroutineSingletons r1 = kotlin.coroutines.intrinsics.CoroutineSingletons.COROUTINE_SUSPENDED
                        int r2 = r0.label
                        r3 = 1
                        if (r2 == 0) goto L2f
                        if (r2 != r3) goto L27
                        kotlin.ResultKt.throwOnFailure(r6)
                        goto L44
                    L27:
                        java.lang.IllegalStateException r4 = new java.lang.IllegalStateException
                        java.lang.String r5 = "call to 'resume' before 'invoke' with coroutine"
                        r4.<init>(r5)
                        throw r4
                    L2f:
                        kotlin.ResultKt.throwOnFailure(r6)
                        com.android.systemui.statusbar.phone.domain.interactor.DarkStateWithoutIntensity r5 = (com.android.systemui.statusbar.phone.domain.interactor.DarkStateWithoutIntensity) r5
                        com.android.systemui.statusbar.phone.domain.interactor.DarkIconInteractor$Companion$toIsAreaDark$2$1 r6 = new com.android.systemui.statusbar.phone.domain.interactor.DarkIconInteractor$Companion$toIsAreaDark$2$1
                        r6.<init>(r5)
                        r0.label = r3
                        kotlinx.coroutines.flow.FlowCollector r4 = r4.$this_unsafeFlow
                        java.lang.Object r4 = r4.emit(r6, r0)
                        if (r4 != r1) goto L44
                        return r1
                    L44:
                        kotlin.Unit r4 = kotlin.Unit.INSTANCE
                        return r4
                    */
                    throw new UnsupportedOperationException("Method not decompiled: com.android.systemui.statusbar.phone.domain.interactor.DarkIconInteractor$Companion$toIsAreaDark$$inlined$map$2.AnonymousClass2.emit(java.lang.Object, kotlin.coroutines.Continuation):java.lang.Object");
                }
            }

            @Override // kotlinx.coroutines.flow.Flow
            public final Object collect(FlowCollector flowCollector, Continuation continuation) {
                Object collect = Flow.this.collect(new AnonymousClass2(flowCollector), continuation);
                return collect == CoroutineSingletons.COROUTINE_SUSPENDED ? collect : Unit.INSTANCE;
            }
        }, -1, 2)));
        FlowKt__ZipKt$combine$$inlined$unsafeFlow$1 flowKt__ZipKt$combine$$inlined$unsafeFlow$12 = new FlowKt__ZipKt$combine$$inlined$unsafeFlow$1(keyguardTransitionInteractor.currentKeyguardState, flowKt__ZipKt$combine$$inlined$unsafeFlow$1, new HomeStatusBarViewModelImpl$isHomeScreenStatusBarAllowedLegacy$1(null));
        this.isHomeScreenStatusBarAllowedLegacy = flowKt__ZipKt$combine$$inlined$unsafeFlow$12;
        this.isHomeStatusBarAllowedCompat = flowKt__ZipKt$combine$$inlined$unsafeFlow$12;
        FlowTracing flowTracing = FlowTracing.INSTANCE;
        ReadonlyStateFlow stateIn = FlowKt.stateIn(new FlowKt__TransformKt$onEach$$inlined$unsafeTransform$1(flowKt__ZipKt$combine$$inlined$unsafeFlow$12, new HomeStatusBarViewModelImpl$special$$inlined$traceEach$default$1(new TraceStateLogger(TrackGroupUtils.trackGroup(PluginLockStar.STATUS_BAR_TYPE, "isHomeStatusBarAllowed"), false, false, true, 6, null), null)), coroutineScope, SharingStarted.Companion.WhileSubscribed$default(companion2, 3), bool);
        this.isHomeStatusBarAllowed = stateIn;
        Flow flowOn = FlowKt.flowOn(DiffableKt.logDiffsForTable(FlowKt.distinctUntilChanged(FlowKt.combine(stateIn, keyguardInteractor.isSecureCameraActive, headsUpNotificationInteractor.statusBarHeadsUpStatus, new HomeStatusBarViewModelImpl$shouldHomeStatusBarBeVisible$1(null))), orCreate, "", "visible", false), coroutineDispatcher);
        final HeadsUpNotificationInteractor$special$$inlined$map$2 headsUpNotificationInteractor$special$$inlined$map$2 = headsUpNotificationInteractor.statusBarHeadsUpStatus;
        ?? r7 = new Flow() { // from class: com.android.systemui.statusbar.pipeline.shared.ui.viewmodel.HomeStatusBarViewModelImpl$special$$inlined$map$6

            /* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
            /* renamed from: com.android.systemui.statusbar.pipeline.shared.ui.viewmodel.HomeStatusBarViewModelImpl$special$$inlined$map$6$2, reason: invalid class name */
            public final class AnonymousClass2 implements FlowCollector {
                public final /* synthetic */ FlowCollector $this_unsafeFlow;

                /* renamed from: com.android.systemui.statusbar.pipeline.shared.ui.viewmodel.HomeStatusBarViewModelImpl$special$$inlined$map$6$2$1, reason: invalid class name */
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
                        boolean r0 = r6 instanceof com.android.systemui.statusbar.pipeline.shared.ui.viewmodel.HomeStatusBarViewModelImpl$special$$inlined$map$6.AnonymousClass2.AnonymousClass1
                        if (r0 == 0) goto L13
                        r0 = r6
                        com.android.systemui.statusbar.pipeline.shared.ui.viewmodel.HomeStatusBarViewModelImpl$special$$inlined$map$6$2$1 r0 = (com.android.systemui.statusbar.pipeline.shared.ui.viewmodel.HomeStatusBarViewModelImpl$special$$inlined$map$6.AnonymousClass2.AnonymousClass1) r0
                        int r1 = r0.label
                        r2 = -2147483648(0xffffffff80000000, float:-0.0)
                        r3 = r1 & r2
                        if (r3 == 0) goto L13
                        int r1 = r1 - r2
                        r0.label = r1
                        goto L18
                    L13:
                        com.android.systemui.statusbar.pipeline.shared.ui.viewmodel.HomeStatusBarViewModelImpl$special$$inlined$map$6$2$1 r0 = new com.android.systemui.statusbar.pipeline.shared.ui.viewmodel.HomeStatusBarViewModelImpl$special$$inlined$map$6$2$1
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
                        com.android.systemui.statusbar.notification.headsup.PinnedStatus r5 = (com.android.systemui.statusbar.notification.headsup.PinnedStatus) r5
                        com.android.systemui.statusbar.notification.headsup.PinnedStatus r6 = com.android.systemui.statusbar.notification.headsup.PinnedStatus.PinnedBySystem
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
                    throw new UnsupportedOperationException("Method not decompiled: com.android.systemui.statusbar.pipeline.shared.ui.viewmodel.HomeStatusBarViewModelImpl$special$$inlined$map$6.AnonymousClass2.emit(java.lang.Object, kotlin.coroutines.Continuation):java.lang.Object");
                }
            }

            @Override // kotlinx.coroutines.flow.Flow
            public final Object collect(FlowCollector flowCollector, Continuation continuation) {
                Object collect = Flow.this.collect(new AnonymousClass2(flowCollector), continuation);
                return collect == CoroutineSingletons.COROUTINE_SUSPENDED ? collect : Unit.INSTANCE;
            }
        };
        this.hideStartSideContentForHeadsUp = r7;
        this.shouldShowOperatorNameView = FlowKt.flowOn(DiffableKt.logDiffsForTable(FlowKt.distinctUntilChanged(FlowKt.combine(flowOn, r7, homeStatusBarInteractor.visibilityViaDisableFlags, homeStatusBarInteractor.shouldShowOperatorName, new HomeStatusBarViewModelImpl$shouldShowOperatorNameView$1(null))), orCreate, "", "showOperatorName", false), coroutineDispatcher);
        FlowKt__ZipKt$combine$$inlined$combineUnsafe$FlowKt__ZipKt$1 combine = FlowKt.combine(stateIn, keyguardInteractor.isSecureCameraActive, r7, new HomeStatusBarViewModelImpl$canShowOngoingActivityChips$1(null));
        this.canShowOngoingActivityChips = combine;
        FlowKt__TransformKt$onEach$$inlined$unsafeTransform$1 flowKt__TransformKt$onEach$$inlined$unsafeTransform$1 = new FlowKt__TransformKt$onEach$$inlined$unsafeTransform$1(new FlowKt__ZipKt$combine$$inlined$unsafeFlow$1(ongoingActivityChipsViewModel.chips, combine, new HomeStatusBarViewModelImpl$chipsVisibilityModel$1(null)), new HomeStatusBarViewModelImpl$special$$inlined$traceEach$default$2(new TraceStateLogger(TrackGroupUtils.trackGroup(PluginLockStar.STATUS_BAR_TYPE, "chips"), false, false, true, 6, null), null));
        this.chipsVisibilityModel = flowKt__TransformKt$onEach$$inlined$unsafeTransform$1;
        this.ongoingActivityChips$delegate = hydrator.hydratedStateOf("ongoingActivityChips", new ChipsVisibilityModel(new MultipleOngoingActivityChipsModel(null, null, null, 7, null), false), flowKt__TransformKt$onEach$$inlined$unsafeTransform$1);
        final ReadonlyStateFlow readonlyStateFlow4 = readonlyStateFlow;
        ?? r2 = new Flow() { // from class: com.android.systemui.statusbar.pipeline.shared.ui.viewmodel.HomeStatusBarViewModelImpl$special$$inlined$map$9

            /* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
            /* renamed from: com.android.systemui.statusbar.pipeline.shared.ui.viewmodel.HomeStatusBarViewModelImpl$special$$inlined$map$9$2, reason: invalid class name */
            public final class AnonymousClass2 implements FlowCollector {
                public final /* synthetic */ FlowCollector $this_unsafeFlow;

                /* renamed from: com.android.systemui.statusbar.pipeline.shared.ui.viewmodel.HomeStatusBarViewModelImpl$special$$inlined$map$9$2$1, reason: invalid class name */
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
                        boolean r0 = r6 instanceof com.android.systemui.statusbar.pipeline.shared.ui.viewmodel.HomeStatusBarViewModelImpl$special$$inlined$map$9.AnonymousClass2.AnonymousClass1
                        if (r0 == 0) goto L13
                        r0 = r6
                        com.android.systemui.statusbar.pipeline.shared.ui.viewmodel.HomeStatusBarViewModelImpl$special$$inlined$map$9$2$1 r0 = (com.android.systemui.statusbar.pipeline.shared.ui.viewmodel.HomeStatusBarViewModelImpl$special$$inlined$map$9.AnonymousClass2.AnonymousClass1) r0
                        int r1 = r0.label
                        r2 = -2147483648(0xffffffff80000000, float:-0.0)
                        r3 = r1 & r2
                        if (r3 == 0) goto L13
                        int r1 = r1 - r2
                        r0.label = r1
                        goto L18
                    L13:
                        com.android.systemui.statusbar.pipeline.shared.ui.viewmodel.HomeStatusBarViewModelImpl$special$$inlined$map$9$2$1 r0 = new com.android.systemui.statusbar.pipeline.shared.ui.viewmodel.HomeStatusBarViewModelImpl$special$$inlined$map$9$2$1
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
                        com.android.systemui.statusbar.chips.ui.model.OngoingActivityChipModel r5 = (com.android.systemui.statusbar.chips.ui.model.OngoingActivityChipModel) r5
                        boolean r5 = r5 instanceof com.android.systemui.statusbar.chips.ui.model.OngoingActivityChipModel.Active
                        java.lang.Boolean r5 = java.lang.Boolean.valueOf(r5)
                        r0.label = r3
                        kotlinx.coroutines.flow.FlowCollector r4 = r4.$this_unsafeFlow
                        java.lang.Object r4 = r4.emit(r5, r0)
                        if (r4 != r1) goto L45
                        return r1
                    L45:
                        kotlin.Unit r4 = kotlin.Unit.INSTANCE
                        return r4
                    */
                    throw new UnsupportedOperationException("Method not decompiled: com.android.systemui.statusbar.pipeline.shared.ui.viewmodel.HomeStatusBarViewModelImpl$special$$inlined$map$9.AnonymousClass2.emit(java.lang.Object, kotlin.coroutines.Continuation):java.lang.Object");
                }
            }

            @Override // kotlinx.coroutines.flow.Flow
            public final Object collect(FlowCollector flowCollector, Continuation continuation) {
                Object collect = Flow.this.collect(new AnonymousClass2(flowCollector), continuation);
                return collect == CoroutineSingletons.COROUTINE_SUSPENDED ? collect : Unit.INSTANCE;
            }
        };
        this.hasOngoingActivityChips = r2;
        FlowKt__ZipKt$combine$$inlined$unsafeFlow$1 flowKt__ZipKt$combine$$inlined$unsafeFlow$13 = new FlowKt__ZipKt$combine$$inlined$unsafeFlow$1(r2, combine, new HomeStatusBarViewModelImpl$isAnyChipVisible$1(null));
        this.isAnyChipVisible = flowKt__ZipKt$combine$$inlined$unsafeFlow$13;
        HomeStatusBarViewModelImpl$isClockVisible$1 homeStatusBarViewModelImpl$isClockVisible$1 = new HomeStatusBarViewModelImpl$isClockVisible$1(this, null);
        HomeStatusBarInteractor$special$$inlined$map$1 homeStatusBarInteractor$special$$inlined$map$1 = homeStatusBarInteractor.visibilityViaDisableFlags;
        this.isClockVisible = FlowKt.flowOn(DiffableKt.logDiffsForTable(FlowKt.distinctUntilChanged(FlowKt.combine(flowOn, r7, homeStatusBarInteractor$special$$inlined$map$1, homeStatusBarViewModelImpl$isClockVisible$1)), orCreate, SubRoom.EXTRA_VALUE_CLOCK, new VisibilityModel(4, false)), coroutineDispatcher);
        this.isNotificationIconContainerVisible = FlowKt.flowOn(DiffableKt.logDiffsForTable(FlowKt.distinctUntilChanged(FlowKt.combine(flowOn, flowKt__ZipKt$combine$$inlined$unsafeFlow$13, homeStatusBarInteractor$special$$inlined$map$1, new HomeStatusBarViewModelImpl$isNotificationIconContainerVisible$1(this, null))), orCreate, "notifContainer", new VisibilityModel(4, false)), coroutineDispatcher);
        FlowKt__ZipKt$combine$$inlined$unsafeFlow$1 flowKt__ZipKt$combine$$inlined$unsafeFlow$14 = new FlowKt__ZipKt$combine$$inlined$unsafeFlow$1(flowOn, homeStatusBarInteractor$special$$inlined$map$1, new HomeStatusBarViewModelImpl$isSystemInfoVisible$1(this, null));
        this.isSystemInfoVisible = flowKt__ZipKt$combine$$inlined$unsafeFlow$14;
        Flow distinctUntilChanged3 = FlowKt.distinctUntilChanged(new FlowKt__ZipKt$combine$$inlined$unsafeFlow$1(flowKt__ZipKt$combine$$inlined$unsafeFlow$14, systemStatusEventAnimationInteractor.animationState, new HomeStatusBarViewModelImpl$systemInfoCombinedVis$1(null)));
        VisibilityModel visibilityModel = new VisibilityModel(0, false);
        SystemEventAnimationState systemEventAnimationState = SystemEventAnimationState.Idle;
        this.systemInfoCombinedVis = FlowKt.stateIn(DiffableKt.logDiffsForTable(distinctUntilChanged3, orCreate, "systemInfo", new SystemInfoCombinedVisibilityModel(visibilityModel, systemEventAnimationState)), coroutineScope, SharingStarted.Companion.WhileSubscribed$default(companion2, 3), new SystemInfoCombinedVisibilityModel(new VisibilityModel(0, false), systemEventAnimationState));
        this.iconBlockList = FlowKt.flowOn(homeStatusBarIconBlockListInteractor.iconBlockList, coroutineDispatcher);
        StatusBarContentInsetsViewModel statusBarContentInsetsViewModel = (StatusBarContentInsetsViewModel) statusBarContentInsetsViewModelStore.forDisplay(i);
        if (statusBarContentInsetsViewModel == null || statusBarContentInsetsViewModel.contentArea == null) {
            FlowKt.flowOn(new FlowKt__BuildersKt$flowOf$$inlined$unsafeFlow$2(new Rect(0, 0, 0, 0)), coroutineDispatcher);
        }
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
            boolean r0 = r5 instanceof com.android.systemui.statusbar.pipeline.shared.ui.viewmodel.HomeStatusBarViewModelImpl$onActivated$1
            if (r0 == 0) goto L13
            r0 = r5
            com.android.systemui.statusbar.pipeline.shared.ui.viewmodel.HomeStatusBarViewModelImpl$onActivated$1 r0 = (com.android.systemui.statusbar.pipeline.shared.ui.viewmodel.HomeStatusBarViewModelImpl$onActivated$1) r0
            int r1 = r0.label
            r2 = -2147483648(0xffffffff80000000, float:-0.0)
            r3 = r1 & r2
            if (r3 == 0) goto L13
            int r1 = r1 - r2
            r0.label = r1
            goto L18
        L13:
            com.android.systemui.statusbar.pipeline.shared.ui.viewmodel.HomeStatusBarViewModelImpl$onActivated$1 r0 = new com.android.systemui.statusbar.pipeline.shared.ui.viewmodel.HomeStatusBarViewModelImpl$onActivated$1
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
            goto L41
        L2f:
            kotlin.ResultKt.throwOnFailure(r5)
            com.android.systemui.statusbar.pipeline.shared.ui.viewmodel.HomeStatusBarViewModelImpl$onActivated$2 r5 = new com.android.systemui.statusbar.pipeline.shared.ui.viewmodel.HomeStatusBarViewModelImpl$onActivated$2
            r2 = 0
            r5.<init>(r4, r2)
            r0.label = r3
            java.lang.Object r4 = kotlinx.coroutines.CoroutineScopeKt.coroutineScope(r5, r0)
            if (r4 != r1) goto L41
            return r1
        L41:
            kotlin.KotlinNothingValueException r4 = new kotlin.KotlinNothingValueException
            r4.<init>()
            throw r4
        */
        throw new UnsupportedOperationException("Method not decompiled: com.android.systemui.statusbar.pipeline.shared.ui.viewmodel.HomeStatusBarViewModelImpl.onActivated(kotlin.coroutines.Continuation):java.lang.Object");
    }
}
