package vendor.samsung.hardware.radio.network;

import android.p009os.Binder;
import android.p009os.IBinder;
import android.p009os.IInterface;
import android.p009os.Parcel;
import android.p009os.RemoteException;
import vendor.samsung.hardware.radio.network.ISehRadioNetworkIndication;
import vendor.samsung.hardware.radio.network.ISehRadioNetworkResponse;

/* loaded from: classes6.dex */
public interface ISehRadioNetwork extends IInterface {
    public static final String DESCRIPTOR = "vendor$samsung$hardware$radio$network$ISehRadioNetwork".replace('$', '.');
    public static final String HASH = "8fb94cb4cf759f224987eb2d1908582a1f6f1600";
    public static final int VERSION = 1;

    void emergencyControl(int i, int i2) throws RemoteException;

    void emergencySearch(int i) throws RemoteException;

    void getAvailableNetworks(int i) throws RemoteException;

    void getCnap(int i) throws RemoteException;

    void getCsgList(int i) throws RemoteException;

    void getDisable2g(int i) throws RemoteException;

    String getInterfaceHash() throws RemoteException;

    int getInterfaceVersion() throws RemoteException;

    void getNrIconType(int i) throws RemoteException;

    void getNrMode(int i) throws RemoteException;

    void getPreferredNetworkList(int i) throws RemoteException;

    void getRoamingNetworkInfoViaBLE(int i, byte b, String str) throws RemoteException;

    void getVendorSpecificConfiguration(int i) throws RemoteException;

    void selectCsgManual(int i, SehCsgInfo sehCsgInfo) throws RemoteException;

    void sendEncodedUssd(int i, SehEncodedUssd sehEncodedUssd) throws RemoteException;

    void sendRequestRaw(int i, byte[] bArr) throws RemoteException;

    void sendRequestStrings(int i, String[] strArr) throws RemoteException;

    void setCurrentNetworkInfoViaBLE(int i, SehCurrentNetworkInfo[] sehCurrentNetworkInfoArr) throws RemoteException;

    void setDisable2g(int i, int i2) throws RemoteException;

    void setImsCallList(int i, SehImsCall[] sehImsCallArr) throws RemoteException;

    void setNrMode(int i, int i2, boolean z) throws RemoteException;

    void setPreferredNetworkList(int i, SehPreferredNetworkInfo sehPreferredNetworkInfo) throws RemoteException;

    void setResponseFunctions(ISehRadioNetworkResponse iSehRadioNetworkResponse, ISehRadioNetworkIndication iSehRadioNetworkIndication) throws RemoteException;

    void setRoamingNetworkInfoViaBLE(int i, SehRoamingNetworkInfo[] sehRoamingNetworkInfoArr) throws RemoteException;

    void setScanResultViaBLE(int i, byte b, byte b2) throws RemoteException;

    void setVendorSpecificConfiguration(int i, SehVendorConfiguration[] sehVendorConfigurationArr) throws RemoteException;

    public static class Default implements ISehRadioNetwork {
        @Override // vendor.samsung.hardware.radio.network.ISehRadioNetwork
        public void emergencyControl(int serial, int command) throws RemoteException {
        }

        @Override // vendor.samsung.hardware.radio.network.ISehRadioNetwork
        public void emergencySearch(int serial) throws RemoteException {
        }

        @Override // vendor.samsung.hardware.radio.network.ISehRadioNetwork
        public void getAvailableNetworks(int serial) throws RemoteException {
        }

        @Override // vendor.samsung.hardware.radio.network.ISehRadioNetwork
        public void getCnap(int serial) throws RemoteException {
        }

        @Override // vendor.samsung.hardware.radio.network.ISehRadioNetwork
        public void getCsgList(int serial) throws RemoteException {
        }

        @Override // vendor.samsung.hardware.radio.network.ISehRadioNetwork
        public void getDisable2g(int serial) throws RemoteException {
        }

        @Override // vendor.samsung.hardware.radio.network.ISehRadioNetwork
        public void getNrIconType(int serial) throws RemoteException {
        }

        @Override // vendor.samsung.hardware.radio.network.ISehRadioNetwork
        public void getNrMode(int serial) throws RemoteException {
        }

        @Override // vendor.samsung.hardware.radio.network.ISehRadioNetwork
        public void getPreferredNetworkList(int serial) throws RemoteException {
        }

