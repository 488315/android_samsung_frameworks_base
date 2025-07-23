package com.android.systemui.samsung.quicksetting;

import com.android.systemui.shade.ShadeController;
import java.util.LinkedHashMap;
import java.util.Map;
import kotlinx.coroutines.flow.FlowKt;
import kotlinx.coroutines.flow.ReadonlyStateFlow;
import kotlinx.coroutines.flow.StateFlowImpl;
import kotlinx.coroutines.flow.StateFlowKt;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
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
        StateFlowImpl MutableStateFlow = StateFlowKt.MutableStateFlow(bool);
        this._isScrollable = MutableStateFlow;
        FlowKt.asStateFlow(MutableStateFlow);
        StateFlowImpl MutableStateFlow2 = StateFlowKt.MutableStateFlow(Boolean.TRUE);
        this._isPanelSlidable = MutableStateFlow2;
        FlowKt.asStateFlow(MutableStateFlow2);
        StateFlowImpl MutableStateFlow3 = StateFlowKt.MutableStateFlow(bool);
        this._legacyDetailVisible = MutableStateFlow3;
        this.legacyDetailVisible = FlowKt.asStateFlow(MutableStateFlow3);
        StateFlowImpl MutableStateFlow4 = StateFlowKt.MutableStateFlow(bool);
        this._detailVisible = MutableStateFlow4;
        FlowKt.asStateFlow(MutableStateFlow4);
        Float valueOf = Float.valueOf(0.0f);
        StateFlowImpl MutableStateFlow5 = StateFlowKt.MutableStateFlow(valueOf);
        this._slideFraction = MutableStateFlow5;
        this.slideFraction = FlowKt.asStateFlow(MutableStateFlow5);
        StateFlowImpl MutableStateFlow6 = StateFlowKt.MutableStateFlow(valueOf);
        this._expansionFraction = MutableStateFlow6;
        this.expansionFraction = FlowKt.asStateFlow(MutableStateFlow6);
    }
}
