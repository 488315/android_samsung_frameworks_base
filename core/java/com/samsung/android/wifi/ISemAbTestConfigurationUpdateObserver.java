package com.samsung.android.wifi;

import android.os.Binder;
import android.os.IBinder;
import android.os.IInterface;
import android.os.Parcel;
import android.os.RemoteException;

/* loaded from: classes6.dex */
public interface ISemAbTestConfigurationUpdateObserver extends IInterface {
    public static final String DESCRIPTOR =
            "com.samsung.android.wifi.ISemAbTestConfigurationUpdateObserver";

    void notifyAbTestConfigUpdate(SemAbTestConfiguration semAbTestConfiguration)
            throws RemoteException;

    public static class Default implements ISemAbTestConfigurationUpdateObserver {
        @Override // com.samsung.android.wifi.ISemAbTestConfigurationUpdateObserver
        public void notifyAbTestConfigUpdate(SemAbTestConfiguration config)
                throws RemoteException {}

        @Override // android.os.IInterface
        public IBinder asBinder() {
            return null;
        }
    }

    public abstract static class Stub extends Binder
            implements ISemAbTestConfigurationUpdateObserver {
        static final int TRANSACTION_notifyAbTestConfigUpdate = 1;

        public Stub() {
            attachInterface(this, ISemAbTestConfigurationUpdateObserver.DESCRIPTOR);
        }

        public static ISemAbTestConfigurationUpdateObserver asInterface(IBinder obj) {
            if (obj == null) {
                return null;
            }
            IInterface iin =
                    obj.queryLocalInterface(ISemAbTestConfigurationUpdateObserver.DESCRIPTOR);
            if (iin != null && (iin instanceof ISemAbTestConfigurationUpdateObserver)) {
                return (ISemAbTestConfigurationUpdateObserver) iin;
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
                    return "notifyAbTestConfigUpdate";
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
                data.enforceInterface(ISemAbTestConfigurationUpdateObserver.DESCRIPTOR);
            }
            if (code == 1598968902) {
                reply.writeString(ISemAbTestConfigurationUpdateObserver.DESCRIPTOR);
                return true;
            }
            switch (code) {
                case 1:
                    SemAbTestConfiguration _arg0 =
                            (SemAbTestConfiguration)
                                    data.readTypedObject(SemAbTestConfiguration.CREATOR);
                    data.enforceNoDataAvail();
                    notifyAbTestConfigUpdate(_arg0);
                    return true;
                default:
                    return super.onTransact(code, data, reply, flags);
            }
        }

        private static class Proxy implements ISemAbTestConfigurationUpdateObserver {
            private IBinder mRemote;

            Proxy(IBinder remote) {
                this.mRemote = remote;
            }

            @Override // android.os.IInterface
            public IBinder asBinder() {
                return this.mRemote;
            }

            public String getInterfaceDescriptor() {
                return ISemAbTestConfigurationUpdateObserver.DESCRIPTOR;
            }

            @Override // com.samsung.android.wifi.ISemAbTestConfigurationUpdateObserver
            public void notifyAbTestConfigUpdate(SemAbTestConfiguration config)
                    throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                try {
                    _data.writeInterfaceToken(ISemAbTestConfigurationUpdateObserver.DESCRIPTOR);
                    _data.writeTypedObject(config, 0);
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
