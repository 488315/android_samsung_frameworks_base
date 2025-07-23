package android.app.job;

import android.Manifest;
import android.app.ActivityThread;
import android.app.job.IJobScheduler;
import android.app.job.IUserVisibleJobObserver;
import android.content.pm.ParceledListSlice;
import android.os.Binder;
import android.os.IBinder;
import android.os.IInterface;
import android.os.Parcel;
import android.os.PermissionEnforcer;
import android.os.RemoteException;
import android.service.notification.ZenModeConfig;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.function.BiConsumer;
import java.util.function.IntConsumer;
import java.util.stream.IntStream;

/* loaded from: classes.dex */
public interface IJobScheduler extends IInterface {

    public static class Default implements IJobScheduler {
        @Override // android.os.IInterface
        public IBinder asBinder() {
            return null;
        }

        @Override // android.app.job.IJobScheduler
        public boolean canRunUserInitiatedJobs(String str) throws RemoteException {
            return false;
        }

        @Override // android.app.job.IJobScheduler
        public void cancel(String str, int i) throws RemoteException {
        }

        @Override // android.app.job.IJobScheduler
        public void cancelAll() throws RemoteException {
        }

        @Override // android.app.job.IJobScheduler
        public void cancelAllInNamespace(String str) throws RemoteException {
        }

        @Override // android.app.job.IJobScheduler
        public int enqueue(String str, JobInfo jobInfo, JobWorkItem jobWorkItem) throws RemoteException {
            return 0;
        }

        @Override // android.app.job.IJobScheduler
        public ParceledListSlice getAllJobSnapshots() throws RemoteException {
            return null;
        }

        @Override // android.app.job.IJobScheduler
        public Map<String, ParceledListSlice<JobInfo>> getAllPendingJobs() throws RemoteException {
            return null;
        }

        @Override // android.app.job.IJobScheduler
        public ParceledListSlice<JobInfo> getAllPendingJobsInNamespace(String str) throws RemoteException {
            return null;
        }

        @Override // android.app.job.IJobScheduler
        public JobInfo getPendingJob(String str, int i) throws RemoteException {
            return null;
        }

        @Override // android.app.job.IJobScheduler
        public int getPendingJobReason(String str, int i) throws RemoteException {
            return 0;
        }

        @Override // android.app.job.IJobScheduler
        public int[] getPendingJobReasons(String str, int i) throws RemoteException {
            return null;
        }

        @Override // android.app.job.IJobScheduler
        public List<PendingJobReasonsInfo> getPendingJobReasonsHistory(String str, int i) throws RemoteException {
            return null;
        }

        @Override // android.app.job.IJobScheduler
        public List<JobInfo> getStartedJobs() throws RemoteException {
            return null;
        }

        @Override // android.app.job.IJobScheduler
        public boolean hasRunUserInitiatedJobsPermission(String str, int i) throws RemoteException {
            return false;
        }

        @Override // android.app.job.IJobScheduler
        public void notePendingUserRequestedAppStop(String str, int i, String str2) throws RemoteException {
        }

        @Override // android.app.job.IJobScheduler
        public void registerUserVisibleJobObserver(IUserVisibleJobObserver iUserVisibleJobObserver) throws RemoteException {
        }

        @Override // android.app.job.IJobScheduler
        public int schedule(String str, JobInfo jobInfo) throws RemoteException {
            return 0;
        }

        @Override // android.app.job.IJobScheduler
        public int scheduleAsPackage(String str, JobInfo jobInfo, String str2, int i, String str3) throws RemoteException {
            return 0;
        }

        @Override // android.app.job.IJobScheduler
        public void unregisterUserVisibleJobObserver(IUserVisibleJobObserver iUserVisibleJobObserver) throws RemoteException {
        }
    }

    boolean canRunUserInitiatedJobs(String str) throws RemoteException;

    void cancel(String str, int i) throws RemoteException;

    void cancelAll() throws RemoteException;

    void cancelAllInNamespace(String str) throws RemoteException;

    int enqueue(String str, JobInfo jobInfo, JobWorkItem jobWorkItem) throws RemoteException;

    ParceledListSlice getAllJobSnapshots() throws RemoteException;

    Map<String, ParceledListSlice<JobInfo>> getAllPendingJobs() throws RemoteException;

    ParceledListSlice<JobInfo> getAllPendingJobsInNamespace(String str) throws RemoteException;

    JobInfo getPendingJob(String str, int i) throws RemoteException;

    int getPendingJobReason(String str, int i) throws RemoteException;

