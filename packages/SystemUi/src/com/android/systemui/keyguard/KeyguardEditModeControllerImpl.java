package com.android.systemui.keyguard;

import android.app.ActivityManager;
import android.app.ActivityOptions;
import android.app.ActivityTaskManager;
import android.app.IActivityTaskManager;
import android.app.IApplicationThread;
import android.app.ProfilerInfo;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.content.res.Resources;
import android.net.Uri;
import android.os.IBinder;
import android.os.ParcelFileDescriptor;
import android.os.RemoteException;
import android.os.UserHandle;
import android.util.TypedValue;
import android.view.WindowManager;
import android.widget.FrameLayout;
import android.widget.ImageView;
import androidx.cardview.widget.CardView;
import com.android.keyguard.KeyguardUpdateMonitor;
import com.android.systemui.LsRune;
import com.android.systemui.R;
import com.android.systemui.keyguard.DisplayLifecycle;
import com.android.systemui.keyguard.KeyguardEditModeController;
import com.android.systemui.keyguard.WakefulnessLifecycle;
import com.android.systemui.keyguardimage.WallpaperImageCreator;
import com.android.systemui.pluginlock.PluginWallpaperManager;
import com.android.systemui.pluginlock.PluginWallpaperManagerImpl;
import com.android.systemui.plugins.ActivityStarter;
import com.android.systemui.shade.NotificationPanelView;
import com.android.systemui.util.DeviceType;
import com.android.systemui.util.SafeUIState;
import com.android.systemui.util.SettingsHelper;
import com.android.systemui.wallpaper.KeyguardWallpaperController;
import com.android.systemui.wallpaper.view.SystemUIWallpaperBase;
import com.samsung.android.desktopsystemui.sharedlib.system.QuickStepContract;
import com.samsung.android.feature.SemFloatingFeature;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.concurrent.Executor;
import kotlin.Unit;
import kotlin.jvm.functions.Function0;
import kotlin.jvm.functions.Function2;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.Ref$IntRef;

/* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
/* loaded from: classes.dex */
public final class KeyguardEditModeControllerImpl implements KeyguardEditModeController {
    public static final /* synthetic */ int $r8$clinit = 0;
    public final ActivityStarter activityStarter;
    public ParcelFileDescriptor backupWallpaperPreviewPFD;
    public final Executor bgExecutor;
    public final DisplayLifecycle displayLifecycle;
    public final Executor executor;
    public boolean isCanceled;
    public boolean isEditMode;
    public final KeyguardUpdateMonitor keyguardUpdateMonitor;
    public Function0 onStartActivityListener;
    public final PluginWallpaperManager pluginWallpaperManager;
    public final SettingsHelper settingsHelper;
    public Function2 updateViewsFunction;
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
        public final Object invoke() {
            return Boolean.FALSE;
        }
    };
    public Function0 isTouchDownAnimationRunning = new Function0() { // from class: com.android.systemui.keyguard.KeyguardEditModeControllerImpl$isTouchDownAnimationRunning$1
        @Override // kotlin.jvm.functions.Function0
        public final Object invoke() {
            return Boolean.FALSE;
        }
    };
    public final List listeners = new ArrayList();
    public String wallpaperRequestID = "";
    public String backupWallpaperRequestId = "";

    /* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
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
        this.settingsHelper = settingsHelper;
        this.keyguardUpdateMonitor = keyguardUpdateMonitor;
        this.displayLifecycle = displayLifecycle;
        this.activityStarter = activityStarter;
        this.windowManager = windowManager;
        if (SafeUIState.isSysUiSafeModeEnabled()) {
            return;
        }
        wakefulnessLifecycle.addObserver(new WakefulnessLifecycle.Observer() { // from class: com.android.systemui.keyguard.KeyguardEditModeControllerImpl.1
            @Override // com.android.systemui.keyguard.WakefulnessLifecycle.Observer
            public final void onStartedGoingToSleep() {
                Log.m138d("KeyguardEditModeController", "onStartedGoingToSleep");
                KeyguardEditModeControllerImpl keyguardEditModeControllerImpl = KeyguardEditModeControllerImpl.this;
                keyguardEditModeControllerImpl.startCancelAnimationFunction.invoke();
                keyguardEditModeControllerImpl.isEditMode = false;
            }
        });
        displayLifecycle.addObserver(new DisplayLifecycle.Observer() { // from class: com.android.systemui.keyguard.KeyguardEditModeControllerImpl.2
            @Override // com.android.systemui.keyguard.DisplayLifecycle.Observer
            public final void onFolderStateChanged(boolean z2) {
                Log.m138d("KeyguardEditModeController", "onFolderStateChanged" + z2);
                KeyguardEditModeControllerImpl keyguardEditModeControllerImpl = KeyguardEditModeControllerImpl.this;
                keyguardEditModeControllerImpl.isEditMode = false;
                keyguardEditModeControllerImpl.cancel();
            }
        });
    }

    public final void bind(NotificationPanelView notificationPanelView) {
        ImageView imageView = (ImageView) notificationPanelView.findViewById(R.id.keyguard_edit_mode_blur_effect);
        ImageView imageView2 = (ImageView) notificationPanelView.findViewById(R.id.keyguard_edit_mode_wallpaper);
        FrameLayout frameLayout = (FrameLayout) notificationPanelView.findViewById(R.id.keyguard_edit_mode_container);
        this.wallpaperCardView = (CardView) notificationPanelView.findViewById(R.id.keyguard_edit_round_layout);
        refreshRadius();
        this.updateViewsFunction = new KeyguardEditModeControllerImpl$bind$1(this, notificationPanelView, imageView, imageView2, frameLayout);
        initPreviewValues(notificationPanelView.getContext());
    }

    public final void cancel() {
        Log.m138d("KeyguardEditModeController", "cancel() " + this.isCanceled);
        if (this.isCanceled) {
            return;
        }
        Function2 function2 = this.updateViewsFunction;
        if (function2 == null) {
            function2 = null;
        }
        Boolean bool = Boolean.FALSE;
        ((KeyguardEditModeControllerImpl$bind$1) function2).invoke(bool, bool);
        Iterator it = this.listeners.iterator();
        while (it.hasNext()) {
            ((KeyguardEditModeController.Listener) it.next()).onAnimationEnded();
        }
        this.isCanceled = true;
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
            Log.m138d("KeyguardEditModeController", "init preview values " + str + " " + this.previewScale + " " + f);
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
        } catch (Resources.NotFoundException e2) {
            e2.printStackTrace();
        }
    }

    public final void refreshRadius() {
        CardView cardView = this.wallpaperCardView;
        if (cardView == null) {
            cardView = null;
        }
        cardView.setRadius(TypedValue.applyDimension(1, SemFloatingFeature.getInstance().getInt("SEC_FLOATING_FEATURE_FRAMEWORK_CONFIG_ROUNDED_CORNER_RADIUS", 26), cardView.getResources().getDisplayMetrics()));
    }

    public final void startAnimation(boolean z) {
        Log.m138d("KeyguardEditModeController", "startAnimation e=" + z);
        this.isEditMode = z;
        this.isCanceled = false;
        Function2 function2 = this.updateViewsFunction;
        if (function2 == null) {
            function2 = null;
        }
        ((KeyguardEditModeControllerImpl$bind$1) function2).invoke(Boolean.TRUE, Boolean.valueOf(z));
        Iterator it = this.listeners.iterator();
        while (it.hasNext()) {
            ((KeyguardEditModeController.Listener) it.next()).onAnimationStarted(z);
        }
    }

    public final boolean startEditActivity(final Context context, boolean z) {
        Log.m138d("KeyguardEditModeController", "startActivity begin");
        Function0 function0 = this.onStartActivityListener;
        if (function0 == null) {
            function0 = null;
        }
        function0.invoke();
        final Ref$IntRef ref$IntRef = new Ref$IntRef();
        ref$IntRef.element = -96;
        final Intent intent = new Intent();
        intent.setAction("com.samsung.dressroom.intent.action.SHOW_LOCK_EDITOR");
        SystemUIWallpaperBase systemUIWallpaperBase = ((KeyguardWallpaperController) this.wallpaperImageCreator.mKeyguardWallpaper).mWallpaperView;
        intent.putExtra("video_wallpaper_start_frame", systemUIWallpaperBase != null ? systemUIWallpaperBase.getCurrentPosition() : 0);
        intent.putExtra("wallpaper_index", ((PluginWallpaperManagerImpl) this.pluginWallpaperManager).getWallpaperIndex());
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
                    Log.m138d("KeyguardEditModeController", "startActivity end " + Ref$IntRef.this.element);
                }
            });
            return ref$IntRef.element != -96;
        }
        Log.m138d("KeyguardEditModeController", "startActivity Dismiss Keyguard");
        this.activityStarter.postStartActivityDismissingKeyguard(intent, 0);
        return true;
    }
}
