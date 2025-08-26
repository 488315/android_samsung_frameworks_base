package com.android.systemui.broadcast;

import android.content.BroadcastReceiver;
import kotlin.jvm.functions.Function2;
import kotlin.jvm.internal.FunctionReferenceImpl;

/* loaded from: classes.dex */
final /* synthetic */ class UserBroadcastDispatcher$createActionReceiver$3 extends FunctionReferenceImpl implements Function2 {
    public UserBroadcastDispatcher$createActionReceiver$3(Object obj) {
        super(2, obj, PendingRemovalStore.class, "isPendingRemoval", "isPendingRemoval(Landroid/content/BroadcastReceiver;I)Z", 0);
    }

    /* JADX WARN: Removed duplicated region for block: B:12:0x0025  */
    @Override // kotlin.jvm.functions.Function2
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    public final Object invoke(Object obj, Object obj2) {
        boolean z;
        BroadcastReceiver broadcastReceiver = (BroadcastReceiver) obj;
        int iIntValue = ((Number) obj2).intValue();
        PendingRemovalStore pendingRemovalStore = (PendingRemovalStore) this.receiver;
        synchronized (pendingRemovalStore.pendingRemoval) {
            if (!pendingRemovalStore.pendingRemoval.contains(iIntValue, broadcastReceiver)) {
                z = pendingRemovalStore.pendingRemoval.contains(-1, broadcastReceiver);
            }
        }
        return Boolean.valueOf(z);
    }
}
