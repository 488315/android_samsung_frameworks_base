package com.android.server.notification;

import android.content.ComponentName;
import android.net.Uri;
import android.service.notification.IConditionProvider;
import android.service.notification.ZenModeConfig;
import android.util.ArrayMap;
import android.util.ArraySet;
import android.util.Log;
import android.util.Slog;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Iterator;

/* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
/* loaded from: classes2.dex */
public final class ZenModeConditions implements ConditionProviders.Callback {
    public static final boolean DEBUG = ZenModeHelper.DEBUG;
    public final ConditionProviders mConditionProviders;
    public final ZenModeHelper mHelper;
    protected final ArrayMap mSubscriptions = new ArrayMap();
    public boolean mFirstEvaluation = true;
    public final HashSet mEvaluatedUsers = new HashSet();

    public ZenModeConditions(ZenModeHelper zenModeHelper, ConditionProviders conditionProviders) {
        this.mHelper = zenModeHelper;
        this.mConditionProviders = conditionProviders;
        if (conditionProviders.mSystemConditionProviderNames.contains("countdown")) {
            conditionProviders.addSystemProvider(new CountdownConditionProvider());
        }
        if (conditionProviders.mSystemConditionProviderNames.contains("schedule")) {
            conditionProviders.addSystemProvider(new ScheduleConditionProvider());
        }
        if (conditionProviders.mSystemConditionProviderNames.contains("event")) {
            conditionProviders.addSystemProvider(new EventConditionProvider());
        }
        conditionProviders.mCallback = this;
    }

    public final void evaluateConfig(
            ZenModeConfig zenModeConfig, ComponentName componentName, boolean z) {
        ZenModeConfig.ZenRule zenRule;
        if (zenModeConfig == null) {
            return;
        }
        if (!android.app.Flags.modesUi()
                && (zenRule = zenModeConfig.manualRule) != null
                && zenRule.condition != null
                && !zenRule.isTrueOrUnknown()) {
            if (DEBUG) {
                Log.d("ZenModeHelper", "evaluateConfig: clearing manual rule");
            }
            zenModeConfig.manualRule = null;
        }
        ArraySet arraySet = new ArraySet();
        evaluateRule(zenModeConfig.manualRule, arraySet, null, z, true);
        for (ZenModeConfig.ZenRule zenRule2 : zenModeConfig.automaticRules.values()) {
            if (zenRule2.component != null) {
                evaluateRule(zenRule2, arraySet, componentName, z, false);
                int i = zenModeConfig.user;
                if (!zenRule2.snoozing || (!this.mFirstEvaluation && zenRule2.isTrueOrUnknown())) {
                    Iterator it = this.mEvaluatedUsers.iterator();
                    while (it.hasNext()) {
                        if (i == ((Integer) it.next()).intValue()) {
                            break;
                        }
                    }
                }
                zenRule2.snoozing = false;
                Log.d("ZenModeHelper", "Snoozing reset for " + zenRule2.conditionId);
            }
        }
        synchronized (this.mSubscriptions) {
            try {
                for (int size = this.mSubscriptions.size() - 1; size >= 0; size--) {
                    Uri uri = (Uri) this.mSubscriptions.keyAt(size);
                    ComponentName componentName2 =
                            (ComponentName) this.mSubscriptions.valueAt(size);
                    if (z && !arraySet.contains(uri)) {
                        this.mConditionProviders.unsubscribeIfNecessary(componentName2, uri);
                        this.mSubscriptions.removeAt(size);
                    }
                }
            } catch (Throwable th) {
                throw th;
            }
        }
        this.mFirstEvaluation = false;
        this.mEvaluatedUsers.add(new Integer(zenModeConfig.user));
    }

