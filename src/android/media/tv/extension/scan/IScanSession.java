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
            IInterface queryLocalInterface = iBinder.queryLocalInterface("android.media.tv.extension.scan.IScanSession");
            if (queryLocalInterface != null && (queryLocalInterface instanceof IScanSession)) {
                return (IScanSession) queryLocalInterface;
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
                    int readInt = parcel.readInt();
                    String readString = parcel.readString();
                    String readString2 = parcel.readString();
                    int[] createIntArray = parcel.createIntArray();
                    String readString3 = parcel.readString();
                    String readString4 = parcel.readString();
                    Bundle bundle = (Bundle) parcel.readTypedObject(Bundle.CREATOR);
                    parcel.enforceNoDataAvail();
                    int startScan = startScan(readInt, readString, readString2, createIntArray, readString3, readString4, bundle);
                    parcel2.writeNoException();
                    parcel2.writeInt(startScan);
                    return true;
                case 2:
                    int resetScan = resetScan();
                    parcel2.writeNoException();
                    parcel2.writeInt(resetScan);
                    return true;
                case 3:
                    int cancelScan = cancelScan();
                    parcel2.writeNoException();
                    parcel2.writeInt(cancelScan);
                    return true;
                case 4:
                    String[] availableExtensionInterfaceNames = getAvailableExtensionInterfaceNames();
                    parcel2.writeNoException();
                    parcel2.writeStringArray(availableExtensionInterfaceNames);
                    return true;
                case 5:
                    String readString5 = parcel.readString();
                    parcel.enforceNoDataAvail();
                    IBinder extensionInterface = getExtensionInterface(readString5);
                    parcel2.writeNoException();
                    parcel2.writeStrongBinder(extensionInterface);
                    return true;
                case 6:
                    Bundle bundle2 = (Bundle) parcel.readTypedObject(Bundle.CREATOR);
                    parcel.enforceNoDataAvail();
                    int clearServiceList = clearServiceList(bundle2);
                    parcel2.writeNoException();
                    parcel2.writeInt(clearServiceList);
                    return true;
                case 7:
                    int storeServiceList = storeServiceList();
                    parcel2.writeNoException();
                    parcel2.writeInt(storeServiceList);
                    return true;
                case 8:
                    String readString6 = parcel.readString();
                    String[] createStringArray = parcel.createStringArray();
                    parcel.enforceNoDataAvail();
                    Bundle serviceInfo = getServiceInfo(readString6, createStringArray);
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
                    String[] createStringArray2 = parcel.createStringArray();
                    parcel.enforceNoDataAvail();
                    Bundle serviceInfoList = getServiceInfoList(bundle3, createStringArray2);
                    parcel2.writeNoException();
                    parcel2.writeTypedObject(serviceInfoList, 1);
                    return true;
                case 11:
                    Bundle bundle4 = (Bundle) parcel.readTypedObject(Bundle.CREATOR);
                    parcel.enforceNoDataAvail();
                    int updateServiceInfo = updateServiceInfo(bundle4);
                    parcel2.writeNoException();
                    parcel2.writeInt(updateServiceInfo);
                    return true;
                case 12:
                    Bundle[] bundleArr = (Bundle[]) parcel.createTypedArray(Bundle.CREATOR);
                    parcel.enforceNoDataAvail();
                    int updateServiceInfoByList = updateServiceInfoByList(bundleArr);
                    parcel2.writeNoException();
                    parcel2.writeInt(updateServiceInfoByList);
                    return true;
                case 13:
                    Bundle serviceLists = getServiceLists();
                    parcel2.writeNoException();
                    parcel2.writeTypedObject(serviceLists, 1);
                    return true;
                case 14:
                    int readInt2 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    int serviceList = setServiceList(readInt2);
                    parcel2.writeNoException();
                    parcel2.writeInt(serviceList);
                    return true;
                case 15:
                    Bundle packageData = getPackageData();
                    parcel2.writeNoException();
                    parcel2.writeTypedObject(packageData, 1);
                    return true;
                case 16:
                    String readString7 = parcel.readString();
                    parcel.enforceNoDataAvail();
                    int i3 = setPackage(readString7);
                    parcel2.writeNoException();
                    parcel2.writeInt(i3);
                    return true;
                case 17:
                    Bundle countryRegionData = getCountryRegionData();
                    parcel2.writeNoException();
                    parcel2.writeTypedObject(countryRegionData, 1);
                    return true;
                case 18:
                    String readString8 = parcel.readString();
                    parcel.enforceNoDataAvail();
                    int countryRegion = setCountryRegion(readString8);
                    parcel2.writeNoException();
                    parcel2.writeInt(countryRegion);
                    return true;
                case 19:
                    Bundle regionData = getRegionData();
                    parcel2.writeNoException();
                    parcel2.writeTypedObject(regionData, 1);
                    return true;
                case 20:
                    String readString9 = parcel.readString();
                    parcel.enforceNoDataAvail();
                    int region = setRegion(readString9);
                    parcel2.writeNoException();
                    parcel2.writeInt(region);
                    return true;
                case 21:
                    String sessionToken = getSessionToken();
                    parcel2.writeNoException();
                    parcel2.writeString(sessionToken);
                    return true;
                case 22:
                    int release = release();
                    parcel2.writeNoException();
                    parcel2.writeInt(release);
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
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken("android.media.tv.extension.scan.IScanSession");
                    obtain.writeInt(i);
                    obtain.writeString(str);
                    obtain.writeString(str2);
                    obtain.writeIntArray(iArr);
                    obtain.writeString(str3);
                    obtain.writeString(str4);
                    obtain.writeTypedObject(bundle, 0);
                    this.mRemote.transact(1, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readInt();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.media.tv.extension.scan.IScanSession
            public int resetScan() throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken("android.media.tv.extension.scan.IScanSession");
                    this.mRemote.transact(2, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readInt();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.media.tv.extension.scan.IScanSession
            public int cancelScan() throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken("android.media.tv.extension.scan.IScanSession");
                    this.mRemote.transact(3, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readInt();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.media.tv.extension.scan.IScanSession
            public String[] getAvailableExtensionInterfaceNames() throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken("android.media.tv.extension.scan.IScanSession");
                    this.mRemote.transact(4, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.createStringArray();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.media.tv.extension.scan.IScanSession
            public IBinder getExtensionInterface(String str) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken("android.media.tv.extension.scan.IScanSession");
                    obtain.writeString(str);
                    this.mRemote.transact(5, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readStrongBinder();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.media.tv.extension.scan.IScanSession
            public int clearServiceList(Bundle bundle) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken("android.media.tv.extension.scan.IScanSession");
                    obtain.writeTypedObject(bundle, 0);
                    this.mRemote.transact(6, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readInt();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.media.tv.extension.scan.IScanSession
            public int storeServiceList() throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken("android.media.tv.extension.scan.IScanSession");
                    this.mRemote.transact(7, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readInt();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.media.tv.extension.scan.IScanSession
            public Bundle getServiceInfo(String str, String[] strArr) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken("android.media.tv.extension.scan.IScanSession");
                    obtain.writeString(str);
                    obtain.writeStringArray(strArr);
                    this.mRemote.transact(8, obtain, obtain2, 0);
                    obtain2.readException();
                    return (Bundle) obtain2.readTypedObject(Bundle.CREATOR);
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.media.tv.extension.scan.IScanSession
            public String[] getServiceInfoIdList() throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken("android.media.tv.extension.scan.IScanSession");
                    this.mRemote.transact(9, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.createStringArray();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.media.tv.extension.scan.IScanSession
            public Bundle getServiceInfoList(Bundle bundle, String[] strArr) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken("android.media.tv.extension.scan.IScanSession");
                    obtain.writeTypedObject(bundle, 0);
                    obtain.writeStringArray(strArr);
                    this.mRemote.transact(10, obtain, obtain2, 0);
                    obtain2.readException();
                    return (Bundle) obtain2.readTypedObject(Bundle.CREATOR);
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.media.tv.extension.scan.IScanSession
            public int updateServiceInfo(Bundle bundle) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken("android.media.tv.extension.scan.IScanSession");
                    obtain.writeTypedObject(bundle, 0);
                    this.mRemote.transact(11, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readInt();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.media.tv.extension.scan.IScanSession
            public int updateServiceInfoByList(Bundle[] bundleArr) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken("android.media.tv.extension.scan.IScanSession");
                    obtain.writeTypedArray(bundleArr, 0);
                    this.mRemote.transact(12, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readInt();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.media.tv.extension.scan.IScanSession
            public Bundle getServiceLists() throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken("android.media.tv.extension.scan.IScanSession");
                    this.mRemote.transact(13, obtain, obtain2, 0);
                    obtain2.readException();
                    return (Bundle) obtain2.readTypedObject(Bundle.CREATOR);
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.media.tv.extension.scan.IScanSession
            public int setServiceList(int i) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken("android.media.tv.extension.scan.IScanSession");
                    obtain.writeInt(i);
                    this.mRemote.transact(14, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readInt();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.media.tv.extension.scan.IScanSession
            public Bundle getPackageData() throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken("android.media.tv.extension.scan.IScanSession");
                    this.mRemote.transact(15, obtain, obtain2, 0);
                    obtain2.readException();
                    return (Bundle) obtain2.readTypedObject(Bundle.CREATOR);
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.media.tv.extension.scan.IScanSession
            public int setPackage(String str) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken("android.media.tv.extension.scan.IScanSession");
                    obtain.writeString(str);
                    this.mRemote.transact(16, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readInt();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.media.tv.extension.scan.IScanSession
            public Bundle getCountryRegionData() throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken("android.media.tv.extension.scan.IScanSession");
                    this.mRemote.transact(17, obtain, obtain2, 0);
                    obtain2.readException();
                    return (Bundle) obtain2.readTypedObject(Bundle.CREATOR);
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.media.tv.extension.scan.IScanSession
            public int setCountryRegion(String str) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken("android.media.tv.extension.scan.IScanSession");
                    obtain.writeString(str);
                    this.mRemote.transact(18, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readInt();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.media.tv.extension.scan.IScanSession
            public Bundle getRegionData() throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken("android.media.tv.extension.scan.IScanSession");
                    this.mRemote.transact(19, obtain, obtain2, 0);
                    obtain2.readException();
                    return (Bundle) obtain2.readTypedObject(Bundle.CREATOR);
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.media.tv.extension.scan.IScanSession
            public int setRegion(String str) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken("android.media.tv.extension.scan.IScanSession");
                    obtain.writeString(str);
                    this.mRemote.transact(20, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readInt();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.media.tv.extension.scan.IScanSession
            public String getSessionToken() throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken("android.media.tv.extension.scan.IScanSession");
                    this.mRemote.transact(21, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readString();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.media.tv.extension.scan.IScanSession
            public int release() throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken("android.media.tv.extension.scan.IScanSession");
                    this.mRemote.transact(22, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readInt();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }
        }
    }
}
