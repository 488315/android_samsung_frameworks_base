package com.android.systemui.camera;

import android.app.ActivityManager;
import android.app.ActivityOptions;
import android.app.IActivityTaskManager;
import android.app.IApplicationThread;
import android.app.ProfilerInfo;
import android.app.admin.DevicePolicyManager;
import android.content.ContentResolver;
import android.content.Context;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.content.pm.PackageManager;
import android.content.pm.ResolveInfo;
import android.os.IBinder;
import android.os.RemoteException;
import android.util.Log;
import android.view.Display;
import com.android.systemui.ActivityIntentHelper;
import com.android.systemui.Dependency;
import com.android.systemui.LsRune;
import com.android.systemui.keyguard.DisplayLifecycle;
import com.android.systemui.keyguard.domain.interactor.KeyguardInteractor;
import com.android.systemui.keyguard.shared.model.CameraLaunchType;
import com.android.systemui.plugins.ActivityStarter;
import com.android.systemui.qp.util.SubscreenUtil;
import com.android.systemui.statusbar.KeyguardShortcutManager;
import com.android.systemui.statusbar.NotificationLockscreenUserManager;
import com.android.systemui.statusbar.NotificationLockscreenUserManagerImpl;
import com.android.systemui.statusbar.phone.CentralSurfaces;
import com.android.systemui.statusbar.phone.CentralSurfacesImpl;
import com.android.systemui.statusbar.phone.StatusBarKeyguardViewManager;
import com.android.systemui.statusbar.policy.KeyguardStateController;
import com.android.systemui.statusbar.policy.KeyguardStateControllerImpl;
import com.android.systemui.user.domain.interactor.SelectedUserInteractor;
import java.util.concurrent.Executor;
import kotlin.jvm.internal.DefaultConstructorMarker;

/* loaded from: classes.dex */
public final class CameraGestureHelper {
    public final ActivityIntentHelper activityIntentHelper;
    public final ActivityStarter activityStarter;
    public final IActivityTaskManager activityTaskManager;
    public final CameraIntentsWrapper cameraIntents;
    public final CentralSurfaces centralSurfaces;
    public final ContentResolver contentResolver;
    public final Context context;
    public final DevicePolicyManager devicePolicyManager;
    public final KeyguardInteractor keyguardInteractor;
    public final KeyguardStateController keyguardStateController;
    public final NotificationLockscreenUserManager lockscreenUserManager;
    public final PackageManager packageManager;
    public final SelectedUserInteractor selectedUserInteractor;
    public final StatusBarKeyguardViewManager statusBarKeyguardViewManager;
    public final Executor uiExecutor;

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

    public CameraGestureHelper(Context context, CentralSurfaces centralSurfaces, KeyguardInteractor keyguardInteractor, KeyguardStateController keyguardStateController, StatusBarKeyguardViewManager statusBarKeyguardViewManager, PackageManager packageManager, ActivityManager activityManager, ActivityStarter activityStarter, ActivityIntentHelper activityIntentHelper, IActivityTaskManager iActivityTaskManager, CameraIntentsWrapper cameraIntentsWrapper, ContentResolver contentResolver, Executor executor, SelectedUserInteractor selectedUserInteractor, DevicePolicyManager devicePolicyManager, NotificationLockscreenUserManager notificationLockscreenUserManager) {
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
        this.devicePolicyManager = devicePolicyManager;
        this.lockscreenUserManager = notificationLockscreenUserManager;
    }

