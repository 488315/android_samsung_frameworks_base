package com.samsung.android.ims;

import android.os.Binder;
import android.os.IBinder;
import android.os.IInterface;
import android.os.Parcel;
import android.os.RemoteException;

/* loaded from: classes6.dex */
public interface SemImsRegiListener extends IInterface {
    public static final String DESCRIPTOR = "com.samsung.android.ims.SemImsRegiListener";

    void onDeregistered(
            SemImsRegistration semImsRegistration, SemImsRegistrationError semImsRegistrationError)
            throws RemoteException;

    void onRegistered(SemImsRegistration semImsRegistration) throws RemoteException;

    public static class Default implements SemImsRegiListener {
        @Override // com.samsung.android.ims.SemImsRegiListener
        public void onRegistered(SemImsRegistration reg) throws RemoteException {}

        @Override // com.samsung.android.ims.SemImsRegiListener
        public void onDeregistered(
                SemImsRegistration reg, SemImsRegistrationError registrationError)
                throws RemoteException {}

        @Override // android.os.IInterface
        public IBinder asBinder() {
            return null;
        }
    }

    public abstract static class Stub extends Binder implements SemImsRegiListener {
        static final int TRANSACTION_onDeregistered = 2;
        static final int TRANSACTION_onRegistered = 1;

        public Stub() {
            attachInterface(this, SemImsRegiListener.DESCRIPTOR);
        }

        public static SemImsRegiListener asInterface(IBinder obj) {
            if (obj == null) {
                return null;
            }
            IInterface iin = obj.queryLocalInterface(SemImsRegiListener.DESCRIPTOR);
            if (iin != null && (iin instanceof SemImsRegiListener)) {
                return (SemImsRegiListener) iin;
            }
            return new Proxy(obj);
        }

        @Override // android.os.IInterface
        public IBinder asBinder() {
            return this;
        }

        public static String getDefaultTransactionName(int transactionCode) {
            switch (transactionCode) {
                case 1:
                    return "onRegistered";
                case 2:
                    return "onDeregistered";
                default:
                    return null;
            }
        }

        @Override // android.os.Binder
        public String getTransactionName(int transactionCode) {
            return getDefaultTransactionName(transactionCode);
        }

        @Override // android.os.Binder
        public boolean onTransact(int code, Parcel data, Parcel reply, int flags)
                throws RemoteException {
            if (code >= 1 && code <= 16777215) {
                data.enforceInterface(SemImsRegiListener.DESCRIPTOR);
            }
            if (code == 1598968902) {
                reply.writeString(SemImsRegiListener.DESCRIPTOR);
                return true;
            }
            switch (code) {
                case 1:
                    SemImsRegistration _arg0 =
                            (SemImsRegistration) data.readTypedObject(SemImsRegistration.CREATOR);
                    data.enforceNoDataAvail();
                    onRegistered(_arg0);
                    return true;
                case 2:
                    SemImsRegistration _arg02 =
                            (SemImsRegistration) data.readTypedObject(SemImsRegistration.CREATOR);
                    SemImsRegistrationError _arg1 =
                            (SemImsRegistrationError)
                                    data.readTypedObject(SemImsRegistrationError.CREATOR);
                    data.enforceNoDataAvail();
                    onDeregistered(_arg02, _arg1);
                    return true;
                default:
                    return super.onTransact(code, data, reply, flags);
            }
        }

        private static class Proxy implements SemImsRegiListener {
            private IBinder mRemote;

            Proxy(IBinder remote) {
                this.mRemote = remote;
            }

            @Override // android.os.IInterface
            public IBinder asBinder() {
                return this.mRemote;
            }

            public String getInterfaceDescriptor() {
                return SemImsRegiListener.DESCRIPTOR;
            }

            @Override // com.samsung.android.ims.SemImsRegiListener
            public void onRegistered(SemImsRegistration reg) throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                try {
                    _data.writeInterfaceToken(SemImsRegiListener.DESCRIPTOR);
                    _data.writeTypedObject(reg, 0);
                    this.mRemote.transact(1, _data, null, 1);
                } finally {
                    _data.recycle();
                }
            }

            @Override // com.samsung.android.ims.SemImsRegiListener
            public void onDeregistered(
                    SemImsRegistration reg, SemImsRegistrationError registrationError)
                    throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                try {
                    _data.writeInterfaceToken(SemImsRegiListener.DESCRIPTOR);
                    _data.writeTypedObject(reg, 0);
                    _data.writeTypedObject(registrationError, 0);
                    this.mRemote.transact(2, _data, null, 1);
                } finally {
                    _data.recycle();
                }
            }
        }

        @Override // android.os.Binder
        public int getMaxTransactionId() {
            return 1;
        }
    }
}
