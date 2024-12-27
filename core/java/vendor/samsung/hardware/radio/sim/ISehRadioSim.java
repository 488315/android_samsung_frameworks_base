package vendor.samsung.hardware.radio.sim;

import android.os.Binder;
import android.os.IBinder;
import android.os.IInterface;
import android.os.Parcel;
import android.os.RemoteException;
import vendor.samsung.hardware.radio.sim.ISehRadioSimIndication;
import vendor.samsung.hardware.radio.sim.ISehRadioSimResponse;

/* loaded from: classes6.dex */
public interface ISehRadioSim extends IInterface {
    public static final String DESCRIPTOR = "vendor$samsung$hardware$radio$sim$ISehRadioSim".replace('$', '.');
    public static final String HASH = "e94363ef2041c98feabe84e756d983ad196394e8";
    public static final int VERSION = 1;

    void accessPhonebookEntry(int i, int i2, int i3, int i4, SehAdnRecord sehAdnRecord, String str) throws RemoteException;

    void changeIccPersonalization(int i, String str, String str2) throws RemoteException;

    void getAtr(int i) throws RemoteException;

    void getIccCardStatus(int i) throws RemoteException;

    String getInterfaceHash() throws RemoteException;

    int getInterfaceVersion() throws RemoteException;

    void getPhonebookEntry(int i, int i2, int i3) throws RemoteException;

    void getPhonebookStorageInfo(int i, int i2) throws RemoteException;

    void getSimLockInfo(int i, int i2, int i3) throws RemoteException;

    void getUsimPhonebookCapability(int i) throws RemoteException;

    void setResponseFunctions(ISehRadioSimResponse iSehRadioSimResponse, ISehRadioSimIndication iSehRadioSimIndication) throws RemoteException;

    void setSimInitEvent(int i) throws RemoteException;

    void setSimOnOff(int i, int i2) throws RemoteException;

    void supplyIccPersonalization(int i, String str) throws RemoteException;

    void supplyNetworkDepersonalization(int i, String str, int i2) throws RemoteException;

    public static class Default implements ISehRadioSim {
        @Override // vendor.samsung.hardware.radio.sim.ISehRadioSim
        public void getIccCardStatus(int serial) throws RemoteException {
        }

        @Override // vendor.samsung.hardware.radio.sim.ISehRadioSim
        public void supplyNetworkDepersonalization(int serial, String netPin, int subState) throws RemoteException {
        }

        @Override // vendor.samsung.hardware.radio.sim.ISehRadioSim
        public void getPhonebookStorageInfo(int serial, int fileId) throws RemoteException {
        }

        @Override // vendor.samsung.hardware.radio.sim.ISehRadioSim
        public void getUsimPhonebookCapability(int serial) throws RemoteException {
        }

        @Override // vendor.samsung.hardware.radio.sim.ISehRadioSim
        public void setSimOnOff(int serial, int mode) throws RemoteException {
        }

        @Override // vendor.samsung.hardware.radio.sim.ISehRadioSim
        public void setSimInitEvent(int serial) throws RemoteException {
        }

        @Override // vendor.samsung.hardware.radio.sim.ISehRadioSim
        public void getSimLockInfo(int serial, int numOfLockType, int lockType) throws RemoteException {
        }

        @Override // vendor.samsung.hardware.radio.sim.ISehRadioSim
        public void supplyIccPersonalization(int serial, String pin) throws RemoteException {
        }

        @Override // vendor.samsung.hardware.radio.sim.ISehRadioSim
        public void changeIccPersonalization(int serial, String oldPass, String newPass) throws RemoteException {
        }

        @Override // vendor.samsung.hardware.radio.sim.ISehRadioSim
        public void getPhonebookEntry(int serial, int fileId, int index) throws RemoteException {
        }

        @Override // vendor.samsung.hardware.radio.sim.ISehRadioSim
        public void accessPhonebookEntry(int serial, int command, int fileId, int index, SehAdnRecord adnRecord, String pin2) throws RemoteException {
        }

        @Override // vendor.samsung.hardware.radio.sim.ISehRadioSim
        public void getAtr(int serial) throws RemoteException {
        }

        @Override // vendor.samsung.hardware.radio.sim.ISehRadioSim
        public void setResponseFunctions(ISehRadioSimResponse radioResponse, ISehRadioSimIndication radioIndication) throws RemoteException {
        }

        @Override // vendor.samsung.hardware.radio.sim.ISehRadioSim
        public int getInterfaceVersion() {
            return 0;
        }

        @Override // vendor.samsung.hardware.radio.sim.ISehRadioSim
        public String getInterfaceHash() {
            return "";
        }

        @Override // android.os.IInterface
        public IBinder asBinder() {
            return null;
        }
    }

