package com.android.server.sepunion.eventdelegator;

import android.app.PendingIntent;

import java.util.ArrayList;

/* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
public final class PendingIntentWithConditions {
    public final ArrayList mConditions;
    public final int mFlag;
    public final PendingIntent mPendingIntent;

    public PendingIntentWithConditions(PendingIntent pendingIntent, int i, ArrayList arrayList) {
        this.mPendingIntent = pendingIntent;
        this.mFlag = i;
        this.mConditions = arrayList;
    }

    public final boolean equals(Object obj) {
        if (obj instanceof PendingIntentWithConditions) {
            return this.mPendingIntent.equals(((PendingIntentWithConditions) obj).mPendingIntent);
        }
        return false;
    }

    public final int hashCode() {
        PendingIntent pendingIntent = this.mPendingIntent;
        int hashCode =
                (((527 + (pendingIntent != null ? pendingIntent.hashCode() : 0)) * 31) + this.mFlag)
                        * 31;
        ArrayList arrayList = this.mConditions;
        return hashCode + (arrayList != null ? arrayList.hashCode() : 0);
    }
}
