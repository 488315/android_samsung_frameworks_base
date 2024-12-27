package android.view;

import android.os.Binder;
import android.os.IBinder;
import android.os.IInterface;
import android.os.Parcel;
import android.os.RemoteException;

/* loaded from: classes4.dex */
public interface IDecorViewGestureListener extends IInterface {
    public static final String DESCRIPTOR = "android.view.IDecorViewGestureListener";

    void onInterceptionChanged(IBinder iBinder, boolean z) throws RemoteException;

    public static class Default implements IDecorViewGestureListener {
        @Override // android.view.IDecorViewGestureListener
        public void onInterceptionChanged(IBinder windowToken, boolean intercepted) throws RemoteException {
        }

        @Override // android.os.IInterface
        public IBinder asBinder() {
            return null;
        }
    }

    public static abstract class Stub extends Binder implements IDecorViewGestureListener {
        static final int TRANSACTION_onInterceptionChanged = 1;

        public Stub() {
            attachInterface(this, IDecorViewGestureListener.DESCRIPTOR);
        }

        public static IDecorViewGestureListener asInterface(IBinder obj) {
            if (obj == null) {
                return null;
            }
            IInterface iin = obj.queryLocalInterface(IDecorViewGestureListener.DESCRIPTOR);
            if (iin != null && (iin instanceof IDecorViewGestureListener)) {
                return (IDecorViewGestureListener) iin;
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
                    return "onInterceptionChanged";
                default:
                    return null;
            }
        }

        @Override // android.os.Binder
        public String getTransactionName(int transactionCode) {
            return getDefaultTransactionName(transactionCode);
        }

        @Override // android.os.Binder
        public boolean onTransact(int code, Parcel data, Parcel reply, int flags) throws RemoteException {
            if (code >= 1 && code <= 16777215) {
                data.enforceInterface(IDecorViewGestureListener.DESCRIPTOR);
            }
            if (code == 1598968902) {
                reply.writeString(IDecorViewGestureListener.DESCRIPTOR);
                return true;
            }
            switch (code) {
                case 1:
                    IBinder _arg0 = data.readStrongBinder();
                    boolean _arg1 = data.readBoolean();
                    data.enforceNoDataAvail();
                    onInterceptionChanged(_arg0, _arg1);
                    return true;
                default:
                    return super.onTransact(code, data, reply, flags);
            }
        }

        private static class Proxy implements IDecorViewGestureListener {
            private IBinder mRemote;

            Proxy(IBinder remote) {
                this.mRemote = remote;
            }

            @Override // android.os.IInterface
            public IBinder asBinder() {
                return this.mRemote;
            }

            public String getInterfaceDescriptor() {
                return IDecorViewGestureListener.DESCRIPTOR;
            }

            @Override // android.view.IDecorViewGestureListener
            public void onInterceptionChanged(IBinder windowToken, boolean intercepted) throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                try {
                    _data.writeInterfaceToken(IDecorViewGestureListener.DESCRIPTOR);
                    _data.writeStrongBinder(windowToken);
                    _data.writeBoolean(intercepted);
                    this.mRemote.transact(1, _data, null, 1);
                } finally {
                    _data.recycle();
                }
            }
        }

        @Override // android.os.Binder
        public int getMaxTransactionId() {
            return 0;
        }
    }
}
