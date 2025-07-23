package android.view.inputmethod;

import android.graphics.RectF;
import android.os.Parcel;
import android.os.Parcelable;
import java.util.Objects;

/* loaded from: classes4.dex */
public final class DeleteGesture extends PreviewableHandwritingGesture implements Parcelable {
    public static final Parcelable.Creator<DeleteGesture> CREATOR = new Parcelable.Creator<DeleteGesture>() { // from class: android.view.inputmethod.DeleteGesture.1
        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public DeleteGesture createFromParcel(Parcel parcel) {
            return new DeleteGesture(parcel);
        }

        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public DeleteGesture[] newArray(int i) {
            return new DeleteGesture[i];
        }
    };
    private RectF mArea;
    private int mGranularity;

    @Override // android.os.Parcelable
    public int describeContents() {
        return 0;
    }

    private DeleteGesture(int i, RectF rectF, String str) {
        this.mType = 4;
        this.mArea = rectF;
        this.mGranularity = i;
        this.mFallbackText = str;
    }

    private DeleteGesture(Parcel parcel) {
        this.mType = 4;
        this.mFallbackText = parcel.readString8();
        this.mGranularity = parcel.readInt();
        this.mArea = (RectF) parcel.readTypedObject(RectF.CREATOR);
    }

    public int getGranularity() {
        return this.mGranularity;
    }

    public RectF getDeletionArea() {
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

        public Builder setDeletionArea(RectF rectF) {
            this.mArea = rectF;
            return this;
        }

        public Builder setFallbackText(String str) {
            this.mFallbackText = str;
            return this;
        }

        public DeleteGesture build() {
            RectF rectF = this.mArea;
            if (rectF == null || rectF.isEmpty()) {
                throw new IllegalArgumentException("Deletion area must be set.");
            }
            if (this.mGranularity <= 0) {
                throw new IllegalArgumentException("Deletion granularity must be set.");
            }
            return new DeleteGesture(this.mGranularity, this.mArea, this.mFallbackText);
        }
    }

    public int hashCode() {
        return Objects.hash(this.mArea, Integer.valueOf(this.mGranularity), this.mFallbackText);
    }

    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (!(obj instanceof DeleteGesture)) {
            return false;
        }
        DeleteGesture deleteGesture = (DeleteGesture) obj;
        if (this.mGranularity == deleteGesture.mGranularity && Objects.equals(this.mFallbackText, deleteGesture.mFallbackText)) {
            return Objects.equals(this.mArea, deleteGesture.mArea);
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
