package android.app.job;

import android.app.Notification;
import android.os.Binder;
import android.os.IBinder;
import android.os.IInterface;
import android.os.Parcel;
import android.os.RemoteException;

/* loaded from: classes.dex */
public interface IJobCallback extends IInterface {

    public static class Default implements IJobCallback {
        @Override // android.app.job.IJobCallback
        public void acknowledgeGetTransferredDownloadBytesMessage(int i, int i2, long j) throws RemoteException {
        }

        @Override // android.app.job.IJobCallback
        public void acknowledgeGetTransferredUploadBytesMessage(int i, int i2, long j) throws RemoteException {
        }

        @Override // android.app.job.IJobCallback
        public void acknowledgeStartMessage(int i, boolean z) throws RemoteException {
        }

        @Override // android.app.job.IJobCallback
        public void acknowledgeStopMessage(int i, boolean z) throws RemoteException {
        }

        @Override // android.os.IInterface
        public IBinder asBinder() {
            return null;
        }

        @Override // android.app.job.IJobCallback
        public boolean completeWork(int i, int i2) throws RemoteException {
            return false;
        }

        @Override // android.app.job.IJobCallback
        public JobWorkItem dequeueWork(int i) throws RemoteException {
            return null;
        }

        @Override // android.app.job.IJobCallback
        public void handleAbandonedJob(int i) throws RemoteException {
        }

        @Override // android.app.job.IJobCallback
        public void jobFinished(int i, boolean z) throws RemoteException {
        }

        @Override // android.app.job.IJobCallback
        public void setNotification(int i, int i2, Notification notification, int i3) throws RemoteException {
        }

        @Override // android.app.job.IJobCallback
        public void updateEstimatedNetworkBytes(int i, JobWorkItem jobWorkItem, long j, long j2) throws RemoteException {
        }

        @Override // android.app.job.IJobCallback
        public void updateTransferredNetworkBytes(int i, JobWorkItem jobWorkItem, long j, long j2) throws RemoteException {
        }
    }

    void acknowledgeGetTransferredDownloadBytesMessage(int i, int i2, long j) throws RemoteException;

    void acknowledgeGetTransferredUploadBytesMessage(int i, int i2, long j) throws RemoteException;

    void acknowledgeStartMessage(int i, boolean z) throws RemoteException;

    void acknowledgeStopMessage(int i, boolean z) throws RemoteException;

    boolean completeWork(int i, int i2) throws RemoteException;

    JobWorkItem dequeueWork(int i) throws RemoteException;

    void handleAbandonedJob(int i) throws RemoteException;

    void jobFinished(int i, boolean z) throws RemoteException;

    void setNotification(int i, int i2, Notification notification, int i3) throws RemoteException;

    void updateEstimatedNetworkBytes(int i, JobWorkItem jobWorkItem, long j, long j2) throws RemoteException;

    void updateTransferredNetworkBytes(int i, JobWorkItem jobWorkItem, long j, long j2) throws RemoteException;

    public static abstract class Stub extends Binder implements IJobCallback {
        public static final String DESCRIPTOR = "android.app.job.IJobCallback";
        static final int TRANSACTION_acknowledgeGetTransferredDownloadBytesMessage = 1;
        static final int TRANSACTION_acknowledgeGetTransferredUploadBytesMessage = 2;
        static final int TRANSACTION_acknowledgeStartMessage = 3;
        static final int TRANSACTION_acknowledgeStopMessage = 4;
        static final int TRANSACTION_completeWork = 6;
        static final int TRANSACTION_dequeueWork = 5;
        static final int TRANSACTION_handleAbandonedJob = 8;
        static final int TRANSACTION_jobFinished = 7;
        static final int TRANSACTION_setNotification = 11;
        static final int TRANSACTION_updateEstimatedNetworkBytes = 9;
        static final int TRANSACTION_updateTransferredNetworkBytes = 10;

        @Override // android.os.IInterface
        public IBinder asBinder() {
            return this;
        }

        @Override // android.os.Binder
        public int getMaxTransactionId() {
            return 10;
        }

