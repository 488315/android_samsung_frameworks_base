package com.android.server.sepunion.eventdelegator;

import android.app.PendingIntent;
import java.util.ArrayList;

/* loaded from: classes3.dex */
public class PendingIntentWithConditions {
    public ArrayList mConditions;
    public int mFlag;
    public PendingIntent mPendingIntent;

    public PendingIntentWithConditions(PendingIntent pendingIntent, int i, ArrayList arrayList) {
        this.mPendingIntent = pendingIntent;
        this.mFlag = i;
        this.mConditions = arrayList;
    }

    public PendingIntent getPendingIntent() {
        return this.mPendingIntent;
    }

    public int getFlag() {
        return this.mFlag;
    }

    public ArrayList getConditions() {
        return this.mConditions;
    }

    public boolean equals(Object obj) {
        if (obj instanceof PendingIntentWithConditions) {
            return this.mPendingIntent.equals(((PendingIntentWithConditions) obj).mPendingIntent);
        }
        return false;
    }
}
