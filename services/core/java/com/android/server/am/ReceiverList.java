package com.android.server.am;

import android.content.IIntentReceiver;
import android.os.Binder;
import android.os.IBinder;

import com.android.server.BootReceiver$$ExternalSyntheticOutline0;

import java.util.ArrayList;

/* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
/* loaded from: classes.dex */
final class ReceiverList extends ArrayList implements IBinder.DeathRecipient {
    public final ProcessRecord app;
    BroadcastRecord curBroadcast = null;
    boolean linkedToDeath = false;
    final ActivityManagerService owner;
    public final int pid;
    public final IIntentReceiver receiver;
    String stringName;
    public final int uid;
    public final int userId;

    public ReceiverList(
            ActivityManagerService activityManagerService,
            ProcessRecord processRecord,
            int i,
            int i2,
            int i3,
            IIntentReceiver iIntentReceiver) {
        this.owner = activityManagerService;
        this.receiver = iIntentReceiver;
        this.app = processRecord;
        this.pid = i;
        this.uid = i2;
        this.userId = i3;
    }

    @Override // android.os.IBinder.DeathRecipient
    public final void binderDied() {
        this.linkedToDeath = false;
        this.owner.unregisterReceiver(this.receiver);
    }

    @Override // java.util.ArrayList, java.util.AbstractList, java.util.Collection, java.util.List
    public final boolean equals(Object obj) {
        return this == obj;
    }

    @Override // java.util.ArrayList, java.util.AbstractList, java.util.Collection, java.util.List
    public final int hashCode() {
        return System.identityHashCode(this);
    }

    @Override // java.util.AbstractCollection
    public final String toString() {
        String str = this.stringName;
        if (str != null) {
            return str;
        }
        StringBuilder m = BootReceiver$$ExternalSyntheticOutline0.m(128, "ReceiverList{");
        m.append(Integer.toHexString(System.identityHashCode(this)));
        m.append(' ');
        m.append(this.pid);
        m.append(' ');
        ProcessRecord processRecord = this.app;
        m.append(processRecord != null ? processRecord.processName : "(unknown name)");
        m.append('/');
        m.append(this.uid);
        m.append("/u");
        m.append(this.userId);
        m.append(this.receiver.asBinder() instanceof Binder ? " local:" : " remote:");
        if (this.receiver.asBinder() instanceof Binder) {
            m.append(this.receiver);
            m.append(",");
        }
        m.append(Integer.toHexString(System.identityHashCode(this.receiver.asBinder())));
        m.append('}');
        String sb = m.toString();
        this.stringName = sb;
        return sb;
    }
}
