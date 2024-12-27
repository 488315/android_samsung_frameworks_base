package com.android.systemui.statusbar.policy;

import android.app.AlarmManager;
import android.app.NotificationManager;
import android.content.BroadcastReceiver;
import android.content.ContentResolver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.database.ContentObserver;
import android.net.Uri;
import android.os.Handler;
import android.os.HandlerExecutor;
import android.os.Trace;
import android.os.UserHandle;
import android.os.UserManager;
import android.provider.Settings;
import android.service.notification.ZenModeConfig;
import android.text.format.DateFormat;
import android.util.ArrayMap;
import android.util.Log;
import androidx.appcompat.widget.ListPopupWindow$$ExternalSyntheticOutline0;
import com.android.keyguard.CarrierTextController$$ExternalSyntheticOutline0;
import com.android.keyguard.KeyguardSecUpdateMonitorImpl$$ExternalSyntheticOutline0;
import com.android.systemui.Dumpable;
import com.android.systemui.Flags;
import com.android.systemui.broadcast.BroadcastDispatcher;
import com.android.systemui.dump.DumpManager;
import com.android.systemui.plugins.subscreen.SubRoom;
import com.android.systemui.settings.UserTracker;
import com.android.systemui.settings.UserTrackerImpl;
import com.android.systemui.statusbar.policy.ZenModeController;
import com.android.systemui.util.SystemUIAnalytics;
import com.android.systemui.util.settings.GlobalSettings;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.Objects;
import java.util.function.Consumer;

public final class ZenModeControllerImpl implements ZenModeController, Dumpable {
    public final AlarmManager mAlarmManager;
    public final BroadcastDispatcher mBroadcastDispatcher;
    public final ArrayList mCallbacks = new ArrayList();
    public final Object mCallbacksLock = new Object();
    public ZenModeConfig mConfig;
    public NotificationManager.Policy mConsolidatedNotificationPolicy;
    public final Context mContext;
    public final GlobalSettings mGlobalSettings;
    public final NotificationManager mNoMan;
    public final AnonymousClass4 mReceiver;
    public boolean mRegistered;
    public final SetupObserver mSetupObserver;
    public final UserTracker.Callback mUserChangedCallback;
    public int mUserId;
    public final UserManager mUserManager;
    public volatile int mZenMode;
    public long mZenUpdateTime;

    public final class SetupObserver extends ContentObserver {
        public boolean mRegistered;
        public final ContentResolver mResolver;

        public SetupObserver(Handler handler) {
            super(handler);
            this.mResolver = ZenModeControllerImpl.this.mContext.getContentResolver();
        }

        @Override // android.database.ContentObserver
        public final void onChange(boolean z, Uri uri) {
            if (Settings.Global.getUriFor("device_provisioned").equals(uri) || Settings.Secure.getUriFor("user_setup_complete").equals(uri)) {
                ZenModeControllerImpl zenModeControllerImpl = ZenModeControllerImpl.this;
                zenModeControllerImpl.fireSafeChange(new ZenModeControllerImpl$$ExternalSyntheticLambda4(zenModeControllerImpl.isZenAvailable()));
            }
        }

        public final void register() {
            if (this.mRegistered) {
                this.mResolver.unregisterContentObserver(this);
            }
            this.mResolver.registerContentObserver(Settings.Global.getUriFor("device_provisioned"), false, this);
            this.mResolver.registerContentObserver(Settings.Secure.getUriFor("user_setup_complete"), false, this, ZenModeControllerImpl.this.mUserId);
            this.mRegistered = true;
            ZenModeControllerImpl zenModeControllerImpl = ZenModeControllerImpl.this;
            zenModeControllerImpl.fireSafeChange(new ZenModeControllerImpl$$ExternalSyntheticLambda4(zenModeControllerImpl.isZenAvailable()));
        }
    }

