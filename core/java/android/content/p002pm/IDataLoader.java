package android.content.p002pm;

import android.content.p002pm.IDataLoaderStatusListener;
import android.p009os.Binder;
import android.p009os.IBinder;
import android.p009os.IInterface;
import android.p009os.Parcel;
import android.p009os.RemoteException;

/* loaded from: classes.dex */
public interface IDataLoader extends IInterface {
    public static final String DESCRIPTOR = "android.content.pm.IDataLoader";

    void create(int i, DataLoaderParamsParcel dataLoaderParamsParcel, FileSystemControlParcel fileSystemControlParcel, IDataLoaderStatusListener iDataLoaderStatusListener) throws RemoteException;

    void destroy(int i) throws RemoteException;

    void prepareImage(int i, InstallationFileParcel[] installationFileParcelArr, String[] strArr) throws RemoteException;

    void start(int i) throws RemoteException;

    void stop(int i) throws RemoteException;

    public static class Default implements IDataLoader {
        @Override // android.content.p002pm.IDataLoader
        public void create(int id, DataLoaderParamsParcel params, FileSystemControlParcel control, IDataLoaderStatusListener listener) throws RemoteException {
        }

        @Override // android.content.p002pm.IDataLoader
        public void start(int id) throws RemoteException {
        }

        @Override // android.content.p002pm.IDataLoader
        public void stop(int id) throws RemoteException {
        }

        @Override // android.content.p002pm.IDataLoader
        public void destroy(int id) throws RemoteException {
        }

        @Override // android.content.p002pm.IDataLoader
        public void prepareImage(int id, InstallationFileParcel[] addedFiles, String[] removedFiles) throws RemoteException {
        }

        @Override // android.p009os.IInterface
        public IBinder asBinder() {
            return null;
        }
    }

    public static abstract class Stub extends Binder implements IDataLoader {
        static final int TRANSACTION_create = 1;
        static final int TRANSACTION_destroy = 4;
        static final int TRANSACTION_prepareImage = 5;
        static final int TRANSACTION_start = 2;
        static final int TRANSACTION_stop = 3;

        public Stub() {
            attachInterface(this, IDataLoader.DESCRIPTOR);
        }

        public static IDataLoader asInterface(IBinder obj) {
            if (obj == null) {
                return null;
            }
            IInterface iin = obj.queryLocalInterface(IDataLoader.DESCRIPTOR);
            if (iin != null && (iin instanceof IDataLoader)) {
                return (IDataLoader) iin;
            }
            return new Proxy(obj);
        }

        @Override // android.p009os.IInterface
        public IBinder asBinder() {
            return this;
        }

        public static String getDefaultTransactionName(int transactionCode) {
            switch (transactionCode) {
                case 1:
                    return "create";
                case 2:
                    return "start";
                case 3:
                    return "stop";
                case 4:
                    return "destroy";
                case 5:
                    return "prepareImage";
                default:
                    return null;
            }
        }

        @Override // android.p009os.Binder
        public String getTransactionName(int transactionCode) {
            return getDefaultTransactionName(transactionCode);
        }

        @Override // android.p009os.Binder
        public boolean onTransact(int code, Parcel data, Parcel reply, int flags) throws RemoteException {
            if (code >= 1 && code <= 16777215) {
                data.enforceInterface(IDataLoader.DESCRIPTOR);
            }
            switch (code) {
                case IBinder.INTERFACE_TRANSACTION /* 1598968902 */:
                    reply.writeString(IDataLoader.DESCRIPTOR);
                    return true;
                default:
                    switch (code) {
                        case 1:
                            int _arg0 = data.readInt();
                            DataLoaderParamsParcel _arg1 = (DataLoaderParamsParcel) data.readTypedObject(DataLoaderParamsParcel.CREATOR);
                            FileSystemControlParcel _arg2 = (FileSystemControlParcel) data.readTypedObject(FileSystemControlParcel.CREATOR);
                            IDataLoaderStatusListener _arg3 = IDataLoaderStatusListener.Stub.asInterface(data.readStrongBinder());
                            data.enforceNoDataAvail();
                            create(_arg0, _arg1, _arg2, _arg3);
                            return true;
                        case 2:
                            int _arg02 = data.readInt();
                            data.enforceNoDataAvail();
                            start(_arg02);
                            return true;
                        case 3:
                            int _arg03 = data.readInt();
                            data.enforceNoDataAvail();
                            stop(_arg03);
                            return true;
                        case 4:
                            int _arg04 = data.readInt();
                            data.enforceNoDataAvail();
                            destroy(_arg04);
                            return true;
                        case 5:
                            int _arg05 = data.readInt();
                            InstallationFileParcel[] _arg12 = (InstallationFileParcel[]) data.createTypedArray(InstallationFileParcel.CREATOR);
                            String[] _arg22 = data.createStringArray();
                            data.enforceNoDataAvail();
                            prepareImage(_arg05, _arg12, _arg22);
                            return true;
                        default:
                            return super.onTransact(code, data, reply, flags);
                    }
            }
        }

        private static class Proxy implements IDataLoader {
            private IBinder mRemote;

            Proxy(IBinder remote) {
                this.mRemote = remote;
            }

            @Override // android.p009os.IInterface
            public IBinder asBinder() {
                return this.mRemote;
            }

            public String getInterfaceDescriptor() {
                return IDataLoader.DESCRIPTOR;
            }

            @Override // android.content.p002pm.IDataLoader
            public void create(int id, DataLoaderParamsParcel params, FileSystemControlParcel control, IDataLoaderStatusListener listener) throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                try {
                    _data.writeInterfaceToken(IDataLoader.DESCRIPTOR);
                    _data.writeInt(id);
                    _data.writeTypedObject(params, 0);
                    _data.writeTypedObject(control, 0);
                    _data.writeStrongInterface(listener);
                    this.mRemote.transact(1, _data, null, 1);
                } finally {
                    _data.recycle();
                }
            }

            @Override // android.content.p002pm.IDataLoader
            public void start(int id) throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                try {
                    _data.writeInterfaceToken(IDataLoader.DESCRIPTOR);
                    _data.writeInt(id);
                    this.mRemote.transact(2, _data, null, 1);
                } finally {
                    _data.recycle();
                }
            }

            @Override // android.content.p002pm.IDataLoader
            public void stop(int id) throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                try {
                    _data.writeInterfaceToken(IDataLoader.DESCRIPTOR);
                    _data.writeInt(id);
                    this.mRemote.transact(3, _data, null, 1);
                } finally {
                    _data.recycle();
                }
            }

            @Override // android.content.p002pm.IDataLoader
            public void destroy(int id) throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                try {
                    _data.writeInterfaceToken(IDataLoader.DESCRIPTOR);
                    _data.writeInt(id);
                    this.mRemote.transact(4, _data, null, 1);
                } finally {
                    _data.recycle();
                }
            }

            @Override // android.content.p002pm.IDataLoader
            public void prepareImage(int id, InstallationFileParcel[] addedFiles, String[] removedFiles) throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                try {
                    _data.writeInterfaceToken(IDataLoader.DESCRIPTOR);
                    _data.writeInt(id);
                    _data.writeTypedArray(addedFiles, 0);
                    _data.writeStringArray(removedFiles);
                    this.mRemote.transact(5, _data, null, 1);
                } finally {
                    _data.recycle();
                }
            }
        }

        @Override // android.p009os.Binder
        public int getMaxTransactionId() {
            return 4;
        }
    }
}
