package com.android.server.pm;

import android.net.ConnectivityModuleConnector$$ExternalSyntheticOutline0;

final class ReconcileFailure extends PackageManagerException {
    public ReconcileFailure(int i, String str) {
        super(
                i,
                ConnectivityModuleConnector$$ExternalSyntheticOutline0.m(
                        "Reconcile failed: ", str));
    }
}
