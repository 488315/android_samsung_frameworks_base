package android.app;

import android.app.ActivityManager;
import android.app.IRequestFinishCallback;
import android.content.ComponentName;
import android.content.Intent;
import android.content.res.Configuration;
import android.graphics.Point;
import android.net.Uri;
import android.os.Binder;
import android.os.Bundle;
import android.os.IBinder;
import android.os.IInterface;
import android.os.IRemoteCallback;
import android.os.Parcel;
import android.os.PersistableBundle;
import android.os.RemoteException;
import android.text.TextUtils;
import android.view.RemoteAnimationDefinition;
import android.window.SizeConfigurationBuckets;
import com.android.internal.policy.IKeyguardDismissCallback;

/* loaded from: classes.dex */
public interface IActivityClientController extends IInterface {
    public static final String DESCRIPTOR = "android.app.IActivityClientController";

    public static class Default implements IActivityClientController {
        @Override // android.app.IActivityClientController
        public void activityDestroyed(IBinder iBinder) throws RemoteException {
        }

        @Override // android.app.IActivityClientController
        public void activityIdle(IBinder iBinder, Configuration configuration, boolean z) throws RemoteException {
        }

        @Override // android.app.IActivityClientController
        public void activityLocalRelaunch(IBinder iBinder) throws RemoteException {
        }

        @Override // android.app.IActivityClientController
        public void activityPaused(IBinder iBinder) throws RemoteException {
        }

        @Override // android.app.IActivityClientController
        public void activityRefreshed(IBinder iBinder) throws RemoteException {
        }

        @Override // android.app.IActivityClientController
        public void activityRelaunched(IBinder iBinder) throws RemoteException {
        }

        @Override // android.app.IActivityClientController
        public void activityResumed(IBinder iBinder, boolean z) throws RemoteException {
        }

        @Override // android.app.IActivityClientController
        public void activityStopped(IBinder iBinder, Bundle bundle, PersistableBundle persistableBundle, CharSequence charSequence) throws RemoteException {
        }

        @Override // android.app.IActivityClientController
        public void activityTopResumedStateLost() throws RemoteException {
        }

        @Override // android.app.IActivityClientController
        public void adjustPopOverOptions(IBinder iBinder, int[] iArr, int[] iArr2, Point[] pointArr, int[] iArr3) throws RemoteException {
        }

        @Override // android.os.IInterface
        public IBinder asBinder() {
            return null;
        }

        @Override // android.app.IActivityClientController
        public int checkActivityCallerContentUriPermission(IBinder iBinder, IBinder iBinder2, Uri uri, int i, int i2) throws RemoteException {
            return 0;
        }

        @Override // android.app.IActivityClientController
        public void clearOverrideActivityTransition(IBinder iBinder, boolean z) throws RemoteException {
        }

        @Override // android.app.IActivityClientController
        public boolean convertFromTranslucent(IBinder iBinder) throws RemoteException {
            return false;
        }

        @Override // android.app.IActivityClientController
        public boolean convertFromTranslucentOp(IBinder iBinder, boolean z) throws RemoteException {
            return false;
        }

        @Override // android.app.IActivityClientController
        public boolean convertToTranslucent(IBinder iBinder, Bundle bundle) throws RemoteException {
            return false;
        }

        @Override // android.app.IActivityClientController
        public void dismissKeyguard(IBinder iBinder, IKeyguardDismissCallback iKeyguardDismissCallback, CharSequence charSequence) throws RemoteException {
        }

        @Override // android.app.IActivityClientController
        public void enableTaskLocaleOverride(IBinder iBinder) throws RemoteException {
        }

        @Override // android.app.IActivityClientController
        public boolean enterPictureInPictureMode(IBinder iBinder, PictureInPictureParams pictureInPictureParams) throws RemoteException {
            return false;
        }

        @Override // android.app.IActivityClientController
        public boolean finishActivity(IBinder iBinder, int i, Intent intent, int i2) throws RemoteException {
            return false;
        }

        @Override // android.app.IActivityClientController
        public boolean finishActivityAffinity(IBinder iBinder) throws RemoteException {
            return false;
        }

        @Override // android.app.IActivityClientController
        public void finishSubActivity(IBinder iBinder, String str, int i) throws RemoteException {
        }

        @Override // android.app.IActivityClientController
        public String getActivityCallerPackage(IBinder iBinder, IBinder iBinder2) throws RemoteException {
            return null;
        }

        @Override // android.app.IActivityClientController
        public int getActivityCallerUid(IBinder iBinder, IBinder iBinder2) throws RemoteException {
            return 0;
        }

        @Override // android.app.IActivityClientController
        public IBinder getActivityTokenBelow(IBinder iBinder) throws RemoteException {
            return null;
        }

        @Override // android.app.IActivityClientController
        public ComponentName getCallingActivity(IBinder iBinder) throws RemoteException {
            return null;
        }

        @Override // android.app.IActivityClientController
        public String getCallingPackage(IBinder iBinder) throws RemoteException {
            return null;
        }

        @Override // android.app.IActivityClientController
        public int getDisplayId(IBinder iBinder) throws RemoteException {
            return 0;
        }

        @Override // android.app.IActivityClientController
        public String getLaunchedFromPackage(IBinder iBinder) throws RemoteException {
            return null;
        }

        @Override // android.app.IActivityClientController
        public int getLaunchedFromUid(IBinder iBinder) throws RemoteException {
            return 0;
        }

        @Override // android.app.IActivityClientController
        public int getRequestedOrientation(IBinder iBinder) throws RemoteException {
            return 0;
        }

        @Override // android.app.IActivityClientController
        public Configuration getTaskConfiguration(IBinder iBinder) throws RemoteException {
            return null;
        }

        @Override // android.app.IActivityClientController
        public int getTaskForActivity(IBinder iBinder, boolean z) throws RemoteException {
            return 0;
        }

        @Override // android.app.IActivityClientController
        public void invalidateHomeTaskSnapshot(IBinder iBinder) throws RemoteException {
        }

        @Override // android.app.IActivityClientController
        public boolean isImmersive(IBinder iBinder) throws RemoteException {
            return false;
        }

        @Override // android.app.IActivityClientController
        public boolean isRequestedToLaunchInTaskFragment(IBinder iBinder, IBinder iBinder2) throws RemoteException {
            return false;
        }

        @Override // android.app.IActivityClientController
        public boolean isRootVoiceInteraction(IBinder iBinder) throws RemoteException {
            return false;
        }

        @Override // android.app.IActivityClientController
        public boolean isTopOfTask(IBinder iBinder) throws RemoteException {
            return false;
        }

        @Override // android.app.IActivityClientController
        public boolean moveActivityTaskToBack(IBinder iBinder, boolean z) throws RemoteException {
            return false;
        }

        @Override // android.app.IActivityClientController
        public boolean navigateUpTo(IBinder iBinder, Intent intent, String str, int i, Intent intent2) throws RemoteException {
            return false;
        }

        @Override // android.app.IActivityClientController
        public void onBackPressed(IBinder iBinder, IRequestFinishCallback iRequestFinishCallback) throws RemoteException {
        }

        @Override // android.app.IActivityClientController
        public void overrideActivityTransition(IBinder iBinder, boolean z, int i, int i2, int i3) throws RemoteException {
        }

        @Override // android.app.IActivityClientController
        public void overridePendingTaskTransition(IBinder iBinder, String str, int i, int i2) throws RemoteException {
        }

        @Override // android.app.IActivityClientController
        public void overridePendingTransition(IBinder iBinder, String str, int i, int i2, int i3) throws RemoteException {
        }

        @Override // android.app.IActivityClientController
        public void registerRemoteAnimations(IBinder iBinder, RemoteAnimationDefinition remoteAnimationDefinition) throws RemoteException {
        }

        @Override // android.app.IActivityClientController
        public boolean releaseActivityInstance(IBinder iBinder) throws RemoteException {
            return false;
        }

        @Override // android.app.IActivityClientController
        public void reportActivityFullyDrawn(IBinder iBinder, boolean z) throws RemoteException {
        }

        @Override // android.app.IActivityClientController
        public void reportSizeConfigurations(IBinder iBinder, SizeConfigurationBuckets sizeConfigurationBuckets) throws RemoteException {
        }

        @Override // android.app.IActivityClientController
        public void requestMultiwindowFullscreen(IBinder iBinder, int i, IRemoteCallback iRemoteCallback) throws RemoteException {
        }

        @Override // android.app.IActivityClientController
        public void setActivityRecordInputSinkEnabled(IBinder iBinder, boolean z) throws RemoteException {
        }

        @Override // android.app.IActivityClientController
        public void setAllowCrossUidActivitySwitchFromBelow(IBinder iBinder, boolean z) throws RemoteException {
        }

        @Override // android.app.IActivityClientController
        public void setForceSendResultForMediaProjection(IBinder iBinder) throws RemoteException {
        }

        @Override // android.app.IActivityClientController
        public void setImmersive(IBinder iBinder, boolean z) throws RemoteException {
        }

        @Override // android.app.IActivityClientController
        public void setInheritShowWhenLocked(IBinder iBinder, boolean z) throws RemoteException {
        }

        @Override // android.app.IActivityClientController
        public void setPictureInPictureParams(IBinder iBinder, PictureInPictureParams pictureInPictureParams) throws RemoteException {
        }

        @Override // android.app.IActivityClientController
        public void setRecentsScreenshotEnabled(IBinder iBinder, boolean z) throws RemoteException {
        }

        @Override // android.app.IActivityClientController
        public void setRequestedOrientation(IBinder iBinder, int i) throws RemoteException {
        }

        @Override // android.app.IActivityClientController
        public void setShouldDockBigOverlays(IBinder iBinder, boolean z) throws RemoteException {
        }

        @Override // android.app.IActivityClientController
        public void setShowWhenLocked(IBinder iBinder, boolean z) throws RemoteException {
        }

        @Override // android.app.IActivityClientController
        public void setTaskDescription(IBinder iBinder, ActivityManager.TaskDescription taskDescription) throws RemoteException {
        }

