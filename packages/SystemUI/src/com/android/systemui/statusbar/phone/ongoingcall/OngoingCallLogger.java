package com.android.systemui.statusbar.phone.ongoingcall;

import com.android.internal.logging.UiEventLogger;
import kotlin.enums.EnumEntriesKt;

public final class OngoingCallLogger {
    public boolean chipIsVisible;
    public final UiEventLogger logger;

    /* JADX WARN: Failed to restore enum class, 'enum' modifier and super class removed */
    /* JADX WARN: Unknown enum class pattern. Please report as an issue! */
    public final class OngoingCallEvents implements UiEventLogger.UiEventEnum {
        public static final /* synthetic */ OngoingCallEvents[] $VALUES;
        public static final OngoingCallEvents ONGOING_CALL_CLICKED;
        public static final OngoingCallEvents ONGOING_CALL_VISIBLE;
        private final int metricId;

        static {
            OngoingCallEvents ongoingCallEvents = new OngoingCallEvents("ONGOING_CALL_VISIBLE", 0, 813);
            ONGOING_CALL_VISIBLE = ongoingCallEvents;
            OngoingCallEvents ongoingCallEvents2 = new OngoingCallEvents("ONGOING_CALL_CLICKED", 1, 814);
            ONGOING_CALL_CLICKED = ongoingCallEvents2;
            OngoingCallEvents[] ongoingCallEventsArr = {ongoingCallEvents, ongoingCallEvents2};
            $VALUES = ongoingCallEventsArr;
            EnumEntriesKt.enumEntries(ongoingCallEventsArr);
        }

        private OngoingCallEvents(String str, int i, int i2) {
            this.metricId = i2;
        }

        public static OngoingCallEvents valueOf(String str) {
            return (OngoingCallEvents) Enum.valueOf(OngoingCallEvents.class, str);
        }

        public static OngoingCallEvents[] values() {
            return (OngoingCallEvents[]) $VALUES.clone();
        }

        public final int getId() {
            return this.metricId;
        }
    }

    public OngoingCallLogger(UiEventLogger uiEventLogger) {
        this.logger = uiEventLogger;
    }
}
