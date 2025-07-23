package android.hardware.face;

import android.os.Parcel;
import android.os.Parcelable;

/* loaded from: classes2.dex */
public final class FaceEnrollCell implements Parcelable {
    public static final Parcelable.Creator<FaceEnrollCell> CREATOR = new Parcelable.Creator<FaceEnrollCell>() { // from class: android.hardware.face.FaceEnrollCell.1
        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public FaceEnrollCell createFromParcel(Parcel parcel) {
            return new FaceEnrollCell(parcel);
        }

        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public FaceEnrollCell[] newArray(int i) {
            return new FaceEnrollCell[i];
        }
    };
    private final int mX;
    private final int mY;
    private final int mZ;

    @Override // android.os.Parcelable
    public int describeContents() {
        return 0;
    }

    public FaceEnrollCell(int i, int i2, int i3) {
        this.mX = i;
        this.mY = i2;
        this.mZ = i3;
    }

    public int getX() {
        return this.mX;
    }

    public int getY() {
        return this.mY;
    }

    public int getZ() {
        return this.mZ;
    }

    private FaceEnrollCell(Parcel parcel) {
        this.mX = parcel.readInt();
        this.mY = parcel.readInt();
        this.mZ = parcel.readInt();
    }

    @Override // android.os.Parcelable
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeInt(this.mX);
        parcel.writeInt(this.mY);
        parcel.writeInt(this.mZ);
    }
}
