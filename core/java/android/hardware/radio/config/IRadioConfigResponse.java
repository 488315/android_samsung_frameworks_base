package android.hardware.radio.config;

import android.hardware.radio.RadioResponseInfo;
import android.os.Binder;
import android.os.IBinder;
import android.os.IInterface;
import android.os.Parcel;
import android.os.RemoteException;

public interface IRadioConfigResponse extends IInterface {
    public static final String DESCRIPTOR =
            "android$hardware$radio$config$IRadioConfigResponse".replace('$', '.');
    public static final String HASH = "1e3dcfffc1e90fc886cf5a22ecaa94601b115710";
    public static final int VERSION = 3;

    void getHalDeviceCapabilitiesResponse(RadioResponseInfo radioResponseInfo, boolean z)
            throws RemoteException;

    String getInterfaceHash() throws RemoteException;

    int getInterfaceVersion() throws RemoteException;

    void getNumOfLiveModemsResponse(RadioResponseInfo radioResponseInfo, byte b)
            throws RemoteException;

    void getPhoneCapabilityResponse(
            RadioResponseInfo radioResponseInfo, PhoneCapability phoneCapability)
            throws RemoteException;

    void getSimSlotsStatusResponse(
            RadioResponseInfo radioResponseInfo, SimSlotStatus[] simSlotStatusArr)
            throws RemoteException;

    void getSimultaneousCallingSupportResponse(RadioResponseInfo radioResponseInfo, int[] iArr)
            throws RemoteException;

    void setNumOfLiveModemsResponse(RadioResponseInfo radioResponseInfo) throws RemoteException;

    void setPreferredDataModemResponse(RadioResponseInfo radioResponseInfo) throws RemoteException;

    void setSimSlotsMappingResponse(RadioResponseInfo radioResponseInfo) throws RemoteException;

    public static class Default implements IRadioConfigResponse {
        @Override // android.hardware.radio.config.IRadioConfigResponse
        public void getHalDeviceCapabilitiesResponse(
                RadioResponseInfo info, boolean modemReducedFeatureSet1) throws RemoteException {}

        @Override // android.hardware.radio.config.IRadioConfigResponse
        public void getNumOfLiveModemsResponse(RadioResponseInfo info, byte numOfLiveModems)
                throws RemoteException {}

        @Override // android.hardware.radio.config.IRadioConfigResponse
        public void getPhoneCapabilityResponse(
                RadioResponseInfo info, PhoneCapability phoneCapability) throws RemoteException {}

        @Override // android.hardware.radio.config.IRadioConfigResponse
        public void getSimSlotsStatusResponse(RadioResponseInfo info, SimSlotStatus[] slotStatus)
                throws RemoteException {}

        @Override // android.hardware.radio.config.IRadioConfigResponse
        public void setNumOfLiveModemsResponse(RadioResponseInfo info) throws RemoteException {}

        @Override // android.hardware.radio.config.IRadioConfigResponse
        public void setPreferredDataModemResponse(RadioResponseInfo info) throws RemoteException {}

        @Override // android.hardware.radio.config.IRadioConfigResponse
        public void setSimSlotsMappingResponse(RadioResponseInfo info) throws RemoteException {}

        @Override // android.hardware.radio.config.IRadioConfigResponse
        public void getSimultaneousCallingSupportResponse(
                RadioResponseInfo info, int[] enabledLogicalSlots) throws RemoteException {}

        @Override // android.hardware.radio.config.IRadioConfigResponse
        public int getInterfaceVersion() {
            return 0;
        }

        @Override // android.hardware.radio.config.IRadioConfigResponse
        public String getInterfaceHash() {
            return "";
        }

        @Override // android.os.IInterface
        public IBinder asBinder() {
            return null;
        }
    }

    public abstract static class Stub extends Binder implements IRadioConfigResponse {
        static final int TRANSACTION_getHalDeviceCapabilitiesResponse = 1;
        static final int TRANSACTION_getInterfaceHash = 16777214;
        static final int TRANSACTION_getInterfaceVersion = 16777215;
        static final int TRANSACTION_getNumOfLiveModemsResponse = 2;
        static final int TRANSACTION_getPhoneCapabilityResponse = 3;
        static final int TRANSACTION_getSimSlotsStatusResponse = 4;
        static final int TRANSACTION_getSimultaneousCallingSupportResponse = 8;
        static final int TRANSACTION_setNumOfLiveModemsResponse = 5;
        static final int TRANSACTION_setPreferredDataModemResponse = 6;
        static final int TRANSACTION_setSimSlotsMappingResponse = 7;

