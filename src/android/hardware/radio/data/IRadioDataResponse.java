package android.hardware.radio.data;

import android.hardware.radio.RadioResponseInfo;
import android.os.Binder;
import android.os.IBinder;
import android.os.IInterface;
import android.os.Parcel;
import android.os.RemoteException;

/* loaded from: classes2.dex */
public interface IRadioDataResponse extends IInterface {
    public static final String DESCRIPTOR = "android$hardware$radio$data$IRadioDataResponse".replace('$', '.');
    public static final String HASH = "70713939dbe39fdbd3a294b3a3e3d2842b3bf4eb";
    public static final int VERSION = 4;

    void acknowledgeRequest(int i) throws RemoteException;

    void allocatePduSessionIdResponse(RadioResponseInfo radioResponseInfo, int i) throws RemoteException;

    void cancelHandoverResponse(RadioResponseInfo radioResponseInfo) throws RemoteException;

    void deactivateDataCallResponse(RadioResponseInfo radioResponseInfo) throws RemoteException;

    void getDataCallListResponse(RadioResponseInfo radioResponseInfo, SetupDataCallResult[] setupDataCallResultArr) throws RemoteException;

    String getInterfaceHash() throws RemoteException;

    int getInterfaceVersion() throws RemoteException;

    void getSlicingConfigResponse(RadioResponseInfo radioResponseInfo, SlicingConfig slicingConfig) throws RemoteException;

    void releasePduSessionIdResponse(RadioResponseInfo radioResponseInfo) throws RemoteException;

    void setDataAllowedResponse(RadioResponseInfo radioResponseInfo) throws RemoteException;

    void setDataProfileResponse(RadioResponseInfo radioResponseInfo) throws RemoteException;

    void setDataThrottlingResponse(RadioResponseInfo radioResponseInfo) throws RemoteException;

    void setInitialAttachApnResponse(RadioResponseInfo radioResponseInfo) throws RemoteException;

    void setupDataCallResponse(RadioResponseInfo radioResponseInfo, SetupDataCallResult setupDataCallResult) throws RemoteException;

    void startHandoverResponse(RadioResponseInfo radioResponseInfo) throws RemoteException;

    void startKeepaliveResponse(RadioResponseInfo radioResponseInfo, KeepaliveStatus keepaliveStatus) throws RemoteException;

    void stopKeepaliveResponse(RadioResponseInfo radioResponseInfo) throws RemoteException;

    public static class Default implements IRadioDataResponse {
        @Override // android.hardware.radio.data.IRadioDataResponse
        public void acknowledgeRequest(int i) throws RemoteException {
        }

        @Override // android.hardware.radio.data.IRadioDataResponse
        public void allocatePduSessionIdResponse(RadioResponseInfo radioResponseInfo, int i) throws RemoteException {
        }

        @Override // android.os.IInterface
        public IBinder asBinder() {
            return null;
        }

        @Override // android.hardware.radio.data.IRadioDataResponse
        public void cancelHandoverResponse(RadioResponseInfo radioResponseInfo) throws RemoteException {
        }

        @Override // android.hardware.radio.data.IRadioDataResponse
        public void deactivateDataCallResponse(RadioResponseInfo radioResponseInfo) throws RemoteException {
        }

        @Override // android.hardware.radio.data.IRadioDataResponse
        public void getDataCallListResponse(RadioResponseInfo radioResponseInfo, SetupDataCallResult[] setupDataCallResultArr) throws RemoteException {
        }

        @Override // android.hardware.radio.data.IRadioDataResponse
        public int getInterfaceVersion() {
            return 0;
        }

        @Override // android.hardware.radio.data.IRadioDataResponse
        public void getSlicingConfigResponse(RadioResponseInfo radioResponseInfo, SlicingConfig slicingConfig) throws RemoteException {
        }

