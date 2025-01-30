package com.samsung.android.continuity;

import android.p009os.Binder;
import android.p009os.Bundle;
import android.p009os.IBinder;
import android.p009os.IInterface;
import android.p009os.Parcel;
import android.p009os.RemoteException;
import com.samsung.android.continuity.ISemContinuitySimpleListener;

/* loaded from: classes5.dex */
public interface ISemContinuityManager extends IInterface {
    public static final String DESCRIPTOR = "com.samsung.android.continuity.ISemContinuityManager";

    void cancelDownload(String str, int i) throws RemoteException;

    void clearLocalClip(int i) throws RemoteException;

    int getNearbyDeviceCount(int i, int i2) throws RemoteException;

    void registerContinuityCopyListener(ISemContinuitySimpleListener iSemContinuitySimpleListener, int i) throws RemoteException;

    boolean requestDownload(String str, ISemContinuitySimpleListener iSemContinuitySimpleListener, int i) throws RemoteException;

    void setLocalClip(Bundle bundle, int i) throws RemoteException;

    void unregisterContinuityCopyListener(int i) throws RemoteException;

    public static class Default implements ISemContinuityManager {
        @Override // com.samsung.android.continuity.ISemContinuityManager
        public int getNearbyDeviceCount(int filterType, int userId) throws RemoteException {
            return 0;
        }

        @Override // com.samsung.android.continuity.ISemContinuityManager
        public void setLocalClip(Bundle clipBundle, int userId) throws RemoteException {
        }

        @Override // com.samsung.android.continuity.ISemContinuityManager
        public void clearLocalClip(int userId) throws RemoteException {
        }

        @Override // com.samsung.android.continuity.ISemContinuityManager
        public void registerContinuityCopyListener(ISemContinuitySimpleListener listener, int userId) throws RemoteException {
        }

        @Override // com.samsung.android.continuity.ISemContinuityManager
        public void unregisterContinuityCopyListener(int userId) throws RemoteException {
        }

        @Override // com.samsung.android.continuity.ISemContinuityManager
        public boolean requestDownload(String dataId, ISemContinuitySimpleListener listener, int userId) throws RemoteException {
            return false;
        }

        @Override // com.samsung.android.continuity.ISemContinuityManager
        public void cancelDownload(String dataId, int userId) throws RemoteException {
        }

        @Override // android.p009os.IInterface
        public IBinder asBinder() {
            return null;
        }
    }

    public static abstract class Stub extends Binder implements ISemContinuityManager {
        static final int TRANSACTION_cancelDownload = 7;
        static final int TRANSACTION_clearLocalClip = 3;
        static final int TRANSACTION_getNearbyDeviceCount = 1;
        static final int TRANSACTION_registerContinuityCopyListener = 4;
        static final int TRANSACTION_requestDownload = 6;
        static final int TRANSACTION_setLocalClip = 2;
        static final int TRANSACTION_unregisterContinuityCopyListener = 5;

        public Stub() {
            attachInterface(this, ISemContinuityManager.DESCRIPTOR);
        }

        public static ISemContinuityManager asInterface(IBinder obj) {
            if (obj == null) {
                return null;
            }
            IInterface iin = obj.queryLocalInterface(ISemContinuityManager.DESCRIPTOR);
            if (iin != null && (iin instanceof ISemContinuityManager)) {
                return (ISemContinuityManager) iin;
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
                    return "getNearbyDeviceCount";
                case 2:
                    return "setLocalClip";
                case 3:
                    return "clearLocalClip";
                case 4:
                    return "registerContinuityCopyListener";
                case 5:
                    return "unregisterContinuityCopyListener";
                case 6:
                    return "requestDownload";
                case 7:
                    return "cancelDownload";
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
                data.enforceInterface(ISemContinuityManager.DESCRIPTOR);
            }
            switch (code) {
                case IBinder.INTERFACE_TRANSACTION /* 1598968902 */:
                    reply.writeString(ISemContinuityManager.DESCRIPTOR);
                    return true;
                default:
                    switch (code) {
                        case 1:
                            int _arg0 = data.readInt();
                            int _arg1 = data.readInt();
                            data.enforceNoDataAvail();
                            int _result = getNearbyDeviceCount(_arg0, _arg1);
                            reply.writeNoException();
                            reply.writeInt(_result);
                            return true;
                        case 2:
                            Bundle _arg02 = (Bundle) data.readTypedObject(Bundle.CREATOR);
                            int _arg12 = data.readInt();
                            data.enforceNoDataAvail();
                            setLocalClip(_arg02, _arg12);
                            reply.writeNoException();
                            return true;
                        case 3:
                            int _arg03 = data.readInt();
                            data.enforceNoDataAvail();
                            clearLocalClip(_arg03);
                            reply.writeNoException();
                            return true;
                        case 4:
                            ISemContinuitySimpleListener _arg04 = ISemContinuitySimpleListener.Stub.asInterface(data.readStrongBinder());
                            int _arg13 = data.readInt();
                            data.enforceNoDataAvail();
                            registerContinuityCopyListener(_arg04, _arg13);
                            reply.writeNoException();
                            return true;
                        case 5:
                            int _arg05 = data.readInt();
                            data.enforceNoDataAvail();
                            unregisterContinuityCopyListener(_arg05);
                            reply.writeNoException();
                            return true;
                        case 6:
                            String _arg06 = data.readString();
                            ISemContinuitySimpleListener _arg14 = ISemContinuitySimpleListener.Stub.asInterface(data.readStrongBinder());
                            int _arg2 = data.readInt();
                            data.enforceNoDataAvail();
                            boolean _result2 = requestDownload(_arg06, _arg14, _arg2);
                            reply.writeNoException();
                            reply.writeBoolean(_result2);
                            return true;
                        case 7:
                            String _arg07 = data.readString();
                            int _arg15 = data.readInt();
                            data.enforceNoDataAvail();
                            cancelDownload(_arg07, _arg15);
                            reply.writeNoException();
                            return true;
                        default:
                            return super.onTransact(code, data, reply, flags);
                    }
            }
        }

