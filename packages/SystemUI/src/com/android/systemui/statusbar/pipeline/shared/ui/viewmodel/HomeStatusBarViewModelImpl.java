package com.android.systemui.statusbar.pipeline.shared.ui.viewmodel;

import android.graphics.Rect;
import androidx.compose.runtime.State;
import com.android.app.tracing.FlowTracing;
import com.android.app.tracing.TraceStateLogger;
import com.android.app.tracing.TrackGroupUtils;
import com.android.app.tracing.coroutines.CoroutineTracingKt;
import com.android.systemui.flags.RefactorFlagUtils;
import com.android.systemui.keyguard.domain.interactor.KeyguardInteractor;
import com.android.systemui.keyguard.domain.interactor.KeyguardInteractor$$ExternalSyntheticOutline0;
import com.android.systemui.keyguard.domain.interactor.KeyguardTransitionInteractor;
import com.android.systemui.keyguard.shared.model.Edge;
import com.android.systemui.keyguard.shared.model.KeyguardState;
import com.android.systemui.keyguard.shared.model.TransitionState;
import com.android.systemui.keyguard.shared.model.TransitionStep;
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
import com.android.systemui.statusbar.chips.ui.model.OngoingActivityChipModel;
import com.android.systemui.statusbar.chips.ui.viewmodel.OngoingActivityChipsViewModel;
import com.android.systemui.statusbar.chips.uievents.StatusBarChipsUiEventLogger;
import com.android.systemui.statusbar.data.model.StatusBarMode;
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
import com.android.systemui.statusbar.notification.headsup.PinnedStatus;
import com.android.systemui.statusbar.notification.shared.NotificationsLiveDataStoreRefactor;
import com.android.systemui.statusbar.phone.SysuiDarkIconDispatcher$DarkChange;
import com.android.systemui.statusbar.phone.data.repository.DarkIconRepositoryImpl;
import com.android.systemui.statusbar.phone.domain.interactor.DarkIconInteractor;
import com.android.systemui.statusbar.phone.domain.interactor.LightsOutInteractor;
import com.android.systemui.statusbar.phone.domain.model.DarkState;
import com.android.systemui.statusbar.pipeline.battery.ui.viewmodel.BatteryViewModel;
import com.android.systemui.statusbar.pipeline.shared.domain.interactor.HomeStatusBarIconBlockListInteractor;
import com.android.systemui.statusbar.pipeline.shared.domain.interactor.HomeStatusBarInteractor;
import com.android.systemui.statusbar.pipeline.shared.domain.interactor.HomeStatusBarInteractor$special$$inlined$map$1;
import com.android.systemui.statusbar.pipeline.shared.ui.model.ChipsVisibilityModel;
import com.android.systemui.statusbar.pipeline.shared.ui.model.SystemInfoCombinedVisibilityModel;
import com.android.systemui.statusbar.pipeline.shared.ui.model.VisibilityModel;
import com.android.systemui.statusbar.pipeline.shared.ui.viewmodel.HomeStatusBarViewModelImpl;
import com.samsung.systemui.splugins.lockstar.PluginLockStar;
import java.util.Collection;
import javax.inject.Provider;
import kotlin.KotlinNothingValueException;
import kotlin.Lazy;
import kotlin.LazyKt__LazyJVMKt;
import kotlin.ResultKt;
import kotlin.Unit;
import kotlin.coroutines.Continuation;
import kotlin.coroutines.intrinsics.CoroutineSingletons;
import kotlin.coroutines.jvm.internal.ContinuationImpl;
import kotlin.coroutines.jvm.internal.SuspendLambda;
import kotlin.jvm.functions.Function0;
import kotlin.jvm.functions.Function2;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlinx.coroutines.CoroutineDispatcher;
import kotlinx.coroutines.CoroutineScope;
import kotlinx.coroutines.CoroutineScopeKt;
import kotlinx.coroutines.DelayKt;
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

    public final class Companion {
        public /* synthetic */ Companion(DefaultConstructorMarker defaultConstructorMarker) {
            this();
        }

        private Companion() {
        }
    }

    /* renamed from: com.android.systemui.statusbar.pipeline.shared.ui.viewmodel.HomeStatusBarViewModelImpl$onActivated$1, reason: invalid class name */
    final class AnonymousClass1 extends ContinuationImpl {
        int label;
        /* synthetic */ Object result;

        public AnonymousClass1(Continuation continuation) {
            super(continuation);
        }

        @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
        public final Object invokeSuspend(Object obj) {
            this.result = obj;
            this.label |= Integer.MIN_VALUE;
            return HomeStatusBarViewModelImpl.this.onActivated(this);
        }
    }

    /* renamed from: com.android.systemui.statusbar.pipeline.shared.ui.viewmodel.HomeStatusBarViewModelImpl$onActivated$2, reason: invalid class name */
    final class AnonymousClass2 extends SuspendLambda implements Function2 {
        private /* synthetic */ Object L$0;
        int label;

        /* renamed from: com.android.systemui.statusbar.pipeline.shared.ui.viewmodel.HomeStatusBarViewModelImpl$onActivated$2$1, reason: invalid class name */
        final class AnonymousClass1 extends SuspendLambda implements Function2 {
            int label;
            final /* synthetic */ HomeStatusBarViewModelImpl this$0;

            /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
            public AnonymousClass1(HomeStatusBarViewModelImpl homeStatusBarViewModelImpl, Continuation continuation) {
                super(2, continuation);
                this.this$0 = homeStatusBarViewModelImpl;
            }

            @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
            public final Continuation create(Object obj, Continuation continuation) {
                return new AnonymousClass1(this.this$0, continuation);
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
                    Hydrator hydrator = this.this$0.hydrator;
                    this.label = 1;
                    if (hydrator.activate(this) == coroutineSingletons) {
                        return coroutineSingletons;
                    }
                } else {
                    if (i != 1) {
                        throw new IllegalStateException("call to 'resume' before 'invoke' with coroutine");
                    }
                    ResultKt.throwOnFailure(obj);
                }
                throw new KotlinNothingValueException();
            }
        }

        /* renamed from: com.android.systemui.statusbar.pipeline.shared.ui.viewmodel.HomeStatusBarViewModelImpl$onActivated$2$3, reason: invalid class name */
        final class AnonymousClass3 extends SuspendLambda implements Function2 {
            int label;
            final /* synthetic */ HomeStatusBarViewModelImpl this$0;

            /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
            public AnonymousClass3(HomeStatusBarViewModelImpl homeStatusBarViewModelImpl, Continuation continuation) {
                super(2, continuation);
                this.this$0 = homeStatusBarViewModelImpl;
            }

            @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
            public final Continuation create(Object obj, Continuation continuation) {
                return new AnonymousClass3(this.this$0, continuation);
            }

            @Override // kotlin.jvm.functions.Function2
            public final Object invoke(Object obj, Object obj2) {
                return ((AnonymousClass3) create((CoroutineScope) obj, (Continuation) obj2)).invokeSuspend(Unit.INSTANCE);
            }

            @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
            public final Object invokeSuspend(Object obj) {
                CoroutineSingletons coroutineSingletons = CoroutineSingletons.COROUTINE_SUSPENDED;
                int i = this.label;
                if (i == 0) {
                    ResultKt.throwOnFailure(obj);
                    HomeStatusBarViewModelImpl homeStatusBarViewModelImpl = this.this$0;
                    StatusBarChipsUiEventLogger statusBarChipsUiEventLogger = homeStatusBarViewModelImpl.uiEventLogger;
                    this.label = 1;
                    if (statusBarChipsUiEventLogger.hydrateUiEventLogging(homeStatusBarViewModelImpl.chipsVisibilityModel, this) == coroutineSingletons) {
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

        public AnonymousClass2(Continuation continuation) {
            super(2, continuation);
        }

        @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
        public final Continuation create(Object obj, Continuation continuation) {
            AnonymousClass2 anonymousClass2 = HomeStatusBarViewModelImpl.this.new AnonymousClass2(continuation);
            anonymousClass2.L$0 = obj;
            return anonymousClass2;
        }

        @Override // kotlin.jvm.functions.Function2
        public final Object invoke(Object obj, Object obj2) {
            return ((AnonymousClass2) create((CoroutineScope) obj, (Continuation) obj2)).invokeSuspend(Unit.INSTANCE);
        }

        @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
        public final Object invokeSuspend(Object obj) {
            CoroutineSingletons coroutineSingletons = CoroutineSingletons.COROUTINE_SUSPENDED;
            int i = this.label;
            if (i == 0) {
                ResultKt.throwOnFailure(obj);
                CoroutineScope coroutineScope = (CoroutineScope) this.L$0;
                CoroutineTracingKt.launchTraced$default(coroutineScope, null, null, new AnonymousClass1(HomeStatusBarViewModelImpl.this, null), 7);
                CoroutineTracingKt.launchTraced$default(coroutineScope, null, null, new AnonymousClass3(HomeStatusBarViewModelImpl.this, null), 7);
                this.label = 1;
                if (DelayKt.awaitCancellation(this) == coroutineSingletons) {
                    return coroutineSingletons;
                }
            } else {
                if (i != 1) {
                    throw new IllegalStateException("call to 'resume' before 'invoke' with coroutine");
                }
                ResultKt.throwOnFailure(obj);
            }
            throw new KotlinNothingValueException();
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
                return factory2.create();
            }
        });
        Edge.Companion companion = Edge.Companion;
        KeyguardState keyguardState = KeyguardState.LOCKSCREEN;
        Edge.StateToState stateToStateM = KeyguardInteractor$$ExternalSyntheticOutline0.m(companion, keyguardState, KeyguardState.OCCLUDED);
        String str = KeyguardTransitionInteractor.TAG;
        Flow flowLogDiffsForTable = DiffableKt.logDiffsForTable(FlowKt.distinctUntilChanged(keyguardTransitionInteractor.isInTransition(stateToStateM, null)), orCreate, "", "Lock->Occluded", false);
        SharingStarted.Companion companion2 = SharingStarted.Companion;
        StartedWhileSubscribed startedWhileSubscribedWhileSubscribed$default = SharingStarted.Companion.WhileSubscribed$default(companion2, 3);
        Boolean bool = Boolean.FALSE;
        this.isTransitioningFromLockscreenToOccluded = FlowKt.stateIn(flowLogDiffsForTable, coroutineScope, startedWhileSubscribedWhileSubscribed$default, bool);
        final Flow flowTransition = keyguardTransitionInteractor.transition(new Edge.StateToState(keyguardState, KeyguardState.DREAMING));
        final Flow flow = new Flow() { // from class: com.android.systemui.statusbar.pipeline.shared.ui.viewmodel.HomeStatusBarViewModelImpl$special$$inlined$filter$1

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
                        if (((TransitionStep) obj).transitionState == TransitionState.STARTED) {
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
                Object objCollect = flowTransition.collect(new AnonymousClass2(flowCollector), continuation);
                return objCollect == CoroutineSingletons.COROUTINE_SUSPENDED ? objCollect : Unit.INSTANCE;
            }
        };
        this.transitionFromLockscreenToDreamStartedEvent = FlowKt.flowOn(new Flow() { // from class: com.android.systemui.statusbar.pipeline.shared.ui.viewmodel.HomeStatusBarViewModelImpl$special$$inlined$map$1

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
                        Unit unit = Unit.INSTANCE;
                        anonymousClass1.label = 1;
                        if (this.$this_unsafeFlow.emit(unit, anonymousClass1) == coroutineSingletons) {
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
        }, coroutineDispatcher);
        this.mediaProjectionStopDialogDueToCallEndedState = shareToAppChipViewModel.stopDialogToShow;
        ReadonlyStateFlow readonlyStateFlow3 = ongoingActivityChipsViewModel.primaryChip;
        this.primaryOngoingActivityChip = readonlyStateFlow3;
        this.ongoingActivityChipsLegacy = ongoingActivityChipsViewModel.chipsLegacy;
        final StateFlow anyExpansion = ((ShadeInteractorImpl) shadeInteractor).baseShadeInteractor.getAnyExpansion();
        Flow flowDistinctUntilChanged = FlowKt.distinctUntilChanged(new Flow() { // from class: com.android.systemui.statusbar.pipeline.shared.ui.viewmodel.HomeStatusBarViewModelImpl$special$$inlined$map$2

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
                        Boolean boolValueOf = Boolean.valueOf(((double) ((Number) obj).floatValue()) >= 0.2d);
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
                Object objCollect = anyExpansion.collect(new AnonymousClass2(flowCollector), continuation);
                return objCollect == CoroutineSingletons.COROUTINE_SUSPENDED ? objCollect : Unit.INSTANCE;
            }
        });
        ShadeWindowGoesAround.INSTANCE.getClass();
        if (ShadeWindowGoesAround.FLAG.isTrue()) {
            final StateFlowImpl stateFlowImpl = ((ShadeDisplaysInteractor) provider.get()).displayId;
            readonlyStateFlow = readonlyStateFlow3;
            flowKt__BuildersKt$flowOf$$inlined$unsafeFlow$2 = new Flow() { // from class: com.android.systemui.statusbar.pipeline.shared.ui.viewmodel.HomeStatusBarViewModelImpl$special$$inlined$map$3

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
                            Boolean boolValueOf = Boolean.valueOf(this.$thisDisplayId$inlined == ((Number) obj).intValue());
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
                    Object objCollect = stateFlowImpl.collect(new AnonymousClass2(flowCollector, i), continuation);
                    return objCollect == CoroutineSingletons.COROUTINE_SUSPENDED ? objCollect : Unit.INSTANCE;
                }
            };
        } else {
            readonlyStateFlow = readonlyStateFlow3;
            flowKt__BuildersKt$flowOf$$inlined$unsafeFlow$2 = new FlowKt__BuildersKt$flowOf$$inlined$unsafeFlow$2(Boolean.valueOf(i == 0));
        }
        FlowKt__ZipKt$combine$$inlined$unsafeFlow$1 flowKt__ZipKt$combine$$inlined$unsafeFlow$1 = new FlowKt__ZipKt$combine$$inlined$unsafeFlow$1(flowKt__BuildersKt$flowOf$$inlined$unsafeFlow$2, flowDistinctUntilChanged, new HomeStatusBarViewModelImpl$isShadeVisibleOnThisDisplay$1(null));
        this.isShadeVisibleOnThisDisplay = flowKt__ZipKt$combine$$inlined$unsafeFlow$1;
        DiffableKt.logDiffsForTable(FlowKt.distinctUntilChanged(FlowKt.combine(sceneInteractor.currentScene, flowKt__ZipKt$combine$$inlined$unsafeFlow$1, sceneContainerOcclusionInteractor.invisibleDueToOcclusion, new HomeStatusBarViewModelImpl$isHomeStatusBarAllowedByScene$1(null))), orCreate, "", "allowedByScene", false);
        RefactorFlagUtils refactorFlagUtils = RefactorFlagUtils.INSTANCE;
        int i2 = NotificationsLiveDataStoreRefactor.$r8$clinit;
        Flow flow2 = activeNotificationsInteractor.areAnyNotificationsPresent;
        StatusBarModePerDisplayRepository statusBarModePerDisplayRepository = (StatusBarModePerDisplayRepository) lightsOutInteractor.repository.forDisplay(i);
        Flow flow3 = (statusBarModePerDisplayRepository == null || (readonlyStateFlow2 = ((StatusBarModePerDisplayRepositoryImpl) statusBarModePerDisplayRepository).statusBarMode) == null) ? null : new Flow() { // from class: com.android.systemui.statusbar.phone.domain.interactor.LightsOutInteractor$isLowProfile$$inlined$map$1

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
                        int i3 = LightsOutInteractor.WhenMappings.$EnumSwitchMapping$0[((StatusBarMode) obj).ordinal()];
                        Boolean boolValueOf = Boolean.valueOf(i3 == 1 || i3 == 2);
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
                Object objCollect = readonlyStateFlow2.collect(new AnonymousClass2(flowCollector), continuation);
                return objCollect == CoroutineSingletons.COROUTINE_SUSPENDED ? objCollect : Unit.INSTANCE;
            }
        };
        this.areNotificationsLightsOut = FlowKt.flowOn(DiffableKt.logDiffsForTable(FlowKt.distinctUntilChanged(new FlowKt__ZipKt$combine$$inlined$unsafeFlow$1(flow2, flow3 == null ? new FlowKt__BuildersKt$flowOf$$inlined$unsafeFlow$2(bool) : flow3, new HomeStatusBarViewModelImpl$areNotificationsLightsOut$1(null))), orCreate, "", "notifLightsOut", false), coroutineDispatcher);
        final StateFlow stateFlowDarkState = ((DarkIconRepositoryImpl) darkIconInteractor.repository).darkState(i);
        final Flow flow4 = new Flow() { // from class: com.android.systemui.statusbar.phone.domain.interactor.DarkIconInteractor$darkState$$inlined$map$1

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
                        SysuiDarkIconDispatcher$DarkChange sysuiDarkIconDispatcher$DarkChange = (SysuiDarkIconDispatcher$DarkChange) obj;
                        DarkState darkState = new DarkState(sysuiDarkIconDispatcher$DarkChange.areas, sysuiDarkIconDispatcher$DarkChange.tint, sysuiDarkIconDispatcher$DarkChange.darkIntensity);
                        anonymousClass1.label = 1;
                        if (this.$this_unsafeFlow.emit(darkState, anonymousClass1) == coroutineSingletons) {
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
                Object objCollect = stateFlowDarkState.collect(new AnonymousClass2(flowCollector), continuation);
                return objCollect == CoroutineSingletons.COROUTINE_SUSPENDED ? objCollect : Unit.INSTANCE;
            }
        };
        FlowKt.flowOn(FlowKt.distinctUntilChanged(FlowKt.buffer$default(new Flow() { // from class: com.android.systemui.statusbar.pipeline.shared.ui.viewmodel.HomeStatusBarViewModelImpl$special$$inlined$map$5

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
                        DarkState darkState = (DarkState) obj;
                        final Collection collection = darkState.areas;
                        final int i3 = darkState.tint;
                        Object obj3 = 
                        /*  JADX ERROR: Method code generation error
                            jadx.core.utils.exceptions.CodegenException: Error generate insn: 0x003a: CONSTRUCTOR (r2v1 'obj3' java.lang.Object) = (r6v2 'collection' java.util.Collection A[DONT_INLINE]), (r5v2 'i3' int A[DONT_INLINE]) A[DECLARE_VAR, MD:(java.util.Collection<android.graphics.Rect>, int):void (m)] (LINE:59) call: com.android.systemui.statusbar.pipeline.shared.ui.viewmodel.HomeStatusBarViewModelImpl$areaTint$1$1.<init>(java.util.Collection, int):void type: CONSTRUCTOR in method: com.android.systemui.statusbar.pipeline.shared.ui.viewmodel.HomeStatusBarViewModelImpl$special$$inlined$map$5.2.emit(java.lang.Object, kotlin.coroutines.Continuation):java.lang.Object, file: classes3.dex
                            	at jadx.core.codegen.InsnGen.makeInsn(InsnGen.java:310)
                            	at jadx.core.codegen.InsnGen.makeInsn(InsnGen.java:273)
                            	at jadx.core.codegen.RegionGen.makeSimpleBlock(RegionGen.java:94)
                            	at jadx.core.dex.nodes.IBlock.generate(IBlock.java:15)
                            	at jadx.core.codegen.RegionGen.makeRegion(RegionGen.java:66)
                            	at jadx.core.dex.regions.Region.generate(Region.java:35)
                            	at jadx.core.codegen.RegionGen.makeRegion(RegionGen.java:66)
                            	at jadx.core.codegen.RegionGen.makeRegionIndent(RegionGen.java:83)
                            	at jadx.core.codegen.RegionGen.makeIf(RegionGen.java:126)
                            	at jadx.core.dex.regions.conditions.IfRegion.generate(IfRegion.java:90)
                            	at jadx.core.codegen.RegionGen.makeRegion(RegionGen.java:66)
                            	at jadx.core.dex.regions.Region.generate(Region.java:35)
                            	at jadx.core.codegen.RegionGen.makeRegion(RegionGen.java:66)
                            	at jadx.core.codegen.MethodGen.addRegionInsns(MethodGen.java:298)
                            	at jadx.core.codegen.MethodGen.addInstructions(MethodGen.java:277)
                            	at jadx.core.codegen.ClassGen.addMethodCode(ClassGen.java:410)
                            	at jadx.core.codegen.ClassGen.addMethod(ClassGen.java:335)
                            	at jadx.core.codegen.ClassGen.lambda$addInnerClsAndMethods$3(ClassGen.java:301)
                            	at java.base/java.util.stream.ForEachOps$ForEachOp$OfRef.accept(ForEachOps.java:184)
                            	at java.base/java.util.ArrayList.forEach(ArrayList.java:1596)
                            	at java.base/java.util.stream.SortedOps$RefSortingSink.end(SortedOps.java:395)
                            	at java.base/java.util.stream.Sink$ChainedReference.end(Sink.java:261)
                            Caused by: jadx.core.utils.exceptions.JadxRuntimeException: Expected class to be processed at this point, class: com.android.systemui.statusbar.pipeline.shared.ui.viewmodel.HomeStatusBarViewModelImpl$areaTint$1$1, state: NOT_LOADED
                            	at jadx.core.dex.nodes.ClassNode.ensureProcessed(ClassNode.java:304)
                            	at jadx.core.codegen.InsnGen.inlineAnonymousConstructor(InsnGen.java:807)
                            	at jadx.core.codegen.InsnGen.makeConstructor(InsnGen.java:730)
                            	at jadx.core.codegen.InsnGen.makeInsnBody(InsnGen.java:418)
                            	at jadx.core.codegen.InsnGen.makeInsn(InsnGen.java:303)
                            	... 21 more
                            */
                        /*
                            this = this;
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
                    Object objCollect = flow4.collect(new AnonymousClass2(flowCollector), continuation);
                    return objCollect == CoroutineSingletons.COROUTINE_SUSPENDED ? objCollect : Unit.INSTANCE;
                }
            }, -1, 2)), coroutineDispatcher);
            HomeStatusBarViewModelImpl$areaDark$2 homeStatusBarViewModelImpl$areaDark$2 = new Object() { // from class: com.android.systemui.statusbar.pipeline.shared.ui.viewmodel.HomeStatusBarViewModelImpl$areaDark$2
            };
            final StateFlow stateFlowDarkState2 = ((DarkIconRepositoryImpl) darkIconInteractor.repository).darkState(i);
            DarkIconInteractor.Companion.getClass();
            final Flow flowDistinctUntilChanged2 = FlowKt.distinctUntilChanged(new Flow() { // from class: com.android.systemui.statusbar.phone.domain.interactor.DarkIconInteractor$Companion$toIsAreaDark$$inlined$map$1

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
                            SysuiDarkIconDispatcher$DarkChange sysuiDarkIconDispatcher$DarkChange = (SysuiDarkIconDispatcher$DarkChange) obj;
                            DarkStateWithoutIntensity darkStateWithoutIntensity = new DarkStateWithoutIntensity(sysuiDarkIconDispatcher$DarkChange.areas, sysuiDarkIconDispatcher$DarkChange.darkIntensity < 0.5f);
                            anonymousClass1.label = 1;
                            if (this.$this_unsafeFlow.emit(darkStateWithoutIntensity, anonymousClass1) == coroutineSingletons) {
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
                    Object objCollect = stateFlowDarkState2.collect(new AnonymousClass2(flowCollector), continuation);
                    return objCollect == CoroutineSingletons.COROUTINE_SUSPENDED ? objCollect : Unit.INSTANCE;
                }
            });
            this.areaDark$delegate = hydrator.hydratedStateOf("areaDark", homeStatusBarViewModelImpl$areaDark$2, FlowKt.distinctUntilChanged(FlowKt.buffer$default(new Flow() { // from class: com.android.systemui.statusbar.phone.domain.interactor.DarkIconInteractor$Companion$toIsAreaDark$$inlined$map$2

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
                            final DarkStateWithoutIntensity darkStateWithoutIntensity = (DarkStateWithoutIntensity) obj;
                            Object obj3 = 
                            /*  JADX ERROR: Method code generation error
                                jadx.core.utils.exceptions.CodegenException: Error generate insn: 0x0036: CONSTRUCTOR (r6v2 'obj3' java.lang.Object) = 
                                  (r5v1 'darkStateWithoutIntensity' com.android.systemui.statusbar.phone.domain.interactor.DarkStateWithoutIntensity A[DONT_INLINE])
                                 A[DECLARE_VAR, MD:(com.android.systemui.statusbar.phone.domain.interactor.DarkStateWithoutIntensity):void (m)] (LINE:55) call: com.android.systemui.statusbar.phone.domain.interactor.DarkIconInteractor$Companion$toIsAreaDark$2$1.<init>(com.android.systemui.statusbar.phone.domain.interactor.DarkStateWithoutIntensity):void type: CONSTRUCTOR in method: com.android.systemui.statusbar.phone.domain.interactor.DarkIconInteractor$Companion$toIsAreaDark$$inlined$map$2.2.emit(java.lang.Object, kotlin.coroutines.Continuation):java.lang.Object, file: classes3.dex
                                	at jadx.core.codegen.InsnGen.makeInsn(InsnGen.java:310)
                                	at jadx.core.codegen.InsnGen.makeInsn(InsnGen.java:273)
                                	at jadx.core.codegen.RegionGen.makeSimpleBlock(RegionGen.java:94)
                                	at jadx.core.dex.nodes.IBlock.generate(IBlock.java:15)
                                	at jadx.core.codegen.RegionGen.makeRegion(RegionGen.java:66)
                                	at jadx.core.dex.regions.Region.generate(Region.java:35)
                                	at jadx.core.codegen.RegionGen.makeRegion(RegionGen.java:66)
                                	at jadx.core.codegen.RegionGen.makeRegionIndent(RegionGen.java:83)
                                	at jadx.core.codegen.RegionGen.makeIf(RegionGen.java:126)
                                	at jadx.core.dex.regions.conditions.IfRegion.generate(IfRegion.java:90)
                                	at jadx.core.codegen.RegionGen.makeRegion(RegionGen.java:66)
                                	at jadx.core.dex.regions.Region.generate(Region.java:35)
                                	at jadx.core.codegen.RegionGen.makeRegion(RegionGen.java:66)
                                	at jadx.core.codegen.MethodGen.addRegionInsns(MethodGen.java:298)
                                	at jadx.core.codegen.MethodGen.addInstructions(MethodGen.java:277)
                                	at jadx.core.codegen.ClassGen.addMethodCode(ClassGen.java:410)
                                	at jadx.core.codegen.ClassGen.addMethod(ClassGen.java:335)
                                	at jadx.core.codegen.ClassGen.lambda$addInnerClsAndMethods$3(ClassGen.java:301)
                                	at java.base/java.util.stream.ForEachOps$ForEachOp$OfRef.accept(ForEachOps.java:184)
                                	at java.base/java.util.ArrayList.forEach(ArrayList.java:1596)
                                	at java.base/java.util.stream.SortedOps$RefSortingSink.end(SortedOps.java:395)
                                	at java.base/java.util.stream.Sink$ChainedReference.end(Sink.java:261)
                                Caused by: jadx.core.utils.exceptions.JadxRuntimeException: Expected class to be processed at this point, class: com.android.systemui.statusbar.phone.domain.interactor.DarkIconInteractor$Companion$toIsAreaDark$2$1, state: NOT_LOADED
                                	at jadx.core.dex.nodes.ClassNode.ensureProcessed(ClassNode.java:304)
                                	at jadx.core.codegen.InsnGen.inlineAnonymousConstructor(InsnGen.java:807)
                                	at jadx.core.codegen.InsnGen.makeConstructor(InsnGen.java:730)
                                	at jadx.core.codegen.InsnGen.makeInsnBody(InsnGen.java:418)
                                	at jadx.core.codegen.InsnGen.makeInsn(InsnGen.java:303)
                                	... 21 more
                                */
                            /*
                                this = this;
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
                        Object objCollect = flowDistinctUntilChanged2.collect(new AnonymousClass2(flowCollector), continuation);
                        return objCollect == CoroutineSingletons.COROUTINE_SUSPENDED ? objCollect : Unit.INSTANCE;
                    }
                }, -1, 2)));
                FlowKt__ZipKt$combine$$inlined$unsafeFlow$1 flowKt__ZipKt$combine$$inlined$unsafeFlow$12 = new FlowKt__ZipKt$combine$$inlined$unsafeFlow$1(keyguardTransitionInteractor.currentKeyguardState, flowKt__ZipKt$combine$$inlined$unsafeFlow$1, new HomeStatusBarViewModelImpl$isHomeScreenStatusBarAllowedLegacy$1(null));
                this.isHomeScreenStatusBarAllowedLegacy = flowKt__ZipKt$combine$$inlined$unsafeFlow$12;
                this.isHomeStatusBarAllowedCompat = flowKt__ZipKt$combine$$inlined$unsafeFlow$12;
                FlowTracing flowTracing = FlowTracing.INSTANCE;
                ReadonlyStateFlow readonlyStateFlowStateIn = FlowKt.stateIn(new FlowKt__TransformKt$onEach$$inlined$unsafeTransform$1(flowKt__ZipKt$combine$$inlined$unsafeFlow$12, new HomeStatusBarViewModelImpl$special$$inlined$traceEach$default$1(new TraceStateLogger(TrackGroupUtils.trackGroup(PluginLockStar.STATUS_BAR_TYPE, "isHomeStatusBarAllowed"), false, false, true, 6, null), null)), coroutineScope, SharingStarted.Companion.WhileSubscribed$default(companion2, 3), bool);
                this.isHomeStatusBarAllowed = readonlyStateFlowStateIn;
                Flow flowFlowOn = FlowKt.flowOn(DiffableKt.logDiffsForTable(FlowKt.distinctUntilChanged(FlowKt.combine(readonlyStateFlowStateIn, keyguardInteractor.isSecureCameraActive, headsUpNotificationInteractor.statusBarHeadsUpStatus, new HomeStatusBarViewModelImpl$shouldHomeStatusBarBeVisible$1(null))), orCreate, "", "visible", false), coroutineDispatcher);
                final HeadsUpNotificationInteractor$special$$inlined$map$2 headsUpNotificationInteractor$special$$inlined$map$2 = headsUpNotificationInteractor.statusBarHeadsUpStatus;
                ?? r7 = new Flow() { // from class: com.android.systemui.statusbar.pipeline.shared.ui.viewmodel.HomeStatusBarViewModelImpl$special$$inlined$map$6

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
                                Boolean boolValueOf = Boolean.valueOf(((PinnedStatus) obj) == PinnedStatus.PinnedBySystem);
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
                        Object objCollect = headsUpNotificationInteractor$special$$inlined$map$2.collect(new AnonymousClass2(flowCollector), continuation);
                        return objCollect == CoroutineSingletons.COROUTINE_SUSPENDED ? objCollect : Unit.INSTANCE;
                    }
                };
                this.hideStartSideContentForHeadsUp = r7;
                this.shouldShowOperatorNameView = FlowKt.flowOn(DiffableKt.logDiffsForTable(FlowKt.distinctUntilChanged(FlowKt.combine(flowFlowOn, r7, homeStatusBarInteractor.visibilityViaDisableFlags, homeStatusBarInteractor.shouldShowOperatorName, new HomeStatusBarViewModelImpl$shouldShowOperatorNameView$1(null))), orCreate, "", "showOperatorName", false), coroutineDispatcher);
                FlowKt__ZipKt$combine$$inlined$combineUnsafe$FlowKt__ZipKt$1 flowKt__ZipKt$combine$$inlined$combineUnsafe$FlowKt__ZipKt$1Combine = FlowKt.combine(readonlyStateFlowStateIn, keyguardInteractor.isSecureCameraActive, r7, new HomeStatusBarViewModelImpl$canShowOngoingActivityChips$1(null));
                this.canShowOngoingActivityChips = flowKt__ZipKt$combine$$inlined$combineUnsafe$FlowKt__ZipKt$1Combine;
                FlowKt__TransformKt$onEach$$inlined$unsafeTransform$1 flowKt__TransformKt$onEach$$inlined$unsafeTransform$1 = new FlowKt__TransformKt$onEach$$inlined$unsafeTransform$1(new FlowKt__ZipKt$combine$$inlined$unsafeFlow$1(ongoingActivityChipsViewModel.chips, flowKt__ZipKt$combine$$inlined$combineUnsafe$FlowKt__ZipKt$1Combine, new HomeStatusBarViewModelImpl$chipsVisibilityModel$1(null)), new HomeStatusBarViewModelImpl$special$$inlined$traceEach$default$2(new TraceStateLogger(TrackGroupUtils.trackGroup(PluginLockStar.STATUS_BAR_TYPE, "chips"), false, false, true, 6, null), null));
                this.chipsVisibilityModel = flowKt__TransformKt$onEach$$inlined$unsafeTransform$1;
                this.ongoingActivityChips$delegate = hydrator.hydratedStateOf("ongoingActivityChips", new ChipsVisibilityModel(new MultipleOngoingActivityChipsModel(null, null, null, 7, null), false), flowKt__TransformKt$onEach$$inlined$unsafeTransform$1);
                final ReadonlyStateFlow readonlyStateFlow4 = readonlyStateFlow;
                ?? r2 = new Flow() { // from class: com.android.systemui.statusbar.pipeline.shared.ui.viewmodel.HomeStatusBarViewModelImpl$special$$inlined$map$9

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
                                Boolean boolValueOf = Boolean.valueOf(((OngoingActivityChipModel) obj) instanceof OngoingActivityChipModel.Active);
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
                        Object objCollect = readonlyStateFlow4.collect(new AnonymousClass2(flowCollector), continuation);
                        return objCollect == CoroutineSingletons.COROUTINE_SUSPENDED ? objCollect : Unit.INSTANCE;
                    }
                };
                this.hasOngoingActivityChips = r2;
                FlowKt__ZipKt$combine$$inlined$unsafeFlow$1 flowKt__ZipKt$combine$$inlined$unsafeFlow$13 = new FlowKt__ZipKt$combine$$inlined$unsafeFlow$1(r2, flowKt__ZipKt$combine$$inlined$combineUnsafe$FlowKt__ZipKt$1Combine, new HomeStatusBarViewModelImpl$isAnyChipVisible$1(null));
                this.isAnyChipVisible = flowKt__ZipKt$combine$$inlined$unsafeFlow$13;
                HomeStatusBarViewModelImpl$isClockVisible$1 homeStatusBarViewModelImpl$isClockVisible$1 = new HomeStatusBarViewModelImpl$isClockVisible$1(this, null);
                HomeStatusBarInteractor$special$$inlined$map$1 homeStatusBarInteractor$special$$inlined$map$1 = homeStatusBarInteractor.visibilityViaDisableFlags;
                this.isClockVisible = FlowKt.flowOn(DiffableKt.logDiffsForTable(FlowKt.distinctUntilChanged(FlowKt.combine(flowFlowOn, r7, homeStatusBarInteractor$special$$inlined$map$1, homeStatusBarViewModelImpl$isClockVisible$1)), orCreate, SubRoom.EXTRA_VALUE_CLOCK, new VisibilityModel(4, false)), coroutineDispatcher);
                this.isNotificationIconContainerVisible = FlowKt.flowOn(DiffableKt.logDiffsForTable(FlowKt.distinctUntilChanged(FlowKt.combine(flowFlowOn, flowKt__ZipKt$combine$$inlined$unsafeFlow$13, homeStatusBarInteractor$special$$inlined$map$1, new HomeStatusBarViewModelImpl$isNotificationIconContainerVisible$1(this, null))), orCreate, "notifContainer", new VisibilityModel(4, false)), coroutineDispatcher);
                FlowKt__ZipKt$combine$$inlined$unsafeFlow$1 flowKt__ZipKt$combine$$inlined$unsafeFlow$14 = new FlowKt__ZipKt$combine$$inlined$unsafeFlow$1(flowFlowOn, homeStatusBarInteractor$special$$inlined$map$1, new HomeStatusBarViewModelImpl$isSystemInfoVisible$1(this, null));
                this.isSystemInfoVisible = flowKt__ZipKt$combine$$inlined$unsafeFlow$14;
                Flow flowDistinctUntilChanged3 = FlowKt.distinctUntilChanged(new FlowKt__ZipKt$combine$$inlined$unsafeFlow$1(flowKt__ZipKt$combine$$inlined$unsafeFlow$14, systemStatusEventAnimationInteractor.animationState, new HomeStatusBarViewModelImpl$systemInfoCombinedVis$1(null)));
                VisibilityModel visibilityModel = new VisibilityModel(0, false);
                SystemEventAnimationState systemEventAnimationState = SystemEventAnimationState.Idle;
                this.systemInfoCombinedVis = FlowKt.stateIn(DiffableKt.logDiffsForTable(flowDistinctUntilChanged3, orCreate, "systemInfo", new SystemInfoCombinedVisibilityModel(visibilityModel, systemEventAnimationState)), coroutineScope, SharingStarted.Companion.WhileSubscribed$default(companion2, 3), new SystemInfoCombinedVisibilityModel(new VisibilityModel(0, false), systemEventAnimationState));
                this.iconBlockList = FlowKt.flowOn(homeStatusBarIconBlockListInteractor.iconBlockList, coroutineDispatcher);
                StatusBarContentInsetsViewModel statusBarContentInsetsViewModel = (StatusBarContentInsetsViewModel) statusBarContentInsetsViewModelStore.forDisplay(i);
                if (statusBarContentInsetsViewModel == null || statusBarContentInsetsViewModel.contentArea == null) {
                    FlowKt.flowOn(new FlowKt__BuildersKt$flowOf$$inlined$unsafeFlow$2(new Rect(0, 0, 0, 0)), coroutineDispatcher);
                }
            }

            /* JADX WARN: Removed duplicated region for block: B:7:0x0013  */
            @Override // com.android.systemui.lifecycle.ExclusiveActivatable
            /*
                Code decompiled incorrectly, please refer to instructions dump.
            */
            public final Object onActivated(Continuation continuation) {
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
                Object obj = anonymousClass1.result;
                CoroutineSingletons coroutineSingletons = CoroutineSingletons.COROUTINE_SUSPENDED;
                int i2 = anonymousClass1.label;
                if (i2 == 0) {
                    ResultKt.throwOnFailure(obj);
                    AnonymousClass2 anonymousClass2 = new AnonymousClass2(null);
                    anonymousClass1.label = 1;
                    if (CoroutineScopeKt.coroutineScope(anonymousClass2, anonymousClass1) == coroutineSingletons) {
                        return coroutineSingletons;
                    }
                } else {
                    if (i2 != 1) {
                        throw new IllegalStateException("call to 'resume' before 'invoke' with coroutine");
                    }
                    ResultKt.throwOnFailure(obj);
                }
                throw new KotlinNothingValueException();
            }
        }
