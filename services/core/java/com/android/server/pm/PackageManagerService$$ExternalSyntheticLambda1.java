package com.android.server.pm;

import android.content.pm.PackagePartitions;

import java.util.function.Function;

public final /* synthetic */ class PackageManagerService$$ExternalSyntheticLambda1
        implements Function {
    @Override // java.util.function.Function
    public final Object apply(Object obj) {
        return new ScanPartition((PackagePartitions.SystemPartition) obj);
    }
}
