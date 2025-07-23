package android.app.backup;

import android.annotation.SystemApi;
import android.content.Intent;
import android.content.pm.PackageInfo;
import android.os.IBinder;
import android.os.ParcelFileDescriptor;
import android.os.RemoteException;
import com.android.internal.backup.IBackupTransport;
import com.android.internal.backup.ITransportStatusCallback;
import com.android.internal.infra.AndroidFuture;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

@SystemApi
/* loaded from: classes.dex */
public class BackupTransport {
    public static final int AGENT_ERROR = -1003;
    public static final int AGENT_UNKNOWN = -1004;
    public static final String EXTRA_TRANSPORT_REGISTRATION = "android.app.backup.extra.TRANSPORT_REGISTRATION";
    public static final int FLAG_DATA_NOT_CHANGED = 8;
    public static final int FLAG_INCREMENTAL = 2;
    public static final int FLAG_NON_INCREMENTAL = 4;
    public static final int FLAG_USER_INITIATED = 1;
    public static final int NO_MORE_DATA = -1;
    public static final int TRANSPORT_ERROR = -1000;
    public static final int TRANSPORT_NON_INCREMENTAL_BACKUP_REQUIRED = -1006;
    public static final int TRANSPORT_NOT_INITIALIZED = -1001;
    public static final int TRANSPORT_OK = 0;
    public static final int TRANSPORT_PACKAGE_REJECTED = -1002;
    public static final int TRANSPORT_QUOTA_EXCEEDED = -1005;
    IBackupTransport mBinderImpl = new TransportImpl();

    public int abortFullRestore() {
        return 0;
    }

    public int checkFullBackupSize(long j) {
        return 0;
    }

    public int clearBackupData(PackageInfo packageInfo) {
        return -1000;
    }

    public Intent configurationIntent() {
        return null;
    }

    public Intent dataManagementIntent() {
        return null;
    }

    public int finishBackup() {
        return -1000;
    }

    public RestoreSet[] getAvailableRestoreSets() {
        return null;
    }

    public BackupManagerMonitor getBackupManagerMonitor() {
        return null;
    }

    public long getBackupQuota(String str, boolean z) {
        return Long.MAX_VALUE;
    }

    public long getCurrentRestoreSet() {
        return 0L;
    }

    public int getNextFullRestoreDataChunk(ParcelFileDescriptor parcelFileDescriptor) {
        return 0;
    }

    public int getRestoreData(ParcelFileDescriptor parcelFileDescriptor) {
        return -1000;
    }

    public int getTransportFlags() {
        return 0;
    }

    public int initializeDevice() {
        return -1000;
    }

    public boolean isAppEligibleForBackup(PackageInfo packageInfo, boolean z) {
        return true;
    }

    public RestoreDescription nextRestorePackage() {
        return null;
    }

    public int performBackup(PackageInfo packageInfo, ParcelFileDescriptor parcelFileDescriptor) {
        return -1000;
    }

    public int performFullBackup(PackageInfo packageInfo, ParcelFileDescriptor parcelFileDescriptor) {
        return -1002;
    }

    public long requestBackupTime() {
        return 0L;
    }

    public long requestFullBackupTime() {
        return 0L;
    }

    public int sendBackupData(int i) {
        return -1000;
    }

    public int startRestore(long j, PackageInfo[] packageInfoArr) {
        return -1000;
    }

    public IBinder getBinder() {
        return this.mBinderImpl.asBinder();
    }

    public String name() {
        throw new UnsupportedOperationException("Transport name() not implemented");
    }

    public String currentDestinationString() {
        throw new UnsupportedOperationException("Transport currentDestinationString() not implemented");
    }

    @Deprecated
    public String dataManagementLabel() {
        throw new UnsupportedOperationException("Transport dataManagementLabel() not implemented");
    }

    public CharSequence dataManagementIntentLabel() {
        return dataManagementLabel();
    }

    public String transportDirName() {
        throw new UnsupportedOperationException("Transport transportDirName() not implemented");
    }

    public int performBackup(PackageInfo packageInfo, ParcelFileDescriptor parcelFileDescriptor, int i) {
        return performBackup(packageInfo, parcelFileDescriptor);
    }

