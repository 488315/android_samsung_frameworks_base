package android.app.admin;

import android.annotation.SystemApi;
import android.os.Bundle;
import android.os.Parcel;
import android.os.Parcelable;
import java.util.Objects;

@SystemApi
/* loaded from: classes.dex */
public final class UserRestrictionPolicyKey extends PolicyKey {
    public static final Parcelable.Creator<UserRestrictionPolicyKey> CREATOR = new Parcelable.Creator<UserRestrictionPolicyKey>() { // from class: android.app.admin.UserRestrictionPolicyKey.1
        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public UserRestrictionPolicyKey createFromParcel(Parcel parcel) {
            return new UserRestrictionPolicyKey(parcel);
        }

        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public UserRestrictionPolicyKey[] newArray(int i) {
            return new UserRestrictionPolicyKey[i];
        }
    };
    private final String mRestriction;

    @Override // android.os.Parcelable
    public int describeContents() {
        return 0;
    }

    public UserRestrictionPolicyKey(String str, String str2) {
        super(str);
        PolicySizeVerifier.enforceMaxStringLength(str2, "restriction");
        this.mRestriction = (String) Objects.requireNonNull(str2);
    }

    private UserRestrictionPolicyKey(Parcel parcel) {
        this(parcel.readString(), parcel.readString());
    }

    public String getRestriction() {
        return this.mRestriction;
    }

    @Override // android.app.admin.PolicyKey
    public void writeToBundle(Bundle bundle) {
        bundle.putString(PolicyUpdateReceiver.EXTRA_POLICY_KEY, getIdentifier());
    }

    @Override // android.os.Parcelable
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeString(getIdentifier());
        parcel.writeString(this.mRestriction);
    }

    public String toString() {
        return "UserRestrictionPolicyKey " + getIdentifier();
    }
}
