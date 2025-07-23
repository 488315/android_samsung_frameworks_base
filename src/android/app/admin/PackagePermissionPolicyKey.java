package android.app.admin;

import android.annotation.SystemApi;
import android.os.Bundle;
import android.os.Parcel;
import android.os.Parcelable;
import com.android.modules.utils.TypedXmlPullParser;
import com.android.modules.utils.TypedXmlSerializer;
import java.io.IOException;
import java.util.Objects;
import org.xmlpull.v1.XmlPullParserException;

@SystemApi
/* loaded from: classes.dex */
public final class PackagePermissionPolicyKey extends PolicyKey {
    private static final String ATTR_PACKAGE_NAME = "package-name";
    private static final String ATTR_PERMISSION_NAME = "permission-name";
    public static final Parcelable.Creator<PackagePermissionPolicyKey> CREATOR = new Parcelable.Creator<PackagePermissionPolicyKey>() { // from class: android.app.admin.PackagePermissionPolicyKey.1
        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public PackagePermissionPolicyKey createFromParcel(Parcel parcel) {
            return new PackagePermissionPolicyKey(parcel);
        }

        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public PackagePermissionPolicyKey[] newArray(int i) {
            return new PackagePermissionPolicyKey[i];
        }
    };
    private final String mPackageName;
    private final String mPermissionName;

    @Override // android.os.Parcelable
    public int describeContents() {
        return 0;
    }

    public PackagePermissionPolicyKey(String str, String str2, String str3) {
        super(str);
        PolicySizeVerifier.enforceMaxPackageNameLength(str2);
        PolicySizeVerifier.enforceMaxStringLength(str3, "permissionName");
        this.mPackageName = (String) Objects.requireNonNull(str2);
        this.mPermissionName = (String) Objects.requireNonNull(str3);
    }

    public PackagePermissionPolicyKey(String str) {
        super(str);
        this.mPackageName = null;
        this.mPermissionName = null;
    }

    private PackagePermissionPolicyKey(Parcel parcel) {
        super(parcel.readString());
        this.mPackageName = parcel.readString();
        this.mPermissionName = parcel.readString();
    }

    public String getPackageName() {
        return this.mPackageName;
    }

    public String getPermissionName() {
        return this.mPermissionName;
    }

    @Override // android.app.admin.PolicyKey
    public void saveToXml(TypedXmlSerializer typedXmlSerializer) throws IOException {
        typedXmlSerializer.attribute(null, "policy-identifier", getIdentifier());
        typedXmlSerializer.attribute(null, ATTR_PACKAGE_NAME, this.mPackageName);
        typedXmlSerializer.attribute(null, ATTR_PERMISSION_NAME, this.mPermissionName);
    }

    @Override // android.app.admin.PolicyKey
    public PackagePermissionPolicyKey readFromXml(TypedXmlPullParser typedXmlPullParser) throws XmlPullParserException, IOException {
        return new PackagePermissionPolicyKey(typedXmlPullParser.getAttributeValue(null, "policy-identifier"), typedXmlPullParser.getAttributeValue(null, ATTR_PACKAGE_NAME), typedXmlPullParser.getAttributeValue(null, ATTR_PERMISSION_NAME));
    }

    @Override // android.app.admin.PolicyKey
    public void writeToBundle(Bundle bundle) {
        bundle.putString(PolicyUpdateReceiver.EXTRA_POLICY_KEY, getIdentifier());
        Bundle bundle2 = new Bundle();
        bundle2.putString(PolicyUpdateReceiver.EXTRA_PACKAGE_NAME, this.mPackageName);
        bundle2.putString(PolicyUpdateReceiver.EXTRA_PERMISSION_NAME, this.mPermissionName);
        bundle.putBundle(PolicyUpdateReceiver.EXTRA_POLICY_BUNDLE_KEY, bundle2);
    }

    @Override // android.app.admin.PolicyKey
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj != null && getClass() == obj.getClass()) {
            PackagePermissionPolicyKey packagePermissionPolicyKey = (PackagePermissionPolicyKey) obj;
            if (Objects.equals(getIdentifier(), packagePermissionPolicyKey.getIdentifier()) && Objects.equals(this.mPackageName, packagePermissionPolicyKey.mPackageName) && Objects.equals(this.mPermissionName, packagePermissionPolicyKey.mPermissionName)) {
                return true;
            }
        }
        return false;
    }

    @Override // android.app.admin.PolicyKey
    public int hashCode() {
        return Objects.hash(getIdentifier(), this.mPackageName, this.mPermissionName);
    }

    public String toString() {
        return "PackagePermissionPolicyKey{mIdentifier= " + getIdentifier() + "; mPackageName= " + this.mPackageName + "; mPermissionName= " + this.mPermissionName + "}";
    }

    @Override // android.os.Parcelable
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeString(getIdentifier());
        parcel.writeString(this.mPackageName);
        parcel.writeString(this.mPermissionName);
    }
}
