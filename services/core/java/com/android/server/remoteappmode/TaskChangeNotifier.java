package com.android.server.remoteappmode;

import android.app.ActivityManager;
import android.app.ActivityTaskManager;
import android.app.TaskInfo;
import android.content.Context;
import android.os.Binder;
import android.os.IBinder;
import android.os.RemoteException;
import android.util.ArrayMap;
import com.android.server.ServiceThread;
import com.samsung.android.remoteappmode.ITaskChangeListener;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Objects;

/* loaded from: classes3.dex */
public class TaskChangeNotifier {
  public Context mContext;
  public RemoteAppTaskWatcher mTaskWatcher;
  public ServiceThread mTaskWatcherThread = null;
  public final Object lockObject = new Object();
  public final Map mTaskChangeListeners = new ArrayMap();

  public TaskChangeNotifier(Context context) {
    this.mContext = context;
  }

  public void initTaskWatcherThread(boolean z) {
    synchronized (this.lockObject) {
      if (this.mTaskWatcherThread == null) {
        ServiceThread serviceThread = new ServiceThread("remoteapp_taskwatcher", -2, false);
        this.mTaskWatcherThread = serviceThread;
        serviceThread.start();
        Log.m83i("TaskChangeNotifier", "mTaskWatcherThread start : " + this.mTaskWatcherThread);
      }
    }
  }

  public final void registerWatcherToActivityManager() {
    try {
      Log.m83i("TaskChangeNotifier", "registerWatcherToActivityManager : " + this.mTaskWatcher);
      ActivityTaskManager.getService().registerTaskStackListener(this.mTaskWatcher);
    } catch (RemoteException e) {
      Log.m82e("TaskChangeNotifier", " registerTaskWatcher: RemoteException " + e.getMessage());
    } catch (SecurityException e2) {
      Log.m82e("TaskChangeNotifier", " registerTaskWatcher: SecurityException " + e2.getMessage());
    }
  }

  public final void unregisterWatcherFromActivityManager() {
    try {
      Log.m83i("TaskChangeNotifier", "unregisterWatcherFromActivityManager : " + this.mTaskWatcher);
      ActivityTaskManager.getService().unregisterTaskStackListener(this.mTaskWatcher);
    } catch (RemoteException e) {
      Log.m82e("TaskChangeNotifier", " unregisterTaskWatcher: RemoteException " + e.getMessage());
    } catch (SecurityException e2) {
      Log.m82e(
          "TaskChangeNotifier", " unregisterTaskWatcher: SecurityException " + e2.getMessage());
    }
  }

  public boolean registerTaskChangeListener(
      ITaskChangeListener iTaskChangeListener, String str, boolean z) {
    boolean linkListenerToDeath;
    synchronized (this.lockObject) {
      unregisterWatcherInternal();
      deinitTaskWatcherThread();
      initTaskWatcherThread(z);
      RemoteAppTaskWatcher remoteAppTaskWatcher =
          new RemoteAppTaskWatcher(this.mTaskWatcherThread.getLooper());
      this.mTaskWatcher = remoteAppTaskWatcher;
      if (z) {
        remoteAppTaskWatcher.setNeedToNotifyTaskDisplayChanged(true);
        this.mTaskWatcher.setNeedToNotifyRecentTaskListUpdated(true);
      }
      registerWatcherToActivityManager();
      this.mTaskWatcher.registerTaskChangeNotifier(this);
      linkListenerToDeath = linkListenerToDeath(iTaskChangeListener, str);
    }
    return linkListenerToDeath;
  }

  public final void unregisterWatcherInternal() {
    if (this.mTaskWatcher != null) {
      unregisterWatcherFromActivityManager();
      this.mTaskWatcher.unregisterTaskChangeNotifier();
      this.mTaskWatcher.clearMessages();
      this.mTaskWatcher = null;
      Log.m83i("TaskChangeNotifier", "unregisterWatcherInternal : mTaskWatcher = null");
    }
  }

  public final boolean linkListenerToDeath(ITaskChangeListener iTaskChangeListener, String str) {
    synchronized (this.mTaskChangeListeners) {
      IBinder asBinder = iTaskChangeListener.asBinder();
      try {
        if (str.length() > 100) {
          str = str.substring(0, 100);
        }
        TaskChangeListenerInfo taskChangeListenerInfo =
            new TaskChangeListenerInfo(
                iTaskChangeListener, str, Binder.getCallingPid(), Binder.getCallingUid());
        asBinder.linkToDeath(taskChangeListenerInfo, 0);
        this.mTaskChangeListeners.put(asBinder, taskChangeListenerInfo);
      } catch (RemoteException unused) {
        return false;
      }
    }
    return true;
  }

  public void deinitTaskWatcherThread() {
    synchronized (this.lockObject) {
      try {
        ServiceThread serviceThread = this.mTaskWatcherThread;
        if (serviceThread != null) {
          serviceThread.interrupt();
          this.mTaskWatcherThread.quitSafely();
        }
      } catch (SecurityException e) {
        Log.m82e(
            "TaskChangeNotifier",
            " unregisterTaskChangeListener: SecurityException " + e.getMessage());
      }
      this.mTaskWatcherThread = null;
      Log.m83i("TaskChangeNotifier", " deinitTaskWatcherThread : mTaskWatcherThread = null");
    }
  }

