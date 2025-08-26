package com.android.systemui.volume.panel.domain.interactor;

import com.android.systemui.log.LogBuffer;
import com.android.systemui.log.LogMessageImpl;
import com.android.systemui.log.core.LogLevel;
import com.android.systemui.log.core.LogMessage;
import com.android.systemui.volume.panel.data.repository.VolumePanelGlobalStateRepository;
import com.android.systemui.volume.panel.shared.VolumePanelLogger;
import com.android.systemui.volume.panel.shared.VolumePanelLogger$$ExternalSyntheticLambda0;
import com.android.systemui.volume.panel.shared.model.VolumePanelGlobalState;
import kotlinx.coroutines.flow.StateFlowImpl;

/* loaded from: classes3.dex */
public final class VolumePanelGlobalStateInteractor {
    public final VolumePanelGlobalStateRepository repository;

    public VolumePanelGlobalStateInteractor(VolumePanelGlobalStateRepository volumePanelGlobalStateRepository) {
        this.repository = volumePanelGlobalStateRepository;
    }

    public final void setVisible(boolean z) {
        Object value;
        VolumePanelGlobalStateRepository volumePanelGlobalStateRepository = this.repository;
        StateFlowImpl stateFlowImpl = volumePanelGlobalStateRepository.mutableGlobalState;
        do {
            value = stateFlowImpl.getValue();
            ((VolumePanelGlobalState) value).getClass();
        } while (!stateFlowImpl.compareAndSet(value, new VolumePanelGlobalState(z)));
        VolumePanelGlobalState volumePanelGlobalState = (VolumePanelGlobalState) stateFlowImpl.getValue();
        VolumePanelLogger volumePanelLogger = volumePanelGlobalStateRepository.logger;
        volumePanelLogger.getClass();
        LogLevel logLevel = LogLevel.DEBUG;
        VolumePanelLogger$$ExternalSyntheticLambda0 volumePanelLogger$$ExternalSyntheticLambda0 = new VolumePanelLogger$$ExternalSyntheticLambda0(7);
        LogBuffer logBuffer = volumePanelLogger.logBuffer;
        LogMessage logMessageObtain = logBuffer.obtain("SysUI_VolumePanel", logLevel, volumePanelLogger$$ExternalSyntheticLambda0, null);
        ((LogMessageImpl) logMessageObtain).bool1 = volumePanelGlobalState.isVisible;
        logBuffer.commit(logMessageObtain);
    }
}
