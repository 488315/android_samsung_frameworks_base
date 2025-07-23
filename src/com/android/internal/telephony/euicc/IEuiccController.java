package com.android.internal.telephony.euicc;

import android.app.PendingIntent;
import android.content.Intent;
import android.os.Binder;
import android.os.Bundle;
import android.os.IBinder;
import android.os.IInterface;
import android.os.Parcel;
import android.os.RemoteException;
import android.telephony.euicc.DownloadableSubscription;
import android.telephony.euicc.EuiccInfo;
import java.util.ArrayList;
import java.util.List;

/* loaded from: classes4.dex */
public interface IEuiccController extends IInterface {
    public static final String DESCRIPTOR = "com.android.internal.telephony.euicc.IEuiccController";

    public static class Default implements IEuiccController {
        @Override // android.os.IInterface
        public IBinder asBinder() {
            return null;
        }

        @Override // com.android.internal.telephony.euicc.IEuiccController
        public void continueOperation(int i, Intent intent, Bundle bundle) throws RemoteException {
        }

        @Override // com.android.internal.telephony.euicc.IEuiccController
        public void deleteSubscription(int i, int i2, String str, PendingIntent pendingIntent) throws RemoteException {
        }

        @Override // com.android.internal.telephony.euicc.IEuiccController
        public void downloadSubscription(int i, DownloadableSubscription downloadableSubscription, boolean z, String str, Bundle bundle, PendingIntent pendingIntent) throws RemoteException {
        }

        @Override // com.android.internal.telephony.euicc.IEuiccController
        public void eraseSubscriptions(int i, PendingIntent pendingIntent) throws RemoteException {
        }

        @Override // com.android.internal.telephony.euicc.IEuiccController
        public void eraseSubscriptionsWithOptions(int i, int i2, PendingIntent pendingIntent) throws RemoteException {
        }

        @Override // com.android.internal.telephony.euicc.IEuiccController
        public long getAvailableMemoryInBytes(int i, String str) throws RemoteException {
            return 0L;
        }

        @Override // com.android.internal.telephony.euicc.IEuiccController
        public void getDefaultDownloadableSubscriptionList(int i, String str, PendingIntent pendingIntent) throws RemoteException {
        }

        @Override // com.android.internal.telephony.euicc.IEuiccController
        public void getDownloadableSubscriptionMetadata(int i, DownloadableSubscription downloadableSubscription, String str, PendingIntent pendingIntent) throws RemoteException {
        }

        @Override // com.android.internal.telephony.euicc.IEuiccController
        public String getEid(int i, String str) throws RemoteException {
            return null;
        }

        @Override // com.android.internal.telephony.euicc.IEuiccController
        public EuiccInfo getEuiccInfo(int i) throws RemoteException {
            return null;
        }

        @Override // com.android.internal.telephony.euicc.IEuiccController
        public int getOtaStatus(int i) throws RemoteException {
            return 0;
        }

        @Override // com.android.internal.telephony.euicc.IEuiccController
        public List<String> getSupportedCountries(boolean z) throws RemoteException {
            return null;
        }

        @Override // com.android.internal.telephony.euicc.IEuiccController
        public boolean hasCarrierPrivilegesForPackageOnAnyPhone(String str) throws RemoteException {
            return false;
        }

        @Override // com.android.internal.telephony.euicc.IEuiccController
        public boolean isCompatChangeEnabled(String str, long j) throws RemoteException {
            return false;
        }

        @Override // com.android.internal.telephony.euicc.IEuiccController
        public boolean isPsimConversionSupported(int i) throws RemoteException {
            return false;
        }

        @Override // com.android.internal.telephony.euicc.IEuiccController
        public boolean isSimPortAvailable(int i, int i2, String str) throws RemoteException {
            return false;
        }

        @Override // com.android.internal.telephony.euicc.IEuiccController
        public boolean isSupportedCountry(String str) throws RemoteException {
            return false;
        }

        @Override // com.android.internal.telephony.euicc.IEuiccController
        public void retainSubscriptionsForFactoryReset(int i, PendingIntent pendingIntent) throws RemoteException {
        }

        @Override // com.android.internal.telephony.euicc.IEuiccController
        public void setPsimConversionSupportedCarriers(int[] iArr) throws RemoteException {
        }

