package com.android.systemui.communal.ui.viewmodel;

import android.view.View;
import com.android.systemui.communal.domain.interactor.CommunalInteractor;
import com.android.systemui.communal.domain.interactor.CommunalSceneInteractor;
import com.android.systemui.media.controls.ui.view.MediaHost;
import java.util.Map;
import kotlinx.coroutines.flow.Flow;
import kotlinx.coroutines.flow.FlowKt__BuildersKt$flowOf$$inlined$unsafeFlow$2;
import kotlinx.coroutines.flow.FlowKt__TransformKt$onEach$$inlined$unsafeTransform$1;
import kotlinx.coroutines.flow.StateFlowImpl;
import kotlinx.coroutines.flow.StateFlowKt;

public abstract class BaseCommunalViewModel {
    public final StateFlowImpl _selectedKey;
    public final CommunalInteractor communalInteractor;
    public final CommunalSceneInteractor communalSceneInteractor;
    public final FlowKt__BuildersKt$flowOf$$inlined$unsafeFlow$2 currentPopup;
    public final Flow currentScene;
    public final StateFlowImpl isCommunalContentVisible;
    public final FlowKt__BuildersKt$flowOf$$inlined$unsafeFlow$2 isEmptyState;
    public final StateFlowImpl isFocusable;
    public final MediaHost mediaHost;
    public final StateFlowImpl reorderingWidgets;

    public BaseCommunalViewModel(CommunalSceneInteractor communalSceneInteractor, CommunalInteractor communalInteractor, MediaHost mediaHost) {
        this.communalSceneInteractor = communalSceneInteractor;
        this.communalInteractor = communalInteractor;
        this.mediaHost = mediaHost;
        this.currentScene = communalSceneInteractor.currentScene;
        Boolean bool = Boolean.FALSE;
        this.isCommunalContentVisible = StateFlowKt.MutableStateFlow(bool);
        this.isFocusable = StateFlowKt.MutableStateFlow(bool);
        this.reorderingWidgets = StateFlowKt.MutableStateFlow(bool);
        this._selectedKey = StateFlowKt.MutableStateFlow(null);
        new FlowKt__BuildersKt$flowOf$$inlined$unsafeFlow$2(bool);
        this.currentPopup = new FlowKt__BuildersKt$flowOf$$inlined$unsafeFlow$2(null);
        this.isEmptyState = new FlowKt__BuildersKt$flowOf$$inlined$unsafeFlow$2(bool);
    }

    public static /* synthetic */ void onOpenWidgetEditor$default(BaseCommunalViewModel baseCommunalViewModel, String str, boolean z, int i) {
        if ((i & 1) != 0) {
            str = null;
        }
        if ((i & 2) != 0) {
            z = false;
        }
        baseCommunalViewModel.onOpenWidgetEditor(str, z);
    }

    public abstract FlowKt__TransformKt$onEach$$inlined$unsafeTransform$1 getCommunalContent();

    public Flow getCurrentPopup() {
        return this.currentPopup;
    }

    public StateFlowImpl getReorderingWidgets() {
        return this.reorderingWidgets;
    }

    public View.AccessibilityDelegate getWidgetAccessibilityDelegate() {
        return null;
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

    public final void setSelectedKey(String str) {
        this._selectedKey.setValue(str);
    }

    public void onDismissCtaTile() {
    }

    public void onHidePopup() {
    }

    public void onOpenEnableWidgetDialog() {
    }

    public void onOpenEnableWorkProfileDialog() {
    }

    public void onReorderWidgetCancel() {
    }

    public void onReorderWidgetEnd() {
    }

    public void onReorderWidgetStart() {
    }

    public void onShowCustomizeWidgetButton() {
    }

    public void onDeleteWidget(int i) {
    }

    public void onReorderWidgets(Map map) {
    }

    public void onOpenWidgetEditor(String str, boolean z) {
    }
}
