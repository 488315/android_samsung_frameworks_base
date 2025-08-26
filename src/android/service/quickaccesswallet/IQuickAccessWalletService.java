package android.service.quickaccesswallet;

import android.os.Binder;
import android.os.IBinder;
import android.os.IInterface;
import android.os.Parcel;
import android.os.RemoteException;
import android.service.quickaccesswallet.IQuickAccessWalletServiceCallbacks;

/* loaded from: classes3.dex */
public interface IQuickAccessWalletService extends IInterface {
    public static final String DESCRIPTOR = "android.service.quickaccesswallet.IQuickAccessWalletService";

    public static class Default implements IQuickAccessWalletService {
        @Override // android.os.IInterface
        public IBinder asBinder() {
            return null;
        }

        @Override // android.service.quickaccesswallet.IQuickAccessWalletService
        public void onGestureTargetActivityIntentRequested(IQuickAccessWalletServiceCallbacks iQuickAccessWalletServiceCallbacks) throws RemoteException {
        }

        @Override // android.service.quickaccesswallet.IQuickAccessWalletService
        public void onTargetActivityIntentRequested(IQuickAccessWalletServiceCallbacks iQuickAccessWalletServiceCallbacks) throws RemoteException {
        }

        @Override // android.service.quickaccesswallet.IQuickAccessWalletService
        public void onWalletCardSelected(SelectWalletCardRequest selectWalletCardRequest) throws RemoteException {
        }

        @Override // android.service.quickaccesswallet.IQuickAccessWalletService
        public void onWalletCardsRequested(GetWalletCardsRequest getWalletCardsRequest, IQuickAccessWalletServiceCallbacks iQuickAccessWalletServiceCallbacks) throws RemoteException {
        }

        @Override // android.service.quickaccesswallet.IQuickAccessWalletService
        public void onWalletDismissed() throws RemoteException {
        }

        @Override // android.service.quickaccesswallet.IQuickAccessWalletService
        public void registerWalletServiceEventListener(WalletServiceEventListenerRequest walletServiceEventListenerRequest, IQuickAccessWalletServiceCallbacks iQuickAccessWalletServiceCallbacks) throws RemoteException {
        }

        @Override // android.service.quickaccesswallet.IQuickAccessWalletService
        public void unregisterWalletServiceEventListener(WalletServiceEventListenerRequest walletServiceEventListenerRequest) throws RemoteException {
        }
    }

    void onGestureTargetActivityIntentRequested(IQuickAccessWalletServiceCallbacks iQuickAccessWalletServiceCallbacks) throws RemoteException;

    void onTargetActivityIntentRequested(IQuickAccessWalletServiceCallbacks iQuickAccessWalletServiceCallbacks) throws RemoteException;

    void onWalletCardSelected(SelectWalletCardRequest selectWalletCardRequest) throws RemoteException;

    void onWalletCardsRequested(GetWalletCardsRequest getWalletCardsRequest, IQuickAccessWalletServiceCallbacks iQuickAccessWalletServiceCallbacks) throws RemoteException;

    void onWalletDismissed() throws RemoteException;

    void registerWalletServiceEventListener(WalletServiceEventListenerRequest walletServiceEventListenerRequest, IQuickAccessWalletServiceCallbacks iQuickAccessWalletServiceCallbacks) throws RemoteException;

    void unregisterWalletServiceEventListener(WalletServiceEventListenerRequest walletServiceEventListenerRequest) throws RemoteException;

    public static abstract class Stub extends Binder implements IQuickAccessWalletService {
        static final int TRANSACTION_onGestureTargetActivityIntentRequested = 7;
        static final int TRANSACTION_onTargetActivityIntentRequested = 6;
        static final int TRANSACTION_onWalletCardSelected = 2;
        static final int TRANSACTION_onWalletCardsRequested = 1;
        static final int TRANSACTION_onWalletDismissed = 3;
        static final int TRANSACTION_registerWalletServiceEventListener = 4;
        static final int TRANSACTION_unregisterWalletServiceEventListener = 5;

        @Override // android.os.IInterface
        public IBinder asBinder() {
            return this;
        }

        @Override // android.os.Binder
        public int getMaxTransactionId() {
            return 6;
        }

