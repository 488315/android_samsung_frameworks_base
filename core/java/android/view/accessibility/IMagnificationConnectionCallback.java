package android.view.accessibility;

import android.graphics.Rect;
import android.os.Binder;
import android.os.IBinder;
import android.os.IInterface;
import android.os.Parcel;
import android.os.RemoteException;

public interface IMagnificationConnectionCallback extends IInterface {
    public static final String DESCRIPTOR =
            "android.view.accessibility.IMagnificationConnectionCallback";

    void onAccessibilityActionPerformed(int i) throws RemoteException;

    void onChangeMagnificationMode(int i, int i2) throws RemoteException;

    void onMove(int i) throws RemoteException;

    void onPerformScaleAction(int i, float f, boolean z) throws RemoteException;

    void onSourceBoundsChanged(int i, Rect rect) throws RemoteException;

    void onWindowMagnifierBoundsChanged(int i, Rect rect) throws RemoteException;

    public static class Default implements IMagnificationConnectionCallback {
        @Override // android.view.accessibility.IMagnificationConnectionCallback
        public void onWindowMagnifierBoundsChanged(int displayId, Rect bounds)
                throws RemoteException {}

        @Override // android.view.accessibility.IMagnificationConnectionCallback
        public void onChangeMagnificationMode(int displayId, int magnificationMode)
                throws RemoteException {}

        @Override // android.view.accessibility.IMagnificationConnectionCallback
        public void onSourceBoundsChanged(int displayId, Rect sourceBounds)
                throws RemoteException {}

        @Override // android.view.accessibility.IMagnificationConnectionCallback
        public void onPerformScaleAction(int displayId, float scale, boolean updatePersistence)
                throws RemoteException {}

        @Override // android.view.accessibility.IMagnificationConnectionCallback
        public void onAccessibilityActionPerformed(int displayId) throws RemoteException {}

        @Override // android.view.accessibility.IMagnificationConnectionCallback
        public void onMove(int displayId) throws RemoteException {}

        @Override // android.os.IInterface
        public IBinder asBinder() {
            return null;
        }
    }

    public abstract static class Stub extends Binder implements IMagnificationConnectionCallback {
        static final int TRANSACTION_onAccessibilityActionPerformed = 5;
        static final int TRANSACTION_onChangeMagnificationMode = 2;
        static final int TRANSACTION_onMove = 6;
        static final int TRANSACTION_onPerformScaleAction = 4;
        static final int TRANSACTION_onSourceBoundsChanged = 3;
        static final int TRANSACTION_onWindowMagnifierBoundsChanged = 1;

        public Stub() {
            attachInterface(this, IMagnificationConnectionCallback.DESCRIPTOR);
        }

        public static IMagnificationConnectionCallback asInterface(IBinder obj) {
            if (obj == null) {
                return null;
            }
            IInterface iin = obj.queryLocalInterface(IMagnificationConnectionCallback.DESCRIPTOR);
            if (iin != null && (iin instanceof IMagnificationConnectionCallback)) {
                return (IMagnificationConnectionCallback) iin;
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
                    return "onWindowMagnifierBoundsChanged";
                case 2:
                    return "onChangeMagnificationMode";
                case 3:
                    return "onSourceBoundsChanged";
                case 4:
                    return "onPerformScaleAction";
                case 5:
                    return "onAccessibilityActionPerformed";
                case 6:
                    return "onMove";
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
                data.enforceInterface(IMagnificationConnectionCallback.DESCRIPTOR);
            }
            if (code == 1598968902) {
                reply.writeString(IMagnificationConnectionCallback.DESCRIPTOR);
                return true;
            }
            switch (code) {
                case 1:
                    int _arg0 = data.readInt();
                    Rect _arg1 = (Rect) data.readTypedObject(Rect.CREATOR);
                    data.enforceNoDataAvail();
                    onWindowMagnifierBoundsChanged(_arg0, _arg1);
                    return true;
                case 2:
                    int _arg02 = data.readInt();
                    int _arg12 = data.readInt();
                    data.enforceNoDataAvail();
                    onChangeMagnificationMode(_arg02, _arg12);
                    return true;
                case 3:
                    int _arg03 = data.readInt();
                    Rect _arg13 = (Rect) data.readTypedObject(Rect.CREATOR);
                    data.enforceNoDataAvail();
                    onSourceBoundsChanged(_arg03, _arg13);
                    return true;
                case 4:
                    int _arg04 = data.readInt();
                    float _arg14 = data.readFloat();
                    boolean _arg2 = data.readBoolean();
                    data.enforceNoDataAvail();
                    onPerformScaleAction(_arg04, _arg14, _arg2);
                    return true;
                case 5:
                    int _arg05 = data.readInt();
                    data.enforceNoDataAvail();
                    onAccessibilityActionPerformed(_arg05);
                    return true;
                case 6:
                    int _arg06 = data.readInt();
                    data.enforceNoDataAvail();
                    onMove(_arg06);
                    return true;
                default:
                    return super.onTransact(code, data, reply, flags);
            }
        }

