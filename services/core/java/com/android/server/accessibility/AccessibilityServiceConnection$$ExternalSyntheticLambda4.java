package com.android.server.accessibility;

import android.accessibilityservice.IAccessibilityServiceClient;
import android.accessibilityservice.TouchInteractionController;
import android.os.RemoteException;
import android.util.Slog;

import com.android.internal.util.function.TriConsumer;

/* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
/* loaded from: classes.dex */
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
