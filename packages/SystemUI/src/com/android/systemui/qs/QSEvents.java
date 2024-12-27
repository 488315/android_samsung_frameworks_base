package com.android.systemui.qs;

import com.android.internal.logging.UiEventLogger;
import com.android.internal.logging.UiEventLoggerImpl;

/* compiled from: qb/89523975 427a50d40ec74a85ca352b86f77450b1c52ece5389e11158752b0d641a3a5098 */
/* loaded from: classes2.dex */
public final class QSEvents {
    public static final QSEvents INSTANCE = new QSEvents();
    public static final UiEventLogger qsUiEventsLogger = new UiEventLoggerImpl();

    private QSEvents() {
    }
}