        private static class Proxy implements IMagnificationConnectionCallback {
            private IBinder mRemote;

            Proxy(IBinder remote) {
                this.mRemote = remote;
            }

            @Override // android.os.IInterface
            public IBinder asBinder() {
                return this.mRemote;
            }

            public String getInterfaceDescriptor() {
                return IMagnificationConnectionCallback.DESCRIPTOR;
            }

            @Override // android.view.accessibility.IMagnificationConnectionCallback
            public void onWindowMagnifierBoundsChanged(int displayId, Rect bounds)
                    throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                try {
                    _data.writeInterfaceToken(IMagnificationConnectionCallback.DESCRIPTOR);
                    _data.writeInt(displayId);
                    _data.writeTypedObject(bounds, 0);
                    this.mRemote.transact(1, _data, null, 1);
                } finally {
                    _data.recycle();
                }
            }

            @Override // android.view.accessibility.IMagnificationConnectionCallback
            public void onChangeMagnificationMode(int displayId, int magnificationMode)
                    throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                try {
                    _data.writeInterfaceToken(IMagnificationConnectionCallback.DESCRIPTOR);
                    _data.writeInt(displayId);
                    _data.writeInt(magnificationMode);
                    this.mRemote.transact(2, _data, null, 1);
                } finally {
                    _data.recycle();
                }
            }

            @Override // android.view.accessibility.IMagnificationConnectionCallback
            public void onSourceBoundsChanged(int displayId, Rect sourceBounds)
                    throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                try {
                    _data.writeInterfaceToken(IMagnificationConnectionCallback.DESCRIPTOR);
                    _data.writeInt(displayId);
                    _data.writeTypedObject(sourceBounds, 0);
                    this.mRemote.transact(3, _data, null, 1);
                } finally {
                    _data.recycle();
                }
            }

            @Override // android.view.accessibility.IMagnificationConnectionCallback
            public void onPerformScaleAction(int displayId, float scale, boolean updatePersistence)
                    throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                try {
                    _data.writeInterfaceToken(IMagnificationConnectionCallback.DESCRIPTOR);
                    _data.writeInt(displayId);
                    _data.writeFloat(scale);
                    _data.writeBoolean(updatePersistence);
                    this.mRemote.transact(4, _data, null, 1);
                } finally {
                    _data.recycle();
                }
            }

            @Override // android.view.accessibility.IMagnificationConnectionCallback
            public void onAccessibilityActionPerformed(int displayId) throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                try {
                    _data.writeInterfaceToken(IMagnificationConnectionCallback.DESCRIPTOR);
                    _data.writeInt(displayId);
                    this.mRemote.transact(5, _data, null, 1);
                } finally {
                    _data.recycle();
                }
            }

            @Override // android.view.accessibility.IMagnificationConnectionCallback
            public void onMove(int displayId) throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                try {
                    _data.writeInterfaceToken(IMagnificationConnectionCallback.DESCRIPTOR);
                    _data.writeInt(displayId);
                    this.mRemote.transact(6, _data, null, 1);
                } finally {
                    _data.recycle();
                }
            }
        }

        @Override // android.os.Binder
        public int getMaxTransactionId() {
            return 5;
        }
    }
}
