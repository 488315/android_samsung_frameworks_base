package com.samsung.android.knox;

import android.os.Parcel;
import android.os.Parcelable;
import android.os.Process;
import android.os.UserHandle;

/* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
/* loaded from: classes3.dex */
public final class ContextInfo implements Parcelable {
    public static final Parcelable.Creator<ContextInfo> CREATOR = new Parcelable.Creator<ContextInfo>() { // from class: com.samsung.android.knox.ContextInfo.1
        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public final ContextInfo[] newArray(int i) {
            return new ContextInfo[i];
        }

        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public final ContextInfo createFromParcel(Parcel parcel) {
            return new ContextInfo(parcel, 0);
        }

        @Override // android.os.Parcelable.Creator
        public final ContextInfo[] newArray(int i) {
            return new ContextInfo[i];
        }
    };
    public static final int DEVICE_CONTAINER_ID = 0;
    public static final int NON_DALESS_CALLER = -1;
    public final int mCallerUid;
    public final int mContainerId;
    public final int mDALessCallerUid;
    public final boolean mParent;

    public /* synthetic */ ContextInfo(Parcel parcel, int i) {
        this(parcel);
    }

    @Override // android.os.Parcelable
    public final int describeContents() {
        return 0;
    }

    public final String toString() {
        return "Caller uid: " + this.mCallerUid + " ,Container id: " + this.mContainerId + " , mParent: " + this.mParent;
    }

    @Override // android.os.Parcelable
    public final void writeToParcel(Parcel parcel, int i) {
        parcel.writeInt(this.mCallerUid);
        parcel.writeInt(this.mContainerId);
        parcel.writeInt(this.mParent ? 1 : 0);
        parcel.writeInt(this.mDALessCallerUid);
    }

    public ContextInfo() {
        int myUid = Process.myUid();
        this.mCallerUid = myUid;
        int userId = UserHandle.getUserId(myUid);
        if (SemPersonaManager.isKnoxId(userId)) {
            this.mContainerId = userId;
        } else {
            this.mContainerId = 0;
        }
        this.mParent = false;
        this.mDALessCallerUid = -1;
    }

    public ContextInfo(int i) {
        this.mCallerUid = i;
        int userId = UserHandle.getUserId(i);
        if (!SemPersonaManager.isKnoxId(userId)) {
            this.mContainerId = 0;
        } else {
            this.mContainerId = userId;
        }
        this.mParent = false;
        this.mDALessCallerUid = -1;
    }

    public ContextInfo(int i, boolean z) {
        this(i, z, -1);
    }

    public ContextInfo(int i, boolean z, int i2) {
        this.mCallerUid = i;
        int userId = UserHandle.getUserId(i);
        if (SemPersonaManager.isKnoxId(userId) && !z) {
            this.mContainerId = userId;
        } else {
            this.mContainerId = 0;
        }
        this.mParent = z;
        this.mDALessCallerUid = i2;
    }

    public ContextInfo(int i, int i2) {
        this.mCallerUid = i;
        this.mContainerId = i2;
        this.mParent = false;
        this.mDALessCallerUid = -1;
    }

    public ContextInfo(int i, int i2, boolean z) {
        this(i, i2, z, -1);
    }

    public ContextInfo(int i, int i2, boolean z, int i3) {
        this.mCallerUid = i;
        this.mContainerId = i2;
        this.mParent = z;
        this.mDALessCallerUid = i3;
    }

    private ContextInfo(Parcel parcel) {
        this.mCallerUid = parcel.readInt();
        this.mContainerId = parcel.readInt();
        this.mParent = parcel.readInt() == 1;
        this.mDALessCallerUid = parcel.readInt();
    }
}