  public boolean unregisterTaskChangeListener(ITaskChangeListener iTaskChangeListener) {
    boolean unlinkListenerToDeath;
    synchronized (this.lockObject) {
      unregisterWatcherInternal();
      deinitTaskWatcherThread();
      unlinkListenerToDeath = unlinkListenerToDeath(iTaskChangeListener);
    }
    return unlinkListenerToDeath;
  }

  public final boolean unlinkListenerToDeath(ITaskChangeListener iTaskChangeListener) {
    synchronized (this.mTaskChangeListeners) {
      TaskChangeListenerInfo taskChangeListenerInfo =
          (TaskChangeListenerInfo) this.mTaskChangeListeners.remove(iTaskChangeListener.asBinder());
      if (taskChangeListenerInfo == null) {
        return false;
      }
      taskChangeListenerInfo.listener.asBinder().unlinkToDeath(taskChangeListenerInfo, 0);
      return true;
    }
  }

  public void notifyTaskPlayed(int i) {
    ArrayList<TaskChangeListenerInfo> arrayList;
    synchronized (this.mTaskChangeListeners) {
      arrayList = new ArrayList(this.mTaskChangeListeners.values());
    }
    for (TaskChangeListenerInfo taskChangeListenerInfo : arrayList) {
      try {
        TaskInfo taskInfo = getTaskInfo(this.mContext, i);
        taskChangeListenerInfo.listener.onTaskPlayed(i, taskInfo != null ? taskInfo.displayId : -1);
      } catch (RemoteException e) {
        e.printStackTrace();
      }
    }
  }

  public void notifyTaskRemoved(int i) {
    ArrayList arrayList;
    synchronized (this.mTaskChangeListeners) {
      arrayList = new ArrayList(this.mTaskChangeListeners.values());
    }
    Iterator it = arrayList.iterator();
    while (it.hasNext()) {
      try {
        ((TaskChangeListenerInfo) it.next()).listener.onTaskRemoved(i);
      } catch (RemoteException e) {
        e.printStackTrace();
      }
    }
  }

  public void notifyTaskMoved(int i, int i2) {
    ArrayList arrayList;
    synchronized (this.mTaskChangeListeners) {
      arrayList = new ArrayList(this.mTaskChangeListeners.values());
    }
    Iterator it = arrayList.iterator();
    while (it.hasNext()) {
      try {
        ((TaskChangeListenerInfo) it.next()).listener.onTaskDisplayChanged(i, i2);
      } catch (RemoteException e) {
        e.printStackTrace();
      }
    }
  }

  public void notifyTaskStackUpdated() {
    ArrayList arrayList;
    synchronized (this.mTaskChangeListeners) {
      arrayList = new ArrayList(this.mTaskChangeListeners.values());
    }
    Iterator it = arrayList.iterator();
    while (it.hasNext()) {
      try {
        ((TaskChangeListenerInfo) it.next()).listener.onRecentTaskListUpdated();
      } catch (RemoteException e) {
        e.printStackTrace();
      }
    }
  }

  public void releaseAllListeners() {
    Log.m83i("TaskChangeNotifier", "releaseAllListeners");
    synchronized (this.mTaskChangeListeners) {
      for (IBinder iBinder : this.mTaskChangeListeners.keySet()) {
        try {
          TaskChangeListenerInfo taskChangeListenerInfo =
              (TaskChangeListenerInfo) this.mTaskChangeListeners.get(iBinder);
          Objects.requireNonNull(taskChangeListenerInfo);
          iBinder.unlinkToDeath(taskChangeListenerInfo, 0);
        } catch (NullPointerException e) {
          e.printStackTrace();
        }
      }
      this.mTaskChangeListeners.clear();
    }
    synchronized (this.lockObject) {
      unregisterWatcherInternal();
      deinitTaskWatcherThread();
    }
  }

  public static TaskInfo getTaskInfo(Context context, int i) {
    List<ActivityManager.RunningTaskInfo> runningTasks =
        ((ActivityManager) context.getSystemService("activity")).getRunningTasks(Integer.MAX_VALUE);
    if (runningTasks == null) {
      return null;
    }
    for (ActivityManager.RunningTaskInfo runningTaskInfo : runningTasks) {
      if (runningTaskInfo.taskId == i) {
        return runningTaskInfo;
      }
    }
    return null;
  }

  public class TaskChangeListenerInfo extends ListenerInfo {
    public final ITaskChangeListener listener;

    public TaskChangeListenerInfo(
        ITaskChangeListener iTaskChangeListener, String str, int i, int i2) {
      super(str, i, i2);
      this.listener = iTaskChangeListener;
    }

    @Override // com.android.server.remoteappmode.ListenerInfo, android.os.IBinder.DeathRecipient
    public void binderDied() {
      boolean isEmpty;
      super.binderDied();
      synchronized (TaskChangeNotifier.this.mTaskChangeListeners) {
        TaskChangeNotifier.this.mTaskChangeListeners.remove(this.listener.asBinder());
        isEmpty = TaskChangeNotifier.this.mTaskChangeListeners.isEmpty();
      }
      if (isEmpty) {
        synchronized (TaskChangeNotifier.this.lockObject) {
          TaskChangeNotifier.this.unregisterWatcherInternal();
          TaskChangeNotifier.this.deinitTaskWatcherThread();
        }
      }
      this.listener.asBinder().unlinkToDeath(this, 0);
    }
  }
}
