package com.samsung.android.hardware.secinputdev;

import android.os.Binder;
import android.os.IBinder;
import android.os.IInterface;
import android.os.Parcel;
import android.os.RemoteException;
import com.samsung.android.hardware.secinputdev.utils.SemInputConstants;

/* loaded from: classes6.dex */
public interface ISemInputDeviceManager extends IInterface {
    public static final String DESCRIPTOR = "com.samsung.android.hardware.secinputdev.ISemInputDeviceManager";

    public static class Default implements ISemInputDeviceManager {
        @Override // com.samsung.android.hardware.secinputdev.ISemInputDeviceManager
        public int activate(SemInputConstants.Device device, SemInputConstants.DisplayState displayState, boolean z) throws RemoteException {
            return 0;
        }

        @Override // android.os.IInterface
        public IBinder asBinder() {
            return null;
        }

        @Override // com.samsung.android.hardware.secinputdev.ISemInputDeviceManager
        public int enableMotion(String str, boolean z, String str2) throws RemoteException {
            return 0;
        }

        @Override // com.samsung.android.hardware.secinputdev.ISemInputDeviceManager
        public String getCommandList(SemInputConstants.Device device) throws RemoteException {
            return null;
        }

        @Override // com.samsung.android.hardware.secinputdev.ISemInputDeviceManager
        public int getDeviceEnabled(SemInputConstants.Device device) throws RemoteException {
            return 0;
        }

        @Override // com.samsung.android.hardware.secinputdev.ISemInputDeviceManager
        public String getKeyPressStateAll() throws RemoteException {
            return null;
        }

        @Override // com.samsung.android.hardware.secinputdev.ISemInputDeviceManager
        public int getMotionControl(String str, String str2) throws RemoteException {
            return 0;
        }

        @Override // com.samsung.android.hardware.secinputdev.ISemInputDeviceManager
        public String getProperty(SemInputConstants.Device device, SemInputConstants.Property property) throws RemoteException {
            return null;
        }

        @Override // com.samsung.android.hardware.secinputdev.ISemInputDeviceManager
        public int getSupportDevice(SemInputConstants.Device device) throws RemoteException {
            return 0;
        }

        @Override // com.samsung.android.hardware.secinputdev.ISemInputDeviceManager
        public int getTspSupportFeature(SemInputConstants.Device device) throws RemoteException {
            return 0;
        }

        @Override // com.samsung.android.hardware.secinputdev.ISemInputDeviceManager
        public int isEnableMotion(String str, String str2) throws RemoteException {
            return 0;
        }

        @Override // com.samsung.android.hardware.secinputdev.ISemInputDeviceManager
        public boolean isKeyPressedByKeycode(int i) throws RemoteException {
            return false;
        }

        @Override // com.samsung.android.hardware.secinputdev.ISemInputDeviceManager
        public boolean isSupportMotion(String str) throws RemoteException {
            return false;
        }

        @Override // com.samsung.android.hardware.secinputdev.ISemInputDeviceManager
        public boolean registerListener(IBinder iBinder, int i, String str) throws RemoteException {
            return false;
        }

        @Override // com.samsung.android.hardware.secinputdev.ISemInputDeviceManager
        public String runCommand(SemInputConstants.Device device, String str) throws RemoteException {
            return null;
        }

        @Override // com.samsung.android.hardware.secinputdev.ISemInputDeviceManager
        public int sendRawdataTsp(SemInputConstants.Device device, int[] iArr) throws RemoteException {
            return 0;
        }

        @Override // com.samsung.android.hardware.secinputdev.ISemInputDeviceManager
        public int setAodEnable(int i) throws RemoteException {
            return 0;
        }

        @Override // com.samsung.android.hardware.secinputdev.ISemInputDeviceManager
        public int setAodRect(int i, int i2, int i3, int i4) throws RemoteException {
            return 0;
        }

        @Override // com.samsung.android.hardware.secinputdev.ISemInputDeviceManager
        public int setAotEnable(int i) throws RemoteException {
            return 0;
        }

        @Override // com.samsung.android.hardware.secinputdev.ISemInputDeviceManager
        public int setCommand(SemInputConstants.Device device, SemInputConstants.Command command, String str) throws RemoteException {
            return 0;
        }

