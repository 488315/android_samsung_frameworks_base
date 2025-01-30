package com.android.systemui.qs;

import android.app.ActivityManager;
import android.content.BroadcastReceiver;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Handler;
import android.provider.Settings;
import android.text.TextUtils;
import android.util.Log;
import androidx.activity.result.ActivityResultRegistry$$ExternalSyntheticOutline0;
import androidx.appcompat.app.AppCompatDelegateImpl$$ExternalSyntheticOutline0;
import androidx.recyclerview.widget.RecyclerView$$ExternalSyntheticOutline0;
import com.android.keyguard.KeyguardFMMViewController$$ExternalSyntheticOutline0;
import com.android.keyguard.logging.AbstractC0866xb1ce8deb;
import com.android.systemui.Dependency;
import com.android.systemui.Prefs;
import com.android.systemui.appops.AppOpItem$$ExternalSyntheticOutline0;
import com.android.systemui.broadcast.BroadcastDispatcher;
import com.android.systemui.qs.AutoAddTracker;
import com.android.systemui.qs.external.CustomTile;
import com.android.systemui.settings.UserTracker;
import com.android.systemui.settings.UserTrackerImpl;
import com.android.systemui.statusbar.phone.ManagedProfileController;
import com.android.systemui.statusbar.phone.ManagedProfileControllerImpl;
import com.android.systemui.util.DeviceState;
import com.android.systemui.util.SettingsHelper;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
/* loaded from: classes2.dex */
public final class SecAutoTileManager {
    public final AutoAddTracker mAutoTracker;
    public final BroadcastDispatcher mBroadcastDispatcher;
    public final Context mContext;
    public final C20653 mDemoResetStartedReceiver;
    public final Handler mHandler;
    public final QSTileHost mHost;
    public final ManagedProfileController mManagedProfileController;
    public BroadcastReceiver mPreInstallerFinishedReceiver;
    public final C20664 mProfileCallback;
    public final SecQQSTileHost mQQSHost;
    public final ArrayList mRemovedTileListByAppIntent = new ArrayList();
    public final C20642 mTileVisibilityUpdateReceiver;

    /* renamed from: -$$Nest$mupdateRemovedTileListByAppIntent, reason: not valid java name */
    public static void m1618$$Nest$mupdateRemovedTileListByAppIntent(SecAutoTileManager secAutoTileManager, boolean z, String str) {
        secAutoTileManager.getClass();
        StringBuilder sb = new StringBuilder("updateRemovedTileListByAppIntent : isAdded = ");
        sb.append(z);
        sb.append(", spec = ");
        sb.append(str);
        sb.append(", mRemovedTileListByAppIntent = ");
        ArrayList arrayList = secAutoTileManager.mRemovedTileListByAppIntent;
        sb.append(arrayList);
        Log.d("AutoTileManager", sb.toString());
        if (z) {
            if (arrayList.contains(str)) {
                arrayList.remove(str);
            }
        } else {
            if (arrayList.contains(str)) {
                return;
            }
            arrayList.add(str);
        }
    }

