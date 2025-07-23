package android.content.rollback;

import android.annotation.SystemApi;
import android.content.pm.VersionedPackage;
import android.os.Parcel;
import android.os.Parcelable;
import java.util.List;

@SystemApi
/* loaded from: classes.dex */
public final class RollbackInfo implements Parcelable {
    public static final Parcelable.Creator<RollbackInfo> CREATOR = new Parcelable.Creator<RollbackInfo>() { // from class: android.content.rollback.RollbackInfo.1
        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public RollbackInfo createFromParcel(Parcel parcel) {
            return new RollbackInfo(parcel);
        }

        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public RollbackInfo[] newArray(int i) {
            return new RollbackInfo[i];
        }
    };
    private final List<VersionedPackage> mCausePackages;
    private int mCommittedSessionId;
    private final boolean mIsStaged;
    private final List<PackageRollbackInfo> mPackages;
    private final int mRollbackId;
    private int mRollbackImpactLevel;

    @Override // android.os.Parcelable
    public int describeContents() {
        return 0;
    }

    public RollbackInfo(int i, List<PackageRollbackInfo> list, boolean z, List<VersionedPackage> list2, int i2, int i3) {
        this.mRollbackId = i;
        this.mPackages = list;
        this.mIsStaged = z;
        this.mCausePackages = list2;
        this.mCommittedSessionId = i2;
        this.mRollbackImpactLevel = i3;
    }

    public RollbackInfo(int i, List<PackageRollbackInfo> list, boolean z, List<VersionedPackage> list2, int i2) {
        this(i, list, z, list2, i2, 0);
    }

    private RollbackInfo(Parcel parcel) {
        this.mRollbackId = parcel.readInt();
        this.mPackages = parcel.createTypedArrayList(PackageRollbackInfo.CREATOR);
        this.mIsStaged = parcel.readBoolean();
        this.mCausePackages = parcel.createTypedArrayList(VersionedPackage.CREATOR);
        this.mCommittedSessionId = parcel.readInt();
        this.mRollbackImpactLevel = parcel.readInt();
    }

    public int getRollbackId() {
        return this.mRollbackId;
    }

    public List<PackageRollbackInfo> getPackages() {
        return this.mPackages;
    }

    public boolean isStaged() {
        return this.mIsStaged;
    }

    public int getCommittedSessionId() {
        return this.mCommittedSessionId;
    }

    public void setCommittedSessionId(int i) {
        this.mCommittedSessionId = i;
    }

    public List<VersionedPackage> getCausePackages() {
        return this.mCausePackages;
    }

    public int getRollbackImpactLevel() {
        return this.mRollbackImpactLevel;
    }

    public void setRollbackImpactLevel(int i) {
        this.mRollbackImpactLevel = i;
    }

    @Override // android.os.Parcelable
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeInt(this.mRollbackId);
        parcel.writeTypedList(this.mPackages);
        parcel.writeBoolean(this.mIsStaged);
        parcel.writeTypedList(this.mCausePackages);
        parcel.writeInt(this.mCommittedSessionId);
        parcel.writeInt(this.mRollbackImpactLevel);
    }
}
