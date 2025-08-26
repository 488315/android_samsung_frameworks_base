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
            IInterface iInterfaceQueryLocalInterface = iBinder.queryLocalInterface(INfcLedCoverTouchListenerCallback.DESCRIPTOR);
            if (iInterfaceQueryLocalInterface != null && (iInterfaceQueryLocalInterface instanceof INfcLedCoverTouchListenerCallback)) {
                return (INfcLedCoverTouchListenerCallback) iInterfaceQueryLocalInterface;
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
                    int i3 = parcel.readInt();
                    Bundle bundle = (Bundle) parcel.readTypedObject(Bundle.CREATOR);
                    parcel.enforceNoDataAvail();
                    onSystemCoverEvent(i3, bundle);
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
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(INfcLedCoverTouchListenerCallback.DESCRIPTOR);
                    this.mRemote.transact(1, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // com.samsung.android.cover.INfcLedCoverTouchListenerCallback
            public void onCoverTouchReject() throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(INfcLedCoverTouchListenerCallback.DESCRIPTOR);
                    this.mRemote.transact(2, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // com.samsung.android.cover.INfcLedCoverTouchListenerCallback
            public void onCoverTapLeft() throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(INfcLedCoverTouchListenerCallback.DESCRIPTOR);
                    this.mRemote.transact(3, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // com.samsung.android.cover.INfcLedCoverTouchListenerCallback
            public void onCoverTapMid() throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(INfcLedCoverTouchListenerCallback.DESCRIPTOR);
                    this.mRemote.transact(4, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // com.samsung.android.cover.INfcLedCoverTouchListenerCallback
            public void onCoverTapRight() throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(INfcLedCoverTouchListenerCallback.DESCRIPTOR);
                    this.mRemote.transact(5, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // com.samsung.android.cover.INfcLedCoverTouchListenerCallback
            public void onSystemCoverEvent(int i, Bundle bundle) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(INfcLedCoverTouchListenerCallback.DESCRIPTOR);
                    parcelObtain.writeInt(i);
                    parcelObtain.writeTypedObject(bundle, 0);
                    this.mRemote.transact(6, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }
        }
    }
}
