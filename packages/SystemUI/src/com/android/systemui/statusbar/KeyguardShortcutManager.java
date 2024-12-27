package com.android.systemui.statusbar;

import android.app.admin.DevicePolicyManager;
import android.content.BroadcastReceiver;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.pm.ActivityInfo;
import android.content.pm.PackageManager;
import android.content.pm.ResolveInfo;
import android.content.pm.Signature;
import android.content.pm.SigningInfo;
import android.graphics.Bitmap;
import android.graphics.BlendMode;
import android.graphics.BlendModeColorFilter;
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
import android.text.TextUtils;
import android.util.Log;
import android.util.Slog;
import android.view.View;
import androidx.core.app.NotificationManagerCompat$SideChannelManager$$ExternalSyntheticOutline0;
import com.android.keyguard.ActiveUnlockConfig$$ExternalSyntheticOutline0;
import com.android.keyguard.ClockEventController$zenModeCallback$1$$ExternalSyntheticOutline0;
import com.android.keyguard.EmergencyButtonController$$ExternalSyntheticOutline0;
import com.android.keyguard.KeyguardSecUpdateMonitorImpl$$ExternalSyntheticOutline0;
import com.android.keyguard.KeyguardUpdateMonitor;
import com.android.keyguard.KeyguardUpdateMonitorCallback;
import com.android.systemui.CscRune;
import com.android.systemui.Dependency;
import com.android.systemui.Dumpable;
import com.android.systemui.LsRune;
import com.android.systemui.R;
import com.android.systemui.animation.Expandable;
import com.android.systemui.biometrics.UdfpsKeyguardViewControllerLegacy$$ExternalSyntheticOutline0;
import com.android.systemui.broadcast.BroadcastDispatcher;
import com.android.systemui.common.shared.model.ContentDescription;
import com.android.systemui.common.shared.model.Icon;
import com.android.systemui.dump.DumpManager;
import com.android.systemui.keyguard.DisplayLifecycle;
import com.android.systemui.keyguard.data.quickaffordance.KeyguardQuickAffordanceConfig;
import com.android.systemui.keyguard.data.quickaffordance.KeyguardSecTilesQuickAffordanceConfig;
import com.android.systemui.keyguard.shared.quickaffordance.KeyguardQuickAffordancePosition;
import com.android.systemui.qs.QSTileHost;
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
import com.android.systemui.util.DesktopManager;
import com.android.systemui.util.DeviceState;
import com.android.systemui.util.SettingsHelper;
import com.android.systemui.util.ViewController;
import com.android.systemui.wallpaper.WallpaperUtils;
import java.io.PrintWriter;
import java.lang.ref.WeakReference;
import java.nio.charset.Charset;
import java.security.MessageDigest;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Set;
import java.util.concurrent.Executor;
import java.util.function.IntPredicate;
import kotlin.Unit;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.Intrinsics;
import kotlin.text.CharsKt__CharJVMKt;
import kotlinx.coroutines.flow.Flow;
import kotlinx.coroutines.flow.FlowKt__BuildersKt$flowOf$$inlined$unsafeFlow$2;

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
    public final KeyguardShortcutDialerUpdateManager keyguardShortcutDialerUpdateManager;
    public final KeyguardStateController keyguardStateController;
    public final KeyguardUpdateMonitor keyguardUpdateMonitor;
    public final PackageManager packageManager;
    public final SelectedUserInteractor selectedUserInteractor;
    private final SettingsHelper settingsHelper;
    public final ArrayList shortcutCallbacks;
    public final ShortcutData[] shortcutsData;
    public final StringBuilder stringBuilder;
    public final Set taskConfigs;
    public final HashMap themeShortcutHashMap;
    public final KeyguardShortcutManager$updateShortcutsRunnable$1 updateShortcutsRunnable;
    public final UserSwitcherController userSwitcherController;
    public final UserTracker userTracker;
    public static final Companion Companion = new Companion(null);
    public static final String[] SAMSUNG_LIVE_ICON_PACKAGES = {"com.samsung.android.calendar", "com.android.calendar", "com.sec.android.app.clockpackage"};
    public static final Intent SECURE_CAMERA_INTENT = new Intent("android.intent.action.MAIN").addCategory("android.intent.category.LAUNCHER").setClassName("com.sec.android.app.camera", "com.sec.android.app.camera.Camera");
    public static final Intent INSECURE_CAMERA_INTENT = new Intent("android.intent.action.MAIN").addCategory("android.intent.category.LAUNCHER").setClassName("com.sec.android.app.camera", "com.sec.android.app.camera.Camera");
    public static final Intent SAMSUNG_EXPERT_RAW_CAMERA_INTENT = new Intent("android.intent.action.MAIN").addCategory("android.intent.category.LAUNCHER").setClassName("com.samsung.android.app.galaxyraw", "com.samsung.android.app.galaxyraw.GalaxyRaw");
    public static final Intent PHONE_INTENT = new Intent("android.intent.action.DIAL").setClassName("com.samsung.android.dialer", "com.samsung.android.dialer.DialtactsActivity");
    public static final KeyguardShortcutManager$Companion$EMPTY_CONFIG$1 EMPTY_CONFIG = new KeyguardQuickAffordanceConfig() { // from class: com.android.systemui.statusbar.KeyguardShortcutManager$Companion$EMPTY_CONFIG$1
        public final String key = "";
        public final int pickerIconResourceId = R.drawable.bg_bk;
        public final FlowKt__BuildersKt$flowOf$$inlined$unsafeFlow$2 lockScreenState = new FlowKt__BuildersKt$flowOf$$inlined$unsafeFlow$2(KeyguardQuickAffordanceConfig.LockScreenState.Hidden.INSTANCE);

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
            return KeyguardQuickAffordanceConfig.OnTriggeredResult.Handled.INSTANCE;
        }

        @Override // com.android.systemui.keyguard.data.quickaffordance.KeyguardQuickAffordanceConfig
        public final String pickerName() {
            return "";
        }
    };

    public final class Companion {
        private Companion() {
        }

        public /* synthetic */ Companion(DefaultConstructorMarker defaultConstructorMarker) {
            this();
        }
    }

    public interface ShortcutCallback {
    }

    public final class ShortcutData {
        public String appLabel;
        public ComponentName componentName;
        public Drawable drawable;
        public boolean enabled;
        public boolean isMonotoneIcon;
        public boolean launchInsecureMain;
        public boolean noUnlockNeeded;
        public Drawable panelDrawable;
        public Drawable panelTransitDrawable;
        public int shortcutProperty;
        public String taskName;
    }

    /* JADX WARN: Type inference failed for: r2v2, types: [com.android.systemui.statusbar.KeyguardShortcutManager$intentReceiver$1] */
    /* JADX WARN: Type inference failed for: r4v17, types: [com.android.systemui.statusbar.KeyguardShortcutManager$updateShortcutsRunnable$1] */
    public KeyguardShortcutManager(Context context, BroadcastDispatcher broadcastDispatcher, Executor executor, Handler handler, UserTracker userTracker, SelectedUserInteractor selectedUserInteractor, KeyguardUpdateMonitor keyguardUpdateMonitor, SettingsHelper settingsHelper, PackageManager packageManager, KeyguardStateController keyguardStateController, Set<KeyguardQuickAffordanceConfig> set, DumpManager dumpManager, QSTileHost qSTileHost, UserSwitcherController userSwitcherController) {
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
        KeyguardShortcutManager$Companion$EMPTY_CONFIG$1 keyguardShortcutManager$Companion$EMPTY_CONFIG$1 = EMPTY_CONFIG;
        this.keyguardBottomAreaShortcutTask = new KeyguardQuickAffordanceConfig[]{keyguardShortcutManager$Companion$EMPTY_CONFIG$1, keyguardShortcutManager$Companion$EMPTY_CONFIG$1};
        this.keyguardShortcutDialerUpdateManager = new KeyguardShortcutDialerUpdateManager(context, settingsHelper);
        this.shortcutsData = new ShortcutData[]{new ShortcutData(), new ShortcutData()};
        this.stringBuilder = new StringBuilder();
        this.shortcutCallbacks = new ArrayList();
        this.updateShortcutsRunnable = new Runnable() { // from class: com.android.systemui.statusbar.KeyguardShortcutManager$updateShortcutsRunnable$1
            @Override // java.lang.Runnable
            public final void run() {
                KeyguardShortcutManager.access$handleUpdateShortcuts(KeyguardShortcutManager.this);
            }
        };
        this.themeShortcutHashMap = new HashMap();
        this.isReduceTransparencyEnabled = settingsHelper.isReduceTransparencyEnabled();
        this.intentReceiver = new BroadcastReceiver() { // from class: com.android.systemui.statusbar.KeyguardShortcutManager$intentReceiver$1
            /* JADX WARN: Failed to restore switch over string. Please report as a decompilation issue */
            /* JADX WARN: Removed duplicated region for block: B:15:0x0134  */
            /* JADX WARN: Removed duplicated region for block: B:37:0x017a  */
            @Override // android.content.BroadcastReceiver
            /*
                Code decompiled incorrectly, please refer to instructions dump.
                To view partially-correct code enable 'Show inconsistent code' option in preferences
            */
            public final void onReceive(android.content.Context r9, android.content.Intent r10) {
                /*
                    Method dump skipped, instructions count: 540
                    To view this dump change 'Code comments level' option to 'DEBUG'
                */
                throw new UnsupportedOperationException("Method not decompiled: com.android.systemui.statusbar.KeyguardShortcutManager$intentReceiver$1.onReceive(android.content.Context, android.content.Intent):void");
            }
        };
        if (!Intrinsics.areEqual(Process.myUserHandle(), UserHandle.SYSTEM)) {
            Log.d("KeyguardShortcutManager", "dont initialize for other than system user");
            return;
        }
        this.keyguardBottomAreaShortcutTask = new KeyguardQuickAffordanceConfig[]{new KeyguardSecTilesQuickAffordanceConfig(context, qSTileHost), new KeyguardSecTilesQuickAffordanceConfig(context, qSTileHost)};
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
        this.context.getResources().getDimensionPixelSize(R.dimen.keyguard_affordance_height);
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

    /* JADX WARN: Code restructure failed: missing block: B:58:0x00eb, code lost:
    
        if (r5 != null) goto L41;
     */
    /* JADX WARN: Removed duplicated region for block: B:82:0x006a  */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public static final android.graphics.drawable.Drawable access$getShortcutIcon(com.android.systemui.statusbar.KeyguardShortcutManager r10, android.content.pm.ActivityInfo r11, boolean r12, int r13) {
        /*
            Method dump skipped, instructions count: 572
            To view this dump change 'Code comments level' option to 'DEBUG'
        */
        throw new UnsupportedOperationException("Method not decompiled: com.android.systemui.statusbar.KeyguardShortcutManager.access$getShortcutIcon(com.android.systemui.statusbar.KeyguardShortcutManager, android.content.pm.ActivityInfo, boolean, int):android.graphics.drawable.Drawable");
    }

    /* JADX WARN: Removed duplicated region for block: B:32:0x0169  */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public static final void access$handleUpdateShortcuts(final com.android.systemui.statusbar.KeyguardShortcutManager r13) {
        /*
            Method dump skipped, instructions count: 408
            To view this dump change 'Code comments level' option to 'DEBUG'
        */
        throw new UnsupportedOperationException("Method not decompiled: com.android.systemui.statusbar.KeyguardShortcutManager.access$handleUpdateShortcuts(com.android.systemui.statusbar.KeyguardShortcutManager):void");
    }

    public static final boolean access$isMonotoneIconRequired(KeyguardShortcutManager keyguardShortcutManager, int i) {
        Drawable drawable;
        if (keyguardShortcutManager.settingsHelper.getActiveIconPackage() != null) {
            return false;
        }
        ResolveInfo resolveActivityAsUser = keyguardShortcutManager.packageManager.resolveActivityAsUser(Intent.makeMainActivity(keyguardShortcutManager.shortcutsData[i].componentName), 129, ((UserTrackerImpl) keyguardShortcutManager.userTracker).getUserId());
        ActivityInfo activityInfo = resolveActivityAsUser != null ? resolveActivityAsUser.activityInfo : null;
        if (activityInfo == null) {
            Slog.d("KeyguardShortcutManager", "updateShortcut : " + i + " activityInfo is null, resolveInfo is : " + resolveActivityAsUser + ",  return FALSE");
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
            if ((bundle != null ? bundle.getString("com.sec.android.app.launcher.icon_theme", null) : null) == null) {
                return false;
            }
            DrawableWrapper drawableWrapper = samsungAppIconDrawable instanceof DrawableWrapper ? (DrawableWrapper) samsungAppIconDrawable : null;
            if (drawableWrapper != null && (drawable = drawableWrapper.getDrawable()) != null) {
                samsungAppIconDrawable = drawable;
            }
            AdaptiveIconDrawable adaptiveIconDrawable = samsungAppIconDrawable instanceof AdaptiveIconDrawable ? (AdaptiveIconDrawable) samsungAppIconDrawable : null;
            if ((adaptiveIconDrawable != null ? adaptiveIconDrawable.getMonochrome() : null) == null) {
                return false;
            }
        }
        return true;
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
        View view;
        Iterator it = keyguardShortcutManager.shortcutCallbacks.iterator();
        while (it.hasNext()) {
            WeakReference weakReference = (WeakReference) it.next();
            if (((ShortcutCallback) weakReference.get()) != null) {
                Object obj = weakReference.get();
                Intrinsics.checkNotNull(obj);
                final KeyguardSecBottomAreaViewController keyguardSecBottomAreaViewController = ((KeyguardSecBottomAreaViewController$shortcutManagerCallback$1) ((ShortcutCallback) obj)).this$0;
                view = ((ViewController) keyguardSecBottomAreaViewController).mView;
                ((KeyguardSecBottomAreaView) view).post(new Runnable() { // from class: com.android.systemui.statusbar.phone.KeyguardSecBottomAreaViewController$shortcutManagerCallback$1$updateShortcutIconOnly$1
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

    public static boolean isARShortcutIcon(String str) {
        return "com.samsung.android.aremoji".equals(str) || "com.sec.android.mimage.avatarstickers".equals(str);
    }

    public static boolean isAllowNonPlatformKeyApp(Context context, String str, String str2) {
        Signature[] signingCertificateHistory;
        ArrayList arrayList = new ArrayList();
        arrayList.add(str2);
        Unit unit = Unit.INSTANCE;
        ArrayList arrayList2 = new ArrayList();
        try {
            MessageDigest messageDigest = MessageDigest.getInstance("SHA-256");
            SigningInfo signingInfo = context.getPackageManager().getPackageInfo(str, 134217728).signingInfo;
            Intrinsics.checkNotNull(signingInfo);
            if (signingInfo.hasMultipleSigners()) {
                signingCertificateHistory = signingInfo.getApkContentsSigners();
                Intrinsics.checkNotNull(signingCertificateHistory);
            } else {
                signingCertificateHistory = signingInfo.getSigningCertificateHistory();
                Intrinsics.checkNotNull(signingCertificateHistory);
            }
            for (Signature signature : signingCertificateHistory) {
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
        Iterator it = arrayList.iterator();
        while (it.hasNext()) {
            if (arrayList2.contains((String) it.next())) {
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

    public final void addListener$1() {
        for (int i = 0; i < 2; i++) {
            this.keyguardBottomAreaShortcutTask[i].addListener();
        }
    }

    /* JADX WARN: Code restructure failed: missing block: B:11:0x0012, code lost:
    
        if (r4 != false) goto L15;
     */
    /* JADX WARN: Code restructure failed: missing block: B:4:0x000a, code lost:
    
        if (r4 != false) goto L6;
     */
    /* JADX WARN: Code restructure failed: missing block: B:5:0x000c, code lost:
    
        r0 = com.android.systemui.R.color.shortcut_black_tint_task_on;
     */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public final android.graphics.drawable.Drawable convertTaskDrawable(android.graphics.drawable.Drawable r3, boolean r4, boolean r5, boolean r6) {
        /*
            r2 = this;
            if (r3 == 0) goto L53
            r0 = 2131102011(0x7f06093b, float:1.7816448E38)
            r1 = 2131102002(0x7f060932, float:1.781643E38)
            if (r6 == 0) goto Le
            if (r4 == 0) goto L20
        Lc:
            r0 = r1
            goto L20
        Le:
            boolean r6 = r2.isReduceTransparencyEnabled
            if (r6 == 0) goto L15
            if (r4 == 0) goto Lc
            goto L20
        L15:
            if (r5 == 0) goto L1d
            if (r4 != 0) goto L1d
            r0 = 2131102005(0x7f060935, float:1.7816436E38)
            goto L20
        L1d:
            r0 = 2131102006(0x7f060936, float:1.7816438E38)
        L20:
            android.graphics.drawable.BitmapDrawable r4 = new android.graphics.drawable.BitmapDrawable
            android.content.Context r5 = r2.context
            android.content.res.Resources r5 = r5.getResources()
            android.graphics.Bitmap r3 = androidx.core.graphics.drawable.DrawableKt.toBitmap$default(r3)
            android.graphics.Bitmap r3 = r2.scaleIcon(r3)
            r4.<init>(r5, r3)
            android.graphics.drawable.Drawable r3 = r4.mutate()
            r3.clearColorFilter()
            android.graphics.drawable.Drawable r3 = r4.mutate()
            r5 = 0
            r3.setTint(r5)
            android.graphics.BlendModeColorFilter r3 = new android.graphics.BlendModeColorFilter
            android.content.Context r2 = r2.context
            int r2 = r2.getColor(r0)
            android.graphics.BlendMode r5 = android.graphics.BlendMode.SRC_ATOP
            r3.<init>(r2, r5)
            r4.setColorFilter(r3)
            return r4
        L53:
            r2 = 0
            return r2
        */
        throw new UnsupportedOperationException("Method not decompiled: com.android.systemui.statusbar.KeyguardShortcutManager.convertTaskDrawable(android.graphics.drawable.Drawable, boolean, boolean, boolean):android.graphics.drawable.Drawable");
    }

    @Override // com.android.systemui.Dumpable
    public final void dump(PrintWriter printWriter, String[] strArr) {
        printWriter.println("KeyguardShortcutManager state:");
        printWriter.println("  CurrentUserId = " + this.selectedUserInteractor.getSelectedUserId(false));
        printWriter.println("  Shortcut count = 2");
        ActiveUnlockConfig$$ExternalSyntheticOutline0.m("  Master switch = ", this.settingsHelper.isShortcutMasterEnabled(), printWriter);
        ActiveUnlockConfig$$ExternalSyntheticOutline0.m("  disabled shortcut by MDM = ", !this.isShortcutVisibleForMDM, printWriter);
        ShortcutData[] shortcutDataArr = this.shortcutsData;
        int length = shortcutDataArr.length;
        for (int i = 0; i < length; i++) {
            UdfpsKeyguardViewControllerLegacy$$ExternalSyntheticOutline0.m("  Shortcut ", i, printWriter);
            ShortcutData shortcutData = shortcutDataArr[i];
            if (shortcutData == null) {
                printWriter.println("    null");
            } else {
                ActiveUnlockConfig$$ExternalSyntheticOutline0.m("    enabled = ", shortcutData.enabled, printWriter);
                printWriter.println("    component = " + shortcutData.componentName);
                UdfpsKeyguardViewControllerLegacy$$ExternalSyntheticOutline0.m(printWriter, "    label = ", shortcutData.appLabel);
                ActiveUnlockConfig$$ExternalSyntheticOutline0.m("    noUnlock = ", shortcutData.noUnlockNeeded, printWriter);
                ActiveUnlockConfig$$ExternalSyntheticOutline0.m("    monotone = ", shortcutData.isMonotoneIcon, printWriter);
                ActiveUnlockConfig$$ExternalSyntheticOutline0.m("    launchInsecureMain = ", shortcutData.launchInsecureMain, printWriter);
                ComponentName componentName = shortcutData.componentName;
                if (componentName != null) {
                    String packageName = componentName.getPackageName();
                    if (packageName == null) {
                        packageName = "";
                    }
                    ActiveUnlockConfig$$ExternalSyntheticOutline0.m("    isSuspended = ", getSuspended(packageName), printWriter);
                    ComponentName componentName2 = shortcutData.componentName;
                    ActiveUnlockConfig$$ExternalSyntheticOutline0.m("    isLockTaskPermitted = ", isLockTaskPermitted(componentName2 != null ? componentName2.getPackageName() : null), printWriter);
                }
            }
        }
    }

    public final Intent getIntent(int i) {
        if (i < 0 || i >= 2) {
            ClockEventController$zenModeCallback$1$$ExternalSyntheticOutline0.m(i, "getIntent wrong param : ", "KeyguardShortcutManager");
            return null;
        }
        ShortcutData[] shortcutDataArr = this.shortcutsData;
        if (isSamsungCameraPackage(shortcutDataArr[i].componentName)) {
            Log.d("KeyguardShortcutManager", "th = " + i + " is camera package");
            return isSecure() ? SECURE_CAMERA_INTENT : INSECURE_CAMERA_INTENT;
        }
        ComponentName componentName = shortcutDataArr[i].componentName;
        if (componentName == null ? false : "com.samsung.android.app.galaxyraw".equals(componentName.getPackageName())) {
            NotificationManagerCompat$SideChannelManager$$ExternalSyntheticOutline0.m(i, "th = ", " is expert raw camera package", "KeyguardShortcutManager");
            return SAMSUNG_EXPERT_RAW_CAMERA_INTENT;
        }
        Intent intent = new Intent("android.intent.action.MAIN");
        boolean isSecure = isSecure();
        if (isSecure || !shortcutDataArr[i].launchInsecureMain) {
            intent.setComponent(shortcutDataArr[i].componentName);
        } else {
            intent.addCategory("android.intent.category.LAUNCHER");
            ComponentName componentName2 = shortcutDataArr[i].componentName;
            intent.setPackage(componentName2 != null ? componentName2.getPackageName() : null);
            ResolveInfo resolveActivityAsUser = this.packageManager.resolveActivityAsUser(intent, 1, this.selectedUserInteractor.getSelectedUserId(false));
            if ((resolveActivityAsUser != null ? resolveActivityAsUser.activityInfo : null) != null) {
                ActivityInfo activityInfo = resolveActivityAsUser.activityInfo;
                intent.setComponent(new ComponentName(activityInfo.packageName, activityInfo.name));
            } else {
                intent.setComponent(shortcutDataArr[i].componentName);
            }
        }
        intent.putExtra("isSecure", isSecure);
        return intent.addFlags(268500992);
    }

    public final KeyguardQuickAffordanceConfig getKeyguardBottomAreaShortcutTask(int i, String str) {
        Object obj;
        if (isTaskType(i)) {
            KeyguardQuickAffordanceConfig[] keyguardQuickAffordanceConfigArr = this.keyguardBottomAreaShortcutTask;
            keyguardQuickAffordanceConfigArr[i].setSpecName(str);
            return keyguardQuickAffordanceConfigArr[i];
        }
        Iterator it = this.taskConfigs.iterator();
        while (true) {
            if (!it.hasNext()) {
                obj = null;
                break;
            }
            obj = it.next();
            if (Intrinsics.areEqual(((KeyguardQuickAffordanceConfig) obj).getKey(), str)) {
                break;
            }
        }
        KeyguardQuickAffordanceConfig keyguardQuickAffordanceConfig = (KeyguardQuickAffordanceConfig) obj;
        return keyguardQuickAffordanceConfig == null ? EMPTY_CONFIG : keyguardQuickAffordanceConfig;
    }

    public final List getQuickAffordanceConfigList() {
        ComponentName componentName;
        KeyguardQuickAffordancePosition[] values = KeyguardQuickAffordancePosition.values();
        ArrayList arrayList = new ArrayList(values.length);
        for (KeyguardQuickAffordancePosition keyguardQuickAffordancePosition : values) {
            final int ordinal = keyguardQuickAffordancePosition.ordinal();
            ShortcutData[] shortcutDataArr = this.shortcutsData;
            final ShortcutData shortcutData = shortcutDataArr[ordinal];
            arrayList.add((shortcutData == null || !shortcutData.enabled || (!isTaskType(ordinal) && ((componentName = shortcutData.componentName) == null || componentName.getPackageName() == null))) ? EMPTY_CONFIG : isTaskType(ordinal) ? getKeyguardBottomAreaShortcutTask(keyguardQuickAffordancePosition.ordinal(), shortcutDataArr[ordinal].taskName) : new KeyguardQuickAffordanceConfig() { // from class: com.android.systemui.statusbar.KeyguardShortcutManager$generateQuickAffordanceConfig$1$1
                @Override // com.android.systemui.keyguard.data.quickaffordance.KeyguardQuickAffordanceConfig
                public final String getKey() {
                    ComponentName componentName2 = KeyguardShortcutManager.ShortcutData.this.componentName;
                    String flattenToString = componentName2 != null ? componentName2.flattenToString() : null;
                    Intrinsics.checkNotNull(flattenToString);
                    return flattenToString;
                }

                @Override // com.android.systemui.keyguard.data.quickaffordance.KeyguardQuickAffordanceConfig
                public final Flow getLockScreenState() {
                    KeyguardShortcutManager keyguardShortcutManager = this;
                    Drawable drawable = keyguardShortcutManager.context.getDrawable(R.drawable.bg_bk);
                    Intrinsics.checkNotNull(drawable);
                    return new FlowKt__BuildersKt$flowOf$$inlined$unsafeFlow$2(new KeyguardQuickAffordanceConfig.LockScreenState.Visible(new Icon.Loaded(drawable, new ContentDescription.Loaded((String) keyguardShortcutManager.getShortcutContentDescription(ordinal))), null, 2, null));
                }

                @Override // com.android.systemui.keyguard.data.quickaffordance.KeyguardQuickAffordanceConfig
                public final int getPickerIconResourceId() {
                    return R.drawable.bg_bk;
                }

                @Override // com.android.systemui.keyguard.data.quickaffordance.KeyguardQuickAffordanceConfig
                public final KeyguardQuickAffordanceConfig.OnTriggeredResult onTriggered(Expandable expandable) {
                    KeyguardShortcutManager keyguardShortcutManager = this;
                    int i = ordinal;
                    Intent intent = keyguardShortcutManager.getIntent(i);
                    Intrinsics.checkNotNull(intent);
                    return new KeyguardQuickAffordanceConfig.OnTriggeredResult.StartActivity(intent, keyguardShortcutManager.isNoUnlockNeeded(i));
                }

                @Override // com.android.systemui.keyguard.data.quickaffordance.KeyguardQuickAffordanceConfig
                public final String pickerName() {
                    String str = KeyguardShortcutManager.ShortcutData.this.appLabel;
                    Intrinsics.checkNotNull(str);
                    return str;
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
        int i = this.context.getResources().getDisplayMetrics().widthPixels;
        return getShortcutBottomMargin(this.context.getResources().getDisplayMetrics().heightPixels, this.context.getResources().getConfiguration().orientation, z);
    }

    public final CharSequence getShortcutContentDescription(int i) {
        if (i < 0 || i >= 2) {
            ClockEventController$zenModeCallback$1$$ExternalSyntheticOutline0.m(i, "IllegalArgument : ", "KeyguardShortcutManager");
            return null;
        }
        ShortcutData[] shortcutDataArr = this.shortcutsData;
        return TextUtils.isEmpty(shortcutDataArr[i].appLabel) ? "Shortcut" : shortcutDataArr[i].appLabel;
    }

    public final Drawable getShortcutDrawable(int i) {
        if (i >= 0 && i < 2) {
            return this.shortcutsData[i].drawable;
        }
        ClockEventController$zenModeCallback$1$$ExternalSyntheticOutline0.m(i, "IllegalArgument : ", "KeyguardShortcutManager");
        return null;
    }

    public final int getShortcutIconSizeValue() {
        return getShortcutIconSizeValue(this.context.getResources().getDisplayMetrics().widthPixels, this.context.getResources().getDisplayMetrics().heightPixels, this.context.getResources().getConfiguration().orientation, this.isNowBarVisible);
    }

    public final int getShortcutSideMargin(boolean z) {
        int i = this.context.getResources().getDisplayMetrics().widthPixels;
        int i2 = this.context.getResources().getDisplayMetrics().heightPixels;
        return getShortcutSideMargin(i, this.context.getResources().getConfiguration().orientation);
    }

    public final boolean getSuspended(String str) {
        try {
            return this.packageManager.isPackageSuspended(str);
        } catch (PackageManager.NameNotFoundException unused) {
            Log.e("KeyguardShortcutManager", "getSuspended() - Not found package name = " + str);
            return false;
        }
    }

    public final void grayInvertDrawable(BitmapDrawable bitmapDrawable, boolean z, String str, boolean z2) {
        if (bitmapDrawable == null || isARShortcutIcon(str)) {
            return;
        }
        bitmapDrawable.mutate().setColorFilter(new BlendModeColorFilter(((this.isReduceTransparencyEnabled || z2) && !z) ? this.context.getColor(R.color.shortcut_icon_color_black) : this.context.getColor(R.color.shortcut_icon_color_white), BlendMode.SRC_ATOP));
    }

    public final boolean hasShortcut(int i) {
        ComponentName componentName;
        if (!((DesktopManager) Dependency.sDependency.getDependencyInner(DesktopManager.class)).isStandalone() && 2 > i && this.settingsHelper.isShortcutMasterEnabled()) {
            ShortcutData[] shortcutDataArr = this.shortcutsData;
            if (shortcutDataArr[i].enabled && ((isTaskType(i) && shortcutDataArr[i].taskName != null) || ((componentName = shortcutDataArr[i].componentName) != null && isLockTaskPermitted(componentName.getPackageName())))) {
                return true;
            }
        }
        return false;
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
        ClockEventController$zenModeCallback$1$$ExternalSyntheticOutline0.m(i, "IllegalArgument : ", "KeyguardShortcutManager");
        return false;
    }

    public final boolean isNoUnlockNeeded(int i) {
        if (i >= 0 && i < 2) {
            return this.shortcutsData[i].noUnlockNeeded;
        }
        ClockEventController$zenModeCallback$1$$ExternalSyntheticOutline0.m(i, "isNoUnlockNeeded wrong param: ", "KeyguardShortcutManager");
        return false;
    }

    public final boolean isSecure() {
        KeyguardStateController keyguardStateController = this.keyguardStateController;
        return ((KeyguardStateControllerImpl) keyguardStateController).mSecure && !((KeyguardStateControllerImpl) keyguardStateController).mCanDismissLockScreen;
    }

    public final boolean isShortcutForLiveIcon(int i) {
        ComponentName componentName = this.shortcutsData[i].componentName;
        if (componentName == null) {
            return false;
        }
        String packageName = componentName.getPackageName();
        for (String str : SAMSUNG_LIVE_ICON_PACKAGES) {
            if (Intrinsics.areEqual(str, packageName)) {
                return true;
            }
        }
        return false;
    }

    public final boolean isShortcutForPhone(int i) {
        ComponentName componentName = this.shortcutsData[i].componentName;
        return componentName != null && "com.samsung.android.dialer".equals(componentName.getPackageName()) && "com.samsung.android.dialer.DialtactsActivity".equals(componentName.getClassName());
    }

    /* JADX WARN: Code restructure failed: missing block: B:22:0x0038, code lost:
    
        if (r6.equals("com.sec.android.app.popupcalculator") == false) goto L21;
     */
    /* JADX WARN: Code restructure failed: missing block: B:29:0x0068, code lost:
    
        if (isAllowNonPlatformKeyApp(r5.context, r6, "9e92121f90ad13d9f1085b06ea9e7c72ca6d5b603cdfd6adaff7b3071792d71f") == false) goto L24;
     */
    /* JADX WARN: Removed duplicated region for block: B:19:0x004b  */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public final boolean isShortcutPermission(java.lang.String r6) {
        /*
            r5 = this;
            android.content.pm.PackageManager r0 = r5.packageManager
            java.lang.String r1 = "com.samsung.keyguard.SHORTCUT_PERMISSION"
            int r0 = r0.checkPermission(r1, r6)
            r1 = 1
            if (r0 == 0) goto L6f
            int r0 = r6.hashCode()
            r2 = 0
            r3 = -662003450(0xffffffffd88aa106, float:-1.21939356E15)
            java.lang.String r4 = "com.snapchat.android"
            if (r0 == r3) goto L3d
            r3 = 988032088(0x3ae42c58, float:0.0017408235)
            if (r0 == r3) goto L32
            r3 = 2094270320(0x7cd40770, float:8.807342E36)
            if (r0 == r3) goto L22
            goto L45
        L22:
            boolean r0 = r6.equals(r4)
            if (r0 != 0) goto L29
            goto L45
        L29:
            android.content.Context r5 = r5.context
            java.lang.String r0 = "9c1c8918e17cc686d3274f41cd04154b4cbe6a5272700de3f4f30c2c62ae2ad4"
            boolean r5 = isAllowNonPlatformKeyApp(r5, r6, r0)
            goto L6b
        L32:
            java.lang.String r0 = "com.sec.android.app.popupcalculator"
            boolean r0 = r6.equals(r0)
            if (r0 != 0) goto L3b
            goto L45
        L3b:
            r5 = r1
            goto L6b
        L3d:
            java.lang.String r0 = "com.instagram.android"
            boolean r0 = r6.equals(r0)
            if (r0 != 0) goto L56
        L45:
            boolean r0 = r6.startsWith(r4)
            if (r0 == 0) goto L54
            android.content.Context r5 = r5.context
            java.lang.String r0 = "2f4eaa0c67e2a670935ca79164f3ba4b426988b6997a97bb31152cc317dc648a"
            boolean r5 = isAllowNonPlatformKeyApp(r5, r6, r0)
            goto L6b
        L54:
            r5 = r2
            goto L6b
        L56:
            android.content.Context r0 = r5.context
            java.lang.String r3 = "a044dbdb712ab81e76949f5d76ada4dd7035643b462cb7ea2b75ecae637c2da3"
            boolean r0 = isAllowNonPlatformKeyApp(r0, r6, r3)
            if (r0 != 0) goto L3b
            android.content.Context r5 = r5.context
            java.lang.String r0 = "9e92121f90ad13d9f1085b06ea9e7c72ca6d5b603cdfd6adaff7b3071792d71f"
            boolean r5 = isAllowNonPlatformKeyApp(r5, r6, r0)
            if (r5 == 0) goto L54
            goto L3b
        L6b:
            if (r5 == 0) goto L6e
            goto L6f
        L6e:
            r1 = r2
        L6f:
            return r1
        */
        throw new UnsupportedOperationException("Method not decompiled: com.android.systemui.statusbar.KeyguardShortcutManager.isShortcutPermission(java.lang.String):boolean");
    }

    public final boolean isSupportBlur() {
        return LsRune.LOCKUI_SHORTCUT_BLUR_BG && !this.isReduceTransparencyEnabled;
    }

    public final boolean isTaskType(int i) {
        if (i >= 0 && i < 2) {
            return this.shortcutsData[i].shortcutProperty == 1;
        }
        ClockEventController$zenModeCallback$1$$ExternalSyntheticOutline0.m(i, "isTaskType wrong param: ", "KeyguardShortcutManager");
        return false;
    }

    public final boolean isTaskTypeEnabled(int i) {
        if (i >= 0 && i < 2) {
            KeyguardQuickAffordanceConfig[] keyguardQuickAffordanceConfigArr = this.keyguardBottomAreaShortcutTask;
            if (!Intrinsics.areEqual(keyguardQuickAffordanceConfigArr[i], EMPTY_CONFIG)) {
                return keyguardQuickAffordanceConfigArr[i].isTaskEnabled();
            }
        }
        ClockEventController$zenModeCallback$1$$ExternalSyntheticOutline0.m(i, "IllegalArgument : ", "KeyguardShortcutManager");
        return false;
    }

    @Override // com.android.systemui.util.SettingsHelper.OnChangedCallback
    public final void onChanged(Uri uri) {
        if (uri.equals(Settings.System.getUriFor(SettingsHelper.INDEX_AWESOME_SHORTCUT_APP_LIST)) || uri.equals(Settings.System.getUriFor(SettingsHelper.INDEX_CURRENT_SEC_APPICON_THEME_PACKAGE))) {
            updateShortcuts();
            return;
        }
        if (!uri.equals(Settings.System.getUriFor(SettingsHelper.INDEX_SET_SHORTCUTS_MODE))) {
            if (uri.equals(Settings.System.getUriFor(SettingsHelper.INDEX_ACCESSIBILITY_REDUCE_TRANSPARENCY))) {
                this.isReduceTransparencyEnabled = this.settingsHelper.isReduceTransparencyEnabled();
                updateShortcutIcons();
                return;
            }
            return;
        }
        boolean z = this.isShortcutVisibleForMDM;
        boolean isShortcutsVisibleForMDM = this.settingsHelper.isShortcutsVisibleForMDM();
        this.isShortcutVisibleForMDM = isShortcutsVisibleForMDM;
        EmergencyButtonController$$ExternalSyntheticOutline0.m("onSystemSettingsChanged oldShortcutVisibleForMDM = ", ", isShortcutVisibleForMDM = ", "KeyguardShortcutManager", z, isShortcutsVisibleForMDM);
        if (z != this.isShortcutVisibleForMDM) {
            updateShortcuts();
        }
    }

    @Override // com.android.keyguard.KeyguardUpdateMonitorCallback
    public final void onKeyguardVisibilityChanged(boolean z) {
        if (z && this.keyguardUpdateMonitor.mDeviceInteractive) {
            addListener$1();
            return;
        }
        for (int i = 0; i < 2; i++) {
            this.keyguardBottomAreaShortcutTask[i].removeListener();
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
            addListener$1();
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

    public final Bitmap scaleIcon(Bitmap bitmap) {
        float shortcutIconSizeValue = getShortcutIconSizeValue(false) * 0.85f;
        if (shortcutIconSizeValue > 0.0f) {
            int i = (int) shortcutIconSizeValue;
            bitmap = Bitmap.createScaledBitmap(bitmap, i, i, true);
        }
        Intrinsics.checkNotNull(bitmap);
        bitmap.setDensity(this.context.getResources().getDisplayMetrics().densityDpi);
        return bitmap;
    }

    public final void sendUpdateShortcutViewToCallback(int i) {
        Iterator it = this.shortcutCallbacks.iterator();
        while (it.hasNext()) {
            WeakReference weakReference = (WeakReference) it.next();
            if (((ShortcutCallback) weakReference.get()) != null) {
                Object obj = weakReference.get();
                Intrinsics.checkNotNull(obj);
                ((KeyguardSecBottomAreaViewController$shortcutManagerCallback$1) ((ShortcutCallback) obj)).updateShortcutView(i);
            }
        }
    }

    public final void updateShortcutIcon(final int i) {
        if (isTaskType(i)) {
            this.executor.execute(new Runnable() { // from class: com.android.systemui.statusbar.KeyguardShortcutManager$updateShortcutIcon$1
                @Override // java.lang.Runnable
                public final void run() {
                    final KeyguardShortcutManager keyguardShortcutManager = this;
                    final int i2 = i;
                    if (new IntPredicate() { // from class: com.android.systemui.statusbar.KeyguardShortcutManager$updateShortcutIcon$1.1
                        @Override // java.util.function.IntPredicate
                        public final boolean test(int i3) {
                            if (Intrinsics.areEqual(KeyguardShortcutManager.this.keyguardBottomAreaShortcutTask[i3], KeyguardShortcutManager.EMPTY_CONFIG)) {
                                NotificationManagerCompat$SideChannelManager$$ExternalSyntheticOutline0.m(i3, "updateShortcutsIcon : ", " is invalid task name", "KeyguardShortcutManager");
                                return false;
                            }
                            KeyguardShortcutManager keyguardShortcutManager2 = KeyguardShortcutManager.this;
                            keyguardShortcutManager2.shortcutsData[i3].isMonotoneIcon = true;
                            boolean isTaskTypeEnabled = keyguardShortcutManager2.isTaskTypeEnabled(i2);
                            KeyguardShortcutManager keyguardShortcutManager3 = KeyguardShortcutManager.this;
                            keyguardShortcutManager3.shortcutsData[i3].drawable = keyguardShortcutManager3.convertTaskDrawable(keyguardShortcutManager3.keyguardBottomAreaShortcutTask[i2].getDrawable(), WallpaperUtils.isWhiteKeyguardWallpaper("navibar"), isTaskTypeEnabled, false);
                            KeyguardShortcutManager keyguardShortcutManager4 = KeyguardShortcutManager.this;
                            KeyguardShortcutManager.ShortcutData[] shortcutDataArr = keyguardShortcutManager4.shortcutsData;
                            int i4 = i2;
                            shortcutDataArr[i4].panelDrawable = keyguardShortcutManager4.keyguardBottomAreaShortcutTask[i4].getDrawable();
                            KeyguardShortcutManager keyguardShortcutManager5 = KeyguardShortcutManager.this;
                            KeyguardShortcutManager.ShortcutData[] shortcutDataArr2 = keyguardShortcutManager5.shortcutsData;
                            int i5 = i2;
                            shortcutDataArr2[i5].panelTransitDrawable = keyguardShortcutManager5.keyguardBottomAreaShortcutTask[i5].getPanelIconTransitionDrawable(isTaskTypeEnabled);
                            return true;
                        }
                    }.test(i)) {
                        final KeyguardShortcutManager keyguardShortcutManager2 = this;
                        Handler handler = keyguardShortcutManager2.handler;
                        final int i3 = i;
                        handler.post(new Runnable() { // from class: com.android.systemui.statusbar.KeyguardShortcutManager$updateShortcutIcon$1.2
                            @Override // java.lang.Runnable
                            public final void run() {
                                KeyguardShortcutManager.access$sendUpdateIconOnlyToCallback(KeyguardShortcutManager.this, i3);
                            }
                        });
                    }
                }
            });
        } else if (this.shortcutsData[i].componentName != null) {
            this.executor.execute(new Runnable() { // from class: com.android.systemui.statusbar.KeyguardShortcutManager$updateShortcutIcon$2
                @Override // java.lang.Runnable
                public final void run() {
                    final KeyguardShortcutManager keyguardShortcutManager = this;
                    if (new IntPredicate() { // from class: com.android.systemui.statusbar.KeyguardShortcutManager$updateShortcutIcon$2.1
                        @Override // java.util.function.IntPredicate
                        public final boolean test(int i2) {
                            try {
                                Intent intent = new Intent("android.intent.action.MAIN");
                                intent.setComponent(KeyguardShortcutManager.this.shortcutsData[i2].componentName);
                                KeyguardShortcutManager keyguardShortcutManager2 = KeyguardShortcutManager.this;
                                ResolveInfo resolveActivityAsUser = keyguardShortcutManager2.packageManager.resolveActivityAsUser(intent, 129, keyguardShortcutManager2.selectedUserInteractor.getSelectedUserId(false));
                                ActivityInfo activityInfo = resolveActivityAsUser != null ? resolveActivityAsUser.activityInfo : null;
                                if (activityInfo != null) {
                                    KeyguardShortcutManager keyguardShortcutManager3 = KeyguardShortcutManager.this;
                                    keyguardShortcutManager3.shortcutsData[i2].isMonotoneIcon = KeyguardShortcutManager.access$isMonotoneIconRequired(keyguardShortcutManager3, i2);
                                    KeyguardShortcutManager keyguardShortcutManager4 = KeyguardShortcutManager.this;
                                    keyguardShortcutManager4.shortcutsData[i2].drawable = KeyguardShortcutManager.access$getShortcutIcon(keyguardShortcutManager4, activityInfo, false, i2);
                                    KeyguardShortcutManager keyguardShortcutManager5 = KeyguardShortcutManager.this;
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
                        handler.post(new Runnable() { // from class: com.android.systemui.statusbar.KeyguardShortcutManager$updateShortcutIcon$2.2
                            @Override // java.lang.Runnable
                            public final void run() {
                                KeyguardShortcutManager.access$sendUpdateIconOnlyToCallback(KeyguardShortcutManager.this, i2);
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

    public static int getShortcutBottomMargin(int i, int i2, boolean z) {
        return (int) (i * (LsRune.KEYGUARD_SUB_DISPLAY_LOCK ? z ? 0.057d : 0.051d : i2 == 1 ? DeviceState.isTablet() ? z ? 0.064d : 0.059d : z ? 0.045d : 0.0387d : DeviceState.isTablet() ? 0.069d : z ? 0.078d : 0.053d));
    }

    public static int getShortcutSideMargin(int i, int i2) {
        double d;
        double d2;
        double d3;
        if (DeviceState.isTablet() || (LsRune.KEYGUARD_SUB_DISPLAY_LOCK && ((DisplayLifecycle) Dependency.sDependency.getDependencyInner(DisplayLifecycle.class)).mIsFolderOpened)) {
            if (i2 == 1) {
                d = i;
                d2 = 0.128d;
                d3 = d * d2;
            }
            d3 = i * 0.136d;
        } else {
            if (i2 == 1) {
                d = i;
                d2 = 0.066d;
                d3 = d * d2;
            }
            d3 = i * 0.136d;
        }
        return (int) d3;
    }

    public final int getShortcutIconSizeValue(boolean z) {
        return getShortcutIconSizeValue(this.context.getResources().getDisplayMetrics().widthPixels, this.context.getResources().getDisplayMetrics().heightPixels, this.context.getResources().getConfiguration().orientation, z);
    }

    /* JADX WARN: Code restructure failed: missing block: B:10:0x0029, code lost:
    
        r3 = r4;
     */
    /* JADX WARN: Code restructure failed: missing block: B:14:0x002d, code lost:
    
        r3 = r5;
     */
    /* JADX WARN: Code restructure failed: missing block: B:16:0x0034, code lost:
    
        if (r6 == 1) goto L13;
     */
    /* JADX WARN: Code restructure failed: missing block: B:9:0x0027, code lost:
    
        if (r6 == 1) goto L13;
     */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public final int getShortcutIconSizeValue(int r4, int r5, int r6, boolean r7) {
        /*
            r3 = this;
            if (r7 == 0) goto L8
            r0 = 4592662813601374929(0x3fbc6a7ef9db22d1, double:0.111)
            goto Ld
        L8:
            r0 = 4594139994279152452(0x3fc1a9fbe76c8b44, double:0.138)
        Ld:
            boolean r3 = com.android.systemui.util.DeviceState.isTablet()
            r7 = 1
            if (r3 != 0) goto L2f
            boolean r3 = com.android.systemui.LsRune.KEYGUARD_SUB_DISPLAY_LOCK
            if (r3 == 0) goto L27
            com.android.systemui.Dependency r3 = com.android.systemui.Dependency.sDependency
            java.lang.Class<com.android.systemui.keyguard.DisplayLifecycle> r2 = com.android.systemui.keyguard.DisplayLifecycle.class
            java.lang.Object r3 = r3.getDependencyInner(r2)
            com.android.systemui.keyguard.DisplayLifecycle r3 = (com.android.systemui.keyguard.DisplayLifecycle) r3
            boolean r3 = r3.mIsFolderOpened
            if (r3 == 0) goto L27
            goto L2f
        L27:
            if (r6 != r7) goto L2d
        L29:
            double r3 = (double) r4
        L2a:
            double r3 = r3 * r0
            int r3 = (int) r3
            goto L37
        L2d:
            double r3 = (double) r5
            goto L2a
        L2f:
            r0 = 4590068740216009523(0x3fb3333333333333, double:0.075)
            if (r6 != r7) goto L2d
            goto L29
        L37:
            return r3
        */
        throw new UnsupportedOperationException("Method not decompiled: com.android.systemui.statusbar.KeyguardShortcutManager.getShortcutIconSizeValue(int, int, int, boolean):int");
    }

    public static /* synthetic */ void getIntentReceiver$annotations() {
    }
}
