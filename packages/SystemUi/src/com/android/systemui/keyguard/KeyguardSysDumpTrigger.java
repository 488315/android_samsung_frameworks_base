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
import androidx.concurrent.futures.AbstractResolvableFuture$$ExternalSyntheticOutline0;
import androidx.core.animation.ValueAnimator$$ExternalSyntheticOutline0;
import androidx.recyclerview.widget.GridLayoutManager$$ExternalSyntheticOutline0;
import com.android.keyguard.KeyguardCarrierPasswordViewController$$ExternalSyntheticOutline0;
import com.android.keyguard.KeyguardUpdateMonitor;
import com.android.systemui.broadcast.BroadcastDispatcher;
import com.android.systemui.statusbar.notification.collection.notifcollection.CommonNotifCollection;
import com.android.systemui.util.DeviceType;
import com.android.systemui.util.LogUtil;
import com.android.systemui.util.concurrency.DelayableExecutor;
import com.android.systemui.util.concurrency.ExecutorImpl;
import com.samsung.android.desktopsystemui.sharedlib.system.QuickStepContract;
import java.util.Timer;
import kotlin.Unit;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.Intrinsics;

/* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
/* loaded from: classes.dex */
public final class KeyguardSysDumpTrigger {
    public static final int[] KEY;
    public static final ComponentName SYSDUMP_COMPONENT_NAME;
    public final Handler bgHandler;
    public ExecutorImpl.ExecutionToken cancelExecToken;
    public final Context context;
    public String dumpPath;
    public final DelayableExecutor executor;
    public final boolean isDebug;
    public boolean isEnabled;
    public int keyIndex;
    public final PowerManager powerManager;
    public long prevEventTime;
    public final UserManager userManager;
    public long viewCount;
    public final WakefulnessLifecycle wakefulnessLifecycle;

    static {
        new Companion(null);
        KEY = new int[]{24, 24, 24, 25, 25, 25, 24, 25, 24, 25};
        SYSDUMP_COMPONENT_NAME = new ComponentName("com.sec.android.app.servicemodeapp", "com.sec.android.app.servicemodeapp.SysDump");
    }

