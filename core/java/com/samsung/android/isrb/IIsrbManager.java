package com.samsung.android.isrb;

import android.p009os.Binder;
import android.p009os.IBinder;
import android.p009os.IInterface;
import android.p009os.Parcel;
import android.p009os.RemoteException;

/* loaded from: classes5.dex */
public interface IIsrbManager extends IInterface {
  public static final String DESCRIPTOR = "com.samsung.android.isrb.IIsrbManager";

  boolean isBootCompleteState() throws RemoteException;

  void setFakeTime() throws RemoteException;

  void setIsrbEnable(boolean z) throws RemoteException;

  public static class Default implements IIsrbManager {
    @Override // com.samsung.android.isrb.IIsrbManager
    public boolean isBootCompleteState() throws RemoteException {
      return false;
    }

    @Override // com.samsung.android.isrb.IIsrbManager
    public void setIsrbEnable(boolean state) throws RemoteException {}

    @Override // com.samsung.android.isrb.IIsrbManager
    public void setFakeTime() throws RemoteException {}

    @Override // android.p009os.IInterface
    public IBinder asBinder() {
      return null;
    }
  }

  public abstract static class Stub extends Binder implements IIsrbManager {
    static final int TRANSACTION_isBootCompleteState = 1;
    static final int TRANSACTION_setFakeTime = 3;
    static final int TRANSACTION_setIsrbEnable = 2;

    public Stub() {
      attachInterface(this, IIsrbManager.DESCRIPTOR);
    }

    public static IIsrbManager asInterface(IBinder obj) {
      if (obj == null) {
        return null;
      }
      IInterface iin = obj.queryLocalInterface(IIsrbManager.DESCRIPTOR);
      if (iin != null && (iin instanceof IIsrbManager)) {
        return (IIsrbManager) iin;
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
          return "isBootCompleteState";
        case 2:
          return "setIsrbEnable";
        case 3:
          return "setFakeTime";
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
        data.enforceInterface(IIsrbManager.DESCRIPTOR);
      }
      switch (code) {
        case IBinder.INTERFACE_TRANSACTION /* 1598968902 */:
          reply.writeString(IIsrbManager.DESCRIPTOR);
          return true;
        default:
          switch (code) {
            case 1:
              boolean _result = isBootCompleteState();
              reply.writeNoException();
              reply.writeBoolean(_result);
              return true;
            case 2:
              boolean _arg0 = data.readBoolean();
              data.enforceNoDataAvail();
              setIsrbEnable(_arg0);
              reply.writeNoException();
              return true;
            case 3:
              setFakeTime();
              reply.writeNoException();
              return true;
            default:
              return super.onTransact(code, data, reply, flags);
          }
      }
    }

    private static class Proxy implements IIsrbManager {
      private IBinder mRemote;

      Proxy(IBinder remote) {
        this.mRemote = remote;
      }

      @Override // android.p009os.IInterface
      public IBinder asBinder() {
        return this.mRemote;
      }

      public String getInterfaceDescriptor() {
        return IIsrbManager.DESCRIPTOR;
      }

      @Override // com.samsung.android.isrb.IIsrbManager
      public boolean isBootCompleteState() throws RemoteException {
        Parcel _data = Parcel.obtain(asBinder());
        Parcel _reply = Parcel.obtain();
        try {
          _data.writeInterfaceToken(IIsrbManager.DESCRIPTOR);
          this.mRemote.transact(1, _data, _reply, 0);
          _reply.readException();
          boolean _result = _reply.readBoolean();
          return _result;
        } finally {
          _reply.recycle();
          _data.recycle();
        }
      }

      @Override // com.samsung.android.isrb.IIsrbManager
      public void setIsrbEnable(boolean state) throws RemoteException {
        Parcel _data = Parcel.obtain(asBinder());
        Parcel _reply = Parcel.obtain();
        try {
          _data.writeInterfaceToken(IIsrbManager.DESCRIPTOR);
          _data.writeBoolean(state);
          this.mRemote.transact(2, _data, _reply, 0);
          _reply.readException();
        } finally {
          _reply.recycle();
          _data.recycle();
        }
      }

      @Override // com.samsung.android.isrb.IIsrbManager
      public void setFakeTime() throws RemoteException {
        Parcel _data = Parcel.obtain(asBinder());
        Parcel _reply = Parcel.obtain();
        try {
          _data.writeInterfaceToken(IIsrbManager.DESCRIPTOR);
          this.mRemote.transact(3, _data, _reply, 0);
          _reply.readException();
        } finally {
          _reply.recycle();
          _data.recycle();
        }
      }
    }

    @Override // android.p009os.Binder
    public int getMaxTransactionId() {
      return 2;
    }
  }
}
