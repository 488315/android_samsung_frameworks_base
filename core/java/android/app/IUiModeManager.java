package android.app;

import android.p009os.Binder;
import android.p009os.IBinder;
import android.p009os.IInterface;
import android.p009os.Parcel;
import android.p009os.RemoteException;
import java.util.List;

/* loaded from: classes.dex */
public interface IUiModeManager extends IInterface {
  void addCallback(IUiModeManagerCallback iUiModeManagerCallback) throws RemoteException;

  void addNightPriorityAllowedPackageFromShell(String str) throws RemoteException;

  void addOnProjectionStateChangedListener(
      IOnProjectionStateChangedListener iOnProjectionStateChangedListener, int i)
      throws RemoteException;

  void disableCarMode(int i) throws RemoteException;

  void disableCarModeByCallingPackage(int i, String str) throws RemoteException;

  void enableCarMode(int i, int i2, String str) throws RemoteException;

  int getActiveProjectionTypes() throws RemoteException;

  float getContrast() throws RemoteException;

  int getCurrentModeType() throws RemoteException;

  long getCustomNightModeEnd() throws RemoteException;

  long getCustomNightModeStart() throws RemoteException;

  int getNightMode() throws RemoteException;

  int getNightModeCustomType() throws RemoteException;

  List<String> getNightPriorityAllowedPackagesFromScpm() throws RemoteException;

  int getPackageNightMode(String str, int i) throws RemoteException;

  List<String> getProjectingPackages(int i) throws RemoteException;

  boolean isNightModeLocked() throws RemoteException;

  boolean isUiModeLocked() throws RemoteException;

  boolean releaseProjection(int i, String str) throws RemoteException;

  void removeOnProjectionStateChangedListener(
      IOnProjectionStateChangedListener iOnProjectionStateChangedListener) throws RemoteException;

  boolean requestProjection(IBinder iBinder, int i, String str) throws RemoteException;

  void resetNightPriorityAppliedPackages(int i) throws RemoteException;

  void setApplicationNightMode(int i) throws RemoteException;

  void setCustomNightModeEnd(long j) throws RemoteException;

  void setCustomNightModeStart(long j) throws RemoteException;

  void setDesktopMode(boolean z) throws RemoteException;

  void setNightMode(int i) throws RemoteException;

  boolean setNightModeActivated(boolean z) throws RemoteException;

  boolean setNightModeActivatedForCustomMode(int i, boolean z) throws RemoteException;

  void setNightModeCustomType(int i) throws RemoteException;

  void setNightPriorityAllowedPackagesFromScpm(List<String> list) throws RemoteException;

  void setPackageNightMode(String str, int i, int i2) throws RemoteException;

  public static class Default implements IUiModeManager {
    @Override // android.app.IUiModeManager
    public void addCallback(IUiModeManagerCallback callback) throws RemoteException {}

    @Override // android.app.IUiModeManager
    public void enableCarMode(int flags, int priority, String callingPackage)
        throws RemoteException {}

    @Override // android.app.IUiModeManager
    public void disableCarMode(int flags) throws RemoteException {}

    @Override // android.app.IUiModeManager
    public void disableCarModeByCallingPackage(int flags, String callingPackage)
        throws RemoteException {}

    @Override // android.app.IUiModeManager
    public int getCurrentModeType() throws RemoteException {
      return 0;
    }

    @Override // android.app.IUiModeManager
    public void setNightMode(int mode) throws RemoteException {}

    @Override // android.app.IUiModeManager
    public int getNightMode() throws RemoteException {
      return 0;
    }

    @Override // android.app.IUiModeManager
    public void setNightModeCustomType(int nightModeCustomType) throws RemoteException {}

    @Override // android.app.IUiModeManager
    public int getNightModeCustomType() throws RemoteException {
      return 0;
    }

    @Override // android.app.IUiModeManager
    public void setApplicationNightMode(int mode) throws RemoteException {}

    @Override // android.app.IUiModeManager
    public void setPackageNightMode(String packageName, int userId, int mode)
        throws RemoteException {}

    @Override // android.app.IUiModeManager
    public int getPackageNightMode(String packageName, int userId) throws RemoteException {
      return 0;
    }

    @Override // android.app.IUiModeManager
    public void setNightPriorityAllowedPackagesFromScpm(List<String> packages)
        throws RemoteException {}

