package androidx.compose.ui.input.pointer;

import kotlin.Unit;
import kotlin.jvm.functions.Function1;

/* loaded from: classes.dex */
public final class RequestDisallowInterceptTouchEvent implements Function1 {
    public PointerInteropFilter pointerInteropFilter;

    @Override // kotlin.jvm.functions.Function1
    /* renamed from: invoke */
    public final Object mo781invoke(Object obj) {
        boolean zBooleanValue = ((Boolean) obj).booleanValue();
        PointerInteropFilter pointerInteropFilter = this.pointerInteropFilter;
        if (pointerInteropFilter != null) {
            pointerInteropFilter.disallowIntercept = zBooleanValue;
        }
        return Unit.INSTANCE;
    }
}
