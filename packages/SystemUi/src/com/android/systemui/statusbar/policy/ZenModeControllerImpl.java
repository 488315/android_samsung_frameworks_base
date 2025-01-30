package com.android.systemui.statusbar.policy;

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
import com.android.keyguard.KeyguardSecUpdateMonitorImpl$$ExternalSyntheticOutline0;
import com.android.systemui.Dumpable;
import com.android.systemui.broadcast.BroadcastDispatcher;
import com.android.systemui.dump.DumpManager;
import com.android.systemui.qs.SettingObserver;
import com.android.systemui.plugins.subscreen.SubRoom;
import com.android.systemui.settings.UserTracker;
import com.android.systemui.settings.UserTrackerImpl;
import com.android.systemui.statusbar.policy.ZenModeController;
import com.android.systemui.util.SystemUIAnalytics;
import com.android.systemui.util.Utils;
import com.android.systemui.util.settings.GlobalSettings;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.Objects;
import java.util.function.Consumer;

/* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
/* loaded from: classes2.dex */
public final class ZenModeControllerImpl implements ZenModeController, Dumpable {
    public final BroadcastDispatcher mBroadcastDispatcher;
    public final ArrayList mCallbacks = new ArrayList();
    public final Object mCallbacksLock = new Object();
    public ZenModeConfig mConfig;
    public final C34523 mConfigSetting;
    public NotificationManager.Policy mConsolidatedNotificationPolicy;
    public final Context mContext;
    public final C34512 mModeSetting;
    public final NotificationManager mNoMan;
    public final C34534 mReceiver;
    public boolean mRegistered;
    public final SetupObserver mSetupObserver;
    public final UserTracker.Callback mUserChangedCallback;
    public int mUserId;
    public final UserManager mUserManager;
    public final UserTracker mUserTracker;
    public int mZenMode;
    public long mZenUpdateTime;

