package com.android.systemui.communal.ui.viewmodel;

import android.content.Intent;
import android.content.res.Resources;
import android.view.View;
import android.view.accessibility.AccessibilityNodeInfo;
import com.android.systemui.R;
import com.android.systemui.communal.domain.interactor.CommunalInteractor;
import com.android.systemui.communal.domain.interactor.CommunalSceneInteractor;
import com.android.systemui.communal.domain.interactor.CommunalSettingsInteractor;
import com.android.systemui.communal.domain.interactor.CommunalTutorialInteractor;
import com.android.systemui.communal.shared.model.EditModeState;
import com.android.systemui.communal.ui.viewmodel.PopupType;
import com.android.systemui.communal.widgets.EditWidgetsActivity;
import com.android.systemui.communal.widgets.EditWidgetsActivityStarterImpl;
import com.android.systemui.keyguard.domain.interactor.KeyguardInteractor;
import com.android.systemui.keyguard.domain.interactor.KeyguardTransitionInteractor;
import com.android.systemui.keyguard.shared.model.KeyguardState;
import com.android.systemui.log.LogBuffer;
import com.android.systemui.log.core.Logger;
import com.android.systemui.media.controls.ui.view.MediaHost;
import com.android.systemui.shade.domain.interactor.ShadeInteractor;
import com.android.systemui.shade.domain.interactor.ShadeInteractorImpl;
import com.android.systemui.util.kotlin.BooleanFlowOperators;
import com.android.systemui.utils.coroutines.flow.FlowConflatedKt;
import com.android.systemui.utils.coroutines.flow.LatestConflatedKt;
import java.util.List;
import java.util.Locale;
import kotlin.Unit;
import kotlin.coroutines.Continuation;
import kotlin.coroutines.intrinsics.CoroutineSingletons;
import kotlin.coroutines.jvm.internal.ContinuationImpl;
import kotlin.jvm.functions.Function0;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlinx.coroutines.BuildersKt;
import kotlinx.coroutines.CoroutineDispatcher;
import kotlinx.coroutines.CoroutineScope;
import kotlinx.coroutines.Job;
import kotlinx.coroutines.flow.Flow;
import kotlinx.coroutines.flow.FlowCollector;
import kotlinx.coroutines.flow.FlowKt;
import kotlinx.coroutines.flow.FlowKt__EmittersKt$onStart$$inlined$unsafeFlow$1;
import kotlinx.coroutines.flow.FlowKt__TransformKt$onEach$$inlined$unsafeTransform$1;
import kotlinx.coroutines.flow.FlowKt__ZipKt$combine$$inlined$combineUnsafe$FlowKt__ZipKt$1;
import kotlinx.coroutines.flow.ReadonlyStateFlow;
import kotlinx.coroutines.flow.StateFlowImpl;
import kotlinx.coroutines.flow.StateFlowKt;

public final class CommunalViewModel extends BaseCommunalViewModel {
    public static final /* synthetic */ int $r8$clinit = 0;
    public final StateFlowImpl _currentPopup;
    public final StateFlowImpl _isEnableWidgetDialogShowing;
    public final StateFlowImpl _isEnableWorkProfileDialogShowing;
    public final Flow _isMediaHostVisible;
    public final Flow communalBackground;
    public final FlowKt__TransformKt$onEach$$inlined$unsafeTransform$1 communalContent;
    public final CommunalInteractor communalInteractor;
    public final ReadonlyStateFlow currentPopup;
    public Job delayedHideCurrentPopupJob;
    public List frozenCommunalContent;
    public final FlowKt__TransformKt$onEach$$inlined$unsafeTransform$1 isCommunalContentFlowFrozen;
    public final StateFlowImpl isCommunalContentVisible;
    public final FlowKt__TransformKt$onEach$$inlined$unsafeTransform$1 isEmptyState;
    public final ReadonlyStateFlow isEnableWidgetDialogShowing;
    public final ReadonlyStateFlow isEnableWorkProfileDialogShowing;
    public final Flow isFocusable;
    public final FlowKt__TransformKt$onEach$$inlined$unsafeTransform$1 latestCommunalContent;
    public final Logger logger;
    public final Resources resources;
    public final CoroutineScope scope;
    public final ShadeInteractor shadeInteractor;
    public final Flow showGestureIndicator;
    public final Flow touchesAllowed;
    public final CommunalViewModel$widgetAccessibilityDelegate$1 widgetAccessibilityDelegate;

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

