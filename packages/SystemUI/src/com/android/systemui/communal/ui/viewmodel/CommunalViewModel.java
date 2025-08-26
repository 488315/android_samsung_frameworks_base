package com.android.systemui.communal.ui.viewmodel;

import android.content.ComponentName;
import android.content.Intent;
import android.content.res.Resources;
import com.android.app.tracing.coroutines.CoroutineTracingKt;
import com.android.compose.animation.scene.SceneKey;
import com.android.keyguard.logging.KeyguardUpdateMonitorLogger$$ExternalSyntheticOutline0;
import com.android.systemui.R;
import com.android.systemui.communal.data.repository.CommunalPrefsRepositoryImpl;
import com.android.systemui.communal.domain.interactor.CommunalInteractor;
import com.android.systemui.communal.domain.interactor.CommunalPrefsInteractor;
import com.android.systemui.communal.domain.interactor.CommunalSceneInteractor;
import com.android.systemui.communal.domain.interactor.CommunalSettingsInteractor;
import com.android.systemui.communal.domain.interactor.CommunalTutorialInteractor;
import com.android.systemui.communal.shared.log.CommunalMetricsLogger;
import com.android.systemui.communal.shared.log.CommunalStatsLogProxyImpl;
import com.android.systemui.communal.shared.model.EditModeState;
import com.android.systemui.communal.ui.viewmodel.PopupType;
import com.android.systemui.communal.widgets.EditWidgetsActivity;
import com.android.systemui.communal.widgets.EditWidgetsActivityStarterImpl;
import com.android.systemui.keyguard.domain.interactor.KeyguardInteractor;
import com.android.systemui.keyguard.domain.interactor.KeyguardTransitionInteractor;
import com.android.systemui.keyguard.shared.model.KeyguardState;
import com.android.systemui.keyguard.ui.transitions.BlurConfig;
import com.android.systemui.log.LogBuffer;
import com.android.systemui.log.core.Logger;
import com.android.systemui.media.controls.ui.controller.MediaCarouselController;
import com.android.systemui.media.controls.ui.view.MediaHost;
import com.android.systemui.media.controls.ui.view.MediaHost$$ExternalSyntheticLambda0;
import com.android.systemui.scene.shared.model.Scenes;
import com.android.systemui.settings.UserTrackerImpl;
import com.android.systemui.shade.domain.interactor.ShadeInteractor;
import com.android.systemui.shade.domain.interactor.ShadeInteractorImpl;
import com.android.systemui.statusbar.KeyguardIndicationController;
import com.android.systemui.util.kotlin.BooleanFlowOperators;
import com.android.systemui.utils.coroutines.flow.FlowConflatedKt;
import com.android.systemui.utils.coroutines.flow.LatestConflatedKt;
import java.util.List;
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
import kotlin.jvm.functions.Function4;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlinx.coroutines.CoroutineDispatcher;
import kotlinx.coroutines.CoroutineScope;
import kotlinx.coroutines.DelayKt;
import kotlinx.coroutines.StandaloneCoroutine;
import kotlinx.coroutines.flow.Flow;
import kotlinx.coroutines.flow.FlowCollector;
import kotlinx.coroutines.flow.FlowKt;
import kotlinx.coroutines.flow.FlowKt__EmittersKt$onStart$$inlined$unsafeFlow$1;
import kotlinx.coroutines.flow.FlowKt__TransformKt$onEach$$inlined$unsafeTransform$1;
import kotlinx.coroutines.flow.FlowKt__ZipKt$combine$$inlined$unsafeFlow$1;
import kotlinx.coroutines.flow.ReadonlyStateFlow;
import kotlinx.coroutines.flow.SharingStarted;
import kotlinx.coroutines.flow.StateFlowImpl;
import kotlinx.coroutines.flow.StateFlowKt;
import kotlinx.coroutines.flow.internal.ChannelFlowTransformLatest;

