package com.android.server.notification;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.pm.PackageManager;
import android.net.ConnectivityModuleConnector$$ExternalSyntheticOutline0;
import android.net.Uri;
import android.net.shared.InitialConfiguration$$ExternalSyntheticOutline0;
import android.os.Handler;
import android.os.HandlerThread;
import android.os.UserHandle;
import android.os.UserManager;
import android.service.notification.Condition;
import android.service.notification.IConditionProvider;
import android.service.notification.ZenModeConfig;
import android.util.ArraySet;
import android.util.Log;
import android.util.Slog;
import android.util.SparseArray;
import android.util.TimeUtils;

import com.android.server.BootReceiver$$ExternalSyntheticOutline0;
import com.android.server.DeviceIdleController$$ExternalSyntheticOutline0;
import com.android.server.DirEncryptServiceHelper$$ExternalSyntheticOutline0;
import com.android.server.am.AppBatteryTracker$AppBatteryPolicy$$ExternalSyntheticOutline0;

import com.samsung.android.knox.analytics.activation.DevicePolicyListener;

import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Iterator;

public final class EventConditionProvider extends SystemConditionProviderService {
    public boolean mBootComplete;
    public boolean mConnected;
    public long mNextAlarmTime;
    public boolean mRegistered;
    public final Handler mWorker;
    public static final boolean DEBUG = Log.isLoggable("ConditionProviders", 3);
    public static final ComponentName COMPONENT =
            new ComponentName("android", EventConditionProvider.class.getName());
    public static final String SIMPLE_NAME = "EventConditionProvider";
    public static final String ACTION_EVALUATE =
            ConnectivityModuleConnector$$ExternalSyntheticOutline0.m$1(
                    "EventConditionProvider", ".EVALUATE");
    public final EventConditionProvider mContext = this;
    public final ArraySet mSubscriptions = new ArraySet();
    public final SparseArray mTrackers = new SparseArray();
    public final AnonymousClass2 mTrackerCallback = new AnonymousClass2();
    public final AnonymousClass1 mReceiver = new AnonymousClass1(this, 1);
    public final AnonymousClass4 mEvaluateSubscriptionsW =
            new Runnable() { // from class: com.android.server.notification.EventConditionProvider.4
                @Override // java.lang.Runnable
                public final void run() {
                    Iterator it;
                    CalendarTracker.CheckEventResult checkEventResult;
                    Iterator it2;
                    boolean z;
                    EventConditionProvider eventConditionProvider = EventConditionProvider.this;
                    boolean z2 = EventConditionProvider.DEBUG;
                    if (z2) {
                        eventConditionProvider.getClass();
                        Slog.d("ConditionProviders.ECP", "evaluateSubscriptions");
                    }
                    if (!eventConditionProvider.mBootComplete) {
                        if (z2) {
                            Slog.d(
                                    "ConditionProviders.ECP",
                                    "Skipping evaluate before boot complete");
                            return;
                        }
                        return;
                    }
                    long currentTimeMillis = System.currentTimeMillis();
                    ArrayList arrayList = new ArrayList();
                    synchronized (eventConditionProvider.mSubscriptions) {
                        int i = 0;
                        for (int i2 = 0; i2 < eventConditionProvider.mTrackers.size(); i2++) {
                            try {
                                ((CalendarTracker) eventConditionProvider.mTrackers.valueAt(i2))
                                        .setCallback(
                                                eventConditionProvider.mSubscriptions.isEmpty()
                                                        ? null
                                                        : eventConditionProvider.mTrackerCallback);
                            } catch (Throwable th) {
                                throw th;
                            }
                        }
                        eventConditionProvider.setRegistered(
                                !eventConditionProvider.mSubscriptions.isEmpty());
                        Iterator it3 = eventConditionProvider.mSubscriptions.iterator();
                        long j = 0;
                        while (it3.hasNext()) {
                            Uri uri = (Uri) it3.next();
                            ZenModeConfig.EventInfo tryParseEventConditionId =
                                    ZenModeConfig.tryParseEventConditionId(uri);
                            if (tryParseEventConditionId == null) {
                                arrayList.add(EventConditionProvider.createCondition(i, uri));
                                it = it3;
                            } else {
                                if (tryParseEventConditionId.calName == null) {
                                    int i3 = i;
                                    checkEventResult = null;
                                    while (i3 < eventConditionProvider.mTrackers.size()) {
                                        CalendarTracker.CheckEventResult checkEvent =
                                                ((CalendarTracker)
                                                                eventConditionProvider.mTrackers
                                                                        .valueAt(i3))
                                                        .checkEvent(
                                                                tryParseEventConditionId,
                                                                currentTimeMillis);
                                        if (checkEventResult == null) {
                                            it2 = it3;
                                            checkEventResult = checkEvent;
                                        } else {
                                            checkEventResult.inEvent |= checkEvent.inEvent;
                                            it2 = it3;
                                            checkEventResult.recheckAt =
                                                    Math.min(
                                                            checkEventResult.recheckAt,
                                                            checkEvent.recheckAt);
                                        }
                                        i3++;
                                        it3 = it2;
                                    }
                                    it = it3;
                                } else {
                                    it = it3;
                                    int resolveUserId =
                                            ZenModeConfig.EventInfo.resolveUserId(
                                                    tryParseEventConditionId.userId);
                                    CalendarTracker calendarTracker =
                                            (CalendarTracker)
                                                    eventConditionProvider.mTrackers.get(
                                                            resolveUserId);
                                    if (calendarTracker == null) {
                                        Slog.w(
                                                "ConditionProviders.ECP",
                                                "No calendar tracker found for user "
                                                        + resolveUserId);
                                        arrayList.add(
                                                EventConditionProvider.createCondition(0, uri));
                                    } else {
                                        checkEventResult =
                                                calendarTracker.checkEvent(
                                                        tryParseEventConditionId,
                                                        currentTimeMillis);
                                    }
                                }
                                long j2 = checkEventResult.recheckAt;
                                if (j2 != 0 && (j == 0 || j2 < j)) {
                                    j = j2;
                                }
                                if (checkEventResult.inEvent) {
                                    i = 0;
                                    z = true;
                                    arrayList.add(EventConditionProvider.createCondition(1, uri));
                                } else {
                                    i = 0;
                                    arrayList.add(EventConditionProvider.createCondition(0, uri));
                                    z = true;
                                }
                                it3 = it;
                            }
                            it3 = it;
                            i = 0;
                        }
                        eventConditionProvider.rescheduleAlarm(currentTimeMillis, j);
                    }
                    Iterator it4 = arrayList.iterator();
                    while (it4.hasNext()) {
                        Condition condition = (Condition) it4.next();
                        if (condition != null) {
                            eventConditionProvider.notifyCondition(condition);
                        }
                    }
                    if (EventConditionProvider.DEBUG) {
                        Slog.d(
                                "ConditionProviders.ECP",
                                "evaluateSubscriptions took "
                                        + (System.currentTimeMillis() - currentTimeMillis));
                    }
                }
            };