        @Override // vendor.samsung.hardware.radio.network.ISehRadioNetwork
        public void getRoamingNetworkInfoViaBLE(int serial, byte mode, String homePlmn) throws RemoteException {
        }

        @Override // vendor.samsung.hardware.radio.network.ISehRadioNetwork
        public void getVendorSpecificConfiguration(int serial) throws RemoteException {
        }

        @Override // vendor.samsung.hardware.radio.network.ISehRadioNetwork
        public void selectCsgManual(int serial, SehCsgInfo csgInfo) throws RemoteException {
        }

        @Override // vendor.samsung.hardware.radio.network.ISehRadioNetwork
        public void sendEncodedUssd(int serial, SehEncodedUssd encodedUssd) throws RemoteException {
        }

        @Override // vendor.samsung.hardware.radio.network.ISehRadioNetwork
        public void sendRequestRaw(int serial, byte[] data) throws RemoteException {
        }

        @Override // vendor.samsung.hardware.radio.network.ISehRadioNetwork
        public void sendRequestStrings(int serial, String[] data) throws RemoteException {
        }

        @Override // vendor.samsung.hardware.radio.network.ISehRadioNetwork
        public void setCurrentNetworkInfoViaBLE(int serial, SehCurrentNetworkInfo[] networkInfo) throws RemoteException {
        }

        @Override // vendor.samsung.hardware.radio.network.ISehRadioNetwork
        public void setDisable2g(int serial, int on) throws RemoteException {
        }

        @Override // vendor.samsung.hardware.radio.network.ISehRadioNetwork
        public void setImsCallList(int serial, SehImsCall[] imsCalls) throws RemoteException {
        }

        @Override // vendor.samsung.hardware.radio.network.ISehRadioNetwork
        public void setNrMode(int serial, int mode, boolean force) throws RemoteException {
        }

        @Override // vendor.samsung.hardware.radio.network.ISehRadioNetwork
        public void setPreferredNetworkList(int serial, SehPreferredNetworkInfo info) throws RemoteException {
        }

        @Override // vendor.samsung.hardware.radio.network.ISehRadioNetwork
        public void setResponseFunctions(ISehRadioNetworkResponse radioResponse, ISehRadioNetworkIndication radioIndication) throws RemoteException {
        }

        @Override // vendor.samsung.hardware.radio.network.ISehRadioNetwork
        public void setRoamingNetworkInfoViaBLE(int serial, SehRoamingNetworkInfo[] networkInfo) throws RemoteException {
        }

        @Override // vendor.samsung.hardware.radio.network.ISehRadioNetwork
        public void setScanResultViaBLE(int serial, byte result, byte scanType) throws RemoteException {
        }

        @Override // vendor.samsung.hardware.radio.network.ISehRadioNetwork
        public void setVendorSpecificConfiguration(int serial, SehVendorConfiguration[] configurations) throws RemoteException {
        }

        @Override // vendor.samsung.hardware.radio.network.ISehRadioNetwork
        public int getInterfaceVersion() {
            return 0;
        }

        @Override // vendor.samsung.hardware.radio.network.ISehRadioNetwork
        public String getInterfaceHash() {
            return "";
        }

        @Override // android.p009os.IInterface
        public IBinder asBinder() {
            return null;
        }
    }

    public static abstract class Stub extends Binder implements ISehRadioNetwork {
        static final int TRANSACTION_emergencyControl = 1;
        static final int TRANSACTION_emergencySearch = 2;
        static final int TRANSACTION_getAvailableNetworks = 3;
        static final int TRANSACTION_getCnap = 4;
        static final int TRANSACTION_getCsgList = 5;
        static final int TRANSACTION_getDisable2g = 6;
        static final int TRANSACTION_getInterfaceHash = 16777214;
        static final int TRANSACTION_getInterfaceVersion = 16777215;
        static final int TRANSACTION_getNrIconType = 7;
        static final int TRANSACTION_getNrMode = 8;
        static final int TRANSACTION_getPreferredNetworkList = 9;
        static final int TRANSACTION_getRoamingNetworkInfoViaBLE = 10;
        static final int TRANSACTION_getVendorSpecificConfiguration = 11;
        static final int TRANSACTION_selectCsgManual = 12;
        static final int TRANSACTION_sendEncodedUssd = 13;
        static final int TRANSACTION_sendRequestRaw = 14;
        static final int TRANSACTION_sendRequestStrings = 15;
        static final int TRANSACTION_setCurrentNetworkInfoViaBLE = 16;
        static final int TRANSACTION_setDisable2g = 17;
        static final int TRANSACTION_setImsCallList = 18;
        static final int TRANSACTION_setNrMode = 19;
        static final int TRANSACTION_setPreferredNetworkList = 20;
        static final int TRANSACTION_setResponseFunctions = 21;
        static final int TRANSACTION_setRoamingNetworkInfoViaBLE = 22;
        static final int TRANSACTION_setScanResultViaBLE = 23;
        static final int TRANSACTION_setVendorSpecificConfiguration = 24;

