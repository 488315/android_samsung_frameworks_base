package com.android.systemui.qs;

import com.android.internal.logging.UiEventLogger;
import com.android.internal.logging.UiEventLoggerImpl;

public final class QSEvents {
    public static final QSEvents INSTANCE = new QSEvents();
    public static final UiEventLogger qsUiEventsLogger = new UiEventLoggerImpl();

    private QSEvents() {
    }
}
