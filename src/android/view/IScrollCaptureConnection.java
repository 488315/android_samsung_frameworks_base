package android.view;

import android.graphics.Rect;
import android.os.Binder;
import android.os.IBinder;
import android.os.ICancellationSignal;
import android.os.IInterface;
import android.os.Parcel;
import android.os.RemoteException;
import android.view.IScrollCaptureCallbacks;

/* loaded from: classes4.dex */
public interface IScrollCaptureConnection extends IInterface {
    public static final String DESCRIPTOR = "android.view.IScrollCaptureConnection";

    public static class Default implements IScrollCaptureConnection {
        @Override // android.os.IInterface
        public IBinder asBinder() {
            return null;
        }

        @Override // android.view.IScrollCaptureConnection
        public void close() throws RemoteException {
        }

        @Override // android.view.IScrollCaptureConnection
        public ICancellationSignal endCapture() throws RemoteException {
            return null;
        }

        @Override // android.view.IScrollCaptureConnection
        public ICancellationSignal requestImage(Rect rect) throws RemoteException {
            return null;
        }

        @Override // android.view.IScrollCaptureConnection
        public ICancellationSignal startCapture(Surface surface, IScrollCaptureCallbacks iScrollCaptureCallbacks) throws RemoteException {
            return null;
        }
    }

    void close() throws RemoteException;

    ICancellationSignal endCapture() throws RemoteException;

    ICancellationSignal requestImage(Rect rect) throws RemoteException;

    ICancellationSignal startCapture(Surface surface, IScrollCaptureCallbacks iScrollCaptureCallbacks) throws RemoteException;

    public static abstract class Stub extends Binder implements IScrollCaptureConnection {
        static final int TRANSACTION_close = 4;
        static final int TRANSACTION_endCapture = 3;
        static final int TRANSACTION_requestImage = 2;
        static final int TRANSACTION_startCapture = 1;

        @Override // android.os.IInterface
        public IBinder asBinder() {
            return this;
        }

        @Override // android.os.Binder
        public int getMaxTransactionId() {
            return 3;
        }

        public Stub() {
            attachInterface(this, IScrollCaptureConnection.DESCRIPTOR);
        }

        public static IScrollCaptureConnection asInterface(IBinder iBinder) {
            if (iBinder == null) {
                return null;
            }
            IInterface queryLocalInterface = iBinder.queryLocalInterface(IScrollCaptureConnection.DESCRIPTOR);
            if (queryLocalInterface != null && (queryLocalInterface instanceof IScrollCaptureConnection)) {
                return (IScrollCaptureConnection) queryLocalInterface;
            }
            return new Proxy(iBinder);
        }

        public static String getDefaultTransactionName(int i) {
            if (i == 1) {
                return "startCapture";
            }
            if (i == 2) {
                return "requestImage";
            }
            if (i == 3) {
                return "endCapture";
            }
            if (i != 4) {
                return null;
            }
            return "close";
        }

        @Override // android.os.Binder
        public String getTransactionName(int i) {
            return getDefaultTransactionName(i);
        }

        @Override // android.os.Binder
        public boolean onTransact(int i, Parcel parcel, Parcel parcel2, int i2) throws RemoteException {
            if (i >= 1 && i <= 16777215) {
                parcel.enforceInterface(IScrollCaptureConnection.DESCRIPTOR);
            }
            if (i == 1598968902) {
                parcel2.writeString(IScrollCaptureConnection.DESCRIPTOR);
                return true;
            }
            if (i == 1) {
                Surface surface = (Surface) parcel.readTypedObject(Surface.CREATOR);
                IScrollCaptureCallbacks asInterface = IScrollCaptureCallbacks.Stub.asInterface(parcel.readStrongBinder());
                parcel.enforceNoDataAvail();
                ICancellationSignal startCapture = startCapture(surface, asInterface);
                parcel2.writeNoException();
                parcel2.writeStrongInterface(startCapture);
            } else if (i == 2) {
                Rect rect = (Rect) parcel.readTypedObject(Rect.CREATOR);
                parcel.enforceNoDataAvail();
                ICancellationSignal requestImage = requestImage(rect);
                parcel2.writeNoException();
                parcel2.writeStrongInterface(requestImage);
            } else if (i == 3) {
                ICancellationSignal endCapture = endCapture();
                parcel2.writeNoException();
                parcel2.writeStrongInterface(endCapture);
            } else if (i == 4) {
                close();
            } else {
                return super.onTransact(i, parcel, parcel2, i2);
            }
            return true;
        }

        private static class Proxy implements IScrollCaptureConnection {
            private IBinder mRemote;

            Proxy(IBinder iBinder) {
                this.mRemote = iBinder;
            }

            @Override // android.os.IInterface
            public IBinder asBinder() {
                return this.mRemote;
            }

            public String getInterfaceDescriptor() {
                return IScrollCaptureConnection.DESCRIPTOR;
            }

            @Override // android.view.IScrollCaptureConnection
            public ICancellationSignal startCapture(Surface surface, IScrollCaptureCallbacks iScrollCaptureCallbacks) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(IScrollCaptureConnection.DESCRIPTOR);
                    obtain.writeTypedObject(surface, 0);
                    obtain.writeStrongInterface(iScrollCaptureCallbacks);
                    this.mRemote.transact(1, obtain, obtain2, 0);
                    obtain2.readException();
                    return ICancellationSignal.Stub.asInterface(obtain2.readStrongBinder());
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.view.IScrollCaptureConnection
            public ICancellationSignal requestImage(Rect rect) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(IScrollCaptureConnection.DESCRIPTOR);
                    obtain.writeTypedObject(rect, 0);
                    this.mRemote.transact(2, obtain, obtain2, 0);
                    obtain2.readException();
                    return ICancellationSignal.Stub.asInterface(obtain2.readStrongBinder());
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.view.IScrollCaptureConnection
            public ICancellationSignal endCapture() throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(IScrollCaptureConnection.DESCRIPTOR);
                    this.mRemote.transact(3, obtain, obtain2, 0);
                    obtain2.readException();
                    return ICancellationSignal.Stub.asInterface(obtain2.readStrongBinder());
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.view.IScrollCaptureConnection
            public void close() throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                try {
                    obtain.writeInterfaceToken(IScrollCaptureConnection.DESCRIPTOR);
                    this.mRemote.transact(4, obtain, null, 1);
                } finally {
                    obtain.recycle();
                }
            }
        }
    }
}