    int[] getPendingJobReasons(String str, int i) throws RemoteException;

    List<PendingJobReasonsInfo> getPendingJobReasonsHistory(String str, int i) throws RemoteException;

    List<JobInfo> getStartedJobs() throws RemoteException;

    boolean hasRunUserInitiatedJobsPermission(String str, int i) throws RemoteException;

    void notePendingUserRequestedAppStop(String str, int i, String str2) throws RemoteException;

    void registerUserVisibleJobObserver(IUserVisibleJobObserver iUserVisibleJobObserver) throws RemoteException;

    int schedule(String str, JobInfo jobInfo) throws RemoteException;

    int scheduleAsPackage(String str, JobInfo jobInfo, String str2, int i, String str3) throws RemoteException;

    void unregisterUserVisibleJobObserver(IUserVisibleJobObserver iUserVisibleJobObserver) throws RemoteException;

    public static abstract class Stub extends Binder implements IJobScheduler {
        public static final String DESCRIPTOR = "android.app.job.IJobScheduler";
        static final int TRANSACTION_canRunUserInitiatedJobs = 13;
        static final int TRANSACTION_cancel = 4;
        static final int TRANSACTION_cancelAll = 5;
        static final int TRANSACTION_cancelAllInNamespace = 6;
        static final int TRANSACTION_enqueue = 2;
        static final int TRANSACTION_getAllJobSnapshots = 16;
        static final int TRANSACTION_getAllPendingJobs = 7;
        static final int TRANSACTION_getAllPendingJobsInNamespace = 8;
        static final int TRANSACTION_getPendingJob = 9;
        static final int TRANSACTION_getPendingJobReason = 10;
        static final int TRANSACTION_getPendingJobReasons = 11;
        static final int TRANSACTION_getPendingJobReasonsHistory = 12;
        static final int TRANSACTION_getStartedJobs = 15;
        static final int TRANSACTION_hasRunUserInitiatedJobsPermission = 14;
        static final int TRANSACTION_notePendingUserRequestedAppStop = 19;
        static final int TRANSACTION_registerUserVisibleJobObserver = 17;
        static final int TRANSACTION_schedule = 1;
        static final int TRANSACTION_scheduleAsPackage = 3;
        static final int TRANSACTION_unregisterUserVisibleJobObserver = 18;
        private final PermissionEnforcer mEnforcer;
        static final String[] PERMISSIONS_registerUserVisibleJobObserver = {Manifest.permission.MANAGE_ACTIVITY_TASKS, Manifest.permission.INTERACT_ACROSS_USERS_FULL};
        static final String[] PERMISSIONS_unregisterUserVisibleJobObserver = {Manifest.permission.MANAGE_ACTIVITY_TASKS, Manifest.permission.INTERACT_ACROSS_USERS_FULL};
        static final String[] PERMISSIONS_notePendingUserRequestedAppStop = {Manifest.permission.MANAGE_ACTIVITY_TASKS, Manifest.permission.INTERACT_ACROSS_USERS_FULL};

        @Override // android.os.IInterface
        public IBinder asBinder() {
            return this;
        }

        @Override // android.os.Binder
        public int getMaxTransactionId() {
            return 18;
        }

        public Stub(PermissionEnforcer permissionEnforcer) {
            attachInterface(this, DESCRIPTOR);
            if (permissionEnforcer == null) {
                throw new IllegalArgumentException("enforcer cannot be null");
            }
            this.mEnforcer = permissionEnforcer;
        }

        @Deprecated
        public Stub() {
            this(PermissionEnforcer.fromContext(ActivityThread.currentActivityThread().getSystemContext()));
        }

        public static IJobScheduler asInterface(IBinder iBinder) {
            if (iBinder == null) {
                return null;
            }
            IInterface queryLocalInterface = iBinder.queryLocalInterface(DESCRIPTOR);
            if (queryLocalInterface != null && (queryLocalInterface instanceof IJobScheduler)) {
                return (IJobScheduler) queryLocalInterface;
            }
            return new Proxy(iBinder);
        }

