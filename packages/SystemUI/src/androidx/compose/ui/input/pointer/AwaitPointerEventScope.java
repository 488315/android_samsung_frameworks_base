package androidx.compose.ui.input.pointer;

import androidx.compose.ui.platform.ViewConfiguration;
import androidx.compose.ui.unit.Density;
import kotlin.coroutines.Continuation;
import kotlin.coroutines.jvm.internal.BaseContinuationImpl;
import kotlin.jvm.functions.Function2;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes.dex */
public interface AwaitPointerEventScope extends Density {
    Object awaitPointerEvent(PointerEventPass pointerEventPass, Continuation continuation);

    PointerEvent getCurrentEvent();

    /* renamed from: getExtendedTouchPadding-NH-jbRc, reason: not valid java name */
    long mo584getExtendedTouchPaddingNHjbRc();

    /* renamed from: getSize-YbymL2g, reason: not valid java name */
    long mo585getSizeYbymL2g();

    ViewConfiguration getViewConfiguration();

    Object withTimeout(long j, Function2 function2, BaseContinuationImpl baseContinuationImpl);

    Object withTimeoutOrNull(long j, Function2 function2, BaseContinuationImpl baseContinuationImpl);
}
