package com.android.systemui.statusbar;

import android.app.ActivityManager;
import android.app.admin.DevicePolicyManager;
import android.content.BroadcastReceiver;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.SharedPreferences;
import android.content.pm.ActivityInfo;
import android.content.pm.PackageManager;
import android.content.pm.ResolveInfo;
import android.content.pm.Signature;
import android.content.pm.SigningInfo;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.BlendMode;
import android.graphics.BlendModeColorFilter;
import android.graphics.BlurMaskFilter;
import android.graphics.Canvas;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffXfermode;
import android.graphics.RectF;
import android.graphics.drawable.AdaptiveIconDrawable;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.DrawableWrapper;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.os.Process;
import android.os.UserHandle;
import android.provider.Settings;
import android.support.v4.media.MediaBrowserCompat$MediaBrowserImplBase$$ExternalSyntheticOutline0;
import android.text.TextUtils;
import android.util.Log;
import android.util.Slog;
import androidx.concurrent.futures.AbstractResolvableFuture$$ExternalSyntheticOutline0;
import androidx.constraintlayout.widget.ConstraintSet$WriteJsonEngine$$ExternalSyntheticOutline0;
import androidx.core.app.NotificationManagerCompat$SideChannelManager$$ExternalSyntheticOutline0;
import androidx.core.graphics.drawable.DrawableKt;
import androidx.exifinterface.media.ExifInterface$$ExternalSyntheticOutline0;
import com.android.keyguard.ActiveUnlockConfig$$ExternalSyntheticOutline0;
import com.android.keyguard.ClockEventController$$ExternalSyntheticOutline0;
import com.android.keyguard.KeyguardBiometricLockoutLogger$mKeyguardUpdateMonitorCallback$1$$ExternalSyntheticOutline0;
import com.android.keyguard.KeyguardCarrierViewController$2$$ExternalSyntheticOutline0;
import com.android.keyguard.KeyguardDeskTopStateMonitor;
import com.android.keyguard.KeyguardDisplayManager;
import com.android.keyguard.KeyguardKnoxGuardViewController$$ExternalSyntheticOutline0;
import com.android.keyguard.KeyguardSecSimPinViewController$$ExternalSyntheticOutline0;
import com.android.keyguard.KeyguardSecUpdateMonitorImpl$$ExternalSyntheticOutline0;
import com.android.keyguard.KeyguardUpdateMonitor;
import com.android.keyguard.KeyguardUpdateMonitorCallback;
import com.android.settingslib.volume.MediaSessions$H$$ExternalSyntheticOutline0;
import com.android.systemui.CscRune;
import com.android.systemui.Dependency;
import com.android.systemui.Dumpable;
import com.android.systemui.LsRune;
import com.android.systemui.R;
import com.android.systemui.animation.Expandable;
import com.android.systemui.broadcast.ActionReceiver$$ExternalSyntheticOutline0;
import com.android.systemui.broadcast.BroadcastDispatcher;
import com.android.systemui.common.shared.model.ContentDescription;
import com.android.systemui.common.shared.model.Icon;
import com.android.systemui.deviceentry.data.repository.DeviceEntryFaceAuthRepositoryImpl$$ExternalSyntheticOutline0;
import com.android.systemui.dump.DumpManager;
import com.android.systemui.keyguard.DisplayLifecycle;
import com.android.systemui.keyguard.data.quickaffordance.KeyguardQuickAffordanceConfig;
import com.android.systemui.keyguard.data.quickaffordance.KeyguardSecTilesQuickAffordanceConfig;
import com.android.systemui.keyguard.shared.quickaffordance.KeyguardQuickAffordancePosition;
import com.android.systemui.qs.SecLockscreenTileHost;
import com.android.systemui.settings.UserTracker;
import com.android.systemui.settings.UserTrackerImpl;
import com.android.systemui.statusbar.KeyguardShortcutManager;
import com.android.systemui.statusbar.phone.KeyguardSecBottomAreaView;
import com.android.systemui.statusbar.phone.KeyguardSecBottomAreaViewController;
import com.android.systemui.statusbar.phone.KeyguardSecBottomAreaViewController$shortcutManagerCallback$1;
import com.android.systemui.statusbar.policy.KeyguardStateController;
import com.android.systemui.statusbar.policy.KeyguardStateControllerImpl;
import com.android.systemui.statusbar.policy.UserSwitcherController;
import com.android.systemui.user.domain.interactor.SelectedUserInteractor;
import com.android.systemui.util.DeviceState;
import com.android.systemui.util.SettingsHelper;
import com.android.systemui.util.SystemUIAnalytics;
import com.android.systemui.util.ViewController;
import com.android.systemui.wallpaper.WallpaperUtils;
import dagger.Lazy;
import java.io.PrintWriter;
import java.lang.ref.WeakReference;
import java.nio.charset.Charset;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Set;
import java.util.concurrent.Executor;
import java.util.function.IntPredicate;
import java.util.function.Predicate;
import kotlin.Unit;
import kotlin.collections.CollectionsKt__CollectionsKt;
import kotlin.collections.CollectionsKt__IterablesKt;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.Intrinsics;
import kotlin.text.CharsKt__CharJVMKt;
import kotlin.text.Regex;
import kotlin.text.StringsKt__StringsKt;
import kotlinx.coroutines.flow.Flow;
import kotlinx.coroutines.flow.FlowKt__BuildersKt$flowOf$$inlined$unsafeFlow$2;

/* loaded from: classes3.dex */
public final class KeyguardShortcutManager extends KeyguardUpdateMonitorCallback implements SettingsHelper.OnChangedCallback, Dumpable {
    public final Context context;
    public final DumpManager dumpManager;
    public final Executor executor;
    public final Handler handler;
    public final KeyguardShortcutManager$intentReceiver$1 intentReceiver;
    public boolean isLockTaskMode;
    public boolean isNowBarVisible;
    public boolean isPermDisabled;
    public boolean isReduceTransparencyEnabled;
    public boolean isShortcutVisibleForMDM;
    public final KeyguardQuickAffordanceConfig[] keyguardBottomAreaShortcutTask;
    public final KeyguardDisplayManager keyguardDisplayManager;
    public final KeyguardShortcutDialerUpdateManager keyguardShortcutDialerUpdateManager;
    public final KeyguardStateController keyguardStateController;
    public final KeyguardUpdateMonitor keyguardUpdateMonitor;
    public final PackageManager packageManager;
    public final SelectedUserInteractor selectedUserInteractor;
    private final SettingsHelper settingsHelper;
    public final ArrayList shortcutCallbacks;
    public int shortcutIconSize;
    public final ShortcutData[] shortcutsData;
    public final StringBuilder stringBuilder;
    public final Set taskConfigs;
    public final HashMap themeShortcutHashMap;
    public final KeyguardShortcutManager$updateShortcutsRunnable$1 updateShortcutsRunnable;
    public final UserSwitcherController userSwitcherController;
    public final UserTracker userTracker;
    public int wallpaperBrightness;
    public static final Companion Companion = new Companion(null);
    public static final String[] SAMSUNG_LIVE_ICON_PACKAGES = {"com.samsung.android.calendar", "com.android.calendar", "com.sec.android.app.clockpackage"};
    public static final Intent SECURE_CAMERA_INTENT = new Intent("android.intent.action.MAIN").addCategory("android.intent.category.LAUNCHER").setClassName("com.sec.android.app.camera", "com.sec.android.app.camera.Camera");
    public static final Intent INSECURE_CAMERA_INTENT = new Intent("android.intent.action.MAIN").addCategory("android.intent.category.LAUNCHER").setClassName("com.sec.android.app.camera", "com.sec.android.app.camera.Camera");
    public static final Intent SAMSUNG_EXPERT_RAW_CAMERA_INTENT = new Intent("android.intent.action.MAIN").addCategory("android.intent.category.LAUNCHER").setClassName("com.samsung.android.app.galaxyraw", "com.samsung.android.app.galaxyraw.GalaxyRaw");
    public static final Intent PHONE_INTENT = new Intent("android.intent.action.DIAL").setClassName("com.samsung.android.dialer", "com.samsung.android.dialer.DialtactsActivity");
    public static final KeyguardShortcutManager$Companion$EMPTY_CONFIG$1 EMPTY_CONFIG = new KeyguardQuickAffordanceConfig() { // from class: com.android.systemui.statusbar.KeyguardShortcutManager$Companion$EMPTY_CONFIG$1
        public final FlowKt__BuildersKt$flowOf$$inlined$unsafeFlow$2 lockScreenState = new FlowKt__BuildersKt$flowOf$$inlined$unsafeFlow$2(KeyguardQuickAffordanceConfig.LockScreenState.Hidden.INSTANCE);

        @Override // com.android.systemui.keyguard.data.quickaffordance.KeyguardQuickAffordanceConfig
        public final String getKey() {
            return "";
        }

        @Override // com.android.systemui.keyguard.data.quickaffordance.KeyguardQuickAffordanceConfig
        public final Flow getLockScreenState() {
            return this.lockScreenState;
        }

        @Override // com.android.systemui.keyguard.data.quickaffordance.KeyguardQuickAffordanceConfig
        public final int getPickerIconResourceId() {
            return R.drawable.bg_bk;
        }

        @Override // com.android.systemui.keyguard.data.quickaffordance.KeyguardQuickAffordanceConfig
        public final KeyguardQuickAffordanceConfig.OnTriggeredResult onTriggered(Expandable expandable) {
            return new KeyguardQuickAffordanceConfig.OnTriggeredResult.Handled(false);
        }

        @Override // com.android.systemui.keyguard.data.quickaffordance.KeyguardQuickAffordanceConfig
        public final String pickerName() {
            return "";
        }
    };

    public final class Companion {
        public /* synthetic */ Companion(DefaultConstructorMarker defaultConstructorMarker) {
            this();
        }

        private Companion() {
        }
    }

    public final class ShortcutData {
        public String appLabel;
        public ComponentName componentName;
        public Drawable drawable;
        public boolean enabled;
        public boolean isIconPaddingNeeded;
        public boolean isMonotoneIcon;
        public boolean isUnlockWaitNeeded;
        public boolean launchInsecureMain;
        public boolean noUnlockNeeded;
        public Drawable panelDrawable;
        public Drawable panelTransitDrawable;
        public int shortcutProperty;
        public String taskName;
    }

