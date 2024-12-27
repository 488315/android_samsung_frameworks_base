package com.android.server.enterprise.auditlog.eventsdata.types;

public final class EventOutcome {
    public boolean outcome;
    public final int outcomeType;

    public EventOutcome(int i) {
        this.outcomeType = i;
    }
}
