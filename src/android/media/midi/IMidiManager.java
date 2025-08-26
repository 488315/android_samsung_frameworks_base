package android.media.midi;

import android.bluetooth.BluetoothDevice;
import android.media.midi.IMidiDeviceListener;
import android.media.midi.IMidiDeviceOpenCallback;
import android.media.midi.IMidiDeviceServer;
import android.os.Binder;
import android.os.Bundle;
import android.os.IBinder;
import android.os.IInterface;
import android.os.Parcel;
import android.os.RemoteException;

/* loaded from: classes3.dex */
public interface IMidiManager extends IInterface {

    public static class Default implements IMidiManager {
        @Override // android.os.IInterface
        public IBinder asBinder() {
            return null;
        }

        @Override // android.media.midi.IMidiManager
        public void closeDevice(IBinder iBinder, IBinder iBinder2) throws RemoteException {
        }

        @Override // android.media.midi.IMidiManager
        public MidiDeviceStatus getDeviceStatus(MidiDeviceInfo midiDeviceInfo) throws RemoteException {
            return null;
        }

        @Override // android.media.midi.IMidiManager
        public MidiDeviceInfo[] getDevices() throws RemoteException {
            return null;
        }

        @Override // android.media.midi.IMidiManager
        public MidiDeviceInfo[] getDevicesForTransport(int i) throws RemoteException {
            return null;
        }

        @Override // android.media.midi.IMidiManager
        public MidiDeviceInfo getServiceDeviceInfo(String str, String str2) throws RemoteException {
            return null;
        }

        @Override // android.media.midi.IMidiManager
        public void openBluetoothDevice(IBinder iBinder, BluetoothDevice bluetoothDevice, IMidiDeviceOpenCallback iMidiDeviceOpenCallback) throws RemoteException {
        }

        @Override // android.media.midi.IMidiManager
        public void openDevice(IBinder iBinder, MidiDeviceInfo midiDeviceInfo, IMidiDeviceOpenCallback iMidiDeviceOpenCallback) throws RemoteException {
        }

        @Override // android.media.midi.IMidiManager
        public MidiDeviceInfo registerDeviceServer(IMidiDeviceServer iMidiDeviceServer, int i, int i2, String[] strArr, String[] strArr2, Bundle bundle, int i3, int i4) throws RemoteException {
            return null;
        }

        @Override // android.media.midi.IMidiManager
        public void registerListener(IBinder iBinder, IMidiDeviceListener iMidiDeviceListener) throws RemoteException {
        }

        @Override // android.media.midi.IMidiManager
        public void setDeviceStatus(IMidiDeviceServer iMidiDeviceServer, MidiDeviceStatus midiDeviceStatus) throws RemoteException {
        }

        @Override // android.media.midi.IMidiManager
        public void unregisterDeviceServer(IMidiDeviceServer iMidiDeviceServer) throws RemoteException {
        }

        @Override // android.media.midi.IMidiManager
        public void unregisterListener(IBinder iBinder, IMidiDeviceListener iMidiDeviceListener) throws RemoteException {
        }

        @Override // android.media.midi.IMidiManager
        public void updateTotalBytes(IMidiDeviceServer iMidiDeviceServer, int i, int i2) throws RemoteException {
        }
    }

    void closeDevice(IBinder iBinder, IBinder iBinder2) throws RemoteException;

    MidiDeviceStatus getDeviceStatus(MidiDeviceInfo midiDeviceInfo) throws RemoteException;

    MidiDeviceInfo[] getDevices() throws RemoteException;

    MidiDeviceInfo[] getDevicesForTransport(int i) throws RemoteException;

    MidiDeviceInfo getServiceDeviceInfo(String str, String str2) throws RemoteException;

    void openBluetoothDevice(IBinder iBinder, BluetoothDevice bluetoothDevice, IMidiDeviceOpenCallback iMidiDeviceOpenCallback) throws RemoteException;

    void openDevice(IBinder iBinder, MidiDeviceInfo midiDeviceInfo, IMidiDeviceOpenCallback iMidiDeviceOpenCallback) throws RemoteException;

    MidiDeviceInfo registerDeviceServer(IMidiDeviceServer iMidiDeviceServer, int i, int i2, String[] strArr, String[] strArr2, Bundle bundle, int i3, int i4) throws RemoteException;

    void registerListener(IBinder iBinder, IMidiDeviceListener iMidiDeviceListener) throws RemoteException;

    void setDeviceStatus(IMidiDeviceServer iMidiDeviceServer, MidiDeviceStatus midiDeviceStatus) throws RemoteException;

