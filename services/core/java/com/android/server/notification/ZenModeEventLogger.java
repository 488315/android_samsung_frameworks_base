package com.android.server.notification;

import android.app.NotificationManager;
import android.content.pm.PackageManager;
import android.service.notification.ZenModeConfig;
import android.service.notification.ZenModeDiff;
import android.util.ArrayMap;
import android.util.Log;
import android.util.Pair;
import android.util.Slog;
import android.util.proto.ProtoOutputStream;
import com.android.internal.logging.UiEventLogger;
import com.android.internal.util.FrameworkStatsLog;
import java.io.ByteArrayOutputStream;
import java.util.Objects;

/* loaded from: classes2.dex */
public class ZenModeEventLogger {
    public ZenStateChanges mChangeState = new ZenStateChanges();
    public PackageManager mPm;

    public ZenModeEventLogger(PackageManager packageManager) {
        this.mPm = packageManager;
    }

    enum ZenStateChangedEvent implements UiEventLogger.UiEventEnum {
        DND_TURNED_ON(1368),
        DND_TURNED_OFF(1369),
        DND_POLICY_CHANGED(1370),
        DND_ACTIVE_RULES_CHANGED(1371);

        private final int mId;

        ZenStateChangedEvent(int i) {
            this.mId = i;
        }

        public int getId() {
            return this.mId;
        }
    }

    public final void maybeLogZenChange(ZenModeInfo zenModeInfo, ZenModeInfo zenModeInfo2, int i, boolean z) {
        this.mChangeState.init(zenModeInfo, zenModeInfo2, i, z);
        if (this.mChangeState.shouldLogChanges()) {
            maybeReassignCallingUid();
            logChanges();
        }
        this.mChangeState = new ZenStateChanges();
    }

    public final void maybeReassignCallingUid() {
        String str;
        int i;
        Pair rulePackageAndUser;
        if (this.mChangeState.getChangedRuleType() == 1) {
            ZenStateChanges zenStateChanges = this.mChangeState;
            if (!zenStateChanges.mFromSystemOrSystemUi || zenStateChanges.getNewManualRuleEnabler() == null) {
                return;
            }
            str = this.mChangeState.getNewManualRuleEnabler();
            i = this.mChangeState.mNewConfig.user;
        } else {
            str = null;
            i = -1;
        }
        if (this.mChangeState.getChangedRuleType() == 2) {
            if (this.mChangeState.getIsUserAction()) {
                return;
            }
            ZenStateChanges zenStateChanges2 = this.mChangeState;
            if (!zenStateChanges2.mFromSystemOrSystemUi) {
                return;
            }
            ArrayMap changedAutomaticRules = zenStateChanges2.getChangedAutomaticRules();
            if (changedAutomaticRules.size() != 1 || (rulePackageAndUser = this.mChangeState.getRulePackageAndUser((String) changedAutomaticRules.keyAt(0), (ZenModeDiff.RuleDiff) changedAutomaticRules.valueAt(0))) == null || ((String) rulePackageAndUser.first).equals("android")) {
                return;
            }
            String str2 = (String) rulePackageAndUser.first;
            i = ((Integer) rulePackageAndUser.second).intValue();
            str = str2;
        }
        if (i == -1 || str == null) {
            return;
        }
        try {
            this.mChangeState.mCallingUid = this.mPm.getPackageUidAsUser(str, i);
        } catch (PackageManager.NameNotFoundException unused) {
            Slog.e("ZenModeEventLogger", "unable to find package name " + str + " " + i);
        }
    }

    public void logChanges() {
        int id = this.mChangeState.getEventId().getId();
        ZenStateChanges zenStateChanges = this.mChangeState;
        FrameworkStatsLog.write(FrameworkStatsLog.DND_STATE_CHANGED, id, zenStateChanges.mNewZenMode, zenStateChanges.mPrevZenMode, zenStateChanges.getChangedRuleType(), this.mChangeState.getNumRulesActive(), this.mChangeState.getIsUserAction(), this.mChangeState.getPackageUid(), this.mChangeState.getDNDPolicyProto(), this.mChangeState.getAreChannelsBypassing());
    }

    public class ZenModeInfo {
        public final ZenModeConfig mConfig;
        public final NotificationManager.Policy mPolicy;
        public final int mZenMode;

        public ZenModeInfo(int i, ZenModeConfig zenModeConfig, NotificationManager.Policy policy) {
            this.mZenMode = i;
            this.mConfig = zenModeConfig != null ? zenModeConfig.copy() : null;
            this.mPolicy = policy != null ? policy.copy() : null;
        }
    }

    public class ZenStateChanges {
        public ZenModeConfig mNewConfig;
        public NotificationManager.Policy mNewPolicy;
        public ZenModeConfig mPrevConfig;
        public NotificationManager.Policy mPrevPolicy;
        public int mPrevZenMode = -1;
        public int mNewZenMode = -1;
        public int mCallingUid = -1;
        public boolean mFromSystemOrSystemUi = false;

