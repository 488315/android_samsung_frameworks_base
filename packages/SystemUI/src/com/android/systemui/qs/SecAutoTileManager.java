package com.android.systemui.qs;

import android.accessibilityservice.AccessibilityServiceInfo;
import android.accessibilityservice.AccessibilityShortcutInfo;
import android.app.ActivityManager;
import android.content.BroadcastReceiver;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.database.ContentObserver;
import android.os.Handler;
import android.os.UserHandle;
import android.provider.Settings;
import android.text.TextUtils;
import android.util.Log;
import android.view.accessibility.AccessibilityManager;
import androidx.activity.result.ActivityResultRegistry$$ExternalSyntheticOutline0;
import androidx.appcompat.app.AppCompatDelegateImpl$$ExternalSyntheticOutline0;
import androidx.appcompat.util.SeslRoundedCorner$SeslRoundedChunkingDrawable$$ExternalSyntheticOutline0;
import androidx.constraintlayout.core.widgets.ConstraintWidget$$ExternalSyntheticOutline0;
import androidx.recyclerview.widget.RecyclerView$$ExternalSyntheticOutline0;
import com.android.internal.accessibility.AccessibilityShortcutController;
import com.android.systemui.Dependency;
import com.android.systemui.Prefs;
import com.android.systemui.broadcast.BroadcastDispatcher;
import com.android.systemui.qs.AutoAddTracker;
import com.android.systemui.qs.QSHost;
import com.android.systemui.qs.external.CustomTile;
import com.android.systemui.settings.UserTrackerImpl;
import com.android.systemui.statusbar.phone.ManagedProfileController;
import com.android.systemui.statusbar.phone.ManagedProfileControllerImpl;
import com.android.systemui.util.DeviceState;
import com.android.systemui.util.SettingsHelper;
import com.android.systemui.util.settings.SecureSettings;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

/* compiled from: qb/89523975 427a50d40ec74a85ca352b86f77450b1c52ece5389e11158752b0d641a3a5098 */
public final class SecAutoTileManager {
    public final Map COMPONENT_TO_TILE_SPEC_MAPPING;
    public final Map TILE_SPEC_TO_COMPONENT_MAPPING;
    public final Map TILE_SPEC_TO_TILE_COMPONENT_MAPPING;
    public final AnonymousClass4 mA11yQsObserver;
    public final AccessibilityManager mAccessibilityManager;
    public final AutoAddTracker mAutoTracker;
    public final BroadcastDispatcher mBroadcastDispatcher;
    public final Context mContext;
    public final UserHandle mCurrentUser;
    public final AnonymousClass3 mDemoResetStartedReceiver;
    public final Handler mHandler;
    public final QSTileHost mHost;
    public final ManagedProfileController mManagedProfileController;
    public BroadcastReceiver mPreInstallerFinishedReceiver;
    public final AnonymousClass5 mProfileCallback;
    public final SecureSettings mSecureSettings;
    public final SecAutoTileManager$$ExternalSyntheticLambda0 mTileChangedCallback;
    public final AnonymousClass2 mTileVisibilityUpdateReceiver;
    public final ArrayList mRemovedTileListByAppIntent = new ArrayList();
    public final ArrayList previousAddedQs = new ArrayList();

    /* renamed from: -$$Nest$mupdateRemovedTileListByAppIntent, reason: not valid java name */
    public static void m2062$$Nest$mupdateRemovedTileListByAppIntent(SecAutoTileManager secAutoTileManager, boolean z, String str) {
        secAutoTileManager.getClass();
        Log.d("AutoTileManager", "updateRemovedTileListByAppIntent : isAdded = " + z + ", spec = " + str + ", mRemovedTileListByAppIntent = " + secAutoTileManager.mRemovedTileListByAppIntent);
        if (z) {
            if (secAutoTileManager.mRemovedTileListByAppIntent.contains(str)) {
                secAutoTileManager.mRemovedTileListByAppIntent.remove(str);
            }
        } else {
            if (secAutoTileManager.mRemovedTileListByAppIntent.contains(str)) {
                return;
            }
            secAutoTileManager.mRemovedTileListByAppIntent.add(str);
        }
    }

