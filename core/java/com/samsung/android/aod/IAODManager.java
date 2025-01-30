package com.samsung.android.aod;

import android.os.Binder;
import android.os.IBinder;
import android.os.IInterface;
import android.os.Parcel;
import android.os.RemoteException;
import java.util.List;

/* loaded from: classes5.dex */
public interface IAODManager extends IInterface {
  public static final String DESCRIPTOR = "com.samsung.android.aod.IAODManager";

  void acquireDoze(IBinder iBinder, String str, String str2) throws RemoteException;

  void addLogText(List<String> list) throws RemoteException;

  String getActiveImageInfo() throws RemoteException;

  String getAodActiveArea(boolean z) throws RemoteException;

  boolean isAODState() throws RemoteException;

  boolean isSViewCoverBrightnessHigh() throws RemoteException;

  void readyToScreenTurningOn() throws RemoteException;

  void registerAODDozeCallback(IBinder iBinder) throws RemoteException;

  void registerAODListener(IBinder iBinder) throws RemoteException;

  void releaseDoze(IBinder iBinder) throws RemoteException;

  void requestAODToast(String str, AODToast aODToast) throws RemoteException;

  int setLiveClockCommand(int i, int i2, int i3, int[] iArr) throws RemoteException;

  int setLiveClockImage(int i, int i2, byte[] bArr, String str) throws RemoteException;

  int setLiveClockInfo(int i, long j, long j2, long j3, long j4, long j5, long j6, long j7, long j8)
      throws RemoteException;

  void setLiveClockNeedle(byte[] bArr) throws RemoteException;

  void unregisterAODDozeCallback(IBinder iBinder) throws RemoteException;

  void unregisterAODListener(IBinder iBinder) throws RemoteException;

  void updateAODTspRect(int i, int i2, int i3, int i4, String str) throws RemoteException;

  void writeAODCommand(String str, String str2, String str3, String str4, String str5)
      throws RemoteException;

  public static class Default implements IAODManager {
    @Override // com.samsung.android.aod.IAODManager
    public boolean isAODState() throws RemoteException {
      return false;
    }

    @Override // com.samsung.android.aod.IAODManager
    public void updateAODTspRect(int width, int height, int x, int y, String packageName)
        throws RemoteException {}

    @Override // com.samsung.android.aod.IAODManager
    public void writeAODCommand(String location, String cmd, String arg1, String arg2, String arg3)
        throws RemoteException {}

    @Override // com.samsung.android.aod.IAODManager
    public void addLogText(List<String> logs) throws RemoteException {}

    @Override // com.samsung.android.aod.IAODManager
    public int setLiveClockInfo(
        int type,
        long en,
        long interval,
        long hour,
        long min,
        long second,
        long ms,
        long pos_x,
        long pos_y)
        throws RemoteException {
      return 0;
    }

    @Override // com.samsung.android.aod.IAODManager
    public void setLiveClockNeedle(byte[] img_buf) throws RemoteException {}

    @Override // com.samsung.android.aod.IAODManager
    public String getActiveImageInfo() throws RemoteException {
      return null;
    }

    @Override // com.samsung.android.aod.IAODManager
    public void readyToScreenTurningOn() throws RemoteException {}

    @Override // com.samsung.android.aod.IAODManager
    public void registerAODListener(IBinder binder) throws RemoteException {}

    @Override // com.samsung.android.aod.IAODManager
    public void unregisterAODListener(IBinder binder) throws RemoteException {}

    @Override // com.samsung.android.aod.IAODManager
    public void registerAODDozeCallback(IBinder binder) throws RemoteException {}

    @Override // com.samsung.android.aod.IAODManager
    public void unregisterAODDozeCallback(IBinder binder) throws RemoteException {}

    @Override // com.samsung.android.aod.IAODManager
    public void acquireDoze(IBinder binder, String tag, String packageName)
        throws RemoteException {}

    @Override // com.samsung.android.aod.IAODManager
    public void releaseDoze(IBinder binder) throws RemoteException {}

    @Override // com.samsung.android.aod.IAODManager
    public void requestAODToast(String packageName, AODToast toast) throws RemoteException {}

