package com.android.systemui.searcle.omni;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import java.util.function.Consumer;

/* compiled from: qb/89523975 427a50d40ec74a85ca352b86f77450b1c52ece5389e11158752b0d641a3a5098 */
public final class SimpleBroadcastReceiver extends BroadcastReceiver {
    public final Consumer mIntentConsumer;

    public SimpleBroadcastReceiver(Consumer<Intent> consumer) {
        this.mIntentConsumer = consumer;
    }

    @Override // android.content.BroadcastReceiver
    public final void onReceive(Context context, Intent intent) {
        this.mIntentConsumer.accept(intent);
    }
}
