package android.app;

import android.app.ActivityManager;
import android.app.IActivityTaskManager;
import android.content.Context;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageManager;
import android.content.res.Configuration;
import android.content.res.Resources;
import android.graphics.Rect;
import android.os.Build;
import android.os.IBinder;
import android.os.Parcel;
import android.os.Parcelable;
import android.os.RemoteException;
import android.os.ServiceManager;
import android.util.DisplayMetrics;
import android.util.Singleton;
import android.view.RemoteAnimationDefinition;
import android.window.SplashScreenView;
import com.android.internal.R;
import com.samsung.android.graphics.imagefilter.ShaderAssembler;
import com.samsung.android.multiwindow.MultiWindowCoreState;
import java.util.List;

/* loaded from: classes.dex */
public class ActivityTaskManager {
    public static final int ADAPTIVE_APP_BY_DEFAULT_OVERRIDES = 16384;
    public static final int APP_CONTINUITY_PACKAGES = 1;
    public static final int COVER_LAUNCHER_PACKAGES = 2048;
    public static final int DEFAULT_MINIMAL_SPLIT_SCREEN_DISPLAY_SIZE_DP = 440;
    public static final int DISPLAY_COMPAT_PACKAGES = 4;
    public static final int DISPLAY_CUTOUT_PACKAGES = 128;
    public static final int EMBED_ACTIVITY_PACKAGES = 1024;
    public static final String EXTRA_IGNORE_TARGET_SECURITY = "android.app.extra.EXTRA_IGNORE_TARGET_SECURITY";
    public static final String EXTRA_OPTIONS = "android.app.extra.OPTIONS";
    public static final int INVALID_STACK_ID = -1;
    public static final int INVALID_TASK_ID = -1;
    public static final int INVALID_WINDOWING_MODE = -1;
    public static final int MAX_ASPECT_RATIO_PACKAGES = 2;
    public static final int MIN_ASPECT_RATIO_PACKAGES = 8;
    public static final int NIGHT_MODE_PRIORITY_APPLIED_PACKAGES = 4096;
    public static final int NIGHT_MODE_SHOW_DIALOG_PACKAGES = 8192;
    public static final int ORIENTATION_POLICY_PACKAGES = 64;
    public static final int RESET_ALL_PACKAGES_SETTINGS = 23805;
    public static final int RESIZE_MODE_FORCED = 2;
    public static final int RESIZE_MODE_PRESERVE_WINDOW = 1;
    public static final int RESIZE_MODE_SYSTEM = 0;
    public static final int RESIZE_MODE_USER = 1;
    public static final int RESIZE_MODE_USER_FORCED = 3;
    public static final int SPLIT_ACTIVITY_PACKAGES = 32;
    public static final int SPLIT_SCREEN_CREATE_MODE_BOTTOM = 5;
    public static final int SPLIT_SCREEN_CREATE_MODE_LEFT = 2;
    public static final int SPLIT_SCREEN_CREATE_MODE_RIGHT = 4;
    public static final int SPLIT_SCREEN_CREATE_MODE_TOP = 3;
    public static final int SPLIT_SCREEN_CREATE_MODE_UNDEFINED = -1;
    public static final int SUPPORTS_FLEX_PANEL_PACKAGES = 16;
    private static int sMaxRecentTasks = -1;
    private static final Singleton<ActivityTaskManager> sInstance = new Singleton<ActivityTaskManager>() { // from class: android.app.ActivityTaskManager.1
        /* JADX INFO: Access modifiers changed from: protected */
        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.util.Singleton
        public ActivityTaskManager create() {
            return new ActivityTaskManager();
        }
    };
    private static final Singleton<IActivityTaskManager> IActivityTaskManagerSingleton = new Singleton<IActivityTaskManager>() { // from class: android.app.ActivityTaskManager.2
        /* JADX INFO: Access modifiers changed from: protected */
        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.util.Singleton
        public IActivityTaskManager create() {
            return IActivityTaskManager.Stub.asInterface(ServiceManager.getService(Context.ACTIVITY_TASK_SERVICE));
        }
    };