        @Override // android.hardware.radio.data.IRadioDataResponse
        public void releasePduSessionIdResponse(RadioResponseInfo radioResponseInfo) throws RemoteException {
        }

        @Override // android.hardware.radio.data.IRadioDataResponse
        public void setDataAllowedResponse(RadioResponseInfo radioResponseInfo) throws RemoteException {
        }

        @Override // android.hardware.radio.data.IRadioDataResponse
        public void setDataProfileResponse(RadioResponseInfo radioResponseInfo) throws RemoteException {
        }

        @Override // android.hardware.radio.data.IRadioDataResponse
        public void setDataThrottlingResponse(RadioResponseInfo radioResponseInfo) throws RemoteException {
        }

        @Override // android.hardware.radio.data.IRadioDataResponse
        public void setInitialAttachApnResponse(RadioResponseInfo radioResponseInfo) throws RemoteException {
        }

        @Override // android.hardware.radio.data.IRadioDataResponse
        public void setupDataCallResponse(RadioResponseInfo radioResponseInfo, SetupDataCallResult setupDataCallResult) throws RemoteException {
        }

        @Override // android.hardware.radio.data.IRadioDataResponse
        public void startHandoverResponse(RadioResponseInfo radioResponseInfo) throws RemoteException {
        }

        @Override // android.hardware.radio.data.IRadioDataResponse
        public void startKeepaliveResponse(RadioResponseInfo radioResponseInfo, KeepaliveStatus keepaliveStatus) throws RemoteException {
        }

        @Override // android.hardware.radio.data.IRadioDataResponse
        public void stopKeepaliveResponse(RadioResponseInfo radioResponseInfo) throws RemoteException {
        }

        @Override // android.hardware.radio.data.IRadioDataResponse
        public String getInterfaceHash() {
            return "";
        }
    }

    public static abstract class Stub extends Binder implements IRadioDataResponse {
        static final int TRANSACTION_acknowledgeRequest = 1;
        static final int TRANSACTION_allocatePduSessionIdResponse = 2;
        static final int TRANSACTION_cancelHandoverResponse = 3;
        static final int TRANSACTION_deactivateDataCallResponse = 4;
        static final int TRANSACTION_getDataCallListResponse = 5;
        static final int TRANSACTION_getInterfaceHash = 16777214;
        static final int TRANSACTION_getInterfaceVersion = 16777215;
        static final int TRANSACTION_getSlicingConfigResponse = 6;
        static final int TRANSACTION_releasePduSessionIdResponse = 7;
        static final int TRANSACTION_setDataAllowedResponse = 8;
        static final int TRANSACTION_setDataProfileResponse = 9;
        static final int TRANSACTION_setDataThrottlingResponse = 10;
        static final int TRANSACTION_setInitialAttachApnResponse = 11;
        static final int TRANSACTION_setupDataCallResponse = 12;
        static final int TRANSACTION_startHandoverResponse = 13;
        static final int TRANSACTION_startKeepaliveResponse = 14;
        static final int TRANSACTION_stopKeepaliveResponse = 15;

        @Override // android.os.IInterface
        public IBinder asBinder() {
            return this;
        }

        public Stub() {
            markVintfStability();
            attachInterface(this, DESCRIPTOR);
        }

        public static IRadioDataResponse asInterface(IBinder iBinder) {
            if (iBinder == null) {
                return null;
            }
            IInterface queryLocalInterface = iBinder.queryLocalInterface(DESCRIPTOR);
            if (queryLocalInterface != null && (queryLocalInterface instanceof IRadioDataResponse)) {
                return (IRadioDataResponse) queryLocalInterface;
            }
            return new Proxy(iBinder);
        }

