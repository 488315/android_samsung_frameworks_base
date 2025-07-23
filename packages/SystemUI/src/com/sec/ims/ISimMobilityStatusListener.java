package com.sec.ims;

import android.os.Binder;
import android.os.IBinder;
import android.os.IInterface;
import android.os.Parcel;
import android.os.RemoteException;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes4.dex */
public interface ISimMobilityStatusListener extends IInterface {
    public static final String DESCRIPTOR = "com.sec.ims.ISimMobilityStatusListener";

    void onSimMobilityStateChanged(boolean z) throws RemoteException;

    /* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
    public abstract class Stub extends Binder implements ISimMobilityStatusListener {
        static final int TRANSACTION_onSimMobilityStateChanged = 1;

        /* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
        class Proxy implements ISimMobilityStatusListener {
            private IBinder mRemote;

            public Proxy(IBinder iBinder) {
                this.mRemote = iBinder;
            }

            @Override // android.os.IInterface
            public IBinder asBinder() {
                return this.mRemote;
            }

            public String getInterfaceDescriptor() {
                return ISimMobilityStatusListener.DESCRIPTOR;
            }

            @Override // com.sec.ims.ISimMobilityStatusListener
            public void onSimMobilityStateChanged(boolean z) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                try {
                    obtain.writeInterfaceToken(ISimMobilityStatusListener.DESCRIPTOR);
                    obtain.writeBoolean(z);
                    this.mRemote.transact(1, obtain, null, 1);
                } finally {
                    obtain.recycle();
                }
            }
        }

        public Stub() {
            attachInterface(this, ISimMobilityStatusListener.DESCRIPTOR);
        }

        public static ISimMobilityStatusListener asInterface(IBinder iBinder) {
            if (iBinder == null) {
                return null;
            }
            IInterface queryLocalInterface = iBinder.queryLocalInterface(ISimMobilityStatusListener.DESCRIPTOR);
            return (queryLocalInterface == null || !(queryLocalInterface instanceof ISimMobilityStatusListener)) ? new Proxy(iBinder) : (ISimMobilityStatusListener) queryLocalInterface;
        }

        @Override // android.os.Binder
        public boolean onTransact(int i, Parcel parcel, Parcel parcel2, int i2) throws RemoteException {
            if (i >= 1 && i <= 16777215) {
                parcel.enforceInterface(ISimMobilityStatusListener.DESCRIPTOR);
            }
            if (i == 1598968902) {
                parcel2.writeString(ISimMobilityStatusListener.DESCRIPTOR);
                return true;
            }
            if (i != 1) {
                return super.onTransact(i, parcel, parcel2, i2);
            }
            boolean readBoolean = parcel.readBoolean();
            parcel.enforceNoDataAvail();
            onSimMobilityStateChanged(readBoolean);
            return true;
        }

        @Override // android.os.IInterface
        public IBinder asBinder() {
            return this;
        }
    }

    /* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
    public class Default implements ISimMobilityStatusListener {
        @Override // android.os.IInterface
        public IBinder asBinder() {
            return null;
        }

        @Override // com.sec.ims.ISimMobilityStatusListener
        public void onSimMobilityStateChanged(boolean z) throws RemoteException {
        }
    }
}
