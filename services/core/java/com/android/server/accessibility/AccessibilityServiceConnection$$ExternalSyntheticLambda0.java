package com.android.server.accessibility;

import android.accessibilityservice.IAccessibilityServiceClient;
import android.os.RemoteException;
import android.util.Slog;
import android.view.MotionEvent;

import java.util.function.BiConsumer;

public final /* synthetic */ class AccessibilityServiceConnection$$ExternalSyntheticLambda0
        implements BiConsumer {
    @Override // java.util.function.BiConsumer
    public final void accept(Object obj, Object obj2) {
        AccessibilityServiceConnection accessibilityServiceConnection =
                (AccessibilityServiceConnection) obj;
        MotionEvent motionEvent = (MotionEvent) obj2;
        IAccessibilityServiceClient serviceInterfaceSafely =
                accessibilityServiceConnection.getServiceInterfaceSafely();
        if (serviceInterfaceSafely != null) {
            try {
                if (accessibilityServiceConnection.mTrace.isA11yTracingEnabled()) {
                    accessibilityServiceConnection.logTraceSvcClient(
                            ".onMotionEvent ", motionEvent.toString());
                }
                serviceInterfaceSafely.onMotionEvent(motionEvent);
            } catch (RemoteException e) {
                Slog.e(
                        "AccessibilityServiceConnection",
                        "Error sending motion event to" + accessibilityServiceConnection.mService,
                        e);
            }
        }
    }
}