        @Override // android.app.IActivityClientController
        public void setTurnScreenOn(IBinder iBinder, boolean z) throws RemoteException {
        }

        @Override // android.app.IActivityClientController
        public int setVrMode(IBinder iBinder, boolean z, ComponentName componentName) throws RemoteException {
            return 0;
        }

        @Override // android.app.IActivityClientController
        public boolean shouldPreloadHardwareRenderer(int i) throws RemoteException {
            return false;
        }

        @Override // android.app.IActivityClientController
        public boolean shouldUpRecreateTask(IBinder iBinder, String str) throws RemoteException {
            return false;
        }

        @Override // android.app.IActivityClientController
        public boolean showAssistFromActivity(IBinder iBinder, Bundle bundle) throws RemoteException {
            return false;
        }

        @Override // android.app.IActivityClientController
        public void showLockTaskEscapeMessage(IBinder iBinder) throws RemoteException {
        }

        @Override // android.app.IActivityClientController
        public void splashScreenAttached(IBinder iBinder) throws RemoteException {
        }

        @Override // android.app.IActivityClientController
        public void startLocalVoiceInteraction(IBinder iBinder, Bundle bundle) throws RemoteException {
        }

        @Override // android.app.IActivityClientController
        public void startLockTaskModeByToken(IBinder iBinder) throws RemoteException {
        }

        @Override // android.app.IActivityClientController
        public void stopLocalVoiceInteraction(IBinder iBinder) throws RemoteException {
        }

        @Override // android.app.IActivityClientController
        public void stopLockTaskModeByToken(IBinder iBinder) throws RemoteException {
        }

        @Override // android.app.IActivityClientController
        public void toggleFreeformWindowingMode(IBinder iBinder) throws RemoteException {
        }

        @Override // android.app.IActivityClientController
        public void unregisterRemoteAnimations(IBinder iBinder) throws RemoteException {
        }

        @Override // android.app.IActivityClientController
        public boolean willActivityBeVisible(IBinder iBinder) throws RemoteException {
            return false;
        }
    }

    void activityDestroyed(IBinder iBinder) throws RemoteException;

    void activityIdle(IBinder iBinder, Configuration configuration, boolean z) throws RemoteException;

    void activityLocalRelaunch(IBinder iBinder) throws RemoteException;

    void activityPaused(IBinder iBinder) throws RemoteException;

    void activityRefreshed(IBinder iBinder) throws RemoteException;

    void activityRelaunched(IBinder iBinder) throws RemoteException;

    void activityResumed(IBinder iBinder, boolean z) throws RemoteException;

    void activityStopped(IBinder iBinder, Bundle bundle, PersistableBundle persistableBundle, CharSequence charSequence) throws RemoteException;

    void activityTopResumedStateLost() throws RemoteException;

    void adjustPopOverOptions(IBinder iBinder, int[] iArr, int[] iArr2, Point[] pointArr, int[] iArr3) throws RemoteException;

    int checkActivityCallerContentUriPermission(IBinder iBinder, IBinder iBinder2, Uri uri, int i, int i2) throws RemoteException;

    void clearOverrideActivityTransition(IBinder iBinder, boolean z) throws RemoteException;

    boolean convertFromTranslucent(IBinder iBinder) throws RemoteException;

    boolean convertFromTranslucentOp(IBinder iBinder, boolean z) throws RemoteException;

    boolean convertToTranslucent(IBinder iBinder, Bundle bundle) throws RemoteException;

    void dismissKeyguard(IBinder iBinder, IKeyguardDismissCallback iKeyguardDismissCallback, CharSequence charSequence) throws RemoteException;

    void enableTaskLocaleOverride(IBinder iBinder) throws RemoteException;

    boolean enterPictureInPictureMode(IBinder iBinder, PictureInPictureParams pictureInPictureParams) throws RemoteException;

    boolean finishActivity(IBinder iBinder, int i, Intent intent, int i2) throws RemoteException;

    boolean finishActivityAffinity(IBinder iBinder) throws RemoteException;

    void finishSubActivity(IBinder iBinder, String str, int i) throws RemoteException;

    String getActivityCallerPackage(IBinder iBinder, IBinder iBinder2) throws RemoteException;

    int getActivityCallerUid(IBinder iBinder, IBinder iBinder2) throws RemoteException;

    IBinder getActivityTokenBelow(IBinder iBinder) throws RemoteException;

    ComponentName getCallingActivity(IBinder iBinder) throws RemoteException;

    String getCallingPackage(IBinder iBinder) throws RemoteException;

    int getDisplayId(IBinder iBinder) throws RemoteException;

    String getLaunchedFromPackage(IBinder iBinder) throws RemoteException;

    int getLaunchedFromUid(IBinder iBinder) throws RemoteException;

    int getRequestedOrientation(IBinder iBinder) throws RemoteException;

    Configuration getTaskConfiguration(IBinder iBinder) throws RemoteException;

    int getTaskForActivity(IBinder iBinder, boolean z) throws RemoteException;

    void invalidateHomeTaskSnapshot(IBinder iBinder) throws RemoteException;

    boolean isImmersive(IBinder iBinder) throws RemoteException;

    boolean isRequestedToLaunchInTaskFragment(IBinder iBinder, IBinder iBinder2) throws RemoteException;

    boolean isRootVoiceInteraction(IBinder iBinder) throws RemoteException;

    boolean isTopOfTask(IBinder iBinder) throws RemoteException;

    boolean moveActivityTaskToBack(IBinder iBinder, boolean z) throws RemoteException;

    boolean navigateUpTo(IBinder iBinder, Intent intent, String str, int i, Intent intent2) throws RemoteException;

    void onBackPressed(IBinder iBinder, IRequestFinishCallback iRequestFinishCallback) throws RemoteException;

    void overrideActivityTransition(IBinder iBinder, boolean z, int i, int i2, int i3) throws RemoteException;

    void overridePendingTaskTransition(IBinder iBinder, String str, int i, int i2) throws RemoteException;

    void overridePendingTransition(IBinder iBinder, String str, int i, int i2, int i3) throws RemoteException;

    void registerRemoteAnimations(IBinder iBinder, RemoteAnimationDefinition remoteAnimationDefinition) throws RemoteException;

    boolean releaseActivityInstance(IBinder iBinder) throws RemoteException;

    void reportActivityFullyDrawn(IBinder iBinder, boolean z) throws RemoteException;

    void reportSizeConfigurations(IBinder iBinder, SizeConfigurationBuckets sizeConfigurationBuckets) throws RemoteException;

    void requestMultiwindowFullscreen(IBinder iBinder, int i, IRemoteCallback iRemoteCallback) throws RemoteException;

    void setActivityRecordInputSinkEnabled(IBinder iBinder, boolean z) throws RemoteException;

    void setAllowCrossUidActivitySwitchFromBelow(IBinder iBinder, boolean z) throws RemoteException;

    void setForceSendResultForMediaProjection(IBinder iBinder) throws RemoteException;

    void setImmersive(IBinder iBinder, boolean z) throws RemoteException;

    void setInheritShowWhenLocked(IBinder iBinder, boolean z) throws RemoteException;

    void setPictureInPictureParams(IBinder iBinder, PictureInPictureParams pictureInPictureParams) throws RemoteException;

    void setRecentsScreenshotEnabled(IBinder iBinder, boolean z) throws RemoteException;

    void setRequestedOrientation(IBinder iBinder, int i) throws RemoteException;

    void setShouldDockBigOverlays(IBinder iBinder, boolean z) throws RemoteException;

    void setShowWhenLocked(IBinder iBinder, boolean z) throws RemoteException;

    void setTaskDescription(IBinder iBinder, ActivityManager.TaskDescription taskDescription) throws RemoteException;

    void setTurnScreenOn(IBinder iBinder, boolean z) throws RemoteException;

    int setVrMode(IBinder iBinder, boolean z, ComponentName componentName) throws RemoteException;

    boolean shouldPreloadHardwareRenderer(int i) throws RemoteException;

    boolean shouldUpRecreateTask(IBinder iBinder, String str) throws RemoteException;

    boolean showAssistFromActivity(IBinder iBinder, Bundle bundle) throws RemoteException;

    void showLockTaskEscapeMessage(IBinder iBinder) throws RemoteException;

    void splashScreenAttached(IBinder iBinder) throws RemoteException;

    void startLocalVoiceInteraction(IBinder iBinder, Bundle bundle) throws RemoteException;

    void startLockTaskModeByToken(IBinder iBinder) throws RemoteException;

    void stopLocalVoiceInteraction(IBinder iBinder) throws RemoteException;

    void stopLockTaskModeByToken(IBinder iBinder) throws RemoteException;

    void toggleFreeformWindowingMode(IBinder iBinder) throws RemoteException;

    void unregisterRemoteAnimations(IBinder iBinder) throws RemoteException;

    boolean willActivityBeVisible(IBinder iBinder) throws RemoteException;

