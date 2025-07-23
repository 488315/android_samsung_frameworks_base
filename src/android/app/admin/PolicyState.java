package android.app.admin;

import android.annotation.SystemApi;
import android.os.Parcel;
import android.os.Parcelable;
import java.util.LinkedHashMap;
import java.util.Objects;

@SystemApi
/* loaded from: classes.dex */
public final class PolicyState<V> implements Parcelable {
    public static final Parcelable.Creator<PolicyState<?>> CREATOR = new Parcelable.Creator<PolicyState<?>>() { // from class: android.app.admin.PolicyState.1
        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public PolicyState<?> createFromParcel(Parcel parcel) {
            return new PolicyState<>(parcel);
        }

        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public PolicyState<?>[] newArray(int i) {
            return new PolicyState[i];
        }
    };
    private PolicyValue<V> mCurrentResolvedPolicy;
    private final LinkedHashMap<EnforcingAdmin, PolicyValue<V>> mPoliciesSetByAdmins;
    private ResolutionMechanism<V> mResolutionMechanism;

    @Override // android.os.Parcelable
    public int describeContents() {
        return 0;
    }

    public PolicyState(LinkedHashMap<EnforcingAdmin, PolicyValue<V>> linkedHashMap, PolicyValue<V> policyValue, ResolutionMechanism<V> resolutionMechanism) {
        LinkedHashMap<EnforcingAdmin, PolicyValue<V>> linkedHashMap2 = new LinkedHashMap<>();
        this.mPoliciesSetByAdmins = linkedHashMap2;
        Objects.requireNonNull(linkedHashMap);
        Objects.requireNonNull(resolutionMechanism);
        linkedHashMap2.putAll(linkedHashMap);
        this.mCurrentResolvedPolicy = policyValue;
        this.mResolutionMechanism = resolutionMechanism;
    }

    private PolicyState(Parcel parcel) {
        this.mPoliciesSetByAdmins = new LinkedHashMap<>();
        int readInt = parcel.readInt();
        for (int i = 0; i < readInt; i++) {
            this.mPoliciesSetByAdmins.put((EnforcingAdmin) parcel.readParcelable(EnforcingAdmin.class.getClassLoader()), (PolicyValue) parcel.readParcelable(PolicyValue.class.getClassLoader()));
        }
        this.mCurrentResolvedPolicy = (PolicyValue) parcel.readParcelable(PolicyValue.class.getClassLoader());
        this.mResolutionMechanism = (ResolutionMechanism) parcel.readParcelable(ResolutionMechanism.class.getClassLoader());
    }

    public LinkedHashMap<EnforcingAdmin, V> getPoliciesSetByAdmins() {
        LinkedHashMap<EnforcingAdmin, V> linkedHashMap = new LinkedHashMap<>();
        for (EnforcingAdmin enforcingAdmin : this.mPoliciesSetByAdmins.keySet()) {
            linkedHashMap.put(enforcingAdmin, this.mPoliciesSetByAdmins.get(enforcingAdmin).getValue());
        }
        return linkedHashMap;
    }

    public V getCurrentResolvedPolicy() {
        PolicyValue<V> policyValue = this.mCurrentResolvedPolicy;
        if (policyValue == null) {
            return null;
        }
        return policyValue.getValue();
    }

    public ResolutionMechanism<V> getResolutionMechanism() {
        return this.mResolutionMechanism;
    }

    public String toString() {
        return "PolicyState { mPoliciesSetByAdmins= " + this.mPoliciesSetByAdmins + ", mCurrentResolvedPolicy= " + this.mCurrentResolvedPolicy + ", mResolutionMechanism= " + this.mResolutionMechanism + " }";
    }

    @Override // android.os.Parcelable
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeInt(this.mPoliciesSetByAdmins.size());
        for (EnforcingAdmin enforcingAdmin : this.mPoliciesSetByAdmins.keySet()) {
            parcel.writeParcelable(enforcingAdmin, i);
            parcel.writeParcelable(this.mPoliciesSetByAdmins.get(enforcingAdmin), i);
        }
        parcel.writeParcelable(this.mCurrentResolvedPolicy, i);
        parcel.writeParcelable(this.mResolutionMechanism, i);
    }
}
