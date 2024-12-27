package com.android.server.accessibility;

import android.os.RemoteException;
import android.util.Slog;
import android.view.SurfaceControl;
import android.view.accessibility.IAccessibilityInteractionConnectionCallback;
import com.android.internal.util.function.QuintConsumer;

/* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
/* loaded from: classes.dex */
public final /* synthetic */ class AccessibilityManagerService$$ExternalSyntheticLambda21 implements QuintConsumer {
    public final void accept(Object obj, Object obj2, Object obj3, Object obj4, Object obj5) {
        int i;
        AccessibilityManagerService accessibilityManagerService = (AccessibilityManagerService) obj;
        int intValue = ((Integer) obj2).intValue();
        int intValue2 = ((Integer) obj3).intValue();
        SurfaceControl surfaceControl = (SurfaceControl) obj4;
        IAccessibilityInteractionConnectionCallback iAccessibilityInteractionConnectionCallback = (IAccessibilityInteractionConnectionCallback) obj5;
        if (!accessibilityManagerService.mA11yOverlayLayers.contains(intValue2)) {
            accessibilityManagerService.mA11yOverlayLayers.put(intValue2, accessibilityManagerService.mWindowManagerService.getA11yOverlayLayer(intValue2));
        }
        SurfaceControl surfaceControl2 = (SurfaceControl) accessibilityManagerService.mA11yOverlayLayers.get(intValue2);
        if (surfaceControl2 == null) {
            Slog.e("AccessibilityManagerService", "Unable to get accessibility overlay SurfaceControl.");
            accessibilityManagerService.mA11yOverlayLayers.remove(intValue2);
            i = 2;
        } else {
            SurfaceControl.Transaction transaction = new SurfaceControl.Transaction();
            transaction.reparent(surfaceControl, surfaceControl2).setTrustedOverlay(surfaceControl, true).apply();
            transaction.close();
            i = 0;
        }
        if (iAccessibilityInteractionConnectionCallback != null) {
            try {
                iAccessibilityInteractionConnectionCallback.sendAttachOverlayResult(i, intValue);
            } catch (RemoteException e) {
                Slog.e("AccessibilityManagerService", "Exception while attaching overlay.", e);
            }
        }
    }
}
