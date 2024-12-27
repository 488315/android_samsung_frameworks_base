package com.android.server.wm;

import android.app.BackgroundStartPrivileges;
import android.app.IApplicationThread;
import android.content.ComponentName;
import android.content.Intent;
import android.content.pm.ApplicationInfo;
import android.os.Bundle;
import android.os.IBinder;
import android.os.LocaleList;
import android.util.proto.ProtoOutputStream;

import com.android.server.am.PendingIntentRecord;
import com.android.server.remoteappmode.RemoteAppModeService;

import java.io.FileDescriptor;
import java.io.PrintWriter;
import java.util.List;

/* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
/* loaded from: classes2.dex */
public abstract class ActivityTaskManagerInternal {

    /* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
    public final class ActivityTokens {
        public final IBinder mActivityToken;
        public final IApplicationThread mAppThread;
        public final IBinder mAssistToken;
        public final IBinder mShareableActivityToken;
        public final int mUid;

        public ActivityTokens(
                IBinder iBinder,
                IBinder iBinder2,
                IApplicationThread iApplicationThread,
                IBinder iBinder3,
                int i) {
            this.mActivityToken = iBinder;
            this.mAssistToken = iBinder2;
            this.mAppThread = iApplicationThread;
            this.mShareableActivityToken = iBinder3;
            this.mUid = i;
        }
    }

    /* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
    public final class PackageConfig {
        public final Integer mGrammaticalGender;
        public final LocaleList mLocales;
        public final Integer mNightMode;

        public PackageConfig(Integer num, LocaleList localeList, Integer num2) {
            this.mNightMode = num;
            this.mLocales = localeList;
            this.mGrammaticalGender = num2;
        }

        public final String toString() {
            return "PackageConfig: nightMode " + this.mNightMode + " locales " + this.mLocales;
        }
    }

    /* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
    public interface ScreenObserver {
        void onAwakeStateChanged(boolean z);

        void onKeyguardStateChanged(boolean z);
    }

    public abstract boolean attachApplication(WindowProcessController windowProcessController);

    public abstract boolean canShowErrorDialogs();

    public abstract void cleanupRecentTasksForUser();

    public abstract void clearHeavyWeightProcessIfEquals(
            WindowProcessController windowProcessController);

    public abstract void clearHomeStack(int i);

    public abstract void closeSystemDialogs(String str);

    public abstract void dismissSplitScreenMode();

    public abstract void dump(
            String str,
            FileDescriptor fileDescriptor,
            PrintWriter printWriter,
            String[] strArr,
            int i,
            boolean z,
            boolean z2,
            String str2,
            int i2);

    public abstract boolean dumpForProcesses(
            PrintWriter printWriter, boolean z, String str, int i, boolean z2, boolean z3, int i2);

    public abstract void enableScreenAfterBoot(boolean z);

    public abstract ComponentName getActivityName(IBinder iBinder);

    public abstract List getAppTasks(int i, String str);

    public abstract ActivityTokens getAttachedNonFinishingActivityForTask(int i, IBinder iBinder);

    public abstract int getForegroundTaskId(int i);

    public abstract LaunchObserverRegistryImpl getLaunchObserverRegistry();

    public abstract int getTaskToShowPermissionDialogOn(int i, String str);

    public abstract int getTopProcessState();

    public abstract List getTopVisibleActivities();

    public abstract boolean isBaseOfLockedTask(String str);

    public abstract boolean isCallerRecents(int i);

    public abstract void minimizeAllTasks(int i, boolean z);

    public abstract boolean moveTaskToBack(int i, boolean z, Bundle bundle);

    public abstract void notifyActiveDreamChanged(ComponentName componentName);

    public abstract void onCleanUpApplicationRecord(
            WindowProcessController windowProcessController);

    public abstract boolean onForceStopPackage(String str, boolean z, boolean z2, int i);

    public abstract void onPackageAdded(String str, boolean z);

    public abstract void onPackageDataCleared(String str, int i);

    public abstract void onPackageReplaced(ApplicationInfo applicationInfo);

    public abstract void onPackageUninstalled(int i, String str);

    public abstract void onPackagesSuspendedChanged(int i, boolean z, String[] strArr);

    public abstract void onUserStopped(int i);

    public abstract void preBindApplication(WindowProcessController windowProcessController);

    public abstract void registerActivityStartInterceptor(
            int i, ActivityInterceptorCallback activityInterceptorCallback);

    public abstract void registerRemoteAppControllerCallbacks(
            RemoteAppModeService.AnonymousClass1 anonymousClass1);

    public abstract void registerScreenObserver(ScreenObserver screenObserver);

    public abstract void releaseAltTabKeyConsumer();

    public abstract void removeRecentTasksByPackageName(int i, String str);

    public abstract void resumeTopActivities(boolean z);

    public abstract void scheduleDestroyAllActivities();

    public abstract void sendActivityResult(
            IBinder iBinder, String str, int i, int i2, Intent intent);

    public abstract void setAllowAppSwitches(int i, int i2, String str);

    public abstract boolean showStrictModeViolationDialog();

    public abstract int startActivitiesAsPackage(
            String str, String str2, int i, Intent[] intentArr, Bundle bundle);

    public abstract int startActivitiesInPackage(
            int i,
            int i2,
            int i3,
            String str,
            String str2,
            Intent[] intentArr,
            String[] strArr,
            IBinder iBinder,
            SafeActivityOptions safeActivityOptions,
            int i4,
            PendingIntentRecord pendingIntentRecord,
            BackgroundStartPrivileges backgroundStartPrivileges);

    public abstract int startActivityAsUser(
            IApplicationThread iApplicationThread,
            String str,
            String str2,
            Intent intent,
            IBinder iBinder,
            int i,
            Bundle bundle,
            int i2);

    public abstract int startActivityInPackage(
            int i,
            int i2,
            int i3,
            String str,
            String str2,
            Intent intent,
            String str3,
            IBinder iBinder,
            String str4,
            int i4,
            SafeActivityOptions safeActivityOptions,
            int i5,
            PendingIntentRecord pendingIntentRecord,
            BackgroundStartPrivileges backgroundStartPrivileges);

    public abstract int startActivityWithScreenshot(
            int i, Intent intent, int i2, Bundle bundle, String str, int i3);

    public abstract AppTaskImpl startDreamActivity(int i, int i2, Intent intent);

    public abstract boolean startHomeActivity(int i, String str);

    public abstract void startRemoteActivityAsCaller(
            Intent intent, RemoteAppController.CallerInfo callerInfo, int i, Bundle bundle);

    public abstract void unregisterRemoteAppControllerCallbacks(
            RemoteAppModeService.AnonymousClass1 anonymousClass1);

    public abstract void writeActivitiesToProto(ProtoOutputStream protoOutputStream);

    public abstract void writeProcessesToProto(
            ProtoOutputStream protoOutputStream, String str, int i, boolean z);
}
