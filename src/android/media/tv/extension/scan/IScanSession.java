package android.media.tv.extension.scan;

import android.os.Binder;
import android.os.Bundle;
import android.os.IBinder;
import android.os.IInterface;
import android.os.Parcel;
import android.os.RemoteException;

/* loaded from: classes3.dex */
public interface IScanSession extends IInterface {
    public static final String DESCRIPTOR = "android.media.tv.extension.scan.IScanSession";

    public static class Default implements IScanSession {
        @Override // android.os.IInterface
        public IBinder asBinder() {
            return null;
        }

        @Override // android.media.tv.extension.scan.IScanSession
        public int cancelScan() throws RemoteException {
            return 0;
        }

        @Override // android.media.tv.extension.scan.IScanSession
        public int clearServiceList(Bundle bundle) throws RemoteException {
            return 0;
        }

        @Override // android.media.tv.extension.scan.IScanSession
        public String[] getAvailableExtensionInterfaceNames() throws RemoteException {
            return null;
        }

        @Override // android.media.tv.extension.scan.IScanSession
        public Bundle getCountryRegionData() throws RemoteException {
            return null;
        }

        @Override // android.media.tv.extension.scan.IScanSession
        public IBinder getExtensionInterface(String str) throws RemoteException {
            return null;
        }

        @Override // android.media.tv.extension.scan.IScanSession
        public Bundle getPackageData() throws RemoteException {
            return null;
        }

        @Override // android.media.tv.extension.scan.IScanSession
        public Bundle getRegionData() throws RemoteException {
            return null;
        }

        @Override // android.media.tv.extension.scan.IScanSession
        public Bundle getServiceInfo(String str, String[] strArr) throws RemoteException {
            return null;
        }

        @Override // android.media.tv.extension.scan.IScanSession
        public String[] getServiceInfoIdList() throws RemoteException {
            return null;
        }

        @Override // android.media.tv.extension.scan.IScanSession
        public Bundle getServiceInfoList(Bundle bundle, String[] strArr) throws RemoteException {
            return null;
        }

        @Override // android.media.tv.extension.scan.IScanSession
        public Bundle getServiceLists() throws RemoteException {
            return null;
        }

        @Override // android.media.tv.extension.scan.IScanSession
        public String getSessionToken() throws RemoteException {
            return null;
        }

        @Override // android.media.tv.extension.scan.IScanSession
        public int release() throws RemoteException {
            return 0;
        }

        @Override // android.media.tv.extension.scan.IScanSession
        public int resetScan() throws RemoteException {
            return 0;
        }

        @Override // android.media.tv.extension.scan.IScanSession
        public int setCountryRegion(String str) throws RemoteException {
            return 0;
        }

        @Override // android.media.tv.extension.scan.IScanSession
        public int setPackage(String str) throws RemoteException {
            return 0;
        }

        @Override // android.media.tv.extension.scan.IScanSession
        public int setRegion(String str) throws RemoteException {
            return 0;
        }

        @Override // android.media.tv.extension.scan.IScanSession
        public int setServiceList(int i) throws RemoteException {
            return 0;
        }

        @Override // android.media.tv.extension.scan.IScanSession
        public int startScan(int i, String str, String str2, int[] iArr, String str3, String str4, Bundle bundle) throws RemoteException {
            return 0;
        }

        @Override // android.media.tv.extension.scan.IScanSession
        public int storeServiceList() throws RemoteException {
            return 0;
        }

        @Override // android.media.tv.extension.scan.IScanSession
        public int updateServiceInfo(Bundle bundle) throws RemoteException {
            return 0;
        }

        @Override // android.media.tv.extension.scan.IScanSession
        public int updateServiceInfoByList(Bundle[] bundleArr) throws RemoteException {
            return 0;
        }
    }

    int cancelScan() throws RemoteException;

    int clearServiceList(Bundle bundle) throws RemoteException;

    String[] getAvailableExtensionInterfaceNames() throws RemoteException;

    Bundle getCountryRegionData() throws RemoteException;

    IBinder getExtensionInterface(String str) throws RemoteException;

    Bundle getPackageData() throws RemoteException;

    Bundle getRegionData() throws RemoteException;

