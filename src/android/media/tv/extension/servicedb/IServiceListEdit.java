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
            IInterface iInterfaceQueryLocalInterface = iBinder.queryLocalInterface("android.media.tv.extension.servicedb.IServiceListEdit");
            if (iInterfaceQueryLocalInterface != null && (iInterfaceQueryLocalInterface instanceof IServiceListEdit)) {
                return (IServiceListEdit) iInterfaceQueryLocalInterface;
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
                    IServiceListEditListener iServiceListEditListenerAsInterface = IServiceListEditListener.Stub.asInterface(parcel.readStrongBinder());
                    parcel.enforceNoDataAvail();
                    int iOpen = open(iServiceListEditListenerAsInterface);
                    parcel2.writeNoException();
                    parcel2.writeInt(iOpen);
                    return true;
                case 2:
                    int iClose = close();
                    parcel2.writeNoException();
                    parcel2.writeInt(iClose);
                    return true;
                case 3:
                    int iCommit = commit();
                    parcel2.writeNoException();
                    parcel2.writeInt(iCommit);
                    return true;
                case 4:
                    int iUserEditCommit = userEditCommit();
                    parcel2.writeNoException();
                    parcel2.writeInt(iUserEditCommit);
                    return true;
                case 5:
                    String string = parcel.readString();
                    String[] strArrCreateStringArray = parcel.createStringArray();
                    parcel.enforceNoDataAvail();
                    Bundle serviceInfoFromDatabase = getServiceInfoFromDatabase(string, strArrCreateStringArray);
                    parcel2.writeNoException();
                    parcel2.writeTypedObject(serviceInfoFromDatabase, 1);
                    return true;
                case 6:
                    String string2 = parcel.readString();
                    String[] strArrCreateStringArray2 = parcel.createStringArray();
                    parcel.enforceNoDataAvail();
                    Bundle serviceInfoListFromDatabase = getServiceInfoListFromDatabase(string2, strArrCreateStringArray2);
                    parcel2.writeNoException();
                    parcel2.writeTypedObject(serviceInfoListFromDatabase, 1);
                    return true;
                case 7:
                    String string3 = parcel.readString();
                    parcel.enforceNoDataAvail();
                    String[] serviceInfoIdsFromDatabase = getServiceInfoIdsFromDatabase(string3);
                    parcel2.writeNoException();
                    parcel2.writeStringArray(serviceInfoIdsFromDatabase);
                    return true;
                case 8:
                    Bundle bundle = (Bundle) parcel.readTypedObject(Bundle.CREATOR);
                    parcel.enforceNoDataAvail();
                    int iUpdateServiceInfoFromDatabase = updateServiceInfoFromDatabase(bundle);
                    parcel2.writeNoException();
                    parcel2.writeInt(iUpdateServiceInfoFromDatabase);
                    return true;
                case 9:
                    Bundle[] bundleArr = (Bundle[]) parcel.createTypedArray(Bundle.CREATOR);
                    parcel.enforceNoDataAvail();
                    int iUpdateServiceInfoByListFromDatabase = updateServiceInfoByListFromDatabase(bundleArr);
                    parcel2.writeNoException();
                    parcel2.writeInt(iUpdateServiceInfoByListFromDatabase);
                    return true;
                case 10:
                    String string4 = parcel.readString();
                    parcel.enforceNoDataAvail();
                    int iRemoveServiceInfoFromDatabase = removeServiceInfoFromDatabase(string4);
                    parcel2.writeNoException();
                    parcel2.writeInt(iRemoveServiceInfoFromDatabase);
                    return true;
                case 11:
                    String[] strArrCreateStringArray3 = parcel.createStringArray();
                    parcel.enforceNoDataAvail();
                    int iRemoveServiceInfoByListFromDatabase = removeServiceInfoByListFromDatabase(strArrCreateStringArray3);
                    parcel2.writeNoException();
                    parcel2.writeInt(iRemoveServiceInfoByListFromDatabase);
                    return true;
                case 12:
                    String[] serviceListChannelIds = getServiceListChannelIds();
                    parcel2.writeNoException();
                    parcel2.writeStringArray(serviceListChannelIds);
                    return true;
                case 13:
                    String string5 = parcel.readString();
                    String[] strArrCreateStringArray4 = parcel.createStringArray();
                    parcel.enforceNoDataAvail();
                    Bundle serviceListInfoByChannelId = getServiceListInfoByChannelId(string5, strArrCreateStringArray4);
                    parcel2.writeNoException();
                    parcel2.writeTypedObject(serviceListInfoByChannelId, 1);
                    return true;
                case 14:
                    String string6 = parcel.readString();
                    String[] strArrCreateStringArray5 = parcel.createStringArray();
                    parcel.enforceNoDataAvail();
                    Bundle transportStreamInfoList = getTransportStreamInfoList(string6, strArrCreateStringArray5);
                    parcel2.writeNoException();
                    parcel2.writeTypedObject(transportStreamInfoList, 1);
                    return true;
                case 15:
                    String string7 = parcel.readString();
                    String[] strArrCreateStringArray6 = parcel.createStringArray();
                    parcel.enforceNoDataAvail();
                    Bundle transportStreamInfoListForce = getTransportStreamInfoListForce(string7, strArrCreateStringArray6);
                    parcel2.writeNoException();
                    parcel2.writeTypedObject(transportStreamInfoListForce, 1);
                    return true;
                case 16:
                    String string8 = parcel.readString();
                    String[] strArrCreateStringArray7 = parcel.createStringArray();
                    parcel.enforceNoDataAvail();
                    Bundle networkInfoList = getNetworkInfoList(string8, strArrCreateStringArray7);
                    parcel2.writeNoException();
                    parcel2.writeTypedObject(networkInfoList, 1);
                    return true;
                case 17:
                    String string9 = parcel.readString();
                    String[] strArrCreateStringArray8 = parcel.createStringArray();
                    parcel.enforceNoDataAvail();
                    Bundle satelliteInfoList = getSatelliteInfoList(string9, strArrCreateStringArray8);
                    parcel2.writeNoException();
                    parcel2.writeTypedObject(satelliteInfoList, 1);
                    return true;
                case 18:
                    Bundle bundle2 = (Bundle) parcel.readTypedObject(Bundle.CREATOR);
                    String string10 = parcel.readString();
                    parcel.enforceNoDataAvail();
                    String recordInfoByType = toRecordInfoByType(bundle2, string10);
                    parcel2.writeNoException();
                    parcel2.writeString(recordInfoByType);
                    return true;
                case 19:
                    String string11 = parcel.readString();
                    Bundle bundle3 = (Bundle) parcel.readTypedObject(Bundle.CREATOR);
                    int i3 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    int iPutRecordIdList = putRecordIdList(string11, bundle3, i3);
                    parcel2.writeNoException();
                    parcel2.writeInt(iPutRecordIdList);
                    return true;
                case 20:
                    int i4 = parcel.readInt();
                    String string12 = parcel.readString();
                    String string13 = parcel.readString();
                    String string14 = parcel.readString();
                    int i5 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    String strAddPredefinedServiceListInfo = addPredefinedServiceListInfo(i4, string12, string13, string14, i5);
                    parcel2.writeNoException();
                    parcel2.writeString(strAddPredefinedServiceListInfo);
                    return true;
                case 21:
                    String string15 = parcel.readString();
                    Bundle[] bundleArr2 = (Bundle[]) parcel.createTypedArray(Bundle.CREATOR);
                    parcel.enforceNoDataAvail();
                    int iAddPredefinedChannelList = addPredefinedChannelList(string15, bundleArr2);
                    parcel2.writeNoException();
                    parcel2.writeInt(iAddPredefinedChannelList);
                    return true;
                case 22:
                    String string16 = parcel.readString();
                    Bundle bundle4 = (Bundle) parcel.readTypedObject(Bundle.CREATOR);
                    parcel.enforceNoDataAvail();
                    int iAddPredefinedSatInfo = addPredefinedSatInfo(string16, bundle4);
                    parcel2.writeNoException();
                    parcel2.writeInt(iAddPredefinedSatInfo);
                    return true;
                case 23:
                    int i6 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    String serviceLogoUri = getServiceLogoUri(i6);
                    parcel2.writeNoException();
                    parcel2.writeString(serviceLogoUri);
                    return true;
                case 24:
                    String string17 = parcel.readString();
                    parcel.enforceNoDataAvail();
                    Bundle installedServiceListInfo = getInstalledServiceListInfo(string17);
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
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken("android.media.tv.extension.servicedb.IServiceListEdit");
                    parcelObtain.writeStrongInterface(iServiceListEditListener);
                    this.mRemote.transact(1, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return parcelObtain2.readInt();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.media.tv.extension.servicedb.IServiceListEdit
            public int close() throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken("android.media.tv.extension.servicedb.IServiceListEdit");
                    this.mRemote.transact(2, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return parcelObtain2.readInt();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.media.tv.extension.servicedb.IServiceListEdit
            public int commit() throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken("android.media.tv.extension.servicedb.IServiceListEdit");
                    this.mRemote.transact(3, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return parcelObtain2.readInt();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.media.tv.extension.servicedb.IServiceListEdit
            public int userEditCommit() throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken("android.media.tv.extension.servicedb.IServiceListEdit");
                    this.mRemote.transact(4, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return parcelObtain2.readInt();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.media.tv.extension.servicedb.IServiceListEdit
            public Bundle getServiceInfoFromDatabase(String str, String[] strArr) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken("android.media.tv.extension.servicedb.IServiceListEdit");
                    parcelObtain.writeString(str);
                    parcelObtain.writeStringArray(strArr);
                    this.mRemote.transact(5, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return (Bundle) parcelObtain2.readTypedObject(Bundle.CREATOR);
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.media.tv.extension.servicedb.IServiceListEdit
            public Bundle getServiceInfoListFromDatabase(String str, String[] strArr) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken("android.media.tv.extension.servicedb.IServiceListEdit");
                    parcelObtain.writeString(str);
                    parcelObtain.writeStringArray(strArr);
                    this.mRemote.transact(6, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return (Bundle) parcelObtain2.readTypedObject(Bundle.CREATOR);
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.media.tv.extension.servicedb.IServiceListEdit
            public String[] getServiceInfoIdsFromDatabase(String str) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken("android.media.tv.extension.servicedb.IServiceListEdit");
                    parcelObtain.writeString(str);
                    this.mRemote.transact(7, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return parcelObtain2.createStringArray();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.media.tv.extension.servicedb.IServiceListEdit
            public int updateServiceInfoFromDatabase(Bundle bundle) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken("android.media.tv.extension.servicedb.IServiceListEdit");
                    parcelObtain.writeTypedObject(bundle, 0);
                    this.mRemote.transact(8, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return parcelObtain2.readInt();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.media.tv.extension.servicedb.IServiceListEdit
            public int updateServiceInfoByListFromDatabase(Bundle[] bundleArr) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken("android.media.tv.extension.servicedb.IServiceListEdit");
                    parcelObtain.writeTypedArray(bundleArr, 0);
                    this.mRemote.transact(9, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return parcelObtain2.readInt();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.media.tv.extension.servicedb.IServiceListEdit
            public int removeServiceInfoFromDatabase(String str) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken("android.media.tv.extension.servicedb.IServiceListEdit");
                    parcelObtain.writeString(str);
                    this.mRemote.transact(10, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return parcelObtain2.readInt();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.media.tv.extension.servicedb.IServiceListEdit
            public int removeServiceInfoByListFromDatabase(String[] strArr) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken("android.media.tv.extension.servicedb.IServiceListEdit");
                    parcelObtain.writeStringArray(strArr);
                    this.mRemote.transact(11, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return parcelObtain2.readInt();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.media.tv.extension.servicedb.IServiceListEdit
            public String[] getServiceListChannelIds() throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken("android.media.tv.extension.servicedb.IServiceListEdit");
                    this.mRemote.transact(12, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return parcelObtain2.createStringArray();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.media.tv.extension.servicedb.IServiceListEdit
            public Bundle getServiceListInfoByChannelId(String str, String[] strArr) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken("android.media.tv.extension.servicedb.IServiceListEdit");
                    parcelObtain.writeString(str);
                    parcelObtain.writeStringArray(strArr);
                    this.mRemote.transact(13, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return (Bundle) parcelObtain2.readTypedObject(Bundle.CREATOR);
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.media.tv.extension.servicedb.IServiceListEdit
            public Bundle getTransportStreamInfoList(String str, String[] strArr) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken("android.media.tv.extension.servicedb.IServiceListEdit");
                    parcelObtain.writeString(str);
                    parcelObtain.writeStringArray(strArr);
                    this.mRemote.transact(14, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return (Bundle) parcelObtain2.readTypedObject(Bundle.CREATOR);
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.media.tv.extension.servicedb.IServiceListEdit
            public Bundle getTransportStreamInfoListForce(String str, String[] strArr) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken("android.media.tv.extension.servicedb.IServiceListEdit");
                    parcelObtain.writeString(str);
                    parcelObtain.writeStringArray(strArr);
                    this.mRemote.transact(15, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return (Bundle) parcelObtain2.readTypedObject(Bundle.CREATOR);
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.media.tv.extension.servicedb.IServiceListEdit
            public Bundle getNetworkInfoList(String str, String[] strArr) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken("android.media.tv.extension.servicedb.IServiceListEdit");
                    parcelObtain.writeString(str);
                    parcelObtain.writeStringArray(strArr);
                    this.mRemote.transact(16, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return (Bundle) parcelObtain2.readTypedObject(Bundle.CREATOR);
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.media.tv.extension.servicedb.IServiceListEdit
            public Bundle getSatelliteInfoList(String str, String[] strArr) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken("android.media.tv.extension.servicedb.IServiceListEdit");
                    parcelObtain.writeString(str);
                    parcelObtain.writeStringArray(strArr);
                    this.mRemote.transact(17, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return (Bundle) parcelObtain2.readTypedObject(Bundle.CREATOR);
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.media.tv.extension.servicedb.IServiceListEdit
            public String toRecordInfoByType(Bundle bundle, String str) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken("android.media.tv.extension.servicedb.IServiceListEdit");
                    parcelObtain.writeTypedObject(bundle, 0);
                    parcelObtain.writeString(str);
                    this.mRemote.transact(18, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return parcelObtain2.readString();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.media.tv.extension.servicedb.IServiceListEdit
            public int putRecordIdList(String str, Bundle bundle, int i) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken("android.media.tv.extension.servicedb.IServiceListEdit");
                    parcelObtain.writeString(str);
                    parcelObtain.writeTypedObject(bundle, 0);
                    parcelObtain.writeInt(i);
                    this.mRemote.transact(19, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return parcelObtain2.readInt();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.media.tv.extension.servicedb.IServiceListEdit
            public String addPredefinedServiceListInfo(int i, String str, String str2, String str3, int i2) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken("android.media.tv.extension.servicedb.IServiceListEdit");
                    parcelObtain.writeInt(i);
                    parcelObtain.writeString(str);
                    parcelObtain.writeString(str2);
                    parcelObtain.writeString(str3);
                    parcelObtain.writeInt(i2);
                    this.mRemote.transact(20, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return parcelObtain2.readString();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.media.tv.extension.servicedb.IServiceListEdit
            public int addPredefinedChannelList(String str, Bundle[] bundleArr) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken("android.media.tv.extension.servicedb.IServiceListEdit");
                    parcelObtain.writeString(str);
                    parcelObtain.writeTypedArray(bundleArr, 0);
                    this.mRemote.transact(21, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return parcelObtain2.readInt();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.media.tv.extension.servicedb.IServiceListEdit
            public int addPredefinedSatInfo(String str, Bundle bundle) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken("android.media.tv.extension.servicedb.IServiceListEdit");
                    parcelObtain.writeString(str);
                    parcelObtain.writeTypedObject(bundle, 0);
                    this.mRemote.transact(22, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return parcelObtain2.readInt();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.media.tv.extension.servicedb.IServiceListEdit
            public String getServiceLogoUri(int i) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken("android.media.tv.extension.servicedb.IServiceListEdit");
                    parcelObtain.writeInt(i);
                    this.mRemote.transact(23, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return parcelObtain2.readString();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.media.tv.extension.servicedb.IServiceListEdit
            public Bundle getInstalledServiceListInfo(String str) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken("android.media.tv.extension.servicedb.IServiceListEdit");
                    parcelObtain.writeString(str);
                    this.mRemote.transact(24, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return (Bundle) parcelObtain2.readTypedObject(Bundle.CREATOR);
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.media.tv.extension.servicedb.IServiceListEdit
            public Bundle[] getAllInstalledServiceListInfo() throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken("android.media.tv.extension.servicedb.IServiceListEdit");
                    this.mRemote.transact(25, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return (Bundle[]) parcelObtain2.createTypedArray(Bundle.CREATOR);
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }
        }
    }
}
