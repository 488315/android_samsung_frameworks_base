package android.media.tv.extension.servicedb;

import android.media.tv.extension.servicedb.IServiceListEditListener;
import android.os.Binder;
import android.os.Bundle;
import android.os.IBinder;
import android.os.IInterface;
import android.os.Parcel;
import android.os.RemoteException;
import android.telephony.ims.RcsContactPresenceTuple;

/* loaded from: classes3.dex */
public interface IServiceListEdit extends IInterface {
    public static final String DESCRIPTOR = "android.media.tv.extension.servicedb.IServiceListEdit";

    public static class Default implements IServiceListEdit {
        @Override // android.media.tv.extension.servicedb.IServiceListEdit
        public int addPredefinedChannelList(String str, Bundle[] bundleArr) throws RemoteException {
            return 0;
        }

        @Override // android.media.tv.extension.servicedb.IServiceListEdit
        public int addPredefinedSatInfo(String str, Bundle bundle) throws RemoteException {
            return 0;
        }

        @Override // android.media.tv.extension.servicedb.IServiceListEdit
        public String addPredefinedServiceListInfo(int i, String str, String str2, String str3, int i2) throws RemoteException {
            return null;
        }

        @Override // android.os.IInterface
        public IBinder asBinder() {
            return null;
        }

        @Override // android.media.tv.extension.servicedb.IServiceListEdit
        public int close() throws RemoteException {
            return 0;
        }

        @Override // android.media.tv.extension.servicedb.IServiceListEdit
        public int commit() throws RemoteException {
            return 0;
        }

        @Override // android.media.tv.extension.servicedb.IServiceListEdit
        public Bundle[] getAllInstalledServiceListInfo() throws RemoteException {
            return null;
        }

        @Override // android.media.tv.extension.servicedb.IServiceListEdit
        public Bundle getInstalledServiceListInfo(String str) throws RemoteException {
            return null;
        }

        @Override // android.media.tv.extension.servicedb.IServiceListEdit
        public Bundle getNetworkInfoList(String str, String[] strArr) throws RemoteException {
            return null;
        }

        @Override // android.media.tv.extension.servicedb.IServiceListEdit
        public Bundle getSatelliteInfoList(String str, String[] strArr) throws RemoteException {
            return null;
        }

        @Override // android.media.tv.extension.servicedb.IServiceListEdit
        public Bundle getServiceInfoFromDatabase(String str, String[] strArr) throws RemoteException {
            return null;
        }

        @Override // android.media.tv.extension.servicedb.IServiceListEdit
        public String[] getServiceInfoIdsFromDatabase(String str) throws RemoteException {
            return null;
        }

        @Override // android.media.tv.extension.servicedb.IServiceListEdit
        public Bundle getServiceInfoListFromDatabase(String str, String[] strArr) throws RemoteException {
            return null;
        }

        @Override // android.media.tv.extension.servicedb.IServiceListEdit
        public String[] getServiceListChannelIds() throws RemoteException {
            return null;
        }

        @Override // android.media.tv.extension.servicedb.IServiceListEdit
        public Bundle getServiceListInfoByChannelId(String str, String[] strArr) throws RemoteException {
            return null;
        }

        @Override // android.media.tv.extension.servicedb.IServiceListEdit
        public String getServiceLogoUri(int i) throws RemoteException {
            return null;
        }

        @Override // android.media.tv.extension.servicedb.IServiceListEdit
        public Bundle getTransportStreamInfoList(String str, String[] strArr) throws RemoteException {
            return null;
        }

        @Override // android.media.tv.extension.servicedb.IServiceListEdit
        public Bundle getTransportStreamInfoListForce(String str, String[] strArr) throws RemoteException {
            return null;
        }

        @Override // android.media.tv.extension.servicedb.IServiceListEdit
        public int open(IServiceListEditListener iServiceListEditListener) throws RemoteException {
            return 0;
        }

        @Override // android.media.tv.extension.servicedb.IServiceListEdit
        public int putRecordIdList(String str, Bundle bundle, int i) throws RemoteException {
            return 0;
        }

