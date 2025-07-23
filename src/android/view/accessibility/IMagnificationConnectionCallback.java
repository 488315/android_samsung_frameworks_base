package android.view.accessibility;

import android.graphics.Rect;
import android.os.Binder;
import android.os.IBinder;
import android.os.IInterface;
import android.os.Parcel;
import android.os.RemoteException;

/* loaded from: classes4.dex */
public interface IMagnificationConnectionCallback extends IInterface {
    public static final String DESCRIPTOR = "android.view.accessibility.IMagnificationConnectionCallback";

    public static class Default implements IMagnificationConnectionCallback {
        @Override // android.os.IInterface
        public IBinder asBinder() {
            return null;
        }

        @Override // android.view.accessibility.IMagnificationConnectionCallback
        public void onAccessibilityActionPerformed(int i) throws RemoteException {
        }

        @Override // android.view.accessibility.IMagnificationConnectionCallback
        public void onChangeMagnificationMode(int i, int i2) throws RemoteException {
        }

        @Override // android.view.accessibility.IMagnificationConnectionCallback
        public void onMove(int i) throws RemoteException {
        }

        @Override // android.view.accessibility.IMagnificationConnectionCallback
        public void onPerformScaleAction(int i, float f, boolean z) throws RemoteException {
        }

        @Override // android.view.accessibility.IMagnificationConnectionCallback
        public void onSourceBoundsChanged(int i, Rect rect) throws RemoteException {
        }

        @Override // android.view.accessibility.IMagnificationConnectionCallback
        public void onWindowMagnifierBoundsChanged(int i, Rect rect) throws RemoteException {
        }
    }

    void onAccessibilityActionPerformed(int i) throws RemoteException;

    void onChangeMagnificationMode(int i, int i2) throws RemoteException;

    void onMove(int i) throws RemoteException;

    void onPerformScaleAction(int i, float f, boolean z) throws RemoteException;

    void onSourceBoundsChanged(int i, Rect rect) throws RemoteException;

    void onWindowMagnifierBoundsChanged(int i, Rect rect) throws RemoteException;

    public static abstract class Stub extends Binder implements IMagnificationConnectionCallback {
        static final int TRANSACTION_onAccessibilityActionPerformed = 5;
        static final int TRANSACTION_onChangeMagnificationMode = 2;
        static final int TRANSACTION_onMove = 6;
        static final int TRANSACTION_onPerformScaleAction = 4;
        static final int TRANSACTION_onSourceBoundsChanged = 3;
        static final int TRANSACTION_onWindowMagnifierBoundsChanged = 1;

        @Override // android.os.IInterface
        public IBinder asBinder() {
            return this;
        }

        @Override // android.os.Binder
        public int getMaxTransactionId() {
            return 5;
        }

        public Stub() {
            attachInterface(this, IMagnificationConnectionCallback.DESCRIPTOR);
        }

        public static IMagnificationConnectionCallback asInterface(IBinder iBinder) {
            if (iBinder == null) {
                return null;
            }
            IInterface queryLocalInterface = iBinder.queryLocalInterface(IMagnificationConnectionCallback.DESCRIPTOR);
            if (queryLocalInterface != null && (queryLocalInterface instanceof IMagnificationConnectionCallback)) {
                return (IMagnificationConnectionCallback) queryLocalInterface;
            }
            return new Proxy(iBinder);
        }