    public KeyguardSysDumpTrigger(Context context, DelayableExecutor delayableExecutor, Handler handler, PowerManager powerManager, UserManager userManager, BroadcastDispatcher broadcastDispatcher, WakefulnessLifecycle wakefulnessLifecycle, CommonNotifCollection commonNotifCollection) {
        this.context = context;
        this.executor = delayableExecutor;
        this.bgHandler = handler;
        this.powerManager = powerManager;
        this.userManager = userManager;
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
                if (action == null || !Intrinsics.areEqual("com.salab.issuetracker", str)) {
                    return;
                }
                if (Intrinsics.areEqual(action, "android.intent.action.PACKAGE_ADDED")) {
                    KeyguardSysDumpTrigger.this.isEnabled = true;
                } else if (Intrinsics.areEqual(action, "android.intent.action.PACKAGE_REMOVED")) {
                    KeyguardSysDumpTrigger.this.isEnabled = false;
                }
                KeyguardCarrierPasswordViewController$$ExternalSyntheticOutline0.m62m("pkg receiver ", KeyguardSysDumpTrigger.this.isEnabled(), "KeyguardSysDumpTrigger");
            }
        };
        this.isDebug = DeviceType.getDebugLevel() == 1;
        ((ExecutorImpl) delayableExecutor).execute(new Runnable() { // from class: com.android.systemui.keyguard.KeyguardSysDumpTrigger.1
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
                KeyguardCarrierPasswordViewController$$ExternalSyntheticOutline0.m62m("isInstalled: ", KeyguardSysDumpTrigger.this.isEnabled(), "KeyguardSysDumpTrigger");
            }
        });
        IntentFilter intentFilter = new IntentFilter("android.intent.action.PACKAGE_ADDED");
        intentFilter.addAction("android.intent.action.PACKAGE_REMOVED");
        intentFilter.addDataScheme("package");
        Unit unit = Unit.INSTANCE;
        BroadcastDispatcher.registerReceiver$default(broadcastDispatcher, broadcastReceiver, intentFilter, null, null, 0, null, 60);
    }

    public final int[] getKeys() {
        return KEY;
    }

    public final String getTriggerMsg(int i) {
        return AbstractResolvableFuture$$ExternalSyntheticOutline0.m14m(i > 1 ? "systemui heap dump - " : "lockscreen dump - ", i != 0 ? i != 1 ? i != 2 ? i != 3 ? "" : ValueAnimator$$ExternalSyntheticOutline0.m25m("too many views. notiCount : 0. totalView : ", this.viewCount) : "saved heap dump" : "input keys" : "timeout of app resume");
    }

    public final boolean isEnabled() {
        return this.isEnabled && this.isDebug;
    }

    public final void sendIntent(final int i, final long j) {
        if (isEnabled()) {
            final int currentUser = KeyguardUpdateMonitor.getCurrentUser();
            final boolean isUserUnlocked = this.userManager.isUserUnlocked(0);
            StringBuilder m45m = GridLayoutManager$$ExternalSyntheticOutline0.m45m("sendIntent reason=", i, " currentUser=", currentUser, " userUnlocked=");
            m45m.append(isUserUnlocked);
            Log.m138d("KeyguardSysDumpTrigger", m45m.toString());
            this.powerManager.userActivity(SystemClock.uptimeMillis(), false);
            this.bgHandler.post(new Runnable() { // from class: com.android.systemui.keyguard.KeyguardSysDumpTrigger$sendIntent$1
                @Override // java.lang.Runnable
                public final void run() {
                    boolean z = currentUser == 0 && isUserUnlocked;
                    if (!z) {
                        if (z) {
                            return;
                        }
                        Intent intent = new Intent();
                        intent.setComponent(KeyguardSysDumpTrigger.SYSDUMP_COMPONENT_NAME);
                        intent.setFlags(335544320);
                        intent.putExtra("occluded", true);
                        try {
                            ActivityTaskManager.getService().startActivityAsUser((IApplicationThread) null, this.context.getBasePackageName(), this.context.getAttributionTag(), intent, intent.resolveTypeIfNeeded(this.context.getContentResolver()), (IBinder) null, (String) null, 0, QuickStepContract.SYSUI_STATE_NAV_BAR_VIS_GONE, (ProfilerInfo) null, (Bundle) null, UserHandle.CURRENT.getIdentifier());
                            return;
                        } catch (RemoteException e) {
                            android.util.Log.w("KeyguardSysDumpTrigger", "unable to start activity", e);
                            return;
                        }
                    }
                    Context context = this.context;
                    Intent intent2 = new Intent("com.sec.android.ISSUE_TRACKER_ACTION");
                    KeyguardSysDumpTrigger keyguardSysDumpTrigger = this;
                    int i2 = i;
                    long j2 = j;
                    intent2.setPackage("com.salab.issuetracker");
                    intent2.putExtra("ERRCODE", -125);
                    intent2.putExtra("ERRNAME", keyguardSysDumpTrigger.getTriggerMsg(i2));
                    intent2.putExtra("ERRPKG", "com.android.systemui");
                    intent2.putExtra("ERRMSG", keyguardSysDumpTrigger.getTriggerMsg(i2) + " / " + LogUtil.makeDateTimeStr(j2));
                    intent2.putExtra("EXTLOG", keyguardSysDumpTrigger.dumpPath);
                    context.sendBroadcastAsUser(intent2, UserHandle.SYSTEM);
                    Toast.makeText(this.context, "dump in progress", 0).show();
                    int i3 = i;
                    if (i3 == 2 || i3 == 3) {
                        KeyguardSysDumpTrigger keyguardSysDumpTrigger2 = this;
                        keyguardSysDumpTrigger2.dumpPath = null;
                        keyguardSysDumpTrigger2.viewCount = 0L;
                    }
                }
            });
        }
    }

    public final synchronized void start(final int i, long j, final long j2) {
        synchronized (this) {
            ExecutorImpl.ExecutionToken executionToken = this.cancelExecToken;
            if (executionToken != null) {
                executionToken.run();
                this.cancelExecToken = null;
                android.util.Log.d("KeyguardSysDumpTrigger", "cancel");
            }
        }
        this.cancelExecToken = this.executor.executeDelayed(j, new Runnable() { // from class: com.android.systemui.keyguard.KeyguardSysDumpTrigger$start$1
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
        });
        android.util.Log.d("KeyguardSysDumpTrigger", "start " + j);
    }

    /* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
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

    public static /* synthetic */ void isEnabled$annotations() {
    }
}