    @Override // com.samsung.android.aod.IAODManager
    public boolean isSViewCoverBrightnessHigh() throws RemoteException {
      return false;
    }

    @Override // com.samsung.android.aod.IAODManager
    public int setLiveClockImage(int nodeType, int clockType, byte[] img_buf, String info)
        throws RemoteException {
      return 0;
    }

    @Override // com.samsung.android.aod.IAODManager
    public int setLiveClockCommand(int nodeType, int cmd, int dataSize, int[] dataArray)
        throws RemoteException {
      return 0;
    }

    @Override // com.samsung.android.aod.IAODManager
    public String getAodActiveArea(boolean isSubDisplay) throws RemoteException {
      return null;
    }

    @Override // android.os.IInterface
    public IBinder asBinder() {
      return null;
    }
  }

  public abstract static class Stub extends Binder implements IAODManager {
    static final int TRANSACTION_acquireDoze = 13;
    static final int TRANSACTION_addLogText = 4;
    static final int TRANSACTION_getActiveImageInfo = 7;
    static final int TRANSACTION_getAodActiveArea = 19;
    static final int TRANSACTION_isAODState = 1;
    static final int TRANSACTION_isSViewCoverBrightnessHigh = 16;
    static final int TRANSACTION_readyToScreenTurningOn = 8;
    static final int TRANSACTION_registerAODDozeCallback = 11;
    static final int TRANSACTION_registerAODListener = 9;
    static final int TRANSACTION_releaseDoze = 14;
    static final int TRANSACTION_requestAODToast = 15;
    static final int TRANSACTION_setLiveClockCommand = 18;
    static final int TRANSACTION_setLiveClockImage = 17;
    static final int TRANSACTION_setLiveClockInfo = 5;
    static final int TRANSACTION_setLiveClockNeedle = 6;
    static final int TRANSACTION_unregisterAODDozeCallback = 12;
    static final int TRANSACTION_unregisterAODListener = 10;
    static final int TRANSACTION_updateAODTspRect = 2;
    static final int TRANSACTION_writeAODCommand = 3;

    public Stub() {
      attachInterface(this, IAODManager.DESCRIPTOR);
    }

