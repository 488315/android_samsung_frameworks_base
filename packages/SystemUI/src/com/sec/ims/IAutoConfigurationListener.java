package com.sec.ims;

import android.os.Binder;
import android.os.IBinder;
import android.os.IInterface;
import android.os.Parcel;
import android.os.RemoteException;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes4.dex */
public interface IAutoConfigurationListener extends IInterface {
    public static final String DESCRIPTOR = "com.sec.ims.IAutoConfigurationListener";

    void onAutoConfigurationCompleted(boolean z) throws RemoteException;

    void onIidTokenNeeded() throws RemoteException;

    void onMsisdnNumberNeeded() throws RemoteException;

    void onVerificationCodeNeeded() throws RemoteException;

    /* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
    public class Default implements IAutoConfigurationListener {
        @Override // android.os.IInterface
        public IBinder asBinder() {
            return null;
        }

        @Override // com.sec.ims.IAutoConfigurationListener
        public void onIidTokenNeeded() throws RemoteException {
        }

        @Override // com.sec.ims.IAutoConfigurationListener
        public void onMsisdnNumberNeeded() throws RemoteException {
        }

        @Override // com.sec.ims.IAutoConfigurationListener
        public void onVerificationCodeNeeded() throws RemoteException {
        }

        @Override // com.sec.ims.IAutoConfigurationListener
        public void onAutoConfigurationCompleted(boolean z) throws RemoteException {
        }
    }

    /* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
    public abstract class Stub extends Binder implements IAutoConfigurationListener {
        static final int TRANSACTION_onAutoConfigurationCompleted = 4;
        static final int TRANSACTION_onIidTokenNeeded = 3;
        static final int TRANSACTION_onMsisdnNumberNeeded = 2;
        static final int TRANSACTION_onVerificationCodeNeeded = 1;

        /* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
        class Proxy implements IAutoConfigurationListener {
            private IBinder mRemote;

            public Proxy(IBinder iBinder) {
                this.mRemote = iBinder;
            }

            @Override // android.os.IInterface
            public IBinder asBinder() {
                return this.mRemote;
            }

            public String getInterfaceDescriptor() {
                return IAutoConfigurationListener.DESCRIPTOR;
            }

            @Override // com.sec.ims.IAutoConfigurationListener
            public void onAutoConfigurationCompleted(boolean z) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(IAutoConfigurationListener.DESCRIPTOR);
                    obtain.writeBoolean(z);
                    this.mRemote.transact(4, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.sec.ims.IAutoConfigurationListener
            public void onIidTokenNeeded() throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(IAutoConfigurationListener.DESCRIPTOR);
                    this.mRemote.transact(3, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.sec.ims.IAutoConfigurationListener
            public void onMsisdnNumberNeeded() throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(IAutoConfigurationListener.DESCRIPTOR);
                    this.mRemote.transact(2, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.sec.ims.IAutoConfigurationListener
            public void onVerificationCodeNeeded() throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(IAutoConfigurationListener.DESCRIPTOR);
                    this.mRemote.transact(1, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }
        }

        public Stub() {
            attachInterface(this, IAutoConfigurationListener.DESCRIPTOR);
        }

        public static IAutoConfigurationListener asInterface(IBinder iBinder) {
            if (iBinder == null) {
                return null;
            }
            IInterface queryLocalInterface = iBinder.queryLocalInterface(IAutoConfigurationListener.DESCRIPTOR);
            return (queryLocalInterface == null || !(queryLocalInterface instanceof IAutoConfigurationListener)) ? new Proxy(iBinder) : (IAutoConfigurationListener) queryLocalInterface;
        }

        @Override // android.os.Binder
        public boolean onTransact(int i, Parcel parcel, Parcel parcel2, int i2) throws RemoteException {
            if (i >= 1 && i <= 16777215) {
                parcel.enforceInterface(IAutoConfigurationListener.DESCRIPTOR);
            }
            if (i == 1598968902) {
                parcel2.writeString(IAutoConfigurationListener.DESCRIPTOR);
                return true;
            }
            if (i == 1) {
                onVerificationCodeNeeded();
                parcel2.writeNoException();
            } else if (i == 2) {
                onMsisdnNumberNeeded();
                parcel2.writeNoException();
            } else if (i == 3) {
                onIidTokenNeeded();
                parcel2.writeNoException();
            } else {
                if (i != 4) {
                    return super.onTransact(i, parcel, parcel2, i2);
                }
                boolean readBoolean = parcel.readBoolean();
                parcel.enforceNoDataAvail();
                onAutoConfigurationCompleted(readBoolean);
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
