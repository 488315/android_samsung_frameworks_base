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
            IInterface iInterfaceQueryLocalInterface = iBinder.queryLocalInterface(DESCRIPTOR);
            if (iInterfaceQueryLocalInterface != null && (iInterfaceQueryLocalInterface instanceof ISehRadioNetworkIndication)) {
                return (ISehRadioNetworkIndication) iInterfaceQueryLocalInterface;
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
                    int[] iArrCreateIntArray = parcel.createIntArray();
                    parcel.enforceNoDataAvail();
                    acbInfoChanged(i3, iArrCreateIntArray);
                    return true;
                case 2:
                    int i4 = parcel.readInt();
                    SehCallDetails[] sehCallDetailsArr = (SehCallDetails[]) parcel.createTypedArray(SehCallDetails.CREATOR);
                    parcel.enforceNoDataAvail();
                    callDetailsChanged(i4, sehCallDetailsArr);
                    parcel2.writeNoException();
                    return true;
                case 3:
                    int i5 = parcel.readInt();
                    int i6 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    csFallback(i5, i6);
                    return true;
                case 4:
                    int i7 = parcel.readInt();
                    byte b = parcel.readByte();
                    parcel.enforceNoDataAvail();
                    currentNetworkScanIsRequested(i7, b);
                    return true;
                case 5:
                    int i8 = parcel.readInt();
                    SehEriInfo sehEriInfo = (SehEriInfo) parcel.readTypedObject(SehEriInfo.CREATOR);
                    parcel.enforceNoDataAvail();
                    eriInfoReceived(i8, sehEriInfo);
                    parcel2.writeNoException();
                    return true;
                case 6:
                    int i9 = parcel.readInt();
                    String string = parcel.readString();
                    parcel.enforceNoDataAvail();
                    execute(i9, string);
                    return true;
                case 7:
                    int i10 = parcel.readInt();
                    SehExtendedRegStateResult sehExtendedRegStateResult = (SehExtendedRegStateResult) parcel.readTypedObject(SehExtendedRegStateResult.CREATOR);
                    parcel.enforceNoDataAvail();
                    extendedRegistrationState(i10, sehExtendedRegStateResult);
                    parcel2.writeNoException();
                    return true;
                case 8:
                    int i11 = parcel.readInt();
                    int[] iArrCreateIntArray2 = parcel.createIntArray();
                    parcel.enforceNoDataAvail();
                    imsPreferenceChanged(i11, iArrCreateIntArray2);
                    return true;
                case 9:
                    int i12 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    needTurnOnRadioIndication(i12);
                    return true;
                case 10:
                    int i13 = parcel.readInt();
                    int i14 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    nrBearerAllocationChanged(i13, i14);
                    return true;
                case 11:
                    int i15 = parcel.readInt();
                    int i16 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    nrIconTypeChanged(i15, i16);
                    return true;
                case 12:
                    int i17 = parcel.readInt();
                    int i18 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    nrNetworkTypeAdded(i17, i18);
                    return true;
                case 13:
                    int i19 = parcel.readInt();
                    byte[] bArrCreateByteArray = parcel.createByteArray();
                    parcel.enforceNoDataAvail();
                    roamingNetworkScanIsRequested(i19, bArrCreateByteArray);
                    return true;
                case 14:
                    int i20 = parcel.readInt();
                    SehSignalBar sehSignalBar = (SehSignalBar) parcel.readTypedObject(SehSignalBar.CREATOR);
                    parcel.enforceNoDataAvail();
                    signalLevelInfoChanged(i20, sehSignalBar);
                    parcel2.writeNoException();
                    return true;
                case 15:
                    int i21 = parcel.readInt();
                    SehVendorConfiguration[] sehVendorConfigurationArr = (SehVendorConfiguration[]) parcel.createTypedArray(SehVendorConfiguration.CREATOR);
                    parcel.enforceNoDataAvail();
                    vendorConfigurationChanged(i21, sehVendorConfigurationArr);
                    parcel2.writeNoException();
                    return true;
                case 16:
                    int i22 = parcel.readInt();
                    int i23 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    cpaiModelUpdateNoti(i22, i23);
                    parcel2.writeNoException();
                    return true;
                case 17:
                    int i24 = parcel.readInt();
                    int i25 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    cpaiFeatureInfoNoti(i24, i25);
                    parcel2.writeNoException();
                    return true;
                case 18:
                    int i26 = parcel.readInt();
                    int i27 = parcel.readInt();
                    byte[] bArrCreateByteArray2 = parcel.createByteArray();
                    parcel.enforceNoDataAvail();
                    cpaiDataGatheringNoti(i26, i27, bArrCreateByteArray2);
                    parcel2.writeNoException();
                    return true;
                case 19:
                    int i28 = parcel.readInt();
                    int i29 = parcel.readInt();
                    int i30 = parcel.readInt();
                    byte[] bArrCreateByteArray3 = parcel.createByteArray();
                    parcel.enforceNoDataAvail();
                    cpaiDevAppMessageNoti(i28, i29, i30, bArrCreateByteArray3);
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
                Parcel parcelObtain = Parcel.obtain(asBinder());
                try {
                    parcelObtain.writeInterfaceToken(DESCRIPTOR);
                    parcelObtain.writeInt(i);
                    parcelObtain.writeIntArray(iArr);
                    if (this.mRemote.transact(1, parcelObtain, null, 1)) {
                    } else {
                        throw new RemoteException("Method acbInfoChanged is unimplemented.");
                    }
                } finally {
                    parcelObtain.recycle();
                }
            }

            @Override // vendor.samsung.hardware.radio.network.ISehRadioNetworkIndication
            public void callDetailsChanged(int i, SehCallDetails[] sehCallDetailsArr) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(DESCRIPTOR);
                    parcelObtain.writeInt(i);
                    parcelObtain.writeTypedArray(sehCallDetailsArr, 0);
                    if (!this.mRemote.transact(2, parcelObtain, parcelObtain2, 0)) {
                        throw new RemoteException("Method callDetailsChanged is unimplemented.");
                    }
                    parcelObtain2.readException();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // vendor.samsung.hardware.radio.network.ISehRadioNetworkIndication
            public void csFallback(int i, int i2) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                try {
                    parcelObtain.writeInterfaceToken(DESCRIPTOR);
                    parcelObtain.writeInt(i);
                    parcelObtain.writeInt(i2);
                    if (this.mRemote.transact(3, parcelObtain, null, 1)) {
                    } else {
                        throw new RemoteException("Method csFallback is unimplemented.");
                    }
                } finally {
                    parcelObtain.recycle();
                }
            }

            @Override // vendor.samsung.hardware.radio.network.ISehRadioNetworkIndication
            public void currentNetworkScanIsRequested(int i, byte b) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                try {
                    parcelObtain.writeInterfaceToken(DESCRIPTOR);
                    parcelObtain.writeInt(i);
                    parcelObtain.writeByte(b);
                    if (this.mRemote.transact(4, parcelObtain, null, 1)) {
                    } else {
                        throw new RemoteException("Method currentNetworkScanIsRequested is unimplemented.");
                    }
                } finally {
                    parcelObtain.recycle();
                }
            }

            @Override // vendor.samsung.hardware.radio.network.ISehRadioNetworkIndication
            public void eriInfoReceived(int i, SehEriInfo sehEriInfo) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(DESCRIPTOR);
                    parcelObtain.writeInt(i);
                    parcelObtain.writeTypedObject(sehEriInfo, 0);
                    if (!this.mRemote.transact(5, parcelObtain, parcelObtain2, 0)) {
                        throw new RemoteException("Method eriInfoReceived is unimplemented.");
                    }
                    parcelObtain2.readException();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // vendor.samsung.hardware.radio.network.ISehRadioNetworkIndication
            public void execute(int i, String str) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                try {
                    parcelObtain.writeInterfaceToken(DESCRIPTOR);
                    parcelObtain.writeInt(i);
                    parcelObtain.writeString(str);
                    if (this.mRemote.transact(6, parcelObtain, null, 1)) {
                    } else {
                        throw new RemoteException("Method execute is unimplemented.");
                    }
                } finally {
                    parcelObtain.recycle();
                }
            }

            @Override // vendor.samsung.hardware.radio.network.ISehRadioNetworkIndication
            public void extendedRegistrationState(int i, SehExtendedRegStateResult sehExtendedRegStateResult) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(DESCRIPTOR);
                    parcelObtain.writeInt(i);
                    parcelObtain.writeTypedObject(sehExtendedRegStateResult, 0);
                    if (!this.mRemote.transact(7, parcelObtain, parcelObtain2, 0)) {
                        throw new RemoteException("Method extendedRegistrationState is unimplemented.");
                    }
                    parcelObtain2.readException();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // vendor.samsung.hardware.radio.network.ISehRadioNetworkIndication
            public void imsPreferenceChanged(int i, int[] iArr) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                try {
                    parcelObtain.writeInterfaceToken(DESCRIPTOR);
                    parcelObtain.writeInt(i);
                    parcelObtain.writeIntArray(iArr);
                    if (this.mRemote.transact(8, parcelObtain, null, 1)) {
                    } else {
                        throw new RemoteException("Method imsPreferenceChanged is unimplemented.");
                    }
                } finally {
                    parcelObtain.recycle();
                }
            }

            @Override // vendor.samsung.hardware.radio.network.ISehRadioNetworkIndication
            public void needTurnOnRadioIndication(int i) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                try {
                    parcelObtain.writeInterfaceToken(DESCRIPTOR);
                    parcelObtain.writeInt(i);
                    if (this.mRemote.transact(9, parcelObtain, null, 1)) {
                    } else {
                        throw new RemoteException("Method needTurnOnRadioIndication is unimplemented.");
                    }
                } finally {
                    parcelObtain.recycle();
                }
            }

            @Override // vendor.samsung.hardware.radio.network.ISehRadioNetworkIndication
            public void nrBearerAllocationChanged(int i, int i2) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                try {
                    parcelObtain.writeInterfaceToken(DESCRIPTOR);
                    parcelObtain.writeInt(i);
                    parcelObtain.writeInt(i2);
                    if (this.mRemote.transact(10, parcelObtain, null, 1)) {
                    } else {
                        throw new RemoteException("Method nrBearerAllocationChanged is unimplemented.");
                    }
                } finally {
                    parcelObtain.recycle();
                }
            }

            @Override // vendor.samsung.hardware.radio.network.ISehRadioNetworkIndication
            public void nrIconTypeChanged(int i, int i2) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                try {
                    parcelObtain.writeInterfaceToken(DESCRIPTOR);
                    parcelObtain.writeInt(i);
                    parcelObtain.writeInt(i2);
                    if (this.mRemote.transact(11, parcelObtain, null, 1)) {
                    } else {
                        throw new RemoteException("Method nrIconTypeChanged is unimplemented.");
                    }
                } finally {
                    parcelObtain.recycle();
                }
            }

            @Override // vendor.samsung.hardware.radio.network.ISehRadioNetworkIndication
            public void nrNetworkTypeAdded(int i, int i2) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                try {
                    parcelObtain.writeInterfaceToken(DESCRIPTOR);
                    parcelObtain.writeInt(i);
                    parcelObtain.writeInt(i2);
                    if (this.mRemote.transact(12, parcelObtain, null, 1)) {
                    } else {
                        throw new RemoteException("Method nrNetworkTypeAdded is unimplemented.");
                    }
                } finally {
                    parcelObtain.recycle();
                }
            }

            @Override // vendor.samsung.hardware.radio.network.ISehRadioNetworkIndication
            public void roamingNetworkScanIsRequested(int i, byte[] bArr) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                try {
                    parcelObtain.writeInterfaceToken(DESCRIPTOR);
                    parcelObtain.writeInt(i);
                    parcelObtain.writeByteArray(bArr);
                    if (this.mRemote.transact(13, parcelObtain, null, 1)) {
                    } else {
                        throw new RemoteException("Method roamingNetworkScanIsRequested is unimplemented.");
                    }
                } finally {
                    parcelObtain.recycle();
                }
            }

            @Override // vendor.samsung.hardware.radio.network.ISehRadioNetworkIndication
            public void signalLevelInfoChanged(int i, SehSignalBar sehSignalBar) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(DESCRIPTOR);
                    parcelObtain.writeInt(i);
                    parcelObtain.writeTypedObject(sehSignalBar, 0);
                    if (!this.mRemote.transact(14, parcelObtain, parcelObtain2, 0)) {
                        throw new RemoteException("Method signalLevelInfoChanged is unimplemented.");
                    }
                    parcelObtain2.readException();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // vendor.samsung.hardware.radio.network.ISehRadioNetworkIndication
            public void vendorConfigurationChanged(int i, SehVendorConfiguration[] sehVendorConfigurationArr) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(DESCRIPTOR);
                    parcelObtain.writeInt(i);
                    parcelObtain.writeTypedArray(sehVendorConfigurationArr, 0);
                    if (!this.mRemote.transact(15, parcelObtain, parcelObtain2, 0)) {
                        throw new RemoteException("Method vendorConfigurationChanged is unimplemented.");
                    }
                    parcelObtain2.readException();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // vendor.samsung.hardware.radio.network.ISehRadioNetworkIndication
            public void cpaiModelUpdateNoti(int i, int i2) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(DESCRIPTOR);
                    parcelObtain.writeInt(i);
                    parcelObtain.writeInt(i2);
                    if (!this.mRemote.transact(16, parcelObtain, parcelObtain2, 0)) {
                        throw new RemoteException("Method cpaiModelUpdateNoti is unimplemented.");
                    }
                    parcelObtain2.readException();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // vendor.samsung.hardware.radio.network.ISehRadioNetworkIndication
            public void cpaiFeatureInfoNoti(int i, int i2) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(DESCRIPTOR);
                    parcelObtain.writeInt(i);
                    parcelObtain.writeInt(i2);
                    if (!this.mRemote.transact(17, parcelObtain, parcelObtain2, 0)) {
                        throw new RemoteException("Method cpaiFeatureInfoNoti is unimplemented.");
                    }
                    parcelObtain2.readException();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // vendor.samsung.hardware.radio.network.ISehRadioNetworkIndication
            public void cpaiDataGatheringNoti(int i, int i2, byte[] bArr) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(DESCRIPTOR);
                    parcelObtain.writeInt(i);
                    parcelObtain.writeInt(i2);
                    parcelObtain.writeByteArray(bArr);
                    if (!this.mRemote.transact(18, parcelObtain, parcelObtain2, 0)) {
                        throw new RemoteException("Method cpaiDataGatheringNoti is unimplemented.");
                    }
                    parcelObtain2.readException();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // vendor.samsung.hardware.radio.network.ISehRadioNetworkIndication
            public void cpaiDevAppMessageNoti(int i, int i2, int i3, byte[] bArr) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(DESCRIPTOR);
                    parcelObtain.writeInt(i);
                    parcelObtain.writeInt(i2);
                    parcelObtain.writeInt(i3);
                    parcelObtain.writeByteArray(bArr);
                    if (!this.mRemote.transact(19, parcelObtain, parcelObtain2, 0)) {
                        throw new RemoteException("Method cpaiDevAppMessageNoti is unimplemented.");
                    }
                    parcelObtain2.readException();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // vendor.samsung.hardware.radio.network.ISehRadioNetworkIndication
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

            @Override // vendor.samsung.hardware.radio.network.ISehRadioNetworkIndication
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
