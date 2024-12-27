package com.android.systemui.media.controls.domain.pipeline.interactor.factory;

import com.android.internal.logging.InstanceId;
import com.android.systemui.media.controls.domain.pipeline.interactor.MediaControlInteractor;

public interface MediaControlInteractorFactory {
    MediaControlInteractor create(InstanceId instanceId);
}
