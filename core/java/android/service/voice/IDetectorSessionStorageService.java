package android.service.voice;

import android.os.Binder;
import android.os.IBinder;
import android.os.IInterface;
import android.os.Parcel;
import android.os.RemoteException;

import com.android.internal.infra.AndroidFuture;

public interface IDetectorSessionStorageService extends IInterface {
    public static final String DESCRIPTOR = "android.service.voice.IDetectorSessionStorageService";

    void openFile(String str, AndroidFuture androidFuture) throws RemoteException;

    public static class Default implements IDetectorSessionStorageService {
        @Override // android.service.voice.IDetectorSessionStorageService
        public void openFile(String filename, AndroidFuture future) throws RemoteException {}

        @Override // android.os.IInterface
        public IBinder asBinder() {
            return null;
        }
    }

    public abstract static class Stub extends Binder implements IDetectorSessionStorageService {
        static final int TRANSACTION_openFile = 1;

        public Stub() {
            attachInterface(this, IDetectorSessionStorageService.DESCRIPTOR);
        }

        public static IDetectorSessionStorageService asInterface(IBinder obj) {
            if (obj == null) {
                return null;
            }
            IInterface iin = obj.queryLocalInterface(IDetectorSessionStorageService.DESCRIPTOR);
            if (iin != null && (iin instanceof IDetectorSessionStorageService)) {
                return (IDetectorSessionStorageService) iin;
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
                    return "openFile";
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
                data.enforceInterface(IDetectorSessionStorageService.DESCRIPTOR);
            }
            if (code == 1598968902) {
                reply.writeString(IDetectorSessionStorageService.DESCRIPTOR);
                return true;
            }
            switch (code) {
                case 1:
                    String _arg0 = data.readString();
                    AndroidFuture _arg1 =
                            (AndroidFuture) data.readTypedObject(AndroidFuture.CREATOR);
                    data.enforceNoDataAvail();
                    openFile(_arg0, _arg1);
                    return true;
                default:
                    return super.onTransact(code, data, reply, flags);
            }
        }

        private static class Proxy implements IDetectorSessionStorageService {
            private IBinder mRemote;

            Proxy(IBinder remote) {
                this.mRemote = remote;
            }

            @Override // android.os.IInterface
            public IBinder asBinder() {
                return this.mRemote;
            }

            public String getInterfaceDescriptor() {
                return IDetectorSessionStorageService.DESCRIPTOR;
            }

            @Override // android.service.voice.IDetectorSessionStorageService
            public void openFile(String filename, AndroidFuture future) throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                try {
                    _data.writeInterfaceToken(IDetectorSessionStorageService.DESCRIPTOR);
                    _data.writeString(filename);
                    _data.writeTypedObject(future, 0);
                    this.mRemote.transact(1, _data, null, 1);
                } finally {
                    _data.recycle();
                }
            }
        }

        @Override // android.os.Binder
        public int getMaxTransactionId() {
            return 0;
        }
    }
}
