package com.samsung.android.emergencymode;

import android.p009os.Binder;
import android.p009os.IBinder;
import android.p009os.IInterface;
import android.p009os.Parcel;
import android.p009os.RemoteException;

/* loaded from: classes5.dex */
public interface IEmergencyManager extends IInterface {
  public static final String DESCRIPTOR = "com.samsung.android.emergencymode.IEmergencyManager";

  boolean addAppToLauncher(String str, boolean z) throws RemoteException;

  boolean checkInvalidBroadcast(String str, String str2) throws RemoteException;

  boolean checkInvalidProcess(String str) throws RemoteException;

  boolean checkModeType(int i) throws RemoteException;

  boolean checkValidIntentAction(String str, String str2) throws RemoteException;

  boolean checkValidPackage(String str, String str2, int i) throws RemoteException;

  int getEmergencyState() throws RemoteException;

  boolean isEmergencyMode() throws RemoteException;

  boolean isModifying() throws RemoteException;

  boolean isScreenOn() throws RemoteException;

  boolean isUserPackageBlocked() throws RemoteException;

  boolean needMobileDataBlock() throws RemoteException;

  void setLocationProviderEnabled(boolean z) throws RemoteException;

  void setUserPackageBlocked(boolean z) throws RemoteException;

  public static class Default implements IEmergencyManager {
    @Override // com.samsung.android.emergencymode.IEmergencyManager
    public boolean isEmergencyMode() throws RemoteException {
      return false;
    }

    @Override // com.samsung.android.emergencymode.IEmergencyManager
    public int getEmergencyState() throws RemoteException {
      return 0;
    }

    @Override // com.samsung.android.emergencymode.IEmergencyManager
    public boolean checkValidPackage(String pkgName, String actName, int allowFlag)
        throws RemoteException {
      return false;
    }

    @Override // com.samsung.android.emergencymode.IEmergencyManager
    public boolean checkValidIntentAction(String pkgName, String actName) throws RemoteException {
      return false;
    }

    @Override // com.samsung.android.emergencymode.IEmergencyManager
    public boolean checkInvalidProcess(String pkgName) throws RemoteException {
      return false;
    }

    @Override // com.samsung.android.emergencymode.IEmergencyManager
    public boolean checkInvalidBroadcast(String pkgName, String action) throws RemoteException {
      return false;
    }

    @Override // com.samsung.android.emergencymode.IEmergencyManager
    public boolean needMobileDataBlock() throws RemoteException {
      return false;
    }

    @Override // com.samsung.android.emergencymode.IEmergencyManager
    public boolean isScreenOn() throws RemoteException {
      return false;
    }

    @Override // com.samsung.android.emergencymode.IEmergencyManager
    public void setUserPackageBlocked(boolean enabled) throws RemoteException {}

    @Override // com.samsung.android.emergencymode.IEmergencyManager
    public boolean isUserPackageBlocked() throws RemoteException {
      return false;
    }

    @Override // com.samsung.android.emergencymode.IEmergencyManager
    public boolean isModifying() throws RemoteException {
      return false;
    }

    @Override // com.samsung.android.emergencymode.IEmergencyManager
    public void setLocationProviderEnabled(boolean enable) throws RemoteException {}

    @Override // com.samsung.android.emergencymode.IEmergencyManager
    public boolean addAppToLauncher(String pkgName, boolean enabled) throws RemoteException {
      return false;
    }

    @Override // com.samsung.android.emergencymode.IEmergencyManager
    public boolean checkModeType(int type) throws RemoteException {
      return false;
    }

    @Override // android.p009os.IInterface
    public IBinder asBinder() {
      return null;
    }
  }

  public abstract static class Stub extends Binder implements IEmergencyManager {
    static final int TRANSACTION_addAppToLauncher = 13;
    static final int TRANSACTION_checkInvalidBroadcast = 6;
    static final int TRANSACTION_checkInvalidProcess = 5;
    static final int TRANSACTION_checkModeType = 14;
    static final int TRANSACTION_checkValidIntentAction = 4;
    static final int TRANSACTION_checkValidPackage = 3;
    static final int TRANSACTION_getEmergencyState = 2;
    static final int TRANSACTION_isEmergencyMode = 1;
    static final int TRANSACTION_isModifying = 11;
    static final int TRANSACTION_isScreenOn = 8;
    static final int TRANSACTION_isUserPackageBlocked = 10;
    static final int TRANSACTION_needMobileDataBlock = 7;
    static final int TRANSACTION_setLocationProviderEnabled = 12;
    static final int TRANSACTION_setUserPackageBlocked = 9;

