package com.samsung.android.knox.remotecontrol;

import android.os.Binder;
import android.os.IBinder;
import android.os.IInterface;
import android.os.Parcel;
import android.os.RemoteException;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes4.dex */
public interface IRemoteScreenWatcherCallback extends IInterface {
    public static final String DESCRIPTOR = "com.samsung.android.knox.remotecontrol.IRemoteScreenWatcherCallback";

    void onRemoteScreenStart() throws RemoteException;

    void onRemoteScreenStop() throws RemoteException;

    /* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
    public class Default implements IRemoteScreenWatcherCallback {
        @Override // android.os.IInterface
        public IBinder asBinder() {
            return null;
        }

        @Override // com.samsung.android.knox.remotecontrol.IRemoteScreenWatcherCallback
        public void onRemoteScreenStart() throws RemoteException {
        }

        @Override // com.samsung.android.knox.remotecontrol.IRemoteScreenWatcherCallback
        public void onRemoteScreenStop() throws RemoteException {
        }
    }

    /* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
    public abstract class Stub extends Binder implements IRemoteScreenWatcherCallback {
        public static final int TRANSACTION_onRemoteScreenStart = 1;
        public static final int TRANSACTION_onRemoteScreenStop = 2;

        /* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
        class Proxy implements IRemoteScreenWatcherCallback {
            public IBinder mRemote;

            public Proxy(IBinder iBinder) {
                this.mRemote = iBinder;
            }

            @Override // android.os.IInterface
            public IBinder asBinder() {
                return this.mRemote;
            }

            public String getInterfaceDescriptor() {
                return IRemoteScreenWatcherCallback.DESCRIPTOR;
            }

            @Override // com.samsung.android.knox.remotecontrol.IRemoteScreenWatcherCallback
            public void onRemoteScreenStart() throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(IRemoteScreenWatcherCallback.DESCRIPTOR);
                    this.mRemote.transact(1, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.samsung.android.knox.remotecontrol.IRemoteScreenWatcherCallback
            public void onRemoteScreenStop() throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(IRemoteScreenWatcherCallback.DESCRIPTOR);
                    this.mRemote.transact(2, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }
        }

        public Stub() {
            attachInterface(this, IRemoteScreenWatcherCallback.DESCRIPTOR);
        }

        public static IRemoteScreenWatcherCallback asInterface(IBinder iBinder) {
            if (iBinder == null) {
                return null;
            }
            IInterface queryLocalInterface = iBinder.queryLocalInterface(IRemoteScreenWatcherCallback.DESCRIPTOR);
            return (queryLocalInterface == null || !(queryLocalInterface instanceof IRemoteScreenWatcherCallback)) ? new Proxy(iBinder) : (IRemoteScreenWatcherCallback) queryLocalInterface;
        }

        public static String getDefaultTransactionName(int i) {
            if (i == 1) {
                return "onRemoteScreenStart";
            }
            if (i != 2) {
                return null;
            }
            return "onRemoteScreenStop";
        }

        public int getMaxTransactionId() {
            return 1;
        }

        public String getTransactionName(int i) {
            return getDefaultTransactionName(i);
        }

        @Override // android.os.Binder
        public boolean onTransact(int i, Parcel parcel, Parcel parcel2, int i2) throws RemoteException {
            if (i >= 1 && i <= 16777215) {
                parcel.enforceInterface(IRemoteScreenWatcherCallback.DESCRIPTOR);
            }
            if (i == 1598968902) {
                parcel2.writeString(IRemoteScreenWatcherCallback.DESCRIPTOR);
                return true;
            }
            if (i == 1) {
                onRemoteScreenStart();
                parcel2.writeNoException();
            } else {
                if (i != 2) {
                    return super.onTransact(i, parcel, parcel2, i2);
                }
                onRemoteScreenStop();
                parcel2.writeNoException();
            }
            return true;
        }

        @Override // android.os.IInterface
        public IBinder asBinder() {
            return this;
        }
    }
}