    public @interface OrientationControlPolicy {
        public static final int DISABLED_FROM_ASPECT_RATIO = 0;
        public static final int DISABLED_FROM_ASPECT_RATIO_BY_SYSTEM = 128;
        public static final int DISABLED_FROM_FULL_SCREEN = 32;
        public static final int ENABLED_AS_ASPECT_RATIO = 31;
        public static final int ENABLED_AS_ASPECT_RATIO_BY_SYSTEM = 64;
        public static final int ENABLED_AS_FULL_SCREEN = 7;
        public static final int LEGACY_ASPECT_RATIO_FLAG = 24;
        public static final int LEGACY_FULL_SCREEN_FLAG = 7;
    }

    public @interface SplitCreateMode {
    }

    public static String splitCreateModeToString(int i) {
        if (i == -1) {
            return "SPLIT_SCREEN_CREATE_MODE_UNDEFINED";
        }
        if (i == 2) {
            return "SPLIT_SCREEN_CREATE_MODE_LEFT";
        }
        if (i == 3) {
            return "SPLIT_SCREEN_CREATE_MODE_TOP";
        }
        if (i == 4) {
            return "SPLIT_SCREEN_CREATE_MODE_RIGHT";
        }
        if (i == 5) {
            return "SPLIT_SCREEN_CREATE_MODE_BOTTOM";
        }
        return Integer.toString(i);
    }

    private ActivityTaskManager() {
    }

    public static ActivityTaskManager getInstance() {
        return sInstance.get();
    }

    public static IActivityTaskManager getService() {
        return IActivityTaskManagerSingleton.get();
    }

    public void removeRootTasksInWindowingModes(int[] iArr) {
        try {
            getService().removeRootTasksInWindowingModes(iArr);
        } catch (RemoteException e) {
            throw e.rethrowFromSystemServer();
        }
    }

    public void removeRootTasksWithActivityTypes(int[] iArr) {
        try {
            getService().removeRootTasksWithActivityTypes(iArr);
        } catch (RemoteException e) {
            throw e.rethrowFromSystemServer();
        }
    }

    public void removeAllVisibleRecentTasks() {
        try {
            getService().removeAllVisibleRecentTasks();
        } catch (RemoteException e) {
            throw e.rethrowFromSystemServer();
        }
    }

    public static int getMaxRecentTasksStatic() {
        int i = sMaxRecentTasks;
        if (i < 0) {
            i = ActivityManager.isLowRamDeviceStatic() ? 36 : 48;
            sMaxRecentTasks = i;
        }
        return i;
    }

    public void onSplashScreenViewCopyFinished(int i, SplashScreenView.SplashScreenViewParcelable splashScreenViewParcelable) {
        try {
            getService().onSplashScreenViewCopyFinished(i, splashScreenViewParcelable);
        } catch (RemoteException e) {
            throw e.rethrowFromSystemServer();
        }
    }

    public static int getDefaultAppRecentsLimitStatic() {
        return getMaxRecentTasksStatic() / 6;
    }

    public static int getMaxAppRecentsLimitStatic() {
        return getMaxRecentTasksStatic() / 2;
    }

    public static boolean deviceSupportsMultiWindow(Context context) {
        return supportsMultiWindow(context, true);
    }

    public static boolean supportsMultiWindow(Context context) {
        return supportsMultiWindow(context, false);
    }

    private static boolean supportsMultiWindow(Context context, boolean z) {
        boolean hasSystemFeature = context.getPackageManager().hasSystemFeature(PackageManager.FEATURE_WATCH);
        if ((!ActivityManager.isLowRamDeviceStatic() || hasSystemFeature) && Resources.getSystem().getBoolean(R.bool.config_supportsMultiWindow)) {
            return z || MultiWindowCoreState.MW_ENABLED;
        }
        return false;
    }

    public static boolean supportsSplitScreenMultiWindow(Context context) {
        DisplayMetrics displayMetrics = new DisplayMetrics();
        context.getDisplay().getRealMetrics(displayMetrics);
        return Math.max((int) (((float) displayMetrics.widthPixels) / displayMetrics.density), (int) (((float) displayMetrics.heightPixels) / displayMetrics.density)) >= 440 && supportsMultiWindow(context) && Resources.getSystem().getBoolean(R.bool.config_supportsSplitScreenMultiWindow);
    }

