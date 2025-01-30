package com.samsung.android.cover;

import android.p009os.Binder;
import android.p009os.IBinder;
import android.p009os.IInterface;
import android.p009os.Parcel;
import android.p009os.RemoteException;

/* loaded from: classes5.dex */
public interface ISViewCoverBaseService extends IInterface {
    public static final String DESCRIPTOR = "com.samsung.android.cover.ISViewCoverBaseService";

    boolean isCoverViewShowing() throws RemoteException;

    int onCoverAppCovered(boolean z) throws RemoteException;

    void onSViewCoverHide() throws RemoteException;

    void onSViewCoverShow() throws RemoteException;

    void onSystemReady() throws RemoteException;

    void updateCoverState(CoverState coverState) throws RemoteException;

    public static class Default implements ISViewCoverBaseService {
        @Override // com.samsung.android.cover.ISViewCoverBaseService
        public void onSystemReady() throws RemoteException {
        }

        @Override // com.samsung.android.cover.ISViewCoverBaseService
        public void onSViewCoverShow() throws RemoteException {
        }

        @Override // com.samsung.android.cover.ISViewCoverBaseService
        public void onSViewCoverHide() throws RemoteException {
        }

        @Override // com.samsung.android.cover.ISViewCoverBaseService
        public void updateCoverState(CoverState state) throws RemoteException {
        }

        @Override // com.samsung.android.cover.ISViewCoverBaseService
        public boolean isCoverViewShowing() throws RemoteException {
            return false;
        }

        @Override // com.samsung.android.cover.ISViewCoverBaseService
        public int onCoverAppCovered(boolean covered) throws RemoteException {
            return 0;
        }

        @Override // android.p009os.IInterface
        public IBinder asBinder() {
            return null;
        }
    }

    public static abstract class Stub extends Binder implements ISViewCoverBaseService {
        static final int TRANSACTION_isCoverViewShowing = 5;
        static final int TRANSACTION_onCoverAppCovered = 6;
        static final int TRANSACTION_onSViewCoverHide = 3;
        static final int TRANSACTION_onSViewCoverShow = 2;
        static final int TRANSACTION_onSystemReady = 1;
        static final int TRANSACTION_updateCoverState = 4;

        public Stub() {
            attachInterface(this, ISViewCoverBaseService.DESCRIPTOR);
        }

        public static ISViewCoverBaseService asInterface(IBinder obj) {
            if (obj == null) {
                return null;
            }
            IInterface iin = obj.queryLocalInterface(ISViewCoverBaseService.DESCRIPTOR);
            if (iin != null && (iin instanceof ISViewCoverBaseService)) {
                return (ISViewCoverBaseService) iin;
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
                    return "onSViewCoverShow";
                case 3:
                    return "onSViewCoverHide";
                case 4:
                    return "updateCoverState";
                case 5:
                    return "isCoverViewShowing";
                case 6:
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
                data.enforceInterface(ISViewCoverBaseService.DESCRIPTOR);
            }
            switch (code) {
                case IBinder.INTERFACE_TRANSACTION /* 1598968902 */:
                    reply.writeString(ISViewCoverBaseService.DESCRIPTOR);
                    return true;
                default:
                    switch (code) {
                        case 1:
                            onSystemReady();
                            return true;
                        case 2:
                            onSViewCoverShow();
                            return true;
                        case 3:
                            onSViewCoverHide();
                            return true;
                        case 4:
                            CoverState _arg0 = (CoverState) data.readTypedObject(CoverState.CREATOR);
                            data.enforceNoDataAvail();
                            updateCoverState(_arg0);
                            return true;
                        case 5:
                            boolean _result = isCoverViewShowing();
                            reply.writeNoException();
                            reply.writeBoolean(_result);
                            return true;
                        case 6:
                            boolean _arg02 = data.readBoolean();
                            data.enforceNoDataAvail();
                            int _result2 = onCoverAppCovered(_arg02);
                            reply.writeNoException();
                            reply.writeInt(_result2);
                            return true;
                        default:
                            return super.onTransact(code, data, reply, flags);
                    }
            }
        }

        private static class Proxy implements ISViewCoverBaseService {
            private IBinder mRemote;

            Proxy(IBinder remote) {
                this.mRemote = remote;
            }

            @Override // android.p009os.IInterface
            public IBinder asBinder() {
                return this.mRemote;
            }

            public String getInterfaceDescriptor() {
                return ISViewCoverBaseService.DESCRIPTOR;
            }

            @Override // com.samsung.android.cover.ISViewCoverBaseService
            public void onSystemReady() throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                try {
                    _data.writeInterfaceToken(ISViewCoverBaseService.DESCRIPTOR);
                    this.mRemote.transact(1, _data, null, 1);
                } finally {
                    _data.recycle();
                }
            }

            @Override // com.samsung.android.cover.ISViewCoverBaseService
            public void onSViewCoverShow() throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                try {
                    _data.writeInterfaceToken(ISViewCoverBaseService.DESCRIPTOR);
                    this.mRemote.transact(2, _data, null, 1);
                } finally {
                    _data.recycle();
                }
            }

            @Override // com.samsung.android.cover.ISViewCoverBaseService
            public void onSViewCoverHide() throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                try {
                    _data.writeInterfaceToken(ISViewCoverBaseService.DESCRIPTOR);
                    this.mRemote.transact(3, _data, null, 1);
                } finally {
                    _data.recycle();
                }
            }

            @Override // com.samsung.android.cover.ISViewCoverBaseService
            public void updateCoverState(CoverState state) throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                try {
                    _data.writeInterfaceToken(ISViewCoverBaseService.DESCRIPTOR);
                    _data.writeTypedObject(state, 0);
                    this.mRemote.transact(4, _data, null, 1);
                } finally {
                    _data.recycle();
                }
            }

            @Override // com.samsung.android.cover.ISViewCoverBaseService
            public boolean isCoverViewShowing() throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(ISViewCoverBaseService.DESCRIPTOR);
                    this.mRemote.transact(5, _data, _reply, 0);
                    _reply.readException();
                    boolean _result = _reply.readBoolean();
                    return _result;
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // com.samsung.android.cover.ISViewCoverBaseService
            public int onCoverAppCovered(boolean covered) throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(ISViewCoverBaseService.DESCRIPTOR);
                    _data.writeBoolean(covered);
                    this.mRemote.transact(6, _data, _reply, 0);
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
            return 5;
        }
    }
}
