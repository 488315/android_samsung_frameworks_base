package android.app.smartspace;

import android.annotation.SystemApi;
import android.p009os.Parcel;
import android.p009os.Parcelable;
import android.p009os.UserHandle;
import android.text.format.DateFormat;
import java.util.Objects;

@SystemApi
/* loaded from: classes.dex */
public final class SmartspaceSessionId implements Parcelable {
  public static final Parcelable.Creator<SmartspaceSessionId> CREATOR =
      new Parcelable.Creator<
          SmartspaceSessionId>() { // from class: android.app.smartspace.SmartspaceSessionId.1
        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public SmartspaceSessionId createFromParcel(Parcel parcel) {
          return new SmartspaceSessionId(parcel);
        }

        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public SmartspaceSessionId[] newArray(int size) {
          return new SmartspaceSessionId[size];
        }
      };
  private final String mId;
  private final UserHandle mUserHandle;

  public SmartspaceSessionId(String id, UserHandle userHandle) {
    this.mId = id;
    this.mUserHandle = userHandle;
  }

  private SmartspaceSessionId(Parcel p) {
    this.mId = p.readString();
    this.mUserHandle = (UserHandle) p.readTypedObject(UserHandle.CREATOR);
  }

  public String getId() {
    return this.mId;
  }

  public UserHandle getUserHandle() {
    return this.mUserHandle;
  }

  public boolean equals(Object o) {
    if (!getClass().equals(o != null ? o.getClass() : null)) {
      return false;
    }
    SmartspaceSessionId other = (SmartspaceSessionId) o;
    return this.mId.equals(other.mId) && this.mUserHandle == other.mUserHandle;
  }

  public String toString() {
    return "SmartspaceSessionId{mId='"
        + this.mId
        + DateFormat.QUOTE
        + ", mUserId="
        + this.mUserHandle.getIdentifier()
        + '}';
  }

  public int hashCode() {
    return Objects.hash(this.mId, this.mUserHandle);
  }

  @Override // android.p009os.Parcelable
  public int describeContents() {
    return 0;
  }

  @Override // android.p009os.Parcelable
  public void writeToParcel(Parcel dest, int flags) {
    dest.writeString(this.mId);
    dest.writeTypedObject(this.mUserHandle, flags);
  }
}
