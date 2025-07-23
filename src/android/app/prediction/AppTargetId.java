package android.app.prediction;

import android.annotation.SystemApi;
import android.os.Parcel;
import android.os.Parcelable;

@SystemApi
/* loaded from: classes.dex */
public final class AppTargetId implements Parcelable {
    public static final Parcelable.Creator<AppTargetId> CREATOR = new Parcelable.Creator<AppTargetId>() { // from class: android.app.prediction.AppTargetId.1
        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public AppTargetId createFromParcel(Parcel parcel) {
            return new AppTargetId(parcel);
        }

        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public AppTargetId[] newArray(int i) {
            return new AppTargetId[i];
        }
    };
    private final String mId;

    @Override // android.os.Parcelable
    public int describeContents() {
        return 0;
    }

    @SystemApi
    public AppTargetId(String str) {
        this.mId = str;
    }

    private AppTargetId(Parcel parcel) {
        this.mId = parcel.readString();
    }

    public String getId() {
        return this.mId;
    }

    public boolean equals(Object obj) {
        if (getClass().equals(obj != null ? obj.getClass() : null)) {
            return this.mId.equals(((AppTargetId) obj).mId);
        }
        return false;
    }

    public int hashCode() {
        return this.mId.hashCode();
    }

    @Override // android.os.Parcelable
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeString(this.mId);
    }
}
