package com.samsung.android.core;

import android.p009os.Binder;
import android.p009os.IBinder;
import android.p009os.IInterface;
import android.p009os.Parcel;
import android.p009os.RemoteException;
import java.util.List;

/* loaded from: classes5.dex */
public interface ICompatChangeableManager extends IInterface {
  public static final String DESCRIPTOR = "com.samsung.android.core.ICompatChangeableManager";

  List<String> getCompatChangeablePackageNameList() throws RemoteException;

  int getUid(String str) throws RemoteException;

  boolean hasGameCategory(String str) throws RemoteException;

  boolean hasLauncherActivity(String str) throws RemoteException;

  boolean isMinAspectRatioOverrideDisallowed(String str) throws RemoteException;

  boolean isOrientationOverrideDisallowed(String str) throws RemoteException;

  public static class Default implements ICompatChangeableManager {
    @Override // com.samsung.android.core.ICompatChangeableManager
    public List<String> getCompatChangeablePackageNameList() throws RemoteException {
      return null;
    }

    @Override // com.samsung.android.core.ICompatChangeableManager
    public int getUid(String packageName) throws RemoteException {
      return 0;
    }

    @Override // com.samsung.android.core.ICompatChangeableManager
    public boolean hasLauncherActivity(String packageName) throws RemoteException {
      return false;
    }

    @Override // com.samsung.android.core.ICompatChangeableManager
    public boolean hasGameCategory(String packageName) throws RemoteException {
      return false;
    }

    @Override // com.samsung.android.core.ICompatChangeableManager
    public boolean isOrientationOverrideDisallowed(String packageName) throws RemoteException {
      return false;
    }

    @Override // com.samsung.android.core.ICompatChangeableManager
    public boolean isMinAspectRatioOverrideDisallowed(String packageName) throws RemoteException {
      return false;
    }

    @Override // android.p009os.IInterface
    public IBinder asBinder() {
      return null;
    }
  }

  public abstract static class Stub extends Binder implements ICompatChangeableManager {
    static final int TRANSACTION_getCompatChangeablePackageNameList = 2;
    static final int TRANSACTION_getUid = 3;
    static final int TRANSACTION_hasGameCategory = 5;
    static final int TRANSACTION_hasLauncherActivity = 4;
    static final int TRANSACTION_isMinAspectRatioOverrideDisallowed = 102;
    static final int TRANSACTION_isOrientationOverrideDisallowed = 101;

    public Stub() {
      attachInterface(this, ICompatChangeableManager.DESCRIPTOR);
    }

    public static ICompatChangeableManager asInterface(IBinder obj) {
      if (obj == null) {
        return null;
      }
      IInterface iin = obj.queryLocalInterface(ICompatChangeableManager.DESCRIPTOR);
      if (iin != null && (iin instanceof ICompatChangeableManager)) {
        return (ICompatChangeableManager) iin;
      }
      return new Proxy(obj);
    }

    @Override // android.p009os.IInterface
    public IBinder asBinder() {
      return this;
    }

    public static String getDefaultTransactionName(int transactionCode) {
      switch (transactionCode) {
        case 2:
          return "getCompatChangeablePackageNameList";
        case 3:
          return "getUid";
        case 4:
          return "hasLauncherActivity";
        case 5:
          return "hasGameCategory";
        case 101:
          return "isOrientationOverrideDisallowed";
        case 102:
          return "isMinAspectRatioOverrideDisallowed";
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
        data.enforceInterface(ICompatChangeableManager.DESCRIPTOR);
      }
      switch (code) {
        case IBinder.INTERFACE_TRANSACTION /* 1598968902 */:
          reply.writeString(ICompatChangeableManager.DESCRIPTOR);
          return true;
        default:
          switch (code) {
            case 2:
              List<String> _result = getCompatChangeablePackageNameList();
              reply.writeNoException();
              reply.writeStringList(_result);
              return true;
            case 3:
              String _arg0 = data.readString();
              data.enforceNoDataAvail();
              int _result2 = getUid(_arg0);
              reply.writeNoException();
              reply.writeInt(_result2);
              return true;
            case 4:
              String _arg02 = data.readString();
              data.enforceNoDataAvail();
              boolean _result3 = hasLauncherActivity(_arg02);
              reply.writeNoException();
              reply.writeBoolean(_result3);
              return true;
            case 5:
              String _arg03 = data.readString();
              data.enforceNoDataAvail();
              boolean _result4 = hasGameCategory(_arg03);
              reply.writeNoException();
              reply.writeBoolean(_result4);
              return true;
            case 101:
              String _arg04 = data.readString();
              data.enforceNoDataAvail();
              boolean _result5 = isOrientationOverrideDisallowed(_arg04);
              reply.writeNoException();
              reply.writeBoolean(_result5);
              return true;
            case 102:
              String _arg05 = data.readString();
              data.enforceNoDataAvail();
              boolean _result6 = isMinAspectRatioOverrideDisallowed(_arg05);
              reply.writeNoException();
              reply.writeBoolean(_result6);
              return true;
            default:
              return super.onTransact(code, data, reply, flags);
          }
      }
    }

