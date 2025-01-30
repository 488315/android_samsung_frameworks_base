package android.hardware.display;

import android.Manifest;
import android.app.ActivityThread;
import android.os.Binder;
import android.os.IBinder;
import android.os.IInterface;
import android.os.Parcel;
import android.os.PermissionEnforcer;
import android.os.RemoteException;

/* loaded from: classes2.dex */
public interface IColorDisplayManager extends IInterface {
  public static final String DESCRIPTOR = "android.hardware.display.IColorDisplayManager";

  int getColorMode() throws RemoteException;

  int getNightDisplayAutoMode() throws RemoteException;

  int getNightDisplayAutoModeRaw() throws RemoteException;

  int getNightDisplayColorTemperature() throws RemoteException;

  Time getNightDisplayCustomEndTime() throws RemoteException;

  Time getNightDisplayCustomStartTime() throws RemoteException;

  float getReduceBrightColorsOffsetFactor() throws RemoteException;

  int getReduceBrightColorsStrength() throws RemoteException;

  int getTransformCapabilities() throws RemoteException;

  boolean isDeviceColorManaged() throws RemoteException;

  boolean isDisplayWhiteBalanceEnabled() throws RemoteException;

  boolean isNightDisplayActivated() throws RemoteException;

  boolean isReduceBrightColorsActivated() throws RemoteException;

  boolean isSaturationActivated() throws RemoteException;

  boolean setAppSaturationLevel(String str, int i) throws RemoteException;

  void setColorMode(int i) throws RemoteException;

  boolean setDisplayWhiteBalanceEnabled(boolean z) throws RemoteException;

  boolean setNightDisplayActivated(boolean z) throws RemoteException;

  boolean setNightDisplayAutoMode(int i) throws RemoteException;

  boolean setNightDisplayColorTemperature(int i) throws RemoteException;

  boolean setNightDisplayCustomEndTime(Time time) throws RemoteException;

  boolean setNightDisplayCustomStartTime(Time time) throws RemoteException;

  boolean setReduceBrightColorsActivated(boolean z) throws RemoteException;

  boolean setReduceBrightColorsStrength(int i) throws RemoteException;

  boolean setSaturationLevel(int i) throws RemoteException;

  public static class Default implements IColorDisplayManager {
    @Override // android.hardware.display.IColorDisplayManager
    public boolean isDeviceColorManaged() throws RemoteException {
      return false;
    }

    @Override // android.hardware.display.IColorDisplayManager
    public boolean setSaturationLevel(int saturationLevel) throws RemoteException {
      return false;
    }

    @Override // android.hardware.display.IColorDisplayManager
    public boolean setAppSaturationLevel(String packageName, int saturationLevel)
        throws RemoteException {
      return false;
    }

    @Override // android.hardware.display.IColorDisplayManager
    public boolean isSaturationActivated() throws RemoteException {
      return false;
    }

    @Override // android.hardware.display.IColorDisplayManager
    public int getTransformCapabilities() throws RemoteException {
      return 0;
    }

    @Override // android.hardware.display.IColorDisplayManager
    public boolean isNightDisplayActivated() throws RemoteException {
      return false;
    }

    @Override // android.hardware.display.IColorDisplayManager
    public boolean setNightDisplayActivated(boolean activated) throws RemoteException {
      return false;
    }

    @Override // android.hardware.display.IColorDisplayManager
    public int getNightDisplayColorTemperature() throws RemoteException {
      return 0;
    }

    @Override // android.hardware.display.IColorDisplayManager
    public boolean setNightDisplayColorTemperature(int temperature) throws RemoteException {
      return false;
    }

    @Override // android.hardware.display.IColorDisplayManager
    public int getNightDisplayAutoMode() throws RemoteException {
      return 0;
    }

    @Override // android.hardware.display.IColorDisplayManager
    public int getNightDisplayAutoModeRaw() throws RemoteException {
      return 0;
    }

