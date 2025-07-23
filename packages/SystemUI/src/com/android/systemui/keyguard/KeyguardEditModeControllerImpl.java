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
import android.view.WindowManager;
import android.widget.Toast;
import androidx.cardview.widget.CardView;
import androidx.collection.MutableObjectList$$ExternalSyntheticOutline0;
import androidx.core.content.FileProvider;
import com.android.keyguard.KeyguardDisplayManager;
import com.android.keyguard.KeyguardUpdateMonitor;
import com.android.keyguard.KeyguardUpdateMonitorCallback;
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
import com.android.systemui.shade.NotificationPanelViewController$$ExternalSyntheticLambda20;
import com.android.systemui.util.DeviceState;
import com.android.systemui.util.DeviceType;
import com.android.systemui.util.SettingsHelper;
import com.android.systemui.util.leak.LeakReporter;
import com.android.systemui.wallpaper.KeyguardWallpaperController;
import com.samsung.android.feature.SemFloatingFeature;
import com.samsung.android.knox.EnterpriseDeviceManager;
import com.samsung.android.knox.container.KnoxContainerManager;
import com.samsung.android.knox.custom.CustomDeviceManager;
import java.io.File;
import java.io.FileOutputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Executor;
import kotlin.Unit;
import kotlin.jvm.functions.Function0;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.Ref$IntRef;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes2.dex */
public final class KeyguardEditModeControllerImpl implements KeyguardEditModeController {
    public static final /* synthetic */ int $r8$clinit = 0;
    public final ActivityStarter activityStarter;
    public ParcelFileDescriptor backupWallpaperPreviewPFD;
    public final Executor bgExecutor;
    public final DisplayLifecycle displayLifecycle;
    public final Executor executor;
    public Function0 isAnimationRunning;
    public boolean isCanceled;
    public boolean isEditMode;
    public boolean isNowBarVisible;
    public boolean isShowEditorRequested;
    public final KeyguardDisplayManager keyguardDisplayManager;
    public final KeyguardUpdateMonitor keyguardUpdateMonitor;
    public NotificationPanelViewController$$ExternalSyntheticLambda20 onStartActivityListener;
    public final PluginWallpaperManager pluginWallpaperManager;
    private final SettingsHelper settingsHelper;
    public Function0 startCancelAnimationFunction;
    public KeyguardEditModeControllerImpl$$ExternalSyntheticLambda2 updateViewsFunction;
    public final WakefulnessLifecycle wakefulnessLifecycle;
    public Uri wallpaperBitmapUri;
    public CardView wallpaperCardView;
    public final WallpaperImageCreator wallpaperImageCreator;
    public final WindowManager windowManager;
    public float previewScale = 0.82f;
    public float previewTopMargin = 0.041f;
    public final List listeners = new ArrayList();
    public String wallpaperRequestID = "";
    public String backupWallpaperRequestId = "";
    public final KeyguardEditModeControllerImpl$keyguardUpdateMonitorCallback$1 keyguardUpdateMonitorCallback = new KeyguardUpdateMonitorCallback() { // from class: com.android.systemui.keyguard.KeyguardEditModeControllerImpl$keyguardUpdateMonitorCallback$1
        @Override // com.android.keyguard.KeyguardUpdateMonitorCallback
        public final void onKeyguardBouncerFullyShowingChanged(boolean z) {
            CardView cardView;
            KeyguardEditModeControllerImpl keyguardEditModeControllerImpl = KeyguardEditModeControllerImpl.this;
            Log.d("KeyguardEditModeController", "onKeyguardBouncerFullyShowingChanged editorRequested=" + keyguardEditModeControllerImpl.isShowEditorRequested + " bouncerIsFullyShowing=" + z);
            if (z) {
                return;
            }
            if (keyguardEditModeControllerImpl.isShowEditorRequested && (cardView = keyguardEditModeControllerImpl.wallpaperCardView) != null) {
                WallpaperManager.getInstance(cardView.getContext()).semSendWallpaperCommand(2, "samsung.android.wallpaper.resume", null);
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

    /* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
    public final class Companion {
        public /* synthetic */ Companion(DefaultConstructorMarker defaultConstructorMarker) {
            this();
        }

        private Companion() {
        }
    }

    static {
        new Companion(null);
    }

    /* JADX WARN: Type inference failed for: r1v7, types: [com.android.systemui.keyguard.KeyguardEditModeControllerImpl$keyguardUpdateMonitorCallback$1] */
    /* JADX WARN: Type inference failed for: r1v8, types: [com.android.systemui.keyguard.KeyguardEditModeControllerImpl$wakefulnessLifecycleObserver$1] */
    /* JADX WARN: Type inference failed for: r1v9, types: [com.android.systemui.keyguard.KeyguardEditModeControllerImpl$displayLifecycleObserver$1] */
    public KeyguardEditModeControllerImpl(boolean z, Executor executor, Executor executor2, WallpaperImageCreator wallpaperImageCreator, PluginWallpaperManager pluginWallpaperManager, WakefulnessLifecycle wakefulnessLifecycle, SettingsHelper settingsHelper, KeyguardUpdateMonitor keyguardUpdateMonitor, DisplayLifecycle displayLifecycle, ActivityStarter activityStarter, WindowManager windowManager, KeyguardDisplayManager keyguardDisplayManager) {
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
        this.keyguardDisplayManager = keyguardDisplayManager;
        final int i = 0;
        this.startCancelAnimationFunction = new Function0() { // from class: com.android.systemui.keyguard.KeyguardEditModeControllerImpl$$ExternalSyntheticLambda0
            @Override // kotlin.jvm.functions.Function0
            public final Object invoke() {
                switch (i) {
                    case 0:
                        int i2 = KeyguardEditModeControllerImpl.$r8$clinit;
                        return Unit.INSTANCE;
                    default:
                        int i3 = KeyguardEditModeControllerImpl.$r8$clinit;
                        return Boolean.FALSE;
                }
            }
        };
        final int i2 = 1;
        this.isAnimationRunning = new Function0() { // from class: com.android.systemui.keyguard.KeyguardEditModeControllerImpl$$ExternalSyntheticLambda0
            @Override // kotlin.jvm.functions.Function0
            public final Object invoke() {
                switch (i2) {
                    case 0:
                        int i22 = KeyguardEditModeControllerImpl.$r8$clinit;
                        return Unit.INSTANCE;
                    default:
                        int i3 = KeyguardEditModeControllerImpl.$r8$clinit;
                        return Boolean.FALSE;
                }
            }
        };
    }

    public static final void access$saveWallpaperBitmap(KeyguardEditModeControllerImpl keyguardEditModeControllerImpl, Context context, Bitmap bitmap) {
        keyguardEditModeControllerImpl.getClass();
        try {
            File file = new File(new File(context.getFilesDir().getAbsolutePath(), "keyguard_edit.jpg").getAbsolutePath());
            FileOutputStream fileOutputStream = new FileOutputStream(file);
            try {
                bitmap.compress(Bitmap.CompressFormat.JPEG, 100, fileOutputStream);
                fileOutputStream.flush();
                Unit unit = Unit.INSTANCE;
                fileOutputStream.close();
                Uri uriForFile = FileProvider.getUriForFile(context, LeakReporter.FILEPROVIDER_AUTHORITY, file);
                keyguardEditModeControllerImpl.wallpaperBitmapUri = uriForFile;
                context.grantUriPermission("com.samsung.android.app.dressroom", uriForFile, 1);
            } finally {
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
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
        if (!this.keyguardUpdateMonitor.isUserUnlocked$1()) {
            Log.d("KeyguardEditModeController", "can not be FBE");
            return false;
        }
        if (DeviceType.isTablet() && this.keyguardDisplayManager.isDesktopMode()) {
            Log.d("KeyguardEditModeController", "can not be : New Dex or Dex Standalone");
            return false;
        }
        if (!CustomDeviceManager.getInstance().getProKioskManager().getProKioskState()) {
            try {
                if (EnterpriseDeviceManager.getInstance(context).getKioskMode().isKioskModeEnabled()) {
                    Log.d("KeyguardEditModeController", "isKioskMode : Kiosk mode");
                }
            } catch (SecurityException e) {
                Log.w("KeyguardEditModeController", "SecurityException: " + e);
            }
            return isLandscapeEditModeEnable(context) || context.getResources().getConfiguration().orientation == 1;
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
        KeyguardEditModeControllerImpl$$ExternalSyntheticLambda2 keyguardEditModeControllerImpl$$ExternalSyntheticLambda2 = this.updateViewsFunction;
        if (keyguardEditModeControllerImpl$$ExternalSyntheticLambda2 == null) {
            keyguardEditModeControllerImpl$$ExternalSyntheticLambda2 = null;
        }
        Boolean bool = Boolean.FALSE;
        keyguardEditModeControllerImpl$$ExternalSyntheticLambda2.invoke(bool, bool);
        CardView cardView = this.wallpaperCardView;
        if (cardView != null) {
            Log.d("KeyguardEditModeController", "cancel() call semSendWallpaperCommand resume");
            WallpaperManager.getInstance(cardView.getContext()).semSendWallpaperCommand(2, "samsung.android.wallpaper.resume", null);
        }
        ArrayList arrayList = (ArrayList) this.listeners;
        int size = arrayList.size();
        int i = 0;
        while (i < size) {
            Object obj = arrayList.get(i);
            i++;
            ((KeyguardEditModeController.Listener) obj).onAnimationEnded();
        }
        this.isCanceled = true;
    }

    public final long getDurationForCancelAnim() {
        return this.settingsHelper.isUltraPowerSavingMode() ? 1000L : 4000L;
    }

    public final boolean getVIRunning() {
        if (this.keyguardUpdateMonitor.isKeyguardVisible()) {
            return this.isEditMode || ((Boolean) this.isAnimationRunning.invoke()).booleanValue();
        }
        return false;
    }

    public final Bitmap getWallpaperBitmap(Context context, boolean z) {
        if (this.settingsHelper.isUltraPowerSavingMode()) {
            return null;
        }
        WallpaperManager wallpaperManager = WallpaperManager.getInstance(context);
        if (z) {
            long elapsedRealtime = SystemClock.elapsedRealtime();
            this.wallpaperRequestID = String.valueOf(elapsedRealtime);
            Bundle bundle = new Bundle();
            bundle.putString(KnoxContainerManager.CONTAINER_CREATION_REQUEST_ID, this.wallpaperRequestID);
            bundle.putLong("requestTime", elapsedRealtime);
            wallpaperManager.semSendWallpaperCommand(2, "samsung.android.wallpaper.backuprunningstate", bundle);
        }
        Log.d("KeyguardEditModeController", "send command to lockscreen semSendWallpaperCommand: pause");
        wallpaperManager.semSendWallpaperCommand(2, "samsung.android.wallpaper.pause", null);
        ImageOptionCreator.ImageOption imageOption = new ImageOptionCreator.ImageOption();
        Point realSize = this.displayLifecycle.getRealSize();
        int i = context.getResources().getConfiguration().orientation;
        if (i == 2) {
            imageOption.width = Math.max(realSize.x, realSize.y);
            imageOption.height = Math.min(realSize.x, realSize.y);
        } else {
            imageOption.width = Math.min(realSize.x, realSize.y);
            imageOption.height = Math.max(realSize.x, realSize.y);
        }
        int rotation = DeviceState.getRotation(context.getResources().getConfiguration().windowConfiguration.getRotation());
        imageOption.rotation = rotation;
        int i2 = imageOption.width;
        int i3 = imageOption.height;
        StringBuilder m = MutableObjectList$$ExternalSyntheticOutline0.m(i, rotation, "getWallpaperBitmap, orientation=", ", rotation=", ", w=");
        m.append(i2);
        m.append(", h=");
        m.append(i3);
        Log.d("KeyguardEditModeController", m.toString());
        if (z) {
            imageOption.useScreenshot = true;
        } else {
            Log.i("KeyguardEditModeController", "getWallpaperBitmap, parcelFileDescriptor : " + this.backupWallpaperPreviewPFD + ", requestId : " + this.backupWallpaperRequestId);
            try {
                ParcelFileDescriptor parcelFileDescriptor = this.backupWallpaperPreviewPFD;
                Bitmap decodeFileDescriptor = BitmapFactory.decodeFileDescriptor(parcelFileDescriptor != null ? parcelFileDescriptor.getFileDescriptor() : null);
                ParcelFileDescriptor parcelFileDescriptor2 = this.backupWallpaperPreviewPFD;
                if (parcelFileDescriptor2 != null) {
                    parcelFileDescriptor2.close();
                }
                return decodeFileDescriptor;
            } catch (Exception e) {
                Log.e("KeyguardEditModeController", String.valueOf(e));
            }
        }
        try {
            return this.wallpaperImageCreator.createImage(imageOption, null);
        } catch (IllegalArgumentException e2) {
            e2.printStackTrace();
            return null;
        }
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
            this.previewTopMargin = resourcesForApplication.getFloat(resourcesForApplication.getIdentifier("preview_top_margin_" + str, "dimen", "com.samsung.android.app.dressroom"));
            CardView cardView = this.wallpaperCardView;
            if (cardView != null) {
                cardView.setRadius(context.getResources().getDimension(R.dimen.lock_ui_edit_wallpaper_radius) / this.previewScale);
            }
            Log.d("KeyguardEditModeController", "init preview values " + str + " " + this.previewScale + " " + this.previewTopMargin);
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
        } catch (Resources.NotFoundException e2) {
            e2.printStackTrace();
        }
    }

    public final boolean isLandscapeEditModeEnable(Context context) {
        return (DeviceType.isTablet() || (LsRune.LOCKUI_SUB_DISPLAY_LOCK && context.getResources().getConfiguration().semDisplayDeviceType == 0)) && !this.settingsHelper.isUltraPowerSavingMode();
    }

    public final void onFullscreenModeChanged(Context context, boolean z) {
        if (this.settingsHelper.isSupportTouchAndHoldToEdit() && !isLandscapeEditModeEnable(context) && z) {
            if (this.settingsHelper.isUltraPowerSavingMode()) {
                Toast.makeText(context, context.getResources().getString(R.string.sec_edit_mode_disabled_by_power_saving_mode), 0).show();
            } else if (context.getResources().getConfiguration().orientation == 2) {
                Toast.makeText(context, context.getResources().getString(R.string.switch_to_portrait_mode_to_edit_the_lock_screen_toast_message), 0).show();
            }
        }
    }

    public final void refreshRadius() {
        CardView cardView = this.wallpaperCardView;
        if (cardView != null) {
            cardView.setRadius(TypedValue.applyDimension(1, SemFloatingFeature.getInstance().getInt("SEC_FLOATING_FEATURE_FRAMEWORK_CONFIG_ROUNDED_CORNER_RADIUS", 26), cardView.getResources().getDisplayMetrics()) / this.previewScale);
        }
    }

    public final boolean startEditActivity(final Context context, boolean z) {
        Log.d("KeyguardEditModeController", "startActivity begin");
        NotificationPanelViewController$$ExternalSyntheticLambda20 notificationPanelViewController$$ExternalSyntheticLambda20 = this.onStartActivityListener;
        if (notificationPanelViewController$$ExternalSyntheticLambda20 == null) {
            notificationPanelViewController$$ExternalSyntheticLambda20 = null;
        }
        notificationPanelViewController$$ExternalSyntheticLambda20.invoke();
        final Ref$IntRef ref$IntRef = new Ref$IntRef();
        ref$IntRef.element = -96;
        final Intent intent = new Intent();
        intent.setAction("com.samsung.dressroom.intent.action.SHOW_LOCK_EDITOR");
        Bundle notifyEvent = ((KeyguardWallpaperController) this.wallpaperImageCreator.mKeyguardWallpaper).notifyEvent(615);
        intent.putExtra("video_wallpaper_start_frame", notifyEvent != null ? notifyEvent.getInt("current_position") : 0);
        intent.putExtra(PluginLockProvider.KEY_WALLPAPER_INDEX, this.pluginWallpaperManager.getWallpaperIndex());
        intent.putExtra("lock_bouncer_enabled", z);
        intent.putExtra("stateBackupRequestId", this.wallpaperRequestID);
        intent.putExtra("preview_uri_from_lock", this.wallpaperBitmapUri);
        intent.putExtra("nowbar_visible", this.isNowBarVisible && !z);
        intent.setPackage("com.samsung.android.app.dressroom");
        intent.addFlags(335544352);
        if (!z) {
            this.executor.execute(new Runnable() { // from class: com.android.systemui.keyguard.KeyguardEditModeControllerImpl$startEditActivity$2
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
                        ref$IntRef2.element = service.startActivityAsUser((IApplicationThread) null, packageName, attributionTag, intent2, intent2.resolveTypeIfNeeded(context.getContentResolver()), (IBinder) null, (String) null, 0, 268435456, (ProfilerInfo) null, makeBasic.toBundle(), UserHandle.CURRENT.getIdentifier());
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
        final Bitmap wallpaperBitmap = getWallpaperBitmap(context, true);
        if (wallpaperBitmap != null) {
            this.bgExecutor.execute(new Runnable() { // from class: com.android.systemui.keyguard.KeyguardEditModeControllerImpl$startEditActivity$1$1
                @Override // java.lang.Runnable
                public final void run() {
                    KeyguardEditModeControllerImpl.access$saveWallpaperBitmap(KeyguardEditModeControllerImpl.this, context, wallpaperBitmap);
                }
            });
        }
        this.activityStarter.postStartActivityDismissingKeyguard(intent, 0);
        return true;
    }
}
