package com.att.iqi;

import android.os.Binder;
import android.os.IBinder;
import android.os.IInterface;
import android.os.Parcel;
import android.os.Parcelable;
import android.os.RemoteException;

import com.att.iqi.lib.Metric;

public interface IIQIService extends IInterface {
    public static final String DESCRIPTOR = "com.att.iqi.IIQIService";

    public class Default implements IIQIService {
        @Override // android.os.IInterface
        public IBinder asBinder() {
            return null;
        }

        @Override // com.att.iqi.IIQIService
        public void disableService() throws RemoteException {}

        @Override // com.att.iqi.IIQIService
        public void forceStopService() throws RemoteException {}

        @Override // com.att.iqi.IIQIService
        public long getTimestamp() throws RemoteException {
            return 0L;
        }

        @Override // com.att.iqi.IIQIService
        public void registerMetricQueryCallback(
                Metric.ID id, IMetricQueryCallback iMetricQueryCallback) throws RemoteException {}

        @Override // com.att.iqi.IIQIService
        public void registerMetricSourcingCallback(
                Metric.ID id, IMetricSourcingCallback iMetricSourcingCallback)
                throws RemoteException {}

        @Override // com.att.iqi.IIQIService
        public void registerProfileChangedCallback(IProfileChangedCallback iProfileChangedCallback)
                throws RemoteException {}

        @Override // com.att.iqi.IIQIService
        public boolean reportKeyCode(byte[] bArr) throws RemoteException {
            return false;
        }

        @Override // com.att.iqi.IIQIService
        public boolean setUnlockCode(long j) throws RemoteException {
            return false;
        }

        @Override // com.att.iqi.IIQIService
        public boolean shouldSubmitMetric(Metric.ID id) throws RemoteException {
            return false;
        }

        @Override // com.att.iqi.IIQIService
        public void submitMetric(Metric metric) throws RemoteException {}

        @Override // com.att.iqi.IIQIService
        public void unregisterMetricQueryCallback(
                Metric.ID id, IMetricQueryCallback iMetricQueryCallback) throws RemoteException {}

        @Override // com.att.iqi.IIQIService
        public void unregisterMetricSourcingCallback(
                Metric.ID id, IMetricSourcingCallback iMetricSourcingCallback)
                throws RemoteException {}

        @Override // com.att.iqi.IIQIService
        public void unregisterProfileChangedCallback(
                IProfileChangedCallback iProfileChangedCallback) throws RemoteException {}
    }

    public abstract class Stub extends Binder implements IIQIService {
        static final int TRANSACTION_disableService = 12;
        static final int TRANSACTION_forceStopService = 11;
        static final int TRANSACTION_getTimestamp = 10;
        static final int TRANSACTION_registerMetricQueryCallback = 3;
        static final int TRANSACTION_registerMetricSourcingCallback = 5;
        static final int TRANSACTION_registerProfileChangedCallback = 7;
        static final int TRANSACTION_reportKeyCode = 9;
        static final int TRANSACTION_setUnlockCode = 13;
        static final int TRANSACTION_shouldSubmitMetric = 1;
        static final int TRANSACTION_submitMetric = 2;
        static final int TRANSACTION_unregisterMetricQueryCallback = 4;
        static final int TRANSACTION_unregisterMetricSourcingCallback = 6;
        static final int TRANSACTION_unregisterProfileChangedCallback = 8;

        class Proxy implements IIQIService {
            private IBinder mRemote;

            public Proxy(IBinder iBinder) {
                this.mRemote = iBinder;
            }

            @Override // android.os.IInterface
            public IBinder asBinder() {
                return this.mRemote;
            }

            @Override // com.att.iqi.IIQIService
            public void disableService() throws RemoteException {
                Parcel obtain = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(IIQIService.DESCRIPTOR);
                    this.mRemote.transact(12, obtain, null, 1);
                } finally {
                    obtain.recycle();
                }
            }

            @Override // com.att.iqi.IIQIService
            public void forceStopService() throws RemoteException {
                Parcel obtain = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(IIQIService.DESCRIPTOR);
                    this.mRemote.transact(11, obtain, null, 1);
                } finally {
                    obtain.recycle();
                }
            }

            public String getInterfaceDescriptor() {
                return IIQIService.DESCRIPTOR;
            }

