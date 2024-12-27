package android.app;

import android.annotation.SystemApi;
import android.content.Context;
import android.os.Binder;
import android.util.Log;

import com.android.internal.R;
import com.android.internal.util.FunctionalUtils;

import java.util.List;
import java.util.concurrent.Executor;

@SystemApi(client = SystemApi.Client.MODULE_LIBRARIES)
/* loaded from: classes.dex */
public abstract class HomeVisibilityListener {
    private ActivityTaskManager mActivityTaskManager;
    private Executor mExecutor;
    boolean mIsHomeActivityVisible;
    private int mMaxScanTasksForHomeVisibility;
    IProcessObserver.Stub mObserver = new AnonymousClass1();
    private static final String TAG = HomeVisibilityListener.class.getSimpleName();
    private static final boolean DBG = Log.isLoggable(TAG, 3);

    public abstract void onHomeVisibilityChanged(boolean z);

    void init(Context context, Executor executor) {
        this.mActivityTaskManager = ActivityTaskManager.getInstance();
        this.mExecutor = executor;
        this.mMaxScanTasksForHomeVisibility =
                context.getResources().getInteger(R.integer.config_maxScanTasksForHomeVisibility);
        this.mIsHomeActivityVisible = isHomeActivityVisible();
    }

    /* renamed from: android.app.HomeVisibilityListener$1, reason: invalid class name */
    class AnonymousClass1 extends IProcessObserver.Stub {
        AnonymousClass1() {}

        @Override // android.app.IProcessObserver
        public void onProcessStarted(
                int pid, int processUid, int packageUid, String packageName, String processName) {}

        @Override // android.app.IProcessObserver
        public void onForegroundActivitiesChanged(int pid, int uid, boolean fg) {
            refreshHomeVisibility();
        }

        @Override // android.app.IProcessObserver
        public void onForegroundServicesChanged(int pid, int uid, int fgServiceTypes) {}

        @Override // android.app.IProcessObserver
        public void onProcessDied(int pid, int uid) {
            refreshHomeVisibility();
        }

        private void refreshHomeVisibility() {
            boolean isHomeActivityVisible = HomeVisibilityListener.this.isHomeActivityVisible();
            if (HomeVisibilityListener.this.mIsHomeActivityVisible != isHomeActivityVisible) {
                HomeVisibilityListener.this.mIsHomeActivityVisible = isHomeActivityVisible;
                Binder.withCleanCallingIdentity(
                        new FunctionalUtils.ThrowingRunnable() { // from class:
                            // android.app.HomeVisibilityListener$1$$ExternalSyntheticLambda0
                            @Override // com.android.internal.util.FunctionalUtils.ThrowingRunnable
                            public final void runOrThrow() {
                                HomeVisibilityListener.AnonymousClass1.this
                                        .lambda$refreshHomeVisibility$1();
                            }
                        });
            }
        }

        /* JADX INFO: Access modifiers changed from: private */
        public /* synthetic */ void lambda$refreshHomeVisibility$1() throws Exception {
            HomeVisibilityListener.this.mExecutor.execute(
                    new Runnable() { // from class:
                        // android.app.HomeVisibilityListener$1$$ExternalSyntheticLambda1
                        @Override // java.lang.Runnable
                        public final void run() {
                            HomeVisibilityListener.AnonymousClass1.this
                                    .lambda$refreshHomeVisibility$0();
                        }
                    });
        }

        /* JADX INFO: Access modifiers changed from: private */
        public /* synthetic */ void lambda$refreshHomeVisibility$0() {
            HomeVisibilityListener.this.onHomeVisibilityChanged(
                    HomeVisibilityListener.this.mIsHomeActivityVisible);
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public boolean isHomeActivityVisible() {
        List<ActivityManager.RunningTaskInfo> tasksTopToBottom =
                this.mActivityTaskManager.getTasks(
                        this.mMaxScanTasksForHomeVisibility, true, false, 0);
        if (tasksTopToBottom == null || tasksTopToBottom.isEmpty()) {
            return false;
        }
        int taskSize = tasksTopToBottom.size();
        for (int i = 0; i < taskSize; i++) {
            ActivityManager.RunningTaskInfo task = tasksTopToBottom.get(i);
            if (DBG) {
                Log.d(
                        TAG,
                        "Task#"
                                + i
                                + ": activity="
                                + task.topActivity
                                + ", visible="
                                + task.isVisible()
                                + ", flg="
                                + Integer.toHexString(task.baseIntent.getFlags())
                                + ", type="
                                + task.getActivityType());
            }
            if (task.isVisible() && task.getActivityType() == 2) {
                return true;
            }
        }
        return false;
    }
}
