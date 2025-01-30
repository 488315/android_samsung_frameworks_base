package com.samsung.android.media;

import android.media.MediaMetrics;
import android.p009os.Binder;
import android.p009os.IBinder;
import android.p009os.IInterface;
import android.p009os.Parcel;
import android.p009os.RemoteException;

/* loaded from: classes5.dex */
public interface IResourceManagerObserver extends IInterface {
    public static final String DESCRIPTOR = "com.samsung.android.media.IResourceManagerObserver";

    void disconnect() throws RemoteException;

    int enableObserver(int i, int i2) throws RemoteException;

    GetCodecCapacityReturn getCodecCapacity(int i) throws RemoteException;

    GetResourceInfoReturn getResourceInfo(int i) throws RemoteException;

    int setResourcePriority(int i) throws RemoteException;

    public static class Default implements IResourceManagerObserver {
        @Override // com.samsung.android.media.IResourceManagerObserver
        public int enableObserver(int listenerType, int enable) throws RemoteException {
            return 0;
        }

        @Override // com.samsung.android.media.IResourceManagerObserver
        public GetResourceInfoReturn getResourceInfo(int type) throws RemoteException {
            return null;
        }

        @Override // com.samsung.android.media.IResourceManagerObserver
        public GetCodecCapacityReturn getCodecCapacity(int param) throws RemoteException {
            return null;
        }

        @Override // com.samsung.android.media.IResourceManagerObserver
        public int setResourcePriority(int priority) throws RemoteException {
            return 0;
        }

        @Override // com.samsung.android.media.IResourceManagerObserver
        public void disconnect() throws RemoteException {
        }

        @Override // android.p009os.IInterface
        public IBinder asBinder() {
            return null;
        }
    }

    public static abstract class Stub extends Binder implements IResourceManagerObserver {
        static final int TRANSACTION_disconnect = 5;
        static final int TRANSACTION_enableObserver = 1;
        static final int TRANSACTION_getCodecCapacity = 3;
        static final int TRANSACTION_getResourceInfo = 2;
        static final int TRANSACTION_setResourcePriority = 4;

        public Stub() {
            attachInterface(this, IResourceManagerObserver.DESCRIPTOR);
        }

        public static IResourceManagerObserver asInterface(IBinder obj) {
            if (obj == null) {
                return null;
            }
            IInterface iin = obj.queryLocalInterface(IResourceManagerObserver.DESCRIPTOR);
            if (iin != null && (iin instanceof IResourceManagerObserver)) {
                return (IResourceManagerObserver) iin;
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
                    return "enableObserver";
                case 2:
                    return "getResourceInfo";
                case 3:
                    return "getCodecCapacity";
                case 4:
                    return "setResourcePriority";
                case 5:
                    return MediaMetrics.Value.DISCONNECT;
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
                data.enforceInterface(IResourceManagerObserver.DESCRIPTOR);
            }
            switch (code) {
                case IBinder.INTERFACE_TRANSACTION /* 1598968902 */:
                    reply.writeString(IResourceManagerObserver.DESCRIPTOR);
                    return true;
                default:
                    switch (code) {
                        case 1:
                            int _arg0 = data.readInt();
                            int _arg1 = data.readInt();
                            data.enforceNoDataAvail();
                            int _result = enableObserver(_arg0, _arg1);
                            reply.writeNoException();
                            reply.writeInt(_result);
                            return true;
                        case 2:
                            int _arg02 = data.readInt();
                            data.enforceNoDataAvail();
                            GetResourceInfoReturn _result2 = getResourceInfo(_arg02);
                            reply.writeNoException();
                            reply.writeTypedObject(_result2, 1);
                            return true;
                        case 3:
                            int _arg03 = data.readInt();
                            data.enforceNoDataAvail();
                            GetCodecCapacityReturn _result3 = getCodecCapacity(_arg03);
                            reply.writeNoException();
                            reply.writeTypedObject(_result3, 1);
                            return true;
                        case 4:
                            int _arg04 = data.readInt();
                            data.enforceNoDataAvail();
                            int _result4 = setResourcePriority(_arg04);
                            reply.writeNoException();
                            reply.writeInt(_result4);
                            return true;
                        case 5:
                            disconnect();
                            reply.writeNoException();
                            return true;
                        default:
                            return super.onTransact(code, data, reply, flags);
                    }
            }
        }

        private static class Proxy implements IResourceManagerObserver {
            private IBinder mRemote;

            Proxy(IBinder remote) {
                this.mRemote = remote;
            }

            @Override // android.p009os.IInterface
            public IBinder asBinder() {
                return this.mRemote;
            }

            public String getInterfaceDescriptor() {
                return IResourceManagerObserver.DESCRIPTOR;
            }

            @Override // com.samsung.android.media.IResourceManagerObserver
            public int enableObserver(int listenerType, int enable) throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(IResourceManagerObserver.DESCRIPTOR);
                    _data.writeInt(listenerType);
                    _data.writeInt(enable);
                    this.mRemote.transact(1, _data, _reply, 0);
                    _reply.readException();
                    int _result = _reply.readInt();
                    return _result;
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // com.samsung.android.media.IResourceManagerObserver
            public GetResourceInfoReturn getResourceInfo(int type) throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(IResourceManagerObserver.DESCRIPTOR);
                    _data.writeInt(type);
                    this.mRemote.transact(2, _data, _reply, 0);
                    _reply.readException();
                    GetResourceInfoReturn _result = (GetResourceInfoReturn) _reply.readTypedObject(GetResourceInfoReturn.CREATOR);
                    return _result;
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // com.samsung.android.media.IResourceManagerObserver
            public GetCodecCapacityReturn getCodecCapacity(int param) throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(IResourceManagerObserver.DESCRIPTOR);
                    _data.writeInt(param);
                    this.mRemote.transact(3, _data, _reply, 0);
                    _reply.readException();
                    GetCodecCapacityReturn _result = (GetCodecCapacityReturn) _reply.readTypedObject(GetCodecCapacityReturn.CREATOR);
                    return _result;
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // com.samsung.android.media.IResourceManagerObserver
            public int setResourcePriority(int priority) throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(IResourceManagerObserver.DESCRIPTOR);
                    _data.writeInt(priority);
                    this.mRemote.transact(4, _data, _reply, 0);
                    _reply.readException();
                    int _result = _reply.readInt();
                    return _result;
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // com.samsung.android.media.IResourceManagerObserver
            public void disconnect() throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(IResourceManagerObserver.DESCRIPTOR);
                    this.mRemote.transact(5, _data, _reply, 0);
                    _reply.readException();
                } finally {
                    _reply.recycle();
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
