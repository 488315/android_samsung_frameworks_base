package com.android.server.remoteappmode;

import android.app.ActivityManager;
import android.app.ActivityTaskManager;
import android.app.TaskInfo;
import android.content.Context;
import android.os.Binder;
import android.os.Handler;
import android.os.IBinder;
import android.os.Looper;
import android.os.Message;
import android.os.RemoteException;
import android.util.ArrayMap;

import com.android.server.ServiceThread;
import com.android.server.accessibility.GestureWakeup$$ExternalSyntheticOutline0;

import com.samsung.android.remoteappmode.ITaskChangeListener;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

/* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
/* loaded from: classes2.dex */
public final class TaskChangeNotifier {
    public final Context mContext;
    public RemoteAppTaskWatcher mTaskWatcher;
    public ServiceThread mTaskWatcherThread = null;
    public final Object lockObject = new Object();
    public final Map mTaskChangeListeners = new ArrayMap();

    /* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
    public final class TaskChangeListenerInfo extends ListenerInfo {
        public final ITaskChangeListener listener;

        public TaskChangeListenerInfo(
                ITaskChangeListener iTaskChangeListener, String str, int i, int i2) {
            super(i, i2, str);
            this.listener = iTaskChangeListener;
        }

        @Override // android.os.IBinder.DeathRecipient
        public final void binderDied() {
            boolean isEmpty;
            synchronized (TaskChangeNotifier.this.mTaskChangeListeners) {
                ((ArrayMap) TaskChangeNotifier.this.mTaskChangeListeners)
                        .remove(this.listener.asBinder());
                isEmpty = ((ArrayMap) TaskChangeNotifier.this.mTaskChangeListeners).isEmpty();
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

    public TaskChangeNotifier(Context context) {
        this.mContext = context;
    }

    public final void deinitTaskWatcherThread() {
        synchronized (this.lockObject) {
            try {
                ServiceThread serviceThread = this.mTaskWatcherThread;
                if (serviceThread != null) {
                    serviceThread.interrupt();
                    this.mTaskWatcherThread.quitSafely();
                }
            } catch (SecurityException e) {
                Log.e(
                        "TaskChangeNotifier",
                        " unregisterTaskChangeListener: SecurityException " + e.getMessage());
            }
            this.mTaskWatcherThread = null;
            Log.i("TaskChangeNotifier", " deinitTaskWatcherThread : mTaskWatcherThread = null");
        }
    }

    public final void initTaskWatcherThread() {
        synchronized (this.lockObject) {
            try {
                if (this.mTaskWatcherThread == null) {
                    ServiceThread serviceThread =
                            new ServiceThread(-2, "remoteapp_taskwatcher", false);
                    this.mTaskWatcherThread = serviceThread;
                    serviceThread.start();
                    Log.i(
                            "TaskChangeNotifier",
                            "mTaskWatcherThread start : " + this.mTaskWatcherThread);
                }
            } catch (Throwable th) {
                throw th;
            }
        }
    }

    public final boolean linkListenerToDeath(ITaskChangeListener iTaskChangeListener, String str) {
        synchronized (this.mTaskChangeListeners) {
            try {
                IBinder asBinder = iTaskChangeListener.asBinder();
                try {
                    if (str.length() > 100) {
                        str = str.substring(0, 100);
                    }
                    TaskChangeListenerInfo taskChangeListenerInfo =
                            new TaskChangeListenerInfo(
                                    iTaskChangeListener,
                                    str,
                                    Binder.getCallingPid(),
                                    Binder.getCallingUid());
                    asBinder.linkToDeath(taskChangeListenerInfo, 0);
                    ((ArrayMap) this.mTaskChangeListeners).put(asBinder, taskChangeListenerInfo);
                } catch (RemoteException unused) {
                    return false;
                }
            } catch (Throwable th) {
                throw th;
            }
        }
        return true;
    }

    /* JADX WARN: Type inference failed for: r3v3, types: [com.android.server.remoteappmode.RemoteAppTaskWatcher$1] */
    public final boolean registerTaskChangeListener(
            ITaskChangeListener iTaskChangeListener, String str, boolean z) {
        boolean linkListenerToDeath;
        synchronized (this.lockObject) {
            unregisterWatcherInternal();
            deinitTaskWatcherThread();
            initTaskWatcherThread();
            Looper looper = this.mTaskWatcherThread.getLooper();
            final RemoteAppTaskWatcher remoteAppTaskWatcher = new RemoteAppTaskWatcher();
            remoteAppTaskWatcher.mCallback = null;
            android.util.Log.d("RemoteAppTaskWatcher", "RemoteAppTaskWatcher: Entered");
            remoteAppTaskWatcher.mNeedToNotifyTaskDisplayChanged = false;
            remoteAppTaskWatcher.mNeedToNotifyRecentTaskListUpdated = false;
            remoteAppTaskWatcher.mHandler =
                    new Handler(
                            looper) { // from class:
                                      // com.android.server.remoteappmode.RemoteAppTaskWatcher.1
                        @Override // android.os.Handler
                        public final void handleMessage(Message message) {
                            ArrayList arrayList;
                            ActivityManager.RunningTaskInfo runningTaskInfo;
                            ArrayList arrayList2;
                            ArrayList arrayList3;
                            ArrayList arrayList4;
                            super.handleMessage(message);
                            StringBuilder sb =
                                    new StringBuilder(
                                            " ****** RemoteAppTaskWatcher: Message Received ");
                            sb.append(message.what);
                            sb.append(" Task ID = ");
                            GestureWakeup$$ExternalSyntheticOutline0.m(
                                    sb, message.arg1, "RemoteAppTaskWatcher");
                            int i = message.what;
                            if (i == 0) {
                                TaskChangeNotifier taskChangeNotifier =
                                        RemoteAppTaskWatcher.this.mCallback;
                                if (taskChangeNotifier != null) {
                                    int i2 = message.arg1;
                                    synchronized (taskChangeNotifier.mTaskChangeListeners) {
                                        arrayList =
                                                new ArrayList(
                                                        ((ArrayMap)
                                                                        taskChangeNotifier
                                                                                .mTaskChangeListeners)
                                                                .values());
                                    }
                                    Iterator it = arrayList.iterator();
                                    while (it.hasNext()) {
                                        TaskChangeNotifier.TaskChangeListenerInfo
                                                taskChangeListenerInfo =
                                                        (TaskChangeNotifier.TaskChangeListenerInfo)
                                                                it.next();
                                        try {
                                            List<ActivityManager.RunningTaskInfo> runningTasks =
                                                    ((ActivityManager)
                                                                    taskChangeNotifier.mContext
                                                                            .getSystemService(
                                                                                    "activity"))
                                                            .getRunningTasks(Integer.MAX_VALUE);
                                            if (runningTasks != null) {
                                                Iterator<ActivityManager.RunningTaskInfo> it2 =
                                                        runningTasks.iterator();
                                                while (it2.hasNext()) {
                                                    runningTaskInfo = it2.next();
                                                    if (runningTaskInfo.taskId == i2) {
                                                        break;
                                                    }
                                                }
                                            }
                                            runningTaskInfo = null;
                                            taskChangeListenerInfo.listener.onTaskPlayed(
                                                    i2,
                                                    runningTaskInfo != null
                                                            ? ((TaskInfo) runningTaskInfo).displayId
                                                            : -1);
                                        } catch (RemoteException e) {
                                            e.printStackTrace();
                                        }
                                    }
                                    return;
                                }
                                return;
                            }
                            if (i == 1 || i == 2) {
                                return;
                            }
                            if (i == 3) {
                                int i3 = message.arg1;
                                TaskChangeNotifier taskChangeNotifier2 =
                                        RemoteAppTaskWatcher.this.mCallback;
                                if (taskChangeNotifier2 != null) {
                                    synchronized (taskChangeNotifier2.mTaskChangeListeners) {
                                        arrayList2 =
                                                new ArrayList(
                                                        ((ArrayMap)
                                                                        taskChangeNotifier2
                                                                                .mTaskChangeListeners)
                                                                .values());
                                    }
                                    Iterator it3 = arrayList2.iterator();
                                    while (it3.hasNext()) {
                                        try {
                                            ((TaskChangeNotifier.TaskChangeListenerInfo) it3.next())
                                                    .listener.onTaskRemoved(i3);
                                        } catch (RemoteException e2) {
                                            e2.printStackTrace();
                                        }
                                    }
                                    return;
                                }
                                return;
                            }
                            if (i == 4) {
                                int i4 = message.arg1;
                                int i5 = message.arg2;
                                TaskChangeNotifier taskChangeNotifier3 =
                                        RemoteAppTaskWatcher.this.mCallback;
                                if (taskChangeNotifier3 != null) {
                                    synchronized (taskChangeNotifier3.mTaskChangeListeners) {
                                        arrayList3 =
                                                new ArrayList(
                                                        ((ArrayMap)
                                                                        taskChangeNotifier3
                                                                                .mTaskChangeListeners)
                                                                .values());
                                    }
                                    Iterator it4 = arrayList3.iterator();
                                    while (it4.hasNext()) {
                                        try {
                                            ((TaskChangeNotifier.TaskChangeListenerInfo) it4.next())
                                                    .listener.onTaskDisplayChanged(i4, i5);
                                        } catch (RemoteException e3) {
                                            e3.printStackTrace();
                                        }
                                    }
                                    return;
                                }
                                return;
                            }
                            if (i != 5) {
                                android.util.Log.e(
                                        "RemoteAppTaskWatcher",
                                        " ****** Error in received message ");
                                return;
                            }
                            TaskChangeNotifier taskChangeNotifier4 =
                                    RemoteAppTaskWatcher.this.mCallback;
                            if (taskChangeNotifier4 != null) {
                                synchronized (taskChangeNotifier4.mTaskChangeListeners) {
                                    arrayList4 =
                                            new ArrayList(
                                                    ((ArrayMap)
                                                                    taskChangeNotifier4
                                                                            .mTaskChangeListeners)
                                                            .values());
                                }
                                Iterator it5 = arrayList4.iterator();
                                while (it5.hasNext()) {
                                    try {
                                        ((TaskChangeNotifier.TaskChangeListenerInfo) it5.next())
                                                .listener.onRecentTaskListUpdated();
                                    } catch (RemoteException e4) {
                                        e4.printStackTrace();
                                    }
                                }
                            }
                        }
                    };
            this.mTaskWatcher = remoteAppTaskWatcher;
            if (z) {
                remoteAppTaskWatcher.mNeedToNotifyTaskDisplayChanged = true;
                remoteAppTaskWatcher.mNeedToNotifyRecentTaskListUpdated = true;
            }
            registerWatcherToActivityManager();
            RemoteAppTaskWatcher remoteAppTaskWatcher2 = this.mTaskWatcher;
            remoteAppTaskWatcher2.getClass();
            android.util.Log.d("RemoteAppTaskWatcher", "registerTaskChangeNotifier");
            remoteAppTaskWatcher2.mCallback = this;
            linkListenerToDeath = linkListenerToDeath(iTaskChangeListener, str);
        }
        return linkListenerToDeath;
    }