/* loaded from: classes2.dex */
public final class CommunalViewModel extends BaseCommunalViewModel {
    public static final /* synthetic */ int $r8$clinit = 0;
    public final StateFlowImpl _currentPopup;
    public final StateFlowImpl _isEnableWidgetDialogShowing;
    public final StateFlowImpl _isEnableWorkProfileDialogShowing;
    public final float blurRadiusPx;
    public final Flow communalBackground;
    public final FlowKt__TransformKt$onEach$$inlined$unsafeTransform$1 communalContent;
    public final CommunalInteractor communalInteractor;
    public final CommunalSettingsInteractor communalSettingsInteractor;
    public final ReadonlyStateFlow currentPopup;
    public StandaloneCoroutine delayedHideCurrentPopupJob;
    public List frozenCommunalContent;
    public final FlowKt__TransformKt$onEach$$inlined$unsafeTransform$1 isCommunalContentFlowFrozen;
    public final StateFlowImpl isCommunalContentVisible;
    public final FlowKt__TransformKt$onEach$$inlined$unsafeTransform$1 isEmptyState;
    public final ReadonlyStateFlow isEnableWidgetDialogShowing;
    public final ReadonlyStateFlow isEnableWorkProfileDialogShowing;
    public final Flow isFocusable;
    public final StateFlowImpl isUiBlurred;
    public final KeyguardIndicationController keyguardIndicationController;
    public final FlowKt__TransformKt$onEach$$inlined$unsafeTransform$1 latestCommunalContent;
    public final Logger logger;
    public final CommunalMetricsLogger metricsLogger;
    public final ChannelFlowTransformLatest ongoingContent;
    public final CoroutineScope scope;
    public final ShadeInteractor shadeInteractor;
    public final Flow swipeFromHubInLandscape;
    public final boolean swipeToHub;
    public final Lazy swipeToHubEnabled$delegate;
    public final ReadonlyStateFlow touchesAllowed;

    public final class Companion {
        public /* synthetic */ Companion(DefaultConstructorMarker defaultConstructorMarker) {
            this();
        }

        private Companion() {
        }
    }

    /* renamed from: com.android.systemui.communal.ui.viewmodel.CommunalViewModel$isEmptyState$2, reason: invalid class name */
    final class AnonymousClass2 extends SuspendLambda implements Function2 {
        /* synthetic */ boolean Z$0;
        int label;

        public AnonymousClass2(Continuation continuation) {
            super(2, continuation);
        }

        @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
        public final Continuation create(Object obj, Continuation continuation) {
            AnonymousClass2 anonymousClass2 = CommunalViewModel.this.new AnonymousClass2(continuation);
            anonymousClass2.Z$0 = ((Boolean) obj).booleanValue();
            return anonymousClass2;
        }

        @Override // kotlin.jvm.functions.Function2
        public final Object invoke(Object obj, Object obj2) {
            Boolean bool = (Boolean) obj;
            bool.booleanValue();
            return ((AnonymousClass2) create(bool, (Continuation) obj2)).invokeSuspend(Unit.INSTANCE);
        }

        @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
        public final Object invokeSuspend(Object obj) {
            CoroutineSingletons coroutineSingletons = CoroutineSingletons.COROUTINE_SUSPENDED;
            if (this.label != 0) {
                throw new IllegalStateException("call to 'resume' before 'invoke' with coroutine");
            }
            ResultKt.throwOnFailure(obj);
            Logger.d$default(CommunalViewModel.this.logger, KeyguardUpdateMonitorLogger$$ExternalSyntheticOutline0.m("isEmptyState: ", this.Z$0), null, 2, null);
            return Unit.INSTANCE;
        }
    }

    /* renamed from: com.android.systemui.communal.ui.viewmodel.CommunalViewModel$isFocusable$1, reason: invalid class name */
    final class AnonymousClass1 extends SuspendLambda implements Function4 {
        /* synthetic */ boolean Z$0;
        /* synthetic */ boolean Z$1;
        /* synthetic */ boolean Z$2;
        int label;

