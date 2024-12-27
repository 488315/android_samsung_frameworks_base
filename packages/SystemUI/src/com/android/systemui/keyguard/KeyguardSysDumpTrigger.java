package com.android.systemui.keyguard;

import android.app.ActivityTaskManager;
import android.app.IApplicationThread;
import android.app.ProfilerInfo;
import android.content.BroadcastReceiver;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.os.IBinder;
import android.os.PowerManager;
import android.os.RemoteException;
import android.os.SystemClock;
import android.os.UserHandle;
import android.os.UserManager;
import android.widget.Toast;
import androidx.compose.foundation.layout.RowColumnMeasurePolicyKt$$ExternalSyntheticOutline0;
import androidx.compose.foundation.text.input.internal.RecordingInputConnection$$ExternalSyntheticOutline0;
import androidx.concurrent.futures.AbstractResolvableFuture$$ExternalSyntheticOutline0;
import com.android.keyguard.KeyguardCarrierPasswordViewController$$ExternalSyntheticOutline0;
import com.android.systemui.broadcast.BroadcastDispatcher;
import com.android.systemui.statusbar.notification.collection.notifcollection.CommonNotifCollection;
import com.android.systemui.user.domain.interactor.SelectedUserInteractor;
import com.android.systemui.util.DeviceType;
import com.android.systemui.util.LogUtil;
import com.android.systemui.util.concurrency.DelayableExecutor;
import com.samsung.android.desktopsystemui.sharedlib.system.QuickStepContract;
import java.util.Timer;
import kotlin.Unit;
import kotlin.jvm.internal.DefaultConstructorMarker;

/* compiled from: qb/89523975 427a50d40ec74a85ca352b86f77450b1c52ece5389e11158752b0d641a3a5098 */
public final class KeyguardSysDumpTrigger {
    public static final int[] KEY;
    public static final ComponentName SYSDUMP_COMPONENT_NAME;
    public final Handler bgHandler;
    public Runnable cancelExecToken;
    public Runnable cancelIssueReportExecToken;
    public final Context context;
    public final DelayableExecutor executor;
    public final boolean isDebug;
    public boolean isEnabled;
    public int keyIndex;
    public final PowerManager powerManager;
    public long prevEventTime;
    public final SelectedUserInteractor selectedUserInteractor;
    public final UserManager userManager;
    public final WakefulnessLifecycle wakefulnessLifecycle;

    /* compiled from: qb/89523975 427a50d40ec74a85ca352b86f77450b1c52ece5389e11158752b0d641a3a5098 */
    public final class Companion {
        private Companion() {
        }

        public /* synthetic */ Companion(DefaultConstructorMarker defaultConstructorMarker) {
            this();
        }

        public static /* synthetic */ void getREASON_KEY_INPUT$annotations() {
        }

        public static /* synthetic */ void getREASON_TIMEOUT$annotations() {
        }
    }

    static {
        new Companion(null);
        KEY = new int[]{24, 24, 24, 25, 25, 25, 24, 25, 24, 25};
        SYSDUMP_COMPONENT_NAME = new ComponentName("com.sec.android.app.servicemodeapp", "com.sec.android.app.servicemodeapp.SysDump");
    }

