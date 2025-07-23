package android.hardware.radio.data;

import android.hardware.radio.data.IRadioDataIndication;
import android.hardware.radio.data.IRadioDataResponse;
import android.os.Binder;
import android.os.IBinder;
import android.os.IInterface;
import android.os.Parcel;
import android.os.RemoteException;

/* loaded from: classes2.dex */
public interface IRadioData extends IInterface {
    public static final String DESCRIPTOR = "android$hardware$radio$data$IRadioData".replace('$', '.');
    public static final String HASH = "70713939dbe39fdbd3a294b3a3e3d2842b3bf4eb";
    public static final int VERSION = 4;

    void allocatePduSessionId(int i) throws RemoteException;

    void cancelHandover(int i, int i2) throws RemoteException;

    void deactivateDataCall(int i, int i2, int i3) throws RemoteException;

    void getDataCallList(int i) throws RemoteException;

    String getInterfaceHash() throws RemoteException;

    int getInterfaceVersion() throws RemoteException;

    void getSlicingConfig(int i) throws RemoteException;

    void releasePduSessionId(int i, int i2) throws RemoteException;

    void responseAcknowledgement() throws RemoteException;

    void setDataAllowed(int i, boolean z) throws RemoteException;

    void setDataProfile(int i, DataProfileInfo[] dataProfileInfoArr) throws RemoteException;

    void setDataThrottling(int i, byte b, long j) throws RemoteException;

    void setInitialAttachApn(int i, DataProfileInfo dataProfileInfo) throws RemoteException;

    void setResponseFunctions(IRadioDataResponse iRadioDataResponse, IRadioDataIndication iRadioDataIndication) throws RemoteException;

    void setupDataCall(int i, int i2, DataProfileInfo dataProfileInfo, boolean z, int i3, LinkAddress[] linkAddressArr, String[] strArr, int i4, SliceInfo sliceInfo, boolean z2) throws RemoteException;

    void startHandover(int i, int i2) throws RemoteException;

    void startKeepalive(int i, KeepaliveRequest keepaliveRequest) throws RemoteException;

    void stopKeepalive(int i, int i2) throws RemoteException;

    public static class Default implements IRadioData {
        @Override // android.hardware.radio.data.IRadioData
        public void allocatePduSessionId(int i) throws RemoteException {
        }

        @Override // android.os.IInterface
        public IBinder asBinder() {
            return null;
        }

        @Override // android.hardware.radio.data.IRadioData
        public void cancelHandover(int i, int i2) throws RemoteException {
        }

        @Override // android.hardware.radio.data.IRadioData
        public void deactivateDataCall(int i, int i2, int i3) throws RemoteException {
        }

        @Override // android.hardware.radio.data.IRadioData
        public void getDataCallList(int i) throws RemoteException {
        }

        @Override // android.hardware.radio.data.IRadioData
        public int getInterfaceVersion() {
            return 0;
        }

        @Override // android.hardware.radio.data.IRadioData
        public void getSlicingConfig(int i) throws RemoteException {
        }

        @Override // android.hardware.radio.data.IRadioData
        public void releasePduSessionId(int i, int i2) throws RemoteException {
        }

        @Override // android.hardware.radio.data.IRadioData
        public void responseAcknowledgement() throws RemoteException {
        }

        @Override // android.hardware.radio.data.IRadioData
        public void setDataAllowed(int i, boolean z) throws RemoteException {
        }

        @Override // android.hardware.radio.data.IRadioData
        public void setDataProfile(int i, DataProfileInfo[] dataProfileInfoArr) throws RemoteException {
        }

        @Override // android.hardware.radio.data.IRadioData
        public void setDataThrottling(int i, byte b, long j) throws RemoteException {
        }

        @Override // android.hardware.radio.data.IRadioData
        public void setInitialAttachApn(int i, DataProfileInfo dataProfileInfo) throws RemoteException {
        }

