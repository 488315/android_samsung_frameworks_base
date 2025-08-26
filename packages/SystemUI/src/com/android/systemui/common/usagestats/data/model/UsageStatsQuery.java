package com.android.systemui.common.usagestats.data.model;

import android.os.UserHandle;
import defpackage.MoveResult$$ExternalSyntheticOutline0;
import java.util.List;
import kotlin.collections.EmptyList;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.Intrinsics;

/* loaded from: classes.dex */
public final class UsageStatsQuery {
    public final long endTime;
    public final List packageNames;
    public final long startTime;
    public final UserHandle user;

    public UsageStatsQuery(UserHandle userHandle, long j, long j2, List<String> list) {
        this.user = userHandle;
        this.startTime = j;
        this.endTime = j2;
        this.packageNames = list;
    }

    public final boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (!(obj instanceof UsageStatsQuery)) {
            return false;
        }
        UsageStatsQuery usageStatsQuery = (UsageStatsQuery) obj;
        return Intrinsics.areEqual(this.user, usageStatsQuery.user) && this.startTime == usageStatsQuery.startTime && this.endTime == usageStatsQuery.endTime && Intrinsics.areEqual(this.packageNames, usageStatsQuery.packageNames);
    }

    public final int hashCode() {
        return this.packageNames.hashCode() + MoveResult$$ExternalSyntheticOutline0.m(MoveResult$$ExternalSyntheticOutline0.m(this.user.hashCode() * 31, 31, this.startTime), 31, this.endTime);
    }

    public final String toString() {
        return "UsageStatsQuery(user=" + this.user + ", startTime=" + this.startTime + ", endTime=" + this.endTime + ", packageNames=" + this.packageNames + ")";
    }

    public UsageStatsQuery(UserHandle userHandle, long j, long j2, List list, int i, DefaultConstructorMarker defaultConstructorMarker) {
        this(userHandle, j, j2, (i & 8) != 0 ? EmptyList.INSTANCE : list);
    }
}
