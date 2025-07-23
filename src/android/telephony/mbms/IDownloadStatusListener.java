package android.telephony.mbms;

import android.os.Binder;
import android.os.IBinder;
import android.os.IInterface;
import android.os.Parcel;
import android.os.RemoteException;

/* loaded from: classes4.dex */
public interface IDownloadStatusListener extends IInterface {

    public static class Default implements IDownloadStatusListener {
        @Override // android.os.IInterface
        public IBinder asBinder() {
            return null;
        }

        @Override // android.telephony.mbms.IDownloadStatusListener
        public void onStatusUpdated(DownloadRequest downloadRequest, FileInfo fileInfo, int i) throws RemoteException {
        }
    }

    void onStatusUpdated(DownloadRequest downloadRequest, FileInfo fileInfo, int i) throws RemoteException;

    public static abstract class Stub extends Binder implements IDownloadStatusListener {
        public static final String DESCRIPTOR = "android.telephony.mbms.IDownloadStatusListener";
        static final int TRANSACTION_onStatusUpdated = 1;

        @Override // android.os.IInterface
        public IBinder asBinder() {
            return this;
        }

        @Override // android.os.Binder
        public int getMaxTransactionId() {
            return 0;
        }

        public Stub() {
            attachInterface(this, DESCRIPTOR);
        }

        public static IDownloadStatusListener asInterface(IBinder iBinder) {
            if (iBinder == null) {
                return null;
            }
            IInterface queryLocalInterface = iBinder.queryLocalInterface(DESCRIPTOR);
            if (queryLocalInterface != null && (queryLocalInterface instanceof IDownloadStatusListener)) {
                return (IDownloadStatusListener) queryLocalInterface;
            }
            return new Proxy(iBinder);
        }

        public static String getDefaultTransactionName(int i) {
            if (i != 1) {
                return null;
            }
            return "onStatusUpdated";
        }

        @Override // android.os.Binder
        public String getTransactionName(int i) {
            return getDefaultTransactionName(i);
        }

        @Override // android.os.Binder
        public boolean onTransact(int i, Parcel parcel, Parcel parcel2, int i2) throws RemoteException {
            if (i >= 1 && i <= 16777215) {
                parcel.enforceInterface(DESCRIPTOR);
            }
            if (i == 1598968902) {
                parcel2.writeString(DESCRIPTOR);
                return true;
            }
            if (i == 1) {
                DownloadRequest downloadRequest = (DownloadRequest) parcel.readTypedObject(DownloadRequest.CREATOR);
                FileInfo fileInfo = (FileInfo) parcel.readTypedObject(FileInfo.CREATOR);
                int readInt = parcel.readInt();
                parcel.enforceNoDataAvail();
                onStatusUpdated(downloadRequest, fileInfo, readInt);
                parcel2.writeNoException();
                return true;
            }
            return super.onTransact(i, parcel, parcel2, i2);
        }

        private static class Proxy implements IDownloadStatusListener {
            private IBinder mRemote;

            Proxy(IBinder iBinder) {
                this.mRemote = iBinder;
            }

            @Override // android.os.IInterface
            public IBinder asBinder() {
                return this.mRemote;
            }

            public String getInterfaceDescriptor() {
                return Stub.DESCRIPTOR;
            }

            @Override // android.telephony.mbms.IDownloadStatusListener
            public void onStatusUpdated(DownloadRequest downloadRequest, FileInfo fileInfo, int i) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    obtain.writeTypedObject(downloadRequest, 0);
                    obtain.writeTypedObject(fileInfo, 0);
                    obtain.writeInt(i);
                    this.mRemote.transact(1, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }
        }
    }
}