        @Override // android.hardware.radio.data.IRadioData
        public void setResponseFunctions(IRadioDataResponse iRadioDataResponse, IRadioDataIndication iRadioDataIndication) throws RemoteException {
        }

        @Override // android.hardware.radio.data.IRadioData
        public void setupDataCall(int i, int i2, DataProfileInfo dataProfileInfo, boolean z, int i3, LinkAddress[] linkAddressArr, String[] strArr, int i4, SliceInfo sliceInfo, boolean z2) throws RemoteException {
        }

        @Override // android.hardware.radio.data.IRadioData
        public void startHandover(int i, int i2) throws RemoteException {
        }

        @Override // android.hardware.radio.data.IRadioData
        public void startKeepalive(int i, KeepaliveRequest keepaliveRequest) throws RemoteException {
        }

        @Override // android.hardware.radio.data.IRadioData
        public void stopKeepalive(int i, int i2) throws RemoteException {
        }

        @Override // android.hardware.radio.data.IRadioData
        public String getInterfaceHash() {
            return "";
        }
    }

    public static abstract class Stub extends Binder implements IRadioData {
        static final int TRANSACTION_allocatePduSessionId = 1;
        static final int TRANSACTION_cancelHandover = 2;
        static final int TRANSACTION_deactivateDataCall = 3;
        static final int TRANSACTION_getDataCallList = 4;
        static final int TRANSACTION_getInterfaceHash = 16777214;
        static final int TRANSACTION_getInterfaceVersion = 16777215;
        static final int TRANSACTION_getSlicingConfig = 5;
        static final int TRANSACTION_releasePduSessionId = 6;
        static final int TRANSACTION_responseAcknowledgement = 7;
        static final int TRANSACTION_setDataAllowed = 8;
        static final int TRANSACTION_setDataProfile = 9;
        static final int TRANSACTION_setDataThrottling = 10;
        static final int TRANSACTION_setInitialAttachApn = 11;
        static final int TRANSACTION_setResponseFunctions = 12;
        static final int TRANSACTION_setupDataCall = 13;
        static final int TRANSACTION_startHandover = 14;
        static final int TRANSACTION_startKeepalive = 15;
        static final int TRANSACTION_stopKeepalive = 16;

        @Override // android.os.IInterface
        public IBinder asBinder() {
            return this;
        }

        public Stub() {
            markVintfStability();
            attachInterface(this, DESCRIPTOR);
        }

