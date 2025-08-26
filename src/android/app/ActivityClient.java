package android.app;

import android.app.ActivityManager;
import android.content.ComponentName;
import android.content.ContentProvider;
import android.content.Intent;
import android.content.res.Configuration;
import android.graphics.Point;
import android.net.Uri;
import android.os.Bundle;
import android.os.IBinder;
import android.os.IRemoteCallback;
import android.os.PersistableBundle;
import android.os.RemoteException;
import android.os.UserHandle;
import android.util.Log;
import android.util.Singleton;
import android.view.RemoteAnimationDefinition;
import android.window.SizeConfigurationBuckets;
import com.android.internal.policy.IKeyguardDismissCallback;

/* loaded from: classes.dex */
public class ActivityClient {
    public static final String TAG = "ActivityClient";
    private static final Singleton<ActivityClient> sInstance = new Singleton<ActivityClient>() { // from class: android.app.ActivityClient.1
        /* JADX INFO: Access modifiers changed from: protected */
        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.util.Singleton
        public ActivityClient create() {
            return new ActivityClient();
        }
    };
    private static final ActivityClientControllerSingleton INTERFACE_SINGLETON = new ActivityClientControllerSingleton();

    private ActivityClient() {
    }

    public void activityIdle(IBinder iBinder, Configuration configuration, boolean z) {
        try {
            getActivityClientController().activityIdle(iBinder, configuration, z);
        } catch (RemoteException e) {
            e.rethrowFromSystemServer();
        }
    }

    public void activityResumed(IBinder iBinder, boolean z) {
        try {
            getActivityClientController().activityResumed(iBinder, z);
        } catch (RemoteException e) {
            e.rethrowFromSystemServer();
        }
    }

    public void activityRefreshed(IBinder iBinder) {
        try {
            getActivityClientController().activityRefreshed(iBinder);
        } catch (RemoteException e) {
            e.rethrowFromSystemServer();
        }
    }

    public void activityTopResumedStateLost() {
        try {
            getActivityClientController().activityTopResumedStateLost();
        } catch (RemoteException e) {
            e.rethrowFromSystemServer();
        }
    }

    public void activityPaused(IBinder iBinder) {
        try {
            getActivityClientController().activityPaused(iBinder);
        } catch (RemoteException e) {
            e.rethrowFromSystemServer();
        }
    }

    public void activityStopped(IBinder iBinder, Bundle bundle, PersistableBundle persistableBundle, CharSequence charSequence) {
        try {
            getActivityClientController().activityStopped(iBinder, bundle, persistableBundle, charSequence);
        } catch (RemoteException e) {
            e.rethrowFromSystemServer();
        }
    }

    public void activityDestroyed(IBinder iBinder) {
        try {
            getActivityClientController().activityDestroyed(iBinder);
        } catch (RemoteException e) {
            e.rethrowFromSystemServer();
        }
    }

    public void activityLocalRelaunch(IBinder iBinder) {
        try {
            getActivityClientController().activityLocalRelaunch(iBinder);
        } catch (RemoteException e) {
            e.rethrowFromSystemServer();
        }
    }

    public void activityRelaunched(IBinder iBinder) {
        try {
            getActivityClientController().activityRelaunched(iBinder);
        } catch (RemoteException e) {
            e.rethrowFromSystemServer();
        }
    }

    void reportSizeConfigurations(IBinder iBinder, SizeConfigurationBuckets sizeConfigurationBuckets) {
        try {
            getActivityClientController().reportSizeConfigurations(iBinder, sizeConfigurationBuckets);
        } catch (RemoteException e) {
            e.rethrowFromSystemServer();
        }
    }

    public boolean moveActivityTaskToBack(IBinder iBinder, boolean z) {
        try {
            return getActivityClientController().moveActivityTaskToBack(iBinder, z);
        } catch (RemoteException e) {
            throw e.rethrowFromSystemServer();
        }
    }

    boolean shouldUpRecreateTask(IBinder iBinder, String str) {
        try {
            return getActivityClientController().shouldUpRecreateTask(iBinder, str);
        } catch (RemoteException e) {
            throw e.rethrowFromSystemServer();
        }
    }

