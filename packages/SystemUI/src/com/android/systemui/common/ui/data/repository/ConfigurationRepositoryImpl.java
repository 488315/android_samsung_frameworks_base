package com.android.systemui.common.ui.data.repository;

import android.content.Context;
import android.view.Display;
import android.view.DisplayInfo;
import com.android.systemui.common.coroutine.ConflatedCallbackFlow;
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

public final class ConfigurationRepositoryImpl implements ConfigurationRepository {
    public final ConfigurationController configurationController;
    public final Flow configurationValues;
    public final Context context;
    public final StateFlowImpl displayInfo = StateFlowKt.MutableStateFlow(new DisplayInfo());
    public final DisplayUtilsWrapper displayUtils;
    public final Flow onAnyConfigurationChange;
    public final Flow onConfigurationChange;
    public final ReadonlyStateFlow scaleForResolution;

    public ConfigurationRepositoryImpl(ConfigurationController configurationController, Context context, CoroutineScope coroutineScope, DisplayUtilsWrapper displayUtilsWrapper) {
        this.configurationController = configurationController;
        this.context = context;
        this.displayUtils = displayUtilsWrapper;
        ConflatedCallbackFlow conflatedCallbackFlow = ConflatedCallbackFlow.INSTANCE;
        ConfigurationRepositoryImpl$onAnyConfigurationChange$1 configurationRepositoryImpl$onAnyConfigurationChange$1 = new ConfigurationRepositoryImpl$onAnyConfigurationChange$1(this, null);
        conflatedCallbackFlow.getClass();
        this.onAnyConfigurationChange = FlowConflatedKt.conflatedCallbackFlow(configurationRepositoryImpl$onAnyConfigurationChange$1);
        Flow conflatedCallbackFlow2 = FlowConflatedKt.conflatedCallbackFlow(new ConfigurationRepositoryImpl$onConfigurationChange$1(this, null));
        this.onConfigurationChange = conflatedCallbackFlow2;
        this.configurationValues = FlowConflatedKt.conflatedCallbackFlow(new ConfigurationRepositoryImpl$configurationValues$1(this, null));
        this.scaleForResolution = FlowKt.stateIn(FlowKt.distinctUntilChanged(FlowKt.mapLatest(conflatedCallbackFlow2, new ConfigurationRepositoryImpl$scaleForResolution$1(this, null))), coroutineScope, SharingStarted.Companion.WhileSubscribed$default(SharingStarted.Companion, 3), Float.valueOf(getResolutionScale()));
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
