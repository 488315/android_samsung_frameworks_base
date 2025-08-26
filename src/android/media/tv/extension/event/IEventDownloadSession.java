package android.media.tv.extension.event;

import android.net.Uri;
import android.os.Binder;
import android.os.Bundle;
import android.os.IBinder;
import android.os.IInterface;
import android.os.Parcel;
import android.os.RemoteException;

/* loaded from: classes3.dex */
public interface IEventDownloadSession extends IInterface {
    public static final String DESCRIPTOR = "android.media.tv.extension.event.IEventDownloadSession";

    public static class Default implements IEventDownloadSession {
        @Override // android.os.IInterface
        public IBinder asBinder() {
            return null;
        }

        @Override // android.media.tv.extension.event.IEventDownloadSession
        public void cancel() throws RemoteException {
        }

        @Override // android.media.tv.extension.event.IEventDownloadSession
        public int isBarkerOrSequentialDownloadByServiceRecord(Bundle bundle) throws RemoteException {
            return 0;
        }

        @Override // android.media.tv.extension.event.IEventDownloadSession
        public int isBarkerOrSequentialDownloadByServiceType(Bundle bundle) throws RemoteException {
            return 0;
        }

        @Override // android.media.tv.extension.event.IEventDownloadSession
        public void release() throws RemoteException {
        }

        @Override // android.media.tv.extension.event.IEventDownloadSession
        public void setActiveWindowChannelInfo(Uri[] uriArr) throws RemoteException {
        }

        @Override // android.media.tv.extension.event.IEventDownloadSession
        public void startTuningMultiplex(Uri uri) throws RemoteException {
        }
    }

    void cancel() throws RemoteException;

    int isBarkerOrSequentialDownloadByServiceRecord(Bundle bundle) throws RemoteException;

    int isBarkerOrSequentialDownloadByServiceType(Bundle bundle) throws RemoteException;

    void release() throws RemoteException;

    void setActiveWindowChannelInfo(Uri[] uriArr) throws RemoteException;

    void startTuningMultiplex(Uri uri) throws RemoteException;

    public static abstract class Stub extends Binder implements IEventDownloadSession {
        static final int TRANSACTION_cancel = 5;
        static final int TRANSACTION_isBarkerOrSequentialDownloadByServiceRecord = 2;
        static final int TRANSACTION_isBarkerOrSequentialDownloadByServiceType = 1;
        static final int TRANSACTION_release = 6;
        static final int TRANSACTION_setActiveWindowChannelInfo = 4;
        static final int TRANSACTION_startTuningMultiplex = 3;

        @Override // android.os.IInterface
        public IBinder asBinder() {
            return this;
        }

        @Override // android.os.Binder
        public int getMaxTransactionId() {
            return 5;
        }

        public Stub() {
            attachInterface(this, "android.media.tv.extension.event.IEventDownloadSession");
        }

        public static IEventDownloadSession asInterface(IBinder iBinder) {
            if (iBinder == null) {
                return null;
            }
            IInterface iInterfaceQueryLocalInterface = iBinder.queryLocalInterface("android.media.tv.extension.event.IEventDownloadSession");
            if (iInterfaceQueryLocalInterface != null && (iInterfaceQueryLocalInterface instanceof IEventDownloadSession)) {
                return (IEventDownloadSession) iInterfaceQueryLocalInterface;
            }
            return new Proxy(iBinder);
        }

        public static String getDefaultTransactionName(int i) {
            switch (i) {
                case 1:
                    return "isBarkerOrSequentialDownloadByServiceType";
                case 2:
                    return "isBarkerOrSequentialDownloadByServiceRecord";
                case 3:
                    return "startTuningMultiplex";
                case 4:
                    return "setActiveWindowChannelInfo";
                case 5:
                    return "cancel";
                case 6:
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
                parcel.enforceInterface("android.media.tv.extension.event.IEventDownloadSession");
            }
            if (i == 1598968902) {
                parcel2.writeString("android.media.tv.extension.event.IEventDownloadSession");
                return true;
            }
            switch (i) {
                case 1:
                    Bundle bundle = (Bundle) parcel.readTypedObject(Bundle.CREATOR);
                    parcel.enforceNoDataAvail();
                    int iIsBarkerOrSequentialDownloadByServiceType = isBarkerOrSequentialDownloadByServiceType(bundle);
                    parcel2.writeNoException();
                    parcel2.writeInt(iIsBarkerOrSequentialDownloadByServiceType);
                    return true;
                case 2:
                    Bundle bundle2 = (Bundle) parcel.readTypedObject(Bundle.CREATOR);
                    parcel.enforceNoDataAvail();
                    int iIsBarkerOrSequentialDownloadByServiceRecord = isBarkerOrSequentialDownloadByServiceRecord(bundle2);
                    parcel2.writeNoException();
                    parcel2.writeInt(iIsBarkerOrSequentialDownloadByServiceRecord);
                    return true;
                case 3:
                    Uri uri = (Uri) parcel.readTypedObject(Uri.CREATOR);
                    parcel.enforceNoDataAvail();
                    startTuningMultiplex(uri);
                    parcel2.writeNoException();
                    return true;
                case 4:
                    Uri[] uriArr = (Uri[]) parcel.createTypedArray(Uri.CREATOR);
                    parcel.enforceNoDataAvail();
                    setActiveWindowChannelInfo(uriArr);
                    parcel2.writeNoException();
                    return true;
                case 5:
                    cancel();
                    parcel2.writeNoException();
                    return true;
                case 6:
                    release();
                    parcel2.writeNoException();
                    return true;
                default:
                    return super.onTransact(i, parcel, parcel2, i2);
            }
        }

        private static class Proxy implements IEventDownloadSession {
            private IBinder mRemote;

            Proxy(IBinder iBinder) {
                this.mRemote = iBinder;
            }

            @Override // android.os.IInterface
            public IBinder asBinder() {
                return this.mRemote;
            }

            public String getInterfaceDescriptor() {
                return "android.media.tv.extension.event.IEventDownloadSession";
            }

            @Override // android.media.tv.extension.event.IEventDownloadSession
            public int isBarkerOrSequentialDownloadByServiceType(Bundle bundle) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken("android.media.tv.extension.event.IEventDownloadSession");
                    parcelObtain.writeTypedObject(bundle, 0);
                    this.mRemote.transact(1, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return parcelObtain2.readInt();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.media.tv.extension.event.IEventDownloadSession
            public int isBarkerOrSequentialDownloadByServiceRecord(Bundle bundle) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken("android.media.tv.extension.event.IEventDownloadSession");
                    parcelObtain.writeTypedObject(bundle, 0);
                    this.mRemote.transact(2, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return parcelObtain2.readInt();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.media.tv.extension.event.IEventDownloadSession
            public void startTuningMultiplex(Uri uri) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken("android.media.tv.extension.event.IEventDownloadSession");
                    parcelObtain.writeTypedObject(uri, 0);
                    this.mRemote.transact(3, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.media.tv.extension.event.IEventDownloadSession
            public void setActiveWindowChannelInfo(Uri[] uriArr) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken("android.media.tv.extension.event.IEventDownloadSession");
                    parcelObtain.writeTypedArray(uriArr, 0);
                    this.mRemote.transact(4, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.media.tv.extension.event.IEventDownloadSession
            public void cancel() throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken("android.media.tv.extension.event.IEventDownloadSession");
                    this.mRemote.transact(5, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.media.tv.extension.event.IEventDownloadSession
            public void release() throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken("android.media.tv.extension.event.IEventDownloadSession");
                    this.mRemote.transact(6, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }
        }
    }
}