    /* renamed from: com.android.server.notification.EventConditionProvider$1, reason: invalid class name */
    public final class AnonymousClass1 extends BroadcastReceiver {
        public final /* synthetic */ int $r8$classId;
        public final /* synthetic */ EventConditionProvider this$0;

        public /* synthetic */ AnonymousClass1(
                EventConditionProvider eventConditionProvider, int i) {
            this.$r8$classId = i;
            this.this$0 = eventConditionProvider;
        }

        @Override // android.content.BroadcastReceiver
        public final void onReceive(Context context, Intent intent) {
            switch (this.$r8$classId) {
                case 0:
                    this.this$0.reloadTrackers();
                    break;
                default:
                    if (EventConditionProvider.DEBUG) {
                        Slog.d("ConditionProviders.ECP", "onReceive " + intent.getAction());
                    }
                    this.this$0.evaluateSubscriptions();
                    break;
            }
        }
    }

    /* renamed from: com.android.server.notification.EventConditionProvider$2, reason: invalid class name */
    public final class AnonymousClass2 {
        public AnonymousClass2() {}
    }

    public EventConditionProvider() {
        if (DEBUG) {
            DeviceIdleController$$ExternalSyntheticOutline0.m(
                    new StringBuilder("new "), SIMPLE_NAME, "()", "ConditionProviders.ECP");
        }
        HandlerThread handlerThread = new HandlerThread("ConditionProviders.ECP", 10);
        handlerThread.start();
        this.mWorker = new Handler(handlerThread.getLooper());
    }

