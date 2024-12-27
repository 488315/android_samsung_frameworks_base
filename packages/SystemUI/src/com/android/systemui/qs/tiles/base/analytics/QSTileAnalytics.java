package com.android.systemui.qs.tiles.base.analytics;

import com.android.internal.logging.UiEventLogger;

public final class QSTileAnalytics {
    public final UiEventLogger uiEventLogger;

    public QSTileAnalytics(UiEventLogger uiEventLogger) {
        this.uiEventLogger = uiEventLogger;
    }
}
