package com.samsung.android.ims.options;

import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.os.RemoteException;
import android.util.Log;
import android.util.Pair;
import com.samsung.android.ims.options.SemCapabilityServiceEventListener;
import com.samsung.android.ims.util.SemImsUri;
import java.util.List;

/* loaded from: classes6.dex */
public class SemCapabilityListener {
    private static final boolean DBG = false;
    private static final String LOG_TAG = "SemCapabilityListener";
    private final int EVT_OWN_CAP_CHANGED = 1;
    private final int EVT_MULTIPLE_CAP_CHANGED = 2;
    private final int EVT_CAP_CHANGED = 3;
    private final int EVT_CAP_PUBLISHED = 4;
    private Handler mHandler = new Handler(Looper.getMainLooper()) { // from class: com.samsung.android.ims.options.SemCapabilityListener.1
        /* JADX WARN: Multi-variable type inference failed */
        @Override // android.os.Handler
        public void handleMessage(Message message) {
            int i = message.what;
            if (i == 1) {
                Log.d(SemCapabilityListener.LOG_TAG, "onOwnCapabilitiesChanged: listener = " + SemCapabilityListener.this);
                SemCapabilityListener.this.onOwnCapabilitiesChanged();
                return;
            }
            if (i == 3) {
                Pair pair = (Pair) message.obj;
                SemCapabilityListener.this.onCapabilitiesChanged((SemImsUri) pair.first, (SemCapabilities) pair.second);
            } else {
                if (i != 4) {
                    return;
                }
                SemCapabilityListener.this.onCapabilityAndAvailabilityPublished(message.arg1);
            }
        }
    };
    SemCapabilityServiceEventListenerDelegate callback = new SemCapabilityServiceEventListenerDelegate();

    public void onCapabilitiesChanged(SemImsUri semImsUri, SemCapabilities semCapabilities) {
    }

    public void onCapabilityAndAvailabilityPublished(int i) {
    }

    public void onOwnCapabilitiesChanged() {
    }

    protected String getToken() {
        return this.callback.mToken;
    }

    protected void setToken(String str) {
        this.callback.mToken = str;
    }

    /* JADX INFO: Access modifiers changed from: private */
    class SemCapabilityServiceEventListenerDelegate extends SemCapabilityServiceEventListener.Stub {
        String mToken;

        private SemCapabilityServiceEventListenerDelegate() {
        }

        @Override // com.samsung.android.ims.options.SemCapabilityServiceEventListener
        public void onOwnCapabilitiesChanged() throws RemoteException {
            Message.obtain(SemCapabilityListener.this.mHandler, 1, null).sendToTarget();
        }

        @Override // com.samsung.android.ims.options.SemCapabilityServiceEventListener
        public void onMultipleCapabilitiesChanged(List<SemImsUri> list, List<SemCapabilities> list2) throws RemoteException {
            Message.obtain(SemCapabilityListener.this.mHandler, 2, new Pair(list, list2)).sendToTarget();
        }

        @Override // com.samsung.android.ims.options.SemCapabilityServiceEventListener
        public void onCapabilitiesChanged(SemImsUri semImsUri, SemCapabilities semCapabilities) throws RemoteException {
            Message.obtain(SemCapabilityListener.this.mHandler, 3, new Pair(semImsUri, semCapabilities)).sendToTarget();
        }

        @Override // com.samsung.android.ims.options.SemCapabilityServiceEventListener
        public void onCapabilityAndAvailabilityPublished(int i) throws RemoteException {
            Message.obtain(SemCapabilityListener.this.mHandler, 4, i, -1).sendToTarget();
        }
    }
}
