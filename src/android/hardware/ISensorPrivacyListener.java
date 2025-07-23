package android.hardware;

import android.os.Binder;
import android.os.IBinder;
import android.os.IInterface;
import android.os.Parcel;
import android.os.RemoteException;

/* loaded from: classes2.dex */
public interface ISensorPrivacyListener extends IInterface {
    public static final String DESCRIPTOR = "android.hardware.ISensorPrivacyListener";

    public static class Default implements ISensorPrivacyListener {
        @Override // android.os.IInterface
        public IBinder asBinder() {
            return null;
        }

        @Override // android.hardware.ISensorPrivacyListener
        public void onSensorPrivacyChanged(int i, int i2, boolean z) throws RemoteException {
        }

        @Override // android.hardware.ISensorPrivacyListener
        public void onSensorPrivacyStateChanged(int i, int i2, int i3) throws RemoteException {
        }
    }

    void onSensorPrivacyChanged(int i, int i2, boolean z) throws RemoteException;

    void onSensorPrivacyStateChanged(int i, int i2, int i3) throws RemoteException;

    public static abstract class Stub extends Binder implements ISensorPrivacyListener {
        static final int TRANSACTION_onSensorPrivacyChanged = 1;
        static final int TRANSACTION_onSensorPrivacyStateChanged = 2;

        @Override // android.os.IInterface
        public IBinder asBinder() {
            return this;
        }

        @Override // android.os.Binder
        public int getMaxTransactionId() {
            return 1;
        }

        public Stub() {
            attachInterface(this, ISensorPrivacyListener.DESCRIPTOR);
        }

        public static ISensorPrivacyListener asInterface(IBinder iBinder) {
            if (iBinder == null) {
                return null;
            }
            IInterface queryLocalInterface = iBinder.queryLocalInterface(ISensorPrivacyListener.DESCRIPTOR);
            if (queryLocalInterface != null && (queryLocalInterface instanceof ISensorPrivacyListener)) {
                return (ISensorPrivacyListener) queryLocalInterface;
            }
            return new Proxy(iBinder);
        }

        public static String getDefaultTransactionName(int i) {
            if (i == 1) {
                return "onSensorPrivacyChanged";
            }
            if (i != 2) {
                return null;
            }
            return "onSensorPrivacyStateChanged";
        }

        @Override // android.os.Binder
        public String getTransactionName(int i) {
            return getDefaultTransactionName(i);
        }

        @Override // android.os.Binder
        public boolean onTransact(int i, Parcel parcel, Parcel parcel2, int i2) throws RemoteException {
            if (i >= 1 && i <= 16777215) {
                parcel.enforceInterface(ISensorPrivacyListener.DESCRIPTOR);
            }
            if (i == 1598968902) {
                parcel2.writeString(ISensorPrivacyListener.DESCRIPTOR);
                return true;
            }
            if (i == 1) {
                int readInt = parcel.readInt();
                int readInt2 = parcel.readInt();
                boolean readBoolean = parcel.readBoolean();
                parcel.enforceNoDataAvail();
                onSensorPrivacyChanged(readInt, readInt2, readBoolean);
            } else if (i == 2) {
                int readInt3 = parcel.readInt();
                int readInt4 = parcel.readInt();
                int readInt5 = parcel.readInt();
                parcel.enforceNoDataAvail();
                onSensorPrivacyStateChanged(readInt3, readInt4, readInt5);
            } else {
                return super.onTransact(i, parcel, parcel2, i2);
            }
            return true;
        }

        private static class Proxy implements ISensorPrivacyListener {
            private IBinder mRemote;

            Proxy(IBinder iBinder) {
                this.mRemote = iBinder;
            }

            @Override // android.os.IInterface
            public IBinder asBinder() {
                return this.mRemote;
            }

            public String getInterfaceDescriptor() {
                return ISensorPrivacyListener.DESCRIPTOR;
            }

            @Override // android.hardware.ISensorPrivacyListener
            public void onSensorPrivacyChanged(int i, int i2, boolean z) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                try {
                    obtain.writeInterfaceToken(ISensorPrivacyListener.DESCRIPTOR);
                    obtain.writeInt(i);
                    obtain.writeInt(i2);
                    obtain.writeBoolean(z);
                    this.mRemote.transact(1, obtain, null, 1);
                } finally {
                    obtain.recycle();
                }
            }

            @Override // android.hardware.ISensorPrivacyListener
            public void onSensorPrivacyStateChanged(int i, int i2, int i3) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                try {
                    obtain.writeInterfaceToken(ISensorPrivacyListener.DESCRIPTOR);
                    obtain.writeInt(i);
                    obtain.writeInt(i2);
                    obtain.writeInt(i3);
                    this.mRemote.transact(2, obtain, null, 1);
                } finally {
                    obtain.recycle();
                }
            }
        }
    }
}
