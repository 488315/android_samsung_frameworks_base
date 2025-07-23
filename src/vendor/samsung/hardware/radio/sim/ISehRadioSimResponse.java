package vendor.samsung.hardware.radio.sim;

import android.os.Binder;
import android.os.IBinder;
import android.os.IInterface;
import android.os.Parcel;
import android.os.RemoteException;
import vendor.samsung.hardware.radio.SehRadioResponseInfo;

/* loaded from: classes6.dex */
public interface ISehRadioSimResponse extends IInterface {
    public static final String DESCRIPTOR = "vendor$samsung$hardware$radio$sim$ISehRadioSimResponse".replace('$', '.');
    public static final String HASH = "e94363ef2041c98feabe84e756d983ad196394e8";
    public static final int VERSION = 1;

    void accessPhonebookEntryResponse(SehRadioResponseInfo sehRadioResponseInfo, int i) throws RemoteException;

    void changeIccPersonalizationResponse(SehRadioResponseInfo sehRadioResponseInfo) throws RemoteException;

    void getAtrResponse(SehRadioResponseInfo sehRadioResponseInfo, String str) throws RemoteException;

    void getIccCardStatusResponse(SehRadioResponseInfo sehRadioResponseInfo, SehCardStatus sehCardStatus) throws RemoteException;

    String getInterfaceHash() throws RemoteException;

    int getInterfaceVersion() throws RemoteException;

    void getPhonebookEntryResponse(SehRadioResponseInfo sehRadioResponseInfo, SehSimPhonebookResponse sehSimPhonebookResponse) throws RemoteException;

    void getPhonebookStorageInfoResponse(SehRadioResponseInfo sehRadioResponseInfo, SehPhonebookInfo sehPhonebookInfo) throws RemoteException;

    void getSimLockInfoResponse(SehRadioResponseInfo sehRadioResponseInfo, SehSimLockInfo sehSimLockInfo) throws RemoteException;

    void getUsimPhonebookCapabilityResponse(SehRadioResponseInfo sehRadioResponseInfo, int[] iArr) throws RemoteException;

    void setSimInitEventResponse(SehRadioResponseInfo sehRadioResponseInfo) throws RemoteException;

    void setSimOnOffResponse(SehRadioResponseInfo sehRadioResponseInfo) throws RemoteException;

    void supplyIccPersonalizationResponse(SehRadioResponseInfo sehRadioResponseInfo) throws RemoteException;

    void supplyNetworkDepersonalizationResponse(SehRadioResponseInfo sehRadioResponseInfo, int i) throws RemoteException;

    public static class Default implements ISehRadioSimResponse {
        @Override // vendor.samsung.hardware.radio.sim.ISehRadioSimResponse
        public void accessPhonebookEntryResponse(SehRadioResponseInfo sehRadioResponseInfo, int i) throws RemoteException {
        }

        @Override // android.os.IInterface
        public IBinder asBinder() {
            return null;
        }

        @Override // vendor.samsung.hardware.radio.sim.ISehRadioSimResponse
        public void changeIccPersonalizationResponse(SehRadioResponseInfo sehRadioResponseInfo) throws RemoteException {
        }

        @Override // vendor.samsung.hardware.radio.sim.ISehRadioSimResponse
        public void getAtrResponse(SehRadioResponseInfo sehRadioResponseInfo, String str) throws RemoteException {
        }

        @Override // vendor.samsung.hardware.radio.sim.ISehRadioSimResponse
        public void getIccCardStatusResponse(SehRadioResponseInfo sehRadioResponseInfo, SehCardStatus sehCardStatus) throws RemoteException {
        }

        @Override // vendor.samsung.hardware.radio.sim.ISehRadioSimResponse
        public int getInterfaceVersion() {
            return 0;
        }

        @Override // vendor.samsung.hardware.radio.sim.ISehRadioSimResponse
        public void getPhonebookEntryResponse(SehRadioResponseInfo sehRadioResponseInfo, SehSimPhonebookResponse sehSimPhonebookResponse) throws RemoteException {
        }

