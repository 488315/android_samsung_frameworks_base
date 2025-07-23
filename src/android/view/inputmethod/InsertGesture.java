package android.view.inputmethod;

import android.graphics.PointF;
import android.os.Parcel;
import android.os.Parcelable;
import java.util.Objects;

/* loaded from: classes4.dex */
public final class InsertGesture extends HandwritingGesture implements Parcelable {
    public static final Parcelable.Creator<InsertGesture> CREATOR = new Parcelable.Creator<InsertGesture>() { // from class: android.view.inputmethod.InsertGesture.1
        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public InsertGesture createFromParcel(Parcel parcel) {
            return new InsertGesture(parcel);
        }

        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public InsertGesture[] newArray(int i) {
            return new InsertGesture[i];
        }
    };
    private PointF mPoint;
    private String mTextToInsert;

    @Override // android.os.Parcelable
    public int describeContents() {
        return 0;
    }

    private InsertGesture(String str, PointF pointF, String str2) {
        this.mType = 2;
        this.mPoint = pointF;
        this.mTextToInsert = str;
        this.mFallbackText = str2;
    }

    private InsertGesture(Parcel parcel) {
        this.mType = 2;
        this.mFallbackText = parcel.readString8();
        this.mTextToInsert = parcel.readString8();
        this.mPoint = (PointF) parcel.readTypedObject(PointF.CREATOR);
    }

    public String getTextToInsert() {
        return this.mTextToInsert;
    }

    public PointF getInsertionPoint() {
        return this.mPoint;
    }

    public static final class Builder {
        private String mFallbackText;
        private PointF mPoint;
        private String mText;

        public Builder setTextToInsert(String str) {
            this.mText = str;
            return this;
        }

        public Builder setInsertionPoint(PointF pointF) {
            this.mPoint = pointF;
            return this;
        }

        public Builder setFallbackText(String str) {
            this.mFallbackText = str;
            return this;
        }

        public InsertGesture build() {
            if (this.mPoint == null) {
                throw new IllegalArgumentException("Insertion point must be set.");
            }
            if (this.mText == null) {
                throw new IllegalArgumentException("Text to insert must be set.");
            }
            return new InsertGesture(this.mText, this.mPoint, this.mFallbackText);
        }
    }

    public int hashCode() {
        return Objects.hash(this.mPoint, this.mTextToInsert, this.mFallbackText);
    }

    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (!(obj instanceof InsertGesture)) {
            return false;
        }
        InsertGesture insertGesture = (InsertGesture) obj;
        if (Objects.equals(this.mFallbackText, insertGesture.mFallbackText) && Objects.equals(this.mTextToInsert, insertGesture.mTextToInsert)) {
            return Objects.equals(this.mPoint, insertGesture.mPoint);
        }
        return false;
    }

    @Override // android.os.Parcelable
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeString8(this.mFallbackText);
        parcel.writeString8(this.mTextToInsert);
        parcel.writeTypedObject(this.mPoint, i);
    }
}
