package com.android.server.ibs;

import com.android.server.ibs.IntelligentBatterySaverFastDrainPolicy;
import java.util.function.Consumer;

/* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
/* loaded from: classes.dex */
public final /* synthetic */ class IntelligentBatterySaverService$BootCompleteReceiver$$ExternalSyntheticLambda0 implements Consumer {
    @Override // java.util.function.Consumer
    public final void accept(Object obj) {
        IntelligentBatterySaverFastDrainPolicy.IntelligentBatterySaverFastDrainHandler intelligentBatterySaverFastDrainHandler = ((IntelligentBatterySaverFastDrainPolicy) obj).mHandler;
        if (intelligentBatterySaverFastDrainHandler != null) {
            intelligentBatterySaverFastDrainHandler.sendMessage(intelligentBatterySaverFastDrainHandler.obtainMessage(10));
        }
    }
}