        public static String getDefaultTransactionName(int i) {
            switch (i) {
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
        public String getTransactionName(int i) {
            return getDefaultTransactionName(i);
        }

        @Override // android.os.Binder
        public boolean onTransact(int i, Parcel parcel, Parcel parcel2, int i2) throws RemoteException {
            if (i >= 1 && i <= 16777215) {
                parcel.enforceInterface(IMagnificationConnectionCallback.DESCRIPTOR);
            }
            if (i == 1598968902) {
                parcel2.writeString(IMagnificationConnectionCallback.DESCRIPTOR);
                return true;
            }
            switch (i) {
                case 1:
                    int readInt = parcel.readInt();
                    Rect rect = (Rect) parcel.readTypedObject(Rect.CREATOR);
                    parcel.enforceNoDataAvail();
                    onWindowMagnifierBoundsChanged(readInt, rect);
                    return true;
                case 2:
                    int readInt2 = parcel.readInt();
                    int readInt3 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    onChangeMagnificationMode(readInt2, readInt3);
                    return true;
                case 3:
                    int readInt4 = parcel.readInt();
                    Rect rect2 = (Rect) parcel.readTypedObject(Rect.CREATOR);
                    parcel.enforceNoDataAvail();
                    onSourceBoundsChanged(readInt4, rect2);
                    return true;
                case 4:
                    int readInt5 = parcel.readInt();
                    float readFloat = parcel.readFloat();
                    boolean readBoolean = parcel.readBoolean();
                    parcel.enforceNoDataAvail();
                    onPerformScaleAction(readInt5, readFloat, readBoolean);
                    return true;
                case 5:
                    int readInt6 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    onAccessibilityActionPerformed(readInt6);
                    return true;
                case 6:
                    int readInt7 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    onMove(readInt7);
                    return true;
                default:
                    return super.onTransact(i, parcel, parcel2, i2);
            }
        }

        private static class Proxy implements IMagnificationConnectionCallback {
            private IBinder mRemote;

            Proxy(IBinder iBinder) {
                this.mRemote = iBinder;
            }

            @Override // android.os.IInterface
            public IBinder asBinder() {
                return this.mRemote;
            }

            public String getInterfaceDescriptor() {
                return IMagnificationConnectionCallback.DESCRIPTOR;
            }

            @Override // android.view.accessibility.IMagnificationConnectionCallback
            public void onWindowMagnifierBoundsChanged(int i, Rect rect) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                try {
                    obtain.writeInterfaceToken(IMagnificationConnectionCallback.DESCRIPTOR);
                    obtain.writeInt(i);
                    obtain.writeTypedObject(rect, 0);
                    this.mRemote.transact(1, obtain, null, 1);
                } finally {
                    obtain.recycle();
                }
            }

            @Override // android.view.accessibility.IMagnificationConnectionCallback
            public void onChangeMagnificationMode(int i, int i2) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                try {
                    obtain.writeInterfaceToken(IMagnificationConnectionCallback.DESCRIPTOR);
                    obtain.writeInt(i);
                    obtain.writeInt(i2);
                    this.mRemote.transact(2, obtain, null, 1);
                } finally {
                    obtain.recycle();
                }
            }

            @Override // android.view.accessibility.IMagnificationConnectionCallback
            public void onSourceBoundsChanged(int i, Rect rect) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                try {
                    obtain.writeInterfaceToken(IMagnificationConnectionCallback.DESCRIPTOR);
                    obtain.writeInt(i);
                    obtain.writeTypedObject(rect, 0);
                    this.mRemote.transact(3, obtain, null, 1);
                } finally {
                    obtain.recycle();
                }
            }

            @Override // android.view.accessibility.IMagnificationConnectionCallback
            public void onPerformScaleAction(int i, float f, boolean z) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                try {
                    obtain.writeInterfaceToken(IMagnificationConnectionCallback.DESCRIPTOR);
                    obtain.writeInt(i);
                    obtain.writeFloat(f);
                    obtain.writeBoolean(z);
                    this.mRemote.transact(4, obtain, null, 1);
                } finally {
                    obtain.recycle();
                }
            }

            @Override // android.view.accessibility.IMagnificationConnectionCallback
            public void onAccessibilityActionPerformed(int i) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                try {
                    obtain.writeInterfaceToken(IMagnificationConnectionCallback.DESCRIPTOR);
                    obtain.writeInt(i);
                    this.mRemote.transact(5, obtain, null, 1);
                } finally {
                    obtain.recycle();
                }
            }

            @Override // android.view.accessibility.IMagnificationConnectionCallback
            public void onMove(int i) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                try {
                    obtain.writeInterfaceToken(IMagnificationConnectionCallback.DESCRIPTOR);
                    obtain.writeInt(i);
                    this.mRemote.transact(6, obtain, null, 1);
                } finally {
                    obtain.recycle();
                }
            }
        }
    }
}
