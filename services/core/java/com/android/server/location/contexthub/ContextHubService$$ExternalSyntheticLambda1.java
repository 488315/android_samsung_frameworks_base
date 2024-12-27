package com.android.server.location.contexthub;

import java.util.Comparator;

public final /* synthetic */ class ContextHubService$$ExternalSyntheticLambda1
        implements Comparator {
    @Override // java.util.Comparator
    public final int compare(Object obj, Object obj2) {
        return Long.compare(
                ((ContextHubService.ReliableMessageRecord) obj).mTimestamp,
                ((ContextHubService.ReliableMessageRecord) obj2).mTimestamp);
    }
}
