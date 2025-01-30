package com.android.server.notification;

import android.R;
import android.app.AppOpsManager;
import android.app.AutomaticZenRule;
import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.content.pm.PackageItemInfo;
import android.content.pm.PackageManager;
import android.content.pm.ResolveInfo;
import android.content.pm.ServiceInfo;
import android.content.res.Resources;
import android.content.res.XmlResourceParser;
import android.database.ContentObserver;
import android.graphics.drawable.Icon;
import android.media.AudioAttributes;
import android.media.AudioManagerInternal;
import android.media.VolumePolicy;
import android.net.Uri;
import android.os.Binder;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.os.SystemClock;
import android.os.UserHandle;
import android.provider.Settings;
import android.service.notification.Condition;
import android.service.notification.ZenModeConfig;
import android.service.notification.ZenPolicy;
import android.util.AndroidRuntimeException;
import android.util.ArrayMap;
import android.util.Log;
import android.util.Slog;
import android.util.SparseArray;
import android.util.proto.ProtoOutputStream;
import com.android.internal.config.sysui.SystemUiSystemPropertiesFlags;
import com.android.internal.logging.MetricsLogger;
import com.android.internal.notification.SystemNotificationChannels;
import com.android.internal.util.FrameworkStatsLog;
import com.android.internal.util.XmlUtils;
import com.android.modules.utils.TypedXmlPullParser;
import com.android.modules.utils.TypedXmlSerializer;
import com.android.server.LocalServices;
import com.android.server.notification.ManagedServices;
import com.android.server.notification.ZenModeEventLogger;
import com.samsung.android.feature.SemCscFeature;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Objects;
import libcore.io.IoUtils;

/* loaded from: classes2.dex */
public class ZenModeHelper {
    public static final boolean DEBUG = Log.isLoggable("ZenModeHelper", 3);
    public final AppOpsManager mAppOps;
    protected AudioManagerInternal mAudioManager;
    protected final ZenModeConditions mConditions;
    protected ZenModeConfig mConfig;
    final SparseArray mConfigs;
    protected NotificationManager.Policy mConsolidatedPolicy;
    public final Context mContext;
    protected ZenModeConfig mDefaultConfig;
    public final ZenModeFiltering mFiltering;
    public SystemUiSystemPropertiesFlags.FlagResolver mFlagResolver;
    public final HandlerC2036H mHandler;
    protected boolean mIsBootComplete;
    public final Metrics mMetrics;
    protected final NotificationManager mNotificationManager;
    public PackageManager mPm;
    public String[] mPriorityOnlyDndExemptPackages;
    public final ManagedServices.Config mServiceConfig;
    public final SettingsObserver mSettingsObserver;
    public final SysUiStatsEvent$BuilderFactory mStatsEventBuilderFactory;
    public long mSuppressedEffects;
    public int mUser;
    protected int mZenMode;
    protected ZenModeEventLogger mZenModeEventLogger;
    public final ArrayMap mRulesUidCache = new ArrayMap();
    public final ArrayList mCallbacks = new ArrayList();
    public final RingerModeDelegate mRingerModeDelegate = new RingerModeDelegate();
    public Object mConfigsLock = new Object();

    public abstract class Callback {
        public void onAutomaticRuleStatusChanged(int i, String str, String str2, int i2) {
        }

        public abstract void onConfigChanged();

        public void onConsolidatedPolicyChanged() {
        }

        public void onPolicyChanged() {
        }

        public abstract void onZenModeChanged();
    }

    public static int zenSeverity(int i) {
        int i2 = 1;
        if (i != 1) {
            i2 = 3;
            if (i != 2) {
                return i != 3 ? 0 : 2;
            }
        }
        return i2;
    }

    public String toString() {
        return "ZenModeHelper";
    }

    /* JADX WARN: Multi-variable type inference failed */
    public ZenModeHelper(Context context, Looper looper, ConditionProviders conditionProviders, SysUiStatsEvent$BuilderFactory sysUiStatsEvent$BuilderFactory, SystemUiSystemPropertiesFlags.FlagResolver flagResolver, ZenModeEventLogger zenModeEventLogger) {
        SparseArray sparseArray = new SparseArray();
        this.mConfigs = sparseArray;
        Metrics metrics = new Metrics();
        this.mMetrics = metrics;
        this.mUser = 0;
        this.mContext = context;
        HandlerC2036H handlerC2036H = new HandlerC2036H(looper);
        this.mHandler = handlerC2036H;
        addCallback(metrics);
        this.mAppOps = (AppOpsManager) context.getSystemService(AppOpsManager.class);
        this.mNotificationManager = (NotificationManager) context.getSystemService(NotificationManager.class);
        this.mDefaultConfig = readDefaultConfig(context.getResources());
        updateDefaultAutomaticRuleNames();
        this.mConfig = this.mDefaultConfig.copy();
        synchronized (this.mConfigsLock) {
            sparseArray.put(0, this.mConfig);
        }
        this.mConsolidatedPolicy = this.mConfig.toNotificationPolicy();
        SettingsObserver settingsObserver = new SettingsObserver(handlerC2036H);
        this.mSettingsObserver = settingsObserver;
        settingsObserver.observe();
        this.mFiltering = new ZenModeFiltering(context);
        this.mConditions = new ZenModeConditions(this, conditionProviders);
        this.mServiceConfig = conditionProviders.getConfig();
        this.mStatsEventBuilderFactory = sysUiStatsEvent$BuilderFactory;
        this.mFlagResolver = flagResolver;
        this.mZenModeEventLogger = zenModeEventLogger;
    }

    public boolean matchesCallFilter(UserHandle userHandle, Bundle bundle, ValidateNotificationPeople validateNotificationPeople, int i, float f, int i2) {
        boolean matchesCallFilter;
        synchronized (this.mConfig) {
            matchesCallFilter = ZenModeFiltering.matchesCallFilter(this.mContext, this.mZenMode, this.mConsolidatedPolicy, userHandle, bundle, validateNotificationPeople, i, f, i2);
        }
        return matchesCallFilter;
    }

    public boolean isCall(NotificationRecord notificationRecord) {
        return this.mFiltering.isCall(notificationRecord);
    }

    public void recordCaller(NotificationRecord notificationRecord) {
        this.mFiltering.recordCall(notificationRecord);
    }

    public void cleanUpCallersAfter(long j) {
        this.mFiltering.cleanUpCallersAfter(j);
    }

    public boolean shouldIntercept(NotificationRecord notificationRecord) {
        boolean shouldIntercept;
        synchronized (this.mConfig) {
            shouldIntercept = this.mFiltering.shouldIntercept(this.mZenMode, this.mConsolidatedPolicy, notificationRecord);
        }
        return shouldIntercept;
    }

    public void addCallback(Callback callback) {
        this.mCallbacks.add(callback);
    }

    public void initZenMode() {
        if (DEBUG) {
            Log.d("ZenModeHelper", "initZenMode");
        }
        synchronized (this.mConfig) {
            updateConfigAndZenModeLocked(this.mConfig, "init", true, 1000, true);
        }
    }

    public void onSystemReady() {
        if (DEBUG) {
            Log.d("ZenModeHelper", "onSystemReady");
        }
        this.mAudioManager = null;
        this.mPm = this.mContext.getPackageManager();
        this.mHandler.postMetricsTimer();
        cleanUpZenRules();
        this.mIsBootComplete = true;
        showZenUpgradeNotification(this.mZenMode);
    }

    public void onUserSwitched(int i) {
        loadConfigForUser(i, "onUserSwitched");
    }

    public void onUserRemoved(int i) {
        if (i < 0) {
            return;
        }
        if (DEBUG) {
            Log.d("ZenModeHelper", "onUserRemoved u=" + i);
        }
        synchronized (this.mConfigsLock) {
            this.mConfigs.remove(i);
        }
    }

    public void onUserUnlocked(int i) {
        loadConfigForUser(i, "onUserUnlocked");
    }

    public void setPriorityOnlyDndExemptPackages(String[] strArr) {
        this.mPriorityOnlyDndExemptPackages = strArr;
    }

