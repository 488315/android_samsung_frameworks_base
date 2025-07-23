package com.android.systemui.communal.ui.viewmodel;

import android.appwidget.AppWidgetProviderInfo;
import android.content.ComponentName;
import android.content.Context;
import android.content.pm.PackageManager;
import android.content.res.Resources;
import android.os.UserHandle;
import android.view.accessibility.AccessibilityEvent;
import android.view.accessibility.AccessibilityManager;
import com.android.compose.animation.scene.SceneKey;
import com.android.internal.logging.UiEventLogger;
import com.android.systemui.R;
import com.android.systemui.communal.domain.interactor.CommunalInteractor;
import com.android.systemui.communal.domain.interactor.CommunalSceneInteractor;
import com.android.systemui.communal.domain.interactor.CommunalSettingsInteractor;
import com.android.systemui.communal.shared.log.CommunalMetricsLogger;
import com.android.systemui.communal.shared.log.CommunalStatsLogProxyImpl;
import com.android.systemui.communal.shared.log.CommunalUiEvent;
import com.android.systemui.communal.widgets.EditWidgetsActivity$onOpenWidgetPicker$1$$ExternalSyntheticLambda0;
import com.android.systemui.communal.widgets.WidgetConfigurator;
import com.android.systemui.keyguard.domain.interactor.KeyguardTransitionInteractor;
import com.android.systemui.keyguard.shared.model.KeyguardState;
import com.android.systemui.log.LogBuffer;
import com.android.systemui.log.core.Logger;
import com.android.systemui.media.controls.ui.controller.MediaCarouselController;
import com.android.systemui.media.controls.ui.view.MediaHost;
import com.android.systemui.scene.shared.model.Scenes;
import com.android.systemui.util.kotlin.BooleanFlowOperators;
import com.samsung.android.knox.net.nap.NetworkAnalyticsConstants;
import java.util.Map;
import kotlin.Unit;
import kotlin.coroutines.Continuation;
import kotlin.coroutines.intrinsics.CoroutineSingletons;
import kotlin.coroutines.jvm.internal.ContinuationImpl;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.Intrinsics;
import kotlinx.coroutines.BuildersKt;
import kotlinx.coroutines.CoroutineDispatcher;
import kotlinx.coroutines.flow.Flow;
import kotlinx.coroutines.flow.FlowCollector;
import kotlinx.coroutines.flow.FlowKt__TransformKt$onEach$$inlined$unsafeTransform$1;
import kotlinx.coroutines.flow.ReadonlyStateFlow;
import kotlinx.coroutines.flow.StateFlowImpl;
import kotlinx.coroutines.flow.StateFlowKt;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes2.dex */
public final class CommunalEditModeViewModel extends BaseCommunalViewModel {
    public static final /* synthetic */ int $r8$clinit = 0;
    public final StateFlowImpl _reorderingWidgets;
    public final AccessibilityManager accessibilityManager;
    public final CoroutineDispatcher backgroundDispatcher;
    public final CommunalEditModeViewModel$special$$inlined$filter$1 canShowEditMode;
    public final FlowKt__TransformKt$onEach$$inlined$unsafeTransform$1 communalContent;
    public final CommunalInteractor communalInteractor;
    public final CommunalSettingsInteractor communalSettingsInteractor;
    public final Context context;
    public final CommunalEditModeViewModel$special$$inlined$map$1 isCommunalContentVisible;
    public final boolean isEditMode;
    public final ReadonlyStateFlow isIdleOnCommunal;
    public final String launcherPackage;
    public final Logger logger;
    public final CommunalMetricsLogger metricsLogger;
    public final PackageManager packageManager;
    public final Flow showDisclaimer;
    public final UiEventLogger uiEventLogger;

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

