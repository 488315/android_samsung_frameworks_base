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

        @Override // android.os.IInterface
        public IBinder asBinder() {
            return this;
        }

        @Override // android.os.Binder
        public int getMaxTransactionId() {
            return 71;
        }

        public Stub() {
            attachInterface(this, IActivityClientController.DESCRIPTOR);
        }

        public static IActivityClientController asInterface(IBinder iBinder) {
            if (iBinder == null) {
                return null;
            }
            IInterface queryLocalInterface = iBinder.queryLocalInterface(IActivityClientController.DESCRIPTOR);
            if (queryLocalInterface != null && (queryLocalInterface instanceof IActivityClientController)) {
                return (IActivityClientController) queryLocalInterface;
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
                    IBinder readStrongBinder = parcel.readStrongBinder();
                    Configuration configuration = (Configuration) parcel.readTypedObject(Configuration.CREATOR);
                    boolean readBoolean = parcel.readBoolean();
                    parcel.enforceNoDataAvail();
                    activityIdle(readStrongBinder, configuration, readBoolean);
                    return true;
                case 2:
                    IBinder readStrongBinder2 = parcel.readStrongBinder();
                    boolean readBoolean2 = parcel.readBoolean();
                    parcel.enforceNoDataAvail();
                    activityResumed(readStrongBinder2, readBoolean2);
                    return true;
                case 3:
                    IBinder readStrongBinder3 = parcel.readStrongBinder();
                    parcel.enforceNoDataAvail();
                    activityRefreshed(readStrongBinder3);
                    return true;
                case 4:
                    activityTopResumedStateLost();
                    parcel2.writeNoException();
                    return true;
                case 5:
                    IBinder readStrongBinder4 = parcel.readStrongBinder();
                    parcel.enforceNoDataAvail();
                    activityPaused(readStrongBinder4);
                    parcel2.writeNoException();
                    return true;
                case 6:
                    IBinder readStrongBinder5 = parcel.readStrongBinder();
                    Bundle bundle = (Bundle) parcel.readTypedObject(Bundle.CREATOR);
                    PersistableBundle persistableBundle = (PersistableBundle) parcel.readTypedObject(PersistableBundle.CREATOR);
                    CharSequence charSequence = (CharSequence) parcel.readTypedObject(TextUtils.CHAR_SEQUENCE_CREATOR);
                    parcel.enforceNoDataAvail();
                    activityStopped(readStrongBinder5, bundle, persistableBundle, charSequence);
                    return true;
                case 7:
                    IBinder readStrongBinder6 = parcel.readStrongBinder();
                    parcel.enforceNoDataAvail();
                    activityDestroyed(readStrongBinder6);
                    return true;
                case 8:
                    IBinder readStrongBinder7 = parcel.readStrongBinder();
                    parcel.enforceNoDataAvail();
                    activityLocalRelaunch(readStrongBinder7);
                    return true;
                case 9:
                    IBinder readStrongBinder8 = parcel.readStrongBinder();
                    parcel.enforceNoDataAvail();
                    activityRelaunched(readStrongBinder8);
                    return true;
                case 10:
                    IBinder readStrongBinder9 = parcel.readStrongBinder();
                    SizeConfigurationBuckets sizeConfigurationBuckets = (SizeConfigurationBuckets) parcel.readTypedObject(SizeConfigurationBuckets.CREATOR);
                    parcel.enforceNoDataAvail();
                    reportSizeConfigurations(readStrongBinder9, sizeConfigurationBuckets);
                    return true;
                case 11:
                    IBinder readStrongBinder10 = parcel.readStrongBinder();
                    boolean readBoolean3 = parcel.readBoolean();
                    parcel.enforceNoDataAvail();
                    boolean moveActivityTaskToBack = moveActivityTaskToBack(readStrongBinder10, readBoolean3);
                    parcel2.writeNoException();
                    parcel2.writeBoolean(moveActivityTaskToBack);
                    return true;
                case 12:
                    IBinder readStrongBinder11 = parcel.readStrongBinder();
                    String readString = parcel.readString();
                    parcel.enforceNoDataAvail();
                    boolean shouldUpRecreateTask = shouldUpRecreateTask(readStrongBinder11, readString);
                    parcel2.writeNoException();
                    parcel2.writeBoolean(shouldUpRecreateTask);
                    return true;
                case 13:
                    IBinder readStrongBinder12 = parcel.readStrongBinder();
                    Intent intent = (Intent) parcel.readTypedObject(Intent.CREATOR);
                    String readString2 = parcel.readString();
                    int readInt = parcel.readInt();
                    Intent intent2 = (Intent) parcel.readTypedObject(Intent.CREATOR);
                    parcel.enforceNoDataAvail();
                    boolean navigateUpTo = navigateUpTo(readStrongBinder12, intent, readString2, readInt, intent2);
                    parcel2.writeNoException();
                    parcel2.writeBoolean(navigateUpTo);
                    return true;
                case 14:
                    IBinder readStrongBinder13 = parcel.readStrongBinder();
                    parcel.enforceNoDataAvail();
                    boolean releaseActivityInstance = releaseActivityInstance(readStrongBinder13);
                    parcel2.writeNoException();
                    parcel2.writeBoolean(releaseActivityInstance);
                    return true;
                case 15:
                    IBinder readStrongBinder14 = parcel.readStrongBinder();
                    int readInt2 = parcel.readInt();
                    Intent intent3 = (Intent) parcel.readTypedObject(Intent.CREATOR);
                    int readInt3 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    boolean finishActivity = finishActivity(readStrongBinder14, readInt2, intent3, readInt3);
                    parcel2.writeNoException();
                    parcel2.writeBoolean(finishActivity);
                    return true;
                case 16:
                    IBinder readStrongBinder15 = parcel.readStrongBinder();
                    parcel.enforceNoDataAvail();
                    boolean finishActivityAffinity = finishActivityAffinity(readStrongBinder15);
                    parcel2.writeNoException();
                    parcel2.writeBoolean(finishActivityAffinity);
                    return true;
                case 17:
                    IBinder readStrongBinder16 = parcel.readStrongBinder();
                    String readString3 = parcel.readString();
                    int readInt4 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    finishSubActivity(readStrongBinder16, readString3, readInt4);
                    parcel2.writeNoException();
                    return true;
                case 18:
                    IBinder readStrongBinder17 = parcel.readStrongBinder();
                    parcel.enforceNoDataAvail();
                    setForceSendResultForMediaProjection(readStrongBinder17);
                    parcel2.writeNoException();
                    return true;
                case 19:
                    IBinder readStrongBinder18 = parcel.readStrongBinder();
                    parcel.enforceNoDataAvail();
                    boolean isTopOfTask = isTopOfTask(readStrongBinder18);
                    parcel2.writeNoException();
                    parcel2.writeBoolean(isTopOfTask);
                    return true;
                case 20:
                    IBinder readStrongBinder19 = parcel.readStrongBinder();
                    parcel.enforceNoDataAvail();
                    boolean willActivityBeVisible = willActivityBeVisible(readStrongBinder19);
                    parcel2.writeNoException();
                    parcel2.writeBoolean(willActivityBeVisible);
                    return true;
                case 21:
                    IBinder readStrongBinder20 = parcel.readStrongBinder();
                    parcel.enforceNoDataAvail();
                    int displayId = getDisplayId(readStrongBinder20);
                    parcel2.writeNoException();
                    parcel2.writeInt(displayId);
                    return true;
                case 22:
                    IBinder readStrongBinder21 = parcel.readStrongBinder();
                    boolean readBoolean4 = parcel.readBoolean();
                    parcel.enforceNoDataAvail();
                    int taskForActivity = getTaskForActivity(readStrongBinder21, readBoolean4);
                    parcel2.writeNoException();
                    parcel2.writeInt(taskForActivity);
                    return true;
                case 23:
                    IBinder readStrongBinder22 = parcel.readStrongBinder();
                    parcel.enforceNoDataAvail();
                    Configuration taskConfiguration = getTaskConfiguration(readStrongBinder22);
                    parcel2.writeNoException();
                    parcel2.writeTypedObject(taskConfiguration, 1);
                    return true;
                case 24:
                    IBinder readStrongBinder23 = parcel.readStrongBinder();
                    parcel.enforceNoDataAvail();
                    IBinder activityTokenBelow = getActivityTokenBelow(readStrongBinder23);
                    parcel2.writeNoException();
                    parcel2.writeStrongBinder(activityTokenBelow);
                    return true;
                case 25:
                    IBinder readStrongBinder24 = parcel.readStrongBinder();
                    parcel.enforceNoDataAvail();
                    ComponentName callingActivity = getCallingActivity(readStrongBinder24);
                    parcel2.writeNoException();
                    parcel2.writeTypedObject(callingActivity, 1);
                    return true;
                case 26:
                    IBinder readStrongBinder25 = parcel.readStrongBinder();
                    parcel.enforceNoDataAvail();
                    String callingPackage = getCallingPackage(readStrongBinder25);
                    parcel2.writeNoException();
                    parcel2.writeString(callingPackage);
                    return true;
                case 27:
                    IBinder readStrongBinder26 = parcel.readStrongBinder();
                    parcel.enforceNoDataAvail();
                    int launchedFromUid = getLaunchedFromUid(readStrongBinder26);
                    parcel2.writeNoException();
                    parcel2.writeInt(launchedFromUid);
                    return true;
                case 28:
                    IBinder readStrongBinder27 = parcel.readStrongBinder();
                    IBinder readStrongBinder28 = parcel.readStrongBinder();
                    parcel.enforceNoDataAvail();
                    int activityCallerUid = getActivityCallerUid(readStrongBinder27, readStrongBinder28);
                    parcel2.writeNoException();
                    parcel2.writeInt(activityCallerUid);
                    return true;
                case 29:
                    IBinder readStrongBinder29 = parcel.readStrongBinder();
                    parcel.enforceNoDataAvail();
                    String launchedFromPackage = getLaunchedFromPackage(readStrongBinder29);
                    parcel2.writeNoException();
                    parcel2.writeString(launchedFromPackage);
                    return true;
                case 30:
                    IBinder readStrongBinder30 = parcel.readStrongBinder();
                    IBinder readStrongBinder31 = parcel.readStrongBinder();
                    parcel.enforceNoDataAvail();
                    String activityCallerPackage = getActivityCallerPackage(readStrongBinder30, readStrongBinder31);
                    parcel2.writeNoException();
                    parcel2.writeString(activityCallerPackage);
                    return true;
                case 31:
                    IBinder readStrongBinder32 = parcel.readStrongBinder();
                    IBinder readStrongBinder33 = parcel.readStrongBinder();
                    Uri uri = (Uri) parcel.readTypedObject(Uri.CREATOR);
                    int readInt5 = parcel.readInt();
                    int readInt6 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    int checkActivityCallerContentUriPermission = checkActivityCallerContentUriPermission(readStrongBinder32, readStrongBinder33, uri, readInt5, readInt6);
                    parcel2.writeNoException();
                    parcel2.writeInt(checkActivityCallerContentUriPermission);
                    return true;
                case 32:
                    IBinder readStrongBinder34 = parcel.readStrongBinder();
                    int readInt7 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    setRequestedOrientation(readStrongBinder34, readInt7);
                    parcel2.writeNoException();
                    return true;
                case 33:
                    IBinder readStrongBinder35 = parcel.readStrongBinder();
                    parcel.enforceNoDataAvail();
                    int requestedOrientation = getRequestedOrientation(readStrongBinder35);
                    parcel2.writeNoException();
                    parcel2.writeInt(requestedOrientation);
                    return true;
                case 34:
                    IBinder readStrongBinder36 = parcel.readStrongBinder();
                    parcel.enforceNoDataAvail();
                    boolean convertFromTranslucent = convertFromTranslucent(readStrongBinder36);
                    parcel2.writeNoException();
                    parcel2.writeBoolean(convertFromTranslucent);
                    return true;
                case 35:
                    IBinder readStrongBinder37 = parcel.readStrongBinder();
                    Bundle bundle2 = (Bundle) parcel.readTypedObject(Bundle.CREATOR);
                    parcel.enforceNoDataAvail();
                    boolean convertToTranslucent = convertToTranslucent(readStrongBinder37, bundle2);
                    parcel2.writeNoException();
                    parcel2.writeBoolean(convertToTranslucent);
                    return true;
                case 36:
                    IBinder readStrongBinder38 = parcel.readStrongBinder();
                    parcel.enforceNoDataAvail();
                    boolean isImmersive = isImmersive(readStrongBinder38);
                    parcel2.writeNoException();
                    parcel2.writeBoolean(isImmersive);
                    return true;
                case 37:
                    IBinder readStrongBinder39 = parcel.readStrongBinder();
                    boolean readBoolean5 = parcel.readBoolean();
                    parcel.enforceNoDataAvail();
                    setImmersive(readStrongBinder39, readBoolean5);
                    parcel2.writeNoException();
                    return true;
                case 38:
                    IBinder readStrongBinder40 = parcel.readStrongBinder();
                    PictureInPictureParams pictureInPictureParams = (PictureInPictureParams) parcel.readTypedObject(PictureInPictureParams.CREATOR);
                    parcel.enforceNoDataAvail();
                    boolean enterPictureInPictureMode = enterPictureInPictureMode(readStrongBinder40, pictureInPictureParams);
                    parcel2.writeNoException();
                    parcel2.writeBoolean(enterPictureInPictureMode);
                    return true;
                case 39:
                    IBinder readStrongBinder41 = parcel.readStrongBinder();
                    PictureInPictureParams pictureInPictureParams2 = (PictureInPictureParams) parcel.readTypedObject(PictureInPictureParams.CREATOR);
                    parcel.enforceNoDataAvail();
                    setPictureInPictureParams(readStrongBinder41, pictureInPictureParams2);
                    parcel2.writeNoException();
                    return true;
                case 40:
                    IBinder readStrongBinder42 = parcel.readStrongBinder();
                    boolean readBoolean6 = parcel.readBoolean();
                    parcel.enforceNoDataAvail();
                    setShouldDockBigOverlays(readStrongBinder42, readBoolean6);
                    return true;
                case 41:
                    IBinder readStrongBinder43 = parcel.readStrongBinder();
                    parcel.enforceNoDataAvail();
                    toggleFreeformWindowingMode(readStrongBinder43);
                    parcel2.writeNoException();
                    return true;
                case 42:
                    IBinder readStrongBinder44 = parcel.readStrongBinder();
                    int readInt8 = parcel.readInt();
                    IRemoteCallback asInterface = IRemoteCallback.Stub.asInterface(parcel.readStrongBinder());
                    parcel.enforceNoDataAvail();
                    requestMultiwindowFullscreen(readStrongBinder44, readInt8, asInterface);
                    return true;
                case 43:
                    IBinder readStrongBinder45 = parcel.readStrongBinder();
                    parcel.enforceNoDataAvail();
                    startLockTaskModeByToken(readStrongBinder45);
                    parcel2.writeNoException();
                    return true;
                case 44:
                    IBinder readStrongBinder46 = parcel.readStrongBinder();
                    parcel.enforceNoDataAvail();
                    stopLockTaskModeByToken(readStrongBinder46);
                    parcel2.writeNoException();
                    return true;
                case 45:
                    IBinder readStrongBinder47 = parcel.readStrongBinder();
                    parcel.enforceNoDataAvail();
                    showLockTaskEscapeMessage(readStrongBinder47);
                    return true;
                case 46:
                    IBinder readStrongBinder48 = parcel.readStrongBinder();
                    ActivityManager.TaskDescription taskDescription = (ActivityManager.TaskDescription) parcel.readTypedObject(ActivityManager.TaskDescription.CREATOR);
                    parcel.enforceNoDataAvail();
                    setTaskDescription(readStrongBinder48, taskDescription);
                    parcel2.writeNoException();
                    return true;
                case 47:
                    IBinder readStrongBinder49 = parcel.readStrongBinder();
                    Bundle bundle3 = (Bundle) parcel.readTypedObject(Bundle.CREATOR);
                    parcel.enforceNoDataAvail();
                    boolean showAssistFromActivity = showAssistFromActivity(readStrongBinder49, bundle3);
                    parcel2.writeNoException();
                    parcel2.writeBoolean(showAssistFromActivity);
                    return true;
                case 48:
                    IBinder readStrongBinder50 = parcel.readStrongBinder();
                    parcel.enforceNoDataAvail();
                    boolean isRootVoiceInteraction = isRootVoiceInteraction(readStrongBinder50);
                    parcel2.writeNoException();
                    parcel2.writeBoolean(isRootVoiceInteraction);
                    return true;
                case 49:
                    IBinder readStrongBinder51 = parcel.readStrongBinder();
                    Bundle bundle4 = (Bundle) parcel.readTypedObject(Bundle.CREATOR);
                    parcel.enforceNoDataAvail();
                    startLocalVoiceInteraction(readStrongBinder51, bundle4);
                    parcel2.writeNoException();
                    return true;
                case 50:
                    IBinder readStrongBinder52 = parcel.readStrongBinder();
                    parcel.enforceNoDataAvail();
                    stopLocalVoiceInteraction(readStrongBinder52);
                    parcel2.writeNoException();
                    return true;
                case 51:
                    IBinder readStrongBinder53 = parcel.readStrongBinder();
                    boolean readBoolean7 = parcel.readBoolean();
                    parcel.enforceNoDataAvail();
                    setShowWhenLocked(readStrongBinder53, readBoolean7);
                    return true;
                case 52:
                    IBinder readStrongBinder54 = parcel.readStrongBinder();
                    boolean readBoolean8 = parcel.readBoolean();
                    parcel.enforceNoDataAvail();
                    setInheritShowWhenLocked(readStrongBinder54, readBoolean8);
                    return true;
                case 53:
                    IBinder readStrongBinder55 = parcel.readStrongBinder();
                    boolean readBoolean9 = parcel.readBoolean();
                    parcel.enforceNoDataAvail();
                    setTurnScreenOn(readStrongBinder55, readBoolean9);
                    parcel2.writeNoException();
                    return true;
                case 54:
                    IBinder readStrongBinder56 = parcel.readStrongBinder();
                    boolean readBoolean10 = parcel.readBoolean();
                    parcel.enforceNoDataAvail();
                    setAllowCrossUidActivitySwitchFromBelow(readStrongBinder56, readBoolean10);
                    return true;
                case 55:
                    IBinder readStrongBinder57 = parcel.readStrongBinder();
                    boolean readBoolean11 = parcel.readBoolean();
                    parcel.enforceNoDataAvail();
                    reportActivityFullyDrawn(readStrongBinder57, readBoolean11);
                    return true;
                case 56:
                    IBinder readStrongBinder58 = parcel.readStrongBinder();
                    boolean readBoolean12 = parcel.readBoolean();
                    int readInt9 = parcel.readInt();
                    int readInt10 = parcel.readInt();
                    int readInt11 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    overrideActivityTransition(readStrongBinder58, readBoolean12, readInt9, readInt10, readInt11);
                    return true;
                case 57:
                    IBinder readStrongBinder59 = parcel.readStrongBinder();
                    boolean readBoolean13 = parcel.readBoolean();
                    parcel.enforceNoDataAvail();
                    clearOverrideActivityTransition(readStrongBinder59, readBoolean13);
                    return true;
                case 58:
                    IBinder readStrongBinder60 = parcel.readStrongBinder();
                    String readString4 = parcel.readString();
                    int readInt12 = parcel.readInt();
                    int readInt13 = parcel.readInt();
                    int readInt14 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    overridePendingTransition(readStrongBinder60, readString4, readInt12, readInt13, readInt14);
                    parcel2.writeNoException();
                    return true;
                case 59:
                    IBinder readStrongBinder61 = parcel.readStrongBinder();
                    boolean readBoolean14 = parcel.readBoolean();
                    ComponentName componentName = (ComponentName) parcel.readTypedObject(ComponentName.CREATOR);
                    parcel.enforceNoDataAvail();
                    int vrMode = setVrMode(readStrongBinder61, readBoolean14, componentName);
                    parcel2.writeNoException();
                    parcel2.writeInt(vrMode);
                    return true;
                case 60:
                    IBinder readStrongBinder62 = parcel.readStrongBinder();
                    boolean readBoolean15 = parcel.readBoolean();
                    parcel.enforceNoDataAvail();
                    setRecentsScreenshotEnabled(readStrongBinder62, readBoolean15);
                    return true;
                case 61:
                    IBinder readStrongBinder63 = parcel.readStrongBinder();
                    parcel.enforceNoDataAvail();
                    invalidateHomeTaskSnapshot(readStrongBinder63);
                    parcel2.writeNoException();
                    return true;
                case 62:
                    IBinder readStrongBinder64 = parcel.readStrongBinder();
                    IKeyguardDismissCallback asInterface2 = IKeyguardDismissCallback.Stub.asInterface(parcel.readStrongBinder());
                    CharSequence charSequence2 = (CharSequence) parcel.readTypedObject(TextUtils.CHAR_SEQUENCE_CREATOR);
                    parcel.enforceNoDataAvail();
                    dismissKeyguard(readStrongBinder64, asInterface2, charSequence2);
                    parcel2.writeNoException();
                    return true;
                case 63:
                    IBinder readStrongBinder65 = parcel.readStrongBinder();
                    RemoteAnimationDefinition remoteAnimationDefinition = (RemoteAnimationDefinition) parcel.readTypedObject(RemoteAnimationDefinition.CREATOR);
                    parcel.enforceNoDataAvail();
                    registerRemoteAnimations(readStrongBinder65, remoteAnimationDefinition);
                    parcel2.writeNoException();
                    return true;
                case 64:
                    IBinder readStrongBinder66 = parcel.readStrongBinder();
                    parcel.enforceNoDataAvail();
                    unregisterRemoteAnimations(readStrongBinder66);
                    parcel2.writeNoException();
                    return true;
                case 65:
                    IBinder readStrongBinder67 = parcel.readStrongBinder();
                    IRequestFinishCallback asInterface3 = IRequestFinishCallback.Stub.asInterface(parcel.readStrongBinder());
                    parcel.enforceNoDataAvail();
                    onBackPressed(readStrongBinder67, asInterface3);
                    return true;
                case 66:
                    IBinder readStrongBinder68 = parcel.readStrongBinder();
                    parcel.enforceNoDataAvail();
                    splashScreenAttached(readStrongBinder68);
                    return true;
                case 67:
                    IBinder readStrongBinder69 = parcel.readStrongBinder();
                    parcel.enforceNoDataAvail();
                    enableTaskLocaleOverride(readStrongBinder69);
                    parcel2.writeNoException();
                    return true;
                case 68:
                    IBinder readStrongBinder70 = parcel.readStrongBinder();
                    IBinder readStrongBinder71 = parcel.readStrongBinder();
                    parcel.enforceNoDataAvail();
                    boolean isRequestedToLaunchInTaskFragment = isRequestedToLaunchInTaskFragment(readStrongBinder70, readStrongBinder71);
                    parcel2.writeNoException();
                    parcel2.writeBoolean(isRequestedToLaunchInTaskFragment);
                    return true;
                case 69:
                    IBinder readStrongBinder72 = parcel.readStrongBinder();
                    boolean readBoolean16 = parcel.readBoolean();
                    parcel.enforceNoDataAvail();
                    setActivityRecordInputSinkEnabled(readStrongBinder72, readBoolean16);
                    return true;
                case 70:
                    IBinder readStrongBinder73 = parcel.readStrongBinder();
                    boolean readBoolean17 = parcel.readBoolean();
                    parcel.enforceNoDataAvail();
                    boolean convertFromTranslucentOp = convertFromTranslucentOp(readStrongBinder73, readBoolean17);
                    parcel2.writeNoException();
                    parcel2.writeBoolean(convertFromTranslucentOp);
                    return true;
                case 71:
                    IBinder readStrongBinder74 = parcel.readStrongBinder();
                    String readString5 = parcel.readString();
                    int readInt15 = parcel.readInt();
                    int readInt16 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    overridePendingTaskTransition(readStrongBinder74, readString5, readInt15, readInt16);
                    parcel2.writeNoException();
                    return true;
                case 72:
                    IBinder readStrongBinder75 = parcel.readStrongBinder();
                    int[] createIntArray = parcel.createIntArray();
                    int[] createIntArray2 = parcel.createIntArray();
                    Point[] pointArr = (Point[]) parcel.createTypedArray(Point.CREATOR);
                    int[] createIntArray3 = parcel.createIntArray();
                    parcel.enforceNoDataAvail();
                    adjustPopOverOptions(readStrongBinder75, createIntArray, createIntArray2, pointArr, createIntArray3);
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
                Parcel obtain = Parcel.obtain(asBinder());
                try {
                    obtain.writeInterfaceToken(IActivityClientController.DESCRIPTOR);
                    obtain.writeStrongBinder(iBinder);
                    obtain.writeTypedObject(configuration, 0);
                    obtain.writeBoolean(z);
                    this.mRemote.transact(1, obtain, null, 1);
                } finally {
                    obtain.recycle();
                }
            }

            @Override // android.app.IActivityClientController
            public void activityResumed(IBinder iBinder, boolean z) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                try {
                    obtain.writeInterfaceToken(IActivityClientController.DESCRIPTOR);
                    obtain.writeStrongBinder(iBinder);
                    obtain.writeBoolean(z);
                    this.mRemote.transact(2, obtain, null, 1);
                } finally {
                    obtain.recycle();
                }
            }

            @Override // android.app.IActivityClientController
            public void activityRefreshed(IBinder iBinder) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                try {
                    obtain.writeInterfaceToken(IActivityClientController.DESCRIPTOR);
                    obtain.writeStrongBinder(iBinder);
                    this.mRemote.transact(3, obtain, null, 1);
                } finally {
                    obtain.recycle();
                }
            }

            @Override // android.app.IActivityClientController
            public void activityTopResumedStateLost() throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(IActivityClientController.DESCRIPTOR);
                    this.mRemote.transact(4, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.app.IActivityClientController
            public void activityPaused(IBinder iBinder) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(IActivityClientController.DESCRIPTOR);
                    obtain.writeStrongBinder(iBinder);
                    this.mRemote.transact(5, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.app.IActivityClientController
            public void activityStopped(IBinder iBinder, Bundle bundle, PersistableBundle persistableBundle, CharSequence charSequence) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                try {
                    obtain.writeInterfaceToken(IActivityClientController.DESCRIPTOR);
                    obtain.writeStrongBinder(iBinder);
                    obtain.writeTypedObject(bundle, 0);
                    obtain.writeTypedObject(persistableBundle, 0);
                    if (charSequence != null) {
                        obtain.writeInt(1);
                        TextUtils.writeToParcel(charSequence, obtain, 0);
                    } else {
                        obtain.writeInt(0);
                    }
                    this.mRemote.transact(6, obtain, null, 1);
                } finally {
                    obtain.recycle();
                }
            }

            @Override // android.app.IActivityClientController
            public void activityDestroyed(IBinder iBinder) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                try {
                    obtain.writeInterfaceToken(IActivityClientController.DESCRIPTOR);
                    obtain.writeStrongBinder(iBinder);
                    this.mRemote.transact(7, obtain, null, 1);
                } finally {
                    obtain.recycle();
                }
            }

            @Override // android.app.IActivityClientController
            public void activityLocalRelaunch(IBinder iBinder) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                try {
                    obtain.writeInterfaceToken(IActivityClientController.DESCRIPTOR);
                    obtain.writeStrongBinder(iBinder);
                    this.mRemote.transact(8, obtain, null, 1);
                } finally {
                    obtain.recycle();
                }
            }

            @Override // android.app.IActivityClientController
            public void activityRelaunched(IBinder iBinder) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                try {
                    obtain.writeInterfaceToken(IActivityClientController.DESCRIPTOR);
                    obtain.writeStrongBinder(iBinder);
                    this.mRemote.transact(9, obtain, null, 1);
                } finally {
                    obtain.recycle();
                }
            }

            @Override // android.app.IActivityClientController
            public void reportSizeConfigurations(IBinder iBinder, SizeConfigurationBuckets sizeConfigurationBuckets) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                try {
                    obtain.writeInterfaceToken(IActivityClientController.DESCRIPTOR);
                    obtain.writeStrongBinder(iBinder);
                    obtain.writeTypedObject(sizeConfigurationBuckets, 0);
                    this.mRemote.transact(10, obtain, null, 1);
                } finally {
                    obtain.recycle();
                }
            }

            @Override // android.app.IActivityClientController
            public boolean moveActivityTaskToBack(IBinder iBinder, boolean z) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(IActivityClientController.DESCRIPTOR);
                    obtain.writeStrongBinder(iBinder);
                    obtain.writeBoolean(z);
                    this.mRemote.transact(11, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readBoolean();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.app.IActivityClientController
            public boolean shouldUpRecreateTask(IBinder iBinder, String str) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(IActivityClientController.DESCRIPTOR);
                    obtain.writeStrongBinder(iBinder);
                    obtain.writeString(str);
                    this.mRemote.transact(12, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readBoolean();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.app.IActivityClientController
            public boolean navigateUpTo(IBinder iBinder, Intent intent, String str, int i, Intent intent2) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(IActivityClientController.DESCRIPTOR);
                    obtain.writeStrongBinder(iBinder);
                    obtain.writeTypedObject(intent, 0);
                    obtain.writeString(str);
                    obtain.writeInt(i);
                    obtain.writeTypedObject(intent2, 0);
                    this.mRemote.transact(13, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readBoolean();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.app.IActivityClientController
            public boolean releaseActivityInstance(IBinder iBinder) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(IActivityClientController.DESCRIPTOR);
                    obtain.writeStrongBinder(iBinder);
                    this.mRemote.transact(14, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readBoolean();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.app.IActivityClientController
            public boolean finishActivity(IBinder iBinder, int i, Intent intent, int i2) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(IActivityClientController.DESCRIPTOR);
                    obtain.writeStrongBinder(iBinder);
                    obtain.writeInt(i);
                    obtain.writeTypedObject(intent, 0);
                    obtain.writeInt(i2);
                    this.mRemote.transact(15, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readBoolean();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.app.IActivityClientController
            public boolean finishActivityAffinity(IBinder iBinder) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(IActivityClientController.DESCRIPTOR);
                    obtain.writeStrongBinder(iBinder);
                    this.mRemote.transact(16, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readBoolean();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.app.IActivityClientController
            public void finishSubActivity(IBinder iBinder, String str, int i) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(IActivityClientController.DESCRIPTOR);
                    obtain.writeStrongBinder(iBinder);
                    obtain.writeString(str);
                    obtain.writeInt(i);
                    this.mRemote.transact(17, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.app.IActivityClientController
            public void setForceSendResultForMediaProjection(IBinder iBinder) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(IActivityClientController.DESCRIPTOR);
                    obtain.writeStrongBinder(iBinder);
                    this.mRemote.transact(18, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.app.IActivityClientController
            public boolean isTopOfTask(IBinder iBinder) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(IActivityClientController.DESCRIPTOR);
                    obtain.writeStrongBinder(iBinder);
                    this.mRemote.transact(19, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readBoolean();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.app.IActivityClientController
            public boolean willActivityBeVisible(IBinder iBinder) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(IActivityClientController.DESCRIPTOR);
                    obtain.writeStrongBinder(iBinder);
                    this.mRemote.transact(20, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readBoolean();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.app.IActivityClientController
            public int getDisplayId(IBinder iBinder) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(IActivityClientController.DESCRIPTOR);
                    obtain.writeStrongBinder(iBinder);
                    this.mRemote.transact(21, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readInt();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.app.IActivityClientController
            public int getTaskForActivity(IBinder iBinder, boolean z) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(IActivityClientController.DESCRIPTOR);
                    obtain.writeStrongBinder(iBinder);
                    obtain.writeBoolean(z);
                    this.mRemote.transact(22, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readInt();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.app.IActivityClientController
            public Configuration getTaskConfiguration(IBinder iBinder) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(IActivityClientController.DESCRIPTOR);
                    obtain.writeStrongBinder(iBinder);
                    this.mRemote.transact(23, obtain, obtain2, 0);
                    obtain2.readException();
                    return (Configuration) obtain2.readTypedObject(Configuration.CREATOR);
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.app.IActivityClientController
            public IBinder getActivityTokenBelow(IBinder iBinder) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(IActivityClientController.DESCRIPTOR);
                    obtain.writeStrongBinder(iBinder);
                    this.mRemote.transact(24, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readStrongBinder();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.app.IActivityClientController
            public ComponentName getCallingActivity(IBinder iBinder) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(IActivityClientController.DESCRIPTOR);
                    obtain.writeStrongBinder(iBinder);
                    this.mRemote.transact(25, obtain, obtain2, 0);
                    obtain2.readException();
                    return (ComponentName) obtain2.readTypedObject(ComponentName.CREATOR);
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.app.IActivityClientController
            public String getCallingPackage(IBinder iBinder) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(IActivityClientController.DESCRIPTOR);
                    obtain.writeStrongBinder(iBinder);
                    this.mRemote.transact(26, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readString();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.app.IActivityClientController
            public int getLaunchedFromUid(IBinder iBinder) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(IActivityClientController.DESCRIPTOR);
                    obtain.writeStrongBinder(iBinder);
                    this.mRemote.transact(27, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readInt();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.app.IActivityClientController
            public int getActivityCallerUid(IBinder iBinder, IBinder iBinder2) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(IActivityClientController.DESCRIPTOR);
                    obtain.writeStrongBinder(iBinder);
                    obtain.writeStrongBinder(iBinder2);
                    this.mRemote.transact(28, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readInt();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.app.IActivityClientController
            public String getLaunchedFromPackage(IBinder iBinder) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(IActivityClientController.DESCRIPTOR);
                    obtain.writeStrongBinder(iBinder);
                    this.mRemote.transact(29, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readString();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.app.IActivityClientController
            public String getActivityCallerPackage(IBinder iBinder, IBinder iBinder2) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(IActivityClientController.DESCRIPTOR);
                    obtain.writeStrongBinder(iBinder);
                    obtain.writeStrongBinder(iBinder2);
                    this.mRemote.transact(30, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readString();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.app.IActivityClientController
            public int checkActivityCallerContentUriPermission(IBinder iBinder, IBinder iBinder2, Uri uri, int i, int i2) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(IActivityClientController.DESCRIPTOR);
                    obtain.writeStrongBinder(iBinder);
                    obtain.writeStrongBinder(iBinder2);
                    obtain.writeTypedObject(uri, 0);
                    obtain.writeInt(i);
                    obtain.writeInt(i2);
                    this.mRemote.transact(31, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readInt();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.app.IActivityClientController
            public void setRequestedOrientation(IBinder iBinder, int i) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(IActivityClientController.DESCRIPTOR);
                    obtain.writeStrongBinder(iBinder);
                    obtain.writeInt(i);
                    this.mRemote.transact(32, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.app.IActivityClientController
            public int getRequestedOrientation(IBinder iBinder) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(IActivityClientController.DESCRIPTOR);
                    obtain.writeStrongBinder(iBinder);
                    this.mRemote.transact(33, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readInt();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.app.IActivityClientController
            public boolean convertFromTranslucent(IBinder iBinder) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(IActivityClientController.DESCRIPTOR);
                    obtain.writeStrongBinder(iBinder);
                    this.mRemote.transact(34, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readBoolean();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.app.IActivityClientController
            public boolean convertToTranslucent(IBinder iBinder, Bundle bundle) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(IActivityClientController.DESCRIPTOR);
                    obtain.writeStrongBinder(iBinder);
                    obtain.writeTypedObject(bundle, 0);
                    this.mRemote.transact(35, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readBoolean();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.app.IActivityClientController
            public boolean isImmersive(IBinder iBinder) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(IActivityClientController.DESCRIPTOR);
                    obtain.writeStrongBinder(iBinder);
                    this.mRemote.transact(36, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readBoolean();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.app.IActivityClientController
            public void setImmersive(IBinder iBinder, boolean z) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(IActivityClientController.DESCRIPTOR);
                    obtain.writeStrongBinder(iBinder);
                    obtain.writeBoolean(z);
                    this.mRemote.transact(37, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.app.IActivityClientController
            public boolean enterPictureInPictureMode(IBinder iBinder, PictureInPictureParams pictureInPictureParams) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(IActivityClientController.DESCRIPTOR);
                    obtain.writeStrongBinder(iBinder);
                    obtain.writeTypedObject(pictureInPictureParams, 0);
                    this.mRemote.transact(38, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readBoolean();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.app.IActivityClientController
            public void setPictureInPictureParams(IBinder iBinder, PictureInPictureParams pictureInPictureParams) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(IActivityClientController.DESCRIPTOR);
                    obtain.writeStrongBinder(iBinder);
                    obtain.writeTypedObject(pictureInPictureParams, 0);
                    this.mRemote.transact(39, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.app.IActivityClientController
            public void setShouldDockBigOverlays(IBinder iBinder, boolean z) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                try {
                    obtain.writeInterfaceToken(IActivityClientController.DESCRIPTOR);
                    obtain.writeStrongBinder(iBinder);
                    obtain.writeBoolean(z);
                    this.mRemote.transact(40, obtain, null, 1);
                } finally {
                    obtain.recycle();
                }
            }

            @Override // android.app.IActivityClientController
            public void toggleFreeformWindowingMode(IBinder iBinder) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(IActivityClientController.DESCRIPTOR);
                    obtain.writeStrongBinder(iBinder);
                    this.mRemote.transact(41, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.app.IActivityClientController
            public void requestMultiwindowFullscreen(IBinder iBinder, int i, IRemoteCallback iRemoteCallback) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                try {
                    obtain.writeInterfaceToken(IActivityClientController.DESCRIPTOR);
                    obtain.writeStrongBinder(iBinder);
                    obtain.writeInt(i);
                    obtain.writeStrongInterface(iRemoteCallback);
                    this.mRemote.transact(42, obtain, null, 1);
                } finally {
                    obtain.recycle();
                }
            }

            @Override // android.app.IActivityClientController
            public void startLockTaskModeByToken(IBinder iBinder) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(IActivityClientController.DESCRIPTOR);
                    obtain.writeStrongBinder(iBinder);
                    this.mRemote.transact(43, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.app.IActivityClientController
            public void stopLockTaskModeByToken(IBinder iBinder) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(IActivityClientController.DESCRIPTOR);
                    obtain.writeStrongBinder(iBinder);
                    this.mRemote.transact(44, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.app.IActivityClientController
            public void showLockTaskEscapeMessage(IBinder iBinder) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                try {
                    obtain.writeInterfaceToken(IActivityClientController.DESCRIPTOR);
                    obtain.writeStrongBinder(iBinder);
                    this.mRemote.transact(45, obtain, null, 1);
                } finally {
                    obtain.recycle();
                }
            }

            @Override // android.app.IActivityClientController
            public void setTaskDescription(IBinder iBinder, ActivityManager.TaskDescription taskDescription) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(IActivityClientController.DESCRIPTOR);
                    obtain.writeStrongBinder(iBinder);
                    obtain.writeTypedObject(taskDescription, 0);
                    this.mRemote.transact(46, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.app.IActivityClientController
            public boolean showAssistFromActivity(IBinder iBinder, Bundle bundle) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(IActivityClientController.DESCRIPTOR);
                    obtain.writeStrongBinder(iBinder);
                    obtain.writeTypedObject(bundle, 0);
                    this.mRemote.transact(47, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readBoolean();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.app.IActivityClientController
            public boolean isRootVoiceInteraction(IBinder iBinder) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(IActivityClientController.DESCRIPTOR);
                    obtain.writeStrongBinder(iBinder);
                    this.mRemote.transact(48, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readBoolean();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.app.IActivityClientController
            public void startLocalVoiceInteraction(IBinder iBinder, Bundle bundle) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(IActivityClientController.DESCRIPTOR);
                    obtain.writeStrongBinder(iBinder);
                    obtain.writeTypedObject(bundle, 0);
                    this.mRemote.transact(49, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.app.IActivityClientController
            public void stopLocalVoiceInteraction(IBinder iBinder) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(IActivityClientController.DESCRIPTOR);
                    obtain.writeStrongBinder(iBinder);
                    this.mRemote.transact(50, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.app.IActivityClientController
            public void setShowWhenLocked(IBinder iBinder, boolean z) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                try {
                    obtain.writeInterfaceToken(IActivityClientController.DESCRIPTOR);
                    obtain.writeStrongBinder(iBinder);
                    obtain.writeBoolean(z);
                    this.mRemote.transact(51, obtain, null, 1);
                } finally {
                    obtain.recycle();
                }
            }

            @Override // android.app.IActivityClientController
            public void setInheritShowWhenLocked(IBinder iBinder, boolean z) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                try {
                    obtain.writeInterfaceToken(IActivityClientController.DESCRIPTOR);
                    obtain.writeStrongBinder(iBinder);
                    obtain.writeBoolean(z);
                    this.mRemote.transact(52, obtain, null, 1);
                } finally {
                    obtain.recycle();
                }
            }

            @Override // android.app.IActivityClientController
            public void setTurnScreenOn(IBinder iBinder, boolean z) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(IActivityClientController.DESCRIPTOR);
                    obtain.writeStrongBinder(iBinder);
                    obtain.writeBoolean(z);
                    this.mRemote.transact(53, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.app.IActivityClientController
            public void setAllowCrossUidActivitySwitchFromBelow(IBinder iBinder, boolean z) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                try {
                    obtain.writeInterfaceToken(IActivityClientController.DESCRIPTOR);
                    obtain.writeStrongBinder(iBinder);
                    obtain.writeBoolean(z);
                    this.mRemote.transact(54, obtain, null, 1);
                } finally {
                    obtain.recycle();
                }
            }

            @Override // android.app.IActivityClientController
            public void reportActivityFullyDrawn(IBinder iBinder, boolean z) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                try {
                    obtain.writeInterfaceToken(IActivityClientController.DESCRIPTOR);
                    obtain.writeStrongBinder(iBinder);
                    obtain.writeBoolean(z);
                    this.mRemote.transact(55, obtain, null, 1);
                } finally {
                    obtain.recycle();
                }
            }

            @Override // android.app.IActivityClientController
            public void overrideActivityTransition(IBinder iBinder, boolean z, int i, int i2, int i3) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                try {
                    obtain.writeInterfaceToken(IActivityClientController.DESCRIPTOR);
                    obtain.writeStrongBinder(iBinder);
                    obtain.writeBoolean(z);
                    obtain.writeInt(i);
                    obtain.writeInt(i2);
                    obtain.writeInt(i3);
                    this.mRemote.transact(56, obtain, null, 1);
                } finally {
                    obtain.recycle();
                }
            }

            @Override // android.app.IActivityClientController
            public void clearOverrideActivityTransition(IBinder iBinder, boolean z) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                try {
                    obtain.writeInterfaceToken(IActivityClientController.DESCRIPTOR);
                    obtain.writeStrongBinder(iBinder);
                    obtain.writeBoolean(z);
                    this.mRemote.transact(57, obtain, null, 1);
                } finally {
                    obtain.recycle();
                }
            }

            @Override // android.app.IActivityClientController
            public void overridePendingTransition(IBinder iBinder, String str, int i, int i2, int i3) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(IActivityClientController.DESCRIPTOR);
                    obtain.writeStrongBinder(iBinder);
                    obtain.writeString(str);
                    obtain.writeInt(i);
                    obtain.writeInt(i2);
                    obtain.writeInt(i3);
                    this.mRemote.transact(58, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.app.IActivityClientController
            public int setVrMode(IBinder iBinder, boolean z, ComponentName componentName) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(IActivityClientController.DESCRIPTOR);
                    obtain.writeStrongBinder(iBinder);
                    obtain.writeBoolean(z);
                    obtain.writeTypedObject(componentName, 0);
                    this.mRemote.transact(59, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readInt();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.app.IActivityClientController
            public void setRecentsScreenshotEnabled(IBinder iBinder, boolean z) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                try {
                    obtain.writeInterfaceToken(IActivityClientController.DESCRIPTOR);
                    obtain.writeStrongBinder(iBinder);
                    obtain.writeBoolean(z);
                    this.mRemote.transact(60, obtain, null, 1);
                } finally {
                    obtain.recycle();
                }
            }

            @Override // android.app.IActivityClientController
            public void invalidateHomeTaskSnapshot(IBinder iBinder) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(IActivityClientController.DESCRIPTOR);
                    obtain.writeStrongBinder(iBinder);
                    this.mRemote.transact(61, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.app.IActivityClientController
            public void dismissKeyguard(IBinder iBinder, IKeyguardDismissCallback iKeyguardDismissCallback, CharSequence charSequence) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(IActivityClientController.DESCRIPTOR);
                    obtain.writeStrongBinder(iBinder);
                    obtain.writeStrongInterface(iKeyguardDismissCallback);
                    if (charSequence != null) {
                        obtain.writeInt(1);
                        TextUtils.writeToParcel(charSequence, obtain, 0);
                    } else {
                        obtain.writeInt(0);
                    }
                    this.mRemote.transact(62, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.app.IActivityClientController
            public void registerRemoteAnimations(IBinder iBinder, RemoteAnimationDefinition remoteAnimationDefinition) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(IActivityClientController.DESCRIPTOR);
                    obtain.writeStrongBinder(iBinder);
                    obtain.writeTypedObject(remoteAnimationDefinition, 0);
                    this.mRemote.transact(63, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.app.IActivityClientController
            public void unregisterRemoteAnimations(IBinder iBinder) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(IActivityClientController.DESCRIPTOR);
                    obtain.writeStrongBinder(iBinder);
                    this.mRemote.transact(64, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.app.IActivityClientController
            public void onBackPressed(IBinder iBinder, IRequestFinishCallback iRequestFinishCallback) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                try {
                    obtain.writeInterfaceToken(IActivityClientController.DESCRIPTOR);
                    obtain.writeStrongBinder(iBinder);
                    obtain.writeStrongInterface(iRequestFinishCallback);
                    this.mRemote.transact(65, obtain, null, 1);
                } finally {
                    obtain.recycle();
                }
            }

            @Override // android.app.IActivityClientController
            public void splashScreenAttached(IBinder iBinder) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                try {
                    obtain.writeInterfaceToken(IActivityClientController.DESCRIPTOR);
                    obtain.writeStrongBinder(iBinder);
                    this.mRemote.transact(66, obtain, null, 1);
                } finally {
                    obtain.recycle();
                }
            }

            @Override // android.app.IActivityClientController
            public void enableTaskLocaleOverride(IBinder iBinder) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(IActivityClientController.DESCRIPTOR);
                    obtain.writeStrongBinder(iBinder);
                    this.mRemote.transact(67, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.app.IActivityClientController
            public boolean isRequestedToLaunchInTaskFragment(IBinder iBinder, IBinder iBinder2) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(IActivityClientController.DESCRIPTOR);
                    obtain.writeStrongBinder(iBinder);
                    obtain.writeStrongBinder(iBinder2);
                    this.mRemote.transact(68, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readBoolean();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.app.IActivityClientController
            public void setActivityRecordInputSinkEnabled(IBinder iBinder, boolean z) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                try {
                    obtain.writeInterfaceToken(IActivityClientController.DESCRIPTOR);
                    obtain.writeStrongBinder(iBinder);
                    obtain.writeBoolean(z);
                    this.mRemote.transact(69, obtain, null, 1);
                } finally {
                    obtain.recycle();
                }
            }

            @Override // android.app.IActivityClientController
            public boolean convertFromTranslucentOp(IBinder iBinder, boolean z) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(IActivityClientController.DESCRIPTOR);
                    obtain.writeStrongBinder(iBinder);
                    obtain.writeBoolean(z);
                    this.mRemote.transact(70, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readBoolean();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.app.IActivityClientController
            public void overridePendingTaskTransition(IBinder iBinder, String str, int i, int i2) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(IActivityClientController.DESCRIPTOR);
                    obtain.writeStrongBinder(iBinder);
                    obtain.writeString(str);
                    obtain.writeInt(i);
                    obtain.writeInt(i2);
                    this.mRemote.transact(71, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.app.IActivityClientController
            public void adjustPopOverOptions(IBinder iBinder, int[] iArr, int[] iArr2, Point[] pointArr, int[] iArr3) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(IActivityClientController.DESCRIPTOR);
                    obtain.writeStrongBinder(iBinder);
                    obtain.writeIntArray(iArr);
                    obtain.writeIntArray(iArr2);
                    obtain.writeTypedArray(pointArr, 0);
                    obtain.writeIntArray(iArr3);
                    this.mRemote.transact(72, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }
        }
    }
}
