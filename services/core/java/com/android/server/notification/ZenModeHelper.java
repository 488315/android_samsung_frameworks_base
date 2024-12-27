package com.android.server.notification;

import android.R;
import android.app.AppOpsManager;
import android.app.AutomaticZenRule;
import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.ComponentName;
import android.content.Context;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageManager;
import android.content.res.Resources;
import android.database.ContentObserver;
import android.graphics.drawable.Icon;
import android.media.AudioAttributes;
import android.media.AudioManagerInternal;
import android.media.VolumePolicy;
import android.net.ConnectivityModuleConnector$$ExternalSyntheticOutline0;
import android.net.Uri;
import android.os.Binder;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.os.SystemClock;
import android.provider.Settings;
import android.service.notification.Condition;
import android.service.notification.DeviceEffectsApplier;
import android.service.notification.SystemZenRules;
import android.service.notification.ZenAdapters;
import android.service.notification.ZenDeviceEffects;
import android.service.notification.ZenModeConfig;
import android.service.notification.ZenPolicy;
import android.text.TextUtils;
import android.util.ArrayMap;
import android.util.LocalLog;
import android.util.Log;
import android.util.Slog;
import android.util.SparseArray;
import android.util.proto.ProtoOutputStream;

import com.android.internal.config.sysui.SystemUiSystemPropertiesFlags;
import com.android.internal.logging.MetricsLogger;
import com.android.internal.notification.SystemNotificationChannels;
import com.android.internal.util.FrameworkStatsLog;
import com.android.internal.util.FunctionalUtils;
import com.android.internal.util.Preconditions;
import com.android.internal.util.jobs.Preconditions$$ExternalSyntheticOutline0;
import com.android.server.BatteryService$$ExternalSyntheticOutline0;
import com.android.server.LocalServices;
import com.android.server.VpnManagerService$$ExternalSyntheticOutline0;
import com.android.server.accounts.AccountManagerService$$ExternalSyntheticOutline0;
import com.android.server.am.ActivityManagerConstants$$ExternalSyntheticOutline0;

import com.samsung.android.knox.custom.KnoxCustomManagerService;
import com.samsung.android.knox.zt.devicetrust.EndpointMonitorConst;

import java.io.PrintWriter;
import java.time.Clock;
import java.time.Duration;
import java.time.Instant;
import java.time.temporal.TemporalAmount;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;
import java.util.Objects;

/* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
/* loaded from: classes2.dex */
public final class ZenModeHelper {
    public static final boolean DEBUG = Log.isLoggable("ZenModeHelper", 3);
    public static final Duration DELETED_RULE_KEPT_FOR = Duration.ofDays(30);
    public final AppOpsManager mAppOps;
    protected AudioManagerInternal mAudioManager;
    public final ArrayList mCallbacks;
    public final Clock mClock;
    protected final ZenModeConditions mConditions;
    protected ZenModeConfig mConfig;
    public final Object mConfigLock;
    final SparseArray mConfigs;
    public final Object mConfigsArrayLock;
    public ZenDeviceEffects mConsolidatedDeviceEffects;
    protected NotificationManager.Policy mConsolidatedPolicy;
    public final Context mContext;
    public final ZenModeConfig mDefaultConfig;
    public DeviceEffectsApplier mDeviceEffectsApplier;
    public final ZenModeFiltering mFiltering;
    public final SystemUiSystemPropertiesFlags.FlagResolver mFlagResolver;
    public final H mHandler;
    protected boolean mIsSystemServicesReady;
    public final Metrics mMetrics;
    public final NotificationManager mNotificationManager;
    public PackageManager mPm;
    public String[] mPriorityOnlyDndExemptPackages;
    protected final ArrayMap mRulesUidCache = new ArrayMap();
    public final ManagedServices.Config mServiceConfig;
    public long mSuppressedEffects;
    public int mUser;
    protected int mZenMode;
    public final ZenModeEventLogger mZenModeEventLogger;

    /* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
    public abstract class Callback {
        public void onAutomaticRuleStatusChanged(int i, int i2, String str, String str2) {}

        public abstract void onConfigChanged();

        public void onConsolidatedPolicyChanged(NotificationManager.Policy policy) {}

        public void onPolicyChanged(NotificationManager.Policy policy) {}

        public abstract void onZenModeChanged();
    }

    /* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
    public final class H extends Handler {
        public static final /* synthetic */ int $r8$clinit = 0;

        public H(Looper looper) {
            super(looper);
        }

