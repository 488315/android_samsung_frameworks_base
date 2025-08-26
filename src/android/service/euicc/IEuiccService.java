package android.service.euicc;

import android.os.Binder;
import android.os.Bundle;
import android.os.IBinder;
import android.os.IInterface;
import android.os.Parcel;
import android.os.RemoteException;
import android.service.euicc.IDeleteSubscriptionCallback;
import android.service.euicc.IDownloadSubscriptionCallback;
import android.service.euicc.IEraseSubscriptionsCallback;
import android.service.euicc.IEuiccServiceDumpResultCallback;
import android.service.euicc.IGetAvailableMemoryInBytesCallback;
import android.service.euicc.IGetDefaultDownloadableSubscriptionListCallback;
import android.service.euicc.IGetDownloadableSubscriptionMetadataCallback;
import android.service.euicc.IGetEidCallback;
import android.service.euicc.IGetEuiccInfoCallback;
import android.service.euicc.IGetEuiccProfileInfoListCallback;
import android.service.euicc.IGetOtaStatusCallback;
import android.service.euicc.IOtaStatusChangedCallback;
import android.service.euicc.IRetainSubscriptionsForFactoryResetCallback;
import android.service.euicc.ISwitchToSubscriptionCallback;
import android.service.euicc.IUpdateSubscriptionNicknameCallback;
import android.telephony.euicc.DownloadableSubscription;

/* loaded from: classes3.dex */
public interface IEuiccService extends IInterface {

    public static class Default implements IEuiccService {
        @Override // android.os.IInterface
        public IBinder asBinder() {
            return null;
        }

        @Override // android.service.euicc.IEuiccService
        public void deleteSubscription(int i, String str, IDeleteSubscriptionCallback iDeleteSubscriptionCallback) throws RemoteException {
        }

        @Override // android.service.euicc.IEuiccService
        public void downloadSubscription(int i, int i2, DownloadableSubscription downloadableSubscription, boolean z, boolean z2, Bundle bundle, IDownloadSubscriptionCallback iDownloadSubscriptionCallback) throws RemoteException {
        }

        @Override // android.service.euicc.IEuiccService
        public void dump(IEuiccServiceDumpResultCallback iEuiccServiceDumpResultCallback) throws RemoteException {
        }

        @Override // android.service.euicc.IEuiccService
        public void eraseSubscriptions(int i, IEraseSubscriptionsCallback iEraseSubscriptionsCallback) throws RemoteException {
        }

        @Override // android.service.euicc.IEuiccService
        public void eraseSubscriptionsWithOptions(int i, int i2, IEraseSubscriptionsCallback iEraseSubscriptionsCallback) throws RemoteException {
        }

        @Override // android.service.euicc.IEuiccService
        public void getAvailableMemoryInBytes(int i, IGetAvailableMemoryInBytesCallback iGetAvailableMemoryInBytesCallback) throws RemoteException {
        }

        @Override // android.service.euicc.IEuiccService
        public void getDefaultDownloadableSubscriptionList(int i, boolean z, IGetDefaultDownloadableSubscriptionListCallback iGetDefaultDownloadableSubscriptionListCallback) throws RemoteException {
        }

        @Override // android.service.euicc.IEuiccService
        public void getDownloadableSubscriptionMetadata(int i, int i2, DownloadableSubscription downloadableSubscription, boolean z, boolean z2, IGetDownloadableSubscriptionMetadataCallback iGetDownloadableSubscriptionMetadataCallback) throws RemoteException {
        }

        @Override // android.service.euicc.IEuiccService
        public void getEid(int i, IGetEidCallback iGetEidCallback) throws RemoteException {
        }

        @Override // android.service.euicc.IEuiccService
        public void getEuiccInfo(int i, IGetEuiccInfoCallback iGetEuiccInfoCallback) throws RemoteException {
        }

