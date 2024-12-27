package com.android.server.enterprise.auditlog.eventsdata;

import com.android.server.enterprise.auditlog.eventsdata.types.EventComponent;
import com.android.server.enterprise.auditlog.eventsdata.types.EventGroup;
import com.android.server.enterprise.auditlog.eventsdata.types.EventMessage;
import com.android.server.enterprise.auditlog.eventsdata.types.EventOutcome;
import com.android.server.enterprise.auditlog.eventsdata.types.EventPrivacy;
import com.android.server.enterprise.auditlog.eventsdata.types.EventSeverity;

public final class EventData {
    public final EventComponent component;
    public final EventGroup group;
    public final EventMessage message;
    public final EventOutcome outcome;
    public final EventPrivacy privacy;
    public final EventSeverity severity;

    public EventData(
            EventMessage eventMessage,
            EventComponent eventComponent,
            EventPrivacy eventPrivacy,
            EventSeverity eventSeverity,
            EventGroup eventGroup,
            EventOutcome eventOutcome) {
        this.message = eventMessage;
        this.privacy = eventPrivacy;
        this.component = eventComponent;
        this.severity = eventSeverity;
        this.group = eventGroup;
        this.outcome = eventOutcome;
    }

    public static EventData createEvent(int i, int i2, int i3, String str, String str2, int i4) {
        return new EventData(
                new EventMessage(str, 0),
                new EventComponent(str2, 0),
                new EventPrivacy(i),
                new EventSeverity(i2),
                new EventGroup(i3),
                new EventOutcome(i4));
    }
}