    /* JADX WARN: Multi-variable type inference failed */
    /* JADX WARN: Type inference failed for: r0v1, types: [com.android.systemui.qs.SecAutoTileManager$4, java.lang.Object] */
    /* JADX WARN: Type inference failed for: r11v5, types: [android.content.BroadcastReceiver, com.android.systemui.qs.SecAutoTileManager$3] */
    /* JADX WARN: Type inference failed for: r2v1, types: [android.content.BroadcastReceiver, com.android.systemui.qs.SecAutoTileManager$2] */
    public SecAutoTileManager(Context context, AutoAddTracker.Builder builder, QSTileHost qSTileHost, Handler handler, ManagedProfileController managedProfileController, BroadcastDispatcher broadcastDispatcher) {
        boolean contains;
        this.mPreInstallerFinishedReceiver = null;
        this.mTileVisibilityUpdateReceiver = null;
        this.mDemoResetStartedReceiver = null;
        ?? r0 = new ManagedProfileController.Callback() { // from class: com.android.systemui.qs.SecAutoTileManager.4
            @Override // com.android.systemui.statusbar.phone.ManagedProfileController.Callback
            public final void onManagedProfileChanged() {
                boolean contains2;
                String join;
                boolean contains3;
                AutoAddTracker.AutoTile autoTile;
                if (!((ManagedProfileControllerImpl) SecAutoTileManager.this.mManagedProfileController).hasActiveProfile()) {
                    AutoAddTracker autoAddTracker = SecAutoTileManager.this.mAutoTracker;
                    synchronized (autoAddTracker.autoAdded) {
                        contains2 = autoAddTracker.autoAdded.contains("WorkMode");
                    }
                    if (contains2) {
                        SecAutoTileManager.this.mHost.removeTile("WorkMode");
                        AutoAddTracker autoAddTracker2 = SecAutoTileManager.this.mAutoTracker;
                        synchronized (autoAddTracker2.autoAdded) {
                            join = autoAddTracker2.autoAdded.remove("WorkMode") ? TextUtils.join(",", autoAddTracker2.autoAdded) : null;
                        }
                        if (join != null) {
                            autoAddTracker2.saveTiles(join);
                            return;
                        }
                        return;
                    }
                    return;
                }
                AutoAddTracker autoAddTracker3 = SecAutoTileManager.this.mAutoTracker;
                synchronized (autoAddTracker3.autoAdded) {
                    contains3 = autoAddTracker3.autoAdded.contains("WorkMode");
                }
                if (contains3) {
                    return;
                }
                Map map = SecAutoTileManager.this.mAutoTracker.restoredTiles;
                int i = (map == null || (autoTile = (AutoAddTracker.AutoTile) map.get("WorkMode")) == null) ? -1 : autoTile.index;
                QSTileHost qSTileHost2 = SecAutoTileManager.this.mHost;
                qSTileHost2.getClass();
                qSTileHost2.mMainExecutor.execute(new QSTileHost$$ExternalSyntheticLambda4(qSTileHost2, "WorkMode", i, 1));
                AutoAddTracker autoAddTracker4 = SecAutoTileManager.this.mAutoTracker;
                synchronized (autoAddTracker4.autoAdded) {
                    join = autoAddTracker4.autoAdded.add("WorkMode") ? TextUtils.join(",", autoAddTracker4.autoAdded) : null;
                }
                if (join != null) {
                    autoAddTracker4.saveTiles(join);
                }
            }

            @Override // com.android.systemui.statusbar.phone.ManagedProfileController.Callback
            public final void onManagedProfileRemoved() {
            }
        };
        this.mProfileCallback = r0;
        this.mContext = context;
        this.mHost = qSTileHost;
        this.mQQSHost = qSTileHost.mQQSTileHost;
        this.mHandler = handler;
        builder.userId = qSTileHost.mUserContext.getUser().getIdentifier();
        AutoAddTracker autoAddTracker = new AutoAddTracker(builder.secureSettings, builder.broadcastDispatcher, builder.qsHost, builder.dumpManager, builder.handler, builder.executor, builder.userId);
        this.mAutoTracker = autoAddTracker;
        this.mBroadcastDispatcher = broadcastDispatcher;
        this.mPreInstallerFinishedReceiver = new BroadcastReceiver() { // from class: com.android.systemui.qs.SecAutoTileManager.1
            @Override // android.content.BroadcastReceiver
            public final void onReceive(Context context2, Intent intent) {
                String action = intent.getAction();
                Log.d("AutoTileManager", "PreInstallerFinished");
                if ("com.samsung.intent.action.PREINSTALLER_FINISH".equals(action)) {
                    SecAutoTileManager.this.mHost.refreshTileList();
                    SecAutoTileManager.this.mHandler.post(new Runnable() { // from class: com.android.systemui.qs.SecAutoTileManager.1.1
                        @Override // java.lang.Runnable
                        public final void run() {
                            SecAutoTileManager secAutoTileManager = SecAutoTileManager.this;
                            BroadcastReceiver broadcastReceiver = secAutoTileManager.mPreInstallerFinishedReceiver;
                            if (broadcastReceiver != null) {
                                secAutoTileManager.mBroadcastDispatcher.unregisterReceiver(broadcastReceiver);
                                SecAutoTileManager.this.mPreInstallerFinishedReceiver = null;
                                Log.d("AutoTileManager", "unregister PreInstallerFinished");
                            }
                        }
                    });
                }
            }
        };
        broadcastDispatcher.registerReceiver(AppCompatDelegateImpl$$ExternalSyntheticOutline0.m5m("com.samsung.intent.action.PREINSTALLER_FINISH"), this.mPreInstallerFinishedReceiver);
        ?? r2 = new BroadcastReceiver() { // from class: com.android.systemui.qs.SecAutoTileManager.2
            @Override // android.content.BroadcastReceiver
            public final void onReceive(Context context2, Intent intent) {
                int indexOf;
                if ("com.samsung.systemui.qs.action.ACTION_UPDATE_CUSTOMTILE_VISIBILITY".equals(intent.getAction())) {
                    if (((SettingsHelper) Dependency.get(SettingsHelper.class)).isEmergencyMode()) {
                        Log.d("AutoTileManager", "EmergencyMode is enabled : do not change tile list");
                        return;
                    }
                    String stringExtra = intent.getStringExtra("operation");
                    String stringExtra2 = intent.getStringExtra("componentName");
                    String stringExtra3 = intent.getStringExtra("packageName");
                    final String stringExtra4 = intent.getStringExtra("tileName");
                    final int intExtra = intent.getIntExtra("index", -1);
                    StringBuilder m87m = AbstractC0866xb1ce8deb.m87m("TileVisibilityUpdate : [", stringExtra, "] ", stringExtra4, ":");
                    AppOpItem$$ExternalSyntheticOutline0.m97m(m87m, stringExtra3, ", ", stringExtra2, ", ");
                    RecyclerView$$ExternalSyntheticOutline0.m46m(m87m, intExtra, "AutoTileManager");
                    if (stringExtra3 == null || stringExtra2 == null || stringExtra4 == null) {
                        return;
                    }
                    final ComponentName componentName = new ComponentName(stringExtra3, stringExtra2);
                    if (!"add".equals(stringExtra)) {
                        if ("remove".equals(stringExtra)) {
                            SecAutoTileManager.this.mHost.removeTileByUser(componentName);
                            SecAutoTileManager.m1618$$Nest$mupdateRemovedTileListByAppIntent(SecAutoTileManager.this, false, CustomTile.toSpec(componentName));
                            SecQQSTileHost secQQSTileHost = SecAutoTileManager.this.mQQSHost;
                            secQQSTileHost.getClass();
                            String spec = CustomTile.toSpec(componentName);
                            if ("WifiCalling".equals(secQQSTileHost.mQSTileHost.getCustomTileNameFromSpec(spec)) && (indexOf = secQQSTileHost.mTileSpecs.indexOf(spec)) != -1) {
                                Prefs.putInt(secQQSTileHost.mContext, "QQsWifiCallingTileIndex", indexOf);
                            }
                            Log.d("SecQQSTileHost", "removeTileByUser  ".concat(spec));
                            secQQSTileHost.removeTile(spec);
                            return;
                        }
                        return;
                    }
                    final QSTileHost qSTileHost2 = SecAutoTileManager.this.mHost;
                    qSTileHost2.getClass();
                    qSTileHost2.mMainExecutor.execute(new Runnable() { // from class: com.android.systemui.qs.QSTileHost$$ExternalSyntheticLambda13
                        @Override // java.lang.Runnable
                        public final void run() {
                            int i;
                            QSTileHost qSTileHost3 = QSTileHost.this;
                            ComponentName componentName2 = componentName;
                            String str = stringExtra4;
                            int i2 = intExtra;
                            String defaultTileList = qSTileHost3.getDefaultTileList();
                            Context context3 = qSTileHost3.mContext;
                            String stringForUser = Settings.Secure.getStringForUser(context3.getContentResolver(), "sysui_removed_qs_tiles", ActivityManager.getCurrentUser());
                            if ((stringForUser == null || !stringForUser.contains(CustomTile.toSpec(componentName2))) && defaultTileList.contains(str)) {
                                if (Settings.Secure.getStringForUser(context3.getContentResolver(), "sysui_qs_tiles", ActivityManager.getCurrentUser()).contains(CustomTile.toSpec(componentName2))) {
                                    qSTileHost3.refreshTileList();
                                    return;
                                }
                                ArrayList arrayList = new ArrayList();
                                if (!"WifiCalling".equals(str) || (i = Prefs.getInt(context3, "QsWifiCallingTileIndex", -1)) == -1) {
                                    for (String str2 : defaultTileList.split(",")) {
                                        String trim = str2.trim();
                                        if (!trim.isEmpty()) {
                                            arrayList.add(trim);
                                        }
                                    }
                                    int indexOf2 = arrayList.indexOf(str);
                                    if (indexOf2 != -1) {
                                        i2 = indexOf2;
                                    }
                                } else {
                                    i2 = i;
                                }
                                String stringForUser2 = Settings.Secure.getStringForUser(context3.getContentResolver(), "sysui_qs_tiles", ((UserTrackerImpl) qSTileHost3.mUserTracker).getUserId());
                                List loadTileSpecs = qSTileHost3.loadTileSpecs(context3, stringForUser2);
                                List loadTileSpecs2 = qSTileHost3.loadTileSpecs(context3, stringForUser2);
                                if (i2 < 0 || i2 > loadTileSpecs2.size()) {
                                    loadTileSpecs2.add(CustomTile.toSpec(componentName2));
                                } else {
                                    loadTileSpecs2.add(i2, CustomTile.toSpec(componentName2));
                                }
                                qSTileHost3.changeTilesByUser(loadTileSpecs, loadTileSpecs2);
                            }
                        }
                    });
                    SecAutoTileManager.m1618$$Nest$mupdateRemovedTileListByAppIntent(SecAutoTileManager.this, true, CustomTile.toSpec(componentName));
                    SecQQSTileHost secQQSTileHost2 = SecAutoTileManager.this.mQQSHost;
                    secQQSTileHost2.getClass();
                    String spec2 = CustomTile.toSpec(componentName);
                    if ("WifiCalling".equals(secQQSTileHost2.mQSTileHost.getCustomTileNameFromSpec(spec2))) {
                        Context context3 = secQQSTileHost2.mContext;
                        boolean z = Prefs.getBoolean(context3, "QQsHasEditedQuickTileList", false);
                        int i = Prefs.getInt(context3, "QQsWifiCallingTileIndex", -1);
                        ((SecQSPanelResourcePicker) Dependency.get(SecQSPanelResourcePicker.class)).getClass();
                        int qsTileMinNum = SecQSPanelResourcePicker.getQsTileMinNum(context3);
                        StringBuilder m66m = KeyguardFMMViewController$$ExternalSyntheticOutline0.m66m("addTileByIntent hasEdited=", z, "  index=", i, "  tiles=");
                        ArrayList arrayList = secQQSTileHost2.mTileSpecs;
                        m66m.append(arrayList.size());
                        Log.d("SecQQSTileHost", m66m.toString());
                        UserTracker userTracker = secQQSTileHost2.mUserTracker;
                        if (z || i != -1) {
                            if (i == -1 || arrayList.size() >= qsTileMinNum) {
                                return;
                            }
                            List loadTileSpecs = secQQSTileHost2.loadTileSpecs(context3, Settings.Secure.getStringForUser(context3.getContentResolver(), "sysui_quick_qs_tiles", ((UserTrackerImpl) userTracker).getUserId()));
                            if (loadTileSpecs.contains(spec2)) {
                                secQQSTileHost2.refreshTileList();
                                return;
                            } else {
                                loadTileSpecs.add(i, spec2);
                                secQQSTileHost2.changeTiles(loadTileSpecs);
                                return;
                            }
                        }
                        String qQSDefaultTileList = secQQSTileHost2.getQQSDefaultTileList();
                        ArrayList arrayList2 = new ArrayList();
                        for (String str : qQSDefaultTileList.split(",")) {
                            String trim = str.trim();
                            if (!trim.isEmpty()) {
                                arrayList2.add(trim);
                            }
                        }
                        int indexOf2 = arrayList2.indexOf(spec2);
                        if (indexOf2 == -1 || indexOf2 >= qsTileMinNum) {
                            return;
                        }
                        List loadTileSpecs2 = secQQSTileHost2.loadTileSpecs(context3, Settings.Secure.getStringForUser(context3.getContentResolver(), "sysui_quick_qs_tiles", ((UserTrackerImpl) userTracker).getUserId()));
                        if (loadTileSpecs2.contains(spec2)) {
                            secQQSTileHost2.refreshTileList();
                            return;
                        }
                        loadTileSpecs2.add(indexOf2, spec2);
                        if (loadTileSpecs2.size() > qsTileMinNum) {
                            loadTileSpecs2.remove(loadTileSpecs2.size() - 1);
                        }
                        secQQSTileHost2.changeTiles(loadTileSpecs2);
                    }
                }
            }
        };
        this.mTileVisibilityUpdateReceiver = r2;
        broadcastDispatcher.registerReceiver(r2, AppCompatDelegateImpl$$ExternalSyntheticOutline0.m5m("com.samsung.systemui.qs.action.ACTION_UPDATE_CUSTOMTILE_VISIBILITY"), null, null, 2, "com.samsung.systemui.permission.SEC_UPDATE_CUSTOMTILE_VISIBILITY");
        ?? r11 = new BroadcastReceiver() { // from class: com.android.systemui.qs.SecAutoTileManager.3
            @Override // android.content.BroadcastReceiver
            public final void onReceive(Context context2, Intent intent) {
                String action = intent.getAction();
                Log.d("AutoTileManager", "DemoResetStarted");
                if ("com.samsung.sea.rm.DEMO_RESET_STARTED".equals(action) && DeviceState.isShopDemo(SecAutoTileManager.this.mContext)) {
                    SecAutoTileManager.this.mHost.resetTileList();
                }
            }
        };
        this.mDemoResetStartedReceiver = r11;
        IntentFilter intentFilter = new IntentFilter();
        intentFilter.addAction("com.samsung.sea.rm.DEMO_RESET_STARTED");
        broadcastDispatcher.registerReceiver(intentFilter, r11);
        this.mManagedProfileController = managedProfileController;
        synchronized (autoAddTracker.autoAdded) {
            contains = autoAddTracker.autoAdded.contains("WorkMode");
        }
        if (contains) {
            return;
        }
        ((ManagedProfileControllerImpl) managedProfileController).addCallback(r0);
    }

    public final boolean isRemovedTileByAppIntent(String str) {
        StringBuilder m4m = ActivityResultRegistry$$ExternalSyntheticOutline0.m4m("isRemovedTileByAppIntent : spec = ", str, ", mRemovedTileListByAppIntent = ");
        ArrayList arrayList = this.mRemovedTileListByAppIntent;
        m4m.append(arrayList);
        Log.d("AutoTileManager", m4m.toString());
        return arrayList.contains(str);
    }
}