    public final void loadConfigForUser(int i, String str) {
        ZenModeConfig copy;
        if (this.mUser == i || i < 0) {
            return;
        }
        this.mUser = i;
        boolean z = DEBUG;
        if (z) {
            Log.d("ZenModeHelper", str + " u=" + i);
        }
        synchronized (this.mConfigsLock) {
            copy = this.mConfigs.get(i) != null ? ((ZenModeConfig) this.mConfigs.get(i)).copy() : null;
        }
        if (copy == null) {
            if (z) {
                Log.d("ZenModeHelper", str + " generating default config for user " + i);
            }
            copy = this.mDefaultConfig.copy();
            copy.user = i;
        }
        ZenModeConfig zenModeConfig = copy;
        synchronized (this.mConfig) {
            setConfigLocked(zenModeConfig, null, str, 1000, true);
        }
        cleanUpZenRules();
    }

    public int getZenModeListenerInterruptionFilter() {
        return NotificationManager.zenModeToInterruptionFilter(this.mZenMode);
    }

    public void requestFromListener(ComponentName componentName, int i, int i2, boolean z) {
        int zenModeFromInterruptionFilter = NotificationManager.zenModeFromInterruptionFilter(i, -1);
        if (zenModeFromInterruptionFilter != -1) {
            String packageName = componentName != null ? componentName.getPackageName() : null;
            StringBuilder sb = new StringBuilder();
            sb.append("listener:");
            sb.append(componentName != null ? componentName.flattenToShortString() : null);
            setManualZenMode(zenModeFromInterruptionFilter, null, packageName, sb.toString(), i2, z);
        }
    }

    public void setSuppressedEffects(long j) {
        if (this.mSuppressedEffects == j) {
            return;
        }
        this.mSuppressedEffects = j;
        applyRestrictions();
    }

    public long getSuppressedEffects() {
        return this.mSuppressedEffects;
    }

    public int getZenMode() {
        return this.mZenMode;
    }

    public List getZenRules() {
        ArrayList arrayList = new ArrayList();
        synchronized (this.mConfig) {
            ZenModeConfig zenModeConfig = this.mConfig;
            if (zenModeConfig == null) {
                return arrayList;
            }
            for (ZenModeConfig.ZenRule zenRule : zenModeConfig.automaticRules.values()) {
                if (canManageAutomaticZenRule(zenRule)) {
                    arrayList.add(zenRule);
                }
            }
            return arrayList;
        }
    }

    public AutomaticZenRule getAutomaticZenRule(String str) {
        synchronized (this.mConfig) {
            ZenModeConfig zenModeConfig = this.mConfig;
            if (zenModeConfig == null) {
                return null;
            }
            ZenModeConfig.ZenRule zenRule = (ZenModeConfig.ZenRule) zenModeConfig.automaticRules.get(str);
            if (zenRule != null && canManageAutomaticZenRule(zenRule)) {
                return createAutomaticZenRule(zenRule);
            }
            return null;
        }
    }

    public String addAutomaticZenRule(String str, AutomaticZenRule automaticZenRule, String str2, int i, boolean z) {
        String str3;
        if (!"android".equals(str)) {
            PackageItemInfo serviceInfo = getServiceInfo(automaticZenRule.getOwner());
            if (serviceInfo == null) {
                serviceInfo = getActivityInfo(automaticZenRule.getConfigurationActivity());
            }
            if (serviceInfo == null) {
                throw new IllegalArgumentException("Lacking enabled CPS or config activity");
            }
            Bundle bundle = serviceInfo.metaData;
            int i2 = bundle != null ? bundle.getInt("android.service.zen.automatic.ruleInstanceLimit", -1) : -1;
            int currentInstanceCount = getCurrentInstanceCount(automaticZenRule.getOwner()) + getCurrentInstanceCount(automaticZenRule.getConfigurationActivity()) + 1;
            if (getPackageRuleCount(str) + 1 > 100 || (i2 > 0 && i2 < currentInstanceCount)) {
                throw new IllegalArgumentException("Rule instance limit exceeded");
            }
        }
        synchronized (this.mConfig) {
            if (this.mConfig == null) {
                throw new AndroidRuntimeException("Could not create rule");
            }
            if (DEBUG) {
                Log.d("ZenModeHelper", "addAutomaticZenRule rule= " + automaticZenRule + " reason=" + str2);
            }
            ZenModeConfig copy = this.mConfig.copy();
            ZenModeConfig.ZenRule zenRule = new ZenModeConfig.ZenRule();
            populateZenRule(str, automaticZenRule, zenRule, true);
            copy.automaticRules.put(zenRule.id, zenRule);
            if (setConfigLocked(copy, str2, zenRule.component, true, i, z)) {
                str3 = zenRule.id;
            } else {
                throw new AndroidRuntimeException("Could not create rule");
            }
        }
        return str3;
    }

    public boolean updateAutomaticZenRule(String str, AutomaticZenRule automaticZenRule, String str2, int i, boolean z) {
        synchronized (this.mConfig) {
            if (this.mConfig == null) {
                return false;
            }
            if (DEBUG) {
                Log.d("ZenModeHelper", "updateAutomaticZenRule zenRule=" + automaticZenRule + " reason=" + str2);
            }
            ZenModeConfig copy = this.mConfig.copy();
            if (str == null) {
                throw new IllegalArgumentException("Rule doesn't exist");
            }
            ZenModeConfig.ZenRule zenRule = (ZenModeConfig.ZenRule) copy.automaticRules.get(str);
            if (zenRule == null || !canManageAutomaticZenRule(zenRule)) {
                throw new SecurityException("Cannot update rules not owned by your condition provider");
            }
            if (zenRule.enabled != automaticZenRule.isEnabled()) {
                dispatchOnAutomaticRuleStatusChanged(this.mConfig.user, zenRule.getPkg(), str, automaticZenRule.isEnabled() ? 1 : 2);
            }
            populateZenRule(zenRule.pkg, automaticZenRule, zenRule, false);
            return setConfigLocked(copy, str2, zenRule.component, true, i, z);
        }
    }

    public boolean removeAutomaticZenRule(String str, String str2, int i, boolean z) {
        synchronized (this.mConfig) {
            ZenModeConfig zenModeConfig = this.mConfig;
            if (zenModeConfig == null) {
                return false;
            }
            ZenModeConfig copy = zenModeConfig.copy();
            ZenModeConfig.ZenRule zenRule = (ZenModeConfig.ZenRule) copy.automaticRules.get(str);
            if (zenRule == null) {
                return false;
            }
            if (canManageAutomaticZenRule(zenRule)) {
                copy.automaticRules.remove(str);
                if (zenRule.getPkg() != null && !"android".equals(zenRule.getPkg())) {
                    for (ZenModeConfig.ZenRule zenRule2 : copy.automaticRules.values()) {
                        if (zenRule2.getPkg() != null && zenRule2.getPkg().equals(zenRule.getPkg())) {
                            break;
                        }
                    }
                    this.mRulesUidCache.remove(getPackageUserKey(zenRule.getPkg(), copy.user));
                }
                if (DEBUG) {
                    Log.d("ZenModeHelper", "removeZenRule zenRule=" + str + " reason=" + str2);
                }
                dispatchOnAutomaticRuleStatusChanged(this.mConfig.user, zenRule.getPkg(), str, 3);
                return setConfigLocked(copy, str2, null, true, i, z);
            }
            throw new SecurityException("Cannot delete rules not owned by your condition provider");
        }
    }

    public boolean removeAutomaticZenRules(String str, String str2, int i, boolean z) {
        synchronized (this.mConfig) {
            ZenModeConfig zenModeConfig = this.mConfig;
            if (zenModeConfig == null) {
                return false;
            }
            ZenModeConfig copy = zenModeConfig.copy();
            for (int size = copy.automaticRules.size() - 1; size >= 0; size--) {
                ArrayMap arrayMap = copy.automaticRules;
                ZenModeConfig.ZenRule zenRule = (ZenModeConfig.ZenRule) arrayMap.get(arrayMap.keyAt(size));
                if (Objects.equals(zenRule.getPkg(), str) && canManageAutomaticZenRule(zenRule)) {
                    copy.automaticRules.removeAt(size);
                }
            }
            return setConfigLocked(copy, str2, null, true, i, z);
        }
    }