        @Override // com.android.internal.telephony.euicc.IEuiccController
        public void setSupportedCountries(boolean z, List<String> list) throws RemoteException {
        }

        @Override // com.android.internal.telephony.euicc.IEuiccController
        public void switchToSubscription(int i, int i2, String str, PendingIntent pendingIntent) throws RemoteException {
        }

        @Override // com.android.internal.telephony.euicc.IEuiccController
        public void switchToSubscriptionWithPort(int i, int i2, int i3, String str, PendingIntent pendingIntent) throws RemoteException {
        }

        @Override // com.android.internal.telephony.euicc.IEuiccController
        public void updateSubscriptionNickname(int i, int i2, String str, String str2, PendingIntent pendingIntent) throws RemoteException {
        }
    }

    void continueOperation(int i, Intent intent, Bundle bundle) throws RemoteException;

    void deleteSubscription(int i, int i2, String str, PendingIntent pendingIntent) throws RemoteException;

    void downloadSubscription(int i, DownloadableSubscription downloadableSubscription, boolean z, String str, Bundle bundle, PendingIntent pendingIntent) throws RemoteException;

    void eraseSubscriptions(int i, PendingIntent pendingIntent) throws RemoteException;

    void eraseSubscriptionsWithOptions(int i, int i2, PendingIntent pendingIntent) throws RemoteException;

    long getAvailableMemoryInBytes(int i, String str) throws RemoteException;

    void getDefaultDownloadableSubscriptionList(int i, String str, PendingIntent pendingIntent) throws RemoteException;

    void getDownloadableSubscriptionMetadata(int i, DownloadableSubscription downloadableSubscription, String str, PendingIntent pendingIntent) throws RemoteException;

    String getEid(int i, String str) throws RemoteException;

    EuiccInfo getEuiccInfo(int i) throws RemoteException;

    int getOtaStatus(int i) throws RemoteException;

    List<String> getSupportedCountries(boolean z) throws RemoteException;

    boolean hasCarrierPrivilegesForPackageOnAnyPhone(String str) throws RemoteException;

    boolean isCompatChangeEnabled(String str, long j) throws RemoteException;

    boolean isPsimConversionSupported(int i) throws RemoteException;

    boolean isSimPortAvailable(int i, int i2, String str) throws RemoteException;

    boolean isSupportedCountry(String str) throws RemoteException;

    void retainSubscriptionsForFactoryReset(int i, PendingIntent pendingIntent) throws RemoteException;

    void setPsimConversionSupportedCarriers(int[] iArr) throws RemoteException;

    void setSupportedCountries(boolean z, List<String> list) throws RemoteException;

    void switchToSubscription(int i, int i2, String str, PendingIntent pendingIntent) throws RemoteException;

    void switchToSubscriptionWithPort(int i, int i2, int i3, String str, PendingIntent pendingIntent) throws RemoteException;

    void updateSubscriptionNickname(int i, int i2, String str, String str2, PendingIntent pendingIntent) throws RemoteException;

    public static abstract class Stub extends Binder implements IEuiccController {
        static final int TRANSACTION_continueOperation = 1;
        static final int TRANSACTION_deleteSubscription = 8;
        static final int TRANSACTION_downloadSubscription = 6;
        static final int TRANSACTION_eraseSubscriptions = 12;
        static final int TRANSACTION_eraseSubscriptionsWithOptions = 13;
        static final int TRANSACTION_getAvailableMemoryInBytes = 23;
        static final int TRANSACTION_getDefaultDownloadableSubscriptionList = 3;
        static final int TRANSACTION_getDownloadableSubscriptionMetadata = 2;
        static final int TRANSACTION_getEid = 4;
        static final int TRANSACTION_getEuiccInfo = 7;
        static final int TRANSACTION_getOtaStatus = 5;
        static final int TRANSACTION_getSupportedCountries = 16;
        static final int TRANSACTION_hasCarrierPrivilegesForPackageOnAnyPhone = 19;
        static final int TRANSACTION_isCompatChangeEnabled = 20;
        static final int TRANSACTION_isPsimConversionSupported = 22;
        static final int TRANSACTION_isSimPortAvailable = 18;
        static final int TRANSACTION_isSupportedCountry = 17;
        static final int TRANSACTION_retainSubscriptionsForFactoryReset = 14;
        static final int TRANSACTION_setPsimConversionSupportedCarriers = 21;
        static final int TRANSACTION_setSupportedCountries = 15;
        static final int TRANSACTION_switchToSubscription = 9;
        static final int TRANSACTION_switchToSubscriptionWithPort = 10;
        static final int TRANSACTION_updateSubscriptionNickname = 11;