    /* JADX WARN: Type inference failed for: r1v0, types: [com.android.systemui.statusbar.policy.ZenModeControllerImpl$4] */
    /* JADX WARN: Type inference failed for: r1v1, types: [com.android.systemui.statusbar.policy.ZenModeControllerImpl$3] */
    /* JADX WARN: Type inference failed for: r7v1, types: [com.android.systemui.statusbar.policy.ZenModeControllerImpl$2] */
    public ZenModeControllerImpl(Context context, Handler handler, Handler handler2, BroadcastDispatcher broadcastDispatcher, DumpManager dumpManager, final GlobalSettings globalSettings, UserTracker userTracker) {
        UserTracker.Callback callback = new UserTracker.Callback() { // from class: com.android.systemui.statusbar.policy.ZenModeControllerImpl.1
            @Override // com.android.systemui.settings.UserTracker.Callback
            public final void onUserChanged(int i, Context context2) {
                ZenModeControllerImpl zenModeControllerImpl = ZenModeControllerImpl.this;
                zenModeControllerImpl.mUserId = i;
                boolean z = zenModeControllerImpl.mRegistered;
                AnonymousClass4 anonymousClass4 = zenModeControllerImpl.mReceiver;
                BroadcastDispatcher broadcastDispatcher2 = zenModeControllerImpl.mBroadcastDispatcher;
                if (z) {
                    broadcastDispatcher2.unregisterReceiver(anonymousClass4);
                }
                IntentFilter intentFilter = new IntentFilter("android.app.action.NEXT_ALARM_CLOCK_CHANGED");
                intentFilter.addAction("android.os.action.ACTION_EFFECTS_SUPPRESSOR_CHANGED");
                broadcastDispatcher2.registerReceiver(anonymousClass4, intentFilter, null, UserHandle.of(zenModeControllerImpl.mUserId));
                zenModeControllerImpl.mRegistered = true;
                zenModeControllerImpl.mSetupObserver.register();
            }
        };
        this.mUserChangedCallback = callback;
        this.mReceiver = new BroadcastReceiver() { // from class: com.android.systemui.statusbar.policy.ZenModeControllerImpl.4
            @Override // android.content.BroadcastReceiver
            public final void onReceive(Context context2, Intent intent) {
                if ("android.app.action.NEXT_ALARM_CLOCK_CHANGED".equals(intent.getAction())) {
                    ZenModeControllerImpl zenModeControllerImpl = ZenModeControllerImpl.this;
                    zenModeControllerImpl.getClass();
                    final int i = 0;
                    zenModeControllerImpl.fireSafeChange(new Consumer() { // from class: com.android.systemui.statusbar.policy.ZenModeControllerImpl$$ExternalSyntheticLambda5
                        @Override // java.util.function.Consumer
                        public final void accept(Object obj) {
                            ZenModeController.Callback callback2 = (ZenModeController.Callback) obj;
                            switch (i) {
                                case 0:
                                    callback2.onNextAlarmChanged();
                                    break;
                                default:
                                    callback2.onEffectsSupressorChanged();
                                    break;
                            }
                        }
                    });
                }
                if ("android.os.action.ACTION_EFFECTS_SUPPRESSOR_CHANGED".equals(intent.getAction())) {
                    ZenModeControllerImpl zenModeControllerImpl2 = ZenModeControllerImpl.this;
                    zenModeControllerImpl2.getClass();
                    final int i2 = 1;
                    zenModeControllerImpl2.fireSafeChange(new Consumer() { // from class: com.android.systemui.statusbar.policy.ZenModeControllerImpl$$ExternalSyntheticLambda5
                        @Override // java.util.function.Consumer
                        public final void accept(Object obj) {
                            ZenModeController.Callback callback2 = (ZenModeController.Callback) obj;
                            switch (i2) {
                                case 0:
                                    callback2.onNextAlarmChanged();
                                    break;
                                default:
                                    callback2.onEffectsSupressorChanged();
                                    break;
                            }
                        }
                    });
                }
            }
        };
        this.mContext = context;
        this.mBroadcastDispatcher = broadcastDispatcher;
        this.mGlobalSettings = globalSettings;
        final ?? r7 = new ContentObserver(handler) { // from class: com.android.systemui.statusbar.policy.ZenModeControllerImpl.2
            @Override // android.database.ContentObserver
            public final void onChange(boolean z) {
                ZenModeConfig.ZenRule zenRule;
                ArrayMap arrayMap;
                final int i = ZenModeControllerImpl.this.mGlobalSettings.getInt("zen_mode", 0);
                ListPopupWindow$$ExternalSyntheticOutline0.m(i, "Zen mode setting changed to ", "ZenModeController");
                ZenModeControllerImpl.this.updateZenMode(i);
                ZenModeControllerImpl zenModeControllerImpl = ZenModeControllerImpl.this;
                zenModeControllerImpl.getClass();
                zenModeControllerImpl.fireSafeChange(new Consumer() { // from class: com.android.systemui.statusbar.policy.ZenModeControllerImpl$$ExternalSyntheticLambda6
                    @Override // java.util.function.Consumer
                    public final void accept(Object obj) {
                        ((ZenModeController.Callback) obj).onZenChanged(i);
                    }
                });
                ZenModeControllerImpl zenModeControllerImpl2 = ZenModeControllerImpl.this;
                if (zenModeControllerImpl2.mZenMode != 0) {
                    Iterator it = zenModeControllerImpl2.mConfig.automaticRules.values().iterator();
                    while (true) {
                        if (!it.hasNext()) {
                            zenRule = null;
                            break;
                        } else {
                            zenRule = (ZenModeConfig.ZenRule) it.next();
                            if (zenRule.isAutomaticActive()) {
                                break;
                            }
                        }
                    }
                    ZenModeConfig zenModeConfig = zenModeControllerImpl2.mConfig;
                    boolean z2 = (zenModeConfig == null || zenModeConfig.manualRule == null) ? false : true;
                    boolean z3 = (zenModeConfig == null || zenModeConfig.manualRule != null || (arrayMap = zenModeConfig.automaticRules) == null || arrayMap.isEmpty() || zenRule == null) ? false : true;
                    if (z2) {
                        SystemUIAnalytics.sendEventCDLog("", SystemUIAnalytics.EID_DO_NOT_DISTURB, "zen_mode_from", "manual", "zen_mode_byRuleApp", zenModeControllerImpl2.mConfig.manualRule.enabler, "zen_mode_duration", Integer.toString(Settings.Secure.getInt(zenModeControllerImpl2.mContext.getContentResolver(), "zen_duration", 0)));
                    } else if (z3) {
                        SystemUIAnalytics.sendEventCDLog("", SystemUIAnalytics.EID_DO_NOT_DISTURB, "zen_mode_from", "byRule", "zen_mode_byRuleApp", zenRule.getPkg());
                    }
                }
            }
        };
        final ?? r1 = new ContentObserver(handler) { // from class: com.android.systemui.statusbar.policy.ZenModeControllerImpl.3
            @Override // android.database.ContentObserver
            public final void onChange(boolean z) {
                try {
                    Trace.beginSection("updateZenModeConfig");
                    ZenModeControllerImpl.this.updateZenModeConfig();
                } finally {
                    Trace.endSection();
                }
            }
        };
        this.mNoMan = (NotificationManager) context.getSystemService(SubRoom.EXTRA_VALUE_NOTIFICATION);
        Flags.FEATURE_FLAGS.getClass();
        handler2.post(new Runnable() { // from class: com.android.systemui.statusbar.policy.ZenModeControllerImpl$$ExternalSyntheticLambda2
            @Override // java.lang.Runnable
            public final void run() {
                GlobalSettings globalSettings2 = GlobalSettings.this;
                ContentObserver contentObserver = r7;
                ContentObserver contentObserver2 = r1;
                globalSettings2.registerContentObserverSync("zen_mode", contentObserver);
                globalSettings2.registerContentObserverSync("zen_mode_config_etag", contentObserver2);
            }
        });
        updateZenMode(globalSettings.getInt("zen_mode", 0));
        updateZenModeConfig();
        updateConsolidatedNotificationPolicy();
        this.mAlarmManager = (AlarmManager) context.getSystemService("alarm");
        SetupObserver setupObserver = new SetupObserver(handler);
        this.mSetupObserver = setupObserver;
        setupObserver.register();
        this.mUserManager = (UserManager) context.getSystemService(UserManager.class);
        UserTrackerImpl userTrackerImpl = (UserTrackerImpl) userTracker;
        userTrackerImpl.addCallback(callback, new HandlerExecutor(handler));
        callback.onUserChanged(userTrackerImpl.getUserId(), context);
        dumpManager.getClass();
        DumpManager.registerDumpable$default(dumpManager, "ZenModeControllerImpl", this);
    }

