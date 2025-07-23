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
            IInterface queryLocalInterface = iBinder.queryLocalInterface(ISemInputDeviceManager.DESCRIPTOR);
            if (queryLocalInterface != null && (queryLocalInterface instanceof ISemInputDeviceManager)) {
                return (ISemInputDeviceManager) queryLocalInterface;
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
                    int readInt = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    boolean isKeyPressedByKeycode = isKeyPressedByKeycode(readInt);
                    parcel2.writeNoException();
                    parcel2.writeBoolean(isKeyPressedByKeycode);
                    return true;
                case 3:
                    IBinder readStrongBinder = parcel.readStrongBinder();
                    int readInt2 = parcel.readInt();
                    String readString = parcel.readString();
                    parcel.enforceNoDataAvail();
                    boolean registerListener = registerListener(readStrongBinder, readInt2, readString);
                    parcel2.writeNoException();
                    parcel2.writeBoolean(registerListener);
                    return true;
                case 4:
                    IBinder readStrongBinder2 = parcel.readStrongBinder();
                    int readInt3 = parcel.readInt();
                    String readString2 = parcel.readString();
                    parcel.enforceNoDataAvail();
                    boolean unregisterListener = unregisterListener(readStrongBinder2, readInt3, readString2);
                    parcel2.writeNoException();
                    parcel2.writeBoolean(unregisterListener);
                    return true;
                case 5:
                    SemInputConstants.Device device = (SemInputConstants.Device) parcel.readTypedObject(SemInputConstants.Device.CREATOR);
                    int[] createIntArray = parcel.createIntArray();
                    parcel.enforceNoDataAvail();
                    int sendRawdataTsp = sendRawdataTsp(device, createIntArray);
                    parcel2.writeNoException();
                    parcel2.writeInt(sendRawdataTsp);
                    return true;
                case 6:
                    String readString3 = parcel.readString();
                    parcel.enforceNoDataAvail();
                    boolean isSupportMotion = isSupportMotion(readString3);
                    parcel2.writeNoException();
                    parcel2.writeBoolean(isSupportMotion);
                    return true;
                case 7:
                    String readString4 = parcel.readString();
                    boolean readBoolean = parcel.readBoolean();
                    String readString5 = parcel.readString();
                    parcel.enforceNoDataAvail();
                    int enableMotion = enableMotion(readString4, readBoolean, readString5);
                    parcel2.writeNoException();
                    parcel2.writeInt(enableMotion);
                    return true;
                case 8:
                    String readString6 = parcel.readString();
                    String readString7 = parcel.readString();
                    parcel.enforceNoDataAvail();
                    int isEnableMotion = isEnableMotion(readString6, readString7);
                    parcel2.writeNoException();
                    parcel2.writeInt(isEnableMotion);
                    return true;
                case 9:
                    String readString8 = parcel.readString();
                    int readInt4 = parcel.readInt();
                    String readString9 = parcel.readString();
                    parcel.enforceNoDataAvail();
                    int motionControl = setMotionControl(readString8, readInt4, readString9);
                    parcel2.writeNoException();
                    parcel2.writeInt(motionControl);
                    return true;
                case 10:
                    String readString10 = parcel.readString();
                    String readString11 = parcel.readString();
                    parcel.enforceNoDataAvail();
                    int motionControl2 = getMotionControl(readString10, readString11);
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
                    int readInt5 = parcel.readInt();
                    int readInt6 = parcel.readInt();
                    boolean readBoolean2 = parcel.readBoolean();
                    parcel.enforceNoDataAvail();
                    int tspEnabled = setTspEnabled(readInt5, readInt6, readBoolean2);
                    parcel2.writeNoException();
                    parcel2.writeInt(tspEnabled);
                    return true;
                case 16:
                    int readInt7 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    int temperature = setTemperature(readInt7);
                    parcel2.writeNoException();
                    parcel2.writeInt(temperature);
                    return true;
                case 17:
                    int readInt8 = parcel.readInt();
                    int readInt9 = parcel.readInt();
                    int readInt10 = parcel.readInt();
                    int readInt11 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    int aodRect = setAodRect(readInt8, readInt9, readInt10, readInt11);
                    parcel2.writeNoException();
                    parcel2.writeInt(aodRect);
                    return true;
                case 18:
                    int readInt12 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    int aodEnable = setAodEnable(readInt12);
                    parcel2.writeNoException();
                    parcel2.writeInt(aodEnable);
                    return true;
                case 19:
                    int readInt13 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    int aotEnable = setAotEnable(readInt13);
                    parcel2.writeNoException();
                    parcel2.writeInt(aotEnable);
                    return true;
                case 20:
                    int readInt14 = parcel.readInt();
                    int readInt15 = parcel.readInt();
                    int readInt16 = parcel.readInt();
                    int readInt17 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    int fodEnable = setFodEnable(readInt14, readInt15, readInt16, readInt17);
                    parcel2.writeNoException();
                    parcel2.writeInt(fodEnable);
                    return true;
                case 21:
                    int readInt18 = parcel.readInt();
                    int readInt19 = parcel.readInt();
                    int readInt20 = parcel.readInt();
                    int readInt21 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    int fodRect = setFodRect(readInt18, readInt19, readInt20, readInt21);
                    parcel2.writeNoException();
                    parcel2.writeInt(fodRect);
                    return true;
                case 22:
                    int readInt22 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    int fodLpMode = setFodLpMode(readInt22);
                    parcel2.writeNoException();
                    parcel2.writeInt(fodLpMode);
                    return true;
                case 23:
                    int readInt23 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    int singletapEnable = setSingletapEnable(readInt23);
                    parcel2.writeNoException();
                    parcel2.writeInt(singletapEnable);
                    return true;
                case 24:
                    int readInt24 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    int syncChanged = setSyncChanged(readInt24);
                    parcel2.writeNoException();
                    parcel2.writeInt(syncChanged);
                    return true;
                case 25:
                    int readInt25 = parcel.readInt();
                    int readInt26 = parcel.readInt();
                    boolean readBoolean3 = parcel.readBoolean();
                    parcel.enforceNoDataAvail();
                    int spenEnabled = setSpenEnabled(readInt25, readInt26, readBoolean3);
                    parcel2.writeNoException();
                    parcel2.writeInt(spenEnabled);
                    return true;
                case 26:
                    SemInputConstants.Device device6 = (SemInputConstants.Device) parcel.readTypedObject(SemInputConstants.Device.CREATOR);
                    SemInputConstants.DisplayState displayState = (SemInputConstants.DisplayState) parcel.readTypedObject(SemInputConstants.DisplayState.CREATOR);
                    boolean readBoolean4 = parcel.readBoolean();
                    parcel.enforceNoDataAvail();
                    int activate = activate(device6, displayState, readBoolean4);
                    parcel2.writeNoException();
                    parcel2.writeInt(activate);
                    return true;
                case 27:
                    SemInputConstants.Device device7 = (SemInputConstants.Device) parcel.readTypedObject(SemInputConstants.Device.CREATOR);
                    SemInputConstants.Command command = (SemInputConstants.Command) parcel.readTypedObject(SemInputConstants.Command.CREATOR);
                    String readString12 = parcel.readString();
                    parcel.enforceNoDataAvail();
                    int command2 = setCommand(device7, command, readString12);
                    parcel2.writeNoException();
                    parcel2.writeInt(command2);
                    return true;
                case 28:
                    SemInputConstants.Device device8 = (SemInputConstants.Device) parcel.readTypedObject(SemInputConstants.Device.CREATOR);
                    SemInputConstants.Property property = (SemInputConstants.Property) parcel.readTypedObject(SemInputConstants.Property.CREATOR);
                    String readString13 = parcel.readString();
                    parcel.enforceNoDataAvail();
                    int property2 = setProperty(device8, property, readString13);
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
                    String readString14 = parcel.readString();
                    parcel.enforceNoDataAvail();
                    String runCommand = runCommand(device10, readString14);
                    parcel2.writeNoException();
                    parcel2.writeString(runCommand);
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
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(ISemInputDeviceManager.DESCRIPTOR);
                    this.mRemote.transact(1, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readString();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.samsung.android.hardware.secinputdev.ISemInputDeviceManager
            public boolean isKeyPressedByKeycode(int i) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(ISemInputDeviceManager.DESCRIPTOR);
                    obtain.writeInt(i);
                    this.mRemote.transact(2, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readBoolean();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.samsung.android.hardware.secinputdev.ISemInputDeviceManager
            public boolean registerListener(IBinder iBinder, int i, String str) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(ISemInputDeviceManager.DESCRIPTOR);
                    obtain.writeStrongBinder(iBinder);
                    obtain.writeInt(i);
                    obtain.writeString(str);
                    this.mRemote.transact(3, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readBoolean();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.samsung.android.hardware.secinputdev.ISemInputDeviceManager
            public boolean unregisterListener(IBinder iBinder, int i, String str) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(ISemInputDeviceManager.DESCRIPTOR);
                    obtain.writeStrongBinder(iBinder);
                    obtain.writeInt(i);
                    obtain.writeString(str);
                    this.mRemote.transact(4, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readBoolean();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.samsung.android.hardware.secinputdev.ISemInputDeviceManager
            public int sendRawdataTsp(SemInputConstants.Device device, int[] iArr) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(ISemInputDeviceManager.DESCRIPTOR);
                    obtain.writeTypedObject(device, 0);
                    obtain.writeIntArray(iArr);
                    this.mRemote.transact(5, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readInt();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.samsung.android.hardware.secinputdev.ISemInputDeviceManager
            public boolean isSupportMotion(String str) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(ISemInputDeviceManager.DESCRIPTOR);
                    obtain.writeString(str);
                    this.mRemote.transact(6, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readBoolean();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.samsung.android.hardware.secinputdev.ISemInputDeviceManager
            public int enableMotion(String str, boolean z, String str2) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(ISemInputDeviceManager.DESCRIPTOR);
                    obtain.writeString(str);
                    obtain.writeBoolean(z);
                    obtain.writeString(str2);
                    this.mRemote.transact(7, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readInt();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.samsung.android.hardware.secinputdev.ISemInputDeviceManager
            public int isEnableMotion(String str, String str2) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(ISemInputDeviceManager.DESCRIPTOR);
                    obtain.writeString(str);
                    obtain.writeString(str2);
                    this.mRemote.transact(8, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readInt();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.samsung.android.hardware.secinputdev.ISemInputDeviceManager
            public int setMotionControl(String str, int i, String str2) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(ISemInputDeviceManager.DESCRIPTOR);
                    obtain.writeString(str);
                    obtain.writeInt(i);
                    obtain.writeString(str2);
                    this.mRemote.transact(9, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readInt();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.samsung.android.hardware.secinputdev.ISemInputDeviceManager
            public int getMotionControl(String str, String str2) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(ISemInputDeviceManager.DESCRIPTOR);
                    obtain.writeString(str);
                    obtain.writeString(str2);
                    this.mRemote.transact(10, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readInt();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.samsung.android.hardware.secinputdev.ISemInputDeviceManager
            public int getSupportDevice(SemInputConstants.Device device) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(ISemInputDeviceManager.DESCRIPTOR);
                    obtain.writeTypedObject(device, 0);
                    this.mRemote.transact(11, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readInt();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.samsung.android.hardware.secinputdev.ISemInputDeviceManager
            public int getTspSupportFeature(SemInputConstants.Device device) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(ISemInputDeviceManager.DESCRIPTOR);
                    obtain.writeTypedObject(device, 0);
                    this.mRemote.transact(12, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readInt();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.samsung.android.hardware.secinputdev.ISemInputDeviceManager
            public int getDeviceEnabled(SemInputConstants.Device device) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(ISemInputDeviceManager.DESCRIPTOR);
                    obtain.writeTypedObject(device, 0);
                    this.mRemote.transact(13, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readInt();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.samsung.android.hardware.secinputdev.ISemInputDeviceManager
            public String getCommandList(SemInputConstants.Device device) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(ISemInputDeviceManager.DESCRIPTOR);
                    obtain.writeTypedObject(device, 0);
                    this.mRemote.transact(14, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readString();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.samsung.android.hardware.secinputdev.ISemInputDeviceManager
            public int setTspEnabled(int i, int i2, boolean z) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(ISemInputDeviceManager.DESCRIPTOR);
                    obtain.writeInt(i);
                    obtain.writeInt(i2);
                    obtain.writeBoolean(z);
                    this.mRemote.transact(15, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readInt();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.samsung.android.hardware.secinputdev.ISemInputDeviceManager
            public int setTemperature(int i) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(ISemInputDeviceManager.DESCRIPTOR);
                    obtain.writeInt(i);
                    this.mRemote.transact(16, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readInt();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.samsung.android.hardware.secinputdev.ISemInputDeviceManager
            public int setAodRect(int i, int i2, int i3, int i4) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(ISemInputDeviceManager.DESCRIPTOR);
                    obtain.writeInt(i);
                    obtain.writeInt(i2);
                    obtain.writeInt(i3);
                    obtain.writeInt(i4);
                    this.mRemote.transact(17, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readInt();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.samsung.android.hardware.secinputdev.ISemInputDeviceManager
            public int setAodEnable(int i) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(ISemInputDeviceManager.DESCRIPTOR);
                    obtain.writeInt(i);
                    this.mRemote.transact(18, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readInt();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.samsung.android.hardware.secinputdev.ISemInputDeviceManager
            public int setAotEnable(int i) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(ISemInputDeviceManager.DESCRIPTOR);
                    obtain.writeInt(i);
                    this.mRemote.transact(19, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readInt();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.samsung.android.hardware.secinputdev.ISemInputDeviceManager
            public int setFodEnable(int i, int i2, int i3, int i4) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(ISemInputDeviceManager.DESCRIPTOR);
                    obtain.writeInt(i);
                    obtain.writeInt(i2);
                    obtain.writeInt(i3);
                    obtain.writeInt(i4);
                    this.mRemote.transact(20, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readInt();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.samsung.android.hardware.secinputdev.ISemInputDeviceManager
            public int setFodRect(int i, int i2, int i3, int i4) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(ISemInputDeviceManager.DESCRIPTOR);
                    obtain.writeInt(i);
                    obtain.writeInt(i2);
                    obtain.writeInt(i3);
                    obtain.writeInt(i4);
                    this.mRemote.transact(21, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readInt();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.samsung.android.hardware.secinputdev.ISemInputDeviceManager
            public int setFodLpMode(int i) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(ISemInputDeviceManager.DESCRIPTOR);
                    obtain.writeInt(i);
                    this.mRemote.transact(22, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readInt();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.samsung.android.hardware.secinputdev.ISemInputDeviceManager
            public int setSingletapEnable(int i) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(ISemInputDeviceManager.DESCRIPTOR);
                    obtain.writeInt(i);
                    this.mRemote.transact(23, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readInt();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.samsung.android.hardware.secinputdev.ISemInputDeviceManager
            public int setSyncChanged(int i) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(ISemInputDeviceManager.DESCRIPTOR);
                    obtain.writeInt(i);
                    this.mRemote.transact(24, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readInt();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.samsung.android.hardware.secinputdev.ISemInputDeviceManager
            public int setSpenEnabled(int i, int i2, boolean z) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(ISemInputDeviceManager.DESCRIPTOR);
                    obtain.writeInt(i);
                    obtain.writeInt(i2);
                    obtain.writeBoolean(z);
                    this.mRemote.transact(25, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readInt();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.samsung.android.hardware.secinputdev.ISemInputDeviceManager
            public int activate(SemInputConstants.Device device, SemInputConstants.DisplayState displayState, boolean z) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(ISemInputDeviceManager.DESCRIPTOR);
                    obtain.writeTypedObject(device, 0);
                    obtain.writeTypedObject(displayState, 0);
                    obtain.writeBoolean(z);
                    this.mRemote.transact(26, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readInt();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.samsung.android.hardware.secinputdev.ISemInputDeviceManager
            public int setCommand(SemInputConstants.Device device, SemInputConstants.Command command, String str) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(ISemInputDeviceManager.DESCRIPTOR);
                    obtain.writeTypedObject(device, 0);
                    obtain.writeTypedObject(command, 0);
                    obtain.writeString(str);
                    this.mRemote.transact(27, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readInt();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.samsung.android.hardware.secinputdev.ISemInputDeviceManager
            public int setProperty(SemInputConstants.Device device, SemInputConstants.Property property, String str) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(ISemInputDeviceManager.DESCRIPTOR);
                    obtain.writeTypedObject(device, 0);
                    obtain.writeTypedObject(property, 0);
                    obtain.writeString(str);
                    this.mRemote.transact(28, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readInt();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.samsung.android.hardware.secinputdev.ISemInputDeviceManager
            public String getProperty(SemInputConstants.Device device, SemInputConstants.Property property) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(ISemInputDeviceManager.DESCRIPTOR);
                    obtain.writeTypedObject(device, 0);
                    obtain.writeTypedObject(property, 0);
                    this.mRemote.transact(29, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readString();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.samsung.android.hardware.secinputdev.ISemInputDeviceManager
            public String runCommand(SemInputConstants.Device device, String str) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(ISemInputDeviceManager.DESCRIPTOR);
                    obtain.writeTypedObject(device, 0);
                    obtain.writeString(str);
                    this.mRemote.transact(30, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readString();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }
        }
    }
}
