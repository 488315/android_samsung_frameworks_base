package android.os;

import android.content.pm.SignedPackage;
import android.content.pm.SignedPackageParcel;

import java.util.function.Function;

public final /* synthetic */ class SystemConfigManager$$ExternalSyntheticLambda0
        implements Function {
    @Override // java.util.function.Function
    public final Object apply(Object obj) {
        return new SignedPackage((SignedPackageParcel) obj);
    }
}
