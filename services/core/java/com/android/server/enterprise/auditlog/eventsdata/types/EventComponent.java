package com.android.server.enterprise.auditlog.eventsdata.types;

public final class EventComponent {
    public String component;
    public final int componentMode;

    public EventComponent(String str, int i) {
        this.component = str;
        this.componentMode = i;
    }
}
