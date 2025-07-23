package androidx.compose.ui.platform;

import android.view.View;
import android.view.contentcapture.ContentCaptureSession;
import androidx.compose.ui.platform.coreshims.ContentCaptureSessionCompat;
import kotlin.jvm.functions.Function0;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.internal.FunctionReferenceImpl;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes.dex */
final /* synthetic */ class AndroidComposeView$contentCaptureManager$1 extends FunctionReferenceImpl implements Function0 {
    public AndroidComposeView$contentCaptureManager$1(Object obj) {
        super(0, obj, AndroidComposeView_androidKt.class, "getContentCaptureSessionCompat", "getContentCaptureSessionCompat(Landroid/view/View;)Landroidx/compose/ui/platform/coreshims/ContentCaptureSessionCompat;", 1);
    }

    @Override // kotlin.jvm.functions.Function0
    public final Object invoke() {
        View view = (View) this.receiver;
        Function1 function1 = AndroidComposeView_androidKt.platformTextInputServiceInterceptor;
        view.setImportantForContentCapture(1);
        ContentCaptureSession contentCaptureSession = view.getContentCaptureSession();
        if (contentCaptureSession == null) {
            return null;
        }
        return ContentCaptureSessionCompat.toContentCaptureSessionCompat(contentCaptureSession, view);
    }
}