        public static IRadioData asInterface(IBinder iBinder) {
            if (iBinder == null) {
                return null;
            }
            IInterface queryLocalInterface = iBinder.queryLocalInterface(DESCRIPTOR);
            if (queryLocalInterface != null && (queryLocalInterface instanceof IRadioData)) {
                return (IRadioData) queryLocalInterface;
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
                    allocatePduSessionId(readInt);
                    return true;
                case 2:
                    int readInt2 = parcel.readInt();
                    int readInt3 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    cancelHandover(readInt2, readInt3);
                    return true;
                case 3:
                    int readInt4 = parcel.readInt();
                    int readInt5 = parcel.readInt();
                    int readInt6 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    deactivateDataCall(readInt4, readInt5, readInt6);
                    return true;
                case 4:
                    int readInt7 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    getDataCallList(readInt7);
                    return true;
                case 5:
                    int readInt8 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    getSlicingConfig(readInt8);
                    return true;
                case 6:
                    int readInt9 = parcel.readInt();
                    int readInt10 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    releasePduSessionId(readInt9, readInt10);
                    return true;
                case 7:
                    responseAcknowledgement();
                    return true;
                case 8:
                    int readInt11 = parcel.readInt();
                    boolean readBoolean = parcel.readBoolean();
                    parcel.enforceNoDataAvail();
                    setDataAllowed(readInt11, readBoolean);
                    return true;
                case 9:
                    int readInt12 = parcel.readInt();
                    DataProfileInfo[] dataProfileInfoArr = (DataProfileInfo[]) parcel.createTypedArray(DataProfileInfo.CREATOR);
                    parcel.enforceNoDataAvail();
                    setDataProfile(readInt12, dataProfileInfoArr);
                    return true;
                case 10:
                    int readInt13 = parcel.readInt();
                    byte readByte = parcel.readByte();
                    long readLong = parcel.readLong();
                    parcel.enforceNoDataAvail();
                    setDataThrottling(readInt13, readByte, readLong);
                    return true;
                case 11:
                    int readInt14 = parcel.readInt();
                    DataProfileInfo dataProfileInfo = (DataProfileInfo) parcel.readTypedObject(DataProfileInfo.CREATOR);
                    parcel.enforceNoDataAvail();
                    setInitialAttachApn(readInt14, dataProfileInfo);
                    return true;
                case 12:
                    IRadioDataResponse asInterface = IRadioDataResponse.Stub.asInterface(parcel.readStrongBinder());
                    IRadioDataIndication asInterface2 = IRadioDataIndication.Stub.asInterface(parcel.readStrongBinder());
                    parcel.enforceNoDataAvail();
                    setResponseFunctions(asInterface, asInterface2);
                    return true;
                case 13:
                    int readInt15 = parcel.readInt();
                    int readInt16 = parcel.readInt();
                    DataProfileInfo dataProfileInfo2 = (DataProfileInfo) parcel.readTypedObject(DataProfileInfo.CREATOR);
                    boolean readBoolean2 = parcel.readBoolean();
                    int readInt17 = parcel.readInt();
                    LinkAddress[] linkAddressArr = (LinkAddress[]) parcel.createTypedArray(LinkAddress.CREATOR);
                    String[] createStringArray = parcel.createStringArray();
                    int readInt18 = parcel.readInt();
                    SliceInfo sliceInfo = (SliceInfo) parcel.readTypedObject(SliceInfo.CREATOR);
                    boolean readBoolean3 = parcel.readBoolean();
                    parcel.enforceNoDataAvail();
                    setupDataCall(readInt15, readInt16, dataProfileInfo2, readBoolean2, readInt17, linkAddressArr, createStringArray, readInt18, sliceInfo, readBoolean3);
                    return true;
                case 14:
                    int readInt19 = parcel.readInt();
                    int readInt20 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    startHandover(readInt19, readInt20);
                    return true;
                case 15:
                    int readInt21 = parcel.readInt();
                    KeepaliveRequest keepaliveRequest = (KeepaliveRequest) parcel.readTypedObject(KeepaliveRequest.CREATOR);
                    parcel.enforceNoDataAvail();
                    startKeepalive(readInt21, keepaliveRequest);
                    return true;
                case 16:
                    int readInt22 = parcel.readInt();
                    int readInt23 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    stopKeepalive(readInt22, readInt23);
                    return true;
                default:
                    return super.onTransact(i, parcel, parcel2, i2);
            }
        }

        private static class Proxy implements IRadioData {
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

            @Override // android.hardware.radio.data.IRadioData
            public void allocatePduSessionId(int i) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                try {
                    obtain.writeInterfaceToken(DESCRIPTOR);
                    obtain.writeInt(i);
                    if (this.mRemote.transact(1, obtain, null, 1)) {
                    } else {
                        throw new RemoteException("Method allocatePduSessionId is unimplemented.");
                    }
                } finally {
                    obtain.recycle();
                }
            }

            @Override // android.hardware.radio.data.IRadioData
            public void cancelHandover(int i, int i2) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                try {
                    obtain.writeInterfaceToken(DESCRIPTOR);
                    obtain.writeInt(i);
                    obtain.writeInt(i2);
                    if (this.mRemote.transact(2, obtain, null, 1)) {
                    } else {
                        throw new RemoteException("Method cancelHandover is unimplemented.");
                    }
                } finally {
                    obtain.recycle();
                }
            }

