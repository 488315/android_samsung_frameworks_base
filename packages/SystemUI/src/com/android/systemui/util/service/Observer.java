package com.android.systemui.util.service;

/* loaded from: classes3.dex */
public interface Observer {

    public interface Callback {
        void onSourceChanged();
    }

    void addCallback(Callback callback);

    void removeCallback(Callback callback);
}
