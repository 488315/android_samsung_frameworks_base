package com.android.internal.net;

import android.os.Binder;
import android.os.IBinder;
import android.os.IInterface;
import android.os.Parcel;
import android.os.RemoteException;

/* loaded from: classes5.dex */
public interface INetworkWatchlistManager extends IInterface {

    public static class Default implements INetworkWatchlistManager {
        @Override // android.os.IInterface
        public IBinder asBinder() {
            return null;
        }

        @Override // com.android.internal.net.INetworkWatchlistManager
        public byte[] getWatchlistConfigHash() throws RemoteException {
            return null;
        }

        @Override // com.android.internal.net.INetworkWatchlistManager
        public void reloadWatchlist() throws RemoteException {
        }

        @Override // com.android.internal.net.INetworkWatchlistManager
        public void reportWatchlistIfNecessary() throws RemoteException {
        }

        @Override // com.android.internal.net.INetworkWatchlistManager
        public boolean startWatchlistLogging() throws RemoteException {
            return false;
        }

        @Override // com.android.internal.net.INetworkWatchlistManager
        public boolean stopWatchlistLogging() throws RemoteException {
            return false;
        }
    }

    byte[] getWatchlistConfigHash() throws RemoteException;

    void reloadWatchlist() throws RemoteException;

    void reportWatchlistIfNecessary() throws RemoteException;

    boolean startWatchlistLogging() throws RemoteException;

    boolean stopWatchlistLogging() throws RemoteException;

    public static abstract class Stub extends Binder implements INetworkWatchlistManager {
        public static final String DESCRIPTOR = "com.android.internal.net.INetworkWatchlistManager";
        static final int TRANSACTION_getWatchlistConfigHash = 5;
        static final int TRANSACTION_reloadWatchlist = 3;
        static final int TRANSACTION_reportWatchlistIfNecessary = 4;
        static final int TRANSACTION_startWatchlistLogging = 1;
        static final int TRANSACTION_stopWatchlistLogging = 2;

        @Override // android.os.IInterface
        public IBinder asBinder() {
            return this;
        }

        @Override // android.os.Binder
        public int getMaxTransactionId() {
            return 4;
        }

        public Stub() {
            attachInterface(this, DESCRIPTOR);
        }

        public static INetworkWatchlistManager asInterface(IBinder iBinder) {
            if (iBinder == null) {
                return null;
            }
            IInterface iInterfaceQueryLocalInterface = iBinder.queryLocalInterface(DESCRIPTOR);
            if (iInterfaceQueryLocalInterface != null && (iInterfaceQueryLocalInterface instanceof INetworkWatchlistManager)) {
                return (INetworkWatchlistManager) iInterfaceQueryLocalInterface;
            }
            return new Proxy(iBinder);
        }

        public static String getDefaultTransactionName(int i) {
            if (i == 1) {
                return "startWatchlistLogging";
            }
            if (i == 2) {
                return "stopWatchlistLogging";
            }
            if (i == 3) {
                return "reloadWatchlist";
            }
            if (i == 4) {
                return "reportWatchlistIfNecessary";
            }
            if (i != 5) {
                return null;
            }
            return "getWatchlistConfigHash";
        }

        @Override // android.os.Binder
        public String getTransactionName(int i) {
            return getDefaultTransactionName(i);
        }

        @Override // android.os.Binder
        public boolean onTransact(int i, Parcel parcel, Parcel parcel2, int i2) throws RemoteException {
            if (i >= 1 && i <= 16777215) {
                parcel.enforceInterface(DESCRIPTOR);
            }
            if (i == 1598968902) {
                parcel2.writeString(DESCRIPTOR);
                return true;
            }
            if (i == 1) {
                boolean zStartWatchlistLogging = startWatchlistLogging();
                parcel2.writeNoException();
                parcel2.writeBoolean(zStartWatchlistLogging);
            } else if (i == 2) {
                boolean zStopWatchlistLogging = stopWatchlistLogging();
                parcel2.writeNoException();
                parcel2.writeBoolean(zStopWatchlistLogging);
            } else if (i == 3) {
                reloadWatchlist();
                parcel2.writeNoException();
            } else if (i == 4) {
                reportWatchlistIfNecessary();
                parcel2.writeNoException();
            } else if (i == 5) {
                byte[] watchlistConfigHash = getWatchlistConfigHash();
                parcel2.writeNoException();
                parcel2.writeByteArray(watchlistConfigHash);
            } else {
                return super.onTransact(i, parcel, parcel2, i2);
            }
            return true;
        }

        private static class Proxy implements INetworkWatchlistManager {
            private IBinder mRemote;

            Proxy(IBinder iBinder) {
                this.mRemote = iBinder;
            }

            @Override // android.os.IInterface
            public IBinder asBinder() {
                return this.mRemote;
            }

            public String getInterfaceDescriptor() {
                return Stub.DESCRIPTOR;
            }

            @Override // com.android.internal.net.INetworkWatchlistManager
            public boolean startWatchlistLogging() throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    this.mRemote.transact(1, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return parcelObtain2.readBoolean();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // com.android.internal.net.INetworkWatchlistManager
            public boolean stopWatchlistLogging() throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    this.mRemote.transact(2, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return parcelObtain2.readBoolean();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // com.android.internal.net.INetworkWatchlistManager
            public void reloadWatchlist() throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    this.mRemote.transact(3, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // com.android.internal.net.INetworkWatchlistManager
            public void reportWatchlistIfNecessary() throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    this.mRemote.transact(4, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // com.android.internal.net.INetworkWatchlistManager
            public byte[] getWatchlistConfigHash() throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    this.mRemote.transact(5, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return parcelObtain2.createByteArray();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }
        }
    }
}
