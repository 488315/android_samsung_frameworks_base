package com.samsung.android.perfsdkservice;

import android.os.Binder;
import android.os.IBinder;
import android.os.IInterface;
import android.os.Parcel;
import android.os.RemoteException;

/* loaded from: classes5.dex */
public interface IPerfSDKService extends IInterface {
  public static final String DESCRIPTOR = "com.samsung.android.perfsdkservice.IPerfSDKService";

  int connectionRequest() throws RemoteException;

  String getAllowedPkgName() throws RemoteException;

  String getChangedForegroundPackagename() throws RemoteException;

  String getForegroundPackagename() throws RemoteException;

  int[] getHighBoostingLevel() throws RemoteException;

  int[] getLowBoostingLevel() throws RemoteException;

  int[] getThermalTable() throws RemoteException;

  int initPerfSDK(String str) throws RemoteException;

  int removeSessionKey(String str) throws RemoteException;

  int setSessionKey(String str) throws RemoteException;

  public static class Default implements IPerfSDKService {
    @Override // com.samsung.android.perfsdkservice.IPerfSDKService
    public int initPerfSDK(String serverName) throws RemoteException {
      return 0;
    }

    @Override // com.samsung.android.perfsdkservice.IPerfSDKService
    public String getAllowedPkgName() throws RemoteException {
      return null;
    }

    @Override // com.samsung.android.perfsdkservice.IPerfSDKService
    public int[] getHighBoostingLevel() throws RemoteException {
      return null;
    }

    @Override // com.samsung.android.perfsdkservice.IPerfSDKService
    public int[] getLowBoostingLevel() throws RemoteException {
      return null;
    }

    @Override // com.samsung.android.perfsdkservice.IPerfSDKService
    public int[] getThermalTable() throws RemoteException {
      return null;
    }

    @Override // com.samsung.android.perfsdkservice.IPerfSDKService
    public String getForegroundPackagename() throws RemoteException {
      return null;
    }

    @Override // com.samsung.android.perfsdkservice.IPerfSDKService
    public String getChangedForegroundPackagename() throws RemoteException {
      return null;
    }

    @Override // com.samsung.android.perfsdkservice.IPerfSDKService
    public int setSessionKey(String sessionKey) throws RemoteException {
      return 0;
    }

    @Override // com.samsung.android.perfsdkservice.IPerfSDKService
    public int removeSessionKey(String sessionKey) throws RemoteException {
      return 0;
    }

    @Override // com.samsung.android.perfsdkservice.IPerfSDKService
    public int connectionRequest() throws RemoteException {
      return 0;
    }

    @Override // android.os.IInterface
    public IBinder asBinder() {
      return null;
    }
  }

  public abstract static class Stub extends Binder implements IPerfSDKService {
    static final int TRANSACTION_connectionRequest = 10;
    static final int TRANSACTION_getAllowedPkgName = 2;
    static final int TRANSACTION_getChangedForegroundPackagename = 7;
    static final int TRANSACTION_getForegroundPackagename = 6;
    static final int TRANSACTION_getHighBoostingLevel = 3;
    static final int TRANSACTION_getLowBoostingLevel = 4;
    static final int TRANSACTION_getThermalTable = 5;
    static final int TRANSACTION_initPerfSDK = 1;
    static final int TRANSACTION_removeSessionKey = 9;
    static final int TRANSACTION_setSessionKey = 8;

    public Stub() {
      attachInterface(this, IPerfSDKService.DESCRIPTOR);
    }

