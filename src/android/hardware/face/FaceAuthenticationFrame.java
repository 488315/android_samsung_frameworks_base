package android.hardware.face;

import android.os.Parcel;
import android.os.Parcelable;

/* loaded from: classes2.dex */
public final class FaceAuthenticationFrame implements Parcelable {
    public static final Parcelable.Creator<FaceAuthenticationFrame> CREATOR = new Parcelable.Creator<FaceAuthenticationFrame>() { // from class: android.hardware.face.FaceAuthenticationFrame.1
        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public FaceAuthenticationFrame createFromParcel(Parcel parcel) {
            return new FaceAuthenticationFrame(parcel);
        }

        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public FaceAuthenticationFrame[] newArray(int i) {
            return new FaceAuthenticationFrame[i];
        }
    };
    private final FaceDataFrame mData;

    @Override // android.os.Parcelable
    public int describeContents() {
        return 0;
    }

    public FaceAuthenticationFrame(FaceDataFrame faceDataFrame) {
        this.mData = faceDataFrame;
    }

    public FaceDataFrame getData() {
        return this.mData;
    }

    private FaceAuthenticationFrame(Parcel parcel) {
        this.mData = (FaceDataFrame) parcel.readParcelable(FaceDataFrame.class.getClassLoader(), FaceDataFrame.class);
    }

    @Override // android.os.Parcelable
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeParcelable(this.mData, i);
    }
}
