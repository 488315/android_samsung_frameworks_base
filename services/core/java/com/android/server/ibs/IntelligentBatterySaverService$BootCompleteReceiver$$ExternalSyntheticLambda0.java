package com.android.server.ibs;

import java.util.function.Consumer;

public final /* synthetic */
class IntelligentBatterySaverService$BootCompleteReceiver$$ExternalSyntheticLambda0
        implements Consumer {
    @Override // java.util.function.Consumer
    public final void accept(Object obj) {
        IntelligentBatterySaverFastDrainPolicy.IntelligentBatterySaverFastDrainHandler
                intelligentBatterySaverFastDrainHandler =
                        ((IntelligentBatterySaverFastDrainPolicy) obj).mHandler;
        if (intelligentBatterySaverFastDrainHandler != null) {
            intelligentBatterySaverFastDrainHandler.sendMessage(
                    intelligentBatterySaverFastDrainHandler.obtainMessage(10));
        }
    }
}