    /* JADX WARN: Type inference failed for: r7v11, types: [com.android.systemui.statusbar.KeyguardShortcutManager$updateShortcutsRunnable$1] */
    /* JADX WARN: Type inference failed for: r7v15, types: [com.android.systemui.statusbar.KeyguardShortcutManager$intentReceiver$1] */
    public KeyguardShortcutManager(Context context, BroadcastDispatcher broadcastDispatcher, Executor executor, Handler handler, UserTracker userTracker, SelectedUserInteractor selectedUserInteractor, KeyguardUpdateMonitor keyguardUpdateMonitor, SettingsHelper settingsHelper, PackageManager packageManager, KeyguardStateController keyguardStateController, Set<KeyguardQuickAffordanceConfig> set, DumpManager dumpManager, SecLockscreenTileHost secLockscreenTileHost, UserSwitcherController userSwitcherController, KeyguardDisplayManager keyguardDisplayManager) throws Resources.NotFoundException {
        String[] stringArray;
        String[] stringArray2;
        this.context = context;
        this.executor = executor;
        this.handler = handler;
        this.userTracker = userTracker;
        this.selectedUserInteractor = selectedUserInteractor;
        this.keyguardUpdateMonitor = keyguardUpdateMonitor;
        this.settingsHelper = settingsHelper;
        this.packageManager = packageManager;
        this.keyguardStateController = keyguardStateController;
        this.taskConfigs = set;
        this.dumpManager = dumpManager;
        this.userSwitcherController = userSwitcherController;
        this.keyguardDisplayManager = keyguardDisplayManager;
        KeyguardShortcutManager$Companion$EMPTY_CONFIG$1 keyguardShortcutManager$Companion$EMPTY_CONFIG$1 = EMPTY_CONFIG;
        this.keyguardBottomAreaShortcutTask = new KeyguardQuickAffordanceConfig[]{keyguardShortcutManager$Companion$EMPTY_CONFIG$1, keyguardShortcutManager$Companion$EMPTY_CONFIG$1};
        this.keyguardShortcutDialerUpdateManager = new KeyguardShortcutDialerUpdateManager(context, settingsHelper);
        this.shortcutsData = new ShortcutData[]{new ShortcutData(), new ShortcutData()};
        this.stringBuilder = new StringBuilder();
        this.shortcutCallbacks = new ArrayList();
        this.updateShortcutsRunnable = new Runnable() { // from class: com.android.systemui.statusbar.KeyguardShortcutManager$updateShortcutsRunnable$1
            @Override // java.lang.Runnable
            public final void run() {
                KeyguardShortcutManager.access$handleUpdateShortcuts(this.this$0);
            }
        };
        this.themeShortcutHashMap = new HashMap();
        this.isReduceTransparencyEnabled = settingsHelper.isReduceTransparencyEnabled();
        this.wallpaperBrightness = -1;
        this.intentReceiver = new BroadcastReceiver() { // from class: com.android.systemui.statusbar.KeyguardShortcutManager$intentReceiver$1
            /* JADX WARN: Failed to restore switch over string. Please report as a decompilation issue */
            /* JADX WARN: Removed duplicated region for block: B:70:0x0121  */
            /* JADX WARN: Removed duplicated region for block: B:85:0x0167  */
            @Override // android.content.BroadcastReceiver
            /*
                Code decompiled incorrectly, please refer to instructions dump.
            */
            public final void onReceive(Context context2, Intent intent) {
                Uri data;
                String schemeSpecificPart;
                Uri data2;
                String schemeSpecificPart2;
                String action = intent.getAction();
                if (action != null) {
                    switch (action.hashCode()) {
                        case -1662080879:
                            if (!action.equals("com.sec.android.app.secsetupwizard.SETUPWIZARD_COMPLETE")) {
                            }
                            Log.d("KeyguardShortcutManager", "onReceive : ".concat(action));
                            this.this$0.keyguardShortcutDialerUpdateManager.updateLockShortcutDialerApp(intent);
                            break;
                        case -1001645458:
                            if (!action.equals("android.intent.action.PACKAGES_SUSPENDED")) {
                            }
                            String[] stringArrayExtra = intent.getStringArrayExtra("android.intent.extra.changed_package_list");
                            for (int i = 0; i < 2; i++) {
                                if (this.this$0.shortcutsData[i].componentName != null && stringArrayExtra != null) {
                                    List listListOf = CollectionsKt__CollectionsKt.listOf(Arrays.copyOf(stringArrayExtra, stringArrayExtra.length));
                                    ComponentName componentName = this.this$0.shortcutsData[i].componentName;
                                    if (listListOf.contains(componentName != null ? componentName.getPackageName() : null)) {
                                        ComponentName componentName2 = this.this$0.shortcutsData[i].componentName;
                                        MediaSessions$H$$ExternalSyntheticOutline0.m("onReceive : ", action, ", suspended shortcut ", componentName2 != null ? componentName2.getPackageName() : null, "KeyguardShortcutManager");
                                        KeyguardShortcutManager keyguardShortcutManager = this.this$0;
                                        keyguardShortcutManager.executor.execute(new KeyguardShortcutManager$updateShortcut$1(keyguardShortcutManager.shortcutsData[i].componentName, keyguardShortcutManager, i));
                                    }
                                }
                            }
                            break;
                        case -810471698:
                            if (!action.equals("android.intent.action.PACKAGE_REPLACED")) {
                            }
                            data2 = intent.getData();
                            if (data2 != null && (schemeSpecificPart2 = data2.getSchemeSpecificPart()) != null) {
                                KeyguardShortcutManager keyguardShortcutManager2 = this.this$0;
                                for (int i2 = 0; i2 < 2; i2++) {
                                    ComponentName componentName3 = keyguardShortcutManager2.shortcutsData[i2].componentName;
                                    if (componentName3 != null && schemeSpecificPart2.equals(componentName3.getPackageName())) {
                                        MediaSessions$H$$ExternalSyntheticOutline0.m("onReceive : ", action, ", starting update of shortcut ", schemeSpecificPart2, "KeyguardShortcutManager");
                                        keyguardShortcutManager2.executor.execute(new KeyguardShortcutManager$updateShortcut$1(keyguardShortcutManager2.shortcutsData[i2].componentName, keyguardShortcutManager2, i2));
                                    }
                                }
                                break;
                            }
                            break;
                        case -224747295:
                            if (action.equals("com.samsung.android.action.LOCK_TASK_MODE")) {
                                KeyguardShortcutManager keyguardShortcutManager3 = this.this$0;
                                keyguardShortcutManager3.isLockTaskMode = ((ActivityManager) keyguardShortcutManager3.context.getSystemService("activity")).getLockTaskModeState() == 1;
                                Log.d("KeyguardShortcutManager", "onReceive : " + action + ", mIsLocksTaskModeLocked : " + this.this$0.isLockTaskMode);
                                break;
                            }
                            break;
                        case -147579983:
                            if (action.equals("com.samsung.intent.action.EMERGENCY_STATE_CHANGED")) {
                                int intExtra = intent.getIntExtra("reason", 0);
                                Log.d("KeyguardShortcutManager", ConstraintSet$WriteJsonEngine$$ExternalSyntheticOutline0.m(intExtra, "onReceive : ", action, ", with state ", ", updating shortcuts"));
                                if (intExtra == 3 || intExtra == 5) {
                                    this.this$0.updateShortcuts();
                                    break;
                                }
                            }
                            break;
                        case -19011148:
                            if (!action.equals("android.intent.action.LOCALE_CHANGED")) {
                            }
                            this.this$0.updateShortcuts();
                            break;
                        case 30791346:
                            if (!action.equals("android.telecom.action.DEFAULT_DIALER_CHANGED")) {
                            }
                            Log.d("KeyguardShortcutManager", "onReceive : ".concat(action));
                            this.this$0.keyguardShortcutDialerUpdateManager.updateLockShortcutDialerApp(intent);
                            break;
                        case 172491798:
                            if (!action.equals("android.intent.action.PACKAGE_CHANGED")) {
                            }
                            data2 = intent.getData();
                            if (data2 != null) {
                                break;
                            }
                            break;
                        case 525384130:
                            if (action.equals("android.intent.action.PACKAGE_REMOVED")) {
                                Bundle extras = intent.getExtras();
                                extras.getClass();
                                if (!extras.getBoolean("android.intent.extra.REPLACING") && (data = intent.getData()) != null && (schemeSpecificPart = data.getSchemeSpecificPart()) != null) {
                                    KeyguardShortcutManager keyguardShortcutManager4 = this.this$0;
                                    Log.d("KeyguardShortcutManager", "onReceive : Intent.EXTRA_REPLACING false, ".concat(schemeSpecificPart));
                                    for (int i3 = 0; i3 < 2; i3++) {
                                        ComponentName componentName4 = keyguardShortcutManager4.shortcutsData[i3].componentName;
                                        if (componentName4 != null && schemeSpecificPart.equals(componentName4.getPackageName())) {
                                            KeyguardShortcutManager.access$resetShortcut(keyguardShortcutManager4, i3);
                                        }
                                    }
                                    break;
                                }
                            }
                            break;
                        case 1129769556:
                            if (!action.equals("com.samsung.applock.intent.action.APPLOCK_ENABLE_CHANGED")) {
                            }
                            this.this$0.updateShortcuts();
                            break;
                        case 1290767157:
                            if (!action.equals("android.intent.action.PACKAGES_UNSUSPENDED")) {
                            }
                            String[] stringArrayExtra2 = intent.getStringArrayExtra("android.intent.extra.changed_package_list");
                            while (i < 2) {
                            }
                            break;
                        case 1544582882:
                            if (!action.equals("android.intent.action.PACKAGE_ADDED")) {
                            }
                            data2 = intent.getData();
                            if (data2 != null) {
                            }
                            break;
                        case 2039271079:
                            if (!action.equals("com.samsung.applock.intent.action.SSECURE_UPDATE")) {
                            }
                            this.this$0.updateShortcuts();
                            break;
                    }
                }
            }
        };
        if (!Intrinsics.areEqual(Process.myUserHandle(), UserHandle.SYSTEM)) {
            Log.d("KeyguardShortcutManager", "dont initialize for other than system user");
            return;
        }
        this.keyguardBottomAreaShortcutTask = new KeyguardQuickAffordanceConfig[]{new KeyguardSecTilesQuickAffordanceConfig(context, secLockscreenTileHost), new KeyguardSecTilesQuickAffordanceConfig(context, secLockscreenTileHost)};
        try {
            stringArray = context.getResources().getStringArray(R.array.theme_app_icon_package);
            stringArray2 = context.getResources().getStringArray(R.array.theme_app_icon_drawable);
        } catch (IllegalArgumentException e) {
            Log.d("KeyguardShortcutManager", "Making theme hash error : " + e);
        }
        if (stringArray.length != stringArray2.length) {
            Log.d("KeyguardShortcutManager", "themeAppIconPackageArray error :" + stringArray.length);
            Log.d("KeyguardShortcutManager", "themeAppIconDrawableArray error :" + stringArray2.length);
            throw new IllegalArgumentException("Arrays must have the same size");
        }
        int length = stringArray2.length;
        for (int i = 0; i < length; i++) {
            this.themeShortcutHashMap.put(stringArray[i], stringArray2[i]);
        }
        this.shortcutIconSize = this.context.getResources().getDimensionPixelSize(R.dimen.keyguard_affordance_height);
        this.isShortcutVisibleForMDM = this.settingsHelper.isShortcutsVisibleForMDM();
        this.settingsHelper.registerCallback(this, Settings.System.getUriFor(SettingsHelper.INDEX_AWESOME_SHORTCUT_APP_LIST), Settings.System.getUriFor(SettingsHelper.INDEX_SET_SHORTCUTS_MODE), Settings.System.getUriFor(SettingsHelper.INDEX_CURRENT_SEC_APPICON_THEME_PACKAGE), Settings.System.getUriFor(SettingsHelper.INDEX_ACCESSIBILITY_REDUCE_TRANSPARENCY));
        this.keyguardUpdateMonitor.registerCallback(this);
        IntentFilter intentFilter = new IntentFilter();
        intentFilter.addAction("android.intent.action.PACKAGE_REMOVED");
        intentFilter.addAction("android.intent.action.PACKAGE_REPLACED");
        intentFilter.addAction("android.intent.action.PACKAGE_CHANGED");
        intentFilter.addAction("android.intent.action.PACKAGE_ADDED");
        intentFilter.addDataScheme("package");
        KeyguardShortcutManager$intentReceiver$1 keyguardShortcutManager$intentReceiver$1 = this.intentReceiver;
        UserHandle userHandle = UserHandle.CURRENT;
        BroadcastDispatcher.registerReceiver$default(broadcastDispatcher, keyguardShortcutManager$intentReceiver$1, intentFilter, null, userHandle, 0, null, 48);
        IntentFilter intentFilter2 = new IntentFilter();
        intentFilter2.addAction("android.intent.action.LOCALE_CHANGED");
        intentFilter2.addAction("android.intent.action.USER_SWITCHED");
        intentFilter2.addAction("com.samsung.intent.action.EMERGENCY_STATE_CHANGED");
        BroadcastDispatcher.registerReceiver$default(broadcastDispatcher, this.intentReceiver, intentFilter2, null, userHandle, 0, null, 48);
        BroadcastDispatcher.registerReceiver$default(broadcastDispatcher, this.intentReceiver, KeyguardSecUpdateMonitorImpl$$ExternalSyntheticOutline0.m("android.intent.action.PACKAGES_SUSPENDED", "android.intent.action.PACKAGES_UNSUSPENDED"), null, userHandle, 0, null, 48);
        IntentFilter intentFilter3 = new IntentFilter();
        intentFilter3.addAction("com.samsung.applock.intent.action.APPLOCK_ENABLE_CHANGED");
        intentFilter3.addAction("com.samsung.applock.intent.action.SSECURE_UPDATE");
        intentFilter3.addAction("com.samsung.android.action.LOCK_TASK_MODE");
        BroadcastDispatcher.registerReceiver$default(broadcastDispatcher, this.intentReceiver, intentFilter3, null, userHandle, 0, null, 48);
        BroadcastDispatcher.registerReceiver$default(broadcastDispatcher, this.intentReceiver, KeyguardSecUpdateMonitorImpl$$ExternalSyntheticOutline0.m("android.telecom.action.DEFAULT_DIALER_CHANGED", "com.sec.android.app.secsetupwizard.SETUPWIZARD_COMPLETE"), null, userHandle, 0, null, 48);
        DumpManager.registerDumpable$default(this.dumpManager, "KeyguardShortcutManager", this);
    }

