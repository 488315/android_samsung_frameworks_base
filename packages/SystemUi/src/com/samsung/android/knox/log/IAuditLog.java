package com.samsung.android.knox.log;

import android.os.Binder;
import android.os.IBinder;
import android.os.IInterface;
import android.os.Parcel;
import android.os.ParcelFileDescriptor;
import com.samsung.android.knox.ContextInfo;

/* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
/* loaded from: classes3.dex */
public interface IAuditLog extends IInterface {
    public static final String DESCRIPTOR = "com.samsung.android.knox.log.IAuditLog";

    void AuditLogger(ContextInfo contextInfo, int i, int i2, boolean z, int i3, String str, String str2);

    boolean disableAuditLog(ContextInfo contextInfo);

    boolean dumpLogFile(ContextInfo contextInfo, long j, long j2, String str, ParcelFileDescriptor parcelFileDescriptor);

    boolean enableAuditLog(ContextInfo contextInfo);

    AuditLogRulesInfo getAuditLogRules(ContextInfo contextInfo);

    int getCriticalLogSize(ContextInfo contextInfo);

    int getCurrentLogFileSize(ContextInfo contextInfo);

    int getMaximumLogSize(ContextInfo contextInfo);

    boolean isAuditLogEnabled(ContextInfo contextInfo);

    boolean isAuditServiceRunning();

    boolean setAuditLogRules(ContextInfo contextInfo, AuditLogRulesInfo auditLogRulesInfo);

    boolean setCriticalLogSize(ContextInfo contextInfo, int i);

    boolean setMaximumLogSize(ContextInfo contextInfo, int i);

