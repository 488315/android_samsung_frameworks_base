package com.samsung.android.app;

import android.os.Binder;
import android.os.Bundle;
import android.os.IBinder;
import android.os.IInterface;
import android.os.Parcel;
import android.os.RemoteException;
import java.util.List;

/* loaded from: classes5.dex */
public interface ISemDualAppManager extends IInterface {
  public static final String DESCRIPTOR = "com.samsung.android.app.ISemDualAppManager";

  List<String> getAllInstalledWhitelistedPackages() throws RemoteException;

  String[] getAllWhitelistedPackages() throws RemoteException;

  boolean isInstalledWhitelistedPackage(String str) throws RemoteException;

  Bundle updateDualAppData(String str, int i, Bundle bundle) throws RemoteException;

  public static class Default implements ISemDualAppManager {
    @Override // com.samsung.android.app.ISemDualAppManager
    public List<String> getAllInstalledWhitelistedPackages() throws RemoteException {
      return null;
    }

    @Override // com.samsung.android.app.ISemDualAppManager
    public boolean isInstalledWhitelistedPackage(String pkgName) throws RemoteException {
      return false;
    }

    @Override // com.samsung.android.app.ISemDualAppManager
    public String[] getAllWhitelistedPackages() throws RemoteException {
      return null;
    }

    @Override // com.samsung.android.app.ISemDualAppManager
    public Bundle updateDualAppData(String pkgName, int userId, Bundle bundle)
        throws RemoteException {
      return null;
    }

    @Override // android.os.IInterface
    public IBinder asBinder() {
      return null;
    }
  }

  public abstract static class Stub extends Binder implements ISemDualAppManager {
    static final int TRANSACTION_getAllInstalledWhitelistedPackages = 1;
    static final int TRANSACTION_getAllWhitelistedPackages = 3;
    static final int TRANSACTION_isInstalledWhitelistedPackage = 2;
    static final int TRANSACTION_updateDualAppData = 4;

    public Stub() {
      attachInterface(this, ISemDualAppManager.DESCRIPTOR);
    }

    public static ISemDualAppManager asInterface(IBinder obj) {
      if (obj == null) {
        return null;
      }
      IInterface iin = obj.queryLocalInterface(ISemDualAppManager.DESCRIPTOR);
      if (iin != null && (iin instanceof ISemDualAppManager)) {
        return (ISemDualAppManager) iin;
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
          return "getAllInstalledWhitelistedPackages";
        case 2:
          return "isInstalledWhitelistedPackage";
        case 3:
          return "getAllWhitelistedPackages";
        case 4:
          return "updateDualAppData";
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
        data.enforceInterface(ISemDualAppManager.DESCRIPTOR);
      }
      switch (code) {
        case IBinder.INTERFACE_TRANSACTION /* 1598968902 */:
          reply.writeString(ISemDualAppManager.DESCRIPTOR);
          return true;
        default:
          switch (code) {
            case 1:
              List<String> _result = getAllInstalledWhitelistedPackages();
              reply.writeNoException();
              reply.writeStringList(_result);
              return true;
            case 2:
              String _arg0 = data.readString();
              data.enforceNoDataAvail();
              boolean _result2 = isInstalledWhitelistedPackage(_arg0);
              reply.writeNoException();
              reply.writeBoolean(_result2);
              return true;
            case 3:
              String[] _result3 = getAllWhitelistedPackages();
              reply.writeNoException();
              reply.writeStringArray(_result3);
              return true;
            case 4:
              String _arg02 = data.readString();
              int _arg1 = data.readInt();
              Bundle _arg2 = (Bundle) data.readTypedObject(Bundle.CREATOR);
              data.enforceNoDataAvail();
              Bundle _result4 = updateDualAppData(_arg02, _arg1, _arg2);
              reply.writeNoException();
              reply.writeTypedObject(_result4, 1);
              return true;
            default:
              return super.onTransact(code, data, reply, flags);
          }
      }
    }

    private static class Proxy implements ISemDualAppManager {
      private IBinder mRemote;

      Proxy(IBinder remote) {
        this.mRemote = remote;
      }

      @Override // android.os.IInterface
      public IBinder asBinder() {
        return this.mRemote;
      }

      public String getInterfaceDescriptor() {
        return ISemDualAppManager.DESCRIPTOR;
      }

      @Override // com.samsung.android.app.ISemDualAppManager
      public List<String> getAllInstalledWhitelistedPackages() throws RemoteException {
        Parcel _data = Parcel.obtain(asBinder());
        Parcel _reply = Parcel.obtain();
        try {
          _data.writeInterfaceToken(ISemDualAppManager.DESCRIPTOR);
          this.mRemote.transact(1, _data, _reply, 0);
          _reply.readException();
          List<String> _result = _reply.createStringArrayList();
          return _result;
        } finally {
          _reply.recycle();
          _data.recycle();
        }
      }

      @Override // com.samsung.android.app.ISemDualAppManager
      public boolean isInstalledWhitelistedPackage(String pkgName) throws RemoteException {
        Parcel _data = Parcel.obtain(asBinder());
        Parcel _reply = Parcel.obtain();
        try {
          _data.writeInterfaceToken(ISemDualAppManager.DESCRIPTOR);
          _data.writeString(pkgName);
          this.mRemote.transact(2, _data, _reply, 0);
          _reply.readException();
          boolean _result = _reply.readBoolean();
          return _result;
        } finally {
          _reply.recycle();
          _data.recycle();
        }
      }

      @Override // com.samsung.android.app.ISemDualAppManager
      public String[] getAllWhitelistedPackages() throws RemoteException {
        Parcel _data = Parcel.obtain(asBinder());
        Parcel _reply = Parcel.obtain();
        try {
          _data.writeInterfaceToken(ISemDualAppManager.DESCRIPTOR);
          this.mRemote.transact(3, _data, _reply, 0);
          _reply.readException();
          String[] _result = _reply.createStringArray();
          return _result;
        } finally {
          _reply.recycle();
          _data.recycle();
        }
      }

      @Override // com.samsung.android.app.ISemDualAppManager
      public Bundle updateDualAppData(String pkgName, int userId, Bundle bundle)
          throws RemoteException {
        Parcel _data = Parcel.obtain(asBinder());
        Parcel _reply = Parcel.obtain();
        try {
          _data.writeInterfaceToken(ISemDualAppManager.DESCRIPTOR);
          _data.writeString(pkgName);
          _data.writeInt(userId);
          _data.writeTypedObject(bundle, 0);
          this.mRemote.transact(4, _data, _reply, 0);
          _reply.readException();
          Bundle _result = (Bundle) _reply.readTypedObject(Bundle.CREATOR);
          return _result;
        } finally {
          _reply.recycle();
          _data.recycle();
        }
      }
    }

    @Override // android.os.Binder
    public int getMaxTransactionId() {
      return 3;
    }
  }
}
