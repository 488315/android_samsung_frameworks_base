package com.android.server.wm;

import com.android.server.uri.NeededUriGrants;

public final class PendingActivityLaunch {
    public final NeededUriGrants intentGrants;
    public final ActivityRecord r;
    public final ActivityRecord sourceRecord;
    public final int startFlags;

    public PendingActivityLaunch(
            ActivityRecord activityRecord,
            ActivityRecord activityRecord2,
            int i,
            NeededUriGrants neededUriGrants) {
        this.r = activityRecord;
        this.sourceRecord = activityRecord2;
        this.startFlags = i;
        this.intentGrants = neededUriGrants;
    }
}
