package com.android.server.notification;

import android.content.ComponentName;
import android.net.Uri;
import android.os.Binder;
import android.service.notification.Condition;
import android.service.notification.IConditionProvider;
import android.service.notification.ZenModeConfig;
import android.util.ArrayMap;
import android.util.ArraySet;
import android.util.Log;
import java.io.PrintWriter;
import java.util.HashSet;
import java.util.Iterator;

/* loaded from: classes2.dex */
public class ZenModeConditions implements ConditionProviders.Callback {
  public static final boolean DEBUG = ZenModeHelper.DEBUG;
  public final ConditionProviders mConditionProviders;
  public final ZenModeHelper mHelper;
  protected final ArrayMap mSubscriptions = new ArrayMap();
  public boolean mFirstEvaluation = true;
  public HashSet mEvaluatedUsers = new HashSet();

  @Override // com.android.server.notification.ConditionProviders.Callback
  public void onBootComplete() {}

  @Override // com.android.server.notification.ConditionProviders.Callback
  public void onUserSwitched() {}

  public ZenModeConditions(ZenModeHelper zenModeHelper, ConditionProviders conditionProviders) {
    this.mHelper = zenModeHelper;
    this.mConditionProviders = conditionProviders;
    if (conditionProviders.isSystemProviderEnabled("countdown")) {
      conditionProviders.addSystemProvider(new CountdownConditionProvider());
    }
    if (conditionProviders.isSystemProviderEnabled("schedule")) {
      conditionProviders.addSystemProvider(new ScheduleConditionProvider());
    }
    if (conditionProviders.isSystemProviderEnabled("event")) {
      conditionProviders.addSystemProvider(new EventConditionProvider());
    }
    conditionProviders.setCallback(this);
  }

  public void dump(PrintWriter printWriter, String str) {
    printWriter.print(str);
    printWriter.print("mSubscriptions=");
    printWriter.println(this.mSubscriptions);
  }

  public void evaluateConfig(ZenModeConfig zenModeConfig, ComponentName componentName, boolean z) {
    if (zenModeConfig == null) {
      return;
    }
    ZenModeConfig.ZenRule zenRule = zenModeConfig.manualRule;
    if (zenRule != null && zenRule.condition != null && !zenRule.isTrueOrUnknown()) {
      if (DEBUG) {
        Log.d("ZenModeHelper", "evaluateConfig: clearing manual rule");
      }
      zenModeConfig.manualRule = null;
    }
    ArraySet arraySet = new ArraySet();
    evaluateRule(zenModeConfig.manualRule, arraySet, null, z);
    for (ZenModeConfig.ZenRule zenRule2 : zenModeConfig.automaticRules.values()) {
      if (zenRule2.component != null) {
        evaluateRule(zenRule2, arraySet, componentName, z);
        updateSnoozing(zenRule2, zenModeConfig.user);
      }
    }
    synchronized (this.mSubscriptions) {
      for (int size = this.mSubscriptions.size() - 1; size >= 0; size--) {
        Uri uri = (Uri) this.mSubscriptions.keyAt(size);
        ComponentName componentName2 = (ComponentName) this.mSubscriptions.valueAt(size);
        if (z && !arraySet.contains(uri)) {
          this.mConditionProviders.unsubscribeIfNecessary(componentName2, uri);
          this.mSubscriptions.removeAt(size);
        }
      }
    }
    this.mFirstEvaluation = false;
    this.mEvaluatedUsers.add(new Integer(zenModeConfig.user));
  }

  @Override // com.android.server.notification.ConditionProviders.Callback
  public void onServiceAdded(ComponentName componentName) {
    if (DEBUG) {
      Log.d("ZenModeHelper", "onServiceAdded " + componentName);
    }
    int callingUid = Binder.getCallingUid();
    ZenModeHelper zenModeHelper = this.mHelper;
    zenModeHelper.setConfig(
        zenModeHelper.getConfig(),
        componentName,
        "zmc.onServiceAdded:" + componentName,
        callingUid,
        callingUid == 1000);
  }

  @Override // com.android.server.notification.ConditionProviders.Callback
  public void onConditionChanged(Uri uri, Condition condition) {
    if (DEBUG) {
      Log.d("ZenModeHelper", "onConditionChanged " + uri + " " + condition);
    }
    if (this.mHelper.getConfig() == null) {
      return;
    }
    int callingUid = Binder.getCallingUid();
    this.mHelper.setAutomaticZenRuleState(uri, condition, callingUid, callingUid == 1000);
  }