        @Override // com.samsung.android.hardware.secinputdev.ISemInputDeviceManager
        public int setFodEnable(int i, int i2, int i3, int i4) throws RemoteException {
            return 0;
        }

        @Override // com.samsung.android.hardware.secinputdev.ISemInputDeviceManager
        public int setFodLpMode(int i) throws RemoteException {
            return 0;
        }

        @Override // com.samsung.android.hardware.secinputdev.ISemInputDeviceManager
        public int setFodRect(int i, int i2, int i3, int i4) throws RemoteException {
            return 0;
        }

        @Override // com.samsung.android.hardware.secinputdev.ISemInputDeviceManager
        public int setMotionControl(String str, int i, String str2) throws RemoteException {
            return 0;
        }

        @Override // com.samsung.android.hardware.secinputdev.ISemInputDeviceManager
        public int setProperty(SemInputConstants.Device device, SemInputConstants.Property property, String str) throws RemoteException {
            return 0;
        }

        @Override // com.samsung.android.hardware.secinputdev.ISemInputDeviceManager
        public int setSingletapEnable(int i) throws RemoteException {
            return 0;
        }

        @Override // com.samsung.android.hardware.secinputdev.ISemInputDeviceManager
        public int setSpenEnabled(int i, int i2, boolean z) throws RemoteException {
            return 0;
        }

        @Override // com.samsung.android.hardware.secinputdev.ISemInputDeviceManager
        public int setSyncChanged(int i) throws RemoteException {
            return 0;
        }

        @Override // com.samsung.android.hardware.secinputdev.ISemInputDeviceManager
        public int setTemperature(int i) throws RemoteException {
            return 0;
        }

        @Override // com.samsung.android.hardware.secinputdev.ISemInputDeviceManager
        public int setTspEnabled(int i, int i2, boolean z) throws RemoteException {
            return 0;
        }

        @Override // com.samsung.android.hardware.secinputdev.ISemInputDeviceManager
        public boolean unregisterListener(IBinder iBinder, int i, String str) throws RemoteException {
            return false;
        }
    }

    int activate(SemInputConstants.Device device, SemInputConstants.DisplayState displayState, boolean z) throws RemoteException;

    int enableMotion(String str, boolean z, String str2) throws RemoteException;

    String getCommandList(SemInputConstants.Device device) throws RemoteException;

    int getDeviceEnabled(SemInputConstants.Device device) throws RemoteException;

    String getKeyPressStateAll() throws RemoteException;

    int getMotionControl(String str, String str2) throws RemoteException;

    String getProperty(SemInputConstants.Device device, SemInputConstants.Property property) throws RemoteException;

    int getSupportDevice(SemInputConstants.Device device) throws RemoteException;

    int getTspSupportFeature(SemInputConstants.Device device) throws RemoteException;

    int isEnableMotion(String str, String str2) throws RemoteException;

    boolean isKeyPressedByKeycode(int i) throws RemoteException;

    boolean isSupportMotion(String str) throws RemoteException;

    boolean registerListener(IBinder iBinder, int i, String str) throws RemoteException;

    String runCommand(SemInputConstants.Device device, String str) throws RemoteException;

    int sendRawdataTsp(SemInputConstants.Device device, int[] iArr) throws RemoteException;

    int setAodEnable(int i) throws RemoteException;

    int setAodRect(int i, int i2, int i3, int i4) throws RemoteException;

    int setAotEnable(int i) throws RemoteException;

    int setCommand(SemInputConstants.Device device, SemInputConstants.Command command, String str) throws RemoteException;

    int setFodEnable(int i, int i2, int i3, int i4) throws RemoteException;

    int setFodLpMode(int i) throws RemoteException;

    int setFodRect(int i, int i2, int i3, int i4) throws RemoteException;

    int setMotionControl(String str, int i, String str2) throws RemoteException;

    int setProperty(SemInputConstants.Device device, SemInputConstants.Property property, String str) throws RemoteException;

    int setSingletapEnable(int i) throws RemoteException;

    int setSpenEnabled(int i, int i2, boolean z) throws RemoteException;

    int setSyncChanged(int i) throws RemoteException;

    int setTemperature(int i) throws RemoteException;

    int setTspEnabled(int i, int i2, boolean z) throws RemoteException;

    boolean unregisterListener(IBinder iBinder, int i, String str) throws RemoteException;

