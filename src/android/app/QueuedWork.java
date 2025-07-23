package android.app;

import android.os.Handler;
import android.os.HandlerThread;
import android.os.Looper;
import android.os.Message;
import android.os.StrictMode;
import com.android.internal.util.ExponentiallyBucketedHistogram;
import java.util.Iterator;
import java.util.LinkedList;

/* loaded from: classes.dex */
public class QueuedWork {
    private static final boolean DEBUG = false;
    private static final long DELAY = 100;
    private static final String LOG_TAG = "QueuedWork";
    private static final long MAX_WAIT_TIME_MILLIS = 512;
    private static final Object sLock = new Object();
    private static Object sProcessingWork = new Object();
    private static final LinkedList<Runnable> sFinishers = new LinkedList<>();
    private static Handler sHandler = null;
    private static LinkedList<Runnable> sWork = new LinkedList<>();
    private static boolean sCanDelay = true;
    private static final ExponentiallyBucketedHistogram mWaitTimes = new ExponentiallyBucketedHistogram(16);
    private static int mNumWaits = 0;

    private static Handler getHandler() {
        Handler handler;
        synchronized (sLock) {
            if (sHandler == null) {
                HandlerThread handlerThread = new HandlerThread("queued-work-looper", -2);
                handlerThread.start();
                sHandler = new QueuedWorkHandler(handlerThread.getLooper());
            }
            handler = sHandler;
        }
        return handler;
    }

    public static void resetHandler() {
        synchronized (sLock) {
            Handler handler = sHandler;
            if (handler == null) {
                return;
            }
            handler.getLooper().quitSafely();
            sHandler = null;
        }
    }

    private static void handlerRemoveMessages(int i) {
        synchronized (sLock) {
            if (sHandler == null) {
                return;
            }
            getHandler().removeMessages(i);
        }
    }

    public static void addFinisher(Runnable runnable) {
        synchronized (sLock) {
            sFinishers.add(runnable);
        }
    }

    public static void removeFinisher(Runnable runnable) {
        synchronized (sLock) {
            sFinishers.remove(runnable);
        }
    }

    public static void waitToFinish() {
        Object obj;
        Runnable poll;
        long currentTimeMillis = System.currentTimeMillis();
        synchronized (sLock) {
            handlerRemoveMessages(1);
            sCanDelay = false;
        }
        StrictMode.ThreadPolicy allowThreadDiskWrites = StrictMode.allowThreadDiskWrites();
        try {
            processPendingWork();
            while (true) {
                try {
                    obj = sLock;
                    synchronized (obj) {
                        poll = sFinishers.poll();
                    }
                    if (poll == null) {
                        break;
                    } else {
                        poll.run();
                    }
                } finally {
                    sCanDelay = true;
                }
            }
            synchronized (obj) {
                long currentTimeMillis2 = System.currentTimeMillis() - currentTimeMillis;
                if (currentTimeMillis2 > 0) {
                    ExponentiallyBucketedHistogram exponentiallyBucketedHistogram = mWaitTimes;
                    exponentiallyBucketedHistogram.add(Long.valueOf(currentTimeMillis2).intValue());
                    int i = mNumWaits + 1;
                    mNumWaits = i;
                    if (i % 1024 == 0 || currentTimeMillis2 > 512) {
                        exponentiallyBucketedHistogram.log(LOG_TAG, "waited: ");
                    }
                }
            }
        } finally {
            StrictMode.setThreadPolicy(allowThreadDiskWrites);
        }
    }

    public static void queue(Runnable runnable, boolean z) {
        Handler handler = getHandler();
        synchronized (sLock) {
            sWork.add(runnable);
            if (z && sCanDelay) {
                handler.sendEmptyMessageDelayed(1, DELAY);
            } else {
                handler.sendEmptyMessage(1);
            }
        }
    }

    public static boolean hasPendingWork() {
        boolean z;
        synchronized (sLock) {
            z = !sWork.isEmpty();
        }
        return z;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static void processPendingWork() {
        LinkedList<Runnable> linkedList;
        synchronized (sProcessingWork) {
            synchronized (sLock) {
                linkedList = sWork;
                sWork = new LinkedList<>();
                handlerRemoveMessages(1);
            }
            if (linkedList.size() > 0) {
                Iterator<Runnable> it = linkedList.iterator();
                while (it.hasNext()) {
                    it.next().run();
                }
            }
        }
    }

    private static class QueuedWorkHandler extends Handler {
        static final int MSG_RUN = 1;

        QueuedWorkHandler(Looper looper) {
            super(looper);
        }

        @Override // android.os.Handler
        public void handleMessage(Message message) {
            if (message.what == 1) {
                QueuedWork.processPendingWork();
            }
        }
    }
}
