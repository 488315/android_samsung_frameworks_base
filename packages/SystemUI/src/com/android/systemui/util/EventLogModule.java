package com.android.systemui.util;

public interface EventLogModule {
    EventLog bindEventLog(EventLogImpl eventLogImpl);
}
