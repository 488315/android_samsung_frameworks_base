package com.samsung.systemui.splugins.volume;

import java.util.List;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes4.dex */
public interface VolumeStarDependency {
    List<VolumeMiddleware<?, ?>> getDefaultMiddlewares();

    VolumePanelReducerBase getDefaultReducer();

    VolumeInfraMediator getInfraMediator();

    ExtendableVolumePanel getVolumePanel();
}
