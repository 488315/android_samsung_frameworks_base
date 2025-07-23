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
import android.graphics.BlurMaskFilter;
import android.graphics.Canvas;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffXfermode;
import android.graphics.RectF;
import android.graphics.drawable.AdaptiveIconDrawable;
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
import androidx.concurrent.futures.AbstractResolvableFuture$$ExternalSyntheticOutline0;
import androidx.core.app.NotificationManagerCompat$SideChannelManager$$ExternalSyntheticOutline0;
import com.android.keyguard.ActiveUnlockConfig$$ExternalSyntheticOutline0;
import com.android.keyguard.ClockEventController$$ExternalSyntheticOutline0;
import com.android.keyguard.KeyguardDeskTopStateMonitor;
import com.android.keyguard.KeyguardDisplayManager;
import com.android.keyguard.KeyguardKnoxGuardViewController$$ExternalSyntheticOutline0;
import com.android.keyguard.KeyguardSecUpdateMonitorImpl$$ExternalSyntheticOutline0;
import com.android.keyguard.KeyguardUpdateMonitor;
import com.android.keyguard.KeyguardUpdateMonitorCallback;
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
import com.android.systemui.util.ViewController;
import com.android.systemui.wallpaper.WallpaperUtils;
import dagger.Lazy;
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

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
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

    /* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
    public final class Companion {
        public /* synthetic */ Companion(DefaultConstructorMarker defaultConstructorMarker) {
            this();
        }

        private Companion() {
        }
    }

    /* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
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
    public KeyguardShortcutManager(Context context, BroadcastDispatcher broadcastDispatcher, Executor executor, Handler handler, UserTracker userTracker, SelectedUserInteractor selectedUserInteractor, KeyguardUpdateMonitor keyguardUpdateMonitor, SettingsHelper settingsHelper, PackageManager packageManager, KeyguardStateController keyguardStateController, Set<KeyguardQuickAffordanceConfig> set, DumpManager dumpManager, SecLockscreenTileHost secLockscreenTileHost, UserSwitcherController userSwitcherController, KeyguardDisplayManager keyguardDisplayManager) {
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
                KeyguardShortcutManager.access$handleUpdateShortcuts(KeyguardShortcutManager.this);
            }
        };
        this.themeShortcutHashMap = new HashMap();
        this.isReduceTransparencyEnabled = settingsHelper.isReduceTransparencyEnabled();
        this.wallpaperBrightness = -1;
        this.intentReceiver = new BroadcastReceiver() { // from class: com.android.systemui.statusbar.KeyguardShortcutManager$intentReceiver$1
            /* JADX WARN: Failed to restore switch over string. Please report as a decompilation issue */
            /* JADX WARN: Removed duplicated region for block: B:15:0x0121  */
            /* JADX WARN: Removed duplicated region for block: B:37:0x0167  */
            @Override // android.content.BroadcastReceiver
            /*
                Code decompiled incorrectly, please refer to instructions dump.
                To view partially-correct code enable 'Show inconsistent code' option in preferences
            */
            public final void onReceive(android.content.Context r9, android.content.Intent r10) {
                /*
                    Method dump skipped, instructions count: 522
                    To view this dump change 'Code comments level' option to 'DEBUG'
                */
                throw new UnsupportedOperationException("Method not decompiled: com.android.systemui.statusbar.KeyguardShortcutManager$intentReceiver$1.onReceive(android.content.Context, android.content.Intent):void");
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

    /* JADX WARN: Code restructure failed: missing block: B:66:0x00f3, code lost:
    
        if (r6 != null) goto L44;
     */
    /* JADX WARN: Removed duplicated region for block: B:91:0x006a  */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public static final android.graphics.drawable.Drawable access$getShortcutIcon(com.android.systemui.statusbar.KeyguardShortcutManager r9, android.content.pm.ActivityInfo r10, boolean r11, int r12) {
        /*
            Method dump skipped, instructions count: 459
            To view this dump change 'Code comments level' option to 'DEBUG'
        */
        throw new UnsupportedOperationException("Method not decompiled: com.android.systemui.statusbar.KeyguardShortcutManager.access$getShortcutIcon(com.android.systemui.statusbar.KeyguardShortcutManager, android.content.pm.ActivityInfo, boolean, int):android.graphics.drawable.Drawable");
    }

    /* JADX WARN: Removed duplicated region for block: B:32:0x016a  */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public static final void access$handleUpdateShortcuts(final com.android.systemui.statusbar.KeyguardShortcutManager r13) {
        /*
            Method dump skipped, instructions count: 442
            To view this dump change 'Code comments level' option to 'DEBUG'
        */
        throw new UnsupportedOperationException("Method not decompiled: com.android.systemui.statusbar.KeyguardShortcutManager.access$handleUpdateShortcuts(com.android.systemui.statusbar.KeyguardShortcutManager):void");
    }

    public static final boolean access$isMonotoneIconRequired(KeyguardShortcutManager keyguardShortcutManager, int i) {
        Drawable drawable;
        if (keyguardShortcutManager.settingsHelper.getActiveIconPackage() == null) {
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
        View view;
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

    public static Bitmap getCircleBitmap(Bitmap bitmap, float f) {
        int width = bitmap.getWidth();
        int height = bitmap.getHeight();
        Bitmap createBitmap = Bitmap.createBitmap(width, height, Bitmap.Config.ARGB_8888);
        Path path = new Path();
        float f2 = width / 2;
        float f3 = height / 2;
        path.addCircle(f2, f3, Math.min(f2 - f, f3 - f), Path.Direction.CCW);
        Canvas canvas = new Canvas(createBitmap);
        canvas.clipPath(path);
        canvas.drawBitmap(bitmap, 0.0f, 0.0f, (Paint) null);
        return createBitmap;
    }

    public static Bitmap imgShadow(Bitmap bitmap, int i) {
        int width = bitmap.getWidth();
        int height = bitmap.getHeight();
        Bitmap createBitmap = Bitmap.createBitmap(width, height, Bitmap.Config.ALPHA_8);
        Matrix matrix = new Matrix();
        matrix.setRectToRect(new RectF(0.0f, 0.0f, bitmap.getWidth(), bitmap.getHeight()), new RectF(0.0f, 0.0f, width, height), Matrix.ScaleToFit.CENTER);
        Matrix matrix2 = new Matrix(matrix);
        matrix2.postTranslate(0.0f, 0.0f);
        Canvas canvas = new Canvas(createBitmap);
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
        Bitmap createBitmap2 = Bitmap.createBitmap(width, height, Bitmap.Config.ARGB_8888);
        Canvas canvas2 = new Canvas(createBitmap2);
        canvas2.drawBitmap(createBitmap, 0.0f, 0.0f, paint);
        canvas2.drawBitmap(bitmap, matrix, paint2);
        createBitmap.recycle();
        return createBitmap2;
    }

    public static boolean isARShortcutIcon(String str) {
        return "com.samsung.android.aremoji".equals(str) || "com.sec.android.mimage.avatarstickers".equals(str);
    }

    public static boolean isAllowNonPlatformKeyApp(Context context, String str, String str2) {
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

    /* JADX WARN: Code restructure failed: missing block: B:16:0x0012, code lost:
    
        if (r5 != false) goto L17;
     */
    /* JADX WARN: Code restructure failed: missing block: B:4:0x000a, code lost:
    
        if (r5 != false) goto L6;
     */
    /* JADX WARN: Code restructure failed: missing block: B:5:0x000c, code lost:
    
        r0 = com.android.systemui.R.color.shortcut_black_tint_task_on;
     */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public final android.graphics.drawable.Drawable convertTaskDrawable(android.graphics.drawable.Drawable r4, boolean r5, boolean r6, boolean r7, boolean r8) {
        /*
            r3 = this;
            if (r4 == 0) goto L70
            r0 = 2131102171(0x7f0609db, float:1.7816772E38)
            r1 = 2131102162(0x7f0609d2, float:1.7816754E38)
            if (r7 == 0) goto Le
            if (r5 == 0) goto L26
        Lc:
            r0 = r1
            goto L26
        Le:
            boolean r2 = r3.isReduceTransparencyEnabled
            if (r2 == 0) goto L15
            if (r5 == 0) goto Lc
            goto L26
        L15:
            if (r6 == 0) goto L19
            if (r5 == 0) goto L1f
        L19:
            int r5 = r3.wallpaperBrightness
            r6 = 84
            if (r5 <= r6) goto L23
        L1f:
            r0 = 2131102165(0x7f0609d5, float:1.781676E38)
            goto L26
        L23:
            r0 = 2131102166(0x7f0609d6, float:1.7816762E38)
        L26:
            android.content.Context r5 = r3.context
            int r5 = r5.getColor(r0)
            android.graphics.Bitmap r4 = androidx.core.graphics.drawable.DrawableKt.toBitmap$default(r4)
            android.graphics.Bitmap r4 = r3.scaleIcon(r4, r8)
            boolean r6 = com.android.systemui.LsRune.LOCKUI_SHORTCUT_BLUR_BG
            if (r6 != 0) goto L4b
            if (r7 == 0) goto L3b
            goto L4b
        L3b:
            android.graphics.drawable.BitmapDrawable r6 = new android.graphics.drawable.BitmapDrawable
            android.content.Context r3 = r3.context
            android.content.res.Resources r3 = r3.getResources()
            android.graphics.Bitmap r4 = imgShadow(r4, r5)
            r6.<init>(r3, r4)
            return r6
        L4b:
            android.graphics.drawable.BitmapDrawable r6 = new android.graphics.drawable.BitmapDrawable
            android.content.Context r3 = r3.context
            android.content.res.Resources r3 = r3.getResources()
            r6.<init>(r3, r4)
            android.graphics.drawable.Drawable r3 = r6.mutate()
            r3.clearColorFilter()
            android.graphics.drawable.Drawable r3 = r6.mutate()
            r4 = 0
            r3.setTint(r4)
            android.graphics.BlendModeColorFilter r3 = new android.graphics.BlendModeColorFilter
            android.graphics.BlendMode r4 = android.graphics.BlendMode.SRC_ATOP
            r3.<init>(r5, r4)
            r6.setColorFilter(r3)
            return r6
        L70:
            r3 = 0
            return r3
        */
        throw new UnsupportedOperationException("Method not decompiled: com.android.systemui.statusbar.KeyguardShortcutManager.convertTaskDrawable(android.graphics.drawable.Drawable, boolean, boolean, boolean, boolean):android.graphics.drawable.Drawable");
    }

    @Override // com.android.systemui.Dumpable
    public final void dump(PrintWriter printWriter, String[] strArr) {
        String str;
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
                    if (componentName == null || (str = componentName.getPackageName()) == null) {
                        str = "";
                    }
                    ActiveUnlockConfig$$ExternalSyntheticOutline0.m(printWriter, "    isSuspended = ", getSuspended(str));
                    ComponentName componentName2 = shortcutData.componentName;
                    ActiveUnlockConfig$$ExternalSyntheticOutline0.m(printWriter, "    isLockTaskPermitted = ", isLockTaskPermitted(componentName2 != null ? componentName2.getPackageName() : null));
                }
            }
        }
    }

    public final int getColorCurvePreset(int i) {
        boolean z = isTaskType(i) && isTaskTypeEnabled(i);
        boolean isWhiteKeyguardWallpaper = WallpaperUtils.isWhiteKeyguardWallpaper("navibar");
        if (z) {
            return isWhiteKeyguardWallpaper ? 120 : 105;
        }
        int i2 = this.wallpaperBrightness;
        if (i2 == -1) {
            return isWhiteKeyguardWallpaper ? 117 : 106;
        }
        if (i2 < 0 || i2 >= 29) {
            return (29 > i2 || i2 >= 85) ? 117 : 116;
        }
        return 106;
    }

    public final String getComponentNameForSALogging(int i) {
        String m;
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
                if (componentName != null && (m = AbstractResolvableFuture$$ExternalSyntheticOutline0.m(componentName.getPackageName(), "/", componentName.getClassName())) != null) {
                    return m;
                }
            }
        }
        return "Empty";
    }

    public final int getInvertColor(boolean z, boolean z2) {
        return (((this.isReduceTransparencyEnabled || z2) && !z) || this.wallpaperBrightness > 84) ? this.context.getColor(R.color.shortcut_icon_color_black) : this.context.getColor(R.color.shortcut_icon_color_white);
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

    public final int getNowBarBottomMargin(int i, int i2) {
        float f;
        float f2;
        boolean z = i2 == 2;
        if (DeviceState.isTablet() || DeviceState.isMultiFoldMain()) {
            if (!z) {
                Lazy lazy = this.keyguardDisplayManager.mKeyguardDeskTopStateMonitorLazy;
                if (!(lazy != null ? ((KeyguardDeskTopStateMonitor) lazy.get()).mIsDesktopStandAlone : false)) {
                    f = i;
                    f2 = 0.045f;
                }
            }
            f = i;
            f2 = 0.059f;
        } else if (LsRune.LOCKUI_SUB_DISPLAY_LOCK && ((DisplayLifecycle) Dependency.sDependency.getDependencyInner(DisplayLifecycle.class)).mIsFolderOpened) {
            f = i;
            f2 = 0.047f;
        } else if (z) {
            f = i;
            f2 = 0.056f;
        } else {
            f = i;
            f2 = 0.035f;
        }
        return (int) (f * f2);
    }

    public final int getNowBarCollapsedHeight() {
        Number valueOf;
        float f = this.context.getResources().getDisplayMetrics().widthPixels;
        float f2 = this.context.getResources().getDisplayMetrics().heightPixels;
        float f3 = this.context.getResources().getDisplayMetrics().density;
        if (DeviceState.isTablet() || DeviceState.isMultiFoldMain()) {
            valueOf = Float.valueOf(62 * f3);
        } else if (LsRune.LOCKUI_SUB_DISPLAY_LOCK && ((DisplayLifecycle) Dependency.sDependency.getDependencyInner(DisplayLifecycle.class)).mIsFolderOpened) {
            valueOf = Float.valueOf(56 * f3);
        } else {
            if (f < f2) {
                f = f2;
            }
            valueOf = Integer.valueOf((int) (f * 0.07f));
        }
        return valueOf.intValue();
    }

    public final List getQuickAffordanceConfigList() {
        ComponentName componentName;
        KeyguardQuickAffordancePosition[] values = KeyguardQuickAffordancePosition.values();
        ArrayList arrayList = new ArrayList(values.length);
        for (KeyguardQuickAffordancePosition keyguardQuickAffordancePosition : values) {
            final int ordinal = keyguardQuickAffordancePosition.ordinal();
            ShortcutData[] shortcutDataArr = this.shortcutsData;
            final ShortcutData shortcutData = shortcutDataArr[ordinal];
            arrayList.add((shortcutData == null || !shortcutData.enabled || (!isTaskType(ordinal) && ((componentName = shortcutData.componentName) == null || componentName.getPackageName() == null))) ? EMPTY_CONFIG : isTaskType(ordinal) ? getKeyguardBottomAreaShortcutTask(keyguardQuickAffordancePosition.ordinal(), shortcutDataArr[ordinal].taskName) : new KeyguardQuickAffordanceConfig(this, ordinal) { // from class: com.android.systemui.statusbar.KeyguardShortcutManager$generateQuickAffordanceConfig$1$1
                public final /* synthetic */ int $this_with;
                public final String key;
                public final FlowKt__BuildersKt$flowOf$$inlined$unsafeFlow$2 lockScreenState;
                public final int pickerIconResourceId;
                public final /* synthetic */ KeyguardShortcutManager this$0;

                {
                    String flattenToString;
                    this.this$0 = this;
                    this.$this_with = ordinal;
                    ComponentName componentName2 = KeyguardShortcutManager.ShortcutData.this.componentName;
                    this.key = (componentName2 == null || (flattenToString = componentName2.flattenToString()) == null) ? "" : flattenToString;
                    this.pickerIconResourceId = R.drawable.bg_bk;
                    Drawable drawable = this.context.getDrawable(R.drawable.bg_bk);
                    drawable.getClass();
                    this.lockScreenState = new FlowKt__BuildersKt$flowOf$$inlined$unsafeFlow$2(new KeyguardQuickAffordanceConfig.LockScreenState.Visible(new Icon.Loaded(drawable, new ContentDescription.Loaded((String) this.getShortcutContentDescription(ordinal)), null, 4, null), null, 2, null));
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
                    Intent intent = null;
                    if (i < 0 || i >= 2) {
                        ClockEventController$$ExternalSyntheticOutline0.m(i, "getIntent wrong param : ", "KeyguardShortcutManager");
                    } else {
                        KeyguardShortcutManager.ShortcutData[] shortcutDataArr2 = keyguardShortcutManager.shortcutsData;
                        if (KeyguardShortcutManager.isSamsungCameraPackage(shortcutDataArr2[i].componentName)) {
                            Log.d("KeyguardShortcutManager", "th = " + i + " is camera package");
                            intent = keyguardShortcutManager.isSecure() ? KeyguardShortcutManager.SECURE_CAMERA_INTENT : KeyguardShortcutManager.INSECURE_CAMERA_INTENT;
                        } else {
                            ComponentName componentName2 = shortcutDataArr2[i].componentName;
                            if (componentName2 == null ? false : "com.samsung.android.app.galaxyraw".equals(componentName2.getPackageName())) {
                                NotificationManagerCompat$SideChannelManager$$ExternalSyntheticOutline0.m(i, "th = ", " is expert raw camera package", "KeyguardShortcutManager");
                                intent = KeyguardShortcutManager.SAMSUNG_EXPERT_RAW_CAMERA_INTENT;
                            } else {
                                Intent intent2 = new Intent("android.intent.action.MAIN");
                                boolean isSecure = keyguardShortcutManager.isSecure();
                                if (isSecure || !shortcutDataArr2[i].launchInsecureMain) {
                                    intent2.setComponent(shortcutDataArr2[i].componentName);
                                } else {
                                    intent2.addCategory("android.intent.category.LAUNCHER");
                                    ComponentName componentName3 = shortcutDataArr2[i].componentName;
                                    intent2.setPackage(componentName3 != null ? componentName3.getPackageName() : null);
                                    ResolveInfo resolveActivityAsUser = keyguardShortcutManager.packageManager.resolveActivityAsUser(intent2, 1, keyguardShortcutManager.selectedUserInteractor.getSelectedUserId());
                                    if ((resolveActivityAsUser != null ? resolveActivityAsUser.activityInfo : null) != null) {
                                        ActivityInfo activityInfo = resolveActivityAsUser.activityInfo;
                                        intent2.setComponent(new ComponentName(activityInfo.packageName, activityInfo.name));
                                    } else {
                                        intent2.setComponent(shortcutDataArr2[i].componentName);
                                    }
                                }
                                intent2.putExtra("isSecure", isSecure);
                                intent = intent2.addFlags(268500992);
                            }
                        }
                    }
                    intent.getClass();
                    return new KeyguardQuickAffordanceConfig.OnTriggeredResult.StartActivity(intent, keyguardShortcutManager.isNoUnlockNeeded(i));
                }

                @Override // com.android.systemui.keyguard.data.quickaffordance.KeyguardQuickAffordanceConfig
                public final String pickerName() {
                    String str = KeyguardShortcutManager.ShortcutData.this.appLabel;
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
        if (z) {
            return getNowBarBottomMargin(i, i2) + ((getNowBarCollapsedHeight() - getShortcutIconSizeValue(true)) / 2);
        }
        double d = 0.051d;
        if (i2 == 1) {
            if (DeviceState.isTablet() || DeviceState.isMultiFoldMain()) {
                d = 0.045d;
            } else if (!LsRune.LOCKUI_SUB_DISPLAY_LOCK || !((DisplayLifecycle) Dependency.sDependency.getDependencyInner(DisplayLifecycle.class)).mIsFolderOpened) {
                d = 0.038d;
            }
        } else if (DeviceState.isTablet() || DeviceState.isMultiFoldMain()) {
            d = 0.069d;
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
        if (DeviceState.isTablet() || DeviceState.isMultiFoldMain()) {
            this.shortcutIconSize = (int) ((z ? 48 : 60) * this.context.getResources().getDisplayMetrics().density);
        } else if (LsRune.LOCKUI_SUB_DISPLAY_LOCK && ((DisplayLifecycle) Dependency.sDependency.getDependencyInner(DisplayLifecycle.class)).mIsFolderOpened) {
            this.shortcutIconSize = (int) ((z ? 40 : 50) * this.context.getResources().getDisplayMetrics().density);
        } else {
            this.shortcutIconSize = (int) (Math.min(i, i2) * (z ? 0.111d : 0.139d));
        }
        return this.shortcutIconSize;
    }

    /* JADX WARN: Removed duplicated region for block: B:12:0x0084  */
    /* JADX WARN: Removed duplicated region for block: B:15:0x008b  */
    /* JADX WARN: Removed duplicated region for block: B:30:0x00b8  */
    /* JADX WARN: Removed duplicated region for block: B:31:0x00bc  */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public final int getShortcutSideMargin() {
        /*
            r10 = this;
            android.content.Context r0 = r10.context
            android.content.res.Resources r0 = r0.getResources()
            android.util.DisplayMetrics r0 = r0.getDisplayMetrics()
            int r0 = r0.widthPixels
            android.content.Context r1 = r10.context
            android.content.res.Resources r1 = r1.getResources()
            android.util.DisplayMetrics r1 = r1.getDisplayMetrics()
            int r1 = r1.heightPixels
            android.content.Context r2 = r10.context
            android.content.res.Resources r2 = r2.getResources()
            android.content.res.Configuration r2 = r2.getConfiguration()
            int r2 = r2.orientation
            r3 = 1
            int r4 = r10.getShortcutIconSizeValue(r3)
            r5 = 2
            int r4 = r4 * r5
            int r4 = r0 - r4
            r6 = 0
            if (r2 != r5) goto L32
            r7 = r3
            goto L33
        L32:
            r7 = r6
        L33:
            boolean r8 = com.android.systemui.util.DeviceState.isTablet()
            java.lang.Class<com.android.systemui.keyguard.DisplayLifecycle> r9 = com.android.systemui.keyguard.DisplayLifecycle.class
            if (r8 == 0) goto L47
            if (r7 == 0) goto L41
            r10 = 1049012208(0x3e86a7f0, float:0.263)
            goto L44
        L41:
            r10 = 1054280253(0x3ed70a3d, float:0.42)
        L44:
            float r1 = (float) r0
        L45:
            float r10 = r10 * r1
            goto L7f
        L47:
            boolean r8 = com.android.systemui.util.DeviceState.isMultiFoldMain()
            if (r8 == 0) goto L5c
            android.content.Context r10 = r10.context
            android.content.res.Resources r10 = r10.getResources()
            r1 = 2131168494(0x7f070cee, float:1.7951291E38)
            int r10 = r10.getDimensionPixelSize(r1)
            float r10 = (float) r10
            goto L7f
        L5c:
            boolean r10 = com.android.systemui.LsRune.LOCKUI_SUB_DISPLAY_LOCK
            if (r10 == 0) goto L75
            com.android.systemui.Dependency r10 = com.android.systemui.Dependency.sDependency
            java.lang.Object r10 = r10.getDependencyInner(r9)
            com.android.systemui.keyguard.DisplayLifecycle r10 = (com.android.systemui.keyguard.DisplayLifecycle) r10
            boolean r10 = r10.mIsFolderOpened
            if (r10 == 0) goto L75
            int r10 = java.lang.Math.min(r1, r0)
            float r10 = (float) r10
            r1 = 1052736750(0x3ebf7cee, float:0.374)
            goto L45
        L75:
            if (r7 == 0) goto L7b
            r10 = 1053609165(0x3ecccccd, float:0.4)
            goto L44
        L7b:
            r10 = 1058457780(0x3f16c8b4, float:0.589)
            goto L44
        L7f:
            int r10 = (int) r10
            int r4 = r4 - r10
            if (r2 != r5) goto L84
            goto L85
        L84:
            r3 = r6
        L85:
            boolean r10 = com.android.systemui.util.DeviceState.isTablet()
            if (r10 != 0) goto Lb6
            boolean r10 = com.android.systemui.util.DeviceState.isMultiFoldMain()
            if (r10 == 0) goto L92
            goto Lb6
        L92:
            boolean r10 = com.android.systemui.LsRune.LOCKUI_SUB_DISPLAY_LOCK
            if (r10 == 0) goto La9
            com.android.systemui.Dependency r10 = com.android.systemui.Dependency.sDependency
            java.lang.Object r10 = r10.getDependencyInner(r9)
            com.android.systemui.keyguard.DisplayLifecycle r10 = (com.android.systemui.keyguard.DisplayLifecycle) r10
            boolean r10 = r10.mIsFolderOpened
            if (r10 == 0) goto La9
            r10 = 1042871747(0x3e28f5c3, float:0.165)
            float r0 = (float) r0
            float r0 = r0 * r10
            int r10 = (int) r0
            goto Lc0
        La9:
            if (r3 == 0) goto Laf
            r10 = 1038710997(0x3de978d5, float:0.114)
            goto Lb2
        Laf:
            r10 = 1021665346(0x3ce56042, float:0.028)
        Lb2:
            float r0 = (float) r0
            float r10 = r10 * r0
            int r10 = (int) r10
            goto Lc0
        Lb6:
            if (r3 == 0) goto Lbc
            r10 = 1032402764(0x3d89374c, float:0.067)
            goto Lb2
        Lbc:
            r10 = 1037771473(0x3ddb22d1, float:0.107)
            goto Lb2
        Lc0:
            int r10 = r10 * r5
            int r4 = r4 - r10
            int r4 = r4 / r5
            return r4
        */
        throw new UnsupportedOperationException("Method not decompiled: com.android.systemui.statusbar.KeyguardShortcutManager.getShortcutSideMargin():int");
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
        Lazy lazy = this.keyguardDisplayManager.mKeyguardDeskTopStateMonitorLazy;
        if (!(lazy == null ? false : ((KeyguardDeskTopStateMonitor) lazy.get()).mIsDesktopStandAlone) && 2 > i && this.settingsHelper.isShortcutMasterEnabled()) {
            ShortcutData[] shortcutDataArr = this.shortcutsData;
            if (shortcutDataArr[i].enabled) {
                if (isTaskType(i) && shortcutDataArr[i].taskName != null) {
                    return true;
                }
                ComponentName componentName = shortcutDataArr[i].componentName;
                if (componentName != null && isLockTaskPermitted(componentName.getPackageName())) {
                    return true;
                }
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

    /* JADX WARN: Code restructure failed: missing block: B:20:0x0038, code lost:
    
        if (r6.equals("com.sec.android.app.popupcalculator") == false) goto L21;
     */
    /* JADX WARN: Code restructure failed: missing block: B:27:0x0068, code lost:
    
        if (isAllowNonPlatformKeyApp(r5.context, r6, "9e92121f90ad13d9f1085b06ea9e7c72ca6d5b603cdfd6adaff7b3071792d71f") == false) goto L24;
     */
    /* JADX WARN: Removed duplicated region for block: B:13:0x006e A[RETURN] */
    /* JADX WARN: Removed duplicated region for block: B:17:0x004b  */
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
            r3 = -662003450(0xffffffffd88aa106, float:-1.2193936E15)
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
            return r2
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
        boolean isShortcutsVisibleForMDM = this.settingsHelper.isShortcutsVisibleForMDM();
        this.isShortcutVisibleForMDM = isShortcutsVisibleForMDM;
        KeyguardKnoxGuardViewController$$ExternalSyntheticOutline0.m("onSystemSettingsChanged oldShortcutVisibleForMDM = ", ", isShortcutVisibleForMDM = ", "KeyguardShortcutManager", z, isShortcutsVisibleForMDM);
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
                            KeyguardShortcutManager.ShortcutData[] shortcutDataArr = keyguardShortcutManager3.shortcutsData;
                            int i4 = i2;
                            shortcutDataArr[i4].isIconPaddingNeeded = keyguardShortcutManager3.keyguardBottomAreaShortcutTask[i4].isIconPaddingRequired();
                            KeyguardShortcutManager keyguardShortcutManager4 = KeyguardShortcutManager.this;
                            keyguardShortcutManager4.shortcutsData[i3].drawable = keyguardShortcutManager4.convertTaskDrawable(keyguardShortcutManager4.keyguardBottomAreaShortcutTask[i2].getDrawable(), WallpaperUtils.isWhiteKeyguardWallpaper("navibar"), isTaskTypeEnabled, false, KeyguardShortcutManager.this.shortcutsData[i2].isIconPaddingNeeded);
                            KeyguardShortcutManager keyguardShortcutManager5 = KeyguardShortcutManager.this;
                            KeyguardShortcutManager.ShortcutData[] shortcutDataArr2 = keyguardShortcutManager5.shortcutsData;
                            int i5 = i2;
                            shortcutDataArr2[i5].panelDrawable = keyguardShortcutManager5.keyguardBottomAreaShortcutTask[i5].getDrawable();
                            KeyguardShortcutManager keyguardShortcutManager6 = KeyguardShortcutManager.this;
                            KeyguardShortcutManager.ShortcutData[] shortcutDataArr3 = keyguardShortcutManager6.shortcutsData;
                            int i6 = i2;
                            shortcutDataArr3[i6].panelTransitDrawable = keyguardShortcutManager6.keyguardBottomAreaShortcutTask[i6].getPanelIconTransitionDrawable();
                            KeyguardShortcutManager keyguardShortcutManager7 = KeyguardShortcutManager.this;
                            KeyguardShortcutManager.ShortcutData[] shortcutDataArr4 = keyguardShortcutManager7.shortcutsData;
                            int i7 = i2;
                            shortcutDataArr4[i7].isUnlockWaitNeeded = keyguardShortcutManager7.keyguardBottomAreaShortcutTask[i7].isUnlockWaitRequired();
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
                                ResolveInfo resolveActivityAsUser = keyguardShortcutManager2.packageManager.resolveActivityAsUser(intent, 129, keyguardShortcutManager2.selectedUserInteractor.getSelectedUserId());
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

    public static /* synthetic */ void getIntentReceiver$annotations() {
    }
}