        @Override // android.service.euicc.IEuiccService
        public void getEuiccProfileInfoList(int i, IGetEuiccProfileInfoListCallback iGetEuiccProfileInfoListCallback) throws RemoteException {
        }

        @Override // android.service.euicc.IEuiccService
        public void getOtaStatus(int i, IGetOtaStatusCallback iGetOtaStatusCallback) throws RemoteException {
        }

        @Override // android.service.euicc.IEuiccService
        public void retainSubscriptionsForFactoryReset(int i, IRetainSubscriptionsForFactoryResetCallback iRetainSubscriptionsForFactoryResetCallback) throws RemoteException {
        }

        @Override // android.service.euicc.IEuiccService
        public void startOtaIfNecessary(int i, IOtaStatusChangedCallback iOtaStatusChangedCallback) throws RemoteException {
        }

        @Override // android.service.euicc.IEuiccService
        public void switchToSubscription(int i, int i2, String str, boolean z, ISwitchToSubscriptionCallback iSwitchToSubscriptionCallback, boolean z2) throws RemoteException {
        }

        @Override // android.service.euicc.IEuiccService
        public void updateSubscriptionNickname(int i, String str, String str2, IUpdateSubscriptionNicknameCallback iUpdateSubscriptionNicknameCallback) throws RemoteException {
        }
    }

    void deleteSubscription(int i, String str, IDeleteSubscriptionCallback iDeleteSubscriptionCallback) throws RemoteException;

    void downloadSubscription(int i, int i2, DownloadableSubscription downloadableSubscription, boolean z, boolean z2, Bundle bundle, IDownloadSubscriptionCallback iDownloadSubscriptionCallback) throws RemoteException;

    void dump(IEuiccServiceDumpResultCallback iEuiccServiceDumpResultCallback) throws RemoteException;

    void eraseSubscriptions(int i, IEraseSubscriptionsCallback iEraseSubscriptionsCallback) throws RemoteException;

    void eraseSubscriptionsWithOptions(int i, int i2, IEraseSubscriptionsCallback iEraseSubscriptionsCallback) throws RemoteException;

    void getAvailableMemoryInBytes(int i, IGetAvailableMemoryInBytesCallback iGetAvailableMemoryInBytesCallback) throws RemoteException;

    void getDefaultDownloadableSubscriptionList(int i, boolean z, IGetDefaultDownloadableSubscriptionListCallback iGetDefaultDownloadableSubscriptionListCallback) throws RemoteException;

    void getDownloadableSubscriptionMetadata(int i, int i2, DownloadableSubscription downloadableSubscription, boolean z, boolean z2, IGetDownloadableSubscriptionMetadataCallback iGetDownloadableSubscriptionMetadataCallback) throws RemoteException;

    void getEid(int i, IGetEidCallback iGetEidCallback) throws RemoteException;

    void getEuiccInfo(int i, IGetEuiccInfoCallback iGetEuiccInfoCallback) throws RemoteException;

    void getEuiccProfileInfoList(int i, IGetEuiccProfileInfoListCallback iGetEuiccProfileInfoListCallback) throws RemoteException;

    void getOtaStatus(int i, IGetOtaStatusCallback iGetOtaStatusCallback) throws RemoteException;

    void retainSubscriptionsForFactoryReset(int i, IRetainSubscriptionsForFactoryResetCallback iRetainSubscriptionsForFactoryResetCallback) throws RemoteException;

    void startOtaIfNecessary(int i, IOtaStatusChangedCallback iOtaStatusChangedCallback) throws RemoteException;

    void switchToSubscription(int i, int i2, String str, boolean z, ISwitchToSubscriptionCallback iSwitchToSubscriptionCallback, boolean z2) throws RemoteException;

    void updateSubscriptionNickname(int i, String str, String str2, IUpdateSubscriptionNicknameCallback iUpdateSubscriptionNicknameCallback) throws RemoteException;

