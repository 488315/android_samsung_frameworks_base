package com.android.systemui.statusbar.phone.ongoingcall;

import com.android.internal.logging.UiEventLogger;

/* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
/* loaded from: classes2.dex */
public final class OngoingCallLogger {
    public boolean chipIsVisible;
    public final UiEventLogger logger;

    /* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
    public enum OngoingCallEvents implements UiEventLogger.UiEventEnum {
        ONGOING_CALL_VISIBLE(813),
        ONGOING_CALL_CLICKED(814);

        private final int metricId;

        OngoingCallEvents(int i) {
            this.metricId = i;
        }

        public final int getId() {
            return this.metricId;
        }
    }

    public OngoingCallLogger(UiEventLogger uiEventLogger) {
        this.logger = uiEventLogger;
    }
}
