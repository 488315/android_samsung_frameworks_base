package com.android.systemui.statusbar.connectivity;

import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import com.android.keyguard.KeyguardKnoxGuardViewController$$ExternalSyntheticOutline0;
import com.android.systemui.statusbar.connectivity.NetworkController;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

/* compiled from: qb/89523975 427a50d40ec74a85ca352b86f77450b1c52ece5389e11158752b0d641a3a5098 */
public final class CallbackHandler extends Handler implements NetworkController.EmergencyListener, SignalCallback {
    public static final SimpleDateFormat SSDF = new SimpleDateFormat("MM-dd HH:mm:ss.SSS");
    public final ArrayList mEmergencyListeners;
    public final String[] mHistory;
    public int mHistoryIndex;
    public String mLastCallback;
    public final ArrayList mSignalCallbacks;

    public CallbackHandler(Looper looper) {
        super(looper);
        this.mEmergencyListeners = new ArrayList();
        this.mSignalCallbacks = new ArrayList();
        this.mHistory = new String[64];
    }

    @Override // android.os.Handler
    public final void handleMessage(Message message) {
        switch (message.what) {
            case 0:
                Iterator it = this.mEmergencyListeners.iterator();
                while (it.hasNext()) {
                    ((CallbackHandler) ((NetworkController.EmergencyListener) it.next())).obtainMessage(0, message.arg1 != 0 ? 1 : 0, 0).sendToTarget();
                }
                break;
            case 1:
                Iterator it2 = this.mSignalCallbacks.iterator();
                while (it2.hasNext()) {
                    ((SignalCallback) it2.next()).setSubs((List) message.obj);
                }
                break;
            case 2:
                Iterator it3 = this.mSignalCallbacks.iterator();
                while (it3.hasNext()) {
                    ((SignalCallback) it3.next()).setNoSims(message.arg1 != 0, message.arg2 != 0);
                }
                break;
            case 3:
                Iterator it4 = this.mSignalCallbacks.iterator();
                while (it4.hasNext()) {
                    ((SignalCallback) it4.next()).setEthernetIndicators((IconState) message.obj);
                }
                break;
            case 4:
                Iterator it5 = this.mSignalCallbacks.iterator();
                while (it5.hasNext()) {
                    ((SignalCallback) it5.next()).setIsAirplaneMode((IconState) message.obj);
                }
                break;
            case 5:
                Iterator it6 = this.mSignalCallbacks.iterator();
                while (it6.hasNext()) {
                    ((SignalCallback) it6.next()).setMobileDataEnabled(message.arg1 != 0);
                }
                break;
            case 6:
                if (message.arg1 == 0) {
                    this.mEmergencyListeners.remove((NetworkController.EmergencyListener) message.obj);
                    break;
                } else {
                    this.mEmergencyListeners.add((NetworkController.EmergencyListener) message.obj);
                    break;
                }
            case 7:
                if (message.arg1 == 0) {
                    this.mSignalCallbacks.remove((SignalCallback) message.obj);
                    break;
                } else {
                    this.mSignalCallbacks.add((SignalCallback) message.obj);
                    break;
                }
        }
    }

    public final void recordLastCallback(String str) {
        int i = this.mHistoryIndex;
        this.mHistory[i] = str;
        this.mHistoryIndex = (i + 1) % 64;
    }

    @Override // com.android.systemui.statusbar.connectivity.SignalCallback
    public final void setConnectivityStatus(final boolean z, final boolean z2, final boolean z3) {
        StringBuilder m = KeyguardKnoxGuardViewController$$ExternalSyntheticOutline0.m("setConnectivityStatus: noDefaultNetwork=", ",noValidatedNetwork=", ",noNetworksAvailable=", z, z2);
        m.append(z3);
        String sb = m.toString();
        if (!sb.equals(this.mLastCallback)) {
            this.mLastCallback = sb;
            recordLastCallback(SSDF.format(Long.valueOf(System.currentTimeMillis())) + "," + sb + ",");
        }
        post(new Runnable() { // from class: com.android.systemui.statusbar.connectivity.CallbackHandler$$ExternalSyntheticLambda2
            @Override // java.lang.Runnable
            public final void run() {
                CallbackHandler callbackHandler = CallbackHandler.this;
                boolean z4 = z;
                boolean z5 = z2;
                boolean z6 = z3;
                Iterator it = callbackHandler.mSignalCallbacks.iterator();
                while (it.hasNext()) {
                    ((SignalCallback) it.next()).setConnectivityStatus(z4, z5, z6);
                }
            }
        });
    }

    @Override // com.android.systemui.statusbar.connectivity.SignalCallback
    public final void setEthernetIndicators(IconState iconState) {
        recordLastCallback(SSDF.format(Long.valueOf(System.currentTimeMillis())) + ",setEthernetIndicators: icon=" + iconState);
        obtainMessage(3, iconState).sendToTarget();
    }

    @Override // com.android.systemui.statusbar.connectivity.SignalCallback
    public final void setIsAirplaneMode(IconState iconState) {
        String str = "setIsAirplaneMode: icon=" + iconState;
        if (!str.equals(this.mLastCallback)) {
            this.mLastCallback = str;
            recordLastCallback(SSDF.format(Long.valueOf(System.currentTimeMillis())) + "," + str + ",");
        }
        obtainMessage(4, iconState).sendToTarget();
    }

    @Override // com.android.systemui.statusbar.connectivity.SignalCallback
    public final void setMobileDataEnabled(boolean z) {
        obtainMessage(5, z ? 1 : 0, 0).sendToTarget();
    }

    @Override // com.android.systemui.statusbar.connectivity.SignalCallback
    public final void setMobileDataIndicators(MobileDataIndicators mobileDataIndicators) {
        recordLastCallback(SSDF.format(Long.valueOf(System.currentTimeMillis())) + "," + mobileDataIndicators);
        post(new CallbackHandler$$ExternalSyntheticLambda0(this, mobileDataIndicators, 1));
    }

    @Override // com.android.systemui.statusbar.connectivity.SignalCallback
    public final void setNoSims(boolean z, boolean z2) {
        obtainMessage(2, z ? 1 : 0, z2 ? 1 : 0).sendToTarget();
    }

    @Override // com.android.systemui.statusbar.connectivity.SignalCallback
    public final void setSubs(List list) {
        StringBuilder sb = new StringBuilder("setSubs: subs=");
        sb.append(list == null ? "" : list.toString());
        String sb2 = sb.toString();
        if (!sb2.equals(this.mLastCallback)) {
            this.mLastCallback = sb2;
            recordLastCallback(SSDF.format(Long.valueOf(System.currentTimeMillis())) + "," + sb2 + ",");
        }
        obtainMessage(1, list).sendToTarget();
    }

    @Override // com.android.systemui.statusbar.connectivity.SignalCallback
    public final void setWifiIndicators(WifiIndicators wifiIndicators) {
        recordLastCallback(SSDF.format(Long.valueOf(System.currentTimeMillis())) + "," + wifiIndicators);
        post(new CallbackHandler$$ExternalSyntheticLambda0(this, wifiIndicators, 0));
    }
}