    public SecAutoTileManager(Context context, AutoAddTracker.Builder builder, QSTileHost qSTileHost, Handler handler, ManagedProfileController managedProfileController, BroadcastDispatcher broadcastDispatcher, SecureSettings secureSettings, AccessibilityManager accessibilityManager) {
        boolean contains;
        this.mPreInstallerFinishedReceiver = null;
        this.mSecureSettings = null;
        ComponentName componentName = AccessibilityShortcutController.DALTONIZER_COMPONENT_NAME;
        ComponentName componentName2 = AccessibilityShortcutController.COLOR_INVERSION_COMPONENT_NAME;
        ComponentName componentName3 = AccessibilityShortcutController.ONE_HANDED_COMPONENT_NAME;
        ComponentName componentName4 = AccessibilityShortcutController.REDUCE_BRIGHT_COLORS_COMPONENT_NAME;
        ComponentName componentName5 = AccessibilityShortcutController.FONT_SIZE_COMPONENT_NAME;
        ComponentName componentName6 = AccessibilityShortcutController.HIGH_CONTRAST_FONTS_COMPONENT_NAME;
        ComponentName componentName7 = AccessibilityShortcutController.COLOR_LENS_COMPONENT_NAME;
        this.TILE_SPEC_TO_COMPONENT_MAPPING = Map.ofEntries(Map.entry("ColorCorrection", componentName), Map.entry("ColorInversion", componentName2), Map.entry("onehanded", componentName3), Map.entry("ReduceBrightColors", componentName4), Map.entry("font_scaling", componentName5), Map.entry("HighContrastFont", componentName6), Map.entry("ColorLens", componentName7));
        this.TILE_SPEC_TO_TILE_COMPONENT_MAPPING = Map.ofEntries(Map.entry("ColorCorrection", AccessibilityShortcutController.DALTONIZER_TILE_COMPONENT_NAME), Map.entry("ColorInversion", AccessibilityShortcutController.COLOR_INVERSION_TILE_COMPONENT_NAME), Map.entry("onehanded", AccessibilityShortcutController.ONE_HANDED_TILE_COMPONENT_NAME), Map.entry("ReduceBrightColors", AccessibilityShortcutController.REDUCE_BRIGHT_COLORS_TILE_SERVICE_COMPONENT_NAME), Map.entry("font_scaling", AccessibilityShortcutController.FONT_SIZE_TILE_COMPONENT_NAME), Map.entry("HighContrastFont", AccessibilityShortcutController.HIGH_CONTRAST_FONTS_TILE_COMPONENT_NAME), Map.entry("ColorLens", AccessibilityShortcutController.COLOR_LENS_TILE_COMPONENT_NAME));
        this.COMPONENT_TO_TILE_SPEC_MAPPING = Map.ofEntries(Map.entry(componentName, "ColorCorrection"), Map.entry(componentName2, "ColorInversion"), Map.entry(componentName3, "onehanded"), Map.entry(componentName4, "ReduceBrightColors"), Map.entry(componentName5, "font_scaling"), Map.entry(componentName6, "HighContrastFont"), Map.entry(componentName7, "ColorLens"));
        ManagedProfileController.Callback callback = new ManagedProfileController.Callback() { // from class: com.android.systemui.qs.SecAutoTileManager.5
            @Override // com.android.systemui.statusbar.phone.ManagedProfileController.Callback
            public final void onManagedProfileChanged() {
                boolean contains2;
                String join;
                boolean contains3;
                String join2;
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
                            autoAddTracker2.secureSettings.putStringForUser("qs_auto_tiles", join, null, false, autoAddTracker2.userId, true);
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
                SecAutoTileManager.this.mHost.addTile((map == null || (autoTile = (AutoAddTracker.AutoTile) map.get("WorkMode")) == null) ? -1 : autoTile.index, "WorkMode");
                AutoAddTracker autoAddTracker4 = SecAutoTileManager.this.mAutoTracker;
                synchronized (autoAddTracker4.autoAdded) {
                    join2 = autoAddTracker4.autoAdded.add("WorkMode") ? TextUtils.join(",", autoAddTracker4.autoAdded) : null;
                }
                if (join2 != null) {
                    autoAddTracker4.secureSettings.putStringForUser("qs_auto_tiles", join2, null, false, autoAddTracker4.userId, true);
                }
            }

            @Override // com.android.systemui.statusbar.phone.ManagedProfileController.Callback
            public final void onManagedProfileRemoved() {
            }
        };
        this.mContext = context;
        this.mHost = qSTileHost;
        qSTileHost.getClass();
        this.mHandler = handler;
        UserHandle user = qSTileHost.mUserContext.getUser();
        this.mCurrentUser = user;
        builder.userId = user.getIdentifier();
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
        broadcastDispatcher.registerReceiver(AppCompatDelegateImpl$$ExternalSyntheticOutline0.m("com.samsung.intent.action.PREINSTALLER_FINISH"), this.mPreInstallerFinishedReceiver);
        broadcastDispatcher.registerReceiver(new BroadcastReceiver() { // from class: com.android.systemui.qs.SecAutoTileManager.2
            @Override // android.content.BroadcastReceiver
            public final void onReceive(Context context2, Intent intent) {
                if ("com.samsung.systemui.qs.action.ACTION_UPDATE_CUSTOMTILE_VISIBILITY".equals(intent.getAction())) {
                    if (((SettingsHelper) Dependency.sDependency.getDependencyInner(SettingsHelper.class)).isEmergencyMode()) {
                        Log.d("AutoTileManager", "EmergencyMode is enabled : do not change tile list");
                        return;
                    }
                    String stringExtra = intent.getStringExtra("operation");
                    String stringExtra2 = intent.getStringExtra("componentName");
                    String stringExtra3 = intent.getStringExtra("packageName");
                    final String stringExtra4 = intent.getStringExtra("tileName");
                    final int intExtra = intent.getIntExtra("index", -1);
                    StringBuilder m = SeslRoundedCorner$SeslRoundedChunkingDrawable$$ExternalSyntheticOutline0.m("TileVisibilityUpdate : [", stringExtra, "] ", stringExtra4, ":");
                    ConstraintWidget$$ExternalSyntheticOutline0.m(m, stringExtra3, ", ", stringExtra2, ", ");
                    RecyclerView$$ExternalSyntheticOutline0.m(intExtra, "AutoTileManager", m);
                    if (stringExtra3 == null || stringExtra2 == null || stringExtra4 == null) {
                        return;
                    }
                    final ComponentName componentName8 = new ComponentName(stringExtra3, stringExtra2);
                    if ("add".equals(stringExtra)) {
                        final QSTileHost qSTileHost2 = SecAutoTileManager.this.mHost;
                        qSTileHost2.mMainExecutor.execute(new Runnable() { // from class: com.android.systemui.qs.QSTileHost$$ExternalSyntheticLambda22
                            @Override // java.lang.Runnable
                            public final void run() {
                                int i;
                                QSTileHost qSTileHost3 = QSTileHost.this;
                                ComponentName componentName9 = componentName8;
                                String str = stringExtra4;
                                int i2 = intExtra;
                                String defaultTileList = qSTileHost3.getDefaultTileList();
                                String stringForUser = Settings.Secure.getStringForUser(qSTileHost3.mContext.getContentResolver(), "sysui_removed_qs_tiles", ActivityManager.getCurrentUser());
                                if ((stringForUser == null || !stringForUser.contains(CustomTile.toSpec(componentName9))) && defaultTileList.contains(str)) {
                                    if (Settings.Secure.getStringForUser(qSTileHost3.mContext.getContentResolver(), "sysui_qs_tiles", ActivityManager.getCurrentUser()).contains(CustomTile.toSpec(componentName9))) {
                                        qSTileHost3.refreshTileList();
                                        return;
                                    }
                                    ArrayList arrayList = new ArrayList();
                                    if (!"WifiCalling".equals(str) || (i = Prefs.get(qSTileHost3.mContext).getInt("QsWifiCallingTileIndex", -1)) == -1) {
                                        for (String str2 : defaultTileList.split(",")) {
                                            String trim = str2.trim();
                                            if (!trim.isEmpty()) {
                                                arrayList.add(trim);
                                            }
                                        }
                                        int indexOf = arrayList.indexOf(str);
                                        if (indexOf != -1) {
                                            i2 = indexOf;
                                        }
                                    } else {
                                        i2 = i;
                                    }
                                    String stringForUser2 = Settings.Secure.getStringForUser(qSTileHost3.mContext.getContentResolver(), "sysui_qs_tiles", ((UserTrackerImpl) qSTileHost3.mUserTracker).getUserId());
                                    List loadTileSpecs = qSTileHost3.loadTileSpecs(qSTileHost3.mContext, stringForUser2);
                                    List loadTileSpecs2 = qSTileHost3.loadTileSpecs(qSTileHost3.mContext, stringForUser2);
                                    if (i2 < 0 || i2 > loadTileSpecs2.size()) {
                                        loadTileSpecs2.add(CustomTile.toSpec(componentName9));
                                    } else {
                                        loadTileSpecs2.add(i2, CustomTile.toSpec(componentName9));
                                    }
                                    qSTileHost3.changeTilesByUser(loadTileSpecs, loadTileSpecs2);
                                }
                            }
                        });
                        SecAutoTileManager.m2062$$Nest$mupdateRemovedTileListByAppIntent(SecAutoTileManager.this, true, CustomTile.toSpec(componentName8));
                    } else if ("remove".equals(stringExtra)) {
                        SecAutoTileManager.this.mHost.removeTileByUser(componentName8);
                        SecAutoTileManager.m2062$$Nest$mupdateRemovedTileListByAppIntent(SecAutoTileManager.this, false, CustomTile.toSpec(componentName8));
                    }
                }
            }
        }, AppCompatDelegateImpl$$ExternalSyntheticOutline0.m("com.samsung.systemui.qs.action.ACTION_UPDATE_CUSTOMTILE_VISIBILITY"), null, null, 2, "com.samsung.systemui.permission.SEC_UPDATE_CUSTOMTILE_VISIBILITY");
        BroadcastReceiver broadcastReceiver = new BroadcastReceiver() { // from class: com.android.systemui.qs.SecAutoTileManager.3
            @Override // android.content.BroadcastReceiver
            public final void onReceive(Context context2, Intent intent) {
                String action = intent.getAction();
                Log.d("AutoTileManager", "DemoResetStarted");
                if ("com.samsung.sea.rm.DEMO_RESET_STARTED".equals(action) && DeviceState.isShopDemo(SecAutoTileManager.this.mContext)) {
                    SecAutoTileManager.this.mHost.resetTileList();
                }
            }
        };
        IntentFilter intentFilter = new IntentFilter();
        intentFilter.addAction("com.samsung.sea.rm.DEMO_RESET_STARTED");
        broadcastDispatcher.registerReceiver(intentFilter, broadcastReceiver);
        this.mManagedProfileController = managedProfileController;
        synchronized (autoAddTracker.autoAdded) {
            contains = autoAddTracker.autoAdded.contains("WorkMode");
        }
        if (!contains) {
            ((ManagedProfileControllerImpl) managedProfileController).addCallback(callback);
        }
        this.mSecureSettings = secureSettings;
        this.mAccessibilityManager = accessibilityManager;
        qSTileHost.addCallback(new QSHost.Callback() { // from class: com.android.systemui.qs.SecAutoTileManager$$ExternalSyntheticLambda0
            @Override // com.android.systemui.qs.QSHost.Callback
            public final void onTilesChanged() {
                final SecAutoTileManager secAutoTileManager = SecAutoTileManager.this;
                secAutoTileManager.getClass();
                secAutoTileManager.mHandler.post(new Runnable() { // from class: com.android.systemui.qs.SecAutoTileManager$$ExternalSyntheticLambda1
                    @Override // java.lang.Runnable
                    public final void run() {
                        SecAutoTileManager secAutoTileManager2 = SecAutoTileManager.this;
                        Context context2 = secAutoTileManager2.mContext;
                        HashSet hashSet = new HashSet();
                        for (AccessibilityServiceInfo accessibilityServiceInfo : secAutoTileManager2.mAccessibilityManager.getInstalledAccessibilityServiceList()) {
                            String str = accessibilityServiceInfo.getResolveInfo().serviceInfo.packageName;
                            String tileServiceName = accessibilityServiceInfo.getTileServiceName();
                            if (!TextUtils.isEmpty(tileServiceName)) {
                                hashSet.add(new ComponentName(str, tileServiceName));
                            }
                        }
                        for (AccessibilityShortcutInfo accessibilityShortcutInfo : secAutoTileManager2.mAccessibilityManager.getInstalledAccessibilityShortcutListAsUser(context2, secAutoTileManager2.mCurrentUser.getIdentifier())) {
                            String packageName = accessibilityShortcutInfo.getComponentName().getPackageName();
                            String tileServiceName2 = accessibilityShortcutInfo.getTileServiceName();
                            if (!TextUtils.isEmpty(tileServiceName2)) {
                                hashSet.add(new ComponentName(packageName, tileServiceName2));
                            }
                        }
                        Log.d("AutoTileManager", "getAccessibilityTileServices  " + hashSet.toString());
                        String stringForUser = secAutoTileManager2.mSecureSettings.getStringForUser("sysui_qs_tiles", secAutoTileManager2.mCurrentUser.getIdentifier());
                        ArrayList arrayList = new ArrayList();
                        for (String str2 : stringForUser.split(",")) {
                            if (str2.startsWith("custom(")) {
                                ComponentName componentFromSpec = CustomTile.getComponentFromSpec(str2);
                                if (hashSet.contains(componentFromSpec)) {
                                    arrayList.add(componentFromSpec);
                                }
                            } else {
                                ComponentName componentName8 = (ComponentName) secAutoTileManager2.TILE_SPEC_TO_TILE_COMPONENT_MAPPING.get(str2);
                                if (componentName8 != null) {
                                    arrayList.add(componentName8);
                                }
                            }
                        }
                        Log.d("AutoTileManager", "notifyQuickSettingsTilesChanged  " + arrayList.toString());
                        secAutoTileManager2.mAccessibilityManager.notifyQuickSettingsTilesChanged(secAutoTileManager2.mCurrentUser.getIdentifier(), arrayList);
                    }
                });
            }
        });
        updateA11yQsTiles();
        secureSettings.registerContentObserverForUserSync("accessibility_qs_targets", new ContentObserver(handler) { // from class: com.android.systemui.qs.SecAutoTileManager.4
            @Override // android.database.ContentObserver
            public final void onChange(boolean z) {
                SecAutoTileManager.this.updateA11yQsTiles();
            }
        }, -1);
    }

