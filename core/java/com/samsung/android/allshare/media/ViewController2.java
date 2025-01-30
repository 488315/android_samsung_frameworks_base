package com.samsung.android.allshare.media;

import com.samsung.android.allshare.ERROR;

/* loaded from: classes5.dex */
public abstract class ViewController2 {

    public interface IViewController2EventListener {
        void onDisconnected(ViewController2 viewController2, ERROR error);
    }

    public interface IViewController2ResponseListener {
        void onConnectResponseReceived(ViewController2 viewController2, ERROR error);

        void onDisconnectResponseReceived(ViewController2 viewController2, ERROR error);
    }

    public abstract void connect();

    public abstract void disconnect();

    public abstract boolean isConnected();

    public abstract void setEventListener(IViewController2EventListener iViewController2EventListener);

    public abstract void setResponseListener(IViewController2ResponseListener iViewController2ResponseListener);

    public abstract void setViewAngle(int i);

    public abstract void zoom(int i, int i2, float f);

    protected ViewController2() {
    }
}
