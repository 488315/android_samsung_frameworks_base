package android.hardware.face;

import android.os.Parcel;
import android.os.Parcelable;

/* loaded from: classes2.dex */
public final class FaceEnrollFrame implements Parcelable {
    public static final Parcelable.Creator<FaceEnrollFrame> CREATOR = new Parcelable.Creator<FaceEnrollFrame>() { // from class: android.hardware.face.FaceEnrollFrame.1
        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public FaceEnrollFrame createFromParcel(Parcel parcel) {
            return new FaceEnrollFrame(parcel);
        }

        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public FaceEnrollFrame[] newArray(int i) {
            return new FaceEnrollFrame[i];
        }
    };
    private final FaceEnrollCell mCell;
    private final FaceDataFrame mData;
    private final int mStage;

    @Override // android.os.Parcelable
    public int describeContents() {
        return 0;
    }

    public FaceEnrollFrame(FaceEnrollCell faceEnrollCell, int i, FaceDataFrame faceDataFrame) {
        this.mCell = faceEnrollCell;
        this.mStage = i;
        this.mData = faceDataFrame;
    }

    public FaceEnrollCell getCell() {
        return this.mCell;
    }

    public int getStage() {
        return this.mStage;
    }

    public FaceDataFrame getData() {
        return this.mData;
    }

    private FaceEnrollFrame(Parcel parcel) {
        this.mCell = (FaceEnrollCell) parcel.readParcelable(FaceEnrollCell.class.getClassLoader(), FaceEnrollCell.class);
        this.mStage = parcel.readInt();
        this.mData = (FaceDataFrame) parcel.readParcelable(FaceDataFrame.class.getClassLoader(), FaceDataFrame.class);
    }

    @Override // android.os.Parcelable
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeParcelable(this.mCell, i);
        parcel.writeInt(this.mStage);
        parcel.writeParcelable(this.mData, i);
    }
}