    public void setAutomaticZenRuleState(String str, Condition condition, int i, boolean z) {
        synchronized (this.mConfig) {
            ZenModeConfig zenModeConfig = this.mConfig;
            if (zenModeConfig == null) {
                return;
            }
            ZenModeConfig copy = zenModeConfig.copy();
            ArrayList arrayList = new ArrayList();
            arrayList.add((ZenModeConfig.ZenRule) copy.automaticRules.get(str));
            setAutomaticZenRuleStateLocked(copy, arrayList, condition, i, z);
        }
    }

    public void setAutomaticZenRuleState(Uri uri, Condition condition, int i, boolean z) {
        synchronized (this.mConfig) {
            ZenModeConfig zenModeConfig = this.mConfig;
            if (zenModeConfig == null) {
                return;
            }
            ZenModeConfig copy = zenModeConfig.copy();
            setAutomaticZenRuleStateLocked(copy, findMatchingRules(copy, uri, condition), condition, i, z);
        }
    }

    public final void setAutomaticZenRuleStateLocked(ZenModeConfig zenModeConfig, List list, Condition condition, int i, boolean z) {
        if (list == null || list.isEmpty()) {
            return;
        }
        Iterator it = list.iterator();
        while (it.hasNext()) {
            ZenModeConfig.ZenRule zenRule = (ZenModeConfig.ZenRule) it.next();
            zenRule.condition = condition;
            updateSnoozing(zenRule);
            setConfigLocked(zenModeConfig, zenRule.component, "conditionChanged", i, z);
        }
    }

    public final List findMatchingRules(ZenModeConfig zenModeConfig, Uri uri, Condition condition) {
        ArrayList arrayList = new ArrayList();
        if (ruleMatches(uri, condition, zenModeConfig.manualRule)) {
            arrayList.add(zenModeConfig.manualRule);
        } else {
            for (ZenModeConfig.ZenRule zenRule : zenModeConfig.automaticRules.values()) {
                if (ruleMatches(uri, condition, zenRule)) {
                    arrayList.add(zenRule);
                }
            }
        }
        return arrayList;
    }

    public final boolean ruleMatches(Uri uri, Condition condition, ZenModeConfig.ZenRule zenRule) {
        Uri uri2;
        return (uri == null || zenRule == null || (uri2 = zenRule.conditionId) == null || !uri2.equals(uri) || Objects.equals(condition, zenRule.condition)) ? false : true;
    }

    public final boolean updateSnoozing(ZenModeConfig.ZenRule zenRule) {
        if (zenRule == null || !zenRule.snoozing || zenRule.isTrueOrUnknown()) {
            return false;
        }
        zenRule.snoozing = false;
        if (DEBUG) {
            Log.d("ZenModeHelper", "Snoozing reset for " + zenRule.conditionId);
        }
        return true;
    }

    public int getCurrentInstanceCount(ComponentName componentName) {
        int i = 0;
        if (componentName == null) {
            return 0;
        }
        synchronized (this.mConfig) {
            for (ZenModeConfig.ZenRule zenRule : this.mConfig.automaticRules.values()) {
                if (componentName.equals(zenRule.component) || componentName.equals(zenRule.configurationActivity)) {
                    i++;
                }
            }
        }
        return i;
    }

    public final int getPackageRuleCount(String str) {
        int i = 0;
        if (str == null) {
            return 0;
        }
        synchronized (this.mConfig) {
            Iterator it = this.mConfig.automaticRules.values().iterator();
            while (it.hasNext()) {
                if (str.equals(((ZenModeConfig.ZenRule) it.next()).getPkg())) {
                    i++;
                }
            }
        }
        return i;
    }

    public boolean canManageAutomaticZenRule(ZenModeConfig.ZenRule zenRule) {
        int callingUid = Binder.getCallingUid();
        if (callingUid == 0 || callingUid == 1000 || this.mContext.checkCallingPermission("android.permission.MANAGE_NOTIFICATIONS") == 0) {
            return true;
        }
        String[] packagesForUid = this.mPm.getPackagesForUid(Binder.getCallingUid());
        if (packagesForUid != null) {
            for (String str : packagesForUid) {
                if (str.equals(zenRule.getPkg())) {
                    return true;
                }
            }
        }
        return false;
    }

    public void updateDefaultZenRules(int i, boolean z) {
        updateDefaultAutomaticRuleNames();
        for (ZenModeConfig.ZenRule zenRule : this.mDefaultConfig.automaticRules.values()) {
            ZenModeConfig.ZenRule zenRule2 = (ZenModeConfig.ZenRule) this.mConfig.automaticRules.get(zenRule.id);
            if (zenRule2 != null && !zenRule2.modified && !zenRule2.enabled && !zenRule.name.equals(zenRule2.name) && canManageAutomaticZenRule(zenRule2)) {
                if (DEBUG) {
                    Slog.d("ZenModeHelper", "Locale change - updating default zen rule name from " + zenRule2.name + " to " + zenRule.name);
                }
                zenRule2.name = zenRule.name;
                updateAutomaticZenRule(zenRule.id, createAutomaticZenRule(zenRule2), "locale changed", i, z);
            }
        }
    }

    public final ServiceInfo getServiceInfo(ComponentName componentName) {
        Intent intent = new Intent();
        intent.setComponent(componentName);
        List queryIntentServicesAsUser = this.mPm.queryIntentServicesAsUser(intent, 132, UserHandle.getCallingUserId());
        if (queryIntentServicesAsUser == null) {
            return null;
        }
        int size = queryIntentServicesAsUser.size();
        for (int i = 0; i < size; i++) {
            ServiceInfo serviceInfo = ((ResolveInfo) queryIntentServicesAsUser.get(i)).serviceInfo;
            if (this.mServiceConfig.bindPermission.equals(serviceInfo.permission)) {
                return serviceInfo;
            }
        }
        return null;
    }

    public final ActivityInfo getActivityInfo(ComponentName componentName) {
        Intent intent = new Intent();
        intent.setComponent(componentName);
        List queryIntentActivitiesAsUser = this.mPm.queryIntentActivitiesAsUser(intent, 129, UserHandle.getCallingUserId());
        if (queryIntentActivitiesAsUser == null || queryIntentActivitiesAsUser.size() <= 0) {
            return null;
        }
        return ((ResolveInfo) queryIntentActivitiesAsUser.get(0)).activityInfo;
    }

    public final void populateZenRule(String str, AutomaticZenRule automaticZenRule, ZenModeConfig.ZenRule zenRule, boolean z) {
        if (z) {
            zenRule.id = ZenModeConfig.newRuleId();
            zenRule.creationTime = System.currentTimeMillis();
            zenRule.component = automaticZenRule.getOwner();
            zenRule.pkg = str;
        }
        if (zenRule.enabled != automaticZenRule.isEnabled()) {
            zenRule.snoozing = false;
        }
        zenRule.name = automaticZenRule.getName();
        zenRule.condition = null;
        zenRule.conditionId = automaticZenRule.getConditionId();
        zenRule.enabled = automaticZenRule.isEnabled();
        zenRule.modified = automaticZenRule.isModified();
        zenRule.zenPolicy = automaticZenRule.getZenPolicy();
        zenRule.zenMode = NotificationManager.zenModeFromInterruptionFilter(automaticZenRule.getInterruptionFilter(), 0);
        zenRule.configurationActivity = automaticZenRule.getConfigurationActivity();
    }

    public AutomaticZenRule createAutomaticZenRule(ZenModeConfig.ZenRule zenRule) {
        AutomaticZenRule automaticZenRule = new AutomaticZenRule(zenRule.name, zenRule.component, zenRule.configurationActivity, zenRule.conditionId, zenRule.zenPolicy, NotificationManager.zenModeToInterruptionFilter(zenRule.zenMode), zenRule.enabled, zenRule.creationTime);
        automaticZenRule.setPackageName(zenRule.pkg);
        return automaticZenRule;
    }

    public void setManualZenMode(int i, Uri uri, String str, String str2, int i2, boolean z) {
        setManualZenMode(i, uri, str2, str, true, i2, z);
        Settings.Secure.putInt(this.mContext.getContentResolver(), "show_zen_settings_suggestion", 0);
    }

