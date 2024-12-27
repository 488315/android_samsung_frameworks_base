package com.samsung.aasaservice;

import android.content.Context;

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
