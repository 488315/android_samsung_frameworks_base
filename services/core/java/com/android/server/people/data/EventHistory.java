package com.android.server.people.data;

import java.util.Set;

public interface EventHistory {
    EventIndex getEventIndex(int i);

    EventIndex getEventIndex(Set set);
}