        public AnonymousClass1(Continuation continuation) {
            super(4, continuation);
        }

        @Override // kotlin.jvm.functions.Function4
        public final Object invoke(Object obj, Object obj2, Object obj3, Object obj4) {
            boolean zBooleanValue = ((Boolean) obj).booleanValue();
            boolean zBooleanValue2 = ((Boolean) obj2).booleanValue();
            boolean zBooleanValue3 = ((Boolean) obj3).booleanValue();
            AnonymousClass1 anonymousClass1 = new AnonymousClass1((Continuation) obj4);
            anonymousClass1.Z$0 = zBooleanValue;
            anonymousClass1.Z$1 = zBooleanValue2;
            anonymousClass1.Z$2 = zBooleanValue3;
            return anonymousClass1.invokeSuspend(Unit.INSTANCE);
        }

        @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
        public final Object invokeSuspend(Object obj) {
            CoroutineSingletons coroutineSingletons = CoroutineSingletons.COROUTINE_SUSPENDED;
            if (this.label != 0) {
                throw new IllegalStateException("call to 'resume' before 'invoke' with coroutine");
            }
            ResultKt.throwOnFailure(obj);
            return Boolean.valueOf(this.Z$0 && this.Z$1 && !this.Z$2);
        }
    }

    /* renamed from: com.android.systemui.communal.ui.viewmodel.CommunalViewModel$onDismissCtaTile$1, reason: invalid class name and case insensitive filesystem */
    final class C08431 extends SuspendLambda implements Function2 {
        int label;

        public C08431(Continuation continuation) {
            super(2, continuation);
        }

        @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
        public final Continuation create(Object obj, Continuation continuation) {
            return CommunalViewModel.this.new C08431(continuation);
        }

        @Override // kotlin.jvm.functions.Function2
        public final Object invoke(Object obj, Object obj2) {
            return ((C08431) create((CoroutineScope) obj, (Continuation) obj2)).invokeSuspend(Unit.INSTANCE);
        }

        @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
        public final Object invokeSuspend(Object obj) {
            CoroutineSingletons coroutineSingletons = CoroutineSingletons.COROUTINE_SUSPENDED;
            int i = this.label;
            if (i == 0) {
                ResultKt.throwOnFailure(obj);
                CommunalInteractor communalInteractor = CommunalViewModel.this.communalInteractor;
                this.label = 1;
                CommunalPrefsInteractor communalPrefsInteractor = communalInteractor.communalPrefsInteractor;
                Object booleanKeyValueForUser = ((CommunalPrefsRepositoryImpl) communalPrefsInteractor.repository).setBooleanKeyValueForUser(((UserTrackerImpl) communalPrefsInteractor.userTracker).getUserInfo(), "cta_dismissed", "Dismissed CTA tile", this);
                if (booleanKeyValueForUser != coroutineSingletons) {
                    booleanKeyValueForUser = Unit.INSTANCE;
                }
                if (booleanKeyValueForUser != coroutineSingletons) {
                    booleanKeyValueForUser = Unit.INSTANCE;
                }
                if (booleanKeyValueForUser != coroutineSingletons) {
                    booleanKeyValueForUser = Unit.INSTANCE;
                }
                if (booleanKeyValueForUser == coroutineSingletons) {
                    return coroutineSingletons;
                }
            } else {
                if (i != 1) {
                    throw new IllegalStateException("call to 'resume' before 'invoke' with coroutine");
                }
                ResultKt.throwOnFailure(obj);
            }
            CommunalViewModel communalViewModel = CommunalViewModel.this;
            PopupType.CtaTile ctaTile = PopupType.CtaTile.INSTANCE;
            int i2 = CommunalViewModel.$r8$clinit;
            communalViewModel.setCurrentPopupType(ctaTile);
            return Unit.INSTANCE;
        }
    }

    /* renamed from: com.android.systemui.communal.ui.viewmodel.CommunalViewModel$setCurrentPopupType$1, reason: invalid class name and case insensitive filesystem */
    final class C08441 extends SuspendLambda implements Function2 {
        int label;