    @Override // android.hardware.display.IColorDisplayManager
    public boolean setNightDisplayAutoMode(int autoMode) throws RemoteException {
      return false;
    }

    @Override // android.hardware.display.IColorDisplayManager
    public Time getNightDisplayCustomStartTime() throws RemoteException {
      return null;
    }

    @Override // android.hardware.display.IColorDisplayManager
    public boolean setNightDisplayCustomStartTime(Time time) throws RemoteException {
      return false;
    }

    @Override // android.hardware.display.IColorDisplayManager
    public Time getNightDisplayCustomEndTime() throws RemoteException {
      return null;
    }

    @Override // android.hardware.display.IColorDisplayManager
    public boolean setNightDisplayCustomEndTime(Time time) throws RemoteException {
      return false;
    }

    @Override // android.hardware.display.IColorDisplayManager
    public int getColorMode() throws RemoteException {
      return 0;
    }

    @Override // android.hardware.display.IColorDisplayManager
    public void setColorMode(int colorMode) throws RemoteException {}

    @Override // android.hardware.display.IColorDisplayManager
    public boolean isDisplayWhiteBalanceEnabled() throws RemoteException {
      return false;
    }

    @Override // android.hardware.display.IColorDisplayManager
    public boolean setDisplayWhiteBalanceEnabled(boolean enabled) throws RemoteException {
      return false;
    }

    @Override // android.hardware.display.IColorDisplayManager
    public boolean isReduceBrightColorsActivated() throws RemoteException {
      return false;
    }

    @Override // android.hardware.display.IColorDisplayManager
    public boolean setReduceBrightColorsActivated(boolean activated) throws RemoteException {
      return false;
    }

    @Override // android.hardware.display.IColorDisplayManager
    public int getReduceBrightColorsStrength() throws RemoteException {
      return 0;
    }

    @Override // android.hardware.display.IColorDisplayManager
    public boolean setReduceBrightColorsStrength(int strength) throws RemoteException {
      return false;
    }

    @Override // android.hardware.display.IColorDisplayManager
    public float getReduceBrightColorsOffsetFactor() throws RemoteException {
      return 0.0f;
    }

    @Override // android.os.IInterface
    public IBinder asBinder() {
      return null;
    }
  }

  public abstract static class Stub extends Binder implements IColorDisplayManager {
    static final int TRANSACTION_getColorMode = 17;
    static final int TRANSACTION_getNightDisplayAutoMode = 10;
    static final int TRANSACTION_getNightDisplayAutoModeRaw = 11;
    static final int TRANSACTION_getNightDisplayColorTemperature = 8;
    static final int TRANSACTION_getNightDisplayCustomEndTime = 15;
    static final int TRANSACTION_getNightDisplayCustomStartTime = 13;
    static final int TRANSACTION_getReduceBrightColorsOffsetFactor = 25;
    static final int TRANSACTION_getReduceBrightColorsStrength = 23;
    static final int TRANSACTION_getTransformCapabilities = 5;
    static final int TRANSACTION_isDeviceColorManaged = 1;
    static final int TRANSACTION_isDisplayWhiteBalanceEnabled = 19;
    static final int TRANSACTION_isNightDisplayActivated = 6;
    static final int TRANSACTION_isReduceBrightColorsActivated = 21;
    static final int TRANSACTION_isSaturationActivated = 4;
    static final int TRANSACTION_setAppSaturationLevel = 3;
    static final int TRANSACTION_setColorMode = 18;
    static final int TRANSACTION_setDisplayWhiteBalanceEnabled = 20;
    static final int TRANSACTION_setNightDisplayActivated = 7;
    static final int TRANSACTION_setNightDisplayAutoMode = 12;
    static final int TRANSACTION_setNightDisplayColorTemperature = 9;
    static final int TRANSACTION_setNightDisplayCustomEndTime = 16;
    static final int TRANSACTION_setNightDisplayCustomStartTime = 14;
    static final int TRANSACTION_setReduceBrightColorsActivated = 22;
    static final int TRANSACTION_setReduceBrightColorsStrength = 24;
    static final int TRANSACTION_setSaturationLevel = 2;
    private final PermissionEnforcer mEnforcer;