    @Override // android.app.IUiModeManager
    public List<String> getNightPriorityAllowedPackagesFromScpm() throws RemoteException {
      return null;
    }

    @Override // android.app.IUiModeManager
    public void addNightPriorityAllowedPackageFromShell(String packageName)
        throws RemoteException {}

    @Override // android.app.IUiModeManager
    public void resetNightPriorityAppliedPackages(int userId) throws RemoteException {}

    @Override // android.app.IUiModeManager
    public boolean isUiModeLocked() throws RemoteException {
      return false;
    }

    @Override // android.app.IUiModeManager
    public boolean isNightModeLocked() throws RemoteException {
      return false;
    }

    @Override // android.app.IUiModeManager
    public boolean setNightModeActivatedForCustomMode(int nightModeCustom, boolean active)
        throws RemoteException {
      return false;
    }

    @Override // android.app.IUiModeManager
    public boolean setNightModeActivated(boolean active) throws RemoteException {
      return false;
    }

    @Override // android.app.IUiModeManager
    public long getCustomNightModeStart() throws RemoteException {
      return 0L;
    }

    @Override // android.app.IUiModeManager
    public void setCustomNightModeStart(long time) throws RemoteException {}

    @Override // android.app.IUiModeManager
    public long getCustomNightModeEnd() throws RemoteException {
      return 0L;
    }

    @Override // android.app.IUiModeManager
    public void setCustomNightModeEnd(long time) throws RemoteException {}

    @Override // android.app.IUiModeManager
    public boolean requestProjection(IBinder binder, int projectionType, String callingPackage)
        throws RemoteException {
      return false;
    }

    @Override // android.app.IUiModeManager
    public boolean releaseProjection(int projectionType, String callingPackage)
        throws RemoteException {
      return false;
    }

    @Override // android.app.IUiModeManager
    public void addOnProjectionStateChangedListener(
        IOnProjectionStateChangedListener listener, int projectionType) throws RemoteException {}

    @Override // android.app.IUiModeManager
    public void removeOnProjectionStateChangedListener(IOnProjectionStateChangedListener listener)
        throws RemoteException {}

    @Override // android.app.IUiModeManager
    public List<String> getProjectingPackages(int projectionType) throws RemoteException {
      return null;
    }

    @Override // android.app.IUiModeManager
    public int getActiveProjectionTypes() throws RemoteException {
      return 0;
    }

    @Override // android.app.IUiModeManager
    public float getContrast() throws RemoteException {
      return 0.0f;
    }

    @Override // android.app.IUiModeManager
    public void setDesktopMode(boolean enabled) throws RemoteException {}

    @Override // android.p009os.IInterface
    public IBinder asBinder() {
      return null;
    }
  }

  public abstract static class Stub extends Binder implements IUiModeManager {
    public static final String DESCRIPTOR = "android.app.IUiModeManager";
    static final int TRANSACTION_addCallback = 1;
    static final int TRANSACTION_addNightPriorityAllowedPackageFromShell = 15;
    static final int TRANSACTION_addOnProjectionStateChangedListener = 27;
    static final int TRANSACTION_disableCarMode = 3;
    static final int TRANSACTION_disableCarModeByCallingPackage = 4;
    static final int TRANSACTION_enableCarMode = 2;
    static final int TRANSACTION_getActiveProjectionTypes = 30;
    static final int TRANSACTION_getContrast = 31;
    static final int TRANSACTION_getCurrentModeType = 5;
    static final int TRANSACTION_getCustomNightModeEnd = 23;
    static final int TRANSACTION_getCustomNightModeStart = 21;
    static final int TRANSACTION_getNightMode = 7;
    static final int TRANSACTION_getNightModeCustomType = 9;
    static final int TRANSACTION_getNightPriorityAllowedPackagesFromScpm = 14;
    static final int TRANSACTION_getPackageNightMode = 12;
    static final int TRANSACTION_getProjectingPackages = 29;
    static final int TRANSACTION_isNightModeLocked = 18;
    static final int TRANSACTION_isUiModeLocked = 17;
    static final int TRANSACTION_releaseProjection = 26;
    static final int TRANSACTION_removeOnProjectionStateChangedListener = 28;
    static final int TRANSACTION_requestProjection = 25;
    static final int TRANSACTION_resetNightPriorityAppliedPackages = 16;
    static final int TRANSACTION_setApplicationNightMode = 10;
    static final int TRANSACTION_setCustomNightModeEnd = 24;
    static final int TRANSACTION_setCustomNightModeStart = 22;
    static final int TRANSACTION_setDesktopMode = 32;
    static final int TRANSACTION_setNightMode = 6;
    static final int TRANSACTION_setNightModeActivated = 20;
    static final int TRANSACTION_setNightModeActivatedForCustomMode = 19;
    static final int TRANSACTION_setNightModeCustomType = 8;
    static final int TRANSACTION_setNightPriorityAllowedPackagesFromScpm = 13;
    static final int TRANSACTION_setPackageNightMode = 11;