        public C08441(Continuation continuation) {
            super(2, continuation);
        }

        @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
        public final Continuation create(Object obj, Continuation continuation) {
            return CommunalViewModel.this.new C08441(continuation);
        }

        @Override // kotlin.jvm.functions.Function2
        public final Object invoke(Object obj, Object obj2) {
            return ((C08441) create((CoroutineScope) obj, (Continuation) obj2)).invokeSuspend(Unit.INSTANCE);
        }

        @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
        public final Object invokeSuspend(Object obj) {
            CoroutineSingletons coroutineSingletons = CoroutineSingletons.COROUTINE_SUSPENDED;
            int i = this.label;
            if (i == 0) {
                ResultKt.throwOnFailure(obj);
                this.label = 1;
                if (DelayKt.delay(12000L, this) == coroutineSingletons) {
                    return coroutineSingletons;
                }
            } else {
                if (i != 1) {
                    throw new IllegalStateException("call to 'resume' before 'invoke' with coroutine");
                }
                ResultKt.throwOnFailure(obj);
            }
            CommunalViewModel communalViewModel = CommunalViewModel.this;
            int i2 = CommunalViewModel.$r8$clinit;
            communalViewModel.setCurrentPopupType(null);
            return Unit.INSTANCE;
        }
    }

    static {
        new Companion(null);
    }

