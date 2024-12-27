package android.app;

import android.os.Handler;
import android.os.HandlerThread;
import android.os.Looper;
import android.os.Message;
import android.os.StrictMode;

import com.android.internal.util.ExponentiallyBucketedHistogram;

import java.util.Iterator;
import java.util.LinkedList;

public class QueuedWork {
    private static final boolean DEBUG = false;
    private static final long DELAY = 100;
    private static final long MAX_WAIT_TIME_MILLIS = 512;
    private static final String LOG_TAG = QueuedWork.class.getSimpleName();
    private static final Object sLock = new Object();
    private static Object sProcessingWork = new Object();
    private static final LinkedList<Runnable> sFinishers = new LinkedList<>();
    private static Handler sHandler = null;
    private static LinkedList<Runnable> sWork = new LinkedList<>();
    private static boolean sCanDelay = true;
    private static final ExponentiallyBucketedHistogram mWaitTimes =
            new ExponentiallyBucketedHistogram(16);
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
            if (sHandler == null) {
                return;
            }
            sHandler.getLooper().quitSafely();
            sHandler = null;
        }
    }

    private static void handlerRemoveMessages(int what) {
        synchronized (sLock) {
            if (sHandler == null) {
                return;
            }
            getHandler().removeMessages(what);
        }
    }

    public static void addFinisher(Runnable finisher) {
        synchronized (sLock) {
            sFinishers.add(finisher);
        }
    }

    public static void removeFinisher(Runnable finisher) {
        synchronized (sLock) {
            sFinishers.remove(finisher);
        }
    }

    public static void waitToFinish() {
        Runnable finisher;
        long startTime = System.currentTimeMillis();
        synchronized (sLock) {
            handlerRemoveMessages(1);
            sCanDelay = false;
        }
        StrictMode.ThreadPolicy oldPolicy = StrictMode.allowThreadDiskWrites();
        try {
            processPendingWork();
            while (true) {
                try {
                    synchronized (sLock) {
                        finisher = sFinishers.poll();
                    }
                    if (finisher == null) {
                        break;
                    } else {
                        finisher.run();
                    }
                } catch (Throwable th) {
                    sCanDelay = true;
                    throw th;
                }
            }
            sCanDelay = true;
            synchronized (sLock) {
                long waitTime = System.currentTimeMillis() - startTime;
                if (waitTime > 0 || 0 != 0) {
                    mWaitTimes.add(Long.valueOf(waitTime).intValue());
                    mNumWaits++;
                    if (mNumWaits % 1024 == 0 || waitTime > 512) {
                        mWaitTimes.log(LOG_TAG, "waited: ");
                    }
                }
            }
        } finally {
            StrictMode.setThreadPolicy(oldPolicy);
        }
    }

    public static void queue(Runnable work, boolean shouldDelay) {
        Handler handler = getHandler();
        synchronized (sLock) {
            sWork.add(work);
            if (shouldDelay && sCanDelay) {
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
        LinkedList<Runnable> work;
        synchronized (sProcessingWork) {
            synchronized (sLock) {
                work = sWork;
                sWork = new LinkedList<>();
                handlerRemoveMessages(1);
            }
            if (work.size() > 0) {
                Iterator<Runnable> it = work.iterator();
                while (it.hasNext()) {
                    Runnable w = it.next();
                    w.run();
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
        public void handleMessage(Message msg) {
            if (msg.what == 1) {
                QueuedWork.processPendingWork();
            }
        }
    }
}
