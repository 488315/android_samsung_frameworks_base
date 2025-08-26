package android.app.usage;

import android.Manifest;
import android.app.ActivityThread;
import android.app.PendingIntent;
import android.content.ComponentName;
import android.content.pm.ParceledListSlice;
import android.os.Binder;
import android.os.IBinder;
import android.os.IInterface;
import android.os.Parcel;
import android.os.PermissionEnforcer;
import android.os.PersistableBundle;
import android.os.RemoteException;
import com.samsung.android.app.usage.IUsageStatsWatcher;
import java.util.ArrayList;
import java.util.List;

/* loaded from: classes.dex */
public interface IUsageStatsManager extends IInterface {

    public static class Default implements IUsageStatsManager {
        @Override // android.os.IInterface
        public IBinder asBinder() {
            return null;
        }

        @Override // android.app.usage.IUsageStatsManager
        public void clearBroadcastEvents(String str, int i) throws RemoteException {
        }

        @Override // android.app.usage.IUsageStatsManager
        public void clearBroadcastResponseStats(String str, long j, String str2, int i) throws RemoteException {
        }

        @Override // android.app.usage.IUsageStatsManager
        public void deleteUsageStats() throws RemoteException {
        }

        @Override // android.app.usage.IUsageStatsManager
        public void forceUsageSourceSettingRead() throws RemoteException {
        }

        @Override // android.app.usage.IUsageStatsManager
        public int getAppMinStandbyBucket(String str, String str2, int i) throws RemoteException {
            return 0;
        }

        @Override // android.app.usage.IUsageStatsManager
        public int getAppStandbyBucket(String str, String str2, int i) throws RemoteException {
            return 0;
        }

        @Override // android.app.usage.IUsageStatsManager
        public ParceledListSlice getAppStandbyBuckets(String str, int i) throws RemoteException {
            return null;
        }

        @Override // android.app.usage.IUsageStatsManager
        public String getAppStandbyConstant(String str) throws RemoteException {
            return null;
        }

        @Override // android.app.usage.IUsageStatsManager
        public long getLastTimeAnyComponentUsed(String str, String str2) throws RemoteException {
            return 0L;
        }

        @Override // android.app.usage.IUsageStatsManager
        public int getUsageSource() throws RemoteException {
            return 0;
        }

        @Override // android.app.usage.IUsageStatsManager
        public boolean isAppInactive(String str, int i, String str2) throws RemoteException {
            return false;
        }

        @Override // android.app.usage.IUsageStatsManager
        public boolean isAppStandbyEnabled() throws RemoteException {
            return false;
        }

        @Override // android.app.usage.IUsageStatsManager
        public boolean isPackageExemptedFromBroadcastResponseStats(String str, int i) throws RemoteException {
            return false;
        }

        @Override // android.app.usage.IUsageStatsManager
        public void onCarrierPrivilegedAppsChanged() throws RemoteException {
        }

        @Override // android.app.usage.IUsageStatsManager
        public BroadcastResponseStatsList queryBroadcastResponseStats(String str, long j, String str2, int i) throws RemoteException {
            return null;
        }

        @Override // android.app.usage.IUsageStatsManager
        public ParceledListSlice queryConfigurationStats(int i, long j, long j2, String str) throws RemoteException {
            return null;
        }

        @Override // android.app.usage.IUsageStatsManager
        public ParceledListSlice queryEventStats(int i, long j, long j2, String str) throws RemoteException {
            return null;
        }

        @Override // android.app.usage.IUsageStatsManager
        public UsageEvents queryEvents(long j, long j2, String str) throws RemoteException {
            return null;
        }

        @Override // android.app.usage.IUsageStatsManager
        public UsageEvents queryEventsForPackage(long j, long j2, String str) throws RemoteException {
            return null;
        }

        @Override // android.app.usage.IUsageStatsManager
        public UsageEvents queryEventsForPackageForUser(long j, long j2, int i, String str, String str2) throws RemoteException {
            return null;
        }

        @Override // android.app.usage.IUsageStatsManager
        public UsageEvents queryEventsForUser(long j, long j2, int i, String str) throws RemoteException {
            return null;
        }

        @Override // android.app.usage.IUsageStatsManager
        public UsageEvents queryEventsWithFilter(UsageEventsQuery usageEventsQuery, String str) throws RemoteException {
            return null;
        }

        @Override // android.app.usage.IUsageStatsManager
        public ParceledListSlice queryUsageStats(int i, long j, long j2, String str, int i2) throws RemoteException {
            return null;
        }

        @Override // android.app.usage.IUsageStatsManager
        public void registerAppUsageLimitObserver(int i, String[] strArr, long j, long j2, PendingIntent pendingIntent, String str) throws RemoteException {
        }

        @Override // android.app.usage.IUsageStatsManager
        public void registerAppUsageObserver(int i, String[] strArr, long j, PendingIntent pendingIntent, String str) throws RemoteException {
        }

        @Override // android.app.usage.IUsageStatsManager
        public void registerUsageSessionObserver(int i, String[] strArr, long j, long j2, PendingIntent pendingIntent, PendingIntent pendingIntent2, String str) throws RemoteException {
        }

        @Override // android.app.usage.IUsageStatsManager
        public void registerUsageStatsWatcher(IUsageStatsWatcher iUsageStatsWatcher) throws RemoteException {
        }

        @Override // android.app.usage.IUsageStatsManager
        public void registerUsageStatsWatcherWithComponent(IUsageStatsWatcher iUsageStatsWatcher, List<ComponentName> list) throws RemoteException {
        }

        @Override // android.app.usage.IUsageStatsManager
        public void reportChooserSelection(String str, int i, String str2, String[] strArr, String str3) throws RemoteException {
        }

        @Override // android.app.usage.IUsageStatsManager
        public void reportPastUsageStart(IBinder iBinder, String str, long j, String str2) throws RemoteException {
        }