    public final boolean canCameraGestureBeLaunched(int i) {
        ActivityInfo activityInfo;
        DevicePolicyManager devicePolicyManager = this.devicePolicyManager;
        NotificationLockscreenUserManagerImpl notificationLockscreenUserManagerImpl = (NotificationLockscreenUserManagerImpl) this.lockscreenUserManager;
        String str = null;
        if (devicePolicyManager.getCameraDisabled(null, notificationLockscreenUserManagerImpl.mCurrentUserId)) {
            return false;
        }
        if (((KeyguardStateControllerImpl) this.keyguardStateController).mShowing && this.statusBarKeyguardViewManager.isSecure() && (this.devicePolicyManager.getKeyguardDisabledFeatures(null, notificationLockscreenUserManagerImpl.mCurrentUserId) & 2) != 0) {
            return false;
        }
        PackageManager packageManager = this.packageManager;
        SelectedUserInteractor selectedUserInteractor = this.selectedUserInteractor;
        selectedUserInteractor.getSelectedUserId();
        ResolveInfo resolveInfoResolveActivityAsUser = packageManager.resolveActivityAsUser(getStartCameraIntent(), 65536, selectedUserInteractor.getSelectedUserId());
        if (resolveInfoResolveActivityAsUser != null && (activityInfo = resolveInfoResolveActivityAsUser.activityInfo) != null) {
            str = activityInfo.packageName;
        }
        if (str == null) {
            return false;
        }
        if (i == 0) {
            return !((CentralSurfacesImpl) this.centralSurfaces).isForegroundComponentName(resolveInfoResolveActivityAsUser.activityInfo.getComponentName());
        }
        return true;
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

    public final void launchCamera(int i) {
        SelectedUserInteractor selectedUserInteractor = this.selectedUserInteractor;
        selectedUserInteractor.getSelectedUserId();
        final Intent startCameraIntent = getStartCameraIntent();
        startCameraIntent.putExtra("com.android.systemui.camera_launch_source", i);
        boolean zWouldLaunchResolverActivity = this.activityIntentHelper.wouldLaunchResolverActivity(selectedUserInteractor.getSelectedUserId(), startCameraIntent);
        CameraLaunchType cameraLaunchType = CameraLaunchType.POWER_DOUBLE_TAP;
        this.keyguardInteractor.getClass();
        final boolean z = cameraLaunchType == KeyguardInteractor.cameraLaunchSourceIntToType(i);
        startCameraIntent.putExtra("isQuickLaunchMode", z);
        CameraIntents.Companion.getClass();
        KeyguardShortcutManager.Companion.getClass();
        if (!startCameraIntent.equals(KeyguardShortcutManager.SECURE_CAMERA_INTENT) || zWouldLaunchResolverActivity) {
            startCameraIntent.putExtra("isSecure", false);
            if (((CentralSurfacesImpl) this.centralSurfaces).isForegroundComponentName(startCameraIntent.getComponent())) {
                startCameraIntent.setFlags(270532608);
            } else {
                startCameraIntent.addFlags(805371904);
                if (((KeyguardStateControllerImpl) this.keyguardStateController).mShowing || !z) {
                    startCameraIntent.addFlags(67141632);
                }
            }
            this.activityStarter.startCameraActivity(startCameraIntent, false, new ActivityStarter.Callback() { // from class: com.android.systemui.camera.CameraGestureHelper.launchCamera.2
                @Override // com.android.systemui.plugins.ActivityStarter.Callback
                public final void onActivityStarted(int i2) {
                }
            });
        } else {
            this.uiExecutor.execute(new Runnable() { // from class: com.android.systemui.camera.CameraGestureHelper.launchCamera.1
                @Override // java.lang.Runnable
                public final void run() {
                    int displayId;
                    ActivityOptions activityOptionsMakeBasic = ActivityOptions.makeBasic();
                    activityOptionsMakeBasic.setDisallowEnterPictureInPictureWhileLaunching(true);
                    activityOptionsMakeBasic.setRotationAnimationHint(3);
                    startCameraIntent.collectExtraIntentKeys();
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
                            display.getClass();
                            displayId = display.getDisplayId();
                        } else {
                            Context context = this.context;
                            ((SubscreenUtil) Dependency.sDependency.getDependencyInner(SubscreenUtil.class)).getClass();
                            displayId = SubscreenUtil.getSubDisplay(context).getDisplayId();
                        }
                        this.activityTaskManager.resumeAppSwitches();
                        activityOptionsMakeBasic.setForceLaunchWindowingMode(1);
                        activityOptionsMakeBasic.setLaunchDisplayId(displayId);
                        CameraGestureHelper cameraGestureHelper = this;
                        IActivityTaskManager iActivityTaskManager = cameraGestureHelper.activityTaskManager;
                        String basePackageName = cameraGestureHelper.context.getBasePackageName();
                        String attributionTag = this.context.getAttributionTag();
                        Intent intent = startCameraIntent;
                        iActivityTaskManager.startActivityAsUser((IApplicationThread) null, basePackageName, attributionTag, intent, intent.resolveTypeIfNeeded(this.contentResolver), (IBinder) null, (String) null, 0, 268435456, (ProfilerInfo) null, activityOptionsMakeBasic.toBundle(), this.selectedUserInteractor.getSelectedUserId());
                    } catch (RemoteException e) {
                        Log.w("CameraGestureHelper", "Unable to start camera activity", e);
                    }
                }
            });
        }
        this.statusBarKeyguardViewManager.readyForKeyguardDone();
    }
}