    public static abstract class Stub extends Binder implements IEuiccService {
        public static final String DESCRIPTOR = "android.service.euicc.IEuiccService";
        static final int TRANSACTION_deleteSubscription = 9;
        static final int TRANSACTION_downloadSubscription = 1;
        static final int TRANSACTION_dump = 15;
        static final int TRANSACTION_eraseSubscriptions = 12;
        static final int TRANSACTION_eraseSubscriptionsWithOptions = 13;
        static final int TRANSACTION_getAvailableMemoryInBytes = 16;
        static final int TRANSACTION_getDefaultDownloadableSubscriptionList = 7;
        static final int TRANSACTION_getDownloadableSubscriptionMetadata = 2;
        static final int TRANSACTION_getEid = 3;
        static final int TRANSACTION_getEuiccInfo = 8;
        static final int TRANSACTION_getEuiccProfileInfoList = 6;
        static final int TRANSACTION_getOtaStatus = 4;
        static final int TRANSACTION_retainSubscriptionsForFactoryReset = 14;
        static final int TRANSACTION_startOtaIfNecessary = 5;
        static final int TRANSACTION_switchToSubscription = 10;
        static final int TRANSACTION_updateSubscriptionNickname = 11;

        @Override // android.os.IInterface
        public IBinder asBinder() {
            return this;
        }

        @Override // android.os.Binder
        public int getMaxTransactionId() {
            return 15;
        }

        public Stub() {
            attachInterface(this, DESCRIPTOR);
        }

        public static IEuiccService asInterface(IBinder iBinder) {
            if (iBinder == null) {
                return null;
            }
            IInterface iInterfaceQueryLocalInterface = iBinder.queryLocalInterface(DESCRIPTOR);
            if (iInterfaceQueryLocalInterface != null && (iInterfaceQueryLocalInterface instanceof IEuiccService)) {
                return (IEuiccService) iInterfaceQueryLocalInterface;
            }
            return new Proxy(iBinder);
        }

        public static String getDefaultTransactionName(int i) {
            switch (i) {
                case 1:
                    return "downloadSubscription";
                case 2:
                    return "getDownloadableSubscriptionMetadata";
                case 3:
                    return "getEid";
                case 4:
                    return "getOtaStatus";
                case 5:
                    return "startOtaIfNecessary";
                case 6:
                    return "getEuiccProfileInfoList";
                case 7:
                    return "getDefaultDownloadableSubscriptionList";
                case 8:
                    return "getEuiccInfo";
                case 9:
                    return "deleteSubscription";
                case 10:
                    return "switchToSubscription";
                case 11:
                    return "updateSubscriptionNickname";
                case 12:
                    return "eraseSubscriptions";
                case 13:
                    return "eraseSubscriptionsWithOptions";
                case 14:
                    return "retainSubscriptionsForFactoryReset";
                case 15:
                    return "dump";
                case 16:
                    return "getAvailableMemoryInBytes";
                default:
                    return null;
            }
        }

        @Override // android.os.Binder
        public String getTransactionName(int i) {
            return getDefaultTransactionName(i);
        }

