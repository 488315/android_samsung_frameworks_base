package android.view.accessibility;

import android.os.Binder;
import android.os.IBinder;
import android.os.IInterface;
import android.os.Parcel;
import android.os.RemoteException;
import java.util.ArrayList;
import java.util.List;

/* loaded from: classes4.dex */
public interface IAccessibilityInteractionConnectionCallback extends IInterface {

    public static class Default implements IAccessibilityInteractionConnectionCallback {
        @Override // android.os.IInterface
        public IBinder asBinder() {
            return null;
        }

        @Override // android.view.accessibility.IAccessibilityInteractionConnectionCallback
        public void sendAttachOverlayResult(int i, int i2) throws RemoteException {
        }

        @Override // android.view.accessibility.IAccessibilityInteractionConnectionCallback
        public void sendTakeScreenshotOfWindowError(int i, int i2) throws RemoteException {
        }

        @Override // android.view.accessibility.IAccessibilityInteractionConnectionCallback
        public void setFindAccessibilityNodeInfoResult(AccessibilityNodeInfo accessibilityNodeInfo, int i) throws RemoteException {
        }

        @Override // android.view.accessibility.IAccessibilityInteractionConnectionCallback
        public void setFindAccessibilityNodeInfosResult(List<AccessibilityNodeInfo> list, int i) throws RemoteException {
        }

        @Override // android.view.accessibility.IAccessibilityInteractionConnectionCallback
        public void setPerformAccessibilityActionResult(boolean z, int i) throws RemoteException {
        }

        @Override // android.view.accessibility.IAccessibilityInteractionConnectionCallback
        public void setPrefetchAccessibilityNodeInfoResult(List<AccessibilityNodeInfo> list, int i) throws RemoteException {
        }
    }

    void sendAttachOverlayResult(int i, int i2) throws RemoteException;

    void sendTakeScreenshotOfWindowError(int i, int i2) throws RemoteException;

    void setFindAccessibilityNodeInfoResult(AccessibilityNodeInfo accessibilityNodeInfo, int i) throws RemoteException;

    void setFindAccessibilityNodeInfosResult(List<AccessibilityNodeInfo> list, int i) throws RemoteException;

    void setPerformAccessibilityActionResult(boolean z, int i) throws RemoteException;

    void setPrefetchAccessibilityNodeInfoResult(List<AccessibilityNodeInfo> list, int i) throws RemoteException;

    public static abstract class Stub extends Binder implements IAccessibilityInteractionConnectionCallback {
        public static final String DESCRIPTOR = "android.view.accessibility.IAccessibilityInteractionConnectionCallback";
        static final int TRANSACTION_sendAttachOverlayResult = 6;
        static final int TRANSACTION_sendTakeScreenshotOfWindowError = 5;
        static final int TRANSACTION_setFindAccessibilityNodeInfoResult = 1;
        static final int TRANSACTION_setFindAccessibilityNodeInfosResult = 2;
        static final int TRANSACTION_setPerformAccessibilityActionResult = 4;
        static final int TRANSACTION_setPrefetchAccessibilityNodeInfoResult = 3;

        @Override // android.os.IInterface
        public IBinder asBinder() {
            return this;
        }

        @Override // android.os.Binder
        public int getMaxTransactionId() {
            return 5;
        }

        public Stub() {
            attachInterface(this, DESCRIPTOR);
        }

        public static IAccessibilityInteractionConnectionCallback asInterface(IBinder iBinder) {
            if (iBinder == null) {
                return null;
            }
            IInterface queryLocalInterface = iBinder.queryLocalInterface(DESCRIPTOR);
            if (queryLocalInterface != null && (queryLocalInterface instanceof IAccessibilityInteractionConnectionCallback)) {
                return (IAccessibilityInteractionConnectionCallback) queryLocalInterface;
            }
            return new Proxy(iBinder);
        }

