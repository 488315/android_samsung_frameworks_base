package com.android.server.am;

import android.content.IntentFilter;
import android.util.proto.ProtoOutputStream;

import dalvik.annotation.optimization.NeverCompile;

/* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
public final class BroadcastFilter extends IntentFilter {
    public final boolean exported;
    public final String featureId;
    public final boolean instantApp;
    public final int owningUid;
    public final int owningUserId;
    public final String packageName;
    public final String receiverId;
    public final ReceiverList receiverList;
    public final String requiredPermission;
    public final boolean visibleToInstantApp;

    public BroadcastFilter(
            IntentFilter intentFilter,
            ReceiverList receiverList,
            String str,
            String str2,
            String str3,
            String str4,
            int i,
            int i2,
            boolean z,
            boolean z2,
            boolean z3) {
        super(intentFilter);
        this.receiverList = receiverList;
        this.packageName = str;
        this.featureId = str2;
        this.receiverId = str3;
        this.requiredPermission = str4;
        this.owningUid = i;
        this.owningUserId = i2;
        this.instantApp = z;
        this.visibleToInstantApp = z2;
        this.exported = z3;
    }

    @NeverCompile
    public final void dumpDebug(ProtoOutputStream protoOutputStream, long j) {
        long start = protoOutputStream.start(j);
        super.dumpDebug(protoOutputStream, 1146756268033L);
        String str = this.requiredPermission;
        if (str != null) {
            protoOutputStream.write(1138166333442L, str);
        }
        protoOutputStream.write(1138166333443L, Integer.toHexString(System.identityHashCode(this)));
        protoOutputStream.write(1120986464260L, this.owningUserId);
        protoOutputStream.end(start);
    }

    public final String toString() {
        return "BroadcastFilter{"
                + Integer.toHexString(System.identityHashCode(this))
                + ' '
                + this.owningUid
                + "/u"
                + this.owningUserId
                + ' '
                + this.receiverList
                + '}';
    }
}
