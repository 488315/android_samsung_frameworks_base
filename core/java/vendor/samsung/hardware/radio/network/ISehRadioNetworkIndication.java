package vendor.samsung.hardware.radio.network;

import android.os.Binder;
import android.os.IBinder;
import android.os.IInterface;
import android.os.Parcel;
import android.os.RemoteException;

/* loaded from: classes6.dex */
public interface ISehRadioNetworkIndication extends IInterface {
    public static final String DESCRIPTOR = "vendor$samsung$hardware$radio$network$ISehRadioNetworkIndication".replace('$', '.');
    public static final String HASH = "8fb94cb4cf759f224987eb2d1908582a1f6f1600";
    public static final int VERSION = 1;

    void acbInfoChanged(int i, int[] iArr) throws RemoteException;

    void callDetailsChanged(int i, SehCallDetails[] sehCallDetailsArr) throws RemoteException;

    void csFallback(int i, int i2) throws RemoteException;

    void currentNetworkScanIsRequested(int i, byte b) throws RemoteException;

    void eriInfoReceived(int i, SehEriInfo sehEriInfo) throws RemoteException;

    void execute(int i, String str) throws RemoteException;

    void extendedRegistrationState(int i, SehExtendedRegStateResult sehExtendedRegStateResult) throws RemoteException;

    String getInterfaceHash() throws RemoteException;

    int getInterfaceVersion() throws RemoteException;

    void imsPreferenceChanged(int i, int[] iArr) throws RemoteException;

    void needTurnOnRadioIndication(int i) throws RemoteException;

    void nrBearerAllocationChanged(int i, int i2) throws RemoteException;

    void nrIconTypeChanged(int i, int i2) throws RemoteException;

    void nrNetworkTypeAdded(int i, int i2) throws RemoteException;

    void roamingNetworkScanIsRequested(int i, byte[] bArr) throws RemoteException;

    void signalLevelInfoChanged(int i, SehSignalBar sehSignalBar) throws RemoteException;

    void vendorConfigurationChanged(int i, SehVendorConfiguration[] sehVendorConfigurationArr) throws RemoteException;

    public static class Default implements ISehRadioNetworkIndication {
        @Override // vendor.samsung.hardware.radio.network.ISehRadioNetworkIndication
        public void acbInfoChanged(int type, int[] acbInfo) throws RemoteException {
        }

        @Override // vendor.samsung.hardware.radio.network.ISehRadioNetworkIndication
        public void callDetailsChanged(int type, SehCallDetails[] callDetails) throws RemoteException {
        }

        @Override // vendor.samsung.hardware.radio.network.ISehRadioNetworkIndication
        public void csFallback(int type, int state) throws RemoteException {
        }

        @Override // vendor.samsung.hardware.radio.network.ISehRadioNetworkIndication
        public void currentNetworkScanIsRequested(int type, byte mode) throws RemoteException {
        }

        @Override // vendor.samsung.hardware.radio.network.ISehRadioNetworkIndication
        public void eriInfoReceived(int type, SehEriInfo eriInfo) throws RemoteException {
        }

        @Override // vendor.samsung.hardware.radio.network.ISehRadioNetworkIndication
        public void execute(int type, String cmd) throws RemoteException {
        }

        @Override // vendor.samsung.hardware.radio.network.ISehRadioNetworkIndication
        public void extendedRegistrationState(int type, SehExtendedRegStateResult state) throws RemoteException {
        }

        @Override // vendor.samsung.hardware.radio.network.ISehRadioNetworkIndication
        public void imsPreferenceChanged(int type, int[] imsPref) throws RemoteException {
        }

        @Override // vendor.samsung.hardware.radio.network.ISehRadioNetworkIndication
        public void needTurnOnRadioIndication(int type) throws RemoteException {
        }

        @Override // vendor.samsung.hardware.radio.network.ISehRadioNetworkIndication
        public void nrBearerAllocationChanged(int type, int status) throws RemoteException {
        }

        @Override // vendor.samsung.hardware.radio.network.ISehRadioNetworkIndication
        public void nrIconTypeChanged(int type, int nrIconType) throws RemoteException {
        }

        @Override // vendor.samsung.hardware.radio.network.ISehRadioNetworkIndication
        public void nrNetworkTypeAdded(int type, int status) throws RemoteException {
        }

        @Override // vendor.samsung.hardware.radio.network.ISehRadioNetworkIndication
        public void roamingNetworkScanIsRequested(int type, byte[] scanData) throws RemoteException {
        }

        @Override // vendor.samsung.hardware.radio.network.ISehRadioNetworkIndication
        public void signalLevelInfoChanged(int type, SehSignalBar signalBarInfo) throws RemoteException {
        }

