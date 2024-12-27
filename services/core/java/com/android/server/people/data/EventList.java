package com.android.server.people.data;

import java.util.ArrayList;
import java.util.List;

/* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
public final class EventList {
    public final List mEvents = new ArrayList();

    public final void add(Event event) {
        int firstIndexOnOrAfter = firstIndexOnOrAfter(event.mTimestamp);
        if (firstIndexOnOrAfter < ((ArrayList) this.mEvents).size()) {
            long j = ((Event) ((ArrayList) this.mEvents).get(firstIndexOnOrAfter)).mTimestamp;
            long j2 = event.mTimestamp;
            if (j == j2) {
                int size = ((ArrayList) this.mEvents).size();
                int i = firstIndexOnOrAfter;
                while (i < size && ((Event) ((ArrayList) this.mEvents).get(i)).mTimestamp <= j2) {
                    int i2 = i + 1;
                    if (((Event) ((ArrayList) this.mEvents).get(i)).mType == event.mType) {
                        return;
                    } else {
                        i = i2;
                    }
                }
            }
        }
        ((ArrayList) this.mEvents).add(firstIndexOnOrAfter, event);
    }

    public final int firstIndexOnOrAfter(long j) {
        int size = ((ArrayList) this.mEvents).size();
        int size2 = ((ArrayList) this.mEvents).size() - 1;
        int i = 0;
        while (i <= size2) {
            int i2 = (i + size2) >>> 1;
            if (((Event) ((ArrayList) this.mEvents).get(i2)).mTimestamp >= j) {
                size2 = i2 - 1;
                size = i2;
            } else {
                i = i2 + 1;
            }
        }
        return size;
    }

    public final void removeOldEvents(long j) {
        int firstIndexOnOrAfter = firstIndexOnOrAfter(j);
        if (firstIndexOnOrAfter == 0) {
            return;
        }
        int size = ((ArrayList) this.mEvents).size();
        if (firstIndexOnOrAfter == size) {
            ((ArrayList) this.mEvents).clear();
            return;
        }
        int i = 0;
        while (firstIndexOnOrAfter < size) {
            List list = this.mEvents;
            ((ArrayList) list).set(i, (Event) ((ArrayList) list).get(firstIndexOnOrAfter));
            i++;
            firstIndexOnOrAfter++;
        }
        if (size > i) {
            ((ArrayList) this.mEvents).subList(i, size).clear();
        }
    }
}
