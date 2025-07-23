package android.service.games;

import android.os.Parcel;
import android.os.Parcelable;
import java.util.Objects;

/* loaded from: classes3.dex */
public final class GameSessionViewHostConfiguration implements Parcelable {
    public static final Parcelable.Creator<GameSessionViewHostConfiguration> CREATOR = new Parcelable.Creator<GameSessionViewHostConfiguration>() { // from class: android.service.games.GameSessionViewHostConfiguration.1
        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public GameSessionViewHostConfiguration createFromParcel(Parcel parcel) {
            return new GameSessionViewHostConfiguration(parcel.readInt(), parcel.readInt(), parcel.readInt());
        }

        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public GameSessionViewHostConfiguration[] newArray(int i) {
            return new GameSessionViewHostConfiguration[0];
        }
    };
    final int mDisplayId;
    final int mHeightPx;
    final int mWidthPx;

    @Override // android.os.Parcelable
    public int describeContents() {
        return 0;
    }

    public GameSessionViewHostConfiguration(int i, int i2, int i3) {
        this.mDisplayId = i;
        this.mWidthPx = i2;
        this.mHeightPx = i3;
    }

    @Override // android.os.Parcelable
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeInt(this.mDisplayId);
        parcel.writeInt(this.mWidthPx);
        parcel.writeInt(this.mHeightPx);
    }

    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (!(obj instanceof GameSessionViewHostConfiguration)) {
            return false;
        }
        GameSessionViewHostConfiguration gameSessionViewHostConfiguration = (GameSessionViewHostConfiguration) obj;
        return this.mDisplayId == gameSessionViewHostConfiguration.mDisplayId && this.mWidthPx == gameSessionViewHostConfiguration.mWidthPx && this.mHeightPx == gameSessionViewHostConfiguration.mHeightPx;
    }

    public int hashCode() {
        return Objects.hash(Integer.valueOf(this.mDisplayId), Integer.valueOf(this.mWidthPx), Integer.valueOf(this.mHeightPx));
    }

    public String toString() {
        return "GameSessionViewHostConfiguration{mDisplayId=" + this.mDisplayId + ", mWidthPx=" + this.mWidthPx + ", mHeightPx=" + this.mHeightPx + '}';
    }
}
