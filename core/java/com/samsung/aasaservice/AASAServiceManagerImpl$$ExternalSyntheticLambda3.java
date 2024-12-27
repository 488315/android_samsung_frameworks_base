package com.samsung.aasaservice;

/* compiled from: D8$$SyntheticClass */
public final /* synthetic */ class AASAServiceManagerImpl$$ExternalSyntheticLambda3
        implements Runnable {
    public final /* synthetic */ AASAServiceManagerImpl f$0;

    public /* synthetic */ AASAServiceManagerImpl$$ExternalSyntheticLambda3(
            AASAServiceManagerImpl aASAServiceManagerImpl) {
        this.f$0 = aASAServiceManagerImpl;
    }

    @Override // java.lang.Runnable
    public final void run() {
        this.f$0.notifyReady();
    }
}
