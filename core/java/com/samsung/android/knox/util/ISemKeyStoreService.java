package com.samsung.android.knox.util;

import android.p009os.Binder;
import android.p009os.IBinder;
import android.p009os.IInterface;
import android.p009os.Parcel;
import android.p009os.RemoteException;

/* loaded from: classes5.dex */
public interface ISemKeyStoreService extends IInterface {
  public static final String DESCRIPTOR = "com.samsung.android.knox.util.ISemKeyStoreService";

  int getKeystoreStatus() throws RemoteException;

  void grantAccessForAKS(int i, String str) throws RemoteException;

  int installCACert(SemCertAndroidKeyStore semCertAndroidKeyStore) throws RemoteException;

  int installCertificateInAndroidKeyStore(SemCertByte semCertByte, String str, char[] cArr, int i)
      throws RemoteException;

  int isAliasExists(String str) throws RemoteException;

  public static class Default implements ISemKeyStoreService {
    @Override // com.samsung.android.knox.util.ISemKeyStoreService
    public int isAliasExists(String aliasName) throws RemoteException {
      return 0;
    }

    @Override // com.samsung.android.knox.util.ISemKeyStoreService
    public int installCertificateInAndroidKeyStore(
        SemCertByte certificate, String aliasName, char[] password, int uid)
        throws RemoteException {
      return 0;
    }

    @Override // com.samsung.android.knox.util.ISemKeyStoreService
    public void grantAccessForAKS(int uid, String alias) throws RemoteException {}

    @Override // com.samsung.android.knox.util.ISemKeyStoreService
    public int installCACert(SemCertAndroidKeyStore caCert) throws RemoteException {
      return 0;
    }

    @Override // com.samsung.android.knox.util.ISemKeyStoreService
    public int getKeystoreStatus() throws RemoteException {
      return 0;
    }

    @Override // android.p009os.IInterface
    public IBinder asBinder() {
      return null;
    }
  }

  public abstract static class Stub extends Binder implements ISemKeyStoreService {
    static final int TRANSACTION_getKeystoreStatus = 5;
    static final int TRANSACTION_grantAccessForAKS = 3;
    static final int TRANSACTION_installCACert = 4;
    static final int TRANSACTION_installCertificateInAndroidKeyStore = 2;
    static final int TRANSACTION_isAliasExists = 1;

    public Stub() {
      attachInterface(this, ISemKeyStoreService.DESCRIPTOR);
    }

    public static ISemKeyStoreService asInterface(IBinder obj) {
      if (obj == null) {
        return null;
      }
      IInterface iin = obj.queryLocalInterface(ISemKeyStoreService.DESCRIPTOR);
      if (iin != null && (iin instanceof ISemKeyStoreService)) {
        return (ISemKeyStoreService) iin;
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
          return "isAliasExists";
        case 2:
          return "installCertificateInAndroidKeyStore";
        case 3:
          return "grantAccessForAKS";
        case 4:
          return "installCACert";
        case 5:
          return "getKeystoreStatus";
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
        data.enforceInterface(ISemKeyStoreService.DESCRIPTOR);
      }
      switch (code) {
        case IBinder.INTERFACE_TRANSACTION /* 1598968902 */:
          reply.writeString(ISemKeyStoreService.DESCRIPTOR);
          return true;
        default:
          switch (code) {
            case 1:
              String _arg0 = data.readString();
              data.enforceNoDataAvail();
              int _result = isAliasExists(_arg0);
              reply.writeNoException();
              reply.writeInt(_result);
              return true;
            case 2:
              SemCertByte _arg02 = (SemCertByte) data.readTypedObject(SemCertByte.CREATOR);
              String _arg1 = data.readString();
              char[] _arg2 = data.createCharArray();
              int _arg3 = data.readInt();
              data.enforceNoDataAvail();
              int _result2 = installCertificateInAndroidKeyStore(_arg02, _arg1, _arg2, _arg3);
              reply.writeNoException();
              reply.writeInt(_result2);
              return true;
            case 3:
              int _arg03 = data.readInt();
              String _arg12 = data.readString();
              data.enforceNoDataAvail();
              grantAccessForAKS(_arg03, _arg12);
              reply.writeNoException();
              return true;
            case 4:
              SemCertAndroidKeyStore _arg04 =
                  (SemCertAndroidKeyStore) data.readTypedObject(SemCertAndroidKeyStore.CREATOR);
              data.enforceNoDataAvail();
              int _result3 = installCACert(_arg04);
              reply.writeNoException();
              reply.writeInt(_result3);
              return true;
            case 5:
              int _result4 = getKeystoreStatus();
              reply.writeNoException();
              reply.writeInt(_result4);
              return true;
            default:
              return super.onTransact(code, data, reply, flags);
          }
      }
    }

