package com.android.server.notification;

import android.R;
import android.app.INotificationManager;
import android.app.NotificationManager;
import android.content.ComponentName;
import android.content.Context;
import android.content.pm.IPackageManager;
import android.content.pm.ServiceInfo;
import android.net.ConnectivityModuleConnector$$ExternalSyntheticOutline0;
import android.net.Uri;
import android.os.Binder;
import android.os.IBinder;
import android.os.IInterface;
import android.os.RemoteException;
import android.os.SystemProperties;
import android.service.notification.Condition;
import android.service.notification.IConditionProvider;
import android.service.notification.ZenModeConfig;
import android.text.TextUtils;
import android.text.format.DateUtils;
import android.util.ArrayMap;
import android.util.ArraySet;
import android.util.LocalLog;
import android.util.Log;
import android.util.Slog;
import com.android.modules.utils.TypedXmlSerializer;
import com.android.server.HeimdAllFsService$$ExternalSyntheticOutline0;
import com.android.server.ambientcontext.AmbientContextManagerPerUserService$$ExternalSyntheticOutline0;
import com.android.server.notification.ManagedServices;
import com.android.server.notification.NotificationManagerService;
import java.io.PrintWriter;
import java.util.ArrayList;

/* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
/* loaded from: classes2.dex */
public final class ConditionProviders extends ManagedServices {
    static final String TAG_ENABLED_DND_APPS = "dnd_apps";
    public Callback mCallback;
    public final ArrayList mRecords;
    public final ArraySet mSystemConditionProviderNames;
    public final ArraySet mSystemConditionProviders;

