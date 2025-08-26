package android.widget;

import android.os.Handler;
import android.os.HandlerThread;
import android.os.Looper;
import android.os.Message;
import android.util.Log;

/* loaded from: classes5.dex */
public abstract class Filter {
    private static final int FILTER_TOKEN = -791613427;
    private static final int FINISH_TOKEN = -559038737;
    private static final String LOG_TAG = "Filter";
    private static final String THREAD_NAME = "Filter";
    private Delayer mDelayer;
    private final Object mLock = new Object();
    private Handler mResultHandler = new ResultsHandler();
    private Handler mThreadHandler;

    public interface Delayer {
        long getPostingDelay(CharSequence charSequence);
    }

    public interface FilterListener {
        void onFilterComplete(int i);
    }

    /* JADX INFO: Access modifiers changed from: protected */
    public static class FilterResults {
        public int count;
        public Object values;
    }

    protected abstract FilterResults performFiltering(CharSequence charSequence);

    protected abstract void publishResults(CharSequence charSequence, FilterResults filterResults);

    public void setDelayer(Delayer delayer) {
        synchronized (this.mLock) {
            this.mDelayer = delayer;
        }
    }

    public final void filter(CharSequence charSequence) {
        filter(charSequence, null);
    }

    /* JADX WARN: Multi-variable type inference failed */
    public final void filter(CharSequence charSequence, FilterListener filterListener) {
        synchronized (this.mLock) {
            if (this.mThreadHandler == null) {
                HandlerThread handlerThread = new HandlerThread("Filter", 10);
                handlerThread.start();
                this.mThreadHandler = new RequestHandler(handlerThread.getLooper());
            }
            Delayer delayer = this.mDelayer;
            long postingDelay = delayer == null ? 0L : delayer.getPostingDelay(charSequence);
            Message messageObtainMessage = this.mThreadHandler.obtainMessage(FILTER_TOKEN);
            RequestArguments requestArguments = new RequestArguments();
            requestArguments.constraint = charSequence != null ? charSequence.toString() : null;
            requestArguments.listener = filterListener;
            messageObtainMessage.obj = requestArguments;
            this.mThreadHandler.removeMessages(FILTER_TOKEN);
            this.mThreadHandler.removeMessages(FINISH_TOKEN);
            this.mThreadHandler.sendMessageDelayed(messageObtainMessage, postingDelay);
        }
    }

    public CharSequence convertResultToString(Object obj) {
        return obj == null ? "" : obj.toString();
    }

    private class RequestHandler extends Handler {
        public RequestHandler(Looper looper) {
            super(looper);
        }

        /* JADX WARN: Multi-variable type inference failed */
        /* JADX WARN: Type inference failed for: r0v0, types: [int] */
        /* JADX WARN: Type inference failed for: r0v2, types: [android.os.Message] */
        @Override // android.os.Handler
        public void handleMessage(Message message) {
            int i = message.what;
            if (i != Filter.FILTER_TOKEN) {
                if (i != Filter.FINISH_TOKEN) {
                    return;
                }
                synchronized (Filter.this.mLock) {
                    if (Filter.this.mThreadHandler != null) {
                        Filter.this.mThreadHandler.getLooper().quit();
                        Filter.this.mThreadHandler = null;
                    }
                }
                return;
            }
            RequestArguments requestArguments = (RequestArguments) message.obj;
            try {
                try {
                    requestArguments.results = Filter.this.performFiltering(requestArguments.constraint);
                } catch (Exception e) {
                    requestArguments.results = new FilterResults();
                    Log.w("Filter", "An exception occured during performFiltering()!", e);
                }
                synchronized (Filter.this.mLock) {
                    if (Filter.this.mThreadHandler != null) {
                        Filter.this.mThreadHandler.sendMessageDelayed(Filter.this.mThreadHandler.obtainMessage(Filter.FINISH_TOKEN), 3000L);
                    }
                }
            } finally {
                Message messageObtainMessage = Filter.this.mResultHandler.obtainMessage(i);
                messageObtainMessage.obj = requestArguments;
                messageObtainMessage.sendToTarget();
            }
        }
    }

    private class ResultsHandler extends Handler {
        private ResultsHandler() {
        }

        @Override // android.os.Handler
        public void handleMessage(Message message) {
            RequestArguments requestArguments = (RequestArguments) message.obj;
            Filter.this.publishResults(requestArguments.constraint, requestArguments.results);
            if (requestArguments.listener != null) {
                requestArguments.listener.onFilterComplete(requestArguments.results != null ? requestArguments.results.count : -1);
            }
        }
    }

    private static class RequestArguments {
        CharSequence constraint;
        FilterListener listener;
        FilterResults results;

        private RequestArguments() {
        }
    }
}
