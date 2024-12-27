package com.android.server.am;

import android.content.Intent;
import android.os.IBinder;
import android.util.ArrayMap;
import android.util.ArraySet;

import com.android.server.BootReceiver$$ExternalSyntheticOutline0;

public final class IntentBindRecord {
    public final ArrayMap apps = new ArrayMap();
    public IBinder binder;
    public boolean doRebind;
    public boolean hasBound;
    public final Intent.FilterComparison intent;
    public boolean received;
    public boolean requested;
    public final ServiceRecord service;
    public String stringName;

    public IntentBindRecord(ServiceRecord serviceRecord, Intent.FilterComparison filterComparison) {
        this.service = serviceRecord;
        this.intent = filterComparison;
    }

    public final long collectFlags() {
        long j = 0;
        for (int size = this.apps.size() - 1; size >= 0; size--) {
            ArraySet arraySet = ((AppBindRecord) this.apps.valueAt(size)).connections;
            for (int size2 = arraySet.size() - 1; size2 >= 0; size2--) {
                j |= ((ConnectionRecord) arraySet.valueAt(size2)).flags;
            }
        }
        return j;
    }

    public final String toString() {
        String str = this.stringName;
        if (str != null) {
            return str;
        }
        StringBuilder m = BootReceiver$$ExternalSyntheticOutline0.m(128, "IntentBindRecord{");
        m.append(Integer.toHexString(System.identityHashCode(this)));
        m.append(' ');
        if ((collectFlags() & 1) != 0) {
            m.append("CR ");
        }
        m.append(this.service.shortInstanceName);
        m.append(':');
        Intent.FilterComparison filterComparison = this.intent;
        if (filterComparison != null) {
            filterComparison.getIntent().toShortString(m, false, false, false, false);
        }
        m.append('}');
        String sb = m.toString();
        this.stringName = sb;
        return sb;
    }
}