    public void finishRestore() {
        throw new UnsupportedOperationException("Transport finishRestore() not implemented");
    }

    public int performFullBackup(PackageInfo packageInfo, ParcelFileDescriptor parcelFileDescriptor, int i) {
        return performFullBackup(packageInfo, parcelFileDescriptor);
    }

    public void cancelFullBackup() {
        throw new UnsupportedOperationException("Transport cancelFullBackup() not implemented");
    }

    public List<String> getPackagesThatShouldNotUseRestrictedMode(List<String> list, int i) {
        return Collections.EMPTY_LIST;
    }

    class TransportImpl extends IBackupTransport.Stub {
        TransportImpl() {
        }

        @Override // com.android.internal.backup.IBackupTransport
        public void name(AndroidFuture<String> androidFuture) throws RemoteException {
            try {
                androidFuture.complete(BackupTransport.this.name());
            } catch (RuntimeException unused) {
                androidFuture.cancel(true);
            }
        }

        @Override // com.android.internal.backup.IBackupTransport
        public void configurationIntent(AndroidFuture<Intent> androidFuture) throws RemoteException {
            try {
                androidFuture.complete(BackupTransport.this.configurationIntent());
            } catch (RuntimeException unused) {
                androidFuture.cancel(true);
            }
        }

        @Override // com.android.internal.backup.IBackupTransport
        public void currentDestinationString(AndroidFuture<String> androidFuture) throws RemoteException {
            try {
                androidFuture.complete(BackupTransport.this.currentDestinationString());
            } catch (RuntimeException unused) {
                androidFuture.cancel(true);
            }
        }

        @Override // com.android.internal.backup.IBackupTransport
        public void dataManagementIntent(AndroidFuture<Intent> androidFuture) throws RemoteException {
            try {
                androidFuture.complete(BackupTransport.this.dataManagementIntent());
            } catch (RuntimeException unused) {
                androidFuture.cancel(true);
            }
        }

        @Override // com.android.internal.backup.IBackupTransport
        public void dataManagementIntentLabel(AndroidFuture<CharSequence> androidFuture) throws RemoteException {
            try {
                androidFuture.complete(BackupTransport.this.dataManagementIntentLabel());
            } catch (RuntimeException unused) {
                androidFuture.cancel(true);
            }
        }

        @Override // com.android.internal.backup.IBackupTransport
        public void transportDirName(AndroidFuture<String> androidFuture) throws RemoteException {
            try {
                androidFuture.complete(BackupTransport.this.transportDirName());
            } catch (RuntimeException unused) {
                androidFuture.cancel(true);
            }
        }

        @Override // com.android.internal.backup.IBackupTransport
        public void requestBackupTime(AndroidFuture<Long> androidFuture) throws RemoteException {
            try {
                androidFuture.complete(Long.valueOf(BackupTransport.this.requestBackupTime()));
            } catch (RuntimeException unused) {
                androidFuture.cancel(true);
            }
        }

        @Override // com.android.internal.backup.IBackupTransport
        public void initializeDevice(ITransportStatusCallback iTransportStatusCallback) throws RemoteException {
            try {
                iTransportStatusCallback.onOperationCompleteWithStatus(BackupTransport.this.initializeDevice());
            } catch (RuntimeException unused) {
                iTransportStatusCallback.onOperationCompleteWithStatus(-1000);
            }
        }

        @Override // com.android.internal.backup.IBackupTransport
        public void performBackup(PackageInfo packageInfo, ParcelFileDescriptor parcelFileDescriptor, int i, ITransportStatusCallback iTransportStatusCallback) throws RemoteException {
            try {
                iTransportStatusCallback.onOperationCompleteWithStatus(BackupTransport.this.performBackup(packageInfo, parcelFileDescriptor, i));
            } catch (RuntimeException unused) {
                iTransportStatusCallback.onOperationCompleteWithStatus(-1000);
            }
        }

