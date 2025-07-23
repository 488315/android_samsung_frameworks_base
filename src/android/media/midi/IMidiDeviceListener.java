package android.media.midi;

import android.os.Binder;
import android.os.IBinder;
import android.os.IInterface;
import android.os.Parcel;
import android.os.RemoteException;

/* loaded from: classes3.dex */
public interface IMidiDeviceListener extends IInterface {

    public static class Default implements IMidiDeviceListener {
        @Override // android.os.IInterface
        public IBinder asBinder() {
            return null;
        }

        @Override // android.media.midi.IMidiDeviceListener
        public void onDeviceAdded(MidiDeviceInfo midiDeviceInfo) throws RemoteException {
        }

        @Override // android.media.midi.IMidiDeviceListener
        public void onDeviceRemoved(MidiDeviceInfo midiDeviceInfo) throws RemoteException {
        }

        @Override // android.media.midi.IMidiDeviceListener
        public void onDeviceStatusChanged(MidiDeviceStatus midiDeviceStatus) throws RemoteException {
        }
    }

    void onDeviceAdded(MidiDeviceInfo midiDeviceInfo) throws RemoteException;

    void onDeviceRemoved(MidiDeviceInfo midiDeviceInfo) throws RemoteException;

    void onDeviceStatusChanged(MidiDeviceStatus midiDeviceStatus) throws RemoteException;

    public static abstract class Stub extends Binder implements IMidiDeviceListener {
        public static final String DESCRIPTOR = "android.media.midi.IMidiDeviceListener";
        static final int TRANSACTION_onDeviceAdded = 1;
        static final int TRANSACTION_onDeviceRemoved = 2;
        static final int TRANSACTION_onDeviceStatusChanged = 3;

        @Override // android.os.IInterface
        public IBinder asBinder() {
            return this;
        }

        @Override // android.os.Binder
        public int getMaxTransactionId() {
            return 2;
        }

        public Stub() {
            attachInterface(this, DESCRIPTOR);
        }

        public static IMidiDeviceListener asInterface(IBinder iBinder) {
            if (iBinder == null) {
                return null;
            }
            IInterface queryLocalInterface = iBinder.queryLocalInterface(DESCRIPTOR);
            if (queryLocalInterface != null && (queryLocalInterface instanceof IMidiDeviceListener)) {
                return (IMidiDeviceListener) queryLocalInterface;
            }
            return new Proxy(iBinder);
        }

        public static String getDefaultTransactionName(int i) {
            if (i == 1) {
                return "onDeviceAdded";
            }
            if (i == 2) {
                return "onDeviceRemoved";
            }
            if (i != 3) {
                return null;
            }
            return "onDeviceStatusChanged";
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
            if (i == 1) {
                MidiDeviceInfo midiDeviceInfo = (MidiDeviceInfo) parcel.readTypedObject(MidiDeviceInfo.CREATOR);
                parcel.enforceNoDataAvail();
                onDeviceAdded(midiDeviceInfo);
            } else if (i == 2) {
                MidiDeviceInfo midiDeviceInfo2 = (MidiDeviceInfo) parcel.readTypedObject(MidiDeviceInfo.CREATOR);
                parcel.enforceNoDataAvail();
                onDeviceRemoved(midiDeviceInfo2);
            } else if (i == 3) {
                MidiDeviceStatus midiDeviceStatus = (MidiDeviceStatus) parcel.readTypedObject(MidiDeviceStatus.CREATOR);
                parcel.enforceNoDataAvail();
                onDeviceStatusChanged(midiDeviceStatus);
            } else {
                return super.onTransact(i, parcel, parcel2, i2);
            }
            return true;
        }

        private static class Proxy implements IMidiDeviceListener {
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

            @Override // android.media.midi.IMidiDeviceListener
            public void onDeviceAdded(MidiDeviceInfo midiDeviceInfo) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    obtain.writeTypedObject(midiDeviceInfo, 0);
                    this.mRemote.transact(1, obtain, null, 1);
                } finally {
                    obtain.recycle();
                }
            }

            @Override // android.media.midi.IMidiDeviceListener
            public void onDeviceRemoved(MidiDeviceInfo midiDeviceInfo) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    obtain.writeTypedObject(midiDeviceInfo, 0);
                    this.mRemote.transact(2, obtain, null, 1);
                } finally {
                    obtain.recycle();
                }
            }

            @Override // android.media.midi.IMidiDeviceListener
            public void onDeviceStatusChanged(MidiDeviceStatus midiDeviceStatus) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    obtain.writeTypedObject(midiDeviceStatus, 0);
                    this.mRemote.transact(3, obtain, null, 1);
                } finally {
                    obtain.recycle();
                }
            }
        }
    }
}