        public Stub() {
            markVintfStability();
            attachInterface(this, DESCRIPTOR);
        }

        public static ISehRadioNetwork asInterface(IBinder obj) {
            if (obj == null) {
                return null;
            }
            IInterface iin = obj.queryLocalInterface(DESCRIPTOR);
            if (iin != null && (iin instanceof ISehRadioNetwork)) {
                return (ISehRadioNetwork) iin;
            }
            return new Proxy(obj);
        }

        @Override // android.p009os.IInterface
        public IBinder asBinder() {
            return this;
        }

        @Override // android.p009os.Binder
        public boolean onTransact(int code, Parcel data, Parcel reply, int flags) throws RemoteException {
            String descriptor = DESCRIPTOR;
            if (code >= 1 && code <= 16777215) {
                data.enforceInterface(descriptor);
            }
            switch (code) {
                case 16777214:
                    reply.writeNoException();
                    reply.writeString(getInterfaceHash());
                    return true;
                case 16777215:
                    reply.writeNoException();
                    reply.writeInt(getInterfaceVersion());
                    return true;
                case IBinder.INTERFACE_TRANSACTION /* 1598968902 */:
                    reply.writeString(descriptor);
                    return true;
                default:
                    switch (code) {
                        case 1:
                            int _arg0 = data.readInt();
                            int _arg1 = data.readInt();
                            data.enforceNoDataAvail();
                            emergencyControl(_arg0, _arg1);
                            return true;
                        case 2:
                            int _arg02 = data.readInt();
                            data.enforceNoDataAvail();
                            emergencySearch(_arg02);
                            return true;
                        case 3:
                            int _arg03 = data.readInt();
                            data.enforceNoDataAvail();
                            getAvailableNetworks(_arg03);
                            return true;
                        case 4:
                            int _arg04 = data.readInt();
                            data.enforceNoDataAvail();
                            getCnap(_arg04);
                            return true;
                        case 5:
                            int _arg05 = data.readInt();
                            data.enforceNoDataAvail();
                            getCsgList(_arg05);
                            return true;
                        case 6:
                            int _arg06 = data.readInt();
                            data.enforceNoDataAvail();
                            getDisable2g(_arg06);
                            return true;
                        case 7:
                            int _arg07 = data.readInt();
                            data.enforceNoDataAvail();
                            getNrIconType(_arg07);
                            return true;
                        case 8:
                            int _arg08 = data.readInt();
                            data.enforceNoDataAvail();
                            getNrMode(_arg08);
                            return true;
                        case 9:
                            int _arg09 = data.readInt();
                            data.enforceNoDataAvail();
                            getPreferredNetworkList(_arg09);
                            return true;
                        case 10:
                            int _arg010 = data.readInt();
                            byte _arg12 = data.readByte();
                            String _arg2 = data.readString();
                            data.enforceNoDataAvail();
                            getRoamingNetworkInfoViaBLE(_arg010, _arg12, _arg2);
                            return true;
                        case 11:
                            int _arg011 = data.readInt();
                            data.enforceNoDataAvail();
                            getVendorSpecificConfiguration(_arg011);
                            return true;
                        case 12:
                            int _arg012 = data.readInt();
                            SehCsgInfo _arg13 = (SehCsgInfo) data.readTypedObject(SehCsgInfo.CREATOR);
                            data.enforceNoDataAvail();
                            selectCsgManual(_arg012, _arg13);
                            return true;
                        case 13:
                            int _arg013 = data.readInt();
                            SehEncodedUssd _arg14 = (SehEncodedUssd) data.readTypedObject(SehEncodedUssd.CREATOR);
                            data.enforceNoDataAvail();
                            sendEncodedUssd(_arg013, _arg14);
                            return true;
                        case 14:
                            int _arg014 = data.readInt();
                            byte[] _arg15 = data.createByteArray();
                            data.enforceNoDataAvail();
                            sendRequestRaw(_arg014, _arg15);
                            return true;
                        case 15:
                            int _arg015 = data.readInt();
                            String[] _arg16 = data.createStringArray();
                            data.enforceNoDataAvail();
                            sendRequestStrings(_arg015, _arg16);
                            return true;
                        case 16:
                            int _arg016 = data.readInt();
                            SehCurrentNetworkInfo[] _arg17 = (SehCurrentNetworkInfo[]) data.createTypedArray(SehCurrentNetworkInfo.CREATOR);
                            data.enforceNoDataAvail();
                            setCurrentNetworkInfoViaBLE(_arg016, _arg17);
                            return true;
                        case 17:
                            int _arg017 = data.readInt();
                            int _arg18 = data.readInt();
                            data.enforceNoDataAvail();
                            setDisable2g(_arg017, _arg18);
                            return true;
                        case 18:
                            int _arg018 = data.readInt();
                            SehImsCall[] _arg19 = (SehImsCall[]) data.createTypedArray(SehImsCall.CREATOR);
                            data.enforceNoDataAvail();
                            setImsCallList(_arg018, _arg19);
                            return true;
                        case 19:
                            int _arg019 = data.readInt();
                            int _arg110 = data.readInt();
                            boolean _arg22 = data.readBoolean();
                            data.enforceNoDataAvail();
                            setNrMode(_arg019, _arg110, _arg22);
                            return true;
                        case 20:
                            int _arg020 = data.readInt();
                            SehPreferredNetworkInfo _arg111 = (SehPreferredNetworkInfo) data.readTypedObject(SehPreferredNetworkInfo.CREATOR);
                            data.enforceNoDataAvail();
                            setPreferredNetworkList(_arg020, _arg111);
                            return true;
                        case 21:
                            ISehRadioNetworkResponse _arg021 = ISehRadioNetworkResponse.Stub.asInterface(data.readStrongBinder());
                            ISehRadioNetworkIndication _arg112 = ISehRadioNetworkIndication.Stub.asInterface(data.readStrongBinder());
                            data.enforceNoDataAvail();
                            setResponseFunctions(_arg021, _arg112);
                            reply.writeNoException();
                            return true;
                        case 22:
                            int _arg022 = data.readInt();
                            SehRoamingNetworkInfo[] _arg113 = (SehRoamingNetworkInfo[]) data.createTypedArray(SehRoamingNetworkInfo.CREATOR);
                            data.enforceNoDataAvail();
                            setRoamingNetworkInfoViaBLE(_arg022, _arg113);
                            return true;
                        case 23:
                            int _arg023 = data.readInt();
                            byte _arg114 = data.readByte();
                            byte _arg23 = data.readByte();
                            data.enforceNoDataAvail();
                            setScanResultViaBLE(_arg023, _arg114, _arg23);
                            return true;
                        case 24:
                            int _arg024 = data.readInt();
                            SehVendorConfiguration[] _arg115 = (SehVendorConfiguration[]) data.createTypedArray(SehVendorConfiguration.CREATOR);
                            data.enforceNoDataAvail();
                            setVendorSpecificConfiguration(_arg024, _arg115);
                            return true;
                        default:
                            return super.onTransact(code, data, reply, flags);
                    }
            }
        }

