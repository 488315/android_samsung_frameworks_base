package android.print;

import android.content.Context;
import android.content.Loader;
import android.os.Handler;
import android.os.Message;
import android.print.PrintManager;
import android.printservice.PrintServiceInfo;
import com.android.internal.util.Preconditions;
import java.util.List;

/* loaded from: classes3.dex */
public class PrintServicesLoader extends Loader<List<PrintServiceInfo>> {
    private final Handler mHandler;
    private PrintManager.PrintServicesChangeListener mListener;
    private final PrintManager mPrintManager;
    private final int mSelectionFlags;

    public PrintServicesLoader(PrintManager printManager, Context context, int i) {
        super((Context) Preconditions.checkNotNull(context));
        this.mHandler = new MyHandler();
        this.mPrintManager = (PrintManager) Preconditions.checkNotNull(printManager);
        this.mSelectionFlags = Preconditions.checkFlagsArgument(i, 3);
    }

    @Override // android.content.Loader
    protected void onForceLoad() {
        queueNewResult();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void queueNewResult() {
        Message obtainMessage = this.mHandler.obtainMessage(0);
        obtainMessage.obj = this.mPrintManager.getPrintServices(this.mSelectionFlags);
        this.mHandler.sendMessage(obtainMessage);
    }

    @Override // android.content.Loader
    protected void onStartLoading() {
        PrintManager.PrintServicesChangeListener printServicesChangeListener = new PrintManager.PrintServicesChangeListener() { // from class: android.print.PrintServicesLoader.1
            @Override // android.print.PrintManager.PrintServicesChangeListener
            public void onPrintServicesChanged() {
                PrintServicesLoader.this.queueNewResult();
            }
        };
        this.mListener = printServicesChangeListener;
        this.mPrintManager.addPrintServicesChangeListener(printServicesChangeListener, null);
        deliverResult(this.mPrintManager.getPrintServices(this.mSelectionFlags));
    }

    @Override // android.content.Loader
    protected void onStopLoading() {
        PrintManager.PrintServicesChangeListener printServicesChangeListener = this.mListener;
        if (printServicesChangeListener != null) {
            this.mPrintManager.removePrintServicesChangeListener(printServicesChangeListener);
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
        public void handleMessage(Message message) {
            if (PrintServicesLoader.this.isStarted()) {
                PrintServicesLoader.this.deliverResult((List) message.obj);
            }
        }
    }
}
