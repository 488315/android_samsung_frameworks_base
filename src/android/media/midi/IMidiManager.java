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
            IInterface queryLocalInterface = iBinder.queryLocalInterface(DESCRIPTOR);
            if (queryLocalInterface != null && (queryLocalInterface instanceof IMidiManager)) {
                return (IMidiManager) queryLocalInterface;
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
                    int readInt = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    MidiDeviceInfo[] devicesForTransport = getDevicesForTransport(readInt);
                    parcel2.writeNoException();
                    parcel2.writeTypedArray(devicesForTransport, 1);
                    return true;
                case 3:
                    IBinder readStrongBinder = parcel.readStrongBinder();
                    IMidiDeviceListener asInterface = IMidiDeviceListener.Stub.asInterface(parcel.readStrongBinder());
                    parcel.enforceNoDataAvail();
                    registerListener(readStrongBinder, asInterface);
                    parcel2.writeNoException();
                    return true;
                case 4:
                    IBinder readStrongBinder2 = parcel.readStrongBinder();
                    IMidiDeviceListener asInterface2 = IMidiDeviceListener.Stub.asInterface(parcel.readStrongBinder());
                    parcel.enforceNoDataAvail();
                    unregisterListener(readStrongBinder2, asInterface2);
                    parcel2.writeNoException();
                    return true;
                case 5:
                    IBinder readStrongBinder3 = parcel.readStrongBinder();
                    MidiDeviceInfo midiDeviceInfo = (MidiDeviceInfo) parcel.readTypedObject(MidiDeviceInfo.CREATOR);
                    IMidiDeviceOpenCallback asInterface3 = IMidiDeviceOpenCallback.Stub.asInterface(parcel.readStrongBinder());
                    parcel.enforceNoDataAvail();
                    openDevice(readStrongBinder3, midiDeviceInfo, asInterface3);
                    parcel2.writeNoException();
                    return true;
                case 6:
                    IBinder readStrongBinder4 = parcel.readStrongBinder();
                    BluetoothDevice bluetoothDevice = (BluetoothDevice) parcel.readTypedObject(BluetoothDevice.CREATOR);
                    IMidiDeviceOpenCallback asInterface4 = IMidiDeviceOpenCallback.Stub.asInterface(parcel.readStrongBinder());
                    parcel.enforceNoDataAvail();
                    openBluetoothDevice(readStrongBinder4, bluetoothDevice, asInterface4);
                    parcel2.writeNoException();
                    return true;
                case 7:
                    IBinder readStrongBinder5 = parcel.readStrongBinder();
                    IBinder readStrongBinder6 = parcel.readStrongBinder();
                    parcel.enforceNoDataAvail();
                    closeDevice(readStrongBinder5, readStrongBinder6);
                    parcel2.writeNoException();
                    return true;
                case 8:
                    IMidiDeviceServer asInterface5 = IMidiDeviceServer.Stub.asInterface(parcel.readStrongBinder());
                    int readInt2 = parcel.readInt();
                    int readInt3 = parcel.readInt();
                    String[] createStringArray = parcel.createStringArray();
                    String[] createStringArray2 = parcel.createStringArray();
                    Bundle bundle = (Bundle) parcel.readTypedObject(Bundle.CREATOR);
                    int readInt4 = parcel.readInt();
                    int readInt5 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    MidiDeviceInfo registerDeviceServer = registerDeviceServer(asInterface5, readInt2, readInt3, createStringArray, createStringArray2, bundle, readInt4, readInt5);
                    parcel2.writeNoException();
                    parcel2.writeTypedObject(registerDeviceServer, 1);
                    return true;
                case 9:
                    IMidiDeviceServer asInterface6 = IMidiDeviceServer.Stub.asInterface(parcel.readStrongBinder());
                    parcel.enforceNoDataAvail();
                    unregisterDeviceServer(asInterface6);
                    parcel2.writeNoException();
                    return true;
                case 10:
                    String readString = parcel.readString();
                    String readString2 = parcel.readString();
                    parcel.enforceNoDataAvail();
                    MidiDeviceInfo serviceDeviceInfo = getServiceDeviceInfo(readString, readString2);
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
                    IMidiDeviceServer asInterface7 = IMidiDeviceServer.Stub.asInterface(parcel.readStrongBinder());
                    MidiDeviceStatus midiDeviceStatus = (MidiDeviceStatus) parcel.readTypedObject(MidiDeviceStatus.CREATOR);
                    parcel.enforceNoDataAvail();
                    setDeviceStatus(asInterface7, midiDeviceStatus);
                    parcel2.writeNoException();
                    return true;
                case 13:
                    IMidiDeviceServer asInterface8 = IMidiDeviceServer.Stub.asInterface(parcel.readStrongBinder());
                    int readInt6 = parcel.readInt();
                    int readInt7 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    updateTotalBytes(asInterface8, readInt6, readInt7);
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
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    this.mRemote.transact(1, obtain, obtain2, 0);
                    obtain2.readException();
                    return (MidiDeviceInfo[]) obtain2.createTypedArray(MidiDeviceInfo.CREATOR);
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.media.midi.IMidiManager
            public MidiDeviceInfo[] getDevicesForTransport(int i) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    obtain.writeInt(i);
                    this.mRemote.transact(2, obtain, obtain2, 0);
                    obtain2.readException();
                    return (MidiDeviceInfo[]) obtain2.createTypedArray(MidiDeviceInfo.CREATOR);
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.media.midi.IMidiManager
            public void registerListener(IBinder iBinder, IMidiDeviceListener iMidiDeviceListener) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    obtain.writeStrongBinder(iBinder);
                    obtain.writeStrongInterface(iMidiDeviceListener);
                    this.mRemote.transact(3, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.media.midi.IMidiManager
            public void unregisterListener(IBinder iBinder, IMidiDeviceListener iMidiDeviceListener) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    obtain.writeStrongBinder(iBinder);
                    obtain.writeStrongInterface(iMidiDeviceListener);
                    this.mRemote.transact(4, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.media.midi.IMidiManager
            public void openDevice(IBinder iBinder, MidiDeviceInfo midiDeviceInfo, IMidiDeviceOpenCallback iMidiDeviceOpenCallback) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    obtain.writeStrongBinder(iBinder);
                    obtain.writeTypedObject(midiDeviceInfo, 0);
                    obtain.writeStrongInterface(iMidiDeviceOpenCallback);
                    this.mRemote.transact(5, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.media.midi.IMidiManager
            public void openBluetoothDevice(IBinder iBinder, BluetoothDevice bluetoothDevice, IMidiDeviceOpenCallback iMidiDeviceOpenCallback) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    obtain.writeStrongBinder(iBinder);
                    obtain.writeTypedObject(bluetoothDevice, 0);
                    obtain.writeStrongInterface(iMidiDeviceOpenCallback);
                    this.mRemote.transact(6, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.media.midi.IMidiManager
            public void closeDevice(IBinder iBinder, IBinder iBinder2) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    obtain.writeStrongBinder(iBinder);
                    obtain.writeStrongBinder(iBinder2);
                    this.mRemote.transact(7, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.media.midi.IMidiManager
            public MidiDeviceInfo registerDeviceServer(IMidiDeviceServer iMidiDeviceServer, int i, int i2, String[] strArr, String[] strArr2, Bundle bundle, int i3, int i4) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    obtain.writeStrongInterface(iMidiDeviceServer);
                    obtain.writeInt(i);
                    obtain.writeInt(i2);
                    obtain.writeStringArray(strArr);
                    obtain.writeStringArray(strArr2);
                    obtain.writeTypedObject(bundle, 0);
                    obtain.writeInt(i3);
                    obtain.writeInt(i4);
                    this.mRemote.transact(8, obtain, obtain2, 0);
                    obtain2.readException();
                    return (MidiDeviceInfo) obtain2.readTypedObject(MidiDeviceInfo.CREATOR);
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.media.midi.IMidiManager
            public void unregisterDeviceServer(IMidiDeviceServer iMidiDeviceServer) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    obtain.writeStrongInterface(iMidiDeviceServer);
                    this.mRemote.transact(9, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.media.midi.IMidiManager
            public MidiDeviceInfo getServiceDeviceInfo(String str, String str2) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    obtain.writeString(str);
                    obtain.writeString(str2);
                    this.mRemote.transact(10, obtain, obtain2, 0);
                    obtain2.readException();
                    return (MidiDeviceInfo) obtain2.readTypedObject(MidiDeviceInfo.CREATOR);
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.media.midi.IMidiManager
            public MidiDeviceStatus getDeviceStatus(MidiDeviceInfo midiDeviceInfo) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    obtain.writeTypedObject(midiDeviceInfo, 0);
                    this.mRemote.transact(11, obtain, obtain2, 0);
                    obtain2.readException();
                    return (MidiDeviceStatus) obtain2.readTypedObject(MidiDeviceStatus.CREATOR);
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.media.midi.IMidiManager
            public void setDeviceStatus(IMidiDeviceServer iMidiDeviceServer, MidiDeviceStatus midiDeviceStatus) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    obtain.writeStrongInterface(iMidiDeviceServer);
                    obtain.writeTypedObject(midiDeviceStatus, 0);
                    this.mRemote.transact(12, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.media.midi.IMidiManager
            public void updateTotalBytes(IMidiDeviceServer iMidiDeviceServer, int i, int i2) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    obtain.writeStrongInterface(iMidiDeviceServer);
                    obtain.writeInt(i);
                    obtain.writeInt(i2);
                    this.mRemote.transact(13, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }
        }
    }
}
