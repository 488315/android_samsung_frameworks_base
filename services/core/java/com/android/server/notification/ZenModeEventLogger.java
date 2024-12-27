package com.android.server.notification;

import android.app.NotificationManager;
import android.content.pm.PackageManager;
import android.service.notification.ZenModeConfig;
import android.service.notification.ZenModeDiff;
import android.util.ArrayMap;

import com.android.internal.logging.UiEventLogger;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

/* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
/* loaded from: classes2.dex */
public final class ZenModeEventLogger {
    public ZenStateChanges mChangeState = new ZenStateChanges();
    public final PackageManager mPm;

    /* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
    enum ZenStateChangedEvent implements UiEventLogger.UiEventEnum {
        DND_TURNED_ON("DND_TURNED_ON"),
        DND_TURNED_OFF("DND_TURNED_OFF"),
        DND_POLICY_CHANGED("DND_POLICY_CHANGED"),
        DND_ACTIVE_RULES_CHANGED("DND_ACTIVE_RULES_CHANGED");

        private final int mId;

        ZenStateChangedEvent(String str) {
            this.mId = r2;
        }

        public final int getId() {
            return this.mId;
        }
    }

    /* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
    public final class ZenStateChanges {
        public ZenModeConfig mNewConfig;
        public NotificationManager.Policy mNewPolicy;
        public ZenModeConfig mPrevConfig;
        public NotificationManager.Policy mPrevPolicy;
        public int mPrevZenMode = -1;
        public int mNewZenMode = -1;
        public int mCallingUid = -1;
        public int mOrigin = 0;

        public static List activeRulesList(ZenModeConfig zenModeConfig) {
            ArrayList arrayList = new ArrayList();
            if (zenModeConfig == null) {
                return arrayList;
            }
            if (zenModeConfig.isManualActive()) {
                ZenModeConfig.ZenRule copy = zenModeConfig.manualRule.copy();
                copy.type = 999;
                arrayList.add(copy);
            }
            ArrayMap arrayMap = zenModeConfig.automaticRules;
            if (arrayMap != null) {
                for (ZenModeConfig.ZenRule zenRule : arrayMap.values()) {
                    if (zenRule != null && zenRule.isAutomaticActive()) {
                        arrayList.add(zenRule);
                    }
                }
            }
            return arrayList;
        }

        public static int toState(boolean z) {
            return z ? 1 : 2;
        }

        public final int getChangedRuleType() {
            ZenModeDiff.ConfigDiff configDiff =
                    new ZenModeDiff.ConfigDiff(this.mPrevConfig, this.mNewConfig);
            if (!configDiff.hasDiff()) {
                return 0;
            }
            ZenModeDiff.RuleDiff manualRuleDiff = configDiff.getManualRuleDiff();
            if (manualRuleDiff != null && manualRuleDiff.hasDiff()) {
                if (manualRuleDiff.wasAdded() || manualRuleDiff.wasRemoved()) {
                    return 1;
                }
                if (android.app.Flags.modesUi()
                        && (manualRuleDiff.becameActive() || manualRuleDiff.becameInactive())) {
                    return 1;
                }
            }
            ArrayMap allAutomaticRuleDiffs = configDiff.getAllAutomaticRuleDiffs();
            if (allAutomaticRuleDiffs != null) {
                for (ZenModeDiff.RuleDiff ruleDiff : allAutomaticRuleDiffs.values()) {
                    if (ruleDiff != null
                            && ruleDiff.hasDiff()
                            && (ruleDiff.becameActive() || ruleDiff.becameInactive())) {
                        return 2;
                    }
                }
            }
            return 0;
        }

        public final boolean getIsUserAction() {
            ZenModeConfig.ZenRule zenRule;
            ArrayMap allAutomaticRuleDiffs;
            if (android.app.Flags.modesApi()) {
                return this.mOrigin == 3;
            }
            int changedRuleType = getChangedRuleType();
            if (changedRuleType == 1) {
                if (!isFromSystemOrSystemUi()) {
                    return false;
                }
                ZenModeConfig zenModeConfig = this.mNewConfig;
                return ((zenModeConfig == null || (zenRule = zenModeConfig.manualRule) == null)
                                ? null
                                : zenRule.enabler)
                        == null;
            }
            if (changedRuleType != 2) {
                return (hasPolicyDiff() || hasChannelsBypassingDiff()) && this.mCallingUid == 1000;
            }
            ArrayMap arrayMap = new ArrayMap();
            ZenModeDiff.ConfigDiff configDiff =
                    new ZenModeDiff.ConfigDiff(this.mPrevConfig, this.mNewConfig);
            if (configDiff.hasDiff()
                    && (allAutomaticRuleDiffs = configDiff.getAllAutomaticRuleDiffs()) != null) {
                arrayMap = allAutomaticRuleDiffs;
            }
            for (ZenModeDiff.RuleDiff ruleDiff : arrayMap.values()) {
                if (ruleDiff.wasAdded() || ruleDiff.wasRemoved()) {
                    return isFromSystemOrSystemUi();
                }
                ZenModeDiff.FieldDiff diffForField = ruleDiff.getDiffForField("enabled");
                if (diffForField != null && diffForField.hasDiff()) {
                    return true;
                }
                ZenModeDiff.FieldDiff diffForField2 = ruleDiff.getDiffForField("snoozing");
                if (diffForField2 != null
                        && diffForField2.hasDiff()
                        && ((Boolean) diffForField2.to()).booleanValue()) {
                    return true;
                }
            }
            return false;
        }

        public final boolean hasChannelsBypassingDiff() {
            NotificationManager.Policy policy = this.mPrevPolicy;
            boolean z = (policy == null || (policy.state & 1) == 0) ? false : true;
            NotificationManager.Policy policy2 = this.mNewPolicy;
            return z != (policy2 != null && (policy2.state & 1) != 0);
        }

        public final boolean hasPolicyDiff() {
            NotificationManager.Policy policy = this.mPrevPolicy;
            return (policy == null || Objects.equals(policy, this.mNewPolicy)) ? false : true;
        }

        public final boolean isFromSystemOrSystemUi() {
            int i = this.mOrigin;
            return i == 1 || i == 2 || i == 5 || i == 6;
        }

        public final boolean shouldLogChanges() {
            int i = this.mPrevZenMode;
            int i2 = this.mNewZenMode;
            if (i != i2 && (i == 0 || i2 == 0)) {
                return true;
            }
            if (android.app.Flags.modesApi()
                    && ((ArrayList) activeRulesList(this.mPrevConfig)).size()
                            != ((ArrayList) activeRulesList(this.mNewConfig)).size()) {
                return true;
            }
            if (this.mNewZenMode == 0) {
                return false;
            }
            return hasPolicyDiff()
                    || ((ArrayList) activeRulesList(this.mPrevConfig)).size()
                            != ((ArrayList) activeRulesList(this.mNewConfig)).size();
        }
    }

    public ZenModeEventLogger(PackageManager packageManager) {
        this.mPm = packageManager;
    }
}