        @Override // vendor.samsung.hardware.radio.network.ISehRadioNetworkIndication
        public void vendorConfigurationChanged(int type, SehVendorConfiguration[] configurations) throws RemoteException {
        }

        @Override // vendor.samsung.hardware.radio.network.ISehRadioNetworkIndication
        public int getInterfaceVersion() {
            return 0;
        }

        @Override // vendor.samsung.hardware.radio.network.ISehRadioNetworkIndication
        public String getInterfaceHash() {
            return "";
        }

        @Override // android.os.IInterface
        public IBinder asBinder() {
            return null;
        }
    }

    public static abstract class Stub extends Binder implements ISehRadioNetworkIndication {
        static final int TRANSACTION_acbInfoChanged = 1;
        static final int TRANSACTION_callDetailsChanged = 2;
        static final int TRANSACTION_csFallback = 3;
        static final int TRANSACTION_currentNetworkScanIsRequested = 4;
        static final int TRANSACTION_eriInfoReceived = 5;
        static final int TRANSACTION_execute = 6;
        static final int TRANSACTION_extendedRegistrationState = 7;
        static final int TRANSACTION_getInterfaceHash = 16777214;
        static final int TRANSACTION_getInterfaceVersion = 16777215;
        static final int TRANSACTION_imsPreferenceChanged = 8;
        static final int TRANSACTION_needTurnOnRadioIndication = 9;
        static final int TRANSACTION_nrBearerAllocationChanged = 10;
        static final int TRANSACTION_nrIconTypeChanged = 11;
        static final int TRANSACTION_nrNetworkTypeAdded = 12;
        static final int TRANSACTION_roamingNetworkScanIsRequested = 13;
        static final int TRANSACTION_signalLevelInfoChanged = 14;
        static final int TRANSACTION_vendorConfigurationChanged = 15;

        public Stub() {
            markVintfStability();
            attachInterface(this, DESCRIPTOR);
        }

        public static ISehRadioNetworkIndication asInterface(IBinder obj) {
            if (obj == null) {
                return null;
            }
            IInterface iin = obj.queryLocalInterface(DESCRIPTOR);
            if (iin != null && (iin instanceof ISehRadioNetworkIndication)) {
                return (ISehRadioNetworkIndication) iin;
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
                    int[] _arg1 = data.createIntArray();
                    data.enforceNoDataAvail();
                    acbInfoChanged(_arg0, _arg1);
                    return true;
                case 2:
                    int _arg02 = data.readInt();
                    SehCallDetails[] _arg12 = (SehCallDetails[]) data.createTypedArray(SehCallDetails.CREATOR);
                    data.enforceNoDataAvail();
                    callDetailsChanged(_arg02, _arg12);
                    reply.writeNoException();
                    return true;
                case 3:
                    int _arg03 = data.readInt();
                    int _arg13 = data.readInt();
                    data.enforceNoDataAvail();
                    csFallback(_arg03, _arg13);
                    return true;
                case 4:
                    int _arg04 = data.readInt();
                    byte _arg14 = data.readByte();
                    data.enforceNoDataAvail();
                    currentNetworkScanIsRequested(_arg04, _arg14);
                    return true;
                case 5:
                    int _arg05 = data.readInt();
                    SehEriInfo _arg15 = (SehEriInfo) data.readTypedObject(SehEriInfo.CREATOR);
                    data.enforceNoDataAvail();
                    eriInfoReceived(_arg05, _arg15);
                    reply.writeNoException();
                    return true;
                case 6:
                    int _arg06 = data.readInt();
                    String _arg16 = data.readString();
                    data.enforceNoDataAvail();
                    execute(_arg06, _arg16);
                    return true;
                case 7:
                    int _arg07 = data.readInt();
                    SehExtendedRegStateResult _arg17 = (SehExtendedRegStateResult) data.readTypedObject(SehExtendedRegStateResult.CREATOR);
                    data.enforceNoDataAvail();
                    extendedRegistrationState(_arg07, _arg17);
                    reply.writeNoException();
                    return true;
                case 8:
                    int _arg08 = data.readInt();
                    int[] _arg18 = data.createIntArray();
                    data.enforceNoDataAvail();
                    imsPreferenceChanged(_arg08, _arg18);
                    return true;
                case 9:
                    int _arg09 = data.readInt();
                    data.enforceNoDataAvail();
                    needTurnOnRadioIndication(_arg09);
                    return true;
                case 10:
                    int _arg010 = data.readInt();
                    int _arg19 = data.readInt();
                    data.enforceNoDataAvail();
                    nrBearerAllocationChanged(_arg010, _arg19);
                    return true;
                case 11:
                    int _arg011 = data.readInt();
                    int _arg110 = data.readInt();
                    data.enforceNoDataAvail();
                    nrIconTypeChanged(_arg011, _arg110);
                    return true;
                case 12:
                    int _arg012 = data.readInt();
                    int _arg111 = data.readInt();
                    data.enforceNoDataAvail();
                    nrNetworkTypeAdded(_arg012, _arg111);
                    return true;
                case 13:
                    int _arg013 = data.readInt();
                    byte[] _arg112 = data.createByteArray();
                    data.enforceNoDataAvail();
                    roamingNetworkScanIsRequested(_arg013, _arg112);
                    return true;
                case 14:
                    int _arg014 = data.readInt();
                    SehSignalBar _arg113 = (SehSignalBar) data.readTypedObject(SehSignalBar.CREATOR);
                    data.enforceNoDataAvail();
                    signalLevelInfoChanged(_arg014, _arg113);
                    reply.writeNoException();
                    return true;
                case 15:
                    int _arg015 = data.readInt();
                    SehVendorConfiguration[] _arg114 = (SehVendorConfiguration[]) data.createTypedArray(SehVendorConfiguration.CREATOR);
                    data.enforceNoDataAvail();
                    vendorConfigurationChanged(_arg015, _arg114);
                    reply.writeNoException();
                    return true;
                default:
                    return super.onTransact(code, data, reply, flags);
            }
        }

