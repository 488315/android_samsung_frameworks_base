package com.samsung.android.cover;

import android.os.Binder;
import android.os.Bundle;
import android.os.IBinder;
import android.os.IInterface;
import android.os.Parcel;
import android.os.RemoteException;

/* loaded from: classes6.dex */
public interface INfcLedCoverTouchListenerCallback extends IInterface {
    public static final String DESCRIPTOR = "com.samsung.android.cover.INfcLedCoverTouchListenerCallback";

    public static class Default implements INfcLedCoverTouchListenerCallback {
        @Override // android.os.IInterface
        public IBinder asBinder() {
            return null;
        }

        @Override // com.samsung.android.cover.INfcLedCoverTouchListenerCallback
        public void onCoverTapLeft() throws RemoteException {
        }

        @Override // com.samsung.android.cover.INfcLedCoverTouchListenerCallback
        public void onCoverTapMid() throws RemoteException {
        }

        @Override // com.samsung.android.cover.INfcLedCoverTouchListenerCallback
        public void onCoverTapRight() throws RemoteException {
        }

        @Override // com.samsung.android.cover.INfcLedCoverTouchListenerCallback
        public void onCoverTouchAccept() throws RemoteException {
        }

        @Override // com.samsung.android.cover.INfcLedCoverTouchListenerCallback
        public void onCoverTouchReject() throws RemoteException {
        }

        @Override // com.samsung.android.cover.INfcLedCoverTouchListenerCallback
        public void onSystemCoverEvent(int i, Bundle bundle) throws RemoteException {
        }
    }

    void onCoverTapLeft() throws RemoteException;

    void onCoverTapMid() throws RemoteException;

    void onCoverTapRight() throws RemoteException;

    void onCoverTouchAccept() throws RemoteException;

    void onCoverTouchReject() throws RemoteException;

    void onSystemCoverEvent(int i, Bundle bundle) throws RemoteException;

    public static abstract class Stub extends Binder implements INfcLedCoverTouchListenerCallback {
        static final int TRANSACTION_onCoverTapLeft = 3;
        static final int TRANSACTION_onCoverTapMid = 4;
        static final int TRANSACTION_onCoverTapRight = 5;
        static final int TRANSACTION_onCoverTouchAccept = 1;
        static final int TRANSACTION_onCoverTouchReject = 2;
        static final int TRANSACTION_onSystemCoverEvent = 6;

        @Override // android.os.IInterface
        public IBinder asBinder() {
            return this;
        }

        @Override // android.os.Binder
        public int getMaxTransactionId() {
            return 5;
        }

        public Stub() {
            attachInterface(this, INfcLedCoverTouchListenerCallback.DESCRIPTOR);
        }

        public static INfcLedCoverTouchListenerCallback asInterface(IBinder iBinder) {
            if (iBinder == null) {
                return null;
            }
            IInterface queryLocalInterface = iBinder.queryLocalInterface(INfcLedCoverTouchListenerCallback.DESCRIPTOR);
            if (queryLocalInterface != null && (queryLocalInterface instanceof INfcLedCoverTouchListenerCallback)) {
                return (INfcLedCoverTouchListenerCallback) queryLocalInterface;
            }
            return new Proxy(iBinder);
        }

        public static String getDefaultTransactionName(int i) {
            switch (i) {
                case 1:
                    return "onCoverTouchAccept";
                case 2:
                    return "onCoverTouchReject";
                case 3:
                    return "onCoverTapLeft";
                case 4:
                    return "onCoverTapMid";
                case 5:
                    return "onCoverTapRight";
                case 6:
                    return "onSystemCoverEvent";
                default:
                    return null;
            }
        }

        @Override // android.os.Binder
        public String getTransactionName(int i) {
            return getDefaultTransactionName(i);
        }

        @Override // android.os.Binder
        public boolean onTransact(int i, Parcel parcel, Parcel parcel2, int i2) throws RemoteException {
            if (i >= 1 && i <= 16777215) {
                parcel.enforceInterface(INfcLedCoverTouchListenerCallback.DESCRIPTOR);
            }
            if (i == 1598968902) {
                parcel2.writeString(INfcLedCoverTouchListenerCallback.DESCRIPTOR);
                return true;
            }
            switch (i) {
                case 1:
                    onCoverTouchAccept();
                    parcel2.writeNoException();
                    return true;
                case 2:
                    onCoverTouchReject();
                    parcel2.writeNoException();
                    return true;
                case 3:
                    onCoverTapLeft();
                    parcel2.writeNoException();
                    return true;
                case 4:
                    onCoverTapMid();
                    parcel2.writeNoException();
                    return true;
                case 5:
                    onCoverTapRight();
                    parcel2.writeNoException();
                    return true;
                case 6:
                    int readInt = parcel.readInt();
                    Bundle bundle = (Bundle) parcel.readTypedObject(Bundle.CREATOR);
                    parcel.enforceNoDataAvail();
                    onSystemCoverEvent(readInt, bundle);
                    parcel2.writeNoException();
                    return true;
                default:
                    return super.onTransact(i, parcel, parcel2, i2);
            }
        }

        private static class Proxy implements INfcLedCoverTouchListenerCallback {
            private IBinder mRemote;

            Proxy(IBinder iBinder) {
                this.mRemote = iBinder;
            }

            @Override // android.os.IInterface
            public IBinder asBinder() {
                return this.mRemote;
            }

            public String getInterfaceDescriptor() {
                return INfcLedCoverTouchListenerCallback.DESCRIPTOR;
            }

            @Override // com.samsung.android.cover.INfcLedCoverTouchListenerCallback
            public void onCoverTouchAccept() throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(INfcLedCoverTouchListenerCallback.DESCRIPTOR);
                    this.mRemote.transact(1, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.samsung.android.cover.INfcLedCoverTouchListenerCallback
            public void onCoverTouchReject() throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(INfcLedCoverTouchListenerCallback.DESCRIPTOR);
                    this.mRemote.transact(2, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.samsung.android.cover.INfcLedCoverTouchListenerCallback
            public void onCoverTapLeft() throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(INfcLedCoverTouchListenerCallback.DESCRIPTOR);
                    this.mRemote.transact(3, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.samsung.android.cover.INfcLedCoverTouchListenerCallback
            public void onCoverTapMid() throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(INfcLedCoverTouchListenerCallback.DESCRIPTOR);
                    this.mRemote.transact(4, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.samsung.android.cover.INfcLedCoverTouchListenerCallback
            public void onCoverTapRight() throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(INfcLedCoverTouchListenerCallback.DESCRIPTOR);
                    this.mRemote.transact(5, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.samsung.android.cover.INfcLedCoverTouchListenerCallback
            public void onSystemCoverEvent(int i, Bundle bundle) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(INfcLedCoverTouchListenerCallback.DESCRIPTOR);
                    obtain.writeInt(i);
                    obtain.writeTypedObject(bundle, 0);
                    this.mRemote.transact(6, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }
        }
    }
}
