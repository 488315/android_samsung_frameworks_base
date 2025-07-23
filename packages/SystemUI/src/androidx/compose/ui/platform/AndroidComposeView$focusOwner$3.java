package androidx.compose.ui.platform;

import androidx.compose.ui.focus.FocusDirection;
import androidx.compose.ui.platform.AndroidComposeView;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.internal.FunctionReferenceImpl;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes.dex */
final /* synthetic */ class AndroidComposeView$focusOwner$3 extends FunctionReferenceImpl implements Function1 {
    public AndroidComposeView$focusOwner$3(Object obj) {
        super(1, obj, AndroidComposeView.class, "onMoveFocusInChildren", "onMoveFocusInChildren-3ESFkO8(I)Z", 0);
    }

    @Override // kotlin.jvm.functions.Function1
    /* renamed from: invoke */
    public final Object mo779invoke(Object obj) {
        int i = ((FocusDirection) obj).value;
        AndroidComposeView androidComposeView = (AndroidComposeView) this.receiver;
        AndroidComposeView.Companion companion = AndroidComposeView.Companion;
        return Boolean.valueOf(androidComposeView.m695onMoveFocusInChildren3ESFkO8(i));
    }
}
