package android.location;

import android.os.Binder;
import android.os.Bundle;
import android.os.IBinder;
import android.os.IInterface;
import android.os.Parcel;
import android.os.RemoteException;
import java.util.List;

/* loaded from: classes2.dex */
public interface INSLocationCallback extends IInterface {
  public static final String DESCRIPTOR = "android.location.INSLocationCallback";

  Bundle getActiveRequests(String str) throws RemoteException;

  int getCrashCount() throws RemoteException;

  long getCrashTime() throws RemoteException;

  Bundle getUidState(int i, int i2) throws RemoteException;

  boolean isLocationEnabled(int i) throws RemoteException;

  boolean isProviderEnabled(String str, int i) throws RemoteException;

  void noteGpsOp(int i, int i2) throws RemoteException;

  void notifyCrash(long j) throws RemoteException;

  void registerDeviceActivityDetector(int i, int i2, boolean z) throws RemoteException;

  void requestToUpdateDeviceActivityDetector() throws RemoteException;

  void sendLogToHqm(String str, String str2, String str3) throws RemoteException;

  boolean setFeatureDeviceActivity(boolean z) throws RemoteException;

  void setMotionPowerSaveMode(boolean z) throws RemoteException;

  void unregisterDeviceActivityDetector() throws RemoteException;

  void updateBackgroundThrottlingAllowlist(List<String> list) throws RemoteException;

  void writeUtLog(int i, String str, String str2) throws RemoteException;

  public static class Default implements INSLocationCallback {
    @Override // android.location.INSLocationCallback
    public void updateBackgroundThrottlingAllowlist(List<String> packageList)
        throws RemoteException {}

    @Override // android.location.INSLocationCallback
    public Bundle getActiveRequests(String provider) throws RemoteException {
      return null;
    }

    @Override // android.location.INSLocationCallback
    public boolean isLocationEnabled(int userId) throws RemoteException {
      return false;
    }

    @Override // android.location.INSLocationCallback
    public void noteGpsOp(int mode, int uid) throws RemoteException {}

    @Override // android.location.INSLocationCallback
    public void sendLogToHqm(String feature, String devLogData, String logData)
        throws RemoteException {}

    @Override // android.location.INSLocationCallback
    public void registerDeviceActivityDetector(int mode, int duration, boolean requestToUpdate)
        throws RemoteException {}

    @Override // android.location.INSLocationCallback
    public void unregisterDeviceActivityDetector() throws RemoteException {}

    @Override // android.location.INSLocationCallback
    public void requestToUpdateDeviceActivityDetector() throws RemoteException {}

    @Override // android.location.INSLocationCallback
    public boolean setFeatureDeviceActivity(boolean flag) throws RemoteException {
      return false;
    }

    @Override // android.location.INSLocationCallback
    public Bundle getUidState(int uid, int pid) throws RemoteException {
      return null;
    }

    @Override // android.location.INSLocationCallback
    public void setMotionPowerSaveMode(boolean flag) throws RemoteException {}

    @Override // android.location.INSLocationCallback
    public boolean isProviderEnabled(String provider, int userId) throws RemoteException {
      return false;
    }

    @Override // android.location.INSLocationCallback
    public void writeUtLog(int type, String path, String data) throws RemoteException {}

    @Override // android.location.INSLocationCallback
    public void notifyCrash(long time) throws RemoteException {}

    @Override // android.location.INSLocationCallback
    public int getCrashCount() throws RemoteException {
      return 0;
    }

    @Override // android.location.INSLocationCallback
    public long getCrashTime() throws RemoteException {
      return 0L;
    }

    @Override // android.os.IInterface
    public IBinder asBinder() {
      return null;
    }
  }

  public abstract static class Stub extends Binder implements INSLocationCallback {
    static final int TRANSACTION_getActiveRequests = 2;
    static final int TRANSACTION_getCrashCount = 15;
    static final int TRANSACTION_getCrashTime = 16;
    static final int TRANSACTION_getUidState = 10;
    static final int TRANSACTION_isLocationEnabled = 3;
    static final int TRANSACTION_isProviderEnabled = 12;
    static final int TRANSACTION_noteGpsOp = 4;
    static final int TRANSACTION_notifyCrash = 14;
    static final int TRANSACTION_registerDeviceActivityDetector = 6;
    static final int TRANSACTION_requestToUpdateDeviceActivityDetector = 8;
    static final int TRANSACTION_sendLogToHqm = 5;
    static final int TRANSACTION_setFeatureDeviceActivity = 9;
    static final int TRANSACTION_setMotionPowerSaveMode = 11;
    static final int TRANSACTION_unregisterDeviceActivityDetector = 7;
    static final int TRANSACTION_updateBackgroundThrottlingAllowlist = 1;
    static final int TRANSACTION_writeUtLog = 13;

