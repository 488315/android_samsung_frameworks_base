package vendor.samsung.hardware.radio.data;

import android.os.Binder;
import android.os.IBinder;
import android.os.IInterface;
import android.os.Parcel;
import android.os.RemoteException;

import vendor.samsung.hardware.radio.SehRadioResponseInfo;

public interface ISehRadioDataResponse extends IInterface {
    public static final String DESCRIPTOR =
            "vendor$samsung$hardware$radio$data$ISehRadioDataResponse".replace('$', '.');
    public static final String HASH = "1c18f89373d68cf0030dbdb95f4a9287fe232a2e";
    public static final int VERSION = 1;

    String getInterfaceHash() throws RemoteException;

    int getInterfaceVersion() throws RemoteException;

    void setDataAllowedResponse(SehRadioResponseInfo sehRadioResponseInfo) throws RemoteException;

    void setMobileDataSettingResponse(SehRadioResponseInfo sehRadioResponseInfo)
            throws RemoteException;

    public static class Default implements ISehRadioDataResponse {
        @Override // vendor.samsung.hardware.radio.data.ISehRadioDataResponse
        public void setDataAllowedResponse(SehRadioResponseInfo info) throws RemoteException {}

        @Override // vendor.samsung.hardware.radio.data.ISehRadioDataResponse
        public void setMobileDataSettingResponse(SehRadioResponseInfo info)
                throws RemoteException {}

        @Override // vendor.samsung.hardware.radio.data.ISehRadioDataResponse
        public int getInterfaceVersion() {
            return 0;
        }

        @Override // vendor.samsung.hardware.radio.data.ISehRadioDataResponse
        public String getInterfaceHash() {
            return "";
        }

        @Override // android.os.IInterface
        public IBinder asBinder() {
            return null;
        }
    }

    public abstract static class Stub extends Binder implements ISehRadioDataResponse {
        static final int TRANSACTION_getInterfaceHash = 16777214;
        static final int TRANSACTION_getInterfaceVersion = 16777215;
        static final int TRANSACTION_setDataAllowedResponse = 1;
        static final int TRANSACTION_setMobileDataSettingResponse = 2;

        public Stub() {
            markVintfStability();
            attachInterface(this, DESCRIPTOR);
        }

        public static ISehRadioDataResponse asInterface(IBinder obj) {
            if (obj == null) {
                return null;
            }
            IInterface iin = obj.queryLocalInterface(DESCRIPTOR);
            if (iin != null && (iin instanceof ISehRadioDataResponse)) {
                return (ISehRadioDataResponse) iin;
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
                    SehRadioResponseInfo _arg0 =
                            (SehRadioResponseInfo)
                                    data.readTypedObject(SehRadioResponseInfo.CREATOR);
                    data.enforceNoDataAvail();
                    setDataAllowedResponse(_arg0);
                    return true;
                case 2:
                    SehRadioResponseInfo _arg02 =
                            (SehRadioResponseInfo)
                                    data.readTypedObject(SehRadioResponseInfo.CREATOR);
                    data.enforceNoDataAvail();
                    setMobileDataSettingResponse(_arg02);
                    return true;
                default:
                    return super.onTransact(code, data, reply, flags);
            }
        }

        private static class Proxy implements ISehRadioDataResponse {
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

            @Override // vendor.samsung.hardware.radio.data.ISehRadioDataResponse
            public void setDataAllowedResponse(SehRadioResponseInfo info) throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                try {
                    _data.writeInterfaceToken(DESCRIPTOR);
                    _data.writeTypedObject(info, 0);
                    boolean _status = this.mRemote.transact(1, _data, null, 1);
                    if (!_status) {
                        throw new RemoteException(
                                "Method setDataAllowedResponse is unimplemented.");
                    }
                } finally {
                    _data.recycle();
                }
            }

            @Override // vendor.samsung.hardware.radio.data.ISehRadioDataResponse
            public void setMobileDataSettingResponse(SehRadioResponseInfo info)
                    throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                try {
                    _data.writeInterfaceToken(DESCRIPTOR);
                    _data.writeTypedObject(info, 0);
                    boolean _status = this.mRemote.transact(2, _data, null, 1);
                    if (!_status) {
                        throw new RemoteException(
                                "Method setMobileDataSettingResponse is unimplemented.");
                    }
                } finally {
                    _data.recycle();
                }
            }

            @Override // vendor.samsung.hardware.radio.data.ISehRadioDataResponse
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

            @Override // vendor.samsung.hardware.radio.data.ISehRadioDataResponse
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