        @Override // android.os.Handler
        public final void handleMessage(Message message) {
            int i = message.what;
            ZenModeHelper zenModeHelper = ZenModeHelper.this;
            if (i == 1) {
                Iterator it = zenModeHelper.mCallbacks.iterator();
                while (it.hasNext()) {
                    ((Callback) it.next()).onZenModeChanged();
                }
                return;
            }
            if (i == 2) {
                zenModeHelper.mMetrics.emit();
                return;
            }
            if (i != 5) {
                if (i != 6) {
                    return;
                }
                zenModeHelper.applyConsolidatedDeviceEffects(message.arg1);
                return;
            }
            boolean booleanValue = ((Boolean) message.obj).booleanValue();
            zenModeHelper.getClass();
            AudioManagerInternal audioManagerInternal =
                    (AudioManagerInternal) LocalServices.getService(AudioManagerInternal.class);
            if (audioManagerInternal != null) {
                audioManagerInternal.updateRingerModeAffectedStreamsInternal();
            }
            if (booleanValue) {
                zenModeHelper.applyZenToRingerMode();
            }
            zenModeHelper.applyRestrictions();
        }
    }

    /* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
    public final class Metrics extends Callback {
        public int mPreviousZenMode = -1;
        public long mModeLogTimeMs = 0;
        public int mNumZenRules = -1;
        public long mRuleCountLogTime = 0;
        public int mPreviousZenType = -1;
        public long mTypeLogTimeMs = 0;

        public Metrics() {}

        public final void emit() {
            H h = ZenModeHelper.this.mHandler;
            int i = H.$r8$clinit;
            int i2 = 2;
            h.removeMessages(2);
            h.sendEmptyMessageDelayed(2, 21600000L);
            long elapsedRealtime = SystemClock.elapsedRealtime();
            long j = elapsedRealtime - this.mModeLogTimeMs;
            int i3 = this.mPreviousZenMode;
            ZenModeHelper zenModeHelper = ZenModeHelper.this;
            if (i3 != zenModeHelper.mZenMode || j > 60000) {
                if (i3 != -1) {
                    MetricsLogger.count(
                            zenModeHelper.mContext, "dnd_mode_" + this.mPreviousZenMode, (int) j);
                }
                this.mPreviousZenMode = zenModeHelper.mZenMode;
                this.mModeLogTimeMs = elapsedRealtime;
            }
            long elapsedRealtime2 = SystemClock.elapsedRealtime() - this.mRuleCountLogTime;
            synchronized (ZenModeHelper.this.mConfigLock) {
                try {
                    int size = ZenModeHelper.this.mConfig.automaticRules.size();
                    int i4 = this.mNumZenRules;
                    if (i4 != size || elapsedRealtime2 > 60000) {
                        if (i4 != -1) {
                            MetricsLogger.count(
                                    ZenModeHelper.this.mContext, "dnd_rule_count", size - i4);
                        }
                        this.mNumZenRules = size;
                        this.mRuleCountLogTime = elapsedRealtime2;
                    }
                } finally {
                }
            }
            long elapsedRealtime3 = SystemClock.elapsedRealtime();
            long j2 = elapsedRealtime3 - this.mTypeLogTimeMs;
            synchronized (ZenModeHelper.this.mConfigLock) {
                try {
                    ZenModeHelper zenModeHelper2 = ZenModeHelper.this;
                    if (zenModeHelper2.mZenMode == 0) {
                        i2 = 0;
                    } else if (zenModeHelper2.mConfig.manualRule != null) {
                        i2 = 1;
                    }
                    int i5 = this.mPreviousZenType;
                    if (i2 != i5 || j2 > 60000) {
                        if (i5 != -1) {
                            MetricsLogger.count(
                                    zenModeHelper2.mContext,
                                    "dnd_type_" + this.mPreviousZenType,
                                    (int) j2);
                        }
                        this.mTypeLogTimeMs = elapsedRealtime3;
                        this.mPreviousZenType = i2;
                    }
                } finally {
                }
            }
        }

        @Override // com.android.server.notification.ZenModeHelper.Callback
        public final void onConfigChanged() {
            emit();
        }

        @Override // com.android.server.notification.ZenModeHelper.Callback
        public final void onZenModeChanged() {
            emit();
        }
    }

    /* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
    public final class RingerModeDelegate implements AudioManagerInternal.RingerModeDelegate {
        public final boolean canVolumeDownEnterSilent() {
            throw null;
        }

        public final int getRingerModeAffectedStreams(int i) {
            throw null;
        }

        public final int onSetRingerModeExternal(
                int i, int i2, String str, int i3, VolumePolicy volumePolicy) {
            int i4;
            boolean z = i != i2;
            if (i2 != 0) {
                if (i2 == 1) {
                    throw null;
                }
                if (i2 == 2) {
                    throw null;
                }
                i4 = i2;
            } else {
                if (z) {
                    throw null;
                }
                i4 = i3;
            }
            LocalLog localLog = ZenLog.STATE_CHANGES;
            StringBuilder m = Preconditions$$ExternalSyntheticOutline0.m(str, ",e:");
            m.append(ZenLog.ringerModeToString(i));
            m.append("->");
            m.append(ZenLog.ringerModeToString(i2));
            m.append(",i:");
            m.append(ZenLog.ringerModeToString(i3));
            m.append("->");
            m.append(ZenLog.ringerModeToString(i4));
            ZenLog.append(3, m.toString());
            return i4;
        }

        public final int onSetRingerModeInternal(
                int i, int i2, String str, int i3, VolumePolicy volumePolicy) {
            throw null;
        }

        public final String toString() {
            return "ZenModeHelper";
        }
    }

    /* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
    public final class SettingsObserver extends ContentObserver {
        public final Uri ZEN_MODE;

        public SettingsObserver(Handler handler) {
            super(handler);
            this.ZEN_MODE = Settings.Global.getUriFor("zen_mode");
        }

        @Override // android.database.ContentObserver
        public final void onChange(boolean z, Uri uri) {
            update(uri);
        }

        public final void update(Uri uri) {
            if (this.ZEN_MODE.equals(uri)) {
                ZenModeHelper zenModeHelper = ZenModeHelper.this;
                if (zenModeHelper.mZenMode
                        != Settings.Global.getInt(
                                zenModeHelper.mContext.getContentResolver(), "zen_mode", 0)) {
                    if (ZenModeHelper.DEBUG) {
                        Log.d("ZenModeHelper", "Fixing zen mode setting");
                    }
                    ZenModeHelper zenModeHelper2 = ZenModeHelper.this;
                    zenModeHelper2.setZenModeSetting(zenModeHelper2.mZenMode);
                }
            }
        }
    }

    /* JADX WARN: Removed duplicated region for block: B:11:0x00ce  */
    /* JADX WARN: Removed duplicated region for block: B:25:0x00f4 A[EXC_TOP_SPLITTER, SYNTHETIC] */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public ZenModeHelper(
            android.content.Context r5,
            android.os.Looper r6,
            java.time.Clock r7,
            com.android.server.notification.ConditionProviders r8,
            com.android.internal.config.sysui.SystemUiSystemPropertiesFlags.FlagResolver r9,
            com.android.server.notification.ZenModeEventLogger r10) {
        /*
            Method dump skipped, instructions count: 315
            To view this dump change 'Code comments level' option to 'DEBUG'
        */
        throw new UnsupportedOperationException(
                "Method not decompiled:"
                    + " com.android.server.notification.ZenModeHelper.<init>(android.content.Context,"
                    + " android.os.Looper, java.time.Clock,"
                    + " com.android.server.notification.ConditionProviders,"
                    + " com.android.internal.config.sysui.SystemUiSystemPropertiesFlags$FlagResolver,"
                    + " com.android.server.notification.ZenModeEventLogger):void");
    }

    public static String implicitRuleId(String str) {
        return ConnectivityModuleConnector$$ExternalSyntheticOutline0.m("implicit_", str);
    }

    public static void requirePublicOrigin(int i, String str) {
        if (android.app.Flags.modesApi()) {
            Preconditions.checkArgument(
                    i == 4 || i == 5 || i == 3,
                    "Expected one of UPDATE_ORIGIN_APP, UPDATE_ORIGIN_SYSTEM_OR_SYSTEMUI, or"
                        + " UPDATE_ORIGIN_USER for %s, but received '%s'.",
                    new Object[] {str, Integer.valueOf(i)});
        }
    }

    public final void applyConsolidatedDeviceEffects(int i) {
        DeviceEffectsApplier deviceEffectsApplier;
        ZenDeviceEffects zenDeviceEffects;
        if (android.app.Flags.modesApi()) {
            synchronized (this.mConfigLock) {
                deviceEffectsApplier = this.mDeviceEffectsApplier;
                zenDeviceEffects = this.mConsolidatedDeviceEffects;
            }
            if (deviceEffectsApplier != null) {
                deviceEffectsApplier.apply(zenDeviceEffects, i);
            }
        }
    }

    public final void applyCustomPolicy(
            ZenPolicy zenPolicy, ZenModeConfig.ZenRule zenRule, boolean z) {
        int i = zenRule.zenMode;
        if (i == 2) {
            zenPolicy.apply(
                    new ZenPolicy.Builder()
                            .disallowAllSounds()
                            .allowPriorityChannels(false)
                            .build());
            return;
        }
        if (i == 3) {
            zenPolicy.apply(
                    new ZenPolicy.Builder()
                            .disallowAllSounds()
                            .allowAlarms(true)
                            .allowMedia(true)
                            .allowPriorityChannels(false)
                            .build());
            return;
        }
        ZenPolicy zenPolicy2 = zenRule.zenPolicy;
        if (zenPolicy2 != null) {
            zenPolicy.apply(zenPolicy2);
            return;
        }
        if (!android.app.Flags.modesApi()) {
            zenPolicy.apply(this.mConfig.getZenPolicy());
            return;
        }
        if (z) {
            zenPolicy.apply(this.mConfig.getZenPolicy());
            return;
        }
        Log.wtf(
                "ZenModeHelper",
                "active automatic rule found with no specified policy: " + zenRule);
        zenPolicy.apply(
                (android.app.Flags.modesUi() ? this.mDefaultConfig : this.mConfig).getZenPolicy());
    }

    public final void applyGlobalPolicyAsImplicitZenRule(
            String str, int i, NotificationManager.Policy policy) {
        boolean z;
        if (!android.app.Flags.modesApi()) {
            Log.wtf("ZenModeHelper", "applyGlobalPolicyAsImplicitZenRule called with flag off!");
            return;
        }
        synchronized (this.mConfigLock) {
            try {
                ZenModeConfig zenModeConfig = this.mConfig;
                if (zenModeConfig == null) {
                    return;
                }
                ZenModeConfig copy = zenModeConfig.copy();
                ZenModeConfig.ZenRule zenRule =
                        (ZenModeConfig.ZenRule) copy.automaticRules.get(implicitRuleId(str));
                if (zenRule == null) {
                    zenRule = newImplicitZenRule(str);
                    z = true;
                    zenRule.zenMode = 1;
                    copy.automaticRules.put(zenRule.id, zenRule);
                } else {
                    z = false;
                }
                if (zenRule.zenPolicyUserModifiedFields == 0) {
                    ZenPolicy notificationPolicyToZenPolicy =
                            ZenAdapters.notificationPolicyToZenPolicy(policy);
                    if (z) {
                        notificationPolicyToZenPolicy =
                                this.mConfig
                                        .getZenPolicy()
                                        .overwrittenWith(notificationPolicyToZenPolicy);
                    }
                    updatePolicy(zenRule, notificationPolicyToZenPolicy, false, z);
                    setConfigLocked(copy, 4, "applyGlobalPolicyAsImplicitZenRule", null, true, i);
                }
            } catch (Throwable th) {
                throw th;
            }
        }
    }

    public final void applyGlobalZenModeAsImplicitZenRule(int i, int i2, String str) {
        if (!android.app.Flags.modesApi()) {
            Log.wtf("ZenModeHelper", "applyGlobalZenModeAsImplicitZenRule called with flag off!");
            return;
        }
        synchronized (this.mConfigLock) {
            try {
                ZenModeConfig zenModeConfig = this.mConfig;
                if (zenModeConfig == null) {
                    return;
                }
                ZenModeConfig copy = zenModeConfig.copy();
                ZenModeConfig.ZenRule zenRule =
                        (ZenModeConfig.ZenRule) copy.automaticRules.get(implicitRuleId(str));
                if (i2 != 0) {
                    if (zenRule == null) {
                        zenRule = newImplicitZenRule(str);
                        zenRule.zenPolicy = this.mConfig.getZenPolicy().copy();
                        copy.automaticRules.put(zenRule.id, zenRule);
                    }
                    if ((zenRule.userModifiedFields & 2) == 0) {
                        zenRule.zenMode = i2;
                    }
                    zenRule.snoozing = false;
                    zenRule.condition =
                            new Condition(
                                    zenRule.conditionId, this.mContext.getString(17043667), 1);
                    setConfigLocked(copy, 4, "applyGlobalZenModeAsImplicitZenRule", null, true, i);
                } else if (zenRule != null) {
                    setAutomaticZenRuleStateLocked(
                            copy,
                            Collections.singletonList(zenRule),
                            new Condition(
                                    zenRule.conditionId, this.mContext.getString(17043668), 0),
                            4,
                            i);
                }
            } catch (Throwable th) {
                throw th;
            }
        }
    }

    public void applyRestrictions() {
        int i = this.mZenMode;
        boolean z = true;
        boolean z2 = i == 1;
        boolean z3 = i == 2;
        boolean z4 = i == 3;
        boolean z5 =
                this.mConsolidatedPolicy.allowCalls()
                        || this.mConsolidatedPolicy.getExceptionContacts().size() > 0;
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
        boolean z11 =
                z3 || (z2 && ZenModeConfig.areAllZenBehaviorSoundsMuted(this.mConsolidatedPolicy));
        int[] array = AudioAttributes.SDK_USAGES.toArray();
        int length = array.length;
        int i2 = 0;
        while (i2 < length) {
            int i3 = array[i2];
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

    public void applyRestrictions(boolean z, boolean z2, int i) {
        applyRestrictions(z, z2, i, 3);
        applyRestrictions(z, z2, i, 28);
    }

    public void applyRestrictions(boolean z, boolean z2, int i, int i2) {
        long clearCallingIdentity = Binder.clearCallingIdentity();
        try {
            this.mAppOps.setRestriction(
                    i2, i, z2 ? 1 : 0, z ? this.mPriorityOnlyDndExemptPackages : null);
            Binder.restoreCallingIdentity(clearCallingIdentity);
        } catch (Throwable th) {
            Binder.restoreCallingIdentity(clearCallingIdentity);
            throw th;
        }
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
            ringerModeInternal =
                    Settings.Global.getInt(
                            this.mContext.getContentResolver(), "zen_mode_ringer_level", 2);
            setPreviousRingerModeSetting(null);
        }
        if (ringerModeInternal != -1) {
            this.mAudioManager.setRingerModeInternal(ringerModeInternal, "ZenModeHelper");
        }
    }

    public final boolean canManageAutomaticZenRule(ZenModeConfig.ZenRule zenRule) {
        int callingUid = Binder.getCallingUid();
        if (callingUid == 0
                || callingUid == 1000
                || this.mContext.checkCallingPermission("android.permission.MANAGE_NOTIFICATIONS")
                        == 0) {
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

    public final void cleanUpZenRules() {
        Instant minus = this.mClock.instant().minus((TemporalAmount) DELETED_RULE_KEPT_FOR);
        synchronized (this.mConfigLock) {
            try {
                ZenModeConfig copy = this.mConfig.copy();
                deleteRulesWithoutOwner(copy.automaticRules);
                if (android.app.Flags.modesApi()) {
                    deleteRulesWithoutOwner(copy.deletedRules);
                    for (int size = copy.deletedRules.size() - 1; size >= 0; size--) {
                        Instant instant =
                                ((ZenModeConfig.ZenRule) copy.deletedRules.valueAt(size))
                                        .deletionInstant;
                        if (instant != null && !instant.isBefore(minus)) {}
                        copy.deletedRules.removeAt(size);
                    }
                }
                if (!copy.equals(this.mConfig)) {
                    setConfigLocked(copy, 5, "cleanUpZenRules", null, true, 1000);
                }
            } catch (Throwable th) {
                throw th;
            }
        }
    }

    public Notification createZenUpgradeNotification() {
        int i;
        int i2;
        int i3;
        Bundle bundle = new Bundle();
        bundle.putString(
                "android.substName",
                this.mContext.getResources().getString(R.string.mediasize_na_ansi_f));
        if (NotificationManager.Policy.areAllVisualEffectsSuppressed(
                this.mConsolidatedPolicy.copy().suppressedVisualEffects)) {
            i = 17043679;
            i2 = 17043678;
            i3 = R.drawable.ic_find_next_material;
        } else {
            i = 17043677;
            i2 = 17043676;
            i3 = R.drawable.jog_tab_left_confirm_red;
        }
        return new Notification.Builder(this.mContext, SystemNotificationChannels.DO_NOT_DISTURB)
                .setAutoCancel(true)
                .setSmallIcon(R.drawable.ic_vibrate_small)
                .setLargeIcon(Icon.createWithResource(this.mContext, i3))
                .setContentTitle(this.mContext.getResources().getString(i))
                .setContentText(this.mContext.getResources().getString(i2))
                .setContentIntent(
                        PendingIntent.getActivity(
                                this.mContext,
                                0,
                                BatteryService$$ExternalSyntheticOutline0.m(
                                        268468224, "android.settings.ZEN_MODE_ONBOARDING"),
                                201326592))
                .setAutoCancel(true)
                .setLocalOnly(true)
                .addExtras(bundle)
                .setStyle(new Notification.BigTextStyle())
                .build();
    }

    public final void deleteRulesWithoutOwner(ArrayMap arrayMap) {
        long millis =
                android.app.Flags.modesApi() ? this.mClock.millis() : System.currentTimeMillis();
        if (arrayMap != null) {
            for (int size = arrayMap.size() - 1; size >= 0; size--) {
                ZenModeConfig.ZenRule zenRule = (ZenModeConfig.ZenRule) arrayMap.valueAt(size);
                if (259200000 < millis - zenRule.creationTime) {
                    try {
                        if (zenRule.getPkg() != null) {
                            this.mPm.getPackageInfo(zenRule.getPkg(), 4194304);
                        }
                    } catch (PackageManager.NameNotFoundException unused) {
                        arrayMap.removeAt(size);
                    }
                }
            }
        }
    }

    public final void dispatchOnAutomaticRuleStatusChanged(int i, int i2, String str, String str2) {
        Iterator it = this.mCallbacks.iterator();
        while (it.hasNext()) {
            ((Callback) it.next()).onAutomaticRuleStatusChanged(i, i2, str, str2);
        }
    }

    public final String drawableResIdToResName(int i, String str) {
        if (i == 0) {
            return null;
        }
        Objects.requireNonNull(str);
        try {
            String resourceName = this.mPm.getResourcesForApplication(str).getResourceName(i);
            if (resourceName == null || resourceName.length() <= 1000) {
                return resourceName;
            }
            Slog.e(
                    "ZenModeHelper",
                    "Resource name for ID="
                            + i
                            + " in package "
                            + str
                            + " is too long ("
                            + resourceName.length()
                            + "); ignoring it");
            return null;
        } catch (PackageManager.NameNotFoundException | Resources.NotFoundException unused) {
            Slog.e(
                    "ZenModeHelper",
                    AccountManagerService$$ExternalSyntheticOutline0.m(
                            i,
                            "Resource name for ID=",
                            " not found in package ",
                            str,
                            ". Resource IDs may change when the application is upgraded, and the"
                                + " system may not be able to find the correct resource."));
            return null;
        }
    }

    public final void dump(ProtoOutputStream protoOutputStream) {
        Condition condition;
        protoOutputStream.write(1159641169921L, this.mZenMode);
        synchronized (this.mConfigLock) {
            try {
                ZenModeConfig.ZenRule zenRule = this.mConfig.manualRule;
                if (zenRule != null) {
                    zenRule.dumpDebug(protoOutputStream, 2246267895810L);
                }
                for (ZenModeConfig.ZenRule zenRule2 : this.mConfig.automaticRules.values()) {
                    if (zenRule2.enabled
                            && (condition = zenRule2.condition) != null
                            && condition.state == 1
                            && !zenRule2.snoozing) {
                        zenRule2.dumpDebug(protoOutputStream, 2246267895810L);
                    }
                }
                this.mConfig.toNotificationPolicy().dumpDebug(protoOutputStream, 1146756268037L);
                protoOutputStream.write(1120986464259L, this.mSuppressedEffects);
            } catch (Throwable th) {
                throw th;
            }
        }
    }

    public final void dump(PrintWriter printWriter) {
        int i;
        printWriter.print("    ");
        printWriter.print("mZenMode=");
        printWriter.println(Settings.Global.zenModeToString(this.mZenMode));
        printWriter.print("    ");
        printWriter.println("mConsolidatedPolicy=" + this.mConsolidatedPolicy.toString());
        synchronized (this.mConfigsArrayLock) {
            try {
                int size = this.mConfigs.size();
                for (int i2 = 0; i2 < size; i2++) {
                    String str = "mConfigs[u=" + this.mConfigs.keyAt(i2) + "]";
                    ZenModeConfig zenModeConfig = (ZenModeConfig) this.mConfigs.valueAt(i2);
                    printWriter.print("    ");
                    printWriter.print(str);
                    printWriter.print('=');
                    printWriter.println(zenModeConfig);
                }
            } catch (Throwable th) {
                throw th;
            }
        }
        printWriter.print("    ");
        printWriter.print("mUser=");
        printWriter.println(this.mUser);
        synchronized (this.mConfigLock) {
            ZenModeConfig zenModeConfig2 = this.mConfig;
            printWriter.print("    ");
            printWriter.print("mConfig");
            printWriter.print('=');
            printWriter.println(zenModeConfig2);
        }
        printWriter.print("    ");
        printWriter.print("mSuppressedEffects=");
        ActivityManagerConstants$$ExternalSyntheticOutline0.m(
                this.mSuppressedEffects, printWriter, "    ", "mPriorityOnlyDndExemptPackages=");
        printWriter.println("");
        for (i = 0; i < this.mPriorityOnlyDndExemptPackages.length; i++) {
            printWriter.print("    ");
            printWriter.print("        ");
            printWriter.println(this.mPriorityOnlyDndExemptPackages[i]);
        }
        ZenModeFiltering zenModeFiltering = this.mFiltering;
        zenModeFiltering.getClass();
        printWriter.print("    ");
        printWriter.print("mDefaultPhoneApp=");
        printWriter.println(zenModeFiltering.mDefaultPhoneApp);
        printWriter.print("    ");
        printWriter.print("RepeatCallers.mThresholdMinutes=");
        printWriter.println(ZenModeFiltering.REPEAT_CALLERS.mThresholdMinutes);
        ZenModeConditions zenModeConditions = this.mConditions;
        zenModeConditions.getClass();
        printWriter.print("    ");
        printWriter.print("mSubscriptions=");
        printWriter.println(zenModeConditions.mSubscriptions);
    }

    public void evaluateZenModeLocked(int i, String str, boolean z) {
        int i2;
        if (DEBUG) {
            Log.d("ZenModeHelper", "evaluateZenMode");
        }
        if (this.mConfig == null) {
            return;
        }
        NotificationManager.Policy policy = this.mConsolidatedPolicy;
        boolean z2 = false;
        int hashCode = policy == null ? 0 : policy.hashCode();
        int i3 = this.mZenMode;
        synchronized (this.mConfigLock) {
            try {
                ZenModeConfig zenModeConfig = this.mConfig;
                if (zenModeConfig == null) {
                    i2 = 0;
                } else if (zenModeConfig.isManualActive()) {
                    i2 = this.mConfig.manualRule.zenMode;
                } else {
                    int i4 = 0;
                    for (ZenModeConfig.ZenRule zenRule : this.mConfig.automaticRules.values()) {
                        if (zenRule.isAutomaticActive()) {
                            int i5 = zenRule.zenMode;
                            char c = 2;
                            char c2 =
                                    i5 != 1
                                            ? i5 != 2 ? i5 != 3 ? (char) 0 : (char) 2 : (char) 3
                                            : (char) 1;
                            if (i4 == 1) {
                                c = 1;
                            } else if (i4 == 2) {
                                c = 3;
                            } else if (i4 != 3) {
                                c = 0;
                            }
                            if (c2 > c) {
                                if (Settings.Secure.getInt(
                                                this.mContext.getContentResolver(),
                                                "zen_settings_suggestion_viewed",
                                                1)
                                        == 0) {
                                    Settings.Secure.putInt(
                                            this.mContext.getContentResolver(),
                                            "show_zen_settings_suggestion",
                                            1);
                                }
                                i4 = zenRule.zenMode;
                            }
                        }
                    }
                    i2 = i4;
                }
            } finally {
            }
        }
        ZenLog.traceSetZenMode(i2, str);
        this.mZenMode = i2;
        setZenModeSetting(i2);
        updateAndApplyConsolidatedPolicyAndDeviceEffects(i, str);
        if (z && (i2 != i3 || (i2 == 1 && hashCode != this.mConsolidatedPolicy.hashCode()))) {
            z2 = true;
        }
        H h = this.mHandler;
        int i6 = H.$r8$clinit;
        h.removeMessages(5);
        h.sendMessage(h.obtainMessage(5, Boolean.valueOf(z2)));
        if (i2 != i3) {
            H h2 = this.mHandler;
            h2.removeMessages(1);
            h2.sendEmptyMessage(1);
        }
    }

    public final ArrayList getAppsToBypassDndForEnabledForMode() {
        ArrayList arrayList;
        ZenPolicy zenPolicy;
        synchronized (this.mConfigLock) {
            try {
                arrayList = null;
                for (ZenModeConfig.ZenRule zenRule : this.mConfig.automaticRules.values()) {
                    if (zenRule.isAutomaticActive()
                            && zenRule.pkg.equals(KnoxCustomManagerService.SETTING_PKG_NAME)
                            && (zenPolicy = zenRule.zenPolicy) != null) {
                        arrayList = zenPolicy.getAppsToBypassDnd();
                        if (DEBUG) {
                            Log.d(
                                    "ZenModeHelper",
                                    "BixbyRoutine Mode apps=" + arrayList.toString());
                        }
                    }
                }
            } catch (Throwable th) {
                throw th;
            }
        }
        return arrayList;
    }

    public final ZenModeConfig getConfig() {
        ZenModeConfig copy;
        synchronized (this.mConfigLock) {
            copy = this.mConfig.copy();
        }
        return copy;
    }

    public final int getCurrentInstanceCount(ComponentName componentName) {
        int i = 0;
        if (componentName == null) {
            return 0;
        }
        synchronized (this.mConfigLock) {
            try {
                for (ZenModeConfig.ZenRule zenRule : this.mConfig.automaticRules.values()) {
                    if (!componentName.equals(zenRule.component)
                            && !componentName.equals(zenRule.configurationActivity)) {}
                    i++;
                }
            } catch (Throwable th) {
                throw th;
            }
        }
        return i;
    }

    public ZenPolicy getDefaultZenPolicy() {
        return this.mDefaultConfig.getZenPolicy();
    }

    public final NotificationManager.Policy getNotificationPolicy() {
        NotificationManager.Policy notificationPolicy;
        synchronized (this.mConfigLock) {
            ZenModeConfig zenModeConfig = this.mConfig;
            notificationPolicy =
                    zenModeConfig == null ? null : zenModeConfig.toNotificationPolicy();
        }
        return notificationPolicy;
    }

    public final List getZenRules() {
        ArrayList arrayList = new ArrayList();
        synchronized (this.mConfigLock) {
            try {
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
            } catch (Throwable th) {
                throw th;
            }
        }
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
        synchronized (this.mConfigsArrayLock) {
            try {
                copy =
                        this.mConfigs.get(i) != null
                                ? ((ZenModeConfig) this.mConfigs.get(i)).copy()
                                : null;
            } catch (Throwable th) {
                throw th;
            }
        }
        if (copy == null) {
            if (z) {
                Log.d("ZenModeHelper", str + " generating default config for user " + i);
            }
            copy = this.mDefaultConfig.copy();
            copy.user = i;
        }
        ZenModeConfig zenModeConfig = copy;
        synchronized (this.mConfigLock) {
            setConfigLocked(zenModeConfig, 2, str, null, true, 1000);
        }
        cleanUpZenRules();
    }

    public final void maybePreserveRemovedRule(
            ZenModeConfig zenModeConfig, ZenModeConfig.ZenRule zenRule, int i) {
        String deletedRuleKey;
        if (!android.app.Flags.modesApi()
                || i != 4
                || zenRule.canBeUpdatedByApp()
                || "android".equals(zenRule.pkg)
                || (deletedRuleKey = ZenModeConfig.deletedRuleKey(zenRule)) == null) {
            return;
        }
        ZenModeConfig.ZenRule copy = zenRule.copy();
        copy.deletionInstant = Instant.now(this.mClock);
        copy.snoozing = false;
        copy.condition = null;
        zenModeConfig.deletedRules.put(deletedRuleKey, copy);
    }

    public final ZenModeConfig.ZenRule newImplicitZenRule(final String str) {
        final ZenModeConfig.ZenRule zenRule = new ZenModeConfig.ZenRule();
        zenRule.id = implicitRuleId(str);
        zenRule.pkg = str;
        zenRule.creationTime = this.mClock.millis();
        Binder.withCleanCallingIdentity(
                new FunctionalUtils
                        .ThrowingRunnable() { // from class:
                                              // com.android.server.notification.ZenModeHelper$$ExternalSyntheticLambda0
                    public final void runOrThrow() {
                        ZenModeHelper zenModeHelper = ZenModeHelper.this;
                        String str2 = str;
                        ZenModeConfig.ZenRule zenRule2 = zenRule;
                        zenModeHelper.getClass();
                        try {
                            ApplicationInfo applicationInfo =
                                    zenModeHelper.mPm.getApplicationInfo(str2, 0);
                            zenRule2.name = applicationInfo.loadLabel(zenModeHelper.mPm).toString();
                            zenRule2.iconResName =
                                    zenModeHelper.drawableResIdToResName(
                                            applicationInfo.icon, str2);
                        } catch (PackageManager.NameNotFoundException unused) {
                            Log.w(
                                    "ZenModeHelper",
                                    "Package not found for creating implicit zen rule");
                            zenRule2.name = "Unknown";
                        }
                    }
                });
        zenRule.type = 0;
        zenRule.triggerDescription = this.mContext.getString(17043669, zenRule.name);
        zenRule.condition = null;
        zenRule.conditionId =
                new Uri.Builder()
                        .scheme("condition")
                        .authority("android")
                        .appendPath("implicit")
                        .appendPath(str)
                        .build();
        zenRule.enabled = true;
        zenRule.modified = false;
        zenRule.component = null;
        zenRule.configurationActivity = null;
        return zenRule;
    }

    public final boolean populateZenRule(
            String str,
            AutomaticZenRule automaticZenRule,
            ZenModeConfig.ZenRule zenRule,
            int i,
            boolean z) {
        boolean z2;
        Uri uri;
        Condition condition;
        boolean z3 = false;
        if (!android.app.Flags.modesApi()) {
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
            zenRule.zenMode =
                    NotificationManager.zenModeFromInterruptionFilter(
                            automaticZenRule.getInterruptionFilter(), 0);
            zenRule.configurationActivity = automaticZenRule.getConfigurationActivity();
            return true;
        }
        if (z) {
            zenRule.id = ZenModeConfig.newRuleId();
            zenRule.creationTime = this.mClock.millis();
            zenRule.component = automaticZenRule.getOwner();
            zenRule.pkg = str;
            z2 = true;
        } else {
            z2 = false;
        }
        if (!Objects.equals(zenRule.conditionId, automaticZenRule.getConditionId())) {
            zenRule.conditionId = automaticZenRule.getConditionId();
            z2 = true;
        }
        if (!android.app.Flags.modesApi()
                || !android.app.Flags.modesUi()
                || z
                || i != 3
                || zenRule.enabled != automaticZenRule.isEnabled()
                || (uri = zenRule.conditionId) == null
                || (condition = zenRule.condition) == null
                || !uri.equals(condition.id)) {
            zenRule.condition = null;
        }
        if (zenRule.enabled != automaticZenRule.isEnabled()) {
            zenRule.enabled = automaticZenRule.isEnabled();
            zenRule.snoozing = false;
            z2 = true;
        }
        if (!Objects.equals(
                zenRule.configurationActivity, automaticZenRule.getConfigurationActivity())) {
            zenRule.configurationActivity = automaticZenRule.getConfigurationActivity();
            z2 = true;
        }
        if (zenRule.allowManualInvocation != automaticZenRule.isManualInvocationAllowed()) {
            zenRule.allowManualInvocation = automaticZenRule.isManualInvocationAllowed();
            z2 = true;
        }
        if (!android.app.Flags.modesUi()) {
            String drawableResIdToResName =
                    drawableResIdToResName(automaticZenRule.getIconResId(), zenRule.pkg);
            if (!Objects.equals(zenRule.iconResName, drawableResIdToResName)) {
                zenRule.iconResName = drawableResIdToResName;
                z2 = true;
            }
        }
        if (!Objects.equals(zenRule.triggerDescription, automaticZenRule.getTriggerDescription())) {
            zenRule.triggerDescription = automaticZenRule.getTriggerDescription();
            z2 = true;
        }
        if (zenRule.type != automaticZenRule.getType()) {
            zenRule.type = automaticZenRule.getType();
            z2 = true;
        }
        zenRule.modified = automaticZenRule.isModified();
        String str2 = zenRule.name;
        if (z || i == 3 || i == 5 || (zenRule.userModifiedFields & 1) == 0) {
            zenRule.name = automaticZenRule.getName();
            z2 |= !Objects.equals(r5, str2);
        }
        if (!z && i != 3 && i != 5 && !zenRule.canBeUpdatedByApp()) {
            return z2;
        }
        boolean z4 = i == 3;
        if (z4 && !TextUtils.equals(str2, automaticZenRule.getName())) {
            zenRule.userModifiedFields |= 1;
        }
        int zenModeFromInterruptionFilter =
                NotificationManager.zenModeFromInterruptionFilter(
                        automaticZenRule.getInterruptionFilter(), 0);
        if (zenRule.zenMode != zenModeFromInterruptionFilter) {
            zenRule.zenMode = zenModeFromInterruptionFilter;
            if (z4) {
                zenRule.userModifiedFields |= 2;
            }
            z2 = true;
        }
        if (android.app.Flags.modesUi()) {
            String drawableResIdToResName2 =
                    drawableResIdToResName(automaticZenRule.getIconResId(), zenRule.pkg);
            if (!Objects.equals(zenRule.iconResName, drawableResIdToResName2)) {
                zenRule.iconResName = drawableResIdToResName2;
                if (z4) {
                    zenRule.userModifiedFields |= 4;
                }
                z2 = true;
            }
        }
        boolean updatePolicy = updatePolicy(zenRule, automaticZenRule.getZenPolicy(), z4, z) | z2;
        ZenDeviceEffects deviceEffects = automaticZenRule.getDeviceEffects();
        boolean z5 = i == 4;
        if (deviceEffects != null) {
            ZenDeviceEffects zenDeviceEffects = zenRule.zenDeviceEffects;
            if (zenDeviceEffects == null) {
                zenDeviceEffects = new ZenDeviceEffects.Builder().build();
            }
            if (z5) {
                deviceEffects =
                        new ZenDeviceEffects.Builder(deviceEffects)
                                .setShouldDisableAutoBrightness(
                                        zenDeviceEffects.shouldDisableAutoBrightness())
                                .setShouldDisableTapToWake(
                                        zenDeviceEffects.shouldDisableTapToWake())
                                .setShouldDisableTiltToWake(
                                        zenDeviceEffects.shouldDisableTiltToWake())
                                .setShouldDisableTouch(zenDeviceEffects.shouldDisableTouch())
                                .setShouldMinimizeRadioUsage(
                                        zenDeviceEffects.shouldMinimizeRadioUsage())
                                .setShouldMaximizeDoze(zenDeviceEffects.shouldMaximizeDoze())
                                .setExtraEffects(zenDeviceEffects.getExtraEffects())
                                .build();
            }
            zenRule.zenDeviceEffects = deviceEffects;
            if (z4) {
                int i2 = zenRule.zenDeviceEffectsUserModifiedFields;
                if (zenDeviceEffects.shouldDisplayGrayscale()
                        != deviceEffects.shouldDisplayGrayscale()) {
                    i2 |= 1;
                }
                if (zenDeviceEffects.shouldSuppressAmbientDisplay()
                        != deviceEffects.shouldSuppressAmbientDisplay()) {
                    i2 |= 2;
                }
                if (zenDeviceEffects.shouldDimWallpaper() != deviceEffects.shouldDimWallpaper()) {
                    i2 |= 4;
                }
                if (zenDeviceEffects.shouldUseNightMode() != deviceEffects.shouldUseNightMode()) {
                    i2 |= 8;
                }
                if (zenDeviceEffects.shouldDisableAutoBrightness()
                        != deviceEffects.shouldDisableAutoBrightness()) {
                    i2 |= 16;
                }
                if (zenDeviceEffects.shouldDisableTapToWake()
                        != deviceEffects.shouldDisableTapToWake()) {
                    i2 |= 32;
                }
                if (zenDeviceEffects.shouldDisableTiltToWake()
                        != deviceEffects.shouldDisableTiltToWake()) {
                    i2 |= 64;
                }
                if (zenDeviceEffects.shouldDisableTouch() != deviceEffects.shouldDisableTouch()) {
                    i2 |= 128;
                }
                if (zenDeviceEffects.shouldMinimizeRadioUsage()
                        != deviceEffects.shouldMinimizeRadioUsage()) {
                    i2 |= 256;
                }
                if (zenDeviceEffects.shouldMaximizeDoze() != deviceEffects.shouldMaximizeDoze()) {
                    i2 |= 512;
                }
                if (!Objects.equals(
                        zenDeviceEffects.getExtraEffects(), deviceEffects.getExtraEffects())) {
                    i2 |= 1024;
                }
                zenRule.zenDeviceEffectsUserModifiedFields = i2;
            }
            z3 = !deviceEffects.equals(zenDeviceEffects);
        }
        return updatePolicy | z3;
    }

    public final void ruleToProtoLocked(
            int i, ZenModeConfig.ZenRule zenRule, boolean z, List list) {
        int intValue;
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
        int i2 = zenRule.type;
        if (z) {
            str = "MANUAL_RULE";
            i2 = 999;
        }
        int i3 = i2;
        String str3 = str;
        byte[] bArr = new byte[0];
        ZenPolicy zenPolicy = zenRule.zenPolicy;
        if (zenPolicy != null) {
            bArr = zenPolicy.toProto();
        }
        byte[] bArr2 = bArr;
        boolean z2 = zenRule.enabled;
        int i4 = zenRule.zenMode;
        if ("android".equals(pkg)) {
            intValue = 1000;
        } else {
            String m = VpnManagerService$$ExternalSyntheticOutline0.m(i, pkg, "|");
            if (this.mRulesUidCache.get(m) == null) {
                try {
                    this.mRulesUidCache.put(
                            m, Integer.valueOf(this.mPm.getPackageUidAsUser(pkg, i)));
                } catch (PackageManager.NameNotFoundException unused) {
                }
            }
            intValue = ((Integer) this.mRulesUidCache.getOrDefault(m, -1)).intValue();
        }
        list.add(
                FrameworkStatsLog.buildStatsEvent(
                        FrameworkStatsLog.DND_MODE_RULE,
                        i,
                        z2,
                        false,
                        i4,
                        str3,
                        intValue,
                        bArr2,
                        zenRule.userModifiedFields,
                        zenRule.zenPolicyUserModifiedFields,
                        zenRule.zenDeviceEffectsUserModifiedFields,
                        i3));
    }

    public final void setAutomaticZenRuleStateLocked(
            ZenModeConfig zenModeConfig, List list, Condition condition, int i, int i2) {
        if (list == null || list.isEmpty()) {
            return;
        }
        if (android.app.Flags.modesApi() && condition.source == 1) {
            i = 3;
        }
        Iterator it = list.iterator();
        while (it.hasNext()) {
            ZenModeConfig.ZenRule zenRule = (ZenModeConfig.ZenRule) it.next();
            zenRule.condition = condition;
            if (zenRule.snoozing && !zenRule.isTrueOrUnknown()) {
                zenRule.snoozing = false;
                if (DEBUG) {
                    Log.d("ZenModeHelper", "Snoozing reset for " + zenRule.conditionId);
                }
            }
            setConfigLocked(zenModeConfig, i, "conditionChanged", zenRule.component, true, i2);
        }
    }

    public final boolean setConfigLocked(
            ZenModeConfig zenModeConfig,
            int i,
            String str,
            ComponentName componentName,
            boolean z,
            int i2) {
        long clearCallingIdentity = Binder.clearCallingIdentity();
        try {
            if (zenModeConfig != null) {
                if (zenModeConfig.isValid()) {
                    if (zenModeConfig.user != this.mUser) {
                        synchronized (this.mConfigsArrayLock) {
                            this.mConfigs.put(zenModeConfig.user, zenModeConfig);
                        }
                        if (DEBUG) {
                            Log.d(
                                    "ZenModeHelper",
                                    "setConfigLocked: store config for user " + zenModeConfig.user);
                        }
                        return true;
                    }
                    NotificationManager.Policy policy = null;
                    this.mConditions.evaluateConfig(zenModeConfig, null, false);
                    synchronized (this.mConfigsArrayLock) {
                        this.mConfigs.put(zenModeConfig.user, zenModeConfig);
                    }
                    if (DEBUG) {
                        Log.d("ZenModeHelper", "setConfigLocked reason=" + str, new Throwable());
                    }
                    ZenLog.traceConfig(str, componentName, this.mConfig, zenModeConfig, i2);
                    NotificationManager.Policy notificationPolicy =
                            zenModeConfig.toNotificationPolicy();
                    ZenModeConfig zenModeConfig2 = this.mConfig;
                    if (zenModeConfig2 != null) {
                        policy = zenModeConfig2.toNotificationPolicy();
                    }
                    boolean z2 = !Objects.equals(policy, notificationPolicy);
                    updateConfigAndZenModeLocked(zenModeConfig, i, str, z, i2);
                    if (z2) {
                        Iterator it = this.mCallbacks.iterator();
                        while (it.hasNext()) {
                            ((Callback) it.next()).onPolicyChanged(notificationPolicy);
                        }
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

    public final void setManualZenMode(int i, Uri uri, int i2, String str, String str2, int i3) {
        synchronized (this.mConfigLock) {
            try {
                if (this.mConfig != null) {
                    if (Settings.Global.isValidZenMode(i)) {
                        if (DEBUG) {
                            Log.d(
                                    "ZenModeHelper",
                                    "setManualZenMode "
                                            + Settings.Global.zenModeToString(i)
                                            + " conditionId="
                                            + uri
                                            + " reason="
                                            + str
                                            + " setRingerMode=true");
                        }
                        ZenModeConfig copy = this.mConfig.copy();
                        if (android.app.Flags.modesUi()) {
                            ZenModeConfig.ZenRule zenRule = copy.manualRule;
                            zenRule.enabler = str2;
                            if (uri == null) {
                                uri = Uri.EMPTY;
                            }
                            zenRule.conditionId = uri;
                            zenRule.pkg = "android";
                            zenRule.zenMode = i;
                            zenRule.condition =
                                    new Condition(
                                            copy.manualRule.conditionId,
                                            "",
                                            i == 0 ? 0 : 1,
                                            i2 == 3 ? 1 : 0);
                            if (i == 0 && i2 != 3) {
                                for (ZenModeConfig.ZenRule zenRule2 :
                                        copy.automaticRules.values()) {
                                    if (zenRule2.isAutomaticActive()) {
                                        zenRule2.snoozing = true;
                                    }
                                }
                            }
                        } else if (i == 0) {
                            copy.manualRule = null;
                            for (ZenModeConfig.ZenRule zenRule3 : copy.automaticRules.values()) {
                                if (zenRule3.isAutomaticActive()) {
                                    zenRule3.snoozing = true;
                                }
                            }
                        } else {
                            ZenModeConfig.ZenRule zenRule4 = new ZenModeConfig.ZenRule();
                            zenRule4.enabled = true;
                            zenRule4.zenMode = i;
                            zenRule4.conditionId = uri;
                            zenRule4.enabler = str2;
                            if (android.app.Flags.modesApi()) {
                                zenRule4.allowManualInvocation = true;
                            }
                            copy.manualRule = zenRule4;
                        }
                        setConfigLocked(copy, i2, str, null, true, i3);
                    }
                }
            } finally {
            }
        }
        Settings.Secure.putInt(
                this.mContext.getContentResolver(), "show_zen_settings_suggestion", 0);
    }

    public final void setNotificationPolicy(NotificationManager.Policy policy, int i, int i2) {
        ZenPolicy zenPolicy;
        synchronized (this.mConfigLock) {
            try {
                ZenModeConfig zenModeConfig = this.mConfig;
                if (zenModeConfig == null) {
                    return;
                }
                ZenModeConfig copy = zenModeConfig.copy();
                if (!android.app.Flags.modesApi() || android.app.Flags.modesUi()) {
                    copy.applyNotificationPolicy(policy);
                } else {
                    ZenPolicy notificationPolicyToZenPolicy =
                            ZenAdapters.notificationPolicyToZenPolicy(copy.toNotificationPolicy());
                    ZenPolicy notificationPolicyToZenPolicy2 =
                            ZenAdapters.notificationPolicyToZenPolicy(policy);
                    copy.applyNotificationPolicy(policy);
                    if (!notificationPolicyToZenPolicy.equals(notificationPolicyToZenPolicy2)) {
                        for (ZenModeConfig.ZenRule zenRule : copy.automaticRules.values()) {
                            if (!SystemZenRules.isSystemOwnedRule(zenRule)
                                    && zenRule.zenMode == 1
                                    && ((zenPolicy = zenRule.zenPolicy) == null
                                            || zenPolicy.equals(notificationPolicyToZenPolicy)
                                            || zenRule.zenPolicy.equals(getDefaultZenPolicy()))) {
                                zenRule.zenPolicy = notificationPolicyToZenPolicy2;
                            }
                        }
                    }
                }
                setConfigLocked(copy, i, "setNotificationPolicy", null, true, i2);
            } catch (Throwable th) {
                throw th;
            }
        }
    }

    public final void setPreviousRingerModeSetting(Integer num) {
        Settings.Global.putString(
                this.mContext.getContentResolver(),
                "zen_mode_ringer_level",
                num == null ? null : Integer.toString(num.intValue()));
    }

    public void setZenModeSetting(int i) {
        Settings.Global.putInt(this.mContext.getContentResolver(), "zen_mode", i);
        ZenLog.traceSetZenMode(
                Settings.Global.getInt(this.mContext.getContentResolver(), "zen_mode", -1),
                "updated setting");
        showZenUpgradeNotification(i);
    }

    /* JADX WARN: Code restructure failed: missing block: B:8:0x0032, code lost:

       if (android.provider.Settings.Secure.getInt(r5.mContext.getContentResolver(), "zen_settings_updated", 0) != 1) goto L12;
    */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public final void showZenUpgradeNotification(int r6) {
        /*
            r5 = this;
            android.content.Context r0 = r5.mContext
            android.content.pm.PackageManager r0 = r0.getPackageManager()
            java.lang.String r1 = "android.hardware.type.watch"
            boolean r0 = r0.hasSystemFeature(r1)
            boolean r1 = r5.mIsSystemServicesReady
            java.lang.String r2 = "show_zen_upgrade_notification"
            r3 = 0
            if (r1 == 0) goto L35
            if (r6 == 0) goto L35
            if (r0 != 0) goto L35
            android.content.Context r6 = r5.mContext
            android.content.ContentResolver r6 = r6.getContentResolver()
            int r6 = android.provider.Settings.Secure.getInt(r6, r2, r3)
            if (r6 == 0) goto L35
            android.content.Context r6 = r5.mContext
            android.content.ContentResolver r6 = r6.getContentResolver()
            java.lang.String r1 = "zen_settings_updated"
            int r6 = android.provider.Settings.Secure.getInt(r6, r1, r3)
            r1 = 1
            if (r6 == r1) goto L35
            goto L36
        L35:
            r1 = r3
        L36:
            if (r0 == 0) goto L41
            android.content.Context r6 = r5.mContext
            android.content.ContentResolver r6 = r6.getContentResolver()
            android.provider.Settings.Secure.putInt(r6, r2, r3)
        L41:
            if (r1 == 0) goto L59
            android.app.NotificationManager r6 = r5.mNotificationManager
            r0 = 48
            android.app.Notification r1 = r5.createZenUpgradeNotification()
            java.lang.String r4 = "ZenModeHelper"
            r6.notify(r4, r0, r1)
            android.content.Context r5 = r5.mContext
            android.content.ContentResolver r5 = r5.getContentResolver()
            android.provider.Settings.Secure.putInt(r5, r2, r3)
        L59:
            return
        */
        throw new UnsupportedOperationException(
                "Method not decompiled:"
                    + " com.android.server.notification.ZenModeHelper.showZenUpgradeNotification(int):void");
    }

    public final String toString() {
        return "ZenModeHelper";
    }

    public final void updateAndApplyConsolidatedPolicyAndDeviceEffects(int i, String str) {
        synchronized (this.mConfigLock) {
            try {
                if (this.mConfig == null) {
                    return;
                }
                ZenPolicy zenPolicy = new ZenPolicy();
                ZenDeviceEffects.Builder builder = new ZenDeviceEffects.Builder();
                if (this.mConfig.isManualActive()) {
                    applyCustomPolicy(zenPolicy, this.mConfig.manualRule, true);
                    if (android.app.Flags.modesApi()) {
                        builder.add(this.mConfig.manualRule.zenDeviceEffects);
                    }
                }
                for (ZenModeConfig.ZenRule zenRule : this.mConfig.automaticRules.values()) {
                    if (zenRule.isAutomaticActive()) {
                        if (!android.app.Flags.modesApi() || zenRule.zenMode != 0) {
                            applyCustomPolicy(zenPolicy, zenRule, false);
                        }
                        if (android.app.Flags.modesApi()) {
                            builder.add(zenRule.zenDeviceEffects);
                        }
                    }
                }
                NotificationManager.Policy notificationPolicy =
                        this.mConfig.toNotificationPolicy(zenPolicy);
                if (!Objects.equals(this.mConsolidatedPolicy, notificationPolicy)) {
                    this.mConsolidatedPolicy = notificationPolicy;
                    Iterator it = this.mCallbacks.iterator();
                    while (it.hasNext()) {
                        ((Callback) it.next()).onConsolidatedPolicyChanged(notificationPolicy);
                    }
                    NotificationManager.Policy policy = this.mConsolidatedPolicy;
                    LocalLog localLog = ZenLog.STATE_CHANGES;
                    ZenLog.append(17, policy.toString() + "," + str);
                }
                if (android.app.Flags.modesApi()) {
                    ZenDeviceEffects build = builder.build();
                    if (!build.equals(this.mConsolidatedDeviceEffects)) {
                        this.mConsolidatedDeviceEffects = build;
                        H h = this.mHandler;
                        int i2 = H.$r8$clinit;
                        h.removeMessages(6);
                        h.sendMessage(h.obtainMessage(6, i, 0));
                    }
                }
            } catch (Throwable th) {
                throw th;
            }
        }
    }

    /* JADX WARN: Removed duplicated region for block: B:100:0x0468  */
    /* JADX WARN: Removed duplicated region for block: B:103:0x046d  */
    /* JADX WARN: Removed duplicated region for block: B:117:0x045d  */
    /* JADX WARN: Removed duplicated region for block: B:120:0x02d3  */
    /* JADX WARN: Removed duplicated region for block: B:128:0x043d  */
    /* JADX WARN: Removed duplicated region for block: B:130:0x027d  */
    /* JADX WARN: Removed duplicated region for block: B:133:0x024d  */
    /* JADX WARN: Removed duplicated region for block: B:143:0x026b  */
    /* JADX WARN: Removed duplicated region for block: B:75:0x0236  */
    /* JADX WARN: Removed duplicated region for block: B:78:0x0242 A[ADDED_TO_REGION] */
    /* JADX WARN: Removed duplicated region for block: B:81:0x027a  */
    /* JADX WARN: Removed duplicated region for block: B:84:0x0298  */
    /* JADX WARN: Removed duplicated region for block: B:89:0x02bd  */
    /* JADX WARN: Removed duplicated region for block: B:94:0x0451  */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public final void updateConfigAndZenModeLocked(
            android.service.notification.ZenModeConfig r28,
            int r29,
            java.lang.String r30,
            boolean r31,
            int r32) {
        /*
            Method dump skipped, instructions count: 1230
            To view this dump change 'Code comments level' option to 'DEBUG'
        */
        throw new UnsupportedOperationException(
                "Method not decompiled:"
                    + " com.android.server.notification.ZenModeHelper.updateConfigAndZenModeLocked(android.service.notification.ZenModeConfig,"
                    + " int, java.lang.String, boolean, int):void");
    }

    public final void updateDefaultConfigAutomaticRules() {
        for (ZenModeConfig.ZenRule zenRule : this.mDefaultConfig.automaticRules.values()) {
            if ("EVENTS_DEFAULT_RULE".equals(zenRule.id)) {
                zenRule.name = this.mContext.getResources().getString(17043651);
            } else if ("EVERY_NIGHT_DEFAULT_RULE".equals(zenRule.id)) {
                zenRule.name = this.mContext.getResources().getString(17043652);
            }
            if (android.app.Flags.modesApi() && android.app.Flags.modesUi()) {
                SystemZenRules.updateTriggerDescription(this.mContext, zenRule);
            }
        }
    }

    public final boolean updatePolicy(
            ZenModeConfig.ZenRule zenRule, ZenPolicy zenPolicy, boolean z, boolean z2) {
        if (zenPolicy == null) {
            if (!z2) {
                return false;
            }
            zenRule.zenPolicy =
                    (android.app.Flags.modesUi() ? this.mDefaultConfig : this.mConfig)
                            .getZenPolicy();
            return true;
        }
        ZenPolicy zenPolicy2 = zenRule.zenPolicy;
        if (zenPolicy2 == null) {
            zenPolicy2 =
                    (android.app.Flags.modesUi() ? this.mDefaultConfig : this.mConfig)
                            .getZenPolicy();
        }
        ZenPolicy overwrittenWith = zenPolicy2.overwrittenWith(zenPolicy);
        zenRule.zenPolicy = overwrittenWith;
        if (z) {
            int i = zenRule.zenPolicyUserModifiedFields;
            if (zenPolicy2.getPriorityMessageSenders()
                    != overwrittenWith.getPriorityMessageSenders()) {
                i |= 1;
            }
            if (zenPolicy2.getPriorityCallSenders() != overwrittenWith.getPriorityCallSenders()) {
                i |= 2;
            }
            if (zenPolicy2.getPriorityConversationSenders()
                    != overwrittenWith.getPriorityConversationSenders()) {
                i |= 4;
            }
            if (zenPolicy2.getPriorityChannelsAllowed()
                    != overwrittenWith.getPriorityChannelsAllowed()) {
                i |= 8;
            }
            if (zenPolicy2.getPriorityCategoryReminders()
                    != overwrittenWith.getPriorityCategoryReminders()) {
                i |= 16;
            }
            if (zenPolicy2.getPriorityCategoryEvents()
                    != overwrittenWith.getPriorityCategoryEvents()) {
                i |= 32;
            }
            if (zenPolicy2.getPriorityCategoryRepeatCallers()
                    != overwrittenWith.getPriorityCategoryRepeatCallers()) {
                i |= 64;
            }
            if (zenPolicy2.getPriorityCategoryAlarms()
                    != overwrittenWith.getPriorityCategoryAlarms()) {
                i |= 128;
            }
            if (zenPolicy2.getPriorityCategoryMedia()
                    != overwrittenWith.getPriorityCategoryMedia()) {
                i |= 256;
            }
            if (zenPolicy2.getPriorityCategorySystem()
                    != overwrittenWith.getPriorityCategorySystem()) {
                i |= 512;
            }
            if (zenPolicy2.getVisualEffectFullScreenIntent()
                    != overwrittenWith.getVisualEffectFullScreenIntent()) {
                i |= 1024;
            }
            if (zenPolicy2.getVisualEffectLights() != overwrittenWith.getVisualEffectLights()) {
                i |= 2048;
            }
            if (zenPolicy2.getVisualEffectPeek() != overwrittenWith.getVisualEffectPeek()) {
                i |= 4096;
            }
            if (zenPolicy2.getVisualEffectStatusBar()
                    != overwrittenWith.getVisualEffectStatusBar()) {
                i |= 8192;
            }
            if (zenPolicy2.getVisualEffectBadge() != overwrittenWith.getVisualEffectBadge()) {
                i |= EndpointMonitorConst.FLAG_TRACING_PROCESS_PERMISSIONS_MODIFICATION;
            }
            if (zenPolicy2.getVisualEffectAmbient() != overwrittenWith.getVisualEffectAmbient()) {
                i |= 32768;
            }
            if (zenPolicy2.getVisualEffectNotificationList()
                    != overwrittenWith.getVisualEffectNotificationList()) {
                i |= EndpointMonitorConst.FLAG_TRACING_NETWORK_EVENT_ABNORMAL_PKT;
            }
            zenRule.zenPolicyUserModifiedFields = i;
        }
        return !overwrittenWith.equals(zenPolicy2);
    }

    public final AutomaticZenRule zenRuleToAutomaticZenRule(ZenModeConfig.ZenRule zenRule) {
        if (!android.app.Flags.modesApi()) {
            AutomaticZenRule automaticZenRule =
                    new AutomaticZenRule(
                            zenRule.name,
                            zenRule.component,
                            zenRule.configurationActivity,
                            zenRule.conditionId,
                            zenRule.zenPolicy,
                            NotificationManager.zenModeToInterruptionFilter(zenRule.zenMode),
                            zenRule.enabled,
                            zenRule.creationTime);
            automaticZenRule.setPackageName(zenRule.pkg);
            return automaticZenRule;
        }
        AutomaticZenRule.Builder creationTime =
                new AutomaticZenRule.Builder(zenRule.name, zenRule.conditionId)
                        .setManualInvocationAllowed(zenRule.allowManualInvocation)
                        .setPackage(zenRule.pkg)
                        .setCreationTime(zenRule.creationTime);
        String str = zenRule.pkg;
        String str2 = zenRule.iconResName;
        int i = 0;
        if (!TextUtils.isEmpty(str2)) {
            try {
                i = this.mPm.getResourcesForApplication(str).getIdentifier(str2, null, null);
            } catch (PackageManager.NameNotFoundException e) {
                Slog.w("ZenModeHelper", "cannot load rule icon for pkg", e);
            }
        }
        return creationTime
                .setIconResId(i)
                .setType(zenRule.type)
                .setZenPolicy(zenRule.zenPolicy)
                .setDeviceEffects(zenRule.zenDeviceEffects)
                .setEnabled(zenRule.enabled)
                .setInterruptionFilter(
                        NotificationManager.zenModeToInterruptionFilter(zenRule.zenMode))
                .setOwner(zenRule.component)
                .setConfigurationActivity(zenRule.configurationActivity)
                .setTriggerDescription(zenRule.triggerDescription)
                .build();
    }
}