        public final int toState(boolean z) {
            return z ? 1 : 2;
        }

        public final void init(ZenModeInfo zenModeInfo, ZenModeInfo zenModeInfo2, int i, boolean z) {
            this.mPrevZenMode = zenModeInfo.mZenMode;
            this.mNewZenMode = zenModeInfo2.mZenMode;
            this.mPrevConfig = zenModeInfo.mConfig;
            this.mNewConfig = zenModeInfo2.mConfig;
            this.mPrevPolicy = zenModeInfo.mPolicy;
            this.mNewPolicy = zenModeInfo2.mPolicy;
            this.mCallingUid = i;
            this.mFromSystemOrSystemUi = z;
        }

        public final boolean hasPolicyDiff() {
            NotificationManager.Policy policy = this.mPrevPolicy;
            return (policy == null || Objects.equals(policy, this.mNewPolicy)) ? false : true;
        }

        public final boolean shouldLogChanges() {
            if (zenModeFlipped()) {
                return true;
            }
            if (this.mNewZenMode == 0) {
                return false;
            }
            return hasPolicyDiff() || hasRuleCountDiff();
        }

        public final boolean zenModeFlipped() {
            int i = this.mPrevZenMode;
            int i2 = this.mNewZenMode;
            if (i == i2) {
                return false;
            }
            return i == 0 || i2 == 0;
        }

        public ZenStateChangedEvent getEventId() {
            if (!shouldLogChanges()) {
                Log.wtf("ZenModeEventLogger", "attempt to get DNDStateChanged fields without shouldLog=true");
            }
            if (zenModeFlipped()) {
                if (this.mPrevZenMode == 0) {
                    return ZenStateChangedEvent.DND_TURNED_ON;
                }
                return ZenStateChangedEvent.DND_TURNED_OFF;
            }
            if (hasPolicyDiff() || hasChannelsBypassingDiff()) {
                return ZenStateChangedEvent.DND_POLICY_CHANGED;
            }
            return ZenStateChangedEvent.DND_ACTIVE_RULES_CHANGED;
        }

        public int getChangedRuleType() {
            ZenModeDiff.ConfigDiff configDiff = new ZenModeDiff.ConfigDiff(this.mPrevConfig, this.mNewConfig);
            if (!configDiff.hasDiff()) {
                return 0;
            }
            ZenModeDiff.RuleDiff manualRuleDiff = configDiff.getManualRuleDiff();
            if (manualRuleDiff != null && manualRuleDiff.hasDiff() && (manualRuleDiff.wasAdded() || manualRuleDiff.wasRemoved())) {
                return 1;
            }
            ArrayMap allAutomaticRuleDiffs = configDiff.getAllAutomaticRuleDiffs();
            if (allAutomaticRuleDiffs != null) {
                for (ZenModeDiff.RuleDiff ruleDiff : allAutomaticRuleDiffs.values()) {
                    if (ruleDiff != null && ruleDiff.hasDiff() && (ruleDiff.becameActive() || ruleDiff.becameInactive())) {
                        return 2;
                    }
                }
            }
            return 0;
        }

        public final boolean hasRuleCountDiff() {
            return numActiveRulesInConfig(this.mPrevConfig) != numActiveRulesInConfig(this.mNewConfig);
        }

        public int numActiveRulesInConfig(ZenModeConfig zenModeConfig) {
            if (zenModeConfig == null) {
                return 0;
            }
            int i = zenModeConfig.manualRule != null ? 1 : 0;
            ArrayMap arrayMap = zenModeConfig.automaticRules;
            if (arrayMap != null) {
                for (ZenModeConfig.ZenRule zenRule : arrayMap.values()) {
                    if (zenRule != null && zenRule.isAutomaticActive()) {
                        i++;
                    }
                }
            }
            return i;
        }

        public int getNumRulesActive() {
            if (this.mNewZenMode == 0) {
                return 0;
            }
            return numActiveRulesInConfig(this.mNewConfig);
        }

        public boolean getIsUserAction() {
            int changedRuleType = getChangedRuleType();
            if (changedRuleType == 1) {
                return this.mFromSystemOrSystemUi && getNewManualRuleEnabler() == null;
            }
            if (changedRuleType != 2) {
                return (hasPolicyDiff() || hasChannelsBypassingDiff()) && this.mCallingUid == 1000;
            }
            for (ZenModeDiff.RuleDiff ruleDiff : getChangedAutomaticRules().values()) {
                if (ruleDiff.wasAdded() || ruleDiff.wasRemoved()) {
                    return this.mFromSystemOrSystemUi;
                }
                ZenModeDiff.FieldDiff diffForField = ruleDiff.getDiffForField("enabled");
                if (diffForField != null && diffForField.hasDiff()) {
                    return true;
                }
                ZenModeDiff.FieldDiff diffForField2 = ruleDiff.getDiffForField("snoozing");
                if (diffForField2 != null && diffForField2.hasDiff() && ((Boolean) diffForField2.to()).booleanValue()) {
                    return true;
                }
            }
            return false;
        }

