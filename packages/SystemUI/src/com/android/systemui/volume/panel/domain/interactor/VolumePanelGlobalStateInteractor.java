package com.android.systemui.volume.panel.domain.interactor;

import com.android.systemui.volume.panel.data.repository.VolumePanelGlobalStateRepository;
import com.android.systemui.volume.panel.shared.model.VolumePanelGlobalState;
import kotlin.jvm.functions.Function1;
import kotlinx.coroutines.flow.StateFlowImpl;

public final class VolumePanelGlobalStateInteractor {
    public final VolumePanelGlobalStateRepository repository;

    public VolumePanelGlobalStateInteractor(VolumePanelGlobalStateRepository volumePanelGlobalStateRepository) {
        this.repository = volumePanelGlobalStateRepository;
    }

    public final void setVisible(final boolean z) {
        Object value;
        Function1 function1 = new Function1() { // from class: com.android.systemui.volume.panel.domain.interactor.VolumePanelGlobalStateInteractor$setVisible$1
            {
                super(1);
            }

            @Override // kotlin.jvm.functions.Function1
            public final Object invoke(Object obj) {
                return new VolumePanelGlobalState(z);
            }
        };
        StateFlowImpl stateFlowImpl = this.repository.mutableGlobalState;
        do {
            value = stateFlowImpl.getValue();
        } while (!stateFlowImpl.compareAndSet(value, function1.invoke(value)));
    }
}