        @Override // android.os.Binder
        public boolean onTransact(int i, Parcel parcel, Parcel parcel2, int i2) throws RemoteException {
            String str = DESCRIPTOR;
            if (i >= 1 && i <= 16777215) {
                parcel.enforceInterface(str);
            }
            if (i == 1598968902) {
                parcel2.writeString(str);
                return true;
            }
            if (i == 16777215) {
                parcel2.writeNoException();
                parcel2.writeInt(getInterfaceVersion());
                return true;
            }
            if (i == 16777214) {
                parcel2.writeNoException();
                parcel2.writeString(getInterfaceHash());
                return true;
            }
            switch (i) {
                case 1:
                    int readInt = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    acknowledgeRequest(readInt);
                    return true;
                case 2:
                    RadioResponseInfo radioResponseInfo = (RadioResponseInfo) parcel.readTypedObject(RadioResponseInfo.CREATOR);
                    int readInt2 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    allocatePduSessionIdResponse(radioResponseInfo, readInt2);
                    return true;
                case 3:
                    RadioResponseInfo radioResponseInfo2 = (RadioResponseInfo) parcel.readTypedObject(RadioResponseInfo.CREATOR);
                    parcel.enforceNoDataAvail();
                    cancelHandoverResponse(radioResponseInfo2);
                    return true;
                case 4:
                    RadioResponseInfo radioResponseInfo3 = (RadioResponseInfo) parcel.readTypedObject(RadioResponseInfo.CREATOR);
                    parcel.enforceNoDataAvail();
                    deactivateDataCallResponse(radioResponseInfo3);
                    return true;
                case 5:
                    RadioResponseInfo radioResponseInfo4 = (RadioResponseInfo) parcel.readTypedObject(RadioResponseInfo.CREATOR);
                    SetupDataCallResult[] setupDataCallResultArr = (SetupDataCallResult[]) parcel.createTypedArray(SetupDataCallResult.CREATOR);
                    parcel.enforceNoDataAvail();
                    getDataCallListResponse(radioResponseInfo4, setupDataCallResultArr);
                    return true;
                case 6:
                    RadioResponseInfo radioResponseInfo5 = (RadioResponseInfo) parcel.readTypedObject(RadioResponseInfo.CREATOR);
                    SlicingConfig slicingConfig = (SlicingConfig) parcel.readTypedObject(SlicingConfig.CREATOR);
                    parcel.enforceNoDataAvail();
                    getSlicingConfigResponse(radioResponseInfo5, slicingConfig);
                    return true;
                case 7:
                    RadioResponseInfo radioResponseInfo6 = (RadioResponseInfo) parcel.readTypedObject(RadioResponseInfo.CREATOR);
                    parcel.enforceNoDataAvail();
                    releasePduSessionIdResponse(radioResponseInfo6);
                    return true;
                case 8:
                    RadioResponseInfo radioResponseInfo7 = (RadioResponseInfo) parcel.readTypedObject(RadioResponseInfo.CREATOR);
                    parcel.enforceNoDataAvail();
                    setDataAllowedResponse(radioResponseInfo7);
                    return true;
                case 9:
                    RadioResponseInfo radioResponseInfo8 = (RadioResponseInfo) parcel.readTypedObject(RadioResponseInfo.CREATOR);
                    parcel.enforceNoDataAvail();
                    setDataProfileResponse(radioResponseInfo8);
                    return true;
                case 10:
                    RadioResponseInfo radioResponseInfo9 = (RadioResponseInfo) parcel.readTypedObject(RadioResponseInfo.CREATOR);
                    parcel.enforceNoDataAvail();
                    setDataThrottlingResponse(radioResponseInfo9);
                    return true;
                case 11:
                    RadioResponseInfo radioResponseInfo10 = (RadioResponseInfo) parcel.readTypedObject(RadioResponseInfo.CREATOR);
                    parcel.enforceNoDataAvail();
                    setInitialAttachApnResponse(radioResponseInfo10);
                    return true;
                case 12:
                    RadioResponseInfo radioResponseInfo11 = (RadioResponseInfo) parcel.readTypedObject(RadioResponseInfo.CREATOR);
                    SetupDataCallResult setupDataCallResult = (SetupDataCallResult) parcel.readTypedObject(SetupDataCallResult.CREATOR);
                    parcel.enforceNoDataAvail();
                    setupDataCallResponse(radioResponseInfo11, setupDataCallResult);
                    return true;
                case 13:
                    RadioResponseInfo radioResponseInfo12 = (RadioResponseInfo) parcel.readTypedObject(RadioResponseInfo.CREATOR);
                    parcel.enforceNoDataAvail();
                    startHandoverResponse(radioResponseInfo12);
                    return true;
                case 14:
                    RadioResponseInfo radioResponseInfo13 = (RadioResponseInfo) parcel.readTypedObject(RadioResponseInfo.CREATOR);
                    KeepaliveStatus keepaliveStatus = (KeepaliveStatus) parcel.readTypedObject(KeepaliveStatus.CREATOR);
                    parcel.enforceNoDataAvail();
                    startKeepaliveResponse(radioResponseInfo13, keepaliveStatus);
                    return true;
                case 15:
                    RadioResponseInfo radioResponseInfo14 = (RadioResponseInfo) parcel.readTypedObject(RadioResponseInfo.CREATOR);
                    parcel.enforceNoDataAvail();
                    stopKeepaliveResponse(radioResponseInfo14);
                    return true;
                default:
                    return super.onTransact(i, parcel, parcel2, i2);
            }
        }

