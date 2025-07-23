package android.os.vibrator;

import android.os.Binder;
import android.os.CombinedVibration;
import android.os.IBinder;
import android.os.IInterface;
import android.os.Parcel;
import android.os.RemoteException;
import com.samsung.android.edge.EdgeManagerInternal;

/* loaded from: classes3.dex */
public interface IVibrationSession extends IInterface {
    public static final String DESCRIPTOR = "android.os.vibrator.IVibrationSession";
    public static final int STATUS_CANCELED = 4;
    public static final int STATUS_IGNORED = 2;
    public static final int STATUS_SUCCESS = 1;
    public static final int STATUS_UNKNOWN = 0;
    public static final int STATUS_UNKNOWN_ERROR = 5;
    public static final int STATUS_UNSUPPORTED = 3;

    public static class Default implements IVibrationSession {
        @Override // android.os.IInterface
        public IBinder asBinder() {
            return null;
        }

        @Override // android.os.vibrator.IVibrationSession
        public void cancelSession() throws RemoteException {
        }

        @Override // android.os.vibrator.IVibrationSession
        public void finishSession() throws RemoteException {
        }

        @Override // android.os.vibrator.IVibrationSession
        public void vibrate(CombinedVibration combinedVibration, String str) throws RemoteException {
        }
    }

    void cancelSession() throws RemoteException;

    void finishSession() throws RemoteException;

    void vibrate(CombinedVibration combinedVibration, String str) throws RemoteException;

    public static abstract class Stub extends Binder implements IVibrationSession {
        static final int TRANSACTION_cancelSession = 3;
        static final int TRANSACTION_finishSession = 2;
        static final int TRANSACTION_vibrate = 1;

        @Override // android.os.IInterface
        public IBinder asBinder() {
            return this;
        }

        @Override // android.os.Binder
        public int getMaxTransactionId() {
            return 2;
        }

        public Stub() {
            attachInterface(this, IVibrationSession.DESCRIPTOR);
        }

        public static IVibrationSession asInterface(IBinder iBinder) {
            if (iBinder == null) {
                return null;
            }
            IInterface queryLocalInterface = iBinder.queryLocalInterface(IVibrationSession.DESCRIPTOR);
            if (queryLocalInterface != null && (queryLocalInterface instanceof IVibrationSession)) {
                return (IVibrationSession) queryLocalInterface;
            }
            return new Proxy(iBinder);
        }

        public static String getDefaultTransactionName(int i) {
            if (i == 1) {
                return EdgeManagerInternal.NOTIFICATION_KEY_VIBRATE;
            }
            if (i == 2) {
                return "finishSession";
            }
            if (i != 3) {
                return null;
            }
            return "cancelSession";
        }

        @Override // android.os.Binder
        public String getTransactionName(int i) {
            return getDefaultTransactionName(i);
        }

        @Override // android.os.Binder
        public boolean onTransact(int i, Parcel parcel, Parcel parcel2, int i2) throws RemoteException {
            if (i >= 1 && i <= 16777215) {
                parcel.enforceInterface(IVibrationSession.DESCRIPTOR);
            }
            if (i == 1598968902) {
                parcel2.writeString(IVibrationSession.DESCRIPTOR);
                return true;
            }
            if (i == 1) {
                CombinedVibration combinedVibration = (CombinedVibration) parcel.readTypedObject(CombinedVibration.CREATOR);
                String readString = parcel.readString();
                parcel.enforceNoDataAvail();
                vibrate(combinedVibration, readString);
                parcel2.writeNoException();
            } else if (i == 2) {
                finishSession();
                parcel2.writeNoException();
            } else if (i == 3) {
                cancelSession();
                parcel2.writeNoException();
            } else {
                return super.onTransact(i, parcel, parcel2, i2);
            }
            return true;
        }

        private static class Proxy implements IVibrationSession {
            private IBinder mRemote;

            Proxy(IBinder iBinder) {
                this.mRemote = iBinder;
            }

            @Override // android.os.IInterface
            public IBinder asBinder() {
                return this.mRemote;
            }

            public String getInterfaceDescriptor() {
                return IVibrationSession.DESCRIPTOR;
            }

            @Override // android.os.vibrator.IVibrationSession
            public void vibrate(CombinedVibration combinedVibration, String str) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(IVibrationSession.DESCRIPTOR);
                    obtain.writeTypedObject(combinedVibration, 0);
                    obtain.writeString(str);
                    this.mRemote.transact(1, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.os.vibrator.IVibrationSession
            public void finishSession() throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(IVibrationSession.DESCRIPTOR);
                    this.mRemote.transact(2, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.os.vibrator.IVibrationSession
            public void cancelSession() throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(IVibrationSession.DESCRIPTOR);
                    this.mRemote.transact(3, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }
        }
    }
}