    void unregisterDeviceServer(IMidiDeviceServer iMidiDeviceServer) throws RemoteException;

    void unregisterListener(IBinder iBinder, IMidiDeviceListener iMidiDeviceListener) throws RemoteException;

    void updateTotalBytes(IMidiDeviceServer iMidiDeviceServer, int i, int i2) throws RemoteException;

    public static abstract class Stub extends Binder implements IMidiManager {
        public static final String DESCRIPTOR = "android.media.midi.IMidiManager";
        static final int TRANSACTION_closeDevice = 7;
        static final int TRANSACTION_getDeviceStatus = 11;
        static final int TRANSACTION_getDevices = 1;
        static final int TRANSACTION_getDevicesForTransport = 2;
        static final int TRANSACTION_getServiceDeviceInfo = 10;
        static final int TRANSACTION_openBluetoothDevice = 6;
        static final int TRANSACTION_openDevice = 5;
        static final int TRANSACTION_registerDeviceServer = 8;
        static final int TRANSACTION_registerListener = 3;
        static final int TRANSACTION_setDeviceStatus = 12;
        static final int TRANSACTION_unregisterDeviceServer = 9;
        static final int TRANSACTION_unregisterListener = 4;
        static final int TRANSACTION_updateTotalBytes = 13;

        @Override // android.os.IInterface
        public IBinder asBinder() {
            return this;
        }

        @Override // android.os.Binder
        public int getMaxTransactionId() {
            return 12;
        }

        public Stub() {
            attachInterface(this, DESCRIPTOR);
        }

        public static IMidiManager asInterface(IBinder iBinder) {
            if (iBinder == null) {
                return null;
            }
            IInterface iInterfaceQueryLocalInterface = iBinder.queryLocalInterface(DESCRIPTOR);
            if (iInterfaceQueryLocalInterface != null && (iInterfaceQueryLocalInterface instanceof IMidiManager)) {
                return (IMidiManager) iInterfaceQueryLocalInterface;
            }
            return new Proxy(iBinder);
        }