        public Stub() {
            markVintfStability();
            attachInterface(this, DESCRIPTOR);
        }

        public static IRadioConfigResponse asInterface(IBinder obj) {
            if (obj == null) {
                return null;
            }
            IInterface iin = obj.queryLocalInterface(DESCRIPTOR);
            if (iin != null && (iin instanceof IRadioConfigResponse)) {
                return (IRadioConfigResponse) iin;
            }
            return new Proxy(obj);
        }

        @Override // android.os.IInterface
        public IBinder asBinder() {
            return this;
        }

        @Override // android.os.Binder
        public boolean onTransact(int code, Parcel data, Parcel reply, int flags)
                throws RemoteException {
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
                    RadioResponseInfo _arg0 =
                            (RadioResponseInfo) data.readTypedObject(RadioResponseInfo.CREATOR);
                    boolean _arg1 = data.readBoolean();
                    data.enforceNoDataAvail();
                    getHalDeviceCapabilitiesResponse(_arg0, _arg1);
                    return true;
                case 2:
                    RadioResponseInfo _arg02 =
                            (RadioResponseInfo) data.readTypedObject(RadioResponseInfo.CREATOR);
                    byte _arg12 = data.readByte();
                    data.enforceNoDataAvail();
                    getNumOfLiveModemsResponse(_arg02, _arg12);
                    return true;
                case 3:
                    RadioResponseInfo _arg03 =
                            (RadioResponseInfo) data.readTypedObject(RadioResponseInfo.CREATOR);
                    PhoneCapability _arg13 =
                            (PhoneCapability) data.readTypedObject(PhoneCapability.CREATOR);
                    data.enforceNoDataAvail();
                    getPhoneCapabilityResponse(_arg03, _arg13);
                    return true;
                case 4:
                    RadioResponseInfo _arg04 =
                            (RadioResponseInfo) data.readTypedObject(RadioResponseInfo.CREATOR);
                    SimSlotStatus[] _arg14 =
                            (SimSlotStatus[]) data.createTypedArray(SimSlotStatus.CREATOR);
                    data.enforceNoDataAvail();
                    getSimSlotsStatusResponse(_arg04, _arg14);
                    return true;
                case 5:
                    RadioResponseInfo _arg05 =
                            (RadioResponseInfo) data.readTypedObject(RadioResponseInfo.CREATOR);
                    data.enforceNoDataAvail();
                    setNumOfLiveModemsResponse(_arg05);
                    return true;
                case 6:
                    RadioResponseInfo _arg06 =
                            (RadioResponseInfo) data.readTypedObject(RadioResponseInfo.CREATOR);
                    data.enforceNoDataAvail();
                    setPreferredDataModemResponse(_arg06);
                    return true;
                case 7:
                    RadioResponseInfo _arg07 =
                            (RadioResponseInfo) data.readTypedObject(RadioResponseInfo.CREATOR);
                    data.enforceNoDataAvail();
                    setSimSlotsMappingResponse(_arg07);
                    return true;
                case 8:
                    RadioResponseInfo _arg08 =
                            (RadioResponseInfo) data.readTypedObject(RadioResponseInfo.CREATOR);
                    int[] _arg15 = data.createIntArray();
                    data.enforceNoDataAvail();
                    getSimultaneousCallingSupportResponse(_arg08, _arg15);
                    return true;
                default:
                    return super.onTransact(code, data, reply, flags);
            }
        }

        private static class Proxy implements IRadioConfigResponse {
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

            @Override // android.hardware.radio.config.IRadioConfigResponse
            public void getHalDeviceCapabilitiesResponse(
                    RadioResponseInfo info, boolean modemReducedFeatureSet1)
                    throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                try {
                    _data.writeInterfaceToken(DESCRIPTOR);
                    _data.writeTypedObject(info, 0);
                    _data.writeBoolean(modemReducedFeatureSet1);
                    boolean _status = this.mRemote.transact(1, _data, null, 1);
                    if (!_status) {
                        throw new RemoteException(
                                "Method getHalDeviceCapabilitiesResponse is unimplemented.");
                    }
                } finally {
                    _data.recycle();
                }
            }

