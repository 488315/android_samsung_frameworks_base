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
import com.android.systemui.ActivityIntentHelper;
import com.android.systemui.Dependency;
import com.android.systemui.LsRune;
import com.android.systemui.keyguard.DisplayLifecycle;
import com.android.systemui.keyguard.domain.interactor.KeyguardInteractor;
import com.android.systemui.keyguard.shared.model.CameraLaunchSourceModel;
import com.android.systemui.plugins.ActivityStarter;
import com.android.systemui.qp.util.SubscreenUtil;
import com.android.systemui.statusbar.KeyguardShortcutManager;
import com.android.systemui.statusbar.phone.CentralSurfaces;
import com.android.systemui.statusbar.phone.CentralSurfacesImpl;
import com.android.systemui.statusbar.phone.StatusBarKeyguardViewManager;
import com.android.systemui.statusbar.policy.KeyguardStateController;
import com.android.systemui.statusbar.policy.KeyguardStateControllerImpl;
import com.android.systemui.user.domain.interactor.SelectedUserInteractor;
import com.samsung.android.desktopsystemui.sharedlib.system.QuickStepContract;
import java.util.concurrent.Executor;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.Intrinsics;

/* compiled from: qb/89523975 427a50d40ec74a85ca352b86f77450b1c52ece5389e11158752b0d641a3a5098 */
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
    public final SelectedUserInteractor selectedUserInteractor;
    public final StatusBarKeyguardViewManager statusBarKeyguardViewManager;
    public final Executor uiExecutor;

    /* compiled from: qb/89523975 427a50d40ec74a85ca352b86f77450b1c52ece5389e11158752b0d641a3a5098 */
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

    public CameraGestureHelper(Context context, CentralSurfaces centralSurfaces, KeyguardInteractor keyguardInteractor, KeyguardStateController keyguardStateController, StatusBarKeyguardViewManager statusBarKeyguardViewManager, PackageManager packageManager, ActivityManager activityManager, ActivityStarter activityStarter, ActivityIntentHelper activityIntentHelper, IActivityTaskManager iActivityTaskManager, CameraIntentsWrapper cameraIntentsWrapper, ContentResolver contentResolver, Executor executor, SelectedUserInteractor selectedUserInteractor) {
        this.context = context;
        this.centralSurfaces = centralSurfaces;
        this.keyguardInteractor = keyguardInteractor;
        this.keyguardStateController = keyguardStateController;
        this.statusBarKeyguardViewManager = statusBarKeyguardViewManager;
        this.packageManager = packageManager;
        this.activityStarter = activityStarter;
        this.activityIntentHelper = activityIntentHelper;
        this.activityTaskManager = iActivityTaskManager;
        this.cameraIntents = cameraIntentsWrapper;
        this.contentResolver = contentResolver;
        this.uiExecutor = executor;
        this.selectedUserInteractor = selectedUserInteractor;
    }

    public final Intent getStartCameraIntent(int i) {
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

    public final void launchCamera(int i) {
        SelectedUserInteractor selectedUserInteractor = this.selectedUserInteractor;
        final Intent startCameraIntent = getStartCameraIntent(selectedUserInteractor.getSelectedUserId(false));
        startCameraIntent.putExtra("com.android.systemui.camera_launch_source", i);
        boolean wouldLaunchResolverActivity = this.activityIntentHelper.wouldLaunchResolverActivity(selectedUserInteractor.getSelectedUserId(false), startCameraIntent);
        CameraLaunchSourceModel cameraLaunchSourceModel = CameraLaunchSourceModel.POWER_DOUBLE_TAP;
        this.keyguardInteractor.getClass();
        final boolean z = cameraLaunchSourceModel == KeyguardInteractor.cameraLaunchSourceIntToModel(i);
        startCameraIntent.putExtra("isQuickLaunchMode", z);
        CameraIntents.Companion.getClass();
        KeyguardShortcutManager.Companion.getClass();
        boolean equals = startCameraIntent.equals(KeyguardShortcutManager.SECURE_CAMERA_INTENT);
        CentralSurfaces centralSurfaces = this.centralSurfaces;
        if (!equals || wouldLaunchResolverActivity) {
            startCameraIntent.putExtra("isSecure", false);
            if (((CentralSurfacesImpl) centralSurfaces).isForegroundComponentName(startCameraIntent.getComponent())) {
                startCameraIntent.setFlags(270532608);
            } else {
                startCameraIntent.addFlags(805371904);
                if (((KeyguardStateControllerImpl) this.keyguardStateController).mShowing || !z) {
                    startCameraIntent.addFlags(67141632);
                }
            }
            this.activityStarter.startCameraActivity(startCameraIntent, false, new ActivityStarter.Callback() { // from class: com.android.systemui.camera.CameraGestureHelper$launchCamera$2
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
                        startCameraIntent.putExtra("isSecure", true);
                        if (((CentralSurfacesImpl) this.centralSurfaces).isForegroundComponentName(startCameraIntent.getComponent())) {
                            startCameraIntent.setFlags(270532608);
                        } else {
                            startCameraIntent.addFlags(805371904);
                            if (((KeyguardStateControllerImpl) this.keyguardStateController).mShowing || !z) {
                                startCameraIntent.addFlags(67141632);
                            }
                        }
                        if (!LsRune.SUBSCREEN_LARGE_FRONT_SUB_DISPLAY || ((DisplayLifecycle) Dependency.sDependency.getDependencyInner(DisplayLifecycle.class)).mIsFolderOpened) {
                            Display display = this.context.getDisplay();
                            Intrinsics.checkNotNull(display);
                            displayId = display.getDisplayId();
                        } else {
                            Context context = this.context;
                            ((SubscreenUtil) Dependency.sDependency.getDependencyInner(SubscreenUtil.class)).getClass();
                            displayId = SubscreenUtil.getSubDisplay(context).getDisplayId();
                        }
                        this.activityTaskManager.resumeAppSwitches();
                        makeBasic.setForceLaunchWindowingMode(1);
                        makeBasic.setLaunchDisplayId(displayId);
                        CameraGestureHelper cameraGestureHelper = this;
                        IActivityTaskManager iActivityTaskManager = cameraGestureHelper.activityTaskManager;
                        String basePackageName = cameraGestureHelper.context.getBasePackageName();
                        String attributionTag = this.context.getAttributionTag();
                        Intent intent = startCameraIntent;
                        iActivityTaskManager.startActivityAsUser((IApplicationThread) null, basePackageName, attributionTag, intent, intent.resolveTypeIfNeeded(this.contentResolver), (IBinder) null, (String) null, 0, QuickStepContract.SYSUI_STATE_NAV_BAR_VIS_GONE, (ProfilerInfo) null, makeBasic.toBundle(), this.selectedUserInteractor.getSelectedUserId(true));
                    } catch (RemoteException e) {
                        Log.w("CameraGestureHelper", "Unable to start camera activity", e);
                    }
                }
            });
        }
        ((CentralSurfacesImpl) centralSurfaces).mMessageRouter.sendMessageDelayed(1003, 5000L);
        this.statusBarKeyguardViewManager.readyForKeyguardDone();
    }
}