    public final void setManualZenMode(int i, Uri uri, String str, String str2, boolean z, int i2, boolean z2) {
        synchronized (this.mConfig) {
            if (this.mConfig == null) {
                return;
            }
            if (Settings.Global.isValidZenMode(i)) {
                if (DEBUG) {
                    Log.d("ZenModeHelper", "setManualZenMode " + Settings.Global.zenModeToString(i) + " conditionId=" + uri + " reason=" + str + " setRingerMode=" + z);
                }
                ZenModeConfig copy = this.mConfig.copy();
                if (i == 0) {
                    copy.manualRule = null;
                    for (ZenModeConfig.ZenRule zenRule : copy.automaticRules.values()) {
                        if (zenRule.isAutomaticActive()) {
                            zenRule.snoozing = true;
                        }
                    }
                } else {
                    ZenModeConfig.ZenRule zenRule2 = new ZenModeConfig.ZenRule();
                    zenRule2.enabled = true;
                    zenRule2.zenMode = i;
                    zenRule2.conditionId = uri;
                    zenRule2.enabler = str2;
                    copy.manualRule = zenRule2;
                }
                setConfigLocked(copy, str, null, z, i2, z2);
            }
        }
    }

    public void dump(ProtoOutputStream protoOutputStream) {
        Condition condition;
        protoOutputStream.write(1159641169921L, this.mZenMode);
        synchronized (this.mConfig) {
            ZenModeConfig.ZenRule zenRule = this.mConfig.manualRule;
            if (zenRule != null) {
                zenRule.dumpDebug(protoOutputStream, 2246267895810L);
            }
            for (ZenModeConfig.ZenRule zenRule2 : this.mConfig.automaticRules.values()) {
                if (zenRule2.enabled && (condition = zenRule2.condition) != null && condition.state == 1 && !zenRule2.snoozing) {
                    zenRule2.dumpDebug(protoOutputStream, 2246267895810L);
                }
            }
            this.mConfig.toNotificationPolicy().dumpDebug(protoOutputStream, 1146756268037L);
            protoOutputStream.write(1120986464259L, this.mSuppressedEffects);
        }
    }

    public void dump(PrintWriter printWriter, String str) {
        int i;
        printWriter.print(str);
        printWriter.print("mZenMode=");
        printWriter.println(Settings.Global.zenModeToString(this.mZenMode));
        printWriter.print(str);
        printWriter.println("mConsolidatedPolicy=" + this.mConsolidatedPolicy.toString());
        synchronized (this.mConfigsLock) {
            int size = this.mConfigs.size();
            for (int i2 = 0; i2 < size; i2++) {
                dump(printWriter, str, "mConfigs[u=" + this.mConfigs.keyAt(i2) + "]", (ZenModeConfig) this.mConfigs.valueAt(i2));
            }
        }
        printWriter.print(str);
        printWriter.print("mUser=");
        printWriter.println(this.mUser);
        synchronized (this.mConfig) {
            dump(printWriter, str, "mConfig", this.mConfig);
        }
        printWriter.print(str);
        printWriter.print("mSuppressedEffects=");
        printWriter.println(this.mSuppressedEffects);
        printWriter.print(str);
        printWriter.print("mPriorityOnlyDndExemptPackages=");
        printWriter.println("");
        for (i = 0; i < this.mPriorityOnlyDndExemptPackages.length; i++) {
            printWriter.print(str);
            printWriter.print("        ");
            printWriter.println(this.mPriorityOnlyDndExemptPackages[i]);
        }
        this.mFiltering.dump(printWriter, str);
        this.mConditions.dump(printWriter, str);
    }

    public static void dump(PrintWriter printWriter, String str, String str2, ZenModeConfig zenModeConfig) {
        printWriter.print(str);
        printWriter.print(str2);
        printWriter.print('=');
        if (zenModeConfig == null) {
            printWriter.println(zenModeConfig);
            return;
        }
        printWriter.printf("allow(alarms=%b,media=%b,system=%b,calls=%b,callsFrom=%s,repeatCallers=%b,messages=%b,messagesFrom=%s,conversations=%b,conversationsFrom=%s,events=%b,reminders=%bexceptionContacts=%s,appBypassDndList=%s)\n", Boolean.valueOf(zenModeConfig.allowAlarms), Boolean.valueOf(zenModeConfig.allowMedia), Boolean.valueOf(zenModeConfig.allowSystem), Boolean.valueOf(zenModeConfig.allowCalls), ZenModeConfig.sourceToString(zenModeConfig.allowCallsFrom), Boolean.valueOf(zenModeConfig.allowRepeatCallers), Boolean.valueOf(zenModeConfig.allowMessages), ZenModeConfig.sourceToString(zenModeConfig.allowMessagesFrom), Boolean.valueOf(zenModeConfig.allowConversations), ZenPolicy.conversationTypeToString(zenModeConfig.allowConversationsFrom), Boolean.valueOf(zenModeConfig.allowEvents), Boolean.valueOf(zenModeConfig.allowReminders), ZenModeConfig.joinStrings(",", zenModeConfig.allowExceptionContacts), ZenModeConfig.joinStrings(",", zenModeConfig.allowAppBypassDndList));
        printWriter.print(str);
        printWriter.printf("  disallow(visualEffects=%s)\n", Integer.valueOf(zenModeConfig.suppressedVisualEffects));
        printWriter.print(str);
        printWriter.print("  manualRule=");
        printWriter.println(zenModeConfig.manualRule);
        if (zenModeConfig.automaticRules.isEmpty()) {
            return;
        }
        int size = zenModeConfig.automaticRules.size();
        int i = 0;
        while (i < size) {
            printWriter.print(str);
            printWriter.print(i == 0 ? "  automaticRules=" : "                 ");
            printWriter.println(zenModeConfig.automaticRules.valueAt(i));
            i++;
        }
    }

    public void readXml(TypedXmlPullParser typedXmlPullParser, boolean z, int i) {
        boolean z2;
        ZenModeConfig readXml = ZenModeConfig.readXml(typedXmlPullParser);
        String str = "readXml";
        if (readXml != null) {
            if (z) {
                readXml.user = i;
                readXml.manualRule = null;
            }
            if (Settings.Secure.getInt(this.mContext.getContentResolver(), "fixed_delete_mode_rule", 0) == 0) {
                Log.d("ZenModeHelper", "need Delete ModeRule");
                for (int size = readXml.automaticRules.size() - 1; size >= 0; size--) {
                    ZenModeConfig.ZenRule zenRule = (ZenModeConfig.ZenRule) readXml.automaticRules.valueAt(size);
                    if ("com.android.settings".equals(zenRule.pkg) && !ZenModeConfig.isValidScheduleConditionId(zenRule.conditionId)) {
                        Log.d("ZenModeHelper", "removeAt!! rule id = " + zenRule.id);
                        readXml.automaticRules.removeAt(size);
                    }
                }
                Settings.Secure.putInt(this.mContext.getContentResolver(), "fixed_delete_mode_rule", 1);
            }
            for (int size2 = readXml.automaticRules.size() - 1; size2 >= 0; size2--) {
                ZenModeConfig.ZenRule zenRule2 = (ZenModeConfig.ZenRule) readXml.automaticRules.valueAt(size2);
                if ("SCHEDULED_DEFAULT_RULE".equals(zenRule2.id) && !zenRule2.modified) {
                    zenRule2.name = this.mContext.getResources().getString(17043428);
                    Log.d("ZenModeHelper", "change default rule name= " + readXml.automaticRules.valueAt(size2));
                } else if ("EVENTS_DEFAULT_RULE".equals(zenRule2.id)) {
                    Log.d("ZenModeHelper", "removeAt!! rule id = " + zenRule2.id);
                    readXml.automaticRules.removeAt(size2);
                }
            }
            boolean containsAll = readXml.automaticRules.containsAll(ZenModeConfig.DEFAULT_RULE_IDS);
            long currentTimeMillis = System.currentTimeMillis();
            ArrayMap arrayMap = readXml.automaticRules;
            if (arrayMap == null || arrayMap.size() <= 0) {
                z2 = true;
            } else {
                z2 = true;
                for (ZenModeConfig.ZenRule zenRule3 : readXml.automaticRules.values()) {
                    if (z) {
                        zenRule3.snoozing = false;
                        zenRule3.condition = null;
                        zenRule3.creationTime = currentTimeMillis;
                    }
                    z2 &= !zenRule3.enabled;
                }
            }
            if (!containsAll && z2 && (z || readXml.version < 8)) {
                readXml.automaticRules = new ArrayMap();
                for (ZenModeConfig.ZenRule zenRule4 : this.mDefaultConfig.automaticRules.values()) {
                    readXml.automaticRules.put(zenRule4.id, zenRule4);
                }
                str = "readXml, reset to default rules";
            }
            if (i == -1) {
                i = 0;
            }
            if (readXml.version < 8) {
                Settings.Secure.putIntForUser(this.mContext.getContentResolver(), "show_zen_upgrade_notification", 1, i);
            } else {
                Settings.Secure.putIntForUser(this.mContext.getContentResolver(), "zen_settings_updated", 1, i);
            }
            if (DEBUG) {
                Log.d("ZenModeHelper", str);
            }
            synchronized (this.mConfig) {
                setConfigLocked(readXml, null, str, 1000, true);
            }
        }
    }

