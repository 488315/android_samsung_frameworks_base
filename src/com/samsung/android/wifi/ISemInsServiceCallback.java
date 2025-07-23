package com.samsung.android.wifi;

import android.os.Binder;
import android.os.IBinder;
import android.os.IInterface;
import android.os.Parcel;
import android.os.RemoteException;
import com.samsung.android.wifi.intelligence.ins.tr.entity.ModelMetrics;

/* loaded from: classes6.dex */
public interface ISemInsServiceCallback extends IInterface {
    public static final String DESCRIPTOR = "com.samsung.android.wifi.ISemInsServiceCallback";

    public static class Default implements ISemInsServiceCallback {
        @Override // android.os.IInterface
        public IBinder asBinder() {
            return null;
        }

        @Override // com.samsung.android.wifi.ISemInsServiceCallback
        public void onInferResult(String str, String[] strArr) throws RemoteException {
        }

        @Override // com.samsung.android.wifi.ISemInsServiceCallback
        public void onNsmMetricsUpdate(ModelMetrics modelMetrics) throws RemoteException {
        }

        @Override // com.samsung.android.wifi.ISemInsServiceCallback
        public void onOutageCountChanged(String str, double d, double d2, int i, boolean z, String str2) throws RemoteException {
        }

        @Override // com.samsung.android.wifi.ISemInsServiceCallback
        public void onPathMetricsUpdate(ModelMetrics modelMetrics) throws RemoteException {
        }

        @Override // com.samsung.android.wifi.ISemInsServiceCallback
        public void onServiceStateChange(int i) throws RemoteException {
        }

        @Override // com.samsung.android.wifi.ISemInsServiceCallback
        public void onTrainingStarted(String[] strArr) throws RemoteException {
        }

        @Override // com.samsung.android.wifi.ISemInsServiceCallback
        public void onTrainingStopped() throws RemoteException {
        }
    }

    void onInferResult(String str, String[] strArr) throws RemoteException;

    void onNsmMetricsUpdate(ModelMetrics modelMetrics) throws RemoteException;

    void onOutageCountChanged(String str, double d, double d2, int i, boolean z, String str2) throws RemoteException;

    void onPathMetricsUpdate(ModelMetrics modelMetrics) throws RemoteException;

    void onServiceStateChange(int i) throws RemoteException;

    void onTrainingStarted(String[] strArr) throws RemoteException;

    void onTrainingStopped() throws RemoteException;

    public static abstract class Stub extends Binder implements ISemInsServiceCallback {
        static final int TRANSACTION_onInferResult = 5;
        static final int TRANSACTION_onNsmMetricsUpdate = 4;
        static final int TRANSACTION_onOutageCountChanged = 1;
        static final int TRANSACTION_onPathMetricsUpdate = 3;
        static final int TRANSACTION_onServiceStateChange = 2;
        static final int TRANSACTION_onTrainingStarted = 6;
        static final int TRANSACTION_onTrainingStopped = 7;

        @Override // android.os.IInterface
        public IBinder asBinder() {
            return this;
        }

        @Override // android.os.Binder
        public int getMaxTransactionId() {
            return 6;
        }

        public Stub() {
            attachInterface(this, ISemInsServiceCallback.DESCRIPTOR);
        }

        public static ISemInsServiceCallback asInterface(IBinder iBinder) {
            if (iBinder == null) {
                return null;
            }
            IInterface queryLocalInterface = iBinder.queryLocalInterface(ISemInsServiceCallback.DESCRIPTOR);
            if (queryLocalInterface != null && (queryLocalInterface instanceof ISemInsServiceCallback)) {
                return (ISemInsServiceCallback) queryLocalInterface;
            }
            return new Proxy(iBinder);
        }

