package com.android.systemui.communal.ui.viewmodel;

import android.appwidget.AppWidgetProviderInfo;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.content.pm.UserInfo;
import android.content.res.Resources;
import android.os.Parcelable;
import android.os.UserHandle;
import android.util.Log;
import android.view.accessibility.AccessibilityEvent;
import android.view.accessibility.AccessibilityManager;
import com.android.compose.animation.scene.SceneKey;
import com.android.internal.logging.UiEventLogger;
import com.android.systemui.R;
import com.android.systemui.communal.data.model.CommunalWidgetCategories;
import com.android.systemui.communal.domain.interactor.CommunalInteractor;
import com.android.systemui.communal.domain.interactor.CommunalSceneInteractor;
import com.android.systemui.communal.domain.interactor.CommunalSettingsInteractor;
import com.android.systemui.communal.domain.model.CommunalContentModel;
import com.android.systemui.communal.shared.log.CommunalMetricsLogger;
import com.android.systemui.communal.shared.log.CommunalStatsLogProxyImpl;
import com.android.systemui.communal.shared.log.CommunalUiEvent;
import com.android.systemui.communal.shared.model.EditModeState;
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
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import kotlin.ResultKt;
import kotlin.Unit;
import kotlin.collections.CollectionsKt__CollectionsKt;
import kotlin.coroutines.Continuation;
import kotlin.coroutines.intrinsics.CoroutineSingletons;
import kotlin.coroutines.jvm.internal.Boxing;
import kotlin.coroutines.jvm.internal.ContinuationImpl;
import kotlin.coroutines.jvm.internal.SuspendLambda;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.functions.Function2;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.Intrinsics;
import kotlinx.coroutines.BuildersKt;
import kotlinx.coroutines.CoroutineDispatcher;
import kotlinx.coroutines.CoroutineScope;
import kotlinx.coroutines.flow.Flow;
import kotlinx.coroutines.flow.FlowCollector;
import kotlinx.coroutines.flow.FlowKt;
import kotlinx.coroutines.flow.FlowKt__TransformKt$onEach$$inlined$unsafeTransform$1;
import kotlinx.coroutines.flow.FlowKt__ZipKt$combine$$inlined$unsafeFlow$1;
import kotlinx.coroutines.flow.ReadonlyStateFlow;
import kotlinx.coroutines.flow.StateFlowImpl;
import kotlinx.coroutines.flow.StateFlowKt;

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

    public final class Companion {
        public /* synthetic */ Companion(DefaultConstructorMarker defaultConstructorMarker) {
            this();
        }

        private Companion() {
        }
    }

    /* renamed from: com.android.systemui.communal.ui.viewmodel.CommunalEditModeViewModel$onOpenWidgetPicker$2, reason: invalid class name */
    final class AnonymousClass2 extends SuspendLambda implements Function2 {
        final /* synthetic */ Resources $resources;
        final /* synthetic */ Function1 $startActivity;
        int label;

        /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
        public AnonymousClass2(Resources resources, Function1 function1, Continuation continuation) {
            super(2, continuation);
            this.$resources = resources;
            this.$startActivity = function1;
        }

        @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
        public final Continuation create(Object obj, Continuation continuation) {
            return CommunalEditModeViewModel.this.new AnonymousClass2(this.$resources, this.$startActivity, continuation);
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
                FlowKt__ZipKt$combine$$inlined$unsafeFlow$1 flowKt__ZipKt$combine$$inlined$unsafeFlow$1 = CommunalEditModeViewModel.this.communalInteractor.widgetContent;
                this.label = 1;
                obj = FlowKt.first(flowKt__ZipKt$combine$$inlined$unsafeFlow$1, this);
                if (obj == coroutineSingletons) {
                    return coroutineSingletons;
                }
            } else {
                if (i != 1) {
                    throw new IllegalStateException("call to 'resume' before 'invoke' with coroutine");
                }
                ResultKt.throwOnFailure(obj);
            }
            ArrayList arrayList = new ArrayList();
            for (Object obj2 : (List) obj) {
                if (obj2 instanceof CommunalContentModel.WidgetContent.Widget) {
                    arrayList.add(obj2);
                }
            }
            ArrayList<? extends Parcelable> arrayList2 = new ArrayList<>();
            int size = arrayList.size();
            int i2 = 0;
            while (i2 < size) {
                Object obj3 = arrayList.get(i2);
                i2++;
                arrayList2.add(((CommunalContentModel.WidgetContent.Widget) obj3).providerInfo);
            }
            CommunalEditModeViewModel communalEditModeViewModel = CommunalEditModeViewModel.this;
            Resources resources = this.$resources;
            int i3 = CommunalEditModeViewModel.$r8$clinit;
            communalEditModeViewModel.getClass();
            Intent intent = new Intent("android.intent.action.PICK");
            intent.setPackage(communalEditModeViewModel.launcherPackage);
            intent.putExtra("desired_widget_width", resources.getDimensionPixelSize(R.dimen.communal_widget_picker_desired_width));
            intent.putExtra("desired_widget_height", resources.getDimensionPixelSize(R.dimen.communal_widget_picker_desired_height));
            CommunalWidgetCategories.INSTANCE.getClass();
            intent.putExtra("categoryFilter", 3);
            intent.putExtra("category_exclusion_filter", -9);
            UserInfo userInfo = (UserInfo) communalEditModeViewModel.communalSettingsInteractor.workProfileUserDisallowedByDevicePolicy.$$delegate_0.getValue();
            if (userInfo != null) {
                intent.putExtra("filtered_user_ids", CollectionsKt__CollectionsKt.arrayListOf(Integer.valueOf(userInfo.id)));
            }
            intent.putExtra("ui_surface", "widgets_hub");
            intent.putExtra("picker_title", resources.getString(R.string.communal_widget_picker_title));
            intent.putExtra("picker_description", resources.getString(R.string.communal_widget_picker_description));
            intent.putParcelableArrayListExtra("added_app_widgets", arrayList2);
            try {
                this.$startActivity.mo781invoke(intent);
                return Boolean.TRUE;
            } catch (Exception e) {
                Boxing.boxInt(Log.e("CommunalEditModeViewModel", "Failed to launch widget picker activity", e));
                return Boolean.FALSE;
            }
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
                        Boolean boolValueOf = Boolean.valueOf(((EditModeState) obj) == EditModeState.SHOWING);
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
        String strFlattenToString = componentName.flattenToString();
        CommunalMetricsLogger communalMetricsLogger = this.metricsLogger;
        if (communalMetricsLogger.isLoggable(strFlattenToString)) {
            ((CommunalStatsLogProxyImpl) communalMetricsLogger.statsLogProxy).writeCommunalHubWidgetEventReported(1, num != null ? num.intValue() : -1, 0, strFlattenToString);
        }
    }

    @Override // com.android.systemui.communal.ui.viewmodel.BaseCommunalViewModel
    public final void onDeleteWidget(int i, String str, ComponentName componentName, int i2) {
        if (Intrinsics.areEqual(this.selectedKey.$$delegate_0.getValue(), str)) {
            setSelectedKey(null);
        }
        this.communalInteractor.widgetRepository.deleteWidget(i);
        String strFlattenToString = componentName.flattenToString();
        CommunalMetricsLogger communalMetricsLogger = this.metricsLogger;
        if (communalMetricsLogger.isLoggable(strFlattenToString)) {
            ((CommunalStatsLogProxyImpl) communalMetricsLogger.statsLogProxy).writeCommunalHubWidgetEventReported(2, i2, 0, strFlattenToString);
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
        return BuildersKt.withContext(this.backgroundDispatcher, new AnonymousClass2(resources, editWidgetsActivity$onOpenWidgetPicker$1$$ExternalSyntheticLambda0, null), continuation);
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
        String strFlattenToString = componentName.flattenToString();
        CommunalMetricsLogger communalMetricsLogger = this.metricsLogger;
        if (communalMetricsLogger.isLoggable(strFlattenToString)) {
            ((CommunalStatsLogProxyImpl) communalMetricsLogger.statsLogProxy).writeCommunalHubWidgetEventReported(4, i3, i2, strFlattenToString);
        }
    }
}
