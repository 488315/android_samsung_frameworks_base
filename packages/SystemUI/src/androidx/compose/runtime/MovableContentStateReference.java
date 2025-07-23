package androidx.compose.runtime;

import java.util.List;
import kotlin.Pair;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes.dex */
public final class MovableContentStateReference {
    public final Anchor anchor;
    public final ControlledComposition composition;
    public final MovableContent content;
    public List invalidations;
    public final PersistentCompositionLocalMap locals;
    public final List nestedReferences;
    public final Object parameter;
    public final SlotTable slotTable;

    public MovableContentStateReference(MovableContent<Object> movableContent, Object obj, ControlledComposition controlledComposition, SlotTable slotTable, Anchor anchor, List<? extends Pair<RecomposeScopeImpl, ? extends Object>> list, PersistentCompositionLocalMap persistentCompositionLocalMap, List<MovableContentStateReference> list2) {
        this.content = movableContent;
        this.parameter = obj;
        this.composition = controlledComposition;
        this.slotTable = slotTable;
        this.anchor = anchor;
        this.invalidations = list;
        this.locals = persistentCompositionLocalMap;
        this.nestedReferences = list2;
    }
}
