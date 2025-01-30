package com.android.systemui.multishade.data.repository;

import android.content.Context;
import com.android.systemui.R;
import com.android.systemui.multishade.data.remoteproxy.MultiShadeInputProxy;
import com.android.systemui.multishade.shared.model.ShadeConfig;
import java.util.LinkedHashMap;
import java.util.Map;
import kotlinx.coroutines.flow.FlowKt;
import kotlinx.coroutines.flow.ReadonlySharedFlow;
import kotlinx.coroutines.flow.ReadonlyStateFlow;
import kotlinx.coroutines.flow.StateFlowImpl;
import kotlinx.coroutines.flow.StateFlowKt;

/* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
/* loaded from: classes.dex */
public final class MultiShadeRepository {
    public final StateFlowImpl _forceCollapseAll;
    public final StateFlowImpl _shadeInteraction;
    public final ReadonlyStateFlow forceCollapseAll;
    public final ReadonlySharedFlow proxiedInput;
    public final ReadonlyStateFlow shadeConfig;
    public final ReadonlyStateFlow shadeInteraction;
    public final Map stateByShade;

    public MultiShadeRepository(Context context, MultiShadeInputProxy multiShadeInputProxy) {
        this.proxiedInput = multiShadeInputProxy.proxiedInput;
        int dimensionPixelSize = context.getResources().getDimensionPixelSize(R.dimen.left_shade_width);
        int dimensionPixelSize2 = context.getResources().getDimensionPixelSize(R.dimen.right_shade_width);
        float f = context.getResources().getFloat(R.dimen.shade_swipe_collapse_threshold);
        checkInBounds(f);
        float f2 = context.getResources().getFloat(R.dimen.shade_swipe_expand_threshold);
        checkInBounds(f2);
        float f3 = context.getResources().getFloat(R.dimen.dual_shade_scrim_alpha);
        checkInBounds(f3);
        this.shadeConfig = FlowKt.asStateFlow(StateFlowKt.MutableStateFlow(context.getResources().getBoolean(R.bool.dual_shade_enabled) ? new ShadeConfig.DualShadeConfig(dimensionPixelSize, dimensionPixelSize2, f, f2, context.getResources().getFloat(R.dimen.dual_shade_split_fraction), f3) : new ShadeConfig.SingleShadeConfig(f, f2)));
        StateFlowImpl MutableStateFlow = StateFlowKt.MutableStateFlow(Boolean.FALSE);
        this._forceCollapseAll = MutableStateFlow;
        this.forceCollapseAll = FlowKt.asStateFlow(MutableStateFlow);
        StateFlowImpl MutableStateFlow2 = StateFlowKt.MutableStateFlow(null);
        this._shadeInteraction = MutableStateFlow2;
        this.shadeInteraction = FlowKt.asStateFlow(MutableStateFlow2);
        this.stateByShade = new LinkedHashMap();
    }

    public static void checkInBounds(float f) {
        if (0.0f <= f && f <= 1.0f) {
            return;
        }
        throw new IllegalStateException((f + " isn't between 0 and 1.").toString());
    }
}
