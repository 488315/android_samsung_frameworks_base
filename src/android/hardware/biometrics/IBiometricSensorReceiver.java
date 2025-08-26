package android.hardware.biometrics;

import android.os.Binder;
import android.os.Bundle;
import android.os.IBinder;
import android.os.IInterface;
import android.os.Parcel;
import android.os.RemoteException;

/* loaded from: classes2.dex */
public interface IBiometricSensorReceiver extends IInterface {
    public static final String DESCRIPTOR = "android.hardware.biometrics.IBiometricSensorReceiver";

    public static class Default implements IBiometricSensorReceiver {
        @Override // android.os.IInterface
        public IBinder asBinder() {
            return null;
        }

        @Override // android.hardware.biometrics.IBiometricSensorReceiver
        public void onAcquired(int i, int i2, int i3) throws RemoteException {
        }

        @Override // android.hardware.biometrics.IBiometricSensorReceiver
        public void onAuthenticationFailed(int i) throws RemoteException {
        }

        @Override // android.hardware.biometrics.IBiometricSensorReceiver
        public void onAuthenticationSucceeded(int i, byte[] bArr) throws RemoteException {
        }

        @Override // android.hardware.biometrics.IBiometricSensorReceiver
        public void onError(int i, int i2, int i3, int i4) throws RemoteException {
        }

        @Override // android.hardware.biometrics.IBiometricSensorReceiver
        public void onSemAuthenticationSucceeded(int i, byte[] bArr, Bundle bundle) throws RemoteException {
        }
    }

    void onAcquired(int i, int i2, int i3) throws RemoteException;

    void onAuthenticationFailed(int i) throws RemoteException;

    void onAuthenticationSucceeded(int i, byte[] bArr) throws RemoteException;

    void onError(int i, int i2, int i3, int i4) throws RemoteException;

    void onSemAuthenticationSucceeded(int i, byte[] bArr, Bundle bundle) throws RemoteException;

    public static abstract class Stub extends Binder implements IBiometricSensorReceiver {
        static final int TRANSACTION_onAcquired = 4;
        static final int TRANSACTION_onAuthenticationFailed = 2;
        static final int TRANSACTION_onAuthenticationSucceeded = 1;
        static final int TRANSACTION_onError = 3;
        static final int TRANSACTION_onSemAuthenticationSucceeded = 5;

        @Override // android.os.IInterface
        public IBinder asBinder() {
            return this;
        }

        @Override // android.os.Binder
        public int getMaxTransactionId() {
            return 4;
        }

        public Stub() {
            attachInterface(this, IBiometricSensorReceiver.DESCRIPTOR);
        }

        public static IBiometricSensorReceiver asInterface(IBinder iBinder) {
            if (iBinder == null) {
                return null;
            }
            IInterface iInterfaceQueryLocalInterface = iBinder.queryLocalInterface(IBiometricSensorReceiver.DESCRIPTOR);
            if (iInterfaceQueryLocalInterface != null && (iInterfaceQueryLocalInterface instanceof IBiometricSensorReceiver)) {
                return (IBiometricSensorReceiver) iInterfaceQueryLocalInterface;
            }
            return new Proxy(iBinder);
        }

        public static String getDefaultTransactionName(int i) {
            if (i == 1) {
                return "onAuthenticationSucceeded";
            }
            if (i == 2) {
                return "onAuthenticationFailed";
            }
            if (i == 3) {
                return "onError";
            }
            if (i == 4) {
                return "onAcquired";
            }
            if (i != 5) {
                return null;
            }
            return "onSemAuthenticationSucceeded";
        }

        @Override // android.os.Binder
        public String getTransactionName(int i) {
            return getDefaultTransactionName(i);
        }