        @Override // android.media.tv.extension.servicedb.IServiceListEdit
        public int removeServiceInfoByListFromDatabase(String[] strArr) throws RemoteException {
            return 0;
        }

        @Override // android.media.tv.extension.servicedb.IServiceListEdit
        public int removeServiceInfoFromDatabase(String str) throws RemoteException {
            return 0;
        }

        @Override // android.media.tv.extension.servicedb.IServiceListEdit
        public String toRecordInfoByType(Bundle bundle, String str) throws RemoteException {
            return null;
        }

        @Override // android.media.tv.extension.servicedb.IServiceListEdit
        public int updateServiceInfoByListFromDatabase(Bundle[] bundleArr) throws RemoteException {
            return 0;
        }

        @Override // android.media.tv.extension.servicedb.IServiceListEdit
        public int updateServiceInfoFromDatabase(Bundle bundle) throws RemoteException {
            return 0;
        }

        @Override // android.media.tv.extension.servicedb.IServiceListEdit
        public int userEditCommit() throws RemoteException {
            return 0;
        }
    }

    int addPredefinedChannelList(String str, Bundle[] bundleArr) throws RemoteException;

    int addPredefinedSatInfo(String str, Bundle bundle) throws RemoteException;

    String addPredefinedServiceListInfo(int i, String str, String str2, String str3, int i2) throws RemoteException;

    int close() throws RemoteException;

    int commit() throws RemoteException;

    Bundle[] getAllInstalledServiceListInfo() throws RemoteException;

    Bundle getInstalledServiceListInfo(String str) throws RemoteException;

    Bundle getNetworkInfoList(String str, String[] strArr) throws RemoteException;

    Bundle getSatelliteInfoList(String str, String[] strArr) throws RemoteException;

    Bundle getServiceInfoFromDatabase(String str, String[] strArr) throws RemoteException;

    String[] getServiceInfoIdsFromDatabase(String str) throws RemoteException;

    Bundle getServiceInfoListFromDatabase(String str, String[] strArr) throws RemoteException;

    String[] getServiceListChannelIds() throws RemoteException;

    Bundle getServiceListInfoByChannelId(String str, String[] strArr) throws RemoteException;

    String getServiceLogoUri(int i) throws RemoteException;

    Bundle getTransportStreamInfoList(String str, String[] strArr) throws RemoteException;

    Bundle getTransportStreamInfoListForce(String str, String[] strArr) throws RemoteException;

    int open(IServiceListEditListener iServiceListEditListener) throws RemoteException;

    int putRecordIdList(String str, Bundle bundle, int i) throws RemoteException;

    int removeServiceInfoByListFromDatabase(String[] strArr) throws RemoteException;

    int removeServiceInfoFromDatabase(String str) throws RemoteException;

    String toRecordInfoByType(Bundle bundle, String str) throws RemoteException;

    int updateServiceInfoByListFromDatabase(Bundle[] bundleArr) throws RemoteException;

    int updateServiceInfoFromDatabase(Bundle bundle) throws RemoteException;

    int userEditCommit() throws RemoteException;

    public static abstract class Stub extends Binder implements IServiceListEdit {
        static final int TRANSACTION_addPredefinedChannelList = 21;
        static final int TRANSACTION_addPredefinedSatInfo = 22;
        static final int TRANSACTION_addPredefinedServiceListInfo = 20;
        static final int TRANSACTION_close = 2;
        static final int TRANSACTION_commit = 3;
        static final int TRANSACTION_getAllInstalledServiceListInfo = 25;
        static final int TRANSACTION_getInstalledServiceListInfo = 24;
        static final int TRANSACTION_getNetworkInfoList = 16;
        static final int TRANSACTION_getSatelliteInfoList = 17;
        static final int TRANSACTION_getServiceInfoFromDatabase = 5;
        static final int TRANSACTION_getServiceInfoIdsFromDatabase = 7;
        static final int TRANSACTION_getServiceInfoListFromDatabase = 6;
        static final int TRANSACTION_getServiceListChannelIds = 12;
        static final int TRANSACTION_getServiceListInfoByChannelId = 13;
        static final int TRANSACTION_getServiceLogoUri = 23;
        static final int TRANSACTION_getTransportStreamInfoList = 14;
        static final int TRANSACTION_getTransportStreamInfoListForce = 15;
        static final int TRANSACTION_open = 1;
        static final int TRANSACTION_putRecordIdList = 19;
        static final int TRANSACTION_removeServiceInfoByListFromDatabase = 11;
        static final int TRANSACTION_removeServiceInfoFromDatabase = 10;
        static final int TRANSACTION_toRecordInfoByType = 18;
        static final int TRANSACTION_updateServiceInfoByListFromDatabase = 9;
        static final int TRANSACTION_updateServiceInfoFromDatabase = 8;
        static final int TRANSACTION_userEditCommit = 4;