    public CommunalViewModel(CoroutineDispatcher coroutineDispatcher, CoroutineScope coroutineScope, CoroutineScope coroutineScope2, final KeyguardTransitionInteractor keyguardTransitionInteractor, KeyguardInteractor keyguardInteractor, KeyguardIndicationController keyguardIndicationController, CommunalSceneInteractor communalSceneInteractor, CommunalInteractor communalInteractor, CommunalSettingsInteractor communalSettingsInteractor, CommunalTutorialInteractor communalTutorialInteractor, ShadeInteractor shadeInteractor, MediaHost mediaHost, LogBuffer logBuffer, CommunalMetricsLogger communalMetricsLogger, MediaCarouselController mediaCarouselController, BlurConfig blurConfig, boolean z) {
        super(communalSceneInteractor, communalInteractor, mediaHost, mediaCarouselController);
        this.scope = coroutineScope;
        this.keyguardIndicationController = keyguardIndicationController;
        this.communalInteractor = communalInteractor;
        this.communalSettingsInteractor = communalSettingsInteractor;
        this.shadeInteractor = shadeInteractor;
        this.metricsLogger = communalMetricsLogger;
        this.swipeToHub = z;
        this.logger = new Logger(logBuffer, "CommunalViewModel");
        this.ongoingContent = FlowKt.transformLatest(FlowKt.flowOn(new FlowKt__TransformKt$onEach$$inlined$unsafeTransform$1(FlowKt.distinctUntilChanged(new FlowKt__EmittersKt$onStart$$inlined$unsafeFlow$1(new CommunalViewModel$isMediaHostVisible$2(mediaHost, null), FlowConflatedKt.conflatedCallbackFlow(new CommunalViewModel$isMediaHostVisible$1(mediaHost, null)))), new CommunalViewModel$isMediaHostVisible$3(this, null)), coroutineDispatcher), new CommunalViewModel$special$$inlined$flatMapLatest$1(null, this, mediaHost));
        this.latestCommunalContent = new FlowKt__TransformKt$onEach$$inlined$unsafeTransform$1(FlowKt.transformLatest(communalTutorialInteractor.isTutorialAvailable, new CommunalViewModel$special$$inlined$flatMapLatest$2(null, this)), new CommunalViewModel$latestCommunalContent$2(this, null));
        this.isCommunalContentVisible = StateFlowKt.MutableStateFlow(Boolean.TRUE);
        BooleanFlowOperators booleanFlowOperators = BooleanFlowOperators.INSTANCE;
        SceneKey sceneKey = Scenes.Communal;
        KeyguardState keyguardState = KeyguardState.GLANCEABLE_HUB;
        FlowKt__TransformKt$onEach$$inlined$unsafeTransform$1 flowKt__TransformKt$onEach$$inlined$unsafeTransform$1 = new FlowKt__TransformKt$onEach$$inlined$unsafeTransform$1(FlowKt.distinctUntilChanged(booleanFlowOperators.allOf(keyguardTransitionInteractor.isFinishedIn(keyguardState), keyguardInteractor.isKeyguardOccluded, booleanFlowOperators.not(keyguardInteractor.isAbleToDream))), new CommunalViewModel$isCommunalContentFlowFrozen$1(this, null));
        this.isCommunalContentFlowFrozen = flowKt__TransformKt$onEach$$inlined$unsafeTransform$1;
        this.communalContent = new FlowKt__TransformKt$onEach$$inlined$unsafeTransform$1(LatestConflatedKt.flatMapLatestConflated(flowKt__TransformKt$onEach$$inlined$unsafeTransform$1, new CommunalViewModel$communalContent$1(this, null)), new CommunalViewModel$communalContent$2(this, null));
        final FlowKt__ZipKt$combine$$inlined$unsafeFlow$1 flowKt__ZipKt$combine$$inlined$unsafeFlow$1 = communalInteractor.widgetContent;
        this.isEmptyState = new FlowKt__TransformKt$onEach$$inlined$unsafeTransform$1(FlowKt.distinctUntilChanged(new Flow() { // from class: com.android.systemui.communal.ui.viewmodel.CommunalViewModel$special$$inlined$map$1

            /* renamed from: com.android.systemui.communal.ui.viewmodel.CommunalViewModel$special$$inlined$map$1$2, reason: invalid class name */
            public final class AnonymousClass2 implements FlowCollector {
                public final /* synthetic */ FlowCollector $this_unsafeFlow;

                /* renamed from: com.android.systemui.communal.ui.viewmodel.CommunalViewModel$special$$inlined$map$1$2$1, reason: invalid class name */
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
                        Boolean boolValueOf = Boolean.valueOf(((List) obj).isEmpty());
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
                Object objCollect = flowKt__ZipKt$combine$$inlined$unsafeFlow$1.collect(new AnonymousClass2(flowCollector), continuation);
                return objCollect == CoroutineSingletons.COROUTINE_SUSPENDED ? objCollect : Unit.INSTANCE;
            }
        }), new AnonymousClass2(null));
        StateFlowImpl stateFlowImplMutableStateFlow = StateFlowKt.MutableStateFlow(null);
        this._currentPopup = stateFlowImplMutableStateFlow;
        this.currentPopup = FlowKt.asStateFlow(stateFlowImplMutableStateFlow);
        ShadeInteractorImpl shadeInteractorImpl = (ShadeInteractorImpl) shadeInteractor;
        this.isFocusable = FlowKt.distinctUntilChanged(FlowKt.combine(FlowKt.distinctUntilChanged(keyguardTransitionInteractor.isFinishedIn$1(keyguardState)), communalInteractor.isIdleOnCommunal, shadeInteractorImpl.isAnyFullyExpanded, new AnonymousClass1(null)));
        Boolean bool = Boolean.FALSE;
        StateFlowImpl stateFlowImplMutableStateFlow2 = StateFlowKt.MutableStateFlow(bool);
        this._isEnableWidgetDialogShowing = stateFlowImplMutableStateFlow2;
        this.isEnableWidgetDialogShowing = FlowKt.asStateFlow(stateFlowImplMutableStateFlow2);
        StateFlowImpl stateFlowImplMutableStateFlow3 = StateFlowKt.MutableStateFlow(bool);
        this._isEnableWorkProfileDialogShowing = stateFlowImplMutableStateFlow3;
        this.isEnableWorkProfileDialogShowing = FlowKt.asStateFlow(stateFlowImplMutableStateFlow3);
        this.isUiBlurred = StateFlowKt.MutableStateFlow(bool);
        this.blurRadiusPx = blurConfig.maxBlurRadiusPx;
        mediaHost.setExpansion(1.0f);
        MediaHost.MediaHostStateHolder mediaHostStateHolder = mediaHost.state;
        if (true != mediaHostStateHolder.expandedMatchesParentHeight) {
            mediaHostStateHolder.expandedMatchesParentHeight = true;
            MediaHost$$ExternalSyntheticLambda0 mediaHost$$ExternalSyntheticLambda0 = mediaHostStateHolder.changedListener;
            if (mediaHost$$ExternalSyntheticLambda0 != null) {
                mediaHost$$ExternalSyntheticLambda0.invoke();
            }
        }
        communalSettingsInteractor.isV2FlagEnabled();
        mediaHost.setShowsOnlyActiveMedia(false);
        if (mediaHostStateHolder.falsingProtectionNeeded) {
            mediaHostStateHolder.falsingProtectionNeeded = false;
            MediaHost$$ExternalSyntheticLambda0 mediaHost$$ExternalSyntheticLambda02 = mediaHostStateHolder.changedListener;
            if (mediaHost$$ExternalSyntheticLambda02 != null) {
                mediaHost$$ExternalSyntheticLambda02.invoke();
            }
        }
        if (!mediaHostStateHolder.disableScrolling) {
            mediaHostStateHolder.disableScrolling = true;
            MediaHost$$ExternalSyntheticLambda0 mediaHost$$ExternalSyntheticLambda03 = mediaHostStateHolder.changedListener;
            if (mediaHost$$ExternalSyntheticLambda03 != null) {
                mediaHost$$ExternalSyntheticLambda03.invoke();
            }
        }
        mediaHost.init(4);
        Flow flowNot = booleanFlowOperators.not(shadeInteractorImpl.isAnyFullyExpanded);
        SharingStarted.Companion.getClass();
        this.touchesAllowed = FlowKt.stateIn(flowNot, coroutineScope2, SharingStarted.Companion.Eagerly, bool);
        this.communalBackground = communalSettingsInteractor.communalBackground;
        this.swipeToHubEnabled$delegate = LazyKt__LazyJVMKt.lazy(new Function0(keyguardTransitionInteractor) { // from class: com.android.systemui.communal.ui.viewmodel.CommunalViewModel$$ExternalSyntheticLambda0
            @Override // kotlin.jvm.functions.Function0
            public final Object invoke() {
                int i = CommunalViewModel.$r8$clinit;
                CommunalViewModel communalViewModel = this.f$0;
                CommunalSettingsInteractor communalSettingsInteractor2 = communalViewModel.communalSettingsInteractor;
                communalSettingsInteractor2.isV2FlagEnabled();
                StateFlowImpl stateFlowImplMutableStateFlow4 = StateFlowKt.MutableStateFlow(Boolean.valueOf(communalViewModel.swipeToHub));
                communalSettingsInteractor2.isV2FlagEnabled();
                return stateFlowImplMutableStateFlow4;
            }
        });
        this.swipeFromHubInLandscape = communalSceneInteractor.willRotateToPortrait;
    }

