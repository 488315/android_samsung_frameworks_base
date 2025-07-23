package android.print;

import android.content.Context;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.CancellationSignal;
import android.os.FileUtils;
import android.os.OperationCanceledException;
import android.os.ParcelFileDescriptor;
import android.print.PrintDocumentAdapter;
import android.util.Log;
import com.android.internal.R;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.concurrent.Executor;

/* loaded from: classes3.dex */
public class PrintFileDocumentAdapter extends PrintDocumentAdapter {
    private static final String LOG_TAG = "PrintedFileDocAdapter";
    private final Context mContext;
    private final PrintDocumentInfo mDocumentInfo;
    private final File mFile;
    private WriteFileAsyncTask mWriteFileAsyncTask;

    public PrintFileDocumentAdapter(Context context, File file, PrintDocumentInfo printDocumentInfo) {
        if (file == null) {
            throw new IllegalArgumentException("File cannot be null!");
        }
        if (printDocumentInfo == null) {
            throw new IllegalArgumentException("documentInfo cannot be null!");
        }
        this.mContext = context;
        this.mFile = file;
        this.mDocumentInfo = printDocumentInfo;
    }

    @Override // android.print.PrintDocumentAdapter
    public void onLayout(PrintAttributes printAttributes, PrintAttributes printAttributes2, CancellationSignal cancellationSignal, PrintDocumentAdapter.LayoutResultCallback layoutResultCallback, Bundle bundle) {
        layoutResultCallback.onLayoutFinished(this.mDocumentInfo, false);
    }

    @Override // android.print.PrintDocumentAdapter
    public void onWrite(PageRange[] pageRangeArr, ParcelFileDescriptor parcelFileDescriptor, CancellationSignal cancellationSignal, PrintDocumentAdapter.WriteResultCallback writeResultCallback) {
        WriteFileAsyncTask writeFileAsyncTask = new WriteFileAsyncTask(parcelFileDescriptor, cancellationSignal, writeResultCallback);
        this.mWriteFileAsyncTask = writeFileAsyncTask;
        writeFileAsyncTask.executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR, null);
    }

    private final class WriteFileAsyncTask extends AsyncTask<Void, Void, Void> {
        private final CancellationSignal mCancellationSignal;
        private final ParcelFileDescriptor mDestination;
        private final PrintDocumentAdapter.WriteResultCallback mResultCallback;

        public WriteFileAsyncTask(ParcelFileDescriptor parcelFileDescriptor, CancellationSignal cancellationSignal, PrintDocumentAdapter.WriteResultCallback writeResultCallback) {
            this.mDestination = parcelFileDescriptor;
            this.mResultCallback = writeResultCallback;
            this.mCancellationSignal = cancellationSignal;
            cancellationSignal.setOnCancelListener(new CancellationSignal.OnCancelListener() { // from class: android.print.PrintFileDocumentAdapter.WriteFileAsyncTask.1
                @Override // android.os.CancellationSignal.OnCancelListener
                public void onCancel() {
                    WriteFileAsyncTask.this.cancel(true);
                }
            });
        }

        /* JADX INFO: Access modifiers changed from: protected */
        @Override // android.os.AsyncTask
        public Void doInBackground(Void... voidArr) {
            FileInputStream fileInputStream;
            try {
                fileInputStream = new FileInputStream(PrintFileDocumentAdapter.this.mFile);
            } catch (OperationCanceledException unused) {
            } catch (IOException e) {
                Log.e(PrintFileDocumentAdapter.LOG_TAG, "Error writing data!", e);
                this.mResultCallback.onWriteFailed(PrintFileDocumentAdapter.this.mContext.getString(R.string.write_fail_reason_cannot_write));
            }
            try {
                FileOutputStream fileOutputStream = new FileOutputStream(this.mDestination.getFileDescriptor());
                try {
                    FileUtils.copy(fileInputStream, fileOutputStream, this.mCancellationSignal, (Executor) null, (FileUtils.ProgressListener) null);
                    fileOutputStream.close();
                    fileInputStream.close();
                    return null;
                } finally {
                }
            } finally {
            }
        }

        /* JADX INFO: Access modifiers changed from: protected */
        @Override // android.os.AsyncTask
        public void onPostExecute(Void r3) {
            this.mResultCallback.onWriteFinished(new PageRange[]{PageRange.ALL_PAGES});
        }

        /* JADX INFO: Access modifiers changed from: protected */
        @Override // android.os.AsyncTask
        public void onCancelled(Void r2) {
            this.mResultCallback.onWriteFailed(PrintFileDocumentAdapter.this.mContext.getString(R.string.write_fail_reason_cancelled));
        }
    }
}
