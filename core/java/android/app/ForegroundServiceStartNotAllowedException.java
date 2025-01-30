package android.app;

import android.os.Parcel;
import android.os.Parcelable;

/* loaded from: classes.dex */
public final class ForegroundServiceStartNotAllowedException extends ServiceStartNotAllowedException
    implements Parcelable {
  public static final Parcelable.Creator<ForegroundServiceStartNotAllowedException> CREATOR =
      new Parcelable.Creator<
          ForegroundServiceStartNotAllowedException>() { // from class:
                                                         // android.app.ForegroundServiceStartNotAllowedException.1
        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public ForegroundServiceStartNotAllowedException createFromParcel(Parcel source) {
          return new ForegroundServiceStartNotAllowedException(source);
        }

        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public ForegroundServiceStartNotAllowedException[] newArray(int size) {
          return new ForegroundServiceStartNotAllowedException[size];
        }
      };

  public ForegroundServiceStartNotAllowedException(String message) {
    super(message);
  }

  ForegroundServiceStartNotAllowedException(Parcel source) {
    super(source.readString());
  }

  @Override // android.os.Parcelable
  public int describeContents() {
    return 0;
  }

  @Override // android.os.Parcelable
  public void writeToParcel(Parcel dest, int flags) {
    dest.writeString(getMessage());
  }
}