    @Override // com.android.systemui.statusbar.policy.CallbackController
    public final void addCallback(Object obj) {
        ZenModeController.Callback callback = (ZenModeController.Callback) obj;
        synchronized (this.mCallbacksLock) {
            Log.d("ZenModeController", "Added callback " + callback.getClass());
            this.mCallbacks.add(callback);
        }
    }

    @Override // com.android.systemui.Dumpable
    public final void dump(PrintWriter printWriter, String[] strArr) {
        StringBuilder m = KeyguardSecUpdateMonitorImpl$$ExternalSyntheticOutline0.m(CarrierTextController$$ExternalSyntheticOutline0.m(printWriter, "ZenModeControllerImpl:", "  mZenMode="), this.mZenMode, printWriter, "  mConfig=");
        m.append(this.mConfig);
        printWriter.println(m.toString());
        printWriter.println("  mConsolidatedNotificationPolicy=" + this.mConsolidatedNotificationPolicy);
        printWriter.println("  mZenUpdateTime=" + ((Object) DateFormat.format("MM-dd HH:mm:ss", this.mZenUpdateTime)));
    }

    public void fireConfigChanged(ZenModeConfig zenModeConfig) {
        fireSafeChange(new ZenModeControllerImpl$$ExternalSyntheticLambda0(zenModeConfig));
    }

