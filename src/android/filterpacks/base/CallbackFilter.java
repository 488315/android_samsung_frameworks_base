package android.filterpacks.base;

import android.filterfw.core.Filter;
import android.filterfw.core.FilterContext;
import android.filterfw.core.Frame;
import android.filterfw.core.GenerateFieldPort;
import android.filterfw.core.GenerateFinalPort;
import android.os.Handler;
import android.os.Looper;

/* loaded from: classes.dex */
public class CallbackFilter extends Filter {

    @GenerateFinalPort(hasDefault = true, name = "callUiThread")
    private boolean mCallbacksOnUiThread;

    @GenerateFieldPort(hasDefault = true, name = "listener")
    private FilterContext.OnFrameReceivedListener mListener;
    private Handler mUiThreadHandler;

    @GenerateFieldPort(hasDefault = true, name = "userData")
    private Object mUserData;

    private class CallbackRunnable implements Runnable {
        private Filter mFilter;
        private Frame mFrame;
        private FilterContext.OnFrameReceivedListener mListener;
        private Object mUserData;

        public CallbackRunnable(CallbackFilter callbackFilter, FilterContext.OnFrameReceivedListener onFrameReceivedListener, Filter filter, Frame frame, Object obj) {
            this.mListener = onFrameReceivedListener;
            this.mFilter = filter;
            this.mFrame = frame;
            this.mUserData = obj;
        }

        @Override // java.lang.Runnable
        public void run() {
            this.mListener.onFrameReceived(this.mFilter, this.mFrame, this.mUserData);
            this.mFrame.release();
        }
    }

    public CallbackFilter(String str) {
        super(str);
        this.mCallbacksOnUiThread = true;
    }

    @Override // android.filterfw.core.Filter
    public void setupPorts() {
        addInputPort("frame");
    }

    @Override // android.filterfw.core.Filter
    public void prepare(FilterContext filterContext) {
        if (this.mCallbacksOnUiThread) {
            this.mUiThreadHandler = new Handler(Looper.getMainLooper());
        }
    }

    @Override // android.filterfw.core.Filter
    public void process(FilterContext filterContext) {
        Frame framePullInput = pullInput("frame");
        FilterContext.OnFrameReceivedListener onFrameReceivedListener = this.mListener;
        if (onFrameReceivedListener != null) {
            if (this.mCallbacksOnUiThread) {
                framePullInput.retain();
                if (!this.mUiThreadHandler.post(new CallbackRunnable(this, this.mListener, this, framePullInput, this.mUserData))) {
                    throw new RuntimeException("Unable to send callback to UI thread!");
                }
                return;
            }
            onFrameReceivedListener.onFrameReceived(this, framePullInput, this.mUserData);
            return;
        }
        throw new RuntimeException("CallbackFilter received frame, but no listener set!");
    }
}
