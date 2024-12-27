package com.android.server.accessibility;

import android.hardware.display.DisplayManager;
import android.view.Display;

import com.android.internal.util.FunctionalUtils;

public final /* synthetic */ class ProxyManager$$ExternalSyntheticLambda5 implements Runnable {
    public final /* synthetic */ int $r8$classId;
    public final /* synthetic */ ProxyManager f$0;
    public final /* synthetic */ int f$1;

    public /* synthetic */ ProxyManager$$ExternalSyntheticLambda5(
            ProxyManager proxyManager, int i, int i2) {
        this.$r8$classId = i2;
        this.f$0 = proxyManager;
        this.f$1 = i;
    }

    @Override // java.lang.Runnable
    public final void run() {
        Display display;
        switch (this.$r8$classId) {
            case 0:
                ProxyManager proxyManager = this.f$0;
                int i = this.f$1;
                AccessibilityInputFilter accessibilityInputFilter = proxyManager.mA11yInputFilter;
                if (accessibilityInputFilter == null || !accessibilityInputFilter.mInstalled) {
                    return;
                }
                accessibilityInputFilter.disableFeaturesForDisplay(i);
                accessibilityInputFilter.resetStreamStateForDisplay(i);
                return;
            case 1:
                final ProxyManager proxyManager2 = this.f$0;
                final int i2 = this.f$1;
                proxyManager2.getClass();
                final int i3 = 0;
                proxyManager2.broadcastToClientsLocked(
                        FunctionalUtils.ignoreRemoteException(
                                new FunctionalUtils
                                        .RemoteExceptionIgnoringConsumer() { // from class:
                                                                             // com.android.server.accessibility.ProxyManager$$ExternalSyntheticLambda11
                                    public final void acceptOrThrow(Object obj) {
                                        int computeRelevantEventTypesLocked;
                                        switch (i3) {
                                            case 0:
                                                ProxyManager proxyManager3 = proxyManager2;
                                                int i4 = i2;
                                                AccessibilityManagerService.Client client =
                                                        (AccessibilityManagerService.Client) obj;
                                                proxyManager3.getClass();
                                                if (client.mDeviceId == i4) {
                                                    synchronized (proxyManager3.mLock) {
                                                        client.mCallback.notifyServicesStateChanged(
                                                                proxyManager3
                                                                        .getRecommendedTimeoutMillisLocked(
                                                                                i4));
                                                    }
                                                    return;
                                                }
                                                return;
                                            default:
                                                ProxyManager proxyManager4 = proxyManager2;
                                                int i5 = i2;
                                                AccessibilityManagerService.Client client2 =
                                                        (AccessibilityManagerService.Client) obj;
                                                proxyManager4.getClass();
                                                if (client2.mDeviceId != i5
                                                        || client2.mLastSentRelevantEventTypes
                                                                == (computeRelevantEventTypesLocked =
                                                                        proxyManager4
                                                                                .computeRelevantEventTypesLocked(
                                                                                        client2))) {
                                                    return;
                                                }
                                                client2.mLastSentRelevantEventTypes =
                                                        computeRelevantEventTypesLocked;
                                                client2.mCallback.setRelevantEventTypes(
                                                        computeRelevantEventTypesLocked);
                                                return;
                                        }
                                    }
                                }));
                return;
            case 2:
                final ProxyManager proxyManager3 = this.f$0;
                final int i4 = this.f$1;
                synchronized (proxyManager3.mLock) {
                    final int i5 = 1;
                    proxyManager3.broadcastToClientsLocked(
                            FunctionalUtils.ignoreRemoteException(
                                    new FunctionalUtils
                                            .RemoteExceptionIgnoringConsumer() { // from class:
                                                                                 // com.android.server.accessibility.ProxyManager$$ExternalSyntheticLambda11
                                        public final void acceptOrThrow(Object obj) {
                                            int computeRelevantEventTypesLocked;
                                            switch (i5) {
                                                case 0:
                                                    ProxyManager proxyManager32 = proxyManager3;
                                                    int i42 = i4;
                                                    AccessibilityManagerService.Client client =
                                                            (AccessibilityManagerService.Client)
                                                                    obj;
                                                    proxyManager32.getClass();
                                                    if (client.mDeviceId == i42) {
                                                        synchronized (proxyManager32.mLock) {
                                                            client.mCallback
                                                                    .notifyServicesStateChanged(
                                                                            proxyManager32
                                                                                    .getRecommendedTimeoutMillisLocked(
                                                                                            i42));
                                                        }
                                                        return;
                                                    }
                                                    return;
                                                default:
                                                    ProxyManager proxyManager4 = proxyManager3;
                                                    int i52 = i4;
                                                    AccessibilityManagerService.Client client2 =
                                                            (AccessibilityManagerService.Client)
                                                                    obj;
                                                    proxyManager4.getClass();
                                                    if (client2.mDeviceId != i52
                                                            || client2.mLastSentRelevantEventTypes
                                                                    == (computeRelevantEventTypesLocked =
                                                                            proxyManager4
                                                                                    .computeRelevantEventTypesLocked(
                                                                                            client2))) {
                                                        return;
                                                    }
                                                    client2.mLastSentRelevantEventTypes =
                                                            computeRelevantEventTypesLocked;
                                                    client2.mCallback.setRelevantEventTypes(
                                                            computeRelevantEventTypesLocked);
                                                    return;
                                            }
                                        }
                                    }));
                }
                return;
            default:
                ProxyManager proxyManager4 = this.f$0;
                int i6 = this.f$1;
                if (proxyManager4.mA11yInputFilter == null
                        || (display =
                                        ((DisplayManager)
                                                        proxyManager4.mContext.getSystemService(
                                                                "display"))
                                                .getDisplay(i6))
                                == null) {
                    return;
                }
                AccessibilityInputFilter accessibilityInputFilter2 = proxyManager4.mA11yInputFilter;
                if (accessibilityInputFilter2.mInstalled) {
                    accessibilityInputFilter2.resetStreamStateForDisplay(display.getDisplayId());
                    accessibilityInputFilter2.enableFeaturesForDisplay(display);
                    return;
                }
                return;
        }
    }
}
