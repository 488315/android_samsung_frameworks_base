package com.samsung.android.service.HermesService;

import android.p009os.Binder;
import android.p009os.IBinder;
import android.p009os.IInterface;
import android.p009os.Parcel;
import android.p009os.RemoteException;

/* loaded from: classes5.dex */
public interface IHermesService extends IInterface {
  public static final String DESCRIPTOR =
      "com.samsung.android.service.HermesService.IHermesService";

  byte[] hermesGetSecureHWInfo() throws RemoteException;

  int hermesProvisioning() throws RemoteException;

  byte[] hermesSelftest() throws RemoteException;

  int hermesTerminateService() throws RemoteException;

  byte[] hermesUpdateCryptoFW() throws RemoteException;

  int hermesVerifyProvisioning() throws RemoteException;

  public static class Default implements IHermesService {
    @Override // com.samsung.android.service.HermesService.IHermesService
    public byte[] hermesSelftest() throws RemoteException {
      return null;
    }

    @Override // com.samsung.android.service.HermesService.IHermesService
    public int hermesProvisioning() throws RemoteException {
      return 0;
    }

    @Override // com.samsung.android.service.HermesService.IHermesService
    public int hermesVerifyProvisioning() throws RemoteException {
      return 0;
    }

    @Override // com.samsung.android.service.HermesService.IHermesService
    public byte[] hermesGetSecureHWInfo() throws RemoteException {
      return null;
    }

    @Override // com.samsung.android.service.HermesService.IHermesService
    public int hermesTerminateService() throws RemoteException {
      return 0;
    }

    @Override // com.samsung.android.service.HermesService.IHermesService
    public byte[] hermesUpdateCryptoFW() throws RemoteException {
      return null;
    }

    @Override // android.p009os.IInterface
    public IBinder asBinder() {
      return null;
    }
  }

  public abstract static class Stub extends Binder implements IHermesService {
    static final int TRANSACTION_hermesGetSecureHWInfo = 4;
    static final int TRANSACTION_hermesProvisioning = 2;
    static final int TRANSACTION_hermesSelftest = 1;
    static final int TRANSACTION_hermesTerminateService = 5;
    static final int TRANSACTION_hermesUpdateCryptoFW = 6;
    static final int TRANSACTION_hermesVerifyProvisioning = 3;

    public Stub() {
      attachInterface(this, IHermesService.DESCRIPTOR);
    }

    public static IHermesService asInterface(IBinder obj) {
      if (obj == null) {
        return null;
      }
      IInterface iin = obj.queryLocalInterface(IHermesService.DESCRIPTOR);
      if (iin != null && (iin instanceof IHermesService)) {
        return (IHermesService) iin;
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
          return "hermesSelftest";
        case 2:
          return "hermesProvisioning";
        case 3:
          return "hermesVerifyProvisioning";
        case 4:
          return "hermesGetSecureHWInfo";
        case 5:
          return "hermesTerminateService";
        case 6:
          return "hermesUpdateCryptoFW";
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
        data.enforceInterface(IHermesService.DESCRIPTOR);
      }
      switch (code) {
        case IBinder.INTERFACE_TRANSACTION /* 1598968902 */:
          reply.writeString(IHermesService.DESCRIPTOR);
          return true;
        default:
          switch (code) {
            case 1:
              byte[] _result = hermesSelftest();
              reply.writeNoException();
              reply.writeByteArray(_result);
              return true;
            case 2:
              int _result2 = hermesProvisioning();
              reply.writeNoException();
              reply.writeInt(_result2);
              return true;
            case 3:
              int _result3 = hermesVerifyProvisioning();
              reply.writeNoException();
              reply.writeInt(_result3);
              return true;
            case 4:
              byte[] _result4 = hermesGetSecureHWInfo();
              reply.writeNoException();
              reply.writeByteArray(_result4);
              return true;
            case 5:
              int _result5 = hermesTerminateService();
              reply.writeNoException();
              reply.writeInt(_result5);
              return true;
            case 6:
              byte[] _result6 = hermesUpdateCryptoFW();
              reply.writeNoException();
              reply.writeByteArray(_result6);
              return true;
            default:
              return super.onTransact(code, data, reply, flags);
          }
      }
    }

    private static class Proxy implements IHermesService {
      private IBinder mRemote;

      Proxy(IBinder remote) {
        this.mRemote = remote;
      }

      @Override // android.p009os.IInterface
      public IBinder asBinder() {
        return this.mRemote;
      }

      public String getInterfaceDescriptor() {
        return IHermesService.DESCRIPTOR;
      }

      @Override // com.samsung.android.service.HermesService.IHermesService
      public byte[] hermesSelftest() throws RemoteException {
        Parcel _data = Parcel.obtain(asBinder());
        Parcel _reply = Parcel.obtain();
        try {
          _data.writeInterfaceToken(IHermesService.DESCRIPTOR);
          this.mRemote.transact(1, _data, _reply, 0);
          _reply.readException();
          byte[] _result = _reply.createByteArray();
          return _result;
        } finally {
          _reply.recycle();
          _data.recycle();
        }
      }

      @Override // com.samsung.android.service.HermesService.IHermesService
      public int hermesProvisioning() throws RemoteException {
        Parcel _data = Parcel.obtain(asBinder());
        Parcel _reply = Parcel.obtain();
        try {
          _data.writeInterfaceToken(IHermesService.DESCRIPTOR);
          this.mRemote.transact(2, _data, _reply, 0);
          _reply.readException();
          int _result = _reply.readInt();
          return _result;
        } finally {
          _reply.recycle();
          _data.recycle();
        }
      }

      @Override // com.samsung.android.service.HermesService.IHermesService
      public int hermesVerifyProvisioning() throws RemoteException {
        Parcel _data = Parcel.obtain(asBinder());
        Parcel _reply = Parcel.obtain();
        try {
          _data.writeInterfaceToken(IHermesService.DESCRIPTOR);
          this.mRemote.transact(3, _data, _reply, 0);
          _reply.readException();
          int _result = _reply.readInt();
          return _result;
        } finally {
          _reply.recycle();
          _data.recycle();
        }
      }

      @Override // com.samsung.android.service.HermesService.IHermesService
      public byte[] hermesGetSecureHWInfo() throws RemoteException {
        Parcel _data = Parcel.obtain(asBinder());
        Parcel _reply = Parcel.obtain();
        try {
          _data.writeInterfaceToken(IHermesService.DESCRIPTOR);
          this.mRemote.transact(4, _data, _reply, 0);
          _reply.readException();
          byte[] _result = _reply.createByteArray();
          return _result;
        } finally {
          _reply.recycle();
          _data.recycle();
        }
      }

      @Override // com.samsung.android.service.HermesService.IHermesService
      public int hermesTerminateService() throws RemoteException {
        Parcel _data = Parcel.obtain(asBinder());
        Parcel _reply = Parcel.obtain();
        try {
          _data.writeInterfaceToken(IHermesService.DESCRIPTOR);
          this.mRemote.transact(5, _data, _reply, 0);
          _reply.readException();
          int _result = _reply.readInt();
          return _result;
        } finally {
          _reply.recycle();
          _data.recycle();
        }
      }

      @Override // com.samsung.android.service.HermesService.IHermesService
      public byte[] hermesUpdateCryptoFW() throws RemoteException {
        Parcel _data = Parcel.obtain(asBinder());
        Parcel _reply = Parcel.obtain();
        try {
          _data.writeInterfaceToken(IHermesService.DESCRIPTOR);
          this.mRemote.transact(6, _data, _reply, 0);
          _reply.readException();
          byte[] _result = _reply.createByteArray();
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
