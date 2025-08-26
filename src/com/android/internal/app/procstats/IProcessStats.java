package com.android.internal.app.procstats;

import android.Manifest;
import android.app.ActivityThread;
import android.os.Binder;
import android.os.IBinder;
import android.os.IInterface;
import android.os.Parcel;
import android.os.ParcelFileDescriptor;
import android.os.PermissionEnforcer;
import android.os.RemoteException;
import java.util.ArrayList;
import java.util.List;

/* loaded from: classes5.dex */
public interface IProcessStats extends IInterface {
    public static final String DESCRIPTOR = "com.android.internal.app.procstats.IProcessStats";

    public static class Default implements IProcessStats {
        @Override // android.os.IInterface
        public IBinder asBinder() {
            return null;
        }

        @Override // com.android.internal.app.procstats.IProcessStats
        public long getCommittedStats(long j, int i, boolean z, List<ParcelFileDescriptor> list) throws RemoteException {
            return 0L;
        }

        @Override // com.android.internal.app.procstats.IProcessStats
        public long getCommittedStatsMerged(long j, int i, boolean z, List<ParcelFileDescriptor> list, ProcessStats processStats) throws RemoteException {
            return 0L;
        }

        @Override // com.android.internal.app.procstats.IProcessStats
        public int getCurrentMemoryState() throws RemoteException {
            return 0;
        }

        @Override // com.android.internal.app.procstats.IProcessStats
        public byte[] getCurrentStats(List<ParcelFileDescriptor> list) throws RemoteException {
            return null;
        }

        @Override // com.android.internal.app.procstats.IProcessStats
        public long getMinAssociationDumpDuration() throws RemoteException {
            return 0L;
        }

        @Override // com.android.internal.app.procstats.IProcessStats
        public ParcelFileDescriptor getStatsOverTime(long j) throws RemoteException {
            return null;
        }
    }

    long getCommittedStats(long j, int i, boolean z, List<ParcelFileDescriptor> list) throws RemoteException;

    long getCommittedStatsMerged(long j, int i, boolean z, List<ParcelFileDescriptor> list, ProcessStats processStats) throws RemoteException;

    int getCurrentMemoryState() throws RemoteException;

    byte[] getCurrentStats(List<ParcelFileDescriptor> list) throws RemoteException;

    long getMinAssociationDumpDuration() throws RemoteException;

    ParcelFileDescriptor getStatsOverTime(long j) throws RemoteException;

    public static abstract class Stub extends Binder implements IProcessStats {
        static final int TRANSACTION_getCommittedStats = 4;
        static final int TRANSACTION_getCommittedStatsMerged = 5;
        static final int TRANSACTION_getCurrentMemoryState = 3;
        static final int TRANSACTION_getCurrentStats = 1;
        static final int TRANSACTION_getMinAssociationDumpDuration = 6;
        static final int TRANSACTION_getStatsOverTime = 2;
        private final PermissionEnforcer mEnforcer;

        @Override // android.os.IInterface
        public IBinder asBinder() {
            return this;
        }

        @Override // android.os.Binder
        public int getMaxTransactionId() {
            return 5;
        }

        public Stub(PermissionEnforcer permissionEnforcer) {
            attachInterface(this, IProcessStats.DESCRIPTOR);
            if (permissionEnforcer == null) {
                throw new IllegalArgumentException("enforcer cannot be null");
            }
            this.mEnforcer = permissionEnforcer;
        }

        @Deprecated
        public Stub() {
            this(PermissionEnforcer.fromContext(ActivityThread.currentActivityThread().getSystemContext()));
        }

        public static IProcessStats asInterface(IBinder iBinder) {
            if (iBinder == null) {
                return null;
            }
            IInterface iInterfaceQueryLocalInterface = iBinder.queryLocalInterface(IProcessStats.DESCRIPTOR);
            if (iInterfaceQueryLocalInterface != null && (iInterfaceQueryLocalInterface instanceof IProcessStats)) {
                return (IProcessStats) iInterfaceQueryLocalInterface;
            }
            return new Proxy(iBinder);
        }

