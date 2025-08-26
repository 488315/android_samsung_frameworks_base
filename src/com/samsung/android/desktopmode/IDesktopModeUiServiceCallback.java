package com.samsung.android.desktopmode;

import android.os.Binder;
import android.os.IBinder;
import android.os.IInterface;
import android.os.Parcel;
import android.os.RemoteException;

/* loaded from: classes6.dex */
public interface IDesktopModeUiServiceCallback extends IInterface {
    public static final String DESCRIPTOR = "com.samsung.android.desktopmode.IDesktopModeUiServiceCallback";

    public static class Default implements IDesktopModeUiServiceCallback {
        @Override // android.os.IInterface
        public IBinder asBinder() {
            return null;
        }

        @Override // com.samsung.android.desktopmode.IDesktopModeUiServiceCallback
        public void onAnimationComplete() throws RemoteException {
        }

        @Override // com.samsung.android.desktopmode.IDesktopModeUiServiceCallback
        public void onClickButtonNegative() throws RemoteException {
        }

        @Override // com.samsung.android.desktopmode.IDesktopModeUiServiceCallback
        public void onClickButtonPositive() throws RemoteException {
        }

        @Override // com.samsung.android.desktopmode.IDesktopModeUiServiceCallback
        public void onDismiss() throws RemoteException {
        }

        @Override // com.samsung.android.desktopmode.IDesktopModeUiServiceCallback
        public void onShow() throws RemoteException {
        }
    }

    void onAnimationComplete() throws RemoteException;

    void onClickButtonNegative() throws RemoteException;

    void onClickButtonPositive() throws RemoteException;

    void onDismiss() throws RemoteException;

    void onShow() throws RemoteException;

    public static abstract class Stub extends Binder implements IDesktopModeUiServiceCallback {
        static final int TRANSACTION_onAnimationComplete = 5;
        static final int TRANSACTION_onClickButtonNegative = 2;
        static final int TRANSACTION_onClickButtonPositive = 1;
        static final int TRANSACTION_onDismiss = 4;
        static final int TRANSACTION_onShow = 3;

        @Override // android.os.IInterface
        public IBinder asBinder() {
            return this;
        }

        @Override // android.os.Binder
        public int getMaxTransactionId() {
            return 4;
        }

        public Stub() {
            attachInterface(this, IDesktopModeUiServiceCallback.DESCRIPTOR);
        }

        public static IDesktopModeUiServiceCallback asInterface(IBinder iBinder) {
            if (iBinder == null) {
                return null;
            }
            IInterface iInterfaceQueryLocalInterface = iBinder.queryLocalInterface(IDesktopModeUiServiceCallback.DESCRIPTOR);
            if (iInterfaceQueryLocalInterface != null && (iInterfaceQueryLocalInterface instanceof IDesktopModeUiServiceCallback)) {
                return (IDesktopModeUiServiceCallback) iInterfaceQueryLocalInterface;
            }
            return new Proxy(iBinder);
        }

        public static String getDefaultTransactionName(int i) {
            if (i == 1) {
                return "onClickButtonPositive";
            }
            if (i == 2) {
                return "onClickButtonNegative";
            }
            if (i == 3) {
                return "onShow";
            }
            if (i == 4) {
                return "onDismiss";
            }
            if (i != 5) {
                return null;
            }
            return "onAnimationComplete";
        }

        @Override // android.os.Binder
        public String getTransactionName(int i) {
            return getDefaultTransactionName(i);
        }

        @Override // android.os.Binder
        public boolean onTransact(int i, Parcel parcel, Parcel parcel2, int i2) throws RemoteException {
            if (i >= 1 && i <= 16777215) {
                parcel.enforceInterface(IDesktopModeUiServiceCallback.DESCRIPTOR);
            }
            if (i == 1598968902) {
                parcel2.writeString(IDesktopModeUiServiceCallback.DESCRIPTOR);
                return true;
            }
            if (i == 1) {
                onClickButtonPositive();
            } else if (i == 2) {
                onClickButtonNegative();
            } else if (i == 3) {
                onShow();
            } else if (i == 4) {
                onDismiss();
            } else if (i == 5) {
                onAnimationComplete();
            } else {
                return super.onTransact(i, parcel, parcel2, i2);
            }
            return true;
        }

        private static class Proxy implements IDesktopModeUiServiceCallback {
            private IBinder mRemote;

            Proxy(IBinder iBinder) {
                this.mRemote = iBinder;
            }

            @Override // android.os.IInterface
            public IBinder asBinder() {
                return this.mRemote;
            }

            public String getInterfaceDescriptor() {
                return IDesktopModeUiServiceCallback.DESCRIPTOR;
            }

            @Override // com.samsung.android.desktopmode.IDesktopModeUiServiceCallback
            public void onClickButtonPositive() throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                try {
                    parcelObtain.writeInterfaceToken(IDesktopModeUiServiceCallback.DESCRIPTOR);
                    this.mRemote.transact(1, parcelObtain, null, 1);
                } finally {
                    parcelObtain.recycle();
                }
            }

            @Override // com.samsung.android.desktopmode.IDesktopModeUiServiceCallback
            public void onClickButtonNegative() throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                try {
                    parcelObtain.writeInterfaceToken(IDesktopModeUiServiceCallback.DESCRIPTOR);
                    this.mRemote.transact(2, parcelObtain, null, 1);
                } finally {
                    parcelObtain.recycle();
                }
            }

            @Override // com.samsung.android.desktopmode.IDesktopModeUiServiceCallback
            public void onShow() throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                try {
                    parcelObtain.writeInterfaceToken(IDesktopModeUiServiceCallback.DESCRIPTOR);
                    this.mRemote.transact(3, parcelObtain, null, 1);
                } finally {
                    parcelObtain.recycle();
                }
            }

            @Override // com.samsung.android.desktopmode.IDesktopModeUiServiceCallback
            public void onDismiss() throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                try {
                    parcelObtain.writeInterfaceToken(IDesktopModeUiServiceCallback.DESCRIPTOR);
                    this.mRemote.transact(4, parcelObtain, null, 1);
                } finally {
                    parcelObtain.recycle();
                }
            }

            @Override // com.samsung.android.desktopmode.IDesktopModeUiServiceCallback
            public void onAnimationComplete() throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                try {
                    parcelObtain.writeInterfaceToken(IDesktopModeUiServiceCallback.DESCRIPTOR);
                    this.mRemote.transact(5, parcelObtain, null, 1);
                } finally {
                    parcelObtain.recycle();
                }
            }
        }
    }
}
