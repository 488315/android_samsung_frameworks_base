package android.content;

import android.Manifest;
import android.accounts.Account;
import android.app.ActivityThread;
import android.content.ISyncStatusObserver;
import android.database.IContentObserver;
import android.net.Uri;
import android.os.Binder;
import android.os.Bundle;
import android.os.IBinder;
import android.os.IInterface;
import android.os.Parcel;
import android.os.PermissionEnforcer;
import android.os.RemoteException;
import java.util.List;

/* loaded from: classes.dex */
public interface IContentService extends IInterface {

    public static class Default implements IContentService {
        @Override // android.content.IContentService
        public void addPeriodicSync(Account account, String str, Bundle bundle, long j) throws RemoteException {
        }

        @Override // android.content.IContentService
        public void addStatusChangeListener(int i, ISyncStatusObserver iSyncStatusObserver) throws RemoteException {
        }

        @Override // android.os.IInterface
        public IBinder asBinder() {
            return null;
        }

        @Override // android.content.IContentService
        public void cancelRequest(SyncRequest syncRequest) throws RemoteException {
        }

        @Override // android.content.IContentService
        public void cancelSync(Account account, String str, ComponentName componentName) throws RemoteException {
        }

        @Override // android.content.IContentService
        public void cancelSyncAsUser(Account account, String str, ComponentName componentName, int i) throws RemoteException {
        }

        @Override // android.content.IContentService
        public Bundle getCache(String str, Uri uri, int i) throws RemoteException {
            return null;
        }

        @Override // android.content.IContentService
        public List<SyncInfo> getCurrentSyncs() throws RemoteException {
            return null;
        }

        @Override // android.content.IContentService
        public List<SyncInfo> getCurrentSyncsAsUser(int i) throws RemoteException {
            return null;
        }

        @Override // android.content.IContentService
        public int getIsSyncable(Account account, String str) throws RemoteException {
            return 0;
        }

        @Override // android.content.IContentService
        public int getIsSyncableAsUser(Account account, String str, int i) throws RemoteException {
            return 0;
        }

        @Override // android.content.IContentService
        public boolean getMasterSyncAutomatically() throws RemoteException {
            return false;
        }

        @Override // android.content.IContentService
        public boolean getMasterSyncAutomaticallyAsUser(int i) throws RemoteException {
            return false;
        }

        @Override // android.content.IContentService
        public List<PeriodicSync> getPeriodicSyncs(Account account, String str, ComponentName componentName) throws RemoteException {
            return null;
        }

        @Override // android.content.IContentService
        public String getSyncAdapterPackageAsUser(String str, String str2, int i) throws RemoteException {
            return null;
        }

        @Override // android.content.IContentService
        public String[] getSyncAdapterPackagesForAuthorityAsUser(String str, int i) throws RemoteException {
            return null;
        }

        @Override // android.content.IContentService
        public SyncAdapterType[] getSyncAdapterTypes() throws RemoteException {
            return null;
        }

        @Override // android.content.IContentService
        public SyncAdapterType[] getSyncAdapterTypesAsUser(int i) throws RemoteException {
            return null;
        }

        @Override // android.content.IContentService
        public boolean getSyncAutomatically(Account account, String str) throws RemoteException {
            return false;
        }

        @Override // android.content.IContentService
        public boolean getSyncAutomaticallyAsUser(Account account, String str, int i) throws RemoteException {
            return false;
        }

        @Override // android.content.IContentService
        public SyncStatusInfo getSyncStatus(Account account, String str, ComponentName componentName) throws RemoteException {
            return null;
        }

        @Override // android.content.IContentService
        public SyncStatusInfo getSyncStatusAsUser(Account account, String str, ComponentName componentName, int i) throws RemoteException {
            return null;
        }

        @Override // android.content.IContentService
        public boolean isSyncActive(Account account, String str, ComponentName componentName) throws RemoteException {
            return false;
        }

        @Override // android.content.IContentService
        public boolean isSyncPending(Account account, String str, ComponentName componentName) throws RemoteException {
            return false;
        }

        @Override // android.content.IContentService
        public boolean isSyncPendingAsUser(Account account, String str, ComponentName componentName, int i) throws RemoteException {
            return false;
        }

        @Override // android.content.IContentService
        public void notifyChange(Uri[] uriArr, IContentObserver iContentObserver, boolean z, int i, int i2, int i3, String str) throws RemoteException {
        }

        @Override // android.content.IContentService
        public void onDbCorruption(String str, String str2, String str3) throws RemoteException {
        }

        @Override // android.content.IContentService
        public void putCache(String str, Uri uri, Bundle bundle, int i) throws RemoteException {
        }

        @Override // android.content.IContentService
        public void registerContentObserver(Uri uri, boolean z, IContentObserver iContentObserver, int i, int i2) throws RemoteException {
        }

        @Override // android.content.IContentService
        public void removePeriodicSync(Account account, String str, Bundle bundle) throws RemoteException {
        }

        @Override // android.content.IContentService
        public void removeStatusChangeListener(ISyncStatusObserver iSyncStatusObserver) throws RemoteException {
        }

        @Override // android.content.IContentService
        public void requestSync(Account account, String str, Bundle bundle, String str2) throws RemoteException {
        }

