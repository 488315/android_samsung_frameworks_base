package androidx.compose.foundation.text;

import androidx.compose.foundation.interaction.InteractionSource;
import androidx.compose.runtime.MutableIntState;
import androidx.compose.runtime.SnapshotIntStateKt;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes.dex */
public final class LinkStateInteractionSourceObserver {
    public final InteractionSource interactionSource;
    public final MutableIntState interactionState = SnapshotIntStateKt.mutableIntStateOf(0);

    public LinkStateInteractionSourceObserver(InteractionSource interactionSource) {
        this.interactionSource = interactionSource;
    }
}
