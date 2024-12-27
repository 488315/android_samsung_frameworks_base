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

public interface IProcessStats extends IInterface {
    public static final String DESCRIPTOR = "com.android.internal.app.procstats.IProcessStats";

    long getCommittedStats(long j, int i, boolean z, List<ParcelFileDescriptor> list)
            throws RemoteException;

    long getCommittedStatsMerged(
            long j, int i, boolean z, List<ParcelFileDescriptor> list, ProcessStats processStats)
            throws RemoteException;

    int getCurrentMemoryState() throws RemoteException;

    byte[] getCurrentStats(List<ParcelFileDescriptor> list) throws RemoteException;

    long getMinAssociationDumpDuration() throws RemoteException;

    ParcelFileDescriptor getStatsOverTime(long j) throws RemoteException;

    public static class Default implements IProcessStats {
        @Override // com.android.internal.app.procstats.IProcessStats
        public byte[] getCurrentStats(List<ParcelFileDescriptor> historic) throws RemoteException {
            return null;
        }

        @Override // com.android.internal.app.procstats.IProcessStats
        public ParcelFileDescriptor getStatsOverTime(long minTime) throws RemoteException {
            return null;
        }

        @Override // com.android.internal.app.procstats.IProcessStats
        public int getCurrentMemoryState() throws RemoteException {
            return 0;
        }

        @Override // com.android.internal.app.procstats.IProcessStats
        public long getCommittedStats(
                long highWaterMarkMs,
                int section,
                boolean doAggregate,
                List<ParcelFileDescriptor> committedStats)
                throws RemoteException {
            return 0L;
        }

        @Override // com.android.internal.app.procstats.IProcessStats
        public long getCommittedStatsMerged(
                long highWaterMarkMs,
                int section,
                boolean doAggregate,
                List<ParcelFileDescriptor> committedStats,
                ProcessStats mergedStats)
                throws RemoteException {
            return 0L;
        }

        @Override // com.android.internal.app.procstats.IProcessStats
        public long getMinAssociationDumpDuration() throws RemoteException {
            return 0L;
        }

        @Override // android.os.IInterface
        public IBinder asBinder() {
            return null;
        }
    }

    public abstract static class Stub extends Binder implements IProcessStats {
        static final int TRANSACTION_getCommittedStats = 4;
        static final int TRANSACTION_getCommittedStatsMerged = 5;
        static final int TRANSACTION_getCurrentMemoryState = 3;
        static final int TRANSACTION_getCurrentStats = 1;
        static final int TRANSACTION_getMinAssociationDumpDuration = 6;
        static final int TRANSACTION_getStatsOverTime = 2;
        private final PermissionEnforcer mEnforcer;

        public Stub(PermissionEnforcer enforcer) {
            attachInterface(this, IProcessStats.DESCRIPTOR);
            if (enforcer == null) {
                throw new IllegalArgumentException("enforcer cannot be null");
            }
            this.mEnforcer = enforcer;
        }

        @Deprecated
        public Stub() {
            this(
                    PermissionEnforcer.fromContext(
                            ActivityThread.currentActivityThread().getSystemContext()));
        }

        public static IProcessStats asInterface(IBinder obj) {
            if (obj == null) {
                return null;
            }
            IInterface iin = obj.queryLocalInterface(IProcessStats.DESCRIPTOR);
            if (iin != null && (iin instanceof IProcessStats)) {
                return (IProcessStats) iin;
            }
            return new Proxy(obj);
        }

        @Override // android.os.IInterface
        public IBinder asBinder() {
            return this;
        }