        @Override // android.os.IInterface
        public IBinder asBinder() {
            return this;
        }

        @Override // android.os.Binder
        public int getMaxTransactionId() {
            return 24;
        }

        public Stub() {
            attachInterface(this, "android.media.tv.extension.servicedb.IServiceListEdit");
        }

        public static IServiceListEdit asInterface(IBinder iBinder) {
            if (iBinder == null) {
                return null;
            }
            IInterface queryLocalInterface = iBinder.queryLocalInterface("android.media.tv.extension.servicedb.IServiceListEdit");
            if (queryLocalInterface != null && (queryLocalInterface instanceof IServiceListEdit)) {
                return (IServiceListEdit) queryLocalInterface;
            }
            return new Proxy(iBinder);
        }

        public static String getDefaultTransactionName(int i) {
            switch (i) {
                case 1:
                    return RcsContactPresenceTuple.TUPLE_BASIC_STATUS_OPEN;
                case 2:
                    return "close";
                case 3:
                    return "commit";
                case 4:
                    return "userEditCommit";
                case 5:
                    return "getServiceInfoFromDatabase";
                case 6:
                    return "getServiceInfoListFromDatabase";
                case 7:
                    return "getServiceInfoIdsFromDatabase";
                case 8:
                    return "updateServiceInfoFromDatabase";
                case 9:
                    return "updateServiceInfoByListFromDatabase";
                case 10:
                    return "removeServiceInfoFromDatabase";
                case 11:
                    return "removeServiceInfoByListFromDatabase";
                case 12:
                    return "getServiceListChannelIds";
                case 13:
                    return "getServiceListInfoByChannelId";
                case 14:
                    return "getTransportStreamInfoList";
                case 15:
                    return "getTransportStreamInfoListForce";
                case 16:
                    return "getNetworkInfoList";
                case 17:
                    return "getSatelliteInfoList";
                case 18:
                    return "toRecordInfoByType";
                case 19:
                    return "putRecordIdList";
                case 20:
                    return "addPredefinedServiceListInfo";
                case 21:
                    return "addPredefinedChannelList";
                case 22:
                    return "addPredefinedSatInfo";
                case 23:
                    return "getServiceLogoUri";
                case 24:
                    return "getInstalledServiceListInfo";
                case 25:
                    return "getAllInstalledServiceListInfo";
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
                parcel.enforceInterface("android.media.tv.extension.servicedb.IServiceListEdit");
            }
            if (i == 1598968902) {
                parcel2.writeString("android.media.tv.extension.servicedb.IServiceListEdit");
                return true;
            }
            switch (i) {
                case 1:
                    IServiceListEditListener asInterface = IServiceListEditListener.Stub.asInterface(parcel.readStrongBinder());
                    parcel.enforceNoDataAvail();
                    int open = open(asInterface);
                    parcel2.writeNoException();
                    parcel2.writeInt(open);
                    return true;
                case 2:
                    int close = close();
                    parcel2.writeNoException();
                    parcel2.writeInt(close);
                    return true;
                case 3:
                    int commit = commit();
                    parcel2.writeNoException();
                    parcel2.writeInt(commit);
                    return true;
                case 4:
                    int userEditCommit = userEditCommit();
                    parcel2.writeNoException();
                    parcel2.writeInt(userEditCommit);
                    return true;
                case 5:
                    String readString = parcel.readString();
                    String[] createStringArray = parcel.createStringArray();
                    parcel.enforceNoDataAvail();
                    Bundle serviceInfoFromDatabase = getServiceInfoFromDatabase(readString, createStringArray);
                    parcel2.writeNoException();
                    parcel2.writeTypedObject(serviceInfoFromDatabase, 1);
                    return true;
                case 6:
                    String readString2 = parcel.readString();
                    String[] createStringArray2 = parcel.createStringArray();
                    parcel.enforceNoDataAvail();
                    Bundle serviceInfoListFromDatabase = getServiceInfoListFromDatabase(readString2, createStringArray2);
                    parcel2.writeNoException();
                    parcel2.writeTypedObject(serviceInfoListFromDatabase, 1);
                    return true;
                case 7:
                    String readString3 = parcel.readString();
                    parcel.enforceNoDataAvail();
                    String[] serviceInfoIdsFromDatabase = getServiceInfoIdsFromDatabase(readString3);
                    parcel2.writeNoException();
                    parcel2.writeStringArray(serviceInfoIdsFromDatabase);
                    return true;
                case 8:
                    Bundle bundle = (Bundle) parcel.readTypedObject(Bundle.CREATOR);
                    parcel.enforceNoDataAvail();
                    int updateServiceInfoFromDatabase = updateServiceInfoFromDatabase(bundle);
                    parcel2.writeNoException();
                    parcel2.writeInt(updateServiceInfoFromDatabase);
                    return true;
                case 9:
                    Bundle[] bundleArr = (Bundle[]) parcel.createTypedArray(Bundle.CREATOR);
                    parcel.enforceNoDataAvail();
                    int updateServiceInfoByListFromDatabase = updateServiceInfoByListFromDatabase(bundleArr);
                    parcel2.writeNoException();
                    parcel2.writeInt(updateServiceInfoByListFromDatabase);
                    return true;
                case 10:
                    String readString4 = parcel.readString();
                    parcel.enforceNoDataAvail();
                    int removeServiceInfoFromDatabase = removeServiceInfoFromDatabase(readString4);
                    parcel2.writeNoException();
                    parcel2.writeInt(removeServiceInfoFromDatabase);
                    return true;
                case 11:
                    String[] createStringArray3 = parcel.createStringArray();
                    parcel.enforceNoDataAvail();
                    int removeServiceInfoByListFromDatabase = removeServiceInfoByListFromDatabase(createStringArray3);
                    parcel2.writeNoException();
                    parcel2.writeInt(removeServiceInfoByListFromDatabase);
                    return true;
                case 12:
                    String[] serviceListChannelIds = getServiceListChannelIds();
                    parcel2.writeNoException();
                    parcel2.writeStringArray(serviceListChannelIds);
                    return true;
                case 13:
                    String readString5 = parcel.readString();
                    String[] createStringArray4 = parcel.createStringArray();
                    parcel.enforceNoDataAvail();
                    Bundle serviceListInfoByChannelId = getServiceListInfoByChannelId(readString5, createStringArray4);
                    parcel2.writeNoException();
                    parcel2.writeTypedObject(serviceListInfoByChannelId, 1);
                    return true;
                case 14:
                    String readString6 = parcel.readString();
                    String[] createStringArray5 = parcel.createStringArray();
                    parcel.enforceNoDataAvail();
                    Bundle transportStreamInfoList = getTransportStreamInfoList(readString6, createStringArray5);
                    parcel2.writeNoException();
                    parcel2.writeTypedObject(transportStreamInfoList, 1);
                    return true;
                case 15:
                    String readString7 = parcel.readString();
                    String[] createStringArray6 = parcel.createStringArray();
                    parcel.enforceNoDataAvail();
                    Bundle transportStreamInfoListForce = getTransportStreamInfoListForce(readString7, createStringArray6);
                    parcel2.writeNoException();
                    parcel2.writeTypedObject(transportStreamInfoListForce, 1);
                    return true;
                case 16:
                    String readString8 = parcel.readString();
                    String[] createStringArray7 = parcel.createStringArray();
                    parcel.enforceNoDataAvail();
                    Bundle networkInfoList = getNetworkInfoList(readString8, createStringArray7);
                    parcel2.writeNoException();
                    parcel2.writeTypedObject(networkInfoList, 1);
                    return true;
                case 17:
                    String readString9 = parcel.readString();
                    String[] createStringArray8 = parcel.createStringArray();
                    parcel.enforceNoDataAvail();
                    Bundle satelliteInfoList = getSatelliteInfoList(readString9, createStringArray8);
                    parcel2.writeNoException();
                    parcel2.writeTypedObject(satelliteInfoList, 1);
                    return true;
                case 18:
                    Bundle bundle2 = (Bundle) parcel.readTypedObject(Bundle.CREATOR);
                    String readString10 = parcel.readString();
                    parcel.enforceNoDataAvail();
                    String recordInfoByType = toRecordInfoByType(bundle2, readString10);
                    parcel2.writeNoException();
                    parcel2.writeString(recordInfoByType);
                    return true;
                case 19:
                    String readString11 = parcel.readString();
                    Bundle bundle3 = (Bundle) parcel.readTypedObject(Bundle.CREATOR);
                    int readInt = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    int putRecordIdList = putRecordIdList(readString11, bundle3, readInt);
                    parcel2.writeNoException();
                    parcel2.writeInt(putRecordIdList);
                    return true;
                case 20:
                    int readInt2 = parcel.readInt();
                    String readString12 = parcel.readString();
                    String readString13 = parcel.readString();
                    String readString14 = parcel.readString();
                    int readInt3 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    String addPredefinedServiceListInfo = addPredefinedServiceListInfo(readInt2, readString12, readString13, readString14, readInt3);
                    parcel2.writeNoException();
                    parcel2.writeString(addPredefinedServiceListInfo);
                    return true;
                case 21:
                    String readString15 = parcel.readString();
                    Bundle[] bundleArr2 = (Bundle[]) parcel.createTypedArray(Bundle.CREATOR);
                    parcel.enforceNoDataAvail();
                    int addPredefinedChannelList = addPredefinedChannelList(readString15, bundleArr2);
                    parcel2.writeNoException();
                    parcel2.writeInt(addPredefinedChannelList);
                    return true;
                case 22:
                    String readString16 = parcel.readString();
                    Bundle bundle4 = (Bundle) parcel.readTypedObject(Bundle.CREATOR);
                    parcel.enforceNoDataAvail();
                    int addPredefinedSatInfo = addPredefinedSatInfo(readString16, bundle4);
                    parcel2.writeNoException();
                    parcel2.writeInt(addPredefinedSatInfo);
                    return true;
                case 23:
                    int readInt4 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    String serviceLogoUri = getServiceLogoUri(readInt4);
                    parcel2.writeNoException();
                    parcel2.writeString(serviceLogoUri);
                    return true;
                case 24:
                    String readString17 = parcel.readString();
                    parcel.enforceNoDataAvail();
                    Bundle installedServiceListInfo = getInstalledServiceListInfo(readString17);
                    parcel2.writeNoException();
                    parcel2.writeTypedObject(installedServiceListInfo, 1);
                    return true;
                case 25:
                    Bundle[] allInstalledServiceListInfo = getAllInstalledServiceListInfo();
                    parcel2.writeNoException();
                    parcel2.writeTypedArray(allInstalledServiceListInfo, 1);
                    return true;
                default:
                    return super.onTransact(i, parcel, parcel2, i2);
            }
        }

