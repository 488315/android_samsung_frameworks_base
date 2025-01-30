package com.samsung.android.desktopmode;

import android.p009os.Binder;
import android.p009os.IBinder;
import android.p009os.IInterface;
import android.p009os.Parcel;
import android.p009os.RemoteException;

/* loaded from: classes5.dex */
public interface IDesktopModeUiServiceCallback extends IInterface {
    public static final String DESCRIPTOR = "com.samsung.android.desktopmode.IDesktopModeUiServiceCallback";

    void onAnimationComplete() throws RemoteException;

    void onClickButtonNegative() throws RemoteException;

    void onClickButtonPositive() throws RemoteException;

    void onDismiss() throws RemoteException;

    void onShow() throws RemoteException;

    public static class Default implements IDesktopModeUiServiceCallback {
        @Override // com.samsung.android.desktopmode.IDesktopModeUiServiceCallback
        public void onClickButtonPositive() throws RemoteException {
        }

        @Override // com.samsung.android.desktopmode.IDesktopModeUiServiceCallback
        public void onClickButtonNegative() throws RemoteException {
        }

        @Override // com.samsung.android.desktopmode.IDesktopModeUiServiceCallback
        public void onShow() throws RemoteException {
        }

        @Override // com.samsung.android.desktopmode.IDesktopModeUiServiceCallback
        public void onDismiss() throws RemoteException {
        }

        @Override // com.samsung.android.desktopmode.IDesktopModeUiServiceCallback
        public void onAnimationComplete() throws RemoteException {
        }

        @Override // android.p009os.IInterface
        public IBinder asBinder() {
            return null;
        }
    }

    public static abstract class Stub extends Binder implements IDesktopModeUiServiceCallback {
        static final int TRANSACTION_onAnimationComplete = 5;
        static final int TRANSACTION_onClickButtonNegative = 2;
        static final int TRANSACTION_onClickButtonPositive = 1;
        static final int TRANSACTION_onDismiss = 4;
        static final int TRANSACTION_onShow = 3;

        public Stub() {
            attachInterface(this, IDesktopModeUiServiceCallback.DESCRIPTOR);
        }

        public static IDesktopModeUiServiceCallback asInterface(IBinder obj) {
            if (obj == null) {
                return null;
            }
            IInterface iin = obj.queryLocalInterface(IDesktopModeUiServiceCallback.DESCRIPTOR);
            if (iin != null && (iin instanceof IDesktopModeUiServiceCallback)) {
                return (IDesktopModeUiServiceCallback) iin;
            }
            return new Proxy(obj);
        }

        @Override // android.p009os.IInterface
        public IBinder asBinder() {
            return this;
        }

        public static String getDefaultTransactionName(int transactionCode) {
            switch (transactionCode) {
                case 1:
                    return "onClickButtonPositive";
                case 2:
                    return "onClickButtonNegative";
                case 3:
                    return "onShow";
                case 4:
                    return "onDismiss";
                case 5:
                    return "onAnimationComplete";
                default:
                    return null;
            }
        }

        @Override // android.p009os.Binder
        public String getTransactionName(int transactionCode) {
            return getDefaultTransactionName(transactionCode);
        }

        @Override // android.p009os.Binder
        public boolean onTransact(int code, Parcel data, Parcel reply, int flags) throws RemoteException {
            if (code >= 1 && code <= 16777215) {
                data.enforceInterface(IDesktopModeUiServiceCallback.DESCRIPTOR);
            }
            switch (code) {
                case IBinder.INTERFACE_TRANSACTION /* 1598968902 */:
                    reply.writeString(IDesktopModeUiServiceCallback.DESCRIPTOR);
                    return true;
                default:
                    switch (code) {
                        case 1:
                            onClickButtonPositive();
                            return true;
                        case 2:
                            onClickButtonNegative();
                            return true;
                        case 3:
                            onShow();
                            return true;
                        case 4:
                            onDismiss();
                            return true;
                        case 5:
                            onAnimationComplete();
                            return true;
                        default:
                            return super.onTransact(code, data, reply, flags);
                    }
            }
        }

        private static class Proxy implements IDesktopModeUiServiceCallback {
            private IBinder mRemote;

            Proxy(IBinder remote) {
                this.mRemote = remote;
            }

            @Override // android.p009os.IInterface
            public IBinder asBinder() {
                return this.mRemote;
            }

            public String getInterfaceDescriptor() {
                return IDesktopModeUiServiceCallback.DESCRIPTOR;
            }

            @Override // com.samsung.android.desktopmode.IDesktopModeUiServiceCallback
            public void onClickButtonPositive() throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                try {
                    _data.writeInterfaceToken(IDesktopModeUiServiceCallback.DESCRIPTOR);
                    this.mRemote.transact(1, _data, null, 1);
                } finally {
                    _data.recycle();
                }
            }

            @Override // com.samsung.android.desktopmode.IDesktopModeUiServiceCallback
            public void onClickButtonNegative() throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                try {
                    _data.writeInterfaceToken(IDesktopModeUiServiceCallback.DESCRIPTOR);
                    this.mRemote.transact(2, _data, null, 1);
                } finally {
                    _data.recycle();
                }
            }

            @Override // com.samsung.android.desktopmode.IDesktopModeUiServiceCallback
            public void onShow() throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                try {
                    _data.writeInterfaceToken(IDesktopModeUiServiceCallback.DESCRIPTOR);
                    this.mRemote.transact(3, _data, null, 1);
                } finally {
                    _data.recycle();
                }
            }

            @Override // com.samsung.android.desktopmode.IDesktopModeUiServiceCallback
            public void onDismiss() throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                try {
                    _data.writeInterfaceToken(IDesktopModeUiServiceCallback.DESCRIPTOR);
                    this.mRemote.transact(4, _data, null, 1);
                } finally {
                    _data.recycle();
                }
            }

            @Override // com.samsung.android.desktopmode.IDesktopModeUiServiceCallback
            public void onAnimationComplete() throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                try {
                    _data.writeInterfaceToken(IDesktopModeUiServiceCallback.DESCRIPTOR);
                    this.mRemote.transact(5, _data, null, 1);
                } finally {
                    _data.recycle();
                }
            }
        }

        @Override // android.p009os.Binder
        public int getMaxTransactionId() {
            return 4;
        }
    }
}
