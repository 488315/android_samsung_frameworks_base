package android.telephony.mbms;

import android.os.Binder;
import android.os.IBinder;
import android.os.IInterface;
import android.os.Parcel;
import android.os.RemoteException;

import java.util.List;

public interface IMbmsDownloadSessionCallback extends IInterface {
    void onError(int i, String str) throws RemoteException;

    void onFileServicesUpdated(List<FileServiceInfo> list) throws RemoteException;

    void onMiddlewareReady() throws RemoteException;

    public static class Default implements IMbmsDownloadSessionCallback {
        @Override // android.telephony.mbms.IMbmsDownloadSessionCallback
        public void onError(int errorCode, String message) throws RemoteException {}

        @Override // android.telephony.mbms.IMbmsDownloadSessionCallback
        public void onFileServicesUpdated(List<FileServiceInfo> services) throws RemoteException {}

        @Override // android.telephony.mbms.IMbmsDownloadSessionCallback
        public void onMiddlewareReady() throws RemoteException {}

        @Override // android.os.IInterface
        public IBinder asBinder() {
            return null;
        }
    }

    public abstract static class Stub extends Binder implements IMbmsDownloadSessionCallback {
        public static final String DESCRIPTOR =
                "android.telephony.mbms.IMbmsDownloadSessionCallback";
        static final int TRANSACTION_onError = 1;
        static final int TRANSACTION_onFileServicesUpdated = 2;
        static final int TRANSACTION_onMiddlewareReady = 3;

        public Stub() {
            attachInterface(this, DESCRIPTOR);
        }

        public static IMbmsDownloadSessionCallback asInterface(IBinder obj) {
            if (obj == null) {
                return null;
            }
            IInterface iin = obj.queryLocalInterface(DESCRIPTOR);
            if (iin != null && (iin instanceof IMbmsDownloadSessionCallback)) {
                return (IMbmsDownloadSessionCallback) iin;
            }
            return new Proxy(obj);
        }

        @Override // android.os.IInterface
        public IBinder asBinder() {
            return this;
        }

        public static String getDefaultTransactionName(int transactionCode) {
            switch (transactionCode) {
                case 1:
                    return "onError";
                case 2:
                    return "onFileServicesUpdated";
                case 3:
                    return "onMiddlewareReady";
                default:
                    return null;
            }
        }

        @Override // android.os.Binder
        public String getTransactionName(int transactionCode) {
            return getDefaultTransactionName(transactionCode);
        }

        @Override // android.os.Binder
        public boolean onTransact(int code, Parcel data, Parcel reply, int flags)
                throws RemoteException {
            if (code >= 1 && code <= 16777215) {
                data.enforceInterface(DESCRIPTOR);
            }
            if (code == 1598968902) {
                reply.writeString(DESCRIPTOR);
                return true;
            }
            switch (code) {
                case 1:
                    int _arg0 = data.readInt();
                    String _arg1 = data.readString();
                    data.enforceNoDataAvail();
                    onError(_arg0, _arg1);
                    return true;
                case 2:
                    List<FileServiceInfo> _arg02 =
                            data.createTypedArrayList(FileServiceInfo.CREATOR);
                    data.enforceNoDataAvail();
                    onFileServicesUpdated(_arg02);
                    return true;
                case 3:
                    onMiddlewareReady();
                    return true;
                default:
                    return super.onTransact(code, data, reply, flags);
            }
        }

        private static class Proxy implements IMbmsDownloadSessionCallback {
            private IBinder mRemote;

            Proxy(IBinder remote) {
                this.mRemote = remote;
            }

            @Override // android.os.IInterface
            public IBinder asBinder() {
                return this.mRemote;
            }

            public String getInterfaceDescriptor() {
                return Stub.DESCRIPTOR;
            }

            @Override // android.telephony.mbms.IMbmsDownloadSessionCallback
            public void onError(int errorCode, String message) throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeInt(errorCode);
                    _data.writeString(message);
                    this.mRemote.transact(1, _data, null, 1);
                } finally {
                    _data.recycle();
                }
            }

            @Override // android.telephony.mbms.IMbmsDownloadSessionCallback
            public void onFileServicesUpdated(List<FileServiceInfo> services)
                    throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeTypedList(services, 0);
                    this.mRemote.transact(2, _data, null, 1);
                } finally {
                    _data.recycle();
                }
            }

            @Override // android.telephony.mbms.IMbmsDownloadSessionCallback
            public void onMiddlewareReady() throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    this.mRemote.transact(3, _data, null, 1);
                } finally {
                    _data.recycle();
                }
            }
        }

        @Override // android.os.Binder
        public int getMaxTransactionId() {
            return 2;
        }
    }
}
