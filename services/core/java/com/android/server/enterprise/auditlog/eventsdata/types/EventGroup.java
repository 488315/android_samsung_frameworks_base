package com.android.server.enterprise.auditlog.eventsdata.types;

import android.util.Log;

public final class EventGroup {
    public final int group;

    public EventGroup(int i) {
        if (i == 0) {
            this.group = 5;
            return;
        }
        if (i == 1) {
            this.group = 1;
            return;
        }
        if (i == 2) {
            this.group = 2;
            return;
        }
        if (i == 3) {
            this.group = 4;
        } else if (i != 4) {
            Log.d("EventGroup", "Unknown group");
        } else {
            this.group = 3;
        }
    }
}