    public final void evaluateRule(
            ZenModeConfig.ZenRule zenRule,
            ArraySet arraySet,
            ComponentName componentName,
            boolean z,
            boolean z2) {
        Uri uri;
        IConditionProvider iConditionProvider;
        if (zenRule == null
                || (uri = zenRule.conditionId) == null
                || zenRule.configurationActivity != null) {
            return;
        }
        Iterator it = this.mConditionProviders.mSystemConditionProviders.iterator();
        boolean z3 = true;
        boolean z4 = false;
        boolean z5 = true;
        while (it.hasNext()) {
            SystemConditionProviderService systemConditionProviderService =
                    (SystemConditionProviderService) it.next();
            if (systemConditionProviderService.isValidConditionId(uri)) {
                if (systemConditionProviderService
                                .getComponent()
                                .getClassName()
                                .equals("com.android.server.notification.ScheduleConditionProvider")
                        && z) {
                    ZenModeConfig config = this.mHelper.getConfig();
                    String str = zenRule.id;
                    if (str == null
                            || !str.equals("EVERY_NIGHT_DEFAULT_RULE")
                            || config.automaticRules.size() != 1
                            || zenRule.enabled) {
                        z5 = systemConditionProviderService.isScheduleEnabled();
                        systemConditionProviderService.onScheduleEnabled(true);
                    } else {
                        z5 = !systemConditionProviderService.isScheduleEnabled();
                        systemConditionProviderService.onScheduleEnabled(false);
                    }
                }
                this.mConditionProviders.ensureRecordExists(
                        systemConditionProviderService.getComponent(),
                        uri,
                        systemConditionProviderService.asInterface());
                zenRule.component = systemConditionProviderService.getComponent();
                z4 = true;
            }
        }
        if (!z4) {
            ConditionProviders conditionProviders = this.mConditionProviders;
            ComponentName componentName2 = zenRule.component;
            if (componentName2 == null) {
                conditionProviders.getClass();
            } else {
                Iterator it2 = ((ArrayList) conditionProviders.getServices()).iterator();
                while (it2.hasNext()) {
                    ManagedServices.ManagedServiceInfo managedServiceInfo =
                            (ManagedServices.ManagedServiceInfo) it2.next();
                    if (componentName2.equals(managedServiceInfo.component)) {
                        iConditionProvider = managedServiceInfo.service;
                        break;
                    }
                }
            }
            iConditionProvider = null;
            if (DEBUG) {
                StringBuilder sb = new StringBuilder("Ensure external rule exists: ");
                sb.append(iConditionProvider != null);
                sb.append(" for ");
                sb.append(uri);
                Log.d("ZenModeHelper", sb.toString());
            }
            if (iConditionProvider != null) {
                this.mConditionProviders.ensureRecordExists(
                        zenRule.component, uri, iConditionProvider);
            }
        }
        if (zenRule.component == null && zenRule.enabler == null) {
            if (!android.app.Flags.modesUi() || (android.app.Flags.modesUi() && !z2)) {
                Log.w(
                        "ZenModeHelper",
                        "No component found for automatic rule: " + zenRule.conditionId);
                zenRule.enabled = false;
                return;
            }
            return;
        }
        arraySet.add(uri);
        if (z && ((componentName != null && componentName.equals(zenRule.component)) || z4)) {
            boolean z6 = DEBUG;
            if (z6) {
                Log.d("ZenModeHelper", "Subscribing to " + zenRule.component);
            }
            ConditionProviders conditionProviders2 = this.mConditionProviders;
            ComponentName componentName3 = zenRule.component;
            Uri uri2 = zenRule.conditionId;
            synchronized (conditionProviders2.mMutex) {
                try {
                    ConditionProviders.ConditionRecord recordLocked =
                            conditionProviders2.getRecordLocked(uri2, componentName3, false);
                    if (recordLocked == null) {
                        Slog.w(
                                conditionProviders2.TAG,
                                "Unable to subscribe to " + componentName3 + " " + uri2);
                        z3 = false;
                    } else if (!recordLocked.subscribed || !z5) {
                        conditionProviders2.subscribeLocked(recordLocked);
                        z3 = recordLocked.subscribed;
                    }
                } finally {
                }
            }
            if (z3) {
                synchronized (this.mSubscriptions) {
                    this.mSubscriptions.put(zenRule.conditionId, zenRule.component);
                }
            } else {
                zenRule.condition = null;
                if (z6) {
                    Log.d("ZenModeHelper", "zmc failed to subscribe");
                }
            }
        }
        ComponentName componentName4 = zenRule.component;
        if (componentName4 == null || zenRule.condition != null) {
            return;
        }
        ConditionProviders conditionProviders3 = this.mConditionProviders;
        Uri uri3 = zenRule.conditionId;
        conditionProviders3.getClass();
        if (uri3 != null) {
            synchronized (conditionProviders3.mMutex) {
                try {
                    ConditionProviders.ConditionRecord recordLocked2 =
                            conditionProviders3.getRecordLocked(uri3, componentName4, false);
                    r1 = recordLocked2 != null ? recordLocked2.condition : null;
                } finally {
                }
            }
        }
        zenRule.condition = r1;
        if (r1 == null || !DEBUG) {
            return;
        }
        Log.d("ZenModeHelper", "Found existing condition for: " + zenRule.conditionId);
    }
}
