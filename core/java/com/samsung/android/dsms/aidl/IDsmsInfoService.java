package com.samsung.android.dsms.aidl;

import android.p009os.Binder;
import android.p009os.IBinder;
import android.p009os.IInterface;
import android.p009os.Parcel;
import android.p009os.RemoteException;

/* loaded from: classes5.dex */
public interface IDsmsInfoService extends IInterface {
  public static final String DESCRIPTOR = "com.samsung.android.dsms.aidl.IDsmsInfoService";

  boolean isCommercializedDevice() throws RemoteException;

  public static class Default implements IDsmsInfoService {
    @Override // com.samsung.android.dsms.aidl.IDsmsInfoService
    public boolean isCommercializedDevice() throws RemoteException {
      return false;
    }

    @Override // android.p009os.IInterface
    public IBinder asBinder() {
      return null;
    }
  }

  public abstract static class Stub extends Binder implements IDsmsInfoService {
    static final int TRANSACTION_isCommercializedDevice = 1;

    public Stub() {
      attachInterface(this, IDsmsInfoService.DESCRIPTOR);
    }

    public static IDsmsInfoService asInterface(IBinder obj) {
      if (obj == null) {
        return null;
      }
      IInterface iin = obj.queryLocalInterface(IDsmsInfoService.DESCRIPTOR);
      if (iin != null && (iin instanceof IDsmsInfoService)) {
        return (IDsmsInfoService) iin;
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
          return "isCommercializedDevice";
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
        data.enforceInterface(IDsmsInfoService.DESCRIPTOR);
      }
      switch (code) {
        case IBinder.INTERFACE_TRANSACTION /* 1598968902 */:
          reply.writeString(IDsmsInfoService.DESCRIPTOR);
          return true;
        default:
          switch (code) {
            case 1:
              boolean _result = isCommercializedDevice();
              reply.writeNoException();
              reply.writeBoolean(_result);
              return true;
            default:
              return super.onTransact(code, data, reply, flags);
          }
      }
    }

    private static class Proxy implements IDsmsInfoService {
      private IBinder mRemote;

      Proxy(IBinder remote) {
        this.mRemote = remote;
      }

      @Override // android.p009os.IInterface
      public IBinder asBinder() {
        return this.mRemote;
      }

      public String getInterfaceDescriptor() {
        return IDsmsInfoService.DESCRIPTOR;
      }

      @Override // com.samsung.android.dsms.aidl.IDsmsInfoService
      public boolean isCommercializedDevice() throws RemoteException {
        Parcel _data = Parcel.obtain(asBinder());
        Parcel _reply = Parcel.obtain();
        try {
          _data.writeInterfaceToken(IDsmsInfoService.DESCRIPTOR);
          this.mRemote.transact(1, _data, _reply, 0);
          _reply.readException();
          boolean _result = _reply.readBoolean();
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
