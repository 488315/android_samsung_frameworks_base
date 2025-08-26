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
import com.samsung.android.knox.custom.IKnoxCustomManager;
import java.util.Set;
import kotlin.collections.ArraysKt___ArraysKt;
import kotlin.collections.CollectionsKt___CollectionsKt;
import kotlin.jvm.internal.DefaultConstructorMarker;

/* loaded from: classes.dex */
public class AssistLogger {
    public static final Set SESSION_END_EVENTS;
    public final AssistUtils assistUtils;
    public final Context context;
    public InstanceId currentInstanceId;
    public final InstanceIdSequence instanceIdSequence = new InstanceIdSequence(1048576);
    public final PhoneStateMonitor phoneStateMonitor;
    public final UiEventLogger uiEventLogger;
    public final UserTracker userTracker;

    public final class Companion {
        public /* synthetic */ Companion(DefaultConstructorMarker defaultConstructorMarker) {
            this();
        }

        private Companion() {
        }
    }

    static {
        new Companion(null);
        SESSION_END_EVENTS = ArraysKt___ArraysKt.toSet(new AssistantSessionEvent[]{AssistantSessionEvent.ASSISTANT_SESSION_INVOCATION_CANCELLED, AssistantSessionEvent.ASSISTANT_SESSION_CLOSE});
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
        Integer numValueOf;
        AssistantInvocationEvent assistantInvocationEvent;
        String strFlattenToString;
        int iIntValue = 3;
        if (num == null) {
            numValueOf = null;
        } else {
            AssistantInvocationEvent.Companion companion = AssistantInvocationEvent.Companion;
            int iIntValue2 = num.intValue();
            companion.getClass();
            switch (iIntValue2) {
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
            numValueOf = Integer.valueOf(i2);
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
        if (numValueOf == null) {
            switch (this.phoneStateMonitor.getPhoneState()) {
                case 1:
                    iIntValue = 1;
                    break;
                case 2:
                    iIntValue = 2;
                    break;
                case 3:
                    break;
                case 4:
                    iIntValue = 4;
                    break;
                case 5:
                    iIntValue = 5;
                    break;
                case 6:
                    iIntValue = 6;
                    break;
                case 7:
                    iIntValue = 7;
                    break;
                case 8:
                    iIntValue = 8;
                    break;
                case 9:
                    iIntValue = 9;
                    break;
                case 10:
                    iIntValue = 10;
                    break;
                default:
                    iIntValue = 0;
                    break;
            }
        } else {
            iIntValue = numValueOf.intValue();
        }
        int i3 = iIntValue;
        int id = assistantInvocationEvent.getId();
        if (assistComponentForUser == null || (strFlattenToString = assistComponentForUser.flattenToString()) == null) {
            strFlattenToString = "";
        }
        String str = strFlattenToString;
        InstanceId instanceIdNewInstanceId = this.currentInstanceId;
        if (instanceIdNewInstanceId == null) {
            instanceIdNewInstanceId = this.instanceIdSequence.newInstanceId();
        }
        this.currentInstanceId = instanceIdNewInstanceId;
        instanceIdNewInstanceId.getClass();
        FrameworkStatsLog.write(IKnoxCustomManager.Stub.TRANSACTION_setBootingAnimationSub, id, assistantUid, str, instanceIdNewInstanceId.getId(), i3, false);
    }

    public final void reportAssistantSessionEvent(AssistantSessionEvent assistantSessionEvent) {
        ComponentName assistComponentForUser = this.assistUtils.getAssistComponentForUser(((UserTrackerImpl) this.userTracker).getUserId());
        int assistantUid = getAssistantUid(assistComponentForUser);
        UiEventLogger uiEventLogger = this.uiEventLogger;
        String strFlattenToString = assistComponentForUser != null ? assistComponentForUser.flattenToString() : null;
        InstanceId instanceIdNewInstanceId = this.currentInstanceId;
        if (instanceIdNewInstanceId == null) {
            instanceIdNewInstanceId = this.instanceIdSequence.newInstanceId();
        }
        this.currentInstanceId = instanceIdNewInstanceId;
        instanceIdNewInstanceId.getClass();
        uiEventLogger.logWithInstanceId(assistantSessionEvent, assistantUid, strFlattenToString, instanceIdNewInstanceId);
        if (CollectionsKt___CollectionsKt.contains(SESSION_END_EVENTS, assistantSessionEvent)) {
            this.currentInstanceId = null;
        }
    }
}
