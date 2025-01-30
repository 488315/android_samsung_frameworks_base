package android.app;

import android.p009os.Parcel;
import android.p009os.Parcelable;
import java.util.Objects;

/* loaded from: classes.dex */
public final class PictureInPictureUiState implements Parcelable {
    public static final Parcelable.Creator<PictureInPictureUiState> CREATOR = new Parcelable.Creator<PictureInPictureUiState>() { // from class: android.app.PictureInPictureUiState.1
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
    private boolean mIsStashed;

    PictureInPictureUiState(Parcel in) {
        this.mIsStashed = in.readBoolean();
    }

    public PictureInPictureUiState(boolean isStashed) {
        this.mIsStashed = isStashed;
    }

    public boolean isStashed() {
        return this.mIsStashed;
    }

    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof PictureInPictureUiState)) {
            return false;
        }
        PictureInPictureUiState that = (PictureInPictureUiState) o;
        return Objects.equals(Boolean.valueOf(this.mIsStashed), Boolean.valueOf(that.mIsStashed));
    }

    public int hashCode() {
        return Objects.hash(Boolean.valueOf(this.mIsStashed));
    }

    @Override // android.p009os.Parcelable
    public int describeContents() {
        return 0;
    }

    @Override // android.p009os.Parcelable
    public void writeToParcel(Parcel out, int flags) {
        out.writeBoolean(this.mIsStashed);
    }
}
