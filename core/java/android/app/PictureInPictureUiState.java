package android.app;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.Objects;

/* loaded from: classes.dex */
public final class PictureInPictureUiState implements Parcelable {
    public static final Parcelable.Creator<PictureInPictureUiState> CREATOR =
            new Parcelable.Creator<PictureInPictureUiState>() { // from class:
                // android.app.PictureInPictureUiState.1
                /* JADX WARN: Can't rename method to resolve collision */
                @Override // android.os.Parcelable.Creator
                public PictureInPictureUiState createFromParcel(Parcel in) {
                    return new PictureInPictureUiState(in);
                }

                /* JADX WARN: Can't rename method to resolve collision */
                @Override // android.os.Parcelable.Creator
                public PictureInPictureUiState[] newArray(int size) {
                    return new PictureInPictureUiState[size];
                }
            };
    private final boolean mIsStashed;
    private final boolean mIsTransitioningToPip;

    PictureInPictureUiState(Parcel in) {
        this.mIsStashed = in.readBoolean();
        this.mIsTransitioningToPip = in.readBoolean();
    }

    public PictureInPictureUiState(boolean isStashed) {
        this(isStashed, false);
    }

    private PictureInPictureUiState(boolean isStashed, boolean isTransitioningToPip) {
        this.mIsStashed = isStashed;
        this.mIsTransitioningToPip = isTransitioningToPip;
    }

    public boolean isStashed() {
        return this.mIsStashed;
    }

    public boolean isTransitioningToPip() {
        return this.mIsTransitioningToPip;
    }

    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof PictureInPictureUiState)) {
            return false;
        }
        PictureInPictureUiState that = (PictureInPictureUiState) o;
        return this.mIsStashed == that.mIsStashed
                && this.mIsTransitioningToPip == that.mIsTransitioningToPip;
    }

    public int hashCode() {
        return Objects.hash(
                Boolean.valueOf(this.mIsStashed), Boolean.valueOf(this.mIsTransitioningToPip));
    }

    @Override // android.os.Parcelable
    public int describeContents() {
        return 0;
    }

    @Override // android.os.Parcelable
    public void writeToParcel(Parcel out, int flags) {
        out.writeBoolean(this.mIsStashed);
        out.writeBoolean(this.mIsTransitioningToPip);
    }

    public static final class Builder {
        private boolean mIsStashed;
        private boolean mIsTransitioningToPip;

        public Builder setStashed(boolean isStashed) {
            this.mIsStashed = isStashed;
            return this;
        }

        public Builder setTransitioningToPip(boolean isEnteringPip) {
            this.mIsTransitioningToPip = isEnteringPip;
            return this;
        }

        public PictureInPictureUiState build() {
            return new PictureInPictureUiState(this.mIsStashed, this.mIsTransitioningToPip);
        }
    }
}
