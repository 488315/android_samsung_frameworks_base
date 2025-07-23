package android.app.job;

import android.os.Binder;
import android.os.IBinder;
import android.os.IInterface;
import android.os.Parcel;
import android.os.RemoteException;

/* loaded from: classes.dex */
public interface IJobService extends IInterface {

    public static class Default implements IJobService {
        @Override // android.os.IInterface
        public IBinder asBinder() {
            return null;
        }

        @Override // android.app.job.IJobService
        public void getTransferredDownloadBytes(JobParameters jobParameters, JobWorkItem jobWorkItem) throws RemoteException {
        }

        @Override // android.app.job.IJobService
        public void getTransferredUploadBytes(JobParameters jobParameters, JobWorkItem jobWorkItem) throws RemoteException {
        }

        @Override // android.app.job.IJobService
        public void onNetworkChanged(JobParameters jobParameters) throws RemoteException {
        }

        @Override // android.app.job.IJobService
        public void startJob(JobParameters jobParameters) throws RemoteException {
        }

        @Override // android.app.job.IJobService
        public void stopJob(JobParameters jobParameters) throws RemoteException {
        }
    }

    void getTransferredDownloadBytes(JobParameters jobParameters, JobWorkItem jobWorkItem) throws RemoteException;

    void getTransferredUploadBytes(JobParameters jobParameters, JobWorkItem jobWorkItem) throws RemoteException;

    void onNetworkChanged(JobParameters jobParameters) throws RemoteException;

    void startJob(JobParameters jobParameters) throws RemoteException;

    void stopJob(JobParameters jobParameters) throws RemoteException;

    public static abstract class Stub extends Binder implements IJobService {
        public static final String DESCRIPTOR = "android.app.job.IJobService";
        static final int TRANSACTION_getTransferredDownloadBytes = 4;
        static final int TRANSACTION_getTransferredUploadBytes = 5;
        static final int TRANSACTION_onNetworkChanged = 3;
        static final int TRANSACTION_startJob = 1;
        static final int TRANSACTION_stopJob = 2;

        @Override // android.os.IInterface
        public IBinder asBinder() {
            return this;
        }

        @Override // android.os.Binder
        public int getMaxTransactionId() {
            return 4;
        }

        public Stub() {
            attachInterface(this, DESCRIPTOR);
        }

        public static IJobService asInterface(IBinder iBinder) {
            if (iBinder == null) {
                return null;
            }
            IInterface queryLocalInterface = iBinder.queryLocalInterface(DESCRIPTOR);
            if (queryLocalInterface != null && (queryLocalInterface instanceof IJobService)) {
                return (IJobService) queryLocalInterface;
            }
            return new Proxy(iBinder);
        }

        public static String getDefaultTransactionName(int i) {
            if (i == 1) {
                return "startJob";
            }
            if (i == 2) {
                return "stopJob";
            }
            if (i == 3) {
                return "onNetworkChanged";
            }
            if (i == 4) {
                return "getTransferredDownloadBytes";
            }
            if (i != 5) {
                return null;
            }
            return "getTransferredUploadBytes";
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
            if (i == 1) {
                JobParameters jobParameters = (JobParameters) parcel.readTypedObject(JobParameters.CREATOR);
                parcel.enforceNoDataAvail();
                startJob(jobParameters);
            } else if (i == 2) {
                JobParameters jobParameters2 = (JobParameters) parcel.readTypedObject(JobParameters.CREATOR);
                parcel.enforceNoDataAvail();
                stopJob(jobParameters2);
            } else if (i == 3) {
                JobParameters jobParameters3 = (JobParameters) parcel.readTypedObject(JobParameters.CREATOR);
                parcel.enforceNoDataAvail();
                onNetworkChanged(jobParameters3);
            } else if (i == 4) {
                JobParameters jobParameters4 = (JobParameters) parcel.readTypedObject(JobParameters.CREATOR);
                JobWorkItem jobWorkItem = (JobWorkItem) parcel.readTypedObject(JobWorkItem.CREATOR);
                parcel.enforceNoDataAvail();
                getTransferredDownloadBytes(jobParameters4, jobWorkItem);
            } else if (i == 5) {
                JobParameters jobParameters5 = (JobParameters) parcel.readTypedObject(JobParameters.CREATOR);
                JobWorkItem jobWorkItem2 = (JobWorkItem) parcel.readTypedObject(JobWorkItem.CREATOR);
                parcel.enforceNoDataAvail();
                getTransferredUploadBytes(jobParameters5, jobWorkItem2);
            } else {
                return super.onTransact(i, parcel, parcel2, i2);
            }
            return true;
        }

        private static class Proxy implements IJobService {
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

            @Override // android.app.job.IJobService
            public void startJob(JobParameters jobParameters) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    obtain.writeTypedObject(jobParameters, 0);
                    this.mRemote.transact(1, obtain, null, 1);
                } finally {
                    obtain.recycle();
                }
            }

            @Override // android.app.job.IJobService
            public void stopJob(JobParameters jobParameters) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    obtain.writeTypedObject(jobParameters, 0);
                    this.mRemote.transact(2, obtain, null, 1);
                } finally {
                    obtain.recycle();
                }
            }

            @Override // android.app.job.IJobService
            public void onNetworkChanged(JobParameters jobParameters) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    obtain.writeTypedObject(jobParameters, 0);
                    this.mRemote.transact(3, obtain, null, 1);
                } finally {
                    obtain.recycle();
                }
            }

            @Override // android.app.job.IJobService
            public void getTransferredDownloadBytes(JobParameters jobParameters, JobWorkItem jobWorkItem) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    obtain.writeTypedObject(jobParameters, 0);
                    obtain.writeTypedObject(jobWorkItem, 0);
                    this.mRemote.transact(4, obtain, null, 1);
                } finally {
                    obtain.recycle();
                }
            }

            @Override // android.app.job.IJobService
            public void getTransferredUploadBytes(JobParameters jobParameters, JobWorkItem jobWorkItem) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    obtain.writeTypedObject(jobParameters, 0);
                    obtain.writeTypedObject(jobWorkItem, 0);
                    this.mRemote.transact(5, obtain, null, 1);
                } finally {
                    obtain.recycle();
                }
            }
        }
    }
}