    Bundle getServiceInfo(String str, String[] strArr) throws RemoteException;

    String[] getServiceInfoIdList() throws RemoteException;

    Bundle getServiceInfoList(Bundle bundle, String[] strArr) throws RemoteException;

    Bundle getServiceLists() throws RemoteException;

    String getSessionToken() throws RemoteException;

    int release() throws RemoteException;

    int resetScan() throws RemoteException;

    int setCountryRegion(String str) throws RemoteException;

    int setPackage(String str) throws RemoteException;

    int setRegion(String str) throws RemoteException;

    int setServiceList(int i) throws RemoteException;

    int startScan(int i, String str, String str2, int[] iArr, String str3, String str4, Bundle bundle) throws RemoteException;

    int storeServiceList() throws RemoteException;

    int updateServiceInfo(Bundle bundle) throws RemoteException;

    int updateServiceInfoByList(Bundle[] bundleArr) throws RemoteException;

    public static abstract class Stub extends Binder implements IScanSession {
        static final int TRANSACTION_cancelScan = 3;
        static final int TRANSACTION_clearServiceList = 6;
        static final int TRANSACTION_getAvailableExtensionInterfaceNames = 4;
        static final int TRANSACTION_getCountryRegionData = 17;
        static final int TRANSACTION_getExtensionInterface = 5;
        static final int TRANSACTION_getPackageData = 15;
        static final int TRANSACTION_getRegionData = 19;
        static final int TRANSACTION_getServiceInfo = 8;
        static final int TRANSACTION_getServiceInfoIdList = 9;
        static final int TRANSACTION_getServiceInfoList = 10;
        static final int TRANSACTION_getServiceLists = 13;
        static final int TRANSACTION_getSessionToken = 21;
        static final int TRANSACTION_release = 22;
        static final int TRANSACTION_resetScan = 2;
        static final int TRANSACTION_setCountryRegion = 18;
        static final int TRANSACTION_setPackage = 16;
        static final int TRANSACTION_setRegion = 20;
        static final int TRANSACTION_setServiceList = 14;
        static final int TRANSACTION_startScan = 1;
        static final int TRANSACTION_storeServiceList = 7;
        static final int TRANSACTION_updateServiceInfo = 11;
        static final int TRANSACTION_updateServiceInfoByList = 12;

        @Override // android.os.IInterface
        public IBinder asBinder() {
            return this;
        }

        @Override // android.os.Binder
        public int getMaxTransactionId() {
            return 21;
        }

        public Stub() {
            attachInterface(this, "android.media.tv.extension.scan.IScanSession");
        }

        public static IScanSession asInterface(IBinder iBinder) {
            if (iBinder == null) {
                return null;
            }
            IInterface iInterfaceQueryLocalInterface = iBinder.queryLocalInterface("android.media.tv.extension.scan.IScanSession");
            if (iInterfaceQueryLocalInterface != null && (iInterfaceQueryLocalInterface instanceof IScanSession)) {
                return (IScanSession) iInterfaceQueryLocalInterface;
            }
            return new Proxy(iBinder);
        }

