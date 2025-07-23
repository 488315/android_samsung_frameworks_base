package android.media.tv.extension.servicedb;

import android.os.Binder;
import android.os.Bundle;
import android.os.IBinder;
import android.os.IInterface;
import android.os.Parcel;
import android.os.RemoteException;

/* loaded from: classes3.dex */
public interface IServiceList extends IInterface {
    public static final String DESCRIPTOR = "android.media.tv.extension.servicedb.IServiceList";

    public static class Default implements IServiceList {
        @Override // android.os.IInterface
        public IBinder asBinder() {
            return null;
        }

        @Override // android.media.tv.extension.servicedb.IServiceList
        public String[] getServiceListIds() throws RemoteException {
            return null;
        }

        @Override // android.media.tv.extension.servicedb.IServiceList
        public Bundle getServiceListInfo(String str, String[] strArr) throws RemoteException {
            return null;
        }
    }

    String[] getServiceListIds() throws RemoteException;

    Bundle getServiceListInfo(String str, String[] strArr) throws RemoteException;

    public static abstract class Stub extends Binder implements IServiceList {
        static final int TRANSACTION_getServiceListIds = 1;
        static final int TRANSACTION_getServiceListInfo = 2;

        @Override // android.os.IInterface
        public IBinder asBinder() {
            return this;
        }

        @Override // android.os.Binder
        public int getMaxTransactionId() {
            return 1;
        }

        public Stub() {
            attachInterface(this, "android.media.tv.extension.servicedb.IServiceList");
        }

        public static IServiceList asInterface(IBinder iBinder) {
            if (iBinder == null) {
                return null;
            }
            IInterface queryLocalInterface = iBinder.queryLocalInterface("android.media.tv.extension.servicedb.IServiceList");
            if (queryLocalInterface != null && (queryLocalInterface instanceof IServiceList)) {
                return (IServiceList) queryLocalInterface;
            }
            return new Proxy(iBinder);
        }

        public static String getDefaultTransactionName(int i) {
            if (i == 1) {
                return "getServiceListIds";
            }
            if (i != 2) {
                return null;
            }
            return "getServiceListInfo";
        }

        @Override // android.os.Binder
        public String getTransactionName(int i) {
            return getDefaultTransactionName(i);
        }

        @Override // android.os.Binder
        public boolean onTransact(int i, Parcel parcel, Parcel parcel2, int i2) throws RemoteException {
            if (i >= 1 && i <= 16777215) {
                parcel.enforceInterface("android.media.tv.extension.servicedb.IServiceList");
            }
            if (i == 1598968902) {
                parcel2.writeString("android.media.tv.extension.servicedb.IServiceList");
                return true;
            }
            if (i == 1) {
                String[] serviceListIds = getServiceListIds();
                parcel2.writeNoException();
                parcel2.writeStringArray(serviceListIds);
            } else if (i == 2) {
                String readString = parcel.readString();
                String[] createStringArray = parcel.createStringArray();
                parcel.enforceNoDataAvail();
                Bundle serviceListInfo = getServiceListInfo(readString, createStringArray);
                parcel2.writeNoException();
                parcel2.writeTypedObject(serviceListInfo, 1);
            } else {
                return super.onTransact(i, parcel, parcel2, i2);
            }
            return true;
        }

        private static class Proxy implements IServiceList {
            private IBinder mRemote;

            Proxy(IBinder iBinder) {
                this.mRemote = iBinder;
            }

            @Override // android.os.IInterface
            public IBinder asBinder() {
                return this.mRemote;
            }

            public String getInterfaceDescriptor() {
                return "android.media.tv.extension.servicedb.IServiceList";
            }

            @Override // android.media.tv.extension.servicedb.IServiceList
            public String[] getServiceListIds() throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken("android.media.tv.extension.servicedb.IServiceList");
                    this.mRemote.transact(1, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.createStringArray();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.media.tv.extension.servicedb.IServiceList
            public Bundle getServiceListInfo(String str, String[] strArr) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken("android.media.tv.extension.servicedb.IServiceList");
                    obtain.writeString(str);
                    obtain.writeStringArray(strArr);
                    this.mRemote.transact(2, obtain, obtain2, 0);
                    obtain2.readException();
                    return (Bundle) obtain2.readTypedObject(Bundle.CREATOR);
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }
        }
    }
}
