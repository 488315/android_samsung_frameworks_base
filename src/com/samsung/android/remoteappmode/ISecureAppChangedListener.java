package com.samsung.android.remoteappmode;

import android.os.Binder;
import android.os.IBinder;
import android.os.IInterface;
import android.os.Parcel;
import android.os.RemoteException;

/* loaded from: classes6.dex */
public interface ISecureAppChangedListener extends IInterface {
    public static final String DESCRIPTOR = "com.samsung.android.remoteappmode.ISecureAppChangedListener";

    public static class Default implements ISecureAppChangedListener {
        @Override // android.os.IInterface
        public IBinder asBinder() {
            return null;
        }

        @Override // com.samsung.android.remoteappmode.ISecureAppChangedListener
        public void onSecuredAppLaunched(int i, String str) throws RemoteException {
        }

        @Override // com.samsung.android.remoteappmode.ISecureAppChangedListener
        public void onSecuredAppRemoved(int i, String str) throws RemoteException {
        }
    }

    void onSecuredAppLaunched(int i, String str) throws RemoteException;

    void onSecuredAppRemoved(int i, String str) throws RemoteException;

    public static abstract class Stub extends Binder implements ISecureAppChangedListener {
        static final int TRANSACTION_onSecuredAppLaunched = 1;
        static final int TRANSACTION_onSecuredAppRemoved = 2;

        @Override // android.os.IInterface
        public IBinder asBinder() {
            return this;
        }

        @Override // android.os.Binder
        public int getMaxTransactionId() {
            return 1;
        }

        public Stub() {
            attachInterface(this, ISecureAppChangedListener.DESCRIPTOR);
        }

        public static ISecureAppChangedListener asInterface(IBinder iBinder) {
            if (iBinder == null) {
                return null;
            }
            IInterface iInterfaceQueryLocalInterface = iBinder.queryLocalInterface(ISecureAppChangedListener.DESCRIPTOR);
            if (iInterfaceQueryLocalInterface != null && (iInterfaceQueryLocalInterface instanceof ISecureAppChangedListener)) {
                return (ISecureAppChangedListener) iInterfaceQueryLocalInterface;
            }
            return new Proxy(iBinder);
        }

        public static String getDefaultTransactionName(int i) {
            if (i == 1) {
                return "onSecuredAppLaunched";
            }
            if (i != 2) {
                return null;
            }
            return "onSecuredAppRemoved";
        }

        @Override // android.os.Binder
        public String getTransactionName(int i) {
            return getDefaultTransactionName(i);
        }

        @Override // android.os.Binder
        public boolean onTransact(int i, Parcel parcel, Parcel parcel2, int i2) throws RemoteException {
            if (i >= 1 && i <= 16777215) {
                parcel.enforceInterface(ISecureAppChangedListener.DESCRIPTOR);
            }
            if (i == 1598968902) {
                parcel2.writeString(ISecureAppChangedListener.DESCRIPTOR);
                return true;
            }
            if (i == 1) {
                int i3 = parcel.readInt();
                String string = parcel.readString();
                parcel.enforceNoDataAvail();
                onSecuredAppLaunched(i3, string);
            } else if (i == 2) {
                int i4 = parcel.readInt();
                String string2 = parcel.readString();
                parcel.enforceNoDataAvail();
                onSecuredAppRemoved(i4, string2);
            } else {
                return super.onTransact(i, parcel, parcel2, i2);
            }
            return true;
        }

        private static class Proxy implements ISecureAppChangedListener {
            private IBinder mRemote;

            Proxy(IBinder iBinder) {
                this.mRemote = iBinder;
            }

            @Override // android.os.IInterface
            public IBinder asBinder() {
                return this.mRemote;
            }

            public String getInterfaceDescriptor() {
                return ISecureAppChangedListener.DESCRIPTOR;
            }

            @Override // com.samsung.android.remoteappmode.ISecureAppChangedListener
            public void onSecuredAppLaunched(int i, String str) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                try {
                    parcelObtain.writeInterfaceToken(ISecureAppChangedListener.DESCRIPTOR);
                    parcelObtain.writeInt(i);
                    parcelObtain.writeString(str);
                    this.mRemote.transact(1, parcelObtain, null, 1);
                } finally {
                    parcelObtain.recycle();
                }
            }

            @Override // com.samsung.android.remoteappmode.ISecureAppChangedListener
            public void onSecuredAppRemoved(int i, String str) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                try {
                    parcelObtain.writeInterfaceToken(ISecureAppChangedListener.DESCRIPTOR);
                    parcelObtain.writeInt(i);
                    parcelObtain.writeString(str);
                    this.mRemote.transact(2, parcelObtain, null, 1);
                } finally {
                    parcelObtain.recycle();
                }
            }
        }
    }
}