        public static String getDefaultTransactionName(int i) {
            switch (i) {
                case 1:
                    return "getDevices";
                case 2:
                    return "getDevicesForTransport";
                case 3:
                    return "registerListener";
                case 4:
                    return "unregisterListener";
                case 5:
                    return "openDevice";
                case 6:
                    return "openBluetoothDevice";
                case 7:
                    return "closeDevice";
                case 8:
                    return "registerDeviceServer";
                case 9:
                    return "unregisterDeviceServer";
                case 10:
                    return "getServiceDeviceInfo";
                case 11:
                    return "getDeviceStatus";
                case 12:
                    return "setDeviceStatus";
                case 13:
                    return "updateTotalBytes";
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
                parcel.enforceInterface(DESCRIPTOR);
            }
            if (i == 1598968902) {
                parcel2.writeString(DESCRIPTOR);
                return true;
            }
            switch (i) {
                case 1:
                    MidiDeviceInfo[] devices = getDevices();
                    parcel2.writeNoException();
                    parcel2.writeTypedArray(devices, 1);
                    return true;
                case 2:
                    int i3 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    MidiDeviceInfo[] devicesForTransport = getDevicesForTransport(i3);
                    parcel2.writeNoException();
                    parcel2.writeTypedArray(devicesForTransport, 1);
                    return true;
                case 3:
                    IBinder strongBinder = parcel.readStrongBinder();
                    IMidiDeviceListener iMidiDeviceListenerAsInterface = IMidiDeviceListener.Stub.asInterface(parcel.readStrongBinder());
                    parcel.enforceNoDataAvail();
                    registerListener(strongBinder, iMidiDeviceListenerAsInterface);
                    parcel2.writeNoException();
                    return true;
                case 4:
                    IBinder strongBinder2 = parcel.readStrongBinder();
                    IMidiDeviceListener iMidiDeviceListenerAsInterface2 = IMidiDeviceListener.Stub.asInterface(parcel.readStrongBinder());
                    parcel.enforceNoDataAvail();
                    unregisterListener(strongBinder2, iMidiDeviceListenerAsInterface2);
                    parcel2.writeNoException();
                    return true;
                case 5:
                    IBinder strongBinder3 = parcel.readStrongBinder();
                    MidiDeviceInfo midiDeviceInfo = (MidiDeviceInfo) parcel.readTypedObject(MidiDeviceInfo.CREATOR);
                    IMidiDeviceOpenCallback iMidiDeviceOpenCallbackAsInterface = IMidiDeviceOpenCallback.Stub.asInterface(parcel.readStrongBinder());
                    parcel.enforceNoDataAvail();
                    openDevice(strongBinder3, midiDeviceInfo, iMidiDeviceOpenCallbackAsInterface);
                    parcel2.writeNoException();
                    return true;
                case 6:
                    IBinder strongBinder4 = parcel.readStrongBinder();
                    BluetoothDevice bluetoothDevice = (BluetoothDevice) parcel.readTypedObject(BluetoothDevice.CREATOR);
                    IMidiDeviceOpenCallback iMidiDeviceOpenCallbackAsInterface2 = IMidiDeviceOpenCallback.Stub.asInterface(parcel.readStrongBinder());
                    parcel.enforceNoDataAvail();
                    openBluetoothDevice(strongBinder4, bluetoothDevice, iMidiDeviceOpenCallbackAsInterface2);
                    parcel2.writeNoException();
                    return true;
                case 7:
                    IBinder strongBinder5 = parcel.readStrongBinder();
                    IBinder strongBinder6 = parcel.readStrongBinder();
                    parcel.enforceNoDataAvail();
                    closeDevice(strongBinder5, strongBinder6);
                    parcel2.writeNoException();
                    return true;
                case 8:
                    IMidiDeviceServer iMidiDeviceServerAsInterface = IMidiDeviceServer.Stub.asInterface(parcel.readStrongBinder());
                    int i4 = parcel.readInt();
                    int i5 = parcel.readInt();
                    String[] strArrCreateStringArray = parcel.createStringArray();
                    String[] strArrCreateStringArray2 = parcel.createStringArray();
                    Bundle bundle = (Bundle) parcel.readTypedObject(Bundle.CREATOR);
                    int i6 = parcel.readInt();
                    int i7 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    MidiDeviceInfo midiDeviceInfoRegisterDeviceServer = registerDeviceServer(iMidiDeviceServerAsInterface, i4, i5, strArrCreateStringArray, strArrCreateStringArray2, bundle, i6, i7);
                    parcel2.writeNoException();
                    parcel2.writeTypedObject(midiDeviceInfoRegisterDeviceServer, 1);
                    return true;
                case 9:
                    IMidiDeviceServer iMidiDeviceServerAsInterface2 = IMidiDeviceServer.Stub.asInterface(parcel.readStrongBinder());
                    parcel.enforceNoDataAvail();
                    unregisterDeviceServer(iMidiDeviceServerAsInterface2);
                    parcel2.writeNoException();
                    return true;
                case 10:
                    String string = parcel.readString();
                    String string2 = parcel.readString();
                    parcel.enforceNoDataAvail();
                    MidiDeviceInfo serviceDeviceInfo = getServiceDeviceInfo(string, string2);
                    parcel2.writeNoException();
                    parcel2.writeTypedObject(serviceDeviceInfo, 1);
                    return true;
                case 11:
                    MidiDeviceInfo midiDeviceInfo2 = (MidiDeviceInfo) parcel.readTypedObject(MidiDeviceInfo.CREATOR);
                    parcel.enforceNoDataAvail();
                    MidiDeviceStatus deviceStatus = getDeviceStatus(midiDeviceInfo2);
                    parcel2.writeNoException();
                    parcel2.writeTypedObject(deviceStatus, 1);
                    return true;
                case 12:
                    IMidiDeviceServer iMidiDeviceServerAsInterface3 = IMidiDeviceServer.Stub.asInterface(parcel.readStrongBinder());
                    MidiDeviceStatus midiDeviceStatus = (MidiDeviceStatus) parcel.readTypedObject(MidiDeviceStatus.CREATOR);
                    parcel.enforceNoDataAvail();
                    setDeviceStatus(iMidiDeviceServerAsInterface3, midiDeviceStatus);
                    parcel2.writeNoException();
                    return true;
                case 13:
                    IMidiDeviceServer iMidiDeviceServerAsInterface4 = IMidiDeviceServer.Stub.asInterface(parcel.readStrongBinder());
                    int i8 = parcel.readInt();
                    int i9 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    updateTotalBytes(iMidiDeviceServerAsInterface4, i8, i9);
                    parcel2.writeNoException();
                    return true;
                default:
                    return super.onTransact(i, parcel, parcel2, i2);
            }
        }

        private static class Proxy implements IMidiManager {
            private IBinder mRemote;

            Proxy(IBinder iBinder) {
                this.mRemote = iBinder;
            }

            @Override // android.os.IInterface
            public IBinder asBinder() {
                return this.mRemote;
            }

            public String getInterfaceDescriptor() {
                return Stub.DESCRIPTOR;
            }

