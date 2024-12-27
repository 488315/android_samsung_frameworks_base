package com.samsung.android.multiwindow;

import android.os.Binder;
import android.os.IBinder;
import android.os.IInterface;
import android.os.Parcel;
import android.os.RemoteException;

public interface IRemoteAppTransitionListener extends IInterface {
    public static final String DESCRIPTOR =
            "com.samsung.android.multiwindow.IRemoteAppTransitionListener";

    void onFinishRecentsAnimation(boolean z) throws RemoteException;

    void onStartHomeAnimation(boolean z) throws RemoteException;

    void onStartRecentsAnimation(boolean z) throws RemoteException;

    void onWallpaperVisibilityChanged(boolean z, boolean z2) throws RemoteException;

    public static class Default implements IRemoteAppTransitionListener {
        @Override // com.samsung.android.multiwindow.IRemoteAppTransitionListener
        public void onStartRecentsAnimation(boolean isHomeOrRecentsVisible)
                throws RemoteException {}

        @Override // com.samsung.android.multiwindow.IRemoteAppTransitionListener
        public void onFinishRecentsAnimation(boolean isHomeOrRecentsVisible)
                throws RemoteException {}

        @Override // com.samsung.android.multiwindow.IRemoteAppTransitionListener
        public void onStartHomeAnimation(boolean isHomeOrRecentsVisible) throws RemoteException {}

        @Override // com.samsung.android.multiwindow.IRemoteAppTransitionListener
        public void onWallpaperVisibilityChanged(
                boolean wallpaerVisibility, boolean isHomeOrRecentsVisible)
                throws RemoteException {}

        @Override // android.os.IInterface
        public IBinder asBinder() {
            return null;
        }
    }

    public abstract static class Stub extends Binder implements IRemoteAppTransitionListener {
        static final int TRANSACTION_onFinishRecentsAnimation = 2;
        static final int TRANSACTION_onStartHomeAnimation = 3;
        static final int TRANSACTION_onStartRecentsAnimation = 1;
        static final int TRANSACTION_onWallpaperVisibilityChanged = 4;

        public Stub() {
            attachInterface(this, IRemoteAppTransitionListener.DESCRIPTOR);
        }

        public static IRemoteAppTransitionListener asInterface(IBinder obj) {
            if (obj == null) {
                return null;
            }
            IInterface iin = obj.queryLocalInterface(IRemoteAppTransitionListener.DESCRIPTOR);
            if (iin != null && (iin instanceof IRemoteAppTransitionListener)) {
                return (IRemoteAppTransitionListener) iin;
            }
            return new Proxy(obj);
        }

        @Override // android.os.IInterface
        public IBinder asBinder() {
            return this;
        }

        public static String getDefaultTransactionName(int transactionCode) {
            switch (transactionCode) {
                case 1:
                    return "onStartRecentsAnimation";
                case 2:
                    return "onFinishRecentsAnimation";
                case 3:
                    return "onStartHomeAnimation";
                case 4:
                    return "onWallpaperVisibilityChanged";
                default:
                    return null;
            }
        }

        @Override // android.os.Binder
        public String getTransactionName(int transactionCode) {
            return getDefaultTransactionName(transactionCode);
        }

        @Override // android.os.Binder
        public boolean onTransact(int code, Parcel data, Parcel reply, int flags)
                throws RemoteException {
            if (code >= 1 && code <= 16777215) {
                data.enforceInterface(IRemoteAppTransitionListener.DESCRIPTOR);
            }
            if (code == 1598968902) {
                reply.writeString(IRemoteAppTransitionListener.DESCRIPTOR);
                return true;
            }
            switch (code) {
                case 1:
                    boolean _arg0 = data.readBoolean();
                    data.enforceNoDataAvail();
                    onStartRecentsAnimation(_arg0);
                    return true;
                case 2:
                    boolean _arg02 = data.readBoolean();
                    data.enforceNoDataAvail();
                    onFinishRecentsAnimation(_arg02);
                    return true;
                case 3:
                    boolean _arg03 = data.readBoolean();
                    data.enforceNoDataAvail();
                    onStartHomeAnimation(_arg03);
                    return true;
                case 4:
                    boolean _arg04 = data.readBoolean();
                    boolean _arg1 = data.readBoolean();
                    data.enforceNoDataAvail();
                    onWallpaperVisibilityChanged(_arg04, _arg1);
                    return true;
                default:
                    return super.onTransact(code, data, reply, flags);
            }
        }

        private static class Proxy implements IRemoteAppTransitionListener {
            private IBinder mRemote;

            Proxy(IBinder remote) {
                this.mRemote = remote;
            }

            @Override // android.os.IInterface
            public IBinder asBinder() {
                return this.mRemote;
            }

            public String getInterfaceDescriptor() {
                return IRemoteAppTransitionListener.DESCRIPTOR;
            }

            @Override // com.samsung.android.multiwindow.IRemoteAppTransitionListener
            public void onStartRecentsAnimation(boolean isHomeOrRecentsVisible)
                    throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                try {
                    _data.writeInterfaceToken(IRemoteAppTransitionListener.DESCRIPTOR);
                    _data.writeBoolean(isHomeOrRecentsVisible);
                    this.mRemote.transact(1, _data, null, 1);
                } finally {
                    _data.recycle();
                }
            }

            @Override // com.samsung.android.multiwindow.IRemoteAppTransitionListener
            public void onFinishRecentsAnimation(boolean isHomeOrRecentsVisible)
                    throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                try {
                    _data.writeInterfaceToken(IRemoteAppTransitionListener.DESCRIPTOR);
                    _data.writeBoolean(isHomeOrRecentsVisible);
                    this.mRemote.transact(2, _data, null, 1);
                } finally {
                    _data.recycle();
                }
            }

            @Override // com.samsung.android.multiwindow.IRemoteAppTransitionListener
            public void onStartHomeAnimation(boolean isHomeOrRecentsVisible)
                    throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                try {
                    _data.writeInterfaceToken(IRemoteAppTransitionListener.DESCRIPTOR);
                    _data.writeBoolean(isHomeOrRecentsVisible);
                    this.mRemote.transact(3, _data, null, 1);
                } finally {
                    _data.recycle();
                }
            }

            @Override // com.samsung.android.multiwindow.IRemoteAppTransitionListener
            public void onWallpaperVisibilityChanged(
                    boolean wallpaerVisibility, boolean isHomeOrRecentsVisible)
                    throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                try {
                    _data.writeInterfaceToken(IRemoteAppTransitionListener.DESCRIPTOR);
                    _data.writeBoolean(wallpaerVisibility);
                    _data.writeBoolean(isHomeOrRecentsVisible);
                    this.mRemote.transact(4, _data, null, 1);
                } finally {
                    _data.recycle();
                }
            }
        }

        @Override // android.os.Binder
        public int getMaxTransactionId() {
            return 3;
        }
    }
}
