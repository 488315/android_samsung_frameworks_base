package android.service.games;

import android.annotation.SystemApi;
import android.os.Parcel;
import android.os.Parcelable;
import java.util.Objects;

@SystemApi
/* loaded from: classes3.dex */
public final class CreateGameSessionRequest implements Parcelable {
    public static final Parcelable.Creator<CreateGameSessionRequest> CREATOR = new Parcelable.Creator<CreateGameSessionRequest>() { // from class: android.service.games.CreateGameSessionRequest.1
        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public CreateGameSessionRequest createFromParcel(Parcel parcel) {
            return new CreateGameSessionRequest(parcel.readInt(), parcel.readString8());
        }

        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public CreateGameSessionRequest[] newArray(int i) {
            return new CreateGameSessionRequest[0];
        }
    };
    private final String mGamePackageName;
    private final int mTaskId;

    @Override // android.os.Parcelable
    public int describeContents() {
        return 0;
    }

    public CreateGameSessionRequest(int i, String str) {
        this.mTaskId = i;
        this.mGamePackageName = str;
    }

    @Override // android.os.Parcelable
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeInt(this.mTaskId);
        parcel.writeString8(this.mGamePackageName);
    }

    public int getTaskId() {
        return this.mTaskId;
    }

    public String getGamePackageName() {
        return this.mGamePackageName;
    }

    public String toString() {
        return "GameSessionRequest{mTaskId=" + this.mTaskId + ", mGamePackageName='" + this.mGamePackageName + "'}";
    }

    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (!(obj instanceof CreateGameSessionRequest)) {
            return false;
        }
        CreateGameSessionRequest createGameSessionRequest = (CreateGameSessionRequest) obj;
        return this.mTaskId == createGameSessionRequest.mTaskId && Objects.equals(this.mGamePackageName, createGameSessionRequest.mGamePackageName);
    }

    public int hashCode() {
        return Objects.hash(Integer.valueOf(this.mTaskId), this.mGamePackageName);
    }
}
