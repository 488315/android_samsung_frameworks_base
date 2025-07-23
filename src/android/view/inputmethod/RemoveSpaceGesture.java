package android.view.inputmethod;

import android.graphics.PointF;
import android.os.Parcel;
import android.os.Parcelable;
import java.util.Objects;

/* loaded from: classes4.dex */
public final class RemoveSpaceGesture extends HandwritingGesture implements Parcelable {
    public static final Parcelable.Creator<RemoveSpaceGesture> CREATOR = new Parcelable.Creator<RemoveSpaceGesture>() { // from class: android.view.inputmethod.RemoveSpaceGesture.1
        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public RemoveSpaceGesture createFromParcel(Parcel parcel) {
            return new RemoveSpaceGesture(parcel);
        }

        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public RemoveSpaceGesture[] newArray(int i) {
            return new RemoveSpaceGesture[i];
        }
    };
    private final PointF mEndPoint;
    private final PointF mStartPoint;

    @Override // android.os.Parcelable
    public int describeContents() {
        return 0;
    }

    private RemoveSpaceGesture(PointF pointF, PointF pointF2, String str) {
        this.mType = 8;
        this.mStartPoint = pointF;
        this.mEndPoint = pointF2;
        this.mFallbackText = str;
    }

    private RemoveSpaceGesture(Parcel parcel) {
        this.mType = 8;
        this.mStartPoint = (PointF) parcel.readTypedObject(PointF.CREATOR);
        this.mEndPoint = (PointF) parcel.readTypedObject(PointF.CREATOR);
        this.mFallbackText = parcel.readString8();
    }

    public PointF getStartPoint() {
        return this.mStartPoint;
    }

    public PointF getEndPoint() {
        return this.mEndPoint;
    }

    public static final class Builder {
        private PointF mEndPoint;
        private String mFallbackText;
        private PointF mStartPoint;

        public Builder setPoints(PointF pointF, PointF pointF2) {
            this.mStartPoint = pointF;
            this.mEndPoint = pointF2;
            return this;
        }

        public Builder setFallbackText(String str) {
            this.mFallbackText = str;
            return this;
        }

        public RemoveSpaceGesture build() {
            if (this.mStartPoint == null || this.mEndPoint == null) {
                throw new IllegalArgumentException("Start and end points must be set.");
            }
            return new RemoveSpaceGesture(this.mStartPoint, this.mEndPoint, this.mFallbackText);
        }
    }

    public int hashCode() {
        return Objects.hash(this.mStartPoint, this.mEndPoint, this.mFallbackText);
    }

    public boolean equals(Object obj) {
        if (!(obj instanceof RemoveSpaceGesture)) {
            return false;
        }
        RemoveSpaceGesture removeSpaceGesture = (RemoveSpaceGesture) obj;
        return Objects.equals(this.mStartPoint, removeSpaceGesture.mStartPoint) && Objects.equals(this.mEndPoint, removeSpaceGesture.mEndPoint) && Objects.equals(this.mFallbackText, removeSpaceGesture.mFallbackText);
    }

    @Override // android.os.Parcelable
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeTypedObject(this.mStartPoint, i);
        parcel.writeTypedObject(this.mEndPoint, i);
        parcel.writeString8(this.mFallbackText);
    }
}