    public Stub(PermissionEnforcer enforcer) {
      attachInterface(this, IColorDisplayManager.DESCRIPTOR);
      if (enforcer == null) {
        throw new IllegalArgumentException("enforcer cannot be null");
      }
      this.mEnforcer = enforcer;
    }

    @Deprecated
    public Stub() {
      this(
          PermissionEnforcer.fromContext(
              ActivityThread.currentActivityThread().getSystemContext()));
    }

    public static IColorDisplayManager asInterface(IBinder obj) {
      if (obj == null) {
        return null;
      }
      IInterface iin = obj.queryLocalInterface(IColorDisplayManager.DESCRIPTOR);
      if (iin != null && (iin instanceof IColorDisplayManager)) {
        return (IColorDisplayManager) iin;
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
          return "isDeviceColorManaged";
        case 2:
          return "setSaturationLevel";
        case 3:
          return "setAppSaturationLevel";
        case 4:
          return "isSaturationActivated";
        case 5:
          return "getTransformCapabilities";
        case 6:
          return "isNightDisplayActivated";
        case 7:
          return "setNightDisplayActivated";
        case 8:
          return "getNightDisplayColorTemperature";
        case 9:
          return "setNightDisplayColorTemperature";
        case 10:
          return "getNightDisplayAutoMode";
        case 11:
          return "getNightDisplayAutoModeRaw";
        case 12:
          return "setNightDisplayAutoMode";
        case 13:
          return "getNightDisplayCustomStartTime";
        case 14:
          return "setNightDisplayCustomStartTime";
        case 15:
          return "getNightDisplayCustomEndTime";
        case 16:
          return "setNightDisplayCustomEndTime";
        case 17:
          return "getColorMode";
        case 18:
          return "setColorMode";
        case 19:
          return "isDisplayWhiteBalanceEnabled";
        case 20:
          return "setDisplayWhiteBalanceEnabled";
        case 21:
          return "isReduceBrightColorsActivated";
        case 22:
          return "setReduceBrightColorsActivated";
        case 23:
          return "getReduceBrightColorsStrength";
        case 24:
          return "setReduceBrightColorsStrength";
        case 25:
          return "getReduceBrightColorsOffsetFactor";
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
        data.enforceInterface(IColorDisplayManager.DESCRIPTOR);
      }
      switch (code) {
        case IBinder.INTERFACE_TRANSACTION /* 1598968902 */:
          reply.writeString(IColorDisplayManager.DESCRIPTOR);
          return true;
        default:
          switch (code) {
            case 1:
              boolean _result = isDeviceColorManaged();
              reply.writeNoException();
              reply.writeBoolean(_result);
              return true;
            case 2:
              int _arg0 = data.readInt();
              data.enforceNoDataAvail();
              boolean _result2 = setSaturationLevel(_arg0);
              reply.writeNoException();
              reply.writeBoolean(_result2);
              return true;
            case 3:
              String _arg02 = data.readString();
              int _arg1 = data.readInt();
              data.enforceNoDataAvail();
              boolean _result3 = setAppSaturationLevel(_arg02, _arg1);
              reply.writeNoException();
              reply.writeBoolean(_result3);
              return true;
            case 4:
              boolean _result4 = isSaturationActivated();
              reply.writeNoException();
              reply.writeBoolean(_result4);
              return true;
            case 5:
              int _result5 = getTransformCapabilities();
              reply.writeNoException();
              reply.writeInt(_result5);
              return true;
            case 6:
              boolean _result6 = isNightDisplayActivated();
              reply.writeNoException();
              reply.writeBoolean(_result6);
              return true;
            case 7:
              boolean _arg03 = data.readBoolean();
              data.enforceNoDataAvail();
              boolean _result7 = setNightDisplayActivated(_arg03);
              reply.writeNoException();
              reply.writeBoolean(_result7);
              return true;
            case 8:
              int _result8 = getNightDisplayColorTemperature();
              reply.writeNoException();
              reply.writeInt(_result8);
              return true;
            case 9:
              int _arg04 = data.readInt();
              data.enforceNoDataAvail();
              boolean _result9 = setNightDisplayColorTemperature(_arg04);
              reply.writeNoException();
              reply.writeBoolean(_result9);
              return true;
            case 10:
              int _result10 = getNightDisplayAutoMode();
              reply.writeNoException();
              reply.writeInt(_result10);
              return true;
            case 11:
              int _result11 = getNightDisplayAutoModeRaw();
              reply.writeNoException();
              reply.writeInt(_result11);
              return true;
            case 12:
              int _arg05 = data.readInt();
              data.enforceNoDataAvail();
              boolean _result12 = setNightDisplayAutoMode(_arg05);
              reply.writeNoException();
              reply.writeBoolean(_result12);
              return true;
            case 13:
              Time _result13 = getNightDisplayCustomStartTime();
              reply.writeNoException();
              reply.writeTypedObject(_result13, 1);
              return true;
            case 14:
              Time _arg06 = (Time) data.readTypedObject(Time.CREATOR);
              data.enforceNoDataAvail();
              boolean _result14 = setNightDisplayCustomStartTime(_arg06);
              reply.writeNoException();
              reply.writeBoolean(_result14);
              return true;
            case 15:
              Time _result15 = getNightDisplayCustomEndTime();
              reply.writeNoException();
              reply.writeTypedObject(_result15, 1);
              return true;
            case 16:
              Time _arg07 = (Time) data.readTypedObject(Time.CREATOR);
              data.enforceNoDataAvail();
              boolean _result16 = setNightDisplayCustomEndTime(_arg07);
              reply.writeNoException();
              reply.writeBoolean(_result16);
              return true;
            case 17:
              int _result17 = getColorMode();
              reply.writeNoException();
              reply.writeInt(_result17);
              return true;
            case 18:
              int _arg08 = data.readInt();
              data.enforceNoDataAvail();
              setColorMode(_arg08);
              reply.writeNoException();
              return true;
            case 19:
              boolean _result18 = isDisplayWhiteBalanceEnabled();
              reply.writeNoException();
              reply.writeBoolean(_result18);
              return true;
            case 20:
              boolean _arg09 = data.readBoolean();
              data.enforceNoDataAvail();
              boolean _result19 = setDisplayWhiteBalanceEnabled(_arg09);
              reply.writeNoException();
              reply.writeBoolean(_result19);
              return true;
            case 21:
              boolean _result20 = isReduceBrightColorsActivated();
              reply.writeNoException();
              reply.writeBoolean(_result20);
              return true;
            case 22:
              boolean _arg010 = data.readBoolean();
              data.enforceNoDataAvail();
              boolean _result21 = setReduceBrightColorsActivated(_arg010);
              reply.writeNoException();
              reply.writeBoolean(_result21);
              return true;
            case 23:
              int _result22 = getReduceBrightColorsStrength();
              reply.writeNoException();
              reply.writeInt(_result22);
              return true;
            case 24:
              int _arg011 = data.readInt();
              data.enforceNoDataAvail();
              boolean _result23 = setReduceBrightColorsStrength(_arg011);
              reply.writeNoException();
              reply.writeBoolean(_result23);
              return true;
            case 25:
              float _result24 = getReduceBrightColorsOffsetFactor();
              reply.writeNoException();
              reply.writeFloat(_result24);
              return true;
            default:
              return super.onTransact(code, data, reply, flags);
          }
      }
    }