        @Override // android.os.Binder
        public boolean onTransact(int i, Parcel parcel, Parcel parcel2, int i2) throws RemoteException {
            if (i >= 1 && i <= 16777215) {
                parcel.enforceInterface(IBiometricSensorReceiver.DESCRIPTOR);
            }
            if (i == 1598968902) {
                parcel2.writeString(IBiometricSensorReceiver.DESCRIPTOR);
                return true;
            }
            if (i == 1) {
                int i3 = parcel.readInt();
                byte[] bArrCreateByteArray = parcel.createByteArray();
                parcel.enforceNoDataAvail();
                onAuthenticationSucceeded(i3, bArrCreateByteArray);
            } else if (i == 2) {
                int i4 = parcel.readInt();
                parcel.enforceNoDataAvail();
                onAuthenticationFailed(i4);
            } else if (i == 3) {
                int i5 = parcel.readInt();
                int i6 = parcel.readInt();
                int i7 = parcel.readInt();
                int i8 = parcel.readInt();
                parcel.enforceNoDataAvail();
                onError(i5, i6, i7, i8);
            } else if (i == 4) {
                int i9 = parcel.readInt();
                int i10 = parcel.readInt();
                int i11 = parcel.readInt();
                parcel.enforceNoDataAvail();
                onAcquired(i9, i10, i11);
            } else if (i == 5) {
                int i12 = parcel.readInt();
                byte[] bArrCreateByteArray2 = parcel.createByteArray();
                Bundle bundle = (Bundle) parcel.readTypedObject(Bundle.CREATOR);
                parcel.enforceNoDataAvail();
                onSemAuthenticationSucceeded(i12, bArrCreateByteArray2, bundle);
            } else {
                return super.onTransact(i, parcel, parcel2, i2);
            }
            return true;
        }

        private static class Proxy implements IBiometricSensorReceiver {
            private IBinder mRemote;

            Proxy(IBinder iBinder) {
                this.mRemote = iBinder;
            }

            @Override // android.os.IInterface
            public IBinder asBinder() {
                return this.mRemote;
            }

            public String getInterfaceDescriptor() {
                return IBiometricSensorReceiver.DESCRIPTOR;
            }

            @Override // android.hardware.biometrics.IBiometricSensorReceiver
            public void onAuthenticationSucceeded(int i, byte[] bArr) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                try {
                    parcelObtain.writeInterfaceToken(IBiometricSensorReceiver.DESCRIPTOR);
                    parcelObtain.writeInt(i);
                    parcelObtain.writeByteArray(bArr);
                    this.mRemote.transact(1, parcelObtain, null, 1);
                } finally {
                    parcelObtain.recycle();
                }
            }

            @Override // android.hardware.biometrics.IBiometricSensorReceiver
            public void onAuthenticationFailed(int i) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                try {
                    parcelObtain.writeInterfaceToken(IBiometricSensorReceiver.DESCRIPTOR);
                    parcelObtain.writeInt(i);
                    this.mRemote.transact(2, parcelObtain, null, 1);
                } finally {
                    parcelObtain.recycle();
                }
            }

            @Override // android.hardware.biometrics.IBiometricSensorReceiver
            public void onError(int i, int i2, int i3, int i4) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                try {
                    parcelObtain.writeInterfaceToken(IBiometricSensorReceiver.DESCRIPTOR);
                    parcelObtain.writeInt(i);
                    parcelObtain.writeInt(i2);
                    parcelObtain.writeInt(i3);
                    parcelObtain.writeInt(i4);
                    this.mRemote.transact(3, parcelObtain, null, 1);
                } finally {
                    parcelObtain.recycle();
                }
            }

            @Override // android.hardware.biometrics.IBiometricSensorReceiver
            public void onAcquired(int i, int i2, int i3) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                try {
                    parcelObtain.writeInterfaceToken(IBiometricSensorReceiver.DESCRIPTOR);
                    parcelObtain.writeInt(i);
                    parcelObtain.writeInt(i2);
                    parcelObtain.writeInt(i3);
                    this.mRemote.transact(4, parcelObtain, null, 1);
                } finally {
                    parcelObtain.recycle();
                }
            }

            @Override // android.hardware.biometrics.IBiometricSensorReceiver
            public void onSemAuthenticationSucceeded(int i, byte[] bArr, Bundle bundle) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                try {
                    parcelObtain.writeInterfaceToken(IBiometricSensorReceiver.DESCRIPTOR);
                    parcelObtain.writeInt(i);
                    parcelObtain.writeByteArray(bArr);
                    parcelObtain.writeTypedObject(bundle, 0);
                    this.mRemote.transact(5, parcelObtain, null, 1);
                } finally {
                    parcelObtain.recycle();
                }
            }
        }
    }
}
