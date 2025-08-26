package com.samsung.android.knox.log;

import android.os.Binder;
import android.os.IBinder;
import android.os.IInterface;
import android.os.Parcel;
import android.os.ParcelFileDescriptor;
import android.os.RemoteException;
import com.samsung.android.knox.ContextInfo;

/* loaded from: classes4.dex */
public interface IAuditLog extends IInterface {
    public static final String DESCRIPTOR = "com.samsung.android.knox.log.IAuditLog";

    boolean disableAuditLog(ContextInfo contextInfo) throws RemoteException;

    boolean dumpLogFile(ContextInfo contextInfo, long j, long j2, String str, ParcelFileDescriptor parcelFileDescriptor) throws RemoteException;

    boolean enableAuditLog(ContextInfo contextInfo) throws RemoteException;

    AuditLogRulesInfo getAuditLogRules(ContextInfo contextInfo) throws RemoteException;

    int getCriticalLogSize(ContextInfo contextInfo) throws RemoteException;

    int getCurrentLogFileSize(ContextInfo contextInfo) throws RemoteException;

    int getMaximumLogSize(ContextInfo contextInfo) throws RemoteException;

    boolean isAuditLogEnabled(ContextInfo contextInfo) throws RemoteException;

    boolean isAuditServiceRunning() throws RemoteException;

    void logEventForLegacyComponents(int i, LegacyEvent legacyEvent) throws RemoteException;

    boolean setAuditLogRules(ContextInfo contextInfo, AuditLogRulesInfo auditLogRulesInfo) throws RemoteException;

    boolean setCriticalLogSize(ContextInfo contextInfo, int i) throws RemoteException;

    boolean setMaximumLogSize(ContextInfo contextInfo, int i) throws RemoteException;

    public abstract class Stub extends Binder implements IAuditLog {
        public static final int TRANSACTION_disableAuditLog = 2;
        public static final int TRANSACTION_dumpLogFile = 10;
        public static final int TRANSACTION_enableAuditLog = 1;
        public static final int TRANSACTION_getAuditLogRules = 13;
        public static final int TRANSACTION_getCriticalLogSize = 6;
        public static final int TRANSACTION_getCurrentLogFileSize = 4;
        public static final int TRANSACTION_getMaximumLogSize = 8;
        public static final int TRANSACTION_isAuditLogEnabled = 3;
        public static final int TRANSACTION_isAuditServiceRunning = 11;
        public static final int TRANSACTION_logEventForLegacyComponents = 9;
        public static final int TRANSACTION_setAuditLogRules = 12;
        public static final int TRANSACTION_setCriticalLogSize = 5;
        public static final int TRANSACTION_setMaximumLogSize = 7;

        class Proxy implements IAuditLog {
            public IBinder mRemote;

            public Proxy(IBinder iBinder) {
                this.mRemote = iBinder;
            }

            @Override // android.os.IInterface
            public IBinder asBinder() {
                return this.mRemote;
            }

