package android.app;

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

public interface IActivityClientController extends IInterface {
    public static final String DESCRIPTOR = "android.app.IActivityClientController";

    void activityDestroyed(IBinder iBinder) throws RemoteException;

    void activityIdle(IBinder iBinder, Configuration configuration, boolean z)
            throws RemoteException;

    void activityLocalRelaunch(IBinder iBinder) throws RemoteException;

    void activityPaused(IBinder iBinder) throws RemoteException;

    void activityRefreshed(IBinder iBinder) throws RemoteException;

    void activityRelaunched(IBinder iBinder) throws RemoteException;

    void activityResumed(IBinder iBinder, boolean z) throws RemoteException;

    void activityStopped(
            IBinder iBinder,
            Bundle bundle,
            PersistableBundle persistableBundle,
            CharSequence charSequence)
            throws RemoteException;

    void activityTopResumedStateLost() throws RemoteException;

    void adjustPopOverOptions(
            IBinder iBinder, int[] iArr, int[] iArr2, Point[] pointArr, int[] iArr3)
            throws RemoteException;

    int checkActivityCallerContentUriPermission(
            IBinder iBinder, IBinder iBinder2, Uri uri, int i, int i2) throws RemoteException;

    void clearOverrideActivityTransition(IBinder iBinder, boolean z) throws RemoteException;

    boolean convertFromTranslucent(IBinder iBinder) throws RemoteException;

    boolean convertFromTranslucentOp(IBinder iBinder, boolean z) throws RemoteException;

    boolean convertToTranslucent(IBinder iBinder, Bundle bundle) throws RemoteException;

    void dismissKeyguard(
            IBinder iBinder,
            IKeyguardDismissCallback iKeyguardDismissCallback,
            CharSequence charSequence)
            throws RemoteException;

    void enableTaskLocaleOverride(IBinder iBinder) throws RemoteException;

    boolean enterPictureInPictureMode(
            IBinder iBinder, PictureInPictureParams pictureInPictureParams) throws RemoteException;

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

    boolean isRequestedToLaunchInTaskFragment(IBinder iBinder, IBinder iBinder2)
            throws RemoteException;

    boolean isRootVoiceInteraction(IBinder iBinder) throws RemoteException;

    boolean isTopOfTask(IBinder iBinder) throws RemoteException;

    boolean moveActivityTaskToBack(IBinder iBinder, boolean z) throws RemoteException;

    boolean navigateUpTo(IBinder iBinder, Intent intent, String str, int i, Intent intent2)
            throws RemoteException;

    void onBackPressed(IBinder iBinder, IRequestFinishCallback iRequestFinishCallback)
            throws RemoteException;

    void overrideActivityTransition(IBinder iBinder, boolean z, int i, int i2, int i3)
            throws RemoteException;

    void overridePendingTaskTransition(IBinder iBinder, String str, int i, int i2)
            throws RemoteException;

    void overridePendingTransition(IBinder iBinder, String str, int i, int i2, int i3)
            throws RemoteException;

    void registerRemoteAnimations(
            IBinder iBinder, RemoteAnimationDefinition remoteAnimationDefinition)
            throws RemoteException;

    boolean releaseActivityInstance(IBinder iBinder) throws RemoteException;

    void reportActivityFullyDrawn(IBinder iBinder, boolean z) throws RemoteException;

    void reportSizeConfigurations(
            IBinder iBinder, SizeConfigurationBuckets sizeConfigurationBuckets)
            throws RemoteException;

    void requestMultiwindowFullscreen(IBinder iBinder, int i, IRemoteCallback iRemoteCallback)
            throws RemoteException;

    void setActivityRecordInputSinkEnabled(IBinder iBinder, boolean z) throws RemoteException;

    void setAllowCrossUidActivitySwitchFromBelow(IBinder iBinder, boolean z) throws RemoteException;

    void setForceSendResultForMediaProjection(IBinder iBinder) throws RemoteException;

    void setImmersive(IBinder iBinder, boolean z) throws RemoteException;

    void setInheritShowWhenLocked(IBinder iBinder, boolean z) throws RemoteException;

    void setPictureInPictureParams(IBinder iBinder, PictureInPictureParams pictureInPictureParams)
            throws RemoteException;

    void setRecentsScreenshotEnabled(IBinder iBinder, boolean z) throws RemoteException;

    void setRequestedOrientation(IBinder iBinder, int i) throws RemoteException;

    void setShouldDockBigOverlays(IBinder iBinder, boolean z) throws RemoteException;

    void setShowWhenLocked(IBinder iBinder, boolean z) throws RemoteException;

    void setTaskDescription(IBinder iBinder, ActivityManager.TaskDescription taskDescription)
            throws RemoteException;

    void setTurnScreenOn(IBinder iBinder, boolean z) throws RemoteException;

    int setVrMode(IBinder iBinder, boolean z, ComponentName componentName) throws RemoteException;

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

    public static class Default implements IActivityClientController {
        @Override // android.app.IActivityClientController
        public void activityIdle(IBinder token, Configuration config, boolean stopProfiling)
                throws RemoteException {}

        @Override // android.app.IActivityClientController
        public void activityResumed(IBinder token, boolean handleSplashScreenExit)
                throws RemoteException {}

        @Override // android.app.IActivityClientController
        public void activityRefreshed(IBinder token) throws RemoteException {}

        @Override // android.app.IActivityClientController
        public void activityTopResumedStateLost() throws RemoteException {}

        @Override // android.app.IActivityClientController
        public void activityPaused(IBinder token) throws RemoteException {}

        @Override // android.app.IActivityClientController
        public void activityStopped(
                IBinder token,
                Bundle state,
                PersistableBundle persistentState,
                CharSequence description)
                throws RemoteException {}

        @Override // android.app.IActivityClientController
        public void activityDestroyed(IBinder token) throws RemoteException {}

        @Override // android.app.IActivityClientController
        public void activityLocalRelaunch(IBinder token) throws RemoteException {}

        @Override // android.app.IActivityClientController
        public void activityRelaunched(IBinder token) throws RemoteException {}

        @Override // android.app.IActivityClientController
        public void reportSizeConfigurations(
                IBinder token, SizeConfigurationBuckets sizeConfigurations)
                throws RemoteException {}

        @Override // android.app.IActivityClientController
        public boolean moveActivityTaskToBack(IBinder token, boolean nonRoot)
                throws RemoteException {
            return false;
        }

        @Override // android.app.IActivityClientController
        public boolean shouldUpRecreateTask(IBinder token, String destAffinity)
                throws RemoteException {
            return false;
        }

        @Override // android.app.IActivityClientController
        public boolean navigateUpTo(
                IBinder token,
                Intent target,
                String resolvedType,
                int resultCode,
                Intent resultData)
                throws RemoteException {
            return false;
        }

        @Override // android.app.IActivityClientController
        public boolean releaseActivityInstance(IBinder token) throws RemoteException {
            return false;
        }

        @Override // android.app.IActivityClientController
        public boolean finishActivity(IBinder token, int code, Intent data, int finishTask)
                throws RemoteException {
            return false;
        }

        @Override // android.app.IActivityClientController
        public boolean finishActivityAffinity(IBinder token) throws RemoteException {
            return false;
        }

        @Override // android.app.IActivityClientController
        public void finishSubActivity(IBinder token, String resultWho, int requestCode)
                throws RemoteException {}

        @Override // android.app.IActivityClientController
        public void setForceSendResultForMediaProjection(IBinder token) throws RemoteException {}

        @Override // android.app.IActivityClientController
        public boolean isTopOfTask(IBinder token) throws RemoteException {
            return false;
        }

        @Override // android.app.IActivityClientController
        public boolean willActivityBeVisible(IBinder token) throws RemoteException {
            return false;
        }

        @Override // android.app.IActivityClientController
        public int getDisplayId(IBinder activityToken) throws RemoteException {
            return 0;
        }

        @Override // android.app.IActivityClientController
        public int getTaskForActivity(IBinder token, boolean onlyRoot) throws RemoteException {
            return 0;
        }

        @Override // android.app.IActivityClientController
        public Configuration getTaskConfiguration(IBinder activityToken) throws RemoteException {
            return null;
        }

        @Override // android.app.IActivityClientController
        public IBinder getActivityTokenBelow(IBinder token) throws RemoteException {
            return null;
        }

        @Override // android.app.IActivityClientController
        public ComponentName getCallingActivity(IBinder token) throws RemoteException {
            return null;
        }

        @Override // android.app.IActivityClientController
        public String getCallingPackage(IBinder token) throws RemoteException {
            return null;
        }

