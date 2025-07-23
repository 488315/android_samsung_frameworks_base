package androidx.compose.ui.platform.coreshims;

import android.view.View;
import android.view.autofill.AutofillId;
import android.view.contentcapture.ContentCaptureSession;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes.dex */
public class ContentCaptureSessionCompat {
    public final View mView;
    public final Object mWrappedObj;

    private ContentCaptureSessionCompat(ContentCaptureSession contentCaptureSession, View view) {
        this.mWrappedObj = contentCaptureSession;
        this.mView = view;
    }

    public static ContentCaptureSessionCompat toContentCaptureSessionCompat(ContentCaptureSession contentCaptureSession, View view) {
        return new ContentCaptureSessionCompat(contentCaptureSession, view);
    }

    public final AutofillId newAutofillId(long j) {
        return ((ContentCaptureSession) this.mWrappedObj).newAutofillId((AutofillId) AutofillIdCompat.toAutofillIdCompat(this.mView.getAutofillId()).mWrappedObj, j);
    }
}