    /* JADX WARN: Removed duplicated region for block: B:14:0x0067  */
    /* JADX WARN: Removed duplicated region for block: B:16:0x006a  */
    /* JADX WARN: Removed duplicated region for block: B:44:0x00d8 A[PHI: r6
      0x00d8: PHI (r6v30 android.graphics.drawable.Drawable) = (r6v21 android.graphics.drawable.Drawable), (r6v28 android.graphics.drawable.Drawable) binds: [B:43:0x00d6, B:54:0x00f3] A[DONT_GENERATE, DONT_INLINE]] */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    public static final Drawable access$getShortcutIcon(KeyguardShortcutManager keyguardShortcutManager, ActivityInfo activityInfo, boolean z, int i) {
        Drawable drawableLoadIcon;
        int identifier;
        Drawable monochrome;
        boolean z2 = keyguardShortcutManager.shortcutsData[i].isMonotoneIcon;
        String str = activityInfo.packageName;
        if (keyguardShortcutManager.settingsHelper.getActiveIconPackage() == null) {
            drawableLoadIcon = keyguardShortcutManager.getSamsungAppIconDrawable(str);
        } else {
            str.getClass();
            String str2 = (String) keyguardShortcutManager.themeShortcutHashMap.get(str);
            if (str2 == null) {
                str2 = null;
            }
            if (str2 == null || (identifier = keyguardShortcutManager.context.getResources().getIdentifier(str2, "drawable", keyguardShortcutManager.context.getPackageName())) == 0) {
                drawableLoadIcon = null;
                if (drawableLoadIcon == null) {
                    BitmapFactory.Options options = new BitmapFactory.Options();
                    options.inJustDecodeBounds = true;
                    BitmapFactory.decodeResource(keyguardShortcutManager.context.getResources(), R.drawable.ic_shortcut_theme_bg, options);
                    if (options.outWidth != 1) {
                        drawableLoadIcon = activityInfo.loadIcon(keyguardShortcutManager.packageManager, true, 256);
                    }
                }
            } else {
                BitmapFactory.Options options2 = new BitmapFactory.Options();
                options2.inJustDecodeBounds = true;
                BitmapFactory.decodeResource(keyguardShortcutManager.context.getResources(), identifier, options2);
                BitmapFactory.Options options3 = new BitmapFactory.Options();
                options3.inJustDecodeBounds = true;
                BitmapFactory.decodeResource(keyguardShortcutManager.context.getResources(), identifier, options3);
                if (options3.outWidth != 1) {
                    drawableLoadIcon = keyguardShortcutManager.context.getDrawable(identifier);
                }
                if (drawableLoadIcon == null) {
                }
            }
        }
        if (drawableLoadIcon == null) {
            drawableLoadIcon = activityInfo.loadIcon(keyguardShortcutManager.packageManager, true, 1);
        }
        if (drawableLoadIcon == null) {
            drawableLoadIcon = activityInfo.loadDefaultIcon(keyguardShortcutManager.packageManager);
        }
        int shortcutIconSizeValue = keyguardShortcutManager.getShortcutIconSizeValue(keyguardShortcutManager.isNowBarVisible);
        if (z2) {
            try {
                str.getClass();
                if ("com.sec.android.app.camera".equals(str) && keyguardShortcutManager.settingsHelper.getActiveIconPackage() == null) {
                    monochrome = keyguardShortcutManager.getSamsungAppIconDrawable(str);
                } else {
                    DrawableWrapper drawableWrapper = (DrawableWrapper) drawableLoadIcon;
                    AdaptiveIconDrawable adaptiveIconDrawable = (AdaptiveIconDrawable) (drawableWrapper != null ? drawableWrapper.getDrawable() : null);
                    monochrome = adaptiveIconDrawable != null ? adaptiveIconDrawable.getMonochrome() : null;
                }
                if (monochrome == null) {
                    if (isARShortcutIcon(str)) {
                        DrawableWrapper drawableWrapper2 = (DrawableWrapper) drawableLoadIcon;
                        AdaptiveIconDrawable adaptiveIconDrawable2 = (AdaptiveIconDrawable) (drawableWrapper2 != null ? drawableWrapper2.getDrawable() : null);
                        if (adaptiveIconDrawable2 != null) {
                            monochrome = adaptiveIconDrawable2.getForeground();
                            if (monochrome != null) {
                                drawableLoadIcon = monochrome;
                            }
                        }
                    }
                }
            } catch (Exception e) {
                KeyguardSecSimPinViewController$$ExternalSyntheticOutline0.m("Making samsung Icon error : ", e, "KeyguardShortcutManager");
            }
        }
        Bitmap bitmap = drawableLoadIcon != null ? DrawableKt.toBitmap(drawableLoadIcon, drawableLoadIcon.getIntrinsicWidth(), drawableLoadIcon.getIntrinsicHeight(), null) : null;
        if (keyguardShortcutManager.settingsHelper.getActiveIconPackage() == null || keyguardShortcutManager.isTaskType(i)) {
            if (!z2) {
                float f = keyguardShortcutManager.context.getResources().getDisplayMetrics().density * 2;
                if (bitmap == null) {
                    bitmap = null;
                } else if (shortcutIconSizeValue > 0) {
                    bitmap.setDensity(keyguardShortcutManager.context.getResources().getDisplayMetrics().densityDpi);
                    int i2 = (int) (shortcutIconSizeValue + f);
                    bitmap = Bitmap.createScaledBitmap(bitmap, i2, i2, true);
                }
                bitmap = bitmap != null ? getCircleBitmap(bitmap, f) : null;
                if (bitmap != null) {
                    bitmap.setDensity(keyguardShortcutManager.context.getResources().getDisplayMetrics().densityDpi);
                }
            } else if (bitmap != null) {
                if (!z) {
                    bitmap = keyguardShortcutManager.scaleIcon(bitmap, false);
                }
                bitmap = getCircleBitmap(bitmap, 0.0f);
                if (!z) {
                    if (!LsRune.LOCKUI_SHORTCUT_BLUR_BG) {
                        return new BitmapDrawable(keyguardShortcutManager.context.getResources(), imgShadow(bitmap, keyguardShortcutManager.getInvertColor(WallpaperUtils.isWhiteKeyguardWallpaper("navibar"), false)));
                    }
                    BitmapDrawable bitmapDrawable = new BitmapDrawable(keyguardShortcutManager.context.getResources(), bitmap);
                    boolean zIsWhiteKeyguardWallpaper = WallpaperUtils.isWhiteKeyguardWallpaper("navibar");
                    if (isARShortcutIcon(str)) {
                        return bitmapDrawable;
                    }
                    bitmapDrawable.mutate().setColorFilter(new BlendModeColorFilter(keyguardShortcutManager.getInvertColor(zIsWhiteKeyguardWallpaper, false), BlendMode.SRC_ATOP));
                    return bitmapDrawable;
                }
                new BitmapDrawable(keyguardShortcutManager.context.getResources(), bitmap);
            }
        }
        return bitmap != null ? new BitmapDrawable(keyguardShortcutManager.context.getResources(), bitmap) : null;
    }

