package com.samsung.android.knox.dar.ddar.proxy;

import android.os.Binder;
import android.os.Bundle;
import android.os.IBinder;
import android.os.IInterface;
import android.os.Parcel;
import android.os.RemoteException;

public interface IProxyService extends IInterface {
    public static final String DESCRIPTOR = "com.samsung.android.knox.dar.ddar.proxy.IProxyService";

    void deregisterAgent(String str) throws RemoteException;

    boolean registerAgentByAction(String str, int i, String str2, String str3)
            throws RemoteException;

    boolean registerAgentByMetadata(String str, int i, String str2, String str3)
            throws RemoteException;

    Bundle relay(String str, String str2, String str3, Bundle bundle) throws RemoteException;

    Bundle relayAsync(String str, String str2, String str3, Bundle bundle) throws RemoteException;

    public static class Default implements IProxyService {
        @Override // com.samsung.android.knox.dar.ddar.proxy.IProxyService
        public boolean registerAgentByAction(
                String agentName, int userId, String packageName, String actionName)
                throws RemoteException {
            return false;
        }

        @Override // com.samsung.android.knox.dar.ddar.proxy.IProxyService
        public boolean registerAgentByMetadata(
                String agentName, int userId, String packageName, String metaDataName)
                throws RemoteException {
            return false;
        }

        @Override // com.samsung.android.knox.dar.ddar.proxy.IProxyService
        public void deregisterAgent(String agentName) throws RemoteException {}

        @Override // com.samsung.android.knox.dar.ddar.proxy.IProxyService
        public Bundle relay(String agentName, String svcName, String action, Bundle params)
                throws RemoteException {
            return null;
        }

        @Override // com.samsung.android.knox.dar.ddar.proxy.IProxyService
        public Bundle relayAsync(String agentName, String svcName, String action, Bundle params)
                throws RemoteException {
            return null;
        }

        @Override // android.os.IInterface
        public IBinder asBinder() {
            return null;
        }
    }

    public abstract static class Stub extends Binder implements IProxyService {
        static final int TRANSACTION_deregisterAgent = 3;
        static final int TRANSACTION_registerAgentByAction = 1;
        static final int TRANSACTION_registerAgentByMetadata = 2;
        static final int TRANSACTION_relay = 4;
        static final int TRANSACTION_relayAsync = 5;

        public Stub() {
            attachInterface(this, IProxyService.DESCRIPTOR);
        }

        public static IProxyService asInterface(IBinder obj) {
            if (obj == null) {
                return null;
            }
            IInterface iin = obj.queryLocalInterface(IProxyService.DESCRIPTOR);
            if (iin != null && (iin instanceof IProxyService)) {
                return (IProxyService) iin;
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
                    return "registerAgentByAction";
                case 2:
                    return "registerAgentByMetadata";
                case 3:
                    return "deregisterAgent";
                case 4:
                    return "relay";
                case 5:
                    return "relayAsync";
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
                data.enforceInterface(IProxyService.DESCRIPTOR);
            }
            if (code == 1598968902) {
                reply.writeString(IProxyService.DESCRIPTOR);
                return true;
            }
            switch (code) {
                case 1:
                    String _arg0 = data.readString();
                    int _arg1 = data.readInt();
                    String _arg2 = data.readString();
                    String _arg3 = data.readString();
                    data.enforceNoDataAvail();
                    boolean _result = registerAgentByAction(_arg0, _arg1, _arg2, _arg3);
                    reply.writeNoException();
                    reply.writeBoolean(_result);
                    return true;
                case 2:
                    String _arg02 = data.readString();
                    int _arg12 = data.readInt();
                    String _arg22 = data.readString();
                    String _arg32 = data.readString();
                    data.enforceNoDataAvail();
                    boolean _result2 = registerAgentByMetadata(_arg02, _arg12, _arg22, _arg32);
                    reply.writeNoException();
                    reply.writeBoolean(_result2);
                    return true;
                case 3:
                    String _arg03 = data.readString();
                    data.enforceNoDataAvail();
                    deregisterAgent(_arg03);
                    reply.writeNoException();
                    return true;
                case 4:
                    String _arg04 = data.readString();
                    String _arg13 = data.readString();
                    String _arg23 = data.readString();
                    Bundle _arg33 = (Bundle) data.readTypedObject(Bundle.CREATOR);
                    data.enforceNoDataAvail();
                    Bundle _result3 = relay(_arg04, _arg13, _arg23, _arg33);
                    reply.writeNoException();
                    reply.writeTypedObject(_result3, 1);
                    return true;
                case 5:
                    String _arg05 = data.readString();
                    String _arg14 = data.readString();
                    String _arg24 = data.readString();
                    Bundle _arg34 = (Bundle) data.readTypedObject(Bundle.CREATOR);
                    data.enforceNoDataAvail();
                    Bundle _result4 = relayAsync(_arg05, _arg14, _arg24, _arg34);
                    reply.writeNoException();
                    reply.writeTypedObject(_result4, 1);
                    return true;
                default:
                    return super.onTransact(code, data, reply, flags);
            }
        }