        public static String getDefaultTransactionName(int i) {
            switch (i) {
                case 1:
                    return "startScan";
                case 2:
                    return "resetScan";
                case 3:
                    return "cancelScan";
                case 4:
                    return "getAvailableExtensionInterfaceNames";
                case 5:
                    return "getExtensionInterface";
                case 6:
                    return "clearServiceList";
                case 7:
                    return "storeServiceList";
                case 8:
                    return "getServiceInfo";
                case 9:
                    return "getServiceInfoIdList";
                case 10:
                    return "getServiceInfoList";
                case 11:
                    return "updateServiceInfo";
                case 12:
                    return "updateServiceInfoByList";
                case 13:
                    return "getServiceLists";
                case 14:
                    return "setServiceList";
                case 15:
                    return "getPackageData";
                case 16:
                    return "setPackage";
                case 17:
                    return "getCountryRegionData";
                case 18:
                    return "setCountryRegion";
                case 19:
                    return "getRegionData";
                case 20:
                    return "setRegion";
                case 21:
                    return "getSessionToken";
                case 22:
                    return "release";
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
                parcel.enforceInterface("android.media.tv.extension.scan.IScanSession");
            }
            if (i == 1598968902) {
                parcel2.writeString("android.media.tv.extension.scan.IScanSession");
                return true;
            }
            switch (i) {
                case 1:
                    int i3 = parcel.readInt();
                    String string = parcel.readString();
                    String string2 = parcel.readString();
                    int[] iArrCreateIntArray = parcel.createIntArray();
                    String string3 = parcel.readString();
                    String string4 = parcel.readString();
                    Bundle bundle = (Bundle) parcel.readTypedObject(Bundle.CREATOR);
                    parcel.enforceNoDataAvail();
                    int iStartScan = startScan(i3, string, string2, iArrCreateIntArray, string3, string4, bundle);
                    parcel2.writeNoException();
                    parcel2.writeInt(iStartScan);
                    return true;
                case 2:
                    int iResetScan = resetScan();
                    parcel2.writeNoException();
                    parcel2.writeInt(iResetScan);
                    return true;
                case 3:
                    int iCancelScan = cancelScan();
                    parcel2.writeNoException();
                    parcel2.writeInt(iCancelScan);
                    return true;
                case 4:
                    String[] availableExtensionInterfaceNames = getAvailableExtensionInterfaceNames();
                    parcel2.writeNoException();
                    parcel2.writeStringArray(availableExtensionInterfaceNames);
                    return true;
                case 5:
                    String string5 = parcel.readString();
                    parcel.enforceNoDataAvail();
                    IBinder extensionInterface = getExtensionInterface(string5);
                    parcel2.writeNoException();
                    parcel2.writeStrongBinder(extensionInterface);
                    return true;
                case 6:
                    Bundle bundle2 = (Bundle) parcel.readTypedObject(Bundle.CREATOR);
                    parcel.enforceNoDataAvail();
                    int iClearServiceList = clearServiceList(bundle2);
                    parcel2.writeNoException();
                    parcel2.writeInt(iClearServiceList);
                    return true;
                case 7:
                    int iStoreServiceList = storeServiceList();
                    parcel2.writeNoException();
                    parcel2.writeInt(iStoreServiceList);
                    return true;
                case 8:
                    String string6 = parcel.readString();
                    String[] strArrCreateStringArray = parcel.createStringArray();
                    parcel.enforceNoDataAvail();
                    Bundle serviceInfo = getServiceInfo(string6, strArrCreateStringArray);
                    parcel2.writeNoException();
                    parcel2.writeTypedObject(serviceInfo, 1);
                    return true;
                case 9:
                    String[] serviceInfoIdList = getServiceInfoIdList();
                    parcel2.writeNoException();
                    parcel2.writeStringArray(serviceInfoIdList);
                    return true;
                case 10:
                    Bundle bundle3 = (Bundle) parcel.readTypedObject(Bundle.CREATOR);
                    String[] strArrCreateStringArray2 = parcel.createStringArray();
                    parcel.enforceNoDataAvail();
                    Bundle serviceInfoList = getServiceInfoList(bundle3, strArrCreateStringArray2);
                    parcel2.writeNoException();
                    parcel2.writeTypedObject(serviceInfoList, 1);
                    return true;
                case 11:
                    Bundle bundle4 = (Bundle) parcel.readTypedObject(Bundle.CREATOR);
                    parcel.enforceNoDataAvail();
                    int iUpdateServiceInfo = updateServiceInfo(bundle4);
                    parcel2.writeNoException();
                    parcel2.writeInt(iUpdateServiceInfo);
                    return true;
                case 12:
                    Bundle[] bundleArr = (Bundle[]) parcel.createTypedArray(Bundle.CREATOR);
                    parcel.enforceNoDataAvail();
                    int iUpdateServiceInfoByList = updateServiceInfoByList(bundleArr);
                    parcel2.writeNoException();
                    parcel2.writeInt(iUpdateServiceInfoByList);
                    return true;
                case 13:
                    Bundle serviceLists = getServiceLists();
                    parcel2.writeNoException();
                    parcel2.writeTypedObject(serviceLists, 1);
                    return true;
                case 14:
                    int i4 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    int serviceList = setServiceList(i4);
                    parcel2.writeNoException();
                    parcel2.writeInt(serviceList);
                    return true;
                case 15:
                    Bundle packageData = getPackageData();
                    parcel2.writeNoException();
                    parcel2.writeTypedObject(packageData, 1);
                    return true;
                case 16:
                    String string7 = parcel.readString();
                    parcel.enforceNoDataAvail();
                    int i5 = setPackage(string7);
                    parcel2.writeNoException();
                    parcel2.writeInt(i5);
                    return true;
                case 17:
                    Bundle countryRegionData = getCountryRegionData();
                    parcel2.writeNoException();
                    parcel2.writeTypedObject(countryRegionData, 1);
                    return true;
                case 18:
                    String string8 = parcel.readString();
                    parcel.enforceNoDataAvail();
                    int countryRegion = setCountryRegion(string8);
                    parcel2.writeNoException();
                    parcel2.writeInt(countryRegion);
                    return true;
                case 19:
                    Bundle regionData = getRegionData();
                    parcel2.writeNoException();
                    parcel2.writeTypedObject(regionData, 1);
                    return true;
                case 20:
                    String string9 = parcel.readString();
                    parcel.enforceNoDataAvail();
                    int region = setRegion(string9);
                    parcel2.writeNoException();
                    parcel2.writeInt(region);
                    return true;
                case 21:
                    String sessionToken = getSessionToken();
                    parcel2.writeNoException();
                    parcel2.writeString(sessionToken);
                    return true;
                case 22:
                    int iRelease = release();
                    parcel2.writeNoException();
                    parcel2.writeInt(iRelease);
                    return true;
                default:
                    return super.onTransact(i, parcel, parcel2, i2);
            }
        }

