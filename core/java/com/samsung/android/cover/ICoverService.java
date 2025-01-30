package com.samsung.android.cover;

import android.p009os.Binder;
import android.p009os.IBinder;
import android.p009os.IInterface;
import android.p009os.Parcel;
import android.p009os.RemoteException;

/* loaded from: classes5.dex */
public interface ICoverService extends IInterface {
    public static final String DESCRIPTOR = "com.samsung.android.cover.ICoverService";

    int onCoverAppCovered(boolean z) throws RemoteException;

    void onSystemReady() throws RemoteException;

    void onUpdateCoverState(CoverState coverState) throws RemoteException;

    public static class Default implements ICoverService {
        @Override // com.samsung.android.cover.ICoverService
        public void onSystemReady() throws RemoteException {
        }

        @Override // com.samsung.android.cover.ICoverService
        public void onUpdateCoverState(CoverState state) throws RemoteException {
        }

        @Override // com.samsung.android.cover.ICoverService
        public int onCoverAppCovered(boolean covered) throws RemoteException {
            return 0;
        }

        @Override // android.p009os.IInterface
        public IBinder asBinder() {
            return null;
        }
    }

    public static abstract class Stub extends Binder implements ICoverService {
        static final int TRANSACTION_onCoverAppCovered = 3;
        static final int TRANSACTION_onSystemReady = 1;
        static final int TRANSACTION_onUpdateCoverState = 2;

        public Stub() {
            attachInterface(this, ICoverService.DESCRIPTOR);
        }

        public static ICoverService asInterface(IBinder obj) {
            if (obj == null) {
                return null;
            }
            IInterface iin = obj.queryLocalInterface(ICoverService.DESCRIPTOR);
            if (iin != null && (iin instanceof ICoverService)) {
                return (ICoverService) iin;
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
                    return "onSystemReady";
                case 2:
                    return "onUpdateCoverState";
                case 3:
                    return "onCoverAppCovered";
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
                data.enforceInterface(ICoverService.DESCRIPTOR);
            }
            switch (code) {
                case IBinder.INTERFACE_TRANSACTION /* 1598968902 */:
                    reply.writeString(ICoverService.DESCRIPTOR);
                    return true;
                default:
                    switch (code) {
                        case 1:
                            onSystemReady();
                            return true;
                        case 2:
                            CoverState _arg0 = (CoverState) data.readTypedObject(CoverState.CREATOR);
                            data.enforceNoDataAvail();
                            onUpdateCoverState(_arg0);
                            return true;
                        case 3:
                            boolean _arg02 = data.readBoolean();
                            data.enforceNoDataAvail();
                            int _result = onCoverAppCovered(_arg02);
                            reply.writeNoException();
                            reply.writeInt(_result);
                            return true;
                        default:
                            return super.onTransact(code, data, reply, flags);
                    }
            }
        }

        private static class Proxy implements ICoverService {
            private IBinder mRemote;

            Proxy(IBinder remote) {
                this.mRemote = remote;
            }

            @Override // android.p009os.IInterface
            public IBinder asBinder() {
                return this.mRemote;
            }

            public String getInterfaceDescriptor() {
                return ICoverService.DESCRIPTOR;
            }

            @Override // com.samsung.android.cover.ICoverService
            public void onSystemReady() throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                try {
                    _data.writeInterfaceToken(ICoverService.DESCRIPTOR);
                    this.mRemote.transact(1, _data, null, 1);
                } finally {
                    _data.recycle();
                }
            }

            @Override // com.samsung.android.cover.ICoverService
            public void onUpdateCoverState(CoverState state) throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                try {
                    _data.writeInterfaceToken(ICoverService.DESCRIPTOR);
                    _data.writeTypedObject(state, 0);
                    this.mRemote.transact(2, _data, null, 1);
                } finally {
                    _data.recycle();
                }
            }

            @Override // com.samsung.android.cover.ICoverService
            public int onCoverAppCovered(boolean covered) throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(ICoverService.DESCRIPTOR);
                    _data.writeBoolean(covered);
                    this.mRemote.transact(3, _data, _reply, 0);
                    _reply.readException();
                    int _result = _reply.readInt();
                    return _result;
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }
        }

        @Override // android.p009os.Binder
        public int getMaxTransactionId() {
            return 2;
        }
    }
}