        private static class Proxy implements IProxyService {
            private IBinder mRemote;

            Proxy(IBinder remote) {
                this.mRemote = remote;
            }

            @Override // android.os.IInterface
            public IBinder asBinder() {
                return this.mRemote;
            }

            public String getInterfaceDescriptor() {
                return IProxyService.DESCRIPTOR;
            }

            @Override // com.samsung.android.knox.dar.ddar.proxy.IProxyService
            public boolean registerAgentByAction(
                    String agentName, int userId, String packageName, String actionName)
                    throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(IProxyService.DESCRIPTOR);
                    _data.writeString(agentName);
                    _data.writeInt(userId);
                    _data.writeString(packageName);
                    _data.writeString(actionName);
                    this.mRemote.transact(1, _data, _reply, 0);
                    _reply.readException();
                    boolean _result = _reply.readBoolean();
                    return _result;
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // com.samsung.android.knox.dar.ddar.proxy.IProxyService
            public boolean registerAgentByMetadata(
                    String agentName, int userId, String packageName, String metaDataName)
                    throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(IProxyService.DESCRIPTOR);
                    _data.writeString(agentName);
                    _data.writeInt(userId);
                    _data.writeString(packageName);
                    _data.writeString(metaDataName);
                    this.mRemote.transact(2, _data, _reply, 0);
                    _reply.readException();
                    boolean _result = _reply.readBoolean();
                    return _result;
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // com.samsung.android.knox.dar.ddar.proxy.IProxyService
            public void deregisterAgent(String agentName) throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(IProxyService.DESCRIPTOR);
                    _data.writeString(agentName);
                    this.mRemote.transact(3, _data, _reply, 0);
                    _reply.readException();
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // com.samsung.android.knox.dar.ddar.proxy.IProxyService
            public Bundle relay(String agentName, String svcName, String action, Bundle params)
                    throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(IProxyService.DESCRIPTOR);
                    _data.writeString(agentName);
                    _data.writeString(svcName);
                    _data.writeString(action);
                    _data.writeTypedObject(params, 0);
                    this.mRemote.transact(4, _data, _reply, 0);
                    _reply.readException();
                    Bundle _result = (Bundle) _reply.readTypedObject(Bundle.CREATOR);
                    return _result;
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // com.samsung.android.knox.dar.ddar.proxy.IProxyService
            public Bundle relayAsync(String agentName, String svcName, String action, Bundle params)
                    throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(IProxyService.DESCRIPTOR);
                    _data.writeString(agentName);
                    _data.writeString(svcName);
                    _data.writeString(action);
                    _data.writeTypedObject(params, 0);
                    this.mRemote.transact(5, _data, _reply, 0);
                    _reply.readException();
                    Bundle _result = (Bundle) _reply.readTypedObject(Bundle.CREATOR);
                    return _result;
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }
        }

        @Override // android.os.Binder
        public int getMaxTransactionId() {
            return 4;
        }
    }
}