    public static IAODManager asInterface(IBinder obj) {
      if (obj == null) {
        return null;
      }
      IInterface iin = obj.queryLocalInterface(IAODManager.DESCRIPTOR);
      if (iin != null && (iin instanceof IAODManager)) {
        return (IAODManager) iin;
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
          return "isAODState";
        case 2:
          return "updateAODTspRect";
        case 3:
          return "writeAODCommand";
        case 4:
          return "addLogText";
        case 5:
          return "setLiveClockInfo";
        case 6:
          return "setLiveClockNeedle";
        case 7:
          return "getActiveImageInfo";
        case 8:
          return "readyToScreenTurningOn";
        case 9:
          return "registerAODListener";
        case 10:
          return "unregisterAODListener";
        case 11:
          return "registerAODDozeCallback";
        case 12:
          return "unregisterAODDozeCallback";
        case 13:
          return "acquireDoze";
        case 14:
          return "releaseDoze";
        case 15:
          return "requestAODToast";
        case 16:
          return "isSViewCoverBrightnessHigh";
        case 17:
          return "setLiveClockImage";
        case 18:
          return "setLiveClockCommand";
        case 19:
          return "getAodActiveArea";
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
        data.enforceInterface(IAODManager.DESCRIPTOR);
      }
      switch (code) {
        case IBinder.INTERFACE_TRANSACTION /* 1598968902 */:
          reply.writeString(IAODManager.DESCRIPTOR);
          return true;
        default:
          switch (code) {
            case 1:
              boolean _result = isAODState();
              reply.writeNoException();
              reply.writeBoolean(_result);
              return true;
            case 2:
              int _arg0 = data.readInt();
              int _arg1 = data.readInt();
              int _arg2 = data.readInt();
              int _arg3 = data.readInt();
              String _arg4 = data.readString();
              data.enforceNoDataAvail();
              updateAODTspRect(_arg0, _arg1, _arg2, _arg3, _arg4);
              reply.writeNoException();
              return true;
            case 3:
              String _arg02 = data.readString();
              String _arg12 = data.readString();
              String _arg22 = data.readString();
              String _arg32 = data.readString();
              String _arg42 = data.readString();
              data.enforceNoDataAvail();
              writeAODCommand(_arg02, _arg12, _arg22, _arg32, _arg42);
              reply.writeNoException();
              return true;
            case 4:
              List<String> _arg03 = data.createStringArrayList();
              data.enforceNoDataAvail();
              addLogText(_arg03);
              reply.writeNoException();
              return true;
            case 5:
              int _arg04 = data.readInt();
              long _arg13 = data.readLong();
              long _arg23 = data.readLong();
              long _arg33 = data.readLong();
              long _arg43 = data.readLong();
              long _arg5 = data.readLong();
              long _arg6 = data.readLong();
              long _arg7 = data.readLong();
              long _arg8 = data.readLong();
              data.enforceNoDataAvail();
              int _result2 =
                  setLiveClockInfo(
                      _arg04, _arg13, _arg23, _arg33, _arg43, _arg5, _arg6, _arg7, _arg8);
              reply.writeNoException();
              reply.writeInt(_result2);
              return true;
            case 6:
              byte[] _arg05 = data.createByteArray();
              data.enforceNoDataAvail();
              setLiveClockNeedle(_arg05);
              reply.writeNoException();
              return true;
            case 7:
              String _result3 = getActiveImageInfo();
              reply.writeNoException();
              reply.writeString(_result3);
              return true;
            case 8:
              readyToScreenTurningOn();
              reply.writeNoException();
              return true;
            case 9:
              IBinder _arg06 = data.readStrongBinder();
              data.enforceNoDataAvail();
              registerAODListener(_arg06);
              reply.writeNoException();
              return true;
            case 10:
              IBinder _arg07 = data.readStrongBinder();
              data.enforceNoDataAvail();
              unregisterAODListener(_arg07);
              reply.writeNoException();
              return true;
            case 11:
              IBinder _arg08 = data.readStrongBinder();
              data.enforceNoDataAvail();
              registerAODDozeCallback(_arg08);
              reply.writeNoException();
              return true;
            case 12:
              IBinder _arg09 = data.readStrongBinder();
              data.enforceNoDataAvail();
              unregisterAODDozeCallback(_arg09);
              reply.writeNoException();
              return true;
            case 13:
              IBinder _arg010 = data.readStrongBinder();
              String _arg14 = data.readString();
              String _arg24 = data.readString();
              data.enforceNoDataAvail();
              acquireDoze(_arg010, _arg14, _arg24);
              reply.writeNoException();
              return true;
            case 14:
              IBinder _arg011 = data.readStrongBinder();
              data.enforceNoDataAvail();
              releaseDoze(_arg011);
              reply.writeNoException();
              return true;
            case 15:
              String _arg012 = data.readString();
              AODToast _arg15 = (AODToast) data.readTypedObject(AODToast.CREATOR);
              data.enforceNoDataAvail();
              requestAODToast(_arg012, _arg15);
              reply.writeNoException();
              return true;
            case 16:
              boolean _result4 = isSViewCoverBrightnessHigh();
              reply.writeNoException();
              reply.writeBoolean(_result4);
              return true;
            case 17:
              int _arg013 = data.readInt();
              int _arg16 = data.readInt();
              byte[] _arg25 = data.createByteArray();
              String _arg34 = data.readString();
              data.enforceNoDataAvail();
              int _result5 = setLiveClockImage(_arg013, _arg16, _arg25, _arg34);
              reply.writeNoException();
              reply.writeInt(_result5);
              return true;
            case 18:
              int _arg014 = data.readInt();
              int _arg17 = data.readInt();
              int _arg26 = data.readInt();
              int[] _arg35 = data.createIntArray();
              data.enforceNoDataAvail();
              int _result6 = setLiveClockCommand(_arg014, _arg17, _arg26, _arg35);
              reply.writeNoException();
              reply.writeInt(_result6);
              return true;
            case 19:
              boolean _arg015 = data.readBoolean();
              data.enforceNoDataAvail();
              String _result7 = getAodActiveArea(_arg015);
              reply.writeNoException();
              reply.writeString(_result7);
              return true;
            default:
              return super.onTransact(code, data, reply, flags);
          }
      }
    }

