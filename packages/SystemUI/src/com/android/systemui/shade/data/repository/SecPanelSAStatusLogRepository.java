package com.android.systemui.shade.data.repository;

import android.content.SharedPreferences;
import kotlinx.coroutines.flow.FlowKt;
import kotlinx.coroutines.flow.ReadonlyStateFlow;
import kotlinx.coroutines.flow.StateFlowImpl;
import kotlinx.coroutines.flow.StateFlowKt;

/* compiled from: qb/89523975 427a50d40ec74a85ca352b86f77450b1c52ece5389e11158752b0d641a3a5098 */
public final class SecPanelSAStatusLogRepository {
    public final StateFlowImpl _horizontalSwipingToNotificationPanel;
    public final StateFlowImpl _horizontalSwipingToQuickPanel;
    public final StateFlowImpl _openQuickPanelFrom1Depth;
    public final StateFlowImpl _openQuickPanelFrom2Depth;
    public final StateFlowImpl _openQuickPanelFromHorizontalSwiping;
    public final StateFlowImpl _openQuickPanelFromWipeDown;
    public final ReadonlyStateFlow horizontalSwipingToNotificationPanel;
    public final ReadonlyStateFlow horizontalSwipingToQuickPanel;
    public final ReadonlyStateFlow openQuickPanelFrom1Depth;
    public final ReadonlyStateFlow openQuickPanelFrom2Depth;
    public final ReadonlyStateFlow openQuickPanelFromHorizontalSwiping;
    public final ReadonlyStateFlow openQuickPanelFromWipeDown;
    public final SharedPreferences sharedPreferences;

    public SecPanelSAStatusLogRepository(SharedPreferences sharedPreferences) {
        this.sharedPreferences = sharedPreferences;
        StateFlowImpl mutableStateFlow = toMutableStateFlow("open_quick_panel_from_wipe_down");
        this._openQuickPanelFromWipeDown = mutableStateFlow;
        this.openQuickPanelFromWipeDown = FlowKt.asStateFlow(mutableStateFlow);
        StateFlowImpl mutableStateFlow2 = toMutableStateFlow("open_quick_panel_from_horizontal_swiping");
        this._openQuickPanelFromHorizontalSwiping = mutableStateFlow2;
        this.openQuickPanelFromHorizontalSwiping = FlowKt.asStateFlow(mutableStateFlow2);
        StateFlowImpl mutableStateFlow3 = toMutableStateFlow("horizontal_swiping_to_quick_panel");
        this._horizontalSwipingToQuickPanel = mutableStateFlow3;
        this.horizontalSwipingToQuickPanel = FlowKt.asStateFlow(mutableStateFlow3);
        StateFlowImpl mutableStateFlow4 = toMutableStateFlow("horizontal_swiping_to_notification_panel");
        this._horizontalSwipingToNotificationPanel = mutableStateFlow4;
        this.horizontalSwipingToNotificationPanel = FlowKt.asStateFlow(mutableStateFlow4);
        StateFlowImpl mutableStateFlow5 = toMutableStateFlow("open_quick_panel_from_1depth");
        this._openQuickPanelFrom1Depth = mutableStateFlow5;
        this.openQuickPanelFrom1Depth = FlowKt.asStateFlow(mutableStateFlow5);
        StateFlowImpl mutableStateFlow6 = toMutableStateFlow("open_quick_panel_from_2depth");
        this._openQuickPanelFrom2Depth = mutableStateFlow6;
        this.openQuickPanelFrom2Depth = FlowKt.asStateFlow(mutableStateFlow6);
    }

    public final StateFlowImpl toMutableStateFlow(String str) {
        return StateFlowKt.MutableStateFlow(Long.valueOf(this.sharedPreferences.getLong(str, 0L)));
    }
}