        @Override // android.content.IContentService
        public void resetTodayStats() throws RemoteException {
        }

        @Override // android.content.IContentService
        public void setIsSyncable(Account account, String str, int i) throws RemoteException {
        }

        @Override // android.content.IContentService
        public void setIsSyncableAsUser(Account account, String str, int i, int i2) throws RemoteException {
        }

        @Override // android.content.IContentService
        public void setMasterSyncAutomatically(boolean z) throws RemoteException {
        }

        @Override // android.content.IContentService
        public void setMasterSyncAutomaticallyAsUser(boolean z, int i) throws RemoteException {
        }

        @Override // android.content.IContentService
        public void setSyncAutomatically(Account account, String str, boolean z) throws RemoteException {
        }

        @Override // android.content.IContentService
        public void setSyncAutomaticallyAsUser(Account account, String str, boolean z, int i) throws RemoteException {
        }

        @Override // android.content.IContentService
        public void sync(SyncRequest syncRequest, String str) throws RemoteException {
        }

        @Override // android.content.IContentService
        public void syncAsUser(SyncRequest syncRequest, int i, String str) throws RemoteException {
        }

        @Override // android.content.IContentService
        public void unregisterContentObserver(IContentObserver iContentObserver) throws RemoteException {
        }
    }

    void addPeriodicSync(Account account, String str, Bundle bundle, long j) throws RemoteException;

    void addStatusChangeListener(int i, ISyncStatusObserver iSyncStatusObserver) throws RemoteException;

    void cancelRequest(SyncRequest syncRequest) throws RemoteException;

    void cancelSync(Account account, String str, ComponentName componentName) throws RemoteException;

    void cancelSyncAsUser(Account account, String str, ComponentName componentName, int i) throws RemoteException;

    Bundle getCache(String str, Uri uri, int i) throws RemoteException;

    List<SyncInfo> getCurrentSyncs() throws RemoteException;

    List<SyncInfo> getCurrentSyncsAsUser(int i) throws RemoteException;

    int getIsSyncable(Account account, String str) throws RemoteException;

    int getIsSyncableAsUser(Account account, String str, int i) throws RemoteException;

    boolean getMasterSyncAutomatically() throws RemoteException;

    boolean getMasterSyncAutomaticallyAsUser(int i) throws RemoteException;

    List<PeriodicSync> getPeriodicSyncs(Account account, String str, ComponentName componentName) throws RemoteException;

    String getSyncAdapterPackageAsUser(String str, String str2, int i) throws RemoteException;

    String[] getSyncAdapterPackagesForAuthorityAsUser(String str, int i) throws RemoteException;

    SyncAdapterType[] getSyncAdapterTypes() throws RemoteException;

    SyncAdapterType[] getSyncAdapterTypesAsUser(int i) throws RemoteException;

    boolean getSyncAutomatically(Account account, String str) throws RemoteException;

    boolean getSyncAutomaticallyAsUser(Account account, String str, int i) throws RemoteException;

    SyncStatusInfo getSyncStatus(Account account, String str, ComponentName componentName) throws RemoteException;

    SyncStatusInfo getSyncStatusAsUser(Account account, String str, ComponentName componentName, int i) throws RemoteException;

    boolean isSyncActive(Account account, String str, ComponentName componentName) throws RemoteException;

    boolean isSyncPending(Account account, String str, ComponentName componentName) throws RemoteException;

    boolean isSyncPendingAsUser(Account account, String str, ComponentName componentName, int i) throws RemoteException;

    void notifyChange(Uri[] uriArr, IContentObserver iContentObserver, boolean z, int i, int i2, int i3, String str) throws RemoteException;

    void onDbCorruption(String str, String str2, String str3) throws RemoteException;

    void putCache(String str, Uri uri, Bundle bundle, int i) throws RemoteException;

    void registerContentObserver(Uri uri, boolean z, IContentObserver iContentObserver, int i, int i2) throws RemoteException;

    void removePeriodicSync(Account account, String str, Bundle bundle) throws RemoteException;

    void removeStatusChangeListener(ISyncStatusObserver iSyncStatusObserver) throws RemoteException;

    void requestSync(Account account, String str, Bundle bundle, String str2) throws RemoteException;

    void resetTodayStats() throws RemoteException;

    void setIsSyncable(Account account, String str, int i) throws RemoteException;

    void setIsSyncableAsUser(Account account, String str, int i, int i2) throws RemoteException;

    void setMasterSyncAutomatically(boolean z) throws RemoteException;

    void setMasterSyncAutomaticallyAsUser(boolean z, int i) throws RemoteException;

    void setSyncAutomatically(Account account, String str, boolean z) throws RemoteException;

    void setSyncAutomaticallyAsUser(Account account, String str, boolean z, int i) throws RemoteException;

    void sync(SyncRequest syncRequest, String str) throws RemoteException;

    void syncAsUser(SyncRequest syncRequest, int i, String str) throws RemoteException;

    void unregisterContentObserver(IContentObserver iContentObserver) throws RemoteException;