        private static class Proxy implements ISehRadioNetworkIndication {
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

            @Override // vendor.samsung.hardware.radio.network.ISehRadioNetworkIndication
            public void acbInfoChanged(int type, int[] acbInfo) throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                try {
                    _data.writeInterfaceToken(DESCRIPTOR);
                    _data.writeInt(type);
                    _data.writeIntArray(acbInfo);
                    boolean _status = this.mRemote.transact(1, _data, null, 1);
                    if (!_status) {
                        throw new RemoteException("Method acbInfoChanged is unimplemented.");
                    }
                } finally {
                    _data.recycle();
                }
            }

            @Override // vendor.samsung.hardware.radio.network.ISehRadioNetworkIndication
            public void callDetailsChanged(int type, SehCallDetails[] callDetails) throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(DESCRIPTOR);
                    _data.writeInt(type);
                    _data.writeTypedArray(callDetails, 0);
                    boolean _status = this.mRemote.transact(2, _data, _reply, 0);
                    if (!_status) {
                        throw new RemoteException("Method callDetailsChanged is unimplemented.");
                    }
                    _reply.readException();
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // vendor.samsung.hardware.radio.network.ISehRadioNetworkIndication
            public void csFallback(int type, int state) throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                try {
                    _data.writeInterfaceToken(DESCRIPTOR);
                    _data.writeInt(type);
                    _data.writeInt(state);
                    boolean _status = this.mRemote.transact(3, _data, null, 1);
                    if (!_status) {
                        throw new RemoteException("Method csFallback is unimplemented.");
                    }
                } finally {
                    _data.recycle();
                }
            }