    public Stub() {
      attachInterface(this, INSLocationCallback.DESCRIPTOR);
    }

    public static INSLocationCallback asInterface(IBinder obj) {
      if (obj == null) {
        return null;
      }
      IInterface iin = obj.queryLocalInterface(INSLocationCallback.DESCRIPTOR);
      if (iin != null && (iin instanceof INSLocationCallback)) {
        return (INSLocationCallback) iin;
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
          return "updateBackgroundThrottlingAllowlist";
        case 2:
          return "getActiveRequests";
        case 3:
          return "isLocationEnabled";
        case 4:
          return "noteGpsOp";
        case 5:
          return "sendLogToHqm";
        case 6:
          return "registerDeviceActivityDetector";
        case 7:
          return "unregisterDeviceActivityDetector";
        case 8:
          return "requestToUpdateDeviceActivityDetector";
        case 9:
          return "setFeatureDeviceActivity";
        case 10:
          return "getUidState";
        case 11:
          return "setMotionPowerSaveMode";
        case 12:
          return "isProviderEnabled";
        case 13:
          return "writeUtLog";
        case 14:
          return "notifyCrash";
        case 15:
          return "getCrashCount";
        case 16:
          return "getCrashTime";
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
        data.enforceInterface(INSLocationCallback.DESCRIPTOR);
      }
      switch (code) {
        case IBinder.INTERFACE_TRANSACTION /* 1598968902 */:
          reply.writeString(INSLocationCallback.DESCRIPTOR);
          return true;
        default:
          switch (code) {
            case 1:
              List<String> _arg0 = data.createStringArrayList();
              data.enforceNoDataAvail();
              updateBackgroundThrottlingAllowlist(_arg0);
              reply.writeNoException();
              return true;
            case 2:
              String _arg02 = data.readString();
              data.enforceNoDataAvail();
              Bundle _result = getActiveRequests(_arg02);
              reply.writeNoException();
              reply.writeTypedObject(_result, 1);
              return true;
            case 3:
              int _arg03 = data.readInt();
              data.enforceNoDataAvail();
              boolean _result2 = isLocationEnabled(_arg03);
              reply.writeNoException();
              reply.writeBoolean(_result2);
              return true;
            case 4:
              int _arg04 = data.readInt();
              int _arg1 = data.readInt();
              data.enforceNoDataAvail();
              noteGpsOp(_arg04, _arg1);
              reply.writeNoException();
              return true;
            case 5:
              String _arg05 = data.readString();
              String _arg12 = data.readString();
              String _arg2 = data.readString();
              data.enforceNoDataAvail();
              sendLogToHqm(_arg05, _arg12, _arg2);
              reply.writeNoException();
              return true;
            case 6:
              int _arg06 = data.readInt();
              int _arg13 = data.readInt();
              boolean _arg22 = data.readBoolean();
              data.enforceNoDataAvail();
              registerDeviceActivityDetector(_arg06, _arg13, _arg22);
              reply.writeNoException();
              return true;
            case 7:
              unregisterDeviceActivityDetector();
              reply.writeNoException();
              return true;
            case 8:
              requestToUpdateDeviceActivityDetector();
              reply.writeNoException();
              return true;
            case 9:
              boolean _arg07 = data.readBoolean();
              data.enforceNoDataAvail();
              boolean _result3 = setFeatureDeviceActivity(_arg07);
              reply.writeNoException();
              reply.writeBoolean(_result3);
              return true;
            case 10:
              int _arg08 = data.readInt();
              int _arg14 = data.readInt();
              data.enforceNoDataAvail();
              Bundle _result4 = getUidState(_arg08, _arg14);
              reply.writeNoException();
              reply.writeTypedObject(_result4, 1);
              return true;
            case 11:
              boolean _arg09 = data.readBoolean();
              data.enforceNoDataAvail();
              setMotionPowerSaveMode(_arg09);
              reply.writeNoException();
              return true;
            case 12:
              String _arg010 = data.readString();
              int _arg15 = data.readInt();
              data.enforceNoDataAvail();
              boolean _result5 = isProviderEnabled(_arg010, _arg15);
              reply.writeNoException();
              reply.writeBoolean(_result5);
              return true;
            case 13:
              int _arg011 = data.readInt();
              String _arg16 = data.readString();
              String _arg23 = data.readString();
              data.enforceNoDataAvail();
              writeUtLog(_arg011, _arg16, _arg23);
              reply.writeNoException();
              return true;
            case 14:
              long _arg012 = data.readLong();
              data.enforceNoDataAvail();
              notifyCrash(_arg012);
              reply.writeNoException();
              return true;
            case 15:
              int _result6 = getCrashCount();
              reply.writeNoException();
              reply.writeInt(_result6);
              return true;
            case 16:
              long _result7 = getCrashTime();
              reply.writeNoException();
              reply.writeLong(_result7);
              return true;
            default:
              return super.onTransact(code, data, reply, flags);
          }
      }
    }

