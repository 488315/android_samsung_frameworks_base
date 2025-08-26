package android.hardware.biometrics;

import android.os.Binder;
import android.os.IBinder;
import android.os.IInterface;
import android.os.Parcel;
import android.os.RemoteException;

/* loaded from: classes2.dex */
public interface IBiometricSysuiReceiver extends IInterface {
    public static final String DESCRIPTOR = "android.hardware.biometrics.IBiometricSysuiReceiver";

    public static class Default implements IBiometricSysuiReceiver {
        @Override // android.os.IInterface
        public IBinder asBinder() {
            return null;
        }

        @Override // android.hardware.biometrics.IBiometricSysuiReceiver
        public void onDeviceCredentialPressed() throws RemoteException {
        }

        @Override // android.hardware.biometrics.IBiometricSysuiReceiver
        public void onDialogAnimatedIn(boolean z) throws RemoteException {
        }

        @Override // android.hardware.biometrics.IBiometricSysuiReceiver
        public void onDialogDismissed(int i, byte[] bArr) throws RemoteException {
        }

        @Override // android.hardware.biometrics.IBiometricSysuiReceiver
        public void onStartFingerprintNow() throws RemoteException {
        }

        @Override // android.hardware.biometrics.IBiometricSysuiReceiver
        public void onSystemEvent(int i) throws RemoteException {
        }

        @Override // android.hardware.biometrics.IBiometricSysuiReceiver
        public void onTryAgainPressed() throws RemoteException {
        }
    }

    void onDeviceCredentialPressed() throws RemoteException;

    void onDialogAnimatedIn(boolean z) throws RemoteException;

    void onDialogDismissed(int i, byte[] bArr) throws RemoteException;

    void onStartFingerprintNow() throws RemoteException;

    void onSystemEvent(int i) throws RemoteException;

    void onTryAgainPressed() throws RemoteException;

    public static abstract class Stub extends Binder implements IBiometricSysuiReceiver {
        static final int TRANSACTION_onDeviceCredentialPressed = 3;
        static final int TRANSACTION_onDialogAnimatedIn = 5;
        static final int TRANSACTION_onDialogDismissed = 1;
        static final int TRANSACTION_onStartFingerprintNow = 6;
        static final int TRANSACTION_onSystemEvent = 4;
        static final int TRANSACTION_onTryAgainPressed = 2;

        @Override // android.os.IInterface
        public IBinder asBinder() {
            return this;
        }

        @Override // android.os.Binder
        public int getMaxTransactionId() {
            return 5;
        }

        public Stub() {
            attachInterface(this, IBiometricSysuiReceiver.DESCRIPTOR);
        }

        public static IBiometricSysuiReceiver asInterface(IBinder iBinder) {
            if (iBinder == null) {
                return null;
            }
            IInterface iInterfaceQueryLocalInterface = iBinder.queryLocalInterface(IBiometricSysuiReceiver.DESCRIPTOR);
            if (iInterfaceQueryLocalInterface != null && (iInterfaceQueryLocalInterface instanceof IBiometricSysuiReceiver)) {
                return (IBiometricSysuiReceiver) iInterfaceQueryLocalInterface;
            }
            return new Proxy(iBinder);
        }

        public static String getDefaultTransactionName(int i) {
            switch (i) {
                case 1:
                    return "onDialogDismissed";
                case 2:
                    return "onTryAgainPressed";
                case 3:
                    return "onDeviceCredentialPressed";
                case 4:
                    return "onSystemEvent";
                case 5:
                    return "onDialogAnimatedIn";
                case 6:
                    return "onStartFingerprintNow";
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
                parcel.enforceInterface(IBiometricSysuiReceiver.DESCRIPTOR);
            }
            if (i == 1598968902) {
                parcel2.writeString(IBiometricSysuiReceiver.DESCRIPTOR);
                return true;
            }
            switch (i) {
                case 1:
                    int i3 = parcel.readInt();
                    byte[] bArrCreateByteArray = parcel.createByteArray();
                    parcel.enforceNoDataAvail();
                    onDialogDismissed(i3, bArrCreateByteArray);
                    return true;
                case 2:
                    onTryAgainPressed();
                    return true;
                case 3:
                    onDeviceCredentialPressed();
                    return true;
                case 4:
                    int i4 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    onSystemEvent(i4);
                    return true;
                case 5:
                    boolean z = parcel.readBoolean();
                    parcel.enforceNoDataAvail();
                    onDialogAnimatedIn(z);
                    return true;
                case 6:
                    onStartFingerprintNow();
                    return true;
                default:
                    return super.onTransact(i, parcel, parcel2, i2);
            }
        }

        private static class Proxy implements IBiometricSysuiReceiver {
            private IBinder mRemote;

            Proxy(IBinder iBinder) {
                this.mRemote = iBinder;
            }

            @Override // android.os.IInterface
            public IBinder asBinder() {
                return this.mRemote;
            }

            public String getInterfaceDescriptor() {
                return IBiometricSysuiReceiver.DESCRIPTOR;
            }

            @Override // android.hardware.biometrics.IBiometricSysuiReceiver
            public void onDialogDismissed(int i, byte[] bArr) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                try {
                    parcelObtain.writeInterfaceToken(IBiometricSysuiReceiver.DESCRIPTOR);
                    parcelObtain.writeInt(i);
                    parcelObtain.writeByteArray(bArr);
                    this.mRemote.transact(1, parcelObtain, null, 1);
                } finally {
                    parcelObtain.recycle();
                }
            }

            @Override // android.hardware.biometrics.IBiometricSysuiReceiver
            public void onTryAgainPressed() throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                try {
                    parcelObtain.writeInterfaceToken(IBiometricSysuiReceiver.DESCRIPTOR);
                    this.mRemote.transact(2, parcelObtain, null, 1);
                } finally {
                    parcelObtain.recycle();
                }
            }

            @Override // android.hardware.biometrics.IBiometricSysuiReceiver
            public void onDeviceCredentialPressed() throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                try {
                    parcelObtain.writeInterfaceToken(IBiometricSysuiReceiver.DESCRIPTOR);
                    this.mRemote.transact(3, parcelObtain, null, 1);
                } finally {
                    parcelObtain.recycle();
                }
            }

            @Override // android.hardware.biometrics.IBiometricSysuiReceiver
            public void onSystemEvent(int i) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                try {
                    parcelObtain.writeInterfaceToken(IBiometricSysuiReceiver.DESCRIPTOR);
                    parcelObtain.writeInt(i);
                    this.mRemote.transact(4, parcelObtain, null, 1);
                } finally {
                    parcelObtain.recycle();
                }
            }

            @Override // android.hardware.biometrics.IBiometricSysuiReceiver
            public void onDialogAnimatedIn(boolean z) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                try {
                    parcelObtain.writeInterfaceToken(IBiometricSysuiReceiver.DESCRIPTOR);
                    parcelObtain.writeBoolean(z);
                    this.mRemote.transact(5, parcelObtain, null, 1);
                } finally {
                    parcelObtain.recycle();
                }
            }

            @Override // android.hardware.biometrics.IBiometricSysuiReceiver
            public void onStartFingerprintNow() throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                try {
                    parcelObtain.writeInterfaceToken(IBiometricSysuiReceiver.DESCRIPTOR);
                    this.mRemote.transact(6, parcelObtain, null, 1);
                } finally {
                    parcelObtain.recycle();
                }
            }
        }
    }
}
