package android.os;

import java.util.List;
import java.util.Map;

/* loaded from: classes3.dex */
public interface IIntelligentBatterySaverService extends IInterface {
  public static final String DESCRIPTOR = "android.os.IIntelligentBatterySaverService";

  boolean addSqdBlockList(int i, String str) throws RemoteException;

  List dexoptPackages(List<String> list) throws RemoteException;

  long[] getGain() throws RemoteException;

  Bundle getOperationHistory() throws RemoteException;

  Bundle getSleepTime() throws RemoteException;

  Map getSqdBlockList() throws RemoteException;

  boolean isEnableSerive() throws RemoteException;

  boolean isSqdSupport() throws RemoteException;

  boolean isSqdUiControlEnabled() throws RemoteException;

  boolean removeSqdBlockList(int i, String str) throws RemoteException;

  void setRubinEvent(String str) throws RemoteException;

  void setSarrUiControlEnable(boolean z) throws RemoteException;

  void setSleepModeEnabled(boolean z) throws RemoteException;

  void setSleepTime(long j, long j2) throws RemoteException;

  void setSqdUiControlEnabled(boolean z) throws RemoteException;

  public static class Default implements IIntelligentBatterySaverService {
    @Override // android.os.IIntelligentBatterySaverService
    public void setSqdUiControlEnabled(boolean allow) throws RemoteException {}

    @Override // android.os.IIntelligentBatterySaverService
    public boolean isSqdUiControlEnabled() throws RemoteException {
      return false;
    }

    @Override // android.os.IIntelligentBatterySaverService
    public boolean isSqdSupport() throws RemoteException {
      return false;
    }

    @Override // android.os.IIntelligentBatterySaverService
    public boolean addSqdBlockList(int uid, String pkgName) throws RemoteException {
      return false;
    }

    @Override // android.os.IIntelligentBatterySaverService
    public boolean removeSqdBlockList(int uid, String pkgName) throws RemoteException {
      return false;
    }

    @Override // android.os.IIntelligentBatterySaverService
    public Map getSqdBlockList() throws RemoteException {
      return null;
    }

    @Override // android.os.IIntelligentBatterySaverService
    public long[] getGain() throws RemoteException {
      return null;
    }

    @Override // android.os.IIntelligentBatterySaverService
    public void setSarrUiControlEnable(boolean allow) throws RemoteException {}

    @Override // android.os.IIntelligentBatterySaverService
    public void setSleepModeEnabled(boolean allow) throws RemoteException {}

    @Override // android.os.IIntelligentBatterySaverService
    public void setSleepTime(long startTime, long endTime) throws RemoteException {}

    @Override // android.os.IIntelligentBatterySaverService
    public void setRubinEvent(String type) throws RemoteException {}

    @Override // android.os.IIntelligentBatterySaverService
    public boolean isEnableSerive() throws RemoteException {
      return false;
    }

    @Override // android.os.IIntelligentBatterySaverService
    public Bundle getOperationHistory() throws RemoteException {
      return null;
    }

    @Override // android.os.IIntelligentBatterySaverService
    public Bundle getSleepTime() throws RemoteException {
      return null;
    }

    @Override // android.os.IIntelligentBatterySaverService
    public List dexoptPackages(List<String> pkgNames) throws RemoteException {
      return null;
    }

    @Override // android.os.IInterface
    public IBinder asBinder() {
      return null;
    }
  }

  public abstract static class Stub extends Binder implements IIntelligentBatterySaverService {
    static final int TRANSACTION_addSqdBlockList = 4;
    static final int TRANSACTION_dexoptPackages = 15;
    static final int TRANSACTION_getGain = 7;
    static final int TRANSACTION_getOperationHistory = 13;
    static final int TRANSACTION_getSleepTime = 14;
    static final int TRANSACTION_getSqdBlockList = 6;
    static final int TRANSACTION_isEnableSerive = 12;
    static final int TRANSACTION_isSqdSupport = 3;
    static final int TRANSACTION_isSqdUiControlEnabled = 2;
    static final int TRANSACTION_removeSqdBlockList = 5;
    static final int TRANSACTION_setRubinEvent = 11;
    static final int TRANSACTION_setSarrUiControlEnable = 8;
    static final int TRANSACTION_setSleepModeEnabled = 9;
    static final int TRANSACTION_setSleepTime = 10;
    static final int TRANSACTION_setSqdUiControlEnabled = 1;