    public final boolean isRemovedTileByAppIntent(String str) {
        StringBuilder m = ActivityResultRegistry$$ExternalSyntheticOutline0.m("isRemovedTileByAppIntent : spec = ", str, ", mRemovedTileListByAppIntent = ");
        m.append(this.mRemovedTileListByAppIntent);
        Log.d("AutoTileManager", m.toString());
        return this.mRemovedTileListByAppIntent.contains(str);
    }

    public final void updateA11yQsTiles() {
        QSTileHost qSTileHost;
        ArrayList arrayList = new ArrayList();
        ArrayList arrayList2 = new ArrayList();
        String stringForUser = this.mSecureSettings.getStringForUser("accessibility_qs_targets", this.mCurrentUser.getIdentifier());
        if (stringForUser != null) {
            List list = Arrays.stream(stringForUser.split(":")).toList();
            Iterator it = list.iterator();
            while (true) {
                boolean hasNext = it.hasNext();
                qSTileHost = this.mHost;
                if (!hasNext) {
                    break;
                }
                String str = (String) it.next();
                if (!str.isEmpty()) {
                    String[] split = str.split("/");
                    String str2 = (String) this.COMPONENT_TO_TILE_SPEC_MAPPING.get(new ComponentName(split[0], split[1]));
                    if (str2 != null) {
                        if (!this.previousAddedQs.contains(str2)) {
                            qSTileHost.addTile(-1, str2);
                        }
                        arrayList.add(str2);
                    }
                }
            }
            Iterator it2 = this.previousAddedQs.iterator();
            while (it2.hasNext()) {
                String str3 = (String) it2.next();
                if (!str3.startsWith("custom(") && !list.contains(((ComponentName) this.TILE_SPEC_TO_COMPONENT_MAPPING.get(str3)).flattenToString())) {
                    arrayList2.add(str3);
                }
            }
            qSTileHost.removeTiles(arrayList2);
        }
        Log.d("AutoTileManager", "updateA11yQsTiles  " + this.previousAddedQs.toString() + "  to  " + arrayList.toString());
        this.previousAddedQs.clear();
        this.previousAddedQs.addAll(arrayList);
    }
}
