package androidx.compose.ui.input.pointer;

import androidx.compose.ui.platform.ViewConfiguration;
import androidx.compose.ui.unit.Density;
import kotlin.coroutines.Continuation;
import kotlin.jvm.functions.Function2;

/* loaded from: classes.dex */
public interface PointerInputScope extends Density {
    Object awaitPointerEventScope(Function2 function2, Continuation continuation);

    /* renamed from: getSize-YbymL2g */
    long mo51getSizeYbymL2g();

    ViewConfiguration getViewConfiguration();

    void setInterceptOutOfBoundsChildEvents();
}