    @Override // com.android.systemui.communal.ui.viewmodel.BaseCommunalViewModel
    public final FlowKt__TransformKt$onEach$$inlined$unsafeTransform$1 getCommunalContent() {
        return this.communalContent;
    }

    @Override // com.android.systemui.communal.ui.viewmodel.BaseCommunalViewModel
    public final Flow isCommunalContentVisible() {
        return this.isCommunalContentVisible;
    }

    @Override // com.android.systemui.communal.ui.viewmodel.BaseCommunalViewModel
    public final Flow isEmptyState() {
        return this.isEmptyState;
    }

    @Override // com.android.systemui.communal.ui.viewmodel.BaseCommunalViewModel
    public final Flow isFocusable() {
        return this.isFocusable;
    }

    @Override // com.android.systemui.communal.ui.viewmodel.BaseCommunalViewModel
    public final void onDismissCtaTile() {
        CoroutineTracingKt.launchTraced$default(this.scope, null, null, new C08431(null), 7);
    }

    @Override // com.android.systemui.communal.ui.viewmodel.BaseCommunalViewModel
    public final void onLongClick() {
        setCurrentPopupType(PopupType.CustomizeWidgetButton.INSTANCE);
    }

    @Override // com.android.systemui.communal.ui.viewmodel.BaseCommunalViewModel
    public final void onOpenEnableWidgetDialog() {
        this._isEnableWidgetDialogShowing.updateState(null, Boolean.TRUE);
    }

