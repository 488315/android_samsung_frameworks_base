package android.view.inputmethod;

import android.graphics.PointF;
import android.os.Parcel;
import android.os.Parcelable;
import java.util.Objects;

/* loaded from: classes4.dex */
public final class JoinOrSplitGesture extends HandwritingGesture implements Parcelable {
    public static final Parcelable.Creator<JoinOrSplitGesture> CREATOR = new Parcelable.Creator<JoinOrSplitGesture>() { // from class: android.view.inputmethod.JoinOrSplitGesture.1
        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public JoinOrSplitGesture createFromParcel(Parcel parcel) {
            return new JoinOrSplitGesture(parcel);
        }

        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public JoinOrSplitGesture[] newArray(int i) {
            return new JoinOrSplitGesture[i];
        }
    };
    private final PointF mPoint;

    @Override // android.os.Parcelable
    public int describeContents() {
        return 0;
    }

    private JoinOrSplitGesture(PointF pointF, String str) {
        this.mType = 16;
        this.mPoint = pointF;
        this.mFallbackText = str;
    }

    private JoinOrSplitGesture(Parcel parcel) {
        this.mType = 16;
        this.mPoint = (PointF) parcel.readTypedObject(PointF.CREATOR);
        this.mFallbackText = parcel.readString8();
    }

    public PointF getJoinOrSplitPoint() {
        return this.mPoint;
    }

    public static final class Builder {
        private String mFallbackText;
        private PointF mPoint;

        public Builder setJoinOrSplitPoint(PointF pointF) {
            this.mPoint = pointF;
            return this;
        }

        public Builder setFallbackText(String str) {
            this.mFallbackText = str;
            return this;
        }

        public JoinOrSplitGesture build() {
            if (this.mPoint == null) {
                throw new IllegalArgumentException("Point must be set.");
            }
            return new JoinOrSplitGesture(this.mPoint, this.mFallbackText);
        }
    }

    public int hashCode() {
        return Objects.hash(this.mPoint, this.mFallbackText);
    }

    public boolean equals(Object obj) {
        if (!(obj instanceof JoinOrSplitGesture)) {
            return false;
        }
        JoinOrSplitGesture joinOrSplitGesture = (JoinOrSplitGesture) obj;
        return Objects.equals(this.mPoint, joinOrSplitGesture.mPoint) && Objects.equals(this.mFallbackText, joinOrSplitGesture.mFallbackText);
    }

    @Override // android.os.Parcelable
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeTypedObject(this.mPoint, i);
        parcel.writeString8(this.mFallbackText);
    }
}
