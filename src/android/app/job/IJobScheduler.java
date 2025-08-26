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
            IInterface iInterfaceQueryLocalInterface = iBinder.queryLocalInterface(DESCRIPTOR);
            if (iInterfaceQueryLocalInterface != null && (iInterfaceQueryLocalInterface instanceof IJobScheduler)) {
                return (IJobScheduler) iInterfaceQueryLocalInterface;
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
                    String string = parcel.readString();
                    JobInfo jobInfo = (JobInfo) parcel.readTypedObject(JobInfo.CREATOR);
                    parcel.enforceNoDataAvail();
                    int iSchedule = schedule(string, jobInfo);
                    parcel2.writeNoException();
                    parcel2.writeInt(iSchedule);
                    return true;
                case 2:
                    String string2 = parcel.readString();
                    JobInfo jobInfo2 = (JobInfo) parcel.readTypedObject(JobInfo.CREATOR);
                    JobWorkItem jobWorkItem = (JobWorkItem) parcel.readTypedObject(JobWorkItem.CREATOR);
                    parcel.enforceNoDataAvail();
                    int iEnqueue = enqueue(string2, jobInfo2, jobWorkItem);
                    parcel2.writeNoException();
                    parcel2.writeInt(iEnqueue);
                    return true;
                case 3:
                    String string3 = parcel.readString();
                    JobInfo jobInfo3 = (JobInfo) parcel.readTypedObject(JobInfo.CREATOR);
                    String string4 = parcel.readString();
                    int i3 = parcel.readInt();
                    String string5 = parcel.readString();
                    parcel.enforceNoDataAvail();
                    int iScheduleAsPackage = scheduleAsPackage(string3, jobInfo3, string4, i3, string5);
                    parcel2.writeNoException();
                    parcel2.writeInt(iScheduleAsPackage);
                    return true;
                case 4:
                    String string6 = parcel.readString();
                    int i4 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    cancel(string6, i4);
                    parcel2.writeNoException();
                    return true;
                case 5:
                    cancelAll();
                    parcel2.writeNoException();
                    return true;
                case 6:
                    String string7 = parcel.readString();
                    parcel.enforceNoDataAvail();
                    cancelAllInNamespace(string7);
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
                                IJobScheduler.Stub.lambda$onTransact$0(parcel2, (String) obj, (ParceledListSlice) obj2);
                            }
                        });
                    }
                    return true;
                case 8:
                    String string8 = parcel.readString();
                    parcel.enforceNoDataAvail();
                    ParceledListSlice<JobInfo> allPendingJobsInNamespace = getAllPendingJobsInNamespace(string8);
                    parcel2.writeNoException();
                    parcel2.writeTypedObject(allPendingJobsInNamespace, 1);
                    return true;
                case 9:
                    String string9 = parcel.readString();
                    int i5 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    JobInfo pendingJob = getPendingJob(string9, i5);
                    parcel2.writeNoException();
                    parcel2.writeTypedObject(pendingJob, 1);
                    return true;
                case 10:
                    String string10 = parcel.readString();
                    int i6 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    int pendingJobReason = getPendingJobReason(string10, i6);
                    parcel2.writeNoException();
                    parcel2.writeInt(pendingJobReason);
                    return true;
                case 11:
                    String string11 = parcel.readString();
                    int i7 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    int[] pendingJobReasons = getPendingJobReasons(string11, i7);
                    parcel2.writeNoException();
                    parcel2.writeIntArray(pendingJobReasons);
                    return true;
                case 12:
                    String string12 = parcel.readString();
                    int i8 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    List<PendingJobReasonsInfo> pendingJobReasonsHistory = getPendingJobReasonsHistory(string12, i8);
                    parcel2.writeNoException();
                    parcel2.writeTypedList(pendingJobReasonsHistory, 1);
                    return true;
                case 13:
                    String string13 = parcel.readString();
                    parcel.enforceNoDataAvail();
                    boolean zCanRunUserInitiatedJobs = canRunUserInitiatedJobs(string13);
                    parcel2.writeNoException();
                    parcel2.writeBoolean(zCanRunUserInitiatedJobs);
                    return true;
                case 14:
                    String string14 = parcel.readString();
                    int i9 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    boolean zHasRunUserInitiatedJobsPermission = hasRunUserInitiatedJobsPermission(string14, i9);
                    parcel2.writeNoException();
                    parcel2.writeBoolean(zHasRunUserInitiatedJobsPermission);
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
                    IUserVisibleJobObserver iUserVisibleJobObserverAsInterface = IUserVisibleJobObserver.Stub.asInterface(parcel.readStrongBinder());
                    parcel.enforceNoDataAvail();
                    registerUserVisibleJobObserver(iUserVisibleJobObserverAsInterface);
                    parcel2.writeNoException();
                    return true;
                case 18:
                    IUserVisibleJobObserver iUserVisibleJobObserverAsInterface2 = IUserVisibleJobObserver.Stub.asInterface(parcel.readStrongBinder());
                    parcel.enforceNoDataAvail();
                    unregisterUserVisibleJobObserver(iUserVisibleJobObserverAsInterface2);
                    parcel2.writeNoException();
                    return true;
                case 19:
                    String string15 = parcel.readString();
                    int i10 = parcel.readInt();
                    String string16 = parcel.readString();
                    parcel.enforceNoDataAvail();
                    notePendingUserRequestedAppStop(string15, i10, string16);
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
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcelObtain.writeString(str);
                    parcelObtain.writeTypedObject(jobInfo, 0);
                    this.mRemote.transact(1, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return parcelObtain2.readInt();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.app.job.IJobScheduler
            public int enqueue(String str, JobInfo jobInfo, JobWorkItem jobWorkItem) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcelObtain.writeString(str);
                    parcelObtain.writeTypedObject(jobInfo, 0);
                    parcelObtain.writeTypedObject(jobWorkItem, 0);
                    this.mRemote.transact(2, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return parcelObtain2.readInt();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.app.job.IJobScheduler
            public int scheduleAsPackage(String str, JobInfo jobInfo, String str2, int i, String str3) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcelObtain.writeString(str);
                    parcelObtain.writeTypedObject(jobInfo, 0);
                    parcelObtain.writeString(str2);
                    parcelObtain.writeInt(i);
                    parcelObtain.writeString(str3);
                    this.mRemote.transact(3, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return parcelObtain2.readInt();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.app.job.IJobScheduler
            public void cancel(String str, int i) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcelObtain.writeString(str);
                    parcelObtain.writeInt(i);
                    this.mRemote.transact(4, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.app.job.IJobScheduler
            public void cancelAll() throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    this.mRemote.transact(5, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.app.job.IJobScheduler
            public void cancelAllInNamespace(String str) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcelObtain.writeString(str);
                    this.mRemote.transact(6, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.app.job.IJobScheduler
            public Map<String, ParceledListSlice<JobInfo>> getAllPendingJobs() throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                final Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    this.mRemote.transact(7, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    int i = parcelObtain2.readInt();
                    final HashMap map = i < 0 ? null : new HashMap();
                    IntStream.range(0, i).forEach(new IntConsumer() { // from class: android.app.job.IJobScheduler$Stub$Proxy$$ExternalSyntheticLambda0
                        @Override // java.util.function.IntConsumer
                        public final void accept(int i2) {
                            Parcel parcel = parcelObtain2;
                            map.put(parcel.readString(), (ParceledListSlice) parcel.readTypedObject(ParceledListSlice.CREATOR));
                        }
                    });
                    return map;
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.app.job.IJobScheduler
            public ParceledListSlice<JobInfo> getAllPendingJobsInNamespace(String str) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcelObtain.writeString(str);
                    this.mRemote.transact(8, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return (ParceledListSlice) parcelObtain2.readTypedObject(ParceledListSlice.CREATOR);
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.app.job.IJobScheduler
            public JobInfo getPendingJob(String str, int i) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcelObtain.writeString(str);
                    parcelObtain.writeInt(i);
                    this.mRemote.transact(9, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return (JobInfo) parcelObtain2.readTypedObject(JobInfo.CREATOR);
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.app.job.IJobScheduler
            public int getPendingJobReason(String str, int i) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcelObtain.writeString(str);
                    parcelObtain.writeInt(i);
                    this.mRemote.transact(10, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return parcelObtain2.readInt();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.app.job.IJobScheduler
            public int[] getPendingJobReasons(String str, int i) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcelObtain.writeString(str);
                    parcelObtain.writeInt(i);
                    this.mRemote.transact(11, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return parcelObtain2.createIntArray();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.app.job.IJobScheduler
            public List<PendingJobReasonsInfo> getPendingJobReasonsHistory(String str, int i) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcelObtain.writeString(str);
                    parcelObtain.writeInt(i);
                    this.mRemote.transact(12, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return parcelObtain2.createTypedArrayList(PendingJobReasonsInfo.CREATOR);
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.app.job.IJobScheduler
            public boolean canRunUserInitiatedJobs(String str) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcelObtain.writeString(str);
                    this.mRemote.transact(13, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return parcelObtain2.readBoolean();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.app.job.IJobScheduler
            public boolean hasRunUserInitiatedJobsPermission(String str, int i) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcelObtain.writeString(str);
                    parcelObtain.writeInt(i);
                    this.mRemote.transact(14, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return parcelObtain2.readBoolean();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.app.job.IJobScheduler
            public List<JobInfo> getStartedJobs() throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    this.mRemote.transact(15, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return parcelObtain2.createTypedArrayList(JobInfo.CREATOR);
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.app.job.IJobScheduler
            public ParceledListSlice getAllJobSnapshots() throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    this.mRemote.transact(16, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return (ParceledListSlice) parcelObtain2.readTypedObject(ParceledListSlice.CREATOR);
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.app.job.IJobScheduler
            public void registerUserVisibleJobObserver(IUserVisibleJobObserver iUserVisibleJobObserver) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcelObtain.writeStrongInterface(iUserVisibleJobObserver);
                    this.mRemote.transact(17, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.app.job.IJobScheduler
            public void unregisterUserVisibleJobObserver(IUserVisibleJobObserver iUserVisibleJobObserver) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcelObtain.writeStrongInterface(iUserVisibleJobObserver);
                    this.mRemote.transact(18, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.app.job.IJobScheduler
            public void notePendingUserRequestedAppStop(String str, int i, String str2) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcelObtain.writeString(str);
                    parcelObtain.writeInt(i);
                    parcelObtain.writeString(str2);
                    this.mRemote.transact(19, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
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
