package com.android.systemui.searcle.omni;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import java.util.function.Consumer;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes2.dex */
public class SimpleBroadcastReceiver extends BroadcastReceiver {
    public final Consumer mIntentConsumer;

    public SimpleBroadcastReceiver(Consumer<Intent> consumer) {
        this.mIntentConsumer = consumer;
    }

    @Override // android.content.BroadcastReceiver
    public final void onReceive(Context context, Intent intent) {
        this.mIntentConsumer.accept(intent);
    }
}
