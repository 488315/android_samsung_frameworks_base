package android.view;

import android.annotation.NonNull;
import android.p009os.Parcel;
import android.p009os.Parcelable;
import android.p009os.RemoteException;
import com.android.internal.util.AnnotationValidations;

/* loaded from: classes4.dex */
public final class InputMonitor implements Parcelable {
  public static final Parcelable.Creator<InputMonitor> CREATOR =
      new Parcelable.Creator<InputMonitor>() { // from class: android.view.InputMonitor.1
        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public InputMonitor[] newArray(int size) {
          return new InputMonitor[size];
        }

        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public InputMonitor createFromParcel(Parcel in) {
          return new InputMonitor(in);
        }
      };
  private static final boolean DEBUG = false;
  private static final String TAG = "InputMonitor";
  private final IInputMonitorHost mHost;
  private final InputChannel mInputChannel;
  private final SurfaceControl mSurface;

  public void pilferPointers() {
    try {
      this.mHost.pilferPointers();
    } catch (RemoteException e) {
      e.rethrowFromSystemServer();
    }
  }

  public void dispose() {
    this.mInputChannel.dispose();
    this.mSurface.release();
    try {
      this.mHost.dispose();
    } catch (RemoteException e) {
      e.rethrowFromSystemServer();
    }
  }

  public InputMonitor(InputChannel inputChannel, IInputMonitorHost host, SurfaceControl surface) {
    this.mInputChannel = inputChannel;
    AnnotationValidations.validate(
        (Class<NonNull>) NonNull.class, (NonNull) null, (Object) inputChannel);
    this.mHost = host;
    AnnotationValidations.validate((Class<NonNull>) NonNull.class, (NonNull) null, (Object) host);
    this.mSurface = surface;
    AnnotationValidations.validate(
        (Class<NonNull>) NonNull.class, (NonNull) null, (Object) surface);
  }

  public InputChannel getInputChannel() {
    return this.mInputChannel;
  }

  public IInputMonitorHost getHost() {
    return this.mHost;
  }

  public SurfaceControl getSurface() {
    return this.mSurface;
  }

  public String toString() {
    return "InputMonitor { inputChannel = "
        + this.mInputChannel
        + ", host = "
        + this.mHost
        + ", surface = "
        + this.mSurface
        + " }";
  }

  @Override // android.p009os.Parcelable
  public void writeToParcel(Parcel dest, int flags) {
    dest.writeTypedObject(this.mInputChannel, flags);
    dest.writeStrongInterface(this.mHost);
    dest.writeTypedObject(this.mSurface, flags);
  }

  @Override // android.p009os.Parcelable
  public int describeContents() {
    return 0;
  }

  InputMonitor(Parcel in) {
    InputChannel inputChannel = (InputChannel) in.readTypedObject(InputChannel.CREATOR);
    IInputMonitorHost host = IInputMonitorHost.Stub.asInterface(in.readStrongBinder());
    SurfaceControl surface = (SurfaceControl) in.readTypedObject(SurfaceControl.CREATOR);
    this.mInputChannel = inputChannel;
    AnnotationValidations.validate(
        (Class<NonNull>) NonNull.class, (NonNull) null, (Object) inputChannel);
    this.mHost = host;
    AnnotationValidations.validate((Class<NonNull>) NonNull.class, (NonNull) null, (Object) host);
    this.mSurface = surface;
    AnnotationValidations.validate(
        (Class<NonNull>) NonNull.class, (NonNull) null, (Object) surface);
  }

  @Deprecated
  private void __metadata() {}
}