    boolean navigateUpTo(IBinder iBinder, Intent intent, String str, int i, Intent intent2) {
        try {
            return getActivityClientController().navigateUpTo(iBinder, intent, str, i, intent2);
        } catch (RemoteException e) {
            throw e.rethrowFromSystemServer();
        }
    }

    boolean releaseActivityInstance(IBinder iBinder) {
        try {
            return getActivityClientController().releaseActivityInstance(iBinder);
        } catch (RemoteException e) {
            throw e.rethrowFromSystemServer();
        }
    }

    public boolean finishActivity(IBinder iBinder, int i, Intent intent, int i2) {
        try {
            return getActivityClientController().finishActivity(iBinder, i, intent, i2);
        } catch (RemoteException e) {
            throw e.rethrowFromSystemServer();
        }
    }

    boolean finishActivityAffinity(IBinder iBinder) {
        try {
            return getActivityClientController().finishActivityAffinity(iBinder);
        } catch (RemoteException e) {
            throw e.rethrowFromSystemServer();
        }
    }

    void finishSubActivity(IBinder iBinder, String str, int i) {
        try {
            getActivityClientController().finishSubActivity(iBinder, str, i);
        } catch (RemoteException e) {
            e.rethrowFromSystemServer();
        }
    }

    void setForceSendResultForMediaProjection(IBinder iBinder) {
        try {
            getActivityClientController().setForceSendResultForMediaProjection(iBinder);
        } catch (RemoteException e) {
            throw e.rethrowFromSystemServer();
        }
    }

    public boolean isTopOfTask(IBinder iBinder) {
        try {
            return getActivityClientController().isTopOfTask(iBinder);
        } catch (RemoteException e) {
            throw e.rethrowFromSystemServer();
        }
    }

    boolean willActivityBeVisible(IBinder iBinder) {
        try {
            return getActivityClientController().willActivityBeVisible(iBinder);
        } catch (RemoteException e) {
            throw e.rethrowFromSystemServer();
        }
    }

    public int getDisplayId(IBinder iBinder) {
        try {
            return getActivityClientController().getDisplayId(iBinder);
        } catch (RemoteException e) {
            throw e.rethrowFromSystemServer();
        }
    }

    public int getTaskForActivity(IBinder iBinder, boolean z) {
        try {
            return getActivityClientController().getTaskForActivity(iBinder, z);
        } catch (RemoteException e) {
            throw e.rethrowFromSystemServer();
        }
    }

    public Configuration getTaskConfiguration(IBinder iBinder) {
        try {
            return getActivityClientController().getTaskConfiguration(iBinder);
        } catch (RemoteException e) {
            throw e.rethrowFromSystemServer();
        }
    }

    public IBinder getActivityTokenBelow(IBinder iBinder) {
        try {
            return getActivityClientController().getActivityTokenBelow(iBinder);
        } catch (RemoteException e) {
            throw e.rethrowFromSystemServer();
        }
    }

    ComponentName getCallingActivity(IBinder iBinder) {
        try {
            return getActivityClientController().getCallingActivity(iBinder);
        } catch (RemoteException e) {
            throw e.rethrowFromSystemServer();
        }
    }

    String getCallingPackage(IBinder iBinder) {
        try {
            return getActivityClientController().getCallingPackage(iBinder);
        } catch (RemoteException e) {
            throw e.rethrowFromSystemServer();
        }
    }

    public int getLaunchedFromUid(IBinder iBinder) {
        try {
            return getActivityClientController().getLaunchedFromUid(iBinder);
        } catch (RemoteException e) {
            throw e.rethrowFromSystemServer();
        }
    }

    public String getLaunchedFromPackage(IBinder iBinder) {
        try {
            return getActivityClientController().getLaunchedFromPackage(iBinder);
        } catch (RemoteException e) {
            throw e.rethrowFromSystemServer();
        }
    }

    public int getActivityCallerUid(IBinder iBinder, IBinder iBinder2) {
        try {
            return getActivityClientController().getActivityCallerUid(iBinder, iBinder2);
        } catch (RemoteException e) {
            throw e.rethrowFromSystemServer();
        }
    }

    public String getActivityCallerPackage(IBinder iBinder, IBinder iBinder2) {
        try {
            return getActivityClientController().getActivityCallerPackage(iBinder, iBinder2);
        } catch (RemoteException e) {
            throw e.rethrowFromSystemServer();
        }
    }

