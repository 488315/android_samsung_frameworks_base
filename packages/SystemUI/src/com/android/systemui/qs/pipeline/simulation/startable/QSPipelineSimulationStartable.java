package com.android.systemui.qs.pipeline.simulation.startable;

import com.android.systemui.CoreStartable;
import com.android.systemui.qs.pipeline.simulation.interactor.SimulationInteractor;

/* loaded from: classes2.dex */
public final class QSPipelineSimulationStartable implements CoreStartable {
    public final SimulationInteractor simulationInteractor;

    public QSPipelineSimulationStartable(SimulationInteractor simulationInteractor) {
        this.simulationInteractor = simulationInteractor;
    }

    @Override // com.android.systemui.CoreStartable
    public final void start() {
        this.simulationInteractor.init();
    }
}
