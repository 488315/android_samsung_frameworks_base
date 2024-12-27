package com.android.server.wm;

import android.app.ActivityOptions;
import android.app.AppGlobals;
import android.app.WindowConfiguration;
import android.content.ComponentName;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.os.Binder;
import android.os.Bundle;
import android.os.UserHandle;
import android.util.Slog;
import android.view.RemoteAnimationAdapter;
import android.window.WindowContainerToken;

import com.android.server.ServiceKeeper$$ExternalSyntheticOutline0;
import com.android.server.am.ActivityManagerService;
import com.android.server.am.ActivityManagerService$$ExternalSyntheticOutline0;

import java.util.function.Function;

/* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
public final class SafeActivityOptions {
    public ActivityOptions mCallerOptions;
    public final int mOriginalCallingPid;
    public final int mOriginalCallingUid;
    public final ActivityOptions mOriginalOptions;
    public int mRealCallingPid;
    public int mRealCallingUid;
    public boolean mShouldCheckFreeform;

    public SafeActivityOptions(ActivityOptions activityOptions) {
        this.mShouldCheckFreeform = false;
        this.mOriginalCallingPid = Binder.getCallingPid();
        this.mOriginalCallingUid = Binder.getCallingUid();
        this.mOriginalOptions = activityOptions;
    }

    public SafeActivityOptions(ActivityOptions activityOptions, int i, int i2) {
        this.mShouldCheckFreeform = false;
        this.mOriginalCallingPid = i;
        this.mOriginalCallingUid = i2;
        this.mOriginalOptions = activityOptions;
    }

    public static void abort(SafeActivityOptions safeActivityOptions) {
        if (safeActivityOptions != null) {
            ActivityOptions activityOptions = safeActivityOptions.mOriginalOptions;
            if (activityOptions != null) {
                ActivityOptions.abort(activityOptions);
            }
            ActivityOptions activityOptions2 = safeActivityOptions.mCallerOptions;
            if (activityOptions2 != null) {
                ActivityOptions.abort(activityOptions2);
            }
        }
    }

    public static ActivityOptions cloneLaunchingOptions(ActivityOptions activityOptions) {
        if (activityOptions == null) {
            return null;
        }
        return ActivityOptions.makeBasic()
                .setLaunchTaskDisplayArea(activityOptions.getLaunchTaskDisplayArea())
                .setLaunchDisplayId(activityOptions.getLaunchDisplayId())
                .setCallerDisplayId(activityOptions.getCallerDisplayId())
                .setLaunchRootTask(activityOptions.getLaunchRootTask())
                .setPendingIntentBackgroundActivityStartMode(
                        activityOptions.getPendingIntentBackgroundActivityStartMode())
                .setPendingIntentCreatorBackgroundActivityStartMode(
                        activityOptions.getPendingIntentCreatorBackgroundActivityStartMode())
                .setRemoteTransition(activityOptions.getRemoteTransition());
    }

    public static SafeActivityOptions fromBundle(Bundle bundle) {
        if (bundle != null) {
            return new SafeActivityOptions(ActivityOptions.fromBundle(bundle));
        }
        return null;
    }

    public static String getIntentString(Intent intent) {
        return intent != null ? intent.toString() : "(no intent)";
    }

    public static boolean isAssistant(
            int i, ActivityTaskManagerService activityTaskManagerService) {
        ComponentName componentName =
                activityTaskManagerService.mActiveVoiceInteractionServiceComponent;
        if (componentName == null) {
            return false;
        }
        return AppGlobals.getPackageManager()
                        .getPackageUid(
                                componentName.getPackageName(), 268435456L, UserHandle.getUserId(i))
                == i;
    }

    public final void checkPermissions(
            Intent intent,
            ActivityInfo activityInfo,
            WindowProcessController windowProcessController,
            ActivityTaskSupervisor activityTaskSupervisor,
            ActivityOptions activityOptions,
            int i,
            int i2) {
        int i3;
        int i4;
        if ((activityOptions.getLaunchTaskId() != -1 || activityOptions.getDisableStartingWindow())
                && !activityTaskSupervisor.mRecentTasks.isCallerRecents(i2)) {
            Boolean bool = ActivityTaskManagerService.sIsPip2ExperimentEnabled;
            i3 = -1;
            if (ActivityManagerService.checkComponentPermission(
                            i, i2, "android.permission.START_TASKS_FROM_RECENTS", 0, -1, true)
                    == -1) {
                StringBuilder sb = new StringBuilder("Permission Denial: starting ");
                sb.append(getIntentString(intent));
                sb.append(" from ");
                sb.append(windowProcessController);
                sb.append(" (pid=");
                ServiceKeeper$$ExternalSyntheticOutline0.m(
                        i, i2, ", uid=", ") with launchTaskId=", sb);
                sb.append(activityOptions.getLaunchTaskId());
                String sb2 = sb.toString();
                Slog.w("ActivityTaskManager", sb2);
                throw new SecurityException(sb2);
            }
        } else {
            i3 = -1;
        }
        if (activityOptions.getTransientLaunch()
                && !activityTaskSupervisor.mRecentTasks.isCallerRecents(i2)) {
            Boolean bool2 = ActivityTaskManagerService.sIsPip2ExperimentEnabled;
            if (ActivityManagerService.checkComponentPermission(
                            i, i2, "android.permission.MANAGE_ACTIVITY_TASKS", 0, -1, true)
                    == i3) {
                String str =
                        "Permission Denial: starting transient launch from "
                                + windowProcessController
                                + ", pid="
                                + i
                                + ", uid="
                                + i2;
                Slog.w("ActivityTaskManager", str);
                throw new SecurityException(str);
            }
        }
        TaskDisplayArea launchTaskDisplayArea =
                getLaunchTaskDisplayArea(activityOptions, activityTaskSupervisor);
        int i5 = i3;
        if (activityInfo != null && launchTaskDisplayArea != null) {
            activityTaskSupervisor.getClass();
            if (!activityTaskSupervisor.isCallerAllowedToLaunchOnDisplay(
                    i, i2, launchTaskDisplayArea.mDisplayContent.mDisplayId, activityInfo)) {
                StringBuilder sb3 = new StringBuilder("Permission Denial: starting ");
                sb3.append(getIntentString(intent));
                sb3.append(" from ");
                sb3.append(windowProcessController);
                sb3.append(" (pid=");
                ServiceKeeper$$ExternalSyntheticOutline0.m(
                        i, i2, ", uid=", ") with launchTaskDisplayArea=", sb3);
                sb3.append(launchTaskDisplayArea);
                String sb4 = sb3.toString();
                Slog.w("ActivityTaskManager", sb4);
                throw new SecurityException(sb4);
            }
        }
        int launchDisplayId = activityOptions.getLaunchDisplayId();
        if (activityInfo != null
                && launchDisplayId != i5
                && !activityTaskSupervisor.isCallerAllowedToLaunchOnDisplay(
                        i, i2, launchDisplayId, activityInfo)) {
            StringBuilder sb5 = new StringBuilder("Permission Denial: starting ");
            sb5.append(getIntentString(intent));
            sb5.append(" from ");
            sb5.append(windowProcessController);
            sb5.append(" (pid=");
            ServiceKeeper$$ExternalSyntheticOutline0.m(
                    i, i2, ", uid=", ") with launchDisplayId=", sb5);
            sb5.append(launchDisplayId);
            String sb6 = sb5.toString();
            Slog.w("ActivityTaskManager", sb6);
            throw new SecurityException(sb6);
        }
        boolean lockTaskMode = activityOptions.getLockTaskMode();
        if (activityInfo != null
                && lockTaskMode
                && !activityTaskSupervisor.mService.mLockTaskController.isPackageAllowlisted(
                        UserHandle.getUserId(i2), activityInfo.packageName)) {
            StringBuilder sb7 = new StringBuilder("Permission Denial: starting ");
            sb7.append(getIntentString(intent));
            sb7.append(" from ");
            sb7.append(windowProcessController);
            sb7.append(" (pid=");
            String m =
                    ActivityManagerService$$ExternalSyntheticOutline0.m(
                            i, i2, ", uid=", ") with lockTaskMode=true", sb7);
            Slog.w("ActivityTaskManager", m);
            throw new SecurityException(m);
        }
        boolean overrideTaskTransition = activityOptions.getOverrideTaskTransition();
        if (activityInfo != null && overrideTaskTransition) {
            Boolean bool3 = ActivityTaskManagerService.sIsPip2ExperimentEnabled;
            if (ActivityManagerService.checkComponentPermission(
                                    i,
                                    i2,
                                    "android.permission.START_TASKS_FROM_RECENTS",
                                    0,
                                    -1,
                                    true)
                            != 0
                    && !isAssistant(i2, activityTaskSupervisor.mService)) {
                StringBuilder sb8 = new StringBuilder("Permission Denial: starting ");
                sb8.append(getIntentString(intent));
                sb8.append(" from ");
                sb8.append(windowProcessController);
                sb8.append(" (pid=");
                String m2 =
                        ActivityManagerService$$ExternalSyntheticOutline0.m(
                                i, i2, ", uid=", ") with overrideTaskTransition=true", sb8);
                Slog.w("ActivityTaskManager", m2);
                throw new SecurityException(m2);
            }
        }
        boolean dismissKeyguardIfInsecure = activityOptions.getDismissKeyguardIfInsecure();
        if (activityInfo != null && dismissKeyguardIfInsecure) {
            Boolean bool4 = ActivityTaskManagerService.sIsPip2ExperimentEnabled;
            if (ActivityManagerService.checkComponentPermission(
                            i, i2, "android.permission.CONTROL_KEYGUARD", 0, -1, true)
                    != 0) {
                StringBuilder sb9 = new StringBuilder("Permission Denial: starting ");
                sb9.append(getIntentString(intent));
                sb9.append(" from ");
                sb9.append(windowProcessController);
                sb9.append(" (pid=");
                String m3 =
                        ActivityManagerService$$ExternalSyntheticOutline0.m(
                                i, i2, ", uid=", ") with dismissKeyguardIfInsecure=true", sb9);
                Slog.w("ActivityTaskManager", m3);
                throw new SecurityException(m3);
            }
        }
        if (activityOptions.getRemoteAnimationAdapter() != null) {
            ActivityTaskManagerService activityTaskManagerService = activityTaskSupervisor.mService;
            Boolean bool5 = ActivityTaskManagerService.sIsPip2ExperimentEnabled;
            if (ActivityManagerService.checkComponentPermission(
                            i,
                            i2,
                            "android.permission.CONTROL_REMOTE_APP_TRANSITION_ANIMATIONS",
                            0,
                            -1,
                            true)
                    != 0) {
                StringBuilder sb10 = new StringBuilder("Permission Denial: starting ");
                sb10.append(getIntentString(intent));
                sb10.append(" from ");
                sb10.append(windowProcessController);
                sb10.append(" (pid=");
                String m4 =
                        ActivityManagerService$$ExternalSyntheticOutline0.m(
                                i, i2, ", uid=", ") with remoteAnimationAdapter", sb10);
                Slog.w("ActivityTaskManager", m4);
                throw new SecurityException(m4);
            }
        }
        if (activityOptions.getRemoteTransition() != null) {
            ActivityTaskManagerService activityTaskManagerService2 =
                    activityTaskSupervisor.mService;
            Boolean bool6 = ActivityTaskManagerService.sIsPip2ExperimentEnabled;
            if (ActivityManagerService.checkComponentPermission(
                            i,
                            i2,
                            "android.permission.CONTROL_REMOTE_APP_TRANSITION_ANIMATIONS",
                            0,
                            -1,
                            true)
                    != 0) {
                StringBuilder sb11 = new StringBuilder("Permission Denial: starting ");
                sb11.append(getIntentString(intent));
                sb11.append(" from ");
                sb11.append(windowProcessController);
                sb11.append(" (pid=");
                String m5 =
                        ActivityManagerService$$ExternalSyntheticOutline0.m(
                                i, i2, ", uid=", ") with remoteTransition", sb11);
                Slog.w("ActivityTaskManager", m5);
                throw new SecurityException(m5);
            }
        }
        if (!activityOptions.getLaunchedFromBubble() || i2 == 1000) {
            i4 = 1000;
        } else {
            Boolean bool7 = ActivityTaskManagerService.sIsPip2ExperimentEnabled;
            i4 = 1000;
            if (ActivityManagerService.checkComponentPermission(
                            i, i2, "android.permission.STATUS_BAR_SERVICE", 0, -1, true)
                    != 0) {
                StringBuilder sb12 = new StringBuilder("Permission Denial: starting ");
                sb12.append(getIntentString(intent));
                sb12.append(" from ");
                sb12.append(windowProcessController);
                sb12.append(" (pid=");
                String m6 =
                        ActivityManagerService$$ExternalSyntheticOutline0.m(
                                i, i2, ", uid=", ") with launchedFromBubble=true", sb12);
                Slog.w("ActivityTaskManager", m6);
                throw new SecurityException(m6);
            }
        }
        int launchActivityType = activityOptions.getLaunchActivityType();
        if (launchActivityType == 0 || i2 == i4) {
            return;
        }
        Boolean bool8 = ActivityTaskManagerService.sIsPip2ExperimentEnabled;
        if (ActivityManagerService.checkComponentPermission(
                        i, i2, "android.permission.STATUS_BAR_SERVICE", 0, -1, true)
                == 0) {
            return;
        }
        if (launchActivityType == 4 && isAssistant(i2, activityTaskSupervisor.mService)) {
            return;
        }
        StringBuilder sb13 = new StringBuilder("Permission Denial: starting ");
        sb13.append(getIntentString(intent));
        sb13.append(" from ");
        sb13.append(windowProcessController);
        sb13.append(" (pid=");
        ServiceKeeper$$ExternalSyntheticOutline0.m(
                i, i2, ", uid=", ") with launchActivityType=", sb13);
        sb13.append(
                WindowConfiguration.activityTypeToString(activityOptions.getLaunchActivityType()));
        String sb14 = sb13.toString();
        Slog.w("ActivityTaskManager", sb14);
        throw new SecurityException(sb14);
    }

    public TaskDisplayArea getLaunchTaskDisplayArea(
            ActivityOptions activityOptions, ActivityTaskSupervisor activityTaskSupervisor) {
        final int launchTaskDisplayAreaFeatureId;
        WindowContainerToken launchTaskDisplayArea = activityOptions.getLaunchTaskDisplayArea();
        TaskDisplayArea taskDisplayArea =
                launchTaskDisplayArea != null
                        ? (TaskDisplayArea)
                                WindowContainer.fromBinder(launchTaskDisplayArea.asBinder())
                        : null;
        if (taskDisplayArea != null
                || (launchTaskDisplayAreaFeatureId =
                                activityOptions.getLaunchTaskDisplayAreaFeatureId())
                        == -1) {
            return taskDisplayArea;
        }
        DisplayContent displayContent =
                activityTaskSupervisor.mRootWindowContainer.getDisplayContent(
                        activityOptions.getLaunchDisplayId() == -1
                                ? 0
                                : activityOptions.getLaunchDisplayId());
        return displayContent != null
                ? (TaskDisplayArea)
                        displayContent.getItemFromTaskDisplayAreas(
                                new Function() { // from class:
                                                 // com.android.server.wm.SafeActivityOptions$$ExternalSyntheticLambda0
                                    @Override // java.util.function.Function
                                    public final Object apply(Object obj) {
                                        TaskDisplayArea taskDisplayArea2 = (TaskDisplayArea) obj;
                                        if (taskDisplayArea2.mFeatureId
                                                == launchTaskDisplayAreaFeatureId) {
                                            return taskDisplayArea2;
                                        }
                                        return null;
                                    }
                                })
                : taskDisplayArea;
    }

    public final ActivityOptions getOptions(
            Intent intent,
            ActivityInfo activityInfo,
            WindowProcessController windowProcessController,
            ActivityTaskSupervisor activityTaskSupervisor) {
        ActivityOptions activityOptions = this.mOriginalOptions;
        if (activityOptions != null) {
            checkPermissions(
                    intent,
                    activityInfo,
                    windowProcessController,
                    activityTaskSupervisor,
                    activityOptions,
                    this.mOriginalCallingPid,
                    this.mOriginalCallingUid);
            RemoteAnimationAdapter remoteAnimationAdapter =
                    this.mOriginalOptions.getRemoteAnimationAdapter();
            if (remoteAnimationAdapter != null) {
                int i = WindowManagerService.MY_PID;
                int i2 = this.mOriginalCallingPid;
                if (i2 == i) {
                    Slog.wtf(
                            "ActivityTaskManager",
                            "Safe activity options constructed after clearing calling id");
                } else {
                    remoteAnimationAdapter.setCallingPidUid(i2, this.mOriginalCallingUid);
                }
            }
        }
        ActivityOptions activityOptions2 = this.mCallerOptions;
        if (activityOptions2 != null) {
            checkPermissions(
                    intent,
                    activityInfo,
                    windowProcessController,
                    activityTaskSupervisor,
                    activityOptions2,
                    this.mRealCallingPid,
                    this.mRealCallingUid);
            ActivityOptions activityOptions3 = this.mCallerOptions;
            int i3 = this.mRealCallingPid;
            int i4 = this.mRealCallingUid;
            RemoteAnimationAdapter remoteAnimationAdapter2 =
                    activityOptions3.getRemoteAnimationAdapter();
            if (remoteAnimationAdapter2 != null) {
                if (i3 == WindowManagerService.MY_PID) {
                    Slog.wtf(
                            "ActivityTaskManager",
                            "Safe activity options constructed after clearing calling id");
                } else {
                    remoteAnimationAdapter2.setCallingPidUid(i3, i4);
                }
            }
        }
        return mergeActivityOptions(this.mOriginalOptions, this.mCallerOptions);
    }

    public ActivityOptions mergeActivityOptions(
            ActivityOptions activityOptions, ActivityOptions activityOptions2) {
        if (activityOptions == null) {
            return activityOptions2;
        }
        if (activityOptions2 == null) {
            return activityOptions;
        }
        Bundle bundle = activityOptions.toBundle();
        bundle.putAll(activityOptions2.toBundle());
        return ActivityOptions.fromBundle(bundle);
    }

    public final void setCallerOptions(ActivityOptions activityOptions) {
        this.mRealCallingPid = Binder.getCallingPid();
        this.mRealCallingUid = Binder.getCallingUid();
        this.mCallerOptions = activityOptions;
    }
}