    public Stub() {
      attachInterface(this, IIntelligentBatterySaverService.DESCRIPTOR);
    }

    public static IIntelligentBatterySaverService asInterface(IBinder obj) {
      if (obj == null) {
        return null;
      }
      IInterface iin = obj.queryLocalInterface(IIntelligentBatterySaverService.DESCRIPTOR);
      if (iin != null && (iin instanceof IIntelligentBatterySaverService)) {
        return (IIntelligentBatterySaverService) iin;
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
          return "setSqdUiControlEnabled";
        case 2:
          return "isSqdUiControlEnabled";
        case 3:
          return "isSqdSupport";
        case 4:
          return "addSqdBlockList";
        case 5:
          return "removeSqdBlockList";
        case 6:
          return "getSqdBlockList";
        case 7:
          return "getGain";
        case 8:
          return "setSarrUiControlEnable";
        case 9:
          return "setSleepModeEnabled";
        case 10:
          return "setSleepTime";
        case 11:
          return "setRubinEvent";
        case 12:
          return "isEnableSerive";
        case 13:
          return "getOperationHistory";
        case 14:
          return "getSleepTime";
        case 15:
          return "dexoptPackages";
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
        data.enforceInterface(IIntelligentBatterySaverService.DESCRIPTOR);
      }
      switch (code) {
        case IBinder.INTERFACE_TRANSACTION /* 1598968902 */:
          reply.writeString(IIntelligentBatterySaverService.DESCRIPTOR);
          return true;
        default:
          switch (code) {
            case 1:
              boolean _arg0 = data.readBoolean();
              data.enforceNoDataAvail();
              setSqdUiControlEnabled(_arg0);
              reply.writeNoException();
              return true;
            case 2:
              boolean _result = isSqdUiControlEnabled();
              reply.writeNoException();
              reply.writeBoolean(_result);
              return true;
            case 3:
              boolean _result2 = isSqdSupport();
              reply.writeNoException();
              reply.writeBoolean(_result2);
              return true;
            case 4:
              int _arg02 = data.readInt();
              String _arg1 = data.readString();
              data.enforceNoDataAvail();
              boolean _result3 = addSqdBlockList(_arg02, _arg1);
              reply.writeNoException();
              reply.writeBoolean(_result3);
              return true;
            case 5:
              int _arg03 = data.readInt();
              String _arg12 = data.readString();
              data.enforceNoDataAvail();
              boolean _result4 = removeSqdBlockList(_arg03, _arg12);
              reply.writeNoException();
              reply.writeBoolean(_result4);
              return true;
            case 6:
              Map _result5 = getSqdBlockList();
              reply.writeNoException();
              reply.writeMap(_result5);
              return true;
            case 7:
              long[] _result6 = getGain();
              reply.writeNoException();
              reply.writeLongArray(_result6);
              return true;
            case 8:
              boolean _arg04 = data.readBoolean();
              data.enforceNoDataAvail();
              setSarrUiControlEnable(_arg04);
              reply.writeNoException();
              return true;
            case 9:
              boolean _arg05 = data.readBoolean();
              data.enforceNoDataAvail();
              setSleepModeEnabled(_arg05);
              reply.writeNoException();
              return true;
            case 10:
              long _arg06 = data.readLong();
              long _arg13 = data.readLong();
              data.enforceNoDataAvail();
              setSleepTime(_arg06, _arg13);
              reply.writeNoException();
              return true;
            case 11:
              String _arg07 = data.readString();
              data.enforceNoDataAvail();
              setRubinEvent(_arg07);
              reply.writeNoException();
              return true;
            case 12:
              boolean _result7 = isEnableSerive();
              reply.writeNoException();
              reply.writeBoolean(_result7);
              return true;
            case 13:
              Bundle _result8 = getOperationHistory();
              reply.writeNoException();
              reply.writeTypedObject(_result8, 1);
              return true;
            case 14:
              Bundle _result9 = getSleepTime();
              reply.writeNoException();
              reply.writeTypedObject(_result9, 1);
              return true;
            case 15:
              List<String> _arg08 = data.createStringArrayList();
              data.enforceNoDataAvail();
              List _result10 = dexoptPackages(_arg08);
              reply.writeNoException();
              reply.writeList(_result10);
              return true;
            default:
              return super.onTransact(code, data, reply, flags);
          }
      }
    }

    private static class Proxy implements IIntelligentBatterySaverService {
      private IBinder mRemote;

