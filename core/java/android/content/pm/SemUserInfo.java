package android.content.pm;

import android.os.Parcel;
import android.os.Parcelable;
import android.os.UserHandle;

/* loaded from: classes.dex */
public class SemUserInfo implements Parcelable {
  public static final Parcelable.Creator<SemUserInfo> CREATOR =
      new Parcelable.Creator<SemUserInfo>() { // from class: android.content.pm.SemUserInfo.1
        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public SemUserInfo createFromParcel(Parcel source) {
          return new SemUserInfo(source);
        }

        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public SemUserInfo[] newArray(int size) {
          return new SemUserInfo[size];
        }
      };
  public static final int FLAG_BMODE = 65536;
  public int flags;

  /* renamed from: id */
  public int f53id;
  public String name;

  public SemUserInfo(UserInfo ui) {
    if (ui != null) {
      this.f53id = ui.f54id;
      this.name = ui.name;
      this.flags = ui.flags;
      return;
    }
    throw new IllegalArgumentException("UserInfo is null");
  }

  public UserHandle getUserHandle() {
    return new UserHandle(this.f53id);
  }

  public boolean isSecondNumberMode() {
    return (this.flags & 65536) != 0;
  }

  @Override // android.os.Parcelable
  public void writeToParcel(Parcel dest, int parcelableFlags) {
    dest.writeInt(this.f53id);
    dest.writeString(this.name);
    dest.writeInt(this.flags);
  }

  public String toString() {
    return "SemUserInfo{" + this.f53id + ":" + Integer.toHexString(this.flags) + "}";
  }

  @Override // android.os.Parcelable
  public int describeContents() {
    return 0;
  }

  private SemUserInfo(Parcel source) {
    this.f53id = source.readInt();
    this.name = source.readString();
    this.flags = source.readInt();
  }
}