        public Stub() {
            attachInterface(this, DESCRIPTOR);
        }

        public static IJobCallback asInterface(IBinder iBinder) {
            if (iBinder == null) {
                return null;
            }
            IInterface queryLocalInterface = iBinder.queryLocalInterface(DESCRIPTOR);
            if (queryLocalInterface != null && (queryLocalInterface instanceof IJobCallback)) {
                return (IJobCallback) queryLocalInterface;
            }
            return new Proxy(iBinder);
        }

        public static String getDefaultTransactionName(int i) {
            switch (i) {
                case 1:
                    return "acknowledgeGetTransferredDownloadBytesMessage";
                case 2:
                    return "acknowledgeGetTransferredUploadBytesMessage";
                case 3:
                    return "acknowledgeStartMessage";
                case 4:
                    return "acknowledgeStopMessage";
                case 5:
                    return "dequeueWork";
                case 6:
                    return "completeWork";
                case 7:
                    return "jobFinished";
                case 8:
                    return "handleAbandonedJob";
                case 9:
                    return "updateEstimatedNetworkBytes";
                case 10:
                    return "updateTransferredNetworkBytes";
                case 11:
                    return "setNotification";
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
                parcel.enforceInterface(DESCRIPTOR);
            }
            if (i == 1598968902) {
                parcel2.writeString(DESCRIPTOR);
                return true;
            }
            switch (i) {
                case 1:
                    int readInt = parcel.readInt();
                    int readInt2 = parcel.readInt();
                    long readLong = parcel.readLong();
                    parcel.enforceNoDataAvail();
                    acknowledgeGetTransferredDownloadBytesMessage(readInt, readInt2, readLong);
                    parcel2.writeNoException();
                    return true;
                case 2:
                    int readInt3 = parcel.readInt();
                    int readInt4 = parcel.readInt();
                    long readLong2 = parcel.readLong();
                    parcel.enforceNoDataAvail();
                    acknowledgeGetTransferredUploadBytesMessage(readInt3, readInt4, readLong2);
                    parcel2.writeNoException();
                    return true;
                case 3:
                    int readInt5 = parcel.readInt();
                    boolean readBoolean = parcel.readBoolean();
                    parcel.enforceNoDataAvail();
                    acknowledgeStartMessage(readInt5, readBoolean);
                    parcel2.writeNoException();
                    return true;
                case 4:
                    int readInt6 = parcel.readInt();
                    boolean readBoolean2 = parcel.readBoolean();
                    parcel.enforceNoDataAvail();
                    acknowledgeStopMessage(readInt6, readBoolean2);
                    parcel2.writeNoException();
                    return true;
                case 5:
                    int readInt7 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    JobWorkItem dequeueWork = dequeueWork(readInt7);
                    parcel2.writeNoException();
                    parcel2.writeTypedObject(dequeueWork, 1);
                    return true;
                case 6:
                    int readInt8 = parcel.readInt();
                    int readInt9 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    boolean completeWork = completeWork(readInt8, readInt9);
                    parcel2.writeNoException();
                    parcel2.writeBoolean(completeWork);
                    return true;
                case 7:
                    int readInt10 = parcel.readInt();
                    boolean readBoolean3 = parcel.readBoolean();
                    parcel.enforceNoDataAvail();
                    jobFinished(readInt10, readBoolean3);
                    parcel2.writeNoException();
                    return true;
                case 8:
                    int readInt11 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    handleAbandonedJob(readInt11);
                    parcel2.writeNoException();
                    return true;
                case 9:
                    int readInt12 = parcel.readInt();
                    JobWorkItem jobWorkItem = (JobWorkItem) parcel.readTypedObject(JobWorkItem.CREATOR);
                    long readLong3 = parcel.readLong();
                    long readLong4 = parcel.readLong();
                    parcel.enforceNoDataAvail();
                    updateEstimatedNetworkBytes(readInt12, jobWorkItem, readLong3, readLong4);
                    parcel2.writeNoException();
                    return true;
                case 10:
                    int readInt13 = parcel.readInt();
                    JobWorkItem jobWorkItem2 = (JobWorkItem) parcel.readTypedObject(JobWorkItem.CREATOR);
                    long readLong5 = parcel.readLong();
                    long readLong6 = parcel.readLong();
                    parcel.enforceNoDataAvail();
                    updateTransferredNetworkBytes(readInt13, jobWorkItem2, readLong5, readLong6);
                    parcel2.writeNoException();
                    return true;
                case 11:
                    int readInt14 = parcel.readInt();
                    int readInt15 = parcel.readInt();
                    Notification notification = (Notification) parcel.readTypedObject(Notification.CREATOR);
                    int readInt16 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    setNotification(readInt14, readInt15, notification, readInt16);
                    parcel2.writeNoException();
                    return true;
                default:
                    return super.onTransact(i, parcel, parcel2, i2);
            }
        }

