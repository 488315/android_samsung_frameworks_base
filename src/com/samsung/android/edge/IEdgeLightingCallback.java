package com.samsung.android.edge;

import android.os.Binder;
import android.os.IBinder;
import android.os.IInterface;
import android.os.Parcel;
import android.os.RemoteException;

/* loaded from: classes6.dex */
public interface IEdgeLightingCallback extends IInterface {
    public static final String DESCRIPTOR = "com.samsung.android.edge.IEdgeLightingCallback";

    public static class Default implements IEdgeLightingCallback {
        @Override // android.os.IInterface
        public IBinder asBinder() {
            return null;
        }

        @Override // com.samsung.android.edge.IEdgeLightingCallback
        public void onEdgeLightingStarted() throws RemoteException {
        }

        @Override // com.samsung.android.edge.IEdgeLightingCallback
        public void onEdgeLightingStopped() throws RemoteException {
        }

        @Override // com.samsung.android.edge.IEdgeLightingCallback
        public void onScreenChanged(boolean z) throws RemoteException {
        }

        @Override // com.samsung.android.edge.IEdgeLightingCallback
        public void onStartEdgeLighting(String str, SemEdgeLightingInfo semEdgeLightingInfo, int i) throws RemoteException {
        }

        @Override // com.samsung.android.edge.IEdgeLightingCallback
        public void onStopEdgeLighting(String str, int i) throws RemoteException {
        }
    }

    void onEdgeLightingStarted() throws RemoteException;

    void onEdgeLightingStopped() throws RemoteException;

    void onScreenChanged(boolean z) throws RemoteException;

    void onStartEdgeLighting(String str, SemEdgeLightingInfo semEdgeLightingInfo, int i) throws RemoteException;

    void onStopEdgeLighting(String str, int i) throws RemoteException;

    public static abstract class Stub extends Binder implements IEdgeLightingCallback {
        static final int TRANSACTION_onEdgeLightingStarted = 4;
        static final int TRANSACTION_onEdgeLightingStopped = 5;
        static final int TRANSACTION_onScreenChanged = 3;
        static final int TRANSACTION_onStartEdgeLighting = 1;
        static final int TRANSACTION_onStopEdgeLighting = 2;

        @Override // android.os.IInterface
        public IBinder asBinder() {
            return this;
        }

        @Override // android.os.Binder
        public int getMaxTransactionId() {
            return 4;
        }

        public Stub() {
            attachInterface(this, IEdgeLightingCallback.DESCRIPTOR);
        }

        public static IEdgeLightingCallback asInterface(IBinder iBinder) {
            if (iBinder == null) {
                return null;
            }
            IInterface iInterfaceQueryLocalInterface = iBinder.queryLocalInterface(IEdgeLightingCallback.DESCRIPTOR);
            if (iInterfaceQueryLocalInterface != null && (iInterfaceQueryLocalInterface instanceof IEdgeLightingCallback)) {
                return (IEdgeLightingCallback) iInterfaceQueryLocalInterface;
            }
            return new Proxy(iBinder);
        }

        public static String getDefaultTransactionName(int i) {
            if (i == 1) {
                return "onStartEdgeLighting";
            }
            if (i == 2) {
                return "onStopEdgeLighting";
            }
            if (i == 3) {
                return "onScreenChanged";
            }
            if (i == 4) {
                return "onEdgeLightingStarted";
            }
            if (i != 5) {
                return null;
            }
            return "onEdgeLightingStopped";
        }

        @Override // android.os.Binder
        public String getTransactionName(int i) {
            return getDefaultTransactionName(i);
        }

        @Override // android.os.Binder
        public boolean onTransact(int i, Parcel parcel, Parcel parcel2, int i2) throws RemoteException {
            if (i >= 1 && i <= 16777215) {
                parcel.enforceInterface(IEdgeLightingCallback.DESCRIPTOR);
            }
            if (i == 1598968902) {
                parcel2.writeString(IEdgeLightingCallback.DESCRIPTOR);
                return true;
            }
            if (i == 1) {
                String string = parcel.readString();
                SemEdgeLightingInfo semEdgeLightingInfo = (SemEdgeLightingInfo) parcel.readTypedObject(SemEdgeLightingInfo.CREATOR);
                int i3 = parcel.readInt();
                parcel.enforceNoDataAvail();
                onStartEdgeLighting(string, semEdgeLightingInfo, i3);
            } else if (i == 2) {
                String string2 = parcel.readString();
                int i4 = parcel.readInt();
                parcel.enforceNoDataAvail();
                onStopEdgeLighting(string2, i4);
            } else if (i == 3) {
                boolean z = parcel.readBoolean();
                parcel.enforceNoDataAvail();
                onScreenChanged(z);
            } else if (i == 4) {
                onEdgeLightingStarted();
            } else if (i == 5) {
                onEdgeLightingStopped();
            } else {
                return super.onTransact(i, parcel, parcel2, i2);
            }
            return true;
        }

        private static class Proxy implements IEdgeLightingCallback {
            private IBinder mRemote;

            Proxy(IBinder iBinder) {
                this.mRemote = iBinder;
            }

            @Override // android.os.IInterface
            public IBinder asBinder() {
                return this.mRemote;
            }

            public String getInterfaceDescriptor() {
                return IEdgeLightingCallback.DESCRIPTOR;
            }

            @Override // com.samsung.android.edge.IEdgeLightingCallback
            public void onStartEdgeLighting(String str, SemEdgeLightingInfo semEdgeLightingInfo, int i) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                try {
                    parcelObtain.writeInterfaceToken(IEdgeLightingCallback.DESCRIPTOR);
                    parcelObtain.writeString(str);
                    parcelObtain.writeTypedObject(semEdgeLightingInfo, 0);
                    parcelObtain.writeInt(i);
                    this.mRemote.transact(1, parcelObtain, null, 1);
                } finally {
                    parcelObtain.recycle();
                }
            }

            @Override // com.samsung.android.edge.IEdgeLightingCallback
            public void onStopEdgeLighting(String str, int i) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                try {
                    parcelObtain.writeInterfaceToken(IEdgeLightingCallback.DESCRIPTOR);
                    parcelObtain.writeString(str);
                    parcelObtain.writeInt(i);
                    this.mRemote.transact(2, parcelObtain, null, 1);
                } finally {
                    parcelObtain.recycle();
                }
            }

            @Override // com.samsung.android.edge.IEdgeLightingCallback
            public void onScreenChanged(boolean z) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                try {
                    parcelObtain.writeInterfaceToken(IEdgeLightingCallback.DESCRIPTOR);
                    parcelObtain.writeBoolean(z);
                    this.mRemote.transact(3, parcelObtain, null, 1);
                } finally {
                    parcelObtain.recycle();
                }
            }

            @Override // com.samsung.android.edge.IEdgeLightingCallback
            public void onEdgeLightingStarted() throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                try {
                    parcelObtain.writeInterfaceToken(IEdgeLightingCallback.DESCRIPTOR);
                    this.mRemote.transact(4, parcelObtain, null, 1);
                } finally {
                    parcelObtain.recycle();
                }
            }

            @Override // com.samsung.android.edge.IEdgeLightingCallback
            public void onEdgeLightingStopped() throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                try {
                    parcelObtain.writeInterfaceToken(IEdgeLightingCallback.DESCRIPTOR);
                    this.mRemote.transact(5, parcelObtain, null, 1);
                } finally {
                    parcelObtain.recycle();
                }
            }
        }
    }
}
