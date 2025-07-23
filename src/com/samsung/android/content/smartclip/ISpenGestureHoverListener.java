package com.samsung.android.content.smartclip;

import android.os.Binder;
import android.os.IBinder;
import android.os.IInterface;
import android.os.Parcel;
import android.os.RemoteException;

/* loaded from: classes6.dex */
public interface ISpenGestureHoverListener extends IInterface {
    public static final String DESCRIPTOR = "com.samsung.android.content.smartclip.ISpenGestureHoverListener";

    public static class Default implements ISpenGestureHoverListener {
        @Override // android.os.IInterface
        public IBinder asBinder() {
            return null;
        }

        @Override // com.samsung.android.content.smartclip.ISpenGestureHoverListener
        public void onBackPressed() throws RemoteException {
        }

        @Override // com.samsung.android.content.smartclip.ISpenGestureHoverListener
        public void onHoverEnter() throws RemoteException {
        }

        @Override // com.samsung.android.content.smartclip.ISpenGestureHoverListener
        public void onHoverExit() throws RemoteException {
        }

        @Override // com.samsung.android.content.smartclip.ISpenGestureHoverListener
        public void onHoverExitTowardBack() throws RemoteException {
        }

        @Override // com.samsung.android.content.smartclip.ISpenGestureHoverListener
        public void onHoverStay(int i, int i2) throws RemoteException {
        }
    }

    void onBackPressed() throws RemoteException;

    void onHoverEnter() throws RemoteException;

    void onHoverExit() throws RemoteException;

    void onHoverExitTowardBack() throws RemoteException;

    void onHoverStay(int i, int i2) throws RemoteException;

    public static abstract class Stub extends Binder implements ISpenGestureHoverListener {
        static final int TRANSACTION_onBackPressed = 4;
        static final int TRANSACTION_onHoverEnter = 1;
        static final int TRANSACTION_onHoverExit = 2;
        static final int TRANSACTION_onHoverExitTowardBack = 3;
        static final int TRANSACTION_onHoverStay = 5;

        @Override // android.os.IInterface
        public IBinder asBinder() {
            return this;
        }

        @Override // android.os.Binder
        public int getMaxTransactionId() {
            return 4;
        }

        public Stub() {
            attachInterface(this, ISpenGestureHoverListener.DESCRIPTOR);
        }

        public static ISpenGestureHoverListener asInterface(IBinder iBinder) {
            if (iBinder == null) {
                return null;
            }
            IInterface queryLocalInterface = iBinder.queryLocalInterface(ISpenGestureHoverListener.DESCRIPTOR);
            if (queryLocalInterface != null && (queryLocalInterface instanceof ISpenGestureHoverListener)) {
                return (ISpenGestureHoverListener) queryLocalInterface;
            }
            return new Proxy(iBinder);
        }

        public static String getDefaultTransactionName(int i) {
            if (i == 1) {
                return "onHoverEnter";
            }
            if (i == 2) {
                return "onHoverExit";
            }
            if (i == 3) {
                return "onHoverExitTowardBack";
            }
            if (i == 4) {
                return "onBackPressed";
            }
            if (i != 5) {
                return null;
            }
            return "onHoverStay";
        }

        @Override // android.os.Binder
        public String getTransactionName(int i) {
            return getDefaultTransactionName(i);
        }

        @Override // android.os.Binder
        public boolean onTransact(int i, Parcel parcel, Parcel parcel2, int i2) throws RemoteException {
            if (i >= 1 && i <= 16777215) {
                parcel.enforceInterface(ISpenGestureHoverListener.DESCRIPTOR);
            }
            if (i == 1598968902) {
                parcel2.writeString(ISpenGestureHoverListener.DESCRIPTOR);
                return true;
            }
            if (i == 1) {
                onHoverEnter();
            } else if (i == 2) {
                onHoverExit();
            } else if (i == 3) {
                onHoverExitTowardBack();
            } else if (i == 4) {
                onBackPressed();
            } else if (i == 5) {
                int readInt = parcel.readInt();
                int readInt2 = parcel.readInt();
                parcel.enforceNoDataAvail();
                onHoverStay(readInt, readInt2);
            } else {
                return super.onTransact(i, parcel, parcel2, i2);
            }
            return true;
        }

        private static class Proxy implements ISpenGestureHoverListener {
            private IBinder mRemote;

            Proxy(IBinder iBinder) {
                this.mRemote = iBinder;
            }

            @Override // android.os.IInterface
            public IBinder asBinder() {
                return this.mRemote;
            }

            public String getInterfaceDescriptor() {
                return ISpenGestureHoverListener.DESCRIPTOR;
            }

            @Override // com.samsung.android.content.smartclip.ISpenGestureHoverListener
            public void onHoverEnter() throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                try {
                    obtain.writeInterfaceToken(ISpenGestureHoverListener.DESCRIPTOR);
                    this.mRemote.transact(1, obtain, null, 1);
                } finally {
                    obtain.recycle();
                }
            }

            @Override // com.samsung.android.content.smartclip.ISpenGestureHoverListener
            public void onHoverExit() throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                try {
                    obtain.writeInterfaceToken(ISpenGestureHoverListener.DESCRIPTOR);
                    this.mRemote.transact(2, obtain, null, 1);
                } finally {
                    obtain.recycle();
                }
            }

            @Override // com.samsung.android.content.smartclip.ISpenGestureHoverListener
            public void onHoverExitTowardBack() throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                try {
                    obtain.writeInterfaceToken(ISpenGestureHoverListener.DESCRIPTOR);
                    this.mRemote.transact(3, obtain, null, 1);
                } finally {
                    obtain.recycle();
                }
            }

            @Override // com.samsung.android.content.smartclip.ISpenGestureHoverListener
            public void onBackPressed() throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                try {
                    obtain.writeInterfaceToken(ISpenGestureHoverListener.DESCRIPTOR);
                    this.mRemote.transact(4, obtain, null, 1);
                } finally {
                    obtain.recycle();
                }
            }

            @Override // com.samsung.android.content.smartclip.ISpenGestureHoverListener
            public void onHoverStay(int i, int i2) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                try {
                    obtain.writeInterfaceToken(ISpenGestureHoverListener.DESCRIPTOR);
                    obtain.writeInt(i);
                    obtain.writeInt(i2);
                    this.mRemote.transact(5, obtain, null, 1);
                } finally {
                    obtain.recycle();
                }
            }
        }
    }
}
