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

    public static class Default implements IColorDisplayManager {
        @Override // android.os.IInterface
        public IBinder asBinder() {
            return null;
        }

        @Override // android.hardware.display.IColorDisplayManager
        public int getColorMode() throws RemoteException {
            return 0;
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
        public int getNightDisplayColorTemperature() throws RemoteException {
            return 0;
        }

        @Override // android.hardware.display.IColorDisplayManager
        public Time getNightDisplayCustomEndTime() throws RemoteException {
            return null;
        }

        @Override // android.hardware.display.IColorDisplayManager
        public Time getNightDisplayCustomStartTime() throws RemoteException {
            return null;
        }

        @Override // android.hardware.display.IColorDisplayManager
        public float getReduceBrightColorsOffsetFactor() throws RemoteException {
            return 0.0f;
        }

        @Override // android.hardware.display.IColorDisplayManager
        public int getReduceBrightColorsStrength() throws RemoteException {
            return 0;
        }

        @Override // android.hardware.display.IColorDisplayManager
        public int getTransformCapabilities() throws RemoteException {
            return 0;
        }

        @Override // android.hardware.display.IColorDisplayManager
        public boolean isDeviceColorManaged() throws RemoteException {
            return false;
        }

        @Override // android.hardware.display.IColorDisplayManager
        public boolean isDisplayWhiteBalanceEnabled() throws RemoteException {
            return false;
        }

        @Override // android.hardware.display.IColorDisplayManager
        public boolean isNightDisplayActivated() throws RemoteException {
            return false;
        }

        @Override // android.hardware.display.IColorDisplayManager
        public boolean isReduceBrightColorsActivated() throws RemoteException {
            return false;
        }

        @Override // android.hardware.display.IColorDisplayManager
        public boolean isSaturationActivated() throws RemoteException {
            return false;
        }

        @Override // android.hardware.display.IColorDisplayManager
        public boolean setAppSaturationLevel(String str, int i) throws RemoteException {
            return false;
        }

        @Override // android.hardware.display.IColorDisplayManager
        public void setColorMode(int i) throws RemoteException {
        }

        @Override // android.hardware.display.IColorDisplayManager
        public boolean setDisplayWhiteBalanceEnabled(boolean z) throws RemoteException {
            return false;
        }

        @Override // android.hardware.display.IColorDisplayManager
        public boolean setNightDisplayActivated(boolean z) throws RemoteException {
            return false;
        }

        @Override // android.hardware.display.IColorDisplayManager
        public boolean setNightDisplayAutoMode(int i) throws RemoteException {
            return false;
        }

        @Override // android.hardware.display.IColorDisplayManager
        public boolean setNightDisplayColorTemperature(int i) throws RemoteException {
            return false;
        }

        @Override // android.hardware.display.IColorDisplayManager
        public boolean setNightDisplayCustomEndTime(Time time) throws RemoteException {
            return false;
        }

        @Override // android.hardware.display.IColorDisplayManager
        public boolean setNightDisplayCustomStartTime(Time time) throws RemoteException {
            return false;
        }

        @Override // android.hardware.display.IColorDisplayManager
        public boolean setReduceBrightColorsActivated(boolean z) throws RemoteException {
            return false;
        }

        @Override // android.hardware.display.IColorDisplayManager
        public boolean setReduceBrightColorsStrength(int i) throws RemoteException {
            return false;
        }

        @Override // android.hardware.display.IColorDisplayManager
        public boolean setSaturationLevel(int i) throws RemoteException {
            return false;
        }
    }

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

    public static abstract class Stub extends Binder implements IColorDisplayManager {
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

        @Override // android.os.IInterface
        public IBinder asBinder() {
            return this;
        }

        @Override // android.os.Binder
        public int getMaxTransactionId() {
            return 24;
        }

        public Stub(PermissionEnforcer permissionEnforcer) {
            attachInterface(this, IColorDisplayManager.DESCRIPTOR);
            if (permissionEnforcer == null) {
                throw new IllegalArgumentException("enforcer cannot be null");
            }
            this.mEnforcer = permissionEnforcer;
        }

        @Deprecated
        public Stub() {
            this(PermissionEnforcer.fromContext(ActivityThread.currentActivityThread().getSystemContext()));
        }

        public static IColorDisplayManager asInterface(IBinder iBinder) {
            if (iBinder == null) {
                return null;
            }
            IInterface queryLocalInterface = iBinder.queryLocalInterface(IColorDisplayManager.DESCRIPTOR);
            if (queryLocalInterface != null && (queryLocalInterface instanceof IColorDisplayManager)) {
                return (IColorDisplayManager) queryLocalInterface;
            }
            return new Proxy(iBinder);
        }

        public static String getDefaultTransactionName(int i) {
            switch (i) {
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
        public String getTransactionName(int i) {
            return getDefaultTransactionName(i);
        }

        @Override // android.os.Binder
        public boolean onTransact(int i, Parcel parcel, Parcel parcel2, int i2) throws RemoteException {
            if (i >= 1 && i <= 16777215) {
                parcel.enforceInterface(IColorDisplayManager.DESCRIPTOR);
            }
            if (i == 1598968902) {
                parcel2.writeString(IColorDisplayManager.DESCRIPTOR);
                return true;
            }
            switch (i) {
                case 1:
                    boolean isDeviceColorManaged = isDeviceColorManaged();
                    parcel2.writeNoException();
                    parcel2.writeBoolean(isDeviceColorManaged);
                    return true;
                case 2:
                    int readInt = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    boolean saturationLevel = setSaturationLevel(readInt);
                    parcel2.writeNoException();
                    parcel2.writeBoolean(saturationLevel);
                    return true;
                case 3:
                    String readString = parcel.readString();
                    int readInt2 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    boolean appSaturationLevel = setAppSaturationLevel(readString, readInt2);
                    parcel2.writeNoException();
                    parcel2.writeBoolean(appSaturationLevel);
                    return true;
                case 4:
                    boolean isSaturationActivated = isSaturationActivated();
                    parcel2.writeNoException();
                    parcel2.writeBoolean(isSaturationActivated);
                    return true;
                case 5:
                    int transformCapabilities = getTransformCapabilities();
                    parcel2.writeNoException();
                    parcel2.writeInt(transformCapabilities);
                    return true;
                case 6:
                    boolean isNightDisplayActivated = isNightDisplayActivated();
                    parcel2.writeNoException();
                    parcel2.writeBoolean(isNightDisplayActivated);
                    return true;
                case 7:
                    boolean readBoolean = parcel.readBoolean();
                    parcel.enforceNoDataAvail();
                    boolean nightDisplayActivated = setNightDisplayActivated(readBoolean);
                    parcel2.writeNoException();
                    parcel2.writeBoolean(nightDisplayActivated);
                    return true;
                case 8:
                    int nightDisplayColorTemperature = getNightDisplayColorTemperature();
                    parcel2.writeNoException();
                    parcel2.writeInt(nightDisplayColorTemperature);
                    return true;
                case 9:
                    int readInt3 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    boolean nightDisplayColorTemperature2 = setNightDisplayColorTemperature(readInt3);
                    parcel2.writeNoException();
                    parcel2.writeBoolean(nightDisplayColorTemperature2);
                    return true;
                case 10:
                    int nightDisplayAutoMode = getNightDisplayAutoMode();
                    parcel2.writeNoException();
                    parcel2.writeInt(nightDisplayAutoMode);
                    return true;
                case 11:
                    int nightDisplayAutoModeRaw = getNightDisplayAutoModeRaw();
                    parcel2.writeNoException();
                    parcel2.writeInt(nightDisplayAutoModeRaw);
                    return true;
                case 12:
                    int readInt4 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    boolean nightDisplayAutoMode2 = setNightDisplayAutoMode(readInt4);
                    parcel2.writeNoException();
                    parcel2.writeBoolean(nightDisplayAutoMode2);
                    return true;
                case 13:
                    Time nightDisplayCustomStartTime = getNightDisplayCustomStartTime();
                    parcel2.writeNoException();
                    parcel2.writeTypedObject(nightDisplayCustomStartTime, 1);
                    return true;
                case 14:
                    Time time = (Time) parcel.readTypedObject(Time.CREATOR);
                    parcel.enforceNoDataAvail();
                    boolean nightDisplayCustomStartTime2 = setNightDisplayCustomStartTime(time);
                    parcel2.writeNoException();
                    parcel2.writeBoolean(nightDisplayCustomStartTime2);
                    return true;
                case 15:
                    Time nightDisplayCustomEndTime = getNightDisplayCustomEndTime();
                    parcel2.writeNoException();
                    parcel2.writeTypedObject(nightDisplayCustomEndTime, 1);
                    return true;
                case 16:
                    Time time2 = (Time) parcel.readTypedObject(Time.CREATOR);
                    parcel.enforceNoDataAvail();
                    boolean nightDisplayCustomEndTime2 = setNightDisplayCustomEndTime(time2);
                    parcel2.writeNoException();
                    parcel2.writeBoolean(nightDisplayCustomEndTime2);
                    return true;
                case 17:
                    int colorMode = getColorMode();
                    parcel2.writeNoException();
                    parcel2.writeInt(colorMode);
                    return true;
                case 18:
                    int readInt5 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    setColorMode(readInt5);
                    parcel2.writeNoException();
                    return true;
                case 19:
                    boolean isDisplayWhiteBalanceEnabled = isDisplayWhiteBalanceEnabled();
                    parcel2.writeNoException();
                    parcel2.writeBoolean(isDisplayWhiteBalanceEnabled);
                    return true;
                case 20:
                    boolean readBoolean2 = parcel.readBoolean();
                    parcel.enforceNoDataAvail();
                    boolean displayWhiteBalanceEnabled = setDisplayWhiteBalanceEnabled(readBoolean2);
                    parcel2.writeNoException();
                    parcel2.writeBoolean(displayWhiteBalanceEnabled);
                    return true;
                case 21:
                    boolean isReduceBrightColorsActivated = isReduceBrightColorsActivated();
                    parcel2.writeNoException();
                    parcel2.writeBoolean(isReduceBrightColorsActivated);
                    return true;
                case 22:
                    boolean readBoolean3 = parcel.readBoolean();
                    parcel.enforceNoDataAvail();
                    boolean reduceBrightColorsActivated = setReduceBrightColorsActivated(readBoolean3);
                    parcel2.writeNoException();
                    parcel2.writeBoolean(reduceBrightColorsActivated);
                    return true;
                case 23:
                    int reduceBrightColorsStrength = getReduceBrightColorsStrength();
                    parcel2.writeNoException();
                    parcel2.writeInt(reduceBrightColorsStrength);
                    return true;
                case 24:
                    int readInt6 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    boolean reduceBrightColorsStrength2 = setReduceBrightColorsStrength(readInt6);
                    parcel2.writeNoException();
                    parcel2.writeBoolean(reduceBrightColorsStrength2);
                    return true;
                case 25:
                    float reduceBrightColorsOffsetFactor = getReduceBrightColorsOffsetFactor();
                    parcel2.writeNoException();
                    parcel2.writeFloat(reduceBrightColorsOffsetFactor);
                    return true;
                default:
                    return super.onTransact(i, parcel, parcel2, i2);
            }
        }

        private static class Proxy implements IColorDisplayManager {
            private IBinder mRemote;

            Proxy(IBinder iBinder) {
                this.mRemote = iBinder;
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
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(IColorDisplayManager.DESCRIPTOR);
                    this.mRemote.transact(1, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readBoolean();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.hardware.display.IColorDisplayManager
            public boolean setSaturationLevel(int i) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(IColorDisplayManager.DESCRIPTOR);
                    obtain.writeInt(i);
                    this.mRemote.transact(2, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readBoolean();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.hardware.display.IColorDisplayManager
            public boolean setAppSaturationLevel(String str, int i) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(IColorDisplayManager.DESCRIPTOR);
                    obtain.writeString(str);
                    obtain.writeInt(i);
                    this.mRemote.transact(3, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readBoolean();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.hardware.display.IColorDisplayManager
            public boolean isSaturationActivated() throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(IColorDisplayManager.DESCRIPTOR);
                    this.mRemote.transact(4, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readBoolean();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.hardware.display.IColorDisplayManager
            public int getTransformCapabilities() throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(IColorDisplayManager.DESCRIPTOR);
                    this.mRemote.transact(5, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readInt();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.hardware.display.IColorDisplayManager
            public boolean isNightDisplayActivated() throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(IColorDisplayManager.DESCRIPTOR);
                    this.mRemote.transact(6, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readBoolean();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.hardware.display.IColorDisplayManager
            public boolean setNightDisplayActivated(boolean z) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(IColorDisplayManager.DESCRIPTOR);
                    obtain.writeBoolean(z);
                    this.mRemote.transact(7, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readBoolean();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.hardware.display.IColorDisplayManager
            public int getNightDisplayColorTemperature() throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(IColorDisplayManager.DESCRIPTOR);
                    this.mRemote.transact(8, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readInt();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.hardware.display.IColorDisplayManager
            public boolean setNightDisplayColorTemperature(int i) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(IColorDisplayManager.DESCRIPTOR);
                    obtain.writeInt(i);
                    this.mRemote.transact(9, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readBoolean();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.hardware.display.IColorDisplayManager
            public int getNightDisplayAutoMode() throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(IColorDisplayManager.DESCRIPTOR);
                    this.mRemote.transact(10, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readInt();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.hardware.display.IColorDisplayManager
            public int getNightDisplayAutoModeRaw() throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(IColorDisplayManager.DESCRIPTOR);
                    this.mRemote.transact(11, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readInt();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.hardware.display.IColorDisplayManager
            public boolean setNightDisplayAutoMode(int i) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(IColorDisplayManager.DESCRIPTOR);
                    obtain.writeInt(i);
                    this.mRemote.transact(12, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readBoolean();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.hardware.display.IColorDisplayManager
            public Time getNightDisplayCustomStartTime() throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(IColorDisplayManager.DESCRIPTOR);
                    this.mRemote.transact(13, obtain, obtain2, 0);
                    obtain2.readException();
                    return (Time) obtain2.readTypedObject(Time.CREATOR);
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.hardware.display.IColorDisplayManager
            public boolean setNightDisplayCustomStartTime(Time time) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(IColorDisplayManager.DESCRIPTOR);
                    obtain.writeTypedObject(time, 0);
                    this.mRemote.transact(14, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readBoolean();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.hardware.display.IColorDisplayManager
            public Time getNightDisplayCustomEndTime() throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(IColorDisplayManager.DESCRIPTOR);
                    this.mRemote.transact(15, obtain, obtain2, 0);
                    obtain2.readException();
                    return (Time) obtain2.readTypedObject(Time.CREATOR);
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.hardware.display.IColorDisplayManager
            public boolean setNightDisplayCustomEndTime(Time time) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(IColorDisplayManager.DESCRIPTOR);
                    obtain.writeTypedObject(time, 0);
                    this.mRemote.transact(16, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readBoolean();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.hardware.display.IColorDisplayManager
            public int getColorMode() throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(IColorDisplayManager.DESCRIPTOR);
                    this.mRemote.transact(17, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readInt();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.hardware.display.IColorDisplayManager
            public void setColorMode(int i) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(IColorDisplayManager.DESCRIPTOR);
                    obtain.writeInt(i);
                    this.mRemote.transact(18, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.hardware.display.IColorDisplayManager
            public boolean isDisplayWhiteBalanceEnabled() throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(IColorDisplayManager.DESCRIPTOR);
                    this.mRemote.transact(19, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readBoolean();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.hardware.display.IColorDisplayManager
            public boolean setDisplayWhiteBalanceEnabled(boolean z) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(IColorDisplayManager.DESCRIPTOR);
                    obtain.writeBoolean(z);
                    this.mRemote.transact(20, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readBoolean();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.hardware.display.IColorDisplayManager
            public boolean isReduceBrightColorsActivated() throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(IColorDisplayManager.DESCRIPTOR);
                    this.mRemote.transact(21, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readBoolean();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.hardware.display.IColorDisplayManager
            public boolean setReduceBrightColorsActivated(boolean z) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(IColorDisplayManager.DESCRIPTOR);
                    obtain.writeBoolean(z);
                    this.mRemote.transact(22, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readBoolean();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.hardware.display.IColorDisplayManager
            public int getReduceBrightColorsStrength() throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(IColorDisplayManager.DESCRIPTOR);
                    this.mRemote.transact(23, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readInt();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.hardware.display.IColorDisplayManager
            public boolean setReduceBrightColorsStrength(int i) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(IColorDisplayManager.DESCRIPTOR);
                    obtain.writeInt(i);
                    this.mRemote.transact(24, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readBoolean();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.hardware.display.IColorDisplayManager
            public float getReduceBrightColorsOffsetFactor() throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(IColorDisplayManager.DESCRIPTOR);
                    this.mRemote.transact(25, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readFloat();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }
        }

        protected void setAppSaturationLevel_enforcePermission() throws SecurityException {
            this.mEnforcer.enforcePermission(Manifest.permission.CONTROL_DISPLAY_COLOR_TRANSFORMS, getCallingPid(), getCallingUid());
        }

        protected void isSaturationActivated_enforcePermission() throws SecurityException {
            this.mEnforcer.enforcePermission(Manifest.permission.CONTROL_DISPLAY_COLOR_TRANSFORMS, getCallingPid(), getCallingUid());
        }

        protected void getTransformCapabilities_enforcePermission() throws SecurityException {
            this.mEnforcer.enforcePermission(Manifest.permission.CONTROL_DISPLAY_COLOR_TRANSFORMS, getCallingPid(), getCallingUid());
        }

        protected void setNightDisplayActivated_enforcePermission() throws SecurityException {
            this.mEnforcer.enforcePermission(Manifest.permission.CONTROL_DISPLAY_COLOR_TRANSFORMS, getCallingPid(), getCallingUid());
        }

        protected void setNightDisplayColorTemperature_enforcePermission() throws SecurityException {
            this.mEnforcer.enforcePermission(Manifest.permission.CONTROL_DISPLAY_COLOR_TRANSFORMS, getCallingPid(), getCallingUid());
        }

        protected void getNightDisplayAutoMode_enforcePermission() throws SecurityException {
            this.mEnforcer.enforcePermission(Manifest.permission.CONTROL_DISPLAY_COLOR_TRANSFORMS, getCallingPid(), getCallingUid());
        }

        protected void setNightDisplayAutoMode_enforcePermission() throws SecurityException {
            this.mEnforcer.enforcePermission(Manifest.permission.CONTROL_DISPLAY_COLOR_TRANSFORMS, getCallingPid(), getCallingUid());
        }

        protected void setNightDisplayCustomStartTime_enforcePermission() throws SecurityException {
            this.mEnforcer.enforcePermission(Manifest.permission.CONTROL_DISPLAY_COLOR_TRANSFORMS, getCallingPid(), getCallingUid());
        }

        protected void setNightDisplayCustomEndTime_enforcePermission() throws SecurityException {
            this.mEnforcer.enforcePermission(Manifest.permission.CONTROL_DISPLAY_COLOR_TRANSFORMS, getCallingPid(), getCallingUid());
        }

        protected void setColorMode_enforcePermission() throws SecurityException {
            this.mEnforcer.enforcePermission(Manifest.permission.CONTROL_DISPLAY_COLOR_TRANSFORMS, getCallingPid(), getCallingUid());
        }

        protected void setDisplayWhiteBalanceEnabled_enforcePermission() throws SecurityException {
            this.mEnforcer.enforcePermission(Manifest.permission.CONTROL_DISPLAY_COLOR_TRANSFORMS, getCallingPid(), getCallingUid());
        }

        protected void setReduceBrightColorsActivated_enforcePermission() throws SecurityException {
            this.mEnforcer.enforcePermission(Manifest.permission.CONTROL_DISPLAY_COLOR_TRANSFORMS, getCallingPid(), getCallingUid());
        }

        protected void setReduceBrightColorsStrength_enforcePermission() throws SecurityException {
            this.mEnforcer.enforcePermission(Manifest.permission.CONTROL_DISPLAY_COLOR_TRANSFORMS, getCallingPid(), getCallingUid());
        }
    }
}