    /* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
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
                boolean isZenAvailable = zenModeControllerImpl.isZenAvailable();
                synchronized (zenModeControllerImpl.mCallbacksLock) {
                    Utils.safeForeach(zenModeControllerImpl.mCallbacks, new ZenModeControllerImpl$$ExternalSyntheticLambda3(isZenAvailable));
                }
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
            boolean isZenAvailable = zenModeControllerImpl.isZenAvailable();
            synchronized (zenModeControllerImpl.mCallbacksLock) {
                Utils.safeForeach(zenModeControllerImpl.mCallbacks, new ZenModeControllerImpl$$ExternalSyntheticLambda3(isZenAvailable));
            }
        }
    }

    /* JADX WARN: Type inference failed for: r10v0, types: [com.android.systemui.qs.SettingObserver, com.android.systemui.statusbar.policy.ZenModeControllerImpl$2] */
    /* JADX WARN: Type inference failed for: r12v0, types: [com.android.systemui.qs.SettingObserver, com.android.systemui.statusbar.policy.ZenModeControllerImpl$3] */
    /* JADX WARN: Type inference failed for: r1v2, types: [com.android.systemui.statusbar.policy.ZenModeControllerImpl$4] */
    public ZenModeControllerImpl(Context context, Handler handler, BroadcastDispatcher broadcastDispatcher, DumpManager dumpManager, GlobalSettings globalSettings, UserTracker userTracker) {
        UserTracker.Callback callback = new UserTracker.Callback() { // from class: com.android.systemui.statusbar.policy.ZenModeControllerImpl.1
            @Override // com.android.systemui.settings.UserTracker.Callback
            public final void onUserChanged(int i, Context context2) {
                ZenModeControllerImpl zenModeControllerImpl = ZenModeControllerImpl.this;
                zenModeControllerImpl.mUserId = i;
                boolean z = zenModeControllerImpl.mRegistered;
                C34534 c34534 = zenModeControllerImpl.mReceiver;
                BroadcastDispatcher broadcastDispatcher2 = zenModeControllerImpl.mBroadcastDispatcher;
                if (z) {
                    broadcastDispatcher2.unregisterReceiver(c34534);
                }
                IntentFilter intentFilter = new IntentFilter("android.app.action.NEXT_ALARM_CLOCK_CHANGED");
                intentFilter.addAction("android.os.action.ACTION_EFFECTS_SUPPRESSOR_CHANGED");
                broadcastDispatcher2.registerReceiver(c34534, intentFilter, null, UserHandle.of(zenModeControllerImpl.mUserId));
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
                    synchronized (zenModeControllerImpl.mCallbacksLock) {
                        final int i = 0;
                        Utils.safeForeach(zenModeControllerImpl.mCallbacks, new Consumer() { // from class: com.android.systemui.statusbar.policy.ZenModeControllerImpl$$ExternalSyntheticLambda2
                            @Override // java.util.function.Consumer
                            public final void accept(Object obj) {
                                switch (i) {
                                    case 0:
                                        ((ZenModeController.Callback) obj).getClass();
                                        break;
                                    default:
                                        ((ZenModeController.Callback) obj).getClass();
                                        break;
                                }
                            }
                        });
                    }
                }
                if ("android.os.action.ACTION_EFFECTS_SUPPRESSOR_CHANGED".equals(intent.getAction())) {
                    ZenModeControllerImpl zenModeControllerImpl2 = ZenModeControllerImpl.this;
                    synchronized (zenModeControllerImpl2.mCallbacksLock) {
                        final int i2 = 1;
                        Utils.safeForeach(zenModeControllerImpl2.mCallbacks, new Consumer() { // from class: com.android.systemui.statusbar.policy.ZenModeControllerImpl$$ExternalSyntheticLambda2
                            @Override // java.util.function.Consumer
                            public final void accept(Object obj) {
                                switch (i2) {
                                    case 0:
                                        ((ZenModeController.Callback) obj).getClass();
                                        break;
                                    default:
                                        ((ZenModeController.Callback) obj).getClass();
                                        break;
                                }
                            }
                        });
                    }
                }
            }
        };
        this.mContext = context;
        this.mBroadcastDispatcher = broadcastDispatcher;
        this.mUserTracker = userTracker;
        UserTrackerImpl userTrackerImpl = (UserTrackerImpl) userTracker;
        ?? r10 = new SettingObserver(globalSettings, handler, "zen_mode", userTrackerImpl.getUserId()) { // from class: com.android.systemui.statusbar.policy.ZenModeControllerImpl.2
            @Override // com.android.systemui.qs.SettingObserver
            public final void handleValueChanged(final int i, boolean z) {
                ZenModeConfig.ZenRule zenRule;
                ArrayMap arrayMap;
                ZenModeControllerImpl.this.updateZenMode(i);
                ZenModeControllerImpl zenModeControllerImpl = ZenModeControllerImpl.this;
                synchronized (zenModeControllerImpl.mCallbacksLock) {
                    Utils.safeForeach(zenModeControllerImpl.mCallbacks, new Consumer() { // from class: com.android.systemui.statusbar.policy.ZenModeControllerImpl$$ExternalSyntheticLambda1
                        @Override // java.util.function.Consumer
                        public final void accept(Object obj) {
                            ((ZenModeController.Callback) obj).onZenChanged(i);
                        }
                    });
                }
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
                        SystemUIAnalytics.sendEventCDLog("", "NSTE0300", "zen_mode_from", "manual", "zen_mode_byRuleApp", zenModeControllerImpl2.mConfig.manualRule.enabler, "zen_mode_duration", Integer.toString(Settings.Secure.getInt(zenModeControllerImpl2.mContext.getContentResolver(), "zen_duration", 0)));
                    } else if (z3) {
                        SystemUIAnalytics.sendEventCDLog("", "NSTE0300", "zen_mode_from", "byRule", "zen_mode_byRuleApp", zenRule.getPkg());
                    }
                }
            }
        };
        this.mModeSetting = r10;
        ?? r12 = new SettingObserver(globalSettings, handler, "zen_mode_config_etag", userTrackerImpl.getUserId()) { // from class: com.android.systemui.statusbar.policy.ZenModeControllerImpl.3
            @Override // com.android.systemui.qs.SettingObserver
            public final void handleValueChanged(int i, boolean z) {
                try {
                    Trace.beginSection("updateZenModeConfig");
                    ZenModeControllerImpl.this.updateZenModeConfig();
                } finally {
                    Trace.endSection();
                }
            }
        };
        this.mConfigSetting = r12;
        this.mNoMan = (NotificationManager) context.getSystemService(SubRoom.EXTRA_VALUE_NOTIFICATION);
        r10.setListening(true);
        updateZenMode(r10.getValue());
        r12.setListening(true);
        updateZenModeConfig();
        updateConsolidatedNotificationPolicy();
        SetupObserver setupObserver = new SetupObserver(handler);
        this.mSetupObserver = setupObserver;
        setupObserver.register();
        this.mUserManager = (UserManager) context.getSystemService(UserManager.class);
        userTrackerImpl.addCallback(callback, new HandlerExecutor(handler));
        dumpManager.getClass();
        DumpManager.registerDumpable$default(dumpManager, "ZenModeControllerImpl", this);
    }

    @Override // com.android.systemui.statusbar.policy.CallbackController
    public final void addCallback(Object obj) {
        ZenModeController.Callback callback = (ZenModeController.Callback) obj;
        synchronized (this.mCallbacksLock) {
            this.mCallbacks.add(callback);
        }
    }

    @Override // com.android.systemui.Dumpable
    public final void dump(PrintWriter printWriter, String[] strArr) {
        StringBuilder m77m = KeyguardSecUpdateMonitorImpl$$ExternalSyntheticOutline0.m77m(KeyguardSecUpdateMonitorImpl$$ExternalSyntheticOutline0.m75m(printWriter, "ZenModeControllerImpl:", "  mZenMode="), this.mZenMode, printWriter, "  mConfig=");
        m77m.append(this.mConfig);
        printWriter.println(m77m.toString());
        printWriter.println("  mConsolidatedNotificationPolicy=" + this.mConsolidatedNotificationPolicy);
        printWriter.println("  mZenUpdateTime=" + ((Object) DateFormat.format("MM-dd HH:mm:ss", this.mZenUpdateTime)));
    }

    public void fireConfigChanged(ZenModeConfig zenModeConfig) {
        synchronized (this.mCallbacksLock) {
            Utils.safeForeach(this.mCallbacks, new ZenModeControllerImpl$$ExternalSyntheticLambda0(zenModeConfig, 0));
        }
    }

    public final boolean isZenAvailable() {
        SetupObserver setupObserver = this.mSetupObserver;
        if (Settings.Global.getInt(setupObserver.mResolver, "device_provisioned", 0) != 0) {
            return Settings.Secure.getIntForUser(setupObserver.mResolver, "user_setup_complete", 0, ZenModeControllerImpl.this.mUserId) != 0;
        }
        return false;
    }

    @Override // com.android.systemui.statusbar.policy.CallbackController
    public final void removeCallback(Object obj) {
        ZenModeController.Callback callback = (ZenModeController.Callback) obj;
        synchronized (this.mCallbacksLock) {
            this.mCallbacks.remove(callback);
        }
    }

    public final void setZen(int i, Uri uri, String str) {
        this.mNoMan.setZenMode(i, uri, str);
    }

    public void updateConsolidatedNotificationPolicy() {
        NotificationManager.Policy consolidatedNotificationPolicy = this.mNoMan.getConsolidatedNotificationPolicy();
        if (Objects.equals(consolidatedNotificationPolicy, this.mConsolidatedNotificationPolicy)) {
            return;
        }
        this.mConsolidatedNotificationPolicy = consolidatedNotificationPolicy;
        synchronized (this.mCallbacksLock) {
            Utils.safeForeach(this.mCallbacks, new ZenModeControllerImpl$$ExternalSyntheticLambda0(consolidatedNotificationPolicy, 2));
        }
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
            synchronized (this.mCallbacksLock) {
                Utils.safeForeach(this.mCallbacks, new ZenModeControllerImpl$$ExternalSyntheticLambda0(zenRule2, 1));
            }
        }
        NotificationManager.Policy consolidatedNotificationPolicy = this.mNoMan.getConsolidatedNotificationPolicy();
        if (Objects.equals(consolidatedNotificationPolicy, this.mConsolidatedNotificationPolicy)) {
            return;
        }
        this.mConsolidatedNotificationPolicy = consolidatedNotificationPolicy;
        synchronized (this.mCallbacksLock) {
            Utils.safeForeach(this.mCallbacks, new ZenModeControllerImpl$$ExternalSyntheticLambda0(consolidatedNotificationPolicy, 2));
        }
    }
}