        public static String getDefaultTransactionName(int i) {
            switch (i) {
                case 1:
                    return ZenModeConfig.SCHEDULE_PATH;
                case 2:
                    return "enqueue";
                case 3:
                    return "scheduleAsPackage";
                case 4:
                    return "cancel";
                case 5:
                    return "cancelAll";
                case 6:
                    return "cancelAllInNamespace";
                case 7:
                    return "getAllPendingJobs";
                case 8:
                    return "getAllPendingJobsInNamespace";
                case 9:
                    return "getPendingJob";
                case 10:
                    return "getPendingJobReason";
                case 11:
                    return "getPendingJobReasons";
                case 12:
                    return "getPendingJobReasonsHistory";
                case 13:
                    return "canRunUserInitiatedJobs";
                case 14:
                    return "hasRunUserInitiatedJobsPermission";
                case 15:
                    return "getStartedJobs";
                case 16:
                    return "getAllJobSnapshots";
                case 17:
                    return "registerUserVisibleJobObserver";
                case 18:
                    return "unregisterUserVisibleJobObserver";
                case 19:
                    return "notePendingUserRequestedAppStop";
                default:
                    return null;
            }
        }

        @Override // android.os.Binder
        public String getTransactionName(int i) {
            return getDefaultTransactionName(i);
        }

        @Override // android.os.Binder
        public boolean onTransact(int i, Parcel parcel, final Parcel parcel2, int i2) throws RemoteException {
            if (i >= 1 && i <= 16777215) {
                parcel.enforceInterface(DESCRIPTOR);
            }
            if (i == 1598968902) {
                parcel2.writeString(DESCRIPTOR);
                return true;
            }
            switch (i) {
                case 1:
                    String readString = parcel.readString();
                    JobInfo jobInfo = (JobInfo) parcel.readTypedObject(JobInfo.CREATOR);
                    parcel.enforceNoDataAvail();
                    int schedule = schedule(readString, jobInfo);
                    parcel2.writeNoException();
                    parcel2.writeInt(schedule);
                    return true;
                case 2:
                    String readString2 = parcel.readString();
                    JobInfo jobInfo2 = (JobInfo) parcel.readTypedObject(JobInfo.CREATOR);
                    JobWorkItem jobWorkItem = (JobWorkItem) parcel.readTypedObject(JobWorkItem.CREATOR);
                    parcel.enforceNoDataAvail();
                    int enqueue = enqueue(readString2, jobInfo2, jobWorkItem);
                    parcel2.writeNoException();
                    parcel2.writeInt(enqueue);
                    return true;
                case 3:
                    String readString3 = parcel.readString();
                    JobInfo jobInfo3 = (JobInfo) parcel.readTypedObject(JobInfo.CREATOR);
                    String readString4 = parcel.readString();
                    int readInt = parcel.readInt();
                    String readString5 = parcel.readString();
                    parcel.enforceNoDataAvail();
                    int scheduleAsPackage = scheduleAsPackage(readString3, jobInfo3, readString4, readInt, readString5);
                    parcel2.writeNoException();
                    parcel2.writeInt(scheduleAsPackage);
                    return true;
                case 4:
                    String readString6 = parcel.readString();
                    int readInt2 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    cancel(readString6, readInt2);
                    parcel2.writeNoException();
                    return true;
                case 5:
                    cancelAll();
                    parcel2.writeNoException();
                    return true;
                case 6:
                    String readString7 = parcel.readString();
                    parcel.enforceNoDataAvail();
                    cancelAllInNamespace(readString7);
                    parcel2.writeNoException();
                    return true;
                case 7:
                    Map<String, ParceledListSlice<JobInfo>> allPendingJobs = getAllPendingJobs();
                    parcel2.writeNoException();
                    if (allPendingJobs == null) {
                        parcel2.writeInt(-1);
                    } else {
                        parcel2.writeInt(allPendingJobs.size());
                        allPendingJobs.forEach(new BiConsumer() { // from class: android.app.job.IJobScheduler$Stub$$ExternalSyntheticLambda0
                            @Override // java.util.function.BiConsumer
                            public final void accept(Object obj, Object obj2) {
                                IJobScheduler.Stub.lambda$onTransact$0(Parcel.this, (String) obj, (ParceledListSlice) obj2);
                            }
                        });
                    }
                    return true;
                case 8:
                    String readString8 = parcel.readString();
                    parcel.enforceNoDataAvail();
                    ParceledListSlice<JobInfo> allPendingJobsInNamespace = getAllPendingJobsInNamespace(readString8);
                    parcel2.writeNoException();
                    parcel2.writeTypedObject(allPendingJobsInNamespace, 1);
                    return true;
                case 9:
                    String readString9 = parcel.readString();
                    int readInt3 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    JobInfo pendingJob = getPendingJob(readString9, readInt3);
                    parcel2.writeNoException();
                    parcel2.writeTypedObject(pendingJob, 1);
                    return true;
                case 10:
                    String readString10 = parcel.readString();
                    int readInt4 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    int pendingJobReason = getPendingJobReason(readString10, readInt4);
                    parcel2.writeNoException();
                    parcel2.writeInt(pendingJobReason);
                    return true;
                case 11:
                    String readString11 = parcel.readString();
                    int readInt5 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    int[] pendingJobReasons = getPendingJobReasons(readString11, readInt5);
                    parcel2.writeNoException();
                    parcel2.writeIntArray(pendingJobReasons);
                    return true;
                case 12:
                    String readString12 = parcel.readString();
                    int readInt6 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    List<PendingJobReasonsInfo> pendingJobReasonsHistory = getPendingJobReasonsHistory(readString12, readInt6);
                    parcel2.writeNoException();
                    parcel2.writeTypedList(pendingJobReasonsHistory, 1);
                    return true;
                case 13:
                    String readString13 = parcel.readString();
                    parcel.enforceNoDataAvail();
                    boolean canRunUserInitiatedJobs = canRunUserInitiatedJobs(readString13);
                    parcel2.writeNoException();
                    parcel2.writeBoolean(canRunUserInitiatedJobs);
                    return true;
                case 14:
                    String readString14 = parcel.readString();
                    int readInt7 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    boolean hasRunUserInitiatedJobsPermission = hasRunUserInitiatedJobsPermission(readString14, readInt7);
                    parcel2.writeNoException();
                    parcel2.writeBoolean(hasRunUserInitiatedJobsPermission);
                    return true;
                case 15:
                    List<JobInfo> startedJobs = getStartedJobs();
                    parcel2.writeNoException();
                    parcel2.writeTypedList(startedJobs, 1);
                    return true;
                case 16:
                    ParceledListSlice allJobSnapshots = getAllJobSnapshots();
                    parcel2.writeNoException();
                    parcel2.writeTypedObject(allJobSnapshots, 1);
                    return true;
                case 17:
                    IUserVisibleJobObserver asInterface = IUserVisibleJobObserver.Stub.asInterface(parcel.readStrongBinder());
                    parcel.enforceNoDataAvail();
                    registerUserVisibleJobObserver(asInterface);
                    parcel2.writeNoException();
                    return true;
                case 18:
                    IUserVisibleJobObserver asInterface2 = IUserVisibleJobObserver.Stub.asInterface(parcel.readStrongBinder());
                    parcel.enforceNoDataAvail();
                    unregisterUserVisibleJobObserver(asInterface2);
                    parcel2.writeNoException();
                    return true;
                case 19:
                    String readString15 = parcel.readString();
                    int readInt8 = parcel.readInt();
                    String readString16 = parcel.readString();
                    parcel.enforceNoDataAvail();
                    notePendingUserRequestedAppStop(readString15, readInt8, readString16);
                    parcel2.writeNoException();
                    return true;
                default:
                    return super.onTransact(i, parcel, parcel2, i2);
            }
        }

