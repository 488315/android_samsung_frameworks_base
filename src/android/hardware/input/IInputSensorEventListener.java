package android.hardware.input;

import android.os.Binder;
import android.os.IBinder;
import android.os.IInterface;
import android.os.Parcel;
import android.os.RemoteException;

/* loaded from: classes2.dex */
public interface IInputSensorEventListener extends IInterface {
    public static final String DESCRIPTOR = "android.hardware.input.IInputSensorEventListener";

    public static class Default implements IInputSensorEventListener {
        @Override // android.os.IInterface
        public IBinder asBinder() {
            return null;
        }

        @Override // android.hardware.input.IInputSensorEventListener
        public void onInputSensorAccuracyChanged(int i, int i2, int i3) throws RemoteException {
        }

        @Override // android.hardware.input.IInputSensorEventListener
        public void onInputSensorChanged(int i, int i2, int i3, long j, float[] fArr) throws RemoteException {
        }
    }

    void onInputSensorAccuracyChanged(int i, int i2, int i3) throws RemoteException;

    void onInputSensorChanged(int i, int i2, int i3, long j, float[] fArr) throws RemoteException;

    public static abstract class Stub extends Binder implements IInputSensorEventListener {
        static final int TRANSACTION_onInputSensorAccuracyChanged = 2;
        static final int TRANSACTION_onInputSensorChanged = 1;

        @Override // android.os.IInterface
        public IBinder asBinder() {
            return this;
        }

        @Override // android.os.Binder
        public int getMaxTransactionId() {
            return 1;
        }

        public Stub() {
            attachInterface(this, IInputSensorEventListener.DESCRIPTOR);
        }

        public static IInputSensorEventListener asInterface(IBinder iBinder) {
            if (iBinder == null) {
                return null;
            }
            IInterface queryLocalInterface = iBinder.queryLocalInterface(IInputSensorEventListener.DESCRIPTOR);
            if (queryLocalInterface != null && (queryLocalInterface instanceof IInputSensorEventListener)) {
                return (IInputSensorEventListener) queryLocalInterface;
            }
            return new Proxy(iBinder);
        }

        public static String getDefaultTransactionName(int i) {
            if (i == 1) {
                return "onInputSensorChanged";
            }
            if (i != 2) {
                return null;
            }
            return "onInputSensorAccuracyChanged";
        }

        @Override // android.os.Binder
        public String getTransactionName(int i) {
            return getDefaultTransactionName(i);
        }

        @Override // android.os.Binder
        public boolean onTransact(int i, Parcel parcel, Parcel parcel2, int i2) throws RemoteException {
            if (i >= 1 && i <= 16777215) {
                parcel.enforceInterface(IInputSensorEventListener.DESCRIPTOR);
            }
            if (i == 1598968902) {
                parcel2.writeString(IInputSensorEventListener.DESCRIPTOR);
                return true;
            }
            if (i == 1) {
                int readInt = parcel.readInt();
                int readInt2 = parcel.readInt();
                int readInt3 = parcel.readInt();
                long readLong = parcel.readLong();
                float[] createFloatArray = parcel.createFloatArray();
                parcel.enforceNoDataAvail();
                onInputSensorChanged(readInt, readInt2, readInt3, readLong, createFloatArray);
            } else if (i == 2) {
                int readInt4 = parcel.readInt();
                int readInt5 = parcel.readInt();
                int readInt6 = parcel.readInt();
                parcel.enforceNoDataAvail();
                onInputSensorAccuracyChanged(readInt4, readInt5, readInt6);
            } else {
                return super.onTransact(i, parcel, parcel2, i2);
            }
            return true;
        }

        private static class Proxy implements IInputSensorEventListener {
            private IBinder mRemote;

            Proxy(IBinder iBinder) {
                this.mRemote = iBinder;
            }

            @Override // android.os.IInterface
            public IBinder asBinder() {
                return this.mRemote;
            }

            public String getInterfaceDescriptor() {
                return IInputSensorEventListener.DESCRIPTOR;
            }

            @Override // android.hardware.input.IInputSensorEventListener
            public void onInputSensorChanged(int i, int i2, int i3, long j, float[] fArr) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                try {
                    obtain.writeInterfaceToken(IInputSensorEventListener.DESCRIPTOR);
                    obtain.writeInt(i);
                    obtain.writeInt(i2);
                    obtain.writeInt(i3);
                    obtain.writeLong(j);
                    obtain.writeFloatArray(fArr);
                    this.mRemote.transact(1, obtain, null, 1);
                } finally {
                    obtain.recycle();
                }
            }

            @Override // android.hardware.input.IInputSensorEventListener
            public void onInputSensorAccuracyChanged(int i, int i2, int i3) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                try {
                    obtain.writeInterfaceToken(IInputSensorEventListener.DESCRIPTOR);
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