    public Stub() {
      attachInterface(this, IEmergencyManager.DESCRIPTOR);
    }

    public static IEmergencyManager asInterface(IBinder obj) {
      if (obj == null) {
        return null;
      }
      IInterface iin = obj.queryLocalInterface(IEmergencyManager.DESCRIPTOR);
      if (iin != null && (iin instanceof IEmergencyManager)) {
        return (IEmergencyManager) iin;
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
          return "isEmergencyMode";
        case 2:
          return "getEmergencyState";
        case 3:
          return "checkValidPackage";
        case 4:
          return "checkValidIntentAction";
        case 5:
          return "checkInvalidProcess";
        case 6:
          return "checkInvalidBroadcast";
        case 7:
          return "needMobileDataBlock";
        case 8:
          return "isScreenOn";
        case 9:
          return "setUserPackageBlocked";
        case 10:
          return "isUserPackageBlocked";
        case 11:
          return "isModifying";
        case 12:
          return "setLocationProviderEnabled";
        case 13:
          return "addAppToLauncher";
        case 14:
          return "checkModeType";
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
        data.enforceInterface(IEmergencyManager.DESCRIPTOR);
      }
      switch (code) {
        case IBinder.INTERFACE_TRANSACTION /* 1598968902 */:
          reply.writeString(IEmergencyManager.DESCRIPTOR);
          return true;
        default:
          switch (code) {
            case 1:
              boolean _result = isEmergencyMode();
              reply.writeNoException();
              reply.writeBoolean(_result);
              return true;
            case 2:
              int _result2 = getEmergencyState();
              reply.writeNoException();
              reply.writeInt(_result2);
              return true;
            case 3:
              String _arg0 = data.readString();
              String _arg1 = data.readString();
              int _arg2 = data.readInt();
              data.enforceNoDataAvail();
              boolean _result3 = checkValidPackage(_arg0, _arg1, _arg2);
              reply.writeNoException();
              reply.writeBoolean(_result3);
              return true;
            case 4:
              String _arg02 = data.readString();
              String _arg12 = data.readString();
              data.enforceNoDataAvail();
              boolean _result4 = checkValidIntentAction(_arg02, _arg12);
              reply.writeNoException();
              reply.writeBoolean(_result4);
              return true;
            case 5:
              String _arg03 = data.readString();
              data.enforceNoDataAvail();
              boolean _result5 = checkInvalidProcess(_arg03);
              reply.writeNoException();
              reply.writeBoolean(_result5);
              return true;
            case 6:
              String _arg04 = data.readString();
              String _arg13 = data.readString();
              data.enforceNoDataAvail();
              boolean _result6 = checkInvalidBroadcast(_arg04, _arg13);
              reply.writeNoException();
              reply.writeBoolean(_result6);
              return true;
            case 7:
              boolean _result7 = needMobileDataBlock();
              reply.writeNoException();
              reply.writeBoolean(_result7);
              return true;
            case 8:
              boolean _result8 = isScreenOn();
              reply.writeNoException();
              reply.writeBoolean(_result8);
              return true;
            case 9:
              boolean _arg05 = data.readBoolean();
              data.enforceNoDataAvail();
              setUserPackageBlocked(_arg05);
              reply.writeNoException();
              return true;
            case 10:
              boolean _result9 = isUserPackageBlocked();
              reply.writeNoException();
              reply.writeBoolean(_result9);
              return true;
            case 11:
              boolean _result10 = isModifying();
              reply.writeNoException();
              reply.writeBoolean(_result10);
              return true;
            case 12:
              boolean _arg06 = data.readBoolean();
              data.enforceNoDataAvail();
              setLocationProviderEnabled(_arg06);
              reply.writeNoException();
              return true;
            case 13:
              String _arg07 = data.readString();
              boolean _arg14 = data.readBoolean();
              data.enforceNoDataAvail();
              boolean _result11 = addAppToLauncher(_arg07, _arg14);
              reply.writeNoException();
              reply.writeBoolean(_result11);
              return true;
            case 14:
              int _arg08 = data.readInt();
              data.enforceNoDataAvail();
              boolean _result12 = checkModeType(_arg08);
              reply.writeNoException();
              reply.writeBoolean(_result12);
              return true;
            default:
              return super.onTransact(code, data, reply, flags);
          }
      }
    }

