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
public final class AccountTypePolicyKey extends PolicyKey {
    private static final String ATTR_ACCOUNT_TYPE = "account-type";
    public static final Parcelable.Creator<AccountTypePolicyKey> CREATOR = new Parcelable.Creator<AccountTypePolicyKey>() { // from class: android.app.admin.AccountTypePolicyKey.1
        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public AccountTypePolicyKey createFromParcel(Parcel parcel) {
            return new AccountTypePolicyKey(parcel);
        }

        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public AccountTypePolicyKey[] newArray(int i) {
            return new AccountTypePolicyKey[i];
        }
    };
    private final String mAccountType;

    @Override // android.os.Parcelable
    public int describeContents() {
        return 0;
    }

    public AccountTypePolicyKey(String str, String str2) {
        super(str);
        PolicySizeVerifier.enforceMaxStringLength(str2, "accountType");
        this.mAccountType = (String) Objects.requireNonNull(str2);
    }

    private AccountTypePolicyKey(Parcel parcel) {
        super(parcel.readString());
        this.mAccountType = parcel.readString();
    }

    public AccountTypePolicyKey(String str) {
        super(str);
        this.mAccountType = null;
    }

    public String getAccountType() {
        return this.mAccountType;
    }

    @Override // android.app.admin.PolicyKey
    public void saveToXml(TypedXmlSerializer typedXmlSerializer) throws IOException {
        typedXmlSerializer.attribute(null, "policy-identifier", getIdentifier());
        typedXmlSerializer.attribute(null, ATTR_ACCOUNT_TYPE, this.mAccountType);
    }

    @Override // android.app.admin.PolicyKey
    public AccountTypePolicyKey readFromXml(TypedXmlPullParser typedXmlPullParser) throws XmlPullParserException, IOException {
        return new AccountTypePolicyKey(typedXmlPullParser.getAttributeValue(null, "policy-identifier"), typedXmlPullParser.getAttributeValue(null, ATTR_ACCOUNT_TYPE));
    }

    @Override // android.app.admin.PolicyKey
    public void writeToBundle(Bundle bundle) {
        bundle.putString(PolicyUpdateReceiver.EXTRA_POLICY_KEY, getIdentifier());
        Bundle bundle2 = new Bundle();
        bundle2.putString(PolicyUpdateReceiver.EXTRA_ACCOUNT_TYPE, this.mAccountType);
        bundle.putBundle(PolicyUpdateReceiver.EXTRA_POLICY_BUNDLE_KEY, bundle2);
    }

    @Override // android.app.admin.PolicyKey
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj != null && getClass() == obj.getClass()) {
            AccountTypePolicyKey accountTypePolicyKey = (AccountTypePolicyKey) obj;
            if (Objects.equals(getIdentifier(), accountTypePolicyKey.getIdentifier()) && Objects.equals(this.mAccountType, accountTypePolicyKey.mAccountType)) {
                return true;
            }
        }
        return false;
    }

    @Override // android.app.admin.PolicyKey
    public int hashCode() {
        return Objects.hash(getIdentifier(), this.mAccountType);
    }

    public String toString() {
        return "AccountTypePolicyKey{mPolicyKey= " + getIdentifier() + "; mAccountType= " + this.mAccountType + "}";
    }

    @Override // android.os.Parcelable
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeString(getIdentifier());
        parcel.writeString(this.mAccountType);
    }
}