        @Override // android.app.IActivityClientController
        public int getLaunchedFromUid(IBinder token) throws RemoteException {
            return 0;
        }

        @Override // android.app.IActivityClientController
        public int getActivityCallerUid(IBinder activityToken, IBinder callerToken)
                throws RemoteException {
            return 0;
        }

        @Override // android.app.IActivityClientController
        public String getLaunchedFromPackage(IBinder token) throws RemoteException {
            return null;
        }

        @Override // android.app.IActivityClientController
        public String getActivityCallerPackage(IBinder activityToken, IBinder callerToken)
                throws RemoteException {
            return null;
        }

        @Override // android.app.IActivityClientController
        public int checkActivityCallerContentUriPermission(
                IBinder activityToken, IBinder callerToken, Uri uri, int modeFlags, int userId)
                throws RemoteException {
            return 0;
        }

        @Override // android.app.IActivityClientController
        public void setRequestedOrientation(IBinder token, int requestedOrientation)
                throws RemoteException {}

        @Override // android.app.IActivityClientController
        public int getRequestedOrientation(IBinder token) throws RemoteException {
            return 0;
        }

        @Override // android.app.IActivityClientController
        public boolean convertFromTranslucent(IBinder token) throws RemoteException {
            return false;
        }

        @Override // android.app.IActivityClientController
        public boolean convertToTranslucent(IBinder token, Bundle options) throws RemoteException {
            return false;
        }

        @Override // android.app.IActivityClientController
        public boolean isImmersive(IBinder token) throws RemoteException {
            return false;
        }

        @Override // android.app.IActivityClientController
        public void setImmersive(IBinder token, boolean immersive) throws RemoteException {}

        @Override // android.app.IActivityClientController
        public boolean enterPictureInPictureMode(IBinder token, PictureInPictureParams params)
                throws RemoteException {
            return false;
        }

        @Override // android.app.IActivityClientController
        public void setPictureInPictureParams(IBinder token, PictureInPictureParams params)
                throws RemoteException {}

        @Override // android.app.IActivityClientController
        public void setShouldDockBigOverlays(IBinder token, boolean shouldDockBigOverlays)
                throws RemoteException {}

        @Override // android.app.IActivityClientController
        public void toggleFreeformWindowingMode(IBinder token) throws RemoteException {}

        @Override // android.app.IActivityClientController
        public void requestMultiwindowFullscreen(
                IBinder token, int request, IRemoteCallback callback) throws RemoteException {}

        @Override // android.app.IActivityClientController
        public void startLockTaskModeByToken(IBinder token) throws RemoteException {}

        @Override // android.app.IActivityClientController
        public void stopLockTaskModeByToken(IBinder token) throws RemoteException {}

        @Override // android.app.IActivityClientController
        public void showLockTaskEscapeMessage(IBinder token) throws RemoteException {}

        @Override // android.app.IActivityClientController
        public void setTaskDescription(IBinder token, ActivityManager.TaskDescription values)
                throws RemoteException {}

        @Override // android.app.IActivityClientController
        public boolean showAssistFromActivity(IBinder token, Bundle args) throws RemoteException {
            return false;
        }

        @Override // android.app.IActivityClientController
        public boolean isRootVoiceInteraction(IBinder token) throws RemoteException {
            return false;
        }

        @Override // android.app.IActivityClientController
        public void startLocalVoiceInteraction(IBinder token, Bundle options)
                throws RemoteException {}

        @Override // android.app.IActivityClientController
        public void stopLocalVoiceInteraction(IBinder token) throws RemoteException {}

        @Override // android.app.IActivityClientController
        public void setShowWhenLocked(IBinder token, boolean showWhenLocked)
                throws RemoteException {}

        @Override // android.app.IActivityClientController
        public void setInheritShowWhenLocked(IBinder token, boolean setInheritShownWhenLocked)
                throws RemoteException {}

        @Override // android.app.IActivityClientController
        public void setTurnScreenOn(IBinder token, boolean turnScreenOn) throws RemoteException {}

        @Override // android.app.IActivityClientController
        public void setAllowCrossUidActivitySwitchFromBelow(IBinder token, boolean allowed)
                throws RemoteException {}

        @Override // android.app.IActivityClientController
        public void reportActivityFullyDrawn(IBinder token, boolean restoredFromBundle)
                throws RemoteException {}

        @Override // android.app.IActivityClientController
        public void overrideActivityTransition(
                IBinder token, boolean open, int enterAnim, int exitAnim, int backgroundColor)
                throws RemoteException {}

        @Override // android.app.IActivityClientController
        public void clearOverrideActivityTransition(IBinder token, boolean open)
                throws RemoteException {}

        @Override // android.app.IActivityClientController
        public void overridePendingTransition(
                IBinder token, String packageName, int enterAnim, int exitAnim, int backgroundColor)
                throws RemoteException {}

        @Override // android.app.IActivityClientController
        public int setVrMode(IBinder token, boolean enabled, ComponentName packageName)
                throws RemoteException {
            return 0;
        }

        @Override // android.app.IActivityClientController
        public void setRecentsScreenshotEnabled(IBinder token, boolean enabled)
                throws RemoteException {}

        @Override // android.app.IActivityClientController
        public void invalidateHomeTaskSnapshot(IBinder homeToken) throws RemoteException {}

        @Override // android.app.IActivityClientController
        public void dismissKeyguard(
                IBinder token, IKeyguardDismissCallback callback, CharSequence message)
                throws RemoteException {}

        @Override // android.app.IActivityClientController
        public void registerRemoteAnimations(IBinder token, RemoteAnimationDefinition definition)
                throws RemoteException {}

        @Override // android.app.IActivityClientController
        public void unregisterRemoteAnimations(IBinder token) throws RemoteException {}

        @Override // android.app.IActivityClientController
        public void onBackPressed(IBinder activityToken, IRequestFinishCallback callback)
                throws RemoteException {}

        @Override // android.app.IActivityClientController
        public void splashScreenAttached(IBinder token) throws RemoteException {}

        @Override // android.app.IActivityClientController
        public void enableTaskLocaleOverride(IBinder token) throws RemoteException {}

        @Override // android.app.IActivityClientController
        public boolean isRequestedToLaunchInTaskFragment(
                IBinder activityToken, IBinder taskFragmentToken) throws RemoteException {
            return false;
        }

        @Override // android.app.IActivityClientController
        public void setActivityRecordInputSinkEnabled(IBinder activityToken, boolean enabled)
                throws RemoteException {}

        @Override // android.app.IActivityClientController
        public boolean convertFromTranslucentOp(IBinder token, boolean skipSetWindowOpaque)
                throws RemoteException {
            return false;
        }

        @Override // android.app.IActivityClientController
        public void overridePendingTaskTransition(
                IBinder token, String packageName, int enterAnim, int exitAnim)
                throws RemoteException {}

        @Override // android.app.IActivityClientController
        public void adjustPopOverOptions(
                IBinder token, int[] widthDp, int[] heightDp, Point[] marginDp, int[] position)
                throws RemoteException {}

        @Override // android.os.IInterface
        public IBinder asBinder() {
            return null;
        }
    }

    public abstract static class Stub extends Binder implements IActivityClientController {
        static final int TRANSACTION_activityDestroyed = 7;
        static final int TRANSACTION_activityIdle = 1;
        static final int TRANSACTION_activityLocalRelaunch = 8;
        static final int TRANSACTION_activityPaused = 5;
        static final int TRANSACTION_activityRefreshed = 3;
        static final int TRANSACTION_activityRelaunched = 9;
        static final int TRANSACTION_activityResumed = 2;
        static final int TRANSACTION_activityStopped = 6;
        static final int TRANSACTION_activityTopResumedStateLost = 4;
        static final int TRANSACTION_adjustPopOverOptions = 72;
        static final int TRANSACTION_checkActivityCallerContentUriPermission = 31;
        static final int TRANSACTION_clearOverrideActivityTransition = 57;
        static final int TRANSACTION_convertFromTranslucent = 34;
        static final int TRANSACTION_convertFromTranslucentOp = 70;
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
        static final int TRANSACTION_overridePendingTaskTransition = 71;
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

        public Stub() {
            attachInterface(this, IActivityClientController.DESCRIPTOR);
        }

        public static IActivityClientController asInterface(IBinder obj) {
            if (obj == null) {
                return null;
            }
            IInterface iin = obj.queryLocalInterface(IActivityClientController.DESCRIPTOR);
            if (iin != null && (iin instanceof IActivityClientController)) {
                return (IActivityClientController) iin;
            }
            return new Proxy(obj);
        }

