package android.window;

import android.app.ActivityManager;
import android.os.Binder;
import android.os.IBinder;
import android.os.IInterface;
import android.os.Parcel;
import android.os.RemoteException;
import android.view.DragEvent;
import android.window.IUnhandledDragCallback;

/* loaded from: classes5.dex */
public interface IGlobalDragListener extends IInterface {
    public static final String DESCRIPTOR = "android.window.IGlobalDragListener";

    public static class Default implements IGlobalDragListener {
        @Override // android.os.IInterface
        public IBinder asBinder() {
            return null;
        }

        @Override // android.window.IGlobalDragListener
        public void onCrossWindowDrop(ActivityManager.RunningTaskInfo runningTaskInfo) throws RemoteException {
        }

        @Override // android.window.IGlobalDragListener
        public void onUnhandledDrop(DragEvent dragEvent, IUnhandledDragCallback iUnhandledDragCallback) throws RemoteException {
        }
    }

    void onCrossWindowDrop(ActivityManager.RunningTaskInfo runningTaskInfo) throws RemoteException;

    void onUnhandledDrop(DragEvent dragEvent, IUnhandledDragCallback iUnhandledDragCallback) throws RemoteException;

    public static abstract class Stub extends Binder implements IGlobalDragListener {
        static final int TRANSACTION_onCrossWindowDrop = 1;
        static final int TRANSACTION_onUnhandledDrop = 2;

        @Override // android.os.IInterface
        public IBinder asBinder() {
            return this;
        }

        @Override // android.os.Binder
        public int getMaxTransactionId() {
            return 1;
        }

        public Stub() {
            attachInterface(this, IGlobalDragListener.DESCRIPTOR);
        }

        public static IGlobalDragListener asInterface(IBinder iBinder) {
            if (iBinder == null) {
                return null;
            }
            IInterface queryLocalInterface = iBinder.queryLocalInterface(IGlobalDragListener.DESCRIPTOR);
            if (queryLocalInterface != null && (queryLocalInterface instanceof IGlobalDragListener)) {
                return (IGlobalDragListener) queryLocalInterface;
            }
            return new Proxy(iBinder);
        }

        public static String getDefaultTransactionName(int i) {
            if (i == 1) {
                return "onCrossWindowDrop";
            }
            if (i != 2) {
                return null;
            }
            return "onUnhandledDrop";
        }

        @Override // android.os.Binder
        public String getTransactionName(int i) {
            return getDefaultTransactionName(i);
        }

        @Override // android.os.Binder
        public boolean onTransact(int i, Parcel parcel, Parcel parcel2, int i2) throws RemoteException {
            if (i >= 1 && i <= 16777215) {
                parcel.enforceInterface(IGlobalDragListener.DESCRIPTOR);
            }
            if (i == 1598968902) {
                parcel2.writeString(IGlobalDragListener.DESCRIPTOR);
                return true;
            }
            if (i == 1) {
                ActivityManager.RunningTaskInfo runningTaskInfo = (ActivityManager.RunningTaskInfo) parcel.readTypedObject(ActivityManager.RunningTaskInfo.CREATOR);
                parcel.enforceNoDataAvail();
                onCrossWindowDrop(runningTaskInfo);
            } else if (i == 2) {
                DragEvent dragEvent = (DragEvent) parcel.readTypedObject(DragEvent.CREATOR);
                IUnhandledDragCallback asInterface = IUnhandledDragCallback.Stub.asInterface(parcel.readStrongBinder());
                parcel.enforceNoDataAvail();
                onUnhandledDrop(dragEvent, asInterface);
            } else {
                return super.onTransact(i, parcel, parcel2, i2);
            }
            return true;
        }

        private static class Proxy implements IGlobalDragListener {
            private IBinder mRemote;

            Proxy(IBinder iBinder) {
                this.mRemote = iBinder;
            }

            @Override // android.os.IInterface
            public IBinder asBinder() {
                return this.mRemote;
            }

            public String getInterfaceDescriptor() {
                return IGlobalDragListener.DESCRIPTOR;
            }

            @Override // android.window.IGlobalDragListener
            public void onCrossWindowDrop(ActivityManager.RunningTaskInfo runningTaskInfo) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                try {
                    obtain.writeInterfaceToken(IGlobalDragListener.DESCRIPTOR);
                    obtain.writeTypedObject(runningTaskInfo, 0);
                    this.mRemote.transact(1, obtain, null, 1);
                } finally {
                    obtain.recycle();
                }
            }

            @Override // android.window.IGlobalDragListener
            public void onUnhandledDrop(DragEvent dragEvent, IUnhandledDragCallback iUnhandledDragCallback) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                try {
                    obtain.writeInterfaceToken(IGlobalDragListener.DESCRIPTOR);
                    obtain.writeTypedObject(dragEvent, 0);
                    obtain.writeStrongInterface(iUnhandledDragCallback);
                    this.mRemote.transact(2, obtain, null, 1);
                } finally {
                    obtain.recycle();
                }
            }
        }
    }
}