    /* JADX WARN: Removed duplicated region for block: B:43:0x0158  */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    public static final void access$handleUpdateShortcuts(final KeyguardShortcutManager keyguardShortcutManager) {
        final int i;
        String shortcutAppList = keyguardShortcutManager.settingsHelper.getShortcutAppList();
        boolean zIsEmpty = TextUtils.isEmpty(shortcutAppList);
        ShortcutData[] shortcutDataArr = keyguardShortcutManager.shortcutsData;
        if (zIsEmpty) {
            List listAsList = Arrays.asList(0, 1);
            ArrayList arrayList = new ArrayList(CollectionsKt__IterablesKt.collectionSizeOrDefault(listAsList, 10));
            Iterator it = listAsList.iterator();
            while (it.hasNext()) {
                ShortcutData shortcutData = shortcutDataArr[((Number) it.next()).intValue()];
                shortcutData.taskName = null;
                shortcutData.componentName = null;
                shortcutData.enabled = false;
                arrayList.add(Unit.INSTANCE);
            }
        } else {
            shortcutAppList.getClass();
            StringBuilder sb = keyguardShortcutManager.stringBuilder;
            sb.setLength(0);
            int length = shortcutAppList.length();
            for (int i2 = 0; i2 < length; i2++) {
                if (i2 % 5 == 0) {
                    sb.append((char) (shortcutAppList.codePointAt(i2) + 1));
                } else {
                    sb.append(shortcutAppList.charAt(i2));
                }
            }
            Log.d("KeyguardShortcutManager", "getSettingValues(" + sb.toString() + ")");
            String[] strArr = (String[]) new Regex(";").split(shortcutAppList).toArray(new String[0]);
            if (strArr.length < 4) {
                List listAsList2 = Arrays.asList(0, 1);
                ArrayList arrayList2 = new ArrayList(CollectionsKt__IterablesKt.collectionSizeOrDefault(listAsList2, 10));
                Iterator it2 = listAsList2.iterator();
                while (it2.hasNext()) {
                    ShortcutData shortcutData2 = shortcutDataArr[((Number) it2.next()).intValue()];
                    shortcutData2.taskName = null;
                    shortcutData2.componentName = null;
                    shortcutData2.enabled = false;
                    arrayList2.add(Unit.INSTANCE);
                }
                Unit unit = Unit.INSTANCE;
                for (i = 0; i < 2; i++) {
                    if (keyguardShortcutManager.isTaskType(i)) {
                        final String str = shortcutDataArr[i].taskName;
                        keyguardShortcutManager.executor.execute(new Runnable() { // from class: com.android.systemui.statusbar.KeyguardShortcutManager$updateTaskShortcut$1
                            @Override // java.lang.Runnable
                            public final void run() {
                                try {
                                    final int i3 = i;
                                    final KeyguardShortcutManager keyguardShortcutManager2 = keyguardShortcutManager;
                                    final String str2 = str;
                                    if (new Predicate() { // from class: com.android.systemui.statusbar.KeyguardShortcutManager$updateTaskShortcut$1.1
                                        @Override // java.util.function.Predicate
                                        public final boolean test(Object obj) {
                                            if (((String) obj) == null) {
                                                NotificationManagerCompat$SideChannelManager$$ExternalSyntheticOutline0.m(i3, "updateTaskShortcut : ", " is disabled from settings", "KeyguardShortcutManager");
                                                return false;
                                            }
                                            KeyguardShortcutManager keyguardShortcutManager3 = keyguardShortcutManager2;
                                            KeyguardQuickAffordanceConfig[] keyguardQuickAffordanceConfigArr = keyguardShortcutManager3.keyguardBottomAreaShortcutTask;
                                            int i4 = i3;
                                            keyguardQuickAffordanceConfigArr[i4] = keyguardShortcutManager3.getKeyguardBottomAreaShortcutTask(i4, keyguardShortcutManager3.shortcutsData[i4].taskName);
                                            MediaBrowserCompat$MediaBrowserImplBase$$ExternalSyntheticOutline0.m("updateTaskShortcut: taskName =  ", keyguardShortcutManager2.shortcutsData[i3].taskName, "KeyguardShortcutManager");
                                            if (Intrinsics.areEqual(keyguardShortcutManager2.keyguardBottomAreaShortcutTask[i3], KeyguardShortcutManager.EMPTY_CONFIG)) {
                                                NotificationManagerCompat$SideChannelManager$$ExternalSyntheticOutline0.m(i3, "updateTaskShortcut : ", " is invalid task name", "KeyguardShortcutManager");
                                                return false;
                                            }
                                            if (!keyguardShortcutManager2.keyguardBottomAreaShortcutTask[i3].isAvailable()) {
                                                int i5 = i3;
                                                String str3 = str2;
                                                ExifInterface$$ExternalSyntheticOutline0.m(KeyguardBiometricLockoutLogger$mKeyguardUpdateMonitorCallback$1$$ExternalSyntheticOutline0.m(i5, "updateTaskShortcut : ", " Shortcut set to ", str3, " but "), str3, " is not supported for the device", "KeyguardShortcutManager");
                                                return false;
                                            }
                                            KeyguardShortcutManager keyguardShortcutManager4 = keyguardShortcutManager2;
                                            KeyguardShortcutManager.ShortcutData[] shortcutDataArr2 = keyguardShortcutManager4.shortcutsData;
                                            int i6 = i3;
                                            KeyguardShortcutManager.ShortcutData shortcutData3 = shortcutDataArr2[i6];
                                            shortcutData3.noUnlockNeeded = true;
                                            shortcutData3.enabled = true;
                                            shortcutData3.isMonotoneIcon = true;
                                            boolean zIsTaskTypeEnabled = keyguardShortcutManager4.isTaskTypeEnabled(i6);
                                            KeyguardShortcutManager keyguardShortcutManager5 = keyguardShortcutManager2;
                                            KeyguardShortcutManager.ShortcutData[] shortcutDataArr3 = keyguardShortcutManager5.shortcutsData;
                                            int i7 = i3;
                                            shortcutDataArr3[i7].isIconPaddingNeeded = keyguardShortcutManager5.keyguardBottomAreaShortcutTask[i7].isIconPaddingRequired();
                                            KeyguardShortcutManager keyguardShortcutManager6 = keyguardShortcutManager2;
                                            KeyguardShortcutManager.ShortcutData[] shortcutDataArr4 = keyguardShortcutManager6.shortcutsData;
                                            int i8 = i3;
                                            shortcutDataArr4[i8].drawable = keyguardShortcutManager6.convertTaskDrawable(keyguardShortcutManager6.keyguardBottomAreaShortcutTask[i8].getDrawable(), WallpaperUtils.isWhiteKeyguardWallpaper("navibar"), zIsTaskTypeEnabled, false, keyguardShortcutManager2.shortcutsData[i3].isIconPaddingNeeded);
                                            KeyguardShortcutManager keyguardShortcutManager7 = keyguardShortcutManager2;
                                            KeyguardShortcutManager.ShortcutData[] shortcutDataArr5 = keyguardShortcutManager7.shortcutsData;
                                            int i9 = i3;
                                            shortcutDataArr5[i9].panelDrawable = keyguardShortcutManager7.keyguardBottomAreaShortcutTask[i9].getDrawable();
                                            KeyguardShortcutManager keyguardShortcutManager8 = keyguardShortcutManager2;
                                            KeyguardShortcutManager.ShortcutData[] shortcutDataArr6 = keyguardShortcutManager8.shortcutsData;
                                            int i10 = i3;
                                            shortcutDataArr6[i10].panelTransitDrawable = keyguardShortcutManager8.keyguardBottomAreaShortcutTask[i10].getPanelIconTransitionDrawable();
                                            KeyguardShortcutManager keyguardShortcutManager9 = keyguardShortcutManager2;
                                            KeyguardShortcutManager.ShortcutData[] shortcutDataArr7 = keyguardShortcutManager9.shortcutsData;
                                            int i11 = i3;
                                            KeyguardShortcutManager.ShortcutData shortcutData4 = shortcutDataArr7[i11];
                                            shortcutData4.componentName = null;
                                            shortcutData4.appLabel = keyguardShortcutManager9.keyguardBottomAreaShortcutTask[i11].pickerName();
                                            KeyguardShortcutManager keyguardShortcutManager10 = keyguardShortcutManager2;
                                            KeyguardShortcutManager.ShortcutData[] shortcutDataArr8 = keyguardShortcutManager10.shortcutsData;
                                            int i12 = i3;
                                            shortcutDataArr8[i12].isUnlockWaitNeeded = keyguardShortcutManager10.keyguardBottomAreaShortcutTask[i12].isUnlockWaitRequired();
                                            int i13 = i3;
                                            KeyguardCarrierViewController$2$$ExternalSyntheticOutline0.m(i13, "updateTaskShortcut th : ", " class : ", keyguardShortcutManager2.shortcutsData[i13].taskName, "KeyguardShortcutManager");
                                            keyguardShortcutManager2.getQuickAffordanceConfigList();
                                            return true;
                                        }
                                    }.test(str)) {
                                        final KeyguardShortcutManager keyguardShortcutManager3 = keyguardShortcutManager;
                                        Handler handler = keyguardShortcutManager3.handler;
                                        final int i4 = i;
                                        handler.post(new Runnable() { // from class: com.android.systemui.statusbar.KeyguardShortcutManager$updateTaskShortcut$1.2
                                            @Override // java.lang.Runnable
                                            public final void run() {
                                                KeyguardShortcutManager keyguardShortcutManager4 = keyguardShortcutManager3;
                                                int i5 = i4;
                                                KeyguardShortcutManager.Companion companion = KeyguardShortcutManager.Companion;
                                                keyguardShortcutManager4.sendUpdateShortcutViewToCallback(i5);
                                            }
                                        });
                                        return;
                                    }
                                    final KeyguardShortcutManager keyguardShortcutManager4 = keyguardShortcutManager;
                                    Handler handler2 = keyguardShortcutManager4.handler;
                                    final int i5 = i;
                                    handler2.post(new Runnable() { // from class: com.android.systemui.statusbar.KeyguardShortcutManager$updateTaskShortcut$1.3
                                        @Override // java.lang.Runnable
                                        public final void run() {
                                            KeyguardShortcutManager.access$resetShortcut(keyguardShortcutManager4, i5);
                                        }
                                    });
                                } catch (Exception e) {
                                    keyguardShortcutManager.settingsHelper.resetShortcutValue(keyguardShortcutManager.selectedUserInteractor.getSelectedUserId());
                                    Log.e("KeyguardShortcutManager", "getPositionCorrectionRatio exception = " + e);
                                }
                            }
                        });
                    } else {
                        keyguardShortcutManager.keyguardBottomAreaShortcutTask[i].setSpecName("");
                        keyguardShortcutManager.executor.execute(new KeyguardShortcutManager$updateShortcut$1(shortcutDataArr[i].componentName, keyguardShortcutManager, i));
                    }
                }
                SharedPreferences.Editor editorEdit = keyguardShortcutManager.context.getSharedPreferences(SystemUIAnalytics.LOCK_PREF_NAME, 0).edit();
                editorEdit.putString(SystemUIAnalytics.STATUS_ID_LOCK_LEFT_SHORTCUT, keyguardShortcutManager.getComponentNameForSALogging(0));
                editorEdit.putString(SystemUIAnalytics.STATUS_ID_LOCK_RIGHT_SHORTCUT, keyguardShortcutManager.getComponentNameForSALogging(1));
                editorEdit.apply();
            }
            int length2 = strArr.length / 2;
            for (int i3 = 0; i3 < length2 && i3 < 2; i3++) {
                int i4 = i3 * 2;
                if ("1".equals(strArr[i4])) {
                    int i5 = i4 + 1;
                    String str2 = strArr[i5];
                    if (str2 == null || !StringsKt__StringsKt.contains(str2, "NoUnlockNeeded", false)) {
                        shortcutDataArr[i3].shortcutProperty = 0;
                        String str3 = strArr[i5];
                        str3.getClass();
                        ComponentName componentNameUnflattenFromString = ComponentName.unflattenFromString(str3);
                        if (componentNameUnflattenFromString == null) {
                            PackageManager packageManager = keyguardShortcutManager.packageManager;
                            String str4 = strArr[i5];
                            str4.getClass();
                            Intent launchIntentForPackage = packageManager.getLaunchIntentForPackage(str4);
                            if (launchIntentForPackage != null) {
                                componentNameUnflattenFromString = launchIntentForPackage.getComponent();
                            }
                        }
                        shortcutDataArr[i3].componentName = componentNameUnflattenFromString;
                    } else {
                        ShortcutData shortcutData3 = shortcutDataArr[i3];
                        shortcutData3.shortcutProperty = 1;
                        String str5 = strArr[i5];
                        str5.getClass();
                        String str6 = strArr[i5];
                        str6.getClass();
                        shortcutData3.taskName = str5.substring(StringsKt__StringsKt.lastIndexOf$default(str6, "/", 6) + 1);
                    }
                } else {
                    String str7 = strArr[i4];
                    ShortcutData shortcutData4 = shortcutDataArr[i3];
                    shortcutData4.taskName = null;
                    shortcutData4.componentName = null;
                    shortcutData4.enabled = false;
                }
            }
        }
        Unit unit2 = Unit.INSTANCE;
        while (i < 2) {
        }
        SharedPreferences.Editor editorEdit2 = keyguardShortcutManager.context.getSharedPreferences(SystemUIAnalytics.LOCK_PREF_NAME, 0).edit();
        editorEdit2.putString(SystemUIAnalytics.STATUS_ID_LOCK_LEFT_SHORTCUT, keyguardShortcutManager.getComponentNameForSALogging(0));
        editorEdit2.putString(SystemUIAnalytics.STATUS_ID_LOCK_RIGHT_SHORTCUT, keyguardShortcutManager.getComponentNameForSALogging(1));
        editorEdit2.apply();
    }

    public static final boolean access$isMonotoneIconRequired(KeyguardShortcutManager keyguardShortcutManager, int i) {
        Drawable drawable;
        if (keyguardShortcutManager.settingsHelper.getActiveIconPackage() == null) {
            ResolveInfo resolveInfoResolveActivityAsUser = keyguardShortcutManager.packageManager.resolveActivityAsUser(Intent.makeMainActivity(keyguardShortcutManager.shortcutsData[i].componentName), 129, ((UserTrackerImpl) keyguardShortcutManager.userTracker).getUserId());
            ActivityInfo activityInfo = resolveInfoResolveActivityAsUser != null ? resolveInfoResolveActivityAsUser.activityInfo : null;
            if (activityInfo == null) {
                Slog.d("KeyguardShortcutManager", "updateShortcut : " + i + " activityInfo is null, resolveInfo is : " + resolveInfoResolveActivityAsUser + ",  return FALSE");
                return false;
            }
            Drawable samsungAppIconDrawable = keyguardShortcutManager.settingsHelper.getActiveIconPackage() == null ? keyguardShortcutManager.getSamsungAppIconDrawable(activityInfo.packageName) : null;
            if (samsungAppIconDrawable == null) {
                samsungAppIconDrawable = activityInfo.loadIcon(keyguardShortcutManager.packageManager, true, 1);
            }
            if (samsungAppIconDrawable == null) {
                samsungAppIconDrawable = activityInfo.loadDefaultIcon(keyguardShortcutManager.packageManager);
            }
            if ((!"com.sec.android.app.camera".equals(activityInfo.packageName) || keyguardShortcutManager.settingsHelper.getActiveIconPackage() != null) && !isARShortcutIcon(activityInfo.packageName)) {
                Bundle bundle = activityInfo.metaData;
                if ((bundle != null ? bundle.getString("com.sec.android.app.launcher.icon_theme", null) : null) != null) {
                    DrawableWrapper drawableWrapper = samsungAppIconDrawable instanceof DrawableWrapper ? (DrawableWrapper) samsungAppIconDrawable : null;
                    if (drawableWrapper != null && (drawable = drawableWrapper.getDrawable()) != null) {
                        samsungAppIconDrawable = drawable;
                    }
                    AdaptiveIconDrawable adaptiveIconDrawable = samsungAppIconDrawable instanceof AdaptiveIconDrawable ? (AdaptiveIconDrawable) samsungAppIconDrawable : null;
                    if ((adaptiveIconDrawable != null ? adaptiveIconDrawable.getMonochrome() : null) != null) {
                    }
                }
            }
            return true;
        }
        return false;
    }