    private static class Proxy implements IAODManager {
      private IBinder mRemote;

      Proxy(IBinder remote) {
        this.mRemote = remote;
      }

      @Override // android.os.IInterface
      public IBinder asBinder() {
        return this.mRemote;
      }

      public String getInterfaceDescriptor() {
        return IAODManager.DESCRIPTOR;
      }

      @Override // com.samsung.android.aod.IAODManager
      public boolean isAODState() throws RemoteException {
        Parcel _data = Parcel.obtain(asBinder());
        Parcel _reply = Parcel.obtain();
        try {
          _data.writeInterfaceToken(IAODManager.DESCRIPTOR);
          this.mRemote.transact(1, _data, _reply, 0);
          _reply.readException();
          boolean _result = _reply.readBoolean();
          return _result;
        } finally {
          _reply.recycle();
          _data.recycle();
        }
      }

      @Override // com.samsung.android.aod.IAODManager
      public void updateAODTspRect(int width, int height, int x, int y, String packageName)
          throws RemoteException {
        Parcel _data = Parcel.obtain(asBinder());
        Parcel _reply = Parcel.obtain();
        try {
          _data.writeInterfaceToken(IAODManager.DESCRIPTOR);
          _data.writeInt(width);
          _data.writeInt(height);
          _data.writeInt(x);
          _data.writeInt(y);
          _data.writeString(packageName);
          this.mRemote.transact(2, _data, _reply, 0);
          _reply.readException();
        } finally {
          _reply.recycle();
          _data.recycle();
        }
      }

      @Override // com.samsung.android.aod.IAODManager
      public void writeAODCommand(
          String location, String cmd, String arg1, String arg2, String arg3)
          throws RemoteException {
        Parcel _data = Parcel.obtain(asBinder());
        Parcel _reply = Parcel.obtain();
        try {
          _data.writeInterfaceToken(IAODManager.DESCRIPTOR);
          _data.writeString(location);
          _data.writeString(cmd);
          _data.writeString(arg1);
          _data.writeString(arg2);
          _data.writeString(arg3);
          this.mRemote.transact(3, _data, _reply, 0);
          _reply.readException();
        } finally {
          _reply.recycle();
          _data.recycle();
        }
      }

      @Override // com.samsung.android.aod.IAODManager
      public void addLogText(List<String> logs) throws RemoteException {
        Parcel _data = Parcel.obtain(asBinder());
        Parcel _reply = Parcel.obtain();
        try {
          _data.writeInterfaceToken(IAODManager.DESCRIPTOR);
          _data.writeStringList(logs);
          this.mRemote.transact(4, _data, _reply, 0);
          _reply.readException();
        } finally {
          _reply.recycle();
          _data.recycle();
        }
      }

      @Override // com.samsung.android.aod.IAODManager
      public int setLiveClockInfo(
          int type,
          long en,
          long interval,
          long hour,
          long min,
          long second,
          long ms,
          long pos_x,
          long pos_y)
          throws RemoteException {
        Parcel _data = Parcel.obtain(asBinder());
        Parcel _reply = Parcel.obtain();
        try {
          _data.writeInterfaceToken(IAODManager.DESCRIPTOR);
          _data.writeInt(type);
          _data.writeLong(en);
          try {
            _data.writeLong(interval);
            try {
              _data.writeLong(hour);
              try {
                _data.writeLong(min);
              } catch (Throwable th) {
                th = th;
                _reply.recycle();
                _data.recycle();
                throw th;
              }
            } catch (Throwable th2) {
              th = th2;
              _reply.recycle();
              _data.recycle();
              throw th;
            }
          } catch (Throwable th3) {
            th = th3;
            _reply.recycle();
            _data.recycle();
            throw th;
          }
          try {
            _data.writeLong(second);
            try {
              _data.writeLong(ms);
              _data.writeLong(pos_x);
              _data.writeLong(pos_y);
              try {
                this.mRemote.transact(5, _data, _reply, 0);
                _reply.readException();
                int _result = _reply.readInt();
                _reply.recycle();
                _data.recycle();
                return _result;
              } catch (Throwable th4) {
                th = th4;
                _reply.recycle();
                _data.recycle();
                throw th;
              }
            } catch (Throwable th5) {
              th = th5;
            }
          } catch (Throwable th6) {
            th = th6;
            _reply.recycle();
            _data.recycle();
            throw th;
          }
        } catch (Throwable th7) {
          th = th7;
        }
      }