    public void startSystemLockTaskMode(int i) {
        try {
            getService().startSystemLockTaskMode(i);
        } catch (RemoteException e) {
            throw e.rethrowFromSystemServer();
        }
    }

    public void stopSystemLockTaskMode() {
        try {
            getService().stopSystemLockTaskMode();
        } catch (RemoteException e) {
            throw e.rethrowFromSystemServer();
        }
    }

    public void moveTaskToRootTask(int i, int i2, boolean z) {
        try {
            getService().moveTaskToRootTask(i, i2, z);
        } catch (RemoteException e) {
            throw e.rethrowFromSystemServer();
        }
    }

    public void resizeTask(int i, Rect rect) {
        try {
            getService().resizeTask(i, rect, 0);
        } catch (RemoteException e) {
            throw e.rethrowFromSystemServer();
        }
    }

    public void clearLaunchParamsForPackages(List<String> list) {
        try {
            getService().clearLaunchParamsForPackages(list);
        } catch (RemoteException e) {
            e.rethrowFromSystemServer();
        }
    }

    public static boolean currentUiModeSupportsErrorDialogs(Configuration configuration) {
        int i = configuration.uiMode & 15;
        if (i != 3) {
            return ((i == 6 && Build.IS_USER) || i == 4 || i == 7) ? false : true;
        }
        return false;
    }

    public static boolean currentUiModeSupportsErrorDialogs(Context context) {
        return currentUiModeSupportsErrorDialogs(context.getResources().getConfiguration());
    }

    public static int getMaxNumPictureInPictureActions(Context context) {
        return context.getResources().getInteger(R.integer.config_pictureInPictureMaxNumberOfActions);
    }

    public List<ActivityManager.RunningTaskInfo> getTasks(int i) {
        return getTasks(i, false, false, -1);
    }

    public List<ActivityManager.RunningTaskInfo> getTasks(int i, boolean z) {
        return getTasks(i, z, false, -1);
    }

    public List<ActivityManager.RunningTaskInfo> getTasks(int i, boolean z, boolean z2) {
        return getTasks(i, z, z2, -1);
    }

    public List<ActivityManager.RunningTaskInfo> getTasks(int i, boolean z, boolean z2, int i2) {
        try {
            return getService().getTasks(i, z, z2, i2);
        } catch (RemoteException e) {
            throw e.rethrowFromSystemServer();
        }
    }

    public List<ActivityManager.RecentTaskInfo> getRecentTasks(int i, int i2, int i3) {
        try {
            return getService().getRecentTasks(i, i2, i3).getList();
        } catch (RemoteException e) {
            throw e.rethrowFromSystemServer();
        }
    }

    public void registerTaskStackListener(TaskStackListener taskStackListener) {
        try {
            getService().registerTaskStackListener(taskStackListener);
        } catch (RemoteException e) {
            throw e.rethrowFromSystemServer();
        }
    }

    public void unregisterTaskStackListener(TaskStackListener taskStackListener) {
        try {
            getService().unregisterTaskStackListener(taskStackListener);
        } catch (RemoteException e) {
            throw e.rethrowFromSystemServer();
        }
    }

    public Rect getTaskBounds(int i) {
        try {
            return getService().getTaskBounds(i);
        } catch (RemoteException e) {
            throw e.rethrowFromSystemServer();
        }
    }

    public void registerRemoteAnimationsForDisplay(int i, RemoteAnimationDefinition remoteAnimationDefinition) {
        try {
            getService().registerRemoteAnimationsForDisplay(i, remoteAnimationDefinition);
        } catch (RemoteException e) {
            throw e.rethrowFromSystemServer();
        }
    }

    public boolean isInLockTaskMode() {
        try {
            return getService().isInLockTaskMode();
        } catch (RemoteException e) {
            throw e.rethrowFromSystemServer();
        }
    }

    public boolean removeTask(int i) {
        try {
            return getService().removeTask(i);
        } catch (RemoteException e) {
            throw e.rethrowFromSystemServer();
        }
    }

