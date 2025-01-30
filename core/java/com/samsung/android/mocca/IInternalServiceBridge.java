package com.samsung.android.mocca;

import android.os.Binder;
import android.os.Bundle;
import android.os.IBinder;
import android.os.IInterface;
import android.os.Parcel;
import android.os.RemoteException;

/* loaded from: classes5.dex */
public interface IInternalServiceBridge extends IInterface {
  public static final String DESCRIPTOR = "com.samsung.android.mocca.IInternalServiceBridge";

  void clearAllListeners() throws RemoteException;

  Bundle getValue(String str) throws RemoteException;

  boolean isAvailable(String str) throws RemoteException;

  void setListener(String str, IInternalServiceBridgeListener iInternalServiceBridgeListener)
      throws RemoteException;

  void start() throws RemoteException;

  void stop() throws RemoteException;

  public static class Default implements IInternalServiceBridge {
    @Override // com.samsung.android.mocca.IInternalServiceBridge
    public void start() throws RemoteException {}

    @Override // com.samsung.android.mocca.IInternalServiceBridge
    public void stop() throws RemoteException {}

    @Override // com.samsung.android.mocca.IInternalServiceBridge
    public boolean isAvailable(String type) throws RemoteException {
      return false;
    }

    @Override // com.samsung.android.mocca.IInternalServiceBridge
    public Bundle getValue(String type) throws RemoteException {
      return null;
    }

    @Override // com.samsung.android.mocca.IInternalServiceBridge
    public void setListener(String type, IInternalServiceBridgeListener listener)
        throws RemoteException {}

    @Override // com.samsung.android.mocca.IInternalServiceBridge
    public void clearAllListeners() throws RemoteException {}

    @Override // android.os.IInterface
    public IBinder asBinder() {
      return null;
    }
  }

  public abstract static class Stub extends Binder implements IInternalServiceBridge {
    static final int TRANSACTION_clearAllListeners = 6;
    static final int TRANSACTION_getValue = 4;
    static final int TRANSACTION_isAvailable = 3;
    static final int TRANSACTION_setListener = 5;
    static final int TRANSACTION_start = 1;
    static final int TRANSACTION_stop = 2;

    public Stub() {
      attachInterface(this, IInternalServiceBridge.DESCRIPTOR);
    }

    public static IInternalServiceBridge asInterface(IBinder obj) {
      if (obj == null) {
        return null;
      }
      IInterface iin = obj.queryLocalInterface(IInternalServiceBridge.DESCRIPTOR);
      if (iin != null && (iin instanceof IInternalServiceBridge)) {
        return (IInternalServiceBridge) iin;
      }
      return new Proxy(obj);
    }

    @Override // android.os.IInterface
    public IBinder asBinder() {
      return this;
    }

    public static String getDefaultTransactionName(int transactionCode) {
      switch (transactionCode) {
        case 1:
          return "start";
        case 2:
          return "stop";
        case 3:
          return "isAvailable";
        case 4:
          return "getValue";
        case 5:
          return "setListener";
        case 6:
          return "clearAllListeners";
        default:
          return null;
      }
    }

    @Override // android.os.Binder
    public String getTransactionName(int transactionCode) {
      return getDefaultTransactionName(transactionCode);
    }

    @Override // android.os.Binder
    public boolean onTransact(int code, Parcel data, Parcel reply, int flags)
        throws RemoteException {
      if (code >= 1 && code <= 16777215) {
        data.enforceInterface(IInternalServiceBridge.DESCRIPTOR);
      }
      switch (code) {
        case IBinder.INTERFACE_TRANSACTION /* 1598968902 */:
          reply.writeString(IInternalServiceBridge.DESCRIPTOR);
          return true;
        default:
          switch (code) {
            case 1:
              start();
              reply.writeNoException();
              return true;
            case 2:
              stop();
              reply.writeNoException();
              return true;
            case 3:
              String _arg0 = data.readString();
              data.enforceNoDataAvail();
              boolean _result = isAvailable(_arg0);
              reply.writeNoException();
              reply.writeBoolean(_result);
              return true;
            case 4:
              String _arg02 = data.readString();
              data.enforceNoDataAvail();
              Bundle _result2 = getValue(_arg02);
              reply.writeNoException();
              reply.writeTypedObject(_result2, 1);
              return true;
            case 5:
              String _arg03 = data.readString();
              IInternalServiceBridgeListener _arg1 =
                  IInternalServiceBridgeListener.Stub.asInterface(data.readStrongBinder());
              data.enforceNoDataAvail();
              setListener(_arg03, _arg1);
              reply.writeNoException();
              return true;
            case 6:
              clearAllListeners();
              reply.writeNoException();
              return true;
            default:
              return super.onTransact(code, data, reply, flags);
          }
      }
    }