    /* JADX WARN: Type inference failed for: r3v3, types: [com.android.systemui.communal.ui.viewmodel.CommunalEditModeViewModel$special$$inlined$map$1, kotlinx.coroutines.flow.Flow] */
    public CommunalEditModeViewModel(CommunalSceneInteractor communalSceneInteractor, CommunalInteractor communalInteractor, CommunalSettingsInteractor communalSettingsInteractor, KeyguardTransitionInteractor keyguardTransitionInteractor, MediaHost mediaHost, UiEventLogger uiEventLogger, LogBuffer logBuffer, CoroutineDispatcher coroutineDispatcher, CommunalMetricsLogger communalMetricsLogger, Context context, AccessibilityManager accessibilityManager, PackageManager packageManager, String str, MediaCarouselController mediaCarouselController) {
        super(communalSceneInteractor, communalInteractor, mediaHost, mediaCarouselController);
        this.communalInteractor = communalInteractor;
        this.communalSettingsInteractor = communalSettingsInteractor;
        this.uiEventLogger = uiEventLogger;
        this.backgroundDispatcher = coroutineDispatcher;
        this.metricsLogger = communalMetricsLogger;
        this.context = context;
        this.accessibilityManager = accessibilityManager;
        this.packageManager = packageManager;
        this.launcherPackage = str;
        this.logger = new Logger(logBuffer, "CommunalEditModeViewModel");
        this.isEditMode = true;
        final ReadonlyStateFlow readonlyStateFlow = communalSceneInteractor.editModeState;
        ?? r3 = new Flow() { // from class: com.android.systemui.communal.ui.viewmodel.CommunalEditModeViewModel$special$$inlined$map$1

            /* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
            /* renamed from: com.android.systemui.communal.ui.viewmodel.CommunalEditModeViewModel$special$$inlined$map$1$2, reason: invalid class name */
            public final class AnonymousClass2 implements FlowCollector {
                public final /* synthetic */ FlowCollector $this_unsafeFlow;

                /* renamed from: com.android.systemui.communal.ui.viewmodel.CommunalEditModeViewModel$special$$inlined$map$1$2$1, reason: invalid class name */
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
                        boolean r0 = r6 instanceof com.android.systemui.communal.ui.viewmodel.CommunalEditModeViewModel$special$$inlined$map$1.AnonymousClass2.AnonymousClass1
                        if (r0 == 0) goto L13
                        r0 = r6
                        com.android.systemui.communal.ui.viewmodel.CommunalEditModeViewModel$special$$inlined$map$1$2$1 r0 = (com.android.systemui.communal.ui.viewmodel.CommunalEditModeViewModel$special$$inlined$map$1.AnonymousClass2.AnonymousClass1) r0
                        int r1 = r0.label
                        r2 = -2147483648(0xffffffff80000000, float:-0.0)
                        r3 = r1 & r2
                        if (r3 == 0) goto L13
                        int r1 = r1 - r2
                        r0.label = r1
                        goto L18
                    L13:
                        com.android.systemui.communal.ui.viewmodel.CommunalEditModeViewModel$special$$inlined$map$1$2$1 r0 = new com.android.systemui.communal.ui.viewmodel.CommunalEditModeViewModel$special$$inlined$map$1$2$1
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
                        com.android.systemui.communal.shared.model.EditModeState r5 = (com.android.systemui.communal.shared.model.EditModeState) r5
                        com.android.systemui.communal.shared.model.EditModeState r6 = com.android.systemui.communal.shared.model.EditModeState.SHOWING
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
                    throw new UnsupportedOperationException("Method not decompiled: com.android.systemui.communal.ui.viewmodel.CommunalEditModeViewModel$special$$inlined$map$1.AnonymousClass2.emit(java.lang.Object, kotlin.coroutines.Continuation):java.lang.Object");
                }
            }

            @Override // kotlinx.coroutines.flow.Flow
            public final Object collect(FlowCollector flowCollector, Continuation continuation) {
                Object collect = Flow.this.collect(new AnonymousClass2(flowCollector), continuation);
                return collect == CoroutineSingletons.COROUTINE_SUSPENDED ? collect : Unit.INSTANCE;
            }
        };
        this.isCommunalContentVisible = r3;
        BooleanFlowOperators booleanFlowOperators = BooleanFlowOperators.INSTANCE;
        this.showDisclaimer = booleanFlowOperators.allOf(r3, booleanFlowOperators.not(communalInteractor.isDisclaimerDismissed));
        SceneKey sceneKey = Scenes.Communal;
        this.canShowEditMode = new CommunalEditModeViewModel$special$$inlined$filter$1(booleanFlowOperators.allOf(keyguardTransitionInteractor.isFinishedIn(KeyguardState.GONE), communalInteractor.editModeOpen));
        this.communalContent = new FlowKt__TransformKt$onEach$$inlined$unsafeTransform$1(communalInteractor.widgetContent, new CommunalEditModeViewModel$communalContent$1(this, null));
        this._reorderingWidgets = StateFlowKt.MutableStateFlow(Boolean.FALSE);
        this.isIdleOnCommunal = communalInteractor.isIdleOnCommunal;
    }

    @Override // com.android.systemui.communal.ui.viewmodel.BaseCommunalViewModel
    public final FlowKt__TransformKt$onEach$$inlined$unsafeTransform$1 getCommunalContent() {
        return this.communalContent;
    }

    @Override // com.android.systemui.communal.ui.viewmodel.BaseCommunalViewModel
    public final StateFlowImpl getReorderingWidgets() {
        return this._reorderingWidgets;
    }

    @Override // com.android.systemui.communal.ui.viewmodel.BaseCommunalViewModel
    public final Flow isCommunalContentVisible() {
        return this.isCommunalContentVisible;
    }

    @Override // com.android.systemui.communal.ui.viewmodel.BaseCommunalViewModel
    public final boolean isEditMode() {
        return this.isEditMode;
    }

    @Override // com.android.systemui.communal.ui.viewmodel.BaseCommunalViewModel
    public final void onAddWidget(ComponentName componentName, UserHandle userHandle, Integer num, WidgetConfigurator widgetConfigurator) {
        this.communalInteractor.widgetRepository.addWidget(componentName, userHandle, num, widgetConfigurator);
        String flattenToString = componentName.flattenToString();
        CommunalMetricsLogger communalMetricsLogger = this.metricsLogger;
        if (communalMetricsLogger.isLoggable(flattenToString)) {
            ((CommunalStatsLogProxyImpl) communalMetricsLogger.statsLogProxy).writeCommunalHubWidgetEventReported(1, num != null ? num.intValue() : -1, 0, flattenToString);
        }
    }

    @Override // com.android.systemui.communal.ui.viewmodel.BaseCommunalViewModel
    public final void onDeleteWidget(int i, String str, ComponentName componentName, int i2) {
        if (Intrinsics.areEqual(this.selectedKey.$$delegate_0.getValue(), str)) {
            setSelectedKey(null);
        }
        this.communalInteractor.widgetRepository.deleteWidget(i);
        String flattenToString = componentName.flattenToString();
        CommunalMetricsLogger communalMetricsLogger = this.metricsLogger;
        if (communalMetricsLogger.isLoggable(flattenToString)) {
            ((CommunalStatsLogProxyImpl) communalMetricsLogger.statsLogProxy).writeCommunalHubWidgetEventReported(2, i2, 0, flattenToString);
        }
    }

    @Override // com.android.systemui.communal.ui.viewmodel.BaseCommunalViewModel
    public final void onNewWidgetAdded(AppWidgetProviderInfo appWidgetProviderInfo) {
        if (this.accessibilityManager.isEnabled()) {
            String string = this.context.getString(R.string.accessibility_announcement_communal_widget_added, appWidgetProviderInfo.loadLabel(this.packageManager));
            AccessibilityManager accessibilityManager = this.accessibilityManager;
            AccessibilityEvent accessibilityEvent = new AccessibilityEvent(NetworkAnalyticsConstants.DataPoints.FLAG_SOURCE_PORT);
            accessibilityEvent.setContentDescription(string);
            accessibilityManager.sendAccessibilityEvent(accessibilityEvent);
        }
    }

    public final Object onOpenWidgetPicker(Resources resources, EditWidgetsActivity$onOpenWidgetPicker$1$$ExternalSyntheticLambda0 editWidgetsActivity$onOpenWidgetPicker$1$$ExternalSyntheticLambda0, Continuation continuation) {
        return BuildersKt.withContext(this.backgroundDispatcher, new CommunalEditModeViewModel$onOpenWidgetPicker$2(this, resources, editWidgetsActivity$onOpenWidgetPicker$1$$ExternalSyntheticLambda0, null), continuation);
    }

    @Override // com.android.systemui.communal.ui.viewmodel.BaseCommunalViewModel
    public final void onReorderWidgetCancel() {
        this._reorderingWidgets.updateState(null, Boolean.FALSE);
        this.uiEventLogger.log(CommunalUiEvent.COMMUNAL_HUB_REORDER_WIDGET_CANCEL);
    }

    @Override // com.android.systemui.communal.ui.viewmodel.BaseCommunalViewModel
    public final void onReorderWidgetEnd() {
        this._reorderingWidgets.updateState(null, Boolean.FALSE);
        this.uiEventLogger.log(CommunalUiEvent.COMMUNAL_HUB_REORDER_WIDGET_FINISH);
    }

    @Override // com.android.systemui.communal.ui.viewmodel.BaseCommunalViewModel
    public final void onReorderWidgetStart(String str) {
        setSelectedKey(str);
        this._reorderingWidgets.updateState(null, Boolean.TRUE);
        this.uiEventLogger.log(CommunalUiEvent.COMMUNAL_HUB_REORDER_WIDGET_START);
    }

    @Override // com.android.systemui.communal.ui.viewmodel.BaseCommunalViewModel
    public final void onReorderWidgets(Map map) {
        this.communalInteractor.widgetRepository.updateWidgetOrder(map);
    }

    @Override // com.android.systemui.communal.ui.viewmodel.BaseCommunalViewModel
    public final void onResizeWidget(int i, int i2, Map map, ComponentName componentName, int i3) {
        this.communalInteractor.widgetRepository.resizeWidget(map, i, i2);
        String flattenToString = componentName.flattenToString();
        CommunalMetricsLogger communalMetricsLogger = this.metricsLogger;
        if (communalMetricsLogger.isLoggable(flattenToString)) {
            ((CommunalStatsLogProxyImpl) communalMetricsLogger.statsLogProxy).writeCommunalHubWidgetEventReported(4, i3, i2, flattenToString);
        }
    }
}
