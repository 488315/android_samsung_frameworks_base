package android.os.vibrator;

import android.os.Binder;
import android.os.IBinder;
import android.os.IInterface;
import android.os.Parcel;
import android.os.RemoteException;
import android.os.vibrator.IVibrationSession;

/* loaded from: classes3.dex */
public interface IVibrationSessionCallback extends IInterface {
    public static final String DESCRIPTOR = "android.os.vibrator.IVibrationSessionCallback";

    public static class Default implements IVibrationSessionCallback {
        @Override // android.os.IInterface
        public IBinder asBinder() {
            return null;
        }

        @Override // android.os.vibrator.IVibrationSessionCallback
        public void onFinished(int i) throws RemoteException {
        }

        @Override // android.os.vibrator.IVibrationSessionCallback
        public void onFinishing() throws RemoteException {
        }

        @Override // android.os.vibrator.IVibrationSessionCallback
        public void onStarted(IVibrationSession iVibrationSession) throws RemoteException {
        }
    }

    void onFinished(int i) throws RemoteException;

    void onFinishing() throws RemoteException;

    void onStarted(IVibrationSession iVibrationSession) throws RemoteException;

    public static abstract class Stub extends Binder implements IVibrationSessionCallback {
        static final int TRANSACTION_onFinished = 3;
        static final int TRANSACTION_onFinishing = 2;
        static final int TRANSACTION_onStarted = 1;

        @Override // android.os.IInterface
        public IBinder asBinder() {
            return this;
        }

        @Override // android.os.Binder
        public int getMaxTransactionId() {
            return 2;
        }

        public Stub() {
            attachInterface(this, IVibrationSessionCallback.DESCRIPTOR);
        }

        public static IVibrationSessionCallback asInterface(IBinder iBinder) {
            if (iBinder == null) {
                return null;
            }
            IInterface iInterfaceQueryLocalInterface = iBinder.queryLocalInterface(IVibrationSessionCallback.DESCRIPTOR);
            if (iInterfaceQueryLocalInterface != null && (iInterfaceQueryLocalInterface instanceof IVibrationSessionCallback)) {
                return (IVibrationSessionCallback) iInterfaceQueryLocalInterface;
            }
            return new Proxy(iBinder);
        }

        public static String getDefaultTransactionName(int i) {
            if (i == 1) {
                return "onStarted";
            }
            if (i == 2) {
                return "onFinishing";
            }
            if (i != 3) {
                return null;
            }
            return "onFinished";
        }

        @Override // android.os.Binder
        public String getTransactionName(int i) {
            return getDefaultTransactionName(i);
        }

        @Override // android.os.Binder
        public boolean onTransact(int i, Parcel parcel, Parcel parcel2, int i2) throws RemoteException {
            if (i >= 1 && i <= 16777215) {
                parcel.enforceInterface(IVibrationSessionCallback.DESCRIPTOR);
            }
            if (i == 1598968902) {
                parcel2.writeString(IVibrationSessionCallback.DESCRIPTOR);
                return true;
            }
            if (i == 1) {
                IVibrationSession iVibrationSessionAsInterface = IVibrationSession.Stub.asInterface(parcel.readStrongBinder());
                parcel.enforceNoDataAvail();
                onStarted(iVibrationSessionAsInterface);
            } else if (i == 2) {
                onFinishing();
            } else if (i == 3) {
                int i3 = parcel.readInt();
                parcel.enforceNoDataAvail();
                onFinished(i3);
            } else {
                return super.onTransact(i, parcel, parcel2, i2);
            }
            return true;
        }

        private static class Proxy implements IVibrationSessionCallback {
            private IBinder mRemote;

            Proxy(IBinder iBinder) {
                this.mRemote = iBinder;
            }

            @Override // android.os.IInterface
            public IBinder asBinder() {
                return this.mRemote;
            }

            public String getInterfaceDescriptor() {
                return IVibrationSessionCallback.DESCRIPTOR;
            }

            @Override // android.os.vibrator.IVibrationSessionCallback
            public void onStarted(IVibrationSession iVibrationSession) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                try {
                    parcelObtain.writeInterfaceToken(IVibrationSessionCallback.DESCRIPTOR);
                    parcelObtain.writeStrongInterface(iVibrationSession);
                    this.mRemote.transact(1, parcelObtain, null, 1);
                } finally {
                    parcelObtain.recycle();
                }
            }

            @Override // android.os.vibrator.IVibrationSessionCallback
            public void onFinishing() throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                try {
                    parcelObtain.writeInterfaceToken(IVibrationSessionCallback.DESCRIPTOR);
                    this.mRemote.transact(2, parcelObtain, null, 1);
                } finally {
                    parcelObtain.recycle();
                }
            }

            @Override // android.os.vibrator.IVibrationSessionCallback
            public void onFinished(int i) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                try {
                    parcelObtain.writeInterfaceToken(IVibrationSessionCallback.DESCRIPTOR);
                    parcelObtain.writeInt(i);
                    this.mRemote.transact(3, parcelObtain, null, 1);
                } finally {
                    parcelObtain.recycle();
                }
            }
        }
    }
}
