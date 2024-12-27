package com.android.server.ibs;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.hardware.display.DisplayManager;
import android.os.Message;

public final class IntelligentBatterySaverGather {
    public final AnonymousClass1 mDisplayListener =
            new DisplayManager
                    .DisplayListener() { // from class:
                                         // com.android.server.ibs.IntelligentBatterySaverGather.1
                @Override // android.hardware.display.DisplayManager.DisplayListener
                public final void onDisplayAdded(int i) {}

                @Override // android.hardware.display.DisplayManager.DisplayListener
                public final void onDisplayChanged(int i) {
                    if (i == 0) {
                        synchronized (IntelligentBatterySaverGather.this) {
                            IntelligentBatterySaverFastDrainPolicy
                                    intelligentBatterySaverFastDrainPolicy =
                                            IntelligentBatterySaverGather.this
                                                    .mIBSService
                                                    .mIBSFastDrainPolicy;
                            boolean z =
                                    intelligentBatterySaverFastDrainPolicy
                                                    .mDisplayManager
                                                    .getDisplay(0)
                                                    .getState()
                                            == 2;
                            if (!z && intelligentBatterySaverFastDrainPolicy.mScreenOn) {
                                intelligentBatterySaverFastDrainPolicy.mScreenOn = false;
                                intelligentBatterySaverFastDrainPolicy.reportSetState(4);
                            } else if (z) {
                                intelligentBatterySaverFastDrainPolicy.mScreenOn = true;
                                intelligentBatterySaverFastDrainPolicy.reportClearState(4);
                            }
                        }
                    }
                }

                @Override // android.hardware.display.DisplayManager.DisplayListener
                public final void onDisplayRemoved(int i) {}
            };
    public IntentFilter mFilter;
    public final IntelligentBatterySaverService mIBSService;
    public IntelligentBatterySaverGatherReceiver mReceiver;

    public final class IntelligentBatterySaverGatherReceiver extends BroadcastReceiver {
        public IntelligentBatterySaverGatherReceiver() {}

        @Override // android.content.BroadcastReceiver
        public final void onReceive(Context context, Intent intent) {
            IntelligentBatterySaverFastDrainPolicy.IntelligentBatterySaverFastDrainHandler
                    intelligentBatterySaverFastDrainHandler;
            if ("android.intent.action.BATTERY_CHANGED".equals(intent.getAction())) {
                synchronized (IntelligentBatterySaverGather.this) {
                    try {
                        int intExtra = intent.getIntExtra("plugged", 0);
                        IntelligentBatterySaverFastDrainPolicy
                                intelligentBatterySaverFastDrainPolicy =
                                        IntelligentBatterySaverGather.this
                                                .mIBSService
                                                .mIBSFastDrainPolicy;
                        boolean z = intExtra != 0;
                        if (!z && intelligentBatterySaverFastDrainPolicy.mCharging) {
                            intelligentBatterySaverFastDrainPolicy.mCharging = false;
                            intelligentBatterySaverFastDrainPolicy.reportSetState(8);
                        } else if (z) {
                            intelligentBatterySaverFastDrainPolicy.mCharging = true;
                            intelligentBatterySaverFastDrainPolicy.reportClearState(8);
                        } else {
                            intelligentBatterySaverFastDrainPolicy.getClass();
                        }
                        int intExtra2 = intent.getIntExtra("level", -1);
                        int intExtra3 = intent.getIntExtra("scale", 0);
                        if (intExtra2 != -1
                                && intExtra3 != 0
                                && (intelligentBatterySaverFastDrainHandler =
                                                IntelligentBatterySaverGather.this
                                                        .mIBSService
                                                        .mIBSFastDrainPolicy
                                                        .mHandler)
                                        != null) {
                            intelligentBatterySaverFastDrainHandler.sendMessage(
                                    Message.obtain(
                                            intelligentBatterySaverFastDrainHandler,
                                            6,
                                            intExtra2,
                                            intExtra3));
                        }
                    } finally {
                    }
                }
            }
        }
    }

    public IntelligentBatterySaverGather(
            Context context, IntelligentBatterySaverService intelligentBatterySaverService) {
        this.mIBSService = intelligentBatterySaverService;
    }
}