        @Override // android.app.usage.IUsageStatsManager
        public void reportUsageStart(IBinder iBinder, String str, String str2) throws RemoteException {
        }

        @Override // android.app.usage.IUsageStatsManager
        public void reportUsageStop(IBinder iBinder, String str, String str2) throws RemoteException {
        }

        @Override // android.app.usage.IUsageStatsManager
        public void reportUserInteraction(String str, int i) throws RemoteException {
        }

        @Override // android.app.usage.IUsageStatsManager
        public void reportUserInteractionWithBundle(String str, int i, PersistableBundle persistableBundle) throws RemoteException {
        }

        @Override // android.app.usage.IUsageStatsManager
        public void setAppInactive(String str, boolean z, int i) throws RemoteException {
        }

        @Override // android.app.usage.IUsageStatsManager
        public void setAppStandbyBucket(String str, int i, int i2) throws RemoteException {
        }

        @Override // android.app.usage.IUsageStatsManager
        public void setAppStandbyBuckets(ParceledListSlice parceledListSlice, int i) throws RemoteException {
        }

        @Override // android.app.usage.IUsageStatsManager
        public void setEstimatedLaunchTime(String str, long j, int i) throws RemoteException {
        }

        @Override // android.app.usage.IUsageStatsManager
        public void setEstimatedLaunchTimes(ParceledListSlice parceledListSlice, int i) throws RemoteException {
        }

        @Override // android.app.usage.IUsageStatsManager
        public void unregisterAppUsageLimitObserver(int i, String str) throws RemoteException {
        }

        @Override // android.app.usage.IUsageStatsManager
        public void unregisterAppUsageObserver(int i, String str) throws RemoteException {
        }

        @Override // android.app.usage.IUsageStatsManager
        public void unregisterUsageSessionObserver(int i, String str) throws RemoteException {
        }

        @Override // android.app.usage.IUsageStatsManager
        public void unregisterUsageStatsWatcher(IUsageStatsWatcher iUsageStatsWatcher) throws RemoteException {
        }
    }

    void clearBroadcastEvents(String str, int i) throws RemoteException;

    void clearBroadcastResponseStats(String str, long j, String str2, int i) throws RemoteException;

    void deleteUsageStats() throws RemoteException;

    void forceUsageSourceSettingRead() throws RemoteException;

    int getAppMinStandbyBucket(String str, String str2, int i) throws RemoteException;

    int getAppStandbyBucket(String str, String str2, int i) throws RemoteException;

    ParceledListSlice getAppStandbyBuckets(String str, int i) throws RemoteException;

    String getAppStandbyConstant(String str) throws RemoteException;

    long getLastTimeAnyComponentUsed(String str, String str2) throws RemoteException;

    int getUsageSource() throws RemoteException;

    boolean isAppInactive(String str, int i, String str2) throws RemoteException;

    boolean isAppStandbyEnabled() throws RemoteException;

    boolean isPackageExemptedFromBroadcastResponseStats(String str, int i) throws RemoteException;

    void onCarrierPrivilegedAppsChanged() throws RemoteException;

    BroadcastResponseStatsList queryBroadcastResponseStats(String str, long j, String str2, int i) throws RemoteException;

    ParceledListSlice queryConfigurationStats(int i, long j, long j2, String str) throws RemoteException;

    ParceledListSlice queryEventStats(int i, long j, long j2, String str) throws RemoteException;

    UsageEvents queryEvents(long j, long j2, String str) throws RemoteException;

    UsageEvents queryEventsForPackage(long j, long j2, String str) throws RemoteException;

    UsageEvents queryEventsForPackageForUser(long j, long j2, int i, String str, String str2) throws RemoteException;

    UsageEvents queryEventsForUser(long j, long j2, int i, String str) throws RemoteException;

    UsageEvents queryEventsWithFilter(UsageEventsQuery usageEventsQuery, String str) throws RemoteException;

    ParceledListSlice queryUsageStats(int i, long j, long j2, String str, int i2) throws RemoteException;

    void registerAppUsageLimitObserver(int i, String[] strArr, long j, long j2, PendingIntent pendingIntent, String str) throws RemoteException;

    void registerAppUsageObserver(int i, String[] strArr, long j, PendingIntent pendingIntent, String str) throws RemoteException;

    void registerUsageSessionObserver(int i, String[] strArr, long j, long j2, PendingIntent pendingIntent, PendingIntent pendingIntent2, String str) throws RemoteException;

    void registerUsageStatsWatcher(IUsageStatsWatcher iUsageStatsWatcher) throws RemoteException;

    void registerUsageStatsWatcherWithComponent(IUsageStatsWatcher iUsageStatsWatcher, List<ComponentName> list) throws RemoteException;

    void reportChooserSelection(String str, int i, String str2, String[] strArr, String str3) throws RemoteException;

    void reportPastUsageStart(IBinder iBinder, String str, long j, String str2) throws RemoteException;

    void reportUsageStart(IBinder iBinder, String str, String str2) throws RemoteException;

    void reportUsageStop(IBinder iBinder, String str, String str2) throws RemoteException;

    void reportUserInteraction(String str, int i) throws RemoteException;

    void reportUserInteractionWithBundle(String str, int i, PersistableBundle persistableBundle) throws RemoteException;

    void setAppInactive(String str, boolean z, int i) throws RemoteException;

    void setAppStandbyBucket(String str, int i, int i2) throws RemoteException;

    void setAppStandbyBuckets(ParceledListSlice parceledListSlice, int i) throws RemoteException;

    void setEstimatedLaunchTime(String str, long j, int i) throws RemoteException;

    void setEstimatedLaunchTimes(ParceledListSlice parceledListSlice, int i) throws RemoteException;

    void unregisterAppUsageLimitObserver(int i, String str) throws RemoteException;

    void unregisterAppUsageObserver(int i, String str) throws RemoteException;

