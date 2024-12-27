package com.android.server.biometrics.sensors;

import java.util.ArrayList;
import java.util.List;

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