    public void writeXml(TypedXmlSerializer typedXmlSerializer, boolean z, Integer num, int i) {
        synchronized (this.mConfigsLock) {
            int size = this.mConfigs.size();
            for (int i2 = 0; i2 < size; i2++) {
                if (!z || this.mConfigs.keyAt(i2) == i) {
                    ((ZenModeConfig) this.mConfigs.valueAt(i2)).writeXml(typedXmlSerializer, num);
                }
            }
        }
    }

    public NotificationManager.Policy getNotificationPolicy() {
        return getNotificationPolicy(this.mConfig);
    }

    public static NotificationManager.Policy getNotificationPolicy(ZenModeConfig zenModeConfig) {
        if (zenModeConfig == null) {
            return null;
        }
        return zenModeConfig.toNotificationPolicy();
    }

    public void setNotificationPolicy(NotificationManager.Policy policy, int i, boolean z) {
        ZenModeConfig zenModeConfig;
        if (policy == null || (zenModeConfig = this.mConfig) == null) {
            return;
        }
        synchronized (zenModeConfig) {
            ZenModeConfig copy = this.mConfig.copy();
            copy.applyNotificationPolicy(policy);
            setConfigLocked(copy, null, "setNotificationPolicy", i, z);
        }
    }

    public final void cleanUpZenRules() {
        long currentTimeMillis = System.currentTimeMillis();
        synchronized (this.mConfig) {
            ZenModeConfig copy = this.mConfig.copy();
            ArrayMap arrayMap = copy.automaticRules;
            if (arrayMap != null) {
                for (int size = arrayMap.size() - 1; size >= 0; size--) {
                    ArrayMap arrayMap2 = copy.automaticRules;
                    ZenModeConfig.ZenRule zenRule = (ZenModeConfig.ZenRule) arrayMap2.get(arrayMap2.keyAt(size));
                    if (259200000 < currentTimeMillis - zenRule.creationTime) {
                        try {
                            if (zenRule.getPkg() != null) {
                                this.mPm.getPackageInfo(zenRule.getPkg(), 4194304);
                            }
                        } catch (PackageManager.NameNotFoundException unused) {
                            copy.automaticRules.removeAt(size);
                        }
                    }
                }
            }
            setConfigLocked(copy, null, "cleanUpZenRules", 1000, true);
        }
    }

    public ZenModeConfig getConfig() {
        ZenModeConfig copy;
        synchronized (this.mConfig) {
            copy = this.mConfig.copy();
        }
        return copy;
    }

    public NotificationManager.Policy getConsolidatedNotificationPolicy() {
        return this.mConsolidatedPolicy.copy();
    }

    public boolean setConfigLocked(ZenModeConfig zenModeConfig, ComponentName componentName, String str, int i, boolean z) {
        return setConfigLocked(zenModeConfig, str, componentName, true, i, z);
    }

    public void setConfig(ZenModeConfig zenModeConfig, ComponentName componentName, String str, int i, boolean z) {
        synchronized (this.mConfig) {
            setConfigLocked(zenModeConfig, componentName, str, i, z);
        }
    }

    public final boolean setConfigLocked(ZenModeConfig zenModeConfig, String str, ComponentName componentName, boolean z, int i, boolean z2) {
        long clearCallingIdentity = Binder.clearCallingIdentity();
        try {
            if (zenModeConfig != null) {
                if (zenModeConfig.isValid()) {
                    if (zenModeConfig.user != this.mUser) {
                        synchronized (this.mConfigsLock) {
                            this.mConfigs.put(zenModeConfig.user, zenModeConfig);
                        }
                        if (DEBUG) {
                            Log.d("ZenModeHelper", "setConfigLocked: store config for user " + zenModeConfig.user);
                        }
                        return true;
                    }
                    this.mConditions.evaluateConfig(zenModeConfig, null, false);
                    synchronized (this.mConfigsLock) {
                        this.mConfigs.put(zenModeConfig.user, zenModeConfig);
                    }
                    if (DEBUG) {
                        Log.d("ZenModeHelper", "setConfigLocked reason=" + str, new Throwable());
                    }
                    ZenLog.traceConfig(str, this.mConfig, zenModeConfig);
                    boolean z3 = !Objects.equals(getNotificationPolicy(this.mConfig), getNotificationPolicy(zenModeConfig));
                    updateConfigAndZenModeLocked(zenModeConfig, str, z, i, z2);
                    if (z3) {
                        dispatchOnPolicyChanged();
                    }
                    this.mConditions.evaluateConfig(zenModeConfig, componentName, true);
                    return true;
                }
            }
            Log.w("ZenModeHelper", "Invalid config in setConfigLocked; " + zenModeConfig);
            return false;
        } catch (SecurityException e) {
            Log.wtf("ZenModeHelper", "Invalid rule in config", e);
            return false;
        } finally {
            Binder.restoreCallingIdentity(clearCallingIdentity);
        }
    }

    public final void updateConfigAndZenModeLocked(ZenModeConfig zenModeConfig, String str, boolean z, int i, boolean z2) {
        boolean isEnabled = this.mFlagResolver.isEnabled(SystemUiSystemPropertiesFlags.NotificationFlags.LOG_DND_STATE_EVENTS);
        ZenModeEventLogger.ZenModeInfo zenModeInfo = new ZenModeEventLogger.ZenModeInfo(this.mZenMode, this.mConfig, this.mConsolidatedPolicy);
        if (!zenModeConfig.equals(this.mConfig)) {
            this.mConfig = zenModeConfig;
            dispatchOnConfigChanged();
            updateConsolidatedPolicy(str);
        }
        Settings.Global.putString(this.mContext.getContentResolver(), "zen_mode_config_etag", Integer.toString(zenModeConfig.hashCode()));
        evaluateZenMode(str, z);
        if (isEnabled) {
            this.mZenModeEventLogger.maybeLogZenChange(zenModeInfo, new ZenModeEventLogger.ZenModeInfo(this.mZenMode, this.mConfig, this.mConsolidatedPolicy), i, z2);
        }
    }

    public final int getZenModeSetting() {
        return Settings.Global.getInt(this.mContext.getContentResolver(), "zen_mode", 0);
    }

    public void setZenModeSetting(int i) {
        Settings.Global.putInt(this.mContext.getContentResolver(), "zen_mode", i);
        showZenUpgradeNotification(i);
    }

    public final int getPreviousRingerModeSetting() {
        return Settings.Global.getInt(this.mContext.getContentResolver(), "zen_mode_ringer_level", 2);
    }

    public final void setPreviousRingerModeSetting(Integer num) {
        Settings.Global.putString(this.mContext.getContentResolver(), "zen_mode_ringer_level", num == null ? null : Integer.toString(num.intValue()));
    }

    public void evaluateZenMode(String str, boolean z) {
        if (DEBUG) {
            Log.d("ZenModeHelper", "evaluateZenMode");
        }
        if (this.mConfig == null) {
            return;
        }
        NotificationManager.Policy policy = this.mConsolidatedPolicy;
        boolean z2 = false;
        int hashCode = policy == null ? 0 : policy.hashCode();
        int i = this.mZenMode;
        int computeZenMode = computeZenMode();
        ZenLog.traceSetZenMode(computeZenMode, str);
        this.mZenMode = computeZenMode;
        setZenModeSetting(computeZenMode);
        updateConsolidatedPolicy(str);
        if (z && (computeZenMode != i || (computeZenMode == 1 && hashCode != this.mConsolidatedPolicy.hashCode()))) {
            z2 = true;
        }
        this.mHandler.postUpdateRingerAndAudio(z2);
        if (computeZenMode != i) {
            this.mHandler.postDispatchOnZenModeChanged();
        }
    }

