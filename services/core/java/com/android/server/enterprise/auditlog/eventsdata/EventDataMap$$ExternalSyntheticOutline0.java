package com.android.server.enterprise.auditlog.eventsdata;

import com.android.server.enterprise.auditlog.eventsdata.types.EventComponent;
import com.android.server.enterprise.auditlog.eventsdata.types.EventGroup;
import com.android.server.enterprise.auditlog.eventsdata.types.EventMessage;
import com.android.server.enterprise.auditlog.eventsdata.types.EventOutcome;
import com.android.server.enterprise.auditlog.eventsdata.types.EventPrivacy;
import com.android.server.enterprise.auditlog.eventsdata.types.EventSeverity;

import java.util.Map;

public abstract /* synthetic */ class EventDataMap$$ExternalSyntheticOutline0 {
    public static Map.Entry m(int i, Integer num, String str, String str2) {
        return Map.entry(
                num,
                new EventData(
                        EventMessage.buildMessage(str),
                        new EventComponent(str2, 0),
                        new EventPrivacy(2),
                        new EventSeverity(5),
                        new EventGroup(i),
                        new EventOutcome(0)));
    }
}
