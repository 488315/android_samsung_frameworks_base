package android.os;

/* loaded from: classes3.dex */
public interface IExternalVibratorService extends IInterface {
    public static final String DESCRIPTOR = "android.os.IExternalVibratorService";

    public static class Default implements IExternalVibratorService {
        @Override // android.os.IInterface
        public IBinder asBinder() {
            return null;
        }

        @Override // android.os.IExternalVibratorService
        public ExternalVibrationScale onExternalVibrationStart(ExternalVibration externalVibration) throws RemoteException {
            return null;
        }

        @Override // android.os.IExternalVibratorService
        public void onExternalVibrationStop(ExternalVibration externalVibration) throws RemoteException {
        }

        @Override // android.os.IExternalVibratorService
        public boolean shouldIgnoreExternalVibrationLocked(int i, int i2, int i3, int i4) throws RemoteException {
            return false;
        }
    }

    ExternalVibrationScale onExternalVibrationStart(ExternalVibration externalVibration) throws RemoteException;

    void onExternalVibrationStop(ExternalVibration externalVibration) throws RemoteException;

    boolean shouldIgnoreExternalVibrationLocked(int i, int i2, int i3, int i4) throws RemoteException;

    public static abstract class Stub extends Binder implements IExternalVibratorService {
        static final int TRANSACTION_onExternalVibrationStart = 1;
        static final int TRANSACTION_onExternalVibrationStop = 2;
        static final int TRANSACTION_shouldIgnoreExternalVibrationLocked = 3;

        @Override // android.os.IInterface
        public IBinder asBinder() {
            return this;
        }

        @Override // android.os.Binder
        public int getMaxTransactionId() {
            return 2;
        }

        public Stub() {
            attachInterface(this, IExternalVibratorService.DESCRIPTOR);
        }

        public static IExternalVibratorService asInterface(IBinder iBinder) {
            if (iBinder == null) {
                return null;
            }
            IInterface iInterfaceQueryLocalInterface = iBinder.queryLocalInterface(IExternalVibratorService.DESCRIPTOR);
            if (iInterfaceQueryLocalInterface != null && (iInterfaceQueryLocalInterface instanceof IExternalVibratorService)) {
                return (IExternalVibratorService) iInterfaceQueryLocalInterface;
            }
            return new Proxy(iBinder);
        }

        public static String getDefaultTransactionName(int i) {
            if (i == 1) {
                return "onExternalVibrationStart";
            }
            if (i == 2) {
                return "onExternalVibrationStop";
            }
            if (i != 3) {
                return null;
            }
            return "shouldIgnoreExternalVibrationLocked";
        }

        @Override // android.os.Binder
        public String getTransactionName(int i) {
            return getDefaultTransactionName(i);
        }

        @Override // android.os.Binder
        public boolean onTransact(int i, Parcel parcel, Parcel parcel2, int i2) throws RemoteException {
            if (i >= 1 && i <= 16777215) {
                parcel.enforceInterface(IExternalVibratorService.DESCRIPTOR);
            }
            if (i == 1598968902) {
                parcel2.writeString(IExternalVibratorService.DESCRIPTOR);
                return true;
            }
            if (i == 1) {
                ExternalVibration externalVibration = (ExternalVibration) parcel.readTypedObject(ExternalVibration.CREATOR);
                parcel.enforceNoDataAvail();
                ExternalVibrationScale externalVibrationScaleOnExternalVibrationStart = onExternalVibrationStart(externalVibration);
                parcel2.writeNoException();
                parcel2.writeTypedObject(externalVibrationScaleOnExternalVibrationStart, 1);
            } else if (i == 2) {
                ExternalVibration externalVibration2 = (ExternalVibration) parcel.readTypedObject(ExternalVibration.CREATOR);
                parcel.enforceNoDataAvail();
                onExternalVibrationStop(externalVibration2);
                parcel2.writeNoException();
            } else if (i == 3) {
                int i3 = parcel.readInt();
                int i4 = parcel.readInt();
                int i5 = parcel.readInt();
                int i6 = parcel.readInt();
                parcel.enforceNoDataAvail();
                boolean zShouldIgnoreExternalVibrationLocked = shouldIgnoreExternalVibrationLocked(i3, i4, i5, i6);
                parcel2.writeNoException();
                parcel2.writeBoolean(zShouldIgnoreExternalVibrationLocked);
            } else {
                return super.onTransact(i, parcel, parcel2, i2);
            }
            return true;
        }

        private static class Proxy implements IExternalVibratorService {
            private IBinder mRemote;

            Proxy(IBinder iBinder) {
                this.mRemote = iBinder;
            }

            @Override // android.os.IInterface
            public IBinder asBinder() {
                return this.mRemote;
            }

            public String getInterfaceDescriptor() {
                return IExternalVibratorService.DESCRIPTOR;
            }

            @Override // android.os.IExternalVibratorService
            public ExternalVibrationScale onExternalVibrationStart(ExternalVibration externalVibration) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(IExternalVibratorService.DESCRIPTOR);
                    parcelObtain.writeTypedObject(externalVibration, 0);
                    this.mRemote.transact(1, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return (ExternalVibrationScale) parcelObtain2.readTypedObject(ExternalVibrationScale.CREATOR);
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.os.IExternalVibratorService
            public void onExternalVibrationStop(ExternalVibration externalVibration) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(IExternalVibratorService.DESCRIPTOR);
                    parcelObtain.writeTypedObject(externalVibration, 0);
                    this.mRemote.transact(2, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.os.IExternalVibratorService
            public boolean shouldIgnoreExternalVibrationLocked(int i, int i2, int i3, int i4) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(IExternalVibratorService.DESCRIPTOR);
                    parcelObtain.writeInt(i);
                    parcelObtain.writeInt(i2);
                    parcelObtain.writeInt(i3);
                    parcelObtain.writeInt(i4);
                    this.mRemote.transact(3, parcelObtain, parcelObtain2, 0);
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