        private static class Proxy implements IJobCallback {
            private IBinder mRemote;

            Proxy(IBinder iBinder) {
                this.mRemote = iBinder;
            }

            @Override // android.os.IInterface
            public IBinder asBinder() {
                return this.mRemote;
            }

            public String getInterfaceDescriptor() {
                return Stub.DESCRIPTOR;
            }

            @Override // android.app.job.IJobCallback
            public void acknowledgeGetTransferredDownloadBytesMessage(int i, int i2, long j) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    obtain.writeInt(i);
                    obtain.writeInt(i2);
                    obtain.writeLong(j);
                    this.mRemote.transact(1, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.app.job.IJobCallback
            public void acknowledgeGetTransferredUploadBytesMessage(int i, int i2, long j) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    obtain.writeInt(i);
                    obtain.writeInt(i2);
                    obtain.writeLong(j);
                    this.mRemote.transact(2, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.app.job.IJobCallback
            public void acknowledgeStartMessage(int i, boolean z) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    obtain.writeInt(i);
                    obtain.writeBoolean(z);
                    this.mRemote.transact(3, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.app.job.IJobCallback
            public void acknowledgeStopMessage(int i, boolean z) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    obtain.writeInt(i);
                    obtain.writeBoolean(z);
                    this.mRemote.transact(4, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.app.job.IJobCallback
            public JobWorkItem dequeueWork(int i) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    obtain.writeInt(i);
                    this.mRemote.transact(5, obtain, obtain2, 0);
                    obtain2.readException();
                    return (JobWorkItem) obtain2.readTypedObject(JobWorkItem.CREATOR);
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.app.job.IJobCallback
            public boolean completeWork(int i, int i2) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    obtain.writeInt(i);
                    obtain.writeInt(i2);
                    this.mRemote.transact(6, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readBoolean();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.app.job.IJobCallback
            public void jobFinished(int i, boolean z) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    obtain.writeInt(i);
                    obtain.writeBoolean(z);
                    this.mRemote.transact(7, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.app.job.IJobCallback
            public void handleAbandonedJob(int i) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    obtain.writeInt(i);
                    this.mRemote.transact(8, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.app.job.IJobCallback
            public void updateEstimatedNetworkBytes(int i, JobWorkItem jobWorkItem, long j, long j2) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    obtain.writeInt(i);
                    obtain.writeTypedObject(jobWorkItem, 0);
                    obtain.writeLong(j);
                    obtain.writeLong(j2);
                    this.mRemote.transact(9, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.app.job.IJobCallback
            public void updateTransferredNetworkBytes(int i, JobWorkItem jobWorkItem, long j, long j2) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    obtain.writeInt(i);
                    obtain.writeTypedObject(jobWorkItem, 0);
                    obtain.writeLong(j);
                    obtain.writeLong(j2);
                    this.mRemote.transact(10, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.app.job.IJobCallback
            public void setNotification(int i, int i2, Notification notification, int i3) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    obtain.writeInt(i);
                    obtain.writeInt(i2);
                    obtain.writeTypedObject(notification, 0);
                    obtain.writeInt(i3);
                    this.mRemote.transact(11, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }
        }
    }
}