        public int getPackageUid() {
            return this.mCallingUid;
        }

        public byte[] getDNDPolicyProto() {
            ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
            ProtoOutputStream protoOutputStream = new ProtoOutputStream(byteArrayOutputStream);
            NotificationManager.Policy policy = this.mNewPolicy;
            if (policy != null) {
                protoOutputStream.write(1159641169921L, toState(policy.allowCalls()));
                protoOutputStream.write(1159641169922L, toState(this.mNewPolicy.allowRepeatCallers()));
                protoOutputStream.write(1159641169923L, toState(this.mNewPolicy.allowMessages()));
                protoOutputStream.write(1159641169924L, toState(this.mNewPolicy.allowConversations()));
                protoOutputStream.write(1159641169925L, toState(this.mNewPolicy.allowReminders()));
                protoOutputStream.write(1159641169926L, toState(this.mNewPolicy.allowEvents()));
                protoOutputStream.write(1159641169927L, toState(this.mNewPolicy.allowAlarms()));
                protoOutputStream.write(1159641169928L, toState(this.mNewPolicy.allowMedia()));
                protoOutputStream.write(1159641169929L, toState(this.mNewPolicy.allowSystem()));
                protoOutputStream.write(1159641169930L, toState(this.mNewPolicy.showFullScreenIntents()));
                protoOutputStream.write(1159641169931L, toState(this.mNewPolicy.showLights()));
                protoOutputStream.write(1159641169932L, toState(this.mNewPolicy.showPeeking()));
                protoOutputStream.write(1159641169933L, toState(this.mNewPolicy.showStatusBarIcons()));
                protoOutputStream.write(1159641169934L, toState(this.mNewPolicy.showBadges()));
                protoOutputStream.write(1159641169935L, toState(this.mNewPolicy.showAmbient()));
                protoOutputStream.write(1159641169936L, toState(this.mNewPolicy.showInNotificationList()));
                protoOutputStream.write(1159641169937L, ZenModeConfig.getZenPolicySenders(this.mNewPolicy.allowCallsFrom()));
                protoOutputStream.write(1159641169938L, ZenModeConfig.getZenPolicySenders(this.mNewPolicy.allowMessagesFrom()));
                protoOutputStream.write(1159641169939L, this.mNewPolicy.allowConversationsFrom());
            } else {
                Log.wtf("ZenModeEventLogger", "attempted to write zen mode log event with null policy");
            }
            protoOutputStream.flush();
            return byteArrayOutputStream.toByteArray();
        }

        public boolean getAreChannelsBypassing() {
            NotificationManager.Policy policy = this.mNewPolicy;
            return (policy == null || (policy.state & 1) == 0) ? false : true;
        }

        public final boolean hasChannelsBypassingDiff() {
            NotificationManager.Policy policy = this.mPrevPolicy;
            return (policy != null && (policy.state & 1) != 0) != getAreChannelsBypassing();
        }

        public final ArrayMap getChangedAutomaticRules() {
            ArrayMap allAutomaticRuleDiffs;
            ArrayMap arrayMap = new ArrayMap();
            ZenModeDiff.ConfigDiff configDiff = new ZenModeDiff.ConfigDiff(this.mPrevConfig, this.mNewConfig);
            return (configDiff.hasDiff() && (allAutomaticRuleDiffs = configDiff.getAllAutomaticRuleDiffs()) != null) ? allAutomaticRuleDiffs : arrayMap;
        }

        public final Pair getRulePackageAndUser(String str, ZenModeDiff.RuleDiff ruleDiff) {
            ZenModeConfig.ZenRule zenRule;
            ZenModeConfig zenModeConfig = this.mNewConfig;
            if (ruleDiff.wasRemoved()) {
                zenModeConfig = this.mPrevConfig;
            }
            if (zenModeConfig == null || (zenRule = (ZenModeConfig.ZenRule) zenModeConfig.automaticRules.getOrDefault(str, null)) == null) {
                return null;
            }
            if (zenRule.component != null) {
                return new Pair(zenRule.component.getPackageName(), Integer.valueOf(zenModeConfig.user));
            }
            if (zenRule.configurationActivity != null) {
                return new Pair(zenRule.configurationActivity.getPackageName(), Integer.valueOf(zenModeConfig.user));
            }
            return null;
        }

        public final String getNewManualRuleEnabler() {
            ZenModeConfig.ZenRule zenRule;
            ZenModeConfig zenModeConfig = this.mNewConfig;
            if (zenModeConfig == null || (zenRule = zenModeConfig.manualRule) == null) {
                return null;
            }
            return zenRule.enabler;
        }
    }
}
