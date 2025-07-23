package vendor.samsung.hardware.radio.network;

import android.os.Binder;
import android.os.IBinder;
import android.os.IInterface;
import android.os.Parcel;
import android.os.RemoteException;

/* loaded from: classes6.dex */
public interface ISehRadioNetworkIndication extends IInterface {
    public static final String DESCRIPTOR = "vendor$samsung$hardware$radio$network$ISehRadioNetworkIndication".replace('$', '.');
    public static final String HASH = "0fc15a1faf82e2c79f263a7fd6e8b5ffba3644f3";
    public static final int VERSION = 1;

    void acbInfoChanged(int i, int[] iArr) throws RemoteException;

    void callDetailsChanged(int i, SehCallDetails[] sehCallDetailsArr) throws RemoteException;

    void cpaiDataGatheringNoti(int i, int i2, byte[] bArr) throws RemoteException;

    void cpaiDevAppMessageNoti(int i, int i2, int i3, byte[] bArr) throws RemoteException;

    void cpaiFeatureInfoNoti(int i, int i2) throws RemoteException;

    void cpaiModelUpdateNoti(int i, int i2) throws RemoteException;

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
        public void acbInfoChanged(int i, int[] iArr) throws RemoteException {
        }

        @Override // android.os.IInterface
        public IBinder asBinder() {
            return null;
        }

        @Override // vendor.samsung.hardware.radio.network.ISehRadioNetworkIndication
        public void callDetailsChanged(int i, SehCallDetails[] sehCallDetailsArr) throws RemoteException {
        }

        @Override // vendor.samsung.hardware.radio.network.ISehRadioNetworkIndication
        public void cpaiDataGatheringNoti(int i, int i2, byte[] bArr) throws RemoteException {
        }

        @Override // vendor.samsung.hardware.radio.network.ISehRadioNetworkIndication
        public void cpaiDevAppMessageNoti(int i, int i2, int i3, byte[] bArr) throws RemoteException {
        }

        @Override // vendor.samsung.hardware.radio.network.ISehRadioNetworkIndication
        public void cpaiFeatureInfoNoti(int i, int i2) throws RemoteException {
        }

        @Override // vendor.samsung.hardware.radio.network.ISehRadioNetworkIndication
        public void cpaiModelUpdateNoti(int i, int i2) throws RemoteException {
        }

        @Override // vendor.samsung.hardware.radio.network.ISehRadioNetworkIndication
        public void csFallback(int i, int i2) throws RemoteException {
        }

        @Override // vendor.samsung.hardware.radio.network.ISehRadioNetworkIndication
        public void currentNetworkScanIsRequested(int i, byte b) throws RemoteException {
        }

        @Override // vendor.samsung.hardware.radio.network.ISehRadioNetworkIndication
        public void eriInfoReceived(int i, SehEriInfo sehEriInfo) throws RemoteException {
        }

        @Override // vendor.samsung.hardware.radio.network.ISehRadioNetworkIndication
        public void execute(int i, String str) throws RemoteException {
        }

        @Override // vendor.samsung.hardware.radio.network.ISehRadioNetworkIndication
        public void extendedRegistrationState(int i, SehExtendedRegStateResult sehExtendedRegStateResult) throws RemoteException {
        }

        @Override // vendor.samsung.hardware.radio.network.ISehRadioNetworkIndication
        public int getInterfaceVersion() {
            return 0;
        }

        @Override // vendor.samsung.hardware.radio.network.ISehRadioNetworkIndication
        public void imsPreferenceChanged(int i, int[] iArr) throws RemoteException {
        }

        @Override // vendor.samsung.hardware.radio.network.ISehRadioNetworkIndication
        public void needTurnOnRadioIndication(int i) throws RemoteException {
        }

        @Override // vendor.samsung.hardware.radio.network.ISehRadioNetworkIndication
        public void nrBearerAllocationChanged(int i, int i2) throws RemoteException {
        }

        @Override // vendor.samsung.hardware.radio.network.ISehRadioNetworkIndication
        public void nrIconTypeChanged(int i, int i2) throws RemoteException {
        }

        @Override // vendor.samsung.hardware.radio.network.ISehRadioNetworkIndication
        public void nrNetworkTypeAdded(int i, int i2) throws RemoteException {
        }

