package android.content.pm;

import android.annotation.SystemApi;
import java.util.Arrays;
import java.util.Objects;

@SystemApi(client = SystemApi.Client.MODULE_LIBRARIES)
/* loaded from: classes.dex */
public class SignedPackage {
    private final SignedPackageParcel mData;

    public SignedPackage(String str, byte[] bArr) {
        SignedPackageParcel signedPackageParcel = new SignedPackageParcel();
        signedPackageParcel.packageName = str;
        signedPackageParcel.certificateDigest = bArr;
        this.mData = signedPackageParcel;
    }

    public SignedPackage(SignedPackageParcel signedPackageParcel) {
        this.mData = signedPackageParcel;
    }

    public final SignedPackageParcel getData() {
        return this.mData;
    }

    public String getPackageName() {
        return this.mData.packageName;
    }

    public byte[] getCertificateDigest() {
        return this.mData.certificateDigest;
    }

    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj instanceof SignedPackage) {
            SignedPackage signedPackage = (SignedPackage) obj;
            if (this.mData.packageName.equals(signedPackage.mData.packageName) && Arrays.equals(this.mData.certificateDigest, signedPackage.mData.certificateDigest)) {
                return true;
            }
        }
        return false;
    }

    public int hashCode() {
        return Objects.hash(this.mData.packageName, Integer.valueOf(Arrays.hashCode(this.mData.certificateDigest)));
    }
}