    public static abstract class Stub extends Binder implements IContentService {
        public static final String DESCRIPTOR = "android.content.IContentService";
        static final int TRANSACTION_addPeriodicSync = 15;
        static final int TRANSACTION_addStatusChangeListener = 36;
        static final int TRANSACTION_cancelRequest = 9;
        static final int TRANSACTION_cancelSync = 7;
        static final int TRANSACTION_cancelSyncAsUser = 8;
        static final int TRANSACTION_getCache = 39;
        static final int TRANSACTION_getCurrentSyncs = 25;
        static final int TRANSACTION_getCurrentSyncsAsUser = 26;
        static final int TRANSACTION_getIsSyncable = 17;
        static final int TRANSACTION_getIsSyncableAsUser = 18;
        static final int TRANSACTION_getMasterSyncAutomatically = 23;
        static final int TRANSACTION_getMasterSyncAutomaticallyAsUser = 24;
        static final int TRANSACTION_getPeriodicSyncs = 14;
        static final int TRANSACTION_getSyncAdapterPackageAsUser = 30;
        static final int TRANSACTION_getSyncAdapterPackagesForAuthorityAsUser = 29;
        static final int TRANSACTION_getSyncAdapterTypes = 27;
        static final int TRANSACTION_getSyncAdapterTypesAsUser = 28;
        static final int TRANSACTION_getSyncAutomatically = 10;
        static final int TRANSACTION_getSyncAutomaticallyAsUser = 11;
        static final int TRANSACTION_getSyncStatus = 32;
        static final int TRANSACTION_getSyncStatusAsUser = 33;
        static final int TRANSACTION_isSyncActive = 31;
        static final int TRANSACTION_isSyncPending = 34;
        static final int TRANSACTION_isSyncPendingAsUser = 35;
        static final int TRANSACTION_notifyChange = 3;
        static final int TRANSACTION_onDbCorruption = 41;
        static final int TRANSACTION_putCache = 38;
        static final int TRANSACTION_registerContentObserver = 2;
        static final int TRANSACTION_removePeriodicSync = 16;
        static final int TRANSACTION_removeStatusChangeListener = 37;
        static final int TRANSACTION_requestSync = 4;
        static final int TRANSACTION_resetTodayStats = 40;
        static final int TRANSACTION_setIsSyncable = 19;
        static final int TRANSACTION_setIsSyncableAsUser = 20;
        static final int TRANSACTION_setMasterSyncAutomatically = 21;
        static final int TRANSACTION_setMasterSyncAutomaticallyAsUser = 22;
        static final int TRANSACTION_setSyncAutomatically = 12;
        static final int TRANSACTION_setSyncAutomaticallyAsUser = 13;
        static final int TRANSACTION_sync = 5;
        static final int TRANSACTION_syncAsUser = 6;
        static final int TRANSACTION_unregisterContentObserver = 1;
        private final PermissionEnforcer mEnforcer;

        @Override // android.os.IInterface
        public IBinder asBinder() {
            return this;
        }