        private static class Proxy implements ISehRadioNetwork {
            private IBinder mRemote;
            private int mCachedVersion = -1;
            private String mCachedHash = "-1";

            Proxy(IBinder remote) {
                this.mRemote = remote;
            }

            @Override // android.p009os.IInterface
            public IBinder asBinder() {
                return this.mRemote;
            }

            public String getInterfaceDescriptor() {
                return DESCRIPTOR;
            }

            @Override // vendor.samsung.hardware.radio.network.ISehRadioNetwork
            public void emergencyControl(int serial, int command) throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                try {
                    _data.writeInterfaceToken(DESCRIPTOR);
                    _data.writeInt(serial);
                    _data.writeInt(command);
                    boolean _status = this.mRemote.transact(1, _data, null, 1);
                    if (!_status) {
                        throw new RemoteException("Method emergencyControl is unimplemented.");
                    }
                } finally {
                    _data.recycle();
                }
            }

            @Override // vendor.samsung.hardware.radio.network.ISehRadioNetwork
            public void emergencySearch(int serial) throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                try {
                    _data.writeInterfaceToken(DESCRIPTOR);
                    _data.writeInt(serial);
                    boolean _status = this.mRemote.transact(2, _data, null, 1);
                    if (!_status) {
                        throw new RemoteException("Method emergencySearch is unimplemented.");
                    }
                } finally {
                    _data.recycle();
                }
            }