    public static abstract class Stub extends Binder implements IActivityClientController {
        static final int TRANSACTION_activityDestroyed = 7;
        static final int TRANSACTION_activityIdle = 1;
        static final int TRANSACTION_activityLocalRelaunch = 8;
        static final int TRANSACTION_activityPaused = 5;
        static final int TRANSACTION_activityRefreshed = 3;
        static final int TRANSACTION_activityRelaunched = 9;
        static final int TRANSACTION_activityResumed = 2;
        static final int TRANSACTION_activityStopped = 6;
        static final int TRANSACTION_activityTopResumedStateLost = 4;
        static final int TRANSACTION_adjustPopOverOptions = 73;
        static final int TRANSACTION_checkActivityCallerContentUriPermission = 31;
        static final int TRANSACTION_clearOverrideActivityTransition = 57;
        static final int TRANSACTION_convertFromTranslucent = 34;
        static final int TRANSACTION_convertFromTranslucentOp = 71;
        static final int TRANSACTION_convertToTranslucent = 35;
        static final int TRANSACTION_dismissKeyguard = 62;
        static final int TRANSACTION_enableTaskLocaleOverride = 67;
        static final int TRANSACTION_enterPictureInPictureMode = 38;
        static final int TRANSACTION_finishActivity = 15;
        static final int TRANSACTION_finishActivityAffinity = 16;
        static final int TRANSACTION_finishSubActivity = 17;
        static final int TRANSACTION_getActivityCallerPackage = 30;
        static final int TRANSACTION_getActivityCallerUid = 28;
        static final int TRANSACTION_getActivityTokenBelow = 24;
        static final int TRANSACTION_getCallingActivity = 25;
        static final int TRANSACTION_getCallingPackage = 26;
        static final int TRANSACTION_getDisplayId = 21;
        static final int TRANSACTION_getLaunchedFromPackage = 29;
        static final int TRANSACTION_getLaunchedFromUid = 27;
        static final int TRANSACTION_getRequestedOrientation = 33;
        static final int TRANSACTION_getTaskConfiguration = 23;
        static final int TRANSACTION_getTaskForActivity = 22;
        static final int TRANSACTION_invalidateHomeTaskSnapshot = 61;
        static final int TRANSACTION_isImmersive = 36;
        static final int TRANSACTION_isRequestedToLaunchInTaskFragment = 68;
        static final int TRANSACTION_isRootVoiceInteraction = 48;
        static final int TRANSACTION_isTopOfTask = 19;
        static final int TRANSACTION_moveActivityTaskToBack = 11;
        static final int TRANSACTION_navigateUpTo = 13;
        static final int TRANSACTION_onBackPressed = 65;
        static final int TRANSACTION_overrideActivityTransition = 56;
        static final int TRANSACTION_overridePendingTaskTransition = 72;
        static final int TRANSACTION_overridePendingTransition = 58;
        static final int TRANSACTION_registerRemoteAnimations = 63;
        static final int TRANSACTION_releaseActivityInstance = 14;
        static final int TRANSACTION_reportActivityFullyDrawn = 55;
        static final int TRANSACTION_reportSizeConfigurations = 10;
        static final int TRANSACTION_requestMultiwindowFullscreen = 42;
        static final int TRANSACTION_setActivityRecordInputSinkEnabled = 69;
        static final int TRANSACTION_setAllowCrossUidActivitySwitchFromBelow = 54;
        static final int TRANSACTION_setForceSendResultForMediaProjection = 18;
        static final int TRANSACTION_setImmersive = 37;
        static final int TRANSACTION_setInheritShowWhenLocked = 52;
        static final int TRANSACTION_setPictureInPictureParams = 39;
        static final int TRANSACTION_setRecentsScreenshotEnabled = 60;
        static final int TRANSACTION_setRequestedOrientation = 32;
        static final int TRANSACTION_setShouldDockBigOverlays = 40;
        static final int TRANSACTION_setShowWhenLocked = 51;
        static final int TRANSACTION_setTaskDescription = 46;
        static final int TRANSACTION_setTurnScreenOn = 53;
        static final int TRANSACTION_setVrMode = 59;
        static final int TRANSACTION_shouldPreloadHardwareRenderer = 70;
        static final int TRANSACTION_shouldUpRecreateTask = 12;
        static final int TRANSACTION_showAssistFromActivity = 47;
        static final int TRANSACTION_showLockTaskEscapeMessage = 45;
        static final int TRANSACTION_splashScreenAttached = 66;
        static final int TRANSACTION_startLocalVoiceInteraction = 49;
        static final int TRANSACTION_startLockTaskModeByToken = 43;
        static final int TRANSACTION_stopLocalVoiceInteraction = 50;
        static final int TRANSACTION_stopLockTaskModeByToken = 44;
        static final int TRANSACTION_toggleFreeformWindowingMode = 41;
        static final int TRANSACTION_unregisterRemoteAnimations = 64;
        static final int TRANSACTION_willActivityBeVisible = 20;

        @Override // android.os.IInterface
        public IBinder asBinder() {
            return this;
        }

        @Override // android.os.Binder
        public int getMaxTransactionId() {
            return 72;
        }

        public Stub() {
            attachInterface(this, IActivityClientController.DESCRIPTOR);
        }

        public static IActivityClientController asInterface(IBinder iBinder) {
            if (iBinder == null) {
                return null;
            }
            IInterface iInterfaceQueryLocalInterface = iBinder.queryLocalInterface(IActivityClientController.DESCRIPTOR);
            if (iInterfaceQueryLocalInterface != null && (iInterfaceQueryLocalInterface instanceof IActivityClientController)) {
                return (IActivityClientController) iInterfaceQueryLocalInterface;
            }
            return new Proxy(iBinder);
        }

        public static String getDefaultTransactionName(int i) {
            switch (i) {
                case 1:
                    return "activityIdle";
                case 2:
                    return "activityResumed";
                case 3:
                    return "activityRefreshed";
                case 4:
                    return "activityTopResumedStateLost";
                case 5:
                    return "activityPaused";
                case 6:
                    return "activityStopped";
                case 7:
                    return "activityDestroyed";
                case 8:
                    return "activityLocalRelaunch";
                case 9:
                    return "activityRelaunched";
                case 10:
                    return "reportSizeConfigurations";
                case 11:
                    return "moveActivityTaskToBack";
                case 12:
                    return "shouldUpRecreateTask";
                case 13:
                    return "navigateUpTo";
                case 14:
                    return "releaseActivityInstance";
                case 15:
                    return "finishActivity";
                case 16:
                    return "finishActivityAffinity";
                case 17:
                    return "finishSubActivity";
                case 18:
                    return "setForceSendResultForMediaProjection";
                case 19:
                    return "isTopOfTask";
                case 20:
                    return "willActivityBeVisible";
                case 21:
                    return "getDisplayId";
                case 22:
                    return "getTaskForActivity";
                case 23:
                    return "getTaskConfiguration";
                case 24:
                    return "getActivityTokenBelow";
                case 25:
                    return "getCallingActivity";
                case 26:
                    return "getCallingPackage";
                case 27:
                    return "getLaunchedFromUid";
                case 28:
                    return "getActivityCallerUid";
                case 29:
                    return "getLaunchedFromPackage";
                case 30:
                    return "getActivityCallerPackage";
                case 31:
                    return "checkActivityCallerContentUriPermission";
                case 32:
                    return "setRequestedOrientation";
                case 33:
                    return "getRequestedOrientation";
                case 34:
                    return "convertFromTranslucent";
                case 35:
                    return "convertToTranslucent";
                case 36:
                    return "isImmersive";
                case 37:
                    return "setImmersive";
                case 38:
                    return "enterPictureInPictureMode";
                case 39:
                    return "setPictureInPictureParams";
                case 40:
                    return "setShouldDockBigOverlays";
                case 41:
                    return "toggleFreeformWindowingMode";
                case 42:
                    return "requestMultiwindowFullscreen";
                case 43:
                    return "startLockTaskModeByToken";
                case 44:
                    return "stopLockTaskModeByToken";
                case 45:
                    return "showLockTaskEscapeMessage";
                case 46:
                    return "setTaskDescription";
                case 47:
                    return "showAssistFromActivity";
                case 48:
                    return "isRootVoiceInteraction";
                case 49:
                    return "startLocalVoiceInteraction";
                case 50:
                    return "stopLocalVoiceInteraction";
                case 51:
                    return "setShowWhenLocked";
                case 52:
                    return "setInheritShowWhenLocked";
                case 53:
                    return "setTurnScreenOn";
                case 54:
                    return "setAllowCrossUidActivitySwitchFromBelow";
                case 55:
                    return "reportActivityFullyDrawn";
                case 56:
                    return "overrideActivityTransition";
                case 57:
                    return "clearOverrideActivityTransition";
                case 58:
                    return "overridePendingTransition";
                case 59:
                    return "setVrMode";
                case 60:
                    return "setRecentsScreenshotEnabled";
                case 61:
                    return "invalidateHomeTaskSnapshot";
                case 62:
                    return "dismissKeyguard";
                case 63:
                    return "registerRemoteAnimations";
                case 64:
                    return "unregisterRemoteAnimations";
                case 65:
                    return "onBackPressed";
                case 66:
                    return "splashScreenAttached";
                case 67:
                    return "enableTaskLocaleOverride";
                case 68:
                    return "isRequestedToLaunchInTaskFragment";
                case 69:
                    return "setActivityRecordInputSinkEnabled";
                case 70:
                    return "shouldPreloadHardwareRenderer";
                case 71:
                    return "convertFromTranslucentOp";
                case 72:
                    return "overridePendingTaskTransition";
                case 73:
                    return "adjustPopOverOptions";
                default:
                    return null;
            }
        }

        @Override // android.os.Binder
        public String getTransactionName(int i) {
            return getDefaultTransactionName(i);
        }

