package android.service.credentials;

import android.app.slice.Slice;
import android.os.Parcel;
import android.os.Parcelable;
import java.util.Objects;

/* loaded from: classes3.dex */
public final class Action implements Parcelable {
  public static final Parcelable.Creator<Action> CREATOR =
      new Parcelable.Creator<Action>() { // from class: android.service.credentials.Action.1
        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public Action createFromParcel(Parcel in) {
          return new Action(in);
        }

        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public Action[] newArray(int size) {
          return new Action[size];
        }
      };
  private final Slice mSlice;

  public Action(Slice slice) {
    Objects.requireNonNull(slice, "slice must not be null");
    this.mSlice = slice;
  }

  private Action(Parcel in) {
    this.mSlice = (Slice) in.readTypedObject(Slice.CREATOR);
  }

  @Override // android.os.Parcelable
  public int describeContents() {
    return 0;
  }

  @Override // android.os.Parcelable
  public void writeToParcel(Parcel dest, int flags) {
    dest.writeTypedObject(this.mSlice, flags);
  }

  public Slice getSlice() {
    return this.mSlice;
  }
}