    public Stub() {
      attachInterface(this, DESCRIPTOR);
    }

    public static IUiModeManager asInterface(IBinder obj) {
      if (obj == null) {
        return null;
      }
      IInterface iin = obj.queryLocalInterface(DESCRIPTOR);
      if (iin != null && (iin instanceof IUiModeManager)) {
        return (IUiModeManager) iin;
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
          return "addCallback";
        case 2:
          return "enableCarMode";
        case 3:
          return "disableCarMode";
        case 4:
          return "disableCarModeByCallingPackage";
        case 5:
          return "getCurrentModeType";
        case 6:
          return "setNightMode";
        case 7:
          return "getNightMode";
        case 8:
          return "setNightModeCustomType";
        case 9:
          return "getNightModeCustomType";
        case 10:
          return "setApplicationNightMode";
        case 11:
          return "setPackageNightMode";
        case 12:
          return "getPackageNightMode";
        case 13:
          return "setNightPriorityAllowedPackagesFromScpm";
        case 14:
          return "getNightPriorityAllowedPackagesFromScpm";
        case 15:
          return "addNightPriorityAllowedPackageFromShell";
        case 16:
          return "resetNightPriorityAppliedPackages";
        case 17:
          return "isUiModeLocked";
        case 18:
          return "isNightModeLocked";
        case 19:
          return "setNightModeActivatedForCustomMode";
        case 20:
          return "setNightModeActivated";
        case 21:
          return "getCustomNightModeStart";
        case 22:
          return "setCustomNightModeStart";
        case 23:
          return "getCustomNightModeEnd";
        case 24:
          return "setCustomNightModeEnd";
        case 25:
          return "requestProjection";
        case 26:
          return "releaseProjection";
        case 27:
          return "addOnProjectionStateChangedListener";
        case 28:
          return "removeOnProjectionStateChangedListener";
        case 29:
          return "getProjectingPackages";
        case 30:
          return "getActiveProjectionTypes";
        case 31:
          return "getContrast";
        case 32:
          return "setDesktopMode";
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
              IUiModeManagerCallback _arg0 =
                  IUiModeManagerCallback.Stub.asInterface(data.readStrongBinder());
              data.enforceNoDataAvail();
              addCallback(_arg0);
              reply.writeNoException();
              return true;
            case 2:
              int _arg02 = data.readInt();
              int _arg1 = data.readInt();
              String _arg2 = data.readString();
              data.enforceNoDataAvail();
              enableCarMode(_arg02, _arg1, _arg2);
              reply.writeNoException();
              return true;
            case 3:
              int _arg03 = data.readInt();
              data.enforceNoDataAvail();
              disableCarMode(_arg03);
              reply.writeNoException();
              return true;
            case 4:
              int _arg04 = data.readInt();
              String _arg12 = data.readString();
              data.enforceNoDataAvail();
              disableCarModeByCallingPackage(_arg04, _arg12);
              reply.writeNoException();
              return true;
            case 5:
              int _result = getCurrentModeType();
              reply.writeNoException();
              reply.writeInt(_result);
              return true;
            case 6:
              int _arg05 = data.readInt();
              data.enforceNoDataAvail();
              setNightMode(_arg05);
              reply.writeNoException();
              return true;
            case 7:
              int _result2 = getNightMode();
              reply.writeNoException();
              reply.writeInt(_result2);
              return true;
            case 8:
              int _arg06 = data.readInt();
              data.enforceNoDataAvail();
              setNightModeCustomType(_arg06);
              reply.writeNoException();
              return true;
            case 9:
              int _result3 = getNightModeCustomType();
              reply.writeNoException();
              reply.writeInt(_result3);
              return true;
            case 10:
              int _arg07 = data.readInt();
              data.enforceNoDataAvail();
              setApplicationNightMode(_arg07);
              reply.writeNoException();
              return true;
            case 11:
              String _arg08 = data.readString();
              int _arg13 = data.readInt();
              int _arg22 = data.readInt();
              data.enforceNoDataAvail();
              setPackageNightMode(_arg08, _arg13, _arg22);
              reply.writeNoException();
              return true;
            case 12:
              String _arg09 = data.readString();
              int _arg14 = data.readInt();
              data.enforceNoDataAvail();
              int _result4 = getPackageNightMode(_arg09, _arg14);
              reply.writeNoException();
              reply.writeInt(_result4);
              return true;
            case 13:
              List<String> _arg010 = data.createStringArrayList();
              data.enforceNoDataAvail();
              setNightPriorityAllowedPackagesFromScpm(_arg010);
              reply.writeNoException();
              return true;
            case 14:
              List<String> _result5 = getNightPriorityAllowedPackagesFromScpm();
              reply.writeNoException();
              reply.writeStringList(_result5);
              return true;
            case 15:
              String _arg011 = data.readString();
              data.enforceNoDataAvail();
              addNightPriorityAllowedPackageFromShell(_arg011);
              reply.writeNoException();
              return true;
            case 16:
              int _arg012 = data.readInt();
              data.enforceNoDataAvail();
              resetNightPriorityAppliedPackages(_arg012);
              reply.writeNoException();
              return true;
            case 17:
              boolean _result6 = isUiModeLocked();
              reply.writeNoException();
              reply.writeBoolean(_result6);
              return true;
            case 18:
              boolean _result7 = isNightModeLocked();
              reply.writeNoException();
              reply.writeBoolean(_result7);
              return true;
            case 19:
              int _arg013 = data.readInt();
              boolean _arg15 = data.readBoolean();
              data.enforceNoDataAvail();
              boolean _result8 = setNightModeActivatedForCustomMode(_arg013, _arg15);
              reply.writeNoException();
              reply.writeBoolean(_result8);
              return true;
            case 20:
              boolean _arg014 = data.readBoolean();
              data.enforceNoDataAvail();
              boolean _result9 = setNightModeActivated(_arg014);
              reply.writeNoException();
              reply.writeBoolean(_result9);
              return true;
            case 21:
              long _result10 = getCustomNightModeStart();
              reply.writeNoException();
              reply.writeLong(_result10);
              return true;
            case 22:
              long _arg015 = data.readLong();
              data.enforceNoDataAvail();
              setCustomNightModeStart(_arg015);
              reply.writeNoException();
              return true;
            case 23:
              long _result11 = getCustomNightModeEnd();
              reply.writeNoException();
              reply.writeLong(_result11);
              return true;
            case 24:
              long _arg016 = data.readLong();
              data.enforceNoDataAvail();
              setCustomNightModeEnd(_arg016);
              reply.writeNoException();
              return true;
            case 25:
              IBinder _arg017 = data.readStrongBinder();
              int _arg16 = data.readInt();
              String _arg23 = data.readString();
              data.enforceNoDataAvail();
              boolean _result12 = requestProjection(_arg017, _arg16, _arg23);
              reply.writeNoException();
              reply.writeBoolean(_result12);
              return true;
            case 26:
              int _arg018 = data.readInt();
              String _arg17 = data.readString();
              data.enforceNoDataAvail();
              boolean _result13 = releaseProjection(_arg018, _arg17);
              reply.writeNoException();
              reply.writeBoolean(_result13);
              return true;
            case 27:
              IOnProjectionStateChangedListener _arg019 =
                  IOnProjectionStateChangedListener.Stub.asInterface(data.readStrongBinder());
              int _arg18 = data.readInt();
              data.enforceNoDataAvail();
              addOnProjectionStateChangedListener(_arg019, _arg18);
              reply.writeNoException();
              return true;
            case 28:
              IOnProjectionStateChangedListener _arg020 =
                  IOnProjectionStateChangedListener.Stub.asInterface(data.readStrongBinder());
              data.enforceNoDataAvail();
              removeOnProjectionStateChangedListener(_arg020);
              reply.writeNoException();
              return true;
            case 29:
              int _arg021 = data.readInt();
              data.enforceNoDataAvail();
              List<String> _result14 = getProjectingPackages(_arg021);
              reply.writeNoException();
              reply.writeStringList(_result14);
              return true;
            case 30:
              int _result15 = getActiveProjectionTypes();
              reply.writeNoException();
              reply.writeInt(_result15);
              return true;
            case 31:
              float _result16 = getContrast();
              reply.writeNoException();
              reply.writeFloat(_result16);
              return true;
            case 32:
              boolean _arg022 = data.readBoolean();
              data.enforceNoDataAvail();
              setDesktopMode(_arg022);
              reply.writeNoException();
              return true;
            default:
              return super.onTransact(code, data, reply, flags);
          }
      }
    }

