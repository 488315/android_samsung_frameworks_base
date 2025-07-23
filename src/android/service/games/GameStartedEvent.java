package android.service.games;

import android.annotation.SystemApi;
import android.os.Parcel;
import android.os.Parcelable;
import java.util.Objects;

@SystemApi
/* loaded from: classes3.dex */
public final class GameStartedEvent implements Parcelable {
    public static final Parcelable.Creator<GameStartedEvent> CREATOR = new Parcelable.Creator<GameStartedEvent>() { // from class: android.service.games.GameStartedEvent.1
        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public GameStartedEvent createFromParcel(Parcel parcel) {
            return new GameStartedEvent(parcel.readInt(), parcel.readString());
        }

        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public GameStartedEvent[] newArray(int i) {
            return new GameStartedEvent[0];
        }
    };
    private final String mPackageName;
    private final int mTaskId;

    @Override // android.os.Parcelable
    public int describeContents() {
        return 0;
    }

    public GameStartedEvent(int i, String str) {
        this.mTaskId = i;
        this.mPackageName = str;
    }

    @Override // android.os.Parcelable
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeInt(this.mTaskId);
        parcel.writeString(this.mPackageName);
    }

    public int getTaskId() {
        return this.mTaskId;
    }

    public String getPackageName() {
        return this.mPackageName;
    }

    public String toString() {
        return "GameStartedEvent{mTaskId=" + this.mTaskId + ", mPackageName='" + this.mPackageName + "'}";
    }

    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (!(obj instanceof GameStartedEvent)) {
            return false;
        }
        GameStartedEvent gameStartedEvent = (GameStartedEvent) obj;
        return this.mTaskId == gameStartedEvent.mTaskId && Objects.equals(this.mPackageName, gameStartedEvent.mPackageName);
    }

    public int hashCode() {
        return Objects.hash(Integer.valueOf(this.mTaskId), this.mPackageName);
    }
}