        @Override // android.os.Binder
        public boolean onTransact(int i, Parcel parcel, Parcel parcel2, int i2) throws RemoteException {
            if (i >= 1 && i <= 16777215) {
                parcel.enforceInterface(IActivityClientController.DESCRIPTOR);
            }
            if (i == 1598968902) {
                parcel2.writeString(IActivityClientController.DESCRIPTOR);
                return true;
            }
            switch (i) {
                case 1:
                    IBinder strongBinder = parcel.readStrongBinder();
                    Configuration configuration = (Configuration) parcel.readTypedObject(Configuration.CREATOR);
                    boolean z = parcel.readBoolean();
                    parcel.enforceNoDataAvail();
                    activityIdle(strongBinder, configuration, z);
                    return true;
                case 2:
                    IBinder strongBinder2 = parcel.readStrongBinder();
                    boolean z2 = parcel.readBoolean();
                    parcel.enforceNoDataAvail();
                    activityResumed(strongBinder2, z2);
                    return true;
                case 3:
                    IBinder strongBinder3 = parcel.readStrongBinder();
                    parcel.enforceNoDataAvail();
                    activityRefreshed(strongBinder3);
                    return true;
                case 4:
                    activityTopResumedStateLost();
                    parcel2.writeNoException();
                    return true;
                case 5:
                    IBinder strongBinder4 = parcel.readStrongBinder();
                    parcel.enforceNoDataAvail();
                    activityPaused(strongBinder4);
                    parcel2.writeNoException();
                    return true;
                case 6:
                    IBinder strongBinder5 = parcel.readStrongBinder();
                    Bundle bundle = (Bundle) parcel.readTypedObject(Bundle.CREATOR);
                    PersistableBundle persistableBundle = (PersistableBundle) parcel.readTypedObject(PersistableBundle.CREATOR);
                    CharSequence charSequence = (CharSequence) parcel.readTypedObject(TextUtils.CHAR_SEQUENCE_CREATOR);
                    parcel.enforceNoDataAvail();
                    activityStopped(strongBinder5, bundle, persistableBundle, charSequence);
                    return true;
                case 7:
                    IBinder strongBinder6 = parcel.readStrongBinder();
                    parcel.enforceNoDataAvail();
                    activityDestroyed(strongBinder6);
                    return true;
                case 8:
                    IBinder strongBinder7 = parcel.readStrongBinder();
                    parcel.enforceNoDataAvail();
                    activityLocalRelaunch(strongBinder7);
                    return true;
                case 9:
                    IBinder strongBinder8 = parcel.readStrongBinder();
                    parcel.enforceNoDataAvail();
                    activityRelaunched(strongBinder8);
                    return true;
                case 10:
                    IBinder strongBinder9 = parcel.readStrongBinder();
                    SizeConfigurationBuckets sizeConfigurationBuckets = (SizeConfigurationBuckets) parcel.readTypedObject(SizeConfigurationBuckets.CREATOR);
                    parcel.enforceNoDataAvail();
                    reportSizeConfigurations(strongBinder9, sizeConfigurationBuckets);
                    return true;
                case 11:
                    IBinder strongBinder10 = parcel.readStrongBinder();
                    boolean z3 = parcel.readBoolean();
                    parcel.enforceNoDataAvail();
                    boolean zMoveActivityTaskToBack = moveActivityTaskToBack(strongBinder10, z3);
                    parcel2.writeNoException();
                    parcel2.writeBoolean(zMoveActivityTaskToBack);
                    return true;
                case 12:
                    IBinder strongBinder11 = parcel.readStrongBinder();
                    String string = parcel.readString();
                    parcel.enforceNoDataAvail();
                    boolean zShouldUpRecreateTask = shouldUpRecreateTask(strongBinder11, string);
                    parcel2.writeNoException();
                    parcel2.writeBoolean(zShouldUpRecreateTask);
                    return true;
                case 13:
                    IBinder strongBinder12 = parcel.readStrongBinder();
                    Intent intent = (Intent) parcel.readTypedObject(Intent.CREATOR);
                    String string2 = parcel.readString();
                    int i3 = parcel.readInt();
                    Intent intent2 = (Intent) parcel.readTypedObject(Intent.CREATOR);
                    parcel.enforceNoDataAvail();
                    boolean zNavigateUpTo = navigateUpTo(strongBinder12, intent, string2, i3, intent2);
                    parcel2.writeNoException();
                    parcel2.writeBoolean(zNavigateUpTo);
                    return true;
                case 14:
                    IBinder strongBinder13 = parcel.readStrongBinder();
                    parcel.enforceNoDataAvail();
                    boolean zReleaseActivityInstance = releaseActivityInstance(strongBinder13);
                    parcel2.writeNoException();
                    parcel2.writeBoolean(zReleaseActivityInstance);
                    return true;
                case 15:
                    IBinder strongBinder14 = parcel.readStrongBinder();
                    int i4 = parcel.readInt();
                    Intent intent3 = (Intent) parcel.readTypedObject(Intent.CREATOR);
                    int i5 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    boolean zFinishActivity = finishActivity(strongBinder14, i4, intent3, i5);
                    parcel2.writeNoException();
                    parcel2.writeBoolean(zFinishActivity);
                    return true;
                case 16:
                    IBinder strongBinder15 = parcel.readStrongBinder();
                    parcel.enforceNoDataAvail();
                    boolean zFinishActivityAffinity = finishActivityAffinity(strongBinder15);
                    parcel2.writeNoException();
                    parcel2.writeBoolean(zFinishActivityAffinity);
                    return true;
                case 17:
                    IBinder strongBinder16 = parcel.readStrongBinder();
                    String string3 = parcel.readString();
                    int i6 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    finishSubActivity(strongBinder16, string3, i6);
                    parcel2.writeNoException();
                    return true;
                case 18:
                    IBinder strongBinder17 = parcel.readStrongBinder();
                    parcel.enforceNoDataAvail();
                    setForceSendResultForMediaProjection(strongBinder17);
                    parcel2.writeNoException();
                    return true;
                case 19:
                    IBinder strongBinder18 = parcel.readStrongBinder();
                    parcel.enforceNoDataAvail();
                    boolean zIsTopOfTask = isTopOfTask(strongBinder18);
                    parcel2.writeNoException();
                    parcel2.writeBoolean(zIsTopOfTask);
                    return true;
                case 20:
                    IBinder strongBinder19 = parcel.readStrongBinder();
                    parcel.enforceNoDataAvail();
                    boolean zWillActivityBeVisible = willActivityBeVisible(strongBinder19);
                    parcel2.writeNoException();
                    parcel2.writeBoolean(zWillActivityBeVisible);
                    return true;
                case 21:
                    IBinder strongBinder20 = parcel.readStrongBinder();
                    parcel.enforceNoDataAvail();
                    int displayId = getDisplayId(strongBinder20);
                    parcel2.writeNoException();
                    parcel2.writeInt(displayId);
                    return true;
                case 22:
                    IBinder strongBinder21 = parcel.readStrongBinder();
                    boolean z4 = parcel.readBoolean();
                    parcel.enforceNoDataAvail();
                    int taskForActivity = getTaskForActivity(strongBinder21, z4);
                    parcel2.writeNoException();
                    parcel2.writeInt(taskForActivity);
                    return true;
                case 23:
                    IBinder strongBinder22 = parcel.readStrongBinder();
                    parcel.enforceNoDataAvail();
                    Configuration taskConfiguration = getTaskConfiguration(strongBinder22);
                    parcel2.writeNoException();
                    parcel2.writeTypedObject(taskConfiguration, 1);
                    return true;
                case 24:
                    IBinder strongBinder23 = parcel.readStrongBinder();
                    parcel.enforceNoDataAvail();
                    IBinder activityTokenBelow = getActivityTokenBelow(strongBinder23);
                    parcel2.writeNoException();
                    parcel2.writeStrongBinder(activityTokenBelow);
                    return true;
                case 25:
                    IBinder strongBinder24 = parcel.readStrongBinder();
                    parcel.enforceNoDataAvail();
                    ComponentName callingActivity = getCallingActivity(strongBinder24);
                    parcel2.writeNoException();
                    parcel2.writeTypedObject(callingActivity, 1);
                    return true;
                case 26:
                    IBinder strongBinder25 = parcel.readStrongBinder();
                    parcel.enforceNoDataAvail();
                    String callingPackage = getCallingPackage(strongBinder25);
                    parcel2.writeNoException();
                    parcel2.writeString(callingPackage);
                    return true;
                case 27:
                    IBinder strongBinder26 = parcel.readStrongBinder();
                    parcel.enforceNoDataAvail();
                    int launchedFromUid = getLaunchedFromUid(strongBinder26);
                    parcel2.writeNoException();
                    parcel2.writeInt(launchedFromUid);
                    return true;
                case 28:
                    IBinder strongBinder27 = parcel.readStrongBinder();
                    IBinder strongBinder28 = parcel.readStrongBinder();
                    parcel.enforceNoDataAvail();
                    int activityCallerUid = getActivityCallerUid(strongBinder27, strongBinder28);
                    parcel2.writeNoException();
                    parcel2.writeInt(activityCallerUid);
                    return true;
                case 29:
                    IBinder strongBinder29 = parcel.readStrongBinder();
                    parcel.enforceNoDataAvail();
                    String launchedFromPackage = getLaunchedFromPackage(strongBinder29);
                    parcel2.writeNoException();
                    parcel2.writeString(launchedFromPackage);
                    return true;
                case 30:
                    IBinder strongBinder30 = parcel.readStrongBinder();
                    IBinder strongBinder31 = parcel.readStrongBinder();
                    parcel.enforceNoDataAvail();
                    String activityCallerPackage = getActivityCallerPackage(strongBinder30, strongBinder31);
                    parcel2.writeNoException();
                    parcel2.writeString(activityCallerPackage);
                    return true;
                case 31:
                    IBinder strongBinder32 = parcel.readStrongBinder();
                    IBinder strongBinder33 = parcel.readStrongBinder();
                    Uri uri = (Uri) parcel.readTypedObject(Uri.CREATOR);
                    int i7 = parcel.readInt();
                    int i8 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    int iCheckActivityCallerContentUriPermission = checkActivityCallerContentUriPermission(strongBinder32, strongBinder33, uri, i7, i8);
                    parcel2.writeNoException();
                    parcel2.writeInt(iCheckActivityCallerContentUriPermission);
                    return true;
                case 32:
                    IBinder strongBinder34 = parcel.readStrongBinder();
                    int i9 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    setRequestedOrientation(strongBinder34, i9);
                    parcel2.writeNoException();
                    return true;
                case 33:
                    IBinder strongBinder35 = parcel.readStrongBinder();
                    parcel.enforceNoDataAvail();
                    int requestedOrientation = getRequestedOrientation(strongBinder35);
                    parcel2.writeNoException();
                    parcel2.writeInt(requestedOrientation);
                    return true;
                case 34:
                    IBinder strongBinder36 = parcel.readStrongBinder();
                    parcel.enforceNoDataAvail();
                    boolean zConvertFromTranslucent = convertFromTranslucent(strongBinder36);
                    parcel2.writeNoException();
                    parcel2.writeBoolean(zConvertFromTranslucent);
                    return true;
                case 35:
                    IBinder strongBinder37 = parcel.readStrongBinder();
                    Bundle bundle2 = (Bundle) parcel.readTypedObject(Bundle.CREATOR);
                    parcel.enforceNoDataAvail();
                    boolean zConvertToTranslucent = convertToTranslucent(strongBinder37, bundle2);
                    parcel2.writeNoException();
                    parcel2.writeBoolean(zConvertToTranslucent);
                    return true;
                case 36:
                    IBinder strongBinder38 = parcel.readStrongBinder();
                    parcel.enforceNoDataAvail();
                    boolean zIsImmersive = isImmersive(strongBinder38);
                    parcel2.writeNoException();
                    parcel2.writeBoolean(zIsImmersive);
                    return true;
                case 37:
                    IBinder strongBinder39 = parcel.readStrongBinder();
                    boolean z5 = parcel.readBoolean();
                    parcel.enforceNoDataAvail();
                    setImmersive(strongBinder39, z5);
                    parcel2.writeNoException();
                    return true;
                case 38:
                    IBinder strongBinder40 = parcel.readStrongBinder();
                    PictureInPictureParams pictureInPictureParams = (PictureInPictureParams) parcel.readTypedObject(PictureInPictureParams.CREATOR);
                    parcel.enforceNoDataAvail();
                    boolean zEnterPictureInPictureMode = enterPictureInPictureMode(strongBinder40, pictureInPictureParams);
                    parcel2.writeNoException();
                    parcel2.writeBoolean(zEnterPictureInPictureMode);
                    return true;
                case 39:
                    IBinder strongBinder41 = parcel.readStrongBinder();
                    PictureInPictureParams pictureInPictureParams2 = (PictureInPictureParams) parcel.readTypedObject(PictureInPictureParams.CREATOR);
                    parcel.enforceNoDataAvail();
                    setPictureInPictureParams(strongBinder41, pictureInPictureParams2);
                    parcel2.writeNoException();
                    return true;
                case 40:
                    IBinder strongBinder42 = parcel.readStrongBinder();
                    boolean z6 = parcel.readBoolean();
                    parcel.enforceNoDataAvail();
                    setShouldDockBigOverlays(strongBinder42, z6);
                    return true;
                case 41:
                    IBinder strongBinder43 = parcel.readStrongBinder();
                    parcel.enforceNoDataAvail();
                    toggleFreeformWindowingMode(strongBinder43);
                    parcel2.writeNoException();
                    return true;
                case 42:
                    IBinder strongBinder44 = parcel.readStrongBinder();
                    int i10 = parcel.readInt();
                    IRemoteCallback iRemoteCallbackAsInterface = IRemoteCallback.Stub.asInterface(parcel.readStrongBinder());
                    parcel.enforceNoDataAvail();
                    requestMultiwindowFullscreen(strongBinder44, i10, iRemoteCallbackAsInterface);
                    return true;
                case 43:
                    IBinder strongBinder45 = parcel.readStrongBinder();
                    parcel.enforceNoDataAvail();
                    startLockTaskModeByToken(strongBinder45);
                    parcel2.writeNoException();
                    return true;
                case 44:
                    IBinder strongBinder46 = parcel.readStrongBinder();
                    parcel.enforceNoDataAvail();
                    stopLockTaskModeByToken(strongBinder46);
                    parcel2.writeNoException();
                    return true;
                case 45:
                    IBinder strongBinder47 = parcel.readStrongBinder();
                    parcel.enforceNoDataAvail();
                    showLockTaskEscapeMessage(strongBinder47);
                    return true;
                case 46:
                    IBinder strongBinder48 = parcel.readStrongBinder();
                    ActivityManager.TaskDescription taskDescription = (ActivityManager.TaskDescription) parcel.readTypedObject(ActivityManager.TaskDescription.CREATOR);
                    parcel.enforceNoDataAvail();
                    setTaskDescription(strongBinder48, taskDescription);
                    parcel2.writeNoException();
                    return true;
                case 47:
                    IBinder strongBinder49 = parcel.readStrongBinder();
                    Bundle bundle3 = (Bundle) parcel.readTypedObject(Bundle.CREATOR);
                    parcel.enforceNoDataAvail();
                    boolean zShowAssistFromActivity = showAssistFromActivity(strongBinder49, bundle3);
                    parcel2.writeNoException();
                    parcel2.writeBoolean(zShowAssistFromActivity);
                    return true;
                case 48:
                    IBinder strongBinder50 = parcel.readStrongBinder();
                    parcel.enforceNoDataAvail();
                    boolean zIsRootVoiceInteraction = isRootVoiceInteraction(strongBinder50);
                    parcel2.writeNoException();
                    parcel2.writeBoolean(zIsRootVoiceInteraction);
                    return true;
                case 49:
                    IBinder strongBinder51 = parcel.readStrongBinder();
                    Bundle bundle4 = (Bundle) parcel.readTypedObject(Bundle.CREATOR);
                    parcel.enforceNoDataAvail();
                    startLocalVoiceInteraction(strongBinder51, bundle4);
                    parcel2.writeNoException();
                    return true;
                case 50:
                    IBinder strongBinder52 = parcel.readStrongBinder();
                    parcel.enforceNoDataAvail();
                    stopLocalVoiceInteraction(strongBinder52);
                    parcel2.writeNoException();
                    return true;
                case 51:
                    IBinder strongBinder53 = parcel.readStrongBinder();
                    boolean z7 = parcel.readBoolean();
                    parcel.enforceNoDataAvail();
                    setShowWhenLocked(strongBinder53, z7);
                    return true;
                case 52:
                    IBinder strongBinder54 = parcel.readStrongBinder();
                    boolean z8 = parcel.readBoolean();
                    parcel.enforceNoDataAvail();
                    setInheritShowWhenLocked(strongBinder54, z8);
                    return true;
                case 53:
                    IBinder strongBinder55 = parcel.readStrongBinder();
                    boolean z9 = parcel.readBoolean();
                    parcel.enforceNoDataAvail();
                    setTurnScreenOn(strongBinder55, z9);
                    parcel2.writeNoException();
                    return true;
                case 54:
                    IBinder strongBinder56 = parcel.readStrongBinder();
                    boolean z10 = parcel.readBoolean();
                    parcel.enforceNoDataAvail();
                    setAllowCrossUidActivitySwitchFromBelow(strongBinder56, z10);
                    return true;
                case 55:
                    IBinder strongBinder57 = parcel.readStrongBinder();
                    boolean z11 = parcel.readBoolean();
                    parcel.enforceNoDataAvail();
                    reportActivityFullyDrawn(strongBinder57, z11);
                    return true;
                case 56:
                    IBinder strongBinder58 = parcel.readStrongBinder();
                    boolean z12 = parcel.readBoolean();
                    int i11 = parcel.readInt();
                    int i12 = parcel.readInt();
                    int i13 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    overrideActivityTransition(strongBinder58, z12, i11, i12, i13);
                    return true;
                case 57:
                    IBinder strongBinder59 = parcel.readStrongBinder();
                    boolean z13 = parcel.readBoolean();
                    parcel.enforceNoDataAvail();
                    clearOverrideActivityTransition(strongBinder59, z13);
                    return true;
                case 58:
                    IBinder strongBinder60 = parcel.readStrongBinder();
                    String string4 = parcel.readString();
                    int i14 = parcel.readInt();
                    int i15 = parcel.readInt();
                    int i16 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    overridePendingTransition(strongBinder60, string4, i14, i15, i16);
                    parcel2.writeNoException();
                    return true;
                case 59:
                    IBinder strongBinder61 = parcel.readStrongBinder();
                    boolean z14 = parcel.readBoolean();
                    ComponentName componentName = (ComponentName) parcel.readTypedObject(ComponentName.CREATOR);
                    parcel.enforceNoDataAvail();
                    int vrMode = setVrMode(strongBinder61, z14, componentName);
                    parcel2.writeNoException();
                    parcel2.writeInt(vrMode);
                    return true;
                case 60:
                    IBinder strongBinder62 = parcel.readStrongBinder();
                    boolean z15 = parcel.readBoolean();
                    parcel.enforceNoDataAvail();
                    setRecentsScreenshotEnabled(strongBinder62, z15);
                    return true;
                case 61:
                    IBinder strongBinder63 = parcel.readStrongBinder();
                    parcel.enforceNoDataAvail();
                    invalidateHomeTaskSnapshot(strongBinder63);
                    parcel2.writeNoException();
                    return true;
                case 62:
                    IBinder strongBinder64 = parcel.readStrongBinder();
                    IKeyguardDismissCallback iKeyguardDismissCallbackAsInterface = IKeyguardDismissCallback.Stub.asInterface(parcel.readStrongBinder());
                    CharSequence charSequence2 = (CharSequence) parcel.readTypedObject(TextUtils.CHAR_SEQUENCE_CREATOR);
                    parcel.enforceNoDataAvail();
                    dismissKeyguard(strongBinder64, iKeyguardDismissCallbackAsInterface, charSequence2);
                    parcel2.writeNoException();
                    return true;
                case 63:
                    IBinder strongBinder65 = parcel.readStrongBinder();
                    RemoteAnimationDefinition remoteAnimationDefinition = (RemoteAnimationDefinition) parcel.readTypedObject(RemoteAnimationDefinition.CREATOR);
                    parcel.enforceNoDataAvail();
                    registerRemoteAnimations(strongBinder65, remoteAnimationDefinition);
                    parcel2.writeNoException();
                    return true;
                case 64:
                    IBinder strongBinder66 = parcel.readStrongBinder();
                    parcel.enforceNoDataAvail();
                    unregisterRemoteAnimations(strongBinder66);
                    parcel2.writeNoException();
                    return true;
                case 65:
                    IBinder strongBinder67 = parcel.readStrongBinder();
                    IRequestFinishCallback iRequestFinishCallbackAsInterface = IRequestFinishCallback.Stub.asInterface(parcel.readStrongBinder());
                    parcel.enforceNoDataAvail();
                    onBackPressed(strongBinder67, iRequestFinishCallbackAsInterface);
                    return true;
                case 66:
                    IBinder strongBinder68 = parcel.readStrongBinder();
                    parcel.enforceNoDataAvail();
                    splashScreenAttached(strongBinder68);
                    return true;
                case 67:
                    IBinder strongBinder69 = parcel.readStrongBinder();
                    parcel.enforceNoDataAvail();
                    enableTaskLocaleOverride(strongBinder69);
                    parcel2.writeNoException();
                    return true;
                case 68:
                    IBinder strongBinder70 = parcel.readStrongBinder();
                    IBinder strongBinder71 = parcel.readStrongBinder();
                    parcel.enforceNoDataAvail();
                    boolean zIsRequestedToLaunchInTaskFragment = isRequestedToLaunchInTaskFragment(strongBinder70, strongBinder71);
                    parcel2.writeNoException();
                    parcel2.writeBoolean(zIsRequestedToLaunchInTaskFragment);
                    return true;
                case 69:
                    IBinder strongBinder72 = parcel.readStrongBinder();
                    boolean z16 = parcel.readBoolean();
                    parcel.enforceNoDataAvail();
                    setActivityRecordInputSinkEnabled(strongBinder72, z16);
                    return true;
                case 70:
                    int i17 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    boolean zShouldPreloadHardwareRenderer = shouldPreloadHardwareRenderer(i17);
                    parcel2.writeNoException();
                    parcel2.writeBoolean(zShouldPreloadHardwareRenderer);
                    return true;
                case 71:
                    IBinder strongBinder73 = parcel.readStrongBinder();
                    boolean z17 = parcel.readBoolean();
                    parcel.enforceNoDataAvail();
                    boolean zConvertFromTranslucentOp = convertFromTranslucentOp(strongBinder73, z17);
                    parcel2.writeNoException();
                    parcel2.writeBoolean(zConvertFromTranslucentOp);
                    return true;
                case 72:
                    IBinder strongBinder74 = parcel.readStrongBinder();
                    String string5 = parcel.readString();
                    int i18 = parcel.readInt();
                    int i19 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    overridePendingTaskTransition(strongBinder74, string5, i18, i19);
                    parcel2.writeNoException();
                    return true;
                case 73:
                    IBinder strongBinder75 = parcel.readStrongBinder();
                    int[] iArrCreateIntArray = parcel.createIntArray();
                    int[] iArrCreateIntArray2 = parcel.createIntArray();
                    Point[] pointArr = (Point[]) parcel.createTypedArray(Point.CREATOR);
                    int[] iArrCreateIntArray3 = parcel.createIntArray();
                    parcel.enforceNoDataAvail();
                    adjustPopOverOptions(strongBinder75, iArrCreateIntArray, iArrCreateIntArray2, pointArr, iArrCreateIntArray3);
                    parcel2.writeNoException();
                    return true;
                default:
                    return super.onTransact(i, parcel, parcel2, i2);
            }
        }