    public static IPerfSDKService asInterface(IBinder obj) {
      if (obj == null) {
        return null;
      }
      IInterface iin = obj.queryLocalInterface(IPerfSDKService.DESCRIPTOR);
      if (iin != null && (iin instanceof IPerfSDKService)) {
        return (IPerfSDKService) iin;
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
          return "initPerfSDK";
        case 2:
          return "getAllowedPkgName";
        case 3:
          return "getHighBoostingLevel";
        case 4:
          return "getLowBoostingLevel";
        case 5:
          return "getThermalTable";
        case 6:
          return "getForegroundPackagename";
        case 7:
          return "getChangedForegroundPackagename";
        case 8:
          return "setSessionKey";
        case 9:
          return "removeSessionKey";
        case 10:
          return "connectionRequest";
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
        data.enforceInterface(IPerfSDKService.DESCRIPTOR);
      }
      switch (code) {
        case IBinder.INTERFACE_TRANSACTION /* 1598968902 */:
          reply.writeString(IPerfSDKService.DESCRIPTOR);
          return true;
        default:
          switch (code) {
            case 1:
              String _arg0 = data.readString();
              data.enforceNoDataAvail();
              int _result = initPerfSDK(_arg0);
              reply.writeNoException();
              reply.writeInt(_result);
              return true;
            case 2:
              String _result2 = getAllowedPkgName();
              reply.writeNoException();
              reply.writeString(_result2);
              return true;
            case 3:
              int[] _result3 = getHighBoostingLevel();
              reply.writeNoException();
              reply.writeIntArray(_result3);
              return true;
            case 4:
              int[] _result4 = getLowBoostingLevel();
              reply.writeNoException();
              reply.writeIntArray(_result4);
              return true;
            case 5:
              int[] _result5 = getThermalTable();
              reply.writeNoException();
              reply.writeIntArray(_result5);
              return true;
            case 6:
              String _result6 = getForegroundPackagename();
              reply.writeNoException();
              reply.writeString(_result6);
              return true;
            case 7:
              String _result7 = getChangedForegroundPackagename();
              reply.writeNoException();
              reply.writeString(_result7);
              return true;
            case 8:
              String _arg02 = data.readString();
              data.enforceNoDataAvail();
              int _result8 = setSessionKey(_arg02);
              reply.writeNoException();
              reply.writeInt(_result8);
              return true;
            case 9:
              String _arg03 = data.readString();
              data.enforceNoDataAvail();
              int _result9 = removeSessionKey(_arg03);
              reply.writeNoException();
              reply.writeInt(_result9);
              return true;
            case 10:
              int _result10 = connectionRequest();
              reply.writeNoException();
              reply.writeInt(_result10);
              return true;
            default:
              return super.onTransact(code, data, reply, flags);
          }
      }
    }

    private static class Proxy implements IPerfSDKService {
      private IBinder mRemote;

      Proxy(IBinder remote) {
        this.mRemote = remote;
      }

      @Override // android.os.IInterface
      public IBinder asBinder() {
        return this.mRemote;
      }

      public String getInterfaceDescriptor() {
        return IPerfSDKService.DESCRIPTOR;
      }

      @Override // com.samsung.android.perfsdkservice.IPerfSDKService
      public int initPerfSDK(String serverName) throws RemoteException {
        Parcel _data = Parcel.obtain(asBinder());
        Parcel _reply = Parcel.obtain();
        try {
          _data.writeInterfaceToken(IPerfSDKService.DESCRIPTOR);
          _data.writeString(serverName);
          this.mRemote.transact(1, _data, _reply, 0);
          _reply.readException();
          int _result = _reply.readInt();
          return _result;
        } finally {
          _reply.recycle();
          _data.recycle();
        }
      }

      @Override // com.samsung.android.perfsdkservice.IPerfSDKService
      public String getAllowedPkgName() throws RemoteException {
        Parcel _data = Parcel.obtain(asBinder());
        Parcel _reply = Parcel.obtain();
        try {
          _data.writeInterfaceToken(IPerfSDKService.DESCRIPTOR);
          this.mRemote.transact(2, _data, _reply, 0);
          _reply.readException();
          String _result = _reply.readString();
          return _result;
        } finally {
          _reply.recycle();
          _data.recycle();
        }
      }

      @Override // com.samsung.android.perfsdkservice.IPerfSDKService
      public int[] getHighBoostingLevel() throws RemoteException {
        Parcel _data = Parcel.obtain(asBinder());
        Parcel _reply = Parcel.obtain();
        try {
          _data.writeInterfaceToken(IPerfSDKService.DESCRIPTOR);
          this.mRemote.transact(3, _data, _reply, 0);
          _reply.readException();
          int[] _result = _reply.createIntArray();
          return _result;
        } finally {
          _reply.recycle();
          _data.recycle();
        }
      }

