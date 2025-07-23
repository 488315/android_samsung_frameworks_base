package android.app.admin;

import android.os.Parcel;
import android.os.Parcelable;
import android.util.ArraySet;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.util.Collections;
import java.util.Objects;
import java.util.Set;

/* loaded from: classes.dex */
public final class PackagePolicy implements Parcelable {
    public static final Parcelable.Creator<PackagePolicy> CREATOR = new Parcelable.Creator<PackagePolicy>() { // from class: android.app.admin.PackagePolicy.1
        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public PackagePolicy createFromParcel(Parcel parcel) {
            return new PackagePolicy(parcel);
        }

        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public PackagePolicy[] newArray(int i) {
            return new PackagePolicy[i];
        }
    };
    public static final int PACKAGE_POLICY_ALLOWLIST = 3;
    public static final int PACKAGE_POLICY_ALLOWLIST_AND_SYSTEM = 2;
    public static final int PACKAGE_POLICY_BLOCKLIST = 1;
    private ArraySet<String> mPackageNames;
    private int mPolicyType;

    @Retention(RetentionPolicy.SOURCE)
    public @interface PackagePolicyType {
    }

    @Override // android.os.Parcelable
    public int describeContents() {
        return 0;
    }

    public PackagePolicy(int i) {
        this(i, (Set<String>) Collections.EMPTY_SET);
    }

    public PackagePolicy(int i, Set<String> set) {
        if (i != 1 && i != 2 && i != 3) {
            throw new IllegalArgumentException("Invalid policy type");
        }
        this.mPolicyType = i;
        this.mPackageNames = new ArraySet<>(set);
    }

    private PackagePolicy(Parcel parcel) {
        this.mPolicyType = parcel.readInt();
        this.mPackageNames = parcel.readArraySet(null);
    }

    public int getPolicyType() {
        return this.mPolicyType;
    }

    public Set<String> getPackageNames() {
        return Collections.unmodifiableSet(this.mPackageNames);
    }

    public boolean isPackageAllowed(String str, Set<String> set) {
        if (this.mPolicyType == 1) {
            return !this.mPackageNames.contains(str);
        }
        return this.mPackageNames.contains(str) || (this.mPolicyType == 2 && set.contains(str));
    }

    @Override // android.os.Parcelable
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeInt(this.mPolicyType);
        parcel.writeArraySet(this.mPackageNames);
    }

    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (!(obj instanceof PackagePolicy)) {
            return false;
        }
        PackagePolicy packagePolicy = (PackagePolicy) obj;
        return this.mPolicyType == packagePolicy.mPolicyType && this.mPackageNames.equals(packagePolicy.mPackageNames);
    }

    public int hashCode() {
        return Objects.hash(Integer.valueOf(this.mPolicyType), this.mPackageNames);
    }
}
