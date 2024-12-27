package com.android.server.am;

import android.hardware.audio.common.V2_0.AudioOffloadInfo$$ExternalSyntheticOutline0;
import android.util.ArraySet;

/* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
/* loaded from: classes.dex */
public final class AppBindRecord {
    public final ProcessRecord attributedClient;
    public final ProcessRecord client;
    public final ArraySet connections = new ArraySet();
    public final IntentBindRecord intent;
    public final ServiceRecord service;

    public AppBindRecord(
            ServiceRecord serviceRecord,
            IntentBindRecord intentBindRecord,
            ProcessRecord processRecord,
            ProcessRecord processRecord2) {
        this.service = serviceRecord;
        this.intent = intentBindRecord;
        this.client = processRecord;
        this.attributedClient = processRecord2;
    }

    public final String toString() {
        StringBuilder sb = new StringBuilder("AppBindRecord{");
        sb.append(Integer.toHexString(System.identityHashCode(this)));
        sb.append(" ");
        sb.append(this.service.shortInstanceName);
        sb.append(":");
        return AudioOffloadInfo$$ExternalSyntheticOutline0.m(sb, this.client.processName, "}");
    }
}