    public KeyguardSysDumpTrigger(Context context, DelayableExecutor delayableExecutor, Handler handler, PowerManager powerManager, UserManager userManager, SelectedUserInteractor selectedUserInteractor, BroadcastDispatcher broadcastDispatcher, WakefulnessLifecycle wakefulnessLifecycle, CommonNotifCollection commonNotifCollection) {
        this.context = context;
        this.executor = delayableExecutor;
        this.bgHandler = handler;
        this.powerManager = powerManager;
        this.userManager = userManager;
        this.selectedUserInteractor = selectedUserInteractor;
        this.wakefulnessLifecycle = wakefulnessLifecycle;
        new Timer();
        BroadcastReceiver broadcastReceiver = new BroadcastReceiver() { // from class: com.android.systemui.keyguard.KeyguardSysDumpTrigger$receiver$1
            @Override // android.content.BroadcastReceiver
            public final void onReceive(Context context2, Intent intent) {
                Uri data;
                String str = null;
                String action = intent != null ? intent.getAction() : null;
                if (intent != null && (data = intent.getData()) != null) {
                    str = data.getSchemeSpecificPart();
                }
                if (action == null || !"com.salab.issuetracker".equals(str)) {
                    return;
                }
                if (action.equals("android.intent.action.PACKAGE_ADDED")) {
                    KeyguardSysDumpTrigger.this.isEnabled = true;
                } else if (action.equals("android.intent.action.PACKAGE_REMOVED")) {
                    KeyguardSysDumpTrigger.this.isEnabled = false;
                }
                KeyguardCarrierPasswordViewController$$ExternalSyntheticOutline0.m("pkg receiver ", "KeyguardSysDumpTrigger", KeyguardSysDumpTrigger.this.isEnabled());
            }
        };
        this.isDebug = DeviceType.getDebugLevel() == DeviceType.DEBUG_LEVEL_MID;
        delayableExecutor.execute(new Runnable() { // from class: com.android.systemui.keyguard.KeyguardSysDumpTrigger.1
            @Override // java.lang.Runnable
            public final void run() {
                KeyguardSysDumpTrigger keyguardSysDumpTrigger = KeyguardSysDumpTrigger.this;
                boolean z = false;
                try {
                    keyguardSysDumpTrigger.context.getPackageManager().getPackageInfo("com.salab.issuetracker", 0);
                    z = true;
                } catch (PackageManager.NameNotFoundException unused) {
                }
                keyguardSysDumpTrigger.isEnabled = z;
                KeyguardCarrierPasswordViewController$$ExternalSyntheticOutline0.m("isInstalled: ", "KeyguardSysDumpTrigger", KeyguardSysDumpTrigger.this.isEnabled());
            }
        });
        IntentFilter intentFilter = new IntentFilter("android.intent.action.PACKAGE_ADDED");
        intentFilter.addAction("android.intent.action.PACKAGE_REMOVED");
        intentFilter.addDataScheme("package");
        Unit unit = Unit.INSTANCE;
        BroadcastDispatcher.registerReceiver$default(broadcastDispatcher, broadcastReceiver, intentFilter, null, null, 0, null, 60);
    }