        @Override // android.os.Binder
        public boolean onTransact(int i, Parcel parcel, Parcel parcel2, int i2) throws RemoteException {
            if (i >= 1 && i <= 16777215) {
                parcel.enforceInterface(DESCRIPTOR);
            }
            if (i == 1598968902) {
                parcel2.writeString(DESCRIPTOR);
                return true;
            }
            switch (i) {
                case 1:
                    int i3 = parcel.readInt();
                    int i4 = parcel.readInt();
                    DownloadableSubscription downloadableSubscription = (DownloadableSubscription) parcel.readTypedObject(DownloadableSubscription.CREATOR);
                    boolean z = parcel.readBoolean();
                    boolean z2 = parcel.readBoolean();
                    Bundle bundle = (Bundle) parcel.readTypedObject(Bundle.CREATOR);
                    IDownloadSubscriptionCallback iDownloadSubscriptionCallbackAsInterface = IDownloadSubscriptionCallback.Stub.asInterface(parcel.readStrongBinder());
                    parcel.enforceNoDataAvail();
                    downloadSubscription(i3, i4, downloadableSubscription, z, z2, bundle, iDownloadSubscriptionCallbackAsInterface);
                    return true;
                case 2:
                    int i5 = parcel.readInt();
                    int i6 = parcel.readInt();
                    DownloadableSubscription downloadableSubscription2 = (DownloadableSubscription) parcel.readTypedObject(DownloadableSubscription.CREATOR);
                    boolean z3 = parcel.readBoolean();
                    boolean z4 = parcel.readBoolean();
                    IGetDownloadableSubscriptionMetadataCallback iGetDownloadableSubscriptionMetadataCallbackAsInterface = IGetDownloadableSubscriptionMetadataCallback.Stub.asInterface(parcel.readStrongBinder());
                    parcel.enforceNoDataAvail();
                    getDownloadableSubscriptionMetadata(i5, i6, downloadableSubscription2, z3, z4, iGetDownloadableSubscriptionMetadataCallbackAsInterface);
                    return true;
                case 3:
                    int i7 = parcel.readInt();
                    IGetEidCallback iGetEidCallbackAsInterface = IGetEidCallback.Stub.asInterface(parcel.readStrongBinder());
                    parcel.enforceNoDataAvail();
                    getEid(i7, iGetEidCallbackAsInterface);
                    return true;
                case 4:
                    int i8 = parcel.readInt();
                    IGetOtaStatusCallback iGetOtaStatusCallbackAsInterface = IGetOtaStatusCallback.Stub.asInterface(parcel.readStrongBinder());
                    parcel.enforceNoDataAvail();
                    getOtaStatus(i8, iGetOtaStatusCallbackAsInterface);
                    return true;
                case 5:
                    int i9 = parcel.readInt();
                    IOtaStatusChangedCallback iOtaStatusChangedCallbackAsInterface = IOtaStatusChangedCallback.Stub.asInterface(parcel.readStrongBinder());
                    parcel.enforceNoDataAvail();
                    startOtaIfNecessary(i9, iOtaStatusChangedCallbackAsInterface);
                    return true;
                case 6:
                    int i10 = parcel.readInt();
                    IGetEuiccProfileInfoListCallback iGetEuiccProfileInfoListCallbackAsInterface = IGetEuiccProfileInfoListCallback.Stub.asInterface(parcel.readStrongBinder());
                    parcel.enforceNoDataAvail();
                    getEuiccProfileInfoList(i10, iGetEuiccProfileInfoListCallbackAsInterface);
                    return true;
                case 7:
                    int i11 = parcel.readInt();
                    boolean z5 = parcel.readBoolean();
                    IGetDefaultDownloadableSubscriptionListCallback iGetDefaultDownloadableSubscriptionListCallbackAsInterface = IGetDefaultDownloadableSubscriptionListCallback.Stub.asInterface(parcel.readStrongBinder());
                    parcel.enforceNoDataAvail();
                    getDefaultDownloadableSubscriptionList(i11, z5, iGetDefaultDownloadableSubscriptionListCallbackAsInterface);
                    return true;
                case 8:
                    int i12 = parcel.readInt();
                    IGetEuiccInfoCallback iGetEuiccInfoCallbackAsInterface = IGetEuiccInfoCallback.Stub.asInterface(parcel.readStrongBinder());
                    parcel.enforceNoDataAvail();
                    getEuiccInfo(i12, iGetEuiccInfoCallbackAsInterface);
                    return true;
                case 9:
                    int i13 = parcel.readInt();
                    String string = parcel.readString();
                    IDeleteSubscriptionCallback iDeleteSubscriptionCallbackAsInterface = IDeleteSubscriptionCallback.Stub.asInterface(parcel.readStrongBinder());
                    parcel.enforceNoDataAvail();
                    deleteSubscription(i13, string, iDeleteSubscriptionCallbackAsInterface);
                    return true;
                case 10:
                    int i14 = parcel.readInt();
                    int i15 = parcel.readInt();
                    String string2 = parcel.readString();
                    boolean z6 = parcel.readBoolean();
                    ISwitchToSubscriptionCallback iSwitchToSubscriptionCallbackAsInterface = ISwitchToSubscriptionCallback.Stub.asInterface(parcel.readStrongBinder());
                    boolean z7 = parcel.readBoolean();
                    parcel.enforceNoDataAvail();
                    switchToSubscription(i14, i15, string2, z6, iSwitchToSubscriptionCallbackAsInterface, z7);
                    return true;
                case 11:
                    int i16 = parcel.readInt();
                    String string3 = parcel.readString();
                    String string4 = parcel.readString();
                    IUpdateSubscriptionNicknameCallback iUpdateSubscriptionNicknameCallbackAsInterface = IUpdateSubscriptionNicknameCallback.Stub.asInterface(parcel.readStrongBinder());
                    parcel.enforceNoDataAvail();
                    updateSubscriptionNickname(i16, string3, string4, iUpdateSubscriptionNicknameCallbackAsInterface);
                    return true;
                case 12:
                    int i17 = parcel.readInt();
                    IEraseSubscriptionsCallback iEraseSubscriptionsCallbackAsInterface = IEraseSubscriptionsCallback.Stub.asInterface(parcel.readStrongBinder());
                    parcel.enforceNoDataAvail();
                    eraseSubscriptions(i17, iEraseSubscriptionsCallbackAsInterface);
                    return true;
                case 13:
                    int i18 = parcel.readInt();
                    int i19 = parcel.readInt();
                    IEraseSubscriptionsCallback iEraseSubscriptionsCallbackAsInterface2 = IEraseSubscriptionsCallback.Stub.asInterface(parcel.readStrongBinder());
                    parcel.enforceNoDataAvail();
                    eraseSubscriptionsWithOptions(i18, i19, iEraseSubscriptionsCallbackAsInterface2);
                    return true;
                case 14:
                    int i20 = parcel.readInt();
                    IRetainSubscriptionsForFactoryResetCallback iRetainSubscriptionsForFactoryResetCallbackAsInterface = IRetainSubscriptionsForFactoryResetCallback.Stub.asInterface(parcel.readStrongBinder());
                    parcel.enforceNoDataAvail();
                    retainSubscriptionsForFactoryReset(i20, iRetainSubscriptionsForFactoryResetCallbackAsInterface);
                    return true;
                case 15:
                    IEuiccServiceDumpResultCallback iEuiccServiceDumpResultCallbackAsInterface = IEuiccServiceDumpResultCallback.Stub.asInterface(parcel.readStrongBinder());
                    parcel.enforceNoDataAvail();
                    dump(iEuiccServiceDumpResultCallbackAsInterface);
                    return true;
                case 16:
                    int i21 = parcel.readInt();
                    IGetAvailableMemoryInBytesCallback iGetAvailableMemoryInBytesCallbackAsInterface = IGetAvailableMemoryInBytesCallback.Stub.asInterface(parcel.readStrongBinder());
                    parcel.enforceNoDataAvail();
                    getAvailableMemoryInBytes(i21, iGetAvailableMemoryInBytesCallbackAsInterface);
                    return true;
                default:
                    return super.onTransact(i, parcel, parcel2, i2);
            }
        }

