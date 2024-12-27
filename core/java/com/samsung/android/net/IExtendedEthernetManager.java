package com.samsung.android.net;

import android.net.IpConfiguration;
import android.os.Binder;
import android.os.IBinder;
import android.os.IInterface;
import android.os.Parcel;
import android.os.RemoteException;

/* loaded from: classes6.dex */
public interface IExtendedEthernetManager extends IInterface {
    public static final String DESCRIPTOR = "com.samsung.android.net.IExtendedEthernetManager";

    IpConfiguration getConfiguration(String str) throws RemoteException;

    public static class Default implements IExtendedEthernetManager {
        @Override // com.samsung.android.net.IExtendedEthernetManager
        public IpConfiguration getConfiguration(String iface) throws RemoteException {
            return null;
        }

        @Override // android.os.IInterface
        public IBinder asBinder() {
            return null;
        }
    }

    public static abstract class Stub extends Binder implements IExtendedEthernetManager {
        static final int TRANSACTION_getConfiguration = 1;

        public Stub() {
            attachInterface(this, IExtendedEthernetManager.DESCRIPTOR);
        }

        public static IExtendedEthernetManager asInterface(IBinder obj) {
            if (obj == null) {
                return null;
            }
            IInterface iin = obj.queryLocalInterface(IExtendedEthernetManager.DESCRIPTOR);
            if (iin != null && (iin instanceof IExtendedEthernetManager)) {
                return (IExtendedEthernetManager) iin;
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
                    return "getConfiguration";
                default:
                    return null;
            }
        }

        @Override // android.os.Binder
        public String getTransactionName(int transactionCode) {
            return getDefaultTransactionName(transactionCode);
        }

        @Override // android.os.Binder
        public boolean onTransact(int code, Parcel data, Parcel reply, int flags) throws RemoteException {
            if (code >= 1 && code <= 16777215) {
                data.enforceInterface(IExtendedEthernetManager.DESCRIPTOR);
            }
            if (code == 1598968902) {
                reply.writeString(IExtendedEthernetManager.DESCRIPTOR);
                return true;
            }
            switch (code) {
                case 1:
                    String _arg0 = data.readString();
                    data.enforceNoDataAvail();
                    IpConfiguration _result = getConfiguration(_arg0);
                    reply.writeNoException();
                    reply.writeTypedObject(_result, 1);
                    return true;
                default:
                    return super.onTransact(code, data, reply, flags);
            }
        }

        private static class Proxy implements IExtendedEthernetManager {
            private IBinder mRemote;

            Proxy(IBinder remote) {
                this.mRemote = remote;
            }

            @Override // android.os.IInterface
            public IBinder asBinder() {
                return this.mRemote;
            }

            public String getInterfaceDescriptor() {
                return IExtendedEthernetManager.DESCRIPTOR;
            }

            @Override // com.samsung.android.net.IExtendedEthernetManager
            public IpConfiguration getConfiguration(String iface) throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(IExtendedEthernetManager.DESCRIPTOR);
                    _data.writeString(iface);
                    this.mRemote.transact(1, _data, _reply, 0);
                    _reply.readException();
                    IpConfiguration _result = (IpConfiguration) _reply.readTypedObject(IpConfiguration.CREATOR);
                    return _result;
                } finally {
                    _reply.recycle();
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
