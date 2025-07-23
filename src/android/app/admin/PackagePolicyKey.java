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
public final class PackagePolicyKey extends PolicyKey {
    private static final String ATTR_PACKAGE_NAME = "package-name";
    public static final Parcelable.Creator<PackagePolicyKey> CREATOR = new Parcelable.Creator<PackagePolicyKey>() { // from class: android.app.admin.PackagePolicyKey.1
        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public PackagePolicyKey createFromParcel(Parcel parcel) {
            return new PackagePolicyKey(parcel);
        }

        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public PackagePolicyKey[] newArray(int i) {
            return new PackagePolicyKey[i];
        }
    };
    private final String mPackageName;

    @Override // android.os.Parcelable
    public int describeContents() {
        return 0;
    }

    public PackagePolicyKey(String str, String str2) {
        super(str);
        PolicySizeVerifier.enforceMaxPackageNameLength(str2);
        this.mPackageName = (String) Objects.requireNonNull(str2);
    }

    private PackagePolicyKey(Parcel parcel) {
        super(parcel.readString());
        this.mPackageName = parcel.readString();
    }

    public PackagePolicyKey(String str) {
        super(str);
        this.mPackageName = null;
    }

    public String getPackageName() {
        return this.mPackageName;
    }

    @Override // android.app.admin.PolicyKey
    public void saveToXml(TypedXmlSerializer typedXmlSerializer) throws IOException {
        typedXmlSerializer.attribute(null, "policy-identifier", getIdentifier());
        typedXmlSerializer.attribute(null, ATTR_PACKAGE_NAME, this.mPackageName);
    }

    @Override // android.app.admin.PolicyKey
    public PackagePolicyKey readFromXml(TypedXmlPullParser typedXmlPullParser) throws XmlPullParserException, IOException {
        return new PackagePolicyKey(typedXmlPullParser.getAttributeValue(null, "policy-identifier"), typedXmlPullParser.getAttributeValue(null, ATTR_PACKAGE_NAME));
    }

    @Override // android.app.admin.PolicyKey
    public void writeToBundle(Bundle bundle) {
        bundle.putString(PolicyUpdateReceiver.EXTRA_POLICY_KEY, getIdentifier());
        Bundle bundle2 = new Bundle();
        bundle2.putString(PolicyUpdateReceiver.EXTRA_PACKAGE_NAME, this.mPackageName);
        bundle.putBundle(PolicyUpdateReceiver.EXTRA_POLICY_BUNDLE_KEY, bundle2);
    }

    @Override // android.app.admin.PolicyKey
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj != null && getClass() == obj.getClass()) {
            PackagePolicyKey packagePolicyKey = (PackagePolicyKey) obj;
            if (Objects.equals(getIdentifier(), packagePolicyKey.getIdentifier()) && Objects.equals(this.mPackageName, packagePolicyKey.mPackageName)) {
                return true;
            }
        }
        return false;
    }

    @Override // android.app.admin.PolicyKey
    public int hashCode() {
        return Objects.hash(getIdentifier(), this.mPackageName);
    }

    public String toString() {
        return "PackagePolicyKey{mPolicyKey= " + getIdentifier() + "; mPackageName= " + this.mPackageName + "}";
    }

    @Override // android.os.Parcelable
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeString(getIdentifier());
        parcel.writeString(this.mPackageName);
    }
}