    public static abstract class Stub extends Binder implements ISehRadioSim {
        static final int TRANSACTION_accessPhonebookEntry = 11;
        static final int TRANSACTION_changeIccPersonalization = 9;
        static final int TRANSACTION_getAtr = 12;
        static final int TRANSACTION_getIccCardStatus = 1;
        static final int TRANSACTION_getInterfaceHash = 16777214;
        static final int TRANSACTION_getInterfaceVersion = 16777215;
        static final int TRANSACTION_getPhonebookEntry = 10;
        static final int TRANSACTION_getPhonebookStorageInfo = 3;
        static final int TRANSACTION_getSimLockInfo = 7;
        static final int TRANSACTION_getUsimPhonebookCapability = 4;
        static final int TRANSACTION_setResponseFunctions = 13;
        static final int TRANSACTION_setSimInitEvent = 6;
        static final int TRANSACTION_setSimOnOff = 5;
        static final int TRANSACTION_supplyIccPersonalization = 8;
        static final int TRANSACTION_supplyNetworkDepersonalization = 2;

        public Stub() {
            markVintfStability();
            attachInterface(this, DESCRIPTOR);
        }

        public static ISehRadioSim asInterface(IBinder obj) {
            if (obj == null) {
                return null;
            }
            IInterface iin = obj.queryLocalInterface(DESCRIPTOR);
            if (iin != null && (iin instanceof ISehRadioSim)) {
                return (ISehRadioSim) iin;
            }
            return new Proxy(obj);
        }

        @Override // android.os.IInterface
        public IBinder asBinder() {
            return this;
        }

        @Override // android.os.Binder
        public boolean onTransact(int code, Parcel data, Parcel reply, int flags) throws RemoteException {
            String descriptor = DESCRIPTOR;
            if (code >= 1 && code <= 16777215) {
                data.enforceInterface(descriptor);
            }
            if (code == 1598968902) {
                reply.writeString(descriptor);
                return true;
            }
            if (code == 16777215) {
                reply.writeNoException();
                reply.writeInt(getInterfaceVersion());
                return true;
            }
            if (code == 16777214) {
                reply.writeNoException();
                reply.writeString(getInterfaceHash());
                return true;
            }
            switch (code) {
                case 1:
                    int _arg0 = data.readInt();
                    data.enforceNoDataAvail();
                    getIccCardStatus(_arg0);
                    return true;
                case 2:
                    int _arg02 = data.readInt();
                    String _arg1 = data.readString();
                    int _arg2 = data.readInt();
                    data.enforceNoDataAvail();
                    supplyNetworkDepersonalization(_arg02, _arg1, _arg2);
                    return true;
                case 3:
                    int _arg03 = data.readInt();
                    int _arg12 = data.readInt();
                    data.enforceNoDataAvail();
                    getPhonebookStorageInfo(_arg03, _arg12);
                    return true;
                case 4:
                    int _arg04 = data.readInt();
                    data.enforceNoDataAvail();
                    getUsimPhonebookCapability(_arg04);
                    return true;
                case 5:
                    int _arg05 = data.readInt();
                    int _arg13 = data.readInt();
                    data.enforceNoDataAvail();
                    setSimOnOff(_arg05, _arg13);
                    return true;
                case 6:
                    int _arg06 = data.readInt();
                    data.enforceNoDataAvail();
                    setSimInitEvent(_arg06);
                    return true;
                case 7:
                    int _arg07 = data.readInt();
                    int _arg14 = data.readInt();
                    int _arg22 = data.readInt();
                    data.enforceNoDataAvail();
                    getSimLockInfo(_arg07, _arg14, _arg22);
                    return true;
                case 8:
                    int _arg08 = data.readInt();
                    String _arg15 = data.readString();
                    data.enforceNoDataAvail();
                    supplyIccPersonalization(_arg08, _arg15);
                    return true;
                case 9:
                    int _arg09 = data.readInt();
                    String _arg16 = data.readString();
                    String _arg23 = data.readString();
                    data.enforceNoDataAvail();
                    changeIccPersonalization(_arg09, _arg16, _arg23);
                    return true;
                case 10:
                    int _arg010 = data.readInt();
                    int _arg17 = data.readInt();
                    int _arg24 = data.readInt();
                    data.enforceNoDataAvail();
                    getPhonebookEntry(_arg010, _arg17, _arg24);
                    return true;
                case 11:
                    int _arg011 = data.readInt();
                    int _arg18 = data.readInt();
                    int _arg25 = data.readInt();
                    int _arg3 = data.readInt();
                    SehAdnRecord _arg4 = (SehAdnRecord) data.readTypedObject(SehAdnRecord.CREATOR);
                    String _arg5 = data.readString();
                    data.enforceNoDataAvail();
                    accessPhonebookEntry(_arg011, _arg18, _arg25, _arg3, _arg4, _arg5);
                    return true;
                case 12:
                    int _arg012 = data.readInt();
                    data.enforceNoDataAvail();
                    getAtr(_arg012);
                    return true;
                case 13:
                    ISehRadioSimResponse _arg013 = ISehRadioSimResponse.Stub.asInterface(data.readStrongBinder());
                    ISehRadioSimIndication _arg19 = ISehRadioSimIndication.Stub.asInterface(data.readStrongBinder());
                    data.enforceNoDataAvail();
                    setResponseFunctions(_arg013, _arg19);
                    reply.writeNoException();
                    return true;
                default:
                    return super.onTransact(code, data, reply, flags);
            }
        }

