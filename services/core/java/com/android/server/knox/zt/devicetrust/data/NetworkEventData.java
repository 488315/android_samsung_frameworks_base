package com.android.server.knox.zt.devicetrust.data;

import android.hardware.soundtrigger.V2_3.OptionalModelParameterRange$$ExternalSyntheticOutline0;
import android.os.Bundle;

import com.android.server.UserspaceRebootLogger$$ExternalSyntheticOutline0;
import com.android.server.accessibility.AbstractAccessibilityServiceConnection$$ExternalSyntheticOutline0;

import java.util.Locale;

/* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
/* loaded from: classes.dex */
public final class NetworkEventData {
    public final String destIp;
    public final int destPort;
    public final int eventType;
    public final int family;
    public final int ifIndex;
    public String interfaceName;
    public String packageName;
    public final int protocol;
    public final String srcIp;
    public final int srcPort;
    public long timestamp;
    public final int uid;

    public NetworkEventData(
            int i,
            long j,
            int i2,
            int i3,
            int i4,
            int i5,
            String str,
            int i6,
            String str2,
            int i7) {
        this.eventType = i;
        this.timestamp = j;
        this.uid = i2;
        this.ifIndex = i3;
        this.family = i4;
        this.protocol = i5;
        this.srcIp = str;
        this.srcPort = i6;
        this.destIp = str2;
        this.destPort = i7;
    }

    public final void adjustToActualTimeInMillis(long j) {
        this.timestamp = (j + this.timestamp) / 1000000;
    }

    public final Bundle getBundle() {
        Bundle bundle = new Bundle();
        bundle.putInt("eventType", this.eventType);
        bundle.putLong("timestamp", this.timestamp);
        bundle.putInt("uid", this.uid);
        bundle.putInt("ifIndex", this.ifIndex);
        bundle.putInt("family", this.family);
        bundle.putInt("protocol", this.protocol);
        bundle.putInt("sourcePort", this.srcPort);
        bundle.putInt("destPort", this.destPort);
        bundle.putString("sourceAddr", this.srcIp);
        bundle.putString("remoteAddr", this.destIp);
        bundle.putString("packageName", this.packageName);
        bundle.putString("interfaceName", this.interfaceName);
        return bundle;
    }

    public final void setInterfaceName(String str) {
        this.interfaceName = str;
    }

    public final void setPackageName(String str) {
        this.packageName = str;
    }

    public final String toString() {
        Locale locale = Locale.US;
        int i = this.eventType;
        long j = this.timestamp;
        int i2 = this.uid;
        int i3 = this.ifIndex;
        int i4 = this.family;
        int i5 = this.protocol;
        String str = this.srcIp;
        int i6 = this.srcPort;
        String str2 = this.destIp;
        int i7 = this.destPort;
        String str3 = this.packageName;
        String str4 = this.interfaceName;
        StringBuilder m =
                UserspaceRebootLogger$$ExternalSyntheticOutline0.m(
                        i, "EventType : ", j, " | Time : ");
        AbstractAccessibilityServiceConnection$$ExternalSyntheticOutline0.m(
                i2, i3, " | UID : ", " |Interface Idx : ", m);
        AbstractAccessibilityServiceConnection$$ExternalSyntheticOutline0.m(
                i4, i5, " | Family : ", " | Protocol : ", m);
        m.append(" |Source IP : ");
        m.append(str);
        m.append(" | Source port : ");
        m.append(i6);
        m.append(" |Dest IP : ");
        m.append(str2);
        m.append(" | Dest port : ");
        m.append(i7);
        return OptionalModelParameterRange$$ExternalSyntheticOutline0.m(
                m, " | Package Name: ", str3, " | Interface: ", str4);
    }
}