        public Stub() {
            attachInterface(this, IQuickAccessWalletService.DESCRIPTOR);
        }

        public static IQuickAccessWalletService asInterface(IBinder iBinder) {
            if (iBinder == null) {
                return null;
            }
            IInterface iInterfaceQueryLocalInterface = iBinder.queryLocalInterface(IQuickAccessWalletService.DESCRIPTOR);
            if (iInterfaceQueryLocalInterface != null && (iInterfaceQueryLocalInterface instanceof IQuickAccessWalletService)) {
                return (IQuickAccessWalletService) iInterfaceQueryLocalInterface;
            }
            return new Proxy(iBinder);
        }

        public static String getDefaultTransactionName(int i) {
            switch (i) {
                case 1:
                    return "onWalletCardsRequested";
                case 2:
                    return "onWalletCardSelected";
                case 3:
                    return "onWalletDismissed";
                case 4:
                    return "registerWalletServiceEventListener";
                case 5:
                    return "unregisterWalletServiceEventListener";
                case 6:
                    return "onTargetActivityIntentRequested";
                case 7:
                    return "onGestureTargetActivityIntentRequested";
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
                parcel.enforceInterface(IQuickAccessWalletService.DESCRIPTOR);
            }
            if (i == 1598968902) {
                parcel2.writeString(IQuickAccessWalletService.DESCRIPTOR);
                return true;
            }
            switch (i) {
                case 1:
                    GetWalletCardsRequest getWalletCardsRequest = (GetWalletCardsRequest) parcel.readTypedObject(GetWalletCardsRequest.CREATOR);
                    IQuickAccessWalletServiceCallbacks iQuickAccessWalletServiceCallbacksAsInterface = IQuickAccessWalletServiceCallbacks.Stub.asInterface(parcel.readStrongBinder());
                    parcel.enforceNoDataAvail();
                    onWalletCardsRequested(getWalletCardsRequest, iQuickAccessWalletServiceCallbacksAsInterface);
                    return true;
                case 2:
                    SelectWalletCardRequest selectWalletCardRequest = (SelectWalletCardRequest) parcel.readTypedObject(SelectWalletCardRequest.CREATOR);
                    parcel.enforceNoDataAvail();
                    onWalletCardSelected(selectWalletCardRequest);
                    return true;
                case 3:
                    onWalletDismissed();
                    return true;
                case 4:
                    WalletServiceEventListenerRequest walletServiceEventListenerRequest = (WalletServiceEventListenerRequest) parcel.readTypedObject(WalletServiceEventListenerRequest.CREATOR);
                    IQuickAccessWalletServiceCallbacks iQuickAccessWalletServiceCallbacksAsInterface2 = IQuickAccessWalletServiceCallbacks.Stub.asInterface(parcel.readStrongBinder());
                    parcel.enforceNoDataAvail();
                    registerWalletServiceEventListener(walletServiceEventListenerRequest, iQuickAccessWalletServiceCallbacksAsInterface2);
                    return true;
                case 5:
                    WalletServiceEventListenerRequest walletServiceEventListenerRequest2 = (WalletServiceEventListenerRequest) parcel.readTypedObject(WalletServiceEventListenerRequest.CREATOR);
                    parcel.enforceNoDataAvail();
                    unregisterWalletServiceEventListener(walletServiceEventListenerRequest2);
                    return true;
                case 6:
                    IQuickAccessWalletServiceCallbacks iQuickAccessWalletServiceCallbacksAsInterface3 = IQuickAccessWalletServiceCallbacks.Stub.asInterface(parcel.readStrongBinder());
                    parcel.enforceNoDataAvail();
                    onTargetActivityIntentRequested(iQuickAccessWalletServiceCallbacksAsInterface3);
                    return true;
                case 7:
                    IQuickAccessWalletServiceCallbacks iQuickAccessWalletServiceCallbacksAsInterface4 = IQuickAccessWalletServiceCallbacks.Stub.asInterface(parcel.readStrongBinder());
                    parcel.enforceNoDataAvail();
                    onGestureTargetActivityIntentRequested(iQuickAccessWalletServiceCallbacksAsInterface4);
                    return true;
                default:
                    return super.onTransact(i, parcel, parcel2, i2);
            }
        }

        private static class Proxy implements IQuickAccessWalletService {
            private IBinder mRemote;