        private static class Proxy implements IRadioDataResponse {
            private IBinder mRemote;
            private int mCachedVersion = -1;
            private String mCachedHash = "-1";

            Proxy(IBinder iBinder) {
                this.mRemote = iBinder;
            }

            @Override // android.os.IInterface
            public IBinder asBinder() {
                return this.mRemote;
            }

            public String getInterfaceDescriptor() {
                return DESCRIPTOR;
            }

            @Override // android.hardware.radio.data.IRadioDataResponse
            public void acknowledgeRequest(int i) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                try {
                    obtain.writeInterfaceToken(DESCRIPTOR);
                    obtain.writeInt(i);
                    if (this.mRemote.transact(1, obtain, null, 1)) {
                    } else {
                        throw new RemoteException("Method acknowledgeRequest is unimplemented.");
                    }
                } finally {
                    obtain.recycle();
                }
            }

            @Override // android.hardware.radio.data.IRadioDataResponse
            public void allocatePduSessionIdResponse(RadioResponseInfo radioResponseInfo, int i) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                try {
                    obtain.writeInterfaceToken(DESCRIPTOR);
                    obtain.writeTypedObject(radioResponseInfo, 0);
                    obtain.writeInt(i);
                    if (this.mRemote.transact(2, obtain, null, 1)) {
                    } else {
                        throw new RemoteException("Method allocatePduSessionIdResponse is unimplemented.");
                    }
                } finally {
                    obtain.recycle();
                }
            }

