package android.view.accessibility;

import android.os.Binder;
import android.os.IBinder;
import android.os.IInterface;
import android.os.Parcel;
import android.os.RemoteException;

import java.util.List;

public interface IAccessibilityInteractionConnectionCallback extends IInterface {
    void sendAttachOverlayResult(int i, int i2) throws RemoteException;

    void sendTakeScreenshotOfWindowError(int i, int i2) throws RemoteException;

    void setFindAccessibilityNodeInfoResult(AccessibilityNodeInfo accessibilityNodeInfo, int i)
            throws RemoteException;

    void setFindAccessibilityNodeInfosResult(List<AccessibilityNodeInfo> list, int i)
            throws RemoteException;

    void setPerformAccessibilityActionResult(boolean z, int i) throws RemoteException;

    void setPrefetchAccessibilityNodeInfoResult(List<AccessibilityNodeInfo> list, int i)
            throws RemoteException;

    public static class Default implements IAccessibilityInteractionConnectionCallback {
        @Override // android.view.accessibility.IAccessibilityInteractionConnectionCallback
        public void setFindAccessibilityNodeInfoResult(
                AccessibilityNodeInfo info, int interactionId) throws RemoteException {}

        @Override // android.view.accessibility.IAccessibilityInteractionConnectionCallback
        public void setFindAccessibilityNodeInfosResult(
                List<AccessibilityNodeInfo> infos, int interactionId) throws RemoteException {}

        @Override // android.view.accessibility.IAccessibilityInteractionConnectionCallback
        public void setPrefetchAccessibilityNodeInfoResult(
                List<AccessibilityNodeInfo> infos, int interactionId) throws RemoteException {}

        @Override // android.view.accessibility.IAccessibilityInteractionConnectionCallback
        public void setPerformAccessibilityActionResult(boolean succeeded, int interactionId)
                throws RemoteException {}

        @Override // android.view.accessibility.IAccessibilityInteractionConnectionCallback
        public void sendTakeScreenshotOfWindowError(int errorCode, int interactionId)
                throws RemoteException {}

        @Override // android.view.accessibility.IAccessibilityInteractionConnectionCallback
        public void sendAttachOverlayResult(int result, int interactionId) throws RemoteException {}

        @Override // android.os.IInterface
        public IBinder asBinder() {
            return null;
        }
    }

