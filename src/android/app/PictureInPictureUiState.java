package android.app;

import android.os.Parcel;
import android.os.Parcelable;
import java.util.Objects;

/* loaded from: classes.dex */
public final class PictureInPictureUiState implements Parcelable {
    public static final Parcelable.Creator<PictureInPictureUiState> CREATOR = new Parcelable.Creator<PictureInPictureUiState>() { // from class: android.app.PictureInPictureUiState.1
        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public PictureInPictureUiState createFromParcel(Parcel parcel) {
            return new PictureInPictureUiState(parcel);
        }

        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public PictureInPictureUiState[] newArray(int i) {
            return new PictureInPictureUiState[i];
        }
    };
    private final boolean mIsStashed;
    private final boolean mIsTransitioningToPip;

    @Override // android.os.Parcelable
    public int describeContents() {
        return 0;
    }

    PictureInPictureUiState(Parcel parcel) {
        this.mIsStashed = parcel.readBoolean();
        this.mIsTransitioningToPip = parcel.readBoolean();
    }

    public PictureInPictureUiState(boolean z) {
        this(z, false);
    }

    private PictureInPictureUiState(boolean z, boolean z2) {
        this.mIsStashed = z;
        this.mIsTransitioningToPip = z2;
    }

    public boolean isStashed() {
        return this.mIsStashed;
    }

    public boolean isTransitioningToPip() {
        return this.mIsTransitioningToPip;
    }

    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (!(obj instanceof PictureInPictureUiState)) {
            return false;
        }
        PictureInPictureUiState pictureInPictureUiState = (PictureInPictureUiState) obj;
        return this.mIsStashed == pictureInPictureUiState.mIsStashed && this.mIsTransitioningToPip == pictureInPictureUiState.mIsTransitioningToPip;
    }

    public int hashCode() {
        return Objects.hash(Boolean.valueOf(this.mIsStashed), Boolean.valueOf(this.mIsTransitioningToPip));
    }

    @Override // android.os.Parcelable
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeBoolean(this.mIsStashed);
        parcel.writeBoolean(this.mIsTransitioningToPip);
    }

    public static final class Builder {
        private boolean mIsStashed;
        private boolean mIsTransitioningToPip;

        public Builder setStashed(boolean z) {
            this.mIsStashed = z;
            return this;
        }

        public Builder setTransitioningToPip(boolean z) {
            this.mIsTransitioningToPip = z;
            return this;
        }

        public PictureInPictureUiState build() {
            return new PictureInPictureUiState(this.mIsStashed, this.mIsTransitioningToPip);
        }
    }
}