    public CommunalViewModel(CoroutineDispatcher coroutineDispatcher, CoroutineScope coroutineScope, Resources resources, KeyguardTransitionInteractor keyguardTransitionInteractor, KeyguardInteractor keyguardInteractor, CommunalSceneInteractor communalSceneInteractor, CommunalInteractor communalInteractor, CommunalSettingsInteractor communalSettingsInteractor, CommunalTutorialInteractor communalTutorialInteractor, ShadeInteractor shadeInteractor, MediaHost mediaHost, LogBuffer logBuffer) {
        super(communalSceneInteractor, communalInteractor, mediaHost);
        this.scope = coroutineScope;
        this.resources = resources;
        this.communalInteractor = communalInteractor;
        this.shadeInteractor = shadeInteractor;
        this._isMediaHostVisible = FlowKt.flowOn(new FlowKt__EmittersKt$onStart$$inlined$unsafeFlow$1(new CommunalViewModel$_isMediaHostVisible$2(mediaHost, null), FlowConflatedKt.conflatedCallbackFlow(new CommunalViewModel$_isMediaHostVisible$1(mediaHost, null))), coroutineDispatcher);
        this.logger = new Logger(logBuffer, "CommunalViewModel");
        this.latestCommunalContent = new FlowKt__TransformKt$onEach$$inlined$unsafeTransform$1(FlowKt.transformLatest(communalTutorialInteractor.isTutorialAvailable, new CommunalViewModel$special$$inlined$flatMapLatest$1(null, this)), new CommunalViewModel$latestCommunalContent$2(this, null));
        Boolean bool = Boolean.TRUE;
        this.isCommunalContentVisible = StateFlowKt.MutableStateFlow(bool);
        BooleanFlowOperators booleanFlowOperators = BooleanFlowOperators.INSTANCE;
        KeyguardState keyguardState = KeyguardState.GLANCEABLE_HUB;
        this.communalContent = new FlowKt__TransformKt$onEach$$inlined$unsafeTransform$1(LatestConflatedKt.flatMapLatestConflated(new FlowKt__TransformKt$onEach$$inlined$unsafeTransform$1(FlowKt.distinctUntilChanged(booleanFlowOperators.allOf(keyguardTransitionInteractor.isFinishedInState(keyguardState), keyguardInteractor.isKeyguardOccluded, booleanFlowOperators.not(keyguardInteractor.isAbleToDream))), new CommunalViewModel$isCommunalContentFlowFrozen$1(this, null)), new CommunalViewModel$communalContent$1(this, null)), new CommunalViewModel$communalContent$2(this, null));
        final FlowKt__ZipKt$combine$$inlined$combineUnsafe$FlowKt__ZipKt$1 flowKt__ZipKt$combine$$inlined$combineUnsafe$FlowKt__ZipKt$1 = communalInteractor.widgetContent;
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

                @Override // kotlinx.coroutines.flow.FlowCollector
                /*
                    Code decompiled incorrectly, please refer to instructions dump.
                    To view partially-correct code enable 'Show inconsistent code' option in preferences
                */
                public final java.lang.Object emit(java.lang.Object r5, kotlin.coroutines.Continuation r6) {
                    /*
                        r4 = this;
                        boolean r0 = r6 instanceof com.android.systemui.communal.ui.viewmodel.CommunalViewModel$special$$inlined$map$1.AnonymousClass2.AnonymousClass1
                        if (r0 == 0) goto L13
                        r0 = r6
                        com.android.systemui.communal.ui.viewmodel.CommunalViewModel$special$$inlined$map$1$2$1 r0 = (com.android.systemui.communal.ui.viewmodel.CommunalViewModel$special$$inlined$map$1.AnonymousClass2.AnonymousClass1) r0
                        int r1 = r0.label
                        r2 = -2147483648(0xffffffff80000000, float:-0.0)
                        r3 = r1 & r2
                        if (r3 == 0) goto L13
                        int r1 = r1 - r2
                        r0.label = r1
                        goto L18
                    L13:
                        com.android.systemui.communal.ui.viewmodel.CommunalViewModel$special$$inlined$map$1$2$1 r0 = new com.android.systemui.communal.ui.viewmodel.CommunalViewModel$special$$inlined$map$1$2$1
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
                        java.util.List r5 = (java.util.List) r5
                        boolean r5 = r5.isEmpty()
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
                    throw new UnsupportedOperationException("Method not decompiled: com.android.systemui.communal.ui.viewmodel.CommunalViewModel$special$$inlined$map$1.AnonymousClass2.emit(java.lang.Object, kotlin.coroutines.Continuation):java.lang.Object");
                }
            }

            @Override // kotlinx.coroutines.flow.Flow
            public final Object collect(FlowCollector flowCollector, Continuation continuation) {
                Object collect = Flow.this.collect(new AnonymousClass2(flowCollector), continuation);
                return collect == CoroutineSingletons.COROUTINE_SUSPENDED ? collect : Unit.INSTANCE;
            }
        }), new CommunalViewModel$isEmptyState$2(this, null));
        StateFlowImpl MutableStateFlow = StateFlowKt.MutableStateFlow(null);
        this._currentPopup = MutableStateFlow;
        this.currentPopup = FlowKt.asStateFlow(MutableStateFlow);
        ShadeInteractorImpl shadeInteractorImpl = (ShadeInteractorImpl) shadeInteractor;
        this.isFocusable = FlowKt.distinctUntilChanged(FlowKt.combine(keyguardTransitionInteractor.isFinishedInState(keyguardState), communalInteractor.isIdleOnCommunal, shadeInteractorImpl.isAnyFullyExpanded, new CommunalViewModel$isFocusable$1(null)));
        this.widgetAccessibilityDelegate = new View.AccessibilityDelegate() { // from class: com.android.systemui.communal.ui.viewmodel.CommunalViewModel$widgetAccessibilityDelegate$1
            @Override // android.view.View.AccessibilityDelegate
            public final void onInitializeAccessibilityNodeInfo(View view, AccessibilityNodeInfo accessibilityNodeInfo) {
                super.onInitializeAccessibilityNodeInfo(view, accessibilityNodeInfo);
                accessibilityNodeInfo.addAction(new AccessibilityNodeInfo.AccessibilityAction(AccessibilityNodeInfo.AccessibilityAction.ACTION_LONG_CLICK.getId(), CommunalViewModel.this.resources.getString(R.string.accessibility_action_label_edit_widgets).toLowerCase(Locale.ROOT)));
            }
        };
        Boolean bool2 = Boolean.FALSE;
        StateFlowImpl MutableStateFlow2 = StateFlowKt.MutableStateFlow(bool2);
        this._isEnableWidgetDialogShowing = MutableStateFlow2;
        this.isEnableWidgetDialogShowing = FlowKt.asStateFlow(MutableStateFlow2);
        StateFlowImpl MutableStateFlow3 = StateFlowKt.MutableStateFlow(bool2);
        this._isEnableWorkProfileDialogShowing = MutableStateFlow3;
        this.isEnableWorkProfileDialogShowing = FlowKt.asStateFlow(MutableStateFlow3);
        mediaHost.setExpansion(1.0f);
        MediaHost.MediaHostStateHolder mediaHostStateHolder = mediaHost.state;
        if (true != mediaHostStateHolder.expandedMatchesParentHeight) {
            mediaHostStateHolder.expandedMatchesParentHeight = true;
            Function0 function0 = mediaHostStateHolder.changedListener;
            if (function0 != null) {
                function0.invoke();
            }
        }
        if (!bool.equals(Boolean.valueOf(mediaHostStateHolder.showsOnlyActiveMedia))) {
            mediaHostStateHolder.showsOnlyActiveMedia = true;
            Function0 function02 = mediaHostStateHolder.changedListener;
            if (function02 != null) {
                function02.invoke();
            }
        }
        if (mediaHostStateHolder.falsingProtectionNeeded) {
            mediaHostStateHolder.falsingProtectionNeeded = false;
            Function0 function03 = mediaHostStateHolder.changedListener;
            if (function03 != null) {
                function03.invoke();
            }
        }
        mediaHost.init(4);
        this.touchesAllowed = booleanFlowOperators.not(shadeInteractorImpl.isAnyFullyExpanded);
        this.showGestureIndicator = booleanFlowOperators.not(keyguardInteractor.isDreaming);
        this.communalBackground = communalSettingsInteractor.communalBackground;
    }

    @Override // com.android.systemui.communal.ui.viewmodel.BaseCommunalViewModel
    public final FlowKt__TransformKt$onEach$$inlined$unsafeTransform$1 getCommunalContent() {
        return this.communalContent;
    }

    @Override // com.android.systemui.communal.ui.viewmodel.BaseCommunalViewModel
    public final Flow getCurrentPopup() {
        return this.currentPopup;
    }

    @Override // com.android.systemui.communal.ui.viewmodel.BaseCommunalViewModel
    public final View.AccessibilityDelegate getWidgetAccessibilityDelegate() {
        return this.widgetAccessibilityDelegate;
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
        BuildersKt.launch$default(this.scope, null, null, new CommunalViewModel$onDismissCtaTile$1(this, null), 3);
    }

    @Override // com.android.systemui.communal.ui.viewmodel.BaseCommunalViewModel
    public final void onHidePopup() {
        setCurrentPopupType(null);
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
    public final void onOpenWidgetEditor(String str, boolean z) {
        CommunalInteractor communalInteractor = this.communalInteractor;
        CommunalSceneInteractor communalSceneInteractor = communalInteractor.communalSceneInteractor;
        communalSceneInteractor._editModeState.setValue(EditModeState.STARTING);
        EditWidgetsActivityStarterImpl editWidgetsActivityStarterImpl = (EditWidgetsActivityStarterImpl) communalInteractor.editWidgetsActivityStarter;
        editWidgetsActivityStarterImpl.getClass();
        Intent addFlags = new Intent(editWidgetsActivityStarterImpl.applicationContext, (Class<?>) EditWidgetsActivity.class).addFlags(268468224);
        if (str != null) {
            addFlags.putExtra("preselected_key", str);
        }
        addFlags.putExtra("open_widget_picker_on_start", z);
        editWidgetsActivityStarterImpl.activityStarter.startActivityDismissingKeyguard(addFlags, true, true);
    }

    @Override // com.android.systemui.communal.ui.viewmodel.BaseCommunalViewModel
    public final void onShowCustomizeWidgetButton() {
        setCurrentPopupType(PopupType.CustomizeWidgetButton.INSTANCE);
    }

    public final void setCurrentPopupType(PopupType popupType) {
        this._currentPopup.setValue(popupType);
        Job job = this.delayedHideCurrentPopupJob;
        if (job != null) {
            job.cancel(null);
        }
        if (popupType == null) {
            this.delayedHideCurrentPopupJob = null;
        } else {
            this.delayedHideCurrentPopupJob = BuildersKt.launch$default(this.scope, null, null, new CommunalViewModel$setCurrentPopupType$1(this, null), 3);
        }
    }
}
