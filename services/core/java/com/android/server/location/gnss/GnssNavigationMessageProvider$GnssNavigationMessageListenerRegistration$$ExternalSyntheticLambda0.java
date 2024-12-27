package com.android.server.location.gnss;

import android.location.IGnssNavigationMessageListener;

import com.android.internal.listeners.ListenerExecutor;

public final /* synthetic */
class GnssNavigationMessageProvider$GnssNavigationMessageListenerRegistration$$ExternalSyntheticLambda0
        implements ListenerExecutor.ListenerOperation {
    public final void operate(Object obj) {
        ((IGnssNavigationMessageListener) obj).onStatusChanged(1);
    }
}
