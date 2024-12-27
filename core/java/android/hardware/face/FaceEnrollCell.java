package android.hardware.face;

import android.os.Parcel;
import android.os.Parcelable;

/* loaded from: classes2.dex */
public final class FaceEnrollCell implements Parcelable {
    public static final Parcelable.Creator<FaceEnrollCell> CREATOR = new Parcelable.Creator<FaceEnrollCell>() { // from class: android.hardware.face.FaceEnrollCell.1
        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public FaceEnrollCell createFromParcel(Parcel source) {
            return new FaceEnrollCell(source);
        }

        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public FaceEnrollCell[] newArray(int size) {
            return new FaceEnrollCell[size];
        }
    };
    private final int mX;
    private final int mY;
    private final int mZ;

    public FaceEnrollCell(int x, int y, int z) {
        this.mX = x;
        this.mY = y;
        this.mZ = z;
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

    private FaceEnrollCell(Parcel source) {
        this.mX = source.readInt();
        this.mY = source.readInt();
        this.mZ = source.readInt();
    }

    @Override // android.os.Parcelable
    public int describeContents() {
        return 0;
    }

    @Override // android.os.Parcelable
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(this.mX);
        dest.writeInt(this.mY);
        dest.writeInt(this.mZ);
    }
}
