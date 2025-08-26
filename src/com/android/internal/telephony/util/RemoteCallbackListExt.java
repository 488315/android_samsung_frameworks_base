package com.android.internal.telephony.util;

import android.os.IInterface;
import android.os.RemoteCallbackList;
import java.util.function.Consumer;

/* loaded from: classes4.dex */
public class RemoteCallbackListExt<E extends IInterface> extends RemoteCallbackList<E> {
    public void broadcastAction(Consumer<E> consumer) {
        int iBeginBroadcast = beginBroadcast();
        for (int i = 0; i < iBeginBroadcast; i++) {
            try {
                consumer.accept(getBroadcastItem(i));
            } finally {
                finishBroadcast();
            }
        }
    }
}
