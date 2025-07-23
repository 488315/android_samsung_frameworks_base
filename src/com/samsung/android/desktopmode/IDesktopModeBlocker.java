package com.samsung.android.desktopmode;

import android.os.Binder;
import android.os.IBinder;
import android.os.IInterface;
import android.os.Parcel;
import android.os.RemoteException;

/* loaded from: classes6.dex */
public interface IDesktopModeBlocker extends IInterface {
    public static final String DESCRIPTOR = "com.samsung.android.desktopmode.IDesktopModeBlocker";

    public static class Default implements IDesktopModeBlocker {
        @Override // android.os.IInterface
        public IBinder asBinder() {
            return null;
        }

        @Override // com.samsung.android.desktopmode.IDesktopModeBlocker
        public String onBlocked() throws RemoteException {
            return null;
        }
    }

    String onBlocked() throws RemoteException;

    public static abstract class Stub extends Binder implements IDesktopModeBlocker {
        static final int TRANSACTION_onBlocked = 1;

        @Override // android.os.IInterface
        public IBinder asBinder() {
            return this;
        }

        @Override // android.os.Binder
        public int getMaxTransactionId() {
            return 0;
        }

        public Stub() {
            attachInterface(this, IDesktopModeBlocker.DESCRIPTOR);
        }

        public static IDesktopModeBlocker asInterface(IBinder iBinder) {
            if (iBinder == null) {
                return null;
            }
            IInterface queryLocalInterface = iBinder.queryLocalInterface(IDesktopModeBlocker.DESCRIPTOR);
            if (queryLocalInterface != null && (queryLocalInterface instanceof IDesktopModeBlocker)) {
                return (IDesktopModeBlocker) queryLocalInterface;
            }
            return new Proxy(iBinder);
        }

        public static String getDefaultTransactionName(int i) {
            if (i != 1) {
                return null;
            }
            return "onBlocked";
        }

        @Override // android.os.Binder
        public String getTransactionName(int i) {
            return getDefaultTransactionName(i);
        }

        @Override // android.os.Binder
        public boolean onTransact(int i, Parcel parcel, Parcel parcel2, int i2) throws RemoteException {
            if (i >= 1 && i <= 16777215) {
                parcel.enforceInterface(IDesktopModeBlocker.DESCRIPTOR);
            }
            if (i == 1598968902) {
                parcel2.writeString(IDesktopModeBlocker.DESCRIPTOR);
                return true;
            }
            if (i == 1) {
                String onBlocked = onBlocked();
                parcel2.writeNoException();
                parcel2.writeString(onBlocked);
                return true;
            }
            return super.onTransact(i, parcel, parcel2, i2);
        }

        private static class Proxy implements IDesktopModeBlocker {
            private IBinder mRemote;

            Proxy(IBinder iBinder) {
                this.mRemote = iBinder;
            }

            @Override // android.os.IInterface
            public IBinder asBinder() {
                return this.mRemote;
            }

            public String getInterfaceDescriptor() {
                return IDesktopModeBlocker.DESCRIPTOR;
            }

            @Override // com.samsung.android.desktopmode.IDesktopModeBlocker
            public String onBlocked() throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(IDesktopModeBlocker.DESCRIPTOR);
                    this.mRemote.transact(1, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readString();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }
        }
    }
}
