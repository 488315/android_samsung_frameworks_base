package android.content.rollback;

import android.annotation.SystemApi;
import android.content.pm.VersionedPackage;
import android.os.Parcel;
import android.os.Parcelable;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

@SystemApi
/* loaded from: classes.dex */
public final class PackageRollbackInfo implements Parcelable {
    public static final Parcelable.Creator<PackageRollbackInfo> CREATOR = new Parcelable.Creator<PackageRollbackInfo>() { // from class: android.content.rollback.PackageRollbackInfo.1
        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public PackageRollbackInfo createFromParcel(Parcel parcel) {
            return new PackageRollbackInfo(parcel);
        }

        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public PackageRollbackInfo[] newArray(int i) {
            return new PackageRollbackInfo[i];
        }
    };
    private final boolean mIsApex;
    private final boolean mIsApkInApex;
    private final List<Integer> mPendingBackups;
    private final ArrayList<RestoreInfo> mPendingRestores;
    private final int mRollbackDataPolicy;
    private final List<Integer> mSnapshottedUsers;
    private final VersionedPackage mVersionRolledBackFrom;
    private final VersionedPackage mVersionRolledBackTo;

    @Override // android.os.Parcelable
    public int describeContents() {
        return 0;
    }

    public static class RestoreInfo {
        public final int appId;
        public final String seInfo;
        public final int userId;

        public RestoreInfo(int i, int i2, String str) {
            this.userId = i;
            this.appId = i2;
            this.seInfo = str;
        }
    }

    public String getPackageName() {
        return this.mVersionRolledBackFrom.getPackageName();
    }

    public VersionedPackage getVersionRolledBackFrom() {
        return this.mVersionRolledBackFrom;
    }

    public VersionedPackage getVersionRolledBackTo() {
        return this.mVersionRolledBackTo;
    }

    public void addPendingBackup(int i) {
        this.mPendingBackups.add(Integer.valueOf(i));
    }

    public List<Integer> getPendingBackups() {
        return this.mPendingBackups;
    }

    public ArrayList<RestoreInfo> getPendingRestores() {
        return this.mPendingRestores;
    }

    public RestoreInfo getRestoreInfo(int i) {
        Iterator<RestoreInfo> it = this.mPendingRestores.iterator();
        while (it.hasNext()) {
            RestoreInfo next = it.next();
            if (next.userId == i) {
                return next;
            }
        }
        return null;
    }

    public void removeRestoreInfo(RestoreInfo restoreInfo) {
        this.mPendingRestores.remove(restoreInfo);
    }

    public boolean isApex() {
        return this.mIsApex;
    }

    public int getRollbackDataPolicy() {
        return this.mRollbackDataPolicy;
    }

    public boolean isApkInApex() {
        return this.mIsApkInApex;
    }

    public List<Integer> getSnapshottedUsers() {
        return this.mSnapshottedUsers;
    }

    public void removePendingBackup(int i) {
        this.mPendingBackups.remove(Integer.valueOf(i));
    }

    public void removePendingRestoreInfo(int i) {
        removeRestoreInfo(getRestoreInfo(i));
    }

    public PackageRollbackInfo(VersionedPackage versionedPackage, VersionedPackage versionedPackage2, List<Integer> list, ArrayList<RestoreInfo> arrayList, boolean z, boolean z2, List<Integer> list2) {
        this(versionedPackage, versionedPackage2, list, arrayList, z, z2, list2, 0);
    }

    public PackageRollbackInfo(VersionedPackage versionedPackage, VersionedPackage versionedPackage2, List<Integer> list, ArrayList<RestoreInfo> arrayList, boolean z, boolean z2, List<Integer> list2, int i) {
        this.mVersionRolledBackFrom = versionedPackage;
        this.mVersionRolledBackTo = versionedPackage2;
        this.mPendingBackups = list;
        this.mPendingRestores = arrayList;
        this.mIsApex = z;
        this.mRollbackDataPolicy = i;
        this.mIsApkInApex = z2;
        this.mSnapshottedUsers = list2;
    }

    private PackageRollbackInfo(Parcel parcel) {
        this.mVersionRolledBackFrom = VersionedPackage.CREATOR.createFromParcel(parcel);
        this.mVersionRolledBackTo = VersionedPackage.CREATOR.createFromParcel(parcel);
        this.mIsApex = parcel.readBoolean();
        this.mIsApkInApex = parcel.readBoolean();
        this.mPendingRestores = null;
        this.mPendingBackups = null;
        this.mSnapshottedUsers = null;
        this.mRollbackDataPolicy = 0;
    }

    @Override // android.os.Parcelable
    public void writeToParcel(Parcel parcel, int i) {
        this.mVersionRolledBackFrom.writeToParcel(parcel, i);
        this.mVersionRolledBackTo.writeToParcel(parcel, i);
        parcel.writeBoolean(this.mIsApex);
        parcel.writeBoolean(this.mIsApkInApex);
    }
}
