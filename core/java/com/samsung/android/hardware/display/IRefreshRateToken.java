package com.samsung.android.hardware.display;

import android.p009os.Binder;
import android.p009os.IBinder;
import android.p009os.IInterface;
import android.p009os.Parcel;
import android.p009os.RemoteException;

/* loaded from: classes5.dex */
public interface IRefreshRateToken extends IInterface {
  public static final String DESCRIPTOR = "com.samsung.android.hardware.display.IRefreshRateToken";

  void release() throws RemoteException;

  public static class Default implements IRefreshRateToken {
    @Override // com.samsung.android.hardware.display.IRefreshRateToken
    public void release() throws RemoteException {}

    @Override // android.p009os.IInterface
    public IBinder asBinder() {
      return null;
    }
  }

  public abstract static class Stub extends Binder implements IRefreshRateToken {
    static final int TRANSACTION_release = 1;

    public Stub() {
      attachInterface(this, IRefreshRateToken.DESCRIPTOR);
    }

    public static IRefreshRateToken asInterface(IBinder obj) {
      if (obj == null) {
        return null;
      }
      IInterface iin = obj.queryLocalInterface(IRefreshRateToken.DESCRIPTOR);
      if (iin != null && (iin instanceof IRefreshRateToken)) {
        return (IRefreshRateToken) iin;
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
          return "release";
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
        data.enforceInterface(IRefreshRateToken.DESCRIPTOR);
      }
      switch (code) {
        case IBinder.INTERFACE_TRANSACTION /* 1598968902 */:
          reply.writeString(IRefreshRateToken.DESCRIPTOR);
          return true;
        default:
          switch (code) {
            case 1:
              release();
              reply.writeNoException();
              return true;
            default:
              return super.onTransact(code, data, reply, flags);
          }
      }
    }

    private static class Proxy implements IRefreshRateToken {
      private IBinder mRemote;

      Proxy(IBinder remote) {
        this.mRemote = remote;
      }

      @Override // android.p009os.IInterface
      public IBinder asBinder() {
        return this.mRemote;
      }

      public String getInterfaceDescriptor() {
        return IRefreshRateToken.DESCRIPTOR;
      }

      @Override // com.samsung.android.hardware.display.IRefreshRateToken
      public void release() throws RemoteException {
        Parcel _data = Parcel.obtain(asBinder());
        Parcel _reply = Parcel.obtain();
        try {
          _data.writeInterfaceToken(IRefreshRateToken.DESCRIPTOR);
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
