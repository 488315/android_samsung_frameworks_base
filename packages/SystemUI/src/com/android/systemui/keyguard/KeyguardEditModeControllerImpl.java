package com.android.systemui.keyguard;

import android.app.ActivityManager;
import android.app.ActivityOptions;
import android.app.ActivityTaskManager;
import android.app.IActivityTaskManager;
import android.app.IApplicationThread;
import android.app.ProfilerInfo;
import android.app.WallpaperManager;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Point;
import android.net.Uri;
import android.os.Bundle;
import android.os.IBinder;
import android.os.ParcelFileDescriptor;
import android.os.RemoteException;
import android.os.SystemClock;
import android.os.UserHandle;
import android.util.TypedValue;
import android.view.View;
import android.view.WindowManager;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.Toast;
import androidx.cardview.widget.CardView;
import androidx.compose.foundation.layout.RowColumnMeasurePolicyKt$$ExternalSyntheticOutline0;
import com.android.keyguard.KeyguardUpdateMonitor;
import com.android.keyguard.KeyguardUpdateMonitorCallback;
import com.android.systemui.Dependency;
import com.android.systemui.LsRune;
import com.android.systemui.R;
import com.android.systemui.keyguard.DisplayLifecycle;
import com.android.systemui.keyguard.KeyguardEditModeController;
import com.android.systemui.keyguard.WakefulnessLifecycle;
import com.android.systemui.keyguardimage.ImageOptionCreator;
import com.android.systemui.keyguardimage.WallpaperImageCreator;
import com.android.systemui.pluginlock.PluginLockProvider;
import com.android.systemui.pluginlock.PluginWallpaperManager;
import com.android.systemui.plugins.ActivityStarter;
import com.android.systemui.util.DesktopManager;
import com.android.systemui.util.DeviceType;
import com.android.systemui.util.SettingsHelper;
import com.android.systemui.wallpaper.KeyguardWallpaperController;
import com.samsung.android.desktopsystemui.sharedlib.system.QuickStepContract;
import com.samsung.android.feature.SemFloatingFeature;
import com.samsung.android.knox.EnterpriseDeviceManager;
import com.samsung.android.knox.container.KnoxContainerManager;
import com.samsung.android.knox.custom.CustomDeviceManager;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.concurrent.Executor;
import kotlin.Unit;
import kotlin.jvm.functions.Function0;
import kotlin.jvm.functions.Function2;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.Ref$IntRef;

