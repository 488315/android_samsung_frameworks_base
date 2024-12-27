package com.samsung.android.media.codec;

import android.content.Context;
import android.inputmethodservice.navigationbar.NavigationBarInflaterView;
import android.os.Handler;
import android.os.HandlerThread;
import android.os.IBinder;
import android.os.Message;
import android.os.RemoteException;
import android.util.Log;

import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.Map;
import java.util.Queue;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class VideoTranscodingService extends IVideoTranscodingService.Stub {
    private static final int HANDLER_MESSAGE_QUEUE_UPDATED = 0;
    private static final int MAX_PRINT_TASKS = 20;
    private static final String TAG = "VideoTranscodingService";
    private static final int TASK_STATE_STOPPED = 2;
    private static final int TASK_STATE_TRANSCODING = 1;
    private static final int TASK_STATE_WAITING = 0;
    private final Context mContext;
    private Handler mHandler;
    private final Lock mTaskLock = new ReentrantLock();
    private int mCurrentId = 0;
    private Map<String, Task> mWaitingTasks = new HashMap();
    private Queue<Task> mStartingTasks = new LinkedList();
    private HandlerThread mHandlerThread = new HandlerThread("TranscodingHandler", 1);

    private static class Task {
        private final IVideoTranscodingServiceCallback mCallback;
        private final String mID;
        private final int mMode;
        private IBinder.DeathRecipient mDeathRecipient = null;
        private int mState = 0;

        public Task(String id, int mode, IVideoTranscodingServiceCallback callback) {
            this.mID = id;
            this.mMode = mode;
            this.mCallback = callback;
        }

        public String getID() {
            return this.mID;
        }

        public int getState() {
            return this.mState;
        }

        public void start() {
            if (this.mState != 0) {
                return;
            }
            this.mState = 1;
            try {
                this.mCallback.onReady();
            } catch (RemoteException e) {
                e.printStackTrace();
            }
        }

        public void stop() {
            if (this.mState != 1) {
                return;
            }
            this.mState = 2;
        }

        public boolean linkToDeath(IBinder.DeathRecipient deathRecipient) {
            this.mDeathRecipient = deathRecipient;
            try {
                this.mCallback.asBinder().linkToDeath(this.mDeathRecipient, 0);
                return true;
            } catch (RemoteException e) {
                e.printStackTrace();
                return false;
            }
        }

        public void unlinkToDeath() {
            if (this.mDeathRecipient != null) {
                this.mCallback.asBinder().unlinkToDeath(this.mDeathRecipient, 0);
            }
        }
    }

    public VideoTranscodingService(Context context) {
        this.mContext = context;
        this.mHandlerThread.start();
        this.mHandler =
                new Handler(
                        this.mHandlerThread
                                .getLooper()) { // from class:
                                                // com.samsung.android.media.codec.VideoTranscodingService.1
                    @Override // android.os.Handler
                    public void handleMessage(Message msg) {
                        super.handleMessage(msg);
                        switch (msg.what) {
                            case 0:
                                VideoTranscodingService.this.mTaskLock.lock();
                                try {
                                    try {
                                        VideoTranscodingService.this.printTasks();
                                        while (true) {
                                            if (VideoTranscodingService.this.mStartingTasks.size()
                                                    != 0) {
                                                Task top =
                                                        (Task)
                                                                VideoTranscodingService.this
                                                                        .mStartingTasks.element();
                                                if (top.getState() == 0) {
                                                    Log.i(
                                                            VideoTranscodingService.TAG,
                                                            "Task("
                                                                    + top.getID()
                                                                    + ") has been started");
                                                    top.start();
                                                } else if (top.getState() != 1) {
                                                    if (top.getState() == 2) {
                                                        VideoTranscodingService.this.mStartingTasks
                                                                .remove(top);
                                                    }
                                                }
                                            }
                                        }
                                    } catch (Exception e) {
                                        e.printStackTrace();
                                    }
                                    return;
                                } finally {
                                    VideoTranscodingService.this.mTaskLock.unlock();
                                }
                            default:
                                return;
                        }
                    }
                };
    }

    @Override // com.samsung.android.media.codec.IVideoTranscodingService
    public synchronized String register(int mode, IVideoTranscodingServiceCallback callback) {
        if (callback == null) {
            Log.w(TAG, "callback is null");
            return null;
        }
        String id = Integer.toString(this.mCurrentId);
        this.mCurrentId++;
        if (this.mCurrentId == Integer.MAX_VALUE) {
            this.mCurrentId = 0;
        }
        final Task task = new Task(id, mode, callback);
        boolean ret =
                task.linkToDeath(
                        new IBinder
                                .DeathRecipient() { // from class:
                                                    // com.samsung.android.media.codec.VideoTranscodingService.2
                            @Override // android.os.IBinder.DeathRecipient
                            public void binderDied() {
                                Log.e(
                                        VideoTranscodingService.TAG,
                                        "binderDied: task("
                                                + task.getID()
                                                + NavigationBarInflaterView.KEY_CODE_END);
                                try {
                                    VideoTranscodingService.this.stopTask(task.getID());
                                } catch (RemoteException e) {
                                    e.printStackTrace();
                                }
                            }
                        });
        if (!ret) {
            Log.e(TAG, "Failed to link to death.");
            return null;
        }
        addTask(task);
        return id;
    }

    private void addTask(Task task) {
        Log.d(TAG, "addTask(" + task.getID() + NavigationBarInflaterView.KEY_CODE_END);
        this.mTaskLock.lock();
        try {
            try {
                this.mWaitingTasks.put(task.getID(), task);
            } catch (Exception e) {
                e.printStackTrace();
            }
            this.mTaskLock.unlock();
            updateQueues();
        } catch (Throwable th) {
            this.mTaskLock.unlock();
            throw th;
        }
    }

    @Override // com.samsung.android.media.codec.IVideoTranscodingService
    public void startTask(String id) throws RemoteException {
        Task task;
        Log.d(TAG, "startTask(" + id + NavigationBarInflaterView.KEY_CODE_END);
        this.mTaskLock.lock();
        try {
            try {
                task = this.mWaitingTasks.get(id);
            } catch (Exception e) {
                e.printStackTrace();
            }
            if (task == null) {
                Log.w(TAG, "There is no task id(" + id + ") to start");
                return;
            }
            this.mWaitingTasks.remove(id);
            this.mStartingTasks.add(task);
            this.mTaskLock.unlock();
            updateQueues();
        } finally {
            this.mTaskLock.unlock();
        }
    }

    @Override // com.samsung.android.media.codec.IVideoTranscodingService
    public void stopTask(String id) throws RemoteException {
        Log.d(TAG, "stopTask(" + id + NavigationBarInflaterView.KEY_CODE_END);
        boolean isRemoved = false;
        this.mTaskLock.lock();
        Task removedTask = null;
        try {
            try {
                if (this.mWaitingTasks.containsKey(id)) {
                    removedTask = this.mWaitingTasks.remove(id);
                    Log.i(TAG, "Task(" + id + ") has been removed in w queue.");
                    isRemoved = true;
                } else {
                    Iterator<Task> it = this.mStartingTasks.iterator();
                    while (true) {
                        if (!it.hasNext()) {
                            break;
                        }
                        Task task = it.next();
                        if (task.getID().equals(id)) {
                            removedTask = task;
                            if (task.getState() == 1) {
                                Log.i(TAG, "Task(" + task.getID() + ") try to stop.");
                                task.stop();
                            }
                            this.mStartingTasks.remove(task);
                            Log.i(TAG, "Task(" + id + ") has been removed in s queue.");
                            isRemoved = true;
                        }
                    }
                }
                if (removedTask != null) {
                    removedTask.unlinkToDeath();
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
            if (isRemoved) {
                updateQueues();
            } else {
                Log.w(TAG, "There is no task id(" + id + ") to stop");
            }
        } finally {
            this.mTaskLock.unlock();
        }
    }

    private void updateQueues() {
        Message message = this.mHandler.obtainMessage(0);
        this.mHandler.sendMessage(message);
    }

    public void printTasks() {
        String tasks = "";
        int i = 1;
        Iterator<String> it = this.mWaitingTasks.keySet().iterator();
        while (true) {
            if (!it.hasNext()) {
                break;
            }
            String s = it.next();
            if (i > 20) {
                tasks = tasks + " ... more";
                break;
            } else {
                tasks = tasks + " " + s;
                i++;
            }
        }
        Log.i(
                TAG,
                "Waiting tasks("
                        + this.mWaitingTasks.size()
                        + NavigationBarInflaterView.KEY_CODE_END
                        + tasks);
        String tasks2 = "";
        int i2 = 1;
        Iterator<Task> it2 = this.mStartingTasks.iterator();
        while (true) {
            if (!it2.hasNext()) {
                break;
            }
            Task task = it2.next();
            if (i2 > 20) {
                tasks2 = tasks2 + " ... more";
                break;
            } else {
                tasks2 =
                        tasks2
                                + " "
                                + task.getID()
                                + NavigationBarInflaterView.KEY_CODE_START
                                + task.mMode
                                + NavigationBarInflaterView.KEY_CODE_END;
                i2++;
            }
        }
        Log.i(
                TAG,
                "Starting tasks("
                        + this.mStartingTasks.size()
                        + NavigationBarInflaterView.KEY_CODE_END
                        + tasks2);
    }
}
