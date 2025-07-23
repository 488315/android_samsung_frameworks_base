package androidx.compose.ui.input.pointer;

import kotlin.Unit;
import kotlin.jvm.functions.Function1;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes.dex */
public final class RequestDisallowInterceptTouchEvent implements Function1 {
    public PointerInteropFilter pointerInteropFilter;

    @Override // kotlin.jvm.functions.Function1
    /* renamed from: invoke */
    public final Object mo779invoke(Object obj) {
        boolean booleanValue = ((Boolean) obj).booleanValue();
        PointerInteropFilter pointerInteropFilter = this.pointerInteropFilter;
        if (pointerInteropFilter != null) {
            pointerInteropFilter.disallowIntercept = booleanValue;
        }
        return Unit.INSTANCE;
    }
}
