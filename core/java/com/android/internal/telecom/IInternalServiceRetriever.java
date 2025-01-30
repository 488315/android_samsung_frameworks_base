package com.android.internal.telecom;

import android.p009os.Binder;
import android.p009os.IBinder;
import android.p009os.IInterface;
import android.p009os.Parcel;
import android.p009os.RemoteException;

/* loaded from: classes5.dex */
public interface IInternalServiceRetriever extends IInterface {
  public static final String DESCRIPTOR = "com.android.internal.telecom.IInternalServiceRetriever";

  IDeviceIdleControllerAdapter getDeviceIdleController() throws RemoteException;

  public static class Default implements IInternalServiceRetriever {
    @Override // com.android.internal.telecom.IInternalServiceRetriever
    public IDeviceIdleControllerAdapter getDeviceIdleController() throws RemoteException {
      return null;
    }

    @Override // android.p009os.IInterface
    public IBinder asBinder() {
      return null;
    }
  }

  public abstract static class Stub extends Binder implements IInternalServiceRetriever {
    static final int TRANSACTION_getDeviceIdleController = 1;

    public Stub() {
      attachInterface(this, IInternalServiceRetriever.DESCRIPTOR);
    }

    public static IInternalServiceRetriever asInterface(IBinder obj) {
      if (obj == null) {
        return null;
      }
      IInterface iin = obj.queryLocalInterface(IInternalServiceRetriever.DESCRIPTOR);
      if (iin != null && (iin instanceof IInternalServiceRetriever)) {
        return (IInternalServiceRetriever) iin;
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
          return "getDeviceIdleController";
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
        data.enforceInterface(IInternalServiceRetriever.DESCRIPTOR);
      }
      switch (code) {
        case IBinder.INTERFACE_TRANSACTION /* 1598968902 */:
          reply.writeString(IInternalServiceRetriever.DESCRIPTOR);
          return true;
        default:
          switch (code) {
            case 1:
              IDeviceIdleControllerAdapter _result = getDeviceIdleController();
              reply.writeNoException();
              reply.writeStrongInterface(_result);
              return true;
            default:
              return super.onTransact(code, data, reply, flags);
          }
      }
    }

    private static class Proxy implements IInternalServiceRetriever {
      private IBinder mRemote;

      Proxy(IBinder remote) {
        this.mRemote = remote;
      }

      @Override // android.p009os.IInterface
      public IBinder asBinder() {
        return this.mRemote;
      }

      public String getInterfaceDescriptor() {
        return IInternalServiceRetriever.DESCRIPTOR;
      }

      @Override // com.android.internal.telecom.IInternalServiceRetriever
      public IDeviceIdleControllerAdapter getDeviceIdleController() throws RemoteException {
        Parcel _data = Parcel.obtain(asBinder());
        Parcel _reply = Parcel.obtain();
        try {
          _data.writeInterfaceToken(IInternalServiceRetriever.DESCRIPTOR);
          this.mRemote.transact(1, _data, _reply, 0);
          _reply.readException();
          IDeviceIdleControllerAdapter _result =
              IDeviceIdleControllerAdapter.Stub.asInterface(_reply.readStrongBinder());
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
