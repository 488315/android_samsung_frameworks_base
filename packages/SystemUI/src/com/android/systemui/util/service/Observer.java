package com.android.systemui.util.service;

public interface Observer {

    public interface Callback {
        void onSourceChanged();
    }

    void addCallback(Callback callback);

    void removeCallback(Callback callback);
}
