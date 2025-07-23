package android.view.inputmethod;

import android.graphics.RectF;
import android.os.Parcel;
import android.os.Parcelable;
import java.util.Objects;

/* loaded from: classes4.dex */
public final class SelectGesture extends PreviewableHandwritingGesture implements Parcelable {
    public static final Parcelable.Creator<SelectGesture> CREATOR = new Parcelable.Creator<SelectGesture>() { // from class: android.view.inputmethod.SelectGesture.1
        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public SelectGesture createFromParcel(Parcel parcel) {
            return new SelectGesture(parcel);
        }

        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public SelectGesture[] newArray(int i) {
            return new SelectGesture[i];
        }
    };
    private RectF mArea;
    private int mGranularity;

    @Override // android.os.Parcelable
    public int describeContents() {
        return 0;
    }

    private SelectGesture(int i, RectF rectF, String str) {
        this.mType = 1;
        this.mArea = rectF;
        this.mGranularity = i;
        this.mFallbackText = str;
    }

    private SelectGesture(Parcel parcel) {
        this.mType = 1;
        this.mFallbackText = parcel.readString8();
        this.mGranularity = parcel.readInt();
        this.mArea = (RectF) parcel.readTypedObject(RectF.CREATOR);
    }

    public int getGranularity() {
        return this.mGranularity;
    }

    public RectF getSelectionArea() {
        return this.mArea;
    }

    public static final class Builder {
        private RectF mArea;
        private String mFallbackText;
        private int mGranularity;

        public Builder setGranularity(int i) {
            this.mGranularity = i;
            return this;
        }

        public Builder setSelectionArea(RectF rectF) {
            this.mArea = rectF;
            return this;
        }

        public Builder setFallbackText(String str) {
            this.mFallbackText = str;
            return this;
        }

        public SelectGesture build() {
            RectF rectF = this.mArea;
            if (rectF == null || rectF.isEmpty()) {
                throw new IllegalArgumentException("Selection area must be set.");
            }
            if (this.mGranularity <= 0) {
                throw new IllegalArgumentException("Selection granularity must be set.");
            }
            return new SelectGesture(this.mGranularity, this.mArea, this.mFallbackText);
        }
    }

    public int hashCode() {
        return Objects.hash(Integer.valueOf(this.mGranularity), this.mArea, this.mFallbackText);
    }

    public boolean equals(Object obj) {
        if (!(obj instanceof SelectGesture)) {
            return false;
        }
        SelectGesture selectGesture = (SelectGesture) obj;
        if (this.mGranularity == selectGesture.mGranularity && Objects.equals(this.mFallbackText, selectGesture.mFallbackText)) {
            return Objects.equals(this.mArea, selectGesture.mArea);
        }
        return false;
    }

    @Override // android.os.Parcelable
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeString8(this.mFallbackText);
        parcel.writeInt(this.mGranularity);
        parcel.writeTypedObject(this.mArea, i);
    }
}