    /* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
    public interface Callback {
    }

    /* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
    public final class ConditionRecord {
        public final ComponentName component;
        public Condition condition;
        public final Uri id;
        public ManagedServices.ManagedServiceInfo info;
        public boolean subscribed;

        public ConditionRecord(ComponentName componentName, Uri uri) {
            this.id = uri;
            this.component = componentName;
        }

        public final String toString() {
            return "ConditionRecord[id=" + this.id + ",component=" + this.component + ",subscribed=" + this.subscribed + ']';
        }
    }

    public ConditionProviders(Context context, ManagedServices.UserProfiles userProfiles, IPackageManager iPackageManager) {
        super(context, new Object(), userProfiles, iPackageManager);
        this.mRecords = new ArrayList();
        this.mSystemConditionProviders = new ArraySet();
        Context context2 = this.mContext;
        String str = SystemProperties.get("system.condition.providers", "UNSET");
        String[] split = !"UNSET".equals(str) ? str.split(",") : context2.getResources().getStringArray(17236326);
        ArraySet arraySet = new ArraySet();
        if (split != null && split.length != 0) {
            for (String str2 : split) {
                if (str2 != null) {
                    arraySet.add(str2);
                }
            }
        }
        this.mSystemConditionProviderNames = arraySet;
        this.mApprovalLevel = 0;
    }

    public final void addSystemProvider(SystemConditionProviderService systemConditionProviderService) {
        this.mSystemConditionProviders.add(systemConditionProviderService);
        systemConditionProviderService.attachBase(this.mContext);
        IConditionProvider asInterface = systemConditionProviderService.asInterface();
        ComponentName component = systemConditionProviderService.getComponent();
        checkNotNull(asInterface);
        ManagedServices.ManagedServiceInfo registerServiceImpl = registerServiceImpl(new ManagedServices.ManagedServiceInfo(asInterface, component, 0, true, null, 10000, 1000));
        if (registerServiceImpl != null) {
            onServiceAdded(registerServiceImpl);
        }
    }

    @Override // com.android.server.notification.ManagedServices
    public final boolean allowRebindForParentUser() {
        return true;
    }

    @Override // com.android.server.notification.ManagedServices
    public final IInterface asInterface(IBinder iBinder) {
        return IConditionProvider.Stub.asInterface(iBinder);
    }

    @Override // com.android.server.notification.ManagedServices
    public final void dump(PrintWriter printWriter, NotificationManagerService.DumpFilter dumpFilter) {
        int i;
        String format;
        super.dump(printWriter, dumpFilter);
        synchronized (this.mMutex) {
            try {
                printWriter.print("    mRecords(");
                printWriter.print(this.mRecords.size());
                printWriter.println("):");
                for (int i2 = 0; i2 < this.mRecords.size(); i2++) {
                    ConditionRecord conditionRecord = (ConditionRecord) this.mRecords.get(i2);
                    if (dumpFilter.matches(conditionRecord.component)) {
                        printWriter.print("      ");
                        printWriter.println(conditionRecord);
                        Uri uri = conditionRecord.id;
                        boolean z = CountdownConditionProvider.DEBUG;
                        long tryParseCountdownConditionId = ZenModeConfig.tryParseCountdownConditionId(uri);
                        if (tryParseCountdownConditionId == 0) {
                            format = null;
                        } else {
                            long currentTimeMillis = System.currentTimeMillis();
                            format = String.format("Scheduled for %s, %s in the future (%s), now=%s", SystemConditionProviderService.ts(tryParseCountdownConditionId), Long.valueOf(tryParseCountdownConditionId - currentTimeMillis), DateUtils.getRelativeTimeSpanString(tryParseCountdownConditionId, currentTimeMillis, 60000L), SystemConditionProviderService.ts(currentTimeMillis));
                        }
                        if (format != null) {
                            printWriter.print("        (");
                            printWriter.print(format);
                            printWriter.println(")");
                        }
                    }
                }
            } catch (Throwable th) {
                throw th;
            }
        }
        printWriter.print("    mSystemConditionProviders: ");
        printWriter.println(this.mSystemConditionProviderNames);
        for (i = 0; i < this.mSystemConditionProviders.size(); i++) {
            ((SystemConditionProviderService) this.mSystemConditionProviders.valueAt(i)).dump(printWriter);
        }
    }

    @Override // com.android.server.notification.ManagedServices
    public final void ensureFilters(ServiceInfo serviceInfo, int i) {
    }

    public final void ensureRecordExists(ComponentName componentName, Uri uri, IConditionProvider iConditionProvider) {
        synchronized (this.mMutex) {
            try {
                ConditionRecord recordLocked = getRecordLocked(uri, componentName, true);
                if (recordLocked.info == null) {
                    recordLocked.info = checkServiceTokenLocked(iConditionProvider);
                }
            } catch (Throwable th) {
                throw th;
            }
        }
    }

    @Override // com.android.server.notification.ManagedServices
    public final ManagedServices.Config getConfig() {
        ManagedServices.Config config = new ManagedServices.Config();
        config.caption = "condition provider";
        config.serviceInterface = "android.service.notification.ConditionProviderService";
        config.secureSettingName = null;
        config.xmlTag = TAG_ENABLED_DND_APPS;
        config.secondarySettingName = "enabled_notification_listeners";
        config.bindPermission = "android.permission.BIND_CONDITION_PROVIDER_SERVICE";
        config.settingsAction = "android.settings.ACTION_CONDITION_PROVIDER_SETTINGS";
        config.clientLabel = R.string.config_wimaxStateTrackerClassname;
        return config;
    }

    public final ConditionRecord getRecordLocked(Uri uri, ComponentName componentName, boolean z) {
        if (uri != null && componentName != null) {
            int size = this.mRecords.size();
            for (int i = 0; i < size; i++) {
                ConditionRecord conditionRecord = (ConditionRecord) this.mRecords.get(i);
                if (conditionRecord.id.equals(uri) && conditionRecord.component.equals(componentName)) {
                    return conditionRecord;
                }
            }
            if (z) {
                ConditionRecord conditionRecord2 = new ConditionRecord(componentName, uri);
                this.mRecords.add(conditionRecord2);
                return conditionRecord2;
            }
        }
        return null;
    }

    @Override // com.android.server.notification.ManagedServices
    public final String getRequiredPermission() {
        return null;
    }

    public final Condition[] getValidConditions(String str, Condition[] conditionArr) {
        if (conditionArr == null || conditionArr.length == 0) {
            return null;
        }
        int length = conditionArr.length;
        ArrayMap arrayMap = new ArrayMap(length);
        for (int i = 0; i < length; i++) {
            Condition condition = conditionArr[i];
            String str2 = this.TAG;
            if (condition == null) {
                HeimdAllFsService$$ExternalSyntheticOutline0.m("Ignoring null condition from ", str, str2);
            } else {
                Uri uri = condition.id;
                if (arrayMap.containsKey(uri)) {
                    Slog.w(str2, "Ignoring condition from " + str + " for duplicate id: " + uri);
                } else {
                    arrayMap.put(uri, conditionArr[i]);
                }
            }
        }
        if (arrayMap.size() == 0) {
            return null;
        }
        if (arrayMap.size() == length) {
            return conditionArr;
        }
        int size = arrayMap.size();
        Condition[] conditionArr2 = new Condition[size];
        for (int i2 = 0; i2 < size; i2++) {
            conditionArr2[i2] = (Condition) arrayMap.valueAt(i2);
        }
        return conditionArr2;
    }

    @Override // com.android.server.notification.ManagedServices
    public final boolean isValidEntry(int i, String str) {
        return true;
    }

    @Override // com.android.server.notification.ManagedServices
    public final void loadDefaultsFromConfig() {
        String string = this.mContext.getResources().getString(R.string.day);
        if (string != null) {
            String[] split = string.split(":");
            for (int i = 0; i < split.length; i++) {
                if (!TextUtils.isEmpty(split[i])) {
                    addDefaultComponentOrPackage(split[i]);
                }
            }
        }
    }

    @Override // com.android.server.notification.ManagedServices
    public final void onPackagesChanged(boolean z, String[] strArr, int[] iArr) {
        if (z) {
            INotificationManager service = NotificationManager.getService();
            if (strArr != null && strArr.length > 0) {
                for (String str : strArr) {
                    try {
                        service.removeAutomaticZenRules(str, false);
                        service.setNotificationPolicyAccessGranted(str, false);
                    } catch (Exception e) {
                        Slog.e(this.TAG, ConnectivityModuleConnector$$ExternalSyntheticOutline0.m("Failed to clean up rules for ", str), e);
                    }
                }
            }
        }
        super.onPackagesChanged(z, strArr, iArr);
    }

    @Override // com.android.server.notification.ManagedServices
    public final void onServiceAdded(ManagedServices.ManagedServiceInfo managedServiceInfo) {
        try {
            (managedServiceInfo == null ? null : managedServiceInfo.service).onConnected();
        } catch (RemoteException e) {
            Slog.e(this.TAG, "can't connect to service " + managedServiceInfo, e);
        }
        Callback callback = this.mCallback;
        if (callback != null) {
            ComponentName componentName = managedServiceInfo.component;
            ZenModeConditions zenModeConditions = (ZenModeConditions) callback;
            if (ZenModeConditions.DEBUG) {
                Log.d("ZenModeHelper", "onServiceAdded " + componentName);
            }
            int callingUid = Binder.getCallingUid();
            ZenModeHelper zenModeHelper = zenModeConditions.mHelper;
            ZenModeConfig config = zenModeHelper.getConfig();
            int i = callingUid == 1000 ? 5 : 4;
            String m = AmbientContextManagerPerUserService$$ExternalSyntheticOutline0.m(componentName, "zmc.onServiceAdded:");
            synchronized (zenModeHelper.mConfigLock) {
                zenModeHelper.setConfigLocked(config, i, m, componentName, true, callingUid);
            }
        }
    }

    @Override // com.android.server.notification.ManagedServices
    public final void onServiceRemovedLocked(ManagedServices.ManagedServiceInfo managedServiceInfo) {
        if (managedServiceInfo == null) {
            return;
        }
        for (int size = this.mRecords.size() - 1; size >= 0; size--) {
            if (((ConditionRecord) this.mRecords.get(size)).component.equals(managedServiceInfo.component)) {
                this.mRecords.remove(size);
            }
        }
    }

    public final void subscribeLocked(ConditionRecord conditionRecord) {
        boolean z = this.DEBUG;
        String str = this.TAG;
        if (z) {
            Slog.d(str, "subscribeLocked " + conditionRecord);
        }
        ManagedServices.ManagedServiceInfo managedServiceInfo = conditionRecord.info;
        RemoteException remoteException = null;
        IConditionProvider iConditionProvider = managedServiceInfo == null ? null : managedServiceInfo.service;
        if (iConditionProvider != null) {
            try {
                Slog.d(str, "Subscribing to " + conditionRecord.id + " with " + conditionRecord.component);
                iConditionProvider.onSubscribe(conditionRecord.id);
                conditionRecord.subscribed = true;
            } catch (RemoteException e) {
                remoteException = e;
                Slog.w(str, "Error subscribing to " + conditionRecord, remoteException);
            }
        }
        Uri uri = conditionRecord.id;
        LocalLog localLog = ZenLog.STATE_CHANGES;
        StringBuilder sb = new StringBuilder();
        sb.append(uri);
        sb.append(",");
        sb.append(iConditionProvider == null ? "no provider" : remoteException != null ? remoteException.getMessage() : "ok");
        ZenLog.append(9, sb.toString());
    }

    public final void unsubscribeIfNecessary(ComponentName componentName, Uri uri) {
        synchronized (this.mMutex) {
            try {
                ConditionRecord recordLocked = getRecordLocked(uri, componentName, false);
                if (recordLocked != null) {
                    if (recordLocked.subscribed) {
                        unsubscribeLocked(recordLocked);
                    }
                } else {
                    Slog.w(this.TAG, "Unable to unsubscribe to " + componentName + " " + uri);
                }
            } catch (Throwable th) {
                throw th;
            }
        }
    }

    public final void unsubscribeLocked(ConditionRecord conditionRecord) {
        boolean z = this.DEBUG;
        String str = this.TAG;
        if (z) {
            Slog.d(str, "unsubscribeLocked " + conditionRecord);
        }
        ManagedServices.ManagedServiceInfo managedServiceInfo = conditionRecord.info;
        RemoteException e = null;
        IConditionProvider iConditionProvider = managedServiceInfo == null ? null : managedServiceInfo.service;
        if (iConditionProvider != null) {
            try {
                iConditionProvider.onUnsubscribe(conditionRecord.id);
            } catch (RemoteException e2) {
                e = e2;
                Slog.w(str, "Error unsubscribing to " + conditionRecord, e);
            }
            conditionRecord.subscribed = false;
        }
        Uri uri = conditionRecord.id;
        LocalLog localLog = ZenLog.STATE_CHANGES;
        StringBuilder sb = new StringBuilder();
        sb.append(uri);
        sb.append(",");
        sb.append(iConditionProvider == null ? "no provider" : e != null ? e.getMessage() : "ok");
        ZenLog.append(10, sb.toString());
    }

    @Override // com.android.server.notification.ManagedServices
    public final void writeDefaults(TypedXmlSerializer typedXmlSerializer) {
        synchronized (this.mDefaultsLock) {
            typedXmlSerializer.attribute((String) null, "defaults", String.join(":", this.mDefaultPackages));
        }
    }
}