        public static String getDefaultTransactionName(int i) {
            switch (i) {
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
        public String getTransactionName(int i) {
            return getDefaultTransactionName(i);
        }

        @Override // android.os.Binder
        public boolean onTransact(int i, Parcel parcel, Parcel parcel2, int i2) throws RemoteException {
            if (i >= 1 && i <= 16777215) {
                parcel.enforceInterface(DESCRIPTOR);
            }
            if (i == 1598968902) {
                parcel2.writeString(DESCRIPTOR);
                return true;
            }
            switch (i) {
                case 1:
                    AccessibilityNodeInfo accessibilityNodeInfo = (AccessibilityNodeInfo) parcel.readTypedObject(AccessibilityNodeInfo.CREATOR);
                    int readInt = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    setFindAccessibilityNodeInfoResult(accessibilityNodeInfo, readInt);
                    return true;
                case 2:
                    ArrayList createTypedArrayList = parcel.createTypedArrayList(AccessibilityNodeInfo.CREATOR);
                    int readInt2 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    setFindAccessibilityNodeInfosResult(createTypedArrayList, readInt2);
                    return true;
                case 3:
                    ArrayList createTypedArrayList2 = parcel.createTypedArrayList(AccessibilityNodeInfo.CREATOR);
                    int readInt3 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    setPrefetchAccessibilityNodeInfoResult(createTypedArrayList2, readInt3);
                    return true;
                case 4:
                    boolean readBoolean = parcel.readBoolean();
                    int readInt4 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    setPerformAccessibilityActionResult(readBoolean, readInt4);
                    return true;
                case 5:
                    int readInt5 = parcel.readInt();
                    int readInt6 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    sendTakeScreenshotOfWindowError(readInt5, readInt6);
                    return true;
                case 6:
                    int readInt7 = parcel.readInt();
                    int readInt8 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    sendAttachOverlayResult(readInt7, readInt8);
                    return true;
                default:
                    return super.onTransact(i, parcel, parcel2, i2);
            }
        }

        private static class Proxy implements IAccessibilityInteractionConnectionCallback {
            private IBinder mRemote;

            Proxy(IBinder iBinder) {
                this.mRemote = iBinder;
            }

            @Override // android.os.IInterface
            public IBinder asBinder() {
                return this.mRemote;
            }

            public String getInterfaceDescriptor() {
                return Stub.DESCRIPTOR;
            }

            @Override // android.view.accessibility.IAccessibilityInteractionConnectionCallback
            public void setFindAccessibilityNodeInfoResult(AccessibilityNodeInfo accessibilityNodeInfo, int i) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    obtain.writeTypedObject(accessibilityNodeInfo, 0);
                    obtain.writeInt(i);
                    this.mRemote.transact(1, obtain, null, 1);
                } finally {
                    obtain.recycle();
                }
            }

            @Override // android.view.accessibility.IAccessibilityInteractionConnectionCallback
            public void setFindAccessibilityNodeInfosResult(List<AccessibilityNodeInfo> list, int i) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    obtain.writeTypedList(list, 0);
                    obtain.writeInt(i);
                    this.mRemote.transact(2, obtain, null, 1);
                } finally {
                    obtain.recycle();
                }
            }

            @Override // android.view.accessibility.IAccessibilityInteractionConnectionCallback
            public void setPrefetchAccessibilityNodeInfoResult(List<AccessibilityNodeInfo> list, int i) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    obtain.writeTypedList(list, 0);
                    obtain.writeInt(i);
                    this.mRemote.transact(3, obtain, null, 1);
                } finally {
                    obtain.recycle();
                }
            }

            @Override // android.view.accessibility.IAccessibilityInteractionConnectionCallback
            public void setPerformAccessibilityActionResult(boolean z, int i) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    obtain.writeBoolean(z);
                    obtain.writeInt(i);
                    this.mRemote.transact(4, obtain, null, 1);
                } finally {
                    obtain.recycle();
                }
            }

            @Override // android.view.accessibility.IAccessibilityInteractionConnectionCallback
            public void sendTakeScreenshotOfWindowError(int i, int i2) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    obtain.writeInt(i);
                    obtain.writeInt(i2);
                    this.mRemote.transact(5, obtain, null, 1);
                } finally {
                    obtain.recycle();
                }
            }

            @Override // android.view.accessibility.IAccessibilityInteractionConnectionCallback
            public void sendAttachOverlayResult(int i, int i2) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    obtain.writeInt(i);
                    obtain.writeInt(i2);
                    this.mRemote.transact(6, obtain, null, 1);
                } finally {
                    obtain.recycle();
                }
            }
        }
    }
}