    public final void updateRingerAndAudio(boolean z) {
        AudioManagerInternal audioManagerInternal = (AudioManagerInternal) LocalServices.getService(AudioManagerInternal.class);
        if (audioManagerInternal != null) {
            audioManagerInternal.updateRingerModeAffectedStreamsInternal();
        }
        if (z) {
            applyZenToRingerMode();
        }
        applyRestrictions();
    }

    public final int computeZenMode() {
        ZenModeConfig zenModeConfig = this.mConfig;
        int i = 0;
        if (zenModeConfig == null) {
            return 0;
        }
        synchronized (zenModeConfig) {
            ZenModeConfig zenModeConfig2 = this.mConfig;
            ZenModeConfig.ZenRule zenRule = zenModeConfig2.manualRule;
            if (zenRule != null) {
                return zenRule.zenMode;
            }
            for (ZenModeConfig.ZenRule zenRule2 : zenModeConfig2.automaticRules.values()) {
                if (zenRule2.isAutomaticActive() && zenSeverity(zenRule2.zenMode) > zenSeverity(i)) {
                    if (Settings.Secure.getInt(this.mContext.getContentResolver(), "zen_settings_suggestion_viewed", 1) == 0) {
                        Settings.Secure.putInt(this.mContext.getContentResolver(), "show_zen_settings_suggestion", 1);
                    }
                    i = zenRule2.zenMode;
                }
            }
            return i;
        }
    }

    public final void applyCustomPolicy(ZenPolicy zenPolicy, ZenModeConfig.ZenRule zenRule) {
        int i = zenRule.zenMode;
        if (i == 2) {
            zenPolicy.apply(new ZenPolicy.Builder().disallowAllSounds().build());
        } else if (i == 3) {
            zenPolicy.apply(new ZenPolicy.Builder().disallowAllSounds().allowAlarms(true).allowMedia(true).build());
        } else {
            zenPolicy.apply(zenRule.zenPolicy);
        }
    }

    public final void updateConsolidatedPolicy(String str) {
        ZenModeConfig zenModeConfig = this.mConfig;
        if (zenModeConfig == null) {
            return;
        }
        synchronized (zenModeConfig) {
            ZenPolicy zenPolicy = new ZenPolicy();
            ZenModeConfig.ZenRule zenRule = this.mConfig.manualRule;
            if (zenRule != null) {
                applyCustomPolicy(zenPolicy, zenRule);
            }
            for (ZenModeConfig.ZenRule zenRule2 : this.mConfig.automaticRules.values()) {
                if (zenRule2.isAutomaticActive()) {
                    applyCustomPolicy(zenPolicy, zenRule2);
                }
            }
            NotificationManager.Policy notificationPolicy = this.mConfig.toNotificationPolicy(zenPolicy);
            if (!Objects.equals(this.mConsolidatedPolicy, notificationPolicy)) {
                this.mConsolidatedPolicy = notificationPolicy;
                dispatchOnConsolidatedPolicyChanged();
                ZenLog.traceSetConsolidatedZenPolicy(this.mConsolidatedPolicy, str);
            }
        }
    }

    public ArrayList getAppsToBypassDndForEnabledLifeStyle() {
        ZenPolicy zenPolicy;
        ArrayList arrayList = null;
        for (ZenModeConfig.ZenRule zenRule : this.mConfig.automaticRules.values()) {
            if (zenRule.isAutomaticActive() && zenRule.pkg.equals("com.android.settings") && (zenPolicy = zenRule.zenPolicy) != null) {
                arrayList = zenPolicy.getAppsToBypassDnd();
                if (DEBUG) {
                    Log.d("ZenModeHelper", "Lifestyle apps=" + arrayList.toString());
                }
            }
        }
        return arrayList;
    }

    public final void updateDefaultAutomaticRuleNames() {
        for (ZenModeConfig.ZenRule zenRule : this.mDefaultConfig.automaticRules.values()) {
            if ("EVENTS_DEFAULT_RULE".equals(zenRule.id)) {
                zenRule.name = this.mContext.getResources().getString(17043427);
            } else if ("EVERY_NIGHT_DEFAULT_RULE".equals(zenRule.id)) {
                zenRule.name = this.mContext.getResources().getString(17043428);
            }
        }
    }

    public void applyRestrictions() {
        int i = this.mZenMode;
        boolean z = true;
        boolean z2 = i == 1;
        boolean z3 = i == 2;
        boolean z4 = i == 3;
        boolean z5 = this.mConsolidatedPolicy.allowCalls() || this.mConsolidatedPolicy.getExceptionContacts().size() > 0;
        boolean allowRepeatCallers = this.mConsolidatedPolicy.allowRepeatCallers();
        boolean allowSystem = this.mConsolidatedPolicy.allowSystem();
        boolean allowMedia = this.mConsolidatedPolicy.allowMedia();
        boolean allowAlarms = this.mConsolidatedPolicy.allowAlarms();
        long j = this.mSuppressedEffects;
        boolean z6 = (1 & j) != 0;
        boolean z7 = z4 || !((!z2 || z5 || allowRepeatCallers) && (2 & j) == 0);
        boolean z8 = z2 && !allowAlarms;
        boolean z9 = z2 && !allowMedia;
        boolean z10 = z4 || (z2 && !allowSystem);
        boolean z11 = z3 || (z2 && ZenModeConfig.areAllZenBehaviorSoundsMuted(this.mConsolidatedPolicy));
        int[] iArr = AudioAttributes.SDK_USAGES;
        int length = iArr.length;
        int i2 = 0;
        while (i2 < length) {
            int i3 = iArr[i2];
            int i4 = AudioAttributes.SUPPRESSIBLE_USAGES.get(i3);
            if (i4 == 3) {
                applyRestrictions(z2, false, i3);
            } else if (i4 == z) {
                applyRestrictions(z2, (z6 || z11) ? z : false, i3);
            } else if (i4 == 2) {
                applyRestrictions(z2, z7 || z11, i3);
            } else if (i4 == 4) {
                applyRestrictions(z2, z8 || z11, i3);
            } else if (i4 == 5) {
                applyRestrictions(z2, z9 || z11, i3);
            } else if (i4 != 6) {
                applyRestrictions(z2, z11, i3);
            } else if (i3 == 13) {
                applyRestrictions(z2, z10 || z11, i3, 28);
                applyRestrictions(z2, false, i3, 3);
            } else {
                applyRestrictions(z2, z10 || z11, i3);
            }
            i2++;
            z = true;
        }
    }

    public void applyRestrictions(boolean z, boolean z2, int i, int i2) {
        long clearCallingIdentity = Binder.clearCallingIdentity();
        try {
            this.mAppOps.setRestriction(i2, i, z2 ? 1 : 0, z ? this.mPriorityOnlyDndExemptPackages : null);
        } finally {
            Binder.restoreCallingIdentity(clearCallingIdentity);
        }
    }

    public void applyRestrictions(boolean z, boolean z2, int i) {
        applyRestrictions(z, z2, i, 3);
        applyRestrictions(z, z2, i, 28);
    }

    public String[] getExceptionPackages() {
        String[] stringArray = this.mContext.getResources().getStringArray(17236275);
        String[] split = SemCscFeature.getInstance().getString("CscFeature_SystemUI_ConfigDndExceptionPackage", "").split(",");
        String[] strArr = new String[stringArray.length + split.length];
        System.arraycopy(stringArray, 0, strArr, 0, stringArray.length);
        System.arraycopy(split, 0, strArr, stringArray.length, split.length);
        return strArr;
    }

    public void applyZenToRingerMode() {
        AudioManagerInternal audioManagerInternal = this.mAudioManager;
        if (audioManagerInternal == null) {
            return;
        }
        int ringerModeInternal = audioManagerInternal.getRingerModeInternal();
        int i = this.mZenMode;
        if (i != 0) {
            if ((i == 2 || i == 3) && ringerModeInternal != 0) {
                setPreviousRingerModeSetting(Integer.valueOf(ringerModeInternal));
                ringerModeInternal = 0;
            }
        } else if (ringerModeInternal == 0) {
            ringerModeInternal = getPreviousRingerModeSetting();
            setPreviousRingerModeSetting(null);
        }
        if (ringerModeInternal != -1) {
            this.mAudioManager.setRingerModeInternal(ringerModeInternal, "ZenModeHelper");
        }
    }

