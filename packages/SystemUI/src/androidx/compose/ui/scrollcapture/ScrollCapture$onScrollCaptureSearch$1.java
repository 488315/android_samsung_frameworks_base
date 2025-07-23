package androidx.compose.ui.scrollcapture;

import androidx.compose.runtime.collection.MutableVector;
import kotlin.Unit;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.internal.AdaptedFunctionReference;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes.dex */
final /* synthetic */ class ScrollCapture$onScrollCaptureSearch$1 extends AdaptedFunctionReference implements Function1 {
    public ScrollCapture$onScrollCaptureSearch$1(Object obj) {
        super(1, obj, MutableVector.class, "add", "add(Ljava/lang/Object;)Z", 8);
    }

    @Override // kotlin.jvm.functions.Function1
    /* renamed from: invoke */
    public final Object mo779invoke(Object obj) {
        ((MutableVector) this.receiver).add((ScrollCaptureCandidate) obj);
        return Unit.INSTANCE;
    }
}
