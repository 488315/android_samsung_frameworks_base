package com.samsung.android.ims;

import android.os.Binder;
import android.os.IBinder;
import android.os.IInterface;
import android.os.Parcel;
import android.os.RemoteException;

/* loaded from: classes6.dex */
public interface ISemEpdgListener extends IInterface {
    public static final String DESCRIPTOR = "com.samsung.android.ims.ISemEpdgListener";

    public static class Default implements ISemEpdgListener {
        @Override // android.os.IInterface
        public IBinder asBinder() {
            return null;
        }

        @Override // com.samsung.android.ims.ISemEpdgListener
        public void onEpdgAvailable(int i, boolean z, int i2) throws RemoteException {
        }

        @Override // com.samsung.android.ims.ISemEpdgListener
        public void onEpdgShowPopup(int i, int i2) throws RemoteException {
        }

        @Override // com.samsung.android.ims.ISemEpdgListener
        public void onHandoverResult(int i, int i2, int i3, String str) throws RemoteException {
        }

        @Override // com.samsung.android.ims.ISemEpdgListener
        public void onIpsecConnection(int i, String str, int i2, int i3) throws RemoteException {
        }

        @Override // com.samsung.android.ims.ISemEpdgListener
        public void onIpsecDisconnection(int i, String str) throws RemoteException {
        }
    }

    void onEpdgAvailable(int i, boolean z, int i2) throws RemoteException;

    void onEpdgShowPopup(int i, int i2) throws RemoteException;

    void onHandoverResult(int i, int i2, int i3, String str) throws RemoteException;

    void onIpsecConnection(int i, String str, int i2, int i3) throws RemoteException;

    void onIpsecDisconnection(int i, String str) throws RemoteException;

    public static abstract class Stub extends Binder implements ISemEpdgListener {
        static final int TRANSACTION_onEpdgAvailable = 1;
        static final int TRANSACTION_onEpdgShowPopup = 5;
        static final int TRANSACTION_onHandoverResult = 2;
        static final int TRANSACTION_onIpsecConnection = 3;
        static final int TRANSACTION_onIpsecDisconnection = 4;

        @Override // android.os.IInterface
        public IBinder asBinder() {
            return this;
        }

        @Override // android.os.Binder
        public int getMaxTransactionId() {
            return 4;
        }

        public Stub() {
            attachInterface(this, ISemEpdgListener.DESCRIPTOR);
        }

        public static ISemEpdgListener asInterface(IBinder iBinder) {
            if (iBinder == null) {
                return null;
            }
            IInterface iInterfaceQueryLocalInterface = iBinder.queryLocalInterface(ISemEpdgListener.DESCRIPTOR);
            if (iInterfaceQueryLocalInterface != null && (iInterfaceQueryLocalInterface instanceof ISemEpdgListener)) {
                return (ISemEpdgListener) iInterfaceQueryLocalInterface;
            }
            return new Proxy(iBinder);
        }

        public static String getDefaultTransactionName(int i) {
            if (i == 1) {
                return "onEpdgAvailable";
            }
            if (i == 2) {
                return "onHandoverResult";
            }
            if (i == 3) {
                return "onIpsecConnection";
            }
            if (i == 4) {
                return "onIpsecDisconnection";
            }
            if (i != 5) {
                return null;
            }
            return "onEpdgShowPopup";
        }

        @Override // android.os.Binder
        public String getTransactionName(int i) {
            return getDefaultTransactionName(i);
        }