        private static class Proxy implements IScanSession {
            private IBinder mRemote;

            Proxy(IBinder iBinder) {
                this.mRemote = iBinder;
            }

            @Override // android.os.IInterface
            public IBinder asBinder() {
                return this.mRemote;
            }

            public String getInterfaceDescriptor() {
                return "android.media.tv.extension.scan.IScanSession";
            }

            @Override // android.media.tv.extension.scan.IScanSession
            public int startScan(int i, String str, String str2, int[] iArr, String str3, String str4, Bundle bundle) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken("android.media.tv.extension.scan.IScanSession");
                    parcelObtain.writeInt(i);
                    parcelObtain.writeString(str);
                    parcelObtain.writeString(str2);
                    parcelObtain.writeIntArray(iArr);
                    parcelObtain.writeString(str3);
                    parcelObtain.writeString(str4);
                    parcelObtain.writeTypedObject(bundle, 0);
                    this.mRemote.transact(1, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return parcelObtain2.readInt();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.media.tv.extension.scan.IScanSession
            public int resetScan() throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken("android.media.tv.extension.scan.IScanSession");
                    this.mRemote.transact(2, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return parcelObtain2.readInt();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.media.tv.extension.scan.IScanSession
            public int cancelScan() throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken("android.media.tv.extension.scan.IScanSession");
                    this.mRemote.transact(3, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return parcelObtain2.readInt();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.media.tv.extension.scan.IScanSession
            public String[] getAvailableExtensionInterfaceNames() throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken("android.media.tv.extension.scan.IScanSession");
                    this.mRemote.transact(4, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return parcelObtain2.createStringArray();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.media.tv.extension.scan.IScanSession
            public IBinder getExtensionInterface(String str) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken("android.media.tv.extension.scan.IScanSession");
                    parcelObtain.writeString(str);
                    this.mRemote.transact(5, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return parcelObtain2.readStrongBinder();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.media.tv.extension.scan.IScanSession
            public int clearServiceList(Bundle bundle) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken("android.media.tv.extension.scan.IScanSession");
                    parcelObtain.writeTypedObject(bundle, 0);
                    this.mRemote.transact(6, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return parcelObtain2.readInt();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.media.tv.extension.scan.IScanSession
            public int storeServiceList() throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken("android.media.tv.extension.scan.IScanSession");
                    this.mRemote.transact(7, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return parcelObtain2.readInt();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.media.tv.extension.scan.IScanSession
            public Bundle getServiceInfo(String str, String[] strArr) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken("android.media.tv.extension.scan.IScanSession");
                    parcelObtain.writeString(str);
                    parcelObtain.writeStringArray(strArr);
                    this.mRemote.transact(8, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return (Bundle) parcelObtain2.readTypedObject(Bundle.CREATOR);
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.media.tv.extension.scan.IScanSession
            public String[] getServiceInfoIdList() throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken("android.media.tv.extension.scan.IScanSession");
                    this.mRemote.transact(9, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return parcelObtain2.createStringArray();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.media.tv.extension.scan.IScanSession
            public Bundle getServiceInfoList(Bundle bundle, String[] strArr) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken("android.media.tv.extension.scan.IScanSession");
                    parcelObtain.writeTypedObject(bundle, 0);
                    parcelObtain.writeStringArray(strArr);
                    this.mRemote.transact(10, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return (Bundle) parcelObtain2.readTypedObject(Bundle.CREATOR);
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.media.tv.extension.scan.IScanSession
            public int updateServiceInfo(Bundle bundle) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken("android.media.tv.extension.scan.IScanSession");
                    parcelObtain.writeTypedObject(bundle, 0);
                    this.mRemote.transact(11, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return parcelObtain2.readInt();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.media.tv.extension.scan.IScanSession
            public int updateServiceInfoByList(Bundle[] bundleArr) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken("android.media.tv.extension.scan.IScanSession");
                    parcelObtain.writeTypedArray(bundleArr, 0);
                    this.mRemote.transact(12, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return parcelObtain2.readInt();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.media.tv.extension.scan.IScanSession
            public Bundle getServiceLists() throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken("android.media.tv.extension.scan.IScanSession");
                    this.mRemote.transact(13, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return (Bundle) parcelObtain2.readTypedObject(Bundle.CREATOR);
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.media.tv.extension.scan.IScanSession
            public int setServiceList(int i) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken("android.media.tv.extension.scan.IScanSession");
                    parcelObtain.writeInt(i);
                    this.mRemote.transact(14, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return parcelObtain2.readInt();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.media.tv.extension.scan.IScanSession
            public Bundle getPackageData() throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken("android.media.tv.extension.scan.IScanSession");
                    this.mRemote.transact(15, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return (Bundle) parcelObtain2.readTypedObject(Bundle.CREATOR);
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.media.tv.extension.scan.IScanSession
            public int setPackage(String str) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken("android.media.tv.extension.scan.IScanSession");
                    parcelObtain.writeString(str);
                    this.mRemote.transact(16, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return parcelObtain2.readInt();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.media.tv.extension.scan.IScanSession
            public Bundle getCountryRegionData() throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken("android.media.tv.extension.scan.IScanSession");
                    this.mRemote.transact(17, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return (Bundle) parcelObtain2.readTypedObject(Bundle.CREATOR);
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.media.tv.extension.scan.IScanSession
            public int setCountryRegion(String str) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken("android.media.tv.extension.scan.IScanSession");
                    parcelObtain.writeString(str);
                    this.mRemote.transact(18, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return parcelObtain2.readInt();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.media.tv.extension.scan.IScanSession
            public Bundle getRegionData() throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken("android.media.tv.extension.scan.IScanSession");
                    this.mRemote.transact(19, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return (Bundle) parcelObtain2.readTypedObject(Bundle.CREATOR);
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.media.tv.extension.scan.IScanSession
            public int setRegion(String str) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken("android.media.tv.extension.scan.IScanSession");
                    parcelObtain.writeString(str);
                    this.mRemote.transact(20, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return parcelObtain2.readInt();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.media.tv.extension.scan.IScanSession
            public String getSessionToken() throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken("android.media.tv.extension.scan.IScanSession");
                    this.mRemote.transact(21, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return parcelObtain2.readString();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.media.tv.extension.scan.IScanSession
            public int release() throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken("android.media.tv.extension.scan.IScanSession");
                    this.mRemote.transact(22, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return parcelObtain2.readInt();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }
        }
    }
}