            @Override // android.hardware.radio.data.IRadioDataResponse
            public void cancelHandoverResponse(RadioResponseInfo radioResponseInfo) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                try {
                    obtain.writeInterfaceToken(DESCRIPTOR);
                    obtain.writeTypedObject(radioResponseInfo, 0);
                    if (this.mRemote.transact(3, obtain, null, 1)) {
                    } else {
                        throw new RemoteException("Method cancelHandoverResponse is unimplemented.");
                    }
                } finally {
                    obtain.recycle();
                }
            }

            @Override // android.hardware.radio.data.IRadioDataResponse
            public void deactivateDataCallResponse(RadioResponseInfo radioResponseInfo) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                try {
                    obtain.writeInterfaceToken(DESCRIPTOR);
                    obtain.writeTypedObject(radioResponseInfo, 0);
                    if (this.mRemote.transact(4, obtain, null, 1)) {
                    } else {
                        throw new RemoteException("Method deactivateDataCallResponse is unimplemented.");
                    }
                } finally {
                    obtain.recycle();
                }
            }

            @Override // android.hardware.radio.data.IRadioDataResponse
            public void getDataCallListResponse(RadioResponseInfo radioResponseInfo, SetupDataCallResult[] setupDataCallResultArr) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                try {
                    obtain.writeInterfaceToken(DESCRIPTOR);
                    obtain.writeTypedObject(radioResponseInfo, 0);
                    obtain.writeTypedArray(setupDataCallResultArr, 0);
                    if (this.mRemote.transact(5, obtain, null, 1)) {
                    } else {
                        throw new RemoteException("Method getDataCallListResponse is unimplemented.");
                    }
                } finally {
                    obtain.recycle();
                }
            }

            @Override // android.hardware.radio.data.IRadioDataResponse
            public void getSlicingConfigResponse(RadioResponseInfo radioResponseInfo, SlicingConfig slicingConfig) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                try {
                    obtain.writeInterfaceToken(DESCRIPTOR);
                    obtain.writeTypedObject(radioResponseInfo, 0);
                    obtain.writeTypedObject(slicingConfig, 0);
                    if (this.mRemote.transact(6, obtain, null, 1)) {
                    } else {
                        throw new RemoteException("Method getSlicingConfigResponse is unimplemented.");
                    }
                } finally {
                    obtain.recycle();
                }
            }

            @Override // android.hardware.radio.data.IRadioDataResponse
            public void releasePduSessionIdResponse(RadioResponseInfo radioResponseInfo) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                try {
                    obtain.writeInterfaceToken(DESCRIPTOR);
                    obtain.writeTypedObject(radioResponseInfo, 0);
                    if (this.mRemote.transact(7, obtain, null, 1)) {
                    } else {
                        throw new RemoteException("Method releasePduSessionIdResponse is unimplemented.");
                    }
                } finally {
                    obtain.recycle();
                }
            }

            @Override // android.hardware.radio.data.IRadioDataResponse
            public void setDataAllowedResponse(RadioResponseInfo radioResponseInfo) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                try {
                    obtain.writeInterfaceToken(DESCRIPTOR);
                    obtain.writeTypedObject(radioResponseInfo, 0);
                    if (this.mRemote.transact(8, obtain, null, 1)) {
                    } else {
                        throw new RemoteException("Method setDataAllowedResponse is unimplemented.");
                    }
                } finally {
                    obtain.recycle();
                }
            }

            @Override // android.hardware.radio.data.IRadioDataResponse
            public void setDataProfileResponse(RadioResponseInfo radioResponseInfo) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                try {
                    obtain.writeInterfaceToken(DESCRIPTOR);
                    obtain.writeTypedObject(radioResponseInfo, 0);
                    if (this.mRemote.transact(9, obtain, null, 1)) {
                    } else {
                        throw new RemoteException("Method setDataProfileResponse is unimplemented.");
                    }
                } finally {
                    obtain.recycle();
                }
            }

            @Override // android.hardware.radio.data.IRadioDataResponse
            public void setDataThrottlingResponse(RadioResponseInfo radioResponseInfo) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                try {
                    obtain.writeInterfaceToken(DESCRIPTOR);
                    obtain.writeTypedObject(radioResponseInfo, 0);
                    if (this.mRemote.transact(10, obtain, null, 1)) {
                    } else {
                        throw new RemoteException("Method setDataThrottlingResponse is unimplemented.");
                    }
                } finally {
                    obtain.recycle();
                }
            }

            @Override // android.hardware.radio.data.IRadioDataResponse
            public void setInitialAttachApnResponse(RadioResponseInfo radioResponseInfo) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                try {
                    obtain.writeInterfaceToken(DESCRIPTOR);
                    obtain.writeTypedObject(radioResponseInfo, 0);
                    if (this.mRemote.transact(11, obtain, null, 1)) {
                    } else {
                        throw new RemoteException("Method setInitialAttachApnResponse is unimplemented.");
                    }
                } finally {
                    obtain.recycle();
                }
            }

            @Override // android.hardware.radio.data.IRadioDataResponse
            public void setupDataCallResponse(RadioResponseInfo radioResponseInfo, SetupDataCallResult setupDataCallResult) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                try {
                    obtain.writeInterfaceToken(DESCRIPTOR);
                    obtain.writeTypedObject(radioResponseInfo, 0);
                    obtain.writeTypedObject(setupDataCallResult, 0);
                    if (this.mRemote.transact(12, obtain, null, 1)) {
                    } else {
                        throw new RemoteException("Method setupDataCallResponse is unimplemented.");
                    }
                } finally {
                    obtain.recycle();
                }
            }

            @Override // android.hardware.radio.data.IRadioDataResponse
            public void startHandoverResponse(RadioResponseInfo radioResponseInfo) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                try {
                    obtain.writeInterfaceToken(DESCRIPTOR);
                    obtain.writeTypedObject(radioResponseInfo, 0);
                    if (this.mRemote.transact(13, obtain, null, 1)) {
                    } else {
                        throw new RemoteException("Method startHandoverResponse is unimplemented.");
                    }
                } finally {
                    obtain.recycle();
                }
            }

            @Override // android.hardware.radio.data.IRadioDataResponse
            public void startKeepaliveResponse(RadioResponseInfo radioResponseInfo, KeepaliveStatus keepaliveStatus) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                try {
                    obtain.writeInterfaceToken(DESCRIPTOR);
                    obtain.writeTypedObject(radioResponseInfo, 0);
                    obtain.writeTypedObject(keepaliveStatus, 0);
                    if (this.mRemote.transact(14, obtain, null, 1)) {
                    } else {
                        throw new RemoteException("Method startKeepaliveResponse is unimplemented.");
                    }
                } finally {
                    obtain.recycle();
                }
            }

            @Override // android.hardware.radio.data.IRadioDataResponse
            public void stopKeepaliveResponse(RadioResponseInfo radioResponseInfo) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                try {
                    obtain.writeInterfaceToken(DESCRIPTOR);
                    obtain.writeTypedObject(radioResponseInfo, 0);
                    if (this.mRemote.transact(15, obtain, null, 1)) {
                    } else {
                        throw new RemoteException("Method stopKeepaliveResponse is unimplemented.");
                    }
                } finally {
                    obtain.recycle();
                }
            }

            @Override // android.hardware.radio.data.IRadioDataResponse
            public int getInterfaceVersion() throws RemoteException {
                if (this.mCachedVersion == -1) {
                    Parcel obtain = Parcel.obtain(asBinder());
                    Parcel obtain2 = Parcel.obtain();
                    try {
                        obtain.writeInterfaceToken(DESCRIPTOR);
                        this.mRemote.transact(16777215, obtain, obtain2, 0);
                        obtain2.readException();
                        this.mCachedVersion = obtain2.readInt();
                    } finally {
                        obtain2.recycle();
                        obtain.recycle();
                    }
                }
                return this.mCachedVersion;
            }

            @Override // android.hardware.radio.data.IRadioDataResponse
            public synchronized String getInterfaceHash() throws RemoteException {
                if ("-1".equals(this.mCachedHash)) {
                    Parcel obtain = Parcel.obtain(asBinder());
                    Parcel obtain2 = Parcel.obtain();
                    try {
                        obtain.writeInterfaceToken(DESCRIPTOR);
                        this.mRemote.transact(16777214, obtain, obtain2, 0);
                        obtain2.readException();
                        this.mCachedHash = obtain2.readString();
                        obtain2.recycle();
                        obtain.recycle();
                    } catch (Throwable th) {
                        obtain2.recycle();
                        obtain.recycle();
                        throw th;
                    }
                }
                return this.mCachedHash;
            }
        }
    }
}
