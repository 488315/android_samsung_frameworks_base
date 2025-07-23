package android.print;

import android.os.Bundle;
import android.os.CancellationSignal;
import android.os.ParcelFileDescriptor;

/* loaded from: classes3.dex */
public abstract class PrintDocumentAdapter {
    public static final String EXTRA_PRINT_PREVIEW = "EXTRA_PRINT_PREVIEW";

    public static abstract class LayoutResultCallback {
        public void onLayoutCancelled() {
        }

        public void onLayoutFailed(CharSequence charSequence) {
        }

        public void onLayoutFinished(PrintDocumentInfo printDocumentInfo, boolean z) {
        }
    }

    public static abstract class WriteResultCallback {
        public void onWriteCancelled() {
        }

        public void onWriteFailed(CharSequence charSequence) {
        }

        public void onWriteFinished(PageRange[] pageRangeArr) {
        }
    }

    public void onFinish() {
    }

    public abstract void onLayout(PrintAttributes printAttributes, PrintAttributes printAttributes2, CancellationSignal cancellationSignal, LayoutResultCallback layoutResultCallback, Bundle bundle);

    public void onStart() {
    }

    public abstract void onWrite(PageRange[] pageRangeArr, ParcelFileDescriptor parcelFileDescriptor, CancellationSignal cancellationSignal, WriteResultCallback writeResultCallback);
}
