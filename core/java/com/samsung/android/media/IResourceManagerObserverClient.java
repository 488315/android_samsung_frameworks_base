package com.samsung.android.media;

import android.os.Binder;
import android.os.IBinder;
import android.os.IInterface;
import android.os.Parcel;
import android.os.RemoteException;

/* loaded from: classes6.dex */
public interface IResourceManagerObserverClient extends IInterface {
    public static final String DESCRIPTOR =
            "com.samsung.android.media.IResourceManagerObserverClient";

    void notify(int i, int i2, int i3, MediaResourceNotifyInfoParcel mediaResourceNotifyInfoParcel)
            throws RemoteException;

    public static class Default implements IResourceManagerObserverClient {
        @Override // com.samsung.android.media.IResourceManagerObserverClient
        public void notify(int msg, int ext1, int ext2, MediaResourceNotifyInfoParcel obj)
                throws RemoteException {}

        @Override // android.os.IInterface
        public IBinder asBinder() {
            return null;
        }
    }

    public abstract static class Stub extends Binder implements IResourceManagerObserverClient {
        static final int TRANSACTION_notify = 1;

        public Stub() {
            attachInterface(this, IResourceManagerObserverClient.DESCRIPTOR);
        }

        public static IResourceManagerObserverClient asInterface(IBinder obj) {
            if (obj == null) {
                return null;
            }
            IInterface iin = obj.queryLocalInterface(IResourceManagerObserverClient.DESCRIPTOR);
            if (iin != null && (iin instanceof IResourceManagerObserverClient)) {
                return (IResourceManagerObserverClient) iin;
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
                    return "notify";
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
                data.enforceInterface(IResourceManagerObserverClient.DESCRIPTOR);
            }
            if (code == 1598968902) {
                reply.writeString(IResourceManagerObserverClient.DESCRIPTOR);
                return true;
            }
            switch (code) {
                case 1:
                    int _arg0 = data.readInt();
                    int _arg1 = data.readInt();
                    int _arg2 = data.readInt();
                    MediaResourceNotifyInfoParcel _arg3 =
                            (MediaResourceNotifyInfoParcel)
                                    data.readTypedObject(MediaResourceNotifyInfoParcel.CREATOR);
                    data.enforceNoDataAvail();
                    notify(_arg0, _arg1, _arg2, _arg3);
                    return true;
                default:
                    return super.onTransact(code, data, reply, flags);
            }
        }

        private static class Proxy implements IResourceManagerObserverClient {
            private IBinder mRemote;

            Proxy(IBinder remote) {
                this.mRemote = remote;
            }

            @Override // android.os.IInterface
            public IBinder asBinder() {
                return this.mRemote;
            }

            public String getInterfaceDescriptor() {
                return IResourceManagerObserverClient.DESCRIPTOR;
            }

            @Override // com.samsung.android.media.IResourceManagerObserverClient
            public void notify(int msg, int ext1, int ext2, MediaResourceNotifyInfoParcel obj)
                    throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                try {
                    _data.writeInterfaceToken(IResourceManagerObserverClient.DESCRIPTOR);
                    _data.writeInt(msg);
                    _data.writeInt(ext1);
                    _data.writeInt(ext2);
                    _data.writeTypedObject(obj, 0);
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