    public boolean canBeUniversalResizeable(ApplicationInfo applicationInfo) {
        try {
            return getService().canBeUniversalResizeable(applicationInfo);
        } catch (RemoteException e) {
            throw e.rethrowFromSystemServer();
        }
    }

    public void detachNavigationBarFromApp(IBinder iBinder) {
        try {
            getService().detachNavigationBarFromApp(iBinder);
        } catch (RemoteException e) {
            throw e.rethrowFromSystemServer();
        }
    }

    public void updateLockTaskPackages(Context context, String[] strArr) {
        try {
            getService().updateLockTaskPackages(context.getUserId(), strArr);
        } catch (RemoteException e) {
            throw e.rethrowFromSystemServer();
        }
    }

    public static class RootTaskInfo extends TaskInfo implements Parcelable {
        public static final Parcelable.Creator<RootTaskInfo> CREATOR = new Parcelable.Creator<RootTaskInfo>() { // from class: android.app.ActivityTaskManager.RootTaskInfo.1
            /* JADX WARN: Can't rename method to resolve collision */
            @Override // android.os.Parcelable.Creator
            public RootTaskInfo createFromParcel(Parcel parcel) {
                return new RootTaskInfo(parcel);
            }

            /* JADX WARN: Can't rename method to resolve collision */
            @Override // android.os.Parcelable.Creator
            public RootTaskInfo[] newArray(int i) {
                return new RootTaskInfo[i];
            }
        };
        public Rect bounds;
        public Rect[] childTaskBounds;
        public int[] childTaskIds;
        public String[] childTaskNames;
        public int[] childTaskUserIds;
        public int position;
        public boolean visible;

        @Override // android.os.Parcelable
        public int describeContents() {
            return 0;
        }

        @Override // android.os.Parcelable
        public void writeToParcel(Parcel parcel, int i) {
            parcel.writeTypedObject(this.bounds, i);
            parcel.writeIntArray(this.childTaskIds);
            parcel.writeStringArray(this.childTaskNames);
            parcel.writeTypedArray(this.childTaskBounds, i);
            parcel.writeIntArray(this.childTaskUserIds);
            parcel.writeInt(this.visible ? 1 : 0);
            parcel.writeInt(this.position);
            super.writeTaskToParcel(parcel, i);
        }

        void readFromParcel(Parcel parcel) {
            this.bounds = (Rect) parcel.readTypedObject(Rect.CREATOR);
            this.childTaskIds = parcel.createIntArray();
            this.childTaskNames = parcel.createStringArray();
            this.childTaskBounds = (Rect[]) parcel.createTypedArray(Rect.CREATOR);
            this.childTaskUserIds = parcel.createIntArray();
            this.visible = parcel.readInt() > 0;
            this.position = parcel.readInt();
            super.readTaskFromParcel(parcel);
        }

        public RootTaskInfo() {
            this.bounds = new Rect();
        }

        private RootTaskInfo(Parcel parcel) {
            this.bounds = new Rect();
            readFromParcel(parcel);
        }

        @Override // android.app.TaskInfo
        public String toString() {
            StringBuilder sb = new StringBuilder(256);
            sb.append("RootTask id=");
            sb.append(this.taskId);
            sb.append(" bounds=");
            sb.append(this.bounds.toShortString());
            sb.append(" displayId=");
            sb.append(this.displayId);
            sb.append(" userId=");
            sb.append(this.userId);
            sb.append("\n configuration=");
            sb.append(this.configuration);
            sb.append(ShaderAssembler.NEWLINE);
            for (int i = 0; i < this.childTaskIds.length; i++) {
                sb.append("  taskId=");
                sb.append(this.childTaskIds[i]);
                sb.append(": ");
                sb.append(this.childTaskNames[i]);
                if (this.childTaskBounds != null) {
                    sb.append(" bounds=");
                    sb.append(this.childTaskBounds[i].toShortString());
                }
                sb.append(" userId=");
                sb.append(this.childTaskUserIds[i]);
                sb.append(" visible=");
                sb.append(this.visible);
                if (this.topActivity != null) {
                    sb.append(" topActivity=");
                    sb.append(this.topActivity);
                }
                sb.append(ShaderAssembler.NEWLINE);
            }
            return sb.toString();
        }
    }
}