      @Override // com.samsung.android.aod.IAODManager
      public void setLiveClockNeedle(byte[] img_buf) throws RemoteException {
        Parcel _data = Parcel.obtain(asBinder());
        Parcel _reply = Parcel.obtain();
        try {
          _data.writeInterfaceToken(IAODManager.DESCRIPTOR);
          _data.writeByteArray(img_buf);
          this.mRemote.transact(6, _data, _reply, 0);
          _reply.readException();
        } finally {
          _reply.recycle();
          _data.recycle();
        }
      }

      @Override // com.samsung.android.aod.IAODManager
      public String getActiveImageInfo() throws RemoteException {
        Parcel _data = Parcel.obtain(asBinder());
        Parcel _reply = Parcel.obtain();
        try {
          _data.writeInterfaceToken(IAODManager.DESCRIPTOR);
          this.mRemote.transact(7, _data, _reply, 0);
          _reply.readException();
          String _result = _reply.readString();
          return _result;
        } finally {
          _reply.recycle();
          _data.recycle();
        }
      }

      @Override // com.samsung.android.aod.IAODManager
      public void readyToScreenTurningOn() throws RemoteException {
        Parcel _data = Parcel.obtain(asBinder());
        Parcel _reply = Parcel.obtain();
        try {
          _data.writeInterfaceToken(IAODManager.DESCRIPTOR);
          this.mRemote.transact(8, _data, _reply, 0);
          _reply.readException();
        } finally {
          _reply.recycle();
          _data.recycle();
        }
      }

      @Override // com.samsung.android.aod.IAODManager
      public void registerAODListener(IBinder binder) throws RemoteException {
        Parcel _data = Parcel.obtain(asBinder());
        Parcel _reply = Parcel.obtain();
        try {
          _data.writeInterfaceToken(IAODManager.DESCRIPTOR);
          _data.writeStrongBinder(binder);
          this.mRemote.transact(9, _data, _reply, 0);
          _reply.readException();
        } finally {
          _reply.recycle();
          _data.recycle();
        }
      }

      @Override // com.samsung.android.aod.IAODManager
      public void unregisterAODListener(IBinder binder) throws RemoteException {
        Parcel _data = Parcel.obtain(asBinder());
        Parcel _reply = Parcel.obtain();
        try {
          _data.writeInterfaceToken(IAODManager.DESCRIPTOR);
          _data.writeStrongBinder(binder);
          this.mRemote.transact(10, _data, _reply, 0);
          _reply.readException();
        } finally {
          _reply.recycle();
          _data.recycle();
        }
      }

      @Override // com.samsung.android.aod.IAODManager
      public void registerAODDozeCallback(IBinder binder) throws RemoteException {
        Parcel _data = Parcel.obtain(asBinder());
        Parcel _reply = Parcel.obtain();
        try {
          _data.writeInterfaceToken(IAODManager.DESCRIPTOR);
          _data.writeStrongBinder(binder);
          this.mRemote.transact(11, _data, _reply, 0);
          _reply.readException();
        } finally {
          _reply.recycle();
          _data.recycle();
        }
      }

      @Override // com.samsung.android.aod.IAODManager
      public void unregisterAODDozeCallback(IBinder binder) throws RemoteException {
        Parcel _data = Parcel.obtain(asBinder());
        Parcel _reply = Parcel.obtain();
        try {
          _data.writeInterfaceToken(IAODManager.DESCRIPTOR);
          _data.writeStrongBinder(binder);
          this.mRemote.transact(12, _data, _reply, 0);
          _reply.readException();
        } finally {
          _reply.recycle();
          _data.recycle();
        }
      }

