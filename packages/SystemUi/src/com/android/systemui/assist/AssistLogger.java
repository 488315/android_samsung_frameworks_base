package com.android.systemui.assist;

import android.content.ComponentName;
import android.content.Context;
import android.content.pm.PackageManager;
import android.util.Log;
import com.android.internal.app.AssistUtils;
import com.android.internal.logging.InstanceId;
import com.android.internal.logging.InstanceIdSequence;
import com.android.internal.logging.UiEventLogger;
import com.android.internal.util.FrameworkStatsLog;
import com.android.systemui.assist.AssistantInvocationEvent;
import com.android.systemui.settings.UserTracker;
import com.android.systemui.settings.UserTrackerImpl;
import com.samsung.android.desktopsystemui.sharedlib.system.QuickStepContract;
import com.samsung.android.knox.custom.IKnoxCustomManager;
import java.util.Set;
import kotlin.collections.CollectionsKt___CollectionsKt;
import kotlin.collections.SetsKt__SetsKt;
import kotlin.jvm.internal.DefaultConstructorMarker;

/* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
/* loaded from: classes.dex */
public final class AssistLogger {
    public static final Set SESSION_END_EVENTS;
    public final AssistUtils assistUtils;
    public final Context context;
    public InstanceId currentInstanceId;
    public final InstanceIdSequence instanceIdSequence = new InstanceIdSequence(QuickStepContract.SYSUI_STATE_IME_SWITCHER_SHOWING);
    public final PhoneStateMonitor phoneStateMonitor;
    public final UiEventLogger uiEventLogger;
    public final UserTracker userTracker;

    /* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
    public final class Companion {
        private Companion() {
        }

        public /* synthetic */ Companion(DefaultConstructorMarker defaultConstructorMarker) {
            this();
        }
    }

    static {
        new Companion(null);
        SESSION_END_EVENTS = SetsKt__SetsKt.setOf(AssistantSessionEvent.ASSISTANT_SESSION_INVOCATION_CANCELLED, AssistantSessionEvent.ASSISTANT_SESSION_CLOSE);
    }

    public AssistLogger(Context context, UiEventLogger uiEventLogger, AssistUtils assistUtils, PhoneStateMonitor phoneStateMonitor, UserTracker userTracker) {
        this.context = context;
        this.uiEventLogger = uiEventLogger;
        this.assistUtils = assistUtils;
        this.phoneStateMonitor = phoneStateMonitor;
        this.userTracker = userTracker;
    }

    public final int getAssistantUid(ComponentName componentName) {
        if (componentName == null) {
            return 0;
        }
        try {
            return this.context.getPackageManager().getApplicationInfo(componentName.getPackageName(), 0).uid;
        } catch (PackageManager.NameNotFoundException e) {
            Log.e("AssistLogger", "Unable to find Assistant UID", e);
            return 0;
        }
    }

    public final void reportAssistantInvocationEventFromLegacy(int i, boolean z, ComponentName componentName, Integer num) {
        Integer valueOf;
        AssistantInvocationEvent assistantInvocationEvent;
        if (num == null) {
            valueOf = null;
        } else {
            AssistantInvocationEvent.Companion companion = AssistantInvocationEvent.Companion;
            int intValue = num.intValue();
            companion.getClass();
            valueOf = Integer.valueOf(AssistantInvocationEvent.Companion.deviceStateFromLegacyDeviceState(intValue));
        }
        AssistantInvocationEvent.Companion.getClass();
        if (z) {
            switch (i) {
                case 1:
                    assistantInvocationEvent = AssistantInvocationEvent.ASSISTANT_INVOCATION_TOUCH_GESTURE;
                    break;
                case 2:
                    assistantInvocationEvent = AssistantInvocationEvent.ASSISTANT_INVOCATION_PHYSICAL_GESTURE;
                    break;
                case 3:
                    assistantInvocationEvent = AssistantInvocationEvent.ASSISTANT_INVOCATION_HOTWORD;
                    break;
                case 4:
                    assistantInvocationEvent = AssistantInvocationEvent.ASSISTANT_INVOCATION_QUICK_SEARCH_BAR;
                    break;
                case 5:
                    assistantInvocationEvent = AssistantInvocationEvent.ASSISTANT_INVOCATION_HOME_LONG_PRESS;
                    break;
                case 6:
                    assistantInvocationEvent = AssistantInvocationEvent.ASSISTANT_INVOCATION_POWER_LONG_PRESS;
                    break;
                default:
                    assistantInvocationEvent = AssistantInvocationEvent.ASSISTANT_INVOCATION_UNKNOWN;
                    break;
            }
        } else {
            assistantInvocationEvent = i != 1 ? i != 2 ? AssistantInvocationEvent.ASSISTANT_INVOCATION_START_UNKNOWN : AssistantInvocationEvent.ASSISTANT_INVOCATION_START_PHYSICAL_GESTURE : AssistantInvocationEvent.ASSISTANT_INVOCATION_START_TOUCH_GESTURE;
        }
        if (componentName == null) {
            componentName = this.assistUtils.getAssistComponentForUser(((UserTrackerImpl) this.userTracker).getUserId());
        }
        int assistantUid = getAssistantUid(componentName);
        int intValue2 = valueOf != null ? valueOf.intValue() : AssistantInvocationEvent.Companion.deviceStateFromLegacyDeviceState(this.phoneStateMonitor.getPhoneState());
        int id = assistantInvocationEvent.getId();
        String flattenToString = componentName != null ? componentName.flattenToString() : null;
        String str = flattenToString == null ? "" : flattenToString;
        InstanceId instanceId = this.currentInstanceId;
        if (instanceId == null) {
            instanceId = this.instanceIdSequence.newInstanceId();
        }
        this.currentInstanceId = instanceId;
        FrameworkStatsLog.write(IKnoxCustomManager.Stub.TRANSACTION_setBootingAnimationSub, id, assistantUid, str, instanceId.getId(), intValue2, false);
    }

    public final void reportAssistantSessionEvent(AssistantSessionEvent assistantSessionEvent) {
        ComponentName assistComponentForUser = this.assistUtils.getAssistComponentForUser(((UserTrackerImpl) this.userTracker).getUserId());
        int assistantUid = getAssistantUid(assistComponentForUser);
        String flattenToString = assistComponentForUser != null ? assistComponentForUser.flattenToString() : null;
        InstanceId instanceId = this.currentInstanceId;
        if (instanceId == null) {
            instanceId = this.instanceIdSequence.newInstanceId();
        }
        this.currentInstanceId = instanceId;
        this.uiEventLogger.logWithInstanceId(assistantSessionEvent, assistantUid, flattenToString, instanceId);
        if (CollectionsKt___CollectionsKt.contains(SESSION_END_EVENTS, assistantSessionEvent)) {
            this.currentInstanceId = null;
        }
    }
}
