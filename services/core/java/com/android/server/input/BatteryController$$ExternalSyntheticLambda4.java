package com.android.server.input;

/* compiled from: R8$$SyntheticClass */
/* loaded from: classes2.dex */
public final /* synthetic */ class BatteryController$$ExternalSyntheticLambda4 implements Runnable {
    public final /* synthetic */ BatteryController f$0;

    public /* synthetic */ BatteryController$$ExternalSyntheticLambda4(BatteryController batteryController) {
        this.f$0 = batteryController;
    }

    @Override // java.lang.Runnable
    public final void run() {
        this.f$0.handlePollEvent();
    }
}
