package com.android.systemui.media.mediaoutput.controller.media;

import kotlinx.coroutines.flow.EmptyFlow;
import kotlinx.coroutines.flow.Flow;

/* loaded from: classes2.dex */
public interface DeviceSession extends SessionController {
    @Override // com.android.systemui.media.mediaoutput.controller.media.SessionController
    default Flow getActionsFlow() {
        return EmptyFlow.INSTANCE;
    }

    @Override // com.android.systemui.media.mediaoutput.controller.media.SessionController
    default Flow getAppColorSchemeFlow() {
        return EmptyFlow.INSTANCE;
    }

    @Override // com.android.systemui.media.mediaoutput.controller.media.SessionController
    default Flow getDurationFlow() {
        return EmptyFlow.INSTANCE;
    }

    @Override // com.android.systemui.media.mediaoutput.controller.media.SessionController
    default Flow getMediaActionsFlow() {
        return EmptyFlow.INSTANCE;
    }

    @Override // com.android.systemui.media.mediaoutput.controller.media.SessionController
    default String getPackageName() {
        return ((DeviceSessionController) this).getId();
    }

    @Override // com.android.systemui.media.mediaoutput.controller.media.SessionController
    default Flow getPlaybackStateFlow() {
        return EmptyFlow.INSTANCE;
    }

    @Override // com.android.systemui.media.mediaoutput.controller.media.SessionController
    default Flow getPositionFlow() {
        return EmptyFlow.INSTANCE;
    }

    @Override // com.android.systemui.media.mediaoutput.controller.media.SessionController
    default Flow getThumbColorSchemeFlow() {
        return EmptyFlow.INSTANCE;
    }

    @Override // com.android.systemui.media.mediaoutput.controller.media.SessionController
    default Flow getThumbnailFlow() {
        return EmptyFlow.INSTANCE;
    }
}