        @Override // vendor.samsung.hardware.radio.sim.ISehRadioSimResponse
        public void getPhonebookStorageInfoResponse(SehRadioResponseInfo sehRadioResponseInfo, SehPhonebookInfo sehPhonebookInfo) throws RemoteException {
        }

        @Override // vendor.samsung.hardware.radio.sim.ISehRadioSimResponse
        public void getSimLockInfoResponse(SehRadioResponseInfo sehRadioResponseInfo, SehSimLockInfo sehSimLockInfo) throws RemoteException {
        }

        @Override // vendor.samsung.hardware.radio.sim.ISehRadioSimResponse
        public void getUsimPhonebookCapabilityResponse(SehRadioResponseInfo sehRadioResponseInfo, int[] iArr) throws RemoteException {
        }

        @Override // vendor.samsung.hardware.radio.sim.ISehRadioSimResponse
        public void setSimInitEventResponse(SehRadioResponseInfo sehRadioResponseInfo) throws RemoteException {
        }

        @Override // vendor.samsung.hardware.radio.sim.ISehRadioSimResponse
        public void setSimOnOffResponse(SehRadioResponseInfo sehRadioResponseInfo) throws RemoteException {
        }

        @Override // vendor.samsung.hardware.radio.sim.ISehRadioSimResponse
        public void supplyIccPersonalizationResponse(SehRadioResponseInfo sehRadioResponseInfo) throws RemoteException {
        }

        @Override // vendor.samsung.hardware.radio.sim.ISehRadioSimResponse
        public void supplyNetworkDepersonalizationResponse(SehRadioResponseInfo sehRadioResponseInfo, int i) throws RemoteException {
        }

        @Override // vendor.samsung.hardware.radio.sim.ISehRadioSimResponse
        public String getInterfaceHash() {
            return "";
        }
    }

    public static abstract class Stub extends Binder implements ISehRadioSimResponse {
        static final int TRANSACTION_accessPhonebookEntryResponse = 11;
        static final int TRANSACTION_changeIccPersonalizationResponse = 9;
        static final int TRANSACTION_getAtrResponse = 12;
        static final int TRANSACTION_getIccCardStatusResponse = 1;
        static final int TRANSACTION_getInterfaceHash = 16777214;
        static final int TRANSACTION_getInterfaceVersion = 16777215;
        static final int TRANSACTION_getPhonebookEntryResponse = 10;
        static final int TRANSACTION_getPhonebookStorageInfoResponse = 3;
        static final int TRANSACTION_getSimLockInfoResponse = 7;
        static final int TRANSACTION_getUsimPhonebookCapabilityResponse = 4;
        static final int TRANSACTION_setSimInitEventResponse = 6;
        static final int TRANSACTION_setSimOnOffResponse = 5;
        static final int TRANSACTION_supplyIccPersonalizationResponse = 8;
        static final int TRANSACTION_supplyNetworkDepersonalizationResponse = 2;

        @Override // android.os.IInterface
        public IBinder asBinder() {
            return this;
        }

        public Stub() {
            markVintfStability();
            attachInterface(this, DESCRIPTOR);
        }

