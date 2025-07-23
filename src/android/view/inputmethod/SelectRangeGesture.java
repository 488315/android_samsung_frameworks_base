package android.view.inputmethod;

import android.graphics.RectF;
import android.os.Parcel;
import android.os.Parcelable;
import java.util.Objects;

/* loaded from: classes4.dex */
public final class SelectRangeGesture extends PreviewableHandwritingGesture implements Parcelable {
    public static final Parcelable.Creator<SelectRangeGesture> CREATOR = new Parcelable.Creator<SelectRangeGesture>() { // from class: android.view.inputmethod.SelectRangeGesture.1
        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public SelectRangeGesture createFromParcel(Parcel parcel) {
            return new SelectRangeGesture(parcel);
        }

        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public SelectRangeGesture[] newArray(int i) {
            return new SelectRangeGesture[i];
        }
    };
    private RectF mEndArea;
    private int mGranularity;
    private RectF mStartArea;

    @Override // android.os.Parcelable
    public int describeContents() {
        return 0;
    }

    private SelectRangeGesture(int i, RectF rectF, RectF rectF2, String str) {
        this.mType = 32;
        this.mStartArea = rectF;
        this.mEndArea = rectF2;
        this.mGranularity = i;
        this.mFallbackText = str;
    }

    private SelectRangeGesture(Parcel parcel) {
        this.mType = 32;
        this.mFallbackText = parcel.readString8();
        this.mGranularity = parcel.readInt();
        this.mStartArea = (RectF) parcel.readTypedObject(RectF.CREATOR);
        this.mEndArea = (RectF) parcel.readTypedObject(RectF.CREATOR);
    }

    public int getGranularity() {
        return this.mGranularity;
    }

    public RectF getSelectionStartArea() {
        return this.mStartArea;
    }

    public RectF getSelectionEndArea() {
        return this.mEndArea;
    }

    public static final class Builder {
        private RectF mEndArea;
        private String mFallbackText;
        private int mGranularity;
        private RectF mStartArea;

        public Builder setGranularity(int i) {
            this.mGranularity = i;
            return this;
        }

        public Builder setSelectionStartArea(RectF rectF) {
            this.mStartArea = rectF;
            return this;
        }

        public Builder setSelectionEndArea(RectF rectF) {
            this.mEndArea = rectF;
            return this;
        }

        public Builder setFallbackText(String str) {
            this.mFallbackText = str;
            return this;
        }

        public SelectRangeGesture build() {
            RectF rectF;
            RectF rectF2 = this.mStartArea;
            if (rectF2 == null || rectF2.isEmpty() || (rectF = this.mEndArea) == null || rectF.isEmpty()) {
                throw new IllegalArgumentException("Selection area must be set.");
            }
            if (this.mGranularity <= 0) {
                throw new IllegalArgumentException("Selection granularity must be set.");
            }
            return new SelectRangeGesture(this.mGranularity, this.mStartArea, this.mEndArea, this.mFallbackText);
        }
    }

    public int hashCode() {
        return Objects.hash(Integer.valueOf(this.mGranularity), this.mStartArea, this.mEndArea, this.mFallbackText);
    }

    public boolean equals(Object obj) {
        if (!(obj instanceof SelectRangeGesture)) {
            return false;
        }
        SelectRangeGesture selectRangeGesture = (SelectRangeGesture) obj;
        if (this.mGranularity == selectRangeGesture.mGranularity && Objects.equals(this.mFallbackText, selectRangeGesture.mFallbackText) && Objects.equals(this.mStartArea, selectRangeGesture.mStartArea)) {
            return Objects.equals(this.mEndArea, selectRangeGesture.mEndArea);
        }
        return false;
    }

    @Override // android.os.Parcelable
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeString8(this.mFallbackText);
        parcel.writeInt(this.mGranularity);
        parcel.writeTypedObject(this.mStartArea, i);
        parcel.writeTypedObject(this.mEndArea, i);
    }
}
