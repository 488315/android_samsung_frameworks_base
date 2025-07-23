package com.samsung.android.media.codec;

import android.content.Context;
import android.inputmethodservice.navigationbar.NavigationBarInflaterView;
import android.os.Handler;
import android.os.HandlerThread;
import android.os.IBinder;
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
            /* JADX WARN: Code restructure failed: missing block: B:25:0x0036, code lost:
            
                android.util.Log.i(com.samsung.android.media.codec.VideoTranscodingService.TAG, "Task(" + r4.getID() + ") has been started");
                r4.start();
             */
            @Override // android.os.Handler
            /*
                Code decompiled incorrectly, please refer to instructions dump.
                To view partially-correct code enable 'Show inconsistent code' option in preferences
            */
            public void handleMessage(android.os.Message r4) {
                /*
                    r3 = this;
                    super.handleMessage(r4)
                    int r4 = r4.what
                    if (r4 == 0) goto L9
                    goto L81
                L9:
                    com.samsung.android.media.codec.VideoTranscodingService r4 = com.samsung.android.media.codec.VideoTranscodingService.this
                    java.util.concurrent.locks.Lock r4 = com.samsung.android.media.codec.VideoTranscodingService.m9330$$Nest$fgetmTaskLock(r4)
                    r4.lock()
                    com.samsung.android.media.codec.VideoTranscodingService r4 = com.samsung.android.media.codec.VideoTranscodingService.this     // Catch: java.lang.Throwable -> L72 java.lang.Exception -> L74
                    com.samsung.android.media.codec.VideoTranscodingService.m9331$$Nest$mprintTasks(r4)     // Catch: java.lang.Throwable -> L72 java.lang.Exception -> L74
                L17:
                    com.samsung.android.media.codec.VideoTranscodingService r4 = com.samsung.android.media.codec.VideoTranscodingService.this     // Catch: java.lang.Throwable -> L72 java.lang.Exception -> L74
                    java.util.Queue r4 = com.samsung.android.media.codec.VideoTranscodingService.m9329$$Nest$fgetmStartingTasks(r4)     // Catch: java.lang.Throwable -> L72 java.lang.Exception -> L74
                    int r4 = r4.size()     // Catch: java.lang.Throwable -> L72 java.lang.Exception -> L74
                    if (r4 != 0) goto L24
                    goto L60
                L24:
                    com.samsung.android.media.codec.VideoTranscodingService r4 = com.samsung.android.media.codec.VideoTranscodingService.this     // Catch: java.lang.Throwable -> L72 java.lang.Exception -> L74
                    java.util.Queue r4 = com.samsung.android.media.codec.VideoTranscodingService.m9329$$Nest$fgetmStartingTasks(r4)     // Catch: java.lang.Throwable -> L72 java.lang.Exception -> L74
                    java.lang.Object r4 = r4.element()     // Catch: java.lang.Throwable -> L72 java.lang.Exception -> L74
                    com.samsung.android.media.codec.VideoTranscodingService$Task r4 = (com.samsung.android.media.codec.VideoTranscodingService.Task) r4     // Catch: java.lang.Throwable -> L72 java.lang.Exception -> L74
                    int r0 = r4.getState()     // Catch: java.lang.Throwable -> L72 java.lang.Exception -> L74
                    if (r0 != 0) goto L59
                    java.lang.String r0 = "VideoTranscodingService"
                    java.lang.StringBuilder r1 = new java.lang.StringBuilder     // Catch: java.lang.Throwable -> L72 java.lang.Exception -> L74
                    r1.<init>()     // Catch: java.lang.Throwable -> L72 java.lang.Exception -> L74
                    java.lang.String r2 = "Task("
                    r1.append(r2)     // Catch: java.lang.Throwable -> L72 java.lang.Exception -> L74
                    java.lang.String r2 = r4.getID()     // Catch: java.lang.Throwable -> L72 java.lang.Exception -> L74
                    r1.append(r2)     // Catch: java.lang.Throwable -> L72 java.lang.Exception -> L74
                    java.lang.String r2 = ") has been started"
                    r1.append(r2)     // Catch: java.lang.Throwable -> L72 java.lang.Exception -> L74
                    java.lang.String r1 = r1.toString()     // Catch: java.lang.Throwable -> L72 java.lang.Exception -> L74
                    android.util.Log.i(r0, r1)     // Catch: java.lang.Throwable -> L72 java.lang.Exception -> L74
                    r4.start()     // Catch: java.lang.Throwable -> L72 java.lang.Exception -> L74
                    goto L60
                L59:
                    int r0 = r4.getState()     // Catch: java.lang.Throwable -> L72 java.lang.Exception -> L74
                    r1 = 1
                    if (r0 != r1) goto L61
                L60:
                    goto L78
                L61:
                    int r0 = r4.getState()     // Catch: java.lang.Throwable -> L72 java.lang.Exception -> L74
                    r1 = 2
                    if (r0 != r1) goto L17
                    com.samsung.android.media.codec.VideoTranscodingService r0 = com.samsung.android.media.codec.VideoTranscodingService.this     // Catch: java.lang.Throwable -> L72 java.lang.Exception -> L74
                    java.util.Queue r0 = com.samsung.android.media.codec.VideoTranscodingService.m9329$$Nest$fgetmStartingTasks(r0)     // Catch: java.lang.Throwable -> L72 java.lang.Exception -> L74
                    r0.remove(r4)     // Catch: java.lang.Throwable -> L72 java.lang.Exception -> L74
                    goto L17
                L72:
                    r4 = move-exception
                    goto L82
                L74:
                    r4 = move-exception
                    r4.printStackTrace()     // Catch: java.lang.Throwable -> L72
                L78:
                    com.samsung.android.media.codec.VideoTranscodingService r3 = com.samsung.android.media.codec.VideoTranscodingService.this
                    java.util.concurrent.locks.Lock r3 = com.samsung.android.media.codec.VideoTranscodingService.m9330$$Nest$fgetmTaskLock(r3)
                    r3.unlock()
                L81:
                    return
                L82:
                    com.samsung.android.media.codec.VideoTranscodingService r3 = com.samsung.android.media.codec.VideoTranscodingService.this
                    java.util.concurrent.locks.Lock r3 = com.samsung.android.media.codec.VideoTranscodingService.m9330$$Nest$fgetmTaskLock(r3)
                    r3.unlock()
                    throw r4
                */
                throw new UnsupportedOperationException("Method not decompiled: com.samsung.android.media.codec.VideoTranscodingService.AnonymousClass1.handleMessage(android.os.Message):void");
            }
        };
    }

    @Override // com.samsung.android.media.codec.IVideoTranscodingService
    public synchronized String register(int i, IVideoTranscodingServiceCallback iVideoTranscodingServiceCallback) {
        if (iVideoTranscodingServiceCallback == null) {
            Log.w(TAG, "callback is null");
            return null;
        }
        String num = Integer.toString(this.mCurrentId);
        int i2 = this.mCurrentId + 1;
        this.mCurrentId = i2;
        if (i2 == Integer.MAX_VALUE) {
            this.mCurrentId = 0;
        }
        final Task task = new Task(num, i, iVideoTranscodingServiceCallback);
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
        return num;
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
        Task task;
        Log.d(TAG, "stopTask(" + str + NavigationBarInflaterView.KEY_CODE_END);
        this.mTaskLock.lock();
        boolean z = false;
        try {
            try {
                if (this.mWaitingTasks.containsKey(str)) {
                    task = this.mWaitingTasks.remove(str);
                    Log.i(TAG, "Task(" + str + ") has been removed in w queue.");
                    z = true;
                } else {
                    Iterator<Task> it = this.mStartingTasks.iterator();
                    while (true) {
                        if (!it.hasNext()) {
                            task = null;
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
                            task = next;
                        }
                    }
                }
                if (task != null) {
                    task.unlinkToDeath();
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
