package android.media.tv.extension.teletext;

import android.media.tv.extension.teletext.IDataServiceSignalInfoListener;
import android.os.Binder;
import android.os.Bundle;
import android.os.IBinder;
import android.os.IInterface;
import android.os.Parcel;
import android.os.RemoteException;

/* loaded from: classes3.dex */
public interface IDataServiceSignalInfo extends IInterface {
    public static final String DESCRIPTOR = "android.media.tv.extension.teletext.IDataServiceSignalInfo";

    public static class Default implements IDataServiceSignalInfo {
        @Override // android.media.tv.extension.teletext.IDataServiceSignalInfo
        public void addDataServiceSignalInfoListener(String str, IDataServiceSignalInfoListener iDataServiceSignalInfoListener) throws RemoteException {
        }

        @Override // android.os.IInterface
        public IBinder asBinder() {
            return null;
        }

        @Override // android.media.tv.extension.teletext.IDataServiceSignalInfo
        public Bundle getDataServiceSignalInfo(String str) throws RemoteException {
            return null;
        }

        @Override // android.media.tv.extension.teletext.IDataServiceSignalInfo
        public void removeDataServiceSignalInfoListener(String str, IDataServiceSignalInfoListener iDataServiceSignalInfoListener) throws RemoteException {
        }
    }

    void addDataServiceSignalInfoListener(String str, IDataServiceSignalInfoListener iDataServiceSignalInfoListener) throws RemoteException;

    Bundle getDataServiceSignalInfo(String str) throws RemoteException;

    void removeDataServiceSignalInfoListener(String str, IDataServiceSignalInfoListener iDataServiceSignalInfoListener) throws RemoteException;

    public static abstract class Stub extends Binder implements IDataServiceSignalInfo {
        static final int TRANSACTION_addDataServiceSignalInfoListener = 2;
        static final int TRANSACTION_getDataServiceSignalInfo = 1;
        static final int TRANSACTION_removeDataServiceSignalInfoListener = 3;

        @Override // android.os.IInterface
        public IBinder asBinder() {
            return this;
        }

        @Override // android.os.Binder
        public int getMaxTransactionId() {
            return 2;
        }

        public Stub() {
            attachInterface(this, "android.media.tv.extension.teletext.IDataServiceSignalInfo");
        }

        public static IDataServiceSignalInfo asInterface(IBinder iBinder) {
            if (iBinder == null) {
                return null;
            }
            IInterface queryLocalInterface = iBinder.queryLocalInterface("android.media.tv.extension.teletext.IDataServiceSignalInfo");
            if (queryLocalInterface != null && (queryLocalInterface instanceof IDataServiceSignalInfo)) {
                return (IDataServiceSignalInfo) queryLocalInterface;
            }
            return new Proxy(iBinder);
        }

        public static String getDefaultTransactionName(int i) {
            if (i == 1) {
                return "getDataServiceSignalInfo";
            }
            if (i == 2) {
                return "addDataServiceSignalInfoListener";
            }
            if (i != 3) {
                return null;
            }
            return "removeDataServiceSignalInfoListener";
        }

        @Override // android.os.Binder
        public String getTransactionName(int i) {
            return getDefaultTransactionName(i);
        }

        @Override // android.os.Binder
        public boolean onTransact(int i, Parcel parcel, Parcel parcel2, int i2) throws RemoteException {
            if (i >= 1 && i <= 16777215) {
                parcel.enforceInterface("android.media.tv.extension.teletext.IDataServiceSignalInfo");
            }
            if (i == 1598968902) {
                parcel2.writeString("android.media.tv.extension.teletext.IDataServiceSignalInfo");
                return true;
            }
            if (i == 1) {
                String readString = parcel.readString();
                parcel.enforceNoDataAvail();
                Bundle dataServiceSignalInfo = getDataServiceSignalInfo(readString);
                parcel2.writeNoException();
                parcel2.writeTypedObject(dataServiceSignalInfo, 1);
            } else if (i == 2) {
                String readString2 = parcel.readString();
                IDataServiceSignalInfoListener asInterface = IDataServiceSignalInfoListener.Stub.asInterface(parcel.readStrongBinder());
                parcel.enforceNoDataAvail();
                addDataServiceSignalInfoListener(readString2, asInterface);
                parcel2.writeNoException();
            } else if (i == 3) {
                String readString3 = parcel.readString();
                IDataServiceSignalInfoListener asInterface2 = IDataServiceSignalInfoListener.Stub.asInterface(parcel.readStrongBinder());
                parcel.enforceNoDataAvail();
                removeDataServiceSignalInfoListener(readString3, asInterface2);
                parcel2.writeNoException();
            } else {
                return super.onTransact(i, parcel, parcel2, i2);
            }
            return true;
        }

        private static class Proxy implements IDataServiceSignalInfo {
            private IBinder mRemote;

            Proxy(IBinder iBinder) {
                this.mRemote = iBinder;
            }

            @Override // android.os.IInterface
            public IBinder asBinder() {
                return this.mRemote;
            }

            public String getInterfaceDescriptor() {
                return "android.media.tv.extension.teletext.IDataServiceSignalInfo";
            }

            @Override // android.media.tv.extension.teletext.IDataServiceSignalInfo
            public Bundle getDataServiceSignalInfo(String str) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken("android.media.tv.extension.teletext.IDataServiceSignalInfo");
                    obtain.writeString(str);
                    this.mRemote.transact(1, obtain, obtain2, 0);
                    obtain2.readException();
                    return (Bundle) obtain2.readTypedObject(Bundle.CREATOR);
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.media.tv.extension.teletext.IDataServiceSignalInfo
            public void addDataServiceSignalInfoListener(String str, IDataServiceSignalInfoListener iDataServiceSignalInfoListener) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken("android.media.tv.extension.teletext.IDataServiceSignalInfo");
                    obtain.writeString(str);
                    obtain.writeStrongInterface(iDataServiceSignalInfoListener);
                    this.mRemote.transact(2, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.media.tv.extension.teletext.IDataServiceSignalInfo
            public void removeDataServiceSignalInfoListener(String str, IDataServiceSignalInfoListener iDataServiceSignalInfoListener) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken("android.media.tv.extension.teletext.IDataServiceSignalInfo");
                    obtain.writeString(str);
                    obtain.writeStrongInterface(iDataServiceSignalInfoListener);
                    this.mRemote.transact(3, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }
        }
    }
}