    public final void registerWatcherToActivityManager() {
        try {
            Log.i("TaskChangeNotifier", "registerWatcherToActivityManager : " + this.mTaskWatcher);
            ActivityTaskManager.getService().registerTaskStackListener(this.mTaskWatcher);
        } catch (RemoteException e) {
            Log.e("TaskChangeNotifier", " registerTaskWatcher: RemoteException " + e.getMessage());
        } catch (SecurityException e2) {
            Log.e(
                    "TaskChangeNotifier",
                    " registerTaskWatcher: SecurityException " + e2.getMessage());
        }
    }

    public final boolean unregisterTaskChangeListener(ITaskChangeListener iTaskChangeListener) {
        boolean z;
        synchronized (this.lockObject) {
            unregisterWatcherInternal();
            deinitTaskWatcherThread();
            synchronized (this.mTaskChangeListeners) {
                try {
                    TaskChangeListenerInfo taskChangeListenerInfo =
                            (TaskChangeListenerInfo)
                                    ((ArrayMap) this.mTaskChangeListeners)
                                            .remove(iTaskChangeListener.asBinder());
                    z = false;
                    if (taskChangeListenerInfo != null) {
                        taskChangeListenerInfo
                                .listener
                                .asBinder()
                                .unlinkToDeath(taskChangeListenerInfo, 0);
                        z = true;
                    }
                } finally {
                }
            }
        }
        return z;
    }

