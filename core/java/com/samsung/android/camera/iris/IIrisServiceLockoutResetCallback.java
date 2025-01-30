package com.samsung.android.camera.iris;

import android.p009os.Binder;
import android.p009os.IBinder;
import android.p009os.IInterface;
import android.p009os.Parcel;
import android.p009os.RemoteException;

/* loaded from: classes5.dex */
public interface IIrisServiceLockoutResetCallback extends IInterface {
  public static final String DESCRIPTOR =
      "com.samsung.android.camera.iris.IIrisServiceLockoutResetCallback";

  void onLockoutReset(long j) throws RemoteException;

  public static class Default implements IIrisServiceLockoutResetCallback {
    @Override // com.samsung.android.camera.iris.IIrisServiceLockoutResetCallback
    public void onLockoutReset(long deviceId) throws RemoteException {}

    @Override // android.p009os.IInterface
    public IBinder asBinder() {
      return null;
    }
  }

  public abstract static class Stub extends Binder implements IIrisServiceLockoutResetCallback {
    static final int TRANSACTION_onLockoutReset = 1;

    public Stub() {
      attachInterface(this, IIrisServiceLockoutResetCallback.DESCRIPTOR);
    }

    public static IIrisServiceLockoutResetCallback asInterface(IBinder obj) {
      if (obj == null) {
        return null;
      }
      IInterface iin = obj.queryLocalInterface(IIrisServiceLockoutResetCallback.DESCRIPTOR);
      if (iin != null && (iin instanceof IIrisServiceLockoutResetCallback)) {
        return (IIrisServiceLockoutResetCallback) iin;
      }
      return new Proxy(obj);
    }

    @Override // android.p009os.IInterface
    public IBinder asBinder() {
      return this;
    }

    public static String getDefaultTransactionName(int transactionCode) {
      switch (transactionCode) {
        case 1:
          return "onLockoutReset";
        default:
          return null;
      }
    }

    @Override // android.p009os.Binder
    public String getTransactionName(int transactionCode) {
      return getDefaultTransactionName(transactionCode);
    }

    @Override // android.p009os.Binder
    public boolean onTransact(int code, Parcel data, Parcel reply, int flags)
        throws RemoteException {
      if (code >= 1 && code <= 16777215) {
        data.enforceInterface(IIrisServiceLockoutResetCallback.DESCRIPTOR);
      }
      switch (code) {
        case IBinder.INTERFACE_TRANSACTION /* 1598968902 */:
          reply.writeString(IIrisServiceLockoutResetCallback.DESCRIPTOR);
          return true;
        default:
          switch (code) {
            case 1:
              long _arg0 = data.readLong();
              data.enforceNoDataAvail();
              onLockoutReset(_arg0);
              reply.writeNoException();
              return true;
            default:
              return super.onTransact(code, data, reply, flags);
          }
      }
    }

    private static class Proxy implements IIrisServiceLockoutResetCallback {
      private IBinder mRemote;

      Proxy(IBinder remote) {
        this.mRemote = remote;
      }

      @Override // android.p009os.IInterface
      public IBinder asBinder() {
        return this.mRemote;
      }

      public String getInterfaceDescriptor() {
        return IIrisServiceLockoutResetCallback.DESCRIPTOR;
      }

      @Override // com.samsung.android.camera.iris.IIrisServiceLockoutResetCallback
      public void onLockoutReset(long deviceId) throws RemoteException {
        Parcel _data = Parcel.obtain(asBinder());
        Parcel _reply = Parcel.obtain();
        try {
          _data.writeInterfaceToken(IIrisServiceLockoutResetCallback.DESCRIPTOR);
          _data.writeLong(deviceId);
          this.mRemote.transact(1, _data, _reply, 0);
          _reply.readException();
        } finally {
          _reply.recycle();
          _data.recycle();
        }
      }
    }

    @Override // android.p009os.Binder
    public int getMaxTransactionId() {
      return 0;
    }
  }
}