    private static class Proxy implements IEmergencyManager {
      private IBinder mRemote;

      Proxy(IBinder remote) {
        this.mRemote = remote;
      }

      @Override // android.p009os.IInterface
      public IBinder asBinder() {
        return this.mRemote;
      }

      public String getInterfaceDescriptor() {
        return IEmergencyManager.DESCRIPTOR;
      }

      @Override // com.samsung.android.emergencymode.IEmergencyManager
      public boolean isEmergencyMode() throws RemoteException {
        Parcel _data = Parcel.obtain(asBinder());
        Parcel _reply = Parcel.obtain();
        try {
          _data.writeInterfaceToken(IEmergencyManager.DESCRIPTOR);
          this.mRemote.transact(1, _data, _reply, 0);
          _reply.readException();
          boolean _result = _reply.readBoolean();
          return _result;
        } finally {
          _reply.recycle();
          _data.recycle();
        }
      }

      @Override // com.samsung.android.emergencymode.IEmergencyManager
      public int getEmergencyState() throws RemoteException {
        Parcel _data = Parcel.obtain(asBinder());
        Parcel _reply = Parcel.obtain();
        try {
          _data.writeInterfaceToken(IEmergencyManager.DESCRIPTOR);
          this.mRemote.transact(2, _data, _reply, 0);
          _reply.readException();
          int _result = _reply.readInt();
          return _result;
        } finally {
          _reply.recycle();
          _data.recycle();
        }
      }

      @Override // com.samsung.android.emergencymode.IEmergencyManager
      public boolean checkValidPackage(String pkgName, String actName, int allowFlag)
          throws RemoteException {
        Parcel _data = Parcel.obtain(asBinder());
        Parcel _reply = Parcel.obtain();
        try {
          _data.writeInterfaceToken(IEmergencyManager.DESCRIPTOR);
          _data.writeString(pkgName);
          _data.writeString(actName);
          _data.writeInt(allowFlag);
          this.mRemote.transact(3, _data, _reply, 0);
          _reply.readException();
          boolean _result = _reply.readBoolean();
          return _result;
        } finally {
          _reply.recycle();
          _data.recycle();
        }
      }

      @Override // com.samsung.android.emergencymode.IEmergencyManager
      public boolean checkValidIntentAction(String pkgName, String actName) throws RemoteException {
        Parcel _data = Parcel.obtain(asBinder());
        Parcel _reply = Parcel.obtain();
        try {
          _data.writeInterfaceToken(IEmergencyManager.DESCRIPTOR);
          _data.writeString(pkgName);
          _data.writeString(actName);
          this.mRemote.transact(4, _data, _reply, 0);
          _reply.readException();
          boolean _result = _reply.readBoolean();
          return _result;
        } finally {
          _reply.recycle();
          _data.recycle();
        }
      }

      @Override // com.samsung.android.emergencymode.IEmergencyManager
      public boolean checkInvalidProcess(String pkgName) throws RemoteException {
        Parcel _data = Parcel.obtain(asBinder());
        Parcel _reply = Parcel.obtain();
        try {
          _data.writeInterfaceToken(IEmergencyManager.DESCRIPTOR);
          _data.writeString(pkgName);
          this.mRemote.transact(5, _data, _reply, 0);
          _reply.readException();
          boolean _result = _reply.readBoolean();
          return _result;
        } finally {
          _reply.recycle();
          _data.recycle();
        }
      }

      @Override // com.samsung.android.emergencymode.IEmergencyManager
      public boolean checkInvalidBroadcast(String pkgName, String action) throws RemoteException {
        Parcel _data = Parcel.obtain(asBinder());
        Parcel _reply = Parcel.obtain();
        try {
          _data.writeInterfaceToken(IEmergencyManager.DESCRIPTOR);
          _data.writeString(pkgName);
          _data.writeString(action);
          this.mRemote.transact(6, _data, _reply, 0);
          _reply.readException();
          boolean _result = _reply.readBoolean();
          return _result;
        } finally {
          _reply.recycle();
          _data.recycle();
        }
      }

