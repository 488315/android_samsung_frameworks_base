package com.android.systemui.statusbar.connectivity;

import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import com.android.keyguard.EmergencyButtonController$$ExternalSyntheticOutline0;
import com.android.systemui.statusbar.connectivity.NetworkController;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes3.dex */
public class CallbackHandler extends Handler implements NetworkController.EmergencyListener, SignalCallback {
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
        int i = 0;
        switch (message.what) {
            case 0:
                ArrayList arrayList = this.mEmergencyListeners;
                int size = arrayList.size();
                int i2 = 0;
                while (i2 < size) {
                    Object obj = arrayList.get(i2);
                    i2++;
                    ((CallbackHandler) ((NetworkController.EmergencyListener) obj)).obtainMessage(0, message.arg1 != 0 ? 1 : 0, 0).sendToTarget();
                }
                break;
            case 1:
                ArrayList arrayList2 = this.mSignalCallbacks;
                int size2 = arrayList2.size();
                while (i < size2) {
                    Object obj2 = arrayList2.get(i);
                    i++;
                    ((SignalCallback) obj2).setSubs((List) message.obj);
                }
                break;
            case 2:
                ArrayList arrayList3 = this.mSignalCallbacks;
                int size3 = arrayList3.size();
                int i3 = 0;
                while (i3 < size3) {
                    Object obj3 = arrayList3.get(i3);
                    i3++;
                    ((SignalCallback) obj3).setNoSims(message.arg1 != 0, message.arg2 != 0);
                }
                break;
            case 3:
                ArrayList arrayList4 = this.mSignalCallbacks;
                int size4 = arrayList4.size();
                while (i < size4) {
                    Object obj4 = arrayList4.get(i);
                    i++;
                    ((SignalCallback) obj4).setEthernetIndicators((IconState) message.obj);
                }
                break;
            case 4:
                ArrayList arrayList5 = this.mSignalCallbacks;
                int size5 = arrayList5.size();
                while (i < size5) {
                    Object obj5 = arrayList5.get(i);
                    i++;
                    ((SignalCallback) obj5).setIsAirplaneMode((IconState) message.obj);
                }
                break;
            case 5:
                ArrayList arrayList6 = this.mSignalCallbacks;
                int size6 = arrayList6.size();
                int i4 = 0;
                while (i4 < size6) {
                    Object obj6 = arrayList6.get(i4);
                    i4++;
                    ((SignalCallback) obj6).setMobileDataEnabled(message.arg1 != 0);
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
        StringBuilder m = EmergencyButtonController$$ExternalSyntheticOutline0.m("setConnectivityStatus: noDefaultNetwork=", ",noValidatedNetwork=", ",noNetworksAvailable=", z, z2);
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
                ArrayList arrayList = callbackHandler.mSignalCallbacks;
                int size = arrayList.size();
                int i = 0;
                while (i < size) {
                    Object obj = arrayList.get(i);
                    i++;
                    ((SignalCallback) obj).setConnectivityStatus(z4, z5, z6);
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