    public static Condition createCondition(int i, Uri uri) {
        return new Condition(uri, "...", "...", "...", 0, i, 2);
    }

    @Override // com.android.server.notification.SystemConditionProviderService
    public final IConditionProvider asInterface() {
        return onBind(null);
    }

    @Override // com.android.server.notification.SystemConditionProviderService
    public final void attachBase(Context context) {
        attachBaseContext(context);
    }

    @Override // com.android.server.notification.SystemConditionProviderService
    public final void dump(PrintWriter printWriter) {
        printWriter.print("    ");
        printWriter.print(SIMPLE_NAME);
        printWriter.println(":");
        printWriter.print("      mConnected=");
        printWriter.println(this.mConnected);
        printWriter.print("      mRegistered=");
        printWriter.println(this.mRegistered);
        printWriter.print("      mBootComplete=");
        printWriter.println(this.mBootComplete);
        SystemConditionProviderService.dumpUpcomingTime(
                printWriter, this.mNextAlarmTime, System.currentTimeMillis());
        synchronized (this.mSubscriptions) {
            try {
                printWriter.println("      mSubscriptions=");
                Iterator it = this.mSubscriptions.iterator();
                while (it.hasNext()) {
                    Uri uri = (Uri) it.next();
                    printWriter.print("        ");
                    printWriter.println(uri);
                }
            } catch (Throwable th) {
                throw th;
            }
        }
        printWriter.println("      mTrackers=");
        for (int i = 0; i < this.mTrackers.size(); i++) {
            printWriter.print("        user=");
            printWriter.println(this.mTrackers.keyAt(i));
            CalendarTracker calendarTracker = (CalendarTracker) this.mTrackers.valueAt(i);
            calendarTracker.getClass();
            printWriter.print("          ");
            printWriter.print("mCallback=");
            printWriter.println(calendarTracker.mCallback);
            printWriter.print("          ");
            printWriter.print("mRegistered=");
            AppBatteryTracker$AppBatteryPolicy$$ExternalSyntheticOutline0.m(
                    printWriter, "          ", "u=", calendarTracker.mRegistered);
            printWriter.println(calendarTracker.mUserContext.getUserId());
        }
    }

    public final void evaluateSubscriptions() {
        if (this.mWorker.hasCallbacks(this.mEvaluateSubscriptionsW)) {
            return;
        }
        this.mWorker.post(this.mEvaluateSubscriptionsW);
    }

    @Override // com.android.server.notification.SystemConditionProviderService
    public final ComponentName getComponent() {
        return COMPONENT;
    }

    @Override // com.android.server.notification.SystemConditionProviderService
    public final boolean isScheduleEnabled() {
        return false;
    }

    @Override // com.android.server.notification.SystemConditionProviderService
    public final boolean isValidConditionId(Uri uri) {
        return ZenModeConfig.isValidEventConditionId(uri);
    }

    @Override // com.android.server.notification.SystemConditionProviderService
    public final void onBootComplete() {
        if (DEBUG) {
            Slog.d("ConditionProviders.ECP", "onBootComplete");
        }
        if (this.mBootComplete) {
            return;
        }
        this.mBootComplete = true;
        this.mContext.registerReceiver(
                new AnonymousClass1(this, 0),
                DirEncryptServiceHelper$$ExternalSyntheticOutline0.m(
                        DevicePolicyListener.ACTION_PROFILE_OWNER_ADDED,
                        DevicePolicyListener.ACTION_PROFILE_OWNER_REMOVED));
        reloadTrackers();
    }

    @Override // android.service.notification.ConditionProviderService
    public final void onConnected() {
        if (DEBUG) {
            Slog.d("ConditionProviders.ECP", "onConnected");
        }
        this.mConnected = true;
    }

    @Override // android.app.Service
    public final void onDestroy() {
        super.onDestroy();
        if (DEBUG) {
            Slog.d("ConditionProviders.ECP", "onDestroy");
        }
        this.mConnected = false;
    }

    @Override // com.android.server.notification.SystemConditionProviderService
    public final void onScheduleEnabled(boolean z) {
        if (DEBUG) {
            DeviceIdleController$$ExternalSyntheticOutline0.m(
                    "onScheduleEnabled : ", "ConditionProviders.ECP", z);
        }
    }