      @Override // com.samsung.android.emergencymode.IEmergencyManager
      public boolean needMobileDataBlock() throws RemoteException {
        Parcel _data = Parcel.obtain(asBinder());
        Parcel _reply = Parcel.obtain();
        try {
          _data.writeInterfaceToken(IEmergencyManager.DESCRIPTOR);
          this.mRemote.transact(7, _data, _reply, 0);
          _reply.readException();
          boolean _result = _reply.readBoolean();
          return _result;
        } finally {
          _reply.recycle();
          _data.recycle();
        }
      }

      @Override // com.samsung.android.emergencymode.IEmergencyManager
      public boolean isScreenOn() throws RemoteException {
        Parcel _data = Parcel.obtain(asBinder());
        Parcel _reply = Parcel.obtain();
        try {
          _data.writeInterfaceToken(IEmergencyManager.DESCRIPTOR);
          this.mRemote.transact(8, _data, _reply, 0);
          _reply.readException();
          boolean _result = _reply.readBoolean();
          return _result;
        } finally {
          _reply.recycle();
          _data.recycle();
        }
      }

      @Override // com.samsung.android.emergencymode.IEmergencyManager
      public void setUserPackageBlocked(boolean enabled) throws RemoteException {
        Parcel _data = Parcel.obtain(asBinder());
        Parcel _reply = Parcel.obtain();
        try {
          _data.writeInterfaceToken(IEmergencyManager.DESCRIPTOR);
          _data.writeBoolean(enabled);
          this.mRemote.transact(9, _data, _reply, 0);
          _reply.readException();
        } finally {
          _reply.recycle();
          _data.recycle();
        }
      }

      @Override // com.samsung.android.emergencymode.IEmergencyManager
      public boolean isUserPackageBlocked() throws RemoteException {
        Parcel _data = Parcel.obtain(asBinder());
        Parcel _reply = Parcel.obtain();
        try {
          _data.writeInterfaceToken(IEmergencyManager.DESCRIPTOR);
          this.mRemote.transact(10, _data, _reply, 0);
          _reply.readException();
          boolean _result = _reply.readBoolean();
          return _result;
        } finally {
          _reply.recycle();
          _data.recycle();
        }
      }

      @Override // com.samsung.android.emergencymode.IEmergencyManager
      public boolean isModifying() throws RemoteException {
        Parcel _data = Parcel.obtain(asBinder());
        Parcel _reply = Parcel.obtain();
        try {
          _data.writeInterfaceToken(IEmergencyManager.DESCRIPTOR);
          this.mRemote.transact(11, _data, _reply, 0);
          _reply.readException();
          boolean _result = _reply.readBoolean();
          return _result;
        } finally {
          _reply.recycle();
          _data.recycle();
        }
      }

      @Override // com.samsung.android.emergencymode.IEmergencyManager
      public void setLocationProviderEnabled(boolean enable) throws RemoteException {
        Parcel _data = Parcel.obtain(asBinder());
        Parcel _reply = Parcel.obtain();
        try {
          _data.writeInterfaceToken(IEmergencyManager.DESCRIPTOR);
          _data.writeBoolean(enable);
          this.mRemote.transact(12, _data, _reply, 0);
          _reply.readException();
        } finally {
          _reply.recycle();
          _data.recycle();
        }
      }

      @Override // com.samsung.android.emergencymode.IEmergencyManager
      public boolean addAppToLauncher(String pkgName, boolean enabled) throws RemoteException {
        Parcel _data = Parcel.obtain(asBinder());
        Parcel _reply = Parcel.obtain();
        try {
          _data.writeInterfaceToken(IEmergencyManager.DESCRIPTOR);
          _data.writeString(pkgName);
          _data.writeBoolean(enabled);
          this.mRemote.transact(13, _data, _reply, 0);
          _reply.readException();
          boolean _result = _reply.readBoolean();
          return _result;
        } finally {
          _reply.recycle();
          _data.recycle();
        }
      }

      @Override // com.samsung.android.emergencymode.IEmergencyManager
      public boolean checkModeType(int type) throws RemoteException {
        Parcel _data = Parcel.obtain(asBinder());
        Parcel _reply = Parcel.obtain();
        try {
          _data.writeInterfaceToken(IEmergencyManager.DESCRIPTOR);
          _data.writeInt(type);
          this.mRemote.transact(14, _data, _reply, 0);
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
      return 13;
    }
  }
}
