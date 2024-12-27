package com.android.server.recoverysystem;

import android.ota.nano.OtaPackageMetadata;

import java.util.function.Predicate;

public final /* synthetic */ class RecoverySystemService$$ExternalSyntheticLambda0
        implements Predicate {
    @Override // java.util.function.Predicate
    public final boolean test(Object obj) {
        return ((OtaPackageMetadata.ApexInfo) obj).isCompressed;
    }
}
