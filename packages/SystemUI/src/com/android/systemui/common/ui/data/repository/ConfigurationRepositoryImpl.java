package com.android.systemui.common.ui.data.repository;

import android.content.Context;
import android.view.Display;
import android.view.DisplayInfo;
import com.android.systemui.statusbar.policy.ConfigurationController;
import com.android.systemui.util.wrapper.DisplayUtilsWrapper;
import com.android.systemui.utils.coroutines.flow.FlowConflatedKt;
import kotlinx.coroutines.CoroutineScope;
import kotlinx.coroutines.flow.Flow;
import kotlinx.coroutines.flow.FlowKt;
import kotlinx.coroutines.flow.ReadonlyStateFlow;
import kotlinx.coroutines.flow.SharingStarted;
import kotlinx.coroutines.flow.StateFlowImpl;
import kotlinx.coroutines.flow.StateFlowKt;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes.dex */
public final class ConfigurationRepositoryImpl implements ConfigurationRepository {
    public final ConfigurationController configurationController;
    public final Flow configurationValues;
    public final Context context;
    public final DisplayUtilsWrapper displayUtils;
    public final Flow onConfigurationChange;
    public final ReadonlyStateFlow onMovedToDisplay;
    public final ReadonlyStateFlow scaleForResolution;
    public final StateFlowImpl displayInfo = StateFlowKt.MutableStateFlow(new DisplayInfo());
    public final Flow onAnyConfigurationChange = FlowConflatedKt.conflatedCallbackFlow(new ConfigurationRepositoryImpl$onAnyConfigurationChange$1(this, null));

    public ConfigurationRepositoryImpl(ConfigurationController configurationController, Context context, CoroutineScope coroutineScope, DisplayUtilsWrapper displayUtilsWrapper) {
        this.configurationController = configurationController;
        this.context = context;
        this.displayUtils = displayUtilsWrapper;
        Flow conflatedCallbackFlow = FlowConflatedKt.conflatedCallbackFlow(new ConfigurationRepositoryImpl$onConfigurationChange$1(this, null));
        this.onConfigurationChange = conflatedCallbackFlow;
        this.configurationValues = FlowConflatedKt.conflatedCallbackFlow(new ConfigurationRepositoryImpl$configurationValues$1(this, null));
        Flow conflatedCallbackFlow2 = FlowConflatedKt.conflatedCallbackFlow(new ConfigurationRepositoryImpl$onMovedToDisplay$1(this, null));
        SharingStarted.Companion companion = SharingStarted.Companion;
        companion.getClass();
        this.onMovedToDisplay = FlowKt.stateIn(conflatedCallbackFlow2, coroutineScope, SharingStarted.Companion.Eagerly, 0);
        this.scaleForResolution = FlowKt.stateIn(FlowKt.distinctUntilChanged(FlowKt.mapLatest(conflatedCallbackFlow, new ConfigurationRepositoryImpl$scaleForResolution$1(this, null))), coroutineScope, SharingStarted.Companion.WhileSubscribed$default(companion, 3), Float.valueOf(getResolutionScale()));
    }

    public final float getResolutionScale() {
        Display display = this.context.getDisplay();
        StateFlowImpl stateFlowImpl = this.displayInfo;
        if (display != null) {
            display.getDisplayInfo((DisplayInfo) stateFlowImpl.getValue());
        }
        Display.Mode[] modeArr = ((DisplayInfo) stateFlowImpl.getValue()).supportedModes;
        DisplayUtilsWrapper displayUtilsWrapper = this.displayUtils;
        Display.Mode maximumResolutionDisplayMode = displayUtilsWrapper.getMaximumResolutionDisplayMode(modeArr);
        if (maximumResolutionDisplayMode == null) {
            return 1.0f;
        }
        float physicalPixelDisplaySizeRatio = displayUtilsWrapper.getPhysicalPixelDisplaySizeRatio(maximumResolutionDisplayMode.getPhysicalWidth(), maximumResolutionDisplayMode.getPhysicalHeight(), ((DisplayInfo) stateFlowImpl.getValue()).getNaturalWidth(), ((DisplayInfo) stateFlowImpl.getValue()).getNaturalHeight());
        if (physicalPixelDisplaySizeRatio == Float.POSITIVE_INFINITY) {
            return 1.0f;
        }
        return physicalPixelDisplaySizeRatio;
    }
}
