package com.android.server.biometrics;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.util.Slog;
import com.android.server.biometrics.sensors.BiometricNotificationImpl;
import java.util.function.Consumer;

/* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
/* loaded from: classes.dex */
public final class AuthenticationStatsBroadcastReceiver extends BroadcastReceiver {
    public final Consumer mCollectorConsumer;
    public final int mModality;

    public AuthenticationStatsBroadcastReceiver(Context context, int i, Consumer consumer) {
        IntentFilter intentFilter = new IntentFilter();
        intentFilter.addAction("android.intent.action.USER_UNLOCKED");
        context.registerReceiver(this, intentFilter);
        this.mCollectorConsumer = consumer;
        this.mModality = i;
    }

    @Override // android.content.BroadcastReceiver
    public final void onReceive(Context context, Intent intent) {
        if (intent.getIntExtra("android.intent.extra.user_handle", -10000) == -10000 || !"android.intent.action.USER_UNLOCKED".equals(intent.getAction())) {
            return;
        }
        Slog.d("AuthenticationStatsBroadcastReceiver", "Received: " + intent.getAction());
        this.mCollectorConsumer.accept(new AuthenticationStatsCollector(context, this.mModality, new BiometricNotificationImpl()));
        context.unregisterReceiver(this);
    }
}
