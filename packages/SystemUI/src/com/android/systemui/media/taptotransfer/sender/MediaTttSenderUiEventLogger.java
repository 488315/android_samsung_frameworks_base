package com.android.systemui.media.taptotransfer.sender;

import com.android.internal.logging.UiEventLogger;

public final class MediaTttSenderUiEventLogger {
    public final UiEventLogger logger;

    public MediaTttSenderUiEventLogger(UiEventLogger uiEventLogger) {
        this.logger = uiEventLogger;
    }
}
