package com.samsung.android.wifi.intelligence.ins;

import android.os.Binder;
import android.os.IBinder;
import android.os.IInterface;
import android.os.Parcel;
import android.os.RemoteException;

/* loaded from: classes6.dex */
public interface ISemInsSensorCallback extends IInterface {
    public static final String DESCRIPTOR = "com.samsung.android.wifi.intelligence.ins.ISemInsSensorCallback";

    public static class Default implements ISemInsSensorCallback {
        @Override // android.os.IInterface
        public IBinder asBinder() {
            return null;
        }

        @Override // com.samsung.android.wifi.intelligence.ins.ISemInsSensorCallback
        public void onSensorChanged(long j, int i, float[] fArr) throws RemoteException {
        }
    }

    void onSensorChanged(long j, int i, float[] fArr) throws RemoteException;

    public static abstract class Stub extends Binder implements ISemInsSensorCallback {
        static final int TRANSACTION_onSensorChanged = 1;

        @Override // android.os.IInterface
        public IBinder asBinder() {
            return this;
        }

        @Override // android.os.Binder
        public int getMaxTransactionId() {
            return 0;
        }

        public Stub() {
            attachInterface(this, ISemInsSensorCallback.DESCRIPTOR);
        }

        public static ISemInsSensorCallback asInterface(IBinder iBinder) {
            if (iBinder == null) {
                return null;
            }
            IInterface queryLocalInterface = iBinder.queryLocalInterface(ISemInsSensorCallback.DESCRIPTOR);
            if (queryLocalInterface != null && (queryLocalInterface instanceof ISemInsSensorCallback)) {
                return (ISemInsSensorCallback) queryLocalInterface;
            }
            return new Proxy(iBinder);
        }

        public static String getDefaultTransactionName(int i) {
            if (i != 1) {
                return null;
            }
            return "onSensorChanged";
        }

        @Override // android.os.Binder
        public String getTransactionName(int i) {
            return getDefaultTransactionName(i);
        }

        @Override // android.os.Binder
        public boolean onTransact(int i, Parcel parcel, Parcel parcel2, int i2) throws RemoteException {
            if (i >= 1 && i <= 16777215) {
                parcel.enforceInterface(ISemInsSensorCallback.DESCRIPTOR);
            }
            if (i == 1598968902) {
                parcel2.writeString(ISemInsSensorCallback.DESCRIPTOR);
                return true;
            }
            if (i == 1) {
                long readLong = parcel.readLong();
                int readInt = parcel.readInt();
                float[] createFloatArray = parcel.createFloatArray();
                parcel.enforceNoDataAvail();
                onSensorChanged(readLong, readInt, createFloatArray);
                return true;
            }
            return super.onTransact(i, parcel, parcel2, i2);
        }

        private static class Proxy implements ISemInsSensorCallback {
            private IBinder mRemote;

            Proxy(IBinder iBinder) {
                this.mRemote = iBinder;
            }

            @Override // android.os.IInterface
            public IBinder asBinder() {
                return this.mRemote;
            }

            public String getInterfaceDescriptor() {
                return ISemInsSensorCallback.DESCRIPTOR;
            }

            @Override // com.samsung.android.wifi.intelligence.ins.ISemInsSensorCallback
            public void onSensorChanged(long j, int i, float[] fArr) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                try {
                    obtain.writeInterfaceToken(ISemInsSensorCallback.DESCRIPTOR);
                    obtain.writeLong(j);
                    obtain.writeInt(i);
                    obtain.writeFloatArray(fArr);
                    this.mRemote.transact(1, obtain, null, 1);
                } finally {
                    obtain.recycle();
                }
            }
        }
    }
}