public final class KeyguardEditModeControllerImpl implements KeyguardEditModeController {
    public static final /* synthetic */ int $r8$clinit = 0;
    public final ActivityStarter activityStarter;
    public ParcelFileDescriptor backupWallpaperPreviewPFD;
    public final Executor bgExecutor;
    public final DisplayLifecycle displayLifecycle;
    public final Executor executor;
    public boolean isCanceled;
    public boolean isEditMode;
    public boolean isShowEditorRequested;
    public final KeyguardUpdateMonitor keyguardUpdateMonitor;
    public Function0 onStartActivityListener;
    public final PluginWallpaperManager pluginWallpaperManager;
    private final SettingsHelper settingsHelper;
    public Function2 updateViewsFunction;
    public final WakefulnessLifecycle wakefulnessLifecycle;
    public Uri wallpaperBitmapUri;
    public CardView wallpaperCardView;
    public final WallpaperImageCreator wallpaperImageCreator;
    public final WindowManager windowManager;
    public float previewScale = 0.82f;
    public float previewTopMargin = 0.041f;
    public Function0 startCancelAnimationFunction = new Function0() { // from class: com.android.systemui.keyguard.KeyguardEditModeControllerImpl$startCancelAnimationFunction$1
        @Override // kotlin.jvm.functions.Function0
        public final /* bridge */ /* synthetic */ Object invoke() {
            return Unit.INSTANCE;
        }
    };
    public Function0 isAnimationRunning = new Function0() { // from class: com.android.systemui.keyguard.KeyguardEditModeControllerImpl$isAnimationRunning$1
        @Override // kotlin.jvm.functions.Function0
        public final /* bridge */ /* synthetic */ Object invoke() {
            return Boolean.FALSE;
        }
    };
    public final List listeners = new ArrayList();
    public String wallpaperRequestID = "";
    public String backupWallpaperRequestId = "";
    public final KeyguardEditModeControllerImpl$keyguardUpdateMonitorCallback$1 keyguardUpdateMonitorCallback = new KeyguardUpdateMonitorCallback() { // from class: com.android.systemui.keyguard.KeyguardEditModeControllerImpl$keyguardUpdateMonitorCallback$1
        @Override // com.android.keyguard.KeyguardUpdateMonitorCallback
        public final void onKeyguardBouncerFullyShowingChanged(boolean z) {
            KeyguardEditModeControllerImpl keyguardEditModeControllerImpl = KeyguardEditModeControllerImpl.this;
            Log.d("KeyguardEditModeController", "onKeyguardBouncerFullyShowingChanged editorRequested=" + keyguardEditModeControllerImpl.isShowEditorRequested + " bouncerIsFullyShowing=" + z);
            if (z) {
                return;
            }
            keyguardEditModeControllerImpl.isShowEditorRequested = false;
        }
    };
    public final KeyguardEditModeControllerImpl$wakefulnessLifecycleObserver$1 wakefulnessLifecycleObserver = new WakefulnessLifecycle.Observer() { // from class: com.android.systemui.keyguard.KeyguardEditModeControllerImpl$wakefulnessLifecycleObserver$1
        @Override // com.android.systemui.keyguard.WakefulnessLifecycle.Observer
        public final void onStartedGoingToSleep() {
            Log.d("KeyguardEditModeController", "onStartedGoingToSleep");
            KeyguardEditModeControllerImpl keyguardEditModeControllerImpl = KeyguardEditModeControllerImpl.this;
            keyguardEditModeControllerImpl.startCancelAnimationFunction.invoke();
            keyguardEditModeControllerImpl.isEditMode = false;
        }
    };
    public final KeyguardEditModeControllerImpl$displayLifecycleObserver$1 displayLifecycleObserver = new DisplayLifecycle.Observer() { // from class: com.android.systemui.keyguard.KeyguardEditModeControllerImpl$displayLifecycleObserver$1
        @Override // com.android.systemui.keyguard.DisplayLifecycle.Observer
        public final void onFolderStateChanged(boolean z) {
            Log.d("KeyguardEditModeController", "onFolderStateChanged" + z);
            KeyguardEditModeControllerImpl keyguardEditModeControllerImpl = KeyguardEditModeControllerImpl.this;
            keyguardEditModeControllerImpl.isEditMode = false;
            keyguardEditModeControllerImpl.cancel();
        }
    };

    public final class Companion {
        private Companion() {
        }

        public /* synthetic */ Companion(DefaultConstructorMarker defaultConstructorMarker) {
            this();
        }
    }

    static {
        new Companion(null);
    }

    public KeyguardEditModeControllerImpl(boolean z, Executor executor, Executor executor2, WallpaperImageCreator wallpaperImageCreator, PluginWallpaperManager pluginWallpaperManager, WakefulnessLifecycle wakefulnessLifecycle, SettingsHelper settingsHelper, KeyguardUpdateMonitor keyguardUpdateMonitor, DisplayLifecycle displayLifecycle, ActivityStarter activityStarter, WindowManager windowManager) {
        this.isEditMode = z;
        this.executor = executor;
        this.bgExecutor = executor2;
        this.wallpaperImageCreator = wallpaperImageCreator;
        this.pluginWallpaperManager = pluginWallpaperManager;
        this.wakefulnessLifecycle = wakefulnessLifecycle;
        this.settingsHelper = settingsHelper;
        this.keyguardUpdateMonitor = keyguardUpdateMonitor;
        this.displayLifecycle = displayLifecycle;
        this.activityStarter = activityStarter;
        this.windowManager = windowManager;
    }

