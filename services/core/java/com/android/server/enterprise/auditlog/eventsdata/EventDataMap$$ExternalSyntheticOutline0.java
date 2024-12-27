package com.android.server.enterprise.auditlog.eventsdata;

import com.android.server.enterprise.auditlog.eventsdata.types.EventComponent;
import com.android.server.enterprise.auditlog.eventsdata.types.EventGroup;
import com.android.server.enterprise.auditlog.eventsdata.types.EventMessage;
import com.android.server.enterprise.auditlog.eventsdata.types.EventOutcome;
import com.android.server.enterprise.auditlog.eventsdata.types.EventPrivacy;
import com.android.server.enterprise.auditlog.eventsdata.types.EventSeverity;

import java.util.Map;

/* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
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
