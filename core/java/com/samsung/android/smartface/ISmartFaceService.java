package com.samsung.android.smartface;

import android.p009os.Binder;
import android.p009os.IBinder;
import android.p009os.IInterface;
import android.p009os.Parcel;
import android.p009os.RemoteException;

/* loaded from: classes5.dex */
public interface ISmartFaceService extends IInterface {
  public static final String DESCRIPTOR = "com.samsung.android.smartface.ISmartFaceService";

  int getSupportedServices() throws RemoteException;

  boolean register(ISmartFaceClient iSmartFaceClient, int i) throws RemoteException;

  void registerAsync(ISmartFaceClient iSmartFaceClient, int i) throws RemoteException;

  void setValue(ISmartFaceClient iSmartFaceClient, String str, String str2) throws RemoteException;

  void unregister(ISmartFaceClient iSmartFaceClient) throws RemoteException;

  void unregisterAsync(ISmartFaceClient iSmartFaceClient) throws RemoteException;

  public static class Default implements ISmartFaceService {
    @Override // com.samsung.android.smartface.ISmartFaceService
    public boolean register(ISmartFaceClient client, int serviceType) throws RemoteException {
      return false;
    }

    @Override // com.samsung.android.smartface.ISmartFaceService
    public void unregister(ISmartFaceClient client) throws RemoteException {}

    @Override // com.samsung.android.smartface.ISmartFaceService
    public void registerAsync(ISmartFaceClient client, int serviceType) throws RemoteException {}

    @Override // com.samsung.android.smartface.ISmartFaceService
    public void unregisterAsync(ISmartFaceClient client) throws RemoteException {}

    @Override // com.samsung.android.smartface.ISmartFaceService
    public void setValue(ISmartFaceClient client, String key, String value)
        throws RemoteException {}

    @Override // com.samsung.android.smartface.ISmartFaceService
    public int getSupportedServices() throws RemoteException {
      return 0;
    }

    @Override // android.p009os.IInterface
    public IBinder asBinder() {
      return null;
    }
  }

  public abstract static class Stub extends Binder implements ISmartFaceService {
    static final int TRANSACTION_getSupportedServices = 6;
    static final int TRANSACTION_register = 1;
    static final int TRANSACTION_registerAsync = 3;
    static final int TRANSACTION_setValue = 5;
    static final int TRANSACTION_unregister = 2;
    static final int TRANSACTION_unregisterAsync = 4;

    public Stub() {
      attachInterface(this, ISmartFaceService.DESCRIPTOR);
    }

    public static ISmartFaceService asInterface(IBinder obj) {
      if (obj == null) {
        return null;
      }
      IInterface iin = obj.queryLocalInterface(ISmartFaceService.DESCRIPTOR);
      if (iin != null && (iin instanceof ISmartFaceService)) {
        return (ISmartFaceService) iin;
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
          return "register";
        case 2:
          return "unregister";
        case 3:
          return "registerAsync";
        case 4:
          return "unregisterAsync";
        case 5:
          return "setValue";
        case 6:
          return "getSupportedServices";
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
        data.enforceInterface(ISmartFaceService.DESCRIPTOR);
      }
      switch (code) {
        case IBinder.INTERFACE_TRANSACTION /* 1598968902 */:
          reply.writeString(ISmartFaceService.DESCRIPTOR);
          return true;
        default:
          switch (code) {
            case 1:
              ISmartFaceClient _arg0 = ISmartFaceClient.Stub.asInterface(data.readStrongBinder());
              int _arg1 = data.readInt();
              data.enforceNoDataAvail();
              boolean _result = register(_arg0, _arg1);
              reply.writeNoException();
              reply.writeBoolean(_result);
              return true;
            case 2:
              ISmartFaceClient _arg02 = ISmartFaceClient.Stub.asInterface(data.readStrongBinder());
              data.enforceNoDataAvail();
              unregister(_arg02);
              reply.writeNoException();
              return true;
            case 3:
              ISmartFaceClient _arg03 = ISmartFaceClient.Stub.asInterface(data.readStrongBinder());
              int _arg12 = data.readInt();
              data.enforceNoDataAvail();
              registerAsync(_arg03, _arg12);
              return true;
            case 4:
              ISmartFaceClient _arg04 = ISmartFaceClient.Stub.asInterface(data.readStrongBinder());
              data.enforceNoDataAvail();
              unregisterAsync(_arg04);
              return true;
            case 5:
              ISmartFaceClient _arg05 = ISmartFaceClient.Stub.asInterface(data.readStrongBinder());
              String _arg13 = data.readString();
              String _arg2 = data.readString();
              data.enforceNoDataAvail();
              setValue(_arg05, _arg13, _arg2);
              reply.writeNoException();
              return true;
            case 6:
              int _result2 = getSupportedServices();
              reply.writeNoException();
              reply.writeInt(_result2);
              return true;
            default:
              return super.onTransact(code, data, reply, flags);
          }
      }
    }