        @Override // android.os.IInterface
        public IBinder asBinder() {
            return this;
        }

        public static String getDefaultTransactionName(int transactionCode) {
            switch (transactionCode) {
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
                    return "convertFromTranslucentOp";
                case 71:
                    return "overridePendingTaskTransition";
                case 72:
                    return "adjustPopOverOptions";
                default:
                    return null;
            }
        }

        @Override // android.os.Binder
        public String getTransactionName(int transactionCode) {
            return getDefaultTransactionName(transactionCode);
        }

        @Override // android.os.Binder
        public boolean onTransact(int code, Parcel data, Parcel reply, int flags)
                throws RemoteException {
            if (code >= 1 && code <= 16777215) {
                data.enforceInterface(IActivityClientController.DESCRIPTOR);
            }
            if (code == 1598968902) {
                reply.writeString(IActivityClientController.DESCRIPTOR);
                return true;
            }
            switch (code) {
                case 1:
                    IBinder _arg0 = data.readStrongBinder();
                    Configuration _arg1 =
                            (Configuration) data.readTypedObject(Configuration.CREATOR);
                    boolean _arg2 = data.readBoolean();
                    data.enforceNoDataAvail();
                    activityIdle(_arg0, _arg1, _arg2);
                    return true;
                case 2:
                    IBinder _arg02 = data.readStrongBinder();
                    boolean _arg12 = data.readBoolean();
                    data.enforceNoDataAvail();
                    activityResumed(_arg02, _arg12);
                    return true;
                case 3:
                    IBinder _arg03 = data.readStrongBinder();
                    data.enforceNoDataAvail();
                    activityRefreshed(_arg03);
                    return true;
                case 4:
                    activityTopResumedStateLost();
                    reply.writeNoException();
                    return true;
                case 5:
                    IBinder _arg04 = data.readStrongBinder();
                    data.enforceNoDataAvail();
                    activityPaused(_arg04);
                    reply.writeNoException();
                    return true;
                case 6:
                    IBinder _arg05 = data.readStrongBinder();
                    Bundle _arg13 = (Bundle) data.readTypedObject(Bundle.CREATOR);
                    PersistableBundle _arg22 =
                            (PersistableBundle) data.readTypedObject(PersistableBundle.CREATOR);
                    CharSequence _arg3 =
                            (CharSequence) data.readTypedObject(TextUtils.CHAR_SEQUENCE_CREATOR);
                    data.enforceNoDataAvail();
                    activityStopped(_arg05, _arg13, _arg22, _arg3);
                    return true;
                case 7:
                    IBinder _arg06 = data.readStrongBinder();
                    data.enforceNoDataAvail();
                    activityDestroyed(_arg06);
                    return true;
                case 8:
                    IBinder _arg07 = data.readStrongBinder();
                    data.enforceNoDataAvail();
                    activityLocalRelaunch(_arg07);
                    return true;
                case 9:
                    IBinder _arg08 = data.readStrongBinder();
                    data.enforceNoDataAvail();
                    activityRelaunched(_arg08);
                    return true;
                case 10:
                    IBinder _arg09 = data.readStrongBinder();
                    SizeConfigurationBuckets _arg14 =
                            (SizeConfigurationBuckets)
                                    data.readTypedObject(SizeConfigurationBuckets.CREATOR);
                    data.enforceNoDataAvail();
                    reportSizeConfigurations(_arg09, _arg14);
                    return true;
                case 11:
                    IBinder _arg010 = data.readStrongBinder();
                    boolean _arg15 = data.readBoolean();
                    data.enforceNoDataAvail();
                    boolean _result = moveActivityTaskToBack(_arg010, _arg15);
                    reply.writeNoException();
                    reply.writeBoolean(_result);
                    return true;
                case 12:
                    IBinder _arg011 = data.readStrongBinder();
                    String _arg16 = data.readString();
                    data.enforceNoDataAvail();
                    boolean _result2 = shouldUpRecreateTask(_arg011, _arg16);
                    reply.writeNoException();
                    reply.writeBoolean(_result2);
                    return true;
                case 13:
                    IBinder _arg012 = data.readStrongBinder();
                    Intent _arg17 = (Intent) data.readTypedObject(Intent.CREATOR);
                    String _arg23 = data.readString();
                    int _arg32 = data.readInt();
                    Intent _arg4 = (Intent) data.readTypedObject(Intent.CREATOR);
                    data.enforceNoDataAvail();
                    boolean _result3 = navigateUpTo(_arg012, _arg17, _arg23, _arg32, _arg4);
                    reply.writeNoException();
                    reply.writeBoolean(_result3);
                    return true;
                case 14:
                    IBinder _arg013 = data.readStrongBinder();
                    data.enforceNoDataAvail();
                    boolean _result4 = releaseActivityInstance(_arg013);
                    reply.writeNoException();
                    reply.writeBoolean(_result4);
                    return true;
                case 15:
                    IBinder _arg014 = data.readStrongBinder();
                    int _arg18 = data.readInt();
                    Intent _arg24 = (Intent) data.readTypedObject(Intent.CREATOR);
                    int _arg33 = data.readInt();
                    data.enforceNoDataAvail();
                    boolean _result5 = finishActivity(_arg014, _arg18, _arg24, _arg33);
                    reply.writeNoException();
                    reply.writeBoolean(_result5);
                    return true;
                case 16:
                    IBinder _arg015 = data.readStrongBinder();
                    data.enforceNoDataAvail();
                    boolean _result6 = finishActivityAffinity(_arg015);
                    reply.writeNoException();
                    reply.writeBoolean(_result6);
                    return true;
                case 17:
                    IBinder _arg016 = data.readStrongBinder();
                    String _arg19 = data.readString();
                    int _arg25 = data.readInt();
                    data.enforceNoDataAvail();
                    finishSubActivity(_arg016, _arg19, _arg25);
                    reply.writeNoException();
                    return true;
                case 18:
                    IBinder _arg017 = data.readStrongBinder();
                    data.enforceNoDataAvail();
                    setForceSendResultForMediaProjection(_arg017);
                    reply.writeNoException();
                    return true;
                case 19:
                    IBinder _arg018 = data.readStrongBinder();
                    data.enforceNoDataAvail();
                    boolean _result7 = isTopOfTask(_arg018);
                    reply.writeNoException();
                    reply.writeBoolean(_result7);
                    return true;
                case 20:
                    IBinder _arg019 = data.readStrongBinder();
                    data.enforceNoDataAvail();
                    boolean _result8 = willActivityBeVisible(_arg019);
                    reply.writeNoException();
                    reply.writeBoolean(_result8);
                    return true;
                case 21:
                    IBinder _arg020 = data.readStrongBinder();
                    data.enforceNoDataAvail();
                    int _result9 = getDisplayId(_arg020);
                    reply.writeNoException();
                    reply.writeInt(_result9);
                    return true;
                case 22:
                    IBinder _arg021 = data.readStrongBinder();
                    boolean _arg110 = data.readBoolean();
                    data.enforceNoDataAvail();
                    int _result10 = getTaskForActivity(_arg021, _arg110);
                    reply.writeNoException();
                    reply.writeInt(_result10);
                    return true;
                case 23:
                    IBinder _arg022 = data.readStrongBinder();
                    data.enforceNoDataAvail();
                    Configuration _result11 = getTaskConfiguration(_arg022);
                    reply.writeNoException();
                    reply.writeTypedObject(_result11, 1);
                    return true;
                case 24:
                    IBinder _arg023 = data.readStrongBinder();
                    data.enforceNoDataAvail();
                    IBinder _result12 = getActivityTokenBelow(_arg023);
                    reply.writeNoException();
                    reply.writeStrongBinder(_result12);
                    return true;
                case 25:
                    IBinder _arg024 = data.readStrongBinder();
                    data.enforceNoDataAvail();
                    ComponentName _result13 = getCallingActivity(_arg024);
                    reply.writeNoException();
                    reply.writeTypedObject(_result13, 1);
                    return true;
                case 26:
                    IBinder _arg025 = data.readStrongBinder();
                    data.enforceNoDataAvail();
                    String _result14 = getCallingPackage(_arg025);
                    reply.writeNoException();
                    reply.writeString(_result14);
                    return true;
                case 27:
                    IBinder _arg026 = data.readStrongBinder();
                    data.enforceNoDataAvail();
                    int _result15 = getLaunchedFromUid(_arg026);
                    reply.writeNoException();
                    reply.writeInt(_result15);
                    return true;
                case 28:
                    IBinder _arg027 = data.readStrongBinder();
                    IBinder _arg111 = data.readStrongBinder();
                    data.enforceNoDataAvail();
                    int _result16 = getActivityCallerUid(_arg027, _arg111);
                    reply.writeNoException();
                    reply.writeInt(_result16);
                    return true;
                case 29:
                    IBinder _arg028 = data.readStrongBinder();
                    data.enforceNoDataAvail();
                    String _result17 = getLaunchedFromPackage(_arg028);
                    reply.writeNoException();
                    reply.writeString(_result17);
                    return true;
                case 30:
                    IBinder _arg029 = data.readStrongBinder();
                    IBinder _arg112 = data.readStrongBinder();
                    data.enforceNoDataAvail();
                    String _result18 = getActivityCallerPackage(_arg029, _arg112);
                    reply.writeNoException();
                    reply.writeString(_result18);
                    return true;
                case 31:
                    IBinder _arg030 = data.readStrongBinder();
                    IBinder _arg113 = data.readStrongBinder();
                    Uri _arg26 = (Uri) data.readTypedObject(Uri.CREATOR);
                    int _arg34 = data.readInt();
                    int _arg42 = data.readInt();
                    data.enforceNoDataAvail();
                    int _result19 =
                            checkActivityCallerContentUriPermission(
                                    _arg030, _arg113, _arg26, _arg34, _arg42);
                    reply.writeNoException();
                    reply.writeInt(_result19);
                    return true;
                case 32:
                    IBinder _arg031 = data.readStrongBinder();
                    int _arg114 = data.readInt();
                    data.enforceNoDataAvail();
                    setRequestedOrientation(_arg031, _arg114);
                    reply.writeNoException();
                    return true;
                case 33:
                    IBinder _arg032 = data.readStrongBinder();
                    data.enforceNoDataAvail();
                    int _result20 = getRequestedOrientation(_arg032);
                    reply.writeNoException();
                    reply.writeInt(_result20);
                    return true;
                case 34:
                    IBinder _arg033 = data.readStrongBinder();
                    data.enforceNoDataAvail();
                    boolean _result21 = convertFromTranslucent(_arg033);
                    reply.writeNoException();
                    reply.writeBoolean(_result21);
                    return true;
                case 35:
                    IBinder _arg034 = data.readStrongBinder();
                    Bundle _arg115 = (Bundle) data.readTypedObject(Bundle.CREATOR);
                    data.enforceNoDataAvail();
                    boolean _result22 = convertToTranslucent(_arg034, _arg115);
                    reply.writeNoException();
                    reply.writeBoolean(_result22);
                    return true;
                case 36:
                    IBinder _arg035 = data.readStrongBinder();
                    data.enforceNoDataAvail();
                    boolean _result23 = isImmersive(_arg035);
                    reply.writeNoException();
                    reply.writeBoolean(_result23);
                    return true;
                case 37:
                    IBinder _arg036 = data.readStrongBinder();
                    boolean _arg116 = data.readBoolean();
                    data.enforceNoDataAvail();
                    setImmersive(_arg036, _arg116);
                    reply.writeNoException();
                    return true;
                case 38:
                    IBinder _arg037 = data.readStrongBinder();
                    PictureInPictureParams _arg117 =
                            (PictureInPictureParams)
                                    data.readTypedObject(PictureInPictureParams.CREATOR);
                    data.enforceNoDataAvail();
                    boolean _result24 = enterPictureInPictureMode(_arg037, _arg117);
                    reply.writeNoException();
                    reply.writeBoolean(_result24);
                    return true;
                case 39:
                    IBinder _arg038 = data.readStrongBinder();
                    PictureInPictureParams _arg118 =
                            (PictureInPictureParams)
                                    data.readTypedObject(PictureInPictureParams.CREATOR);
                    data.enforceNoDataAvail();
                    setPictureInPictureParams(_arg038, _arg118);
                    reply.writeNoException();
                    return true;
                case 40:
                    IBinder _arg039 = data.readStrongBinder();
                    boolean _arg119 = data.readBoolean();
                    data.enforceNoDataAvail();
                    setShouldDockBigOverlays(_arg039, _arg119);
                    return true;
                case 41:
                    IBinder _arg040 = data.readStrongBinder();
                    data.enforceNoDataAvail();
                    toggleFreeformWindowingMode(_arg040);
                    reply.writeNoException();
                    return true;
                case 42:
                    IBinder _arg041 = data.readStrongBinder();
                    int _arg120 = data.readInt();
                    IRemoteCallback _arg27 =
                            IRemoteCallback.Stub.asInterface(data.readStrongBinder());
                    data.enforceNoDataAvail();
                    requestMultiwindowFullscreen(_arg041, _arg120, _arg27);
                    return true;
                case 43:
                    IBinder _arg042 = data.readStrongBinder();
                    data.enforceNoDataAvail();
                    startLockTaskModeByToken(_arg042);
                    return true;
                case 44:
                    IBinder _arg043 = data.readStrongBinder();
                    data.enforceNoDataAvail();
                    stopLockTaskModeByToken(_arg043);
                    return true;
                case 45:
                    IBinder _arg044 = data.readStrongBinder();
                    data.enforceNoDataAvail();
                    showLockTaskEscapeMessage(_arg044);
                    return true;
                case 46:
                    IBinder _arg045 = data.readStrongBinder();
                    ActivityManager.TaskDescription _arg121 =
                            (ActivityManager.TaskDescription)
                                    data.readTypedObject(ActivityManager.TaskDescription.CREATOR);
                    data.enforceNoDataAvail();
                    setTaskDescription(_arg045, _arg121);
                    reply.writeNoException();
                    return true;
                case 47:
                    IBinder _arg046 = data.readStrongBinder();
                    Bundle _arg122 = (Bundle) data.readTypedObject(Bundle.CREATOR);
                    data.enforceNoDataAvail();
                    boolean _result25 = showAssistFromActivity(_arg046, _arg122);
                    reply.writeNoException();
                    reply.writeBoolean(_result25);
                    return true;
                case 48:
                    IBinder _arg047 = data.readStrongBinder();
                    data.enforceNoDataAvail();
                    boolean _result26 = isRootVoiceInteraction(_arg047);
                    reply.writeNoException();
                    reply.writeBoolean(_result26);
                    return true;
                case 49:
                    IBinder _arg048 = data.readStrongBinder();
                    Bundle _arg123 = (Bundle) data.readTypedObject(Bundle.CREATOR);
                    data.enforceNoDataAvail();
                    startLocalVoiceInteraction(_arg048, _arg123);
                    reply.writeNoException();
                    return true;
                case 50:
                    IBinder _arg049 = data.readStrongBinder();
                    data.enforceNoDataAvail();
                    stopLocalVoiceInteraction(_arg049);
                    reply.writeNoException();
                    return true;
                case 51:
                    IBinder _arg050 = data.readStrongBinder();
                    boolean _arg124 = data.readBoolean();
                    data.enforceNoDataAvail();
                    setShowWhenLocked(_arg050, _arg124);
                    return true;
                case 52:
                    IBinder _arg051 = data.readStrongBinder();
                    boolean _arg125 = data.readBoolean();
                    data.enforceNoDataAvail();
                    setInheritShowWhenLocked(_arg051, _arg125);
                    return true;
                case 53:
                    IBinder _arg052 = data.readStrongBinder();
                    boolean _arg126 = data.readBoolean();
                    data.enforceNoDataAvail();
                    setTurnScreenOn(_arg052, _arg126);
                    reply.writeNoException();
                    return true;
                case 54:
                    IBinder _arg053 = data.readStrongBinder();
                    boolean _arg127 = data.readBoolean();
                    data.enforceNoDataAvail();
                    setAllowCrossUidActivitySwitchFromBelow(_arg053, _arg127);
                    return true;
                case 55:
                    IBinder _arg054 = data.readStrongBinder();
                    boolean _arg128 = data.readBoolean();
                    data.enforceNoDataAvail();
                    reportActivityFullyDrawn(_arg054, _arg128);
                    return true;
                case 56:
                    IBinder _arg055 = data.readStrongBinder();
                    boolean _arg129 = data.readBoolean();
                    int _arg28 = data.readInt();
                    int _arg35 = data.readInt();
                    int _arg43 = data.readInt();
                    data.enforceNoDataAvail();
                    overrideActivityTransition(_arg055, _arg129, _arg28, _arg35, _arg43);
                    return true;
                case 57:
                    IBinder _arg056 = data.readStrongBinder();
                    boolean _arg130 = data.readBoolean();
                    data.enforceNoDataAvail();
                    clearOverrideActivityTransition(_arg056, _arg130);
                    return true;
                case 58:
                    IBinder _arg057 = data.readStrongBinder();
                    String _arg131 = data.readString();
                    int _arg29 = data.readInt();
                    int _arg36 = data.readInt();
                    int _arg44 = data.readInt();
                    data.enforceNoDataAvail();
                    overridePendingTransition(_arg057, _arg131, _arg29, _arg36, _arg44);
                    reply.writeNoException();
                    return true;
                case 59:
                    IBinder _arg058 = data.readStrongBinder();
                    boolean _arg132 = data.readBoolean();
                    ComponentName _arg210 =
                            (ComponentName) data.readTypedObject(ComponentName.CREATOR);
                    data.enforceNoDataAvail();
                    int _result27 = setVrMode(_arg058, _arg132, _arg210);
                    reply.writeNoException();
                    reply.writeInt(_result27);
                    return true;
                case 60:
                    IBinder _arg059 = data.readStrongBinder();
                    boolean _arg133 = data.readBoolean();
                    data.enforceNoDataAvail();
                    setRecentsScreenshotEnabled(_arg059, _arg133);
                    return true;
                case 61:
                    IBinder _arg060 = data.readStrongBinder();
                    data.enforceNoDataAvail();
                    invalidateHomeTaskSnapshot(_arg060);
                    reply.writeNoException();
                    return true;
                case 62:
                    IBinder _arg061 = data.readStrongBinder();
                    IKeyguardDismissCallback _arg134 =
                            IKeyguardDismissCallback.Stub.asInterface(data.readStrongBinder());
                    CharSequence _arg211 =
                            (CharSequence) data.readTypedObject(TextUtils.CHAR_SEQUENCE_CREATOR);
                    data.enforceNoDataAvail();
                    dismissKeyguard(_arg061, _arg134, _arg211);
                    reply.writeNoException();
                    return true;
                case 63:
                    IBinder _arg062 = data.readStrongBinder();
                    RemoteAnimationDefinition _arg135 =
                            (RemoteAnimationDefinition)
                                    data.readTypedObject(RemoteAnimationDefinition.CREATOR);
                    data.enforceNoDataAvail();
                    registerRemoteAnimations(_arg062, _arg135);
                    reply.writeNoException();
                    return true;
                case 64:
                    IBinder _arg063 = data.readStrongBinder();
                    data.enforceNoDataAvail();
                    unregisterRemoteAnimations(_arg063);
                    reply.writeNoException();
                    return true;
                case 65:
                    IBinder _arg064 = data.readStrongBinder();
                    IRequestFinishCallback _arg136 =
                            IRequestFinishCallback.Stub.asInterface(data.readStrongBinder());
                    data.enforceNoDataAvail();
                    onBackPressed(_arg064, _arg136);
                    return true;
                case 66:
                    IBinder _arg065 = data.readStrongBinder();
                    data.enforceNoDataAvail();
                    splashScreenAttached(_arg065);
                    return true;
                case 67:
                    IBinder _arg066 = data.readStrongBinder();
                    data.enforceNoDataAvail();
                    enableTaskLocaleOverride(_arg066);
                    reply.writeNoException();
                    return true;
                case 68:
                    IBinder _arg067 = data.readStrongBinder();
                    IBinder _arg137 = data.readStrongBinder();
                    data.enforceNoDataAvail();
                    boolean _result28 = isRequestedToLaunchInTaskFragment(_arg067, _arg137);
                    reply.writeNoException();
                    reply.writeBoolean(_result28);
                    return true;
                case 69:
                    IBinder _arg068 = data.readStrongBinder();
                    boolean _arg138 = data.readBoolean();
                    data.enforceNoDataAvail();
                    setActivityRecordInputSinkEnabled(_arg068, _arg138);
                    return true;
                case 70:
                    IBinder _arg069 = data.readStrongBinder();
                    boolean _arg139 = data.readBoolean();
                    data.enforceNoDataAvail();
                    boolean _result29 = convertFromTranslucentOp(_arg069, _arg139);
                    reply.writeNoException();
                    reply.writeBoolean(_result29);
                    return true;
                case 71:
                    IBinder _arg070 = data.readStrongBinder();
                    String _arg140 = data.readString();
                    int _arg212 = data.readInt();
                    int _arg37 = data.readInt();
                    data.enforceNoDataAvail();
                    overridePendingTaskTransition(_arg070, _arg140, _arg212, _arg37);
                    reply.writeNoException();
                    return true;
                case 72:
                    IBinder _arg071 = data.readStrongBinder();
                    int[] _arg141 = data.createIntArray();
                    int[] _arg213 = data.createIntArray();
                    Point[] _arg38 = (Point[]) data.createTypedArray(Point.CREATOR);
                    int[] _arg45 = data.createIntArray();
                    data.enforceNoDataAvail();
                    adjustPopOverOptions(_arg071, _arg141, _arg213, _arg38, _arg45);
                    reply.writeNoException();
                    return true;
                default:
                    return super.onTransact(code, data, reply, flags);
            }
        }

