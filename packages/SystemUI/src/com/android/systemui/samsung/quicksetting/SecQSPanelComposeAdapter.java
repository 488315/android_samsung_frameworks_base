package com.android.systemui.samsung.quicksetting;

import com.android.systemui.shade.ShadeController;
import java.util.LinkedHashMap;
import java.util.Map;
import kotlinx.coroutines.flow.FlowKt;
import kotlinx.coroutines.flow.ReadonlyStateFlow;
import kotlinx.coroutines.flow.StateFlowImpl;
import kotlinx.coroutines.flow.StateFlowKt;

/* loaded from: classes2.dex */
public final class SecQSPanelComposeAdapter {
    public final StateFlowImpl _detailVisible;
    public final StateFlowImpl _expansionFraction;
    public final StateFlowImpl _isPanelSlidable;
    public final StateFlowImpl _isScrollable;
    public final StateFlowImpl _legacyDetailVisible;
    public final StateFlowImpl _slideFraction;
    public final ReadonlyStateFlow expansionFraction;
    public final ReadonlyStateFlow legacyDetailVisible;
    public final ShadeController shadeController;
    public final Map slidableAreas = new LinkedHashMap();
    public final ReadonlyStateFlow slideFraction;

    public SecQSPanelComposeAdapter(ShadeController shadeController) {
        this.shadeController = shadeController;
        Boolean bool = Boolean.FALSE;
        StateFlowImpl stateFlowImplMutableStateFlow = StateFlowKt.MutableStateFlow(bool);
        this._isScrollable = stateFlowImplMutableStateFlow;
        FlowKt.asStateFlow(stateFlowImplMutableStateFlow);
        StateFlowImpl stateFlowImplMutableStateFlow2 = StateFlowKt.MutableStateFlow(Boolean.TRUE);
        this._isPanelSlidable = stateFlowImplMutableStateFlow2;
        FlowKt.asStateFlow(stateFlowImplMutableStateFlow2);
        StateFlowImpl stateFlowImplMutableStateFlow3 = StateFlowKt.MutableStateFlow(bool);
        this._legacyDetailVisible = stateFlowImplMutableStateFlow3;
        this.legacyDetailVisible = FlowKt.asStateFlow(stateFlowImplMutableStateFlow3);
        StateFlowImpl stateFlowImplMutableStateFlow4 = StateFlowKt.MutableStateFlow(bool);
        this._detailVisible = stateFlowImplMutableStateFlow4;
        FlowKt.asStateFlow(stateFlowImplMutableStateFlow4);
        Float fValueOf = Float.valueOf(0.0f);
        StateFlowImpl stateFlowImplMutableStateFlow5 = StateFlowKt.MutableStateFlow(fValueOf);
        this._slideFraction = stateFlowImplMutableStateFlow5;
        this.slideFraction = FlowKt.asStateFlow(stateFlowImplMutableStateFlow5);
        StateFlowImpl stateFlowImplMutableStateFlow6 = StateFlowKt.MutableStateFlow(fValueOf);
        this._expansionFraction = stateFlowImplMutableStateFlow6;
        this.expansionFraction = FlowKt.asStateFlow(stateFlowImplMutableStateFlow6);
    }
}
