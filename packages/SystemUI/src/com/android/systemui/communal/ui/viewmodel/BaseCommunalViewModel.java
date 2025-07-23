package com.android.systemui.communal.ui.viewmodel;

import android.appwidget.AppWidgetProviderInfo;
import android.content.ComponentName;
import android.os.UserHandle;
import com.android.compose.animation.scene.SceneKey;
import com.android.compose.animation.scene.TransitionKey;
import com.android.systemui.communal.domain.interactor.CommunalInteractor;
import com.android.systemui.communal.domain.interactor.CommunalSceneInteractor;
import com.android.systemui.communal.widgets.WidgetConfigurator;
import com.android.systemui.media.controls.ui.controller.MediaCarouselController;
import com.android.systemui.media.controls.ui.view.MediaHost;
import com.android.systemui.util.kotlin.BooleanFlowOperators;
import java.util.Map;
import kotlinx.coroutines.flow.Flow;
import kotlinx.coroutines.flow.FlowKt;
import kotlinx.coroutines.flow.FlowKt__BuildersKt$flowOf$$inlined$unsafeFlow$2;
import kotlinx.coroutines.flow.FlowKt__TransformKt$onEach$$inlined$unsafeTransform$1;
import kotlinx.coroutines.flow.ReadonlyStateFlow;
import kotlinx.coroutines.flow.StateFlowImpl;
import kotlinx.coroutines.flow.StateFlowKt;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes2.dex */
public abstract class BaseCommunalViewModel {
    public final StateFlowImpl _isNestedScrolling;
    public final StateFlowImpl _isTouchConsumed;
    public final CommunalInteractor communalInteractor;
    public final CommunalSceneInteractor communalSceneInteractor;
    public final ReadonlyStateFlow currentScene;
    public int currentScrollIndex;
    public int currentScrollOffset;
    public final Flow glanceableTouchAvailable;
    public final StateFlowImpl isCommunalContentVisible;
    public final FlowKt__BuildersKt$flowOf$$inlined$unsafeFlow$2 isEmptyState;
    public final StateFlowImpl isFocusable;
    public final ReadonlyStateFlow isNestedScrolling;
    public final ReadonlyStateFlow isTouchConsumed;
    public final MediaCarouselController mediaCarouselController;
    public final MediaHost mediaHost;
    public final StateFlowImpl reorderingWidgets;
    public final ReadonlyStateFlow selectedKey;

    public BaseCommunalViewModel(CommunalSceneInteractor communalSceneInteractor, CommunalInteractor communalInteractor, MediaHost mediaHost, MediaCarouselController mediaCarouselController) {
        this.communalSceneInteractor = communalSceneInteractor;
        this.communalInteractor = communalInteractor;
        this.mediaHost = mediaHost;
        this.mediaCarouselController = mediaCarouselController;
        this.currentScene = communalSceneInteractor.currentScene;
        Boolean bool = Boolean.FALSE;
        this.isCommunalContentVisible = StateFlowKt.MutableStateFlow(bool);
        this.isFocusable = StateFlowKt.MutableStateFlow(bool);
        this.reorderingWidgets = StateFlowKt.MutableStateFlow(bool);
        this.selectedKey = communalInteractor.selectedKey;
        StateFlowImpl MutableStateFlow = StateFlowKt.MutableStateFlow(bool);
        this._isTouchConsumed = MutableStateFlow;
        ReadonlyStateFlow asStateFlow = FlowKt.asStateFlow(MutableStateFlow);
        this.isTouchConsumed = asStateFlow;
        StateFlowImpl MutableStateFlow2 = StateFlowKt.MutableStateFlow(bool);
        this._isNestedScrolling = MutableStateFlow2;
        ReadonlyStateFlow asStateFlow2 = FlowKt.asStateFlow(MutableStateFlow2);
        this.isNestedScrolling = asStateFlow2;
        BooleanFlowOperators booleanFlowOperators = BooleanFlowOperators.INSTANCE;
        this.glanceableTouchAvailable = booleanFlowOperators.anyOf(booleanFlowOperators.not(asStateFlow), asStateFlow2);
        new FlowKt__BuildersKt$flowOf$$inlined$unsafeFlow$2(bool);
        new FlowKt__BuildersKt$flowOf$$inlined$unsafeFlow$2(null);
        this.isEmptyState = new FlowKt__BuildersKt$flowOf$$inlined$unsafeFlow$2(bool);
    }

    public static void changeScene$default(BaseCommunalViewModel baseCommunalViewModel, SceneKey sceneKey, String str, TransitionKey transitionKey, int i) {
        if ((i & 4) != 0) {
            transitionKey = null;
        }
        baseCommunalViewModel.communalSceneInteractor.changeScene(sceneKey, str, transitionKey, null);
    }

    public abstract FlowKt__TransformKt$onEach$$inlined$unsafeTransform$1 getCommunalContent();

    public StateFlowImpl getReorderingWidgets() {
        return this.reorderingWidgets;
    }

    public Flow isCommunalContentVisible() {
        return this.isCommunalContentVisible;
    }

    public boolean isEditMode() {
        return false;
    }

    public Flow isEmptyState() {
        return this.isEmptyState;
    }

    public Flow isFocusable() {
        return this.isFocusable;
    }

    public final void onResetTouchState() {
        Boolean bool = Boolean.FALSE;
        this._isTouchConsumed.updateState(null, bool);
        this._isNestedScrolling.updateState(null, bool);
    }

    public final void setSelectedKey(String str) {
        this.communalInteractor._selectedKey.setValue(str);
    }

    public void onNewWidgetAdded(AppWidgetProviderInfo appWidgetProviderInfo) {
    }

    public void onOpenWidgetEditor(boolean z) {
    }

    public void onReorderWidgetStart(String str) {
    }

    public void onReorderWidgets(Map map) {
    }

    public void onDismissCtaTile() {
    }

    public void onLongClick() {
    }

    public void onOpenEnableWidgetDialog() {
    }

    public void onOpenEnableWorkProfileDialog() {
    }

    public void onReorderWidgetCancel() {
    }

    public void onReorderWidgetEnd() {
    }

    public void onShowNextMedia() {
    }

    public void onShowPreviousMedia() {
    }

    public void onTapWidget(int i, ComponentName componentName) {
    }

    public void onAddWidget(ComponentName componentName, UserHandle userHandle, Integer num, WidgetConfigurator widgetConfigurator) {
    }

    public void onDeleteWidget(int i, String str, ComponentName componentName, int i2) {
    }

    public void onResizeWidget(int i, int i2, Map map, ComponentName componentName, int i3) {
    }
}
