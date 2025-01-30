package com.samsung.android.sepunion;

import android.p009os.Binder;
import android.p009os.IBinder;
import android.p009os.IInterface;
import android.p009os.Parcel;
import android.p009os.RemoteException;

/* loaded from: classes5.dex */
public interface IGoodCatchDispatcher extends IInterface {
    public static final String DESCRIPTOR = "com.samsung.android.sepunion.IGoodCatchDispatcher";

    void onStart(String str) throws RemoteException;

    void onStop(String str) throws RemoteException;

    public static class Default implements IGoodCatchDispatcher {
        @Override // com.samsung.android.sepunion.IGoodCatchDispatcher
        public void onStart(String function) throws RemoteException {
        }

        @Override // com.samsung.android.sepunion.IGoodCatchDispatcher
        public void onStop(String function) throws RemoteException {
        }

        @Override // android.p009os.IInterface
        public IBinder asBinder() {
            return null;
        }
    }

    public static abstract class Stub extends Binder implements IGoodCatchDispatcher {
        static final int TRANSACTION_onStart = 1;
        static final int TRANSACTION_onStop = 2;

        public Stub() {
            attachInterface(this, IGoodCatchDispatcher.DESCRIPTOR);
        }

        public static IGoodCatchDispatcher asInterface(IBinder obj) {
            if (obj == null) {
                return null;
            }
            IInterface iin = obj.queryLocalInterface(IGoodCatchDispatcher.DESCRIPTOR);
            if (iin != null && (iin instanceof IGoodCatchDispatcher)) {
                return (IGoodCatchDispatcher) iin;
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
                    return "onStart";
                case 2:
                    return "onStop";
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
                data.enforceInterface(IGoodCatchDispatcher.DESCRIPTOR);
            }
            switch (code) {
                case IBinder.INTERFACE_TRANSACTION /* 1598968902 */:
                    reply.writeString(IGoodCatchDispatcher.DESCRIPTOR);
                    return true;
                default:
                    switch (code) {
                        case 1:
                            String _arg0 = data.readString();
                            data.enforceNoDataAvail();
                            onStart(_arg0);
                            return true;
                        case 2:
                            String _arg02 = data.readString();
                            data.enforceNoDataAvail();
                            onStop(_arg02);
                            return true;
                        default:
                            return super.onTransact(code, data, reply, flags);
                    }
            }
        }

        private static class Proxy implements IGoodCatchDispatcher {
            private IBinder mRemote;

            Proxy(IBinder remote) {
                this.mRemote = remote;
            }

            @Override // android.p009os.IInterface
            public IBinder asBinder() {
                return this.mRemote;
            }

            public String getInterfaceDescriptor() {
                return IGoodCatchDispatcher.DESCRIPTOR;
            }

            @Override // com.samsung.android.sepunion.IGoodCatchDispatcher
            public void onStart(String function) throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                try {
                    _data.writeInterfaceToken(IGoodCatchDispatcher.DESCRIPTOR);
                    _data.writeString(function);
                    this.mRemote.transact(1, _data, null, 1);
                } finally {
                    _data.recycle();
                }
            }

            @Override // com.samsung.android.sepunion.IGoodCatchDispatcher
            public void onStop(String function) throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                try {
                    _data.writeInterfaceToken(IGoodCatchDispatcher.DESCRIPTOR);
                    _data.writeString(function);
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