        @Override // android.os.IInterface
        public IBinder asBinder() {
            return this;
        }

        @Override // android.os.Binder
        public int getMaxTransactionId() {
            return 22;
        }

        public Stub() {
            attachInterface(this, IEuiccController.DESCRIPTOR);
        }

        public static IEuiccController asInterface(IBinder iBinder) {
            if (iBinder == null) {
                return null;
            }
            IInterface queryLocalInterface = iBinder.queryLocalInterface(IEuiccController.DESCRIPTOR);
            if (queryLocalInterface != null && (queryLocalInterface instanceof IEuiccController)) {
                return (IEuiccController) queryLocalInterface;
            }
            return new Proxy(iBinder);
        }

        public static String getDefaultTransactionName(int i) {
            switch (i) {
                case 1:
                    return "continueOperation";
                case 2:
                    return "getDownloadableSubscriptionMetadata";
                case 3:
                    return "getDefaultDownloadableSubscriptionList";
                case 4:
                    return "getEid";
                case 5:
                    return "getOtaStatus";
                case 6:
                    return "downloadSubscription";
                case 7:
                    return "getEuiccInfo";
                case 8:
                    return "deleteSubscription";
                case 9:
                    return "switchToSubscription";
                case 10:
                    return "switchToSubscriptionWithPort";
                case 11:
                    return "updateSubscriptionNickname";
                case 12:
                    return "eraseSubscriptions";
                case 13:
                    return "eraseSubscriptionsWithOptions";
                case 14:
                    return "retainSubscriptionsForFactoryReset";
                case 15:
                    return "setSupportedCountries";
                case 16:
                    return "getSupportedCountries";
                case 17:
                    return "isSupportedCountry";
                case 18:
                    return "isSimPortAvailable";
                case 19:
                    return "hasCarrierPrivilegesForPackageOnAnyPhone";
                case 20:
                    return "isCompatChangeEnabled";
                case 21:
                    return "setPsimConversionSupportedCarriers";
                case 22:
                    return "isPsimConversionSupported";
                case 23:
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
                parcel.enforceInterface(IEuiccController.DESCRIPTOR);
            }
            if (i == 1598968902) {
                parcel2.writeString(IEuiccController.DESCRIPTOR);
                return true;
            }
            switch (i) {
                case 1:
                    int readInt = parcel.readInt();
                    Intent intent = (Intent) parcel.readTypedObject(Intent.CREATOR);
                    Bundle bundle = (Bundle) parcel.readTypedObject(Bundle.CREATOR);
                    parcel.enforceNoDataAvail();
                    continueOperation(readInt, intent, bundle);
                    return true;
                case 2:
                    int readInt2 = parcel.readInt();
                    DownloadableSubscription downloadableSubscription = (DownloadableSubscription) parcel.readTypedObject(DownloadableSubscription.CREATOR);
                    String readString = parcel.readString();
                    PendingIntent pendingIntent = (PendingIntent) parcel.readTypedObject(PendingIntent.CREATOR);
                    parcel.enforceNoDataAvail();
                    getDownloadableSubscriptionMetadata(readInt2, downloadableSubscription, readString, pendingIntent);
                    return true;
                case 3:
                    int readInt3 = parcel.readInt();
                    String readString2 = parcel.readString();
                    PendingIntent pendingIntent2 = (PendingIntent) parcel.readTypedObject(PendingIntent.CREATOR);
                    parcel.enforceNoDataAvail();
                    getDefaultDownloadableSubscriptionList(readInt3, readString2, pendingIntent2);
                    return true;
                case 4:
                    int readInt4 = parcel.readInt();
                    String readString3 = parcel.readString();
                    parcel.enforceNoDataAvail();
                    String eid = getEid(readInt4, readString3);
                    parcel2.writeNoException();
                    parcel2.writeString(eid);
                    return true;
                case 5:
                    int readInt5 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    int otaStatus = getOtaStatus(readInt5);
                    parcel2.writeNoException();
                    parcel2.writeInt(otaStatus);
                    return true;
                case 6:
                    int readInt6 = parcel.readInt();
                    DownloadableSubscription downloadableSubscription2 = (DownloadableSubscription) parcel.readTypedObject(DownloadableSubscription.CREATOR);
                    boolean readBoolean = parcel.readBoolean();
                    String readString4 = parcel.readString();
                    Bundle bundle2 = (Bundle) parcel.readTypedObject(Bundle.CREATOR);
                    PendingIntent pendingIntent3 = (PendingIntent) parcel.readTypedObject(PendingIntent.CREATOR);
                    parcel.enforceNoDataAvail();
                    downloadSubscription(readInt6, downloadableSubscription2, readBoolean, readString4, bundle2, pendingIntent3);
                    return true;
                case 7:
                    int readInt7 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    EuiccInfo euiccInfo = getEuiccInfo(readInt7);
                    parcel2.writeNoException();
                    parcel2.writeTypedObject(euiccInfo, 1);
                    return true;
                case 8:
                    int readInt8 = parcel.readInt();
                    int readInt9 = parcel.readInt();
                    String readString5 = parcel.readString();
                    PendingIntent pendingIntent4 = (PendingIntent) parcel.readTypedObject(PendingIntent.CREATOR);
                    parcel.enforceNoDataAvail();
                    deleteSubscription(readInt8, readInt9, readString5, pendingIntent4);
                    return true;
                case 9:
                    int readInt10 = parcel.readInt();
                    int readInt11 = parcel.readInt();
                    String readString6 = parcel.readString();
                    PendingIntent pendingIntent5 = (PendingIntent) parcel.readTypedObject(PendingIntent.CREATOR);
                    parcel.enforceNoDataAvail();
                    switchToSubscription(readInt10, readInt11, readString6, pendingIntent5);
                    return true;
                case 10:
                    int readInt12 = parcel.readInt();
                    int readInt13 = parcel.readInt();
                    int readInt14 = parcel.readInt();
                    String readString7 = parcel.readString();
                    PendingIntent pendingIntent6 = (PendingIntent) parcel.readTypedObject(PendingIntent.CREATOR);
                    parcel.enforceNoDataAvail();
                    switchToSubscriptionWithPort(readInt12, readInt13, readInt14, readString7, pendingIntent6);
                    return true;
                case 11:
                    int readInt15 = parcel.readInt();
                    int readInt16 = parcel.readInt();
                    String readString8 = parcel.readString();
                    String readString9 = parcel.readString();
                    PendingIntent pendingIntent7 = (PendingIntent) parcel.readTypedObject(PendingIntent.CREATOR);
                    parcel.enforceNoDataAvail();
                    updateSubscriptionNickname(readInt15, readInt16, readString8, readString9, pendingIntent7);
                    return true;
                case 12:
                    int readInt17 = parcel.readInt();
                    PendingIntent pendingIntent8 = (PendingIntent) parcel.readTypedObject(PendingIntent.CREATOR);
                    parcel.enforceNoDataAvail();
                    eraseSubscriptions(readInt17, pendingIntent8);
                    return true;
                case 13:
                    int readInt18 = parcel.readInt();
                    int readInt19 = parcel.readInt();
                    PendingIntent pendingIntent9 = (PendingIntent) parcel.readTypedObject(PendingIntent.CREATOR);
                    parcel.enforceNoDataAvail();
                    eraseSubscriptionsWithOptions(readInt18, readInt19, pendingIntent9);
                    return true;
                case 14:
                    int readInt20 = parcel.readInt();
                    PendingIntent pendingIntent10 = (PendingIntent) parcel.readTypedObject(PendingIntent.CREATOR);
                    parcel.enforceNoDataAvail();
                    retainSubscriptionsForFactoryReset(readInt20, pendingIntent10);
                    return true;
                case 15:
                    boolean readBoolean2 = parcel.readBoolean();
                    ArrayList<String> createStringArrayList = parcel.createStringArrayList();
                    parcel.enforceNoDataAvail();
                    setSupportedCountries(readBoolean2, createStringArrayList);
                    parcel2.writeNoException();
                    return true;
                case 16:
                    boolean readBoolean3 = parcel.readBoolean();
                    parcel.enforceNoDataAvail();
                    List<String> supportedCountries = getSupportedCountries(readBoolean3);
                    parcel2.writeNoException();
                    parcel2.writeStringList(supportedCountries);
                    return true;
                case 17:
                    String readString10 = parcel.readString();
                    parcel.enforceNoDataAvail();
                    boolean isSupportedCountry = isSupportedCountry(readString10);
                    parcel2.writeNoException();
                    parcel2.writeBoolean(isSupportedCountry);
                    return true;
                case 18:
                    int readInt21 = parcel.readInt();
                    int readInt22 = parcel.readInt();
                    String readString11 = parcel.readString();
                    parcel.enforceNoDataAvail();
                    boolean isSimPortAvailable = isSimPortAvailable(readInt21, readInt22, readString11);
                    parcel2.writeNoException();
                    parcel2.writeBoolean(isSimPortAvailable);
                    return true;
                case 19:
                    String readString12 = parcel.readString();
                    parcel.enforceNoDataAvail();
                    boolean hasCarrierPrivilegesForPackageOnAnyPhone = hasCarrierPrivilegesForPackageOnAnyPhone(readString12);
                    parcel2.writeNoException();
                    parcel2.writeBoolean(hasCarrierPrivilegesForPackageOnAnyPhone);
                    return true;
                case 20:
                    String readString13 = parcel.readString();
                    long readLong = parcel.readLong();
                    parcel.enforceNoDataAvail();
                    boolean isCompatChangeEnabled = isCompatChangeEnabled(readString13, readLong);
                    parcel2.writeNoException();
                    parcel2.writeBoolean(isCompatChangeEnabled);
                    return true;
                case 21:
                    int[] createIntArray = parcel.createIntArray();
                    parcel.enforceNoDataAvail();
                    setPsimConversionSupportedCarriers(createIntArray);
                    parcel2.writeNoException();
                    return true;
                case 22:
                    int readInt23 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    boolean isPsimConversionSupported = isPsimConversionSupported(readInt23);
                    parcel2.writeNoException();
                    parcel2.writeBoolean(isPsimConversionSupported);
                    return true;
                case 23:
                    int readInt24 = parcel.readInt();
                    String readString14 = parcel.readString();
                    parcel.enforceNoDataAvail();
                    long availableMemoryInBytes = getAvailableMemoryInBytes(readInt24, readString14);
                    parcel2.writeNoException();
                    parcel2.writeLong(availableMemoryInBytes);
                    return true;
                default:
                    return super.onTransact(i, parcel, parcel2, i2);
            }
        }

