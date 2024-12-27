package android.service.ondeviceintelligence;

import android.app.ondeviceintelligence.Feature;
import android.os.Binder;
import android.os.IBinder;
import android.os.IInterface;
import android.os.Parcel;
import android.os.ParcelFileDescriptor;
import android.os.RemoteCallback;
import android.os.RemoteException;

import com.android.internal.infra.AndroidFuture;

public interface IRemoteStorageService extends IInterface {
    public static final String DESCRIPTOR =
            "android.service.ondeviceintelligence.IRemoteStorageService";

    void getReadOnlyFeatureFileDescriptorMap(Feature feature, RemoteCallback remoteCallback)
            throws RemoteException;

    void getReadOnlyFileDescriptor(String str, AndroidFuture<ParcelFileDescriptor> androidFuture)
            throws RemoteException;

    public static class Default implements IRemoteStorageService {
        @Override // android.service.ondeviceintelligence.IRemoteStorageService
        public void getReadOnlyFileDescriptor(
                String filePath, AndroidFuture<ParcelFileDescriptor> future)
                throws RemoteException {}

        @Override // android.service.ondeviceintelligence.IRemoteStorageService
        public void getReadOnlyFeatureFileDescriptorMap(
                Feature feature, RemoteCallback remoteCallback) throws RemoteException {}

        @Override // android.os.IInterface
        public IBinder asBinder() {
            return null;
        }
    }

    public abstract static class Stub extends Binder implements IRemoteStorageService {
        static final int TRANSACTION_getReadOnlyFeatureFileDescriptorMap = 2;
        static final int TRANSACTION_getReadOnlyFileDescriptor = 1;

        public Stub() {
            attachInterface(this, IRemoteStorageService.DESCRIPTOR);
        }

        public static IRemoteStorageService asInterface(IBinder obj) {
            if (obj == null) {
                return null;
            }
            IInterface iin = obj.queryLocalInterface(IRemoteStorageService.DESCRIPTOR);
            if (iin != null && (iin instanceof IRemoteStorageService)) {
                return (IRemoteStorageService) iin;
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
                    return "getReadOnlyFileDescriptor";
                case 2:
                    return "getReadOnlyFeatureFileDescriptorMap";
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
                data.enforceInterface(IRemoteStorageService.DESCRIPTOR);
            }
            if (code == 1598968902) {
                reply.writeString(IRemoteStorageService.DESCRIPTOR);
                return true;
            }
            switch (code) {
                case 1:
                    String _arg0 = data.readString();
                    AndroidFuture<ParcelFileDescriptor> _arg1 =
                            (AndroidFuture) data.readTypedObject(AndroidFuture.CREATOR);
                    data.enforceNoDataAvail();
                    getReadOnlyFileDescriptor(_arg0, _arg1);
                    return true;
                case 2:
                    Feature _arg02 = (Feature) data.readTypedObject(Feature.CREATOR);
                    RemoteCallback _arg12 =
                            (RemoteCallback) data.readTypedObject(RemoteCallback.CREATOR);
                    data.enforceNoDataAvail();
                    getReadOnlyFeatureFileDescriptorMap(_arg02, _arg12);
                    return true;
                default:
                    return super.onTransact(code, data, reply, flags);
            }
        }

        private static class Proxy implements IRemoteStorageService {
            private IBinder mRemote;

            Proxy(IBinder remote) {
                this.mRemote = remote;
            }

            @Override // android.os.IInterface
            public IBinder asBinder() {
                return this.mRemote;
            }

            public String getInterfaceDescriptor() {
                return IRemoteStorageService.DESCRIPTOR;
            }

            @Override // android.service.ondeviceintelligence.IRemoteStorageService
            public void getReadOnlyFileDescriptor(
                    String filePath, AndroidFuture<ParcelFileDescriptor> future)
                    throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                try {
                    _data.writeInterfaceToken(IRemoteStorageService.DESCRIPTOR);
                    _data.writeString(filePath);
                    _data.writeTypedObject(future, 0);
                    this.mRemote.transact(1, _data, null, 1);
                } finally {
                    _data.recycle();
                }
            }

            @Override // android.service.ondeviceintelligence.IRemoteStorageService
            public void getReadOnlyFeatureFileDescriptorMap(
                    Feature feature, RemoteCallback remoteCallback) throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                try {
                    _data.writeInterfaceToken(IRemoteStorageService.DESCRIPTOR);
                    _data.writeTypedObject(feature, 0);
                    _data.writeTypedObject(remoteCallback, 0);
                    this.mRemote.transact(2, _data, null, 1);
                } finally {
                    _data.recycle();
                }
            }
        }

        @Override // android.os.Binder
        public int getMaxTransactionId() {
            return 1;
        }
    }
}
