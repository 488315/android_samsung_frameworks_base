package android.content.pm;

import android.inputmethodservice.navigationbar.NavigationBarInflaterView;
import android.os.Parcel;
import android.os.Parcelable;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

/* loaded from: classes.dex */
public final class VersionedPackage implements Parcelable {
    public static final Parcelable.Creator<VersionedPackage> CREATOR = new Parcelable.Creator<VersionedPackage>() { // from class: android.content.pm.VersionedPackage.1
        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public VersionedPackage createFromParcel(Parcel parcel) {
            return new VersionedPackage(parcel);
        }

        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public VersionedPackage[] newArray(int i) {
            return new VersionedPackage[i];
        }
    };
    private final String mPackageName;
    private final long mVersionCode;

    @Retention(RetentionPolicy.SOURCE)
    public @interface VersionCode {
    }

    @Override // android.os.Parcelable
    public int describeContents() {
        return 0;
    }

    public VersionedPackage(String str, int i) {
        this.mPackageName = str;
        this.mVersionCode = i;
    }

    public VersionedPackage(String str, long j) {
        this.mPackageName = str;
        this.mVersionCode = j;
    }

    private VersionedPackage(Parcel parcel) {
        this.mPackageName = parcel.readString8();
        this.mVersionCode = parcel.readLong();
    }

    public String getPackageName() {
        return this.mPackageName;
    }

    @Deprecated
    public int getVersionCode() {
        return (int) (this.mVersionCode & 2147483647L);
    }

    public long getLongVersionCode() {
        return this.mVersionCode;
    }

    public String toString() {
        return "VersionedPackage[" + this.mPackageName + "/" + this.mVersionCode + NavigationBarInflaterView.SIZE_MOD_END;
    }

    public boolean equals(Object obj) {
        if (!(obj instanceof VersionedPackage)) {
            return false;
        }
        VersionedPackage versionedPackage = (VersionedPackage) obj;
        return versionedPackage.mPackageName.equals(this.mPackageName) && versionedPackage.mVersionCode == this.mVersionCode;
    }

    public int hashCode() {
        return (this.mPackageName.hashCode() * 31) + Long.hashCode(this.mVersionCode);
    }

    @Override // android.os.Parcelable
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeString8(this.mPackageName);
        parcel.writeLong(this.mVersionCode);
    }
}
