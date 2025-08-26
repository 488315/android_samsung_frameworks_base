package android.window;

import android.os.Binder;
import android.os.IBinder;
import android.os.IInterface;
import android.os.Parcel;
import android.os.RemoteException;
import android.window.IBackAnimationHandoffHandler;

/* loaded from: classes5.dex */
public interface IOnBackInvokedCallback extends IInterface {
    public static final String DESCRIPTOR = "android.window.IOnBackInvokedCallback";

    public static class Default implements IOnBackInvokedCallback {
        @Override // android.os.IInterface
        public IBinder asBinder() {
            return null;
        }

        @Override // android.window.IOnBackInvokedCallback
        public void onBackCancelled() throws RemoteException {
        }

        @Override // android.window.IOnBackInvokedCallback
        public void onBackInvoked() throws RemoteException {
        }

        @Override // android.window.IOnBackInvokedCallback
        public void onBackProgressed(BackMotionEvent backMotionEvent) throws RemoteException {
        }

        @Override // android.window.IOnBackInvokedCallback
        public void onBackStarted(BackMotionEvent backMotionEvent) throws RemoteException {
        }

        @Override // android.window.IOnBackInvokedCallback
        public void setHandoffHandler(IBackAnimationHandoffHandler iBackAnimationHandoffHandler) throws RemoteException {
        }

        @Override // android.window.IOnBackInvokedCallback
        public void setTriggerBack(boolean z) throws RemoteException {
        }
    }

    void onBackCancelled() throws RemoteException;

    void onBackInvoked() throws RemoteException;

    void onBackProgressed(BackMotionEvent backMotionEvent) throws RemoteException;

    void onBackStarted(BackMotionEvent backMotionEvent) throws RemoteException;

    void setHandoffHandler(IBackAnimationHandoffHandler iBackAnimationHandoffHandler) throws RemoteException;

    void setTriggerBack(boolean z) throws RemoteException;

    public static abstract class Stub extends Binder implements IOnBackInvokedCallback {
        static final int TRANSACTION_onBackCancelled = 3;
        static final int TRANSACTION_onBackInvoked = 4;
        static final int TRANSACTION_onBackProgressed = 2;
        static final int TRANSACTION_onBackStarted = 1;
        static final int TRANSACTION_setHandoffHandler = 6;
        static final int TRANSACTION_setTriggerBack = 5;

        @Override // android.os.IInterface
        public IBinder asBinder() {
            return this;
        }

        @Override // android.os.Binder
        public int getMaxTransactionId() {
            return 5;
        }

        public Stub() {
            attachInterface(this, IOnBackInvokedCallback.DESCRIPTOR);
        }

        public static IOnBackInvokedCallback asInterface(IBinder iBinder) {
            if (iBinder == null) {
                return null;
            }
            IInterface iInterfaceQueryLocalInterface = iBinder.queryLocalInterface(IOnBackInvokedCallback.DESCRIPTOR);
            if (iInterfaceQueryLocalInterface != null && (iInterfaceQueryLocalInterface instanceof IOnBackInvokedCallback)) {
                return (IOnBackInvokedCallback) iInterfaceQueryLocalInterface;
            }
            return new Proxy(iBinder);
        }

