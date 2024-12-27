package com.android.systemui.display.data.repository;

import android.content.Context;
import android.util.DisplayMetrics;
import com.android.systemui.common.coroutine.ConflatedCallbackFlow;
import com.android.systemui.log.LogBuffer;
import com.android.systemui.statusbar.policy.ConfigurationController;
import com.android.systemui.utils.coroutines.flow.FlowConflatedKt;
import kotlinx.coroutines.CoroutineScope;
import kotlinx.coroutines.flow.FlowKt;
import kotlinx.coroutines.flow.FlowKt__TransformKt$onEach$$inlined$unsafeTransform$1;
import kotlinx.coroutines.flow.SharingStarted;

/* compiled from: qb/89523975 427a50d40ec74a85ca352b86f77450b1c52ece5389e11158752b0d641a3a5098 */
public final class DisplayMetricsRepository {
    public DisplayMetricsRepository(CoroutineScope coroutineScope, ConfigurationController configurationController, DisplayMetrics displayMetrics, Context context, LogBuffer logBuffer) {
        ConflatedCallbackFlow conflatedCallbackFlow = ConflatedCallbackFlow.INSTANCE;
        DisplayMetricsRepository$displayMetrics$1 displayMetricsRepository$displayMetrics$1 = new DisplayMetricsRepository$displayMetrics$1(configurationController, context, displayMetrics, null);
        conflatedCallbackFlow.getClass();
        FlowKt__TransformKt$onEach$$inlined$unsafeTransform$1 flowKt__TransformKt$onEach$$inlined$unsafeTransform$1 = new FlowKt__TransformKt$onEach$$inlined$unsafeTransform$1(FlowConflatedKt.conflatedCallbackFlow(displayMetricsRepository$displayMetrics$1), new DisplayMetricsRepository$displayMetrics$2(logBuffer, null));
        SharingStarted.Companion.getClass();
        FlowKt.stateIn(flowKt__TransformKt$onEach$$inlined$unsafeTransform$1, coroutineScope, SharingStarted.Companion.Eagerly, displayMetrics);
    }
}