    public static abstract class Stub extends Binder implements ISemInputDeviceManager {
        static final int TRANSACTION_activate = 26;
        static final int TRANSACTION_enableMotion = 7;
        static final int TRANSACTION_getCommandList = 14;
        static final int TRANSACTION_getDeviceEnabled = 13;
        static final int TRANSACTION_getKeyPressStateAll = 1;
        static final int TRANSACTION_getMotionControl = 10;
        static final int TRANSACTION_getProperty = 29;
        static final int TRANSACTION_getSupportDevice = 11;
        static final int TRANSACTION_getTspSupportFeature = 12;
        static final int TRANSACTION_isEnableMotion = 8;
        static final int TRANSACTION_isKeyPressedByKeycode = 2;
        static final int TRANSACTION_isSupportMotion = 6;
        static final int TRANSACTION_registerListener = 3;
        static final int TRANSACTION_runCommand = 30;
        static final int TRANSACTION_sendRawdataTsp = 5;
        static final int TRANSACTION_setAodEnable = 18;
        static final int TRANSACTION_setAodRect = 17;
        static final int TRANSACTION_setAotEnable = 19;
        static final int TRANSACTION_setCommand = 27;
        static final int TRANSACTION_setFodEnable = 20;
        static final int TRANSACTION_setFodLpMode = 22;
        static final int TRANSACTION_setFodRect = 21;
        static final int TRANSACTION_setMotionControl = 9;
        static final int TRANSACTION_setProperty = 28;
        static final int TRANSACTION_setSingletapEnable = 23;
        static final int TRANSACTION_setSpenEnabled = 25;
        static final int TRANSACTION_setSyncChanged = 24;
        static final int TRANSACTION_setTemperature = 16;
        static final int TRANSACTION_setTspEnabled = 15;
        static final int TRANSACTION_unregisterListener = 4;

        @Override // android.os.IInterface
        public IBinder asBinder() {
            return this;
        }

        @Override // android.os.Binder
        public int getMaxTransactionId() {
            return 29;
        }

        public Stub() {
            attachInterface(this, ISemInputDeviceManager.DESCRIPTOR);
        }

        public static ISemInputDeviceManager asInterface(IBinder iBinder) {
            if (iBinder == null) {
                return null;
            }
            IInterface iInterfaceQueryLocalInterface = iBinder.queryLocalInterface(ISemInputDeviceManager.DESCRIPTOR);
            if (iInterfaceQueryLocalInterface != null && (iInterfaceQueryLocalInterface instanceof ISemInputDeviceManager)) {
                return (ISemInputDeviceManager) iInterfaceQueryLocalInterface;
            }
            return new Proxy(iBinder);
        }