    public static final Bitmap access$getWallpaperBitmap(KeyguardEditModeControllerImpl keyguardEditModeControllerImpl, boolean z, Context context) {
        if (keyguardEditModeControllerImpl.settingsHelper.isUltraPowerSavingMode()) {
            return null;
        }
        WallpaperManager wallpaperManager = WallpaperManager.getInstance(context);
        if (z) {
            long elapsedRealtime = SystemClock.elapsedRealtime();
            keyguardEditModeControllerImpl.wallpaperRequestID = String.valueOf(elapsedRealtime);
            Bundle bundle = new Bundle();
            bundle.putString(KnoxContainerManager.CONTAINER_CREATION_REQUEST_ID, keyguardEditModeControllerImpl.wallpaperRequestID);
            bundle.putLong("requestTime", elapsedRealtime);
            wallpaperManager.semSendWallpaperCommand(2, "samsung.android.wallpaper.backuprunningstate", bundle);
        }
        Log.d("KeyguardEditModeController", "send command to lockscreen semSendWallpaperCommand: pause");
        wallpaperManager.semSendWallpaperCommand(2, "samsung.android.wallpaper.pause", null);
        ImageOptionCreator.ImageOption imageOption = new ImageOptionCreator.ImageOption();
        Point realSize = keyguardEditModeControllerImpl.displayLifecycle.getRealSize();
        int i = context.getResources().getConfiguration().orientation;
        if (i == 2) {
            imageOption.width = Math.max(realSize.x, realSize.y);
            imageOption.height = Math.min(realSize.x, realSize.y);
        } else {
            imageOption.width = Math.min(realSize.x, realSize.y);
            imageOption.height = Math.max(realSize.x, realSize.y);
        }
        int i2 = imageOption.width;
        int i3 = imageOption.height;
        StringBuilder m = RowColumnMeasurePolicyKt$$ExternalSyntheticOutline0.m(i, i2, "getWallpaperBitmap, orientation=", ", w=", ", h=");
        m.append(i3);
        Log.d("KeyguardEditModeController", m.toString());
        imageOption.rotation = 0;
        if (z) {
            imageOption.useScreenshot = true;
        } else {
            Log.i("KeyguardEditModeController", "getWallpaperBitmap, parcelFileDescriptor : " + keyguardEditModeControllerImpl.backupWallpaperPreviewPFD + ", requestId : " + keyguardEditModeControllerImpl.backupWallpaperRequestId);
            try {
                ParcelFileDescriptor parcelFileDescriptor = keyguardEditModeControllerImpl.backupWallpaperPreviewPFD;
                Bitmap decodeFileDescriptor = BitmapFactory.decodeFileDescriptor(parcelFileDescriptor != null ? parcelFileDescriptor.getFileDescriptor() : null);
                ParcelFileDescriptor parcelFileDescriptor2 = keyguardEditModeControllerImpl.backupWallpaperPreviewPFD;
                if (parcelFileDescriptor2 != null) {
                    parcelFileDescriptor2.close();
                }
                return decodeFileDescriptor;
            } catch (Exception e) {
                Log.e("KeyguardEditModeController", String.valueOf(e));
            }
        }
        try {
            return keyguardEditModeControllerImpl.wallpaperImageCreator.createImage(imageOption, null);
        } catch (IllegalArgumentException e2) {
            e2.printStackTrace();
            return null;
        }
    }

    public final void bind(View view) {
        ImageView imageView;
        FrameLayout frameLayout;
        ImageView imageView2 = (ImageView) view.findViewById(R.id.keyguard_edit_mode_blur_effect);
        if (imageView2 == null || (imageView = (ImageView) view.findViewById(R.id.keyguard_edit_mode_wallpaper)) == null || (frameLayout = (FrameLayout) view.findViewById(R.id.keyguard_edit_mode_container)) == null) {
            return;
        }
        this.wallpaperCardView = (CardView) view.findViewById(R.id.keyguard_edit_round_layout);
        refreshRadius();
        this.updateViewsFunction = new KeyguardEditModeControllerImpl$bind$1(this, view, imageView2, imageView, frameLayout);
        initPreviewValues(view.getContext());
    }