    private static class Proxy implements IColorDisplayManager {
      private IBinder mRemote;

      Proxy(IBinder remote) {
        this.mRemote = remote;
      }

      @Override // android.os.IInterface
      public IBinder asBinder() {
        return this.mRemote;
      }

      public String getInterfaceDescriptor() {
        return IColorDisplayManager.DESCRIPTOR;
      }

      @Override // android.hardware.display.IColorDisplayManager
      public boolean isDeviceColorManaged() throws RemoteException {
        Parcel _data = Parcel.obtain(asBinder());
        Parcel _reply = Parcel.obtain();
        try {
          _data.writeInterfaceToken(IColorDisplayManager.DESCRIPTOR);
          this.mRemote.transact(1, _data, _reply, 0);
          _reply.readException();
          boolean _result = _reply.readBoolean();
          return _result;
        } finally {
          _reply.recycle();
          _data.recycle();
        }
      }

      @Override // android.hardware.display.IColorDisplayManager
      public boolean setSaturationLevel(int saturationLevel) throws RemoteException {
        Parcel _data = Parcel.obtain(asBinder());
        Parcel _reply = Parcel.obtain();
        try {
          _data.writeInterfaceToken(IColorDisplayManager.DESCRIPTOR);
          _data.writeInt(saturationLevel);
          this.mRemote.transact(2, _data, _reply, 0);
          _reply.readException();
          boolean _result = _reply.readBoolean();
          return _result;
        } finally {
          _reply.recycle();
          _data.recycle();
        }
      }

