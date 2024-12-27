package com.android.server.location.gnss;

import android.location.IGnssMeasurementsListener;

import com.android.internal.listeners.ListenerExecutor;

public final /* synthetic */
class GnssMeasurementsProvider$GnssMeasurementListenerRegistration$$ExternalSyntheticLambda0
        implements ListenerExecutor.ListenerOperation {
    public final void operate(Object obj) {
        ((IGnssMeasurementsListener) obj).onStatusChanged(1);
    }
}
