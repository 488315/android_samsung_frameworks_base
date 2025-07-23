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
            IInterface queryLocalInterface = iBinder.queryLocalInterface(IFreeformCallback.DESCRIPTOR);
            if (queryLocalInterface != null && (queryLocalInterface instanceof IFreeformCallback)) {
                return (IFreeformCallback) queryLocalInterface;
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
                int readInt = parcel.readInt();
                int readInt2 = parcel.readInt();
                int readInt3 = parcel.readInt();
                int readInt4 = parcel.readInt();
                boolean readBoolean = parcel.readBoolean();
                parcel.enforceNoDataAvail();
                onMinimized(componentName, readInt, readInt2, readInt3, readInt4, readBoolean);
            } else if (i == 2) {
                int readInt5 = parcel.readInt();
                parcel.enforceNoDataAvail();
                onUnminimized(readInt5);
            } else if (i == 3) {
                int readInt6 = parcel.readInt();
                parcel.enforceNoDataAvail();
                onMinimizeAnimationEnd(readInt6);
            } else if (i == 4) {
                int readInt7 = parcel.readInt();
                Point point = (Point) parcel.readTypedObject(Point.CREATOR);
                parcel.enforceNoDataAvail();
                onTaskMoveStarted(readInt7, point);
            } else if (i == 5) {
                int readInt8 = parcel.readInt();
                IRemoteCallback asInterface = IRemoteCallback.Stub.asInterface(parcel.readStrongBinder());
                parcel.enforceNoDataAvail();
                onTaskMoveEnded(readInt8, asInterface);
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
                Parcel obtain = Parcel.obtain(asBinder());
                try {
                    obtain.writeInterfaceToken(IFreeformCallback.DESCRIPTOR);
                    obtain.writeTypedObject(componentName, 0);
                    obtain.writeInt(i);
                    obtain.writeInt(i2);
                    obtain.writeInt(i3);
                    obtain.writeInt(i4);
                    obtain.writeBoolean(z);
                    this.mRemote.transact(1, obtain, null, 1);
                } finally {
                    obtain.recycle();
                }
            }

            @Override // com.samsung.android.multiwindow.IFreeformCallback
            public void onUnminimized(int i) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                try {
                    obtain.writeInterfaceToken(IFreeformCallback.DESCRIPTOR);
                    obtain.writeInt(i);
                    this.mRemote.transact(2, obtain, null, 1);
                } finally {
                    obtain.recycle();
                }
            }

            @Override // com.samsung.android.multiwindow.IFreeformCallback
            public void onMinimizeAnimationEnd(int i) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                try {
                    obtain.writeInterfaceToken(IFreeformCallback.DESCRIPTOR);
                    obtain.writeInt(i);
                    this.mRemote.transact(3, obtain, null, 1);
                } finally {
                    obtain.recycle();
                }
            }

            @Override // com.samsung.android.multiwindow.IFreeformCallback
            public void onTaskMoveStarted(int i, Point point) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                try {
                    obtain.writeInterfaceToken(IFreeformCallback.DESCRIPTOR);
                    obtain.writeInt(i);
                    obtain.writeTypedObject(point, 0);
                    this.mRemote.transact(4, obtain, null, 1);
                } finally {
                    obtain.recycle();
                }
            }

            @Override // com.samsung.android.multiwindow.IFreeformCallback
            public void onTaskMoveEnded(int i, IRemoteCallback iRemoteCallback) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                try {
                    obtain.writeInterfaceToken(IFreeformCallback.DESCRIPTOR);
                    obtain.writeInt(i);
                    obtain.writeStrongInterface(iRemoteCallback);
                    this.mRemote.transact(5, obtain, null, 1);
                } finally {
                    obtain.recycle();
                }
            }
        }
    }
}