        private static class Proxy implements ISehRadioSim {
            private IBinder mRemote;
            private int mCachedVersion = -1;
            private String mCachedHash = "-1";

            Proxy(IBinder remote) {
                this.mRemote = remote;
            }

            @Override // android.os.IInterface
            public IBinder asBinder() {
                return this.mRemote;
            }

            public String getInterfaceDescriptor() {
                return DESCRIPTOR;
            }

            @Override // vendor.samsung.hardware.radio.sim.ISehRadioSim
            public void getIccCardStatus(int serial) throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                try {
                    _data.writeInterfaceToken(DESCRIPTOR);
                    _data.writeInt(serial);
                    boolean _status = this.mRemote.transact(1, _data, null, 1);
                    if (!_status) {
                        throw new RemoteException("Method getIccCardStatus is unimplemented.");
                    }
                } finally {
                    _data.recycle();
                }
            }

            @Override // vendor.samsung.hardware.radio.sim.ISehRadioSim
            public void supplyNetworkDepersonalization(int serial, String netPin, int subState) throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                try {
                    _data.writeInterfaceToken(DESCRIPTOR);
                    _data.writeInt(serial);
                    _data.writeString(netPin);
                    _data.writeInt(subState);
                    boolean _status = this.mRemote.transact(2, _data, null, 1);
                    if (!_status) {
                        throw new RemoteException("Method supplyNetworkDepersonalization is unimplemented.");
                    }
                } finally {
                    _data.recycle();
                }
            }

