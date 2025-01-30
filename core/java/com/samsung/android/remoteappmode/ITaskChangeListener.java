package com.samsung.android.remoteappmode;

import android.p009os.Binder;
import android.p009os.IBinder;
import android.p009os.IInterface;
import android.p009os.Parcel;
import android.p009os.RemoteException;

/* loaded from: classes5.dex */
public interface ITaskChangeListener extends IInterface {
    public static final String DESCRIPTOR = "com.samsung.android.remoteappmode.ITaskChangeListener";

    void onRecentTaskListUpdated() throws RemoteException;

    void onTaskDisplayChanged(int i, int i2) throws RemoteException;

    void onTaskPlayed(int i, int i2) throws RemoteException;

    void onTaskRemoved(int i) throws RemoteException;

    void onTaskTriedToGoToBackground(int i, int i2) throws RemoteException;

    public static class Default implements ITaskChangeListener {
        @Override // com.samsung.android.remoteappmode.ITaskChangeListener
        public void onTaskRemoved(int taskId) throws RemoteException {
        }

        @Override // com.samsung.android.remoteappmode.ITaskChangeListener
        public void onTaskPlayed(int taskId, int displayId) throws RemoteException {
        }

        @Override // com.samsung.android.remoteappmode.ITaskChangeListener
        public void onTaskTriedToGoToBackground(int taskId, int displayId) throws RemoteException {
        }

        @Override // com.samsung.android.remoteappmode.ITaskChangeListener
        public void onTaskDisplayChanged(int taskId, int newDisplayId) throws RemoteException {
        }

        @Override // com.samsung.android.remoteappmode.ITaskChangeListener
        public void onRecentTaskListUpdated() throws RemoteException {
        }

        @Override // android.p009os.IInterface
        public IBinder asBinder() {
            return null;
        }
    }

    public static abstract class Stub extends Binder implements ITaskChangeListener {
        static final int TRANSACTION_onRecentTaskListUpdated = 5;
        static final int TRANSACTION_onTaskDisplayChanged = 4;
        static final int TRANSACTION_onTaskPlayed = 2;
        static final int TRANSACTION_onTaskRemoved = 1;
        static final int TRANSACTION_onTaskTriedToGoToBackground = 3;

        public Stub() {
            attachInterface(this, ITaskChangeListener.DESCRIPTOR);
        }

        public static ITaskChangeListener asInterface(IBinder obj) {
            if (obj == null) {
                return null;
            }
            IInterface iin = obj.queryLocalInterface(ITaskChangeListener.DESCRIPTOR);
            if (iin != null && (iin instanceof ITaskChangeListener)) {
                return (ITaskChangeListener) iin;
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
                    return "onTaskRemoved";
                case 2:
                    return "onTaskPlayed";
                case 3:
                    return "onTaskTriedToGoToBackground";
                case 4:
                    return "onTaskDisplayChanged";
                case 5:
                    return "onRecentTaskListUpdated";
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
                data.enforceInterface(ITaskChangeListener.DESCRIPTOR);
            }
            switch (code) {
                case IBinder.INTERFACE_TRANSACTION /* 1598968902 */:
                    reply.writeString(ITaskChangeListener.DESCRIPTOR);
                    return true;
                default:
                    switch (code) {
                        case 1:
                            int _arg0 = data.readInt();
                            data.enforceNoDataAvail();
                            onTaskRemoved(_arg0);
                            return true;
                        case 2:
                            int _arg02 = data.readInt();
                            int _arg1 = data.readInt();
                            data.enforceNoDataAvail();
                            onTaskPlayed(_arg02, _arg1);
                            return true;
                        case 3:
                            int _arg03 = data.readInt();
                            int _arg12 = data.readInt();
                            data.enforceNoDataAvail();
                            onTaskTriedToGoToBackground(_arg03, _arg12);
                            return true;
                        case 4:
                            int _arg04 = data.readInt();
                            int _arg13 = data.readInt();
                            data.enforceNoDataAvail();
                            onTaskDisplayChanged(_arg04, _arg13);
                            return true;
                        case 5:
                            onRecentTaskListUpdated();
                            return true;
                        default:
                            return super.onTransact(code, data, reply, flags);
                    }
            }
        }

        private static class Proxy implements ITaskChangeListener {
            private IBinder mRemote;

            Proxy(IBinder remote) {
                this.mRemote = remote;
            }

            @Override // android.p009os.IInterface
            public IBinder asBinder() {
                return this.mRemote;
            }

            public String getInterfaceDescriptor() {
                return ITaskChangeListener.DESCRIPTOR;
            }

            @Override // com.samsung.android.remoteappmode.ITaskChangeListener
            public void onTaskRemoved(int taskId) throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                try {
                    _data.writeInterfaceToken(ITaskChangeListener.DESCRIPTOR);
                    _data.writeInt(taskId);
                    this.mRemote.transact(1, _data, null, 1);
                } finally {
                    _data.recycle();
                }
            }

            @Override // com.samsung.android.remoteappmode.ITaskChangeListener
            public void onTaskPlayed(int taskId, int displayId) throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                try {
                    _data.writeInterfaceToken(ITaskChangeListener.DESCRIPTOR);
                    _data.writeInt(taskId);
                    _data.writeInt(displayId);
                    this.mRemote.transact(2, _data, null, 1);
                } finally {
                    _data.recycle();
                }
            }

            @Override // com.samsung.android.remoteappmode.ITaskChangeListener
            public void onTaskTriedToGoToBackground(int taskId, int displayId) throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                try {
                    _data.writeInterfaceToken(ITaskChangeListener.DESCRIPTOR);
                    _data.writeInt(taskId);
                    _data.writeInt(displayId);
                    this.mRemote.transact(3, _data, null, 1);
                } finally {
                    _data.recycle();
                }
            }

            @Override // com.samsung.android.remoteappmode.ITaskChangeListener
            public void onTaskDisplayChanged(int taskId, int newDisplayId) throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                try {
                    _data.writeInterfaceToken(ITaskChangeListener.DESCRIPTOR);
                    _data.writeInt(taskId);
                    _data.writeInt(newDisplayId);
                    this.mRemote.transact(4, _data, null, 1);
                } finally {
                    _data.recycle();
                }
            }

            @Override // com.samsung.android.remoteappmode.ITaskChangeListener
            public void onRecentTaskListUpdated() throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                try {
                    _data.writeInterfaceToken(ITaskChangeListener.DESCRIPTOR);
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