        public static String getDefaultTransactionName(int i) {
            switch (i) {
                case 1:
                    return "onBackStarted";
                case 2:
                    return "onBackProgressed";
                case 3:
                    return "onBackCancelled";
                case 4:
                    return "onBackInvoked";
                case 5:
                    return "setTriggerBack";
                case 6:
                    return "setHandoffHandler";
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
                parcel.enforceInterface(IOnBackInvokedCallback.DESCRIPTOR);
            }
            if (i == 1598968902) {
                parcel2.writeString(IOnBackInvokedCallback.DESCRIPTOR);
                return true;
            }
            switch (i) {
                case 1:
                    BackMotionEvent backMotionEvent = (BackMotionEvent) parcel.readTypedObject(BackMotionEvent.CREATOR);
                    parcel.enforceNoDataAvail();
                    onBackStarted(backMotionEvent);
                    return true;
                case 2:
                    BackMotionEvent backMotionEvent2 = (BackMotionEvent) parcel.readTypedObject(BackMotionEvent.CREATOR);
                    parcel.enforceNoDataAvail();
                    onBackProgressed(backMotionEvent2);
                    return true;
                case 3:
                    onBackCancelled();
                    return true;
                case 4:
                    onBackInvoked();
                    return true;
                case 5:
                    boolean z = parcel.readBoolean();
                    parcel.enforceNoDataAvail();
                    setTriggerBack(z);
                    return true;
                case 6:
                    IBackAnimationHandoffHandler iBackAnimationHandoffHandlerAsInterface = IBackAnimationHandoffHandler.Stub.asInterface(parcel.readStrongBinder());
                    parcel.enforceNoDataAvail();
                    setHandoffHandler(iBackAnimationHandoffHandlerAsInterface);
                    return true;
                default:
                    return super.onTransact(i, parcel, parcel2, i2);
            }
        }

        private static class Proxy implements IOnBackInvokedCallback {
            private IBinder mRemote;

            Proxy(IBinder iBinder) {
                this.mRemote = iBinder;
            }

            @Override // android.os.IInterface
            public IBinder asBinder() {
                return this.mRemote;
            }

            public String getInterfaceDescriptor() {
                return IOnBackInvokedCallback.DESCRIPTOR;
            }

            @Override // android.window.IOnBackInvokedCallback
            public void onBackStarted(BackMotionEvent backMotionEvent) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                try {
                    parcelObtain.writeInterfaceToken(IOnBackInvokedCallback.DESCRIPTOR);
                    parcelObtain.writeTypedObject(backMotionEvent, 0);
                    this.mRemote.transact(1, parcelObtain, null, 1);
                } finally {
                    parcelObtain.recycle();
                }
            }

            @Override // android.window.IOnBackInvokedCallback
            public void onBackProgressed(BackMotionEvent backMotionEvent) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                try {
                    parcelObtain.writeInterfaceToken(IOnBackInvokedCallback.DESCRIPTOR);
                    parcelObtain.writeTypedObject(backMotionEvent, 0);
                    this.mRemote.transact(2, parcelObtain, null, 1);
                } finally {
                    parcelObtain.recycle();
                }
            }

            @Override // android.window.IOnBackInvokedCallback
            public void onBackCancelled() throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                try {
                    parcelObtain.writeInterfaceToken(IOnBackInvokedCallback.DESCRIPTOR);
                    this.mRemote.transact(3, parcelObtain, null, 1);
                } finally {
                    parcelObtain.recycle();
                }
            }

            @Override // android.window.IOnBackInvokedCallback
            public void onBackInvoked() throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                try {
                    parcelObtain.writeInterfaceToken(IOnBackInvokedCallback.DESCRIPTOR);
                    this.mRemote.transact(4, parcelObtain, null, 1);
                } finally {
                    parcelObtain.recycle();
                }
            }

            @Override // android.window.IOnBackInvokedCallback
            public void setTriggerBack(boolean z) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                try {
                    parcelObtain.writeInterfaceToken(IOnBackInvokedCallback.DESCRIPTOR);
                    parcelObtain.writeBoolean(z);
                    this.mRemote.transact(5, parcelObtain, null, 1);
                } finally {
                    parcelObtain.recycle();
                }
            }

            @Override // android.window.IOnBackInvokedCallback
            public void setHandoffHandler(IBackAnimationHandoffHandler iBackAnimationHandoffHandler) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                try {
                    parcelObtain.writeInterfaceToken(IOnBackInvokedCallback.DESCRIPTOR);
                    parcelObtain.writeStrongInterface(iBackAnimationHandoffHandler);
                    this.mRemote.transact(6, parcelObtain, null, 1);
                } finally {
                    parcelObtain.recycle();
                }
            }
        }
    }
}
