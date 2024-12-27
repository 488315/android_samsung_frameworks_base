package android.content.pm;

import android.annotation.SystemApi;

import java.util.Arrays;
import java.util.Objects;

@SystemApi(client = SystemApi.Client.MODULE_LIBRARIES)
public class SignedPackage {
    private final SignedPackageParcel mData;

    public SignedPackage(String packageName, byte[] certificateDigest) {
        SignedPackageParcel data = new SignedPackageParcel();
        data.packageName = packageName;
        data.certificateDigest = certificateDigest;
        this.mData = data;
    }

    public SignedPackage(SignedPackageParcel data) {
        this.mData = data;
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

    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof SignedPackage)) {
            return false;
        }
        SignedPackage that = (SignedPackage) o;
        return this.mData.packageName.equals(that.mData.packageName)
                && Arrays.equals(this.mData.certificateDigest, that.mData.certificateDigest);
    }

    public int hashCode() {
        return Objects.hash(
                this.mData.packageName,
                Integer.valueOf(Arrays.hashCode(this.mData.certificateDigest)));
    }
}
