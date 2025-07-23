package android.view.inputmethod;

import android.graphics.RectF;
import android.os.Parcel;
import android.os.Parcelable;
import java.util.Objects;

/* loaded from: classes4.dex */
public final class DeleteRangeGesture extends PreviewableHandwritingGesture implements Parcelable {
    public static final Parcelable.Creator<DeleteRangeGesture> CREATOR = new Parcelable.Creator<DeleteRangeGesture>() { // from class: android.view.inputmethod.DeleteRangeGesture.1
        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public DeleteRangeGesture createFromParcel(Parcel parcel) {
            return new DeleteRangeGesture(parcel);
        }

        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public DeleteRangeGesture[] newArray(int i) {
            return new DeleteRangeGesture[i];
        }
    };
    private RectF mEndArea;
    private int mGranularity;
    private RectF mStartArea;

    @Override // android.os.Parcelable
    public int describeContents() {
        return 0;
    }

    private DeleteRangeGesture(int i, RectF rectF, RectF rectF2, String str) {
        this.mType = 64;
        this.mStartArea = rectF;
        this.mEndArea = rectF2;
        this.mGranularity = i;
        this.mFallbackText = str;
    }

    private DeleteRangeGesture(Parcel parcel) {
        this.mType = 64;
        this.mFallbackText = parcel.readString8();
        this.mGranularity = parcel.readInt();
        this.mStartArea = (RectF) parcel.readTypedObject(RectF.CREATOR);
        this.mEndArea = (RectF) parcel.readTypedObject(RectF.CREATOR);
    }

    public int getGranularity() {
        return this.mGranularity;
    }

    public RectF getDeletionStartArea() {
        return this.mStartArea;
    }

    public RectF getDeletionEndArea() {
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

        public Builder setDeletionStartArea(RectF rectF) {
            this.mStartArea = rectF;
            return this;
        }

        public Builder setDeletionEndArea(RectF rectF) {
            this.mEndArea = rectF;
            return this;
        }

        public Builder setFallbackText(String str) {
            this.mFallbackText = str;
            return this;
        }

        public DeleteRangeGesture build() {
            RectF rectF;
            RectF rectF2 = this.mStartArea;
            if (rectF2 == null || rectF2.isEmpty() || (rectF = this.mEndArea) == null || rectF.isEmpty()) {
                throw new IllegalArgumentException("Deletion area must be set.");
            }
            if (this.mGranularity <= 0) {
                throw new IllegalArgumentException("Deletion granularity must be set.");
            }
            return new DeleteRangeGesture(this.mGranularity, this.mStartArea, this.mEndArea, this.mFallbackText);
        }
    }

    public int hashCode() {
        return Objects.hash(Integer.valueOf(this.mGranularity), this.mStartArea, this.mEndArea, this.mFallbackText);
    }

    public boolean equals(Object obj) {
        if (!(obj instanceof DeleteRangeGesture)) {
            return false;
        }
        DeleteRangeGesture deleteRangeGesture = (DeleteRangeGesture) obj;
        if (this.mGranularity == deleteRangeGesture.mGranularity && Objects.equals(this.mFallbackText, deleteRangeGesture.mFallbackText) && Objects.equals(this.mStartArea, deleteRangeGesture.mStartArea)) {
            return Objects.equals(this.mEndArea, deleteRangeGesture.mEndArea);
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