            @Override // android.media.midi.IMidiManager
            public MidiDeviceInfo[] getDevices() throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    this.mRemote.transact(1, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return (MidiDeviceInfo[]) parcelObtain2.createTypedArray(MidiDeviceInfo.CREATOR);
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.media.midi.IMidiManager
            public MidiDeviceInfo[] getDevicesForTransport(int i) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcelObtain.writeInt(i);
                    this.mRemote.transact(2, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return (MidiDeviceInfo[]) parcelObtain2.createTypedArray(MidiDeviceInfo.CREATOR);
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.media.midi.IMidiManager
            public void registerListener(IBinder iBinder, IMidiDeviceListener iMidiDeviceListener) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcelObtain.writeStrongBinder(iBinder);
                    parcelObtain.writeStrongInterface(iMidiDeviceListener);
                    this.mRemote.transact(3, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.media.midi.IMidiManager
            public void unregisterListener(IBinder iBinder, IMidiDeviceListener iMidiDeviceListener) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcelObtain.writeStrongBinder(iBinder);
                    parcelObtain.writeStrongInterface(iMidiDeviceListener);
                    this.mRemote.transact(4, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.media.midi.IMidiManager
            public void openDevice(IBinder iBinder, MidiDeviceInfo midiDeviceInfo, IMidiDeviceOpenCallback iMidiDeviceOpenCallback) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcelObtain.writeStrongBinder(iBinder);
                    parcelObtain.writeTypedObject(midiDeviceInfo, 0);
                    parcelObtain.writeStrongInterface(iMidiDeviceOpenCallback);
                    this.mRemote.transact(5, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.media.midi.IMidiManager
            public void openBluetoothDevice(IBinder iBinder, BluetoothDevice bluetoothDevice, IMidiDeviceOpenCallback iMidiDeviceOpenCallback) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcelObtain.writeStrongBinder(iBinder);
                    parcelObtain.writeTypedObject(bluetoothDevice, 0);
                    parcelObtain.writeStrongInterface(iMidiDeviceOpenCallback);
                    this.mRemote.transact(6, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.media.midi.IMidiManager
            public void closeDevice(IBinder iBinder, IBinder iBinder2) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcelObtain.writeStrongBinder(iBinder);
                    parcelObtain.writeStrongBinder(iBinder2);
                    this.mRemote.transact(7, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.media.midi.IMidiManager
            public MidiDeviceInfo registerDeviceServer(IMidiDeviceServer iMidiDeviceServer, int i, int i2, String[] strArr, String[] strArr2, Bundle bundle, int i3, int i4) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcelObtain.writeStrongInterface(iMidiDeviceServer);
                    parcelObtain.writeInt(i);
                    parcelObtain.writeInt(i2);
                    parcelObtain.writeStringArray(strArr);
                    parcelObtain.writeStringArray(strArr2);
                    parcelObtain.writeTypedObject(bundle, 0);
                    parcelObtain.writeInt(i3);
                    parcelObtain.writeInt(i4);
                    this.mRemote.transact(8, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return (MidiDeviceInfo) parcelObtain2.readTypedObject(MidiDeviceInfo.CREATOR);
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.media.midi.IMidiManager
            public void unregisterDeviceServer(IMidiDeviceServer iMidiDeviceServer) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcelObtain.writeStrongInterface(iMidiDeviceServer);
                    this.mRemote.transact(9, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.media.midi.IMidiManager
            public MidiDeviceInfo getServiceDeviceInfo(String str, String str2) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcelObtain.writeString(str);
                    parcelObtain.writeString(str2);
                    this.mRemote.transact(10, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return (MidiDeviceInfo) parcelObtain2.readTypedObject(MidiDeviceInfo.CREATOR);
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.media.midi.IMidiManager
            public MidiDeviceStatus getDeviceStatus(MidiDeviceInfo midiDeviceInfo) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcelObtain.writeTypedObject(midiDeviceInfo, 0);
                    this.mRemote.transact(11, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return (MidiDeviceStatus) parcelObtain2.readTypedObject(MidiDeviceStatus.CREATOR);
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.media.midi.IMidiManager
            public void setDeviceStatus(IMidiDeviceServer iMidiDeviceServer, MidiDeviceStatus midiDeviceStatus) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcelObtain.writeStrongInterface(iMidiDeviceServer);
                    parcelObtain.writeTypedObject(midiDeviceStatus, 0);
                    this.mRemote.transact(12, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.media.midi.IMidiManager
            public void updateTotalBytes(IMidiDeviceServer iMidiDeviceServer, int i, int i2) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcelObtain.writeStrongInterface(iMidiDeviceServer);
                    parcelObtain.writeInt(i);
                    parcelObtain.writeInt(i2);
                    this.mRemote.transact(13, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }
        }
    }
}
