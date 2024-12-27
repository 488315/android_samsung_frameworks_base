package com.android.server.accessibility;

import android.accessibilityservice.IAccessibilityServiceClient;
import android.os.RemoteException;
import android.util.Slog;
import android.view.MotionEvent;
import java.util.function.BiConsumer;

/* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
/* loaded from: classes.dex */
public final /* synthetic */ class AccessibilityServiceConnection$$ExternalSyntheticLambda0 implements BiConsumer {
    @Override // java.util.function.BiConsumer
    public final void accept(Object obj, Object obj2) {
        AccessibilityServiceConnection accessibilityServiceConnection = (AccessibilityServiceConnection) obj;
        MotionEvent motionEvent = (MotionEvent) obj2;
        IAccessibilityServiceClient serviceInterfaceSafely = accessibilityServiceConnection.getServiceInterfaceSafely();
        if (serviceInterfaceSafely != null) {
            try {
                if (accessibilityServiceConnection.mTrace.isA11yTracingEnabled()) {
                    accessibilityServiceConnection.logTraceSvcClient(".onMotionEvent ", motionEvent.toString());
                }
                serviceInterfaceSafely.onMotionEvent(motionEvent);
            } catch (RemoteException e) {
                Slog.e("AccessibilityServiceConnection", "Error sending motion event to" + accessibilityServiceConnection.mService, e);
            }
        }
    }
}
