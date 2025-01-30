package com.android.systemui.camera;

import android.app.ActivityManager;
import android.app.ActivityOptions;
import android.app.IActivityTaskManager;
import android.app.IApplicationThread;
import android.app.ProfilerInfo;
import android.content.ContentResolver;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.IBinder;
import android.os.RemoteException;
import android.util.Log;
import android.view.Display;
import com.android.keyguard.KeyguardUpdateMonitor;
import com.android.systemui.ActivityIntentHelper;
import com.android.systemui.Dependency;
import com.android.systemui.LsRune;
import com.android.systemui.keyguard.DisplayLifecycle;
import com.android.systemui.keyguard.domain.interactor.KeyguardInteractor;
import com.android.systemui.keyguard.shared.model.CameraLaunchSourceModel;
import com.android.systemui.p014qp.util.SubscreenUtil;
import com.android.systemui.plugins.ActivityStarter;
import com.android.systemui.settings.UserTracker;
import com.android.systemui.settings.UserTrackerImpl;
import com.android.systemui.statusbar.KeyguardShortcutManager;
import com.android.systemui.statusbar.phone.CentralSurfaces;
import com.android.systemui.statusbar.phone.CentralSurfacesImpl;
import com.android.systemui.statusbar.policy.KeyguardStateController;
import com.android.systemui.statusbar.policy.KeyguardStateControllerImpl;
import com.android.systemui.util.concurrency.MessageRouterImpl;
import com.samsung.android.desktopsystemui.sharedlib.system.QuickStepContract;
import java.util.concurrent.Executor;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.Intrinsics;

/* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
/* loaded from: classes.dex */
public final class CameraGestureHelper {
    public final ActivityIntentHelper activityIntentHelper;
    public final ActivityStarter activityStarter;
    public final IActivityTaskManager activityTaskManager;
    public final CameraIntentsWrapper cameraIntents;
    public final CentralSurfaces centralSurfaces;
    public final ContentResolver contentResolver;
    public final Context context;
    public final KeyguardInteractor keyguardInteractor;
    public final KeyguardStateController keyguardStateController;
    public final PackageManager packageManager;
    public final Executor uiExecutor;
    public final UserTracker userTracker;

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

    public CameraGestureHelper(Context context, CentralSurfaces centralSurfaces, KeyguardInteractor keyguardInteractor, KeyguardStateController keyguardStateController, PackageManager packageManager, ActivityManager activityManager, ActivityStarter activityStarter, ActivityIntentHelper activityIntentHelper, IActivityTaskManager iActivityTaskManager, CameraIntentsWrapper cameraIntentsWrapper, ContentResolver contentResolver, Executor executor, UserTracker userTracker) {
        this.context = context;
        this.centralSurfaces = centralSurfaces;
        this.keyguardInteractor = keyguardInteractor;
        this.keyguardStateController = keyguardStateController;
        this.packageManager = packageManager;
        this.activityStarter = activityStarter;
        this.activityIntentHelper = activityIntentHelper;
        this.activityTaskManager = iActivityTaskManager;
        this.cameraIntents = cameraIntentsWrapper;
        this.contentResolver = contentResolver;
        this.uiExecutor = executor;
        this.userTracker = userTracker;
    }

    public final Intent getStartCameraIntent() {
        KeyguardStateControllerImpl keyguardStateControllerImpl = (KeyguardStateControllerImpl) this.keyguardStateController;
        boolean z = keyguardStateControllerImpl.mCanDismissLockScreen;
        boolean z2 = keyguardStateControllerImpl.mSecure;
        CameraIntentsWrapper cameraIntentsWrapper = this.cameraIntents;
        if (!z2 || z) {
            cameraIntentsWrapper.getClass();
            CameraIntents.Companion.getClass();
            KeyguardShortcutManager.Companion.getClass();
            return KeyguardShortcutManager.INSECURE_CAMERA_INTENT;
        }
        cameraIntentsWrapper.getClass();
        CameraIntents.Companion.getClass();
        KeyguardShortcutManager.Companion.getClass();
        return KeyguardShortcutManager.SECURE_CAMERA_INTENT;
    }

