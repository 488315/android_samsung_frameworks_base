package android.window;

import android.os.Binder;
import android.os.IBinder;
import android.os.IInterface;
import android.os.Parcel;
import android.os.RemoteException;
import android.view.SurfaceControl;

/* loaded from: classes5.dex */
public interface IDisplayAreaOrganizer extends IInterface {
    public static final String DESCRIPTOR = "android.window.IDisplayAreaOrganizer";

    public static class Default implements IDisplayAreaOrganizer {
        @Override // android.os.IInterface
        public IBinder asBinder() {
            return null;
        }

        @Override // android.window.IDisplayAreaOrganizer
        public void onDisplayAreaAppeared(DisplayAreaInfo displayAreaInfo, SurfaceControl surfaceControl) throws RemoteException {
        }

        @Override // android.window.IDisplayAreaOrganizer
        public void onDisplayAreaInfoChanged(DisplayAreaInfo displayAreaInfo) throws RemoteException {
        }

        @Override // android.window.IDisplayAreaOrganizer
        public void onDisplayAreaVanished(DisplayAreaInfo displayAreaInfo) throws RemoteException {
        }
    }

    void onDisplayAreaAppeared(DisplayAreaInfo displayAreaInfo, SurfaceControl surfaceControl) throws RemoteException;

    void onDisplayAreaInfoChanged(DisplayAreaInfo displayAreaInfo) throws RemoteException;

    void onDisplayAreaVanished(DisplayAreaInfo displayAreaInfo) throws RemoteException;

    public static abstract class Stub extends Binder implements IDisplayAreaOrganizer {
        static final int TRANSACTION_onDisplayAreaAppeared = 1;
        static final int TRANSACTION_onDisplayAreaInfoChanged = 3;
        static final int TRANSACTION_onDisplayAreaVanished = 2;

        @Override // android.os.IInterface
        public IBinder asBinder() {
            return this;
        }

        @Override // android.os.Binder
        public int getMaxTransactionId() {
            return 2;
        }

        public Stub() {
            attachInterface(this, IDisplayAreaOrganizer.DESCRIPTOR);
        }

        public static IDisplayAreaOrganizer asInterface(IBinder iBinder) {
            if (iBinder == null) {
                return null;
            }
            IInterface iInterfaceQueryLocalInterface = iBinder.queryLocalInterface(IDisplayAreaOrganizer.DESCRIPTOR);
            if (iInterfaceQueryLocalInterface != null && (iInterfaceQueryLocalInterface instanceof IDisplayAreaOrganizer)) {
                return (IDisplayAreaOrganizer) iInterfaceQueryLocalInterface;
            }
            return new Proxy(iBinder);
        }

        public static String getDefaultTransactionName(int i) {
            if (i == 1) {
                return "onDisplayAreaAppeared";
            }
            if (i == 2) {
                return "onDisplayAreaVanished";
            }
            if (i != 3) {
                return null;
            }
            return "onDisplayAreaInfoChanged";
        }

        @Override // android.os.Binder
        public String getTransactionName(int i) {
            return getDefaultTransactionName(i);
        }

        @Override // android.os.Binder
        public boolean onTransact(int i, Parcel parcel, Parcel parcel2, int i2) throws RemoteException {
            if (i >= 1 && i <= 16777215) {
                parcel.enforceInterface(IDisplayAreaOrganizer.DESCRIPTOR);
            }
            if (i == 1598968902) {
                parcel2.writeString(IDisplayAreaOrganizer.DESCRIPTOR);
                return true;
            }
            if (i == 1) {
                DisplayAreaInfo displayAreaInfo = (DisplayAreaInfo) parcel.readTypedObject(DisplayAreaInfo.CREATOR);
                SurfaceControl surfaceControl = (SurfaceControl) parcel.readTypedObject(SurfaceControl.CREATOR);
                parcel.enforceNoDataAvail();
                onDisplayAreaAppeared(displayAreaInfo, surfaceControl);
            } else if (i == 2) {
                DisplayAreaInfo displayAreaInfo2 = (DisplayAreaInfo) parcel.readTypedObject(DisplayAreaInfo.CREATOR);
                parcel.enforceNoDataAvail();
                onDisplayAreaVanished(displayAreaInfo2);
            } else if (i == 3) {
                DisplayAreaInfo displayAreaInfo3 = (DisplayAreaInfo) parcel.readTypedObject(DisplayAreaInfo.CREATOR);
                parcel.enforceNoDataAvail();
                onDisplayAreaInfoChanged(displayAreaInfo3);
            } else {
                return super.onTransact(i, parcel, parcel2, i2);
            }
            return true;
        }

        private static class Proxy implements IDisplayAreaOrganizer {
            private IBinder mRemote;

            Proxy(IBinder iBinder) {
                this.mRemote = iBinder;
            }

            @Override // android.os.IInterface
            public IBinder asBinder() {
                return this.mRemote;
            }

            public String getInterfaceDescriptor() {
                return IDisplayAreaOrganizer.DESCRIPTOR;
            }

            @Override // android.window.IDisplayAreaOrganizer
            public void onDisplayAreaAppeared(DisplayAreaInfo displayAreaInfo, SurfaceControl surfaceControl) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                try {
                    parcelObtain.writeInterfaceToken(IDisplayAreaOrganizer.DESCRIPTOR);
                    parcelObtain.writeTypedObject(displayAreaInfo, 0);
                    parcelObtain.writeTypedObject(surfaceControl, 0);
                    this.mRemote.transact(1, parcelObtain, null, 1);
                } finally {
                    parcelObtain.recycle();
                }
            }

            @Override // android.window.IDisplayAreaOrganizer
            public void onDisplayAreaVanished(DisplayAreaInfo displayAreaInfo) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                try {
                    parcelObtain.writeInterfaceToken(IDisplayAreaOrganizer.DESCRIPTOR);
                    parcelObtain.writeTypedObject(displayAreaInfo, 0);
                    this.mRemote.transact(2, parcelObtain, null, 1);
                } finally {
                    parcelObtain.recycle();
                }
            }

            @Override // android.window.IDisplayAreaOrganizer
            public void onDisplayAreaInfoChanged(DisplayAreaInfo displayAreaInfo) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                try {
                    parcelObtain.writeInterfaceToken(IDisplayAreaOrganizer.DESCRIPTOR);
                    parcelObtain.writeTypedObject(displayAreaInfo, 0);
                    this.mRemote.transact(3, parcelObtain, null, 1);
                } finally {
                    parcelObtain.recycle();
                }
            }
        }
    }
}
