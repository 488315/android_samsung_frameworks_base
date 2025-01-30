package android.app.admin;

import android.annotation.SystemApi;
import android.os.Parcel;
import android.os.Parcelable;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

@SystemApi
/* loaded from: classes.dex */
public final class LockTaskPolicy extends PolicyValue<LockTaskPolicy> {
  public static final Parcelable.Creator<LockTaskPolicy> CREATOR =
      new Parcelable.Creator<LockTaskPolicy>() { // from class: android.app.admin.LockTaskPolicy.1
        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public LockTaskPolicy createFromParcel(Parcel source) {
          return new LockTaskPolicy(source);
        }

        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public LockTaskPolicy[] newArray(int size) {
          return new LockTaskPolicy[size];
        }
      };
  public static final int DEFAULT_LOCK_TASK_FLAG = 16;
  private int mFlags;
  private Set<String> mPackages;

  public Set<String> getPackages() {
    return this.mPackages;
  }

  public int getFlags() {
    return this.mFlags;
  }

  /* JADX WARN: Can't rename method to resolve collision */
  @Override // android.app.admin.PolicyValue
  public LockTaskPolicy getValue() {
    return this;
  }

  public LockTaskPolicy(Set<String> packages) {
    HashSet hashSet = new HashSet();
    this.mPackages = hashSet;
    this.mFlags = 16;
    if (packages != null) {
      hashSet.addAll(packages);
    }
    setValue(this);
  }

  public LockTaskPolicy(int flags) {
    this.mPackages = new HashSet();
    this.mFlags = 16;
    this.mFlags = flags;
    setValue(this);
  }

  public LockTaskPolicy(Set<String> packages, int flags) {
    HashSet hashSet = new HashSet();
    this.mPackages = hashSet;
    this.mFlags = 16;
    if (packages != null) {
      hashSet.addAll(packages);
    }
    this.mFlags = flags;
    setValue(this);
  }

  private LockTaskPolicy(Parcel source) {
    this.mPackages = new HashSet();
    this.mFlags = 16;
    int size = source.readInt();
    this.mPackages = new HashSet();
    for (int i = 0; i < size; i++) {
      this.mPackages.add(source.readString());
    }
    int i2 = source.readInt();
    this.mFlags = i2;
    setValue(this);
  }

  public LockTaskPolicy(LockTaskPolicy policy) {
    this.mPackages = new HashSet();
    this.mFlags = 16;
    this.mPackages = new HashSet(policy.mPackages);
    this.mFlags = policy.mFlags;
    setValue(this);
  }

  public void setPackages(Set<String> packages) {
    Objects.requireNonNull(packages);
    this.mPackages = new HashSet(packages);
  }

  public void setFlags(int flags) {
    this.mFlags = flags;
  }

  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    LockTaskPolicy other = (LockTaskPolicy) o;
    if (Objects.equals(this.mPackages, other.mPackages) && this.mFlags == other.mFlags) {
      return true;
    }
    return false;
  }

  public int hashCode() {
    return Objects.hash(this.mPackages, Integer.valueOf(this.mFlags));
  }

  public String toString() {
    return "LockTaskPolicy {mPackages= "
        + String.join(", ", this.mPackages)
        + "; mFlags= "
        + this.mFlags
        + " }";
  }

  @Override // android.os.Parcelable
  public int describeContents() {
    return 0;
  }

  @Override // android.os.Parcelable
  public void writeToParcel(Parcel dest, int flags) {
    dest.writeInt(this.mPackages.size());
    for (String p : this.mPackages) {
      dest.writeString(p);
    }
    dest.writeInt(this.mFlags);
  }
}
