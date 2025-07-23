package com.samsung.android.cover;

import android.os.Binder;
import android.os.IBinder;
import android.os.IInterface;
import android.os.Parcel;
import android.os.RemoteException;

/* loaded from: classes6.dex */
public interface ISViewCoverBaseService extends IInterface {
    public static final String DESCRIPTOR = "com.samsung.android.cover.ISViewCoverBaseService";

    public static class Default implements ISViewCoverBaseService {
        @Override // android.os.IInterface
        public IBinder asBinder() {
            return null;
        }

        @Override // com.samsung.android.cover.ISViewCoverBaseService
        public boolean isCoverViewShowing() throws RemoteException {
            return false;
        }

        @Override // com.samsung.android.cover.ISViewCoverBaseService
        public int onCoverAppCovered(boolean z) throws RemoteException {
            return 0;
        }

        @Override // com.samsung.android.cover.ISViewCoverBaseService
        public void onSViewCoverHide() throws RemoteException {
        }

        @Override // com.samsung.android.cover.ISViewCoverBaseService
        public void onSViewCoverShow() throws RemoteException {
        }

        @Override // com.samsung.android.cover.ISViewCoverBaseService
        public void onSystemReady() throws RemoteException {
        }

        @Override // com.samsung.android.cover.ISViewCoverBaseService
        public void updateCoverState(CoverState coverState) throws RemoteException {
        }
    }

    boolean isCoverViewShowing() throws RemoteException;

    int onCoverAppCovered(boolean z) throws RemoteException;

    void onSViewCoverHide() throws RemoteException;

    void onSViewCoverShow() throws RemoteException;

    void onSystemReady() throws RemoteException;

    void updateCoverState(CoverState coverState) throws RemoteException;

    public static abstract class Stub extends Binder implements ISViewCoverBaseService {
        static final int TRANSACTION_isCoverViewShowing = 5;
        static final int TRANSACTION_onCoverAppCovered = 6;
        static final int TRANSACTION_onSViewCoverHide = 3;
        static final int TRANSACTION_onSViewCoverShow = 2;
        static final int TRANSACTION_onSystemReady = 1;
        static final int TRANSACTION_updateCoverState = 4;

        @Override // android.os.IInterface
        public IBinder asBinder() {
            return this;
        }

        @Override // android.os.Binder
        public int getMaxTransactionId() {
            return 5;
        }

        public Stub() {
            attachInterface(this, ISViewCoverBaseService.DESCRIPTOR);
        }

        public static ISViewCoverBaseService asInterface(IBinder iBinder) {
            if (iBinder == null) {
                return null;
            }
            IInterface queryLocalInterface = iBinder.queryLocalInterface(ISViewCoverBaseService.DESCRIPTOR);
            if (queryLocalInterface != null && (queryLocalInterface instanceof ISViewCoverBaseService)) {
                return (ISViewCoverBaseService) queryLocalInterface;
            }
            return new Proxy(iBinder);
        }

        public static String getDefaultTransactionName(int i) {
            switch (i) {
                case 1:
                    return "onSystemReady";
                case 2:
                    return "onSViewCoverShow";
                case 3:
                    return "onSViewCoverHide";
                case 4:
                    return "updateCoverState";
                case 5:
                    return "isCoverViewShowing";
                case 6:
                    return "onCoverAppCovered";
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
                parcel.enforceInterface(ISViewCoverBaseService.DESCRIPTOR);
            }
            if (i == 1598968902) {
                parcel2.writeString(ISViewCoverBaseService.DESCRIPTOR);
                return true;
            }
            switch (i) {
                case 1:
                    onSystemReady();
                    return true;
                case 2:
                    onSViewCoverShow();
                    return true;
                case 3:
                    onSViewCoverHide();
                    return true;
                case 4:
                    CoverState coverState = (CoverState) parcel.readTypedObject(CoverState.CREATOR);
                    parcel.enforceNoDataAvail();
                    updateCoverState(coverState);
                    return true;
                case 5:
                    boolean isCoverViewShowing = isCoverViewShowing();
                    parcel2.writeNoException();
                    parcel2.writeBoolean(isCoverViewShowing);
                    return true;
                case 6:
                    boolean readBoolean = parcel.readBoolean();
                    parcel.enforceNoDataAvail();
                    int onCoverAppCovered = onCoverAppCovered(readBoolean);
                    parcel2.writeNoException();
                    parcel2.writeInt(onCoverAppCovered);
                    return true;
                default:
                    return super.onTransact(i, parcel, parcel2, i2);
            }
        }

        private static class Proxy implements ISViewCoverBaseService {
            private IBinder mRemote;

            Proxy(IBinder iBinder) {
                this.mRemote = iBinder;
            }

            @Override // android.os.IInterface
            public IBinder asBinder() {
                return this.mRemote;
            }

            public String getInterfaceDescriptor() {
                return ISViewCoverBaseService.DESCRIPTOR;
            }

            @Override // com.samsung.android.cover.ISViewCoverBaseService
            public void onSystemReady() throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                try {
                    obtain.writeInterfaceToken(ISViewCoverBaseService.DESCRIPTOR);
                    this.mRemote.transact(1, obtain, null, 1);
                } finally {
                    obtain.recycle();
                }
            }

            @Override // com.samsung.android.cover.ISViewCoverBaseService
            public void onSViewCoverShow() throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                try {
                    obtain.writeInterfaceToken(ISViewCoverBaseService.DESCRIPTOR);
                    this.mRemote.transact(2, obtain, null, 1);
                } finally {
                    obtain.recycle();
                }
            }

            @Override // com.samsung.android.cover.ISViewCoverBaseService
            public void onSViewCoverHide() throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                try {
                    obtain.writeInterfaceToken(ISViewCoverBaseService.DESCRIPTOR);
                    this.mRemote.transact(3, obtain, null, 1);
                } finally {
                    obtain.recycle();
                }
            }

            @Override // com.samsung.android.cover.ISViewCoverBaseService
            public void updateCoverState(CoverState coverState) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                try {
                    obtain.writeInterfaceToken(ISViewCoverBaseService.DESCRIPTOR);
                    obtain.writeTypedObject(coverState, 0);
                    this.mRemote.transact(4, obtain, null, 1);
                } finally {
                    obtain.recycle();
                }
            }

            @Override // com.samsung.android.cover.ISViewCoverBaseService
            public boolean isCoverViewShowing() throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(ISViewCoverBaseService.DESCRIPTOR);
                    this.mRemote.transact(5, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readBoolean();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.samsung.android.cover.ISViewCoverBaseService
            public int onCoverAppCovered(boolean z) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(ISViewCoverBaseService.DESCRIPTOR);
                    obtain.writeBoolean(z);
                    this.mRemote.transact(6, obtain, obtain2, 0);
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