            @Override // android.hardware.radio.data.IRadioData
            public void deactivateDataCall(int i, int i2, int i3) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                try {
                    obtain.writeInterfaceToken(DESCRIPTOR);
                    obtain.writeInt(i);
                    obtain.writeInt(i2);
                    obtain.writeInt(i3);
                    if (this.mRemote.transact(3, obtain, null, 1)) {
                    } else {
                        throw new RemoteException("Method deactivateDataCall is unimplemented.");
                    }
                } finally {
                    obtain.recycle();
                }
            }

            @Override // android.hardware.radio.data.IRadioData
            public void getDataCallList(int i) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                try {
                    obtain.writeInterfaceToken(DESCRIPTOR);
                    obtain.writeInt(i);
                    if (this.mRemote.transact(4, obtain, null, 1)) {
                    } else {
                        throw new RemoteException("Method getDataCallList is unimplemented.");
                    }
                } finally {
                    obtain.recycle();
                }
            }

            @Override // android.hardware.radio.data.IRadioData
            public void getSlicingConfig(int i) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                try {
                    obtain.writeInterfaceToken(DESCRIPTOR);
                    obtain.writeInt(i);
                    if (this.mRemote.transact(5, obtain, null, 1)) {
                    } else {
                        throw new RemoteException("Method getSlicingConfig is unimplemented.");
                    }
                } finally {
                    obtain.recycle();
                }
            }

            @Override // android.hardware.radio.data.IRadioData
            public void releasePduSessionId(int i, int i2) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                try {
                    obtain.writeInterfaceToken(DESCRIPTOR);
                    obtain.writeInt(i);
                    obtain.writeInt(i2);
                    if (this.mRemote.transact(6, obtain, null, 1)) {
                    } else {
                        throw new RemoteException("Method releasePduSessionId is unimplemented.");
                    }
                } finally {
                    obtain.recycle();
                }
            }

            @Override // android.hardware.radio.data.IRadioData
            public void responseAcknowledgement() throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                try {
                    obtain.writeInterfaceToken(DESCRIPTOR);
                    if (this.mRemote.transact(7, obtain, null, 1)) {
                    } else {
                        throw new RemoteException("Method responseAcknowledgement is unimplemented.");
                    }
                } finally {
                    obtain.recycle();
                }
            }

            @Override // android.hardware.radio.data.IRadioData
            public void setDataAllowed(int i, boolean z) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                try {
                    obtain.writeInterfaceToken(DESCRIPTOR);
                    obtain.writeInt(i);
                    obtain.writeBoolean(z);
                    if (this.mRemote.transact(8, obtain, null, 1)) {
                    } else {
                        throw new RemoteException("Method setDataAllowed is unimplemented.");
                    }
                } finally {
                    obtain.recycle();
                }
            }

            @Override // android.hardware.radio.data.IRadioData
            public void setDataProfile(int i, DataProfileInfo[] dataProfileInfoArr) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                try {
                    obtain.writeInterfaceToken(DESCRIPTOR);
                    obtain.writeInt(i);
                    obtain.writeTypedArray(dataProfileInfoArr, 0);
                    if (this.mRemote.transact(9, obtain, null, 1)) {
                    } else {
                        throw new RemoteException("Method setDataProfile is unimplemented.");
                    }
                } finally {
                    obtain.recycle();
                }
            }

            @Override // android.hardware.radio.data.IRadioData
            public void setDataThrottling(int i, byte b, long j) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                try {
                    obtain.writeInterfaceToken(DESCRIPTOR);
                    obtain.writeInt(i);
                    obtain.writeByte(b);
                    obtain.writeLong(j);
                    if (this.mRemote.transact(10, obtain, null, 1)) {
                    } else {
                        throw new RemoteException("Method setDataThrottling is unimplemented.");
                    }
                } finally {
                    obtain.recycle();
                }
            }

            @Override // android.hardware.radio.data.IRadioData
            public void setInitialAttachApn(int i, DataProfileInfo dataProfileInfo) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                try {
                    obtain.writeInterfaceToken(DESCRIPTOR);
                    obtain.writeInt(i);
                    obtain.writeTypedObject(dataProfileInfo, 0);
                    if (this.mRemote.transact(11, obtain, null, 1)) {
                    } else {
                        throw new RemoteException("Method setInitialAttachApn is unimplemented.");
                    }
                } finally {
                    obtain.recycle();
                }
            }

            @Override // android.hardware.radio.data.IRadioData
            public void setResponseFunctions(IRadioDataResponse iRadioDataResponse, IRadioDataIndication iRadioDataIndication) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                try {
                    obtain.writeInterfaceToken(DESCRIPTOR);
                    obtain.writeStrongInterface(iRadioDataResponse);
                    obtain.writeStrongInterface(iRadioDataIndication);
                    if (this.mRemote.transact(12, obtain, null, 1)) {
                    } else {
                        throw new RemoteException("Method setResponseFunctions is unimplemented.");
                    }
                } finally {
                    obtain.recycle();
                }
            }

            @Override // android.hardware.radio.data.IRadioData
            public void setupDataCall(int i, int i2, DataProfileInfo dataProfileInfo, boolean z, int i3, LinkAddress[] linkAddressArr, String[] strArr, int i4, SliceInfo sliceInfo, boolean z2) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                try {
                    obtain.writeInterfaceToken(DESCRIPTOR);
                    obtain.writeInt(i);
                    obtain.writeInt(i2);
                    obtain.writeTypedObject(dataProfileInfo, 0);
                    obtain.writeBoolean(z);
                    obtain.writeInt(i3);
                    obtain.writeTypedArray(linkAddressArr, 0);
                    obtain.writeStringArray(strArr);
                    obtain.writeInt(i4);
                    obtain.writeTypedObject(sliceInfo, 0);
                    obtain.writeBoolean(z2);
                    if (this.mRemote.transact(13, obtain, null, 1)) {
                    } else {
                        throw new RemoteException("Method setupDataCall is unimplemented.");
                    }
                } finally {
                    obtain.recycle();
                }
            }

            @Override // android.hardware.radio.data.IRadioData
            public void startHandover(int i, int i2) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                try {
                    obtain.writeInterfaceToken(DESCRIPTOR);
                    obtain.writeInt(i);
                    obtain.writeInt(i2);
                    if (this.mRemote.transact(14, obtain, null, 1)) {
                    } else {
                        throw new RemoteException("Method startHandover is unimplemented.");
                    }
                } finally {
                    obtain.recycle();
                }
            }

            @Override // android.hardware.radio.data.IRadioData
            public void startKeepalive(int i, KeepaliveRequest keepaliveRequest) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                try {
                    obtain.writeInterfaceToken(DESCRIPTOR);
                    obtain.writeInt(i);
                    obtain.writeTypedObject(keepaliveRequest, 0);
                    if (this.mRemote.transact(15, obtain, null, 1)) {
                    } else {
                        throw new RemoteException("Method startKeepalive is unimplemented.");
                    }
                } finally {
                    obtain.recycle();
                }
            }

            @Override // android.hardware.radio.data.IRadioData
            public void stopKeepalive(int i, int i2) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                try {
                    obtain.writeInterfaceToken(DESCRIPTOR);
                    obtain.writeInt(i);
                    obtain.writeInt(i2);
                    if (this.mRemote.transact(16, obtain, null, 1)) {
                    } else {
                        throw new RemoteException("Method stopKeepalive is unimplemented.");
                    }
                } finally {
                    obtain.recycle();
                }
            }

            @Override // android.hardware.radio.data.IRadioData
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

            @Override // android.hardware.radio.data.IRadioData
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