    private static class Proxy implements IUiModeManager {
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

      @Override // android.app.IUiModeManager
      public void addCallback(IUiModeManagerCallback callback) throws RemoteException {
        Parcel _data = Parcel.obtain(asBinder());
        Parcel _reply = Parcel.obtain();
        try {
          _data.writeInterfaceToken(Stub.DESCRIPTOR);
          _data.writeStrongInterface(callback);
          this.mRemote.transact(1, _data, _reply, 0);
          _reply.readException();
        } finally {
          _reply.recycle();
          _data.recycle();
        }
      }

      @Override // android.app.IUiModeManager
      public void enableCarMode(int flags, int priority, String callingPackage)
          throws RemoteException {
        Parcel _data = Parcel.obtain(asBinder());
        Parcel _reply = Parcel.obtain();
        try {
          _data.writeInterfaceToken(Stub.DESCRIPTOR);
          _data.writeInt(flags);
          _data.writeInt(priority);
          _data.writeString(callingPackage);
          this.mRemote.transact(2, _data, _reply, 0);
          _reply.readException();
        } finally {
          _reply.recycle();
          _data.recycle();
        }
      }

      @Override // android.app.IUiModeManager
      public void disableCarMode(int flags) throws RemoteException {
        Parcel _data = Parcel.obtain(asBinder());
        Parcel _reply = Parcel.obtain();
        try {
          _data.writeInterfaceToken(Stub.DESCRIPTOR);
          _data.writeInt(flags);
          this.mRemote.transact(3, _data, _reply, 0);
          _reply.readException();
        } finally {
          _reply.recycle();
          _data.recycle();
        }
      }