    @Override // android.service.notification.ConditionProviderService
    public final void onSubscribe(Uri uri) {
        if (DEBUG) {
            Slog.d("ConditionProviders.ECP", "onSubscribe " + uri);
        }
        if (!ZenModeConfig.isValidEventConditionId(uri)) {
            notifyCondition(createCondition(0, uri));
            return;
        }
        synchronized (this.mSubscriptions) {
            try {
                if (this.mSubscriptions.add(uri)) {
                    evaluateSubscriptions();
                }
            } catch (Throwable th) {
                throw th;
            }
        }
    }

    @Override // android.service.notification.ConditionProviderService
    public final void onUnsubscribe(Uri uri) {
        if (DEBUG) {
            Slog.d("ConditionProviders.ECP", "onUnsubscribe " + uri);
        }
        synchronized (this.mSubscriptions) {
            try {
                if (this.mSubscriptions.remove(uri)) {
                    evaluateSubscriptions();
                }
            } catch (Throwable th) {
                throw th;
            }
        }
    }

    public final void reloadTrackers() {
        Context context;
        if (DEBUG) {
            Slog.d("ConditionProviders.ECP", "reloadTrackers");
        }
        for (int i = 0; i < this.mTrackers.size(); i++) {
            ((CalendarTracker) this.mTrackers.valueAt(i)).setCallback(null);
        }
        this.mTrackers.clear();
        for (UserHandle userHandle : UserManager.get(this.mContext).getUserProfiles()) {
            if (userHandle.isSystem()) {
                context = this.mContext;
            } else {
                EventConditionProvider eventConditionProvider = this.mContext;
                try {
                    context =
                            eventConditionProvider.createPackageContextAsUser(
                                    eventConditionProvider.getPackageName(), 0, userHandle);
                } catch (PackageManager.NameNotFoundException unused) {
                    context = null;
                }
            }
            if (context == null) {
                Slog.w(
                        "ConditionProviders.ECP",
                        "Unable to create context for user " + userHandle.getIdentifier());
            } else {
                this.mTrackers.put(
                        userHandle.getIdentifier(), new CalendarTracker(this.mContext, context));
            }
        }
        evaluateSubscriptions();
    }

    public final void rescheduleAlarm(long j, long j2) {
        this.mNextAlarmTime = j2;
        AlarmManager alarmManager = (AlarmManager) this.mContext.getSystemService("alarm");
        PendingIntent broadcast =
                PendingIntent.getBroadcast(
                        this.mContext,
                        1,
                        new Intent(ACTION_EVALUATE)
                                .addFlags(268435456)
                                .setPackage("android")
                                .putExtra("time", j2),
                        201326592);
        alarmManager.cancel(broadcast);
        if (j2 == 0 || j2 < j) {
            if (DEBUG) {
                Slog.d(
                        "ConditionProviders.ECP",
                        "Not scheduling evaluate: "
                                .concat(
                                        j2 == 0
                                                ? "no time specified"
                                                : "specified time in the past"));
                return;
            }
            return;
        }
        if (DEBUG) {
            String ts = SystemConditionProviderService.ts(j2);
            StringBuilder sb = new StringBuilder();
            TimeUtils.formatDuration(j2 - j, sb);
            BootReceiver$$ExternalSyntheticOutline0.m(
                    InitialConfiguration$$ExternalSyntheticOutline0.m(
                            "Scheduling evaluate for ", ts, ", in ", sb.toString(), ", now="),
                    SystemConditionProviderService.ts(j),
                    "ConditionProviders.ECP");
        }
        alarmManager.setExact(0, j2, broadcast);
    }

    public final void setRegistered(boolean z) {
        if (this.mRegistered == z) {
            return;
        }
        if (DEBUG) {
            DeviceIdleController$$ExternalSyntheticOutline0.m(
                    "setRegistered ", "ConditionProviders.ECP", z);
        }
        this.mRegistered = z;
        if (!z) {
            unregisterReceiver(this.mReceiver);
            return;
        }
        IntentFilter m =
                DirEncryptServiceHelper$$ExternalSyntheticOutline0.m(
                        "android.intent.action.TIME_SET", "android.intent.action.TIMEZONE_CHANGED");
        m.addAction(ACTION_EVALUATE);
        registerReceiver(this.mReceiver, m, 2);
    }
}
