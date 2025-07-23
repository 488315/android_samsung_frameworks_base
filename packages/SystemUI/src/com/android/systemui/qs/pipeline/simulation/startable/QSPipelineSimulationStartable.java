package com.android.systemui.qs.pipeline.simulation.startable;

import com.android.systemui.CoreStartable;
import com.android.systemui.qs.pipeline.simulation.interactor.SimulationInteractor;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
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
