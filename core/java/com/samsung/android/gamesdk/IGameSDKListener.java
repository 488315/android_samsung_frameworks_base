package com.samsung.android.gamesdk;

import android.p009os.Binder;
import android.p009os.IBinder;
import android.p009os.IInterface;
import android.p009os.Parcel;
import android.p009os.RemoteException;

/* loaded from: classes5.dex */
public interface IGameSDKListener extends IInterface {
    public static final String DESCRIPTOR = "com.samsung.android.gamesdk.IGameSDKListener";

    void onHighTempWarning(int i) throws RemoteException;

    void onRefreshRateChanged() throws RemoteException;

    void onReleasedByTimeout() throws RemoteException;

    void onReleasedCpuBoost() throws RemoteException;

    void onReleasedGpuBoost() throws RemoteException;

    public static class Default implements IGameSDKListener {
        @Override // com.samsung.android.gamesdk.IGameSDKListener
        public void onHighTempWarning(int warningLevel) throws RemoteException {
        }

        @Override // com.samsung.android.gamesdk.IGameSDKListener
        public void onRefreshRateChanged() throws RemoteException {
        }

        @Override // com.samsung.android.gamesdk.IGameSDKListener
        public void onReleasedByTimeout() throws RemoteException {
        }

        @Override // com.samsung.android.gamesdk.IGameSDKListener
        public void onReleasedCpuBoost() throws RemoteException {
        }

        @Override // com.samsung.android.gamesdk.IGameSDKListener
        public void onReleasedGpuBoost() throws RemoteException {
        }

        @Override // android.p009os.IInterface
        public IBinder asBinder() {
            return null;
        }
    }

    public static abstract class Stub extends Binder implements IGameSDKListener {
        static final int TRANSACTION_onHighTempWarning = 1;
        static final int TRANSACTION_onRefreshRateChanged = 2;
        static final int TRANSACTION_onReleasedByTimeout = 3;
        static final int TRANSACTION_onReleasedCpuBoost = 4;
        static final int TRANSACTION_onReleasedGpuBoost = 5;

        public Stub() {
            attachInterface(this, IGameSDKListener.DESCRIPTOR);
        }

        public static IGameSDKListener asInterface(IBinder obj) {
            if (obj == null) {
                return null;
            }
            IInterface iin = obj.queryLocalInterface(IGameSDKListener.DESCRIPTOR);
            if (iin != null && (iin instanceof IGameSDKListener)) {
                return (IGameSDKListener) iin;
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
                    return "onHighTempWarning";
                case 2:
                    return "onRefreshRateChanged";
                case 3:
                    return "onReleasedByTimeout";
                case 4:
                    return "onReleasedCpuBoost";
                case 5:
                    return "onReleasedGpuBoost";
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
                data.enforceInterface(IGameSDKListener.DESCRIPTOR);
            }
            switch (code) {
                case IBinder.INTERFACE_TRANSACTION /* 1598968902 */:
                    reply.writeString(IGameSDKListener.DESCRIPTOR);
                    return true;
                default:
                    switch (code) {
                        case 1:
                            int _arg0 = data.readInt();
                            data.enforceNoDataAvail();
                            onHighTempWarning(_arg0);
                            return true;
                        case 2:
                            onRefreshRateChanged();
                            return true;
                        case 3:
                            onReleasedByTimeout();
                            return true;
                        case 4:
                            onReleasedCpuBoost();
                            return true;
                        case 5:
                            onReleasedGpuBoost();
                            return true;
                        default:
                            return super.onTransact(code, data, reply, flags);
                    }
            }
        }

        private static class Proxy implements IGameSDKListener {
            private IBinder mRemote;

            Proxy(IBinder remote) {
                this.mRemote = remote;
            }

            @Override // android.p009os.IInterface
            public IBinder asBinder() {
                return this.mRemote;
            }

            public String getInterfaceDescriptor() {
                return IGameSDKListener.DESCRIPTOR;
            }

            @Override // com.samsung.android.gamesdk.IGameSDKListener
            public void onHighTempWarning(int warningLevel) throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                try {
                    _data.writeInterfaceToken(IGameSDKListener.DESCRIPTOR);
                    _data.writeInt(warningLevel);
                    this.mRemote.transact(1, _data, null, 1);
                } finally {
                    _data.recycle();
                }
            }

            @Override // com.samsung.android.gamesdk.IGameSDKListener
            public void onRefreshRateChanged() throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                try {
                    _data.writeInterfaceToken(IGameSDKListener.DESCRIPTOR);
                    this.mRemote.transact(2, _data, null, 1);
                } finally {
                    _data.recycle();
                }
            }

            @Override // com.samsung.android.gamesdk.IGameSDKListener
            public void onReleasedByTimeout() throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                try {
                    _data.writeInterfaceToken(IGameSDKListener.DESCRIPTOR);
                    this.mRemote.transact(3, _data, null, 1);
                } finally {
                    _data.recycle();
                }
            }

            @Override // com.samsung.android.gamesdk.IGameSDKListener
            public void onReleasedCpuBoost() throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                try {
                    _data.writeInterfaceToken(IGameSDKListener.DESCRIPTOR);
                    this.mRemote.transact(4, _data, null, 1);
                } finally {
                    _data.recycle();
                }
            }

            @Override // com.samsung.android.gamesdk.IGameSDKListener
            public void onReleasedGpuBoost() throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                try {
                    _data.writeInterfaceToken(IGameSDKListener.DESCRIPTOR);
                    this.mRemote.transact(5, _data, null, 1);
                } finally {
                    _data.recycle();
                }
            }
        }

        @Override // android.p009os.Binder
        public int getMaxTransactionId() {
            return 4;
        }
    }
}
