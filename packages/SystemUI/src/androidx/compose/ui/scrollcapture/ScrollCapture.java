package androidx.compose.ui.scrollcapture;

import android.graphics.Point;
import android.view.ScrollCaptureTarget;
import androidx.compose.runtime.MutableState;
import androidx.compose.runtime.SnapshotStateKt;
import androidx.compose.runtime.collection.MutableVector;
import androidx.compose.ui.geometry.Rect;
import androidx.compose.ui.graphics.RectHelper_androidKt;
import androidx.compose.ui.layout.LayoutCoordinatesKt;
import androidx.compose.ui.platform.AndroidComposeView;
import androidx.compose.ui.scrollcapture.ComposeScrollCaptureCallback;
import androidx.compose.ui.semantics.SemanticsOwner;
import androidx.compose.ui.unit.IntOffset;
import androidx.compose.ui.unit.IntRect;
import androidx.compose.ui.unit.IntRectKt;
import java.util.function.Consumer;
import kotlin.comparisons.ComparisonsKt__ComparisonsKt;
import kotlin.coroutines.CoroutineContext;
import kotlin.jvm.functions.Function1;
import kotlinx.coroutines.CoroutineScopeKt;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes.dex */
public final class ScrollCapture implements ComposeScrollCaptureCallback.ScrollCaptureSessionListener {
    public final MutableState scrollCaptureInProgress$delegate = SnapshotStateKt.mutableStateOf$default(Boolean.FALSE);

    public final void onScrollCaptureSearch(AndroidComposeView androidComposeView, SemanticsOwner semanticsOwner, CoroutineContext coroutineContext, Consumer consumer) {
        Object obj;
        MutableVector mutableVector = new MutableVector(new ScrollCaptureCandidate[16], 0);
        ScrollCapture_androidKt.visitScrollCaptureCandidates(semanticsOwner.getUnmergedRootSemanticsNode(), 0, new ScrollCapture$onScrollCaptureSearch$1(mutableVector));
        mutableVector.sortWith(ComparisonsKt__ComparisonsKt.compareBy(new Function1() { // from class: androidx.compose.ui.scrollcapture.ScrollCapture$onScrollCaptureSearch$2
            @Override // kotlin.jvm.functions.Function1
            /* renamed from: invoke */
            public final Object mo779invoke(Object obj2) {
                return Integer.valueOf(((ScrollCaptureCandidate) obj2).depth);
            }
        }, new Function1() { // from class: androidx.compose.ui.scrollcapture.ScrollCapture$onScrollCaptureSearch$3
            @Override // kotlin.jvm.functions.Function1
            /* renamed from: invoke */
            public final Object mo779invoke(Object obj2) {
                return Integer.valueOf(((ScrollCaptureCandidate) obj2).viewportBoundsInWindow.getHeight());
            }
        }));
        int i = mutableVector.size;
        if (i == 0) {
            obj = null;
        } else {
            obj = mutableVector.content[i - 1];
        }
        ScrollCaptureCandidate scrollCaptureCandidate = (ScrollCaptureCandidate) obj;
        if (scrollCaptureCandidate == null) {
            return;
        }
        ComposeScrollCaptureCallback composeScrollCaptureCallback = new ComposeScrollCaptureCallback(scrollCaptureCandidate.node, scrollCaptureCandidate.viewportBoundsInWindow, CoroutineScopeKt.CoroutineScope(coroutineContext), this, androidComposeView);
        Rect boundsInRoot = LayoutCoordinatesKt.boundsInRoot(scrollCaptureCandidate.coordinates);
        IntRect intRect = scrollCaptureCandidate.viewportBoundsInWindow;
        long m857getTopLeftnOccac = intRect.m857getTopLeftnOccac();
        android.graphics.Rect androidRect = RectHelper_androidKt.toAndroidRect(IntRectKt.roundToIntRect(boundsInRoot));
        IntOffset.Companion companion = IntOffset.Companion;
        ScrollCaptureTarget scrollCaptureTarget = new ScrollCaptureTarget(androidComposeView, androidRect, new Point((int) (m857getTopLeftnOccac >> 32), (int) (m857getTopLeftnOccac & 4294967295L)), composeScrollCaptureCallback);
        scrollCaptureTarget.setScrollBounds(RectHelper_androidKt.toAndroidRect(intRect));
        consumer.accept(scrollCaptureTarget);
    }
}
