package com.samsung.android.ims;

import android.os.Binder;
import android.os.IBinder;
import android.os.IInterface;
import android.os.Parcel;
import android.os.RemoteException;

/* loaded from: classes6.dex */
public interface SemImsRegiListener extends IInterface {
    public static final String DESCRIPTOR = "com.samsung.android.ims.SemImsRegiListener";

    public static class Default implements SemImsRegiListener {
        @Override // android.os.IInterface
        public IBinder asBinder() {
            return null;
        }

        @Override // com.samsung.android.ims.SemImsRegiListener
        public void onDeregistered(SemImsRegistration semImsRegistration, SemImsRegistrationError semImsRegistrationError) throws RemoteException {
        }

        @Override // com.samsung.android.ims.SemImsRegiListener
        public void onRegistered(SemImsRegistration semImsRegistration) throws RemoteException {
        }
    }

    void onDeregistered(SemImsRegistration semImsRegistration, SemImsRegistrationError semImsRegistrationError) throws RemoteException;

    void onRegistered(SemImsRegistration semImsRegistration) throws RemoteException;

    public static abstract class Stub extends Binder implements SemImsRegiListener {
        static final int TRANSACTION_onDeregistered = 2;
        static final int TRANSACTION_onRegistered = 1;

        @Override // android.os.IInterface
        public IBinder asBinder() {
            return this;
        }

        @Override // android.os.Binder
        public int getMaxTransactionId() {
            return 1;
        }

        public Stub() {
            attachInterface(this, SemImsRegiListener.DESCRIPTOR);
        }

        public static SemImsRegiListener asInterface(IBinder iBinder) {
            if (iBinder == null) {
                return null;
            }
            IInterface queryLocalInterface = iBinder.queryLocalInterface(SemImsRegiListener.DESCRIPTOR);
            if (queryLocalInterface != null && (queryLocalInterface instanceof SemImsRegiListener)) {
                return (SemImsRegiListener) queryLocalInterface;
            }
            return new Proxy(iBinder);
        }

        public static String getDefaultTransactionName(int i) {
            if (i == 1) {
                return "onRegistered";
            }
            if (i != 2) {
                return null;
            }
            return "onDeregistered";
        }

        @Override // android.os.Binder
        public String getTransactionName(int i) {
            return getDefaultTransactionName(i);
        }

        @Override // android.os.Binder
        public boolean onTransact(int i, Parcel parcel, Parcel parcel2, int i2) throws RemoteException {
            if (i >= 1 && i <= 16777215) {
                parcel.enforceInterface(SemImsRegiListener.DESCRIPTOR);
            }
            if (i == 1598968902) {
                parcel2.writeString(SemImsRegiListener.DESCRIPTOR);
                return true;
            }
            if (i == 1) {
                SemImsRegistration semImsRegistration = (SemImsRegistration) parcel.readTypedObject(SemImsRegistration.CREATOR);
                parcel.enforceNoDataAvail();
                onRegistered(semImsRegistration);
            } else if (i == 2) {
                SemImsRegistration semImsRegistration2 = (SemImsRegistration) parcel.readTypedObject(SemImsRegistration.CREATOR);
                SemImsRegistrationError semImsRegistrationError = (SemImsRegistrationError) parcel.readTypedObject(SemImsRegistrationError.CREATOR);
                parcel.enforceNoDataAvail();
                onDeregistered(semImsRegistration2, semImsRegistrationError);
            } else {
                return super.onTransact(i, parcel, parcel2, i2);
            }
            return true;
        }

        private static class Proxy implements SemImsRegiListener {
            private IBinder mRemote;

            Proxy(IBinder iBinder) {
                this.mRemote = iBinder;
            }

            @Override // android.os.IInterface
            public IBinder asBinder() {
                return this.mRemote;
            }

            public String getInterfaceDescriptor() {
                return SemImsRegiListener.DESCRIPTOR;
            }

            @Override // com.samsung.android.ims.SemImsRegiListener
            public void onRegistered(SemImsRegistration semImsRegistration) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                try {
                    obtain.writeInterfaceToken(SemImsRegiListener.DESCRIPTOR);
                    obtain.writeTypedObject(semImsRegistration, 0);
                    this.mRemote.transact(1, obtain, null, 1);
                } finally {
                    obtain.recycle();
                }
            }

            @Override // com.samsung.android.ims.SemImsRegiListener
            public void onDeregistered(SemImsRegistration semImsRegistration, SemImsRegistrationError semImsRegistrationError) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                try {
                    obtain.writeInterfaceToken(SemImsRegiListener.DESCRIPTOR);
                    obtain.writeTypedObject(semImsRegistration, 0);
                    obtain.writeTypedObject(semImsRegistrationError, 0);
                    this.mRemote.transact(2, obtain, null, 1);
                } finally {
                    obtain.recycle();
                }
            }
        }
    }
}
