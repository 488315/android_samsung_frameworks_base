package android.media.tv.extension.servicedb;

import android.os.Binder;
import android.os.Bundle;
import android.os.IBinder;
import android.os.IInterface;
import android.os.Parcel;
import android.os.RemoteException;

/* loaded from: classes3.dex */
public interface IServiceListSetChannelListSession extends IInterface {
    public static final String DESCRIPTOR = "android.media.tv.extension.servicedb.IServiceListSetChannelListSession";

    public static class Default implements IServiceListSetChannelListSession {
        @Override // android.os.IInterface
        public IBinder asBinder() {
            return null;
        }

        @Override // android.media.tv.extension.servicedb.IServiceListSetChannelListSession
        public int release() throws RemoteException {
            return 0;
        }

        @Override // android.media.tv.extension.servicedb.IServiceListSetChannelListSession
        public int setChannelList(Bundle[] bundleArr, Bundle bundle, int i) throws RemoteException {
            return 0;
        }
    }

    int release() throws RemoteException;

    int setChannelList(Bundle[] bundleArr, Bundle bundle, int i) throws RemoteException;

    public static abstract class Stub extends Binder implements IServiceListSetChannelListSession {
        static final int TRANSACTION_release = 2;
        static final int TRANSACTION_setChannelList = 1;

        @Override // android.os.IInterface
        public IBinder asBinder() {
            return this;
        }

        @Override // android.os.Binder
        public int getMaxTransactionId() {
            return 1;
        }

        public Stub() {
            attachInterface(this, "android.media.tv.extension.servicedb.IServiceListSetChannelListSession");
        }

        public static IServiceListSetChannelListSession asInterface(IBinder iBinder) {
            if (iBinder == null) {
                return null;
            }
            IInterface iInterfaceQueryLocalInterface = iBinder.queryLocalInterface("android.media.tv.extension.servicedb.IServiceListSetChannelListSession");
            if (iInterfaceQueryLocalInterface != null && (iInterfaceQueryLocalInterface instanceof IServiceListSetChannelListSession)) {
                return (IServiceListSetChannelListSession) iInterfaceQueryLocalInterface;
            }
            return new Proxy(iBinder);
        }

        public static String getDefaultTransactionName(int i) {
            if (i == 1) {
                return "setChannelList";
            }
            if (i != 2) {
                return null;
            }
            return "release";
        }

        @Override // android.os.Binder
        public String getTransactionName(int i) {
            return getDefaultTransactionName(i);
        }

        @Override // android.os.Binder
        public boolean onTransact(int i, Parcel parcel, Parcel parcel2, int i2) throws RemoteException {
            if (i >= 1 && i <= 16777215) {
                parcel.enforceInterface("android.media.tv.extension.servicedb.IServiceListSetChannelListSession");
            }
            if (i == 1598968902) {
                parcel2.writeString("android.media.tv.extension.servicedb.IServiceListSetChannelListSession");
                return true;
            }
            if (i == 1) {
                Bundle[] bundleArr = (Bundle[]) parcel.createTypedArray(Bundle.CREATOR);
                Bundle bundle = (Bundle) parcel.readTypedObject(Bundle.CREATOR);
                int i3 = parcel.readInt();
                parcel.enforceNoDataAvail();
                int channelList = setChannelList(bundleArr, bundle, i3);
                parcel2.writeNoException();
                parcel2.writeInt(channelList);
            } else if (i == 2) {
                int iRelease = release();
                parcel2.writeNoException();
                parcel2.writeInt(iRelease);
            } else {
                return super.onTransact(i, parcel, parcel2, i2);
            }
            return true;
        }

        private static class Proxy implements IServiceListSetChannelListSession {
            private IBinder mRemote;

            Proxy(IBinder iBinder) {
                this.mRemote = iBinder;
            }

            @Override // android.os.IInterface
            public IBinder asBinder() {
                return this.mRemote;
            }

            public String getInterfaceDescriptor() {
                return "android.media.tv.extension.servicedb.IServiceListSetChannelListSession";
            }

            @Override // android.media.tv.extension.servicedb.IServiceListSetChannelListSession
            public int setChannelList(Bundle[] bundleArr, Bundle bundle, int i) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken("android.media.tv.extension.servicedb.IServiceListSetChannelListSession");
                    parcelObtain.writeTypedArray(bundleArr, 0);
                    parcelObtain.writeTypedObject(bundle, 0);
                    parcelObtain.writeInt(i);
                    this.mRemote.transact(1, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return parcelObtain2.readInt();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.media.tv.extension.servicedb.IServiceListSetChannelListSession
            public int release() throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken("android.media.tv.extension.servicedb.IServiceListSetChannelListSession");
                    this.mRemote.transact(2, parcelObtain, parcelObtain2, 0);
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
