package com.android.server.pm.verify.domain;

import android.util.PackageUtils;

import com.android.server.pm.Computer;
import com.android.server.pm.PackageSetting;

import java.util.function.Function;

/* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
public final /* synthetic */ class DomainVerificationService$$ExternalSyntheticLambda1
        implements Function {
    public final /* synthetic */ Computer f$0;

    public /* synthetic */ DomainVerificationService$$ExternalSyntheticLambda1(Computer computer) {
        this.f$0 = computer;
    }

    @Override // java.util.function.Function
    public final Object apply(Object obj) {
        PackageSetting packageStateInternal = this.f$0.getPackageStateInternal((String) obj);
        if (packageStateInternal == null) {
            return null;
        }
        return PackageUtils.computeSignaturesSha256Digest(
                packageStateInternal.signatures.mSigningDetails.getSignatures());
    }
}
