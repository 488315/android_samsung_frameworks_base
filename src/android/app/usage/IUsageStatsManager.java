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
            IInterface queryLocalInterface = iBinder.queryLocalInterface(DESCRIPTOR);
            if (queryLocalInterface != null && (queryLocalInterface instanceof IUsageStatsManager)) {
                return (IUsageStatsManager) queryLocalInterface;
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
                    int readInt = parcel.readInt();
                    long readLong = parcel.readLong();
                    long readLong2 = parcel.readLong();
                    String readString = parcel.readString();
                    int readInt2 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    ParceledListSlice queryUsageStats = queryUsageStats(readInt, readLong, readLong2, readString, readInt2);
                    parcel2.writeNoException();
                    parcel2.writeTypedObject(queryUsageStats, 1);
                    return true;
                case 2:
                    int readInt3 = parcel.readInt();
                    long readLong3 = parcel.readLong();
                    long readLong4 = parcel.readLong();
                    String readString2 = parcel.readString();
                    parcel.enforceNoDataAvail();
                    ParceledListSlice queryConfigurationStats = queryConfigurationStats(readInt3, readLong3, readLong4, readString2);
                    parcel2.writeNoException();
                    parcel2.writeTypedObject(queryConfigurationStats, 1);
                    return true;
                case 3:
                    int readInt4 = parcel.readInt();
                    long readLong5 = parcel.readLong();
                    long readLong6 = parcel.readLong();
                    String readString3 = parcel.readString();
                    parcel.enforceNoDataAvail();
                    ParceledListSlice queryEventStats = queryEventStats(readInt4, readLong5, readLong6, readString3);
                    parcel2.writeNoException();
                    parcel2.writeTypedObject(queryEventStats, 1);
                    return true;
                case 4:
                    long readLong7 = parcel.readLong();
                    long readLong8 = parcel.readLong();
                    String readString4 = parcel.readString();
                    parcel.enforceNoDataAvail();
                    UsageEvents queryEvents = queryEvents(readLong7, readLong8, readString4);
                    parcel2.writeNoException();
                    parcel2.writeTypedObject(queryEvents, 1);
                    return true;
                case 5:
                    long readLong9 = parcel.readLong();
                    long readLong10 = parcel.readLong();
                    String readString5 = parcel.readString();
                    parcel.enforceNoDataAvail();
                    UsageEvents queryEventsForPackage = queryEventsForPackage(readLong9, readLong10, readString5);
                    parcel2.writeNoException();
                    parcel2.writeTypedObject(queryEventsForPackage, 1);
                    return true;
                case 6:
                    long readLong11 = parcel.readLong();
                    long readLong12 = parcel.readLong();
                    int readInt5 = parcel.readInt();
                    String readString6 = parcel.readString();
                    parcel.enforceNoDataAvail();
                    UsageEvents queryEventsForUser = queryEventsForUser(readLong11, readLong12, readInt5, readString6);
                    parcel2.writeNoException();
                    parcel2.writeTypedObject(queryEventsForUser, 1);
                    return true;
                case 7:
                    long readLong13 = parcel.readLong();
                    long readLong14 = parcel.readLong();
                    int readInt6 = parcel.readInt();
                    String readString7 = parcel.readString();
                    String readString8 = parcel.readString();
                    parcel.enforceNoDataAvail();
                    UsageEvents queryEventsForPackageForUser = queryEventsForPackageForUser(readLong13, readLong14, readInt6, readString7, readString8);
                    parcel2.writeNoException();
                    parcel2.writeTypedObject(queryEventsForPackageForUser, 1);
                    return true;
                case 8:
                    UsageEventsQuery usageEventsQuery = (UsageEventsQuery) parcel.readTypedObject(UsageEventsQuery.CREATOR);
                    String readString9 = parcel.readString();
                    parcel.enforceNoDataAvail();
                    UsageEvents queryEventsWithFilter = queryEventsWithFilter(usageEventsQuery, readString9);
                    parcel2.writeNoException();
                    parcel2.writeTypedObject(queryEventsWithFilter, 1);
                    return true;
                case 9:
                    String readString10 = parcel.readString();
                    boolean readBoolean = parcel.readBoolean();
                    int readInt7 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    setAppInactive(readString10, readBoolean, readInt7);
                    parcel2.writeNoException();
                    return true;
                case 10:
                    boolean isAppStandbyEnabled = isAppStandbyEnabled();
                    parcel2.writeNoException();
                    parcel2.writeBoolean(isAppStandbyEnabled);
                    return true;
                case 11:
                    String readString11 = parcel.readString();
                    int readInt8 = parcel.readInt();
                    String readString12 = parcel.readString();
                    parcel.enforceNoDataAvail();
                    boolean isAppInactive = isAppInactive(readString11, readInt8, readString12);
                    parcel2.writeNoException();
                    parcel2.writeBoolean(isAppInactive);
                    return true;
                case 12:
                    onCarrierPrivilegedAppsChanged();
                    parcel2.writeNoException();
                    return true;
                case 13:
                    String readString13 = parcel.readString();
                    int readInt9 = parcel.readInt();
                    String readString14 = parcel.readString();
                    String[] createStringArray = parcel.createStringArray();
                    String readString15 = parcel.readString();
                    parcel.enforceNoDataAvail();
                    reportChooserSelection(readString13, readInt9, readString14, createStringArray, readString15);
                    parcel2.writeNoException();
                    return true;
                case 14:
                    String readString16 = parcel.readString();
                    String readString17 = parcel.readString();
                    int readInt10 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    int appStandbyBucket = getAppStandbyBucket(readString16, readString17, readInt10);
                    parcel2.writeNoException();
                    parcel2.writeInt(appStandbyBucket);
                    return true;
                case 15:
                    String readString18 = parcel.readString();
                    int readInt11 = parcel.readInt();
                    int readInt12 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    setAppStandbyBucket(readString18, readInt11, readInt12);
                    parcel2.writeNoException();
                    return true;
                case 16:
                    String readString19 = parcel.readString();
                    int readInt13 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    ParceledListSlice appStandbyBuckets = getAppStandbyBuckets(readString19, readInt13);
                    parcel2.writeNoException();
                    parcel2.writeTypedObject(appStandbyBuckets, 1);
                    return true;
                case 17:
                    ParceledListSlice parceledListSlice = (ParceledListSlice) parcel.readTypedObject(ParceledListSlice.CREATOR);
                    int readInt14 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    setAppStandbyBuckets(parceledListSlice, readInt14);
                    parcel2.writeNoException();
                    return true;
                case 18:
                    String readString20 = parcel.readString();
                    String readString21 = parcel.readString();
                    int readInt15 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    int appMinStandbyBucket = getAppMinStandbyBucket(readString20, readString21, readInt15);
                    parcel2.writeNoException();
                    parcel2.writeInt(appMinStandbyBucket);
                    return true;
                case 19:
                    String readString22 = parcel.readString();
                    long readLong15 = parcel.readLong();
                    int readInt16 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    setEstimatedLaunchTime(readString22, readLong15, readInt16);
                    parcel2.writeNoException();
                    return true;
                case 20:
                    ParceledListSlice parceledListSlice2 = (ParceledListSlice) parcel.readTypedObject(ParceledListSlice.CREATOR);
                    int readInt17 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    setEstimatedLaunchTimes(parceledListSlice2, readInt17);
                    parcel2.writeNoException();
                    return true;
                case 21:
                    int readInt18 = parcel.readInt();
                    String[] createStringArray2 = parcel.createStringArray();
                    long readLong16 = parcel.readLong();
                    PendingIntent pendingIntent = (PendingIntent) parcel.readTypedObject(PendingIntent.CREATOR);
                    String readString23 = parcel.readString();
                    parcel.enforceNoDataAvail();
                    registerAppUsageObserver(readInt18, createStringArray2, readLong16, pendingIntent, readString23);
                    parcel2.writeNoException();
                    return true;
                case 22:
                    int readInt19 = parcel.readInt();
                    String readString24 = parcel.readString();
                    parcel.enforceNoDataAvail();
                    unregisterAppUsageObserver(readInt19, readString24);
                    parcel2.writeNoException();
                    return true;
                case 23:
                    int readInt20 = parcel.readInt();
                    String[] createStringArray3 = parcel.createStringArray();
                    long readLong17 = parcel.readLong();
                    long readLong18 = parcel.readLong();
                    PendingIntent pendingIntent2 = (PendingIntent) parcel.readTypedObject(PendingIntent.CREATOR);
                    PendingIntent pendingIntent3 = (PendingIntent) parcel.readTypedObject(PendingIntent.CREATOR);
                    String readString25 = parcel.readString();
                    parcel.enforceNoDataAvail();
                    registerUsageSessionObserver(readInt20, createStringArray3, readLong17, readLong18, pendingIntent2, pendingIntent3, readString25);
                    parcel2.writeNoException();
                    return true;
                case 24:
                    int readInt21 = parcel.readInt();
                    String readString26 = parcel.readString();
                    parcel.enforceNoDataAvail();
                    unregisterUsageSessionObserver(readInt21, readString26);
                    parcel2.writeNoException();
                    return true;
                case 25:
                    int readInt22 = parcel.readInt();
                    String[] createStringArray4 = parcel.createStringArray();
                    long readLong19 = parcel.readLong();
                    long readLong20 = parcel.readLong();
                    PendingIntent pendingIntent4 = (PendingIntent) parcel.readTypedObject(PendingIntent.CREATOR);
                    String readString27 = parcel.readString();
                    parcel.enforceNoDataAvail();
                    registerAppUsageLimitObserver(readInt22, createStringArray4, readLong19, readLong20, pendingIntent4, readString27);
                    parcel2.writeNoException();
                    return true;
                case 26:
                    int readInt23 = parcel.readInt();
                    String readString28 = parcel.readString();
                    parcel.enforceNoDataAvail();
                    unregisterAppUsageLimitObserver(readInt23, readString28);
                    parcel2.writeNoException();
                    return true;
                case 27:
                    IBinder readStrongBinder = parcel.readStrongBinder();
                    String readString29 = parcel.readString();
                    String readString30 = parcel.readString();
                    parcel.enforceNoDataAvail();
                    reportUsageStart(readStrongBinder, readString29, readString30);
                    parcel2.writeNoException();
                    return true;
                case 28:
                    IBinder readStrongBinder2 = parcel.readStrongBinder();
                    String readString31 = parcel.readString();
                    long readLong21 = parcel.readLong();
                    String readString32 = parcel.readString();
                    parcel.enforceNoDataAvail();
                    reportPastUsageStart(readStrongBinder2, readString31, readLong21, readString32);
                    parcel2.writeNoException();
                    return true;
                case 29:
                    IBinder readStrongBinder3 = parcel.readStrongBinder();
                    String readString33 = parcel.readString();
                    String readString34 = parcel.readString();
                    parcel.enforceNoDataAvail();
                    reportUsageStop(readStrongBinder3, readString33, readString34);
                    parcel2.writeNoException();
                    return true;
                case 30:
                    String readString35 = parcel.readString();
                    int readInt24 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    reportUserInteraction(readString35, readInt24);
                    parcel2.writeNoException();
                    return true;
                case 31:
                    String readString36 = parcel.readString();
                    int readInt25 = parcel.readInt();
                    PersistableBundle persistableBundle = (PersistableBundle) parcel.readTypedObject(PersistableBundle.CREATOR);
                    parcel.enforceNoDataAvail();
                    reportUserInteractionWithBundle(readString36, readInt25, persistableBundle);
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
                    String readString37 = parcel.readString();
                    String readString38 = parcel.readString();
                    parcel.enforceNoDataAvail();
                    long lastTimeAnyComponentUsed = getLastTimeAnyComponentUsed(readString37, readString38);
                    parcel2.writeNoException();
                    parcel2.writeLong(lastTimeAnyComponentUsed);
                    return true;
                case 35:
                    String readString39 = parcel.readString();
                    long readLong22 = parcel.readLong();
                    String readString40 = parcel.readString();
                    int readInt26 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    BroadcastResponseStatsList queryBroadcastResponseStats = queryBroadcastResponseStats(readString39, readLong22, readString40, readInt26);
                    parcel2.writeNoException();
                    parcel2.writeTypedObject(queryBroadcastResponseStats, 1);
                    return true;
                case 36:
                    String readString41 = parcel.readString();
                    long readLong23 = parcel.readLong();
                    String readString42 = parcel.readString();
                    int readInt27 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    clearBroadcastResponseStats(readString41, readLong23, readString42, readInt27);
                    parcel2.writeNoException();
                    return true;
                case 37:
                    String readString43 = parcel.readString();
                    int readInt28 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    clearBroadcastEvents(readString43, readInt28);
                    parcel2.writeNoException();
                    return true;
                case 38:
                    String readString44 = parcel.readString();
                    int readInt29 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    boolean isPackageExemptedFromBroadcastResponseStats = isPackageExemptedFromBroadcastResponseStats(readString44, readInt29);
                    parcel2.writeNoException();
                    parcel2.writeBoolean(isPackageExemptedFromBroadcastResponseStats);
                    return true;
                case 39:
                    String readString45 = parcel.readString();
                    parcel.enforceNoDataAvail();
                    String appStandbyConstant = getAppStandbyConstant(readString45);
                    parcel2.writeNoException();
                    parcel2.writeString(appStandbyConstant);
                    return true;
                case 40:
                    IUsageStatsWatcher asInterface = IUsageStatsWatcher.Stub.asInterface(parcel.readStrongBinder());
                    parcel.enforceNoDataAvail();
                    registerUsageStatsWatcher(asInterface);
                    parcel2.writeNoException();
                    return true;
                case 41:
                    IUsageStatsWatcher asInterface2 = IUsageStatsWatcher.Stub.asInterface(parcel.readStrongBinder());
                    ArrayList createTypedArrayList = parcel.createTypedArrayList(ComponentName.CREATOR);
                    parcel.enforceNoDataAvail();
                    registerUsageStatsWatcherWithComponent(asInterface2, createTypedArrayList);
                    parcel2.writeNoException();
                    return true;
                case 42:
                    IUsageStatsWatcher asInterface3 = IUsageStatsWatcher.Stub.asInterface(parcel.readStrongBinder());
                    parcel.enforceNoDataAvail();
                    unregisterUsageStatsWatcher(asInterface3);
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
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    obtain.writeInt(i);
                    obtain.writeLong(j);
                    obtain.writeLong(j2);
                    obtain.writeString(str);
                    obtain.writeInt(i2);
                    this.mRemote.transact(1, obtain, obtain2, 0);
                    obtain2.readException();
                    return (ParceledListSlice) obtain2.readTypedObject(ParceledListSlice.CREATOR);
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.app.usage.IUsageStatsManager
            public ParceledListSlice queryConfigurationStats(int i, long j, long j2, String str) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    obtain.writeInt(i);
                    obtain.writeLong(j);
                    obtain.writeLong(j2);
                    obtain.writeString(str);
                    this.mRemote.transact(2, obtain, obtain2, 0);
                    obtain2.readException();
                    return (ParceledListSlice) obtain2.readTypedObject(ParceledListSlice.CREATOR);
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.app.usage.IUsageStatsManager
            public ParceledListSlice queryEventStats(int i, long j, long j2, String str) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    obtain.writeInt(i);
                    obtain.writeLong(j);
                    obtain.writeLong(j2);
                    obtain.writeString(str);
                    this.mRemote.transact(3, obtain, obtain2, 0);
                    obtain2.readException();
                    return (ParceledListSlice) obtain2.readTypedObject(ParceledListSlice.CREATOR);
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.app.usage.IUsageStatsManager
            public UsageEvents queryEvents(long j, long j2, String str) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    obtain.writeLong(j);
                    obtain.writeLong(j2);
                    obtain.writeString(str);
                    this.mRemote.transact(4, obtain, obtain2, 0);
                    obtain2.readException();
                    return (UsageEvents) obtain2.readTypedObject(UsageEvents.CREATOR);
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.app.usage.IUsageStatsManager
            public UsageEvents queryEventsForPackage(long j, long j2, String str) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    obtain.writeLong(j);
                    obtain.writeLong(j2);
                    obtain.writeString(str);
                    this.mRemote.transact(5, obtain, obtain2, 0);
                    obtain2.readException();
                    return (UsageEvents) obtain2.readTypedObject(UsageEvents.CREATOR);
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.app.usage.IUsageStatsManager
            public UsageEvents queryEventsForUser(long j, long j2, int i, String str) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    obtain.writeLong(j);
                    obtain.writeLong(j2);
                    obtain.writeInt(i);
                    obtain.writeString(str);
                    this.mRemote.transact(6, obtain, obtain2, 0);
                    obtain2.readException();
                    return (UsageEvents) obtain2.readTypedObject(UsageEvents.CREATOR);
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.app.usage.IUsageStatsManager
            public UsageEvents queryEventsForPackageForUser(long j, long j2, int i, String str, String str2) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    obtain.writeLong(j);
                    obtain.writeLong(j2);
                    obtain.writeInt(i);
                    obtain.writeString(str);
                    obtain.writeString(str2);
                    this.mRemote.transact(7, obtain, obtain2, 0);
                    obtain2.readException();
                    return (UsageEvents) obtain2.readTypedObject(UsageEvents.CREATOR);
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.app.usage.IUsageStatsManager
            public UsageEvents queryEventsWithFilter(UsageEventsQuery usageEventsQuery, String str) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    obtain.writeTypedObject(usageEventsQuery, 0);
                    obtain.writeString(str);
                    this.mRemote.transact(8, obtain, obtain2, 0);
                    obtain2.readException();
                    return (UsageEvents) obtain2.readTypedObject(UsageEvents.CREATOR);
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.app.usage.IUsageStatsManager
            public void setAppInactive(String str, boolean z, int i) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    obtain.writeString(str);
                    obtain.writeBoolean(z);
                    obtain.writeInt(i);
                    this.mRemote.transact(9, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.app.usage.IUsageStatsManager
            public boolean isAppStandbyEnabled() throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    this.mRemote.transact(10, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readBoolean();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.app.usage.IUsageStatsManager
            public boolean isAppInactive(String str, int i, String str2) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    obtain.writeString(str);
                    obtain.writeInt(i);
                    obtain.writeString(str2);
                    this.mRemote.transact(11, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readBoolean();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.app.usage.IUsageStatsManager
            public void onCarrierPrivilegedAppsChanged() throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    this.mRemote.transact(12, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.app.usage.IUsageStatsManager
            public void reportChooserSelection(String str, int i, String str2, String[] strArr, String str3) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    obtain.writeString(str);
                    obtain.writeInt(i);
                    obtain.writeString(str2);
                    obtain.writeStringArray(strArr);
                    obtain.writeString(str3);
                    this.mRemote.transact(13, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.app.usage.IUsageStatsManager
            public int getAppStandbyBucket(String str, String str2, int i) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    obtain.writeString(str);
                    obtain.writeString(str2);
                    obtain.writeInt(i);
                    this.mRemote.transact(14, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readInt();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.app.usage.IUsageStatsManager
            public void setAppStandbyBucket(String str, int i, int i2) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    obtain.writeString(str);
                    obtain.writeInt(i);
                    obtain.writeInt(i2);
                    this.mRemote.transact(15, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.app.usage.IUsageStatsManager
            public ParceledListSlice getAppStandbyBuckets(String str, int i) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    obtain.writeString(str);
                    obtain.writeInt(i);
                    this.mRemote.transact(16, obtain, obtain2, 0);
                    obtain2.readException();
                    return (ParceledListSlice) obtain2.readTypedObject(ParceledListSlice.CREATOR);
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.app.usage.IUsageStatsManager
            public void setAppStandbyBuckets(ParceledListSlice parceledListSlice, int i) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    obtain.writeTypedObject(parceledListSlice, 0);
                    obtain.writeInt(i);
                    this.mRemote.transact(17, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.app.usage.IUsageStatsManager
            public int getAppMinStandbyBucket(String str, String str2, int i) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    obtain.writeString(str);
                    obtain.writeString(str2);
                    obtain.writeInt(i);
                    this.mRemote.transact(18, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readInt();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.app.usage.IUsageStatsManager
            public void setEstimatedLaunchTime(String str, long j, int i) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    obtain.writeString(str);
                    obtain.writeLong(j);
                    obtain.writeInt(i);
                    this.mRemote.transact(19, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.app.usage.IUsageStatsManager
            public void setEstimatedLaunchTimes(ParceledListSlice parceledListSlice, int i) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    obtain.writeTypedObject(parceledListSlice, 0);
                    obtain.writeInt(i);
                    this.mRemote.transact(20, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.app.usage.IUsageStatsManager
            public void registerAppUsageObserver(int i, String[] strArr, long j, PendingIntent pendingIntent, String str) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    obtain.writeInt(i);
                    obtain.writeStringArray(strArr);
                    obtain.writeLong(j);
                    obtain.writeTypedObject(pendingIntent, 0);
                    obtain.writeString(str);
                    this.mRemote.transact(21, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.app.usage.IUsageStatsManager
            public void unregisterAppUsageObserver(int i, String str) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    obtain.writeInt(i);
                    obtain.writeString(str);
                    this.mRemote.transact(22, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.app.usage.IUsageStatsManager
            public void registerUsageSessionObserver(int i, String[] strArr, long j, long j2, PendingIntent pendingIntent, PendingIntent pendingIntent2, String str) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    obtain.writeInt(i);
                    obtain.writeStringArray(strArr);
                    obtain.writeLong(j);
                    obtain.writeLong(j2);
                    obtain.writeTypedObject(pendingIntent, 0);
                    obtain.writeTypedObject(pendingIntent2, 0);
                    obtain.writeString(str);
                    this.mRemote.transact(23, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.app.usage.IUsageStatsManager
            public void unregisterUsageSessionObserver(int i, String str) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    obtain.writeInt(i);
                    obtain.writeString(str);
                    this.mRemote.transact(24, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.app.usage.IUsageStatsManager
            public void registerAppUsageLimitObserver(int i, String[] strArr, long j, long j2, PendingIntent pendingIntent, String str) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    obtain.writeInt(i);
                    obtain.writeStringArray(strArr);
                    obtain.writeLong(j);
                    obtain.writeLong(j2);
                    obtain.writeTypedObject(pendingIntent, 0);
                    obtain.writeString(str);
                    this.mRemote.transact(25, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.app.usage.IUsageStatsManager
            public void unregisterAppUsageLimitObserver(int i, String str) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    obtain.writeInt(i);
                    obtain.writeString(str);
                    this.mRemote.transact(26, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.app.usage.IUsageStatsManager
            public void reportUsageStart(IBinder iBinder, String str, String str2) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    obtain.writeStrongBinder(iBinder);
                    obtain.writeString(str);
                    obtain.writeString(str2);
                    this.mRemote.transact(27, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.app.usage.IUsageStatsManager
            public void reportPastUsageStart(IBinder iBinder, String str, long j, String str2) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    obtain.writeStrongBinder(iBinder);
                    obtain.writeString(str);
                    obtain.writeLong(j);
                    obtain.writeString(str2);
                    this.mRemote.transact(28, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.app.usage.IUsageStatsManager
            public void reportUsageStop(IBinder iBinder, String str, String str2) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    obtain.writeStrongBinder(iBinder);
                    obtain.writeString(str);
                    obtain.writeString(str2);
                    this.mRemote.transact(29, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.app.usage.IUsageStatsManager
            public void reportUserInteraction(String str, int i) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    obtain.writeString(str);
                    obtain.writeInt(i);
                    this.mRemote.transact(30, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.app.usage.IUsageStatsManager
            public void reportUserInteractionWithBundle(String str, int i, PersistableBundle persistableBundle) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    obtain.writeString(str);
                    obtain.writeInt(i);
                    obtain.writeTypedObject(persistableBundle, 0);
                    this.mRemote.transact(31, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.app.usage.IUsageStatsManager
            public int getUsageSource() throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    this.mRemote.transact(32, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readInt();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.app.usage.IUsageStatsManager
            public void forceUsageSourceSettingRead() throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    this.mRemote.transact(33, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.app.usage.IUsageStatsManager
            public long getLastTimeAnyComponentUsed(String str, String str2) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    obtain.writeString(str);
                    obtain.writeString(str2);
                    this.mRemote.transact(34, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readLong();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.app.usage.IUsageStatsManager
            public BroadcastResponseStatsList queryBroadcastResponseStats(String str, long j, String str2, int i) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    obtain.writeString(str);
                    obtain.writeLong(j);
                    obtain.writeString(str2);
                    obtain.writeInt(i);
                    this.mRemote.transact(35, obtain, obtain2, 0);
                    obtain2.readException();
                    return (BroadcastResponseStatsList) obtain2.readTypedObject(BroadcastResponseStatsList.CREATOR);
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.app.usage.IUsageStatsManager
            public void clearBroadcastResponseStats(String str, long j, String str2, int i) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    obtain.writeString(str);
                    obtain.writeLong(j);
                    obtain.writeString(str2);
                    obtain.writeInt(i);
                    this.mRemote.transact(36, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.app.usage.IUsageStatsManager
            public void clearBroadcastEvents(String str, int i) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    obtain.writeString(str);
                    obtain.writeInt(i);
                    this.mRemote.transact(37, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.app.usage.IUsageStatsManager
            public boolean isPackageExemptedFromBroadcastResponseStats(String str, int i) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    obtain.writeString(str);
                    obtain.writeInt(i);
                    this.mRemote.transact(38, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readBoolean();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.app.usage.IUsageStatsManager
            public String getAppStandbyConstant(String str) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    obtain.writeString(str);
                    this.mRemote.transact(39, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readString();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.app.usage.IUsageStatsManager
            public void registerUsageStatsWatcher(IUsageStatsWatcher iUsageStatsWatcher) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    obtain.writeStrongInterface(iUsageStatsWatcher);
                    this.mRemote.transact(40, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.app.usage.IUsageStatsManager
            public void registerUsageStatsWatcherWithComponent(IUsageStatsWatcher iUsageStatsWatcher, List<ComponentName> list) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    obtain.writeStrongInterface(iUsageStatsWatcher);
                    obtain.writeTypedList(list, 0);
                    this.mRemote.transact(41, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.app.usage.IUsageStatsManager
            public void unregisterUsageStatsWatcher(IUsageStatsWatcher iUsageStatsWatcher) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    obtain.writeStrongInterface(iUsageStatsWatcher);
                    this.mRemote.transact(42, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.app.usage.IUsageStatsManager
            public void deleteUsageStats() throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    this.mRemote.transact(43, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
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
