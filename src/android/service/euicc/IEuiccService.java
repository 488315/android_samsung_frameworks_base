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
            IInterface queryLocalInterface = iBinder.queryLocalInterface(DESCRIPTOR);
            if (queryLocalInterface != null && (queryLocalInterface instanceof IEuiccService)) {
                return (IEuiccService) queryLocalInterface;
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
                    int readInt = parcel.readInt();
                    int readInt2 = parcel.readInt();
                    DownloadableSubscription downloadableSubscription = (DownloadableSubscription) parcel.readTypedObject(DownloadableSubscription.CREATOR);
                    boolean readBoolean = parcel.readBoolean();
                    boolean readBoolean2 = parcel.readBoolean();
                    Bundle bundle = (Bundle) parcel.readTypedObject(Bundle.CREATOR);
                    IDownloadSubscriptionCallback asInterface = IDownloadSubscriptionCallback.Stub.asInterface(parcel.readStrongBinder());
                    parcel.enforceNoDataAvail();
                    downloadSubscription(readInt, readInt2, downloadableSubscription, readBoolean, readBoolean2, bundle, asInterface);
                    return true;
                case 2:
                    int readInt3 = parcel.readInt();
                    int readInt4 = parcel.readInt();
                    DownloadableSubscription downloadableSubscription2 = (DownloadableSubscription) parcel.readTypedObject(DownloadableSubscription.CREATOR);
                    boolean readBoolean3 = parcel.readBoolean();
                    boolean readBoolean4 = parcel.readBoolean();
                    IGetDownloadableSubscriptionMetadataCallback asInterface2 = IGetDownloadableSubscriptionMetadataCallback.Stub.asInterface(parcel.readStrongBinder());
                    parcel.enforceNoDataAvail();
                    getDownloadableSubscriptionMetadata(readInt3, readInt4, downloadableSubscription2, readBoolean3, readBoolean4, asInterface2);
                    return true;
                case 3:
                    int readInt5 = parcel.readInt();
                    IGetEidCallback asInterface3 = IGetEidCallback.Stub.asInterface(parcel.readStrongBinder());
                    parcel.enforceNoDataAvail();
                    getEid(readInt5, asInterface3);
                    return true;
                case 4:
                    int readInt6 = parcel.readInt();
                    IGetOtaStatusCallback asInterface4 = IGetOtaStatusCallback.Stub.asInterface(parcel.readStrongBinder());
                    parcel.enforceNoDataAvail();
                    getOtaStatus(readInt6, asInterface4);
                    return true;
                case 5:
                    int readInt7 = parcel.readInt();
                    IOtaStatusChangedCallback asInterface5 = IOtaStatusChangedCallback.Stub.asInterface(parcel.readStrongBinder());
                    parcel.enforceNoDataAvail();
                    startOtaIfNecessary(readInt7, asInterface5);
                    return true;
                case 6:
                    int readInt8 = parcel.readInt();
                    IGetEuiccProfileInfoListCallback asInterface6 = IGetEuiccProfileInfoListCallback.Stub.asInterface(parcel.readStrongBinder());
                    parcel.enforceNoDataAvail();
                    getEuiccProfileInfoList(readInt8, asInterface6);
                    return true;
                case 7:
                    int readInt9 = parcel.readInt();
                    boolean readBoolean5 = parcel.readBoolean();
                    IGetDefaultDownloadableSubscriptionListCallback asInterface7 = IGetDefaultDownloadableSubscriptionListCallback.Stub.asInterface(parcel.readStrongBinder());
                    parcel.enforceNoDataAvail();
                    getDefaultDownloadableSubscriptionList(readInt9, readBoolean5, asInterface7);
                    return true;
                case 8:
                    int readInt10 = parcel.readInt();
                    IGetEuiccInfoCallback asInterface8 = IGetEuiccInfoCallback.Stub.asInterface(parcel.readStrongBinder());
                    parcel.enforceNoDataAvail();
                    getEuiccInfo(readInt10, asInterface8);
                    return true;
                case 9:
                    int readInt11 = parcel.readInt();
                    String readString = parcel.readString();
                    IDeleteSubscriptionCallback asInterface9 = IDeleteSubscriptionCallback.Stub.asInterface(parcel.readStrongBinder());
                    parcel.enforceNoDataAvail();
                    deleteSubscription(readInt11, readString, asInterface9);
                    return true;
                case 10:
                    int readInt12 = parcel.readInt();
                    int readInt13 = parcel.readInt();
                    String readString2 = parcel.readString();
                    boolean readBoolean6 = parcel.readBoolean();
                    ISwitchToSubscriptionCallback asInterface10 = ISwitchToSubscriptionCallback.Stub.asInterface(parcel.readStrongBinder());
                    boolean readBoolean7 = parcel.readBoolean();
                    parcel.enforceNoDataAvail();
                    switchToSubscription(readInt12, readInt13, readString2, readBoolean6, asInterface10, readBoolean7);
                    return true;
                case 11:
                    int readInt14 = parcel.readInt();
                    String readString3 = parcel.readString();
                    String readString4 = parcel.readString();
                    IUpdateSubscriptionNicknameCallback asInterface11 = IUpdateSubscriptionNicknameCallback.Stub.asInterface(parcel.readStrongBinder());
                    parcel.enforceNoDataAvail();
                    updateSubscriptionNickname(readInt14, readString3, readString4, asInterface11);
                    return true;
                case 12:
                    int readInt15 = parcel.readInt();
                    IEraseSubscriptionsCallback asInterface12 = IEraseSubscriptionsCallback.Stub.asInterface(parcel.readStrongBinder());
                    parcel.enforceNoDataAvail();
                    eraseSubscriptions(readInt15, asInterface12);
                    return true;
                case 13:
                    int readInt16 = parcel.readInt();
                    int readInt17 = parcel.readInt();
                    IEraseSubscriptionsCallback asInterface13 = IEraseSubscriptionsCallback.Stub.asInterface(parcel.readStrongBinder());
                    parcel.enforceNoDataAvail();
                    eraseSubscriptionsWithOptions(readInt16, readInt17, asInterface13);
                    return true;
                case 14:
                    int readInt18 = parcel.readInt();
                    IRetainSubscriptionsForFactoryResetCallback asInterface14 = IRetainSubscriptionsForFactoryResetCallback.Stub.asInterface(parcel.readStrongBinder());
                    parcel.enforceNoDataAvail();
                    retainSubscriptionsForFactoryReset(readInt18, asInterface14);
                    return true;
                case 15:
                    IEuiccServiceDumpResultCallback asInterface15 = IEuiccServiceDumpResultCallback.Stub.asInterface(parcel.readStrongBinder());
                    parcel.enforceNoDataAvail();
                    dump(asInterface15);
                    return true;
                case 16:
                    int readInt19 = parcel.readInt();
                    IGetAvailableMemoryInBytesCallback asInterface16 = IGetAvailableMemoryInBytesCallback.Stub.asInterface(parcel.readStrongBinder());
                    parcel.enforceNoDataAvail();
                    getAvailableMemoryInBytes(readInt19, asInterface16);
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
                Parcel obtain = Parcel.obtain(asBinder());
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    obtain.writeInt(i);
                    obtain.writeInt(i2);
                    obtain.writeTypedObject(downloadableSubscription, 0);
                    obtain.writeBoolean(z);
                    obtain.writeBoolean(z2);
                    obtain.writeTypedObject(bundle, 0);
                    obtain.writeStrongInterface(iDownloadSubscriptionCallback);
                    this.mRemote.transact(1, obtain, null, 1);
                } finally {
                    obtain.recycle();
                }
            }

            @Override // android.service.euicc.IEuiccService
            public void getDownloadableSubscriptionMetadata(int i, int i2, DownloadableSubscription downloadableSubscription, boolean z, boolean z2, IGetDownloadableSubscriptionMetadataCallback iGetDownloadableSubscriptionMetadataCallback) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    obtain.writeInt(i);
                    obtain.writeInt(i2);
                    obtain.writeTypedObject(downloadableSubscription, 0);
                    obtain.writeBoolean(z);
                    obtain.writeBoolean(z2);
                    obtain.writeStrongInterface(iGetDownloadableSubscriptionMetadataCallback);
                    this.mRemote.transact(2, obtain, null, 1);
                } finally {
                    obtain.recycle();
                }
            }

            @Override // android.service.euicc.IEuiccService
            public void getEid(int i, IGetEidCallback iGetEidCallback) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    obtain.writeInt(i);
                    obtain.writeStrongInterface(iGetEidCallback);
                    this.mRemote.transact(3, obtain, null, 1);
                } finally {
                    obtain.recycle();
                }
            }

            @Override // android.service.euicc.IEuiccService
            public void getOtaStatus(int i, IGetOtaStatusCallback iGetOtaStatusCallback) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    obtain.writeInt(i);
                    obtain.writeStrongInterface(iGetOtaStatusCallback);
                    this.mRemote.transact(4, obtain, null, 1);
                } finally {
                    obtain.recycle();
                }
            }

            @Override // android.service.euicc.IEuiccService
            public void startOtaIfNecessary(int i, IOtaStatusChangedCallback iOtaStatusChangedCallback) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    obtain.writeInt(i);
                    obtain.writeStrongInterface(iOtaStatusChangedCallback);
                    this.mRemote.transact(5, obtain, null, 1);
                } finally {
                    obtain.recycle();
                }
            }

            @Override // android.service.euicc.IEuiccService
            public void getEuiccProfileInfoList(int i, IGetEuiccProfileInfoListCallback iGetEuiccProfileInfoListCallback) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    obtain.writeInt(i);
                    obtain.writeStrongInterface(iGetEuiccProfileInfoListCallback);
                    this.mRemote.transact(6, obtain, null, 1);
                } finally {
                    obtain.recycle();
                }
            }

            @Override // android.service.euicc.IEuiccService
            public void getDefaultDownloadableSubscriptionList(int i, boolean z, IGetDefaultDownloadableSubscriptionListCallback iGetDefaultDownloadableSubscriptionListCallback) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    obtain.writeInt(i);
                    obtain.writeBoolean(z);
                    obtain.writeStrongInterface(iGetDefaultDownloadableSubscriptionListCallback);
                    this.mRemote.transact(7, obtain, null, 1);
                } finally {
                    obtain.recycle();
                }
            }

            @Override // android.service.euicc.IEuiccService
            public void getEuiccInfo(int i, IGetEuiccInfoCallback iGetEuiccInfoCallback) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    obtain.writeInt(i);
                    obtain.writeStrongInterface(iGetEuiccInfoCallback);
                    this.mRemote.transact(8, obtain, null, 1);
                } finally {
                    obtain.recycle();
                }
            }

            @Override // android.service.euicc.IEuiccService
            public void deleteSubscription(int i, String str, IDeleteSubscriptionCallback iDeleteSubscriptionCallback) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    obtain.writeInt(i);
                    obtain.writeString(str);
                    obtain.writeStrongInterface(iDeleteSubscriptionCallback);
                    this.mRemote.transact(9, obtain, null, 1);
                } finally {
                    obtain.recycle();
                }
            }

            @Override // android.service.euicc.IEuiccService
            public void switchToSubscription(int i, int i2, String str, boolean z, ISwitchToSubscriptionCallback iSwitchToSubscriptionCallback, boolean z2) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    obtain.writeInt(i);
                    obtain.writeInt(i2);
                    obtain.writeString(str);
                    obtain.writeBoolean(z);
                    obtain.writeStrongInterface(iSwitchToSubscriptionCallback);
                    obtain.writeBoolean(z2);
                    this.mRemote.transact(10, obtain, null, 1);
                } finally {
                    obtain.recycle();
                }
            }

            @Override // android.service.euicc.IEuiccService
            public void updateSubscriptionNickname(int i, String str, String str2, IUpdateSubscriptionNicknameCallback iUpdateSubscriptionNicknameCallback) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    obtain.writeInt(i);
                    obtain.writeString(str);
                    obtain.writeString(str2);
                    obtain.writeStrongInterface(iUpdateSubscriptionNicknameCallback);
                    this.mRemote.transact(11, obtain, null, 1);
                } finally {
                    obtain.recycle();
                }
            }

            @Override // android.service.euicc.IEuiccService
            public void eraseSubscriptions(int i, IEraseSubscriptionsCallback iEraseSubscriptionsCallback) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    obtain.writeInt(i);
                    obtain.writeStrongInterface(iEraseSubscriptionsCallback);
                    this.mRemote.transact(12, obtain, null, 1);
                } finally {
                    obtain.recycle();
                }
            }

            @Override // android.service.euicc.IEuiccService
            public void eraseSubscriptionsWithOptions(int i, int i2, IEraseSubscriptionsCallback iEraseSubscriptionsCallback) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    obtain.writeInt(i);
                    obtain.writeInt(i2);
                    obtain.writeStrongInterface(iEraseSubscriptionsCallback);
                    this.mRemote.transact(13, obtain, null, 1);
                } finally {
                    obtain.recycle();
                }
            }

            @Override // android.service.euicc.IEuiccService
            public void retainSubscriptionsForFactoryReset(int i, IRetainSubscriptionsForFactoryResetCallback iRetainSubscriptionsForFactoryResetCallback) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    obtain.writeInt(i);
                    obtain.writeStrongInterface(iRetainSubscriptionsForFactoryResetCallback);
                    this.mRemote.transact(14, obtain, null, 1);
                } finally {
                    obtain.recycle();
                }
            }

            @Override // android.service.euicc.IEuiccService
            public void dump(IEuiccServiceDumpResultCallback iEuiccServiceDumpResultCallback) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    obtain.writeStrongInterface(iEuiccServiceDumpResultCallback);
                    this.mRemote.transact(15, obtain, null, 1);
                } finally {
                    obtain.recycle();
                }
            }

            @Override // android.service.euicc.IEuiccService
            public void getAvailableMemoryInBytes(int i, IGetAvailableMemoryInBytesCallback iGetAvailableMemoryInBytesCallback) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    obtain.writeInt(i);
                    obtain.writeStrongInterface(iGetAvailableMemoryInBytesCallback);
                    this.mRemote.transact(16, obtain, null, 1);
                } finally {
                    obtain.recycle();
                }
            }
        }
    }
}
