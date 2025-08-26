package com.samsung.systemui.splugins.volume;

import java.util.List;

/* loaded from: classes4.dex */
public interface VolumeStarDependency {
    List<VolumeMiddleware<?, ?>> getDefaultMiddlewares();

    VolumePanelReducerBase getDefaultReducer();

    VolumeInfraMediator getInfraMediator();

    ExtendableVolumePanel getVolumePanel();
}