        private static class Proxy implements IEuiccController {
            private IBinder mRemote;

            Proxy(IBinder iBinder) {
                this.mRemote = iBinder;
            }

            @Override // android.os.IInterface
            public IBinder asBinder() {
                return this.mRemote;
            }

            public String getInterfaceDescriptor() {
                return IEuiccController.DESCRIPTOR;
            }

            @Override // com.android.internal.telephony.euicc.IEuiccController
            public void continueOperation(int i, Intent intent, Bundle bundle) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                try {
                    obtain.writeInterfaceToken(IEuiccController.DESCRIPTOR);
                    obtain.writeInt(i);
                    obtain.writeTypedObject(intent, 0);
                    obtain.writeTypedObject(bundle, 0);
                    this.mRemote.transact(1, obtain, null, 1);
                } finally {
                    obtain.recycle();
                }
            }

            @Override // com.android.internal.telephony.euicc.IEuiccController
            public void getDownloadableSubscriptionMetadata(int i, DownloadableSubscription downloadableSubscription, String str, PendingIntent pendingIntent) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                try {
                    obtain.writeInterfaceToken(IEuiccController.DESCRIPTOR);
                    obtain.writeInt(i);
                    obtain.writeTypedObject(downloadableSubscription, 0);
                    obtain.writeString(str);
                    obtain.writeTypedObject(pendingIntent, 0);
                    this.mRemote.transact(2, obtain, null, 1);
                } finally {
                    obtain.recycle();
                }
            }

