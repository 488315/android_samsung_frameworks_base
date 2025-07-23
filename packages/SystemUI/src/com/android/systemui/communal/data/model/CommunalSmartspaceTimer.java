package com.android.systemui.communal.data.model;

import android.widget.RemoteViews;
import defpackage.MoveResult$$ExternalSyntheticOutline0;
import kotlin.jvm.internal.Intrinsics;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes2.dex */
public final class CommunalSmartspaceTimer {
    public final long createdTimestampMillis;
    public final RemoteViews remoteViews;
    public final String smartspaceTargetId;

    public CommunalSmartspaceTimer(String str, long j, RemoteViews remoteViews) {
        this.smartspaceTargetId = str;
        this.createdTimestampMillis = j;
        this.remoteViews = remoteViews;
    }

    public final boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (!(obj instanceof CommunalSmartspaceTimer)) {
            return false;
        }
        CommunalSmartspaceTimer communalSmartspaceTimer = (CommunalSmartspaceTimer) obj;
        return Intrinsics.areEqual(this.smartspaceTargetId, communalSmartspaceTimer.smartspaceTargetId) && this.createdTimestampMillis == communalSmartspaceTimer.createdTimestampMillis && Intrinsics.areEqual(this.remoteViews, communalSmartspaceTimer.remoteViews);
    }

    public final int hashCode() {
        return this.remoteViews.hashCode() + MoveResult$$ExternalSyntheticOutline0.m(this.smartspaceTargetId.hashCode() * 31, 31, this.createdTimestampMillis);
    }

    public final String toString() {
        return "CommunalSmartspaceTimer(smartspaceTargetId=" + this.smartspaceTargetId + ", createdTimestampMillis=" + this.createdTimestampMillis + ", remoteViews=" + this.remoteViews + ")";
    }
}
