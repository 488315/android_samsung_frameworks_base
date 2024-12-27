package com.android.systemui.broadcast;

import android.content.BroadcastReceiver;
import kotlin.jvm.functions.Function2;
import kotlin.jvm.internal.FunctionReferenceImpl;

/* compiled from: qb/89523975 427a50d40ec74a85ca352b86f77450b1c52ece5389e11158752b0d641a3a5098 */
/* loaded from: classes.dex */
final /* synthetic */ class UserBroadcastDispatcher$createActionReceiver$3 extends FunctionReferenceImpl implements Function2 {
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
