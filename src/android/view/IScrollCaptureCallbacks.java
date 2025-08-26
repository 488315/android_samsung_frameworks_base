package android.view;

import android.graphics.Rect;
import android.os.Binder;
import android.os.IBinder;
import android.os.IInterface;
import android.os.Parcel;
import android.os.RemoteException;

/* loaded from: classes4.dex */
public interface IScrollCaptureCallbacks extends IInterface {
    public static final String DESCRIPTOR = "android.view.IScrollCaptureCallbacks";

    public static class Default implements IScrollCaptureCallbacks {
        @Override // android.os.IInterface
        public IBinder asBinder() {
            return null;
        }

        @Override // android.view.IScrollCaptureCallbacks
        public void onCaptureEnded() throws RemoteException {
        }

        @Override // android.view.IScrollCaptureCallbacks
        public void onCaptureStarted() throws RemoteException {
        }

        @Override // android.view.IScrollCaptureCallbacks
        public void onImageRequestCompleted(int i, Rect rect) throws RemoteException {
        }
    }

    void onCaptureEnded() throws RemoteException;

    void onCaptureStarted() throws RemoteException;

    void onImageRequestCompleted(int i, Rect rect) throws RemoteException;

    public static abstract class Stub extends Binder implements IScrollCaptureCallbacks {
        static final int TRANSACTION_onCaptureEnded = 3;
        static final int TRANSACTION_onCaptureStarted = 1;
        static final int TRANSACTION_onImageRequestCompleted = 2;

        @Override // android.os.IInterface
        public IBinder asBinder() {
            return this;
        }

        @Override // android.os.Binder
        public int getMaxTransactionId() {
            return 2;
        }

        public Stub() {
            attachInterface(this, IScrollCaptureCallbacks.DESCRIPTOR);
        }

        public static IScrollCaptureCallbacks asInterface(IBinder iBinder) {
            if (iBinder == null) {
                return null;
            }
            IInterface iInterfaceQueryLocalInterface = iBinder.queryLocalInterface(IScrollCaptureCallbacks.DESCRIPTOR);
            if (iInterfaceQueryLocalInterface != null && (iInterfaceQueryLocalInterface instanceof IScrollCaptureCallbacks)) {
                return (IScrollCaptureCallbacks) iInterfaceQueryLocalInterface;
            }
            return new Proxy(iBinder);
        }

        public static String getDefaultTransactionName(int i) {
            if (i == 1) {
                return "onCaptureStarted";
            }
            if (i == 2) {
                return "onImageRequestCompleted";
            }
            if (i != 3) {
                return null;
            }
            return "onCaptureEnded";
        }

        @Override // android.os.Binder
        public String getTransactionName(int i) {
            return getDefaultTransactionName(i);
        }

        @Override // android.os.Binder
        public boolean onTransact(int i, Parcel parcel, Parcel parcel2, int i2) throws RemoteException {
            if (i >= 1 && i <= 16777215) {
                parcel.enforceInterface(IScrollCaptureCallbacks.DESCRIPTOR);
            }
            if (i == 1598968902) {
                parcel2.writeString(IScrollCaptureCallbacks.DESCRIPTOR);
                return true;
            }
            if (i == 1) {
                onCaptureStarted();
            } else if (i == 2) {
                int i3 = parcel.readInt();
                Rect rect = (Rect) parcel.readTypedObject(Rect.CREATOR);
                parcel.enforceNoDataAvail();
                onImageRequestCompleted(i3, rect);
            } else if (i == 3) {
                onCaptureEnded();
            } else {
                return super.onTransact(i, parcel, parcel2, i2);
            }
            return true;
        }

        private static class Proxy implements IScrollCaptureCallbacks {
            private IBinder mRemote;

            Proxy(IBinder iBinder) {
                this.mRemote = iBinder;
            }

            @Override // android.os.IInterface
            public IBinder asBinder() {
                return this.mRemote;
            }

            public String getInterfaceDescriptor() {
                return IScrollCaptureCallbacks.DESCRIPTOR;
            }

            @Override // android.view.IScrollCaptureCallbacks
            public void onCaptureStarted() throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                try {
                    parcelObtain.writeInterfaceToken(IScrollCaptureCallbacks.DESCRIPTOR);
                    this.mRemote.transact(1, parcelObtain, null, 1);
                } finally {
                    parcelObtain.recycle();
                }
            }

            @Override // android.view.IScrollCaptureCallbacks
            public void onImageRequestCompleted(int i, Rect rect) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                try {
                    parcelObtain.writeInterfaceToken(IScrollCaptureCallbacks.DESCRIPTOR);
                    parcelObtain.writeInt(i);
                    parcelObtain.writeTypedObject(rect, 0);
                    this.mRemote.transact(2, parcelObtain, null, 1);
                } finally {
                    parcelObtain.recycle();
                }
            }

            @Override // android.view.IScrollCaptureCallbacks
            public void onCaptureEnded() throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                try {
                    parcelObtain.writeInterfaceToken(IScrollCaptureCallbacks.DESCRIPTOR);
                    this.mRemote.transact(3, parcelObtain, null, 1);
                } finally {
                    parcelObtain.recycle();
                }
            }
        }
    }
}
