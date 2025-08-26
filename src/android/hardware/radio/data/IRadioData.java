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
            IInterface iInterfaceQueryLocalInterface = iBinder.queryLocalInterface(DESCRIPTOR);
            if (iInterfaceQueryLocalInterface != null && (iInterfaceQueryLocalInterface instanceof IRadioData)) {
                return (IRadioData) iInterfaceQueryLocalInterface;
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
                    int i3 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    allocatePduSessionId(i3);
                    return true;
                case 2:
                    int i4 = parcel.readInt();
                    int i5 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    cancelHandover(i4, i5);
                    return true;
                case 3:
                    int i6 = parcel.readInt();
                    int i7 = parcel.readInt();
                    int i8 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    deactivateDataCall(i6, i7, i8);
                    return true;
                case 4:
                    int i9 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    getDataCallList(i9);
                    return true;
                case 5:
                    int i10 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    getSlicingConfig(i10);
                    return true;
                case 6:
                    int i11 = parcel.readInt();
                    int i12 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    releasePduSessionId(i11, i12);
                    return true;
                case 7:
                    responseAcknowledgement();
                    return true;
                case 8:
                    int i13 = parcel.readInt();
                    boolean z = parcel.readBoolean();
                    parcel.enforceNoDataAvail();
                    setDataAllowed(i13, z);
                    return true;
                case 9:
                    int i14 = parcel.readInt();
                    DataProfileInfo[] dataProfileInfoArr = (DataProfileInfo[]) parcel.createTypedArray(DataProfileInfo.CREATOR);
                    parcel.enforceNoDataAvail();
                    setDataProfile(i14, dataProfileInfoArr);
                    return true;
                case 10:
                    int i15 = parcel.readInt();
                    byte b = parcel.readByte();
                    long j = parcel.readLong();
                    parcel.enforceNoDataAvail();
                    setDataThrottling(i15, b, j);
                    return true;
                case 11:
                    int i16 = parcel.readInt();
                    DataProfileInfo dataProfileInfo = (DataProfileInfo) parcel.readTypedObject(DataProfileInfo.CREATOR);
                    parcel.enforceNoDataAvail();
                    setInitialAttachApn(i16, dataProfileInfo);
                    return true;
                case 12:
                    IRadioDataResponse iRadioDataResponseAsInterface = IRadioDataResponse.Stub.asInterface(parcel.readStrongBinder());
                    IRadioDataIndication iRadioDataIndicationAsInterface = IRadioDataIndication.Stub.asInterface(parcel.readStrongBinder());
                    parcel.enforceNoDataAvail();
                    setResponseFunctions(iRadioDataResponseAsInterface, iRadioDataIndicationAsInterface);
                    return true;
                case 13:
                    int i17 = parcel.readInt();
                    int i18 = parcel.readInt();
                    DataProfileInfo dataProfileInfo2 = (DataProfileInfo) parcel.readTypedObject(DataProfileInfo.CREATOR);
                    boolean z2 = parcel.readBoolean();
                    int i19 = parcel.readInt();
                    LinkAddress[] linkAddressArr = (LinkAddress[]) parcel.createTypedArray(LinkAddress.CREATOR);
                    String[] strArrCreateStringArray = parcel.createStringArray();
                    int i20 = parcel.readInt();
                    SliceInfo sliceInfo = (SliceInfo) parcel.readTypedObject(SliceInfo.CREATOR);
                    boolean z3 = parcel.readBoolean();
                    parcel.enforceNoDataAvail();
                    setupDataCall(i17, i18, dataProfileInfo2, z2, i19, linkAddressArr, strArrCreateStringArray, i20, sliceInfo, z3);
                    return true;
                case 14:
                    int i21 = parcel.readInt();
                    int i22 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    startHandover(i21, i22);
                    return true;
                case 15:
                    int i23 = parcel.readInt();
                    KeepaliveRequest keepaliveRequest = (KeepaliveRequest) parcel.readTypedObject(KeepaliveRequest.CREATOR);
                    parcel.enforceNoDataAvail();
                    startKeepalive(i23, keepaliveRequest);
                    return true;
                case 16:
                    int i24 = parcel.readInt();
                    int i25 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    stopKeepalive(i24, i25);
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
                Parcel parcelObtain = Parcel.obtain(asBinder());
                try {
                    parcelObtain.writeInterfaceToken(DESCRIPTOR);
                    parcelObtain.writeInt(i);
                    if (this.mRemote.transact(1, parcelObtain, null, 1)) {
                    } else {
                        throw new RemoteException("Method allocatePduSessionId is unimplemented.");
                    }
                } finally {
                    parcelObtain.recycle();
                }
            }

            @Override // android.hardware.radio.data.IRadioData
            public void cancelHandover(int i, int i2) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                try {
                    parcelObtain.writeInterfaceToken(DESCRIPTOR);
                    parcelObtain.writeInt(i);
                    parcelObtain.writeInt(i2);
                    if (this.mRemote.transact(2, parcelObtain, null, 1)) {
                    } else {
                        throw new RemoteException("Method cancelHandover is unimplemented.");
                    }
                } finally {
                    parcelObtain.recycle();
                }
            }

            @Override // android.hardware.radio.data.IRadioData
            public void deactivateDataCall(int i, int i2, int i3) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                try {
                    parcelObtain.writeInterfaceToken(DESCRIPTOR);
                    parcelObtain.writeInt(i);
                    parcelObtain.writeInt(i2);
                    parcelObtain.writeInt(i3);
                    if (this.mRemote.transact(3, parcelObtain, null, 1)) {
                    } else {
                        throw new RemoteException("Method deactivateDataCall is unimplemented.");
                    }
                } finally {
                    parcelObtain.recycle();
                }
            }

            @Override // android.hardware.radio.data.IRadioData
            public void getDataCallList(int i) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                try {
                    parcelObtain.writeInterfaceToken(DESCRIPTOR);
                    parcelObtain.writeInt(i);
                    if (this.mRemote.transact(4, parcelObtain, null, 1)) {
                    } else {
                        throw new RemoteException("Method getDataCallList is unimplemented.");
                    }
                } finally {
                    parcelObtain.recycle();
                }
            }

            @Override // android.hardware.radio.data.IRadioData
            public void getSlicingConfig(int i) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                try {
                    parcelObtain.writeInterfaceToken(DESCRIPTOR);
                    parcelObtain.writeInt(i);
                    if (this.mRemote.transact(5, parcelObtain, null, 1)) {
                    } else {
                        throw new RemoteException("Method getSlicingConfig is unimplemented.");
                    }
                } finally {
                    parcelObtain.recycle();
                }
            }

            @Override // android.hardware.radio.data.IRadioData
            public void releasePduSessionId(int i, int i2) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                try {
                    parcelObtain.writeInterfaceToken(DESCRIPTOR);
                    parcelObtain.writeInt(i);
                    parcelObtain.writeInt(i2);
                    if (this.mRemote.transact(6, parcelObtain, null, 1)) {
                    } else {
                        throw new RemoteException("Method releasePduSessionId is unimplemented.");
                    }
                } finally {
                    parcelObtain.recycle();
                }
            }

            @Override // android.hardware.radio.data.IRadioData
            public void responseAcknowledgement() throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                try {
                    parcelObtain.writeInterfaceToken(DESCRIPTOR);
                    if (this.mRemote.transact(7, parcelObtain, null, 1)) {
                    } else {
                        throw new RemoteException("Method responseAcknowledgement is unimplemented.");
                    }
                } finally {
                    parcelObtain.recycle();
                }
            }

            @Override // android.hardware.radio.data.IRadioData
            public void setDataAllowed(int i, boolean z) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                try {
                    parcelObtain.writeInterfaceToken(DESCRIPTOR);
                    parcelObtain.writeInt(i);
                    parcelObtain.writeBoolean(z);
                    if (this.mRemote.transact(8, parcelObtain, null, 1)) {
                    } else {
                        throw new RemoteException("Method setDataAllowed is unimplemented.");
                    }
                } finally {
                    parcelObtain.recycle();
                }
            }

            @Override // android.hardware.radio.data.IRadioData
            public void setDataProfile(int i, DataProfileInfo[] dataProfileInfoArr) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                try {
                    parcelObtain.writeInterfaceToken(DESCRIPTOR);
                    parcelObtain.writeInt(i);
                    parcelObtain.writeTypedArray(dataProfileInfoArr, 0);
                    if (this.mRemote.transact(9, parcelObtain, null, 1)) {
                    } else {
                        throw new RemoteException("Method setDataProfile is unimplemented.");
                    }
                } finally {
                    parcelObtain.recycle();
                }
            }

            @Override // android.hardware.radio.data.IRadioData
            public void setDataThrottling(int i, byte b, long j) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                try {
                    parcelObtain.writeInterfaceToken(DESCRIPTOR);
                    parcelObtain.writeInt(i);
                    parcelObtain.writeByte(b);
                    parcelObtain.writeLong(j);
                    if (this.mRemote.transact(10, parcelObtain, null, 1)) {
                    } else {
                        throw new RemoteException("Method setDataThrottling is unimplemented.");
                    }
                } finally {
                    parcelObtain.recycle();
                }
            }

            @Override // android.hardware.radio.data.IRadioData
            public void setInitialAttachApn(int i, DataProfileInfo dataProfileInfo) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                try {
                    parcelObtain.writeInterfaceToken(DESCRIPTOR);
                    parcelObtain.writeInt(i);
                    parcelObtain.writeTypedObject(dataProfileInfo, 0);
                    if (this.mRemote.transact(11, parcelObtain, null, 1)) {
                    } else {
                        throw new RemoteException("Method setInitialAttachApn is unimplemented.");
                    }
                } finally {
                    parcelObtain.recycle();
                }
            }

            @Override // android.hardware.radio.data.IRadioData
            public void setResponseFunctions(IRadioDataResponse iRadioDataResponse, IRadioDataIndication iRadioDataIndication) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                try {
                    parcelObtain.writeInterfaceToken(DESCRIPTOR);
                    parcelObtain.writeStrongInterface(iRadioDataResponse);
                    parcelObtain.writeStrongInterface(iRadioDataIndication);
                    if (this.mRemote.transact(12, parcelObtain, null, 1)) {
                    } else {
                        throw new RemoteException("Method setResponseFunctions is unimplemented.");
                    }
                } finally {
                    parcelObtain.recycle();
                }
            }

            @Override // android.hardware.radio.data.IRadioData
            public void setupDataCall(int i, int i2, DataProfileInfo dataProfileInfo, boolean z, int i3, LinkAddress[] linkAddressArr, String[] strArr, int i4, SliceInfo sliceInfo, boolean z2) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                try {
                    parcelObtain.writeInterfaceToken(DESCRIPTOR);
                    parcelObtain.writeInt(i);
                    parcelObtain.writeInt(i2);
                    parcelObtain.writeTypedObject(dataProfileInfo, 0);
                    parcelObtain.writeBoolean(z);
                    parcelObtain.writeInt(i3);
                    parcelObtain.writeTypedArray(linkAddressArr, 0);
                    parcelObtain.writeStringArray(strArr);
                    parcelObtain.writeInt(i4);
                    parcelObtain.writeTypedObject(sliceInfo, 0);
                    parcelObtain.writeBoolean(z2);
                    if (this.mRemote.transact(13, parcelObtain, null, 1)) {
                    } else {
                        throw new RemoteException("Method setupDataCall is unimplemented.");
                    }
                } finally {
                    parcelObtain.recycle();
                }
            }

            @Override // android.hardware.radio.data.IRadioData
            public void startHandover(int i, int i2) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                try {
                    parcelObtain.writeInterfaceToken(DESCRIPTOR);
                    parcelObtain.writeInt(i);
                    parcelObtain.writeInt(i2);
                    if (this.mRemote.transact(14, parcelObtain, null, 1)) {
                    } else {
                        throw new RemoteException("Method startHandover is unimplemented.");
                    }
                } finally {
                    parcelObtain.recycle();
                }
            }

            @Override // android.hardware.radio.data.IRadioData
            public void startKeepalive(int i, KeepaliveRequest keepaliveRequest) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                try {
                    parcelObtain.writeInterfaceToken(DESCRIPTOR);
                    parcelObtain.writeInt(i);
                    parcelObtain.writeTypedObject(keepaliveRequest, 0);
                    if (this.mRemote.transact(15, parcelObtain, null, 1)) {
                    } else {
                        throw new RemoteException("Method startKeepalive is unimplemented.");
                    }
                } finally {
                    parcelObtain.recycle();
                }
            }

            @Override // android.hardware.radio.data.IRadioData
            public void stopKeepalive(int i, int i2) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                try {
                    parcelObtain.writeInterfaceToken(DESCRIPTOR);
                    parcelObtain.writeInt(i);
                    parcelObtain.writeInt(i2);
                    if (this.mRemote.transact(16, parcelObtain, null, 1)) {
                    } else {
                        throw new RemoteException("Method stopKeepalive is unimplemented.");
                    }
                } finally {
                    parcelObtain.recycle();
                }
            }

            @Override // android.hardware.radio.data.IRadioData
            public int getInterfaceVersion() throws RemoteException {
                if (this.mCachedVersion == -1) {
                    Parcel parcelObtain = Parcel.obtain(asBinder());
                    Parcel parcelObtain2 = Parcel.obtain();
                    try {
                        parcelObtain.writeInterfaceToken(DESCRIPTOR);
                        this.mRemote.transact(16777215, parcelObtain, parcelObtain2, 0);
                        parcelObtain2.readException();
                        this.mCachedVersion = parcelObtain2.readInt();
                    } finally {
                        parcelObtain2.recycle();
                        parcelObtain.recycle();
                    }
                }
                return this.mCachedVersion;
            }

            @Override // android.hardware.radio.data.IRadioData
            public synchronized String getInterfaceHash() throws RemoteException {
                if ("-1".equals(this.mCachedHash)) {
                    Parcel parcelObtain = Parcel.obtain(asBinder());
                    Parcel parcelObtain2 = Parcel.obtain();
                    try {
                        parcelObtain.writeInterfaceToken(DESCRIPTOR);
                        this.mRemote.transact(16777214, parcelObtain, parcelObtain2, 0);
                        parcelObtain2.readException();
                        this.mCachedHash = parcelObtain2.readString();
                        parcelObtain2.recycle();
                        parcelObtain.recycle();
                    } catch (Throwable th) {
                        parcelObtain2.recycle();
                        parcelObtain.recycle();
                        throw th;
                    }
                }
                return this.mCachedHash;
            }
        }
    }
}