    public static final void access$resetShortcut(KeyguardShortcutManager keyguardShortcutManager, int i) {
        ShortcutData shortcutData = keyguardShortcutManager.shortcutsData[i];
        shortcutData.enabled = false;
        shortcutData.drawable = null;
        shortcutData.panelDrawable = null;
        shortcutData.panelTransitDrawable = null;
        shortcutData.appLabel = null;
        keyguardShortcutManager.sendUpdateShortcutViewToCallback(i);
    }

    public static final void access$sendUpdateIconOnlyToCallback(KeyguardShortcutManager keyguardShortcutManager, final int i) {
        ArrayList arrayList = keyguardShortcutManager.shortcutCallbacks;
        int size = arrayList.size();
        int i2 = 0;
        while (i2 < size) {
            Object obj = arrayList.get(i2);
            i2++;
            WeakReference weakReference = (WeakReference) obj;
            if (((KeyguardSecBottomAreaViewController$shortcutManagerCallback$1) weakReference.get()) != null) {
                Object obj2 = weakReference.get();
                obj2.getClass();
                final KeyguardSecBottomAreaViewController keyguardSecBottomAreaViewController = ((KeyguardSecBottomAreaViewController$shortcutManagerCallback$1) obj2).this$0;
                ((KeyguardSecBottomAreaView) ((ViewController) keyguardSecBottomAreaViewController).mView).post(new Runnable() { // from class: com.android.systemui.statusbar.phone.KeyguardSecBottomAreaViewController$shortcutManagerCallback$1$updateShortcutIconOnly$1
                    @Override // java.lang.Runnable
                    public final void run() {
                        if (i == 0) {
                            KeyguardSecBottomAreaViewController keyguardSecBottomAreaViewController2 = keyguardSecBottomAreaViewController;
                            String str = KeyguardSecBottomAreaViewController.KEY_HELP_TEXT_VISIBILITY;
                            if (keyguardSecBottomAreaViewController2.getLeftView() != null) {
                                KeyguardSecBottomAreaViewController keyguardSecBottomAreaViewController3 = keyguardSecBottomAreaViewController;
                                keyguardSecBottomAreaViewController3.updateCustomShortcutIcon(keyguardSecBottomAreaViewController3.getLeftView(), 0, keyguardSecBottomAreaViewController.shortcutManager.hasShortcut(0));
                                return;
                            }
                            return;
                        }
                        KeyguardSecBottomAreaViewController keyguardSecBottomAreaViewController4 = keyguardSecBottomAreaViewController;
                        String str2 = KeyguardSecBottomAreaViewController.KEY_HELP_TEXT_VISIBILITY;
                        if (keyguardSecBottomAreaViewController4.getRightView() != null) {
                            KeyguardSecBottomAreaViewController keyguardSecBottomAreaViewController5 = keyguardSecBottomAreaViewController;
                            keyguardSecBottomAreaViewController5.updateCustomShortcutIcon(keyguardSecBottomAreaViewController5.getRightView(), 1, keyguardSecBottomAreaViewController.shortcutManager.hasShortcut(1));
                        }
                    }
                });
            }
        }
    }

    public static Bitmap getCircleBitmap(Bitmap bitmap, float f) {
        int width = bitmap.getWidth();
        int height = bitmap.getHeight();
        Bitmap bitmapCreateBitmap = Bitmap.createBitmap(width, height, Bitmap.Config.ARGB_8888);
        Path path = new Path();
        float f2 = width / 2;
        float f3 = height / 2;
        path.addCircle(f2, f3, Math.min(f2 - f, f3 - f), Path.Direction.CCW);
        Canvas canvas = new Canvas(bitmapCreateBitmap);
        canvas.clipPath(path);
        canvas.drawBitmap(bitmap, 0.0f, 0.0f, (Paint) null);
        return bitmapCreateBitmap;
    }

    public static Bitmap imgShadow(Bitmap bitmap, int i) {
        int width = bitmap.getWidth();
        int height = bitmap.getHeight();
        Bitmap bitmapCreateBitmap = Bitmap.createBitmap(width, height, Bitmap.Config.ALPHA_8);
        Matrix matrix = new Matrix();
        matrix.setRectToRect(new RectF(0.0f, 0.0f, bitmap.getWidth(), bitmap.getHeight()), new RectF(0.0f, 0.0f, width, height), Matrix.ScaleToFit.CENTER);
        Matrix matrix2 = new Matrix(matrix);
        matrix2.postTranslate(0.0f, 0.0f);
        Canvas canvas = new Canvas(bitmapCreateBitmap);
        Paint paint = new Paint(1);
        canvas.drawBitmap(bitmap, matrix, paint);
        paint.setXfermode(new PorterDuffXfermode(PorterDuff.Mode.SRC_IN));
        canvas.drawBitmap(bitmap, matrix2, paint);
        BlurMaskFilter blurMaskFilter = new BlurMaskFilter(2.0f, BlurMaskFilter.Blur.OUTER);
        paint.reset();
        paint.setAntiAlias(true);
        paint.setColor(-16777216);
        paint.setAlpha(51);
        paint.setMaskFilter(blurMaskFilter);
        paint.setFilterBitmap(true);
        Paint paint2 = new Paint(1);
        paint2.setFilterBitmap(true);
        paint2.setColorFilter(new BlendModeColorFilter(i, BlendMode.SRC_ATOP));
        Bitmap bitmapCreateBitmap2 = Bitmap.createBitmap(width, height, Bitmap.Config.ARGB_8888);
        Canvas canvas2 = new Canvas(bitmapCreateBitmap2);
        canvas2.drawBitmap(bitmapCreateBitmap, 0.0f, 0.0f, paint);
        canvas2.drawBitmap(bitmap, matrix, paint2);
        bitmapCreateBitmap.recycle();
        return bitmapCreateBitmap2;
    }

    public static boolean isARShortcutIcon(String str) {
        return "com.samsung.android.aremoji".equals(str) || "com.sec.android.mimage.avatarstickers".equals(str);
    }

    public static boolean isAllowNonPlatformKeyApp(Context context, String str, String str2) throws NoSuchAlgorithmException {
        ArrayList arrayList = new ArrayList();
        arrayList.add(str2);
        Unit unit = Unit.INSTANCE;
        ArrayList arrayList2 = new ArrayList();
        try {
            MessageDigest messageDigest = MessageDigest.getInstance("SHA-256");
            SigningInfo signingInfo = context.getPackageManager().getPackageInfo(str, 134217728).signingInfo;
            signingInfo.getClass();
            for (Signature signature : signingInfo.hasMultipleSigners() ? signingInfo.getApkContentsSigners() : signingInfo.getSigningCertificateHistory()) {
                StringBuilder sb = new StringBuilder();
                for (byte b : messageDigest.digest(signature.toCharsString().getBytes(Charset.defaultCharset()))) {
                    CharsKt__CharJVMKt.checkRadix(16);
                    sb.append(Integer.toString((b & 255) + 256, 16).substring(1));
                }
                arrayList2.add(sb.toString());
            }
        } catch (Exception e) {
            Log.e("AppSignature", "isAllowNonPlatformKeyApp : " + e.getMessage());
        }
        int size = arrayList.size();
        int i = 0;
        while (i < size) {
            Object obj = arrayList.get(i);
            i++;
            if (arrayList2.contains((String) obj)) {
                return true;
            }
        }
        return false;
    }

    public static boolean isSamsungCameraPackage(ComponentName componentName) {
        if (componentName == null) {
            return false;
        }
        return "com.sec.android.app.camera".equals(componentName.getPackageName());
    }

    /* JADX WARN: Removed duplicated region for block: B:6:0x000c  */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    public final Drawable convertTaskDrawable(Drawable drawable, boolean z, boolean z2, boolean z3, boolean z4) {
        if (drawable == null) {
            return null;
        }
        int i = R.color.shortcut_white_tint_task_on;
        if (z3) {
            if (z) {
                i = R.color.shortcut_black_tint_task_on;
            }
        } else if (!this.isReduceTransparencyEnabled) {
            i = ((!z2 || z) && (z2 || this.wallpaperBrightness <= 84)) ? R.color.shortcut_icon_color_white : R.color.shortcut_icon_color_black;
        } else if (!z) {
        }
        int color = this.context.getColor(i);
        Bitmap bitmapScaleIcon = scaleIcon(DrawableKt.toBitmap(drawable, drawable.getIntrinsicWidth(), drawable.getIntrinsicHeight(), null), z4);
        if (!LsRune.LOCKUI_SHORTCUT_BLUR_BG && !z3) {
            return new BitmapDrawable(this.context.getResources(), imgShadow(bitmapScaleIcon, color));
        }
        BitmapDrawable bitmapDrawable = new BitmapDrawable(this.context.getResources(), bitmapScaleIcon);
        bitmapDrawable.mutate().clearColorFilter();
        bitmapDrawable.mutate().setTint(0);
        bitmapDrawable.setColorFilter(new BlendModeColorFilter(color, BlendMode.SRC_ATOP));
        return bitmapDrawable;
    }

    @Override // com.android.systemui.Dumpable
    public final void dump(PrintWriter printWriter, String[] strArr) {
        String packageName;
        printWriter.println("KeyguardShortcutManager state:");
        printWriter.println("  CurrentUserId = " + this.selectedUserInteractor.getSelectedUserId());
        printWriter.println("  Shortcut count = 2");
        ActiveUnlockConfig$$ExternalSyntheticOutline0.m(printWriter, "  Master switch = ", this.settingsHelper.isShortcutMasterEnabled());
        ActiveUnlockConfig$$ExternalSyntheticOutline0.m(printWriter, "  disabled shortcut by MDM = ", !this.isShortcutVisibleForMDM);
        printWriter.println("  wallpaperBrightness = " + this.wallpaperBrightness);
        ActiveUnlockConfig$$ExternalSyntheticOutline0.m(printWriter, "  white wallpaper = ", WallpaperUtils.isWhiteKeyguardWallpaper("navibar"));
        ShortcutData[] shortcutDataArr = this.shortcutsData;
        int length = shortcutDataArr.length;
        for (int i = 0; i < length; i++) {
            DeviceEntryFaceAuthRepositoryImpl$$ExternalSyntheticOutline0.m("  Shortcut ", i, printWriter);
            ShortcutData shortcutData = shortcutDataArr[i];
            if (shortcutData == null) {
                printWriter.println("    null");
            } else {
                ActiveUnlockConfig$$ExternalSyntheticOutline0.m(printWriter, "    enabled = ", shortcutData.enabled);
                printWriter.println("    component = " + shortcutData.componentName);
                ActionReceiver$$ExternalSyntheticOutline0.m(printWriter, "    label = ", shortcutData.appLabel);
                ActiveUnlockConfig$$ExternalSyntheticOutline0.m(printWriter, "    noUnlock = ", shortcutData.noUnlockNeeded);
                ActiveUnlockConfig$$ExternalSyntheticOutline0.m(printWriter, "    monotone = ", shortcutData.isMonotoneIcon);
                printWriter.println("    launchInsecureMain = " + shortcutData.launchInsecureMain);
                DeviceEntryFaceAuthRepositoryImpl$$ExternalSyntheticOutline0.m("    blurPreset = ", getColorCurvePreset(i), printWriter);
                ComponentName componentName = shortcutData.componentName;
                if (componentName != null) {
                    if (componentName == null || (packageName = componentName.getPackageName()) == null) {
                        packageName = "";
                    }
                    ActiveUnlockConfig$$ExternalSyntheticOutline0.m(printWriter, "    isSuspended = ", getSuspended(packageName));
                    ComponentName componentName2 = shortcutData.componentName;
                    ActiveUnlockConfig$$ExternalSyntheticOutline0.m(printWriter, "    isLockTaskPermitted = ", isLockTaskPermitted(componentName2 != null ? componentName2.getPackageName() : null));
                }
            }
        }
    }

