package com.android.internal.textservice;

import android.os.Binder;
import android.os.IBinder;
import android.os.IInterface;
import android.os.Parcel;
import android.os.RemoteException;
import com.android.internal.textservice.ISpellCheckerSession;

/* loaded from: classes4.dex */
public interface ISpellCheckerServiceCallback extends IInterface {

    public static class Default implements ISpellCheckerServiceCallback {
        @Override // android.os.IInterface
        public IBinder asBinder() {
            return null;
        }

        @Override // com.android.internal.textservice.ISpellCheckerServiceCallback
        public void onSessionCreated(ISpellCheckerSession iSpellCheckerSession) throws RemoteException {
        }
    }

    void onSessionCreated(ISpellCheckerSession iSpellCheckerSession) throws RemoteException;

    public static abstract class Stub extends Binder implements ISpellCheckerServiceCallback {
        public static final String DESCRIPTOR = "com.android.internal.textservice.ISpellCheckerServiceCallback";
        static final int TRANSACTION_onSessionCreated = 1;

        @Override // android.os.IInterface
        public IBinder asBinder() {
            return this;
        }

        @Override // android.os.Binder
        public int getMaxTransactionId() {
            return 0;
        }

        public Stub() {
            attachInterface(this, DESCRIPTOR);
        }

        public static ISpellCheckerServiceCallback asInterface(IBinder iBinder) {
            if (iBinder == null) {
                return null;
            }
            IInterface queryLocalInterface = iBinder.queryLocalInterface(DESCRIPTOR);
            if (queryLocalInterface != null && (queryLocalInterface instanceof ISpellCheckerServiceCallback)) {
                return (ISpellCheckerServiceCallback) queryLocalInterface;
            }
            return new Proxy(iBinder);
        }

        public static String getDefaultTransactionName(int i) {
            if (i != 1) {
                return null;
            }
            return "onSessionCreated";
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
                ISpellCheckerSession asInterface = ISpellCheckerSession.Stub.asInterface(parcel.readStrongBinder());
                parcel.enforceNoDataAvail();
                onSessionCreated(asInterface);
                return true;
            }
            return super.onTransact(i, parcel, parcel2, i2);
        }

        private static class Proxy implements ISpellCheckerServiceCallback {
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

            @Override // com.android.internal.textservice.ISpellCheckerServiceCallback
            public void onSessionCreated(ISpellCheckerSession iSpellCheckerSession) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    obtain.writeStrongInterface(iSpellCheckerSession);
                    this.mRemote.transact(1, obtain, null, 1);
                } finally {
                    obtain.recycle();
                }
            }
        }
    }
}
