package android.window;

import android.annotation.NonNull;
import android.app.IApplicationThread;
import android.os.IBinder;
import android.os.Parcel;
import android.os.Parcelable;
import com.android.internal.util.AnnotationValidations;

/* loaded from: classes4.dex */
public class RemoteTransition implements Parcelable {
  public static final Parcelable.Creator<RemoteTransition> CREATOR =
      new Parcelable.Creator<RemoteTransition>() { // from class: android.window.RemoteTransition.1
        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public RemoteTransition[] newArray(int size) {
          return new RemoteTransition[size];
        }

        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public RemoteTransition createFromParcel(Parcel in) {
          return new RemoteTransition(in);
        }
      };
  private IApplicationThread mAppThread;
  private String mDebugName;
  private IRemoteTransition mRemoteTransition;

  public RemoteTransition(IRemoteTransition remoteTransition) {
    this(remoteTransition, null, null);
  }

  public RemoteTransition(IRemoteTransition remoteTransition, String debugName) {
    this(remoteTransition, null, debugName);
  }

  public IBinder asBinder() {
    return this.mRemoteTransition.asBinder();
  }

  public RemoteTransition(
      IRemoteTransition remoteTransition, IApplicationThread appThread, String debugName) {
    this.mRemoteTransition = remoteTransition;
    AnnotationValidations.validate(
        (Class<NonNull>) NonNull.class, (NonNull) null, (Object) remoteTransition);
    this.mAppThread = appThread;
    this.mDebugName = debugName;
  }

  public IRemoteTransition getRemoteTransition() {
    return this.mRemoteTransition;
  }

  public IApplicationThread getAppThread() {
    return this.mAppThread;
  }

  public String getDebugName() {
    return this.mDebugName;
  }

  public RemoteTransition setRemoteTransition(IRemoteTransition value) {
    this.mRemoteTransition = value;
    AnnotationValidations.validate((Class<NonNull>) NonNull.class, (NonNull) null, (Object) value);
    return this;
  }

  public RemoteTransition setAppThread(IApplicationThread value) {
    this.mAppThread = value;
    return this;
  }

  public RemoteTransition setDebugName(String value) {
    this.mDebugName = value;
    return this;
  }

  public String toString() {
    return "RemoteTransition { remoteTransition = "
        + this.mRemoteTransition
        + ", appThread = "
        + this.mAppThread
        + ", debugName = "
        + this.mDebugName
        + " }";
  }

  @Override // android.os.Parcelable
  public void writeToParcel(Parcel dest, int flags) {
    byte flg = this.mAppThread != null ? (byte) (0 | 2) : (byte) 0;
    if (this.mDebugName != null) {
      flg = (byte) (flg | 4);
    }
    dest.writeByte(flg);
    dest.writeStrongInterface(this.mRemoteTransition);
    IApplicationThread iApplicationThread = this.mAppThread;
    if (iApplicationThread != null) {
      dest.writeStrongInterface(iApplicationThread);
    }
    String str = this.mDebugName;
    if (str != null) {
      dest.writeString(str);
    }
  }

  @Override // android.os.Parcelable
  public int describeContents() {
    return 0;
  }

  protected RemoteTransition(Parcel in) {
    byte flg = in.readByte();
    IRemoteTransition remoteTransition = IRemoteTransition.Stub.asInterface(in.readStrongBinder());
    IApplicationThread appThread =
        (flg & 2) == 0 ? null : IApplicationThread.Stub.asInterface(in.readStrongBinder());
    String debugName = (flg & 4) == 0 ? null : in.readString();
    this.mRemoteTransition = remoteTransition;
    AnnotationValidations.validate(
        (Class<NonNull>) NonNull.class, (NonNull) null, (Object) remoteTransition);
    this.mAppThread = appThread;
    this.mDebugName = debugName;
  }

  @Deprecated
  private void __metadata() {}
}
