package com.sec.android.allshare.iface;

import android.os.Binder;
import android.os.IBinder;
import android.os.IInterface;
import android.os.Parcel;
import android.os.RemoteException;

/* loaded from: classes6.dex */
public interface ISubscriber extends IInterface {
    public static final String DESCRIPTOR = "com.sec.android.allshare.iface.ISubscriber";

    String getCaptionFilePathFromURI(String str) throws RemoteException;

    String getServiceVersion() throws RemoteException;

    boolean requestCVAsync(String str, CVMessage cVMessage) throws RemoteException;

    CVMessage requestCVSync(String str, CVMessage cVMessage) throws RemoteException;

    boolean subscribeEvent(String str, CVMessage cVMessage) throws RemoteException;

    void unsubscribeEvent(String str, CVMessage cVMessage) throws RemoteException;

    public static class Default implements ISubscriber {
        @Override // com.sec.android.allshare.iface.ISubscriber
        public boolean requestCVAsync(String subscriber, CVMessage cvm) throws RemoteException {
            return false;
        }

        @Override // com.sec.android.allshare.iface.ISubscriber
        public CVMessage requestCVSync(String subscriber, CVMessage cvm) throws RemoteException {
            return null;
        }

        @Override // com.sec.android.allshare.iface.ISubscriber
        public boolean subscribeEvent(String subscriber, CVMessage cvm) throws RemoteException {
            return false;
        }

        @Override // com.sec.android.allshare.iface.ISubscriber
        public void unsubscribeEvent(String subscriber, CVMessage cvm) throws RemoteException {}

        @Override // com.sec.android.allshare.iface.ISubscriber
        public String getServiceVersion() throws RemoteException {
            return null;
        }

        @Override // com.sec.android.allshare.iface.ISubscriber
        public String getCaptionFilePathFromURI(String uri) throws RemoteException {
            return null;
        }

        @Override // android.os.IInterface
        public IBinder asBinder() {
            return null;
        }
    }

    public abstract static class Stub extends Binder implements ISubscriber {
        static final int TRANSACTION_getCaptionFilePathFromURI = 6;
        static final int TRANSACTION_getServiceVersion = 5;
        static final int TRANSACTION_requestCVAsync = 1;
        static final int TRANSACTION_requestCVSync = 2;
        static final int TRANSACTION_subscribeEvent = 3;
        static final int TRANSACTION_unsubscribeEvent = 4;

        public Stub() {
            attachInterface(this, ISubscriber.DESCRIPTOR);
        }

        public static ISubscriber asInterface(IBinder obj) {
            if (obj == null) {
                return null;
            }
            IInterface iin = obj.queryLocalInterface(ISubscriber.DESCRIPTOR);
            if (iin != null && (iin instanceof ISubscriber)) {
                return (ISubscriber) iin;
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
                    return "requestCVAsync";
                case 2:
                    return "requestCVSync";
                case 3:
                    return "subscribeEvent";
                case 4:
                    return "unsubscribeEvent";
                case 5:
                    return "getServiceVersion";
                case 6:
                    return "getCaptionFilePathFromURI";
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
                data.enforceInterface(ISubscriber.DESCRIPTOR);
            }
            if (code == 1598968902) {
                reply.writeString(ISubscriber.DESCRIPTOR);
                return true;
            }
            switch (code) {
                case 1:
                    String _arg0 = data.readString();
                    CVMessage _arg1 = (CVMessage) data.readTypedObject(CVMessage.CREATOR);
                    data.enforceNoDataAvail();
                    boolean _result = requestCVAsync(_arg0, _arg1);
                    reply.writeNoException();
                    reply.writeBoolean(_result);
                    return true;
                case 2:
                    String _arg02 = data.readString();
                    CVMessage _arg12 = (CVMessage) data.readTypedObject(CVMessage.CREATOR);
                    data.enforceNoDataAvail();
                    CVMessage _result2 = requestCVSync(_arg02, _arg12);
                    reply.writeNoException();
                    reply.writeTypedObject(_result2, 1);
                    return true;
                case 3:
                    String _arg03 = data.readString();
                    CVMessage _arg13 = (CVMessage) data.readTypedObject(CVMessage.CREATOR);
                    data.enforceNoDataAvail();
                    boolean _result3 = subscribeEvent(_arg03, _arg13);
                    reply.writeNoException();
                    reply.writeBoolean(_result3);
                    return true;
                case 4:
                    String _arg04 = data.readString();
                    CVMessage _arg14 = (CVMessage) data.readTypedObject(CVMessage.CREATOR);
                    data.enforceNoDataAvail();
                    unsubscribeEvent(_arg04, _arg14);
                    reply.writeNoException();
                    return true;
                case 5:
                    String _result4 = getServiceVersion();
                    reply.writeNoException();
                    reply.writeString(_result4);
                    return true;
                case 6:
                    String _arg05 = data.readString();
                    data.enforceNoDataAvail();
                    String _result5 = getCaptionFilePathFromURI(_arg05);
                    reply.writeNoException();
                    reply.writeString(_result5);
                    return true;
                default:
                    return super.onTransact(code, data, reply, flags);
            }
        }