    private static class Proxy implements IInternalServiceBridge {
      private IBinder mRemote;

      Proxy(IBinder remote) {
        this.mRemote = remote;
      }

      @Override // android.os.IInterface
      public IBinder asBinder() {
        return this.mRemote;
      }

      public String getInterfaceDescriptor() {
        return IInternalServiceBridge.DESCRIPTOR;
      }

      @Override // com.samsung.android.mocca.IInternalServiceBridge
      public void start() throws RemoteException {
        Parcel _data = Parcel.obtain(asBinder());
        Parcel _reply = Parcel.obtain();
        try {
          _data.writeInterfaceToken(IInternalServiceBridge.DESCRIPTOR);
          this.mRemote.transact(1, _data, _reply, 0);
          _reply.readException();
        } finally {
          _reply.recycle();
          _data.recycle();
        }
      }

      @Override // com.samsung.android.mocca.IInternalServiceBridge
      public void stop() throws RemoteException {
        Parcel _data = Parcel.obtain(asBinder());
        Parcel _reply = Parcel.obtain();
        try {
          _data.writeInterfaceToken(IInternalServiceBridge.DESCRIPTOR);
          this.mRemote.transact(2, _data, _reply, 0);
          _reply.readException();
        } finally {
          _reply.recycle();
          _data.recycle();
        }
      }

      @Override // com.samsung.android.mocca.IInternalServiceBridge
      public boolean isAvailable(String type) throws RemoteException {
        Parcel _data = Parcel.obtain(asBinder());
        Parcel _reply = Parcel.obtain();
        try {
          _data.writeInterfaceToken(IInternalServiceBridge.DESCRIPTOR);
          _data.writeString(type);
          this.mRemote.transact(3, _data, _reply, 0);
          _reply.readException();
          boolean _result = _reply.readBoolean();
          return _result;
        } finally {
          _reply.recycle();
          _data.recycle();
        }
      }

      @Override // com.samsung.android.mocca.IInternalServiceBridge
      public Bundle getValue(String type) throws RemoteException {
        Parcel _data = Parcel.obtain(asBinder());
        Parcel _reply = Parcel.obtain();
        try {
          _data.writeInterfaceToken(IInternalServiceBridge.DESCRIPTOR);
          _data.writeString(type);
          this.mRemote.transact(4, _data, _reply, 0);
          _reply.readException();
          Bundle _result = (Bundle) _reply.readTypedObject(Bundle.CREATOR);
          return _result;
        } finally {
          _reply.recycle();
          _data.recycle();
        }
      }

      @Override // com.samsung.android.mocca.IInternalServiceBridge
      public void setListener(String type, IInternalServiceBridgeListener listener)
          throws RemoteException {
        Parcel _data = Parcel.obtain(asBinder());
        Parcel _reply = Parcel.obtain();
        try {
          _data.writeInterfaceToken(IInternalServiceBridge.DESCRIPTOR);
          _data.writeString(type);
          _data.writeStrongInterface(listener);
          this.mRemote.transact(5, _data, _reply, 0);
          _reply.readException();
        } finally {
          _reply.recycle();
          _data.recycle();
        }
      }

      @Override // com.samsung.android.mocca.IInternalServiceBridge
      public void clearAllListeners() throws RemoteException {
        Parcel _data = Parcel.obtain(asBinder());
        Parcel _reply = Parcel.obtain();
        try {
          _data.writeInterfaceToken(IInternalServiceBridge.DESCRIPTOR);
          this.mRemote.transact(6, _data, _reply, 0);
          _reply.readException();
        } finally {
          _reply.recycle();
          _data.recycle();
        }
      }
    }

    @Override // android.os.Binder
    public int getMaxTransactionId() {
      return 5;
    }
  }
}
