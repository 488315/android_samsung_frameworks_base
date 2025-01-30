package android.gsi;

import android.os.Binder;
import android.os.IBinder;
import android.os.IInterface;
import android.os.Parcel;

/* loaded from: classes.dex */
public interface IProgressCallback extends IInterface {

  public class Default implements IProgressCallback {
    @Override // android.os.IInterface
    public IBinder asBinder() {
      return null;
    }
  }

  void onProgress(long j, long j2);

  public abstract class Stub extends Binder implements IProgressCallback {
    @Override // android.os.IInterface
    public IBinder asBinder() {
      return this;
    }

    public Stub() {
      attachInterface(this, "android.gsi.IProgressCallback");
    }

    public static IProgressCallback asInterface(IBinder iBinder) {
      if (iBinder == null) {
        return null;
      }
      IInterface queryLocalInterface = iBinder.queryLocalInterface("android.gsi.IProgressCallback");
      if (queryLocalInterface != null && (queryLocalInterface instanceof IProgressCallback)) {
        return (IProgressCallback) queryLocalInterface;
      }
      return new Proxy(iBinder);
    }

    @Override // android.os.Binder
    public boolean onTransact(int i, Parcel parcel, Parcel parcel2, int i2) {
      if (i >= 1 && i <= 16777215) {
        parcel.enforceInterface("android.gsi.IProgressCallback");
      }
      if (i == 1598968902) {
        parcel2.writeString("android.gsi.IProgressCallback");
        return true;
      }
      if (i == 1) {
        long readLong = parcel.readLong();
        long readLong2 = parcel.readLong();
        parcel.enforceNoDataAvail();
        onProgress(readLong, readLong2);
        parcel2.writeNoException();
        return true;
      }
      return super.onTransact(i, parcel, parcel2, i2);
    }

    public class Proxy implements IProgressCallback {
      public IBinder mRemote;

      public Proxy(IBinder iBinder) {
        this.mRemote = iBinder;
      }

      @Override // android.os.IInterface
      public IBinder asBinder() {
        return this.mRemote;
      }
    }
  }
}