            @Override // com.att.iqi.IIQIService
            public long getTimestamp() throws RemoteException {
                Parcel obtain = Parcel.obtain();
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(IIQIService.DESCRIPTOR);
                    this.mRemote.transact(10, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readLong();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.att.iqi.IIQIService
            public void registerMetricQueryCallback(
                    Metric.ID id, IMetricQueryCallback iMetricQueryCallback)
                    throws RemoteException {
                Parcel obtain = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(IIQIService.DESCRIPTOR);
                    _Parcel.writeTypedObject(obtain, id, 0);
                    obtain.writeStrongInterface(iMetricQueryCallback);
                    this.mRemote.transact(3, obtain, null, 1);
                } finally {
                    obtain.recycle();
                }
            }

            @Override // com.att.iqi.IIQIService
            public void registerMetricSourcingCallback(
                    Metric.ID id, IMetricSourcingCallback iMetricSourcingCallback)
                    throws RemoteException {
                Parcel obtain = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(IIQIService.DESCRIPTOR);
                    _Parcel.writeTypedObject(obtain, id, 0);
                    obtain.writeStrongInterface(iMetricSourcingCallback);
                    this.mRemote.transact(5, obtain, null, 1);
                } finally {
                    obtain.recycle();
                }
            }

            @Override // com.att.iqi.IIQIService
            public void registerProfileChangedCallback(
                    IProfileChangedCallback iProfileChangedCallback) throws RemoteException {
                Parcel obtain = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(IIQIService.DESCRIPTOR);
                    obtain.writeStrongInterface(iProfileChangedCallback);
                    this.mRemote.transact(7, obtain, null, 1);
                } finally {
                    obtain.recycle();
                }
            }

            @Override // com.att.iqi.IIQIService
            public boolean reportKeyCode(byte[] bArr) throws RemoteException {
                Parcel obtain = Parcel.obtain();
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(IIQIService.DESCRIPTOR);
                    obtain.writeByteArray(bArr);
                    this.mRemote.transact(9, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readInt() != 0;
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.att.iqi.IIQIService
            public boolean setUnlockCode(long j) throws RemoteException {
                Parcel obtain = Parcel.obtain();
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(IIQIService.DESCRIPTOR);
                    obtain.writeLong(j);
                    this.mRemote.transact(13, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readInt() != 0;
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.att.iqi.IIQIService
            public boolean shouldSubmitMetric(Metric.ID id) throws RemoteException {
                Parcel obtain = Parcel.obtain();
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(IIQIService.DESCRIPTOR);
                    _Parcel.writeTypedObject(obtain, id, 0);
                    this.mRemote.transact(1, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readInt() != 0;
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.att.iqi.IIQIService
            public void submitMetric(Metric metric) throws RemoteException {
                Parcel obtain = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(IIQIService.DESCRIPTOR);
                    _Parcel.writeTypedObject(obtain, metric, 0);
                    this.mRemote.transact(2, obtain, null, 1);
                } finally {
                    obtain.recycle();
                }
            }

            @Override // com.att.iqi.IIQIService
            public void unregisterMetricQueryCallback(
                    Metric.ID id, IMetricQueryCallback iMetricQueryCallback)
                    throws RemoteException {
                Parcel obtain = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(IIQIService.DESCRIPTOR);
                    _Parcel.writeTypedObject(obtain, id, 0);
                    obtain.writeStrongInterface(iMetricQueryCallback);
                    this.mRemote.transact(4, obtain, null, 1);
                } finally {
                    obtain.recycle();
                }
            }

            @Override // com.att.iqi.IIQIService
            public void unregisterMetricSourcingCallback(
                    Metric.ID id, IMetricSourcingCallback iMetricSourcingCallback)
                    throws RemoteException {
                Parcel obtain = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(IIQIService.DESCRIPTOR);
                    _Parcel.writeTypedObject(obtain, id, 0);
                    obtain.writeStrongInterface(iMetricSourcingCallback);
                    this.mRemote.transact(6, obtain, null, 1);
                } finally {
                    obtain.recycle();
                }
            }

            @Override // com.att.iqi.IIQIService
            public void unregisterProfileChangedCallback(
                    IProfileChangedCallback iProfileChangedCallback) throws RemoteException {
                Parcel obtain = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(IIQIService.DESCRIPTOR);
                    obtain.writeStrongInterface(iProfileChangedCallback);
                    this.mRemote.transact(8, obtain, null, 1);
                } finally {
                    obtain.recycle();
                }
            }
        }

        public Stub() {
            attachInterface(this, IIQIService.DESCRIPTOR);
        }

        public static IIQIService asInterface(IBinder iBinder) {
            if (iBinder == null) {
                return null;
            }
            IInterface queryLocalInterface = iBinder.queryLocalInterface(IIQIService.DESCRIPTOR);
            return (queryLocalInterface == null || !(queryLocalInterface instanceof IIQIService))
                    ? new Proxy(iBinder)
                    : (IIQIService) queryLocalInterface;
        }

        @Override // android.os.IInterface
        public IBinder asBinder() {
            return this;
        }

        @Override // android.os.Binder
        public boolean onTransact(int i, Parcel parcel, Parcel parcel2, int i2)
                throws RemoteException {
            if (i >= 1 && i <= 16777215) {
                parcel.enforceInterface(IIQIService.DESCRIPTOR);
            }
            if (i == 1598968902) {
                parcel2.writeString(IIQIService.DESCRIPTOR);
                return true;
            }
            switch (i) {
                case 1:
                    boolean shouldSubmitMetric =
                            shouldSubmitMetric(
                                    (Metric.ID) _Parcel.readTypedObject(parcel, Metric.ID.CREATOR));
                    parcel2.writeNoException();
                    parcel2.writeInt(shouldSubmitMetric ? 1 : 0);
                    return true;
                case 2:
                    submitMetric((Metric) _Parcel.readTypedObject(parcel, Metric.CREATOR));
                    return true;
                case 3:
                    registerMetricQueryCallback(
                            (Metric.ID) _Parcel.readTypedObject(parcel, Metric.ID.CREATOR),
                            IMetricQueryCallback.Stub.asInterface(parcel.readStrongBinder()));
                    return true;
                case 4:
                    unregisterMetricQueryCallback(
                            (Metric.ID) _Parcel.readTypedObject(parcel, Metric.ID.CREATOR),
                            IMetricQueryCallback.Stub.asInterface(parcel.readStrongBinder()));
                    return true;
                case 5:
                    registerMetricSourcingCallback(
                            (Metric.ID) _Parcel.readTypedObject(parcel, Metric.ID.CREATOR),
                            IMetricSourcingCallback.Stub.asInterface(parcel.readStrongBinder()));
                    return true;
                case 6:
                    unregisterMetricSourcingCallback(
                            (Metric.ID) _Parcel.readTypedObject(parcel, Metric.ID.CREATOR),
                            IMetricSourcingCallback.Stub.asInterface(parcel.readStrongBinder()));
                    return true;
                case 7:
                    registerProfileChangedCallback(
                            IProfileChangedCallback.Stub.asInterface(parcel.readStrongBinder()));
                    return true;
                case 8:
                    unregisterProfileChangedCallback(
                            IProfileChangedCallback.Stub.asInterface(parcel.readStrongBinder()));
                    return true;
                case 9:
                    boolean reportKeyCode = reportKeyCode(parcel.createByteArray());
                    parcel2.writeNoException();
                    parcel2.writeInt(reportKeyCode ? 1 : 0);
                    return true;
                case 10:
                    long timestamp = getTimestamp();
                    parcel2.writeNoException();
                    parcel2.writeLong(timestamp);
                    return true;
                case 11:
                    forceStopService();
                    return true;
                case 12:
                    disableService();
                    return true;
                case 13:
                    boolean unlockCode = setUnlockCode(parcel.readLong());
                    parcel2.writeNoException();
                    parcel2.writeInt(unlockCode ? 1 : 0);
                    return true;
                default:
                    return super.onTransact(i, parcel, parcel2, i2);
            }
        }
    }

    public class _Parcel {
        /* JADX INFO: Access modifiers changed from: private */
        public static Object readTypedObject(Parcel parcel, Parcelable.Creator creator) {
            if (parcel.readInt() != 0) {
                return creator.createFromParcel(parcel);
            }
            return null;
        }

        /* JADX INFO: Access modifiers changed from: private */
        public static void writeTypedObject(Parcel parcel, Parcelable parcelable, int i) {
            if (parcelable == null) {
                parcel.writeInt(0);
            } else {
                parcel.writeInt(1);
                parcelable.writeToParcel(parcel, i);
            }
        }
    }

    void disableService() throws RemoteException;

    void forceStopService() throws RemoteException;

    long getTimestamp() throws RemoteException;

    void registerMetricQueryCallback(Metric.ID id, IMetricQueryCallback iMetricQueryCallback)
            throws RemoteException;

    void registerMetricSourcingCallback(
            Metric.ID id, IMetricSourcingCallback iMetricSourcingCallback) throws RemoteException;

    void registerProfileChangedCallback(IProfileChangedCallback iProfileChangedCallback)
            throws RemoteException;

    boolean reportKeyCode(byte[] bArr) throws RemoteException;

    boolean setUnlockCode(long j) throws RemoteException;

    boolean shouldSubmitMetric(Metric.ID id) throws RemoteException;

    void submitMetric(Metric metric) throws RemoteException;

    void unregisterMetricQueryCallback(Metric.ID id, IMetricQueryCallback iMetricQueryCallback)
            throws RemoteException;

    void unregisterMetricSourcingCallback(
            Metric.ID id, IMetricSourcingCallback iMetricSourcingCallback) throws RemoteException;

    void unregisterProfileChangedCallback(IProfileChangedCallback iProfileChangedCallback)
            throws RemoteException;
}
