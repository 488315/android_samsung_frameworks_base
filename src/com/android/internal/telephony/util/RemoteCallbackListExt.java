package com.android.internal.telephony.util;

import android.os.IInterface;
import android.os.RemoteCallbackList;
import java.util.function.Consumer;

/* loaded from: classes4.dex */
public class RemoteCallbackListExt<E extends IInterface> extends RemoteCallbackList<E> {
    public void broadcastAction(Consumer<E> consumer) {
        int beginBroadcast = beginBroadcast();
        for (int i = 0; i < beginBroadcast; i++) {
            try {
                consumer.accept(getBroadcastItem(i));
            } finally {
                finishBroadcast();
            }
        }
    }
}
