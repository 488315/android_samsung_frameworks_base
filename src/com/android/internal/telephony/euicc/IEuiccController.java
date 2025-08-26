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
            IInterface iInterfaceQueryLocalInterface = iBinder.queryLocalInterface(IEuiccController.DESCRIPTOR);
            if (iInterfaceQueryLocalInterface != null && (iInterfaceQueryLocalInterface instanceof IEuiccController)) {
                return (IEuiccController) iInterfaceQueryLocalInterface;
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
                    int i3 = parcel.readInt();
                    Intent intent = (Intent) parcel.readTypedObject(Intent.CREATOR);
                    Bundle bundle = (Bundle) parcel.readTypedObject(Bundle.CREATOR);
                    parcel.enforceNoDataAvail();
                    continueOperation(i3, intent, bundle);
                    return true;
                case 2:
                    int i4 = parcel.readInt();
                    DownloadableSubscription downloadableSubscription = (DownloadableSubscription) parcel.readTypedObject(DownloadableSubscription.CREATOR);
                    String string = parcel.readString();
                    PendingIntent pendingIntent = (PendingIntent) parcel.readTypedObject(PendingIntent.CREATOR);
                    parcel.enforceNoDataAvail();
                    getDownloadableSubscriptionMetadata(i4, downloadableSubscription, string, pendingIntent);
                    return true;
                case 3:
                    int i5 = parcel.readInt();
                    String string2 = parcel.readString();
                    PendingIntent pendingIntent2 = (PendingIntent) parcel.readTypedObject(PendingIntent.CREATOR);
                    parcel.enforceNoDataAvail();
                    getDefaultDownloadableSubscriptionList(i5, string2, pendingIntent2);
                    return true;
                case 4:
                    int i6 = parcel.readInt();
                    String string3 = parcel.readString();
                    parcel.enforceNoDataAvail();
                    String eid = getEid(i6, string3);
                    parcel2.writeNoException();
                    parcel2.writeString(eid);
                    return true;
                case 5:
                    int i7 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    int otaStatus = getOtaStatus(i7);
                    parcel2.writeNoException();
                    parcel2.writeInt(otaStatus);
                    return true;
                case 6:
                    int i8 = parcel.readInt();
                    DownloadableSubscription downloadableSubscription2 = (DownloadableSubscription) parcel.readTypedObject(DownloadableSubscription.CREATOR);
                    boolean z = parcel.readBoolean();
                    String string4 = parcel.readString();
                    Bundle bundle2 = (Bundle) parcel.readTypedObject(Bundle.CREATOR);
                    PendingIntent pendingIntent3 = (PendingIntent) parcel.readTypedObject(PendingIntent.CREATOR);
                    parcel.enforceNoDataAvail();
                    downloadSubscription(i8, downloadableSubscription2, z, string4, bundle2, pendingIntent3);
                    return true;
                case 7:
                    int i9 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    EuiccInfo euiccInfo = getEuiccInfo(i9);
                    parcel2.writeNoException();
                    parcel2.writeTypedObject(euiccInfo, 1);
                    return true;
                case 8:
                    int i10 = parcel.readInt();
                    int i11 = parcel.readInt();
                    String string5 = parcel.readString();
                    PendingIntent pendingIntent4 = (PendingIntent) parcel.readTypedObject(PendingIntent.CREATOR);
                    parcel.enforceNoDataAvail();
                    deleteSubscription(i10, i11, string5, pendingIntent4);
                    return true;
                case 9:
                    int i12 = parcel.readInt();
                    int i13 = parcel.readInt();
                    String string6 = parcel.readString();
                    PendingIntent pendingIntent5 = (PendingIntent) parcel.readTypedObject(PendingIntent.CREATOR);
                    parcel.enforceNoDataAvail();
                    switchToSubscription(i12, i13, string6, pendingIntent5);
                    return true;
                case 10:
                    int i14 = parcel.readInt();
                    int i15 = parcel.readInt();
                    int i16 = parcel.readInt();
                    String string7 = parcel.readString();
                    PendingIntent pendingIntent6 = (PendingIntent) parcel.readTypedObject(PendingIntent.CREATOR);
                    parcel.enforceNoDataAvail();
                    switchToSubscriptionWithPort(i14, i15, i16, string7, pendingIntent6);
                    return true;
                case 11:
                    int i17 = parcel.readInt();
                    int i18 = parcel.readInt();
                    String string8 = parcel.readString();
                    String string9 = parcel.readString();
                    PendingIntent pendingIntent7 = (PendingIntent) parcel.readTypedObject(PendingIntent.CREATOR);
                    parcel.enforceNoDataAvail();
                    updateSubscriptionNickname(i17, i18, string8, string9, pendingIntent7);
                    return true;
                case 12:
                    int i19 = parcel.readInt();
                    PendingIntent pendingIntent8 = (PendingIntent) parcel.readTypedObject(PendingIntent.CREATOR);
                    parcel.enforceNoDataAvail();
                    eraseSubscriptions(i19, pendingIntent8);
                    return true;
                case 13:
                    int i20 = parcel.readInt();
                    int i21 = parcel.readInt();
                    PendingIntent pendingIntent9 = (PendingIntent) parcel.readTypedObject(PendingIntent.CREATOR);
                    parcel.enforceNoDataAvail();
                    eraseSubscriptionsWithOptions(i20, i21, pendingIntent9);
                    return true;
                case 14:
                    int i22 = parcel.readInt();
                    PendingIntent pendingIntent10 = (PendingIntent) parcel.readTypedObject(PendingIntent.CREATOR);
                    parcel.enforceNoDataAvail();
                    retainSubscriptionsForFactoryReset(i22, pendingIntent10);
                    return true;
                case 15:
                    boolean z2 = parcel.readBoolean();
                    ArrayList<String> arrayListCreateStringArrayList = parcel.createStringArrayList();
                    parcel.enforceNoDataAvail();
                    setSupportedCountries(z2, arrayListCreateStringArrayList);
                    parcel2.writeNoException();
                    return true;
                case 16:
                    boolean z3 = parcel.readBoolean();
                    parcel.enforceNoDataAvail();
                    List<String> supportedCountries = getSupportedCountries(z3);
                    parcel2.writeNoException();
                    parcel2.writeStringList(supportedCountries);
                    return true;
                case 17:
                    String string10 = parcel.readString();
                    parcel.enforceNoDataAvail();
                    boolean zIsSupportedCountry = isSupportedCountry(string10);
                    parcel2.writeNoException();
                    parcel2.writeBoolean(zIsSupportedCountry);
                    return true;
                case 18:
                    int i23 = parcel.readInt();
                    int i24 = parcel.readInt();
                    String string11 = parcel.readString();
                    parcel.enforceNoDataAvail();
                    boolean zIsSimPortAvailable = isSimPortAvailable(i23, i24, string11);
                    parcel2.writeNoException();
                    parcel2.writeBoolean(zIsSimPortAvailable);
                    return true;
                case 19:
                    String string12 = parcel.readString();
                    parcel.enforceNoDataAvail();
                    boolean zHasCarrierPrivilegesForPackageOnAnyPhone = hasCarrierPrivilegesForPackageOnAnyPhone(string12);
                    parcel2.writeNoException();
                    parcel2.writeBoolean(zHasCarrierPrivilegesForPackageOnAnyPhone);
                    return true;
                case 20:
                    String string13 = parcel.readString();
                    long j = parcel.readLong();
                    parcel.enforceNoDataAvail();
                    boolean zIsCompatChangeEnabled = isCompatChangeEnabled(string13, j);
                    parcel2.writeNoException();
                    parcel2.writeBoolean(zIsCompatChangeEnabled);
                    return true;
                case 21:
                    int[] iArrCreateIntArray = parcel.createIntArray();
                    parcel.enforceNoDataAvail();
                    setPsimConversionSupportedCarriers(iArrCreateIntArray);
                    parcel2.writeNoException();
                    return true;
                case 22:
                    int i25 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    boolean zIsPsimConversionSupported = isPsimConversionSupported(i25);
                    parcel2.writeNoException();
                    parcel2.writeBoolean(zIsPsimConversionSupported);
                    return true;
                case 23:
                    int i26 = parcel.readInt();
                    String string14 = parcel.readString();
                    parcel.enforceNoDataAvail();
                    long availableMemoryInBytes = getAvailableMemoryInBytes(i26, string14);
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
                Parcel parcelObtain = Parcel.obtain(asBinder());
                try {
                    parcelObtain.writeInterfaceToken(IEuiccController.DESCRIPTOR);
                    parcelObtain.writeInt(i);
                    parcelObtain.writeTypedObject(intent, 0);
                    parcelObtain.writeTypedObject(bundle, 0);
                    this.mRemote.transact(1, parcelObtain, null, 1);
                } finally {
                    parcelObtain.recycle();
                }
            }

            @Override // com.android.internal.telephony.euicc.IEuiccController
            public void getDownloadableSubscriptionMetadata(int i, DownloadableSubscription downloadableSubscription, String str, PendingIntent pendingIntent) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                try {
                    parcelObtain.writeInterfaceToken(IEuiccController.DESCRIPTOR);
                    parcelObtain.writeInt(i);
                    parcelObtain.writeTypedObject(downloadableSubscription, 0);
                    parcelObtain.writeString(str);
                    parcelObtain.writeTypedObject(pendingIntent, 0);
                    this.mRemote.transact(2, parcelObtain, null, 1);
                } finally {
                    parcelObtain.recycle();
                }
            }

            @Override // com.android.internal.telephony.euicc.IEuiccController
            public void getDefaultDownloadableSubscriptionList(int i, String str, PendingIntent pendingIntent) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                try {
                    parcelObtain.writeInterfaceToken(IEuiccController.DESCRIPTOR);
                    parcelObtain.writeInt(i);
                    parcelObtain.writeString(str);
                    parcelObtain.writeTypedObject(pendingIntent, 0);
                    this.mRemote.transact(3, parcelObtain, null, 1);
                } finally {
                    parcelObtain.recycle();
                }
            }

            @Override // com.android.internal.telephony.euicc.IEuiccController
            public String getEid(int i, String str) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(IEuiccController.DESCRIPTOR);
                    parcelObtain.writeInt(i);
                    parcelObtain.writeString(str);
                    this.mRemote.transact(4, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return parcelObtain2.readString();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // com.android.internal.telephony.euicc.IEuiccController
            public int getOtaStatus(int i) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(IEuiccController.DESCRIPTOR);
                    parcelObtain.writeInt(i);
                    this.mRemote.transact(5, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return parcelObtain2.readInt();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // com.android.internal.telephony.euicc.IEuiccController
            public void downloadSubscription(int i, DownloadableSubscription downloadableSubscription, boolean z, String str, Bundle bundle, PendingIntent pendingIntent) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                try {
                    parcelObtain.writeInterfaceToken(IEuiccController.DESCRIPTOR);
                    parcelObtain.writeInt(i);
                    parcelObtain.writeTypedObject(downloadableSubscription, 0);
                    parcelObtain.writeBoolean(z);
                    parcelObtain.writeString(str);
                    parcelObtain.writeTypedObject(bundle, 0);
                    parcelObtain.writeTypedObject(pendingIntent, 0);
                    this.mRemote.transact(6, parcelObtain, null, 1);
                } finally {
                    parcelObtain.recycle();
                }
            }

            @Override // com.android.internal.telephony.euicc.IEuiccController
            public EuiccInfo getEuiccInfo(int i) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(IEuiccController.DESCRIPTOR);
                    parcelObtain.writeInt(i);
                    this.mRemote.transact(7, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return (EuiccInfo) parcelObtain2.readTypedObject(EuiccInfo.CREATOR);
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // com.android.internal.telephony.euicc.IEuiccController
            public void deleteSubscription(int i, int i2, String str, PendingIntent pendingIntent) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                try {
                    parcelObtain.writeInterfaceToken(IEuiccController.DESCRIPTOR);
                    parcelObtain.writeInt(i);
                    parcelObtain.writeInt(i2);
                    parcelObtain.writeString(str);
                    parcelObtain.writeTypedObject(pendingIntent, 0);
                    this.mRemote.transact(8, parcelObtain, null, 1);
                } finally {
                    parcelObtain.recycle();
                }
            }

            @Override // com.android.internal.telephony.euicc.IEuiccController
            public void switchToSubscription(int i, int i2, String str, PendingIntent pendingIntent) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                try {
                    parcelObtain.writeInterfaceToken(IEuiccController.DESCRIPTOR);
                    parcelObtain.writeInt(i);
                    parcelObtain.writeInt(i2);
                    parcelObtain.writeString(str);
                    parcelObtain.writeTypedObject(pendingIntent, 0);
                    this.mRemote.transact(9, parcelObtain, null, 1);
                } finally {
                    parcelObtain.recycle();
                }
            }

            @Override // com.android.internal.telephony.euicc.IEuiccController
            public void switchToSubscriptionWithPort(int i, int i2, int i3, String str, PendingIntent pendingIntent) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                try {
                    parcelObtain.writeInterfaceToken(IEuiccController.DESCRIPTOR);
                    parcelObtain.writeInt(i);
                    parcelObtain.writeInt(i2);
                    parcelObtain.writeInt(i3);
                    parcelObtain.writeString(str);
                    parcelObtain.writeTypedObject(pendingIntent, 0);
                    this.mRemote.transact(10, parcelObtain, null, 1);
                } finally {
                    parcelObtain.recycle();
                }
            }

            @Override // com.android.internal.telephony.euicc.IEuiccController
            public void updateSubscriptionNickname(int i, int i2, String str, String str2, PendingIntent pendingIntent) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                try {
                    parcelObtain.writeInterfaceToken(IEuiccController.DESCRIPTOR);
                    parcelObtain.writeInt(i);
                    parcelObtain.writeInt(i2);
                    parcelObtain.writeString(str);
                    parcelObtain.writeString(str2);
                    parcelObtain.writeTypedObject(pendingIntent, 0);
                    this.mRemote.transact(11, parcelObtain, null, 1);
                } finally {
                    parcelObtain.recycle();
                }
            }

            @Override // com.android.internal.telephony.euicc.IEuiccController
            public void eraseSubscriptions(int i, PendingIntent pendingIntent) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                try {
                    parcelObtain.writeInterfaceToken(IEuiccController.DESCRIPTOR);
                    parcelObtain.writeInt(i);
                    parcelObtain.writeTypedObject(pendingIntent, 0);
                    this.mRemote.transact(12, parcelObtain, null, 1);
                } finally {
                    parcelObtain.recycle();
                }
            }

            @Override // com.android.internal.telephony.euicc.IEuiccController
            public void eraseSubscriptionsWithOptions(int i, int i2, PendingIntent pendingIntent) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                try {
                    parcelObtain.writeInterfaceToken(IEuiccController.DESCRIPTOR);
                    parcelObtain.writeInt(i);
                    parcelObtain.writeInt(i2);
                    parcelObtain.writeTypedObject(pendingIntent, 0);
                    this.mRemote.transact(13, parcelObtain, null, 1);
                } finally {
                    parcelObtain.recycle();
                }
            }

            @Override // com.android.internal.telephony.euicc.IEuiccController
            public void retainSubscriptionsForFactoryReset(int i, PendingIntent pendingIntent) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                try {
                    parcelObtain.writeInterfaceToken(IEuiccController.DESCRIPTOR);
                    parcelObtain.writeInt(i);
                    parcelObtain.writeTypedObject(pendingIntent, 0);
                    this.mRemote.transact(14, parcelObtain, null, 1);
                } finally {
                    parcelObtain.recycle();
                }
            }

            @Override // com.android.internal.telephony.euicc.IEuiccController
            public void setSupportedCountries(boolean z, List<String> list) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(IEuiccController.DESCRIPTOR);
                    parcelObtain.writeBoolean(z);
                    parcelObtain.writeStringList(list);
                    this.mRemote.transact(15, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // com.android.internal.telephony.euicc.IEuiccController
            public List<String> getSupportedCountries(boolean z) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(IEuiccController.DESCRIPTOR);
                    parcelObtain.writeBoolean(z);
                    this.mRemote.transact(16, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return parcelObtain2.createStringArrayList();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // com.android.internal.telephony.euicc.IEuiccController
            public boolean isSupportedCountry(String str) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(IEuiccController.DESCRIPTOR);
                    parcelObtain.writeString(str);
                    this.mRemote.transact(17, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return parcelObtain2.readBoolean();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // com.android.internal.telephony.euicc.IEuiccController
            public boolean isSimPortAvailable(int i, int i2, String str) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(IEuiccController.DESCRIPTOR);
                    parcelObtain.writeInt(i);
                    parcelObtain.writeInt(i2);
                    parcelObtain.writeString(str);
                    this.mRemote.transact(18, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return parcelObtain2.readBoolean();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // com.android.internal.telephony.euicc.IEuiccController
            public boolean hasCarrierPrivilegesForPackageOnAnyPhone(String str) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(IEuiccController.DESCRIPTOR);
                    parcelObtain.writeString(str);
                    this.mRemote.transact(19, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return parcelObtain2.readBoolean();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // com.android.internal.telephony.euicc.IEuiccController
            public boolean isCompatChangeEnabled(String str, long j) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(IEuiccController.DESCRIPTOR);
                    parcelObtain.writeString(str);
                    parcelObtain.writeLong(j);
                    this.mRemote.transact(20, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return parcelObtain2.readBoolean();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // com.android.internal.telephony.euicc.IEuiccController
            public void setPsimConversionSupportedCarriers(int[] iArr) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(IEuiccController.DESCRIPTOR);
                    parcelObtain.writeIntArray(iArr);
                    this.mRemote.transact(21, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // com.android.internal.telephony.euicc.IEuiccController
            public boolean isPsimConversionSupported(int i) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(IEuiccController.DESCRIPTOR);
                    parcelObtain.writeInt(i);
                    this.mRemote.transact(22, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return parcelObtain2.readBoolean();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // com.android.internal.telephony.euicc.IEuiccController
            public long getAvailableMemoryInBytes(int i, String str) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(IEuiccController.DESCRIPTOR);
                    parcelObtain.writeInt(i);
                    parcelObtain.writeString(str);
                    this.mRemote.transact(23, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return parcelObtain2.readLong();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }
        }
    }
}
