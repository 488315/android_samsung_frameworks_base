package com.android.server.soundtrigger_middleware;

/* loaded from: classes3.dex */
public interface ICaptureStateNotifier {

    public interface Listener {
        void onCaptureStateChange(boolean z);
    }

    boolean registerListener(Listener listener);

    void unregisterListener(Listener listener);
}