      Proxy(IBinder remote) {
        this.mRemote = remote;
      }

      @Override // android.os.IInterface
      public IBinder asBinder() {
        return this.mRemote;
      }

      public String getInterfaceDescriptor() {
        return IIntelligentBatterySaverService.DESCRIPTOR;
      }

      @Override // android.os.IIntelligentBatterySaverService
      public void setSqdUiControlEnabled(boolean allow) throws RemoteException {
        Parcel _data = Parcel.obtain(asBinder());
        Parcel _reply = Parcel.obtain();
        try {
          _data.writeInterfaceToken(IIntelligentBatterySaverService.DESCRIPTOR);
          _data.writeBoolean(allow);
          this.mRemote.transact(1, _data, _reply, 0);
          _reply.readException();
        } finally {
          _reply.recycle();
          _data.recycle();
        }
      }

      @Override // android.os.IIntelligentBatterySaverService
      public boolean isSqdUiControlEnabled() throws RemoteException {
        Parcel _data = Parcel.obtain(asBinder());
        Parcel _reply = Parcel.obtain();
        try {
          _data.writeInterfaceToken(IIntelligentBatterySaverService.DESCRIPTOR);
          this.mRemote.transact(2, _data, _reply, 0);
          _reply.readException();
          boolean _result = _reply.readBoolean();
          return _result;
        } finally {
          _reply.recycle();
          _data.recycle();
        }
      }

      @Override // android.os.IIntelligentBatterySaverService
      public boolean isSqdSupport() throws RemoteException {
        Parcel _data = Parcel.obtain(asBinder());
        Parcel _reply = Parcel.obtain();
        try {
          _data.writeInterfaceToken(IIntelligentBatterySaverService.DESCRIPTOR);
          this.mRemote.transact(3, _data, _reply, 0);
          _reply.readException();
          boolean _result = _reply.readBoolean();
          return _result;
        } finally {
          _reply.recycle();
          _data.recycle();
        }
      }

      @Override // android.os.IIntelligentBatterySaverService
      public boolean addSqdBlockList(int uid, String pkgName) throws RemoteException {
        Parcel _data = Parcel.obtain(asBinder());
        Parcel _reply = Parcel.obtain();
        try {
          _data.writeInterfaceToken(IIntelligentBatterySaverService.DESCRIPTOR);
          _data.writeInt(uid);
          _data.writeString(pkgName);
          this.mRemote.transact(4, _data, _reply, 0);
          _reply.readException();
          boolean _result = _reply.readBoolean();
          return _result;
        } finally {
          _reply.recycle();
          _data.recycle();
        }
      }

