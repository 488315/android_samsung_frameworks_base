package android.media.midi;

import android.os.Binder;
import android.os.IBinder;
import android.os.IInterface;
import android.os.Parcel;
import android.os.RemoteException;
import java.io.FileDescriptor;

/* loaded from: classes3.dex */
public interface IMidiDeviceServer extends IInterface {

    public static class Default implements IMidiDeviceServer {
        @Override // android.os.IInterface
        public IBinder asBinder() {
            return null;
        }

        @Override // android.media.midi.IMidiDeviceServer
        public void closeDevice() throws RemoteException {
        }

        @Override // android.media.midi.IMidiDeviceServer
        public void closePort(IBinder iBinder) throws RemoteException {
        }

        @Override // android.media.midi.IMidiDeviceServer
        public int connectPorts(IBinder iBinder, FileDescriptor fileDescriptor, int i) throws RemoteException {
            return 0;
        }

        @Override // android.media.midi.IMidiDeviceServer
        public MidiDeviceInfo getDeviceInfo() throws RemoteException {
            return null;
        }

        @Override // android.media.midi.IMidiDeviceServer
        public FileDescriptor openInputPort(IBinder iBinder, int i) throws RemoteException {
            return null;
        }

        @Override // android.media.midi.IMidiDeviceServer
        public FileDescriptor openOutputPort(IBinder iBinder, int i) throws RemoteException {
            return null;
        }

        @Override // android.media.midi.IMidiDeviceServer
        public void setDeviceInfo(MidiDeviceInfo midiDeviceInfo) throws RemoteException {
        }
    }

    void closeDevice() throws RemoteException;

    void closePort(IBinder iBinder) throws RemoteException;

    int connectPorts(IBinder iBinder, FileDescriptor fileDescriptor, int i) throws RemoteException;

    MidiDeviceInfo getDeviceInfo() throws RemoteException;

    FileDescriptor openInputPort(IBinder iBinder, int i) throws RemoteException;

    FileDescriptor openOutputPort(IBinder iBinder, int i) throws RemoteException;

    void setDeviceInfo(MidiDeviceInfo midiDeviceInfo) throws RemoteException;

    public static abstract class Stub extends Binder implements IMidiDeviceServer {
        public static final String DESCRIPTOR = "android.media.midi.IMidiDeviceServer";
        static final int TRANSACTION_closeDevice = 4;
        static final int TRANSACTION_closePort = 3;
        static final int TRANSACTION_connectPorts = 5;
        static final int TRANSACTION_getDeviceInfo = 6;
        static final int TRANSACTION_openInputPort = 1;
        static final int TRANSACTION_openOutputPort = 2;
        static final int TRANSACTION_setDeviceInfo = 7;

        @Override // android.os.IInterface
        public IBinder asBinder() {
            return this;
        }

        @Override // android.os.Binder
        public int getMaxTransactionId() {
            return 6;
        }

        public Stub() {
            attachInterface(this, DESCRIPTOR);
        }

        public static IMidiDeviceServer asInterface(IBinder iBinder) {
            if (iBinder == null) {
                return null;
            }
            IInterface iInterfaceQueryLocalInterface = iBinder.queryLocalInterface(DESCRIPTOR);
            if (iInterfaceQueryLocalInterface != null && (iInterfaceQueryLocalInterface instanceof IMidiDeviceServer)) {
                return (IMidiDeviceServer) iInterfaceQueryLocalInterface;
            }
            return new Proxy(iBinder);
        }

