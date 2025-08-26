package com.samsung.android.multiwindow;

import android.content.ComponentName;
import android.graphics.Point;
import android.os.Binder;
import android.os.IBinder;
import android.os.IInterface;
import android.os.IRemoteCallback;
import android.os.Parcel;
import android.os.RemoteException;

/* loaded from: classes6.dex */
public interface IFreeformCallback extends IInterface {
    public static final String DESCRIPTOR = "com.samsung.android.multiwindow.IFreeformCallback";

    public static class Default implements IFreeformCallback {
        @Override // android.os.IInterface
        public IBinder asBinder() {
            return null;
        }

        @Override // com.samsung.android.multiwindow.IFreeformCallback
        public void onMinimizeAnimationEnd(int i) throws RemoteException {
        }

        @Override // com.samsung.android.multiwindow.IFreeformCallback
        public void onMinimized(ComponentName componentName, int i, int i2, int i3, int i4, boolean z) throws RemoteException {
        }

        @Override // com.samsung.android.multiwindow.IFreeformCallback
        public void onTaskMoveEnded(int i, IRemoteCallback iRemoteCallback) throws RemoteException {
        }

        @Override // com.samsung.android.multiwindow.IFreeformCallback
        public void onTaskMoveStarted(int i, Point point) throws RemoteException {
        }

        @Override // com.samsung.android.multiwindow.IFreeformCallback
        public void onUnminimized(int i) throws RemoteException {
        }
    }

    void onMinimizeAnimationEnd(int i) throws RemoteException;

    void onMinimized(ComponentName componentName, int i, int i2, int i3, int i4, boolean z) throws RemoteException;

    void onTaskMoveEnded(int i, IRemoteCallback iRemoteCallback) throws RemoteException;

    void onTaskMoveStarted(int i, Point point) throws RemoteException;

    void onUnminimized(int i) throws RemoteException;

    public static abstract class Stub extends Binder implements IFreeformCallback {
        static final int TRANSACTION_onMinimizeAnimationEnd = 3;
        static final int TRANSACTION_onMinimized = 1;
        static final int TRANSACTION_onTaskMoveEnded = 5;
        static final int TRANSACTION_onTaskMoveStarted = 4;
        static final int TRANSACTION_onUnminimized = 2;

        @Override // android.os.IInterface
        public IBinder asBinder() {
            return this;
        }

        @Override // android.os.Binder
        public int getMaxTransactionId() {
            return 4;
        }

        public Stub() {
            attachInterface(this, IFreeformCallback.DESCRIPTOR);
        }

        public static IFreeformCallback asInterface(IBinder iBinder) {
            if (iBinder == null) {
                return null;
            }
            IInterface iInterfaceQueryLocalInterface = iBinder.queryLocalInterface(IFreeformCallback.DESCRIPTOR);
            if (iInterfaceQueryLocalInterface != null && (iInterfaceQueryLocalInterface instanceof IFreeformCallback)) {
                return (IFreeformCallback) iInterfaceQueryLocalInterface;
            }
            return new Proxy(iBinder);
        }

        public static String getDefaultTransactionName(int i) {
            if (i == 1) {
                return "onMinimized";
            }
            if (i == 2) {
                return "onUnminimized";
            }
            if (i == 3) {
                return "onMinimizeAnimationEnd";
            }
            if (i == 4) {
                return "onTaskMoveStarted";
            }
            if (i != 5) {
                return null;
            }
            return "onTaskMoveEnded";
        }

        @Override // android.os.Binder
        public String getTransactionName(int i) {
            return getDefaultTransactionName(i);
        }

