package android.view.inputmethod;

import android.graphics.RectF;
import android.os.Parcel;
import android.os.Parcelable;

import java.util.Objects;

public final class DeleteRangeGesture extends PreviewableHandwritingGesture implements Parcelable {
    public static final Parcelable.Creator<DeleteRangeGesture> CREATOR =
            new Parcelable.Creator<
                    DeleteRangeGesture>() { // from class:
                                            // android.view.inputmethod.DeleteRangeGesture.1
                @Override // android.os.Parcelable.Creator
                public DeleteRangeGesture createFromParcel(Parcel source) {
                    return new DeleteRangeGesture(source);
                }

                @Override // android.os.Parcelable.Creator
                public DeleteRangeGesture[] newArray(int size) {
                    return new DeleteRangeGesture[size];
                }
            };
    private RectF mEndArea;
    private int mGranularity;
    private RectF mStartArea;

    private DeleteRangeGesture(
            int granularity, RectF startArea, RectF endArea, String fallbackText) {
        this.mType = 64;
        this.mStartArea = startArea;
        this.mEndArea = endArea;
        this.mGranularity = granularity;
        this.mFallbackText = fallbackText;
    }

    private DeleteRangeGesture(Parcel source) {
        this.mType = 64;
        this.mFallbackText = source.readString8();
        this.mGranularity = source.readInt();
        this.mStartArea = (RectF) source.readTypedObject(RectF.CREATOR);
        this.mEndArea = (RectF) source.readTypedObject(RectF.CREATOR);
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

        public Builder setGranularity(int granularity) {
            this.mGranularity = granularity;
            return this;
        }

        public Builder setDeletionStartArea(RectF startArea) {
            this.mStartArea = startArea;
            return this;
        }

        public Builder setDeletionEndArea(RectF endArea) {
            this.mEndArea = endArea;
            return this;
        }

        public Builder setFallbackText(String fallbackText) {
            this.mFallbackText = fallbackText;
            return this;
        }

        public DeleteRangeGesture build() {
            if (this.mStartArea == null
                    || this.mStartArea.isEmpty()
                    || this.mEndArea == null
                    || this.mEndArea.isEmpty()) {
                throw new IllegalArgumentException("Deletion area must be set.");
            }
            if (this.mGranularity <= 0) {
                throw new IllegalArgumentException("Deletion granularity must be set.");
            }
            return new DeleteRangeGesture(
                    this.mGranularity, this.mStartArea, this.mEndArea, this.mFallbackText);
        }
    }

    public int hashCode() {
        return Objects.hash(
                Integer.valueOf(this.mGranularity),
                this.mStartArea,
                this.mEndArea,
                this.mFallbackText);
    }

    public boolean equals(Object o) {
        if (!(o instanceof DeleteRangeGesture)) {
            return false;
        }
        DeleteRangeGesture that = (DeleteRangeGesture) o;
        if (this.mGranularity == that.mGranularity
                && Objects.equals(this.mFallbackText, that.mFallbackText)
                && Objects.equals(this.mStartArea, that.mStartArea)) {
            return Objects.equals(this.mEndArea, that.mEndArea);
        }
        return false;
    }

    @Override // android.os.Parcelable
    public int describeContents() {
        return 0;
    }

    @Override // android.os.Parcelable
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString8(this.mFallbackText);
        dest.writeInt(this.mGranularity);
        dest.writeTypedObject(this.mStartArea, flags);
        dest.writeTypedObject(this.mEndArea, flags);
    }
}