    private static class Proxy implements ICompatChangeableManager {
      private IBinder mRemote;

      Proxy(IBinder remote) {
        this.mRemote = remote;
      }

      @Override // android.p009os.IInterface
      public IBinder asBinder() {
        return this.mRemote;
      }

      public String getInterfaceDescriptor() {
        return ICompatChangeableManager.DESCRIPTOR;
      }

      @Override // com.samsung.android.core.ICompatChangeableManager
      public List<String> getCompatChangeablePackageNameList() throws RemoteException {
        Parcel _data = Parcel.obtain(asBinder());
        Parcel _reply = Parcel.obtain();
        try {
          _data.writeInterfaceToken(ICompatChangeableManager.DESCRIPTOR);
          this.mRemote.transact(2, _data, _reply, 0);
          _reply.readException();
          List<String> _result = _reply.createStringArrayList();
          return _result;
        } finally {
          _reply.recycle();
          _data.recycle();
        }
      }

      @Override // com.samsung.android.core.ICompatChangeableManager
      public int getUid(String packageName) throws RemoteException {
        Parcel _data = Parcel.obtain(asBinder());
        Parcel _reply = Parcel.obtain();
        try {
          _data.writeInterfaceToken(ICompatChangeableManager.DESCRIPTOR);
          _data.writeString(packageName);
          this.mRemote.transact(3, _data, _reply, 0);
          _reply.readException();
          int _result = _reply.readInt();
          return _result;
        } finally {
          _reply.recycle();
          _data.recycle();
        }
      }

      @Override // com.samsung.android.core.ICompatChangeableManager
      public boolean hasLauncherActivity(String packageName) throws RemoteException {
        Parcel _data = Parcel.obtain(asBinder());
        Parcel _reply = Parcel.obtain();
        try {
          _data.writeInterfaceToken(ICompatChangeableManager.DESCRIPTOR);
          _data.writeString(packageName);
          this.mRemote.transact(4, _data, _reply, 0);
          _reply.readException();
          boolean _result = _reply.readBoolean();
          return _result;
        } finally {
          _reply.recycle();
          _data.recycle();
        }
      }

      @Override // com.samsung.android.core.ICompatChangeableManager
      public boolean hasGameCategory(String packageName) throws RemoteException {
        Parcel _data = Parcel.obtain(asBinder());
        Parcel _reply = Parcel.obtain();
        try {
          _data.writeInterfaceToken(ICompatChangeableManager.DESCRIPTOR);
          _data.writeString(packageName);
          this.mRemote.transact(5, _data, _reply, 0);
          _reply.readException();
          boolean _result = _reply.readBoolean();
          return _result;
        } finally {
          _reply.recycle();
          _data.recycle();
        }
      }

      @Override // com.samsung.android.core.ICompatChangeableManager
      public boolean isOrientationOverrideDisallowed(String packageName) throws RemoteException {
        Parcel _data = Parcel.obtain(asBinder());
        Parcel _reply = Parcel.obtain();
        try {
          _data.writeInterfaceToken(ICompatChangeableManager.DESCRIPTOR);
          _data.writeString(packageName);
          this.mRemote.transact(101, _data, _reply, 0);
          _reply.readException();
          boolean _result = _reply.readBoolean();
          return _result;
        } finally {
          _reply.recycle();
          _data.recycle();
        }
      }

      @Override // com.samsung.android.core.ICompatChangeableManager
      public boolean isMinAspectRatioOverrideDisallowed(String packageName) throws RemoteException {
        Parcel _data = Parcel.obtain(asBinder());
        Parcel _reply = Parcel.obtain();
        try {
          _data.writeInterfaceToken(ICompatChangeableManager.DESCRIPTOR);
          _data.writeString(packageName);
          this.mRemote.transact(102, _data, _reply, 0);
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
      return 101;
    }
  }
}
