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
            IInterface iInterfaceQueryLocalInterface = iBinder.queryLocalInterface(DESCRIPTOR);
            if (iInterfaceQueryLocalInterface != null && (iInterfaceQueryLocalInterface instanceof IJobCallback)) {
                return (IJobCallback) iInterfaceQueryLocalInterface;
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
                    int i3 = parcel.readInt();
                    int i4 = parcel.readInt();
                    long j = parcel.readLong();
                    parcel.enforceNoDataAvail();
                    acknowledgeGetTransferredDownloadBytesMessage(i3, i4, j);
                    parcel2.writeNoException();
                    return true;
                case 2:
                    int i5 = parcel.readInt();
                    int i6 = parcel.readInt();
                    long j2 = parcel.readLong();
                    parcel.enforceNoDataAvail();
                    acknowledgeGetTransferredUploadBytesMessage(i5, i6, j2);
                    parcel2.writeNoException();
                    return true;
                case 3:
                    int i7 = parcel.readInt();
                    boolean z = parcel.readBoolean();
                    parcel.enforceNoDataAvail();
                    acknowledgeStartMessage(i7, z);
                    parcel2.writeNoException();
                    return true;
                case 4:
                    int i8 = parcel.readInt();
                    boolean z2 = parcel.readBoolean();
                    parcel.enforceNoDataAvail();
                    acknowledgeStopMessage(i8, z2);
                    parcel2.writeNoException();
                    return true;
                case 5:
                    int i9 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    JobWorkItem jobWorkItemDequeueWork = dequeueWork(i9);
                    parcel2.writeNoException();
                    parcel2.writeTypedObject(jobWorkItemDequeueWork, 1);
                    return true;
                case 6:
                    int i10 = parcel.readInt();
                    int i11 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    boolean zCompleteWork = completeWork(i10, i11);
                    parcel2.writeNoException();
                    parcel2.writeBoolean(zCompleteWork);
                    return true;
                case 7:
                    int i12 = parcel.readInt();
                    boolean z3 = parcel.readBoolean();
                    parcel.enforceNoDataAvail();
                    jobFinished(i12, z3);
                    parcel2.writeNoException();
                    return true;
                case 8:
                    int i13 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    handleAbandonedJob(i13);
                    parcel2.writeNoException();
                    return true;
                case 9:
                    int i14 = parcel.readInt();
                    JobWorkItem jobWorkItem = (JobWorkItem) parcel.readTypedObject(JobWorkItem.CREATOR);
                    long j3 = parcel.readLong();
                    long j4 = parcel.readLong();
                    parcel.enforceNoDataAvail();
                    updateEstimatedNetworkBytes(i14, jobWorkItem, j3, j4);
                    parcel2.writeNoException();
                    return true;
                case 10:
                    int i15 = parcel.readInt();
                    JobWorkItem jobWorkItem2 = (JobWorkItem) parcel.readTypedObject(JobWorkItem.CREATOR);
                    long j5 = parcel.readLong();
                    long j6 = parcel.readLong();
                    parcel.enforceNoDataAvail();
                    updateTransferredNetworkBytes(i15, jobWorkItem2, j5, j6);
                    parcel2.writeNoException();
                    return true;
                case 11:
                    int i16 = parcel.readInt();
                    int i17 = parcel.readInt();
                    Notification notification = (Notification) parcel.readTypedObject(Notification.CREATOR);
                    int i18 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    setNotification(i16, i17, notification, i18);
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
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcelObtain.writeInt(i);
                    parcelObtain.writeInt(i2);
                    parcelObtain.writeLong(j);
                    this.mRemote.transact(1, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.app.job.IJobCallback
            public void acknowledgeGetTransferredUploadBytesMessage(int i, int i2, long j) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcelObtain.writeInt(i);
                    parcelObtain.writeInt(i2);
                    parcelObtain.writeLong(j);
                    this.mRemote.transact(2, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.app.job.IJobCallback
            public void acknowledgeStartMessage(int i, boolean z) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcelObtain.writeInt(i);
                    parcelObtain.writeBoolean(z);
                    this.mRemote.transact(3, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.app.job.IJobCallback
            public void acknowledgeStopMessage(int i, boolean z) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcelObtain.writeInt(i);
                    parcelObtain.writeBoolean(z);
                    this.mRemote.transact(4, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.app.job.IJobCallback
            public JobWorkItem dequeueWork(int i) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcelObtain.writeInt(i);
                    this.mRemote.transact(5, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return (JobWorkItem) parcelObtain2.readTypedObject(JobWorkItem.CREATOR);
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.app.job.IJobCallback
            public boolean completeWork(int i, int i2) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcelObtain.writeInt(i);
                    parcelObtain.writeInt(i2);
                    this.mRemote.transact(6, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return parcelObtain2.readBoolean();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.app.job.IJobCallback
            public void jobFinished(int i, boolean z) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcelObtain.writeInt(i);
                    parcelObtain.writeBoolean(z);
                    this.mRemote.transact(7, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.app.job.IJobCallback
            public void handleAbandonedJob(int i) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcelObtain.writeInt(i);
                    this.mRemote.transact(8, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.app.job.IJobCallback
            public void updateEstimatedNetworkBytes(int i, JobWorkItem jobWorkItem, long j, long j2) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcelObtain.writeInt(i);
                    parcelObtain.writeTypedObject(jobWorkItem, 0);
                    parcelObtain.writeLong(j);
                    parcelObtain.writeLong(j2);
                    this.mRemote.transact(9, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.app.job.IJobCallback
            public void updateTransferredNetworkBytes(int i, JobWorkItem jobWorkItem, long j, long j2) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcelObtain.writeInt(i);
                    parcelObtain.writeTypedObject(jobWorkItem, 0);
                    parcelObtain.writeLong(j);
                    parcelObtain.writeLong(j2);
                    this.mRemote.transact(10, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.app.job.IJobCallback
            public void setNotification(int i, int i2, Notification notification, int i3) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcelObtain.writeInt(i);
                    parcelObtain.writeInt(i2);
                    parcelObtain.writeTypedObject(notification, 0);
                    parcelObtain.writeInt(i3);
                    this.mRemote.transact(11, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }
        }
    }
}
