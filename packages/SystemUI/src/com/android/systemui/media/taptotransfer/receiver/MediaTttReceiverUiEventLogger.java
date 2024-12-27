package com.android.systemui.media.taptotransfer.receiver;

import com.android.internal.logging.UiEventLogger;

public final class MediaTttReceiverUiEventLogger {
    public final UiEventLogger logger;

    public MediaTttReceiverUiEventLogger(UiEventLogger uiEventLogger) {
        this.logger = uiEventLogger;
    }
}