      @Override // android.app.IUiModeManager
      public void disableCarModeByCallingPackage(int flags, String callingPackage)
          throws RemoteException {
        Parcel _data = Parcel.obtain(asBinder());
        Parcel _reply = Parcel.obtain();
        try {
          _data.writeInterfaceToken(Stub.DESCRIPTOR);
          _data.writeInt(flags);
          _data.writeString(callingPackage);
          this.mRemote.transact(4, _data, _reply, 0);
          _reply.readException();
        } finally {
          _reply.recycle();
          _data.recycle();
        }
      }

      @Override // android.app.IUiModeManager
      public int getCurrentModeType() throws RemoteException {
        Parcel _data = Parcel.obtain(asBinder());
        Parcel _reply = Parcel.obtain();
        try {
          _data.writeInterfaceToken(Stub.DESCRIPTOR);
          this.mRemote.transact(5, _data, _reply, 0);
          _reply.readException();
          int _result = _reply.readInt();
          return _result;
        } finally {
          _reply.recycle();
          _data.recycle();
        }
      }

      @Override // android.app.IUiModeManager
      public void setNightMode(int mode) throws RemoteException {
        Parcel _data = Parcel.obtain(asBinder());
        Parcel _reply = Parcel.obtain();
        try {
          _data.writeInterfaceToken(Stub.DESCRIPTOR);
          _data.writeInt(mode);
          this.mRemote.transact(6, _data, _reply, 0);
          _reply.readException();
        } finally {
          _reply.recycle();
          _data.recycle();
        }
      }

      @Override // android.app.IUiModeManager
      public int getNightMode() throws RemoteException {
        Parcel _data = Parcel.obtain(asBinder());
        Parcel _reply = Parcel.obtain();
        try {
          _data.writeInterfaceToken(Stub.DESCRIPTOR);
          this.mRemote.transact(7, _data, _reply, 0);
          _reply.readException();
          int _result = _reply.readInt();
          return _result;
        } finally {
          _reply.recycle();
          _data.recycle();
        }
      }

      @Override // android.app.IUiModeManager
      public void setNightModeCustomType(int nightModeCustomType) throws RemoteException {
        Parcel _data = Parcel.obtain(asBinder());
        Parcel _reply = Parcel.obtain();
        try {
          _data.writeInterfaceToken(Stub.DESCRIPTOR);
          _data.writeInt(nightModeCustomType);
          this.mRemote.transact(8, _data, _reply, 0);
          _reply.readException();
        } finally {
          _reply.recycle();
          _data.recycle();
        }
      }

