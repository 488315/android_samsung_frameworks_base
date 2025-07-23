package androidx.compose.ui.platform;

import androidx.compose.ui.platform.AndroidComposeView;
import kotlin.jvm.functions.Function0;
import kotlin.jvm.internal.FunctionReferenceImpl;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes.dex */
final /* synthetic */ class AndroidComposeView$focusOwner$5 extends FunctionReferenceImpl implements Function0 {
    public AndroidComposeView$focusOwner$5(Object obj) {
        super(0, obj, AndroidComposeView.class, "onFetchFocusRect", "onFetchFocusRect()Landroidx/compose/ui/geometry/Rect;", 0);
    }

    @Override // kotlin.jvm.functions.Function0
    public final Object invoke() {
        AndroidComposeView androidComposeView = (AndroidComposeView) this.receiver;
        AndroidComposeView.Companion companion = AndroidComposeView.Companion;
        return androidComposeView.onFetchFocusRect();
    }
}
