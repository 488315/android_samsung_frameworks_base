package com.android.server.location.gnss;

import android.location.IGnssMeasurementsListener;

import com.android.internal.listeners.ListenerExecutor;

/* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
/* loaded from: classes.dex */
public final /* synthetic */
class GnssMeasurementsProvider$GnssMeasurementListenerRegistration$$ExternalSyntheticLambda0
        implements ListenerExecutor.ListenerOperation {
    public final void operate(Object obj) {
        ((IGnssMeasurementsListener) obj).onStatusChanged(1);
    }
}