      @Override // android.app.IUiModeManager
      public int getNightModeCustomType() throws RemoteException {
        Parcel _data = Parcel.obtain(asBinder());
        Parcel _reply = Parcel.obtain();
        try {
          _data.writeInterfaceToken(Stub.DESCRIPTOR);
          this.mRemote.transact(9, _data, _reply, 0);
          _reply.readException();
          int _result = _reply.readInt();
          return _result;
        } finally {
          _reply.recycle();
          _data.recycle();
        }
      }

      @Override // android.app.IUiModeManager
      public void setApplicationNightMode(int mode) throws RemoteException {
        Parcel _data = Parcel.obtain(asBinder());
        Parcel _reply = Parcel.obtain();
        try {
          _data.writeInterfaceToken(Stub.DESCRIPTOR);
          _data.writeInt(mode);
          this.mRemote.transact(10, _data, _reply, 0);
          _reply.readException();
        } finally {
          _reply.recycle();
          _data.recycle();
        }
      }

      @Override // android.app.IUiModeManager
      public void setPackageNightMode(String packageName, int userId, int mode)
          throws RemoteException {
        Parcel _data = Parcel.obtain(asBinder());
        Parcel _reply = Parcel.obtain();
        try {
          _data.writeInterfaceToken(Stub.DESCRIPTOR);
          _data.writeString(packageName);
          _data.writeInt(userId);
          _data.writeInt(mode);
          this.mRemote.transact(11, _data, _reply, 0);
          _reply.readException();
        } finally {
          _reply.recycle();
          _data.recycle();
        }
      }

      @Override // android.app.IUiModeManager
      public int getPackageNightMode(String packageName, int userId) throws RemoteException {
        Parcel _data = Parcel.obtain(asBinder());
        Parcel _reply = Parcel.obtain();
        try {
          _data.writeInterfaceToken(Stub.DESCRIPTOR);
          _data.writeString(packageName);
          _data.writeInt(userId);
          this.mRemote.transact(12, _data, _reply, 0);
          _reply.readException();
          int _result = _reply.readInt();
          return _result;
        } finally {
          _reply.recycle();
          _data.recycle();
        }
      }

      @Override // android.app.IUiModeManager
      public void setNightPriorityAllowedPackagesFromScpm(List<String> packages)
          throws RemoteException {
        Parcel _data = Parcel.obtain(asBinder());
        Parcel _reply = Parcel.obtain();
        try {
          _data.writeInterfaceToken(Stub.DESCRIPTOR);
          _data.writeStringList(packages);
          this.mRemote.transact(13, _data, _reply, 0);
          _reply.readException();
        } finally {
          _reply.recycle();
          _data.recycle();
        }
      }

      @Override // android.app.IUiModeManager
      public List<String> getNightPriorityAllowedPackagesFromScpm() throws RemoteException {
        Parcel _data = Parcel.obtain(asBinder());
        Parcel _reply = Parcel.obtain();
        try {
          _data.writeInterfaceToken(Stub.DESCRIPTOR);
          this.mRemote.transact(14, _data, _reply, 0);
          _reply.readException();
          List<String> _result = _reply.createStringArrayList();
          return _result;
        } finally {
          _reply.recycle();
          _data.recycle();
        }
      }

      @Override // android.app.IUiModeManager
      public void addNightPriorityAllowedPackageFromShell(String packageName)
          throws RemoteException {
        Parcel _data = Parcel.obtain(asBinder());
        Parcel _reply = Parcel.obtain();
        try {
          _data.writeInterfaceToken(Stub.DESCRIPTOR);
          _data.writeString(packageName);
          this.mRemote.transact(15, _data, _reply, 0);
          _reply.readException();
        } finally {
          _reply.recycle();
          _data.recycle();
        }
      }

      @Override // android.app.IUiModeManager
      public void resetNightPriorityAppliedPackages(int userId) throws RemoteException {
        Parcel _data = Parcel.obtain(asBinder());
        Parcel _reply = Parcel.obtain();
        try {
          _data.writeInterfaceToken(Stub.DESCRIPTOR);
          _data.writeInt(userId);
          this.mRemote.transact(16, _data, _reply, 0);
          _reply.readException();
        } finally {
          _reply.recycle();
          _data.recycle();
        }
      }

