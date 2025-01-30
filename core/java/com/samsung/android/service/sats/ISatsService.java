package com.samsung.android.service.sats;

import android.p009os.Binder;
import android.p009os.IBinder;
import android.p009os.IInterface;
import android.p009os.Parcel;
import android.p009os.RemoteException;

/* loaded from: classes5.dex */
public interface ISatsService extends IInterface {
  public static final String DESCRIPTOR = "com.samsung.android.service.sats.ISatsService";

  String executePseudoDrkAtCommnd(String str) throws RemoteException;

  public static class Default implements ISatsService {
    @Override // com.samsung.android.service.sats.ISatsService
    public String executePseudoDrkAtCommnd(String cmd) throws RemoteException {
      return null;
    }

    @Override // android.p009os.IInterface
    public IBinder asBinder() {
      return null;
    }
  }

  public abstract static class Stub extends Binder implements ISatsService {
    static final int TRANSACTION_executePseudoDrkAtCommnd = 1;

    public Stub() {
      attachInterface(this, ISatsService.DESCRIPTOR);
    }

    public static ISatsService asInterface(IBinder obj) {
      if (obj == null) {
        return null;
      }
      IInterface iin = obj.queryLocalInterface(ISatsService.DESCRIPTOR);
      if (iin != null && (iin instanceof ISatsService)) {
        return (ISatsService) iin;
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
          return "executePseudoDrkAtCommnd";
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
        data.enforceInterface(ISatsService.DESCRIPTOR);
      }
      switch (code) {
        case IBinder.INTERFACE_TRANSACTION /* 1598968902 */:
          reply.writeString(ISatsService.DESCRIPTOR);
          return true;
        default:
          switch (code) {
            case 1:
              String _arg0 = data.readString();
              data.enforceNoDataAvail();
              String _result = executePseudoDrkAtCommnd(_arg0);
              reply.writeNoException();
              reply.writeString(_result);
              return true;
            default:
              return super.onTransact(code, data, reply, flags);
          }
      }
    }

    private static class Proxy implements ISatsService {
      private IBinder mRemote;

      Proxy(IBinder remote) {
        this.mRemote = remote;
      }

      @Override // android.p009os.IInterface
      public IBinder asBinder() {
        return this.mRemote;
      }

      public String getInterfaceDescriptor() {
        return ISatsService.DESCRIPTOR;
      }

      @Override // com.samsung.android.service.sats.ISatsService
      public String executePseudoDrkAtCommnd(String cmd) throws RemoteException {
        Parcel _data = Parcel.obtain(asBinder());
        Parcel _reply = Parcel.obtain();
        try {
          _data.writeInterfaceToken(ISatsService.DESCRIPTOR);
          _data.writeString(cmd);
          this.mRemote.transact(1, _data, _reply, 0);
          _reply.readException();
          String _result = _reply.readString();
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