            @Override // vendor.samsung.hardware.radio.network.ISehRadioNetworkIndication
            public void currentNetworkScanIsRequested(int type, byte mode) throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                try {
                    _data.writeInterfaceToken(DESCRIPTOR);
                    _data.writeInt(type);
                    _data.writeByte(mode);
                    boolean _status = this.mRemote.transact(4, _data, null, 1);
                    if (!_status) {
                        throw new RemoteException("Method currentNetworkScanIsRequested is unimplemented.");
                    }
                } finally {
                    _data.recycle();
                }
            }

            @Override // vendor.samsung.hardware.radio.network.ISehRadioNetworkIndication
            public void eriInfoReceived(int type, SehEriInfo eriInfo) throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(DESCRIPTOR);
                    _data.writeInt(type);
                    _data.writeTypedObject(eriInfo, 0);
                    boolean _status = this.mRemote.transact(5, _data, _reply, 0);
                    if (!_status) {
                        throw new RemoteException("Method eriInfoReceived is unimplemented.");
                    }
                    _reply.readException();
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // vendor.samsung.hardware.radio.network.ISehRadioNetworkIndication
            public void execute(int type, String cmd) throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                try {
                    _data.writeInterfaceToken(DESCRIPTOR);
                    _data.writeInt(type);
                    _data.writeString(cmd);
                    boolean _status = this.mRemote.transact(6, _data, null, 1);
                    if (!_status) {
                        throw new RemoteException("Method execute is unimplemented.");
                    }
                } finally {
                    _data.recycle();
                }
            }

            @Override // vendor.samsung.hardware.radio.network.ISehRadioNetworkIndication
            public void extendedRegistrationState(int type, SehExtendedRegStateResult state) throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(DESCRIPTOR);
                    _data.writeInt(type);
                    _data.writeTypedObject(state, 0);
                    boolean _status = this.mRemote.transact(7, _data, _reply, 0);
                    if (!_status) {
                        throw new RemoteException("Method extendedRegistrationState is unimplemented.");
                    }
                    _reply.readException();
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // vendor.samsung.hardware.radio.network.ISehRadioNetworkIndication
            public void imsPreferenceChanged(int type, int[] imsPref) throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                try {
                    _data.writeInterfaceToken(DESCRIPTOR);
                    _data.writeInt(type);
                    _data.writeIntArray(imsPref);
                    boolean _status = this.mRemote.transact(8, _data, null, 1);
                    if (!_status) {
                        throw new RemoteException("Method imsPreferenceChanged is unimplemented.");
                    }
                } finally {
                    _data.recycle();
                }
            }

            @Override // vendor.samsung.hardware.radio.network.ISehRadioNetworkIndication
            public void needTurnOnRadioIndication(int type) throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                try {
                    _data.writeInterfaceToken(DESCRIPTOR);
                    _data.writeInt(type);
                    boolean _status = this.mRemote.transact(9, _data, null, 1);
                    if (!_status) {
                        throw new RemoteException("Method needTurnOnRadioIndication is unimplemented.");
                    }
                } finally {
                    _data.recycle();
                }
            }

            @Override // vendor.samsung.hardware.radio.network.ISehRadioNetworkIndication
            public void nrBearerAllocationChanged(int type, int status) throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                try {
                    _data.writeInterfaceToken(DESCRIPTOR);
                    _data.writeInt(type);
                    _data.writeInt(status);
                    boolean _status = this.mRemote.transact(10, _data, null, 1);
                    if (!_status) {
                        throw new RemoteException("Method nrBearerAllocationChanged is unimplemented.");
                    }
                } finally {
                    _data.recycle();
                }
            }

            @Override // vendor.samsung.hardware.radio.network.ISehRadioNetworkIndication
            public void nrIconTypeChanged(int type, int nrIconType) throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                try {
                    _data.writeInterfaceToken(DESCRIPTOR);
                    _data.writeInt(type);
                    _data.writeInt(nrIconType);
                    boolean _status = this.mRemote.transact(11, _data, null, 1);
                    if (!_status) {
                        throw new RemoteException("Method nrIconTypeChanged is unimplemented.");
                    }
                } finally {
                    _data.recycle();
                }
            }

            @Override // vendor.samsung.hardware.radio.network.ISehRadioNetworkIndication
            public void nrNetworkTypeAdded(int type, int status) throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                try {
                    _data.writeInterfaceToken(DESCRIPTOR);
                    _data.writeInt(type);
                    _data.writeInt(status);
                    boolean _status = this.mRemote.transact(12, _data, null, 1);
                    if (!_status) {
                        throw new RemoteException("Method nrNetworkTypeAdded is unimplemented.");
                    }
                } finally {
                    _data.recycle();
                }
            }

            @Override // vendor.samsung.hardware.radio.network.ISehRadioNetworkIndication
            public void roamingNetworkScanIsRequested(int type, byte[] scanData) throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                try {
                    _data.writeInterfaceToken(DESCRIPTOR);
                    _data.writeInt(type);
                    _data.writeByteArray(scanData);
                    boolean _status = this.mRemote.transact(13, _data, null, 1);
                    if (!_status) {
                        throw new RemoteException("Method roamingNetworkScanIsRequested is unimplemented.");
                    }
                } finally {
                    _data.recycle();
                }
            }

            @Override // vendor.samsung.hardware.radio.network.ISehRadioNetworkIndication
            public void signalLevelInfoChanged(int type, SehSignalBar signalBarInfo) throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(DESCRIPTOR);
                    _data.writeInt(type);
                    _data.writeTypedObject(signalBarInfo, 0);
                    boolean _status = this.mRemote.transact(14, _data, _reply, 0);
                    if (!_status) {
                        throw new RemoteException("Method signalLevelInfoChanged is unimplemented.");
                    }
                    _reply.readException();
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // vendor.samsung.hardware.radio.network.ISehRadioNetworkIndication
            public void vendorConfigurationChanged(int type, SehVendorConfiguration[] configurations) throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(DESCRIPTOR);
                    _data.writeInt(type);
                    _data.writeTypedArray(configurations, 0);
                    boolean _status = this.mRemote.transact(15, _data, _reply, 0);
                    if (!_status) {
                        throw new RemoteException("Method vendorConfigurationChanged is unimplemented.");
                    }
                    _reply.readException();
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // vendor.samsung.hardware.radio.network.ISehRadioNetworkIndication
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

            @Override // vendor.samsung.hardware.radio.network.ISehRadioNetworkIndication
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
