package android.app.admin;

import android.annotation.SystemApi;
import android.os.Parcel;
import android.os.Parcelable;
import android.os.UserHandle;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

@SystemApi
/* loaded from: classes.dex */
public final class DevicePolicyState implements Parcelable {
    public static final Parcelable.Creator<DevicePolicyState> CREATOR = new Parcelable.Creator<DevicePolicyState>() { // from class: android.app.admin.DevicePolicyState.1
        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public DevicePolicyState createFromParcel(Parcel parcel) {
            return new DevicePolicyState(parcel);
        }

        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public DevicePolicyState[] newArray(int i) {
            return new DevicePolicyState[i];
        }
    };
    private final Map<UserHandle, Map<PolicyKey, PolicyState<?>>> mPolicies;

    @Override // android.os.Parcelable
    public int describeContents() {
        return 0;
    }

    public DevicePolicyState(Map<UserHandle, Map<PolicyKey, PolicyState<?>>> map) {
        this.mPolicies = (Map) Objects.requireNonNull(map);
    }

    private DevicePolicyState(Parcel parcel) {
        this.mPolicies = new HashMap();
        int readInt = parcel.readInt();
        for (int i = 0; i < readInt; i++) {
            UserHandle of = UserHandle.of(parcel.readInt());
            this.mPolicies.put(of, new HashMap());
            int readInt2 = parcel.readInt();
            for (int i2 = 0; i2 < readInt2; i2++) {
                this.mPolicies.get(of).put((PolicyKey) parcel.readParcelable(PolicyKey.class.getClassLoader()), (PolicyState) parcel.readParcelable(PolicyState.class.getClassLoader()));
            }
        }
    }

    public Map<UserHandle, Map<PolicyKey, PolicyState<?>>> getPoliciesForAllUsers() {
        return this.mPolicies;
    }

    public Map<PolicyKey, PolicyState<?>> getPoliciesForUser(UserHandle userHandle) {
        return this.mPolicies.containsKey(userHandle) ? this.mPolicies.get(userHandle) : new HashMap();
    }

    public String toString() {
        return "DevicePolicyState { mPolicies= " + this.mPolicies + " }";
    }

    @Override // android.os.Parcelable
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeInt(this.mPolicies.size());
        for (UserHandle userHandle : this.mPolicies.keySet()) {
            parcel.writeInt(userHandle.getIdentifier());
            parcel.writeInt(this.mPolicies.get(userHandle).size());
            for (PolicyKey policyKey : this.mPolicies.get(userHandle).keySet()) {
                parcel.writeParcelable(policyKey, i);
                parcel.writeParcelable(this.mPolicies.get(userHandle).get(policyKey), i);
            }
        }
    }
}
