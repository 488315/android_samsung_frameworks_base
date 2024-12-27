package com.android.server.wm;

import com.android.server.uri.NeededUriGrants;

/* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
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