        static /* synthetic */ void lambda$onTransact$0(Parcel parcel, String str, ParceledListSlice parceledListSlice) {
            parcel.writeString(str);
            parcel.writeTypedObject(parceledListSlice, 1);
        }

        /* JADX INFO: Access modifiers changed from: private */
        static class Proxy implements IJobScheduler {
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

            @Override // android.app.job.IJobScheduler
            public int schedule(String str, JobInfo jobInfo) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    obtain.writeString(str);
                    obtain.writeTypedObject(jobInfo, 0);
                    this.mRemote.transact(1, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readInt();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.app.job.IJobScheduler
            public int enqueue(String str, JobInfo jobInfo, JobWorkItem jobWorkItem) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    obtain.writeString(str);
                    obtain.writeTypedObject(jobInfo, 0);
                    obtain.writeTypedObject(jobWorkItem, 0);
                    this.mRemote.transact(2, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readInt();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.app.job.IJobScheduler
            public int scheduleAsPackage(String str, JobInfo jobInfo, String str2, int i, String str3) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    obtain.writeString(str);
                    obtain.writeTypedObject(jobInfo, 0);
                    obtain.writeString(str2);
                    obtain.writeInt(i);
                    obtain.writeString(str3);
                    this.mRemote.transact(3, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readInt();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.app.job.IJobScheduler
            public void cancel(String str, int i) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    obtain.writeString(str);
                    obtain.writeInt(i);
                    this.mRemote.transact(4, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.app.job.IJobScheduler
            public void cancelAll() throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    this.mRemote.transact(5, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.app.job.IJobScheduler
            public void cancelAllInNamespace(String str) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    obtain.writeString(str);
                    this.mRemote.transact(6, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.app.job.IJobScheduler
            public Map<String, ParceledListSlice<JobInfo>> getAllPendingJobs() throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                final Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    this.mRemote.transact(7, obtain, obtain2, 0);
                    obtain2.readException();
                    int readInt = obtain2.readInt();
                    final HashMap hashMap = readInt < 0 ? null : new HashMap();
                    IntStream.range(0, readInt).forEach(new IntConsumer() { // from class: android.app.job.IJobScheduler$Stub$Proxy$$ExternalSyntheticLambda0
                        @Override // java.util.function.IntConsumer
                        public final void accept(int i) {
                            hashMap.put(r0.readString(), (ParceledListSlice) Parcel.this.readTypedObject(ParceledListSlice.CREATOR));
                        }
                    });
                    return hashMap;
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.app.job.IJobScheduler
            public ParceledListSlice<JobInfo> getAllPendingJobsInNamespace(String str) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    obtain.writeString(str);
                    this.mRemote.transact(8, obtain, obtain2, 0);
                    obtain2.readException();
                    return (ParceledListSlice) obtain2.readTypedObject(ParceledListSlice.CREATOR);
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.app.job.IJobScheduler
            public JobInfo getPendingJob(String str, int i) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    obtain.writeString(str);
                    obtain.writeInt(i);
                    this.mRemote.transact(9, obtain, obtain2, 0);
                    obtain2.readException();
                    return (JobInfo) obtain2.readTypedObject(JobInfo.CREATOR);
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.app.job.IJobScheduler
            public int getPendingJobReason(String str, int i) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    obtain.writeString(str);
                    obtain.writeInt(i);
                    this.mRemote.transact(10, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readInt();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.app.job.IJobScheduler
            public int[] getPendingJobReasons(String str, int i) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    obtain.writeString(str);
                    obtain.writeInt(i);
                    this.mRemote.transact(11, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.createIntArray();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.app.job.IJobScheduler
            public List<PendingJobReasonsInfo> getPendingJobReasonsHistory(String str, int i) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    obtain.writeString(str);
                    obtain.writeInt(i);
                    this.mRemote.transact(12, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.createTypedArrayList(PendingJobReasonsInfo.CREATOR);
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.app.job.IJobScheduler
            public boolean canRunUserInitiatedJobs(String str) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    obtain.writeString(str);
                    this.mRemote.transact(13, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readBoolean();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.app.job.IJobScheduler
            public boolean hasRunUserInitiatedJobsPermission(String str, int i) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    obtain.writeString(str);
                    obtain.writeInt(i);
                    this.mRemote.transact(14, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readBoolean();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.app.job.IJobScheduler
            public List<JobInfo> getStartedJobs() throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    this.mRemote.transact(15, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.createTypedArrayList(JobInfo.CREATOR);
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.app.job.IJobScheduler
            public ParceledListSlice getAllJobSnapshots() throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    this.mRemote.transact(16, obtain, obtain2, 0);
                    obtain2.readException();
                    return (ParceledListSlice) obtain2.readTypedObject(ParceledListSlice.CREATOR);
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.app.job.IJobScheduler
            public void registerUserVisibleJobObserver(IUserVisibleJobObserver iUserVisibleJobObserver) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    obtain.writeStrongInterface(iUserVisibleJobObserver);
                    this.mRemote.transact(17, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.app.job.IJobScheduler
            public void unregisterUserVisibleJobObserver(IUserVisibleJobObserver iUserVisibleJobObserver) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    obtain.writeStrongInterface(iUserVisibleJobObserver);
                    this.mRemote.transact(18, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.app.job.IJobScheduler
            public void notePendingUserRequestedAppStop(String str, int i, String str2) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    obtain.writeString(str);
                    obtain.writeInt(i);
                    obtain.writeString(str2);
                    this.mRemote.transact(19, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }
        }

        protected void registerUserVisibleJobObserver_enforcePermission() throws SecurityException {
            this.mEnforcer.enforcePermissionAllOf(PERMISSIONS_registerUserVisibleJobObserver, getCallingPid(), getCallingUid());
        }

        protected void unregisterUserVisibleJobObserver_enforcePermission() throws SecurityException {
            this.mEnforcer.enforcePermissionAllOf(PERMISSIONS_unregisterUserVisibleJobObserver, getCallingPid(), getCallingUid());
        }

        protected void notePendingUserRequestedAppStop_enforcePermission() throws SecurityException {
            this.mEnforcer.enforcePermissionAllOf(PERMISSIONS_notePendingUserRequestedAppStop, getCallingPid(), getCallingUid());
        }
    }
}
