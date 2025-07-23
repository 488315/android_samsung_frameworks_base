package android.hardware.camera2.utils;

import android.os.Parcel;
import android.os.Parcelable;

/* loaded from: classes2.dex */
public class LongParcelable implements Parcelable {
    public static final Parcelable.Creator<LongParcelable> CREATOR = new Parcelable.Creator<LongParcelable>() { // from class: android.hardware.camera2.utils.LongParcelable.1
        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public LongParcelable createFromParcel(Parcel parcel) {
            return new LongParcelable(parcel);
        }

        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public LongParcelable[] newArray(int i) {
            return new LongParcelable[i];
        }
    };
    private long number;

    @Override // android.os.Parcelable
    public int describeContents() {
        return 0;
    }

    public LongParcelable() {
        this.number = 0L;
    }

    public LongParcelable(long j) {
        this.number = j;
    }

    private LongParcelable(Parcel parcel) {
        readFromParcel(parcel);
    }

    @Override // android.os.Parcelable
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeLong(this.number);
    }

    public void readFromParcel(Parcel parcel) {
        this.number = parcel.readLong();
    }

    public long getNumber() {
        return this.number;
    }

    public void setNumber(long j) {
        this.number = j;
    }
}