    void unregisterUsageSessionObserver(int i, String str) throws RemoteException;

    void unregisterUsageStatsWatcher(IUsageStatsWatcher iUsageStatsWatcher) throws RemoteException;

    public static abstract class Stub extends Binder implements IUsageStatsManager {
        public static final String DESCRIPTOR = "android.app.usage.IUsageStatsManager";
        static final int TRANSACTION_clearBroadcastEvents = 37;
        static final int TRANSACTION_clearBroadcastResponseStats = 36;
        static final int TRANSACTION_deleteUsageStats = 43;
        static final int TRANSACTION_forceUsageSourceSettingRead = 33;
        static final int TRANSACTION_getAppMinStandbyBucket = 18;
        static final int TRANSACTION_getAppStandbyBucket = 14;
        static final int TRANSACTION_getAppStandbyBuckets = 16;
        static final int TRANSACTION_getAppStandbyConstant = 39;
        static final int TRANSACTION_getLastTimeAnyComponentUsed = 34;
        static final int TRANSACTION_getUsageSource = 32;
        static final int TRANSACTION_isAppInactive = 11;
        static final int TRANSACTION_isAppStandbyEnabled = 10;
        static final int TRANSACTION_isPackageExemptedFromBroadcastResponseStats = 38;
        static final int TRANSACTION_onCarrierPrivilegedAppsChanged = 12;
        static final int TRANSACTION_queryBroadcastResponseStats = 35;
        static final int TRANSACTION_queryConfigurationStats = 2;
        static final int TRANSACTION_queryEventStats = 3;
        static final int TRANSACTION_queryEvents = 4;
        static final int TRANSACTION_queryEventsForPackage = 5;
        static final int TRANSACTION_queryEventsForPackageForUser = 7;
        static final int TRANSACTION_queryEventsForUser = 6;
        static final int TRANSACTION_queryEventsWithFilter = 8;
        static final int TRANSACTION_queryUsageStats = 1;
        static final int TRANSACTION_registerAppUsageLimitObserver = 25;
        static final int TRANSACTION_registerAppUsageObserver = 21;
        static final int TRANSACTION_registerUsageSessionObserver = 23;
        static final int TRANSACTION_registerUsageStatsWatcher = 40;
        static final int TRANSACTION_registerUsageStatsWatcherWithComponent = 41;
        static final int TRANSACTION_reportChooserSelection = 13;
        static final int TRANSACTION_reportPastUsageStart = 28;
        static final int TRANSACTION_reportUsageStart = 27;
        static final int TRANSACTION_reportUsageStop = 29;
        static final int TRANSACTION_reportUserInteraction = 30;
        static final int TRANSACTION_reportUserInteractionWithBundle = 31;
        static final int TRANSACTION_setAppInactive = 9;
        static final int TRANSACTION_setAppStandbyBucket = 15;
        static final int TRANSACTION_setAppStandbyBuckets = 17;
        static final int TRANSACTION_setEstimatedLaunchTime = 19;
        static final int TRANSACTION_setEstimatedLaunchTimes = 20;
        static final int TRANSACTION_unregisterAppUsageLimitObserver = 26;
        static final int TRANSACTION_unregisterAppUsageObserver = 22;
        static final int TRANSACTION_unregisterUsageSessionObserver = 24;
        static final int TRANSACTION_unregisterUsageStatsWatcher = 42;
        private final PermissionEnforcer mEnforcer;

        @Override // android.os.IInterface
        public IBinder asBinder() {
            return this;
        }