      @Override // android.app.IUiModeManager
      public boolean isUiModeLocked() throws RemoteException {
        Parcel _data = Parcel.obtain(asBinder());
        Parcel _reply = Parcel.obtain();
        try {
          _data.writeInterfaceToken(Stub.DESCRIPTOR);
          this.mRemote.transact(17, _data, _reply, 0);
          _reply.readException();
          boolean _result = _reply.readBoolean();
          return _result;
        } finally {
          _reply.recycle();
          _data.recycle();
        }
      }

      @Override // android.app.IUiModeManager
      public boolean isNightModeLocked() throws RemoteException {
        Parcel _data = Parcel.obtain(asBinder());
        Parcel _reply = Parcel.obtain();
        try {
          _data.writeInterfaceToken(Stub.DESCRIPTOR);
          this.mRemote.transact(18, _data, _reply, 0);
          _reply.readException();
          boolean _result = _reply.readBoolean();
          return _result;
        } finally {
          _reply.recycle();
          _data.recycle();
        }
      }

      @Override // android.app.IUiModeManager
      public boolean setNightModeActivatedForCustomMode(int nightModeCustom, boolean active)
          throws RemoteException {
        Parcel _data = Parcel.obtain(asBinder());
        Parcel _reply = Parcel.obtain();
        try {
          _data.writeInterfaceToken(Stub.DESCRIPTOR);
          _data.writeInt(nightModeCustom);
          _data.writeBoolean(active);
          this.mRemote.transact(19, _data, _reply, 0);
          _reply.readException();
          boolean _result = _reply.readBoolean();
          return _result;
        } finally {
          _reply.recycle();
          _data.recycle();
        }
      }

      @Override // android.app.IUiModeManager
      public boolean setNightModeActivated(boolean active) throws RemoteException {
        Parcel _data = Parcel.obtain(asBinder());
        Parcel _reply = Parcel.obtain();
        try {
          _data.writeInterfaceToken(Stub.DESCRIPTOR);
          _data.writeBoolean(active);
          this.mRemote.transact(20, _data, _reply, 0);
          _reply.readException();
          boolean _result = _reply.readBoolean();
          return _result;
        } finally {
          _reply.recycle();
          _data.recycle();
        }
      }

      @Override // android.app.IUiModeManager
      public long getCustomNightModeStart() throws RemoteException {
        Parcel _data = Parcel.obtain(asBinder());
        Parcel _reply = Parcel.obtain();
        try {
          _data.writeInterfaceToken(Stub.DESCRIPTOR);
          this.mRemote.transact(21, _data, _reply, 0);
          _reply.readException();
          long _result = _reply.readLong();
          return _result;
        } finally {
          _reply.recycle();
          _data.recycle();
        }
      }

      @Override // android.app.IUiModeManager
      public void setCustomNightModeStart(long time) throws RemoteException {
        Parcel _data = Parcel.obtain(asBinder());
        Parcel _reply = Parcel.obtain();
        try {
          _data.writeInterfaceToken(Stub.DESCRIPTOR);
          _data.writeLong(time);
          this.mRemote.transact(22, _data, _reply, 0);
          _reply.readException();
        } finally {
          _reply.recycle();
          _data.recycle();
        }
      }

      @Override // android.app.IUiModeManager
      public long getCustomNightModeEnd() throws RemoteException {
        Parcel _data = Parcel.obtain(asBinder());
        Parcel _reply = Parcel.obtain();
        try {
          _data.writeInterfaceToken(Stub.DESCRIPTOR);
          this.mRemote.transact(23, _data, _reply, 0);
          _reply.readException();
          long _result = _reply.readLong();
          return _result;
        } finally {
          _reply.recycle();
          _data.recycle();
        }
      }

      @Override // android.app.IUiModeManager
      public void setCustomNightModeEnd(long time) throws RemoteException {
        Parcel _data = Parcel.obtain(asBinder());
        Parcel _reply = Parcel.obtain();
        try {
          _data.writeInterfaceToken(Stub.DESCRIPTOR);
          _data.writeLong(time);
          this.mRemote.transact(24, _data, _reply, 0);
          _reply.readException();
        } finally {
          _reply.recycle();
          _data.recycle();
        }
      }