        @Override // com.android.internal.backup.IBackupTransport
        public void clearBackupData(PackageInfo packageInfo, ITransportStatusCallback iTransportStatusCallback) throws RemoteException {
            try {
                iTransportStatusCallback.onOperationCompleteWithStatus(BackupTransport.this.clearBackupData(packageInfo));
            } catch (RuntimeException unused) {
                iTransportStatusCallback.onOperationCompleteWithStatus(-1000);
            }
        }

        @Override // com.android.internal.backup.IBackupTransport
        public void finishBackup(ITransportStatusCallback iTransportStatusCallback) throws RemoteException {
            try {
                iTransportStatusCallback.onOperationCompleteWithStatus(BackupTransport.this.finishBackup());
            } catch (RuntimeException unused) {
                iTransportStatusCallback.onOperationCompleteWithStatus(-1000);
            }
        }

        @Override // com.android.internal.backup.IBackupTransport
        public void getAvailableRestoreSets(AndroidFuture<List<RestoreSet>> androidFuture) throws RemoteException {
            try {
                androidFuture.complete(Arrays.asList(BackupTransport.this.getAvailableRestoreSets()));
            } catch (RuntimeException unused) {
                androidFuture.cancel(true);
            }
        }

        @Override // com.android.internal.backup.IBackupTransport
        public void getCurrentRestoreSet(AndroidFuture<Long> androidFuture) throws RemoteException {
            try {
                androidFuture.complete(Long.valueOf(BackupTransport.this.getCurrentRestoreSet()));
            } catch (RuntimeException unused) {
                androidFuture.cancel(true);
            }
        }

        @Override // com.android.internal.backup.IBackupTransport
        public void startRestore(long j, PackageInfo[] packageInfoArr, ITransportStatusCallback iTransportStatusCallback) throws RemoteException {
            try {
                iTransportStatusCallback.onOperationCompleteWithStatus(BackupTransport.this.startRestore(j, packageInfoArr));
            } catch (RuntimeException unused) {
                iTransportStatusCallback.onOperationCompleteWithStatus(-1000);
            }
        }

        @Override // com.android.internal.backup.IBackupTransport
        public void nextRestorePackage(AndroidFuture<RestoreDescription> androidFuture) throws RemoteException {
            try {
                androidFuture.complete(BackupTransport.this.nextRestorePackage());
            } catch (RuntimeException unused) {
                androidFuture.cancel(true);
            }
        }

        @Override // com.android.internal.backup.IBackupTransport
        public void getRestoreData(ParcelFileDescriptor parcelFileDescriptor, ITransportStatusCallback iTransportStatusCallback) throws RemoteException {
            try {
                iTransportStatusCallback.onOperationCompleteWithStatus(BackupTransport.this.getRestoreData(parcelFileDescriptor));
            } catch (RuntimeException unused) {
                iTransportStatusCallback.onOperationCompleteWithStatus(-1000);
            }
        }

        @Override // com.android.internal.backup.IBackupTransport
        public void finishRestore(ITransportStatusCallback iTransportStatusCallback) throws RemoteException {
            try {
                BackupTransport.this.finishRestore();
                iTransportStatusCallback.onOperationComplete();
            } catch (RuntimeException unused) {
                iTransportStatusCallback.onOperationCompleteWithStatus(-1000);
            }
        }

        @Override // com.android.internal.backup.IBackupTransport
        public void requestFullBackupTime(AndroidFuture<Long> androidFuture) throws RemoteException {
            try {
                androidFuture.complete(Long.valueOf(BackupTransport.this.requestFullBackupTime()));
            } catch (RuntimeException unused) {
                androidFuture.cancel(true);
            }
        }

        @Override // com.android.internal.backup.IBackupTransport
        public void performFullBackup(PackageInfo packageInfo, ParcelFileDescriptor parcelFileDescriptor, int i, ITransportStatusCallback iTransportStatusCallback) throws RemoteException {
            try {
                iTransportStatusCallback.onOperationCompleteWithStatus(BackupTransport.this.performFullBackup(packageInfo, parcelFileDescriptor, i));
            } catch (RuntimeException unused) {
                iTransportStatusCallback.onOperationCompleteWithStatus(-1000);
            }
        }