        public static ISehRadioSimResponse asInterface(IBinder iBinder) {
            if (iBinder == null) {
                return null;
            }
            IInterface queryLocalInterface = iBinder.queryLocalInterface(DESCRIPTOR);
            if (queryLocalInterface != null && (queryLocalInterface instanceof ISehRadioSimResponse)) {
                return (ISehRadioSimResponse) queryLocalInterface;
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
                    SehRadioResponseInfo sehRadioResponseInfo = (SehRadioResponseInfo) parcel.readTypedObject(SehRadioResponseInfo.CREATOR);
                    SehCardStatus sehCardStatus = (SehCardStatus) parcel.readTypedObject(SehCardStatus.CREATOR);
                    parcel.enforceNoDataAvail();
                    getIccCardStatusResponse(sehRadioResponseInfo, sehCardStatus);
                    return true;
                case 2:
                    SehRadioResponseInfo sehRadioResponseInfo2 = (SehRadioResponseInfo) parcel.readTypedObject(SehRadioResponseInfo.CREATOR);
                    int readInt = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    supplyNetworkDepersonalizationResponse(sehRadioResponseInfo2, readInt);
                    return true;
                case 3:
                    SehRadioResponseInfo sehRadioResponseInfo3 = (SehRadioResponseInfo) parcel.readTypedObject(SehRadioResponseInfo.CREATOR);
                    SehPhonebookInfo sehPhonebookInfo = (SehPhonebookInfo) parcel.readTypedObject(SehPhonebookInfo.CREATOR);
                    parcel.enforceNoDataAvail();
                    getPhonebookStorageInfoResponse(sehRadioResponseInfo3, sehPhonebookInfo);
                    return true;
                case 4:
                    SehRadioResponseInfo sehRadioResponseInfo4 = (SehRadioResponseInfo) parcel.readTypedObject(SehRadioResponseInfo.CREATOR);
                    int[] createIntArray = parcel.createIntArray();
                    parcel.enforceNoDataAvail();
                    getUsimPhonebookCapabilityResponse(sehRadioResponseInfo4, createIntArray);
                    return true;
                case 5:
                    SehRadioResponseInfo sehRadioResponseInfo5 = (SehRadioResponseInfo) parcel.readTypedObject(SehRadioResponseInfo.CREATOR);
                    parcel.enforceNoDataAvail();
                    setSimOnOffResponse(sehRadioResponseInfo5);
                    return true;
                case 6:
                    SehRadioResponseInfo sehRadioResponseInfo6 = (SehRadioResponseInfo) parcel.readTypedObject(SehRadioResponseInfo.CREATOR);
                    parcel.enforceNoDataAvail();
                    setSimInitEventResponse(sehRadioResponseInfo6);
                    return true;
                case 7:
                    SehRadioResponseInfo sehRadioResponseInfo7 = (SehRadioResponseInfo) parcel.readTypedObject(SehRadioResponseInfo.CREATOR);
                    SehSimLockInfo sehSimLockInfo = (SehSimLockInfo) parcel.readTypedObject(SehSimLockInfo.CREATOR);
                    parcel.enforceNoDataAvail();
                    getSimLockInfoResponse(sehRadioResponseInfo7, sehSimLockInfo);
                    return true;
                case 8:
                    SehRadioResponseInfo sehRadioResponseInfo8 = (SehRadioResponseInfo) parcel.readTypedObject(SehRadioResponseInfo.CREATOR);
                    parcel.enforceNoDataAvail();
                    supplyIccPersonalizationResponse(sehRadioResponseInfo8);
                    return true;
                case 9:
                    SehRadioResponseInfo sehRadioResponseInfo9 = (SehRadioResponseInfo) parcel.readTypedObject(SehRadioResponseInfo.CREATOR);
                    parcel.enforceNoDataAvail();
                    changeIccPersonalizationResponse(sehRadioResponseInfo9);
                    return true;
                case 10:
                    SehRadioResponseInfo sehRadioResponseInfo10 = (SehRadioResponseInfo) parcel.readTypedObject(SehRadioResponseInfo.CREATOR);
                    SehSimPhonebookResponse sehSimPhonebookResponse = (SehSimPhonebookResponse) parcel.readTypedObject(SehSimPhonebookResponse.CREATOR);
                    parcel.enforceNoDataAvail();
                    getPhonebookEntryResponse(sehRadioResponseInfo10, sehSimPhonebookResponse);
                    return true;
                case 11:
                    SehRadioResponseInfo sehRadioResponseInfo11 = (SehRadioResponseInfo) parcel.readTypedObject(SehRadioResponseInfo.CREATOR);
                    int readInt2 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    accessPhonebookEntryResponse(sehRadioResponseInfo11, readInt2);
                    return true;
                case 12:
                    SehRadioResponseInfo sehRadioResponseInfo12 = (SehRadioResponseInfo) parcel.readTypedObject(SehRadioResponseInfo.CREATOR);
                    String readString = parcel.readString();
                    parcel.enforceNoDataAvail();
                    getAtrResponse(sehRadioResponseInfo12, readString);
                    return true;
                default:
                    return super.onTransact(i, parcel, parcel2, i2);
            }
        }