        private static class Proxy implements ISubscriber {
            private IBinder mRemote;

            Proxy(IBinder remote) {
                this.mRemote = remote;
            }

            @Override // android.os.IInterface
            public IBinder asBinder() {
                return this.mRemote;
            }

            public String getInterfaceDescriptor() {
                return ISubscriber.DESCRIPTOR;
            }

            @Override // com.sec.android.allshare.iface.ISubscriber
            public boolean requestCVAsync(String subscriber, CVMessage cvm) throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(ISubscriber.DESCRIPTOR);
                    _data.writeString(subscriber);
                    _data.writeTypedObject(cvm, 0);
                    this.mRemote.transact(1, _data, _reply, 0);
                    _reply.readException();
                    boolean _result = _reply.readBoolean();
                    return _result;
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // com.sec.android.allshare.iface.ISubscriber
            public CVMessage requestCVSync(String subscriber, CVMessage cvm)
                    throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(ISubscriber.DESCRIPTOR);
                    _data.writeString(subscriber);
                    _data.writeTypedObject(cvm, 0);
                    this.mRemote.transact(2, _data, _reply, 0);
                    _reply.readException();
                    CVMessage _result = (CVMessage) _reply.readTypedObject(CVMessage.CREATOR);
                    return _result;
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // com.sec.android.allshare.iface.ISubscriber
            public boolean subscribeEvent(String subscriber, CVMessage cvm) throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(ISubscriber.DESCRIPTOR);
                    _data.writeString(subscriber);
                    _data.writeTypedObject(cvm, 0);
                    this.mRemote.transact(3, _data, _reply, 0);
                    _reply.readException();
                    boolean _result = _reply.readBoolean();
                    return _result;
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // com.sec.android.allshare.iface.ISubscriber
            public void unsubscribeEvent(String subscriber, CVMessage cvm) throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(ISubscriber.DESCRIPTOR);
                    _data.writeString(subscriber);
                    _data.writeTypedObject(cvm, 0);
                    this.mRemote.transact(4, _data, _reply, 0);
                    _reply.readException();
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // com.sec.android.allshare.iface.ISubscriber
            public String getServiceVersion() throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(ISubscriber.DESCRIPTOR);
                    this.mRemote.transact(5, _data, _reply, 0);
                    _reply.readException();
                    String _result = _reply.readString();
                    return _result;
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // com.sec.android.allshare.iface.ISubscriber
            public String getCaptionFilePathFromURI(String uri) throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(ISubscriber.DESCRIPTOR);
                    _data.writeString(uri);
                    this.mRemote.transact(6, _data, _reply, 0);
                    _reply.readException();
                    String _result = _reply.readString();
                    return _result;
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }
        }

        @Override // android.os.Binder
        public int getMaxTransactionId() {
            return 5;
        }
    }
}