    private static class Proxy implements ISemKeyStoreService {
      private IBinder mRemote;

      Proxy(IBinder remote) {
        this.mRemote = remote;
      }

      @Override // android.p009os.IInterface
      public IBinder asBinder() {
        return this.mRemote;
      }

      public String getInterfaceDescriptor() {
        return ISemKeyStoreService.DESCRIPTOR;
      }

      @Override // com.samsung.android.knox.util.ISemKeyStoreService
      public int isAliasExists(String aliasName) throws RemoteException {
        Parcel _data = Parcel.obtain(asBinder());
        Parcel _reply = Parcel.obtain();
        try {
          _data.writeInterfaceToken(ISemKeyStoreService.DESCRIPTOR);
          _data.writeString(aliasName);
          this.mRemote.transact(1, _data, _reply, 0);
          _reply.readException();
          int _result = _reply.readInt();
          return _result;
        } finally {
          _reply.recycle();
          _data.recycle();
        }
      }

      @Override // com.samsung.android.knox.util.ISemKeyStoreService
      public int installCertificateInAndroidKeyStore(
          SemCertByte certificate, String aliasName, char[] password, int uid)
          throws RemoteException {
        Parcel _data = Parcel.obtain(asBinder());
        Parcel _reply = Parcel.obtain();
        try {
          _data.writeInterfaceToken(ISemKeyStoreService.DESCRIPTOR);
          _data.writeTypedObject(certificate, 0);
          _data.writeString(aliasName);
          _data.writeCharArray(password);
          _data.writeInt(uid);
          this.mRemote.transact(2, _data, _reply, 0);
          _reply.readException();
          int _result = _reply.readInt();
          return _result;
        } finally {
          _reply.recycle();
          _data.recycle();
        }
      }

      @Override // com.samsung.android.knox.util.ISemKeyStoreService
      public void grantAccessForAKS(int uid, String alias) throws RemoteException {
        Parcel _data = Parcel.obtain(asBinder());
        Parcel _reply = Parcel.obtain();
        try {
          _data.writeInterfaceToken(ISemKeyStoreService.DESCRIPTOR);
          _data.writeInt(uid);
          _data.writeString(alias);
          this.mRemote.transact(3, _data, _reply, 0);
          _reply.readException();
        } finally {
          _reply.recycle();
          _data.recycle();
        }
      }

      @Override // com.samsung.android.knox.util.ISemKeyStoreService
      public int installCACert(SemCertAndroidKeyStore caCert) throws RemoteException {
        Parcel _data = Parcel.obtain(asBinder());
        Parcel _reply = Parcel.obtain();
        try {
          _data.writeInterfaceToken(ISemKeyStoreService.DESCRIPTOR);
          _data.writeTypedObject(caCert, 0);
          this.mRemote.transact(4, _data, _reply, 0);
          _reply.readException();
          int _result = _reply.readInt();
          return _result;
        } finally {
          _reply.recycle();
          _data.recycle();
        }
      }

      @Override // com.samsung.android.knox.util.ISemKeyStoreService
      public int getKeystoreStatus() throws RemoteException {
        Parcel _data = Parcel.obtain(asBinder());
        Parcel _reply = Parcel.obtain();
        try {
          _data.writeInterfaceToken(ISemKeyStoreService.DESCRIPTOR);
          this.mRemote.transact(5, _data, _reply, 0);
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
      return 4;
    }
  }
}