    public final int getColorCurvePreset(int i) {
        boolean z = isTaskType(i) && isTaskTypeEnabled(i);
        boolean zIsWhiteKeyguardWallpaper = WallpaperUtils.isWhiteKeyguardWallpaper("navibar");
        if (z) {
            return zIsWhiteKeyguardWallpaper ? 120 : 105;
        }
        int i2 = this.wallpaperBrightness;
        if (i2 == -1) {
            return zIsWhiteKeyguardWallpaper ? 117 : 106;
        }
        if (i2 < 0 || i2 >= 29) {
            return (29 > i2 || i2 >= 85) ? 117 : 116;
        }
        return 106;
    }

    public final String getComponentNameForSALogging(int i) {
        String strM;
        if (i < 0 || i >= 2) {
            ClockEventController$$ExternalSyntheticOutline0.m(i, "IllegalArgument : ", "KeyguardShortcutManager");
            return "Empty";
        }
        ShortcutData[] shortcutDataArr = this.shortcutsData;
        if (shortcutDataArr[i].enabled) {
            if (isTaskType(i)) {
                String str = shortcutDataArr[i].taskName;
                if (str != null) {
                    return str;
                }
            } else {
                ComponentName componentName = shortcutDataArr[i].componentName;
                if (componentName != null && (strM = AbstractResolvableFuture$$ExternalSyntheticOutline0.m(componentName.getPackageName(), "/", componentName.getClassName())) != null) {
                    return strM;
                }
            }
        }
        return "Empty";
    }

    public final int getInvertColor(boolean z, boolean z2) {
        boolean z3 = this.isReduceTransparencyEnabled;
        return (((z3 || z2) && !z) || !(z2 || z3 || this.wallpaperBrightness <= 84)) ? this.context.getColor(R.color.shortcut_icon_color_black) : this.context.getColor(R.color.shortcut_icon_color_white);
    }

    public final KeyguardQuickAffordanceConfig getKeyguardBottomAreaShortcutTask(int i, String str) {
        Object next;
        if (isTaskType(i)) {
            KeyguardQuickAffordanceConfig[] keyguardQuickAffordanceConfigArr = this.keyguardBottomAreaShortcutTask;
            keyguardQuickAffordanceConfigArr[i].setSpecName(str);
            return keyguardQuickAffordanceConfigArr[i];
        }
        Iterator it = this.taskConfigs.iterator();
        while (true) {
            if (!it.hasNext()) {
                next = null;
                break;
            }
            next = it.next();
            if (Intrinsics.areEqual(((KeyguardQuickAffordanceConfig) next).getKey(), str)) {
                break;
            }
        }
        KeyguardQuickAffordanceConfig keyguardQuickAffordanceConfig = (KeyguardQuickAffordanceConfig) next;
        return keyguardQuickAffordanceConfig == null ? EMPTY_CONFIG : keyguardQuickAffordanceConfig;
    }

    public final int getNowBarBottomMargin(int i, int i2) {
        Lazy lazy;
        float f;
        float f2;
        float f3;
        boolean z = i2 == 2;
        if (!DeviceState.isTablet() && !DeviceState.isMultiFoldMain()) {
            if (!LsRune.LOCKUI_SUB_DISPLAY_LOCK || !((DisplayLifecycle) Dependency.sDependency.getDependencyInner(DisplayLifecycle.class)).mIsFolderOpened) {
                if (z) {
                    f2 = i;
                    f3 = 0.056f;
                } else {
                    f2 = i;
                    f3 = 0.035f;
                }
                f = f2 * f3;
            }
            return (int) f;
        }
        if (!z && (lazy = this.keyguardDisplayManager.mKeyguardDeskTopStateMonitorLazy) != null) {
            boolean z2 = ((KeyguardDeskTopStateMonitor) lazy.get()).mIsDesktopStandAlone;
        }
        f = i * 0.045f;
        return (int) f;
    }

    public final int getNowBarCollapsedHeight() {
        Number numberValueOf;
        float f = this.context.getResources().getDisplayMetrics().widthPixels;
        float f2 = this.context.getResources().getDisplayMetrics().heightPixels;
        float f3 = this.context.getResources().getDisplayMetrics().density;
        if (DeviceState.isLargeScreenTablet(this.context)) {
            numberValueOf = Float.valueOf(72 * f3);
        } else if (DeviceState.isTablet() || DeviceState.isMultiFoldMain()) {
            numberValueOf = Float.valueOf(62 * f3);
        } else if (LsRune.LOCKUI_SUB_DISPLAY_LOCK && ((DisplayLifecycle) Dependency.sDependency.getDependencyInner(DisplayLifecycle.class)).mIsFolderOpened) {
            numberValueOf = Float.valueOf(56 * f3);
        } else {
            if (f < f2) {
                f = f2;
            }
            numberValueOf = Integer.valueOf((int) (f * 0.07f));
        }
        return numberValueOf.intValue();
    }

    public final List getQuickAffordanceConfigList() {
        ComponentName componentName;
        KeyguardQuickAffordancePosition[] keyguardQuickAffordancePositionArrValues = KeyguardQuickAffordancePosition.values();
        ArrayList arrayList = new ArrayList(keyguardQuickAffordancePositionArrValues.length);
        for (KeyguardQuickAffordancePosition keyguardQuickAffordancePosition : keyguardQuickAffordancePositionArrValues) {
            final int iOrdinal = keyguardQuickAffordancePosition.ordinal();
            ShortcutData[] shortcutDataArr = this.shortcutsData;
            final ShortcutData shortcutData = shortcutDataArr[iOrdinal];
            arrayList.add((shortcutData == null || !shortcutData.enabled || (!isTaskType(iOrdinal) && ((componentName = shortcutData.componentName) == null || componentName.getPackageName() == null))) ? EMPTY_CONFIG : isTaskType(iOrdinal) ? getKeyguardBottomAreaShortcutTask(keyguardQuickAffordancePosition.ordinal(), shortcutDataArr[iOrdinal].taskName) : new KeyguardQuickAffordanceConfig(this, iOrdinal) { // from class: com.android.systemui.statusbar.KeyguardShortcutManager$generateQuickAffordanceConfig$1$1
                public final /* synthetic */ int $this_with;
                public final String key;
                public final FlowKt__BuildersKt$flowOf$$inlined$unsafeFlow$2 lockScreenState;
                public final int pickerIconResourceId;
                public final /* synthetic */ KeyguardShortcutManager this$0;

                {
                    String strFlattenToString;
                    this.this$0 = this;
                    this.$this_with = iOrdinal;
                    ComponentName componentName2 = this.$shortcutData.componentName;
                    this.key = (componentName2 == null || (strFlattenToString = componentName2.flattenToString()) == null) ? "" : strFlattenToString;
                    this.pickerIconResourceId = R.drawable.bg_bk;
                    Drawable drawable = this.context.getDrawable(R.drawable.bg_bk);
                    drawable.getClass();
                    this.lockScreenState = new FlowKt__BuildersKt$flowOf$$inlined$unsafeFlow$2(new KeyguardQuickAffordanceConfig.LockScreenState.Visible(new Icon.Loaded(drawable, new ContentDescription.Loaded((String) this.getShortcutContentDescription(iOrdinal)), null, 4, null), null, 2, null));
                }

                @Override // com.android.systemui.keyguard.data.quickaffordance.KeyguardQuickAffordanceConfig
                public final String getKey() {
                    return this.key;
                }

                @Override // com.android.systemui.keyguard.data.quickaffordance.KeyguardQuickAffordanceConfig
                public final Flow getLockScreenState() {
                    return this.lockScreenState;
                }

                @Override // com.android.systemui.keyguard.data.quickaffordance.KeyguardQuickAffordanceConfig
                public final int getPickerIconResourceId() {
                    return this.pickerIconResourceId;
                }

                @Override // com.android.systemui.keyguard.data.quickaffordance.KeyguardQuickAffordanceConfig
                public final KeyguardQuickAffordanceConfig.OnTriggeredResult onTriggered(Expandable expandable) {
                    KeyguardShortcutManager keyguardShortcutManager = this.this$0;
                    keyguardShortcutManager.getClass();
                    int i = this.$this_with;
                    Intent intentAddFlags = null;
                    if (i < 0 || i >= 2) {
                        ClockEventController$$ExternalSyntheticOutline0.m(i, "getIntent wrong param : ", "KeyguardShortcutManager");
                    } else {
                        KeyguardShortcutManager.ShortcutData[] shortcutDataArr2 = keyguardShortcutManager.shortcutsData;
                        if (KeyguardShortcutManager.isSamsungCameraPackage(shortcutDataArr2[i].componentName)) {
                            Log.d("KeyguardShortcutManager", "th = " + i + " is camera package");
                            intentAddFlags = keyguardShortcutManager.isSecure() ? KeyguardShortcutManager.SECURE_CAMERA_INTENT : KeyguardShortcutManager.INSECURE_CAMERA_INTENT;
                        } else {
                            ComponentName componentName2 = shortcutDataArr2[i].componentName;
                            if (componentName2 == null ? false : "com.samsung.android.app.galaxyraw".equals(componentName2.getPackageName())) {
                                NotificationManagerCompat$SideChannelManager$$ExternalSyntheticOutline0.m(i, "th = ", " is expert raw camera package", "KeyguardShortcutManager");
                                intentAddFlags = KeyguardShortcutManager.SAMSUNG_EXPERT_RAW_CAMERA_INTENT;
                            } else {
                                Intent intent = new Intent("android.intent.action.MAIN");
                                boolean zIsSecure = keyguardShortcutManager.isSecure();
                                if (zIsSecure || !shortcutDataArr2[i].launchInsecureMain) {
                                    intent.setComponent(shortcutDataArr2[i].componentName);
                                } else {
                                    intent.addCategory("android.intent.category.LAUNCHER");
                                    ComponentName componentName3 = shortcutDataArr2[i].componentName;
                                    intent.setPackage(componentName3 != null ? componentName3.getPackageName() : null);
                                    ResolveInfo resolveInfoResolveActivityAsUser = keyguardShortcutManager.packageManager.resolveActivityAsUser(intent, 1, keyguardShortcutManager.selectedUserInteractor.getSelectedUserId());
                                    if ((resolveInfoResolveActivityAsUser != null ? resolveInfoResolveActivityAsUser.activityInfo : null) != null) {
                                        ActivityInfo activityInfo = resolveInfoResolveActivityAsUser.activityInfo;
                                        intent.setComponent(new ComponentName(activityInfo.packageName, activityInfo.name));
                                    } else {
                                        intent.setComponent(shortcutDataArr2[i].componentName);
                                    }
                                }
                                intent.putExtra("isSecure", zIsSecure);
                                intentAddFlags = intent.addFlags(268500992);
                            }
                        }
                    }
                    intentAddFlags.getClass();
                    return new KeyguardQuickAffordanceConfig.OnTriggeredResult.StartActivity(intentAddFlags, keyguardShortcutManager.isNoUnlockNeeded(i));
                }

                @Override // com.android.systemui.keyguard.data.quickaffordance.KeyguardQuickAffordanceConfig
                public final String pickerName() {
                    String str = this.$shortcutData.appLabel;
                    return str == null ? "" : str;
                }
            });
        }
        return arrayList;
    }

    public final Drawable getSamsungAppIconDrawable(String str) {
        if (str == null) {
            return null;
        }
        int i = str.equals("com.sec.android.app.camera") ? R.drawable.fg_camera : 0;
        if (i != 0) {
            return this.context.getResources().getDrawable(i);
        }
        return null;
    }

    public final int getShortcutBottomMargin(boolean z) {
        int i = this.context.getResources().getDisplayMetrics().heightPixels;
        int i2 = this.context.getResources().getConfiguration().orientation;
        if (z || DeviceState.isTablet() || DeviceState.isMultiFoldMain()) {
            return getNowBarBottomMargin(i, i2) + ((getNowBarCollapsedHeight() - getShortcutIconSizeValue(true)) / 2);
        }
        double d = 0.051d;
        if (i2 == 1) {
            if (!LsRune.LOCKUI_SUB_DISPLAY_LOCK || !((DisplayLifecycle) Dependency.sDependency.getDependencyInner(DisplayLifecycle.class)).mIsFolderOpened) {
                d = 0.038d;
            }
        } else if (!LsRune.LOCKUI_SUB_DISPLAY_LOCK || !((DisplayLifecycle) Dependency.sDependency.getDependencyInner(DisplayLifecycle.class)).mIsFolderOpened) {
            d = 0.053d;
        }
        return (int) (i * d);
    }

