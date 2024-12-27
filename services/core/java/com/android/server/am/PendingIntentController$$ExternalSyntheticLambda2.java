package com.android.server.am;

import android.os.Bundle;
import android.os.RemoteCallbackList;
import android.os.RemoteException;
import java.util.function.BiConsumer;

/* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
/* loaded from: classes.dex */
public final /* synthetic */ class PendingIntentController$$ExternalSyntheticLambda2 implements BiConsumer {
    @Override // java.util.function.BiConsumer
    public final void accept(Object obj, Object obj2) {
        RemoteCallbackList remoteCallbackList = (RemoteCallbackList) obj2;
        ((PendingIntentController) obj).getClass();
        int beginBroadcast = remoteCallbackList.beginBroadcast();
        for (int i = 0; i < beginBroadcast; i++) {
            try {
                remoteCallbackList.getBroadcastItem(i).send(0, (Bundle) null);
            } catch (RemoteException unused) {
            }
        }
        remoteCallbackList.finishBroadcast();
        remoteCallbackList.kill();
    }
}
