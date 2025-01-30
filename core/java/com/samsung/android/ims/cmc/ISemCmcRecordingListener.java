package com.samsung.android.ims.cmc;

import android.p009os.Binder;
import android.p009os.IBinder;
import android.p009os.IInterface;
import android.p009os.Parcel;
import android.p009os.RemoteException;

/* loaded from: classes5.dex */
public interface ISemCmcRecordingListener extends IInterface {
    public static final String DESCRIPTOR = "com.samsung.android.ims.cmc.ISemCmcRecordingListener";

    void onError(int i, int i2) throws RemoteException;

    void onInfo(int i, int i2) throws RemoteException;

    public static class Default implements ISemCmcRecordingListener {
        @Override // com.samsung.android.ims.cmc.ISemCmcRecordingListener
        public void onInfo(int what, int extra) throws RemoteException {
        }

        @Override // com.samsung.android.ims.cmc.ISemCmcRecordingListener
        public void onError(int what, int extra) throws RemoteException {
        }

        @Override // android.p009os.IInterface
        public IBinder asBinder() {
            return null;
        }
    }

    public static abstract class Stub extends Binder implements ISemCmcRecordingListener {
        static final int TRANSACTION_onError = 2;
        static final int TRANSACTION_onInfo = 1;

        public Stub() {
            attachInterface(this, ISemCmcRecordingListener.DESCRIPTOR);
        }

        public static ISemCmcRecordingListener asInterface(IBinder obj) {
            if (obj == null) {
                return null;
            }
            IInterface iin = obj.queryLocalInterface(ISemCmcRecordingListener.DESCRIPTOR);
            if (iin != null && (iin instanceof ISemCmcRecordingListener)) {
                return (ISemCmcRecordingListener) iin;
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
                    return "onInfo";
                case 2:
                    return "onError";
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
                data.enforceInterface(ISemCmcRecordingListener.DESCRIPTOR);
            }
            switch (code) {
                case IBinder.INTERFACE_TRANSACTION /* 1598968902 */:
                    reply.writeString(ISemCmcRecordingListener.DESCRIPTOR);
                    return true;
                default:
                    switch (code) {
                        case 1:
                            int _arg0 = data.readInt();
                            int _arg1 = data.readInt();
                            data.enforceNoDataAvail();
                            onInfo(_arg0, _arg1);
                            return true;
                        case 2:
                            int _arg02 = data.readInt();
                            int _arg12 = data.readInt();
                            data.enforceNoDataAvail();
                            onError(_arg02, _arg12);
                            return true;
                        default:
                            return super.onTransact(code, data, reply, flags);
                    }
            }
        }

        private static class Proxy implements ISemCmcRecordingListener {
            private IBinder mRemote;

            Proxy(IBinder remote) {
                this.mRemote = remote;
            }

            @Override // android.p009os.IInterface
            public IBinder asBinder() {
                return this.mRemote;
            }

            public String getInterfaceDescriptor() {
                return ISemCmcRecordingListener.DESCRIPTOR;
            }

            @Override // com.samsung.android.ims.cmc.ISemCmcRecordingListener
            public void onInfo(int what, int extra) throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                try {
                    _data.writeInterfaceToken(ISemCmcRecordingListener.DESCRIPTOR);
                    _data.writeInt(what);
                    _data.writeInt(extra);
                    this.mRemote.transact(1, _data, null, 1);
                } finally {
                    _data.recycle();
                }
            }

            @Override // com.samsung.android.ims.cmc.ISemCmcRecordingListener
            public void onError(int what, int extra) throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                try {
                    _data.writeInterfaceToken(ISemCmcRecordingListener.DESCRIPTOR);
                    _data.writeInt(what);
                    _data.writeInt(extra);
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