    private static class Proxy implements INSLocationCallback {
      private IBinder mRemote;

      Proxy(IBinder remote) {
        this.mRemote = remote;
      }

      @Override // android.os.IInterface
      public IBinder asBinder() {
        return this.mRemote;
      }

      public String getInterfaceDescriptor() {
        return INSLocationCallback.DESCRIPTOR;
      }

      @Override // android.location.INSLocationCallback
      public void updateBackgroundThrottlingAllowlist(List<String> packageList)
          throws RemoteException {
        Parcel _data = Parcel.obtain(asBinder());
        Parcel _reply = Parcel.obtain();
        try {
          _data.writeInterfaceToken(INSLocationCallback.DESCRIPTOR);
          _data.writeStringList(packageList);
          this.mRemote.transact(1, _data, _reply, 0);
          _reply.readException();
        } finally {
          _reply.recycle();
          _data.recycle();
        }
      }

      @Override // android.location.INSLocationCallback
      public Bundle getActiveRequests(String provider) throws RemoteException {
        Parcel _data = Parcel.obtain(asBinder());
        Parcel _reply = Parcel.obtain();
        try {
          _data.writeInterfaceToken(INSLocationCallback.DESCRIPTOR);
          _data.writeString(provider);
          this.mRemote.transact(2, _data, _reply, 0);
          _reply.readException();
          Bundle _result = (Bundle) _reply.readTypedObject(Bundle.CREATOR);
          return _result;
        } finally {
          _reply.recycle();
          _data.recycle();
        }
      }

      @Override // android.location.INSLocationCallback
      public boolean isLocationEnabled(int userId) throws RemoteException {
        Parcel _data = Parcel.obtain(asBinder());
        Parcel _reply = Parcel.obtain();
        try {
          _data.writeInterfaceToken(INSLocationCallback.DESCRIPTOR);
          _data.writeInt(userId);
          this.mRemote.transact(3, _data, _reply, 0);
          _reply.readException();
          boolean _result = _reply.readBoolean();
          return _result;
        } finally {
          _reply.recycle();
          _data.recycle();
        }
      }

      @Override // android.location.INSLocationCallback
      public void noteGpsOp(int mode, int uid) throws RemoteException {
        Parcel _data = Parcel.obtain(asBinder());
        Parcel _reply = Parcel.obtain();
        try {
          _data.writeInterfaceToken(INSLocationCallback.DESCRIPTOR);
          _data.writeInt(mode);
          _data.writeInt(uid);
          this.mRemote.transact(4, _data, _reply, 0);
          _reply.readException();
        } finally {
          _reply.recycle();
          _data.recycle();
        }
      }

      @Override // android.location.INSLocationCallback
      public void sendLogToHqm(String feature, String devLogData, String logData)
          throws RemoteException {
        Parcel _data = Parcel.obtain(asBinder());
        Parcel _reply = Parcel.obtain();
        try {
          _data.writeInterfaceToken(INSLocationCallback.DESCRIPTOR);
          _data.writeString(feature);
          _data.writeString(devLogData);
          _data.writeString(logData);
          this.mRemote.transact(5, _data, _reply, 0);
          _reply.readException();
        } finally {
          _reply.recycle();
          _data.recycle();
        }
      }

      @Override // android.location.INSLocationCallback
      public void registerDeviceActivityDetector(int mode, int duration, boolean requestToUpdate)
          throws RemoteException {
        Parcel _data = Parcel.obtain(asBinder());
        Parcel _reply = Parcel.obtain();
        try {
          _data.writeInterfaceToken(INSLocationCallback.DESCRIPTOR);
          _data.writeInt(mode);
          _data.writeInt(duration);
          _data.writeBoolean(requestToUpdate);
          this.mRemote.transact(6, _data, _reply, 0);
          _reply.readException();
        } finally {
          _reply.recycle();
          _data.recycle();
        }
      }

      @Override // android.location.INSLocationCallback
      public void unregisterDeviceActivityDetector() throws RemoteException {
        Parcel _data = Parcel.obtain(asBinder());
        Parcel _reply = Parcel.obtain();
        try {
          _data.writeInterfaceToken(INSLocationCallback.DESCRIPTOR);
          this.mRemote.transact(7, _data, _reply, 0);
          _reply.readException();
        } finally {
          _reply.recycle();
          _data.recycle();
        }
      }

      @Override // android.location.INSLocationCallback
      public void requestToUpdateDeviceActivityDetector() throws RemoteException {
        Parcel _data = Parcel.obtain(asBinder());
        Parcel _reply = Parcel.obtain();
        try {
          _data.writeInterfaceToken(INSLocationCallback.DESCRIPTOR);
          this.mRemote.transact(8, _data, _reply, 0);
          _reply.readException();
        } finally {
          _reply.recycle();
          _data.recycle();
        }
      }

