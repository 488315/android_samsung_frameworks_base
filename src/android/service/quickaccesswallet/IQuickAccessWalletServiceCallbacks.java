package android.service.quickaccesswallet;

import android.app.PendingIntent;
import android.os.Binder;
import android.os.IBinder;
import android.os.IInterface;
import android.os.Parcel;
import android.os.RemoteException;

/* loaded from: classes3.dex */
public interface IQuickAccessWalletServiceCallbacks extends IInterface {
    public static final String DESCRIPTOR = "android.service.quickaccesswallet.IQuickAccessWalletServiceCallbacks";

    public static class Default implements IQuickAccessWalletServiceCallbacks {
        @Override // android.os.IInterface
        public IBinder asBinder() {
            return null;
        }

        @Override // android.service.quickaccesswallet.IQuickAccessWalletServiceCallbacks
        public void onGestureTargetActivityPendingIntentReceived(PendingIntent pendingIntent) throws RemoteException {
        }

        @Override // android.service.quickaccesswallet.IQuickAccessWalletServiceCallbacks
        public void onGetWalletCardsFailure(GetWalletCardsError getWalletCardsError) throws RemoteException {
        }

        @Override // android.service.quickaccesswallet.IQuickAccessWalletServiceCallbacks
        public void onGetWalletCardsSuccess(GetWalletCardsResponse getWalletCardsResponse) throws RemoteException {
        }

        @Override // android.service.quickaccesswallet.IQuickAccessWalletServiceCallbacks
        public void onTargetActivityPendingIntentReceived(PendingIntent pendingIntent) throws RemoteException {
        }

        @Override // android.service.quickaccesswallet.IQuickAccessWalletServiceCallbacks
        public void onWalletServiceEvent(WalletServiceEvent walletServiceEvent) throws RemoteException {
        }
    }

    void onGestureTargetActivityPendingIntentReceived(PendingIntent pendingIntent) throws RemoteException;

    void onGetWalletCardsFailure(GetWalletCardsError getWalletCardsError) throws RemoteException;

    void onGetWalletCardsSuccess(GetWalletCardsResponse getWalletCardsResponse) throws RemoteException;

    void onTargetActivityPendingIntentReceived(PendingIntent pendingIntent) throws RemoteException;

    void onWalletServiceEvent(WalletServiceEvent walletServiceEvent) throws RemoteException;

    public static abstract class Stub extends Binder implements IQuickAccessWalletServiceCallbacks {
        static final int TRANSACTION_onGestureTargetActivityPendingIntentReceived = 5;
        static final int TRANSACTION_onGetWalletCardsFailure = 2;
        static final int TRANSACTION_onGetWalletCardsSuccess = 1;
        static final int TRANSACTION_onTargetActivityPendingIntentReceived = 4;
        static final int TRANSACTION_onWalletServiceEvent = 3;

        @Override // android.os.IInterface
        public IBinder asBinder() {
            return this;
        }

        @Override // android.os.Binder
        public int getMaxTransactionId() {
            return 4;
        }

        public Stub() {
            attachInterface(this, IQuickAccessWalletServiceCallbacks.DESCRIPTOR);
        }

        public static IQuickAccessWalletServiceCallbacks asInterface(IBinder iBinder) {
            if (iBinder == null) {
                return null;
            }
            IInterface iInterfaceQueryLocalInterface = iBinder.queryLocalInterface(IQuickAccessWalletServiceCallbacks.DESCRIPTOR);
            if (iInterfaceQueryLocalInterface != null && (iInterfaceQueryLocalInterface instanceof IQuickAccessWalletServiceCallbacks)) {
                return (IQuickAccessWalletServiceCallbacks) iInterfaceQueryLocalInterface;
            }
            return new Proxy(iBinder);
        }

        public static String getDefaultTransactionName(int i) {
            if (i == 1) {
                return "onGetWalletCardsSuccess";
            }
            if (i == 2) {
                return "onGetWalletCardsFailure";
            }
            if (i == 3) {
                return "onWalletServiceEvent";
            }
            if (i == 4) {
                return "onTargetActivityPendingIntentReceived";
            }
            if (i != 5) {
                return null;
            }
            return "onGestureTargetActivityPendingIntentReceived";
        }

        @Override // android.os.Binder
        public String getTransactionName(int i) {
            return getDefaultTransactionName(i);
        }

