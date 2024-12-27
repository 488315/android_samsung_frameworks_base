package com.android.server.soundtrigger_middleware;

public interface ICaptureStateNotifier {

    public interface Listener {}

    void unregisterListener(Listener listener);
}
