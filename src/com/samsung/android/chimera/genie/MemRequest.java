package com.samsung.android.chimera.genie;

import android.os.Parcel;
import android.os.Parcelable;

/* loaded from: classes6.dex */
public final class MemRequest implements Parcelable {
    public static final Parcelable.Creator<MemRequest> CREATOR = new Parcelable.Creator<MemRequest>() { // from class: com.samsung.android.chimera.genie.MemRequest.1
        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public MemRequest createFromParcel(Parcel parcel) {
            return new MemRequest(parcel);
        }

        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public MemRequest[] newArray(int i) {
            return new MemRequest[i];
        }
    };
    public static final int MEMTYPE_CONTIGUOUS = 1;
    public static final int MEMTYPE_DEFAULT = 0;
    private final int mSize;
    private final int mType;

    public @interface MemType {
    }

    @Override // android.os.Parcelable
    public int describeContents() {
        return 0;
    }

    public MemRequest(int i, int i2) {
        this.mType = i;
        this.mSize = i2;
    }

    public MemRequest() {
        this.mType = 0;
        this.mSize = 1048576;
    }

    public int getSize() {
        return this.mSize;
    }

    public int getType() {
        return this.mType;
    }

    @Override // android.os.Parcelable
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeInt(this.mType);
        parcel.writeInt(this.mSize);
    }

    protected MemRequest(Parcel parcel) {
        this.mType = parcel.readInt();
        this.mSize = parcel.readInt();
    }
}