        private static class Proxy implements IEuiccService {
            private IBinder mRemote;

            Proxy(IBinder iBinder) {
                this.mRemote = iBinder;
            }

            @Override // android.os.IInterface
            public IBinder asBinder() {
                return this.mRemote;
            }

            public String getInterfaceDescriptor() {
                return Stub.DESCRIPTOR;
            }

            @Override // android.service.euicc.IEuiccService
            public void downloadSubscription(int i, int i2, DownloadableSubscription downloadableSubscription, boolean z, boolean z2, Bundle bundle, IDownloadSubscriptionCallback iDownloadSubscriptionCallback) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcelObtain.writeInt(i);
                    parcelObtain.writeInt(i2);
                    parcelObtain.writeTypedObject(downloadableSubscription, 0);
                    parcelObtain.writeBoolean(z);
                    parcelObtain.writeBoolean(z2);
                    parcelObtain.writeTypedObject(bundle, 0);
                    parcelObtain.writeStrongInterface(iDownloadSubscriptionCallback);
                    this.mRemote.transact(1, parcelObtain, null, 1);
                } finally {
                    parcelObtain.recycle();
                }
            }

            @Override // android.service.euicc.IEuiccService
            public void getDownloadableSubscriptionMetadata(int i, int i2, DownloadableSubscription downloadableSubscription, boolean z, boolean z2, IGetDownloadableSubscriptionMetadataCallback iGetDownloadableSubscriptionMetadataCallback) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcelObtain.writeInt(i);
                    parcelObtain.writeInt(i2);
                    parcelObtain.writeTypedObject(downloadableSubscription, 0);
                    parcelObtain.writeBoolean(z);
                    parcelObtain.writeBoolean(z2);
                    parcelObtain.writeStrongInterface(iGetDownloadableSubscriptionMetadataCallback);
                    this.mRemote.transact(2, parcelObtain, null, 1);
                } finally {
                    parcelObtain.recycle();
                }
            }

            @Override // android.service.euicc.IEuiccService
            public void getEid(int i, IGetEidCallback iGetEidCallback) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcelObtain.writeInt(i);
                    parcelObtain.writeStrongInterface(iGetEidCallback);
                    this.mRemote.transact(3, parcelObtain, null, 1);
                } finally {
                    parcelObtain.recycle();
                }
            }

            @Override // android.service.euicc.IEuiccService
            public void getOtaStatus(int i, IGetOtaStatusCallback iGetOtaStatusCallback) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcelObtain.writeInt(i);
                    parcelObtain.writeStrongInterface(iGetOtaStatusCallback);
                    this.mRemote.transact(4, parcelObtain, null, 1);
                } finally {
                    parcelObtain.recycle();
                }
            }

            @Override // android.service.euicc.IEuiccService
            public void startOtaIfNecessary(int i, IOtaStatusChangedCallback iOtaStatusChangedCallback) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcelObtain.writeInt(i);
                    parcelObtain.writeStrongInterface(iOtaStatusChangedCallback);
                    this.mRemote.transact(5, parcelObtain, null, 1);
                } finally {
                    parcelObtain.recycle();
                }
            }

            @Override // android.service.euicc.IEuiccService
            public void getEuiccProfileInfoList(int i, IGetEuiccProfileInfoListCallback iGetEuiccProfileInfoListCallback) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcelObtain.writeInt(i);
                    parcelObtain.writeStrongInterface(iGetEuiccProfileInfoListCallback);
                    this.mRemote.transact(6, parcelObtain, null, 1);
                } finally {
                    parcelObtain.recycle();
                }
            }

            @Override // android.service.euicc.IEuiccService
            public void getDefaultDownloadableSubscriptionList(int i, boolean z, IGetDefaultDownloadableSubscriptionListCallback iGetDefaultDownloadableSubscriptionListCallback) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcelObtain.writeInt(i);
                    parcelObtain.writeBoolean(z);
                    parcelObtain.writeStrongInterface(iGetDefaultDownloadableSubscriptionListCallback);
                    this.mRemote.transact(7, parcelObtain, null, 1);
                } finally {
                    parcelObtain.recycle();
                }
            }

            @Override // android.service.euicc.IEuiccService
            public void getEuiccInfo(int i, IGetEuiccInfoCallback iGetEuiccInfoCallback) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcelObtain.writeInt(i);
                    parcelObtain.writeStrongInterface(iGetEuiccInfoCallback);
                    this.mRemote.transact(8, parcelObtain, null, 1);
                } finally {
                    parcelObtain.recycle();
                }
            }

            @Override // android.service.euicc.IEuiccService
            public void deleteSubscription(int i, String str, IDeleteSubscriptionCallback iDeleteSubscriptionCallback) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcelObtain.writeInt(i);
                    parcelObtain.writeString(str);
                    parcelObtain.writeStrongInterface(iDeleteSubscriptionCallback);
                    this.mRemote.transact(9, parcelObtain, null, 1);
                } finally {
                    parcelObtain.recycle();
                }
            }

            @Override // android.service.euicc.IEuiccService
            public void switchToSubscription(int i, int i2, String str, boolean z, ISwitchToSubscriptionCallback iSwitchToSubscriptionCallback, boolean z2) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcelObtain.writeInt(i);
                    parcelObtain.writeInt(i2);
                    parcelObtain.writeString(str);
                    parcelObtain.writeBoolean(z);
                    parcelObtain.writeStrongInterface(iSwitchToSubscriptionCallback);
                    parcelObtain.writeBoolean(z2);
                    this.mRemote.transact(10, parcelObtain, null, 1);
                } finally {
                    parcelObtain.recycle();
                }
            }

            @Override // android.service.euicc.IEuiccService
            public void updateSubscriptionNickname(int i, String str, String str2, IUpdateSubscriptionNicknameCallback iUpdateSubscriptionNicknameCallback) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcelObtain.writeInt(i);
                    parcelObtain.writeString(str);
                    parcelObtain.writeString(str2);
                    parcelObtain.writeStrongInterface(iUpdateSubscriptionNicknameCallback);
                    this.mRemote.transact(11, parcelObtain, null, 1);
                } finally {
                    parcelObtain.recycle();
                }
            }

            @Override // android.service.euicc.IEuiccService
            public void eraseSubscriptions(int i, IEraseSubscriptionsCallback iEraseSubscriptionsCallback) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcelObtain.writeInt(i);
                    parcelObtain.writeStrongInterface(iEraseSubscriptionsCallback);
                    this.mRemote.transact(12, parcelObtain, null, 1);
                } finally {
                    parcelObtain.recycle();
                }
            }

            @Override // android.service.euicc.IEuiccService
            public void eraseSubscriptionsWithOptions(int i, int i2, IEraseSubscriptionsCallback iEraseSubscriptionsCallback) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcelObtain.writeInt(i);
                    parcelObtain.writeInt(i2);
                    parcelObtain.writeStrongInterface(iEraseSubscriptionsCallback);
                    this.mRemote.transact(13, parcelObtain, null, 1);
                } finally {
                    parcelObtain.recycle();
                }
            }

            @Override // android.service.euicc.IEuiccService
            public void retainSubscriptionsForFactoryReset(int i, IRetainSubscriptionsForFactoryResetCallback iRetainSubscriptionsForFactoryResetCallback) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcelObtain.writeInt(i);
                    parcelObtain.writeStrongInterface(iRetainSubscriptionsForFactoryResetCallback);
                    this.mRemote.transact(14, parcelObtain, null, 1);
                } finally {
                    parcelObtain.recycle();
                }
            }

            @Override // android.service.euicc.IEuiccService
            public void dump(IEuiccServiceDumpResultCallback iEuiccServiceDumpResultCallback) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcelObtain.writeStrongInterface(iEuiccServiceDumpResultCallback);
                    this.mRemote.transact(15, parcelObtain, null, 1);
                } finally {
                    parcelObtain.recycle();
                }
            }

            @Override // android.service.euicc.IEuiccService
            public void getAvailableMemoryInBytes(int i, IGetAvailableMemoryInBytesCallback iGetAvailableMemoryInBytesCallback) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcelObtain.writeInt(i);
                    parcelObtain.writeStrongInterface(iGetAvailableMemoryInBytesCallback);
                    this.mRemote.transact(16, parcelObtain, null, 1);
                } finally {
                    parcelObtain.recycle();
                }
            }
        }
    }
}
