package androidx.compose.ui.scrollcapture;

import android.graphics.Rect;
import android.os.CancellationSignal;
import android.view.ScrollCaptureCallback;
import android.view.ScrollCaptureSession;
import android.view.View;
import androidx.compose.runtime.SnapshotMutableStateImpl;
import androidx.compose.ui.graphics.RectHelper_androidKt;
import androidx.compose.ui.semantics.SemanticsNode;
import androidx.compose.ui.unit.IntRect;
import java.util.function.Consumer;
import kotlin.Unit;
import kotlin.jvm.functions.Function1;
import kotlinx.coroutines.BuildersKt;
import kotlinx.coroutines.CoroutineScope;
import kotlinx.coroutines.NonCancellable;
import kotlinx.coroutines.StandaloneCoroutine;
import kotlinx.coroutines.internal.ContextScope;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes.dex */
public final class ComposeScrollCaptureCallback implements ScrollCaptureCallback {
    public final View composeView;
    public final ContextScope coroutineScope;
    public final ScrollCaptureSessionListener listener;
    public final SemanticsNode node;
    public final RelativeScroller scrollTracker;
    public final IntRect viewportBoundsInWindow;

    /* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
    public interface ScrollCaptureSessionListener {
    }

    public ComposeScrollCaptureCallback(SemanticsNode semanticsNode, IntRect intRect, CoroutineScope coroutineScope, ScrollCaptureSessionListener scrollCaptureSessionListener, View view) {
        this.node = semanticsNode;
        this.viewportBoundsInWindow = intRect;
        this.listener = scrollCaptureSessionListener;
        this.composeView = view;
        this.coroutineScope = new ContextScope(coroutineScope.getCoroutineContext().plus(DisableAnimationMotionDurationScale.INSTANCE));
        this.scrollTracker = new RelativeScroller(intRect.getHeight(), new ComposeScrollCaptureCallback$scrollTracker$1(this, null));
    }

    /* JADX WARN: Code restructure failed: missing block: B:43:0x00a4, code lost:
    
        if (r2 == r1) goto L40;
     */
    /* JADX WARN: Removed duplicated region for block: B:13:0x00f4  */
    /* JADX WARN: Removed duplicated region for block: B:16:0x00fc  */
    /* JADX WARN: Removed duplicated region for block: B:33:0x00c4  */
    /* JADX WARN: Removed duplicated region for block: B:34:0x005e  */
    /* JADX WARN: Removed duplicated region for block: B:8:0x0025  */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public static final java.lang.Object access$onScrollCaptureImageRequest(androidx.compose.ui.scrollcapture.ComposeScrollCaptureCallback r10, android.view.ScrollCaptureSession r11, androidx.compose.ui.unit.IntRect r12, kotlin.coroutines.jvm.internal.ContinuationImpl r13) {
        /*
            Method dump skipped, instructions count: 383
            To view this dump change 'Code comments level' option to 'DEBUG'
        */
        throw new UnsupportedOperationException("Method not decompiled: androidx.compose.ui.scrollcapture.ComposeScrollCaptureCallback.access$onScrollCaptureImageRequest(androidx.compose.ui.scrollcapture.ComposeScrollCaptureCallback, android.view.ScrollCaptureSession, androidx.compose.ui.unit.IntRect, kotlin.coroutines.jvm.internal.ContinuationImpl):java.lang.Object");
    }

    @Override // android.view.ScrollCaptureCallback
    public final void onScrollCaptureEnd(Runnable runnable) {
        BuildersKt.launch$default(this.coroutineScope, NonCancellable.INSTANCE, null, new ComposeScrollCaptureCallback$onScrollCaptureEnd$1(this, runnable, null), 2);
    }

    @Override // android.view.ScrollCaptureCallback
    public final void onScrollCaptureImageRequest(ScrollCaptureSession scrollCaptureSession, final CancellationSignal cancellationSignal, Rect rect, Consumer consumer) {
        final StandaloneCoroutine launch$default = BuildersKt.launch$default(this.coroutineScope, null, null, new ComposeScrollCaptureCallback$onScrollCaptureImageRequest$1(this, scrollCaptureSession, rect, consumer, null), 3);
        launch$default.invokeOnCompletion(new Function1() { // from class: androidx.compose.ui.scrollcapture.ComposeScrollCaptureCallback_androidKt$launchWithCancellationSignal$1
            /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
            {
                super(1);
            }

            @Override // kotlin.jvm.functions.Function1
            /* renamed from: invoke */
            public final Object mo779invoke(Object obj) {
                if (((Throwable) obj) != null) {
                    cancellationSignal.cancel();
                }
                return Unit.INSTANCE;
            }
        });
        cancellationSignal.setOnCancelListener(new CancellationSignal.OnCancelListener() { // from class: androidx.compose.ui.scrollcapture.ComposeScrollCaptureCallback_androidKt$$ExternalSyntheticLambda0
            @Override // android.os.CancellationSignal.OnCancelListener
            public final void onCancel() {
                StandaloneCoroutine.this.cancel(null);
            }
        });
    }

    @Override // android.view.ScrollCaptureCallback
    public final void onScrollCaptureSearch(CancellationSignal cancellationSignal, Consumer consumer) {
        consumer.accept(RectHelper_androidKt.toAndroidRect(this.viewportBoundsInWindow));
    }

    @Override // android.view.ScrollCaptureCallback
    public final void onScrollCaptureStart(ScrollCaptureSession scrollCaptureSession, CancellationSignal cancellationSignal, Runnable runnable) {
        this.scrollTracker.scrollAmount = 0.0f;
        ((SnapshotMutableStateImpl) ((ScrollCapture) this.listener).scrollCaptureInProgress$delegate).setValue(Boolean.TRUE);
        runnable.run();
    }
}
