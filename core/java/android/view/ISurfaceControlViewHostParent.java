package android.view;

import android.os.Binder;
import android.os.IBinder;
import android.os.IInterface;
import android.os.Parcel;
import android.os.RemoteException;
import android.view.WindowManager;

/* loaded from: classes4.dex */
public interface ISurfaceControlViewHostParent extends IInterface {
    public static final String DESCRIPTOR = "android.view.ISurfaceControlViewHostParent";

    void forwardBackKeyToParent(KeyEvent keyEvent) throws RemoteException;

    void updateParams(WindowManager.LayoutParams[] layoutParamsArr) throws RemoteException;

    public static class Default implements ISurfaceControlViewHostParent {
        @Override // android.view.ISurfaceControlViewHostParent
        public void updateParams(WindowManager.LayoutParams[] childAttrs) throws RemoteException {
        }

        @Override // android.view.ISurfaceControlViewHostParent
        public void forwardBackKeyToParent(KeyEvent keyEvent) throws RemoteException {
        }

        @Override // android.os.IInterface
        public IBinder asBinder() {
            return null;
        }
    }

    public static abstract class Stub extends Binder implements ISurfaceControlViewHostParent {
        static final int TRANSACTION_forwardBackKeyToParent = 2;
        static final int TRANSACTION_updateParams = 1;

        public Stub() {
            attachInterface(this, ISurfaceControlViewHostParent.DESCRIPTOR);
        }

        public static ISurfaceControlViewHostParent asInterface(IBinder obj) {
            if (obj == null) {
                return null;
            }
            IInterface iin = obj.queryLocalInterface(ISurfaceControlViewHostParent.DESCRIPTOR);
            if (iin != null && (iin instanceof ISurfaceControlViewHostParent)) {
                return (ISurfaceControlViewHostParent) iin;
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
                    return "updateParams";
                case 2:
                    return "forwardBackKeyToParent";
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
                data.enforceInterface(ISurfaceControlViewHostParent.DESCRIPTOR);
            }
            if (code == 1598968902) {
                reply.writeString(ISurfaceControlViewHostParent.DESCRIPTOR);
                return true;
            }
            switch (code) {
                case 1:
                    WindowManager.LayoutParams[] _arg0 = (WindowManager.LayoutParams[]) data.createTypedArray(WindowManager.LayoutParams.CREATOR);
                    data.enforceNoDataAvail();
                    updateParams(_arg0);
                    return true;
                case 2:
                    KeyEvent _arg02 = (KeyEvent) data.readTypedObject(KeyEvent.CREATOR);
                    data.enforceNoDataAvail();
                    forwardBackKeyToParent(_arg02);
                    return true;
                default:
                    return super.onTransact(code, data, reply, flags);
            }
        }

        private static class Proxy implements ISurfaceControlViewHostParent {
            private IBinder mRemote;

            Proxy(IBinder remote) {
                this.mRemote = remote;
            }

            @Override // android.os.IInterface
            public IBinder asBinder() {
                return this.mRemote;
            }

            public String getInterfaceDescriptor() {
                return ISurfaceControlViewHostParent.DESCRIPTOR;
            }

            @Override // android.view.ISurfaceControlViewHostParent
            public void updateParams(WindowManager.LayoutParams[] childAttrs) throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                try {
                    _data.writeInterfaceToken(ISurfaceControlViewHostParent.DESCRIPTOR);
                    _data.writeTypedArray(childAttrs, 0);
                    this.mRemote.transact(1, _data, null, 1);
                } finally {
                    _data.recycle();
                }
            }

            @Override // android.view.ISurfaceControlViewHostParent
            public void forwardBackKeyToParent(KeyEvent keyEvent) throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                try {
                    _data.writeInterfaceToken(ISurfaceControlViewHostParent.DESCRIPTOR);
                    _data.writeTypedObject(keyEvent, 0);
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