        private static class Proxy implements IServiceListEdit {
            private IBinder mRemote;

            Proxy(IBinder iBinder) {
                this.mRemote = iBinder;
            }

            @Override // android.os.IInterface
            public IBinder asBinder() {
                return this.mRemote;
            }

            public String getInterfaceDescriptor() {
                return "android.media.tv.extension.servicedb.IServiceListEdit";
            }

            @Override // android.media.tv.extension.servicedb.IServiceListEdit
            public int open(IServiceListEditListener iServiceListEditListener) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken("android.media.tv.extension.servicedb.IServiceListEdit");
                    obtain.writeStrongInterface(iServiceListEditListener);
                    this.mRemote.transact(1, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readInt();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.media.tv.extension.servicedb.IServiceListEdit
            public int close() throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken("android.media.tv.extension.servicedb.IServiceListEdit");
                    this.mRemote.transact(2, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readInt();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.media.tv.extension.servicedb.IServiceListEdit
            public int commit() throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken("android.media.tv.extension.servicedb.IServiceListEdit");
                    this.mRemote.transact(3, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readInt();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.media.tv.extension.servicedb.IServiceListEdit
            public int userEditCommit() throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken("android.media.tv.extension.servicedb.IServiceListEdit");
                    this.mRemote.transact(4, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readInt();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.media.tv.extension.servicedb.IServiceListEdit
            public Bundle getServiceInfoFromDatabase(String str, String[] strArr) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken("android.media.tv.extension.servicedb.IServiceListEdit");
                    obtain.writeString(str);
                    obtain.writeStringArray(strArr);
                    this.mRemote.transact(5, obtain, obtain2, 0);
                    obtain2.readException();
                    return (Bundle) obtain2.readTypedObject(Bundle.CREATOR);
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.media.tv.extension.servicedb.IServiceListEdit
            public Bundle getServiceInfoListFromDatabase(String str, String[] strArr) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken("android.media.tv.extension.servicedb.IServiceListEdit");
                    obtain.writeString(str);
                    obtain.writeStringArray(strArr);
                    this.mRemote.transact(6, obtain, obtain2, 0);
                    obtain2.readException();
                    return (Bundle) obtain2.readTypedObject(Bundle.CREATOR);
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.media.tv.extension.servicedb.IServiceListEdit
            public String[] getServiceInfoIdsFromDatabase(String str) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken("android.media.tv.extension.servicedb.IServiceListEdit");
                    obtain.writeString(str);
                    this.mRemote.transact(7, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.createStringArray();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.media.tv.extension.servicedb.IServiceListEdit
            public int updateServiceInfoFromDatabase(Bundle bundle) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken("android.media.tv.extension.servicedb.IServiceListEdit");
                    obtain.writeTypedObject(bundle, 0);
                    this.mRemote.transact(8, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readInt();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.media.tv.extension.servicedb.IServiceListEdit
            public int updateServiceInfoByListFromDatabase(Bundle[] bundleArr) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken("android.media.tv.extension.servicedb.IServiceListEdit");
                    obtain.writeTypedArray(bundleArr, 0);
                    this.mRemote.transact(9, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readInt();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.media.tv.extension.servicedb.IServiceListEdit
            public int removeServiceInfoFromDatabase(String str) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken("android.media.tv.extension.servicedb.IServiceListEdit");
                    obtain.writeString(str);
                    this.mRemote.transact(10, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readInt();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.media.tv.extension.servicedb.IServiceListEdit
            public int removeServiceInfoByListFromDatabase(String[] strArr) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken("android.media.tv.extension.servicedb.IServiceListEdit");
                    obtain.writeStringArray(strArr);
                    this.mRemote.transact(11, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readInt();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.media.tv.extension.servicedb.IServiceListEdit
            public String[] getServiceListChannelIds() throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken("android.media.tv.extension.servicedb.IServiceListEdit");
                    this.mRemote.transact(12, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.createStringArray();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.media.tv.extension.servicedb.IServiceListEdit
            public Bundle getServiceListInfoByChannelId(String str, String[] strArr) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken("android.media.tv.extension.servicedb.IServiceListEdit");
                    obtain.writeString(str);
                    obtain.writeStringArray(strArr);
                    this.mRemote.transact(13, obtain, obtain2, 0);
                    obtain2.readException();
                    return (Bundle) obtain2.readTypedObject(Bundle.CREATOR);
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.media.tv.extension.servicedb.IServiceListEdit
            public Bundle getTransportStreamInfoList(String str, String[] strArr) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken("android.media.tv.extension.servicedb.IServiceListEdit");
                    obtain.writeString(str);
                    obtain.writeStringArray(strArr);
                    this.mRemote.transact(14, obtain, obtain2, 0);
                    obtain2.readException();
                    return (Bundle) obtain2.readTypedObject(Bundle.CREATOR);
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.media.tv.extension.servicedb.IServiceListEdit
            public Bundle getTransportStreamInfoListForce(String str, String[] strArr) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken("android.media.tv.extension.servicedb.IServiceListEdit");
                    obtain.writeString(str);
                    obtain.writeStringArray(strArr);
                    this.mRemote.transact(15, obtain, obtain2, 0);
                    obtain2.readException();
                    return (Bundle) obtain2.readTypedObject(Bundle.CREATOR);
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.media.tv.extension.servicedb.IServiceListEdit
            public Bundle getNetworkInfoList(String str, String[] strArr) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken("android.media.tv.extension.servicedb.IServiceListEdit");
                    obtain.writeString(str);
                    obtain.writeStringArray(strArr);
                    this.mRemote.transact(16, obtain, obtain2, 0);
                    obtain2.readException();
                    return (Bundle) obtain2.readTypedObject(Bundle.CREATOR);
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.media.tv.extension.servicedb.IServiceListEdit
            public Bundle getSatelliteInfoList(String str, String[] strArr) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken("android.media.tv.extension.servicedb.IServiceListEdit");
                    obtain.writeString(str);
                    obtain.writeStringArray(strArr);
                    this.mRemote.transact(17, obtain, obtain2, 0);
                    obtain2.readException();
                    return (Bundle) obtain2.readTypedObject(Bundle.CREATOR);
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.media.tv.extension.servicedb.IServiceListEdit
            public String toRecordInfoByType(Bundle bundle, String str) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken("android.media.tv.extension.servicedb.IServiceListEdit");
                    obtain.writeTypedObject(bundle, 0);
                    obtain.writeString(str);
                    this.mRemote.transact(18, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readString();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.media.tv.extension.servicedb.IServiceListEdit
            public int putRecordIdList(String str, Bundle bundle, int i) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken("android.media.tv.extension.servicedb.IServiceListEdit");
                    obtain.writeString(str);
                    obtain.writeTypedObject(bundle, 0);
                    obtain.writeInt(i);
                    this.mRemote.transact(19, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readInt();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.media.tv.extension.servicedb.IServiceListEdit
            public String addPredefinedServiceListInfo(int i, String str, String str2, String str3, int i2) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken("android.media.tv.extension.servicedb.IServiceListEdit");
                    obtain.writeInt(i);
                    obtain.writeString(str);
                    obtain.writeString(str2);
                    obtain.writeString(str3);
                    obtain.writeInt(i2);
                    this.mRemote.transact(20, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readString();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.media.tv.extension.servicedb.IServiceListEdit
            public int addPredefinedChannelList(String str, Bundle[] bundleArr) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken("android.media.tv.extension.servicedb.IServiceListEdit");
                    obtain.writeString(str);
                    obtain.writeTypedArray(bundleArr, 0);
                    this.mRemote.transact(21, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readInt();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.media.tv.extension.servicedb.IServiceListEdit
            public int addPredefinedSatInfo(String str, Bundle bundle) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken("android.media.tv.extension.servicedb.IServiceListEdit");
                    obtain.writeString(str);
                    obtain.writeTypedObject(bundle, 0);
                    this.mRemote.transact(22, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readInt();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.media.tv.extension.servicedb.IServiceListEdit
            public String getServiceLogoUri(int i) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken("android.media.tv.extension.servicedb.IServiceListEdit");
                    obtain.writeInt(i);
                    this.mRemote.transact(23, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readString();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.media.tv.extension.servicedb.IServiceListEdit
            public Bundle getInstalledServiceListInfo(String str) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken("android.media.tv.extension.servicedb.IServiceListEdit");
                    obtain.writeString(str);
                    this.mRemote.transact(24, obtain, obtain2, 0);
                    obtain2.readException();
                    return (Bundle) obtain2.readTypedObject(Bundle.CREATOR);
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.media.tv.extension.servicedb.IServiceListEdit
            public Bundle[] getAllInstalledServiceListInfo() throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken("android.media.tv.extension.servicedb.IServiceListEdit");
                    this.mRemote.transact(25, obtain, obtain2, 0);
                    obtain2.readException();
                    return (Bundle[]) obtain2.createTypedArray(Bundle.CREATOR);
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }
        }
    }
}
