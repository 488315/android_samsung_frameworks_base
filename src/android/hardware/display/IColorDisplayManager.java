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
            IInterface iInterfaceQueryLocalInterface = iBinder.queryLocalInterface(IColorDisplayManager.DESCRIPTOR);
            if (iInterfaceQueryLocalInterface != null && (iInterfaceQueryLocalInterface instanceof IColorDisplayManager)) {
                return (IColorDisplayManager) iInterfaceQueryLocalInterface;
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
                    boolean zIsDeviceColorManaged = isDeviceColorManaged();
                    parcel2.writeNoException();
                    parcel2.writeBoolean(zIsDeviceColorManaged);
                    return true;
                case 2:
                    int i3 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    boolean saturationLevel = setSaturationLevel(i3);
                    parcel2.writeNoException();
                    parcel2.writeBoolean(saturationLevel);
                    return true;
                case 3:
                    String string = parcel.readString();
                    int i4 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    boolean appSaturationLevel = setAppSaturationLevel(string, i4);
                    parcel2.writeNoException();
                    parcel2.writeBoolean(appSaturationLevel);
                    return true;
                case 4:
                    boolean zIsSaturationActivated = isSaturationActivated();
                    parcel2.writeNoException();
                    parcel2.writeBoolean(zIsSaturationActivated);
                    return true;
                case 5:
                    int transformCapabilities = getTransformCapabilities();
                    parcel2.writeNoException();
                    parcel2.writeInt(transformCapabilities);
                    return true;
                case 6:
                    boolean zIsNightDisplayActivated = isNightDisplayActivated();
                    parcel2.writeNoException();
                    parcel2.writeBoolean(zIsNightDisplayActivated);
                    return true;
                case 7:
                    boolean z = parcel.readBoolean();
                    parcel.enforceNoDataAvail();
                    boolean nightDisplayActivated = setNightDisplayActivated(z);
                    parcel2.writeNoException();
                    parcel2.writeBoolean(nightDisplayActivated);
                    return true;
                case 8:
                    int nightDisplayColorTemperature = getNightDisplayColorTemperature();
                    parcel2.writeNoException();
                    parcel2.writeInt(nightDisplayColorTemperature);
                    return true;
                case 9:
                    int i5 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    boolean nightDisplayColorTemperature2 = setNightDisplayColorTemperature(i5);
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
                    int i6 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    boolean nightDisplayAutoMode2 = setNightDisplayAutoMode(i6);
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
                    int i7 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    setColorMode(i7);
                    parcel2.writeNoException();
                    return true;
                case 19:
                    boolean zIsDisplayWhiteBalanceEnabled = isDisplayWhiteBalanceEnabled();
                    parcel2.writeNoException();
                    parcel2.writeBoolean(zIsDisplayWhiteBalanceEnabled);
                    return true;
                case 20:
                    boolean z2 = parcel.readBoolean();
                    parcel.enforceNoDataAvail();
                    boolean displayWhiteBalanceEnabled = setDisplayWhiteBalanceEnabled(z2);
                    parcel2.writeNoException();
                    parcel2.writeBoolean(displayWhiteBalanceEnabled);
                    return true;
                case 21:
                    boolean zIsReduceBrightColorsActivated = isReduceBrightColorsActivated();
                    parcel2.writeNoException();
                    parcel2.writeBoolean(zIsReduceBrightColorsActivated);
                    return true;
                case 22:
                    boolean z3 = parcel.readBoolean();
                    parcel.enforceNoDataAvail();
                    boolean reduceBrightColorsActivated = setReduceBrightColorsActivated(z3);
                    parcel2.writeNoException();
                    parcel2.writeBoolean(reduceBrightColorsActivated);
                    return true;
                case 23:
                    int reduceBrightColorsStrength = getReduceBrightColorsStrength();
                    parcel2.writeNoException();
                    parcel2.writeInt(reduceBrightColorsStrength);
                    return true;
                case 24:
                    int i8 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    boolean reduceBrightColorsStrength2 = setReduceBrightColorsStrength(i8);
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
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(IColorDisplayManager.DESCRIPTOR);
                    this.mRemote.transact(1, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return parcelObtain2.readBoolean();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.hardware.display.IColorDisplayManager
            public boolean setSaturationLevel(int i) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(IColorDisplayManager.DESCRIPTOR);
                    parcelObtain.writeInt(i);
                    this.mRemote.transact(2, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return parcelObtain2.readBoolean();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.hardware.display.IColorDisplayManager
            public boolean setAppSaturationLevel(String str, int i) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(IColorDisplayManager.DESCRIPTOR);
                    parcelObtain.writeString(str);
                    parcelObtain.writeInt(i);
                    this.mRemote.transact(3, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return parcelObtain2.readBoolean();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.hardware.display.IColorDisplayManager
            public boolean isSaturationActivated() throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(IColorDisplayManager.DESCRIPTOR);
                    this.mRemote.transact(4, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return parcelObtain2.readBoolean();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.hardware.display.IColorDisplayManager
            public int getTransformCapabilities() throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(IColorDisplayManager.DESCRIPTOR);
                    this.mRemote.transact(5, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return parcelObtain2.readInt();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.hardware.display.IColorDisplayManager
            public boolean isNightDisplayActivated() throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(IColorDisplayManager.DESCRIPTOR);
                    this.mRemote.transact(6, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return parcelObtain2.readBoolean();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.hardware.display.IColorDisplayManager
            public boolean setNightDisplayActivated(boolean z) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(IColorDisplayManager.DESCRIPTOR);
                    parcelObtain.writeBoolean(z);
                    this.mRemote.transact(7, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return parcelObtain2.readBoolean();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.hardware.display.IColorDisplayManager
            public int getNightDisplayColorTemperature() throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(IColorDisplayManager.DESCRIPTOR);
                    this.mRemote.transact(8, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return parcelObtain2.readInt();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.hardware.display.IColorDisplayManager
            public boolean setNightDisplayColorTemperature(int i) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(IColorDisplayManager.DESCRIPTOR);
                    parcelObtain.writeInt(i);
                    this.mRemote.transact(9, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return parcelObtain2.readBoolean();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.hardware.display.IColorDisplayManager
            public int getNightDisplayAutoMode() throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(IColorDisplayManager.DESCRIPTOR);
                    this.mRemote.transact(10, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return parcelObtain2.readInt();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.hardware.display.IColorDisplayManager
            public int getNightDisplayAutoModeRaw() throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(IColorDisplayManager.DESCRIPTOR);
                    this.mRemote.transact(11, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return parcelObtain2.readInt();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.hardware.display.IColorDisplayManager
            public boolean setNightDisplayAutoMode(int i) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(IColorDisplayManager.DESCRIPTOR);
                    parcelObtain.writeInt(i);
                    this.mRemote.transact(12, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return parcelObtain2.readBoolean();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.hardware.display.IColorDisplayManager
            public Time getNightDisplayCustomStartTime() throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(IColorDisplayManager.DESCRIPTOR);
                    this.mRemote.transact(13, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return (Time) parcelObtain2.readTypedObject(Time.CREATOR);
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.hardware.display.IColorDisplayManager
            public boolean setNightDisplayCustomStartTime(Time time) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(IColorDisplayManager.DESCRIPTOR);
                    parcelObtain.writeTypedObject(time, 0);
                    this.mRemote.transact(14, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return parcelObtain2.readBoolean();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.hardware.display.IColorDisplayManager
            public Time getNightDisplayCustomEndTime() throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(IColorDisplayManager.DESCRIPTOR);
                    this.mRemote.transact(15, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return (Time) parcelObtain2.readTypedObject(Time.CREATOR);
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.hardware.display.IColorDisplayManager
            public boolean setNightDisplayCustomEndTime(Time time) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(IColorDisplayManager.DESCRIPTOR);
                    parcelObtain.writeTypedObject(time, 0);
                    this.mRemote.transact(16, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return parcelObtain2.readBoolean();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.hardware.display.IColorDisplayManager
            public int getColorMode() throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(IColorDisplayManager.DESCRIPTOR);
                    this.mRemote.transact(17, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return parcelObtain2.readInt();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.hardware.display.IColorDisplayManager
            public void setColorMode(int i) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(IColorDisplayManager.DESCRIPTOR);
                    parcelObtain.writeInt(i);
                    this.mRemote.transact(18, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.hardware.display.IColorDisplayManager
            public boolean isDisplayWhiteBalanceEnabled() throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(IColorDisplayManager.DESCRIPTOR);
                    this.mRemote.transact(19, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return parcelObtain2.readBoolean();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.hardware.display.IColorDisplayManager
            public boolean setDisplayWhiteBalanceEnabled(boolean z) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(IColorDisplayManager.DESCRIPTOR);
                    parcelObtain.writeBoolean(z);
                    this.mRemote.transact(20, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return parcelObtain2.readBoolean();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.hardware.display.IColorDisplayManager
            public boolean isReduceBrightColorsActivated() throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(IColorDisplayManager.DESCRIPTOR);
                    this.mRemote.transact(21, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return parcelObtain2.readBoolean();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.hardware.display.IColorDisplayManager
            public boolean setReduceBrightColorsActivated(boolean z) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(IColorDisplayManager.DESCRIPTOR);
                    parcelObtain.writeBoolean(z);
                    this.mRemote.transact(22, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return parcelObtain2.readBoolean();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.hardware.display.IColorDisplayManager
            public int getReduceBrightColorsStrength() throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(IColorDisplayManager.DESCRIPTOR);
                    this.mRemote.transact(23, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return parcelObtain2.readInt();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.hardware.display.IColorDisplayManager
            public boolean setReduceBrightColorsStrength(int i) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(IColorDisplayManager.DESCRIPTOR);
                    parcelObtain.writeInt(i);
                    this.mRemote.transact(24, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return parcelObtain2.readBoolean();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.hardware.display.IColorDisplayManager
            public float getReduceBrightColorsOffsetFactor() throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(IColorDisplayManager.DESCRIPTOR);
                    this.mRemote.transact(25, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return parcelObtain2.readFloat();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
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