        private static class Proxy implements IActivityClientController {
            private IBinder mRemote;

            Proxy(IBinder remote) {
                this.mRemote = remote;
            }

            @Override // android.os.IInterface
            public IBinder asBinder() {
                return this.mRemote;
            }

            public String getInterfaceDescriptor() {
                return IActivityClientController.DESCRIPTOR;
            }

            @Override // android.app.IActivityClientController
            public void activityIdle(IBinder token, Configuration config, boolean stopProfiling)
                    throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                try {
                    _data.writeInterfaceToken(IActivityClientController.DESCRIPTOR);
                    _data.writeStrongBinder(token);
                    _data.writeTypedObject(config, 0);
                    _data.writeBoolean(stopProfiling);
                    this.mRemote.transact(1, _data, null, 1);
                } finally {
                    _data.recycle();
                }
            }

            @Override // android.app.IActivityClientController
            public void activityResumed(IBinder token, boolean handleSplashScreenExit)
                    throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                try {
                    _data.writeInterfaceToken(IActivityClientController.DESCRIPTOR);
                    _data.writeStrongBinder(token);
                    _data.writeBoolean(handleSplashScreenExit);
                    this.mRemote.transact(2, _data, null, 1);
                } finally {
                    _data.recycle();
                }
            }

            @Override // android.app.IActivityClientController
            public void activityRefreshed(IBinder token) throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                try {
                    _data.writeInterfaceToken(IActivityClientController.DESCRIPTOR);
                    _data.writeStrongBinder(token);
                    this.mRemote.transact(3, _data, null, 1);
                } finally {
                    _data.recycle();
                }
            }

            @Override // android.app.IActivityClientController
            public void activityTopResumedStateLost() throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(IActivityClientController.DESCRIPTOR);
                    this.mRemote.transact(4, _data, _reply, 0);
                    _reply.readException();
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // android.app.IActivityClientController
            public void activityPaused(IBinder token) throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(IActivityClientController.DESCRIPTOR);
                    _data.writeStrongBinder(token);
                    this.mRemote.transact(5, _data, _reply, 0);
                    _reply.readException();
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // android.app.IActivityClientController
            public void activityStopped(
                    IBinder token,
                    Bundle state,
                    PersistableBundle persistentState,
                    CharSequence description)
                    throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                try {
                    _data.writeInterfaceToken(IActivityClientController.DESCRIPTOR);
                    _data.writeStrongBinder(token);
                    _data.writeTypedObject(state, 0);
                    _data.writeTypedObject(persistentState, 0);
                    if (description != null) {
                        _data.writeInt(1);
                        TextUtils.writeToParcel(description, _data, 0);
                    } else {
                        _data.writeInt(0);
                    }
                    this.mRemote.transact(6, _data, null, 1);
                } finally {
                    _data.recycle();
                }
            }

            @Override // android.app.IActivityClientController
            public void activityDestroyed(IBinder token) throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                try {
                    _data.writeInterfaceToken(IActivityClientController.DESCRIPTOR);
                    _data.writeStrongBinder(token);
                    this.mRemote.transact(7, _data, null, 1);
                } finally {
                    _data.recycle();
                }
            }

            @Override // android.app.IActivityClientController
            public void activityLocalRelaunch(IBinder token) throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                try {
                    _data.writeInterfaceToken(IActivityClientController.DESCRIPTOR);
                    _data.writeStrongBinder(token);
                    this.mRemote.transact(8, _data, null, 1);
                } finally {
                    _data.recycle();
                }
            }

            @Override // android.app.IActivityClientController
            public void activityRelaunched(IBinder token) throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                try {
                    _data.writeInterfaceToken(IActivityClientController.DESCRIPTOR);
                    _data.writeStrongBinder(token);
                    this.mRemote.transact(9, _data, null, 1);
                } finally {
                    _data.recycle();
                }
            }

            @Override // android.app.IActivityClientController
            public void reportSizeConfigurations(
                    IBinder token, SizeConfigurationBuckets sizeConfigurations)
                    throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                try {
                    _data.writeInterfaceToken(IActivityClientController.DESCRIPTOR);
                    _data.writeStrongBinder(token);
                    _data.writeTypedObject(sizeConfigurations, 0);
                    this.mRemote.transact(10, _data, null, 1);
                } finally {
                    _data.recycle();
                }
            }

            @Override // android.app.IActivityClientController
            public boolean moveActivityTaskToBack(IBinder token, boolean nonRoot)
                    throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(IActivityClientController.DESCRIPTOR);
                    _data.writeStrongBinder(token);
                    _data.writeBoolean(nonRoot);
                    this.mRemote.transact(11, _data, _reply, 0);
                    _reply.readException();
                    boolean _result = _reply.readBoolean();
                    return _result;
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // android.app.IActivityClientController
            public boolean shouldUpRecreateTask(IBinder token, String destAffinity)
                    throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(IActivityClientController.DESCRIPTOR);
                    _data.writeStrongBinder(token);
                    _data.writeString(destAffinity);
                    this.mRemote.transact(12, _data, _reply, 0);
                    _reply.readException();
                    boolean _result = _reply.readBoolean();
                    return _result;
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // android.app.IActivityClientController
            public boolean navigateUpTo(
                    IBinder token,
                    Intent target,
                    String resolvedType,
                    int resultCode,
                    Intent resultData)
                    throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(IActivityClientController.DESCRIPTOR);
                    _data.writeStrongBinder(token);
                    _data.writeTypedObject(target, 0);
                    _data.writeString(resolvedType);
                    _data.writeInt(resultCode);
                    _data.writeTypedObject(resultData, 0);
                    this.mRemote.transact(13, _data, _reply, 0);
                    _reply.readException();
                    boolean _result = _reply.readBoolean();
                    return _result;
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // android.app.IActivityClientController
            public boolean releaseActivityInstance(IBinder token) throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(IActivityClientController.DESCRIPTOR);
                    _data.writeStrongBinder(token);
                    this.mRemote.transact(14, _data, _reply, 0);
                    _reply.readException();
                    boolean _result = _reply.readBoolean();
                    return _result;
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // android.app.IActivityClientController
            public boolean finishActivity(IBinder token, int code, Intent data, int finishTask)
                    throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(IActivityClientController.DESCRIPTOR);
                    _data.writeStrongBinder(token);
                    _data.writeInt(code);
                    _data.writeTypedObject(data, 0);
                    _data.writeInt(finishTask);
                    this.mRemote.transact(15, _data, _reply, 0);
                    _reply.readException();
                    boolean _result = _reply.readBoolean();
                    return _result;
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // android.app.IActivityClientController
            public boolean finishActivityAffinity(IBinder token) throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(IActivityClientController.DESCRIPTOR);
                    _data.writeStrongBinder(token);
                    this.mRemote.transact(16, _data, _reply, 0);
                    _reply.readException();
                    boolean _result = _reply.readBoolean();
                    return _result;
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // android.app.IActivityClientController
            public void finishSubActivity(IBinder token, String resultWho, int requestCode)
                    throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(IActivityClientController.DESCRIPTOR);
                    _data.writeStrongBinder(token);
                    _data.writeString(resultWho);
                    _data.writeInt(requestCode);
                    this.mRemote.transact(17, _data, _reply, 0);
                    _reply.readException();
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // android.app.IActivityClientController
            public void setForceSendResultForMediaProjection(IBinder token) throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(IActivityClientController.DESCRIPTOR);
                    _data.writeStrongBinder(token);
                    this.mRemote.transact(18, _data, _reply, 0);
                    _reply.readException();
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // android.app.IActivityClientController
            public boolean isTopOfTask(IBinder token) throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(IActivityClientController.DESCRIPTOR);
                    _data.writeStrongBinder(token);
                    this.mRemote.transact(19, _data, _reply, 0);
                    _reply.readException();
                    boolean _result = _reply.readBoolean();
                    return _result;
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // android.app.IActivityClientController
            public boolean willActivityBeVisible(IBinder token) throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(IActivityClientController.DESCRIPTOR);
                    _data.writeStrongBinder(token);
                    this.mRemote.transact(20, _data, _reply, 0);
                    _reply.readException();
                    boolean _result = _reply.readBoolean();
                    return _result;
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // android.app.IActivityClientController
            public int getDisplayId(IBinder activityToken) throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(IActivityClientController.DESCRIPTOR);
                    _data.writeStrongBinder(activityToken);
                    this.mRemote.transact(21, _data, _reply, 0);
                    _reply.readException();
                    int _result = _reply.readInt();
                    return _result;
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // android.app.IActivityClientController
            public int getTaskForActivity(IBinder token, boolean onlyRoot) throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(IActivityClientController.DESCRIPTOR);
                    _data.writeStrongBinder(token);
                    _data.writeBoolean(onlyRoot);
                    this.mRemote.transact(22, _data, _reply, 0);
                    _reply.readException();
                    int _result = _reply.readInt();
                    return _result;
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // android.app.IActivityClientController
            public Configuration getTaskConfiguration(IBinder activityToken)
                    throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(IActivityClientController.DESCRIPTOR);
                    _data.writeStrongBinder(activityToken);
                    this.mRemote.transact(23, _data, _reply, 0);
                    _reply.readException();
                    Configuration _result =
                            (Configuration) _reply.readTypedObject(Configuration.CREATOR);
                    return _result;
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // android.app.IActivityClientController
            public IBinder getActivityTokenBelow(IBinder token) throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(IActivityClientController.DESCRIPTOR);
                    _data.writeStrongBinder(token);
                    this.mRemote.transact(24, _data, _reply, 0);
                    _reply.readException();
                    IBinder _result = _reply.readStrongBinder();
                    return _result;
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // android.app.IActivityClientController
            public ComponentName getCallingActivity(IBinder token) throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(IActivityClientController.DESCRIPTOR);
                    _data.writeStrongBinder(token);
                    this.mRemote.transact(25, _data, _reply, 0);
                    _reply.readException();
                    ComponentName _result =
                            (ComponentName) _reply.readTypedObject(ComponentName.CREATOR);
                    return _result;
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // android.app.IActivityClientController
            public String getCallingPackage(IBinder token) throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(IActivityClientController.DESCRIPTOR);
                    _data.writeStrongBinder(token);
                    this.mRemote.transact(26, _data, _reply, 0);
                    _reply.readException();
                    String _result = _reply.readString();
                    return _result;
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // android.app.IActivityClientController
            public int getLaunchedFromUid(IBinder token) throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(IActivityClientController.DESCRIPTOR);
                    _data.writeStrongBinder(token);
                    this.mRemote.transact(27, _data, _reply, 0);
                    _reply.readException();
                    int _result = _reply.readInt();
                    return _result;
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // android.app.IActivityClientController
            public int getActivityCallerUid(IBinder activityToken, IBinder callerToken)
                    throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(IActivityClientController.DESCRIPTOR);
                    _data.writeStrongBinder(activityToken);
                    _data.writeStrongBinder(callerToken);
                    this.mRemote.transact(28, _data, _reply, 0);
                    _reply.readException();
                    int _result = _reply.readInt();
                    return _result;
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // android.app.IActivityClientController
            public String getLaunchedFromPackage(IBinder token) throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(IActivityClientController.DESCRIPTOR);
                    _data.writeStrongBinder(token);
                    this.mRemote.transact(29, _data, _reply, 0);
                    _reply.readException();
                    String _result = _reply.readString();
                    return _result;
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // android.app.IActivityClientController
            public String getActivityCallerPackage(IBinder activityToken, IBinder callerToken)
                    throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(IActivityClientController.DESCRIPTOR);
                    _data.writeStrongBinder(activityToken);
                    _data.writeStrongBinder(callerToken);
                    this.mRemote.transact(30, _data, _reply, 0);
                    _reply.readException();
                    String _result = _reply.readString();
                    return _result;
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // android.app.IActivityClientController
            public int checkActivityCallerContentUriPermission(
                    IBinder activityToken, IBinder callerToken, Uri uri, int modeFlags, int userId)
                    throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(IActivityClientController.DESCRIPTOR);
                    _data.writeStrongBinder(activityToken);
                    _data.writeStrongBinder(callerToken);
                    _data.writeTypedObject(uri, 0);
                    _data.writeInt(modeFlags);
                    _data.writeInt(userId);
                    this.mRemote.transact(31, _data, _reply, 0);
                    _reply.readException();
                    int _result = _reply.readInt();
                    return _result;
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // android.app.IActivityClientController
            public void setRequestedOrientation(IBinder token, int requestedOrientation)
                    throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(IActivityClientController.DESCRIPTOR);
                    _data.writeStrongBinder(token);
                    _data.writeInt(requestedOrientation);
                    this.mRemote.transact(32, _data, _reply, 0);
                    _reply.readException();
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // android.app.IActivityClientController
            public int getRequestedOrientation(IBinder token) throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(IActivityClientController.DESCRIPTOR);
                    _data.writeStrongBinder(token);
                    this.mRemote.transact(33, _data, _reply, 0);
                    _reply.readException();
                    int _result = _reply.readInt();
                    return _result;
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // android.app.IActivityClientController
            public boolean convertFromTranslucent(IBinder token) throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(IActivityClientController.DESCRIPTOR);
                    _data.writeStrongBinder(token);
                    this.mRemote.transact(34, _data, _reply, 0);
                    _reply.readException();
                    boolean _result = _reply.readBoolean();
                    return _result;
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // android.app.IActivityClientController
            public boolean convertToTranslucent(IBinder token, Bundle options)
                    throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(IActivityClientController.DESCRIPTOR);
                    _data.writeStrongBinder(token);
                    _data.writeTypedObject(options, 0);
                    this.mRemote.transact(35, _data, _reply, 0);
                    _reply.readException();
                    boolean _result = _reply.readBoolean();
                    return _result;
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // android.app.IActivityClientController
            public boolean isImmersive(IBinder token) throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(IActivityClientController.DESCRIPTOR);
                    _data.writeStrongBinder(token);
                    this.mRemote.transact(36, _data, _reply, 0);
                    _reply.readException();
                    boolean _result = _reply.readBoolean();
                    return _result;
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // android.app.IActivityClientController
            public void setImmersive(IBinder token, boolean immersive) throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(IActivityClientController.DESCRIPTOR);
                    _data.writeStrongBinder(token);
                    _data.writeBoolean(immersive);
                    this.mRemote.transact(37, _data, _reply, 0);
                    _reply.readException();
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // android.app.IActivityClientController
            public boolean enterPictureInPictureMode(IBinder token, PictureInPictureParams params)
                    throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(IActivityClientController.DESCRIPTOR);
                    _data.writeStrongBinder(token);
                    _data.writeTypedObject(params, 0);
                    this.mRemote.transact(38, _data, _reply, 0);
                    _reply.readException();
                    boolean _result = _reply.readBoolean();
                    return _result;
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // android.app.IActivityClientController
            public void setPictureInPictureParams(IBinder token, PictureInPictureParams params)
                    throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(IActivityClientController.DESCRIPTOR);
                    _data.writeStrongBinder(token);
                    _data.writeTypedObject(params, 0);
                    this.mRemote.transact(39, _data, _reply, 0);
                    _reply.readException();
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // android.app.IActivityClientController
            public void setShouldDockBigOverlays(IBinder token, boolean shouldDockBigOverlays)
                    throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                try {
                    _data.writeInterfaceToken(IActivityClientController.DESCRIPTOR);
                    _data.writeStrongBinder(token);
                    _data.writeBoolean(shouldDockBigOverlays);
                    this.mRemote.transact(40, _data, null, 1);
                } finally {
                    _data.recycle();
                }
            }

            @Override // android.app.IActivityClientController
            public void toggleFreeformWindowingMode(IBinder token) throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(IActivityClientController.DESCRIPTOR);
                    _data.writeStrongBinder(token);
                    this.mRemote.transact(41, _data, _reply, 0);
                    _reply.readException();
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // android.app.IActivityClientController
            public void requestMultiwindowFullscreen(
                    IBinder token, int request, IRemoteCallback callback) throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                try {
                    _data.writeInterfaceToken(IActivityClientController.DESCRIPTOR);
                    _data.writeStrongBinder(token);
                    _data.writeInt(request);
                    _data.writeStrongInterface(callback);
                    this.mRemote.transact(42, _data, null, 1);
                } finally {
                    _data.recycle();
                }
            }

            @Override // android.app.IActivityClientController
            public void startLockTaskModeByToken(IBinder token) throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                try {
                    _data.writeInterfaceToken(IActivityClientController.DESCRIPTOR);
                    _data.writeStrongBinder(token);
                    this.mRemote.transact(43, _data, null, 1);
                } finally {
                    _data.recycle();
                }
            }

            @Override // android.app.IActivityClientController
            public void stopLockTaskModeByToken(IBinder token) throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                try {
                    _data.writeInterfaceToken(IActivityClientController.DESCRIPTOR);
                    _data.writeStrongBinder(token);
                    this.mRemote.transact(44, _data, null, 1);
                } finally {
                    _data.recycle();
                }
            }

            @Override // android.app.IActivityClientController
            public void showLockTaskEscapeMessage(IBinder token) throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                try {
                    _data.writeInterfaceToken(IActivityClientController.DESCRIPTOR);
                    _data.writeStrongBinder(token);
                    this.mRemote.transact(45, _data, null, 1);
                } finally {
                    _data.recycle();
                }
            }

            @Override // android.app.IActivityClientController
            public void setTaskDescription(IBinder token, ActivityManager.TaskDescription values)
                    throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(IActivityClientController.DESCRIPTOR);
                    _data.writeStrongBinder(token);
                    _data.writeTypedObject(values, 0);
                    this.mRemote.transact(46, _data, _reply, 0);
                    _reply.readException();
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // android.app.IActivityClientController
            public boolean showAssistFromActivity(IBinder token, Bundle args)
                    throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(IActivityClientController.DESCRIPTOR);
                    _data.writeStrongBinder(token);
                    _data.writeTypedObject(args, 0);
                    this.mRemote.transact(47, _data, _reply, 0);
                    _reply.readException();
                    boolean _result = _reply.readBoolean();
                    return _result;
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // android.app.IActivityClientController
            public boolean isRootVoiceInteraction(IBinder token) throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(IActivityClientController.DESCRIPTOR);
                    _data.writeStrongBinder(token);
                    this.mRemote.transact(48, _data, _reply, 0);
                    _reply.readException();
                    boolean _result = _reply.readBoolean();
                    return _result;
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // android.app.IActivityClientController
            public void startLocalVoiceInteraction(IBinder token, Bundle options)
                    throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(IActivityClientController.DESCRIPTOR);
                    _data.writeStrongBinder(token);
                    _data.writeTypedObject(options, 0);
                    this.mRemote.transact(49, _data, _reply, 0);
                    _reply.readException();
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // android.app.IActivityClientController
            public void stopLocalVoiceInteraction(IBinder token) throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(IActivityClientController.DESCRIPTOR);
                    _data.writeStrongBinder(token);
                    this.mRemote.transact(50, _data, _reply, 0);
                    _reply.readException();
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // android.app.IActivityClientController
            public void setShowWhenLocked(IBinder token, boolean showWhenLocked)
                    throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                try {
                    _data.writeInterfaceToken(IActivityClientController.DESCRIPTOR);
                    _data.writeStrongBinder(token);
                    _data.writeBoolean(showWhenLocked);
                    this.mRemote.transact(51, _data, null, 1);
                } finally {
                    _data.recycle();
                }
            }

            @Override // android.app.IActivityClientController
            public void setInheritShowWhenLocked(IBinder token, boolean setInheritShownWhenLocked)
                    throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                try {
                    _data.writeInterfaceToken(IActivityClientController.DESCRIPTOR);
                    _data.writeStrongBinder(token);
                    _data.writeBoolean(setInheritShownWhenLocked);
                    this.mRemote.transact(52, _data, null, 1);
                } finally {
                    _data.recycle();
                }
            }

            @Override // android.app.IActivityClientController
            public void setTurnScreenOn(IBinder token, boolean turnScreenOn)
                    throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(IActivityClientController.DESCRIPTOR);
                    _data.writeStrongBinder(token);
                    _data.writeBoolean(turnScreenOn);
                    this.mRemote.transact(53, _data, _reply, 0);
                    _reply.readException();
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // android.app.IActivityClientController
            public void setAllowCrossUidActivitySwitchFromBelow(IBinder token, boolean allowed)
                    throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                try {
                    _data.writeInterfaceToken(IActivityClientController.DESCRIPTOR);
                    _data.writeStrongBinder(token);
                    _data.writeBoolean(allowed);
                    this.mRemote.transact(54, _data, null, 1);
                } finally {
                    _data.recycle();
                }
            }

            @Override // android.app.IActivityClientController
            public void reportActivityFullyDrawn(IBinder token, boolean restoredFromBundle)
                    throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                try {
                    _data.writeInterfaceToken(IActivityClientController.DESCRIPTOR);
                    _data.writeStrongBinder(token);
                    _data.writeBoolean(restoredFromBundle);
                    this.mRemote.transact(55, _data, null, 1);
                } finally {
                    _data.recycle();
                }
            }

            @Override // android.app.IActivityClientController
            public void overrideActivityTransition(
                    IBinder token, boolean open, int enterAnim, int exitAnim, int backgroundColor)
                    throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                try {
                    _data.writeInterfaceToken(IActivityClientController.DESCRIPTOR);
                    _data.writeStrongBinder(token);
                    _data.writeBoolean(open);
                    _data.writeInt(enterAnim);
                    _data.writeInt(exitAnim);
                    _data.writeInt(backgroundColor);
                    this.mRemote.transact(56, _data, null, 1);
                } finally {
                    _data.recycle();
                }
            }

            @Override // android.app.IActivityClientController
            public void clearOverrideActivityTransition(IBinder token, boolean open)
                    throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                try {
                    _data.writeInterfaceToken(IActivityClientController.DESCRIPTOR);
                    _data.writeStrongBinder(token);
                    _data.writeBoolean(open);
                    this.mRemote.transact(57, _data, null, 1);
                } finally {
                    _data.recycle();
                }
            }

            @Override // android.app.IActivityClientController
            public void overridePendingTransition(
                    IBinder token,
                    String packageName,
                    int enterAnim,
                    int exitAnim,
                    int backgroundColor)
                    throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(IActivityClientController.DESCRIPTOR);
                    _data.writeStrongBinder(token);
                    _data.writeString(packageName);
                    _data.writeInt(enterAnim);
                    _data.writeInt(exitAnim);
                    _data.writeInt(backgroundColor);
                    this.mRemote.transact(58, _data, _reply, 0);
                    _reply.readException();
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // android.app.IActivityClientController
            public int setVrMode(IBinder token, boolean enabled, ComponentName packageName)
                    throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(IActivityClientController.DESCRIPTOR);
                    _data.writeStrongBinder(token);
                    _data.writeBoolean(enabled);
                    _data.writeTypedObject(packageName, 0);
                    this.mRemote.transact(59, _data, _reply, 0);
                    _reply.readException();
                    int _result = _reply.readInt();
                    return _result;
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // android.app.IActivityClientController
            public void setRecentsScreenshotEnabled(IBinder token, boolean enabled)
                    throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                try {
                    _data.writeInterfaceToken(IActivityClientController.DESCRIPTOR);
                    _data.writeStrongBinder(token);
                    _data.writeBoolean(enabled);
                    this.mRemote.transact(60, _data, null, 1);
                } finally {
                    _data.recycle();
                }
            }

            @Override // android.app.IActivityClientController
            public void invalidateHomeTaskSnapshot(IBinder homeToken) throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(IActivityClientController.DESCRIPTOR);
                    _data.writeStrongBinder(homeToken);
                    this.mRemote.transact(61, _data, _reply, 0);
                    _reply.readException();
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // android.app.IActivityClientController
            public void dismissKeyguard(
                    IBinder token, IKeyguardDismissCallback callback, CharSequence message)
                    throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(IActivityClientController.DESCRIPTOR);
                    _data.writeStrongBinder(token);
                    _data.writeStrongInterface(callback);
                    if (message != null) {
                        _data.writeInt(1);
                        TextUtils.writeToParcel(message, _data, 0);
                    } else {
                        _data.writeInt(0);
                    }
                    this.mRemote.transact(62, _data, _reply, 0);
                    _reply.readException();
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // android.app.IActivityClientController
            public void registerRemoteAnimations(
                    IBinder token, RemoteAnimationDefinition definition) throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(IActivityClientController.DESCRIPTOR);
                    _data.writeStrongBinder(token);
                    _data.writeTypedObject(definition, 0);
                    this.mRemote.transact(63, _data, _reply, 0);
                    _reply.readException();
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // android.app.IActivityClientController
            public void unregisterRemoteAnimations(IBinder token) throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(IActivityClientController.DESCRIPTOR);
                    _data.writeStrongBinder(token);
                    this.mRemote.transact(64, _data, _reply, 0);
                    _reply.readException();
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // android.app.IActivityClientController
            public void onBackPressed(IBinder activityToken, IRequestFinishCallback callback)
                    throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                try {
                    _data.writeInterfaceToken(IActivityClientController.DESCRIPTOR);
                    _data.writeStrongBinder(activityToken);
                    _data.writeStrongInterface(callback);
                    this.mRemote.transact(65, _data, null, 1);
                } finally {
                    _data.recycle();
                }
            }

            @Override // android.app.IActivityClientController
            public void splashScreenAttached(IBinder token) throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                try {
                    _data.writeInterfaceToken(IActivityClientController.DESCRIPTOR);
                    _data.writeStrongBinder(token);
                    this.mRemote.transact(66, _data, null, 1);
                } finally {
                    _data.recycle();
                }
            }

            @Override // android.app.IActivityClientController
            public void enableTaskLocaleOverride(IBinder token) throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(IActivityClientController.DESCRIPTOR);
                    _data.writeStrongBinder(token);
                    this.mRemote.transact(67, _data, _reply, 0);
                    _reply.readException();
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // android.app.IActivityClientController
            public boolean isRequestedToLaunchInTaskFragment(
                    IBinder activityToken, IBinder taskFragmentToken) throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(IActivityClientController.DESCRIPTOR);
                    _data.writeStrongBinder(activityToken);
                    _data.writeStrongBinder(taskFragmentToken);
                    this.mRemote.transact(68, _data, _reply, 0);
                    _reply.readException();
                    boolean _result = _reply.readBoolean();
                    return _result;
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // android.app.IActivityClientController
            public void setActivityRecordInputSinkEnabled(IBinder activityToken, boolean enabled)
                    throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                try {
                    _data.writeInterfaceToken(IActivityClientController.DESCRIPTOR);
                    _data.writeStrongBinder(activityToken);
                    _data.writeBoolean(enabled);
                    this.mRemote.transact(69, _data, null, 1);
                } finally {
                    _data.recycle();
                }
            }

            @Override // android.app.IActivityClientController
            public boolean convertFromTranslucentOp(IBinder token, boolean skipSetWindowOpaque)
                    throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(IActivityClientController.DESCRIPTOR);
                    _data.writeStrongBinder(token);
                    _data.writeBoolean(skipSetWindowOpaque);
                    this.mRemote.transact(70, _data, _reply, 0);
                    _reply.readException();
                    boolean _result = _reply.readBoolean();
                    return _result;
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // android.app.IActivityClientController
            public void overridePendingTaskTransition(
                    IBinder token, String packageName, int enterAnim, int exitAnim)
                    throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(IActivityClientController.DESCRIPTOR);
                    _data.writeStrongBinder(token);
                    _data.writeString(packageName);
                    _data.writeInt(enterAnim);
                    _data.writeInt(exitAnim);
                    this.mRemote.transact(71, _data, _reply, 0);
                    _reply.readException();
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // android.app.IActivityClientController
            public void adjustPopOverOptions(
                    IBinder token, int[] widthDp, int[] heightDp, Point[] marginDp, int[] position)
                    throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(IActivityClientController.DESCRIPTOR);
                    _data.writeStrongBinder(token);
                    _data.writeIntArray(widthDp);
                    _data.writeIntArray(heightDp);
                    _data.writeTypedArray(marginDp, 0);
                    _data.writeIntArray(position);
                    this.mRemote.transact(72, _data, _reply, 0);
                    _reply.readException();
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }
        }

        @Override // android.os.Binder
        public int getMaxTransactionId() {
            return 71;
        }
    }
}
