package android.print;

import android.content.Context;
import android.content.Loader;
import android.p009os.Handler;
import android.p009os.Message;
import android.printservice.PrintServiceInfo;
import com.android.internal.util.Preconditions;
import java.util.List;

/* loaded from: classes3.dex */
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

  /* JADX INFO: Access modifiers changed from: private */
  public void queueNewResult() {
    Message m = this.mHandler.obtainMessage(0);
    m.obj = this.mPrintManager.getPrintServices(this.mSelectionFlags);
    this.mHandler.sendMessage(m);
  }

  @Override // android.content.Loader
  protected void onStartLoading() {
    PrintManager.PrintServicesChangeListener printServicesChangeListener =
        new PrintManager
            .PrintServicesChangeListener() { // from class: android.print.PrintServicesLoader.1
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

    @Override // android.p009os.Handler
    public void handleMessage(Message msg) {
      if (PrintServicesLoader.this.isStarted()) {
        PrintServicesLoader.this.deliverResult((List) msg.obj);
      }
    }
  }
}
