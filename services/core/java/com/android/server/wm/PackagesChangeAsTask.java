package com.android.server.wm;

import android.content.ComponentName;
import java.io.PrintWriter;
import java.util.function.Consumer;

/* compiled from: PackagesChange.java */
/* loaded from: classes3.dex */
public abstract class PackagesChangeAsTask extends PackagesChange {
  public Runnable mUpdateValueToTaskImmediately;

  public abstract void onDumpInTask(PrintWriter printWriter, String str, Task task);

  public abstract void onUpdateValueToTask(Task task, String str, boolean z);

  public PackagesChangeAsTask(ActivityTaskManagerService activityTaskManagerService) {
    super(activityTaskManagerService);
    this.mUpdateValueToTaskImmediately =
        new Runnable() { // from class:
                         // com.android.server.wm.PackagesChangeAsTask$$ExternalSyntheticLambda1
          @Override // java.lang.Runnable
          public final void run() {
            PackagesChangeAsTask.this.updateValueToTaskImmediately();
          }
        };
    PackagesChange.addPackagesChangeAsTask(this);
  }

  public final void updateValueToTask(boolean z) {
    if (z) {
      updateValueToTaskImmediately();
    } else {
      this.mAtmService.f1734mH.removeCallbacks(this.mUpdateValueToTaskImmediately);
      this.mAtmService.f1734mH.postDelayed(this.mUpdateValueToTaskImmediately, 500L);
    }
  }

  public final void updateValueToTaskImmediately() {
    WindowManagerGlobalLock windowManagerGlobalLock = this.mAtmService.mGlobalLock;
    WindowManagerService.boostPriorityForLockedSection();
    synchronized (windowManagerGlobalLock) {
      try {
        this.mAtmService.mRootWindowContainer.forAllTasks(
            new Consumer() { // from class:
                             // com.android.server.wm.PackagesChangeAsTask$$ExternalSyntheticLambda0
              @Override // java.util.function.Consumer
              public final void accept(Object obj) {
                PackagesChangeAsTask.this.lambda$updateValueToTaskImmediately$0((Task) obj);
              }
            });
      } catch (Throwable th) {
        WindowManagerService.resetPriorityAfterLockedSection();
        throw th;
      }
    }
    WindowManagerService.resetPriorityAfterLockedSection();
  }

  /* JADX INFO: Access modifiers changed from: private */
  public /* synthetic */ void lambda$updateValueToTaskImmediately$0(Task task) {
    updateValueToTask(task, true);
  }

  public final void updateValueToTask(Task task, boolean z) {
    ComponentName componentName = task.realActivity;
    onUpdateValueToTask(task, componentName != null ? componentName.getPackageName() : null, z);
  }
}
