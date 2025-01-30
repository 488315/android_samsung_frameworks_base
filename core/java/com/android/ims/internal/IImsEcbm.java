package com.android.ims.internal;

import android.p009os.Binder;
import android.p009os.IBinder;
import android.p009os.IInterface;
import android.p009os.Parcel;
import android.p009os.RemoteException;

/* loaded from: classes4.dex */
public interface IImsEcbm extends IInterface {
  void exitEmergencyCallbackMode() throws RemoteException;

  void setListener(IImsEcbmListener iImsEcbmListener) throws RemoteException;

  public static class Default implements IImsEcbm {
    @Override // com.android.ims.internal.IImsEcbm
    public void setListener(IImsEcbmListener listener) throws RemoteException {}

    @Override // com.android.ims.internal.IImsEcbm
    public void exitEmergencyCallbackMode() throws RemoteException {}

    @Override // android.p009os.IInterface
    public IBinder asBinder() {
      return null;
    }
  }

  public abstract static class Stub extends Binder implements IImsEcbm {
    public static final String DESCRIPTOR = "com.android.ims.internal.IImsEcbm";
    static final int TRANSACTION_exitEmergencyCallbackMode = 2;
    static final int TRANSACTION_setListener = 1;

    public Stub() {
      attachInterface(this, DESCRIPTOR);
    }

    public static IImsEcbm asInterface(IBinder obj) {
      if (obj == null) {
        return null;
      }
      IInterface iin = obj.queryLocalInterface(DESCRIPTOR);
      if (iin != null && (iin instanceof IImsEcbm)) {
        return (IImsEcbm) iin;
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
          return "setListener";
        case 2:
          return "exitEmergencyCallbackMode";
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
        data.enforceInterface(DESCRIPTOR);
      }
      switch (code) {
        case IBinder.INTERFACE_TRANSACTION /* 1598968902 */:
          reply.writeString(DESCRIPTOR);
          return true;
        default:
          switch (code) {
            case 1:
              IImsEcbmListener _arg0 = IImsEcbmListener.Stub.asInterface(data.readStrongBinder());
              data.enforceNoDataAvail();
              setListener(_arg0);
              reply.writeNoException();
              return true;
            case 2:
              exitEmergencyCallbackMode();
              reply.writeNoException();
              return true;
            default:
              return super.onTransact(code, data, reply, flags);
          }
      }
    }

    private static class Proxy implements IImsEcbm {
      private IBinder mRemote;

      Proxy(IBinder remote) {
        this.mRemote = remote;
      }

      @Override // android.p009os.IInterface
      public IBinder asBinder() {
        return this.mRemote;
      }

      public String getInterfaceDescriptor() {
        return Stub.DESCRIPTOR;
      }

      @Override // com.android.ims.internal.IImsEcbm
      public void setListener(IImsEcbmListener listener) throws RemoteException {
        Parcel _data = Parcel.obtain(asBinder());
        Parcel _reply = Parcel.obtain();
        try {
          _data.writeInterfaceToken(Stub.DESCRIPTOR);
          _data.writeStrongInterface(listener);
          this.mRemote.transact(1, _data, _reply, 0);
          _reply.readException();
        } finally {
          _reply.recycle();
          _data.recycle();
        }
      }

      @Override // com.android.ims.internal.IImsEcbm
      public void exitEmergencyCallbackMode() throws RemoteException {
        Parcel _data = Parcel.obtain(asBinder());
        Parcel _reply = Parcel.obtain();
        try {
          _data.writeInterfaceToken(Stub.DESCRIPTOR);
          this.mRemote.transact(2, _data, _reply, 0);
          _reply.readException();
        } finally {
          _reply.recycle();
          _data.recycle();
        }
      }
    }

    @Override // android.p009os.Binder
    public int getMaxTransactionId() {
      return 1;
    }
  }
}