        @Override // android.os.Binder
        public int getMaxTransactionId() {
            return 42;
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

        public static IUsageStatsManager asInterface(IBinder iBinder) {
            if (iBinder == null) {
                return null;
            }
            IInterface iInterfaceQueryLocalInterface = iBinder.queryLocalInterface(DESCRIPTOR);
            if (iInterfaceQueryLocalInterface != null && (iInterfaceQueryLocalInterface instanceof IUsageStatsManager)) {
                return (IUsageStatsManager) iInterfaceQueryLocalInterface;
            }
            return new Proxy(iBinder);
        }

        public static String getDefaultTransactionName(int i) {
            switch (i) {
                case 1:
                    return "queryUsageStats";
                case 2:
                    return "queryConfigurationStats";
                case 3:
                    return "queryEventStats";
                case 4:
                    return "queryEvents";
                case 5:
                    return "queryEventsForPackage";
                case 6:
                    return "queryEventsForUser";
                case 7:
                    return "queryEventsForPackageForUser";
                case 8:
                    return "queryEventsWithFilter";
                case 9:
                    return "setAppInactive";
                case 10:
                    return "isAppStandbyEnabled";
                case 11:
                    return "isAppInactive";
                case 12:
                    return "onCarrierPrivilegedAppsChanged";
                case 13:
                    return "reportChooserSelection";
                case 14:
                    return "getAppStandbyBucket";
                case 15:
                    return "setAppStandbyBucket";
                case 16:
                    return "getAppStandbyBuckets";
                case 17:
                    return "setAppStandbyBuckets";
                case 18:
                    return "getAppMinStandbyBucket";
                case 19:
                    return "setEstimatedLaunchTime";
                case 20:
                    return "setEstimatedLaunchTimes";
                case 21:
                    return "registerAppUsageObserver";
                case 22:
                    return "unregisterAppUsageObserver";
                case 23:
                    return "registerUsageSessionObserver";
                case 24:
                    return "unregisterUsageSessionObserver";
                case 25:
                    return "registerAppUsageLimitObserver";
                case 26:
                    return "unregisterAppUsageLimitObserver";
                case 27:
                    return "reportUsageStart";
                case 28:
                    return "reportPastUsageStart";
                case 29:
                    return "reportUsageStop";
                case 30:
                    return "reportUserInteraction";
                case 31:
                    return "reportUserInteractionWithBundle";
                case 32:
                    return "getUsageSource";
                case 33:
                    return "forceUsageSourceSettingRead";
                case 34:
                    return "getLastTimeAnyComponentUsed";
                case 35:
                    return "queryBroadcastResponseStats";
                case 36:
                    return "clearBroadcastResponseStats";
                case 37:
                    return "clearBroadcastEvents";
                case 38:
                    return "isPackageExemptedFromBroadcastResponseStats";
                case 39:
                    return "getAppStandbyConstant";
                case 40:
                    return "registerUsageStatsWatcher";
                case 41:
                    return "registerUsageStatsWatcherWithComponent";
                case 42:
                    return "unregisterUsageStatsWatcher";
                case 43:
                    return "deleteUsageStats";
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
                    int i3 = parcel.readInt();
                    long j = parcel.readLong();
                    long j2 = parcel.readLong();
                    String string = parcel.readString();
                    int i4 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    ParceledListSlice parceledListSliceQueryUsageStats = queryUsageStats(i3, j, j2, string, i4);
                    parcel2.writeNoException();
                    parcel2.writeTypedObject(parceledListSliceQueryUsageStats, 1);
                    return true;
                case 2:
                    int i5 = parcel.readInt();
                    long j3 = parcel.readLong();
                    long j4 = parcel.readLong();
                    String string2 = parcel.readString();
                    parcel.enforceNoDataAvail();
                    ParceledListSlice parceledListSliceQueryConfigurationStats = queryConfigurationStats(i5, j3, j4, string2);
                    parcel2.writeNoException();
                    parcel2.writeTypedObject(parceledListSliceQueryConfigurationStats, 1);
                    return true;
                case 3:
                    int i6 = parcel.readInt();
                    long j5 = parcel.readLong();
                    long j6 = parcel.readLong();
                    String string3 = parcel.readString();
                    parcel.enforceNoDataAvail();
                    ParceledListSlice parceledListSliceQueryEventStats = queryEventStats(i6, j5, j6, string3);
                    parcel2.writeNoException();
                    parcel2.writeTypedObject(parceledListSliceQueryEventStats, 1);
                    return true;
                case 4:
                    long j7 = parcel.readLong();
                    long j8 = parcel.readLong();
                    String string4 = parcel.readString();
                    parcel.enforceNoDataAvail();
                    UsageEvents usageEventsQueryEvents = queryEvents(j7, j8, string4);
                    parcel2.writeNoException();
                    parcel2.writeTypedObject(usageEventsQueryEvents, 1);
                    return true;
                case 5:
                    long j9 = parcel.readLong();
                    long j10 = parcel.readLong();
                    String string5 = parcel.readString();
                    parcel.enforceNoDataAvail();
                    UsageEvents usageEventsQueryEventsForPackage = queryEventsForPackage(j9, j10, string5);
                    parcel2.writeNoException();
                    parcel2.writeTypedObject(usageEventsQueryEventsForPackage, 1);
                    return true;
                case 6:
                    long j11 = parcel.readLong();
                    long j12 = parcel.readLong();
                    int i7 = parcel.readInt();
                    String string6 = parcel.readString();
                    parcel.enforceNoDataAvail();
                    UsageEvents usageEventsQueryEventsForUser = queryEventsForUser(j11, j12, i7, string6);
                    parcel2.writeNoException();
                    parcel2.writeTypedObject(usageEventsQueryEventsForUser, 1);
                    return true;
                case 7:
                    long j13 = parcel.readLong();
                    long j14 = parcel.readLong();
                    int i8 = parcel.readInt();
                    String string7 = parcel.readString();
                    String string8 = parcel.readString();
                    parcel.enforceNoDataAvail();
                    UsageEvents usageEventsQueryEventsForPackageForUser = queryEventsForPackageForUser(j13, j14, i8, string7, string8);
                    parcel2.writeNoException();
                    parcel2.writeTypedObject(usageEventsQueryEventsForPackageForUser, 1);
                    return true;
                case 8:
                    UsageEventsQuery usageEventsQuery = (UsageEventsQuery) parcel.readTypedObject(UsageEventsQuery.CREATOR);
                    String string9 = parcel.readString();
                    parcel.enforceNoDataAvail();
                    UsageEvents usageEventsQueryEventsWithFilter = queryEventsWithFilter(usageEventsQuery, string9);
                    parcel2.writeNoException();
                    parcel2.writeTypedObject(usageEventsQueryEventsWithFilter, 1);
                    return true;
                case 9:
                    String string10 = parcel.readString();
                    boolean z = parcel.readBoolean();
                    int i9 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    setAppInactive(string10, z, i9);
                    parcel2.writeNoException();
                    return true;
                case 10:
                    boolean zIsAppStandbyEnabled = isAppStandbyEnabled();
                    parcel2.writeNoException();
                    parcel2.writeBoolean(zIsAppStandbyEnabled);
                    return true;
                case 11:
                    String string11 = parcel.readString();
                    int i10 = parcel.readInt();
                    String string12 = parcel.readString();
                    parcel.enforceNoDataAvail();
                    boolean zIsAppInactive = isAppInactive(string11, i10, string12);
                    parcel2.writeNoException();
                    parcel2.writeBoolean(zIsAppInactive);
                    return true;
                case 12:
                    onCarrierPrivilegedAppsChanged();
                    parcel2.writeNoException();
                    return true;
                case 13:
                    String string13 = parcel.readString();
                    int i11 = parcel.readInt();
                    String string14 = parcel.readString();
                    String[] strArrCreateStringArray = parcel.createStringArray();
                    String string15 = parcel.readString();
                    parcel.enforceNoDataAvail();
                    reportChooserSelection(string13, i11, string14, strArrCreateStringArray, string15);
                    parcel2.writeNoException();
                    return true;
                case 14:
                    String string16 = parcel.readString();
                    String string17 = parcel.readString();
                    int i12 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    int appStandbyBucket = getAppStandbyBucket(string16, string17, i12);
                    parcel2.writeNoException();
                    parcel2.writeInt(appStandbyBucket);
                    return true;
                case 15:
                    String string18 = parcel.readString();
                    int i13 = parcel.readInt();
                    int i14 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    setAppStandbyBucket(string18, i13, i14);
                    parcel2.writeNoException();
                    return true;
                case 16:
                    String string19 = parcel.readString();
                    int i15 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    ParceledListSlice appStandbyBuckets = getAppStandbyBuckets(string19, i15);
                    parcel2.writeNoException();
                    parcel2.writeTypedObject(appStandbyBuckets, 1);
                    return true;
                case 17:
                    ParceledListSlice parceledListSlice = (ParceledListSlice) parcel.readTypedObject(ParceledListSlice.CREATOR);
                    int i16 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    setAppStandbyBuckets(parceledListSlice, i16);
                    parcel2.writeNoException();
                    return true;
                case 18:
                    String string20 = parcel.readString();
                    String string21 = parcel.readString();
                    int i17 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    int appMinStandbyBucket = getAppMinStandbyBucket(string20, string21, i17);
                    parcel2.writeNoException();
                    parcel2.writeInt(appMinStandbyBucket);
                    return true;
                case 19:
                    String string22 = parcel.readString();
                    long j15 = parcel.readLong();
                    int i18 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    setEstimatedLaunchTime(string22, j15, i18);
                    parcel2.writeNoException();
                    return true;
                case 20:
                    ParceledListSlice parceledListSlice2 = (ParceledListSlice) parcel.readTypedObject(ParceledListSlice.CREATOR);
                    int i19 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    setEstimatedLaunchTimes(parceledListSlice2, i19);
                    parcel2.writeNoException();
                    return true;
                case 21:
                    int i20 = parcel.readInt();
                    String[] strArrCreateStringArray2 = parcel.createStringArray();
                    long j16 = parcel.readLong();
                    PendingIntent pendingIntent = (PendingIntent) parcel.readTypedObject(PendingIntent.CREATOR);
                    String string23 = parcel.readString();
                    parcel.enforceNoDataAvail();
                    registerAppUsageObserver(i20, strArrCreateStringArray2, j16, pendingIntent, string23);
                    parcel2.writeNoException();
                    return true;
                case 22:
                    int i21 = parcel.readInt();
                    String string24 = parcel.readString();
                    parcel.enforceNoDataAvail();
                    unregisterAppUsageObserver(i21, string24);
                    parcel2.writeNoException();
                    return true;
                case 23:
                    int i22 = parcel.readInt();
                    String[] strArrCreateStringArray3 = parcel.createStringArray();
                    long j17 = parcel.readLong();
                    long j18 = parcel.readLong();
                    PendingIntent pendingIntent2 = (PendingIntent) parcel.readTypedObject(PendingIntent.CREATOR);
                    PendingIntent pendingIntent3 = (PendingIntent) parcel.readTypedObject(PendingIntent.CREATOR);
                    String string25 = parcel.readString();
                    parcel.enforceNoDataAvail();
                    registerUsageSessionObserver(i22, strArrCreateStringArray3, j17, j18, pendingIntent2, pendingIntent3, string25);
                    parcel2.writeNoException();
                    return true;
                case 24:
                    int i23 = parcel.readInt();
                    String string26 = parcel.readString();
                    parcel.enforceNoDataAvail();
                    unregisterUsageSessionObserver(i23, string26);
                    parcel2.writeNoException();
                    return true;
                case 25:
                    int i24 = parcel.readInt();
                    String[] strArrCreateStringArray4 = parcel.createStringArray();
                    long j19 = parcel.readLong();
                    long j20 = parcel.readLong();
                    PendingIntent pendingIntent4 = (PendingIntent) parcel.readTypedObject(PendingIntent.CREATOR);
                    String string27 = parcel.readString();
                    parcel.enforceNoDataAvail();
                    registerAppUsageLimitObserver(i24, strArrCreateStringArray4, j19, j20, pendingIntent4, string27);
                    parcel2.writeNoException();
                    return true;
                case 26:
                    int i25 = parcel.readInt();
                    String string28 = parcel.readString();
                    parcel.enforceNoDataAvail();
                    unregisterAppUsageLimitObserver(i25, string28);
                    parcel2.writeNoException();
                    return true;
                case 27:
                    IBinder strongBinder = parcel.readStrongBinder();
                    String string29 = parcel.readString();
                    String string30 = parcel.readString();
                    parcel.enforceNoDataAvail();
                    reportUsageStart(strongBinder, string29, string30);
                    parcel2.writeNoException();
                    return true;
                case 28:
                    IBinder strongBinder2 = parcel.readStrongBinder();
                    String string31 = parcel.readString();
                    long j21 = parcel.readLong();
                    String string32 = parcel.readString();
                    parcel.enforceNoDataAvail();
                    reportPastUsageStart(strongBinder2, string31, j21, string32);
                    parcel2.writeNoException();
                    return true;
                case 29:
                    IBinder strongBinder3 = parcel.readStrongBinder();
                    String string33 = parcel.readString();
                    String string34 = parcel.readString();
                    parcel.enforceNoDataAvail();
                    reportUsageStop(strongBinder3, string33, string34);
                    parcel2.writeNoException();
                    return true;
                case 30:
                    String string35 = parcel.readString();
                    int i26 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    reportUserInteraction(string35, i26);
                    parcel2.writeNoException();
                    return true;
                case 31:
                    String string36 = parcel.readString();
                    int i27 = parcel.readInt();
                    PersistableBundle persistableBundle = (PersistableBundle) parcel.readTypedObject(PersistableBundle.CREATOR);
                    parcel.enforceNoDataAvail();
                    reportUserInteractionWithBundle(string36, i27, persistableBundle);
                    parcel2.writeNoException();
                    return true;
                case 32:
                    int usageSource = getUsageSource();
                    parcel2.writeNoException();
                    parcel2.writeInt(usageSource);
                    return true;
                case 33:
                    forceUsageSourceSettingRead();
                    parcel2.writeNoException();
                    return true;
                case 34:
                    String string37 = parcel.readString();
                    String string38 = parcel.readString();
                    parcel.enforceNoDataAvail();
                    long lastTimeAnyComponentUsed = getLastTimeAnyComponentUsed(string37, string38);
                    parcel2.writeNoException();
                    parcel2.writeLong(lastTimeAnyComponentUsed);
                    return true;
                case 35:
                    String string39 = parcel.readString();
                    long j22 = parcel.readLong();
                    String string40 = parcel.readString();
                    int i28 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    BroadcastResponseStatsList broadcastResponseStatsListQueryBroadcastResponseStats = queryBroadcastResponseStats(string39, j22, string40, i28);
                    parcel2.writeNoException();
                    parcel2.writeTypedObject(broadcastResponseStatsListQueryBroadcastResponseStats, 1);
                    return true;
                case 36:
                    String string41 = parcel.readString();
                    long j23 = parcel.readLong();
                    String string42 = parcel.readString();
                    int i29 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    clearBroadcastResponseStats(string41, j23, string42, i29);
                    parcel2.writeNoException();
                    return true;
                case 37:
                    String string43 = parcel.readString();
                    int i30 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    clearBroadcastEvents(string43, i30);
                    parcel2.writeNoException();
                    return true;
                case 38:
                    String string44 = parcel.readString();
                    int i31 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    boolean zIsPackageExemptedFromBroadcastResponseStats = isPackageExemptedFromBroadcastResponseStats(string44, i31);
                    parcel2.writeNoException();
                    parcel2.writeBoolean(zIsPackageExemptedFromBroadcastResponseStats);
                    return true;
                case 39:
                    String string45 = parcel.readString();
                    parcel.enforceNoDataAvail();
                    String appStandbyConstant = getAppStandbyConstant(string45);
                    parcel2.writeNoException();
                    parcel2.writeString(appStandbyConstant);
                    return true;
                case 40:
                    IUsageStatsWatcher iUsageStatsWatcherAsInterface = IUsageStatsWatcher.Stub.asInterface(parcel.readStrongBinder());
                    parcel.enforceNoDataAvail();
                    registerUsageStatsWatcher(iUsageStatsWatcherAsInterface);
                    parcel2.writeNoException();
                    return true;
                case 41:
                    IUsageStatsWatcher iUsageStatsWatcherAsInterface2 = IUsageStatsWatcher.Stub.asInterface(parcel.readStrongBinder());
                    ArrayList arrayListCreateTypedArrayList = parcel.createTypedArrayList(ComponentName.CREATOR);
                    parcel.enforceNoDataAvail();
                    registerUsageStatsWatcherWithComponent(iUsageStatsWatcherAsInterface2, arrayListCreateTypedArrayList);
                    parcel2.writeNoException();
                    return true;
                case 42:
                    IUsageStatsWatcher iUsageStatsWatcherAsInterface3 = IUsageStatsWatcher.Stub.asInterface(parcel.readStrongBinder());
                    parcel.enforceNoDataAvail();
                    unregisterUsageStatsWatcher(iUsageStatsWatcherAsInterface3);
                    parcel2.writeNoException();
                    return true;
                case 43:
                    deleteUsageStats();
                    parcel2.writeNoException();
                    return true;
                default:
                    return super.onTransact(i, parcel, parcel2, i2);
            }
        }

        private static class Proxy implements IUsageStatsManager {
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

            @Override // android.app.usage.IUsageStatsManager
            public ParceledListSlice queryUsageStats(int i, long j, long j2, String str, int i2) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcelObtain.writeInt(i);
                    parcelObtain.writeLong(j);
                    parcelObtain.writeLong(j2);
                    parcelObtain.writeString(str);
                    parcelObtain.writeInt(i2);
                    this.mRemote.transact(1, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return (ParceledListSlice) parcelObtain2.readTypedObject(ParceledListSlice.CREATOR);
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.app.usage.IUsageStatsManager
            public ParceledListSlice queryConfigurationStats(int i, long j, long j2, String str) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcelObtain.writeInt(i);
                    parcelObtain.writeLong(j);
                    parcelObtain.writeLong(j2);
                    parcelObtain.writeString(str);
                    this.mRemote.transact(2, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return (ParceledListSlice) parcelObtain2.readTypedObject(ParceledListSlice.CREATOR);
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.app.usage.IUsageStatsManager
            public ParceledListSlice queryEventStats(int i, long j, long j2, String str) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcelObtain.writeInt(i);
                    parcelObtain.writeLong(j);
                    parcelObtain.writeLong(j2);
                    parcelObtain.writeString(str);
                    this.mRemote.transact(3, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return (ParceledListSlice) parcelObtain2.readTypedObject(ParceledListSlice.CREATOR);
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.app.usage.IUsageStatsManager
            public UsageEvents queryEvents(long j, long j2, String str) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcelObtain.writeLong(j);
                    parcelObtain.writeLong(j2);
                    parcelObtain.writeString(str);
                    this.mRemote.transact(4, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return (UsageEvents) parcelObtain2.readTypedObject(UsageEvents.CREATOR);
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.app.usage.IUsageStatsManager
            public UsageEvents queryEventsForPackage(long j, long j2, String str) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcelObtain.writeLong(j);
                    parcelObtain.writeLong(j2);
                    parcelObtain.writeString(str);
                    this.mRemote.transact(5, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return (UsageEvents) parcelObtain2.readTypedObject(UsageEvents.CREATOR);
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.app.usage.IUsageStatsManager
            public UsageEvents queryEventsForUser(long j, long j2, int i, String str) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcelObtain.writeLong(j);
                    parcelObtain.writeLong(j2);
                    parcelObtain.writeInt(i);
                    parcelObtain.writeString(str);
                    this.mRemote.transact(6, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return (UsageEvents) parcelObtain2.readTypedObject(UsageEvents.CREATOR);
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.app.usage.IUsageStatsManager
            public UsageEvents queryEventsForPackageForUser(long j, long j2, int i, String str, String str2) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcelObtain.writeLong(j);
                    parcelObtain.writeLong(j2);
                    parcelObtain.writeInt(i);
                    parcelObtain.writeString(str);
                    parcelObtain.writeString(str2);
                    this.mRemote.transact(7, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return (UsageEvents) parcelObtain2.readTypedObject(UsageEvents.CREATOR);
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.app.usage.IUsageStatsManager
            public UsageEvents queryEventsWithFilter(UsageEventsQuery usageEventsQuery, String str) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcelObtain.writeTypedObject(usageEventsQuery, 0);
                    parcelObtain.writeString(str);
                    this.mRemote.transact(8, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return (UsageEvents) parcelObtain2.readTypedObject(UsageEvents.CREATOR);
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.app.usage.IUsageStatsManager
            public void setAppInactive(String str, boolean z, int i) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcelObtain.writeString(str);
                    parcelObtain.writeBoolean(z);
                    parcelObtain.writeInt(i);
                    this.mRemote.transact(9, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.app.usage.IUsageStatsManager
            public boolean isAppStandbyEnabled() throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    this.mRemote.transact(10, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return parcelObtain2.readBoolean();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.app.usage.IUsageStatsManager
            public boolean isAppInactive(String str, int i, String str2) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcelObtain.writeString(str);
                    parcelObtain.writeInt(i);
                    parcelObtain.writeString(str2);
                    this.mRemote.transact(11, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return parcelObtain2.readBoolean();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.app.usage.IUsageStatsManager
            public void onCarrierPrivilegedAppsChanged() throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    this.mRemote.transact(12, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.app.usage.IUsageStatsManager
            public void reportChooserSelection(String str, int i, String str2, String[] strArr, String str3) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcelObtain.writeString(str);
                    parcelObtain.writeInt(i);
                    parcelObtain.writeString(str2);
                    parcelObtain.writeStringArray(strArr);
                    parcelObtain.writeString(str3);
                    this.mRemote.transact(13, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.app.usage.IUsageStatsManager
            public int getAppStandbyBucket(String str, String str2, int i) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcelObtain.writeString(str);
                    parcelObtain.writeString(str2);
                    parcelObtain.writeInt(i);
                    this.mRemote.transact(14, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return parcelObtain2.readInt();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.app.usage.IUsageStatsManager
            public void setAppStandbyBucket(String str, int i, int i2) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcelObtain.writeString(str);
                    parcelObtain.writeInt(i);
                    parcelObtain.writeInt(i2);
                    this.mRemote.transact(15, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.app.usage.IUsageStatsManager
            public ParceledListSlice getAppStandbyBuckets(String str, int i) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcelObtain.writeString(str);
                    parcelObtain.writeInt(i);
                    this.mRemote.transact(16, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return (ParceledListSlice) parcelObtain2.readTypedObject(ParceledListSlice.CREATOR);
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.app.usage.IUsageStatsManager
            public void setAppStandbyBuckets(ParceledListSlice parceledListSlice, int i) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcelObtain.writeTypedObject(parceledListSlice, 0);
                    parcelObtain.writeInt(i);
                    this.mRemote.transact(17, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.app.usage.IUsageStatsManager
            public int getAppMinStandbyBucket(String str, String str2, int i) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcelObtain.writeString(str);
                    parcelObtain.writeString(str2);
                    parcelObtain.writeInt(i);
                    this.mRemote.transact(18, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return parcelObtain2.readInt();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.app.usage.IUsageStatsManager
            public void setEstimatedLaunchTime(String str, long j, int i) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcelObtain.writeString(str);
                    parcelObtain.writeLong(j);
                    parcelObtain.writeInt(i);
                    this.mRemote.transact(19, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.app.usage.IUsageStatsManager
            public void setEstimatedLaunchTimes(ParceledListSlice parceledListSlice, int i) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcelObtain.writeTypedObject(parceledListSlice, 0);
                    parcelObtain.writeInt(i);
                    this.mRemote.transact(20, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.app.usage.IUsageStatsManager
            public void registerAppUsageObserver(int i, String[] strArr, long j, PendingIntent pendingIntent, String str) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcelObtain.writeInt(i);
                    parcelObtain.writeStringArray(strArr);
                    parcelObtain.writeLong(j);
                    parcelObtain.writeTypedObject(pendingIntent, 0);
                    parcelObtain.writeString(str);
                    this.mRemote.transact(21, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.app.usage.IUsageStatsManager
            public void unregisterAppUsageObserver(int i, String str) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcelObtain.writeInt(i);
                    parcelObtain.writeString(str);
                    this.mRemote.transact(22, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.app.usage.IUsageStatsManager
            public void registerUsageSessionObserver(int i, String[] strArr, long j, long j2, PendingIntent pendingIntent, PendingIntent pendingIntent2, String str) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcelObtain.writeInt(i);
                    parcelObtain.writeStringArray(strArr);
                    parcelObtain.writeLong(j);
                    parcelObtain.writeLong(j2);
                    parcelObtain.writeTypedObject(pendingIntent, 0);
                    parcelObtain.writeTypedObject(pendingIntent2, 0);
                    parcelObtain.writeString(str);
                    this.mRemote.transact(23, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.app.usage.IUsageStatsManager
            public void unregisterUsageSessionObserver(int i, String str) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcelObtain.writeInt(i);
                    parcelObtain.writeString(str);
                    this.mRemote.transact(24, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.app.usage.IUsageStatsManager
            public void registerAppUsageLimitObserver(int i, String[] strArr, long j, long j2, PendingIntent pendingIntent, String str) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcelObtain.writeInt(i);
                    parcelObtain.writeStringArray(strArr);
                    parcelObtain.writeLong(j);
                    parcelObtain.writeLong(j2);
                    parcelObtain.writeTypedObject(pendingIntent, 0);
                    parcelObtain.writeString(str);
                    this.mRemote.transact(25, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.app.usage.IUsageStatsManager
            public void unregisterAppUsageLimitObserver(int i, String str) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcelObtain.writeInt(i);
                    parcelObtain.writeString(str);
                    this.mRemote.transact(26, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.app.usage.IUsageStatsManager
            public void reportUsageStart(IBinder iBinder, String str, String str2) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcelObtain.writeStrongBinder(iBinder);
                    parcelObtain.writeString(str);
                    parcelObtain.writeString(str2);
                    this.mRemote.transact(27, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.app.usage.IUsageStatsManager
            public void reportPastUsageStart(IBinder iBinder, String str, long j, String str2) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcelObtain.writeStrongBinder(iBinder);
                    parcelObtain.writeString(str);
                    parcelObtain.writeLong(j);
                    parcelObtain.writeString(str2);
                    this.mRemote.transact(28, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.app.usage.IUsageStatsManager
            public void reportUsageStop(IBinder iBinder, String str, String str2) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcelObtain.writeStrongBinder(iBinder);
                    parcelObtain.writeString(str);
                    parcelObtain.writeString(str2);
                    this.mRemote.transact(29, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.app.usage.IUsageStatsManager
            public void reportUserInteraction(String str, int i) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcelObtain.writeString(str);
                    parcelObtain.writeInt(i);
                    this.mRemote.transact(30, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.app.usage.IUsageStatsManager
            public void reportUserInteractionWithBundle(String str, int i, PersistableBundle persistableBundle) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcelObtain.writeString(str);
                    parcelObtain.writeInt(i);
                    parcelObtain.writeTypedObject(persistableBundle, 0);
                    this.mRemote.transact(31, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.app.usage.IUsageStatsManager
            public int getUsageSource() throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    this.mRemote.transact(32, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return parcelObtain2.readInt();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.app.usage.IUsageStatsManager
            public void forceUsageSourceSettingRead() throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    this.mRemote.transact(33, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.app.usage.IUsageStatsManager
            public long getLastTimeAnyComponentUsed(String str, String str2) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcelObtain.writeString(str);
                    parcelObtain.writeString(str2);
                    this.mRemote.transact(34, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return parcelObtain2.readLong();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.app.usage.IUsageStatsManager
            public BroadcastResponseStatsList queryBroadcastResponseStats(String str, long j, String str2, int i) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcelObtain.writeString(str);
                    parcelObtain.writeLong(j);
                    parcelObtain.writeString(str2);
                    parcelObtain.writeInt(i);
                    this.mRemote.transact(35, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return (BroadcastResponseStatsList) parcelObtain2.readTypedObject(BroadcastResponseStatsList.CREATOR);
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.app.usage.IUsageStatsManager
            public void clearBroadcastResponseStats(String str, long j, String str2, int i) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcelObtain.writeString(str);
                    parcelObtain.writeLong(j);
                    parcelObtain.writeString(str2);
                    parcelObtain.writeInt(i);
                    this.mRemote.transact(36, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.app.usage.IUsageStatsManager
            public void clearBroadcastEvents(String str, int i) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcelObtain.writeString(str);
                    parcelObtain.writeInt(i);
                    this.mRemote.transact(37, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.app.usage.IUsageStatsManager
            public boolean isPackageExemptedFromBroadcastResponseStats(String str, int i) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcelObtain.writeString(str);
                    parcelObtain.writeInt(i);
                    this.mRemote.transact(38, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return parcelObtain2.readBoolean();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.app.usage.IUsageStatsManager
            public String getAppStandbyConstant(String str) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcelObtain.writeString(str);
                    this.mRemote.transact(39, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return parcelObtain2.readString();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.app.usage.IUsageStatsManager
            public void registerUsageStatsWatcher(IUsageStatsWatcher iUsageStatsWatcher) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcelObtain.writeStrongInterface(iUsageStatsWatcher);
                    this.mRemote.transact(40, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.app.usage.IUsageStatsManager
            public void registerUsageStatsWatcherWithComponent(IUsageStatsWatcher iUsageStatsWatcher, List<ComponentName> list) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcelObtain.writeStrongInterface(iUsageStatsWatcher);
                    parcelObtain.writeTypedList(list, 0);
                    this.mRemote.transact(41, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.app.usage.IUsageStatsManager
            public void unregisterUsageStatsWatcher(IUsageStatsWatcher iUsageStatsWatcher) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcelObtain.writeStrongInterface(iUsageStatsWatcher);
                    this.mRemote.transact(42, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.app.usage.IUsageStatsManager
            public void deleteUsageStats() throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    this.mRemote.transact(43, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }
        }

        protected void setAppStandbyBucket_enforcePermission() throws SecurityException {
            this.mEnforcer.enforcePermission(Manifest.permission.CHANGE_APP_IDLE_STATE, getCallingPid(), getCallingUid());
        }

        protected void setAppStandbyBuckets_enforcePermission() throws SecurityException {
            this.mEnforcer.enforcePermission(Manifest.permission.CHANGE_APP_IDLE_STATE, getCallingPid(), getCallingUid());
        }

        protected void setEstimatedLaunchTime_enforcePermission() throws SecurityException {
            this.mEnforcer.enforcePermission(Manifest.permission.CHANGE_APP_LAUNCH_TIME_ESTIMATE, getCallingPid(), getCallingUid());
        }

        protected void setEstimatedLaunchTimes_enforcePermission() throws SecurityException {
            this.mEnforcer.enforcePermission(Manifest.permission.CHANGE_APP_LAUNCH_TIME_ESTIMATE, getCallingPid(), getCallingUid());
        }
    }
}
