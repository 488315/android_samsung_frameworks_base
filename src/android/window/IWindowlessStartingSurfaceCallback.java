package android.window;

import android.os.Binder;
import android.os.IBinder;
import android.os.IInterface;
import android.os.Parcel;
import android.os.RemoteException;
import android.view.SurfaceControl;

/* loaded from: classes5.dex */
public interface IWindowlessStartingSurfaceCallback extends IInterface {
    public static final String DESCRIPTOR = "android.window.IWindowlessStartingSurfaceCallback";

    public static class Default implements IWindowlessStartingSurfaceCallback {
        @Override // android.os.IInterface
        public IBinder asBinder() {
            return null;
        }

        @Override // android.window.IWindowlessStartingSurfaceCallback
        public void onSurfaceAdded(SurfaceControl surfaceControl) throws RemoteException {
        }
    }

    void onSurfaceAdded(SurfaceControl surfaceControl) throws RemoteException;

    public static abstract class Stub extends Binder implements IWindowlessStartingSurfaceCallback {
        static final int TRANSACTION_onSurfaceAdded = 1;

        @Override // android.os.IInterface
        public IBinder asBinder() {
            return this;
        }

        @Override // android.os.Binder
        public int getMaxTransactionId() {
            return 0;
        }

        public Stub() {
            attachInterface(this, IWindowlessStartingSurfaceCallback.DESCRIPTOR);
        }

        public static IWindowlessStartingSurfaceCallback asInterface(IBinder iBinder) {
            if (iBinder == null) {
                return null;
            }
            IInterface iInterfaceQueryLocalInterface = iBinder.queryLocalInterface(IWindowlessStartingSurfaceCallback.DESCRIPTOR);
            if (iInterfaceQueryLocalInterface != null && (iInterfaceQueryLocalInterface instanceof IWindowlessStartingSurfaceCallback)) {
                return (IWindowlessStartingSurfaceCallback) iInterfaceQueryLocalInterface;
            }
            return new Proxy(iBinder);
        }

        public static String getDefaultTransactionName(int i) {
            if (i != 1) {
                return null;
            }
            return "onSurfaceAdded";
        }

        @Override // android.os.Binder
        public String getTransactionName(int i) {
            return getDefaultTransactionName(i);
        }

        @Override // android.os.Binder
        public boolean onTransact(int i, Parcel parcel, Parcel parcel2, int i2) throws RemoteException {
            if (i >= 1 && i <= 16777215) {
                parcel.enforceInterface(IWindowlessStartingSurfaceCallback.DESCRIPTOR);
            }
            if (i == 1598968902) {
                parcel2.writeString(IWindowlessStartingSurfaceCallback.DESCRIPTOR);
                return true;
            }
            if (i == 1) {
                SurfaceControl surfaceControl = (SurfaceControl) parcel.readTypedObject(SurfaceControl.CREATOR);
                parcel.enforceNoDataAvail();
                onSurfaceAdded(surfaceControl);
                parcel2.writeNoException();
                return true;
            }
            return super.onTransact(i, parcel, parcel2, i2);
        }

        private static class Proxy implements IWindowlessStartingSurfaceCallback {
            private IBinder mRemote;

            Proxy(IBinder iBinder) {
                this.mRemote = iBinder;
            }

            @Override // android.os.IInterface
            public IBinder asBinder() {
                return this.mRemote;
            }

            public String getInterfaceDescriptor() {
                return IWindowlessStartingSurfaceCallback.DESCRIPTOR;
            }

            @Override // android.window.IWindowlessStartingSurfaceCallback
            public void onSurfaceAdded(SurfaceControl surfaceControl) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(IWindowlessStartingSurfaceCallback.DESCRIPTOR);
                    parcelObtain.writeTypedObject(surfaceControl, 0);
                    this.mRemote.transact(1, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }
        }
    }
}