      @Override // android.hardware.display.IColorDisplayManager
      public boolean setAppSaturationLevel(String packageName, int saturationLevel)
          throws RemoteException {
        Parcel _data = Parcel.obtain(asBinder());
        Parcel _reply = Parcel.obtain();
        try {
          _data.writeInterfaceToken(IColorDisplayManager.DESCRIPTOR);
          _data.writeString(packageName);
          _data.writeInt(saturationLevel);
          this.mRemote.transact(3, _data, _reply, 0);
          _reply.readException();
          boolean _result = _reply.readBoolean();
          return _result;
        } finally {
          _reply.recycle();
          _data.recycle();
        }
      }

      @Override // android.hardware.display.IColorDisplayManager
      public boolean isSaturationActivated() throws RemoteException {
        Parcel _data = Parcel.obtain(asBinder());
        Parcel _reply = Parcel.obtain();
        try {
          _data.writeInterfaceToken(IColorDisplayManager.DESCRIPTOR);
          this.mRemote.transact(4, _data, _reply, 0);
          _reply.readException();
          boolean _result = _reply.readBoolean();
          return _result;
        } finally {
          _reply.recycle();
          _data.recycle();
        }
      }

      @Override // android.hardware.display.IColorDisplayManager
      public int getTransformCapabilities() throws RemoteException {
        Parcel _data = Parcel.obtain(asBinder());
        Parcel _reply = Parcel.obtain();
        try {
          _data.writeInterfaceToken(IColorDisplayManager.DESCRIPTOR);
          this.mRemote.transact(5, _data, _reply, 0);
          _reply.readException();
          int _result = _reply.readInt();
          return _result;
        } finally {
          _reply.recycle();
          _data.recycle();
        }
      }

      @Override // android.hardware.display.IColorDisplayManager
      public boolean isNightDisplayActivated() throws RemoteException {
        Parcel _data = Parcel.obtain(asBinder());
        Parcel _reply = Parcel.obtain();
        try {
          _data.writeInterfaceToken(IColorDisplayManager.DESCRIPTOR);
          this.mRemote.transact(6, _data, _reply, 0);
          _reply.readException();
          boolean _result = _reply.readBoolean();
          return _result;
        } finally {
          _reply.recycle();
          _data.recycle();
        }
      }