        @Override // com.android.internal.backup.IBackupTransport
        public void checkFullBackupSize(long j, ITransportStatusCallback iTransportStatusCallback) throws RemoteException {
            try {
                iTransportStatusCallback.onOperationCompleteWithStatus(BackupTransport.this.checkFullBackupSize(j));
            } catch (RuntimeException unused) {
                iTransportStatusCallback.onOperationCompleteWithStatus(-1000);
            }
        }

        @Override // com.android.internal.backup.IBackupTransport
        public void sendBackupData(int i, ITransportStatusCallback iTransportStatusCallback) throws RemoteException {
            try {
                iTransportStatusCallback.onOperationCompleteWithStatus(BackupTransport.this.sendBackupData(i));
            } catch (RuntimeException unused) {
                iTransportStatusCallback.onOperationCompleteWithStatus(-1000);
            }
        }

        @Override // com.android.internal.backup.IBackupTransport
        public void cancelFullBackup(ITransportStatusCallback iTransportStatusCallback) throws RemoteException {
            try {
                BackupTransport.this.cancelFullBackup();
                iTransportStatusCallback.onOperationComplete();
            } catch (RuntimeException unused) {
                iTransportStatusCallback.onOperationCompleteWithStatus(-1000);
            }
        }

        @Override // com.android.internal.backup.IBackupTransport
        public void isAppEligibleForBackup(PackageInfo packageInfo, boolean z, AndroidFuture<Boolean> androidFuture) throws RemoteException {
            try {
                androidFuture.complete(Boolean.valueOf(BackupTransport.this.isAppEligibleForBackup(packageInfo, z)));
            } catch (RuntimeException unused) {
                androidFuture.cancel(true);
            }
        }

        @Override // com.android.internal.backup.IBackupTransport
        public void getBackupQuota(String str, boolean z, AndroidFuture<Long> androidFuture) throws RemoteException {
            try {
                androidFuture.complete(Long.valueOf(BackupTransport.this.getBackupQuota(str, z)));
            } catch (RuntimeException unused) {
                androidFuture.cancel(true);
            }
        }

        @Override // com.android.internal.backup.IBackupTransport
        public void getTransportFlags(AndroidFuture<Integer> androidFuture) throws RemoteException {
            try {
                androidFuture.complete(Integer.valueOf(BackupTransport.this.getTransportFlags()));
            } catch (RuntimeException unused) {
                androidFuture.cancel(true);
            }
        }

        @Override // com.android.internal.backup.IBackupTransport
        public void getNextFullRestoreDataChunk(ParcelFileDescriptor parcelFileDescriptor, ITransportStatusCallback iTransportStatusCallback) throws RemoteException {
            try {
                iTransportStatusCallback.onOperationCompleteWithStatus(BackupTransport.this.getNextFullRestoreDataChunk(parcelFileDescriptor));
            } catch (RuntimeException unused) {
                iTransportStatusCallback.onOperationCompleteWithStatus(-1000);
            }
        }

        @Override // com.android.internal.backup.IBackupTransport
        public void abortFullRestore(ITransportStatusCallback iTransportStatusCallback) throws RemoteException {
            try {
                iTransportStatusCallback.onOperationCompleteWithStatus(BackupTransport.this.abortFullRestore());
            } catch (RuntimeException unused) {
                iTransportStatusCallback.onOperationCompleteWithStatus(-1000);
            }
        }

        @Override // com.android.internal.backup.IBackupTransport
        public void getBackupManagerMonitor(AndroidFuture<IBackupManagerMonitor> androidFuture) {
            try {
                androidFuture.complete(new BackupManagerMonitorWrapper(BackupTransport.this.getBackupManagerMonitor()));
            } catch (RuntimeException unused) {
                androidFuture.cancel(true);
            }
        }

        @Override // com.android.internal.backup.IBackupTransport
        public void getPackagesThatShouldNotUseRestrictedMode(List<String> list, int i, AndroidFuture<List<String>> androidFuture) {
            try {
                androidFuture.complete(BackupTransport.this.getPackagesThatShouldNotUseRestrictedMode(list, i));
            } catch (RuntimeException unused) {
                androidFuture.cancel(true);
            }
        }
    }
}
