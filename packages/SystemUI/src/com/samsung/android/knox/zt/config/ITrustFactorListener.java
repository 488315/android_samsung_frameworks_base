package com.samsung.android.knox.zt.config;

import android.os.Binder;
import android.os.IBinder;
import android.os.IInterface;
import android.os.Parcel;
import android.os.RemoteException;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes4.dex */
public interface ITrustFactorListener extends IInterface {
    public static final String DESCRIPTOR = "com.samsung.android.knox.zt.config.ITrustFactorListener";

    void onFail(String str) throws RemoteException;

    void onStateUpdate() throws RemoteException;

    void onSuccess() throws RemoteException;

    /* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
    public class Default implements ITrustFactorListener {
        @Override // android.os.IInterface
        public IBinder asBinder() {
            return null;
        }

        @Override // com.samsung.android.knox.zt.config.ITrustFactorListener
        public void onStateUpdate() throws RemoteException {
        }

        @Override // com.samsung.android.knox.zt.config.ITrustFactorListener
        public void onSuccess() throws RemoteException {
        }

        @Override // com.samsung.android.knox.zt.config.ITrustFactorListener
        public void onFail(String str) throws RemoteException {
        }
    }

    /* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
    public abstract class Stub extends Binder implements ITrustFactorListener {
        public static final int TRANSACTION_onFail = 2;
        public static final int TRANSACTION_onStateUpdate = 3;
        public static final int TRANSACTION_onSuccess = 1;

        /* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
        class Proxy implements ITrustFactorListener {
            public IBinder mRemote;

            public Proxy(IBinder iBinder) {
                this.mRemote = iBinder;
            }

            @Override // android.os.IInterface
            public IBinder asBinder() {
                return this.mRemote;
            }

            public String getInterfaceDescriptor() {
                return ITrustFactorListener.DESCRIPTOR;
            }

            @Override // com.samsung.android.knox.zt.config.ITrustFactorListener
            public void onFail(String str) throws RemoteException {
                Parcel obtain = Parcel.obtain();
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(ITrustFactorListener.DESCRIPTOR);
                    obtain.writeString(str);
                    this.mRemote.transact(2, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.samsung.android.knox.zt.config.ITrustFactorListener
            public void onStateUpdate() throws RemoteException {
                Parcel obtain = Parcel.obtain();
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(ITrustFactorListener.DESCRIPTOR);
                    this.mRemote.transact(3, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.samsung.android.knox.zt.config.ITrustFactorListener
            public void onSuccess() throws RemoteException {
                Parcel obtain = Parcel.obtain();
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(ITrustFactorListener.DESCRIPTOR);
                    this.mRemote.transact(1, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }
        }

        public Stub() {
            attachInterface(this, ITrustFactorListener.DESCRIPTOR);
        }

        public static ITrustFactorListener asInterface(IBinder iBinder) {
            if (iBinder == null) {
                return null;
            }
            IInterface queryLocalInterface = iBinder.queryLocalInterface(ITrustFactorListener.DESCRIPTOR);
            return (queryLocalInterface == null || !(queryLocalInterface instanceof ITrustFactorListener)) ? new Proxy(iBinder) : (ITrustFactorListener) queryLocalInterface;
        }

        @Override // android.os.Binder
        public boolean onTransact(int i, Parcel parcel, Parcel parcel2, int i2) throws RemoteException {
            if (i >= 1 && i <= 16777215) {
                parcel.enforceInterface(ITrustFactorListener.DESCRIPTOR);
            }
            if (i == 1598968902) {
                parcel2.writeString(ITrustFactorListener.DESCRIPTOR);
                return true;
            }
            if (i == 1) {
                onSuccess();
                parcel2.writeNoException();
            } else if (i == 2) {
                onFail(parcel.readString());
                parcel2.writeNoException();
            } else {
                if (i != 3) {
                    return super.onTransact(i, parcel, parcel2, i2);
                }
                onStateUpdate();
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
