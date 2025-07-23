package com.android.internal.logging;

import android.os.Parcel;
import android.os.Parcelable;

/* loaded from: classes5.dex */
public final class InstanceId implements Parcelable {
    public static final Parcelable.Creator<InstanceId> CREATOR = new Parcelable.Creator<InstanceId>() { // from class: com.android.internal.logging.InstanceId.1
        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public InstanceId createFromParcel(Parcel parcel) {
            return new InstanceId(parcel);
        }

        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public InstanceId[] newArray(int i) {
            return new InstanceId[i];
        }
    };
    static final int INSTANCE_ID_MAX = 1048576;
    private final int mId;

    @Override // android.os.Parcelable
    public int describeContents() {
        return 0;
    }

    InstanceId(int i) {
        this.mId = Math.min(Math.max(0, i), 1048576);
    }

    private InstanceId(Parcel parcel) {
        this(parcel.readInt());
    }

    public int getId() {
        return this.mId;
    }

    public static InstanceId fakeInstanceId(int i) {
        return new InstanceId(i);
    }

    public int hashCode() {
        return this.mId;
    }

    public boolean equals(Object obj) {
        return (obj instanceof InstanceId) && this.mId == ((InstanceId) obj).mId;
    }

    @Override // android.os.Parcelable
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeInt(this.mId);
    }
}
