package com.android.systemui.statusbar.policy;

import android.app.AlarmManager;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.UserHandle;
import androidx.appcompat.app.AppCompatDelegateImpl$$ExternalSyntheticOutline0;
import com.android.systemui.Dumpable;
import com.android.systemui.broadcast.BroadcastDispatcher;
import com.android.systemui.dump.DumpManager;
import com.android.systemui.settings.UserTracker;
import com.android.systemui.settings.UserTrackerImpl;
import com.android.systemui.statusbar.policy.NextAlarmController;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.concurrent.Executor;

/* compiled from: qb/89523975 427a50d40ec74a85ca352b86f77450b1c52ece5389e11158752b0d641a3a5098 */
/* loaded from: classes3.dex */
public final class NextAlarmControllerImpl extends BroadcastReceiver implements NextAlarmController, Dumpable {
    public static final /* synthetic */ int $r8$clinit = 0;
    public final AlarmManager mAlarmManager;
    public final ArrayList mChangeCallbacks = new ArrayList();
    public AlarmManager.AlarmClockInfo mNextAlarm;
    public final UserTracker.Callback mUserChangedCallback;
    public final UserTracker mUserTracker;

    public NextAlarmControllerImpl(Executor executor, AlarmManager alarmManager, BroadcastDispatcher broadcastDispatcher, DumpManager dumpManager, UserTracker userTracker) {
        UserTracker.Callback callback = new UserTracker.Callback() { // from class: com.android.systemui.statusbar.policy.NextAlarmControllerImpl.1
            @Override // com.android.systemui.settings.UserTracker.Callback
            public final void onUserChanged(int i, Context context) {
                int i2 = NextAlarmControllerImpl.$r8$clinit;
                NextAlarmControllerImpl.this.updateNextAlarm$1();
            }
        };
        this.mUserChangedCallback = callback;
        dumpManager.registerDumpable("NextAlarmController", this);
        this.mAlarmManager = alarmManager;
        this.mUserTracker = userTracker;
        broadcastDispatcher.registerReceiver(this, AppCompatDelegateImpl$$ExternalSyntheticOutline0.m("com.samsung.android.action.ALARM_CLOCK_CHANGED"), null, UserHandle.ALL);
        ((UserTrackerImpl) userTracker).addCallback(callback, executor);
        updateNextAlarm$1();
    }

    @Override // com.android.systemui.statusbar.policy.CallbackController
    public final void addCallback(Object obj) {
        NextAlarmController.NextAlarmChangeCallback nextAlarmChangeCallback = (NextAlarmController.NextAlarmChangeCallback) obj;
        this.mChangeCallbacks.add(nextAlarmChangeCallback);
        nextAlarmChangeCallback.onNextAlarmChanged(this.mNextAlarm);
    }

    @Override // com.android.systemui.Dumpable
    public final void dump(PrintWriter printWriter, String[] strArr) {
        printWriter.print("mNextAlarm=");
        if (this.mNextAlarm != null) {
            printWriter.println(new Date(this.mNextAlarm.getTriggerTime()));
            printWriter.print("  PendingIntentPkg=");
            if (this.mNextAlarm.getShowIntent() != null) {
                printWriter.println(this.mNextAlarm.getShowIntent().getCreatorPackage());
            } else {
                printWriter.println("showIntent=null");
            }
        } else {
            printWriter.println("null");
        }
        printWriter.println("Registered Callbacks:");
        Iterator it = this.mChangeCallbacks.iterator();
        while (it.hasNext()) {
            NextAlarmController.NextAlarmChangeCallback nextAlarmChangeCallback = (NextAlarmController.NextAlarmChangeCallback) it.next();
            printWriter.print("    ");
            printWriter.println(nextAlarmChangeCallback.toString());
        }
    }

    @Override // android.content.BroadcastReceiver
    public final void onReceive(Context context, Intent intent) {
        if (intent.getAction().equals("com.samsung.android.action.ALARM_CLOCK_CHANGED")) {
            updateNextAlarm$1();
        }
    }

    @Override // com.android.systemui.statusbar.policy.CallbackController
    public final void removeCallback(Object obj) {
        this.mChangeCallbacks.remove((NextAlarmController.NextAlarmChangeCallback) obj);
    }

    public final void updateNextAlarm$1() {
        this.mNextAlarm = this.mAlarmManager.getNextAlarmClock(((UserTrackerImpl) this.mUserTracker).getUserId());
        ArrayList arrayList = new ArrayList(this.mChangeCallbacks);
        int size = arrayList.size();
        for (int i = 0; i < size; i++) {
            ((NextAlarmController.NextAlarmChangeCallback) arrayList.get(i)).onNextAlarmChanged(this.mNextAlarm);
        }
    }
}
