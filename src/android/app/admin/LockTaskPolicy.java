package android.app.admin;

import android.annotation.SystemApi;
import android.os.Parcel;
import android.os.Parcelable;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Objects;
import java.util.Set;

@SystemApi
/* loaded from: classes.dex */
public final class LockTaskPolicy extends PolicyValue<LockTaskPolicy> {
    public static final Parcelable.Creator<LockTaskPolicy> CREATOR = new Parcelable.Creator<LockTaskPolicy>() { // from class: android.app.admin.LockTaskPolicy.1
        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public LockTaskPolicy createFromParcel(Parcel parcel) {
            return new LockTaskPolicy(parcel);
        }

        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public LockTaskPolicy[] newArray(int i) {
            return new LockTaskPolicy[i];
        }
    };
    public static final int DEFAULT_LOCK_TASK_FLAG = 16;
    private int mFlags;
    private Set<String> mPackages;

    @Override // android.os.Parcelable
    public int describeContents() {
        return 0;
    }

    /* JADX WARN: Can't rename method to resolve collision */
    @Override // android.app.admin.PolicyValue
    public LockTaskPolicy getValue() {
        return this;
    }

    public Set<String> getPackages() {
        return this.mPackages;
    }

    public int getFlags() {
        return this.mFlags;
    }

    public LockTaskPolicy(Set<String> set) {
        this.mPackages = new HashSet();
        this.mFlags = 16;
        if (set != null) {
            setPackagesInternal(set);
        }
        setValue(this);
    }

    public LockTaskPolicy(int i) {
        this.mPackages = new HashSet();
        this.mFlags = i;
        setValue(this);
    }

    public LockTaskPolicy(Set<String> set, int i) {
        this.mPackages = new HashSet();
        this.mFlags = 16;
        if (set != null) {
            setPackagesInternal(set);
        }
        this.mFlags = i;
        setValue(this);
    }

    private LockTaskPolicy(Parcel parcel) {
        this.mPackages = new HashSet();
        this.mFlags = 16;
        int i = parcel.readInt();
        this.mPackages = new HashSet();
        for (int i2 = 0; i2 < i; i2++) {
            this.mPackages.add(parcel.readString());
        }
        this.mFlags = parcel.readInt();
        setValue(this);
    }

    public LockTaskPolicy(LockTaskPolicy lockTaskPolicy) {
        this.mPackages = new HashSet();
        this.mFlags = 16;
        this.mPackages = new HashSet(lockTaskPolicy.mPackages);
        this.mFlags = lockTaskPolicy.mFlags;
        setValue(this);
    }

    public void setPackages(Set<String> set) {
        Objects.requireNonNull(set);
        setPackagesInternal(set);
    }

    public void setFlags(int i) {
        this.mFlags = i;
    }

    private void setPackagesInternal(Set<String> set) {
        Iterator<String> it = set.iterator();
        while (it.hasNext()) {
            PolicySizeVerifier.enforceMaxPackageNameLength(it.next());
        }
        this.mPackages = new HashSet(set);
    }

    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj != null && getClass() == obj.getClass()) {
            LockTaskPolicy lockTaskPolicy = (LockTaskPolicy) obj;
            if (Objects.equals(this.mPackages, lockTaskPolicy.mPackages) && this.mFlags == lockTaskPolicy.mFlags) {
                return true;
            }
        }
        return false;
    }

    public int hashCode() {
        return Objects.hash(this.mPackages, Integer.valueOf(this.mFlags));
    }

    public String toString() {
        return "LockTaskPolicy {mPackages= " + String.join(", ", this.mPackages) + "; mFlags= " + this.mFlags + " }";
    }

    @Override // android.os.Parcelable
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeInt(this.mPackages.size());
        Iterator<String> it = this.mPackages.iterator();
        while (it.hasNext()) {
            parcel.writeString(it.next());
        }
        parcel.writeInt(this.mFlags);
    }
}
