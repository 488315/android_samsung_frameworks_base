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
            IInterface queryLocalInterface = iBinder.queryLocalInterface(ISemEpdgListener.DESCRIPTOR);
            if (queryLocalInterface != null && (queryLocalInterface instanceof ISemEpdgListener)) {
                return (ISemEpdgListener) queryLocalInterface;
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
                int readInt = parcel.readInt();
                boolean readBoolean = parcel.readBoolean();
                int readInt2 = parcel.readInt();
                parcel.enforceNoDataAvail();
                onEpdgAvailable(readInt, readBoolean, readInt2);
            } else if (i == 2) {
                int readInt3 = parcel.readInt();
                int readInt4 = parcel.readInt();
                int readInt5 = parcel.readInt();
                String readString = parcel.readString();
                parcel.enforceNoDataAvail();
                onHandoverResult(readInt3, readInt4, readInt5, readString);
            } else if (i == 3) {
                int readInt6 = parcel.readInt();
                String readString2 = parcel.readString();
                int readInt7 = parcel.readInt();
                int readInt8 = parcel.readInt();
                parcel.enforceNoDataAvail();
                onIpsecConnection(readInt6, readString2, readInt7, readInt8);
            } else if (i == 4) {
                int readInt9 = parcel.readInt();
                String readString3 = parcel.readString();
                parcel.enforceNoDataAvail();
                onIpsecDisconnection(readInt9, readString3);
            } else if (i == 5) {
                int readInt10 = parcel.readInt();
                int readInt11 = parcel.readInt();
                parcel.enforceNoDataAvail();
                onEpdgShowPopup(readInt10, readInt11);
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
                Parcel obtain = Parcel.obtain(asBinder());
                try {
                    obtain.writeInterfaceToken(ISemEpdgListener.DESCRIPTOR);
                    obtain.writeInt(i);
                    obtain.writeBoolean(z);
                    obtain.writeInt(i2);
                    this.mRemote.transact(1, obtain, null, 1);
                } finally {
                    obtain.recycle();
                }
            }

            @Override // com.samsung.android.ims.ISemEpdgListener
            public void onHandoverResult(int i, int i2, int i3, String str) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                try {
                    obtain.writeInterfaceToken(ISemEpdgListener.DESCRIPTOR);
                    obtain.writeInt(i);
                    obtain.writeInt(i2);
                    obtain.writeInt(i3);
                    obtain.writeString(str);
                    this.mRemote.transact(2, obtain, null, 1);
                } finally {
                    obtain.recycle();
                }
            }

            @Override // com.samsung.android.ims.ISemEpdgListener
            public void onIpsecConnection(int i, String str, int i2, int i3) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                try {
                    obtain.writeInterfaceToken(ISemEpdgListener.DESCRIPTOR);
                    obtain.writeInt(i);
                    obtain.writeString(str);
                    obtain.writeInt(i2);
                    obtain.writeInt(i3);
                    this.mRemote.transact(3, obtain, null, 1);
                } finally {
                    obtain.recycle();
                }
            }

            @Override // com.samsung.android.ims.ISemEpdgListener
            public void onIpsecDisconnection(int i, String str) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                try {
                    obtain.writeInterfaceToken(ISemEpdgListener.DESCRIPTOR);
                    obtain.writeInt(i);
                    obtain.writeString(str);
                    this.mRemote.transact(4, obtain, null, 1);
                } finally {
                    obtain.recycle();
                }
            }

            @Override // com.samsung.android.ims.ISemEpdgListener
            public void onEpdgShowPopup(int i, int i2) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                try {
                    obtain.writeInterfaceToken(ISemEpdgListener.DESCRIPTOR);
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