    public int checkActivityCallerContentUriPermission(IBinder iBinder, IBinder iBinder2, Uri uri, int i) {
        try {
            return getActivityClientController().checkActivityCallerContentUriPermission(iBinder, iBinder2, ContentProvider.getUriWithoutUserId(uri), i, ContentProvider.getUserIdFromUri(uri, UserHandle.getCallingUserId()));
        } catch (RemoteException e) {
            throw e.rethrowFromSystemServer();
        }
    }

    public void setRequestedOrientation(IBinder iBinder, int i) {
        try {
            getActivityClientController().setRequestedOrientation(iBinder, i);
        } catch (RemoteException e) {
            e.rethrowFromSystemServer();
        }
    }

    int getRequestedOrientation(IBinder iBinder) {
        try {
            return getActivityClientController().getRequestedOrientation(iBinder);
        } catch (RemoteException e) {
            throw e.rethrowFromSystemServer();
        }
    }

    boolean convertFromTranslucent(IBinder iBinder) {
        try {
            return getActivityClientController().convertFromTranslucent(iBinder);
        } catch (RemoteException e) {
            throw e.rethrowFromSystemServer();
        }
    }

    boolean convertFromTranslucent(IBinder iBinder, boolean z) {
        try {
            return getActivityClientController().convertFromTranslucentOp(iBinder, z);
        } catch (RemoteException e) {
            throw e.rethrowFromSystemServer();
        }
    }

    boolean convertToTranslucent(IBinder iBinder, Bundle bundle) {
        try {
            return getActivityClientController().convertToTranslucent(iBinder, bundle);
        } catch (RemoteException e) {
            throw e.rethrowFromSystemServer();
        }
    }

    void reportActivityFullyDrawn(IBinder iBinder, boolean z) {
        try {
            getActivityClientController().reportActivityFullyDrawn(iBinder, z);
        } catch (RemoteException e) {
            e.rethrowFromSystemServer();
        }
    }

    boolean isImmersive(IBinder iBinder) {
        try {
            return getActivityClientController().isImmersive(iBinder);
        } catch (RemoteException e) {
            throw e.rethrowFromSystemServer();
        }
    }

    void setImmersive(IBinder iBinder, boolean z) {
        try {
            getActivityClientController().setImmersive(iBinder, z);
        } catch (RemoteException e) {
            e.rethrowFromSystemServer();
        }
    }

    boolean enterPictureInPictureMode(IBinder iBinder, PictureInPictureParams pictureInPictureParams) {
        try {
            return getActivityClientController().enterPictureInPictureMode(iBinder, pictureInPictureParams);
        } catch (RemoteException e) {
            throw e.rethrowFromSystemServer();
        }
    }

    void setPictureInPictureParams(IBinder iBinder, PictureInPictureParams pictureInPictureParams) {
        try {
            getActivityClientController().setPictureInPictureParams(iBinder, pictureInPictureParams);
        } catch (RemoteException e) {
            e.rethrowFromSystemServer();
        }
    }

    void setShouldDockBigOverlays(IBinder iBinder, boolean z) {
        try {
            getActivityClientController().setShouldDockBigOverlays(iBinder, z);
        } catch (RemoteException e) {
            e.rethrowFromSystemServer();
        }
    }

    void toggleFreeformWindowingMode(IBinder iBinder) {
        try {
            getActivityClientController().toggleFreeformWindowingMode(iBinder);
        } catch (RemoteException e) {
            e.rethrowFromSystemServer();
        }
    }

    void requestMultiwindowFullscreen(IBinder iBinder, int i, IRemoteCallback iRemoteCallback) {
        try {
            getActivityClientController().requestMultiwindowFullscreen(iBinder, i, iRemoteCallback);
        } catch (RemoteException e) {
            e.rethrowFromSystemServer();
        }
    }

    void startLockTaskModeByToken(IBinder iBinder) {
        try {
            getActivityClientController().startLockTaskModeByToken(iBinder);
        } catch (RemoteException e) {
            e.rethrowFromSystemServer();
        }
    }

    void stopLockTaskModeByToken(IBinder iBinder) {
        try {
            getActivityClientController().stopLockTaskModeByToken(iBinder);
        } catch (RemoteException e) {
            e.rethrowFromSystemServer();
        }
    }

