package androidx.compose.foundation.interaction;

import kotlin.coroutines.Continuation;

/* loaded from: classes.dex */
public interface MutableInteractionSource extends InteractionSource {
    Object emit(Interaction interaction, Continuation continuation);

    boolean tryEmit(Interaction interaction);
}
