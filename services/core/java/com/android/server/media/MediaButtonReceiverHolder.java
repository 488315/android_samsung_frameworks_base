package com.android.server.media;

import android.app.PendingIntent;
import android.content.ComponentName;
import android.hardware.audio.common.V2_0.AudioOffloadInfo$$ExternalSyntheticOutline0;

/* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
/* loaded from: classes.dex */
public final class MediaButtonReceiverHolder {
    public final ComponentName mComponentName;
    public final int mComponentType;
    public final String mPackageName;
    public final PendingIntent mPendingIntent;
    public final int mUserId;

    public MediaButtonReceiverHolder(
            int i, PendingIntent pendingIntent, ComponentName componentName, int i2) {
        this.mUserId = i;
        this.mPendingIntent = pendingIntent;
        this.mComponentName = componentName;
        this.mPackageName = componentName.getPackageName();
        this.mComponentType = i2;
    }

    public MediaButtonReceiverHolder(String str, PendingIntent pendingIntent, int i) {
        this.mUserId = i;
        this.mPendingIntent = pendingIntent;
        this.mComponentName = null;
        this.mPackageName = str;
        this.mComponentType = 0;
    }

    public final String toString() {
        StringBuilder sb = new StringBuilder("MBR {pi=");
        sb.append(this.mPendingIntent);
        sb.append(", componentName=");
        sb.append(this.mComponentName);
        sb.append(", type=");
        sb.append(this.mComponentType);
        sb.append(", pkg=");
        return AudioOffloadInfo$$ExternalSyntheticOutline0.m(sb, this.mPackageName, "}");
    }
}
