package android.print;

import android.content.Context;
import android.content.Loader;
import android.os.Handler;
import android.os.Message;
import android.printservice.PrintServiceInfo;

import com.android.internal.util.Preconditions;

import java.util.List;

public class PrintServicesLoader extends Loader<List<PrintServiceInfo>> {
    private final Handler mHandler;
    private PrintManager.PrintServicesChangeListener mListener;
    private final PrintManager mPrintManager;
    private final int mSelectionFlags;

    public PrintServicesLoader(PrintManager printManager, Context context, int selectionFlags) {
        super((Context) Preconditions.checkNotNull(context));
        this.mHandler = new MyHandler();
        this.mPrintManager = (PrintManager) Preconditions.checkNotNull(printManager);
        this.mSelectionFlags = Preconditions.checkFlagsArgument(selectionFlags, 3);
    }

    @Override // android.content.Loader
    protected void onForceLoad() {
        queueNewResult();
    }

    public void queueNewResult() {
        Message m = this.mHandler.obtainMessage(0);
        m.obj = this.mPrintManager.getPrintServices(this.mSelectionFlags);
        this.mHandler.sendMessage(m);
    }

    @Override // android.content.Loader
    protected void onStartLoading() {
        this.mListener =
                new PrintManager
                        .PrintServicesChangeListener() { // from class:
                                                         // android.print.PrintServicesLoader.1
                    @Override // android.print.PrintManager.PrintServicesChangeListener
                    public void onPrintServicesChanged() {
                        PrintServicesLoader.this.queueNewResult();
                    }
                };
        this.mPrintManager.addPrintServicesChangeListener(this.mListener, null);
        deliverResult(this.mPrintManager.getPrintServices(this.mSelectionFlags));
    }

    @Override // android.content.Loader
    protected void onStopLoading() {
        if (this.mListener != null) {
            this.mPrintManager.removePrintServicesChangeListener(this.mListener);
            this.mListener = null;
        }
        this.mHandler.removeMessages(0);
    }

    @Override // android.content.Loader
    protected void onReset() {
        onStopLoading();
    }

    private class MyHandler extends Handler {
        public MyHandler() {
            super(PrintServicesLoader.this.getContext().getMainLooper());
        }

        @Override // android.os.Handler
        public void handleMessage(Message msg) {
            if (PrintServicesLoader.this.isStarted()) {
                PrintServicesLoader.this.deliverResult((List) msg.obj);
            }
        }
    }
}