    public final void fireSafeChange(Consumer consumer) {
        ArrayList arrayList;
        synchronized (this.mCallbacksLock) {
            arrayList = new ArrayList(this.mCallbacks);
        }
        int size = arrayList.size();
        for (int i = 0; i < size; i++) {
            consumer.accept((ZenModeController.Callback) arrayList.get(i));
        }
    }

    public final boolean isZenAvailable() {
        SetupObserver setupObserver = this.mSetupObserver;
        return (Settings.Global.getInt(setupObserver.mResolver, "device_provisioned", 0) == 0 || Settings.Secure.getIntForUser(setupObserver.mResolver, "user_setup_complete", 0, ZenModeControllerImpl.this.mUserId) == 0) ? false : true;
    }

    @Override // com.android.systemui.statusbar.policy.CallbackController
    public final void removeCallback(Object obj) {
        ZenModeController.Callback callback = (ZenModeController.Callback) obj;
        synchronized (this.mCallbacksLock) {
            Log.d("ZenModeController", "Removed callback " + callback.getClass());
            this.mCallbacks.remove(callback);
        }
    }

    public final void setZen(int i, Uri uri, String str) {
        if (android.app.Flags.modesApi()) {
            this.mNoMan.setZenMode(i, uri, str, true);
        } else {
            this.mNoMan.setZenMode(i, uri, str);
        }
    }

    public void updateConsolidatedNotificationPolicy() {
        NotificationManager.Policy consolidatedNotificationPolicy = this.mNoMan.getConsolidatedNotificationPolicy();
        if (Objects.equals(consolidatedNotificationPolicy, this.mConsolidatedNotificationPolicy)) {
            return;
        }
        this.mConsolidatedNotificationPolicy = consolidatedNotificationPolicy;
        fireSafeChange(new ZenModeControllerImpl$$ExternalSyntheticLambda0(consolidatedNotificationPolicy));
    }

    public void updateZenMode(int i) {
        this.mZenMode = i;
        this.mZenUpdateTime = System.currentTimeMillis();
    }

    public void updateZenModeConfig() {
        ZenModeConfig zenModeConfig = this.mNoMan.getZenModeConfig();
        if (Objects.equals(zenModeConfig, this.mConfig)) {
            return;
        }
        ZenModeConfig zenModeConfig2 = this.mConfig;
        ZenModeConfig.ZenRule zenRule = zenModeConfig2 != null ? zenModeConfig2.manualRule : null;
        this.mConfig = zenModeConfig;
        this.mZenUpdateTime = System.currentTimeMillis();
        fireConfigChanged(zenModeConfig);
        ZenModeConfig.ZenRule zenRule2 = zenModeConfig != null ? zenModeConfig.manualRule : null;
        if (!Objects.equals(zenRule, zenRule2)) {
            fireSafeChange(new ZenModeControllerImpl$$ExternalSyntheticLambda0(zenRule2));
        }
        NotificationManager.Policy consolidatedNotificationPolicy = this.mNoMan.getConsolidatedNotificationPolicy();
        if (Objects.equals(consolidatedNotificationPolicy, this.mConsolidatedNotificationPolicy)) {
            return;
        }
        this.mConsolidatedNotificationPolicy = consolidatedNotificationPolicy;
        fireSafeChange(new ZenModeControllerImpl$$ExternalSyntheticLambda0(consolidatedNotificationPolicy));
    }
}
