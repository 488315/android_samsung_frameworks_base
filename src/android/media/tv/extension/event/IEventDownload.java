package android.media.tv.extension.event;

import android.media.tv.extension.event.IEventDownloadListener;
import android.os.Binder;
import android.os.Bundle;
import android.os.IBinder;
import android.os.IInterface;
import android.os.Parcel;
import android.os.RemoteException;

/* loaded from: classes3.dex */
public interface IEventDownload extends IInterface {
    public static final String DESCRIPTOR = "android.media.tv.extension.event.IEventDownload";

    public static class Default implements IEventDownload {
        @Override // android.os.IInterface
        public IBinder asBinder() {
            return null;
        }

        @Override // android.media.tv.extension.event.IEventDownload
        public IBinder createSession(Bundle bundle, IEventDownloadListener iEventDownloadListener) throws RemoteException {
            return null;
        }
    }

    IBinder createSession(Bundle bundle, IEventDownloadListener iEventDownloadListener) throws RemoteException;

    public static abstract class Stub extends Binder implements IEventDownload {
        static final int TRANSACTION_createSession = 1;

        @Override // android.os.IInterface
        public IBinder asBinder() {
            return this;
        }

        @Override // android.os.Binder
        public int getMaxTransactionId() {
            return 0;
        }

        public Stub() {
            attachInterface(this, "android.media.tv.extension.event.IEventDownload");
        }

        public static IEventDownload asInterface(IBinder iBinder) {
            if (iBinder == null) {
                return null;
            }
            IInterface iInterfaceQueryLocalInterface = iBinder.queryLocalInterface("android.media.tv.extension.event.IEventDownload");
            if (iInterfaceQueryLocalInterface != null && (iInterfaceQueryLocalInterface instanceof IEventDownload)) {
                return (IEventDownload) iInterfaceQueryLocalInterface;
            }
            return new Proxy(iBinder);
        }

        public static String getDefaultTransactionName(int i) {
            if (i != 1) {
                return null;
            }
            return "createSession";
        }

        @Override // android.os.Binder
        public String getTransactionName(int i) {
            return getDefaultTransactionName(i);
        }

        @Override // android.os.Binder
        public boolean onTransact(int i, Parcel parcel, Parcel parcel2, int i2) throws RemoteException {
            if (i >= 1 && i <= 16777215) {
                parcel.enforceInterface("android.media.tv.extension.event.IEventDownload");
            }
            if (i == 1598968902) {
                parcel2.writeString("android.media.tv.extension.event.IEventDownload");
                return true;
            }
            if (i == 1) {
                Bundle bundle = (Bundle) parcel.readTypedObject(Bundle.CREATOR);
                IEventDownloadListener iEventDownloadListenerAsInterface = IEventDownloadListener.Stub.asInterface(parcel.readStrongBinder());
                parcel.enforceNoDataAvail();
                IBinder iBinderCreateSession = createSession(bundle, iEventDownloadListenerAsInterface);
                parcel2.writeNoException();
                parcel2.writeStrongBinder(iBinderCreateSession);
                return true;
            }
            return super.onTransact(i, parcel, parcel2, i2);
        }

        private static class Proxy implements IEventDownload {
            private IBinder mRemote;

            Proxy(IBinder iBinder) {
                this.mRemote = iBinder;
            }

            @Override // android.os.IInterface
            public IBinder asBinder() {
                return this.mRemote;
            }

            public String getInterfaceDescriptor() {
                return "android.media.tv.extension.event.IEventDownload";
            }

            @Override // android.media.tv.extension.event.IEventDownload
            public IBinder createSession(Bundle bundle, IEventDownloadListener iEventDownloadListener) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken("android.media.tv.extension.event.IEventDownload");
                    parcelObtain.writeTypedObject(bundle, 0);
                    parcelObtain.writeStrongInterface(iEventDownloadListener);
                    this.mRemote.transact(1, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return parcelObtain2.readStrongBinder();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }
        }
    }
}