      @Override // android.hardware.display.IColorDisplayManager
      public boolean setNightDisplayActivated(boolean activated) throws RemoteException {
        Parcel _data = Parcel.obtain(asBinder());
        Parcel _reply = Parcel.obtain();
        try {
          _data.writeInterfaceToken(IColorDisplayManager.DESCRIPTOR);
          _data.writeBoolean(activated);
          this.mRemote.transact(7, _data, _reply, 0);
          _reply.readException();
          boolean _result = _reply.readBoolean();
          return _result;
        } finally {
          _reply.recycle();
          _data.recycle();
        }
      }

      @Override // android.hardware.display.IColorDisplayManager
      public int getNightDisplayColorTemperature() throws RemoteException {
        Parcel _data = Parcel.obtain(asBinder());
        Parcel _reply = Parcel.obtain();
        try {
          _data.writeInterfaceToken(IColorDisplayManager.DESCRIPTOR);
          this.mRemote.transact(8, _data, _reply, 0);
          _reply.readException();
          int _result = _reply.readInt();
          return _result;
        } finally {
          _reply.recycle();
          _data.recycle();
        }
      }

      @Override // android.hardware.display.IColorDisplayManager
      public boolean setNightDisplayColorTemperature(int temperature) throws RemoteException {
        Parcel _data = Parcel.obtain(asBinder());
        Parcel _reply = Parcel.obtain();
        try {
          _data.writeInterfaceToken(IColorDisplayManager.DESCRIPTOR);
          _data.writeInt(temperature);
          this.mRemote.transact(9, _data, _reply, 0);
          _reply.readException();
          boolean _result = _reply.readBoolean();
          return _result;
        } finally {
          _reply.recycle();
          _data.recycle();
        }
      }

      @Override // android.hardware.display.IColorDisplayManager
      public int getNightDisplayAutoMode() throws RemoteException {
        Parcel _data = Parcel.obtain(asBinder());
        Parcel _reply = Parcel.obtain();
        try {
          _data.writeInterfaceToken(IColorDisplayManager.DESCRIPTOR);
          this.mRemote.transact(10, _data, _reply, 0);
          _reply.readException();
          int _result = _reply.readInt();
          return _result;
        } finally {
          _reply.recycle();
          _data.recycle();
        }
      }

      @Override // android.hardware.display.IColorDisplayManager
      public int getNightDisplayAutoModeRaw() throws RemoteException {
        Parcel _data = Parcel.obtain(asBinder());
        Parcel _reply = Parcel.obtain();
        try {
          _data.writeInterfaceToken(IColorDisplayManager.DESCRIPTOR);
          this.mRemote.transact(11, _data, _reply, 0);
          _reply.readException();
          int _result = _reply.readInt();
          return _result;
        } finally {
          _reply.recycle();
          _data.recycle();
        }
      }

      @Override // android.hardware.display.IColorDisplayManager
      public boolean setNightDisplayAutoMode(int autoMode) throws RemoteException {
        Parcel _data = Parcel.obtain(asBinder());
        Parcel _reply = Parcel.obtain();
        try {
          _data.writeInterfaceToken(IColorDisplayManager.DESCRIPTOR);
          _data.writeInt(autoMode);
          this.mRemote.transact(12, _data, _reply, 0);
          _reply.readException();
          boolean _result = _reply.readBoolean();
          return _result;
        } finally {
          _reply.recycle();
          _data.recycle();
        }
      }

      @Override // android.hardware.display.IColorDisplayManager
      public Time getNightDisplayCustomStartTime() throws RemoteException {
        Parcel _data = Parcel.obtain(asBinder());
        Parcel _reply = Parcel.obtain();
        try {
          _data.writeInterfaceToken(IColorDisplayManager.DESCRIPTOR);
          this.mRemote.transact(13, _data, _reply, 0);
          _reply.readException();
          Time _result = (Time) _reply.readTypedObject(Time.CREATOR);
          return _result;
        } finally {
          _reply.recycle();
          _data.recycle();
        }
      }