    public final CharSequence getShortcutContentDescription(int i) {
        if (i < 0 || i >= 2) {
            ClockEventController$$ExternalSyntheticOutline0.m(i, "IllegalArgument : ", "KeyguardShortcutManager");
            return null;
        }
        ShortcutData[] shortcutDataArr = this.shortcutsData;
        return TextUtils.isEmpty(shortcutDataArr[i].appLabel) ? "Shortcut" : shortcutDataArr[i].appLabel;
    }

    public final Drawable getShortcutDrawable(int i) {
        if (i >= 0 && i < 2) {
            return this.shortcutsData[i].drawable;
        }
        ClockEventController$$ExternalSyntheticOutline0.m(i, "IllegalArgument : ", "KeyguardShortcutManager");
        return null;
    }

    public final int getShortcutIconSizeValue(boolean z) {
        int i = this.context.getResources().getDisplayMetrics().widthPixels;
        int i2 = this.context.getResources().getDisplayMetrics().heightPixels;
        if (DeviceState.isLargeScreenTablet(this.context)) {
            this.shortcutIconSize = (int) (64 * this.context.getResources().getDisplayMetrics().density);
        } else if (DeviceState.isTablet() || DeviceState.isMultiFoldMain()) {
            this.shortcutIconSize = (int) (54 * this.context.getResources().getDisplayMetrics().density);
        } else if (LsRune.LOCKUI_SUB_DISPLAY_LOCK && ((DisplayLifecycle) Dependency.sDependency.getDependencyInner(DisplayLifecycle.class)).mIsFolderOpened) {
            this.shortcutIconSize = (int) ((z ? 50 : 56) * this.context.getResources().getDisplayMetrics().density);
        } else {
            this.shortcutIconSize = (int) (Math.min(i, i2) * (z ? 0.111d : 0.139d));
        }
        return this.shortcutIconSize;
    }

    /* JADX WARN: Removed duplicated region for block: B:40:0x00ba  */
    /* JADX WARN: Removed duplicated region for block: B:43:0x00c1  */
    /* JADX WARN: Removed duplicated region for block: B:54:0x00ea  */
    /* JADX WARN: Removed duplicated region for block: B:55:0x00ec  */
    /* JADX WARN: Removed duplicated region for block: B:56:0x00f0  */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    public final int getShortcutSideMargin() {
        float f;
        float f2;
        boolean z;
        float fMin;
        float f3;
        float dimensionPixelSize;
        boolean z2;
        float f4;
        int i;
        if (DeviceState.isLargeScreenTablet(this.context)) {
            f = 60;
            f2 = this.context.getResources().getDisplayMetrics().density;
        } else {
            if (!DeviceState.isTablet() && !DeviceState.isMultiFoldMain() && (!(z = LsRune.LOCKUI_SUB_DISPLAY_LOCK) || !((DisplayLifecycle) Dependency.sDependency.getDependencyInner(DisplayLifecycle.class)).mIsFolderOpened)) {
                int i2 = this.context.getResources().getDisplayMetrics().widthPixels;
                int i3 = this.context.getResources().getDisplayMetrics().heightPixels;
                int i4 = this.context.getResources().getConfiguration().orientation;
                int shortcutIconSizeValue = i2 - (getShortcutIconSizeValue(true) * 2);
                boolean z3 = i4 == 2;
                if (DeviceState.isTablet()) {
                    fMin = z3 ? 0.263f : 0.42f;
                } else {
                    if (DeviceState.isMultiFoldMain()) {
                        dimensionPixelSize = this.context.getResources().getDimensionPixelSize(R.dimen.now_bar_cardview_normal_width_multifold);
                        int i5 = shortcutIconSizeValue - ((int) dimensionPixelSize);
                        z2 = i4 == 2;
                        if (DeviceState.isTablet() || DeviceState.isMultiFoldMain()) {
                            f4 = !z2 ? 0.067f : 0.107f;
                        } else {
                            if (z && ((DisplayLifecycle) Dependency.sDependency.getDependencyInner(DisplayLifecycle.class)).mIsFolderOpened) {
                                i = (int) (i2 * 0.165f);
                                return (i5 - (i * 2)) / 2;
                            }
                            f4 = z2 ? 0.114f : 0.028f;
                        }
                        i = (int) (f4 * i2);
                        return (i5 - (i * 2)) / 2;
                    }
                    if (z && ((DisplayLifecycle) Dependency.sDependency.getDependencyInner(DisplayLifecycle.class)).mIsFolderOpened) {
                        fMin = Math.min(i3, i2);
                        f3 = 0.374f;
                        dimensionPixelSize = fMin * f3;
                        int i52 = shortcutIconSizeValue - ((int) dimensionPixelSize);
                        if (i4 == 2) {
                        }
                        if (DeviceState.isTablet()) {
                            if (!z2) {
                            }
                            i = (int) (f4 * i2);
                        }
                        return (i52 - (i * 2)) / 2;
                    }
                    fMin = z3 ? 0.4f : 0.589f;
                }
                f3 = i2;
                dimensionPixelSize = fMin * f3;
                int i522 = shortcutIconSizeValue - ((int) dimensionPixelSize);
                if (i4 == 2) {
                }
                if (DeviceState.isTablet()) {
                }
                return (i522 - (i * 2)) / 2;
            }
            f = 40;
            f2 = this.context.getResources().getDisplayMetrics().density;
        }
        return (int) (f * f2);
    }

    public final boolean getSuspended(String str) {
        try {
            return this.packageManager.isPackageSuspended(str);
        } catch (PackageManager.NameNotFoundException unused) {
            Log.e("KeyguardShortcutManager", "getSuspended() - Not found package name = " + str);
            return false;
        }
    }

    public final boolean hasShortcut(int i) {
        if (2 <= i || !this.settingsHelper.isShortcutMasterEnabled()) {
            return false;
        }
        ShortcutData[] shortcutDataArr = this.shortcutsData;
        if (!shortcutDataArr[i].enabled) {
            return false;
        }
        if (isTaskType(i) && shortcutDataArr[i].taskName != null) {
            return true;
        }
        ComponentName componentName = shortcutDataArr[i].componentName;
        return componentName != null && isLockTaskPermitted(componentName.getPackageName());
    }

    public final boolean isDarkPanel(int i) {
        return isSamsungCameraPackage(this.shortcutsData[i].componentName) || this.settingsHelper.isDarkTheme();
    }

    public final boolean isLockTaskPermitted(String str) {
        if (this.isLockTaskMode) {
            return ((DevicePolicyManager) this.context.getSystemService("device_policy")).isLockTaskPermitted(str);
        }
        return true;
    }

    public final boolean isMonotoneIcon(int i) {
        if (i >= 0 && i < 2) {
            return this.shortcutsData[i].isMonotoneIcon;
        }
        ClockEventController$$ExternalSyntheticOutline0.m(i, "IllegalArgument : ", "KeyguardShortcutManager");
        return false;
    }

    public final boolean isNoUnlockNeeded(int i) {
        if (i >= 0 && i < 2) {
            return this.shortcutsData[i].noUnlockNeeded && !isUnlockWaitNeeded(i);
        }
        ClockEventController$$ExternalSyntheticOutline0.m(i, "isNoUnlockNeeded wrong param: ", "KeyguardShortcutManager");
        return false;
    }

    public final boolean isSecure() {
        KeyguardStateController keyguardStateController = this.keyguardStateController;
        return ((KeyguardStateControllerImpl) keyguardStateController).mSecure && !((KeyguardStateControllerImpl) keyguardStateController).mCanDismissLockScreen;
    }

    public final boolean isShortcutForLiveIcon(int i) {
        ComponentName componentName = this.shortcutsData[i].componentName;
        if (componentName != null) {
            String packageName = componentName.getPackageName();
            for (String str : SAMSUNG_LIVE_ICON_PACKAGES) {
                str.getClass();
                if (str.equals(packageName)) {
                    return true;
                }
            }
        }
        return false;
    }

    public final boolean isShortcutForPhone(int i) {
        ComponentName componentName = this.shortcutsData[i].componentName;
        return componentName != null && "com.samsung.android.dialer".equals(componentName.getPackageName()) && "com.samsung.android.dialer.DialtactsActivity".equals(componentName.getClassName());
    }

    /* JADX WARN: Removed duplicated region for block: B:18:0x003b  */
    /* JADX WARN: Removed duplicated region for block: B:23:0x004b  */
    /* JADX WARN: Removed duplicated region for block: B:32:0x006e A[RETURN] */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    public final boolean isShortcutPermission(String str) throws NoSuchAlgorithmException {
        boolean zIsAllowNonPlatformKeyApp;
        if (this.packageManager.checkPermission("com.samsung.keyguard.SHORTCUT_PERMISSION", str) != 0) {
            int iHashCode = str.hashCode();
            if (iHashCode != -662003450) {
                if (iHashCode != 988032088) {
                    if (iHashCode == 2094270320 && str.equals("com.snapchat.android")) {
                        zIsAllowNonPlatformKeyApp = isAllowNonPlatformKeyApp(this.context, str, "9c1c8918e17cc686d3274f41cd04154b4cbe6a5272700de3f4f30c2c62ae2ad4");
                    }
                    if (zIsAllowNonPlatformKeyApp) {
                        return false;
                    }
                } else {
                    if (str.equals("com.sec.android.app.popupcalculator")) {
                        zIsAllowNonPlatformKeyApp = true;
                    }
                    if (zIsAllowNonPlatformKeyApp) {
                    }
                }
                zIsAllowNonPlatformKeyApp = !str.startsWith("com.snapchat.android") ? isAllowNonPlatformKeyApp(this.context, str, "2f4eaa0c67e2a670935ca79164f3ba4b426988b6997a97bb31152cc317dc648a") : false;
                if (zIsAllowNonPlatformKeyApp) {
                }
            } else {
                if (str.equals("com.instagram.android")) {
                    if (isAllowNonPlatformKeyApp(this.context, str, "a044dbdb712ab81e76949f5d76ada4dd7035643b462cb7ea2b75ecae637c2da3") || isAllowNonPlatformKeyApp(this.context, str, "9e92121f90ad13d9f1085b06ea9e7c72ca6d5b603cdfd6adaff7b3071792d71f")) {
                    }
                    if (zIsAllowNonPlatformKeyApp) {
                    }
                } else {
                    if (!str.startsWith("com.snapchat.android")) {
                    }
                    if (zIsAllowNonPlatformKeyApp) {
                    }
                }
                if (zIsAllowNonPlatformKeyApp) {
                }
            }
        }
        return true;
    }

    public final boolean isSupportBlur() {
        return LsRune.LOCKUI_SHORTCUT_BLUR_BG && !this.isReduceTransparencyEnabled;
    }

    public final boolean isTaskType(int i) {
        if (i >= 0 && i < 2) {
            return this.shortcutsData[i].shortcutProperty == 1;
        }
        ClockEventController$$ExternalSyntheticOutline0.m(i, "isTaskType wrong param: ", "KeyguardShortcutManager");
        return false;
    }

    public final boolean isTaskTypeEnabled(int i) {
        if (i >= 0 && i < 2) {
            KeyguardQuickAffordanceConfig[] keyguardQuickAffordanceConfigArr = this.keyguardBottomAreaShortcutTask;
            if (!Intrinsics.areEqual(keyguardQuickAffordanceConfigArr[i], EMPTY_CONFIG)) {
                return keyguardQuickAffordanceConfigArr[i].isTaskEnabled();
            }
        }
        ClockEventController$$ExternalSyntheticOutline0.m(i, "IllegalArgument : ", "KeyguardShortcutManager");
        return false;
    }

    public final boolean isUnlockWaitNeeded(int i) {
        if (i >= 0 && i < 2) {
            return this.shortcutsData[i].isUnlockWaitNeeded;
        }
        ClockEventController$$ExternalSyntheticOutline0.m(i, "IllegalArgument : ", "KeyguardShortcutManager");
        return false;
    }