        private static class Proxy implements ISehRadioSimResponse {
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

            @Override // vendor.samsung.hardware.radio.sim.ISehRadioSimResponse
            public void getIccCardStatusResponse(SehRadioResponseInfo sehRadioResponseInfo, SehCardStatus sehCardStatus) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                try {
                    obtain.writeInterfaceToken(DESCRIPTOR);
                    obtain.writeTypedObject(sehRadioResponseInfo, 0);
                    obtain.writeTypedObject(sehCardStatus, 0);
                    if (this.mRemote.transact(1, obtain, null, 1)) {
                    } else {
                        throw new RemoteException("Method getIccCardStatusResponse is unimplemented.");
                    }
                } finally {
                    obtain.recycle();
                }
            }

            @Override // vendor.samsung.hardware.radio.sim.ISehRadioSimResponse
            public void supplyNetworkDepersonalizationResponse(SehRadioResponseInfo sehRadioResponseInfo, int i) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                try {
                    obtain.writeInterfaceToken(DESCRIPTOR);
                    obtain.writeTypedObject(sehRadioResponseInfo, 0);
                    obtain.writeInt(i);
                    if (this.mRemote.transact(2, obtain, null, 1)) {
                    } else {
                        throw new RemoteException("Method supplyNetworkDepersonalizationResponse is unimplemented.");
                    }
                } finally {
                    obtain.recycle();
                }
            }