        @Override // vendor.samsung.hardware.radio.network.ISehRadioNetworkIndication
        public void roamingNetworkScanIsRequested(int i, byte[] bArr) throws RemoteException {
        }

        @Override // vendor.samsung.hardware.radio.network.ISehRadioNetworkIndication
        public void signalLevelInfoChanged(int i, SehSignalBar sehSignalBar) throws RemoteException {
        }

        @Override // vendor.samsung.hardware.radio.network.ISehRadioNetworkIndication
        public void vendorConfigurationChanged(int i, SehVendorConfiguration[] sehVendorConfigurationArr) throws RemoteException {
        }

        @Override // vendor.samsung.hardware.radio.network.ISehRadioNetworkIndication
        public String getInterfaceHash() {
            return "";
        }
    }

    public static abstract class Stub extends Binder implements ISehRadioNetworkIndication {
        static final int TRANSACTION_acbInfoChanged = 1;
        static final int TRANSACTION_callDetailsChanged = 2;
        static final int TRANSACTION_cpaiDataGatheringNoti = 18;
        static final int TRANSACTION_cpaiDevAppMessageNoti = 19;
        static final int TRANSACTION_cpaiFeatureInfoNoti = 17;
        static final int TRANSACTION_cpaiModelUpdateNoti = 16;
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

        @Override // android.os.IInterface
        public IBinder asBinder() {
            return this;
        }

        public Stub() {
            markVintfStability();
            attachInterface(this, DESCRIPTOR);
        }

        public static ISehRadioNetworkIndication asInterface(IBinder iBinder) {
            if (iBinder == null) {
                return null;
            }
            IInterface queryLocalInterface = iBinder.queryLocalInterface(DESCRIPTOR);
            if (queryLocalInterface != null && (queryLocalInterface instanceof ISehRadioNetworkIndication)) {
                return (ISehRadioNetworkIndication) queryLocalInterface;
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
                    int[] createIntArray = parcel.createIntArray();
                    parcel.enforceNoDataAvail();
                    acbInfoChanged(readInt, createIntArray);
                    return true;
                case 2:
                    int readInt2 = parcel.readInt();
                    SehCallDetails[] sehCallDetailsArr = (SehCallDetails[]) parcel.createTypedArray(SehCallDetails.CREATOR);
                    parcel.enforceNoDataAvail();
                    callDetailsChanged(readInt2, sehCallDetailsArr);
                    parcel2.writeNoException();
                    return true;
                case 3:
                    int readInt3 = parcel.readInt();
                    int readInt4 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    csFallback(readInt3, readInt4);
                    return true;
                case 4:
                    int readInt5 = parcel.readInt();
                    byte readByte = parcel.readByte();
                    parcel.enforceNoDataAvail();
                    currentNetworkScanIsRequested(readInt5, readByte);
                    return true;
                case 5:
                    int readInt6 = parcel.readInt();
                    SehEriInfo sehEriInfo = (SehEriInfo) parcel.readTypedObject(SehEriInfo.CREATOR);
                    parcel.enforceNoDataAvail();
                    eriInfoReceived(readInt6, sehEriInfo);
                    parcel2.writeNoException();
                    return true;
                case 6:
                    int readInt7 = parcel.readInt();
                    String readString = parcel.readString();
                    parcel.enforceNoDataAvail();
                    execute(readInt7, readString);
                    return true;
                case 7:
                    int readInt8 = parcel.readInt();
                    SehExtendedRegStateResult sehExtendedRegStateResult = (SehExtendedRegStateResult) parcel.readTypedObject(SehExtendedRegStateResult.CREATOR);
                    parcel.enforceNoDataAvail();
                    extendedRegistrationState(readInt8, sehExtendedRegStateResult);
                    parcel2.writeNoException();
                    return true;
                case 8:
                    int readInt9 = parcel.readInt();
                    int[] createIntArray2 = parcel.createIntArray();
                    parcel.enforceNoDataAvail();
                    imsPreferenceChanged(readInt9, createIntArray2);
                    return true;
                case 9:
                    int readInt10 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    needTurnOnRadioIndication(readInt10);
                    return true;
                case 10:
                    int readInt11 = parcel.readInt();
                    int readInt12 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    nrBearerAllocationChanged(readInt11, readInt12);
                    return true;
                case 11:
                    int readInt13 = parcel.readInt();
                    int readInt14 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    nrIconTypeChanged(readInt13, readInt14);
                    return true;
                case 12:
                    int readInt15 = parcel.readInt();
                    int readInt16 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    nrNetworkTypeAdded(readInt15, readInt16);
                    return true;
                case 13:
                    int readInt17 = parcel.readInt();
                    byte[] createByteArray = parcel.createByteArray();
                    parcel.enforceNoDataAvail();
                    roamingNetworkScanIsRequested(readInt17, createByteArray);
                    return true;
                case 14:
                    int readInt18 = parcel.readInt();
                    SehSignalBar sehSignalBar = (SehSignalBar) parcel.readTypedObject(SehSignalBar.CREATOR);
                    parcel.enforceNoDataAvail();
                    signalLevelInfoChanged(readInt18, sehSignalBar);
                    parcel2.writeNoException();
                    return true;
                case 15:
                    int readInt19 = parcel.readInt();
                    SehVendorConfiguration[] sehVendorConfigurationArr = (SehVendorConfiguration[]) parcel.createTypedArray(SehVendorConfiguration.CREATOR);
                    parcel.enforceNoDataAvail();
                    vendorConfigurationChanged(readInt19, sehVendorConfigurationArr);
                    parcel2.writeNoException();
                    return true;
                case 16:
                    int readInt20 = parcel.readInt();
                    int readInt21 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    cpaiModelUpdateNoti(readInt20, readInt21);
                    parcel2.writeNoException();
                    return true;
                case 17:
                    int readInt22 = parcel.readInt();
                    int readInt23 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    cpaiFeatureInfoNoti(readInt22, readInt23);
                    parcel2.writeNoException();
                    return true;
                case 18:
                    int readInt24 = parcel.readInt();
                    int readInt25 = parcel.readInt();
                    byte[] createByteArray2 = parcel.createByteArray();
                    parcel.enforceNoDataAvail();
                    cpaiDataGatheringNoti(readInt24, readInt25, createByteArray2);
                    parcel2.writeNoException();
                    return true;
                case 19:
                    int readInt26 = parcel.readInt();
                    int readInt27 = parcel.readInt();
                    int readInt28 = parcel.readInt();
                    byte[] createByteArray3 = parcel.createByteArray();
                    parcel.enforceNoDataAvail();
                    cpaiDevAppMessageNoti(readInt26, readInt27, readInt28, createByteArray3);
                    parcel2.writeNoException();
                    return true;
                default:
                    return super.onTransact(i, parcel, parcel2, i2);
            }
        }

        private static class Proxy implements ISehRadioNetworkIndication {
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

            @Override // vendor.samsung.hardware.radio.network.ISehRadioNetworkIndication
            public void acbInfoChanged(int i, int[] iArr) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                try {
                    obtain.writeInterfaceToken(DESCRIPTOR);
                    obtain.writeInt(i);
                    obtain.writeIntArray(iArr);
                    if (this.mRemote.transact(1, obtain, null, 1)) {
                    } else {
                        throw new RemoteException("Method acbInfoChanged is unimplemented.");
                    }
                } finally {
                    obtain.recycle();
                }
            }

            @Override // vendor.samsung.hardware.radio.network.ISehRadioNetworkIndication
            public void callDetailsChanged(int i, SehCallDetails[] sehCallDetailsArr) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(DESCRIPTOR);
                    obtain.writeInt(i);
                    obtain.writeTypedArray(sehCallDetailsArr, 0);
                    if (!this.mRemote.transact(2, obtain, obtain2, 0)) {
                        throw new RemoteException("Method callDetailsChanged is unimplemented.");
                    }
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // vendor.samsung.hardware.radio.network.ISehRadioNetworkIndication
            public void csFallback(int i, int i2) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                try {
                    obtain.writeInterfaceToken(DESCRIPTOR);
                    obtain.writeInt(i);
                    obtain.writeInt(i2);
                    if (this.mRemote.transact(3, obtain, null, 1)) {
                    } else {
                        throw new RemoteException("Method csFallback is unimplemented.");
                    }
                } finally {
                    obtain.recycle();
                }
            }

            @Override // vendor.samsung.hardware.radio.network.ISehRadioNetworkIndication
            public void currentNetworkScanIsRequested(int i, byte b) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                try {
                    obtain.writeInterfaceToken(DESCRIPTOR);
                    obtain.writeInt(i);
                    obtain.writeByte(b);
                    if (this.mRemote.transact(4, obtain, null, 1)) {
                    } else {
                        throw new RemoteException("Method currentNetworkScanIsRequested is unimplemented.");
                    }
                } finally {
                    obtain.recycle();
                }
            }

            @Override // vendor.samsung.hardware.radio.network.ISehRadioNetworkIndication
            public void eriInfoReceived(int i, SehEriInfo sehEriInfo) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(DESCRIPTOR);
                    obtain.writeInt(i);
                    obtain.writeTypedObject(sehEriInfo, 0);
                    if (!this.mRemote.transact(5, obtain, obtain2, 0)) {
                        throw new RemoteException("Method eriInfoReceived is unimplemented.");
                    }
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // vendor.samsung.hardware.radio.network.ISehRadioNetworkIndication
            public void execute(int i, String str) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                try {
                    obtain.writeInterfaceToken(DESCRIPTOR);
                    obtain.writeInt(i);
                    obtain.writeString(str);
                    if (this.mRemote.transact(6, obtain, null, 1)) {
                    } else {
                        throw new RemoteException("Method execute is unimplemented.");
                    }
                } finally {
                    obtain.recycle();
                }
            }

            @Override // vendor.samsung.hardware.radio.network.ISehRadioNetworkIndication
            public void extendedRegistrationState(int i, SehExtendedRegStateResult sehExtendedRegStateResult) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(DESCRIPTOR);
                    obtain.writeInt(i);
                    obtain.writeTypedObject(sehExtendedRegStateResult, 0);
                    if (!this.mRemote.transact(7, obtain, obtain2, 0)) {
                        throw new RemoteException("Method extendedRegistrationState is unimplemented.");
                    }
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // vendor.samsung.hardware.radio.network.ISehRadioNetworkIndication
            public void imsPreferenceChanged(int i, int[] iArr) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                try {
                    obtain.writeInterfaceToken(DESCRIPTOR);
                    obtain.writeInt(i);
                    obtain.writeIntArray(iArr);
                    if (this.mRemote.transact(8, obtain, null, 1)) {
                    } else {
                        throw new RemoteException("Method imsPreferenceChanged is unimplemented.");
                    }
                } finally {
                    obtain.recycle();
                }
            }

            @Override // vendor.samsung.hardware.radio.network.ISehRadioNetworkIndication
            public void needTurnOnRadioIndication(int i) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                try {
                    obtain.writeInterfaceToken(DESCRIPTOR);
                    obtain.writeInt(i);
                    if (this.mRemote.transact(9, obtain, null, 1)) {
                    } else {
                        throw new RemoteException("Method needTurnOnRadioIndication is unimplemented.");
                    }
                } finally {
                    obtain.recycle();
                }
            }

            @Override // vendor.samsung.hardware.radio.network.ISehRadioNetworkIndication
            public void nrBearerAllocationChanged(int i, int i2) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                try {
                    obtain.writeInterfaceToken(DESCRIPTOR);
                    obtain.writeInt(i);
                    obtain.writeInt(i2);
                    if (this.mRemote.transact(10, obtain, null, 1)) {
                    } else {
                        throw new RemoteException("Method nrBearerAllocationChanged is unimplemented.");
                    }
                } finally {
                    obtain.recycle();
                }
            }

            @Override // vendor.samsung.hardware.radio.network.ISehRadioNetworkIndication
            public void nrIconTypeChanged(int i, int i2) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                try {
                    obtain.writeInterfaceToken(DESCRIPTOR);
                    obtain.writeInt(i);
                    obtain.writeInt(i2);
                    if (this.mRemote.transact(11, obtain, null, 1)) {
                    } else {
                        throw new RemoteException("Method nrIconTypeChanged is unimplemented.");
                    }
                } finally {
                    obtain.recycle();
                }
            }

            @Override // vendor.samsung.hardware.radio.network.ISehRadioNetworkIndication
            public void nrNetworkTypeAdded(int i, int i2) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                try {
                    obtain.writeInterfaceToken(DESCRIPTOR);
                    obtain.writeInt(i);
                    obtain.writeInt(i2);
                    if (this.mRemote.transact(12, obtain, null, 1)) {
                    } else {
                        throw new RemoteException("Method nrNetworkTypeAdded is unimplemented.");
                    }
                } finally {
                    obtain.recycle();
                }
            }

            @Override // vendor.samsung.hardware.radio.network.ISehRadioNetworkIndication
            public void roamingNetworkScanIsRequested(int i, byte[] bArr) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                try {
                    obtain.writeInterfaceToken(DESCRIPTOR);
                    obtain.writeInt(i);
                    obtain.writeByteArray(bArr);
                    if (this.mRemote.transact(13, obtain, null, 1)) {
                    } else {
                        throw new RemoteException("Method roamingNetworkScanIsRequested is unimplemented.");
                    }
                } finally {
                    obtain.recycle();
                }
            }

            @Override // vendor.samsung.hardware.radio.network.ISehRadioNetworkIndication
            public void signalLevelInfoChanged(int i, SehSignalBar sehSignalBar) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(DESCRIPTOR);
                    obtain.writeInt(i);
                    obtain.writeTypedObject(sehSignalBar, 0);
                    if (!this.mRemote.transact(14, obtain, obtain2, 0)) {
                        throw new RemoteException("Method signalLevelInfoChanged is unimplemented.");
                    }
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // vendor.samsung.hardware.radio.network.ISehRadioNetworkIndication
            public void vendorConfigurationChanged(int i, SehVendorConfiguration[] sehVendorConfigurationArr) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(DESCRIPTOR);
                    obtain.writeInt(i);
                    obtain.writeTypedArray(sehVendorConfigurationArr, 0);
                    if (!this.mRemote.transact(15, obtain, obtain2, 0)) {
                        throw new RemoteException("Method vendorConfigurationChanged is unimplemented.");
                    }
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // vendor.samsung.hardware.radio.network.ISehRadioNetworkIndication
            public void cpaiModelUpdateNoti(int i, int i2) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(DESCRIPTOR);
                    obtain.writeInt(i);
                    obtain.writeInt(i2);
                    if (!this.mRemote.transact(16, obtain, obtain2, 0)) {
                        throw new RemoteException("Method cpaiModelUpdateNoti is unimplemented.");
                    }
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // vendor.samsung.hardware.radio.network.ISehRadioNetworkIndication
            public void cpaiFeatureInfoNoti(int i, int i2) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(DESCRIPTOR);
                    obtain.writeInt(i);
                    obtain.writeInt(i2);
                    if (!this.mRemote.transact(17, obtain, obtain2, 0)) {
                        throw new RemoteException("Method cpaiFeatureInfoNoti is unimplemented.");
                    }
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // vendor.samsung.hardware.radio.network.ISehRadioNetworkIndication
            public void cpaiDataGatheringNoti(int i, int i2, byte[] bArr) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(DESCRIPTOR);
                    obtain.writeInt(i);
                    obtain.writeInt(i2);
                    obtain.writeByteArray(bArr);
                    if (!this.mRemote.transact(18, obtain, obtain2, 0)) {
                        throw new RemoteException("Method cpaiDataGatheringNoti is unimplemented.");
                    }
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // vendor.samsung.hardware.radio.network.ISehRadioNetworkIndication
            public void cpaiDevAppMessageNoti(int i, int i2, int i3, byte[] bArr) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(DESCRIPTOR);
                    obtain.writeInt(i);
                    obtain.writeInt(i2);
                    obtain.writeInt(i3);
                    obtain.writeByteArray(bArr);
                    if (!this.mRemote.transact(19, obtain, obtain2, 0)) {
                        throw new RemoteException("Method cpaiDevAppMessageNoti is unimplemented.");
                    }
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // vendor.samsung.hardware.radio.network.ISehRadioNetworkIndication
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

            @Override // vendor.samsung.hardware.radio.network.ISehRadioNetworkIndication
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
