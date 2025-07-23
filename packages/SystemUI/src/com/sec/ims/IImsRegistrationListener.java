package com.sec.ims;

import android.os.Binder;
import android.os.IBinder;
import android.os.IInterface;
import android.os.Parcel;
import android.os.RemoteException;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes4.dex */
public interface IImsRegistrationListener extends IInterface {
    public static final String DESCRIPTOR = "com.sec.ims.IImsRegistrationListener";

    void onDeregistered(ImsRegistration imsRegistration, ImsRegistrationError imsRegistrationError) throws RemoteException;

    void onRegistered(ImsRegistration imsRegistration) throws RemoteException;

    /* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
    public abstract class Stub extends Binder implements IImsRegistrationListener {
        static final int TRANSACTION_onDeregistered = 2;
        static final int TRANSACTION_onRegistered = 1;

        /* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
        class Proxy implements IImsRegistrationListener {
            private IBinder mRemote;

            public Proxy(IBinder iBinder) {
                this.mRemote = iBinder;
            }

            @Override // android.os.IInterface
            public IBinder asBinder() {
                return this.mRemote;
            }

            public String getInterfaceDescriptor() {
                return IImsRegistrationListener.DESCRIPTOR;
            }

            @Override // com.sec.ims.IImsRegistrationListener
            public void onDeregistered(ImsRegistration imsRegistration, ImsRegistrationError imsRegistrationError) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                try {
                    obtain.writeInterfaceToken(IImsRegistrationListener.DESCRIPTOR);
                    obtain.writeTypedObject(imsRegistration, 0);
                    obtain.writeTypedObject(imsRegistrationError, 0);
                    this.mRemote.transact(2, obtain, null, 1);
                } finally {
                    obtain.recycle();
                }
            }

            @Override // com.sec.ims.IImsRegistrationListener
            public void onRegistered(ImsRegistration imsRegistration) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                try {
                    obtain.writeInterfaceToken(IImsRegistrationListener.DESCRIPTOR);
                    obtain.writeTypedObject(imsRegistration, 0);
                    this.mRemote.transact(1, obtain, null, 1);
                } finally {
                    obtain.recycle();
                }
            }
        }

        public Stub() {
            attachInterface(this, IImsRegistrationListener.DESCRIPTOR);
        }

        public static IImsRegistrationListener asInterface(IBinder iBinder) {
            if (iBinder == null) {
                return null;
            }
            IInterface queryLocalInterface = iBinder.queryLocalInterface(IImsRegistrationListener.DESCRIPTOR);
            return (queryLocalInterface == null || !(queryLocalInterface instanceof IImsRegistrationListener)) ? new Proxy(iBinder) : (IImsRegistrationListener) queryLocalInterface;
        }

        @Override // android.os.Binder
        public boolean onTransact(int i, Parcel parcel, Parcel parcel2, int i2) throws RemoteException {
            if (i >= 1 && i <= 16777215) {
                parcel.enforceInterface(IImsRegistrationListener.DESCRIPTOR);
            }
            if (i == 1598968902) {
                parcel2.writeString(IImsRegistrationListener.DESCRIPTOR);
                return true;
            }
            if (i == 1) {
                ImsRegistration imsRegistration = (ImsRegistration) parcel.readTypedObject(ImsRegistration.CREATOR);
                parcel.enforceNoDataAvail();
                onRegistered(imsRegistration);
            } else {
                if (i != 2) {
                    return super.onTransact(i, parcel, parcel2, i2);
                }
                ImsRegistration imsRegistration2 = (ImsRegistration) parcel.readTypedObject(ImsRegistration.CREATOR);
                ImsRegistrationError imsRegistrationError = (ImsRegistrationError) parcel.readTypedObject(ImsRegistrationError.CREATOR);
                parcel.enforceNoDataAvail();
                onDeregistered(imsRegistration2, imsRegistrationError);
            }
            return true;
        }

        @Override // android.os.IInterface
        public IBinder asBinder() {
            return this;
        }
    }

    /* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
    public class Default implements IImsRegistrationListener {
        @Override // android.os.IInterface
        public IBinder asBinder() {
            return null;
        }

        @Override // com.sec.ims.IImsRegistrationListener
        public void onRegistered(ImsRegistration imsRegistration) throws RemoteException {
        }

        @Override // com.sec.ims.IImsRegistrationListener
        public void onDeregistered(ImsRegistration imsRegistration, ImsRegistrationError imsRegistrationError) throws RemoteException {
        }
    }
}
