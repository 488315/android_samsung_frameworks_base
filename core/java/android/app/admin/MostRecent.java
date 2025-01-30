package android.app.admin;

import android.p009os.Parcel;
import android.p009os.Parcelable;

/* loaded from: classes.dex */
public final class MostRecent<V> extends ResolutionMechanism<V> {
  public static final MostRecent<?> MOST_RECENT = new MostRecent<>();
  public static final Parcelable.Creator<MostRecent<?>> CREATOR =
      new Parcelable.Creator<MostRecent<?>>() { // from class: android.app.admin.MostRecent.1
        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public MostRecent<?> createFromParcel(Parcel source) {
          return new MostRecent<>();
        }

        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public MostRecent<?>[] newArray(int size) {
          return new MostRecent[size];
        }
      };

  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }
    return o != null && getClass() == o.getClass();
  }

  public int hashCode() {
    return 0;
  }

  public String toString() {
    return "MostRecent {}";
  }

  @Override // android.p009os.Parcelable
  public int describeContents() {
    return 0;
  }

  @Override // android.p009os.Parcelable
  public void writeToParcel(Parcel dest, int flags) {}
}