    public static final void access$sendBroadcastToIssueTracker(KeyguardSysDumpTrigger keyguardSysDumpTrigger, int i, long j) {
        Context context = keyguardSysDumpTrigger.context;
        Intent intent = new Intent("com.sec.android.ISSUE_TRACKER_ACTION");
        intent.setPackage("com.salab.issuetracker");
        intent.putExtra("ERRCODE", -125);
        intent.putExtra("ERRNAME", keyguardSysDumpTrigger.getTriggerMsg(i));
        intent.putExtra("ERRPKG", "com.android.systemui");
        intent.putExtra("ERRMSG", keyguardSysDumpTrigger.getTriggerMsg(i) + " / " + LogUtil.makeDateTimeStr(j));
        intent.putExtra("EXTLOG", (String) null);
        context.sendBroadcastAsUser(intent, UserHandle.SYSTEM);
        Toast.makeText(keyguardSysDumpTrigger.context, "dump in progress", 0).show();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public final void sendIssueReportIntent(final int i, final long j) {
        if (isEnabled()) {
            final int selectedUserId = this.selectedUserInteractor.getSelectedUserId(false);
            Log.d("KeyguardSysDumpTrigger", "sendIntent reason=" + i + " currentUser=" + selectedUserId);
            this.powerManager.userActivity(SystemClock.uptimeMillis(), false);
            this.bgHandler.post(new Runnable() { // from class: com.android.systemui.keyguard.KeyguardSysDumpTrigger$sendIssueReportIntent$1
                @Override // java.lang.Runnable
                public final void run() {
                    int i2 = selectedUserId;
                    boolean z = i2 == 0;
                    if (z) {
                        KeyguardSysDumpTrigger.access$sendBroadcastToIssueTracker(this, i, j);
                    } else {
                        if (z) {
                            return;
                        }
                        RecordingInputConnection$$ExternalSyntheticOutline0.m(i2, "Unable to report issue currentUser=", "KeyguardSysDumpTrigger");
                    }
                }
            });
        }
    }

    public final synchronized void cancel() {
        Runnable runnable = this.cancelExecToken;
        if (runnable != null) {
            runnable.run();
            this.cancelExecToken = null;
            android.util.Log.d("KeyguardSysDumpTrigger", "cancel");
        }
    }

    public final int[] getKeys() {
        return KEY;
    }

    public final String getTriggerMsg(int i) {
        return AbstractResolvableFuture$$ExternalSyntheticOutline0.m(i > 1 ? "systemui heap dump - " : "lockscreen dump - ", i != 0 ? i != 1 ? i != 2 ? i != 3 ? i != 4 ? "" : "Wrong behavior" : "too many views. notiCount : 0. totalView : 0" : "saved heap dump" : "input keys" : "timeout of app resume");
    }

    public final boolean isEnabled() {
        return this.isEnabled && this.isDebug;
    }

    public final void sendIntent(final int i, final long j) {
        if (isEnabled()) {
            final int selectedUserId = this.selectedUserInteractor.getSelectedUserId(false);
            final boolean isUserUnlocked = this.userManager.isUserUnlocked(0);
            StringBuilder m = RowColumnMeasurePolicyKt$$ExternalSyntheticOutline0.m(i, selectedUserId, "sendIntent reason=", " currentUser=", " userUnlocked=");
            m.append(isUserUnlocked);
            Log.d("KeyguardSysDumpTrigger", m.toString());
            this.powerManager.userActivity(SystemClock.uptimeMillis(), false);
            this.bgHandler.post(new Runnable() { // from class: com.android.systemui.keyguard.KeyguardSysDumpTrigger$sendIntent$1
                @Override // java.lang.Runnable
                public final void run() {
                    boolean z = selectedUserId == 0 && isUserUnlocked;
                    if (z) {
                        KeyguardSysDumpTrigger.access$sendBroadcastToIssueTracker(this, i, j);
                        return;
                    }
                    if (z) {
                        return;
                    }
                    Intent intent = new Intent();
                    intent.setComponent(KeyguardSysDumpTrigger.SYSDUMP_COMPONENT_NAME);
                    intent.setFlags(335544320);
                    intent.putExtra("occluded", true);
                    try {
                        ActivityTaskManager.getService().startActivityAsUser((IApplicationThread) null, this.context.getBasePackageName(), this.context.getAttributionTag(), intent, intent.resolveTypeIfNeeded(this.context.getContentResolver()), (IBinder) null, (String) null, 0, QuickStepContract.SYSUI_STATE_NAV_BAR_VIS_GONE, (ProfilerInfo) null, (Bundle) null, UserHandle.CURRENT.getIdentifier());
                    } catch (RemoteException e) {
                        android.util.Log.w("KeyguardSysDumpTrigger", "unable to start activity", e);
                    }
                }
            });
        }
    }

    public final synchronized void start(final int i, long j, final long j2) {
        cancel();
        this.cancelExecToken = this.executor.executeDelayed(new Runnable() { // from class: com.android.systemui.keyguard.KeyguardSysDumpTrigger$start$1
            @Override // java.lang.Runnable
            public final void run() {
                KeyguardSysDumpTrigger keyguardSysDumpTrigger = KeyguardSysDumpTrigger.this;
                int i2 = i;
                long j3 = j2;
                if (j3 <= 0) {
                    j3 = System.currentTimeMillis();
                }
                keyguardSysDumpTrigger.sendIntent(i2, j3);
            }
        }, j);
        android.util.Log.d("KeyguardSysDumpTrigger", "start " + j);
    }

    public static /* synthetic */ void isEnabled$annotations() {
    }
}
