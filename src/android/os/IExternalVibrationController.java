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
            IInterface iInterfaceQueryLocalInterface = iBinder.queryLocalInterface(IExternalVibrationController.DESCRIPTOR);
            if (iInterfaceQueryLocalInterface != null && (iInterfaceQueryLocalInterface instanceof IExternalVibrationController)) {
                return (IExternalVibrationController) iInterfaceQueryLocalInterface;
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
                boolean zMute = mute();
                parcel2.writeNoException();
                parcel2.writeBoolean(zMute);
            } else if (i == 2) {
                boolean zUnmute = unmute();
                parcel2.writeNoException();
                parcel2.writeBoolean(zUnmute);
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
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(IExternalVibrationController.DESCRIPTOR);
                    this.mRemote.transact(1, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return parcelObtain2.readBoolean();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.os.IExternalVibrationController
            public boolean unmute() throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(IExternalVibrationController.DESCRIPTOR);
                    this.mRemote.transact(2, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return parcelObtain2.readBoolean();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }
        }
    }
}
