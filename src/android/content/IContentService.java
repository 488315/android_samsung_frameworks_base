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
            IInterface iInterfaceQueryLocalInterface = iBinder.queryLocalInterface(DESCRIPTOR);
            if (iInterfaceQueryLocalInterface != null && (iInterfaceQueryLocalInterface instanceof IContentService)) {
                return (IContentService) iInterfaceQueryLocalInterface;
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
                    IContentObserver iContentObserverAsInterface = IContentObserver.Stub.asInterface(parcel.readStrongBinder());
                    parcel.enforceNoDataAvail();
                    unregisterContentObserver(iContentObserverAsInterface);
                    parcel2.writeNoException();
                    return true;
                case 2:
                    Uri uri = (Uri) parcel.readTypedObject(Uri.CREATOR);
                    boolean z = parcel.readBoolean();
                    IContentObserver iContentObserverAsInterface2 = IContentObserver.Stub.asInterface(parcel.readStrongBinder());
                    int i3 = parcel.readInt();
                    int i4 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    registerContentObserver(uri, z, iContentObserverAsInterface2, i3, i4);
                    parcel2.writeNoException();
                    return true;
                case 3:
                    Uri[] uriArr = (Uri[]) parcel.createTypedArray(Uri.CREATOR);
                    IContentObserver iContentObserverAsInterface3 = IContentObserver.Stub.asInterface(parcel.readStrongBinder());
                    boolean z2 = parcel.readBoolean();
                    int i5 = parcel.readInt();
                    int i6 = parcel.readInt();
                    int i7 = parcel.readInt();
                    String string = parcel.readString();
                    parcel.enforceNoDataAvail();
                    notifyChange(uriArr, iContentObserverAsInterface3, z2, i5, i6, i7, string);
                    parcel2.writeNoException();
                    return true;
                case 4:
                    Account account = (Account) parcel.readTypedObject(Account.CREATOR);
                    String string2 = parcel.readString();
                    Bundle bundle = (Bundle) parcel.readTypedObject(Bundle.CREATOR);
                    String string3 = parcel.readString();
                    parcel.enforceNoDataAvail();
                    requestSync(account, string2, bundle, string3);
                    parcel2.writeNoException();
                    return true;
                case 5:
                    SyncRequest syncRequest = (SyncRequest) parcel.readTypedObject(SyncRequest.CREATOR);
                    String string4 = parcel.readString();
                    parcel.enforceNoDataAvail();
                    sync(syncRequest, string4);
                    parcel2.writeNoException();
                    return true;
                case 6:
                    SyncRequest syncRequest2 = (SyncRequest) parcel.readTypedObject(SyncRequest.CREATOR);
                    int i8 = parcel.readInt();
                    String string5 = parcel.readString();
                    parcel.enforceNoDataAvail();
                    syncAsUser(syncRequest2, i8, string5);
                    parcel2.writeNoException();
                    return true;
                case 7:
                    Account account2 = (Account) parcel.readTypedObject(Account.CREATOR);
                    String string6 = parcel.readString();
                    ComponentName componentName = (ComponentName) parcel.readTypedObject(ComponentName.CREATOR);
                    parcel.enforceNoDataAvail();
                    cancelSync(account2, string6, componentName);
                    parcel2.writeNoException();
                    return true;
                case 8:
                    Account account3 = (Account) parcel.readTypedObject(Account.CREATOR);
                    String string7 = parcel.readString();
                    ComponentName componentName2 = (ComponentName) parcel.readTypedObject(ComponentName.CREATOR);
                    int i9 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    cancelSyncAsUser(account3, string7, componentName2, i9);
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
                    String string8 = parcel.readString();
                    parcel.enforceNoDataAvail();
                    boolean syncAutomatically = getSyncAutomatically(account4, string8);
                    parcel2.writeNoException();
                    parcel2.writeBoolean(syncAutomatically);
                    return true;
                case 11:
                    Account account5 = (Account) parcel.readTypedObject(Account.CREATOR);
                    String string9 = parcel.readString();
                    int i10 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    boolean syncAutomaticallyAsUser = getSyncAutomaticallyAsUser(account5, string9, i10);
                    parcel2.writeNoException();
                    parcel2.writeBoolean(syncAutomaticallyAsUser);
                    return true;
                case 12:
                    Account account6 = (Account) parcel.readTypedObject(Account.CREATOR);
                    String string10 = parcel.readString();
                    boolean z3 = parcel.readBoolean();
                    parcel.enforceNoDataAvail();
                    setSyncAutomatically(account6, string10, z3);
                    parcel2.writeNoException();
                    return true;
                case 13:
                    Account account7 = (Account) parcel.readTypedObject(Account.CREATOR);
                    String string11 = parcel.readString();
                    boolean z4 = parcel.readBoolean();
                    int i11 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    setSyncAutomaticallyAsUser(account7, string11, z4, i11);
                    parcel2.writeNoException();
                    return true;
                case 14:
                    Account account8 = (Account) parcel.readTypedObject(Account.CREATOR);
                    String string12 = parcel.readString();
                    ComponentName componentName3 = (ComponentName) parcel.readTypedObject(ComponentName.CREATOR);
                    parcel.enforceNoDataAvail();
                    List<PeriodicSync> periodicSyncs = getPeriodicSyncs(account8, string12, componentName3);
                    parcel2.writeNoException();
                    parcel2.writeTypedList(periodicSyncs, 1);
                    return true;
                case 15:
                    Account account9 = (Account) parcel.readTypedObject(Account.CREATOR);
                    String string13 = parcel.readString();
                    Bundle bundle2 = (Bundle) parcel.readTypedObject(Bundle.CREATOR);
                    long j = parcel.readLong();
                    parcel.enforceNoDataAvail();
                    addPeriodicSync(account9, string13, bundle2, j);
                    parcel2.writeNoException();
                    return true;
                case 16:
                    Account account10 = (Account) parcel.readTypedObject(Account.CREATOR);
                    String string14 = parcel.readString();
                    Bundle bundle3 = (Bundle) parcel.readTypedObject(Bundle.CREATOR);
                    parcel.enforceNoDataAvail();
                    removePeriodicSync(account10, string14, bundle3);
                    parcel2.writeNoException();
                    return true;
                case 17:
                    Account account11 = (Account) parcel.readTypedObject(Account.CREATOR);
                    String string15 = parcel.readString();
                    parcel.enforceNoDataAvail();
                    int isSyncable = getIsSyncable(account11, string15);
                    parcel2.writeNoException();
                    parcel2.writeInt(isSyncable);
                    return true;
                case 18:
                    Account account12 = (Account) parcel.readTypedObject(Account.CREATOR);
                    String string16 = parcel.readString();
                    int i12 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    int isSyncableAsUser = getIsSyncableAsUser(account12, string16, i12);
                    parcel2.writeNoException();
                    parcel2.writeInt(isSyncableAsUser);
                    return true;
                case 19:
                    Account account13 = (Account) parcel.readTypedObject(Account.CREATOR);
                    String string17 = parcel.readString();
                    int i13 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    setIsSyncable(account13, string17, i13);
                    parcel2.writeNoException();
                    return true;
                case 20:
                    Account account14 = (Account) parcel.readTypedObject(Account.CREATOR);
                    String string18 = parcel.readString();
                    int i14 = parcel.readInt();
                    int i15 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    setIsSyncableAsUser(account14, string18, i14, i15);
                    parcel2.writeNoException();
                    return true;
                case 21:
                    boolean z5 = parcel.readBoolean();
                    parcel.enforceNoDataAvail();
                    setMasterSyncAutomatically(z5);
                    parcel2.writeNoException();
                    return true;
                case 22:
                    boolean z6 = parcel.readBoolean();
                    int i16 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    setMasterSyncAutomaticallyAsUser(z6, i16);
                    parcel2.writeNoException();
                    return true;
                case 23:
                    boolean masterSyncAutomatically = getMasterSyncAutomatically();
                    parcel2.writeNoException();
                    parcel2.writeBoolean(masterSyncAutomatically);
                    return true;
                case 24:
                    int i17 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    boolean masterSyncAutomaticallyAsUser = getMasterSyncAutomaticallyAsUser(i17);
                    parcel2.writeNoException();
                    parcel2.writeBoolean(masterSyncAutomaticallyAsUser);
                    return true;
                case 25:
                    List<SyncInfo> currentSyncs = getCurrentSyncs();
                    parcel2.writeNoException();
                    parcel2.writeTypedList(currentSyncs, 1);
                    return true;
                case 26:
                    int i18 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    List<SyncInfo> currentSyncsAsUser = getCurrentSyncsAsUser(i18);
                    parcel2.writeNoException();
                    parcel2.writeTypedList(currentSyncsAsUser, 1);
                    return true;
                case 27:
                    SyncAdapterType[] syncAdapterTypes = getSyncAdapterTypes();
                    parcel2.writeNoException();
                    parcel2.writeTypedArray(syncAdapterTypes, 1);
                    return true;
                case 28:
                    int i19 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    SyncAdapterType[] syncAdapterTypesAsUser = getSyncAdapterTypesAsUser(i19);
                    parcel2.writeNoException();
                    parcel2.writeTypedArray(syncAdapterTypesAsUser, 1);
                    return true;
                case 29:
                    String string19 = parcel.readString();
                    int i20 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    String[] syncAdapterPackagesForAuthorityAsUser = getSyncAdapterPackagesForAuthorityAsUser(string19, i20);
                    parcel2.writeNoException();
                    parcel2.writeStringArray(syncAdapterPackagesForAuthorityAsUser);
                    return true;
                case 30:
                    String string20 = parcel.readString();
                    String string21 = parcel.readString();
                    int i21 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    String syncAdapterPackageAsUser = getSyncAdapterPackageAsUser(string20, string21, i21);
                    parcel2.writeNoException();
                    parcel2.writeString(syncAdapterPackageAsUser);
                    return true;
                case 31:
                    Account account15 = (Account) parcel.readTypedObject(Account.CREATOR);
                    String string22 = parcel.readString();
                    ComponentName componentName4 = (ComponentName) parcel.readTypedObject(ComponentName.CREATOR);
                    parcel.enforceNoDataAvail();
                    boolean zIsSyncActive = isSyncActive(account15, string22, componentName4);
                    parcel2.writeNoException();
                    parcel2.writeBoolean(zIsSyncActive);
                    return true;
                case 32:
                    Account account16 = (Account) parcel.readTypedObject(Account.CREATOR);
                    String string23 = parcel.readString();
                    ComponentName componentName5 = (ComponentName) parcel.readTypedObject(ComponentName.CREATOR);
                    parcel.enforceNoDataAvail();
                    SyncStatusInfo syncStatus = getSyncStatus(account16, string23, componentName5);
                    parcel2.writeNoException();
                    parcel2.writeTypedObject(syncStatus, 1);
                    return true;
                case 33:
                    Account account17 = (Account) parcel.readTypedObject(Account.CREATOR);
                    String string24 = parcel.readString();
                    ComponentName componentName6 = (ComponentName) parcel.readTypedObject(ComponentName.CREATOR);
                    int i22 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    SyncStatusInfo syncStatusAsUser = getSyncStatusAsUser(account17, string24, componentName6, i22);
                    parcel2.writeNoException();
                    parcel2.writeTypedObject(syncStatusAsUser, 1);
                    return true;
                case 34:
                    Account account18 = (Account) parcel.readTypedObject(Account.CREATOR);
                    String string25 = parcel.readString();
                    ComponentName componentName7 = (ComponentName) parcel.readTypedObject(ComponentName.CREATOR);
                    parcel.enforceNoDataAvail();
                    boolean zIsSyncPending = isSyncPending(account18, string25, componentName7);
                    parcel2.writeNoException();
                    parcel2.writeBoolean(zIsSyncPending);
                    return true;
                case 35:
                    Account account19 = (Account) parcel.readTypedObject(Account.CREATOR);
                    String string26 = parcel.readString();
                    ComponentName componentName8 = (ComponentName) parcel.readTypedObject(ComponentName.CREATOR);
                    int i23 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    boolean zIsSyncPendingAsUser = isSyncPendingAsUser(account19, string26, componentName8, i23);
                    parcel2.writeNoException();
                    parcel2.writeBoolean(zIsSyncPendingAsUser);
                    return true;
                case 36:
                    int i24 = parcel.readInt();
                    ISyncStatusObserver iSyncStatusObserverAsInterface = ISyncStatusObserver.Stub.asInterface(parcel.readStrongBinder());
                    parcel.enforceNoDataAvail();
                    addStatusChangeListener(i24, iSyncStatusObserverAsInterface);
                    parcel2.writeNoException();
                    return true;
                case 37:
                    ISyncStatusObserver iSyncStatusObserverAsInterface2 = ISyncStatusObserver.Stub.asInterface(parcel.readStrongBinder());
                    parcel.enforceNoDataAvail();
                    removeStatusChangeListener(iSyncStatusObserverAsInterface2);
                    parcel2.writeNoException();
                    return true;
                case 38:
                    String string27 = parcel.readString();
                    Uri uri2 = (Uri) parcel.readTypedObject(Uri.CREATOR);
                    Bundle bundle4 = (Bundle) parcel.readTypedObject(Bundle.CREATOR);
                    int i25 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    putCache(string27, uri2, bundle4, i25);
                    parcel2.writeNoException();
                    return true;
                case 39:
                    String string28 = parcel.readString();
                    Uri uri3 = (Uri) parcel.readTypedObject(Uri.CREATOR);
                    int i26 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    Bundle cache = getCache(string28, uri3, i26);
                    parcel2.writeNoException();
                    parcel2.writeTypedObject(cache, 1);
                    return true;
                case 40:
                    resetTodayStats();
                    parcel2.writeNoException();
                    return true;
                case 41:
                    String string29 = parcel.readString();
                    String string30 = parcel.readString();
                    String string31 = parcel.readString();
                    parcel.enforceNoDataAvail();
                    onDbCorruption(string29, string30, string31);
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
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcelObtain.writeStrongInterface(iContentObserver);
                    this.mRemote.transact(1, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.content.IContentService
            public void registerContentObserver(Uri uri, boolean z, IContentObserver iContentObserver, int i, int i2) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcelObtain.writeTypedObject(uri, 0);
                    parcelObtain.writeBoolean(z);
                    parcelObtain.writeStrongInterface(iContentObserver);
                    parcelObtain.writeInt(i);
                    parcelObtain.writeInt(i2);
                    this.mRemote.transact(2, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.content.IContentService
            public void notifyChange(Uri[] uriArr, IContentObserver iContentObserver, boolean z, int i, int i2, int i3, String str) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcelObtain.writeTypedArray(uriArr, 0);
                    parcelObtain.writeStrongInterface(iContentObserver);
                    parcelObtain.writeBoolean(z);
                    parcelObtain.writeInt(i);
                    parcelObtain.writeInt(i2);
                    parcelObtain.writeInt(i3);
                    parcelObtain.writeString(str);
                    this.mRemote.transact(3, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.content.IContentService
            public void requestSync(Account account, String str, Bundle bundle, String str2) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcelObtain.writeTypedObject(account, 0);
                    parcelObtain.writeString(str);
                    parcelObtain.writeTypedObject(bundle, 0);
                    parcelObtain.writeString(str2);
                    this.mRemote.transact(4, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.content.IContentService
            public void sync(SyncRequest syncRequest, String str) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcelObtain.writeTypedObject(syncRequest, 0);
                    parcelObtain.writeString(str);
                    this.mRemote.transact(5, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.content.IContentService
            public void syncAsUser(SyncRequest syncRequest, int i, String str) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcelObtain.writeTypedObject(syncRequest, 0);
                    parcelObtain.writeInt(i);
                    parcelObtain.writeString(str);
                    this.mRemote.transact(6, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.content.IContentService
            public void cancelSync(Account account, String str, ComponentName componentName) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcelObtain.writeTypedObject(account, 0);
                    parcelObtain.writeString(str);
                    parcelObtain.writeTypedObject(componentName, 0);
                    this.mRemote.transact(7, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.content.IContentService
            public void cancelSyncAsUser(Account account, String str, ComponentName componentName, int i) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcelObtain.writeTypedObject(account, 0);
                    parcelObtain.writeString(str);
                    parcelObtain.writeTypedObject(componentName, 0);
                    parcelObtain.writeInt(i);
                    this.mRemote.transact(8, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.content.IContentService
            public void cancelRequest(SyncRequest syncRequest) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcelObtain.writeTypedObject(syncRequest, 0);
                    this.mRemote.transact(9, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.content.IContentService
            public boolean getSyncAutomatically(Account account, String str) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcelObtain.writeTypedObject(account, 0);
                    parcelObtain.writeString(str);
                    this.mRemote.transact(10, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return parcelObtain2.readBoolean();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.content.IContentService
            public boolean getSyncAutomaticallyAsUser(Account account, String str, int i) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcelObtain.writeTypedObject(account, 0);
                    parcelObtain.writeString(str);
                    parcelObtain.writeInt(i);
                    this.mRemote.transact(11, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return parcelObtain2.readBoolean();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.content.IContentService
            public void setSyncAutomatically(Account account, String str, boolean z) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcelObtain.writeTypedObject(account, 0);
                    parcelObtain.writeString(str);
                    parcelObtain.writeBoolean(z);
                    this.mRemote.transact(12, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.content.IContentService
            public void setSyncAutomaticallyAsUser(Account account, String str, boolean z, int i) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcelObtain.writeTypedObject(account, 0);
                    parcelObtain.writeString(str);
                    parcelObtain.writeBoolean(z);
                    parcelObtain.writeInt(i);
                    this.mRemote.transact(13, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.content.IContentService
            public List<PeriodicSync> getPeriodicSyncs(Account account, String str, ComponentName componentName) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcelObtain.writeTypedObject(account, 0);
                    parcelObtain.writeString(str);
                    parcelObtain.writeTypedObject(componentName, 0);
                    this.mRemote.transact(14, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return parcelObtain2.createTypedArrayList(PeriodicSync.CREATOR);
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.content.IContentService
            public void addPeriodicSync(Account account, String str, Bundle bundle, long j) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcelObtain.writeTypedObject(account, 0);
                    parcelObtain.writeString(str);
                    parcelObtain.writeTypedObject(bundle, 0);
                    parcelObtain.writeLong(j);
                    this.mRemote.transact(15, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.content.IContentService
            public void removePeriodicSync(Account account, String str, Bundle bundle) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcelObtain.writeTypedObject(account, 0);
                    parcelObtain.writeString(str);
                    parcelObtain.writeTypedObject(bundle, 0);
                    this.mRemote.transact(16, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.content.IContentService
            public int getIsSyncable(Account account, String str) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcelObtain.writeTypedObject(account, 0);
                    parcelObtain.writeString(str);
                    this.mRemote.transact(17, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return parcelObtain2.readInt();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.content.IContentService
            public int getIsSyncableAsUser(Account account, String str, int i) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcelObtain.writeTypedObject(account, 0);
                    parcelObtain.writeString(str);
                    parcelObtain.writeInt(i);
                    this.mRemote.transact(18, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return parcelObtain2.readInt();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.content.IContentService
            public void setIsSyncable(Account account, String str, int i) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcelObtain.writeTypedObject(account, 0);
                    parcelObtain.writeString(str);
                    parcelObtain.writeInt(i);
                    this.mRemote.transact(19, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.content.IContentService
            public void setIsSyncableAsUser(Account account, String str, int i, int i2) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcelObtain.writeTypedObject(account, 0);
                    parcelObtain.writeString(str);
                    parcelObtain.writeInt(i);
                    parcelObtain.writeInt(i2);
                    this.mRemote.transact(20, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.content.IContentService
            public void setMasterSyncAutomatically(boolean z) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcelObtain.writeBoolean(z);
                    this.mRemote.transact(21, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.content.IContentService
            public void setMasterSyncAutomaticallyAsUser(boolean z, int i) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcelObtain.writeBoolean(z);
                    parcelObtain.writeInt(i);
                    this.mRemote.transact(22, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.content.IContentService
            public boolean getMasterSyncAutomatically() throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    this.mRemote.transact(23, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return parcelObtain2.readBoolean();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.content.IContentService
            public boolean getMasterSyncAutomaticallyAsUser(int i) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcelObtain.writeInt(i);
                    this.mRemote.transact(24, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return parcelObtain2.readBoolean();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.content.IContentService
            public List<SyncInfo> getCurrentSyncs() throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    this.mRemote.transact(25, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return parcelObtain2.createTypedArrayList(SyncInfo.CREATOR);
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.content.IContentService
            public List<SyncInfo> getCurrentSyncsAsUser(int i) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcelObtain.writeInt(i);
                    this.mRemote.transact(26, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return parcelObtain2.createTypedArrayList(SyncInfo.CREATOR);
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.content.IContentService
            public SyncAdapterType[] getSyncAdapterTypes() throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    this.mRemote.transact(27, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return (SyncAdapterType[]) parcelObtain2.createTypedArray(SyncAdapterType.CREATOR);
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.content.IContentService
            public SyncAdapterType[] getSyncAdapterTypesAsUser(int i) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcelObtain.writeInt(i);
                    this.mRemote.transact(28, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return (SyncAdapterType[]) parcelObtain2.createTypedArray(SyncAdapterType.CREATOR);
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.content.IContentService
            public String[] getSyncAdapterPackagesForAuthorityAsUser(String str, int i) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcelObtain.writeString(str);
                    parcelObtain.writeInt(i);
                    this.mRemote.transact(29, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return parcelObtain2.createStringArray();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.content.IContentService
            public String getSyncAdapterPackageAsUser(String str, String str2, int i) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcelObtain.writeString(str);
                    parcelObtain.writeString(str2);
                    parcelObtain.writeInt(i);
                    this.mRemote.transact(30, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return parcelObtain2.readString();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.content.IContentService
            public boolean isSyncActive(Account account, String str, ComponentName componentName) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcelObtain.writeTypedObject(account, 0);
                    parcelObtain.writeString(str);
                    parcelObtain.writeTypedObject(componentName, 0);
                    this.mRemote.transact(31, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return parcelObtain2.readBoolean();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.content.IContentService
            public SyncStatusInfo getSyncStatus(Account account, String str, ComponentName componentName) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcelObtain.writeTypedObject(account, 0);
                    parcelObtain.writeString(str);
                    parcelObtain.writeTypedObject(componentName, 0);
                    this.mRemote.transact(32, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return (SyncStatusInfo) parcelObtain2.readTypedObject(SyncStatusInfo.CREATOR);
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.content.IContentService
            public SyncStatusInfo getSyncStatusAsUser(Account account, String str, ComponentName componentName, int i) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcelObtain.writeTypedObject(account, 0);
                    parcelObtain.writeString(str);
                    parcelObtain.writeTypedObject(componentName, 0);
                    parcelObtain.writeInt(i);
                    this.mRemote.transact(33, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return (SyncStatusInfo) parcelObtain2.readTypedObject(SyncStatusInfo.CREATOR);
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.content.IContentService
            public boolean isSyncPending(Account account, String str, ComponentName componentName) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcelObtain.writeTypedObject(account, 0);
                    parcelObtain.writeString(str);
                    parcelObtain.writeTypedObject(componentName, 0);
                    this.mRemote.transact(34, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return parcelObtain2.readBoolean();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.content.IContentService
            public boolean isSyncPendingAsUser(Account account, String str, ComponentName componentName, int i) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcelObtain.writeTypedObject(account, 0);
                    parcelObtain.writeString(str);
                    parcelObtain.writeTypedObject(componentName, 0);
                    parcelObtain.writeInt(i);
                    this.mRemote.transact(35, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return parcelObtain2.readBoolean();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.content.IContentService
            public void addStatusChangeListener(int i, ISyncStatusObserver iSyncStatusObserver) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcelObtain.writeInt(i);
                    parcelObtain.writeStrongInterface(iSyncStatusObserver);
                    this.mRemote.transact(36, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.content.IContentService
            public void removeStatusChangeListener(ISyncStatusObserver iSyncStatusObserver) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcelObtain.writeStrongInterface(iSyncStatusObserver);
                    this.mRemote.transact(37, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.content.IContentService
            public void putCache(String str, Uri uri, Bundle bundle, int i) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcelObtain.writeString(str);
                    parcelObtain.writeTypedObject(uri, 0);
                    parcelObtain.writeTypedObject(bundle, 0);
                    parcelObtain.writeInt(i);
                    this.mRemote.transact(38, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.content.IContentService
            public Bundle getCache(String str, Uri uri, int i) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcelObtain.writeString(str);
                    parcelObtain.writeTypedObject(uri, 0);
                    parcelObtain.writeInt(i);
                    this.mRemote.transact(39, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return (Bundle) parcelObtain2.readTypedObject(Bundle.CREATOR);
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.content.IContentService
            public void resetTodayStats() throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    this.mRemote.transact(40, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.content.IContentService
            public void onDbCorruption(String str, String str2, String str3) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcelObtain.writeString(str);
                    parcelObtain.writeString(str2);
                    parcelObtain.writeString(str3);
                    this.mRemote.transact(41, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
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
