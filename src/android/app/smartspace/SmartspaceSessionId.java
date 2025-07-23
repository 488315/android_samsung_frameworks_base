package android.app.smartspace;

import android.annotation.SystemApi;
import android.os.Parcel;
import android.os.Parcelable;
import android.os.UserHandle;
import java.util.Objects;

@SystemApi
/* loaded from: classes.dex */
public final class SmartspaceSessionId implements Parcelable {
    public static final Parcelable.Creator<SmartspaceSessionId> CREATOR = new Parcelable.Creator<SmartspaceSessionId>() { // from class: android.app.smartspace.SmartspaceSessionId.1
        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public SmartspaceSessionId createFromParcel(Parcel parcel) {
            return new SmartspaceSessionId(parcel);
        }

        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public SmartspaceSessionId[] newArray(int i) {
            return new SmartspaceSessionId[i];
        }
    };
    private final String mId;
    private final UserHandle mUserHandle;

    @Override // android.os.Parcelable
    public int describeContents() {
        return 0;
    }

    public SmartspaceSessionId(String str, UserHandle userHandle) {
        this.mId = str;
        this.mUserHandle = userHandle;
    }

    private SmartspaceSessionId(Parcel parcel) {
        this.mId = parcel.readString();
        this.mUserHandle = (UserHandle) parcel.readTypedObject(UserHandle.CREATOR);
    }

    public String getId() {
        return this.mId;
    }

    public UserHandle getUserHandle() {
        return this.mUserHandle;
    }

    public boolean equals(Object obj) {
        if (!getClass().equals(obj != null ? obj.getClass() : null)) {
            return false;
        }
        SmartspaceSessionId smartspaceSessionId = (SmartspaceSessionId) obj;
        return this.mId.equals(smartspaceSessionId.mId) && this.mUserHandle == smartspaceSessionId.mUserHandle;
    }

    public String toString() {
        return "SmartspaceSessionId{mId='" + this.mId + "', mUserId=" + this.mUserHandle.getIdentifier() + '}';
    }

    public int hashCode() {
        return Objects.hash(this.mId, this.mUserHandle);
    }

    @Override // android.os.Parcelable
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeString(this.mId);
        parcel.writeTypedObject(this.mUserHandle, i);
    }
}
