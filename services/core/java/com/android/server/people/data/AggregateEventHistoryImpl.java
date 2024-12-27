package com.android.server.people.data;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

public final class AggregateEventHistoryImpl implements EventHistory {
    public final List mEventHistoryList = new ArrayList();

    @Override // com.android.server.people.data.EventHistory
    public final EventIndex getEventIndex(int i) {
        Iterator it = ((ArrayList) this.mEventHistoryList).iterator();
        while (it.hasNext()) {
            EventIndex eventIndex = ((EventHistory) it.next()).getEventIndex(i);
            if (!eventIndex.isEmpty()) {
                return eventIndex;
            }
        }
        return EventIndex.EMPTY;
    }

    @Override // com.android.server.people.data.EventHistory
    public final EventIndex getEventIndex(Set set) {
        Iterator it = ((ArrayList) this.mEventHistoryList).iterator();
        EventIndex eventIndex = null;
        while (it.hasNext()) {
            EventIndex eventIndex2 = ((EventHistory) it.next()).getEventIndex(set);
            if (eventIndex == null) {
                eventIndex = eventIndex2;
            } else if (!eventIndex2.isEmpty()) {
                eventIndex = EventIndex.combine(eventIndex, eventIndex2);
            }
        }
        return eventIndex != null ? eventIndex : EventIndex.EMPTY;
    }
}