        public static String getDefaultTransactionName(int i) {
            switch (i) {
                case 1:
                    return "getKeyPressStateAll";
                case 2:
                    return "isKeyPressedByKeycode";
                case 3:
                    return "registerListener";
                case 4:
                    return "unregisterListener";
                case 5:
                    return "sendRawdataTsp";
                case 6:
                    return "isSupportMotion";
                case 7:
                    return "enableMotion";
                case 8:
                    return "isEnableMotion";
                case 9:
                    return "setMotionControl";
                case 10:
                    return "getMotionControl";
                case 11:
                    return "getSupportDevice";
                case 12:
                    return "getTspSupportFeature";
                case 13:
                    return "getDeviceEnabled";
                case 14:
                    return "getCommandList";
                case 15:
                    return "setTspEnabled";
                case 16:
                    return "setTemperature";
                case 17:
                    return "setAodRect";
                case 18:
                    return "setAodEnable";
                case 19:
                    return "setAotEnable";
                case 20:
                    return "setFodEnable";
                case 21:
                    return "setFodRect";
                case 22:
                    return "setFodLpMode";
                case 23:
                    return "setSingletapEnable";
                case 24:
                    return "setSyncChanged";
                case 25:
                    return "setSpenEnabled";
                case 26:
                    return "activate";
                case 27:
                    return "setCommand";
                case 28:
                    return "setProperty";
                case 29:
                    return "getProperty";
                case 30:
                    return "runCommand";
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
                parcel.enforceInterface(ISemInputDeviceManager.DESCRIPTOR);
            }
            if (i == 1598968902) {
                parcel2.writeString(ISemInputDeviceManager.DESCRIPTOR);
                return true;
            }
            switch (i) {
                case 1:
                    String keyPressStateAll = getKeyPressStateAll();
                    parcel2.writeNoException();
                    parcel2.writeString(keyPressStateAll);
                    return true;
                case 2:
                    int i3 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    boolean zIsKeyPressedByKeycode = isKeyPressedByKeycode(i3);
                    parcel2.writeNoException();
                    parcel2.writeBoolean(zIsKeyPressedByKeycode);
                    return true;
                case 3:
                    IBinder strongBinder = parcel.readStrongBinder();
                    int i4 = parcel.readInt();
                    String string = parcel.readString();
                    parcel.enforceNoDataAvail();
                    boolean zRegisterListener = registerListener(strongBinder, i4, string);
                    parcel2.writeNoException();
                    parcel2.writeBoolean(zRegisterListener);
                    return true;
                case 4:
                    IBinder strongBinder2 = parcel.readStrongBinder();
                    int i5 = parcel.readInt();
                    String string2 = parcel.readString();
                    parcel.enforceNoDataAvail();
                    boolean zUnregisterListener = unregisterListener(strongBinder2, i5, string2);
                    parcel2.writeNoException();
                    parcel2.writeBoolean(zUnregisterListener);
                    return true;
                case 5:
                    SemInputConstants.Device device = (SemInputConstants.Device) parcel.readTypedObject(SemInputConstants.Device.CREATOR);
                    int[] iArrCreateIntArray = parcel.createIntArray();
                    parcel.enforceNoDataAvail();
                    int iSendRawdataTsp = sendRawdataTsp(device, iArrCreateIntArray);
                    parcel2.writeNoException();
                    parcel2.writeInt(iSendRawdataTsp);
                    return true;
                case 6:
                    String string3 = parcel.readString();
                    parcel.enforceNoDataAvail();
                    boolean zIsSupportMotion = isSupportMotion(string3);
                    parcel2.writeNoException();
                    parcel2.writeBoolean(zIsSupportMotion);
                    return true;
                case 7:
                    String string4 = parcel.readString();
                    boolean z = parcel.readBoolean();
                    String string5 = parcel.readString();
                    parcel.enforceNoDataAvail();
                    int iEnableMotion = enableMotion(string4, z, string5);
                    parcel2.writeNoException();
                    parcel2.writeInt(iEnableMotion);
                    return true;
                case 8:
                    String string6 = parcel.readString();
                    String string7 = parcel.readString();
                    parcel.enforceNoDataAvail();
                    int iIsEnableMotion = isEnableMotion(string6, string7);
                    parcel2.writeNoException();
                    parcel2.writeInt(iIsEnableMotion);
                    return true;
                case 9:
                    String string8 = parcel.readString();
                    int i6 = parcel.readInt();
                    String string9 = parcel.readString();
                    parcel.enforceNoDataAvail();
                    int motionControl = setMotionControl(string8, i6, string9);
                    parcel2.writeNoException();
                    parcel2.writeInt(motionControl);
                    return true;
                case 10:
                    String string10 = parcel.readString();
                    String string11 = parcel.readString();
                    parcel.enforceNoDataAvail();
                    int motionControl2 = getMotionControl(string10, string11);
                    parcel2.writeNoException();
                    parcel2.writeInt(motionControl2);
                    return true;
                case 11:
                    SemInputConstants.Device device2 = (SemInputConstants.Device) parcel.readTypedObject(SemInputConstants.Device.CREATOR);
                    parcel.enforceNoDataAvail();
                    int supportDevice = getSupportDevice(device2);
                    parcel2.writeNoException();
                    parcel2.writeInt(supportDevice);
                    return true;
                case 12:
                    SemInputConstants.Device device3 = (SemInputConstants.Device) parcel.readTypedObject(SemInputConstants.Device.CREATOR);
                    parcel.enforceNoDataAvail();
                    int tspSupportFeature = getTspSupportFeature(device3);
                    parcel2.writeNoException();
                    parcel2.writeInt(tspSupportFeature);
                    return true;
                case 13:
                    SemInputConstants.Device device4 = (SemInputConstants.Device) parcel.readTypedObject(SemInputConstants.Device.CREATOR);
                    parcel.enforceNoDataAvail();
                    int deviceEnabled = getDeviceEnabled(device4);
                    parcel2.writeNoException();
                    parcel2.writeInt(deviceEnabled);
                    return true;
                case 14:
                    SemInputConstants.Device device5 = (SemInputConstants.Device) parcel.readTypedObject(SemInputConstants.Device.CREATOR);
                    parcel.enforceNoDataAvail();
                    String commandList = getCommandList(device5);
                    parcel2.writeNoException();
                    parcel2.writeString(commandList);
                    return true;
                case 15:
                    int i7 = parcel.readInt();
                    int i8 = parcel.readInt();
                    boolean z2 = parcel.readBoolean();
                    parcel.enforceNoDataAvail();
                    int tspEnabled = setTspEnabled(i7, i8, z2);
                    parcel2.writeNoException();
                    parcel2.writeInt(tspEnabled);
                    return true;
                case 16:
                    int i9 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    int temperature = setTemperature(i9);
                    parcel2.writeNoException();
                    parcel2.writeInt(temperature);
                    return true;
                case 17:
                    int i10 = parcel.readInt();
                    int i11 = parcel.readInt();
                    int i12 = parcel.readInt();
                    int i13 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    int aodRect = setAodRect(i10, i11, i12, i13);
                    parcel2.writeNoException();
                    parcel2.writeInt(aodRect);
                    return true;
                case 18:
                    int i14 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    int aodEnable = setAodEnable(i14);
                    parcel2.writeNoException();
                    parcel2.writeInt(aodEnable);
                    return true;
                case 19:
                    int i15 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    int aotEnable = setAotEnable(i15);
                    parcel2.writeNoException();
                    parcel2.writeInt(aotEnable);
                    return true;
                case 20:
                    int i16 = parcel.readInt();
                    int i17 = parcel.readInt();
                    int i18 = parcel.readInt();
                    int i19 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    int fodEnable = setFodEnable(i16, i17, i18, i19);
                    parcel2.writeNoException();
                    parcel2.writeInt(fodEnable);
                    return true;
                case 21:
                    int i20 = parcel.readInt();
                    int i21 = parcel.readInt();
                    int i22 = parcel.readInt();
                    int i23 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    int fodRect = setFodRect(i20, i21, i22, i23);
                    parcel2.writeNoException();
                    parcel2.writeInt(fodRect);
                    return true;
                case 22:
                    int i24 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    int fodLpMode = setFodLpMode(i24);
                    parcel2.writeNoException();
                    parcel2.writeInt(fodLpMode);
                    return true;
                case 23:
                    int i25 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    int singletapEnable = setSingletapEnable(i25);
                    parcel2.writeNoException();
                    parcel2.writeInt(singletapEnable);
                    return true;
                case 24:
                    int i26 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    int syncChanged = setSyncChanged(i26);
                    parcel2.writeNoException();
                    parcel2.writeInt(syncChanged);
                    return true;
                case 25:
                    int i27 = parcel.readInt();
                    int i28 = parcel.readInt();
                    boolean z3 = parcel.readBoolean();
                    parcel.enforceNoDataAvail();
                    int spenEnabled = setSpenEnabled(i27, i28, z3);
                    parcel2.writeNoException();
                    parcel2.writeInt(spenEnabled);
                    return true;
                case 26:
                    SemInputConstants.Device device6 = (SemInputConstants.Device) parcel.readTypedObject(SemInputConstants.Device.CREATOR);
                    SemInputConstants.DisplayState displayState = (SemInputConstants.DisplayState) parcel.readTypedObject(SemInputConstants.DisplayState.CREATOR);
                    boolean z4 = parcel.readBoolean();
                    parcel.enforceNoDataAvail();
                    int iActivate = activate(device6, displayState, z4);
                    parcel2.writeNoException();
                    parcel2.writeInt(iActivate);
                    return true;
                case 27:
                    SemInputConstants.Device device7 = (SemInputConstants.Device) parcel.readTypedObject(SemInputConstants.Device.CREATOR);
                    SemInputConstants.Command command = (SemInputConstants.Command) parcel.readTypedObject(SemInputConstants.Command.CREATOR);
                    String string12 = parcel.readString();
                    parcel.enforceNoDataAvail();
                    int command2 = setCommand(device7, command, string12);
                    parcel2.writeNoException();
                    parcel2.writeInt(command2);
                    return true;
                case 28:
                    SemInputConstants.Device device8 = (SemInputConstants.Device) parcel.readTypedObject(SemInputConstants.Device.CREATOR);
                    SemInputConstants.Property property = (SemInputConstants.Property) parcel.readTypedObject(SemInputConstants.Property.CREATOR);
                    String string13 = parcel.readString();
                    parcel.enforceNoDataAvail();
                    int property2 = setProperty(device8, property, string13);
                    parcel2.writeNoException();
                    parcel2.writeInt(property2);
                    return true;
                case 29:
                    SemInputConstants.Device device9 = (SemInputConstants.Device) parcel.readTypedObject(SemInputConstants.Device.CREATOR);
                    SemInputConstants.Property property3 = (SemInputConstants.Property) parcel.readTypedObject(SemInputConstants.Property.CREATOR);
                    parcel.enforceNoDataAvail();
                    String property4 = getProperty(device9, property3);
                    parcel2.writeNoException();
                    parcel2.writeString(property4);
                    return true;
                case 30:
                    SemInputConstants.Device device10 = (SemInputConstants.Device) parcel.readTypedObject(SemInputConstants.Device.CREATOR);
                    String string14 = parcel.readString();
                    parcel.enforceNoDataAvail();
                    String strRunCommand = runCommand(device10, string14);
                    parcel2.writeNoException();
                    parcel2.writeString(strRunCommand);
                    return true;
                default:
                    return super.onTransact(i, parcel, parcel2, i2);
            }
        }