      @Override // com.samsung.android.perfsdkservice.IPerfSDKService
      public int[] getLowBoostingLevel() throws RemoteException {
        Parcel _data = Parcel.obtain(asBinder());
        Parcel _reply = Parcel.obtain();
        try {
          _data.writeInterfaceToken(IPerfSDKService.DESCRIPTOR);
          this.mRemote.transact(4, _data, _reply, 0);
          _reply.readException();
          int[] _result = _reply.createIntArray();
          return _result;
        } finally {
          _reply.recycle();
          _data.recycle();
        }
      }

      @Override // com.samsung.android.perfsdkservice.IPerfSDKService
      public int[] getThermalTable() throws RemoteException {
        Parcel _data = Parcel.obtain(asBinder());
        Parcel _reply = Parcel.obtain();
        try {
          _data.writeInterfaceToken(IPerfSDKService.DESCRIPTOR);
          this.mRemote.transact(5, _data, _reply, 0);
          _reply.readException();
          int[] _result = _reply.createIntArray();
          return _result;
        } finally {
          _reply.recycle();
          _data.recycle();
        }
      }

      @Override // com.samsung.android.perfsdkservice.IPerfSDKService
      public String getForegroundPackagename() throws RemoteException {
        Parcel _data = Parcel.obtain(asBinder());
        Parcel _reply = Parcel.obtain();
        try {
          _data.writeInterfaceToken(IPerfSDKService.DESCRIPTOR);
          this.mRemote.transact(6, _data, _reply, 0);
          _reply.readException();
          String _result = _reply.readString();
          return _result;
        } finally {
          _reply.recycle();
          _data.recycle();
        }
      }

      @Override // com.samsung.android.perfsdkservice.IPerfSDKService
      public String getChangedForegroundPackagename() throws RemoteException {
        Parcel _data = Parcel.obtain(asBinder());
        Parcel _reply = Parcel.obtain();
        try {
          _data.writeInterfaceToken(IPerfSDKService.DESCRIPTOR);
          this.mRemote.transact(7, _data, _reply, 0);
          _reply.readException();
          String _result = _reply.readString();
          return _result;
        } finally {
          _reply.recycle();
          _data.recycle();
        }
      }

      @Override // com.samsung.android.perfsdkservice.IPerfSDKService
      public int setSessionKey(String sessionKey) throws RemoteException {
        Parcel _data = Parcel.obtain(asBinder());
        Parcel _reply = Parcel.obtain();
        try {
          _data.writeInterfaceToken(IPerfSDKService.DESCRIPTOR);
          _data.writeString(sessionKey);
          this.mRemote.transact(8, _data, _reply, 0);
          _reply.readException();
          int _result = _reply.readInt();
          return _result;
        } finally {
          _reply.recycle();
          _data.recycle();
        }
      }

      @Override // com.samsung.android.perfsdkservice.IPerfSDKService
      public int removeSessionKey(String sessionKey) throws RemoteException {
        Parcel _data = Parcel.obtain(asBinder());
        Parcel _reply = Parcel.obtain();
        try {
          _data.writeInterfaceToken(IPerfSDKService.DESCRIPTOR);
          _data.writeString(sessionKey);
          this.mRemote.transact(9, _data, _reply, 0);
          _reply.readException();
          int _result = _reply.readInt();
          return _result;
        } finally {
          _reply.recycle();
          _data.recycle();
        }
      }

      @Override // com.samsung.android.perfsdkservice.IPerfSDKService
      public int connectionRequest() throws RemoteException {
        Parcel _data = Parcel.obtain(asBinder());
        Parcel _reply = Parcel.obtain();
        try {
          _data.writeInterfaceToken(IPerfSDKService.DESCRIPTOR);
          this.mRemote.transact(10, _data, _reply, 0);
          _reply.readException();
          int _result = _reply.readInt();
          return _result;
        } finally {
          _reply.recycle();
          _data.recycle();
        }
      }
    }

    @Override // android.os.Binder
    public int getMaxTransactionId() {
      return 9;
    }
  }
}