            @Override // vendor.samsung.hardware.radio.sim.ISehRadioSimResponse
            public void getPhonebookStorageInfoResponse(SehRadioResponseInfo sehRadioResponseInfo, SehPhonebookInfo sehPhonebookInfo) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                try {
                    obtain.writeInterfaceToken(DESCRIPTOR);
                    obtain.writeTypedObject(sehRadioResponseInfo, 0);
                    obtain.writeTypedObject(sehPhonebookInfo, 0);
                    if (this.mRemote.transact(3, obtain, null, 1)) {
                    } else {
                        throw new RemoteException("Method getPhonebookStorageInfoResponse is unimplemented.");
                    }
                } finally {
                    obtain.recycle();
                }
            }

            @Override // vendor.samsung.hardware.radio.sim.ISehRadioSimResponse
            public void getUsimPhonebookCapabilityResponse(SehRadioResponseInfo sehRadioResponseInfo, int[] iArr) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                try {
                    obtain.writeInterfaceToken(DESCRIPTOR);
                    obtain.writeTypedObject(sehRadioResponseInfo, 0);
                    obtain.writeIntArray(iArr);
                    if (this.mRemote.transact(4, obtain, null, 1)) {
                    } else {
                        throw new RemoteException("Method getUsimPhonebookCapabilityResponse is unimplemented.");
                    }
                } finally {
                    obtain.recycle();
                }
            }

            @Override // vendor.samsung.hardware.radio.sim.ISehRadioSimResponse
            public void setSimOnOffResponse(SehRadioResponseInfo sehRadioResponseInfo) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                try {
                    obtain.writeInterfaceToken(DESCRIPTOR);
                    obtain.writeTypedObject(sehRadioResponseInfo, 0);
                    if (this.mRemote.transact(5, obtain, null, 1)) {
                    } else {
                        throw new RemoteException("Method setSimOnOffResponse is unimplemented.");
                    }
                } finally {
                    obtain.recycle();
                }
            }

            @Override // vendor.samsung.hardware.radio.sim.ISehRadioSimResponse
            public void setSimInitEventResponse(SehRadioResponseInfo sehRadioResponseInfo) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                try {
                    obtain.writeInterfaceToken(DESCRIPTOR);
                    obtain.writeTypedObject(sehRadioResponseInfo, 0);
                    if (this.mRemote.transact(6, obtain, null, 1)) {
                    } else {
                        throw new RemoteException("Method setSimInitEventResponse is unimplemented.");
                    }
                } finally {
                    obtain.recycle();
                }
            }

            @Override // vendor.samsung.hardware.radio.sim.ISehRadioSimResponse
            public void getSimLockInfoResponse(SehRadioResponseInfo sehRadioResponseInfo, SehSimLockInfo sehSimLockInfo) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                try {
                    obtain.writeInterfaceToken(DESCRIPTOR);
                    obtain.writeTypedObject(sehRadioResponseInfo, 0);
                    obtain.writeTypedObject(sehSimLockInfo, 0);
                    if (this.mRemote.transact(7, obtain, null, 1)) {
                    } else {
                        throw new RemoteException("Method getSimLockInfoResponse is unimplemented.");
                    }
                } finally {
                    obtain.recycle();
                }
            }

            @Override // vendor.samsung.hardware.radio.sim.ISehRadioSimResponse
            public void supplyIccPersonalizationResponse(SehRadioResponseInfo sehRadioResponseInfo) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                try {
                    obtain.writeInterfaceToken(DESCRIPTOR);
                    obtain.writeTypedObject(sehRadioResponseInfo, 0);
                    if (this.mRemote.transact(8, obtain, null, 1)) {
                    } else {
                        throw new RemoteException("Method supplyIccPersonalizationResponse is unimplemented.");
                    }
                } finally {
                    obtain.recycle();
                }
            }

            @Override // vendor.samsung.hardware.radio.sim.ISehRadioSimResponse
            public void changeIccPersonalizationResponse(SehRadioResponseInfo sehRadioResponseInfo) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                try {
                    obtain.writeInterfaceToken(DESCRIPTOR);
                    obtain.writeTypedObject(sehRadioResponseInfo, 0);
                    if (this.mRemote.transact(9, obtain, null, 1)) {
                    } else {
                        throw new RemoteException("Method changeIccPersonalizationResponse is unimplemented.");
                    }
                } finally {
                    obtain.recycle();
                }
            }

            @Override // vendor.samsung.hardware.radio.sim.ISehRadioSimResponse
            public void getPhonebookEntryResponse(SehRadioResponseInfo sehRadioResponseInfo, SehSimPhonebookResponse sehSimPhonebookResponse) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                try {
                    obtain.writeInterfaceToken(DESCRIPTOR);
                    obtain.writeTypedObject(sehRadioResponseInfo, 0);
                    obtain.writeTypedObject(sehSimPhonebookResponse, 0);
                    if (this.mRemote.transact(10, obtain, null, 1)) {
                    } else {
                        throw new RemoteException("Method getPhonebookEntryResponse is unimplemented.");
                    }
                } finally {
                    obtain.recycle();
                }
            }

            @Override // vendor.samsung.hardware.radio.sim.ISehRadioSimResponse
            public void accessPhonebookEntryResponse(SehRadioResponseInfo sehRadioResponseInfo, int i) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                try {
                    obtain.writeInterfaceToken(DESCRIPTOR);
                    obtain.writeTypedObject(sehRadioResponseInfo, 0);
                    obtain.writeInt(i);
                    if (this.mRemote.transact(11, obtain, null, 1)) {
                    } else {
                        throw new RemoteException("Method accessPhonebookEntryResponse is unimplemented.");
                    }
                } finally {
                    obtain.recycle();
                }
            }

            @Override // vendor.samsung.hardware.radio.sim.ISehRadioSimResponse
            public void getAtrResponse(SehRadioResponseInfo sehRadioResponseInfo, String str) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                try {
                    obtain.writeInterfaceToken(DESCRIPTOR);
                    obtain.writeTypedObject(sehRadioResponseInfo, 0);
                    obtain.writeString(str);
                    if (this.mRemote.transact(12, obtain, null, 1)) {
                    } else {
                        throw new RemoteException("Method getAtrResponse is unimplemented.");
                    }
                } finally {
                    obtain.recycle();
                }
            }

            @Override // vendor.samsung.hardware.radio.sim.ISehRadioSimResponse
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

            @Override // vendor.samsung.hardware.radio.sim.ISehRadioSimResponse
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