      @Override // com.samsung.android.aod.IAODManager
      public void acquireDoze(IBinder binder, String tag, String packageName)
          throws RemoteException {
        Parcel _data = Parcel.obtain(asBinder());
        Parcel _reply = Parcel.obtain();
        try {
          _data.writeInterfaceToken(IAODManager.DESCRIPTOR);
          _data.writeStrongBinder(binder);
          _data.writeString(tag);
          _data.writeString(packageName);
          this.mRemote.transact(13, _data, _reply, 0);
          _reply.readException();
        } finally {
          _reply.recycle();
          _data.recycle();
        }
      }

      @Override // com.samsung.android.aod.IAODManager
      public void releaseDoze(IBinder binder) throws RemoteException {
        Parcel _data = Parcel.obtain(asBinder());
        Parcel _reply = Parcel.obtain();
        try {
          _data.writeInterfaceToken(IAODManager.DESCRIPTOR);
          _data.writeStrongBinder(binder);
          this.mRemote.transact(14, _data, _reply, 0);
          _reply.readException();
        } finally {
          _reply.recycle();
          _data.recycle();
        }
      }

      @Override // com.samsung.android.aod.IAODManager
      public void requestAODToast(String packageName, AODToast toast) throws RemoteException {
        Parcel _data = Parcel.obtain(asBinder());
        Parcel _reply = Parcel.obtain();
        try {
          _data.writeInterfaceToken(IAODManager.DESCRIPTOR);
          _data.writeString(packageName);
          _data.writeTypedObject(toast, 0);
          this.mRemote.transact(15, _data, _reply, 0);
          _reply.readException();
        } finally {
          _reply.recycle();
          _data.recycle();
        }
      }

      @Override // com.samsung.android.aod.IAODManager
      public boolean isSViewCoverBrightnessHigh() throws RemoteException {
        Parcel _data = Parcel.obtain(asBinder());
        Parcel _reply = Parcel.obtain();
        try {
          _data.writeInterfaceToken(IAODManager.DESCRIPTOR);
          this.mRemote.transact(16, _data, _reply, 0);
          _reply.readException();
          boolean _result = _reply.readBoolean();
          return _result;
        } finally {
          _reply.recycle();
          _data.recycle();
        }
      }

      @Override // com.samsung.android.aod.IAODManager
      public int setLiveClockImage(int nodeType, int clockType, byte[] img_buf, String info)
          throws RemoteException {
        Parcel _data = Parcel.obtain(asBinder());
        Parcel _reply = Parcel.obtain();
        try {
          _data.writeInterfaceToken(IAODManager.DESCRIPTOR);
          _data.writeInt(nodeType);
          _data.writeInt(clockType);
          _data.writeByteArray(img_buf);
          _data.writeString(info);
          this.mRemote.transact(17, _data, _reply, 0);
          _reply.readException();
          int _result = _reply.readInt();
          return _result;
        } finally {
          _reply.recycle();
          _data.recycle();
        }
      }

      @Override // com.samsung.android.aod.IAODManager
      public int setLiveClockCommand(int nodeType, int cmd, int dataSize, int[] dataArray)
          throws RemoteException {
        Parcel _data = Parcel.obtain(asBinder());
        Parcel _reply = Parcel.obtain();
        try {
          _data.writeInterfaceToken(IAODManager.DESCRIPTOR);
          _data.writeInt(nodeType);
          _data.writeInt(cmd);
          _data.writeInt(dataSize);
          _data.writeIntArray(dataArray);
          this.mRemote.transact(18, _data, _reply, 0);
          _reply.readException();
          int _result = _reply.readInt();
          return _result;
        } finally {
          _reply.recycle();
          _data.recycle();
        }
      }

      @Override // com.samsung.android.aod.IAODManager
      public String getAodActiveArea(boolean isSubDisplay) throws RemoteException {
        Parcel _data = Parcel.obtain(asBinder());
        Parcel _reply = Parcel.obtain();
        try {
          _data.writeInterfaceToken(IAODManager.DESCRIPTOR);
          _data.writeBoolean(isSubDisplay);
          this.mRemote.transact(19, _data, _reply, 0);
          _reply.readException();
          String _result = _reply.readString();
          return _result;
        } finally {
          _reply.recycle();
          _data.recycle();
        }
      }
    }

    @Override // android.os.Binder
    public int getMaxTransactionId() {
      return 18;
    }
  }
}
