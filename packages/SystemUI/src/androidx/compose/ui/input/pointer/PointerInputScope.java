package androidx.compose.ui.input.pointer;

import androidx.compose.ui.platform.ViewConfiguration;
import androidx.compose.ui.unit.Density;
import kotlin.coroutines.Continuation;
import kotlin.jvm.functions.Function2;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes.dex */
public interface PointerInputScope extends Density {
    Object awaitPointerEventScope(Function2 function2, Continuation continuation);

    /* renamed from: getSize-YbymL2g */
    long mo50getSizeYbymL2g();

    ViewConfiguration getViewConfiguration();

    void setInterceptOutOfBoundsChildEvents();
}