            Proxy(IBinder iBinder) {
                this.mRemote = iBinder;
            }

            @Override // android.os.IInterface
            public IBinder asBinder() {
                return this.mRemote;
            }

            public String getInterfaceDescriptor() {
                return IQuickAccessWalletService.DESCRIPTOR;
            }

            @Override // android.service.quickaccesswallet.IQuickAccessWalletService
            public void onWalletCardsRequested(GetWalletCardsRequest getWalletCardsRequest, IQuickAccessWalletServiceCallbacks iQuickAccessWalletServiceCallbacks) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                try {
                    parcelObtain.writeInterfaceToken(IQuickAccessWalletService.DESCRIPTOR);
                    parcelObtain.writeTypedObject(getWalletCardsRequest, 0);
                    parcelObtain.writeStrongInterface(iQuickAccessWalletServiceCallbacks);
                    this.mRemote.transact(1, parcelObtain, null, 1);
                } finally {
                    parcelObtain.recycle();
                }
            }

            @Override // android.service.quickaccesswallet.IQuickAccessWalletService
            public void onWalletCardSelected(SelectWalletCardRequest selectWalletCardRequest) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                try {
                    parcelObtain.writeInterfaceToken(IQuickAccessWalletService.DESCRIPTOR);
                    parcelObtain.writeTypedObject(selectWalletCardRequest, 0);
                    this.mRemote.transact(2, parcelObtain, null, 1);
                } finally {
                    parcelObtain.recycle();
                }
            }

            @Override // android.service.quickaccesswallet.IQuickAccessWalletService
            public void onWalletDismissed() throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                try {
                    parcelObtain.writeInterfaceToken(IQuickAccessWalletService.DESCRIPTOR);
                    this.mRemote.transact(3, parcelObtain, null, 1);
                } finally {
                    parcelObtain.recycle();
                }
            }

            @Override // android.service.quickaccesswallet.IQuickAccessWalletService
            public void registerWalletServiceEventListener(WalletServiceEventListenerRequest walletServiceEventListenerRequest, IQuickAccessWalletServiceCallbacks iQuickAccessWalletServiceCallbacks) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                try {
                    parcelObtain.writeInterfaceToken(IQuickAccessWalletService.DESCRIPTOR);
                    parcelObtain.writeTypedObject(walletServiceEventListenerRequest, 0);
                    parcelObtain.writeStrongInterface(iQuickAccessWalletServiceCallbacks);
                    this.mRemote.transact(4, parcelObtain, null, 1);
                } finally {
                    parcelObtain.recycle();
                }
            }

            @Override // android.service.quickaccesswallet.IQuickAccessWalletService
            public void unregisterWalletServiceEventListener(WalletServiceEventListenerRequest walletServiceEventListenerRequest) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                try {
                    parcelObtain.writeInterfaceToken(IQuickAccessWalletService.DESCRIPTOR);
                    parcelObtain.writeTypedObject(walletServiceEventListenerRequest, 0);
                    this.mRemote.transact(5, parcelObtain, null, 1);
                } finally {
                    parcelObtain.recycle();
                }
            }

            @Override // android.service.quickaccesswallet.IQuickAccessWalletService
            public void onTargetActivityIntentRequested(IQuickAccessWalletServiceCallbacks iQuickAccessWalletServiceCallbacks) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                try {
                    parcelObtain.writeInterfaceToken(IQuickAccessWalletService.DESCRIPTOR);
                    parcelObtain.writeStrongInterface(iQuickAccessWalletServiceCallbacks);
                    this.mRemote.transact(6, parcelObtain, null, 1);
                } finally {
                    parcelObtain.recycle();
                }
            }

            @Override // android.service.quickaccesswallet.IQuickAccessWalletService
            public void onGestureTargetActivityIntentRequested(IQuickAccessWalletServiceCallbacks iQuickAccessWalletServiceCallbacks) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                try {
                    parcelObtain.writeInterfaceToken(IQuickAccessWalletService.DESCRIPTOR);
                    parcelObtain.writeStrongInterface(iQuickAccessWalletServiceCallbacks);
                    this.mRemote.transact(7, parcelObtain, null, 1);
                } finally {
                    parcelObtain.recycle();
                }
            }
        }
    }
}