            @Override // com.samsung.android.knox.log.IAuditLog
            public boolean disableAuditLog(ContextInfo contextInfo) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(IAuditLog.DESCRIPTOR);
                    parcelObtain.writeTypedObject(contextInfo, 0);
                    this.mRemote.transact(2, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return parcelObtain2.readBoolean();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // com.samsung.android.knox.log.IAuditLog
            public boolean dumpLogFile(ContextInfo contextInfo, long j, long j2, String str, ParcelFileDescriptor parcelFileDescriptor) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(IAuditLog.DESCRIPTOR);
                    parcelObtain.writeTypedObject(contextInfo, 0);
                    parcelObtain.writeLong(j);
                    parcelObtain.writeLong(j2);
                    parcelObtain.writeString(str);
                    parcelObtain.writeTypedObject(parcelFileDescriptor, 0);
                    this.mRemote.transact(10, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return parcelObtain2.readBoolean();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // com.samsung.android.knox.log.IAuditLog
            public boolean enableAuditLog(ContextInfo contextInfo) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(IAuditLog.DESCRIPTOR);
                    parcelObtain.writeTypedObject(contextInfo, 0);
                    this.mRemote.transact(1, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return parcelObtain2.readBoolean();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // com.samsung.android.knox.log.IAuditLog
            public AuditLogRulesInfo getAuditLogRules(ContextInfo contextInfo) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(IAuditLog.DESCRIPTOR);
                    parcelObtain.writeTypedObject(contextInfo, 0);
                    this.mRemote.transact(13, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return (AuditLogRulesInfo) parcelObtain2.readTypedObject(AuditLogRulesInfo.CREATOR);
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // com.samsung.android.knox.log.IAuditLog
            public int getCriticalLogSize(ContextInfo contextInfo) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(IAuditLog.DESCRIPTOR);
                    parcelObtain.writeTypedObject(contextInfo, 0);
                    this.mRemote.transact(6, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return parcelObtain2.readInt();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // com.samsung.android.knox.log.IAuditLog
            public int getCurrentLogFileSize(ContextInfo contextInfo) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(IAuditLog.DESCRIPTOR);
                    parcelObtain.writeTypedObject(contextInfo, 0);
                    this.mRemote.transact(4, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return parcelObtain2.readInt();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            public String getInterfaceDescriptor() {
                return IAuditLog.DESCRIPTOR;
            }

            @Override // com.samsung.android.knox.log.IAuditLog
            public int getMaximumLogSize(ContextInfo contextInfo) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(IAuditLog.DESCRIPTOR);
                    parcelObtain.writeTypedObject(contextInfo, 0);
                    this.mRemote.transact(8, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return parcelObtain2.readInt();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // com.samsung.android.knox.log.IAuditLog
            public boolean isAuditLogEnabled(ContextInfo contextInfo) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(IAuditLog.DESCRIPTOR);
                    parcelObtain.writeTypedObject(contextInfo, 0);
                    this.mRemote.transact(3, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return parcelObtain2.readBoolean();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // com.samsung.android.knox.log.IAuditLog
            public boolean isAuditServiceRunning() throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(IAuditLog.DESCRIPTOR);
                    this.mRemote.transact(11, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return parcelObtain2.readBoolean();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // com.samsung.android.knox.log.IAuditLog
            public void logEventForLegacyComponents(int i, LegacyEvent legacyEvent) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(IAuditLog.DESCRIPTOR);
                    parcelObtain.writeInt(i);
                    parcelObtain.writeTypedObject(legacyEvent, 0);
                    this.mRemote.transact(9, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // com.samsung.android.knox.log.IAuditLog
            public boolean setAuditLogRules(ContextInfo contextInfo, AuditLogRulesInfo auditLogRulesInfo) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(IAuditLog.DESCRIPTOR);
                    parcelObtain.writeTypedObject(contextInfo, 0);
                    parcelObtain.writeTypedObject(auditLogRulesInfo, 0);
                    this.mRemote.transact(12, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return parcelObtain2.readBoolean();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // com.samsung.android.knox.log.IAuditLog
            public boolean setCriticalLogSize(ContextInfo contextInfo, int i) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(IAuditLog.DESCRIPTOR);
                    parcelObtain.writeTypedObject(contextInfo, 0);
                    parcelObtain.writeInt(i);
                    this.mRemote.transact(5, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return parcelObtain2.readBoolean();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // com.samsung.android.knox.log.IAuditLog
            public boolean setMaximumLogSize(ContextInfo contextInfo, int i) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(IAuditLog.DESCRIPTOR);
                    parcelObtain.writeTypedObject(contextInfo, 0);
                    parcelObtain.writeInt(i);
                    this.mRemote.transact(7, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return parcelObtain2.readBoolean();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
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
            IInterface iInterfaceQueryLocalInterface = iBinder.queryLocalInterface(IAuditLog.DESCRIPTOR);
            return (iInterfaceQueryLocalInterface == null || !(iInterfaceQueryLocalInterface instanceof IAuditLog)) ? new Proxy(iBinder) : (IAuditLog) iInterfaceQueryLocalInterface;
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
                    return "logEventForLegacyComponents";
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

        public int getMaxTransactionId() {
            return 12;
        }

        public String getTransactionName(int i) {
            return getDefaultTransactionName(i);
        }

        @Override // android.os.Binder
        public boolean onTransact(int i, Parcel parcel, Parcel parcel2, int i2) throws RemoteException {
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
                    boolean zEnableAuditLog = enableAuditLog(contextInfo);
                    parcel2.writeNoException();
                    parcel2.writeBoolean(zEnableAuditLog);
                    return true;
                case 2:
                    ContextInfo contextInfo2 = (ContextInfo) parcel.readTypedObject(ContextInfo.CREATOR);
                    parcel.enforceNoDataAvail();
                    boolean zDisableAuditLog = disableAuditLog(contextInfo2);
                    parcel2.writeNoException();
                    parcel2.writeBoolean(zDisableAuditLog);
                    return true;
                case 3:
                    ContextInfo contextInfo3 = (ContextInfo) parcel.readTypedObject(ContextInfo.CREATOR);
                    parcel.enforceNoDataAvail();
                    boolean zIsAuditLogEnabled = isAuditLogEnabled(contextInfo3);
                    parcel2.writeNoException();
                    parcel2.writeBoolean(zIsAuditLogEnabled);
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
                    int i3 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    boolean criticalLogSize = setCriticalLogSize(contextInfo5, i3);
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
                    int i4 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    boolean maximumLogSize = setMaximumLogSize(contextInfo7, i4);
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
                    int i5 = parcel.readInt();
                    LegacyEvent legacyEvent = (LegacyEvent) parcel.readTypedObject(LegacyEvent.CREATOR);
                    parcel.enforceNoDataAvail();
                    logEventForLegacyComponents(i5, legacyEvent);
                    parcel2.writeNoException();
                    return true;
                case 10:
                    ContextInfo contextInfo9 = (ContextInfo) parcel.readTypedObject(ContextInfo.CREATOR);
                    long j = parcel.readLong();
                    long j2 = parcel.readLong();
                    String string = parcel.readString();
                    ParcelFileDescriptor parcelFileDescriptor = (ParcelFileDescriptor) parcel.readTypedObject(ParcelFileDescriptor.CREATOR);
                    parcel.enforceNoDataAvail();
                    boolean zDumpLogFile = dumpLogFile(contextInfo9, j, j2, string, parcelFileDescriptor);
                    parcel2.writeNoException();
                    parcel2.writeBoolean(zDumpLogFile);
                    return true;
                case 11:
                    boolean zIsAuditServiceRunning = isAuditServiceRunning();
                    parcel2.writeNoException();
                    parcel2.writeBoolean(zIsAuditServiceRunning);
                    return true;
                case 12:
                    ContextInfo contextInfo10 = (ContextInfo) parcel.readTypedObject(ContextInfo.CREATOR);
                    AuditLogRulesInfo auditLogRulesInfo = (AuditLogRulesInfo) parcel.readTypedObject(AuditLogRulesInfo.CREATOR);
                    parcel.enforceNoDataAvail();
                    boolean auditLogRules = setAuditLogRules(contextInfo10, auditLogRulesInfo);
                    parcel2.writeNoException();
                    parcel2.writeBoolean(auditLogRules);
                    return true;
                case 13:
                    ContextInfo contextInfo11 = (ContextInfo) parcel.readTypedObject(ContextInfo.CREATOR);
                    parcel.enforceNoDataAvail();
                    AuditLogRulesInfo auditLogRules2 = getAuditLogRules(contextInfo11);
                    parcel2.writeNoException();
                    parcel2.writeTypedObject(auditLogRules2, 1);
                    return true;
                default:
                    return super.onTransact(i, parcel, parcel2, i2);
            }
        }

        @Override // android.os.IInterface
        public IBinder asBinder() {
            return this;
        }
    }

    public class Default implements IAuditLog {
        @Override // android.os.IInterface
        public IBinder asBinder() {
            return null;
        }

        @Override // com.samsung.android.knox.log.IAuditLog
        public boolean disableAuditLog(ContextInfo contextInfo) throws RemoteException {
            return false;
        }

        @Override // com.samsung.android.knox.log.IAuditLog
        public boolean dumpLogFile(ContextInfo contextInfo, long j, long j2, String str, ParcelFileDescriptor parcelFileDescriptor) throws RemoteException {
            return false;
        }

        @Override // com.samsung.android.knox.log.IAuditLog
        public boolean enableAuditLog(ContextInfo contextInfo) throws RemoteException {
            return false;
        }

        @Override // com.samsung.android.knox.log.IAuditLog
        public AuditLogRulesInfo getAuditLogRules(ContextInfo contextInfo) throws RemoteException {
            return null;
        }

        @Override // com.samsung.android.knox.log.IAuditLog
        public int getCriticalLogSize(ContextInfo contextInfo) throws RemoteException {
            return 0;
        }

        @Override // com.samsung.android.knox.log.IAuditLog
        public int getCurrentLogFileSize(ContextInfo contextInfo) throws RemoteException {
            return 0;
        }

        @Override // com.samsung.android.knox.log.IAuditLog
        public int getMaximumLogSize(ContextInfo contextInfo) throws RemoteException {
            return 0;
        }

        @Override // com.samsung.android.knox.log.IAuditLog
        public boolean isAuditLogEnabled(ContextInfo contextInfo) throws RemoteException {
            return false;
        }

        @Override // com.samsung.android.knox.log.IAuditLog
        public boolean isAuditServiceRunning() throws RemoteException {
            return false;
        }

        @Override // com.samsung.android.knox.log.IAuditLog
        public boolean setAuditLogRules(ContextInfo contextInfo, AuditLogRulesInfo auditLogRulesInfo) throws RemoteException {
            return false;
        }

        @Override // com.samsung.android.knox.log.IAuditLog
        public boolean setCriticalLogSize(ContextInfo contextInfo, int i) throws RemoteException {
            return false;
        }

        @Override // com.samsung.android.knox.log.IAuditLog
        public boolean setMaximumLogSize(ContextInfo contextInfo, int i) throws RemoteException {
            return false;
        }

        @Override // com.samsung.android.knox.log.IAuditLog
        public void logEventForLegacyComponents(int i, LegacyEvent legacyEvent) throws RemoteException {
        }
    }
}
