package com.android.systemui.broadcast;

import android.content.BroadcastReceiver;
import kotlin.jvm.functions.Function2;
import kotlin.jvm.internal.FunctionReferenceImpl;

/* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
/* loaded from: classes.dex */
public /* synthetic */ class UserBroadcastDispatcher$createActionReceiver$3 extends FunctionReferenceImpl implements Function2 {
    public UserBroadcastDispatcher$createActionReceiver$3(Object obj) {
        super(2, obj, PendingRemovalStore.class, "isPendingRemoval", "isPendingRemoval(Landroid/content/BroadcastReceiver;I)Z", 0);
    }

    @Override // kotlin.jvm.functions.Function2
    public final Object invoke(Object obj, Object obj2) {
        boolean z;
        BroadcastReceiver broadcastReceiver = (BroadcastReceiver) obj;
        int intValue = ((Number) obj2).intValue();
        PendingRemovalStore pendingRemovalStore = (PendingRemovalStore) this.receiver;
        synchronized (pendingRemovalStore.pendingRemoval) {
            if (!pendingRemovalStore.pendingRemoval.contains(intValue, broadcastReceiver)) {
                z = pendingRemovalStore.pendingRemoval.contains(-1, broadcastReceiver);
            }
        }
        return Boolean.valueOf(z);
    }
}
