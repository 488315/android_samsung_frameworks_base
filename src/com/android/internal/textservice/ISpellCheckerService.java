package com.android.internal.textservice;

import android.os.Binder;
import android.os.Bundle;
import android.os.IBinder;
import android.os.IInterface;
import android.os.Parcel;
import android.os.RemoteException;
import com.android.internal.textservice.ISpellCheckerServiceCallback;
import com.android.internal.textservice.ISpellCheckerSessionListener;

/* loaded from: classes4.dex */
public interface ISpellCheckerService extends IInterface {

    public static class Default implements ISpellCheckerService {
        @Override // android.os.IInterface
        public IBinder asBinder() {
            return null;
        }

        @Override // com.android.internal.textservice.ISpellCheckerService
        public void getISpellCheckerSession(String str, ISpellCheckerSessionListener iSpellCheckerSessionListener, Bundle bundle, int i, ISpellCheckerServiceCallback iSpellCheckerServiceCallback) throws RemoteException {
        }
    }

    void getISpellCheckerSession(String str, ISpellCheckerSessionListener iSpellCheckerSessionListener, Bundle bundle, int i, ISpellCheckerServiceCallback iSpellCheckerServiceCallback) throws RemoteException;

    public static abstract class Stub extends Binder implements ISpellCheckerService {
        public static final String DESCRIPTOR = "com.android.internal.textservice.ISpellCheckerService";
        static final int TRANSACTION_getISpellCheckerSession = 1;

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

        public static ISpellCheckerService asInterface(IBinder iBinder) {
            if (iBinder == null) {
                return null;
            }
            IInterface queryLocalInterface = iBinder.queryLocalInterface(DESCRIPTOR);
            if (queryLocalInterface != null && (queryLocalInterface instanceof ISpellCheckerService)) {
                return (ISpellCheckerService) queryLocalInterface;
            }
            return new Proxy(iBinder);
        }

        public static String getDefaultTransactionName(int i) {
            if (i != 1) {
                return null;
            }
            return "getISpellCheckerSession";
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
                String readString = parcel.readString();
                ISpellCheckerSessionListener asInterface = ISpellCheckerSessionListener.Stub.asInterface(parcel.readStrongBinder());
                Bundle bundle = (Bundle) parcel.readTypedObject(Bundle.CREATOR);
                int readInt = parcel.readInt();
                ISpellCheckerServiceCallback asInterface2 = ISpellCheckerServiceCallback.Stub.asInterface(parcel.readStrongBinder());
                parcel.enforceNoDataAvail();
                getISpellCheckerSession(readString, asInterface, bundle, readInt, asInterface2);
                return true;
            }
            return super.onTransact(i, parcel, parcel2, i2);
        }

        private static class Proxy implements ISpellCheckerService {
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

            @Override // com.android.internal.textservice.ISpellCheckerService
            public void getISpellCheckerSession(String str, ISpellCheckerSessionListener iSpellCheckerSessionListener, Bundle bundle, int i, ISpellCheckerServiceCallback iSpellCheckerServiceCallback) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    obtain.writeString(str);
                    obtain.writeStrongInterface(iSpellCheckerSessionListener);
                    obtain.writeTypedObject(bundle, 0);
                    obtain.writeInt(i);
                    obtain.writeStrongInterface(iSpellCheckerServiceCallback);
                    this.mRemote.transact(1, obtain, null, 1);
                } finally {
                    obtain.recycle();
                }
            }
        }
    }
}