        public static String getDefaultTransactionName(int transactionCode) {
            switch (transactionCode) {
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
        public String getTransactionName(int transactionCode) {
            return getDefaultTransactionName(transactionCode);
        }

        @Override // android.os.Binder
        public boolean onTransact(int code, Parcel data, Parcel reply, int flags)
                throws RemoteException {
            if (code >= 1 && code <= 16777215) {
                data.enforceInterface(IProcessStats.DESCRIPTOR);
            }
            if (code == 1598968902) {
                reply.writeString(IProcessStats.DESCRIPTOR);
                return true;
            }
            switch (code) {
                case 1:
                    ArrayList arrayList = new ArrayList();
                    data.enforceNoDataAvail();
                    byte[] _result = getCurrentStats(arrayList);
                    reply.writeNoException();
                    reply.writeByteArray(_result);
                    reply.writeTypedList(arrayList, 1);
                    return true;
                case 2:
                    long _arg0 = data.readLong();
                    data.enforceNoDataAvail();
                    ParcelFileDescriptor _result2 = getStatsOverTime(_arg0);
                    reply.writeNoException();
                    reply.writeTypedObject(_result2, 1);
                    return true;
                case 3:
                    int _result3 = getCurrentMemoryState();
                    reply.writeNoException();
                    reply.writeInt(_result3);
                    return true;
                case 4:
                    long _arg02 = data.readLong();
                    int _arg1 = data.readInt();
                    boolean _arg2 = data.readBoolean();
                    ArrayList arrayList2 = new ArrayList();
                    data.enforceNoDataAvail();
                    long _result4 = getCommittedStats(_arg02, _arg1, _arg2, arrayList2);
                    reply.writeNoException();
                    reply.writeLong(_result4);
                    reply.writeTypedList(arrayList2, 1);
                    return true;
                case 5:
                    long _arg03 = data.readLong();
                    int _arg12 = data.readInt();
                    boolean _arg22 = data.readBoolean();
                    ArrayList arrayList3 = new ArrayList();
                    ProcessStats _arg4 = new ProcessStats();
                    data.enforceNoDataAvail();
                    long _result5 =
                            getCommittedStatsMerged(_arg03, _arg12, _arg22, arrayList3, _arg4);
                    reply.writeNoException();
                    reply.writeLong(_result5);
                    reply.writeTypedList(arrayList3, 1);
                    reply.writeTypedObject(_arg4, 1);
                    return true;
                case 6:
                    long _result6 = getMinAssociationDumpDuration();
                    reply.writeNoException();
                    reply.writeLong(_result6);
                    return true;
                default:
                    return super.onTransact(code, data, reply, flags);
            }
        }

        private static class Proxy implements IProcessStats {
            private IBinder mRemote;

            Proxy(IBinder remote) {
                this.mRemote = remote;
            }

            @Override // android.os.IInterface
            public IBinder asBinder() {
                return this.mRemote;
            }

            public String getInterfaceDescriptor() {
                return IProcessStats.DESCRIPTOR;
            }

            @Override // com.android.internal.app.procstats.IProcessStats
            public byte[] getCurrentStats(List<ParcelFileDescriptor> historic)
                    throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(IProcessStats.DESCRIPTOR);
                    this.mRemote.transact(1, _data, _reply, 0);
                    _reply.readException();
                    byte[] _result = _reply.createByteArray();
                    _reply.readTypedList(historic, ParcelFileDescriptor.CREATOR);
                    return _result;
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // com.android.internal.app.procstats.IProcessStats
            public ParcelFileDescriptor getStatsOverTime(long minTime) throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(IProcessStats.DESCRIPTOR);
                    _data.writeLong(minTime);
                    this.mRemote.transact(2, _data, _reply, 0);
                    _reply.readException();
                    ParcelFileDescriptor _result =
                            (ParcelFileDescriptor)
                                    _reply.readTypedObject(ParcelFileDescriptor.CREATOR);
                    return _result;
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // com.android.internal.app.procstats.IProcessStats
            public int getCurrentMemoryState() throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(IProcessStats.DESCRIPTOR);
                    this.mRemote.transact(3, _data, _reply, 0);
                    _reply.readException();
                    int _result = _reply.readInt();
                    return _result;
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // com.android.internal.app.procstats.IProcessStats
            public long getCommittedStats(
                    long highWaterMarkMs,
                    int section,
                    boolean doAggregate,
                    List<ParcelFileDescriptor> committedStats)
                    throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(IProcessStats.DESCRIPTOR);
                    _data.writeLong(highWaterMarkMs);
                    _data.writeInt(section);
                    _data.writeBoolean(doAggregate);
                    this.mRemote.transact(4, _data, _reply, 0);
                    _reply.readException();
                    long _result = _reply.readLong();
                    _reply.readTypedList(committedStats, ParcelFileDescriptor.CREATOR);
                    return _result;
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // com.android.internal.app.procstats.IProcessStats
            public long getCommittedStatsMerged(
                    long highWaterMarkMs,
                    int section,
                    boolean doAggregate,
                    List<ParcelFileDescriptor> committedStats,
                    ProcessStats mergedStats)
                    throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(IProcessStats.DESCRIPTOR);
                    _data.writeLong(highWaterMarkMs);
                    _data.writeInt(section);
                    _data.writeBoolean(doAggregate);
                    this.mRemote.transact(5, _data, _reply, 0);
                    _reply.readException();
                    long _result = _reply.readLong();
                    _reply.readTypedList(committedStats, ParcelFileDescriptor.CREATOR);
                    if (_reply.readInt() != 0) {
                        mergedStats.readFromParcel(_reply);
                    }
                    return _result;
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // com.android.internal.app.procstats.IProcessStats
            public long getMinAssociationDumpDuration() throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(IProcessStats.DESCRIPTOR);
                    this.mRemote.transact(6, _data, _reply, 0);
                    _reply.readException();
                    long _result = _reply.readLong();
                    return _result;
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }
        }

        protected void getCurrentStats_enforcePermission() throws SecurityException {
            this.mEnforcer.enforcePermission(
                    Manifest.permission.PACKAGE_USAGE_STATS, getCallingPid(), getCallingUid());
        }

        protected void getStatsOverTime_enforcePermission() throws SecurityException {
            this.mEnforcer.enforcePermission(
                    Manifest.permission.PACKAGE_USAGE_STATS, getCallingPid(), getCallingUid());
        }

        protected void getCommittedStatsMerged_enforcePermission() throws SecurityException {
            this.mEnforcer.enforcePermission(
                    Manifest.permission.PACKAGE_USAGE_STATS, getCallingPid(), getCallingUid());
        }

        @Override // android.os.Binder
        public int getMaxTransactionId() {
            return 5;
        }
    }
}
