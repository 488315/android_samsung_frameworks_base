package com.samsung.android.cover;

import android.os.Binder;
import android.os.IBinder;
import android.os.IInterface;
import android.os.Parcel;
import android.os.RemoteException;

/* loaded from: classes6.dex */
public interface ICoverService extends IInterface {
    public static final String DESCRIPTOR = "com.samsung.android.cover.ICoverService";

    public static class Default implements ICoverService {
        @Override // android.os.IInterface
        public IBinder asBinder() {
            return null;
        }

        @Override // com.samsung.android.cover.ICoverService
        public int onCoverAppCovered(boolean z) throws RemoteException {
            return 0;
        }

        @Override // com.samsung.android.cover.ICoverService
        public void onSystemReady() throws RemoteException {
        }

        @Override // com.samsung.android.cover.ICoverService
        public void onUpdateCoverState(CoverState coverState) throws RemoteException {
        }
    }

    int onCoverAppCovered(boolean z) throws RemoteException;

    void onSystemReady() throws RemoteException;

    void onUpdateCoverState(CoverState coverState) throws RemoteException;

    public static abstract class Stub extends Binder implements ICoverService {
        static final int TRANSACTION_onCoverAppCovered = 3;
        static final int TRANSACTION_onSystemReady = 1;
        static final int TRANSACTION_onUpdateCoverState = 2;

        @Override // android.os.IInterface
        public IBinder asBinder() {
            return this;
        }

        @Override // android.os.Binder
        public int getMaxTransactionId() {
            return 2;
        }

        public Stub() {
            attachInterface(this, ICoverService.DESCRIPTOR);
        }

        public static ICoverService asInterface(IBinder iBinder) {
            if (iBinder == null) {
                return null;
            }
            IInterface queryLocalInterface = iBinder.queryLocalInterface(ICoverService.DESCRIPTOR);
            if (queryLocalInterface != null && (queryLocalInterface instanceof ICoverService)) {
                return (ICoverService) queryLocalInterface;
            }
            return new Proxy(iBinder);
        }

        public static String getDefaultTransactionName(int i) {
            if (i == 1) {
                return "onSystemReady";
            }
            if (i == 2) {
                return "onUpdateCoverState";
            }
            if (i != 3) {
                return null;
            }
            return "onCoverAppCovered";
        }

        @Override // android.os.Binder
        public String getTransactionName(int i) {
            return getDefaultTransactionName(i);
        }

        @Override // android.os.Binder
        public boolean onTransact(int i, Parcel parcel, Parcel parcel2, int i2) throws RemoteException {
            if (i >= 1 && i <= 16777215) {
                parcel.enforceInterface(ICoverService.DESCRIPTOR);
            }
            if (i == 1598968902) {
                parcel2.writeString(ICoverService.DESCRIPTOR);
                return true;
            }
            if (i == 1) {
                onSystemReady();
            } else if (i == 2) {
                CoverState coverState = (CoverState) parcel.readTypedObject(CoverState.CREATOR);
                parcel.enforceNoDataAvail();
                onUpdateCoverState(coverState);
            } else if (i == 3) {
                boolean readBoolean = parcel.readBoolean();
                parcel.enforceNoDataAvail();
                int onCoverAppCovered = onCoverAppCovered(readBoolean);
                parcel2.writeNoException();
                parcel2.writeInt(onCoverAppCovered);
            } else {
                return super.onTransact(i, parcel, parcel2, i2);
            }
            return true;
        }

        private static class Proxy implements ICoverService {
            private IBinder mRemote;

            Proxy(IBinder iBinder) {
                this.mRemote = iBinder;
            }

            @Override // android.os.IInterface
            public IBinder asBinder() {
                return this.mRemote;
            }

            public String getInterfaceDescriptor() {
                return ICoverService.DESCRIPTOR;
            }

            @Override // com.samsung.android.cover.ICoverService
            public void onSystemReady() throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                try {
                    obtain.writeInterfaceToken(ICoverService.DESCRIPTOR);
                    this.mRemote.transact(1, obtain, null, 1);
                } finally {
                    obtain.recycle();
                }
            }

            @Override // com.samsung.android.cover.ICoverService
            public void onUpdateCoverState(CoverState coverState) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                try {
                    obtain.writeInterfaceToken(ICoverService.DESCRIPTOR);
                    obtain.writeTypedObject(coverState, 0);
                    this.mRemote.transact(2, obtain, null, 1);
                } finally {
                    obtain.recycle();
                }
            }

            @Override // com.samsung.android.cover.ICoverService
            public int onCoverAppCovered(boolean z) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(ICoverService.DESCRIPTOR);
                    obtain.writeBoolean(z);
                    this.mRemote.transact(3, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readInt();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }
        }
    }
}
