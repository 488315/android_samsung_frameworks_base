package com.android.internal.os;

import android.os.Parcel;
import android.os.ParcelFileDescriptor;
import android.os.Parcelable;
import com.android.internal.util.Preconditions;

/* loaded from: classes5.dex */
public class AppFuseMount implements Parcelable {
  public static final Parcelable.Creator<AppFuseMount> CREATOR =
      new Parcelable.Creator<AppFuseMount>() { // from class: com.android.internal.os.AppFuseMount.1
        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public AppFuseMount createFromParcel(Parcel in) {
          return new AppFuseMount(
              in.readInt(),
              (ParcelFileDescriptor) in.readParcelable(null, ParcelFileDescriptor.class));
        }

        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public AppFuseMount[] newArray(int size) {
          return new AppFuseMount[size];
        }
      };

  /* renamed from: fd */
  public final ParcelFileDescriptor f996fd;
  public final int mountPointId;

  public AppFuseMount(int mountPointId, ParcelFileDescriptor fd) {
    Preconditions.checkNotNull(fd);
    this.mountPointId = mountPointId;
    this.f996fd = fd;
  }

  @Override // android.os.Parcelable
  public int describeContents() {
    return 0;
  }

  @Override // android.os.Parcelable
  public void writeToParcel(Parcel dest, int flags) {
    dest.writeInt(this.mountPointId);
    dest.writeParcelable(this.f996fd, flags);
  }
}