    void showLockTaskEscapeMessage(IBinder iBinder) {
        try {
            getActivityClientController().showLockTaskEscapeMessage(iBinder);
        } catch (RemoteException e) {
            e.rethrowFromSystemServer();
        }
    }

    void setTaskDescription(IBinder iBinder, ActivityManager.TaskDescription taskDescription) {
        try {
            getActivityClientController().setTaskDescription(iBinder, taskDescription);
        } catch (RemoteException e) {
            e.rethrowFromSystemServer();
        }
    }

    boolean showAssistFromActivity(IBinder iBinder, Bundle bundle) {
        try {
            return getActivityClientController().showAssistFromActivity(iBinder, bundle);
        } catch (RemoteException e) {
            throw e.rethrowFromSystemServer();
        }
    }

    boolean isRootVoiceInteraction(IBinder iBinder) {
        try {
            return getActivityClientController().isRootVoiceInteraction(iBinder);
        } catch (RemoteException e) {
            throw e.rethrowFromSystemServer();
        }
    }

    void startLocalVoiceInteraction(IBinder iBinder, Bundle bundle) {
        try {
            getActivityClientController().startLocalVoiceInteraction(iBinder, bundle);
        } catch (RemoteException e) {
            e.rethrowFromSystemServer();
        }
    }

    void stopLocalVoiceInteraction(IBinder iBinder) {
        try {
            getActivityClientController().stopLocalVoiceInteraction(iBinder);
        } catch (RemoteException e) {
            e.rethrowFromSystemServer();
        }
    }

    void setShowWhenLocked(IBinder iBinder, boolean z) {
        try {
            getActivityClientController().setShowWhenLocked(iBinder, z);
        } catch (RemoteException e) {
            e.rethrowFromSystemServer();
        }
    }

    void setInheritShowWhenLocked(IBinder iBinder, boolean z) {
        try {
            getActivityClientController().setInheritShowWhenLocked(iBinder, z);
        } catch (RemoteException e) {
            e.rethrowFromSystemServer();
        }
    }

    void setTurnScreenOn(IBinder iBinder, boolean z) {
        try {
            getActivityClientController().setTurnScreenOn(iBinder, z);
        } catch (RemoteException e) {
            e.rethrowFromSystemServer();
        }
    }

    void setAllowCrossUidActivitySwitchFromBelow(IBinder iBinder, boolean z) {
        try {
            getActivityClientController().setAllowCrossUidActivitySwitchFromBelow(iBinder, z);
        } catch (RemoteException e) {
            e.rethrowFromSystemServer();
        }
    }

    int setVrMode(IBinder iBinder, boolean z, ComponentName componentName) {
        try {
            return getActivityClientController().setVrMode(iBinder, z, componentName);
        } catch (RemoteException e) {
            throw e.rethrowFromSystemServer();
        }
    }

    void overrideActivityTransition(IBinder iBinder, boolean z, int i, int i2, int i3) {
        try {
            getActivityClientController().overrideActivityTransition(iBinder, z, i, i2, i3);
        } catch (RemoteException e) {
            e.rethrowFromSystemServer();
        }
    }

    void clearOverrideActivityTransition(IBinder iBinder, boolean z) {
        try {
            getActivityClientController().clearOverrideActivityTransition(iBinder, z);
        } catch (RemoteException e) {
            e.rethrowFromSystemServer();
        }
    }

    void overridePendingTransition(IBinder iBinder, String str, int i, int i2, int i3) {
        try {
            getActivityClientController().overridePendingTransition(iBinder, str, i, i2, i3);
        } catch (RemoteException e) {
            e.rethrowFromSystemServer();
        }
    }

    void overridePendingTaskTransition(IBinder iBinder, String str, int i, int i2) {
        try {
            getActivityClientController().overridePendingTaskTransition(iBinder, str, i, i2);
        } catch (RemoteException e) {
            e.rethrowFromSystemServer();
        }
    }

    void adjustPopOverOptions(IBinder iBinder, int[] iArr, int[] iArr2, Point[] pointArr, int[] iArr3) {
        try {
            getActivityClientController().adjustPopOverOptions(iBinder, iArr, iArr2, pointArr, iArr3);
        } catch (RemoteException e) {
            e.rethrowFromSystemServer();
        }
    }