        public static String getDefaultTransactionName(int i) {
            switch (i) {
                case 1:
                    return "getCurrentStats";
                case 2:
                    return "getStatsOverTime";
                case 3:
                    return "getCurrentMemoryState";
                case 4:
                    return "getCommittedStats";
                case 5:
                    return "getCommittedStatsMerged";
                case 6:
                    return "getMinAssociationDumpDuration";
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
                parcel.enforceInterface(IProcessStats.DESCRIPTOR);
            }
            if (i == 1598968902) {
                parcel2.writeString(IProcessStats.DESCRIPTOR);
                return true;
            }
            switch (i) {
                case 1:
                    ArrayList arrayList = new ArrayList();
                    parcel.enforceNoDataAvail();
                    byte[] currentStats = getCurrentStats(arrayList);
                    parcel2.writeNoException();
                    parcel2.writeByteArray(currentStats);
                    parcel2.writeTypedList(arrayList, 1);
                    return true;
                case 2:
                    long j = parcel.readLong();
                    parcel.enforceNoDataAvail();
                    ParcelFileDescriptor statsOverTime = getStatsOverTime(j);
                    parcel2.writeNoException();
                    parcel2.writeTypedObject(statsOverTime, 1);
                    return true;
                case 3:
                    int currentMemoryState = getCurrentMemoryState();
                    parcel2.writeNoException();
                    parcel2.writeInt(currentMemoryState);
                    return true;
                case 4:
                    long j2 = parcel.readLong();
                    int i3 = parcel.readInt();
                    boolean z = parcel.readBoolean();
                    ArrayList arrayList2 = new ArrayList();
                    parcel.enforceNoDataAvail();
                    long committedStats = getCommittedStats(j2, i3, z, arrayList2);
                    parcel2.writeNoException();
                    parcel2.writeLong(committedStats);
                    parcel2.writeTypedList(arrayList2, 1);
                    return true;
                case 5:
                    long j3 = parcel.readLong();
                    int i4 = parcel.readInt();
                    boolean z2 = parcel.readBoolean();
                    ArrayList arrayList3 = new ArrayList();
                    ProcessStats processStats = new ProcessStats();
                    parcel.enforceNoDataAvail();
                    long committedStatsMerged = getCommittedStatsMerged(j3, i4, z2, arrayList3, processStats);
                    parcel2.writeNoException();
                    parcel2.writeLong(committedStatsMerged);
                    parcel2.writeTypedList(arrayList3, 1);
                    parcel2.writeTypedObject(processStats, 1);
                    return true;
                case 6:
                    long minAssociationDumpDuration = getMinAssociationDumpDuration();
                    parcel2.writeNoException();
                    parcel2.writeLong(minAssociationDumpDuration);
                    return true;
                default:
                    return super.onTransact(i, parcel, parcel2, i2);
            }
        }

        private static class Proxy implements IProcessStats {
            private IBinder mRemote;

            Proxy(IBinder iBinder) {
                this.mRemote = iBinder;
            }

            @Override // android.os.IInterface
            public IBinder asBinder() {
                return this.mRemote;
            }

            public String getInterfaceDescriptor() {
                return IProcessStats.DESCRIPTOR;
            }

            @Override // com.android.internal.app.procstats.IProcessStats
            public byte[] getCurrentStats(List<ParcelFileDescriptor> list) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(IProcessStats.DESCRIPTOR);
                    this.mRemote.transact(1, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    byte[] bArrCreateByteArray = parcelObtain2.createByteArray();
                    parcelObtain2.readTypedList(list, ParcelFileDescriptor.CREATOR);
                    return bArrCreateByteArray;
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // com.android.internal.app.procstats.IProcessStats
            public ParcelFileDescriptor getStatsOverTime(long j) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(IProcessStats.DESCRIPTOR);
                    parcelObtain.writeLong(j);
                    this.mRemote.transact(2, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return (ParcelFileDescriptor) parcelObtain2.readTypedObject(ParcelFileDescriptor.CREATOR);
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // com.android.internal.app.procstats.IProcessStats
            public int getCurrentMemoryState() throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(IProcessStats.DESCRIPTOR);
                    this.mRemote.transact(3, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return parcelObtain2.readInt();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // com.android.internal.app.procstats.IProcessStats
            public long getCommittedStats(long j, int i, boolean z, List<ParcelFileDescriptor> list) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(IProcessStats.DESCRIPTOR);
                    parcelObtain.writeLong(j);
                    parcelObtain.writeInt(i);
                    parcelObtain.writeBoolean(z);
                    this.mRemote.transact(4, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    long j2 = parcelObtain2.readLong();
                    parcelObtain2.readTypedList(list, ParcelFileDescriptor.CREATOR);
                    return j2;
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // com.android.internal.app.procstats.IProcessStats
            public long getCommittedStatsMerged(long j, int i, boolean z, List<ParcelFileDescriptor> list, ProcessStats processStats) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(IProcessStats.DESCRIPTOR);
                    parcelObtain.writeLong(j);
                    parcelObtain.writeInt(i);
                    parcelObtain.writeBoolean(z);
                    this.mRemote.transact(5, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    long j2 = parcelObtain2.readLong();
                    parcelObtain2.readTypedList(list, ParcelFileDescriptor.CREATOR);
                    if (parcelObtain2.readInt() != 0) {
                        processStats.readFromParcel(parcelObtain2);
                    }
                    return j2;
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // com.android.internal.app.procstats.IProcessStats
            public long getMinAssociationDumpDuration() throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(IProcessStats.DESCRIPTOR);
                    this.mRemote.transact(6, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return parcelObtain2.readLong();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }
        }

        protected void getCurrentStats_enforcePermission() throws SecurityException {
            this.mEnforcer.enforcePermission(Manifest.permission.PACKAGE_USAGE_STATS, getCallingPid(), getCallingUid());
        }

        protected void getStatsOverTime_enforcePermission() throws SecurityException {
            this.mEnforcer.enforcePermission(Manifest.permission.PACKAGE_USAGE_STATS, getCallingPid(), getCallingUid());
        }

        protected void getCommittedStatsMerged_enforcePermission() throws SecurityException {
            this.mEnforcer.enforcePermission(Manifest.permission.PACKAGE_USAGE_STATS, getCallingPid(), getCallingUid());
        }
    }
}