    /* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
    public static abstract class Stub extends Binder implements IAuditLog {
        public static final int TRANSACTION_AuditLogger = 9;
        public static final int TRANSACTION_disableAuditLog = 2;
        public static final int TRANSACTION_dumpLogFile = 10;
        public static final int TRANSACTION_enableAuditLog = 1;
        public static final int TRANSACTION_getAuditLogRules = 13;
        public static final int TRANSACTION_getCriticalLogSize = 6;
        public static final int TRANSACTION_getCurrentLogFileSize = 4;
        public static final int TRANSACTION_getMaximumLogSize = 8;
        public static final int TRANSACTION_isAuditLogEnabled = 3;
        public static final int TRANSACTION_isAuditServiceRunning = 11;
        public static final int TRANSACTION_setAuditLogRules = 12;
        public static final int TRANSACTION_setCriticalLogSize = 5;
        public static final int TRANSACTION_setMaximumLogSize = 7;

        /* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
        public static class Proxy implements IAuditLog {
            public IBinder mRemote;

            public Proxy(IBinder iBinder) {
                this.mRemote = iBinder;
            }

            @Override // com.samsung.android.knox.log.IAuditLog
            public final void AuditLogger(ContextInfo contextInfo, int i, int i2, boolean z, int i3, String str, String str2) {
                Parcel obtain = Parcel.obtain(this.mRemote);
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(IAuditLog.DESCRIPTOR);
                    obtain.writeTypedObject(contextInfo, 0);
                    obtain.writeInt(i);
                    obtain.writeInt(i2);
                    obtain.writeBoolean(z);
                    obtain.writeInt(i3);
                    obtain.writeString(str);
                    obtain.writeString(str2);
                    this.mRemote.transact(9, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.os.IInterface
            public final IBinder asBinder() {
                return this.mRemote;
            }

            @Override // com.samsung.android.knox.log.IAuditLog
            public final boolean disableAuditLog(ContextInfo contextInfo) {
                Parcel obtain = Parcel.obtain(this.mRemote);
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(IAuditLog.DESCRIPTOR);
                    obtain.writeTypedObject(contextInfo, 0);
                    this.mRemote.transact(2, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readBoolean();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.samsung.android.knox.log.IAuditLog
            public final boolean dumpLogFile(ContextInfo contextInfo, long j, long j2, String str, ParcelFileDescriptor parcelFileDescriptor) {
                Parcel obtain = Parcel.obtain(this.mRemote);
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(IAuditLog.DESCRIPTOR);
                    obtain.writeTypedObject(contextInfo, 0);
                    obtain.writeLong(j);
                    obtain.writeLong(j2);
                    obtain.writeString(str);
                    obtain.writeTypedObject(parcelFileDescriptor, 0);
                    this.mRemote.transact(10, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readBoolean();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.samsung.android.knox.log.IAuditLog
            public final boolean enableAuditLog(ContextInfo contextInfo) {
                Parcel obtain = Parcel.obtain(this.mRemote);
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(IAuditLog.DESCRIPTOR);
                    obtain.writeTypedObject(contextInfo, 0);
                    this.mRemote.transact(1, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readBoolean();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.samsung.android.knox.log.IAuditLog
            public final AuditLogRulesInfo getAuditLogRules(ContextInfo contextInfo) {
                Parcel obtain = Parcel.obtain(this.mRemote);
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(IAuditLog.DESCRIPTOR);
                    obtain.writeTypedObject(contextInfo, 0);
                    this.mRemote.transact(13, obtain, obtain2, 0);
                    obtain2.readException();
                    return (AuditLogRulesInfo) obtain2.readTypedObject(AuditLogRulesInfo.CREATOR);
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.samsung.android.knox.log.IAuditLog
            public final int getCriticalLogSize(ContextInfo contextInfo) {
                Parcel obtain = Parcel.obtain(this.mRemote);
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(IAuditLog.DESCRIPTOR);
                    obtain.writeTypedObject(contextInfo, 0);
                    this.mRemote.transact(6, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readInt();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.samsung.android.knox.log.IAuditLog
            public final int getCurrentLogFileSize(ContextInfo contextInfo) {
                Parcel obtain = Parcel.obtain(this.mRemote);
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(IAuditLog.DESCRIPTOR);
                    obtain.writeTypedObject(contextInfo, 0);
                    this.mRemote.transact(4, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readInt();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            public final String getInterfaceDescriptor() {
                return IAuditLog.DESCRIPTOR;
            }

            @Override // com.samsung.android.knox.log.IAuditLog
            public final int getMaximumLogSize(ContextInfo contextInfo) {
                Parcel obtain = Parcel.obtain(this.mRemote);
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(IAuditLog.DESCRIPTOR);
                    obtain.writeTypedObject(contextInfo, 0);
                    this.mRemote.transact(8, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readInt();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.samsung.android.knox.log.IAuditLog
            public final boolean isAuditLogEnabled(ContextInfo contextInfo) {
                Parcel obtain = Parcel.obtain(this.mRemote);
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(IAuditLog.DESCRIPTOR);
                    obtain.writeTypedObject(contextInfo, 0);
                    this.mRemote.transact(3, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readBoolean();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.samsung.android.knox.log.IAuditLog
            public final boolean isAuditServiceRunning() {
                Parcel obtain = Parcel.obtain(this.mRemote);
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(IAuditLog.DESCRIPTOR);
                    this.mRemote.transact(11, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readBoolean();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.samsung.android.knox.log.IAuditLog
            public final boolean setAuditLogRules(ContextInfo contextInfo, AuditLogRulesInfo auditLogRulesInfo) {
                Parcel obtain = Parcel.obtain(this.mRemote);
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(IAuditLog.DESCRIPTOR);
                    obtain.writeTypedObject(contextInfo, 0);
                    obtain.writeTypedObject(auditLogRulesInfo, 0);
                    this.mRemote.transact(12, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readBoolean();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.samsung.android.knox.log.IAuditLog
            public final boolean setCriticalLogSize(ContextInfo contextInfo, int i) {
                Parcel obtain = Parcel.obtain(this.mRemote);
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(IAuditLog.DESCRIPTOR);
                    obtain.writeTypedObject(contextInfo, 0);
                    obtain.writeInt(i);
                    this.mRemote.transact(5, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readBoolean();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.samsung.android.knox.log.IAuditLog
            public final boolean setMaximumLogSize(ContextInfo contextInfo, int i) {
                Parcel obtain = Parcel.obtain(this.mRemote);
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(IAuditLog.DESCRIPTOR);
                    obtain.writeTypedObject(contextInfo, 0);
                    obtain.writeInt(i);
                    this.mRemote.transact(7, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readBoolean();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }
        }

        public Stub() {
            attachInterface(this, IAuditLog.DESCRIPTOR);
        }

        public static IAuditLog asInterface(IBinder iBinder) {
            if (iBinder == null) {
                return null;
            }
            IInterface queryLocalInterface = iBinder.queryLocalInterface(IAuditLog.DESCRIPTOR);
            return (queryLocalInterface == null || !(queryLocalInterface instanceof IAuditLog)) ? new Proxy(iBinder) : (IAuditLog) queryLocalInterface;
        }

        public static String getDefaultTransactionName(int i) {
            switch (i) {
                case 1:
                    return "enableAuditLog";
                case 2:
                    return "disableAuditLog";
                case 3:
                    return "isAuditLogEnabled";
                case 4:
                    return "getCurrentLogFileSize";
                case 5:
                    return "setCriticalLogSize";
                case 6:
                    return "getCriticalLogSize";
                case 7:
                    return "setMaximumLogSize";
                case 8:
                    return "getMaximumLogSize";
                case 9:
                    return "AuditLogger";
                case 10:
                    return "dumpLogFile";
                case 11:
                    return "isAuditServiceRunning";
                case 12:
                    return "setAuditLogRules";
                case 13:
                    return "getAuditLogRules";
                default:
                    return null;
            }
        }

        public final int getMaxTransactionId() {
            return 12;
        }

        public final String getTransactionName(int i) {
            return getDefaultTransactionName(i);
        }

        @Override // android.os.Binder
        public final boolean onTransact(int i, Parcel parcel, Parcel parcel2, int i2) {
            if (i >= 1 && i <= 16777215) {
                parcel.enforceInterface(IAuditLog.DESCRIPTOR);
            }
            if (i == 1598968902) {
                parcel2.writeString(IAuditLog.DESCRIPTOR);
                return true;
            }
            switch (i) {
                case 1:
                    ContextInfo contextInfo = (ContextInfo) parcel.readTypedObject(ContextInfo.CREATOR);
                    parcel.enforceNoDataAvail();
                    boolean enableAuditLog = enableAuditLog(contextInfo);
                    parcel2.writeNoException();
                    parcel2.writeBoolean(enableAuditLog);
                    return true;
                case 2:
                    ContextInfo contextInfo2 = (ContextInfo) parcel.readTypedObject(ContextInfo.CREATOR);
                    parcel.enforceNoDataAvail();
                    boolean disableAuditLog = disableAuditLog(contextInfo2);
                    parcel2.writeNoException();
                    parcel2.writeBoolean(disableAuditLog);
                    return true;
                case 3:
                    ContextInfo contextInfo3 = (ContextInfo) parcel.readTypedObject(ContextInfo.CREATOR);
                    parcel.enforceNoDataAvail();
                    boolean isAuditLogEnabled = isAuditLogEnabled(contextInfo3);
                    parcel2.writeNoException();
                    parcel2.writeBoolean(isAuditLogEnabled);
                    return true;
                case 4:
                    ContextInfo contextInfo4 = (ContextInfo) parcel.readTypedObject(ContextInfo.CREATOR);
                    parcel.enforceNoDataAvail();
                    int currentLogFileSize = getCurrentLogFileSize(contextInfo4);
                    parcel2.writeNoException();
                    parcel2.writeInt(currentLogFileSize);
                    return true;
                case 5:
                    ContextInfo contextInfo5 = (ContextInfo) parcel.readTypedObject(ContextInfo.CREATOR);
                    int readInt = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    boolean criticalLogSize = setCriticalLogSize(contextInfo5, readInt);
                    parcel2.writeNoException();
                    parcel2.writeBoolean(criticalLogSize);
                    return true;
                case 6:
                    ContextInfo contextInfo6 = (ContextInfo) parcel.readTypedObject(ContextInfo.CREATOR);
                    parcel.enforceNoDataAvail();
                    int criticalLogSize2 = getCriticalLogSize(contextInfo6);
                    parcel2.writeNoException();
                    parcel2.writeInt(criticalLogSize2);
                    return true;
                case 7:
                    ContextInfo contextInfo7 = (ContextInfo) parcel.readTypedObject(ContextInfo.CREATOR);
                    int readInt2 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    boolean maximumLogSize = setMaximumLogSize(contextInfo7, readInt2);
                    parcel2.writeNoException();
                    parcel2.writeBoolean(maximumLogSize);
                    return true;
                case 8:
                    ContextInfo contextInfo8 = (ContextInfo) parcel.readTypedObject(ContextInfo.CREATOR);
                    parcel.enforceNoDataAvail();
                    int maximumLogSize2 = getMaximumLogSize(contextInfo8);
                    parcel2.writeNoException();
                    parcel2.writeInt(maximumLogSize2);
                    return true;
                case 9:
                    ContextInfo contextInfo9 = (ContextInfo) parcel.readTypedObject(ContextInfo.CREATOR);
                    int readInt3 = parcel.readInt();
                    int readInt4 = parcel.readInt();
                    boolean readBoolean = parcel.readBoolean();
                    int readInt5 = parcel.readInt();
                    String readString = parcel.readString();
                    String readString2 = parcel.readString();
                    parcel.enforceNoDataAvail();
                    AuditLogger(contextInfo9, readInt3, readInt4, readBoolean, readInt5, readString, readString2);
                    parcel2.writeNoException();
                    return true;
                case 10:
                    ContextInfo contextInfo10 = (ContextInfo) parcel.readTypedObject(ContextInfo.CREATOR);
                    long readLong = parcel.readLong();
                    long readLong2 = parcel.readLong();
                    String readString3 = parcel.readString();
                    ParcelFileDescriptor parcelFileDescriptor = (ParcelFileDescriptor) parcel.readTypedObject(ParcelFileDescriptor.CREATOR);
                    parcel.enforceNoDataAvail();
                    boolean dumpLogFile = dumpLogFile(contextInfo10, readLong, readLong2, readString3, parcelFileDescriptor);
                    parcel2.writeNoException();
                    parcel2.writeBoolean(dumpLogFile);
                    return true;
                case 11:
                    boolean isAuditServiceRunning = isAuditServiceRunning();
                    parcel2.writeNoException();
                    parcel2.writeBoolean(isAuditServiceRunning);
                    return true;
                case 12:
                    ContextInfo contextInfo11 = (ContextInfo) parcel.readTypedObject(ContextInfo.CREATOR);
                    AuditLogRulesInfo auditLogRulesInfo = (AuditLogRulesInfo) parcel.readTypedObject(AuditLogRulesInfo.CREATOR);
                    parcel.enforceNoDataAvail();
                    boolean auditLogRules = setAuditLogRules(contextInfo11, auditLogRulesInfo);
                    parcel2.writeNoException();
                    parcel2.writeBoolean(auditLogRules);
                    return true;
                case 13:
                    ContextInfo contextInfo12 = (ContextInfo) parcel.readTypedObject(ContextInfo.CREATOR);
                    parcel.enforceNoDataAvail();
                    AuditLogRulesInfo auditLogRules2 = getAuditLogRules(contextInfo12);
                    parcel2.writeNoException();
                    parcel2.writeTypedObject(auditLogRules2, 1);
                    return true;
                default:
                    return super.onTransact(i, parcel, parcel2, i2);
            }
        }

        @Override // android.os.IInterface
        public final IBinder asBinder() {
            return this;
        }
    }

    /* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
    public static class Default implements IAuditLog {
        @Override // android.os.IInterface
        public final IBinder asBinder() {
            return null;
        }

        @Override // com.samsung.android.knox.log.IAuditLog
        public final boolean disableAuditLog(ContextInfo contextInfo) {
            return false;
        }

        @Override // com.samsung.android.knox.log.IAuditLog
        public final boolean dumpLogFile(ContextInfo contextInfo, long j, long j2, String str, ParcelFileDescriptor parcelFileDescriptor) {
            return false;
        }

        @Override // com.samsung.android.knox.log.IAuditLog
        public final boolean enableAuditLog(ContextInfo contextInfo) {
            return false;
        }

        @Override // com.samsung.android.knox.log.IAuditLog
        public final AuditLogRulesInfo getAuditLogRules(ContextInfo contextInfo) {
            return null;
        }

        @Override // com.samsung.android.knox.log.IAuditLog
        public final int getCriticalLogSize(ContextInfo contextInfo) {
            return 0;
        }

        @Override // com.samsung.android.knox.log.IAuditLog
        public final int getCurrentLogFileSize(ContextInfo contextInfo) {
            return 0;
        }

        @Override // com.samsung.android.knox.log.IAuditLog
        public final int getMaximumLogSize(ContextInfo contextInfo) {
            return 0;
        }

        @Override // com.samsung.android.knox.log.IAuditLog
        public final boolean isAuditLogEnabled(ContextInfo contextInfo) {
            return false;
        }

        @Override // com.samsung.android.knox.log.IAuditLog
        public final boolean isAuditServiceRunning() {
            return false;
        }

        @Override // com.samsung.android.knox.log.IAuditLog
        public final boolean setAuditLogRules(ContextInfo contextInfo, AuditLogRulesInfo auditLogRulesInfo) {
            return false;
        }

        @Override // com.samsung.android.knox.log.IAuditLog
        public final boolean setCriticalLogSize(ContextInfo contextInfo, int i) {
            return false;
        }

        @Override // com.samsung.android.knox.log.IAuditLog
        public final boolean setMaximumLogSize(ContextInfo contextInfo, int i) {
            return false;
        }

        @Override // com.samsung.android.knox.log.IAuditLog
        public final void AuditLogger(ContextInfo contextInfo, int i, int i2, boolean z, int i3, String str, String str2) {
        }
    }
}