        @Override // android.os.Binder
        public boolean onTransact(int i, Parcel parcel, Parcel parcel2, int i2) throws RemoteException {
            if (i >= 1 && i <= 16777215) {
                parcel.enforceInterface(IFreeformCallback.DESCRIPTOR);
            }
            if (i == 1598968902) {
                parcel2.writeString(IFreeformCallback.DESCRIPTOR);
                return true;
            }
            if (i == 1) {
                ComponentName componentName = (ComponentName) parcel.readTypedObject(ComponentName.CREATOR);
                int i3 = parcel.readInt();
                int i4 = parcel.readInt();
                int i5 = parcel.readInt();
                int i6 = parcel.readInt();
                boolean z = parcel.readBoolean();
                parcel.enforceNoDataAvail();
                onMinimized(componentName, i3, i4, i5, i6, z);
            } else if (i == 2) {
                int i7 = parcel.readInt();
                parcel.enforceNoDataAvail();
                onUnminimized(i7);
            } else if (i == 3) {
                int i8 = parcel.readInt();
                parcel.enforceNoDataAvail();
                onMinimizeAnimationEnd(i8);
            } else if (i == 4) {
                int i9 = parcel.readInt();
                Point point = (Point) parcel.readTypedObject(Point.CREATOR);
                parcel.enforceNoDataAvail();
                onTaskMoveStarted(i9, point);
            } else if (i == 5) {
                int i10 = parcel.readInt();
                IRemoteCallback iRemoteCallbackAsInterface = IRemoteCallback.Stub.asInterface(parcel.readStrongBinder());
                parcel.enforceNoDataAvail();
                onTaskMoveEnded(i10, iRemoteCallbackAsInterface);
            } else {
                return super.onTransact(i, parcel, parcel2, i2);
            }
            return true;
        }

        private static class Proxy implements IFreeformCallback {
            private IBinder mRemote;

            Proxy(IBinder iBinder) {
                this.mRemote = iBinder;
            }

            @Override // android.os.IInterface
            public IBinder asBinder() {
                return this.mRemote;
            }

            public String getInterfaceDescriptor() {
                return IFreeformCallback.DESCRIPTOR;
            }

            @Override // com.samsung.android.multiwindow.IFreeformCallback
            public void onMinimized(ComponentName componentName, int i, int i2, int i3, int i4, boolean z) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                try {
                    parcelObtain.writeInterfaceToken(IFreeformCallback.DESCRIPTOR);
                    parcelObtain.writeTypedObject(componentName, 0);
                    parcelObtain.writeInt(i);
                    parcelObtain.writeInt(i2);
                    parcelObtain.writeInt(i3);
                    parcelObtain.writeInt(i4);
                    parcelObtain.writeBoolean(z);
                    this.mRemote.transact(1, parcelObtain, null, 1);
                } finally {
                    parcelObtain.recycle();
                }
            }

            @Override // com.samsung.android.multiwindow.IFreeformCallback
            public void onUnminimized(int i) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                try {
                    parcelObtain.writeInterfaceToken(IFreeformCallback.DESCRIPTOR);
                    parcelObtain.writeInt(i);
                    this.mRemote.transact(2, parcelObtain, null, 1);
                } finally {
                    parcelObtain.recycle();
                }
            }

            @Override // com.samsung.android.multiwindow.IFreeformCallback
            public void onMinimizeAnimationEnd(int i) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                try {
                    parcelObtain.writeInterfaceToken(IFreeformCallback.DESCRIPTOR);
                    parcelObtain.writeInt(i);
                    this.mRemote.transact(3, parcelObtain, null, 1);
                } finally {
                    parcelObtain.recycle();
                }
            }

            @Override // com.samsung.android.multiwindow.IFreeformCallback
            public void onTaskMoveStarted(int i, Point point) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                try {
                    parcelObtain.writeInterfaceToken(IFreeformCallback.DESCRIPTOR);
                    parcelObtain.writeInt(i);
                    parcelObtain.writeTypedObject(point, 0);
                    this.mRemote.transact(4, parcelObtain, null, 1);
                } finally {
                    parcelObtain.recycle();
                }
            }

            @Override // com.samsung.android.multiwindow.IFreeformCallback
            public void onTaskMoveEnded(int i, IRemoteCallback iRemoteCallback) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                try {
                    parcelObtain.writeInterfaceToken(IFreeformCallback.DESCRIPTOR);
                    parcelObtain.writeInt(i);
                    parcelObtain.writeStrongInterface(iRemoteCallback);
                    this.mRemote.transact(5, parcelObtain, null, 1);
                } finally {
                    parcelObtain.recycle();
                }
            }
        }
    }
}
