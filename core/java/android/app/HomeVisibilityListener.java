package android.app;

import android.annotation.SystemApi;
import android.content.Context;
import android.p009os.Binder;
import com.android.internal.C4337R;
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
  IProcessObserver.Stub mObserver = new IProcessObserverStubC01931();

  public abstract void onHomeVisibilityChanged(boolean z);

  void init(Context context, Executor executor) {
    this.mActivityTaskManager = ActivityTaskManager.getInstance();
    this.mExecutor = executor;
    this.mMaxScanTasksForHomeVisibility =
        context.getResources().getInteger(C4337R.integer.config_maxScanTasksForHomeVisibility);
    this.mIsHomeActivityVisible = isHomeActivityVisible();
  }

  /* renamed from: android.app.HomeVisibilityListener$1 */
  class IProcessObserverStubC01931 extends IProcessObserver.Stub {
    IProcessObserverStubC01931() {}

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
            new FunctionalUtils
                .ThrowingRunnable() { // from class:
                                      // android.app.HomeVisibilityListener$1$$ExternalSyntheticLambda1
              @Override // com.android.internal.util.FunctionalUtils.ThrowingRunnable
              public final void runOrThrow() {
                HomeVisibilityListener.IProcessObserverStubC01931.this
                    .lambda$refreshHomeVisibility$1();
              }
            });
      }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$refreshHomeVisibility$1() throws Exception {
      HomeVisibilityListener.this.mExecutor.execute(
          new Runnable() { // from class:
                           // android.app.HomeVisibilityListener$1$$ExternalSyntheticLambda0
            @Override // java.lang.Runnable
            public final void run() {
              HomeVisibilityListener.IProcessObserverStubC01931.this
                  .lambda$refreshHomeVisibility$0();
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
    List<ActivityManager.RunningTaskInfo> tasksTopToBottom =
        this.mActivityTaskManager.getTasks(this.mMaxScanTasksForHomeVisibility, true, false, 0);
    if (tasksTopToBottom == null || tasksTopToBottom.isEmpty()) {
      return false;
    }
    int taskSize = tasksTopToBottom.size();
    for (int i = 0; i < taskSize; i++) {
      ActivityManager.RunningTaskInfo task = tasksTopToBottom.get(i);
      if (task.isVisible() && (task.baseIntent.getFlags() & 8388608) == 0) {
        return task.getActivityType() == 2;
      }
    }
    return false;
  }
}