    void setRecentsScreenshotEnabled(IBinder iBinder, boolean z) {
        try {
            getActivityClientController().setRecentsScreenshotEnabled(iBinder, z);
        } catch (RemoteException e) {
            e.rethrowFromSystemServer();
        }
    }

    public void invalidateHomeTaskSnapshot(IBinder iBinder) {
        try {
            getActivityClientController().invalidateHomeTaskSnapshot(iBinder);
        } catch (RemoteException e) {
            e.rethrowFromSystemServer();
        }
    }

    void dismissKeyguard(IBinder iBinder, IKeyguardDismissCallback iKeyguardDismissCallback, CharSequence charSequence) {
        try {
            getActivityClientController().dismissKeyguard(iBinder, iKeyguardDismissCallback, charSequence);
        } catch (RemoteException e) {
            e.rethrowFromSystemServer();
        }
    }

    void registerRemoteAnimations(IBinder iBinder, RemoteAnimationDefinition remoteAnimationDefinition) {
        try {
            getActivityClientController().registerRemoteAnimations(iBinder, remoteAnimationDefinition);
        } catch (RemoteException e) {
            e.rethrowFromSystemServer();
        }
    }

    void unregisterRemoteAnimations(IBinder iBinder) {
        try {
            getActivityClientController().unregisterRemoteAnimations(iBinder);
        } catch (RemoteException e) {
            e.rethrowFromSystemServer();
        }
    }

    void onBackPressed(IBinder iBinder, IRequestFinishCallback iRequestFinishCallback) {
        try {
            getActivityClientController().onBackPressed(iBinder, iRequestFinishCallback);
        } catch (RemoteException e) {
            e.rethrowFromSystemServer();
        }
    }

    void reportSplashScreenAttached(IBinder iBinder) {
        try {
            getActivityClientController().splashScreenAttached(iBinder);
        } catch (RemoteException e) {
            e.rethrowFromSystemServer();
        }
    }

    void enableTaskLocaleOverride(IBinder iBinder) {
        try {
            getActivityClientController().enableTaskLocaleOverride(iBinder);
        } catch (RemoteException e) {
            e.rethrowFromSystemServer();
        }
    }

    public boolean isRequestedToLaunchInTaskFragment(IBinder iBinder, IBinder iBinder2) {
        try {
            return getActivityClientController().isRequestedToLaunchInTaskFragment(iBinder, iBinder2);
        } catch (RemoteException e) {
            throw e.rethrowFromSystemServer();
        }
    }

    void setActivityRecordInputSinkEnabled(IBinder iBinder, boolean z) {
        try {
            getActivityClientController().setActivityRecordInputSinkEnabled(iBinder, z);
        } catch (RemoteException e) {
            e.rethrowFromSystemServer();
        }
    }

    public static ActivityClient getInstance() {
        return sInstance.get();
    }

    public boolean shouldPreloadHardwareRenderer(int i) {
        try {
            return getActivityClientController().shouldPreloadHardwareRenderer(i);
        } catch (RemoteException e) {
            Log.e(TAG, "shouldPreloadHardwareRenderer failed ", e);
            return false;
        }
    }

    public static IActivityClientController setActivityClientController(IActivityClientController iActivityClientController) {
        INTERFACE_SINGLETON.mKnownInstance = iActivityClientController;
        return iActivityClientController;
    }

    private static IActivityClientController getActivityClientController() {
        ActivityClientControllerSingleton activityClientControllerSingleton = INTERFACE_SINGLETON;
        IActivityClientController iActivityClientController = activityClientControllerSingleton.mKnownInstance;
        return iActivityClientController != null ? iActivityClientController : activityClientControllerSingleton.get();
    }

    private static class ActivityClientControllerSingleton extends Singleton<IActivityClientController> {
        IActivityClientController mKnownInstance;

        private ActivityClientControllerSingleton() {
        }

        /* JADX INFO: Access modifiers changed from: protected */
        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.util.Singleton
        public IActivityClientController create() {
            try {
                return ActivityTaskManager.getService().getActivityClientController();
            } catch (RemoteException e) {
                throw e.rethrowFromSystemServer();
            }
        }
    }
}
