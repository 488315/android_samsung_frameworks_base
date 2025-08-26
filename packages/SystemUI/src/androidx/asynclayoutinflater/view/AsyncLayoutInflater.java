package androidx.asynclayoutinflater.view;

import android.content.Context;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.util.AttributeSet;
import android.util.Log;
import android.view.InflateException;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import androidx.core.util.Pools$SynchronizedPool;
import java.util.concurrent.ArrayBlockingQueue;

/* loaded from: classes.dex */
public final class AsyncLayoutInflater {
    public final Handler mHandler;
    public final AnonymousClass1 mHandlerCallback;
    public final InflateThread mInflateThread;
    public final BasicInflater mInflater;

    public class BasicInflater extends LayoutInflater {
        public static final String[] sClassPrefixList = {"android.widget.", "android.webkit.", "android.app."};

        public BasicInflater(Context context) {
            super(context);
        }

        @Override // android.view.LayoutInflater
        public final LayoutInflater cloneInContext(Context context) {
            return new BasicInflater(context);
        }

        @Override // android.view.LayoutInflater
        public final View onCreateView(String str, AttributeSet attributeSet) throws InflateException, ClassNotFoundException {
            View viewCreateView;
            String[] strArr = sClassPrefixList;
            for (int i = 0; i < 3; i++) {
                try {
                    viewCreateView = createView(str, strArr[i], attributeSet);
                } catch (ClassNotFoundException unused) {
                }
                if (viewCreateView != null) {
                    return viewCreateView;
                }
            }
            return super.onCreateView(str, attributeSet);
        }
    }

    public class InflateRequest {
        public OnInflateFinishedListener callback;
        public Handler mHandler;
        public BasicInflater mInflater;
        public ViewGroup parent;
        public int resid;
        public View view;
    }

    public class InflateThread extends Thread {
        public static final InflateThread sInstance;
        public final ArrayBlockingQueue mQueue = new ArrayBlockingQueue(10);
        public final Pools$SynchronizedPool mRequestPool = new Pools$SynchronizedPool(10);

        static {
            InflateThread inflateThread = new InflateThread();
            sInstance = inflateThread;
            inflateThread.setName("AsyncLayoutInflator");
            inflateThread.start();
        }

        private InflateThread() {
        }

        @Override // java.lang.Thread, java.lang.Runnable
        public final void run() {
            while (true) {
                try {
                    InflateRequest inflateRequest = (InflateRequest) this.mQueue.take();
                    try {
                        inflateRequest.view = inflateRequest.mInflater.inflate(inflateRequest.resid, inflateRequest.parent, false);
                    } catch (RuntimeException e) {
                        Log.w("AsyncLayoutInflater", "Failed to inflate resource in the background! Retrying on the UI thread", e);
                    }
                    View view = inflateRequest.view;
                    Message.obtain(inflateRequest.mHandler, 0, inflateRequest).sendToTarget();
                } catch (InterruptedException e2) {
                    Log.w("AsyncLayoutInflater", e2);
                }
            }
        }
    }

    public interface OnInflateFinishedListener {
        void onInflateFinished(int i, View view, ViewGroup viewGroup);
    }

    /* JADX WARN: Type inference failed for: r0v0, types: [android.os.Handler$Callback, androidx.asynclayoutinflater.view.AsyncLayoutInflater$1] */
    public AsyncLayoutInflater(Context context) {
        ?? r0 = new Handler.Callback() { // from class: androidx.asynclayoutinflater.view.AsyncLayoutInflater.1
            @Override // android.os.Handler.Callback
            public final boolean handleMessage(Message message) {
                InflateRequest inflateRequest = (InflateRequest) message.obj;
                if (inflateRequest.view == null) {
                    inflateRequest.view = inflateRequest.mInflater.inflate(inflateRequest.resid, inflateRequest.parent, false);
                }
                InflateThread inflateThread = AsyncLayoutInflater.this.mInflateThread;
                inflateRequest.callback.onInflateFinished(inflateRequest.resid, inflateRequest.view, inflateRequest.parent);
                inflateThread.getClass();
                inflateRequest.callback = null;
                inflateRequest.mInflater = null;
                inflateRequest.mHandler = null;
                inflateRequest.parent = null;
                inflateRequest.resid = 0;
                inflateRequest.view = null;
                inflateThread.mRequestPool.release(inflateRequest);
                return true;
            }
        };
        this.mHandlerCallback = r0;
        this.mInflater = new BasicInflater(context);
        this.mHandler = new Handler(Looper.myLooper(), r0);
        this.mInflateThread = InflateThread.sInstance;
    }

    public final void inflateInternal(int i, ViewGroup viewGroup, OnInflateFinishedListener onInflateFinishedListener, BasicInflater basicInflater) throws InterruptedException {
        InflateThread inflateThread = this.mInflateThread;
        InflateRequest inflateRequest = (InflateRequest) inflateThread.mRequestPool.acquire();
        if (inflateRequest == null) {
            inflateRequest = new InflateRequest();
        }
        inflateRequest.mInflater = basicInflater;
        inflateRequest.mHandler = this.mHandler;
        inflateRequest.resid = i;
        inflateRequest.parent = viewGroup;
        inflateRequest.callback = onInflateFinishedListener;
        try {
            inflateThread.mQueue.put(inflateRequest);
        } catch (InterruptedException e) {
            throw new RuntimeException("Failed to enqueue async inflate request", e);
        }
    }

    /* JADX WARN: Type inference failed for: r0v0, types: [android.os.Handler$Callback, androidx.asynclayoutinflater.view.AsyncLayoutInflater$1] */
    public AsyncLayoutInflater(Context context, AsyncLayoutFactory asyncLayoutFactory) {
        ?? r0 = new Handler.Callback() { // from class: androidx.asynclayoutinflater.view.AsyncLayoutInflater.1
            @Override // android.os.Handler.Callback
            public final boolean handleMessage(Message message) {
                InflateRequest inflateRequest = (InflateRequest) message.obj;
                if (inflateRequest.view == null) {
                    inflateRequest.view = inflateRequest.mInflater.inflate(inflateRequest.resid, inflateRequest.parent, false);
                }
                InflateThread inflateThread = AsyncLayoutInflater.this.mInflateThread;
                inflateRequest.callback.onInflateFinished(inflateRequest.resid, inflateRequest.view, inflateRequest.parent);
                inflateThread.getClass();
                inflateRequest.callback = null;
                inflateRequest.mInflater = null;
                inflateRequest.mHandler = null;
                inflateRequest.parent = null;
                inflateRequest.resid = 0;
                inflateRequest.view = null;
                inflateThread.mRequestPool.release(inflateRequest);
                return true;
            }
        };
        this.mHandlerCallback = r0;
        BasicInflater basicInflater = new BasicInflater(context);
        this.mInflater = basicInflater;
        basicInflater.setFactory2(asyncLayoutFactory);
        this.mHandler = new Handler(Looper.myLooper(), r0);
        this.mInflateThread = InflateThread.sInstance;
    }
}