    private static class Proxy implements ISmartFaceService {
      private IBinder mRemote;

      Proxy(IBinder remote) {
        this.mRemote = remote;
      }

      @Override // android.p009os.IInterface
      public IBinder asBinder() {
        return this.mRemote;
      }

      public String getInterfaceDescriptor() {
        return ISmartFaceService.DESCRIPTOR;
      }

      @Override // com.samsung.android.smartface.ISmartFaceService
      public boolean register(ISmartFaceClient client, int serviceType) throws RemoteException {
        Parcel _data = Parcel.obtain(asBinder());
        Parcel _reply = Parcel.obtain();
        try {
          _data.writeInterfaceToken(ISmartFaceService.DESCRIPTOR);
          _data.writeStrongInterface(client);
          _data.writeInt(serviceType);
          this.mRemote.transact(1, _data, _reply, 0);
          _reply.readException();
          boolean _result = _reply.readBoolean();
          return _result;
        } finally {
          _reply.recycle();
          _data.recycle();
        }
      }

      @Override // com.samsung.android.smartface.ISmartFaceService
      public void unregister(ISmartFaceClient client) throws RemoteException {
        Parcel _data = Parcel.obtain(asBinder());
        Parcel _reply = Parcel.obtain();
        try {
          _data.writeInterfaceToken(ISmartFaceService.DESCRIPTOR);
          _data.writeStrongInterface(client);
          this.mRemote.transact(2, _data, _reply, 0);
          _reply.readException();
        } finally {
          _reply.recycle();
          _data.recycle();
        }
      }

      @Override // com.samsung.android.smartface.ISmartFaceService
      public void registerAsync(ISmartFaceClient client, int serviceType) throws RemoteException {
        Parcel _data = Parcel.obtain(asBinder());
        try {
          _data.writeInterfaceToken(ISmartFaceService.DESCRIPTOR);
          _data.writeStrongInterface(client);
          _data.writeInt(serviceType);
          this.mRemote.transact(3, _data, null, 1);
        } finally {
          _data.recycle();
        }
      }

      @Override // com.samsung.android.smartface.ISmartFaceService
      public void unregisterAsync(ISmartFaceClient client) throws RemoteException {
        Parcel _data = Parcel.obtain(asBinder());
        try {
          _data.writeInterfaceToken(ISmartFaceService.DESCRIPTOR);
          _data.writeStrongInterface(client);
          this.mRemote.transact(4, _data, null, 1);
        } finally {
          _data.recycle();
        }
      }

      @Override // com.samsung.android.smartface.ISmartFaceService
      public void setValue(ISmartFaceClient client, String key, String value)
          throws RemoteException {
        Parcel _data = Parcel.obtain(asBinder());
        Parcel _reply = Parcel.obtain();
        try {
          _data.writeInterfaceToken(ISmartFaceService.DESCRIPTOR);
          _data.writeStrongInterface(client);
          _data.writeString(key);
          _data.writeString(value);
          this.mRemote.transact(5, _data, _reply, 0);
          _reply.readException();
        } finally {
          _reply.recycle();
          _data.recycle();
        }
      }

      @Override // com.samsung.android.smartface.ISmartFaceService
      public int getSupportedServices() throws RemoteException {
        Parcel _data = Parcel.obtain(asBinder());
        Parcel _reply = Parcel.obtain();
        try {
          _data.writeInterfaceToken(ISmartFaceService.DESCRIPTOR);
          this.mRemote.transact(6, _data, _reply, 0);
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
      return 5;
    }
  }
}
