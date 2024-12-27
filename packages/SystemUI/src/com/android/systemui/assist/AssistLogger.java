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
import kotlin.jvm.internal.Intrinsics;

/* compiled from: qb/89523975 427a50d40ec74a85ca352b86f77450b1c52ece5389e11158752b0d641a3a5098 */
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

    /* compiled from: qb/89523975 427a50d40ec74a85ca352b86f77450b1c52ece5389e11158752b0d641a3a5098 */
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
        int i2;
        Integer valueOf;
        AssistantInvocationEvent assistantInvocationEvent;
        String str;
        int i3 = 3;
        if (num == null) {
            valueOf = null;
        } else {
            AssistantInvocationEvent.Companion companion = AssistantInvocationEvent.Companion;
            int intValue = num.intValue();
            companion.getClass();
            switch (intValue) {
                case 1:
                    i2 = 1;
                    break;
                case 2:
                    i2 = 2;
                    break;
                case 3:
                    i2 = 3;
                    break;
                case 4:
                    i2 = 4;
                    break;
                case 5:
                    i2 = 5;
                    break;
                case 6:
                    i2 = 6;
                    break;
                case 7:
                    i2 = 7;
                    break;
                case 8:
                    i2 = 8;
                    break;
                case 9:
                    i2 = 9;
                    break;
                case 10:
                    i2 = 10;
                    break;
                default:
                    i2 = 0;
                    break;
            }
            valueOf = Integer.valueOf(i2);
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
        ComponentName assistComponentForUser = componentName == null ? this.assistUtils.getAssistComponentForUser(((UserTrackerImpl) this.userTracker).getUserId()) : componentName;
        int assistantUid = getAssistantUid(assistComponentForUser);
        if (valueOf == null) {
            switch (this.phoneStateMonitor.getPhoneState()) {
                case 1:
                    i3 = 1;
                    break;
                case 2:
                    i3 = 2;
                    break;
                case 3:
                    break;
                case 4:
                    i3 = 4;
                    break;
                case 5:
                    i3 = 5;
                    break;
                case 6:
                    i3 = 6;
                    break;
                case 7:
                    i3 = 7;
                    break;
                case 8:
                    i3 = 8;
                    break;
                case 9:
                    i3 = 9;
                    break;
                case 10:
                    i3 = 10;
                    break;
                default:
                    i3 = 0;
                    break;
            }
        } else {
            i3 = valueOf.intValue();
        }
        int i4 = i3;
        int id = assistantInvocationEvent.getId();
        if (assistComponentForUser == null || (str = assistComponentForUser.flattenToString()) == null) {
            str = "";
        }
        String str2 = str;
        InstanceId instanceId = this.currentInstanceId;
        if (instanceId == null) {
            instanceId = this.instanceIdSequence.newInstanceId();
        }
        this.currentInstanceId = instanceId;
        Intrinsics.checkNotNull(instanceId);
        FrameworkStatsLog.write(IKnoxCustomManager.Stub.TRANSACTION_setBootingAnimationSub, id, assistantUid, str2, instanceId.getId(), i4, false);
    }

    public final void reportAssistantSessionEvent(UiEventLogger.UiEventEnum uiEventEnum) {
        ComponentName assistComponentForUser = this.assistUtils.getAssistComponentForUser(((UserTrackerImpl) this.userTracker).getUserId());
        int assistantUid = getAssistantUid(assistComponentForUser);
        UiEventLogger uiEventLogger = this.uiEventLogger;
        String flattenToString = assistComponentForUser != null ? assistComponentForUser.flattenToString() : null;
        InstanceId instanceId = this.currentInstanceId;
        if (instanceId == null) {
            instanceId = this.instanceIdSequence.newInstanceId();
        }
        this.currentInstanceId = instanceId;
        Intrinsics.checkNotNull(instanceId);
        uiEventLogger.logWithInstanceId(uiEventEnum, assistantUid, flattenToString, instanceId);
        if (CollectionsKt___CollectionsKt.contains(SESSION_END_EVENTS, uiEventEnum)) {
            this.currentInstanceId = null;
        }
    }
}