    public final void unregisterWatcherInternal() {
        if (this.mTaskWatcher != null) {
            try {
                Log.i(
                        "TaskChangeNotifier",
                        "unregisterWatcherFromActivityManager : " + this.mTaskWatcher);
                ActivityTaskManager.getService().unregisterTaskStackListener(this.mTaskWatcher);
            } catch (RemoteException e) {
                Log.e(
                        "TaskChangeNotifier",
                        " unregisterTaskWatcher: RemoteException " + e.getMessage());
            } catch (SecurityException e2) {
                Log.e(
                        "TaskChangeNotifier",
                        " unregisterTaskWatcher: SecurityException " + e2.getMessage());
            }
            RemoteAppTaskWatcher remoteAppTaskWatcher = this.mTaskWatcher;
            remoteAppTaskWatcher.getClass();
            android.util.Log.d("RemoteAppTaskWatcher", "unregisterTaskChangeNotifier");
            remoteAppTaskWatcher.mCallback = null;
            RemoteAppTaskWatcher remoteAppTaskWatcher2 = this.mTaskWatcher;
            remoteAppTaskWatcher2.getClass();
            android.util.Log.d("RemoteAppTaskWatcher", "****** TaskWatcher:clearMessages ");
            RemoteAppTaskWatcher.AnonymousClass1 anonymousClass1 = remoteAppTaskWatcher2.mHandler;
            if (anonymousClass1 != null) {
                anonymousClass1.removeCallbacksAndMessages(null);
            }
            this.mTaskWatcher = null;
            Log.i("TaskChangeNotifier", "unregisterWatcherInternal : mTaskWatcher = null");
        }
    }
}
