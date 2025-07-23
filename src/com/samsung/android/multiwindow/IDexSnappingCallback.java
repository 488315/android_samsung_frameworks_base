package com.samsung.android.multiwindow;

import android.graphics.Rect;
import android.os.Binder;
import android.os.IBinder;
import android.os.IInterface;
import android.os.Parcel;
import android.os.RemoteException;

/* loaded from: classes6.dex */
public interface IDexSnappingCallback extends IInterface {
    public static final String DESCRIPTOR = "com.samsung.android.multiwindow.IDexSnappingCallback";

    public static class Default implements IDexSnappingCallback {
        @Override // android.os.IInterface
        public IBinder asBinder() {
            return null;
        }

        @Override // com.samsung.android.multiwindow.IDexSnappingCallback
        public void onWindowSnappingChanged(int i, Rect rect) throws RemoteException {
        }
    }

    void onWindowSnappingChanged(int i, Rect rect) throws RemoteException;

    public static abstract class Stub extends Binder implements IDexSnappingCallback {
        static final int TRANSACTION_onWindowSnappingChanged = 1;

        @Override // android.os.IInterface
        public IBinder asBinder() {
            return this;
        }

        @Override // android.os.Binder
        public int getMaxTransactionId() {
            return 0;
        }

        public Stub() {
            attachInterface(this, IDexSnappingCallback.DESCRIPTOR);
        }

        public static IDexSnappingCallback asInterface(IBinder iBinder) {
            if (iBinder == null) {
                return null;
            }
            IInterface queryLocalInterface = iBinder.queryLocalInterface(IDexSnappingCallback.DESCRIPTOR);
            if (queryLocalInterface != null && (queryLocalInterface instanceof IDexSnappingCallback)) {
                return (IDexSnappingCallback) queryLocalInterface;
            }
            return new Proxy(iBinder);
        }

        public static String getDefaultTransactionName(int i) {
            if (i != 1) {
                return null;
            }
            return "onWindowSnappingChanged";
        }

        @Override // android.os.Binder
        public String getTransactionName(int i) {
            return getDefaultTransactionName(i);
        }

        @Override // android.os.Binder
        public boolean onTransact(int i, Parcel parcel, Parcel parcel2, int i2) throws RemoteException {
            if (i >= 1 && i <= 16777215) {
                parcel.enforceInterface(IDexSnappingCallback.DESCRIPTOR);
            }
            if (i == 1598968902) {
                parcel2.writeString(IDexSnappingCallback.DESCRIPTOR);
                return true;
            }
            if (i == 1) {
                int readInt = parcel.readInt();
                Rect rect = (Rect) parcel.readTypedObject(Rect.CREATOR);
                parcel.enforceNoDataAvail();
                onWindowSnappingChanged(readInt, rect);
                return true;
            }
            return super.onTransact(i, parcel, parcel2, i2);
        }

        private static class Proxy implements IDexSnappingCallback {
            private IBinder mRemote;

            Proxy(IBinder iBinder) {
                this.mRemote = iBinder;
            }

            @Override // android.os.IInterface
            public IBinder asBinder() {
                return this.mRemote;
            }

            public String getInterfaceDescriptor() {
                return IDexSnappingCallback.DESCRIPTOR;
            }

            @Override // com.samsung.android.multiwindow.IDexSnappingCallback
            public void onWindowSnappingChanged(int i, Rect rect) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                try {
                    obtain.writeInterfaceToken(IDexSnappingCallback.DESCRIPTOR);
                    obtain.writeInt(i);
                    obtain.writeTypedObject(rect, 0);
                    this.mRemote.transact(1, obtain, null, 1);
                } finally {
                    obtain.recycle();
                }
            }
        }
    }
}
