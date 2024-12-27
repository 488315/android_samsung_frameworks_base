package com.android.server.accessibility;

import android.accessibilityservice.IAccessibilityServiceClient;
import android.accessibilityservice.TouchInteractionController;
import android.os.RemoteException;
import android.util.Slog;

import com.android.internal.util.function.TriConsumer;

public final /* synthetic */ class AccessibilityServiceConnection$$ExternalSyntheticLambda4
        implements TriConsumer {
    public final void accept(Object obj, Object obj2, Object obj3) {
        AccessibilityServiceConnection accessibilityServiceConnection =
                (AccessibilityServiceConnection) obj;
        int intValue = ((Integer) obj2).intValue();
        int intValue2 = ((Integer) obj3).intValue();
        IAccessibilityServiceClient serviceInterfaceSafely =
                accessibilityServiceConnection.getServiceInterfaceSafely();
        if (serviceInterfaceSafely != null) {
            try {
                if (accessibilityServiceConnection.mTrace.isA11yTracingEnabled()) {
                    accessibilityServiceConnection.logTraceSvcClient(
                            ".onTouchStateChanged ",
                            TouchInteractionController.stateToString(intValue2));
                }
                serviceInterfaceSafely.onTouchStateChanged(intValue, intValue2);
            } catch (RemoteException e) {
                Slog.e(
                        "AccessibilityServiceConnection",
                        "Error sending motion event to" + accessibilityServiceConnection.mService,
                        e);
            }
        }
    }
}
