package com.samsung.android.chimera.genie;

import android.os.Parcel;
import android.os.Parcelable;

public final class MemRequest implements Parcelable {
    public static final Parcelable.Creator<MemRequest> CREATOR =
            new Parcelable.Creator<
                    MemRequest>() { // from class: com.samsung.android.chimera.genie.MemRequest.1
                @Override // android.os.Parcelable.Creator
                public MemRequest createFromParcel(Parcel in) {
                    return new MemRequest(in);
                }

                @Override // android.os.Parcelable.Creator
                public MemRequest[] newArray(int size) {
                    return new MemRequest[size];
                }
            };
    public static final int MEMTYPE_CONTIGUOUS = 1;
    public static final int MEMTYPE_DEFAULT = 0;
    private final int mSize;
    private final int mType;

    public @interface MemType {}

    public MemRequest(int type, int size) {
        this.mType = type;
        this.mSize = size;
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
    public int describeContents() {
        return 0;
    }

    @Override // android.os.Parcelable
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(this.mType);
        dest.writeInt(this.mSize);
    }

    protected MemRequest(Parcel in) {
        this.mType = in.readInt();
        this.mSize = in.readInt();
    }
}