    public final void dispatchOnConfigChanged() {
        Iterator it = this.mCallbacks.iterator();
        while (it.hasNext()) {
            ((Callback) it.next()).onConfigChanged();
        }
    }

    public final void dispatchOnPolicyChanged() {
        Iterator it = this.mCallbacks.iterator();
        while (it.hasNext()) {
            ((Callback) it.next()).onPolicyChanged();
        }
    }

    public final void dispatchOnConsolidatedPolicyChanged() {
        Iterator it = this.mCallbacks.iterator();
        while (it.hasNext()) {
            ((Callback) it.next()).onConsolidatedPolicyChanged();
        }
    }

    public final void dispatchOnZenModeChanged() {
        Iterator it = this.mCallbacks.iterator();
        while (it.hasNext()) {
            ((Callback) it.next()).onZenModeChanged();
        }
    }

    public final void dispatchOnAutomaticRuleStatusChanged(int i, String str, String str2, int i2) {
        Iterator it = this.mCallbacks.iterator();
        while (it.hasNext()) {
            ((Callback) it.next()).onAutomaticRuleStatusChanged(i, str, str2, i2);
        }
    }

    public final ZenModeConfig readDefaultConfig(Resources resources) {
        XmlResourceParser xmlResourceParser = null;
        try {
            try {
                xmlResourceParser = resources.getXml(R.xml.password_kbd_extension);
                while (xmlResourceParser.next() != 1) {
                    ZenModeConfig readXml = ZenModeConfig.readXml(XmlUtils.makeTyped(xmlResourceParser));
                    if (readXml != null) {
                        return readXml;
                    }
                }
            } catch (Exception e) {
                Log.w("ZenModeHelper", "Error reading default zen mode config from resource", e);
            }
            return new ZenModeConfig();
        } finally {
            IoUtils.closeQuietly(xmlResourceParser);
        }
    }

    public void pullRules(List list) {
        synchronized (this.mConfigsLock) {
            int size = this.mConfigs.size();
            for (int i = 0; i < size; i++) {
                int keyAt = this.mConfigs.keyAt(i);
                ZenModeConfig zenModeConfig = (ZenModeConfig) this.mConfigs.valueAt(i);
                list.add(this.mStatsEventBuilderFactory.newBuilder().setAtomId(FrameworkStatsLog.DND_MODE_RULE).writeInt(keyAt).writeBoolean(zenModeConfig.manualRule != null).writeBoolean(zenModeConfig.areChannelsBypassingDnd).writeInt(-1).writeString("").writeInt(1000).addBooleanAnnotation((byte) 1, true).writeByteArray(zenModeConfig.toZenPolicy().toProto()).build());
                ZenModeConfig.ZenRule zenRule = zenModeConfig.manualRule;
                if (zenRule != null) {
                    ruleToProtoLocked(keyAt, zenRule, true, list);
                }
                Iterator it = zenModeConfig.automaticRules.values().iterator();
                while (it.hasNext()) {
                    ruleToProtoLocked(keyAt, (ZenModeConfig.ZenRule) it.next(), false, list);
                }
            }
        }
    }

    public final void ruleToProtoLocked(int i, ZenModeConfig.ZenRule zenRule, boolean z, List list) {
        String str = zenRule.id;
        if (str == null) {
            str = "";
        }
        if (!ZenModeConfig.DEFAULT_RULE_IDS.contains(str)) {
            str = "";
        }
        String pkg = zenRule.getPkg() != null ? zenRule.getPkg() : "";
        String str2 = zenRule.enabler;
        if (str2 != null) {
            pkg = str2;
        }
        if (z) {
            str = "MANUAL_RULE";
        }
        SysUiStatsEvent$Builder addBooleanAnnotation = this.mStatsEventBuilderFactory.newBuilder().setAtomId(FrameworkStatsLog.DND_MODE_RULE).writeInt(i).writeBoolean(zenRule.enabled).writeBoolean(false).writeInt(zenRule.zenMode).writeString(str).writeInt(getPackageUid(pkg, i)).addBooleanAnnotation((byte) 1, true);
        byte[] bArr = new byte[0];
        ZenPolicy zenPolicy = zenRule.zenPolicy;
        if (zenPolicy != null) {
            bArr = zenPolicy.toProto();
        }
        addBooleanAnnotation.writeByteArray(bArr);
        list.add(addBooleanAnnotation.build());
    }

    public final int getPackageUid(String str, int i) {
        if ("android".equals(str)) {
            return 1000;
        }
        String packageUserKey = getPackageUserKey(str, i);
        if (this.mRulesUidCache.get(packageUserKey) == null) {
            try {
                this.mRulesUidCache.put(packageUserKey, Integer.valueOf(this.mPm.getPackageUidAsUser(str, i)));
            } catch (PackageManager.NameNotFoundException unused) {
            }
        }
        return ((Integer) this.mRulesUidCache.getOrDefault(packageUserKey, -1)).intValue();
    }

    public static String getPackageUserKey(String str, int i) {
        return str + "|" + i;
    }

    public final class RingerModeDelegate implements AudioManagerInternal.RingerModeDelegate {
        public String toString() {
            return "ZenModeHelper";
        }

        public RingerModeDelegate() {
        }

        public int onSetRingerModeInternal(int i, int i2, String str, int i3, VolumePolicy volumePolicy) {
            int i4;
            ZenModeHelper zenModeHelper;
            int i5;
            int i6 = 0;
            boolean z = i != i2;
            ZenModeHelper zenModeHelper2 = ZenModeHelper.this;
            int i7 = zenModeHelper2.mZenMode;
            if (i7 == 0 || (i7 == 1 && !ZenModeConfig.areAllPriorityOnlyRingerSoundsMuted(zenModeHelper2.mConfig))) {
                ZenModeHelper.this.setPreviousRingerModeSetting(Integer.valueOf(i2));
            }
            if (i2 != 0) {
                if (i2 == 1 || i2 == 2) {
                    if (!z || i != 0 || ((i5 = (zenModeHelper = ZenModeHelper.this).mZenMode) != 2 && i5 != 3 && (i5 != 1 || !ZenModeConfig.areAllPriorityOnlyRingerSoundsMuted(zenModeHelper.mConfig)))) {
                        if (ZenModeHelper.this.mZenMode != 0) {
                            i4 = 0;
                            i6 = -1;
                        }
                    }
                    i4 = i2;
                }
                i4 = i2;
                i6 = -1;
            } else {
                if (z && volumePolicy.doNotDisturbWhenSilent) {
                    ZenModeHelper zenModeHelper3 = ZenModeHelper.this;
                    int i8 = zenModeHelper3.mZenMode != 0 ? -1 : 1;
                    zenModeHelper3.setPreviousRingerModeSetting(Integer.valueOf(i));
                    i6 = i8;
                    i4 = i2;
                }
                i4 = i2;
                i6 = -1;
            }
            if (i6 != -1) {
                ZenModeHelper.this.setManualZenMode(i6, null, "ringerModeInternal", null, false, 1000, true);
            }
            if (z || i6 != -1 || i3 != i4) {
                ZenLog.traceSetRingerModeInternal(i, i2, str, i3, i4);
            }
            return i4;
        }

        public int onSetRingerModeExternal(int i, int i2, String str, int i3, VolumePolicy volumePolicy) {
            int i4;
            int i5 = 0;
            boolean z = i != i2;
            int i6 = i3 == 1 ? 1 : 0;
            if (i2 != 0) {
                if ((i2 == 1 || i2 == 2) && ZenModeHelper.this.mZenMode != 0) {
                    i6 = i2;
                    i4 = i5;
                } else {
                    i6 = i2;
                    i4 = -1;
                }
            } else if (z) {
                i5 = ZenModeHelper.this.mZenMode == 0 ? 1 : -1;
                i4 = i5;
            } else {
                i6 = i3;
                i4 = -1;
            }
            if (i4 != -1) {
                ZenModeHelper.this.setManualZenMode(i4, null, "ringerModeExternal", str, false, 1000, true);
            }
            ZenLog.traceSetRingerModeExternal(i, i2, str, i3, i6);
            return i6;
        }

