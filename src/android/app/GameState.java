package android.app;

import android.os.Parcel;
import android.os.Parcelable;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

/* loaded from: classes.dex */
public final class GameState implements Parcelable {
    public static final Parcelable.Creator<GameState> CREATOR = new Parcelable.Creator<GameState>() { // from class: android.app.GameState.1
        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public GameState createFromParcel(Parcel parcel) {
            return new GameState(parcel);
        }

        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public GameState[] newArray(int i) {
            return new GameState[i];
        }
    };
    public static final int MODE_CONTENT = 4;
    public static final int MODE_GAMEPLAY_INTERRUPTIBLE = 2;
    public static final int MODE_GAMEPLAY_UNINTERRUPTIBLE = 3;
    public static final int MODE_NONE = 1;
    public static final int MODE_UNKNOWN = 0;
    private final boolean mIsLoading;
    private final int mLabel;
    private final int mMode;
    private final int mQuality;

    @Retention(RetentionPolicy.SOURCE)
    @interface GameStateMode {
    }

    @Override // android.os.Parcelable
    public int describeContents() {
        return 0;
    }

    public GameState(boolean z, int i) {
        this(z, i, -1, -1);
    }

    public GameState(boolean z, int i, int i2, int i3) {
        this.mIsLoading = z;
        this.mMode = i;
        this.mLabel = i2;
        this.mQuality = i3;
    }

    private GameState(Parcel parcel) {
        this.mIsLoading = parcel.readBoolean();
        this.mMode = parcel.readInt();
        this.mLabel = parcel.readInt();
        this.mQuality = parcel.readInt();
    }

    public boolean isLoading() {
        return this.mIsLoading;
    }

    public int getMode() {
        return this.mMode;
    }

    public int getLabel() {
        return this.mLabel;
    }

    public int getQuality() {
        return this.mQuality;
    }

    @Override // android.os.Parcelable
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeBoolean(this.mIsLoading);
        parcel.writeInt(this.mMode);
        parcel.writeInt(this.mLabel);
        parcel.writeInt(this.mQuality);
    }
}
