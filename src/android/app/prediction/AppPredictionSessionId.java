package android.app.prediction;

import android.annotation.SystemApi;
import android.os.Parcel;
import android.os.Parcelable;
import java.util.Objects;

@SystemApi
/* loaded from: classes.dex */
public final class AppPredictionSessionId implements Parcelable {
    public static final Parcelable.Creator<AppPredictionSessionId> CREATOR = new Parcelable.Creator<AppPredictionSessionId>() { // from class: android.app.prediction.AppPredictionSessionId.1
        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public AppPredictionSessionId createFromParcel(Parcel parcel) {
            return new AppPredictionSessionId(parcel);
        }

        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public AppPredictionSessionId[] newArray(int i) {
            return new AppPredictionSessionId[i];
        }
    };
    private final String mId;
    private final int mUserId;

    @Override // android.os.Parcelable
    public int describeContents() {
        return 0;
    }

    public AppPredictionSessionId(String str, int i) {
        this.mId = str;
        this.mUserId = i;
    }

    private AppPredictionSessionId(Parcel parcel) {
        this.mId = parcel.readString();
        this.mUserId = parcel.readInt();
    }

    public int getUserId() {
        return this.mUserId;
    }

    public boolean equals(Object obj) {
        if (!getClass().equals(obj != null ? obj.getClass() : null)) {
            return false;
        }
        AppPredictionSessionId appPredictionSessionId = (AppPredictionSessionId) obj;
        return this.mId.equals(appPredictionSessionId.mId) && this.mUserId == appPredictionSessionId.mUserId;
    }

    public String toString() {
        return this.mId + "," + this.mUserId;
    }

    public int hashCode() {
        return Objects.hash(this.mId, Integer.valueOf(this.mUserId));
    }

    @Override // android.os.Parcelable
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeString(this.mId);
        parcel.writeInt(this.mUserId);
    }
}
