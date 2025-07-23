package com.android.internal.inputmethod;

import android.os.Binder;
import android.os.IBinder;
import android.os.IInterface;
import android.os.Parcel;
import android.os.RemoteException;
import com.android.internal.inputmethod.IInputMethodSession;

/* loaded from: classes5.dex */
public interface IInputMethodSessionCallback extends IInterface {
    public static final String DESCRIPTOR = "com.android.internal.inputmethod.IInputMethodSessionCallback";

    public static class Default implements IInputMethodSessionCallback {
        @Override // android.os.IInterface
        public IBinder asBinder() {
            return null;
        }

        @Override // com.android.internal.inputmethod.IInputMethodSessionCallback
        public void sessionCreated(IInputMethodSession iInputMethodSession) throws RemoteException {
        }
    }

    void sessionCreated(IInputMethodSession iInputMethodSession) throws RemoteException;

    public static abstract class Stub extends Binder implements IInputMethodSessionCallback {
        static final int TRANSACTION_sessionCreated = 1;

        @Override // android.os.IInterface
        public IBinder asBinder() {
            return this;
        }

        @Override // android.os.Binder
        public int getMaxTransactionId() {
            return 0;
        }

        public Stub() {
            attachInterface(this, IInputMethodSessionCallback.DESCRIPTOR);
        }

        public static IInputMethodSessionCallback asInterface(IBinder iBinder) {
            if (iBinder == null) {
                return null;
            }
            IInterface queryLocalInterface = iBinder.queryLocalInterface(IInputMethodSessionCallback.DESCRIPTOR);
            if (queryLocalInterface != null && (queryLocalInterface instanceof IInputMethodSessionCallback)) {
                return (IInputMethodSessionCallback) queryLocalInterface;
            }
            return new Proxy(iBinder);
        }

        public static String getDefaultTransactionName(int i) {
            if (i != 1) {
                return null;
            }
            return "sessionCreated";
        }

        @Override // android.os.Binder
        public String getTransactionName(int i) {
            return getDefaultTransactionName(i);
        }

        @Override // android.os.Binder
        public boolean onTransact(int i, Parcel parcel, Parcel parcel2, int i2) throws RemoteException {
            if (i >= 1 && i <= 16777215) {
                parcel.enforceInterface(IInputMethodSessionCallback.DESCRIPTOR);
            }
            if (i == 1598968902) {
                parcel2.writeString(IInputMethodSessionCallback.DESCRIPTOR);
                return true;
            }
            if (i == 1) {
                IInputMethodSession asInterface = IInputMethodSession.Stub.asInterface(parcel.readStrongBinder());
                parcel.enforceNoDataAvail();
                sessionCreated(asInterface);
                return true;
            }
            return super.onTransact(i, parcel, parcel2, i2);
        }

        private static class Proxy implements IInputMethodSessionCallback {
            private IBinder mRemote;

            Proxy(IBinder iBinder) {
                this.mRemote = iBinder;
            }

            @Override // android.os.IInterface
            public IBinder asBinder() {
                return this.mRemote;
            }

            public String getInterfaceDescriptor() {
                return IInputMethodSessionCallback.DESCRIPTOR;
            }

            @Override // com.android.internal.inputmethod.IInputMethodSessionCallback
            public void sessionCreated(IInputMethodSession iInputMethodSession) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                try {
                    obtain.writeInterfaceToken(IInputMethodSessionCallback.DESCRIPTOR);
                    obtain.writeStrongInterface(iInputMethodSession);
                    this.mRemote.transact(1, obtain, null, 1);
                } finally {
                    obtain.recycle();
                }
            }
        }
    }
}