  public final void evaluateRule(
      ZenModeConfig.ZenRule zenRule, ArraySet arraySet, ComponentName componentName, boolean z) {
    Uri uri;
    boolean isScheduleEnabled;
    if (zenRule == null
        || (uri = zenRule.conditionId) == null
        || zenRule.configurationActivity != null) {
      return;
    }
    boolean z2 = false;
    boolean z3 = true;
    for (SystemConditionProviderService systemConditionProviderService :
        this.mConditionProviders.getSystemProviders()) {
      if (systemConditionProviderService.isValidConditionId(uri)) {
        if (systemConditionProviderService
                .getComponent()
                .getClassName()
                .equals("com.android.server.notification.ScheduleConditionProvider")
            && z) {
          ZenModeConfig config = this.mHelper.getConfig();
          String str = zenRule.id;
          if (str != null
              && str.equals("EVERY_NIGHT_DEFAULT_RULE")
              && config.automaticRules.size() == 1
              && !zenRule.enabled) {
            isScheduleEnabled = !systemConditionProviderService.isScheduleEnabled();
            systemConditionProviderService.onScheduleEnabled(false);
          } else {
            isScheduleEnabled = systemConditionProviderService.isScheduleEnabled();
            systemConditionProviderService.onScheduleEnabled(true);
          }
          z3 = isScheduleEnabled;
        }
        this.mConditionProviders.ensureRecordExists(
            systemConditionProviderService.getComponent(),
            uri,
            systemConditionProviderService.asInterface());
        zenRule.component = systemConditionProviderService.getComponent();
        z2 = true;
      }
    }
    if (!z2) {
      IConditionProvider findConditionProvider =
          this.mConditionProviders.findConditionProvider(zenRule.component);
      if (DEBUG) {
        StringBuilder sb = new StringBuilder();
        sb.append("Ensure external rule exists: ");
        sb.append(findConditionProvider != null);
        sb.append(" for ");
        sb.append(uri);
        Log.d("ZenModeHelper", sb.toString());
      }
      if (findConditionProvider != null) {
        this.mConditionProviders.ensureRecordExists(zenRule.component, uri, findConditionProvider);
      }
    }
    if (zenRule.component == null && zenRule.enabler == null) {
      Log.w("ZenModeHelper", "No component found for automatic rule: " + zenRule.conditionId);
      zenRule.enabled = false;
      return;
    }
    if (arraySet != null) {
      arraySet.add(uri);
    }
    if (z && ((componentName != null && componentName.equals(zenRule.component)) || z2)) {
      boolean z4 = DEBUG;
      if (z4) {
        Log.d("ZenModeHelper", "Subscribing to " + zenRule.component);
      }
      if (this.mConditionProviders.subscribeIfNecessary(
          zenRule.component, zenRule.conditionId, z3)) {
        synchronized (this.mSubscriptions) {
          this.mSubscriptions.put(zenRule.conditionId, zenRule.component);
        }
      } else {
        zenRule.condition = null;
        if (z4) {
          Log.d("ZenModeHelper", "zmc failed to subscribe");
        }
      }
    }
    ComponentName componentName2 = zenRule.component;
    if (componentName2 == null || zenRule.condition != null) {
      return;
    }
    Condition findCondition =
        this.mConditionProviders.findCondition(componentName2, zenRule.conditionId);
    zenRule.condition = findCondition;
    if (findCondition == null || !DEBUG) {
      return;
    }
    Log.d("ZenModeHelper", "Found existing condition for: " + zenRule.conditionId);
  }

  public final boolean updateSnoozing(ZenModeConfig.ZenRule zenRule, int i) {
    if ((zenRule == null
            || !zenRule.snoozing
            || (!this.mFirstEvaluation && zenRule.isTrueOrUnknown()))
        && isUserEvaluated(i)) {
      return false;
    }
    zenRule.snoozing = false;
    Log.d("ZenModeHelper", "Snoozing reset for " + zenRule.conditionId);
    return true;
  }

  public final boolean isUserEvaluated(int i) {
    Iterator it = this.mEvaluatedUsers.iterator();
    while (it.hasNext()) {
      if (i == ((Integer) it.next()).intValue()) {
        return true;
      }
    }
    return false;
  }
}
