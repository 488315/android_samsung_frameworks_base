package com.samsung.android.ims.options;

import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.os.RemoteException;
import android.util.Log;
import android.util.Pair;

import com.samsung.android.ims.util.SemImsUri;

import java.util.List;

public class SemCapabilityListener {
    private static final boolean DBG = false;
    private static final String LOG_TAG = "SemCapabilityListener";
    private final int EVT_OWN_CAP_CHANGED = 1;
    private final int EVT_MULTIPLE_CAP_CHANGED = 2;
    private final int EVT_CAP_CHANGED = 3;
    private final int EVT_CAP_PUBLISHED = 4;
    private Handler mHandler =
            new Handler(
                    Looper
                            .getMainLooper()) { // from class:
                                                // com.samsung.android.ims.options.SemCapabilityListener.1
                @Override // android.os.Handler
                public void handleMessage(Message msg) {
                    switch (msg.what) {
                        case 1:
                            Log.d(
                                    SemCapabilityListener.LOG_TAG,
                                    "onOwnCapabilitiesChanged: listener = "
                                            + SemCapabilityListener.this);
                            SemCapabilityListener.this.onOwnCapabilitiesChanged();
                            break;
                        case 3:
                            Pair<SemImsUri, SemCapabilities> p = (Pair) msg.obj;
                            SemCapabilityListener.this.onCapabilitiesChanged(p.first, p.second);
                            break;
                        case 4:
                            SemCapabilityListener.this.onCapabilityAndAvailabilityPublished(
                                    msg.arg1);
                            break;
                    }
                }
            };
    SemCapabilityServiceEventListenerDelegate callback =
            new SemCapabilityServiceEventListenerDelegate();

    public void onOwnCapabilitiesChanged() {}

    public void onCapabilitiesChanged(SemImsUri uri, SemCapabilities cap) {}

    public void onCapabilityAndAvailabilityPublished(int errorCode) {}

    protected String getToken() {
        return this.callback.mToken;
    }

    protected void setToken(String token) {
        this.callback.mToken = token;
    }

    class SemCapabilityServiceEventListenerDelegate extends SemCapabilityServiceEventListener.Stub {
        String mToken;

        private SemCapabilityServiceEventListenerDelegate() {}

        @Override // com.samsung.android.ims.options.SemCapabilityServiceEventListener
        public void onOwnCapabilitiesChanged() throws RemoteException {
            Message.obtain(SemCapabilityListener.this.mHandler, 1, null).sendToTarget();
        }

        @Override // com.samsung.android.ims.options.SemCapabilityServiceEventListener
        public void onMultipleCapabilitiesChanged(
                List<SemImsUri> uris, List<SemCapabilities> capaList) throws RemoteException {
            Message.obtain(SemCapabilityListener.this.mHandler, 2, new Pair(uris, capaList))
                    .sendToTarget();
        }

        @Override // com.samsung.android.ims.options.SemCapabilityServiceEventListener
        public void onCapabilitiesChanged(SemImsUri uri, SemCapabilities capa)
                throws RemoteException {
            Message.obtain(SemCapabilityListener.this.mHandler, 3, new Pair(uri, capa))
                    .sendToTarget();
        }

        @Override // com.samsung.android.ims.options.SemCapabilityServiceEventListener
        public void onCapabilityAndAvailabilityPublished(int errorCode) throws RemoteException {
            Message.obtain(SemCapabilityListener.this.mHandler, 4, errorCode, -1).sendToTarget();
        }
    }
}