        @Override // android.os.Binder
        public boolean onTransact(int i, Parcel parcel, Parcel parcel2, int i2) throws RemoteException {
            if (i >= 1 && i <= 16777215) {
                parcel.enforceInterface(IQuickAccessWalletServiceCallbacks.DESCRIPTOR);
            }
            if (i == 1598968902) {
                parcel2.writeString(IQuickAccessWalletServiceCallbacks.DESCRIPTOR);
                return true;
            }
            if (i == 1) {
                GetWalletCardsResponse getWalletCardsResponse = (GetWalletCardsResponse) parcel.readTypedObject(GetWalletCardsResponse.CREATOR);
                parcel.enforceNoDataAvail();
                onGetWalletCardsSuccess(getWalletCardsResponse);
            } else if (i == 2) {
                GetWalletCardsError getWalletCardsError = (GetWalletCardsError) parcel.readTypedObject(GetWalletCardsError.CREATOR);
                parcel.enforceNoDataAvail();
                onGetWalletCardsFailure(getWalletCardsError);
            } else if (i == 3) {
                WalletServiceEvent walletServiceEvent = (WalletServiceEvent) parcel.readTypedObject(WalletServiceEvent.CREATOR);
                parcel.enforceNoDataAvail();
                onWalletServiceEvent(walletServiceEvent);
            } else if (i == 4) {
                PendingIntent pendingIntent = (PendingIntent) parcel.readTypedObject(PendingIntent.CREATOR);
                parcel.enforceNoDataAvail();
                onTargetActivityPendingIntentReceived(pendingIntent);
            } else if (i == 5) {
                PendingIntent pendingIntent2 = (PendingIntent) parcel.readTypedObject(PendingIntent.CREATOR);
                parcel.enforceNoDataAvail();
                onGestureTargetActivityPendingIntentReceived(pendingIntent2);
            } else {
                return super.onTransact(i, parcel, parcel2, i2);
            }
            return true;
        }

        private static class Proxy implements IQuickAccessWalletServiceCallbacks {
            private IBinder mRemote;

            Proxy(IBinder iBinder) {
                this.mRemote = iBinder;
            }

            @Override // android.os.IInterface
            public IBinder asBinder() {
                return this.mRemote;
            }

            public String getInterfaceDescriptor() {
                return IQuickAccessWalletServiceCallbacks.DESCRIPTOR;
            }

            @Override // android.service.quickaccesswallet.IQuickAccessWalletServiceCallbacks
            public void onGetWalletCardsSuccess(GetWalletCardsResponse getWalletCardsResponse) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                try {
                    parcelObtain.writeInterfaceToken(IQuickAccessWalletServiceCallbacks.DESCRIPTOR);
                    parcelObtain.writeTypedObject(getWalletCardsResponse, 0);
                    this.mRemote.transact(1, parcelObtain, null, 1);
                } finally {
                    parcelObtain.recycle();
                }
            }

            @Override // android.service.quickaccesswallet.IQuickAccessWalletServiceCallbacks
            public void onGetWalletCardsFailure(GetWalletCardsError getWalletCardsError) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                try {
                    parcelObtain.writeInterfaceToken(IQuickAccessWalletServiceCallbacks.DESCRIPTOR);
                    parcelObtain.writeTypedObject(getWalletCardsError, 0);
                    this.mRemote.transact(2, parcelObtain, null, 1);
                } finally {
                    parcelObtain.recycle();
                }
            }

            @Override // android.service.quickaccesswallet.IQuickAccessWalletServiceCallbacks
            public void onWalletServiceEvent(WalletServiceEvent walletServiceEvent) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                try {
                    parcelObtain.writeInterfaceToken(IQuickAccessWalletServiceCallbacks.DESCRIPTOR);
                    parcelObtain.writeTypedObject(walletServiceEvent, 0);
                    this.mRemote.transact(3, parcelObtain, null, 1);
                } finally {
                    parcelObtain.recycle();
                }
            }

            @Override // android.service.quickaccesswallet.IQuickAccessWalletServiceCallbacks
            public void onTargetActivityPendingIntentReceived(PendingIntent pendingIntent) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                try {
                    parcelObtain.writeInterfaceToken(IQuickAccessWalletServiceCallbacks.DESCRIPTOR);
                    parcelObtain.writeTypedObject(pendingIntent, 0);
                    this.mRemote.transact(4, parcelObtain, null, 1);
                } finally {
                    parcelObtain.recycle();
                }
            }

            @Override // android.service.quickaccesswallet.IQuickAccessWalletServiceCallbacks
            public void onGestureTargetActivityPendingIntentReceived(PendingIntent pendingIntent) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                try {
                    parcelObtain.writeInterfaceToken(IQuickAccessWalletServiceCallbacks.DESCRIPTOR);
                    parcelObtain.writeTypedObject(pendingIntent, 0);
                    this.mRemote.transact(5, parcelObtain, null, 1);
                } finally {
                    parcelObtain.recycle();
                }
            }
        }
    }
}