        private static class Proxy implements ISemContinuityManager {
            private IBinder mRemote;

            Proxy(IBinder remote) {
                this.mRemote = remote;
            }

            @Override // android.p009os.IInterface
            public IBinder asBinder() {
                return this.mRemote;
            }

            public String getInterfaceDescriptor() {
                return ISemContinuityManager.DESCRIPTOR;
            }

            @Override // com.samsung.android.continuity.ISemContinuityManager
            public int getNearbyDeviceCount(int filterType, int userId) throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(ISemContinuityManager.DESCRIPTOR);
                    _data.writeInt(filterType);
                    _data.writeInt(userId);
                    this.mRemote.transact(1, _data, _reply, 0);
                    _reply.readException();
                    int _result = _reply.readInt();
                    return _result;
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // com.samsung.android.continuity.ISemContinuityManager
            public void setLocalClip(Bundle clipBundle, int userId) throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(ISemContinuityManager.DESCRIPTOR);
                    _data.writeTypedObject(clipBundle, 0);
                    _data.writeInt(userId);
                    this.mRemote.transact(2, _data, _reply, 0);
                    _reply.readException();
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // com.samsung.android.continuity.ISemContinuityManager
            public void clearLocalClip(int userId) throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(ISemContinuityManager.DESCRIPTOR);
                    _data.writeInt(userId);
                    this.mRemote.transact(3, _data, _reply, 0);
                    _reply.readException();
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // com.samsung.android.continuity.ISemContinuityManager
            public void registerContinuityCopyListener(ISemContinuitySimpleListener listener, int userId) throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(ISemContinuityManager.DESCRIPTOR);
                    _data.writeStrongInterface(listener);
                    _data.writeInt(userId);
                    this.mRemote.transact(4, _data, _reply, 0);
                    _reply.readException();
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // com.samsung.android.continuity.ISemContinuityManager
            public void unregisterContinuityCopyListener(int userId) throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(ISemContinuityManager.DESCRIPTOR);
                    _data.writeInt(userId);
                    this.mRemote.transact(5, _data, _reply, 0);
                    _reply.readException();
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // com.samsung.android.continuity.ISemContinuityManager
            public boolean requestDownload(String dataId, ISemContinuitySimpleListener listener, int userId) throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(ISemContinuityManager.DESCRIPTOR);
                    _data.writeString(dataId);
                    _data.writeStrongInterface(listener);
                    _data.writeInt(userId);
                    this.mRemote.transact(6, _data, _reply, 0);
                    _reply.readException();
                    boolean _result = _reply.readBoolean();
                    return _result;
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // com.samsung.android.continuity.ISemContinuityManager
            public void cancelDownload(String dataId, int userId) throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(ISemContinuityManager.DESCRIPTOR);
                    _data.writeString(dataId);
                    _data.writeInt(userId);
                    this.mRemote.transact(7, _data, _reply, 0);
                    _reply.readException();
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }
        }

        @Override // android.p009os.Binder
        public int getMaxTransactionId() {
            return 6;
        }
    }
}