        @Override // android.os.Binder
        public int getMaxTransactionId() {
            return 40;
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

        public static IContentService asInterface(IBinder iBinder) {
            if (iBinder == null) {
                return null;
            }
            IInterface queryLocalInterface = iBinder.queryLocalInterface(DESCRIPTOR);
            if (queryLocalInterface != null && (queryLocalInterface instanceof IContentService)) {
                return (IContentService) queryLocalInterface;
            }
            return new Proxy(iBinder);
        }

        public static String getDefaultTransactionName(int i) {
            switch (i) {
                case 1:
                    return "unregisterContentObserver";
                case 2:
                    return "registerContentObserver";
                case 3:
                    return "notifyChange";
                case 4:
                    return "requestSync";
                case 5:
                    return "sync";
                case 6:
                    return "syncAsUser";
                case 7:
                    return "cancelSync";
                case 8:
                    return "cancelSyncAsUser";
                case 9:
                    return "cancelRequest";
                case 10:
                    return "getSyncAutomatically";
                case 11:
                    return "getSyncAutomaticallyAsUser";
                case 12:
                    return "setSyncAutomatically";
                case 13:
                    return "setSyncAutomaticallyAsUser";
                case 14:
                    return "getPeriodicSyncs";
                case 15:
                    return "addPeriodicSync";
                case 16:
                    return "removePeriodicSync";
                case 17:
                    return "getIsSyncable";
                case 18:
                    return "getIsSyncableAsUser";
                case 19:
                    return "setIsSyncable";
                case 20:
                    return "setIsSyncableAsUser";
                case 21:
                    return "setMasterSyncAutomatically";
                case 22:
                    return "setMasterSyncAutomaticallyAsUser";
                case 23:
                    return "getMasterSyncAutomatically";
                case 24:
                    return "getMasterSyncAutomaticallyAsUser";
                case 25:
                    return "getCurrentSyncs";
                case 26:
                    return "getCurrentSyncsAsUser";
                case 27:
                    return "getSyncAdapterTypes";
                case 28:
                    return "getSyncAdapterTypesAsUser";
                case 29:
                    return "getSyncAdapterPackagesForAuthorityAsUser";
                case 30:
                    return "getSyncAdapterPackageAsUser";
                case 31:
                    return "isSyncActive";
                case 32:
                    return "getSyncStatus";
                case 33:
                    return "getSyncStatusAsUser";
                case 34:
                    return "isSyncPending";
                case 35:
                    return "isSyncPendingAsUser";
                case 36:
                    return "addStatusChangeListener";
                case 37:
                    return "removeStatusChangeListener";
                case 38:
                    return "putCache";
                case 39:
                    return "getCache";
                case 40:
                    return "resetTodayStats";
                case 41:
                    return "onDbCorruption";
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
                    IContentObserver asInterface = IContentObserver.Stub.asInterface(parcel.readStrongBinder());
                    parcel.enforceNoDataAvail();
                    unregisterContentObserver(asInterface);
                    parcel2.writeNoException();
                    return true;
                case 2:
                    Uri uri = (Uri) parcel.readTypedObject(Uri.CREATOR);
                    boolean readBoolean = parcel.readBoolean();
                    IContentObserver asInterface2 = IContentObserver.Stub.asInterface(parcel.readStrongBinder());
                    int readInt = parcel.readInt();
                    int readInt2 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    registerContentObserver(uri, readBoolean, asInterface2, readInt, readInt2);
                    parcel2.writeNoException();
                    return true;
                case 3:
                    Uri[] uriArr = (Uri[]) parcel.createTypedArray(Uri.CREATOR);
                    IContentObserver asInterface3 = IContentObserver.Stub.asInterface(parcel.readStrongBinder());
                    boolean readBoolean2 = parcel.readBoolean();
                    int readInt3 = parcel.readInt();
                    int readInt4 = parcel.readInt();
                    int readInt5 = parcel.readInt();
                    String readString = parcel.readString();
                    parcel.enforceNoDataAvail();
                    notifyChange(uriArr, asInterface3, readBoolean2, readInt3, readInt4, readInt5, readString);
                    parcel2.writeNoException();
                    return true;
                case 4:
                    Account account = (Account) parcel.readTypedObject(Account.CREATOR);
                    String readString2 = parcel.readString();
                    Bundle bundle = (Bundle) parcel.readTypedObject(Bundle.CREATOR);
                    String readString3 = parcel.readString();
                    parcel.enforceNoDataAvail();
                    requestSync(account, readString2, bundle, readString3);
                    parcel2.writeNoException();
                    return true;
                case 5:
                    SyncRequest syncRequest = (SyncRequest) parcel.readTypedObject(SyncRequest.CREATOR);
                    String readString4 = parcel.readString();
                    parcel.enforceNoDataAvail();
                    sync(syncRequest, readString4);
                    parcel2.writeNoException();
                    return true;
                case 6:
                    SyncRequest syncRequest2 = (SyncRequest) parcel.readTypedObject(SyncRequest.CREATOR);
                    int readInt6 = parcel.readInt();
                    String readString5 = parcel.readString();
                    parcel.enforceNoDataAvail();
                    syncAsUser(syncRequest2, readInt6, readString5);
                    parcel2.writeNoException();
                    return true;
                case 7:
                    Account account2 = (Account) parcel.readTypedObject(Account.CREATOR);
                    String readString6 = parcel.readString();
                    ComponentName componentName = (ComponentName) parcel.readTypedObject(ComponentName.CREATOR);
                    parcel.enforceNoDataAvail();
                    cancelSync(account2, readString6, componentName);
                    parcel2.writeNoException();
                    return true;
                case 8:
                    Account account3 = (Account) parcel.readTypedObject(Account.CREATOR);
                    String readString7 = parcel.readString();
                    ComponentName componentName2 = (ComponentName) parcel.readTypedObject(ComponentName.CREATOR);
                    int readInt7 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    cancelSyncAsUser(account3, readString7, componentName2, readInt7);
                    parcel2.writeNoException();
                    return true;
                case 9:
                    SyncRequest syncRequest3 = (SyncRequest) parcel.readTypedObject(SyncRequest.CREATOR);
                    parcel.enforceNoDataAvail();
                    cancelRequest(syncRequest3);
                    parcel2.writeNoException();
                    return true;
                case 10:
                    Account account4 = (Account) parcel.readTypedObject(Account.CREATOR);
                    String readString8 = parcel.readString();
                    parcel.enforceNoDataAvail();
                    boolean syncAutomatically = getSyncAutomatically(account4, readString8);
                    parcel2.writeNoException();
                    parcel2.writeBoolean(syncAutomatically);
                    return true;
                case 11:
                    Account account5 = (Account) parcel.readTypedObject(Account.CREATOR);
                    String readString9 = parcel.readString();
                    int readInt8 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    boolean syncAutomaticallyAsUser = getSyncAutomaticallyAsUser(account5, readString9, readInt8);
                    parcel2.writeNoException();
                    parcel2.writeBoolean(syncAutomaticallyAsUser);
                    return true;
                case 12:
                    Account account6 = (Account) parcel.readTypedObject(Account.CREATOR);
                    String readString10 = parcel.readString();
                    boolean readBoolean3 = parcel.readBoolean();
                    parcel.enforceNoDataAvail();
                    setSyncAutomatically(account6, readString10, readBoolean3);
                    parcel2.writeNoException();
                    return true;
                case 13:
                    Account account7 = (Account) parcel.readTypedObject(Account.CREATOR);
                    String readString11 = parcel.readString();
                    boolean readBoolean4 = parcel.readBoolean();
                    int readInt9 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    setSyncAutomaticallyAsUser(account7, readString11, readBoolean4, readInt9);
                    parcel2.writeNoException();
                    return true;
                case 14:
                    Account account8 = (Account) parcel.readTypedObject(Account.CREATOR);
                    String readString12 = parcel.readString();
                    ComponentName componentName3 = (ComponentName) parcel.readTypedObject(ComponentName.CREATOR);
                    parcel.enforceNoDataAvail();
                    List<PeriodicSync> periodicSyncs = getPeriodicSyncs(account8, readString12, componentName3);
                    parcel2.writeNoException();
                    parcel2.writeTypedList(periodicSyncs, 1);
                    return true;
                case 15:
                    Account account9 = (Account) parcel.readTypedObject(Account.CREATOR);
                    String readString13 = parcel.readString();
                    Bundle bundle2 = (Bundle) parcel.readTypedObject(Bundle.CREATOR);
                    long readLong = parcel.readLong();
                    parcel.enforceNoDataAvail();
                    addPeriodicSync(account9, readString13, bundle2, readLong);
                    parcel2.writeNoException();
                    return true;
                case 16:
                    Account account10 = (Account) parcel.readTypedObject(Account.CREATOR);
                    String readString14 = parcel.readString();
                    Bundle bundle3 = (Bundle) parcel.readTypedObject(Bundle.CREATOR);
                    parcel.enforceNoDataAvail();
                    removePeriodicSync(account10, readString14, bundle3);
                    parcel2.writeNoException();
                    return true;
                case 17:
                    Account account11 = (Account) parcel.readTypedObject(Account.CREATOR);
                    String readString15 = parcel.readString();
                    parcel.enforceNoDataAvail();
                    int isSyncable = getIsSyncable(account11, readString15);
                    parcel2.writeNoException();
                    parcel2.writeInt(isSyncable);
                    return true;
                case 18:
                    Account account12 = (Account) parcel.readTypedObject(Account.CREATOR);
                    String readString16 = parcel.readString();
                    int readInt10 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    int isSyncableAsUser = getIsSyncableAsUser(account12, readString16, readInt10);
                    parcel2.writeNoException();
                    parcel2.writeInt(isSyncableAsUser);
                    return true;
                case 19:
                    Account account13 = (Account) parcel.readTypedObject(Account.CREATOR);
                    String readString17 = parcel.readString();
                    int readInt11 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    setIsSyncable(account13, readString17, readInt11);
                    parcel2.writeNoException();
                    return true;
                case 20:
                    Account account14 = (Account) parcel.readTypedObject(Account.CREATOR);
                    String readString18 = parcel.readString();
                    int readInt12 = parcel.readInt();
                    int readInt13 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    setIsSyncableAsUser(account14, readString18, readInt12, readInt13);
                    parcel2.writeNoException();
                    return true;
                case 21:
                    boolean readBoolean5 = parcel.readBoolean();
                    parcel.enforceNoDataAvail();
                    setMasterSyncAutomatically(readBoolean5);
                    parcel2.writeNoException();
                    return true;
                case 22:
                    boolean readBoolean6 = parcel.readBoolean();
                    int readInt14 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    setMasterSyncAutomaticallyAsUser(readBoolean6, readInt14);
                    parcel2.writeNoException();
                    return true;
                case 23:
                    boolean masterSyncAutomatically = getMasterSyncAutomatically();
                    parcel2.writeNoException();
                    parcel2.writeBoolean(masterSyncAutomatically);
                    return true;
                case 24:
                    int readInt15 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    boolean masterSyncAutomaticallyAsUser = getMasterSyncAutomaticallyAsUser(readInt15);
                    parcel2.writeNoException();
                    parcel2.writeBoolean(masterSyncAutomaticallyAsUser);
                    return true;
                case 25:
                    List<SyncInfo> currentSyncs = getCurrentSyncs();
                    parcel2.writeNoException();
                    parcel2.writeTypedList(currentSyncs, 1);
                    return true;
                case 26:
                    int readInt16 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    List<SyncInfo> currentSyncsAsUser = getCurrentSyncsAsUser(readInt16);
                    parcel2.writeNoException();
                    parcel2.writeTypedList(currentSyncsAsUser, 1);
                    return true;
                case 27:
                    SyncAdapterType[] syncAdapterTypes = getSyncAdapterTypes();
                    parcel2.writeNoException();
                    parcel2.writeTypedArray(syncAdapterTypes, 1);
                    return true;
                case 28:
                    int readInt17 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    SyncAdapterType[] syncAdapterTypesAsUser = getSyncAdapterTypesAsUser(readInt17);
                    parcel2.writeNoException();
                    parcel2.writeTypedArray(syncAdapterTypesAsUser, 1);
                    return true;
                case 29:
                    String readString19 = parcel.readString();
                    int readInt18 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    String[] syncAdapterPackagesForAuthorityAsUser = getSyncAdapterPackagesForAuthorityAsUser(readString19, readInt18);
                    parcel2.writeNoException();
                    parcel2.writeStringArray(syncAdapterPackagesForAuthorityAsUser);
                    return true;
                case 30:
                    String readString20 = parcel.readString();
                    String readString21 = parcel.readString();
                    int readInt19 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    String syncAdapterPackageAsUser = getSyncAdapterPackageAsUser(readString20, readString21, readInt19);
                    parcel2.writeNoException();
                    parcel2.writeString(syncAdapterPackageAsUser);
                    return true;
                case 31:
                    Account account15 = (Account) parcel.readTypedObject(Account.CREATOR);
                    String readString22 = parcel.readString();
                    ComponentName componentName4 = (ComponentName) parcel.readTypedObject(ComponentName.CREATOR);
                    parcel.enforceNoDataAvail();
                    boolean isSyncActive = isSyncActive(account15, readString22, componentName4);
                    parcel2.writeNoException();
                    parcel2.writeBoolean(isSyncActive);
                    return true;
                case 32:
                    Account account16 = (Account) parcel.readTypedObject(Account.CREATOR);
                    String readString23 = parcel.readString();
                    ComponentName componentName5 = (ComponentName) parcel.readTypedObject(ComponentName.CREATOR);
                    parcel.enforceNoDataAvail();
                    SyncStatusInfo syncStatus = getSyncStatus(account16, readString23, componentName5);
                    parcel2.writeNoException();
                    parcel2.writeTypedObject(syncStatus, 1);
                    return true;
                case 33:
                    Account account17 = (Account) parcel.readTypedObject(Account.CREATOR);
                    String readString24 = parcel.readString();
                    ComponentName componentName6 = (ComponentName) parcel.readTypedObject(ComponentName.CREATOR);
                    int readInt20 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    SyncStatusInfo syncStatusAsUser = getSyncStatusAsUser(account17, readString24, componentName6, readInt20);
                    parcel2.writeNoException();
                    parcel2.writeTypedObject(syncStatusAsUser, 1);
                    return true;
                case 34:
                    Account account18 = (Account) parcel.readTypedObject(Account.CREATOR);
                    String readString25 = parcel.readString();
                    ComponentName componentName7 = (ComponentName) parcel.readTypedObject(ComponentName.CREATOR);
                    parcel.enforceNoDataAvail();
                    boolean isSyncPending = isSyncPending(account18, readString25, componentName7);
                    parcel2.writeNoException();
                    parcel2.writeBoolean(isSyncPending);
                    return true;
                case 35:
                    Account account19 = (Account) parcel.readTypedObject(Account.CREATOR);
                    String readString26 = parcel.readString();
                    ComponentName componentName8 = (ComponentName) parcel.readTypedObject(ComponentName.CREATOR);
                    int readInt21 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    boolean isSyncPendingAsUser = isSyncPendingAsUser(account19, readString26, componentName8, readInt21);
                    parcel2.writeNoException();
                    parcel2.writeBoolean(isSyncPendingAsUser);
                    return true;
                case 36:
                    int readInt22 = parcel.readInt();
                    ISyncStatusObserver asInterface4 = ISyncStatusObserver.Stub.asInterface(parcel.readStrongBinder());
                    parcel.enforceNoDataAvail();
                    addStatusChangeListener(readInt22, asInterface4);
                    parcel2.writeNoException();
                    return true;
                case 37:
                    ISyncStatusObserver asInterface5 = ISyncStatusObserver.Stub.asInterface(parcel.readStrongBinder());
                    parcel.enforceNoDataAvail();
                    removeStatusChangeListener(asInterface5);
                    parcel2.writeNoException();
                    return true;
                case 38:
                    String readString27 = parcel.readString();
                    Uri uri2 = (Uri) parcel.readTypedObject(Uri.CREATOR);
                    Bundle bundle4 = (Bundle) parcel.readTypedObject(Bundle.CREATOR);
                    int readInt23 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    putCache(readString27, uri2, bundle4, readInt23);
                    parcel2.writeNoException();
                    return true;
                case 39:
                    String readString28 = parcel.readString();
                    Uri uri3 = (Uri) parcel.readTypedObject(Uri.CREATOR);
                    int readInt24 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    Bundle cache = getCache(readString28, uri3, readInt24);
                    parcel2.writeNoException();
                    parcel2.writeTypedObject(cache, 1);
                    return true;
                case 40:
                    resetTodayStats();
                    parcel2.writeNoException();
                    return true;
                case 41:
                    String readString29 = parcel.readString();
                    String readString30 = parcel.readString();
                    String readString31 = parcel.readString();
                    parcel.enforceNoDataAvail();
                    onDbCorruption(readString29, readString30, readString31);
                    parcel2.writeNoException();
                    return true;
                default:
                    return super.onTransact(i, parcel, parcel2, i2);
            }
        }

        private static class Proxy implements IContentService {
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

            @Override // android.content.IContentService
            public void unregisterContentObserver(IContentObserver iContentObserver) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    obtain.writeStrongInterface(iContentObserver);
                    this.mRemote.transact(1, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.content.IContentService
            public void registerContentObserver(Uri uri, boolean z, IContentObserver iContentObserver, int i, int i2) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    obtain.writeTypedObject(uri, 0);
                    obtain.writeBoolean(z);
                    obtain.writeStrongInterface(iContentObserver);
                    obtain.writeInt(i);
                    obtain.writeInt(i2);
                    this.mRemote.transact(2, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.content.IContentService
            public void notifyChange(Uri[] uriArr, IContentObserver iContentObserver, boolean z, int i, int i2, int i3, String str) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    obtain.writeTypedArray(uriArr, 0);
                    obtain.writeStrongInterface(iContentObserver);
                    obtain.writeBoolean(z);
                    obtain.writeInt(i);
                    obtain.writeInt(i2);
                    obtain.writeInt(i3);
                    obtain.writeString(str);
                    this.mRemote.transact(3, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.content.IContentService
            public void requestSync(Account account, String str, Bundle bundle, String str2) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    obtain.writeTypedObject(account, 0);
                    obtain.writeString(str);
                    obtain.writeTypedObject(bundle, 0);
                    obtain.writeString(str2);
                    this.mRemote.transact(4, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.content.IContentService
            public void sync(SyncRequest syncRequest, String str) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    obtain.writeTypedObject(syncRequest, 0);
                    obtain.writeString(str);
                    this.mRemote.transact(5, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.content.IContentService
            public void syncAsUser(SyncRequest syncRequest, int i, String str) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    obtain.writeTypedObject(syncRequest, 0);
                    obtain.writeInt(i);
                    obtain.writeString(str);
                    this.mRemote.transact(6, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.content.IContentService
            public void cancelSync(Account account, String str, ComponentName componentName) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    obtain.writeTypedObject(account, 0);
                    obtain.writeString(str);
                    obtain.writeTypedObject(componentName, 0);
                    this.mRemote.transact(7, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.content.IContentService
            public void cancelSyncAsUser(Account account, String str, ComponentName componentName, int i) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    obtain.writeTypedObject(account, 0);
                    obtain.writeString(str);
                    obtain.writeTypedObject(componentName, 0);
                    obtain.writeInt(i);
                    this.mRemote.transact(8, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.content.IContentService
            public void cancelRequest(SyncRequest syncRequest) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    obtain.writeTypedObject(syncRequest, 0);
                    this.mRemote.transact(9, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.content.IContentService
            public boolean getSyncAutomatically(Account account, String str) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    obtain.writeTypedObject(account, 0);
                    obtain.writeString(str);
                    this.mRemote.transact(10, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readBoolean();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.content.IContentService
            public boolean getSyncAutomaticallyAsUser(Account account, String str, int i) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    obtain.writeTypedObject(account, 0);
                    obtain.writeString(str);
                    obtain.writeInt(i);
                    this.mRemote.transact(11, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readBoolean();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.content.IContentService
            public void setSyncAutomatically(Account account, String str, boolean z) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    obtain.writeTypedObject(account, 0);
                    obtain.writeString(str);
                    obtain.writeBoolean(z);
                    this.mRemote.transact(12, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.content.IContentService
            public void setSyncAutomaticallyAsUser(Account account, String str, boolean z, int i) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    obtain.writeTypedObject(account, 0);
                    obtain.writeString(str);
                    obtain.writeBoolean(z);
                    obtain.writeInt(i);
                    this.mRemote.transact(13, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.content.IContentService
            public List<PeriodicSync> getPeriodicSyncs(Account account, String str, ComponentName componentName) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    obtain.writeTypedObject(account, 0);
                    obtain.writeString(str);
                    obtain.writeTypedObject(componentName, 0);
                    this.mRemote.transact(14, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.createTypedArrayList(PeriodicSync.CREATOR);
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.content.IContentService
            public void addPeriodicSync(Account account, String str, Bundle bundle, long j) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    obtain.writeTypedObject(account, 0);
                    obtain.writeString(str);
                    obtain.writeTypedObject(bundle, 0);
                    obtain.writeLong(j);
                    this.mRemote.transact(15, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.content.IContentService
            public void removePeriodicSync(Account account, String str, Bundle bundle) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    obtain.writeTypedObject(account, 0);
                    obtain.writeString(str);
                    obtain.writeTypedObject(bundle, 0);
                    this.mRemote.transact(16, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.content.IContentService
            public int getIsSyncable(Account account, String str) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    obtain.writeTypedObject(account, 0);
                    obtain.writeString(str);
                    this.mRemote.transact(17, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readInt();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.content.IContentService
            public int getIsSyncableAsUser(Account account, String str, int i) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    obtain.writeTypedObject(account, 0);
                    obtain.writeString(str);
                    obtain.writeInt(i);
                    this.mRemote.transact(18, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readInt();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.content.IContentService
            public void setIsSyncable(Account account, String str, int i) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    obtain.writeTypedObject(account, 0);
                    obtain.writeString(str);
                    obtain.writeInt(i);
                    this.mRemote.transact(19, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.content.IContentService
            public void setIsSyncableAsUser(Account account, String str, int i, int i2) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    obtain.writeTypedObject(account, 0);
                    obtain.writeString(str);
                    obtain.writeInt(i);
                    obtain.writeInt(i2);
                    this.mRemote.transact(20, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.content.IContentService
            public void setMasterSyncAutomatically(boolean z) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    obtain.writeBoolean(z);
                    this.mRemote.transact(21, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.content.IContentService
            public void setMasterSyncAutomaticallyAsUser(boolean z, int i) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    obtain.writeBoolean(z);
                    obtain.writeInt(i);
                    this.mRemote.transact(22, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.content.IContentService
            public boolean getMasterSyncAutomatically() throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    this.mRemote.transact(23, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readBoolean();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.content.IContentService
            public boolean getMasterSyncAutomaticallyAsUser(int i) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    obtain.writeInt(i);
                    this.mRemote.transact(24, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readBoolean();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.content.IContentService
            public List<SyncInfo> getCurrentSyncs() throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    this.mRemote.transact(25, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.createTypedArrayList(SyncInfo.CREATOR);
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.content.IContentService
            public List<SyncInfo> getCurrentSyncsAsUser(int i) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    obtain.writeInt(i);
                    this.mRemote.transact(26, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.createTypedArrayList(SyncInfo.CREATOR);
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.content.IContentService
            public SyncAdapterType[] getSyncAdapterTypes() throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    this.mRemote.transact(27, obtain, obtain2, 0);
                    obtain2.readException();
                    return (SyncAdapterType[]) obtain2.createTypedArray(SyncAdapterType.CREATOR);
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.content.IContentService
            public SyncAdapterType[] getSyncAdapterTypesAsUser(int i) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    obtain.writeInt(i);
                    this.mRemote.transact(28, obtain, obtain2, 0);
                    obtain2.readException();
                    return (SyncAdapterType[]) obtain2.createTypedArray(SyncAdapterType.CREATOR);
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.content.IContentService
            public String[] getSyncAdapterPackagesForAuthorityAsUser(String str, int i) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    obtain.writeString(str);
                    obtain.writeInt(i);
                    this.mRemote.transact(29, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.createStringArray();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.content.IContentService
            public String getSyncAdapterPackageAsUser(String str, String str2, int i) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    obtain.writeString(str);
                    obtain.writeString(str2);
                    obtain.writeInt(i);
                    this.mRemote.transact(30, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readString();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.content.IContentService
            public boolean isSyncActive(Account account, String str, ComponentName componentName) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    obtain.writeTypedObject(account, 0);
                    obtain.writeString(str);
                    obtain.writeTypedObject(componentName, 0);
                    this.mRemote.transact(31, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readBoolean();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.content.IContentService
            public SyncStatusInfo getSyncStatus(Account account, String str, ComponentName componentName) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    obtain.writeTypedObject(account, 0);
                    obtain.writeString(str);
                    obtain.writeTypedObject(componentName, 0);
                    this.mRemote.transact(32, obtain, obtain2, 0);
                    obtain2.readException();
                    return (SyncStatusInfo) obtain2.readTypedObject(SyncStatusInfo.CREATOR);
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.content.IContentService
            public SyncStatusInfo getSyncStatusAsUser(Account account, String str, ComponentName componentName, int i) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    obtain.writeTypedObject(account, 0);
                    obtain.writeString(str);
                    obtain.writeTypedObject(componentName, 0);
                    obtain.writeInt(i);
                    this.mRemote.transact(33, obtain, obtain2, 0);
                    obtain2.readException();
                    return (SyncStatusInfo) obtain2.readTypedObject(SyncStatusInfo.CREATOR);
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.content.IContentService
            public boolean isSyncPending(Account account, String str, ComponentName componentName) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    obtain.writeTypedObject(account, 0);
                    obtain.writeString(str);
                    obtain.writeTypedObject(componentName, 0);
                    this.mRemote.transact(34, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readBoolean();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.content.IContentService
            public boolean isSyncPendingAsUser(Account account, String str, ComponentName componentName, int i) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    obtain.writeTypedObject(account, 0);
                    obtain.writeString(str);
                    obtain.writeTypedObject(componentName, 0);
                    obtain.writeInt(i);
                    this.mRemote.transact(35, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readBoolean();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.content.IContentService
            public void addStatusChangeListener(int i, ISyncStatusObserver iSyncStatusObserver) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    obtain.writeInt(i);
                    obtain.writeStrongInterface(iSyncStatusObserver);
                    this.mRemote.transact(36, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.content.IContentService
            public void removeStatusChangeListener(ISyncStatusObserver iSyncStatusObserver) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    obtain.writeStrongInterface(iSyncStatusObserver);
                    this.mRemote.transact(37, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.content.IContentService
            public void putCache(String str, Uri uri, Bundle bundle, int i) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    obtain.writeString(str);
                    obtain.writeTypedObject(uri, 0);
                    obtain.writeTypedObject(bundle, 0);
                    obtain.writeInt(i);
                    this.mRemote.transact(38, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.content.IContentService
            public Bundle getCache(String str, Uri uri, int i) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    obtain.writeString(str);
                    obtain.writeTypedObject(uri, 0);
                    obtain.writeInt(i);
                    this.mRemote.transact(39, obtain, obtain2, 0);
                    obtain2.readException();
                    return (Bundle) obtain2.readTypedObject(Bundle.CREATOR);
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.content.IContentService
            public void resetTodayStats() throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    this.mRemote.transact(40, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.content.IContentService
            public void onDbCorruption(String str, String str2, String str3) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    obtain.writeString(str);
                    obtain.writeString(str2);
                    obtain.writeString(str3);
                    this.mRemote.transact(41, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }
        }

        protected void isSyncActive_enforcePermission() throws SecurityException {
            this.mEnforcer.enforcePermission(Manifest.permission.READ_SYNC_STATS, getCallingPid(), getCallingUid());
        }

        protected void isSyncPendingAsUser_enforcePermission() throws SecurityException {
            this.mEnforcer.enforcePermission(Manifest.permission.READ_SYNC_STATS, getCallingPid(), getCallingUid());
        }
    }
}