      @Override // android.os.IIntelligentBatterySaverService
      public boolean removeSqdBlockList(int uid, String pkgName) throws RemoteException {
        Parcel _data = Parcel.obtain(asBinder());
        Parcel _reply = Parcel.obtain();
        try {
          _data.writeInterfaceToken(IIntelligentBatterySaverService.DESCRIPTOR);
          _data.writeInt(uid);
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

      @Override // android.os.IIntelligentBatterySaverService
      public Map getSqdBlockList() throws RemoteException {
        Parcel _data = Parcel.obtain(asBinder());
        Parcel _reply = Parcel.obtain();
        try {
          _data.writeInterfaceToken(IIntelligentBatterySaverService.DESCRIPTOR);
          this.mRemote.transact(6, _data, _reply, 0);
          _reply.readException();
          ClassLoader cl = getClass().getClassLoader();
          Map _result = _reply.readHashMap(cl);
          return _result;
        } finally {
          _reply.recycle();
          _data.recycle();
        }
      }

      @Override // android.os.IIntelligentBatterySaverService
      public long[] getGain() throws RemoteException {
        Parcel _data = Parcel.obtain(asBinder());
        Parcel _reply = Parcel.obtain();
        try {
          _data.writeInterfaceToken(IIntelligentBatterySaverService.DESCRIPTOR);
          this.mRemote.transact(7, _data, _reply, 0);
          _reply.readException();
          long[] _result = _reply.createLongArray();
          return _result;
        } finally {
          _reply.recycle();
          _data.recycle();
        }
      }

      @Override // android.os.IIntelligentBatterySaverService
      public void setSarrUiControlEnable(boolean allow) throws RemoteException {
        Parcel _data = Parcel.obtain(asBinder());
        Parcel _reply = Parcel.obtain();
        try {
          _data.writeInterfaceToken(IIntelligentBatterySaverService.DESCRIPTOR);
          _data.writeBoolean(allow);
          this.mRemote.transact(8, _data, _reply, 0);
          _reply.readException();
        } finally {
          _reply.recycle();
          _data.recycle();
        }
      }

      @Override // android.os.IIntelligentBatterySaverService
      public void setSleepModeEnabled(boolean allow) throws RemoteException {
        Parcel _data = Parcel.obtain(asBinder());
        Parcel _reply = Parcel.obtain();
        try {
          _data.writeInterfaceToken(IIntelligentBatterySaverService.DESCRIPTOR);
          _data.writeBoolean(allow);
          this.mRemote.transact(9, _data, _reply, 0);
          _reply.readException();
        } finally {
          _reply.recycle();
          _data.recycle();
        }
      }

      @Override // android.os.IIntelligentBatterySaverService
      public void setSleepTime(long startTime, long endTime) throws RemoteException {
        Parcel _data = Parcel.obtain(asBinder());
        Parcel _reply = Parcel.obtain();
        try {
          _data.writeInterfaceToken(IIntelligentBatterySaverService.DESCRIPTOR);
          _data.writeLong(startTime);
          _data.writeLong(endTime);
          this.mRemote.transact(10, _data, _reply, 0);
          _reply.readException();
        } finally {
          _reply.recycle();
          _data.recycle();
        }
      }

      @Override // android.os.IIntelligentBatterySaverService
      public void setRubinEvent(String type) throws RemoteException {
        Parcel _data = Parcel.obtain(asBinder());
        Parcel _reply = Parcel.obtain();
        try {
          _data.writeInterfaceToken(IIntelligentBatterySaverService.DESCRIPTOR);
          _data.writeString(type);
          this.mRemote.transact(11, _data, _reply, 0);
          _reply.readException();
        } finally {
          _reply.recycle();
          _data.recycle();
        }
      }

      @Override // android.os.IIntelligentBatterySaverService
      public boolean isEnableSerive() throws RemoteException {
        Parcel _data = Parcel.obtain(asBinder());
        Parcel _reply = Parcel.obtain();
        try {
          _data.writeInterfaceToken(IIntelligentBatterySaverService.DESCRIPTOR);
          this.mRemote.transact(12, _data, _reply, 0);
          _reply.readException();
          boolean _result = _reply.readBoolean();
          return _result;
        } finally {
          _reply.recycle();
          _data.recycle();
        }
      }

      @Override // android.os.IIntelligentBatterySaverService
      public Bundle getOperationHistory() throws RemoteException {
        Parcel _data = Parcel.obtain(asBinder());
        Parcel _reply = Parcel.obtain();
        try {
          _data.writeInterfaceToken(IIntelligentBatterySaverService.DESCRIPTOR);
          this.mRemote.transact(13, _data, _reply, 0);
          _reply.readException();
          Bundle _result = (Bundle) _reply.readTypedObject(Bundle.CREATOR);
          return _result;
        } finally {
          _reply.recycle();
          _data.recycle();
        }
      }

      @Override // android.os.IIntelligentBatterySaverService
      public Bundle getSleepTime() throws RemoteException {
        Parcel _data = Parcel.obtain(asBinder());
        Parcel _reply = Parcel.obtain();
        try {
          _data.writeInterfaceToken(IIntelligentBatterySaverService.DESCRIPTOR);
          this.mRemote.transact(14, _data, _reply, 0);
          _reply.readException();
          Bundle _result = (Bundle) _reply.readTypedObject(Bundle.CREATOR);
          return _result;
        } finally {
          _reply.recycle();
          _data.recycle();
        }
      }

      @Override // android.os.IIntelligentBatterySaverService
      public List dexoptPackages(List<String> pkgNames) throws RemoteException {
        Parcel _data = Parcel.obtain(asBinder());
        Parcel _reply = Parcel.obtain();
        try {
          _data.writeInterfaceToken(IIntelligentBatterySaverService.DESCRIPTOR);
          _data.writeStringList(pkgNames);
          this.mRemote.transact(15, _data, _reply, 0);
          _reply.readException();
          ClassLoader cl = getClass().getClassLoader();
          List _result = _reply.readArrayList(cl);
          return _result;
        } finally {
          _reply.recycle();
          _data.recycle();
        }
      }
    }

    @Override // android.os.Binder
    public int getMaxTransactionId() {
      return 14;
    }
  }
}