    @Override // com.android.systemui.communal.ui.viewmodel.BaseCommunalViewModel
    public final void onOpenEnableWorkProfileDialog() {
        this._isEnableWorkProfileDialogShowing.updateState(null, Boolean.TRUE);
    }

    @Override // com.android.systemui.communal.ui.viewmodel.BaseCommunalViewModel
    public final void onOpenWidgetEditor(boolean z) throws Resources.NotFoundException {
        int i = this.currentScrollIndex;
        int i2 = this.currentScrollOffset;
        CommunalInteractor communalInteractor = super.communalInteractor;
        communalInteractor._firstVisibleItemIndex = i;
        communalInteractor._firstVisibleItemOffset = i2;
        CommunalInteractor communalInteractor2 = this.communalInteractor;
        CommunalSceneInteractor communalSceneInteractor = communalInteractor2.communalSceneInteractor;
        communalSceneInteractor._editModeState.setValue(EditModeState.STARTING);
        EditWidgetsActivityStarterImpl editWidgetsActivityStarterImpl = (EditWidgetsActivityStarterImpl) communalInteractor2.editWidgetsActivityStarter;
        editWidgetsActivityStarterImpl.getClass();
        Intent intentAddFlags = new Intent(editWidgetsActivityStarterImpl.applicationContext, (Class<?>) EditWidgetsActivity.class).addFlags(268468224);
        intentAddFlags.putExtra("open_widget_picker_on_start", z);
        editWidgetsActivityStarterImpl.activityStarter.startActivityDismissingKeyguard(intentAddFlags, true, true, editWidgetsActivityStarterImpl.applicationContext.getResources().getString(R.string.unlock_reason_to_customize_widgets));
    }

    @Override // com.android.systemui.communal.ui.viewmodel.BaseCommunalViewModel
    public final void onShowNextMedia() {
        this.mediaCarouselController.mediaCarouselScrollHandler.scrollByStep(1);
    }

    @Override // com.android.systemui.communal.ui.viewmodel.BaseCommunalViewModel
    public final void onShowPreviousMedia() {
        this.mediaCarouselController.mediaCarouselScrollHandler.scrollByStep(-1);
    }

    @Override // com.android.systemui.communal.ui.viewmodel.BaseCommunalViewModel
    public final void onTapWidget(int i, ComponentName componentName) {
        String strFlattenToString = componentName.flattenToString();
        CommunalMetricsLogger communalMetricsLogger = this.metricsLogger;
        if (communalMetricsLogger.isLoggable(strFlattenToString)) {
            ((CommunalStatsLogProxyImpl) communalMetricsLogger.statsLogProxy).writeCommunalHubWidgetEventReported(3, i, 0, strFlattenToString);
        }
    }

    public final void setCurrentPopupType(PopupType popupType) {
        this._currentPopup.setValue(popupType);
        StandaloneCoroutine standaloneCoroutine = this.delayedHideCurrentPopupJob;
        if (standaloneCoroutine != null) {
            standaloneCoroutine.cancel(null);
        }
        if (popupType == null) {
            this.delayedHideCurrentPopupJob = null;
        } else {
            this.delayedHideCurrentPopupJob = CoroutineTracingKt.launchTraced$default(this.scope, null, null, new C08441(null), 7);
        }
    }
}