      @Override // android.hardware.display.IColorDisplayManager
      public boolean setNightDisplayCustomStartTime(Time time) throws RemoteException {
        Parcel _data = Parcel.obtain(asBinder());
        Parcel _reply = Parcel.obtain();
        try {
          _data.writeInterfaceToken(IColorDisplayManager.DESCRIPTOR);
          _data.writeTypedObject(time, 0);
          this.mRemote.transact(14, _data, _reply, 0);
          _reply.readException();
          boolean _result = _reply.readBoolean();
          return _result;
        } finally {
          _reply.recycle();
          _data.recycle();
        }
      }

      @Override // android.hardware.display.IColorDisplayManager
      public Time getNightDisplayCustomEndTime() throws RemoteException {
        Parcel _data = Parcel.obtain(asBinder());
        Parcel _reply = Parcel.obtain();
        try {
          _data.writeInterfaceToken(IColorDisplayManager.DESCRIPTOR);
          this.mRemote.transact(15, _data, _reply, 0);
          _reply.readException();
          Time _result = (Time) _reply.readTypedObject(Time.CREATOR);
          return _result;
        } finally {
          _reply.recycle();
          _data.recycle();
        }
      }

      @Override // android.hardware.display.IColorDisplayManager
      public boolean setNightDisplayCustomEndTime(Time time) throws RemoteException {
        Parcel _data = Parcel.obtain(asBinder());
        Parcel _reply = Parcel.obtain();
        try {
          _data.writeInterfaceToken(IColorDisplayManager.DESCRIPTOR);
          _data.writeTypedObject(time, 0);
          this.mRemote.transact(16, _data, _reply, 0);
          _reply.readException();
          boolean _result = _reply.readBoolean();
          return _result;
        } finally {
          _reply.recycle();
          _data.recycle();
        }
      }

      @Override // android.hardware.display.IColorDisplayManager
      public int getColorMode() throws RemoteException {
        Parcel _data = Parcel.obtain(asBinder());
        Parcel _reply = Parcel.obtain();
        try {
          _data.writeInterfaceToken(IColorDisplayManager.DESCRIPTOR);
          this.mRemote.transact(17, _data, _reply, 0);
          _reply.readException();
          int _result = _reply.readInt();
          return _result;
        } finally {
          _reply.recycle();
          _data.recycle();
        }
      }

      @Override // android.hardware.display.IColorDisplayManager
      public void setColorMode(int colorMode) throws RemoteException {
        Parcel _data = Parcel.obtain(asBinder());
        Parcel _reply = Parcel.obtain();
        try {
          _data.writeInterfaceToken(IColorDisplayManager.DESCRIPTOR);
          _data.writeInt(colorMode);
          this.mRemote.transact(18, _data, _reply, 0);
          _reply.readException();
        } finally {
          _reply.recycle();
          _data.recycle();
        }
      }

      @Override // android.hardware.display.IColorDisplayManager
      public boolean isDisplayWhiteBalanceEnabled() throws RemoteException {
        Parcel _data = Parcel.obtain(asBinder());
        Parcel _reply = Parcel.obtain();
        try {
          _data.writeInterfaceToken(IColorDisplayManager.DESCRIPTOR);
          this.mRemote.transact(19, _data, _reply, 0);
          _reply.readException();
          boolean _result = _reply.readBoolean();
          return _result;
        } finally {
          _reply.recycle();
          _data.recycle();
        }
      }

      @Override // android.hardware.display.IColorDisplayManager
      public boolean setDisplayWhiteBalanceEnabled(boolean enabled) throws RemoteException {
        Parcel _data = Parcel.obtain(asBinder());
        Parcel _reply = Parcel.obtain();
        try {
          _data.writeInterfaceToken(IColorDisplayManager.DESCRIPTOR);
          _data.writeBoolean(enabled);
          this.mRemote.transact(20, _data, _reply, 0);
          _reply.readException();
          boolean _result = _reply.readBoolean();
          return _result;
        } finally {
          _reply.recycle();
          _data.recycle();
        }
      }

