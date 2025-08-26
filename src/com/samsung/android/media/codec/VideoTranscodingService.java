package com.samsung.android.media.codec;

import android.content.Context;
import android.inputmethodservice.navigationbar.NavigationBarInflaterView;
import android.os.Handler;
import android.os.HandlerThread;
import android.os.IBinder;
import android.os.Message;
import android.os.RemoteException;
import android.util.Log;
import com.samsung.android.media.codec.IVideoTranscodingService;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.Map;
import java.util.Queue;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

/* loaded from: classes6.dex */
public class VideoTranscodingService extends IVideoTranscodingService.Stub {
    private static final int HANDLER_MESSAGE_QUEUE_UPDATED = 0;
    private static final int MAX_PRINT_TASKS = 20;
    private static final String TAG = "VideoTranscodingService";
    private static final int TASK_STATE_STOPPED = 2;
    private static final int TASK_STATE_TRANSCODING = 1;
    private static final int TASK_STATE_WAITING = 0;
    private final Context mContext;
    private Handler mHandler;
    private HandlerThread mHandlerThread;
    private final Lock mTaskLock = new ReentrantLock();
    private int mCurrentId = 0;
    private Map<String, Task> mWaitingTasks = new HashMap();
    private Queue<Task> mStartingTasks = new LinkedList();

    private static class Task {
        private final IVideoTranscodingServiceCallback mCallback;
        private final String mID;
        private final int mMode;
        private IBinder.DeathRecipient mDeathRecipient = null;
        private int mState = 0;

