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
            IInterface iInterfaceQueryLocalInterface = iBinder.queryLocalInterface(ISViewCoverBaseService.DESCRIPTOR);
            if (iInterfaceQueryLocalInterface != null && (iInterfaceQueryLocalInterface instanceof ISViewCoverBaseService)) {
                return (ISViewCoverBaseService) iInterfaceQueryLocalInterface;
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
                    boolean zIsCoverViewShowing = isCoverViewShowing();
                    parcel2.writeNoException();
                    parcel2.writeBoolean(zIsCoverViewShowing);
                    return true;
                case 6:
                    boolean z = parcel.readBoolean();
                    parcel.enforceNoDataAvail();
                    int iOnCoverAppCovered = onCoverAppCovered(z);
                    parcel2.writeNoException();
                    parcel2.writeInt(iOnCoverAppCovered);
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
                Parcel parcelObtain = Parcel.obtain(asBinder());
                try {
                    parcelObtain.writeInterfaceToken(ISViewCoverBaseService.DESCRIPTOR);
                    this.mRemote.transact(1, parcelObtain, null, 1);
                } finally {
                    parcelObtain.recycle();
                }
            }

            @Override // com.samsung.android.cover.ISViewCoverBaseService
            public void onSViewCoverShow() throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                try {
                    parcelObtain.writeInterfaceToken(ISViewCoverBaseService.DESCRIPTOR);
                    this.mRemote.transact(2, parcelObtain, null, 1);
                } finally {
                    parcelObtain.recycle();
                }
            }

            @Override // com.samsung.android.cover.ISViewCoverBaseService
            public void onSViewCoverHide() throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                try {
                    parcelObtain.writeInterfaceToken(ISViewCoverBaseService.DESCRIPTOR);
                    this.mRemote.transact(3, parcelObtain, null, 1);
                } finally {
                    parcelObtain.recycle();
                }
            }

            @Override // com.samsung.android.cover.ISViewCoverBaseService
            public void updateCoverState(CoverState coverState) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                try {
                    parcelObtain.writeInterfaceToken(ISViewCoverBaseService.DESCRIPTOR);
                    parcelObtain.writeTypedObject(coverState, 0);
                    this.mRemote.transact(4, parcelObtain, null, 1);
                } finally {
                    parcelObtain.recycle();
                }
            }

            @Override // com.samsung.android.cover.ISViewCoverBaseService
            public boolean isCoverViewShowing() throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(ISViewCoverBaseService.DESCRIPTOR);
                    this.mRemote.transact(5, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return parcelObtain2.readBoolean();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // com.samsung.android.cover.ISViewCoverBaseService
            public int onCoverAppCovered(boolean z) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(ISViewCoverBaseService.DESCRIPTOR);
                    parcelObtain.writeBoolean(z);
                    this.mRemote.transact(6, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return parcelObtain2.readInt();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }
        }
    }
}