    public final void launchCamera(int i, final Intent intent) {
        intent.putExtra("com.android.systemui.camera_launch_source", i);
        boolean wouldLaunchResolverActivity = this.activityIntentHelper.wouldLaunchResolverActivity(KeyguardUpdateMonitor.getCurrentUser(), intent);
        CameraLaunchSourceModel cameraLaunchSourceModel = CameraLaunchSourceModel.POWER_DOUBLE_TAP;
        this.keyguardInteractor.getClass();
        final boolean z = cameraLaunchSourceModel == KeyguardInteractor.cameraLaunchSourceIntToModel(i);
        intent.putExtra("isQuickLaunchMode", z);
        KeyguardStateControllerImpl keyguardStateControllerImpl = (KeyguardStateControllerImpl) this.keyguardStateController;
        boolean z2 = (true ^ keyguardStateControllerImpl.mCanDismissLockScreen) & keyguardStateControllerImpl.mSecure & (!keyguardStateControllerImpl.mTrusted);
        CentralSurfaces centralSurfaces = this.centralSurfaces;
        if (!z2 || wouldLaunchResolverActivity) {
            Log.i("CameraGestureHelper", "launch insecure Camera - ");
            intent.putExtra("isSecure", false);
            if (((CentralSurfacesImpl) centralSurfaces).isForegroundComponentName(intent.getComponent())) {
                intent.setFlags(270532608);
            } else {
                intent.addFlags(805371904);
                if (keyguardStateControllerImpl.mShowing || !z) {
                    intent.addFlags(67141632);
                }
            }
            this.activityStarter.startCameraActivity(intent, false, new ActivityStarter.Callback() { // from class: com.android.systemui.camera.CameraGestureHelper$launchCamera$2
                @Override // com.android.systemui.plugins.ActivityStarter.Callback
                public final void onActivityStarted(int i2) {
                }
            });
        } else {
            this.uiExecutor.execute(new Runnable() { // from class: com.android.systemui.camera.CameraGestureHelper$launchCamera$1
                @Override // java.lang.Runnable
                public final void run() {
                    int displayId;
                    ActivityOptions makeBasic = ActivityOptions.makeBasic();
                    makeBasic.setDisallowEnterPictureInPictureWhileLaunching(true);
                    makeBasic.setRotationAnimationHint(3);
                    try {
                        Log.i("CameraGestureHelper", "launch secure Camera - ");
                        intent.putExtra("isSecure", true);
                        if (((CentralSurfacesImpl) this.centralSurfaces).isForegroundComponentName(intent.getComponent())) {
                            intent.setFlags(270532608);
                        } else {
                            intent.addFlags(805371904);
                            if (((KeyguardStateControllerImpl) this.keyguardStateController).mShowing || !z) {
                                intent.addFlags(67141632);
                            }
                        }
                        if (!LsRune.SUPPORT_LARGE_FRONT_SUB_DISPLAY || ((DisplayLifecycle) Dependency.get(DisplayLifecycle.class)).mIsFolderOpened) {
                            Display display = this.context.getDisplay();
                            Intrinsics.checkNotNull(display);
                            displayId = display.getDisplayId();
                        } else {
                            Context context = this.context;
                            ((SubscreenUtil) Dependency.get(SubscreenUtil.class)).getClass();
                            displayId = SubscreenUtil.getSubDisplay(context).getDisplayId();
                        }
                        this.activityTaskManager.requestWaitingForOccluding(displayId);
                        this.activityTaskManager.resumeAppSwitches();
                        makeBasic.setForceLaunchWindowingMode(1);
                        makeBasic.setLaunchDisplayId(displayId);
                        CameraGestureHelper cameraGestureHelper = this;
                        IActivityTaskManager iActivityTaskManager = cameraGestureHelper.activityTaskManager;
                        String basePackageName = cameraGestureHelper.context.getBasePackageName();
                        String attributionTag = this.context.getAttributionTag();
                        Intent intent2 = intent;
                        iActivityTaskManager.startActivityAsUser((IApplicationThread) null, basePackageName, attributionTag, intent2, intent2.resolveTypeIfNeeded(this.contentResolver), (IBinder) null, (String) null, 0, QuickStepContract.SYSUI_STATE_NAV_BAR_VIS_GONE, (ProfilerInfo) null, makeBasic.toBundle(), ((UserTrackerImpl) this.userTracker).getUserId());
                    } catch (RemoteException e) {
                        Log.w("CameraGestureHelper", "Unable to start camera activity", e);
                    }
                }
            });
        }
        CentralSurfacesImpl centralSurfacesImpl = (CentralSurfacesImpl) centralSurfaces;
        ((MessageRouterImpl) centralSurfacesImpl.mMessageRouter).sendMessageDelayed(1003, 5000L);
        centralSurfacesImpl.mStatusBarKeyguardViewManager.readyForKeyguardDone();
    }
}
