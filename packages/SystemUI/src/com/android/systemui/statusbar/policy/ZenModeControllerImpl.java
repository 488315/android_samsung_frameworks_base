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
import android.provider.Settings;
import android.service.notification.ZenModeConfig;
import android.text.format.DateFormat;
import android.util.ArrayMap;
import android.util.Log;
import androidx.appcompat.widget.ListPopupWindow$$ExternalSyntheticOutline0;
import com.android.keyguard.CarrierTextController$$ExternalSyntheticOutline0;
import com.android.keyguard.KeyguardSecUpdateMonitorImpl$$ExternalSyntheticOutline0;
import com.android.systemui.Dumpable;
import com.android.systemui.broadcast.BroadcastDispatcher;
import com.android.systemui.dump.DumpManager;
import com.android.systemui.plugins.subscreen.SubRoom;
import com.android.systemui.settings.UserTracker;
import com.android.systemui.settings.UserTrackerImpl;
import com.android.systemui.statusbar.policy.ZenModeController;
import com.android.systemui.util.SettingsHelper;
import com.android.systemui.util.SystemUIAnalytics;
import com.android.systemui.util.settings.GlobalSettings;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.Objects;
import java.util.function.Consumer;

/* loaded from: classes3.dex */
public class ZenModeControllerImpl implements ZenModeController, Dumpable {
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
    public final UserTracker mUserTracker;
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
            if (Settings.Global.getUriFor("device_provisioned").equals(uri) || Settings.Secure.getUriFor(SettingsHelper.INDEX_USER_SETUP_COMPLETE).equals(uri)) {
                ZenModeControllerImpl zenModeControllerImpl = ZenModeControllerImpl.this;
                zenModeControllerImpl.fireSafeChange(new ZenModeControllerImpl$$ExternalSyntheticLambda2(zenModeControllerImpl.isZenAvailable()));
            }
        }

        public final void register() {
            if (this.mRegistered) {
                this.mResolver.unregisterContentObserver(this);
            }
            this.mResolver.registerContentObserver(Settings.Global.getUriFor("device_provisioned"), false, this);
            this.mResolver.registerContentObserver(Settings.Secure.getUriFor(SettingsHelper.INDEX_USER_SETUP_COMPLETE), false, this, ZenModeControllerImpl.this.mUserId);
            this.mRegistered = true;
            ZenModeControllerImpl zenModeControllerImpl = ZenModeControllerImpl.this;
            zenModeControllerImpl.fireSafeChange(new ZenModeControllerImpl$$ExternalSyntheticLambda2(zenModeControllerImpl.isZenAvailable()));
        }
    }

    /* JADX WARN: Type inference failed for: r1v0, types: [com.android.systemui.statusbar.policy.ZenModeControllerImpl$4] */
    public ZenModeControllerImpl(Context context, Handler handler, BroadcastDispatcher broadcastDispatcher, DumpManager dumpManager, GlobalSettings globalSettings, UserTracker userTracker) {
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
                broadcastDispatcher2.registerReceiver(anonymousClass4, new IntentFilter("android.app.action.NEXT_ALARM_CLOCK_CHANGED"), null, UserHandle.of(zenModeControllerImpl.mUserId));
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
                    zenModeControllerImpl.fireSafeChange(new ZenModeControllerImpl$$ExternalSyntheticLambda3());
                }
            }
        };
        this.mContext = context;
        this.mBroadcastDispatcher = broadcastDispatcher;
        this.mUserTracker = userTracker;
        this.mGlobalSettings = globalSettings;
        ContentObserver contentObserver = new ContentObserver(handler) { // from class: com.android.systemui.statusbar.policy.ZenModeControllerImpl.2
            @Override // android.database.ContentObserver
            public final void onChange(boolean z) {
                ZenModeConfig.ZenRule zenRule;
                ArrayMap arrayMap;
                final int i = ZenModeControllerImpl.this.mGlobalSettings.getInt("zen_mode", 0);
                ListPopupWindow$$ExternalSyntheticOutline0.m(i, "Zen mode setting changed to ", "ZenModeController");
                ZenModeControllerImpl.this.updateZenMode(i);
                ZenModeControllerImpl zenModeControllerImpl = ZenModeControllerImpl.this;
                zenModeControllerImpl.getClass();
                zenModeControllerImpl.fireSafeChange(new Consumer() { // from class: com.android.systemui.statusbar.policy.ZenModeControllerImpl$$ExternalSyntheticLambda4
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
                            if (zenRule.isActive()) {
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
        ContentObserver contentObserver2 = new ContentObserver(handler) { // from class: com.android.systemui.statusbar.policy.ZenModeControllerImpl.3
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
        globalSettings.registerContentObserverAsync("zen_mode", contentObserver);
        globalSettings.registerContentObserverAsync("zen_mode_config_etag", contentObserver2);
        updateZenMode(globalSettings.getInt("zen_mode", 0));
        updateZenModeConfig();
        updateConsolidatedNotificationPolicy();
        this.mAlarmManager = (AlarmManager) context.getSystemService("alarm");
        SetupObserver setupObserver = new SetupObserver(handler);
        this.mSetupObserver = setupObserver;
        setupObserver.register();
        ((UserTrackerImpl) userTracker).addCallback(callback, new HandlerExecutor(handler));
        callback.onUserChanged(((UserTrackerImpl) userTracker).getUserId(), context);
        String simpleName = getClass().getSimpleName();
        dumpManager.getClass();
        DumpManager.registerDumpable$default(dumpManager, simpleName, this);
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
        StringBuilder sbM = KeyguardSecUpdateMonitorImpl$$ExternalSyntheticOutline0.m(CarrierTextController$$ExternalSyntheticOutline0.m(printWriter, "ZenModeControllerImpl:", "  mZenMode="), this.mZenMode, printWriter, "  mConfig=");
        sbM.append(this.mConfig);
        printWriter.println(sbM.toString());
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
        return (Settings.Global.getInt(setupObserver.mResolver, "device_provisioned", 0) == 0 || Settings.Secure.getIntForUser(setupObserver.mResolver, SettingsHelper.INDEX_USER_SETUP_COMPLETE, 0, ZenModeControllerImpl.this.mUserId) == 0) ? false : true;
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
        this.mNoMan.setZenMode(i, uri, str, true);
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
        this.mConfig = zenModeConfig;
        this.mZenUpdateTime = System.currentTimeMillis();
        fireConfigChanged(zenModeConfig);
        NotificationManager.Policy consolidatedNotificationPolicy = this.mNoMan.getConsolidatedNotificationPolicy();
        if (Objects.equals(consolidatedNotificationPolicy, this.mConsolidatedNotificationPolicy)) {
            return;
        }
        this.mConsolidatedNotificationPolicy = consolidatedNotificationPolicy;
        fireSafeChange(new ZenModeControllerImpl$$ExternalSyntheticLambda0(consolidatedNotificationPolicy));
    }
}
