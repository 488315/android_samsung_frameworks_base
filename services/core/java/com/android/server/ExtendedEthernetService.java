package com.android.server;

import android.content.Context;
import android.util.Log;

/* loaded from: classes.dex */
public class ExtendedEthernetService extends SystemService {
    public final ExtendedEthernetServiceImpl mImpl;

    public ExtendedEthernetService(Context context) {
        super(context);
        this.mImpl = new ExtendedEthernetServiceImpl(getContext());
    }

    @Override // com.android.server.SystemService
    public void onStart() {
        Log.i("ExtendedEthernetService", "Registering extendedethernetservice");
        publishBinderService("extendedethernetservice", this.mImpl);
    }

    @Override // com.android.server.SystemService
    public void onBootPhase(int i) {
        if (i == 550) {
            this.mImpl.handleSystemReady();
        }
    }
}