        @Override // android.os.Binder
        public boolean onTransact(int i, Parcel parcel, Parcel parcel2, int i2) throws RemoteException {
            if (i >= 1 && i <= 16777215) {
                parcel.enforceInterface(ISemEpdgListener.DESCRIPTOR);
            }
            if (i == 1598968902) {
                parcel2.writeString(ISemEpdgListener.DESCRIPTOR);
                return true;
            }
            if (i == 1) {
                int i3 = parcel.readInt();
                boolean z = parcel.readBoolean();
                int i4 = parcel.readInt();
                parcel.enforceNoDataAvail();
                onEpdgAvailable(i3, z, i4);
            } else if (i == 2) {
                int i5 = parcel.readInt();
                int i6 = parcel.readInt();
                int i7 = parcel.readInt();
                String string = parcel.readString();
                parcel.enforceNoDataAvail();
                onHandoverResult(i5, i6, i7, string);
            } else if (i == 3) {
                int i8 = parcel.readInt();
                String string2 = parcel.readString();
                int i9 = parcel.readInt();
                int i10 = parcel.readInt();
                parcel.enforceNoDataAvail();
                onIpsecConnection(i8, string2, i9, i10);
            } else if (i == 4) {
                int i11 = parcel.readInt();
                String string3 = parcel.readString();
                parcel.enforceNoDataAvail();
                onIpsecDisconnection(i11, string3);
            } else if (i == 5) {
                int i12 = parcel.readInt();
                int i13 = parcel.readInt();
                parcel.enforceNoDataAvail();
                onEpdgShowPopup(i12, i13);
            } else {
                return super.onTransact(i, parcel, parcel2, i2);
            }
            return true;
        }

        private static class Proxy implements ISemEpdgListener {
            private IBinder mRemote;

            Proxy(IBinder iBinder) {
                this.mRemote = iBinder;
            }

            @Override // android.os.IInterface
            public IBinder asBinder() {
                return this.mRemote;
            }

            public String getInterfaceDescriptor() {
                return ISemEpdgListener.DESCRIPTOR;
            }

            @Override // com.samsung.android.ims.ISemEpdgListener
            public void onEpdgAvailable(int i, boolean z, int i2) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                try {
                    parcelObtain.writeInterfaceToken(ISemEpdgListener.DESCRIPTOR);
                    parcelObtain.writeInt(i);
                    parcelObtain.writeBoolean(z);
                    parcelObtain.writeInt(i2);
                    this.mRemote.transact(1, parcelObtain, null, 1);
                } finally {
                    parcelObtain.recycle();
                }
            }

            @Override // com.samsung.android.ims.ISemEpdgListener
            public void onHandoverResult(int i, int i2, int i3, String str) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                try {
                    parcelObtain.writeInterfaceToken(ISemEpdgListener.DESCRIPTOR);
                    parcelObtain.writeInt(i);
                    parcelObtain.writeInt(i2);
                    parcelObtain.writeInt(i3);
                    parcelObtain.writeString(str);
                    this.mRemote.transact(2, parcelObtain, null, 1);
                } finally {
                    parcelObtain.recycle();
                }
            }

            @Override // com.samsung.android.ims.ISemEpdgListener
            public void onIpsecConnection(int i, String str, int i2, int i3) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                try {
                    parcelObtain.writeInterfaceToken(ISemEpdgListener.DESCRIPTOR);
                    parcelObtain.writeInt(i);
                    parcelObtain.writeString(str);
                    parcelObtain.writeInt(i2);
                    parcelObtain.writeInt(i3);
                    this.mRemote.transact(3, parcelObtain, null, 1);
                } finally {
                    parcelObtain.recycle();
                }
            }

            @Override // com.samsung.android.ims.ISemEpdgListener
            public void onIpsecDisconnection(int i, String str) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                try {
                    parcelObtain.writeInterfaceToken(ISemEpdgListener.DESCRIPTOR);
                    parcelObtain.writeInt(i);
                    parcelObtain.writeString(str);
                    this.mRemote.transact(4, parcelObtain, null, 1);
                } finally {
                    parcelObtain.recycle();
                }
            }

            @Override // com.samsung.android.ims.ISemEpdgListener
            public void onEpdgShowPopup(int i, int i2) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                try {
                    parcelObtain.writeInterfaceToken(ISemEpdgListener.DESCRIPTOR);
                    parcelObtain.writeInt(i);
                    parcelObtain.writeInt(i2);
                    this.mRemote.transact(5, parcelObtain, null, 1);
                } finally {
                    parcelObtain.recycle();
                }
            }
        }
    }
}
