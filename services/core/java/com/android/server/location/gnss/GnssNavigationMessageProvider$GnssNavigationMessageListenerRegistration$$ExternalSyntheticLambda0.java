package com.android.server.location.gnss;

import android.location.IGnssNavigationMessageListener;
import com.android.internal.listeners.ListenerExecutor;

/* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
/* loaded from: classes.dex */
public final /* synthetic */ class GnssNavigationMessageProvider$GnssNavigationMessageListenerRegistration$$ExternalSyntheticLambda0 implements ListenerExecutor.ListenerOperation {
    public final void operate(Object obj) {
        ((IGnssNavigationMessageListener) obj).onStatusChanged(1);
    }
}
