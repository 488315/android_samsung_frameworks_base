package android.print;

import android.content.Context;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.CancellationSignal;
import android.os.FileUtils;
import android.os.OperationCanceledException;
import android.os.ParcelFileDescriptor;
import android.util.Log;
import com.android.internal.C4337R;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.concurrent.Executor;

/* loaded from: classes3.dex */
public class PrintFileDocumentAdapter extends PrintDocumentAdapter {
  private static final String LOG_TAG = "PrintedFileDocAdapter";
  private final Context mContext;
  private final PrintDocumentInfo mDocumentInfo;
  private final File mFile;
  private WriteFileAsyncTask mWriteFileAsyncTask;

  public PrintFileDocumentAdapter(Context context, File file, PrintDocumentInfo documentInfo) {
    if (file == null) {
      throw new IllegalArgumentException("File cannot be null!");
    }
    if (documentInfo == null) {
      throw new IllegalArgumentException("documentInfo cannot be null!");
    }
    this.mContext = context;
    this.mFile = file;
    this.mDocumentInfo = documentInfo;
  }

  @Override // android.print.PrintDocumentAdapter
  public void onLayout(
      PrintAttributes oldAttributes,
      PrintAttributes newAttributes,
      CancellationSignal cancellationSignal,
      PrintDocumentAdapter.LayoutResultCallback callback,
      Bundle metadata) {
    callback.onLayoutFinished(this.mDocumentInfo, false);
  }

  @Override // android.print.PrintDocumentAdapter
  public void onWrite(
      PageRange[] pages,
      ParcelFileDescriptor destination,
      CancellationSignal cancellationSignal,
      PrintDocumentAdapter.WriteResultCallback callback) {
    WriteFileAsyncTask writeFileAsyncTask =
        new WriteFileAsyncTask(destination, cancellationSignal, callback);
    this.mWriteFileAsyncTask = writeFileAsyncTask;
    writeFileAsyncTask.executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR, null);
  }

  private final class WriteFileAsyncTask extends AsyncTask<Void, Void, Void> {
    private final CancellationSignal mCancellationSignal;
    private final ParcelFileDescriptor mDestination;
    private final PrintDocumentAdapter.WriteResultCallback mResultCallback;

    public WriteFileAsyncTask(
        ParcelFileDescriptor destination,
        CancellationSignal cancellationSignal,
        PrintDocumentAdapter.WriteResultCallback callback) {
      this.mDestination = destination;
      this.mResultCallback = callback;
      this.mCancellationSignal = cancellationSignal;
      cancellationSignal.setOnCancelListener(
          new CancellationSignal
              .OnCancelListener() { // from class:
                                    // android.print.PrintFileDocumentAdapter.WriteFileAsyncTask.1
            @Override // android.os.CancellationSignal.OnCancelListener
            public void onCancel() {
              WriteFileAsyncTask.this.cancel(true);
            }
          });
    }

    /* JADX INFO: Access modifiers changed from: protected */
    @Override // android.os.AsyncTask
    public Void doInBackground(Void... params) {
      try {
        InputStream in = new FileInputStream(PrintFileDocumentAdapter.this.mFile);
        try {
          OutputStream out = new FileOutputStream(this.mDestination.getFileDescriptor());
          try {
            FileUtils.copy(
                in,
                out,
                this.mCancellationSignal,
                (Executor) null,
                (FileUtils.ProgressListener) null);
            out.close();
            in.close();
          } finally {
          }
        } finally {
        }
      } catch (OperationCanceledException e) {
      } catch (IOException e2) {
        Log.m97e(PrintFileDocumentAdapter.LOG_TAG, "Error writing data!", e2);
        this.mResultCallback.onWriteFailed(
            PrintFileDocumentAdapter.this.mContext.getString(
                C4337R.string.write_fail_reason_cannot_write));
      }
      return null;
    }

    /* JADX INFO: Access modifiers changed from: protected */
    @Override // android.os.AsyncTask
    public void onPostExecute(Void result) {
      this.mResultCallback.onWriteFinished(new PageRange[] {PageRange.ALL_PAGES});
    }

    /* JADX INFO: Access modifiers changed from: protected */
    @Override // android.os.AsyncTask
    public void onCancelled(Void result) {
      this.mResultCallback.onWriteFailed(
          PrintFileDocumentAdapter.this.mContext.getString(
              C4337R.string.write_fail_reason_cancelled));
    }
  }
}
