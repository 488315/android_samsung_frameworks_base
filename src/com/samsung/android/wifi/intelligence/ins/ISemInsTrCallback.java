package com.samsung.android.wifi.intelligence.ins;

import android.os.Binder;
import android.os.IBinder;
import android.os.IInterface;
import android.os.Parcel;
import android.os.RemoteException;
import com.samsung.android.wifi.intelligence.ins.tr.entity.ModelMetrics;

/* loaded from: classes6.dex */
public interface ISemInsTrCallback extends IInterface {
    public static final String DESCRIPTOR = "com.samsung.android.wifi.intelligence.ins.ISemInsTrCallback";

    public static class Default implements ISemInsTrCallback {
        @Override // android.os.IInterface
        public IBinder asBinder() {
            return null;
        }

        @Override // com.samsung.android.wifi.intelligence.ins.ISemInsTrCallback
        public void onNsmMetricsUpdate(ModelMetrics modelMetrics) throws RemoteException {
        }

        @Override // com.samsung.android.wifi.intelligence.ins.ISemInsTrCallback
        public void onPathMetricsUpdate(ModelMetrics modelMetrics) throws RemoteException {
        }

        @Override // com.samsung.android.wifi.intelligence.ins.ISemInsTrCallback
        public void onTrainingCompleted(boolean z) throws RemoteException {
        }
    }

    void onNsmMetricsUpdate(ModelMetrics modelMetrics) throws RemoteException;

    void onPathMetricsUpdate(ModelMetrics modelMetrics) throws RemoteException;

    void onTrainingCompleted(boolean z) throws RemoteException;

    public static abstract class Stub extends Binder implements ISemInsTrCallback {
        static final int TRANSACTION_onNsmMetricsUpdate = 3;
        static final int TRANSACTION_onPathMetricsUpdate = 2;
        static final int TRANSACTION_onTrainingCompleted = 1;

        @Override // android.os.IInterface
        public IBinder asBinder() {
            return this;
        }

        @Override // android.os.Binder
        public int getMaxTransactionId() {
            return 2;
        }

        public Stub() {
            attachInterface(this, ISemInsTrCallback.DESCRIPTOR);
        }

        public static ISemInsTrCallback asInterface(IBinder iBinder) {
            if (iBinder == null) {
                return null;
            }
            IInterface queryLocalInterface = iBinder.queryLocalInterface(ISemInsTrCallback.DESCRIPTOR);
            if (queryLocalInterface != null && (queryLocalInterface instanceof ISemInsTrCallback)) {
                return (ISemInsTrCallback) queryLocalInterface;
            }
            return new Proxy(iBinder);
        }

        public static String getDefaultTransactionName(int i) {
            if (i == 1) {
                return "onTrainingCompleted";
            }
            if (i == 2) {
                return "onPathMetricsUpdate";
            }
            if (i != 3) {
                return null;
            }
            return "onNsmMetricsUpdate";
        }

        @Override // android.os.Binder
        public String getTransactionName(int i) {
            return getDefaultTransactionName(i);
        }

        @Override // android.os.Binder
        public boolean onTransact(int i, Parcel parcel, Parcel parcel2, int i2) throws RemoteException {
            if (i >= 1 && i <= 16777215) {
                parcel.enforceInterface(ISemInsTrCallback.DESCRIPTOR);
            }
            if (i == 1598968902) {
                parcel2.writeString(ISemInsTrCallback.DESCRIPTOR);
                return true;
            }
            if (i == 1) {
                boolean readBoolean = parcel.readBoolean();
                parcel.enforceNoDataAvail();
                onTrainingCompleted(readBoolean);
            } else if (i == 2) {
                ModelMetrics modelMetrics = (ModelMetrics) parcel.readTypedObject(ModelMetrics.CREATOR);
                parcel.enforceNoDataAvail();
                onPathMetricsUpdate(modelMetrics);
            } else if (i == 3) {
                ModelMetrics modelMetrics2 = (ModelMetrics) parcel.readTypedObject(ModelMetrics.CREATOR);
                parcel.enforceNoDataAvail();
                onNsmMetricsUpdate(modelMetrics2);
            } else {
                return super.onTransact(i, parcel, parcel2, i2);
            }
            return true;
        }

        private static class Proxy implements ISemInsTrCallback {
            private IBinder mRemote;

            Proxy(IBinder iBinder) {
                this.mRemote = iBinder;
            }

            @Override // android.os.IInterface
            public IBinder asBinder() {
                return this.mRemote;
            }

            public String getInterfaceDescriptor() {
                return ISemInsTrCallback.DESCRIPTOR;
            }

            @Override // com.samsung.android.wifi.intelligence.ins.ISemInsTrCallback
            public void onTrainingCompleted(boolean z) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                try {
                    obtain.writeInterfaceToken(ISemInsTrCallback.DESCRIPTOR);
                    obtain.writeBoolean(z);
                    this.mRemote.transact(1, obtain, null, 1);
                } finally {
                    obtain.recycle();
                }
            }

            @Override // com.samsung.android.wifi.intelligence.ins.ISemInsTrCallback
            public void onPathMetricsUpdate(ModelMetrics modelMetrics) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                try {
                    obtain.writeInterfaceToken(ISemInsTrCallback.DESCRIPTOR);
                    obtain.writeTypedObject(modelMetrics, 0);
                    this.mRemote.transact(2, obtain, null, 1);
                } finally {
                    obtain.recycle();
                }
            }

            @Override // com.samsung.android.wifi.intelligence.ins.ISemInsTrCallback
            public void onNsmMetricsUpdate(ModelMetrics modelMetrics) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                try {
                    obtain.writeInterfaceToken(ISemInsTrCallback.DESCRIPTOR);
                    obtain.writeTypedObject(modelMetrics, 0);
                    this.mRemote.transact(3, obtain, null, 1);
                } finally {
                    obtain.recycle();
                }
            }
        }
    }
}