    @Override // com.android.systemui.util.SettingsHelper.OnChangedCallback
    public final void onChanged(Uri uri) {
        if (Intrinsics.areEqual(uri, Settings.System.getUriFor(SettingsHelper.INDEX_AWESOME_SHORTCUT_APP_LIST)) || Intrinsics.areEqual(uri, Settings.System.getUriFor(SettingsHelper.INDEX_CURRENT_SEC_APPICON_THEME_PACKAGE))) {
            updateShortcuts();
            return;
        }
        if (!Intrinsics.areEqual(uri, Settings.System.getUriFor(SettingsHelper.INDEX_SET_SHORTCUTS_MODE))) {
            if (Intrinsics.areEqual(uri, Settings.System.getUriFor(SettingsHelper.INDEX_ACCESSIBILITY_REDUCE_TRANSPARENCY))) {
                this.isReduceTransparencyEnabled = this.settingsHelper.isReduceTransparencyEnabled();
                updateShortcutIcons();
                return;
            }
            return;
        }
        boolean z = this.isShortcutVisibleForMDM;
        boolean zIsShortcutsVisibleForMDM = this.settingsHelper.isShortcutsVisibleForMDM();
        this.isShortcutVisibleForMDM = zIsShortcutsVisibleForMDM;
        KeyguardKnoxGuardViewController$$ExternalSyntheticOutline0.m("onSystemSettingsChanged oldShortcutVisibleForMDM = ", ", isShortcutVisibleForMDM = ", "KeyguardShortcutManager", z, zIsShortcutsVisibleForMDM);
        if (z != this.isShortcutVisibleForMDM) {
            updateShortcuts();
        }
    }

    @Override // com.android.keyguard.KeyguardUpdateMonitorCallback
    public final void onKeyguardVisibilityChanged(boolean z) {
        int i = 0;
        if (z && this.keyguardUpdateMonitor.mDeviceInteractive) {
            while (i < 2) {
                this.keyguardBottomAreaShortcutTask[i].addListener();
                i++;
            }
        } else {
            while (i < 2) {
                this.keyguardBottomAreaShortcutTask[i].removeListener();
                i++;
            }
        }
    }

    @Override // com.android.keyguard.KeyguardUpdateMonitorCallback
    public final void onSimStateChanged(int i, int i2, int i3) {
        boolean z = this.isPermDisabled;
        boolean z2 = CscRune.SECURITY_SIM_PERM_DISABLED && this.keyguardUpdateMonitor.isIccBlockedPermanently();
        this.isPermDisabled = z2;
        if (z != z2) {
            updateShortcuts();
        }
    }

    @Override // com.android.keyguard.KeyguardUpdateMonitorCallback
    public final void onStartedGoingToSleep(int i) {
        for (int i2 = 0; i2 < 2; i2++) {
            this.keyguardBottomAreaShortcutTask[i2].removeListener();
        }
    }

    @Override // com.android.keyguard.KeyguardUpdateMonitorCallback
    public final void onStartedWakingUp() {
        if (isShortcutForLiveIcon(0)) {
            updateShortcutIcon(0);
        }
        if (isShortcutForLiveIcon(1)) {
            updateShortcutIcon(1);
        }
        if (((KeyguardStateControllerImpl) this.keyguardStateController).mShowing) {
            for (int i = 0; i < 2; i++) {
                this.keyguardBottomAreaShortcutTask[i].addListener();
            }
        }
    }

    @Override // com.android.keyguard.KeyguardUpdateMonitorCallback
    public final void onUserSwitchComplete(int i) {
        updateShortcuts();
    }

    @Override // com.android.keyguard.KeyguardUpdateMonitorCallback
    public final void onUserUnlocked() {
        updateShortcuts();
    }

    public final Bitmap scaleIcon(Bitmap bitmap, boolean z) {
        float shortcutIconSizeValue = getShortcutIconSizeValue(false) * 0.85f;
        if (z) {
            shortcutIconSizeValue -= this.context.getResources().getDisplayMetrics().density * 15;
        }
        if (shortcutIconSizeValue > 0.0f) {
            int i = (int) shortcutIconSizeValue;
            bitmap = Bitmap.createScaledBitmap(bitmap, i, i, true);
        }
        bitmap.getClass();
        bitmap.setDensity(this.context.getResources().getDisplayMetrics().densityDpi);
        return bitmap;
    }

    public final void sendUpdateShortcutViewToCallback(int i) {
        Iterator it = this.shortcutCallbacks.iterator();
        while (it.hasNext()) {
            WeakReference weakReference = (WeakReference) it.next();
            if (((KeyguardSecBottomAreaViewController$shortcutManagerCallback$1) weakReference.get()) != null) {
                Object obj = weakReference.get();
                obj.getClass();
                ((KeyguardSecBottomAreaViewController$shortcutManagerCallback$1) obj).updateShortcutView(i);
            }
        }
    }

    public final void updateShortcutIcon(final int i) {
        if (isTaskType(i)) {
            this.executor.execute(new Runnable() { // from class: com.android.systemui.statusbar.KeyguardShortcutManager.updateShortcutIcon.1
                @Override // java.lang.Runnable
                public final void run() {
                    final KeyguardShortcutManager keyguardShortcutManager = this;
                    final int i2 = i;
                    if (new IntPredicate() { // from class: com.android.systemui.statusbar.KeyguardShortcutManager.updateShortcutIcon.1.1
                        @Override // java.util.function.IntPredicate
                        public final boolean test(int i3) {
                            if (Intrinsics.areEqual(keyguardShortcutManager.keyguardBottomAreaShortcutTask[i3], KeyguardShortcutManager.EMPTY_CONFIG)) {
                                NotificationManagerCompat$SideChannelManager$$ExternalSyntheticOutline0.m(i3, "updateShortcutsIcon : ", " is invalid task name", "KeyguardShortcutManager");
                                return false;
                            }
                            KeyguardShortcutManager keyguardShortcutManager2 = keyguardShortcutManager;
                            keyguardShortcutManager2.shortcutsData[i3].isMonotoneIcon = true;
                            boolean zIsTaskTypeEnabled = keyguardShortcutManager2.isTaskTypeEnabled(i2);
                            KeyguardShortcutManager keyguardShortcutManager3 = keyguardShortcutManager;
                            ShortcutData[] shortcutDataArr = keyguardShortcutManager3.shortcutsData;
                            int i4 = i2;
                            shortcutDataArr[i4].isIconPaddingNeeded = keyguardShortcutManager3.keyguardBottomAreaShortcutTask[i4].isIconPaddingRequired();
                            KeyguardShortcutManager keyguardShortcutManager4 = keyguardShortcutManager;
                            keyguardShortcutManager4.shortcutsData[i3].drawable = keyguardShortcutManager4.convertTaskDrawable(keyguardShortcutManager4.keyguardBottomAreaShortcutTask[i2].getDrawable(), WallpaperUtils.isWhiteKeyguardWallpaper("navibar"), zIsTaskTypeEnabled, false, keyguardShortcutManager.shortcutsData[i2].isIconPaddingNeeded);
                            KeyguardShortcutManager keyguardShortcutManager5 = keyguardShortcutManager;
                            ShortcutData[] shortcutDataArr2 = keyguardShortcutManager5.shortcutsData;
                            int i5 = i2;
                            shortcutDataArr2[i5].panelDrawable = keyguardShortcutManager5.keyguardBottomAreaShortcutTask[i5].getDrawable();
                            KeyguardShortcutManager keyguardShortcutManager6 = keyguardShortcutManager;
                            ShortcutData[] shortcutDataArr3 = keyguardShortcutManager6.shortcutsData;
                            int i6 = i2;
                            shortcutDataArr3[i6].panelTransitDrawable = keyguardShortcutManager6.keyguardBottomAreaShortcutTask[i6].getPanelIconTransitionDrawable();
                            KeyguardShortcutManager keyguardShortcutManager7 = keyguardShortcutManager;
                            ShortcutData[] shortcutDataArr4 = keyguardShortcutManager7.shortcutsData;
                            int i7 = i2;
                            shortcutDataArr4[i7].isUnlockWaitNeeded = keyguardShortcutManager7.keyguardBottomAreaShortcutTask[i7].isUnlockWaitRequired();
                            return true;
                        }
                    }.test(i)) {
                        final KeyguardShortcutManager keyguardShortcutManager2 = this;
                        Handler handler = keyguardShortcutManager2.handler;
                        final int i3 = i;
                        handler.post(new Runnable() { // from class: com.android.systemui.statusbar.KeyguardShortcutManager.updateShortcutIcon.1.2
                            @Override // java.lang.Runnable
                            public final void run() {
                                KeyguardShortcutManager.access$sendUpdateIconOnlyToCallback(keyguardShortcutManager2, i3);
                            }
                        });
                    }
                }
            });
        } else if (this.shortcutsData[i].componentName != null) {
            this.executor.execute(new Runnable() { // from class: com.android.systemui.statusbar.KeyguardShortcutManager.updateShortcutIcon.2
                @Override // java.lang.Runnable
                public final void run() {
                    final KeyguardShortcutManager keyguardShortcutManager = this;
                    if (new IntPredicate() { // from class: com.android.systemui.statusbar.KeyguardShortcutManager.updateShortcutIcon.2.1
                        @Override // java.util.function.IntPredicate
                        public final boolean test(int i2) {
                            try {
                                Intent intent = new Intent("android.intent.action.MAIN");
                                intent.setComponent(keyguardShortcutManager.shortcutsData[i2].componentName);
                                KeyguardShortcutManager keyguardShortcutManager2 = keyguardShortcutManager;
                                ResolveInfo resolveInfoResolveActivityAsUser = keyguardShortcutManager2.packageManager.resolveActivityAsUser(intent, 129, keyguardShortcutManager2.selectedUserInteractor.getSelectedUserId());
                                ActivityInfo activityInfo = resolveInfoResolveActivityAsUser != null ? resolveInfoResolveActivityAsUser.activityInfo : null;
                                if (activityInfo != null) {
                                    KeyguardShortcutManager keyguardShortcutManager3 = keyguardShortcutManager;
                                    keyguardShortcutManager3.shortcutsData[i2].isMonotoneIcon = KeyguardShortcutManager.access$isMonotoneIconRequired(keyguardShortcutManager3, i2);
                                    KeyguardShortcutManager keyguardShortcutManager4 = keyguardShortcutManager;
                                    keyguardShortcutManager4.shortcutsData[i2].drawable = KeyguardShortcutManager.access$getShortcutIcon(keyguardShortcutManager4, activityInfo, false, i2);
                                    KeyguardShortcutManager keyguardShortcutManager5 = keyguardShortcutManager;
                                    keyguardShortcutManager5.shortcutsData[i2].panelDrawable = KeyguardShortcutManager.access$getShortcutIcon(keyguardShortcutManager5, activityInfo, true, i2);
                                }
                                return true;
                            } catch (Exception e) {
                                Log.e("KeyguardShortcutManager", "NameNotFoundException while updating icon : " + e.getMessage());
                                return false;
                            }
                        }
                    }.test(i)) {
                        final KeyguardShortcutManager keyguardShortcutManager2 = this;
                        Handler handler = keyguardShortcutManager2.handler;
                        final int i2 = i;
                        handler.post(new Runnable() { // from class: com.android.systemui.statusbar.KeyguardShortcutManager.updateShortcutIcon.2.2
                            @Override // java.lang.Runnable
                            public final void run() {
                                KeyguardShortcutManager.access$sendUpdateIconOnlyToCallback(keyguardShortcutManager2, i2);
                            }
                        });
                    }
                }
            });
        }
    }

    public final void updateShortcutIcons() {
        for (int i = 0; i < 2; i++) {
            updateShortcutIcon(i);
        }
    }

    public final void updateShortcuts() {
        Handler handler = this.handler;
        KeyguardShortcutManager$updateShortcutsRunnable$1 keyguardShortcutManager$updateShortcutsRunnable$1 = this.updateShortcutsRunnable;
        handler.removeCallbacks(keyguardShortcutManager$updateShortcutsRunnable$1);
        handler.post(keyguardShortcutManager$updateShortcutsRunnable$1);
    }

    public static /* synthetic */ void getIntentReceiver$annotations() {
    }
}
