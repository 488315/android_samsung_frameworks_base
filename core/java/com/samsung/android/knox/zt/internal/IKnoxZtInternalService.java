package com.samsung.android.knox.zt.internal;

import android.os.Binder;
import android.os.Bundle;
import android.os.IBinder;
import android.os.IInterface;
import android.os.Parcel;
import android.os.RemoteException;

/* loaded from: classes6.dex */
public interface IKnoxZtInternalService extends IInterface {
    public static final String DESCRIPTOR =
            "com.samsung.android.knox.zt.internal.IKnoxZtInternalService";

    void notifyFrameworkEvent(int i, int i2, Bundle bundle) throws RemoteException;

    public static class Default implements IKnoxZtInternalService {
        @Override // com.samsung.android.knox.zt.internal.IKnoxZtInternalService
        public void notifyFrameworkEvent(int where, int what, Bundle bundle)
                throws RemoteException {}

        @Override // android.os.IInterface
        public IBinder asBinder() {
            return null;
        }
    }

    public abstract static class Stub extends Binder implements IKnoxZtInternalService {
        static final int TRANSACTION_notifyFrameworkEvent = 1;

        public Stub() {
            attachInterface(this, IKnoxZtInternalService.DESCRIPTOR);
        }

        public static IKnoxZtInternalService asInterface(IBinder obj) {
            if (obj == null) {
                return null;
            }
            IInterface iin = obj.queryLocalInterface(IKnoxZtInternalService.DESCRIPTOR);
            if (iin != null && (iin instanceof IKnoxZtInternalService)) {
                return (IKnoxZtInternalService) iin;
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
                    return "notifyFrameworkEvent";
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
                data.enforceInterface(IKnoxZtInternalService.DESCRIPTOR);
            }
            if (code == 1598968902) {
                reply.writeString(IKnoxZtInternalService.DESCRIPTOR);
                return true;
            }
            switch (code) {
                case 1:
                    int _arg0 = data.readInt();
                    int _arg1 = data.readInt();
                    Bundle _arg2 = (Bundle) data.readTypedObject(Bundle.CREATOR);
                    data.enforceNoDataAvail();
                    notifyFrameworkEvent(_arg0, _arg1, _arg2);
                    reply.writeNoException();
                    return true;
                default:
                    return super.onTransact(code, data, reply, flags);
            }
        }

        private static class Proxy implements IKnoxZtInternalService {
            private IBinder mRemote;

            Proxy(IBinder remote) {
                this.mRemote = remote;
            }

            @Override // android.os.IInterface
            public IBinder asBinder() {
                return this.mRemote;
            }

            public String getInterfaceDescriptor() {
                return IKnoxZtInternalService.DESCRIPTOR;
            }

            @Override // com.samsung.android.knox.zt.internal.IKnoxZtInternalService
            public void notifyFrameworkEvent(int where, int what, Bundle bundle)
                    throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(IKnoxZtInternalService.DESCRIPTOR);
                    _data.writeInt(where);
                    _data.writeInt(what);
                    _data.writeTypedObject(bundle, 0);
                    this.mRemote.transact(1, _data, _reply, 0);
                    _reply.readException();
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
