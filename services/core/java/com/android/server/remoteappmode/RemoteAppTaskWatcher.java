package com.android.server.remoteappmode;

import android.app.ActivityManager;
import android.app.ITaskStackListener;
import android.content.ComponentName;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.window.TaskSnapshot;

/* loaded from: classes3.dex */
public class RemoteAppTaskWatcher extends ITaskStackListener.Stub {
  public TaskChangeNotifier mCallback = null;
  public Handler mHandler;
  public boolean mNeedToNotifyRecentTaskListUpdated;
  public boolean mNeedToNotifyTaskDisplayChanged;
  public Looper mWatcherLooper;

  public void onActivityDismissingDockedTask() {}

  public void onActivityDismissingSplitTask(String str) {}

  public void onActivityForcedResizable(String str, int i, int i2) {}

  public void onActivityLaunchOnSecondaryDisplayFailed(
      ActivityManager.RunningTaskInfo runningTaskInfo, int i) {}

  public void onActivityLaunchOnSecondaryDisplayRerouted(
      ActivityManager.RunningTaskInfo runningTaskInfo, int i) {}

  public void onActivityPinned(String str, int i, int i2, int i3) {}

  public void onActivityRequestedOrientationChanged(int i, int i2) {}

  public void onActivityRestartAttempt(
      ActivityManager.RunningTaskInfo runningTaskInfo, boolean z, boolean z2, boolean z3) {}

  public void onActivityRotation(int i) {}

  public void onActivityUnpinned() {}

  public void onBackPressedOnTaskRoot(ActivityManager.RunningTaskInfo runningTaskInfo) {}

  public void onLockTaskModeChanged(int i) {}

  public void onOccludeChangeNotice(ComponentName componentName, boolean z) {}

  public void onRecentTaskListFrozenChanged(boolean z) {}

  public void onRecentTaskListUpdated() {}

  public void onTaskDescriptionChanged(ActivityManager.RunningTaskInfo runningTaskInfo) {}

  public void onTaskFocusChanged(int i, boolean z) {}

  public void onTaskMovedToBack(ActivityManager.RunningTaskInfo runningTaskInfo) {}

  public void onTaskMovedToFront(ActivityManager.RunningTaskInfo runningTaskInfo) {}

  public void onTaskProfileLocked(ActivityManager.RunningTaskInfo runningTaskInfo, int i) {}

  public void onTaskRemovalStarted(ActivityManager.RunningTaskInfo runningTaskInfo) {}

  public void onTaskRequestedOrientationChanged(int i, int i2) {}

  public void onTaskWindowingModeChanged(int i) {}

  public RemoteAppTaskWatcher(Looper looper) {
    android.util.Log.d("RemoteAppTaskWatcher", "RemoteAppTaskWatcher: Entered");
    this.mWatcherLooper = looper;
    this.mNeedToNotifyTaskDisplayChanged = false;
    this.mNeedToNotifyRecentTaskListUpdated = false;
    this.mHandler =
        new Handler(this.mWatcherLooper) { // from class:
          // com.android.server.remoteappmode.RemoteAppTaskWatcher.1
          @Override // android.os.Handler
          public void handleMessage(Message message) {
            super.handleMessage(message);
            android.util.Log.d(
                "RemoteAppTaskWatcher",
                " ****** RemoteAppTaskWatcher: Message Received "
                    + message.what
                    + " Task ID = "
                    + message.arg1);
            int i = message.what;
            if (i == 0) {
              if (RemoteAppTaskWatcher.this.mCallback != null) {
                RemoteAppTaskWatcher.this.mCallback.notifyTaskPlayed(message.arg1);
                return;
              }
              return;
            }
            if (i == 1 || i == 2) {
              return;
            }
            if (i == 3) {
              int i2 = message.arg1;
              if (RemoteAppTaskWatcher.this.mCallback != null) {
                RemoteAppTaskWatcher.this.mCallback.notifyTaskRemoved(i2);
                return;
              }
              return;
            }
            if (i == 4) {
              int i3 = message.arg1;
              int i4 = message.arg2;
              if (RemoteAppTaskWatcher.this.mCallback != null) {
                RemoteAppTaskWatcher.this.mCallback.notifyTaskMoved(i3, i4);
                return;
              }
              return;
            }
            if (i == 5) {
              if (RemoteAppTaskWatcher.this.mCallback != null) {
                RemoteAppTaskWatcher.this.mCallback.notifyTaskStackUpdated();
                return;
              }
              return;
            }
            android.util.Log.e("RemoteAppTaskWatcher", " ****** Error in received message ");
          }
        };
  }

  public void setNeedToNotifyTaskDisplayChanged(boolean z) {
    this.mNeedToNotifyTaskDisplayChanged = z;
  }

  public void setNeedToNotifyRecentTaskListUpdated(boolean z) {
    this.mNeedToNotifyRecentTaskListUpdated = z;
  }

  public void clearMessages() {
    android.util.Log.d("RemoteAppTaskWatcher", "****** TaskWatcher:clearMessages ");
    Handler handler = this.mHandler;
    if (handler != null) {
      handler.removeCallbacksAndMessages(null);
    }
  }

  public void onTaskStackChanged() {
    if (this.mNeedToNotifyRecentTaskListUpdated) {
      Message message = new Message();
      message.what = 5;
      this.mHandler.sendMessage(message);
    }
  }

  public void onTaskCreated(int i, ComponentName componentName) {
    Message message = new Message();
    message.what = 0;
    message.arg1 = i;
    this.mHandler.sendMessage(message);
  }

  public void onTaskRemoved(int i) {
    Message message = new Message();
    message.what = 3;
    message.arg1 = i;
    this.mHandler.sendMessage(message);
  }

  public void onTaskSnapshotChanged(int i, TaskSnapshot taskSnapshot) {
    if (this.mNeedToNotifyRecentTaskListUpdated) {
      Message message = new Message();
      message.what = 5;
      this.mHandler.sendMessage(message);
    }
  }

  public void onTaskDisplayChanged(int i, int i2) {
    if (this.mNeedToNotifyTaskDisplayChanged) {
      Message message = new Message();
      message.what = 4;
      message.arg1 = i;
      message.arg2 = i2;
      this.mHandler.sendMessage(message);
    }
  }

  public void registerTaskChangeNotifier(TaskChangeNotifier taskChangeNotifier) {
    android.util.Log.d("RemoteAppTaskWatcher", "registerTaskChangeNotifier");
    this.mCallback = taskChangeNotifier;
  }

  public void unregisterTaskChangeNotifier() {
    android.util.Log.d("RemoteAppTaskWatcher", "unregisterTaskChangeNotifier");
    this.mCallback = null;
  }
}
