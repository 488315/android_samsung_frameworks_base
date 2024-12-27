package com.android.server.accessibility;

import android.os.RemoteException;

import com.android.internal.util.function.TriConsumer;

/* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
/* loaded from: classes.dex */
public final /* synthetic */ class AccessibilityWindowManager$$ExternalSyntheticLambda0
        implements TriConsumer {
    public final void accept(Object obj, Object obj2, Object obj3) {
        AccessibilityWindowManager accessibilityWindowManager = (AccessibilityWindowManager) obj;
        int intValue = ((Integer) obj2).intValue();
        int intValue2 = ((Integer) obj3).intValue();
        synchronized (accessibilityWindowManager.mLock) {
            try {
                AccessibilityWindowManager.RemoteAccessibilityConnection connectionLocked =
                        accessibilityWindowManager.getConnectionLocked(intValue, intValue2);
                if (connectionLocked == null) {
                    return;
                }
                if (accessibilityWindowManager.mTraceManager.isA11yTracingEnabledForTypes(16L)) {
                    accessibilityWindowManager.mTraceManager.logTrace(
                            "AccessibilityWindowManager.notifyOutsideTouch", 16L);
                }
                try {
                    connectionLocked.mConnection.clearAccessibilityFocus();
                } catch (RemoteException unused) {
                }
            } finally {
            }
        }
    }
}