            @Override // vendor.samsung.hardware.radio.network.ISehRadioNetwork
            public void getAvailableNetworks(int serial) throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                try {
                    _data.writeInterfaceToken(DESCRIPTOR);
                    _data.writeInt(serial);
                    boolean _status = this.mRemote.transact(3, _data, null, 1);
                    if (!_status) {
                        throw new RemoteException("Method getAvailableNetworks is unimplemented.");
                    }
                } finally {
                    _data.recycle();
                }
            }

            @Override // vendor.samsung.hardware.radio.network.ISehRadioNetwork
            public void getCnap(int serial) throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                try {
                    _data.writeInterfaceToken(DESCRIPTOR);
                    _data.writeInt(serial);
                    boolean _status = this.mRemote.transact(4, _data, null, 1);
                    if (!_status) {
                        throw new RemoteException("Method getCnap is unimplemented.");
                    }
                } finally {
                    _data.recycle();
                }
            }

            @Override // vendor.samsung.hardware.radio.network.ISehRadioNetwork
            public void getCsgList(int serial) throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                try {
                    _data.writeInterfaceToken(DESCRIPTOR);
                    _data.writeInt(serial);
                    boolean _status = this.mRemote.transact(5, _data, null, 1);
                    if (!_status) {
                        throw new RemoteException("Method getCsgList is unimplemented.");
                    }
                } finally {
                    _data.recycle();
                }
            }

            @Override // vendor.samsung.hardware.radio.network.ISehRadioNetwork
            public void getDisable2g(int serial) throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                try {
                    _data.writeInterfaceToken(DESCRIPTOR);
                    _data.writeInt(serial);
                    boolean _status = this.mRemote.transact(6, _data, null, 1);
                    if (!_status) {
                        throw new RemoteException("Method getDisable2g is unimplemented.");
                    }
                } finally {
                    _data.recycle();
                }
            }

            @Override // vendor.samsung.hardware.radio.network.ISehRadioNetwork
            public void getNrIconType(int serial) throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                try {
                    _data.writeInterfaceToken(DESCRIPTOR);
                    _data.writeInt(serial);
                    boolean _status = this.mRemote.transact(7, _data, null, 1);
                    if (!_status) {
                        throw new RemoteException("Method getNrIconType is unimplemented.");
                    }
                } finally {
                    _data.recycle();
                }
            }

            @Override // vendor.samsung.hardware.radio.network.ISehRadioNetwork
            public void getNrMode(int serial) throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                try {
                    _data.writeInterfaceToken(DESCRIPTOR);
                    _data.writeInt(serial);
                    boolean _status = this.mRemote.transact(8, _data, null, 1);
                    if (!_status) {
                        throw new RemoteException("Method getNrMode is unimplemented.");
                    }
                } finally {
                    _data.recycle();
                }
            }

            @Override // vendor.samsung.hardware.radio.network.ISehRadioNetwork
            public void getPreferredNetworkList(int serial) throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                try {
                    _data.writeInterfaceToken(DESCRIPTOR);
                    _data.writeInt(serial);
                    boolean _status = this.mRemote.transact(9, _data, null, 1);
                    if (!_status) {
                        throw new RemoteException("Method getPreferredNetworkList is unimplemented.");
                    }
                } finally {
                    _data.recycle();
                }
            }

            @Override // vendor.samsung.hardware.radio.network.ISehRadioNetwork
            public void getRoamingNetworkInfoViaBLE(int serial, byte mode, String homePlmn) throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                try {
                    _data.writeInterfaceToken(DESCRIPTOR);
                    _data.writeInt(serial);
                    _data.writeByte(mode);
                    _data.writeString(homePlmn);
                    boolean _status = this.mRemote.transact(10, _data, null, 1);
                    if (!_status) {
                        throw new RemoteException("Method getRoamingNetworkInfoViaBLE is unimplemented.");
                    }
                } finally {
                    _data.recycle();
                }
            }

            @Override // vendor.samsung.hardware.radio.network.ISehRadioNetwork
            public void getVendorSpecificConfiguration(int serial) throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                try {
                    _data.writeInterfaceToken(DESCRIPTOR);
                    _data.writeInt(serial);
                    boolean _status = this.mRemote.transact(11, _data, null, 1);
                    if (!_status) {
                        throw new RemoteException("Method getVendorSpecificConfiguration is unimplemented.");
                    }
                } finally {
                    _data.recycle();
                }
            }

            @Override // vendor.samsung.hardware.radio.network.ISehRadioNetwork
            public void selectCsgManual(int serial, SehCsgInfo csgInfo) throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                try {
                    _data.writeInterfaceToken(DESCRIPTOR);
                    _data.writeInt(serial);
                    _data.writeTypedObject(csgInfo, 0);
                    boolean _status = this.mRemote.transact(12, _data, null, 1);
                    if (!_status) {
                        throw new RemoteException("Method selectCsgManual is unimplemented.");
                    }
                } finally {
                    _data.recycle();
                }
            }

            @Override // vendor.samsung.hardware.radio.network.ISehRadioNetwork
            public void sendEncodedUssd(int serial, SehEncodedUssd encodedUssd) throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                try {
                    _data.writeInterfaceToken(DESCRIPTOR);
                    _data.writeInt(serial);
                    _data.writeTypedObject(encodedUssd, 0);
                    boolean _status = this.mRemote.transact(13, _data, null, 1);
                    if (!_status) {
                        throw new RemoteException("Method sendEncodedUssd is unimplemented.");
                    }
                } finally {
                    _data.recycle();
                }
            }

            @Override // vendor.samsung.hardware.radio.network.ISehRadioNetwork
            public void sendRequestRaw(int serial, byte[] data) throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                try {
                    _data.writeInterfaceToken(DESCRIPTOR);
                    _data.writeInt(serial);
                    _data.writeByteArray(data);
                    boolean _status = this.mRemote.transact(14, _data, null, 1);
                    if (!_status) {
                        throw new RemoteException("Method sendRequestRaw is unimplemented.");
                    }
                } finally {
                    _data.recycle();
                }
            }

            @Override // vendor.samsung.hardware.radio.network.ISehRadioNetwork
            public void sendRequestStrings(int serial, String[] data) throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                try {
                    _data.writeInterfaceToken(DESCRIPTOR);
                    _data.writeInt(serial);
                    _data.writeStringArray(data);
                    boolean _status = this.mRemote.transact(15, _data, null, 1);
                    if (!_status) {
                        throw new RemoteException("Method sendRequestStrings is unimplemented.");
                    }
                } finally {
                    _data.recycle();
                }
            }

            @Override // vendor.samsung.hardware.radio.network.ISehRadioNetwork
            public void setCurrentNetworkInfoViaBLE(int serial, SehCurrentNetworkInfo[] networkInfo) throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                try {
                    _data.writeInterfaceToken(DESCRIPTOR);
                    _data.writeInt(serial);
                    _data.writeTypedArray(networkInfo, 0);
                    boolean _status = this.mRemote.transact(16, _data, null, 1);
                    if (!_status) {
                        throw new RemoteException("Method setCurrentNetworkInfoViaBLE is unimplemented.");
                    }
                } finally {
                    _data.recycle();
                }
            }

            @Override // vendor.samsung.hardware.radio.network.ISehRadioNetwork
            public void setDisable2g(int serial, int on) throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                try {
                    _data.writeInterfaceToken(DESCRIPTOR);
                    _data.writeInt(serial);
                    _data.writeInt(on);
                    boolean _status = this.mRemote.transact(17, _data, null, 1);
                    if (!_status) {
                        throw new RemoteException("Method setDisable2g is unimplemented.");
                    }
                } finally {
                    _data.recycle();
                }
            }

            @Override // vendor.samsung.hardware.radio.network.ISehRadioNetwork
            public void setImsCallList(int serial, SehImsCall[] imsCalls) throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                try {
                    _data.writeInterfaceToken(DESCRIPTOR);
                    _data.writeInt(serial);
                    _data.writeTypedArray(imsCalls, 0);
                    boolean _status = this.mRemote.transact(18, _data, null, 1);
                    if (!_status) {
                        throw new RemoteException("Method setImsCallList is unimplemented.");
                    }
                } finally {
                    _data.recycle();
                }
            }

            @Override // vendor.samsung.hardware.radio.network.ISehRadioNetwork
            public void setNrMode(int serial, int mode, boolean force) throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                try {
                    _data.writeInterfaceToken(DESCRIPTOR);
                    _data.writeInt(serial);
                    _data.writeInt(mode);
                    _data.writeBoolean(force);
                    boolean _status = this.mRemote.transact(19, _data, null, 1);
                    if (!_status) {
                        throw new RemoteException("Method setNrMode is unimplemented.");
                    }
                } finally {
                    _data.recycle();
                }
            }

            @Override // vendor.samsung.hardware.radio.network.ISehRadioNetwork
            public void setPreferredNetworkList(int serial, SehPreferredNetworkInfo info) throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                try {
                    _data.writeInterfaceToken(DESCRIPTOR);
                    _data.writeInt(serial);
                    _data.writeTypedObject(info, 0);
                    boolean _status = this.mRemote.transact(20, _data, null, 1);
                    if (!_status) {
                        throw new RemoteException("Method setPreferredNetworkList is unimplemented.");
                    }
                } finally {
                    _data.recycle();
                }
            }

            @Override // vendor.samsung.hardware.radio.network.ISehRadioNetwork
            public void setResponseFunctions(ISehRadioNetworkResponse radioResponse, ISehRadioNetworkIndication radioIndication) throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(DESCRIPTOR);
                    _data.writeStrongInterface(radioResponse);
                    _data.writeStrongInterface(radioIndication);
                    boolean _status = this.mRemote.transact(21, _data, _reply, 0);
                    if (!_status) {
                        throw new RemoteException("Method setResponseFunctions is unimplemented.");
                    }
                    _reply.readException();
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // vendor.samsung.hardware.radio.network.ISehRadioNetwork
            public void setRoamingNetworkInfoViaBLE(int serial, SehRoamingNetworkInfo[] networkInfo) throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                try {
                    _data.writeInterfaceToken(DESCRIPTOR);
                    _data.writeInt(serial);
                    _data.writeTypedArray(networkInfo, 0);
                    boolean _status = this.mRemote.transact(22, _data, null, 1);
                    if (!_status) {
                        throw new RemoteException("Method setRoamingNetworkInfoViaBLE is unimplemented.");
                    }
                } finally {
                    _data.recycle();
                }
            }

            @Override // vendor.samsung.hardware.radio.network.ISehRadioNetwork
            public void setScanResultViaBLE(int serial, byte result, byte scanType) throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                try {
                    _data.writeInterfaceToken(DESCRIPTOR);
                    _data.writeInt(serial);
                    _data.writeByte(result);
                    _data.writeByte(scanType);
                    boolean _status = this.mRemote.transact(23, _data, null, 1);
                    if (!_status) {
                        throw new RemoteException("Method setScanResultViaBLE is unimplemented.");
                    }
                } finally {
                    _data.recycle();
                }
            }

            @Override // vendor.samsung.hardware.radio.network.ISehRadioNetwork
            public void setVendorSpecificConfiguration(int serial, SehVendorConfiguration[] configurations) throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                try {
                    _data.writeInterfaceToken(DESCRIPTOR);
                    _data.writeInt(serial);
                    _data.writeTypedArray(configurations, 0);
                    boolean _status = this.mRemote.transact(24, _data, null, 1);
                    if (!_status) {
                        throw new RemoteException("Method setVendorSpecificConfiguration is unimplemented.");
                    }
                } finally {
                    _data.recycle();
                }
            }

            @Override // vendor.samsung.hardware.radio.network.ISehRadioNetwork
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

            @Override // vendor.samsung.hardware.radio.network.ISehRadioNetwork
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
