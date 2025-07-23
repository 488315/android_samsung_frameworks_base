package com.samsung.android.mocca;

import android.os.Binder;
import android.os.IBinder;
import android.os.IInterface;
import android.os.Parcel;
import android.os.RemoteException;

/* loaded from: classes6.dex */
public interface IMoccaEventListener extends IInterface {
    public static final String DESCRIPTOR = "com.samsung.android.mocca.IMoccaEventListener";

    public static class Default implements IMoccaEventListener {
        @Override // android.os.IInterface
        public IBinder asBinder() {
            return null;
        }

        @Override // com.samsung.android.mocca.IMoccaEventListener
        public void onContextAvailable(String str) throws RemoteException {
        }

        @Override // com.samsung.android.mocca.IMoccaEventListener
        public void onContextChanged(ContextEvent contextEvent) throws RemoteException {
        }

        @Override // com.samsung.android.mocca.IMoccaEventListener
        public void onContextStopped(String str) throws RemoteException {
        }

        @Override // com.samsung.android.mocca.IMoccaEventListener
        public void onContextUnavailable(String str) throws RemoteException {
        }
    }

    void onContextAvailable(String str) throws RemoteException;

    void onContextChanged(ContextEvent contextEvent) throws RemoteException;

    void onContextStopped(String str) throws RemoteException;

    void onContextUnavailable(String str) throws RemoteException;

    public static abstract class Stub extends Binder implements IMoccaEventListener {
        static final int TRANSACTION_onContextAvailable = 3;
        static final int TRANSACTION_onContextChanged = 1;
        static final int TRANSACTION_onContextStopped = 2;
        static final int TRANSACTION_onContextUnavailable = 4;

        @Override // android.os.IInterface
        public IBinder asBinder() {
            return this;
        }

        @Override // android.os.Binder
        public int getMaxTransactionId() {
            return 3;
        }

        public Stub() {
            attachInterface(this, IMoccaEventListener.DESCRIPTOR);
        }

        public static IMoccaEventListener asInterface(IBinder iBinder) {
            if (iBinder == null) {
                return null;
            }
            IInterface queryLocalInterface = iBinder.queryLocalInterface(IMoccaEventListener.DESCRIPTOR);
            if (queryLocalInterface != null && (queryLocalInterface instanceof IMoccaEventListener)) {
                return (IMoccaEventListener) queryLocalInterface;
            }
            return new Proxy(iBinder);
        }

        public static String getDefaultTransactionName(int i) {
            if (i == 1) {
                return "onContextChanged";
            }
            if (i == 2) {
                return "onContextStopped";
            }
            if (i == 3) {
                return "onContextAvailable";
            }
            if (i != 4) {
                return null;
            }
            return "onContextUnavailable";
        }

        @Override // android.os.Binder
        public String getTransactionName(int i) {
            return getDefaultTransactionName(i);
        }

        @Override // android.os.Binder
        public boolean onTransact(int i, Parcel parcel, Parcel parcel2, int i2) throws RemoteException {
            if (i >= 1 && i <= 16777215) {
                parcel.enforceInterface(IMoccaEventListener.DESCRIPTOR);
            }
            if (i == 1598968902) {
                parcel2.writeString(IMoccaEventListener.DESCRIPTOR);
                return true;
            }
            if (i == 1) {
                ContextEvent contextEvent = (ContextEvent) parcel.readTypedObject(ContextEvent.CREATOR);
                parcel.enforceNoDataAvail();
                onContextChanged(contextEvent);
            } else if (i == 2) {
                String readString = parcel.readString();
                parcel.enforceNoDataAvail();
                onContextStopped(readString);
            } else if (i == 3) {
                String readString2 = parcel.readString();
                parcel.enforceNoDataAvail();
                onContextAvailable(readString2);
            } else if (i == 4) {
                String readString3 = parcel.readString();
                parcel.enforceNoDataAvail();
                onContextUnavailable(readString3);
            } else {
                return super.onTransact(i, parcel, parcel2, i2);
            }
            return true;
        }

        private static class Proxy implements IMoccaEventListener {
            private IBinder mRemote;

            Proxy(IBinder iBinder) {
                this.mRemote = iBinder;
            }

            @Override // android.os.IInterface
            public IBinder asBinder() {
                return this.mRemote;
            }

            public String getInterfaceDescriptor() {
                return IMoccaEventListener.DESCRIPTOR;
            }

            @Override // com.samsung.android.mocca.IMoccaEventListener
            public void onContextChanged(ContextEvent contextEvent) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                try {
                    obtain.writeInterfaceToken(IMoccaEventListener.DESCRIPTOR);
                    obtain.writeTypedObject(contextEvent, 0);
                    this.mRemote.transact(1, obtain, null, 1);
                } finally {
                    obtain.recycle();
                }
            }

            @Override // com.samsung.android.mocca.IMoccaEventListener
            public void onContextStopped(String str) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                try {
                    obtain.writeInterfaceToken(IMoccaEventListener.DESCRIPTOR);
                    obtain.writeString(str);
                    this.mRemote.transact(2, obtain, null, 1);
                } finally {
                    obtain.recycle();
                }
            }

            @Override // com.samsung.android.mocca.IMoccaEventListener
            public void onContextAvailable(String str) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                try {
                    obtain.writeInterfaceToken(IMoccaEventListener.DESCRIPTOR);
                    obtain.writeString(str);
                    this.mRemote.transact(3, obtain, null, 1);
                } finally {
                    obtain.recycle();
                }
            }

            @Override // com.samsung.android.mocca.IMoccaEventListener
            public void onContextUnavailable(String str) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                try {
                    obtain.writeInterfaceToken(IMoccaEventListener.DESCRIPTOR);
                    obtain.writeString(str);
                    this.mRemote.transact(4, obtain, null, 1);
                } finally {
                    obtain.recycle();
                }
            }
        }
    }
}
