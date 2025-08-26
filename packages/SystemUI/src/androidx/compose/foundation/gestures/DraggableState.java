package androidx.compose.foundation.gestures;

import androidx.compose.foundation.MutatePriority;
import kotlin.coroutines.Continuation;
import kotlin.jvm.functions.Function2;

/* loaded from: classes.dex */
public interface DraggableState {
    Object drag(MutatePriority mutatePriority, Function2 function2, Continuation continuation);
}