    public final boolean canBeEditMode(Context context) {
        if (!this.settingsHelper.isSupportTouchAndHoldToEdit() || this.isEditMode) {
            Log.d("KeyguardEditModeController", "can not be EM=" + this.isEditMode);
            return false;
        }
        if (this.settingsHelper.isUltraPowerSavingMode()) {
            Log.d("KeyguardEditModeController", "can not be: PSM switched On}");
            return false;
        }
        if (!this.keyguardUpdateMonitor.isUserUnlocked()) {
            Log.d("KeyguardEditModeController", "can not be FBE");
            return false;
        }
        if (DeviceType.isTablet()) {
            if (context.getResources().getConfiguration().semDesktopModeEnabled == 1) {
                Log.d("KeyguardEditModeController", "can not be : New Dex");
                return false;
            }
            if (((DesktopManager) Dependency.sDependency.getDependencyInner(DesktopManager.class)).isStandalone()) {
                Log.d("KeyguardEditModeController", "can not be : Dex Standalone");
                return false;
            }
        }
        if (!CustomDeviceManager.getInstance().getProKioskManager().getProKioskState()) {
            try {
                if (EnterpriseDeviceManager.getInstance(context).getKioskMode().isKioskModeEnabled()) {
                    Log.d("KeyguardEditModeController", "isKioskMode : Kiosk mode");
                }
            } catch (SecurityException e) {
                Log.w("KeyguardEditModeController", "SecurityException: " + e);
            }
            return DeviceType.isTablet() || (LsRune.LOCKUI_SUB_DISPLAY_LOCK && context.getResources().getConfiguration().semDisplayDeviceType == 0) || context.getResources().getConfiguration().orientation == 1;
        }
        Log.d("KeyguardEditModeController", "isKioskMode : proKiosk mode");
        Log.d("KeyguardEditModeController", "can not be : Kiosk Modee");
        return false;
    }

    public final void cancel() {
        Log.d("KeyguardEditModeController", "cancel() " + this.isCanceled);
        if (this.isCanceled) {
            return;
        }
        Function2 function2 = this.updateViewsFunction;
        if (function2 == null) {
            function2 = null;
        }
        Boolean bool = Boolean.FALSE;
        ((KeyguardEditModeControllerImpl$bind$1) function2).invoke(bool, bool);
        CardView cardView = this.wallpaperCardView;
        if (cardView != null) {
            Log.d("KeyguardEditModeController", "cancel() call semSendWallpaperCommand resume");
            WallpaperManager.getInstance(cardView.getContext()).semSendWallpaperCommand(2, "samsung.android.wallpaper.resume", null);
        }
        Iterator it = this.listeners.iterator();
        while (it.hasNext()) {
            ((KeyguardEditModeController.Listener) it.next()).onAnimationEnded();
        }
        this.isCanceled = true;
    }

    public final long getDurationForCancelAnim() {
        return this.settingsHelper.isUltraPowerSavingMode() ? 1000L : 4000L;
    }

    public final boolean getVIRunning() {
        return this.keyguardUpdateMonitor.isKeyguardVisible() && (this.isEditMode || ((Boolean) this.isAnimationRunning.invoke()).booleanValue());
    }

    public final void initPreviewValues(Context context) {
        String str;
        try {
            Resources resourcesForApplication = context.getPackageManager().getResourcesForApplication("com.samsung.android.app.dressroom");
            if (DeviceType.isTablet()) {
                str = "tablet";
            } else if (LsRune.LOCKUI_SUB_DISPLAY_LOCK) {
                str = "fold_".concat(this.displayLifecycle.mIsFolderOpened ? "main" : "sub");
            } else {
                str = "phone";
            }
            this.previewScale = resourcesForApplication.getFloat(resourcesForApplication.getIdentifier("preview_scale_" + str, "dimen", "com.samsung.android.app.dressroom"));
            float f = resourcesForApplication.getFloat(resourcesForApplication.getIdentifier("preview_top_margin_" + str, "dimen", "com.samsung.android.app.dressroom"));
            this.previewTopMargin = f;
            Log.d("KeyguardEditModeController", "init preview values " + str + " " + this.previewScale + " " + f);
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
        } catch (Resources.NotFoundException e2) {
            e2.printStackTrace();
        }
    }

    public final void onFullscreenModeChanged(Context context, boolean z) {
        if (this.settingsHelper.isSupportTouchAndHoldToEdit() && !DeviceType.isTablet()) {
            if (!(LsRune.LOCKUI_SUB_DISPLAY_LOCK && context.getResources().getConfiguration().semDisplayDeviceType == 0) && z) {
                if (this.settingsHelper.isUltraPowerSavingMode()) {
                    Toast.makeText(context, context.getResources().getString(R.string.sec_edit_mode_disabled_by_power_saving_mode), 0).show();
                } else if (context.getResources().getConfiguration().orientation == 2) {
                    Toast.makeText(context, context.getResources().getString(R.string.switch_to_portrait_mode_to_edit_the_lock_screen_toast_message), 0).show();
                }
            }
        }
    }