            @Override // android.hardware.radio.config.IRadioConfigResponse
            public void getNumOfLiveModemsResponse(RadioResponseInfo info, byte numOfLiveModems)
                    throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                try {
                    _data.writeInterfaceToken(DESCRIPTOR);
                    _data.writeTypedObject(info, 0);
                    _data.writeByte(numOfLiveModems);
                    boolean _status = this.mRemote.transact(2, _data, null, 1);
                    if (!_status) {
                        throw new RemoteException(
                                "Method getNumOfLiveModemsResponse is unimplemented.");
                    }
                } finally {
                    _data.recycle();
                }
            }

            @Override // android.hardware.radio.config.IRadioConfigResponse
            public void getPhoneCapabilityResponse(
                    RadioResponseInfo info, PhoneCapability phoneCapability)
                    throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                try {
                    _data.writeInterfaceToken(DESCRIPTOR);
                    _data.writeTypedObject(info, 0);
                    _data.writeTypedObject(phoneCapability, 0);
                    boolean _status = this.mRemote.transact(3, _data, null, 1);
                    if (!_status) {
                        throw new RemoteException(
                                "Method getPhoneCapabilityResponse is unimplemented.");
                    }
                } finally {
                    _data.recycle();
                }
            }

            @Override // android.hardware.radio.config.IRadioConfigResponse
            public void getSimSlotsStatusResponse(
                    RadioResponseInfo info, SimSlotStatus[] slotStatus) throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                try {
                    _data.writeInterfaceToken(DESCRIPTOR);
                    _data.writeTypedObject(info, 0);
                    _data.writeTypedArray(slotStatus, 0);
                    boolean _status = this.mRemote.transact(4, _data, null, 1);
                    if (!_status) {
                        throw new RemoteException(
                                "Method getSimSlotsStatusResponse is unimplemented.");
                    }
                } finally {
                    _data.recycle();
                }
            }

            @Override // android.hardware.radio.config.IRadioConfigResponse
            public void setNumOfLiveModemsResponse(RadioResponseInfo info) throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                try {
                    _data.writeInterfaceToken(DESCRIPTOR);
                    _data.writeTypedObject(info, 0);
                    boolean _status = this.mRemote.transact(5, _data, null, 1);
                    if (!_status) {
                        throw new RemoteException(
                                "Method setNumOfLiveModemsResponse is unimplemented.");
                    }
                } finally {
                    _data.recycle();
                }
            }

            @Override // android.hardware.radio.config.IRadioConfigResponse
            public void setPreferredDataModemResponse(RadioResponseInfo info)
                    throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                try {
                    _data.writeInterfaceToken(DESCRIPTOR);
                    _data.writeTypedObject(info, 0);
                    boolean _status = this.mRemote.transact(6, _data, null, 1);
                    if (!_status) {
                        throw new RemoteException(
                                "Method setPreferredDataModemResponse is unimplemented.");
                    }
                } finally {
                    _data.recycle();
                }
            }

            @Override // android.hardware.radio.config.IRadioConfigResponse
            public void setSimSlotsMappingResponse(RadioResponseInfo info) throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                try {
                    _data.writeInterfaceToken(DESCRIPTOR);
                    _data.writeTypedObject(info, 0);
                    boolean _status = this.mRemote.transact(7, _data, null, 1);
                    if (!_status) {
                        throw new RemoteException(
                                "Method setSimSlotsMappingResponse is unimplemented.");
                    }
                } finally {
                    _data.recycle();
                }
            }

            @Override // android.hardware.radio.config.IRadioConfigResponse
            public void getSimultaneousCallingSupportResponse(
                    RadioResponseInfo info, int[] enabledLogicalSlots) throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                try {
                    _data.writeInterfaceToken(DESCRIPTOR);
                    _data.writeTypedObject(info, 0);
                    _data.writeIntArray(enabledLogicalSlots);
                    boolean _status = this.mRemote.transact(8, _data, null, 1);
                    if (!_status) {
                        throw new RemoteException(
                                "Method getSimultaneousCallingSupportResponse is unimplemented.");
                    }
                } finally {
                    _data.recycle();
                }
            }

            @Override // android.hardware.radio.config.IRadioConfigResponse
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

            @Override // android.hardware.radio.config.IRadioConfigResponse
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