        public Task(String str, int i, IVideoTranscodingServiceCallback iVideoTranscodingServiceCallback) {
            this.mID = str;
            this.mMode = i;
            this.mCallback = iVideoTranscodingServiceCallback;
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
        HandlerThread handlerThread = new HandlerThread("TranscodingHandler", 1);
        this.mHandlerThread = handlerThread;
        handlerThread.start();
        this.mHandler = new Handler(this.mHandlerThread.getLooper()) { // from class: com.samsung.android.media.codec.VideoTranscodingService.1
            /* JADX WARN: Code restructure failed: missing block: B:12:0x0036, code lost:
            
                android.util.Log.i(com.samsung.android.media.codec.VideoTranscodingService.TAG, "Task(" + r4.getID() + ") has been started");
                r4.start();
             */
            @Override // android.os.Handler
            /*
                Code decompiled incorrectly, please refer to instructions dump.
            */
            public void handleMessage(Message message) {
                super.handleMessage(message);
                if (message.what != 0) {
                    return;
                }
                VideoTranscodingService.this.mTaskLock.lock();
                try {
                    try {
                        VideoTranscodingService.this.printTasks();
                        while (true) {
                            if (VideoTranscodingService.this.mStartingTasks.size() == 0) {
                                break;
                            }
                            Task task = (Task) VideoTranscodingService.this.mStartingTasks.element();
                            if (task.getState() == 0) {
                                break;
                            }
                            if (task.getState() == 1) {
                                break;
                            } else if (task.getState() == 2) {
                                VideoTranscodingService.this.mStartingTasks.remove(task);
                            }
                        }
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                } finally {
                    VideoTranscodingService.this.mTaskLock.unlock();
                }
            }
        };
    }

    @Override // com.samsung.android.media.codec.IVideoTranscodingService
    public synchronized String register(int i, IVideoTranscodingServiceCallback iVideoTranscodingServiceCallback) {
        if (iVideoTranscodingServiceCallback == null) {
            Log.w(TAG, "callback is null");
            return null;
        }
        String string = Integer.toString(this.mCurrentId);
        int i2 = this.mCurrentId + 1;
        this.mCurrentId = i2;
        if (i2 == Integer.MAX_VALUE) {
            this.mCurrentId = 0;
        }
        final Task task = new Task(string, i, iVideoTranscodingServiceCallback);
        if (!task.linkToDeath(new IBinder.DeathRecipient() { // from class: com.samsung.android.media.codec.VideoTranscodingService.2
            @Override // android.os.IBinder.DeathRecipient
            public void binderDied() {
                Log.e(VideoTranscodingService.TAG, "binderDied: task(" + task.getID() + NavigationBarInflaterView.KEY_CODE_END);
                try {
                    VideoTranscodingService.this.stopTask(task.getID());
                } catch (RemoteException e) {
                    e.printStackTrace();
                }
            }
        })) {
            Log.e(TAG, "Failed to link to death.");
            return null;
        }
        addTask(task);
        return string;
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
    public void startTask(String str) throws RemoteException {
        Task task;
        Log.d(TAG, "startTask(" + str + NavigationBarInflaterView.KEY_CODE_END);
        this.mTaskLock.lock();
        try {
            try {
                task = this.mWaitingTasks.get(str);
            } catch (Exception e) {
                e.printStackTrace();
            }
            if (task == null) {
                Log.w(TAG, "There is no task id(" + str + ") to start");
                return;
            }
            this.mWaitingTasks.remove(str);
            this.mStartingTasks.add(task);
            this.mTaskLock.unlock();
            updateQueues();
        } finally {
            this.mTaskLock.unlock();
        }
    }

    @Override // com.samsung.android.media.codec.IVideoTranscodingService
    public void stopTask(String str) throws RemoteException {
        Task taskRemove;
        Log.d(TAG, "stopTask(" + str + NavigationBarInflaterView.KEY_CODE_END);
        this.mTaskLock.lock();
        boolean z = false;
        try {
            try {
                if (this.mWaitingTasks.containsKey(str)) {
                    taskRemove = this.mWaitingTasks.remove(str);
                    Log.i(TAG, "Task(" + str + ") has been removed in w queue.");
                    z = true;
                } else {
                    Iterator<Task> it = this.mStartingTasks.iterator();
                    while (true) {
                        if (!it.hasNext()) {
                            taskRemove = null;
                            break;
                        }
                        Task next = it.next();
                        if (next.getID().equals(str)) {
                            if (next.getState() == 1) {
                                Log.i(TAG, "Task(" + next.getID() + ") try to stop.");
                                next.stop();
                            }
                            this.mStartingTasks.remove(next);
                            Log.i(TAG, "Task(" + str + ") has been removed in s queue.");
                            z = true;
                            taskRemove = next;
                        }
                    }
                }
                if (taskRemove != null) {
                    taskRemove.unlinkToDeath();
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
            if (z) {
                updateQueues();
                return;
            }
            Log.w(TAG, "There is no task id(" + str + ") to stop");
        } finally {
            this.mTaskLock.unlock();
        }
    }

    private void updateQueues() {
        this.mHandler.sendMessage(this.mHandler.obtainMessage(0));
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void printTasks() {
        Iterator<String> it = this.mWaitingTasks.keySet().iterator();
        String str = "";
        int i = 1;
        String str2 = "";
        int i2 = 1;
        while (true) {
            if (!it.hasNext()) {
                break;
            }
            String next = it.next();
            if (i2 > 20) {
                str2 = str2 + " ... more";
                break;
            }
            str2 = str2 + " " + next;
            i2++;
        }
        Log.i(TAG, "Waiting tasks(" + this.mWaitingTasks.size() + NavigationBarInflaterView.KEY_CODE_END + str2);
        Iterator<Task> it2 = this.mStartingTasks.iterator();
        while (true) {
            if (!it2.hasNext()) {
                break;
            }
            Task next2 = it2.next();
            if (i > 20) {
                str = str + " ... more";
                break;
            }
            str = str + " " + next2.getID() + NavigationBarInflaterView.KEY_CODE_START + next2.mMode + NavigationBarInflaterView.KEY_CODE_END;
            i++;
        }
        Log.i(TAG, "Starting tasks(" + this.mStartingTasks.size() + NavigationBarInflaterView.KEY_CODE_END + str);
    }
}
