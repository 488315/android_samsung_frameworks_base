package com.samsung.android.multiwindow;

import android.content.ComponentName;
import android.graphics.Point;
import android.p009os.Binder;
import android.p009os.IBinder;
import android.p009os.IInterface;
import android.p009os.IRemoteCallback;
import android.p009os.Parcel;
import android.p009os.RemoteException;

/* loaded from: classes5.dex */
public interface IFreeformCallback extends IInterface {
    public static final String DESCRIPTOR = "com.samsung.android.multiwindow.IFreeformCallback";

    void onMinimizeAnimationEnd(int i) throws RemoteException;

    void onMinimized(ComponentName componentName, int i, int i2, int i3, int i4, boolean z) throws RemoteException;

    void onTaskMoveEnded(int i, IRemoteCallback iRemoteCallback) throws RemoteException;

    void onTaskMoveStarted(int i, Point point) throws RemoteException;

    void onUnminimized(int i) throws RemoteException;

    public static class Default implements IFreeformCallback {
        @Override // com.samsung.android.multiwindow.IFreeformCallback
        public void onMinimized(ComponentName realActivity, int taskId, int userId, int x, int y, boolean center) throws RemoteException {
        }

        @Override // com.samsung.android.multiwindow.IFreeformCallback
        public void onUnminimized(int taskId) throws RemoteException {
        }

        @Override // com.samsung.android.multiwindow.IFreeformCallback
        public void onMinimizeAnimationEnd(int taskId) throws RemoteException {
        }

        @Override // com.samsung.android.multiwindow.IFreeformCallback
        public void onTaskMoveStarted(int taskId, Point point) throws RemoteException {
        }

        @Override // com.samsung.android.multiwindow.IFreeformCallback
        public void onTaskMoveEnded(int taskId, IRemoteCallback callback) throws RemoteException {
        }

        @Override // android.p009os.IInterface
        public IBinder asBinder() {
            return null;
        }
    }

    public static abstract class Stub extends Binder implements IFreeformCallback {
        static final int TRANSACTION_onMinimizeAnimationEnd = 3;
        static final int TRANSACTION_onMinimized = 1;
        static final int TRANSACTION_onTaskMoveEnded = 5;
        static final int TRANSACTION_onTaskMoveStarted = 4;
        static final int TRANSACTION_onUnminimized = 2;

        public Stub() {
            attachInterface(this, IFreeformCallback.DESCRIPTOR);
        }

        public static IFreeformCallback asInterface(IBinder obj) {
            if (obj == null) {
                return null;
            }
            IInterface iin = obj.queryLocalInterface(IFreeformCallback.DESCRIPTOR);
            if (iin != null && (iin instanceof IFreeformCallback)) {
                return (IFreeformCallback) iin;
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
                    return "onMinimized";
                case 2:
                    return "onUnminimized";
                case 3:
                    return "onMinimizeAnimationEnd";
                case 4:
                    return "onTaskMoveStarted";
                case 5:
                    return "onTaskMoveEnded";
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
                data.enforceInterface(IFreeformCallback.DESCRIPTOR);
            }
            switch (code) {
                case IBinder.INTERFACE_TRANSACTION /* 1598968902 */:
                    reply.writeString(IFreeformCallback.DESCRIPTOR);
                    return true;
                default:
                    switch (code) {
                        case 1:
                            ComponentName _arg0 = (ComponentName) data.readTypedObject(ComponentName.CREATOR);
                            int _arg1 = data.readInt();
                            int _arg2 = data.readInt();
                            int _arg3 = data.readInt();
                            int _arg4 = data.readInt();
                            boolean _arg5 = data.readBoolean();
                            data.enforceNoDataAvail();
                            onMinimized(_arg0, _arg1, _arg2, _arg3, _arg4, _arg5);
                            return true;
                        case 2:
                            int _arg02 = data.readInt();
                            data.enforceNoDataAvail();
                            onUnminimized(_arg02);
                            return true;
                        case 3:
                            int _arg03 = data.readInt();
                            data.enforceNoDataAvail();
                            onMinimizeAnimationEnd(_arg03);
                            return true;
                        case 4:
                            int _arg04 = data.readInt();
                            Point _arg12 = (Point) data.readTypedObject(Point.CREATOR);
                            data.enforceNoDataAvail();
                            onTaskMoveStarted(_arg04, _arg12);
                            return true;
                        case 5:
                            int _arg05 = data.readInt();
                            IRemoteCallback _arg13 = IRemoteCallback.Stub.asInterface(data.readStrongBinder());
                            data.enforceNoDataAvail();
                            onTaskMoveEnded(_arg05, _arg13);
                            return true;
                        default:
                            return super.onTransact(code, data, reply, flags);
                    }
            }
        }

        private static class Proxy implements IFreeformCallback {
            private IBinder mRemote;

            Proxy(IBinder remote) {
                this.mRemote = remote;
            }

            @Override // android.p009os.IInterface
            public IBinder asBinder() {
                return this.mRemote;
            }

            public String getInterfaceDescriptor() {
                return IFreeformCallback.DESCRIPTOR;
            }

            @Override // com.samsung.android.multiwindow.IFreeformCallback
            public void onMinimized(ComponentName realActivity, int taskId, int userId, int x, int y, boolean center) throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                try {
                    _data.writeInterfaceToken(IFreeformCallback.DESCRIPTOR);
                    _data.writeTypedObject(realActivity, 0);
                    _data.writeInt(taskId);
                    _data.writeInt(userId);
                    _data.writeInt(x);
                    _data.writeInt(y);
                    _data.writeBoolean(center);
                    this.mRemote.transact(1, _data, null, 1);
                } finally {
                    _data.recycle();
                }
            }

            @Override // com.samsung.android.multiwindow.IFreeformCallback
            public void onUnminimized(int taskId) throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                try {
                    _data.writeInterfaceToken(IFreeformCallback.DESCRIPTOR);
                    _data.writeInt(taskId);
                    this.mRemote.transact(2, _data, null, 1);
                } finally {
                    _data.recycle();
                }
            }

            @Override // com.samsung.android.multiwindow.IFreeformCallback
            public void onMinimizeAnimationEnd(int taskId) throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                try {
                    _data.writeInterfaceToken(IFreeformCallback.DESCRIPTOR);
                    _data.writeInt(taskId);
                    this.mRemote.transact(3, _data, null, 1);
                } finally {
                    _data.recycle();
                }
            }

            @Override // com.samsung.android.multiwindow.IFreeformCallback
            public void onTaskMoveStarted(int taskId, Point point) throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                try {
                    _data.writeInterfaceToken(IFreeformCallback.DESCRIPTOR);
                    _data.writeInt(taskId);
                    _data.writeTypedObject(point, 0);
                    this.mRemote.transact(4, _data, null, 1);
                } finally {
                    _data.recycle();
                }
            }

            @Override // com.samsung.android.multiwindow.IFreeformCallback
            public void onTaskMoveEnded(int taskId, IRemoteCallback callback) throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                try {
                    _data.writeInterfaceToken(IFreeformCallback.DESCRIPTOR);
                    _data.writeInt(taskId);
                    _data.writeStrongInterface(callback);
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