        public static String getDefaultTransactionName(int i) {
            switch (i) {
                case 1:
                    return "onOutageCountChanged";
                case 2:
                    return "onServiceStateChange";
                case 3:
                    return "onPathMetricsUpdate";
                case 4:
                    return "onNsmMetricsUpdate";
                case 5:
                    return "onInferResult";
                case 6:
                    return "onTrainingStarted";
                case 7:
                    return "onTrainingStopped";
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
                parcel.enforceInterface(ISemInsServiceCallback.DESCRIPTOR);
            }
            if (i == 1598968902) {
                parcel2.writeString(ISemInsServiceCallback.DESCRIPTOR);
                return true;
            }
            switch (i) {
                case 1:
                    String readString = parcel.readString();
                    double readDouble = parcel.readDouble();
                    double readDouble2 = parcel.readDouble();
                    int readInt = parcel.readInt();
                    boolean readBoolean = parcel.readBoolean();
                    String readString2 = parcel.readString();
                    parcel.enforceNoDataAvail();
                    onOutageCountChanged(readString, readDouble, readDouble2, readInt, readBoolean, readString2);
                    return true;
                case 2:
                    int readInt2 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    onServiceStateChange(readInt2);
                    return true;
                case 3:
                    ModelMetrics modelMetrics = (ModelMetrics) parcel.readTypedObject(ModelMetrics.CREATOR);
                    parcel.enforceNoDataAvail();
                    onPathMetricsUpdate(modelMetrics);
                    return true;
                case 4:
                    ModelMetrics modelMetrics2 = (ModelMetrics) parcel.readTypedObject(ModelMetrics.CREATOR);
                    parcel.enforceNoDataAvail();
                    onNsmMetricsUpdate(modelMetrics2);
                    return true;
                case 5:
                    String readString3 = parcel.readString();
                    String[] createStringArray = parcel.createStringArray();
                    parcel.enforceNoDataAvail();
                    onInferResult(readString3, createStringArray);
                    return true;
                case 6:
                    String[] createStringArray2 = parcel.createStringArray();
                    parcel.enforceNoDataAvail();
                    onTrainingStarted(createStringArray2);
                    return true;
                case 7:
                    onTrainingStopped();
                    return true;
                default:
                    return super.onTransact(i, parcel, parcel2, i2);
            }
        }

        private static class Proxy implements ISemInsServiceCallback {
            private IBinder mRemote;

            Proxy(IBinder iBinder) {
                this.mRemote = iBinder;
            }

            @Override // android.os.IInterface
            public IBinder asBinder() {
                return this.mRemote;
            }

            public String getInterfaceDescriptor() {
                return ISemInsServiceCallback.DESCRIPTOR;
            }

            @Override // com.samsung.android.wifi.ISemInsServiceCallback
            public void onOutageCountChanged(String str, double d, double d2, int i, boolean z, String str2) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                try {
                    obtain.writeInterfaceToken(ISemInsServiceCallback.DESCRIPTOR);
                    obtain.writeString(str);
                    obtain.writeDouble(d);
                    obtain.writeDouble(d2);
                    obtain.writeInt(i);
                    obtain.writeBoolean(z);
                    obtain.writeString(str2);
                    this.mRemote.transact(1, obtain, null, 1);
                } finally {
                    obtain.recycle();
                }
            }

            @Override // com.samsung.android.wifi.ISemInsServiceCallback
            public void onServiceStateChange(int i) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                try {
                    obtain.writeInterfaceToken(ISemInsServiceCallback.DESCRIPTOR);
                    obtain.writeInt(i);
                    this.mRemote.transact(2, obtain, null, 1);
                } finally {
                    obtain.recycle();
                }
            }

            @Override // com.samsung.android.wifi.ISemInsServiceCallback
            public void onPathMetricsUpdate(ModelMetrics modelMetrics) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                try {
                    obtain.writeInterfaceToken(ISemInsServiceCallback.DESCRIPTOR);
                    obtain.writeTypedObject(modelMetrics, 0);
                    this.mRemote.transact(3, obtain, null, 1);
                } finally {
                    obtain.recycle();
                }
            }

            @Override // com.samsung.android.wifi.ISemInsServiceCallback
            public void onNsmMetricsUpdate(ModelMetrics modelMetrics) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                try {
                    obtain.writeInterfaceToken(ISemInsServiceCallback.DESCRIPTOR);
                    obtain.writeTypedObject(modelMetrics, 0);
                    this.mRemote.transact(4, obtain, null, 1);
                } finally {
                    obtain.recycle();
                }
            }

            @Override // com.samsung.android.wifi.ISemInsServiceCallback
            public void onInferResult(String str, String[] strArr) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                try {
                    obtain.writeInterfaceToken(ISemInsServiceCallback.DESCRIPTOR);
                    obtain.writeString(str);
                    obtain.writeStringArray(strArr);
                    this.mRemote.transact(5, obtain, null, 1);
                } finally {
                    obtain.recycle();
                }
            }

            @Override // com.samsung.android.wifi.ISemInsServiceCallback
            public void onTrainingStarted(String[] strArr) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                try {
                    obtain.writeInterfaceToken(ISemInsServiceCallback.DESCRIPTOR);
                    obtain.writeStringArray(strArr);
                    this.mRemote.transact(6, obtain, null, 1);
                } finally {
                    obtain.recycle();
                }
            }

            @Override // com.samsung.android.wifi.ISemInsServiceCallback
            public void onTrainingStopped() throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                try {
                    obtain.writeInterfaceToken(ISemInsServiceCallback.DESCRIPTOR);
                    this.mRemote.transact(7, obtain, null, 1);
                } finally {
                    obtain.recycle();
                }
            }
        }
    }
}
