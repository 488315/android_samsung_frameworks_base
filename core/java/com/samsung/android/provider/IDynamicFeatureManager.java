package com.samsung.android.provider;

import android.os.Binder;
import android.os.IBinder;
import android.os.IInterface;
import android.os.Parcel;
import android.os.RemoteException;

public interface IDynamicFeatureManager extends IInterface {
    public static final String DESCRIPTOR = "com.samsung.android.provider.IDynamicFeatureManager";

    SemDynamicFeature.Properties getProperties(String str, String[] strArr) throws RemoteException;

    boolean sendAbTestResult(String str, String str2, String str3) throws RemoteException;

    public static class Default implements IDynamicFeatureManager {
        @Override // com.samsung.android.provider.IDynamicFeatureManager
        public SemDynamicFeature.Properties getProperties(String namespace, String[] names)
                throws RemoteException {
            return null;
        }

        @Override // com.samsung.android.provider.IDynamicFeatureManager
        public boolean sendAbTestResult(String namespace, String name, String message)
                throws RemoteException {
            return false;
        }

        @Override // android.os.IInterface
        public IBinder asBinder() {
            return null;
        }
    }

    public abstract static class Stub extends Binder implements IDynamicFeatureManager {
        static final int TRANSACTION_getProperties = 1;
        static final int TRANSACTION_sendAbTestResult = 2;

        public Stub() {
            attachInterface(this, IDynamicFeatureManager.DESCRIPTOR);
        }

        public static IDynamicFeatureManager asInterface(IBinder obj) {
            if (obj == null) {
                return null;
            }
            IInterface iin = obj.queryLocalInterface(IDynamicFeatureManager.DESCRIPTOR);
            if (iin != null && (iin instanceof IDynamicFeatureManager)) {
                return (IDynamicFeatureManager) iin;
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
                    return "getProperties";
                case 2:
                    return "sendAbTestResult";
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
                data.enforceInterface(IDynamicFeatureManager.DESCRIPTOR);
            }
            if (code == 1598968902) {
                reply.writeString(IDynamicFeatureManager.DESCRIPTOR);
                return true;
            }
            switch (code) {
                case 1:
                    String _arg0 = data.readString();
                    String[] _arg1 = data.createStringArray();
                    data.enforceNoDataAvail();
                    SemDynamicFeature.Properties _result = getProperties(_arg0, _arg1);
                    reply.writeNoException();
                    reply.writeTypedObject(_result, 1);
                    return true;
                case 2:
                    String _arg02 = data.readString();
                    String _arg12 = data.readString();
                    String _arg2 = data.readString();
                    data.enforceNoDataAvail();
                    boolean _result2 = sendAbTestResult(_arg02, _arg12, _arg2);
                    reply.writeNoException();
                    reply.writeBoolean(_result2);
                    return true;
                default:
                    return super.onTransact(code, data, reply, flags);
            }
        }

        private static class Proxy implements IDynamicFeatureManager {
            private IBinder mRemote;

            Proxy(IBinder remote) {
                this.mRemote = remote;
            }

            @Override // android.os.IInterface
            public IBinder asBinder() {
                return this.mRemote;
            }

            public String getInterfaceDescriptor() {
                return IDynamicFeatureManager.DESCRIPTOR;
            }

            @Override // com.samsung.android.provider.IDynamicFeatureManager
            public SemDynamicFeature.Properties getProperties(String namespace, String[] names)
                    throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(IDynamicFeatureManager.DESCRIPTOR);
                    _data.writeString(namespace);
                    _data.writeStringArray(names);
                    this.mRemote.transact(1, _data, _reply, 0);
                    _reply.readException();
                    SemDynamicFeature.Properties _result =
                            (SemDynamicFeature.Properties)
                                    _reply.readTypedObject(SemDynamicFeature.Properties.CREATOR);
                    return _result;
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // com.samsung.android.provider.IDynamicFeatureManager
            public boolean sendAbTestResult(String namespace, String name, String message)
                    throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(IDynamicFeatureManager.DESCRIPTOR);
                    _data.writeString(namespace);
                    _data.writeString(name);
                    _data.writeString(message);
                    this.mRemote.transact(2, _data, _reply, 0);
                    _reply.readException();
                    boolean _result = _reply.readBoolean();
                    return _result;
                } finally {
                    _reply.recycle();
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
