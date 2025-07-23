package com.android.internal.inputmethod;

import android.os.Binder;
import android.os.IBinder;
import android.os.IInterface;
import android.os.Parcel;
import android.os.RemoteException;

/* loaded from: classes5.dex */
public interface IInputContentUriToken extends IInterface {
    public static final String DESCRIPTOR = "com.android.internal.inputmethod.IInputContentUriToken";

    public static class Default implements IInputContentUriToken {
        @Override // android.os.IInterface
        public IBinder asBinder() {
            return null;
        }

        @Override // com.android.internal.inputmethod.IInputContentUriToken
        public void release() throws RemoteException {
        }

        @Override // com.android.internal.inputmethod.IInputContentUriToken
        public void take() throws RemoteException {
        }
    }

    void release() throws RemoteException;

    void take() throws RemoteException;

    public static abstract class Stub extends Binder implements IInputContentUriToken {
        static final int TRANSACTION_release = 2;
        static final int TRANSACTION_take = 1;

        @Override // android.os.IInterface
        public IBinder asBinder() {
            return this;
        }

        @Override // android.os.Binder
        public int getMaxTransactionId() {
            return 1;
        }

        public Stub() {
            attachInterface(this, IInputContentUriToken.DESCRIPTOR);
        }

        public static IInputContentUriToken asInterface(IBinder iBinder) {
            if (iBinder == null) {
                return null;
            }
            IInterface queryLocalInterface = iBinder.queryLocalInterface(IInputContentUriToken.DESCRIPTOR);
            if (queryLocalInterface != null && (queryLocalInterface instanceof IInputContentUriToken)) {
                return (IInputContentUriToken) queryLocalInterface;
            }
            return new Proxy(iBinder);
        }

        public static String getDefaultTransactionName(int i) {
            if (i == 1) {
                return "take";
            }
            if (i != 2) {
                return null;
            }
            return "release";
        }

        @Override // android.os.Binder
        public String getTransactionName(int i) {
            return getDefaultTransactionName(i);
        }

        @Override // android.os.Binder
        public boolean onTransact(int i, Parcel parcel, Parcel parcel2, int i2) throws RemoteException {
            if (i >= 1 && i <= 16777215) {
                parcel.enforceInterface(IInputContentUriToken.DESCRIPTOR);
            }
            if (i == 1598968902) {
                parcel2.writeString(IInputContentUriToken.DESCRIPTOR);
                return true;
            }
            if (i == 1) {
                take();
                parcel2.writeNoException();
            } else if (i == 2) {
                release();
                parcel2.writeNoException();
            } else {
                return super.onTransact(i, parcel, parcel2, i2);
            }
            return true;
        }

        private static class Proxy implements IInputContentUriToken {
            private IBinder mRemote;

            Proxy(IBinder iBinder) {
                this.mRemote = iBinder;
            }

            @Override // android.os.IInterface
            public IBinder asBinder() {
                return this.mRemote;
            }

            public String getInterfaceDescriptor() {
                return IInputContentUriToken.DESCRIPTOR;
            }

            @Override // com.android.internal.inputmethod.IInputContentUriToken
            public void take() throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(IInputContentUriToken.DESCRIPTOR);
                    this.mRemote.transact(1, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.android.internal.inputmethod.IInputContentUriToken
            public void release() throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(IInputContentUriToken.DESCRIPTOR);
                    this.mRemote.transact(2, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }
        }
    }
}
