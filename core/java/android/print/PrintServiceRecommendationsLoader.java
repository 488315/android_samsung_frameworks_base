package android.print;

import android.content.Context;
import android.content.Loader;
import android.p009os.Handler;
import android.p009os.Message;
import android.printservice.recommendation.RecommendationInfo;
import com.android.internal.util.Preconditions;
import java.util.List;

/* loaded from: classes3.dex */
public class PrintServiceRecommendationsLoader extends Loader<List<RecommendationInfo>> {
  private final Handler mHandler;
  private PrintManager.PrintServiceRecommendationsChangeListener mListener;
  private final PrintManager mPrintManager;

  public PrintServiceRecommendationsLoader(PrintManager printManager, Context context) {
    super((Context) Preconditions.checkNotNull(context));
    this.mHandler = new MyHandler();
    this.mPrintManager = (PrintManager) Preconditions.checkNotNull(printManager);
  }

  @Override // android.content.Loader
  protected void onForceLoad() {
    queueNewResult();
  }

  /* JADX INFO: Access modifiers changed from: private */
  public void queueNewResult() {
    Message m = this.mHandler.obtainMessage(0);
    m.obj = this.mPrintManager.getPrintServiceRecommendations();
    this.mHandler.sendMessage(m);
  }

  @Override // android.content.Loader
  protected void onStartLoading() {
    PrintManager.PrintServiceRecommendationsChangeListener
        printServiceRecommendationsChangeListener =
            new PrintManager
                .PrintServiceRecommendationsChangeListener() { // from class:
                                                               // android.print.PrintServiceRecommendationsLoader.1
              @Override // android.print.PrintManager.PrintServiceRecommendationsChangeListener
              public void onPrintServiceRecommendationsChanged() {
                PrintServiceRecommendationsLoader.this.queueNewResult();
              }
            };
    this.mListener = printServiceRecommendationsChangeListener;
    this.mPrintManager.addPrintServiceRecommendationsChangeListener(
        printServiceRecommendationsChangeListener, null);
    deliverResult(this.mPrintManager.getPrintServiceRecommendations());
  }

  @Override // android.content.Loader
  protected void onStopLoading() {
    PrintManager.PrintServiceRecommendationsChangeListener
        printServiceRecommendationsChangeListener = this.mListener;
    if (printServiceRecommendationsChangeListener != null) {
      this.mPrintManager.removePrintServiceRecommendationsChangeListener(
          printServiceRecommendationsChangeListener);
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
      super(PrintServiceRecommendationsLoader.this.getContext().getMainLooper());
    }

    @Override // android.p009os.Handler
    public void handleMessage(Message msg) {
      if (PrintServiceRecommendationsLoader.this.isStarted()) {
        PrintServiceRecommendationsLoader.this.deliverResult((List) msg.obj);
      }
    }
  }
}
