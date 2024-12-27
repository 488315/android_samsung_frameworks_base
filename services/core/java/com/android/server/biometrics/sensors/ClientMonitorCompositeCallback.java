package com.android.server.biometrics.sensors;

import java.util.ArrayList;
import java.util.List;

/* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
/* loaded from: classes.dex */
public final class ClientMonitorCompositeCallback implements ClientMonitorCallback {
    public final List mCallbacks = new ArrayList();

    public ClientMonitorCompositeCallback(ClientMonitorCallback... clientMonitorCallbackArr) {
        for (ClientMonitorCallback clientMonitorCallback : clientMonitorCallbackArr) {
            if (clientMonitorCallback != null) {
                ((ArrayList) this.mCallbacks).add(clientMonitorCallback);
            }
        }
    }

    @Override // com.android.server.biometrics.sensors.ClientMonitorCallback
    public final void onBiometricAction() {
        for (int i = 0; i < ((ArrayList) this.mCallbacks).size(); i++) {
            ((ClientMonitorCallback) ((ArrayList) this.mCallbacks).get(i)).onBiometricAction();
        }
    }

    @Override // com.android.server.biometrics.sensors.ClientMonitorCallback
    public final void onClientFinished(BaseClientMonitor baseClientMonitor, boolean z) {
        for (int size = ((ArrayList) this.mCallbacks).size() - 1; size >= 0; size--) {
            ((ClientMonitorCallback) ((ArrayList) this.mCallbacks).get(size))
                    .onClientFinished(baseClientMonitor, z);
        }
    }

    @Override // com.android.server.biometrics.sensors.ClientMonitorCallback
    public final void onClientStarted(BaseClientMonitor baseClientMonitor) {
        for (int i = 0; i < ((ArrayList) this.mCallbacks).size(); i++) {
            ((ClientMonitorCallback) ((ArrayList) this.mCallbacks).get(i))
                    .onClientStarted(baseClientMonitor);
        }
    }
}
