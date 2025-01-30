package com.samsung.android.sepunion;

import android.p009os.Binder;
import android.p009os.IBinder;
import android.p009os.IInterface;
import android.p009os.Parcel;
import android.p009os.RemoteException;

/* loaded from: classes5.dex */
public interface IGalaxyRegistryService extends IInterface {
    public static final String DESCRIPTOR = "com.samsung.android.sepunion.IGalaxyRegistryService";

    void registerListener(String str, IBinder iBinder) throws RemoteException;

    void unRegisterListener(String str, IBinder iBinder) throws RemoteException;

    public static class Default implements IGalaxyRegistryService {
        @Override // com.samsung.android.sepunion.IGalaxyRegistryService
        public void registerListener(String packageName, IBinder cb) throws RemoteException {
        }

        @Override // com.samsung.android.sepunion.IGalaxyRegistryService
        public void unRegisterListener(String packageName, IBinder cb) throws RemoteException {
        }

        @Override // android.p009os.IInterface
        public IBinder asBinder() {
            return null;
        }
    }

    public static abstract class Stub extends Binder implements IGalaxyRegistryService {
        static final int TRANSACTION_registerListener = 1;
        static final int TRANSACTION_unRegisterListener = 2;

        public Stub() {
            attachInterface(this, IGalaxyRegistryService.DESCRIPTOR);
        }

        public static IGalaxyRegistryService asInterface(IBinder obj) {
            if (obj == null) {
                return null;
            }
            IInterface iin = obj.queryLocalInterface(IGalaxyRegistryService.DESCRIPTOR);
            if (iin != null && (iin instanceof IGalaxyRegistryService)) {
                return (IGalaxyRegistryService) iin;
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
                    return "registerListener";
                case 2:
                    return "unRegisterListener";
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
                data.enforceInterface(IGalaxyRegistryService.DESCRIPTOR);
            }
            switch (code) {
                case IBinder.INTERFACE_TRANSACTION /* 1598968902 */:
                    reply.writeString(IGalaxyRegistryService.DESCRIPTOR);
                    return true;
                default:
                    switch (code) {
                        case 1:
                            String _arg0 = data.readString();
                            IBinder _arg1 = data.readStrongBinder();
                            data.enforceNoDataAvail();
                            registerListener(_arg0, _arg1);
                            return true;
                        case 2:
                            String _arg02 = data.readString();
                            IBinder _arg12 = data.readStrongBinder();
                            data.enforceNoDataAvail();
                            unRegisterListener(_arg02, _arg12);
                            return true;
                        default:
                            return super.onTransact(code, data, reply, flags);
                    }
            }
        }

        private static class Proxy implements IGalaxyRegistryService {
            private IBinder mRemote;

            Proxy(IBinder remote) {
                this.mRemote = remote;
            }

            @Override // android.p009os.IInterface
            public IBinder asBinder() {
                return this.mRemote;
            }

            public String getInterfaceDescriptor() {
                return IGalaxyRegistryService.DESCRIPTOR;
            }

            @Override // com.samsung.android.sepunion.IGalaxyRegistryService
            public void registerListener(String packageName, IBinder cb) throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                try {
                    _data.writeInterfaceToken(IGalaxyRegistryService.DESCRIPTOR);
                    _data.writeString(packageName);
                    _data.writeStrongBinder(cb);
                    this.mRemote.transact(1, _data, null, 1);
                } finally {
                    _data.recycle();
                }
            }

            @Override // com.samsung.android.sepunion.IGalaxyRegistryService
            public void unRegisterListener(String packageName, IBinder cb) throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                try {
                    _data.writeInterfaceToken(IGalaxyRegistryService.DESCRIPTOR);
                    _data.writeString(packageName);
                    _data.writeStrongBinder(cb);
                    this.mRemote.transact(2, _data, null, 1);
                } finally {
                    _data.recycle();
                }
            }
        }

        @Override // android.p009os.Binder
        public int getMaxTransactionId() {
            return 1;
        }
    }
}
