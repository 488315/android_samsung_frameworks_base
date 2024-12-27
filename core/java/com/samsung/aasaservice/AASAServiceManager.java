package com.samsung.aasaservice;

import android.content.Context;

/* loaded from: classes5.dex */
public interface AASAServiceManager {

    public interface Callback {
        void onReady();
    }

    void deinitialize();

    void initialize(Callback callback);

    void notifyPolicyUpdateCompletion();

    static AASAServiceManager create(Context context) {
        return new AASAServiceManagerImpl(context);
    }
}