        public static String getDefaultTransactionName(int i) {
            switch (i) {
                case 1:
                    return "openInputPort";
                case 2:
                    return "openOutputPort";
                case 3:
                    return "closePort";
                case 4:
                    return "closeDevice";
                case 5:
                    return "connectPorts";
                case 6:
                    return "getDeviceInfo";
                case 7:
                    return "setDeviceInfo";
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
                    IBinder strongBinder = parcel.readStrongBinder();
                    int i3 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    FileDescriptor fileDescriptorOpenInputPort = openInputPort(strongBinder, i3);
                    parcel2.writeNoException();
                    parcel2.writeRawFileDescriptor(fileDescriptorOpenInputPort);
                    return true;
                case 2:
                    IBinder strongBinder2 = parcel.readStrongBinder();
                    int i4 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    FileDescriptor fileDescriptorOpenOutputPort = openOutputPort(strongBinder2, i4);
                    parcel2.writeNoException();
                    parcel2.writeRawFileDescriptor(fileDescriptorOpenOutputPort);
                    return true;
                case 3:
                    IBinder strongBinder3 = parcel.readStrongBinder();
                    parcel.enforceNoDataAvail();
                    closePort(strongBinder3);
                    parcel2.writeNoException();
                    return true;
                case 4:
                    closeDevice();
                    return true;
                case 5:
                    IBinder strongBinder4 = parcel.readStrongBinder();
                    FileDescriptor rawFileDescriptor = parcel.readRawFileDescriptor();
                    int i5 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    int iConnectPorts = connectPorts(strongBinder4, rawFileDescriptor, i5);
                    parcel2.writeNoException();
                    parcel2.writeInt(iConnectPorts);
                    return true;
                case 6:
                    MidiDeviceInfo deviceInfo = getDeviceInfo();
                    parcel2.writeNoException();
                    parcel2.writeTypedObject(deviceInfo, 1);
                    return true;
                case 7:
                    MidiDeviceInfo midiDeviceInfo = (MidiDeviceInfo) parcel.readTypedObject(MidiDeviceInfo.CREATOR);
                    parcel.enforceNoDataAvail();
                    setDeviceInfo(midiDeviceInfo);
                    return true;
                default:
                    return super.onTransact(i, parcel, parcel2, i2);
            }
        }

        private static class Proxy implements IMidiDeviceServer {
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

            @Override // android.media.midi.IMidiDeviceServer
            public FileDescriptor openInputPort(IBinder iBinder, int i) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcelObtain.writeStrongBinder(iBinder);
                    parcelObtain.writeInt(i);
                    this.mRemote.transact(1, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return parcelObtain2.readRawFileDescriptor();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.media.midi.IMidiDeviceServer
            public FileDescriptor openOutputPort(IBinder iBinder, int i) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcelObtain.writeStrongBinder(iBinder);
                    parcelObtain.writeInt(i);
                    this.mRemote.transact(2, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return parcelObtain2.readRawFileDescriptor();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.media.midi.IMidiDeviceServer
            public void closePort(IBinder iBinder) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcelObtain.writeStrongBinder(iBinder);
                    this.mRemote.transact(3, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.media.midi.IMidiDeviceServer
            public void closeDevice() throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    this.mRemote.transact(4, parcelObtain, null, 1);
                } finally {
                    parcelObtain.recycle();
                }
            }

            @Override // android.media.midi.IMidiDeviceServer
            public int connectPorts(IBinder iBinder, FileDescriptor fileDescriptor, int i) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcelObtain.writeStrongBinder(iBinder);
                    parcelObtain.writeRawFileDescriptor(fileDescriptor);
                    parcelObtain.writeInt(i);
                    this.mRemote.transact(5, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return parcelObtain2.readInt();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.media.midi.IMidiDeviceServer
            public MidiDeviceInfo getDeviceInfo() throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    this.mRemote.transact(6, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return (MidiDeviceInfo) parcelObtain2.readTypedObject(MidiDeviceInfo.CREATOR);
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.media.midi.IMidiDeviceServer
            public void setDeviceInfo(MidiDeviceInfo midiDeviceInfo) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcelObtain.writeTypedObject(midiDeviceInfo, 0);
                    this.mRemote.transact(7, parcelObtain, null, 1);
                } finally {
                    parcelObtain.recycle();
                }
            }
        }
    }
}
