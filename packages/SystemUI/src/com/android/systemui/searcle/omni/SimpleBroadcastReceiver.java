package com.android.systemui.searcle.omni;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import java.util.function.Consumer;

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
