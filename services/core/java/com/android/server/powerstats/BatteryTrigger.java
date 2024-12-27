package com.android.server.powerstats;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Message;

/* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
public final class BatteryTrigger extends PowerStatsLogTrigger {
    public int mBatteryLevel;
    public final AnonymousClass1 mBatteryLevelReceiver;

    public BatteryTrigger(Context context, PowerStatsLogger powerStatsLogger) {
        super(context, powerStatsLogger);
        this.mBatteryLevel = 0;
        Intent registerReceiver =
                context.registerReceiver(
                        new BroadcastReceiver() { // from class:
                                                  // com.android.server.powerstats.BatteryTrigger.1
                            @Override // android.content.BroadcastReceiver
                            public final void onReceive(Context context2, Intent intent) {
                                String action = intent.getAction();
                                action.getClass();
                                if (action.equals("android.intent.action.BATTERY_CHANGED")) {
                                    int intExtra = intent.getIntExtra("level", 0);
                                    BatteryTrigger batteryTrigger = BatteryTrigger.this;
                                    if (intExtra < batteryTrigger.mBatteryLevel) {
                                        Message.obtain(batteryTrigger.mPowerStatsLogger, 0)
                                                .sendToTarget();
                                    }
                                    BatteryTrigger.this.mBatteryLevel = intExtra;
                                }
                            }
                        },
                        new IntentFilter("android.intent.action.BATTERY_CHANGED"));
        if (registerReceiver != null) {
            this.mBatteryLevel = registerReceiver.getIntExtra("level", 0);
        }
    }
}
