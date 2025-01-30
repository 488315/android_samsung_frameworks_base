package com.samsung.android.security.mdf.MdfService;

import android.p009os.Binder;
import android.p009os.IBinder;
import android.p009os.IInterface;
import android.p009os.Parcel;
import android.p009os.RemoteException;

/* loaded from: classes5.dex */
public interface IMdfService extends IInterface {
  public static final String DESCRIPTOR = "com.samsung.android.security.mdf.MdfService.IMdfService";

  int initCCMode() throws RemoteException;

  public static class Default implements IMdfService {
    @Override // com.samsung.android.security.mdf.MdfService.IMdfService
    public int initCCMode() throws RemoteException {
      return 0;
    }

    @Override // android.p009os.IInterface
    public IBinder asBinder() {
      return null;
    }
  }

  public abstract static class Stub extends Binder implements IMdfService {
    static final int TRANSACTION_initCCMode = 1;

    public Stub() {
      attachInterface(this, IMdfService.DESCRIPTOR);
    }

    public static IMdfService asInterface(IBinder obj) {
      if (obj == null) {
        return null;
      }
      IInterface iin = obj.queryLocalInterface(IMdfService.DESCRIPTOR);
      if (iin != null && (iin instanceof IMdfService)) {
        return (IMdfService) iin;
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
          return "initCCMode";
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
        data.enforceInterface(IMdfService.DESCRIPTOR);
      }
      switch (code) {
        case IBinder.INTERFACE_TRANSACTION /* 1598968902 */:
          reply.writeString(IMdfService.DESCRIPTOR);
          return true;
        default:
          switch (code) {
            case 1:
              int _result = initCCMode();
              reply.writeNoException();
              reply.writeInt(_result);
              return true;
            default:
              return super.onTransact(code, data, reply, flags);
          }
      }
    }

    private static class Proxy implements IMdfService {
      private IBinder mRemote;

      Proxy(IBinder remote) {
        this.mRemote = remote;
      }

      @Override // android.p009os.IInterface
      public IBinder asBinder() {
        return this.mRemote;
      }

      public String getInterfaceDescriptor() {
        return IMdfService.DESCRIPTOR;
      }

      @Override // com.samsung.android.security.mdf.MdfService.IMdfService
      public int initCCMode() throws RemoteException {
        Parcel _data = Parcel.obtain(asBinder());
        Parcel _reply = Parcel.obtain();
        try {
          _data.writeInterfaceToken(IMdfService.DESCRIPTOR);
          this.mRemote.transact(1, _data, _reply, 0);
          _reply.readException();
          int _result = _reply.readInt();
          return _result;
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