    public final void refreshRadius() {
        CardView cardView = this.wallpaperCardView;
        if (cardView != null) {
            cardView.setRadius(TypedValue.applyDimension(1, SemFloatingFeature.getInstance().getInt("SEC_FLOATING_FEATURE_FRAMEWORK_CONFIG_ROUNDED_CORNER_RADIUS", 26), cardView.getResources().getDisplayMetrics()));
        }
    }

    public final void startAnimation(boolean z) {
        Log.d("KeyguardEditModeController", "startAnimation e=" + z);
        this.isEditMode = z;
        this.isCanceled = false;
        Function2 function2 = this.updateViewsFunction;
        if (function2 == null) {
            function2 = null;
        }
        ((KeyguardEditModeControllerImpl$bind$1) function2).invoke(Boolean.TRUE, Boolean.valueOf(z));
        Iterator it = ((ArrayList) this.listeners).iterator();
        while (it.hasNext()) {
            ((KeyguardEditModeController.Listener) it.next()).onAnimationStarted(z);
        }
    }

    public final boolean startEditActivity(final Context context, boolean z) {
        Log.d("KeyguardEditModeController", "startActivity begin");
        Function0 function0 = this.onStartActivityListener;
        if (function0 == null) {
            function0 = null;
        }
        function0.invoke();
        final Ref$IntRef ref$IntRef = new Ref$IntRef();
        ref$IntRef.element = -96;
        final Intent intent = new Intent();
        intent.setAction("com.samsung.dressroom.intent.action.SHOW_LOCK_EDITOR");
        Bundle notifyEvent = ((KeyguardWallpaperController) this.wallpaperImageCreator.mKeyguardWallpaper).notifyEvent(615);
        intent.putExtra("video_wallpaper_start_frame", notifyEvent != null ? notifyEvent.getInt("current_position") : 0);
        intent.putExtra(PluginLockProvider.KEY_WALLPAPER_INDEX, this.pluginWallpaperManager.getWallpaperIndex());
        intent.putExtra("lock_bouncer_enabled", z);
        if (!z) {
            intent.putExtra("stateBackupRequestId", this.wallpaperRequestID);
            intent.putExtra("preview_uri_from_lock", this.wallpaperBitmapUri);
        }
        intent.setPackage("com.samsung.android.app.dressroom");
        intent.addFlags(335544352);
        if (!z) {
            this.executor.execute(new Runnable() { // from class: com.android.systemui.keyguard.KeyguardEditModeControllerImpl$startEditActivity$1
                @Override // java.lang.Runnable
                public final void run() {
                    ActivityOptions makeBasic = ActivityOptions.makeBasic();
                    makeBasic.setDisallowEnterPictureInPictureWhileLaunching(true);
                    makeBasic.setLaunchDisplayId(0);
                    try {
                        ActivityManager.getService().resumeAppSwitches();
                        Ref$IntRef ref$IntRef2 = Ref$IntRef.this;
                        IActivityTaskManager service = ActivityTaskManager.getService();
                        String packageName = context.getPackageName();
                        String attributionTag = context.getAttributionTag();
                        Intent intent2 = intent;
                        ref$IntRef2.element = service.startActivityAsUser((IApplicationThread) null, packageName, attributionTag, intent2, intent2.resolveTypeIfNeeded(context.getContentResolver()), (IBinder) null, (String) null, 0, QuickStepContract.SYSUI_STATE_NAV_BAR_VIS_GONE, (ProfilerInfo) null, makeBasic.toBundle(), UserHandle.CURRENT.getIdentifier());
                    } catch (RemoteException e) {
                        e.printStackTrace();
                    }
                    Log.d("KeyguardEditModeController", "startActivity end " + Ref$IntRef.this.element);
                }
            });
            return ref$IntRef.element != -96;
        }
        Log.d("KeyguardEditModeController", "startActivity Dismiss Keyguard");
        this.isShowEditorRequested = true;
        this.activityStarter.postStartActivityDismissingKeyguard(intent, 0);
        return true;
    }
}
