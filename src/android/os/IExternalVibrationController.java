package android.os;

import android.media.MediaMetrics;

/* loaded from: classes3.dex */
public interface IExternalVibrationController extends IInterface {
    public static final String DESCRIPTOR = "android.os.IExternalVibrationController";

    public static class Default implements IExternalVibrationController {
        @Override // android.os.IInterface
        public IBinder asBinder() {
            return null;
        }

        @Override // android.os.IExternalVibrationController
        public boolean mute() throws RemoteException {
            return false;
        }

        @Override // android.os.IExternalVibrationController
        public boolean unmute() throws RemoteException {
            return false;
        }
    }

    boolean mute() throws RemoteException;

    boolean unmute() throws RemoteException;

    public static abstract class Stub extends Binder implements IExternalVibrationController {
        static final int TRANSACTION_mute = 1;
        static final int TRANSACTION_unmute = 2;

        @Override // android.os.IInterface
        public IBinder asBinder() {
            return this;
        }

        @Override // android.os.Binder
        public int getMaxTransactionId() {
            return 1;
        }

        public Stub() {
            attachInterface(this, IExternalVibrationController.DESCRIPTOR);
        }

        public static IExternalVibrationController asInterface(IBinder iBinder) {
            if (iBinder == null) {
                return null;
            }
            IInterface queryLocalInterface = iBinder.queryLocalInterface(IExternalVibrationController.DESCRIPTOR);
            if (queryLocalInterface != null && (queryLocalInterface instanceof IExternalVibrationController)) {
                return (IExternalVibrationController) queryLocalInterface;
            }
            return new Proxy(iBinder);
        }

        public static String getDefaultTransactionName(int i) {
            if (i == 1) {
                return "mute";
            }
            if (i != 2) {
                return null;
            }
            return MediaMetrics.Value.UNMUTE;
        }

        @Override // android.os.Binder
        public String getTransactionName(int i) {
            return getDefaultTransactionName(i);
        }

        @Override // android.os.Binder
        public boolean onTransact(int i, Parcel parcel, Parcel parcel2, int i2) throws RemoteException {
            if (i >= 1 && i <= 16777215) {
                parcel.enforceInterface(IExternalVibrationController.DESCRIPTOR);
            }
            if (i == 1598968902) {
                parcel2.writeString(IExternalVibrationController.DESCRIPTOR);
                return true;
            }
            if (i == 1) {
                boolean mute = mute();
                parcel2.writeNoException();
                parcel2.writeBoolean(mute);
            } else if (i == 2) {
                boolean unmute = unmute();
                parcel2.writeNoException();
                parcel2.writeBoolean(unmute);
            } else {
                return super.onTransact(i, parcel, parcel2, i2);
            }
            return true;
        }

        private static class Proxy implements IExternalVibrationController {
            private IBinder mRemote;

            Proxy(IBinder iBinder) {
                this.mRemote = iBinder;
            }

            @Override // android.os.IInterface
            public IBinder asBinder() {
                return this.mRemote;
            }

            public String getInterfaceDescriptor() {
                return IExternalVibrationController.DESCRIPTOR;
            }

            @Override // android.os.IExternalVibrationController
            public boolean mute() throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(IExternalVibrationController.DESCRIPTOR);
                    this.mRemote.transact(1, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readBoolean();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.os.IExternalVibrationController
            public boolean unmute() throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(IExternalVibrationController.DESCRIPTOR);
                    this.mRemote.transact(2, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readBoolean();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }
        }
    }
}