        private static class Proxy implements IActivityClientController {
            private IBinder mRemote;

            Proxy(IBinder iBinder) {
                this.mRemote = iBinder;
            }

            @Override // android.os.IInterface
            public IBinder asBinder() {
                return this.mRemote;
            }

            public String getInterfaceDescriptor() {
                return IActivityClientController.DESCRIPTOR;
            }

            @Override // android.app.IActivityClientController
            public void activityIdle(IBinder iBinder, Configuration configuration, boolean z) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                try {
                    parcelObtain.writeInterfaceToken(IActivityClientController.DESCRIPTOR);
                    parcelObtain.writeStrongBinder(iBinder);
                    parcelObtain.writeTypedObject(configuration, 0);
                    parcelObtain.writeBoolean(z);
                    this.mRemote.transact(1, parcelObtain, null, 1);
                } finally {
                    parcelObtain.recycle();
                }
            }

            @Override // android.app.IActivityClientController
            public void activityResumed(IBinder iBinder, boolean z) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                try {
                    parcelObtain.writeInterfaceToken(IActivityClientController.DESCRIPTOR);
                    parcelObtain.writeStrongBinder(iBinder);
                    parcelObtain.writeBoolean(z);
                    this.mRemote.transact(2, parcelObtain, null, 1);
                } finally {
                    parcelObtain.recycle();
                }
            }