            @Override // vendor.samsung.hardware.radio.sim.ISehRadioSim
            public void getPhonebookStorageInfo(int serial, int fileId) throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                try {
                    _data.writeInterfaceToken(DESCRIPTOR);
                    _data.writeInt(serial);
                    _data.writeInt(fileId);
                    boolean _status = this.mRemote.transact(3, _data, null, 1);
                    if (!_status) {
                        throw new RemoteException("Method getPhonebookStorageInfo is unimplemented.");
                    }
                } finally {
                    _data.recycle();
                }
            }

            @Override // vendor.samsung.hardware.radio.sim.ISehRadioSim
            public void getUsimPhonebookCapability(int serial) throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                try {
                    _data.writeInterfaceToken(DESCRIPTOR);
                    _data.writeInt(serial);
                    boolean _status = this.mRemote.transact(4, _data, null, 1);
                    if (!_status) {
                        throw new RemoteException("Method getUsimPhonebookCapability is unimplemented.");
                    }
                } finally {
                    _data.recycle();
                }
            }

            @Override // vendor.samsung.hardware.radio.sim.ISehRadioSim
            public void setSimOnOff(int serial, int mode) throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                try {
                    _data.writeInterfaceToken(DESCRIPTOR);
                    _data.writeInt(serial);
                    _data.writeInt(mode);
                    boolean _status = this.mRemote.transact(5, _data, null, 1);
                    if (!_status) {
                        throw new RemoteException("Method setSimOnOff is unimplemented.");
                    }
                } finally {
                    _data.recycle();
                }
            }

            @Override // vendor.samsung.hardware.radio.sim.ISehRadioSim
            public void setSimInitEvent(int serial) throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                try {
                    _data.writeInterfaceToken(DESCRIPTOR);
                    _data.writeInt(serial);
                    boolean _status = this.mRemote.transact(6, _data, null, 1);
                    if (!_status) {
                        throw new RemoteException("Method setSimInitEvent is unimplemented.");
                    }
                } finally {
                    _data.recycle();
                }
            }

            @Override // vendor.samsung.hardware.radio.sim.ISehRadioSim
            public void getSimLockInfo(int serial, int numOfLockType, int lockType) throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                try {
                    _data.writeInterfaceToken(DESCRIPTOR);
                    _data.writeInt(serial);
                    _data.writeInt(numOfLockType);
                    _data.writeInt(lockType);
                    boolean _status = this.mRemote.transact(7, _data, null, 1);
                    if (!_status) {
                        throw new RemoteException("Method getSimLockInfo is unimplemented.");
                    }
                } finally {
                    _data.recycle();
                }
            }

            @Override // vendor.samsung.hardware.radio.sim.ISehRadioSim
            public void supplyIccPersonalization(int serial, String pin) throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                try {
                    _data.writeInterfaceToken(DESCRIPTOR);
                    _data.writeInt(serial);
                    _data.writeString(pin);
                    boolean _status = this.mRemote.transact(8, _data, null, 1);
                    if (!_status) {
                        throw new RemoteException("Method supplyIccPersonalization is unimplemented.");
                    }
                } finally {
                    _data.recycle();
                }
            }

            @Override // vendor.samsung.hardware.radio.sim.ISehRadioSim
            public void changeIccPersonalization(int serial, String oldPass, String newPass) throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                try {
                    _data.writeInterfaceToken(DESCRIPTOR);
                    _data.writeInt(serial);
                    _data.writeString(oldPass);
                    _data.writeString(newPass);
                    boolean _status = this.mRemote.transact(9, _data, null, 1);
                    if (!_status) {
                        throw new RemoteException("Method changeIccPersonalization is unimplemented.");
                    }
                } finally {
                    _data.recycle();
                }
            }

            @Override // vendor.samsung.hardware.radio.sim.ISehRadioSim
            public void getPhonebookEntry(int serial, int fileId, int index) throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                try {
                    _data.writeInterfaceToken(DESCRIPTOR);
                    _data.writeInt(serial);
                    _data.writeInt(fileId);
                    _data.writeInt(index);
                    boolean _status = this.mRemote.transact(10, _data, null, 1);
                    if (!_status) {
                        throw new RemoteException("Method getPhonebookEntry is unimplemented.");
                    }
                } finally {
                    _data.recycle();
                }
            }

            @Override // vendor.samsung.hardware.radio.sim.ISehRadioSim
            public void accessPhonebookEntry(int serial, int command, int fileId, int index, SehAdnRecord adnRecord, String pin2) throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                try {
                    _data.writeInterfaceToken(DESCRIPTOR);
                    _data.writeInt(serial);
                    _data.writeInt(command);
                    _data.writeInt(fileId);
                    _data.writeInt(index);
                    _data.writeTypedObject(adnRecord, 0);
                    _data.writeString(pin2);
                    boolean _status = this.mRemote.transact(11, _data, null, 1);
                    if (!_status) {
                        throw new RemoteException("Method accessPhonebookEntry is unimplemented.");
                    }
                } finally {
                    _data.recycle();
                }
            }

            @Override // vendor.samsung.hardware.radio.sim.ISehRadioSim
            public void getAtr(int serial) throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                try {
                    _data.writeInterfaceToken(DESCRIPTOR);
                    _data.writeInt(serial);
                    boolean _status = this.mRemote.transact(12, _data, null, 1);
                    if (!_status) {
                        throw new RemoteException("Method getAtr is unimplemented.");
                    }
                } finally {
                    _data.recycle();
                }
            }

            @Override // vendor.samsung.hardware.radio.sim.ISehRadioSim
            public void setResponseFunctions(ISehRadioSimResponse radioResponse, ISehRadioSimIndication radioIndication) throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(DESCRIPTOR);
                    _data.writeStrongInterface(radioResponse);
                    _data.writeStrongInterface(radioIndication);
                    boolean _status = this.mRemote.transact(13, _data, _reply, 0);
                    if (!_status) {
                        throw new RemoteException("Method setResponseFunctions is unimplemented.");
                    }
                    _reply.readException();
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // vendor.samsung.hardware.radio.sim.ISehRadioSim
            public int getInterfaceVersion() throws RemoteException {
                if (this.mCachedVersion == -1) {
                    Parcel data = Parcel.obtain(asBinder());
                    Parcel reply = Parcel.obtain();
                    try {
                        data.writeInterfaceToken(DESCRIPTOR);
                        this.mRemote.transact(16777215, data, reply, 0);
                        reply.readException();
                        this.mCachedVersion = reply.readInt();
                    } finally {
                        reply.recycle();
                        data.recycle();
                    }
                }
                return this.mCachedVersion;
            }

            @Override // vendor.samsung.hardware.radio.sim.ISehRadioSim
            public synchronized String getInterfaceHash() throws RemoteException {
                if ("-1".equals(this.mCachedHash)) {
                    Parcel data = Parcel.obtain(asBinder());
                    Parcel reply = Parcel.obtain();
                    try {
                        data.writeInterfaceToken(DESCRIPTOR);
                        this.mRemote.transact(16777214, data, reply, 0);
                        reply.readException();
                        this.mCachedHash = reply.readString();
                        reply.recycle();
                        data.recycle();
                    } catch (Throwable th) {
                        reply.recycle();
                        data.recycle();
                        throw th;
                    }
                }
                return this.mCachedHash;
            }
        }
    }
}