      @Override // android.hardware.display.IColorDisplayManager
      public boolean isReduceBrightColorsActivated() throws RemoteException {
        Parcel _data = Parcel.obtain(asBinder());
        Parcel _reply = Parcel.obtain();
        try {
          _data.writeInterfaceToken(IColorDisplayManager.DESCRIPTOR);
          this.mRemote.transact(21, _data, _reply, 0);
          _reply.readException();
          boolean _result = _reply.readBoolean();
          return _result;
        } finally {
          _reply.recycle();
          _data.recycle();
        }
      }

      @Override // android.hardware.display.IColorDisplayManager
      public boolean setReduceBrightColorsActivated(boolean activated) throws RemoteException {
        Parcel _data = Parcel.obtain(asBinder());
        Parcel _reply = Parcel.obtain();
        try {
          _data.writeInterfaceToken(IColorDisplayManager.DESCRIPTOR);
          _data.writeBoolean(activated);
          this.mRemote.transact(22, _data, _reply, 0);
          _reply.readException();
          boolean _result = _reply.readBoolean();
          return _result;
        } finally {
          _reply.recycle();
          _data.recycle();
        }
      }

      @Override // android.hardware.display.IColorDisplayManager
      public int getReduceBrightColorsStrength() throws RemoteException {
        Parcel _data = Parcel.obtain(asBinder());
        Parcel _reply = Parcel.obtain();
        try {
          _data.writeInterfaceToken(IColorDisplayManager.DESCRIPTOR);
          this.mRemote.transact(23, _data, _reply, 0);
          _reply.readException();
          int _result = _reply.readInt();
          return _result;
        } finally {
          _reply.recycle();
          _data.recycle();
        }
      }

      @Override // android.hardware.display.IColorDisplayManager
      public boolean setReduceBrightColorsStrength(int strength) throws RemoteException {
        Parcel _data = Parcel.obtain(asBinder());
        Parcel _reply = Parcel.obtain();
        try {
          _data.writeInterfaceToken(IColorDisplayManager.DESCRIPTOR);
          _data.writeInt(strength);
          this.mRemote.transact(24, _data, _reply, 0);
          _reply.readException();
          boolean _result = _reply.readBoolean();
          return _result;
        } finally {
          _reply.recycle();
          _data.recycle();
        }
      }

      @Override // android.hardware.display.IColorDisplayManager
      public float getReduceBrightColorsOffsetFactor() throws RemoteException {
        Parcel _data = Parcel.obtain(asBinder());
        Parcel _reply = Parcel.obtain();
        try {
          _data.writeInterfaceToken(IColorDisplayManager.DESCRIPTOR);
          this.mRemote.transact(25, _data, _reply, 0);
          _reply.readException();
          float _result = _reply.readFloat();
          return _result;
        } finally {
          _reply.recycle();
          _data.recycle();
        }
      }
    }

    protected void setAppSaturationLevel_enforcePermission() throws SecurityException {
      this.mEnforcer.enforcePermission(
          Manifest.permission.CONTROL_DISPLAY_COLOR_TRANSFORMS, getCallingPid(), getCallingUid());
    }

    protected void isSaturationActivated_enforcePermission() throws SecurityException {
      this.mEnforcer.enforcePermission(
          Manifest.permission.CONTROL_DISPLAY_COLOR_TRANSFORMS, getCallingPid(), getCallingUid());
    }

    protected void getTransformCapabilities_enforcePermission() throws SecurityException {
      this.mEnforcer.enforcePermission(
          Manifest.permission.CONTROL_DISPLAY_COLOR_TRANSFORMS, getCallingPid(), getCallingUid());
    }

    @Override // android.os.Binder
    public int getMaxTransactionId() {
      return 24;
    }
  }
}
