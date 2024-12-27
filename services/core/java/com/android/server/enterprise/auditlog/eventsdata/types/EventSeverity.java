package com.android.server.enterprise.auditlog.eventsdata.types;

public final class EventSeverity {
    public int severity = 5;
    public final int severityType;

    public EventSeverity(int i) {
        this.severityType = i;
    }
}
