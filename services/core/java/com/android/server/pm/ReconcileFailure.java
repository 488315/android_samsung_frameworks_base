package com.android.server.pm;

import android.net.ConnectivityModuleConnector$$ExternalSyntheticOutline0;

/* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
final class ReconcileFailure extends PackageManagerException {
    public ReconcileFailure(int i, String str) {
        super(
                i,
                ConnectivityModuleConnector$$ExternalSyntheticOutline0.m(
                        "Reconcile failed: ", str));
    }
}