        private static class Proxy implements ISemInputDeviceManager {
            private IBinder mRemote;

            Proxy(IBinder iBinder) {
                this.mRemote = iBinder;
            }

            @Override // android.os.IInterface
            public IBinder asBinder() {
                return this.mRemote;
            }

            public String getInterfaceDescriptor() {
                return ISemInputDeviceManager.DESCRIPTOR;
            }

            @Override // com.samsung.android.hardware.secinputdev.ISemInputDeviceManager
            public String getKeyPressStateAll() throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(ISemInputDeviceManager.DESCRIPTOR);
                    this.mRemote.transact(1, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return parcelObtain2.readString();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // com.samsung.android.hardware.secinputdev.ISemInputDeviceManager
            public boolean isKeyPressedByKeycode(int i) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(ISemInputDeviceManager.DESCRIPTOR);
                    parcelObtain.writeInt(i);
                    this.mRemote.transact(2, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return parcelObtain2.readBoolean();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // com.samsung.android.hardware.secinputdev.ISemInputDeviceManager
            public boolean registerListener(IBinder iBinder, int i, String str) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(ISemInputDeviceManager.DESCRIPTOR);
                    parcelObtain.writeStrongBinder(iBinder);
                    parcelObtain.writeInt(i);
                    parcelObtain.writeString(str);
                    this.mRemote.transact(3, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return parcelObtain2.readBoolean();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // com.samsung.android.hardware.secinputdev.ISemInputDeviceManager
            public boolean unregisterListener(IBinder iBinder, int i, String str) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(ISemInputDeviceManager.DESCRIPTOR);
                    parcelObtain.writeStrongBinder(iBinder);
                    parcelObtain.writeInt(i);
                    parcelObtain.writeString(str);
                    this.mRemote.transact(4, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return parcelObtain2.readBoolean();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // com.samsung.android.hardware.secinputdev.ISemInputDeviceManager
            public int sendRawdataTsp(SemInputConstants.Device device, int[] iArr) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(ISemInputDeviceManager.DESCRIPTOR);
                    parcelObtain.writeTypedObject(device, 0);
                    parcelObtain.writeIntArray(iArr);
                    this.mRemote.transact(5, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return parcelObtain2.readInt();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // com.samsung.android.hardware.secinputdev.ISemInputDeviceManager
            public boolean isSupportMotion(String str) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(ISemInputDeviceManager.DESCRIPTOR);
                    parcelObtain.writeString(str);
                    this.mRemote.transact(6, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return parcelObtain2.readBoolean();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // com.samsung.android.hardware.secinputdev.ISemInputDeviceManager
            public int enableMotion(String str, boolean z, String str2) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(ISemInputDeviceManager.DESCRIPTOR);
                    parcelObtain.writeString(str);
                    parcelObtain.writeBoolean(z);
                    parcelObtain.writeString(str2);
                    this.mRemote.transact(7, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return parcelObtain2.readInt();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // com.samsung.android.hardware.secinputdev.ISemInputDeviceManager
            public int isEnableMotion(String str, String str2) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(ISemInputDeviceManager.DESCRIPTOR);
                    parcelObtain.writeString(str);
                    parcelObtain.writeString(str2);
                    this.mRemote.transact(8, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return parcelObtain2.readInt();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // com.samsung.android.hardware.secinputdev.ISemInputDeviceManager
            public int setMotionControl(String str, int i, String str2) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(ISemInputDeviceManager.DESCRIPTOR);
                    parcelObtain.writeString(str);
                    parcelObtain.writeInt(i);
                    parcelObtain.writeString(str2);
                    this.mRemote.transact(9, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return parcelObtain2.readInt();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // com.samsung.android.hardware.secinputdev.ISemInputDeviceManager
            public int getMotionControl(String str, String str2) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(ISemInputDeviceManager.DESCRIPTOR);
                    parcelObtain.writeString(str);
                    parcelObtain.writeString(str2);
                    this.mRemote.transact(10, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return parcelObtain2.readInt();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // com.samsung.android.hardware.secinputdev.ISemInputDeviceManager
            public int getSupportDevice(SemInputConstants.Device device) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(ISemInputDeviceManager.DESCRIPTOR);
                    parcelObtain.writeTypedObject(device, 0);
                    this.mRemote.transact(11, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return parcelObtain2.readInt();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // com.samsung.android.hardware.secinputdev.ISemInputDeviceManager
            public int getTspSupportFeature(SemInputConstants.Device device) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(ISemInputDeviceManager.DESCRIPTOR);
                    parcelObtain.writeTypedObject(device, 0);
                    this.mRemote.transact(12, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return parcelObtain2.readInt();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // com.samsung.android.hardware.secinputdev.ISemInputDeviceManager
            public int getDeviceEnabled(SemInputConstants.Device device) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(ISemInputDeviceManager.DESCRIPTOR);
                    parcelObtain.writeTypedObject(device, 0);
                    this.mRemote.transact(13, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return parcelObtain2.readInt();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // com.samsung.android.hardware.secinputdev.ISemInputDeviceManager
            public String getCommandList(SemInputConstants.Device device) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(ISemInputDeviceManager.DESCRIPTOR);
                    parcelObtain.writeTypedObject(device, 0);
                    this.mRemote.transact(14, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return parcelObtain2.readString();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // com.samsung.android.hardware.secinputdev.ISemInputDeviceManager
            public int setTspEnabled(int i, int i2, boolean z) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(ISemInputDeviceManager.DESCRIPTOR);
                    parcelObtain.writeInt(i);
                    parcelObtain.writeInt(i2);
                    parcelObtain.writeBoolean(z);
                    this.mRemote.transact(15, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return parcelObtain2.readInt();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // com.samsung.android.hardware.secinputdev.ISemInputDeviceManager
            public int setTemperature(int i) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(ISemInputDeviceManager.DESCRIPTOR);
                    parcelObtain.writeInt(i);
                    this.mRemote.transact(16, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return parcelObtain2.readInt();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // com.samsung.android.hardware.secinputdev.ISemInputDeviceManager
            public int setAodRect(int i, int i2, int i3, int i4) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(ISemInputDeviceManager.DESCRIPTOR);
                    parcelObtain.writeInt(i);
                    parcelObtain.writeInt(i2);
                    parcelObtain.writeInt(i3);
                    parcelObtain.writeInt(i4);
                    this.mRemote.transact(17, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return parcelObtain2.readInt();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // com.samsung.android.hardware.secinputdev.ISemInputDeviceManager
            public int setAodEnable(int i) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(ISemInputDeviceManager.DESCRIPTOR);
                    parcelObtain.writeInt(i);
                    this.mRemote.transact(18, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return parcelObtain2.readInt();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // com.samsung.android.hardware.secinputdev.ISemInputDeviceManager
            public int setAotEnable(int i) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(ISemInputDeviceManager.DESCRIPTOR);
                    parcelObtain.writeInt(i);
                    this.mRemote.transact(19, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return parcelObtain2.readInt();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // com.samsung.android.hardware.secinputdev.ISemInputDeviceManager
            public int setFodEnable(int i, int i2, int i3, int i4) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(ISemInputDeviceManager.DESCRIPTOR);
                    parcelObtain.writeInt(i);
                    parcelObtain.writeInt(i2);
                    parcelObtain.writeInt(i3);
                    parcelObtain.writeInt(i4);
                    this.mRemote.transact(20, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return parcelObtain2.readInt();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // com.samsung.android.hardware.secinputdev.ISemInputDeviceManager
            public int setFodRect(int i, int i2, int i3, int i4) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(ISemInputDeviceManager.DESCRIPTOR);
                    parcelObtain.writeInt(i);
                    parcelObtain.writeInt(i2);
                    parcelObtain.writeInt(i3);
                    parcelObtain.writeInt(i4);
                    this.mRemote.transact(21, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return parcelObtain2.readInt();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // com.samsung.android.hardware.secinputdev.ISemInputDeviceManager
            public int setFodLpMode(int i) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(ISemInputDeviceManager.DESCRIPTOR);
                    parcelObtain.writeInt(i);
                    this.mRemote.transact(22, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return parcelObtain2.readInt();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // com.samsung.android.hardware.secinputdev.ISemInputDeviceManager
            public int setSingletapEnable(int i) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(ISemInputDeviceManager.DESCRIPTOR);
                    parcelObtain.writeInt(i);
                    this.mRemote.transact(23, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return parcelObtain2.readInt();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // com.samsung.android.hardware.secinputdev.ISemInputDeviceManager
            public int setSyncChanged(int i) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(ISemInputDeviceManager.DESCRIPTOR);
                    parcelObtain.writeInt(i);
                    this.mRemote.transact(24, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return parcelObtain2.readInt();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // com.samsung.android.hardware.secinputdev.ISemInputDeviceManager
            public int setSpenEnabled(int i, int i2, boolean z) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(ISemInputDeviceManager.DESCRIPTOR);
                    parcelObtain.writeInt(i);
                    parcelObtain.writeInt(i2);
                    parcelObtain.writeBoolean(z);
                    this.mRemote.transact(25, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return parcelObtain2.readInt();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // com.samsung.android.hardware.secinputdev.ISemInputDeviceManager
            public int activate(SemInputConstants.Device device, SemInputConstants.DisplayState displayState, boolean z) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(ISemInputDeviceManager.DESCRIPTOR);
                    parcelObtain.writeTypedObject(device, 0);
                    parcelObtain.writeTypedObject(displayState, 0);
                    parcelObtain.writeBoolean(z);
                    this.mRemote.transact(26, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return parcelObtain2.readInt();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // com.samsung.android.hardware.secinputdev.ISemInputDeviceManager
            public int setCommand(SemInputConstants.Device device, SemInputConstants.Command command, String str) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(ISemInputDeviceManager.DESCRIPTOR);
                    parcelObtain.writeTypedObject(device, 0);
                    parcelObtain.writeTypedObject(command, 0);
                    parcelObtain.writeString(str);
                    this.mRemote.transact(27, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return parcelObtain2.readInt();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // com.samsung.android.hardware.secinputdev.ISemInputDeviceManager
            public int setProperty(SemInputConstants.Device device, SemInputConstants.Property property, String str) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(ISemInputDeviceManager.DESCRIPTOR);
                    parcelObtain.writeTypedObject(device, 0);
                    parcelObtain.writeTypedObject(property, 0);
                    parcelObtain.writeString(str);
                    this.mRemote.transact(28, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return parcelObtain2.readInt();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // com.samsung.android.hardware.secinputdev.ISemInputDeviceManager
            public String getProperty(SemInputConstants.Device device, SemInputConstants.Property property) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(ISemInputDeviceManager.DESCRIPTOR);
                    parcelObtain.writeTypedObject(device, 0);
                    parcelObtain.writeTypedObject(property, 0);
                    this.mRemote.transact(29, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return parcelObtain2.readString();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // com.samsung.android.hardware.secinputdev.ISemInputDeviceManager
            public String runCommand(SemInputConstants.Device device, String str) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(ISemInputDeviceManager.DESCRIPTOR);
                    parcelObtain.writeTypedObject(device, 0);
                    parcelObtain.writeString(str);
                    this.mRemote.transact(30, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return parcelObtain2.readString();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }
        }
    }
}