      @Override // android.location.INSLocationCallback
      public boolean setFeatureDeviceActivity(boolean flag) throws RemoteException {
        Parcel _data = Parcel.obtain(asBinder());
        Parcel _reply = Parcel.obtain();
        try {
          _data.writeInterfaceToken(INSLocationCallback.DESCRIPTOR);
          _data.writeBoolean(flag);
          this.mRemote.transact(9, _data, _reply, 0);
          _reply.readException();
          boolean _result = _reply.readBoolean();
          return _result;
        } finally {
          _reply.recycle();
          _data.recycle();
        }
      }

      @Override // android.location.INSLocationCallback
      public Bundle getUidState(int uid, int pid) throws RemoteException {
        Parcel _data = Parcel.obtain(asBinder());
        Parcel _reply = Parcel.obtain();
        try {
          _data.writeInterfaceToken(INSLocationCallback.DESCRIPTOR);
          _data.writeInt(uid);
          _data.writeInt(pid);
          this.mRemote.transact(10, _data, _reply, 0);
          _reply.readException();
          Bundle _result = (Bundle) _reply.readTypedObject(Bundle.CREATOR);
          return _result;
        } finally {
          _reply.recycle();
          _data.recycle();
        }
      }

      @Override // android.location.INSLocationCallback
      public void setMotionPowerSaveMode(boolean flag) throws RemoteException {
        Parcel _data = Parcel.obtain(asBinder());
        Parcel _reply = Parcel.obtain();
        try {
          _data.writeInterfaceToken(INSLocationCallback.DESCRIPTOR);
          _data.writeBoolean(flag);
          this.mRemote.transact(11, _data, _reply, 0);
          _reply.readException();
        } finally {
          _reply.recycle();
          _data.recycle();
        }
      }

      @Override // android.location.INSLocationCallback
      public boolean isProviderEnabled(String provider, int userId) throws RemoteException {
        Parcel _data = Parcel.obtain(asBinder());
        Parcel _reply = Parcel.obtain();
        try {
          _data.writeInterfaceToken(INSLocationCallback.DESCRIPTOR);
          _data.writeString(provider);
          _data.writeInt(userId);
          this.mRemote.transact(12, _data, _reply, 0);
          _reply.readException();
          boolean _result = _reply.readBoolean();
          return _result;
        } finally {
          _reply.recycle();
          _data.recycle();
        }
      }

      @Override // android.location.INSLocationCallback
      public void writeUtLog(int type, String path, String data) throws RemoteException {
        Parcel _data = Parcel.obtain(asBinder());
        Parcel _reply = Parcel.obtain();
        try {
          _data.writeInterfaceToken(INSLocationCallback.DESCRIPTOR);
          _data.writeInt(type);
          _data.writeString(path);
          _data.writeString(data);
          this.mRemote.transact(13, _data, _reply, 0);
          _reply.readException();
        } finally {
          _reply.recycle();
          _data.recycle();
        }
      }

      @Override // android.location.INSLocationCallback
      public void notifyCrash(long time) throws RemoteException {
        Parcel _data = Parcel.obtain(asBinder());
        Parcel _reply = Parcel.obtain();
        try {
          _data.writeInterfaceToken(INSLocationCallback.DESCRIPTOR);
          _data.writeLong(time);
          this.mRemote.transact(14, _data, _reply, 0);
          _reply.readException();
        } finally {
          _reply.recycle();
          _data.recycle();
        }
      }

      @Override // android.location.INSLocationCallback
      public int getCrashCount() throws RemoteException {
        Parcel _data = Parcel.obtain(asBinder());
        Parcel _reply = Parcel.obtain();
        try {
          _data.writeInterfaceToken(INSLocationCallback.DESCRIPTOR);
          this.mRemote.transact(15, _data, _reply, 0);
          _reply.readException();
          int _result = _reply.readInt();
          return _result;
        } finally {
          _reply.recycle();
          _data.recycle();
        }
      }

      @Override // android.location.INSLocationCallback
      public long getCrashTime() throws RemoteException {
        Parcel _data = Parcel.obtain(asBinder());
        Parcel _reply = Parcel.obtain();
        try {
          _data.writeInterfaceToken(INSLocationCallback.DESCRIPTOR);
          this.mRemote.transact(16, _data, _reply, 0);
          _reply.readException();
          long _result = _reply.readLong();
          return _result;
        } finally {
          _reply.recycle();
          _data.recycle();
        }
      }
    }

    @Override // android.os.Binder
    public int getMaxTransactionId() {
      return 15;
    }
  }
}
