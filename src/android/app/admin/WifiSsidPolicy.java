package android.app.admin;

import android.net.wifi.WifiSsid;
import android.os.Parcel;
import android.os.Parcelable;
import android.util.ArraySet;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.util.Objects;
import java.util.Set;

/* loaded from: classes.dex */
public final class WifiSsidPolicy implements Parcelable {
    public static final Parcelable.Creator<WifiSsidPolicy> CREATOR = new Parcelable.Creator<WifiSsidPolicy>() { // from class: android.app.admin.WifiSsidPolicy.1
        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public WifiSsidPolicy createFromParcel(Parcel parcel) {
            return new WifiSsidPolicy(parcel);
        }

        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public WifiSsidPolicy[] newArray(int i) {
            return new WifiSsidPolicy[i];
        }
    };
    public static final int WIFI_SSID_POLICY_TYPE_ALLOWLIST = 0;
    public static final int WIFI_SSID_POLICY_TYPE_DENYLIST = 1;
    private int mPolicyType;
    private ArraySet<WifiSsid> mSsids;

    @Retention(RetentionPolicy.SOURCE)
    public @interface WifiSsidPolicyType {
    }

    @Override // android.os.Parcelable
    public int describeContents() {
        return 0;
    }

    public WifiSsidPolicy(int i, Set<WifiSsid> set) {
        if (set.isEmpty()) {
            throw new IllegalArgumentException("SSID list cannot be empty");
        }
        if (i != 0 && i != 1) {
            throw new IllegalArgumentException("Invalid policy type");
        }
        this.mPolicyType = i;
        this.mSsids = new ArraySet<>(set);
    }

    private WifiSsidPolicy(Parcel parcel) {
        this.mPolicyType = parcel.readInt();
        this.mSsids = parcel.readArraySet(null);
    }

    public Set<WifiSsid> getSsids() {
        return this.mSsids;
    }

    public int getPolicyType() {
        return this.mPolicyType;
    }

    @Override // android.os.Parcelable
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeInt(this.mPolicyType);
        parcel.writeArraySet(this.mSsids);
    }

    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (!(obj instanceof WifiSsidPolicy)) {
            return false;
        }
        WifiSsidPolicy wifiSsidPolicy = (WifiSsidPolicy) obj;
        return this.mPolicyType == wifiSsidPolicy.mPolicyType && Objects.equals(this.mSsids, wifiSsidPolicy.mSsids);
    }

    public int hashCode() {
        return Objects.hash(Integer.valueOf(this.mPolicyType), this.mSsids);
    }
}