      @Override // android.app.IUiModeManager
      public boolean requestProjection(IBinder binder, int projectionType, String callingPackage)
          throws RemoteException {
        Parcel _data = Parcel.obtain(asBinder());
        Parcel _reply = Parcel.obtain();
        try {
          _data.writeInterfaceToken(Stub.DESCRIPTOR);
          _data.writeStrongBinder(binder);
          _data.writeInt(projectionType);
          _data.writeString(callingPackage);
          this.mRemote.transact(25, _data, _reply, 0);
          _reply.readException();
          boolean _result = _reply.readBoolean();
          return _result;
        } finally {
          _reply.recycle();
          _data.recycle();
        }
      }

      @Override // android.app.IUiModeManager
      public boolean releaseProjection(int projectionType, String callingPackage)
          throws RemoteException {
        Parcel _data = Parcel.obtain(asBinder());
        Parcel _reply = Parcel.obtain();
        try {
          _data.writeInterfaceToken(Stub.DESCRIPTOR);
          _data.writeInt(projectionType);
          _data.writeString(callingPackage);
          this.mRemote.transact(26, _data, _reply, 0);
          _reply.readException();
          boolean _result = _reply.readBoolean();
          return _result;
        } finally {
          _reply.recycle();
          _data.recycle();
        }
      }

      @Override // android.app.IUiModeManager
      public void addOnProjectionStateChangedListener(
          IOnProjectionStateChangedListener listener, int projectionType) throws RemoteException {
        Parcel _data = Parcel.obtain(asBinder());
        Parcel _reply = Parcel.obtain();
        try {
          _data.writeInterfaceToken(Stub.DESCRIPTOR);
          _data.writeStrongInterface(listener);
          _data.writeInt(projectionType);
          this.mRemote.transact(27, _data, _reply, 0);
          _reply.readException();
        } finally {
          _reply.recycle();
          _data.recycle();
        }
      }

      @Override // android.app.IUiModeManager
      public void removeOnProjectionStateChangedListener(IOnProjectionStateChangedListener listener)
          throws RemoteException {
        Parcel _data = Parcel.obtain(asBinder());
        Parcel _reply = Parcel.obtain();
        try {
          _data.writeInterfaceToken(Stub.DESCRIPTOR);
          _data.writeStrongInterface(listener);
          this.mRemote.transact(28, _data, _reply, 0);
          _reply.readException();
        } finally {
          _reply.recycle();
          _data.recycle();
        }
      }

      @Override // android.app.IUiModeManager
      public List<String> getProjectingPackages(int projectionType) throws RemoteException {
        Parcel _data = Parcel.obtain(asBinder());
        Parcel _reply = Parcel.obtain();
        try {
          _data.writeInterfaceToken(Stub.DESCRIPTOR);
          _data.writeInt(projectionType);
          this.mRemote.transact(29, _data, _reply, 0);
          _reply.readException();
          List<String> _result = _reply.createStringArrayList();
          return _result;
        } finally {
          _reply.recycle();
          _data.recycle();
        }
      }

      @Override // android.app.IUiModeManager
      public int getActiveProjectionTypes() throws RemoteException {
        Parcel _data = Parcel.obtain(asBinder());
        Parcel _reply = Parcel.obtain();
        try {
          _data.writeInterfaceToken(Stub.DESCRIPTOR);
          this.mRemote.transact(30, _data, _reply, 0);
          _reply.readException();
          int _result = _reply.readInt();
          return _result;
        } finally {
          _reply.recycle();
          _data.recycle();
        }
      }

      @Override // android.app.IUiModeManager
      public float getContrast() throws RemoteException {
        Parcel _data = Parcel.obtain(asBinder());
        Parcel _reply = Parcel.obtain();
        try {
          _data.writeInterfaceToken(Stub.DESCRIPTOR);
          this.mRemote.transact(31, _data, _reply, 0);
          _reply.readException();
          float _result = _reply.readFloat();
          return _result;
        } finally {
          _reply.recycle();
          _data.recycle();
        }
      }

      @Override // android.app.IUiModeManager
      public void setDesktopMode(boolean enabled) throws RemoteException {
        Parcel _data = Parcel.obtain(asBinder());
        Parcel _reply = Parcel.obtain();
        try {
          _data.writeInterfaceToken(Stub.DESCRIPTOR);
          _data.writeBoolean(enabled);
          this.mRemote.transact(32, _data, _reply, 0);
          _reply.readException();
        } finally {
          _reply.recycle();
          _data.recycle();
        }
      }
    }

    @Override // android.p009os.Binder
    public int getMaxTransactionId() {
      return 31;
    }
  }
}
