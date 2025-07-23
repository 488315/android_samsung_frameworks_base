package android.app;

import android.annotation.SystemApi;
import android.app.ActivityManager;
import android.app.HomeVisibilityListener;
import android.app.IProcessObserver;
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
    private static final String TAG = "HomeVisibilityListener";
    private static final boolean DBG = Log.isLoggable(TAG, 3);

    public abstract void onHomeVisibilityChanged(boolean z);

    void init(Context context, Executor executor) {
        this.mActivityTaskManager = ActivityTaskManager.getInstance();
        this.mExecutor = executor;
        this.mMaxScanTasksForHomeVisibility = context.getResources().getInteger(R.integer.config_maxScanTasksForHomeVisibility);
        this.mIsHomeActivityVisible = isHomeActivityVisible();
    }

    /* renamed from: android.app.HomeVisibilityListener$1, reason: invalid class name */
    class AnonymousClass1 extends IProcessObserver.Stub {
        @Override // android.app.IProcessObserver
        public void onForegroundServicesChanged(int i, int i2, int i3) {
        }

        @Override // android.app.IProcessObserver
        public void onProcessStarted(int i, int i2, int i3, String str, String str2) {
        }

        AnonymousClass1() {
        }

        @Override // android.app.IProcessObserver
        public void onForegroundActivitiesChanged(int i, int i2, boolean z) {
            refreshHomeVisibility();
        }

        @Override // android.app.IProcessObserver
        public void onProcessDied(int i, int i2) {
            refreshHomeVisibility();
        }

        private void refreshHomeVisibility() {
            boolean isHomeActivityVisible = HomeVisibilityListener.this.isHomeActivityVisible();
            if (HomeVisibilityListener.this.mIsHomeActivityVisible != isHomeActivityVisible) {
                HomeVisibilityListener.this.mIsHomeActivityVisible = isHomeActivityVisible;
                Binder.withCleanCallingIdentity(new FunctionalUtils.ThrowingRunnable() { // from class: android.app.HomeVisibilityListener$1$$ExternalSyntheticLambda0
                    @Override // com.android.internal.util.FunctionalUtils.ThrowingRunnable
                    public final void runOrThrow() {
                        HomeVisibilityListener.AnonymousClass1.this.lambda$refreshHomeVisibility$1();
                    }
                });
            }
        }

        /* JADX INFO: Access modifiers changed from: private */
        public /* synthetic */ void lambda$refreshHomeVisibility$1() throws Exception {
            HomeVisibilityListener.this.mExecutor.execute(new Runnable() { // from class: android.app.HomeVisibilityListener$1$$ExternalSyntheticLambda1
                @Override // java.lang.Runnable
                public final void run() {
                    HomeVisibilityListener.AnonymousClass1.this.lambda$refreshHomeVisibility$0();
                }
            });
        }

        /* JADX INFO: Access modifiers changed from: private */
        public /* synthetic */ void lambda$refreshHomeVisibility$0() {
            HomeVisibilityListener homeVisibilityListener = HomeVisibilityListener.this;
            homeVisibilityListener.onHomeVisibilityChanged(homeVisibilityListener.mIsHomeActivityVisible);
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public boolean isHomeActivityVisible() {
        List<ActivityManager.RunningTaskInfo> tasks = this.mActivityTaskManager.getTasks(this.mMaxScanTasksForHomeVisibility, true, false, 0);
        if (tasks != null && !tasks.isEmpty()) {
            int size = tasks.size();
            for (int i = 0; i < size; i++) {
                ActivityManager.RunningTaskInfo runningTaskInfo = tasks.get(i);
                if (DBG) {
                    Log.d(TAG, "Task#" + i + ": activity=" + runningTaskInfo.topActivity + ", visible=" + runningTaskInfo.isVisible() + ", flg=" + Integer.toHexString(runningTaskInfo.baseIntent.getFlags()) + ", type=" + runningTaskInfo.getActivityType());
                }
                if (runningTaskInfo.isVisible() && runningTaskInfo.getActivityType() == 2) {
                    return true;
                }
            }
        }
        return false;
    }
}