    public abstract static class Stub extends Binder
            implements IAccessibilityInteractionConnectionCallback {
        public static final String DESCRIPTOR =
                "android.view.accessibility.IAccessibilityInteractionConnectionCallback";
        static final int TRANSACTION_sendAttachOverlayResult = 6;
        static final int TRANSACTION_sendTakeScreenshotOfWindowError = 5;
        static final int TRANSACTION_setFindAccessibilityNodeInfoResult = 1;
        static final int TRANSACTION_setFindAccessibilityNodeInfosResult = 2;
        static final int TRANSACTION_setPerformAccessibilityActionResult = 4;
        static final int TRANSACTION_setPrefetchAccessibilityNodeInfoResult = 3;

        public Stub() {
            attachInterface(this, DESCRIPTOR);
        }

        public static IAccessibilityInteractionConnectionCallback asInterface(IBinder obj) {
            if (obj == null) {
                return null;
            }
            IInterface iin = obj.queryLocalInterface(DESCRIPTOR);
            if (iin != null && (iin instanceof IAccessibilityInteractionConnectionCallback)) {
                return (IAccessibilityInteractionConnectionCallback) iin;
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
                    return "setFindAccessibilityNodeInfoResult";
                case 2:
                    return "setFindAccessibilityNodeInfosResult";
                case 3:
                    return "setPrefetchAccessibilityNodeInfoResult";
                case 4:
                    return "setPerformAccessibilityActionResult";
                case 5:
                    return "sendTakeScreenshotOfWindowError";
                case 6:
                    return "sendAttachOverlayResult";
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
                data.enforceInterface(DESCRIPTOR);
            }
            if (code == 1598968902) {
                reply.writeString(DESCRIPTOR);
                return true;
            }
            switch (code) {
                case 1:
                    AccessibilityNodeInfo _arg0 =
                            (AccessibilityNodeInfo)
                                    data.readTypedObject(AccessibilityNodeInfo.CREATOR);
                    int _arg1 = data.readInt();
                    data.enforceNoDataAvail();
                    setFindAccessibilityNodeInfoResult(_arg0, _arg1);
                    return true;
                case 2:
                    List<AccessibilityNodeInfo> _arg02 =
                            data.createTypedArrayList(AccessibilityNodeInfo.CREATOR);
                    int _arg12 = data.readInt();
                    data.enforceNoDataAvail();
                    setFindAccessibilityNodeInfosResult(_arg02, _arg12);
                    return true;
                case 3:
                    List<AccessibilityNodeInfo> _arg03 =
                            data.createTypedArrayList(AccessibilityNodeInfo.CREATOR);
                    int _arg13 = data.readInt();
                    data.enforceNoDataAvail();
                    setPrefetchAccessibilityNodeInfoResult(_arg03, _arg13);
                    return true;
                case 4:
                    boolean _arg04 = data.readBoolean();
                    int _arg14 = data.readInt();
                    data.enforceNoDataAvail();
                    setPerformAccessibilityActionResult(_arg04, _arg14);
                    return true;
                case 5:
                    int _arg05 = data.readInt();
                    int _arg15 = data.readInt();
                    data.enforceNoDataAvail();
                    sendTakeScreenshotOfWindowError(_arg05, _arg15);
                    return true;
                case 6:
                    int _arg06 = data.readInt();
                    int _arg16 = data.readInt();
                    data.enforceNoDataAvail();
                    sendAttachOverlayResult(_arg06, _arg16);
                    return true;
                default:
                    return super.onTransact(code, data, reply, flags);
            }
        }

        private static class Proxy implements IAccessibilityInteractionConnectionCallback {
            private IBinder mRemote;

            Proxy(IBinder remote) {
                this.mRemote = remote;
            }

            @Override // android.os.IInterface
            public IBinder asBinder() {
                return this.mRemote;
            }

            public String getInterfaceDescriptor() {
                return Stub.DESCRIPTOR;
            }

            @Override // android.view.accessibility.IAccessibilityInteractionConnectionCallback
            public void setFindAccessibilityNodeInfoResult(
                    AccessibilityNodeInfo info, int interactionId) throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeTypedObject(info, 0);
                    _data.writeInt(interactionId);
                    this.mRemote.transact(1, _data, null, 1);
                } finally {
                    _data.recycle();
                }
            }

            @Override // android.view.accessibility.IAccessibilityInteractionConnectionCallback
            public void setFindAccessibilityNodeInfosResult(
                    List<AccessibilityNodeInfo> infos, int interactionId) throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeTypedList(infos, 0);
                    _data.writeInt(interactionId);
                    this.mRemote.transact(2, _data, null, 1);
                } finally {
                    _data.recycle();
                }
            }

            @Override // android.view.accessibility.IAccessibilityInteractionConnectionCallback
            public void setPrefetchAccessibilityNodeInfoResult(
                    List<AccessibilityNodeInfo> infos, int interactionId) throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeTypedList(infos, 0);
                    _data.writeInt(interactionId);
                    this.mRemote.transact(3, _data, null, 1);
                } finally {
                    _data.recycle();
                }
            }

            @Override // android.view.accessibility.IAccessibilityInteractionConnectionCallback
            public void setPerformAccessibilityActionResult(boolean succeeded, int interactionId)
                    throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeBoolean(succeeded);
                    _data.writeInt(interactionId);
                    this.mRemote.transact(4, _data, null, 1);
                } finally {
                    _data.recycle();
                }
            }

            @Override // android.view.accessibility.IAccessibilityInteractionConnectionCallback
            public void sendTakeScreenshotOfWindowError(int errorCode, int interactionId)
                    throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeInt(errorCode);
                    _data.writeInt(interactionId);
                    this.mRemote.transact(5, _data, null, 1);
                } finally {
                    _data.recycle();
                }
            }

            @Override // android.view.accessibility.IAccessibilityInteractionConnectionCallback
            public void sendAttachOverlayResult(int result, int interactionId)
                    throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeInt(result);
                    _data.writeInt(interactionId);
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
