package android.window;

import android.app.ActivityManager;
import android.os.Binder;
import android.os.IBinder;
import android.os.IInterface;
import android.os.Parcel;
import android.os.RemoteException;
import android.view.DragEvent;

public interface IGlobalDragListener extends IInterface {
    public static final String DESCRIPTOR = "android.window.IGlobalDragListener";

    void onCrossWindowDrop(ActivityManager.RunningTaskInfo runningTaskInfo) throws RemoteException;

    void onUnhandledDrop(DragEvent dragEvent, IUnhandledDragCallback iUnhandledDragCallback)
            throws RemoteException;

    public static class Default implements IGlobalDragListener {
        @Override // android.window.IGlobalDragListener
        public void onCrossWindowDrop(ActivityManager.RunningTaskInfo taskInfo)
                throws RemoteException {}

        @Override // android.window.IGlobalDragListener
        public void onUnhandledDrop(DragEvent event, IUnhandledDragCallback callback)
                throws RemoteException {}

        @Override // android.os.IInterface
        public IBinder asBinder() {
            return null;
        }
    }

    public abstract static class Stub extends Binder implements IGlobalDragListener {
        static final int TRANSACTION_onCrossWindowDrop = 1;
        static final int TRANSACTION_onUnhandledDrop = 2;

        public Stub() {
            attachInterface(this, IGlobalDragListener.DESCRIPTOR);
        }

        public static IGlobalDragListener asInterface(IBinder obj) {
            if (obj == null) {
                return null;
            }
            IInterface iin = obj.queryLocalInterface(IGlobalDragListener.DESCRIPTOR);
            if (iin != null && (iin instanceof IGlobalDragListener)) {
                return (IGlobalDragListener) iin;
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
                    return "onCrossWindowDrop";
                case 2:
                    return "onUnhandledDrop";
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
                data.enforceInterface(IGlobalDragListener.DESCRIPTOR);
            }
            if (code == 1598968902) {
                reply.writeString(IGlobalDragListener.DESCRIPTOR);
                return true;
            }
            switch (code) {
                case 1:
                    ActivityManager.RunningTaskInfo _arg0 =
                            (ActivityManager.RunningTaskInfo)
                                    data.readTypedObject(ActivityManager.RunningTaskInfo.CREATOR);
                    data.enforceNoDataAvail();
                    onCrossWindowDrop(_arg0);
                    return true;
                case 2:
                    DragEvent _arg02 = (DragEvent) data.readTypedObject(DragEvent.CREATOR);
                    IUnhandledDragCallback _arg1 =
                            IUnhandledDragCallback.Stub.asInterface(data.readStrongBinder());
                    data.enforceNoDataAvail();
                    onUnhandledDrop(_arg02, _arg1);
                    return true;
                default:
                    return super.onTransact(code, data, reply, flags);
            }
        }

        private static class Proxy implements IGlobalDragListener {
            private IBinder mRemote;

            Proxy(IBinder remote) {
                this.mRemote = remote;
            }

            @Override // android.os.IInterface
            public IBinder asBinder() {
                return this.mRemote;
            }

            public String getInterfaceDescriptor() {
                return IGlobalDragListener.DESCRIPTOR;
            }

            @Override // android.window.IGlobalDragListener
            public void onCrossWindowDrop(ActivityManager.RunningTaskInfo taskInfo)
                    throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                try {
                    _data.writeInterfaceToken(IGlobalDragListener.DESCRIPTOR);
                    _data.writeTypedObject(taskInfo, 0);
                    this.mRemote.transact(1, _data, null, 1);
                } finally {
                    _data.recycle();
                }
            }

            @Override // android.window.IGlobalDragListener
            public void onUnhandledDrop(DragEvent event, IUnhandledDragCallback callback)
                    throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                try {
                    _data.writeInterfaceToken(IGlobalDragListener.DESCRIPTOR);
                    _data.writeTypedObject(event, 0);
                    _data.writeStrongInterface(callback);
                    this.mRemote.transact(2, _data, null, 1);
                } finally {
                    _data.recycle();
                }
            }
        }

        @Override // android.os.Binder
        public int getMaxTransactionId() {
            return 1;
        }
    }
}