            @Override // com.android.internal.telephony.euicc.IEuiccController
            public void getDefaultDownloadableSubscriptionList(int i, String str, PendingIntent pendingIntent) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                try {
                    obtain.writeInterfaceToken(IEuiccController.DESCRIPTOR);
                    obtain.writeInt(i);
                    obtain.writeString(str);
                    obtain.writeTypedObject(pendingIntent, 0);
                    this.mRemote.transact(3, obtain, null, 1);
                } finally {
                    obtain.recycle();
                }
            }

            @Override // com.android.internal.telephony.euicc.IEuiccController
            public String getEid(int i, String str) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(IEuiccController.DESCRIPTOR);
                    obtain.writeInt(i);
                    obtain.writeString(str);
                    this.mRemote.transact(4, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readString();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.android.internal.telephony.euicc.IEuiccController
            public int getOtaStatus(int i) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(IEuiccController.DESCRIPTOR);
                    obtain.writeInt(i);
                    this.mRemote.transact(5, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readInt();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.android.internal.telephony.euicc.IEuiccController
            public void downloadSubscription(int i, DownloadableSubscription downloadableSubscription, boolean z, String str, Bundle bundle, PendingIntent pendingIntent) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                try {
                    obtain.writeInterfaceToken(IEuiccController.DESCRIPTOR);
                    obtain.writeInt(i);
                    obtain.writeTypedObject(downloadableSubscription, 0);
                    obtain.writeBoolean(z);
                    obtain.writeString(str);
                    obtain.writeTypedObject(bundle, 0);
                    obtain.writeTypedObject(pendingIntent, 0);
                    this.mRemote.transact(6, obtain, null, 1);
                } finally {
                    obtain.recycle();
                }
            }

            @Override // com.android.internal.telephony.euicc.IEuiccController
            public EuiccInfo getEuiccInfo(int i) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(IEuiccController.DESCRIPTOR);
                    obtain.writeInt(i);
                    this.mRemote.transact(7, obtain, obtain2, 0);
                    obtain2.readException();
                    return (EuiccInfo) obtain2.readTypedObject(EuiccInfo.CREATOR);
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.android.internal.telephony.euicc.IEuiccController
            public void deleteSubscription(int i, int i2, String str, PendingIntent pendingIntent) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                try {
                    obtain.writeInterfaceToken(IEuiccController.DESCRIPTOR);
                    obtain.writeInt(i);
                    obtain.writeInt(i2);
                    obtain.writeString(str);
                    obtain.writeTypedObject(pendingIntent, 0);
                    this.mRemote.transact(8, obtain, null, 1);
                } finally {
                    obtain.recycle();
                }
            }

            @Override // com.android.internal.telephony.euicc.IEuiccController
            public void switchToSubscription(int i, int i2, String str, PendingIntent pendingIntent) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                try {
                    obtain.writeInterfaceToken(IEuiccController.DESCRIPTOR);
                    obtain.writeInt(i);
                    obtain.writeInt(i2);
                    obtain.writeString(str);
                    obtain.writeTypedObject(pendingIntent, 0);
                    this.mRemote.transact(9, obtain, null, 1);
                } finally {
                    obtain.recycle();
                }
            }

            @Override // com.android.internal.telephony.euicc.IEuiccController
            public void switchToSubscriptionWithPort(int i, int i2, int i3, String str, PendingIntent pendingIntent) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                try {
                    obtain.writeInterfaceToken(IEuiccController.DESCRIPTOR);
                    obtain.writeInt(i);
                    obtain.writeInt(i2);
                    obtain.writeInt(i3);
                    obtain.writeString(str);
                    obtain.writeTypedObject(pendingIntent, 0);
                    this.mRemote.transact(10, obtain, null, 1);
                } finally {
                    obtain.recycle();
                }
            }

            @Override // com.android.internal.telephony.euicc.IEuiccController
            public void updateSubscriptionNickname(int i, int i2, String str, String str2, PendingIntent pendingIntent) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                try {
                    obtain.writeInterfaceToken(IEuiccController.DESCRIPTOR);
                    obtain.writeInt(i);
                    obtain.writeInt(i2);
                    obtain.writeString(str);
                    obtain.writeString(str2);
                    obtain.writeTypedObject(pendingIntent, 0);
                    this.mRemote.transact(11, obtain, null, 1);
                } finally {
                    obtain.recycle();
                }
            }

            @Override // com.android.internal.telephony.euicc.IEuiccController
            public void eraseSubscriptions(int i, PendingIntent pendingIntent) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                try {
                    obtain.writeInterfaceToken(IEuiccController.DESCRIPTOR);
                    obtain.writeInt(i);
                    obtain.writeTypedObject(pendingIntent, 0);
                    this.mRemote.transact(12, obtain, null, 1);
                } finally {
                    obtain.recycle();
                }
            }

            @Override // com.android.internal.telephony.euicc.IEuiccController
            public void eraseSubscriptionsWithOptions(int i, int i2, PendingIntent pendingIntent) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                try {
                    obtain.writeInterfaceToken(IEuiccController.DESCRIPTOR);
                    obtain.writeInt(i);
                    obtain.writeInt(i2);
                    obtain.writeTypedObject(pendingIntent, 0);
                    this.mRemote.transact(13, obtain, null, 1);
                } finally {
                    obtain.recycle();
                }
            }

            @Override // com.android.internal.telephony.euicc.IEuiccController
            public void retainSubscriptionsForFactoryReset(int i, PendingIntent pendingIntent) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                try {
                    obtain.writeInterfaceToken(IEuiccController.DESCRIPTOR);
                    obtain.writeInt(i);
                    obtain.writeTypedObject(pendingIntent, 0);
                    this.mRemote.transact(14, obtain, null, 1);
                } finally {
                    obtain.recycle();
                }
            }

            @Override // com.android.internal.telephony.euicc.IEuiccController
            public void setSupportedCountries(boolean z, List<String> list) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(IEuiccController.DESCRIPTOR);
                    obtain.writeBoolean(z);
                    obtain.writeStringList(list);
                    this.mRemote.transact(15, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.android.internal.telephony.euicc.IEuiccController
            public List<String> getSupportedCountries(boolean z) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(IEuiccController.DESCRIPTOR);
                    obtain.writeBoolean(z);
                    this.mRemote.transact(16, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.createStringArrayList();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.android.internal.telephony.euicc.IEuiccController
            public boolean isSupportedCountry(String str) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(IEuiccController.DESCRIPTOR);
                    obtain.writeString(str);
                    this.mRemote.transact(17, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readBoolean();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.android.internal.telephony.euicc.IEuiccController
            public boolean isSimPortAvailable(int i, int i2, String str) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(IEuiccController.DESCRIPTOR);
                    obtain.writeInt(i);
                    obtain.writeInt(i2);
                    obtain.writeString(str);
                    this.mRemote.transact(18, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readBoolean();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.android.internal.telephony.euicc.IEuiccController
            public boolean hasCarrierPrivilegesForPackageOnAnyPhone(String str) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(IEuiccController.DESCRIPTOR);
                    obtain.writeString(str);
                    this.mRemote.transact(19, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readBoolean();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.android.internal.telephony.euicc.IEuiccController
            public boolean isCompatChangeEnabled(String str, long j) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(IEuiccController.DESCRIPTOR);
                    obtain.writeString(str);
                    obtain.writeLong(j);
                    this.mRemote.transact(20, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readBoolean();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.android.internal.telephony.euicc.IEuiccController
            public void setPsimConversionSupportedCarriers(int[] iArr) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(IEuiccController.DESCRIPTOR);
                    obtain.writeIntArray(iArr);
                    this.mRemote.transact(21, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.android.internal.telephony.euicc.IEuiccController
            public boolean isPsimConversionSupported(int i) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(IEuiccController.DESCRIPTOR);
                    obtain.writeInt(i);
                    this.mRemote.transact(22, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readBoolean();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.android.internal.telephony.euicc.IEuiccController
            public long getAvailableMemoryInBytes(int i, String str) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(IEuiccController.DESCRIPTOR);
                    obtain.writeInt(i);
                    obtain.writeString(str);
                    this.mRemote.transact(23, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readLong();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }
        }
    }
}
