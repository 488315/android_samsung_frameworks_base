package com.android.server.profcollect;

import android.os.Binder;
import android.os.IBinder;
import android.os.IInterface;
import android.os.Parcel;

/* loaded from: classes3.dex */
public interface IProviderStatusCallback extends IInterface {

  public class Default implements IProviderStatusCallback {
    @Override // android.os.IInterface
    public IBinder asBinder() {
      return null;
    }
  }

  void onProviderReady();

  public abstract class Stub extends Binder implements IProviderStatusCallback {
    @Override // android.os.IInterface
    public IBinder asBinder() {
      return this;
    }

    public Stub() {
      attachInterface(this, "com.android.server.profcollect.IProviderStatusCallback");
    }

    public static IProviderStatusCallback asInterface(IBinder iBinder) {
      if (iBinder == null) {
        return null;
      }
      IInterface queryLocalInterface =
          iBinder.queryLocalInterface("com.android.server.profcollect.IProviderStatusCallback");
      if (queryLocalInterface != null && (queryLocalInterface instanceof IProviderStatusCallback)) {
        return (IProviderStatusCallback) queryLocalInterface;
      }
      return new Proxy(iBinder);
    }

    @Override // android.os.Binder
    public boolean onTransact(int i, Parcel parcel, Parcel parcel2, int i2) {
      if (i >= 1 && i <= 16777215) {
        parcel.enforceInterface("com.android.server.profcollect.IProviderStatusCallback");
      }
      if (i == 1598968902) {
        parcel2.writeString("com.android.server.profcollect.IProviderStatusCallback");
        return true;
      }
      if (i == 1) {
        onProviderReady();
        return true;
      }
      return super.onTransact(i, parcel, parcel2, i2);
    }

    public class Proxy implements IProviderStatusCallback {
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
