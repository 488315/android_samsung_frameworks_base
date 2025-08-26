package android.companion.virtual.camera;

import android.os.Binder;
import android.os.IBinder;
import android.os.IInterface;
import android.os.Parcel;
import android.os.RemoteException;
import android.view.Surface;

/* loaded from: classes.dex */
public interface IVirtualCameraCallback extends IInterface {
    public static final String DESCRIPTOR = "android.companion.virtual.camera.IVirtualCameraCallback";

    public static class Default implements IVirtualCameraCallback {
        @Override // android.os.IInterface
        public IBinder asBinder() {
            return null;
        }

        @Override // android.companion.virtual.camera.IVirtualCameraCallback
        public void onProcessCaptureRequest(int i, long j) throws RemoteException {
        }

        @Override // android.companion.virtual.camera.IVirtualCameraCallback
        public void onStreamClosed(int i) throws RemoteException {
        }

        @Override // android.companion.virtual.camera.IVirtualCameraCallback
        public void onStreamConfigured(int i, Surface surface, int i2, int i3, int i4) throws RemoteException {
        }
    }

    void onProcessCaptureRequest(int i, long j) throws RemoteException;

    void onStreamClosed(int i) throws RemoteException;

    void onStreamConfigured(int i, Surface surface, int i2, int i3, int i4) throws RemoteException;

    public static abstract class Stub extends Binder implements IVirtualCameraCallback {
        static final int TRANSACTION_onProcessCaptureRequest = 2;
        static final int TRANSACTION_onStreamClosed = 3;
        static final int TRANSACTION_onStreamConfigured = 1;

        @Override // android.os.IInterface
        public IBinder asBinder() {
            return this;
        }

        @Override // android.os.Binder
        public int getMaxTransactionId() {
            return 2;
        }

        public Stub() {
            attachInterface(this, IVirtualCameraCallback.DESCRIPTOR);
        }

        public static IVirtualCameraCallback asInterface(IBinder iBinder) {
            if (iBinder == null) {
                return null;
            }
            IInterface iInterfaceQueryLocalInterface = iBinder.queryLocalInterface(IVirtualCameraCallback.DESCRIPTOR);
            if (iInterfaceQueryLocalInterface != null && (iInterfaceQueryLocalInterface instanceof IVirtualCameraCallback)) {
                return (IVirtualCameraCallback) iInterfaceQueryLocalInterface;
            }
            return new Proxy(iBinder);
        }

        public static String getDefaultTransactionName(int i) {
            if (i == 1) {
                return "onStreamConfigured";
            }
            if (i == 2) {
                return "onProcessCaptureRequest";
            }
            if (i != 3) {
                return null;
            }
            return "onStreamClosed";
        }

        @Override // android.os.Binder
        public String getTransactionName(int i) {
            return getDefaultTransactionName(i);
        }

        @Override // android.os.Binder
        public boolean onTransact(int i, Parcel parcel, Parcel parcel2, int i2) throws RemoteException {
            if (i >= 1 && i <= 16777215) {
                parcel.enforceInterface(IVirtualCameraCallback.DESCRIPTOR);
            }
            if (i == 1598968902) {
                parcel2.writeString(IVirtualCameraCallback.DESCRIPTOR);
                return true;
            }
            if (i == 1) {
                int i3 = parcel.readInt();
                Surface surface = (Surface) parcel.readTypedObject(Surface.CREATOR);
                int i4 = parcel.readInt();
                int i5 = parcel.readInt();
                int i6 = parcel.readInt();
                parcel.enforceNoDataAvail();
                onStreamConfigured(i3, surface, i4, i5, i6);
            } else if (i == 2) {
                int i7 = parcel.readInt();
                long j = parcel.readLong();
                parcel.enforceNoDataAvail();
                onProcessCaptureRequest(i7, j);
            } else if (i == 3) {
                int i8 = parcel.readInt();
                parcel.enforceNoDataAvail();
                onStreamClosed(i8);
            } else {
                return super.onTransact(i, parcel, parcel2, i2);
            }
            return true;
        }

        private static class Proxy implements IVirtualCameraCallback {
            private IBinder mRemote;

            Proxy(IBinder iBinder) {
                this.mRemote = iBinder;
            }

            @Override // android.os.IInterface
            public IBinder asBinder() {
                return this.mRemote;
            }

            public String getInterfaceDescriptor() {
                return IVirtualCameraCallback.DESCRIPTOR;
            }

            @Override // android.companion.virtual.camera.IVirtualCameraCallback
            public void onStreamConfigured(int i, Surface surface, int i2, int i3, int i4) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                try {
                    parcelObtain.writeInterfaceToken(IVirtualCameraCallback.DESCRIPTOR);
                    parcelObtain.writeInt(i);
                    parcelObtain.writeTypedObject(surface, 0);
                    parcelObtain.writeInt(i2);
                    parcelObtain.writeInt(i3);
                    parcelObtain.writeInt(i4);
                    this.mRemote.transact(1, parcelObtain, null, 1);
                } finally {
                    parcelObtain.recycle();
                }
            }

            @Override // android.companion.virtual.camera.IVirtualCameraCallback
            public void onProcessCaptureRequest(int i, long j) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                try {
                    parcelObtain.writeInterfaceToken(IVirtualCameraCallback.DESCRIPTOR);
                    parcelObtain.writeInt(i);
                    parcelObtain.writeLong(j);
                    this.mRemote.transact(2, parcelObtain, null, 1);
                } finally {
                    parcelObtain.recycle();
                }
            }

            @Override // android.companion.virtual.camera.IVirtualCameraCallback
            public void onStreamClosed(int i) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                try {
                    parcelObtain.writeInterfaceToken(IVirtualCameraCallback.DESCRIPTOR);
                    parcelObtain.writeInt(i);
                    this.mRemote.transact(3, parcelObtain, null, 1);
                } finally {
                    parcelObtain.recycle();
                }
            }
        }
    }
}
