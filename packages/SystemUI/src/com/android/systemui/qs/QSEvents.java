package com.android.systemui.qs;

import com.android.internal.logging.UiEventLogger;
import com.android.internal.logging.UiEventLoggerImpl;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes2.dex */
public final class QSEvents {
    public static final QSEvents INSTANCE = new QSEvents();
    public static final UiEventLogger qsUiEventsLogger = new UiEventLoggerImpl();

    private QSEvents() {
    }
}
