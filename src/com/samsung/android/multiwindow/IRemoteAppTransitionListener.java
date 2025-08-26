package com.samsung.android.multiwindow;

import android.os.Binder;
import android.os.IBinder;
import android.os.IInterface;
import android.os.Parcel;
import android.os.RemoteException;

/* loaded from: classes6.dex */
public interface IRemoteAppTransitionListener extends IInterface {
    public static final String DESCRIPTOR = "com.samsung.android.multiwindow.IRemoteAppTransitionListener";

    public static class Default implements IRemoteAppTransitionListener {
        @Override // android.os.IInterface
        public IBinder asBinder() {
            return null;
        }

        @Override // com.samsung.android.multiwindow.IRemoteAppTransitionListener
        public void onFinishRecentsAnimation(boolean z) throws RemoteException {
        }

        @Override // com.samsung.android.multiwindow.IRemoteAppTransitionListener
        public void onStartHomeAnimation(boolean z) throws RemoteException {
        }

        @Override // com.samsung.android.multiwindow.IRemoteAppTransitionListener
        public void onStartRecentsAnimation(boolean z) throws RemoteException {
        }

        @Override // com.samsung.android.multiwindow.IRemoteAppTransitionListener
        public void onWallpaperVisibilityChanged(boolean z, boolean z2) throws RemoteException {
        }
    }

    void onFinishRecentsAnimation(boolean z) throws RemoteException;

    void onStartHomeAnimation(boolean z) throws RemoteException;

    void onStartRecentsAnimation(boolean z) throws RemoteException;

    void onWallpaperVisibilityChanged(boolean z, boolean z2) throws RemoteException;

    public static abstract class Stub extends Binder implements IRemoteAppTransitionListener {
        static final int TRANSACTION_onFinishRecentsAnimation = 2;
        static final int TRANSACTION_onStartHomeAnimation = 3;
        static final int TRANSACTION_onStartRecentsAnimation = 1;
        static final int TRANSACTION_onWallpaperVisibilityChanged = 4;

        @Override // android.os.IInterface
        public IBinder asBinder() {
            return this;
        }

        @Override // android.os.Binder
        public int getMaxTransactionId() {
            return 3;
        }

        public Stub() {
            attachInterface(this, IRemoteAppTransitionListener.DESCRIPTOR);
        }

        public static IRemoteAppTransitionListener asInterface(IBinder iBinder) {
            if (iBinder == null) {
                return null;
            }
            IInterface iInterfaceQueryLocalInterface = iBinder.queryLocalInterface(IRemoteAppTransitionListener.DESCRIPTOR);
            if (iInterfaceQueryLocalInterface != null && (iInterfaceQueryLocalInterface instanceof IRemoteAppTransitionListener)) {
                return (IRemoteAppTransitionListener) iInterfaceQueryLocalInterface;
            }
            return new Proxy(iBinder);
        }

        public static String getDefaultTransactionName(int i) {
            if (i == 1) {
                return "onStartRecentsAnimation";
            }
            if (i == 2) {
                return "onFinishRecentsAnimation";
            }
            if (i == 3) {
                return "onStartHomeAnimation";
            }
            if (i != 4) {
                return null;
            }
            return "onWallpaperVisibilityChanged";
        }

        @Override // android.os.Binder
        public String getTransactionName(int i) {
            return getDefaultTransactionName(i);
        }

        @Override // android.os.Binder
        public boolean onTransact(int i, Parcel parcel, Parcel parcel2, int i2) throws RemoteException {
            if (i >= 1 && i <= 16777215) {
                parcel.enforceInterface(IRemoteAppTransitionListener.DESCRIPTOR);
            }
            if (i == 1598968902) {
                parcel2.writeString(IRemoteAppTransitionListener.DESCRIPTOR);
                return true;
            }
            if (i == 1) {
                boolean z = parcel.readBoolean();
                parcel.enforceNoDataAvail();
                onStartRecentsAnimation(z);
            } else if (i == 2) {
                boolean z2 = parcel.readBoolean();
                parcel.enforceNoDataAvail();
                onFinishRecentsAnimation(z2);
            } else if (i == 3) {
                boolean z3 = parcel.readBoolean();
                parcel.enforceNoDataAvail();
                onStartHomeAnimation(z3);
            } else if (i == 4) {
                boolean z4 = parcel.readBoolean();
                boolean z5 = parcel.readBoolean();
                parcel.enforceNoDataAvail();
                onWallpaperVisibilityChanged(z4, z5);
            } else {
                return super.onTransact(i, parcel, parcel2, i2);
            }
            return true;
        }

        private static class Proxy implements IRemoteAppTransitionListener {
            private IBinder mRemote;

            Proxy(IBinder iBinder) {
                this.mRemote = iBinder;
            }

            @Override // android.os.IInterface
            public IBinder asBinder() {
                return this.mRemote;
            }

            public String getInterfaceDescriptor() {
                return IRemoteAppTransitionListener.DESCRIPTOR;
            }

            @Override // com.samsung.android.multiwindow.IRemoteAppTransitionListener
            public void onStartRecentsAnimation(boolean z) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                try {
                    parcelObtain.writeInterfaceToken(IRemoteAppTransitionListener.DESCRIPTOR);
                    parcelObtain.writeBoolean(z);
                    this.mRemote.transact(1, parcelObtain, null, 1);
                } finally {
                    parcelObtain.recycle();
                }
            }

            @Override // com.samsung.android.multiwindow.IRemoteAppTransitionListener
            public void onFinishRecentsAnimation(boolean z) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                try {
                    parcelObtain.writeInterfaceToken(IRemoteAppTransitionListener.DESCRIPTOR);
                    parcelObtain.writeBoolean(z);
                    this.mRemote.transact(2, parcelObtain, null, 1);
                } finally {
                    parcelObtain.recycle();
                }
            }

            @Override // com.samsung.android.multiwindow.IRemoteAppTransitionListener
            public void onStartHomeAnimation(boolean z) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                try {
                    parcelObtain.writeInterfaceToken(IRemoteAppTransitionListener.DESCRIPTOR);
                    parcelObtain.writeBoolean(z);
                    this.mRemote.transact(3, parcelObtain, null, 1);
                } finally {
                    parcelObtain.recycle();
                }
            }

            @Override // com.samsung.android.multiwindow.IRemoteAppTransitionListener
            public void onWallpaperVisibilityChanged(boolean z, boolean z2) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                try {
                    parcelObtain.writeInterfaceToken(IRemoteAppTransitionListener.DESCRIPTOR);
                    parcelObtain.writeBoolean(z);
                    parcelObtain.writeBoolean(z2);
                    this.mRemote.transact(4, parcelObtain, null, 1);
                } finally {
                    parcelObtain.recycle();
                }
            }
        }
    }
}
