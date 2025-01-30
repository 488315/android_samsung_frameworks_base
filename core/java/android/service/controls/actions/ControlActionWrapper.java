package android.service.controls.actions;

import android.p009os.Parcel;
import android.p009os.Parcelable;
import com.android.internal.util.Preconditions;

/* loaded from: classes3.dex */
public final class ControlActionWrapper implements Parcelable {
  public static final Parcelable.Creator<ControlActionWrapper> CREATOR =
      new Parcelable.Creator<
          ControlActionWrapper>() { // from class:
                                    // android.service.controls.actions.ControlActionWrapper.1
        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public ControlActionWrapper createFromParcel(Parcel in) {
          return new ControlActionWrapper(ControlAction.createActionFromBundle(in.readBundle()));
        }

        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public ControlActionWrapper[] newArray(int size) {
          return new ControlActionWrapper[size];
        }
      };
  private final ControlAction mControlAction;

  public ControlActionWrapper(ControlAction controlAction) {
    Preconditions.checkNotNull(controlAction);
    this.mControlAction = controlAction;
  }

  @Override // android.p009os.Parcelable
  public void writeToParcel(Parcel dest, int flags) {
    dest.writeBundle(this.mControlAction.getDataBundle());
  }

  public ControlAction getWrappedAction() {
    return this.mControlAction;
  }

  @Override // android.p009os.Parcelable
  public int describeContents() {
    return 0;
  }
}
