package android.service.games;

import android.os.Parcel;
import android.os.Parcelable;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.util.Objects;

/* loaded from: classes3.dex */
public final class GameScreenshotResult implements Parcelable {
    public static final Parcelable.Creator<GameScreenshotResult> CREATOR = new Parcelable.Creator<GameScreenshotResult>() { // from class: android.service.games.GameScreenshotResult.1
        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public GameScreenshotResult createFromParcel(Parcel parcel) {
            return new GameScreenshotResult(parcel.readInt());
        }

        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public GameScreenshotResult[] newArray(int i) {
            return new GameScreenshotResult[0];
        }
    };
    public static final int GAME_SCREENSHOT_ERROR_INTERNAL_ERROR = 1;
    public static final int GAME_SCREENSHOT_SUCCESS = 0;
    private final int mStatus;

    @Retention(RetentionPolicy.SOURCE)
    public @interface GameScreenshotStatus {
    }

    @Override // android.os.Parcelable
    public int describeContents() {
        return 0;
    }

    public static GameScreenshotResult createSuccessResult() {
        return new GameScreenshotResult(0);
    }

    public static GameScreenshotResult createInternalErrorResult() {
        return new GameScreenshotResult(1);
    }

    private GameScreenshotResult(int i) {
        this.mStatus = i;
    }

    @Override // android.os.Parcelable
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeInt(this.mStatus);
    }

    public int getStatus() {
        return this.mStatus;
    }

    public String toString() {
        return "GameScreenshotResult{mStatus=" + this.mStatus + "}";
    }

    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        return (obj instanceof GameScreenshotResult) && this.mStatus == ((GameScreenshotResult) obj).mStatus;
    }

    public int hashCode() {
        return Objects.hash(Integer.valueOf(this.mStatus));
    }
}
