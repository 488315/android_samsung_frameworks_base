package com.android.server;

import android.content.pm.PackageInfo;

import java.util.function.Predicate;

public final /* synthetic */ class BinaryTransparencyService$$ExternalSyntheticLambda3
        implements Predicate {
    @Override // java.util.function.Predicate
    public final boolean test(Object obj) {
        return ((PackageInfo) obj).isApex;
    }
}