            @Override // android.app.IActivityClientController
            public void activityRefreshed(IBinder iBinder) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                try {
                    parcelObtain.writeInterfaceToken(IActivityClientController.DESCRIPTOR);
                    parcelObtain.writeStrongBinder(iBinder);
                    this.mRemote.transact(3, parcelObtain, null, 1);
                } finally {
                    parcelObtain.recycle();
                }
            }

            @Override // android.app.IActivityClientController
            public void activityTopResumedStateLost() throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(IActivityClientController.DESCRIPTOR);
                    this.mRemote.transact(4, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.app.IActivityClientController
            public void activityPaused(IBinder iBinder) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(IActivityClientController.DESCRIPTOR);
                    parcelObtain.writeStrongBinder(iBinder);
                    this.mRemote.transact(5, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.app.IActivityClientController
            public void activityStopped(IBinder iBinder, Bundle bundle, PersistableBundle persistableBundle, CharSequence charSequence) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                try {
                    parcelObtain.writeInterfaceToken(IActivityClientController.DESCRIPTOR);
                    parcelObtain.writeStrongBinder(iBinder);
                    parcelObtain.writeTypedObject(bundle, 0);
                    parcelObtain.writeTypedObject(persistableBundle, 0);
                    if (charSequence != null) {
                        parcelObtain.writeInt(1);
                        TextUtils.writeToParcel(charSequence, parcelObtain, 0);
                    } else {
                        parcelObtain.writeInt(0);
                    }
                    this.mRemote.transact(6, parcelObtain, null, 1);
                } finally {
                    parcelObtain.recycle();
                }
            }

            @Override // android.app.IActivityClientController
            public void activityDestroyed(IBinder iBinder) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                try {
                    parcelObtain.writeInterfaceToken(IActivityClientController.DESCRIPTOR);
                    parcelObtain.writeStrongBinder(iBinder);
                    this.mRemote.transact(7, parcelObtain, null, 1);
                } finally {
                    parcelObtain.recycle();
                }
            }

            @Override // android.app.IActivityClientController
            public void activityLocalRelaunch(IBinder iBinder) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                try {
                    parcelObtain.writeInterfaceToken(IActivityClientController.DESCRIPTOR);
                    parcelObtain.writeStrongBinder(iBinder);
                    this.mRemote.transact(8, parcelObtain, null, 1);
                } finally {
                    parcelObtain.recycle();
                }
            }

            @Override // android.app.IActivityClientController
            public void activityRelaunched(IBinder iBinder) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                try {
                    parcelObtain.writeInterfaceToken(IActivityClientController.DESCRIPTOR);
                    parcelObtain.writeStrongBinder(iBinder);
                    this.mRemote.transact(9, parcelObtain, null, 1);
                } finally {
                    parcelObtain.recycle();
                }
            }

            @Override // android.app.IActivityClientController
            public void reportSizeConfigurations(IBinder iBinder, SizeConfigurationBuckets sizeConfigurationBuckets) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                try {
                    parcelObtain.writeInterfaceToken(IActivityClientController.DESCRIPTOR);
                    parcelObtain.writeStrongBinder(iBinder);
                    parcelObtain.writeTypedObject(sizeConfigurationBuckets, 0);
                    this.mRemote.transact(10, parcelObtain, null, 1);
                } finally {
                    parcelObtain.recycle();
                }
            }

            @Override // android.app.IActivityClientController
            public boolean moveActivityTaskToBack(IBinder iBinder, boolean z) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(IActivityClientController.DESCRIPTOR);
                    parcelObtain.writeStrongBinder(iBinder);
                    parcelObtain.writeBoolean(z);
                    this.mRemote.transact(11, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return parcelObtain2.readBoolean();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.app.IActivityClientController
            public boolean shouldUpRecreateTask(IBinder iBinder, String str) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(IActivityClientController.DESCRIPTOR);
                    parcelObtain.writeStrongBinder(iBinder);
                    parcelObtain.writeString(str);
                    this.mRemote.transact(12, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return parcelObtain2.readBoolean();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.app.IActivityClientController
            public boolean navigateUpTo(IBinder iBinder, Intent intent, String str, int i, Intent intent2) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(IActivityClientController.DESCRIPTOR);
                    parcelObtain.writeStrongBinder(iBinder);
                    parcelObtain.writeTypedObject(intent, 0);
                    parcelObtain.writeString(str);
                    parcelObtain.writeInt(i);
                    parcelObtain.writeTypedObject(intent2, 0);
                    this.mRemote.transact(13, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return parcelObtain2.readBoolean();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.app.IActivityClientController
            public boolean releaseActivityInstance(IBinder iBinder) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(IActivityClientController.DESCRIPTOR);
                    parcelObtain.writeStrongBinder(iBinder);
                    this.mRemote.transact(14, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return parcelObtain2.readBoolean();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.app.IActivityClientController
            public boolean finishActivity(IBinder iBinder, int i, Intent intent, int i2) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(IActivityClientController.DESCRIPTOR);
                    parcelObtain.writeStrongBinder(iBinder);
                    parcelObtain.writeInt(i);
                    parcelObtain.writeTypedObject(intent, 0);
                    parcelObtain.writeInt(i2);
                    this.mRemote.transact(15, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return parcelObtain2.readBoolean();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.app.IActivityClientController
            public boolean finishActivityAffinity(IBinder iBinder) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(IActivityClientController.DESCRIPTOR);
                    parcelObtain.writeStrongBinder(iBinder);
                    this.mRemote.transact(16, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return parcelObtain2.readBoolean();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.app.IActivityClientController
            public void finishSubActivity(IBinder iBinder, String str, int i) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(IActivityClientController.DESCRIPTOR);
                    parcelObtain.writeStrongBinder(iBinder);
                    parcelObtain.writeString(str);
                    parcelObtain.writeInt(i);
                    this.mRemote.transact(17, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.app.IActivityClientController
            public void setForceSendResultForMediaProjection(IBinder iBinder) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(IActivityClientController.DESCRIPTOR);
                    parcelObtain.writeStrongBinder(iBinder);
                    this.mRemote.transact(18, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.app.IActivityClientController
            public boolean isTopOfTask(IBinder iBinder) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(IActivityClientController.DESCRIPTOR);
                    parcelObtain.writeStrongBinder(iBinder);
                    this.mRemote.transact(19, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return parcelObtain2.readBoolean();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.app.IActivityClientController
            public boolean willActivityBeVisible(IBinder iBinder) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(IActivityClientController.DESCRIPTOR);
                    parcelObtain.writeStrongBinder(iBinder);
                    this.mRemote.transact(20, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return parcelObtain2.readBoolean();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.app.IActivityClientController
            public int getDisplayId(IBinder iBinder) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(IActivityClientController.DESCRIPTOR);
                    parcelObtain.writeStrongBinder(iBinder);
                    this.mRemote.transact(21, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return parcelObtain2.readInt();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.app.IActivityClientController
            public int getTaskForActivity(IBinder iBinder, boolean z) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(IActivityClientController.DESCRIPTOR);
                    parcelObtain.writeStrongBinder(iBinder);
                    parcelObtain.writeBoolean(z);
                    this.mRemote.transact(22, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return parcelObtain2.readInt();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.app.IActivityClientController
            public Configuration getTaskConfiguration(IBinder iBinder) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(IActivityClientController.DESCRIPTOR);
                    parcelObtain.writeStrongBinder(iBinder);
                    this.mRemote.transact(23, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return (Configuration) parcelObtain2.readTypedObject(Configuration.CREATOR);
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.app.IActivityClientController
            public IBinder getActivityTokenBelow(IBinder iBinder) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(IActivityClientController.DESCRIPTOR);
                    parcelObtain.writeStrongBinder(iBinder);
                    this.mRemote.transact(24, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return parcelObtain2.readStrongBinder();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.app.IActivityClientController
            public ComponentName getCallingActivity(IBinder iBinder) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(IActivityClientController.DESCRIPTOR);
                    parcelObtain.writeStrongBinder(iBinder);
                    this.mRemote.transact(25, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return (ComponentName) parcelObtain2.readTypedObject(ComponentName.CREATOR);
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.app.IActivityClientController
            public String getCallingPackage(IBinder iBinder) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(IActivityClientController.DESCRIPTOR);
                    parcelObtain.writeStrongBinder(iBinder);
                    this.mRemote.transact(26, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return parcelObtain2.readString();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.app.IActivityClientController
            public int getLaunchedFromUid(IBinder iBinder) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(IActivityClientController.DESCRIPTOR);
                    parcelObtain.writeStrongBinder(iBinder);
                    this.mRemote.transact(27, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return parcelObtain2.readInt();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.app.IActivityClientController
            public int getActivityCallerUid(IBinder iBinder, IBinder iBinder2) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(IActivityClientController.DESCRIPTOR);
                    parcelObtain.writeStrongBinder(iBinder);
                    parcelObtain.writeStrongBinder(iBinder2);
                    this.mRemote.transact(28, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return parcelObtain2.readInt();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.app.IActivityClientController
            public String getLaunchedFromPackage(IBinder iBinder) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(IActivityClientController.DESCRIPTOR);
                    parcelObtain.writeStrongBinder(iBinder);
                    this.mRemote.transact(29, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return parcelObtain2.readString();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.app.IActivityClientController
            public String getActivityCallerPackage(IBinder iBinder, IBinder iBinder2) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(IActivityClientController.DESCRIPTOR);
                    parcelObtain.writeStrongBinder(iBinder);
                    parcelObtain.writeStrongBinder(iBinder2);
                    this.mRemote.transact(30, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return parcelObtain2.readString();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.app.IActivityClientController
            public int checkActivityCallerContentUriPermission(IBinder iBinder, IBinder iBinder2, Uri uri, int i, int i2) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(IActivityClientController.DESCRIPTOR);
                    parcelObtain.writeStrongBinder(iBinder);
                    parcelObtain.writeStrongBinder(iBinder2);
                    parcelObtain.writeTypedObject(uri, 0);
                    parcelObtain.writeInt(i);
                    parcelObtain.writeInt(i2);
                    this.mRemote.transact(31, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return parcelObtain2.readInt();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.app.IActivityClientController
            public void setRequestedOrientation(IBinder iBinder, int i) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(IActivityClientController.DESCRIPTOR);
                    parcelObtain.writeStrongBinder(iBinder);
                    parcelObtain.writeInt(i);
                    this.mRemote.transact(32, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.app.IActivityClientController
            public int getRequestedOrientation(IBinder iBinder) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(IActivityClientController.DESCRIPTOR);
                    parcelObtain.writeStrongBinder(iBinder);
                    this.mRemote.transact(33, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return parcelObtain2.readInt();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.app.IActivityClientController
            public boolean convertFromTranslucent(IBinder iBinder) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(IActivityClientController.DESCRIPTOR);
                    parcelObtain.writeStrongBinder(iBinder);
                    this.mRemote.transact(34, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return parcelObtain2.readBoolean();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.app.IActivityClientController
            public boolean convertToTranslucent(IBinder iBinder, Bundle bundle) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(IActivityClientController.DESCRIPTOR);
                    parcelObtain.writeStrongBinder(iBinder);
                    parcelObtain.writeTypedObject(bundle, 0);
                    this.mRemote.transact(35, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return parcelObtain2.readBoolean();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.app.IActivityClientController
            public boolean isImmersive(IBinder iBinder) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(IActivityClientController.DESCRIPTOR);
                    parcelObtain.writeStrongBinder(iBinder);
                    this.mRemote.transact(36, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return parcelObtain2.readBoolean();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.app.IActivityClientController
            public void setImmersive(IBinder iBinder, boolean z) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(IActivityClientController.DESCRIPTOR);
                    parcelObtain.writeStrongBinder(iBinder);
                    parcelObtain.writeBoolean(z);
                    this.mRemote.transact(37, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.app.IActivityClientController
            public boolean enterPictureInPictureMode(IBinder iBinder, PictureInPictureParams pictureInPictureParams) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(IActivityClientController.DESCRIPTOR);
                    parcelObtain.writeStrongBinder(iBinder);
                    parcelObtain.writeTypedObject(pictureInPictureParams, 0);
                    this.mRemote.transact(38, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return parcelObtain2.readBoolean();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.app.IActivityClientController
            public void setPictureInPictureParams(IBinder iBinder, PictureInPictureParams pictureInPictureParams) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(IActivityClientController.DESCRIPTOR);
                    parcelObtain.writeStrongBinder(iBinder);
                    parcelObtain.writeTypedObject(pictureInPictureParams, 0);
                    this.mRemote.transact(39, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.app.IActivityClientController
            public void setShouldDockBigOverlays(IBinder iBinder, boolean z) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                try {
                    parcelObtain.writeInterfaceToken(IActivityClientController.DESCRIPTOR);
                    parcelObtain.writeStrongBinder(iBinder);
                    parcelObtain.writeBoolean(z);
                    this.mRemote.transact(40, parcelObtain, null, 1);
                } finally {
                    parcelObtain.recycle();
                }
            }

            @Override // android.app.IActivityClientController
            public void toggleFreeformWindowingMode(IBinder iBinder) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(IActivityClientController.DESCRIPTOR);
                    parcelObtain.writeStrongBinder(iBinder);
                    this.mRemote.transact(41, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.app.IActivityClientController
            public void requestMultiwindowFullscreen(IBinder iBinder, int i, IRemoteCallback iRemoteCallback) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                try {
                    parcelObtain.writeInterfaceToken(IActivityClientController.DESCRIPTOR);
                    parcelObtain.writeStrongBinder(iBinder);
                    parcelObtain.writeInt(i);
                    parcelObtain.writeStrongInterface(iRemoteCallback);
                    this.mRemote.transact(42, parcelObtain, null, 1);
                } finally {
                    parcelObtain.recycle();
                }
            }

            @Override // android.app.IActivityClientController
            public void startLockTaskModeByToken(IBinder iBinder) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(IActivityClientController.DESCRIPTOR);
                    parcelObtain.writeStrongBinder(iBinder);
                    this.mRemote.transact(43, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.app.IActivityClientController
            public void stopLockTaskModeByToken(IBinder iBinder) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(IActivityClientController.DESCRIPTOR);
                    parcelObtain.writeStrongBinder(iBinder);
                    this.mRemote.transact(44, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.app.IActivityClientController
            public void showLockTaskEscapeMessage(IBinder iBinder) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                try {
                    parcelObtain.writeInterfaceToken(IActivityClientController.DESCRIPTOR);
                    parcelObtain.writeStrongBinder(iBinder);
                    this.mRemote.transact(45, parcelObtain, null, 1);
                } finally {
                    parcelObtain.recycle();
                }
            }

            @Override // android.app.IActivityClientController
            public void setTaskDescription(IBinder iBinder, ActivityManager.TaskDescription taskDescription) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(IActivityClientController.DESCRIPTOR);
                    parcelObtain.writeStrongBinder(iBinder);
                    parcelObtain.writeTypedObject(taskDescription, 0);
                    this.mRemote.transact(46, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.app.IActivityClientController
            public boolean showAssistFromActivity(IBinder iBinder, Bundle bundle) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(IActivityClientController.DESCRIPTOR);
                    parcelObtain.writeStrongBinder(iBinder);
                    parcelObtain.writeTypedObject(bundle, 0);
                    this.mRemote.transact(47, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return parcelObtain2.readBoolean();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.app.IActivityClientController
            public boolean isRootVoiceInteraction(IBinder iBinder) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(IActivityClientController.DESCRIPTOR);
                    parcelObtain.writeStrongBinder(iBinder);
                    this.mRemote.transact(48, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return parcelObtain2.readBoolean();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.app.IActivityClientController
            public void startLocalVoiceInteraction(IBinder iBinder, Bundle bundle) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(IActivityClientController.DESCRIPTOR);
                    parcelObtain.writeStrongBinder(iBinder);
                    parcelObtain.writeTypedObject(bundle, 0);
                    this.mRemote.transact(49, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.app.IActivityClientController
            public void stopLocalVoiceInteraction(IBinder iBinder) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(IActivityClientController.DESCRIPTOR);
                    parcelObtain.writeStrongBinder(iBinder);
                    this.mRemote.transact(50, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.app.IActivityClientController
            public void setShowWhenLocked(IBinder iBinder, boolean z) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                try {
                    parcelObtain.writeInterfaceToken(IActivityClientController.DESCRIPTOR);
                    parcelObtain.writeStrongBinder(iBinder);
                    parcelObtain.writeBoolean(z);
                    this.mRemote.transact(51, parcelObtain, null, 1);
                } finally {
                    parcelObtain.recycle();
                }
            }

            @Override // android.app.IActivityClientController
            public void setInheritShowWhenLocked(IBinder iBinder, boolean z) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                try {
                    parcelObtain.writeInterfaceToken(IActivityClientController.DESCRIPTOR);
                    parcelObtain.writeStrongBinder(iBinder);
                    parcelObtain.writeBoolean(z);
                    this.mRemote.transact(52, parcelObtain, null, 1);
                } finally {
                    parcelObtain.recycle();
                }
            }

            @Override // android.app.IActivityClientController
            public void setTurnScreenOn(IBinder iBinder, boolean z) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(IActivityClientController.DESCRIPTOR);
                    parcelObtain.writeStrongBinder(iBinder);
                    parcelObtain.writeBoolean(z);
                    this.mRemote.transact(53, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.app.IActivityClientController
            public void setAllowCrossUidActivitySwitchFromBelow(IBinder iBinder, boolean z) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                try {
                    parcelObtain.writeInterfaceToken(IActivityClientController.DESCRIPTOR);
                    parcelObtain.writeStrongBinder(iBinder);
                    parcelObtain.writeBoolean(z);
                    this.mRemote.transact(54, parcelObtain, null, 1);
                } finally {
                    parcelObtain.recycle();
                }
            }

            @Override // android.app.IActivityClientController
            public void reportActivityFullyDrawn(IBinder iBinder, boolean z) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                try {
                    parcelObtain.writeInterfaceToken(IActivityClientController.DESCRIPTOR);
                    parcelObtain.writeStrongBinder(iBinder);
                    parcelObtain.writeBoolean(z);
                    this.mRemote.transact(55, parcelObtain, null, 1);
                } finally {
                    parcelObtain.recycle();
                }
            }

            @Override // android.app.IActivityClientController
            public void overrideActivityTransition(IBinder iBinder, boolean z, int i, int i2, int i3) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                try {
                    parcelObtain.writeInterfaceToken(IActivityClientController.DESCRIPTOR);
                    parcelObtain.writeStrongBinder(iBinder);
                    parcelObtain.writeBoolean(z);
                    parcelObtain.writeInt(i);
                    parcelObtain.writeInt(i2);
                    parcelObtain.writeInt(i3);
                    this.mRemote.transact(56, parcelObtain, null, 1);
                } finally {
                    parcelObtain.recycle();
                }
            }

            @Override // android.app.IActivityClientController
            public void clearOverrideActivityTransition(IBinder iBinder, boolean z) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                try {
                    parcelObtain.writeInterfaceToken(IActivityClientController.DESCRIPTOR);
                    parcelObtain.writeStrongBinder(iBinder);
                    parcelObtain.writeBoolean(z);
                    this.mRemote.transact(57, parcelObtain, null, 1);
                } finally {
                    parcelObtain.recycle();
                }
            }

            @Override // android.app.IActivityClientController
            public void overridePendingTransition(IBinder iBinder, String str, int i, int i2, int i3) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(IActivityClientController.DESCRIPTOR);
                    parcelObtain.writeStrongBinder(iBinder);
                    parcelObtain.writeString(str);
                    parcelObtain.writeInt(i);
                    parcelObtain.writeInt(i2);
                    parcelObtain.writeInt(i3);
                    this.mRemote.transact(58, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.app.IActivityClientController
            public int setVrMode(IBinder iBinder, boolean z, ComponentName componentName) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(IActivityClientController.DESCRIPTOR);
                    parcelObtain.writeStrongBinder(iBinder);
                    parcelObtain.writeBoolean(z);
                    parcelObtain.writeTypedObject(componentName, 0);
                    this.mRemote.transact(59, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return parcelObtain2.readInt();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.app.IActivityClientController
            public void setRecentsScreenshotEnabled(IBinder iBinder, boolean z) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                try {
                    parcelObtain.writeInterfaceToken(IActivityClientController.DESCRIPTOR);
                    parcelObtain.writeStrongBinder(iBinder);
                    parcelObtain.writeBoolean(z);
                    this.mRemote.transact(60, parcelObtain, null, 1);
                } finally {
                    parcelObtain.recycle();
                }
            }

            @Override // android.app.IActivityClientController
            public void invalidateHomeTaskSnapshot(IBinder iBinder) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(IActivityClientController.DESCRIPTOR);
                    parcelObtain.writeStrongBinder(iBinder);
                    this.mRemote.transact(61, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.app.IActivityClientController
            public void dismissKeyguard(IBinder iBinder, IKeyguardDismissCallback iKeyguardDismissCallback, CharSequence charSequence) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(IActivityClientController.DESCRIPTOR);
                    parcelObtain.writeStrongBinder(iBinder);
                    parcelObtain.writeStrongInterface(iKeyguardDismissCallback);
                    if (charSequence != null) {
                        parcelObtain.writeInt(1);
                        TextUtils.writeToParcel(charSequence, parcelObtain, 0);
                    } else {
                        parcelObtain.writeInt(0);
                    }
                    this.mRemote.transact(62, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.app.IActivityClientController
            public void registerRemoteAnimations(IBinder iBinder, RemoteAnimationDefinition remoteAnimationDefinition) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(IActivityClientController.DESCRIPTOR);
                    parcelObtain.writeStrongBinder(iBinder);
                    parcelObtain.writeTypedObject(remoteAnimationDefinition, 0);
                    this.mRemote.transact(63, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.app.IActivityClientController
            public void unregisterRemoteAnimations(IBinder iBinder) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(IActivityClientController.DESCRIPTOR);
                    parcelObtain.writeStrongBinder(iBinder);
                    this.mRemote.transact(64, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.app.IActivityClientController
            public void onBackPressed(IBinder iBinder, IRequestFinishCallback iRequestFinishCallback) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                try {
                    parcelObtain.writeInterfaceToken(IActivityClientController.DESCRIPTOR);
                    parcelObtain.writeStrongBinder(iBinder);
                    parcelObtain.writeStrongInterface(iRequestFinishCallback);
                    this.mRemote.transact(65, parcelObtain, null, 1);
                } finally {
                    parcelObtain.recycle();
                }
            }

            @Override // android.app.IActivityClientController
            public void splashScreenAttached(IBinder iBinder) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                try {
                    parcelObtain.writeInterfaceToken(IActivityClientController.DESCRIPTOR);
                    parcelObtain.writeStrongBinder(iBinder);
                    this.mRemote.transact(66, parcelObtain, null, 1);
                } finally {
                    parcelObtain.recycle();
                }
            }

            @Override // android.app.IActivityClientController
            public void enableTaskLocaleOverride(IBinder iBinder) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(IActivityClientController.DESCRIPTOR);
                    parcelObtain.writeStrongBinder(iBinder);
                    this.mRemote.transact(67, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.app.IActivityClientController
            public boolean isRequestedToLaunchInTaskFragment(IBinder iBinder, IBinder iBinder2) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(IActivityClientController.DESCRIPTOR);
                    parcelObtain.writeStrongBinder(iBinder);
                    parcelObtain.writeStrongBinder(iBinder2);
                    this.mRemote.transact(68, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return parcelObtain2.readBoolean();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.app.IActivityClientController
            public void setActivityRecordInputSinkEnabled(IBinder iBinder, boolean z) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                try {
                    parcelObtain.writeInterfaceToken(IActivityClientController.DESCRIPTOR);
                    parcelObtain.writeStrongBinder(iBinder);
                    parcelObtain.writeBoolean(z);
                    this.mRemote.transact(69, parcelObtain, null, 1);
                } finally {
                    parcelObtain.recycle();
                }
            }

            @Override // android.app.IActivityClientController
            public boolean shouldPreloadHardwareRenderer(int i) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(IActivityClientController.DESCRIPTOR);
                    parcelObtain.writeInt(i);
                    this.mRemote.transact(70, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return parcelObtain2.readBoolean();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.app.IActivityClientController
            public boolean convertFromTranslucentOp(IBinder iBinder, boolean z) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(IActivityClientController.DESCRIPTOR);
                    parcelObtain.writeStrongBinder(iBinder);
                    parcelObtain.writeBoolean(z);
                    this.mRemote.transact(71, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return parcelObtain2.readBoolean();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.app.IActivityClientController
            public void overridePendingTaskTransition(IBinder iBinder, String str, int i, int i2) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(IActivityClientController.DESCRIPTOR);
                    parcelObtain.writeStrongBinder(iBinder);
                    parcelObtain.writeString(str);
                    parcelObtain.writeInt(i);
                    parcelObtain.writeInt(i2);
                    this.mRemote.transact(72, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.app.IActivityClientController
            public void adjustPopOverOptions(IBinder iBinder, int[] iArr, int[] iArr2, Point[] pointArr, int[] iArr3) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(IActivityClientController.DESCRIPTOR);
                    parcelObtain.writeStrongBinder(iBinder);
                    parcelObtain.writeIntArray(iArr);
                    parcelObtain.writeIntArray(iArr2);
                    parcelObtain.writeTypedArray(pointArr, 0);
                    parcelObtain.writeIntArray(iArr3);
                    this.mRemote.transact(73, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }
        }
    }
}