        public boolean canVolumeDownEnterSilent() {
            return ZenModeHelper.this.mZenMode == 0;
        }

        public int getRingerModeAffectedStreams(int i) {
            int i2 = i | 38;
            return ZenModeHelper.this.mZenMode == 2 ? i2 | 2072 : i2 & (-2073);
        }
    }

    public final class SettingsObserver extends ContentObserver {
        public final Uri ZEN_MODE;

        public SettingsObserver(Handler handler) {
            super(handler);
            this.ZEN_MODE = Settings.Global.getUriFor("zen_mode");
        }

        public void observe() {
            ZenModeHelper.this.mContext.getContentResolver().registerContentObserver(this.ZEN_MODE, false, this);
            update(null);
        }

        @Override // android.database.ContentObserver
        public void onChange(boolean z, Uri uri) {
            update(uri);
        }

        public void update(Uri uri) {
            if (this.ZEN_MODE.equals(uri)) {
                ZenModeHelper zenModeHelper = ZenModeHelper.this;
                if (zenModeHelper.mZenMode != zenModeHelper.getZenModeSetting()) {
                    if (ZenModeHelper.DEBUG) {
                        Log.d("ZenModeHelper", "Fixing zen mode setting");
                    }
                    ZenModeHelper zenModeHelper2 = ZenModeHelper.this;
                    zenModeHelper2.setZenModeSetting(zenModeHelper2.mZenMode);
                }
            }
        }
    }

    /* JADX WARN: Code restructure failed: missing block: B:8:0x0032, code lost:
    
        if (android.provider.Settings.Secure.getInt(r5.mContext.getContentResolver(), "zen_settings_updated", 0) != 1) goto L12;
     */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    public final void showZenUpgradeNotification(int i) {
        boolean z;
        boolean hasSystemFeature = this.mContext.getPackageManager().hasSystemFeature("android.hardware.type.watch");
        if (this.mIsBootComplete && i != 0 && !hasSystemFeature && Settings.Secure.getInt(this.mContext.getContentResolver(), "show_zen_upgrade_notification", 0) != 0) {
            z = true;
        }
        z = false;
        if (hasSystemFeature) {
            Settings.Secure.putInt(this.mContext.getContentResolver(), "show_zen_upgrade_notification", 0);
        }
        if (z) {
            this.mNotificationManager.notify("ZenModeHelper", 48, createZenUpgradeNotification());
            Settings.Secure.putInt(this.mContext.getContentResolver(), "show_zen_upgrade_notification", 0);
        }
    }

    public Notification createZenUpgradeNotification() {
        int i;
        int i2;
        int i3;
        Bundle bundle = new Bundle();
        bundle.putString("android.substName", this.mContext.getResources().getString(R.string.mediasize_iso_a4));
        if (NotificationManager.Policy.areAllVisualEffectsSuppressed(getConsolidatedNotificationPolicy().suppressedVisualEffects)) {
            i = 17043449;
            i2 = 17043448;
            i3 = R.drawable.ic_contact_picture_180_holo_light;
        } else {
            i = 17043447;
            i2 = 17043446;
            i3 = R.drawable.ic_volume_off;
        }
        Intent intent = new Intent("android.settings.ZEN_MODE_ONBOARDING");
        intent.addFlags(268468224);
        return new Notification.Builder(this.mContext, SystemNotificationChannels.DO_NOT_DISTURB).setAutoCancel(true).setSmallIcon(R.drawable.ic_qs_airplane).setLargeIcon(Icon.createWithResource(this.mContext, i3)).setContentTitle(this.mContext.getResources().getString(i)).setContentText(this.mContext.getResources().getString(i2)).setContentIntent(PendingIntent.getActivity(this.mContext, 0, intent, 201326592)).setAutoCancel(true).setLocalOnly(true).addExtras(bundle).setStyle(new Notification.BigTextStyle()).build();
    }

    public final class Metrics extends Callback {
        public long mModeLogTimeMs;
        public int mNumZenRules;
        public int mPreviousZenMode;
        public int mPreviousZenType;
        public long mRuleCountLogTime;
        public long mTypeLogTimeMs;

        public Metrics() {
            this.mPreviousZenMode = -1;
            this.mModeLogTimeMs = 0L;
            this.mNumZenRules = -1;
            this.mRuleCountLogTime = 0L;
            this.mPreviousZenType = -1;
            this.mTypeLogTimeMs = 0L;
        }

        @Override // com.android.server.notification.ZenModeHelper.Callback
        public void onZenModeChanged() {
            emit();
        }

        @Override // com.android.server.notification.ZenModeHelper.Callback
        public void onConfigChanged() {
            emit();
        }

        public final void emit() {
            ZenModeHelper.this.mHandler.postMetricsTimer();
            emitZenMode();
            emitRules();
            emitDndType();
        }

        public final void emitZenMode() {
            long elapsedRealtime = SystemClock.elapsedRealtime();
            long j = elapsedRealtime - this.mModeLogTimeMs;
            int i = this.mPreviousZenMode;
            ZenModeHelper zenModeHelper = ZenModeHelper.this;
            if (i != zenModeHelper.mZenMode || j > 60000) {
                if (i != -1) {
                    MetricsLogger.count(zenModeHelper.mContext, "dnd_mode_" + this.mPreviousZenMode, (int) j);
                }
                this.mPreviousZenMode = ZenModeHelper.this.mZenMode;
                this.mModeLogTimeMs = elapsedRealtime;
            }
        }

        public final void emitRules() {
            long elapsedRealtime = SystemClock.elapsedRealtime() - this.mRuleCountLogTime;
            synchronized (ZenModeHelper.this.mConfig) {
                int size = ZenModeHelper.this.mConfig.automaticRules.size();
                int i = this.mNumZenRules;
                if (i != size || elapsedRealtime > 60000) {
                    if (i != -1) {
                        MetricsLogger.count(ZenModeHelper.this.mContext, "dnd_rule_count", size - this.mNumZenRules);
                    }
                    this.mNumZenRules = size;
                    this.mRuleCountLogTime = elapsedRealtime;
                }
            }
        }

        public final void emitDndType() {
            long elapsedRealtime = SystemClock.elapsedRealtime();
            long j = elapsedRealtime - this.mTypeLogTimeMs;
            synchronized (ZenModeHelper.this.mConfig) {
                ZenModeHelper zenModeHelper = ZenModeHelper.this;
                int i = 1;
                if (!(zenModeHelper.mZenMode != 0)) {
                    i = 0;
                } else if (zenModeHelper.mConfig.manualRule == null) {
                    i = 2;
                }
                int i2 = this.mPreviousZenType;
                if (i != i2 || j > 60000) {
                    if (i2 != -1) {
                        MetricsLogger.count(zenModeHelper.mContext, "dnd_type_" + this.mPreviousZenType, (int) j);
                    }
                    this.mTypeLogTimeMs = elapsedRealtime;
                    this.mPreviousZenType = i;
                }
            }
        }
    }

    /* renamed from: com.android.server.notification.ZenModeHelper$H */
    public final class HandlerC2036H extends Handler {
        public HandlerC2036H(Looper looper) {
            super(looper);
        }

        public final void postDispatchOnZenModeChanged() {
            removeMessages(1);
            sendEmptyMessage(1);
        }

        public final void postMetricsTimer() {
            removeMessages(2);
            sendEmptyMessageDelayed(2, 21600000L);
        }

        public final void postUpdateRingerAndAudio(boolean z) {
            removeMessages(5);
            sendMessage(obtainMessage(5, Boolean.valueOf(z)));
        }

        @Override // android.os.Handler
        public void handleMessage(Message message) {
            int i = message.what;
            if (i == 1) {
                ZenModeHelper.this.dispatchOnZenModeChanged();
                return;
            }
            if (i == 2) {
                ZenModeHelper.this.mMetrics.emit();
            } else {
                if (i != 5) {
                    return;
                }
                ZenModeHelper.this.updateRingerAndAudio(((Boolean) message.obj).booleanValue());
            }
        }
    }
}
